package com.android.systemui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.res.Resources;
import android.graphics.RectF;
import android.os.Handler;
import android.util.ArrayMap;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import com.android.settingslib.widget.ActionBarShadowController;
import com.android.systemui.animation.Interpolators;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.Flags;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.statusbar.NotificationMenuRowPlugin;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.wm.shell.animation.FlingAnimationUtils;
import java.util.function.Consumer;

/* loaded from: mainsysui33.jar:com/android/systemui/SwipeHelper.class */
public class SwipeHelper implements Gefingerpoken {
    public final Callback mCallback;
    public boolean mCanCurrViewBeDimissed;
    public float mDensityScale;
    public boolean mDisableHwLayers;
    public final boolean mFadeDependingOnAmountSwiped;
    public final FalsingManager mFalsingManager;
    public final int mFalsingThreshold;
    public final FeatureFlags mFeatureFlags;
    public final FlingAnimationUtils mFlingAnimationUtils;
    public float mInitialTouchPos;
    public boolean mIsSwiping;
    public boolean mLongPressSent;
    public boolean mMenuRowIntercepting;
    public float mPagingTouchSlop;
    public float mPerpendicularInitialTouchPos;
    public final float mSlopMultiplier;
    public boolean mSnappingChild;
    public final int mSwipeDirection;
    public boolean mTouchAboveFalsingThreshold;
    public int mTouchSlop;
    public View mTouchedView;
    public float mMinSwipeProgress = ActionBarShadowController.ELEVATION_LOW;
    public float mMaxSwipeProgress = 1.0f;
    public float mTranslation = ActionBarShadowController.ELEVATION_LOW;
    public final float[] mDownLocation = new float[2];
    public final Runnable mPerformLongPress = new Runnable() { // from class: com.android.systemui.SwipeHelper.1
        public final int[] mViewOffset = new int[2];

        {
            SwipeHelper.this = this;
        }

        @Override // java.lang.Runnable
        public void run() {
            if (SwipeHelper.this.mTouchedView == null || SwipeHelper.this.mLongPressSent) {
                return;
            }
            SwipeHelper.this.mLongPressSent = true;
            if (SwipeHelper.this.mTouchedView instanceof ExpandableNotificationRow) {
                SwipeHelper.this.mTouchedView.getLocationOnScreen(this.mViewOffset);
                int i = (int) SwipeHelper.this.mDownLocation[0];
                int i2 = this.mViewOffset[0];
                int i3 = (int) SwipeHelper.this.mDownLocation[1];
                int i4 = this.mViewOffset[1];
                SwipeHelper.this.mTouchedView.sendAccessibilityEvent(2);
                SwipeHelper.this.mTouchedView.doLongClickCallback(i - i2, i3 - i4);
                SwipeHelper swipeHelper = SwipeHelper.this;
                if (swipeHelper.isAvailableToDragAndDrop(swipeHelper.mTouchedView)) {
                    SwipeHelper.this.mCallback.onLongPressSent(SwipeHelper.this.mTouchedView);
                }
            }
        }
    };
    public final ArrayMap<View, Animator> mDismissPendingMap = new ArrayMap<>();
    public final Handler mHandler = new Handler();
    public final VelocityTracker mVelocityTracker = VelocityTracker.obtain();
    public float mTouchSlopMultiplier = ViewConfiguration.getAmbiguousGestureMultiplier();
    public final long mLongPressTimeout = ViewConfiguration.getLongPressTimeout() * 1.5f;

    /* loaded from: mainsysui33.jar:com/android/systemui/SwipeHelper$Callback.class */
    public interface Callback {
        boolean canChildBeDismissed(View view);

        default boolean canChildBeDismissedInDirection(View view, boolean z) {
            return canChildBeDismissed(view);
        }

        default boolean canChildBeDragged(View view) {
            return true;
        }

        View getChildAtPosition(MotionEvent motionEvent);

        default int getConstrainSwipeStartPosition() {
            return 0;
        }

        float getFalsingThresholdFactor();

        boolean isAntiFalsingNeeded();

        void onBeginDrag(View view);

        void onChildDismissed(View view);

        void onChildSnappedBack(View view, float f);

        void onDragCancelled(View view);

        void onLongPressSent(View view);

        boolean updateSwipeProgress(View view, boolean z, float f);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.SwipeHelper$$ExternalSyntheticLambda0.onAnimationUpdate(android.animation.ValueAnimator):void] */
    public static /* synthetic */ void $r8$lambda$oZNJKeeaVNOiKb5ASGdHpRK1AoI(SwipeHelper swipeHelper, View view, boolean z, ValueAnimator valueAnimator) {
        swipeHelper.lambda$snapChild$0(view, z, valueAnimator);
    }

    public SwipeHelper(int i, Callback callback, Resources resources, ViewConfiguration viewConfiguration, FalsingManager falsingManager, FeatureFlags featureFlags) {
        this.mCallback = callback;
        this.mSwipeDirection = i;
        this.mPagingTouchSlop = viewConfiguration.getScaledPagingTouchSlop();
        this.mSlopMultiplier = viewConfiguration.getScaledAmbiguousGestureMultiplier();
        this.mTouchSlop = viewConfiguration.getScaledTouchSlop();
        this.mDensityScale = resources.getDisplayMetrics().density;
        this.mFalsingThreshold = resources.getDimensionPixelSize(R$dimen.swipe_helper_falsing_threshold);
        this.mFadeDependingOnAmountSwiped = resources.getBoolean(R$bool.config_fadeDependingOnAmountSwiped);
        this.mFalsingManager = falsingManager;
        this.mFeatureFlags = featureFlags;
        this.mFlingAnimationUtils = new FlingAnimationUtils(resources.getDisplayMetrics(), ((float) getMaxEscapeAnimDuration()) / 1000.0f);
    }

    public static void invalidateGlobalRegion(View view) {
        invalidateGlobalRegion(view, new RectF(view.getLeft(), view.getTop(), view.getRight(), view.getBottom()));
    }

    public static void invalidateGlobalRegion(View view, RectF rectF) {
        while (view.getParent() != null && (view.getParent() instanceof View)) {
            view = (View) view.getParent();
            view.getMatrix().mapRect(rectF);
            view.invalidate((int) Math.floor(rectF.left), (int) Math.floor(rectF.top), (int) Math.ceil(rectF.right), (int) Math.ceil(rectF.bottom));
        }
    }

    public /* synthetic */ void lambda$snapChild$0(View view, boolean z, ValueAnimator valueAnimator) {
        onTranslationUpdate(view, ((Float) valueAnimator.getAnimatedValue()).floatValue(), z);
    }

    public void cancelLongPress() {
        this.mHandler.removeCallbacks(this.mPerformLongPress);
    }

    public ObjectAnimator createTranslationAnimation(View view, float f) {
        return ObjectAnimator.ofFloat(view, this.mSwipeDirection == 0 ? View.TRANSLATION_X : View.TRANSLATION_Y, f);
    }

    /* JADX WARN: Code restructure failed: missing block: B:99:0x0096, code lost:
        if (r18 == false) goto L26;
     */
    /* JADX WARN: Removed duplicated region for block: B:112:0x00c7  */
    /* JADX WARN: Removed duplicated region for block: B:118:0x00fb  */
    /* JADX WARN: Removed duplicated region for block: B:121:0x011a  */
    /* JADX WARN: Removed duplicated region for block: B:123:0x011f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void dismissChild(final View view, float f, final Consumer<Boolean> consumer, long j, boolean z, long j2, boolean z2) {
        boolean z3;
        Animator viewTranslationAnimator;
        final boolean canChildBeDismissed = this.mCallback.canChildBeDismissed(view);
        boolean z4 = view.getLayoutDirection() == 1;
        int i = (f > ActionBarShadowController.ELEVATION_LOW ? 1 : (f == ActionBarShadowController.ELEVATION_LOW ? 0 : -1));
        boolean z5 = i == 0 && (getTranslation(view) == ActionBarShadowController.ELEVATION_LOW || z2) && this.mSwipeDirection == 1;
        boolean z6 = i == 0 && (getTranslation(view) == ActionBarShadowController.ELEVATION_LOW || z2) && z4;
        if (Math.abs(f) <= getEscapeVelocity() || f >= ActionBarShadowController.ELEVATION_LOW) {
            z3 = false;
            if (getTranslation(view) < ActionBarShadowController.ELEVATION_LOW) {
                z3 = false;
            }
            float totalTranslationLength = (!z3 || z6 || z5) ? -getTotalTranslationLength(view) : getTotalTranslationLength(view);
            if (j2 == 0) {
                j2 = i != 0 ? Math.min(400L, (int) ((Math.abs(totalTranslationLength - getTranslation(view)) * 1000.0f) / Math.abs(f))) : 200L;
            }
            if (!this.mDisableHwLayers) {
                view.setLayerType(2, null);
            }
            viewTranslationAnimator = getViewTranslationAnimator(view, totalTranslationLength, new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.SwipeHelper.2
                {
                    SwipeHelper.this = this;
                }

                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    SwipeHelper.this.onTranslationUpdate(view, ((Float) valueAnimator.getAnimatedValue()).floatValue(), canChildBeDismissed);
                }
            });
            if (viewTranslationAnimator != null) {
                onDismissChildWithAnimationFinished();
                return;
            }
            if (z) {
                viewTranslationAnimator.setInterpolator(Interpolators.FAST_OUT_LINEAR_IN);
                viewTranslationAnimator.setDuration(j2);
            } else {
                this.mFlingAnimationUtils.applyDismissing(viewTranslationAnimator, getTranslation(view), totalTranslationLength, f, getSize(view));
            }
            if (j > 0) {
                viewTranslationAnimator.setStartDelay(j);
            }
            viewTranslationAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.SwipeHelper.3
                public boolean mCancelled;

                {
                    SwipeHelper.this = this;
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationCancel(Animator animator) {
                    this.mCancelled = true;
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    SwipeHelper.this.updateSwipeProgressFromOffset(view, canChildBeDismissed);
                    SwipeHelper.this.mDismissPendingMap.remove(view);
                    ExpandableNotificationRow expandableNotificationRow = view;
                    boolean isRemoved = expandableNotificationRow instanceof ExpandableNotificationRow ? expandableNotificationRow.isRemoved() : false;
                    if (!this.mCancelled || isRemoved) {
                        SwipeHelper.this.mCallback.onChildDismissed(view);
                        SwipeHelper.this.resetSwipeState();
                    }
                    Consumer consumer2 = consumer;
                    if (consumer2 != null) {
                        consumer2.accept(Boolean.valueOf(this.mCancelled));
                    }
                    if (!SwipeHelper.this.mDisableHwLayers) {
                        view.setLayerType(0, null);
                    }
                    SwipeHelper.this.onDismissChildWithAnimationFinished();
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationStart(Animator animator) {
                    super.onAnimationStart(animator);
                    SwipeHelper.this.mCallback.onBeginDrag(view);
                }
            });
            prepareDismissAnimation(view, viewTranslationAnimator);
            this.mDismissPendingMap.put(view, viewTranslationAnimator);
            viewTranslationAnimator.start();
            return;
        }
        z3 = true;
        if (z3) {
        }
        if (j2 == 0) {
        }
        if (!this.mDisableHwLayers) {
        }
        viewTranslationAnimator = getViewTranslationAnimator(view, totalTranslationLength, new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.SwipeHelper.2
            {
                SwipeHelper.this = this;
            }

            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                SwipeHelper.this.onTranslationUpdate(view, ((Float) valueAnimator.getAnimatedValue()).floatValue(), canChildBeDismissed);
            }
        });
        if (viewTranslationAnimator != null) {
        }
    }

    public void dismissChild(View view, float f, boolean z) {
        dismissChild(view, f, null, 0L, z, 0L, false);
    }

    public float getEscapeVelocity() {
        return getUnscaledEscapeVelocity() * this.mDensityScale;
    }

    public final int getFalsingThreshold() {
        return (int) (this.mFalsingThreshold * this.mCallback.getFalsingThresholdFactor());
    }

    public long getMaxEscapeAnimDuration() {
        return 400L;
    }

    public final float getMaxVelocity() {
        return this.mDensityScale * 4000.0f;
    }

    public final float getPerpendicularPos(MotionEvent motionEvent) {
        return this.mSwipeDirection == 0 ? motionEvent.getY() : motionEvent.getX();
    }

    public final float getPos(MotionEvent motionEvent) {
        return this.mSwipeDirection == 0 ? motionEvent.getX() : motionEvent.getY();
    }

    public float getSize(View view) {
        return this.mSwipeDirection == 0 ? view.getMeasuredWidth() : view.getMeasuredHeight();
    }

    public float getSwipeAlpha(float f) {
        return this.mFadeDependingOnAmountSwiped ? Math.max(1.0f - f, (float) ActionBarShadowController.ELEVATION_LOW) : 1.0f - Math.max((float) ActionBarShadowController.ELEVATION_LOW, Math.min(1.0f, f / 0.6f));
    }

    public final float getSwipeProgressForOffset(View view, float f) {
        return Math.min(Math.max(this.mMinSwipeProgress, Math.abs(f / getSize(view))), this.mMaxSwipeProgress);
    }

    public View getSwipedView() {
        return this.mIsSwiping ? this.mTouchedView : null;
    }

    public float getTotalTranslationLength(View view) {
        return getSize(view);
    }

    public final float getTouchSlop(MotionEvent motionEvent) {
        return motionEvent.getClassification() == 1 ? this.mTouchSlop * this.mTouchSlopMultiplier : this.mTouchSlop;
    }

    public float getTranslation(View view) {
        return this.mSwipeDirection == 0 ? view.getTranslationX() : view.getTranslationY();
    }

    public float getUnscaledEscapeVelocity() {
        return 500.0f;
    }

    public final float getVelocity(VelocityTracker velocityTracker) {
        return this.mSwipeDirection == 0 ? velocityTracker.getXVelocity() : velocityTracker.getYVelocity();
    }

    public Animator getViewTranslationAnimator(View view, float f, ValueAnimator.AnimatorUpdateListener animatorUpdateListener) {
        ObjectAnimator createTranslationAnimation = createTranslationAnimation(view, f);
        if (animatorUpdateListener != null) {
            createTranslationAnimation.addUpdateListener(animatorUpdateListener);
        }
        return createTranslationAnimation;
    }

    public boolean handleUpEvent(MotionEvent motionEvent, View view, float f, float f2) {
        return false;
    }

    public final boolean isAvailableToDragAndDrop(View view) {
        if (this.mFeatureFlags.isEnabled(Flags.NOTIFICATION_DRAG_TO_CONTENTS) && (view instanceof ExpandableNotificationRow)) {
            ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) view;
            boolean canBubble = expandableNotificationRow.getEntry().canBubble();
            Notification notification = expandableNotificationRow.getEntry().getSbn().getNotification();
            PendingIntent pendingIntent = notification.contentIntent;
            if (pendingIntent == null) {
                pendingIntent = notification.fullScreenIntent;
            }
            return (pendingIntent == null || !pendingIntent.isActivity() || canBubble) ? false : true;
        }
        return false;
    }

    /* JADX WARN: Code restructure failed: missing block: B:32:0x0044, code lost:
        if (swipedFarEnough() != false) goto L11;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean isDismissGesture(MotionEvent motionEvent) {
        float translation = getTranslation(this.mTouchedView);
        boolean z = false;
        if (motionEvent.getActionMasked() == 1) {
            z = false;
            if (!this.mFalsingManager.isUnlockingDisabled()) {
                z = false;
                if (!isFalseGesture()) {
                    if (!swipedFastEnough()) {
                        z = false;
                    }
                    z = false;
                    if (this.mCallback.canChildBeDismissedInDirection(this.mTouchedView, translation > ActionBarShadowController.ELEVATION_LOW)) {
                        z = true;
                    }
                }
            }
        }
        return z;
    }

    /* JADX WARN: Code restructure failed: missing block: B:23:0x002c, code lost:
        if (r3.mFalsingManager.isFalseTouch(1) != false) goto L7;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x002f, code lost:
        r5 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x0040, code lost:
        if (r3.mTouchAboveFalsingThreshold == false) goto L7;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean isFalseGesture() {
        boolean z;
        boolean isAntiFalsingNeeded = this.mCallback.isAntiFalsingNeeded();
        if (this.mFalsingManager.isClassifierEnabled()) {
            z = false;
            if (isAntiFalsingNeeded) {
                z = false;
            }
        } else {
            z = false;
            if (isAntiFalsingNeeded) {
                z = false;
            }
        }
        return z;
    }

    public boolean isSwiping() {
        return this.mIsSwiping;
    }

    public void onDismissChildWithAnimationFinished() {
    }

    public void onDownUpdate(View view, MotionEvent motionEvent) {
    }

    /* JADX WARN: Code restructure failed: missing block: B:77:0x003f, code lost:
        if (r0 != 3) goto L14;
     */
    @Override // com.android.systemui.Gefingerpoken
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        NotificationMenuRowPlugin provider;
        ExpandableNotificationRow expandableNotificationRow = this.mTouchedView;
        if ((expandableNotificationRow instanceof ExpandableNotificationRow) && (provider = expandableNotificationRow.getProvider()) != null) {
            this.mMenuRowIntercepting = provider.onInterceptTouchEvent(this.mTouchedView, motionEvent);
        }
        int action = motionEvent.getAction();
        if (action != 0) {
            if (action != 1) {
                if (action == 2) {
                    if (this.mTouchedView != null && !this.mLongPressSent) {
                        this.mVelocityTracker.addMovement(motionEvent);
                        float pos = getPos(motionEvent);
                        float perpendicularPos = getPerpendicularPos(motionEvent);
                        float f = pos - this.mInitialTouchPos;
                        float f2 = this.mPerpendicularInitialTouchPos;
                        if (Math.abs(f) > (motionEvent.getClassification() == 1 ? this.mPagingTouchSlop * this.mSlopMultiplier : this.mPagingTouchSlop) && Math.abs(f) > Math.abs(perpendicularPos - f2)) {
                            if (this.mCallback.canChildBeDragged(this.mTouchedView)) {
                                this.mIsSwiping = true;
                                this.mCallback.onBeginDrag(this.mTouchedView);
                                this.mInitialTouchPos = getPos(motionEvent);
                                this.mTranslation = getTranslation(this.mTouchedView);
                            }
                            cancelLongPress();
                        } else if (motionEvent.getClassification() == 2 && this.mHandler.hasCallbacks(this.mPerformLongPress)) {
                            cancelLongPress();
                            this.mPerformLongPress.run();
                        }
                    }
                }
            }
            boolean z = this.mIsSwiping || this.mLongPressSent || this.mMenuRowIntercepting;
            this.mIsSwiping = false;
            this.mTouchedView = null;
            this.mLongPressSent = false;
            this.mCallback.onLongPressSent(null);
            this.mMenuRowIntercepting = false;
            cancelLongPress();
            if (z) {
                return true;
            }
        } else {
            this.mTouchAboveFalsingThreshold = false;
            this.mIsSwiping = false;
            this.mSnappingChild = false;
            this.mLongPressSent = false;
            this.mCallback.onLongPressSent(null);
            this.mVelocityTracker.clear();
            cancelLongPress();
            View childAtPosition = this.mCallback.getChildAtPosition(motionEvent);
            this.mTouchedView = childAtPosition;
            if (childAtPosition != null) {
                onDownUpdate(childAtPosition, motionEvent);
                this.mCanCurrViewBeDimissed = this.mCallback.canChildBeDismissed(this.mTouchedView);
                this.mVelocityTracker.addMovement(motionEvent);
                this.mInitialTouchPos = getPos(motionEvent);
                this.mPerpendicularInitialTouchPos = getPerpendicularPos(motionEvent);
                this.mTranslation = getTranslation(this.mTouchedView);
                this.mDownLocation[0] = motionEvent.getRawX();
                this.mDownLocation[1] = motionEvent.getRawY();
                this.mHandler.postDelayed(this.mPerformLongPress, this.mLongPressTimeout);
            }
        }
        boolean z2 = true;
        if (!this.mIsSwiping) {
            z2 = true;
            if (!this.mLongPressSent) {
                z2 = this.mMenuRowIntercepting;
            }
        }
        return z2;
    }

    public void onMoveUpdate(View view, MotionEvent motionEvent, float f, float f2) {
    }

    public void onSnapChildWithAnimationFinished() {
    }

    @Override // com.android.systemui.Gefingerpoken
    public boolean onTouchEvent(MotionEvent motionEvent) {
        float f;
        boolean z = false;
        if (!this.mIsSwiping && !this.mMenuRowIntercepting && !this.mLongPressSent) {
            if (this.mCallback.getChildAtPosition(motionEvent) == null) {
                cancelLongPress();
                return false;
            }
            this.mTouchedView = this.mCallback.getChildAtPosition(motionEvent);
            onInterceptTouchEvent(motionEvent);
            return true;
        }
        this.mVelocityTracker.addMovement(motionEvent);
        int action = motionEvent.getAction();
        if (action != 1) {
            if (action != 2) {
                if (action != 3) {
                    if (action != 4) {
                        return true;
                    }
                }
            }
            if (this.mTouchedView != null) {
                float pos = getPos(motionEvent) - this.mInitialTouchPos;
                float abs = Math.abs(pos);
                if (abs >= getFalsingThreshold()) {
                    this.mTouchAboveFalsingThreshold = true;
                }
                if (this.mLongPressSent) {
                    if (abs >= getTouchSlop(motionEvent)) {
                        ExpandableNotificationRow expandableNotificationRow = this.mTouchedView;
                        if (expandableNotificationRow instanceof ExpandableNotificationRow) {
                            expandableNotificationRow.doDragCallback(motionEvent.getX(), motionEvent.getY());
                            return true;
                        }
                        return true;
                    }
                    return true;
                }
                Callback callback = this.mCallback;
                View view = this.mTouchedView;
                int i = (pos > ActionBarShadowController.ELEVATION_LOW ? 1 : (pos == ActionBarShadowController.ELEVATION_LOW ? 0 : -1));
                if (i > 0) {
                    z = true;
                }
                float f2 = pos;
                if (!callback.canChildBeDismissedInDirection(view, z)) {
                    float size = getSize(this.mTouchedView);
                    float f3 = 0.3f * size;
                    if (abs >= size) {
                        f2 = i > 0 ? f3 : -f3;
                    } else {
                        float constrainSwipeStartPosition = this.mCallback.getConstrainSwipeStartPosition();
                        f2 = pos;
                        if (abs > constrainSwipeStartPosition) {
                            f2 = ((int) (constrainSwipeStartPosition * Math.signum(pos))) + (f3 * ((float) Math.sin(((pos - f) / size) * 1.5707963267948966d)));
                        }
                    }
                }
                setTranslation(this.mTouchedView, this.mTranslation + f2);
                updateSwipeProgressFromOffset(this.mTouchedView, this.mCanCurrViewBeDimissed);
                onMoveUpdate(this.mTouchedView, motionEvent, this.mTranslation + f2, f2);
                return true;
            }
            return true;
        }
        if (this.mTouchedView == null) {
            return true;
        }
        this.mVelocityTracker.computeCurrentVelocity(1000, getMaxVelocity());
        float velocity = getVelocity(this.mVelocityTracker);
        View view2 = this.mTouchedView;
        if (!handleUpEvent(motionEvent, view2, velocity, getTranslation(view2))) {
            if (isDismissGesture(motionEvent)) {
                dismissChild(this.mTouchedView, velocity, !swipedFastEnough());
            } else {
                this.mCallback.onDragCancelled(this.mTouchedView);
                snapChild(this.mTouchedView, ActionBarShadowController.ELEVATION_LOW, velocity);
            }
            this.mTouchedView = null;
        }
        this.mIsSwiping = false;
        return true;
    }

    public void onTranslationUpdate(View view, float f, boolean z) {
        updateSwipeProgressFromOffset(view, z, f);
    }

    public void prepareDismissAnimation(View view, Animator animator) {
    }

    public void prepareSnapBackAnimation(View view, Animator animator) {
    }

    public void resetSwipeState() {
        this.mTouchedView = null;
        this.mIsSwiping = false;
    }

    public void setDensityScale(float f) {
        this.mDensityScale = f;
    }

    public void setPagingTouchSlop(float f) {
        this.mPagingTouchSlop = f;
    }

    public void setTranslation(View view, float f) {
        if (view == null) {
            return;
        }
        if (this.mSwipeDirection == 0) {
            view.setTranslationX(f);
        } else {
            view.setTranslationY(f);
        }
    }

    public void snapChild(final View view, float f, float f2) {
        final boolean canChildBeDismissed = this.mCallback.canChildBeDismissed(view);
        Animator viewTranslationAnimator = getViewTranslationAnimator(view, f, new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.SwipeHelper$$ExternalSyntheticLambda0
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                SwipeHelper.$r8$lambda$oZNJKeeaVNOiKb5ASGdHpRK1AoI(SwipeHelper.this, view, canChildBeDismissed, valueAnimator);
            }
        });
        if (viewTranslationAnimator == null) {
            onSnapChildWithAnimationFinished();
            return;
        }
        viewTranslationAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.SwipeHelper.4
            public boolean wasCancelled = false;

            {
                SwipeHelper.this = this;
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animator) {
                this.wasCancelled = true;
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                SwipeHelper.this.mSnappingChild = false;
                if (!this.wasCancelled) {
                    SwipeHelper.this.updateSwipeProgressFromOffset(view, canChildBeDismissed);
                    SwipeHelper.this.resetSwipeState();
                }
                SwipeHelper.this.onSnapChildWithAnimationFinished();
            }
        });
        prepareSnapBackAnimation(view, viewTranslationAnimator);
        this.mSnappingChild = true;
        this.mFlingAnimationUtils.apply(viewTranslationAnimator, getTranslation(view), f, f2, Math.abs(f - getTranslation(view)));
        viewTranslationAnimator.start();
        this.mCallback.onChildSnappedBack(view, f);
    }

    /* JADX WARN: Code restructure failed: missing block: B:40:0x003e, code lost:
        if (getTranslation(r6) != com.android.settingslib.widget.ActionBarShadowController.ELEVATION_LOW) goto L12;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void snapChildIfNeeded(View view, boolean z, float f) {
        if ((this.mIsSwiping && this.mTouchedView == view) || this.mSnappingChild) {
            return;
        }
        boolean z2 = false;
        Animator animator = this.mDismissPendingMap.get(view);
        if (animator != null) {
            animator.cancel();
        }
        z2 = true;
        if (z2) {
            if (z) {
                snapChild(view, f, ActionBarShadowController.ELEVATION_LOW);
            } else {
                snapChildInstantly(view);
            }
        }
    }

    public final void snapChildInstantly(View view) {
        boolean canChildBeDismissed = this.mCallback.canChildBeDismissed(view);
        setTranslation(view, ActionBarShadowController.ELEVATION_LOW);
        updateSwipeProgressFromOffset(view, canChildBeDismissed);
    }

    public boolean swipedFarEnough() {
        return Math.abs(getTranslation(this.mTouchedView)) > getSize(this.mTouchedView) * 0.6f;
    }

    /* JADX WARN: Code restructure failed: missing block: B:31:0x0049, code lost:
        if ((r0 > com.android.settingslib.widget.ActionBarShadowController.ELEVATION_LOW) == (r0 > com.android.settingslib.widget.ActionBarShadowController.ELEVATION_LOW)) goto L11;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean swipedFastEnough() {
        float velocity = getVelocity(this.mVelocityTracker);
        float translation = getTranslation(this.mTouchedView);
        boolean z = true;
        if (Math.abs(velocity) > getEscapeVelocity()) {
        }
        z = false;
        return z;
    }

    public void updateSwipeProgressAlpha(View view, float f) {
        view.setAlpha(f);
    }

    public final void updateSwipeProgressFromOffset(View view, boolean z) {
        updateSwipeProgressFromOffset(view, z, getTranslation(view));
    }

    public final void updateSwipeProgressFromOffset(View view, boolean z, float f) {
        float swipeProgressForOffset = getSwipeProgressForOffset(view, f);
        if (!this.mCallback.updateSwipeProgress(view, z, swipeProgressForOffset) && z) {
            if (!this.mDisableHwLayers) {
                if (swipeProgressForOffset == ActionBarShadowController.ELEVATION_LOW || swipeProgressForOffset == 1.0f) {
                    view.setLayerType(0, null);
                } else {
                    view.setLayerType(2, null);
                }
            }
            updateSwipeProgressAlpha(view, getSwipeAlpha(swipeProgressForOffset));
        }
        invalidateGlobalRegion(view);
    }
}