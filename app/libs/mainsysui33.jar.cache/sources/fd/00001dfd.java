package com.android.systemui.monet;

import android.app.WallpaperColors;
import android.frameworks.stats.IStats;
import android.graphics.Color;
import com.android.internal.graphics.ColorUtils;
import com.android.internal.graphics.cam.Cam;
import com.android.internal.graphics.cam.CamUtils;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import kotlin.collections.CollectionsKt__CollectionsJVMKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt__MutableCollectionsJVMKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.MapsKt__MapsJVMKt;
import kotlin.comparisons.ComparisonsKt__ComparisonsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.math.MathKt__MathJVMKt;
import kotlin.text.StringsKt__StringsKt;

/* loaded from: mainsysui33.jar:com/android/systemui/monet/ColorScheme.class */
public final class ColorScheme {
    public static final Companion Companion = new Companion(null);
    public final List<Integer> accent1;
    public final List<Integer> accent2;
    public final List<Integer> accent3;
    public final boolean darkTheme;
    public final List<Integer> neutral1;
    public final List<Integer> neutral2;
    public final int seed;
    public final Style style;

    /* loaded from: mainsysui33.jar:com/android/systemui/monet/ColorScheme$Companion.class */
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static /* synthetic */ int getSeedColor$default(Companion companion, WallpaperColors wallpaperColors, boolean z, int i, Object obj) {
            if ((i & 2) != 0) {
                z = true;
            }
            return companion.getSeedColor(wallpaperColors, z);
        }

        public static /* synthetic */ List getSeedColors$default(Companion companion, WallpaperColors wallpaperColors, boolean z, int i, Object obj) {
            if ((i & 2) != 0) {
                z = true;
            }
            return companion.getSeedColors(wallpaperColors, z);
        }

        public final int getSeedColor(WallpaperColors wallpaperColors) {
            return getSeedColor$default(this, wallpaperColors, false, 2, null);
        }

        public final int getSeedColor(WallpaperColors wallpaperColors, boolean z) {
            return ((Number) CollectionsKt___CollectionsKt.first(getSeedColors(wallpaperColors, z))).intValue();
        }

        public final List<Integer> getSeedColors(WallpaperColors wallpaperColors) {
            return getSeedColors$default(this, wallpaperColors, false, 2, null);
        }

        /* JADX WARN: Code restructure failed: missing block: B:103:0x04e8, code lost:
            if (r15 == 15) goto L112;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final List<Integer> getSeedColors(WallpaperColors wallpaperColors, boolean z) {
            Object obj;
            LinkedHashMap linkedHashMap;
            Object obj2;
            Iterator it = wallpaperColors.getAllColors().values().iterator();
            if (it.hasNext()) {
                Integer num = it.next();
                while (true) {
                    obj = num;
                    if (!it.hasNext()) {
                        break;
                    }
                    num = Integer.valueOf(((Integer) obj).intValue() + ((Integer) it.next()).intValue());
                }
                double intValue = ((Number) obj).intValue();
                boolean z2 = intValue == 0.0d;
                if (z2) {
                    List<Color> mainColors = wallpaperColors.getMainColors();
                    ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(mainColors, 10));
                    for (Color color : mainColors) {
                        arrayList.add(Integer.valueOf(color.toArgb()));
                    }
                    List distinct = CollectionsKt___CollectionsKt.distinct(arrayList);
                    ArrayList arrayList2 = new ArrayList();
                    for (Object obj3 : distinct) {
                        if (!z || Cam.fromInt(((Number) obj3).intValue()).getChroma() >= 5.0f) {
                            arrayList2.add(obj3);
                        }
                    }
                    List<Integer> list = CollectionsKt___CollectionsKt.toList(arrayList2);
                    List<Integer> list2 = list;
                    if (list.isEmpty()) {
                        list2 = CollectionsKt__CollectionsJVMKt.listOf(-14979341);
                    }
                    return list2;
                }
                Map allColors = wallpaperColors.getAllColors();
                Map<Integer, Double> linkedHashMap2 = new LinkedHashMap<>(MapsKt__MapsJVMKt.mapCapacity(allColors.size()));
                for (Map.Entry entry : allColors.entrySet()) {
                    linkedHashMap2.put(entry.getKey(), Double.valueOf(((Number) entry.getValue()).intValue() / intValue));
                }
                Map allColors2 = wallpaperColors.getAllColors();
                Map<Integer, ? extends Cam> linkedHashMap3 = new LinkedHashMap<>(MapsKt__MapsJVMKt.mapCapacity(allColors2.size()));
                for (Map.Entry entry2 : allColors2.entrySet()) {
                    linkedHashMap3.put(entry2.getKey(), Cam.fromInt(((Number) entry2.getKey()).intValue()));
                }
                List<Double> huePopulations = huePopulations(linkedHashMap3, linkedHashMap2, z);
                Map allColors3 = wallpaperColors.getAllColors();
                LinkedHashMap linkedHashMap4 = new LinkedHashMap(MapsKt__MapsJVMKt.mapCapacity(allColors3.size()));
                for (Map.Entry entry3 : allColors3.entrySet()) {
                    Object key = entry3.getKey();
                    Object obj4 = linkedHashMap3.get(entry3.getKey());
                    Intrinsics.checkNotNull(obj4);
                    int roundToInt = MathKt__MathJVMKt.roundToInt(((Cam) obj4).getHue());
                    int i = roundToInt - 15;
                    int i2 = roundToInt + 15;
                    double d = 0.0d;
                    double d2 = 0.0d;
                    if (i <= i2) {
                        while (true) {
                            d += huePopulations.get(ColorScheme.Companion.wrapDegrees(i)).doubleValue();
                            d2 = d;
                            if (i != i2) {
                                i++;
                            }
                        }
                    }
                    linkedHashMap4.put(key, Double.valueOf(d2));
                }
                if (z) {
                    LinkedHashMap linkedHashMap5 = new LinkedHashMap();
                    Iterator it2 = linkedHashMap3.entrySet().iterator();
                    while (true) {
                        linkedHashMap = linkedHashMap5;
                        if (!it2.hasNext()) {
                            break;
                        }
                        Map.Entry entry4 = (Map.Entry) it2.next();
                        Cam cam = (Cam) entry4.getValue();
                        Object obj5 = linkedHashMap4.get(entry4.getKey());
                        Intrinsics.checkNotNull(obj5);
                        if (cam.getChroma() >= 5.0f && (z2 || ((Number) obj5).doubleValue() > 0.01d)) {
                            linkedHashMap5.put(entry4.getKey(), entry4.getValue());
                        }
                    }
                } else {
                    linkedHashMap = linkedHashMap3;
                }
                LinkedHashMap linkedHashMap6 = new LinkedHashMap(MapsKt__MapsJVMKt.mapCapacity(linkedHashMap.size()));
                for (Map.Entry entry5 : linkedHashMap.entrySet()) {
                    Object key2 = entry5.getKey();
                    Companion companion = ColorScheme.Companion;
                    Cam cam2 = (Cam) entry5.getValue();
                    Object obj6 = linkedHashMap4.get(entry5.getKey());
                    Intrinsics.checkNotNull(obj6);
                    linkedHashMap6.put(key2, Double.valueOf(companion.score(cam2, ((Number) obj6).doubleValue())));
                }
                List mutableList = CollectionsKt___CollectionsKt.toMutableList(linkedHashMap6.entrySet());
                if (mutableList.size() > 1) {
                    CollectionsKt__MutableCollectionsJVMKt.sortWith(mutableList, new Comparator() { // from class: com.android.systemui.monet.ColorScheme$Companion$getSeedColors$$inlined$sortByDescending$1
                        @Override // java.util.Comparator
                        public final int compare(T t, T t2) {
                            return ComparisonsKt__ComparisonsKt.compareValues((Double) ((Map.Entry) t2).getValue(), (Double) ((Map.Entry) t).getValue());
                        }
                    });
                }
                ArrayList arrayList3 = new ArrayList();
                int i3 = 90;
                loop8: while (true) {
                    arrayList3.clear();
                    Iterator it3 = mutableList.iterator();
                    while (true) {
                        if (!it3.hasNext()) {
                            break;
                        }
                        Integer num2 = (Integer) ((Map.Entry) it3.next()).getKey();
                        Iterator it4 = arrayList3.iterator();
                        while (true) {
                            if (!it4.hasNext()) {
                                obj2 = null;
                                break;
                            }
                            obj2 = it4.next();
                            int intValue2 = ((Number) obj2).intValue();
                            Object obj7 = linkedHashMap3.get(num2);
                            Intrinsics.checkNotNull(obj7);
                            float hue = ((Cam) obj7).getHue();
                            Object obj8 = linkedHashMap3.get(Integer.valueOf(intValue2));
                            Intrinsics.checkNotNull(obj8);
                            if (ColorScheme.Companion.hueDiff(hue, ((Cam) obj8).getHue()) < ((float) i3)) {
                                break;
                            }
                        }
                        if (!(obj2 != null)) {
                            arrayList3.add(num2);
                            if (arrayList3.size() >= 4) {
                                break loop8;
                            }
                        }
                    }
                    i3--;
                }
                if (arrayList3.isEmpty()) {
                    arrayList3.add(-14979341);
                }
                return arrayList3;
            }
            throw new UnsupportedOperationException("Empty collection can't be reduced.");
        }

        public final float hueDiff(float f, float f2) {
            return 180.0f - Math.abs(Math.abs(f - f2) - 180.0f);
        }

        public final List<Double> huePopulations(Map<Integer, ? extends Cam> map, Map<Integer, Double> map2, boolean z) {
            ArrayList arrayList = new ArrayList(360);
            for (int i = 0; i < 360; i++) {
                arrayList.add(Double.valueOf(0.0d));
            }
            List<Double> mutableList = CollectionsKt___CollectionsKt.toMutableList(arrayList);
            for (Map.Entry<Integer, Double> entry : map2.entrySet()) {
                Double d = map2.get(entry.getKey());
                Intrinsics.checkNotNull(d);
                double doubleValue = d.doubleValue();
                Cam cam = map.get(entry.getKey());
                Intrinsics.checkNotNull(cam);
                Cam cam2 = cam;
                int roundToInt = MathKt__MathJVMKt.roundToInt(cam2.getHue()) % 360;
                if (!z || cam2.getChroma() > 5.0f) {
                    mutableList.set(roundToInt, Double.valueOf(mutableList.get(roundToInt).doubleValue() + doubleValue));
                }
            }
            return mutableList;
        }

        public final String humanReadable(String str, List<Integer> list) {
            List<Integer> list2 = list;
            ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list2, 10));
            for (Number number : list2) {
                arrayList.add(ColorScheme.Companion.stringForColor(number.intValue()));
            }
            String joinToString$default = CollectionsKt___CollectionsKt.joinToString$default(arrayList, "\n", (CharSequence) null, (CharSequence) null, 0, (CharSequence) null, new Function1<String, CharSequence>() { // from class: com.android.systemui.monet.ColorScheme$Companion$humanReadable$2
                /* JADX DEBUG: Method merged with bridge method */
                public final CharSequence invoke(String str2) {
                    return str2;
                }
            }, 30, (Object) null);
            return str + "\n" + joinToString$default;
        }

        public final double score(Cam cam, double d) {
            double d2;
            float chroma;
            if (cam.getChroma() < 48.0f) {
                d2 = 0.1d;
                chroma = cam.getChroma();
            } else {
                d2 = 0.3d;
                chroma = cam.getChroma();
            }
            return ((chroma - 48.0f) * d2) + (d * 70.0d);
        }

        public final String stringForColor(int i) {
            Cam fromInt = Cam.fromInt(i);
            return ("H" + StringsKt__StringsKt.padEnd$default(String.valueOf(MathKt__MathJVMKt.roundToInt(fromInt.getHue())), 4, (char) 0, 2, (Object) null)) + ("C" + StringsKt__StringsKt.padEnd$default(String.valueOf(MathKt__MathJVMKt.roundToInt(fromInt.getChroma())), 4, (char) 0, 2, (Object) null)) + ("T" + StringsKt__StringsKt.padEnd$default(String.valueOf(MathKt__MathJVMKt.roundToInt(CamUtils.lstarFromInt(i))), 4, (char) 0, 2, (Object) null)) + " = #" + StringsKt__StringsKt.padStart(Integer.toHexString(i & IStats.Stub.TRANSACTION_getInterfaceVersion), 6, '0').toUpperCase(Locale.ROOT);
        }

        public final int wrapDegrees(int i) {
            int i2;
            if (i < 0) {
                i2 = (i % 360) + 360;
            } else {
                i2 = i;
                if (i >= 360) {
                    i2 = i % 360;
                }
            }
            return i2;
        }

        public final double wrapDegreesDouble(double d) {
            double d2;
            if (d < 0.0d) {
                double d3 = 360;
                d2 = (d % d3) + d3;
            } else {
                d2 = d;
                if (d >= 360.0d) {
                    d2 = d % 360;
                }
            }
            return d2;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:8:0x0039, code lost:
        if (r0.getChroma() < 5.0f) goto L3;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public ColorScheme(int i, boolean z, Style style) {
        int i2;
        this.seed = i;
        this.darkTheme = z;
        this.style = style;
        Cam fromInt = Cam.fromInt(i);
        if (i != 0) {
            i2 = i;
            if (style != Style.CONTENT) {
                i2 = i;
            }
            Cam fromInt2 = Cam.fromInt(i2);
            this.accent1 = style.getCoreSpec$frameworks__base__packages__SystemUI__monet__android_common__monet().getA1().shades(fromInt2);
            this.accent2 = style.getCoreSpec$frameworks__base__packages__SystemUI__monet__android_common__monet().getA2().shades(fromInt2);
            this.accent3 = style.getCoreSpec$frameworks__base__packages__SystemUI__monet__android_common__monet().getA3().shades(fromInt2);
            this.neutral1 = style.getCoreSpec$frameworks__base__packages__SystemUI__monet__android_common__monet().getN1().shades(fromInt2);
            this.neutral2 = style.getCoreSpec$frameworks__base__packages__SystemUI__monet__android_common__monet().getN2().shades(fromInt2);
        }
        i2 = -14979341;
        Cam fromInt22 = Cam.fromInt(i2);
        this.accent1 = style.getCoreSpec$frameworks__base__packages__SystemUI__monet__android_common__monet().getA1().shades(fromInt22);
        this.accent2 = style.getCoreSpec$frameworks__base__packages__SystemUI__monet__android_common__monet().getA2().shades(fromInt22);
        this.accent3 = style.getCoreSpec$frameworks__base__packages__SystemUI__monet__android_common__monet().getA3().shades(fromInt22);
        this.neutral1 = style.getCoreSpec$frameworks__base__packages__SystemUI__monet__android_common__monet().getN1().shades(fromInt22);
        this.neutral2 = style.getCoreSpec$frameworks__base__packages__SystemUI__monet__android_common__monet().getN2().shades(fromInt22);
    }

    public ColorScheme(WallpaperColors wallpaperColors, boolean z) {
        this(wallpaperColors, z, null, 4, null);
    }

    public ColorScheme(WallpaperColors wallpaperColors, boolean z, Style style) {
        this(Companion.getSeedColor(wallpaperColors, style != Style.CONTENT), z, style);
    }

    public /* synthetic */ ColorScheme(WallpaperColors wallpaperColors, boolean z, Style style, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(wallpaperColors, z, (i & 4) != 0 ? Style.TONAL_SPOT : style);
    }

    public static final int getSeedColor(WallpaperColors wallpaperColors) {
        return Companion.getSeedColor(wallpaperColors);
    }

    public static final List<Integer> getSeedColors(WallpaperColors wallpaperColors) {
        return Companion.getSeedColors(wallpaperColors);
    }

    public final List<Integer> getAccent1() {
        return this.accent1;
    }

    public final List<Integer> getAccent2() {
        return this.accent2;
    }

    public final List<Integer> getAccent3() {
        return this.accent3;
    }

    public final List<Integer> getAllAccentColors() {
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(this.accent1);
        arrayList.addAll(this.accent2);
        arrayList.addAll(this.accent3);
        return arrayList;
    }

    public final List<Integer> getAllNeutralColors() {
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(this.neutral1);
        arrayList.addAll(this.neutral2);
        return arrayList;
    }

    public final int getBackgroundColor() {
        return ColorUtils.setAlphaComponent(this.neutral1.get(this.darkTheme ? 8 : 0).intValue(), 255);
    }

    public final List<Integer> getNeutral1() {
        return this.neutral1;
    }

    public final List<Integer> getNeutral2() {
        return this.neutral2;
    }

    public String toString() {
        Companion companion = Companion;
        String stringForColor = companion.stringForColor(this.seed);
        Style style = this.style;
        String humanReadable = companion.humanReadable("PRIMARY", this.accent1);
        String humanReadable2 = companion.humanReadable("SECONDARY", this.accent2);
        String humanReadable3 = companion.humanReadable("TERTIARY", this.accent3);
        String humanReadable4 = companion.humanReadable("NEUTRAL", this.neutral1);
        String humanReadable5 = companion.humanReadable("NEUTRAL VARIANT", this.neutral2);
        return "ColorScheme {\n  seed color: " + stringForColor + "\n  style: " + style + "\n  palettes: \n  " + humanReadable + "\n  " + humanReadable2 + "\n  " + humanReadable3 + "\n  " + humanReadable4 + "\n  " + humanReadable5 + "\n}";
    }
}