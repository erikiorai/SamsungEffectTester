package com.android.settingslib.fuelgauge;

import android.content.ContentResolver;
import android.content.Context;
import android.provider.Settings;
import java.time.Duration;
import java.time.Instant;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: mainsysui33.jar:com/android/settingslib/fuelgauge/Estimate.class */
public final class Estimate {
    public static final Companion Companion = new Companion(null);
    public final long averageDischargeTime;
    public final long estimateMillis;
    public final boolean isBasedOnUsage;

    /* loaded from: mainsysui33.jar:com/android/settingslib/fuelgauge/Estimate$Companion.class */
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final Estimate getCachedEstimateIfAvailable(Context context) {
            Estimate estimate;
            ContentResolver contentResolver = context.getContentResolver();
            if (Duration.between(getLastCacheUpdateTime(context), Instant.now()).compareTo(Duration.ofMinutes(1L)) > 0) {
                estimate = null;
            } else {
                long j = Settings.Global.getLong(contentResolver, "time_remaining_estimate_millis", -1L);
                boolean z = false;
                if (Settings.Global.getInt(contentResolver, "time_remaining_estimate_based_on_usage", 0) == 1) {
                    z = true;
                }
                estimate = new Estimate(j, z, Settings.Global.getLong(contentResolver, "average_time_to_discharge", -1L));
            }
            return estimate;
        }

        public final Instant getLastCacheUpdateTime(Context context) {
            return Instant.ofEpochMilli(Settings.Global.getLong(context.getContentResolver(), "battery_estimates_last_update_time", -1L));
        }

        public final void storeCachedEstimate(Context context, Estimate estimate) {
            ContentResolver contentResolver = context.getContentResolver();
            Settings.Global.putLong(contentResolver, "time_remaining_estimate_millis", estimate.getEstimateMillis());
            Settings.Global.putInt(contentResolver, "time_remaining_estimate_based_on_usage", estimate.isBasedOnUsage() ? 1 : 0);
            Settings.Global.putLong(contentResolver, "average_time_to_discharge", estimate.getAverageDischargeTime());
            Settings.Global.putLong(contentResolver, "battery_estimates_last_update_time", System.currentTimeMillis());
        }
    }

    public Estimate(long j, boolean z, long j2) {
        this.estimateMillis = j;
        this.isBasedOnUsage = z;
        this.averageDischargeTime = j2;
    }

    public static final Estimate getCachedEstimateIfAvailable(Context context) {
        return Companion.getCachedEstimateIfAvailable(context);
    }

    public static final void storeCachedEstimate(Context context, Estimate estimate) {
        Companion.storeCachedEstimate(context, estimate);
    }

    public final long getAverageDischargeTime() {
        return this.averageDischargeTime;
    }

    public final long getEstimateMillis() {
        return this.estimateMillis;
    }

    public final boolean isBasedOnUsage() {
        return this.isBasedOnUsage;
    }
}