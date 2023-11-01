package com.airbnb.lottie.parser;

import com.airbnb.lottie.model.Font;
import com.airbnb.lottie.parser.moshi.JsonReader;
import java.io.IOException;

/* loaded from: mainsysui33.jar:com/airbnb/lottie/parser/FontParser.class */
public class FontParser {
    public static final JsonReader.Options NAMES = JsonReader.Options.of("fFamily", "fName", "fStyle", "ascent");

    public static Font parse(JsonReader jsonReader) throws IOException {
        jsonReader.beginObject();
        String str = null;
        String str2 = null;
        float f = 0.0f;
        String str3 = null;
        while (jsonReader.hasNext()) {
            int selectName = jsonReader.selectName(NAMES);
            if (selectName == 0) {
                str = jsonReader.nextString();
            } else if (selectName == 1) {
                str3 = jsonReader.nextString();
            } else if (selectName == 2) {
                str2 = jsonReader.nextString();
            } else if (selectName != 3) {
                jsonReader.skipName();
                jsonReader.skipValue();
            } else {
                f = (float) jsonReader.nextDouble();
            }
        }
        jsonReader.endObject();
        return new Font(str, str3, str2, f);
    }
}