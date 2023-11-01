package com.android.systemui.controls.management;

import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: mainsysui33.jar:com/android/systemui/controls/management/Holder.class */
public abstract class Holder extends RecyclerView.ViewHolder {
    public Holder(View view) {
        super(view);
    }

    public /* synthetic */ Holder(View view, DefaultConstructorMarker defaultConstructorMarker) {
        this(view);
    }

    public abstract void bindData(ElementWrapper elementWrapper);

    public void updateFavorite(boolean z) {
    }
}