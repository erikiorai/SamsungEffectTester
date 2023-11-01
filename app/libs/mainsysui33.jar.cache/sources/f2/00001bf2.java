package com.android.systemui.media.controls.models.recommendation;

import android.app.smartspace.SmartspaceAction;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import androidx.recyclerview.widget.RecyclerView;
import com.android.internal.logging.InstanceId;
import java.util.ArrayList;
import java.util.List;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: mainsysui33.jar:com/android/systemui/media/controls/models/recommendation/SmartspaceMediaData.class */
public final class SmartspaceMediaData {
    public final SmartspaceAction cardAction;
    public final Intent dismissIntent;
    public final long headphoneConnectionTimeMillis;
    public final InstanceId instanceId;
    public final boolean isActive;
    public final String packageName;
    public final List<SmartspaceAction> recommendations;
    public final String targetId;

    public SmartspaceMediaData(String str, boolean z, String str2, SmartspaceAction smartspaceAction, List<SmartspaceAction> list, Intent intent, long j, InstanceId instanceId) {
        this.targetId = str;
        this.isActive = z;
        this.packageName = str2;
        this.cardAction = smartspaceAction;
        this.recommendations = list;
        this.dismissIntent = intent;
        this.headphoneConnectionTimeMillis = j;
        this.instanceId = instanceId;
    }

    public static /* synthetic */ SmartspaceMediaData copy$default(SmartspaceMediaData smartspaceMediaData, String str, boolean z, String str2, SmartspaceAction smartspaceAction, List list, Intent intent, long j, InstanceId instanceId, int i, Object obj) {
        if ((i & 1) != 0) {
            str = smartspaceMediaData.targetId;
        }
        if ((i & 2) != 0) {
            z = smartspaceMediaData.isActive;
        }
        if ((i & 4) != 0) {
            str2 = smartspaceMediaData.packageName;
        }
        if ((i & 8) != 0) {
            smartspaceAction = smartspaceMediaData.cardAction;
        }
        if ((i & 16) != 0) {
            list = smartspaceMediaData.recommendations;
        }
        if ((i & 32) != 0) {
            intent = smartspaceMediaData.dismissIntent;
        }
        if ((i & 64) != 0) {
            j = smartspaceMediaData.headphoneConnectionTimeMillis;
        }
        if ((i & RecyclerView.ViewHolder.FLAG_IGNORE) != 0) {
            instanceId = smartspaceMediaData.instanceId;
        }
        return smartspaceMediaData.copy(str, z, str2, smartspaceAction, list, intent, j, instanceId);
    }

    public final SmartspaceMediaData copy(String str, boolean z, String str2, SmartspaceAction smartspaceAction, List<SmartspaceAction> list, Intent intent, long j, InstanceId instanceId) {
        return new SmartspaceMediaData(str, z, str2, smartspaceAction, list, intent, j, instanceId);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof SmartspaceMediaData) {
            SmartspaceMediaData smartspaceMediaData = (SmartspaceMediaData) obj;
            return Intrinsics.areEqual(this.targetId, smartspaceMediaData.targetId) && this.isActive == smartspaceMediaData.isActive && Intrinsics.areEqual(this.packageName, smartspaceMediaData.packageName) && Intrinsics.areEqual(this.cardAction, smartspaceMediaData.cardAction) && Intrinsics.areEqual(this.recommendations, smartspaceMediaData.recommendations) && Intrinsics.areEqual(this.dismissIntent, smartspaceMediaData.dismissIntent) && this.headphoneConnectionTimeMillis == smartspaceMediaData.headphoneConnectionTimeMillis && Intrinsics.areEqual(this.instanceId, smartspaceMediaData.instanceId);
        }
        return false;
    }

    public final CharSequence getAppName(Context context) {
        String str;
        CharSequence charSequence;
        Intent intent;
        Bundle extras;
        SmartspaceAction smartspaceAction = this.cardAction;
        String string = (smartspaceAction == null || (intent = smartspaceAction.getIntent()) == null || (extras = intent.getExtras()) == null) ? null : extras.getString("KEY_SMARTSPACE_APP_NAME");
        if (TextUtils.isEmpty(string)) {
            PackageManager packageManager = context.getPackageManager();
            Intent launchIntentForPackage = packageManager.getLaunchIntentForPackage(this.packageName);
            if (launchIntentForPackage != null) {
                return launchIntentForPackage.resolveActivityInfo(packageManager, 0).loadLabel(packageManager);
            }
            str = SmartspaceMediaDataKt.TAG;
            Log.w(str, "Package " + this.packageName + " does not have a main launcher activity. Fallback to full app name");
            try {
                charSequence = packageManager.getApplicationLabel(packageManager.getApplicationInfo(this.packageName, 0));
            } catch (PackageManager.NameNotFoundException e) {
                charSequence = null;
            }
            return charSequence;
        }
        return string;
    }

    public final SmartspaceAction getCardAction() {
        return this.cardAction;
    }

    public final Intent getDismissIntent() {
        return this.dismissIntent;
    }

    public final long getHeadphoneConnectionTimeMillis() {
        return this.headphoneConnectionTimeMillis;
    }

    public final InstanceId getInstanceId() {
        return this.instanceId;
    }

    public final String getPackageName() {
        return this.packageName;
    }

    public final String getTargetId() {
        return this.targetId;
    }

    public final List<SmartspaceAction> getValidRecommendations() {
        List<SmartspaceAction> list = this.recommendations;
        ArrayList arrayList = new ArrayList();
        for (Object obj : list) {
            if (((SmartspaceAction) obj).getIcon() != null) {
                arrayList.add(obj);
            }
        }
        return arrayList;
    }

    public int hashCode() {
        int hashCode = this.targetId.hashCode();
        boolean z = this.isActive;
        int i = z ? 1 : 0;
        if (z) {
            i = 1;
        }
        int hashCode2 = this.packageName.hashCode();
        SmartspaceAction smartspaceAction = this.cardAction;
        int i2 = 0;
        int hashCode3 = smartspaceAction == null ? 0 : smartspaceAction.hashCode();
        int hashCode4 = this.recommendations.hashCode();
        Intent intent = this.dismissIntent;
        if (intent != null) {
            i2 = intent.hashCode();
        }
        return (((((((((((((hashCode * 31) + i) * 31) + hashCode2) * 31) + hashCode3) * 31) + hashCode4) * 31) + i2) * 31) + Long.hashCode(this.headphoneConnectionTimeMillis)) * 31) + this.instanceId.hashCode();
    }

    public final boolean isActive() {
        return this.isActive;
    }

    public final boolean isValid() {
        return getValidRecommendations().size() >= 3;
    }

    public String toString() {
        String str = this.targetId;
        boolean z = this.isActive;
        String str2 = this.packageName;
        SmartspaceAction smartspaceAction = this.cardAction;
        List<SmartspaceAction> list = this.recommendations;
        Intent intent = this.dismissIntent;
        long j = this.headphoneConnectionTimeMillis;
        InstanceId instanceId = this.instanceId;
        return "SmartspaceMediaData(targetId=" + str + ", isActive=" + z + ", packageName=" + str2 + ", cardAction=" + smartspaceAction + ", recommendations=" + list + ", dismissIntent=" + intent + ", headphoneConnectionTimeMillis=" + j + ", instanceId=" + instanceId + ")";
    }
}