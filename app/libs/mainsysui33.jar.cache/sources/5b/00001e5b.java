package com.android.systemui.navigationbar;

import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.TimeInterpolator;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.inputmethodservice.InputMethodService;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.ContextThemeWrapper;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.WindowManagerGlobal;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.FrameLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.android.internal.annotations.VisibleForTesting;
import com.android.settingslib.Utils;
import com.android.settingslib.widget.ActionBarShadowController;
import com.android.systemui.Dependency;
import com.android.systemui.Gefingerpoken;
import com.android.systemui.R$attr;
import com.android.systemui.R$dimen;
import com.android.systemui.R$drawable;
import com.android.systemui.R$id;
import com.android.systemui.R$layout;
import com.android.systemui.R$string;
import com.android.systemui.R$style;
import com.android.systemui.accessibility.SystemActions$$ExternalSyntheticLambda4;
import com.android.systemui.animation.Interpolators;
import com.android.systemui.model.SysUiState;
import com.android.systemui.navigationbar.buttons.ButtonDispatcher;
import com.android.systemui.navigationbar.buttons.ContextualButton;
import com.android.systemui.navigationbar.buttons.ContextualButtonGroup;
import com.android.systemui.navigationbar.buttons.DeadZone;
import com.android.systemui.navigationbar.buttons.KeyButtonDrawable;
import com.android.systemui.navigationbar.buttons.NearestTouchFrame;
import com.android.systemui.navigationbar.buttons.RotationContextButton;
import com.android.systemui.navigationbar.gestural.EdgeBackGestureHandler;
import com.android.systemui.plugins.BcSmartspaceDataPlugin;
import com.android.systemui.recents.Recents;
import com.android.systemui.shade.NotificationPanelViewController;
import com.android.systemui.shared.rotation.FloatingRotationButton;
import com.android.systemui.shared.rotation.RotationButton;
import com.android.systemui.shared.rotation.RotationButtonController;
import com.android.systemui.shared.system.ActivityManagerWrapper;
import com.android.systemui.shared.system.QuickStepContract;
import com.android.systemui.statusbar.phone.AutoHideController;
import com.android.systemui.statusbar.phone.CentralSurfaces;
import com.android.systemui.statusbar.phone.LightBarTransitionsController;
import com.android.systemui.tuner.TunerService;
import com.android.wm.shell.back.BackAnimation;
import com.android.wm.shell.pip.Pip;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import java.util.function.Supplier;

/* loaded from: mainsysui33.jar:com/android/systemui/navigationbar/NavigationBarView.class */
public class NavigationBarView extends FrameLayout implements TunerService.Tunable {
    public AutoHideController mAutoHideController;
    public KeyButtonDrawable mBackIcon;
    public NavigationBarTransitions mBarTransitions;
    public Executor mBgExecutor;
    public final SparseArray<ButtonDispatcher> mButtonDispatchers;
    public Configuration mConfiguration;
    public final ContextualButtonGroup mContextualButtonGroup;
    public int mCurrentRotation;
    public View mCurrentView;
    public KeyButtonDrawable mCursorLeftIcon;
    public KeyButtonDrawable mCursorRightIcon;
    public int mDarkIconColor;
    public final DeadZone mDeadZone;
    public int mDisabledFlags;
    public KeyButtonDrawable mDockedIcon;
    public final Consumer<Boolean> mDockedListener;
    public boolean mDockedStackExists;
    public EdgeBackGestureHandler mEdgeBackGestureHandler;
    public FloatingRotationButton mFloatingRotationButton;
    public KeyButtonDrawable mHomeDefaultIcon;
    public View mHorizontal;
    public final boolean mImeCanRenderGesturalNavButtons;
    public boolean mImeDrawsImeNavBar;
    public boolean mImeVisible;
    public boolean mInCarMode;
    public boolean mIsVertical;
    public boolean mLayoutTransitionsEnabled;
    public Context mLightContext;
    public int mLightIconColor;
    public boolean mLongClickableAccessibilityButton;
    public int mNavBarMode;
    public int mNavigationIconHints;
    public NavigationBarInflaterView mNavigationInflaterView;
    public OnVerticalChangedListener mOnVerticalChangedListener;
    public boolean mOverviewProxyEnabled;
    public NotificationPanelViewController mPanelView;
    public final Consumer<Rect> mPipListener;
    public final View.AccessibilityDelegate mQuickStepAccessibilityDelegate;
    public KeyButtonDrawable mRecentIcon;
    public Optional<Recents> mRecentsOptional;
    public RotationButtonController mRotationButtonController;
    public final RotationButton.RotationButtonUpdatesCallback mRotationButtonListener;
    public RotationContextButton mRotationContextButton;
    public boolean mScreenOn;
    public ScreenPinningNotify mScreenPinningNotify;
    public boolean mShowCursorKeys;
    public boolean mShowSwipeUpUi;
    public Configuration mTmpLastConfiguration;
    public Gefingerpoken mTouchHandler;
    public final NavTransitionListener mTransitionListener;
    public UpdateActiveTouchRegionsCallback mUpdateActiveTouchRegionsCallback;
    public boolean mUseCarModeUi;
    public View mVertical;
    public boolean mWakeAndUnlocking;

    /* loaded from: mainsysui33.jar:com/android/systemui/navigationbar/NavigationBarView$NavTransitionListener.class */
    public class NavTransitionListener implements LayoutTransition.TransitionListener {
        public boolean mBackTransitioning;
        public long mDuration;
        public boolean mHomeAppearing;
        public TimeInterpolator mInterpolator;
        public long mStartDelay;

        public NavTransitionListener() {
            NavigationBarView.this = r4;
        }

        @Override // android.animation.LayoutTransition.TransitionListener
        public void endTransition(LayoutTransition layoutTransition, ViewGroup viewGroup, View view, int i) {
            if (view.getId() == R$id.back) {
                this.mBackTransitioning = false;
            } else if (view.getId() == R$id.home && i == 2) {
                this.mHomeAppearing = false;
            }
        }

        public void onBackAltCleared() {
            ButtonDispatcher backButton = NavigationBarView.this.getBackButton();
            if (!this.mBackTransitioning && backButton.getVisibility() == 0 && this.mHomeAppearing && NavigationBarView.this.getHomeButton().getAlpha() == ActionBarShadowController.ELEVATION_LOW) {
                NavigationBarView.this.getBackButton().setAlpha(ActionBarShadowController.ELEVATION_LOW);
                ObjectAnimator ofFloat = ObjectAnimator.ofFloat(backButton, "alpha", ActionBarShadowController.ELEVATION_LOW, 1.0f);
                ofFloat.setStartDelay(this.mStartDelay);
                ofFloat.setDuration(this.mDuration);
                ofFloat.setInterpolator(this.mInterpolator);
                ofFloat.start();
            }
        }

        @Override // android.animation.LayoutTransition.TransitionListener
        public void startTransition(LayoutTransition layoutTransition, ViewGroup viewGroup, View view, int i) {
            if (view.getId() == R$id.back) {
                this.mBackTransitioning = true;
            } else if (view.getId() == R$id.home && i == 2) {
                this.mHomeAppearing = true;
                this.mStartDelay = layoutTransition.getStartDelay(i);
                this.mDuration = layoutTransition.getDuration(i);
                this.mInterpolator = layoutTransition.getInterpolator(i);
            }
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/navigationbar/NavigationBarView$OnVerticalChangedListener.class */
    public interface OnVerticalChangedListener {
        void onVerticalChanged(boolean z);
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/navigationbar/NavigationBarView$UpdateActiveTouchRegionsCallback.class */
    public interface UpdateActiveTouchRegionsCallback {
        void update();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.navigationbar.NavigationBarView$$ExternalSyntheticLambda3.run():void] */
    public static /* synthetic */ void $r8$lambda$56ojW3c5LJwdHOvcWSEzE_2XjjY(NavigationBarView navigationBarView, Boolean bool) {
        navigationBarView.lambda$new$2(bool);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.navigationbar.NavigationBarView$$ExternalSyntheticLambda5.run():void] */
    public static /* synthetic */ void $r8$lambda$8r8aKGw0qMFO0OJxXi4G1K9aKnA(NavigationBarView navigationBarView) {
        navigationBarView.lambda$updateStates$1();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.navigationbar.NavigationBarView$$ExternalSyntheticLambda4.run():void] */
    /* renamed from: $r8$lambda$H4L2I-sTKOtkeZSXWngcOalwXRM */
    public static /* synthetic */ void m3437$r8$lambda$H4L2IsTKOtkeZSXWngcOalwXRM(NavigationBarView navigationBarView, Rect rect) {
        navigationBarView.lambda$new$4(rect);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.navigationbar.NavigationBarView$$ExternalSyntheticLambda0.accept(java.lang.Object):void] */
    public static /* synthetic */ void $r8$lambda$K9iBItLmpyUq891jn6z1ibfOfGw(NavigationBarView navigationBarView, Boolean bool) {
        navigationBarView.lambda$new$3(bool);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.navigationbar.NavigationBarView$$ExternalSyntheticLambda2.get():java.lang.Object] */
    public static /* synthetic */ Integer $r8$lambda$leiuZT0seiNNtFIgwSqw3n2zunE(NavigationBarView navigationBarView) {
        return navigationBarView.lambda$new$0();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.navigationbar.NavigationBarView$$ExternalSyntheticLambda1.accept(java.lang.Object):void] */
    /* renamed from: $r8$lambda$qLKnAz5yhAEXlZG-5yDplyfZycQ */
    public static /* synthetic */ void m3438$r8$lambda$qLKnAz5yhAEXlZG5yDplyfZycQ(NavigationBarView navigationBarView, Rect rect) {
        navigationBarView.lambda$new$5(rect);
    }

    public NavigationBarView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mCurrentView = null;
        this.mCurrentRotation = -1;
        this.mDisabledFlags = 0;
        this.mNavigationIconHints = 0;
        this.mTransitionListener = new NavTransitionListener();
        this.mLayoutTransitionsEnabled = true;
        this.mUseCarModeUi = false;
        this.mInCarMode = false;
        this.mScreenOn = true;
        SparseArray<ButtonDispatcher> sparseArray = new SparseArray<>();
        this.mButtonDispatchers = sparseArray;
        this.mRecentsOptional = Optional.empty();
        this.mImeCanRenderGesturalNavButtons = InputMethodService.canImeRenderGesturalNavButtons();
        this.mQuickStepAccessibilityDelegate = new View.AccessibilityDelegate() { // from class: com.android.systemui.navigationbar.NavigationBarView.1
            public AccessibilityNodeInfo.AccessibilityAction mToggleOverviewAction;

            {
                NavigationBarView.this = this;
            }

            @Override // android.view.View.AccessibilityDelegate
            public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
                super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
                if (this.mToggleOverviewAction == null) {
                    this.mToggleOverviewAction = new AccessibilityNodeInfo.AccessibilityAction(R$id.action_toggle_overview, NavigationBarView.this.getContext().getString(R$string.quick_step_accessibility_toggle_overview));
                }
                accessibilityNodeInfo.addAction(this.mToggleOverviewAction);
            }

            @Override // android.view.View.AccessibilityDelegate
            public boolean performAccessibilityAction(View view, int i, Bundle bundle) {
                if (i == R$id.action_toggle_overview) {
                    NavigationBarView.this.mRecentsOptional.ifPresent(new SystemActions$$ExternalSyntheticLambda4());
                    return true;
                }
                return super.performAccessibilityAction(view, i, bundle);
            }
        };
        this.mRotationButtonListener = new RotationButton.RotationButtonUpdatesCallback() { // from class: com.android.systemui.navigationbar.NavigationBarView.2
            {
                NavigationBarView.this = this;
            }

            public void onPositionChanged() {
                NavigationBarView.this.notifyActiveTouchRegions();
            }

            public void onVisibilityChanged(boolean z) {
                if (z && NavigationBarView.this.mAutoHideController != null) {
                    NavigationBarView.this.mAutoHideController.touchAutoHide();
                }
                NavigationBarView.this.notifyActiveTouchRegions();
            }
        };
        this.mDockedListener = new Consumer() { // from class: com.android.systemui.navigationbar.NavigationBarView$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                NavigationBarView.$r8$lambda$K9iBItLmpyUq891jn6z1ibfOfGw(NavigationBarView.this, (Boolean) obj);
            }
        };
        this.mPipListener = new Consumer() { // from class: com.android.systemui.navigationbar.NavigationBarView$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                NavigationBarView.m3438$r8$lambda$qLKnAz5yhAEXlZG5yDplyfZycQ(NavigationBarView.this, (Rect) obj);
            }
        };
        ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(context, Utils.getThemeAttr(context, R$attr.darkIconTheme));
        ContextThemeWrapper contextThemeWrapper2 = new ContextThemeWrapper(context, Utils.getThemeAttr(context, R$attr.lightIconTheme));
        this.mLightContext = contextThemeWrapper2;
        int i = R$attr.singleToneColor;
        this.mLightIconColor = Utils.getColorAttrDefaultColor(contextThemeWrapper2, i);
        this.mDarkIconColor = Utils.getColorAttrDefaultColor(contextThemeWrapper, i);
        this.mIsVertical = false;
        this.mLongClickableAccessibilityButton = false;
        int i2 = R$id.menu_container;
        ContextualButtonGroup contextualButtonGroup = new ContextualButtonGroup(i2);
        this.mContextualButtonGroup = contextualButtonGroup;
        int i3 = R$id.ime_switcher;
        ContextualButton contextualButton = new ContextualButton(i3, this.mLightContext, R$drawable.ic_ime_switcher_default);
        int i4 = R$id.accessibility_button;
        ContextualButton contextualButton2 = new ContextualButton(i4, this.mLightContext, R$drawable.ic_sysbar_accessibility_button);
        int i5 = R$id.dpad_left;
        ContextualButton contextualButton3 = new ContextualButton(i5, this.mLightContext, R$drawable.ic_chevron_start);
        int i6 = R$id.dpad_right;
        ContextualButton contextualButton4 = new ContextualButton(i6, this.mLightContext, R$drawable.ic_chevron_end);
        contextualButtonGroup.addButton(contextualButton);
        contextualButtonGroup.addButton(contextualButton2);
        int i7 = R$id.rotate_suggestion;
        Context context2 = this.mLightContext;
        int i8 = R$drawable.ic_sysbar_rotate_button_ccw_start_0;
        this.mRotationContextButton = new RotationContextButton(i7, context2, i8);
        this.mFloatingRotationButton = new FloatingRotationButton(((FrameLayout) this).mContext, R$string.accessibility_rotate_button, R$layout.rotate_suggestion, i7, R$dimen.floating_rotation_button_min_margin, R$dimen.rounded_corner_content_padding, R$dimen.floating_rotation_button_taskbar_left_margin, R$dimen.floating_rotation_button_taskbar_bottom_margin, R$dimen.floating_rotation_button_diameter, R$dimen.key_button_ripple_max_width);
        this.mRotationButtonController = new RotationButtonController(this.mLightContext, this.mLightIconColor, this.mDarkIconColor, i8, R$drawable.ic_sysbar_rotate_button_ccw_start_90, R$drawable.ic_sysbar_rotate_button_cw_start_0, R$drawable.ic_sysbar_rotate_button_cw_start_90, new Supplier() { // from class: com.android.systemui.navigationbar.NavigationBarView$$ExternalSyntheticLambda2
            @Override // java.util.function.Supplier
            public final Object get() {
                return NavigationBarView.$r8$lambda$leiuZT0seiNNtFIgwSqw3n2zunE(NavigationBarView.this);
            }
        });
        this.mConfiguration = new Configuration();
        this.mTmpLastConfiguration = new Configuration();
        this.mConfiguration.updateFrom(context.getResources().getConfiguration());
        this.mScreenPinningNotify = new ScreenPinningNotify(((FrameLayout) this).mContext);
        int i9 = R$id.back;
        sparseArray.put(i9, new ButtonDispatcher(i9));
        int i10 = R$id.home;
        sparseArray.put(i10, new ButtonDispatcher(i10));
        int i11 = R$id.home_handle;
        sparseArray.put(i11, new ButtonDispatcher(i11));
        int i12 = R$id.recent_apps;
        sparseArray.put(i12, new ButtonDispatcher(i12));
        sparseArray.put(i3, contextualButton);
        sparseArray.put(i4, contextualButton2);
        sparseArray.put(i2, contextualButtonGroup);
        sparseArray.put(i5, contextualButton3);
        sparseArray.put(i6, contextualButton4);
        this.mDeadZone = new DeadZone(this);
    }

    public static void dumpButton(PrintWriter printWriter, String str, ButtonDispatcher buttonDispatcher) {
        printWriter.print("      " + str + ": ");
        if (buttonDispatcher == null) {
            printWriter.print("null");
        } else {
            printWriter.print(visibilityToString(buttonDispatcher.getVisibility()) + " alpha=" + buttonDispatcher.getAlpha());
        }
        printWriter.println();
    }

    public /* synthetic */ Integer lambda$new$0() {
        return Integer.valueOf(this.mCurrentRotation);
    }

    public /* synthetic */ void lambda$new$2(Boolean bool) {
        this.mDockedStackExists = bool.booleanValue();
        updateRecentsIcon();
    }

    public /* synthetic */ void lambda$new$3(final Boolean bool) {
        post(new Runnable() { // from class: com.android.systemui.navigationbar.NavigationBarView$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                NavigationBarView.$r8$lambda$56ojW3c5LJwdHOvcWSEzE_2XjjY(NavigationBarView.this, bool);
            }
        });
    }

    public /* synthetic */ void lambda$new$4(Rect rect) {
        this.mEdgeBackGestureHandler.setPipStashExclusionBounds(rect);
    }

    public /* synthetic */ void lambda$new$5(final Rect rect) {
        post(new Runnable() { // from class: com.android.systemui.navigationbar.NavigationBarView$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                NavigationBarView.m3437$r8$lambda$H4L2IsTKOtkeZSXWngcOalwXRM(NavigationBarView.this, rect);
            }
        });
    }

    public /* synthetic */ void lambda$updateStates$1() {
        setNavBarVirtualKeyHapticFeedbackEnabled(!this.mShowSwipeUpUi);
    }

    public static String visibilityToString(int i) {
        return i != 4 ? i != 8 ? "VISIBLE" : "GONE" : "INVISIBLE";
    }

    public void addPipExclusionBoundsChangeListener(Pip pip) {
        pip.addPipExclusionBoundsChangeListener(this.mPipListener);
    }

    public final int chooseNavigationIconDrawableRes(int i, int i2) {
        if (this.mShowSwipeUpUi) {
            i = i2;
        }
        return i;
    }

    public void dump(PrintWriter printWriter) {
        Rect rect = new Rect();
        Point point = new Point();
        getContextDisplay().getRealSize(point);
        printWriter.println("NavigationBarView:");
        printWriter.println(String.format("      this: " + CentralSurfaces.viewInfo(this) + " " + visibilityToString(getVisibility()), new Object[0]));
        getWindowVisibleDisplayFrame(rect);
        boolean z = rect.right > point.x || rect.bottom > point.y;
        StringBuilder sb = new StringBuilder();
        sb.append("      window: ");
        sb.append(rect.toShortString());
        sb.append(" ");
        sb.append(visibilityToString(getWindowVisibility()));
        sb.append(z ? " OFFSCREEN!" : "");
        printWriter.println(sb.toString());
        printWriter.println(String.format("      mCurrentView: id=%s (%dx%d) %s %f", getResourceName(getCurrentView().getId()), Integer.valueOf(getCurrentView().getWidth()), Integer.valueOf(getCurrentView().getHeight()), visibilityToString(getCurrentView().getVisibility()), Float.valueOf(getCurrentView().getAlpha())));
        printWriter.println(String.format("      disabled=0x%08x vertical=%s darkIntensity=%.2f", Integer.valueOf(this.mDisabledFlags), this.mIsVertical ? "true" : "false", Float.valueOf(getLightTransitionsController().getCurrentDarkIntensity())));
        printWriter.println("    mScreenOn: " + this.mScreenOn);
        dumpButton(printWriter, BcSmartspaceDataPlugin.UI_SURFACE_HOME_SCREEN, getHomeButton());
        dumpButton(printWriter, "handle", getHomeHandle());
        dumpButton(printWriter, "rcnt", getRecentsButton());
        dumpButton(printWriter, "rota", getRotateSuggestionButton());
        dumpButton(printWriter, "a11y", getAccessibilityButton());
        dumpButton(printWriter, "ime", getImeSwitchButton());
        dumpButton(printWriter, "curl", getCursorLeftButton());
        dumpButton(printWriter, "curr", getCursorRightButton());
        NavigationBarInflaterView navigationBarInflaterView = this.mNavigationInflaterView;
        if (navigationBarInflaterView != null) {
            navigationBarInflaterView.dump(printWriter);
        }
        this.mBarTransitions.dump(printWriter);
        this.mContextualButtonGroup.dump(printWriter);
        this.mEdgeBackGestureHandler.dump(printWriter);
    }

    public void forEachView(Consumer<View> consumer) {
        View view = this.mVertical;
        if (view != null) {
            consumer.accept(view);
        }
        View view2 = this.mHorizontal;
        if (view2 != null) {
            consumer.accept(view2);
        }
    }

    public ButtonDispatcher getAccessibilityButton() {
        return this.mButtonDispatchers.get(R$id.accessibility_button);
    }

    public ButtonDispatcher getBackButton() {
        return this.mButtonDispatchers.get(R$id.back);
    }

    public KeyButtonDrawable getBackDrawable() {
        KeyButtonDrawable drawable = getDrawable(getBackDrawableRes());
        orientBackButton(drawable);
        return drawable;
    }

    public int getBackDrawableRes() {
        return chooseNavigationIconDrawableRes(R$drawable.ic_sysbar_back, R$drawable.ic_sysbar_back_quick_step);
    }

    public SparseArray<ButtonDispatcher> getButtonDispatchers() {
        return this.mButtonDispatchers;
    }

    public Map<View, Rect> getButtonTouchRegionCache() {
        return ((NearestTouchFrame) (this.mIsVertical ? this.mNavigationInflaterView.mVertical : this.mNavigationInflaterView.mHorizontal).findViewById(R$id.nav_buttons)).getFullTouchableChildRegions();
    }

    public final Display getContextDisplay() {
        return getContext().getDisplay();
    }

    public View getCurrentView() {
        return this.mCurrentView;
    }

    public ButtonDispatcher getCursorLeftButton() {
        return this.mButtonDispatchers.get(R$id.dpad_left);
    }

    public ButtonDispatcher getCursorRightButton() {
        return this.mButtonDispatchers.get(R$id.dpad_right);
    }

    public final KeyButtonDrawable getDrawable(int i) {
        return KeyButtonDrawable.create(this.mLightContext, this.mLightIconColor, this.mDarkIconColor, i, true, null);
    }

    public FloatingRotationButton getFloatingRotationButton() {
        return this.mFloatingRotationButton;
    }

    public ButtonDispatcher getHomeButton() {
        return this.mButtonDispatchers.get(R$id.home);
    }

    public KeyButtonDrawable getHomeDrawable() {
        KeyButtonDrawable drawable = this.mShowSwipeUpUi ? getDrawable(R$drawable.ic_sysbar_home_quick_step) : getDrawable(R$drawable.ic_sysbar_home);
        orientHomeButton(drawable);
        return drawable;
    }

    public ButtonDispatcher getHomeHandle() {
        return this.mButtonDispatchers.get(R$id.home_handle);
    }

    public ButtonDispatcher getImeSwitchButton() {
        return this.mButtonDispatchers.get(R$id.ime_switcher);
    }

    public LightBarTransitionsController getLightTransitionsController() {
        return this.mBarTransitions.getLightTransitionsController();
    }

    public int getNavBarHeight() {
        return this.mIsVertical ? getResources().getDimensionPixelSize(17105367) : getResources().getDimensionPixelSize(17105365);
    }

    public ButtonDispatcher getRecentsButton() {
        return this.mButtonDispatchers.get(R$id.recent_apps);
    }

    public final String getResourceName(int i) {
        if (i != 0) {
            try {
                return getContext().getResources().getResourceName(i);
            } catch (Resources.NotFoundException e) {
                return "(unknown)";
            }
        }
        return "(null)";
    }

    public RotationContextButton getRotateSuggestionButton() {
        return (RotationContextButton) this.mButtonDispatchers.get(R$id.rotate_suggestion);
    }

    public RotationButtonController getRotationButtonController() {
        return this.mRotationButtonController;
    }

    public boolean isImeRenderingNavButtons() {
        return this.mImeDrawsImeNavBar && this.mImeCanRenderGesturalNavButtons && (this.mNavigationIconHints & 2) != 0;
    }

    public boolean isOverviewEnabled() {
        return (this.mDisabledFlags & 16777216) == 0;
    }

    public final boolean isQuickStepSwipeUpEnabled() {
        return this.mShowSwipeUpUi && isOverviewEnabled();
    }

    @VisibleForTesting
    public boolean isRecentsButtonDisabled() {
        return (!this.mUseCarModeUi && isOverviewEnabled() && getContext().getDisplayId() == 0) ? false : true;
    }

    public boolean isRecentsButtonVisible() {
        return getRecentsButton().getVisibility() == 0;
    }

    public boolean needsReorient(int i) {
        return this.mCurrentRotation != i;
    }

    public void notifyActiveTouchRegions() {
        UpdateActiveTouchRegionsCallback updateActiveTouchRegionsCallback = this.mUpdateActiveTouchRegionsCallback;
        if (updateActiveTouchRegionsCallback != null) {
            updateActiveTouchRegionsCallback.update();
        }
    }

    public final void notifyVerticalChangedListener(boolean z) {
        OnVerticalChangedListener onVerticalChangedListener = this.mOnVerticalChangedListener;
        if (onVerticalChangedListener != null) {
            onVerticalChangedListener.onVerticalChanged(z);
        }
    }

    @Override // android.view.View
    public WindowInsets onApplyWindowInsets(WindowInsets windowInsets) {
        int systemWindowInsetLeft = windowInsets.getSystemWindowInsetLeft();
        int systemWindowInsetRight = windowInsets.getSystemWindowInsetRight();
        setPadding(systemWindowInsetLeft, windowInsets.getSystemWindowInsetTop(), systemWindowInsetRight, windowInsets.getSystemWindowInsetBottom());
        this.mEdgeBackGestureHandler.setInsets(systemWindowInsetLeft, systemWindowInsetRight);
        boolean z = !QuickStepContract.isGesturalMode(this.mNavBarMode) || windowInsets.getSystemWindowInsetBottom() == 0;
        setClipChildren(z);
        setClipToPadding(z);
        return super.onApplyWindowInsets(windowInsets);
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mEdgeBackGestureHandler.onNavBarAttached();
        requestApplyInsets();
        reorient();
        ((TunerService) Dependency.get(TunerService.class)).addTunable(this, new String[]{"customsystem:navigation_bar_menu_arrow_keys"});
        RotationButtonController rotationButtonController = this.mRotationButtonController;
        if (rotationButtonController != null) {
            rotationButtonController.registerListeners();
        }
        updateNavButtonIcons();
    }

    @Override // android.view.View
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this.mTmpLastConfiguration.updateFrom(this.mConfiguration);
        this.mFloatingRotationButton.onConfigurationChanged(this.mConfiguration.updateFrom(configuration));
        boolean updateCarMode = updateCarMode();
        updateIcons(this.mTmpLastConfiguration);
        updateRecentsIcon();
        updateCurrentRotation();
        this.mEdgeBackGestureHandler.onConfigurationChanged(this.mConfiguration);
        if (!updateCarMode) {
            Configuration configuration2 = this.mTmpLastConfiguration;
            if (configuration2.densityDpi == this.mConfiguration.densityDpi && configuration2.getLayoutDirection() == this.mConfiguration.getLayoutDirection()) {
                return;
            }
        }
        updateNavButtonIcons();
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        for (int i = 0; i < this.mButtonDispatchers.size(); i++) {
            this.mButtonDispatchers.valueAt(i).onDestroy();
        }
        if (this.mRotationButtonController != null) {
            this.mFloatingRotationButton.hide();
            this.mRotationButtonController.unregisterListeners();
        }
        this.mEdgeBackGestureHandler.onNavBarDetached();
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        this.mDeadZone.onDraw(canvas);
        super.onDraw(canvas);
    }

    @Override // android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        NavigationBarInflaterView navigationBarInflaterView = (NavigationBarInflaterView) findViewById(R$id.navigation_inflater);
        this.mNavigationInflaterView = navigationBarInflaterView;
        navigationBarInflaterView.setButtonDispatchers(this.mButtonDispatchers);
        updateOrientationViews();
        reloadNavIcons();
    }

    public void onImeVisibilityChanged(boolean z) {
        if (!z) {
            this.mTransitionListener.onBackAltCleared();
        }
        this.mImeVisible = z;
        this.mRotationButtonController.getRotationButton().setCanShowRotationButton(!z);
    }

    @Override // android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        return this.mTouchHandler.onInterceptTouchEvent(motionEvent) || super.onInterceptTouchEvent(motionEvent);
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        notifyActiveTouchRegions();
    }

    @Override // android.widget.FrameLayout, android.view.View
    public void onMeasure(int i, int i2) {
        int size = View.MeasureSpec.getSize(i);
        int size2 = View.MeasureSpec.getSize(i2);
        boolean z = size > 0 && size2 > size && !QuickStepContract.isGesturalMode(this.mNavBarMode);
        if (z != this.mIsVertical) {
            this.mIsVertical = z;
            reorient();
            notifyVerticalChangedListener(z);
        }
        if (QuickStepContract.isGesturalMode(this.mNavBarMode)) {
            this.mBarTransitions.setBackgroundFrame(new Rect(0, getResources().getDimensionPixelSize(17105361) - (this.mIsVertical ? getResources().getDimensionPixelSize(17105367) : getResources().getDimensionPixelSize(17105365)), size, size2));
        } else {
            this.mBarTransitions.setBackgroundFrame(null);
        }
        super.onMeasure(i, i2);
    }

    public void onOverviewProxyConnectionChange(boolean z) {
        this.mOverviewProxyEnabled = z;
    }

    public void onScreenStateChanged(boolean z) {
        this.mScreenOn = z;
    }

    public void onStatusBarPanelStateChanged() {
        updateSlippery();
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        this.mTouchHandler.onTouchEvent(motionEvent);
        return super.onTouchEvent(motionEvent);
    }

    public void onTuningChanged(String str, String str2) {
        if ("customsystem:navigation_bar_menu_arrow_keys".equals(str)) {
            this.mShowCursorKeys = TunerService.parseIntegerSwitch(str2, false);
            setNavigationIconHints(this.mNavigationIconHints);
        }
    }

    public final void orientBackButton(KeyButtonDrawable keyButtonDrawable) {
        float f;
        boolean z = (this.mNavigationIconHints & 1) != 0;
        boolean z2 = this.mConfiguration.getLayoutDirection() == 1;
        if (z) {
            f = z2 ? 90 : -90;
        } else {
            f = 0.0f;
        }
        if (keyButtonDrawable.getRotation() == f) {
            return;
        }
        if (QuickStepContract.isGesturalMode(this.mNavBarMode)) {
            keyButtonDrawable.setRotation(f);
            return;
        }
        float f2 = 0.0f;
        if (!this.mShowSwipeUpUi) {
            f2 = 0.0f;
            if (!this.mIsVertical) {
                f2 = 0.0f;
                if (z) {
                    f2 = -getResources().getDimension(R$dimen.navbar_back_button_ime_offset);
                }
            }
        }
        ObjectAnimator ofPropertyValuesHolder = ObjectAnimator.ofPropertyValuesHolder(keyButtonDrawable, PropertyValuesHolder.ofFloat(KeyButtonDrawable.KEY_DRAWABLE_ROTATE, f), PropertyValuesHolder.ofFloat(KeyButtonDrawable.KEY_DRAWABLE_TRANSLATE_Y, f2));
        ofPropertyValuesHolder.setInterpolator(Interpolators.FAST_OUT_SLOW_IN);
        ofPropertyValuesHolder.setDuration(200L);
        ofPropertyValuesHolder.start();
    }

    public final void orientHomeButton(KeyButtonDrawable keyButtonDrawable) {
        keyButtonDrawable.setRotation(this.mIsVertical ? 90.0f : 0.0f);
    }

    public void registerBackAnimation(BackAnimation backAnimation) {
        this.mEdgeBackGestureHandler.setBackAnimation(backAnimation);
    }

    public final void reloadNavIcons() {
        updateIcons(Configuration.EMPTY);
    }

    public void removePipExclusionBoundsChangeListener(Pip pip) {
        pip.removePipExclusionBoundsChangeListener(this.mPipListener);
    }

    public void reorient() {
        updateCurrentView();
        ((NavigationBarFrame) getRootView()).setDeadZone(this.mDeadZone);
        this.mBarTransitions.init();
        if (!isLayoutDirectionResolved()) {
            resolveLayoutDirection();
        }
        updateNavButtonIcons();
        getHomeButton().setVertical(this.mIsVertical);
    }

    public final void resetViews() {
        this.mHorizontal.setVisibility(8);
        this.mVertical.setVisibility(8);
    }

    public void setAccessibilityButtonState(boolean z, boolean z2) {
        this.mLongClickableAccessibilityButton = z2;
        getAccessibilityButton().setLongClickable(z2);
        this.mContextualButtonGroup.setButtonVisibility(R$id.accessibility_button, z);
    }

    public void setAutoHideController(AutoHideController autoHideController) {
        this.mAutoHideController = autoHideController;
    }

    public void setBackgroundExecutor(Executor executor) {
        this.mBgExecutor = executor;
    }

    public void setBarTransitions(NavigationBarTransitions navigationBarTransitions) {
        this.mBarTransitions = navigationBarTransitions;
    }

    public void setBehavior(int i) {
        this.mRotationButtonController.onBehaviorChanged(0, i);
    }

    public void setComponents(NotificationPanelViewController notificationPanelViewController) {
        this.mPanelView = notificationPanelViewController;
        updatePanelSystemUiStateFlags();
    }

    public void setComponents(Optional<Recents> optional) {
        this.mRecentsOptional = optional;
    }

    public void setDisabledFlags(int i, SysUiState sysUiState) {
        if (this.mDisabledFlags == i) {
            return;
        }
        boolean isOverviewEnabled = isOverviewEnabled();
        this.mDisabledFlags = i;
        if (!isOverviewEnabled && isOverviewEnabled()) {
            reloadNavIcons();
        }
        updateNavButtonIcons();
        updateSlippery();
        updateDisabledSystemUiStateFlags(sysUiState);
    }

    public void setEdgeBackGestureHandler(EdgeBackGestureHandler edgeBackGestureHandler) {
        this.mEdgeBackGestureHandler = edgeBackGestureHandler;
    }

    @Override // android.view.View
    public void setLayoutDirection(int i) {
        reloadNavIcons();
        super.setLayoutDirection(i);
    }

    public void setLayoutTransitionsEnabled(boolean z) {
        this.mLayoutTransitionsEnabled = z;
        updateLayoutTransitionsEnabled();
    }

    public void setNavBarMode(int i, boolean z) {
        this.mNavBarMode = i;
        this.mImeDrawsImeNavBar = z;
        this.mBarTransitions.onNavigationModeChanged(i);
        this.mEdgeBackGestureHandler.onNavigationModeChanged(this.mNavBarMode);
        this.mRotationButtonController.onNavigationModeChanged(this.mNavBarMode);
        updateRotationButton();
    }

    public final void setNavBarVirtualKeyHapticFeedbackEnabled(boolean z) {
        try {
            WindowManagerGlobal.getWindowManagerService().setNavBarVirtualKeyHapticFeedbackEnabled(z);
        } catch (RemoteException e) {
            Log.w("NavBarView", "Failed to enable or disable navigation bar button haptics: ", e);
        }
    }

    public void setNavigationIconHints(int i) {
        if (i == this.mNavigationIconHints) {
            return;
        }
        this.mNavigationIconHints = i;
        updateNavButtonIcons();
    }

    public void setOnVerticalChangedListener(OnVerticalChangedListener onVerticalChangedListener) {
        this.mOnVerticalChangedListener = onVerticalChangedListener;
        notifyVerticalChangedListener(this.mIsVertical);
    }

    public void setShouldShowSwipeUpUi(boolean z) {
        this.mShowSwipeUpUi = z;
        updateStates();
    }

    public void setSlippery(boolean z) {
        setWindowFlag(536870912, z);
    }

    public void setTouchHandler(Gefingerpoken gefingerpoken) {
        this.mTouchHandler = gefingerpoken;
    }

    public void setUpdateActiveTouchRegionsCallback(UpdateActiveTouchRegionsCallback updateActiveTouchRegionsCallback) {
        this.mUpdateActiveTouchRegionsCallback = updateActiveTouchRegionsCallback;
        notifyActiveTouchRegions();
    }

    public final void setUseFadingAnimations(boolean z) {
        WindowManager.LayoutParams layoutParams = (WindowManager.LayoutParams) ((ViewGroup) getParent()).getLayoutParams();
        if (layoutParams != null) {
            boolean z2 = layoutParams.windowAnimations != 0;
            if (!z2 && z) {
                layoutParams.windowAnimations = R$style.Animation_NavigationBarFadeIn;
            } else if (!z2 || z) {
                return;
            } else {
                layoutParams.windowAnimations = 0;
            }
            ((WindowManager) getContext().getSystemService(WindowManager.class)).updateViewLayout((View) getParent(), layoutParams);
        }
    }

    public void setWakeAndUnlocking(boolean z) {
        setUseFadingAnimations(z);
        this.mWakeAndUnlocking = z;
        updateLayoutTransitionsEnabled();
    }

    public final void setWindowFlag(int i, boolean z) {
        WindowManager.LayoutParams layoutParams;
        ViewGroup viewGroup = (ViewGroup) getParent();
        if (viewGroup == null || (layoutParams = (WindowManager.LayoutParams) viewGroup.getLayoutParams()) == null) {
            return;
        }
        int i2 = layoutParams.flags;
        if (z == ((i2 & i) != 0)) {
            return;
        }
        if (z) {
            layoutParams.flags = i | i2;
        } else {
            layoutParams.flags = (i ^ (-1)) & i2;
        }
        ((WindowManager) getContext().getSystemService(WindowManager.class)).updateViewLayout(viewGroup, layoutParams);
    }

    public void setWindowVisible(boolean z) {
        this.mRotationButtonController.onNavigationBarWindowVisibilityChange(z);
    }

    public void showPinningEnterExitToast(boolean z) {
        if (z) {
            this.mScreenPinningNotify.showPinningStartToast();
        } else {
            this.mScreenPinningNotify.showPinningExitToast();
        }
    }

    public void showPinningEscapeToast() {
        this.mScreenPinningNotify.showEscapeToast(this.mNavBarMode == 2, isRecentsButtonVisible());
    }

    public final boolean updateCarMode() {
        Configuration configuration = this.mConfiguration;
        if (configuration != null) {
            boolean z = (configuration.uiMode & 15) == 3;
            if (z != this.mInCarMode) {
                this.mInCarMode = z;
                this.mUseCarModeUi = false;
                return false;
            }
            return false;
        }
        return false;
    }

    public final void updateCurrentRotation() {
        int displayRotation = this.mConfiguration.windowConfiguration.getDisplayRotation();
        if (this.mCurrentRotation == displayRotation) {
            return;
        }
        this.mCurrentRotation = displayRotation;
        NavigationBarInflaterView navigationBarInflaterView = this.mNavigationInflaterView;
        boolean z = true;
        if (displayRotation != 1) {
            z = false;
        }
        navigationBarInflaterView.setAlternativeOrder(z);
        this.mDeadZone.onConfigurationChanged(this.mCurrentRotation);
    }

    public final void updateCurrentView() {
        resetViews();
        View view = this.mIsVertical ? this.mVertical : this.mHorizontal;
        this.mCurrentView = view;
        view.setVisibility(0);
        this.mNavigationInflaterView.setVertical(this.mIsVertical);
        this.mNavigationInflaterView.updateButtonDispatchersCurrentView();
        updateLayoutTransitionsEnabled();
        updateCurrentRotation();
    }

    public void updateDisabledSystemUiStateFlags(SysUiState sysUiState) {
        sysUiState.setFlag(1, ActivityManagerWrapper.getInstance().isScreenPinningActive()).setFlag(RecyclerView.ViewHolder.FLAG_IGNORE, (this.mDisabledFlags & 16777216) != 0).setFlag(RecyclerView.ViewHolder.FLAG_TMP_DETACHED, (this.mDisabledFlags & 2097152) != 0).setFlag(RecyclerView.ViewHolder.FLAG_ADAPTER_FULLUPDATE, (this.mDisabledFlags & 33554432) != 0).commitUpdate(((FrameLayout) this).mContext.getDisplayId());
    }

    public final void updateIcons(Configuration configuration) {
        int i = configuration.orientation;
        Configuration configuration2 = this.mConfiguration;
        boolean z = true;
        boolean z2 = i != configuration2.orientation;
        boolean z3 = configuration.densityDpi != configuration2.densityDpi;
        if (configuration.getLayoutDirection() == this.mConfiguration.getLayoutDirection()) {
            z = false;
        }
        if (z2 || z3) {
            this.mDockedIcon = getDrawable(R$drawable.ic_sysbar_docked);
            this.mHomeDefaultIcon = getHomeDrawable();
        }
        if (z3 || z) {
            this.mRecentIcon = getDrawable(R$drawable.ic_sysbar_recent);
            this.mCursorLeftIcon = getDrawable(R$drawable.ic_chevron_start);
            this.mCursorRightIcon = getDrawable(R$drawable.ic_chevron_end);
            this.mContextualButtonGroup.updateIcons(this.mLightIconColor, this.mDarkIconColor);
        }
        if (z2 || z3 || z) {
            this.mBackIcon = getBackDrawable();
        }
    }

    public final void updateLayoutTransitionsEnabled() {
        boolean z = !this.mWakeAndUnlocking && this.mLayoutTransitionsEnabled;
        LayoutTransition layoutTransition = ((ViewGroup) getCurrentView().findViewById(R$id.nav_buttons)).getLayoutTransition();
        if (layoutTransition != null) {
            if (z) {
                layoutTransition.enableTransitionType(2);
                layoutTransition.enableTransitionType(3);
                layoutTransition.enableTransitionType(0);
                layoutTransition.enableTransitionType(1);
                return;
            }
            layoutTransition.disableTransitionType(2);
            layoutTransition.disableTransitionType(3);
            layoutTransition.disableTransitionType(0);
            layoutTransition.disableTransitionType(1);
        }
    }

    public void updateNavButtonIcons() {
        boolean z;
        boolean z2;
        LayoutTransition layoutTransition;
        boolean z3 = (this.mNavigationIconHints & 1) != 0;
        KeyButtonDrawable keyButtonDrawable = this.mBackIcon;
        orientBackButton(keyButtonDrawable);
        KeyButtonDrawable keyButtonDrawable2 = this.mHomeDefaultIcon;
        KeyButtonDrawable keyButtonDrawable3 = this.mCursorLeftIcon;
        KeyButtonDrawable keyButtonDrawable4 = this.mCursorRightIcon;
        if (!this.mUseCarModeUi) {
            orientHomeButton(keyButtonDrawable2);
        }
        getHomeButton().setImageDrawable(keyButtonDrawable2);
        getBackButton().setImageDrawable(keyButtonDrawable);
        getCursorLeftButton().setImageDrawable(keyButtonDrawable3);
        getCursorRightButton().setImageDrawable(keyButtonDrawable4);
        updateRecentsIcon();
        boolean z4 = (this.mShowCursorKeys && z3 && (!QuickStepContract.isGesturalMode(this.mNavBarMode) || !this.mImeVisible)) ? false : true;
        this.mContextualButtonGroup.setButtonVisibility(R$id.ime_switcher, !((this.mNavigationIconHints & 4) == 0 || isImeRenderingNavButtons() || !z4));
        this.mBarTransitions.reapplyDarkIntensity();
        boolean z5 = QuickStepContract.isGesturalMode(this.mNavBarMode) || (this.mDisabledFlags & 2097152) != 0;
        boolean isRecentsButtonDisabled = isRecentsButtonDisabled();
        boolean z6 = isRecentsButtonDisabled && (2097152 & this.mDisabledFlags) != 0;
        boolean z7 = (!z3 && (this.mEdgeBackGestureHandler.isHandlingGestures() || (this.mDisabledFlags & 4194304) != 0)) || isImeRenderingNavButtons();
        boolean isScreenPinningActive = ActivityManagerWrapper.getInstance().isScreenPinningActive();
        if (this.mOverviewProxyEnabled) {
            boolean isLegacyMode = isRecentsButtonDisabled | (true ^ QuickStepContract.isLegacyMode(this.mNavBarMode));
            z = z7;
            z2 = z5;
            isRecentsButtonDisabled = isLegacyMode;
            if (isScreenPinningActive) {
                z = z7;
                z2 = z5;
                isRecentsButtonDisabled = isLegacyMode;
                if (!QuickStepContract.isGesturalMode(this.mNavBarMode)) {
                    z = false;
                    z2 = false;
                    isRecentsButtonDisabled = isLegacyMode;
                }
            }
        } else {
            z = z7;
            z2 = z5;
            if (isScreenPinningActive) {
                z = false;
                isRecentsButtonDisabled = false;
                z2 = z5;
            }
        }
        ViewGroup viewGroup = (ViewGroup) getCurrentView().findViewById(R$id.nav_buttons);
        if (viewGroup != null && (layoutTransition = viewGroup.getLayoutTransition()) != null && !layoutTransition.getTransitionListeners().contains(this.mTransitionListener)) {
            layoutTransition.addTransitionListener(this.mTransitionListener);
        }
        getBackButton().setVisibility(z ? 4 : 0);
        getHomeButton().setVisibility(z2 ? 4 : 0);
        getRecentsButton().setVisibility(isRecentsButtonDisabled ? 4 : 0);
        getHomeHandle().setVisibility(z6 ? 4 : 0);
        getCursorLeftButton().setVisibility(z4 ? 4 : 0);
        ButtonDispatcher cursorRightButton = getCursorRightButton();
        int i = 0;
        if (z4) {
            i = 4;
        }
        cursorRightButton.setVisibility(i);
        notifyActiveTouchRegions();
    }

    public final void updateOrientationViews() {
        this.mHorizontal = findViewById(R$id.horizontal);
        this.mVertical = findViewById(R$id.vertical);
        updateCurrentView();
    }

    public final void updatePanelSystemUiStateFlags() {
        NotificationPanelViewController notificationPanelViewController = this.mPanelView;
        if (notificationPanelViewController != null) {
            notificationPanelViewController.updateSystemUiStateFlags();
        }
    }

    public final void updateRecentsIcon() {
        this.mDockedIcon.setRotation((this.mDockedStackExists && this.mIsVertical) ? 90.0f : 0.0f);
        getRecentsButton().setImageDrawable(this.mDockedStackExists ? this.mDockedIcon : this.mRecentIcon);
        this.mBarTransitions.reapplyDarkIntensity();
    }

    public void updateRotationButton() {
        if (QuickStepContract.isGesturalMode(this.mNavBarMode)) {
            ContextualButtonGroup contextualButtonGroup = this.mContextualButtonGroup;
            int i = R$id.rotate_suggestion;
            contextualButtonGroup.removeButton(i);
            this.mButtonDispatchers.remove(i);
            this.mRotationButtonController.setRotationButton(this.mFloatingRotationButton, this.mRotationButtonListener);
        } else {
            ContextualButtonGroup contextualButtonGroup2 = this.mContextualButtonGroup;
            int i2 = R$id.rotate_suggestion;
            if (contextualButtonGroup2.getContextButton(i2) == null) {
                this.mContextualButtonGroup.addButton(this.mRotationContextButton);
                this.mButtonDispatchers.put(i2, this.mRotationContextButton);
                this.mRotationButtonController.setRotationButton(this.mRotationContextButton, this.mRotationButtonListener);
            }
        }
        this.mNavigationInflaterView.setButtonDispatchers(this.mButtonDispatchers);
    }

    public void updateSlippery() {
        NotificationPanelViewController notificationPanelViewController;
        setSlippery((isQuickStepSwipeUpEnabled() && ((notificationPanelViewController = this.mPanelView) == null || !notificationPanelViewController.isFullyExpanded() || this.mPanelView.isCollapsing())) ? false : true);
    }

    public void updateStates() {
        NavigationBarInflaterView navigationBarInflaterView = this.mNavigationInflaterView;
        if (navigationBarInflaterView != null) {
            navigationBarInflaterView.onLikelyDefaultLayoutChange();
        }
        updateSlippery();
        reloadNavIcons();
        updateNavButtonIcons();
        this.mBgExecutor.execute(new Runnable() { // from class: com.android.systemui.navigationbar.NavigationBarView$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                NavigationBarView.$r8$lambda$8r8aKGw0qMFO0OJxXi4G1K9aKnA(NavigationBarView.this);
            }
        });
        getHomeButton().setAccessibilityDelegate(this.mShowSwipeUpUi ? this.mQuickStepAccessibilityDelegate : null);
    }
}