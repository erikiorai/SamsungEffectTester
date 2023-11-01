package com.android.keyguard;

import android.content.res.Resources;
import android.os.Build;
import android.os.PowerManager;
import com.android.systemui.Dumpable;
import com.android.systemui.R$array;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.util.settings.GlobalSettings;
import java.io.PrintWriter;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.text.StringsKt__StringsKt;

/* loaded from: mainsysui33.jar:com/android/keyguard/FaceWakeUpTriggersConfig.class */
public final class FaceWakeUpTriggersConfig implements Dumpable {
    public final Set<Integer> defaultTriggerFaceAuthOnWakeUpFrom;
    public final Set<Integer> triggerFaceAuthOnWakeUpFrom;

    public FaceWakeUpTriggersConfig(Resources resources, GlobalSettings globalSettings, DumpManager dumpManager) {
        Set<Integer> set = ArraysKt___ArraysKt.toSet(resources.getIntArray(R$array.config_face_auth_wake_up_triggers));
        this.defaultTriggerFaceAuthOnWakeUpFrom = set;
        this.triggerFaceAuthOnWakeUpFrom = Build.IS_DEBUGGABLE ? processStringArray(globalSettings.getString("face_wake_triggers"), set) : set;
        dumpManager.registerDumpable(this);
    }

    @Override // com.android.systemui.Dumpable
    public void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println("FaceWakeUpTriggers:");
        for (Integer num : this.triggerFaceAuthOnWakeUpFrom) {
            String wakeReasonToString = PowerManager.wakeReasonToString(num.intValue());
            printWriter.println("    " + wakeReasonToString);
        }
    }

    public final Set<Integer> processStringArray(String str, Set<Integer> set) {
        Set<Integer> set2 = str != null ? (Set) StringsKt__StringsKt.split$default(str, new String[]{"|"}, false, 0, 6, (Object) null).stream().map(new Function() { // from class: com.android.keyguard.FaceWakeUpTriggersConfig$processStringArray$1$1
            /* JADX DEBUG: Method merged with bridge method */
            @Override // java.util.function.Function
            public final Integer apply(String str2) {
                return Integer.valueOf(Integer.parseInt(str2));
            }
        }).collect(Collectors.toSet()) : null;
        if (set2 != null) {
            set = set2;
        }
        return set;
    }

    public final boolean shouldTriggerFaceAuthOnWakeUpFrom(int i) {
        return this.triggerFaceAuthOnWakeUpFrom.contains(Integer.valueOf(i));
    }
}