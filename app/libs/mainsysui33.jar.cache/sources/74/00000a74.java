package com.airbnb.lottie.model;

import androidx.collection.LruCache;
import com.airbnb.lottie.LottieComposition;

/* loaded from: mainsysui33.jar:com/airbnb/lottie/model/LottieCompositionCache.class */
public class LottieCompositionCache {
    public static final LottieCompositionCache INSTANCE = new LottieCompositionCache();
    public final LruCache<String, LottieComposition> cache = new LruCache<>(20);

    public static LottieCompositionCache getInstance() {
        return INSTANCE;
    }

    public LottieComposition get(String str) {
        if (str == null) {
            return null;
        }
        return this.cache.get(str);
    }

    public void put(String str, LottieComposition lottieComposition) {
        if (str == null) {
            return;
        }
        this.cache.put(str, lottieComposition);
    }
}