package com.android.systemui.qs.external;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.UserHandle;
import android.service.quicksettings.IQSService;
import android.service.quicksettings.IQSTileService;
import android.util.ArraySet;
import android.util.Log;
import com.android.systemui.broadcast.BroadcastDispatcher;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/external/TileLifecycleManager.class */
public class TileLifecycleManager extends BroadcastReceiver implements IQSTileService, ServiceConnection, IBinder.DeathRecipient {
    public int mBindRetryDelay;
    public int mBindTryCount;
    public boolean mBound;
    public final BroadcastDispatcher mBroadcastDispatcher;
    public TileChangeListener mChangeListener;
    public IBinder mClickBinder;
    public final Context mContext;
    public final Handler mHandler;
    public final Intent mIntent;
    public boolean mIsBound;
    public boolean mListening;
    public final PackageManagerAdapter mPackageManagerAdapter;
    public AtomicBoolean mPackageReceiverRegistered;
    public Set<Integer> mQueuedMessages;
    public final IBinder mToken;
    public boolean mUnbindImmediate;
    public final UserHandle mUser;
    public AtomicBoolean mUserReceiverRegistered;
    public QSTileServiceWrapper mWrapper;

    /* loaded from: mainsysui33.jar:com/android/systemui/qs/external/TileLifecycleManager$Factory.class */
    public interface Factory {
        TileLifecycleManager create(Intent intent, UserHandle userHandle);
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/qs/external/TileLifecycleManager$TileChangeListener.class */
    public interface TileChangeListener {
        void onTileChanged(ComponentName componentName);
    }

    public TileLifecycleManager(Handler handler, Context context, IQSService iQSService, PackageManagerAdapter packageManagerAdapter, BroadcastDispatcher broadcastDispatcher, Intent intent, UserHandle userHandle) {
        Binder binder = new Binder();
        this.mToken = binder;
        this.mQueuedMessages = new ArraySet();
        this.mBindRetryDelay = 1000;
        this.mPackageReceiverRegistered = new AtomicBoolean(false);
        this.mUserReceiverRegistered = new AtomicBoolean(false);
        this.mContext = context;
        this.mHandler = handler;
        this.mIntent = intent;
        intent.putExtra("service", iQSService.asBinder());
        intent.putExtra("token", binder);
        this.mUser = userHandle;
        this.mPackageManagerAdapter = packageManagerAdapter;
        this.mBroadcastDispatcher = broadcastDispatcher;
    }

    public IBinder asBinder() {
        QSTileServiceWrapper qSTileServiceWrapper = this.mWrapper;
        return qSTileServiceWrapper != null ? qSTileServiceWrapper.asBinder() : null;
    }

    @Override // android.os.IBinder.DeathRecipient
    public void binderDied() {
        handleDeath();
    }

    public final boolean checkComponentState() {
        if (isPackageAvailable() && isComponentAvailable()) {
            return true;
        }
        startPackageListening();
        return false;
    }

    public void flushMessagesAndUnbind() {
        this.mUnbindImmediate = true;
        setBindService(true);
    }

    public final void freeWrapper() {
        QSTileServiceWrapper qSTileServiceWrapper = this.mWrapper;
        if (qSTileServiceWrapper != null) {
            try {
                qSTileServiceWrapper.asBinder().unlinkToDeath(this, 0);
            } catch (NoSuchElementException e) {
                Log.w("TileLifecycleManager", "Trying to unlink not linked recipient for component" + this.mIntent.getComponent().flattenToShortString());
            }
            this.mWrapper = null;
        }
    }

    public ComponentName getComponent() {
        return this.mIntent.getComponent();
    }

    public IBinder getToken() {
        return this.mToken;
    }

    public int getUserId() {
        return this.mUser.getIdentifier();
    }

    public final void handleDeath() {
        if (this.mWrapper == null) {
            return;
        }
        freeWrapper();
        this.mIsBound = false;
        if (this.mBound && checkComponentState()) {
            this.mHandler.postDelayed(new Runnable() { // from class: com.android.systemui.qs.external.TileLifecycleManager.1
                @Override // java.lang.Runnable
                public void run() {
                    if (TileLifecycleManager.this.mBound) {
                        TileLifecycleManager.this.setBindService(true);
                    }
                }
            }, this.mBindRetryDelay);
        }
    }

    public void handleDestroy() {
        if (this.mPackageReceiverRegistered.get() || this.mUserReceiverRegistered.get()) {
            stopPackageListening();
        }
        this.mChangeListener = null;
    }

    public final void handlePendingMessages() {
        ArraySet arraySet;
        synchronized (this.mQueuedMessages) {
            arraySet = new ArraySet(this.mQueuedMessages);
            this.mQueuedMessages.clear();
        }
        if (arraySet.contains(0)) {
            onTileAdded();
        }
        if (this.mListening) {
            onStartListening();
        }
        if (arraySet.contains(2)) {
            if (this.mListening) {
                onClick(this.mClickBinder);
            } else {
                Log.w("TileLifecycleManager", "Managed to get click on non-listening state...");
            }
        }
        if (arraySet.contains(3)) {
            if (this.mListening) {
                onUnlockComplete();
            } else {
                Log.w("TileLifecycleManager", "Managed to get unlock on non-listening state...");
            }
        }
        if (arraySet.contains(1)) {
            if (this.mListening) {
                Log.w("TileLifecycleManager", "Managed to get remove in listening state...");
                onStopListening();
            }
            onTileRemoved();
        }
        if (this.mUnbindImmediate) {
            this.mUnbindImmediate = false;
            setBindService(false);
        }
    }

    public boolean hasPendingClick() {
        boolean contains;
        synchronized (this.mQueuedMessages) {
            contains = this.mQueuedMessages.contains(2);
        }
        return contains;
    }

    public boolean isActiveTile() {
        boolean z;
        try {
            Bundle bundle = this.mPackageManagerAdapter.getServiceInfo(this.mIntent.getComponent(), 794752).metaData;
            z = false;
            if (bundle != null) {
                z = false;
                if (bundle.getBoolean("android.service.quicksettings.ACTIVE_TILE", false)) {
                    z = true;
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            z = false;
        }
        return z;
    }

    public final boolean isComponentAvailable() {
        this.mIntent.getComponent().getPackageName();
        boolean z = false;
        try {
            if (this.mPackageManagerAdapter.getServiceInfo(this.mIntent.getComponent(), 0, this.mUser.getIdentifier()) != null) {
                z = true;
            }
        } catch (RemoteException e) {
        }
        return z;
    }

    public final boolean isPackageAvailable() {
        String packageName = this.mIntent.getComponent().getPackageName();
        try {
            this.mPackageManagerAdapter.getPackageInfoAsUser(packageName, 0, this.mUser.getIdentifier());
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            Log.d("TileLifecycleManager", "Package not available: " + packageName);
            return false;
        }
    }

    public boolean isToggleableTile() {
        boolean z;
        try {
            Bundle bundle = this.mPackageManagerAdapter.getServiceInfo(this.mIntent.getComponent(), 794752).metaData;
            z = false;
            if (bundle != null) {
                z = false;
                if (bundle.getBoolean("android.service.quicksettings.TOGGLEABLE_TILE", false)) {
                    z = true;
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            z = false;
        }
        return z;
    }

    public void onClick(IBinder iBinder) {
        QSTileServiceWrapper qSTileServiceWrapper = this.mWrapper;
        if (qSTileServiceWrapper == null || !qSTileServiceWrapper.onClick(iBinder)) {
            this.mClickBinder = iBinder;
            queueMessage(2);
            handleDeath();
        }
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        TileChangeListener tileChangeListener;
        if ("android.intent.action.USER_UNLOCKED".equals(intent.getAction()) || Objects.equals(intent.getData().getEncodedSchemeSpecificPart(), this.mIntent.getComponent().getPackageName())) {
            if ("android.intent.action.PACKAGE_CHANGED".equals(intent.getAction()) && (tileChangeListener = this.mChangeListener) != null) {
                tileChangeListener.onTileChanged(this.mIntent.getComponent());
            }
            stopPackageListening();
            if (this.mBound) {
                setBindService(true);
            }
        }
    }

    @Override // android.content.ServiceConnection
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        this.mBindTryCount = 0;
        QSTileServiceWrapper qSTileServiceWrapper = new QSTileServiceWrapper(IQSTileService.Stub.asInterface(iBinder));
        try {
            iBinder.linkToDeath(this, 0);
        } catch (RemoteException e) {
        }
        this.mWrapper = qSTileServiceWrapper;
        handlePendingMessages();
    }

    @Override // android.content.ServiceConnection
    public void onServiceDisconnected(ComponentName componentName) {
        handleDeath();
    }

    public void onStartListening() {
        this.mListening = true;
        QSTileServiceWrapper qSTileServiceWrapper = this.mWrapper;
        if (qSTileServiceWrapper == null || qSTileServiceWrapper.onStartListening()) {
            return;
        }
        handleDeath();
    }

    public void onStopListening() {
        this.mListening = false;
        QSTileServiceWrapper qSTileServiceWrapper = this.mWrapper;
        if (qSTileServiceWrapper == null || qSTileServiceWrapper.onStopListening()) {
            return;
        }
        handleDeath();
    }

    public void onTileAdded() {
        QSTileServiceWrapper qSTileServiceWrapper = this.mWrapper;
        if (qSTileServiceWrapper == null || !qSTileServiceWrapper.onTileAdded()) {
            queueMessage(0);
            handleDeath();
        }
    }

    public void onTileRemoved() {
        QSTileServiceWrapper qSTileServiceWrapper = this.mWrapper;
        if (qSTileServiceWrapper == null || !qSTileServiceWrapper.onTileRemoved()) {
            queueMessage(1);
            handleDeath();
        }
    }

    public void onUnlockComplete() {
        QSTileServiceWrapper qSTileServiceWrapper = this.mWrapper;
        if (qSTileServiceWrapper == null || !qSTileServiceWrapper.onUnlockComplete()) {
            queueMessage(3);
            handleDeath();
        }
    }

    public final void queueMessage(int i) {
        synchronized (this.mQueuedMessages) {
            this.mQueuedMessages.add(Integer.valueOf(i));
        }
    }

    public void setBindService(boolean z) {
        if (this.mBound && this.mUnbindImmediate) {
            this.mUnbindImmediate = false;
            return;
        }
        this.mBound = z;
        if (!z) {
            this.mBindTryCount = 0;
            freeWrapper();
            if (this.mIsBound) {
                try {
                    this.mContext.unbindService(this);
                } catch (Exception e) {
                    Log.e("TileLifecycleManager", "Failed to unbind service " + this.mIntent.getComponent().flattenToShortString(), e);
                }
                this.mIsBound = false;
            }
        } else if (this.mBindTryCount == 5) {
            startPackageListening();
        } else if (checkComponentState()) {
            this.mBindTryCount++;
            try {
                boolean bindServiceAsUser = this.mContext.bindServiceAsUser(this.mIntent, this, 34603041, this.mUser);
                this.mIsBound = bindServiceAsUser;
                if (bindServiceAsUser) {
                    return;
                }
                this.mContext.unbindService(this);
            } catch (SecurityException e2) {
                Log.e("TileLifecycleManager", "Failed to bind to service", e2);
                this.mIsBound = false;
            }
        }
    }

    public void setTileChangeListener(TileChangeListener tileChangeListener) {
        this.mChangeListener = tileChangeListener;
    }

    public final void startPackageListening() {
        IntentFilter intentFilter = new IntentFilter("android.intent.action.PACKAGE_ADDED");
        intentFilter.addAction("android.intent.action.PACKAGE_CHANGED");
        intentFilter.addDataScheme("package");
        try {
            this.mPackageReceiverRegistered.set(true);
            this.mContext.registerReceiverAsUser(this, this.mUser, intentFilter, null, this.mHandler, 2);
        } catch (Exception e) {
            this.mPackageReceiverRegistered.set(false);
            Log.e("TileLifecycleManager", "Could not register package receiver", e);
        }
        IntentFilter intentFilter2 = new IntentFilter("android.intent.action.USER_UNLOCKED");
        try {
            this.mUserReceiverRegistered.set(true);
            this.mBroadcastDispatcher.registerReceiverWithHandler(this, intentFilter2, this.mHandler, this.mUser);
        } catch (Exception e2) {
            this.mUserReceiverRegistered.set(false);
            Log.e("TileLifecycleManager", "Could not register unlock receiver", e2);
        }
    }

    public final void stopPackageListening() {
        if (this.mUserReceiverRegistered.compareAndSet(true, false)) {
            this.mBroadcastDispatcher.unregisterReceiver(this);
        }
        if (this.mPackageReceiverRegistered.compareAndSet(true, false)) {
            this.mContext.unregisterReceiver(this);
        }
    }
}