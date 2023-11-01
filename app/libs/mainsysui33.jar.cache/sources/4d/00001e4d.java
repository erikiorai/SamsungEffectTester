package com.android.systemui.navigationbar;

import android.app.ActivityManager;
import android.content.Context;
import android.content.om.IOverlayManager;
import android.content.res.Configuration;
import android.graphics.drawable.Icon;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.provider.Settings;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Space;
import com.android.internal.annotations.VisibleForTesting;
import com.android.systemui.Dependency;
import com.android.systemui.R$id;
import com.android.systemui.R$layout;
import com.android.systemui.R$string;
import com.android.systemui.navigationbar.NavigationModeController;
import com.android.systemui.navigationbar.buttons.ButtonDispatcher;
import com.android.systemui.navigationbar.buttons.KeyButtonView;
import com.android.systemui.navigationbar.buttons.ReverseLinearLayout;
import com.android.systemui.plugins.BcSmartspaceDataPlugin;
import com.android.systemui.recents.OverviewProxyService;
import com.android.systemui.shared.system.QuickStepContract;
import com.android.systemui.tuner.TunerService;
import java.io.PrintWriter;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: mainsysui33.jar:com/android/systemui/navigationbar/NavigationBarInflaterView.class */
public class NavigationBarInflaterView extends FrameLayout implements NavigationModeController.ModeChangedListener, TunerService.Tunable {
    public static AtomicReference<Boolean> mIsHintEnabledRef;
    public boolean mAlternativeOrder;
    @VisibleForTesting
    public SparseArray<ButtonDispatcher> mButtonDispatchers;
    public boolean mCompactLayout;
    public String mCurrentLayout;
    public FrameLayout mHorizontal;
    public boolean mInverseLayout;
    public boolean mIsVertical;
    public LayoutInflater mLandscapeInflater;
    public View mLastLandscape;
    public View mLastPortrait;
    public LayoutInflater mLayoutInflater;
    public int mNavBarMode;
    public OverviewProxyService mOverviewProxyService;
    public FrameLayout mVertical;

    public NavigationBarInflaterView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        boolean z = false;
        this.mNavBarMode = 0;
        createInflaters();
        this.mOverviewProxyService = (OverviewProxyService) Dependency.get(OverviewProxyService.class);
        this.mNavBarMode = ((NavigationModeController) Dependency.get(NavigationModeController.class)).addListener(this);
        this.mCompactLayout = Settings.System.getInt(context.getContentResolver(), "navigation_bar_compact_layout", 0) != 0 ? true : z;
        mIsHintEnabledRef = new AtomicReference<>(Boolean.TRUE);
    }

    public static float convertDpToPx(Context context, float f) {
        return f * context.getResources().getDisplayMetrics().density;
    }

    public static String extractButton(String str) {
        return !str.contains("[") ? str : str.substring(0, str.indexOf("["));
    }

    public static String extractImage(String str) {
        if (str.contains(":")) {
            return str.substring(str.indexOf(":") + 1, str.indexOf(")"));
        }
        return null;
    }

    public static int extractKeycode(String str) {
        if (str.contains("(")) {
            return Integer.parseInt(str.substring(str.indexOf("(") + 1, str.indexOf(":")));
        }
        return 1;
    }

    public static String extractSize(String str) {
        if (str.contains("[")) {
            return str.substring(str.indexOf("[") + 1, str.indexOf("]"));
        }
        return null;
    }

    public final void addAll(ButtonDispatcher buttonDispatcher, ViewGroup viewGroup) {
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            if (viewGroup.getChildAt(i).getId() == buttonDispatcher.getId()) {
                buttonDispatcher.addView(viewGroup.getChildAt(i));
            }
            if (viewGroup.getChildAt(i) instanceof ViewGroup) {
                addAll(buttonDispatcher, (ViewGroup) viewGroup.getChildAt(i));
            }
        }
    }

    public final void addGravitySpacer(LinearLayout linearLayout) {
        linearLayout.addView(new Space(((FrameLayout) this).mContext), new LinearLayout.LayoutParams(0, 0, 1.0f));
    }

    public final void addToDispatchers(View view) {
        SparseArray<ButtonDispatcher> sparseArray = this.mButtonDispatchers;
        if (sparseArray != null) {
            int indexOfKey = sparseArray.indexOfKey(view.getId());
            if (indexOfKey >= 0) {
                this.mButtonDispatchers.valueAt(indexOfKey).addView(view);
            }
            if (view instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) view;
                int childCount = viewGroup.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    addToDispatchers(viewGroup.getChildAt(i));
                }
            }
        }
    }

    public final View applySize(View view, String str, boolean z, boolean z2) {
        ViewGroup.LayoutParams layoutParams;
        String extractSize = extractSize(str);
        if (extractSize == null) {
            return view;
        }
        if (!extractSize.contains("W") && !extractSize.contains("A")) {
            float parseFloat = Float.parseFloat(extractSize);
            view.getLayoutParams().width = (int) (layoutParams.width * parseFloat);
            return view;
        }
        ReverseLinearLayout.ReverseRelativeLayout reverseRelativeLayout = new ReverseLinearLayout.ReverseRelativeLayout(((FrameLayout) this).mContext);
        FrameLayout.LayoutParams layoutParams2 = new FrameLayout.LayoutParams(view.getLayoutParams());
        int i = z ? z2 ? 48 : 80 : z2 ? 8388611 : 8388613;
        if (extractSize.endsWith("WC")) {
            i = 17;
        } else if (extractSize.endsWith("C")) {
            i = 16;
        }
        reverseRelativeLayout.setDefaultGravity(i);
        reverseRelativeLayout.setGravity(i);
        reverseRelativeLayout.addView(view, layoutParams2);
        if (extractSize.contains("W")) {
            reverseRelativeLayout.setLayoutParams(new LinearLayout.LayoutParams(0, -1, Float.parseFloat(extractSize.substring(0, extractSize.indexOf("W")))));
        } else {
            reverseRelativeLayout.setLayoutParams(new LinearLayout.LayoutParams((int) convertDpToPx(((FrameLayout) this).mContext, Float.parseFloat(extractSize.substring(0, extractSize.indexOf("A")))), -1));
        }
        reverseRelativeLayout.setClipChildren(false);
        reverseRelativeLayout.setClipToPadding(false);
        return reverseRelativeLayout;
    }

    public final void clearAllChildren(ViewGroup viewGroup) {
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            ((ViewGroup) viewGroup.getChildAt(i)).removeAllViews();
        }
    }

    public final void clearDispatcherViews() {
        if (this.mButtonDispatchers != null) {
            for (int i = 0; i < this.mButtonDispatchers.size(); i++) {
                this.mButtonDispatchers.valueAt(i).clear();
            }
        }
    }

    public final void clearViews() {
        clearDispatcherViews();
        FrameLayout frameLayout = this.mHorizontal;
        int i = R$id.nav_buttons;
        clearAllChildren((ViewGroup) frameLayout.findViewById(i));
        clearAllChildren((ViewGroup) this.mVertical.findViewById(i));
    }

    @VisibleForTesting
    public void createInflaters() {
        this.mLayoutInflater = LayoutInflater.from(((FrameLayout) this).mContext);
        Configuration configuration = new Configuration();
        configuration.setTo(((FrameLayout) this).mContext.getResources().getConfiguration());
        configuration.orientation = 2;
        this.mLandscapeInflater = LayoutInflater.from(((FrameLayout) this).mContext.createConfigurationContext(configuration));
    }

    public View createView(String str, ViewGroup viewGroup, LayoutInflater layoutInflater) {
        String str2;
        View view;
        String extractButton = extractButton(str);
        if ("left".equals(extractButton)) {
            str2 = extractButton("space");
        } else {
            str2 = extractButton;
            if ("right".equals(extractButton)) {
                str2 = extractButton("menu_ime");
            }
        }
        if (BcSmartspaceDataPlugin.UI_SURFACE_HOME_SCREEN.equals(str2)) {
            view = layoutInflater.inflate(R$layout.home, viewGroup, false);
        } else if ("back".equals(str2)) {
            view = layoutInflater.inflate(R$layout.back, viewGroup, false);
        } else if ("recent".equals(str2)) {
            view = layoutInflater.inflate(R$layout.recent_apps, viewGroup, false);
        } else if ("menu_ime".equals(str2)) {
            view = layoutInflater.inflate(R$layout.menu_ime, viewGroup, false);
        } else if ("space".equals(str2)) {
            view = layoutInflater.inflate(R$layout.nav_key_space, viewGroup, false);
        } else if ("clipboard".equals(str2)) {
            view = layoutInflater.inflate(R$layout.clipboard, viewGroup, false);
        } else if ("contextual".equals(str2)) {
            view = layoutInflater.inflate(R$layout.contextual, viewGroup, false);
        } else if ("home_handle".equals(str2)) {
            view = layoutInflater.inflate(R$layout.home_handle, viewGroup, false);
        } else if ("ime_switcher".equals(str2)) {
            view = layoutInflater.inflate(R$layout.ime_switcher, viewGroup, false);
        } else if (str2.startsWith("key")) {
            String extractImage = extractImage(str2);
            int extractKeycode = extractKeycode(str2);
            view = layoutInflater.inflate(R$layout.custom_key, viewGroup, false);
            KeyButtonView keyButtonView = (KeyButtonView) view;
            keyButtonView.setCode(extractKeycode);
            if (extractImage != null) {
                if (extractImage.contains(":")) {
                    keyButtonView.loadAsync(Icon.createWithContentUri(extractImage));
                } else if (extractImage.contains("/")) {
                    int indexOf = extractImage.indexOf(47);
                    keyButtonView.loadAsync(Icon.createWithResource(extractImage.substring(0, indexOf), Integer.parseInt(extractImage.substring(indexOf + 1))));
                }
            }
        } else {
            view = null;
        }
        return view;
    }

    public void dump(PrintWriter printWriter) {
        printWriter.println("NavigationBarInflaterView");
        printWriter.println("  mCurrentLayout: " + this.mCurrentLayout);
    }

    public String getDefaultLayout() {
        int i = QuickStepContract.isGesturalMode(this.mNavBarMode) ? R$string.config_navBarLayoutHandle : this.mOverviewProxyService.shouldShowSwipeUpUI() ? R$string.config_navBarLayoutQuickstep : R$string.config_navBarLayout;
        return ((i == R$string.config_navBarLayout || i == R$string.config_navBarLayoutQuickstep) && this.mCompactLayout) ? "left;back,home,recent;right" : (mIsHintEnabledRef.get().booleanValue() || i != R$string.config_navBarLayoutHandle) ? getContext().getString(i) : getContext().getString(i).replace("home_handle", "");
    }

    public View inflateButton(String str, ViewGroup viewGroup, boolean z, boolean z2) {
        View createView = createView(str, viewGroup, z ? this.mLandscapeInflater : this.mLayoutInflater);
        if (createView == null) {
            return null;
        }
        View applySize = applySize(createView, str, z, z2);
        viewGroup.addView(applySize);
        addToDispatchers(applySize);
        View view = z ? this.mLastLandscape : this.mLastPortrait;
        View childAt = applySize instanceof ReverseLinearLayout.ReverseRelativeLayout ? ((ReverseLinearLayout.ReverseRelativeLayout) applySize).getChildAt(0) : applySize;
        if (view != null) {
            childAt.setAccessibilityTraversalAfter(view.getId());
        }
        if (z) {
            this.mLastLandscape = childAt;
        } else {
            this.mLastPortrait = childAt;
        }
        return applySize;
    }

    public final void inflateButtons(String[] strArr, ViewGroup viewGroup, boolean z, boolean z2) {
        for (String str : strArr) {
            inflateButton(str, viewGroup, z, z2);
        }
    }

    public final void inflateChildren() {
        removeAllViews();
        FrameLayout frameLayout = (FrameLayout) this.mLayoutInflater.inflate(R$layout.navigation_layout, (ViewGroup) this, false);
        this.mHorizontal = frameLayout;
        addView(frameLayout);
        FrameLayout frameLayout2 = (FrameLayout) this.mLayoutInflater.inflate(R$layout.navigation_layout_vertical, (ViewGroup) this, false);
        this.mVertical = frameLayout2;
        addView(frameLayout2);
        updateAlternativeOrder();
    }

    public final void inflateCursorButtons(ViewGroup viewGroup, boolean z) {
        (z ? this.mLandscapeInflater : this.mLayoutInflater).inflate(z ? R$layout.nav_buttons_dpad_group_vertical : R$layout.nav_buttons_dpad_group, viewGroup);
        addToDispatchers(viewGroup);
    }

    public void inflateLayout(String str) {
        this.mCurrentLayout = str;
        String str2 = str;
        if (str == null) {
            str2 = getDefaultLayout();
        }
        String[] split = str2.split(";", 3);
        String[] strArr = split;
        if (split.length != 3) {
            Log.d("NavBarInflater", "Invalid layout.");
            strArr = getDefaultLayout().split(";", 3);
        }
        String[] split2 = strArr[0].split(",");
        String[] split3 = strArr[1].split(",");
        String[] split4 = strArr[2].split(",");
        FrameLayout frameLayout = this.mHorizontal;
        int i = R$id.ends_group;
        inflateButtons(split2, (ViewGroup) frameLayout.findViewById(i), false, true);
        inflateButtons(split2, (ViewGroup) this.mVertical.findViewById(i), true, true);
        FrameLayout frameLayout2 = this.mHorizontal;
        int i2 = R$id.center_group;
        inflateButtons(split3, (ViewGroup) frameLayout2.findViewById(i2), false, false);
        inflateButtons(split3, (ViewGroup) this.mVertical.findViewById(i2), true, false);
        addGravitySpacer((LinearLayout) this.mHorizontal.findViewById(i));
        addGravitySpacer((LinearLayout) this.mVertical.findViewById(i));
        inflateButtons(split4, (ViewGroup) this.mHorizontal.findViewById(i), false, false);
        inflateButtons(split4, (ViewGroup) this.mVertical.findViewById(i), true, false);
        FrameLayout frameLayout3 = this.mHorizontal;
        int i3 = R$id.dpad_group;
        inflateCursorButtons((ViewGroup) frameLayout3.findViewById(i3), false);
        inflateCursorButtons((ViewGroup) this.mVertical.findViewById(i3), true);
        updateButtonDispatchersCurrentView();
    }

    public final void initiallyFill(ButtonDispatcher buttonDispatcher) {
        FrameLayout frameLayout = this.mHorizontal;
        int i = R$id.ends_group;
        addAll(buttonDispatcher, (ViewGroup) frameLayout.findViewById(i));
        FrameLayout frameLayout2 = this.mHorizontal;
        int i2 = R$id.center_group;
        addAll(buttonDispatcher, (ViewGroup) frameLayout2.findViewById(i2));
        FrameLayout frameLayout3 = this.mHorizontal;
        int i3 = R$id.dpad_group;
        addAll(buttonDispatcher, (ViewGroup) frameLayout3.findViewById(i3));
        addAll(buttonDispatcher, (ViewGroup) this.mVertical.findViewById(i));
        addAll(buttonDispatcher, (ViewGroup) this.mVertical.findViewById(i2));
        addAll(buttonDispatcher, (ViewGroup) this.mVertical.findViewById(i3));
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        ((TunerService) Dependency.get(TunerService.class)).addTunable(this, new String[]{"sysui_nav_bar_inverse"});
        ((TunerService) Dependency.get(TunerService.class)).addTunable(this, new String[]{"customsystem:navigation_bar_compact_layout"});
        ((TunerService) Dependency.get(TunerService.class)).addTunable(this, new String[]{"customsystem:navigation_bar_hint"});
    }

    @Override // android.view.View
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        updateLayoutInversion();
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        ((NavigationModeController) Dependency.get(NavigationModeController.class)).removeListener(this);
        ((TunerService) Dependency.get(TunerService.class)).removeTunable(this);
        super.onDetachedFromWindow();
    }

    @Override // android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        inflateChildren();
        clearViews();
        inflateLayout(getDefaultLayout());
    }

    public void onLikelyDefaultLayoutChange() {
        String defaultLayout = getDefaultLayout();
        if (Objects.equals(this.mCurrentLayout, defaultLayout)) {
            return;
        }
        clearViews();
        inflateLayout(defaultLayout);
    }

    @Override // com.android.systemui.navigationbar.NavigationModeController.ModeChangedListener
    public void onNavigationModeChanged(int i) {
        this.mNavBarMode = i;
        onLikelyDefaultLayoutChange();
        updateHint();
    }

    public void onTuningChanged(String str, String str2) {
        if ("sysui_nav_bar_inverse".equals(str)) {
            this.mInverseLayout = TunerService.parseIntegerSwitch(str2, false);
            updateLayoutInversion();
        } else if ("customsystem:navigation_bar_compact_layout".equals(str)) {
            boolean parseIntegerSwitch = TunerService.parseIntegerSwitch(str2, false);
            if (parseIntegerSwitch != this.mCompactLayout) {
                this.mCompactLayout = parseIntegerSwitch;
                setNavigationBarLayout(getDefaultLayout());
            }
        } else if ("customsystem:navigation_bar_hint".equals(str)) {
            mIsHintEnabledRef.compareAndSet(mIsHintEnabledRef.get(), Boolean.valueOf(TunerService.parseIntegerSwitch(str2, true)));
            updateHint();
            onLikelyDefaultLayoutChange();
        }
        if (QuickStepContract.isGesturalMode(this.mNavBarMode)) {
            setNavigationBarLayout(str2);
        }
    }

    public void setAlternativeOrder(boolean z) {
        if (z != this.mAlternativeOrder) {
            this.mAlternativeOrder = z;
            updateAlternativeOrder();
        }
    }

    public void setButtonDispatchers(SparseArray<ButtonDispatcher> sparseArray) {
        this.mButtonDispatchers = sparseArray;
        clearDispatcherViews();
        for (int i = 0; i < sparseArray.size(); i++) {
            initiallyFill(sparseArray.valueAt(i));
        }
    }

    public final void setNavigationBarLayout(String str) {
        if (Objects.equals(this.mCurrentLayout, str)) {
            return;
        }
        clearViews();
        inflateLayout(str);
    }

    public void setVertical(boolean z) {
        if (z != this.mIsVertical) {
            this.mIsVertical = z;
        }
    }

    public final void updateAlternativeOrder() {
        FrameLayout frameLayout = this.mHorizontal;
        int i = R$id.ends_group;
        updateAlternativeOrder(frameLayout.findViewById(i));
        FrameLayout frameLayout2 = this.mHorizontal;
        int i2 = R$id.center_group;
        updateAlternativeOrder(frameLayout2.findViewById(i2));
        updateAlternativeOrder(this.mVertical.findViewById(i));
        updateAlternativeOrder(this.mVertical.findViewById(i2));
    }

    public final void updateAlternativeOrder(View view) {
        if (view instanceof ReverseLinearLayout) {
            ((ReverseLinearLayout) view).setAlternativeOrder(this.mAlternativeOrder);
        }
    }

    public void updateButtonDispatchersCurrentView() {
        if (this.mButtonDispatchers != null) {
            FrameLayout frameLayout = this.mIsVertical ? this.mVertical : this.mHorizontal;
            for (int i = 0; i < this.mButtonDispatchers.size(); i++) {
                this.mButtonDispatchers.valueAt(i).setCurrentView(frameLayout);
            }
        }
    }

    public final void updateHint() {
        IOverlayManager asInterface = IOverlayManager.Stub.asInterface(ServiceManager.getService("overlay"));
        boolean z = this.mNavBarMode == 2 && !mIsHintEnabledRef.get().booleanValue();
        int currentUser = ActivityManager.getCurrentUser();
        try {
            asInterface.setEnabled("org.pixelexperience.overlay.navbar.nohint", z, currentUser);
            if (z) {
                asInterface.setHighestPriority("org.pixelexperience.overlay.navbar.nohint", currentUser);
            }
        } catch (RemoteException | IllegalArgumentException e) {
            StringBuilder sb = new StringBuilder();
            sb.append("Failed to ");
            sb.append(z ? "enable" : "disable");
            sb.append(" overlay ");
            sb.append("org.pixelexperience.overlay.navbar.nohint");
            sb.append(" for user ");
            sb.append(currentUser);
            Log.e("NavBarInflater", sb.toString());
        }
    }

    public final void updateLayoutInversion() {
        if (!this.mInverseLayout) {
            setLayoutDirection(2);
        } else if (((FrameLayout) this).mContext.getResources().getConfiguration().getLayoutDirection() == 1) {
            setLayoutDirection(0);
        } else {
            setLayoutDirection(1);
        }
    }
}