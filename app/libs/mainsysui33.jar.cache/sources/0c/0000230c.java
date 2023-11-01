package com.android.systemui.qs.tiles;

import android.os.Handler;
import android.os.Looper;
import android.view.View;
import com.android.internal.logging.MetricsLogger;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.plugins.qs.QSTile.State;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.qs.QSHost;
import com.android.systemui.qs.logging.QSLogger;
import com.android.systemui.qs.tileimpl.QSTileImpl;
import com.android.systemui.statusbar.policy.KeyguardStateController;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/tiles/SecureQSTile.class */
public abstract class SecureQSTile<TState extends QSTile.State> extends QSTileImpl<TState> {
    public final KeyguardStateController mKeyguard;

    public SecureQSTile(QSHost qSHost, Looper looper, Handler handler, FalsingManager falsingManager, MetricsLogger metricsLogger, StatusBarStateController statusBarStateController, ActivityStarter activityStarter, QSLogger qSLogger, KeyguardStateController keyguardStateController) {
        super(qSHost, looper, handler, falsingManager, metricsLogger, statusBarStateController, activityStarter, qSLogger);
        this.mKeyguard = keyguardStateController;
    }

    public final boolean checkKeyguard(final View view, boolean z) {
        boolean z2;
        if (z) {
            this.mActivityStarter.postQSRunnableDismissingKeyguard(new Runnable(this) { // from class: com.android.systemui.qs.tiles.SecureQSTile$checkKeyguard$1
                public final /* synthetic */ SecureQSTile<TState> this$0;

                {
                    this.this$0 = this;
                }

                @Override // java.lang.Runnable
                public final void run() {
                    this.this$0.handleClick(view, false);
                }
            });
            z2 = true;
        } else {
            z2 = false;
        }
        return z2;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public void handleClick(View view) {
        handleClick(view, this.mKeyguard.isMethodSecure() && this.mKeyguard.isShowing());
    }

    public abstract void handleClick(View view, boolean z);
}