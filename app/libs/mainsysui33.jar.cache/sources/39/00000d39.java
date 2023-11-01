package com.android.launcher3.icons;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import com.android.launcher3.util.FlagOp;

/* loaded from: mainsysui33.jar:com/android/launcher3/icons/BitmapInfo.class */
public class BitmapInfo {
    public static final Bitmap LOW_RES_ICON;
    public static final BitmapInfo LOW_RES_INFO;
    public BitmapInfo badgeInfo;
    public final int color;
    public int flags;
    public final Bitmap icon;
    public Bitmap mMono;
    public Bitmap mWhiteShadowLayer;

    /* loaded from: mainsysui33.jar:com/android/launcher3/icons/BitmapInfo$Extender.class */
    public interface Extender {
        void drawForPersistence(Canvas canvas);

        BitmapInfo getExtendedInfo(Bitmap bitmap, int i, BaseIconFactory baseIconFactory, float f);
    }

    static {
        Bitmap createBitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ALPHA_8);
        LOW_RES_ICON = createBitmap;
        LOW_RES_INFO = fromBitmap(createBitmap);
    }

    public BitmapInfo(Bitmap bitmap, int i) {
        this.icon = bitmap;
        this.color = i;
    }

    public static BitmapInfo fromBitmap(Bitmap bitmap) {
        return of(bitmap, 0);
    }

    public static BitmapInfo of(Bitmap bitmap, int i) {
        return new BitmapInfo(bitmap, i);
    }

    public void applyFlags(Context context, FastBitmapDrawable fastBitmapDrawable, int i) {
        fastBitmapDrawable.mDisabledAlpha = GraphicsUtils.getFloat(context, R$attr.disabledIconAlpha, 1.0f);
        if ((i & 2) == 0) {
            BitmapInfo bitmapInfo = this.badgeInfo;
            if (bitmapInfo != null) {
                fastBitmapDrawable.setBadge(bitmapInfo.newIcon(context, i));
                return;
            }
            int i2 = this.flags;
            if ((i2 & 2) != 0) {
                fastBitmapDrawable.setBadge(context.getDrawable(R$drawable.ic_instant_app_badge));
            } else if ((i2 & 1) != 0) {
                fastBitmapDrawable.setBadge(context.getDrawable(R$drawable.ic_work_app_badge));
            }
        }
    }

    /* JADX DEBUG: Method merged with bridge method */
    @Override // 
    /* renamed from: clone */
    public BitmapInfo mo913clone() {
        return copyInternalsTo(new BitmapInfo(this.icon, this.color));
    }

    public BitmapInfo copyInternalsTo(BitmapInfo bitmapInfo) {
        bitmapInfo.mMono = this.mMono;
        bitmapInfo.mWhiteShadowLayer = this.mWhiteShadowLayer;
        bitmapInfo.flags = this.flags;
        bitmapInfo.badgeInfo = this.badgeInfo;
        return bitmapInfo;
    }

    public final boolean isLowRes() {
        return LOW_RES_ICON == this.icon;
    }

    public FastBitmapDrawable newIcon(Context context) {
        return newIcon(context, 0);
    }

    public FastBitmapDrawable newIcon(Context context, int i) {
        PlaceHolderIconDrawable placeHolderIconDrawable = isLowRes() ? new PlaceHolderIconDrawable(this, context) : ((i & 1) == 0 || this.mMono == null) ? new FastBitmapDrawable(this) : ThemedIconDrawable.newDrawable(this, context);
        applyFlags(context, placeHolderIconDrawable, i);
        return placeHolderIconDrawable;
    }

    public void setMonoIcon(Bitmap bitmap, BaseIconFactory baseIconFactory) {
        this.mMono = bitmap;
        this.mWhiteShadowLayer = baseIconFactory.getWhiteShadowLayer();
    }

    public BitmapInfo withFlags(FlagOp flagOp) {
        if (flagOp == FlagOp.NO_OP) {
            return this;
        }
        BitmapInfo mo913clone = mo913clone();
        mo913clone.flags = flagOp.apply(mo913clone.flags);
        return mo913clone;
    }
}