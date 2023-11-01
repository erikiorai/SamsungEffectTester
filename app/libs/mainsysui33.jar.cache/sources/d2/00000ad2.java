package com.airbnb.lottie.parser;

import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.model.animatable.AnimatableColorValue;
import com.airbnb.lottie.model.animatable.AnimatableFloatValue;
import com.airbnb.lottie.model.animatable.AnimatableIntegerValue;
import com.airbnb.lottie.model.content.ShapeStroke;
import com.airbnb.lottie.parser.moshi.JsonReader;
import java.io.IOException;
import java.util.ArrayList;

/* loaded from: mainsysui33.jar:com/airbnb/lottie/parser/ShapeStrokeParser.class */
public class ShapeStrokeParser {
    public static JsonReader.Options NAMES = JsonReader.Options.of("nm", "c", "w", "o", "lc", "lj", "ml", "hd", "d");
    public static final JsonReader.Options DASH_PATTERN_NAMES = JsonReader.Options.of("n", "v");

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static ShapeStroke parse(JsonReader jsonReader, LottieComposition lottieComposition) throws IOException {
        boolean z;
        ArrayList arrayList = new ArrayList();
        boolean z2 = false;
        float f = 0.0f;
        String str = null;
        AnimatableFloatValue animatableFloatValue = null;
        AnimatableColorValue animatableColorValue = null;
        AnimatableIntegerValue animatableIntegerValue = null;
        AnimatableFloatValue animatableFloatValue2 = null;
        ShapeStroke.LineCapType lineCapType = null;
        ShapeStroke.LineJoinType lineJoinType = null;
        while (jsonReader.hasNext()) {
            switch (jsonReader.selectName(NAMES)) {
                case 0:
                    str = jsonReader.nextString();
                    break;
                case 1:
                    animatableColorValue = AnimatableValueParser.parseColor(jsonReader, lottieComposition);
                    break;
                case 2:
                    animatableFloatValue2 = AnimatableValueParser.parseFloat(jsonReader, lottieComposition);
                    break;
                case 3:
                    animatableIntegerValue = AnimatableValueParser.parseInteger(jsonReader, lottieComposition);
                    break;
                case 4:
                    lineCapType = ShapeStroke.LineCapType.values()[jsonReader.nextInt() - 1];
                    break;
                case 5:
                    lineJoinType = ShapeStroke.LineJoinType.values()[jsonReader.nextInt() - 1];
                    break;
                case 6:
                    f = (float) jsonReader.nextDouble();
                    break;
                case 7:
                    z2 = jsonReader.nextBoolean();
                    break;
                case 8:
                    jsonReader.beginArray();
                    AnimatableFloatValue animatableFloatValue3 = animatableFloatValue;
                    while (jsonReader.hasNext()) {
                        jsonReader.beginObject();
                        String str2 = null;
                        AnimatableFloatValue animatableFloatValue4 = null;
                        while (jsonReader.hasNext()) {
                            int selectName = jsonReader.selectName(DASH_PATTERN_NAMES);
                            if (selectName == 0) {
                                str2 = jsonReader.nextString();
                            } else if (selectName != 1) {
                                jsonReader.skipName();
                                jsonReader.skipValue();
                            } else {
                                animatableFloatValue4 = AnimatableValueParser.parseFloat(jsonReader, lottieComposition);
                            }
                        }
                        jsonReader.endObject();
                        str2.hashCode();
                        switch (str2.hashCode()) {
                            case 100:
                                if (str2.equals("d")) {
                                    z = false;
                                    break;
                                }
                                z = true;
                                break;
                            case 103:
                                if (str2.equals("g")) {
                                    z = true;
                                    break;
                                }
                                z = true;
                                break;
                            case 111:
                                if (str2.equals("o")) {
                                    z = true;
                                    break;
                                }
                                z = true;
                                break;
                            default:
                                z = true;
                                break;
                        }
                        switch (z) {
                            case false:
                            case true:
                                lottieComposition.setHasDashPattern(true);
                                arrayList.add(animatableFloatValue4);
                                break;
                            case true:
                                animatableFloatValue3 = animatableFloatValue4;
                                break;
                        }
                    }
                    jsonReader.endArray();
                    animatableFloatValue = animatableFloatValue3;
                    if (arrayList.size() != 1) {
                        break;
                    } else {
                        arrayList.add(arrayList.get(0));
                        animatableFloatValue = animatableFloatValue3;
                        break;
                    }
                default:
                    jsonReader.skipValue();
                    break;
            }
        }
        return new ShapeStroke(str, animatableFloatValue, arrayList, animatableColorValue, animatableIntegerValue, animatableFloatValue2, lineCapType, lineJoinType, f, z2);
    }
}