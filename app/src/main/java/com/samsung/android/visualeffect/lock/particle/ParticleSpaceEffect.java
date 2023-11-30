package com.samsung.android.visualeffect.lock.particle;

import android.animation.Animator;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.aj.effect.Utils;
import com.samsung.android.visualeffect.EffectDataObj;
import com.samsung.android.visualeffect.IEffectListener;
import com.samsung.android.visualeffect.IEffectView;

import java.util.HashMap;

/* loaded from: classes.dex */
public class ParticleSpaceEffect extends FrameLayout implements IEffectView {
    private int CREATED_DOTS_AMOUNT_AFFORDANCE = 50;
    private int CREATED_DOTS_AMOUNT_DOWN = 15;
    private int CREATED_DOTS_AMOUNT_MOVE = 3;
    private final boolean DBG = true;
    private final String TAG = "VisualEffectParticleEffect";
    private int affordanceColor;
    private Runnable affordanceRunnable;
    private float affordanceX;
    private float affordanceY;
    private float centerX;
    private float centerY;
    private float currentX;
    private float currentY;
    private boolean isUnlocked = false;
    private Bitmap mBgBitmap;
    private Context mContext;
    private ImageView mLockscreenWallpaperImage;
    private ParticleEffect particleEffect;
    private float stageHeight;
    private float stageRatio;
    private float stageWidth;

    public ParticleSpaceEffect(Context context) {
        super(context);
        this.isUnlocked = false;
        if (this.DBG) {
            Log.d(this.TAG, "Constructor");
        }
        this.mContext = context;
        particleSpaceInit();
    }

    public ParticleSpaceEffect(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.isUnlocked = false;
        if (this.DBG) {
            Log.d(this.TAG, "Constructor");
        }
        this.mContext = context;
        particleSpaceInit();
    }

    public ParticleSpaceEffect(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.isUnlocked = false;
        if (this.DBG) {
            Log.d(this.TAG, "Constructor");
        }
        this.mContext = context;
        particleSpaceInit();
    }

    private void particleSpaceInit() {
        if (this.DBG) {
            Log.d(this.TAG, "particleSpaceInit");
        }
        resetOrientation();
        this.mLockscreenWallpaperImage = new ImageView(this.mContext);
        this.mLockscreenWallpaperImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
        this.mLockscreenWallpaperImage.setDrawingCacheEnabled(true);
        addView(this.mLockscreenWallpaperImage, -1, -1);
        this.particleEffect = new ParticleEffect(this.mContext);
        addView(this.particleEffect);
    }

    private void resetOrientation() {
        if (this.DBG) {
            Log.d(this.TAG, "resetOrientation");
        }
        //DisplayMetrics dm = getResources().getDisplayMetrics();
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager mWindowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        Rect rect = Utils.getViewRect(dm, mWindowManager);
        stageWidth = rect.width();
        stageHeight = rect.height();
        if (this.DBG) {
            Log.d(this.TAG, "stage : " + this.stageWidth + " x " + this.stageHeight);
        }
        this.centerX = this.stageWidth / 2.0f;
        this.centerY = this.stageHeight / 2.0f;
        this.stageRatio = this.stageWidth / this.stageHeight;
        if (this.particleEffect != null) {
            this.particleEffect.clearEffect();
        }
    }

    @Override // android.view.View
    protected void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (this.DBG) {
            Log.d(this.TAG, "onConfigurationChanged");
        }
        resetOrientation();
    }

    private void clearEffect() {
        if (this.DBG) {
            Log.d(this.TAG, "clearEffect");
        }
        this.currentX = this.centerX;
        this.currentY = this.centerY;
        this.particleEffect.clearEffect();
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
            if (this.DBG) {
                Log.d(this.TAG, "getColor : mBgBitmap = null");
            }
        } else {
            int bitmapWidth = this.mBgBitmap.getWidth();
            int bitmapHeight = this.mBgBitmap.getHeight();
            float bitmapRatio = (float) bitmapWidth / bitmapHeight;
            int offsetX = 0;
            int offsetY = 0;
            if (bitmapRatio > this.stageRatio) {
                ratio = bitmapHeight / this.stageHeight;
                float resizedStageWidth = this.stageWidth * ratio;
                offsetX = (int) ((bitmapWidth - resizedStageWidth) / 2.0f);
            } else {
                ratio = bitmapWidth / this.stageWidth;
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
                if (this.DBG) {
                    Log.d(this.TAG, "getColor : IllegalArgumentException = " + e.toString());
                }
                if (this.DBG) {
                    Log.d(this.TAG, "getColor : bitmap = " + this.mBgBitmap.getWidth() + " x " + this.mBgBitmap.getHeight());
                }
                if (this.DBG) {
                    Log.d(this.TAG, "getColor : stageWidth = " + this.stageWidth + ", stageHeight =  " + this.stageHeight);
                }
                if (this.DBG) {
                    Log.d(this.TAG, "getColor : x = " + x + ", y =  " + y);
                }
            }
        }
        return color;
    }

    private void unlock() {
        if (this.DBG) {
            Log.d(this.TAG, "unlock");
        }
        this.isUnlocked = true;
        this.particleEffect.unlockDots();
        animate().setDuration(350L).setListener(new Animator.AnimatorListener() { // from class: com.samsung.android.visualeffect.lock.particle.ParticleSpaceEffect.1
            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animation) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationRepeat(Animator animation) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animation) {
                ParticleSpaceEffect.this.unlockFinished();
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animation) {
                if (ParticleSpaceEffect.this.DBG) {
                    Log.d(ParticleSpaceEffect.this.TAG, "unlock : onAnimationCancel");
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void unlockFinished() {
        if (this.DBG) {
            Log.d(this.TAG, "unlockFinished");
        }
        clearEffect();
    }

    private void setBGBitmap(Bitmap bitmap) {
        if (this.DBG) {
            Log.d(this.TAG, "setBGBitmap : " + bitmap.toString());
        }
        if (this.DBG) {
            Log.d(this.TAG, "setBGBitmap : " + bitmap.getWidth() + " x " + bitmap.getHeight());
        }
        this.mBgBitmap = bitmap;
        if (this.mLockscreenWallpaperImage != null) {
            this.mLockscreenWallpaperImage.setImageBitmap(bitmap);
        }
    }

    private void showAffordanceEffect(long startDelay, Rect rect) {
        if (this.DBG) {
            Log.d(this.TAG, "showUnlockAffordance : " + rect.left + ", " + rect.right + ", " + rect.top + ", " + rect.bottom + ", startDelay : " + startDelay);
        }
        this.affordanceX = rect.left + ((rect.right - rect.left) / 2.0f);
        this.affordanceY = rect.top + ((rect.bottom - rect.top) / 2.0f);
        this.affordanceColor = getColor(this.affordanceX, this.affordanceY);
        this.affordanceRunnable = new Runnable() { // from class: com.samsung.android.visualeffect.lock.particle.ParticleSpaceEffect.2
            @Override // java.lang.Runnable
            public void run() {
                ParticleSpaceEffect.this.particleEffect.addDots(ParticleSpaceEffect.this.CREATED_DOTS_AMOUNT_AFFORDANCE, ParticleSpaceEffect.this.affordanceX, ParticleSpaceEffect.this.affordanceY, ParticleSpaceEffect.this.affordanceColor);
                ParticleSpaceEffect.this.affordanceRunnable = null;
            }
        };
        postDelayed(this.affordanceRunnable, startDelay);
    }

    @Override // com.samsung.android.visualeffect.IEffectView
    public void init(EffectDataObj data) {
        if (this.DBG) {
            Log.d(this.TAG, "init");
        }
        clearEffect();
    }

    @Override // com.samsung.android.visualeffect.IEffectView
    public void reInit(EffectDataObj data) {
    }

    @Override // com.samsung.android.visualeffect.IEffectView
    public void handleCustomEvent(int cmd, HashMap<?, ?> params) {
        if (cmd == 0) {
            setBGBitmap((Bitmap) params.get("BGBitmap"));
        } else if (cmd == 1) {
            showAffordanceEffect(((Long) params.get("StartDelay")).longValue(), (Rect) params.get("Rect"));
        } else if (cmd == 2) {
            unlock();
        }
    }

    @Override // com.samsung.android.visualeffect.IEffectView
    public void setListener(IEffectListener listener) {
    }

    @Override
    public boolean handleHoverEvent(MotionEvent event) {
        return false;
    }

    @Override // com.samsung.android.visualeffect.IEffectView
    public void removeListener() {
    }

    @Override // com.samsung.android.visualeffect.IEffectView
    public void handleTouchEvent(MotionEvent event, View view) {
        this.currentX = event.getX();
        this.currentY = event.getY();
        int color = getColor(this.currentX, this.currentY);
        if (event.getActionMasked() == 0) {
            if (this.DBG) {
                Log.d(this.TAG, "handleTouchEvent : ACTION_DOWN");
            }
            this.isUnlocked = false;
            this.particleEffect.addDots(this.CREATED_DOTS_AMOUNT_DOWN, this.currentX, this.currentY, color);
        } else if (event.getActionMasked() == MotionEvent.ACTION_MOVE && event.getActionIndex() == 0) {
            if (!this.isUnlocked) {
                this.particleEffect.addDots(this.CREATED_DOTS_AMOUNT_MOVE, this.currentX, this.currentY, color);
            }
        } else if (event.getActionMasked() == 1 || event.getActionMasked() == 3) {
            if (this.DBG) {
                Log.d(this.TAG, "handleTouchEvent : ACTION_UP || ACTION_CANCEL");
            }
            this.currentX = this.centerX;
            this.currentY = this.centerY;
        }
    }

    @Override // com.samsung.android.visualeffect.IEffectView
    public void clearScreen() {
        clearEffect();
    }
}