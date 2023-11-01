package com.android.systemui.notetask;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.text.StringsKt__StringsJVMKt;

/* loaded from: mainsysui33.jar:com/android/systemui/notetask/NoteTaskIntentResolver.class */
public final class NoteTaskIntentResolver {
    public static final Companion Companion = new Companion(null);
    public final PackageManager packageManager;

    /* loaded from: mainsysui33.jar:com/android/systemui/notetask/NoteTaskIntentResolver$Companion.class */
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public NoteTaskIntentResolver(PackageManager packageManager) {
        this.packageManager = packageManager;
    }

    public final String resolveActivityNameForNotesAction(String str) {
        boolean showWhenLocked;
        boolean turnScreenOn;
        ResolveInfo resolveActivity = this.packageManager.resolveActivity(new Intent("android.intent.action.NOTES").setPackage(str), PackageManager.ResolveInfoFlags.of(65536L));
        ActivityInfo activityInfo = resolveActivity != null ? resolveActivity.activityInfo : null;
        if (activityInfo == null) {
            return null;
        }
        String str2 = activityInfo.name;
        if (!(str2 == null || StringsKt__StringsJVMKt.isBlank(str2)) && activityInfo.exported && activityInfo.enabled) {
            showWhenLocked = NoteTaskIntentResolverKt.getShowWhenLocked(activityInfo);
            if (showWhenLocked) {
                turnScreenOn = NoteTaskIntentResolverKt.getTurnScreenOn(activityInfo);
                if (turnScreenOn) {
                    return activityInfo.name;
                }
                return null;
            }
            return null;
        }
        return null;
    }

    public final Intent resolveIntent() {
        String resolveActivityNameForNotesAction;
        for (ResolveInfo resolveInfo : this.packageManager.queryIntentActivities(new Intent("android.intent.action.NOTES"), PackageManager.ResolveInfoFlags.of(65536L))) {
            String str = resolveInfo.serviceInfo.applicationInfo.packageName;
            if (str != null && (resolveActivityNameForNotesAction = resolveActivityNameForNotesAction(str)) != null) {
                return new Intent("android.intent.action.NOTES").setComponent(new ComponentName(str, resolveActivityNameForNotesAction)).addFlags(268435456);
            }
        }
        return null;
    }
}