package com.android.systemui.qs.footer.data.repository;

import android.content.Context;
import android.os.Handler;
import android.os.UserManager;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.statusbar.policy.UserInfoController;
import com.android.systemui.statusbar.policy.UserSwitcherController;
import com.android.systemui.util.settings.GlobalSettings;
import dagger.internal.Factory;
import javax.inject.Provider;
import kotlinx.coroutines.CoroutineDispatcher;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/footer/data/repository/UserSwitcherRepositoryImpl_Factory.class */
public final class UserSwitcherRepositoryImpl_Factory implements Factory<UserSwitcherRepositoryImpl> {
    public final Provider<CoroutineDispatcher> bgDispatcherProvider;
    public final Provider<Handler> bgHandlerProvider;
    public final Provider<Context> contextProvider;
    public final Provider<GlobalSettings> globalSettingProvider;
    public final Provider<UserInfoController> userInfoControllerProvider;
    public final Provider<UserManager> userManagerProvider;
    public final Provider<UserSwitcherController> userSwitcherControllerProvider;
    public final Provider<UserTracker> userTrackerProvider;

    public UserSwitcherRepositoryImpl_Factory(Provider<Context> provider, Provider<Handler> provider2, Provider<CoroutineDispatcher> provider3, Provider<UserManager> provider4, Provider<UserTracker> provider5, Provider<UserSwitcherController> provider6, Provider<UserInfoController> provider7, Provider<GlobalSettings> provider8) {
        this.contextProvider = provider;
        this.bgHandlerProvider = provider2;
        this.bgDispatcherProvider = provider3;
        this.userManagerProvider = provider4;
        this.userTrackerProvider = provider5;
        this.userSwitcherControllerProvider = provider6;
        this.userInfoControllerProvider = provider7;
        this.globalSettingProvider = provider8;
    }

    public static UserSwitcherRepositoryImpl_Factory create(Provider<Context> provider, Provider<Handler> provider2, Provider<CoroutineDispatcher> provider3, Provider<UserManager> provider4, Provider<UserTracker> provider5, Provider<UserSwitcherController> provider6, Provider<UserInfoController> provider7, Provider<GlobalSettings> provider8) {
        return new UserSwitcherRepositoryImpl_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8);
    }

    public static UserSwitcherRepositoryImpl newInstance(Context context, Handler handler, CoroutineDispatcher coroutineDispatcher, UserManager userManager, UserTracker userTracker, UserSwitcherController userSwitcherController, UserInfoController userInfoController, GlobalSettings globalSettings) {
        return new UserSwitcherRepositoryImpl(context, handler, coroutineDispatcher, userManager, userTracker, userSwitcherController, userInfoController, globalSettings);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public UserSwitcherRepositoryImpl m3928get() {
        return newInstance((Context) this.contextProvider.get(), (Handler) this.bgHandlerProvider.get(), (CoroutineDispatcher) this.bgDispatcherProvider.get(), (UserManager) this.userManagerProvider.get(), (UserTracker) this.userTrackerProvider.get(), (UserSwitcherController) this.userSwitcherControllerProvider.get(), (UserInfoController) this.userInfoControllerProvider.get(), (GlobalSettings) this.globalSettingProvider.get());
    }
}