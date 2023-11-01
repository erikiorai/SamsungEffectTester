package com.android.keyguard;

import android.app.Presentation;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Rect;
import android.hardware.display.DisplayManager;
import android.media.MediaRouter;
import android.os.Bundle;
import android.os.Trace;
import android.util.Log;
import android.util.SparseArray;
import android.view.Display;
import android.view.DisplayInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.internal.annotations.VisibleForTesting;
import com.android.keyguard.dagger.KeyguardStatusViewComponent;
import com.android.systemui.R$id;
import com.android.systemui.R$layout;
import com.android.systemui.R$style;
import com.android.systemui.navigationbar.NavigationBarController;
import com.android.systemui.navigationbar.NavigationBarView;
import dagger.Lazy;
import java.util.concurrent.Executor;

/* loaded from: mainsysui33.jar:com/android/keyguard/KeyguardDisplayManager.class */
public class KeyguardDisplayManager {
    public static final boolean DEBUG = KeyguardConstants.DEBUG;
    public final Context mContext;
    public final DisplayManager.DisplayListener mDisplayListener;
    public final DisplayManager mDisplayService;
    public final KeyguardStatusViewComponent.Factory mKeyguardStatusViewComponentFactory;
    public final MediaRouter.SimpleCallback mMediaRouterCallback;
    public final Lazy<NavigationBarController> mNavigationBarControllerLazy;
    public boolean mShowing;
    public MediaRouter mMediaRouter = null;
    public final DisplayInfo mTmpDisplayInfo = new DisplayInfo();
    public final SparseArray<Presentation> mPresentations = new SparseArray<>();

    @VisibleForTesting
    /* loaded from: mainsysui33.jar:com/android/keyguard/KeyguardDisplayManager$KeyguardPresentation.class */
    public static final class KeyguardPresentation extends Presentation {
        public View mClock;
        public KeyguardClockSwitchController mKeyguardClockSwitchController;
        public final KeyguardStatusViewComponent.Factory mKeyguardStatusViewComponentFactory;
        public int mMarginLeft;
        public int mMarginTop;
        public Runnable mMoveTextRunnable;
        public int mUsableHeight;
        public int mUsableWidth;

        public KeyguardPresentation(Context context, Display display, KeyguardStatusViewComponent.Factory factory) {
            super(context, display, R$style.Theme_SystemUI_KeyguardPresentation, 2009);
            this.mMoveTextRunnable = new Runnable() { // from class: com.android.keyguard.KeyguardDisplayManager.KeyguardPresentation.1
                {
                    KeyguardPresentation.this = this;
                }

                @Override // java.lang.Runnable
                public void run() {
                    int i = KeyguardPresentation.this.mMarginLeft;
                    int random = (int) (Math.random() * (KeyguardPresentation.this.mUsableWidth - KeyguardPresentation.this.mClock.getWidth()));
                    int i2 = KeyguardPresentation.this.mMarginTop;
                    int random2 = (int) (Math.random() * (KeyguardPresentation.this.mUsableHeight - KeyguardPresentation.this.mClock.getHeight()));
                    KeyguardPresentation.this.mClock.setTranslationX(i + random);
                    KeyguardPresentation.this.mClock.setTranslationY(i2 + random2);
                    KeyguardPresentation.this.mClock.postDelayed(KeyguardPresentation.this.mMoveTextRunnable, 10000L);
                }
            };
            this.mKeyguardStatusViewComponentFactory = factory;
            setCancelable(false);
        }

        @Override // android.app.Dialog, android.content.DialogInterface
        public void cancel() {
        }

        @Override // android.app.Dialog
        public void onCreate(Bundle bundle) {
            super.onCreate(bundle);
            updateBounds();
            setContentView(LayoutInflater.from(getContext()).inflate(R$layout.keyguard_presentation, (ViewGroup) null));
            getWindow().getDecorView().setSystemUiVisibility(1792);
            getWindow().getAttributes().setFitInsetsTypes(0);
            getWindow().setNavigationBarContrastEnforced(false);
            getWindow().setNavigationBarColor(0);
            int i = R$id.clock;
            View findViewById = findViewById(i);
            this.mClock = findViewById;
            findViewById.post(this.mMoveTextRunnable);
            KeyguardClockSwitchController keyguardClockSwitchController = this.mKeyguardStatusViewComponentFactory.build((KeyguardStatusView) findViewById(i)).getKeyguardClockSwitchController();
            this.mKeyguardClockSwitchController = keyguardClockSwitchController;
            keyguardClockSwitchController.setOnlyClock(true);
            this.mKeyguardClockSwitchController.init();
        }

        @Override // android.app.Dialog, android.view.Window.Callback
        public void onDetachedFromWindow() {
            this.mClock.removeCallbacks(this.mMoveTextRunnable);
        }

        @Override // android.app.Presentation
        public void onDisplayChanged() {
            updateBounds();
            getWindow().getDecorView().requestLayout();
        }

        public final void updateBounds() {
            Rect bounds = getWindow().getWindowManager().getMaximumWindowMetrics().getBounds();
            this.mUsableWidth = (bounds.width() * 80) / 100;
            this.mUsableHeight = (bounds.height() * 80) / 100;
            this.mMarginLeft = (bounds.width() * 20) / 200;
            this.mMarginTop = (bounds.height() * 20) / 200;
        }
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.keyguard.KeyguardDisplayManager$$ExternalSyntheticLambda1.onDismiss(android.content.DialogInterface):void] */
    public static /* synthetic */ void $r8$lambda$sNxDj1WbSo_lspmkytihRyUV4zo(KeyguardDisplayManager keyguardDisplayManager, Presentation presentation, int i, DialogInterface dialogInterface) {
        keyguardDisplayManager.lambda$showPresentation$1(presentation, i, dialogInterface);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.keyguard.KeyguardDisplayManager$$ExternalSyntheticLambda0.run():void] */
    public static /* synthetic */ void $r8$lambda$tqRUgyyu5MITLAhvyo4XafQFbM4(KeyguardDisplayManager keyguardDisplayManager) {
        keyguardDisplayManager.lambda$new$0();
    }

    public KeyguardDisplayManager(Context context, Lazy<NavigationBarController> lazy, KeyguardStatusViewComponent.Factory factory, Executor executor) {
        DisplayManager.DisplayListener displayListener = new DisplayManager.DisplayListener() { // from class: com.android.keyguard.KeyguardDisplayManager.1
            {
                KeyguardDisplayManager.this = this;
            }

            @Override // android.hardware.display.DisplayManager.DisplayListener
            public void onDisplayAdded(int i) {
                Trace.beginSection("KeyguardDisplayManager#onDisplayAdded(displayId=" + i + ")");
                Display display = KeyguardDisplayManager.this.mDisplayService.getDisplay(i);
                if (KeyguardDisplayManager.this.mShowing) {
                    KeyguardDisplayManager.this.updateNavigationBarVisibility(i, false);
                    KeyguardDisplayManager.this.showPresentation(display);
                }
                Trace.endSection();
            }

            @Override // android.hardware.display.DisplayManager.DisplayListener
            public void onDisplayChanged(int i) {
            }

            @Override // android.hardware.display.DisplayManager.DisplayListener
            public void onDisplayRemoved(int i) {
                Trace.beginSection("KeyguardDisplayManager#onDisplayRemoved(displayId=" + i + ")");
                KeyguardDisplayManager.this.hidePresentation(i);
                Trace.endSection();
            }
        };
        this.mDisplayListener = displayListener;
        this.mMediaRouterCallback = new MediaRouter.SimpleCallback() { // from class: com.android.keyguard.KeyguardDisplayManager.2
            {
                KeyguardDisplayManager.this = this;
            }

            @Override // android.media.MediaRouter.Callback
            public void onRoutePresentationDisplayChanged(MediaRouter mediaRouter, MediaRouter.RouteInfo routeInfo) {
                if (KeyguardDisplayManager.DEBUG) {
                    Log.d("KeyguardDisplayManager", "onRoutePresentationDisplayChanged: info=" + routeInfo);
                }
                KeyguardDisplayManager keyguardDisplayManager = KeyguardDisplayManager.this;
                keyguardDisplayManager.updateDisplays(keyguardDisplayManager.mShowing);
            }

            @Override // android.media.MediaRouter.SimpleCallback, android.media.MediaRouter.Callback
            public void onRouteSelected(MediaRouter mediaRouter, int i, MediaRouter.RouteInfo routeInfo) {
                if (KeyguardDisplayManager.DEBUG) {
                    Log.d("KeyguardDisplayManager", "onRouteSelected: type=" + i + ", info=" + routeInfo);
                }
                KeyguardDisplayManager keyguardDisplayManager = KeyguardDisplayManager.this;
                keyguardDisplayManager.updateDisplays(keyguardDisplayManager.mShowing);
            }

            @Override // android.media.MediaRouter.SimpleCallback, android.media.MediaRouter.Callback
            public void onRouteUnselected(MediaRouter mediaRouter, int i, MediaRouter.RouteInfo routeInfo) {
                if (KeyguardDisplayManager.DEBUG) {
                    Log.d("KeyguardDisplayManager", "onRouteUnselected: type=" + i + ", info=" + routeInfo);
                }
                KeyguardDisplayManager keyguardDisplayManager = KeyguardDisplayManager.this;
                keyguardDisplayManager.updateDisplays(keyguardDisplayManager.mShowing);
            }
        };
        this.mContext = context;
        this.mNavigationBarControllerLazy = lazy;
        this.mKeyguardStatusViewComponentFactory = factory;
        executor.execute(new Runnable() { // from class: com.android.keyguard.KeyguardDisplayManager$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                KeyguardDisplayManager.$r8$lambda$tqRUgyyu5MITLAhvyo4XafQFbM4(KeyguardDisplayManager.this);
            }
        });
        DisplayManager displayManager = (DisplayManager) context.getSystemService(DisplayManager.class);
        this.mDisplayService = displayManager;
        displayManager.registerDisplayListener(displayListener, null);
    }

    public /* synthetic */ void lambda$new$0() {
        this.mMediaRouter = (MediaRouter) this.mContext.getSystemService(MediaRouter.class);
    }

    public /* synthetic */ void lambda$showPresentation$1(Presentation presentation, int i, DialogInterface dialogInterface) {
        if (presentation.equals(this.mPresentations.get(i))) {
            this.mPresentations.remove(i);
        }
    }

    public KeyguardPresentation createPresentation(Display display) {
        return new KeyguardPresentation(this.mContext, display, this.mKeyguardStatusViewComponentFactory);
    }

    public void hide() {
        if (this.mShowing) {
            if (DEBUG) {
                Log.v("KeyguardDisplayManager", "hide");
            }
            MediaRouter mediaRouter = this.mMediaRouter;
            if (mediaRouter != null) {
                mediaRouter.removeCallback(this.mMediaRouterCallback);
            }
            updateDisplays(false);
        }
        this.mShowing = false;
    }

    public final void hidePresentation(int i) {
        Presentation presentation = this.mPresentations.get(i);
        if (presentation != null) {
            presentation.dismiss();
            this.mPresentations.remove(i);
        }
    }

    public final boolean isKeyguardShowable(Display display) {
        if (display == null) {
            if (DEBUG) {
                Log.i("KeyguardDisplayManager", "Cannot show Keyguard on null display");
                return false;
            }
            return false;
        } else if (display.getDisplayId() == 0) {
            if (DEBUG) {
                Log.i("KeyguardDisplayManager", "Do not show KeyguardPresentation on the default display");
                return false;
            }
            return false;
        } else {
            display.getDisplayInfo(this.mTmpDisplayInfo);
            int i = this.mTmpDisplayInfo.flags;
            if ((i & 4) != 0) {
                if (DEBUG) {
                    Log.i("KeyguardDisplayManager", "Do not show KeyguardPresentation on a private display");
                    return false;
                }
                return false;
            } else if ((i & RecyclerView.ViewHolder.FLAG_ADAPTER_POSITION_UNKNOWN) != 0) {
                if (DEBUG) {
                    Log.i("KeyguardDisplayManager", "Do not show KeyguardPresentation on an unlocked display");
                    return false;
                }
                return false;
            } else {
                return true;
            }
        }
    }

    public void show() {
        if (!this.mShowing) {
            if (DEBUG) {
                Log.v("KeyguardDisplayManager", "show");
            }
            MediaRouter mediaRouter = this.mMediaRouter;
            if (mediaRouter != null) {
                mediaRouter.addCallback(4, this.mMediaRouterCallback, 8);
            } else {
                Log.w("KeyguardDisplayManager", "MediaRouter not yet initialized");
            }
            updateDisplays(true);
        }
        this.mShowing = true;
    }

    public final boolean showPresentation(Display display) {
        if (isKeyguardShowable(display)) {
            if (DEBUG) {
                Log.i("KeyguardDisplayManager", "Keyguard enabled on display: " + display);
            }
            final int displayId = display.getDisplayId();
            if (this.mPresentations.get(displayId) == null) {
                final KeyguardPresentation createPresentation = createPresentation(display);
                createPresentation.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.android.keyguard.KeyguardDisplayManager$$ExternalSyntheticLambda1
                    @Override // android.content.DialogInterface.OnDismissListener
                    public final void onDismiss(DialogInterface dialogInterface) {
                        KeyguardDisplayManager.$r8$lambda$sNxDj1WbSo_lspmkytihRyUV4zo(KeyguardDisplayManager.this, createPresentation, displayId, dialogInterface);
                    }
                });
                try {
                    createPresentation.show();
                } catch (WindowManager.InvalidDisplayException e) {
                    Log.w("KeyguardDisplayManager", "Invalid display:", e);
                    createPresentation = null;
                }
                if (createPresentation != null) {
                    this.mPresentations.append(displayId, createPresentation);
                    return true;
                }
                return false;
            }
            return false;
        }
        return false;
    }

    public boolean updateDisplays(boolean z) {
        boolean z2;
        if (z) {
            Display[] displays = this.mDisplayService.getDisplays();
            int length = displays.length;
            int i = 0;
            boolean z3 = false;
            while (true) {
                z2 = z3;
                if (i >= length) {
                    break;
                }
                Display display = displays[i];
                updateNavigationBarVisibility(display.getDisplayId(), false);
                z3 |= showPresentation(display);
                i++;
            }
        } else {
            boolean z4 = this.mPresentations.size() > 0;
            for (int size = this.mPresentations.size() - 1; size >= 0; size--) {
                updateNavigationBarVisibility(this.mPresentations.keyAt(size), true);
                this.mPresentations.valueAt(size).dismiss();
            }
            this.mPresentations.clear();
            z2 = z4;
        }
        return z2;
    }

    public final void updateNavigationBarVisibility(int i, boolean z) {
        NavigationBarView navigationBarView;
        if (i == 0 || (navigationBarView = ((NavigationBarController) this.mNavigationBarControllerLazy.get()).getNavigationBarView(i)) == null) {
            return;
        }
        if (z) {
            navigationBarView.getRootView().setVisibility(0);
        } else {
            navigationBarView.getRootView().setVisibility(8);
        }
    }
}