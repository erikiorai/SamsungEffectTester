package com.android.systemui.assist;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;
import com.android.internal.app.AssistUtils;
import com.android.internal.logging.InstanceId;
import com.android.internal.logging.InstanceIdSequence;
import com.android.internal.logging.UiEventLogger;
import com.android.internal.util.FrameworkStatsLog;
import com.android.systemui.settings.UserTracker;
import java.util.Set;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.SetsKt__SetsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: mainsysui33.jar:com/android/systemui/assist/AssistLogger.class */
public class AssistLogger {
    public static final Companion Companion = new Companion(null);
    public static final Set<AssistantSessionEvent> SESSION_END_EVENTS = SetsKt__SetsKt.setOf(new AssistantSessionEvent[]{AssistantSessionEvent.ASSISTANT_SESSION_INVOCATION_CANCELLED, AssistantSessionEvent.ASSISTANT_SESSION_CLOSE});
    public final AssistUtils assistUtils;
    public final Context context;
    public InstanceId currentInstanceId;
    public final InstanceIdSequence instanceIdSequence = new InstanceIdSequence(1048576);
    public final PhoneStateMonitor phoneStateMonitor;
    public final UiEventLogger uiEventLogger;
    public final UserTracker userTracker;

    /* loaded from: mainsysui33.jar:com/android/systemui/assist/AssistLogger$Companion.class */
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public AssistLogger(Context context, UiEventLogger uiEventLogger, AssistUtils assistUtils, PhoneStateMonitor phoneStateMonitor, UserTracker userTracker) {
        this.context = context;
        this.uiEventLogger = uiEventLogger;
        this.assistUtils = assistUtils;
        this.phoneStateMonitor = phoneStateMonitor;
        this.userTracker = userTracker;
    }

    public final void clearInstanceId() {
        this.currentInstanceId = null;
    }

    public final ComponentName getAssistantComponentForCurrentUser() {
        return this.assistUtils.getAssistComponentForUser(this.userTracker.getUserId());
    }

    public final int getAssistantUid(ComponentName componentName) {
        int i;
        if (componentName == null) {
            return 0;
        }
        try {
            i = this.context.getPackageManager().getApplicationInfo(componentName.getPackageName(), 0).uid;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("AssistLogger", "Unable to find Assistant UID", e);
            i = 0;
        }
        return i;
    }

    public final InstanceId getOrCreateInstanceId() {
        InstanceId instanceId = this.currentInstanceId;
        InstanceId instanceId2 = instanceId;
        if (instanceId == null) {
            instanceId2 = this.instanceIdSequence.newInstanceId();
        }
        this.currentInstanceId = instanceId2;
        return instanceId2;
    }

    public final void reportAssistantInvocationEvent(UiEventLogger.UiEventEnum uiEventEnum, ComponentName componentName, Integer num) {
        ComponentName componentName2 = componentName;
        if (componentName == null) {
            componentName2 = getAssistantComponentForCurrentUser();
        }
        int assistantUid = getAssistantUid(componentName2);
        int intValue = num != null ? num.intValue() : AssistantInvocationEvent.Companion.deviceStateFromLegacyDeviceState(this.phoneStateMonitor.getPhoneState());
        int id = uiEventEnum.getId();
        String flattenToString = componentName2 != null ? componentName2.flattenToString() : null;
        String str = flattenToString;
        if (flattenToString == null) {
            str = "";
        }
        FrameworkStatsLog.write(281, id, assistantUid, str, getOrCreateInstanceId().getId(), intValue, false);
        reportAssistantInvocationExtraData();
    }

    public final void reportAssistantInvocationEventFromLegacy(int i, boolean z, ComponentName componentName, Integer num) {
        reportAssistantInvocationEvent(AssistantInvocationEvent.Companion.eventFromLegacyInvocationType(i, z), componentName, num == null ? null : Integer.valueOf(AssistantInvocationEvent.Companion.deviceStateFromLegacyDeviceState(num.intValue())));
    }

    public void reportAssistantInvocationExtraData() {
    }

    public final void reportAssistantSessionEvent(UiEventLogger.UiEventEnum uiEventEnum) {
        ComponentName assistantComponentForCurrentUser = getAssistantComponentForCurrentUser();
        this.uiEventLogger.logWithInstanceId(uiEventEnum, getAssistantUid(assistantComponentForCurrentUser), assistantComponentForCurrentUser != null ? assistantComponentForCurrentUser.flattenToString() : null, getOrCreateInstanceId());
        if (CollectionsKt___CollectionsKt.contains(SESSION_END_EVENTS, uiEventEnum)) {
            clearInstanceId();
        }
    }
}