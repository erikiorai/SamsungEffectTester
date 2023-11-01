package com.android.systemui.recents;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.app.ActivityManager;
import android.app.ActivityTaskManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.ColorDrawable;
import android.os.Binder;
import android.os.RemoteException;
import android.text.SpannableStringBuilder;
import android.text.style.BulletSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.IWindowManager;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.WindowManagerGlobal;
import android.view.accessibility.AccessibilityManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.android.internal.util.custom.NavbarUtils;
import com.android.settingslib.widget.ActionBarShadowController;
import com.android.systemui.R$color;
import com.android.systemui.R$dimen;
import com.android.systemui.R$id;
import com.android.systemui.R$layout;
import com.android.systemui.R$string;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.navigationbar.NavigationBarView;
import com.android.systemui.navigationbar.NavigationModeController;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.shared.recents.utilities.Utilities;
import com.android.systemui.shared.system.QuickStepContract;
import com.android.systemui.statusbar.phone.CentralSurfaces;
import com.android.systemui.util.leak.RotationUtils;
import dagger.Lazy;
import java.util.ArrayList;
import java.util.Optional;
import java.util.function.Function;

/* loaded from: mainsysui33.jar:com/android/systemui/recents/ScreenPinningRequest.class */
public class ScreenPinningRequest implements View.OnClickListener, NavigationModeController.ModeChangedListener {
    public final AccessibilityManager mAccessibilityService;
    public final BroadcastDispatcher mBroadcastDispatcher;
    public final Lazy<Optional<CentralSurfaces>> mCentralSurfacesOptionalLazy;
    public final Context mContext;
    public int mNavBarMode;
    public RequestWindowView mRequestWindow;
    public final UserTracker mUserTracker;
    public final WindowManager mWindowManager;
    public int taskId;
    public final UserTracker.Callback mUserChangedCallback = new UserTracker.Callback() { // from class: com.android.systemui.recents.ScreenPinningRequest.1
        public void onUserChanged(int i, Context context) {
            ScreenPinningRequest.this.clearPrompt();
        }
    };
    public final IWindowManager mWindowManagerService = WindowManagerGlobal.getWindowManagerService();

    /* loaded from: mainsysui33.jar:com/android/systemui/recents/ScreenPinningRequest$RequestWindowView.class */
    public class RequestWindowView extends FrameLayout {
        public final ColorDrawable mColor;
        public ViewGroup mLayout;
        public final BroadcastReceiver mReceiver;
        public final boolean mShowCancel;
        public final Runnable mUpdateLayoutRunnable;

        public RequestWindowView(Context context, boolean z) {
            super(context);
            ColorDrawable colorDrawable = new ColorDrawable(0);
            this.mColor = colorDrawable;
            this.mUpdateLayoutRunnable = new Runnable() { // from class: com.android.systemui.recents.ScreenPinningRequest.RequestWindowView.2
                @Override // java.lang.Runnable
                public void run() {
                    if (RequestWindowView.this.mLayout == null || RequestWindowView.this.mLayout.getParent() == null) {
                        return;
                    }
                    ViewGroup viewGroup = RequestWindowView.this.mLayout;
                    RequestWindowView requestWindowView = RequestWindowView.this;
                    viewGroup.setLayoutParams(ScreenPinningRequest.this.getRequestLayoutParams(requestWindowView.getRotation(((FrameLayout) requestWindowView).mContext)));
                }
            };
            this.mReceiver = new BroadcastReceiver() { // from class: com.android.systemui.recents.ScreenPinningRequest.RequestWindowView.3
                @Override // android.content.BroadcastReceiver
                public void onReceive(Context context2, Intent intent) {
                    if (intent.getAction().equals("android.intent.action.CONFIGURATION_CHANGED")) {
                        RequestWindowView requestWindowView = RequestWindowView.this;
                        requestWindowView.post(requestWindowView.mUpdateLayoutRunnable);
                    } else if (intent.getAction().equals("android.intent.action.SCREEN_OFF")) {
                        ScreenPinningRequest.this.clearPrompt();
                    }
                }
            };
            setClickable(true);
            setOnClickListener(ScreenPinningRequest.this);
            setBackground(colorDrawable);
            this.mShowCancel = z;
        }

        public final int getRotation(Context context) {
            if (context.getResources().getConfiguration().smallestScreenWidthDp >= 600) {
                return 0;
            }
            return RotationUtils.getRotation(context);
        }

        public final boolean hasNavigationBar() {
            try {
                return ScreenPinningRequest.this.mWindowManagerService.hasNavigationBar(((FrameLayout) this).mContext.getDisplayId());
            } catch (RemoteException e) {
                return false;
            }
        }

        public final boolean hasSoftNavigationBar(Context context, int i) {
            if (i == 0 && NavbarUtils.isEnabled(context)) {
                return true;
            }
            try {
                return WindowManagerGlobal.getWindowManagerService().hasNavigationBar(i);
            } catch (RemoteException e) {
                Log.e("ScreenPinningRequest", "Failed to check soft navigation bar", e);
                return false;
            }
        }

        /* JADX WARN: Removed duplicated region for block: B:19:0x00b5  */
        /* JADX WARN: Removed duplicated region for block: B:20:0x00cc  */
        /* JADX WARN: Removed duplicated region for block: B:23:0x011f  */
        /* JADX WARN: Removed duplicated region for block: B:24:0x0126  */
        /* JADX WARN: Removed duplicated region for block: B:48:0x01f1  */
        /* JADX WARN: Removed duplicated region for block: B:51:0x029f  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final void inflateView(int i) {
            boolean isTouchExplorationEnabled;
            int i2;
            NavigationBarView navigationBarView;
            ViewGroup viewGroup = (ViewGroup) View.inflate(getContext(), i == 3 ? R$layout.screen_pinning_request_sea_phone : i == 1 ? R$layout.screen_pinning_request_land_phone : R$layout.screen_pinning_request, null);
            this.mLayout = viewGroup;
            viewGroup.setClickable(true);
            this.mLayout.setLayoutDirection(0);
            this.mLayout.findViewById(R$id.screen_pinning_text_area).setLayoutDirection(3);
            View findViewById = this.mLayout.findViewById(R$id.screen_pinning_buttons);
            if (!QuickStepContract.isGesturalMode(ScreenPinningRequest.this.mNavBarMode)) {
                Context context = ((FrameLayout) this).mContext;
                if (hasSoftNavigationBar(context, context.getDisplayId()) && !Utilities.isTablet(((FrameLayout) this).mContext)) {
                    findViewById.setLayoutDirection(3);
                    swapChildrenIfRtlAndVertical(findViewById);
                    ((Button) this.mLayout.findViewById(R$id.screen_pinning_ok_button)).setOnClickListener(ScreenPinningRequest.this);
                    if (this.mShowCancel) {
                        ((Button) this.mLayout.findViewById(R$id.screen_pinning_cancel_button)).setVisibility(4);
                    } else {
                        ((Button) this.mLayout.findViewById(R$id.screen_pinning_cancel_button)).setOnClickListener(ScreenPinningRequest.this);
                    }
                    Optional optional = (Optional) ScreenPinningRequest.this.mCentralSurfacesOptionalLazy.get();
                    boolean booleanValue = ((Boolean) optional.map(new Function() { // from class: com.android.systemui.recents.ScreenPinningRequest$RequestWindowView$$ExternalSyntheticLambda0
                        @Override // java.util.function.Function
                        public final Object apply(Object obj) {
                            return Boolean.valueOf(((CentralSurfaces) obj).isOverviewEnabled());
                        }
                    }).orElse(Boolean.FALSE)).booleanValue();
                    isTouchExplorationEnabled = ScreenPinningRequest.this.mAccessibilityService.isTouchExplorationEnabled();
                    if (!QuickStepContract.isGesturalMode(ScreenPinningRequest.this.mNavBarMode)) {
                        i2 = R$string.screen_pinning_description_gestural;
                    } else if (booleanValue) {
                        this.mLayout.findViewById(R$id.screen_pinning_recents_group).setVisibility(0);
                        this.mLayout.findViewById(R$id.screen_pinning_home_bg_light).setVisibility(4);
                        this.mLayout.findViewById(R$id.screen_pinning_home_bg).setVisibility(4);
                        i2 = !hasNavigationBar() ? supportsGesturesOnFP() ? R$string.screen_pinning_description_no_navbar_fpsensor : R$string.screen_pinning_description_no_navbar : isTouchExplorationEnabled ? R$string.screen_pinning_description_accessible : R$string.screen_pinning_description;
                    } else {
                        this.mLayout.findViewById(R$id.screen_pinning_recents_group).setVisibility(4);
                        this.mLayout.findViewById(R$id.screen_pinning_home_bg_light).setVisibility(0);
                        this.mLayout.findViewById(R$id.screen_pinning_home_bg).setVisibility(0);
                        i2 = !hasNavigationBar() ? supportsGesturesOnFP() ? R$string.screen_pinning_description_no_navbar_fpsensor : R$string.screen_pinning_description_no_navbar : isTouchExplorationEnabled ? R$string.screen_pinning_description_recents_invisible_accessible : R$string.screen_pinning_description_recents_invisible;
                    }
                    navigationBarView = (NavigationBarView) optional.map(new Function() { // from class: com.android.systemui.recents.ScreenPinningRequest$RequestWindowView$$ExternalSyntheticLambda1
                        @Override // java.util.function.Function
                        public final Object apply(Object obj) {
                            return ((CentralSurfaces) obj).getNavigationBarView();
                        }
                    }).orElse(null);
                    if (navigationBarView != null) {
                        ((ImageView) this.mLayout.findViewById(R$id.screen_pinning_back_icon)).setImageDrawable(navigationBarView.getBackDrawable());
                        ((ImageView) this.mLayout.findViewById(R$id.screen_pinning_home_icon)).setImageDrawable(navigationBarView.getHomeDrawable());
                    }
                    int dimensionPixelSize = getResources().getDimensionPixelSize(R$dimen.screen_pinning_description_bullet_gap_width);
                    SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
                    spannableStringBuilder.append(getContext().getText(i2), new BulletSpan(dimensionPixelSize), 0);
                    spannableStringBuilder.append((CharSequence) System.lineSeparator());
                    spannableStringBuilder.append(getContext().getText(R$string.screen_pinning_exposes_personal_data), new BulletSpan(dimensionPixelSize), 0);
                    spannableStringBuilder.append((CharSequence) System.lineSeparator());
                    spannableStringBuilder.append(getContext().getText(R$string.screen_pinning_can_open_other_apps), new BulletSpan(dimensionPixelSize), 0);
                    ((TextView) this.mLayout.findViewById(R$id.screen_pinning_description)).setText(spannableStringBuilder);
                    int i3 = 0;
                    if (isTouchExplorationEnabled) {
                        i3 = 4;
                    }
                    this.mLayout.findViewById(R$id.screen_pinning_back_bg).setVisibility(i3);
                    this.mLayout.findViewById(R$id.screen_pinning_back_bg_light).setVisibility(i3);
                    addView(this.mLayout, ScreenPinningRequest.this.getRequestLayoutParams(i));
                }
            }
            findViewById.setVisibility(8);
            ((Button) this.mLayout.findViewById(R$id.screen_pinning_ok_button)).setOnClickListener(ScreenPinningRequest.this);
            if (this.mShowCancel) {
            }
            Optional optional2 = (Optional) ScreenPinningRequest.this.mCentralSurfacesOptionalLazy.get();
            boolean booleanValue2 = ((Boolean) optional2.map(new Function() { // from class: com.android.systemui.recents.ScreenPinningRequest$RequestWindowView$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return Boolean.valueOf(((CentralSurfaces) obj).isOverviewEnabled());
                }
            }).orElse(Boolean.FALSE)).booleanValue();
            isTouchExplorationEnabled = ScreenPinningRequest.this.mAccessibilityService.isTouchExplorationEnabled();
            if (!QuickStepContract.isGesturalMode(ScreenPinningRequest.this.mNavBarMode)) {
            }
            navigationBarView = (NavigationBarView) optional2.map(new Function() { // from class: com.android.systemui.recents.ScreenPinningRequest$RequestWindowView$$ExternalSyntheticLambda1
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return ((CentralSurfaces) obj).getNavigationBarView();
                }
            }).orElse(null);
            if (navigationBarView != null) {
            }
            int dimensionPixelSize2 = getResources().getDimensionPixelSize(R$dimen.screen_pinning_description_bullet_gap_width);
            SpannableStringBuilder spannableStringBuilder2 = new SpannableStringBuilder();
            spannableStringBuilder2.append(getContext().getText(i2), new BulletSpan(dimensionPixelSize2), 0);
            spannableStringBuilder2.append((CharSequence) System.lineSeparator());
            spannableStringBuilder2.append(getContext().getText(R$string.screen_pinning_exposes_personal_data), new BulletSpan(dimensionPixelSize2), 0);
            spannableStringBuilder2.append((CharSequence) System.lineSeparator());
            spannableStringBuilder2.append(getContext().getText(R$string.screen_pinning_can_open_other_apps), new BulletSpan(dimensionPixelSize2), 0);
            ((TextView) this.mLayout.findViewById(R$id.screen_pinning_description)).setText(spannableStringBuilder2);
            int i32 = 0;
            if (isTouchExplorationEnabled) {
            }
            this.mLayout.findViewById(R$id.screen_pinning_back_bg).setVisibility(i32);
            this.mLayout.findViewById(R$id.screen_pinning_back_bg_light).setVisibility(i32);
            addView(this.mLayout, ScreenPinningRequest.this.getRequestLayoutParams(i));
        }

        @Override // android.view.ViewGroup, android.view.View
        public void onAttachedToWindow() {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            ScreenPinningRequest.this.mWindowManager.getDefaultDisplay().getMetrics(displayMetrics);
            float f = displayMetrics.density;
            int rotation = getRotation(((FrameLayout) this).mContext);
            inflateView(rotation);
            int color = ((FrameLayout) this).mContext.getColor(R$color.screen_pinning_request_window_bg);
            if (ActivityManager.isHighEndGfx()) {
                this.mLayout.setAlpha(ActionBarShadowController.ELEVATION_LOW);
                if (rotation == 3) {
                    this.mLayout.setTranslationX(f * (-96.0f));
                } else if (rotation == 1) {
                    this.mLayout.setTranslationX(f * 96.0f);
                } else {
                    this.mLayout.setTranslationY(f * 96.0f);
                }
                this.mLayout.animate().alpha(1.0f).translationX(ActionBarShadowController.ELEVATION_LOW).translationY(ActionBarShadowController.ELEVATION_LOW).setDuration(300L).setInterpolator(new DecelerateInterpolator()).start();
                ValueAnimator ofObject = ValueAnimator.ofObject(new ArgbEvaluator(), 0, Integer.valueOf(color));
                ofObject.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.recents.ScreenPinningRequest.RequestWindowView.1
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        RequestWindowView.this.mColor.setColor(((Integer) valueAnimator.getAnimatedValue()).intValue());
                    }
                });
                ofObject.setDuration(1000L);
                ofObject.start();
            } else {
                this.mColor.setColor(color);
            }
            IntentFilter intentFilter = new IntentFilter("android.intent.action.CONFIGURATION_CHANGED");
            intentFilter.addAction("android.intent.action.SCREEN_OFF");
            ScreenPinningRequest.this.mBroadcastDispatcher.registerReceiver(this.mReceiver, intentFilter);
            ScreenPinningRequest.this.mUserTracker.addCallback(ScreenPinningRequest.this.mUserChangedCallback, ((FrameLayout) this).mContext.getMainExecutor());
        }

        public void onConfigurationChanged() {
            removeAllViews();
            inflateView(getRotation(((FrameLayout) this).mContext));
        }

        @Override // android.view.ViewGroup, android.view.View
        public void onDetachedFromWindow() {
            ScreenPinningRequest.this.mBroadcastDispatcher.unregisterReceiver(this.mReceiver);
            ScreenPinningRequest.this.mUserTracker.removeCallback(ScreenPinningRequest.this.mUserChangedCallback);
        }

        public final boolean supportsGesturesOnFP() {
            return ((FrameLayout) this).mContext.getResources().getBoolean(17891816);
        }

        public final void swapChildrenIfRtlAndVertical(View view) {
            if (((FrameLayout) this).mContext.getResources().getConfiguration().getLayoutDirection() != 1) {
                return;
            }
            LinearLayout linearLayout = (LinearLayout) view;
            if (linearLayout.getOrientation() == 1) {
                int childCount = linearLayout.getChildCount();
                ArrayList arrayList = new ArrayList(childCount);
                for (int i = 0; i < childCount; i++) {
                    arrayList.add(linearLayout.getChildAt(i));
                }
                linearLayout.removeAllViews();
                for (int i2 = childCount - 1; i2 >= 0; i2--) {
                    linearLayout.addView((View) arrayList.get(i2));
                }
            }
        }
    }

    public ScreenPinningRequest(Context context, Lazy<Optional<CentralSurfaces>> lazy, NavigationModeController navigationModeController, BroadcastDispatcher broadcastDispatcher, UserTracker userTracker) {
        this.mContext = context;
        this.mCentralSurfacesOptionalLazy = lazy;
        this.mAccessibilityService = (AccessibilityManager) context.getSystemService("accessibility");
        this.mWindowManager = (WindowManager) context.getSystemService("window");
        this.mNavBarMode = navigationModeController.addListener(this);
        this.mBroadcastDispatcher = broadcastDispatcher;
        this.mUserTracker = userTracker;
    }

    public void clearPrompt() {
        RequestWindowView requestWindowView = this.mRequestWindow;
        if (requestWindowView != null) {
            this.mWindowManager.removeView(requestWindowView);
            this.mRequestWindow = null;
        }
    }

    public FrameLayout.LayoutParams getRequestLayoutParams(int i) {
        return new FrameLayout.LayoutParams(-2, -2, i == 3 ? 19 : i == 1 ? 21 : 81);
    }

    public WindowManager.LayoutParams getWindowLayoutParams() {
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(-1, -1, 2024, 264, -3);
        layoutParams.token = new Binder();
        layoutParams.privateFlags |= 16;
        layoutParams.setTitle("ScreenPinningConfirmation");
        layoutParams.gravity = 119;
        layoutParams.setFitInsetsTypes(0);
        return layoutParams;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        if (view.getId() == R$id.screen_pinning_ok_button || this.mRequestWindow == view) {
            try {
                ActivityTaskManager.getService().startSystemLockTaskMode(this.taskId);
            } catch (RemoteException e) {
            }
        }
        clearPrompt();
    }

    public void onConfigurationChanged() {
        RequestWindowView requestWindowView = this.mRequestWindow;
        if (requestWindowView != null) {
            requestWindowView.onConfigurationChanged();
        }
    }

    @Override // com.android.systemui.navigationbar.NavigationModeController.ModeChangedListener
    public void onNavigationModeChanged(int i) {
        this.mNavBarMode = i;
    }

    public void showPrompt(int i, boolean z) {
        try {
            clearPrompt();
        } catch (IllegalArgumentException e) {
        }
        this.taskId = i;
        RequestWindowView requestWindowView = new RequestWindowView(this.mContext, z);
        this.mRequestWindow = requestWindowView;
        requestWindowView.setSystemUiVisibility(RecyclerView.ViewHolder.FLAG_TMP_DETACHED);
        this.mWindowManager.addView(this.mRequestWindow, getWindowLayoutParams());
    }
}