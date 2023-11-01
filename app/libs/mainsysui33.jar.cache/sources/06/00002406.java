package com.android.systemui.screenshot;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.MathUtils;
import android.util.Range;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.widget.SeekBar;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.customview.widget.ExploreByTouchHelper;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;
import androidx.recyclerview.widget.RecyclerView;
import com.android.internal.graphics.ColorUtils;
import com.android.settingslib.widget.ActionBarShadowController;
import com.android.systemui.R$string;
import com.android.systemui.R$styleable;
import java.util.List;

/* loaded from: mainsysui33.jar:com/android/systemui/screenshot/CropView.class */
public class CropView extends View {
    public int mActivePointerId;
    public final Paint mContainerBackgroundPaint;
    public RectF mCrop;
    public CropInteractionListener mCropInteractionListener;
    public final float mCropTouchMargin;
    public CropBoundary mCurrentDraggingBoundary;
    public float mEntranceInterpolation;
    public final ExploreByTouchHelper mExploreByTouchHelper;
    public int mExtraBottomPadding;
    public int mExtraTopPadding;
    public final Paint mHandlePaint;
    public int mImageWidth;
    public float mMovementStartValueX;
    public float mMovementStartValueY;
    public final Paint mShadePaint;
    public float mStartingX;
    public float mStartingY;

    /* renamed from: com.android.systemui.screenshot.CropView$1 */
    /* loaded from: mainsysui33.jar:com/android/systemui/screenshot/CropView$1.class */
    public static /* synthetic */ class AnonymousClass1 {
        public static final /* synthetic */ int[] $SwitchMap$com$android$systemui$screenshot$CropView$CropBoundary;

        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:101:0x0099 -> B:107:0x0064). Please submit an issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:103:0x009d -> B:121:0x0070). Please submit an issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:87:0x007d -> B:125:0x0014). Please submit an issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:89:0x0081 -> B:119:0x001f). Please submit an issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:91:0x0085 -> B:115:0x002a). Please submit an issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:93:0x0089 -> B:109:0x0035). Please submit an issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:95:0x008d -> B:123:0x0040). Please submit an issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:97:0x0091 -> B:117:0x004c). Please submit an issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:99:0x0095 -> B:113:0x0058). Please submit an issue!!! */
        static {
            int[] iArr = new int[CropBoundary.values().length];
            $SwitchMap$com$android$systemui$screenshot$CropView$CropBoundary = iArr;
            try {
                iArr[CropBoundary.TOP.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$android$systemui$screenshot$CropView$CropBoundary[CropBoundary.BOTTOM.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$android$systemui$screenshot$CropView$CropBoundary[CropBoundary.LEFT.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$android$systemui$screenshot$CropView$CropBoundary[CropBoundary.RIGHT.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$android$systemui$screenshot$CropView$CropBoundary[CropBoundary.NONE.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$com$android$systemui$screenshot$CropView$CropBoundary[CropBoundary.TOP_LEFT.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$com$android$systemui$screenshot$CropView$CropBoundary[CropBoundary.TOP_RIGHT.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
            try {
                $SwitchMap$com$android$systemui$screenshot$CropView$CropBoundary[CropBoundary.MIDDLE.ordinal()] = 8;
            } catch (NoSuchFieldError e8) {
            }
            try {
                $SwitchMap$com$android$systemui$screenshot$CropView$CropBoundary[CropBoundary.BOTTOM_LEFT.ordinal()] = 9;
            } catch (NoSuchFieldError e9) {
            }
            try {
                $SwitchMap$com$android$systemui$screenshot$CropView$CropBoundary[CropBoundary.BOTTOM_RIGHT.ordinal()] = 10;
            } catch (NoSuchFieldError e10) {
            }
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/screenshot/CropView$AccessibilityHelper.class */
    public class AccessibilityHelper extends ExploreByTouchHelper {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AccessibilityHelper() {
            super(r4);
            CropView.this = r4;
        }

        public final CharSequence getBoundaryContentDescription(CropBoundary cropBoundary) {
            int i;
            int i2 = AnonymousClass1.$SwitchMap$com$android$systemui$screenshot$CropView$CropBoundary[cropBoundary.ordinal()];
            if (i2 == 1) {
                i = R$string.screenshot_top_boundary_pct;
            } else if (i2 == 2) {
                i = R$string.screenshot_bottom_boundary_pct;
            } else if (i2 == 3) {
                i = R$string.screenshot_left_boundary_pct;
            } else if (i2 != 4) {
                return "";
            } else {
                i = R$string.screenshot_right_boundary_pct;
            }
            return CropView.this.getResources().getString(i, Integer.valueOf(Math.round(CropView.this.getBoundaryPosition(cropBoundary) * 100.0f)));
        }

        public final Rect getNodeRect(CropBoundary cropBoundary) {
            Rect rect;
            if (CropView.isVertical(cropBoundary)) {
                CropView cropView = CropView.this;
                float fractionToVerticalPixels = cropView.fractionToVerticalPixels(cropView.getBoundaryPosition(cropBoundary));
                Rect rect2 = new Rect(0, (int) (fractionToVerticalPixels - CropView.this.mCropTouchMargin), CropView.this.getWidth(), (int) (fractionToVerticalPixels + CropView.this.mCropTouchMargin));
                int i = rect2.top;
                rect = rect2;
                if (i < 0) {
                    rect2.offset(0, -i);
                    rect = rect2;
                }
            } else {
                CropView cropView2 = CropView.this;
                float fractionToHorizontalPixels = cropView2.fractionToHorizontalPixels(cropView2.getBoundaryPosition(cropBoundary));
                int i2 = (int) (fractionToHorizontalPixels - CropView.this.mCropTouchMargin);
                CropView cropView3 = CropView.this;
                int fractionToVerticalPixels2 = (int) (cropView3.fractionToVerticalPixels(cropView3.mCrop.top) + CropView.this.mCropTouchMargin);
                int i3 = (int) (fractionToHorizontalPixels + CropView.this.mCropTouchMargin);
                CropView cropView4 = CropView.this;
                rect = new Rect(i2, fractionToVerticalPixels2, i3, (int) (cropView4.fractionToVerticalPixels(cropView4.mCrop.bottom) - CropView.this.mCropTouchMargin));
            }
            return rect;
        }

        @Override // androidx.customview.widget.ExploreByTouchHelper
        public int getVirtualViewAt(float f, float f2) {
            CropView cropView = CropView.this;
            if (Math.abs(f2 - cropView.fractionToVerticalPixels(cropView.mCrop.top)) < CropView.this.mCropTouchMargin) {
                return 1;
            }
            CropView cropView2 = CropView.this;
            if (Math.abs(f2 - cropView2.fractionToVerticalPixels(cropView2.mCrop.bottom)) < CropView.this.mCropTouchMargin) {
                return 2;
            }
            CropView cropView3 = CropView.this;
            if (f2 > cropView3.fractionToVerticalPixels(cropView3.mCrop.top)) {
                CropView cropView4 = CropView.this;
                if (f2 < cropView4.fractionToVerticalPixels(cropView4.mCrop.bottom)) {
                    CropView cropView5 = CropView.this;
                    if (Math.abs(f - cropView5.fractionToHorizontalPixels(cropView5.mCrop.left)) < CropView.this.mCropTouchMargin) {
                        return 3;
                    }
                    CropView cropView6 = CropView.this;
                    return Math.abs(f - ((float) cropView6.fractionToHorizontalPixels(cropView6.mCrop.right))) < CropView.this.mCropTouchMargin ? 4 : -1;
                }
                return -1;
            }
            return -1;
        }

        @Override // androidx.customview.widget.ExploreByTouchHelper
        public void getVisibleVirtualViews(List<Integer> list) {
            list.add(1);
            list.add(3);
            list.add(4);
            list.add(2);
        }

        @Override // androidx.customview.widget.ExploreByTouchHelper
        public boolean onPerformActionForVirtualView(int i, int i2, Bundle bundle) {
            if (i2 == 4096 || i2 == 8192) {
                CropBoundary viewIdToBoundary = viewIdToBoundary(i);
                CropView cropView = CropView.this;
                float pixelDistanceToFraction = cropView.pixelDistanceToFraction(cropView.mCropTouchMargin, viewIdToBoundary);
                float f = pixelDistanceToFraction;
                if (i2 == 4096) {
                    f = -pixelDistanceToFraction;
                }
                CropView cropView2 = CropView.this;
                cropView2.setBoundaryPosition(viewIdToBoundary, f + cropView2.getBoundaryPosition(viewIdToBoundary));
                invalidateVirtualView(i);
                sendEventForVirtualView(i, 4);
                return true;
            }
            return false;
        }

        @Override // androidx.customview.widget.ExploreByTouchHelper
        public void onPopulateEventForVirtualView(int i, AccessibilityEvent accessibilityEvent) {
            accessibilityEvent.setContentDescription(getBoundaryContentDescription(viewIdToBoundary(i)));
        }

        @Override // androidx.customview.widget.ExploreByTouchHelper
        public void onPopulateNodeForVirtualView(int i, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            CropBoundary viewIdToBoundary = viewIdToBoundary(i);
            accessibilityNodeInfoCompat.setContentDescription(getBoundaryContentDescription(viewIdToBoundary));
            setNodePosition(getNodeRect(viewIdToBoundary), accessibilityNodeInfoCompat);
            accessibilityNodeInfoCompat.setClassName(SeekBar.class.getName());
            accessibilityNodeInfoCompat.addAction(RecyclerView.ViewHolder.FLAG_APPEARED_IN_PRE_LAYOUT);
            accessibilityNodeInfoCompat.addAction(RecyclerView.ViewHolder.FLAG_BOUNCED_FROM_HIDDEN_LIST);
        }

        public final void setNodePosition(Rect rect, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            accessibilityNodeInfoCompat.setBoundsInParent(rect);
            int[] iArr = new int[2];
            CropView.this.getLocationOnScreen(iArr);
            rect.offset(iArr[0], iArr[1]);
            accessibilityNodeInfoCompat.setBoundsInScreen(rect);
        }

        public final CropBoundary viewIdToBoundary(int i) {
            return i != 1 ? i != 2 ? i != 3 ? i != 4 ? CropBoundary.NONE : CropBoundary.RIGHT : CropBoundary.LEFT : CropBoundary.BOTTOM : CropBoundary.TOP;
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/screenshot/CropView$CropBoundary.class */
    public enum CropBoundary {
        NONE,
        TOP,
        BOTTOM,
        LEFT,
        RIGHT,
        TOP_LEFT,
        TOP_RIGHT,
        BOTTOM_LEFT,
        BOTTOM_RIGHT,
        MIDDLE
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/screenshot/CropView$CropInteractionListener.class */
    public interface CropInteractionListener {
        void onCropDragComplete();

        void onCropDragMoved(CropBoundary cropBoundary, float f, int i, float f2, float f3);

        void onCropDragStarted(CropBoundary cropBoundary, float f, int i, float f2, float f3);
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/screenshot/CropView$SavedState.class */
    public static class SavedState extends View.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() { // from class: com.android.systemui.screenshot.CropView.SavedState.1
            @Override // android.os.Parcelable.Creator
            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            @Override // android.os.Parcelable.Creator
            public SavedState[] newArray(int i) {
                return new SavedState[i];
            }
        };
        public RectF mCrop;

        public SavedState(Parcel parcel) {
            super(parcel);
            this.mCrop = (RectF) parcel.readParcelable(ClassLoader.getSystemClassLoader());
        }

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        @Override // android.view.View.BaseSavedState, android.view.AbsSavedState, android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeParcelable(this.mCrop, 0);
        }
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.screenshot.CropView$$ExternalSyntheticLambda0.onAnimationUpdate(android.animation.ValueAnimator):void] */
    public static /* synthetic */ void $r8$lambda$Eu3lnVV9CoXVrdC1xPAUci9nHYI(CropView cropView, ValueAnimator valueAnimator) {
        cropView.lambda$animateEntrance$1(valueAnimator);
    }

    public CropView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public CropView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mCrop = new RectF(ActionBarShadowController.ELEVATION_LOW, ActionBarShadowController.ELEVATION_LOW, 1.0f, 1.0f);
        this.mCurrentDraggingBoundary = CropBoundary.NONE;
        this.mEntranceInterpolation = 1.0f;
        TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(attributeSet, R$styleable.CropView, 0, 0);
        Paint paint = new Paint();
        this.mShadePaint = paint;
        paint.setColor(ColorUtils.setAlphaComponent(obtainStyledAttributes.getColor(R$styleable.CropView_scrimColor, 0), obtainStyledAttributes.getInteger(R$styleable.CropView_scrimAlpha, 255)));
        Paint paint2 = new Paint();
        this.mContainerBackgroundPaint = paint2;
        paint2.setColor(obtainStyledAttributes.getColor(R$styleable.CropView_containerBackgroundColor, 0));
        Paint paint3 = new Paint();
        this.mHandlePaint = paint3;
        paint3.setColor(obtainStyledAttributes.getColor(R$styleable.CropView_handleColor, -16777216));
        paint3.setStrokeCap(Paint.Cap.ROUND);
        paint3.setStrokeWidth(obtainStyledAttributes.getDimensionPixelSize(R$styleable.CropView_handleThickness, 20));
        obtainStyledAttributes.recycle();
        this.mCropTouchMargin = getResources().getDisplayMetrics().density * 24.0f;
        AccessibilityHelper accessibilityHelper = new AccessibilityHelper();
        this.mExploreByTouchHelper = accessibilityHelper;
        ViewCompat.setAccessibilityDelegate(this, accessibilityHelper);
    }

    public static boolean isVertical(CropBoundary cropBoundary) {
        return cropBoundary == CropBoundary.TOP || cropBoundary == CropBoundary.BOTTOM;
    }

    public /* synthetic */ void lambda$animateEntrance$1(ValueAnimator valueAnimator) {
        this.mEntranceInterpolation = valueAnimator.getAnimatedFraction();
        invalidate();
    }

    public void animateEntrance() {
        this.mEntranceInterpolation = ActionBarShadowController.ELEVATION_LOW;
        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.screenshot.CropView$$ExternalSyntheticLambda0
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                CropView.$r8$lambda$Eu3lnVV9CoXVrdC1xPAUci9nHYI(CropView.this, valueAnimator2);
            }
        });
        valueAnimator.setFloatValues(ActionBarShadowController.ELEVATION_LOW, 1.0f);
        valueAnimator.setDuration(750L);
        valueAnimator.setInterpolator(new FastOutSlowInInterpolator());
        valueAnimator.start();
    }

    @Override // android.view.View
    public boolean dispatchHoverEvent(MotionEvent motionEvent) {
        return this.mExploreByTouchHelper.dispatchHoverEvent(motionEvent) || super.dispatchHoverEvent(motionEvent);
    }

    @Override // android.view.View
    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        return this.mExploreByTouchHelper.dispatchKeyEvent(keyEvent) || super.dispatchKeyEvent(keyEvent);
    }

    public final void drawContainerBackground(Canvas canvas, float f, float f2, float f3, float f4) {
        canvas.drawRect(fractionToHorizontalPixels(f), fractionToVerticalPixels(f2), fractionToHorizontalPixels(f3), fractionToVerticalPixels(f4), this.mContainerBackgroundPaint);
    }

    public final void drawHorizontalHandle(Canvas canvas, float f, boolean z) {
        float fractionToVerticalPixels = fractionToVerticalPixels(f);
        canvas.drawLine(fractionToHorizontalPixels(this.mCrop.left), fractionToVerticalPixels, fractionToHorizontalPixels(this.mCrop.right), fractionToVerticalPixels, this.mHandlePaint);
        float f2 = getResources().getDisplayMetrics().density * 8.0f;
        float fractionToHorizontalPixels = (fractionToHorizontalPixels(this.mCrop.left) + fractionToHorizontalPixels(this.mCrop.right)) / 2;
        canvas.drawArc(fractionToHorizontalPixels - f2, fractionToVerticalPixels - f2, fractionToHorizontalPixels + f2, fractionToVerticalPixels + f2, z ? 180.0f : 0.0f, 180.0f, true, this.mHandlePaint);
    }

    public final void drawShade(Canvas canvas, float f, float f2, float f3, float f4) {
        canvas.drawRect(fractionToHorizontalPixels(f), fractionToVerticalPixels(f2), fractionToHorizontalPixels(f3), fractionToVerticalPixels(f4), this.mShadePaint);
    }

    public final void drawVerticalHandle(Canvas canvas, float f, boolean z) {
        float fractionToHorizontalPixels = fractionToHorizontalPixels(f);
        canvas.drawLine(fractionToHorizontalPixels, fractionToVerticalPixels(this.mCrop.top), fractionToHorizontalPixels, fractionToVerticalPixels(this.mCrop.bottom), this.mHandlePaint);
        float f2 = getResources().getDisplayMetrics().density * 8.0f;
        float fractionToVerticalPixels = (fractionToVerticalPixels(getBoundaryPosition(CropBoundary.TOP)) + fractionToVerticalPixels(getBoundaryPosition(CropBoundary.BOTTOM))) / 2;
        canvas.drawArc(fractionToHorizontalPixels - f2, fractionToVerticalPixels - f2, fractionToHorizontalPixels + f2, fractionToVerticalPixels + f2, z ? 90.0f : 270.0f, 180.0f, true, this.mHandlePaint);
    }

    public final int fractionToHorizontalPixels(float f) {
        int width = getWidth();
        int i = this.mImageWidth;
        return (int) (((width - i) / 2) + (f * i));
    }

    public final int fractionToVerticalPixels(float f) {
        return (int) (this.mExtraTopPadding + (f * getImageHeight()));
    }

    public final Range getAllowedValues(CropBoundary cropBoundary) {
        int i = AnonymousClass1.$SwitchMap$com$android$systemui$screenshot$CropView$CropBoundary[cropBoundary.ordinal()];
        if (i == 1) {
            if (this.mCurrentDraggingBoundary == CropBoundary.MIDDLE) {
                RectF rectF = this.mCrop;
                return new Range(Float.valueOf((float) ActionBarShadowController.ELEVATION_LOW), Float.valueOf(1.0f - (rectF.bottom - rectF.top)));
            }
            return new Range(Float.valueOf((float) ActionBarShadowController.ELEVATION_LOW), Float.valueOf(this.mCrop.bottom - pixelDistanceToFraction(this.mCropTouchMargin, CropBoundary.BOTTOM)));
        } else if (i != 2) {
            if (i != 3) {
                if (i != 4) {
                    return null;
                }
                return new Range(Float.valueOf(this.mCrop.left + pixelDistanceToFraction(this.mCropTouchMargin, CropBoundary.LEFT)), Float.valueOf(1.0f));
            } else if (this.mCurrentDraggingBoundary == CropBoundary.MIDDLE) {
                RectF rectF2 = this.mCrop;
                return new Range(Float.valueOf((float) ActionBarShadowController.ELEVATION_LOW), Float.valueOf(1.0f - (rectF2.right - rectF2.left)));
            } else {
                return new Range(Float.valueOf((float) ActionBarShadowController.ELEVATION_LOW), Float.valueOf(this.mCrop.right - pixelDistanceToFraction(this.mCropTouchMargin, CropBoundary.RIGHT)));
            }
        } else {
            return new Range(Float.valueOf(this.mCrop.top + pixelDistanceToFraction(this.mCropTouchMargin, CropBoundary.TOP)), Float.valueOf(1.0f));
        }
    }

    public final float getBoundaryPosition(CropBoundary cropBoundary) {
        int i = AnonymousClass1.$SwitchMap$com$android$systemui$screenshot$CropView$CropBoundary[cropBoundary.ordinal()];
        return i != 1 ? i != 2 ? i != 3 ? i != 4 ? ActionBarShadowController.ELEVATION_LOW : this.mCrop.right : this.mCrop.left : this.mCrop.bottom : this.mCrop.top;
    }

    public Rect getCropBoundaries(int i, int i2) {
        RectF rectF = this.mCrop;
        float f = rectF.left;
        float f2 = i;
        int i3 = (int) (f * f2);
        float f3 = rectF.top;
        float f4 = i2;
        return new Rect(i3, (int) (f3 * f4), (int) (rectF.right * f2), (int) (rectF.bottom * f4));
    }

    public final CropBoundary getHorizontalBoundary(CropBoundary cropBoundary) {
        switch (AnonymousClass1.$SwitchMap$com$android$systemui$screenshot$CropView$CropBoundary[cropBoundary.ordinal()]) {
            case 3:
            case 6:
            case 8:
            case 9:
                return CropBoundary.LEFT;
            case 4:
            case 7:
            case 10:
                return CropBoundary.RIGHT;
            case 5:
            default:
                return CropBoundary.NONE;
        }
    }

    public final int getImageHeight() {
        return (getHeight() - this.mExtraTopPadding) - this.mExtraBottomPadding;
    }

    public final CropBoundary getVerticalBoundary(CropBoundary cropBoundary) {
        int i = AnonymousClass1.$SwitchMap$com$android$systemui$screenshot$CropView$CropBoundary[cropBoundary.ordinal()];
        if (i != 1) {
            if (i != 2) {
                switch (i) {
                    case 6:
                    case 7:
                    case 8:
                        break;
                    case 9:
                    case 10:
                        break;
                    default:
                        return CropBoundary.NONE;
                }
            }
            return CropBoundary.BOTTOM;
        }
        return CropBoundary.TOP;
    }

    public final CropBoundary nearestBoundary(MotionEvent motionEvent, int i, int i2, int i3, int i4) {
        float f = i3;
        boolean z = Math.abs(motionEvent.getX() - f) < this.mCropTouchMargin;
        float f2 = i4;
        boolean z2 = Math.abs(motionEvent.getX() - f2) < this.mCropTouchMargin;
        float f3 = i;
        if (Math.abs(motionEvent.getY() - f3) < this.mCropTouchMargin) {
            return z ? CropBoundary.TOP_LEFT : z2 ? CropBoundary.TOP_RIGHT : CropBoundary.TOP;
        }
        float f4 = i2;
        if (Math.abs(motionEvent.getY() - f4) < this.mCropTouchMargin) {
            return z ? CropBoundary.BOTTOM_LEFT : z2 ? CropBoundary.BOTTOM_RIGHT : CropBoundary.BOTTOM;
        }
        if (motionEvent.getY() > f3 || motionEvent.getY() < f4) {
            if (Math.abs(motionEvent.getX() - f) < this.mCropTouchMargin) {
                return CropBoundary.LEFT;
            }
            if (Math.abs(motionEvent.getX() - f2) < this.mCropTouchMargin) {
                return CropBoundary.RIGHT;
            }
        }
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        return (x <= f || x >= f2 || y <= f3 || y >= f4) ? CropBoundary.NONE : CropBoundary.MIDDLE;
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float lerp = MathUtils.lerp(this.mCrop.top, (float) ActionBarShadowController.ELEVATION_LOW, this.mEntranceInterpolation);
        float lerp2 = MathUtils.lerp(this.mCrop.bottom, 1.0f, this.mEntranceInterpolation);
        drawShade(canvas, ActionBarShadowController.ELEVATION_LOW, lerp, 1.0f, this.mCrop.top);
        drawShade(canvas, ActionBarShadowController.ELEVATION_LOW, this.mCrop.bottom, 1.0f, lerp2);
        RectF rectF = this.mCrop;
        drawShade(canvas, ActionBarShadowController.ELEVATION_LOW, rectF.top, rectF.left, rectF.bottom);
        RectF rectF2 = this.mCrop;
        drawShade(canvas, rectF2.right, rectF2.top, 1.0f, rectF2.bottom);
        drawContainerBackground(canvas, ActionBarShadowController.ELEVATION_LOW, ActionBarShadowController.ELEVATION_LOW, 1.0f, lerp);
        drawContainerBackground(canvas, ActionBarShadowController.ELEVATION_LOW, lerp2, 1.0f, 1.0f);
        this.mHandlePaint.setAlpha((int) (this.mEntranceInterpolation * 255.0f));
        drawHorizontalHandle(canvas, this.mCrop.top, true);
        drawHorizontalHandle(canvas, this.mCrop.bottom, false);
        drawVerticalHandle(canvas, this.mCrop.left, true);
        drawVerticalHandle(canvas, this.mCrop.right, false);
    }

    @Override // android.view.View
    public void onFocusChanged(boolean z, int i, Rect rect) {
        super.onFocusChanged(z, i, rect);
        this.mExploreByTouchHelper.onFocusChanged(z, i, rect);
    }

    @Override // android.view.View
    public void onRestoreInstanceState(Parcelable parcelable) {
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        this.mCrop = savedState.mCrop;
    }

    @Override // android.view.View
    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.mCrop = this.mCrop;
        return savedState;
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        int i;
        int fractionToVerticalPixels = fractionToVerticalPixels(this.mCrop.top);
        int fractionToVerticalPixels2 = fractionToVerticalPixels(this.mCrop.bottom);
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 0) {
            CropBoundary nearestBoundary = nearestBoundary(motionEvent, fractionToVerticalPixels, fractionToVerticalPixels2, fractionToHorizontalPixels(this.mCrop.left), fractionToHorizontalPixels(this.mCrop.right));
            this.mCurrentDraggingBoundary = nearestBoundary;
            if (nearestBoundary != CropBoundary.NONE) {
                this.mActivePointerId = motionEvent.getPointerId(0);
                this.mStartingY = motionEvent.getY();
                this.mStartingX = motionEvent.getX();
                CropBoundary horizontalBoundary = getHorizontalBoundary(this.mCurrentDraggingBoundary);
                CropBoundary verticalBoundary = getVerticalBoundary(this.mCurrentDraggingBoundary);
                this.mMovementStartValueX = getBoundaryPosition(horizontalBoundary);
                this.mMovementStartValueY = getBoundaryPosition(verticalBoundary);
                updateListener(0, motionEvent.getX());
                return true;
            }
            return true;
        }
        if (actionMasked != 1) {
            if (actionMasked != 2) {
                if (actionMasked != 3) {
                    if (actionMasked != 5) {
                        if (actionMasked == 6 && this.mActivePointerId == motionEvent.getPointerId(motionEvent.getActionIndex()) && this.mCurrentDraggingBoundary != CropBoundary.NONE) {
                            updateListener(1, motionEvent.getX(motionEvent.getActionIndex()));
                            return true;
                        }
                    } else if (this.mActivePointerId == motionEvent.getPointerId(motionEvent.getActionIndex()) && this.mCurrentDraggingBoundary != CropBoundary.NONE) {
                        updateListener(0, motionEvent.getX(motionEvent.getActionIndex()));
                        return true;
                    }
                }
            } else if (this.mCurrentDraggingBoundary != CropBoundary.NONE) {
                int findPointerIndex = motionEvent.findPointerIndex(this.mActivePointerId);
                if (findPointerIndex >= 0) {
                    CropBoundary horizontalBoundary2 = getHorizontalBoundary(this.mCurrentDraggingBoundary);
                    CropBoundary verticalBoundary2 = getVerticalBoundary(this.mCurrentDraggingBoundary);
                    float x = motionEvent.getX(findPointerIndex);
                    float f = this.mStartingX;
                    float y = motionEvent.getY(findPointerIndex);
                    float f2 = this.mStartingY;
                    float pixelDistanceToFraction = pixelDistanceToFraction((int) (x - f), horizontalBoundary2);
                    float pixelDistanceToFraction2 = pixelDistanceToFraction((int) (y - f2), verticalBoundary2);
                    setBoundaryPosition(horizontalBoundary2, this.mMovementStartValueX + pixelDistanceToFraction);
                    setBoundaryPosition(verticalBoundary2, this.mMovementStartValueY + pixelDistanceToFraction2);
                    updateListener(2, motionEvent.getX(findPointerIndex));
                    invalidate();
                    return true;
                }
                return true;
            }
            return super.onTouchEvent(motionEvent);
        }
        if (this.mCurrentDraggingBoundary != CropBoundary.NONE && (i = this.mActivePointerId) == motionEvent.getPointerId(i)) {
            updateListener(1, motionEvent.getX(0));
            return true;
        }
        return super.onTouchEvent(motionEvent);
    }

    public final float pixelDistanceToFraction(float f, CropBoundary cropBoundary) {
        return f / (isVertical(cropBoundary) ? getImageHeight() : this.mImageWidth);
    }

    public void setBoundaryPosition(CropBoundary cropBoundary, float f) {
        if (cropBoundary == CropBoundary.NONE) {
            return;
        }
        float floatValue = ((Float) getAllowedValues(cropBoundary).clamp(Float.valueOf(f))).floatValue();
        int i = AnonymousClass1.$SwitchMap$com$android$systemui$screenshot$CropView$CropBoundary[cropBoundary.ordinal()];
        if (i == 1) {
            if (this.mCurrentDraggingBoundary == CropBoundary.MIDDLE) {
                RectF rectF = this.mCrop;
                rectF.bottom = (rectF.bottom - rectF.top) + floatValue;
            }
            this.mCrop.top = floatValue;
        } else if (i == 2) {
            this.mCrop.bottom = floatValue;
        } else if (i == 3) {
            if (this.mCurrentDraggingBoundary == CropBoundary.MIDDLE) {
                RectF rectF2 = this.mCrop;
                rectF2.right = (rectF2.right - rectF2.left) + floatValue;
            }
            this.mCrop.left = floatValue;
        } else if (i == 4) {
            this.mCrop.right = floatValue;
        } else if (i == 5) {
            Log.w("CropView", "No boundary selected");
        }
        invalidate();
    }

    public void setCropInteractionListener(CropInteractionListener cropInteractionListener) {
        this.mCropInteractionListener = cropInteractionListener;
    }

    public void setExtraPadding(int i, int i2) {
        this.mExtraTopPadding = i;
        this.mExtraBottomPadding = i2;
        invalidate();
    }

    public void setImageWidth(int i) {
        this.mImageWidth = i;
        invalidate();
    }

    public final void updateListener(int i, float f) {
        CropBoundary verticalBoundary = getVerticalBoundary(this.mCurrentDraggingBoundary);
        if (this.mCropInteractionListener == null || verticalBoundary == CropBoundary.NONE) {
            return;
        }
        float boundaryPosition = getBoundaryPosition(verticalBoundary);
        if (i == 0) {
            CropInteractionListener cropInteractionListener = this.mCropInteractionListener;
            int fractionToVerticalPixels = fractionToVerticalPixels(boundaryPosition);
            RectF rectF = this.mCrop;
            cropInteractionListener.onCropDragStarted(verticalBoundary, boundaryPosition, fractionToVerticalPixels, (rectF.left + rectF.right) / 2.0f, f);
        } else if (i == 1) {
            this.mCropInteractionListener.onCropDragComplete();
        } else if (i != 2) {
        } else {
            CropInteractionListener cropInteractionListener2 = this.mCropInteractionListener;
            int fractionToVerticalPixels2 = fractionToVerticalPixels(boundaryPosition);
            RectF rectF2 = this.mCrop;
            cropInteractionListener2.onCropDragMoved(verticalBoundary, boundaryPosition, fractionToVerticalPixels2, (rectF2.left + rectF2.right) / 2.0f, f);
        }
    }
}