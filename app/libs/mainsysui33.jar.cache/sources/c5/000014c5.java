package com.android.systemui.controls.ui;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.service.controls.Control;
import android.service.controls.templates.ControlTemplate;
import android.service.controls.templates.TemperatureControlTemplate;
import android.view.View;
import com.android.systemui.R$id;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: mainsysui33.jar:com/android/systemui/controls/ui/TemperatureControlBehavior.class */
public final class TemperatureControlBehavior implements Behavior {
    public Drawable clipLayer;
    public Control control;
    public ControlViewHolder cvh;
    public Behavior subBehavior;

    @Override // com.android.systemui.controls.ui.Behavior
    public void bind(ControlWithState controlWithState, int i) {
        Control control = controlWithState.getControl();
        Intrinsics.checkNotNull(control);
        setControl(control);
        int i2 = 0;
        ControlViewHolder.setStatusText$default(getCvh(), getControl().getStatusText(), false, 2, null);
        setClipLayer(((LayerDrawable) getCvh().getLayout().getBackground()).findDrawableByLayerId(R$id.clip_layer));
        final TemperatureControlTemplate temperatureControlTemplate = (TemperatureControlTemplate) getControl().getControlTemplate();
        int currentActiveMode = temperatureControlTemplate.getCurrentActiveMode();
        ControlTemplate template = temperatureControlTemplate.getTemplate();
        if (!Intrinsics.areEqual(template, ControlTemplate.getNoTemplateObject()) && !Intrinsics.areEqual(template, ControlTemplate.getErrorTemplate())) {
            this.subBehavior = getCvh().bindBehavior(this.subBehavior, ControlViewHolder.Companion.findBehaviorClass(getControl().getStatus(), template, getControl().getDeviceType()), currentActiveMode);
            return;
        }
        boolean z = (currentActiveMode == 0 || currentActiveMode == 1) ? false : true;
        Drawable clipLayer = getClipLayer();
        if (z) {
            i2 = 10000;
        }
        clipLayer.setLevel(i2);
        ControlViewHolder.applyRenderInfo$frameworks__base__packages__SystemUI__android_common__SystemUI_core$default(getCvh(), z, currentActiveMode, false, 4, null);
        getCvh().getLayout().setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.controls.ui.TemperatureControlBehavior$bind$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                TemperatureControlBehavior.this.getCvh().getControlActionCoordinator().touch(TemperatureControlBehavior.this.getCvh(), temperatureControlTemplate.getTemplateId(), TemperatureControlBehavior.this.getControl());
            }
        });
    }

    public final Drawable getClipLayer() {
        Drawable drawable = this.clipLayer;
        if (drawable != null) {
            return drawable;
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

    @Override // com.android.systemui.controls.ui.Behavior
    public void initialize(ControlViewHolder controlViewHolder) {
        setCvh(controlViewHolder);
    }

    public final void setClipLayer(Drawable drawable) {
        this.clipLayer = drawable;
    }

    public final void setControl(Control control) {
        this.control = control;
    }

    public final void setCvh(ControlViewHolder controlViewHolder) {
        this.cvh = controlViewHolder;
    }
}