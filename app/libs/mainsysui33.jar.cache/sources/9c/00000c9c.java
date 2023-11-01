package com.android.keyguard;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.PowerManager;
import android.os.SystemClock;
import android.provider.Settings;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityManager;
import android.widget.TextView;
import com.android.internal.widget.LockPatternUtils;
import com.android.settingslib.Utils;
import com.android.systemui.R$array;
import com.android.systemui.R$attr;
import com.android.systemui.R$id;
import com.android.systemui.R$layout;
import com.android.systemui.R$style;
import com.android.systemui.R$styleable;

/* loaded from: mainsysui33.jar:com/android/keyguard/NumPadKey.class */
public class NumPadKey extends ViewGroup implements NumPadAnimationListener {
    public static String[] sKlondike;
    public NumPadAnimator mAnimator;
    public int mDigit;
    public final TextView mDigitText;
    public final TextView mKlondikeText;
    public View.OnClickListener mListener;
    public final LockPatternUtils mLockPatternUtils;
    public int mOrientation;
    public final PowerManager mPM;
    public PasswordTextView mTextView;
    public int mTextViewResId;

    public NumPadKey(Context context) {
        this(context, null);
    }

    public NumPadKey(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R$attr.numPadKeyStyle);
    }

    public NumPadKey(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, R$layout.keyguard_num_pad_key);
    }

    public NumPadKey(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i);
        this.mDigit = -1;
        this.mListener = new View.OnClickListener() { // from class: com.android.keyguard.NumPadKey.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                View findViewById;
                if (NumPadKey.this.mTextView == null && NumPadKey.this.mTextViewResId > 0 && (findViewById = NumPadKey.this.getRootView().findViewById(NumPadKey.this.mTextViewResId)) != null && (findViewById instanceof PasswordTextView)) {
                    NumPadKey.this.mTextView = (PasswordTextView) findViewById;
                }
                if (NumPadKey.this.mTextView != null && NumPadKey.this.mTextView.isEnabled()) {
                    NumPadKey.this.mTextView.append(Character.forDigit(NumPadKey.this.mDigit, 10));
                }
                NumPadKey.this.userActivity();
            }
        };
        setFocusable(true);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.NumPadKey, i, i2);
        try {
            this.mDigit = obtainStyledAttributes.getInt(R$styleable.NumPadKey_digit, this.mDigit);
            this.mTextViewResId = obtainStyledAttributes.getResourceId(R$styleable.NumPadKey_textView, 0);
            obtainStyledAttributes.recycle();
            setOnClickListener(this.mListener);
            setOnHoverListener(new LiftToActivateListener((AccessibilityManager) context.getSystemService("accessibility")));
            this.mLockPatternUtils = new LockPatternUtils(context);
            this.mPM = (PowerManager) ((ViewGroup) this).mContext.getSystemService("power");
            ((LayoutInflater) getContext().getSystemService("layout_inflater")).inflate(i2, (ViewGroup) this, true);
            TextView textView = (TextView) findViewById(R$id.digit_text);
            this.mDigitText = textView;
            textView.setText(Integer.toString(this.mDigit));
            this.mKlondikeText = (TextView) findViewById(R$id.klondike_text);
            updateText();
            setContentDescription(textView.getText().toString());
            Drawable background = getBackground();
            if (background instanceof GradientDrawable) {
                this.mAnimator = new NumPadAnimator(context, background.mutate(), R$style.NumPadKey, textView, null);
            } else {
                this.mAnimator = null;
            }
        } catch (Throwable th) {
            obtainStyledAttributes.recycle();
            throw th;
        }
    }

    public void doHapticKeyClick() {
        performHapticFeedback(1, 1);
    }

    @Override // android.view.View
    public boolean hasOverlappingRendering() {
        return false;
    }

    @Override // android.view.View
    public void onConfigurationChanged(Configuration configuration) {
        this.mOrientation = configuration.orientation;
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int measuredHeight = this.mDigitText.getMeasuredHeight();
        int measuredHeight2 = this.mKlondikeText.getMeasuredHeight();
        int height = (getHeight() / 2) - ((measuredHeight + measuredHeight2) / 2);
        int width = getWidth() / 2;
        int measuredWidth = width - (this.mDigitText.getMeasuredWidth() / 2);
        int i5 = measuredHeight + height;
        TextView textView = this.mDigitText;
        textView.layout(measuredWidth, height, textView.getMeasuredWidth() + measuredWidth, i5);
        int i6 = (int) (i5 - (measuredHeight2 * 0.35f));
        int measuredWidth2 = width - (this.mKlondikeText.getMeasuredWidth() / 2);
        TextView textView2 = this.mKlondikeText;
        textView2.layout(measuredWidth2, i6, textView2.getMeasuredWidth() + measuredWidth2, measuredHeight2 + i6);
        NumPadAnimator numPadAnimator = this.mAnimator;
        if (numPadAnimator != null) {
            numPadAnimator.onLayout(i4 - i2);
        }
    }

    @Override // android.view.View
    public void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        measureChildren(i, i2);
        int measuredWidth = getMeasuredWidth();
        int i3 = measuredWidth;
        if (this.mAnimator == null || this.mOrientation == 2) {
            i3 = (int) (measuredWidth * 0.66f);
        }
        setMeasuredDimension(getMeasuredWidth(), i3);
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        NumPadAnimator numPadAnimator;
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 0) {
            doHapticKeyClick();
            NumPadAnimator numPadAnimator2 = this.mAnimator;
            if (numPadAnimator2 != null) {
                numPadAnimator2.expand();
            }
        } else if ((actionMasked == 1 || actionMasked == 3) && (numPadAnimator = this.mAnimator) != null) {
            numPadAnimator.contract();
        }
        return super.onTouchEvent(motionEvent);
    }

    public void reloadColors() {
        int defaultColor = Utils.getColorAttr(getContext(), 16842806).getDefaultColor();
        int defaultColor2 = Utils.getColorAttr(getContext(), 16842808).getDefaultColor();
        this.mDigitText.setTextColor(defaultColor);
        this.mKlondikeText.setTextColor(defaultColor2);
        NumPadAnimator numPadAnimator = this.mAnimator;
        if (numPadAnimator != null) {
            numPadAnimator.reloadColors(getContext());
        }
    }

    public void setDigit(int i) {
        this.mDigit = i;
        updateText();
    }

    @Override // com.android.keyguard.NumPadAnimationListener
    public void setProgress(float f) {
        NumPadAnimator numPadAnimator = this.mAnimator;
        if (numPadAnimator != null) {
            numPadAnimator.setProgress(f);
        }
    }

    public final void updateText() {
        boolean z = false;
        if (Settings.System.getInt(getContext().getContentResolver(), "lockscreen_scramble_pin_layout", 0) == 1) {
            z = true;
        }
        int i = this.mDigit;
        if (i >= 0) {
            this.mDigitText.setText(Integer.toString(i));
            if (sKlondike == null) {
                sKlondike = getResources().getStringArray(R$array.lockscreen_num_pad_klondike);
            }
            String[] strArr = sKlondike;
            if (strArr != null) {
                int length = strArr.length;
                int i2 = this.mDigit;
                if (length > i2) {
                    String str = strArr[i2];
                    if (str.length() > 0 || z) {
                        this.mKlondikeText.setText(str);
                    } else if (this.mKlondikeText.getVisibility() != 8) {
                        this.mKlondikeText.setVisibility(4);
                    }
                }
            }
        }
    }

    public void userActivity() {
        this.mPM.userActivity(SystemClock.uptimeMillis(), false);
    }
}