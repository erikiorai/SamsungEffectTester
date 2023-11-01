package com.android.systemui.qs.external;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ResolveInfo;
import android.os.Handler;
import android.os.IBinder;
import android.service.quicksettings.IQSTileService;
import android.util.Log;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.qs.external.TileLifecycleManager;
import com.android.systemui.settings.UserTracker;
import java.util.Objects;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/external/TileServiceManager.class */
public class TileServiceManager {
    public static final String PREFS_FILE = "CustomTileModes";
    public boolean mBindAllowed;
    public boolean mBindRequested;
    public boolean mBound;
    public final Handler mHandler;
    public boolean mJustBound;
    public final Runnable mJustBoundOver;
    public long mLastUpdate;
    public boolean mPendingBind;
    public int mPriority;
    public final TileServices mServices;
    public boolean mShowingDialog;
    public boolean mStarted;
    public final TileLifecycleManager mStateManager;
    public final Runnable mUnbind;
    public final BroadcastReceiver mUninstallReceiver;
    public final UserTracker mUserTracker;

    public TileServiceManager(TileServices tileServices, Handler handler, ComponentName componentName, BroadcastDispatcher broadcastDispatcher, UserTracker userTracker) {
        this(tileServices, handler, userTracker, new TileLifecycleManager(handler, tileServices.getContext(), tileServices, new PackageManagerAdapter(tileServices.getContext()), broadcastDispatcher, new Intent().setComponent(componentName), userTracker.getUserHandle()));
    }

    public TileServiceManager(TileServices tileServices, Handler handler, UserTracker userTracker, TileLifecycleManager tileLifecycleManager) {
        this.mPendingBind = true;
        this.mStarted = false;
        this.mUnbind = new Runnable() { // from class: com.android.systemui.qs.external.TileServiceManager.1
            @Override // java.lang.Runnable
            public void run() {
                if (!TileServiceManager.this.mBound || TileServiceManager.this.mBindRequested) {
                    return;
                }
                TileServiceManager.this.unbindService();
            }
        };
        this.mJustBoundOver = new Runnable() { // from class: com.android.systemui.qs.external.TileServiceManager.2
            @Override // java.lang.Runnable
            public void run() {
                TileServiceManager.this.mJustBound = false;
                TileServiceManager.this.mServices.recalculateBindAllowance();
            }
        };
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() { // from class: com.android.systemui.qs.external.TileServiceManager.3
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                if ("android.intent.action.PACKAGE_REMOVED".equals(intent.getAction())) {
                    String encodedSchemeSpecificPart = intent.getData().getEncodedSchemeSpecificPart();
                    ComponentName component = TileServiceManager.this.mStateManager.getComponent();
                    if (Objects.equals(encodedSchemeSpecificPart, component.getPackageName())) {
                        if (intent.getBooleanExtra("android.intent.extra.REPLACING", false)) {
                            Intent intent2 = new Intent("android.service.quicksettings.action.QS_TILE");
                            intent2.setPackage(encodedSchemeSpecificPart);
                            for (ResolveInfo resolveInfo : context.getPackageManager().queryIntentServicesAsUser(intent2, 0, TileServiceManager.this.mUserTracker.getUserId())) {
                                if (Objects.equals(resolveInfo.serviceInfo.packageName, component.getPackageName()) && Objects.equals(resolveInfo.serviceInfo.name, component.getClassName())) {
                                    return;
                                }
                            }
                        }
                        TileServiceManager.this.mServices.getHost().removeTile(CustomTile.toSpec(component));
                    }
                }
            }
        };
        this.mUninstallReceiver = broadcastReceiver;
        this.mServices = tileServices;
        this.mHandler = handler;
        this.mStateManager = tileLifecycleManager;
        this.mUserTracker = userTracker;
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
        intentFilter.addDataScheme("package");
        tileServices.getContext().registerReceiverAsUser(broadcastReceiver, userTracker.getUserHandle(), intentFilter, null, handler, 2);
    }

    public final void bindService() {
        if (this.mBound) {
            Log.e("TileServiceManager", "Service already bound");
            return;
        }
        this.mPendingBind = true;
        this.mBound = true;
        this.mJustBound = true;
        this.mHandler.postDelayed(this.mJustBoundOver, 5000L);
        this.mStateManager.setBindService(true);
    }

    public void calculateBindPriority(long j) {
        if (this.mStateManager.hasPendingClick()) {
            this.mPriority = Integer.MAX_VALUE;
        } else if (this.mShowingDialog) {
            this.mPriority = 2147483646;
        } else if (this.mJustBound) {
            this.mPriority = 2147483645;
        } else if (!this.mBindRequested) {
            this.mPriority = Integer.MIN_VALUE;
        } else {
            long j2 = j - this.mLastUpdate;
            if (j2 > 2147483644) {
                this.mPriority = 2147483644;
            } else {
                this.mPriority = (int) j2;
            }
        }
    }

    public void clearPendingBind() {
        this.mPendingBind = false;
    }

    public int getBindPriority() {
        return this.mPriority;
    }

    public IQSTileService getTileService() {
        return this.mStateManager;
    }

    public IBinder getToken() {
        return this.mStateManager.getToken();
    }

    public void handleDestroy() {
        setBindAllowed(false);
        this.mServices.getContext().unregisterReceiver(this.mUninstallReceiver);
        this.mStateManager.handleDestroy();
    }

    public boolean hasPendingBind() {
        return this.mPendingBind;
    }

    public boolean isActiveTile() {
        return this.mStateManager.isActiveTile();
    }

    public boolean isLifecycleStarted() {
        return this.mStarted;
    }

    public boolean isToggleableTile() {
        return this.mStateManager.isToggleableTile();
    }

    public void setBindAllowed(boolean z) {
        if (this.mBindAllowed == z) {
            return;
        }
        this.mBindAllowed = z;
        if (!z && this.mBound) {
            unbindService();
        } else if (z && this.mBindRequested && !this.mBound) {
            bindService();
        }
    }

    public void setBindRequested(boolean z) {
        if (this.mBindRequested == z) {
            return;
        }
        this.mBindRequested = z;
        if (this.mBindAllowed && z && !this.mBound) {
            this.mHandler.removeCallbacks(this.mUnbind);
            bindService();
        } else {
            this.mServices.recalculateBindAllowance();
        }
        if (!this.mBound || this.mBindRequested) {
            return;
        }
        this.mHandler.postDelayed(this.mUnbind, 30000L);
    }

    public void setLastUpdate(long j) {
        this.mLastUpdate = j;
        if (this.mBound && isActiveTile()) {
            this.mStateManager.onStopListening();
            setBindRequested(false);
        }
        this.mServices.recalculateBindAllowance();
    }

    public void setShowingDialog(boolean z) {
        this.mShowingDialog = z;
    }

    public void setTileChangeListener(TileLifecycleManager.TileChangeListener tileChangeListener) {
        this.mStateManager.setTileChangeListener(tileChangeListener);
    }

    public void startLifecycleManagerAndAddTile() {
        this.mStarted = true;
        ComponentName component = this.mStateManager.getComponent();
        int userId = this.mStateManager.getUserId();
        if (this.mServices.getHost().isTileAdded(component, userId)) {
            return;
        }
        this.mServices.getHost().setTileAdded(component, userId, true);
        this.mStateManager.onTileAdded();
        this.mStateManager.flushMessagesAndUnbind();
    }

    public final void unbindService() {
        if (!this.mBound) {
            Log.e("TileServiceManager", "Service not bound");
            return;
        }
        this.mBound = false;
        this.mJustBound = false;
        this.mStateManager.setBindService(false);
    }
}