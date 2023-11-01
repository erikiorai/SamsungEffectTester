package com.android.systemui.decor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/* loaded from: mainsysui33.jar:com/android/systemui/decor/PrivacyDotCornerDecorProviderImpl.class */
public final class PrivacyDotCornerDecorProviderImpl extends CornerDecorProvider {
    public final int alignedBound1;
    public final int alignedBound2;
    public final int layoutId;
    public final int viewId;

    public PrivacyDotCornerDecorProviderImpl(int i, int i2, int i3, int i4) {
        this.viewId = i;
        this.alignedBound1 = i2;
        this.alignedBound2 = i3;
        this.layoutId = i4;
    }

    @Override // com.android.systemui.decor.CornerDecorProvider
    public int getAlignedBound1() {
        return this.alignedBound1;
    }

    @Override // com.android.systemui.decor.CornerDecorProvider
    public int getAlignedBound2() {
        return this.alignedBound2;
    }

    @Override // com.android.systemui.decor.DecorProvider
    public int getViewId() {
        return this.viewId;
    }

    @Override // com.android.systemui.decor.DecorProvider
    public View inflateView(Context context, ViewGroup viewGroup, int i, int i2) {
        LayoutInflater.from(context).inflate(this.layoutId, viewGroup, true);
        return viewGroup.getChildAt(viewGroup.getChildCount() - 1);
    }

    @Override // com.android.systemui.decor.DecorProvider
    public void onReloadResAndMeasure(View view, int i, int i2, int i3, String str) {
    }
}