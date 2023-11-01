package com.android.systemui.qs.tileimpl;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.metrics.LogMaker;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.ArraySet;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.logging.InstanceId;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.logging.UiEventLogger;
import com.android.settingslib.RestrictedLockUtils;
import com.android.settingslib.RestrictedLockUtilsInternal;
import com.android.systemui.Dumpable;
import com.android.systemui.animation.ActivityLaunchAnimator;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.qs.QSIconView;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.plugins.qs.QSTile.State;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.qs.QSEvent;
import com.android.systemui.qs.QSHost;
import com.android.systemui.qs.SideLabelTileLayout;
import com.android.systemui.qs.logging.QSLogger;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/tileimpl/QSTileImpl.class */
public abstract class QSTileImpl<TState extends QSTile.State> implements QSTile, LifecycleOwner, Dumpable {
    private static final long DEFAULT_STALE_TIMEOUT = 600000;
    private static final int READY_STATE_NOT_READY = 0;
    private static final int READY_STATE_READY = 2;
    private static final int READY_STATE_READYING = 1;
    public final ActivityStarter mActivityStarter;
    private boolean mAnnounceNextStateChange;
    public final Context mContext;
    @VisibleForTesting
    public RestrictedLockUtils.EnforcedAdmin mEnforcedAdmin;
    private final FalsingManager mFalsingManager;
    public final QSTileImpl<TState>.H mHandler;
    public final QSHost mHost;
    private final InstanceId mInstanceId;
    private int mIsFullQs;
    private final MetricsLogger mMetricsLogger;
    public final QSLogger mQSLogger;
    private volatile int mReadyState;
    private boolean mShowingDetail;
    public TState mState;
    private final StatusBarStateController mStatusBarStateController;
    private String mTileSpec;
    private TState mTmpState;
    private final UiEventLogger mUiEventLogger;
    public final Handler mUiHandler;
    public static final boolean DEBUG = Log.isLoggable("Tile", 3);
    public static final Object ARG_SHOW_TRANSIENT_ENABLING = new Object();
    public final String TAG = "Tile." + getClass().getSimpleName();
    private final ArraySet<Object> mListeners = new ArraySet<>();
    private int mClickEventId = 0;
    private final ArrayList<QSTile.Callback> mCallbacks = new ArrayList<>();
    private final Object mStaleListener = new Object();
    private final LifecycleRegistry mLifecycle = new LifecycleRegistry(this);

    /* loaded from: mainsysui33.jar:com/android/systemui/qs/tileimpl/QSTileImpl$DrawableIcon.class */
    public static class DrawableIcon extends QSTile.Icon {
        public final Drawable mDrawable;
        public final Drawable mInvisibleDrawable;

        public DrawableIcon(Drawable drawable) {
            this.mDrawable = drawable;
            this.mInvisibleDrawable = drawable.getConstantState().newDrawable();
        }

        @Override // com.android.systemui.plugins.qs.QSTile.Icon
        public Drawable getDrawable(Context context) {
            return this.mDrawable;
        }

        @Override // com.android.systemui.plugins.qs.QSTile.Icon
        public Drawable getInvisibleDrawable(Context context) {
            return this.mInvisibleDrawable;
        }

        @Override // com.android.systemui.plugins.qs.QSTile.Icon
        public String toString() {
            return "DrawableIcon";
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/qs/tileimpl/QSTileImpl$H.class */
    public final class H extends Handler {
        @VisibleForTesting
        public static final int STALE = 11;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        @VisibleForTesting
        public H(Looper looper) {
            super(looper);
            QSTileImpl.this = r4;
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            try {
                int i = message.what;
                boolean z = true;
                if (i == 1) {
                    QSTileImpl.this.handleAddCallback((QSTile.Callback) message.obj);
                } else if (i == 8) {
                    QSTileImpl.this.handleRemoveCallbacks();
                } else if (i == 9) {
                    QSTileImpl.this.handleRemoveCallback((QSTile.Callback) message.obj);
                } else if (i == 2) {
                    QSTileImpl qSTileImpl = QSTileImpl.this;
                    if (qSTileImpl.mState.disabledByPolicy) {
                        QSTileImpl.this.mActivityStarter.postStartActivityDismissingKeyguard(RestrictedLockUtils.getShowAdminSupportDetailsIntent(qSTileImpl.mContext, qSTileImpl.mEnforcedAdmin), 0);
                        return;
                    }
                    qSTileImpl.mQSLogger.logHandleClick(qSTileImpl.mTileSpec, message.arg1);
                    QSTileImpl.this.handleClick((View) message.obj);
                } else if (i == 3) {
                    QSTileImpl qSTileImpl2 = QSTileImpl.this;
                    qSTileImpl2.mQSLogger.logHandleSecondaryClick(qSTileImpl2.mTileSpec, message.arg1);
                    QSTileImpl.this.handleSecondaryClick((View) message.obj);
                } else if (i == 4) {
                    QSTileImpl qSTileImpl3 = QSTileImpl.this;
                    qSTileImpl3.mQSLogger.logHandleLongClick(qSTileImpl3.mTileSpec, message.arg1);
                    QSTileImpl.this.handleLongClick((View) message.obj);
                } else if (i == 5) {
                    QSTileImpl.this.handleRefreshState(message.obj);
                } else if (i == 6) {
                    QSTileImpl.this.handleUserSwitch(message.arg1);
                } else if (i == 7) {
                    QSTileImpl.this.handleDestroy();
                } else if (i == 10) {
                    QSTileImpl qSTileImpl4 = QSTileImpl.this;
                    Object obj = message.obj;
                    if (message.arg1 == 0) {
                        z = false;
                    }
                    qSTileImpl4.handleSetListeningInternal(obj, z);
                } else if (i == 11) {
                    QSTileImpl.this.handleStale();
                } else if (i == 12) {
                    QSTileImpl.this.handleInitialize();
                } else {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Unknown msg: ");
                    sb.append(message.what);
                    throw new IllegalArgumentException(sb.toString());
                }
            } catch (Throwable th) {
                String str = "Error in " + ((String) null);
                Log.w(QSTileImpl.this.TAG, str, th);
                QSTileImpl.this.mHost.warn(str, th);
            }
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/qs/tileimpl/QSTileImpl$ResourceIcon.class */
    public static class ResourceIcon extends QSTile.Icon {
        public static final SparseArray<QSTile.Icon> ICONS = new SparseArray<>();
        public final int mResId;

        public ResourceIcon(int i) {
            this.mResId = i;
        }

        public static QSTile.Icon get(int i) {
            QSTile.Icon icon;
            synchronized (ResourceIcon.class) {
                try {
                    SparseArray<QSTile.Icon> sparseArray = ICONS;
                    QSTile.Icon icon2 = sparseArray.get(i);
                    icon = icon2;
                    if (icon2 == null) {
                        icon = new ResourceIcon(i);
                        sparseArray.put(i, icon);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            return icon;
        }

        public boolean equals(Object obj) {
            return (obj instanceof ResourceIcon) && ((ResourceIcon) obj).mResId == this.mResId;
        }

        @Override // com.android.systemui.plugins.qs.QSTile.Icon
        public Drawable getDrawable(Context context) {
            return context.getDrawable(this.mResId);
        }

        @Override // com.android.systemui.plugins.qs.QSTile.Icon
        public Drawable getInvisibleDrawable(Context context) {
            return context.getDrawable(this.mResId);
        }

        @Override // com.android.systemui.plugins.qs.QSTile.Icon
        public String toString() {
            return String.format("ResourceIcon[resId=0x%08x]", Integer.valueOf(this.mResId));
        }
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.tileimpl.QSTileImpl$$ExternalSyntheticLambda2.run():void] */
    public static /* synthetic */ void $r8$lambda$3uKFUQSnmdOuhXykiztOtCMfJqs(QSTileImpl qSTileImpl) {
        qSTileImpl.lambda$handleDestroy$3();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.tileimpl.QSTileImpl$$ExternalSyntheticLambda0.run():void] */
    /* renamed from: $r8$lambda$a1oEqk3pmNFOBUDdiI8l-P4TBEQ */
    public static /* synthetic */ void m3961$r8$lambda$a1oEqk3pmNFOBUDdiI8lP4TBEQ(QSTileImpl qSTileImpl) {
        qSTileImpl.lambda$handleSetListeningInternal$1();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.tileimpl.QSTileImpl$$ExternalSyntheticLambda1.run():void] */
    /* renamed from: $r8$lambda$e-cBBTyS1RezsenZ9WhR4oHkA5k */
    public static /* synthetic */ void m3962$r8$lambda$ecBBTyS1RezsenZ9WhR4oHkA5k(QSTileImpl qSTileImpl) {
        qSTileImpl.lambda$handleSetListeningInternal$2();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.tileimpl.QSTileImpl$$ExternalSyntheticLambda3.run():void] */
    /* renamed from: $r8$lambda$r2OLczPmk2v7I3E-ku4iiKwTDsA */
    public static /* synthetic */ void m3963$r8$lambda$r2OLczPmk2v7I3Eku4iiKwTDsA(QSTileImpl qSTileImpl) {
        qSTileImpl.lambda$new$0();
    }

    public QSTileImpl(QSHost qSHost, Looper looper, Handler handler, FalsingManager falsingManager, MetricsLogger metricsLogger, StatusBarStateController statusBarStateController, ActivityStarter activityStarter, QSLogger qSLogger) {
        this.mHost = qSHost;
        this.mContext = qSHost.getContext();
        this.mInstanceId = qSHost.getNewInstanceId();
        this.mUiEventLogger = qSHost.getUiEventLogger();
        this.mUiHandler = handler;
        this.mHandler = new H(looper);
        this.mFalsingManager = falsingManager;
        this.mQSLogger = qSLogger;
        this.mMetricsLogger = metricsLogger;
        this.mStatusBarStateController = statusBarStateController;
        this.mActivityStarter = activityStarter;
        resetStates();
        handler.post(new Runnable() { // from class: com.android.systemui.qs.tileimpl.QSTileImpl$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                QSTileImpl.m3963$r8$lambda$r2OLczPmk2v7I3Eku4iiKwTDsA(QSTileImpl.this);
            }
        });
    }

    public void handleAddCallback(QSTile.Callback callback) {
        this.mCallbacks.add(callback);
        callback.onStateChanged(this.mState);
    }

    public void handleRemoveCallback(QSTile.Callback callback) {
        this.mCallbacks.remove(callback);
    }

    public void handleRemoveCallbacks() {
        this.mCallbacks.clear();
    }

    public void handleSetListeningInternal(Object obj, boolean z) {
        if (z) {
            if (this.mListeners.add(obj) && this.mListeners.size() == 1) {
                if (DEBUG) {
                    Log.d(this.TAG, "handleSetListening true");
                }
                handleSetListening(z);
                this.mUiHandler.post(new Runnable() { // from class: com.android.systemui.qs.tileimpl.QSTileImpl$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        QSTileImpl.m3961$r8$lambda$a1oEqk3pmNFOBUDdiI8lP4TBEQ(QSTileImpl.this);
                    }
                });
            }
        } else if (this.mListeners.remove(obj) && this.mListeners.size() == 0) {
            if (DEBUG) {
                Log.d(this.TAG, "handleSetListening false");
            }
            handleSetListening(z);
            this.mUiHandler.post(new Runnable() { // from class: com.android.systemui.qs.tileimpl.QSTileImpl$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    QSTileImpl.m3962$r8$lambda$ecBBTyS1RezsenZ9WhR4oHkA5k(QSTileImpl.this);
                }
            });
        }
        updateIsFullQs();
    }

    private void handleStateChanged() {
        if (this.mCallbacks.size() != 0) {
            for (int i = 0; i < this.mCallbacks.size(); i++) {
                this.mCallbacks.get(i).onStateChanged(this.mState);
            }
        }
    }

    public /* synthetic */ void lambda$handleDestroy$3() {
        this.mLifecycle.setCurrentState(Lifecycle.State.DESTROYED);
    }

    public /* synthetic */ void lambda$handleSetListeningInternal$1() {
        if (this.mLifecycle.getCurrentState().equals(Lifecycle.State.DESTROYED)) {
            return;
        }
        this.mLifecycle.setCurrentState(Lifecycle.State.RESUMED);
        if (this.mReadyState == 0) {
            this.mReadyState = 1;
        }
        refreshState();
    }

    public /* synthetic */ void lambda$handleSetListeningInternal$2() {
        if (this.mLifecycle.getCurrentState().equals(Lifecycle.State.DESTROYED)) {
            return;
        }
        this.mLifecycle.setCurrentState(Lifecycle.State.STARTED);
    }

    public /* synthetic */ void lambda$new$0() {
        this.mLifecycle.setCurrentState(Lifecycle.State.CREATED);
    }

    private void updateIsFullQs() {
        Iterator<Object> it = this.mListeners.iterator();
        while (it.hasNext()) {
            if (SideLabelTileLayout.class.equals(it.next().getClass())) {
                this.mIsFullQs = 1;
                return;
            }
        }
        this.mIsFullQs = 0;
    }

    @Override // com.android.systemui.plugins.qs.QSTile
    public void addCallback(QSTile.Callback callback) {
        this.mHandler.obtainMessage(1, callback).sendToTarget();
    }

    public void checkIfRestrictionEnforcedByAdminOnly(QSTile.State state, String str) {
        RestrictedLockUtils.EnforcedAdmin checkIfRestrictionEnforced = RestrictedLockUtilsInternal.checkIfRestrictionEnforced(this.mContext, str, this.mHost.getUserId());
        if (checkIfRestrictionEnforced == null || RestrictedLockUtilsInternal.hasBaseUserRestriction(this.mContext, str, this.mHost.getUserId())) {
            state.disabledByPolicy = false;
            this.mEnforcedAdmin = null;
            return;
        }
        state.disabledByPolicy = true;
        this.mEnforcedAdmin = checkIfRestrictionEnforced;
    }

    @Override // com.android.systemui.plugins.qs.QSTile
    public void click(View view) {
        this.mMetricsLogger.write(populate(new LogMaker(925).setType(4).addTaggedData(1592, Integer.valueOf(this.mStatusBarStateController.getState()))));
        this.mUiEventLogger.logWithInstanceId(QSEvent.QS_ACTION_CLICK, 0, getMetricsSpec(), getInstanceId());
        int i = this.mClickEventId;
        this.mClickEventId = i + 1;
        this.mQSLogger.logTileClick(this.mTileSpec, this.mStatusBarStateController.getState(), this.mState.state, i);
        if (this.mFalsingManager.isFalseTap(1)) {
            return;
        }
        this.mHandler.obtainMessage(2, i, 0, view).sendToTarget();
    }

    @Override // com.android.systemui.plugins.qs.QSTile
    public QSIconView createTileView(Context context) {
        return new QSIconViewImpl(context);
    }

    @Override // com.android.systemui.plugins.qs.QSTile
    public void destroy() {
        this.mHandler.sendEmptyMessage(7);
    }

    @Override // com.android.systemui.Dumpable
    public void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println(getClass().getSimpleName() + ":");
        printWriter.print("    ");
        printWriter.println(getState().toString());
    }

    public QSHost getHost() {
        return this.mHost;
    }

    @Override // com.android.systemui.plugins.qs.QSTile
    public InstanceId getInstanceId() {
        return this.mInstanceId;
    }

    @Override // androidx.lifecycle.LifecycleOwner
    public Lifecycle getLifecycle() {
        return this.mLifecycle;
    }

    public abstract Intent getLongClickIntent();

    @Override // com.android.systemui.plugins.qs.QSTile
    @Deprecated
    public int getMetricsCategory() {
        return 0;
    }

    @Override // com.android.systemui.plugins.qs.QSTile
    public String getMetricsSpec() {
        return this.mTileSpec;
    }

    public long getStaleTimeout() {
        return DEFAULT_STALE_TIMEOUT;
    }

    @Override // com.android.systemui.plugins.qs.QSTile
    public TState getState() {
        return this.mState;
    }

    @Override // com.android.systemui.plugins.qs.QSTile
    public abstract CharSequence getTileLabel();

    @Override // com.android.systemui.plugins.qs.QSTile
    public String getTileSpec() {
        return this.mTileSpec;
    }

    public abstract void handleClick(View view);

    public void handleDestroy() {
        this.mQSLogger.logTileDestroyed(this.mTileSpec, "Handle destroy");
        if (this.mListeners.size() != 0) {
            handleSetListening(false);
            this.mListeners.clear();
        }
        this.mCallbacks.clear();
        this.mHandler.removeCallbacksAndMessages(null);
        this.mUiHandler.post(new Runnable() { // from class: com.android.systemui.qs.tileimpl.QSTileImpl$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                QSTileImpl.$r8$lambda$3uKFUQSnmdOuhXykiztOtCMfJqs(QSTileImpl.this);
            }
        });
    }

    public void handleInitialize() {
    }

    public void handleLongClick(View view) {
        this.mActivityStarter.postStartActivityDismissingKeyguard(getLongClickIntent(), 0, view != null ? ActivityLaunchAnimator.Controller.fromView(view, 32) : null);
    }

    public final void handleRefreshState(Object obj) {
        handleUpdateState(this.mTmpState, obj);
        boolean copyTo = this.mTmpState.copyTo(this.mState);
        if (this.mReadyState == 1) {
            this.mReadyState = 2;
            copyTo = true;
        }
        if (copyTo) {
            this.mQSLogger.logTileUpdated(this.mTileSpec, this.mState);
            handleStateChanged();
        }
        this.mHandler.removeMessages(11);
        this.mHandler.sendEmptyMessageDelayed(11, getStaleTimeout());
        setListening(this.mStaleListener, false);
    }

    public void handleSecondaryClick(View view) {
        handleClick(view);
    }

    public void handleSetListening(boolean z) {
        String str = this.mTileSpec;
        if (str != null) {
            this.mQSLogger.logTileChangeListening(str, z);
        }
    }

    @VisibleForTesting
    public void handleStale() {
        if (this.mListeners.isEmpty()) {
            setListening(this.mStaleListener, true);
        } else {
            refreshState();
        }
    }

    public abstract void handleUpdateState(TState tstate, Object obj);

    public void handleUserSwitch(int i) {
        handleRefreshState(null);
    }

    public void initialize() {
        this.mHandler.sendEmptyMessage(12);
    }

    @Override // com.android.systemui.plugins.qs.QSTile
    public boolean isAvailable() {
        return true;
    }

    @Override // com.android.systemui.plugins.qs.QSTile
    public final boolean isListening() {
        return getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.RESUMED);
    }

    @Override // com.android.systemui.plugins.qs.QSTile
    public boolean isTileReady() {
        return this.mReadyState == 2;
    }

    @Override // com.android.systemui.plugins.qs.QSTile
    public void longClick(View view) {
        this.mMetricsLogger.write(populate(new LogMaker(366).setType(4).addTaggedData(1592, Integer.valueOf(this.mStatusBarStateController.getState()))));
        this.mUiEventLogger.logWithInstanceId(QSEvent.QS_ACTION_LONG_PRESS, 0, getMetricsSpec(), getInstanceId());
        int i = this.mClickEventId;
        this.mClickEventId = i + 1;
        this.mQSLogger.logTileLongClick(this.mTileSpec, this.mStatusBarStateController.getState(), this.mState.state, i);
        this.mHandler.obtainMessage(4, i, 0, view).sendToTarget();
    }

    public abstract TState newTileState();

    @Override // com.android.systemui.plugins.qs.QSTile
    public LogMaker populate(LogMaker logMaker) {
        TState tstate = this.mState;
        if (tstate instanceof QSTile.BooleanState) {
            logMaker.addTaggedData(928, Integer.valueOf(((QSTile.BooleanState) tstate).value ? 1 : 0));
        }
        return logMaker.setSubtype(getMetricsCategory()).addTaggedData(1593, Integer.valueOf(this.mIsFullQs)).addTaggedData(927, Integer.valueOf(this.mHost.indexOf(this.mTileSpec)));
    }

    public void postStale() {
        this.mHandler.sendEmptyMessage(11);
    }

    @Override // com.android.systemui.plugins.qs.QSTile
    public void refreshState() {
        refreshState(null);
    }

    public final void refreshState(Object obj) {
        this.mHandler.obtainMessage(5, obj).sendToTarget();
    }

    @Override // com.android.systemui.plugins.qs.QSTile
    public void removeCallback(QSTile.Callback callback) {
        this.mHandler.obtainMessage(9, callback).sendToTarget();
    }

    @Override // com.android.systemui.plugins.qs.QSTile
    public void removeCallbacks() {
        this.mHandler.sendEmptyMessage(8);
    }

    public final void resetStates() {
        this.mState = newTileState();
        TState newTileState = newTileState();
        this.mTmpState = newTileState;
        TState tstate = this.mState;
        String str = this.mTileSpec;
        tstate.spec = str;
        newTileState.spec = str;
    }

    @Override // com.android.systemui.plugins.qs.QSTile
    public void secondaryClick(View view) {
        this.mMetricsLogger.write(populate(new LogMaker(926).setType(4).addTaggedData(1592, Integer.valueOf(this.mStatusBarStateController.getState()))));
        this.mUiEventLogger.logWithInstanceId(QSEvent.QS_ACTION_SECONDARY_CLICK, 0, getMetricsSpec(), getInstanceId());
        int i = this.mClickEventId;
        this.mClickEventId = i + 1;
        this.mQSLogger.logTileSecondaryClick(this.mTileSpec, this.mStatusBarStateController.getState(), this.mState.state, i);
        this.mHandler.obtainMessage(3, i, 0, view).sendToTarget();
    }

    @Override // com.android.systemui.plugins.qs.QSTile
    public void setDetailListening(boolean z) {
    }

    @Override // com.android.systemui.plugins.qs.QSTile
    public void setListening(Object obj, boolean z) {
        this.mHandler.obtainMessage(10, z ? 1 : 0, 0, obj).sendToTarget();
    }

    @Override // com.android.systemui.plugins.qs.QSTile
    public void setTileSpec(String str) {
        this.mTileSpec = str;
        this.mState.spec = str;
        this.mTmpState.spec = str;
    }

    @Override // com.android.systemui.plugins.qs.QSTile
    public void userSwitch(int i) {
        this.mHandler.obtainMessage(6, i, 0).sendToTarget();
    }
}