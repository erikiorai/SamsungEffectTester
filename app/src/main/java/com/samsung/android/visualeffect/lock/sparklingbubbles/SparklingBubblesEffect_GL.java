package com.samsung.android.visualeffect.lock.sparklingbubbles;

import android.content.Context;
import android.util.Log;

import com.samsung.android.visualeffect.EffectDataObj;
import com.samsung.android.visualeffect.lock.common.SPhysicsEffect_GL;

/* loaded from: classes.dex */
public class SparklingBubblesEffect_GL extends SPhysicsEffect_GL {
    public SparklingBubblesEffect_GL(Context context) {
        super(context);
        this.TAG = "SparklingBubblesEffect_GL";
        Log.d(this.TAG, "SparklingBubblesEffect_GL Constructor");
        this.veContext = context;
        this.mIRenderer = new SparklingBubblesRenderer_GL(this.veContext, this);
        if (detectOpenGLES20()) {
            SPhysicsEffect_GL.VFXContextFactory factory = new SPhysicsEffect_GL.VFXContextFactory(this.mIRenderer);
            setEGLContextFactory(factory);
            setEGLConfigChooser(8, 8, 8, 8, 16, 8);
            setRenderer((SparklingBubblesRenderer_GL) this.mIRenderer);
            getHolder().setFormat(3);
            setRenderMode(1);
            return;
        }
        Log.e(this.TAG, "this machine does not support OpenGL ES2.0");
    }

    @Override // com.samsung.android.visualeffect.lock.common.SPhysicsEffect_GL, com.samsung.android.visualeffect.IEffectView
    public void init(EffectDataObj data) {
        Log.d(this.TAG, "init");
        this.mIRenderer.initConfig(data.sparklingBubblesData.windowWidth, data.sparklingBubblesData.windowHeight, data.sparklingBubblesData.mIEffectListener);
        this.mIRenderer.setResourcesBitmap1(data.sparklingBubblesData.resBmp);
    }
}