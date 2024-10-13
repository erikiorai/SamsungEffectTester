package com.samsung.android.visualeffect.lock;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import com.aj.effect.Utils;
import com.samsung.android.visualeffect.EffectDataObj;
import com.samsung.android.visualeffect.IEffectListener;
import com.samsung.android.visualeffect.IEffectView;
import com.samsung.android.visualeffect.circlemorphing.CircleMorphingEffect;
import com.samsung.android.visualeffect.circlemorphing.morphing.Morphing;

import java.util.HashMap;

public class CircleMorphingWrappedEffect extends CircleMorphingEffect implements IEffectView {
    private static final boolean DBG = true;

    private Context mContext;
    private int stageWidth;
    private int stageHeight;
    private float centerX;
    private float centerY;
    private int stageRatio;
    private Bitmap mBgBitmap;

    public CircleMorphingWrappedEffect(Context context) {
        super(context);
        mContext = context;
        resetOrientation();
    }

    private void resetOrientation() {
        if (DBG) {
            Log.d(this.TAG, "resetOrientation");
        }
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager mWindowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        Rect rect = Utils.getViewRect(dm, mWindowManager);
        stageWidth = rect.width();
        stageHeight = rect.height();
        if (DBG) {
            Log.d(this.TAG, "stage : " + this.stageWidth + " x " + this.stageHeight);
        }
        this.centerX = this.stageWidth / 2.0f;
        this.centerY = this.stageHeight / 2.0f;
        this.stageRatio = this.stageWidth / this.stageHeight;
        removeAllMorph();
    }

    @Override // android.view.View
    protected void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (DBG) {
            Log.d(this.TAG, "onConfigurationChanged");
        }
        resetOrientation();
    }

    @Override
    public void clearScreen() {
        removeAllMorph();
    }

    @Override
    public void handleCustomEvent(int cmd, HashMap<?, ?> params) {
        if (cmd == 0) {
            setBGBitmap((Bitmap) params.get("BGBitmap"));
        } else if (cmd == 1) {
            showAffordanceEffect((Long) params.get("StartDelay"), (Rect) params.get("Rect"));
        } else if (cmd == 2) {
            unlock();
        }
    }

    private void unlock() {
        // todo add unlock
    }

    private void showAffordanceEffect(long startDelay, Rect rect) {
        if (DBG) {
            Log.d(this.TAG, "showUnlockAffordance : " + rect.left + ", " + rect.right + ", " + rect.top + ", " + rect.bottom + ", startDelay : " + startDelay);
        }
        float x = rect.left + ((rect.right - rect.left) / 2.0f);
        float y = rect.top + ((rect.bottom - rect.top) / 2.0f);
        int color = getColor(x, y);
        // from class: com.samsung.android.visualeffect.lock.particle.ParticleSpaceEffect.2
// java.lang.Runnable
        Runnable affordanceRunnable = () -> {
            // todo add affordance
        };
        postDelayed(affordanceRunnable, startDelay);
    }

    private void setBGBitmap(Bitmap bitmap) {
        if (DBG) {
            Log.d(this.TAG, "setBGBitmap : " + bitmap.toString());
        }
        if (DBG) {
            Log.d(this.TAG, "setBGBitmap : " + bitmap.getWidth() + " x " + bitmap.getHeight());
        }
        this.mBgBitmap = bitmap;
    }

    private int getColor(float x, float y) {
        float ratio;
        int color = 16777215;
        if (x <= 0.0f || x > this.stageWidth) {
            return 16777215;
        }
        if (y <= 0.0f || y > this.stageHeight) {
            return 16777215;
        }
        if (this.mBgBitmap == null) {
            if (DBG) {
                Log.d(this.TAG, "getColor : mBgBitmap = null");
            }
        } else {
            int bitmapWidth = this.mBgBitmap.getWidth();
            int bitmapHeight = this.mBgBitmap.getHeight();
            float bitmapRatio = (float) bitmapWidth / bitmapHeight;
            int offsetX = 0;
            int offsetY = 0;
            if (bitmapRatio > this.stageRatio) {
                ratio = (float) bitmapHeight / this.stageHeight;
                float resizedStageWidth = this.stageWidth * ratio;
                offsetX = (int) ((bitmapWidth - resizedStageWidth) / 2.0f);
            } else {
                ratio = (float) bitmapWidth / this.stageWidth;
                float resizedStageHeight = this.stageHeight * ratio;
                offsetY = (int) ((bitmapHeight - resizedStageHeight) / 2.0f);
            }
            int finalX = offsetX + ((int) (x * ratio));
            int finalY = offsetY + ((int) (y * ratio));
            if (finalX < 0) {
                finalX = 0;
            }
            if (finalX >= bitmapWidth) {
                finalX = bitmapWidth - 1;
            }
            if (finalY < 0) {
                finalY = 0;
            }
            if (finalY >= bitmapHeight) {
                finalY = bitmapHeight - 1;
            }
            try {
                color = this.mBgBitmap.getPixel(finalX, finalY);
            } catch (IllegalArgumentException e) {
                if (DBG) {
                    Log.d(this.TAG, "getColor : IllegalArgumentException = " + e.toString());
                }
                if (DBG) {
                    Log.d(this.TAG, "getColor : bitmap = " + this.mBgBitmap.getWidth() + " x " + this.mBgBitmap.getHeight());
                }
                if (DBG) {
                    Log.d(this.TAG, "getColor : stageWidth = " + this.stageWidth + ", stageHeight =  " + this.stageHeight);
                }
                if (DBG) {
                    Log.d(this.TAG, "getColor : x = " + x + ", y =  " + y);
                }
            }
        }
        return color;
    }

    /*float max = 80.0f;
    float min = 50.0f;

    public float getRandom() {
        return (float) ((Math.random() * (max - min)) + min);
    }

    private float x1;
    private float y1;

    @Override
    public void handleTouchEvent(MotionEvent motionEvent, View view) {
        switch (motionEvent.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                x1 = motionEvent.getX();
                y1 = motionEvent.getY();
                Paint paint = new Paint();
                paint.setColor(getColor(x1, y1));
                Morphing morphing = new Morphing(0, paint);
                morphing.setMorph(x1, y1, getRandom(), true, x1, y1, getRandom(),
                        true);
                addMorph(morphing);
                draw();
                break;
            case MotionEvent.ACTION_MOVE:
                float x2 = motionEvent.getX();
                float y2 = motionEvent.getY();
                Paint paint1 = new Paint();
                paint1.setColor(getColor(x2, y2));
                Morphing morphing1 = new Morphing(0, paint1);
                morphing1.setMorph(x1, y1, getRandom(), true, x2, y2, getRandom(),
                        true);
                addMorph(morphing1);
                draw();
                break;
            case MotionEvent.ACTION_UP:
                Handler handler = new Handler(Looper.getMainLooper());
                //handler.postDelayed(this::removeAllMorph, 500L);
                break;
        }
    }*/

    private Morphing mMorphingObj;
    private Paint mMorphingPaint;
    private int mMorphingRadius;
    private int mMorphingStartX;
    private int mMorphingStartY;
    private int mMorphingThreshold;


    @Override
    public void handleTouchEvent(MotionEvent event, View view) {
        /*if (this.mIsAnimating) {
            return;
        }
        int action = event.getActionMasked();
        float x = event.getX();
        float y = event.getY();
        if (this.mIsShortcutTouched && mShortcutUnlockEventHandler != null) {
            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    ColorSet colorSet = ColorManager.getInstance(this.mContext).getCurrentColorSet();
                    int morphingColor = ColorManager.getInstance(this.mContext).getTransparentColor(colorSet.mPrimaryColor, 0.5f);
                    addMorphing();
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    removeMorphing();
                    break;
            }
            updateMorphing(x, y);
        }
        this.mIsTouchHandled = isTouchInView(this.mAppTrayHandle, event, this.mHandleTouchAreaSize);
        if (!this.mIsTouchHandled && !this.mIsTouched) {
            if (this.mIsExpanded) {
                if (isTouchInView(this.mAppTrayView, event, 0)) {
                    return true;
                }
                collapseAppTray();
                return true;
            }
            return super.onTouchEvent(event);
        }
        switch (action) {
            case 0:
            case MotionEvent.ACTION_POINTER_DOWN:
                getParent().requestDisallowInterceptTouchEvent(true);
                this.mIsTouched = true;
                this.mStartY = y;
                if (this.mAppTrayContainer != null) {
                    this.mContainerPrevY = (int) this.mAppTrayContainer.getY();
                    return true;
                }
                return true;
            case 1:
            case 3:
                getParent().requestDisallowInterceptTouchEvent(false);
                if (this.mIsTouchHandled) {
                    if (this.mIsExpanded) {
                        collapseAppTray();
                    } else {
                        expandAppTray();
                    }
                } else {
                    relayoutAppTray();
                }
                this.mIsTouched = false;
                return true;
            case 2:
                float diffY = y - this.mStartY;
                float currentY = this.mContainerPrevY + diffY;
                this.mCurrentExpandRatio = 1.0f - ((currentY - this.mContainerExpandedY) / (this.mContainerCollapsedY - this.mContainerExpandedY));
                if (this.mCurrentExpandRatio > 1.0f) {
                    this.mCurrentExpandRatio = 1.0f;
                } else if (this.mCurrentExpandRatio < 0.0f) {
                    this.mCurrentExpandRatio = 0.0f;
                }
                setBgAlpha(this.mCurrentExpandRatio);
                if (this.mAppTrayContainer != null) {
                    this.mAppTrayContainer.setY(getProperYValue((int) currentY));
                    return true;
                }
                return true;
            case 4:
            default:
                return true;
        }*/
    }

   /* private void updateMorphing(float endX, float endY) {
        if (this.mAppShortcutView != null) {
            if (this.mMorphingObj != null) {
                this.mMorphingObj.setMorph(this.mMorphingStartX, this.mMorphingStartY, this.mMorphingRadius, true, endX, endY, this.mMorphingRadius, true);
            }
            this.mCircleMorphingEffect.draw();
        }
    }

    private void removeMorphing() {
        if (this.mMorphingObj != null) {
            this.mCircleMorphingEffect.removeMorph(this.mMorphingObj);
            this.mMorphingObj = null;
        }
    }

    private void addMorphing(int x, int y) {
            if (mMorphingObj != null) {
                removeMorph(this.mMorphingObj);
                mMorphingObj = null;
            }
            ColorSet colorSet = ColorManager.getInstance(this.mContext).getCurrentColorSet();
            int morphingColor = ColorManager.getInstance(this.mContext).getTransparentColor(colorSet.mPrimaryColor, 0.5f);
            this.mMorphingPaint.setColor(morphingColor);
            this.mMorphingObj = new Morphing(this.mMorphingThreshold, this.mMorphingPaint);
            this.mMorphingStartX = x;
            this.mMorphingStartY = y;
            this.mMorphingRadius = 200;
            addMorph(mMorphingObj);

    }*/
    // todo morphing


    @Override
    public void init(EffectDataObj effectDataObj) {

    }

    @Override
    public void reInit(EffectDataObj effectDataObj) {

    }

    @Override
    public void removeListener() {

    }

    @Override
    public void setListener(IEffectListener iEffectListener) {

    }

    @Override
    public boolean handleHoverEvent(MotionEvent event) {
        return false;
    }

    @Override
    public void drawPause() {

    }

    @Override
    public void drawResume() {

    }
}
