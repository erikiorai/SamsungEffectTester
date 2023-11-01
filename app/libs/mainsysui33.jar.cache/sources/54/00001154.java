package com.android.systemui.biometrics;

import android.content.Context;
import android.content.res.Configuration;
import android.hardware.fingerprint.FingerprintManager;
import android.hardware.fingerprint.FingerprintSensorPropertiesInternal;
import android.view.Display;
import android.view.DisplayInfo;
import com.airbnb.lottie.LottieAnimationView;
import com.android.settingslib.widget.LottieColorUtils;
import com.android.systemui.R$dimen;
import com.android.systemui.R$raw;
import com.android.systemui.R$string;
import com.android.systemui.unfold.compat.ScreenSizeFoldProvider;
import com.android.systemui.unfold.updates.FoldProvider;
import java.util.Iterator;
import kotlin.Pair;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: mainsysui33.jar:com/android/systemui/biometrics/AuthBiometricFingerprintIconController.class */
public class AuthBiometricFingerprintIconController extends AuthIconController implements FoldProvider.FoldCallback {
    public Pair<Integer, Integer> iconLayoutParamSize;
    public final LottieAnimationView iconViewOverlay;
    public boolean isDeviceFolded;
    public final boolean isSideFps;
    public final ScreenSizeFoldProvider screenSizeFoldProvider;

    public AuthBiometricFingerprintIconController(Context context, LottieAnimationView lottieAnimationView, LottieAnimationView lottieAnimationView2) {
        super(context, lottieAnimationView);
        this.iconViewOverlay = lottieAnimationView2;
        this.screenSizeFoldProvider = new ScreenSizeFoldProvider(context);
        this.iconLayoutParamSize = new Pair<>(1, 1);
        setIconLayoutParamSize(new Pair<>(Integer.valueOf(context.getResources().getDimensionPixelSize(R$dimen.biometric_dialog_fingerprint_icon_width)), Integer.valueOf(context.getResources().getDimensionPixelSize(R$dimen.biometric_dialog_fingerprint_icon_height))));
        FingerprintManager fingerprintManager = (FingerprintManager) context.getSystemService("fingerprint");
        boolean z = false;
        boolean z2 = false;
        if (fingerprintManager != null) {
            Iterator it = fingerprintManager.getSensorPropertiesInternal().iterator();
            while (true) {
                z = z2;
                if (!it.hasNext()) {
                    break;
                } else if (((FingerprintSensorPropertiesInternal) it.next()).isAnySidefpsType()) {
                    z2 = true;
                }
            }
        }
        this.isSideFps = z;
        DisplayInfo displayInfo = new DisplayInfo();
        Display display = context.getDisplay();
        if (display != null) {
            display.getDisplayInfo(displayInfo);
        }
        if (z && displayInfo.rotation == 2) {
            lottieAnimationView.setRotation(180.0f);
        }
        this.screenSizeFoldProvider.registerCallback(this, context.getMainExecutor());
        this.screenSizeFoldProvider.onConfigurationChange(context.getResources().getConfiguration());
    }

    public Integer getAnimationForTransition(int i, int i2) {
        int i3;
        if (i2 == 1 || i2 == 2) {
            i3 = (i == 3 || i == 4) ? R$raw.fingerprint_dialogue_error_to_fingerprint_lottie : R$raw.fingerprint_dialogue_fingerprint_to_error_lottie;
        } else if (i2 == 3 || i2 == 4) {
            i3 = R$raw.fingerprint_dialogue_fingerprint_to_error_lottie;
        } else if (i2 != 6) {
            return null;
        } else {
            i3 = (i == 3 || i == 4) ? R$raw.fingerprint_dialogue_error_to_success_lottie : R$raw.fingerprint_dialogue_fingerprint_to_success_lottie;
        }
        return Integer.valueOf(i3);
    }

    public final CharSequence getIconContentDescription(int i) {
        Integer valueOf;
        String str = null;
        switch (i) {
            case 0:
            case 1:
            case 2:
            case 5:
            case 6:
                valueOf = Integer.valueOf(R$string.accessibility_fingerprint_dialog_fingerprint_icon);
                break;
            case 3:
            case 4:
                valueOf = Integer.valueOf(R$string.biometric_dialog_try_again);
                break;
            default:
                valueOf = null;
                break;
        }
        if (valueOf != null) {
            str = getContext().getString(valueOf.intValue());
        }
        return str;
    }

    public final int getSideFpsAnimationForTransition(int i) {
        return i != 1 ? i != 3 ? this.isDeviceFolded ? R$raw.biometricprompt_folded_base_default : R$raw.biometricprompt_landscape_base : this.isDeviceFolded ? R$raw.biometricprompt_folded_base_bottomright : R$raw.biometricprompt_portrait_base_bottomright : this.isDeviceFolded ? R$raw.biometricprompt_folded_base_topleft : R$raw.biometricprompt_portrait_base_topleft;
    }

    public final Integer getSideFpsOverlayAnimationForTransition(int i, int i2, int i3) {
        Integer valueOf;
        if (i2 == 1 || i2 == 2) {
            valueOf = Integer.valueOf((i == 3 || i == 4) ? i3 != 0 ? i3 != 1 ? i3 != 2 ? i3 != 3 ? R$raw.biometricprompt_symbol_error_to_fingerprint_landscape : R$raw.biometricprompt_symbol_error_to_fingerprint_portrait_bottomright : R$raw.biometricprompt_symbol_error_to_fingerprint_landscape : R$raw.biometricprompt_symbol_error_to_fingerprint_portrait_topleft : R$raw.biometricprompt_symbol_error_to_fingerprint_landscape : i3 != 0 ? i3 != 1 ? i3 != 2 ? i3 != 3 ? R$raw.biometricprompt_fingerprint_to_error_landscape : R$raw.biometricprompt_symbol_fingerprint_to_error_portrait_bottomright : R$raw.biometricprompt_fingerprint_to_error_landscape : R$raw.biometricprompt_symbol_fingerprint_to_error_portrait_topleft : R$raw.biometricprompt_fingerprint_to_error_landscape);
        } else if (i2 == 3 || i2 == 4) {
            valueOf = Integer.valueOf(i3 != 0 ? i3 != 1 ? i3 != 2 ? i3 != 3 ? R$raw.biometricprompt_fingerprint_to_error_landscape : R$raw.biometricprompt_symbol_fingerprint_to_error_portrait_bottomright : R$raw.biometricprompt_fingerprint_to_error_landscape : R$raw.biometricprompt_symbol_fingerprint_to_error_portrait_topleft : R$raw.biometricprompt_fingerprint_to_error_landscape);
        } else if (i2 != 6) {
            valueOf = null;
        } else {
            valueOf = Integer.valueOf((i == 3 || i == 4) ? i3 != 0 ? i3 != 1 ? i3 != 2 ? i3 != 3 ? R$raw.biometricprompt_symbol_error_to_success_landscape : R$raw.biometricprompt_symbol_error_to_success_portrait_bottomright : R$raw.biometricprompt_symbol_error_to_success_landscape : R$raw.biometricprompt_symbol_error_to_success_portrait_topleft : R$raw.biometricprompt_symbol_error_to_success_landscape : i3 != 0 ? i3 != 1 ? i3 != 2 ? i3 != 3 ? R$raw.biometricprompt_symbol_fingerprint_to_success_landscape : R$raw.biometricprompt_symbol_fingerprint_to_success_portrait_bottomright : R$raw.biometricprompt_symbol_fingerprint_to_success_landscape : R$raw.biometricprompt_symbol_fingerprint_to_success_portrait_topleft : R$raw.biometricprompt_symbol_fingerprint_to_success_landscape);
        }
        return valueOf;
    }

    @Override // com.android.systemui.biometrics.AuthIconController
    public void onConfigurationChanged(Configuration configuration) {
        this.screenSizeFoldProvider.onConfigurationChange(configuration);
    }

    public void onFoldUpdated(boolean z) {
        this.isDeviceFolded = z;
    }

    public final void setIconLayoutParamSize(Pair<Integer, Integer> pair) {
        if (Intrinsics.areEqual(this.iconLayoutParamSize, pair)) {
            return;
        }
        this.iconViewOverlay.getLayoutParams().width = ((Number) pair.getFirst()).intValue();
        this.iconViewOverlay.getLayoutParams().height = ((Number) pair.getSecond()).intValue();
        getIconView().getLayoutParams().width = ((Number) pair.getFirst()).intValue();
        getIconView().getLayoutParams().height = ((Number) pair.getSecond()).intValue();
        this.iconLayoutParamSize = pair;
    }

    public boolean shouldAnimateForTransition(int i, int i2) {
        boolean z = false;
        if (i2 == 1 || i2 == 2 ? i == 4 || i == 3 : i2 == 3 || i2 == 4 || i2 == 6) {
            z = true;
        }
        return z;
    }

    @Override // com.android.systemui.biometrics.AuthIconController
    public void updateIcon(int i, int i2) {
        if (this.isSideFps) {
            updateIconSideFps(i, i2);
            return;
        }
        this.iconViewOverlay.setVisibility(8);
        updateIconNormal(i, i2);
    }

    public final void updateIconNormal(int i, int i2) {
        Integer animationForTransition = getAnimationForTransition(i, i2);
        if (animationForTransition != null) {
            int intValue = animationForTransition.intValue();
            if (i != 1 || i2 != 2) {
                getIconView().setAnimation(intValue);
            }
            CharSequence iconContentDescription = getIconContentDescription(i2);
            if (iconContentDescription != null) {
                getIconView().setContentDescription(iconContentDescription);
            }
            getIconView().setFrame(0);
            if (shouldAnimateForTransition(i, i2)) {
                getIconView().playAnimation();
            }
            LottieColorUtils.applyDynamicColors(getContext(), getIconView());
        }
    }

    public final void updateIconSideFps(int i, int i2) {
        DisplayInfo displayInfo = new DisplayInfo();
        Display display = getContext().getDisplay();
        if (display != null) {
            display.getDisplayInfo(displayInfo);
        }
        int i3 = displayInfo.rotation;
        int sideFpsAnimationForTransition = getSideFpsAnimationForTransition(i3);
        Integer sideFpsOverlayAnimationForTransition = getSideFpsOverlayAnimationForTransition(i, i2, i3);
        if (sideFpsOverlayAnimationForTransition != null) {
            int intValue = sideFpsOverlayAnimationForTransition.intValue();
            if (i != 1 || i2 != 2) {
                getIconView().setAnimation(sideFpsAnimationForTransition);
                this.iconViewOverlay.setAnimation(intValue);
            }
            CharSequence iconContentDescription = getIconContentDescription(i2);
            if (iconContentDescription != null) {
                getIconView().setContentDescription(iconContentDescription);
                this.iconViewOverlay.setContentDescription(iconContentDescription);
            }
            getIconView().setFrame(0);
            this.iconViewOverlay.setFrame(0);
            if (shouldAnimateForTransition(i, i2)) {
                getIconView().playAnimation();
                this.iconViewOverlay.playAnimation();
            } else if (i == 0 && i2 == 1) {
                getIconView().playAnimation();
            }
            LottieColorUtils.applyDynamicColors(getContext(), getIconView());
            LottieColorUtils.applyDynamicColors(getContext(), this.iconViewOverlay);
        }
    }
}