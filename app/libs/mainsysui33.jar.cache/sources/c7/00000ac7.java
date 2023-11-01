package com.airbnb.lottie.parser;

import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.animation.keyframe.PathKeyframe;
import com.airbnb.lottie.parser.moshi.JsonReader;
import com.airbnb.lottie.utils.Utils;
import java.io.IOException;

/* loaded from: mainsysui33.jar:com/airbnb/lottie/parser/PathKeyframeParser.class */
public class PathKeyframeParser {
    public static PathKeyframe parse(JsonReader jsonReader, LottieComposition lottieComposition) throws IOException {
        return new PathKeyframe(lottieComposition, KeyframeParser.parse(jsonReader, lottieComposition, Utils.dpScale(), PathParser.INSTANCE, jsonReader.peek() == JsonReader.Token.BEGIN_OBJECT));
    }
}