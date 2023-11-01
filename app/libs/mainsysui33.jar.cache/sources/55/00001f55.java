package com.android.systemui.people.widget;

import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.LauncherApps;
import android.content.pm.PackageManager;
import android.os.UserManager;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.statusbar.notification.collection.notifcollection.CommonNotifCollection;
import com.android.wm.shell.bubbles.Bubbles;
import dagger.internal.Factory;
import java.util.Optional;
import java.util.concurrent.Executor;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/people/widget/PeopleSpaceWidgetManager_Factory.class */
public final class PeopleSpaceWidgetManager_Factory implements Factory<PeopleSpaceWidgetManager> {
    public final Provider<Executor> bgExecutorProvider;
    public final Provider<BroadcastDispatcher> broadcastDispatcherProvider;
    public final Provider<Optional<Bubbles>> bubblesOptionalProvider;
    public final Provider<Context> contextProvider;
    public final Provider<LauncherApps> launcherAppsProvider;
    public final Provider<CommonNotifCollection> notifCollectionProvider;
    public final Provider<NotificationManager> notificationManagerProvider;
    public final Provider<PackageManager> packageManagerProvider;
    public final Provider<UserManager> userManagerProvider;

    public PeopleSpaceWidgetManager_Factory(Provider<Context> provider, Provider<LauncherApps> provider2, Provider<CommonNotifCollection> provider3, Provider<PackageManager> provider4, Provider<Optional<Bubbles>> provider5, Provider<UserManager> provider6, Provider<NotificationManager> provider7, Provider<BroadcastDispatcher> provider8, Provider<Executor> provider9) {
        this.contextProvider = provider;
        this.launcherAppsProvider = provider2;
        this.notifCollectionProvider = provider3;
        this.packageManagerProvider = provider4;
        this.bubblesOptionalProvider = provider5;
        this.userManagerProvider = provider6;
        this.notificationManagerProvider = provider7;
        this.broadcastDispatcherProvider = provider8;
        this.bgExecutorProvider = provider9;
    }

    public static PeopleSpaceWidgetManager_Factory create(Provider<Context> provider, Provider<LauncherApps> provider2, Provider<CommonNotifCollection> provider3, Provider<PackageManager> provider4, Provider<Optional<Bubbles>> provider5, Provider<UserManager> provider6, Provider<NotificationManager> provider7, Provider<BroadcastDispatcher> provider8, Provider<Executor> provider9) {
        return new PeopleSpaceWidgetManager_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, provider9);
    }

    public static PeopleSpaceWidgetManager newInstance(Context context, LauncherApps launcherApps, CommonNotifCollection commonNotifCollection, PackageManager packageManager, Optional<Bubbles> optional, UserManager userManager, NotificationManager notificationManager, BroadcastDispatcher broadcastDispatcher, Executor executor) {
        return new PeopleSpaceWidgetManager(context, launcherApps, commonNotifCollection, packageManager, optional, userManager, notificationManager, broadcastDispatcher, executor);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public PeopleSpaceWidgetManager m3571get() {
        return newInstance((Context) this.contextProvider.get(), (LauncherApps) this.launcherAppsProvider.get(), (CommonNotifCollection) this.notifCollectionProvider.get(), (PackageManager) this.packageManagerProvider.get(), (Optional) this.bubblesOptionalProvider.get(), (UserManager) this.userManagerProvider.get(), (NotificationManager) this.notificationManagerProvider.get(), (BroadcastDispatcher) this.broadcastDispatcherProvider.get(), (Executor) this.bgExecutorProvider.get());
    }
}