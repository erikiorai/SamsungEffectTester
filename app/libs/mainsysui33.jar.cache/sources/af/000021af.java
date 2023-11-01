package com.android.systemui.qs.external;

import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.metrics.LogMaker;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.service.quicksettings.IQSTileService;
import android.service.quicksettings.Tile;
import android.util.Log;
import android.view.IWindowManager;
import android.view.View;
import android.view.WindowManagerGlobal;
import android.widget.Switch;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.logging.MetricsLogger;
import com.android.systemui.animation.ActivityLaunchAnimator;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.qs.QSHost;
import com.android.systemui.qs.external.TileLifecycleManager;
import com.android.systemui.qs.logging.QSLogger;
import com.android.systemui.qs.tileimpl.QSTileImpl;
import dagger.Lazy;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/external/CustomTile.class */
public class CustomTile extends QSTileImpl<QSTile.State> implements TileLifecycleManager.TileChangeListener {
    public final ComponentName mComponent;
    public final CustomTileStatePersister mCustomTileStatePersister;
    public Icon mDefaultIcon;
    public CharSequence mDefaultLabel;
    public final AtomicBoolean mInitialDefaultIconFetched;
    public boolean mIsShowingDialog;
    public boolean mIsTokenGranted;
    public final TileServiceKey mKey;
    public boolean mListening;
    public final IQSTileService mService;
    public final TileServiceManager mServiceManager;
    public final Tile mTile;
    public final TileServices mTileServices;
    public final IBinder mToken;
    public final int mUser;
    public final Context mUserContext;
    public View mViewClicked;
    public final IWindowManager mWindowManager;

    /* loaded from: mainsysui33.jar:com/android/systemui/qs/external/CustomTile$Builder.class */
    public static class Builder {
        public final ActivityStarter mActivityStarter;
        public final Looper mBackgroundLooper;
        public final CustomTileStatePersister mCustomTileStatePersister;
        public final FalsingManager mFalsingManager;
        public final Handler mMainHandler;
        public final MetricsLogger mMetricsLogger;
        public final Lazy<QSHost> mQSHostLazy;
        public final QSLogger mQSLogger;
        public String mSpec = "";
        public final StatusBarStateController mStatusBarStateController;
        public TileServices mTileServices;
        public Context mUserContext;

        public Builder(Lazy<QSHost> lazy, Looper looper, Handler handler, FalsingManager falsingManager, MetricsLogger metricsLogger, StatusBarStateController statusBarStateController, ActivityStarter activityStarter, QSLogger qSLogger, CustomTileStatePersister customTileStatePersister, TileServices tileServices) {
            this.mQSHostLazy = lazy;
            this.mBackgroundLooper = looper;
            this.mMainHandler = handler;
            this.mFalsingManager = falsingManager;
            this.mMetricsLogger = metricsLogger;
            this.mStatusBarStateController = statusBarStateController;
            this.mActivityStarter = activityStarter;
            this.mQSLogger = qSLogger;
            this.mCustomTileStatePersister = customTileStatePersister;
            this.mTileServices = tileServices;
        }

        @VisibleForTesting
        public CustomTile build() {
            if (this.mUserContext != null) {
                return new CustomTile((QSHost) this.mQSHostLazy.get(), this.mBackgroundLooper, this.mMainHandler, this.mFalsingManager, this.mMetricsLogger, this.mStatusBarStateController, this.mActivityStarter, this.mQSLogger, CustomTile.getAction(this.mSpec), this.mUserContext, this.mCustomTileStatePersister, this.mTileServices, null);
            }
            throw new NullPointerException("UserContext cannot be null");
        }

        public Builder setSpec(String str) {
            this.mSpec = str;
            return this;
        }

        public Builder setUserContext(Context context) {
            this.mUserContext = context;
            return this;
        }
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.external.CustomTile$$ExternalSyntheticLambda3.run():void] */
    public static /* synthetic */ void $r8$lambda$FIR9VEqO_6S3KWkyqvXoVAmYACc(CustomTile customTile) {
        customTile.lambda$startUnlockAndRun$2();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.external.CustomTile$$ExternalSyntheticLambda1.get():java.lang.Object] */
    /* renamed from: $r8$lambda$ZTDfjP1xh9GHxMeHu-b5RURberY */
    public static /* synthetic */ QSTile.Icon m3890$r8$lambda$ZTDfjP1xh9GHxMeHub5RURberY(Drawable drawable) {
        return lambda$handleUpdateState$1(drawable);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.external.CustomTile$$ExternalSyntheticLambda4.run():void] */
    public static /* synthetic */ void $r8$lambda$e96RI3otxG8H9BNZ_p8bYjXCTDI(CustomTile customTile) {
        customTile.updateDefaultTileAndIcon();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.external.CustomTile$$ExternalSyntheticLambda2.run():void] */
    public static /* synthetic */ void $r8$lambda$uSeMgrq_cUGH8kI4PB165tLhWPE(CustomTile customTile, Tile tile) {
        customTile.lambda$updateTileState$0(tile);
    }

    public CustomTile(QSHost qSHost, Looper looper, Handler handler, FalsingManager falsingManager, MetricsLogger metricsLogger, StatusBarStateController statusBarStateController, ActivityStarter activityStarter, QSLogger qSLogger, String str, Context context, CustomTileStatePersister customTileStatePersister, TileServices tileServices) {
        super(qSHost, looper, handler, falsingManager, metricsLogger, statusBarStateController, activityStarter, qSLogger);
        this.mToken = new Binder();
        this.mInitialDefaultIconFetched = new AtomicBoolean(false);
        this.mTileServices = tileServices;
        this.mWindowManager = WindowManagerGlobal.getWindowManagerService();
        ComponentName unflattenFromString = ComponentName.unflattenFromString(str);
        this.mComponent = unflattenFromString;
        this.mTile = new Tile();
        this.mUserContext = context;
        int userId = context.getUserId();
        this.mUser = userId;
        this.mKey = new TileServiceKey(unflattenFromString, userId);
        TileServiceManager tileWrapper = tileServices.getTileWrapper(this);
        this.mServiceManager = tileWrapper;
        this.mService = tileWrapper.getTileService();
        this.mCustomTileStatePersister = customTileStatePersister;
    }

    public /* synthetic */ CustomTile(QSHost qSHost, Looper looper, Handler handler, FalsingManager falsingManager, MetricsLogger metricsLogger, StatusBarStateController statusBarStateController, ActivityStarter activityStarter, QSLogger qSLogger, String str, Context context, CustomTileStatePersister customTileStatePersister, TileServices tileServices, CustomTile-IA r27) {
        this(qSHost, looper, handler, falsingManager, metricsLogger, statusBarStateController, activityStarter, qSLogger, str, context, customTileStatePersister, tileServices);
    }

    public static CustomTile create(Builder builder, String str, Context context) {
        return builder.setSpec(str).setUserContext(context).build();
    }

    public static String getAction(String str) {
        if (str != null && str.startsWith("custom(") && str.endsWith(")")) {
            String substring = str.substring(7, str.length() - 1);
            if (substring.isEmpty()) {
                throw new IllegalArgumentException("Empty custom tile spec action");
            }
            return substring;
        }
        throw new IllegalArgumentException("Bad custom tile spec: " + str);
    }

    public static ComponentName getComponentFromSpec(String str) {
        String substring = str.substring(7, str.length() - 1);
        if (substring.isEmpty()) {
            throw new IllegalArgumentException("Empty custom tile spec action");
        }
        return ComponentName.unflattenFromString(substring);
    }

    public static /* synthetic */ QSTile.Icon lambda$handleUpdateState$1(Drawable drawable) {
        if (drawable == null) {
            return null;
        }
        Drawable.ConstantState constantState = drawable.getConstantState();
        QSTileImpl.DrawableIcon drawableIcon = null;
        if (constantState != null) {
            drawableIcon = new QSTileImpl.DrawableIcon(constantState.newDrawable());
        }
        return drawableIcon;
    }

    public /* synthetic */ void lambda$startActivityAndCollapse$3(PendingIntent pendingIntent, ActivityLaunchAnimator.Controller controller) {
        this.mActivityStarter.startPendingIntentDismissingKeyguard(pendingIntent, (Runnable) null, controller);
    }

    public /* synthetic */ void lambda$startUnlockAndRun$2() {
        try {
            this.mService.onUnlockComplete();
        } catch (RemoteException e) {
        }
    }

    public static String toSpec(ComponentName componentName) {
        return "custom(" + componentName.flattenToShortString() + ")";
    }

    public final void applyTileState(Tile tile, boolean z) {
        if (tile.getIcon() != null || z) {
            this.mTile.setIcon(tile.getIcon());
        }
        if (tile.getLabel() != null || z) {
            this.mTile.setLabel(tile.getLabel());
        }
        if (tile.getSubtitle() != null || z) {
            this.mTile.setSubtitle(tile.getSubtitle());
        }
        if (tile.getContentDescription() != null || z) {
            this.mTile.setContentDescription(tile.getContentDescription());
        }
        if (tile.getStateDescription() != null || z) {
            this.mTile.setStateDescription(tile.getStateDescription());
        }
        this.mTile.setActivityLaunchForClick(tile.getActivityLaunchForClick());
        this.mTile.setState(tile.getState());
    }

    public ComponentName getComponent() {
        return this.mComponent;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public Intent getLongClickIntent() {
        Intent intent = new Intent("android.service.quicksettings.action.QS_TILE_PREFERENCES");
        intent.setPackage(this.mComponent.getPackageName());
        Intent resolveIntent = resolveIntent(intent);
        if (resolveIntent != null) {
            resolveIntent.putExtra("android.intent.extra.COMPONENT_NAME", this.mComponent);
            resolveIntent.putExtra("state", this.mTile.getState());
            return resolveIntent;
        }
        return new Intent("android.settings.APPLICATION_DETAILS_SETTINGS").setData(Uri.fromParts("package", this.mComponent.getPackageName(), null));
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public int getMetricsCategory() {
        return 268;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public final String getMetricsSpec() {
        return this.mComponent.getPackageName();
    }

    public Tile getQsTile() {
        updateDefaultTileAndIcon();
        return this.mTile;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public long getStaleTimeout() {
        return (this.mHost.indexOf(getTileSpec()) * 60000) + 3600000;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public CharSequence getTileLabel() {
        return getState().label;
    }

    public int getUser() {
        return this.mUser;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public void handleClick(View view) {
        if (this.mTile.getState() == 0) {
            return;
        }
        this.mViewClicked = view;
        try {
            this.mWindowManager.addWindowToken(this.mToken, 2035, 0, (Bundle) null);
            this.mIsTokenGranted = true;
        } catch (RemoteException e) {
        }
        try {
            if (this.mServiceManager.isActiveTile()) {
                this.mServiceManager.setBindRequested(true);
                this.mService.onStartListening();
            }
            if (this.mTile.getActivityLaunchForClick() != null) {
                startActivityAndCollapse(this.mTile.getActivityLaunchForClick());
            } else {
                this.mService.onClick(this.mToken);
            }
        } catch (RemoteException e2) {
        }
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public void handleDestroy() {
        super.handleDestroy();
        if (this.mIsTokenGranted) {
            try {
                this.mWindowManager.removeWindowToken(this.mToken, 0);
            } catch (RemoteException e) {
            }
        }
        this.mTileServices.freeService(this, this.mServiceManager);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public void handleInitialize() {
        Tile readState;
        updateDefaultTileAndIcon();
        if (this.mInitialDefaultIconFetched.compareAndSet(false, true) && this.mDefaultIcon == null) {
            String str = this.TAG;
            Log.w(str, "No default icon for " + getTileSpec() + ", destroying tile");
            this.mHost.removeTile(getTileSpec());
        }
        if (this.mServiceManager.isToggleableTile()) {
            resetStates();
        }
        this.mServiceManager.setTileChangeListener(this);
        if (!this.mServiceManager.isActiveTile() || (readState = this.mCustomTileStatePersister.readState(this.mKey)) == null) {
            return;
        }
        applyTileState(readState, false);
        this.mServiceManager.clearPendingBind();
        refreshState();
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public void handleSetListening(boolean z) {
        super.handleSetListening(z);
        if (this.mListening == z) {
            return;
        }
        this.mListening = z;
        try {
            if (z) {
                updateDefaultTileAndIcon();
                refreshState();
                if (this.mServiceManager.isActiveTile()) {
                    return;
                }
                this.mServiceManager.setBindRequested(true);
                this.mService.onStartListening();
                return;
            }
            this.mViewClicked = null;
            this.mService.onStopListening();
            if (this.mIsTokenGranted && !this.mIsShowingDialog) {
                try {
                    this.mWindowManager.removeWindowToken(this.mToken, 0);
                } catch (RemoteException e) {
                }
                this.mIsTokenGranted = false;
            }
            this.mIsShowingDialog = false;
            this.mServiceManager.setBindRequested(false);
        } catch (RemoteException e2) {
        }
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public void handleUpdateState(QSTile.State state, Object obj) {
        Drawable loadDrawable;
        int state2 = this.mTile.getState();
        boolean z = false;
        if (this.mServiceManager.hasPendingBind()) {
            state2 = 0;
        }
        state.state = state2;
        try {
            loadDrawable = this.mTile.getIcon().loadDrawable(this.mUserContext);
        } catch (Exception e) {
            Log.w(this.TAG, "Invalid icon, forcing into unavailable state");
            state.state = 0;
            loadDrawable = this.mDefaultIcon.loadDrawable(this.mUserContext);
        }
        final Drawable drawable = loadDrawable;
        state.iconSupplier = new Supplier() { // from class: com.android.systemui.qs.external.CustomTile$$ExternalSyntheticLambda1
            @Override // java.util.function.Supplier
            public final Object get() {
                return CustomTile.m3890$r8$lambda$ZTDfjP1xh9GHxMeHub5RURberY(drawable);
            }
        };
        state.label = this.mTile.getLabel();
        CharSequence subtitle = this.mTile.getSubtitle();
        if (subtitle == null || subtitle.length() <= 0) {
            state.secondaryLabel = null;
        } else {
            state.secondaryLabel = subtitle;
        }
        if (this.mTile.getContentDescription() != null) {
            state.contentDescription = this.mTile.getContentDescription();
        } else {
            state.contentDescription = state.label;
        }
        if (this.mTile.getStateDescription() != null) {
            state.stateDescription = this.mTile.getStateDescription();
        } else {
            state.stateDescription = null;
        }
        if (state instanceof QSTile.BooleanState) {
            state.expandedAccessibilityClassName = Switch.class.getName();
            QSTile.BooleanState booleanState = (QSTile.BooleanState) state;
            if (state.state == 2) {
                z = true;
            }
            booleanState.value = z;
        }
    }

    /* renamed from: handleUpdateTileState */
    public final void lambda$updateTileState$0(Tile tile) {
        applyTileState(tile, true);
        if (this.mServiceManager.isActiveTile()) {
            this.mCustomTileStatePersister.persistState(this.mKey, tile);
        }
    }

    public final boolean iconEquals(Icon icon, Icon icon2) {
        if (icon == icon2) {
            return true;
        }
        return icon != null && icon2 != null && icon.getType() == 2 && icon2.getType() == 2 && icon.getResId() == icon2.getResId() && Objects.equals(icon.getResPackage(), icon2.getResPackage());
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public boolean isAvailable() {
        boolean z = true;
        if (this.mInitialDefaultIconFetched.get()) {
            z = this.mDefaultIcon != null;
        }
        return z;
    }

    public final boolean isSystemApp(PackageManager packageManager) throws PackageManager.NameNotFoundException {
        return packageManager.getApplicationInfo(this.mComponent.getPackageName(), 0).isSystemApp();
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public QSTile.State newTileState() {
        TileServiceManager tileServiceManager = this.mServiceManager;
        return (tileServiceManager == null || !tileServiceManager.isToggleableTile()) ? new QSTile.State() : new QSTile.BooleanState();
    }

    public void onDialogHidden() {
        this.mIsShowingDialog = false;
        try {
            this.mWindowManager.removeWindowToken(this.mToken, 0);
        } catch (RemoteException e) {
        }
    }

    public void onDialogShown() {
        this.mIsShowingDialog = true;
    }

    @Override // com.android.systemui.qs.external.TileLifecycleManager.TileChangeListener
    public void onTileChanged(ComponentName componentName) {
        this.mHandler.post(new Runnable() { // from class: com.android.systemui.qs.external.CustomTile$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                CustomTile.$r8$lambda$e96RI3otxG8H9BNZ_p8bYjXCTDI(CustomTile.this);
            }
        });
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public LogMaker populate(LogMaker logMaker) {
        return super.populate(logMaker).setComponentName(this.mComponent);
    }

    public final Intent resolveIntent(Intent intent) {
        Intent intent2;
        ResolveInfo resolveActivityAsUser = this.mContext.getPackageManager().resolveActivityAsUser(intent, 0, this.mUser);
        if (resolveActivityAsUser != null) {
            Intent intent3 = new Intent("android.service.quicksettings.action.QS_TILE_PREFERENCES");
            ActivityInfo activityInfo = resolveActivityAsUser.activityInfo;
            intent2 = intent3.setClassName(activityInfo.packageName, activityInfo.name);
        } else {
            intent2 = null;
        }
        return intent2;
    }

    public void startActivityAndCollapse(final PendingIntent pendingIntent) {
        if (!pendingIntent.isActivity()) {
            Log.i(this.TAG, "Intent not for activity.");
        } else if (!this.mIsTokenGranted) {
            Log.i(this.TAG, "Launching activity before click");
        } else {
            Log.i(this.TAG, "The activity is starting");
            View view = this.mViewClicked;
            final ActivityLaunchAnimator.Controller fromView = view == null ? null : ActivityLaunchAnimator.Controller.fromView(view, 0);
            this.mUiHandler.post(new Runnable() { // from class: com.android.systemui.qs.external.CustomTile$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    CustomTile.this.lambda$startActivityAndCollapse$3(pendingIntent, fromView);
                }
            });
        }
    }

    public void startUnlockAndRun() {
        this.mActivityStarter.postQSRunnableDismissingKeyguard(new Runnable() { // from class: com.android.systemui.qs.external.CustomTile$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                CustomTile.$r8$lambda$FIR9VEqO_6S3KWkyqvXoVAmYACc(CustomTile.this);
            }
        });
    }

    /* JADX WARN: Code restructure failed: missing block: B:81:0x00ab, code lost:
        if (android.text.TextUtils.equals(r4.mTile.getLabel(), r4.mDefaultLabel) != false) goto L39;
     */
    /* JADX WARN: Removed duplicated region for block: B:70:0x0069 A[Catch: NameNotFoundException -> 0x00cd, TRY_ENTER, TRY_LEAVE, TryCatch #0 {NameNotFoundException -> 0x00cd, blocks: (B:49:0x0000, B:51:0x000c, B:55:0x0018, B:58:0x002e, B:60:0x0037, B:63:0x0047, B:70:0x0069, B:72:0x007c, B:74:0x0087, B:76:0x0091, B:80:0x009d, B:84:0x00b0, B:86:0x00c1), top: B:90:0x0000 }] */
    /* JADX WARN: Removed duplicated region for block: B:71:0x0079  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x0087 A[Catch: NameNotFoundException -> 0x00cd, TRY_ENTER, TryCatch #0 {NameNotFoundException -> 0x00cd, blocks: (B:49:0x0000, B:51:0x000c, B:55:0x0018, B:58:0x002e, B:60:0x0037, B:63:0x0047, B:70:0x0069, B:72:0x007c, B:74:0x0087, B:76:0x0091, B:80:0x009d, B:84:0x00b0, B:86:0x00c1), top: B:90:0x0000 }] */
    /* JADX WARN: Removed duplicated region for block: B:78:0x009a  */
    /* JADX WARN: Removed duplicated region for block: B:86:0x00c1 A[Catch: NameNotFoundException -> 0x00cd, TRY_ENTER, TRY_LEAVE, TryCatch #0 {NameNotFoundException -> 0x00cd, blocks: (B:49:0x0000, B:51:0x000c, B:55:0x0018, B:58:0x002e, B:60:0x0037, B:63:0x0047, B:70:0x0069, B:72:0x007c, B:74:0x0087, B:76:0x0091, B:80:0x009d, B:84:0x00b0, B:86:0x00c1), top: B:90:0x0000 }] */
    /* JADX WARN: Removed duplicated region for block: B:92:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updateDefaultTileAndIcon() {
        boolean z;
        boolean z2;
        try {
            PackageManager packageManager = this.mUserContext.getPackageManager();
            int i = 786432;
            if (isSystemApp(packageManager)) {
                i = 786944;
            }
            ServiceInfo serviceInfo = packageManager.getServiceInfo(this.mComponent, i);
            int i2 = serviceInfo.icon;
            if (i2 == 0) {
                i2 = serviceInfo.applicationInfo.icon;
            }
            if (this.mTile.getIcon() != null && !iconEquals(this.mTile.getIcon(), this.mDefaultIcon)) {
                z = false;
                Icon createWithResource = i2 == 0 ? Icon.createWithResource(this.mComponent.getPackageName(), i2) : null;
                this.mDefaultIcon = createWithResource;
                if (z) {
                    this.mTile.setIcon(createWithResource);
                }
                if (this.mTile.getLabel() != null) {
                    z2 = false;
                }
                z2 = true;
                CharSequence loadLabel = serviceInfo.loadLabel(packageManager);
                this.mDefaultLabel = loadLabel;
                if (z2) {
                    return;
                }
                this.mTile.setLabel(loadLabel);
                return;
            }
            z = true;
            if (i2 == 0) {
            }
            this.mDefaultIcon = createWithResource;
            if (z) {
            }
            if (this.mTile.getLabel() != null) {
            }
            z2 = true;
            CharSequence loadLabel2 = serviceInfo.loadLabel(packageManager);
            this.mDefaultLabel = loadLabel2;
            if (z2) {
            }
        } catch (PackageManager.NameNotFoundException e) {
            this.mDefaultIcon = null;
            this.mDefaultLabel = null;
        }
    }

    public void updateTileState(final Tile tile) {
        this.mHandler.post(new Runnable() { // from class: com.android.systemui.qs.external.CustomTile$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                CustomTile.$r8$lambda$uSeMgrq_cUGH8kI4PB165tLhWPE(CustomTile.this, tile);
            }
        });
    }
}