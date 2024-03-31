package com.samsung.android.visualeffect;

import android.content.Context;

import com.samsung.android.visualeffect.lock.CircleMorphingWrappedEffect;
import com.samsung.android.visualeffect.lock.abstracttile.AbstractTileEffect;
import com.samsung.android.visualeffect.lock.blind.BlindEffect;
import com.samsung.android.visualeffect.lock.brilliantcut.BrilliantCutEffect;
import com.samsung.android.visualeffect.lock.brilliantring.BrilliantRingEffect;
import com.samsung.android.visualeffect.lock.circleunlock.CircleUnlockEffect;
import com.samsung.android.visualeffect.lock.droplet.DropletEffect_GL;
import com.samsung.android.visualeffect.lock.geometricmosaic.GeometricMosaicEffect;
import com.samsung.android.visualeffect.lock.indigodiffusion.IndigoDiffusionEffect;
import com.samsung.android.visualeffect.lock.lensflare.LensFlareEffect;
import com.samsung.android.visualeffect.lock.particle.ParticleEffect;
import com.samsung.android.visualeffect.lock.particle.ParticleSpaceEffect;
import com.samsung.android.visualeffect.lock.rippleink.RippleInkEffect;
import com.samsung.android.visualeffect.lock.sparklingbubbles.SparklingBubblesEffect_GL;
import com.samsung.android.visualeffect.lock.watercolor.WaterColorEffect;

/* loaded from: classes.dex */
public class InnerViewManager {
    public static IEffectView getInstance(Context context, int argv) {
        if (argv == EffectType.ABSTRACT_TILES) {
            return new AbstractTileEffect(context);
        }
        else if (argv == EffectType.GEOMETRIC_MOSAIC) {
            return new GeometricMosaicEffect(context);
        }
        else if (argv == EffectType.CIRCLE) {
            return new CircleUnlockEffect(context);
        }
        else if (argv == EffectType.POPPING_COLOUR) {
            return new ParticleSpaceEffect(context);
        }
        else if (argv == EffectType.PARTICLE_MUSIC) {
            return new ParticleEffect(context);
        }
        else if (argv == EffectType.WATERCOLOR) {
            return new WaterColorEffect(context);
        }
        else if (argv == EffectType.BRILLIANT_CUT) {
            return new BrilliantCutEffect(context);
        }
        else if (argv == EffectType.BRILLIANT_RING) {
            return new BrilliantRingEffect(context);
        }
        else if (argv == EffectType.RIPPLE_INK) {
            return new RippleInkEffect(context);
        }
        else if (argv == EffectType.INDIGO_DIFFUSE) {
            return new IndigoDiffusionEffect(context);
        }
        else if (argv == EffectType.BLIND) {
            return new BlindEffect(context);
        }
        else if (argv == EffectType.LENSFLARE) {
            return new LensFlareEffect(context);
        }
        else if (argv == EffectType.WATER_DROPLET_GL) {
            return new DropletEffect_GL(context, false);
        } /*
        else if (argv == 13) {
            return new WaterDropletEffect_TV(context);
        } */
        else if (argv == EffectType.SPARKLING_BUBBLES_GL) {
            return new SparklingBubblesEffect_GL(context);
        }
        /*else if (argv == 15) {
            return new SparklingBubblesEffect_TV(context);
        }*/
        else if (argv == EffectType.COLOUR_DROPLET_GL) {
            return new DropletEffect_GL(context, true);
        }
        /*else if (argv == 17) {
            return new ColourDropletEffect_TV(context);
        }*/
        else if (argv == 18) {
            return new CircleMorphingWrappedEffect(context);
        }
        /* todo else if (argv == 19) {
            return new EffectViewPoppingColor(context);
        }
        else if (argv == 20) {
            return new EffectViewRectangleTraveller(context);
        }
        else if (argv == 21) {
            return new EffectViewBouncingColor(context);
        }*/

        return null;
    }
}