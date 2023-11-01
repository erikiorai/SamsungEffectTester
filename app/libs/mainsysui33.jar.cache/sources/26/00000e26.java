package com.android.settingslib.collapsingtoolbar;

import android.app.ActionBar;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toolbar;
import androidx.fragment.app.FragmentActivity;
import com.android.settingslib.collapsingtoolbar.CollapsingToolbarDelegate;
import com.android.settingslib.utils.BuildCompatUtils;
import com.android.settingslib.widget.R$id;

/* loaded from: mainsysui33.jar:com/android/settingslib/collapsingtoolbar/CollapsingToolbarBaseActivity.class */
public class CollapsingToolbarBaseActivity extends FragmentActivity {
    public int mCustomizeLayoutResId = 0;
    public CollapsingToolbarDelegate mToolbardelegate;

    /* loaded from: mainsysui33.jar:com/android/settingslib/collapsingtoolbar/CollapsingToolbarBaseActivity$DelegateCallback.class */
    public class DelegateCallback implements CollapsingToolbarDelegate.HostCallback {
        public DelegateCallback() {
        }

        @Override // com.android.settingslib.collapsingtoolbar.CollapsingToolbarDelegate.HostCallback
        public ActionBar setActionBar(Toolbar toolbar) {
            CollapsingToolbarBaseActivity.super.setActionBar(toolbar);
            return CollapsingToolbarBaseActivity.super.getActionBar();
        }

        @Override // com.android.settingslib.collapsingtoolbar.CollapsingToolbarDelegate.HostCallback
        public void setOuterTitle(CharSequence charSequence) {
            CollapsingToolbarBaseActivity.super.setTitle(charSequence);
        }
    }

    public final CollapsingToolbarDelegate getToolbarDelegate() {
        if (this.mToolbardelegate == null) {
            this.mToolbardelegate = new CollapsingToolbarDelegate(new DelegateCallback());
        }
        return this.mToolbardelegate;
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (this.mCustomizeLayoutResId <= 0 || BuildCompatUtils.isAtLeastS()) {
            super.setContentView(getToolbarDelegate().onCreateView(getLayoutInflater(), null));
        } else {
            super.setContentView(this.mCustomizeLayoutResId);
        }
    }

    @Override // android.app.Activity
    public boolean onNavigateUp() {
        if (super.onNavigateUp()) {
            return true;
        }
        finishAfterTransition();
        return true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v12, types: [android.view.ViewGroup] */
    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void setContentView(int i) {
        CollapsingToolbarDelegate collapsingToolbarDelegate = this.mToolbardelegate;
        FrameLayout contentFrameLayout = collapsingToolbarDelegate == null ? (ViewGroup) findViewById(R$id.content_frame) : collapsingToolbarDelegate.getContentFrameLayout();
        if (contentFrameLayout != null) {
            contentFrameLayout.removeAllViews();
        }
        LayoutInflater.from(this).inflate(i, contentFrameLayout);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v9, types: [android.view.ViewGroup] */
    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void setContentView(View view) {
        CollapsingToolbarDelegate collapsingToolbarDelegate = this.mToolbardelegate;
        FrameLayout contentFrameLayout = collapsingToolbarDelegate == null ? (ViewGroup) findViewById(R$id.content_frame) : collapsingToolbarDelegate.getContentFrameLayout();
        if (contentFrameLayout != null) {
            contentFrameLayout.addView(view);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v9, types: [android.view.ViewGroup] */
    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void setContentView(View view, ViewGroup.LayoutParams layoutParams) {
        CollapsingToolbarDelegate collapsingToolbarDelegate = this.mToolbardelegate;
        FrameLayout contentFrameLayout = collapsingToolbarDelegate == null ? (ViewGroup) findViewById(R$id.content_frame) : collapsingToolbarDelegate.getContentFrameLayout();
        if (contentFrameLayout != null) {
            contentFrameLayout.addView(view, layoutParams);
        }
    }

    @Override // android.app.Activity
    public void setTitle(int i) {
        setTitle(getText(i));
    }

    @Override // android.app.Activity
    public void setTitle(CharSequence charSequence) {
        getToolbarDelegate().setTitle(charSequence);
    }
}