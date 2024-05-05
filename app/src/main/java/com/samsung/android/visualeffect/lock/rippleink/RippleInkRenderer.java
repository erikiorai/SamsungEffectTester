package com.samsung.android.visualeffect.lock.rippleink;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import com.samsung.android.visualeffect.common.GLTextureView;
import com.samsung.android.visualeffect.lock.common.GLTextureViewRendererForRippleType;
import java.util.LinkedList;

/* loaded from: classes.dex */
public class RippleInkRenderer extends GLTextureViewRendererForRippleType {
    public RippleInkRenderer(Context context, GLTextureView view) {
        this.TAG = "RippleInkRenderer";
        Log.d(this.TAG, "RippleInkRenderer Constructor 2014-1031");
        this.mParent = view;
        this.mBgChangeCheckQueue = new LinkedList<>();
        this.mEffectChangeCheckQueue = new LinkedList<>();
        Log.d(this.TAG, "spenUspLevel = " + this.spenUspLevel);
        if (this.spenUspLevel < 2) {
            this.isSPenSupport = false;
        }
    }

    @Override // com.samsung.android.visualeffect.lock.common.GLTextureViewRendererForRippleType
    public void native_onInitUVHBuffer() {
        NativeForRippleInk.onInitUVHBuffer();
    }

    @Override // com.samsung.android.visualeffect.lock.common.GLTextureViewRendererForRippleType
    public void native_LoadTextures(Bitmap bmp, int type) {
        NativeForRippleInk.LoadTextures(bmp, type);
    }

    @Override // com.samsung.android.visualeffect.lock.common.GLTextureViewRendererForRippleType
    public void native_loadShaderSetting(boolean isInk) {
        NativeForRippleInk.onInitSetting(this.mScreenWidth, this.mScreenHeight, isInk);
        NativeForRippleInk.onInitGPU();
    }

    @Override // com.samsung.android.visualeffect.lock.common.GLTextureViewRendererForRippleType
    public void native_onDrawFrame() {
        if (!this.mLandscape) {
            NativeForRippleInk.onDrawFrame(this.vertices, this.gpuHeights, this.indices, this.vertices.length, this.gpuHeights.length, this.indices.length, this.wvp, (int) (this.MESH_SIZE_WIDTH / this.mBitmapRatio), this.MESH_SIZE_HEIGHT, this.NUM_DETAILS_WIDTH / 2, this.NUM_DETAILS_HEIGHT / 2, this.refractiveIndex, this.reflectionRatio, this.alphaRatio1, this.alphaRatio2, this.inkColorFromSetting[this.mInkEffectColor][0], this.inkColorFromSetting[this.mInkEffectColor][1], this.inkColorFromSetting[this.mInkEffectColor][2], this.mFresnelRatio, this.mSpecularRatio, this.mExponentRatio, this.mLandscape);
        } else {
            NativeForRippleInk.onDrawFrame(this.vertices, this.gpuHeights, this.indices, this.vertices.length, this.gpuHeights.length, this.indices.length, this.wvp, this.MESH_SIZE_WIDTH, (int) (this.MESH_SIZE_HEIGHT * this.mBitmapRatio), this.NUM_DETAILS_WIDTH / 2, this.NUM_DETAILS_HEIGHT / 2, this.refractiveIndex, this.reflectionRatio, this.alphaRatio1, this.alphaRatio2, this.inkColorFromSetting[this.mInkEffectColor][0], this.inkColorFromSetting[this.mInkEffectColor][1], this.inkColorFromSetting[this.mInkEffectColor][2], this.mFresnelRatio, this.mSpecularRatio, this.mExponentRatio, this.mLandscape);
        }
    }

    @Override // com.samsung.android.visualeffect.lock.common.GLTextureViewRendererForRippleType
    public void native_onTouch(int x, int y, int action, float pressure) {
        NativeForRippleInk.onTouch(x, y, action, pressure);
    }

    @Override // com.samsung.android.visualeffect.lock.common.GLTextureViewRendererForRippleType
    public void native_initWaters() {
        NativeForRippleInk.initWaters(this.vertices, this.indices, this.VCOUNT, this.MESH_SIZE_HEIGHT, this.MESH_SIZE_WIDTH, this.SURFACE_DETAILS_HEIGHT, this.SURFACE_DETAILS_WIDTH);
    }

    @Override // com.samsung.android.visualeffect.lock.common.GLTextureViewRendererForRippleType
    public int native_move(int ySpan, int xSpan, int imax, int jmax, boolean control, float speed) {
        return NativeForRippleInk.move(this.velocity, this.heights, ySpan, xSpan, imax, jmax, this.NUM_DETAILS_WIDTH, this.NUM_DETAILS_HEIGHT, true, this.mReductionRate, 0.5f);
    }

    @Override // com.samsung.android.visualeffect.lock.common.GLTextureViewRendererForRippleType
    public void native_ripple(float mx, float my, float intensity) {
        NativeForRippleInk.ripple(this.velocity, this.MESH_SIZE_WIDTH, this.MESH_SIZE_HEIGHT, this.NUM_DETAILS_WIDTH, this.NUM_DETAILS_HEIGHT, mx, my, intensity);
    }

    @Override // com.samsung.android.visualeffect.lock.common.GLTextureViewRendererForRippleType
    public void native_clear() {
        NativeForRippleInk.clearInkValue();
    }
}