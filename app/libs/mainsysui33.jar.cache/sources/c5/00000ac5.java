package com.airbnb.lottie.parser;

import androidx.constraintlayout.widget.R$styleable;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.model.animatable.AnimatableIntegerValue;
import com.airbnb.lottie.model.animatable.AnimatableShapeValue;
import com.airbnb.lottie.model.content.Mask;
import com.airbnb.lottie.parser.moshi.JsonReader;
import com.airbnb.lottie.utils.Logger;
import java.io.IOException;

/* loaded from: mainsysui33.jar:com/airbnb/lottie/parser/MaskParser.class */
public class MaskParser {
    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x0118, code lost:
        if (r0.equals("s") == false) goto L12;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static Mask parse(JsonReader jsonReader, LottieComposition lottieComposition) throws IOException {
        boolean z;
        boolean z2;
        jsonReader.beginObject();
        Mask.MaskMode maskMode = null;
        boolean z3 = false;
        AnimatableShapeValue animatableShapeValue = null;
        AnimatableIntegerValue animatableIntegerValue = null;
        while (jsonReader.hasNext()) {
            String nextName = jsonReader.nextName();
            nextName.hashCode();
            switch (nextName.hashCode()) {
                case 111:
                    if (nextName.equals("o")) {
                        z = false;
                        break;
                    }
                    z = true;
                    break;
                case 3588:
                    if (nextName.equals("pt")) {
                        z = true;
                        break;
                    }
                    z = true;
                    break;
                case 104433:
                    if (nextName.equals("inv")) {
                        z = true;
                        break;
                    }
                    z = true;
                    break;
                case 3357091:
                    if (nextName.equals("mode")) {
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
                    animatableIntegerValue = AnimatableValueParser.parseInteger(jsonReader, lottieComposition);
                    break;
                case true:
                    animatableShapeValue = AnimatableValueParser.parseShapeData(jsonReader, lottieComposition);
                    break;
                case true:
                    z3 = jsonReader.nextBoolean();
                    break;
                case true:
                    String nextString = jsonReader.nextString();
                    nextString.hashCode();
                    switch (nextString.hashCode()) {
                        case 97:
                            if (nextString.equals("a")) {
                                z2 = false;
                                break;
                            }
                            z2 = true;
                            break;
                        case 105:
                            if (nextString.equals("i")) {
                                z2 = true;
                                break;
                            }
                            z2 = true;
                            break;
                        case R$styleable.ConstraintLayout_Layout_layout_goneMarginStart /* 110 */:
                            if (nextString.equals("n")) {
                                z2 = true;
                                break;
                            }
                            z2 = true;
                            break;
                        case 115:
                            z2 = true;
                            break;
                        default:
                            z2 = true;
                            break;
                    }
                    switch (z2) {
                        case false:
                            maskMode = Mask.MaskMode.MASK_MODE_ADD;
                            continue;
                        case true:
                            lottieComposition.addWarning("Animation contains intersect masks. They are not supported but will be treated like add masks.");
                            maskMode = Mask.MaskMode.MASK_MODE_INTERSECT;
                            continue;
                        case true:
                            maskMode = Mask.MaskMode.MASK_MODE_NONE;
                            continue;
                        case true:
                            maskMode = Mask.MaskMode.MASK_MODE_SUBTRACT;
                            continue;
                        default:
                            Logger.warning("Unknown mask mode " + nextName + ". Defaulting to Add.");
                            maskMode = Mask.MaskMode.MASK_MODE_ADD;
                            continue;
                    }
                default:
                    jsonReader.skipValue();
                    break;
            }
        }
        jsonReader.endObject();
        return new Mask(maskMode, animatableShapeValue, animatableIntegerValue, z3);
    }
}