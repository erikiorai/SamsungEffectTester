package com.samsung.android.visualeffect.lock.geometricmosaic;

import android.content.Context;
import com.samsung.android.visualeffect.lock.common.LockBGEffect;

public class GeometricMosaicEffect extends LockBGEffect {
    public GeometricMosaicEffect(Context context) {
        super(context);
        this.TAG = "GeometricMosaic_View";
        this.veContext = context;
        setEffectRenderer(1);
    }
}