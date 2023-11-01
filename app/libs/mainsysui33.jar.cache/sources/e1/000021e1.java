package com.android.systemui.qs.external;

import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.Icon;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.UserHandle;
import android.service.quicksettings.IQSService;
import android.service.quicksettings.Tile;
import android.util.ArrayMap;
import android.util.Log;
import com.android.internal.statusbar.StatusBarIcon;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.qs.QSTileHost;
import com.android.systemui.qs.external.TileServices;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.phone.StatusBarIconController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Objects;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/external/TileServices.class */
public class TileServices extends IQSService.Stub {
    public static final Comparator<TileServiceManager> SERVICE_SORT = new Comparator<TileServiceManager>() { // from class: com.android.systemui.qs.external.TileServices.3
        @Override // java.util.Comparator
        public int compare(TileServiceManager tileServiceManager, TileServiceManager tileServiceManager2) {
            return -Integer.compare(tileServiceManager.getBindPriority(), tileServiceManager2.getBindPriority());
        }
    };
    public final BroadcastDispatcher mBroadcastDispatcher;
    public final CommandQueue mCommandQueue;
    public final Context mContext;
    public final Provider<Handler> mHandlerProvider;
    public final QSTileHost mHost;
    public final KeyguardStateController mKeyguardStateController;
    public final Handler mMainHandler;
    public final CommandQueue.Callbacks mRequestListeningCallback;
    public final UserTracker mUserTracker;
    public final ArrayMap<CustomTile, TileServiceManager> mServices = new ArrayMap<>();
    public final ArrayMap<ComponentName, CustomTile> mTiles = new ArrayMap<>();
    public final ArrayMap<IBinder, CustomTile> mTokenMap = new ArrayMap<>();
    public int mMaxBound = 3;

    /* renamed from: com.android.systemui.qs.external.TileServices$2 */
    /* loaded from: mainsysui33.jar:com/android/systemui/qs/external/TileServices$2.class */
    public class AnonymousClass2 implements CommandQueue.Callbacks {
        /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.external.TileServices$2$$ExternalSyntheticLambda0.run():void] */
        /* renamed from: $r8$lambda$VDikcK9O69g-3kgpwg0Lce7VjOo */
        public static /* synthetic */ void m3917$r8$lambda$VDikcK9O69g3kgpwg0Lce7VjOo(AnonymousClass2 anonymousClass2, ComponentName componentName) {
            anonymousClass2.lambda$requestTileServiceListeningState$0(componentName);
        }

        public AnonymousClass2() {
            TileServices.this = r4;
        }

        public /* synthetic */ void lambda$requestTileServiceListeningState$0(ComponentName componentName) {
            TileServices.this.requestListening(componentName);
        }

        public void requestTileServiceListeningState(final ComponentName componentName) {
            TileServices.this.mMainHandler.post(new Runnable() { // from class: com.android.systemui.qs.external.TileServices$2$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    TileServices.AnonymousClass2.m3917$r8$lambda$VDikcK9O69g3kgpwg0Lce7VjOo(TileServices.AnonymousClass2.this, componentName);
                }
            });
        }
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.external.TileServices$$ExternalSyntheticLambda0.run():void] */
    /* renamed from: $r8$lambda$wP-4ifFvEEj64D7SS0UC0dYc2Y8 */
    public static /* synthetic */ void m3912$r8$lambda$wP4ifFvEEj64D7SS0UC0dYc2Y8(TileServices tileServices, String str) {
        tileServices.lambda$freeService$0(str);
    }

    public TileServices(QSTileHost qSTileHost, Provider<Handler> provider, BroadcastDispatcher broadcastDispatcher, UserTracker userTracker, KeyguardStateController keyguardStateController, CommandQueue commandQueue) {
        AnonymousClass2 anonymousClass2 = new AnonymousClass2();
        this.mRequestListeningCallback = anonymousClass2;
        this.mHost = qSTileHost;
        this.mKeyguardStateController = keyguardStateController;
        this.mContext = qSTileHost.getContext();
        this.mBroadcastDispatcher = broadcastDispatcher;
        this.mHandlerProvider = provider;
        this.mMainHandler = (Handler) provider.get();
        this.mUserTracker = userTracker;
        this.mCommandQueue = commandQueue;
        commandQueue.addCallback(anonymousClass2);
    }

    public /* synthetic */ void lambda$freeService$0(String str) {
        this.mHost.getIconController().removeAllIconsForExternalSlot(str);
    }

    public void freeService(CustomTile customTile, TileServiceManager tileServiceManager) {
        synchronized (this.mServices) {
            tileServiceManager.setBindAllowed(false);
            tileServiceManager.handleDestroy();
            this.mServices.remove(customTile);
            this.mTokenMap.remove(tileServiceManager.getToken());
            this.mTiles.remove(customTile.getComponent());
            final String className = customTile.getComponent().getClassName();
            this.mMainHandler.post(new Runnable() { // from class: com.android.systemui.qs.external.TileServices$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    TileServices.m3912$r8$lambda$wP4ifFvEEj64D7SS0UC0dYc2Y8(TileServices.this, className);
                }
            });
        }
    }

    public Context getContext() {
        return this.mContext;
    }

    public QSTileHost getHost() {
        return this.mHost;
    }

    public Tile getTile(IBinder iBinder) {
        CustomTile tileForToken = getTileForToken(iBinder);
        if (tileForToken != null) {
            verifyCaller(tileForToken);
            return tileForToken.getQsTile();
        }
        return null;
    }

    public final CustomTile getTileForComponent(ComponentName componentName) {
        CustomTile customTile;
        synchronized (this.mServices) {
            customTile = this.mTiles.get(componentName);
        }
        return customTile;
    }

    public CustomTile getTileForToken(IBinder iBinder) {
        CustomTile customTile;
        synchronized (this.mServices) {
            customTile = this.mTokenMap.get(iBinder);
        }
        return customTile;
    }

    public TileServiceManager getTileWrapper(CustomTile customTile) {
        ComponentName component = customTile.getComponent();
        TileServiceManager onCreateTileService = onCreateTileService(component, this.mBroadcastDispatcher);
        synchronized (this.mServices) {
            this.mServices.put(customTile, onCreateTileService);
            this.mTiles.put(component, customTile);
            this.mTokenMap.put(onCreateTileService.getToken(), customTile);
        }
        onCreateTileService.startLifecycleManagerAndAddTile();
        return onCreateTileService;
    }

    public boolean isLocked() {
        return this.mKeyguardStateController.isShowing();
    }

    public boolean isSecure() {
        return this.mKeyguardStateController.isMethodSecure() && this.mKeyguardStateController.isShowing();
    }

    public TileServiceManager onCreateTileService(ComponentName componentName, BroadcastDispatcher broadcastDispatcher) {
        return new TileServiceManager(this, (Handler) this.mHandlerProvider.get(), componentName, broadcastDispatcher, this.mUserTracker);
    }

    public void onDialogHidden(IBinder iBinder) {
        CustomTile tileForToken = getTileForToken(iBinder);
        if (tileForToken != null) {
            verifyCaller(tileForToken);
            TileServiceManager tileServiceManager = this.mServices.get(tileForToken);
            Objects.requireNonNull(tileServiceManager);
            tileServiceManager.setShowingDialog(false);
            tileForToken.onDialogHidden();
        }
    }

    public void onShowDialog(IBinder iBinder) {
        CustomTile tileForToken = getTileForToken(iBinder);
        if (tileForToken != null) {
            verifyCaller(tileForToken);
            tileForToken.onDialogShown();
            this.mHost.forceCollapsePanels();
            TileServiceManager tileServiceManager = this.mServices.get(tileForToken);
            Objects.requireNonNull(tileServiceManager);
            tileServiceManager.setShowingDialog(true);
        }
    }

    public void onStartActivity(IBinder iBinder) {
        CustomTile tileForToken = getTileForToken(iBinder);
        if (tileForToken != null) {
            verifyCaller(tileForToken);
            this.mHost.forceCollapsePanels();
        }
    }

    public void onStartSuccessful(IBinder iBinder) {
        CustomTile tileForToken = getTileForToken(iBinder);
        if (tileForToken != null) {
            verifyCaller(tileForToken);
            synchronized (this.mServices) {
                TileServiceManager tileServiceManager = this.mServices.get(tileForToken);
                if (tileServiceManager != null && tileServiceManager.isLifecycleStarted()) {
                    tileServiceManager.clearPendingBind();
                    tileForToken.refreshState();
                    return;
                }
                Log.e("TileServices", "TileServiceManager not started for " + tileForToken.getComponent(), new IllegalStateException());
            }
        }
    }

    public void recalculateBindAllowance() {
        ArrayList arrayList;
        int i;
        synchronized (this.mServices) {
            arrayList = new ArrayList(this.mServices.values());
        }
        int size = arrayList.size();
        if (size > this.mMaxBound) {
            long currentTimeMillis = System.currentTimeMillis();
            for (int i2 = 0; i2 < size; i2++) {
                ((TileServiceManager) arrayList.get(i2)).calculateBindPriority(currentTimeMillis);
            }
            Collections.sort(arrayList, SERVICE_SORT);
        }
        int i3 = 0;
        while (true) {
            i = i3;
            if (i3 >= this.mMaxBound) {
                break;
            }
            i = i3;
            if (i3 >= size) {
                break;
            }
            ((TileServiceManager) arrayList.get(i3)).setBindAllowed(true);
            i3++;
        }
        while (i < size) {
            ((TileServiceManager) arrayList.get(i)).setBindAllowed(false);
            i++;
        }
    }

    public final void requestListening(ComponentName componentName) {
        synchronized (this.mServices) {
            CustomTile tileForComponent = getTileForComponent(componentName);
            if (tileForComponent == null) {
                Log.d("TileServices", "Couldn't find tile for " + componentName);
                return;
            }
            TileServiceManager tileServiceManager = this.mServices.get(tileForComponent);
            if (tileServiceManager == null) {
                Log.e("TileServices", "No TileServiceManager found in requestListening for tile " + tileForComponent.getTileSpec());
            } else if (tileServiceManager.isActiveTile()) {
                tileServiceManager.setBindRequested(true);
                try {
                    tileServiceManager.getTileService().onStartListening();
                } catch (RemoteException e) {
                }
            }
        }
    }

    public void startActivity(IBinder iBinder, PendingIntent pendingIntent) {
        startActivity(getTileForToken(iBinder), pendingIntent);
    }

    public void startActivity(CustomTile customTile, PendingIntent pendingIntent) {
        if (customTile != null) {
            verifyCaller(customTile);
            customTile.startActivityAndCollapse(pendingIntent);
        }
    }

    public void startUnlockAndRun(IBinder iBinder) {
        CustomTile tileForToken = getTileForToken(iBinder);
        if (tileForToken != null) {
            verifyCaller(tileForToken);
            tileForToken.startUnlockAndRun();
        }
    }

    public void updateQsTile(Tile tile, IBinder iBinder) {
        CustomTile tileForToken = getTileForToken(iBinder);
        if (tileForToken != null) {
            verifyCaller(tileForToken);
            synchronized (this.mServices) {
                TileServiceManager tileServiceManager = this.mServices.get(tileForToken);
                if (tileServiceManager != null && tileServiceManager.isLifecycleStarted()) {
                    tileServiceManager.clearPendingBind();
                    tileServiceManager.setLastUpdate(System.currentTimeMillis());
                    tileForToken.updateTileState(tile);
                    tileForToken.refreshState();
                    return;
                }
                Log.e("TileServices", "TileServiceManager not started for " + tileForToken.getComponent(), new IllegalStateException());
            }
        }
    }

    public void updateStatusIcon(IBinder iBinder, Icon icon, String str) {
        CustomTile tileForToken = getTileForToken(iBinder);
        if (tileForToken != null) {
            verifyCaller(tileForToken);
            try {
                final ComponentName component = tileForToken.getComponent();
                String packageName = component.getPackageName();
                UserHandle callingUserHandle = IQSService.Stub.getCallingUserHandle();
                if (this.mContext.getPackageManager().getPackageInfoAsUser(packageName, 0, callingUserHandle.getIdentifier()).applicationInfo.isSystemApp()) {
                    final StatusBarIcon statusBarIcon = icon != null ? new StatusBarIcon(callingUserHandle, packageName, icon, 0, 0, str) : null;
                    this.mMainHandler.post(new Runnable() { // from class: com.android.systemui.qs.external.TileServices.1
                        {
                            TileServices.this = this;
                        }

                        @Override // java.lang.Runnable
                        public void run() {
                            StatusBarIconController iconController = TileServices.this.mHost.getIconController();
                            iconController.setIcon(component.getClassName(), statusBarIcon);
                            iconController.setExternalIcon(component.getClassName());
                        }
                    });
                }
            } catch (PackageManager.NameNotFoundException e) {
            }
        }
    }

    public final void verifyCaller(CustomTile customTile) {
        try {
            if (Binder.getCallingUid() == this.mContext.getPackageManager().getPackageUidAsUser(customTile.getComponent().getPackageName(), Binder.getCallingUserHandle().getIdentifier())) {
                return;
            }
            throw new SecurityException("Component outside caller's uid");
        } catch (PackageManager.NameNotFoundException e) {
            throw new SecurityException(e);
        }
    }
}