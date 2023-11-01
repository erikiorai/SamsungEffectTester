package com.airbnb.lottie.parser;

import android.graphics.Color;
import com.airbnb.lottie.parser.moshi.JsonReader;
import java.io.IOException;

/* loaded from: mainsysui33.jar:com/airbnb/lottie/parser/ColorParser.class */
public class ColorParser implements ValueParser<Integer> {
    public static final ColorParser INSTANCE = new ColorParser();

    /* JADX DEBUG: Method merged with bridge method */
    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.airbnb.lottie.parser.ValueParser
    public Integer parse(JsonReader jsonReader, float f) throws IOException {
        boolean z = jsonReader.peek() == JsonReader.Token.BEGIN_ARRAY;
        if (z) {
            jsonReader.beginArray();
        }
        double nextDouble = jsonReader.nextDouble();
        double nextDouble2 = jsonReader.nextDouble();
        double nextDouble3 = jsonReader.nextDouble();
        double nextDouble4 = jsonReader.nextDouble();
        if (z) {
            jsonReader.endArray();
        }
        double d = nextDouble;
        double d2 = nextDouble2;
        double d3 = nextDouble3;
        double d4 = nextDouble4;
        if (nextDouble <= 1.0d) {
            d = nextDouble;
            d2 = nextDouble2;
            d3 = nextDouble3;
            d4 = nextDouble4;
            if (nextDouble2 <= 1.0d) {
                d = nextDouble;
                d2 = nextDouble2;
                d3 = nextDouble3;
                d4 = nextDouble4;
                if (nextDouble3 <= 1.0d) {
                    d = nextDouble;
                    d2 = nextDouble2;
                    d3 = nextDouble3;
                    d4 = nextDouble4;
                    if (nextDouble4 <= 1.0d) {
                        d = nextDouble * 255.0d;
                        d2 = nextDouble2 * 255.0d;
                        d3 = nextDouble3 * 255.0d;
                        d4 = nextDouble4 * 255.0d;
                    }
                }
            }
        }
        return Integer.valueOf(Color.argb((int) d4, (int) d, (int) d2, (int) d3));
    }
}