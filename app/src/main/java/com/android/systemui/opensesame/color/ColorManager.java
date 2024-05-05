package com.android.systemui.opensesame.color;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.lang.ref.WeakReference;
import java.lang.reflect.Array;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class ColorManager {
    public static int BLACK = 0;
    public static final int DEFAULT_COLOR = -8388353;
    private static final float DIFF_H = 9.0f;
    private static final float DIFF_SV = 0.03f;
    private static final int SET_TARGET_COLOR = -1;
    public static int WHITE;
    private static volatile ColorManager sInstance;
    private Context mContext;
    public static final String TAG = ColorManager.class.getSimpleName();
    private static ArrayList<Integer> mSampleColorList = new ArrayList<>();
    private ColorSet mCurrentColorSet = new ColorSet();
    private ColorSet mDefaultColorSet = new ColorSet();
    private int mDefaultRoutineId = 1;
    private int mCurrentRoutineId = 1;
    private int mHandlerSequence = 0;
    private boolean mScreenTurnedOn = false;
    private Handler mColorChangeHandler = new Handler(Looper.getMainLooper()) { // from class: com.android.systemui.opensesame.color.ColorManager.2
        private static final int MAX_CHECKSUM = 15;
        private int mCheckSum;
        private ColorSet mTargetColorSet = null;
        private float[][] mTargetColorHSV = (float[][]) Array.newInstance(Float.TYPE, 5, 3);
        private float[][] mCurrentColorHSV = (float[][]) Array.newInstance(Float.TYPE, 5, 3);

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            if (msg.what != -1) {
                if (msg.what == ColorManager.this.mHandlerSequence) {
                    this.mCheckSum = 0;
                    for (int iIdx = 0; iIdx < 5; iIdx++) {
                        if (this.mCurrentColorHSV[iIdx][0] < this.mTargetColorHSV[iIdx][0] - ColorManager.DIFF_H) {
                            float[] fArr = this.mCurrentColorHSV[iIdx];
                            fArr[0] = fArr[0] + ColorManager.DIFF_H;
                        } else if (this.mCurrentColorHSV[iIdx][0] > this.mTargetColorHSV[iIdx][0] + ColorManager.DIFF_H) {
                            float[] fArr2 = this.mCurrentColorHSV[iIdx];
                            fArr2[0] = fArr2[0] - ColorManager.DIFF_H;
                        } else {
                            this.mCurrentColorHSV[iIdx][0] = this.mTargetColorHSV[iIdx][0];
                            this.mCheckSum++;
                        }
                        if (this.mCurrentColorHSV[iIdx][1] < this.mTargetColorHSV[iIdx][1] - ColorManager.DIFF_SV) {
                            float[] fArr3 = this.mCurrentColorHSV[iIdx];
                            fArr3[1] = fArr3[1] + ColorManager.DIFF_SV;
                        } else if (this.mCurrentColorHSV[iIdx][1] > this.mTargetColorHSV[iIdx][1] + ColorManager.DIFF_SV) {
                            float[] fArr4 = this.mCurrentColorHSV[iIdx];
                            fArr4[1] = fArr4[1] - ColorManager.DIFF_SV;
                        } else {
                            this.mCurrentColorHSV[iIdx][1] = this.mTargetColorHSV[iIdx][1];
                            this.mCheckSum++;
                        }
                        if (this.mCurrentColorHSV[iIdx][2] < this.mTargetColorHSV[iIdx][2] - ColorManager.DIFF_SV) {
                            float[] fArr5 = this.mCurrentColorHSV[iIdx];
                            fArr5[2] = fArr5[2] + ColorManager.DIFF_SV;
                        } else if (this.mCurrentColorHSV[iIdx][2] > this.mTargetColorHSV[iIdx][2] + ColorManager.DIFF_SV) {
                            float[] fArr6 = this.mCurrentColorHSV[iIdx];
                            fArr6[2] = fArr6[2] - ColorManager.DIFF_SV;
                        } else {
                            this.mCurrentColorHSV[iIdx][2] = this.mTargetColorHSV[iIdx][2];
                            this.mCheckSum++;
                        }
                    }
                    if (ColorManager.this.mScreenTurnedOn && this.mCheckSum < 15) {
                        ColorManager.this.updateColorSetFromHSV(ColorManager.this.mCurrentColorSet, this.mCurrentColorHSV);
                        Message newMsg = Message.obtain();
                        newMsg.what = msg.what;
                        sendMessageDelayed(newMsg, 40L);
                    } else {
                        ColorManager.this.mCurrentColorSet = this.mTargetColorSet;
                    }
                    ColorManager.this.notifyColorChanged();
                    return;
                }
                return;
            }
            this.mTargetColorSet = (ColorSet) msg.obj;
            this.mTargetColorHSV = ColorManager.this.getColorToHSV(this.mTargetColorSet);
            this.mCurrentColorHSV = ColorManager.this.getColorToHSV(ColorManager.this.mCurrentColorSet);
        }
    };
    private ArrayList<WeakReference<OnColorChangeListener>> mCallbacks = new ArrayList<>();

    /* loaded from: classes.dex */
    public interface OnColorChangeListener {
        void onBaseColorChanged();

        void onColorChangeStarted(ColorSet colorSet);
    }

    /* loaded from: classes.dex */
    public static class OnColorChangeListenerImpl implements OnColorChangeListener {
        @Override // com.android.systemui.opensesame.color.ColorManager.OnColorChangeListener
        public void onBaseColorChanged() {
        }

        @Override // com.android.systemui.opensesame.color.ColorManager.OnColorChangeListener
        public void onColorChangeStarted(ColorSet targetColorSet) {
        }
    }

    private ColorManager(Context context) {
        this.mContext = context;
        BLACK = 0x99000000;
        WHITE = -1;
    }

    public void init() {
    }

    public static synchronized ColorManager getInstance(Context context) {
        ColorManager colorManager;
        synchronized (ColorManager.class) {
            if (sInstance == null) {
                synchronized (ColorManager.class) {
                    if (sInstance == null) {
                        sInstance = new ColorManager(context);
                    }
                }
            }
            colorManager = sInstance;
        }
        return colorManager;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startColorChange(ColorSet targetColorSet, boolean isSmooth) {
        notifyColorChangeStart(targetColorSet);
        if (isSmooth) {
            this.mColorChangeHandler.removeMessages(this.mHandlerSequence);
            this.mHandlerSequence++;
            Message msg = Message.obtain();
            msg.what = -1;
            msg.obj = targetColorSet;
            this.mColorChangeHandler.sendMessage(msg);
            this.mColorChangeHandler.sendEmptyMessage(this.mHandlerSequence);
            return;
        }
        this.mCurrentColorSet = targetColorSet;
        notifyColorChanged();
    }

    private int getColor(float alpha, float hue, float saturation, float brightness) {
        return getColor(this.mCurrentColorSet.mBaseColor, alpha, hue, saturation, brightness);
    }

    private int getColor(int baseColor, float alpha, float hue, float saturation, float brightness) {
        float[] hsvColor = new float[3];
        Color.colorToHSV(baseColor, hsvColor);
        if (-360.0f < hue && hue < 360.0f) {
            hsvColor[0] = hsvColor[0] + hue;
            if (hsvColor[0] < 0.0f) {
                hsvColor[0] = hsvColor[0] + 360.0f;
            } else if (360.0f <= hsvColor[0]) {
                hsvColor[0] = hsvColor[0] - 360.0f;
            }
        }
        int color = Color.HSVToColor(hsvColor);
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        if (0.0f < saturation && saturation <= 1.0f) {
            red = (int) (red + ((255 - red) * (1.0f - saturation)));
            green = (int) (green + ((255 - green) * (1.0f - saturation)));
            blue = (int) (blue + ((255 - blue) * (1.0f - saturation)));
        }
        if (0.0f < brightness && brightness <= 1.0f) {
            red = (int) (red - (red * (1.0f - brightness)));
            green = (int) (green - (green * (1.0f - brightness)));
            blue = (int) (blue - (blue * (1.0f - brightness)));
        }
        int a = 255;
        if (0.0f <= alpha && alpha <= 1.0f) {
            a = (int) (255 * alpha);
        }
        return Color.argb(a, red, green, blue);
    }

    public int getCombinationColor(int firstColor, int secondColor, float mixtureRate) {
        return getCombinationColor(firstColor, secondColor, mixtureRate, 1.0f);
    }

    public int getCombinationColor(int firstColor, int secondColor, float mixtureRate, float alpha) {
        int fred = Color.red(firstColor);
        int fgreen = Color.green(firstColor);
        int fblue = Color.blue(firstColor);
        int sred = Color.red(secondColor);
        int sgreen = Color.green(secondColor);
        int sblue = Color.blue(secondColor);
        int cred = (int) ((fred * (1.0f - mixtureRate)) + (sred * mixtureRate));
        int cgreen = (int) ((fgreen * (1.0f - mixtureRate)) + (sgreen * mixtureRate));
        int cblue = (int) ((fblue * (1.0f - mixtureRate)) + (sblue * mixtureRate));
        int a = 255;
        if (0.0f <= alpha && alpha <= 1.0f) {
            a = (int) (255 * alpha);
        }
        return Color.argb(a, cred, cgreen, cblue);
    }

    public int getTransparentColor(int color, float alpha) {
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        int a = 255;
        if (0.0f <= alpha && alpha <= 1.0f) {
            a = (int) (255 * alpha);
        }
        return Color.argb(a, red, green, blue);
    }

    public ColorSet getCurrentColorSet() {
        return this.mCurrentColorSet.getClone();
    }

    public ColorSet getDefaultColorSet() {
        return this.mDefaultColorSet.getClone();
    }

    public void setRecommendationColorSet(ColorSet targetSet, int baseColor) {
        targetSet.mBaseColor = baseColor;
        targetSet.mBackgroundColor = getColor(baseColor, 1.0f, -22.0f, 0.07f, 1.0f);
        targetSet.mForegroundColor = getColor(baseColor, 1.0f, -5.0f, 0.55f, 0.85f);
        targetSet.mPrimaryColor = getColor(baseColor, 1.0f, 0.0f, 0.66f, 0.74f);
        targetSet.mPointColor = getColor(baseColor, 1.0f, -68.0f, 0.93f, 0.87f);
    }

    public static ArrayList<Integer> getSampleColors() {
        ArrayList<Integer> arrayList;
        synchronized (mSampleColorList) {
            if (mSampleColorList.isEmpty()) {
                for (float brightness = 0.8f; brightness >= 0.0f; brightness -= 0.1f) {
                    float[] hsvColor = {0.0f, 0.0f, brightness};
                    mSampleColorList.add(Color.HSVToColor(hsvColor));
                }
                for (float brightness2 = 0.2f; brightness2 <= 1.0f; brightness2 += 0.4f) {
                    for (float colorTemperature = 0.0f; colorTemperature < 360.0f; colorTemperature += 10.0f) {
                        float[] hsvColor2 = {colorTemperature, 1.0f, brightness2};
                        mSampleColorList.add(Color.HSVToColor(hsvColor2));
                    }
                }
                for (float colorSaturation = 0.7f; colorSaturation > 0.0f; colorSaturation -= 0.3f) {
                    for (float colorTemperature2 = 0.0f; colorTemperature2 < 360.0f; colorTemperature2 += 10.0f) {
                        float[] hsvColor3 = {colorTemperature2, colorSaturation, 1.0f};
                        mSampleColorList.add(Color.HSVToColor(hsvColor3));
                    }
                }
            }
            arrayList = mSampleColorList;
        }
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyColorChanged() {
        for (int idx = 0; idx < this.mCallbacks.size(); idx++) {
            OnColorChangeListener callback = this.mCallbacks.get(idx).get();
            if (callback != null) {
                callback.onBaseColorChanged();
            }
        }
    }

    private void notifyColorChangeStart(ColorSet targetColorSet) {
        for (int idx = 0; idx < this.mCallbacks.size(); idx++) {
            OnColorChangeListener callback = this.mCallbacks.get(idx).get();
            if (callback != null) {
                callback.onColorChangeStarted(targetColorSet);
            }
        }
    }

    public void registerCallback(OnColorChangeListener callback) {
        for (int i = 0; i < this.mCallbacks.size(); i++) {
            if (this.mCallbacks.get(i).get() == callback) {
                return;
            }
        }
        this.mCallbacks.add(new WeakReference<>(callback));
        unregisterCallback(null);
    }

    public void unregisterCallback(OnColorChangeListener callback) {
        for (int idx = this.mCallbacks.size() - 1; idx >= 0; idx--) {
            if (this.mCallbacks.get(idx).get() == callback) {
                this.mCallbacks.remove(idx);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateColorSetFromHSV(ColorSet colorSet, float[][] colorHSV) {
        colorSet.mBaseColor = Color.HSVToColor(colorHSV[0]);
        colorSet.mBackgroundColor = Color.HSVToColor(colorHSV[1]);
        colorSet.mForegroundColor = Color.HSVToColor(colorHSV[2]);
        colorSet.mPrimaryColor = Color.HSVToColor(colorHSV[3]);
        colorSet.mPointColor = Color.HSVToColor(colorHSV[4]);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public float[][] getColorToHSV(ColorSet colorSet) {
        float[][] colorSetHSV = (float[][]) Array.newInstance(Float.TYPE, 5, 3);
        Color.colorToHSV(colorSet.mBaseColor, colorSetHSV[0]);
        Color.colorToHSV(colorSet.mBackgroundColor, colorSetHSV[1]);
        Color.colorToHSV(colorSet.mForegroundColor, colorSetHSV[2]);
        Color.colorToHSV(colorSet.mPrimaryColor, colorSetHSV[3]);
        Color.colorToHSV(colorSet.mPointColor, colorSetHSV[4]);
        return colorSetHSV;
    }
}