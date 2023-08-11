package com.samsung.android.visualeffect.lock.common;

import android.content.Context;

import com.samsung.android.visualeffect.common.GLTextureView;
import com.samsung.android.visualeffect.lock.abstracttile.AbstractTileRenderer;
import com.samsung.android.visualeffect.lock.brilliantring.BrilliantRingRenderer;

public class RenderManager {
    // TODO: add another effect renderers
    public static GLTextureViewRenderer getInstance(Context context, int argv, GLTextureView view) {
        if (argv == 0) {
            return new AbstractTileRenderer(context, view);
        }/*
        if (argv == 1) {
            return new GeometricMosaicRenderer(context, view);
        }
        if (argv == 5) {
            return new WaterColorRenderer(context, view);
        }
        if (argv == 6) {
            return new BrilliantCutRenderer(context, view);
        }*/
        else if (argv == 7) {
            return new BrilliantRingRenderer(context, view);
        }
        return null;
    }
}
