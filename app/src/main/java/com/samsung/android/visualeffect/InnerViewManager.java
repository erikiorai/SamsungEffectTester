package com.samsung.android.visualeffect;

import android.content.Context;

import com.samsung.android.visualeffect.lock.abstracttile.AbstractTileEffect;
import com.samsung.android.visualeffect.lock.brilliantring.BrilliantRingEffect;
import com.samsung.android.visualeffect.lock.lensflare.LensFlareEffect;

/* loaded from: classes.dex */
public class InnerViewManager {
    public static IEffectView getInstance(Context context, int argv) {
        if (argv == 0) {
            return new AbstractTileEffect(context);
        }/*
        else if (argv == 1) {
            return new GeometricMosaicEffect(context);
        }
        else if (argv == 2) {
            return new CircleUnlockEffect(context);
        }
        else if (argv == 3) {
            return new ParticleSpaceEffect(context);
        }
        else if (argv == 4) {
            return new ParticleEffect(context);
        }
        else if (argv == 5) {
            return new WaterColorEffect(context);
        }
        else if (argv == 6) {
            return new BrilliantCutEffect(context);
        } */
        else if (argv == 7) {
            return new BrilliantRingEffect(context);
        } /*
        else if (argv == 8) {
            return new RippleInkEffect(context);
        }
        else if (argv == 9) {
            return new IndigoDiffusionEffect(context);
        }
        else if (argv == 10) {
            return new BlindEffect(context);
        } */
        else if (argv == 11) {
            return new LensFlareEffect(context);
        }
        /* else if (argv == 12) {
            return new WaterDropletEffect_GL(context);
        }
        else if (argv == 13) {
            return new WaterDropletEffect_TV(context);
        }
        else if (argv == 14) {
            return new SparklingBubblesEffect_GL(context);
        }
        else if (argv == 15) {
            return new SparklingBubblesEffect_TV(context);
        }
        else if (argv == 16) {
            return new ColourDropletEffect_GL(context);
        }
        else if (argv == 17) {
            return new ColourDropletEffect_TV(context);
        }
*/
        return null;
    }
}