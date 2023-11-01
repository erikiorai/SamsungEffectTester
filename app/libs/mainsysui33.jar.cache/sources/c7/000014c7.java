package com.android.systemui.controls.ui;

import android.graphics.BlendMode;
import android.graphics.BlendModeColorFilter;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.service.controls.Control;
import android.service.controls.templates.ThumbnailTemplate;
import android.util.TypedValue;
import android.view.View;
import com.android.settingslib.widget.ActionBarShadowController;
import com.android.systemui.R$color;
import com.android.systemui.R$dimen;
import com.android.systemui.R$id;
import com.android.systemui.util.concurrency.DelayableExecutor;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: mainsysui33.jar:com/android/systemui/controls/ui/ThumbnailBehavior.class */
public final class ThumbnailBehavior implements Behavior {
    public Control control;
    public ControlViewHolder cvh;
    public int shadowColor;
    public float shadowOffsetX;
    public float shadowOffsetY;
    public float shadowRadius;
    public ThumbnailTemplate template;

    @Override // com.android.systemui.controls.ui.Behavior
    public void bind(ControlWithState controlWithState, final int i) {
        Control control = controlWithState.getControl();
        Intrinsics.checkNotNull(control);
        setControl(control);
        ControlViewHolder.setStatusText$default(getCvh(), getControl().getStatusText(), false, 2, null);
        setTemplate((ThumbnailTemplate) getControl().getControlTemplate());
        final ClipDrawable clipDrawable = (ClipDrawable) ((LayerDrawable) getCvh().getLayout().getBackground()).findDrawableByLayerId(R$id.clip_layer);
        clipDrawable.setLevel(getEnabled() ? 10000 : 0);
        if (getTemplate().isActive()) {
            getCvh().getTitle().setVisibility(4);
            getCvh().getSubtitle().setVisibility(4);
            getCvh().getStatus().setShadowLayer(this.shadowOffsetX, this.shadowOffsetY, this.shadowRadius, this.shadowColor);
            getCvh().getBgExecutor().execute(new Runnable() { // from class: com.android.systemui.controls.ui.ThumbnailBehavior$bind$1
                @Override // java.lang.Runnable
                public final void run() {
                    final Drawable loadDrawable = ThumbnailBehavior.this.getTemplate().getThumbnail().loadDrawable(ThumbnailBehavior.this.getCvh().getContext());
                    DelayableExecutor uiExecutor = ThumbnailBehavior.this.getCvh().getUiExecutor();
                    final ThumbnailBehavior thumbnailBehavior = ThumbnailBehavior.this;
                    final ClipDrawable clipDrawable2 = clipDrawable;
                    final int i2 = i;
                    uiExecutor.execute(new Runnable() { // from class: com.android.systemui.controls.ui.ThumbnailBehavior$bind$1.1
                        @Override // java.lang.Runnable
                        public final void run() {
                            boolean enabled;
                            clipDrawable2.setDrawable(new CornerDrawable(loadDrawable, ThumbnailBehavior.this.getCvh().getContext().getResources().getDimensionPixelSize(R$dimen.control_corner_radius)));
                            clipDrawable2.setColorFilter(new BlendModeColorFilter(ThumbnailBehavior.this.getCvh().getContext().getResources().getColor(R$color.control_thumbnail_tint), BlendMode.LUMINOSITY));
                            ControlViewHolder cvh = ThumbnailBehavior.this.getCvh();
                            enabled = ThumbnailBehavior.this.getEnabled();
                            ControlViewHolder.applyRenderInfo$frameworks__base__packages__SystemUI__android_common__SystemUI_core$default(cvh, enabled, i2, false, 4, null);
                        }
                    });
                }
            });
        } else {
            getCvh().getTitle().setVisibility(0);
            getCvh().getSubtitle().setVisibility(0);
            getCvh().getStatus().setShadowLayer(ActionBarShadowController.ELEVATION_LOW, ActionBarShadowController.ELEVATION_LOW, ActionBarShadowController.ELEVATION_LOW, this.shadowColor);
        }
        ControlViewHolder.applyRenderInfo$frameworks__base__packages__SystemUI__android_common__SystemUI_core$default(getCvh(), getEnabled(), i, false, 4, null);
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
        return getTemplate().isActive();
    }

    public final ThumbnailTemplate getTemplate() {
        ThumbnailTemplate thumbnailTemplate = this.template;
        if (thumbnailTemplate != null) {
            return thumbnailTemplate;
        }
        return null;
    }

    @Override // com.android.systemui.controls.ui.Behavior
    public void initialize(final ControlViewHolder controlViewHolder) {
        setCvh(controlViewHolder);
        TypedValue typedValue = new TypedValue();
        controlViewHolder.getContext().getResources().getValue(R$dimen.controls_thumbnail_shadow_x, typedValue, true);
        this.shadowOffsetX = typedValue.getFloat();
        controlViewHolder.getContext().getResources().getValue(R$dimen.controls_thumbnail_shadow_y, typedValue, true);
        this.shadowOffsetY = typedValue.getFloat();
        controlViewHolder.getContext().getResources().getValue(R$dimen.controls_thumbnail_shadow_radius, typedValue, true);
        this.shadowRadius = typedValue.getFloat();
        this.shadowColor = controlViewHolder.getContext().getResources().getColor(R$color.control_thumbnail_shadow_color);
        controlViewHolder.getLayout().setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.controls.ui.ThumbnailBehavior$initialize$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ControlViewHolder.this.getControlActionCoordinator().touch(ControlViewHolder.this, this.getTemplate().getTemplateId(), this.getControl());
            }
        });
    }

    public final void setControl(Control control) {
        this.control = control;
    }

    public final void setCvh(ControlViewHolder controlViewHolder) {
        this.cvh = controlViewHolder;
    }

    public final void setTemplate(ThumbnailTemplate thumbnailTemplate) {
        this.template = thumbnailTemplate;
    }
}