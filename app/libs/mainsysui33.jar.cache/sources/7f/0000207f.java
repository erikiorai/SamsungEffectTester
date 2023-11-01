package com.android.systemui.qs;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import com.android.systemui.qs.tileimpl.SlashImageView;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/AlphaControlledSignalTileView.class */
public class AlphaControlledSignalTileView extends SignalTileView {

    /* loaded from: mainsysui33.jar:com/android/systemui/qs/AlphaControlledSignalTileView$AlphaControlledSlashDrawable.class */
    public static class AlphaControlledSlashDrawable extends SlashDrawable {
        public AlphaControlledSlashDrawable(Drawable drawable) {
            super(drawable);
        }

        @Override // com.android.systemui.qs.SlashDrawable
        public void setDrawableTintList(ColorStateList colorStateList) {
        }

        public void setFinalTintList(ColorStateList colorStateList) {
            super.setDrawableTintList(colorStateList);
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/qs/AlphaControlledSignalTileView$AlphaControlledSlashImageView.class */
    public static class AlphaControlledSlashImageView extends SlashImageView {
        public AlphaControlledSlashImageView(Context context) {
            super(context);
        }

        @Override // com.android.systemui.qs.tileimpl.SlashImageView
        public void ensureSlashDrawable() {
            if (getSlash() == null) {
                AlphaControlledSlashDrawable alphaControlledSlashDrawable = new AlphaControlledSlashDrawable(getDrawable());
                setSlash(alphaControlledSlashDrawable);
                alphaControlledSlashDrawable.setAnimationEnabled(getAnimationEnabled());
                setImageViewDrawable(alphaControlledSlashDrawable);
            }
        }

        public void setFinalImageTintList(ColorStateList colorStateList) {
            super.setImageTintList(colorStateList);
            SlashDrawable slash = getSlash();
            if (slash != null) {
                ((AlphaControlledSlashDrawable) slash).setFinalTintList(colorStateList);
            }
        }
    }

    public AlphaControlledSignalTileView(Context context) {
        super(context);
    }

    @Override // com.android.systemui.qs.SignalTileView
    public SlashImageView createSlashImageView(Context context) {
        return new AlphaControlledSlashImageView(context);
    }
}