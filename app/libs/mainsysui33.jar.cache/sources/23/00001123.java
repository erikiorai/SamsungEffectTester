package com.android.systemui.assist;

import android.provider.DeviceConfig;
import com.android.systemui.DejankUtils;
import java.util.function.Supplier;

/* loaded from: mainsysui33.jar:com/android/systemui/assist/DeviceConfigHelper.class */
public class DeviceConfigHelper {
    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.assist.DeviceConfigHelper$$ExternalSyntheticLambda0.get():java.lang.Object] */
    public static /* synthetic */ Long $r8$lambda$TrTHK9UGFwR20J2G2zxOQhBIBt0(String str, long j) {
        return lambda$getLong$0(str, j);
    }

    public static /* synthetic */ Long lambda$getLong$0(String str, long j) {
        return Long.valueOf(DeviceConfig.getLong("systemui", str, j));
    }

    public long getLong(final String str, final long j) {
        return ((Long) DejankUtils.whitelistIpcs(new Supplier() { // from class: com.android.systemui.assist.DeviceConfigHelper$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final Object get() {
                return DeviceConfigHelper.$r8$lambda$TrTHK9UGFwR20J2G2zxOQhBIBt0(str, j);
            }
        })).longValue();
    }
}