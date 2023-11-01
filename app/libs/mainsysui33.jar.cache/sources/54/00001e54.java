package com.android.systemui.navigationbar;

import android.graphics.Rect;
import android.os.Handler;
import android.os.RemoteException;
import android.util.SparseArray;
import android.view.IWallpaperVisibilityListener;
import android.view.IWindowManager;
import android.view.View;
import com.android.systemui.R$bool;
import com.android.systemui.R$drawable;
import com.android.systemui.R$id;
import com.android.systemui.navigationbar.NavigationBarTransitions;
import com.android.systemui.navigationbar.buttons.ButtonDispatcher;
import com.android.systemui.statusbar.phone.BarTransitions;
import com.android.systemui.statusbar.phone.LightBarTransitionsController;
import com.android.systemui.util.Utils;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/* loaded from: mainsysui33.jar:com/android/systemui/navigationbar/NavigationBarTransitions.class */
public final class NavigationBarTransitions extends BarTransitions implements LightBarTransitionsController.DarkIntensityApplier {
    public final boolean mAllowAutoDimWallpaperNotVisible;
    public boolean mAutoDim;
    public List<DarkIntensityListener> mDarkIntensityListeners;
    public final Handler mHandler;
    public final LightBarTransitionsController mLightTransitionsController;
    public boolean mLightsOut;
    public List<Listener> mListeners;
    public int mNavBarMode;
    public View mNavButtons;
    public final NavigationBarView mView;
    public final IWallpaperVisibilityListener mWallpaperVisibilityListener;
    public boolean mWallpaperVisible;
    public final IWindowManager mWindowManagerService;

    /* renamed from: com.android.systemui.navigationbar.NavigationBarTransitions$1 */
    /* loaded from: mainsysui33.jar:com/android/systemui/navigationbar/NavigationBarTransitions$1.class */
    public class AnonymousClass1 extends IWallpaperVisibilityListener.Stub {
        /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.navigationbar.NavigationBarTransitions$1$$ExternalSyntheticLambda0.run():void] */
        public static /* synthetic */ void $r8$lambda$eVFl9hmigiA_aJSiK1RQcSPTynE(AnonymousClass1 anonymousClass1) {
            anonymousClass1.lambda$onWallpaperVisibilityChanged$0();
        }

        public AnonymousClass1() {
            NavigationBarTransitions.this = r4;
        }

        public /* synthetic */ void lambda$onWallpaperVisibilityChanged$0() {
            NavigationBarTransitions.this.applyLightsOut(true, false);
        }

        public void onWallpaperVisibilityChanged(boolean z, int i) throws RemoteException {
            NavigationBarTransitions.this.mWallpaperVisible = z;
            NavigationBarTransitions.this.mHandler.post(new Runnable() { // from class: com.android.systemui.navigationbar.NavigationBarTransitions$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    NavigationBarTransitions.AnonymousClass1.$r8$lambda$eVFl9hmigiA_aJSiK1RQcSPTynE(NavigationBarTransitions.AnonymousClass1.this);
                }
            });
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/navigationbar/NavigationBarTransitions$DarkIntensityListener.class */
    public interface DarkIntensityListener {
        void onDarkIntensity(float f);
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/navigationbar/NavigationBarTransitions$Listener.class */
    public interface Listener {
        void onTransition(int i);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.navigationbar.NavigationBarTransitions$$ExternalSyntheticLambda0.onLayoutChange(android.view.View, int, int, int, int, int, int, int, int):void] */
    public static /* synthetic */ void $r8$lambda$WqcjeFaJhsVtAPdB0RrO5uOtEHo(NavigationBarTransitions navigationBarTransitions, View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        navigationBarTransitions.lambda$new$0(view, i, i2, i3, i4, i5, i6, i7, i8);
    }

    public NavigationBarTransitions(NavigationBarView navigationBarView, IWindowManager iWindowManager, LightBarTransitionsController.Factory factory) {
        super(navigationBarView, R$drawable.nav_background);
        this.mListeners = new ArrayList();
        this.mNavBarMode = 0;
        this.mHandler = Handler.getMain();
        AnonymousClass1 anonymousClass1 = new AnonymousClass1();
        this.mWallpaperVisibilityListener = anonymousClass1;
        this.mView = navigationBarView;
        this.mWindowManagerService = iWindowManager;
        this.mLightTransitionsController = factory.create(this);
        this.mAllowAutoDimWallpaperNotVisible = navigationBarView.getContext().getResources().getBoolean(R$bool.config_navigation_bar_enable_auto_dim_no_visible_wallpaper);
        this.mDarkIntensityListeners = new ArrayList();
        try {
            this.mWallpaperVisible = iWindowManager.registerWallpaperVisibilityListener(anonymousClass1, 0);
        } catch (RemoteException e) {
        }
        this.mView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() { // from class: com.android.systemui.navigationbar.NavigationBarTransitions$$ExternalSyntheticLambda0
            @Override // android.view.View.OnLayoutChangeListener
            public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                NavigationBarTransitions.$r8$lambda$WqcjeFaJhsVtAPdB0RrO5uOtEHo(NavigationBarTransitions.this, view, i, i2, i3, i4, i5, i6, i7, i8);
            }
        });
        View currentView = this.mView.getCurrentView();
        if (currentView != null) {
            this.mNavButtons = currentView.findViewById(R$id.nav_buttons);
        }
    }

    public /* synthetic */ void lambda$new$0(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        View currentView = this.mView.getCurrentView();
        if (currentView != null) {
            this.mNavButtons = currentView.findViewById(R$id.nav_buttons);
            applyLightsOut(false, true);
        }
    }

    public float addDarkIntensityListener(DarkIntensityListener darkIntensityListener) {
        this.mDarkIntensityListeners.add(darkIntensityListener);
        return this.mLightTransitionsController.getCurrentDarkIntensity();
    }

    public void addListener(Listener listener) {
        this.mListeners.add(listener);
    }

    public void applyDarkIntensity(float f) {
        SparseArray<ButtonDispatcher> buttonDispatchers = this.mView.getButtonDispatchers();
        for (int size = buttonDispatchers.size() - 1; size >= 0; size--) {
            buttonDispatchers.valueAt(size).setDarkIntensity(f);
        }
        this.mView.getRotationButtonController().setDarkIntensity(f);
        for (DarkIntensityListener darkIntensityListener : this.mDarkIntensityListeners) {
            darkIntensityListener.onDarkIntensity(f);
        }
        if (this.mAutoDim) {
            applyLightsOut(false, true);
        }
    }

    public final void applyLightsOut(boolean z, boolean z2) {
        applyLightsOut(isLightsOut(getMode()), z, z2);
    }

    public final void applyLightsOut(boolean z, boolean z2, boolean z3) {
        if (z3 || z != this.mLightsOut) {
            this.mLightsOut = z;
            View view = this.mNavButtons;
            if (view == null) {
                return;
            }
            view.animate().cancel();
            float currentDarkIntensity = z ? (this.mLightTransitionsController.getCurrentDarkIntensity() / 10.0f) + 0.6f : 1.0f;
            if (z2) {
                this.mNavButtons.animate().alpha(currentDarkIntensity).setDuration(z ? 1500 : 250).start();
            } else {
                this.mNavButtons.setAlpha(currentDarkIntensity);
            }
        }
    }

    public void destroy() {
        try {
            this.mWindowManagerService.unregisterWallpaperVisibilityListener(this.mWallpaperVisibilityListener, 0);
        } catch (RemoteException e) {
        }
        this.mLightTransitionsController.destroy();
    }

    public void dump(PrintWriter printWriter) {
        printWriter.println("NavigationBarTransitions:");
        printWriter.println("  mMode: " + getMode());
        printWriter.println("  mAlwaysOpaque: " + isAlwaysOpaque());
        printWriter.println("  mAllowAutoDimWallpaperNotVisible: " + this.mAllowAutoDimWallpaperNotVisible);
        printWriter.println("  mWallpaperVisible: " + this.mWallpaperVisible);
        printWriter.println("  mLightsOut: " + this.mLightsOut);
        printWriter.println("  mAutoDim: " + this.mAutoDim);
        printWriter.println("  bg overrideAlpha: " + ((BarTransitions) this).mBarBackground.getOverrideAlpha());
        printWriter.println("  bg color: " + ((BarTransitions) this).mBarBackground.getColor());
        printWriter.println("  bg frame: " + ((BarTransitions) this).mBarBackground.getFrame());
    }

    public LightBarTransitionsController getLightTransitionsController() {
        return this.mLightTransitionsController;
    }

    public int getTintAnimationDuration() {
        if (Utils.isGesturalModeOnDefaultDisplay(this.mView.getContext(), this.mNavBarMode)) {
            return Math.max(1700, 400);
        }
        return 120;
    }

    public void init() {
        applyModeBackground(-1, getMode(), false);
        applyLightsOut(false, true);
    }

    public boolean isLightsOut(int i) {
        return super.isLightsOut(i) || (this.mAllowAutoDimWallpaperNotVisible && this.mAutoDim && !this.mWallpaperVisible && i != 5);
    }

    public void onNavigationModeChanged(int i) {
        this.mNavBarMode = i;
    }

    public void onTransition(int i, int i2, boolean z) {
        super.onTransition(i, i2, z);
        applyLightsOut(z, false);
        for (Listener listener : this.mListeners) {
            listener.onTransition(i2);
        }
    }

    public void reapplyDarkIntensity() {
        applyDarkIntensity(this.mLightTransitionsController.getCurrentDarkIntensity());
    }

    public void removeDarkIntensityListener(DarkIntensityListener darkIntensityListener) {
        this.mDarkIntensityListeners.remove(darkIntensityListener);
    }

    public void setAutoDim(boolean z) {
        if ((z && Utils.isGesturalModeOnDefaultDisplay(this.mView.getContext(), this.mNavBarMode)) || this.mAutoDim == z) {
            return;
        }
        this.mAutoDim = z;
        applyLightsOut(true, false);
    }

    public void setBackgroundFrame(Rect rect) {
        ((BarTransitions) this).mBarBackground.setFrame(rect);
    }

    public void setBackgroundOverrideAlpha(float f) {
        ((BarTransitions) this).mBarBackground.setOverrideAlpha(f);
    }
}