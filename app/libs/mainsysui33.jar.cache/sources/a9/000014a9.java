package com.android.systemui.controls.ui;

import android.service.controls.Control;

/* loaded from: mainsysui33.jar:com/android/systemui/controls/ui/DefaultBehavior.class */
public final class DefaultBehavior implements Behavior {
    public ControlViewHolder cvh;

    @Override // com.android.systemui.controls.ui.Behavior
    public void bind(ControlWithState controlWithState, int i) {
        ControlViewHolder cvh = getCvh();
        Control control = controlWithState.getControl();
        CharSequence statusText = control != null ? control.getStatusText() : null;
        CharSequence charSequence = statusText;
        if (statusText == null) {
            charSequence = "";
        }
        ControlViewHolder.setStatusText$default(cvh, charSequence, false, 2, null);
        ControlViewHolder.applyRenderInfo$frameworks__base__packages__SystemUI__android_common__SystemUI_core$default(getCvh(), false, i, false, 4, null);
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

    public final void setCvh(ControlViewHolder controlViewHolder) {
        this.cvh = controlViewHolder;
    }
}