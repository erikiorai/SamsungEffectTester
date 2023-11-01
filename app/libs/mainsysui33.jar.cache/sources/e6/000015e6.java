package com.android.systemui.decor;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import com.android.systemui.R$id;
import com.android.systemui.ScreenDecorations;

/* loaded from: mainsysui33.jar:com/android/systemui/decor/CutoutDecorProviderImpl.class */
public final class CutoutDecorProviderImpl extends BoundDecorProvider {
    public final int alignedBound;
    public final int viewId;

    public CutoutDecorProviderImpl(int i) {
        this.alignedBound = i;
        int alignedBound = getAlignedBound();
        this.viewId = alignedBound != 0 ? alignedBound != 1 ? alignedBound != 2 ? R$id.display_cutout_bottom : R$id.display_cutout_right : R$id.display_cutout : R$id.display_cutout_left;
    }

    @Override // com.android.systemui.decor.BoundDecorProvider
    public int getAlignedBound() {
        return this.alignedBound;
    }

    @Override // com.android.systemui.decor.DecorProvider
    public int getViewId() {
        return this.viewId;
    }

    @Override // com.android.systemui.decor.DecorProvider
    public View inflateView(Context context, ViewGroup viewGroup, int i, int i2) {
        ScreenDecorations.DisplayCutoutView displayCutoutView = new ScreenDecorations.DisplayCutoutView(context, getAlignedBound());
        displayCutoutView.setId(getViewId());
        displayCutoutView.setColor(i2);
        viewGroup.addView(displayCutoutView);
        displayCutoutView.updateRotation(i);
        return displayCutoutView;
    }

    @Override // com.android.systemui.decor.DecorProvider
    public void onReloadResAndMeasure(View view, int i, int i2, int i3, String str) {
        ScreenDecorations.DisplayCutoutView displayCutoutView = view instanceof ScreenDecorations.DisplayCutoutView ? (ScreenDecorations.DisplayCutoutView) view : null;
        if (displayCutoutView != null) {
            displayCutoutView.setColor(i3);
            displayCutoutView.updateRotation(i2);
            displayCutoutView.updateConfiguration(str);
        }
    }
}