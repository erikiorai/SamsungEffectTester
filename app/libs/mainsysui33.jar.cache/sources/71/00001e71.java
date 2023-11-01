package com.android.systemui.navigationbar;

import android.content.Context;
import android.os.RemoteException;
import android.os.SystemClock;
import android.util.Slog;
import android.view.IWindowManager;
import android.view.WindowManagerGlobal;
import android.widget.Toast;
import com.android.systemui.R$string;
import com.android.systemui.SysUIToast;

/* loaded from: mainsysui33.jar:com/android/systemui/navigationbar/ScreenPinningNotify.class */
public class ScreenPinningNotify {
    public final Context mContext;
    public long mLastShowToastTime;
    public Toast mLastToast;
    public final IWindowManager mWindowManagerService = WindowManagerGlobal.getWindowManagerService();

    public ScreenPinningNotify(Context context) {
        this.mContext = context;
    }

    public final boolean hasNavigationBar() {
        try {
            return this.mWindowManagerService.hasNavigationBar(this.mContext.getDisplayId());
        } catch (RemoteException e) {
            return false;
        }
    }

    public final Toast makeAllUserToastAndShow(int i) {
        Toast makeText = SysUIToast.makeText(this.mContext, i, 1);
        makeText.show();
        return makeText;
    }

    public void showEscapeToast(boolean z, boolean z2) {
        long elapsedRealtime = SystemClock.elapsedRealtime();
        if (elapsedRealtime - this.mLastShowToastTime < 1000) {
            Slog.i("ScreenPinningNotify", "Ignore toast since it is requested in very short interval.");
            return;
        }
        Toast toast = this.mLastToast;
        if (toast != null) {
            toast.cancel();
        }
        int i = supportsGesturesOnFP() ? R$string.screen_pinning_toast_no_navbar_fpsensor : R$string.screen_pinning_toast_no_navbar;
        if (hasNavigationBar()) {
            i = z ? R$string.screen_pinning_toast_gesture_nav : z2 ? R$string.screen_pinning_toast : R$string.screen_pinning_toast_recents_invisible;
        }
        this.mLastToast = makeAllUserToastAndShow(i);
        this.mLastShowToastTime = elapsedRealtime;
    }

    public void showPinningExitToast() {
        makeAllUserToastAndShow(R$string.screen_pinning_exit);
    }

    public void showPinningStartToast() {
        makeAllUserToastAndShow(R$string.screen_pinning_start);
    }

    public final boolean supportsGesturesOnFP() {
        return this.mContext.getResources().getBoolean(17891816);
    }
}