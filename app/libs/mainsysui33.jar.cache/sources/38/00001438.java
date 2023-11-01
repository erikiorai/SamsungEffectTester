package com.android.systemui.controls.management;

import android.view.View;
import com.android.systemui.R$id;

/* loaded from: mainsysui33.jar:com/android/systemui/controls/management/DividerHolder.class */
public final class DividerHolder extends Holder {
    public final View divider;
    public final View frame;

    public DividerHolder(View view) {
        super(view, null);
        this.frame = this.itemView.requireViewById(R$id.frame);
        this.divider = this.itemView.requireViewById(R$id.divider);
    }

    @Override // com.android.systemui.controls.management.Holder
    public void bindData(ElementWrapper elementWrapper) {
        DividerWrapper dividerWrapper = (DividerWrapper) elementWrapper;
        this.frame.setVisibility(dividerWrapper.getShowNone() ? 0 : 8);
        this.divider.setVisibility(dividerWrapper.getShowDivider() ? 0 : 8);
    }
}