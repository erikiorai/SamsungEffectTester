package com.android.systemui.animation;

import android.graphics.fonts.Font;
import android.graphics.fonts.FontVariationAxis;
import android.util.MathUtils;
import com.android.settingslib.widget.ActionBarShadowController;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import kotlin.Unit;
import kotlin.collections.ArraysKt___ArraysJvmKt;
import kotlin.collections.CollectionsKt__MutableCollectionsJVMKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.comparisons.ComparisonsKt__ComparisonsKt;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt___RangesKt;

/* loaded from: mainsysui33.jar:com/android/systemui/animation/FontInterpolator.class */
public final class FontInterpolator {
    public static final Companion Companion = new Companion(null);
    public static final FontVariationAxis[] EMPTY_AXES = new FontVariationAxis[0];
    public final HashMap<InterpKey, Font> interpCache = new HashMap<>();
    public final HashMap<VarFontKey, Font> verFontCache = new HashMap<>();
    public final InterpKey tmpInterpKey = new InterpKey(null, null, ActionBarShadowController.ELEVATION_LOW);
    public final VarFontKey tmpVarFontKey = new VarFontKey(0, 0, new ArrayList());

    /* loaded from: mainsysui33.jar:com/android/systemui/animation/FontInterpolator$Companion.class */
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final boolean canInterpolate(Font font, Font font2) {
            return font.getTtcIndex() == font2.getTtcIndex() && font.getSourceIdentifier() == font2.getSourceIdentifier();
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/animation/FontInterpolator$InterpKey.class */
    public static final class InterpKey {
        public Font l;
        public float progress;
        public Font r;

        public InterpKey(Font font, Font font2, float f) {
            this.l = font;
            this.r = font2;
            this.progress = f;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj instanceof InterpKey) {
                InterpKey interpKey = (InterpKey) obj;
                return Intrinsics.areEqual(this.l, interpKey.l) && Intrinsics.areEqual(this.r, interpKey.r) && Float.compare(this.progress, interpKey.progress) == 0;
            }
            return false;
        }

        public int hashCode() {
            Font font = this.l;
            int i = 0;
            int hashCode = font == null ? 0 : font.hashCode();
            Font font2 = this.r;
            if (font2 != null) {
                i = font2.hashCode();
            }
            return (((hashCode * 31) + i) * 31) + Float.hashCode(this.progress);
        }

        public final void set(Font font, Font font2, float f) {
            this.l = font;
            this.r = font2;
            this.progress = f;
        }

        public String toString() {
            Font font = this.l;
            Font font2 = this.r;
            float f = this.progress;
            return "InterpKey(l=" + font + ", r=" + font2 + ", progress=" + f + ")";
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/animation/FontInterpolator$VarFontKey.class */
    public static final class VarFontKey {
        public int index;
        public final List<FontVariationAxis> sortedAxes;
        public int sourceId;

        public VarFontKey(int i, int i2, List<FontVariationAxis> list) {
            this.sourceId = i;
            this.index = i2;
            this.sortedAxes = list;
        }

        /* JADX WARN: Illegal instructions before constructor call */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public VarFontKey(Font font, List<FontVariationAxis> list) {
            this(r0, r0, r0);
            int sourceIdentifier = font.getSourceIdentifier();
            int ttcIndex = font.getTtcIndex();
            List mutableList = CollectionsKt___CollectionsKt.toMutableList(list);
            if (mutableList.size() > 1) {
                CollectionsKt__MutableCollectionsJVMKt.sortWith(mutableList, new Comparator() { // from class: com.android.systemui.animation.FontInterpolator$VarFontKey$_init_$lambda$1$$inlined$sortBy$1
                    @Override // java.util.Comparator
                    public final int compare(T t, T t2) {
                        return ComparisonsKt__ComparisonsKt.compareValues(((FontVariationAxis) t).getTag(), ((FontVariationAxis) t2).getTag());
                    }
                });
            }
            Unit unit = Unit.INSTANCE;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj instanceof VarFontKey) {
                VarFontKey varFontKey = (VarFontKey) obj;
                return this.sourceId == varFontKey.sourceId && this.index == varFontKey.index && Intrinsics.areEqual(this.sortedAxes, varFontKey.sortedAxes);
            }
            return false;
        }

        public int hashCode() {
            return (((Integer.hashCode(this.sourceId) * 31) + Integer.hashCode(this.index)) * 31) + this.sortedAxes.hashCode();
        }

        public final void set(Font font, List<FontVariationAxis> list) {
            this.sourceId = font.getSourceIdentifier();
            this.index = font.getTtcIndex();
            this.sortedAxes.clear();
            this.sortedAxes.addAll(list);
            List<FontVariationAxis> list2 = this.sortedAxes;
            if (list2.size() > 1) {
                CollectionsKt__MutableCollectionsJVMKt.sortWith(list2, new Comparator() { // from class: com.android.systemui.animation.FontInterpolator$VarFontKey$set$$inlined$sortBy$1
                    @Override // java.util.Comparator
                    public final int compare(T t, T t2) {
                        return ComparisonsKt__ComparisonsKt.compareValues(((FontVariationAxis) t).getTag(), ((FontVariationAxis) t2).getTag());
                    }
                });
            }
        }

        public String toString() {
            int i = this.sourceId;
            int i2 = this.index;
            List<FontVariationAxis> list = this.sortedAxes;
            return "VarFontKey(sourceId=" + i + ", index=" + i2 + ", sortedAxes=" + list + ")";
        }
    }

    public final float adjustItalic(float f) {
        return coerceInWithStep(f, ActionBarShadowController.ELEVATION_LOW, 1.0f, 0.1f);
    }

    public final float adjustWeight(float f) {
        return coerceInWithStep(f, ActionBarShadowController.ELEVATION_LOW, 1000.0f, 10.0f);
    }

    public final float coerceInWithStep(float f, float f2, float f3, float f4) {
        return ((int) (RangesKt___RangesKt.coerceIn(f, f2, f3) / f4)) * f4;
    }

    public final Font lerp(Font font, Font font2, final float f) {
        if (f == ActionBarShadowController.ELEVATION_LOW) {
            return font;
        }
        if (f == 1.0f) {
            return font2;
        }
        FontVariationAxis[] axes = font.getAxes();
        FontVariationAxis[] fontVariationAxisArr = axes;
        if (axes == null) {
            fontVariationAxisArr = EMPTY_AXES;
        }
        FontVariationAxis[] axes2 = font2.getAxes();
        FontVariationAxis[] fontVariationAxisArr2 = axes2;
        if (axes2 == null) {
            fontVariationAxisArr2 = EMPTY_AXES;
        }
        if (fontVariationAxisArr.length == 0) {
            if (fontVariationAxisArr2.length == 0) {
                return font;
            }
        }
        this.tmpInterpKey.set(font, font2, f);
        Font font3 = this.interpCache.get(this.tmpInterpKey);
        if (font3 != null) {
            return font3;
        }
        List<FontVariationAxis> lerp = lerp(fontVariationAxisArr, fontVariationAxisArr2, new Function3<String, Float, Float, Float>() { // from class: com.android.systemui.animation.FontInterpolator$lerp$newAxes$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(3);
            }

            /* JADX DEBUG: Method merged with bridge method */
            public final Float invoke(String str, Float f2, Float f3) {
                float lerp2;
                float adjustItalic;
                float adjustWeight;
                if (Intrinsics.areEqual(str, "wght")) {
                    FontInterpolator fontInterpolator = FontInterpolator.this;
                    float f4 = 400.0f;
                    float floatValue = f2 != null ? f2.floatValue() : 400.0f;
                    if (f3 != null) {
                        f4 = f3.floatValue();
                    }
                    adjustWeight = fontInterpolator.adjustWeight(MathUtils.lerp(floatValue, f4, f));
                    lerp2 = adjustWeight;
                } else if (Intrinsics.areEqual(str, "ital")) {
                    FontInterpolator fontInterpolator2 = FontInterpolator.this;
                    float f5 = 0.0f;
                    float floatValue2 = f2 != null ? f2.floatValue() : 0.0f;
                    if (f3 != null) {
                        f5 = f3.floatValue();
                    }
                    adjustItalic = fontInterpolator2.adjustItalic(MathUtils.lerp(floatValue2, f5, f));
                    lerp2 = adjustItalic;
                } else {
                    if (!((f2 == null || f3 == null) ? false : true)) {
                        throw new IllegalArgumentException(("Unable to interpolate due to unknown default axes value : " + str).toString());
                    }
                    lerp2 = MathUtils.lerp(f2.floatValue(), f3.floatValue(), f);
                }
                return Float.valueOf(lerp2);
            }
        });
        this.tmpVarFontKey.set(font, lerp);
        Font font4 = this.verFontCache.get(this.tmpVarFontKey);
        if (font4 != null) {
            this.interpCache.put(new InterpKey(font, font2, f), font4);
            return font4;
        }
        Font build = new Font.Builder(font).setFontVariationSettings((FontVariationAxis[]) lerp.toArray(new FontVariationAxis[0])).build();
        this.interpCache.put(new InterpKey(font, font2, f), build);
        this.verFontCache.put(new VarFontKey(font, lerp), build);
        return build;
    }

    public final List<FontVariationAxis> lerp(FontVariationAxis[] fontVariationAxisArr, FontVariationAxis[] fontVariationAxisArr2, Function3<? super String, ? super Float, ? super Float, Float> function3) {
        FontVariationAxis fontVariationAxis;
        int i;
        if (fontVariationAxisArr.length > 1) {
            ArraysKt___ArraysJvmKt.sortWith(fontVariationAxisArr, new Comparator() { // from class: com.android.systemui.animation.FontInterpolator$lerp$$inlined$sortBy$1
                @Override // java.util.Comparator
                public final int compare(T t, T t2) {
                    return ComparisonsKt__ComparisonsKt.compareValues(((FontVariationAxis) t).getTag(), ((FontVariationAxis) t2).getTag());
                }
            });
        }
        if (fontVariationAxisArr2.length > 1) {
            ArraysKt___ArraysJvmKt.sortWith(fontVariationAxisArr2, new Comparator() { // from class: com.android.systemui.animation.FontInterpolator$lerp$$inlined$sortBy$2
                @Override // java.util.Comparator
                public final int compare(T t, T t2) {
                    return ComparisonsKt__ComparisonsKt.compareValues(((FontVariationAxis) t).getTag(), ((FontVariationAxis) t2).getTag());
                }
            });
        }
        ArrayList arrayList = new ArrayList();
        int i2 = 0;
        int i3 = 0;
        while (true) {
            if (i2 >= fontVariationAxisArr.length && i3 >= fontVariationAxisArr2.length) {
                return arrayList;
            }
            String tag = i2 < fontVariationAxisArr.length ? fontVariationAxisArr[i2].getTag() : null;
            String tag2 = i3 < fontVariationAxisArr2.length ? fontVariationAxisArr2[i3].getTag() : null;
            int compareTo = tag == null ? 1 : tag2 == null ? -1 : tag.compareTo(tag2);
            if (compareTo == 0) {
                Intrinsics.checkNotNull(tag);
                fontVariationAxis = new FontVariationAxis(tag, ((Number) function3.invoke(tag, Float.valueOf(fontVariationAxisArr[i2].getStyleValue()), Float.valueOf(fontVariationAxisArr2[i3].getStyleValue()))).floatValue());
                i = i2 + 1;
                i3++;
            } else if (compareTo < 0) {
                Intrinsics.checkNotNull(tag);
                fontVariationAxis = new FontVariationAxis(tag, ((Number) function3.invoke(tag, Float.valueOf(fontVariationAxisArr[i2].getStyleValue()), (Object) null)).floatValue());
                i = i2 + 1;
            } else {
                Intrinsics.checkNotNull(tag2);
                fontVariationAxis = new FontVariationAxis(tag2, ((Number) function3.invoke(tag2, (Object) null, Float.valueOf(fontVariationAxisArr2[i3].getStyleValue()))).floatValue());
                i3++;
                i = i2;
            }
            arrayList.add(fontVariationAxis);
            i2 = i;
        }
    }
}