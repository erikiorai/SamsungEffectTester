package com.android.settingslib.notification;

import android.content.Context;
import android.content.pm.LauncherApps;
import android.content.pm.PackageManager;
import android.content.pm.ShortcutInfo;
import android.graphics.drawable.Drawable;
import android.util.IconDrawableFactory;
import androidx.recyclerview.widget.RecyclerView;
import com.android.launcher3.icons.BaseIconFactory;
import com.android.settingslib.R$color;
import com.android.settingslib.Utils;

/* loaded from: mainsysui33.jar:com/android/settingslib/notification/ConversationIconFactory.class */
public class ConversationIconFactory extends BaseIconFactory {
    public static final float CIRCLE_RADIUS;
    public static final float INN_CIRCLE_DIA;
    public static final float INN_CIRCLE_RAD;
    public static final float RING_STROKE_WIDTH;
    public final IconDrawableFactory mIconDrawableFactory;
    public int mImportantConversationColor;
    public final LauncherApps mLauncherApps;
    public final PackageManager mPackageManager;

    static {
        float sqrt = (float) Math.sqrt(288.0d);
        INN_CIRCLE_DIA = sqrt;
        float f = sqrt / 2.0f;
        INN_CIRCLE_RAD = f;
        CIRCLE_RADIUS = f + ((10.0f - f) / 2.0f);
        RING_STROKE_WIDTH = (20.0f - sqrt) / 2.0f;
    }

    public ConversationIconFactory(Context context, LauncherApps launcherApps, PackageManager packageManager, IconDrawableFactory iconDrawableFactory, int i) {
        super(context, context.getResources().getConfiguration().densityDpi, i);
        this.mLauncherApps = launcherApps;
        this.mPackageManager = packageManager;
        this.mIconDrawableFactory = iconDrawableFactory;
        this.mImportantConversationColor = context.getResources().getColor(R$color.important_conversation, null);
    }

    public Drawable getAppBadge(String str, int i) {
        Drawable defaultActivityIcon;
        try {
            defaultActivityIcon = Utils.getBadgedIcon(this.mContext, this.mPackageManager.getApplicationInfoAsUser(str, RecyclerView.ViewHolder.FLAG_IGNORE, i));
        } catch (PackageManager.NameNotFoundException e) {
            defaultActivityIcon = this.mPackageManager.getDefaultActivityIcon();
        }
        return defaultActivityIcon;
    }

    public Drawable getBaseIconDrawable(ShortcutInfo shortcutInfo) {
        return this.mLauncherApps.getShortcutIconDrawable(shortcutInfo, this.mFillResIconDpi);
    }
}