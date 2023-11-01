package com.airbnb.lottie.parser;

import android.graphics.Color;
import android.graphics.Rect;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.model.animatable.AnimatableFloatValue;
import com.airbnb.lottie.model.animatable.AnimatableTextFrame;
import com.airbnb.lottie.model.animatable.AnimatableTextProperties;
import com.airbnb.lottie.model.animatable.AnimatableTransform;
import com.airbnb.lottie.model.content.ContentModel;
import com.airbnb.lottie.model.layer.Layer;
import com.airbnb.lottie.parser.moshi.JsonReader;
import com.airbnb.lottie.utils.Utils;
import com.airbnb.lottie.value.Keyframe;
import com.android.settingslib.widget.ActionBarShadowController;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

/* loaded from: mainsysui33.jar:com/airbnb/lottie/parser/LayerParser.class */
public class LayerParser {
    public static final JsonReader.Options NAMES = JsonReader.Options.of("nm", "ind", "refId", "ty", "parent", "sw", "sh", "sc", "ks", "tt", "masksProperties", "shapes", "t", "ef", "sr", "st", "w", "h", "ip", "op", "tm", "cl", "hd");
    public static final JsonReader.Options TEXT_NAMES = JsonReader.Options.of("d", "a");
    public static final JsonReader.Options EFFECTS_NAMES = JsonReader.Options.of("nm");

    public static Layer parse(LottieComposition lottieComposition) {
        Rect bounds = lottieComposition.getBounds();
        return new Layer(Collections.emptyList(), lottieComposition, "__container", -1L, Layer.LayerType.PRE_COMP, -1L, null, Collections.emptyList(), new AnimatableTransform(), 0, 0, 0, ActionBarShadowController.ELEVATION_LOW, ActionBarShadowController.ELEVATION_LOW, bounds.width(), bounds.height(), null, null, Collections.emptyList(), Layer.MatteType.NONE, null, false);
    }

    public static Layer parse(JsonReader jsonReader, LottieComposition lottieComposition) throws IOException {
        Layer.MatteType matteType = Layer.MatteType.NONE;
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        jsonReader.beginObject();
        Float valueOf = Float.valueOf(1.0f);
        Float valueOf2 = Float.valueOf((float) ActionBarShadowController.ELEVATION_LOW);
        float f = 1.0f;
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        int i5 = 0;
        boolean z = false;
        AnimatableTransform animatableTransform = null;
        AnimatableTextProperties animatableTextProperties = null;
        AnimatableFloatValue animatableFloatValue = null;
        float f2 = 0.0f;
        float f3 = 0.0f;
        float f4 = 0.0f;
        long j = -1;
        long j2 = 0;
        String str = null;
        AnimatableTextFrame animatableTextFrame = null;
        String str2 = null;
        Layer.LayerType layerType = null;
        String str3 = "UNSET";
        while (jsonReader.hasNext()) {
            switch (jsonReader.selectName(NAMES)) {
                case 0:
                    str3 = jsonReader.nextString();
                    break;
                case 1:
                    j2 = jsonReader.nextInt();
                    break;
                case 2:
                    str2 = jsonReader.nextString();
                    break;
                case 3:
                    int nextInt = jsonReader.nextInt();
                    Layer.LayerType layerType2 = Layer.LayerType.UNKNOWN;
                    layerType = layerType2;
                    if (nextInt >= layerType2.ordinal()) {
                        break;
                    } else {
                        layerType = Layer.LayerType.values()[nextInt];
                        break;
                    }
                case 4:
                    j = jsonReader.nextInt();
                    break;
                case 5:
                    i = (int) (jsonReader.nextInt() * Utils.dpScale());
                    break;
                case 6:
                    i2 = (int) (jsonReader.nextInt() * Utils.dpScale());
                    break;
                case 7:
                    i3 = Color.parseColor(jsonReader.nextString());
                    break;
                case 8:
                    animatableTransform = AnimatableTransformParser.parse(jsonReader, lottieComposition);
                    break;
                case 9:
                    matteType = Layer.MatteType.values()[jsonReader.nextInt()];
                    lottieComposition.incrementMatteOrMaskCount(1);
                    break;
                case 10:
                    jsonReader.beginArray();
                    while (jsonReader.hasNext()) {
                        arrayList.add(MaskParser.parse(jsonReader, lottieComposition));
                    }
                    lottieComposition.incrementMatteOrMaskCount(arrayList.size());
                    jsonReader.endArray();
                    break;
                case 11:
                    jsonReader.beginArray();
                    while (jsonReader.hasNext()) {
                        ContentModel parse = ContentModelParser.parse(jsonReader, lottieComposition);
                        if (parse != null) {
                            arrayList2.add(parse);
                        }
                    }
                    jsonReader.endArray();
                    break;
                case 12:
                    jsonReader.beginObject();
                    while (jsonReader.hasNext()) {
                        int selectName = jsonReader.selectName(TEXT_NAMES);
                        if (selectName == 0) {
                            animatableTextFrame = AnimatableValueParser.parseDocumentData(jsonReader, lottieComposition);
                        } else if (selectName != 1) {
                            jsonReader.skipName();
                            jsonReader.skipValue();
                        } else {
                            jsonReader.beginArray();
                            if (jsonReader.hasNext()) {
                                animatableTextProperties = AnimatableTextPropertiesParser.parse(jsonReader, lottieComposition);
                            }
                            while (jsonReader.hasNext()) {
                                jsonReader.skipValue();
                            }
                            jsonReader.endArray();
                        }
                    }
                    jsonReader.endObject();
                    break;
                case 13:
                    jsonReader.beginArray();
                    ArrayList arrayList3 = new ArrayList();
                    while (jsonReader.hasNext()) {
                        jsonReader.beginObject();
                        while (jsonReader.hasNext()) {
                            if (jsonReader.selectName(EFFECTS_NAMES) != 0) {
                                jsonReader.skipName();
                                jsonReader.skipValue();
                            } else {
                                arrayList3.add(jsonReader.nextString());
                            }
                        }
                        jsonReader.endObject();
                    }
                    jsonReader.endArray();
                    lottieComposition.addWarning("Lottie doesn't support layer effects. If you are using them for  fills, strokes, trim paths etc. then try adding them directly as contents  in your shape. Found: " + arrayList3);
                    break;
                case 14:
                    f = (float) jsonReader.nextDouble();
                    break;
                case 15:
                    f4 = (float) jsonReader.nextDouble();
                    break;
                case 16:
                    i4 = (int) (jsonReader.nextInt() * Utils.dpScale());
                    break;
                case 17:
                    i5 = (int) (jsonReader.nextInt() * Utils.dpScale());
                    break;
                case 18:
                    f2 = (float) jsonReader.nextDouble();
                    break;
                case 19:
                    f3 = (float) jsonReader.nextDouble();
                    break;
                case 20:
                    animatableFloatValue = AnimatableValueParser.parseFloat(jsonReader, lottieComposition, false);
                    break;
                case 21:
                    str = jsonReader.nextString();
                    break;
                case 22:
                    z = jsonReader.nextBoolean();
                    break;
                default:
                    jsonReader.skipName();
                    jsonReader.skipValue();
                    break;
            }
        }
        jsonReader.endObject();
        float f5 = f2 / f;
        float f6 = f3 / f;
        ArrayList arrayList4 = new ArrayList();
        if (f5 > ActionBarShadowController.ELEVATION_LOW) {
            arrayList4.add(new Keyframe(lottieComposition, valueOf2, valueOf2, null, ActionBarShadowController.ELEVATION_LOW, Float.valueOf(f5)));
        }
        if (f6 <= ActionBarShadowController.ELEVATION_LOW) {
            f6 = lottieComposition.getEndFrame();
        }
        arrayList4.add(new Keyframe(lottieComposition, valueOf, valueOf, null, f5, Float.valueOf(f6)));
        arrayList4.add(new Keyframe(lottieComposition, valueOf2, valueOf2, null, f6, Float.valueOf(Float.MAX_VALUE)));
        if (str3.endsWith(".ai") || "ai".equals(str)) {
            lottieComposition.addWarning("Convert your Illustrator layers to shape layers.");
        }
        return new Layer(arrayList2, lottieComposition, str3, j2, layerType, j, str2, arrayList, animatableTransform, i, i2, i3, f, f4, i4, i5, animatableTextFrame, animatableTextProperties, arrayList4, matteType, animatableFloatValue, z);
    }
}