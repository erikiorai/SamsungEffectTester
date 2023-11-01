package com.android.settingslib.collapsingtoolbar.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.text.LineBreakConfig;
import android.text.StaticLayout;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.android.settingslib.widget.R$id;
import com.android.settingslib.widget.R$layout;
import com.android.settingslib.widget.R$styleable;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

/* loaded from: mainsysui33.jar:com/android/settingslib/collapsingtoolbar/widget/CollapsingCoordinatorLayout.class */
public class CollapsingCoordinatorLayout extends CoordinatorLayout {
    public AppBarLayout mAppBarLayout;
    public CollapsingToolbarLayout mCollapsingToolbarLayout;
    public boolean mIsMatchParentHeight;
    public CharSequence mToolbarTitle;

    /* JADX DEBUG: Method not inlined, still used in: [com.android.settingslib.collapsingtoolbar.widget.CollapsingCoordinatorLayout$$ExternalSyntheticLambda0.configure(android.text.StaticLayout$Builder):void] */
    /* renamed from: $r8$lambda$g2HwggP6EoatWqec-12yJQKXWm4 */
    public static /* synthetic */ void m1074$r8$lambda$g2HwggP6EoatWqec12yJQKXWm4(StaticLayout.Builder builder) {
        lambda$init$0(builder);
    }

    public CollapsingCoordinatorLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public CollapsingCoordinatorLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mIsMatchParentHeight = false;
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.CollapsingCoordinatorLayout);
            this.mToolbarTitle = obtainStyledAttributes.getText(R$styleable.CollapsingCoordinatorLayout_collapsing_toolbar_title);
            this.mIsMatchParentHeight = obtainStyledAttributes.getBoolean(R$styleable.CollapsingCoordinatorLayout_content_frame_height_match_parent, false);
            obtainStyledAttributes.recycle();
        }
        init();
    }

    public static /* synthetic */ void lambda$init$0(StaticLayout.Builder builder) {
        builder.setLineBreakConfig(new LineBreakConfig.Builder().setLineBreakWordStyle(1).build());
    }

    @Override // android.view.ViewGroup
    public void addView(View view, int i, ViewGroup.LayoutParams layoutParams) {
        int id = view.getId();
        int i2 = R$id.content_frame;
        if (id == i2 && this.mIsMatchParentHeight) {
            layoutParams.height = -1;
        }
        ViewGroup viewGroup = (ViewGroup) findViewById(i2);
        if (viewGroup == null || !isContentFrameChild(view.getId())) {
            super.addView(view, i, layoutParams);
        } else {
            viewGroup.addView(view, i, layoutParams);
        }
    }

    public final void disableCollapsingToolbarLayoutScrollingBehavior() {
        AppBarLayout appBarLayout = this.mAppBarLayout;
        if (appBarLayout == null) {
            return;
        }
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) appBarLayout.getLayoutParams();
        AppBarLayout.Behavior behavior = new AppBarLayout.Behavior();
        behavior.setDragCallback(new AppBarLayout.Behavior.DragCallback() { // from class: com.android.settingslib.collapsingtoolbar.widget.CollapsingCoordinatorLayout.1
            {
                CollapsingCoordinatorLayout.this = this;
            }

            public boolean canDrag(AppBarLayout appBarLayout2) {
                return false;
            }
        });
        layoutParams.setBehavior(behavior);
    }

    public final void init() {
        ViewGroup.inflate(getContext(), R$layout.collapsing_toolbar_content_layout, this);
        this.mCollapsingToolbarLayout = findViewById(R$id.collapsing_toolbar);
        this.mAppBarLayout = findViewById(R$id.app_bar);
        CollapsingToolbarLayout collapsingToolbarLayout = this.mCollapsingToolbarLayout;
        if (collapsingToolbarLayout != null) {
            collapsingToolbarLayout.setLineSpacingMultiplier(1.1f);
            this.mCollapsingToolbarLayout.setHyphenationFrequency(3);
            this.mCollapsingToolbarLayout.setStaticLayoutBuilderConfigurer(new CollapsingToolbarLayout.StaticLayoutBuilderConfigurer() { // from class: com.android.settingslib.collapsingtoolbar.widget.CollapsingCoordinatorLayout$$ExternalSyntheticLambda0
                public final void configure(StaticLayout.Builder builder) {
                    CollapsingCoordinatorLayout.m1074$r8$lambda$g2HwggP6EoatWqec12yJQKXWm4(builder);
                }
            });
            if (!TextUtils.isEmpty(this.mToolbarTitle)) {
                this.mCollapsingToolbarLayout.setTitle(this.mToolbarTitle);
            }
        }
        disableCollapsingToolbarLayoutScrollingBehavior();
    }

    public final boolean isContentFrameChild(int i) {
        return (i == R$id.app_bar || i == R$id.content_frame) ? false : true;
    }
}