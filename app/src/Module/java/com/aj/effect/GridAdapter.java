package com.aj.effect;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class GridAdapter extends BaseAdapter {

    private final Context context;
    private final List<EffectEnum> list = EffectEnum.effectList;
    private final List<EffectEnum> entries;
    private int selection = -1;
    private final List<View> views = new ArrayList<>();

    public GridAdapter(Context context, List<EffectEnum> entries) {
        this.context = context;
        this.entries = entries;
    }

    public void setSelection(int selection) {
        this.selection = selection;
    }

    @Override
    public int getCount() {
        return entries.size();
    }

    @Override
    public View getItem(int i) {
        return views.get(i);
    }

    public EffectEnum getEffectItem(int i) {
        return entries.get(i);
    }

    @Override
    public long getItemId(int i) {
        return entries.get(i).hashCode(); // ??????
    }

    @Override
    public View getView(int pos, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = View.inflate(context, R.layout.list_item_grid, null);
        }
        CheckedTextView name = view.findViewById(android.R.id.text1);
        ImageView image = view.findViewById(R.id.preview_image);
        EffectEnum effect = getEffectItem(pos);

        name.setText(effect.name);
        image.setImageResource(effect.drawable);

        name.setChecked(selection == pos);

        return view;
    }
}
