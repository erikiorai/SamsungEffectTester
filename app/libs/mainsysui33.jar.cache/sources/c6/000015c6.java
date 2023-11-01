package com.android.systemui.dagger;

import android.content.Context;
import android.hardware.SensorPrivacyManager;
import android.os.Handler;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.recents.Recents;
import com.android.systemui.recents.RecentsImplementation;
import com.android.systemui.shade.ShadeExpansionStateManager;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.notification.collection.provider.VisualStabilityProvider;
import com.android.systemui.statusbar.notification.collection.render.GroupMembershipManager;
import com.android.systemui.statusbar.phone.HeadsUpManagerPhone;
import com.android.systemui.statusbar.phone.KeyguardBypassController;
import com.android.systemui.statusbar.policy.AccessibilityManagerWrapper;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.android.systemui.statusbar.policy.DeviceProvisionedControllerImpl;
import com.android.systemui.statusbar.policy.HeadsUpManagerLogger;
import com.android.systemui.statusbar.policy.IndividualSensorPrivacyController;
import com.android.systemui.statusbar.policy.IndividualSensorPrivacyControllerImpl;
import com.android.systemui.statusbar.policy.SensorPrivacyController;
import com.android.systemui.statusbar.policy.SensorPrivacyControllerImpl;

/* loaded from: mainsysui33.jar:com/android/systemui/dagger/ReferenceSystemUIModule.class */
public abstract class ReferenceSystemUIModule {
    public static DeviceProvisionedController bindDeviceProvisionedController(DeviceProvisionedControllerImpl deviceProvisionedControllerImpl) {
        deviceProvisionedControllerImpl.init();
        return deviceProvisionedControllerImpl;
    }

    public static boolean provideAllowNotificationLongPress() {
        return true;
    }

    public static HeadsUpManagerPhone provideHeadsUpManagerPhone(Context context, HeadsUpManagerLogger headsUpManagerLogger, StatusBarStateController statusBarStateController, KeyguardBypassController keyguardBypassController, GroupMembershipManager groupMembershipManager, VisualStabilityProvider visualStabilityProvider, ConfigurationController configurationController, Handler handler, AccessibilityManagerWrapper accessibilityManagerWrapper, UiEventLogger uiEventLogger, ShadeExpansionStateManager shadeExpansionStateManager) {
        return new HeadsUpManagerPhone(context, headsUpManagerLogger, statusBarStateController, keyguardBypassController, groupMembershipManager, visualStabilityProvider, configurationController, handler, accessibilityManagerWrapper, uiEventLogger, shadeExpansionStateManager);
    }

    public static IndividualSensorPrivacyController provideIndividualSensorPrivacyController(SensorPrivacyManager sensorPrivacyManager) {
        IndividualSensorPrivacyControllerImpl individualSensorPrivacyControllerImpl = new IndividualSensorPrivacyControllerImpl(sensorPrivacyManager);
        individualSensorPrivacyControllerImpl.init();
        return individualSensorPrivacyControllerImpl;
    }

    public static String provideLeakReportEmail() {
        return null;
    }

    public static Recents provideRecents(Context context, RecentsImplementation recentsImplementation, CommandQueue commandQueue) {
        return new Recents(context, recentsImplementation, commandQueue);
    }

    public static SensorPrivacyController provideSensorPrivacyController(SensorPrivacyManager sensorPrivacyManager) {
        SensorPrivacyControllerImpl sensorPrivacyControllerImpl = new SensorPrivacyControllerImpl(sensorPrivacyManager);
        sensorPrivacyControllerImpl.init();
        return sensorPrivacyControllerImpl;
    }
}