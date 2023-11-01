package com.android.systemui.keyguard.ui.binder;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.graphics.drawable.Animatable2;
import android.graphics.drawable.Drawable;
import android.os.VibrationEffect;
import android.util.Size;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.core.animation.CycleInterpolator;
import androidx.core.animation.ObjectAnimator;
import com.android.settingslib.Utils;
import com.android.settingslib.widget.ActionBarShadowController;
import com.android.systemui.R$dimen;
import com.android.systemui.R$id;
import com.android.systemui.R$string;
import com.android.systemui.animation.Expandable;
import com.android.systemui.animation.Interpolators;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.common.ui.binder.IconViewBinder;
import com.android.systemui.keyguard.ui.binder.KeyguardBottomAreaViewBinder;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardBottomAreaViewModel;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardQuickAffordanceViewModel;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.statusbar.VibratorHelper;
import java.util.ArrayList;
import java.util.List;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.time.Duration;
import kotlin.time.DurationKt;
import kotlin.time.DurationUnit;
import kotlinx.coroutines.flow.MutableStateFlow;
import kotlinx.coroutines.flow.StateFlowKt;

/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/ui/binder/KeyguardBottomAreaViewBinder.class */
public final class KeyguardBottomAreaViewBinder {
    public static final KeyguardBottomAreaViewBinder INSTANCE = new KeyguardBottomAreaViewBinder();
    public static final float ShakeAnimationCycles;
    public static final long ShakeAnimationDuration;

    /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/ui/binder/KeyguardBottomAreaViewBinder$Binding.class */
    public interface Binding {
        List<ViewPropertyAnimator> getIndicationAreaAnimators();

        void onConfigurationChanged();

        boolean shouldConstrainToTopOfLockIcon();
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/ui/binder/KeyguardBottomAreaViewBinder$ConfigurationBasedDimensions.class */
    public static final class ConfigurationBasedDimensions {
        public final Size buttonSizePx;
        public final int defaultBurnInPreventionYOffsetPx;
        public final int indicationAreaPaddingPx;
        public final int indicationTextSizePx;

        public ConfigurationBasedDimensions(int i, int i2, int i3, Size size) {
            this.defaultBurnInPreventionYOffsetPx = i;
            this.indicationAreaPaddingPx = i2;
            this.indicationTextSizePx = i3;
            this.buttonSizePx = size;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj instanceof ConfigurationBasedDimensions) {
                ConfigurationBasedDimensions configurationBasedDimensions = (ConfigurationBasedDimensions) obj;
                return this.defaultBurnInPreventionYOffsetPx == configurationBasedDimensions.defaultBurnInPreventionYOffsetPx && this.indicationAreaPaddingPx == configurationBasedDimensions.indicationAreaPaddingPx && this.indicationTextSizePx == configurationBasedDimensions.indicationTextSizePx && Intrinsics.areEqual(this.buttonSizePx, configurationBasedDimensions.buttonSizePx);
            }
            return false;
        }

        public final Size getButtonSizePx() {
            return this.buttonSizePx;
        }

        public final int getDefaultBurnInPreventionYOffsetPx() {
            return this.defaultBurnInPreventionYOffsetPx;
        }

        public final int getIndicationAreaPaddingPx() {
            return this.indicationAreaPaddingPx;
        }

        public final int getIndicationTextSizePx() {
            return this.indicationTextSizePx;
        }

        public int hashCode() {
            return (((((Integer.hashCode(this.defaultBurnInPreventionYOffsetPx) * 31) + Integer.hashCode(this.indicationAreaPaddingPx)) * 31) + Integer.hashCode(this.indicationTextSizePx)) * 31) + this.buttonSizePx.hashCode();
        }

        public String toString() {
            int i = this.defaultBurnInPreventionYOffsetPx;
            int i2 = this.indicationAreaPaddingPx;
            int i3 = this.indicationTextSizePx;
            Size size = this.buttonSizePx;
            return "ConfigurationBasedDimensions(defaultBurnInPreventionYOffsetPx=" + i + ", indicationAreaPaddingPx=" + i2 + ", indicationTextSizePx=" + i3 + ", buttonSizePx=" + size + ")";
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/ui/binder/KeyguardBottomAreaViewBinder$OnClickListener.class */
    public static final class OnClickListener implements View.OnClickListener {
        public final FalsingManager falsingManager;
        public final KeyguardQuickAffordanceViewModel viewModel;

        public OnClickListener(KeyguardQuickAffordanceViewModel keyguardQuickAffordanceViewModel, FalsingManager falsingManager) {
            this.viewModel = keyguardQuickAffordanceViewModel;
            this.falsingManager = falsingManager;
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            if (this.falsingManager.isFalseTap(1) || this.viewModel.getConfigKey() == null) {
                return;
            }
            this.viewModel.getOnClicked().invoke(new KeyguardQuickAffordanceViewModel.OnClickedParameters(this.viewModel.getConfigKey(), Expandable.Companion.fromView(view)));
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/ui/binder/KeyguardBottomAreaViewBinder$OnTouchListener.class */
    public static final class OnTouchListener implements View.OnTouchListener {
        public static final Companion Companion = new Companion(null);
        public long downTimestamp;
        public ViewPropertyAnimator longPressAnimator;
        public final long longPressDurationMs = ViewConfiguration.getLongPressTimeout();
        public final Function1<Integer, Unit> messageDisplayer;
        public final VibratorHelper vibratorHelper;
        public final View view;
        public final KeyguardQuickAffordanceViewModel viewModel;

        /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/ui/binder/KeyguardBottomAreaViewBinder$OnTouchListener$Companion.class */
        public static final class Companion {
            public Companion() {
            }

            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }
        }

        /* JADX DEBUG: Multi-variable search result rejected for r7v0, resolved type: kotlin.jvm.functions.Function1<? super java.lang.Integer, kotlin.Unit> */
        /* JADX WARN: Multi-variable type inference failed */
        public OnTouchListener(View view, KeyguardQuickAffordanceViewModel keyguardQuickAffordanceViewModel, Function1<? super Integer, Unit> function1, VibratorHelper vibratorHelper) {
            this.view = view;
            this.viewModel = keyguardQuickAffordanceViewModel;
            this.messageDisplayer = function1;
            this.vibratorHelper = vibratorHelper;
        }

        /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.keyguard.ui.binder.KeyguardBottomAreaViewBinder$OnTouchListener$onTouch$1.run():void, com.android.systemui.keyguard.ui.binder.KeyguardBottomAreaViewBinder$OnTouchListener$onTouch$1.1.onClick(android.view.View):void] */
        public static final /* synthetic */ View access$getView$p(OnTouchListener onTouchListener) {
            return onTouchListener.view;
        }

        /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.keyguard.ui.binder.KeyguardBottomAreaViewBinder$OnTouchListener$onTouch$1.1.onClick(android.view.View):void] */
        public static final /* synthetic */ KeyguardQuickAffordanceViewModel access$getViewModel$p(OnTouchListener onTouchListener) {
            return onTouchListener.viewModel;
        }

        public static /* synthetic */ void cancel$default(OnTouchListener onTouchListener, Runnable runnable, int i, Object obj) {
            if ((i & 1) != 0) {
                runnable = null;
            }
            onTouchListener.cancel(runnable);
        }

        public final void cancel(Runnable runnable) {
            this.downTimestamp = 0L;
            ViewPropertyAnimator viewPropertyAnimator = this.longPressAnimator;
            if (viewPropertyAnimator != null) {
                viewPropertyAnimator.cancel();
            }
            this.longPressAnimator = null;
            this.view.animate().scaleX(1.0f).scaleY(1.0f).withEndAction(runnable);
        }

        @Override // android.view.View.OnTouchListener
        @SuppressLint({"ClickableViewAccessibility"})
        public boolean onTouch(View view, MotionEvent motionEvent) {
            Integer valueOf = motionEvent != null ? Integer.valueOf(motionEvent.getActionMasked()) : null;
            boolean z = false;
            if (valueOf == null || valueOf.intValue() != 0) {
                if (valueOf != null && valueOf.intValue() == 2) {
                    if (motionEvent.getHistorySize() > 0) {
                        double d = 2;
                        if (((float) Math.sqrt(((float) Math.pow(motionEvent.getY() - motionEvent.getHistoricalY(0), d)) + ((float) Math.pow(motionEvent.getX() - motionEvent.getHistoricalX(0), d)))) > ViewConfiguration.getTouchSlop()) {
                            cancel$default(this, null, 1, null);
                        }
                    }
                } else if (valueOf != null && valueOf.intValue() == 1) {
                    Runnable runnable = null;
                    if (System.currentTimeMillis() - this.downTimestamp < this.longPressDurationMs) {
                        runnable = new Runnable() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardBottomAreaViewBinder$OnTouchListener$onTouch$2
                            @Override // java.lang.Runnable
                            public final void run() {
                                Function1 function1;
                                View view2;
                                View view3;
                                VibratorHelper vibratorHelper;
                                function1 = KeyguardBottomAreaViewBinder.OnTouchListener.this.messageDisplayer;
                                function1.invoke(Integer.valueOf(R$string.keyguard_affordance_press_too_short));
                                view2 = KeyguardBottomAreaViewBinder.OnTouchListener.this.view;
                                float dimensionPixelSize = view2.getContext().getResources().getDimensionPixelSize(R$dimen.keyguard_affordance_shake_amplitude);
                                view3 = KeyguardBottomAreaViewBinder.OnTouchListener.this.view;
                                float f = -dimensionPixelSize;
                                float f2 = 2;
                                ObjectAnimator ofFloat = ObjectAnimator.ofFloat(view3, "translationX", f / f2, dimensionPixelSize / f2);
                                ofFloat.setDuration(Duration.getInWholeMilliseconds-impl(KeyguardBottomAreaViewBinder.ShakeAnimationDuration));
                                ofFloat.setInterpolator(new CycleInterpolator(KeyguardBottomAreaViewBinder.ShakeAnimationCycles));
                                ofFloat.start();
                                vibratorHelper = KeyguardBottomAreaViewBinder.OnTouchListener.this.vibratorHelper;
                                if (vibratorHelper != null) {
                                    vibratorHelper.vibrate(KeyguardBottomAreaViewBinder.Vibrations.INSTANCE.getShake());
                                }
                            }
                        };
                    }
                    cancel(runnable);
                } else if (valueOf != null && valueOf.intValue() == 3) {
                    cancel$default(this, null, 1, null);
                }
                z = true;
            } else if (this.viewModel.getConfigKey() != null) {
                this.downTimestamp = System.currentTimeMillis();
                this.longPressAnimator = this.view.animate().scaleX(1.5f).scaleY(1.5f).setDuration(this.longPressDurationMs).withEndAction(new Runnable() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardBottomAreaViewBinder$OnTouchListener$onTouch$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        View access$getView$p = KeyguardBottomAreaViewBinder.OnTouchListener.access$getView$p(KeyguardBottomAreaViewBinder.OnTouchListener.this);
                        final KeyguardBottomAreaViewBinder.OnTouchListener onTouchListener = KeyguardBottomAreaViewBinder.OnTouchListener.this;
                        access$getView$p.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardBottomAreaViewBinder$OnTouchListener$onTouch$1.1
                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view2) {
                                KeyguardBottomAreaViewBinder.OnTouchListener.access$getViewModel$p(KeyguardBottomAreaViewBinder.OnTouchListener.this).getOnClicked().invoke(new KeyguardQuickAffordanceViewModel.OnClickedParameters(KeyguardBottomAreaViewBinder.OnTouchListener.access$getViewModel$p(KeyguardBottomAreaViewBinder.OnTouchListener.this).getConfigKey(), Expandable.Companion.fromView(KeyguardBottomAreaViewBinder.OnTouchListener.access$getView$p(KeyguardBottomAreaViewBinder.OnTouchListener.this))));
                            }
                        });
                        KeyguardBottomAreaViewBinder.OnTouchListener.access$getView$p(KeyguardBottomAreaViewBinder.OnTouchListener.this).performClick();
                        KeyguardBottomAreaViewBinder.OnTouchListener.access$getView$p(KeyguardBottomAreaViewBinder.OnTouchListener.this).setOnClickListener(null);
                        KeyguardBottomAreaViewBinder.OnTouchListener.cancel$default(KeyguardBottomAreaViewBinder.OnTouchListener.this, null, 1, null);
                    }
                });
                z = true;
            }
            return z;
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/ui/binder/KeyguardBottomAreaViewBinder$Vibrations.class */
    public static final class Vibrations {
        public static final VibrationEffect Activated;
        public static final VibrationEffect Deactivated;
        public static final Vibrations INSTANCE = new Vibrations();
        public static final VibrationEffect Shake;

        static {
            VibrationEffect.Composition startComposition = VibrationEffect.startComposition();
            int i = (int) (((float) Duration.getInWholeMilliseconds-impl(KeyguardBottomAreaViewBinder.ShakeAnimationDuration)) / (KeyguardBottomAreaViewBinder.ShakeAnimationCycles * 2));
            int i2 = (int) KeyguardBottomAreaViewBinder.ShakeAnimationCycles;
            for (int i3 = 0; i3 < i2 * 2; i3++) {
                startComposition.addPrimitive(7, 0.3f, i);
            }
            Shake = startComposition.compose();
            Activated = VibrationEffect.startComposition().addPrimitive(7, 0.6f, 0).addPrimitive(4, 0.1f, 0).compose();
            Deactivated = VibrationEffect.startComposition().addPrimitive(7, 0.6f, 0).addPrimitive(6, 0.1f, 0).compose();
        }

        public final VibrationEffect getActivated() {
            return Activated;
        }

        public final VibrationEffect getDeactivated() {
            return Deactivated;
        }

        public final VibrationEffect getShake() {
            return Shake;
        }
    }

    static {
        Duration.Companion companion = Duration.Companion;
        ShakeAnimationDuration = DurationKt.toDuration(300, DurationUnit.MILLISECONDS);
        ShakeAnimationCycles = 5.0f;
    }

    public static final Binding bind(final ViewGroup viewGroup, final KeyguardBottomAreaViewModel keyguardBottomAreaViewModel, FalsingManager falsingManager, VibratorHelper vibratorHelper, Function1<? super Integer, Unit> function1) {
        final View requireViewById = viewGroup.requireViewById(R$id.keyguard_indication_area);
        final View findViewById = viewGroup.findViewById(R$id.ambient_indication_container);
        ImageView imageView = (ImageView) viewGroup.requireViewById(R$id.start_button);
        ImageView imageView2 = (ImageView) viewGroup.requireViewById(R$id.end_button);
        View requireViewById2 = viewGroup.requireViewById(R$id.overlay_container);
        TextView textView = (TextView) viewGroup.requireViewById(R$id.keyguard_indication_text);
        TextView textView2 = (TextView) viewGroup.requireViewById(R$id.keyguard_indication_text_bottom);
        viewGroup.setClipChildren(false);
        viewGroup.setClipToPadding(false);
        final MutableStateFlow MutableStateFlow = StateFlowKt.MutableStateFlow(INSTANCE.loadFromResources(viewGroup));
        RepeatWhenAttachedKt.repeatWhenAttached$default(viewGroup, null, new KeyguardBottomAreaViewBinder$bind$1(keyguardBottomAreaViewModel, imageView, falsingManager, function1, vibratorHelper, imageView2, requireViewById2, viewGroup, findViewById, requireViewById, MutableStateFlow, textView, textView2, null), 1, null);
        return new Binding() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardBottomAreaViewBinder$bind$2
            @Override // com.android.systemui.keyguard.ui.binder.KeyguardBottomAreaViewBinder.Binding
            public List<ViewPropertyAnimator> getIndicationAreaAnimators() {
                List<View> listOf = CollectionsKt__CollectionsKt.listOf(new View[]{requireViewById, findViewById});
                ArrayList arrayList = new ArrayList();
                for (View view : listOf) {
                    ViewPropertyAnimator animate = view != null ? view.animate() : null;
                    if (animate != null) {
                        arrayList.add(animate);
                    }
                }
                return arrayList;
            }

            @Override // com.android.systemui.keyguard.ui.binder.KeyguardBottomAreaViewBinder.Binding
            public void onConfigurationChanged() {
                KeyguardBottomAreaViewBinder.ConfigurationBasedDimensions loadFromResources;
                MutableStateFlow<KeyguardBottomAreaViewBinder.ConfigurationBasedDimensions> mutableStateFlow = MutableStateFlow;
                loadFromResources = KeyguardBottomAreaViewBinder.INSTANCE.loadFromResources(viewGroup);
                mutableStateFlow.setValue(loadFromResources);
            }

            @Override // com.android.systemui.keyguard.ui.binder.KeyguardBottomAreaViewBinder.Binding
            public boolean shouldConstrainToTopOfLockIcon() {
                return keyguardBottomAreaViewModel.shouldConstrainToTopOfLockIcon();
            }
        };
    }

    public final ConfigurationBasedDimensions loadFromResources(View view) {
        return new ConfigurationBasedDimensions(view.getResources().getDimensionPixelOffset(R$dimen.default_burn_in_prevention_offset), view.getResources().getDimensionPixelOffset(R$dimen.keyguard_indication_area_padding), view.getResources().getDimensionPixelSize(17105585), new Size(view.getResources().getDimensionPixelSize(R$dimen.keyguard_affordance_fixed_width), view.getResources().getDimensionPixelSize(R$dimen.keyguard_affordance_fixed_height)));
    }

    @SuppressLint({"ClickableViewAccessibility"})
    public final void updateButton(ImageView imageView, KeyguardQuickAffordanceViewModel keyguardQuickAffordanceViewModel, FalsingManager falsingManager, Function1<? super Integer, Unit> function1, VibratorHelper vibratorHelper) {
        ColorStateList colorStateList;
        if (!keyguardQuickAffordanceViewModel.isVisible()) {
            imageView.setVisibility(8);
            return;
        }
        if (!(imageView.getVisibility() == 0)) {
            imageView.setVisibility(0);
            if (keyguardQuickAffordanceViewModel.getAnimateReveal()) {
                imageView.setAlpha(ActionBarShadowController.ELEVATION_LOW);
                imageView.setTranslationY(imageView.getHeight() / 2.0f);
                imageView.animate().alpha(1.0f).translationY(ActionBarShadowController.ELEVATION_LOW).setInterpolator(Interpolators.LINEAR_OUT_SLOW_IN).setDuration(250L).start();
            }
        }
        IconViewBinder.INSTANCE.bind(keyguardQuickAffordanceViewModel.getIcon(), imageView);
        Drawable drawable = imageView.getDrawable();
        Animatable2 animatable2 = drawable instanceof Animatable2 ? (Animatable2) drawable : null;
        if (animatable2 != null) {
            Icon icon = keyguardQuickAffordanceViewModel.getIcon();
            Icon.Resource resource = icon instanceof Icon.Resource ? (Icon.Resource) icon : null;
            if (resource != null) {
                int res = resource.getRes();
                animatable2.start();
                if (Intrinsics.areEqual(imageView.getTag(), Integer.valueOf(res))) {
                    animatable2.stop();
                } else {
                    imageView.setTag(Integer.valueOf(res));
                }
            }
        }
        imageView.setActivated(keyguardQuickAffordanceViewModel.isActivated());
        imageView.getDrawable().setTint(Utils.getColorAttrDefaultColor(imageView.getContext(), keyguardQuickAffordanceViewModel.isActivated() ? 16842809 : 16842806));
        if (keyguardQuickAffordanceViewModel.isSelected()) {
            colorStateList = null;
        } else {
            colorStateList = Utils.getColorAttr(imageView.getContext(), keyguardQuickAffordanceViewModel.isActivated() ? 17956900 : 17956909);
        }
        imageView.setBackgroundTintList(colorStateList);
        imageView.setClickable(keyguardQuickAffordanceViewModel.isClickable());
        if (!keyguardQuickAffordanceViewModel.isClickable()) {
            imageView.setOnClickListener(null);
            imageView.setOnTouchListener(null);
        } else if (keyguardQuickAffordanceViewModel.getUseLongPress()) {
            imageView.setOnTouchListener(new OnTouchListener(imageView, keyguardQuickAffordanceViewModel, function1, vibratorHelper));
        } else if (falsingManager == null) {
            throw new IllegalStateException("Required value was null.".toString());
        } else {
            imageView.setOnClickListener(new OnClickListener(keyguardQuickAffordanceViewModel, falsingManager));
        }
        imageView.setSelected(keyguardQuickAffordanceViewModel.isSelected());
    }
}