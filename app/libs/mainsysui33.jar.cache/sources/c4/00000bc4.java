package com.android.keyguard;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Configuration;
import android.provider.Settings;
import android.util.AttributeSet;
import android.util.MathUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.LinearLayout;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import com.android.settingslib.animation.DisappearAnimationUtils;
import com.android.settingslib.widget.ActionBarShadowController;
import com.android.systemui.R$dimen;
import com.android.systemui.R$id;
import com.android.systemui.R$string;
import com.android.systemui.animation.Interpolators;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/* loaded from: mainsysui33.jar:com/android/keyguard/KeyguardPINView.class */
public class KeyguardPINView extends KeyguardPinBasedInputView {
    public ValueAnimator mAppearAnimator;
    public View mBouncerMessageView;
    public ConstraintLayout mContainer;
    public final List<Integer> mDefaultNumbers;
    public final DisappearAnimationUtils mDisappearAnimationUtils;
    public final DisappearAnimationUtils mDisappearAnimationUtilsLocked;
    public int mDisappearYTranslation;
    public int mLastDevicePosture;
    public List<Integer> mNumbers;
    public boolean mScramblePin;
    public View[][] mViews;
    public int mYTrans;
    public int mYTransOffset;

    /* JADX DEBUG: Method not inlined, still used in: [com.android.keyguard.KeyguardPINView$$ExternalSyntheticLambda1.run():void] */
    public static /* synthetic */ void $r8$lambda$H5jqx49Odpl1xelI6nf0IhDbDTI(Runnable runnable) {
        lambda$startDisappearAnimation$1(runnable);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.keyguard.KeyguardPINView$$ExternalSyntheticLambda0.onAnimationUpdate(android.animation.ValueAnimator):void] */
    public static /* synthetic */ void $r8$lambda$jILFrChAtFeBfPHPlXv7yl8isl0(KeyguardPINView keyguardPINView, ValueAnimator valueAnimator) {
        keyguardPINView.lambda$startAppearAnimation$0(valueAnimator);
    }

    public KeyguardPINView(Context context) {
        this(context, null);
    }

    public KeyguardPINView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mAppearAnimator = ValueAnimator.ofFloat(ActionBarShadowController.ELEVATION_LOW, 1.0f);
        this.mLastDevicePosture = 0;
        List<Integer> asList = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 0);
        this.mNumbers = asList;
        this.mDefaultNumbers = List.of((Object[]) ((Integer[]) asList.toArray(new Integer[0])));
        this.mDisappearAnimationUtils = new DisappearAnimationUtils(context, 125L, 0.6f, 0.45f, AnimationUtils.loadInterpolator(((LinearLayout) this).mContext, 17563663));
        this.mDisappearAnimationUtilsLocked = new DisappearAnimationUtils(context, 187L, 0.6f, 0.45f, AnimationUtils.loadInterpolator(((LinearLayout) this).mContext, 17563663));
        this.mDisappearYTranslation = getResources().getDimensionPixelSize(R$dimen.disappear_y_translation);
        this.mYTrans = getResources().getDimensionPixelSize(R$dimen.pin_view_trans_y_entry);
        this.mYTransOffset = getResources().getDimensionPixelSize(R$dimen.pin_view_trans_y_entry_offset);
    }

    public /* synthetic */ void lambda$startAppearAnimation$0(ValueAnimator valueAnimator) {
        animate(valueAnimator.getAnimatedFraction());
    }

    public static /* synthetic */ void lambda$startDisappearAnimation$1(Runnable runnable) {
        if (runnable != null) {
            runnable.run();
        }
    }

    public final void animate(float f) {
        View[] viewArr;
        Interpolator interpolator = Interpolators.STANDARD_DECELERATE;
        Interpolator interpolator2 = Interpolators.LEGACY_DECELERATE;
        View view = this.mBouncerMessageView;
        int i = this.mYTrans;
        view.setTranslationY(i - (i * interpolator.getInterpolation(f)));
        int i2 = 0;
        while (true) {
            View[][] viewArr2 = this.mViews;
            if (i2 >= viewArr2.length) {
                return;
            }
            for (View view2 : viewArr2[i2]) {
                if (view2 != null) {
                    float interpolation = interpolator2.getInterpolation(MathUtils.constrain((f - (i2 * 0.075f)) / (1.0f - (this.mViews.length * 0.075f)), (float) ActionBarShadowController.ELEVATION_LOW, 1.0f));
                    view2.setAlpha(interpolation);
                    float f2 = this.mYTrans + (this.mYTransOffset * i2);
                    view2.setTranslationY(f2 - (interpolator.getInterpolation(f) * f2));
                    if (view2 instanceof NumPadAnimationListener) {
                        ((NumPadAnimationListener) view2).setProgress(interpolation);
                    }
                }
            }
            i2++;
        }
    }

    @Override // com.android.keyguard.KeyguardPinBasedInputView
    public int getNumberIndex(int i) {
        return this.mScramblePin ? (this.mNumbers.indexOf(Integer.valueOf(i)) + 1) % this.mNumbers.size() : super.getNumberIndex(i);
    }

    @Override // com.android.keyguard.KeyguardAbsKeyInputView
    public int getPasswordTextViewId() {
        return R$id.pinEntry;
    }

    @Override // com.android.keyguard.KeyguardAbsKeyInputView
    public int getWrongPasswordStringId() {
        return R$string.kg_wrong_pin;
    }

    @Override // android.view.View
    public boolean hasOverlappingRendering() {
        return false;
    }

    @Override // android.view.View
    public void onConfigurationChanged(Configuration configuration) {
        updateMargins();
    }

    public void onDevicePostureChanged(int i) {
        this.mLastDevicePosture = i;
        updateMargins();
    }

    /* JADX WARN: Type inference failed for: r1v21, types: [android.view.View[], android.view.View[][]] */
    @Override // com.android.keyguard.KeyguardPinBasedInputView, com.android.keyguard.KeyguardAbsKeyInputView, android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mContainer = (ConstraintLayout) findViewById(R$id.pin_container);
        this.mBouncerMessageView = findViewById(R$id.bouncer_message_area);
        this.mViews = new View[]{new View[]{findViewById(R$id.row0), null, null}, new View[]{findViewById(R$id.key1), findViewById(R$id.key2), findViewById(R$id.key3)}, new View[]{findViewById(R$id.key4), findViewById(R$id.key5), findViewById(R$id.key6)}, new View[]{findViewById(R$id.key7), findViewById(R$id.key8), findViewById(R$id.key9)}, new View[]{findViewById(R$id.delete_button), findViewById(R$id.key0), findViewById(R$id.key_enter)}, new View[]{null, this.mEcaView, null}};
        updatePinScrambling();
    }

    @Override // com.android.keyguard.KeyguardInputView
    public void startAppearAnimation() {
        updatePinScrambling();
        setAlpha(1.0f);
        setTranslationY(ActionBarShadowController.ELEVATION_LOW);
        if (this.mAppearAnimator.isRunning()) {
            this.mAppearAnimator.cancel();
        }
        this.mAppearAnimator.setDuration(650L);
        this.mAppearAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.keyguard.KeyguardPINView$$ExternalSyntheticLambda0
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                KeyguardPINView.$r8$lambda$jILFrChAtFeBfPHPlXv7yl8isl0(KeyguardPINView.this, valueAnimator);
            }
        });
        this.mAppearAnimator.start();
    }

    public boolean startDisappearAnimation(boolean z, final Runnable runnable) {
        if (this.mAppearAnimator.isRunning()) {
            this.mAppearAnimator.cancel();
        }
        setTranslationY(ActionBarShadowController.ELEVATION_LOW);
        (z ? this.mDisappearAnimationUtilsLocked : this.mDisappearAnimationUtils).createAnimation(this, 0L, 200L, this.mDisappearYTranslation, false, this.mDisappearAnimationUtils.getInterpolator(), new Runnable() { // from class: com.android.keyguard.KeyguardPINView$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                KeyguardPINView.$r8$lambda$H5jqx49Odpl1xelI6nf0IhDbDTI(runnable);
            }
        }, getAnimationListener(22));
        return true;
    }

    public final void updateMargins() {
        int dimensionPixelSize = ((LinearLayout) this).mContext.getResources().getDimensionPixelSize(R$dimen.num_pad_entry_row_margin_bottom);
        int dimensionPixelSize2 = ((LinearLayout) this).mContext.getResources().getDimensionPixelSize(R$dimen.num_pad_key_margin_end);
        String string = ((LinearLayout) this).mContext.getResources().getString(R$string.num_pad_key_ratio);
        for (int i = 1; i < 5; i++) {
            for (int i2 = 0; i2 < 3; i2++) {
                View view = this.mViews[i][i2];
                ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) view.getLayoutParams();
                layoutParams.dimensionRatio = string;
                if (i != 4) {
                    ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin = dimensionPixelSize;
                }
                if (i2 != 2) {
                    ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin = dimensionPixelSize2;
                }
                view.setLayoutParams(layoutParams);
            }
        }
        float f = ((LinearLayout) this).mContext.getResources().getFloat(R$dimen.half_opened_bouncer_height_ratio);
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(this.mContainer);
        int i3 = R$id.pin_pad_top_guideline;
        if (this.mLastDevicePosture != 2) {
            f = 0.0f;
        }
        constraintSet.setGuidelinePercent(i3, f);
        constraintSet.applyTo(this.mContainer);
    }

    public final void updatePinScrambling() {
        int i;
        int i2 = 1;
        boolean z = Settings.System.getInt(getContext().getContentResolver(), "lockscreen_scramble_pin_layout", 0) == 1;
        if (z || z != this.mScramblePin) {
            this.mScramblePin = z;
            if (z) {
                Collections.shuffle(this.mNumbers);
            } else {
                this.mNumbers = new ArrayList(this.mDefaultNumbers);
            }
            ArrayList arrayList = new ArrayList();
            while (true) {
                if (i2 >= 5) {
                    break;
                }
                for (int i3 = 0; i3 < 3; i3++) {
                    View view = this.mViews[i2][i3];
                    if (view instanceof NumPadKey) {
                        arrayList.add((NumPadKey) view);
                    }
                }
                i2++;
            }
            for (i = 0; i < this.mNumbers.size(); i++) {
                ((NumPadKey) arrayList.get(i)).setDigit(this.mNumbers.get(i).intValue());
            }
        }
    }
}