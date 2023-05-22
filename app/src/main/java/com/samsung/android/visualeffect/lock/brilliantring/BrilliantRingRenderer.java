package com.samsung.android.visualeffect.lock.brilliantring;

import android.content.Context;

import com.samsung.android.visualeffect.common.GLTextureView;
import com.samsung.android.visualeffect.lock.common.GLTextureViewRenderer;

/* loaded from: classes.dex */
public class BrilliantRingRenderer extends GLTextureViewRenderer {
    public BrilliantRingRenderer(Context context, GLTextureView view) {
        this.mContext = context;
        this.mGlView = view;
        this.mLibName = "libsecveBrilliantRing.so";
        this.TAG = "BrilliantRing Renderer";
    }
}
