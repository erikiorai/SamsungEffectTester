package com.android.keyguard.sec;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

public class KeyguardEffectViewUtil {
    private static final String TAG = "KeyguardEffectViewUtil";
    public static Bitmap getPreferredConfigBitmap(Bitmap srcBitmap, Bitmap.Config config) {
        if (srcBitmap == null) {
            return null;
        }
        if (srcBitmap.getConfig() != config) {
            Log.i(TAG, "start to convert album art");
            int width = srcBitmap.getWidth();
            int height = srcBitmap.getHeight();
            if (width <= 0 || height <= 0) {
                return null;
            }
            Bitmap destBitmap = Bitmap.createBitmap(srcBitmap.getWidth(), srcBitmap.getHeight(), config);
            Canvas destCanvas = new Canvas(destBitmap);
            destCanvas.drawBitmap(srcBitmap, 0.0f, 0.0f, (Paint) null);
            return destBitmap;
        }
        return srcBitmap;
    }

}
