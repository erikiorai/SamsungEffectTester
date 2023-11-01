package com.android.systemui.people;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.IconDrawableFactory;
import android.util.Log;
import android.view.ContextThemeWrapper;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.recyclerview.widget.RecyclerView;
import com.android.settingslib.Utils;
import com.android.systemui.R$color;

/* loaded from: mainsysui33.jar:com/android/systemui/people/PeopleStoryIconFactory.class */
public class PeopleStoryIconFactory implements AutoCloseable {
    public int mAccentColor;
    public Context mContext;
    public float mDensity;
    public final int mIconBitmapSize;
    public final IconDrawableFactory mIconDrawableFactory;
    public float mIconSize;
    public int mImportantConversationColor;
    public final PackageManager mPackageManager;

    /* loaded from: mainsysui33.jar:com/android/systemui/people/PeopleStoryIconFactory$PeopleStoryIconDrawable.class */
    public static class PeopleStoryIconDrawable extends Drawable {
        public RoundedBitmapDrawable mAvatar;
        public Drawable mBadgeIcon;
        public float mDensity;
        public float mFullIconSize;
        public int mIconSize;
        public Paint mPriorityRingPaint;
        public boolean mShowImportantRing;
        public boolean mShowStoryRing;
        public Paint mStoryPaint;

        public PeopleStoryIconDrawable(RoundedBitmapDrawable roundedBitmapDrawable, Drawable drawable, int i, int i2, boolean z, float f, float f2, int i3, boolean z2) {
            roundedBitmapDrawable.setCircular(true);
            this.mAvatar = roundedBitmapDrawable;
            this.mBadgeIcon = drawable;
            this.mIconSize = i;
            this.mShowImportantRing = z;
            Paint paint = new Paint();
            this.mPriorityRingPaint = paint;
            paint.setStyle(Paint.Style.FILL_AND_STROKE);
            this.mPriorityRingPaint.setColor(i2);
            this.mShowStoryRing = z2;
            Paint paint2 = new Paint();
            this.mStoryPaint = paint2;
            paint2.setStyle(Paint.Style.STROKE);
            this.mStoryPaint.setColor(i3);
            this.mFullIconSize = f;
            this.mDensity = f2;
        }

        @Override // android.graphics.drawable.Drawable
        public void draw(Canvas canvas) {
            Rect bounds = getBounds();
            float min = Math.min(bounds.height(), bounds.width()) / this.mFullIconSize;
            float f = this.mDensity;
            int i = (int) (f * 2.0f);
            int i2 = (int) (f * 2.0f);
            float f2 = i2;
            this.mPriorityRingPaint.setStrokeWidth(f2);
            this.mStoryPaint.setStrokeWidth(f2);
            int i3 = (int) (this.mFullIconSize * min);
            int i4 = i3 - (i * 2);
            if (this.mAvatar != null) {
                int i5 = i4 + i;
                int i6 = i;
                int i7 = i5;
                if (this.mShowStoryRing) {
                    float f3 = i3 / 2;
                    canvas.drawCircle(f3, f3, getRadius(i4, i2), this.mStoryPaint);
                    int i8 = i2 + i;
                    i6 = i + i8;
                    i7 = i5 - i8;
                }
                this.mAvatar.setBounds(i6, i6, i7, i7);
                this.mAvatar.draw(canvas);
            } else {
                Log.w("PeopleStoryIconFactory", "Null avatar icon");
            }
            int min2 = Math.min((int) (this.mDensity * 40.0f), (int) (i4 / 2.4d));
            if (this.mBadgeIcon == null) {
                Log.w("PeopleStoryIconFactory", "Null badge icon");
                return;
            }
            int i9 = i3 - min2;
            int i10 = i3;
            int i11 = i9;
            if (this.mShowImportantRing) {
                float f4 = (min2 / 2) + i9;
                canvas.drawCircle(f4, f4, getRadius(min2, i2), this.mPriorityRingPaint);
                i11 = i9 + i2;
                i10 = i3 - i2;
            }
            this.mBadgeIcon.setBounds(i11, i11, i10, i10);
            this.mBadgeIcon.draw(canvas);
        }

        @Override // android.graphics.drawable.Drawable
        public int getIntrinsicHeight() {
            return this.mIconSize;
        }

        @Override // android.graphics.drawable.Drawable
        public int getIntrinsicWidth() {
            return this.mIconSize;
        }

        @Override // android.graphics.drawable.Drawable
        public int getOpacity() {
            return -3;
        }

        public final int getRadius(int i, int i2) {
            return (i - i2) / 2;
        }

        @Override // android.graphics.drawable.Drawable
        public void setAlpha(int i) {
        }

        @Override // android.graphics.drawable.Drawable
        public void setColorFilter(ColorFilter colorFilter) {
            RoundedBitmapDrawable roundedBitmapDrawable = this.mAvatar;
            if (roundedBitmapDrawable != null) {
                roundedBitmapDrawable.setColorFilter(colorFilter);
            }
            Drawable drawable = this.mBadgeIcon;
            if (drawable != null) {
                drawable.setColorFilter(colorFilter);
            }
        }
    }

    public PeopleStoryIconFactory(Context context, PackageManager packageManager, IconDrawableFactory iconDrawableFactory, int i) {
        ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(context, 16974563);
        this.mContext = contextThemeWrapper;
        float f = i;
        this.mIconBitmapSize = (int) (contextThemeWrapper.getResources().getDisplayMetrics().density * f);
        float f2 = this.mContext.getResources().getDisplayMetrics().density;
        this.mDensity = f2;
        this.mIconSize = f2 * f;
        this.mPackageManager = packageManager;
        this.mIconDrawableFactory = iconDrawableFactory;
        this.mImportantConversationColor = this.mContext.getColor(R$color.important_conversation);
        this.mAccentColor = Utils.getColorAttr(this.mContext, 17956901).getDefaultColor();
    }

    @Override // java.lang.AutoCloseable
    public void close() {
    }

    public final Drawable getAppBadge(String str, int i) {
        Drawable defaultActivityIcon;
        try {
            defaultActivityIcon = Utils.getBadgedIcon(this.mContext, this.mPackageManager.getApplicationInfoAsUser(str, RecyclerView.ViewHolder.FLAG_IGNORE, i));
        } catch (PackageManager.NameNotFoundException e) {
            defaultActivityIcon = this.mPackageManager.getDefaultActivityIcon();
        }
        return defaultActivityIcon;
    }

    public Drawable getPeopleTileDrawable(RoundedBitmapDrawable roundedBitmapDrawable, String str, int i, boolean z, boolean z2) {
        return new PeopleStoryIconDrawable(roundedBitmapDrawable, getAppBadge(str, i), this.mIconBitmapSize, this.mImportantConversationColor, z, this.mIconSize, this.mDensity, this.mAccentColor, z2);
    }
}