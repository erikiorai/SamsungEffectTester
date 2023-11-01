package com.android.systemui.monet;

import com.android.internal.graphics.cam.Cam;
import java.util.List;
import kotlin.collections.ArraysKt___ArraysKt;

/* loaded from: mainsysui33.jar:com/android/systemui/monet/TonalSpec.class */
public final class TonalSpec {
    public final Chroma chroma;
    public final Hue hue;

    public TonalSpec(Hue hue, Chroma chroma) {
        this.hue = hue;
        this.chroma = chroma;
    }

    public final List<Integer> shades(Cam cam) {
        return ArraysKt___ArraysKt.toList(Shades.of((float) this.hue.get(cam), (float) this.chroma.get(cam)));
    }
}