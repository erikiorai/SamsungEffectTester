package com.android.settingslib.net;

import android.app.usage.NetworkStatsManager;
import android.content.Context;
import android.net.NetworkPolicyManager;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.util.ArrayUtils;
import java.util.Formatter;
import java.util.Locale;

/* loaded from: mainsysui33.jar:com/android/settingslib/net/DataUsageController.class */
public class DataUsageController {
    public static final boolean DEBUG = Log.isLoggable("DataUsageController", 3);
    public static final StringBuilder PERIOD_BUILDER;
    public static final Formatter PERIOD_FORMATTER;
    public Callback mCallback;
    public final Context mContext;
    public NetworkNameProvider mNetworkController;
    public final NetworkStatsManager mNetworkStatsManager;
    public final NetworkPolicyManager mPolicyManager;
    public int mSubscriptionId = -1;

    /* loaded from: mainsysui33.jar:com/android/settingslib/net/DataUsageController$Callback.class */
    public interface Callback {
        void onMobileDataEnabled(boolean z);
    }

    /* loaded from: mainsysui33.jar:com/android/settingslib/net/DataUsageController$NetworkNameProvider.class */
    public interface NetworkNameProvider {
    }

    static {
        StringBuilder sb = new StringBuilder(50);
        PERIOD_BUILDER = sb;
        PERIOD_FORMATTER = new Formatter(sb, Locale.getDefault());
    }

    public DataUsageController(Context context) {
        this.mContext = context;
        this.mPolicyManager = NetworkPolicyManager.from(context);
        this.mNetworkStatsManager = (NetworkStatsManager) context.getSystemService(NetworkStatsManager.class);
    }

    @VisibleForTesting
    public TelephonyManager getTelephonyManager() {
        int i = this.mSubscriptionId;
        int i2 = i;
        if (!SubscriptionManager.isValidSubscriptionId(i)) {
            i2 = SubscriptionManager.getDefaultDataSubscriptionId();
        }
        int i3 = i2;
        if (!SubscriptionManager.isValidSubscriptionId(i2)) {
            int[] activeSubscriptionIdList = SubscriptionManager.from(this.mContext).getActiveSubscriptionIdList();
            i3 = i2;
            if (!ArrayUtils.isEmpty(activeSubscriptionIdList)) {
                i3 = activeSubscriptionIdList[0];
            }
        }
        return ((TelephonyManager) this.mContext.getSystemService(TelephonyManager.class)).createForSubscriptionId(i3);
    }

    public boolean isMobileDataEnabled() {
        return getTelephonyManager().isDataEnabled();
    }

    public boolean isMobileDataSupported() {
        return getTelephonyManager().isDataCapable() && getTelephonyManager().getSimState() == 5;
    }

    public void setCallback(Callback callback) {
        this.mCallback = callback;
    }

    public void setMobileDataEnabled(boolean z) {
        Log.d("DataUsageController", "setMobileDataEnabled: enabled=" + z);
        getTelephonyManager().setDataEnabled(z);
        Callback callback = this.mCallback;
        if (callback != null) {
            callback.onMobileDataEnabled(z);
        }
    }

    public void setNetworkController(NetworkNameProvider networkNameProvider) {
        this.mNetworkController = networkNameProvider;
    }
}