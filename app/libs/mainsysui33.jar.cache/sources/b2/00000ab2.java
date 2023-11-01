package com.airbnb.lottie.parser;

import android.graphics.PointF;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.model.animatable.AnimatableFloatValue;
import com.airbnb.lottie.model.animatable.AnimatableIntegerValue;
import com.airbnb.lottie.model.animatable.AnimatablePathValue;
import com.airbnb.lottie.model.animatable.AnimatableScaleValue;
import com.airbnb.lottie.model.animatable.AnimatableSplitDimensionPathValue;
import com.airbnb.lottie.model.animatable.AnimatableTransform;
import com.airbnb.lottie.model.animatable.AnimatableValue;
import com.airbnb.lottie.parser.moshi.JsonReader;
import com.airbnb.lottie.value.Keyframe;
import com.android.settingslib.widget.ActionBarShadowController;
import java.io.IOException;

/* loaded from: mainsysui33.jar:com/airbnb/lottie/parser/AnimatableTransformParser.class */
public class AnimatableTransformParser {
    public static JsonReader.Options NAMES = JsonReader.Options.of("a", "p", "s", "rz", "r", "o", "so", "eo", "sk", "sa");
    public static JsonReader.Options ANIMATABLE_NAMES = JsonReader.Options.of("k");

    /* JADX WARN: Code restructure failed: missing block: B:7:0x0029, code lost:
        if (r4.getKeyframes().get(0).startValue.equals(com.android.settingslib.widget.ActionBarShadowController.ELEVATION_LOW, com.android.settingslib.widget.ActionBarShadowController.ELEVATION_LOW) != false) goto L10;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static boolean isAnchorPointIdentity(AnimatablePathValue animatablePathValue) {
        boolean z;
        if (animatablePathValue != null) {
            z = false;
            if (animatablePathValue.isStatic()) {
                z = false;
            }
            return z;
        }
        z = true;
        return z;
    }

    /* JADX WARN: Code restructure failed: missing block: B:9:0x0036, code lost:
        if (r4.getKeyframes().get(0).startValue.equals(com.android.settingslib.widget.ActionBarShadowController.ELEVATION_LOW, com.android.settingslib.widget.ActionBarShadowController.ELEVATION_LOW) != false) goto L12;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static boolean isPositionIdentity(AnimatableValue<PointF, PointF> animatableValue) {
        boolean z;
        if (animatableValue != null) {
            z = false;
            if (!(animatableValue instanceof AnimatableSplitDimensionPathValue)) {
                z = false;
                if (animatableValue.isStatic()) {
                    z = false;
                }
            }
            return z;
        }
        z = true;
        return z;
    }

    /* JADX WARN: Code restructure failed: missing block: B:7:0x0029, code lost:
        if (((java.lang.Float) ((com.airbnb.lottie.value.Keyframe) r3.getKeyframes().get(0)).startValue).floatValue() == com.android.settingslib.widget.ActionBarShadowController.ELEVATION_LOW) goto L10;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static boolean isRotationIdentity(AnimatableFloatValue animatableFloatValue) {
        boolean z;
        if (animatableFloatValue != null) {
            z = false;
            if (animatableFloatValue.isStatic()) {
                z = false;
            }
            return z;
        }
        z = true;
        return z;
    }

    /* JADX WARN: Code restructure failed: missing block: B:7:0x0029, code lost:
        if (((com.airbnb.lottie.value.ScaleXY) ((com.airbnb.lottie.value.Keyframe) r4.getKeyframes().get(0)).startValue).equals(1.0f, 1.0f) != false) goto L10;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static boolean isScaleIdentity(AnimatableScaleValue animatableScaleValue) {
        boolean z;
        if (animatableScaleValue != null) {
            z = false;
            if (animatableScaleValue.isStatic()) {
                z = false;
            }
            return z;
        }
        z = true;
        return z;
    }

    /* JADX WARN: Code restructure failed: missing block: B:7:0x0029, code lost:
        if (((java.lang.Float) ((com.airbnb.lottie.value.Keyframe) r3.getKeyframes().get(0)).startValue).floatValue() == com.android.settingslib.widget.ActionBarShadowController.ELEVATION_LOW) goto L10;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static boolean isSkewAngleIdentity(AnimatableFloatValue animatableFloatValue) {
        boolean z;
        if (animatableFloatValue != null) {
            z = false;
            if (animatableFloatValue.isStatic()) {
                z = false;
            }
            return z;
        }
        z = true;
        return z;
    }

    /* JADX WARN: Code restructure failed: missing block: B:7:0x0029, code lost:
        if (((java.lang.Float) ((com.airbnb.lottie.value.Keyframe) r3.getKeyframes().get(0)).startValue).floatValue() == com.android.settingslib.widget.ActionBarShadowController.ELEVATION_LOW) goto L10;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static boolean isSkewIdentity(AnimatableFloatValue animatableFloatValue) {
        boolean z;
        if (animatableFloatValue != null) {
            z = false;
            if (animatableFloatValue.isStatic()) {
                z = false;
            }
            return z;
        }
        z = true;
        return z;
    }

    public static AnimatableTransform parse(JsonReader jsonReader, LottieComposition lottieComposition) throws IOException {
        boolean z = false;
        boolean z2 = jsonReader.peek() == JsonReader.Token.BEGIN_OBJECT;
        if (z2) {
            jsonReader.beginObject();
        }
        AnimatableFloatValue animatableFloatValue = null;
        AnimatablePathValue animatablePathValue = null;
        AnimatableValue<PointF, PointF> animatableValue = null;
        AnimatableScaleValue animatableScaleValue = null;
        AnimatableFloatValue animatableFloatValue2 = null;
        AnimatableFloatValue animatableFloatValue3 = null;
        AnimatableIntegerValue animatableIntegerValue = null;
        AnimatableFloatValue animatableFloatValue4 = null;
        AnimatableFloatValue animatableFloatValue5 = null;
        while (jsonReader.hasNext()) {
            switch (jsonReader.selectName(NAMES)) {
                case 0:
                    jsonReader.beginObject();
                    while (jsonReader.hasNext()) {
                        if (jsonReader.selectName(ANIMATABLE_NAMES) != 0) {
                            jsonReader.skipName();
                            jsonReader.skipValue();
                        } else {
                            animatablePathValue = AnimatablePathValueParser.parse(jsonReader, lottieComposition);
                        }
                    }
                    jsonReader.endObject();
                    continue;
                case 1:
                    animatableValue = AnimatablePathValueParser.parseSplitPath(jsonReader, lottieComposition);
                    continue;
                case 2:
                    animatableScaleValue = AnimatableValueParser.parseScale(jsonReader, lottieComposition);
                    continue;
                case 3:
                    lottieComposition.addWarning("Lottie doesn't support 3D layers.");
                    break;
                case 4:
                    break;
                case 5:
                    animatableIntegerValue = AnimatableValueParser.parseInteger(jsonReader, lottieComposition);
                    continue;
                case 6:
                    animatableFloatValue4 = AnimatableValueParser.parseFloat(jsonReader, lottieComposition, z);
                    continue;
                case 7:
                    animatableFloatValue5 = AnimatableValueParser.parseFloat(jsonReader, lottieComposition, z);
                    continue;
                case 8:
                    animatableFloatValue2 = AnimatableValueParser.parseFloat(jsonReader, lottieComposition, z);
                    continue;
                case 9:
                    animatableFloatValue3 = AnimatableValueParser.parseFloat(jsonReader, lottieComposition, z);
                    continue;
                default:
                    jsonReader.skipName();
                    jsonReader.skipValue();
                    continue;
            }
            animatableFloatValue = AnimatableValueParser.parseFloat(jsonReader, lottieComposition, z);
            if (animatableFloatValue.getKeyframes().isEmpty()) {
                animatableFloatValue.getKeyframes().add(new Keyframe(lottieComposition, Float.valueOf((float) ActionBarShadowController.ELEVATION_LOW), Float.valueOf((float) ActionBarShadowController.ELEVATION_LOW), null, ActionBarShadowController.ELEVATION_LOW, Float.valueOf(lottieComposition.getEndFrame())));
            } else if (((Keyframe) animatableFloatValue.getKeyframes().get(0)).startValue == 0) {
                animatableFloatValue.getKeyframes().set(0, new Keyframe(lottieComposition, Float.valueOf((float) ActionBarShadowController.ELEVATION_LOW), Float.valueOf((float) ActionBarShadowController.ELEVATION_LOW), null, ActionBarShadowController.ELEVATION_LOW, Float.valueOf(lottieComposition.getEndFrame())));
            }
            z = false;
        }
        if (z2) {
            jsonReader.endObject();
        }
        AnimatablePathValue animatablePathValue2 = isAnchorPointIdentity(animatablePathValue) ? null : animatablePathValue;
        AnimatableValue<PointF, PointF> animatableValue2 = isPositionIdentity(animatableValue) ? null : animatableValue;
        AnimatableFloatValue animatableFloatValue6 = isRotationIdentity(animatableFloatValue) ? null : animatableFloatValue;
        AnimatableScaleValue animatableScaleValue2 = animatableScaleValue;
        if (isScaleIdentity(animatableScaleValue)) {
            animatableScaleValue2 = null;
        }
        if (isSkewIdentity(animatableFloatValue2)) {
            animatableFloatValue2 = null;
        }
        if (isSkewAngleIdentity(animatableFloatValue3)) {
            animatableFloatValue3 = null;
        }
        return new AnimatableTransform(animatablePathValue2, animatableValue2, animatableScaleValue2, animatableFloatValue6, animatableIntegerValue, animatableFloatValue4, animatableFloatValue5, animatableFloatValue2, animatableFloatValue3);
    }
}