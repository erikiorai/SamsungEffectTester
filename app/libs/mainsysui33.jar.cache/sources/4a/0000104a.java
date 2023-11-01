package com.android.systemui.accessibility;

import android.graphics.Rect;
import android.os.Handler;
import android.os.RemoteException;
import android.util.Log;
import android.view.accessibility.IRemoteMagnificationAnimationCallback;
import android.view.accessibility.IWindowMagnificationConnection;
import android.view.accessibility.IWindowMagnificationConnectionCallback;

/* loaded from: mainsysui33.jar:com/android/systemui/accessibility/WindowMagnificationConnectionImpl.class */
public class WindowMagnificationConnectionImpl extends IWindowMagnificationConnection.Stub {
    public IWindowMagnificationConnectionCallback mConnectionCallback;
    public final Handler mHandler;
    public final ModeSwitchesController mModeSwitchesController;
    public final WindowMagnification mWindowMagnification;

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.accessibility.WindowMagnificationConnectionImpl$$ExternalSyntheticLambda1.run():void] */
    /* renamed from: $r8$lambda$P0sJF-FyWtl-wig1XsrL8nsQVC8 */
    public static /* synthetic */ void m1364$r8$lambda$P0sJFFyWtlwig1XsrL8nsQVC8(WindowMagnificationConnectionImpl windowMagnificationConnectionImpl, int i, float f, float f2, float f3, float f4, float f5, IRemoteMagnificationAnimationCallback iRemoteMagnificationAnimationCallback) {
        windowMagnificationConnectionImpl.lambda$enableWindowMagnification$0(i, f, f2, f3, f4, f5, iRemoteMagnificationAnimationCallback);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.accessibility.WindowMagnificationConnectionImpl$$ExternalSyntheticLambda5.run():void] */
    public static /* synthetic */ void $r8$lambda$P_3aimcUylVZW2OKjcUiPgxDyVQ(WindowMagnificationConnectionImpl windowMagnificationConnectionImpl, int i) {
        windowMagnificationConnectionImpl.lambda$removeMagnificationButton$6(i);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.accessibility.WindowMagnificationConnectionImpl$$ExternalSyntheticLambda3.run():void] */
    public static /* synthetic */ void $r8$lambda$Rzf78V9HDxPy9zmfsb_45kWo_MI(WindowMagnificationConnectionImpl windowMagnificationConnectionImpl, int i, float f) {
        windowMagnificationConnectionImpl.lambda$setScale$1(i, f);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.accessibility.WindowMagnificationConnectionImpl$$ExternalSyntheticLambda4.run():void] */
    public static /* synthetic */ void $r8$lambda$XpOSryAGilW2pyj3FTKsILOXGj0(WindowMagnificationConnectionImpl windowMagnificationConnectionImpl, int i, float f, float f2) {
        windowMagnificationConnectionImpl.lambda$moveWindowMagnifier$3(i, f, f2);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.accessibility.WindowMagnificationConnectionImpl$$ExternalSyntheticLambda0.run():void] */
    /* renamed from: $r8$lambda$_SpwK_WIj1AcXAIe9-lPLF7iLcA */
    public static /* synthetic */ void m1365$r8$lambda$_SpwK_WIj1AcXAIe9lPLF7iLcA(WindowMagnificationConnectionImpl windowMagnificationConnectionImpl, int i, float f, float f2, IRemoteMagnificationAnimationCallback iRemoteMagnificationAnimationCallback) {
        windowMagnificationConnectionImpl.lambda$moveWindowMagnifierToPosition$4(i, f, f2, iRemoteMagnificationAnimationCallback);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.accessibility.WindowMagnificationConnectionImpl$$ExternalSyntheticLambda2.run():void] */
    /* renamed from: $r8$lambda$vIJqXnDd88n5TwQTlmUK-sbJ8lY */
    public static /* synthetic */ void m1366$r8$lambda$vIJqXnDd88n5TwQTlmUKsbJ8lY(WindowMagnificationConnectionImpl windowMagnificationConnectionImpl, int i, int i2) {
        windowMagnificationConnectionImpl.lambda$showMagnificationButton$5(i, i2);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.accessibility.WindowMagnificationConnectionImpl$$ExternalSyntheticLambda6.run():void] */
    public static /* synthetic */ void $r8$lambda$ysVXK0NW7CCgoUQ2N8_EsDwhy8Q(WindowMagnificationConnectionImpl windowMagnificationConnectionImpl, int i, IRemoteMagnificationAnimationCallback iRemoteMagnificationAnimationCallback) {
        windowMagnificationConnectionImpl.lambda$disableWindowMagnification$2(i, iRemoteMagnificationAnimationCallback);
    }

    public WindowMagnificationConnectionImpl(WindowMagnification windowMagnification, Handler handler, ModeSwitchesController modeSwitchesController) {
        this.mWindowMagnification = windowMagnification;
        this.mHandler = handler;
        this.mModeSwitchesController = modeSwitchesController;
    }

    public /* synthetic */ void lambda$disableWindowMagnification$2(int i, IRemoteMagnificationAnimationCallback iRemoteMagnificationAnimationCallback) {
        this.mWindowMagnification.disableWindowMagnification(i, iRemoteMagnificationAnimationCallback);
    }

    public /* synthetic */ void lambda$enableWindowMagnification$0(int i, float f, float f2, float f3, float f4, float f5, IRemoteMagnificationAnimationCallback iRemoteMagnificationAnimationCallback) {
        this.mWindowMagnification.enableWindowMagnification(i, f, f2, f3, f4, f5, iRemoteMagnificationAnimationCallback);
    }

    public /* synthetic */ void lambda$moveWindowMagnifier$3(int i, float f, float f2) {
        this.mWindowMagnification.moveWindowMagnifier(i, f, f2);
    }

    public /* synthetic */ void lambda$moveWindowMagnifierToPosition$4(int i, float f, float f2, IRemoteMagnificationAnimationCallback iRemoteMagnificationAnimationCallback) {
        this.mWindowMagnification.moveWindowMagnifierToPositionInternal(i, f, f2, iRemoteMagnificationAnimationCallback);
    }

    public /* synthetic */ void lambda$removeMagnificationButton$6(int i) {
        this.mModeSwitchesController.removeButton(i);
    }

    public /* synthetic */ void lambda$setScale$1(int i, float f) {
        this.mWindowMagnification.setScale(i, f);
    }

    public /* synthetic */ void lambda$showMagnificationButton$5(int i, int i2) {
        this.mModeSwitchesController.showButton(i, i2);
    }

    public void disableWindowMagnification(final int i, final IRemoteMagnificationAnimationCallback iRemoteMagnificationAnimationCallback) {
        this.mHandler.post(new Runnable() { // from class: com.android.systemui.accessibility.WindowMagnificationConnectionImpl$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                WindowMagnificationConnectionImpl.$r8$lambda$ysVXK0NW7CCgoUQ2N8_EsDwhy8Q(WindowMagnificationConnectionImpl.this, i, iRemoteMagnificationAnimationCallback);
            }
        });
    }

    public void enableWindowMagnification(final int i, final float f, final float f2, final float f3, final float f4, final float f5, final IRemoteMagnificationAnimationCallback iRemoteMagnificationAnimationCallback) {
        this.mHandler.post(new Runnable() { // from class: com.android.systemui.accessibility.WindowMagnificationConnectionImpl$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                WindowMagnificationConnectionImpl.m1364$r8$lambda$P0sJFFyWtlwig1XsrL8nsQVC8(WindowMagnificationConnectionImpl.this, i, f, f2, f3, f4, f5, iRemoteMagnificationAnimationCallback);
            }
        });
    }

    public void moveWindowMagnifier(final int i, final float f, final float f2) {
        this.mHandler.post(new Runnable() { // from class: com.android.systemui.accessibility.WindowMagnificationConnectionImpl$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                WindowMagnificationConnectionImpl.$r8$lambda$XpOSryAGilW2pyj3FTKsILOXGj0(WindowMagnificationConnectionImpl.this, i, f, f2);
            }
        });
    }

    public void moveWindowMagnifierToPosition(final int i, final float f, final float f2, final IRemoteMagnificationAnimationCallback iRemoteMagnificationAnimationCallback) {
        this.mHandler.post(new Runnable() { // from class: com.android.systemui.accessibility.WindowMagnificationConnectionImpl$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                WindowMagnificationConnectionImpl.m1365$r8$lambda$_SpwK_WIj1AcXAIe9lPLF7iLcA(WindowMagnificationConnectionImpl.this, i, f, f2, iRemoteMagnificationAnimationCallback);
            }
        });
    }

    public void onAccessibilityActionPerformed(int i) {
        IWindowMagnificationConnectionCallback iWindowMagnificationConnectionCallback = this.mConnectionCallback;
        if (iWindowMagnificationConnectionCallback != null) {
            try {
                iWindowMagnificationConnectionCallback.onAccessibilityActionPerformed(i);
            } catch (RemoteException e) {
                Log.e("WindowMagnificationConnectionImpl", "Failed to inform an accessibility action is already performed", e);
            }
        }
    }

    public void onChangeMagnificationMode(int i, int i2) {
        IWindowMagnificationConnectionCallback iWindowMagnificationConnectionCallback = this.mConnectionCallback;
        if (iWindowMagnificationConnectionCallback != null) {
            try {
                iWindowMagnificationConnectionCallback.onChangeMagnificationMode(i, i2);
            } catch (RemoteException e) {
                Log.e("WindowMagnificationConnectionImpl", "Failed to inform changing magnification mode", e);
            }
        }
    }

    public void onMove(int i) {
        IWindowMagnificationConnectionCallback iWindowMagnificationConnectionCallback = this.mConnectionCallback;
        if (iWindowMagnificationConnectionCallback != null) {
            try {
                iWindowMagnificationConnectionCallback.onMove(i);
            } catch (RemoteException e) {
                Log.e("WindowMagnificationConnectionImpl", "Failed to inform taking control by a user", e);
            }
        }
    }

    public void onPerformScaleAction(int i, float f) {
        IWindowMagnificationConnectionCallback iWindowMagnificationConnectionCallback = this.mConnectionCallback;
        if (iWindowMagnificationConnectionCallback != null) {
            try {
                iWindowMagnificationConnectionCallback.onPerformScaleAction(i, f);
            } catch (RemoteException e) {
                Log.e("WindowMagnificationConnectionImpl", "Failed to inform performing scale action", e);
            }
        }
    }

    public void onSourceBoundsChanged(int i, Rect rect) {
        IWindowMagnificationConnectionCallback iWindowMagnificationConnectionCallback = this.mConnectionCallback;
        if (iWindowMagnificationConnectionCallback != null) {
            try {
                iWindowMagnificationConnectionCallback.onSourceBoundsChanged(i, rect);
            } catch (RemoteException e) {
                Log.e("WindowMagnificationConnectionImpl", "Failed to inform source bounds changed", e);
            }
        }
    }

    public void onWindowMagnifierBoundsChanged(int i, Rect rect) {
        IWindowMagnificationConnectionCallback iWindowMagnificationConnectionCallback = this.mConnectionCallback;
        if (iWindowMagnificationConnectionCallback != null) {
            try {
                iWindowMagnificationConnectionCallback.onWindowMagnifierBoundsChanged(i, rect);
            } catch (RemoteException e) {
                Log.e("WindowMagnificationConnectionImpl", "Failed to inform bounds changed", e);
            }
        }
    }

    public void removeMagnificationButton(final int i) {
        this.mHandler.post(new Runnable() { // from class: com.android.systemui.accessibility.WindowMagnificationConnectionImpl$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                WindowMagnificationConnectionImpl.$r8$lambda$P_3aimcUylVZW2OKjcUiPgxDyVQ(WindowMagnificationConnectionImpl.this, i);
            }
        });
    }

    public void setConnectionCallback(IWindowMagnificationConnectionCallback iWindowMagnificationConnectionCallback) {
        this.mConnectionCallback = iWindowMagnificationConnectionCallback;
    }

    public void setScale(final int i, final float f) {
        this.mHandler.post(new Runnable() { // from class: com.android.systemui.accessibility.WindowMagnificationConnectionImpl$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                WindowMagnificationConnectionImpl.$r8$lambda$Rzf78V9HDxPy9zmfsb_45kWo_MI(WindowMagnificationConnectionImpl.this, i, f);
            }
        });
    }

    public void showMagnificationButton(final int i, final int i2) {
        this.mHandler.post(new Runnable() { // from class: com.android.systemui.accessibility.WindowMagnificationConnectionImpl$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                WindowMagnificationConnectionImpl.m1366$r8$lambda$vIJqXnDd88n5TwQTlmUKsbJ8lY(WindowMagnificationConnectionImpl.this, i, i2);
            }
        });
    }
}