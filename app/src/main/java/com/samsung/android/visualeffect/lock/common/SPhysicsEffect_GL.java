package com.samsung.android.visualeffect.lock.common;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.hardware.SensorEvent;
import android.opengl.GLSurfaceView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.samsung.android.visualeffect.EffectDataObj;
import com.samsung.android.visualeffect.IEffectListener;
import com.samsung.android.visualeffect.IEffectView;

import java.util.HashMap;

import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;

/* loaded from: classes.dex */
public class SPhysicsEffect_GL extends GLSurfaceView implements IEffectView {
    protected String TAG;
    protected ISPhysicsRenderer mIRenderer;
    protected Context veContext;

    public SPhysicsEffect_GL(Context context) {
        super(context);
        this.mIRenderer = null;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean detectOpenGLES20() {
        ActivityManager am = (ActivityManager) this.veContext.getSystemService(Context.ACTIVITY_SERVICE);
        ConfigurationInfo info = am.getDeviceConfigurationInfo();
        return info != null && info.reqGlEsVersion >= 131072;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* loaded from: classes.dex */
    public static class VFXContextFactory implements GLSurfaceView.EGLContextFactory {
        private int EGL_CONTEXT_CLIENT_VERSION = 12440;
        private int mEGLClientVersion = 2;
        private ISPhysicsRenderer mRenderer;

        public VFXContextFactory(ISPhysicsRenderer render) {
            Log.d("SPhysicsEffect_GL", "VFXContextFactory Constructure");
            this.mRenderer = render;
        }

        @Override // android.opengl.GLSurfaceView.EGLContextFactory
        public EGLContext createContext(EGL10 egl, EGLDisplay display, EGLConfig config) {
            int[] attrib_list = {this.EGL_CONTEXT_CLIENT_VERSION, this.mEGLClientVersion, 12344};
            Log.d("SPhysicsEffect_GL", "createContext");
            EGLContext eGLContext = EGL10.EGL_NO_CONTEXT;
            if (this.mEGLClientVersion == 0) {
                attrib_list = null;
            }
            return egl.eglCreateContext(display, config, eGLContext, attrib_list);
        }

        @Override // android.opengl.GLSurfaceView.EGLContextFactory
        public void destroyContext(EGL10 egl, EGLDisplay display, EGLContext context) {
            Log.d("SPhysicsEffect_GL", "destroyContext");
            this.mRenderer.onDestroy();
            egl.eglDestroyContext(display, context);
        }
    }

    public void init(EffectDataObj data) {
    }

    @Override // com.samsung.android.visualeffect.IEffectView
    public void reInit(EffectDataObj data) {
        this.mIRenderer.show();
    }

    @Override // com.samsung.android.visualeffect.IEffectView
    public void handleCustomEvent(int cmd, HashMap<?, ?> params) {
        switch (cmd) {
            case 0:
                changeBackground((Bitmap) params.get("Bitmap"), (Integer) params.get("Mode"));
                return;
            case 1:
                Rect rect = (Rect) params.get("Rect");
                showUnlockAffordance(rect.centerX(), rect.centerY());
                return;
            case 2:
                unlockEffect();
                return;
            case 3:
                screenTurnedOff();
                return;
            case 4:
                screenTurnedOn();
                return;
            case 99 /* 99 */:
                String CustomComStr = (String) params.get("CustomEvent");
                if (CustomComStr == "SensorEvent") {
                    onSensorChanged((SensorEvent) params.get("EventObject"));
                    return;
                } else if (CustomComStr == "ForceDirty") {
                    setRenderMode(0);
                    return;
                } else {
                    return;
                }
            default:
        }
    }

    @Override // com.samsung.android.visualeffect.IEffectView
    public void setListener(IEffectListener listener) {
    }

    @Override
    public boolean handleHoverEvent(MotionEvent event) {
        return false;
    }

    @Override // com.samsung.android.visualeffect.IEffectView
    public void removeListener() {
    }

    @Override // com.samsung.android.visualeffect.IEffectView
    public void handleTouchEvent(MotionEvent event, View view) {
        final MotionEvent mEvent = MotionEvent.obtain(event);
        // from class: com.samsung.android.visualeffect.lock.common.SPhysicsEffect_GL.1
// java.lang.Runnable
        queueEvent(() -> {
            if (SPhysicsEffect_GL.this.getRenderMode() == 0) {
                SPhysicsEffect_GL.this.setRenderMode(1);
            }
            SPhysicsEffect_GL.this.mIRenderer.onTouchEvent(mEvent);
            mEvent.recycle();
        });
    }

    @Override // com.samsung.android.visualeffect.IEffectView
    public void clearScreen() {
        // from class: com.samsung.android.visualeffect.lock.common.SPhysicsEffect_GL.2
// java.lang.Runnable
        queueEvent(() -> {
            if (SPhysicsEffect_GL.this.getRenderMode() == 0) {
                SPhysicsEffect_GL.this.setRenderMode(1);
            }
            SPhysicsEffect_GL.this.mIRenderer.clearEffect();
        });
    }

    private void changeBackground(final Bitmap mBG, final int mMode) {
        Log.d(this.TAG, "changeBackground Mode = " + mMode);
        if (mMode != 0) {
            this.mIRenderer.changeBackground(mBG, mMode);
            if (getRenderMode() == 0) {
                setRenderMode(1);
            }
        } else if (this.mIRenderer.getDrawCount() == 0) {
            this.mIRenderer.changeBackground(mBG, mMode);
        } else {
            // from class: com.samsung.android.visualeffect.lock.common.SPhysicsEffect_GL.3
// java.lang.Runnable
            queueEvent(() -> {
                SPhysicsEffect_GL.this.mIRenderer.changeBackground(mBG, mMode);
                if (SPhysicsEffect_GL.this.getRenderMode() == 0) {
                    SPhysicsEffect_GL.this.setRenderMode(1);
                }
            });
        }
    }

    private void screenTurnedOn() {
        // from class: com.samsung.android.visualeffect.lock.common.SPhysicsEffect_GL.4
// java.lang.Runnable
        queueEvent(() -> {
            if (SPhysicsEffect_GL.this.getRenderMode() == 0) {
                SPhysicsEffect_GL.this.setRenderMode(1);
            }
            SPhysicsEffect_GL.this.mIRenderer.screenTurnedOn();
        });
    }

    private void screenTurnedOff() {
        // from class: com.samsung.android.visualeffect.lock.common.SPhysicsEffect_GL.5
// java.lang.Runnable
        queueEvent(() -> {
            if (SPhysicsEffect_GL.this.getRenderMode() == 0) {
                SPhysicsEffect_GL.this.setRenderMode(1);
            }
            SPhysicsEffect_GL.this.mIRenderer.screenTurnedOff();
        });
    }

    private void unlockEffect() {
        // from class: com.samsung.android.visualeffect.lock.common.SPhysicsEffect_GL.6
// java.lang.Runnable
        queueEvent(() -> {
            if (SPhysicsEffect_GL.this.getRenderMode() == 0) {
                SPhysicsEffect_GL.this.setRenderMode(1);
            }
            SPhysicsEffect_GL.this.mIRenderer.unlockEffect();
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showUnlockAffordance(final float x, final float y) {
        Log.d(this.TAG, "showUnlockAffordance");
        if (this.mIRenderer.getDrawCount() > 2) {
            Log.d(this.TAG, "no delay call queueEventForAffordance()");
            // from class: com.samsung.android.visualeffect.lock.common.SPhysicsEffect_GL.7
// java.lang.Runnable
            queueEvent(() -> {
                if (SPhysicsEffect_GL.this.getRenderMode() == 0) {
                    SPhysicsEffect_GL.this.setRenderMode(1);
                }
                SPhysicsEffect_GL.this.mIRenderer.affordanceEffect(x, y);
            });
            return;
        }
        Log.d(this.TAG, "postDelayed call showUnlockAffordance(100)");
        // from class: com.samsung.android.visualeffect.lock.common.SPhysicsEffect_GL.8
// java.lang.Runnable
        postDelayed(() -> {
            Log.d(SPhysicsEffect_GL.this.TAG, "postDelayed call showUnlockAffordance()");
            SPhysicsEffect_GL.this.showUnlockAffordance(x, y);
        }, 100L);
    }

    @Override // android.view.SurfaceView, android.view.View
    protected void onWindowVisibilityChanged(int visibility) {
        if (visibility == 0) {
            super.onWindowVisibilityChanged(visibility);
        }
    }

    public void onSensorChanged(SensorEvent event) {
        mIRenderer.onSensorChanged(event);
    }
}