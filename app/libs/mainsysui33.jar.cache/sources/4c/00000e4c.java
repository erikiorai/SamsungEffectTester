package com.android.settingslib.drawable;

import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.UserHandle;
import com.android.settingslib.utils.BuildCompatUtils;
import com.android.settingslib.widget.ActionBarShadowController;
import java.util.function.Supplier;

/* loaded from: mainsysui33.jar:com/android/settingslib/drawable/UserIconDrawable.class */
public class UserIconDrawable extends Drawable implements Drawable.Callback {
    public Drawable mBadge;
    public float mBadgeMargin;
    public float mBadgeRadius;
    public Bitmap mBitmap;
    public Paint mClearPaint;
    public float mDisplayRadius;
    public ColorStateList mFrameColor;
    public float mFramePadding;
    public Paint mFramePaint;
    public float mFrameWidth;
    public final Matrix mIconMatrix;
    public final Paint mIconPaint;
    public float mIntrinsicRadius;
    public boolean mInvalidated;
    public float mPadding;
    public final Paint mPaint;
    public int mSize;
    public ColorStateList mTintColor;
    public PorterDuff.Mode mTintMode;
    public Drawable mUserDrawable;
    public Bitmap mUserIcon;

    /* JADX DEBUG: Method not inlined, still used in: [com.android.settingslib.drawable.UserIconDrawable$$ExternalSyntheticLambda0.get():java.lang.Object] */
    /* renamed from: $r8$lambda$ZQbyljPHl-5HAuZIEdYu4pJYSQE */
    public static /* synthetic */ Drawable m1088$r8$lambda$ZQbyljPHl5HAuZIEdYu4pJYSQE(Context context) {
        return getDrawableForDisplayDensity(context, 17302400);
    }

    public UserIconDrawable() {
        this(0);
    }

    public UserIconDrawable(int i) {
        Paint paint = new Paint();
        this.mIconPaint = paint;
        Paint paint2 = new Paint();
        this.mPaint = paint2;
        this.mIconMatrix = new Matrix();
        this.mPadding = ActionBarShadowController.ELEVATION_LOW;
        this.mSize = 0;
        this.mInvalidated = true;
        this.mTintColor = null;
        this.mTintMode = PorterDuff.Mode.SRC_ATOP;
        this.mFrameColor = null;
        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        paint2.setFilterBitmap(true);
        paint2.setAntiAlias(true);
        if (i > 0) {
            setBounds(0, 0, i, i);
            setIntrinsicSize(i);
        }
        setIcon(null);
    }

    public static Drawable getDrawableForDisplayDensity(Context context, int i) {
        return context.getResources().getDrawableForDensity(i, context.getResources().getDisplayMetrics().densityDpi, context.getTheme());
    }

    public static Drawable getManagementBadge(Context context) {
        return BuildCompatUtils.isAtLeastT() ? getUpdatableManagementBadge(context) : getDrawableForDisplayDensity(context, 17302409);
    }

    public static Drawable getUpdatableManagementBadge(final Context context) {
        return ((DevicePolicyManager) context.getSystemService(DevicePolicyManager.class)).getResources().getDrawableForDensity("WORK_PROFILE_ICON", "SOLID_COLORED", context.getResources().getDisplayMetrics().densityDpi, new Supplier() { // from class: com.android.settingslib.drawable.UserIconDrawable$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final Object get() {
                return UserIconDrawable.m1088$r8$lambda$ZQbyljPHl5HAuZIEdYu4pJYSQE(context);
            }
        });
    }

    public UserIconDrawable bake() {
        if (this.mSize > 0) {
            int i = this.mSize;
            onBoundsChange(new Rect(0, 0, i, i));
            rebake();
            this.mFrameColor = null;
            this.mFramePaint = null;
            this.mClearPaint = null;
            Drawable drawable = this.mUserDrawable;
            if (drawable != null) {
                drawable.setCallback(null);
                this.mUserDrawable = null;
            } else {
                Bitmap bitmap = this.mUserIcon;
                if (bitmap != null) {
                    bitmap.recycle();
                    this.mUserIcon = null;
                }
            }
            return this;
        }
        throw new IllegalStateException("Baking requires an explicit intrinsic size");
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        if (this.mInvalidated) {
            rebake();
        }
        if (this.mBitmap != null) {
            ColorStateList colorStateList = this.mTintColor;
            if (colorStateList == null) {
                this.mPaint.setColorFilter(null);
            } else {
                int colorForState = colorStateList.getColorForState(getState(), this.mTintColor.getDefaultColor());
                if (shouldUpdateColorFilter(colorForState, this.mTintMode)) {
                    this.mPaint.setColorFilter(new PorterDuffColorFilter(colorForState, this.mTintMode));
                }
            }
            canvas.drawBitmap(this.mBitmap, ActionBarShadowController.ELEVATION_LOW, ActionBarShadowController.ELEVATION_LOW, this.mPaint);
        }
    }

    public Drawable getBadge() {
        return this.mBadge;
    }

    @Override // android.graphics.drawable.Drawable
    public Drawable.ConstantState getConstantState() {
        return new BitmapDrawable(this.mBitmap).getConstantState();
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicHeight() {
        return getIntrinsicWidth();
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicWidth() {
        int i = this.mSize;
        int i2 = i;
        if (i <= 0) {
            i2 = ((int) this.mIntrinsicRadius) * 2;
        }
        return i2;
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return -3;
    }

    public Drawable getUserDrawable() {
        return this.mUserDrawable;
    }

    public Bitmap getUserIcon() {
        return this.mUserIcon;
    }

    public final void initFramePaint() {
        if (this.mFramePaint == null) {
            Paint paint = new Paint();
            this.mFramePaint = paint;
            paint.setStyle(Paint.Style.STROKE);
            this.mFramePaint.setAntiAlias(true);
        }
    }

    @Override // android.graphics.drawable.Drawable.Callback
    public void invalidateDrawable(Drawable drawable) {
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public void invalidateSelf() {
        super.invalidateSelf();
        this.mInvalidated = true;
    }

    public boolean isEmpty() {
        return getUserIcon() == null && getUserDrawable() == null;
    }

    public boolean isInvalidated() {
        return this.mInvalidated;
    }

    @Override // android.graphics.drawable.Drawable
    public boolean isStateful() {
        ColorStateList colorStateList = this.mFrameColor;
        return colorStateList != null && colorStateList.isStateful();
    }

    @Override // android.graphics.drawable.Drawable
    public void onBoundsChange(Rect rect) {
        if (rect.isEmpty()) {
            return;
        }
        if (this.mUserIcon == null && this.mUserDrawable == null) {
            return;
        }
        float min = Math.min(rect.width(), rect.height()) * 0.5f;
        int i = (int) (min * 2.0f);
        Bitmap bitmap = this.mBitmap;
        if (bitmap == null || i != ((int) (this.mDisplayRadius * 2.0f))) {
            this.mDisplayRadius = min;
            if (bitmap != null) {
                bitmap.recycle();
            }
            this.mBitmap = Bitmap.createBitmap(i, i, Bitmap.Config.ARGB_8888);
        }
        float min2 = Math.min(rect.width(), rect.height()) * 0.5f;
        this.mDisplayRadius = min2;
        float f = ((min2 - this.mFrameWidth) - this.mFramePadding) - this.mPadding;
        RectF rectF = new RectF(rect.exactCenterX() - f, rect.exactCenterY() - f, rect.exactCenterX() + f, rect.exactCenterY() + f);
        if (this.mUserDrawable != null) {
            Rect rect2 = new Rect();
            rectF.round(rect2);
            this.mIntrinsicRadius = Math.min(this.mUserDrawable.getIntrinsicWidth(), this.mUserDrawable.getIntrinsicHeight()) * 0.5f;
            this.mUserDrawable.setBounds(rect2);
        } else {
            Bitmap bitmap2 = this.mUserIcon;
            if (bitmap2 != null) {
                float width = bitmap2.getWidth() * 0.5f;
                float height = this.mUserIcon.getHeight() * 0.5f;
                this.mIntrinsicRadius = Math.min(width, height);
                float f2 = this.mIntrinsicRadius;
                this.mIconMatrix.setRectToRect(new RectF(width - f2, height - f2, width + f2, height + f2), rectF, Matrix.ScaleToFit.FILL);
            }
        }
        invalidateSelf();
    }

    public final void rebake() {
        this.mInvalidated = false;
        if (this.mBitmap != null) {
            if (this.mUserDrawable == null && this.mUserIcon == null) {
                return;
            }
            Canvas canvas = new Canvas(this.mBitmap);
            canvas.drawColor(0, PorterDuff.Mode.CLEAR);
            Drawable drawable = this.mUserDrawable;
            if (drawable != null) {
                drawable.draw(canvas);
            } else if (this.mUserIcon != null) {
                int save = canvas.save();
                canvas.concat(this.mIconMatrix);
                canvas.drawCircle(this.mUserIcon.getWidth() * 0.5f, this.mUserIcon.getHeight() * 0.5f, this.mIntrinsicRadius, this.mIconPaint);
                canvas.restoreToCount(save);
            }
            ColorStateList colorStateList = this.mFrameColor;
            if (colorStateList != null) {
                this.mFramePaint.setColor(colorStateList.getColorForState(getState(), 0));
            }
            float f = this.mFrameWidth;
            if (this.mFramePadding + f > 0.001f) {
                canvas.drawCircle(getBounds().exactCenterX(), getBounds().exactCenterY(), (this.mDisplayRadius - this.mPadding) - (f * 0.5f), this.mFramePaint);
            }
            if (this.mBadge != null) {
                float f2 = this.mBadgeRadius;
                if (f2 > 0.001f) {
                    float f3 = f2 * 2.0f;
                    float height = this.mBitmap.getHeight() - f3;
                    float width = this.mBitmap.getWidth() - f3;
                    this.mBadge.setBounds((int) width, (int) height, (int) (width + f3), (int) (f3 + height));
                    float width2 = this.mBadge.getBounds().width();
                    float f4 = this.mBadgeMargin;
                    float f5 = this.mBadgeRadius;
                    canvas.drawCircle(width + f5, height + f5, (width2 * 0.5f) + f4, this.mClearPaint);
                    this.mBadge.draw(canvas);
                }
            }
        }
    }

    @Override // android.graphics.drawable.Drawable.Callback
    public void scheduleDrawable(Drawable drawable, Runnable runnable, long j) {
        scheduleSelf(runnable, j);
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
        this.mPaint.setAlpha(i);
        super.invalidateSelf();
    }

    public UserIconDrawable setBadge(Drawable drawable) {
        this.mBadge = drawable;
        if (drawable != null) {
            if (this.mClearPaint == null) {
                Paint paint = new Paint();
                this.mClearPaint = paint;
                paint.setAntiAlias(true);
                this.mClearPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
                this.mClearPaint.setStyle(Paint.Style.FILL);
            }
            onBoundsChange(getBounds());
        } else {
            invalidateSelf();
        }
        return this;
    }

    public UserIconDrawable setBadgeIfManagedUser(Context context, int i) {
        Drawable drawable;
        if (i != -10000) {
            DevicePolicyManager devicePolicyManager = (DevicePolicyManager) context.getSystemService(DevicePolicyManager.class);
            if (devicePolicyManager.getProfileOwnerAsUser(i) != null && devicePolicyManager.getProfileOwnerOrDeviceOwnerSupervisionComponent(UserHandle.of(i)) == null) {
                drawable = getManagementBadge(context);
                return setBadge(drawable);
            }
        }
        drawable = null;
        return setBadge(drawable);
    }

    public void setBadgeMargin(float f) {
        this.mBadgeMargin = f;
        onBoundsChange(getBounds());
    }

    public void setBadgeRadius(float f) {
        this.mBadgeRadius = f;
        onBoundsChange(getBounds());
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
    }

    public void setFrameColor(ColorStateList colorStateList) {
        initFramePaint();
        this.mFrameColor = colorStateList;
        invalidateSelf();
    }

    public void setFramePadding(float f) {
        initFramePaint();
        this.mFramePadding = f;
        onBoundsChange(getBounds());
    }

    public void setFrameWidth(float f) {
        initFramePaint();
        this.mFrameWidth = f;
        this.mFramePaint.setStrokeWidth(f);
        onBoundsChange(getBounds());
    }

    public UserIconDrawable setIcon(Bitmap bitmap) {
        Drawable drawable = this.mUserDrawable;
        if (drawable != null) {
            drawable.setCallback(null);
            this.mUserDrawable = null;
        }
        this.mUserIcon = bitmap;
        if (bitmap == null) {
            this.mIconPaint.setShader(null);
            this.mBitmap = null;
        } else {
            Paint paint = this.mIconPaint;
            Shader.TileMode tileMode = Shader.TileMode.CLAMP;
            paint.setShader(new BitmapShader(bitmap, tileMode, tileMode));
        }
        onBoundsChange(getBounds());
        return this;
    }

    public UserIconDrawable setIconDrawable(Drawable drawable) {
        Drawable drawable2 = this.mUserDrawable;
        if (drawable2 != null) {
            drawable2.setCallback(null);
        }
        this.mUserIcon = null;
        this.mUserDrawable = drawable;
        if (drawable == null) {
            this.mBitmap = null;
        } else {
            drawable.setCallback(this);
        }
        onBoundsChange(getBounds());
        return this;
    }

    public void setIntrinsicSize(int i) {
        this.mSize = i;
    }

    public void setPadding(float f) {
        this.mPadding = f;
        onBoundsChange(getBounds());
    }

    @Override // android.graphics.drawable.Drawable
    public void setTintList(ColorStateList colorStateList) {
        this.mTintColor = colorStateList;
        super.invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public void setTintMode(PorterDuff.Mode mode) {
        this.mTintMode = mode;
        super.invalidateSelf();
    }

    public final boolean shouldUpdateColorFilter(int i, PorterDuff.Mode mode) {
        ColorFilter colorFilter = this.mPaint.getColorFilter();
        boolean z = true;
        if (colorFilter instanceof PorterDuffColorFilter) {
            PorterDuffColorFilter porterDuffColorFilter = (PorterDuffColorFilter) colorFilter;
            int color = porterDuffColorFilter.getColor();
            PorterDuff.Mode mode2 = porterDuffColorFilter.getMode();
            z = true;
            if (color == i) {
                z = mode2 != mode;
            }
        }
        return z;
    }

    @Override // android.graphics.drawable.Drawable.Callback
    public void unscheduleDrawable(Drawable drawable, Runnable runnable) {
        unscheduleSelf(runnable);
    }
}