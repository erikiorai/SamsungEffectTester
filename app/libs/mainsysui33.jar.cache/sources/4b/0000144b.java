package com.android.systemui.controls.management;

import android.view.View;
import android.widget.TextView;

/* loaded from: mainsysui33.jar:com/android/systemui/controls/management/ZoneHolder.class */
public final class ZoneHolder extends Holder {
    public final TextView zone;

    public ZoneHolder(View view) {
        super(view, null);
        this.zone = (TextView) this.itemView;
    }

    @Override // com.android.systemui.controls.management.Holder
    public void bindData(ElementWrapper elementWrapper) {
        this.zone.setText(((ZoneNameWrapper) elementWrapper).getZoneName());
    }
}