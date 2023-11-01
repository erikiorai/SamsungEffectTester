package com.android.systemui.accessibility;

import android.content.Context;
import android.graphics.Rect;
import android.hardware.display.DisplayManager;
import android.os.Handler;
import android.view.Display;
import android.view.SurfaceControl;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.IRemoteMagnificationAnimationCallback;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.graphics.SfVsyncFrameCallbackProvider;
import com.android.systemui.CoreStartable;
import com.android.systemui.accessibility.MagnificationModeSwitch;
import com.android.systemui.model.SysUiState;
import com.android.systemui.recents.OverviewProxyService;
import com.android.systemui.statusbar.CommandQueue;
import java.io.PrintWriter;
import java.util.Objects;
import java.util.function.Consumer;

/* loaded from: mainsysui33.jar:com/android/systemui/accessibility/WindowMagnification.class */
public class WindowMagnification implements CoreStartable, WindowMagnifierCallback, CommandQueue.Callbacks {
    public final AccessibilityManager mAccessibilityManager;
    public final CommandQueue mCommandQueue;
    public final Context mContext;
    public final Handler mHandler;
    @VisibleForTesting
    public DisplayIdIndexSupplier<WindowMagnificationController> mMagnificationControllerSupplier;
    public final ModeSwitchesController mModeSwitchesController;
    public final OverviewProxyService mOverviewProxyService;
    public SysUiState mSysUiState;
    public WindowMagnificationConnectionImpl mWindowMagnificationConnectionImpl;

    /* loaded from: mainsysui33.jar:com/android/systemui/accessibility/WindowMagnification$ControllerSupplier.class */
    public static class ControllerSupplier extends DisplayIdIndexSupplier<WindowMagnificationController> {
        public final Context mContext;
        public final Handler mHandler;
        public final SysUiState mSysUiState;
        public final WindowMagnifierCallback mWindowMagnifierCallback;

        public ControllerSupplier(Context context, Handler handler, WindowMagnifierCallback windowMagnifierCallback, DisplayManager displayManager, SysUiState sysUiState) {
            super(displayManager);
            this.mContext = context;
            this.mHandler = handler;
            this.mWindowMagnifierCallback = windowMagnifierCallback;
            this.mSysUiState = sysUiState;
        }

        /* JADX DEBUG: Method merged with bridge method */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.android.systemui.accessibility.DisplayIdIndexSupplier
        public WindowMagnificationController createInstance(Display display) {
            Context createWindowContext = this.mContext.createWindowContext(display, 2039, null);
            return new WindowMagnificationController(createWindowContext, this.mHandler, new WindowMagnificationAnimationController(createWindowContext), new SfVsyncFrameCallbackProvider(), null, new SurfaceControl.Transaction(), this.mWindowMagnifierCallback, this.mSysUiState);
        }
    }

    public WindowMagnification(Context context, Handler handler, CommandQueue commandQueue, ModeSwitchesController modeSwitchesController, SysUiState sysUiState, OverviewProxyService overviewProxyService) {
        this.mContext = context;
        this.mHandler = handler;
        this.mAccessibilityManager = (AccessibilityManager) context.getSystemService(AccessibilityManager.class);
        this.mCommandQueue = commandQueue;
        this.mModeSwitchesController = modeSwitchesController;
        this.mSysUiState = sysUiState;
        this.mOverviewProxyService = overviewProxyService;
        this.mMagnificationControllerSupplier = new ControllerSupplier(context, handler, this, (DisplayManager) context.getSystemService(DisplayManager.class), sysUiState);
    }

    public final void clearWindowMagnificationConnection() {
        this.mAccessibilityManager.setWindowMagnificationConnection(null);
        this.mModeSwitchesController.setSwitchListenerDelegate(null);
    }

    public void disableWindowMagnification(int i, IRemoteMagnificationAnimationCallback iRemoteMagnificationAnimationCallback) {
        WindowMagnificationController windowMagnificationController = this.mMagnificationControllerSupplier.get(i);
        if (windowMagnificationController != null) {
            windowMagnificationController.deleteWindowMagnification(iRemoteMagnificationAnimationCallback);
        }
    }

    @Override // com.android.systemui.CoreStartable, com.android.systemui.Dumpable
    public void dump(final PrintWriter printWriter, String[] strArr) {
        printWriter.println("WindowMagnification");
        this.mMagnificationControllerSupplier.forEach(new Consumer() { // from class: com.android.systemui.accessibility.WindowMagnification$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((WindowMagnificationController) obj).dump(printWriter);
            }
        });
    }

    public void enableWindowMagnification(int i, float f, float f2, float f3, float f4, float f5, IRemoteMagnificationAnimationCallback iRemoteMagnificationAnimationCallback) {
        WindowMagnificationController windowMagnificationController = this.mMagnificationControllerSupplier.get(i);
        if (windowMagnificationController != null) {
            windowMagnificationController.enableWindowMagnification(f, f2, f3, f4, f5, iRemoteMagnificationAnimationCallback);
        }
    }

    public void moveWindowMagnifier(int i, float f, float f2) {
        WindowMagnificationController windowMagnificationController = this.mMagnificationControllerSupplier.get(i);
        if (windowMagnificationController != null) {
            windowMagnificationController.moveWindowMagnifier(f, f2);
        }
    }

    public void moveWindowMagnifierToPositionInternal(int i, float f, float f2, IRemoteMagnificationAnimationCallback iRemoteMagnificationAnimationCallback) {
        WindowMagnificationController windowMagnificationController = this.mMagnificationControllerSupplier.get(i);
        if (windowMagnificationController != null) {
            windowMagnificationController.moveWindowMagnifierToPosition(f, f2, iRemoteMagnificationAnimationCallback);
        }
    }

    @Override // com.android.systemui.accessibility.WindowMagnifierCallback
    public void onAccessibilityActionPerformed(int i) {
        WindowMagnificationConnectionImpl windowMagnificationConnectionImpl = this.mWindowMagnificationConnectionImpl;
        if (windowMagnificationConnectionImpl != null) {
            windowMagnificationConnectionImpl.onAccessibilityActionPerformed(i);
        }
    }

    @Override // com.android.systemui.accessibility.WindowMagnifierCallback
    public void onMove(int i) {
        WindowMagnificationConnectionImpl windowMagnificationConnectionImpl = this.mWindowMagnificationConnectionImpl;
        if (windowMagnificationConnectionImpl != null) {
            windowMagnificationConnectionImpl.onMove(i);
        }
    }

    @Override // com.android.systemui.accessibility.WindowMagnifierCallback
    public void onPerformScaleAction(int i, float f) {
        WindowMagnificationConnectionImpl windowMagnificationConnectionImpl = this.mWindowMagnificationConnectionImpl;
        if (windowMagnificationConnectionImpl != null) {
            windowMagnificationConnectionImpl.onPerformScaleAction(i, f);
        }
    }

    @Override // com.android.systemui.accessibility.WindowMagnifierCallback
    public void onSourceBoundsChanged(int i, Rect rect) {
        WindowMagnificationConnectionImpl windowMagnificationConnectionImpl = this.mWindowMagnificationConnectionImpl;
        if (windowMagnificationConnectionImpl != null) {
            windowMagnificationConnectionImpl.onSourceBoundsChanged(i, rect);
        }
    }

    @Override // com.android.systemui.accessibility.WindowMagnifierCallback
    public void onWindowMagnifierBoundsChanged(int i, Rect rect) {
        WindowMagnificationConnectionImpl windowMagnificationConnectionImpl = this.mWindowMagnificationConnectionImpl;
        if (windowMagnificationConnectionImpl != null) {
            windowMagnificationConnectionImpl.onWindowMagnifierBoundsChanged(i, rect);
        }
    }

    public void requestWindowMagnificationConnection(boolean z) {
        if (z) {
            setWindowMagnificationConnection();
        } else {
            clearWindowMagnificationConnection();
        }
    }

    public void setScale(int i, float f) {
        WindowMagnificationController windowMagnificationController = this.mMagnificationControllerSupplier.get(i);
        if (windowMagnificationController != null) {
            windowMagnificationController.setScale(f);
        }
    }

    public final void setWindowMagnificationConnection() {
        if (this.mWindowMagnificationConnectionImpl == null) {
            this.mWindowMagnificationConnectionImpl = new WindowMagnificationConnectionImpl(this, this.mHandler, this.mModeSwitchesController);
        }
        ModeSwitchesController modeSwitchesController = this.mModeSwitchesController;
        final WindowMagnificationConnectionImpl windowMagnificationConnectionImpl = this.mWindowMagnificationConnectionImpl;
        Objects.requireNonNull(windowMagnificationConnectionImpl);
        modeSwitchesController.setSwitchListenerDelegate(new MagnificationModeSwitch.SwitchListener() { // from class: com.android.systemui.accessibility.WindowMagnification$$ExternalSyntheticLambda1
            @Override // com.android.systemui.accessibility.MagnificationModeSwitch.SwitchListener
            public final void onSwitch(int i, int i2) {
                WindowMagnificationConnectionImpl.this.onChangeMagnificationMode(i, i2);
            }
        });
        this.mAccessibilityManager.setWindowMagnificationConnection(this.mWindowMagnificationConnectionImpl);
    }

    @Override // com.android.systemui.CoreStartable
    public void start() {
        this.mCommandQueue.addCallback(this);
        this.mOverviewProxyService.addCallback(new OverviewProxyService.OverviewProxyListener() { // from class: com.android.systemui.accessibility.WindowMagnification.1
            @Override // com.android.systemui.recents.OverviewProxyService.OverviewProxyListener
            public void onConnectionChanged(boolean z) {
                if (z) {
                    WindowMagnification.this.updateSysUiStateFlag();
                }
            }
        });
    }

    public final void updateSysUiStateFlag() {
        WindowMagnificationController valueAt = this.mMagnificationControllerSupplier.valueAt(0);
        if (valueAt != null) {
            valueAt.updateSysUIStateFlag();
        } else {
            this.mSysUiState.setFlag(524288, false).commitUpdate(0);
        }
    }
}