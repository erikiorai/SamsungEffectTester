package com.android.systemui.qs;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.android.settingslib.widget.ActionBarShadowController;
import com.android.systemui.Dumpable;
import com.android.systemui.R$dimen;
import com.android.systemui.R$id;
import com.android.systemui.qs.customize.QSCustomizer;
import com.android.systemui.util.LargeScreenUtils;
import java.io.PrintWriter;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/QSContainerImpl.class */
public class QSContainerImpl extends FrameLayout implements Dumpable {
    public boolean mClippingEnabled;
    public int mContentHorizontalPadding;
    public int mFancyClippingBottom;
    public final Path mFancyClippingPath;
    public final float[] mFancyClippingRadii;
    public int mFancyClippingTop;
    public QuickStatusBarHeader mHeader;
    public int mHeightOverride;
    public int mHorizontalMargins;
    public QSCustomizer mQSCustomizer;
    public NonInterceptingScrollView mQSPanelContainer;
    public boolean mQsDisabled;
    public float mQsExpansion;
    public int mTilesPageMargin;
    public boolean mUseCombinedHeaders;

    public QSContainerImpl(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mFancyClippingRadii = new float[]{ActionBarShadowController.ELEVATION_LOW, ActionBarShadowController.ELEVATION_LOW, ActionBarShadowController.ELEVATION_LOW, ActionBarShadowController.ELEVATION_LOW, ActionBarShadowController.ELEVATION_LOW, ActionBarShadowController.ELEVATION_LOW, ActionBarShadowController.ELEVATION_LOW, ActionBarShadowController.ELEVATION_LOW};
        this.mFancyClippingPath = new Path();
        this.mHeightOverride = -1;
        this.mContentHorizontalPadding = -1;
    }

    public int calculateContainerHeight() {
        int height;
        int i = this.mHeightOverride;
        if (i == -1) {
            i = getMeasuredHeight();
        }
        if (this.mQSCustomizer.isCustomizing()) {
            height = this.mQSCustomizer.getHeight();
        } else {
            height = this.mHeader.getHeight() + Math.round(this.mQsExpansion * (i - this.mHeader.getHeight()));
        }
        return height;
    }

    public void disable(int i, int i2, boolean z) {
        boolean z2 = true;
        if ((i2 & 1) == 0) {
            z2 = false;
        }
        if (z2 == this.mQsDisabled) {
            return;
        }
        this.mQsDisabled = z2;
    }

    @Override // android.view.ViewGroup, android.view.View
    public void dispatchDraw(Canvas canvas) {
        if (!this.mFancyClippingPath.isEmpty()) {
            canvas.translate(ActionBarShadowController.ELEVATION_LOW, -getTranslationY());
            canvas.clipOutPath(this.mFancyClippingPath);
            canvas.translate(ActionBarShadowController.ELEVATION_LOW, getTranslationY());
        }
        super.dispatchDraw(canvas);
    }

    @Override // com.android.systemui.Dumpable
    public void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println(getClass().getSimpleName() + " updateClippingPath: top(" + this.mFancyClippingTop + ") bottom(" + this.mFancyClippingBottom + ") mClippingEnabled(" + this.mClippingEnabled + ")");
    }

    public NonInterceptingScrollView getQSPanelContainer() {
        return this.mQSPanelContainer;
    }

    @Override // android.view.View
    public boolean hasOverlappingRendering() {
        return false;
    }

    public boolean isTransformedTouchPointInView(float f, float f2, View view, PointF pointF) {
        if (!this.mClippingEnabled || getTranslationY() + f2 <= this.mFancyClippingTop) {
            return super.isTransformedTouchPointInView(f, f2, view, pointF);
        }
        return false;
    }

    @Override // android.view.ViewGroup
    public void measureChildWithMargins(View view, int i, int i2, int i3, int i4) {
        if (view != this.mQSPanelContainer) {
            super.measureChildWithMargins(view, i, i2, i3, i4);
        }
    }

    @Override // android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mQSPanelContainer = (NonInterceptingScrollView) findViewById(R$id.expanded_qs_scroll_view);
        this.mHeader = (QuickStatusBarHeader) findViewById(R$id.header);
        this.mQSCustomizer = (QSCustomizer) findViewById(R$id.qs_customize);
        setImportantForAccessibility(2);
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        updateExpansion();
        updateClippingPath();
    }

    @Override // android.widget.FrameLayout, android.view.View
    public void onMeasure(int i, int i2) {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) this.mQSPanelContainer.getLayoutParams();
        int size = View.MeasureSpec.getSize(i2);
        int i3 = marginLayoutParams.topMargin;
        int i4 = marginLayoutParams.bottomMargin;
        int paddingBottom = getPaddingBottom();
        int i5 = ((FrameLayout) this).mPaddingLeft + ((FrameLayout) this).mPaddingRight + marginLayoutParams.leftMargin + marginLayoutParams.rightMargin;
        this.mQSPanelContainer.measure(FrameLayout.getChildMeasureSpec(i, i5, marginLayoutParams.width), View.MeasureSpec.makeMeasureSpec(((size - i3) - i4) - paddingBottom, Integer.MIN_VALUE));
        super.onMeasure(View.MeasureSpec.makeMeasureSpec(this.mQSPanelContainer.getMeasuredWidth() + i5, 1073741824), View.MeasureSpec.makeMeasureSpec(size, 1073741824));
        this.mQSCustomizer.measure(i, View.MeasureSpec.makeMeasureSpec(size, 1073741824));
    }

    @Override // android.view.View
    public boolean performClick() {
        return true;
    }

    public void setExpansion(float f) {
        this.mQsExpansion = f;
        this.mQSPanelContainer.setScrollingEnabled(f > ActionBarShadowController.ELEVATION_LOW);
        updateExpansion();
    }

    public void setFancyClipping(int i, int i2, int i3, boolean z) {
        float[] fArr = this.mFancyClippingRadii;
        float f = fArr[0];
        float f2 = i3;
        boolean z2 = false;
        if (f != f2) {
            fArr[0] = f2;
            fArr[1] = f2;
            fArr[2] = f2;
            fArr[3] = f2;
            z2 = true;
        }
        if (this.mFancyClippingTop != i) {
            this.mFancyClippingTop = i;
            z2 = true;
        }
        if (this.mFancyClippingBottom != i2) {
            this.mFancyClippingBottom = i2;
            z2 = true;
        }
        if (this.mClippingEnabled != z) {
            this.mClippingEnabled = z;
            z2 = true;
        }
        if (z2) {
            updateClippingPath();
        }
    }

    public void setHeightOverride(int i) {
        this.mHeightOverride = i;
        updateExpansion();
    }

    public void setUseCombinedHeaders(boolean z) {
        this.mUseCombinedHeaders = z;
    }

    public final void updateClippingPath() {
        this.mFancyClippingPath.reset();
        if (!this.mClippingEnabled) {
            invalidate();
            return;
        }
        this.mFancyClippingPath.addRoundRect(ActionBarShadowController.ELEVATION_LOW, this.mFancyClippingTop, getWidth(), this.mFancyClippingBottom, this.mFancyClippingRadii, Path.Direction.CW);
        invalidate();
    }

    public void updateExpansion() {
        setBottom(getTop() + calculateContainerHeight());
    }

    public final void updatePaddingsAndMargins(QSPanelController qSPanelController, QuickStatusBarHeaderController quickStatusBarHeaderController) {
        for (int i = 0; i < getChildCount(); i++) {
            View childAt = getChildAt(i);
            if (childAt != this.mQSCustomizer) {
                if (childAt.getId() != R$id.qs_footer_actions) {
                    FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) childAt.getLayoutParams();
                    int i2 = this.mHorizontalMargins;
                    layoutParams.rightMargin = i2;
                    layoutParams.leftMargin = i2;
                }
                if (childAt == this.mQSPanelContainer) {
                    int i3 = this.mContentHorizontalPadding;
                    qSPanelController.setContentMargins(i3, i3);
                    qSPanelController.setPageMargin(this.mTilesPageMargin);
                } else if (childAt == this.mHeader) {
                    int i4 = this.mContentHorizontalPadding;
                    quickStatusBarHeaderController.setContentMargins(i4, i4);
                } else {
                    childAt.setPaddingRelative(this.mContentHorizontalPadding, childAt.getPaddingTop(), this.mContentHorizontalPadding, childAt.getPaddingBottom());
                }
            }
        }
    }

    public void updateResources(QSPanelController qSPanelController, QuickStatusBarHeaderController quickStatusBarHeaderController) {
        int qsHeaderSystemIconsAreaHeight = QSUtils.getQsHeaderSystemIconsAreaHeight(((FrameLayout) this).mContext);
        int i = qsHeaderSystemIconsAreaHeight;
        if (this.mUseCombinedHeaders) {
            i = qsHeaderSystemIconsAreaHeight;
            if (!LargeScreenUtils.shouldUseLargeScreenShadeHeader(((FrameLayout) this).mContext.getResources())) {
                i = ((FrameLayout) this).mContext.getResources().getDimensionPixelSize(R$dimen.large_screen_shade_header_height);
            }
        }
        NonInterceptingScrollView nonInterceptingScrollView = this.mQSPanelContainer;
        nonInterceptingScrollView.setPaddingRelative(nonInterceptingScrollView.getPaddingStart(), i, this.mQSPanelContainer.getPaddingEnd(), this.mQSPanelContainer.getPaddingBottom());
        int dimensionPixelSize = getResources().getDimensionPixelSize(R$dimen.qs_horizontal_margin);
        int dimensionPixelSize2 = getResources().getDimensionPixelSize(R$dimen.qs_content_horizontal_padding);
        int dimensionPixelSize3 = getResources().getDimensionPixelSize(R$dimen.qs_tiles_page_horizontal_margin);
        boolean z = (dimensionPixelSize2 == this.mContentHorizontalPadding && dimensionPixelSize == this.mHorizontalMargins && dimensionPixelSize3 == this.mTilesPageMargin) ? false : true;
        this.mContentHorizontalPadding = dimensionPixelSize2;
        this.mHorizontalMargins = dimensionPixelSize;
        this.mTilesPageMargin = dimensionPixelSize3;
        if (z) {
            updatePaddingsAndMargins(qSPanelController, quickStatusBarHeaderController);
        }
    }
}