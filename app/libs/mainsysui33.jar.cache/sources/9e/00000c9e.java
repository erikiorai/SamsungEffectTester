package com.android.keyguard;

import android.R;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.PowerManager;
import android.os.SystemClock;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.EditText;
import com.android.settingslib.Utils;
import com.android.settingslib.widget.ActionBarShadowController;
import com.android.systemui.R$dimen;
import com.android.systemui.R$integer;
import com.android.systemui.R$styleable;
import java.util.ArrayList;
import java.util.Stack;

/* loaded from: mainsysui33.jar:com/android/keyguard/PasswordTextView.class */
public class PasswordTextView extends View {
    public static char DOT = 8226;
    public Interpolator mAppearInterpolator;
    public int mCharPadding;
    public Stack<CharState> mCharPool;
    public Interpolator mDisappearInterpolator;
    public int mDotSize;
    public final Paint mDrawPaint;
    public Interpolator mFastOutSlowInInterpolator;
    public final int mGravity;
    public PowerManager mPM;
    public boolean mShowPassword;
    public String mText;
    public ArrayList<CharState> mTextChars;
    public int mTextHeightRaw;
    public UserActivityListener mUserActivityListener;

    /* loaded from: mainsysui33.jar:com/android/keyguard/PasswordTextView$CharState.class */
    public class CharState {
        public float currentDotSizeFactor;
        public float currentTextSizeFactor;
        public float currentTextTranslationY;
        public float currentWidthFactor;
        public boolean dotAnimationIsGrowing;
        public Animator dotAnimator;
        public Animator.AnimatorListener dotFinishListener;
        public ValueAnimator.AnimatorUpdateListener dotSizeUpdater;
        public Runnable dotSwapperRunnable;
        public boolean isDotSwapPending;
        public Animator.AnimatorListener removeEndListener;
        public boolean textAnimationIsGrowing;
        public ValueAnimator textAnimator;
        public Animator.AnimatorListener textFinishListener;
        public ValueAnimator.AnimatorUpdateListener textSizeUpdater;
        public ValueAnimator textTranslateAnimator;
        public Animator.AnimatorListener textTranslateFinishListener;
        public ValueAnimator.AnimatorUpdateListener textTranslationUpdater;
        public char whichChar;
        public boolean widthAnimationIsGrowing;
        public ValueAnimator widthAnimator;
        public Animator.AnimatorListener widthFinishListener;
        public ValueAnimator.AnimatorUpdateListener widthUpdater;

        public CharState() {
            this.currentTextTranslationY = 1.0f;
            this.removeEndListener = new AnimatorListenerAdapter() { // from class: com.android.keyguard.PasswordTextView.CharState.1
                public boolean mCancelled;

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationCancel(Animator animator) {
                    this.mCancelled = true;
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    if (this.mCancelled) {
                        return;
                    }
                    PasswordTextView.this.mTextChars.remove(CharState.this);
                    PasswordTextView.this.mCharPool.push(CharState.this);
                    CharState.this.reset();
                    CharState charState = CharState.this;
                    charState.cancelAnimator(charState.textTranslateAnimator);
                    CharState.this.textTranslateAnimator = null;
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationStart(Animator animator) {
                    this.mCancelled = false;
                }
            };
            this.dotFinishListener = new AnimatorListenerAdapter() { // from class: com.android.keyguard.PasswordTextView.CharState.2
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    CharState.this.dotAnimator = null;
                }
            };
            this.textFinishListener = new AnimatorListenerAdapter() { // from class: com.android.keyguard.PasswordTextView.CharState.3
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    CharState.this.textAnimator = null;
                }
            };
            this.textTranslateFinishListener = new AnimatorListenerAdapter() { // from class: com.android.keyguard.PasswordTextView.CharState.4
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    CharState.this.textTranslateAnimator = null;
                }
            };
            this.widthFinishListener = new AnimatorListenerAdapter() { // from class: com.android.keyguard.PasswordTextView.CharState.5
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    CharState.this.widthAnimator = null;
                }
            };
            this.dotSizeUpdater = new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.keyguard.PasswordTextView.CharState.6
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    CharState.this.currentDotSizeFactor = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                    PasswordTextView.this.invalidate();
                }
            };
            this.textSizeUpdater = new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.keyguard.PasswordTextView.CharState.7
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    boolean isCharVisibleForA11y = CharState.this.isCharVisibleForA11y();
                    CharState charState = CharState.this;
                    float f = charState.currentTextSizeFactor;
                    charState.currentTextSizeFactor = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                    if (isCharVisibleForA11y != CharState.this.isCharVisibleForA11y()) {
                        CharState charState2 = CharState.this;
                        charState2.currentTextSizeFactor = f;
                        CharSequence transformedText = PasswordTextView.this.getTransformedText();
                        CharState.this.currentTextSizeFactor = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                        int indexOf = PasswordTextView.this.mTextChars.indexOf(CharState.this);
                        if (indexOf >= 0) {
                            PasswordTextView.this.sendAccessibilityEventTypeViewTextChanged(transformedText, indexOf, 1, 1);
                        }
                    }
                    PasswordTextView.this.invalidate();
                }
            };
            this.textTranslationUpdater = new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.keyguard.PasswordTextView.CharState.8
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    CharState.this.currentTextTranslationY = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                    PasswordTextView.this.invalidate();
                }
            };
            this.widthUpdater = new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.keyguard.PasswordTextView.CharState.9
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    CharState.this.currentWidthFactor = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                    PasswordTextView.this.invalidate();
                }
            };
            this.dotSwapperRunnable = new Runnable() { // from class: com.android.keyguard.PasswordTextView.CharState.10
                @Override // java.lang.Runnable
                public void run() {
                    CharState.this.performSwap();
                    CharState.this.isDotSwapPending = false;
                }
            };
        }

        public final void cancelAnimator(Animator animator) {
            if (animator != null) {
                animator.cancel();
            }
        }

        public float draw(Canvas canvas, float f, int i, float f2, float f3) {
            float f4 = this.currentTextSizeFactor;
            boolean z = true;
            boolean z2 = f4 > ActionBarShadowController.ELEVATION_LOW;
            if (this.currentDotSizeFactor <= ActionBarShadowController.ELEVATION_LOW) {
                z = false;
            }
            float f5 = f3 * this.currentWidthFactor;
            if (z2) {
                float f6 = i;
                float f7 = f6 / 2.0f;
                float f8 = this.currentTextTranslationY;
                canvas.save();
                canvas.translate((f5 / 2.0f) + f, (f7 * f4) + f2 + (f6 * f8 * 0.8f));
                float f9 = this.currentTextSizeFactor;
                canvas.scale(f9, f9);
                canvas.drawText(Character.toString(this.whichChar), ActionBarShadowController.ELEVATION_LOW, ActionBarShadowController.ELEVATION_LOW, PasswordTextView.this.mDrawPaint);
                canvas.restore();
            }
            if (z) {
                canvas.save();
                canvas.translate(f + (f5 / 2.0f), f2);
                canvas.drawCircle(ActionBarShadowController.ELEVATION_LOW, ActionBarShadowController.ELEVATION_LOW, (PasswordTextView.this.mDotSize / 2) * this.currentDotSizeFactor, PasswordTextView.this.mDrawPaint);
                canvas.restore();
            }
            return f5 + (PasswordTextView.this.mCharPadding * this.currentWidthFactor);
        }

        public boolean isCharVisibleForA11y() {
            boolean z = this.textAnimator != null && this.textAnimationIsGrowing;
            boolean z2 = true;
            if (this.currentTextSizeFactor <= ActionBarShadowController.ELEVATION_LOW) {
                z2 = z;
            }
            return z2;
        }

        public final void performSwap() {
            startTextDisappearAnimation(0L);
            startDotAppearAnimation(30L);
        }

        public final void postDotSwap(long j) {
            removeDotSwapCallbacks();
            PasswordTextView.this.postDelayed(this.dotSwapperRunnable, j);
            this.isDotSwapPending = true;
        }

        public final void removeDotSwapCallbacks() {
            PasswordTextView.this.removeCallbacks(this.dotSwapperRunnable);
            this.isDotSwapPending = false;
        }

        public void reset() {
            this.whichChar = (char) 0;
            this.currentTextSizeFactor = ActionBarShadowController.ELEVATION_LOW;
            this.currentDotSizeFactor = ActionBarShadowController.ELEVATION_LOW;
            this.currentWidthFactor = ActionBarShadowController.ELEVATION_LOW;
            cancelAnimator(this.textAnimator);
            this.textAnimator = null;
            cancelAnimator(this.dotAnimator);
            this.dotAnimator = null;
            cancelAnimator(this.widthAnimator);
            this.widthAnimator = null;
            this.currentTextTranslationY = 1.0f;
            removeDotSwapCallbacks();
        }

        public void startAppearAnimation() {
            boolean z = !PasswordTextView.this.mShowPassword && (this.dotAnimator == null || !this.dotAnimationIsGrowing);
            boolean z2 = PasswordTextView.this.mShowPassword && (this.textAnimator == null || !this.textAnimationIsGrowing);
            boolean z3 = true;
            if (this.widthAnimator != null) {
                z3 = !this.widthAnimationIsGrowing;
            }
            if (z) {
                startDotAppearAnimation(0L);
            }
            if (z2) {
                startTextAppearAnimation();
            }
            if (z3) {
                startWidthAppearAnimation();
            }
            if (PasswordTextView.this.mShowPassword) {
                postDotSwap(1300L);
            }
        }

        public final void startDotAppearAnimation(long j) {
            cancelAnimator(this.dotAnimator);
            if (PasswordTextView.this.mShowPassword) {
                ValueAnimator ofFloat = ValueAnimator.ofFloat(this.currentDotSizeFactor, 1.0f);
                ofFloat.addUpdateListener(this.dotSizeUpdater);
                ofFloat.setDuration((1.0f - this.currentDotSizeFactor) * 160.0f);
                ofFloat.addListener(this.dotFinishListener);
                ofFloat.setStartDelay(j);
                ofFloat.start();
                this.dotAnimator = ofFloat;
            } else {
                ValueAnimator ofFloat2 = ValueAnimator.ofFloat(this.currentDotSizeFactor, 1.5f);
                ofFloat2.addUpdateListener(this.dotSizeUpdater);
                ofFloat2.setInterpolator(PasswordTextView.this.mAppearInterpolator);
                ofFloat2.setDuration(160L);
                ValueAnimator ofFloat3 = ValueAnimator.ofFloat(1.5f, 1.0f);
                ofFloat3.addUpdateListener(this.dotSizeUpdater);
                ofFloat3.setDuration(160L);
                ofFloat3.addListener(this.dotFinishListener);
                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.playSequentially(ofFloat2, ofFloat3);
                animatorSet.setStartDelay(j);
                animatorSet.start();
                this.dotAnimator = animatorSet;
            }
            this.dotAnimationIsGrowing = true;
        }

        public final void startDotDisappearAnimation(long j) {
            cancelAnimator(this.dotAnimator);
            ValueAnimator ofFloat = ValueAnimator.ofFloat(this.currentDotSizeFactor, ActionBarShadowController.ELEVATION_LOW);
            ofFloat.addUpdateListener(this.dotSizeUpdater);
            ofFloat.addListener(this.dotFinishListener);
            ofFloat.setInterpolator(PasswordTextView.this.mDisappearInterpolator);
            ofFloat.setDuration(Math.min(this.currentDotSizeFactor, 1.0f) * 160.0f);
            ofFloat.setStartDelay(j);
            ofFloat.start();
            this.dotAnimator = ofFloat;
            this.dotAnimationIsGrowing = false;
        }

        /* JADX WARN: Code restructure failed: missing block: B:25:0x0066, code lost:
            if (r4.widthAnimator != null) goto L27;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public void startRemoveAnimation(long j, long j2) {
            boolean z;
            boolean z2 = (this.currentDotSizeFactor > ActionBarShadowController.ELEVATION_LOW && this.dotAnimator == null) || (this.dotAnimator != null && this.dotAnimationIsGrowing);
            boolean z3 = (this.currentTextSizeFactor > ActionBarShadowController.ELEVATION_LOW && this.textAnimator == null) || (this.textAnimator != null && this.textAnimationIsGrowing);
            if (this.currentWidthFactor > ActionBarShadowController.ELEVATION_LOW) {
                z = true;
            }
            z = this.widthAnimator != null && this.widthAnimationIsGrowing;
            if (z2) {
                startDotDisappearAnimation(j);
            }
            if (z3) {
                startTextDisappearAnimation(j);
            }
            if (z) {
                startWidthDisappearAnimation(j2);
            }
        }

        public final void startTextAppearAnimation() {
            cancelAnimator(this.textAnimator);
            ValueAnimator ofFloat = ValueAnimator.ofFloat(this.currentTextSizeFactor, 1.0f);
            this.textAnimator = ofFloat;
            ofFloat.addUpdateListener(this.textSizeUpdater);
            this.textAnimator.addListener(this.textFinishListener);
            this.textAnimator.setInterpolator(PasswordTextView.this.mAppearInterpolator);
            this.textAnimator.setDuration((1.0f - this.currentTextSizeFactor) * 160.0f);
            this.textAnimator.start();
            this.textAnimationIsGrowing = true;
            if (this.textTranslateAnimator == null) {
                ValueAnimator ofFloat2 = ValueAnimator.ofFloat(1.0f, ActionBarShadowController.ELEVATION_LOW);
                this.textTranslateAnimator = ofFloat2;
                ofFloat2.addUpdateListener(this.textTranslationUpdater);
                this.textTranslateAnimator.addListener(this.textTranslateFinishListener);
                this.textTranslateAnimator.setInterpolator(PasswordTextView.this.mAppearInterpolator);
                this.textTranslateAnimator.setDuration(160L);
                this.textTranslateAnimator.start();
            }
        }

        public final void startTextDisappearAnimation(long j) {
            cancelAnimator(this.textAnimator);
            ValueAnimator ofFloat = ValueAnimator.ofFloat(this.currentTextSizeFactor, ActionBarShadowController.ELEVATION_LOW);
            this.textAnimator = ofFloat;
            ofFloat.addUpdateListener(this.textSizeUpdater);
            this.textAnimator.addListener(this.textFinishListener);
            this.textAnimator.setInterpolator(PasswordTextView.this.mDisappearInterpolator);
            this.textAnimator.setDuration(this.currentTextSizeFactor * 160.0f);
            this.textAnimator.setStartDelay(j);
            this.textAnimator.start();
            this.textAnimationIsGrowing = false;
        }

        public final void startWidthAppearAnimation() {
            cancelAnimator(this.widthAnimator);
            ValueAnimator ofFloat = ValueAnimator.ofFloat(this.currentWidthFactor, 1.0f);
            this.widthAnimator = ofFloat;
            ofFloat.addUpdateListener(this.widthUpdater);
            this.widthAnimator.addListener(this.widthFinishListener);
            this.widthAnimator.setDuration((1.0f - this.currentWidthFactor) * 160.0f);
            this.widthAnimator.start();
            this.widthAnimationIsGrowing = true;
        }

        public final void startWidthDisappearAnimation(long j) {
            cancelAnimator(this.widthAnimator);
            ValueAnimator ofFloat = ValueAnimator.ofFloat(this.currentWidthFactor, ActionBarShadowController.ELEVATION_LOW);
            this.widthAnimator = ofFloat;
            ofFloat.addUpdateListener(this.widthUpdater);
            this.widthAnimator.addListener(this.widthFinishListener);
            this.widthAnimator.addListener(this.removeEndListener);
            this.widthAnimator.setDuration(this.currentWidthFactor * 160.0f);
            this.widthAnimator.setStartDelay(j);
            this.widthAnimator.start();
            this.widthAnimationIsGrowing = false;
        }

        public void swapToDotWhenAppearFinished() {
            removeDotSwapCallbacks();
            ValueAnimator valueAnimator = this.textAnimator;
            if (valueAnimator != null) {
                postDotSwap((valueAnimator.getDuration() - this.textAnimator.getCurrentPlayTime()) + 100);
            } else {
                performSwap();
            }
        }
    }

    /* loaded from: mainsysui33.jar:com/android/keyguard/PasswordTextView$UserActivityListener.class */
    public interface UserActivityListener {
        void onUserActivity();
    }

    public PasswordTextView(Context context) {
        this(context, null);
    }

    public PasswordTextView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public PasswordTextView(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public PasswordTextView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mTextChars = new ArrayList<>();
        this.mText = "";
        this.mCharPool = new Stack<>();
        Paint paint = new Paint();
        this.mDrawPaint = paint;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.View);
        boolean z = true;
        try {
            boolean z2 = obtainStyledAttributes.getBoolean(19, true);
            boolean z3 = obtainStyledAttributes.getBoolean(20, true);
            setFocusable(z2);
            setFocusableInTouchMode(z3);
            obtainStyledAttributes.recycle();
            obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.PasswordTextView);
            try {
                this.mTextHeightRaw = obtainStyledAttributes.getInt(R$styleable.PasswordTextView_scaledTextSize, 0);
                this.mGravity = obtainStyledAttributes.getInt(R$styleable.PasswordTextView_android_gravity, 17);
                this.mDotSize = obtainStyledAttributes.getDimensionPixelSize(R$styleable.PasswordTextView_dotSize, getContext().getResources().getDimensionPixelSize(R$dimen.password_dot_size));
                this.mCharPadding = obtainStyledAttributes.getDimensionPixelSize(R$styleable.PasswordTextView_charPadding, getContext().getResources().getDimensionPixelSize(R$dimen.password_char_padding));
                paint.setColor(obtainStyledAttributes.getColor(R$styleable.PasswordTextView_android_textColor, -1));
                obtainStyledAttributes.recycle();
                paint.setFlags(129);
                paint.setTextAlign(Paint.Align.CENTER);
                paint.setTypeface(Typeface.create(context.getString(17039995), 0));
                this.mShowPassword = Settings.System.getInt(((View) this).mContext.getContentResolver(), "show_password", 1) != 1 ? false : z;
                this.mAppearInterpolator = AnimationUtils.loadInterpolator(((View) this).mContext, 17563662);
                this.mDisappearInterpolator = AnimationUtils.loadInterpolator(((View) this).mContext, 17563663);
                this.mFastOutSlowInInterpolator = AnimationUtils.loadInterpolator(((View) this).mContext, 17563661);
                this.mPM = (PowerManager) ((View) this).mContext.getSystemService("power");
            } finally {
            }
        } finally {
        }
    }

    public void append(char c) {
        CharState charState;
        int size = this.mTextChars.size();
        CharSequence transformedText = getTransformedText();
        String str = this.mText + c;
        this.mText = str;
        int length = str.length();
        if (length > size) {
            charState = obtainCharState(c);
            this.mTextChars.add(charState);
        } else {
            charState = this.mTextChars.get(length - 1);
            charState.whichChar = c;
        }
        charState.startAppearAnimation();
        if (length > 1) {
            CharState charState2 = this.mTextChars.get(length - 2);
            if (charState2.isDotSwapPending) {
                charState2.swapToDotWhenAppearFinished();
            }
        }
        userActivity();
        sendAccessibilityEventTypeViewTextChanged(transformedText, transformedText.length(), 0, 1);
    }

    public void deleteLastChar() {
        int length = this.mText.length();
        CharSequence transformedText = getTransformedText();
        if (length > 0) {
            int i = length - 1;
            this.mText = this.mText.substring(0, i);
            this.mTextChars.get(i).startRemoveAnimation(0L, 0L);
            sendAccessibilityEventTypeViewTextChanged(transformedText, transformedText.length() - 1, 1, 0);
        }
        userActivity();
    }

    public final Rect getCharBounds() {
        this.mDrawPaint.setTextSize(this.mTextHeightRaw * getResources().getDisplayMetrics().scaledDensity);
        Rect rect = new Rect();
        this.mDrawPaint.getTextBounds("0", 0, 1, rect);
        return rect;
    }

    public final float getDrawingWidth() {
        int size = this.mTextChars.size();
        Rect charBounds = getCharBounds();
        int i = charBounds.right;
        int i2 = charBounds.left;
        int i3 = 0;
        for (int i4 = 0; i4 < size; i4++) {
            CharState charState = this.mTextChars.get(i4);
            int i5 = i3;
            if (i4 != 0) {
                i5 = (int) (i3 + (this.mCharPadding * charState.currentWidthFactor));
            }
            i3 = (int) (i5 + ((i - i2) * charState.currentWidthFactor));
        }
        return i3;
    }

    public String getText() {
        return this.mText;
    }

    public final CharSequence getTransformedText() {
        int size = this.mTextChars.size();
        StringBuilder sb = new StringBuilder(size);
        for (int i = 0; i < size; i++) {
            CharState charState = this.mTextChars.get(i);
            if (charState.dotAnimator == null || charState.dotAnimationIsGrowing) {
                sb.append(charState.isCharVisibleForA11y() ? charState.whichChar : DOT);
            }
        }
        return sb;
    }

    @Override // android.view.View
    public boolean hasOverlappingRendering() {
        return false;
    }

    public final CharState obtainCharState(char c) {
        CharState pop;
        if (this.mCharPool.isEmpty()) {
            pop = new CharState();
        } else {
            pop = this.mCharPool.pop();
            pop.reset();
        }
        pop.whichChar = c;
        return pop;
    }

    @Override // android.view.View
    public void onConfigurationChanged(Configuration configuration) {
        this.mTextHeightRaw = getContext().getResources().getInteger(R$integer.scaled_password_text_size);
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        float width;
        float drawingWidth = getDrawingWidth();
        int i = this.mGravity;
        if ((i & 7) == 3) {
            width = ((i & 8388608) == 0 || getLayoutDirection() != 1) ? getPaddingLeft() : (getWidth() - getPaddingRight()) - drawingWidth;
        } else {
            width = (getWidth() - getPaddingRight()) - drawingWidth;
            float width2 = (getWidth() / 2.0f) - (drawingWidth / 2.0f);
            if (width2 > ActionBarShadowController.ELEVATION_LOW) {
                width = width2;
            }
        }
        int size = this.mTextChars.size();
        Rect charBounds = getCharBounds();
        int i2 = charBounds.bottom;
        int i3 = charBounds.top;
        float height = (((getHeight() - getPaddingBottom()) - getPaddingTop()) / 2) + getPaddingTop();
        canvas.clipRect(getPaddingLeft(), getPaddingTop(), getWidth() - getPaddingRight(), getHeight() - getPaddingBottom());
        float f = charBounds.right - charBounds.left;
        for (int i4 = 0; i4 < size; i4++) {
            width += this.mTextChars.get(i4).draw(canvas, width, i2 - i3, height, f);
        }
    }

    @Override // android.view.View
    public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(accessibilityEvent);
        accessibilityEvent.setClassName(EditText.class.getName());
        accessibilityEvent.setPassword(true);
    }

    @Override // android.view.View
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setClassName(EditText.class.getName());
        accessibilityNodeInfo.setPassword(true);
        accessibilityNodeInfo.setText(getTransformedText());
        accessibilityNodeInfo.setEditable(true);
        accessibilityNodeInfo.setInputType(16);
    }

    public void reloadColors() {
        this.mDrawPaint.setColor(Utils.getColorAttr(getContext(), 16842806).getDefaultColor());
    }

    public void reset(boolean z, boolean z2) {
        CharSequence transformedText = getTransformedText();
        this.mText = "";
        int size = this.mTextChars.size();
        int i = size - 1;
        int i2 = i / 2;
        int i3 = 0;
        while (i3 < size) {
            CharState charState = this.mTextChars.get(i3);
            if (z) {
                charState.startRemoveAnimation(Math.min((i3 <= i2 ? i3 * 2 : i - (((i3 - i2) - 1) * 2)) * 40, 200L), Math.min(40 * i, 200L) + 160);
                charState.removeDotSwapCallbacks();
            } else {
                this.mCharPool.push(charState);
            }
            i3++;
        }
        if (!z) {
            this.mTextChars.clear();
        }
        if (z2) {
            sendAccessibilityEventTypeViewTextChanged(transformedText, 0, transformedText.length(), 0);
        }
    }

    public void sendAccessibilityEventTypeViewTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        if (AccessibilityManager.getInstance(((View) this).mContext).isEnabled()) {
            if (isFocused() || (isSelected() && isShown())) {
                AccessibilityEvent obtain = AccessibilityEvent.obtain(16);
                obtain.setFromIndex(i);
                obtain.setRemovedCount(i2);
                obtain.setAddedCount(i3);
                obtain.setBeforeText(charSequence);
                CharSequence transformedText = getTransformedText();
                if (!TextUtils.isEmpty(transformedText)) {
                    obtain.getText().add(transformedText);
                }
                obtain.setPassword(true);
                sendAccessibilityEventUnchecked(obtain);
            }
        }
    }

    public void setUserActivityListener(UserActivityListener userActivityListener) {
        this.mUserActivityListener = userActivityListener;
    }

    public final void userActivity() {
        this.mPM.userActivity(SystemClock.uptimeMillis(), false);
        UserActivityListener userActivityListener = this.mUserActivityListener;
        if (userActivityListener != null) {
            userActivityListener.onUserActivity();
        }
    }
}