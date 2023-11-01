package com.android.systemui.biometrics;

import android.media.AudioAttributes;
import android.os.VibrationEffect;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.statusbar.VibratorHelper;
import com.android.systemui.statusbar.commandline.Command;
import com.android.systemui.statusbar.commandline.CommandRegistry;
import java.io.PrintWriter;
import java.util.List;
import kotlin.jvm.functions.Function0;

/* loaded from: mainsysui33.jar:com/android/systemui/biometrics/UdfpsHapticsSimulator.class */
public final class UdfpsHapticsSimulator implements Command {
    public final KeyguardUpdateMonitor keyguardUpdateMonitor;
    public final AudioAttributes sonificationEffects = new AudioAttributes.Builder().setContentType(4).setUsage(13).build();
    public UdfpsController udfpsController;
    public final VibratorHelper vibrator;

    public UdfpsHapticsSimulator(CommandRegistry commandRegistry, VibratorHelper vibratorHelper, KeyguardUpdateMonitor keyguardUpdateMonitor) {
        this.vibrator = vibratorHelper;
        this.keyguardUpdateMonitor = keyguardUpdateMonitor;
        commandRegistry.registerCommand("udfps-haptic", new Function0<Command>() { // from class: com.android.systemui.biometrics.UdfpsHapticsSimulator.1
            {
                super(0);
            }

            /* JADX DEBUG: Method merged with bridge method */
            /* renamed from: invoke */
            public final Command m1598invoke() {
                return UdfpsHapticsSimulator.this;
            }
        });
    }

    public void execute(PrintWriter printWriter, List<String> list) {
        if (list.isEmpty()) {
            invalidCommand(printWriter);
            return;
        }
        String str = list.get(0);
        int hashCode = str.hashCode();
        if (hashCode != -1867169789) {
            if (hashCode != 96784904) {
                if (hashCode == 109757538 && str.equals("start")) {
                    UdfpsController udfpsController = this.udfpsController;
                    if (udfpsController != null) {
                        udfpsController.playStartHaptic();
                        return;
                    }
                    return;
                }
            } else if (str.equals("error")) {
                this.vibrator.vibrate(VibrationEffect.get(1), this.sonificationEffects);
                return;
            }
        } else if (str.equals("success")) {
            this.vibrator.vibrate(VibrationEffect.get(0), this.sonificationEffects);
            return;
        }
        invalidCommand(printWriter);
    }

    public void help(PrintWriter printWriter) {
        printWriter.println("Usage: adb shell cmd statusbar udfps-haptic <haptic>");
        printWriter.println("Available commands:");
        printWriter.println("  start");
        printWriter.println("  success, always plays CLICK haptic");
        printWriter.println("  error, always plays DOUBLE_CLICK haptic");
    }

    public final void invalidCommand(PrintWriter printWriter) {
        printWriter.println("invalid command");
        help(printWriter);
    }

    public final void setUdfpsController(UdfpsController udfpsController) {
        this.udfpsController = udfpsController;
    }
}