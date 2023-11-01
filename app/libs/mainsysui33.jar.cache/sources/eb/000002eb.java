package androidx.constraintlayout.widget;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.frameworks.stats.IStats;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;
import android.view.ViewGroup;
import androidx.constraintlayout.core.Metrics;
import androidx.constraintlayout.core.widgets.ConstraintAnchor;
import androidx.constraintlayout.core.widgets.ConstraintWidget;
import androidx.constraintlayout.core.widgets.ConstraintWidgetContainer;
import androidx.constraintlayout.core.widgets.Optimizer;
import androidx.constraintlayout.core.widgets.analyzer.BasicMeasure;
import androidx.recyclerview.widget.RecyclerView;
import com.android.settingslib.widget.ActionBarShadowController;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/* loaded from: mainsysui33.jar:androidx/constraintlayout/widget/ConstraintLayout.class */
public class ConstraintLayout extends ViewGroup {
    private static final boolean DEBUG = false;
    private static final boolean DEBUG_DRAW_CONSTRAINTS = false;
    public static final int DESIGN_INFO_ID = 0;
    private static final boolean OPTIMIZE_HEIGHT_CHANGE = false;
    private static final String TAG = "ConstraintLayout";
    private static final boolean USE_CONSTRAINTS_HELPER = true;
    public static final String VERSION = "ConstraintLayout-2.2.0-alpha03";
    private static SharedValues sSharedValues;
    public SparseArray<View> mChildrenByIds;
    private ArrayList<ConstraintHelper> mConstraintHelpers;
    public ConstraintLayoutStates mConstraintLayoutSpec;
    private ConstraintSet mConstraintSet;
    private int mConstraintSetId;
    private HashMap<String, Integer> mDesignIds;
    public boolean mDirtyHierarchy;
    private int mLastMeasureHeight;
    public int mLastMeasureHeightMode;
    public int mLastMeasureHeightSize;
    private int mLastMeasureWidth;
    public int mLastMeasureWidthMode;
    public int mLastMeasureWidthSize;
    public ConstraintWidgetContainer mLayoutWidget;
    private int mMaxHeight;
    private int mMaxWidth;
    public Measurer mMeasurer;
    private Metrics mMetrics;
    private int mMinHeight;
    private int mMinWidth;
    private ArrayList<ValueModifier> mModifiers;
    private int mOnMeasureHeightMeasureSpec;
    private int mOnMeasureWidthMeasureSpec;
    private int mOptimizationLevel;
    private SparseArray<ConstraintWidget> mTempMapIdToWidget;

    /* renamed from: androidx.constraintlayout.widget.ConstraintLayout$1  reason: invalid class name */
    /* loaded from: mainsysui33.jar:androidx/constraintlayout/widget/ConstraintLayout$1.class */
    public static /* synthetic */ class AnonymousClass1 {
        public static final /* synthetic */ int[] $SwitchMap$androidx$constraintlayout$core$widgets$ConstraintWidget$DimensionBehaviour;

        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:11:0x0036 -> B:21:0x0014). Please submit an issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:13:0x003a -> B:19:0x001f). Please submit an issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:15:0x003e -> B:25:0x002a). Please submit an issue!!! */
        static {
            int[] iArr = new int[ConstraintWidget.DimensionBehaviour.values().length];
            $SwitchMap$androidx$constraintlayout$core$widgets$ConstraintWidget$DimensionBehaviour = iArr;
            try {
                iArr[ConstraintWidget.DimensionBehaviour.FIXED.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$androidx$constraintlayout$core$widgets$ConstraintWidget$DimensionBehaviour[ConstraintWidget.DimensionBehaviour.WRAP_CONTENT.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$androidx$constraintlayout$core$widgets$ConstraintWidget$DimensionBehaviour[ConstraintWidget.DimensionBehaviour.MATCH_PARENT.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$androidx$constraintlayout$core$widgets$ConstraintWidget$DimensionBehaviour[ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    /* loaded from: mainsysui33.jar:androidx/constraintlayout/widget/ConstraintLayout$LayoutParams.class */
    public static class LayoutParams extends ViewGroup.MarginLayoutParams {
        public int baselineMargin;
        public int baselineToBaseline;
        public int baselineToBottom;
        public int baselineToTop;
        public int bottomToBottom;
        public int bottomToTop;
        public float circleAngle;
        public int circleConstraint;
        public int circleRadius;
        public boolean constrainedHeight;
        public boolean constrainedWidth;
        public String constraintTag;
        public String dimensionRatio;
        public int editorAbsoluteX;
        public int editorAbsoluteY;
        public int endToEnd;
        public int endToStart;
        public int goneBaselineMargin;
        public int goneBottomMargin;
        public int goneEndMargin;
        public int goneLeftMargin;
        public int goneRightMargin;
        public int goneStartMargin;
        public int goneTopMargin;
        public int guideBegin;
        public int guideEnd;
        public float guidePercent;
        public boolean guidelineUseRtl;
        public boolean helped;
        public float horizontalBias;
        public int horizontalChainStyle;
        public float horizontalWeight;
        public int leftToLeft;
        public int leftToRight;
        public int mDimensionRatioSide;
        public float mDimensionRatioValue;
        public boolean mHeightSet;
        public boolean mHorizontalDimensionFixed;
        public boolean mIsGuideline;
        public boolean mIsHelper;
        public boolean mIsInPlaceholder;
        public boolean mIsVirtualGroup;
        public boolean mNeedsBaseline;
        public int mResolveGoneLeftMargin;
        public int mResolveGoneRightMargin;
        public int mResolvedGuideBegin;
        public int mResolvedGuideEnd;
        public float mResolvedGuidePercent;
        public float mResolvedHorizontalBias;
        public int mResolvedLeftToLeft;
        public int mResolvedLeftToRight;
        public int mResolvedRightToLeft;
        public int mResolvedRightToRight;
        public boolean mVerticalDimensionFixed;
        public ConstraintWidget mWidget;
        public boolean mWidthSet;
        public int matchConstraintDefaultHeight;
        public int matchConstraintDefaultWidth;
        public int matchConstraintMaxHeight;
        public int matchConstraintMaxWidth;
        public int matchConstraintMinHeight;
        public int matchConstraintMinWidth;
        public float matchConstraintPercentHeight;
        public float matchConstraintPercentWidth;
        public int orientation;
        public int rightToLeft;
        public int rightToRight;
        public int startToEnd;
        public int startToStart;
        public int topToBottom;
        public int topToTop;
        public float verticalBias;
        public int verticalChainStyle;
        public float verticalWeight;
        public int wrapBehaviorInParent;

        /* loaded from: mainsysui33.jar:androidx/constraintlayout/widget/ConstraintLayout$LayoutParams$Table.class */
        public static class Table {
            public static final SparseIntArray sMap;

            static {
                SparseIntArray sparseIntArray = new SparseIntArray();
                sMap = sparseIntArray;
                sparseIntArray.append(R$styleable.ConstraintLayout_Layout_layout_constraintWidth, 64);
                sparseIntArray.append(R$styleable.ConstraintLayout_Layout_layout_constraintHeight, 65);
                sparseIntArray.append(R$styleable.ConstraintLayout_Layout_layout_constraintLeft_toLeftOf, 8);
                sparseIntArray.append(R$styleable.ConstraintLayout_Layout_layout_constraintLeft_toRightOf, 9);
                sparseIntArray.append(R$styleable.ConstraintLayout_Layout_layout_constraintRight_toLeftOf, 10);
                sparseIntArray.append(R$styleable.ConstraintLayout_Layout_layout_constraintRight_toRightOf, 11);
                sparseIntArray.append(R$styleable.ConstraintLayout_Layout_layout_constraintTop_toTopOf, 12);
                sparseIntArray.append(R$styleable.ConstraintLayout_Layout_layout_constraintTop_toBottomOf, 13);
                sparseIntArray.append(R$styleable.ConstraintLayout_Layout_layout_constraintBottom_toTopOf, 14);
                sparseIntArray.append(R$styleable.ConstraintLayout_Layout_layout_constraintBottom_toBottomOf, 15);
                sparseIntArray.append(R$styleable.ConstraintLayout_Layout_layout_constraintBaseline_toBaselineOf, 16);
                sparseIntArray.append(R$styleable.ConstraintLayout_Layout_layout_constraintBaseline_toTopOf, 52);
                sparseIntArray.append(R$styleable.ConstraintLayout_Layout_layout_constraintBaseline_toBottomOf, 53);
                sparseIntArray.append(R$styleable.ConstraintLayout_Layout_layout_constraintCircle, 2);
                sparseIntArray.append(R$styleable.ConstraintLayout_Layout_layout_constraintCircleRadius, 3);
                sparseIntArray.append(R$styleable.ConstraintLayout_Layout_layout_constraintCircleAngle, 4);
                sparseIntArray.append(R$styleable.ConstraintLayout_Layout_layout_editor_absoluteX, 49);
                sparseIntArray.append(R$styleable.ConstraintLayout_Layout_layout_editor_absoluteY, 50);
                sparseIntArray.append(R$styleable.ConstraintLayout_Layout_layout_constraintGuide_begin, 5);
                sparseIntArray.append(R$styleable.ConstraintLayout_Layout_layout_constraintGuide_end, 6);
                sparseIntArray.append(R$styleable.ConstraintLayout_Layout_layout_constraintGuide_percent, 7);
                sparseIntArray.append(R$styleable.ConstraintLayout_Layout_guidelineUseRtl, 67);
                sparseIntArray.append(R$styleable.ConstraintLayout_Layout_android_orientation, 1);
                sparseIntArray.append(R$styleable.ConstraintLayout_Layout_layout_constraintStart_toEndOf, 17);
                sparseIntArray.append(R$styleable.ConstraintLayout_Layout_layout_constraintStart_toStartOf, 18);
                sparseIntArray.append(R$styleable.ConstraintLayout_Layout_layout_constraintEnd_toStartOf, 19);
                sparseIntArray.append(R$styleable.ConstraintLayout_Layout_layout_constraintEnd_toEndOf, 20);
                sparseIntArray.append(R$styleable.ConstraintLayout_Layout_layout_goneMarginLeft, 21);
                sparseIntArray.append(R$styleable.ConstraintLayout_Layout_layout_goneMarginTop, 22);
                sparseIntArray.append(R$styleable.ConstraintLayout_Layout_layout_goneMarginRight, 23);
                sparseIntArray.append(R$styleable.ConstraintLayout_Layout_layout_goneMarginBottom, 24);
                sparseIntArray.append(R$styleable.ConstraintLayout_Layout_layout_goneMarginStart, 25);
                sparseIntArray.append(R$styleable.ConstraintLayout_Layout_layout_goneMarginEnd, 26);
                sparseIntArray.append(R$styleable.ConstraintLayout_Layout_layout_goneMarginBaseline, 55);
                sparseIntArray.append(R$styleable.ConstraintLayout_Layout_layout_marginBaseline, 54);
                sparseIntArray.append(R$styleable.ConstraintLayout_Layout_layout_constraintHorizontal_bias, 29);
                sparseIntArray.append(R$styleable.ConstraintLayout_Layout_layout_constraintVertical_bias, 30);
                sparseIntArray.append(R$styleable.ConstraintLayout_Layout_layout_constraintDimensionRatio, 44);
                sparseIntArray.append(R$styleable.ConstraintLayout_Layout_layout_constraintHorizontal_weight, 45);
                sparseIntArray.append(R$styleable.ConstraintLayout_Layout_layout_constraintVertical_weight, 46);
                sparseIntArray.append(R$styleable.ConstraintLayout_Layout_layout_constraintHorizontal_chainStyle, 47);
                sparseIntArray.append(R$styleable.ConstraintLayout_Layout_layout_constraintVertical_chainStyle, 48);
                sparseIntArray.append(R$styleable.ConstraintLayout_Layout_layout_constrainedWidth, 27);
                sparseIntArray.append(R$styleable.ConstraintLayout_Layout_layout_constrainedHeight, 28);
                sparseIntArray.append(R$styleable.ConstraintLayout_Layout_layout_constraintWidth_default, 31);
                sparseIntArray.append(R$styleable.ConstraintLayout_Layout_layout_constraintHeight_default, 32);
                sparseIntArray.append(R$styleable.ConstraintLayout_Layout_layout_constraintWidth_min, 33);
                sparseIntArray.append(R$styleable.ConstraintLayout_Layout_layout_constraintWidth_max, 34);
                sparseIntArray.append(R$styleable.ConstraintLayout_Layout_layout_constraintWidth_percent, 35);
                sparseIntArray.append(R$styleable.ConstraintLayout_Layout_layout_constraintHeight_min, 36);
                sparseIntArray.append(R$styleable.ConstraintLayout_Layout_layout_constraintHeight_max, 37);
                sparseIntArray.append(R$styleable.ConstraintLayout_Layout_layout_constraintHeight_percent, 38);
                sparseIntArray.append(R$styleable.ConstraintLayout_Layout_layout_constraintLeft_creator, 39);
                sparseIntArray.append(R$styleable.ConstraintLayout_Layout_layout_constraintTop_creator, 40);
                sparseIntArray.append(R$styleable.ConstraintLayout_Layout_layout_constraintRight_creator, 41);
                sparseIntArray.append(R$styleable.ConstraintLayout_Layout_layout_constraintBottom_creator, 42);
                sparseIntArray.append(R$styleable.ConstraintLayout_Layout_layout_constraintBaseline_creator, 43);
                sparseIntArray.append(R$styleable.ConstraintLayout_Layout_layout_constraintTag, 51);
                sparseIntArray.append(R$styleable.ConstraintLayout_Layout_layout_wrapBehaviorInParent, 66);
            }
        }

        public LayoutParams(int i, int i2) {
            super(i, i2);
            this.guideBegin = -1;
            this.guideEnd = -1;
            this.guidePercent = -1.0f;
            this.guidelineUseRtl = true;
            this.leftToLeft = -1;
            this.leftToRight = -1;
            this.rightToLeft = -1;
            this.rightToRight = -1;
            this.topToTop = -1;
            this.topToBottom = -1;
            this.bottomToTop = -1;
            this.bottomToBottom = -1;
            this.baselineToBaseline = -1;
            this.baselineToTop = -1;
            this.baselineToBottom = -1;
            this.circleConstraint = -1;
            this.circleRadius = 0;
            this.circleAngle = ActionBarShadowController.ELEVATION_LOW;
            this.startToEnd = -1;
            this.startToStart = -1;
            this.endToStart = -1;
            this.endToEnd = -1;
            this.goneLeftMargin = Integer.MIN_VALUE;
            this.goneTopMargin = Integer.MIN_VALUE;
            this.goneRightMargin = Integer.MIN_VALUE;
            this.goneBottomMargin = Integer.MIN_VALUE;
            this.goneStartMargin = Integer.MIN_VALUE;
            this.goneEndMargin = Integer.MIN_VALUE;
            this.goneBaselineMargin = Integer.MIN_VALUE;
            this.baselineMargin = 0;
            this.mWidthSet = true;
            this.mHeightSet = true;
            this.horizontalBias = 0.5f;
            this.verticalBias = 0.5f;
            this.dimensionRatio = null;
            this.mDimensionRatioValue = ActionBarShadowController.ELEVATION_LOW;
            this.mDimensionRatioSide = 1;
            this.horizontalWeight = -1.0f;
            this.verticalWeight = -1.0f;
            this.horizontalChainStyle = 0;
            this.verticalChainStyle = 0;
            this.matchConstraintDefaultWidth = 0;
            this.matchConstraintDefaultHeight = 0;
            this.matchConstraintMinWidth = 0;
            this.matchConstraintMinHeight = 0;
            this.matchConstraintMaxWidth = 0;
            this.matchConstraintMaxHeight = 0;
            this.matchConstraintPercentWidth = 1.0f;
            this.matchConstraintPercentHeight = 1.0f;
            this.editorAbsoluteX = -1;
            this.editorAbsoluteY = -1;
            this.orientation = -1;
            this.constrainedWidth = false;
            this.constrainedHeight = false;
            this.constraintTag = null;
            this.wrapBehaviorInParent = 0;
            this.mHorizontalDimensionFixed = true;
            this.mVerticalDimensionFixed = true;
            this.mNeedsBaseline = false;
            this.mIsGuideline = false;
            this.mIsHelper = false;
            this.mIsInPlaceholder = false;
            this.mIsVirtualGroup = false;
            this.mResolvedLeftToLeft = -1;
            this.mResolvedLeftToRight = -1;
            this.mResolvedRightToLeft = -1;
            this.mResolvedRightToRight = -1;
            this.mResolveGoneLeftMargin = Integer.MIN_VALUE;
            this.mResolveGoneRightMargin = Integer.MIN_VALUE;
            this.mResolvedHorizontalBias = 0.5f;
            this.mWidget = new ConstraintWidget();
            this.helped = false;
        }

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            this.guideBegin = -1;
            this.guideEnd = -1;
            this.guidePercent = -1.0f;
            this.guidelineUseRtl = true;
            this.leftToLeft = -1;
            this.leftToRight = -1;
            this.rightToLeft = -1;
            this.rightToRight = -1;
            this.topToTop = -1;
            this.topToBottom = -1;
            this.bottomToTop = -1;
            this.bottomToBottom = -1;
            this.baselineToBaseline = -1;
            this.baselineToTop = -1;
            this.baselineToBottom = -1;
            this.circleConstraint = -1;
            this.circleRadius = 0;
            this.circleAngle = ActionBarShadowController.ELEVATION_LOW;
            this.startToEnd = -1;
            this.startToStart = -1;
            this.endToStart = -1;
            this.endToEnd = -1;
            this.goneLeftMargin = Integer.MIN_VALUE;
            this.goneTopMargin = Integer.MIN_VALUE;
            this.goneRightMargin = Integer.MIN_VALUE;
            this.goneBottomMargin = Integer.MIN_VALUE;
            this.goneStartMargin = Integer.MIN_VALUE;
            this.goneEndMargin = Integer.MIN_VALUE;
            this.goneBaselineMargin = Integer.MIN_VALUE;
            this.baselineMargin = 0;
            this.mWidthSet = true;
            this.mHeightSet = true;
            this.horizontalBias = 0.5f;
            this.verticalBias = 0.5f;
            this.dimensionRatio = null;
            this.mDimensionRatioValue = ActionBarShadowController.ELEVATION_LOW;
            this.mDimensionRatioSide = 1;
            this.horizontalWeight = -1.0f;
            this.verticalWeight = -1.0f;
            this.horizontalChainStyle = 0;
            this.verticalChainStyle = 0;
            this.matchConstraintDefaultWidth = 0;
            this.matchConstraintDefaultHeight = 0;
            this.matchConstraintMinWidth = 0;
            this.matchConstraintMinHeight = 0;
            this.matchConstraintMaxWidth = 0;
            this.matchConstraintMaxHeight = 0;
            this.matchConstraintPercentWidth = 1.0f;
            this.matchConstraintPercentHeight = 1.0f;
            this.editorAbsoluteX = -1;
            this.editorAbsoluteY = -1;
            this.orientation = -1;
            this.constrainedWidth = false;
            this.constrainedHeight = false;
            this.constraintTag = null;
            this.wrapBehaviorInParent = 0;
            this.mHorizontalDimensionFixed = true;
            this.mVerticalDimensionFixed = true;
            this.mNeedsBaseline = false;
            this.mIsGuideline = false;
            this.mIsHelper = false;
            this.mIsInPlaceholder = false;
            this.mIsVirtualGroup = false;
            this.mResolvedLeftToLeft = -1;
            this.mResolvedLeftToRight = -1;
            this.mResolvedRightToLeft = -1;
            this.mResolvedRightToRight = -1;
            this.mResolveGoneLeftMargin = Integer.MIN_VALUE;
            this.mResolveGoneRightMargin = Integer.MIN_VALUE;
            this.mResolvedHorizontalBias = 0.5f;
            this.mWidget = new ConstraintWidget();
            this.helped = false;
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.ConstraintLayout_Layout);
            int indexCount = obtainStyledAttributes.getIndexCount();
            for (int i = 0; i < indexCount; i++) {
                int index = obtainStyledAttributes.getIndex(i);
                int i2 = Table.sMap.get(index);
                switch (i2) {
                    case 1:
                        this.orientation = obtainStyledAttributes.getInt(index, this.orientation);
                        break;
                    case 2:
                        int resourceId = obtainStyledAttributes.getResourceId(index, this.circleConstraint);
                        this.circleConstraint = resourceId;
                        if (resourceId == -1) {
                            this.circleConstraint = obtainStyledAttributes.getInt(index, -1);
                            break;
                        } else {
                            break;
                        }
                    case 3:
                        this.circleRadius = obtainStyledAttributes.getDimensionPixelSize(index, this.circleRadius);
                        break;
                    case 4:
                        float f = obtainStyledAttributes.getFloat(index, this.circleAngle) % 360.0f;
                        this.circleAngle = f;
                        if (f < ActionBarShadowController.ELEVATION_LOW) {
                            this.circleAngle = (360.0f - f) % 360.0f;
                            break;
                        } else {
                            break;
                        }
                    case 5:
                        this.guideBegin = obtainStyledAttributes.getDimensionPixelOffset(index, this.guideBegin);
                        break;
                    case 6:
                        this.guideEnd = obtainStyledAttributes.getDimensionPixelOffset(index, this.guideEnd);
                        break;
                    case 7:
                        this.guidePercent = obtainStyledAttributes.getFloat(index, this.guidePercent);
                        break;
                    case 8:
                        int resourceId2 = obtainStyledAttributes.getResourceId(index, this.leftToLeft);
                        this.leftToLeft = resourceId2;
                        if (resourceId2 == -1) {
                            this.leftToLeft = obtainStyledAttributes.getInt(index, -1);
                            break;
                        } else {
                            break;
                        }
                    case 9:
                        int resourceId3 = obtainStyledAttributes.getResourceId(index, this.leftToRight);
                        this.leftToRight = resourceId3;
                        if (resourceId3 == -1) {
                            this.leftToRight = obtainStyledAttributes.getInt(index, -1);
                            break;
                        } else {
                            break;
                        }
                    case 10:
                        int resourceId4 = obtainStyledAttributes.getResourceId(index, this.rightToLeft);
                        this.rightToLeft = resourceId4;
                        if (resourceId4 == -1) {
                            this.rightToLeft = obtainStyledAttributes.getInt(index, -1);
                            break;
                        } else {
                            break;
                        }
                    case 11:
                        int resourceId5 = obtainStyledAttributes.getResourceId(index, this.rightToRight);
                        this.rightToRight = resourceId5;
                        if (resourceId5 == -1) {
                            this.rightToRight = obtainStyledAttributes.getInt(index, -1);
                            break;
                        } else {
                            break;
                        }
                    case 12:
                        int resourceId6 = obtainStyledAttributes.getResourceId(index, this.topToTop);
                        this.topToTop = resourceId6;
                        if (resourceId6 == -1) {
                            this.topToTop = obtainStyledAttributes.getInt(index, -1);
                            break;
                        } else {
                            break;
                        }
                    case 13:
                        int resourceId7 = obtainStyledAttributes.getResourceId(index, this.topToBottom);
                        this.topToBottom = resourceId7;
                        if (resourceId7 == -1) {
                            this.topToBottom = obtainStyledAttributes.getInt(index, -1);
                            break;
                        } else {
                            break;
                        }
                    case 14:
                        int resourceId8 = obtainStyledAttributes.getResourceId(index, this.bottomToTop);
                        this.bottomToTop = resourceId8;
                        if (resourceId8 == -1) {
                            this.bottomToTop = obtainStyledAttributes.getInt(index, -1);
                            break;
                        } else {
                            break;
                        }
                    case 15:
                        int resourceId9 = obtainStyledAttributes.getResourceId(index, this.bottomToBottom);
                        this.bottomToBottom = resourceId9;
                        if (resourceId9 == -1) {
                            this.bottomToBottom = obtainStyledAttributes.getInt(index, -1);
                            break;
                        } else {
                            break;
                        }
                    case 16:
                        int resourceId10 = obtainStyledAttributes.getResourceId(index, this.baselineToBaseline);
                        this.baselineToBaseline = resourceId10;
                        if (resourceId10 == -1) {
                            this.baselineToBaseline = obtainStyledAttributes.getInt(index, -1);
                            break;
                        } else {
                            break;
                        }
                    case 17:
                        int resourceId11 = obtainStyledAttributes.getResourceId(index, this.startToEnd);
                        this.startToEnd = resourceId11;
                        if (resourceId11 == -1) {
                            this.startToEnd = obtainStyledAttributes.getInt(index, -1);
                            break;
                        } else {
                            break;
                        }
                    case 18:
                        int resourceId12 = obtainStyledAttributes.getResourceId(index, this.startToStart);
                        this.startToStart = resourceId12;
                        if (resourceId12 == -1) {
                            this.startToStart = obtainStyledAttributes.getInt(index, -1);
                            break;
                        } else {
                            break;
                        }
                    case 19:
                        int resourceId13 = obtainStyledAttributes.getResourceId(index, this.endToStart);
                        this.endToStart = resourceId13;
                        if (resourceId13 == -1) {
                            this.endToStart = obtainStyledAttributes.getInt(index, -1);
                            break;
                        } else {
                            break;
                        }
                    case 20:
                        int resourceId14 = obtainStyledAttributes.getResourceId(index, this.endToEnd);
                        this.endToEnd = resourceId14;
                        if (resourceId14 == -1) {
                            this.endToEnd = obtainStyledAttributes.getInt(index, -1);
                            break;
                        } else {
                            break;
                        }
                    case 21:
                        this.goneLeftMargin = obtainStyledAttributes.getDimensionPixelSize(index, this.goneLeftMargin);
                        break;
                    case 22:
                        this.goneTopMargin = obtainStyledAttributes.getDimensionPixelSize(index, this.goneTopMargin);
                        break;
                    case 23:
                        this.goneRightMargin = obtainStyledAttributes.getDimensionPixelSize(index, this.goneRightMargin);
                        break;
                    case 24:
                        this.goneBottomMargin = obtainStyledAttributes.getDimensionPixelSize(index, this.goneBottomMargin);
                        break;
                    case 25:
                        this.goneStartMargin = obtainStyledAttributes.getDimensionPixelSize(index, this.goneStartMargin);
                        break;
                    case 26:
                        this.goneEndMargin = obtainStyledAttributes.getDimensionPixelSize(index, this.goneEndMargin);
                        break;
                    case 27:
                        this.constrainedWidth = obtainStyledAttributes.getBoolean(index, this.constrainedWidth);
                        break;
                    case 28:
                        this.constrainedHeight = obtainStyledAttributes.getBoolean(index, this.constrainedHeight);
                        break;
                    case 29:
                        this.horizontalBias = obtainStyledAttributes.getFloat(index, this.horizontalBias);
                        break;
                    case 30:
                        this.verticalBias = obtainStyledAttributes.getFloat(index, this.verticalBias);
                        break;
                    case 31:
                        int i3 = obtainStyledAttributes.getInt(index, 0);
                        this.matchConstraintDefaultWidth = i3;
                        if (i3 == 1) {
                            Log.e(ConstraintLayout.TAG, "layout_constraintWidth_default=\"wrap\" is deprecated.\nUse layout_width=\"WRAP_CONTENT\" and layout_constrainedWidth=\"true\" instead.");
                            break;
                        } else {
                            break;
                        }
                    case 32:
                        int i4 = obtainStyledAttributes.getInt(index, 0);
                        this.matchConstraintDefaultHeight = i4;
                        if (i4 == 1) {
                            Log.e(ConstraintLayout.TAG, "layout_constraintHeight_default=\"wrap\" is deprecated.\nUse layout_height=\"WRAP_CONTENT\" and layout_constrainedHeight=\"true\" instead.");
                            break;
                        } else {
                            break;
                        }
                    case 33:
                        try {
                            this.matchConstraintMinWidth = obtainStyledAttributes.getDimensionPixelSize(index, this.matchConstraintMinWidth);
                            break;
                        } catch (Exception e) {
                            if (obtainStyledAttributes.getInt(index, this.matchConstraintMinWidth) == -2) {
                                this.matchConstraintMinWidth = -2;
                                break;
                            } else {
                                break;
                            }
                        }
                    case 34:
                        try {
                            this.matchConstraintMaxWidth = obtainStyledAttributes.getDimensionPixelSize(index, this.matchConstraintMaxWidth);
                            break;
                        } catch (Exception e2) {
                            if (obtainStyledAttributes.getInt(index, this.matchConstraintMaxWidth) == -2) {
                                this.matchConstraintMaxWidth = -2;
                                break;
                            } else {
                                break;
                            }
                        }
                    case 35:
                        this.matchConstraintPercentWidth = Math.max((float) ActionBarShadowController.ELEVATION_LOW, obtainStyledAttributes.getFloat(index, this.matchConstraintPercentWidth));
                        this.matchConstraintDefaultWidth = 2;
                        break;
                    case R$styleable.ConstraintLayout_Layout_constraint_referenced_tags /* 36 */:
                        try {
                            this.matchConstraintMinHeight = obtainStyledAttributes.getDimensionPixelSize(index, this.matchConstraintMinHeight);
                            break;
                        } catch (Exception e3) {
                            if (obtainStyledAttributes.getInt(index, this.matchConstraintMinHeight) == -2) {
                                this.matchConstraintMinHeight = -2;
                                break;
                            } else {
                                break;
                            }
                        }
                    case 37:
                        try {
                            this.matchConstraintMaxHeight = obtainStyledAttributes.getDimensionPixelSize(index, this.matchConstraintMaxHeight);
                            break;
                        } catch (Exception e4) {
                            if (obtainStyledAttributes.getInt(index, this.matchConstraintMaxHeight) == -2) {
                                this.matchConstraintMaxHeight = -2;
                                break;
                            } else {
                                break;
                            }
                        }
                    case 38:
                        this.matchConstraintPercentHeight = Math.max((float) ActionBarShadowController.ELEVATION_LOW, obtainStyledAttributes.getFloat(index, this.matchConstraintPercentHeight));
                        this.matchConstraintDefaultHeight = 2;
                        break;
                    default:
                        switch (i2) {
                            case 44:
                                ConstraintSet.parseDimensionRatioString(this, obtainStyledAttributes.getString(index));
                                continue;
                            case 45:
                                this.horizontalWeight = obtainStyledAttributes.getFloat(index, this.horizontalWeight);
                                continue;
                            case 46:
                                this.verticalWeight = obtainStyledAttributes.getFloat(index, this.verticalWeight);
                                continue;
                            case 47:
                                this.horizontalChainStyle = obtainStyledAttributes.getInt(index, 0);
                                continue;
                            case 48:
                                this.verticalChainStyle = obtainStyledAttributes.getInt(index, 0);
                                continue;
                            case 49:
                                this.editorAbsoluteX = obtainStyledAttributes.getDimensionPixelOffset(index, this.editorAbsoluteX);
                                continue;
                            case 50:
                                this.editorAbsoluteY = obtainStyledAttributes.getDimensionPixelOffset(index, this.editorAbsoluteY);
                                continue;
                            case 51:
                                this.constraintTag = obtainStyledAttributes.getString(index);
                                continue;
                            case 52:
                                int resourceId15 = obtainStyledAttributes.getResourceId(index, this.baselineToTop);
                                this.baselineToTop = resourceId15;
                                if (resourceId15 == -1) {
                                    this.baselineToTop = obtainStyledAttributes.getInt(index, -1);
                                    break;
                                } else {
                                    continue;
                                }
                            case 53:
                                int resourceId16 = obtainStyledAttributes.getResourceId(index, this.baselineToBottom);
                                this.baselineToBottom = resourceId16;
                                if (resourceId16 == -1) {
                                    this.baselineToBottom = obtainStyledAttributes.getInt(index, -1);
                                    break;
                                } else {
                                    continue;
                                }
                            case 54:
                                this.baselineMargin = obtainStyledAttributes.getDimensionPixelSize(index, this.baselineMargin);
                                continue;
                            case 55:
                                this.goneBaselineMargin = obtainStyledAttributes.getDimensionPixelSize(index, this.goneBaselineMargin);
                                continue;
                            default:
                                switch (i2) {
                                    case 64:
                                        ConstraintSet.parseDimensionConstraints(this, obtainStyledAttributes, index, 0);
                                        this.mWidthSet = true;
                                        continue;
                                    case 65:
                                        ConstraintSet.parseDimensionConstraints(this, obtainStyledAttributes, index, 1);
                                        this.mHeightSet = true;
                                        continue;
                                    case 66:
                                        this.wrapBehaviorInParent = obtainStyledAttributes.getInt(index, this.wrapBehaviorInParent);
                                        continue;
                                    case 67:
                                        this.guidelineUseRtl = obtainStyledAttributes.getBoolean(index, this.guidelineUseRtl);
                                        continue;
                                        continue;
                                }
                        }
                }
            }
            obtainStyledAttributes.recycle();
            validate();
        }

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
            this.guideBegin = -1;
            this.guideEnd = -1;
            this.guidePercent = -1.0f;
            this.guidelineUseRtl = true;
            this.leftToLeft = -1;
            this.leftToRight = -1;
            this.rightToLeft = -1;
            this.rightToRight = -1;
            this.topToTop = -1;
            this.topToBottom = -1;
            this.bottomToTop = -1;
            this.bottomToBottom = -1;
            this.baselineToBaseline = -1;
            this.baselineToTop = -1;
            this.baselineToBottom = -1;
            this.circleConstraint = -1;
            this.circleRadius = 0;
            this.circleAngle = ActionBarShadowController.ELEVATION_LOW;
            this.startToEnd = -1;
            this.startToStart = -1;
            this.endToStart = -1;
            this.endToEnd = -1;
            this.goneLeftMargin = Integer.MIN_VALUE;
            this.goneTopMargin = Integer.MIN_VALUE;
            this.goneRightMargin = Integer.MIN_VALUE;
            this.goneBottomMargin = Integer.MIN_VALUE;
            this.goneStartMargin = Integer.MIN_VALUE;
            this.goneEndMargin = Integer.MIN_VALUE;
            this.goneBaselineMargin = Integer.MIN_VALUE;
            this.baselineMargin = 0;
            this.mWidthSet = true;
            this.mHeightSet = true;
            this.horizontalBias = 0.5f;
            this.verticalBias = 0.5f;
            this.dimensionRatio = null;
            this.mDimensionRatioValue = ActionBarShadowController.ELEVATION_LOW;
            this.mDimensionRatioSide = 1;
            this.horizontalWeight = -1.0f;
            this.verticalWeight = -1.0f;
            this.horizontalChainStyle = 0;
            this.verticalChainStyle = 0;
            this.matchConstraintDefaultWidth = 0;
            this.matchConstraintDefaultHeight = 0;
            this.matchConstraintMinWidth = 0;
            this.matchConstraintMinHeight = 0;
            this.matchConstraintMaxWidth = 0;
            this.matchConstraintMaxHeight = 0;
            this.matchConstraintPercentWidth = 1.0f;
            this.matchConstraintPercentHeight = 1.0f;
            this.editorAbsoluteX = -1;
            this.editorAbsoluteY = -1;
            this.orientation = -1;
            this.constrainedWidth = false;
            this.constrainedHeight = false;
            this.constraintTag = null;
            this.wrapBehaviorInParent = 0;
            this.mHorizontalDimensionFixed = true;
            this.mVerticalDimensionFixed = true;
            this.mNeedsBaseline = false;
            this.mIsGuideline = false;
            this.mIsHelper = false;
            this.mIsInPlaceholder = false;
            this.mIsVirtualGroup = false;
            this.mResolvedLeftToLeft = -1;
            this.mResolvedLeftToRight = -1;
            this.mResolvedRightToLeft = -1;
            this.mResolvedRightToRight = -1;
            this.mResolveGoneLeftMargin = Integer.MIN_VALUE;
            this.mResolveGoneRightMargin = Integer.MIN_VALUE;
            this.mResolvedHorizontalBias = 0.5f;
            this.mWidget = new ConstraintWidget();
            this.helped = false;
            if (layoutParams instanceof LayoutParams) {
                LayoutParams layoutParams2 = (LayoutParams) layoutParams;
                this.guideBegin = layoutParams2.guideBegin;
                this.guideEnd = layoutParams2.guideEnd;
                this.guidePercent = layoutParams2.guidePercent;
                this.guidelineUseRtl = layoutParams2.guidelineUseRtl;
                this.leftToLeft = layoutParams2.leftToLeft;
                this.leftToRight = layoutParams2.leftToRight;
                this.rightToLeft = layoutParams2.rightToLeft;
                this.rightToRight = layoutParams2.rightToRight;
                this.topToTop = layoutParams2.topToTop;
                this.topToBottom = layoutParams2.topToBottom;
                this.bottomToTop = layoutParams2.bottomToTop;
                this.bottomToBottom = layoutParams2.bottomToBottom;
                this.baselineToBaseline = layoutParams2.baselineToBaseline;
                this.baselineToTop = layoutParams2.baselineToTop;
                this.baselineToBottom = layoutParams2.baselineToBottom;
                this.circleConstraint = layoutParams2.circleConstraint;
                this.circleRadius = layoutParams2.circleRadius;
                this.circleAngle = layoutParams2.circleAngle;
                this.startToEnd = layoutParams2.startToEnd;
                this.startToStart = layoutParams2.startToStart;
                this.endToStart = layoutParams2.endToStart;
                this.endToEnd = layoutParams2.endToEnd;
                this.goneLeftMargin = layoutParams2.goneLeftMargin;
                this.goneTopMargin = layoutParams2.goneTopMargin;
                this.goneRightMargin = layoutParams2.goneRightMargin;
                this.goneBottomMargin = layoutParams2.goneBottomMargin;
                this.goneStartMargin = layoutParams2.goneStartMargin;
                this.goneEndMargin = layoutParams2.goneEndMargin;
                this.goneBaselineMargin = layoutParams2.goneBaselineMargin;
                this.baselineMargin = layoutParams2.baselineMargin;
                this.horizontalBias = layoutParams2.horizontalBias;
                this.verticalBias = layoutParams2.verticalBias;
                this.dimensionRatio = layoutParams2.dimensionRatio;
                this.mDimensionRatioValue = layoutParams2.mDimensionRatioValue;
                this.mDimensionRatioSide = layoutParams2.mDimensionRatioSide;
                this.horizontalWeight = layoutParams2.horizontalWeight;
                this.verticalWeight = layoutParams2.verticalWeight;
                this.horizontalChainStyle = layoutParams2.horizontalChainStyle;
                this.verticalChainStyle = layoutParams2.verticalChainStyle;
                this.constrainedWidth = layoutParams2.constrainedWidth;
                this.constrainedHeight = layoutParams2.constrainedHeight;
                this.matchConstraintDefaultWidth = layoutParams2.matchConstraintDefaultWidth;
                this.matchConstraintDefaultHeight = layoutParams2.matchConstraintDefaultHeight;
                this.matchConstraintMinWidth = layoutParams2.matchConstraintMinWidth;
                this.matchConstraintMaxWidth = layoutParams2.matchConstraintMaxWidth;
                this.matchConstraintMinHeight = layoutParams2.matchConstraintMinHeight;
                this.matchConstraintMaxHeight = layoutParams2.matchConstraintMaxHeight;
                this.matchConstraintPercentWidth = layoutParams2.matchConstraintPercentWidth;
                this.matchConstraintPercentHeight = layoutParams2.matchConstraintPercentHeight;
                this.editorAbsoluteX = layoutParams2.editorAbsoluteX;
                this.editorAbsoluteY = layoutParams2.editorAbsoluteY;
                this.orientation = layoutParams2.orientation;
                this.mHorizontalDimensionFixed = layoutParams2.mHorizontalDimensionFixed;
                this.mVerticalDimensionFixed = layoutParams2.mVerticalDimensionFixed;
                this.mNeedsBaseline = layoutParams2.mNeedsBaseline;
                this.mIsGuideline = layoutParams2.mIsGuideline;
                this.mResolvedLeftToLeft = layoutParams2.mResolvedLeftToLeft;
                this.mResolvedLeftToRight = layoutParams2.mResolvedLeftToRight;
                this.mResolvedRightToLeft = layoutParams2.mResolvedRightToLeft;
                this.mResolvedRightToRight = layoutParams2.mResolvedRightToRight;
                this.mResolveGoneLeftMargin = layoutParams2.mResolveGoneLeftMargin;
                this.mResolveGoneRightMargin = layoutParams2.mResolveGoneRightMargin;
                this.mResolvedHorizontalBias = layoutParams2.mResolvedHorizontalBias;
                this.constraintTag = layoutParams2.constraintTag;
                this.wrapBehaviorInParent = layoutParams2.wrapBehaviorInParent;
                this.mWidget = layoutParams2.mWidget;
                this.mWidthSet = layoutParams2.mWidthSet;
                this.mHeightSet = layoutParams2.mHeightSet;
            }
        }

        public String getConstraintTag() {
            return this.constraintTag;
        }

        public ConstraintWidget getConstraintWidget() {
            return this.mWidget;
        }

        /* JADX WARN: Removed duplicated region for block: B:17:0x00b3  */
        /* JADX WARN: Removed duplicated region for block: B:20:0x00c7  */
        /* JADX WARN: Removed duplicated region for block: B:23:0x00dc  */
        /* JADX WARN: Removed duplicated region for block: B:26:0x00ef  */
        /* JADX WARN: Removed duplicated region for block: B:29:0x00f9  */
        /* JADX WARN: Removed duplicated region for block: B:38:0x011f  */
        /* JADX WARN: Removed duplicated region for block: B:39:0x0134  */
        @Override // android.view.ViewGroup.MarginLayoutParams, android.view.ViewGroup.LayoutParams
        @TargetApi(17)
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public void resolveLayoutDirection(int i) {
            boolean z;
            int i2;
            int i3;
            int i4;
            int i5;
            int i6 = ((ViewGroup.MarginLayoutParams) this).leftMargin;
            int i7 = ((ViewGroup.MarginLayoutParams) this).rightMargin;
            super.resolveLayoutDirection(i);
            boolean z2 = 1 == getLayoutDirection();
            this.mResolvedRightToLeft = -1;
            this.mResolvedRightToRight = -1;
            this.mResolvedLeftToLeft = -1;
            this.mResolvedLeftToRight = -1;
            this.mResolveGoneLeftMargin = this.goneLeftMargin;
            this.mResolveGoneRightMargin = this.goneRightMargin;
            float f = this.horizontalBias;
            this.mResolvedHorizontalBias = f;
            int i8 = this.guideBegin;
            this.mResolvedGuideBegin = i8;
            int i9 = this.guideEnd;
            this.mResolvedGuideEnd = i9;
            float f2 = this.guidePercent;
            this.mResolvedGuidePercent = f2;
            if (z2) {
                int i10 = this.startToEnd;
                if (i10 != -1) {
                    this.mResolvedRightToLeft = i10;
                } else {
                    int i11 = this.startToStart;
                    z = false;
                    if (i11 != -1) {
                        this.mResolvedRightToRight = i11;
                    }
                    i2 = this.endToStart;
                    if (i2 != -1) {
                        this.mResolvedLeftToRight = i2;
                        z = true;
                    }
                    i3 = this.endToEnd;
                    if (i3 != -1) {
                        this.mResolvedLeftToLeft = i3;
                        z = true;
                    }
                    i4 = this.goneStartMargin;
                    if (i4 != Integer.MIN_VALUE) {
                        this.mResolveGoneRightMargin = i4;
                    }
                    i5 = this.goneEndMargin;
                    if (i5 != Integer.MIN_VALUE) {
                        this.mResolveGoneLeftMargin = i5;
                    }
                    if (z) {
                        this.mResolvedHorizontalBias = 1.0f - f;
                    }
                    if (this.mIsGuideline && this.orientation == 1 && this.guidelineUseRtl) {
                        if (f2 == -1.0f) {
                            this.mResolvedGuidePercent = 1.0f - f2;
                            this.mResolvedGuideBegin = -1;
                            this.mResolvedGuideEnd = -1;
                        } else if (i8 != -1) {
                            this.mResolvedGuideEnd = i8;
                            this.mResolvedGuideBegin = -1;
                            this.mResolvedGuidePercent = -1.0f;
                        } else if (i9 != -1) {
                            this.mResolvedGuideBegin = i9;
                            this.mResolvedGuideEnd = -1;
                            this.mResolvedGuidePercent = -1.0f;
                        }
                    }
                }
                z = true;
                i2 = this.endToStart;
                if (i2 != -1) {
                }
                i3 = this.endToEnd;
                if (i3 != -1) {
                }
                i4 = this.goneStartMargin;
                if (i4 != Integer.MIN_VALUE) {
                }
                i5 = this.goneEndMargin;
                if (i5 != Integer.MIN_VALUE) {
                }
                if (z) {
                }
                if (this.mIsGuideline) {
                    if (f2 == -1.0f) {
                    }
                }
            } else {
                int i12 = this.startToEnd;
                if (i12 != -1) {
                    this.mResolvedLeftToRight = i12;
                }
                int i13 = this.startToStart;
                if (i13 != -1) {
                    this.mResolvedLeftToLeft = i13;
                }
                int i14 = this.endToStart;
                if (i14 != -1) {
                    this.mResolvedRightToLeft = i14;
                }
                int i15 = this.endToEnd;
                if (i15 != -1) {
                    this.mResolvedRightToRight = i15;
                }
                int i16 = this.goneStartMargin;
                if (i16 != Integer.MIN_VALUE) {
                    this.mResolveGoneLeftMargin = i16;
                }
                int i17 = this.goneEndMargin;
                if (i17 != Integer.MIN_VALUE) {
                    this.mResolveGoneRightMargin = i17;
                }
            }
            if (this.endToStart == -1 && this.endToEnd == -1 && this.startToStart == -1 && this.startToEnd == -1) {
                int i18 = this.rightToLeft;
                if (i18 != -1) {
                    this.mResolvedRightToLeft = i18;
                    if (((ViewGroup.MarginLayoutParams) this).rightMargin <= 0 && i7 > 0) {
                        ((ViewGroup.MarginLayoutParams) this).rightMargin = i7;
                    }
                } else {
                    int i19 = this.rightToRight;
                    if (i19 != -1) {
                        this.mResolvedRightToRight = i19;
                        if (((ViewGroup.MarginLayoutParams) this).rightMargin <= 0 && i7 > 0) {
                            ((ViewGroup.MarginLayoutParams) this).rightMargin = i7;
                        }
                    }
                }
                int i20 = this.leftToLeft;
                if (i20 != -1) {
                    this.mResolvedLeftToLeft = i20;
                    if (((ViewGroup.MarginLayoutParams) this).leftMargin > 0 || i6 <= 0) {
                        return;
                    }
                    ((ViewGroup.MarginLayoutParams) this).leftMargin = i6;
                    return;
                }
                int i21 = this.leftToRight;
                if (i21 != -1) {
                    this.mResolvedLeftToRight = i21;
                    if (((ViewGroup.MarginLayoutParams) this).leftMargin > 0 || i6 <= 0) {
                        return;
                    }
                    ((ViewGroup.MarginLayoutParams) this).leftMargin = i6;
                }
            }
        }

        public void validate() {
            this.mIsGuideline = false;
            this.mHorizontalDimensionFixed = true;
            this.mVerticalDimensionFixed = true;
            int i = ((ViewGroup.MarginLayoutParams) this).width;
            if (i == -2 && this.constrainedWidth) {
                this.mHorizontalDimensionFixed = false;
                if (this.matchConstraintDefaultWidth == 0) {
                    this.matchConstraintDefaultWidth = 1;
                }
            }
            int i2 = ((ViewGroup.MarginLayoutParams) this).height;
            if (i2 == -2 && this.constrainedHeight) {
                this.mVerticalDimensionFixed = false;
                if (this.matchConstraintDefaultHeight == 0) {
                    this.matchConstraintDefaultHeight = 1;
                }
            }
            if (i == 0 || i == -1) {
                this.mHorizontalDimensionFixed = false;
                if (i == 0 && this.matchConstraintDefaultWidth == 1) {
                    ((ViewGroup.MarginLayoutParams) this).width = -2;
                    this.constrainedWidth = true;
                }
            }
            if (i2 == 0 || i2 == -1) {
                this.mVerticalDimensionFixed = false;
                if (i2 == 0 && this.matchConstraintDefaultHeight == 1) {
                    ((ViewGroup.MarginLayoutParams) this).height = -2;
                    this.constrainedHeight = true;
                }
            }
            if (this.guidePercent == -1.0f && this.guideBegin == -1 && this.guideEnd == -1) {
                return;
            }
            this.mIsGuideline = true;
            this.mHorizontalDimensionFixed = true;
            this.mVerticalDimensionFixed = true;
            if (!(this.mWidget instanceof androidx.constraintlayout.core.widgets.Guideline)) {
                this.mWidget = new androidx.constraintlayout.core.widgets.Guideline();
            }
            ((androidx.constraintlayout.core.widgets.Guideline) this.mWidget).setOrientation(this.orientation);
        }
    }

    /* loaded from: mainsysui33.jar:androidx/constraintlayout/widget/ConstraintLayout$Measurer.class */
    public class Measurer implements BasicMeasure.Measurer {
        public ConstraintLayout mLayout;
        public int mLayoutHeightSpec;
        public int mLayoutWidthSpec;
        public int mPaddingBottom;
        public int mPaddingHeight;
        public int mPaddingTop;
        public int mPaddingWidth;

        public Measurer(ConstraintLayout constraintLayout) {
            this.mLayout = constraintLayout;
        }

        public void captureLayoutInfo(int i, int i2, int i3, int i4, int i5, int i6) {
            this.mPaddingTop = i3;
            this.mPaddingBottom = i4;
            this.mPaddingWidth = i5;
            this.mPaddingHeight = i6;
            this.mLayoutWidthSpec = i;
            this.mLayoutHeightSpec = i2;
        }

        @Override // androidx.constraintlayout.core.widgets.analyzer.BasicMeasure.Measurer
        public final void didMeasures() {
            int childCount = this.mLayout.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childAt = this.mLayout.getChildAt(i);
                if (childAt instanceof Placeholder) {
                    ((Placeholder) childAt).updatePostMeasure(this.mLayout);
                }
            }
            int size = this.mLayout.mConstraintHelpers.size();
            if (size > 0) {
                for (int i2 = 0; i2 < size; i2++) {
                    ((ConstraintHelper) this.mLayout.mConstraintHelpers.get(i2)).updatePostMeasure(this.mLayout);
                }
            }
        }

        public final boolean isSimilarSpec(int i, int i2, int i3) {
            boolean z = true;
            if (i == i2) {
                return true;
            }
            int mode = View.MeasureSpec.getMode(i);
            int mode2 = View.MeasureSpec.getMode(i2);
            int size = View.MeasureSpec.getSize(i2);
            if (mode2 != 1073741824 || ((mode != Integer.MIN_VALUE && mode != 0) || i3 != size)) {
                z = false;
            }
            return z;
        }

        /* JADX WARN: Code restructure failed: missing block: B:210:0x04fb, code lost:
            if (r0 != r11) goto L155;
         */
        /* JADX WARN: Code restructure failed: missing block: B:30:0x00c7, code lost:
            if (r0 == androidx.constraintlayout.core.widgets.analyzer.BasicMeasure.Measure.USE_GIVEN_DIMENSIONS) goto L210;
         */
        /* JADX WARN: Code restructure failed: missing block: B:72:0x01b4, code lost:
            if (r0 == androidx.constraintlayout.core.widgets.analyzer.BasicMeasure.Measure.USE_GIVEN_DIMENSIONS) goto L178;
         */
        @Override // androidx.constraintlayout.core.widgets.analyzer.BasicMeasure.Measurer
        @SuppressLint({"WrongCall"})
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final void measure(ConstraintWidget constraintWidget, BasicMeasure.Measure measure) {
            int makeMeasureSpec;
            int makeMeasureSpec2;
            int measuredWidth;
            int measuredHeight;
            int baseline;
            if (constraintWidget == null) {
                return;
            }
            if (constraintWidget.getVisibility() == 8 && !constraintWidget.isInPlaceholder()) {
                measure.measuredWidth = 0;
                measure.measuredHeight = 0;
                measure.measuredBaseline = 0;
            } else if (constraintWidget.getParent() == null) {
            } else {
                ConstraintLayout.access$000(ConstraintLayout.this);
                ConstraintWidget.DimensionBehaviour dimensionBehaviour = measure.horizontalBehavior;
                ConstraintWidget.DimensionBehaviour dimensionBehaviour2 = measure.verticalBehavior;
                int i = measure.horizontalDimension;
                int i2 = measure.verticalDimension;
                int i3 = this.mPaddingTop + this.mPaddingBottom;
                int i4 = this.mPaddingWidth;
                View view = (View) constraintWidget.getCompanionWidget();
                int[] iArr = AnonymousClass1.$SwitchMap$androidx$constraintlayout$core$widgets$ConstraintWidget$DimensionBehaviour;
                int i5 = iArr[dimensionBehaviour.ordinal()];
                if (i5 == 1) {
                    makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(i, 1073741824);
                } else if (i5 == 2) {
                    makeMeasureSpec = ViewGroup.getChildMeasureSpec(this.mLayoutWidthSpec, i4, -2);
                } else if (i5 == 3) {
                    makeMeasureSpec = ViewGroup.getChildMeasureSpec(this.mLayoutWidthSpec, i4 + constraintWidget.getHorizontalMargin(), -1);
                } else if (i5 != 4) {
                    makeMeasureSpec = 0;
                } else {
                    int childMeasureSpec = ViewGroup.getChildMeasureSpec(this.mLayoutWidthSpec, i4, -2);
                    boolean z = constraintWidget.mMatchConstraintDefaultWidth == 1;
                    int i6 = measure.measureStrategy;
                    if (i6 != BasicMeasure.Measure.TRY_GIVEN_DIMENSIONS) {
                        makeMeasureSpec = childMeasureSpec;
                    }
                    makeMeasureSpec = childMeasureSpec;
                    if (measure.measureStrategy == BasicMeasure.Measure.USE_GIVEN_DIMENSIONS || !z || (z && (view.getMeasuredHeight() == constraintWidget.getHeight())) || (view instanceof Placeholder) || constraintWidget.isResolvedHorizontally()) {
                        makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(constraintWidget.getWidth(), 1073741824);
                    }
                }
                int i7 = iArr[dimensionBehaviour2.ordinal()];
                if (i7 == 1) {
                    makeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(i2, 1073741824);
                } else if (i7 == 2) {
                    makeMeasureSpec2 = ViewGroup.getChildMeasureSpec(this.mLayoutHeightSpec, i3, -2);
                } else if (i7 == 3) {
                    makeMeasureSpec2 = ViewGroup.getChildMeasureSpec(this.mLayoutHeightSpec, i3 + constraintWidget.getVerticalMargin(), -1);
                } else if (i7 != 4) {
                    makeMeasureSpec2 = 0;
                } else {
                    int childMeasureSpec2 = ViewGroup.getChildMeasureSpec(this.mLayoutHeightSpec, i3, -2);
                    boolean z2 = constraintWidget.mMatchConstraintDefaultHeight == 1;
                    int i8 = measure.measureStrategy;
                    if (i8 != BasicMeasure.Measure.TRY_GIVEN_DIMENSIONS) {
                        makeMeasureSpec2 = childMeasureSpec2;
                    }
                    makeMeasureSpec2 = childMeasureSpec2;
                    if (measure.measureStrategy == BasicMeasure.Measure.USE_GIVEN_DIMENSIONS || !z2 || (z2 && (view.getMeasuredWidth() == constraintWidget.getWidth())) || (view instanceof Placeholder) || constraintWidget.isResolvedVertically()) {
                        makeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(constraintWidget.getHeight(), 1073741824);
                    }
                }
                ConstraintWidgetContainer constraintWidgetContainer = (ConstraintWidgetContainer) constraintWidget.getParent();
                if (constraintWidgetContainer != null && Optimizer.enabled(ConstraintLayout.this.mOptimizationLevel, RecyclerView.ViewHolder.FLAG_TMP_DETACHED) && view.getMeasuredWidth() == constraintWidget.getWidth() && view.getMeasuredWidth() < constraintWidgetContainer.getWidth() && view.getMeasuredHeight() == constraintWidget.getHeight() && view.getMeasuredHeight() < constraintWidgetContainer.getHeight() && view.getBaseline() == constraintWidget.getBaselineDistance() && !constraintWidget.isMeasureRequested()) {
                    if (isSimilarSpec(constraintWidget.getLastHorizontalMeasureSpec(), makeMeasureSpec, constraintWidget.getWidth()) && isSimilarSpec(constraintWidget.getLastVerticalMeasureSpec(), makeMeasureSpec2, constraintWidget.getHeight())) {
                        measure.measuredWidth = constraintWidget.getWidth();
                        measure.measuredHeight = constraintWidget.getHeight();
                        measure.measuredBaseline = constraintWidget.getBaselineDistance();
                        return;
                    }
                }
                ConstraintWidget.DimensionBehaviour dimensionBehaviour3 = ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT;
                boolean z3 = dimensionBehaviour == dimensionBehaviour3;
                boolean z4 = dimensionBehaviour2 == dimensionBehaviour3;
                ConstraintWidget.DimensionBehaviour dimensionBehaviour4 = ConstraintWidget.DimensionBehaviour.MATCH_PARENT;
                boolean z5 = dimensionBehaviour2 == dimensionBehaviour4 || dimensionBehaviour2 == ConstraintWidget.DimensionBehaviour.FIXED;
                boolean z6 = dimensionBehaviour == dimensionBehaviour4 || dimensionBehaviour == ConstraintWidget.DimensionBehaviour.FIXED;
                boolean z7 = z3 && constraintWidget.mDimensionRatio > ActionBarShadowController.ELEVATION_LOW;
                boolean z8 = z4 && constraintWidget.mDimensionRatio > ActionBarShadowController.ELEVATION_LOW;
                if (view == null) {
                    return;
                }
                LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
                int i9 = measure.measureStrategy;
                if (i9 != BasicMeasure.Measure.TRY_GIVEN_DIMENSIONS && i9 != BasicMeasure.Measure.USE_GIVEN_DIMENSIONS && z3 && constraintWidget.mMatchConstraintDefaultWidth == 0 && z4 && constraintWidget.mMatchConstraintDefaultHeight == 0) {
                    measuredHeight = 0;
                    baseline = 0;
                    measuredWidth = 0;
                } else {
                    if ((view instanceof VirtualLayout) && (constraintWidget instanceof androidx.constraintlayout.core.widgets.VirtualLayout)) {
                        ((VirtualLayout) view).onMeasure((androidx.constraintlayout.core.widgets.VirtualLayout) constraintWidget, makeMeasureSpec, makeMeasureSpec2);
                    } else {
                        view.measure(makeMeasureSpec, makeMeasureSpec2);
                    }
                    constraintWidget.setLastMeasureSpec(makeMeasureSpec, makeMeasureSpec2);
                    int measuredWidth2 = view.getMeasuredWidth();
                    int measuredHeight2 = view.getMeasuredHeight();
                    int baseline2 = view.getBaseline();
                    int i10 = constraintWidget.mMatchConstraintMinWidth;
                    int max = i10 > 0 ? Math.max(i10, measuredWidth2) : measuredWidth2;
                    int i11 = constraintWidget.mMatchConstraintMaxWidth;
                    int i12 = max;
                    if (i11 > 0) {
                        i12 = Math.min(i11, max);
                    }
                    int i13 = constraintWidget.mMatchConstraintMinHeight;
                    int max2 = i13 > 0 ? Math.max(i13, measuredHeight2) : measuredHeight2;
                    int i14 = constraintWidget.mMatchConstraintMaxHeight;
                    int i15 = max2;
                    if (i14 > 0) {
                        i15 = Math.min(i14, max2);
                    }
                    int i16 = i15;
                    int i17 = i12;
                    if (!Optimizer.enabled(ConstraintLayout.this.mOptimizationLevel, 1)) {
                        if (z7 && z5) {
                            i17 = (int) ((i15 * constraintWidget.mDimensionRatio) + 0.5f);
                            i16 = i15;
                        } else {
                            i16 = i15;
                            i17 = i12;
                            if (z8) {
                                i16 = i15;
                                i17 = i12;
                                if (z6) {
                                    i16 = (int) ((i12 / constraintWidget.mDimensionRatio) + 0.5f);
                                    i17 = i12;
                                }
                            }
                        }
                    }
                    if (measuredWidth2 == i17) {
                        measuredHeight = i16;
                        baseline = baseline2;
                        measuredWidth = i17;
                    }
                    if (measuredWidth2 != i17) {
                        makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(i17, 1073741824);
                    }
                    if (measuredHeight2 != i16) {
                        makeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(i16, 1073741824);
                    }
                    view.measure(makeMeasureSpec, makeMeasureSpec2);
                    constraintWidget.setLastMeasureSpec(makeMeasureSpec, makeMeasureSpec2);
                    measuredWidth = view.getMeasuredWidth();
                    measuredHeight = view.getMeasuredHeight();
                    baseline = view.getBaseline();
                }
                boolean z9 = baseline != -1;
                measure.measuredNeedsSolverPass = (measuredWidth == measure.horizontalDimension && measuredHeight == measure.verticalDimension) ? false : true;
                if (layoutParams.mNeedsBaseline) {
                    z9 = true;
                }
                if (z9 && baseline != -1 && constraintWidget.getBaselineDistance() != baseline) {
                    measure.measuredNeedsSolverPass = true;
                }
                measure.measuredWidth = measuredWidth;
                measure.measuredHeight = measuredHeight;
                measure.measuredHasBaseline = z9;
                measure.measuredBaseline = baseline;
                ConstraintLayout.access$000(ConstraintLayout.this);
            }
        }
    }

    /* loaded from: mainsysui33.jar:androidx/constraintlayout/widget/ConstraintLayout$ValueModifier.class */
    public interface ValueModifier {
        boolean update(int i, int i2, int i3, View view, LayoutParams layoutParams);
    }

    public ConstraintLayout(Context context) {
        super(context);
        this.mChildrenByIds = new SparseArray<>();
        this.mConstraintHelpers = new ArrayList<>(4);
        this.mLayoutWidget = new ConstraintWidgetContainer();
        this.mMinWidth = 0;
        this.mMinHeight = 0;
        this.mMaxWidth = Integer.MAX_VALUE;
        this.mMaxHeight = Integer.MAX_VALUE;
        this.mDirtyHierarchy = true;
        this.mOptimizationLevel = 257;
        this.mConstraintSet = null;
        this.mConstraintLayoutSpec = null;
        this.mConstraintSetId = -1;
        this.mDesignIds = new HashMap<>();
        this.mLastMeasureWidth = -1;
        this.mLastMeasureHeight = -1;
        this.mLastMeasureWidthSize = -1;
        this.mLastMeasureHeightSize = -1;
        this.mLastMeasureWidthMode = 0;
        this.mLastMeasureHeightMode = 0;
        this.mTempMapIdToWidget = new SparseArray<>();
        this.mMeasurer = new Measurer(this);
        this.mOnMeasureWidthMeasureSpec = 0;
        this.mOnMeasureHeightMeasureSpec = 0;
        init(null, 0, 0);
    }

    public ConstraintLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mChildrenByIds = new SparseArray<>();
        this.mConstraintHelpers = new ArrayList<>(4);
        this.mLayoutWidget = new ConstraintWidgetContainer();
        this.mMinWidth = 0;
        this.mMinHeight = 0;
        this.mMaxWidth = Integer.MAX_VALUE;
        this.mMaxHeight = Integer.MAX_VALUE;
        this.mDirtyHierarchy = true;
        this.mOptimizationLevel = 257;
        this.mConstraintSet = null;
        this.mConstraintLayoutSpec = null;
        this.mConstraintSetId = -1;
        this.mDesignIds = new HashMap<>();
        this.mLastMeasureWidth = -1;
        this.mLastMeasureHeight = -1;
        this.mLastMeasureWidthSize = -1;
        this.mLastMeasureHeightSize = -1;
        this.mLastMeasureWidthMode = 0;
        this.mLastMeasureHeightMode = 0;
        this.mTempMapIdToWidget = new SparseArray<>();
        this.mMeasurer = new Measurer(this);
        this.mOnMeasureWidthMeasureSpec = 0;
        this.mOnMeasureHeightMeasureSpec = 0;
        init(attributeSet, 0, 0);
    }

    public ConstraintLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mChildrenByIds = new SparseArray<>();
        this.mConstraintHelpers = new ArrayList<>(4);
        this.mLayoutWidget = new ConstraintWidgetContainer();
        this.mMinWidth = 0;
        this.mMinHeight = 0;
        this.mMaxWidth = Integer.MAX_VALUE;
        this.mMaxHeight = Integer.MAX_VALUE;
        this.mDirtyHierarchy = true;
        this.mOptimizationLevel = 257;
        this.mConstraintSet = null;
        this.mConstraintLayoutSpec = null;
        this.mConstraintSetId = -1;
        this.mDesignIds = new HashMap<>();
        this.mLastMeasureWidth = -1;
        this.mLastMeasureHeight = -1;
        this.mLastMeasureWidthSize = -1;
        this.mLastMeasureHeightSize = -1;
        this.mLastMeasureWidthMode = 0;
        this.mLastMeasureHeightMode = 0;
        this.mTempMapIdToWidget = new SparseArray<>();
        this.mMeasurer = new Measurer(this);
        this.mOnMeasureWidthMeasureSpec = 0;
        this.mOnMeasureHeightMeasureSpec = 0;
        init(attributeSet, i, 0);
    }

    @TargetApi(21)
    public ConstraintLayout(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mChildrenByIds = new SparseArray<>();
        this.mConstraintHelpers = new ArrayList<>(4);
        this.mLayoutWidget = new ConstraintWidgetContainer();
        this.mMinWidth = 0;
        this.mMinHeight = 0;
        this.mMaxWidth = Integer.MAX_VALUE;
        this.mMaxHeight = Integer.MAX_VALUE;
        this.mDirtyHierarchy = true;
        this.mOptimizationLevel = 257;
        this.mConstraintSet = null;
        this.mConstraintLayoutSpec = null;
        this.mConstraintSetId = -1;
        this.mDesignIds = new HashMap<>();
        this.mLastMeasureWidth = -1;
        this.mLastMeasureHeight = -1;
        this.mLastMeasureWidthSize = -1;
        this.mLastMeasureHeightSize = -1;
        this.mLastMeasureWidthMode = 0;
        this.mLastMeasureHeightMode = 0;
        this.mTempMapIdToWidget = new SparseArray<>();
        this.mMeasurer = new Measurer(this);
        this.mOnMeasureWidthMeasureSpec = 0;
        this.mOnMeasureHeightMeasureSpec = 0;
        init(attributeSet, i, i2);
    }

    public static /* synthetic */ Metrics access$000(ConstraintLayout constraintLayout) {
        constraintLayout.getClass();
        return null;
    }

    private int getPaddingWidth() {
        int max = Math.max(0, getPaddingLeft()) + Math.max(0, getPaddingRight());
        int max2 = Math.max(0, getPaddingStart()) + Math.max(0, getPaddingEnd());
        if (max2 > 0) {
            max = max2;
        }
        return max;
    }

    public static SharedValues getSharedValues() {
        if (sSharedValues == null) {
            sSharedValues = new SharedValues();
        }
        return sSharedValues;
    }

    private ConstraintWidget getTargetWidget(int i) {
        if (i == 0) {
            return this.mLayoutWidget;
        }
        View view = this.mChildrenByIds.get(i);
        View view2 = view;
        if (view == null) {
            View findViewById = findViewById(i);
            view2 = findViewById;
            if (findViewById != null) {
                view2 = findViewById;
                if (findViewById != this) {
                    view2 = findViewById;
                    if (findViewById.getParent() == this) {
                        onViewAdded(findViewById);
                        view2 = findViewById;
                    }
                }
            }
        }
        if (view2 == this) {
            return this.mLayoutWidget;
        }
        return view2 == null ? null : ((LayoutParams) view2.getLayoutParams()).mWidget;
    }

    private void init(AttributeSet attributeSet, int i, int i2) {
        this.mLayoutWidget.setCompanionWidget(this);
        this.mLayoutWidget.setMeasurer(this.mMeasurer);
        this.mChildrenByIds.put(getId(), this);
        this.mConstraintSet = null;
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, R$styleable.ConstraintLayout_Layout, i, i2);
            int indexCount = obtainStyledAttributes.getIndexCount();
            for (int i3 = 0; i3 < indexCount; i3++) {
                int index = obtainStyledAttributes.getIndex(i3);
                if (index == R$styleable.ConstraintLayout_Layout_android_minWidth) {
                    this.mMinWidth = obtainStyledAttributes.getDimensionPixelOffset(index, this.mMinWidth);
                } else if (index == R$styleable.ConstraintLayout_Layout_android_minHeight) {
                    this.mMinHeight = obtainStyledAttributes.getDimensionPixelOffset(index, this.mMinHeight);
                } else if (index == R$styleable.ConstraintLayout_Layout_android_maxWidth) {
                    this.mMaxWidth = obtainStyledAttributes.getDimensionPixelOffset(index, this.mMaxWidth);
                } else if (index == R$styleable.ConstraintLayout_Layout_android_maxHeight) {
                    this.mMaxHeight = obtainStyledAttributes.getDimensionPixelOffset(index, this.mMaxHeight);
                } else if (index == R$styleable.ConstraintLayout_Layout_layout_optimizationLevel) {
                    this.mOptimizationLevel = obtainStyledAttributes.getInt(index, this.mOptimizationLevel);
                } else if (index == R$styleable.ConstraintLayout_Layout_layoutDescription) {
                    int resourceId = obtainStyledAttributes.getResourceId(index, 0);
                    if (resourceId != 0) {
                        try {
                            parseLayoutDescription(resourceId);
                        } catch (Resources.NotFoundException e) {
                            this.mConstraintLayoutSpec = null;
                        }
                    }
                } else if (index == R$styleable.ConstraintLayout_Layout_constraintSet) {
                    int resourceId2 = obtainStyledAttributes.getResourceId(index, 0);
                    try {
                        ConstraintSet constraintSet = new ConstraintSet();
                        this.mConstraintSet = constraintSet;
                        constraintSet.load(getContext(), resourceId2);
                    } catch (Resources.NotFoundException e2) {
                        this.mConstraintSet = null;
                    }
                    this.mConstraintSetId = resourceId2;
                }
            }
            obtainStyledAttributes.recycle();
        }
        this.mLayoutWidget.setOptimizationLevel(this.mOptimizationLevel);
    }

    private void markHierarchyDirty() {
        this.mDirtyHierarchy = true;
        this.mLastMeasureWidth = -1;
        this.mLastMeasureHeight = -1;
        this.mLastMeasureWidthSize = -1;
        this.mLastMeasureHeightSize = -1;
        this.mLastMeasureWidthMode = 0;
        this.mLastMeasureHeightMode = 0;
    }

    private void setChildrenConstraints() {
        boolean isInEditMode = isInEditMode();
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            ConstraintWidget viewWidget = getViewWidget(getChildAt(i));
            if (viewWidget != null) {
                viewWidget.reset();
            }
        }
        if (isInEditMode) {
            for (int i2 = 0; i2 < childCount; i2++) {
                View childAt = getChildAt(i2);
                try {
                    String resourceName = getResources().getResourceName(childAt.getId());
                    setDesignInformation(0, resourceName, Integer.valueOf(childAt.getId()));
                    int indexOf = resourceName.indexOf(47);
                    String str = resourceName;
                    if (indexOf != -1) {
                        str = resourceName.substring(indexOf + 1);
                    }
                    getTargetWidget(childAt.getId()).setDebugName(str);
                } catch (Resources.NotFoundException e) {
                }
            }
        }
        if (this.mConstraintSetId != -1) {
            for (int i3 = 0; i3 < childCount; i3++) {
                View childAt2 = getChildAt(i3);
                if (childAt2.getId() == this.mConstraintSetId && (childAt2 instanceof Constraints)) {
                    this.mConstraintSet = ((Constraints) childAt2).getConstraintSet();
                }
            }
        }
        ConstraintSet constraintSet = this.mConstraintSet;
        if (constraintSet != null) {
            constraintSet.applyToInternal(this, true);
        }
        this.mLayoutWidget.removeAllChildren();
        int size = this.mConstraintHelpers.size();
        if (size > 0) {
            for (int i4 = 0; i4 < size; i4++) {
                this.mConstraintHelpers.get(i4).updatePreLayout(this);
            }
        }
        for (int i5 = 0; i5 < childCount; i5++) {
            View childAt3 = getChildAt(i5);
            if (childAt3 instanceof Placeholder) {
                ((Placeholder) childAt3).updatePreLayout(this);
            }
        }
        this.mTempMapIdToWidget.clear();
        this.mTempMapIdToWidget.put(0, this.mLayoutWidget);
        this.mTempMapIdToWidget.put(getId(), this.mLayoutWidget);
        for (int i6 = 0; i6 < childCount; i6++) {
            View childAt4 = getChildAt(i6);
            this.mTempMapIdToWidget.put(childAt4.getId(), getViewWidget(childAt4));
        }
        for (int i7 = 0; i7 < childCount; i7++) {
            View childAt5 = getChildAt(i7);
            ConstraintWidget viewWidget2 = getViewWidget(childAt5);
            if (viewWidget2 != null) {
                LayoutParams layoutParams = (LayoutParams) childAt5.getLayoutParams();
                this.mLayoutWidget.add(viewWidget2);
                applyConstraintsFromLayoutParams(isInEditMode, childAt5, viewWidget2, layoutParams, this.mTempMapIdToWidget);
            }
        }
    }

    private void setWidgetBaseline(ConstraintWidget constraintWidget, LayoutParams layoutParams, SparseArray<ConstraintWidget> sparseArray, int i, ConstraintAnchor.Type type) {
        View view = this.mChildrenByIds.get(i);
        ConstraintWidget constraintWidget2 = sparseArray.get(i);
        if (constraintWidget2 == null || view == null || !(view.getLayoutParams() instanceof LayoutParams)) {
            return;
        }
        layoutParams.mNeedsBaseline = true;
        ConstraintAnchor.Type type2 = ConstraintAnchor.Type.BASELINE;
        if (type == type2) {
            LayoutParams layoutParams2 = (LayoutParams) view.getLayoutParams();
            layoutParams2.mNeedsBaseline = true;
            layoutParams2.mWidget.setHasBaseline(true);
        }
        constraintWidget.getAnchor(type2).connect(constraintWidget2.getAnchor(type), layoutParams.baselineMargin, layoutParams.goneBaselineMargin, true);
        constraintWidget.setHasBaseline(true);
        constraintWidget.getAnchor(ConstraintAnchor.Type.TOP).reset();
        constraintWidget.getAnchor(ConstraintAnchor.Type.BOTTOM).reset();
    }

    private boolean updateHierarchy() {
        boolean z;
        int childCount = getChildCount();
        int i = 0;
        while (true) {
            z = false;
            if (i >= childCount) {
                break;
            } else if (getChildAt(i).isLayoutRequested()) {
                z = true;
                break;
            } else {
                i++;
            }
        }
        if (z) {
            setChildrenConstraints();
        }
        return z;
    }

    public void addValueModifier(ValueModifier valueModifier) {
        if (this.mModifiers == null) {
            this.mModifiers = new ArrayList<>();
        }
        this.mModifiers.add(valueModifier);
    }

    public void applyConstraintsFromLayoutParams(boolean z, View view, ConstraintWidget constraintWidget, LayoutParams layoutParams, SparseArray<ConstraintWidget> sparseArray) {
        ConstraintWidget constraintWidget2;
        ConstraintWidget constraintWidget3;
        ConstraintWidget constraintWidget4;
        ConstraintWidget constraintWidget5;
        int i;
        layoutParams.validate();
        layoutParams.helped = false;
        constraintWidget.setVisibility(view.getVisibility());
        if (layoutParams.mIsInPlaceholder) {
            constraintWidget.setInPlaceholder(true);
            constraintWidget.setVisibility(8);
        }
        constraintWidget.setCompanionWidget(view);
        if (view instanceof ConstraintHelper) {
            ((ConstraintHelper) view).resolveRtl(constraintWidget, this.mLayoutWidget.isRtl());
        }
        if (layoutParams.mIsGuideline) {
            androidx.constraintlayout.core.widgets.Guideline guideline = (androidx.constraintlayout.core.widgets.Guideline) constraintWidget;
            int i2 = layoutParams.mResolvedGuideBegin;
            int i3 = layoutParams.mResolvedGuideEnd;
            float f = layoutParams.mResolvedGuidePercent;
            if (f != -1.0f) {
                guideline.setGuidePercent(f);
                return;
            } else if (i2 != -1) {
                guideline.setGuideBegin(i2);
                return;
            } else if (i3 != -1) {
                guideline.setGuideEnd(i3);
                return;
            } else {
                return;
            }
        }
        int i4 = layoutParams.mResolvedLeftToLeft;
        int i5 = layoutParams.mResolvedLeftToRight;
        int i6 = layoutParams.mResolvedRightToLeft;
        int i7 = layoutParams.mResolvedRightToRight;
        int i8 = layoutParams.mResolveGoneLeftMargin;
        int i9 = layoutParams.mResolveGoneRightMargin;
        float f2 = layoutParams.mResolvedHorizontalBias;
        int i10 = layoutParams.circleConstraint;
        if (i10 != -1) {
            ConstraintWidget constraintWidget6 = sparseArray.get(i10);
            if (constraintWidget6 != null) {
                constraintWidget.connectCircularConstraint(constraintWidget6, layoutParams.circleAngle, layoutParams.circleRadius);
            }
        } else {
            if (i4 != -1) {
                ConstraintWidget constraintWidget7 = sparseArray.get(i4);
                if (constraintWidget7 != null) {
                    ConstraintAnchor.Type type = ConstraintAnchor.Type.LEFT;
                    constraintWidget.immediateConnect(type, constraintWidget7, type, ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin, i8);
                }
            } else if (i5 != -1 && (constraintWidget2 = sparseArray.get(i5)) != null) {
                constraintWidget.immediateConnect(ConstraintAnchor.Type.LEFT, constraintWidget2, ConstraintAnchor.Type.RIGHT, ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin, i8);
            }
            if (i6 != -1) {
                ConstraintWidget constraintWidget8 = sparseArray.get(i6);
                if (constraintWidget8 != null) {
                    constraintWidget.immediateConnect(ConstraintAnchor.Type.RIGHT, constraintWidget8, ConstraintAnchor.Type.LEFT, ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin, i9);
                }
            } else if (i7 != -1 && (constraintWidget3 = sparseArray.get(i7)) != null) {
                ConstraintAnchor.Type type2 = ConstraintAnchor.Type.RIGHT;
                constraintWidget.immediateConnect(type2, constraintWidget3, type2, ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin, i9);
            }
            int i11 = layoutParams.topToTop;
            if (i11 != -1) {
                ConstraintWidget constraintWidget9 = sparseArray.get(i11);
                if (constraintWidget9 != null) {
                    ConstraintAnchor.Type type3 = ConstraintAnchor.Type.TOP;
                    constraintWidget.immediateConnect(type3, constraintWidget9, type3, ((ViewGroup.MarginLayoutParams) layoutParams).topMargin, layoutParams.goneTopMargin);
                }
            } else {
                int i12 = layoutParams.topToBottom;
                if (i12 != -1 && (constraintWidget4 = sparseArray.get(i12)) != null) {
                    constraintWidget.immediateConnect(ConstraintAnchor.Type.TOP, constraintWidget4, ConstraintAnchor.Type.BOTTOM, ((ViewGroup.MarginLayoutParams) layoutParams).topMargin, layoutParams.goneTopMargin);
                }
            }
            int i13 = layoutParams.bottomToTop;
            if (i13 != -1) {
                ConstraintWidget constraintWidget10 = sparseArray.get(i13);
                if (constraintWidget10 != null) {
                    constraintWidget.immediateConnect(ConstraintAnchor.Type.BOTTOM, constraintWidget10, ConstraintAnchor.Type.TOP, ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin, layoutParams.goneBottomMargin);
                }
            } else {
                int i14 = layoutParams.bottomToBottom;
                if (i14 != -1 && (constraintWidget5 = sparseArray.get(i14)) != null) {
                    ConstraintAnchor.Type type4 = ConstraintAnchor.Type.BOTTOM;
                    constraintWidget.immediateConnect(type4, constraintWidget5, type4, ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin, layoutParams.goneBottomMargin);
                }
            }
            int i15 = layoutParams.baselineToBaseline;
            if (i15 != -1) {
                setWidgetBaseline(constraintWidget, layoutParams, sparseArray, i15, ConstraintAnchor.Type.BASELINE);
            } else {
                int i16 = layoutParams.baselineToTop;
                if (i16 != -1) {
                    setWidgetBaseline(constraintWidget, layoutParams, sparseArray, i16, ConstraintAnchor.Type.TOP);
                } else {
                    int i17 = layoutParams.baselineToBottom;
                    if (i17 != -1) {
                        setWidgetBaseline(constraintWidget, layoutParams, sparseArray, i17, ConstraintAnchor.Type.BOTTOM);
                    }
                }
            }
            if (f2 >= ActionBarShadowController.ELEVATION_LOW) {
                constraintWidget.setHorizontalBiasPercent(f2);
            }
            float f3 = layoutParams.verticalBias;
            if (f3 >= ActionBarShadowController.ELEVATION_LOW) {
                constraintWidget.setVerticalBiasPercent(f3);
            }
        }
        if (z && ((i = layoutParams.editorAbsoluteX) != -1 || layoutParams.editorAbsoluteY != -1)) {
            constraintWidget.setOrigin(i, layoutParams.editorAbsoluteY);
        }
        if (layoutParams.mHorizontalDimensionFixed) {
            constraintWidget.setHorizontalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.FIXED);
            constraintWidget.setWidth(((ViewGroup.MarginLayoutParams) layoutParams).width);
            if (((ViewGroup.MarginLayoutParams) layoutParams).width == -2) {
                constraintWidget.setHorizontalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.WRAP_CONTENT);
            }
        } else if (((ViewGroup.MarginLayoutParams) layoutParams).width == -1) {
            if (layoutParams.constrainedWidth) {
                constraintWidget.setHorizontalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT);
            } else {
                constraintWidget.setHorizontalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.MATCH_PARENT);
            }
            constraintWidget.getAnchor(ConstraintAnchor.Type.LEFT).mMargin = ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin;
            constraintWidget.getAnchor(ConstraintAnchor.Type.RIGHT).mMargin = ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin;
        } else {
            constraintWidget.setHorizontalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT);
            constraintWidget.setWidth(0);
        }
        if (layoutParams.mVerticalDimensionFixed) {
            constraintWidget.setVerticalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.FIXED);
            constraintWidget.setHeight(((ViewGroup.MarginLayoutParams) layoutParams).height);
            if (((ViewGroup.MarginLayoutParams) layoutParams).height == -2) {
                constraintWidget.setVerticalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.WRAP_CONTENT);
            }
        } else if (((ViewGroup.MarginLayoutParams) layoutParams).height == -1) {
            if (layoutParams.constrainedHeight) {
                constraintWidget.setVerticalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT);
            } else {
                constraintWidget.setVerticalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.MATCH_PARENT);
            }
            constraintWidget.getAnchor(ConstraintAnchor.Type.TOP).mMargin = ((ViewGroup.MarginLayoutParams) layoutParams).topMargin;
            constraintWidget.getAnchor(ConstraintAnchor.Type.BOTTOM).mMargin = ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin;
        } else {
            constraintWidget.setVerticalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT);
            constraintWidget.setHeight(0);
        }
        constraintWidget.setDimensionRatio(layoutParams.dimensionRatio);
        constraintWidget.setHorizontalWeight(layoutParams.horizontalWeight);
        constraintWidget.setVerticalWeight(layoutParams.verticalWeight);
        constraintWidget.setHorizontalChainStyle(layoutParams.horizontalChainStyle);
        constraintWidget.setVerticalChainStyle(layoutParams.verticalChainStyle);
        constraintWidget.setWrapBehaviorInParent(layoutParams.wrapBehaviorInParent);
        constraintWidget.setHorizontalMatchStyle(layoutParams.matchConstraintDefaultWidth, layoutParams.matchConstraintMinWidth, layoutParams.matchConstraintMaxWidth, layoutParams.matchConstraintPercentWidth);
        constraintWidget.setVerticalMatchStyle(layoutParams.matchConstraintDefaultHeight, layoutParams.matchConstraintMinHeight, layoutParams.matchConstraintMaxHeight, layoutParams.matchConstraintPercentHeight);
    }

    @Override // android.view.ViewGroup
    public boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof LayoutParams;
    }

    @Override // android.view.ViewGroup, android.view.View
    public void dispatchDraw(Canvas canvas) {
        Object tag;
        int size;
        ArrayList<ConstraintHelper> arrayList = this.mConstraintHelpers;
        if (arrayList != null && (size = arrayList.size()) > 0) {
            for (int i = 0; i < size; i++) {
                this.mConstraintHelpers.get(i).updatePreDraw(this);
            }
        }
        super.dispatchDraw(canvas);
        if (isInEditMode()) {
            float width = getWidth();
            float height = getHeight();
            int childCount = getChildCount();
            for (int i2 = 0; i2 < childCount; i2++) {
                View childAt = getChildAt(i2);
                if (childAt.getVisibility() != 8 && (tag = childAt.getTag()) != null && (tag instanceof String)) {
                    String[] split = ((String) tag).split(",");
                    if (split.length == 4) {
                        int parseInt = Integer.parseInt(split[0]);
                        int parseInt2 = Integer.parseInt(split[1]);
                        int parseInt3 = Integer.parseInt(split[2]);
                        int parseInt4 = Integer.parseInt(split[3]);
                        int i3 = (int) ((parseInt / 1080.0f) * width);
                        int i4 = (int) ((parseInt2 / 1920.0f) * height);
                        int i5 = (int) ((parseInt3 / 1080.0f) * width);
                        int i6 = (int) ((parseInt4 / 1920.0f) * height);
                        Paint paint = new Paint();
                        paint.setColor(-65536);
                        float f = i3;
                        float f2 = i4;
                        float f3 = i3 + i5;
                        canvas.drawLine(f, f2, f3, f2, paint);
                        float f4 = i4 + i6;
                        canvas.drawLine(f3, f2, f3, f4, paint);
                        canvas.drawLine(f3, f4, f, f4, paint);
                        canvas.drawLine(f, f4, f, f2, paint);
                        paint.setColor(-16711936);
                        canvas.drawLine(f, f2, f3, f4, paint);
                        canvas.drawLine(f, f4, f3, f2, paint);
                    }
                }
            }
        }
    }

    public boolean dynamicUpdateConstraints(int i, int i2) {
        boolean z = false;
        if (this.mModifiers == null) {
            return false;
        }
        int size = View.MeasureSpec.getSize(i);
        int size2 = View.MeasureSpec.getSize(i2);
        Iterator<ValueModifier> it = this.mModifiers.iterator();
        while (it.hasNext()) {
            ValueModifier next = it.next();
            Iterator<ConstraintWidget> it2 = this.mLayoutWidget.getChildren().iterator();
            boolean z2 = z;
            while (true) {
                boolean z3 = z2;
                z = z3;
                if (it2.hasNext()) {
                    View view = (View) it2.next().getCompanionWidget();
                    z2 = z3 | next.update(size, size2, view.getId(), view, (LayoutParams) view.getLayoutParams());
                }
            }
        }
        return z;
    }

    public void fillMetrics(Metrics metrics) {
        this.mLayoutWidget.fillMetrics(metrics);
    }

    @Override // android.view.View
    public void forceLayout() {
        markHierarchyDirty();
        super.forceLayout();
    }

    /* JADX DEBUG: Method merged with bridge method */
    @Override // android.view.ViewGroup
    public LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-2, -2);
    }

    @Override // android.view.ViewGroup
    public ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return new LayoutParams(layoutParams);
    }

    /* JADX DEBUG: Method merged with bridge method */
    @Override // android.view.ViewGroup
    public LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(getContext(), attributeSet);
    }

    public Object getDesignInformation(int i, Object obj) {
        if (i == 0 && (obj instanceof String)) {
            String str = (String) obj;
            HashMap<String, Integer> hashMap = this.mDesignIds;
            if (hashMap == null || !hashMap.containsKey(str)) {
                return null;
            }
            return this.mDesignIds.get(str);
        }
        return null;
    }

    public int getMaxHeight() {
        return this.mMaxHeight;
    }

    public int getMaxWidth() {
        return this.mMaxWidth;
    }

    public int getMinHeight() {
        return this.mMinHeight;
    }

    public int getMinWidth() {
        return this.mMinWidth;
    }

    public int getOptimizationLevel() {
        return this.mLayoutWidget.getOptimizationLevel();
    }

    public String getSceneString() {
        int id;
        StringBuilder sb = new StringBuilder();
        if (this.mLayoutWidget.stringId == null) {
            int id2 = getId();
            if (id2 != -1) {
                this.mLayoutWidget.stringId = getContext().getResources().getResourceEntryName(id2);
            } else {
                this.mLayoutWidget.stringId = "parent";
            }
        }
        if (this.mLayoutWidget.getDebugName() == null) {
            ConstraintWidgetContainer constraintWidgetContainer = this.mLayoutWidget;
            constraintWidgetContainer.setDebugName(constraintWidgetContainer.stringId);
            Log.v(TAG, " setDebugName " + this.mLayoutWidget.getDebugName());
        }
        Iterator<ConstraintWidget> it = this.mLayoutWidget.getChildren().iterator();
        while (it.hasNext()) {
            ConstraintWidget next = it.next();
            View view = (View) next.getCompanionWidget();
            if (view != null) {
                if (next.stringId == null && (id = view.getId()) != -1) {
                    next.stringId = getContext().getResources().getResourceEntryName(id);
                }
                if (next.getDebugName() == null) {
                    next.setDebugName(next.stringId);
                    Log.v(TAG, " setDebugName " + next.getDebugName());
                }
            }
        }
        this.mLayoutWidget.getSceneString(sb);
        return sb.toString();
    }

    public View getViewById(int i) {
        return this.mChildrenByIds.get(i);
    }

    public final ConstraintWidget getViewWidget(View view) {
        if (view == this) {
            return this.mLayoutWidget;
        }
        if (view != null) {
            if (view.getLayoutParams() instanceof LayoutParams) {
                return ((LayoutParams) view.getLayoutParams()).mWidget;
            }
            view.setLayoutParams(generateLayoutParams(view.getLayoutParams()));
            if (view.getLayoutParams() instanceof LayoutParams) {
                return ((LayoutParams) view.getLayoutParams()).mWidget;
            }
            return null;
        }
        return null;
    }

    public boolean isRtl() {
        boolean z = false;
        if ((getContext().getApplicationInfo().flags & 4194304) != 0) {
            z = false;
            if (1 == getLayoutDirection()) {
                z = true;
            }
        }
        return z;
    }

    public void loadLayoutDescription(int i) {
        if (i == 0) {
            this.mConstraintLayoutSpec = null;
            return;
        }
        try {
            this.mConstraintLayoutSpec = new ConstraintLayoutStates(getContext(), this, i);
        } catch (Resources.NotFoundException e) {
            this.mConstraintLayoutSpec = null;
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        View content;
        int childCount = getChildCount();
        boolean isInEditMode = isInEditMode();
        for (int i5 = 0; i5 < childCount; i5++) {
            View childAt = getChildAt(i5);
            LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
            ConstraintWidget constraintWidget = layoutParams.mWidget;
            if ((childAt.getVisibility() != 8 || layoutParams.mIsGuideline || layoutParams.mIsHelper || layoutParams.mIsVirtualGroup || isInEditMode) && !layoutParams.mIsInPlaceholder) {
                int x = constraintWidget.getX();
                int y = constraintWidget.getY();
                int width = constraintWidget.getWidth() + x;
                int height = constraintWidget.getHeight() + y;
                childAt.layout(x, y, width, height);
                if ((childAt instanceof Placeholder) && (content = ((Placeholder) childAt).getContent()) != null) {
                    content.setVisibility(0);
                    content.layout(x, y, width, height);
                }
            }
        }
        int size = this.mConstraintHelpers.size();
        if (size > 0) {
            for (int i6 = 0; i6 < size; i6++) {
                this.mConstraintHelpers.get(i6).updatePostLayout(this);
            }
        }
    }

    @Override // android.view.View
    public void onMeasure(int i, int i2) {
        boolean dynamicUpdateConstraints = this.mDirtyHierarchy | dynamicUpdateConstraints(i, i2);
        this.mDirtyHierarchy = dynamicUpdateConstraints;
        if (!dynamicUpdateConstraints) {
            int childCount = getChildCount();
            int i3 = 0;
            while (true) {
                if (i3 >= childCount) {
                    break;
                } else if (getChildAt(i3).isLayoutRequested()) {
                    this.mDirtyHierarchy = true;
                    break;
                } else {
                    i3++;
                }
            }
        }
        boolean z = this.mDirtyHierarchy;
        this.mOnMeasureWidthMeasureSpec = i;
        this.mOnMeasureHeightMeasureSpec = i2;
        this.mLayoutWidget.setRtl(isRtl());
        if (this.mDirtyHierarchy) {
            this.mDirtyHierarchy = false;
            if (updateHierarchy()) {
                this.mLayoutWidget.updateHierarchy();
            }
        }
        this.mLayoutWidget.fillMetrics(null);
        resolveSystem(this.mLayoutWidget, this.mOptimizationLevel, i, i2);
        resolveMeasuredDimension(i, i2, this.mLayoutWidget.getWidth(), this.mLayoutWidget.getHeight(), this.mLayoutWidget.isWidthMeasuredTooSmall(), this.mLayoutWidget.isHeightMeasuredTooSmall());
    }

    @Override // android.view.ViewGroup
    public void onViewAdded(View view) {
        super.onViewAdded(view);
        ConstraintWidget viewWidget = getViewWidget(view);
        if ((view instanceof Guideline) && !(viewWidget instanceof androidx.constraintlayout.core.widgets.Guideline)) {
            LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
            androidx.constraintlayout.core.widgets.Guideline guideline = new androidx.constraintlayout.core.widgets.Guideline();
            layoutParams.mWidget = guideline;
            layoutParams.mIsGuideline = true;
            guideline.setOrientation(layoutParams.orientation);
        }
        if (view instanceof ConstraintHelper) {
            ConstraintHelper constraintHelper = (ConstraintHelper) view;
            constraintHelper.validateParams();
            ((LayoutParams) view.getLayoutParams()).mIsHelper = true;
            if (!this.mConstraintHelpers.contains(constraintHelper)) {
                this.mConstraintHelpers.add(constraintHelper);
            }
        }
        this.mChildrenByIds.put(view.getId(), view);
        this.mDirtyHierarchy = true;
    }

    @Override // android.view.ViewGroup
    public void onViewRemoved(View view) {
        super.onViewRemoved(view);
        this.mChildrenByIds.remove(view.getId());
        this.mLayoutWidget.remove(getViewWidget(view));
        this.mConstraintHelpers.remove(view);
        this.mDirtyHierarchy = true;
    }

    public void parseLayoutDescription(int i) {
        this.mConstraintLayoutSpec = new ConstraintLayoutStates(getContext(), this, i);
    }

    public void removeValueModifier(ValueModifier valueModifier) {
        if (valueModifier == null) {
            return;
        }
        this.mModifiers.remove(valueModifier);
    }

    @Override // android.view.View, android.view.ViewParent
    public void requestLayout() {
        markHierarchyDirty();
        super.requestLayout();
    }

    public void resolveMeasuredDimension(int i, int i2, int i3, int i4, boolean z, boolean z2) {
        Measurer measurer = this.mMeasurer;
        int i5 = measurer.mPaddingHeight;
        int resolveSizeAndState = ViewGroup.resolveSizeAndState(i3 + measurer.mPaddingWidth, i, 0);
        int resolveSizeAndState2 = ViewGroup.resolveSizeAndState(i4 + i5, i2, 0);
        int min = Math.min(this.mMaxWidth, resolveSizeAndState & IStats.Stub.TRANSACTION_getInterfaceVersion);
        int min2 = Math.min(this.mMaxHeight, resolveSizeAndState2 & IStats.Stub.TRANSACTION_getInterfaceVersion);
        int i6 = min;
        if (z) {
            i6 = min | 16777216;
        }
        int i7 = min2;
        if (z2) {
            i7 = min2 | 16777216;
        }
        setMeasuredDimension(i6, i7);
        this.mLastMeasureWidth = i6;
        this.mLastMeasureHeight = i7;
    }

    public void resolveSystem(ConstraintWidgetContainer constraintWidgetContainer, int i, int i2, int i3) {
        int mode = View.MeasureSpec.getMode(i2);
        int size = View.MeasureSpec.getSize(i2);
        int mode2 = View.MeasureSpec.getMode(i3);
        int size2 = View.MeasureSpec.getSize(i3);
        int max = Math.max(0, getPaddingTop());
        int max2 = Math.max(0, getPaddingBottom());
        int i4 = max + max2;
        int paddingWidth = getPaddingWidth();
        this.mMeasurer.captureLayoutInfo(i2, i3, max, max2, paddingWidth, i4);
        int max3 = Math.max(0, getPaddingStart());
        int max4 = Math.max(0, getPaddingEnd());
        if (max3 <= 0 && max4 <= 0) {
            max3 = Math.max(0, getPaddingLeft());
        } else if (isRtl()) {
            max3 = max4;
        }
        int i5 = size - paddingWidth;
        int i6 = size2 - i4;
        setSelfDimensionBehaviour(constraintWidgetContainer, mode, i5, mode2, i6);
        constraintWidgetContainer.measure(i, mode, i5, mode2, i6, this.mLastMeasureWidth, this.mLastMeasureHeight, max3, max);
    }

    public void setConstraintSet(ConstraintSet constraintSet) {
        this.mConstraintSet = constraintSet;
    }

    public void setDesignInformation(int i, Object obj, Object obj2) {
        if (i == 0 && (obj instanceof String) && (obj2 instanceof Integer)) {
            if (this.mDesignIds == null) {
                this.mDesignIds = new HashMap<>();
            }
            String str = (String) obj;
            int indexOf = str.indexOf("/");
            String str2 = str;
            if (indexOf != -1) {
                str2 = str.substring(indexOf + 1);
            }
            this.mDesignIds.put(str2, Integer.valueOf(((Integer) obj2).intValue()));
        }
    }

    @Override // android.view.View
    public void setId(int i) {
        this.mChildrenByIds.remove(getId());
        super.setId(i);
        this.mChildrenByIds.put(getId(), this);
    }

    public void setMaxHeight(int i) {
        if (i == this.mMaxHeight) {
            return;
        }
        this.mMaxHeight = i;
        requestLayout();
    }

    public void setMaxWidth(int i) {
        if (i == this.mMaxWidth) {
            return;
        }
        this.mMaxWidth = i;
        requestLayout();
    }

    public void setMinHeight(int i) {
        if (i == this.mMinHeight) {
            return;
        }
        this.mMinHeight = i;
        requestLayout();
    }

    public void setMinWidth(int i) {
        if (i == this.mMinWidth) {
            return;
        }
        this.mMinWidth = i;
        requestLayout();
    }

    public void setOnConstraintsChanged(ConstraintsChangedListener constraintsChangedListener) {
        ConstraintLayoutStates constraintLayoutStates = this.mConstraintLayoutSpec;
        if (constraintLayoutStates != null) {
            constraintLayoutStates.setOnConstraintsChanged(constraintsChangedListener);
        }
    }

    public void setOptimizationLevel(int i) {
        this.mOptimizationLevel = i;
        this.mLayoutWidget.setOptimizationLevel(i);
    }

    public void setSelfDimensionBehaviour(ConstraintWidgetContainer constraintWidgetContainer, int i, int i2, int i3, int i4) {
        ConstraintWidget.DimensionBehaviour dimensionBehaviour;
        Measurer measurer = this.mMeasurer;
        int i5 = measurer.mPaddingHeight;
        int i6 = measurer.mPaddingWidth;
        ConstraintWidget.DimensionBehaviour dimensionBehaviour2 = ConstraintWidget.DimensionBehaviour.FIXED;
        int childCount = getChildCount();
        if (i == Integer.MIN_VALUE) {
            ConstraintWidget.DimensionBehaviour dimensionBehaviour3 = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
            dimensionBehaviour = dimensionBehaviour3;
            if (childCount == 0) {
                i2 = Math.max(0, this.mMinWidth);
                dimensionBehaviour = dimensionBehaviour3;
            }
        } else if (i == 0) {
            ConstraintWidget.DimensionBehaviour dimensionBehaviour4 = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
            dimensionBehaviour = dimensionBehaviour4;
            if (childCount == 0) {
                i2 = Math.max(0, this.mMinWidth);
                dimensionBehaviour = dimensionBehaviour4;
            }
            i2 = 0;
        } else if (i != 1073741824) {
            dimensionBehaviour = dimensionBehaviour2;
            i2 = 0;
        } else {
            i2 = Math.min(this.mMaxWidth - i6, i2);
            dimensionBehaviour = dimensionBehaviour2;
        }
        if (i3 == Integer.MIN_VALUE) {
            ConstraintWidget.DimensionBehaviour dimensionBehaviour5 = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
            dimensionBehaviour2 = dimensionBehaviour5;
            if (childCount == 0) {
                i4 = Math.max(0, this.mMinHeight);
                dimensionBehaviour2 = dimensionBehaviour5;
            }
        } else if (i3 != 0) {
            if (i3 == 1073741824) {
                i4 = Math.min(this.mMaxHeight - i5, i4);
            }
            i4 = 0;
        } else {
            ConstraintWidget.DimensionBehaviour dimensionBehaviour6 = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
            dimensionBehaviour2 = dimensionBehaviour6;
            if (childCount == 0) {
                i4 = Math.max(0, this.mMinHeight);
                dimensionBehaviour2 = dimensionBehaviour6;
            }
            i4 = 0;
        }
        if (i2 != constraintWidgetContainer.getWidth() || i4 != constraintWidgetContainer.getHeight()) {
            constraintWidgetContainer.invalidateMeasures();
        }
        constraintWidgetContainer.setX(0);
        constraintWidgetContainer.setY(0);
        constraintWidgetContainer.setMaxWidth(this.mMaxWidth - i6);
        constraintWidgetContainer.setMaxHeight(this.mMaxHeight - i5);
        constraintWidgetContainer.setMinWidth(0);
        constraintWidgetContainer.setMinHeight(0);
        constraintWidgetContainer.setHorizontalDimensionBehaviour(dimensionBehaviour);
        constraintWidgetContainer.setWidth(i2);
        constraintWidgetContainer.setVerticalDimensionBehaviour(dimensionBehaviour2);
        constraintWidgetContainer.setHeight(i4);
        constraintWidgetContainer.setMinWidth(this.mMinWidth - i6);
        constraintWidgetContainer.setMinHeight(this.mMinHeight - i5);
    }

    public void setState(int i, int i2, int i3) {
        ConstraintLayoutStates constraintLayoutStates = this.mConstraintLayoutSpec;
        if (constraintLayoutStates != null) {
            constraintLayoutStates.updateConstraints(i, i2, i3);
        }
    }

    @Override // android.view.ViewGroup
    public boolean shouldDelayChildPressedState() {
        return false;
    }
}