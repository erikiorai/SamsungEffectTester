package com.android.systemui.people.widget;

import android.os.UserManager;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.notification.collection.notifcollection.CommonNotifCollection;
import com.android.systemui.statusbar.notification.collection.render.NotificationVisibilityProvider;
import com.android.systemui.wmshell.BubblesManager;
import dagger.internal.Factory;
import java.util.Optional;
import java.util.concurrent.Executor;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/people/widget/LaunchConversationActivity_Factory.class */
public final class LaunchConversationActivity_Factory implements Factory<LaunchConversationActivity> {
    public final Provider<Executor> bgExecutorProvider;
    public final Provider<Optional<BubblesManager>> bubblesManagerOptionalProvider;
    public final Provider<CommandQueue> commandQueueProvider;
    public final Provider<CommonNotifCollection> commonNotifCollectionProvider;
    public final Provider<UserManager> userManagerProvider;
    public final Provider<NotificationVisibilityProvider> visibilityProvider;

    public LaunchConversationActivity_Factory(Provider<NotificationVisibilityProvider> provider, Provider<CommonNotifCollection> provider2, Provider<Optional<BubblesManager>> provider3, Provider<UserManager> provider4, Provider<CommandQueue> provider5, Provider<Executor> provider6) {
        this.visibilityProvider = provider;
        this.commonNotifCollectionProvider = provider2;
        this.bubblesManagerOptionalProvider = provider3;
        this.userManagerProvider = provider4;
        this.commandQueueProvider = provider5;
        this.bgExecutorProvider = provider6;
    }

    public static LaunchConversationActivity_Factory create(Provider<NotificationVisibilityProvider> provider, Provider<CommonNotifCollection> provider2, Provider<Optional<BubblesManager>> provider3, Provider<UserManager> provider4, Provider<CommandQueue> provider5, Provider<Executor> provider6) {
        return new LaunchConversationActivity_Factory(provider, provider2, provider3, provider4, provider5, provider6);
    }

    public static LaunchConversationActivity newInstance(NotificationVisibilityProvider notificationVisibilityProvider, CommonNotifCollection commonNotifCollection, Optional<BubblesManager> optional, UserManager userManager, CommandQueue commandQueue, Executor executor) {
        return new LaunchConversationActivity(notificationVisibilityProvider, commonNotifCollection, optional, userManager, commandQueue, executor);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public LaunchConversationActivity m3557get() {
        return newInstance((NotificationVisibilityProvider) this.visibilityProvider.get(), (CommonNotifCollection) this.commonNotifCollectionProvider.get(), (Optional) this.bubblesManagerOptionalProvider.get(), (UserManager) this.userManagerProvider.get(), (CommandQueue) this.commandQueueProvider.get(), (Executor) this.bgExecutorProvider.get());
    }
}