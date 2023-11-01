package com.airbnb.lottie.parser;

import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.model.content.ContentModel;
import com.airbnb.lottie.parser.moshi.JsonReader;
import com.airbnb.lottie.utils.Logger;
import java.io.IOException;

/* loaded from: mainsysui33.jar:com/airbnb/lottie/parser/ContentModelParser.class */
public class ContentModelParser {
    public static JsonReader.Options NAMES = JsonReader.Options.of("ty", "d");

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:61:0x018f, code lost:
        if (r11.equals("gf") == false) goto L23;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static ContentModel parse(JsonReader jsonReader, LottieComposition lottieComposition) throws IOException {
        String str;
        ContentModel parse;
        jsonReader.beginObject();
        boolean z = true;
        int i = 2;
        while (true) {
            if (!jsonReader.hasNext()) {
                str = null;
                break;
            }
            int selectName = jsonReader.selectName(NAMES);
            if (selectName == 0) {
                str = jsonReader.nextString();
                break;
            } else if (selectName != 1) {
                jsonReader.skipName();
                jsonReader.skipValue();
            } else {
                i = jsonReader.nextInt();
            }
        }
        if (str == null) {
            return null;
        }
        switch (str.hashCode()) {
            case 3239:
                if (str.equals("el")) {
                    z = false;
                    break;
                }
                z = true;
                break;
            case 3270:
                if (str.equals("fl")) {
                    z = true;
                    break;
                }
                z = true;
                break;
            case 3295:
                break;
            case 3307:
                if (str.equals("gr")) {
                    z = true;
                    break;
                }
                z = true;
                break;
            case 3308:
                if (str.equals("gs")) {
                    z = true;
                    break;
                }
                z = true;
                break;
            case 3488:
                if (str.equals("mm")) {
                    z = true;
                    break;
                }
                z = true;
                break;
            case 3633:
                if (str.equals("rc")) {
                    z = true;
                    break;
                }
                z = true;
                break;
            case 3646:
                if (str.equals("rp")) {
                    z = true;
                    break;
                }
                z = true;
                break;
            case 3669:
                if (str.equals("sh")) {
                    z = true;
                    break;
                }
                z = true;
                break;
            case 3679:
                if (str.equals("sr")) {
                    z = true;
                    break;
                }
                z = true;
                break;
            case 3681:
                if (str.equals("st")) {
                    z = true;
                    break;
                }
                z = true;
                break;
            case 3705:
                if (str.equals("tm")) {
                    z = true;
                    break;
                }
                z = true;
                break;
            case 3710:
                if (str.equals("tr")) {
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
                parse = CircleShapeParser.parse(jsonReader, lottieComposition, i);
                break;
            case true:
                parse = ShapeFillParser.parse(jsonReader, lottieComposition);
                break;
            case true:
                parse = GradientFillParser.parse(jsonReader, lottieComposition);
                break;
            case true:
                parse = ShapeGroupParser.parse(jsonReader, lottieComposition);
                break;
            case true:
                parse = GradientStrokeParser.parse(jsonReader, lottieComposition);
                break;
            case true:
                ContentModel parse2 = MergePathsParser.parse(jsonReader);
                lottieComposition.addWarning("Animation contains merge paths. Merge paths are only supported on KitKat+ and must be manually enabled by calling enableMergePathsForKitKatAndAbove().");
                parse = parse2;
                break;
            case true:
                parse = RectangleShapeParser.parse(jsonReader, lottieComposition);
                break;
            case true:
                parse = RepeaterParser.parse(jsonReader, lottieComposition);
                break;
            case true:
                parse = ShapePathParser.parse(jsonReader, lottieComposition);
                break;
            case true:
                parse = PolystarShapeParser.parse(jsonReader, lottieComposition);
                break;
            case true:
                parse = ShapeStrokeParser.parse(jsonReader, lottieComposition);
                break;
            case true:
                parse = ShapeTrimPathParser.parse(jsonReader, lottieComposition);
                break;
            case true:
                parse = AnimatableTransformParser.parse(jsonReader, lottieComposition);
                break;
            default:
                Logger.warning("Unknown shape type " + str);
                parse = null;
                break;
        }
        while (jsonReader.hasNext()) {
            jsonReader.skipValue();
        }
        jsonReader.endObject();
        return parse;
    }
}