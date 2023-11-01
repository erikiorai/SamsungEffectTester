package com.android.settingslib.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.core.content.res.TypedArrayUtils;
import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;

/* loaded from: mainsysui33.jar:com/android/settingslib/widget/LayoutPreference.class */
public class LayoutPreference extends Preference {
    public boolean mAllowDividerAbove;
    public boolean mAllowDividerBelow;
    public final View.OnClickListener mClickListener;
    public View mRootView;

    /* JADX DEBUG: Method not inlined, still used in: [com.android.settingslib.widget.LayoutPreference$$ExternalSyntheticLambda0.onClick(android.view.View):void] */
    /* renamed from: $r8$lambda$OiLRmfQWbEP-fDieCVpcBzOJp3k */
    public static /* synthetic */ void m1172$r8$lambda$OiLRmfQWbEPfDieCVpcBzOJp3k(LayoutPreference layoutPreference, View view) {
        layoutPreference.lambda$new$0(view);
    }

    public LayoutPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mClickListener = new View.OnClickListener() { // from class: com.android.settingslib.widget.LayoutPreference$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                LayoutPreference.m1172$r8$lambda$OiLRmfQWbEPfDieCVpcBzOJp3k(LayoutPreference.this, view);
            }
        };
        init(context, attributeSet, 0);
    }

    public /* synthetic */ void lambda$new$0(View view) {
        performClick(view);
    }

    public final void init(Context context, AttributeSet attributeSet, int i) {
        int[] iArr = R$styleable.Preference;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, iArr);
        int i2 = R$styleable.Preference_allowDividerAbove;
        this.mAllowDividerAbove = TypedArrayUtils.getBoolean(obtainStyledAttributes, i2, i2, false);
        int i3 = R$styleable.Preference_allowDividerBelow;
        this.mAllowDividerBelow = TypedArrayUtils.getBoolean(obtainStyledAttributes, i3, i3, false);
        obtainStyledAttributes.recycle();
        TypedArray obtainStyledAttributes2 = context.obtainStyledAttributes(attributeSet, iArr, i, 0);
        int resourceId = obtainStyledAttributes2.getResourceId(R$styleable.Preference_android_layout, 0);
        if (resourceId == 0) {
            throw new IllegalArgumentException("LayoutPreference requires a layout to be defined");
        }
        obtainStyledAttributes2.recycle();
        setView(LayoutInflater.from(getContext()).inflate(resourceId, (ViewGroup) null, false));
    }

    @Override // androidx.preference.Preference
    public void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        preferenceViewHolder.itemView.setOnClickListener(this.mClickListener);
        boolean isSelectable = isSelectable();
        preferenceViewHolder.itemView.setFocusable(isSelectable);
        preferenceViewHolder.itemView.setClickable(isSelectable);
        preferenceViewHolder.setDividerAllowedAbove(this.mAllowDividerAbove);
        preferenceViewHolder.setDividerAllowedBelow(this.mAllowDividerBelow);
        FrameLayout frameLayout = (FrameLayout) preferenceViewHolder.itemView;
        frameLayout.removeAllViews();
        ViewGroup viewGroup = (ViewGroup) this.mRootView.getParent();
        if (viewGroup != null) {
            viewGroup.removeView(this.mRootView);
        }
        frameLayout.addView(this.mRootView);
    }

    public final void setView(View view) {
        setLayoutResource(R$layout.layout_preference_frame);
        this.mRootView = view;
        setShouldDisableView(false);
    }
}