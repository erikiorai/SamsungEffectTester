package com.android.systemui.media.taptotransfer.common;

import android.content.Context;
import android.content.pm.PackageManager;
import com.android.systemui.R$drawable;
import com.android.systemui.R$string;
import com.android.systemui.common.shared.model.ContentDescription;
import com.android.systemui.media.taptotransfer.common.MediaTttIcon;
import com.android.systemui.temporarydisplay.TemporaryViewInfo;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: mainsysui33.jar:com/android/systemui/media/taptotransfer/common/MediaTttUtils.class */
public final class MediaTttUtils {
    public static final Companion Companion = new Companion(null);

    /* loaded from: mainsysui33.jar:com/android/systemui/media/taptotransfer/common/MediaTttUtils$Companion.class */
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final IconInfo getIconInfoFromPackageName(Context context, String str, MediaTttLogger<? extends TemporaryViewInfo> mediaTttLogger) {
            if (str != null) {
                PackageManager packageManager = context.getPackageManager();
                try {
                    return new IconInfo(new ContentDescription.Loaded(packageManager.getApplicationInfo(str, PackageManager.ApplicationInfoFlags.of(0L)).loadLabel(packageManager).toString()), new MediaTttIcon.Loaded(packageManager.getApplicationIcon(str)), null, true);
                } catch (PackageManager.NameNotFoundException e) {
                    mediaTttLogger.logPackageNotFound(str);
                }
            }
            return new IconInfo(new ContentDescription.Resource(R$string.media_output_dialog_unknown_launch_app_name), new MediaTttIcon.Resource(R$drawable.ic_cast), 16842806, false);
        }
    }
}