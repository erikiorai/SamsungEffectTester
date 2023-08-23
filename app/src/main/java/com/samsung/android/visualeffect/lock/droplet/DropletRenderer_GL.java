package com.samsung.android.visualeffect.lock.droplet;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.Log;

import com.samsung.android.visualeffect.lock.colourdroplet.JniColourDropletRenderer;
import com.samsung.android.visualeffect.lock.common.SPhysicsRenderer_GL;
import com.samsung.android.visualeffect.lock.waterdroplet.JniWaterDropletRenderer;

/* loaded from: classes.dex */
public class DropletRenderer_GL extends SPhysicsRenderer_GL {
    boolean colour;
    public DropletRenderer_GL(Context context, GLSurfaceView view, boolean colour, String type) {
        this.TAG = type + "DropletRenderer_GL";
        this.colour = colour;
        Log.d(this.TAG, type + "DropletRenderer_GL Constructor 2015-06-22");
        this.veContext = context;
        this.mGlView = view;
        this.mIJniRenderer = colour ? new JniColourDropletRenderer() : new JniWaterDropletRenderer(); //new JniDropletRenderer(type);
        this.mBitmapResStr1 = "Normal";
        this.mBitmapResStr2 = "EdgeDensity";
        initRender();
    }

    @Override // com.samsung.android.visualeffect.lock.common.SPhysicsRenderer_GL
    public void preSetTexture() {
        if (this.mBitmapRes1 != null) {
            this.mIJniRenderer.SetTexture(this.mBitmapResStr1, this.mBitmapRes1);
        }
        if (this.mBitmapRes2 != null) {
            this.mIJniRenderer.SetTexture(this.mBitmapResStr2, this.mBitmapRes2);
        }
        recycleResource();
    }

    @Override // com.samsung.android.visualeffect.lock.common.SPhysicsRenderer_GL
    protected void InitPhysics(int projectKind, int width, int height) {
        this.mIJniRenderer.Init_PhysicsEngine(projectKind, this.mQualityLevel, width, height);
        this.mIJniRenderer.onCustomEvent(this.EVENT_INIT_RESOLUTION, Math.min(this.windowWidth, this.windowHeight), Math.max(this.windowWidth, this.windowHeight), 0.0f);
    }

    @Override // com.samsung.android.visualeffect.lock.common.SPhysicsRenderer_GL
    protected void onSensorEvent(int sensorType, float xValue, float yValue, float zValue) {
        if (!colour)
            this.mIJniRenderer.onSensorEvent(sensorType, xValue, yValue, zValue);
    }
}