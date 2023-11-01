package com.android.systemui.screenshot;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.Region;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.MathUtils;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.android.settingslib.widget.ActionBarShadowController;
import com.android.systemui.R$id;
import com.android.systemui.screenshot.DraggableConstraintLayout;

/* loaded from: mainsysui33.jar:com/android/systemui/screenshot/DraggableConstraintLayout.class */
public class DraggableConstraintLayout extends ConstraintLayout implements ViewTreeObserver.OnComputeInternalInsetsListener {
    public View mActionsContainer;
    public SwipeDismissCallbacks mCallbacks;
    public final DisplayMetrics mDisplayMetrics;
    public final GestureDetector mSwipeDetector;
    public final SwipeDismissHandler mSwipeDismissHandler;

    /* loaded from: mainsysui33.jar:com/android/systemui/screenshot/DraggableConstraintLayout$SwipeDismissCallbacks.class */
    public interface SwipeDismissCallbacks {
        default void onDismissComplete() {
        }

        default void onInteraction() {
        }

        default void onSwipeDismissInitiated(Animator animator) {
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/screenshot/DraggableConstraintLayout$SwipeDismissHandler.class */
    public class SwipeDismissHandler implements View.OnTouchListener {
        public int mDirectionX;
        public ValueAnimator mDismissAnimation;
        public final DisplayMetrics mDisplayMetrics;
        public final GestureDetector mGestureDetector;
        public float mPreviousX;
        public float mStartX;
        public final DraggableConstraintLayout mView;

        /* loaded from: mainsysui33.jar:com/android/systemui/screenshot/DraggableConstraintLayout$SwipeDismissHandler$SwipeDismissGestureListener.class */
        public class SwipeDismissGestureListener extends GestureDetector.SimpleOnGestureListener {
            public SwipeDismissGestureListener() {
                SwipeDismissHandler.this = r4;
            }

            @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
            public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
                if (SwipeDismissHandler.this.mView.getTranslationX() * f > ActionBarShadowController.ELEVATION_LOW) {
                    if (SwipeDismissHandler.this.mDismissAnimation == null || !SwipeDismissHandler.this.mDismissAnimation.isRunning()) {
                        ValueAnimator createSwipeDismissAnimation = SwipeDismissHandler.this.createSwipeDismissAnimation(f / 1000.0f);
                        DraggableConstraintLayout.this.mCallbacks.onSwipeDismissInitiated(createSwipeDismissAnimation);
                        SwipeDismissHandler.this.dismiss(createSwipeDismissAnimation);
                        return true;
                    }
                    return false;
                }
                return false;
            }

            @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
            public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
                SwipeDismissHandler.this.mView.setTranslationX(motionEvent2.getRawX() - SwipeDismissHandler.this.mStartX);
                SwipeDismissHandler.this.mDirectionX = motionEvent2.getRawX() < SwipeDismissHandler.this.mPreviousX ? -1 : 1;
                SwipeDismissHandler.this.mPreviousX = motionEvent2.getRawX();
                return true;
            }
        }

        /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.screenshot.DraggableConstraintLayout$SwipeDismissHandler$$ExternalSyntheticLambda0.onAnimationUpdate(android.animation.ValueAnimator):void] */
        /* renamed from: $r8$lambda$WcCM8TYN0qCme-EyhSxXDN8G5KM */
        public static /* synthetic */ void m4212$r8$lambda$WcCM8TYN0qCmeEyhSxXDN8G5KM(SwipeDismissHandler swipeDismissHandler, float f, float f2, ValueAnimator valueAnimator) {
            swipeDismissHandler.lambda$createSwipeDismissAnimation$0(f, f2, valueAnimator);
        }

        /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.screenshot.DraggableConstraintLayout$SwipeDismissHandler$$ExternalSyntheticLambda1.onAnimationUpdate(android.animation.ValueAnimator):void] */
        /* renamed from: $r8$lambda$wrwuyvzExY6N-Y2WHzKwvwuCMko */
        public static /* synthetic */ void m4213$r8$lambda$wrwuyvzExY6NY2WHzKwvwuCMko(SwipeDismissHandler swipeDismissHandler, float f, float f2, ValueAnimator valueAnimator) {
            swipeDismissHandler.lambda$createSwipeReturnAnimation$1(f, f2, valueAnimator);
        }

        public SwipeDismissHandler(Context context, DraggableConstraintLayout draggableConstraintLayout) {
            DraggableConstraintLayout.this = r9;
            this.mView = draggableConstraintLayout;
            this.mGestureDetector = new GestureDetector(context, new SwipeDismissGestureListener());
            DisplayMetrics displayMetrics = new DisplayMetrics();
            this.mDisplayMetrics = displayMetrics;
            context.getDisplay().getRealMetrics(displayMetrics);
        }

        public /* synthetic */ void lambda$createSwipeDismissAnimation$0(float f, float f2, ValueAnimator valueAnimator) {
            this.mView.setTranslationX(MathUtils.lerp(f, f2, valueAnimator.getAnimatedFraction()));
            this.mView.setAlpha(1.0f - valueAnimator.getAnimatedFraction());
        }

        public /* synthetic */ void lambda$createSwipeReturnAnimation$1(float f, float f2, ValueAnimator valueAnimator) {
            this.mView.setTranslationX(MathUtils.lerp(f, f2, valueAnimator.getAnimatedFraction()));
        }

        public void cancel() {
            if (isDismissing()) {
                this.mDismissAnimation.cancel();
            }
        }

        public final ValueAnimator createSwipeDismissAnimation() {
            return createSwipeDismissAnimation(FloatingWindowUtil.dpToPx(this.mDisplayMetrics, 1.0f));
        }

        public final ValueAnimator createSwipeDismissAnimation(float f) {
            float min = Math.min(3.0f, Math.max(1.0f, f));
            ValueAnimator ofFloat = ValueAnimator.ofFloat(ActionBarShadowController.ELEVATION_LOW, 1.0f);
            final float translationX = this.mView.getTranslationX();
            int layoutDirection = this.mView.getContext().getResources().getConfiguration().getLayoutDirection();
            int i = (translationX > ActionBarShadowController.ELEVATION_LOW ? 1 : (translationX == ActionBarShadowController.ELEVATION_LOW ? 0 : -1));
            final float backgroundRight = (i > 0 || (i == 0 && layoutDirection == 1)) ? this.mDisplayMetrics.widthPixels : DraggableConstraintLayout.this.getBackgroundRight() * (-1);
            float abs = Math.abs(backgroundRight - translationX);
            ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.screenshot.DraggableConstraintLayout$SwipeDismissHandler$$ExternalSyntheticLambda0
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    DraggableConstraintLayout.SwipeDismissHandler.m4212$r8$lambda$WcCM8TYN0qCmeEyhSxXDN8G5KM(DraggableConstraintLayout.SwipeDismissHandler.this, translationX, backgroundRight, valueAnimator);
                }
            });
            ofFloat.setDuration(abs / Math.abs(min));
            return ofFloat;
        }

        public final ValueAnimator createSwipeReturnAnimation() {
            ValueAnimator ofFloat = ValueAnimator.ofFloat(ActionBarShadowController.ELEVATION_LOW, 1.0f);
            final float translationX = this.mView.getTranslationX();
            ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.screenshot.DraggableConstraintLayout$SwipeDismissHandler$$ExternalSyntheticLambda1
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    DraggableConstraintLayout.SwipeDismissHandler.m4213$r8$lambda$wrwuyvzExY6NY2WHzKwvwuCMko(DraggableConstraintLayout.SwipeDismissHandler.this, translationX, r6, valueAnimator);
                }
            });
            return ofFloat;
        }

        public void dismiss() {
            dismiss(createSwipeDismissAnimation());
        }

        public final void dismiss(ValueAnimator valueAnimator) {
            this.mDismissAnimation = valueAnimator;
            valueAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.screenshot.DraggableConstraintLayout.SwipeDismissHandler.1
                public boolean mCancelled;

                {
                    SwipeDismissHandler.this = this;
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationCancel(Animator animator) {
                    super.onAnimationCancel(animator);
                    this.mCancelled = true;
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    super.onAnimationEnd(animator);
                    if (this.mCancelled) {
                        return;
                    }
                    DraggableConstraintLayout.this.mCallbacks.onDismissComplete();
                }
            });
            this.mDismissAnimation.start();
        }

        public boolean isDismissing() {
            ValueAnimator valueAnimator = this.mDismissAnimation;
            return valueAnimator != null && valueAnimator.isRunning();
        }

        public final boolean isPastDismissThreshold() {
            float translationX = this.mView.getTranslationX();
            boolean z = false;
            if (this.mDirectionX * translationX > ActionBarShadowController.ELEVATION_LOW) {
                z = false;
                if (Math.abs(translationX) >= FloatingWindowUtil.dpToPx(this.mDisplayMetrics, 20.0f)) {
                    z = true;
                }
            }
            return z;
        }

        @Override // android.view.View.OnTouchListener
        public boolean onTouch(View view, MotionEvent motionEvent) {
            boolean onTouchEvent = this.mGestureDetector.onTouchEvent(motionEvent);
            DraggableConstraintLayout.this.mCallbacks.onInteraction();
            if (motionEvent.getActionMasked() == 0) {
                float rawX = motionEvent.getRawX();
                this.mStartX = rawX;
                this.mPreviousX = rawX;
                return true;
            } else if (motionEvent.getActionMasked() == 1) {
                ValueAnimator valueAnimator = this.mDismissAnimation;
                if (valueAnimator == null || !valueAnimator.isRunning()) {
                    if (!isPastDismissThreshold()) {
                        createSwipeReturnAnimation().start();
                        return true;
                    }
                    ValueAnimator createSwipeDismissAnimation = createSwipeDismissAnimation();
                    DraggableConstraintLayout.this.mCallbacks.onSwipeDismissInitiated(createSwipeDismissAnimation);
                    dismiss(createSwipeDismissAnimation);
                    return true;
                }
                return true;
            } else {
                return onTouchEvent;
            }
        }
    }

    public DraggableConstraintLayout(Context context) {
        this(context, null);
    }

    public DraggableConstraintLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public DraggableConstraintLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        this.mDisplayMetrics = displayMetrics;
        ((ViewGroup) this).mContext.getDisplay().getRealMetrics(displayMetrics);
        SwipeDismissHandler swipeDismissHandler = new SwipeDismissHandler(((ViewGroup) this).mContext, this);
        this.mSwipeDismissHandler = swipeDismissHandler;
        setOnTouchListener(swipeDismissHandler);
        GestureDetector gestureDetector = new GestureDetector(((ViewGroup) this).mContext, new GestureDetector.SimpleOnGestureListener() { // from class: com.android.systemui.screenshot.DraggableConstraintLayout.1
            public final Rect mActionsRect = new Rect();

            {
                DraggableConstraintLayout.this = this;
            }

            @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
            public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
                DraggableConstraintLayout.this.mActionsContainer.getBoundsOnScreen(this.mActionsRect);
                return (this.mActionsRect.contains((int) motionEvent2.getRawX(), (int) motionEvent2.getRawY()) && DraggableConstraintLayout.this.mActionsContainer.canScrollHorizontally((int) f)) ? false : true;
            }
        });
        this.mSwipeDetector = gestureDetector;
        gestureDetector.setIsLongpressEnabled(false);
        this.mCallbacks = new SwipeDismissCallbacks() { // from class: com.android.systemui.screenshot.DraggableConstraintLayout.2
            {
                DraggableConstraintLayout.this = this;
            }
        };
    }

    public void cancelDismissal() {
        this.mSwipeDismissHandler.cancel();
    }

    public void dismiss() {
        this.mSwipeDismissHandler.dismiss();
    }

    public final int getBackgroundRight() {
        View findViewById = findViewById(R$id.actions_container_background);
        return findViewById == null ? 0 : findViewById.getRight();
    }

    public boolean isDismissing() {
        return this.mSwipeDismissHandler.isDismissing();
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        getViewTreeObserver().addOnComputeInternalInsetsListener(this);
    }

    public void onComputeInternalInsets(ViewTreeObserver.InternalInsetsInfo internalInsetsInfo) {
        Region region = new Region();
        Rect rect = new Rect();
        for (int i = 0; i < getChildCount(); i++) {
            getChildAt(i).getGlobalVisibleRect(rect);
            region.op(rect, Region.Op.UNION);
        }
        internalInsetsInfo.setTouchableInsets(3);
        internalInsetsInfo.touchableRegion.set(region);
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        getViewTreeObserver().removeOnComputeInternalInsetsListener(this);
    }

    @Override // android.view.View
    public void onFinishInflate() {
        this.mActionsContainer = findViewById(R$id.actions_container);
    }

    @Override // android.view.ViewGroup
    public boolean onInterceptHoverEvent(MotionEvent motionEvent) {
        this.mCallbacks.onInteraction();
        return super.onInterceptHoverEvent(motionEvent);
    }

    @Override // android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getActionMasked() == 0) {
            this.mSwipeDismissHandler.onTouch(this, motionEvent);
        }
        return this.mSwipeDetector.onTouchEvent(motionEvent);
    }

    public void setCallbacks(SwipeDismissCallbacks swipeDismissCallbacks) {
        this.mCallbacks = swipeDismissCallbacks;
    }
}