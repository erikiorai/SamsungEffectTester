package com.android.systemui.monet;

import com.android.internal.graphics.cam.Cam;
import com.android.systemui.monet.Hue;
import java.util.List;
import kotlin.Pair;
import kotlin.collections.CollectionsKt__CollectionsKt;

/* loaded from: mainsysui33.jar:com/android/systemui/monet/Style.class */
public enum Style {
    SPRITZ(new CoreSpec(new TonalSpec(new Hue() { // from class: com.android.systemui.monet.HueSource
        @Override // com.android.systemui.monet.Hue
        public double get(Cam cam) {
            return cam.getHue();
        }
    }, new Chroma(12.0d) { // from class: com.android.systemui.monet.ChromaConstant
        public final double chroma;

        {
            this.chroma = r5;
        }

        @Override // com.android.systemui.monet.Chroma
        public double get(Cam cam) {
            return this.chroma;
        }
    }), new TonalSpec(new Hue() { // from class: com.android.systemui.monet.HueSource
        @Override // com.android.systemui.monet.Hue
        public double get(Cam cam) {
            return cam.getHue();
        }
    }, new Chroma(8.0d) { // from class: com.android.systemui.monet.ChromaConstant
        public final double chroma;

        {
            this.chroma = r5;
        }

        @Override // com.android.systemui.monet.Chroma
        public double get(Cam cam) {
            return this.chroma;
        }
    }), new TonalSpec(new Hue() { // from class: com.android.systemui.monet.HueSource
        @Override // com.android.systemui.monet.Hue
        public double get(Cam cam) {
            return cam.getHue();
        }
    }, new Chroma(16.0d) { // from class: com.android.systemui.monet.ChromaConstant
        public final double chroma;

        {
            this.chroma = r5;
        }

        @Override // com.android.systemui.monet.Chroma
        public double get(Cam cam) {
            return this.chroma;
        }
    }), new TonalSpec(new Hue() { // from class: com.android.systemui.monet.HueSource
        @Override // com.android.systemui.monet.Hue
        public double get(Cam cam) {
            return cam.getHue();
        }
    }, new Chroma(2.0d) { // from class: com.android.systemui.monet.ChromaConstant
        public final double chroma;

        {
            this.chroma = r5;
        }

        @Override // com.android.systemui.monet.Chroma
        public double get(Cam cam) {
            return this.chroma;
        }
    }), new TonalSpec(new Hue() { // from class: com.android.systemui.monet.HueSource
        @Override // com.android.systemui.monet.Hue
        public double get(Cam cam) {
            return cam.getHue();
        }
    }, new Chroma(2.0d) { // from class: com.android.systemui.monet.ChromaConstant
        public final double chroma;

        {
            this.chroma = r5;
        }

        @Override // com.android.systemui.monet.Chroma
        public double get(Cam cam) {
            return this.chroma;
        }
    }))),
    TONAL_SPOT(new CoreSpec(new TonalSpec(new Hue() { // from class: com.android.systemui.monet.HueSource
        @Override // com.android.systemui.monet.Hue
        public double get(Cam cam) {
            return cam.getHue();
        }
    }, new Chroma(36.0d) { // from class: com.android.systemui.monet.ChromaConstant
        public final double chroma;

        {
            this.chroma = r5;
        }

        @Override // com.android.systemui.monet.Chroma
        public double get(Cam cam) {
            return this.chroma;
        }
    }), new TonalSpec(new Hue() { // from class: com.android.systemui.monet.HueSource
        @Override // com.android.systemui.monet.Hue
        public double get(Cam cam) {
            return cam.getHue();
        }
    }, new Chroma(16.0d) { // from class: com.android.systemui.monet.ChromaConstant
        public final double chroma;

        {
            this.chroma = r5;
        }

        @Override // com.android.systemui.monet.Chroma
        public double get(Cam cam) {
            return this.chroma;
        }
    }), new TonalSpec(new Hue(60.0d) { // from class: com.android.systemui.monet.HueAdd
        public final double amountDegrees;

        {
            this.amountDegrees = r5;
        }

        @Override // com.android.systemui.monet.Hue
        public double get(Cam cam) {
            return ColorScheme.Companion.wrapDegreesDouble(cam.getHue() + this.amountDegrees);
        }
    }, new Chroma(24.0d) { // from class: com.android.systemui.monet.ChromaConstant
        public final double chroma;

        {
            this.chroma = r5;
        }

        @Override // com.android.systemui.monet.Chroma
        public double get(Cam cam) {
            return this.chroma;
        }
    }), new TonalSpec(new Hue() { // from class: com.android.systemui.monet.HueSource
        @Override // com.android.systemui.monet.Hue
        public double get(Cam cam) {
            return cam.getHue();
        }
    }, new Chroma(4.0d) { // from class: com.android.systemui.monet.ChromaConstant
        public final double chroma;

        {
            this.chroma = r5;
        }

        @Override // com.android.systemui.monet.Chroma
        public double get(Cam cam) {
            return this.chroma;
        }
    }), new TonalSpec(new Hue() { // from class: com.android.systemui.monet.HueSource
        @Override // com.android.systemui.monet.Hue
        public double get(Cam cam) {
            return cam.getHue();
        }
    }, new Chroma(8.0d) { // from class: com.android.systemui.monet.ChromaConstant
        public final double chroma;

        {
            this.chroma = r5;
        }

        @Override // com.android.systemui.monet.Chroma
        public double get(Cam cam) {
            return this.chroma;
        }
    }))),
    VIBRANT(new CoreSpec(new TonalSpec(new Hue() { // from class: com.android.systemui.monet.HueSource
        @Override // com.android.systemui.monet.Hue
        public double get(Cam cam) {
            return cam.getHue();
        }
    }, new Chroma() { // from class: com.android.systemui.monet.ChromaMaxOut
        @Override // com.android.systemui.monet.Chroma
        public double get(Cam cam) {
            return 130.0d;
        }
    }), new TonalSpec(new Hue() { // from class: com.android.systemui.monet.HueVibrantSecondary
        public final List<Pair<Integer, Integer>> hueToRotations = CollectionsKt__CollectionsKt.listOf(new Pair[]{new Pair(0, 18), new Pair(41, 15), new Pair(61, 10), new Pair(101, 12), new Pair(131, 15), new Pair(181, 18), new Pair(251, 15), new Pair(301, 12), new Pair(360, 12)});

        @Override // com.android.systemui.monet.Hue
        public double get(Cam cam) {
            return getHueRotation(cam.getHue(), this.hueToRotations);
        }

        public double getHueRotation(float f, List<Pair<Integer, Integer>> list) {
            return Hue.DefaultImpls.getHueRotation(this, f, list);
        }
    }, new Chroma(24.0d) { // from class: com.android.systemui.monet.ChromaConstant
        public final double chroma;

        {
            this.chroma = r5;
        }

        @Override // com.android.systemui.monet.Chroma
        public double get(Cam cam) {
            return this.chroma;
        }
    }), new TonalSpec(new Hue() { // from class: com.android.systemui.monet.HueVibrantTertiary
        public final List<Pair<Integer, Integer>> hueToRotations = CollectionsKt__CollectionsKt.listOf(new Pair[]{new Pair(0, 35), new Pair(41, 30), new Pair(61, 20), new Pair(101, 25), new Pair(131, 30), new Pair(181, 35), new Pair(251, 30), new Pair(301, 25), new Pair(360, 25)});

        @Override // com.android.systemui.monet.Hue
        public double get(Cam cam) {
            return getHueRotation(cam.getHue(), this.hueToRotations);
        }

        public double getHueRotation(float f, List<Pair<Integer, Integer>> list) {
            return Hue.DefaultImpls.getHueRotation(this, f, list);
        }
    }, new Chroma(32.0d) { // from class: com.android.systemui.monet.ChromaConstant
        public final double chroma;

        {
            this.chroma = r5;
        }

        @Override // com.android.systemui.monet.Chroma
        public double get(Cam cam) {
            return this.chroma;
        }
    }), new TonalSpec(new Hue() { // from class: com.android.systemui.monet.HueSource
        @Override // com.android.systemui.monet.Hue
        public double get(Cam cam) {
            return cam.getHue();
        }
    }, new Chroma(10.0d) { // from class: com.android.systemui.monet.ChromaConstant
        public final double chroma;

        {
            this.chroma = r5;
        }

        @Override // com.android.systemui.monet.Chroma
        public double get(Cam cam) {
            return this.chroma;
        }
    }), new TonalSpec(new Hue() { // from class: com.android.systemui.monet.HueSource
        @Override // com.android.systemui.monet.Hue
        public double get(Cam cam) {
            return cam.getHue();
        }
    }, new Chroma(12.0d) { // from class: com.android.systemui.monet.ChromaConstant
        public final double chroma;

        {
            this.chroma = r5;
        }

        @Override // com.android.systemui.monet.Chroma
        public double get(Cam cam) {
            return this.chroma;
        }
    }))),
    EXPRESSIVE(new CoreSpec(new TonalSpec(new Hue(240.0d) { // from class: com.android.systemui.monet.HueAdd
        public final double amountDegrees;

        {
            this.amountDegrees = r5;
        }

        @Override // com.android.systemui.monet.Hue
        public double get(Cam cam) {
            return ColorScheme.Companion.wrapDegreesDouble(cam.getHue() + this.amountDegrees);
        }
    }, new Chroma(40.0d) { // from class: com.android.systemui.monet.ChromaConstant
        public final double chroma;

        {
            this.chroma = r5;
        }

        @Override // com.android.systemui.monet.Chroma
        public double get(Cam cam) {
            return this.chroma;
        }
    }), new TonalSpec(new Hue() { // from class: com.android.systemui.monet.HueExpressiveSecondary
        public final List<Pair<Integer, Integer>> hueToRotations = CollectionsKt__CollectionsKt.listOf(new Pair[]{new Pair(0, 45), new Pair(21, 95), new Pair(51, 45), new Pair(121, 20), new Pair(151, 45), new Pair(191, 90), new Pair(271, 45), new Pair(321, 45), new Pair(360, 45)});

        @Override // com.android.systemui.monet.Hue
        public double get(Cam cam) {
            return getHueRotation(cam.getHue(), this.hueToRotations);
        }

        public double getHueRotation(float f, List<Pair<Integer, Integer>> list) {
            return Hue.DefaultImpls.getHueRotation(this, f, list);
        }
    }, new Chroma(24.0d) { // from class: com.android.systemui.monet.ChromaConstant
        public final double chroma;

        {
            this.chroma = r5;
        }

        @Override // com.android.systemui.monet.Chroma
        public double get(Cam cam) {
            return this.chroma;
        }
    }), new TonalSpec(new Hue() { // from class: com.android.systemui.monet.HueExpressiveTertiary
        public final List<Pair<Integer, Integer>> hueToRotations = CollectionsKt__CollectionsKt.listOf(new Pair[]{new Pair(0, 120), new Pair(21, 120), new Pair(51, 20), new Pair(121, 45), new Pair(151, 20), new Pair(191, 15), new Pair(271, 20), new Pair(321, 120), new Pair(360, 120)});

        @Override // com.android.systemui.monet.Hue
        public double get(Cam cam) {
            return getHueRotation(cam.getHue(), this.hueToRotations);
        }

        public double getHueRotation(float f, List<Pair<Integer, Integer>> list) {
            return Hue.DefaultImpls.getHueRotation(this, f, list);
        }
    }, new Chroma(32.0d) { // from class: com.android.systemui.monet.ChromaConstant
        public final double chroma;

        {
            this.chroma = r5;
        }

        @Override // com.android.systemui.monet.Chroma
        public double get(Cam cam) {
            return this.chroma;
        }
    }), new TonalSpec(new Hue(15.0d) { // from class: com.android.systemui.monet.HueAdd
        public final double amountDegrees;

        {
            this.amountDegrees = r5;
        }

        @Override // com.android.systemui.monet.Hue
        public double get(Cam cam) {
            return ColorScheme.Companion.wrapDegreesDouble(cam.getHue() + this.amountDegrees);
        }
    }, new Chroma(8.0d) { // from class: com.android.systemui.monet.ChromaConstant
        public final double chroma;

        {
            this.chroma = r5;
        }

        @Override // com.android.systemui.monet.Chroma
        public double get(Cam cam) {
            return this.chroma;
        }
    }), new TonalSpec(new Hue(15.0d) { // from class: com.android.systemui.monet.HueAdd
        public final double amountDegrees;

        {
            this.amountDegrees = r5;
        }

        @Override // com.android.systemui.monet.Hue
        public double get(Cam cam) {
            return ColorScheme.Companion.wrapDegreesDouble(cam.getHue() + this.amountDegrees);
        }
    }, new Chroma(12.0d) { // from class: com.android.systemui.monet.ChromaConstant
        public final double chroma;

        {
            this.chroma = r5;
        }

        @Override // com.android.systemui.monet.Chroma
        public double get(Cam cam) {
            return this.chroma;
        }
    }))),
    RAINBOW(new CoreSpec(new TonalSpec(new Hue() { // from class: com.android.systemui.monet.HueSource
        @Override // com.android.systemui.monet.Hue
        public double get(Cam cam) {
            return cam.getHue();
        }
    }, new Chroma(48.0d) { // from class: com.android.systemui.monet.ChromaConstant
        public final double chroma;

        {
            this.chroma = r5;
        }

        @Override // com.android.systemui.monet.Chroma
        public double get(Cam cam) {
            return this.chroma;
        }
    }), new TonalSpec(new Hue() { // from class: com.android.systemui.monet.HueSource
        @Override // com.android.systemui.monet.Hue
        public double get(Cam cam) {
            return cam.getHue();
        }
    }, new Chroma(16.0d) { // from class: com.android.systemui.monet.ChromaConstant
        public final double chroma;

        {
            this.chroma = r5;
        }

        @Override // com.android.systemui.monet.Chroma
        public double get(Cam cam) {
            return this.chroma;
        }
    }), new TonalSpec(new Hue(60.0d) { // from class: com.android.systemui.monet.HueAdd
        public final double amountDegrees;

        {
            this.amountDegrees = r5;
        }

        @Override // com.android.systemui.monet.Hue
        public double get(Cam cam) {
            return ColorScheme.Companion.wrapDegreesDouble(cam.getHue() + this.amountDegrees);
        }
    }, new Chroma(24.0d) { // from class: com.android.systemui.monet.ChromaConstant
        public final double chroma;

        {
            this.chroma = r5;
        }

        @Override // com.android.systemui.monet.Chroma
        public double get(Cam cam) {
            return this.chroma;
        }
    }), new TonalSpec(new Hue() { // from class: com.android.systemui.monet.HueSource
        @Override // com.android.systemui.monet.Hue
        public double get(Cam cam) {
            return cam.getHue();
        }
    }, new Chroma(0.0d) { // from class: com.android.systemui.monet.ChromaConstant
        public final double chroma;

        {
            this.chroma = r5;
        }

        @Override // com.android.systemui.monet.Chroma
        public double get(Cam cam) {
            return this.chroma;
        }
    }), new TonalSpec(new Hue() { // from class: com.android.systemui.monet.HueSource
        @Override // com.android.systemui.monet.Hue
        public double get(Cam cam) {
            return cam.getHue();
        }
    }, new Chroma(0.0d) { // from class: com.android.systemui.monet.ChromaConstant
        public final double chroma;

        {
            this.chroma = r5;
        }

        @Override // com.android.systemui.monet.Chroma
        public double get(Cam cam) {
            return this.chroma;
        }
    }))),
    FRUIT_SALAD(new CoreSpec(new TonalSpec(new Hue(50.0d) { // from class: com.android.systemui.monet.HueSubtract
        public final double amountDegrees;

        {
            this.amountDegrees = r5;
        }

        @Override // com.android.systemui.monet.Hue
        public double get(Cam cam) {
            return ColorScheme.Companion.wrapDegreesDouble(cam.getHue() - this.amountDegrees);
        }
    }, new Chroma(48.0d) { // from class: com.android.systemui.monet.ChromaConstant
        public final double chroma;

        {
            this.chroma = r5;
        }

        @Override // com.android.systemui.monet.Chroma
        public double get(Cam cam) {
            return this.chroma;
        }
    }), new TonalSpec(new Hue(50.0d) { // from class: com.android.systemui.monet.HueSubtract
        public final double amountDegrees;

        {
            this.amountDegrees = r5;
        }

        @Override // com.android.systemui.monet.Hue
        public double get(Cam cam) {
            return ColorScheme.Companion.wrapDegreesDouble(cam.getHue() - this.amountDegrees);
        }
    }, new Chroma(36.0d) { // from class: com.android.systemui.monet.ChromaConstant
        public final double chroma;

        {
            this.chroma = r5;
        }

        @Override // com.android.systemui.monet.Chroma
        public double get(Cam cam) {
            return this.chroma;
        }
    }), new TonalSpec(new Hue() { // from class: com.android.systemui.monet.HueSource
        @Override // com.android.systemui.monet.Hue
        public double get(Cam cam) {
            return cam.getHue();
        }
    }, new Chroma(36.0d) { // from class: com.android.systemui.monet.ChromaConstant
        public final double chroma;

        {
            this.chroma = r5;
        }

        @Override // com.android.systemui.monet.Chroma
        public double get(Cam cam) {
            return this.chroma;
        }
    }), new TonalSpec(new Hue() { // from class: com.android.systemui.monet.HueSource
        @Override // com.android.systemui.monet.Hue
        public double get(Cam cam) {
            return cam.getHue();
        }
    }, new Chroma(10.0d) { // from class: com.android.systemui.monet.ChromaConstant
        public final double chroma;

        {
            this.chroma = r5;
        }

        @Override // com.android.systemui.monet.Chroma
        public double get(Cam cam) {
            return this.chroma;
        }
    }), new TonalSpec(new Hue() { // from class: com.android.systemui.monet.HueSource
        @Override // com.android.systemui.monet.Hue
        public double get(Cam cam) {
            return cam.getHue();
        }
    }, new Chroma(16.0d) { // from class: com.android.systemui.monet.ChromaConstant
        public final double chroma;

        {
            this.chroma = r5;
        }

        @Override // com.android.systemui.monet.Chroma
        public double get(Cam cam) {
            return this.chroma;
        }
    }))),
    CONTENT(new CoreSpec(new TonalSpec(new Hue() { // from class: com.android.systemui.monet.HueSource
        @Override // com.android.systemui.monet.Hue
        public double get(Cam cam) {
            return cam.getHue();
        }
    }, new Chroma() { // from class: com.android.systemui.monet.ChromaSource
        @Override // com.android.systemui.monet.Chroma
        public double get(Cam cam) {
            return cam.getChroma();
        }
    }), new TonalSpec(new Hue() { // from class: com.android.systemui.monet.HueSource
        @Override // com.android.systemui.monet.Hue
        public double get(Cam cam) {
            return cam.getHue();
        }
    }, new Chroma(0.33d) { // from class: com.android.systemui.monet.ChromaMultiple
        public final double multiple;

        {
            this.multiple = r5;
        }

        @Override // com.android.systemui.monet.Chroma
        public double get(Cam cam) {
            return cam.getChroma() * this.multiple;
        }
    }), new TonalSpec(new Hue() { // from class: com.android.systemui.monet.HueSource
        @Override // com.android.systemui.monet.Hue
        public double get(Cam cam) {
            return cam.getHue();
        }
    }, new Chroma(0.66d) { // from class: com.android.systemui.monet.ChromaMultiple
        public final double multiple;

        {
            this.multiple = r5;
        }

        @Override // com.android.systemui.monet.Chroma
        public double get(Cam cam) {
            return cam.getChroma() * this.multiple;
        }
    }), new TonalSpec(new Hue() { // from class: com.android.systemui.monet.HueSource
        @Override // com.android.systemui.monet.Hue
        public double get(Cam cam) {
            return cam.getHue();
        }
    }, new Chroma(0.0833d) { // from class: com.android.systemui.monet.ChromaMultiple
        public final double multiple;

        {
            this.multiple = r5;
        }

        @Override // com.android.systemui.monet.Chroma
        public double get(Cam cam) {
            return cam.getChroma() * this.multiple;
        }
    }), new TonalSpec(new Hue() { // from class: com.android.systemui.monet.HueSource
        @Override // com.android.systemui.monet.Hue
        public double get(Cam cam) {
            return cam.getHue();
        }
    }, new Chroma(0.1666d) { // from class: com.android.systemui.monet.ChromaMultiple
        public final double multiple;

        {
            this.multiple = r5;
        }

        @Override // com.android.systemui.monet.Chroma
        public double get(Cam cam) {
            return cam.getChroma() * this.multiple;
        }
    }))),
    MONOCHROMATIC(new CoreSpec(new TonalSpec(new Hue() { // from class: com.android.systemui.monet.HueSource
        @Override // com.android.systemui.monet.Hue
        public double get(Cam cam) {
            return cam.getHue();
        }
    }, new Chroma(0.0d) { // from class: com.android.systemui.monet.ChromaConstant
        public final double chroma;

        {
            this.chroma = r5;
        }

        @Override // com.android.systemui.monet.Chroma
        public double get(Cam cam) {
            return this.chroma;
        }
    }), new TonalSpec(new Hue() { // from class: com.android.systemui.monet.HueSource
        @Override // com.android.systemui.monet.Hue
        public double get(Cam cam) {
            return cam.getHue();
        }
    }, new Chroma(0.0d) { // from class: com.android.systemui.monet.ChromaConstant
        public final double chroma;

        {
            this.chroma = r5;
        }

        @Override // com.android.systemui.monet.Chroma
        public double get(Cam cam) {
            return this.chroma;
        }
    }), new TonalSpec(new Hue() { // from class: com.android.systemui.monet.HueSource
        @Override // com.android.systemui.monet.Hue
        public double get(Cam cam) {
            return cam.getHue();
        }
    }, new Chroma(0.0d) { // from class: com.android.systemui.monet.ChromaConstant
        public final double chroma;

        {
            this.chroma = r5;
        }

        @Override // com.android.systemui.monet.Chroma
        public double get(Cam cam) {
            return this.chroma;
        }
    }), new TonalSpec(new Hue() { // from class: com.android.systemui.monet.HueSource
        @Override // com.android.systemui.monet.Hue
        public double get(Cam cam) {
            return cam.getHue();
        }
    }, new Chroma(0.0d) { // from class: com.android.systemui.monet.ChromaConstant
        public final double chroma;

        {
            this.chroma = r5;
        }

        @Override // com.android.systemui.monet.Chroma
        public double get(Cam cam) {
            return this.chroma;
        }
    }), new TonalSpec(new Hue() { // from class: com.android.systemui.monet.HueSource
        @Override // com.android.systemui.monet.Hue
        public double get(Cam cam) {
            return cam.getHue();
        }
    }, new Chroma(0.0d) { // from class: com.android.systemui.monet.ChromaConstant
        public final double chroma;

        {
            this.chroma = r5;
        }

        @Override // com.android.systemui.monet.Chroma
        public double get(Cam cam) {
            return this.chroma;
        }
    })));
    
    private final CoreSpec coreSpec;

    Style(CoreSpec coreSpec) {
        this.coreSpec = coreSpec;
    }

    public final CoreSpec getCoreSpec$frameworks__base__packages__SystemUI__monet__android_common__monet() {
        return this.coreSpec;
    }
}