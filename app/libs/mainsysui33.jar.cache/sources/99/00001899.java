package com.android.systemui.keyguard;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;

/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/KeyguardIndication.class */
public class KeyguardIndication {
    public final Drawable mBackground;
    public final Drawable mIcon;
    public final CharSequence mMessage;
    public final Long mMinVisibilityMillis;
    public final View.OnClickListener mOnClickListener;
    public final ColorStateList mTextColor;

    /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/KeyguardIndication$Builder.class */
    public static class Builder {
        public Drawable mBackground;
        public Drawable mIcon;
        public CharSequence mMessage;
        public Long mMinVisibilityMillis;
        public View.OnClickListener mOnClickListener;
        public ColorStateList mTextColor;

        public KeyguardIndication build() {
            if (TextUtils.isEmpty(this.mMessage) && this.mIcon == null) {
                throw new IllegalStateException("message or icon must be set");
            }
            ColorStateList colorStateList = this.mTextColor;
            if (colorStateList != null) {
                return new KeyguardIndication(this.mMessage, colorStateList, this.mIcon, this.mOnClickListener, this.mBackground, this.mMinVisibilityMillis, null);
            }
            throw new IllegalStateException("text color must be set");
        }

        public Builder setBackground(Drawable drawable) {
            this.mBackground = drawable;
            return this;
        }

        public Builder setClickListener(View.OnClickListener onClickListener) {
            this.mOnClickListener = onClickListener;
            return this;
        }

        public Builder setIcon(Drawable drawable) {
            this.mIcon = drawable;
            return this;
        }

        public Builder setMessage(CharSequence charSequence) {
            this.mMessage = charSequence;
            return this;
        }

        public Builder setMinVisibilityMillis(Long l) {
            this.mMinVisibilityMillis = l;
            return this;
        }

        public Builder setTextColor(ColorStateList colorStateList) {
            this.mTextColor = colorStateList;
            return this;
        }
    }

    public KeyguardIndication(CharSequence charSequence, ColorStateList colorStateList, Drawable drawable, View.OnClickListener onClickListener, Drawable drawable2, Long l) {
        this.mMessage = charSequence;
        this.mTextColor = colorStateList;
        this.mIcon = drawable;
        this.mOnClickListener = onClickListener;
        this.mBackground = drawable2;
        this.mMinVisibilityMillis = l;
    }

    public /* synthetic */ KeyguardIndication(CharSequence charSequence, ColorStateList colorStateList, Drawable drawable, View.OnClickListener onClickListener, Drawable drawable2, Long l, KeyguardIndication-IA r15) {
        this(charSequence, colorStateList, drawable, onClickListener, drawable2, l);
    }

    public Drawable getBackground() {
        return this.mBackground;
    }

    public View.OnClickListener getClickListener() {
        return this.mOnClickListener;
    }

    public Drawable getIcon() {
        return this.mIcon;
    }

    public CharSequence getMessage() {
        return this.mMessage;
    }

    public Long getMinVisibilityMillis() {
        return this.mMinVisibilityMillis;
    }

    public ColorStateList getTextColor() {
        return this.mTextColor;
    }

    public String toString() {
        String str = "KeyguardIndication{";
        if (!TextUtils.isEmpty(this.mMessage)) {
            str = "KeyguardIndication{mMessage=" + ((Object) this.mMessage);
        }
        String str2 = str;
        if (this.mIcon != null) {
            str2 = str + " mIcon=" + this.mIcon;
        }
        String str3 = str2;
        if (this.mOnClickListener != null) {
            str3 = str2 + " mOnClickListener=" + this.mOnClickListener;
        }
        String str4 = str3;
        if (this.mBackground != null) {
            str4 = str3 + " mBackground=" + this.mBackground;
        }
        String str5 = str4;
        if (this.mMinVisibilityMillis != null) {
            str5 = str4 + " mMinVisibilityMillis=" + this.mMinVisibilityMillis;
        }
        return str5 + "}";
    }
}