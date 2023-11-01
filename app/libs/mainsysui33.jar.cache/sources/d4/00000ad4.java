package com.airbnb.lottie.parser;

import com.airbnb.lottie.parser.moshi.JsonReader;
import java.io.IOException;

/* loaded from: mainsysui33.jar:com/airbnb/lottie/parser/ValueParser.class */
public interface ValueParser<V> {
    V parse(JsonReader jsonReader, float f) throws IOException;
}