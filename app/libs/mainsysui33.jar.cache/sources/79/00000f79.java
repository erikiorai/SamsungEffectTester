package com.android.systemui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.FloatProperty;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.settingslib.widget.ActionBarShadowController;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.ExpandableView;
import com.android.systemui.statusbar.policy.ScrollAdapter;
import com.android.wm.shell.animation.FlingAnimationUtils;

/* loaded from: mainsysui33.jar:com/android/systemui/ExpandHelper.class */
public class ExpandHelper implements Gefingerpoken {
    public static final FloatProperty<ViewScaler> VIEW_SCALER_HEIGHT_PROPERTY = new FloatProperty<ViewScaler>("ViewScalerHeight") { // from class: com.android.systemui.ExpandHelper.1
        /* JADX DEBUG: Method merged with bridge method */
        @Override // android.util.Property
        public Float get(ViewScaler viewScaler) {
            return Float.valueOf(viewScaler.getHeight());
        }

        /* JADX DEBUG: Method merged with bridge method */
        @Override // android.util.FloatProperty
        public void setValue(ViewScaler viewScaler, float f) {
            viewScaler.setHeight(f);
        }
    };
    public Callback mCallback;
    public Context mContext;
    public float mCurrentHeight;
    public View mEventSource;
    public boolean mExpanding;
    public FlingAnimationUtils mFlingAnimationUtils;
    public int mGravity;
    public boolean mHasPopped;
    public float mInitialTouchFocusY;
    public float mInitialTouchSpan;
    public float mInitialTouchX;
    public float mInitialTouchY;
    public int mLargeSize;
    public float mLastFocusY;
    public float mLastMotionY;
    public float mLastSpanY;
    public float mMaximumStretch;
    public float mNaturalHeight;
    public float mOldHeight;
    public boolean mOnlyMovements;
    public float mPullGestureMinXSpan;
    public ExpandableView mResizedView;
    public ScaleGestureDetector mSGD;
    public ObjectAnimator mScaleAnimation;
    public ViewScaler mScaler;
    public ScrollAdapter mScrollAdapter;
    public final float mSlopMultiplier;
    public int mSmallSize;
    public final int mTouchSlop;
    public VelocityTracker mVelocityTracker;
    public boolean mWatchingForPull;
    public int mExpansionStyle = 0;
    public boolean mEnabled = true;
    public ScaleGestureDetector.OnScaleGestureListener mScaleGestureListener = new ScaleGestureDetector.SimpleOnScaleGestureListener() { // from class: com.android.systemui.ExpandHelper.2
        @Override // android.view.ScaleGestureDetector.SimpleOnScaleGestureListener, android.view.ScaleGestureDetector.OnScaleGestureListener
        public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
            return true;
        }

        @Override // android.view.ScaleGestureDetector.SimpleOnScaleGestureListener, android.view.ScaleGestureDetector.OnScaleGestureListener
        public boolean onScaleBegin(ScaleGestureDetector scaleGestureDetector) {
            if (!ExpandHelper.this.mOnlyMovements) {
                ExpandHelper expandHelper = ExpandHelper.this;
                expandHelper.startExpanding(expandHelper.mResizedView, 4);
            }
            return ExpandHelper.this.mExpanding;
        }

        @Override // android.view.ScaleGestureDetector.SimpleOnScaleGestureListener, android.view.ScaleGestureDetector.OnScaleGestureListener
        public void onScaleEnd(ScaleGestureDetector scaleGestureDetector) {
        }
    };

    /* loaded from: mainsysui33.jar:com/android/systemui/ExpandHelper$Callback.class */
    public interface Callback {
        boolean canChildBeExpanded(View view);

        void expansionStateChanged(boolean z);

        ExpandableView getChildAtPosition(float f, float f2);

        ExpandableView getChildAtRawPosition(float f, float f2);

        int getMaxExpandHeight(ExpandableView expandableView);

        void setExpansionCancelled(View view);

        void setUserExpandedChild(View view, boolean z);

        void setUserLockedChild(View view, boolean z);
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/ExpandHelper$ViewScaler.class */
    public class ViewScaler {
        public ExpandableView mView;

        public ViewScaler() {
        }

        public float getHeight() {
            return this.mView.getActualHeight();
        }

        public int getNaturalHeight() {
            return ExpandHelper.this.mCallback.getMaxExpandHeight(this.mView);
        }

        public void setHeight(float f) {
            this.mView.setActualHeight((int) f);
            ExpandHelper.this.mCurrentHeight = f;
        }

        public void setView(ExpandableView expandableView) {
            this.mView = expandableView;
        }
    }

    public ExpandHelper(Context context, Callback callback, int i, int i2) {
        this.mSmallSize = i;
        this.mMaximumStretch = i * 2.0f;
        this.mLargeSize = i2;
        this.mContext = context;
        this.mCallback = callback;
        ViewScaler viewScaler = new ViewScaler();
        this.mScaler = viewScaler;
        this.mGravity = 48;
        this.mScaleAnimation = ObjectAnimator.ofFloat(viewScaler, VIEW_SCALER_HEIGHT_PROPERTY, ActionBarShadowController.ELEVATION_LOW);
        this.mPullGestureMinXSpan = this.mContext.getResources().getDimension(R$dimen.pull_span_min);
        this.mTouchSlop = ViewConfiguration.get(this.mContext).getScaledTouchSlop();
        this.mSlopMultiplier = ViewConfiguration.getAmbiguousGestureMultiplier();
        this.mSGD = new ScaleGestureDetector(context, this.mScaleGestureListener);
        this.mFlingAnimationUtils = new FlingAnimationUtils(this.mContext.getResources().getDisplayMetrics(), 0.3f);
    }

    public void cancel() {
        cancel(true);
    }

    public final void cancel(boolean z) {
        finishExpanding(true, ActionBarShadowController.ELEVATION_LOW, z);
        clearView();
        this.mSGD = new ScaleGestureDetector(this.mContext, this.mScaleGestureListener);
    }

    public void cancelImmediately() {
        cancel(false);
    }

    public final float clamp(float f) {
        int i = this.mSmallSize;
        float f2 = f;
        if (f < i) {
            f2 = i;
        }
        float f3 = this.mNaturalHeight;
        float f4 = f2;
        if (f2 > f3) {
            f4 = f3;
        }
        return f4;
    }

    public final void clearView() {
        this.mResizedView = null;
    }

    public final ExpandableView findView(float f, float f2) {
        ExpandableView childAtPosition;
        View view = this.mEventSource;
        if (view != null) {
            int[] iArr = new int[2];
            view.getLocationOnScreen(iArr);
            childAtPosition = this.mCallback.getChildAtRawPosition(f + iArr[0], f2 + iArr[1]);
        } else {
            childAtPosition = this.mCallback.getChildAtPosition(f, f2);
        }
        return childAtPosition;
    }

    @VisibleForTesting
    public void finishExpanding(boolean z, float f) {
        finishExpanding(z, f, true);
    }

    public final void finishExpanding(boolean z, float f, boolean z2) {
        boolean z3;
        if (this.mExpanding) {
            float height = this.mScaler.getHeight();
            float f2 = this.mOldHeight;
            int i = this.mSmallSize;
            boolean z4 = f2 == ((float) i);
            if (z) {
                z3 = !z4;
            } else {
                z3 = (!z4 ? !(height >= f2 || f > ActionBarShadowController.ELEVATION_LOW) : !(height > f2 && f >= ActionBarShadowController.ELEVATION_LOW)) | (this.mNaturalHeight == ((float) i));
            }
            if (this.mScaleAnimation.isRunning()) {
                this.mScaleAnimation.cancel();
            }
            this.mCallback.expansionStateChanged(false);
            int naturalHeight = this.mScaler.getNaturalHeight();
            if (!z3) {
                naturalHeight = this.mSmallSize;
            }
            float f3 = naturalHeight;
            int i2 = (f3 > height ? 1 : (f3 == height ? 0 : -1));
            if (i2 != 0 && this.mEnabled && z2) {
                this.mScaleAnimation.setFloatValues(f3);
                this.mScaleAnimation.setupStartValues();
                final ExpandableView expandableView = this.mResizedView;
                final boolean z5 = z3;
                final boolean z6 = z4;
                this.mScaleAnimation.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.ExpandHelper.3
                    public boolean mCancelled;

                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationCancel(Animator animator) {
                        this.mCancelled = true;
                    }

                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animator) {
                        if (this.mCancelled) {
                            ExpandHelper.this.mCallback.setExpansionCancelled(expandableView);
                        } else {
                            ExpandHelper.this.mCallback.setUserExpandedChild(expandableView, z5);
                            if (!ExpandHelper.this.mExpanding) {
                                ExpandHelper.this.mScaler.setView(null);
                            }
                        }
                        ExpandHelper.this.mCallback.setUserLockedChild(expandableView, false);
                        ExpandHelper.this.mScaleAnimation.removeListener(this);
                        if (z6) {
                            InteractionJankMonitor.getInstance().end(3);
                        }
                    }
                });
                if (z3 != (f >= ActionBarShadowController.ELEVATION_LOW)) {
                    f = 0.0f;
                }
                this.mFlingAnimationUtils.apply(this.mScaleAnimation, height, f3, f);
                this.mScaleAnimation.start();
            } else {
                if (i2 != 0) {
                    this.mScaler.setHeight(f3);
                }
                this.mCallback.setUserExpandedChild(this.mResizedView, z3);
                this.mCallback.setUserLockedChild(this.mResizedView, false);
                this.mScaler.setView(null);
                if (z4) {
                    InteractionJankMonitor.getInstance().end(3);
                }
            }
            this.mExpanding = false;
            this.mExpansionStyle = 0;
        }
    }

    public final float getCurrentVelocity() {
        VelocityTracker velocityTracker = this.mVelocityTracker;
        if (velocityTracker != null) {
            velocityTracker.computeCurrentVelocity(1000);
            return this.mVelocityTracker.getYVelocity();
        }
        return ActionBarShadowController.ELEVATION_LOW;
    }

    @VisibleForTesting
    public ObjectAnimator getScaleAnimation() {
        return this.mScaleAnimation;
    }

    public final float getTouchSlop(MotionEvent motionEvent) {
        return motionEvent.getClassification() == 1 ? this.mTouchSlop * this.mSlopMultiplier : this.mTouchSlop;
    }

    public final boolean isEnabled() {
        return this.mEnabled;
    }

    public final boolean isFullyExpanded(ExpandableView expandableView) {
        return expandableView.getIntrinsicHeight() == expandableView.getMaxContentHeight() && (!expandableView.isSummaryWithChildren() || expandableView.areChildrenExpanded());
    }

    public final boolean isInside(View view, float f, float f2) {
        int[] iArr;
        int[] iArr2;
        if (view == null) {
            return false;
        }
        View view2 = this.mEventSource;
        float f3 = f;
        float f4 = f2;
        if (view2 != null) {
            view2.getLocationOnScreen(new int[2]);
            f3 = f + iArr2[0];
            f4 = f2 + iArr2[1];
        }
        view.getLocationOnScreen(new int[2]);
        float f5 = f3 - iArr[0];
        float f6 = f4 - iArr[1];
        boolean z = false;
        if (f5 > ActionBarShadowController.ELEVATION_LOW) {
            z = false;
            if (f6 > ActionBarShadowController.ELEVATION_LOW) {
                z = false;
                if ((f5 < ((float) view.getWidth())) & (f6 < ((float) view.getHeight()))) {
                    z = true;
                }
            }
        }
        return z;
    }

    public final void maybeRecycleVelocityTracker(MotionEvent motionEvent) {
        if (this.mVelocityTracker != null) {
            if (motionEvent.getActionMasked() == 3 || motionEvent.getActionMasked() == 1) {
                this.mVelocityTracker.recycle();
                this.mVelocityTracker = null;
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:23:0x00a2, code lost:
        if (r0 != 3) goto L22;
     */
    @Override // com.android.systemui.Gefingerpoken
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        boolean z = false;
        if (isEnabled()) {
            trackVelocity(motionEvent);
            int action = motionEvent.getAction();
            this.mSGD.onTouchEvent(motionEvent);
            int focusX = (int) this.mSGD.getFocusX();
            float focusY = (int) this.mSGD.getFocusY();
            this.mInitialTouchFocusY = focusY;
            float currentSpan = this.mSGD.getCurrentSpan();
            this.mInitialTouchSpan = currentSpan;
            this.mLastFocusY = this.mInitialTouchFocusY;
            this.mLastSpanY = currentSpan;
            if (this.mExpanding) {
                this.mLastMotionY = motionEvent.getRawY();
                maybeRecycleVelocityTracker(motionEvent);
                return true;
            } else if (action != 2 || (this.mExpansionStyle & 1) == 0) {
                int i = action & 255;
                if (i != 0) {
                    if (i != 1) {
                        if (i == 2) {
                            float currentSpanX = this.mSGD.getCurrentSpanX();
                            if (currentSpanX > this.mPullGestureMinXSpan && currentSpanX > this.mSGD.getCurrentSpanY() && !this.mExpanding) {
                                startExpanding(this.mResizedView, 2);
                                this.mWatchingForPull = false;
                            }
                            if (this.mWatchingForPull) {
                                float rawY = motionEvent.getRawY() - this.mInitialTouchY;
                                float rawX = motionEvent.getRawX();
                                float f = this.mInitialTouchX;
                                if (rawY > getTouchSlop(motionEvent) && rawY > Math.abs(rawX - f)) {
                                    this.mWatchingForPull = false;
                                    ExpandableView expandableView = this.mResizedView;
                                    if (expandableView != null && !isFullyExpanded(expandableView) && startExpanding(this.mResizedView, 1)) {
                                        this.mLastMotionY = motionEvent.getRawY();
                                        this.mInitialTouchY = motionEvent.getRawY();
                                        this.mHasPopped = false;
                                    }
                                }
                            }
                        }
                    }
                    if (motionEvent.getActionMasked() == 3) {
                        z = true;
                    }
                    finishExpanding(z, getCurrentVelocity());
                    clearView();
                } else {
                    ScrollAdapter scrollAdapter = this.mScrollAdapter;
                    this.mWatchingForPull = scrollAdapter != null && isInside(scrollAdapter.getHostView(), (float) focusX, focusY) && this.mScrollAdapter.isScrolledToTop();
                    View findView = findView(focusX, focusY);
                    this.mResizedView = findView;
                    if (findView != null && !this.mCallback.canChildBeExpanded(findView)) {
                        this.mResizedView = null;
                        this.mWatchingForPull = false;
                    }
                    this.mInitialTouchY = motionEvent.getRawY();
                    this.mInitialTouchX = motionEvent.getRawX();
                }
                this.mLastMotionY = motionEvent.getRawY();
                maybeRecycleVelocityTracker(motionEvent);
                return this.mExpanding;
            } else {
                return true;
            }
        }
        return false;
    }

    @Override // com.android.systemui.Gefingerpoken
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (isEnabled() || this.mExpanding) {
            trackVelocity(motionEvent);
            int actionMasked = motionEvent.getActionMasked();
            this.mSGD.onTouchEvent(motionEvent);
            int focusX = (int) this.mSGD.getFocusX();
            int focusY = (int) this.mSGD.getFocusY();
            if (this.mOnlyMovements) {
                this.mLastMotionY = motionEvent.getRawY();
                return false;
            }
            if (actionMasked != 0) {
                if (actionMasked != 1) {
                    if (actionMasked == 2) {
                        if (this.mWatchingForPull) {
                            float rawY = motionEvent.getRawY() - this.mInitialTouchY;
                            float rawX = motionEvent.getRawX();
                            float f = this.mInitialTouchX;
                            if (rawY > getTouchSlop(motionEvent) && rawY > Math.abs(rawX - f)) {
                                this.mWatchingForPull = false;
                                ExpandableView expandableView = this.mResizedView;
                                if (expandableView != null && !isFullyExpanded(expandableView) && startExpanding(this.mResizedView, 1)) {
                                    this.mInitialTouchY = motionEvent.getRawY();
                                    this.mLastMotionY = motionEvent.getRawY();
                                    this.mHasPopped = false;
                                }
                            }
                        }
                        boolean z = this.mExpanding;
                        if (z && (this.mExpansionStyle & 1) != 0) {
                            float rawY2 = (motionEvent.getRawY() - this.mLastMotionY) + this.mCurrentHeight;
                            float clamp = clamp(rawY2);
                            boolean z2 = rawY2 > this.mNaturalHeight;
                            if (rawY2 < this.mSmallSize) {
                                z2 = true;
                            }
                            if (!this.mHasPopped) {
                                View view = this.mEventSource;
                                if (view != null) {
                                    view.performHapticFeedback(1);
                                }
                                this.mHasPopped = true;
                            }
                            this.mScaler.setHeight(clamp);
                            this.mLastMotionY = motionEvent.getRawY();
                            if (z2) {
                                this.mCallback.expansionStateChanged(false);
                                return true;
                            }
                            this.mCallback.expansionStateChanged(true);
                            return true;
                        } else if (z) {
                            updateExpansion();
                            this.mLastMotionY = motionEvent.getRawY();
                            return true;
                        }
                    } else if (actionMasked != 3) {
                        if (actionMasked == 5 || actionMasked == 6) {
                            this.mInitialTouchY += this.mSGD.getFocusY() - this.mLastFocusY;
                            this.mInitialTouchSpan += this.mSGD.getCurrentSpan() - this.mLastSpanY;
                        }
                    }
                }
                finishExpanding(!isEnabled() || motionEvent.getActionMasked() == 3, getCurrentVelocity());
                clearView();
            } else {
                ScrollAdapter scrollAdapter = this.mScrollAdapter;
                this.mWatchingForPull = scrollAdapter != null && isInside(scrollAdapter.getHostView(), (float) focusX, (float) focusY);
                this.mResizedView = findView(focusX, focusY);
                this.mInitialTouchX = motionEvent.getRawX();
                this.mInitialTouchY = motionEvent.getRawY();
            }
            this.mLastMotionY = motionEvent.getRawY();
            maybeRecycleVelocityTracker(motionEvent);
            boolean z3 = false;
            if (this.mResizedView != null) {
                z3 = true;
            }
            return z3;
        }
        return false;
    }

    public void onlyObserveMovements(boolean z) {
        this.mOnlyMovements = z;
    }

    public void setEnabled(boolean z) {
        this.mEnabled = z;
    }

    public void setEventSource(View view) {
        this.mEventSource = view;
    }

    public void setScrollAdapter(ScrollAdapter scrollAdapter) {
        this.mScrollAdapter = scrollAdapter;
    }

    @VisibleForTesting
    public boolean startExpanding(ExpandableView expandableView, int i) {
        if (expandableView instanceof ExpandableNotificationRow) {
            this.mExpansionStyle = i;
            if (this.mExpanding && expandableView == this.mResizedView) {
                return true;
            }
            this.mExpanding = true;
            this.mCallback.expansionStateChanged(true);
            this.mCallback.setUserLockedChild(expandableView, true);
            this.mScaler.setView(expandableView);
            float height = this.mScaler.getHeight();
            this.mOldHeight = height;
            this.mCurrentHeight = height;
            if (this.mCallback.canChildBeExpanded(expandableView)) {
                this.mNaturalHeight = this.mScaler.getNaturalHeight();
                this.mSmallSize = expandableView.getCollapsedHeight();
            } else {
                this.mNaturalHeight = this.mOldHeight;
            }
            InteractionJankMonitor.getInstance().begin(expandableView, 3);
            return true;
        }
        return false;
    }

    public final void trackVelocity(MotionEvent motionEvent) {
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked != 0) {
            if (actionMasked != 2) {
                return;
            }
            if (this.mVelocityTracker == null) {
                this.mVelocityTracker = VelocityTracker.obtain();
            }
            this.mVelocityTracker.addMovement(motionEvent);
            return;
        }
        VelocityTracker velocityTracker = this.mVelocityTracker;
        if (velocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        } else {
            velocityTracker.clear();
        }
        this.mVelocityTracker.addMovement(motionEvent);
    }

    @VisibleForTesting
    public void updateExpansion() {
        float currentSpan = (this.mSGD.getCurrentSpan() - this.mInitialTouchSpan) * 1.0f;
        float focusY = (this.mSGD.getFocusY() - this.mInitialTouchFocusY) * 1.0f * (this.mGravity == 80 ? -1.0f : 1.0f);
        float abs = Math.abs(focusY) + Math.abs(currentSpan) + 1.0f;
        this.mScaler.setHeight(clamp(((focusY * Math.abs(focusY)) / abs) + ((currentSpan * Math.abs(currentSpan)) / abs) + this.mOldHeight));
        this.mLastFocusY = this.mSGD.getFocusY();
        this.mLastSpanY = this.mSGD.getCurrentSpan();
    }
}