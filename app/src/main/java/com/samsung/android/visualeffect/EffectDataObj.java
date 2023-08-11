package com.samsung.android.visualeffect;

import com.samsung.android.visualeffect.lock.data.LensFlareData;

/* loaded from: classes.dex */
public class EffectDataObj {
    //public CircleData circleData;
    //public IndigoDiffuseData indigoDiffuseData;
    public LensFlareData lensFlareData;
    /*public PoppingColorData poppingColorData;
    public RippleInkData rippleInkData;*/

    public void setEffect(int effect) {
        switch (effect) {
            /*case 1:
                this.poppingColorData = new PoppingColorData();
                return;
            case 2:
                this.circleData = new CircleData();
                return;
            case 3:
                this.poppingColorData = new PoppingColorData();
                return; */
            case EffectType.PARTICLE_MUSIC /* 4 */:
            case EffectType.WATERCOLOR /* 5 */:
            case EffectType.BRILLIANT_CUT /* 6 */:
            case EffectType.BRILLIANT_RING /* 7 */:
            case EffectType.BLIND /* 10 */:
            default:
                return; /*
            case EffectType.RIPPLE_INK:
                this.rippleInkData = new RippleInkData();
                return;
            case EffectType.INDIGO_DIFFUSE:
                this.indigoDiffuseData = new IndigoDiffuseData();
                return; */
            case EffectType.LENSFLARE:
                this.lensFlareData = new LensFlareData();
        }
    }
}