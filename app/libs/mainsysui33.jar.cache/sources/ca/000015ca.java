package com.android.systemui.dagger;

import android.content.Context;
import android.os.Handler;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.shade.ShadeExpansionStateManager;
import com.android.systemui.statusbar.notification.collection.provider.VisualStabilityProvider;
import com.android.systemui.statusbar.notification.collection.render.GroupMembershipManager;
import com.android.systemui.statusbar.phone.HeadsUpManagerPhone;
import com.android.systemui.statusbar.phone.KeyguardBypassController;
import com.android.systemui.statusbar.policy.AccessibilityManagerWrapper;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.HeadsUpManagerLogger;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/dagger/ReferenceSystemUIModule_ProvideHeadsUpManagerPhoneFactory.class */
public final class ReferenceSystemUIModule_ProvideHeadsUpManagerPhoneFactory implements Factory<HeadsUpManagerPhone> {
    public final Provider<AccessibilityManagerWrapper> accessibilityManagerWrapperProvider;
    public final Provider<KeyguardBypassController> bypassControllerProvider;
    public final Provider<ConfigurationController> configurationControllerProvider;
    public final Provider<Context> contextProvider;
    public final Provider<GroupMembershipManager> groupManagerProvider;
    public final Provider<Handler> handlerProvider;
    public final Provider<HeadsUpManagerLogger> headsUpManagerLoggerProvider;
    public final Provider<ShadeExpansionStateManager> shadeExpansionStateManagerProvider;
    public final Provider<StatusBarStateController> statusBarStateControllerProvider;
    public final Provider<UiEventLogger> uiEventLoggerProvider;
    public final Provider<VisualStabilityProvider> visualStabilityProvider;

    public ReferenceSystemUIModule_ProvideHeadsUpManagerPhoneFactory(Provider<Context> provider, Provider<HeadsUpManagerLogger> provider2, Provider<StatusBarStateController> provider3, Provider<KeyguardBypassController> provider4, Provider<GroupMembershipManager> provider5, Provider<VisualStabilityProvider> provider6, Provider<ConfigurationController> provider7, Provider<Handler> provider8, Provider<AccessibilityManagerWrapper> provider9, Provider<UiEventLogger> provider10, Provider<ShadeExpansionStateManager> provider11) {
        this.contextProvider = provider;
        this.headsUpManagerLoggerProvider = provider2;
        this.statusBarStateControllerProvider = provider3;
        this.bypassControllerProvider = provider4;
        this.groupManagerProvider = provider5;
        this.visualStabilityProvider = provider6;
        this.configurationControllerProvider = provider7;
        this.handlerProvider = provider8;
        this.accessibilityManagerWrapperProvider = provider9;
        this.uiEventLoggerProvider = provider10;
        this.shadeExpansionStateManagerProvider = provider11;
    }

    public static ReferenceSystemUIModule_ProvideHeadsUpManagerPhoneFactory create(Provider<Context> provider, Provider<HeadsUpManagerLogger> provider2, Provider<StatusBarStateController> provider3, Provider<KeyguardBypassController> provider4, Provider<GroupMembershipManager> provider5, Provider<VisualStabilityProvider> provider6, Provider<ConfigurationController> provider7, Provider<Handler> provider8, Provider<AccessibilityManagerWrapper> provider9, Provider<UiEventLogger> provider10, Provider<ShadeExpansionStateManager> provider11) {
        return new ReferenceSystemUIModule_ProvideHeadsUpManagerPhoneFactory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, provider9, provider10, provider11);
    }

    public static HeadsUpManagerPhone provideHeadsUpManagerPhone(Context context, HeadsUpManagerLogger headsUpManagerLogger, StatusBarStateController statusBarStateController, KeyguardBypassController keyguardBypassController, GroupMembershipManager groupMembershipManager, VisualStabilityProvider visualStabilityProvider, ConfigurationController configurationController, Handler handler, AccessibilityManagerWrapper accessibilityManagerWrapper, UiEventLogger uiEventLogger, ShadeExpansionStateManager shadeExpansionStateManager) {
        return (HeadsUpManagerPhone) Preconditions.checkNotNullFromProvides(ReferenceSystemUIModule.provideHeadsUpManagerPhone(context, headsUpManagerLogger, statusBarStateController, keyguardBypassController, groupMembershipManager, visualStabilityProvider, configurationController, handler, accessibilityManagerWrapper, uiEventLogger, shadeExpansionStateManager));
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public HeadsUpManagerPhone m2378get() {
        return provideHeadsUpManagerPhone((Context) this.contextProvider.get(), (HeadsUpManagerLogger) this.headsUpManagerLoggerProvider.get(), (StatusBarStateController) this.statusBarStateControllerProvider.get(), (KeyguardBypassController) this.bypassControllerProvider.get(), (GroupMembershipManager) this.groupManagerProvider.get(), (VisualStabilityProvider) this.visualStabilityProvider.get(), (ConfigurationController) this.configurationControllerProvider.get(), (Handler) this.handlerProvider.get(), (AccessibilityManagerWrapper) this.accessibilityManagerWrapperProvider.get(), (UiEventLogger) this.uiEventLoggerProvider.get(), (ShadeExpansionStateManager) this.shadeExpansionStateManagerProvider.get());
    }
}