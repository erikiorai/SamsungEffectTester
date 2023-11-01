package com.android.systemui.navigationbar.gestural;

import android.content.res.AssetManager;
import java.util.HashMap;
import java.util.Map;

/* loaded from: mainsysui33.jar:com/android/systemui/navigationbar/gestural/BackGestureTfClassifierProvider.class */
public class BackGestureTfClassifierProvider {
    private static final String TAG = "BackGestureTfClassifierProvider";

    public boolean isActive() {
        return false;
    }

    public Map<String, Integer> loadVocab(AssetManager assetManager) {
        return new HashMap();
    }

    public float predict(Object[] objArr) {
        return -1.0f;
    }

    public void release() {
    }
}