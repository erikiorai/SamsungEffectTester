package com.android.systemui.accessibility;

import android.content.Context;
import android.hardware.display.DisplayManager;
import android.view.Display;
import com.android.internal.annotations.VisibleForTesting;
import com.android.systemui.accessibility.MagnificationModeSwitch;

/* loaded from: mainsysui33.jar:com/android/systemui/accessibility/ModeSwitchesController.class */
public class ModeSwitchesController implements MagnificationModeSwitch.SwitchListener {
    public MagnificationModeSwitch.SwitchListener mSwitchListenerDelegate;
    public final DisplayIdIndexSupplier<MagnificationModeSwitch> mSwitchSupplier;

    /* loaded from: mainsysui33.jar:com/android/systemui/accessibility/ModeSwitchesController$SwitchSupplier.class */
    public static class SwitchSupplier extends DisplayIdIndexSupplier<MagnificationModeSwitch> {
        public final Context mContext;
        public final MagnificationModeSwitch.SwitchListener mSwitchListener;

        public SwitchSupplier(Context context, DisplayManager displayManager, MagnificationModeSwitch.SwitchListener switchListener) {
            super(displayManager);
            this.mContext = context;
            this.mSwitchListener = switchListener;
        }

        /* JADX DEBUG: Method merged with bridge method */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.android.systemui.accessibility.DisplayIdIndexSupplier
        public MagnificationModeSwitch createInstance(Display display) {
            return new MagnificationModeSwitch(this.mContext.createWindowContext(display, 2039, null), this.mSwitchListener);
        }
    }

    public ModeSwitchesController(Context context) {
        this.mSwitchSupplier = new SwitchSupplier(context, (DisplayManager) context.getSystemService(DisplayManager.class), new MagnificationModeSwitch.SwitchListener() { // from class: com.android.systemui.accessibility.ModeSwitchesController$$ExternalSyntheticLambda0
            @Override // com.android.systemui.accessibility.MagnificationModeSwitch.SwitchListener
            public final void onSwitch(int i, int i2) {
                ModeSwitchesController.this.onSwitch(i, i2);
            }
        });
    }

    @VisibleForTesting
    public ModeSwitchesController(DisplayIdIndexSupplier<MagnificationModeSwitch> displayIdIndexSupplier) {
        this.mSwitchSupplier = displayIdIndexSupplier;
    }

    @Override // com.android.systemui.accessibility.MagnificationModeSwitch.SwitchListener
    public void onSwitch(int i, int i2) {
        MagnificationModeSwitch.SwitchListener switchListener = this.mSwitchListenerDelegate;
        if (switchListener != null) {
            switchListener.onSwitch(i, i2);
        }
    }

    public void removeButton(int i) {
        MagnificationModeSwitch magnificationModeSwitch = this.mSwitchSupplier.get(i);
        if (magnificationModeSwitch == null) {
            return;
        }
        magnificationModeSwitch.lambda$new$2();
    }

    public void setSwitchListenerDelegate(MagnificationModeSwitch.SwitchListener switchListener) {
        this.mSwitchListenerDelegate = switchListener;
    }

    public void showButton(int i, int i2) {
        MagnificationModeSwitch magnificationModeSwitch = this.mSwitchSupplier.get(i);
        if (magnificationModeSwitch == null) {
            return;
        }
        magnificationModeSwitch.showButton(i2);
    }
}