package com.android.keyguard;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.text.method.SingleLineTransformationMethod;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;
import com.android.systemui.R$styleable;
import java.util.Locale;

/* loaded from: mainsysui33.jar:com/android/keyguard/CarrierText.class */
public class CarrierText extends TextView {
    public final boolean mShowAirplaneMode;
    public final boolean mShowMissingSim;

    /* loaded from: mainsysui33.jar:com/android/keyguard/CarrierText$CarrierTextTransformationMethod.class */
    public static class CarrierTextTransformationMethod extends SingleLineTransformationMethod {
        public final boolean mAllCaps;
        public final Locale mLocale;

        public CarrierTextTransformationMethod(Context context, boolean z) {
            this.mLocale = context.getResources().getConfiguration().locale;
            this.mAllCaps = z;
        }

        @Override // android.text.method.ReplacementTransformationMethod, android.text.method.TransformationMethod
        public CharSequence getTransformation(CharSequence charSequence, View view) {
            CharSequence transformation = super.getTransformation(charSequence, view);
            String str = transformation;
            if (this.mAllCaps) {
                str = transformation;
                if (transformation != null) {
                    str = transformation.toString().toUpperCase(this.mLocale);
                }
            }
            return str;
        }
    }

    public CarrierText(Context context) {
        this(context, null);
    }

    public CarrierText(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(attributeSet, R$styleable.CarrierText, 0, 0);
        try {
            boolean z = obtainStyledAttributes.getBoolean(R$styleable.CarrierText_allCaps, false);
            this.mShowAirplaneMode = obtainStyledAttributes.getBoolean(R$styleable.CarrierText_showAirplaneMode, false);
            this.mShowMissingSim = obtainStyledAttributes.getBoolean(R$styleable.CarrierText_showMissingSim, false);
            obtainStyledAttributes.recycle();
            setTransformationMethod(new CarrierTextTransformationMethod(((TextView) this).mContext, z));
        } catch (Throwable th) {
            obtainStyledAttributes.recycle();
            throw th;
        }
    }

    public boolean getShowAirplaneMode() {
        return this.mShowAirplaneMode;
    }

    public boolean getShowMissingSim() {
        return this.mShowMissingSim;
    }

    @Override // android.widget.TextView, android.view.View
    public void onVisibilityChanged(View view, int i) {
        super.onVisibilityChanged(view, i);
        if (i == 0) {
            setEllipsize(TextUtils.TruncateAt.MARQUEE);
        } else {
            setEllipsize(TextUtils.TruncateAt.END);
        }
    }
}