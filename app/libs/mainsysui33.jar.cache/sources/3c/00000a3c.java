package com.airbnb.lottie;

import java.util.HashMap;
import java.util.Map;

/* loaded from: mainsysui33.jar:com/airbnb/lottie/TextDelegate.class */
public class TextDelegate {
    public final Map<String, String> stringMap = new HashMap();
    public boolean cacheText = true;
    public final LottieAnimationView animationView = null;
    public final LottieDrawable drawable = null;

    public final String getText(String str) {
        return str;
    }

    public final String getTextInternal(String str) {
        if (this.cacheText && this.stringMap.containsKey(str)) {
            return this.stringMap.get(str);
        }
        String text = getText(str);
        if (this.cacheText) {
            this.stringMap.put(str, text);
        }
        return text;
    }
}