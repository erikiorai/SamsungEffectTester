package com.samsung.android.visualeffect.lock.droplet;

import android.content.Context;
import android.hardware.SensorEvent;
import android.util.Log;
import com.samsung.android.visualeffect.EffectDataObj;
import com.samsung.android.visualeffect.lock.common.SPhysicsEffect_GL;

public class DropletEffect_GL extends SPhysicsEffect_GL {
    public DropletEffect_GL(Context context, boolean colour) {
        super(context);
        String type;
        if (colour)
            type = "Colour";
        else type = "Water";
        this.TAG = type + "DropletEffect_GL";
        Log.d(this.TAG, type + "DropletEffect_GL Constructor");
        this.veContext = context;
        this.mIRenderer = new DropletRenderer_GL(this.veContext, this, colour, type);
        if (detectOpenGLES20()) {
            SPhysicsEffect_GL.VFXContextFactory factory = new SPhysicsEffect_GL.VFXContextFactory(this.mIRenderer);
            setEGLContextFactory(factory);
            setEGLConfigChooser(8, 8, 8, 8, 16, 8);
            setRenderer((DropletRenderer_GL) this.mIRenderer);
            getHolder().setFormat(3);
            setRenderMode(1);
            return;
        }
        Log.e(this.TAG, "this machine does not support OpenGL ES2.0");
    }

    @Override // com.samsung.android.visualeffect.lock.common.SPhysicsEffect_GL, com.samsung.android.visualeffect.IEffectView
    public void init(EffectDataObj data) {
        Log.d(this.TAG, "init");
        this.mIRenderer.initConfig(data.dropletData.windowWidth, data.dropletData.windowHeight, data.dropletData.mIEffectListener);
        this.mIRenderer.setResourcesBitmap1(data.dropletData.resNormal);
        this.mIRenderer.setResourcesBitmap2(data.dropletData.resEdgeDensity);
    }

    @Override // com.samsung.android.visualeffect.lock.common.SPhysicsEffect_GL
    public void onSensorChanged(SensorEvent event) {
        if (this.mIRenderer != null) {
            this.mIRenderer.onSensorChanged(event);
        }
    }
}