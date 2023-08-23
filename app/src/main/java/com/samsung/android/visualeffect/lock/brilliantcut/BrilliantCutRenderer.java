package com.samsung.android.visualeffect.lock.brilliantcut;

import android.content.Context;
import android.util.Log;
import com.samsung.android.visualeffect.common.GLTextureView;
import com.samsung.android.visualeffect.lock.common.GLTextureViewRenderer;

/* loaded from: classes.dex */
public class BrilliantCutRenderer extends GLTextureViewRenderer {
    private final int IMAGE_TYPE_NORAL = 0;
    private final int IMAGE_TYPE_SPECIAL = 1;
    private int imageType = -1;
    private float radiusForSpecial = 0.34f;
    private float radiusForNormal = 0.23f;
    private float brightnessForAffordanceAndUnlock = 1.0f;
    private float brightnessForTouchNormal = 0.35f;
    private float brightnessForTouchSpecial = 0.45f;
    private float speedForTouch = 1.6f;
    private float repeatCount = 1.0f;
    private float nextTermSpeed = 0.075f;

    public BrilliantCutRenderer(Context context, GLTextureView view) {
        this.mContext = context;
        this.mGlView = view;
        this.mLibName = "libsecveBrilliantCut.so";
        this.TAG = "BrilliantCut_Renderer";
    }

    @Override // com.samsung.android.visualeffect.lock.common.GLTextureViewRenderer
    public void setParameters(int[] aNumsImageType, float[] aValuesImageType) {
        Log.d(this.TAG, "settingsForImageType() imageType = " + this.imageType + ", type = " + ((int) aValuesImageType[0]));
        if (this.imageType != ((int) aValuesImageType[0])) {
            this.imageType = (int) aValuesImageType[0];
            float radius = this.radiusForNormal;
            float brightnessForTouch = this.brightnessForTouchNormal;
            if (this.imageType == 1) {
                radius = this.radiusForSpecial;
                brightnessForTouch = this.brightnessForTouchSpecial;
            }
            int[] numsForSettingTouch = {2, 3, 4, 5, 6};
            float[] valuesForSettingTouch = {radius, brightnessForTouch, this.speedForTouch, this.repeatCount, this.nextTermSpeed};
            this.mNative.setParameters(aNumsImageType, aValuesImageType);
            this.mNative.setParameters(numsForSettingTouch, valuesForSettingTouch);
        }
    }
}