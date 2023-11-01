package com.android.systemui.controls.ui;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.service.controls.Control;
import android.service.controls.templates.ControlTemplate;
import android.service.controls.templates.StatelessTemplate;
import android.view.View;
import com.android.systemui.R$id;
import com.android.systemui.util.concurrency.DelayableExecutor;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: mainsysui33.jar:com/android/systemui/controls/ui/TouchBehavior.class */
public final class TouchBehavior implements Behavior {
    public static final Companion Companion = new Companion(null);
    public Drawable clipLayer;
    public Control control;
    public ControlViewHolder cvh;
    public int lastColorOffset;
    public boolean statelessTouch;
    public ControlTemplate template;

    /* loaded from: mainsysui33.jar:com/android/systemui/controls/ui/TouchBehavior$Companion.class */
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    @Override // com.android.systemui.controls.ui.Behavior
    public void bind(ControlWithState controlWithState, int i) {
        Control control = controlWithState.getControl();
        Intrinsics.checkNotNull(control);
        setControl(control);
        this.lastColorOffset = i;
        int i2 = 0;
        ControlViewHolder.setStatusText$default(getCvh(), getControl().getStatusText(), false, 2, null);
        setTemplate(getControl().getControlTemplate());
        setClipLayer(((LayerDrawable) getCvh().getLayout().getBackground()).findDrawableByLayerId(R$id.clip_layer));
        Drawable clipLayer = getClipLayer();
        if (getEnabled()) {
            i2 = 10000;
        }
        clipLayer.setLevel(i2);
        ControlViewHolder.applyRenderInfo$frameworks__base__packages__SystemUI__android_common__SystemUI_core$default(getCvh(), getEnabled(), i, false, 4, null);
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

    public final boolean getEnabled() {
        return this.lastColorOffset > 0 || this.statelessTouch;
    }

    public final ControlTemplate getTemplate() {
        ControlTemplate controlTemplate = this.template;
        if (controlTemplate != null) {
            return controlTemplate;
        }
        return null;
    }

    @Override // com.android.systemui.controls.ui.Behavior
    public void initialize(final ControlViewHolder controlViewHolder) {
        setCvh(controlViewHolder);
        controlViewHolder.getLayout().setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.controls.ui.TouchBehavior$initialize$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                boolean enabled;
                int i;
                ControlViewHolder.this.getControlActionCoordinator().touch(ControlViewHolder.this, this.getTemplate().getTemplateId(), this.getControl());
                if (this.getTemplate() instanceof StatelessTemplate) {
                    this.statelessTouch = true;
                    ControlViewHolder controlViewHolder2 = ControlViewHolder.this;
                    enabled = this.getEnabled();
                    i = this.lastColorOffset;
                    ControlViewHolder.applyRenderInfo$frameworks__base__packages__SystemUI__android_common__SystemUI_core$default(controlViewHolder2, enabled, i, false, 4, null);
                    DelayableExecutor uiExecutor = ControlViewHolder.this.getUiExecutor();
                    final TouchBehavior touchBehavior = this;
                    final ControlViewHolder controlViewHolder3 = ControlViewHolder.this;
                    uiExecutor.executeDelayed(new Runnable() { // from class: com.android.systemui.controls.ui.TouchBehavior$initialize$1.1
                        @Override // java.lang.Runnable
                        public final void run() {
                            boolean enabled2;
                            int i2;
                            TouchBehavior.this.statelessTouch = false;
                            ControlViewHolder controlViewHolder4 = controlViewHolder3;
                            enabled2 = TouchBehavior.this.getEnabled();
                            i2 = TouchBehavior.this.lastColorOffset;
                            ControlViewHolder.applyRenderInfo$frameworks__base__packages__SystemUI__android_common__SystemUI_core$default(controlViewHolder4, enabled2, i2, false, 4, null);
                        }
                    }, 3000L);
                }
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

    public final void setTemplate(ControlTemplate controlTemplate) {
        this.template = controlTemplate;
    }
}