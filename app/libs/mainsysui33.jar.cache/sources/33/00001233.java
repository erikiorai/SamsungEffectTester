package com.android.systemui.biometrics;

import android.hardware.fingerprint.IUdfpsOverlayController;
import android.hardware.fingerprint.IUdfpsOverlayControllerCallback;
import android.util.Log;
import com.android.systemui.statusbar.commandline.Command;
import com.android.systemui.statusbar.commandline.CommandRegistry;
import java.io.PrintWriter;
import java.util.List;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: mainsysui33.jar:com/android/systemui/biometrics/UdfpsShell.class */
public final class UdfpsShell implements Command {
    public IUdfpsOverlayController udfpsOverlayController;

    public UdfpsShell(CommandRegistry commandRegistry) {
        commandRegistry.registerCommand("udfps", new Function0<Command>() { // from class: com.android.systemui.biometrics.UdfpsShell.1
            {
                super(0);
            }

            /* JADX DEBUG: Method merged with bridge method */
            /* renamed from: invoke */
            public final Command m1608invoke() {
                return UdfpsShell.this;
            }
        });
    }

    public void execute(PrintWriter printWriter, List<String> list) {
        if (list.size() == 1 && Intrinsics.areEqual(list.get(0), "hide")) {
            hideOverlay();
        } else if (list.size() == 2 && Intrinsics.areEqual(list.get(0), "show")) {
            showOverlay(getEnrollmentReason(list.get(1)));
        } else {
            invalidCommand(printWriter);
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public final int getEnrollmentReason(String str) {
        int i;
        switch (str.hashCode()) {
            case -945543637:
                if (str.equals("auth-keyguard")) {
                    i = 4;
                    break;
                }
                i = 0;
                break;
            case -943067225:
                if (str.equals("enroll-find-sensor")) {
                    i = 1;
                    break;
                }
                i = 0;
                break;
            case -646572397:
                if (str.equals("auth-bp")) {
                    i = 3;
                    break;
                }
                i = 0;
                break;
            case -19448152:
                if (str.equals("auth-settings")) {
                    i = 6;
                    break;
                }
                i = 0;
                break;
            case 244570389:
                if (str.equals("enroll-enrolling")) {
                    i = 2;
                    break;
                }
                i = 0;
                break;
            case 902271659:
                if (str.equals("auth-other")) {
                    i = 5;
                    break;
                }
                i = 0;
                break;
            default:
                i = 0;
                break;
        }
        return i;
    }

    public void help(PrintWriter printWriter) {
        printWriter.println("Usage: adb shell cmd statusbar udfps <cmd>");
        printWriter.println("Supported commands:");
        printWriter.println("  - show <reason>");
        printWriter.println("    -> supported reasons: [enroll-find-sensor, enroll-enrolling, auth-bp, auth-keyguard, auth-other, auth-settings]");
        printWriter.println("    -> reason otherwise defaults to unknown");
        printWriter.println("  - hide");
    }

    public final void hideOverlay() {
        IUdfpsOverlayController iUdfpsOverlayController = this.udfpsOverlayController;
        if (iUdfpsOverlayController != null) {
            iUdfpsOverlayController.hideUdfpsOverlay(0);
        }
    }

    public final void invalidCommand(PrintWriter printWriter) {
        printWriter.println("invalid command");
        help(printWriter);
    }

    public final void setUdfpsOverlayController(IUdfpsOverlayController iUdfpsOverlayController) {
        this.udfpsOverlayController = iUdfpsOverlayController;
    }

    public final void showOverlay(int i) {
        IUdfpsOverlayController iUdfpsOverlayController = this.udfpsOverlayController;
        if (iUdfpsOverlayController != null) {
            iUdfpsOverlayController.showUdfpsOverlay(2L, 0, i, new IUdfpsOverlayControllerCallback.Stub() { // from class: com.android.systemui.biometrics.UdfpsShell$showOverlay$1
                public void onUserCanceled() {
                    Log.e("UdfpsShell", "User cancelled");
                }
            });
        }
    }
}