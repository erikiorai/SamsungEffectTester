package com.android.launcher3.icons;

import android.annotation.TargetApi;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.AdaptiveIconDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import androidx.core.os.BuildCompat;
import java.util.Calendar;
import java.util.function.Supplier;

/* loaded from: mainsysui33.jar:com/android/launcher3/icons/IconProvider.class */
public class IconProvider {
    public final String ACTION_OVERLAY_CHANGED = "android.intent.action.OVERLAY_CHANGED";
    public final ComponentName mCalendar;
    public final ComponentName mClock;
    public final Context mContext;
    public static final int CONFIG_ICON_MASK_RES_ID = Resources.getSystem().getIdentifier("config_icon_mask", "string", "android");
    public static final boolean ATLEAST_T = BuildCompat.isAtLeastT();

    /* loaded from: mainsysui33.jar:com/android/launcher3/icons/IconProvider$ThemeData.class */
    public static class ThemeData {
        public final int mResID;
        public final Resources mResources;

        public ThemeData(Resources resources, int i) {
            this.mResources = resources;
            this.mResID = i;
        }

        public Drawable loadPaddedDrawable() {
            if ("drawable".equals(this.mResources.getResourceTypeName(this.mResID))) {
                return new InsetDrawable(new InsetDrawable(this.mResources.getDrawable(this.mResID).mutate(), 0.2f), AdaptiveIconDrawable.getExtraInsetFraction() / ((AdaptiveIconDrawable.getExtraInsetFraction() * 2.0f) + 1.0f));
            }
            return null;
        }
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.launcher3.icons.IconProvider$$ExternalSyntheticLambda0.get():java.lang.Object] */
    public static /* synthetic */ Drawable $r8$lambda$kAHCALh0e24_mhthYcY_3qohKLM(IconProvider iconProvider, ActivityInfo activityInfo, int i) {
        return iconProvider.lambda$getIcon$1(activityInfo, i);
    }

    public IconProvider(Context context) {
        this.mContext = context;
        this.mCalendar = parseComponentOrNull(context, R$string.calendar_component_name);
        this.mClock = parseComponentOrNull(context, R$string.clock_component_name);
    }

    public static int getDay() {
        return Calendar.getInstance().get(5) - 1;
    }

    public static ComponentName parseComponentOrNull(Context context, int i) {
        String string = context.getString(i);
        return TextUtils.isEmpty(string) ? null : ComponentName.unflattenFromString(string);
    }

    public final int getDynamicIconId(Bundle bundle, Resources resources) {
        if (bundle == null) {
            return 0;
        }
        int i = bundle.getInt(this.mCalendar.getPackageName() + ".dynamic_icons", 0);
        if (i == 0) {
            return 0;
        }
        try {
            return resources.obtainTypedArray(i).getResourceId(getDay(), 0);
        } catch (Resources.NotFoundException e) {
            return 0;
        }
    }

    public Drawable getIcon(ActivityInfo activityInfo) {
        return getIcon(activityInfo, this.mContext.getResources().getConfiguration().densityDpi);
    }

    public Drawable getIcon(final ActivityInfo activityInfo, final int i) {
        return getIconWithOverrides(activityInfo.applicationInfo.packageName, i, new Supplier() { // from class: com.android.launcher3.icons.IconProvider$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final Object get() {
                return IconProvider.$r8$lambda$kAHCALh0e24_mhthYcY_3qohKLM(IconProvider.this, activityInfo, i);
            }
        });
    }

    @TargetApi(33)
    public final Drawable getIconWithOverrides(String str, int i, Supplier<Drawable> supplier) {
        ClockDrawableWrapper forPackage;
        ThemeData themeDataForPackage = getThemeDataForPackage(str);
        ComponentName componentName = this.mCalendar;
        if (componentName == null || !componentName.getPackageName().equals(str)) {
            ComponentName componentName2 = this.mClock;
            forPackage = (componentName2 == null || !componentName2.getPackageName().equals(str)) ? null : ClockDrawableWrapper.forPackage(this.mContext, this.mClock.getPackageName(), i, themeDataForPackage);
        } else {
            forPackage = loadCalendarDrawable(i, themeDataForPackage);
        }
        AdaptiveIconDrawable adaptiveIconDrawable = forPackage;
        if (forPackage == null) {
            Drawable drawable = supplier.get();
            adaptiveIconDrawable = drawable;
            if (ATLEAST_T) {
                adaptiveIconDrawable = drawable;
                if (drawable instanceof AdaptiveIconDrawable) {
                    adaptiveIconDrawable = drawable;
                    if (themeDataForPackage != null) {
                        AdaptiveIconDrawable adaptiveIconDrawable2 = (AdaptiveIconDrawable) drawable;
                        adaptiveIconDrawable = drawable;
                        if (adaptiveIconDrawable2.getMonochrome() == null) {
                            adaptiveIconDrawable = new AdaptiveIconDrawable(adaptiveIconDrawable2.getBackground(), adaptiveIconDrawable2.getForeground(), themeDataForPackage.loadPaddedDrawable());
                        }
                    }
                }
            }
        }
        return adaptiveIconDrawable;
    }

    public ThemeData getThemeDataForPackage(String str) {
        return null;
    }

    /* JADX WARN: Removed duplicated region for block: B:27:0x0031  */
    /* renamed from: loadActivityInfoIcon */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Drawable lambda$getIcon$1(ActivityInfo activityInfo, int i) {
        Drawable drawable;
        int iconResource = activityInfo.getIconResource();
        if (i != 0 && iconResource != 0) {
            try {
                drawable = this.mContext.getPackageManager().getResourcesForApplication(activityInfo.applicationInfo).getDrawableForDensity(iconResource, i);
            } catch (PackageManager.NameNotFoundException | Resources.NotFoundException e) {
            }
            Drawable drawable2 = drawable;
            if (drawable == null) {
                drawable2 = activityInfo.loadIcon(this.mContext.getPackageManager());
            }
            return drawable2;
        }
        drawable = null;
        Drawable drawable22 = drawable;
        if (drawable == null) {
        }
        return drawable22;
    }

    @TargetApi(33)
    public final Drawable loadCalendarDrawable(int i, ThemeData themeData) {
        PackageManager packageManager = this.mContext.getPackageManager();
        try {
            Bundle bundle = packageManager.getActivityInfo(this.mCalendar, 8320).metaData;
            Resources resourcesForApplication = packageManager.getResourcesForApplication(this.mCalendar.getPackageName());
            int dynamicIconId = getDynamicIconId(bundle, resourcesForApplication);
            if (dynamicIconId != 0) {
                Drawable drawableForDensity = resourcesForApplication.getDrawableForDensity(dynamicIconId, i, null);
                AdaptiveIconDrawable adaptiveIconDrawable = drawableForDensity;
                if (ATLEAST_T) {
                    adaptiveIconDrawable = drawableForDensity;
                    if (drawableForDensity instanceof AdaptiveIconDrawable) {
                        adaptiveIconDrawable = drawableForDensity;
                        if (themeData != null) {
                            AdaptiveIconDrawable adaptiveIconDrawable2 = (AdaptiveIconDrawable) drawableForDensity;
                            if (adaptiveIconDrawable2.getMonochrome() != null) {
                                return drawableForDensity;
                            }
                            adaptiveIconDrawable = drawableForDensity;
                            if ("array".equals(themeData.mResources.getResourceTypeName(themeData.mResID))) {
                                TypedArray obtainTypedArray = themeData.mResources.obtainTypedArray(themeData.mResID);
                                int resourceId = obtainTypedArray.getResourceId(getDay(), 0);
                                obtainTypedArray.recycle();
                                adaptiveIconDrawable = resourceId == 0 ? drawableForDensity : new AdaptiveIconDrawable(adaptiveIconDrawable2.getBackground(), adaptiveIconDrawable2.getForeground(), new ThemeData(themeData.mResources, resourceId).loadPaddedDrawable());
                            }
                        }
                    }
                }
                return adaptiveIconDrawable;
            }
            return null;
        } catch (PackageManager.NameNotFoundException e) {
            return null;
        }
    }
}