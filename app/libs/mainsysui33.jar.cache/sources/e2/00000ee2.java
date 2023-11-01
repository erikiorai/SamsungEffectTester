package com.android.settingslib.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.TextView;
import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;

/* loaded from: mainsysui33.jar:com/android/settingslib/widget/BarChartPreference.class */
public class BarChartPreference extends Preference {
    public static final int[] BAR_VIEWS = {R$id.bar_view1, R$id.bar_view2, R$id.bar_view3, R$id.bar_view4};
    public boolean mIsLoading;
    public int mMaxBarHeight;

    public BarChartPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    public final void bindChartDetailsView(PreferenceViewHolder preferenceViewHolder) {
        Button button = (Button) preferenceViewHolder.findViewById(R$id.bar_chart_details);
        throw null;
    }

    public final void bindChartTitleView(PreferenceViewHolder preferenceViewHolder) {
        TextView textView = (TextView) preferenceViewHolder.findViewById(R$id.bar_chart_title);
        throw null;
    }

    public final void init() {
        setSelectable(false);
        setLayoutResource(R$layout.settings_bar_chart);
        this.mMaxBarHeight = getContext().getResources().getDimensionPixelSize(R$dimen.settings_bar_view_max_height);
    }

    @Override // androidx.preference.Preference
    public void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        preferenceViewHolder.setDividerAllowedAbove(true);
        preferenceViewHolder.setDividerAllowedBelow(true);
        bindChartTitleView(preferenceViewHolder);
        bindChartDetailsView(preferenceViewHolder);
        if (this.mIsLoading) {
            preferenceViewHolder.itemView.setVisibility(4);
        } else {
            preferenceViewHolder.itemView.setVisibility(0);
            throw null;
        }
    }
}