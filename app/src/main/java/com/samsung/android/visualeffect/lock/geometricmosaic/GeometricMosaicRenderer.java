package com.samsung.android.visualeffect.lock.geometricmosaic;

import android.content.Context;
import com.samsung.android.visualeffect.common.GLTextureView;
import com.samsung.android.visualeffect.lock.common.GLTextureViewRenderer;

/* loaded from: classes.dex */
public class GeometricMosaicRenderer extends GLTextureViewRenderer {
    public GeometricMosaicRenderer(Context context, GLTextureView view) {
        this.mContext = context;
        this.mGlView = view;
        this.mLibName = "libsecveGeometricMosaic.so";
        this.TAG = "GeometricMosaic_Renderer";
    }
}