package com.android.systemui.navigationbar.buttons;

import android.animation.ObjectAnimator;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.os.SystemClock;
import android.util.FloatProperty;
import android.util.Slog;
import android.view.MotionEvent;
import com.android.settingslib.widget.ActionBarShadowController;
import com.android.systemui.Dependency;
import com.android.systemui.R$bool;
import com.android.systemui.R$dimen;
import com.android.systemui.R$integer;
import com.android.systemui.navigationbar.NavigationBarController;
import com.android.systemui.navigationbar.NavigationBarView;

/* loaded from: mainsysui33.jar:com/android/systemui/navigationbar/buttons/DeadZone.class */
public class DeadZone {
    public static final FloatProperty<DeadZone> FLASH_PROPERTY = new FloatProperty<DeadZone>("DeadZoneFlash") { // from class: com.android.systemui.navigationbar.buttons.DeadZone.1
        /* JADX DEBUG: Method merged with bridge method */
        @Override // android.util.Property
        public Float get(DeadZone deadZone) {
            return Float.valueOf(deadZone.getFlash());
        }

        /* JADX DEBUG: Method merged with bridge method */
        @Override // android.util.FloatProperty
        public void setValue(DeadZone deadZone, float f) {
            deadZone.setFlash(f);
        }
    };
    public int mDecay;
    public final int mDisplayId;
    public int mDisplayRotation;
    public int mHold;
    public long mLastPokeTime;
    public final NavigationBarView mNavigationBarView;
    public boolean mShouldFlash;
    public int mSizeMax;
    public int mSizeMin;
    public boolean mVertical;
    public float mFlashFrac = ActionBarShadowController.ELEVATION_LOW;
    public final Runnable mDebugFlash = new Runnable() { // from class: com.android.systemui.navigationbar.buttons.DeadZone.2
        @Override // java.lang.Runnable
        public void run() {
            ObjectAnimator.ofFloat(DeadZone.this, DeadZone.FLASH_PROPERTY, 1.0f, ActionBarShadowController.ELEVATION_LOW).setDuration(150L).start();
        }
    };
    public final NavigationBarController mNavBarController = (NavigationBarController) Dependency.get(NavigationBarController.class);

    public DeadZone(NavigationBarView navigationBarView) {
        this.mNavigationBarView = navigationBarView;
        this.mDisplayId = navigationBarView.getContext().getDisplayId();
        onConfigurationChanged(0);
    }

    public static float lerp(float f, float f2, float f3) {
        return ((f2 - f) * f3) + f;
    }

    public float getFlash() {
        return this.mFlashFrac;
    }

    public final float getSize(long j) {
        int lerp;
        int i = this.mSizeMax;
        if (i == 0) {
            return ActionBarShadowController.ELEVATION_LOW;
        }
        long j2 = j - this.mLastPokeTime;
        int i2 = this.mHold;
        int i3 = this.mDecay;
        if (j2 > i2 + i3) {
            lerp = this.mSizeMin;
        } else if (j2 < i2) {
            return i;
        } else {
            lerp = (int) lerp(i, this.mSizeMin, ((float) (j2 - i2)) / i3);
        }
        return lerp;
    }

    public void onConfigurationChanged(int i) {
        this.mDisplayRotation = i;
        Resources resources = this.mNavigationBarView.getResources();
        this.mHold = resources.getInteger(R$integer.navigation_bar_deadzone_hold);
        this.mDecay = resources.getInteger(R$integer.navigation_bar_deadzone_decay);
        this.mSizeMin = resources.getDimensionPixelSize(R$dimen.navigation_bar_deadzone_size);
        this.mSizeMax = resources.getDimensionPixelSize(R$dimen.navigation_bar_deadzone_size_max);
        boolean z = true;
        if (resources.getInteger(R$integer.navigation_bar_deadzone_orientation) != 1) {
            z = false;
        }
        this.mVertical = z;
        setFlashOnTouchCapture(resources.getBoolean(R$bool.config_dead_zone_flash));
    }

    public void onDraw(Canvas canvas) {
        if (!this.mShouldFlash || this.mFlashFrac <= ActionBarShadowController.ELEVATION_LOW) {
            return;
        }
        int size = (int) getSize(SystemClock.uptimeMillis());
        if (!this.mVertical) {
            canvas.clipRect(0, 0, canvas.getWidth(), size);
        } else if (this.mDisplayRotation == 3) {
            canvas.clipRect(canvas.getWidth() - size, 0, canvas.getWidth(), canvas.getHeight());
        } else {
            canvas.clipRect(0, 0, size, canvas.getHeight());
        }
        canvas.drawARGB((int) (this.mFlashFrac * 255.0f), 221, 238, 170);
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getToolType(0) == 3) {
            return false;
        }
        int action = motionEvent.getAction();
        if (action == 4) {
            poke(motionEvent);
            return true;
        } else if (action == 0) {
            this.mNavBarController.touchAutoDim(this.mDisplayId);
            int size = (int) getSize(motionEvent.getEventTime());
            if (!this.mVertical ? motionEvent.getY() >= ((float) size) : this.mDisplayRotation != 3 ? motionEvent.getX() >= ((float) size) : motionEvent.getX() <= ((float) (this.mNavigationBarView.getWidth() - size))) {
                Slog.v("DeadZone", "consuming errant click: (" + motionEvent.getX() + "," + motionEvent.getY() + ")");
                if (this.mShouldFlash) {
                    this.mNavigationBarView.post(this.mDebugFlash);
                    this.mNavigationBarView.postInvalidate();
                    return true;
                }
                return true;
            }
            return false;
        } else {
            return false;
        }
    }

    public final void poke(MotionEvent motionEvent) {
        this.mLastPokeTime = motionEvent.getEventTime();
        if (this.mShouldFlash) {
            this.mNavigationBarView.postInvalidate();
        }
    }

    public void setFlash(float f) {
        this.mFlashFrac = f;
        this.mNavigationBarView.postInvalidate();
    }

    public void setFlashOnTouchCapture(boolean z) {
        this.mShouldFlash = z;
        this.mFlashFrac = ActionBarShadowController.ELEVATION_LOW;
        this.mNavigationBarView.postInvalidate();
    }
}