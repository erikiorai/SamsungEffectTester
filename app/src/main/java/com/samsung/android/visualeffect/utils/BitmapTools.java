package com.samsung.android.visualeffect.utils;

import android.graphics.Bitmap;
import android.util.Log;

public class BitmapTools {
    private static final String TAG = "BitmapTools";

    public static Bitmap getCenterCropBitmap(Bitmap bitmap, int width, int height) {
        if (bitmap == null) {
            Log.e(TAG, "getCenterCropBitmap() - bitmap is null!!");
            return null;
        }
        Log.d(TAG, "getCenterCropBitmap() - bitmap size.getWidth() : " + bitmap.getWidth());
        Log.d(TAG, "getCenterCropBitmap() - bitmap size.getHeight() : " + bitmap.getHeight());
        Log.d(TAG, "getCenterCropBitmap() - width : " + width + ", height : " + height);
        int bitmapWidth = bitmap.getWidth();
        int bitmapHeight = bitmap.getHeight();
        float ratio = (float) width / height;
        float bitmapRatio = (float) bitmapWidth / bitmapHeight;
        if (bitmapRatio > ratio) {
            Log.d(TAG, "getCenterCropBitmap() - bmp is horizontally");
            int targetWidth = (int) (bitmapHeight * ratio);
            if (targetWidth <= 0) {
                Log.d(TAG, "getCenterCropBitmap() - targetWidth <= 0");
                targetWidth = 1;
            }
            return Bitmap.createBitmap(bitmap, (bitmapWidth - targetWidth) / 2, 0, targetWidth, bitmapHeight);
        }
        Log.d(TAG, "getCenterCropBitmap() - bmp is vertically");
        int targetHeight = (int) (bitmapWidth / ratio);
        if (targetHeight <= 0) {
            Log.d(TAG, "getCenterCropBitmap() - targetHeight <= 0");
            targetHeight = 1;
        }
        return Bitmap.createBitmap(bitmap, 0, (bitmapHeight - targetHeight) / 2, bitmapWidth, targetHeight);
    }
}