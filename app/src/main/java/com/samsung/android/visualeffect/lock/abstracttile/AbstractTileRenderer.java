package com.samsung.android.visualeffect.lock.abstracttile;

import android.content.Context;

import com.samsung.android.visualeffect.common.GLTextureView;
import com.samsung.android.visualeffect.lock.common.GLTextureViewRenderer;

/* loaded from: classes.dex */
public class AbstractTileRenderer extends GLTextureViewRenderer {
    public AbstractTileRenderer(Context context, GLTextureView view) {
        this.mContext = context;
        this.mGlView = view;
        this.mLibName = "libsecveAbstractTile.so";
        this.TAG = "AbstractTile Renderer";
    }
}