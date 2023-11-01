package com.android.settingslib.fuelgauge;

import android.content.Context;
import android.content.Intent;
import com.android.settingslib.R$integer;

/* loaded from: mainsysui33.jar:com/android/settingslib/fuelgauge/BatteryStatus.class */
public class BatteryStatus {
    public final int health;
    public final int level;
    public final int maxChargingWattage;
    public final boolean oemFastChargeStatus;
    public final int plugged;
    public final boolean present;
    public final int status;

    public BatteryStatus(int i, int i2, int i3, int i4, int i5, boolean z, boolean z2) {
        this.status = i;
        this.level = i2;
        this.plugged = i3;
        this.health = i4;
        this.maxChargingWattage = i5;
        this.oemFastChargeStatus = z;
        this.present = z2;
    }

    public BatteryStatus(Intent intent) {
        this.status = intent.getIntExtra("status", 1);
        this.plugged = intent.getIntExtra("plugged", 0);
        this.level = intent.getIntExtra("level", 0);
        this.health = intent.getIntExtra("health", 1);
        this.oemFastChargeStatus = intent.getBooleanExtra("oem_fast_charger", false);
        this.present = intent.getBooleanExtra("present", true);
        int intExtra = intent.getIntExtra("max_charging_current", -1);
        int intExtra2 = intent.getIntExtra("max_charging_voltage", -1);
        int i = intExtra2 <= 0 ? 5000000 : intExtra2;
        if (intExtra > 0) {
            this.maxChargingWattage = (intExtra / 1000) * (i / 1000);
        } else {
            this.maxChargingWattage = -1;
        }
    }

    public static boolean isCharged(int i, int i2) {
        return i == 5 || i2 >= 100;
    }

    public final int getChargingSpeed(Context context) {
        int i = 2;
        if (this.oemFastChargeStatus) {
            return 2;
        }
        int integer = context.getResources().getInteger(R$integer.config_chargingSlowlyThreshold);
        int integer2 = context.getResources().getInteger(R$integer.config_chargingFastThreshold);
        int i2 = this.maxChargingWattage;
        if (i2 <= 0) {
            i = -1;
        } else if (i2 < integer) {
            i = 0;
        } else if (i2 <= integer2) {
            i = 1;
        }
        return i;
    }

    public boolean isCharged() {
        return isCharged(this.status, this.level);
    }

    public boolean isOverheated() {
        return this.health == 3;
    }

    public boolean isPluggedIn() {
        int i = this.plugged;
        boolean z = true;
        if (i != 1) {
            z = true;
            if (i != 2) {
                z = true;
                if (i != 4) {
                    z = i == 8;
                }
            }
        }
        return z;
    }

    public boolean isPluggedInDock() {
        return this.plugged == 8;
    }

    public boolean isPluggedInWired() {
        int i = this.plugged;
        boolean z = true;
        if (i != 1) {
            z = i == 2;
        }
        return z;
    }

    public boolean isPluggedInWireless() {
        return this.plugged == 4;
    }

    public String toString() {
        return "BatteryStatus{status=" + this.status + ",level=" + this.level + ",plugged=" + this.plugged + ",health=" + this.health + ",maxChargingWattage=" + this.maxChargingWattage + "}";
    }
}