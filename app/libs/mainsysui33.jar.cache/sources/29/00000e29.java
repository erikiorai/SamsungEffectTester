package com.android.settingslib.collapsingtoolbar;

import android.app.ActionBar;
import android.graphics.text.LineBreakConfig;
import android.text.StaticLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.android.settingslib.widget.R$id;
import com.android.settingslib.widget.R$layout;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

/* loaded from: mainsysui33.jar:com/android/settingslib/collapsingtoolbar/CollapsingToolbarDelegate.class */
public class CollapsingToolbarDelegate {
    public AppBarLayout mAppBarLayout;
    public CollapsingToolbarLayout mCollapsingToolbarLayout;
    public FrameLayout mContentFrameLayout;
    public CoordinatorLayout mCoordinatorLayout;
    public final HostCallback mHostCallback;
    public Toolbar mToolbar;

    /* loaded from: mainsysui33.jar:com/android/settingslib/collapsingtoolbar/CollapsingToolbarDelegate$HostCallback.class */
    public interface HostCallback {
        ActionBar setActionBar(Toolbar toolbar);

        void setOuterTitle(CharSequence charSequence);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.settingslib.collapsingtoolbar.CollapsingToolbarDelegate$$ExternalSyntheticLambda0.configure(android.text.StaticLayout$Builder):void] */
    public static /* synthetic */ void $r8$lambda$_nK7I073xUZIQr92uvpg5zOrqIY(StaticLayout.Builder builder) {
        lambda$onCreateView$0(builder);
    }

    public CollapsingToolbarDelegate(HostCallback hostCallback) {
        this.mHostCallback = hostCallback;
    }

    public static /* synthetic */ void lambda$onCreateView$0(StaticLayout.Builder builder) {
        builder.setLineBreakConfig(new LineBreakConfig.Builder().setLineBreakWordStyle(1).build());
    }

    public final void disableCollapsingToolbarLayoutScrollingBehavior() {
        AppBarLayout appBarLayout = this.mAppBarLayout;
        if (appBarLayout == null) {
            return;
        }
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) appBarLayout.getLayoutParams();
        AppBarLayout.Behavior behavior = new AppBarLayout.Behavior();
        behavior.setDragCallback(new AppBarLayout.Behavior.DragCallback() { // from class: com.android.settingslib.collapsingtoolbar.CollapsingToolbarDelegate.1
            {
                CollapsingToolbarDelegate.this = this;
            }

            public boolean canDrag(AppBarLayout appBarLayout2) {
                return false;
            }
        });
        layoutParams.setBehavior(behavior);
    }

    public FrameLayout getContentFrameLayout() {
        return this.mContentFrameLayout;
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup) {
        View inflate = layoutInflater.inflate(R$layout.collapsing_toolbar_base_layout, viewGroup, false);
        if (inflate instanceof CoordinatorLayout) {
            this.mCoordinatorLayout = (CoordinatorLayout) inflate;
        }
        this.mCollapsingToolbarLayout = inflate.findViewById(R$id.collapsing_toolbar);
        this.mAppBarLayout = inflate.findViewById(R$id.app_bar);
        CollapsingToolbarLayout collapsingToolbarLayout = this.mCollapsingToolbarLayout;
        if (collapsingToolbarLayout != null) {
            collapsingToolbarLayout.setLineSpacingMultiplier(1.1f);
            this.mCollapsingToolbarLayout.setHyphenationFrequency(3);
            this.mCollapsingToolbarLayout.setStaticLayoutBuilderConfigurer(new CollapsingToolbarLayout.StaticLayoutBuilderConfigurer() { // from class: com.android.settingslib.collapsingtoolbar.CollapsingToolbarDelegate$$ExternalSyntheticLambda0
                public final void configure(StaticLayout.Builder builder) {
                    CollapsingToolbarDelegate.$r8$lambda$_nK7I073xUZIQr92uvpg5zOrqIY(builder);
                }
            });
        }
        disableCollapsingToolbarLayoutScrollingBehavior();
        this.mToolbar = (Toolbar) inflate.findViewById(R$id.action_bar);
        this.mContentFrameLayout = (FrameLayout) inflate.findViewById(R$id.content_frame);
        ActionBar actionBar = this.mHostCallback.setActionBar(this.mToolbar);
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayShowTitleEnabled(true);
        }
        return inflate;
    }

    public void setTitle(CharSequence charSequence) {
        CollapsingToolbarLayout collapsingToolbarLayout = this.mCollapsingToolbarLayout;
        if (collapsingToolbarLayout != null) {
            collapsingToolbarLayout.setTitle(charSequence);
        } else {
            this.mHostCallback.setOuterTitle(charSequence);
        }
    }
}