package com.samsung.android.visualeffect;

import com.samsung.android.visualeffect.lock.data.DropletData;
import com.samsung.android.visualeffect.lock.data.IndigoDiffuseData;
import com.samsung.android.visualeffect.lock.data.LensFlareData;
import com.samsung.android.visualeffect.lock.data.RippleInkData;
import com.samsung.android.visualeffect.lock.data.SparklingBullesData;

/* loaded from: classes.dex */
public class EffectDataObj {
    /*public CircleData circleData;
    public ColourDropletData colorDroplet;*/
    public IndigoDiffuseData indigoDiffuseData;
    //public PoppingColorData poppingColorData;
    public LensFlareData lensFlareData;
    public RippleInkData rippleInkData;
    public SparklingBullesData sparklingBubblesData;
    public DropletData dropletData;

    public void setEffect(int effect) {
        switch (effect) {
            case EffectType.CIRCLE:
                //this.circleData = new CircleData();
                //return;
            case EffectType.POPPING_COLOUR:
            case EffectType.PARTICLE_MUSIC:
            case EffectType.WATERCOLOR /* 5 */:
            case EffectType.BRILLIANT_CUT /* 6 */:
            case EffectType.BRILLIANT_RING /* 7 */:
            case EffectType.BLIND /* 10 */:
            case EffectType.WATER_DROPLET_TV:
            case EffectType.SPARKLING_BUBBLES_TV /* 15 */:
            default:
                return;
            case EffectType.RIPPLE_INK:
                this.rippleInkData = new RippleInkData();
                return;
            case EffectType.INDIGO_DIFFUSE:
                this.indigoDiffuseData = new IndigoDiffuseData();
                return;
            case EffectType.LENSFLARE /* 11 */:
                this.lensFlareData = new LensFlareData();
                return;
            case EffectType.WATER_DROPLET_GL:
            case EffectType.COLOUR_DROPLET_GL:
                this.dropletData = new DropletData();
                return;
            case EffectType.SPARKLING_BUBBLES_GL /* 14 */:
                this.sparklingBubblesData = new SparklingBullesData();
        }
    }
}