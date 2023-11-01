package com.android.systemui.controls.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.systemui.R$id;

/* loaded from: mainsysui33.jar:com/android/systemui/controls/ui/ItemAdapter.class */
public final class ItemAdapter extends ArrayAdapter<SelectionItem> {
    public final LayoutInflater layoutInflater;
    public final Context parentContext;
    public final int resource;

    public ItemAdapter(Context context, int i) {
        super(context, i);
        this.parentContext = context;
        this.resource = i;
        this.layoutInflater = LayoutInflater.from(getContext());
    }

    @Override // android.widget.ArrayAdapter, android.widget.Adapter
    public View getView(int i, View view, ViewGroup viewGroup) {
        SelectionItem item = getItem(i);
        View view2 = view;
        if (view == null) {
            view2 = this.layoutInflater.inflate(this.resource, viewGroup, false);
        }
        ((TextView) view2.requireViewById(R$id.controls_spinner_item)).setText(item.getTitle());
        ((ImageView) view2.requireViewById(R$id.app_icon)).setImageDrawable(item.getIcon());
        return view2;
    }
}