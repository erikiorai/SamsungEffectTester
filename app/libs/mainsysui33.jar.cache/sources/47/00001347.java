package com.android.systemui.clipboardoverlay;

import android.app.ICompatCameraControlCallback;
import android.content.Context;
import android.content.res.Configuration;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.view.ViewRootImpl;
import android.view.ViewTreeObserver;
import android.view.WindowInsets;
import android.view.WindowManager;
import com.android.internal.policy.PhoneWindow;
import com.android.systemui.screenshot.FloatingWindowUtil;
import java.util.function.BiConsumer;

/* loaded from: mainsysui33.jar:com/android/systemui/clipboardoverlay/ClipboardOverlayWindow.class */
public class ClipboardOverlayWindow extends PhoneWindow implements ViewRootImpl.ActivityConfigCallback {
    public final Context mContext;
    public boolean mKeyboardVisible;
    public BiConsumer<WindowInsets, Integer> mOnKeyboardChangeListener;
    public Runnable mOnOrientationChangeListener;
    public final int mOrientation;
    public final WindowManager.LayoutParams mWindowLayoutParams;
    public final WindowManager mWindowManager;

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.clipboardoverlay.ClipboardOverlayWindow$$ExternalSyntheticLambda1.onGlobalLayout():void] */
    public static /* synthetic */ void $r8$lambda$gUXz964ikgYoFAnCuJWdko9uT0E(ClipboardOverlayWindow clipboardOverlayWindow) {
        clipboardOverlayWindow.lambda$init$0();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.clipboardoverlay.ClipboardOverlayWindow$$ExternalSyntheticLambda0.run():void] */
    public static /* synthetic */ void $r8$lambda$jFGuy2BmYVd0BMvRYu1efmZDunI(ClipboardOverlayWindow clipboardOverlayWindow) {
        clipboardOverlayWindow.lambda$init$1();
    }

    public ClipboardOverlayWindow(Context context) {
        super(context);
        this.mContext = context;
        this.mOrientation = context.getResources().getConfiguration().orientation;
        requestFeature(1);
        requestFeature(13);
        setBackgroundDrawableResource(17170445);
        WindowManager windowManager = (WindowManager) context.getSystemService(WindowManager.class);
        this.mWindowManager = windowManager;
        WindowManager.LayoutParams floatingWindowParams = FloatingWindowUtil.getFloatingWindowParams();
        this.mWindowLayoutParams = floatingWindowParams;
        floatingWindowParams.setTitle("ClipboardOverlay");
        setWindowManager(windowManager, (IBinder) null, (String) null);
        setWindowFocusable(false);
    }

    public /* synthetic */ void lambda$init$0() {
        WindowInsets windowInsets = this.mWindowManager.getCurrentWindowMetrics().getWindowInsets();
        boolean isVisible = windowInsets.isVisible(WindowInsets.Type.ime());
        if (isVisible != this.mKeyboardVisible) {
            this.mKeyboardVisible = isVisible;
            this.mOnKeyboardChangeListener.accept(windowInsets, Integer.valueOf(this.mOrientation));
        }
    }

    public /* synthetic */ void lambda$init$1() {
        this.mKeyboardVisible = this.mWindowManager.getCurrentWindowMetrics().getWindowInsets().isVisible(WindowInsets.Type.ime());
        peekDecorView().getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.android.systemui.clipboardoverlay.ClipboardOverlayWindow$$ExternalSyntheticLambda1
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public final void onGlobalLayout() {
                ClipboardOverlayWindow.$r8$lambda$gUXz964ikgYoFAnCuJWdko9uT0E(ClipboardOverlayWindow.this);
            }
        });
        peekDecorView().getViewRootImpl().setActivityConfigCallback(this);
    }

    public final void attach() {
        View decorView = getDecorView();
        if (decorView.isAttachedToWindow()) {
            return;
        }
        this.mWindowManager.addView(decorView, this.mWindowLayoutParams);
        decorView.requestApplyInsets();
    }

    public WindowInsets getWindowInsets() {
        return this.mWindowManager.getCurrentWindowMetrics().getWindowInsets();
    }

    public void init(BiConsumer<WindowInsets, Integer> biConsumer, Runnable runnable) {
        this.mOnKeyboardChangeListener = biConsumer;
        this.mOnOrientationChangeListener = runnable;
        attach();
        withWindowAttached(new Runnable() { // from class: com.android.systemui.clipboardoverlay.ClipboardOverlayWindow$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                ClipboardOverlayWindow.$r8$lambda$jFGuy2BmYVd0BMvRYu1efmZDunI(ClipboardOverlayWindow.this);
            }
        });
    }

    public void onConfigurationChanged(Configuration configuration, int i) {
        if (this.mContext.getResources().getConfiguration().orientation != this.mOrientation) {
            this.mOnOrientationChangeListener.run();
        }
    }

    public void remove() {
        View peekDecorView = peekDecorView();
        if (peekDecorView == null || !peekDecorView.isAttachedToWindow()) {
            return;
        }
        this.mWindowManager.removeViewImmediate(peekDecorView);
    }

    public void requestCompatCameraControl(boolean z, boolean z2, ICompatCameraControlCallback iCompatCameraControlCallback) {
        Log.w("ClipboardOverlayWindow", "unexpected requestCompatCameraControl call");
    }

    public final void setWindowFocusable(boolean z) {
        View peekDecorView;
        WindowManager.LayoutParams layoutParams = this.mWindowLayoutParams;
        int i = layoutParams.flags;
        if (z) {
            layoutParams.flags = i & (-9);
        } else {
            layoutParams.flags = i | 8;
        }
        if (layoutParams.flags == i || (peekDecorView = peekDecorView()) == null || !peekDecorView.isAttachedToWindow()) {
            return;
        }
        this.mWindowManager.updateViewLayout(peekDecorView, this.mWindowLayoutParams);
    }

    public void withWindowAttached(final Runnable runnable) {
        final View decorView = getDecorView();
        if (decorView.isAttachedToWindow()) {
            runnable.run();
        } else {
            decorView.getViewTreeObserver().addOnWindowAttachListener(new ViewTreeObserver.OnWindowAttachListener() { // from class: com.android.systemui.clipboardoverlay.ClipboardOverlayWindow.1
                {
                    ClipboardOverlayWindow.this = this;
                }

                @Override // android.view.ViewTreeObserver.OnWindowAttachListener
                public void onWindowAttached() {
                    decorView.getViewTreeObserver().removeOnWindowAttachListener(this);
                    runnable.run();
                }

                @Override // android.view.ViewTreeObserver.OnWindowAttachListener
                public void onWindowDetached() {
                }
            });
        }
    }
}