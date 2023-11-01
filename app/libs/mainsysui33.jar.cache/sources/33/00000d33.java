package com.android.launcher3.icons;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.AdaptiveIconDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.os.UserHandle;
import android.util.SparseBooleanArray;
import com.android.launcher3.icons.BitmapInfo;
import com.android.launcher3.util.FlagOp;

/* loaded from: mainsysui33.jar:com/android/launcher3/icons/BaseIconFactory.class */
public class BaseIconFactory implements AutoCloseable {
    public static int PLACEHOLDER_BACKGROUND_COLOR = Color.rgb(245, 245, 245);
    public final Canvas mCanvas;
    public final ColorExtractor mColorExtractor;
    public final Context mContext;
    public final int mFillResIconDpi;
    public final int mIconBitmapSize;
    public final SparseBooleanArray mIsUserBadged;
    public boolean mMonoIconEnabled;
    public IconNormalizer mNormalizer;
    public final Rect mOldBounds;
    public final PackageManager mPm;
    public ShadowGenerator mShadowGenerator;
    public final boolean mShapeDetection;
    public Bitmap mWhiteShadowLayer;
    public int mWrapperBackgroundColor;
    public Drawable mWrapperIcon;

    /* loaded from: mainsysui33.jar:com/android/launcher3/icons/BaseIconFactory$ClippedMonoDrawable.class */
    public static class ClippedMonoDrawable extends InsetDrawable {
        public final AdaptiveIconDrawable mCrop;

        public ClippedMonoDrawable(Drawable drawable) {
            super(drawable, -AdaptiveIconDrawable.getExtraInsetFraction());
            this.mCrop = new AdaptiveIconDrawable(new ColorDrawable(-16777216), null);
        }

        @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
        public void draw(Canvas canvas) {
            this.mCrop.setBounds(getBounds());
            int save = canvas.save();
            canvas.clipPath(this.mCrop.getIconMask());
            super.draw(canvas);
            canvas.restoreToCount(save);
        }
    }

    /* loaded from: mainsysui33.jar:com/android/launcher3/icons/BaseIconFactory$IconOptions.class */
    public static class IconOptions {
        public Integer mExtractedColor;
        public boolean mIsInstantApp;
        public boolean mShrinkNonAdaptiveIcons = true;
        public UserHandle mUserHandle;

        public IconOptions setUser(UserHandle userHandle) {
            this.mUserHandle = userHandle;
            return this;
        }
    }

    /* loaded from: mainsysui33.jar:com/android/launcher3/icons/BaseIconFactory$NoopDrawable.class */
    public static class NoopDrawable extends ColorDrawable {
        public NoopDrawable() {
        }

        @Override // android.graphics.drawable.Drawable
        public int getIntrinsicHeight() {
            return 1;
        }

        @Override // android.graphics.drawable.Drawable
        public int getIntrinsicWidth() {
            return 1;
        }
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.launcher3.icons.BaseIconFactory$$ExternalSyntheticLambda0.draw(android.graphics.Canvas):void] */
    /* renamed from: $r8$lambda$TecH-7wvmZxjLpVNWshedXWce_w */
    public static /* synthetic */ void m910$r8$lambda$TecH7wvmZxjLpVNWshedXWce_w(BaseIconFactory baseIconFactory, Drawable drawable, float f, int i, Canvas canvas) {
        baseIconFactory.lambda$createIconBitmap$0(drawable, f, i, canvas);
    }

    public BaseIconFactory(Context context, int i, int i2) {
        this(context, i, i2, false);
    }

    public BaseIconFactory(Context context, int i, int i2, boolean z) {
        this.mOldBounds = new Rect();
        this.mIsUserBadged = new SparseBooleanArray();
        this.mWrapperBackgroundColor = -1;
        Context applicationContext = context.getApplicationContext();
        this.mContext = applicationContext;
        this.mShapeDetection = z;
        this.mFillResIconDpi = i;
        this.mIconBitmapSize = i2;
        this.mPm = applicationContext.getPackageManager();
        this.mColorExtractor = new ColorExtractor();
        Canvas canvas = new Canvas();
        this.mCanvas = canvas;
        canvas.setDrawFilter(new PaintFlagsDrawFilter(4, 2));
        clear();
    }

    public static int getBadgeSizeForIconSize(int i) {
        return (int) (i * 0.444f);
    }

    public /* synthetic */ void lambda$createIconBitmap$0(Drawable drawable, float f, int i, Canvas canvas) {
        drawIconBitmap(canvas, drawable, f, i, null);
    }

    public void clear() {
        this.mWrapperBackgroundColor = -1;
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        clear();
    }

    public BitmapInfo createBadgedIconBitmap(Drawable drawable) {
        return createBadgedIconBitmap(drawable, null);
    }

    @TargetApi(33)
    public BitmapInfo createBadgedIconBitmap(Drawable drawable, IconOptions iconOptions) {
        BitmapInfo bitmapInfo;
        Integer num;
        float[] fArr = new float[1];
        Drawable normalizeAndWrapToAdaptiveIcon = normalizeAndWrapToAdaptiveIcon(drawable, iconOptions == null || iconOptions.mShrinkNonAdaptiveIcons, null, fArr);
        Bitmap createIconBitmap = createIconBitmap(normalizeAndWrapToAdaptiveIcon, fArr[0], 2);
        int findDominantColorByHue = (iconOptions == null || (num = iconOptions.mExtractedColor) == null) ? this.mColorExtractor.findDominantColorByHue(createIconBitmap) : num.intValue();
        BitmapInfo of = BitmapInfo.of(createIconBitmap, findDominantColorByHue);
        if (normalizeAndWrapToAdaptiveIcon instanceof BitmapInfo.Extender) {
            bitmapInfo = ((BitmapInfo.Extender) normalizeAndWrapToAdaptiveIcon).getExtendedInfo(createIconBitmap, findDominantColorByHue, this, fArr[0]);
        } else {
            bitmapInfo = of;
            if (IconProvider.ATLEAST_T) {
                bitmapInfo = of;
                if (this.mMonoIconEnabled) {
                    Drawable monochromeDrawable = getMonochromeDrawable(normalizeAndWrapToAdaptiveIcon);
                    bitmapInfo = of;
                    if (monochromeDrawable != null) {
                        of.setMonoIcon(createIconBitmap(monochromeDrawable, fArr[0], 1), this);
                        bitmapInfo = of;
                    }
                }
            }
        }
        return bitmapInfo.withFlags(getBitmapFlagOp(iconOptions));
    }

    public Bitmap createIconBitmap(Drawable drawable, float f) {
        return createIconBitmap(drawable, f, 0);
    }

    public Bitmap createIconBitmap(final Drawable drawable, final float f, final int i) {
        Bitmap createBitmap;
        int i2 = this.mIconBitmapSize;
        if (i == 1) {
            createBitmap = Bitmap.createBitmap(i2, i2, Bitmap.Config.ALPHA_8);
        } else if (i == 3 || i == 4) {
            return BitmapRenderer.createHardwareBitmap(i2, i2, new BitmapRenderer() { // from class: com.android.launcher3.icons.BaseIconFactory$$ExternalSyntheticLambda0
                @Override // com.android.launcher3.icons.BitmapRenderer
                public final void draw(Canvas canvas) {
                    BaseIconFactory.m910$r8$lambda$TecH7wvmZxjLpVNWshedXWce_w(BaseIconFactory.this, drawable, f, i, canvas);
                }
            });
        } else {
            createBitmap = Bitmap.createBitmap(i2, i2, Bitmap.Config.ARGB_8888);
        }
        if (drawable == null) {
            return createBitmap;
        }
        this.mCanvas.setBitmap(createBitmap);
        drawIconBitmap(this.mCanvas, drawable, f, i, createBitmap);
        this.mCanvas.setBitmap(null);
        return createBitmap;
    }

    /* JADX WARN: Code restructure failed: missing block: B:14:0x0015, code lost:
        if (r6.mIconBitmapSize != r7.getHeight()) goto L8;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public BitmapInfo createIconBitmap(Bitmap bitmap) {
        Bitmap createIconBitmap;
        if (this.mIconBitmapSize == bitmap.getWidth()) {
            createIconBitmap = bitmap;
        }
        createIconBitmap = createIconBitmap(new BitmapDrawable(this.mContext.getResources(), bitmap), 1.0f);
        return BitmapInfo.of(createIconBitmap, this.mColorExtractor.findDominantColorByHue(createIconBitmap));
    }

    public Bitmap createScaledBitmap(Drawable drawable, int i) {
        RectF rectF = new RectF();
        float[] fArr = new float[1];
        return createIconBitmap(normalizeAndWrapToAdaptiveIcon(drawable, true, rectF, fArr), Math.min(fArr[0], ShadowGenerator.getScaleForBounds(rectF)), i);
    }

    public final void drawIconBitmap(Canvas canvas, Drawable drawable, float f, int i, Bitmap bitmap) {
        int i2;
        int i3;
        float f2;
        BitmapDrawable bitmapDrawable;
        Bitmap bitmap2;
        float f3;
        int i4 = this.mIconBitmapSize;
        this.mOldBounds.set(drawable.getBounds());
        if (drawable instanceof AdaptiveIconDrawable) {
            int max = Math.max((int) Math.ceil(0.035f * f3), Math.round((i4 * (1.0f - f)) / 2.0f));
            int i5 = (i4 - max) - max;
            drawable.setBounds(0, 0, i5, i5);
            int save = canvas.save();
            float f4 = max;
            canvas.translate(f4, f4);
            if (i == 2 || i == 4) {
                getShadowGenerator().addPathShadow(((AdaptiveIconDrawable) drawable).getIconMask(), canvas);
            }
            if (drawable instanceof BitmapInfo.Extender) {
                ((BitmapInfo.Extender) drawable).drawForPersistence(canvas);
            } else {
                drawable.draw(canvas);
            }
            canvas.restoreToCount(save);
        } else {
            if ((drawable instanceof BitmapDrawable) && (bitmap2 = (bitmapDrawable = (BitmapDrawable) drawable).getBitmap()) != null && bitmap2.getDensity() == 0) {
                bitmapDrawable.setTargetDensity(this.mContext.getResources().getDisplayMetrics());
            }
            int intrinsicWidth = drawable.getIntrinsicWidth();
            int intrinsicHeight = drawable.getIntrinsicHeight();
            if (intrinsicWidth > 0 && intrinsicHeight > 0) {
                float f5 = intrinsicWidth / intrinsicHeight;
                if (intrinsicWidth > intrinsicHeight) {
                    i3 = (int) (i4 / f5);
                    i2 = i4;
                } else if (intrinsicHeight > intrinsicWidth) {
                    i2 = (int) (i4 * f5);
                    i3 = i4;
                }
                int i6 = (i4 - i2) / 2;
                int i7 = (i4 - i3) / 2;
                drawable.setBounds(i6, i7, i2 + i6, i3 + i7);
                canvas.save();
                f2 = i4 / 2;
                canvas.scale(f, f, f2, f2);
                drawable.draw(canvas);
                canvas.restore();
                if (i == 2 && bitmap != null) {
                    getShadowGenerator().drawShadow(bitmap, canvas);
                    canvas.save();
                    canvas.scale(f, f, f2, f2);
                    drawable.draw(canvas);
                    canvas.restore();
                }
            }
            i2 = i4;
            i3 = i2;
            int i62 = (i4 - i2) / 2;
            int i72 = (i4 - i3) / 2;
            drawable.setBounds(i62, i72, i2 + i62, i3 + i72);
            canvas.save();
            f2 = i4 / 2;
            canvas.scale(f, f, f2, f2);
            drawable.draw(canvas);
            canvas.restore();
            if (i == 2) {
                getShadowGenerator().drawShadow(bitmap, canvas);
                canvas.save();
                canvas.scale(f, f, f2, f2);
                drawable.draw(canvas);
                canvas.restore();
            }
        }
        drawable.setBounds(this.mOldBounds);
    }

    public FlagOp getBitmapFlagOp(IconOptions iconOptions) {
        boolean z;
        FlagOp flagOp = FlagOp.NO_OP;
        FlagOp flagOp2 = flagOp;
        if (iconOptions != null) {
            FlagOp flagOp3 = flagOp;
            if (iconOptions.mIsInstantApp) {
                flagOp3 = flagOp.addFlag(2);
            }
            UserHandle userHandle = iconOptions.mUserHandle;
            flagOp2 = flagOp3;
            if (userHandle != null) {
                int hashCode = userHandle.hashCode();
                int indexOfKey = this.mIsUserBadged.indexOfKey(hashCode);
                if (indexOfKey >= 0) {
                    z = this.mIsUserBadged.valueAt(indexOfKey);
                } else {
                    NoopDrawable noopDrawable = new NoopDrawable();
                    z = noopDrawable != this.mPm.getUserBadgedIcon(noopDrawable, iconOptions.mUserHandle);
                    this.mIsUserBadged.put(hashCode, z);
                }
                flagOp2 = flagOp3.setFlag(1, z);
            }
        }
        return flagOp2;
    }

    @TargetApi(33)
    public Drawable getMonochromeDrawable(Drawable drawable) {
        Drawable monochrome;
        if (!(drawable instanceof AdaptiveIconDrawable) || (monochrome = ((AdaptiveIconDrawable) drawable).getMonochrome()) == null) {
            return null;
        }
        return new ClippedMonoDrawable(monochrome);
    }

    public IconNormalizer getNormalizer() {
        if (this.mNormalizer == null) {
            this.mNormalizer = new IconNormalizer(this.mContext, this.mIconBitmapSize, this.mShapeDetection);
        }
        return this.mNormalizer;
    }

    public ShadowGenerator getShadowGenerator() {
        if (this.mShadowGenerator == null) {
            this.mShadowGenerator = new ShadowGenerator(this.mIconBitmapSize);
        }
        return this.mShadowGenerator;
    }

    public Bitmap getWhiteShadowLayer() {
        if (this.mWhiteShadowLayer == null) {
            this.mWhiteShadowLayer = createScaledBitmap(new AdaptiveIconDrawable(new ColorDrawable(-1), null), 4);
        }
        return this.mWhiteShadowLayer;
    }

    public Drawable normalizeAndWrapToAdaptiveIcon(Drawable drawable, boolean z, RectF rectF, float[] fArr) {
        float scale;
        AdaptiveIconDrawable adaptiveIconDrawable;
        if (drawable == null) {
            return null;
        }
        if (!z || (drawable instanceof AdaptiveIconDrawable)) {
            scale = getNormalizer().getScale(drawable, rectF, null, null);
            adaptiveIconDrawable = drawable;
        } else {
            if (this.mWrapperIcon == null) {
                this.mWrapperIcon = this.mContext.getDrawable(R$drawable.adaptive_icon_drawable_wrapper).mutate();
            }
            AdaptiveIconDrawable adaptiveIconDrawable2 = (AdaptiveIconDrawable) this.mWrapperIcon;
            adaptiveIconDrawable2.setBounds(0, 0, 1, 1);
            boolean[] zArr = new boolean[1];
            float scale2 = getNormalizer().getScale(drawable, rectF, adaptiveIconDrawable2.getIconMask(), zArr);
            scale = scale2;
            adaptiveIconDrawable = drawable;
            if (!zArr[0]) {
                FixedScaleDrawable fixedScaleDrawable = (FixedScaleDrawable) adaptiveIconDrawable2.getForeground();
                fixedScaleDrawable.setDrawable(drawable);
                fixedScaleDrawable.setScale(scale2);
                scale = getNormalizer().getScale(adaptiveIconDrawable2, rectF, null, null);
                ((ColorDrawable) adaptiveIconDrawable2.getBackground()).setColor(this.mWrapperBackgroundColor);
                adaptiveIconDrawable = adaptiveIconDrawable2;
            }
        }
        fArr[0] = scale;
        return adaptiveIconDrawable;
    }
}