package com.android.systemui.controls.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Dialog;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.Icon;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.StateListDrawable;
import android.service.controls.Control;
import android.service.controls.actions.ControlAction;
import android.service.controls.templates.ControlTemplate;
import android.service.controls.templates.RangeTemplate;
import android.service.controls.templates.StatelessTemplate;
import android.service.controls.templates.TemperatureControlTemplate;
import android.service.controls.templates.ThumbnailTemplate;
import android.service.controls.templates.ToggleRangeTemplate;
import android.service.controls.templates.ToggleTemplate;
import android.util.MathUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.internal.graphics.ColorUtils;
import com.android.settingslib.widget.ActionBarShadowController;
import com.android.systemui.R$color;
import com.android.systemui.R$fraction;
import com.android.systemui.R$id;
import com.android.systemui.R$string;
import com.android.systemui.animation.Interpolators;
import com.android.systemui.controls.ControlsMetricsLogger;
import com.android.systemui.controls.controller.ControlsController;
import com.android.systemui.util.concurrency.DelayableExecutor;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.SetsKt__SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: mainsysui33.jar:com/android/systemui/controls/ui/ControlViewHolder.class */
public final class ControlViewHolder {
    public final GradientDrawable baseLayer;
    public Behavior behavior;
    public final DelayableExecutor bgExecutor;
    public final ImageView chevronIcon;
    public final ClipDrawable clipLayer;
    public final Context context;
    public final ControlActionCoordinator controlActionCoordinator;
    public final ControlsController controlsController;
    public final ControlsMetricsLogger controlsMetricsLogger;
    public ControlWithState cws;
    public final ImageView icon;
    public boolean isLoading;
    public ControlAction lastAction;
    public Dialog lastChallengeDialog;
    public final ViewGroup layout;
    public CharSequence nextStatusText;
    public final Function0<Unit> onDialogCancel;
    public ValueAnimator stateAnimator;
    public final TextView status;
    public Animator statusAnimator;
    public final TextView subtitle;
    public final TextView title;
    public final float toggleBackgroundIntensity;
    public final DelayableExecutor uiExecutor;
    public final int uid;
    public boolean userInteractionInProgress;
    public Dialog visibleDialog;
    public static final Companion Companion = new Companion(null);
    public static final Set<Integer> FORCE_PANEL_DEVICES = SetsKt__SetsKt.setOf(new Integer[]{49, 50});
    public static final int[] ATTR_ENABLED = {16842910};
    public static final int[] ATTR_DISABLED = {-16842910};

    /* loaded from: mainsysui33.jar:com/android/systemui/controls/ui/ControlViewHolder$Companion.class */
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final Supplier<? extends Behavior> findBehaviorClass(int i, ControlTemplate controlTemplate, int i2) {
            return i != 1 ? new Supplier() { // from class: com.android.systemui.controls.ui.ControlViewHolder$Companion$findBehaviorClass$1
                /* JADX DEBUG: Method merged with bridge method */
                @Override // java.util.function.Supplier
                public final StatusBehavior get() {
                    return new StatusBehavior();
                }
            } : Intrinsics.areEqual(controlTemplate, ControlTemplate.NO_TEMPLATE) ? new Supplier() { // from class: com.android.systemui.controls.ui.ControlViewHolder$Companion$findBehaviorClass$2
                /* JADX DEBUG: Method merged with bridge method */
                @Override // java.util.function.Supplier
                public final TouchBehavior get() {
                    return new TouchBehavior();
                }
            } : controlTemplate instanceof ThumbnailTemplate ? new Supplier() { // from class: com.android.systemui.controls.ui.ControlViewHolder$Companion$findBehaviorClass$3
                /* JADX DEBUG: Method merged with bridge method */
                @Override // java.util.function.Supplier
                public final ThumbnailBehavior get() {
                    return new ThumbnailBehavior();
                }
            } : i2 == 50 ? new Supplier() { // from class: com.android.systemui.controls.ui.ControlViewHolder$Companion$findBehaviorClass$4
                /* JADX DEBUG: Method merged with bridge method */
                @Override // java.util.function.Supplier
                public final TouchBehavior get() {
                    return new TouchBehavior();
                }
            } : controlTemplate instanceof ToggleTemplate ? new Supplier() { // from class: com.android.systemui.controls.ui.ControlViewHolder$Companion$findBehaviorClass$5
                /* JADX DEBUG: Method merged with bridge method */
                @Override // java.util.function.Supplier
                public final ToggleBehavior get() {
                    return new ToggleBehavior();
                }
            } : controlTemplate instanceof StatelessTemplate ? new Supplier() { // from class: com.android.systemui.controls.ui.ControlViewHolder$Companion$findBehaviorClass$6
                /* JADX DEBUG: Method merged with bridge method */
                @Override // java.util.function.Supplier
                public final TouchBehavior get() {
                    return new TouchBehavior();
                }
            } : controlTemplate instanceof ToggleRangeTemplate ? new Supplier() { // from class: com.android.systemui.controls.ui.ControlViewHolder$Companion$findBehaviorClass$7
                /* JADX DEBUG: Method merged with bridge method */
                @Override // java.util.function.Supplier
                public final ToggleRangeBehavior get() {
                    return new ToggleRangeBehavior();
                }
            } : controlTemplate instanceof RangeTemplate ? new Supplier() { // from class: com.android.systemui.controls.ui.ControlViewHolder$Companion$findBehaviorClass$8
                /* JADX DEBUG: Method merged with bridge method */
                @Override // java.util.function.Supplier
                public final ToggleRangeBehavior get() {
                    return new ToggleRangeBehavior();
                }
            } : controlTemplate instanceof TemperatureControlTemplate ? new Supplier() { // from class: com.android.systemui.controls.ui.ControlViewHolder$Companion$findBehaviorClass$9
                /* JADX DEBUG: Method merged with bridge method */
                @Override // java.util.function.Supplier
                public final TemperatureControlBehavior get() {
                    return new TemperatureControlBehavior();
                }
            } : new Supplier() { // from class: com.android.systemui.controls.ui.ControlViewHolder$Companion$findBehaviorClass$10
                /* JADX DEBUG: Method merged with bridge method */
                @Override // java.util.function.Supplier
                public final DefaultBehavior get() {
                    return new DefaultBehavior();
                }
            };
        }
    }

    public ControlViewHolder(ViewGroup viewGroup, ControlsController controlsController, DelayableExecutor delayableExecutor, DelayableExecutor delayableExecutor2, ControlActionCoordinator controlActionCoordinator, ControlsMetricsLogger controlsMetricsLogger, int i) {
        this.layout = viewGroup;
        this.controlsController = controlsController;
        this.uiExecutor = delayableExecutor;
        this.bgExecutor = delayableExecutor2;
        this.controlActionCoordinator = controlActionCoordinator;
        this.controlsMetricsLogger = controlsMetricsLogger;
        this.uid = i;
        this.toggleBackgroundIntensity = viewGroup.getContext().getResources().getFraction(R$fraction.controls_toggle_bg_intensity, 1, 1);
        this.icon = (ImageView) viewGroup.requireViewById(R$id.icon);
        TextView textView = (TextView) viewGroup.requireViewById(R$id.status);
        this.status = textView;
        this.nextStatusText = "";
        this.title = (TextView) viewGroup.requireViewById(R$id.title);
        this.subtitle = (TextView) viewGroup.requireViewById(R$id.subtitle);
        this.chevronIcon = (ImageView) viewGroup.requireViewById(R$id.chevron_icon);
        this.context = viewGroup.getContext();
        this.onDialogCancel = new Function0<Unit>() { // from class: com.android.systemui.controls.ui.ControlViewHolder$onDialogCancel$1
            {
                super(0);
            }

            public /* bridge */ /* synthetic */ Object invoke() {
                m1868invoke();
                return Unit.INSTANCE;
            }

            /* renamed from: invoke  reason: collision with other method in class */
            public final void m1868invoke() {
                ControlViewHolder.access$setLastChallengeDialog$p(ControlViewHolder.this, null);
            }
        };
        LayerDrawable layerDrawable = (LayerDrawable) viewGroup.getBackground();
        layerDrawable.mutate();
        this.clipLayer = (ClipDrawable) layerDrawable.findDrawableByLayerId(R$id.clip_layer);
        this.baseLayer = (GradientDrawable) layerDrawable.findDrawableByLayerId(R$id.background);
        textView.setSelected(true);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.controls.ui.ControlViewHolder$startBackgroundAnimation$1$1.onAnimationUpdate(android.animation.ValueAnimator):void] */
    public static final /* synthetic */ void access$applyBackgroundChange(ControlViewHolder controlViewHolder, Drawable drawable, int i, int i2, int i3, float f) {
        controlViewHolder.applyBackgroundChange(drawable, i, i2, i3, f);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.controls.ui.ControlViewHolder$onDialogCancel$1.invoke():void] */
    public static final /* synthetic */ void access$setLastChallengeDialog$p(ControlViewHolder controlViewHolder, Dialog dialog) {
        controlViewHolder.lastChallengeDialog = dialog;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.controls.ui.ControlViewHolder$startBackgroundAnimation$1$2.onAnimationEnd(android.animation.Animator):void] */
    public static final /* synthetic */ void access$setStateAnimator$p(ControlViewHolder controlViewHolder, ValueAnimator valueAnimator) {
        controlViewHolder.stateAnimator = valueAnimator;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.controls.ui.ControlViewHolder$animateStatusChange$2$1.onAnimationEnd(android.animation.Animator):void] */
    public static final /* synthetic */ void access$setStatusAnimator$p(ControlViewHolder controlViewHolder, Animator animator) {
        controlViewHolder.statusAnimator = animator;
    }

    public static /* synthetic */ void applyRenderInfo$frameworks__base__packages__SystemUI__android_common__SystemUI_core$default(ControlViewHolder controlViewHolder, boolean z, int i, boolean z2, int i2, Object obj) {
        if ((i2 & 4) != 0) {
            z2 = true;
        }
        controlViewHolder.applyRenderInfo$frameworks__base__packages__SystemUI__android_common__SystemUI_core(z, i, z2);
    }

    public static /* synthetic */ Behavior bindBehavior$default(ControlViewHolder controlViewHolder, Behavior behavior, Supplier supplier, int i, int i2, Object obj) {
        if ((i2 & 4) != 0) {
            i = 0;
        }
        return controlViewHolder.bindBehavior(behavior, supplier, i);
    }

    public static /* synthetic */ void setStatusText$default(ControlViewHolder controlViewHolder, CharSequence charSequence, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = false;
        }
        controlViewHolder.setStatusText(charSequence, z);
    }

    public final void action(ControlAction controlAction) {
        this.lastAction = controlAction;
        this.controlsController.action(getCws().getComponentName(), getCws().getCi(), controlAction);
    }

    public final void actionResponse(int i) {
        this.controlActionCoordinator.enableActionOnTouch(getCws().getCi().getControlId());
        boolean z = this.lastChallengeDialog != null;
        if (i == 0) {
            this.lastChallengeDialog = null;
            setErrorStatus();
        } else if (i == 1) {
            this.lastChallengeDialog = null;
        } else if (i == 2) {
            this.lastChallengeDialog = null;
            setErrorStatus();
        } else if (i == 3) {
            Dialog createConfirmationDialog = ChallengeDialogs.INSTANCE.createConfirmationDialog(this, this.onDialogCancel);
            this.lastChallengeDialog = createConfirmationDialog;
            if (createConfirmationDialog != null) {
                createConfirmationDialog.show();
            }
        } else if (i == 4) {
            Dialog createPinDialog = ChallengeDialogs.INSTANCE.createPinDialog(this, false, z, this.onDialogCancel);
            this.lastChallengeDialog = createPinDialog;
            if (createPinDialog != null) {
                createPinDialog.show();
            }
        } else if (i != 5) {
        } else {
            Dialog createPinDialog2 = ChallengeDialogs.INSTANCE.createPinDialog(this, true, z, this.onDialogCancel);
            this.lastChallengeDialog = createPinDialog2;
            if (createPinDialog2 != null) {
                createPinDialog2.show();
            }
        }
    }

    public final void animateBackgroundChange(boolean z, boolean z2, int i) {
        List listOf;
        ColorStateList customColor;
        Resources resources = this.context.getResources();
        int i2 = R$color.control_default_background;
        int color = resources.getColor(i2, this.context.getTheme());
        if (z2) {
            Control control = getCws().getControl();
            listOf = CollectionsKt__CollectionsKt.listOf(new Integer[]{Integer.valueOf((control == null || (customColor = control.getCustomColor()) == null) ? this.context.getResources().getColor(i, this.context.getTheme()) : customColor.getColorForState(new int[]{16842910}, customColor.getDefaultColor())), 255});
        } else {
            listOf = CollectionsKt__CollectionsKt.listOf(new Integer[]{Integer.valueOf(this.context.getResources().getColor(i2, this.context.getTheme())), 0});
        }
        int intValue = ((Number) listOf.get(0)).intValue();
        int intValue2 = ((Number) listOf.get(1)).intValue();
        int i3 = color;
        if (this.behavior instanceof ToggleRangeBehavior) {
            i3 = ColorUtils.blendARGB(color, intValue, this.toggleBackgroundIntensity);
        }
        Drawable drawable = this.clipLayer.getDrawable();
        if (drawable != null) {
            this.clipLayer.setAlpha(0);
            ValueAnimator valueAnimator = this.stateAnimator;
            if (valueAnimator != null) {
                valueAnimator.cancel();
            }
            if (z) {
                startBackgroundAnimation(drawable, intValue2, intValue, i3);
            } else {
                applyBackgroundChange(drawable, intValue2, intValue, i3, 1.0f);
            }
        }
    }

    public final void animateStatusChange(boolean z, final Function0<Unit> function0) {
        Animator animator = this.statusAnimator;
        if (animator != null) {
            animator.cancel();
        }
        if (!z) {
            function0.invoke();
        } else if (this.isLoading) {
            function0.invoke();
            ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this.status, "alpha", 0.45f);
            ofFloat.setRepeatMode(2);
            ofFloat.setRepeatCount(-1);
            ofFloat.setDuration(500L);
            ofFloat.setInterpolator(Interpolators.LINEAR);
            ofFloat.setStartDelay(900L);
            ofFloat.start();
            this.statusAnimator = ofFloat;
        } else {
            ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(this.status, "alpha", ActionBarShadowController.ELEVATION_LOW);
            ofFloat2.setDuration(200L);
            Interpolator interpolator = Interpolators.LINEAR;
            ofFloat2.setInterpolator(interpolator);
            ofFloat2.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.controls.ui.ControlViewHolder$animateStatusChange$fadeOut$1$1
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator2) {
                    function0.invoke();
                }
            });
            ObjectAnimator ofFloat3 = ObjectAnimator.ofFloat(this.status, "alpha", 1.0f);
            ofFloat3.setDuration(200L);
            ofFloat3.setInterpolator(interpolator);
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playSequentially(ofFloat2, ofFloat3);
            animatorSet.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.controls.ui.ControlViewHolder$animateStatusChange$2$1
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator2) {
                    ControlViewHolder.this.getStatus().setAlpha(1.0f);
                    ControlViewHolder.access$setStatusAnimator$p(ControlViewHolder.this, null);
                }
            });
            animatorSet.start();
            this.statusAnimator = animatorSet;
        }
    }

    public final void applyBackgroundChange(Drawable drawable, int i, int i2, int i3, float f) {
        drawable.setAlpha(i);
        if (drawable instanceof GradientDrawable) {
            ((GradientDrawable) drawable).setColor(i2);
        }
        this.baseLayer.setColor(i3);
        this.layout.setAlpha(f);
    }

    public final void applyRenderInfo$frameworks__base__packages__SystemUI__android_common__SystemUI_core(final boolean z, int i, boolean z2) {
        final RenderInfo lookup = RenderInfo.Companion.lookup(this.context, getCws().getComponentName(), (getControlStatus() == 1 || getControlStatus() == 0) ? getDeviceType() : -1000, i);
        final ColorStateList colorStateList = this.context.getResources().getColorStateList(lookup.getForeground(), this.context.getTheme());
        final CharSequence charSequence = this.nextStatusText;
        final Control control = getCws().getControl();
        if (Intrinsics.areEqual(charSequence, this.status.getText())) {
            z2 = false;
        }
        animateStatusChange(z2, new Function0<Unit>() { // from class: com.android.systemui.controls.ui.ControlViewHolder$applyRenderInfo$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            public /* bridge */ /* synthetic */ Object invoke() {
                m1867invoke();
                return Unit.INSTANCE;
            }

            /* renamed from: invoke  reason: collision with other method in class */
            public final void m1867invoke() {
                ControlViewHolder.this.updateStatusRow$frameworks__base__packages__SystemUI__android_common__SystemUI_core(z, charSequence, lookup.getIcon(), colorStateList, control);
            }
        });
        animateBackgroundChange(z2, z, lookup.getEnabledBackground());
    }

    /* JADX WARN: Code restructure failed: missing block: B:14:0x0023, code lost:
        if (kotlin.jvm.internal.Intrinsics.areEqual(kotlin.jvm.internal.Reflection.getOrCreateKotlinClass(r5.getClass()), kotlin.jvm.internal.Reflection.getOrCreateKotlinClass(r0.getClass())) == false) goto L8;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Behavior bindBehavior(Behavior behavior, Supplier<? extends Behavior> supplier, int i) {
        Behavior behavior2;
        Behavior behavior3 = supplier.get();
        if (behavior != null) {
            behavior2 = behavior;
        }
        behavior3.initialize(this);
        this.layout.setAccessibilityDelegate(null);
        behavior2 = behavior3;
        behavior2.bind(getCws(), i);
        return behavior2;
    }

    public final void bindData(ControlWithState controlWithState, boolean z) {
        if (this.userInteractionInProgress) {
            return;
        }
        setCws(controlWithState);
        if (getControlStatus() == 0 || getControlStatus() == 2) {
            this.title.setText(controlWithState.getCi().getControlTitle());
            this.subtitle.setText(controlWithState.getCi().getControlSubtitle());
        } else {
            Control control = controlWithState.getControl();
            if (control != null) {
                this.title.setText(control.getTitle());
                this.subtitle.setText(control.getSubtitle());
                this.chevronIcon.setVisibility(usePanel() ? 0 : 4);
            }
        }
        if (controlWithState.getControl() != null) {
            this.layout.setClickable(true);
            this.layout.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.android.systemui.controls.ui.ControlViewHolder$bindData$2$1
                @Override // android.view.View.OnLongClickListener
                public final boolean onLongClick(View view) {
                    ControlViewHolder.this.getControlActionCoordinator().longPress(ControlViewHolder.this);
                    return true;
                }
            });
        }
        boolean z2 = this.isLoading;
        this.isLoading = false;
        this.behavior = bindBehavior$default(this, this.behavior, Companion.findBehaviorClass(getControlStatus(), getControlTemplate(), getDeviceType()), 0, 4, null);
        updateContentDescription();
        boolean z3 = false;
        if (z2) {
            z3 = false;
            if (!this.isLoading) {
                z3 = true;
            }
        }
        if (z3) {
            this.controlsMetricsLogger.refreshEnd(this, z);
        }
    }

    public final void dismiss() {
        Dialog dialog = this.lastChallengeDialog;
        if (dialog != null) {
            dialog.dismiss();
        }
        this.lastChallengeDialog = null;
        Dialog dialog2 = this.visibleDialog;
        if (dialog2 != null) {
            dialog2.dismiss();
        }
        this.visibleDialog = null;
    }

    public final DelayableExecutor getBgExecutor() {
        return this.bgExecutor;
    }

    public final ClipDrawable getClipLayer() {
        return this.clipLayer;
    }

    public final Context getContext() {
        return this.context;
    }

    public final ControlActionCoordinator getControlActionCoordinator() {
        return this.controlActionCoordinator;
    }

    public final int getControlStatus() {
        Control control = getCws().getControl();
        return control != null ? control.getStatus() : 0;
    }

    public final ControlTemplate getControlTemplate() {
        Control control = getCws().getControl();
        ControlTemplate controlTemplate = control != null ? control.getControlTemplate() : null;
        ControlTemplate controlTemplate2 = controlTemplate;
        if (controlTemplate == null) {
            controlTemplate2 = ControlTemplate.NO_TEMPLATE;
        }
        return controlTemplate2;
    }

    public final ControlWithState getCws() {
        ControlWithState controlWithState = this.cws;
        if (controlWithState != null) {
            return controlWithState;
        }
        return null;
    }

    public final int getDeviceType() {
        Control control = getCws().getControl();
        return control != null ? control.getDeviceType() : getCws().getCi().getDeviceType();
    }

    public final ControlAction getLastAction() {
        return this.lastAction;
    }

    public final ViewGroup getLayout() {
        return this.layout;
    }

    public final TextView getStatus() {
        return this.status;
    }

    public final TextView getSubtitle() {
        return this.subtitle;
    }

    public final TextView getTitle() {
        return this.title;
    }

    public final DelayableExecutor getUiExecutor() {
        return this.uiExecutor;
    }

    public final int getUid() {
        return this.uid;
    }

    public final void setCws(ControlWithState controlWithState) {
        this.cws = controlWithState;
    }

    public final void setEnabled(boolean z) {
        this.status.setEnabled(z);
        this.icon.setEnabled(z);
        this.chevronIcon.setEnabled(z);
    }

    public final void setErrorStatus() {
        final String string = this.context.getResources().getString(R$string.controls_error_failed);
        animateStatusChange(true, new Function0<Unit>() { // from class: com.android.systemui.controls.ui.ControlViewHolder$setErrorStatus$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            public /* bridge */ /* synthetic */ Object invoke() {
                m1869invoke();
                return Unit.INSTANCE;
            }

            /* renamed from: invoke  reason: collision with other method in class */
            public final void m1869invoke() {
                ControlViewHolder.this.setStatusText(string, true);
            }
        });
    }

    public final void setLoading(boolean z) {
        this.isLoading = z;
    }

    public final void setStatusText(CharSequence charSequence, boolean z) {
        if (z) {
            this.status.setAlpha(1.0f);
            this.status.setText(charSequence);
            updateContentDescription();
        }
        this.nextStatusText = charSequence;
    }

    public final void setStatusTextSize(float f) {
        this.status.setTextSize(0, f);
    }

    public final void setUserInteractionInProgress(boolean z) {
        this.userInteractionInProgress = z;
    }

    public final void setVisibleDialog(Dialog dialog) {
        this.visibleDialog = dialog;
    }

    public final void startBackgroundAnimation(final Drawable drawable, int i, final int i2, final int i3) {
        ColorStateList color;
        int defaultColor = (!(drawable instanceof GradientDrawable) || (color = ((GradientDrawable) drawable).getColor()) == null) ? i2 : color.getDefaultColor();
        ColorStateList color2 = this.baseLayer.getColor();
        int defaultColor2 = color2 != null ? color2.getDefaultColor() : i3;
        final float alpha = this.layout.getAlpha();
        ValueAnimator ofInt = ValueAnimator.ofInt(this.clipLayer.getAlpha(), i);
        final int i4 = defaultColor;
        final int i5 = defaultColor2;
        ofInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.controls.ui.ControlViewHolder$startBackgroundAnimation$1$1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                ControlViewHolder.access$applyBackgroundChange(this, drawable, ((Integer) valueAnimator.getAnimatedValue()).intValue(), ColorUtils.blendARGB(i4, i2, valueAnimator.getAnimatedFraction()), ColorUtils.blendARGB(i5, i3, valueAnimator.getAnimatedFraction()), MathUtils.lerp(alpha, 1.0f, valueAnimator.getAnimatedFraction()));
            }
        });
        ofInt.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.controls.ui.ControlViewHolder$startBackgroundAnimation$1$2
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                ControlViewHolder.access$setStateAnimator$p(ControlViewHolder.this, null);
            }
        });
        ofInt.setDuration(700L);
        ofInt.setInterpolator(Interpolators.CONTROL_STATE);
        ofInt.start();
        this.stateAnimator = ofInt;
    }

    public final void updateContentDescription() {
        ViewGroup viewGroup = this.layout;
        CharSequence text = this.title.getText();
        CharSequence text2 = this.subtitle.getText();
        CharSequence text3 = this.status.getText();
        viewGroup.setContentDescription(((Object) text) + " " + ((Object) text2) + " " + ((Object) text3));
    }

    public final void updateStatusRow$frameworks__base__packages__SystemUI__android_common__SystemUI_core(boolean z, CharSequence charSequence, Drawable drawable, ColorStateList colorStateList, Control control) {
        Unit unit;
        Icon customIcon;
        setEnabled(z);
        this.status.setText(charSequence);
        updateContentDescription();
        this.status.setTextColor(colorStateList);
        if (control == null || (customIcon = control.getCustomIcon()) == null) {
            unit = null;
        } else {
            this.icon.setImageIcon(customIcon);
            this.icon.setImageTintList(customIcon.getTintList());
            unit = Unit.INSTANCE;
        }
        if (unit == null) {
            if (drawable instanceof StateListDrawable) {
                if (this.icon.getDrawable() == null || !(this.icon.getDrawable() instanceof StateListDrawable)) {
                    this.icon.setImageDrawable(drawable);
                }
                this.icon.setImageState(z ? ATTR_ENABLED : ATTR_DISABLED, true);
            } else {
                this.icon.setImageDrawable(drawable);
            }
            if (getDeviceType() != 52) {
                this.icon.setImageTintList(colorStateList);
            }
        }
        this.chevronIcon.setImageTintList(this.icon.getImageTintList());
    }

    public final boolean usePanel() {
        return FORCE_PANEL_DEVICES.contains(Integer.valueOf(getDeviceType())) || Intrinsics.areEqual(getControlTemplate(), ControlTemplate.NO_TEMPLATE);
    }
}