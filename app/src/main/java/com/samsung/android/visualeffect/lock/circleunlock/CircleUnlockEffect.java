package com.samsung.android.visualeffect.lock.circleunlock;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.provider.Settings;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.aj.effect.interpolator.QuintEaseIn;
import com.aj.effect.interpolator.QuintEaseOut;
import com.samsung.android.visualeffect.EffectDataObj;
import com.samsung.android.visualeffect.IEffectListener;
import com.samsung.android.visualeffect.IEffectView;

import java.util.HashMap;

/* loaded from: classes.dex */
public class CircleUnlockEffect extends FrameLayout implements IEffectView {
    private final int ARROW_ANIMATION_DURATION = 500;
    private final boolean DBG = true;
    private final int IN_ANIMATION_DURATION = 666;
    private final int IN_ANIMATION_DURATION_FOR_AFFORDANCE = 666;
    private final int OUT_ANIMATION_DURATION = 333;
    private final int OUT_ANIMATION_DURATION_FOR_AFFORDANCE = 700;
    private final int SHOWING_DURATION_FOR_AFFORDANCE = -200;
    private final String TAG = "VisualEffectCircleUnlockEffect";
    private ImageView arrow;
    private float arrowAlphaMax;
    private boolean arrowAnimationToggle = false;
    private ValueAnimator arrowAnimator;
    private ImageView arrowForButton;
    private int arrowForButtonId;
    private int arrowImageId;
    private int arrowWidth;
    private CircleUnlockCircle circle;
    private float circleAnimationMax;
    private float circleAnimationMin = 0.0f;
    private FrameLayout circleGroup;
    private ValueAnimator circleInAnimator;
    private ValueAnimator circleOutAnimator;
    private int circleUnlockMaxHeight;
    private int circleUnlockMaxRadius;
    private int circleUnlockMaxWidth;
    private int circleUnlockMinRadius;
    private int circleUnlockMinWidth;
    private int currentLockSequenceNumber = 0;
    private float dragAnimationValue = 0.0f;
    private float fillAnimationValueMax;
    private boolean hasNoOuterCircle;
    private int innerStrokeWidth;
    private boolean isForAffordance = false;
    private boolean isForShortcut = false;
    private boolean isShowSwipeCircle = true;
    private boolean isStroke = true;
    private boolean isUnlocked = false;
    private ImageView lockImageView;
    private int[] lockSequenceImageId;
    private int lockSequenceTotal;
    private Context mContext;
    private int minWidthOffset;
    private int outerStrokeWidth;
    private float startX;
    private float startY;
    private float strokeAnimationValue = 0.0f;

    public CircleUnlockEffect(Context context) {
        super(context);
        Log.d(TAG, "Constructor");
        mContext = context;
    }

    @Override // com.samsung.android.visualeffect.IEffectView
    public void init(EffectDataObj data) {
        if (data.circleData == null) {
            Log.d(TAG, "circleData is null");
            return;
        }
        arrowImageId = data.circleData.arrowId;
        circleUnlockMaxWidth = data.circleData.circleUnlockMaxWidth;
        outerStrokeWidth = data.circleData.outerStrokeWidth;
        innerStrokeWidth = data.circleData.innerStrokeWidth;
        hasNoOuterCircle = data.circleData.hasNoOuterCircle;
        circleUnlockMaxHeight = circleUnlockMaxWidth;
        circleUnlockMinRadius = circleUnlockMinWidth / 2;
        circleUnlockMaxRadius = circleUnlockMaxWidth / 2;
        lockSequenceImageId = data.circleData.lockSequenceImageId;
        lockSequenceTotal = lockSequenceImageId.length;
        Log.d(TAG, "arrowImageId = " + arrowImageId);
        Log.d(TAG, "circleUnlockMaxWidth = " + circleUnlockMaxWidth);
        Log.d(TAG, "outerStrokeWidth = " + outerStrokeWidth);
        Log.d(TAG, "innerStrokeWidth = " + innerStrokeWidth);
        Log.d(TAG, "hasNoOuterCircle = " + hasNoOuterCircle);
        Log.d(TAG, "lockSequenceTotal = " + lockSequenceTotal);
        setLayout();
        setAnimator();
    }

    private void setLayout() {
        circleGroup = new FrameLayout(mContext);
        circleGroup.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        addView(circleGroup, circleUnlockMaxWidth, circleUnlockMaxHeight);
        setAlphaAndVisibility(circleGroup, 0.0f);
        circle = new CircleUnlockCircle(mContext, circleUnlockMaxWidth, circleUnlockMinWidth, outerStrokeWidth, innerStrokeWidth);
        circleGroup.addView(circle);
        arrow = new ImageView(mContext);
        Drawable d = mContext.getDrawable(arrowImageId);
        arrow.setBackground(d);
        circleGroup.addView(arrow, -2, -2);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), arrowImageId, options);
        arrowWidth = options.outWidth;
        lockImageView = new ImageView(mContext);
        lockImageView.setImageResource(lockSequenceImageId[0]);
        circleGroup.addView(lockImageView, -2, -2);
    }

    private void setAnimator() {
        circleInAnimator = ValueAnimator.ofFloat(0.0f, 1.0f);
        circleInAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.samsung.android.visualeffect.lock.circleunlock.CircleUnlockEffect.1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator animation) {
                strokeAnimationValue = (((Float) animation.getAnimatedValue()).floatValue() * (1.0f - circleAnimationMin)) + circleAnimationMin;
                setAlphaAndVisibility(circleGroup, strokeAnimationValue);
                if (!hasNoOuterCircle || isForShortcut) {
                    circle.strokeAnimationUpdate(strokeAnimationValue);
                } else if (isForAffordance) {
                    dragAnimationValue = fillAnimationValueMax = strokeAnimationValue;
                    circle.strokeAnimationUpdate(strokeAnimationValue);
                    circle.dragAnimationUpdate(dragAnimationValue);
                }
            }
        });
        circleInAnimator.addListener(new Animator.AnimatorListener() { // from class: com.samsung.android.visualeffect.lock.circleunlock.CircleUnlockEffect.2
            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animation) {
                checkIsWhiteBg();
                checkPosition();
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationRepeat(Animator animation) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animation) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animation) {
            }
        });
        circleOutAnimator = ValueAnimator.ofFloat(1.0f, 0.0f);
        circleOutAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.samsung.android.visualeffect.lock.circleunlock.CircleUnlockEffect.3
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator animation) {
                float tvalue;
                float value = ((Float) animation.getAnimatedValue()).floatValue();
                strokeAnimationValue = circleAnimationMax * value;
                dragAnimationValue = fillAnimationValueMax * value;
                circle.strokeAnimationUpdate(strokeAnimationValue);
                circle.dragAnimationUpdate(dragAnimationValue);
                if (!isForAffordance) {
                    setImageInLockImageView(dragAnimationValue);
                }
                setAlphaAndVisibility(circleGroup, strokeAnimationValue);
                if (value > 0.4f) {
                    tvalue = (arrowAlphaMax * (value - 0.4f)) / 0.6f;
                } else {
                    tvalue = 0.0f;
                }
                arrow.setAlpha(tvalue);
                if (arrowForButton != null) {
                    arrowForButton.setAlpha(tvalue);
                }
            }
        });
        circleOutAnimator.addListener(new Animator.AnimatorListener() { // from class: com.samsung.android.visualeffect.lock.circleunlock.CircleUnlockEffect.4
            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animation) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationRepeat(Animator animation) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animation) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animation) {
                Log.d(TAG, "circleOutAnimator : onAnimationEnd");
                cancelAllAnimator();
            }
        });
        resetAnimatorAfterAffordance();
        arrowAnimator = ValueAnimator.ofFloat(0.0f, 1.0f);
        arrowAnimator.setInterpolator(new LinearInterpolator());
        arrowAnimator.setDuration(ARROW_ANIMATION_DURATION);
        arrowAnimator.setRepeatCount(-1);
        arrowAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.samsung.android.visualeffect.lock.circleunlock.CircleUnlockEffect.5
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = ((Float) animation.getAnimatedValue()).floatValue();
                if (arrowAnimationToggle) {
                    value = 1.0f - value;
                }
                float value2 = dragAnimationValue > 0.4f ? 0.0f : ((0.4f - dragAnimationValue) * value) / 0.4f;
                arrow.setAlpha(value2);
                if (arrowForButton != null) {
                    arrowForButton.setAlpha(value2);
                }
            }
        });
        arrowAnimator.addListener(new Animator.AnimatorListener() { // from class: com.samsung.android.visualeffect.lock.circleunlock.CircleUnlockEffect.6
            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animation) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationRepeat(Animator animation) {
                arrowAnimationToggle = !arrowAnimationToggle;
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animation) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animation) {
            }
        });
    }

    private void changeModeForShorcutButton(int viewWidth) {
        setAlphaAndVisibility(lockImageView, 0.0f);
        isForShortcut = true;
        if (circle != null) {
            circleUnlockMinWidth = (viewWidth - 4) + minWidthOffset;
            circleUnlockMinRadius = circleUnlockMinWidth / 2;
            circle.setCircleMinWidth(circleUnlockMinWidth);
            circle.setIsForShortcut(isForShortcut);
            circle.setOuterCircleType(isStroke);
            circle.showSwipeCircleEffect(isShowSwipeCircle);
        }
        if (arrowForButton != null) {
            arrow.setVisibility(View.INVISIBLE);
            arrowForButton.setVisibility(View.VISIBLE);
        }
    }

    private void changeModeForCircleUnlock() {
        setAlphaAndVisibility(lockImageView, 1.0f);
        isForShortcut = false;
        if (circle != null) {
            circleUnlockMinWidth = (arrowWidth - innerStrokeWidth) - 4; // todo inspect
            circleUnlockMinRadius = circleUnlockMinWidth / 2;
            circle.setCircleMinWidth(circleUnlockMinWidth);
            circle.setIsForShortcut(isForShortcut);
            circle.setOuterCircleType(isStroke);
            circle.showSwipeCircleEffect(true);
        }
        arrow.setVisibility(View.VISIBLE);
        if (arrowForButton != null) {
            arrowForButton.setVisibility(View.INVISIBLE);
        }
    }

    private void setOuterCircleType(boolean value) {
        isStroke = value;
    }

    private void showSwipeCircleEffect(boolean value) {
        isShowSwipeCircle = value;
    }

    private void setIsWhiteBg(boolean value) {
        circle.setIsWhiteBg(value);
    }

    private void unlock() {
        Log.d(TAG, "unlock");
        isUnlocked = true;
        cancelAllAnimator();
    }

    private void startAnimatorForAffordance(long startDelay, float x, float y) {
        if (!isForAffordance) {
            isForAffordance = true;
            changeModeForCircleUnlock();
            dragAnimationValue = 0.0f;
            strokeAnimationValue = 0.0f;
            fillAnimationValueMax = 0.0f;
            circleAnimationMin = 0.0f;
            circleAnimationMax = 1.0f;
            arrowAlphaMax = 1.0f;
            circle.dragAnimationUpdate(dragAnimationValue);
            setImageInLockImageView(dragAnimationValue);
            arrow.setAlpha(1.0f);
            if (arrowForButton != null) {
                arrowForButton.setAlpha(1.0f);
            }
            setCircleGroupXY(x, y);
            circleInAnimator.setStartDelay(startDelay);
            circleInAnimator.setDuration(IN_ANIMATION_DURATION_FOR_AFFORDANCE);
            circleInAnimator.setInterpolator(new QuintEaseOut());
            circleInAnimator.start();
            long delay = startDelay + IN_ANIMATION_DURATION_FOR_AFFORDANCE + SHOWING_DURATION_FOR_AFFORDANCE;
            circleOutAnimator.setStartDelay(delay);
            circleOutAnimator.setDuration(OUT_ANIMATION_DURATION_FOR_AFFORDANCE);
            circleOutAnimator.setInterpolator(new QuintEaseIn());
            circleOutAnimator.start();
        }
    }

    private void resetAnimatorAfterAffordance() {
        if (isForAffordance) {
            isForAffordance = false;
            circleInAnimator.end();
            circleInAnimator.setStartDelay(0L);
            circleInAnimator.setDuration(IN_ANIMATION_DURATION);
            circleInAnimator.setInterpolator(new QuintEaseOut());
            circleOutAnimator.end();
            circleOutAnimator.setStartDelay(0L);
            circleOutAnimator.setDuration(OUT_ANIMATION_DURATION);
            circleOutAnimator.setInterpolator(new QuintEaseOut());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkPosition() {
        circle.setCircleMinWidth(circleUnlockMinWidth);
        Log.d(TAG, "checkPosition : circleUnlockMinWidth = " + circleUnlockMinWidth);
        if (arrow != null) {
            int tx = (circleUnlockMaxWidth - arrow.getWidth()) / 2;
            int ty = (circleUnlockMaxHeight - arrow.getHeight()) / 2;
            arrow.setX(tx);
            arrow.setY(ty);
        }
        if (lockImageView != null) {
            int tx2 = (circleUnlockMaxWidth - lockImageView.getWidth()) / 2;
            int ty2 = (circleUnlockMaxHeight - lockImageView.getHeight()) / 2;
            lockImageView.setX(tx2);
            lockImageView.setY(ty2);
        }
        if (arrowForButton != null) {
            int tx3 = (circleUnlockMaxWidth - arrowForButton.getWidth()) / 2;
            int ty3 = (circleUnlockMaxHeight - arrowForButton.getHeight()) / 2;
            arrowForButton.setX(tx3);
            arrowForButton.setY(ty3);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkIsWhiteBg() {
        int whiteValue = Settings.Global.getInt(mContext.getContentResolver(), "white_lockscreen_wallpaper", 0);
        Log.d(TAG, "whiteValue : " + whiteValue);
        setIsWhiteBg(whiteValue == 1);
    }

    @Override // com.samsung.android.visualeffect.IEffectView
    public void handleTouchEvent(MotionEvent event, View view) {
        if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
            if (view != null) {
                changeModeForShorcutButton(view.getWidth());
            } else {
                changeModeForCircleUnlock();
            }
        }
        float touchX = event.getX();
        float touchY = event.getY();
        if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
            Log.d(TAG, "handleTouchEvent : ACTION_DOWN");
            isUnlocked = false;
            if (isForShortcut) {
                Rect r = new Rect();
                view.getGlobalVisibleRect(r);
                startX = r.left + (view.getWidth() / 2.0f);
                startY = r.top + (view.getHeight() / 2.0f);
            } else {
                startX = touchX;
                startY = touchY;
            }
            arrowAnimationToggle = false;
            cancelAllAnimator();
            setCircleGroupXY(startX, startY);
            circleInAnimator.start();
            if (!hasNoOuterCircle || isForShortcut) {
                arrowAnimator.start();
            }
        } else if (event.getActionMasked() == MotionEvent.ACTION_MOVE && event.getActionIndex() == MotionEvent.ACTION_DOWN) {
            float diffX = touchX - startX;
            float diffY = touchY - startY;
            float distance = (float) Math.sqrt(Math.pow(diffX, 2.0d) + Math.pow(diffY, 2.0d));
            float ratio = (distance - circleUnlockMinRadius) / (circleUnlockMaxRadius - circleUnlockMinRadius);
            if (ratio < 0.0f) {
                ratio = 0.0f;
            }
            if (ratio > 1.0f) {
                ratio = 1.0f;
            }
            dragAnimationValue = ratio;
            setImageInLockImageView(dragAnimationValue);
            circle.dragAnimationUpdate(dragAnimationValue);
            if (!isForAffordance && hasNoOuterCircle && !isForShortcut) {
                circle.strokeAnimationUpdate(dragAnimationValue);
            }
        } else if (event.getActionMasked() == MotionEvent.ACTION_UP || event.getActionMasked() == MotionEvent.ACTION_CANCEL) {
            cancelAllAnimator();
            if (!isForAffordance && hasNoOuterCircle && !isForShortcut) {
                circleAnimationMax = dragAnimationValue;
            } else {
                circleAnimationMax = strokeAnimationValue;
            }
            fillAnimationValueMax = dragAnimationValue;
            arrowAlphaMax = arrow.getAlpha();
            if (!isUnlocked) {
                circleOutAnimator.start();
            }
        }
    }

    private void setCircleGroupXY(float x, float y) {
        float tx = x - (circleUnlockMaxWidth / 2.0f);
        float ty = y - (circleUnlockMaxHeight / 2.0f);
        circleGroup.setX(tx);
        circleGroup.setY(ty);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setImageInLockImageView(float value) {
        int sequenceNumber = (int) ((lockSequenceTotal - 1) * value);
        if (currentLockSequenceNumber != sequenceNumber) {
            lockImageView.setImageResource(lockSequenceImageId[sequenceNumber]);
            currentLockSequenceNumber = sequenceNumber;
        }
    }

    private void clearEffect() {
        Log.d(TAG, "clearEffect");
        arrowAnimationToggle = false;
        setAlphaAndVisibility(circleGroup, 0.0f);
        cancelAllAnimator();
        if (circle != null) {
            circle.dragAnimationUpdate(0.0f);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void cancelAllAnimator() {
        Log.d(TAG, "cancelAllAnimator");
        resetAnimatorAfterAffordance();
        cancelAnimator(circleInAnimator);
        cancelAnimator(circleOutAnimator);
        cancelAnimator(arrowAnimator);
    }

    private void cancelAnimator(Animator animator) {
        if (animator != null && animator.isRunning()) {
            animator.cancel();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setAlphaAndVisibility(View view, float alpha) {
        if (alpha == 0.0f) {
            if (view.getVisibility() != View.GONE) {
                view.setVisibility(view.getWidth() == 0 ? View.INVISIBLE : View.GONE);
                return;
            }
            return;
        }
        if (view.getVisibility() != View.VISIBLE) {
            view.setVisibility(View.VISIBLE);
        }
        view.setAlpha(alpha);
    }

    private void showAffordanceEffect(long startDelay, Rect rect) {
        Log.d(TAG, "showUnlockAffordance : " + rect.left + ", " + rect.right + ", " + rect.top + ", " + rect.bottom + ", startDelay : " + startDelay);
        float affordanceX = rect.left + ((rect.right - rect.left) / 2.0f);
        float affordanceY = rect.top + ((rect.bottom - rect.top) / 2.0f);
        startAnimatorForAffordance(startDelay, affordanceX, affordanceY);
    }

    @Override // com.samsung.android.visualeffect.IEffectView
    public void reInit(EffectDataObj data) {
        Log.d(TAG, "reInit : minWidthOffset = " + minWidthOffset + ", arrowForButtonId = " + arrowForButtonId);
        if (data.circleData.minWidthOffset != 0) {
            minWidthOffset = data.circleData.minWidthOffset;
        }
        if (data.circleData.arrowForButtonId != 0) {
            arrowForButtonId = data.circleData.arrowForButtonId;
            setArrowForButton(arrowForButtonId);
        }
    }

    private void setArrowForButton(int resId) {
        arrowForButton = new ImageView(mContext);
        arrowForButton.setImageResource(resId);
        circleGroup.addView(arrowForButton, -2, -2);
    }

    @Override // com.samsung.android.visualeffect.IEffectView
    public void handleCustomEvent(int cmd, HashMap<?, ?> params) {
        if (cmd == 1) {
            showAffordanceEffect(((Long) params.get("StartDelay")).longValue(), (Rect) params.get("Rect"));
        } else if (cmd == 2) {
            unlock();
        } else if (cmd == 99) {
            if (params.get("setOuterCircleType") != null) {
                setOuterCircleType(((Boolean) params.get("setOuterCircleType")).booleanValue());
            } else if (params.get("showSwipeCircleEffect") != null) {
                showSwipeCircleEffect(((Boolean) params.get("showSwipeCircleEffect")).booleanValue());
            } else if (params.get("reloadResForOpenTheme") != null) {
                reloadResForOpenTheme();
            }
        }
    }

    private void reloadResForOpenTheme() {
        Log.d(TAG, "reloadResForOpenTheme");
        Drawable d = mContext.getDrawable(arrowImageId);
        arrow.setBackground(d);
    }

    @Override // com.samsung.android.visualeffect.IEffectView
    public void setListener(IEffectListener listener) {
    }

    @Override
    public boolean handleHoverEvent(MotionEvent event) {
        return false;
    }

    @Override // com.samsung.android.visualeffect.IEffectView
    public void removeListener() {
    }

    @Override // com.samsung.android.visualeffect.IEffectView
    public void clearScreen() {
        clearEffect();
    }

    @Override // android.view.View
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        if (!hasWindowFocus) {
            clearEffect();
        }
    }
}