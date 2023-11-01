package com.android.systemui.controls.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.service.controls.Control;
import android.service.controls.templates.ControlTemplate;
import android.service.controls.templates.RangeTemplate;
import android.service.controls.templates.TemperatureControlTemplate;
import android.service.controls.templates.ToggleRangeTemplate;
import android.util.Log;
import android.util.MathUtils;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import com.android.settingslib.widget.ActionBarShadowController;
import com.android.systemui.R$dimen;
import com.android.systemui.R$id;
import com.android.systemui.animation.Interpolators;
import java.util.Arrays;
import java.util.IllegalFormatException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;

/* loaded from: mainsysui33.jar:com/android/systemui/controls/ui/ToggleRangeBehavior.class */
public final class ToggleRangeBehavior implements Behavior {
    public static final Companion Companion = new Companion(null);
    public Drawable clipLayer;
    public int colorOffset;
    public Context context;
    public Control control;
    public ControlViewHolder cvh;
    public boolean isChecked;
    public boolean isToggleable;
    public ValueAnimator rangeAnimator;
    public RangeTemplate rangeTemplate;
    public String templateId;
    public CharSequence currentStatusText = "";
    public String currentRangeValue = "";

    /* loaded from: mainsysui33.jar:com/android/systemui/controls/ui/ToggleRangeBehavior$Companion.class */
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/controls/ui/ToggleRangeBehavior$ToggleRangeGestureListener.class */
    public final class ToggleRangeGestureListener extends GestureDetector.SimpleOnGestureListener {
        public boolean isDragging;
        public final View v;

        public ToggleRangeGestureListener(View view) {
            ToggleRangeBehavior.this = r4;
            this.v = view;
        }

        public final boolean isDragging() {
            return this.isDragging;
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
        public boolean onDown(MotionEvent motionEvent) {
            return true;
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
        public void onLongPress(MotionEvent motionEvent) {
            if (this.isDragging) {
                return;
            }
            ToggleRangeBehavior.this.getCvh().getControlActionCoordinator().longPress(ToggleRangeBehavior.this.getCvh());
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
        public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
            if (!this.isDragging) {
                this.v.getParent().requestDisallowInterceptTouchEvent(true);
                ToggleRangeBehavior.this.beginUpdateRange();
                this.isDragging = true;
            }
            int width = (int) (10000 * ((-f) / this.v.getWidth()));
            ToggleRangeBehavior toggleRangeBehavior = ToggleRangeBehavior.this;
            toggleRangeBehavior.updateRange(toggleRangeBehavior.getClipLayer().getLevel() + width, true, true);
            return true;
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
        public boolean onSingleTapUp(MotionEvent motionEvent) {
            if (ToggleRangeBehavior.this.isToggleable()) {
                ToggleRangeBehavior.this.getCvh().getControlActionCoordinator().toggle(ToggleRangeBehavior.this.getCvh(), ToggleRangeBehavior.this.getTemplateId(), ToggleRangeBehavior.this.isChecked());
                return true;
            }
            return false;
        }

        public final void setDragging(boolean z) {
            this.isDragging = z;
        }
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.controls.ui.ToggleRangeBehavior$updateRange$1$2.onAnimationEnd(android.animation.Animator):void] */
    public static final /* synthetic */ void access$setRangeAnimator$p(ToggleRangeBehavior toggleRangeBehavior, ValueAnimator valueAnimator) {
        toggleRangeBehavior.rangeAnimator = valueAnimator;
    }

    public final void beginUpdateRange() {
        getCvh().setUserInteractionInProgress(true);
        getCvh().setStatusTextSize(getContext().getResources().getDimensionPixelSize(R$dimen.control_status_expanded));
    }

    @Override // com.android.systemui.controls.ui.Behavior
    public void bind(ControlWithState controlWithState, int i) {
        Control control = controlWithState.getControl();
        Intrinsics.checkNotNull(control);
        setControl(control);
        this.colorOffset = i;
        this.currentStatusText = getControl().getStatusText();
        getCvh().getLayout().setOnLongClickListener(null);
        setClipLayer(((LayerDrawable) getCvh().getLayout().getBackground()).findDrawableByLayerId(R$id.clip_layer));
        ControlTemplate controlTemplate = getControl().getControlTemplate();
        if (setupTemplate(controlTemplate)) {
            setTemplateId(controlTemplate.getTemplateId());
            updateRange(rangeToLevelValue(getRangeTemplate().getCurrentValue()), this.isChecked, false);
            ControlViewHolder.applyRenderInfo$frameworks__base__packages__SystemUI__android_common__SystemUI_core$default(getCvh(), this.isChecked, i, false, 4, null);
            getCvh().getLayout().setAccessibilityDelegate(new View.AccessibilityDelegate() { // from class: com.android.systemui.controls.ui.ToggleRangeBehavior$bind$1
                @Override // android.view.View.AccessibilityDelegate
                public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
                    float levelToRangeValue;
                    float levelToRangeValue2;
                    float levelToRangeValue3;
                    super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
                    int i2 = 0;
                    levelToRangeValue = ToggleRangeBehavior.this.levelToRangeValue(0);
                    ToggleRangeBehavior toggleRangeBehavior = ToggleRangeBehavior.this;
                    levelToRangeValue2 = toggleRangeBehavior.levelToRangeValue(toggleRangeBehavior.getClipLayer().getLevel());
                    levelToRangeValue3 = ToggleRangeBehavior.this.levelToRangeValue(10000);
                    double stepValue = ToggleRangeBehavior.this.getRangeTemplate().getStepValue();
                    if (stepValue == Math.floor(stepValue)) {
                        i2 = 1;
                    }
                    if (ToggleRangeBehavior.this.isChecked()) {
                        accessibilityNodeInfo.setRangeInfo(AccessibilityNodeInfo.RangeInfo.obtain(i2 ^ 1, levelToRangeValue, levelToRangeValue3, levelToRangeValue2));
                    }
                    accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_SET_PROGRESS);
                }

                @Override // android.view.View.AccessibilityDelegate
                public boolean onRequestSendAccessibilityEvent(ViewGroup viewGroup, View view, AccessibilityEvent accessibilityEvent) {
                    return true;
                }

                @Override // android.view.View.AccessibilityDelegate
                public boolean performAccessibilityAction(View view, int i2, Bundle bundle) {
                    int rangeToLevelValue;
                    boolean z;
                    boolean z2 = false;
                    if (i2 == 16) {
                        if (ToggleRangeBehavior.this.isToggleable()) {
                            ToggleRangeBehavior.this.getCvh().getControlActionCoordinator().toggle(ToggleRangeBehavior.this.getCvh(), ToggleRangeBehavior.this.getTemplateId(), ToggleRangeBehavior.this.isChecked());
                            z = true;
                        }
                        z = false;
                    } else {
                        if (i2 == 32) {
                            ToggleRangeBehavior.this.getCvh().getControlActionCoordinator().longPress(ToggleRangeBehavior.this.getCvh());
                        } else {
                            if (i2 == AccessibilityNodeInfo.AccessibilityAction.ACTION_SET_PROGRESS.getId() && bundle != null && bundle.containsKey("android.view.accessibility.action.ARGUMENT_PROGRESS_VALUE")) {
                                rangeToLevelValue = ToggleRangeBehavior.this.rangeToLevelValue(bundle.getFloat("android.view.accessibility.action.ARGUMENT_PROGRESS_VALUE"));
                                ToggleRangeBehavior toggleRangeBehavior = ToggleRangeBehavior.this;
                                toggleRangeBehavior.updateRange(rangeToLevelValue, toggleRangeBehavior.isChecked(), true);
                                ToggleRangeBehavior.this.endUpdateRange();
                            }
                            z = false;
                        }
                        z = true;
                    }
                    if (z || super.performAccessibilityAction(view, i2, bundle)) {
                        z2 = true;
                    }
                    return z2;
                }
            });
        }
    }

    public final void endUpdateRange() {
        getCvh().setStatusTextSize(getContext().getResources().getDimensionPixelSize(R$dimen.control_status_normal));
        ControlViewHolder cvh = getCvh();
        CharSequence charSequence = this.currentStatusText;
        String str = this.currentRangeValue;
        cvh.setStatusText(((Object) charSequence) + " " + str, true);
        getCvh().getControlActionCoordinator().setValue(getCvh(), getRangeTemplate().getTemplateId(), findNearestStep(levelToRangeValue(getClipLayer().getLevel())));
        getCvh().setUserInteractionInProgress(false);
    }

    public final float findNearestStep(float f) {
        float minValue = getRangeTemplate().getMinValue();
        float f2 = Float.MAX_VALUE;
        while (true) {
            float f3 = f2;
            if (minValue > getRangeTemplate().getMaxValue()) {
                return getRangeTemplate().getMaxValue();
            }
            float abs = Math.abs(f - minValue);
            if (abs >= f3) {
                return minValue - getRangeTemplate().getStepValue();
            }
            minValue += getRangeTemplate().getStepValue();
            f2 = abs;
        }
    }

    public final String format(String str, String str2, float f) {
        String format;
        try {
            StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
            format = String.format(str, Arrays.copyOf(new Object[]{Float.valueOf(findNearestStep(f))}, 1));
        } catch (IllegalFormatException e) {
            Log.w("ControlsUiController", "Illegal format in range template", e);
            format = Intrinsics.areEqual(str2, "") ? "" : format(str2, "", f);
        }
        return format;
    }

    public final Drawable getClipLayer() {
        Drawable drawable = this.clipLayer;
        if (drawable != null) {
            return drawable;
        }
        return null;
    }

    public final Context getContext() {
        Context context = this.context;
        if (context != null) {
            return context;
        }
        return null;
    }

    public final Control getControl() {
        Control control = this.control;
        if (control != null) {
            return control;
        }
        return null;
    }

    public final ControlViewHolder getCvh() {
        ControlViewHolder controlViewHolder = this.cvh;
        if (controlViewHolder != null) {
            return controlViewHolder;
        }
        return null;
    }

    public final RangeTemplate getRangeTemplate() {
        RangeTemplate rangeTemplate = this.rangeTemplate;
        if (rangeTemplate != null) {
            return rangeTemplate;
        }
        return null;
    }

    public final String getTemplateId() {
        String str = this.templateId;
        if (str != null) {
            return str;
        }
        return null;
    }

    @Override // com.android.systemui.controls.ui.Behavior
    public void initialize(ControlViewHolder controlViewHolder) {
        setCvh(controlViewHolder);
        setContext(controlViewHolder.getContext());
        final ToggleRangeGestureListener toggleRangeGestureListener = new ToggleRangeGestureListener(controlViewHolder.getLayout());
        final GestureDetector gestureDetector = new GestureDetector(getContext(), toggleRangeGestureListener);
        controlViewHolder.getLayout().setOnTouchListener(new View.OnTouchListener() { // from class: com.android.systemui.controls.ui.ToggleRangeBehavior$initialize$1
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                if (!gestureDetector.onTouchEvent(motionEvent) && motionEvent.getAction() == 1 && toggleRangeGestureListener.isDragging()) {
                    view.getParent().requestDisallowInterceptTouchEvent(false);
                    toggleRangeGestureListener.setDragging(false);
                    this.endUpdateRange();
                    return false;
                }
                return false;
            }
        });
    }

    public final boolean isChecked() {
        return this.isChecked;
    }

    public final boolean isToggleable() {
        return this.isToggleable;
    }

    public final float levelToRangeValue(int i) {
        return MathUtils.constrainedMap(getRangeTemplate().getMinValue(), getRangeTemplate().getMaxValue(), (float) ActionBarShadowController.ELEVATION_LOW, 10000.0f, i);
    }

    public final int rangeToLevelValue(float f) {
        return (int) MathUtils.constrainedMap((float) ActionBarShadowController.ELEVATION_LOW, 10000.0f, getRangeTemplate().getMinValue(), getRangeTemplate().getMaxValue(), f);
    }

    public final void setClipLayer(Drawable drawable) {
        this.clipLayer = drawable;
    }

    public final void setContext(Context context) {
        this.context = context;
    }

    public final void setControl(Control control) {
        this.control = control;
    }

    public final void setCvh(ControlViewHolder controlViewHolder) {
        this.cvh = controlViewHolder;
    }

    public final void setRangeTemplate(RangeTemplate rangeTemplate) {
        this.rangeTemplate = rangeTemplate;
    }

    public final void setTemplateId(String str) {
        this.templateId = str;
    }

    public final void setup(RangeTemplate rangeTemplate) {
        setRangeTemplate(rangeTemplate);
        this.isChecked = !(getRangeTemplate().getCurrentValue() == getRangeTemplate().getMinValue());
    }

    public final void setup(ToggleRangeTemplate toggleRangeTemplate) {
        setRangeTemplate(toggleRangeTemplate.getRange());
        this.isToggleable = true;
        this.isChecked = toggleRangeTemplate.isChecked();
    }

    public final boolean setupTemplate(ControlTemplate controlTemplate) {
        boolean z = true;
        if (controlTemplate instanceof ToggleRangeTemplate) {
            setup((ToggleRangeTemplate) controlTemplate);
        } else if (controlTemplate instanceof RangeTemplate) {
            setup((RangeTemplate) controlTemplate);
        } else if (controlTemplate instanceof TemperatureControlTemplate) {
            z = setupTemplate(((TemperatureControlTemplate) controlTemplate).getTemplate());
        } else {
            Log.e("ControlsUiController", "Unsupported template type: " + controlTemplate);
            z = false;
        }
        return z;
    }

    public final void updateRange(int i, boolean z, boolean z2) {
        int max = Math.max(0, Math.min(10000, i));
        if (getClipLayer().getLevel() == 0 && max > 0) {
            getCvh().applyRenderInfo$frameworks__base__packages__SystemUI__android_common__SystemUI_core(z, this.colorOffset, false);
        }
        ValueAnimator valueAnimator = this.rangeAnimator;
        if (valueAnimator != null) {
            valueAnimator.cancel();
        }
        if (z2) {
            boolean z3 = max == 0 || max == 10000;
            if (getClipLayer().getLevel() != max) {
                getCvh().getControlActionCoordinator().drag(z3);
                getClipLayer().setLevel(max);
            }
        } else if (max != getClipLayer().getLevel()) {
            ValueAnimator ofInt = ValueAnimator.ofInt(getCvh().getClipLayer().getLevel(), max);
            ofInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.controls.ui.ToggleRangeBehavior$updateRange$1$1
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                    ToggleRangeBehavior.this.getCvh().getClipLayer().setLevel(((Integer) valueAnimator2.getAnimatedValue()).intValue());
                }
            });
            ofInt.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.controls.ui.ToggleRangeBehavior$updateRange$1$2
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    ToggleRangeBehavior.access$setRangeAnimator$p(ToggleRangeBehavior.this, null);
                }
            });
            ofInt.setDuration(700L);
            ofInt.setInterpolator(Interpolators.CONTROL_STATE);
            ofInt.start();
            this.rangeAnimator = ofInt;
        }
        if (!z) {
            ControlViewHolder.setStatusText$default(getCvh(), this.currentStatusText, false, 2, null);
            return;
        }
        this.currentRangeValue = format(getRangeTemplate().getFormatString().toString(), "%.1f", levelToRangeValue(max));
        if (z2) {
            getCvh().setStatusText(this.currentRangeValue, true);
            return;
        }
        ControlViewHolder cvh = getCvh();
        CharSequence charSequence = this.currentStatusText;
        String str = this.currentRangeValue;
        ControlViewHolder.setStatusText$default(cvh, ((Object) charSequence) + " " + str, false, 2, null);
    }
}