package com.samsung.android.visualeffect.lock.common;

import android.content.Context;

import com.samsung.android.visualeffect.EffectType;
import com.samsung.android.visualeffect.common.GLTextureView;
import com.samsung.android.visualeffect.lock.abstracttile.AbstractTileRenderer;
import com.samsung.android.visualeffect.lock.brilliantcut.BrilliantCutRenderer;
import com.samsung.android.visualeffect.lock.brilliantring.BrilliantRingRenderer;
import com.samsung.android.visualeffect.lock.geometricmosaic.GeometricMosaicRenderer;
import com.samsung.android.visualeffect.lock.watercolor.WaterColorRenderer;

public class RenderManager {
    // TODO: add another effect renderers
    public static GLTextureViewRenderer getInstance(Context context, int argv, GLTextureView view) {
        if (argv == EffectType.ABSTRACT_TILES) {
            return new AbstractTileRenderer(context, view);
        }
        if (argv == EffectType.GEOMETRIC_MOSAIC) {
            return new GeometricMosaicRenderer(context, view);
        }
        if (argv == EffectType.WATERCOLOR) {
            return new WaterColorRenderer(context, view);
        }
        if (argv == EffectType.BRILLIANT_CUT) {
            return new BrilliantCutRenderer(context, view);
        }
        else if (argv == EffectType.BRILLIANT_RING) {
            return new BrilliantRingRenderer(context, view);
        }
        return null;
    }
}
