package com.android.systemui.qs;

import android.content.Context;
import com.android.internal.policy.SystemBarUtils;
import com.android.systemui.util.LargeScreenUtils;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/QSUtils.class */
public final class QSUtils {
    public static final QSUtils INSTANCE = new QSUtils();

    public static final int getQsHeaderSystemIconsAreaHeight(Context context) {
        return LargeScreenUtils.shouldUseLargeScreenShadeHeader(context.getResources()) ? 0 : SystemBarUtils.getQuickQsOffsetHeight(context);
    }
}