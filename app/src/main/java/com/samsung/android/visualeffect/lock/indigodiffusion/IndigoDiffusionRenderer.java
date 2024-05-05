package com.samsung.android.visualeffect.lock.indigodiffusion;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import com.samsung.android.visualeffect.common.GLTextureView;
import com.samsung.android.visualeffect.lock.common.GLTextureViewRendererForRippleType;
import java.util.LinkedList;

/* loaded from: classes.dex */
public class IndigoDiffusionRenderer extends GLTextureViewRendererForRippleType {
    public IndigoDiffusionRenderer(Context context, GLTextureView view) {
        this.TAG = "IndigoDiffusionRenderer";
        Log.d(this.TAG, "IndigoDiffusionRenderer Constructor 2014-1031");
        this.isIndigoDiffusion = true;
        this.isSPenSupport = true;
        this.mParent = view;
        this.mBgChangeCheckQueue = new LinkedList<>();
    }

    @Override // com.samsung.android.visualeffect.lock.common.GLTextureViewRendererForRippleType
    public void native_onInitUVHBuffer() {
        NativeForIndigoDiffusion.onInitUVHBuffer();
    }

    @Override // com.samsung.android.visualeffect.lock.common.GLTextureViewRendererForRippleType
    public void native_LoadTextures(Bitmap bmp, int type) {
        NativeForIndigoDiffusion.LoadTextures(bmp, type);
    }

    @Override // com.samsung.android.visualeffect.lock.common.GLTextureViewRendererForRippleType
    public void native_loadShaderSetting(boolean isInk) {
        NativeForIndigoDiffusion.onInitSetting(this.mScreenWidth, this.mScreenHeight, isInk);
        NativeForIndigoDiffusion.onInitGPU();
    }

    @Override // com.samsung.android.visualeffect.lock.common.GLTextureViewRendererForRippleType
    public void native_onDrawFrame() {
        if (!this.mLandscape) {
            NativeForIndigoDiffusion.onDrawFrame(this.vertices, this.gpuHeights, this.indices, this.vertices.length, this.gpuHeights.length, this.indices.length, this.wvp, (int) (this.MESH_SIZE_WIDTH / this.mBitmapRatio), this.MESH_SIZE_HEIGHT, this.NUM_DETAILS_WIDTH / 2, this.NUM_DETAILS_HEIGHT / 2, this.refractiveIndex, this.reflectionRatio, this.alphaRatio1, this.alphaRatio2, this.inkColors[0], this.inkColors[1], this.inkColors[2], this.mFresnelRatio, this.mSpecularRatio, this.mExponentRatio, this.mLandscape);
        } else {
            NativeForIndigoDiffusion.onDrawFrame(this.vertices, this.gpuHeights, this.indices, this.vertices.length, this.gpuHeights.length, this.indices.length, this.wvp, this.MESH_SIZE_WIDTH, (int) (this.MESH_SIZE_HEIGHT * this.mBitmapRatio), this.NUM_DETAILS_WIDTH / 2, this.NUM_DETAILS_HEIGHT / 2, this.refractiveIndex, this.reflectionRatio, this.alphaRatio1, this.alphaRatio2, this.inkColors[0], this.inkColors[1], this.inkColors[2], this.mFresnelRatio, this.mSpecularRatio, this.mExponentRatio, this.mLandscape);
        }
    }

    @Override // com.samsung.android.visualeffect.lock.common.GLTextureViewRendererForRippleType
    public void native_onTouch(int x, int y, int action, float pressure) {
        NativeForIndigoDiffusion.onTouch(x, y, action, pressure);
    }

    @Override // com.samsung.android.visualeffect.lock.common.GLTextureViewRendererForRippleType
    public void native_initWaters() {
        NativeForIndigoDiffusion.initWaters(this.vertices, this.indices, this.VCOUNT, this.MESH_SIZE_HEIGHT, this.MESH_SIZE_WIDTH, this.SURFACE_DETAILS_HEIGHT, this.SURFACE_DETAILS_WIDTH);
    }

    @Override // com.samsung.android.visualeffect.lock.common.GLTextureViewRendererForRippleType
    public int native_move(int ySpan, int xSpan, int imax, int jmax, boolean control, float speed) {
        return NativeForIndigoDiffusion.move(this.velocity, this.heights, ySpan, xSpan, imax, jmax, this.NUM_DETAILS_WIDTH, this.NUM_DETAILS_HEIGHT, true, this.mReductionRate, 0.5f);
    }

    @Override // com.samsung.android.visualeffect.lock.common.GLTextureViewRendererForRippleType
    public void native_ripple(float mx, float my, float intensity) {
        NativeForIndigoDiffusion.ripple(this.velocity, this.MESH_SIZE_WIDTH, this.MESH_SIZE_HEIGHT, this.NUM_DETAILS_WIDTH, this.NUM_DETAILS_HEIGHT, mx, my, intensity);
    }

    @Override // com.samsung.android.visualeffect.lock.common.GLTextureViewRendererForRippleType
    public void native_clear() {
        NativeForIndigoDiffusion.clearInkValue();
    }

    public void changeColor(int r, int g, int b) {
        this.inkColors[0] = r / 255.0f;
        this.inkColors[1] = g / 255.0f;
        this.inkColors[2] = b / 255.0f;
    }
}