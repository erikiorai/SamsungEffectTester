package com.android.systemui.qs.tiles;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.os.PowerManager;
import android.os.SystemClock;
import android.view.View;
import com.android.internal.logging.MetricsLogger;
import com.android.systemui.R$drawable;
import com.android.systemui.R$string;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.qs.QSHost;
import com.android.systemui.qs.logging.QSLogger;
import com.android.systemui.qs.tileimpl.QSTileImpl;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/tiles/CaffeineTile.class */
public class CaffeineTile extends QSTileImpl<QSTile.BooleanState> {
    public static int[] DURATIONS;
    public static final int INFINITE_DURATION_INDEX;
    public CountDownTimer mCountdownTimer;
    public int mDuration;
    public final QSTile.Icon mIcon;
    public long mLastClickTime;
    public final Receiver mReceiver;
    public int mSecondsRemaining;
    public final PowerManager.WakeLock mWakeLock;

    /* loaded from: mainsysui33.jar:com/android/systemui/qs/tiles/CaffeineTile$Receiver.class */
    public final class Receiver extends BroadcastReceiver {
        public Receiver() {
        }

        public void destroy() {
            CaffeineTile.this.mContext.unregisterReceiver(this);
        }

        public void init() {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.intent.action.SCREEN_OFF");
            CaffeineTile.this.mContext.registerReceiver(this, intentFilter, null, CaffeineTile.this.mHandler);
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if ("android.intent.action.SCREEN_OFF".equals(intent.getAction())) {
                CaffeineTile.this.stopCountDown();
                if (CaffeineTile.this.mWakeLock.isHeld()) {
                    CaffeineTile.this.mWakeLock.release();
                }
                CaffeineTile.this.refreshState();
            }
        }
    }

    static {
        int[] iArr = {300, 600, 1800, -1};
        DURATIONS = iArr;
        INFINITE_DURATION_INDEX = iArr.length - 1;
    }

    public CaffeineTile(QSHost qSHost, Looper looper, Handler handler, FalsingManager falsingManager, MetricsLogger metricsLogger, StatusBarStateController statusBarStateController, ActivityStarter activityStarter, QSLogger qSLogger) {
        super(qSHost, looper, handler, falsingManager, metricsLogger, statusBarStateController, activityStarter, qSLogger);
        this.mIcon = QSTileImpl.ResourceIcon.get(R$drawable.ic_qs_caffeine);
        this.mCountdownTimer = null;
        this.mLastClickTime = -1L;
        Receiver receiver = new Receiver();
        this.mReceiver = receiver;
        this.mWakeLock = ((PowerManager) this.mContext.getSystemService(PowerManager.class)).newWakeLock(26, "CaffeineTile");
        receiver.init();
    }

    public final String formatValueWithRemainingTime() {
        int i = this.mSecondsRemaining;
        return i == -1 ? "∞" : String.format("%02d:%02d", Integer.valueOf((i / 60) % 60), Integer.valueOf(this.mSecondsRemaining % 60));
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public Intent getLongClickIntent() {
        return null;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public int getMetricsCategory() {
        return 0;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public CharSequence getTileLabel() {
        return this.mContext.getString(R$string.quick_settings_caffeine_label);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public void handleClick(View view) {
        if (this.mWakeLock.isHeld() && this.mLastClickTime != -1 && SystemClock.elapsedRealtime() - this.mLastClickTime < 5000) {
            int i = this.mDuration + 1;
            this.mDuration = i;
            int[] iArr = DURATIONS;
            if (i >= iArr.length) {
                this.mDuration = -1;
                stopCountDown();
                if (this.mWakeLock.isHeld()) {
                    this.mWakeLock.release();
                }
            } else {
                startCountDown(iArr[i]);
                if (!this.mWakeLock.isHeld()) {
                    this.mWakeLock.acquire();
                }
            }
        } else if (this.mWakeLock.isHeld()) {
            this.mWakeLock.release();
            stopCountDown();
        } else {
            this.mWakeLock.acquire();
            this.mDuration = 0;
            startCountDown(DURATIONS[0]);
        }
        this.mLastClickTime = SystemClock.elapsedRealtime();
        refreshState();
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public void handleDestroy() {
        super.handleDestroy();
        stopCountDown();
        this.mReceiver.destroy();
        if (this.mWakeLock.isHeld()) {
            this.mWakeLock.release();
        }
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public void handleLongClick(View view) {
        if (!this.mWakeLock.isHeld()) {
            this.mWakeLock.acquire();
        } else if (this.mDuration == INFINITE_DURATION_INDEX) {
            return;
        }
        int i = INFINITE_DURATION_INDEX;
        this.mDuration = i;
        startCountDown(DURATIONS[i]);
        refreshState();
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public void handleSetListening(boolean z) {
    }

    /* JADX DEBUG: Method merged with bridge method */
    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public void handleUpdateState(QSTile.BooleanState booleanState, Object obj) {
        booleanState.value = this.mWakeLock.isHeld();
        booleanState.icon = this.mIcon;
        booleanState.label = this.mContext.getString(R$string.quick_settings_caffeine_label);
        if (booleanState.value) {
            booleanState.secondaryLabel = formatValueWithRemainingTime();
            booleanState.contentDescription = this.mContext.getString(R$string.accessibility_quick_settings_caffeine_on);
            booleanState.state = 2;
            return;
        }
        booleanState.secondaryLabel = null;
        booleanState.contentDescription = this.mContext.getString(R$string.accessibility_quick_settings_caffeine_off);
        booleanState.state = 1;
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public QSTile.BooleanState newTileState() {
        return new QSTile.BooleanState();
    }

    public final void startCountDown(long j) {
        stopCountDown();
        this.mSecondsRemaining = (int) j;
        if (j == -1) {
            return;
        }
        this.mCountdownTimer = new CountDownTimer(j * 1000, 1000L) { // from class: com.android.systemui.qs.tiles.CaffeineTile.1
            @Override // android.os.CountDownTimer
            public void onFinish() {
                if (CaffeineTile.this.mWakeLock.isHeld()) {
                    CaffeineTile.this.mWakeLock.release();
                }
                CaffeineTile.this.refreshState();
            }

            @Override // android.os.CountDownTimer
            public void onTick(long j2) {
                CaffeineTile.this.mSecondsRemaining = (int) (j2 / 1000);
                CaffeineTile.this.refreshState();
            }
        }.start();
    }

    public final void stopCountDown() {
        CountDownTimer countDownTimer = this.mCountdownTimer;
        if (countDownTimer != null) {
            countDownTimer.cancel();
            this.mCountdownTimer = null;
        }
    }
}