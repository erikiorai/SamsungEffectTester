package com.android.systemui.plugins;

import android.animation.Animator;
import android.view.View;
import com.android.systemui.plugins.annotations.ProvidesInterface;

@ProvidesInterface(action = ToastPlugin.ACTION, version = 1)
/* loaded from: mainsysui33.jar:com/android/systemui/plugins/ToastPlugin.class */
public interface ToastPlugin extends Plugin {
    public static final String ACTION = "com.android.systemui.action.PLUGIN_TOAST";
    public static final int VERSION = 1;

    /* loaded from: mainsysui33.jar:com/android/systemui/plugins/ToastPlugin$Toast.class */
    public interface Toast {
        default Integer getGravity() {
            return null;
        }

        default Integer getHorizontalMargin() {
            return null;
        }

        default Animator getInAnimation() {
            return null;
        }

        default Animator getOutAnimation() {
            return null;
        }

        default Integer getVerticalMargin() {
            return null;
        }

        default View getView() {
            return null;
        }

        default Integer getXOffset() {
            return null;
        }

        default Integer getYOffset() {
            return null;
        }

        default void onOrientationChange(int i) {
        }
    }

    Toast createToast(CharSequence charSequence, String str, int i);
}