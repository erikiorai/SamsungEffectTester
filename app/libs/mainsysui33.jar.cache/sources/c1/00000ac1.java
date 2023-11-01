package com.airbnb.lottie.parser;

import android.graphics.PointF;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import androidx.collection.SparseArrayCompat;
import androidx.core.view.animation.PathInterpolatorCompat;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.parser.moshi.JsonReader;
import com.airbnb.lottie.utils.MiscUtils;
import com.airbnb.lottie.utils.Utils;
import com.airbnb.lottie.value.Keyframe;
import com.android.settingslib.widget.ActionBarShadowController;
import java.io.IOException;
import java.lang.ref.WeakReference;

/* loaded from: mainsysui33.jar:com/airbnb/lottie/parser/KeyframeParser.class */
public class KeyframeParser {
    public static final Interpolator LINEAR_INTERPOLATOR = new LinearInterpolator();
    public static JsonReader.Options NAMES = JsonReader.Options.of("t", "s", "e", "o", "i", "h", "to", "ti");
    public static SparseArrayCompat<WeakReference<Interpolator>> pathInterpolatorCache;

    public static WeakReference<Interpolator> getInterpolator(int i) {
        WeakReference<Interpolator> weakReference;
        synchronized (KeyframeParser.class) {
            try {
                weakReference = pathInterpolatorCache().get(i);
            } catch (Throwable th) {
                throw th;
            }
        }
        return weakReference;
    }

    public static <T> Keyframe<T> parse(JsonReader jsonReader, LottieComposition lottieComposition, float f, ValueParser<T> valueParser, boolean z) throws IOException {
        return z ? parseKeyframe(lottieComposition, jsonReader, f, valueParser) : parseStaticValue(jsonReader, f, valueParser);
    }

    /* JADX WARN: Code restructure failed: missing block: B:32:0x0165, code lost:
        if (r10 == null) goto L59;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static <T> Keyframe<T> parseKeyframe(LottieComposition lottieComposition, JsonReader jsonReader, float f, ValueParser<T> valueParser) throws IOException {
        Interpolator interpolator;
        LinearInterpolator create;
        Interpolator interpolator2;
        jsonReader.beginObject();
        boolean z = false;
        float f2 = 0.0f;
        PointF pointF = null;
        PointF pointF2 = null;
        T t = null;
        T t2 = null;
        PointF pointF3 = null;
        PointF pointF4 = null;
        while (jsonReader.hasNext()) {
            switch (jsonReader.selectName(NAMES)) {
                case 0:
                    f2 = (float) jsonReader.nextDouble();
                    break;
                case 1:
                    t2 = valueParser.parse(jsonReader, f);
                    break;
                case 2:
                    t = valueParser.parse(jsonReader, f);
                    break;
                case 3:
                    pointF = JsonUtils.jsonToPoint(jsonReader, f);
                    break;
                case 4:
                    pointF2 = JsonUtils.jsonToPoint(jsonReader, f);
                    break;
                case 5:
                    if (jsonReader.nextInt() != 1) {
                        z = false;
                        break;
                    } else {
                        z = true;
                        break;
                    }
                case 6:
                    pointF4 = JsonUtils.jsonToPoint(jsonReader, f);
                    break;
                case 7:
                    pointF3 = JsonUtils.jsonToPoint(jsonReader, f);
                    break;
                default:
                    jsonReader.skipValue();
                    break;
            }
        }
        jsonReader.endObject();
        if (z) {
            interpolator = LINEAR_INTERPOLATOR;
            t = t2;
        } else if (pointF == null || pointF2 == null) {
            interpolator = LINEAR_INTERPOLATOR;
        } else {
            float f3 = -f;
            pointF.x = MiscUtils.clamp(pointF.x, f3, f);
            pointF.y = MiscUtils.clamp(pointF.y, -100.0f, 100.0f);
            pointF2.x = MiscUtils.clamp(pointF2.x, f3, f);
            float clamp = MiscUtils.clamp(pointF2.y, -100.0f, 100.0f);
            pointF2.y = clamp;
            int hashFor = Utils.hashFor(pointF.x, pointF.y, pointF2.x, clamp);
            WeakReference<Interpolator> interpolator3 = getInterpolator(hashFor);
            Interpolator interpolator4 = null;
            if (interpolator3 != null) {
                interpolator4 = interpolator3.get();
            }
            if (interpolator3 != null) {
                interpolator2 = interpolator4;
            }
            pointF.x /= f;
            pointF.y /= f;
            float f4 = pointF2.x / f;
            pointF2.x = f4;
            float f5 = pointF2.y / f;
            pointF2.y = f5;
            try {
                create = PathInterpolatorCompat.create(pointF.x, pointF.y, f4, f5);
            } catch (IllegalArgumentException e) {
                create = e.getMessage().equals("The Path cannot loop back on itself.") ? PathInterpolatorCompat.create(Math.min(pointF.x, 1.0f), pointF.y, Math.max(pointF2.x, (float) ActionBarShadowController.ELEVATION_LOW), pointF2.y) : new LinearInterpolator();
            }
            try {
                putInterpolator(hashFor, new WeakReference(create));
                interpolator2 = create;
            } catch (ArrayIndexOutOfBoundsException e2) {
                interpolator2 = create;
            }
            interpolator = interpolator2;
        }
        Keyframe<T> keyframe = new Keyframe<>(lottieComposition, t2, t, interpolator, f2, null);
        keyframe.pathCp1 = pointF4;
        keyframe.pathCp2 = pointF3;
        return keyframe;
    }

    public static <T> Keyframe<T> parseStaticValue(JsonReader jsonReader, float f, ValueParser<T> valueParser) throws IOException {
        return new Keyframe<>(valueParser.parse(jsonReader, f));
    }

    public static SparseArrayCompat<WeakReference<Interpolator>> pathInterpolatorCache() {
        if (pathInterpolatorCache == null) {
            pathInterpolatorCache = new SparseArrayCompat<>();
        }
        return pathInterpolatorCache;
    }

    public static void putInterpolator(int i, WeakReference<Interpolator> weakReference) {
        synchronized (KeyframeParser.class) {
            try {
                pathInterpolatorCache.put(i, weakReference);
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}