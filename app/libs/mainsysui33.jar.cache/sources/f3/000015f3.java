package com.android.systemui.decor;

import android.content.Context;
import android.content.res.ColorStateList;
import android.util.Size;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

/* loaded from: mainsysui33.jar:com/android/systemui/decor/RoundedCornerDecorProviderImpl.class */
public final class RoundedCornerDecorProviderImpl extends CornerDecorProvider {
    public final int alignedBound1;
    public final int alignedBound2;
    public final boolean isTop = getAlignedBounds().contains(1);
    public final RoundedCornerResDelegate roundedCornerResDelegate;
    public final int viewId;

    public RoundedCornerDecorProviderImpl(int i, int i2, int i3, RoundedCornerResDelegate roundedCornerResDelegate) {
        this.viewId = i;
        this.alignedBound1 = i2;
        this.alignedBound2 = i3;
        this.roundedCornerResDelegate = roundedCornerResDelegate;
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
        int layoutGravity;
        int layoutGravity2;
        ImageView imageView = new ImageView(context);
        imageView.setId(getViewId());
        initView(imageView, i, i2);
        Size topRoundedSize = this.isTop ? this.roundedCornerResDelegate.getTopRoundedSize() : this.roundedCornerResDelegate.getBottomRoundedSize();
        int width = topRoundedSize.getWidth();
        int height = topRoundedSize.getHeight();
        layoutGravity = RoundedCornerDecorProviderImplKt.toLayoutGravity(getAlignedBound1(), i);
        layoutGravity2 = RoundedCornerDecorProviderImplKt.toLayoutGravity(getAlignedBound2(), i);
        viewGroup.addView(imageView, new FrameLayout.LayoutParams(width, height, layoutGravity2 | layoutGravity));
        return imageView;
    }

    public final void initView(ImageView imageView, int i, int i2) {
        RoundedCornerDecorProviderImplKt.setRoundedCornerImage(imageView, this.roundedCornerResDelegate, this.isTop);
        RoundedCornerDecorProviderImplKt.adjustRotation(imageView, getAlignedBounds(), i);
        imageView.setImageTintList(ColorStateList.valueOf(i2));
    }

    @Override // com.android.systemui.decor.DecorProvider
    public void onReloadResAndMeasure(View view, int i, int i2, int i3, String str) {
        int layoutGravity;
        int layoutGravity2;
        this.roundedCornerResDelegate.updateDisplayUniqueId(str, Integer.valueOf(i));
        ImageView imageView = (ImageView) view;
        initView(imageView, i2, i3);
        Size topRoundedSize = this.isTop ? this.roundedCornerResDelegate.getTopRoundedSize() : this.roundedCornerResDelegate.getBottomRoundedSize();
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) imageView.getLayoutParams();
        layoutParams.width = topRoundedSize.getWidth();
        layoutParams.height = topRoundedSize.getHeight();
        layoutGravity = RoundedCornerDecorProviderImplKt.toLayoutGravity(getAlignedBound1(), i2);
        layoutGravity2 = RoundedCornerDecorProviderImplKt.toLayoutGravity(getAlignedBound2(), i2);
        layoutParams.gravity = layoutGravity2 | layoutGravity;
        view.setLayoutParams(layoutParams);
    }
}