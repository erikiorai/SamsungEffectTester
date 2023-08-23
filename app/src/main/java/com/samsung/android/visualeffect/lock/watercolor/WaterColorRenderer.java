package com.samsung.android.visualeffect.lock.watercolor;

import android.content.Context;
import com.samsung.android.visualeffect.common.GLTextureView;
import com.samsung.android.visualeffect.lock.common.GLTextureViewRenderer;

/* loaded from: classes.dex */
public class WaterColorRenderer extends GLTextureViewRenderer {
    public WaterColorRenderer(Context context, GLTextureView view) {
        this.mContext = context;
        this.mGlView = view;
        this.mLibName = "libsecveWaterColor.so";
        this.TAG = "WaterColor_Renderer";
    }
}