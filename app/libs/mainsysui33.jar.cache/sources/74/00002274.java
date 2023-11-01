package com.android.systemui.qs.tileimpl;

import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Trace;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import com.android.settingslib.Utils;
import com.android.settingslib.widget.ActionBarShadowController;
import com.android.systemui.FontSizeUtils;
import com.android.systemui.R$attr;
import com.android.systemui.R$dimen;
import com.android.systemui.R$drawable;
import com.android.systemui.R$id;
import com.android.systemui.R$layout;
import com.android.systemui.R$string;
import com.android.systemui.animation.LaunchableView;
import com.android.systemui.animation.LaunchableViewDelegate;
import com.android.systemui.plugins.qs.QSIconView;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.plugins.qs.QSTileView;
import com.android.systemui.qs.logging.QSLogger;
import java.util.List;
import java.util.Objects;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/tileimpl/QSTileViewImpl.class */
public class QSTileViewImpl extends QSTileView implements HeightOverrideable, LaunchableView {
    public static final Companion Companion = new Companion(null);
    public final QSIconView _icon;
    public int _position;
    public String accessibilityClass;
    public ImageView chevronView;
    public final boolean collapsed;
    public final int colorActive;
    public Drawable colorBackgroundDrawable;
    public final int colorInactive;
    public final int colorLabelActive;
    public final int colorLabelInactive;
    public final int colorLabelUnavailable;
    public final int colorSecondaryLabelActive;
    public final int colorSecondaryLabelInactive;
    public final int colorSecondaryLabelUnavailable;
    public final int colorUnavailable;
    public ImageView customDrawableView;
    public int heightOverride;
    public TextView label;
    public IgnorableChildLinearLayout labelContainer;
    public boolean lastDisabledByPolicy;
    public int lastState;
    public CharSequence lastStateDescription;
    public final LaunchableViewDelegate launchableViewDelegate;
    public final int[] locInScreen;
    public QSLogger mQsLogger;
    public int paintColor;
    public RippleDrawable ripple;
    public TextView secondaryLabel;
    public boolean showRippleEffect;
    public ViewGroup sideView;
    public final ValueAnimator singleAnimator;
    public float squishinessFraction;
    public CharSequence stateDescriptionDeltas;
    public boolean tileState;

    /* loaded from: mainsysui33.jar:com/android/systemui/qs/tileimpl/QSTileViewImpl$Companion.class */
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static /* synthetic */ void getTILE_STATE_RES_PREFIX$frameworks__base__packages__SystemUI__android_common__SystemUI_core$annotations() {
        }
    }

    public QSTileViewImpl(Context context, QSIconView qSIconView, boolean z) {
        super(context);
        this._icon = qSIconView;
        this.collapsed = z;
        this._position = -1;
        this.heightOverride = -1;
        this.squishinessFraction = 1.0f;
        this.colorActive = Utils.getColorAttrDefaultColor(context, 17956900);
        int colorAttrDefaultColor = Utils.getColorAttrDefaultColor(context, R$attr.offStateColor);
        this.colorInactive = colorAttrDefaultColor;
        this.colorUnavailable = Utils.applyAlpha(0.3f, colorAttrDefaultColor);
        this.colorLabelActive = Utils.getColorAttrDefaultColor(context, 17957107);
        this.colorLabelInactive = Utils.getColorAttrDefaultColor(context, 16842806);
        this.colorLabelUnavailable = Utils.getColorAttrDefaultColor(context, 16843282);
        this.colorSecondaryLabelActive = Utils.getColorAttrDefaultColor(context, 16842810);
        this.colorSecondaryLabelInactive = Utils.getColorAttrDefaultColor(context, 16842808);
        this.colorSecondaryLabelUnavailable = Utils.getColorAttrDefaultColor(context, 16843282);
        this.showRippleEffect = true;
        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setDuration(350L);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.qs.tileimpl.QSTileViewImpl$singleAnimator$1$1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                QSTileViewImpl.access$setAllColors(QSTileViewImpl.this, ((Integer) valueAnimator2.getAnimatedValue("background")).intValue(), ((Integer) valueAnimator2.getAnimatedValue("label")).intValue(), ((Integer) valueAnimator2.getAnimatedValue("secondaryLabel")).intValue(), ((Integer) valueAnimator2.getAnimatedValue("chevron")).intValue());
            }
        });
        this.singleAnimator = valueAnimator;
        this.lastState = -1;
        this.launchableViewDelegate = new LaunchableViewDelegate(this, new Function1<Integer, Unit>() { // from class: com.android.systemui.qs.tileimpl.QSTileViewImpl$launchableViewDelegate$1
            {
                super(1);
            }

            public /* bridge */ /* synthetic */ Object invoke(Object obj) {
                invoke(((Number) obj).intValue());
                return Unit.INSTANCE;
            }

            public final void invoke(int i) {
                QSTileViewImpl.m3972access$setVisibility$s473880907(QSTileViewImpl.this, i);
            }
        });
        this.locInScreen = new int[2];
        setId(LinearLayout.generateViewId());
        setOrientation(0);
        setGravity(8388627);
        setImportantForAccessibility(1);
        setClipChildren(false);
        setClipToPadding(false);
        setFocusable(true);
        setBackground(createTileBackground());
        setColor(getBackgroundColorForState$default(this, 2, false, 2, null));
        int dimensionPixelSize = getResources().getDimensionPixelSize(R$dimen.qs_tile_padding);
        setPaddingRelative(getResources().getDimensionPixelSize(R$dimen.qs_tile_start_padding), dimensionPixelSize, dimensionPixelSize, dimensionPixelSize);
        int dimensionPixelSize2 = getResources().getDimensionPixelSize(R$dimen.qs_icon_size);
        addView(qSIconView, new LinearLayout.LayoutParams(dimensionPixelSize2, dimensionPixelSize2));
        createAndAddLabels();
        createAndAddSideView();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.tileimpl.QSTileViewImpl$singleAnimator$1$1.onAnimationUpdate(android.animation.ValueAnimator):void] */
    public static final /* synthetic */ void access$setAllColors(QSTileViewImpl qSTileViewImpl, int i, int i2, int i3, int i4) {
        qSTileViewImpl.setAllColors(i, i2, i3, i4);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.tileimpl.QSTileViewImpl$launchableViewDelegate$1.invoke(int):void] */
    /* renamed from: access$setVisibility$s-473880907 */
    public static final /* synthetic */ void m3972access$setVisibility$s473880907(QSTileViewImpl qSTileViewImpl, int i) {
        super.setVisibility(i);
    }

    public static /* synthetic */ int getBackgroundColorForState$default(QSTileViewImpl qSTileViewImpl, int i, boolean z, int i2, Object obj) {
        if (obj == null) {
            if ((i2 & 2) != 0) {
                z = false;
            }
            return qSTileViewImpl.getBackgroundColorForState(i, z);
        }
        throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: getBackgroundColorForState");
    }

    public static /* synthetic */ int getChevronColorForState$default(QSTileViewImpl qSTileViewImpl, int i, boolean z, int i2, Object obj) {
        if (obj == null) {
            if ((i2 & 2) != 0) {
                z = false;
            }
            return qSTileViewImpl.getChevronColorForState(i, z);
        }
        throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: getChevronColorForState");
    }

    public static /* synthetic */ int getLabelColorForState$default(QSTileViewImpl qSTileViewImpl, int i, boolean z, int i2, Object obj) {
        if (obj == null) {
            if ((i2 & 2) != 0) {
                z = false;
            }
            return qSTileViewImpl.getLabelColorForState(i, z);
        }
        throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: getLabelColorForState");
    }

    public static /* synthetic */ int getSecondaryLabelColorForState$default(QSTileViewImpl qSTileViewImpl, int i, boolean z, int i2, Object obj) {
        if (obj == null) {
            if ((i2 & 2) != 0) {
                z = false;
            }
            return qSTileViewImpl.getSecondaryLabelColorForState(i, z);
        }
        throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: getSecondaryLabelColorForState");
    }

    public boolean animationsEnabled() {
        boolean z = false;
        if (isShown()) {
            if (getAlpha() == 1.0f) {
                getLocationOnScreen(this.locInScreen);
                if (this.locInScreen[1] >= (-getHeight())) {
                    z = true;
                }
                return z;
            }
            return false;
        }
        return false;
    }

    public final void createAndAddLabels() {
        IgnorableChildLinearLayout ignorableChildLinearLayout = (IgnorableChildLinearLayout) LayoutInflater.from(getContext()).inflate(R$layout.qs_tile_label, (ViewGroup) this, false);
        this.labelContainer = ignorableChildLinearLayout;
        IgnorableChildLinearLayout ignorableChildLinearLayout2 = ignorableChildLinearLayout;
        if (ignorableChildLinearLayout == null) {
            ignorableChildLinearLayout2 = null;
        }
        this.label = (TextView) ignorableChildLinearLayout2.requireViewById(R$id.tile_label);
        IgnorableChildLinearLayout ignorableChildLinearLayout3 = this.labelContainer;
        IgnorableChildLinearLayout ignorableChildLinearLayout4 = ignorableChildLinearLayout3;
        if (ignorableChildLinearLayout3 == null) {
            ignorableChildLinearLayout4 = null;
        }
        setSecondaryLabel((TextView) ignorableChildLinearLayout4.requireViewById(R$id.app_label));
        if (this.collapsed) {
            IgnorableChildLinearLayout ignorableChildLinearLayout5 = this.labelContainer;
            IgnorableChildLinearLayout ignorableChildLinearLayout6 = ignorableChildLinearLayout5;
            if (ignorableChildLinearLayout5 == null) {
                ignorableChildLinearLayout6 = null;
            }
            ignorableChildLinearLayout6.setIgnoreLastView(true);
            IgnorableChildLinearLayout ignorableChildLinearLayout7 = this.labelContainer;
            IgnorableChildLinearLayout ignorableChildLinearLayout8 = ignorableChildLinearLayout7;
            if (ignorableChildLinearLayout7 == null) {
                ignorableChildLinearLayout8 = null;
            }
            ignorableChildLinearLayout8.setForceUnspecifiedMeasure(true);
            getSecondaryLabel().setAlpha(ActionBarShadowController.ELEVATION_LOW);
        }
        setLabelColor(getLabelColorForState$default(this, 2, false, 2, null));
        setSecondaryLabelColor(getSecondaryLabelColorForState$default(this, 2, false, 2, null));
        IgnorableChildLinearLayout ignorableChildLinearLayout9 = this.labelContainer;
        if (ignorableChildLinearLayout9 == null) {
            ignorableChildLinearLayout9 = null;
        }
        addView(ignorableChildLinearLayout9);
    }

    public final void createAndAddSideView() {
        setSideView((ViewGroup) LayoutInflater.from(getContext()).inflate(R$layout.qs_tile_side_icon, (ViewGroup) this, false));
        this.customDrawableView = (ImageView) getSideView().requireViewById(R$id.customDrawable);
        this.chevronView = (ImageView) getSideView().requireViewById(R$id.chevron);
        setChevronColor(getChevronColorForState$default(this, 2, false, 2, null));
        addView(getSideView());
    }

    public final Drawable createTileBackground() {
        RippleDrawable rippleDrawable = (RippleDrawable) ((LinearLayout) this).mContext.getDrawable(R$drawable.qs_tile_background);
        this.ripple = rippleDrawable;
        RippleDrawable rippleDrawable2 = rippleDrawable;
        if (rippleDrawable == null) {
            rippleDrawable2 = null;
        }
        this.colorBackgroundDrawable = rippleDrawable2.findDrawableByLayerId(R$id.background);
        RippleDrawable rippleDrawable3 = this.ripple;
        if (rippleDrawable3 == null) {
            rippleDrawable3 = null;
        }
        return rippleDrawable3;
    }

    public final int getBackgroundColorForState(int i, boolean z) {
        int i2;
        if (i == 0 || z) {
            i2 = this.colorUnavailable;
        } else if (i == 2) {
            i2 = this.colorActive;
        } else if (i == 1) {
            i2 = this.colorInactive;
        } else {
            Log.e("QSTileViewImpl", "Invalid state " + i);
            i2 = 0;
        }
        return i2;
    }

    public final int getChevronColorForState(int i, boolean z) {
        return getSecondaryLabelColorForState(i, z);
    }

    public final List<Integer> getCurrentColors$frameworks__base__packages__SystemUI__android_common__SystemUI_core() {
        int i = this.paintColor;
        int i2 = 0;
        TextView textView = this.label;
        TextView textView2 = textView;
        if (textView == null) {
            textView2 = null;
        }
        int currentTextColor = textView2.getCurrentTextColor();
        int currentTextColor2 = getSecondaryLabel().getCurrentTextColor();
        ImageView imageView = this.chevronView;
        if (imageView == null) {
            imageView = null;
        }
        ColorStateList imageTintList = imageView.getImageTintList();
        if (imageTintList != null) {
            i2 = imageTintList.getDefaultColor();
        }
        return CollectionsKt__CollectionsKt.listOf(new Integer[]{Integer.valueOf(i), Integer.valueOf(currentTextColor), Integer.valueOf(currentTextColor2), Integer.valueOf(i2)});
    }

    @Override // com.android.systemui.plugins.qs.QSTileView
    public int getDetailY() {
        return getTop() + (getHeight() / 2);
    }

    public int getHeightOverride() {
        return this.heightOverride;
    }

    @Override // com.android.systemui.plugins.qs.QSTileView
    public QSIconView getIcon() {
        return this._icon;
    }

    @Override // com.android.systemui.plugins.qs.QSTileView
    public View getIconWithBackground() {
        return getIcon();
    }

    @Override // com.android.systemui.plugins.qs.QSTileView
    public View getLabel() {
        TextView textView = this.label;
        TextView textView2 = textView;
        if (textView == null) {
            textView2 = null;
        }
        return textView2;
    }

    public final int getLabelColorForState(int i, boolean z) {
        int i2;
        if (i == 0 || z) {
            i2 = this.colorLabelUnavailable;
        } else if (i == 2) {
            i2 = this.colorLabelActive;
        } else if (i == 1) {
            i2 = this.colorLabelInactive;
        } else {
            Log.e("QSTileViewImpl", "Invalid state " + i);
            i2 = 0;
        }
        return i2;
    }

    @Override // com.android.systemui.plugins.qs.QSTileView
    public View getLabelContainer() {
        IgnorableChildLinearLayout ignorableChildLinearLayout = this.labelContainer;
        IgnorableChildLinearLayout ignorableChildLinearLayout2 = ignorableChildLinearLayout;
        if (ignorableChildLinearLayout == null) {
            ignorableChildLinearLayout2 = null;
        }
        return ignorableChildLinearLayout2;
    }

    @Override // com.android.systemui.plugins.qs.QSTileView
    public View getSecondaryIcon() {
        return getSideView();
    }

    @Override // com.android.systemui.plugins.qs.QSTileView
    public View getSecondaryLabel() {
        return getSecondaryLabel();
    }

    @Override // com.android.systemui.plugins.qs.QSTileView
    public final TextView getSecondaryLabel() {
        TextView textView = this.secondaryLabel;
        if (textView != null) {
            return textView;
        }
        return null;
    }

    public final int getSecondaryLabelColorForState(int i, boolean z) {
        int i2;
        if (i == 0 || z) {
            i2 = this.colorSecondaryLabelUnavailable;
        } else if (i == 2) {
            i2 = this.colorSecondaryLabelActive;
        } else if (i == 1) {
            i2 = this.colorSecondaryLabelInactive;
        } else {
            Log.e("QSTileViewImpl", "Invalid state " + i);
            i2 = 0;
        }
        return i2;
    }

    public final ViewGroup getSideView() {
        ViewGroup viewGroup = this.sideView;
        if (viewGroup != null) {
            return viewGroup;
        }
        return null;
    }

    public float getSquishinessFraction() {
        return this.squishinessFraction;
    }

    public final String getStateText(QSTile.State state) {
        String str;
        if (state.state == 0 || (state instanceof QSTile.BooleanState)) {
            str = getResources().getStringArray(SubtitleArrayMapping.INSTANCE.getSubtitleId(state.spec))[state.state];
        } else {
            str = "";
        }
        return str;
    }

    public final String getUnavailableText(String str) {
        return getResources().getStringArray(SubtitleArrayMapping.INSTANCE.getSubtitleId(str))[0];
    }

    public void handleStateChanged(QSTile.State state) {
        PropertyValuesHolder colorValuesHolder;
        PropertyValuesHolder colorValuesHolder2;
        PropertyValuesHolder colorValuesHolder3;
        PropertyValuesHolder colorValuesHolder4;
        boolean z;
        boolean animationsEnabled = animationsEnabled();
        this.showRippleEffect = state.showRippleEffect;
        setClickable(state.state != 0);
        setLongClickable(state.handlesLongClick);
        getIcon().setIcon(state, animationsEnabled);
        setContentDescription(state.contentDescription);
        StringBuilder sb = new StringBuilder();
        String stateText = getStateText(state);
        if (!TextUtils.isEmpty(stateText)) {
            sb.append(stateText);
            if (TextUtils.isEmpty(state.secondaryLabel)) {
                state.secondaryLabel = stateText;
            }
        }
        if (state.disabledByPolicy && state.state != 0) {
            sb.append(", ");
            sb.append(getUnavailableText(state.spec));
        }
        if (!TextUtils.isEmpty(state.stateDescription)) {
            sb.append(", ");
            sb.append(state.stateDescription);
            int i = this.lastState;
            if (i != -1 && state.state == i && !Intrinsics.areEqual(state.stateDescription, this.lastStateDescription)) {
                this.stateDescriptionDeltas = state.stateDescription;
            }
        }
        setStateDescription(sb.toString());
        this.lastStateDescription = state.stateDescription;
        this.accessibilityClass = state.state == 0 ? null : state.expandedAccessibilityClassName;
        if ((state instanceof QSTile.BooleanState) && this.tileState != (z = ((QSTile.BooleanState) state).value)) {
            this.tileState = z;
        }
        TextView textView = this.label;
        TextView textView2 = textView;
        if (textView == null) {
            textView2 = null;
        }
        if (!Objects.equals(textView2.getText(), state.label)) {
            TextView textView3 = this.label;
            TextView textView4 = textView3;
            if (textView3 == null) {
                textView4 = null;
            }
            textView4.setText(state.label);
        }
        if (!Objects.equals(getSecondaryLabel().getText(), state.secondaryLabel)) {
            getSecondaryLabel().setText(state.secondaryLabel);
            getSecondaryLabel().setVisibility(TextUtils.isEmpty(state.secondaryLabel) ? 8 : 0);
        }
        if (state.state != this.lastState || state.disabledByPolicy || this.lastDisabledByPolicy) {
            this.singleAnimator.cancel();
            QSLogger qSLogger = this.mQsLogger;
            if (qSLogger != null) {
                String str = state.spec;
                int i2 = state.state;
                boolean z2 = state.disabledByPolicy;
                qSLogger.logTileBackgroundColorUpdateIfInternetTile(str, i2, z2, getBackgroundColorForState(i2, z2));
            }
            if (animationsEnabled) {
                ValueAnimator valueAnimator = this.singleAnimator;
                colorValuesHolder = QSTileViewImplKt.colorValuesHolder("background", this.paintColor, getBackgroundColorForState(state.state, state.disabledByPolicy));
                TextView textView5 = this.label;
                TextView textView6 = textView5;
                if (textView5 == null) {
                    textView6 = null;
                }
                colorValuesHolder2 = QSTileViewImplKt.colorValuesHolder("label", textView6.getCurrentTextColor(), getLabelColorForState(state.state, state.disabledByPolicy));
                colorValuesHolder3 = QSTileViewImplKt.colorValuesHolder("secondaryLabel", getSecondaryLabel().getCurrentTextColor(), getSecondaryLabelColorForState(state.state, state.disabledByPolicy));
                ImageView imageView = this.chevronView;
                ImageView imageView2 = imageView;
                if (imageView == null) {
                    imageView2 = null;
                }
                ColorStateList imageTintList = imageView2.getImageTintList();
                colorValuesHolder4 = QSTileViewImplKt.colorValuesHolder("chevron", imageTintList != null ? imageTintList.getDefaultColor() : 0, getChevronColorForState(state.state, state.disabledByPolicy));
                valueAnimator.setValues(colorValuesHolder, colorValuesHolder2, colorValuesHolder3, colorValuesHolder4);
                this.singleAnimator.start();
            } else {
                setAllColors(getBackgroundColorForState(state.state, state.disabledByPolicy), getLabelColorForState(state.state, state.disabledByPolicy), getSecondaryLabelColorForState(state.state, state.disabledByPolicy), getChevronColorForState(state.state, state.disabledByPolicy));
            }
        }
        loadSideViewDrawableIfNecessary(state);
        TextView textView7 = this.label;
        if (textView7 == null) {
            textView7 = null;
        }
        textView7.setEnabled(!state.disabledByPolicy);
        this.lastState = state.state;
        this.lastDisabledByPolicy = state.disabledByPolicy;
    }

    @Override // android.view.View
    public boolean hasOverlappingRendering() {
        return false;
    }

    public final void init(View.OnClickListener onClickListener, View.OnLongClickListener onLongClickListener) {
        setOnClickListener(onClickListener);
        setOnLongClickListener(onLongClickListener);
    }

    @Override // com.android.systemui.plugins.qs.QSTileView
    public void init(final QSTile qSTile) {
        init(new View.OnClickListener() { // from class: com.android.systemui.qs.tileimpl.QSTileViewImpl$init$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                QSTile.this.click(this);
            }
        }, new View.OnLongClickListener() { // from class: com.android.systemui.qs.tileimpl.QSTileViewImpl$init$2
            @Override // android.view.View.OnLongClickListener
            public final boolean onLongClick(View view) {
                QSTile.this.longClick(this);
                return true;
            }
        });
    }

    public final void loadSideViewDrawableIfNecessary(QSTile.State state) {
        if (state.sideViewCustomDrawable != null) {
            ImageView imageView = this.customDrawableView;
            ImageView imageView2 = imageView;
            if (imageView == null) {
                imageView2 = null;
            }
            imageView2.setImageDrawable(state.sideViewCustomDrawable);
            ImageView imageView3 = this.customDrawableView;
            ImageView imageView4 = imageView3;
            if (imageView3 == null) {
                imageView4 = null;
            }
            imageView4.setVisibility(0);
            ImageView imageView5 = this.chevronView;
            if (imageView5 == null) {
                imageView5 = null;
            }
            imageView5.setVisibility(8);
        } else if (!(state instanceof QSTile.BooleanState) || ((QSTile.BooleanState) state).forceExpandIcon) {
            ImageView imageView6 = this.customDrawableView;
            ImageView imageView7 = imageView6;
            if (imageView6 == null) {
                imageView7 = null;
            }
            imageView7.setImageDrawable(null);
            ImageView imageView8 = this.customDrawableView;
            ImageView imageView9 = imageView8;
            if (imageView8 == null) {
                imageView9 = null;
            }
            imageView9.setVisibility(8);
            ImageView imageView10 = this.chevronView;
            if (imageView10 == null) {
                imageView10 = null;
            }
            imageView10.setVisibility(0);
        } else {
            ImageView imageView11 = this.customDrawableView;
            ImageView imageView12 = imageView11;
            if (imageView11 == null) {
                imageView12 = null;
            }
            imageView12.setImageDrawable(null);
            ImageView imageView13 = this.customDrawableView;
            ImageView imageView14 = imageView13;
            if (imageView13 == null) {
                imageView14 = null;
            }
            imageView14.setVisibility(8);
            ImageView imageView15 = this.chevronView;
            if (imageView15 == null) {
                imageView15 = null;
            }
            imageView15.setVisibility(8);
        }
    }

    @Override // android.view.View
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        updateResources();
    }

    @Override // android.view.View
    public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(accessibilityEvent);
        if (!TextUtils.isEmpty(this.accessibilityClass)) {
            accessibilityEvent.setClassName(this.accessibilityClass);
        }
        if (accessibilityEvent.getContentChangeTypes() != 64 || this.stateDescriptionDeltas == null) {
            return;
        }
        accessibilityEvent.getText().add(this.stateDescriptionDeltas);
        this.stateDescriptionDeltas = null;
    }

    @Override // android.view.View
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setSelected(false);
        if (this.lastDisabledByPolicy) {
            accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_CLICK.getId(), getResources().getString(R$string.accessibility_tile_disabled_by_policy_action_description)));
        }
        if (!TextUtils.isEmpty(this.accessibilityClass)) {
            accessibilityNodeInfo.setClassName(this.lastDisabledByPolicy ? Button.class.getName() : this.accessibilityClass);
            if (Intrinsics.areEqual(Switch.class.getName(), this.accessibilityClass)) {
                accessibilityNodeInfo.setText(getResources().getString(this.tileState ? R$string.switch_bar_on : R$string.switch_bar_off));
                accessibilityNodeInfo.setChecked(this.tileState);
                accessibilityNodeInfo.setCheckable(true);
                if (isLongClickable()) {
                    accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_LONG_CLICK.getId(), getResources().getString(R$string.accessibility_long_click_tile)));
                }
            }
        }
        if (this._position != -1) {
            accessibilityNodeInfo.setCollectionItemInfo(new AccessibilityNodeInfo.CollectionItemInfo(this._position, 1, 0, 1, false));
        }
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup, android.view.View
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        updateHeight();
    }

    @Override // android.widget.LinearLayout, android.view.View
    public void onMeasure(int i, int i2) {
        Trace.traceBegin(4096L, "QSTileViewImpl#onMeasure");
        super.onMeasure(i, i2);
        Trace.endSection();
    }

    @Override // com.android.systemui.plugins.qs.QSTileView
    public void onStateChanged(final QSTile.State state) {
        post(new Runnable() { // from class: com.android.systemui.qs.tileimpl.QSTileViewImpl$onStateChanged$1
            @Override // java.lang.Runnable
            public final void run() {
                QSTileViewImpl.this.handleStateChanged(state);
            }
        });
    }

    @Override // com.android.systemui.qs.tileimpl.HeightOverrideable
    public void resetOverride() {
        setHeightOverride(-1);
        updateHeight();
    }

    public final void setAllColors(int i, int i2, int i3, int i4) {
        setColor(i);
        setLabelColor(i2);
        setSecondaryLabelColor(i3);
        setChevronColor(i4);
    }

    public final void setChevronColor(int i) {
        ImageView imageView = this.chevronView;
        ImageView imageView2 = imageView;
        if (imageView == null) {
            imageView2 = null;
        }
        imageView2.setImageTintList(ColorStateList.valueOf(i));
    }

    @Override // android.view.View
    public void setClickable(boolean z) {
        RippleDrawable rippleDrawable;
        super.setClickable(z);
        Drawable drawable = null;
        if (z && this.showRippleEffect) {
            RippleDrawable rippleDrawable2 = this.ripple;
            rippleDrawable = rippleDrawable2;
            if (rippleDrawable2 == null) {
                rippleDrawable = null;
            }
            Drawable drawable2 = this.colorBackgroundDrawable;
            if (drawable2 != null) {
                drawable = drawable2;
            }
            drawable.setCallback(rippleDrawable);
        } else {
            Drawable drawable3 = this.colorBackgroundDrawable;
            rippleDrawable = drawable3;
            if (drawable3 == null) {
                rippleDrawable = null;
            }
        }
        setBackground(rippleDrawable);
    }

    public final void setColor(int i) {
        Drawable drawable = this.colorBackgroundDrawable;
        Drawable drawable2 = drawable;
        if (drawable == null) {
            drawable2 = null;
        }
        drawable2.mutate().setTint(i);
        this.paintColor = i;
    }

    @Override // com.android.systemui.qs.tileimpl.HeightOverrideable
    public void setHeightOverride(int i) {
        if (this.heightOverride == i) {
            return;
        }
        this.heightOverride = i;
        updateHeight();
    }

    public final void setLabelColor(int i) {
        TextView textView = this.label;
        TextView textView2 = textView;
        if (textView == null) {
            textView2 = null;
        }
        textView2.setTextColor(i);
    }

    @Override // com.android.systemui.plugins.qs.QSTileView
    public void setPosition(int i) {
        this._position = i;
    }

    public final void setQsLogger(QSLogger qSLogger) {
        this.mQsLogger = qSLogger;
    }

    public final void setSecondaryLabel(TextView textView) {
        this.secondaryLabel = textView;
    }

    public final void setSecondaryLabelColor(int i) {
        getSecondaryLabel().setTextColor(i);
    }

    @Override // com.android.systemui.animation.LaunchableView
    public void setShouldBlockVisibilityChanges(boolean z) {
        this.launchableViewDelegate.setShouldBlockVisibilityChanges(z);
    }

    public final void setShowRippleEffect(boolean z) {
        this.showRippleEffect = z;
    }

    public final void setSideView(ViewGroup viewGroup) {
        this.sideView = viewGroup;
    }

    @Override // com.android.systemui.qs.tileimpl.HeightOverrideable
    public void setSquishinessFraction(float f) {
        if (this.squishinessFraction == f) {
            return;
        }
        this.squishinessFraction = f;
        updateHeight();
    }

    @Override // android.view.View
    public void setVisibility(int i) {
        this.launchableViewDelegate.setVisibility(i);
    }

    @Override // android.view.View
    public String toString() {
        StringBuilder sb = new StringBuilder(getClass().getSimpleName());
        sb.append('[');
        int[] iArr = this.locInScreen;
        int i = iArr[0];
        int i2 = iArr[1];
        sb.append("locInScreen=(" + i + ", " + i2 + ")");
        QSIconView qSIconView = this._icon;
        StringBuilder sb2 = new StringBuilder();
        sb2.append(", iconView=");
        sb2.append(qSIconView);
        sb.append(sb2.toString());
        boolean z = this.tileState;
        sb.append(", tileState=" + z);
        sb.append("]");
        return sb.toString();
    }

    @Override // com.android.systemui.plugins.qs.QSTileView
    public View updateAccessibilityOrder(View view) {
        setAccessibilityTraversalAfter(view != null ? view.getId() : 0);
        return this;
    }

    public final void updateHeight() {
        int heightOverride = getHeightOverride() != -1 ? getHeightOverride() : getMeasuredHeight();
        setBottom(getTop() + ((int) (heightOverride * QSTileViewImplKt.constrainSquishiness(getSquishinessFraction()))));
        setScrollY((heightOverride - getHeight()) / 2);
    }

    public final void updateResources() {
        TextView textView = this.label;
        TextView textView2 = textView;
        if (textView == null) {
            textView2 = null;
        }
        int i = R$dimen.qs_tile_text_size;
        FontSizeUtils.updateFontSize(textView2, i);
        FontSizeUtils.updateFontSize(getSecondaryLabel(), i);
        int dimensionPixelSize = getContext().getResources().getDimensionPixelSize(R$dimen.qs_icon_size);
        ViewGroup.LayoutParams layoutParams = this._icon.getLayoutParams();
        layoutParams.height = dimensionPixelSize;
        layoutParams.width = dimensionPixelSize;
        int dimensionPixelSize2 = getResources().getDimensionPixelSize(R$dimen.qs_tile_padding);
        setPaddingRelative(getResources().getDimensionPixelSize(R$dimen.qs_tile_start_padding), dimensionPixelSize2, dimensionPixelSize2, dimensionPixelSize2);
        int dimensionPixelSize3 = getResources().getDimensionPixelSize(R$dimen.qs_label_container_margin);
        IgnorableChildLinearLayout ignorableChildLinearLayout = this.labelContainer;
        IgnorableChildLinearLayout ignorableChildLinearLayout2 = ignorableChildLinearLayout;
        if (ignorableChildLinearLayout == null) {
            ignorableChildLinearLayout2 = null;
        }
        ((ViewGroup.MarginLayoutParams) ignorableChildLinearLayout2.getLayoutParams()).setMarginStart(dimensionPixelSize3);
        ((ViewGroup.MarginLayoutParams) getSideView().getLayoutParams()).setMarginStart(dimensionPixelSize3);
        ImageView imageView = this.chevronView;
        ImageView imageView2 = imageView;
        if (imageView == null) {
            imageView2 = null;
        }
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) imageView2.getLayoutParams();
        marginLayoutParams.height = dimensionPixelSize;
        marginLayoutParams.width = dimensionPixelSize;
        int dimensionPixelSize4 = getResources().getDimensionPixelSize(R$dimen.qs_drawable_end_margin);
        ImageView imageView3 = this.customDrawableView;
        if (imageView3 == null) {
            imageView3 = null;
        }
        ViewGroup.MarginLayoutParams marginLayoutParams2 = (ViewGroup.MarginLayoutParams) imageView3.getLayoutParams();
        marginLayoutParams2.height = dimensionPixelSize;
        marginLayoutParams2.setMarginEnd(dimensionPixelSize4);
    }
}