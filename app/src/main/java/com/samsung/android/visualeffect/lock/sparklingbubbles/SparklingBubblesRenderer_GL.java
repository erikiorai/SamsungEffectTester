package com.samsung.android.visualeffect.lock.sparklingbubbles;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.Log;
import com.samsung.android.visualeffect.lock.common.SPhysicsRenderer_GL;

/* loaded from: classes.dex */
public class SparklingBubblesRenderer_GL extends SPhysicsRenderer_GL {
    private int EVENT_PARTICLENUM = 1000;

    public SparklingBubblesRenderer_GL(Context context, GLSurfaceView view) {
        this.TAG = "SparklingBubblesRenderer_GL";
        Log.d(this.TAG, "SparklingBubblesRenderer_GL Constructor 2015-06-22");
        this.veContext = context;
        this.mGlView = view;
        this.mIJniRenderer = new JniSparklingBubblesRenderer();
        this.mBitmapResStr1 = "BlurMask";
        initRender();
    }

    @Override // com.samsung.android.visualeffect.lock.common.SPhysicsRenderer_GL
    public void preSetTexture() {
        if (this.mBitmapRes1 != null) {
            this.mIJniRenderer.SetTexture(this.mBitmapResStr1, this.mBitmapRes1);
        }
        recycleResource();
    }

    @Override // com.samsung.android.visualeffect.lock.common.SPhysicsRenderer_GL
    protected void InitPhysics(int projectKind, int width, int height) {
        this.mIJniRenderer.Init_PhysicsEngine(projectKind, this.mQualityLevel, width, height);
        this.mIJniRenderer.onCustomEvent(this.EVENT_PARTICLENUM, 1100.0f);
        this.mIJniRenderer.onCustomEvent(this.EVENT_INIT_RESOLUTION, Math.min(this.windowWidth, this.windowHeight), Math.max(this.windowWidth, this.windowHeight), 0.0f);
    }

    @Override // com.samsung.android.visualeffect.lock.common.SPhysicsRenderer_GL
    protected void IsExistBubbles() {
        if (this.mIJniRenderer.isEmpty() != 0) {
            if (this.mIEffectListener != null) {
                this.mIEffectListener.onReceive(3, this.map);
            }
        } else if (this.mIEffectListener != null) {
            this.mIEffectListener.onReceive(2, this.map);
        }
    }
}