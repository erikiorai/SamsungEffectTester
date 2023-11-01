package com.android.systemui.qs.tileimpl;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import com.android.internal.annotations.VisibleForTesting;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.qs.SlashDrawable;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/tileimpl/SlashImageView.class */
public class SlashImageView extends ImageView {
    public boolean mAnimationEnabled;
    @VisibleForTesting
    public SlashDrawable mSlash;

    public SlashImageView(Context context) {
        super(context);
        this.mAnimationEnabled = true;
    }

    public void ensureSlashDrawable() {
        if (this.mSlash == null) {
            SlashDrawable slashDrawable = new SlashDrawable(getDrawable());
            this.mSlash = slashDrawable;
            slashDrawable.setAnimationEnabled(this.mAnimationEnabled);
            super.setImageDrawable(this.mSlash);
        }
    }

    public boolean getAnimationEnabled() {
        return this.mAnimationEnabled;
    }

    public SlashDrawable getSlash() {
        return this.mSlash;
    }

    public void setAnimationEnabled(boolean z) {
        this.mAnimationEnabled = z;
    }

    @Override // android.widget.ImageView
    public void setImageDrawable(Drawable drawable) {
        if (drawable == null) {
            this.mSlash = null;
            super.setImageDrawable(null);
            return;
        }
        SlashDrawable slashDrawable = this.mSlash;
        if (slashDrawable == null) {
            setImageLevel(drawable.getLevel());
            super.setImageDrawable(drawable);
            return;
        }
        slashDrawable.setAnimationEnabled(this.mAnimationEnabled);
        this.mSlash.setDrawable(drawable);
    }

    public void setImageViewDrawable(SlashDrawable slashDrawable) {
        super.setImageDrawable(slashDrawable);
    }

    public void setSlash(SlashDrawable slashDrawable) {
        this.mSlash = slashDrawable;
    }

    public final void setSlashState(QSTile.SlashState slashState) {
        ensureSlashDrawable();
        this.mSlash.setRotation(slashState.rotation);
        this.mSlash.setSlashed(slashState.isSlashed);
    }

    public void setState(QSTile.SlashState slashState, Drawable drawable) {
        if (slashState != null) {
            setImageDrawable(drawable);
            setSlashState(slashState);
            return;
        }
        this.mSlash = null;
        setImageDrawable(drawable);
    }
}