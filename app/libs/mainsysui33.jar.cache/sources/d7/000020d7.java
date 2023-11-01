package com.android.systemui.qs;

import android.content.Context;
import android.content.res.Configuration;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.android.settingslib.widget.ActionBarShadowController;
import com.android.systemui.R$dimen;
import com.android.systemui.R$id;
import com.android.systemui.qs.TouchAnimator;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/QSFooterView.class */
public class QSFooterView extends FrameLayout {
    public TextView mBuildText;
    public View mEditButton;
    public boolean mExpanded;
    public float mExpansionAmount;
    public TouchAnimator mFooterAnimator;
    public PageIndicator mPageIndicator;
    public boolean mQsDisabled;
    public boolean mShouldShowBuildText;

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.QSFooterView$$ExternalSyntheticLambda0.run():void] */
    public static /* synthetic */ void $r8$lambda$NOUcho095O4ErNGCKIyHnULa390(QSFooterView qSFooterView) {
        qSFooterView.lambda$updateEverything$0();
    }

    public QSFooterView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public /* synthetic */ void lambda$updateEverything$0() {
        updateVisibilities();
        updateClickabilities();
        setClickable(false);
    }

    public final TouchAnimator createFooterAnimator() {
        return new TouchAnimator.Builder().addFloat(this.mPageIndicator, "alpha", ActionBarShadowController.ELEVATION_LOW, 1.0f).addFloat(this.mBuildText, "alpha", ActionBarShadowController.ELEVATION_LOW, 1.0f).addFloat(this.mEditButton, "alpha", ActionBarShadowController.ELEVATION_LOW, 1.0f).setStartDelay(0.9f).build();
    }

    public void disable(int i) {
        boolean z = true;
        if ((i & 1) == 0) {
            z = false;
        }
        if (z == this.mQsDisabled) {
            return;
        }
        this.mQsDisabled = z;
        updateEverything();
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override // android.view.View
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        updateResources();
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    @Override // android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mPageIndicator = (PageIndicator) findViewById(R$id.footer_page_indicator);
        this.mBuildText = (TextView) findViewById(R$id.build);
        this.mEditButton = findViewById(16908291);
        updateResources();
        setImportantForAccessibility(1);
    }

    public void setExpanded(boolean z) {
        if (this.mExpanded == z) {
            return;
        }
        this.mExpanded = z;
        updateEverything();
    }

    public void setExpansion(float f) {
        this.mExpansionAmount = f;
        TouchAnimator touchAnimator = this.mFooterAnimator;
        if (touchAnimator != null) {
            touchAnimator.setPosition(f);
        }
    }

    public void setKeyguardShowing() {
        setExpansion(this.mExpansionAmount);
    }

    public final void updateClickabilities() {
        TextView textView = this.mBuildText;
        textView.setLongClickable(textView.getVisibility() == 0);
    }

    public void updateEverything() {
        post(new Runnable() { // from class: com.android.systemui.qs.QSFooterView$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                QSFooterView.$r8$lambda$NOUcho095O4ErNGCKIyHnULa390(QSFooterView.this);
            }
        });
    }

    public final void updateFooterAnimator() {
        this.mFooterAnimator = createFooterAnimator();
    }

    public final void updateResources() {
        updateFooterAnimator();
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) getLayoutParams();
        marginLayoutParams.bottomMargin = getResources().getDimensionPixelSize(R$dimen.qs_footers_margin_bottom);
        setLayoutParams(marginLayoutParams);
    }

    public final void updateVisibilities() {
        this.mBuildText.setVisibility((this.mExpanded && this.mShouldShowBuildText) ? 0 : 4);
    }
}