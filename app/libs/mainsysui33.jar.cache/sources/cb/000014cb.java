package com.android.systemui.controls.ui;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.service.controls.Control;
import android.service.controls.templates.ControlTemplate;
import android.service.controls.templates.TemperatureControlTemplate;
import android.service.controls.templates.ToggleTemplate;
import android.util.Log;
import android.view.View;
import com.android.systemui.R$id;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: mainsysui33.jar:com/android/systemui/controls/ui/ToggleBehavior.class */
public final class ToggleBehavior implements Behavior {
    public Drawable clipLayer;
    public Control control;
    public ControlViewHolder cvh;
    public ToggleTemplate template;

    @Override // com.android.systemui.controls.ui.Behavior
    public void bind(ControlWithState controlWithState, int i) {
        ToggleTemplate toggleTemplate;
        Control control = controlWithState.getControl();
        Intrinsics.checkNotNull(control);
        setControl(control);
        ControlViewHolder.setStatusText$default(getCvh(), getControl().getStatusText(), false, 2, null);
        ControlTemplate controlTemplate = getControl().getControlTemplate();
        if (controlTemplate instanceof ToggleTemplate) {
            toggleTemplate = (ToggleTemplate) controlTemplate;
        } else if (!(controlTemplate instanceof TemperatureControlTemplate)) {
            Log.e("ControlsUiController", "Unsupported template type: " + controlTemplate);
            return;
        } else {
            toggleTemplate = (ToggleTemplate) ((TemperatureControlTemplate) controlTemplate).getTemplate();
        }
        setTemplate(toggleTemplate);
        setClipLayer(((LayerDrawable) getCvh().getLayout().getBackground()).findDrawableByLayerId(R$id.clip_layer));
        getClipLayer().setLevel(10000);
        ControlViewHolder.applyRenderInfo$frameworks__base__packages__SystemUI__android_common__SystemUI_core$default(getCvh(), getTemplate().isChecked(), i, false, 4, null);
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

    public final ToggleTemplate getTemplate() {
        ToggleTemplate toggleTemplate = this.template;
        if (toggleTemplate != null) {
            return toggleTemplate;
        }
        return null;
    }

    @Override // com.android.systemui.controls.ui.Behavior
    public void initialize(final ControlViewHolder controlViewHolder) {
        setCvh(controlViewHolder);
        controlViewHolder.getLayout().setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.controls.ui.ToggleBehavior$initialize$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ControlViewHolder.this.getControlActionCoordinator().toggle(ControlViewHolder.this, this.getTemplate().getTemplateId(), this.getTemplate().isChecked());
            }
        });
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

    public final void setTemplate(ToggleTemplate toggleTemplate) {
        this.template = toggleTemplate;
    }
}