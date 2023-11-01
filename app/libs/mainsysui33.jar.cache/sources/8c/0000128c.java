package com.android.systemui.camera;

import android.app.ActivityManager;
import android.app.IActivityTaskManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import com.android.systemui.ActivityIntentHelper;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.statusbar.phone.CentralSurfaces;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import dagger.internal.Factory;
import java.util.concurrent.Executor;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/camera/CameraGestureHelper_Factory.class */
public final class CameraGestureHelper_Factory implements Factory<CameraGestureHelper> {
    public final Provider<ActivityIntentHelper> activityIntentHelperProvider;
    public final Provider<ActivityManager> activityManagerProvider;
    public final Provider<ActivityStarter> activityStarterProvider;
    public final Provider<IActivityTaskManager> activityTaskManagerProvider;
    public final Provider<CameraIntentsWrapper> cameraIntentsProvider;
    public final Provider<CentralSurfaces> centralSurfacesProvider;
    public final Provider<ContentResolver> contentResolverProvider;
    public final Provider<Context> contextProvider;
    public final Provider<KeyguardStateController> keyguardStateControllerProvider;
    public final Provider<PackageManager> packageManagerProvider;
    public final Provider<Executor> uiExecutorProvider;

    public CameraGestureHelper_Factory(Provider<Context> provider, Provider<CentralSurfaces> provider2, Provider<KeyguardStateController> provider3, Provider<PackageManager> provider4, Provider<ActivityManager> provider5, Provider<ActivityStarter> provider6, Provider<ActivityIntentHelper> provider7, Provider<IActivityTaskManager> provider8, Provider<CameraIntentsWrapper> provider9, Provider<ContentResolver> provider10, Provider<Executor> provider11) {
        this.contextProvider = provider;
        this.centralSurfacesProvider = provider2;
        this.keyguardStateControllerProvider = provider3;
        this.packageManagerProvider = provider4;
        this.activityManagerProvider = provider5;
        this.activityStarterProvider = provider6;
        this.activityIntentHelperProvider = provider7;
        this.activityTaskManagerProvider = provider8;
        this.cameraIntentsProvider = provider9;
        this.contentResolverProvider = provider10;
        this.uiExecutorProvider = provider11;
    }

    public static CameraGestureHelper_Factory create(Provider<Context> provider, Provider<CentralSurfaces> provider2, Provider<KeyguardStateController> provider3, Provider<PackageManager> provider4, Provider<ActivityManager> provider5, Provider<ActivityStarter> provider6, Provider<ActivityIntentHelper> provider7, Provider<IActivityTaskManager> provider8, Provider<CameraIntentsWrapper> provider9, Provider<ContentResolver> provider10, Provider<Executor> provider11) {
        return new CameraGestureHelper_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, provider9, provider10, provider11);
    }

    public static CameraGestureHelper newInstance(Context context, CentralSurfaces centralSurfaces, KeyguardStateController keyguardStateController, PackageManager packageManager, ActivityManager activityManager, ActivityStarter activityStarter, ActivityIntentHelper activityIntentHelper, IActivityTaskManager iActivityTaskManager, CameraIntentsWrapper cameraIntentsWrapper, ContentResolver contentResolver, Executor executor) {
        return new CameraGestureHelper(context, centralSurfaces, keyguardStateController, packageManager, activityManager, activityStarter, activityIntentHelper, iActivityTaskManager, cameraIntentsWrapper, contentResolver, executor);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public CameraGestureHelper m1654get() {
        return newInstance((Context) this.contextProvider.get(), (CentralSurfaces) this.centralSurfacesProvider.get(), (KeyguardStateController) this.keyguardStateControllerProvider.get(), (PackageManager) this.packageManagerProvider.get(), (ActivityManager) this.activityManagerProvider.get(), (ActivityStarter) this.activityStarterProvider.get(), (ActivityIntentHelper) this.activityIntentHelperProvider.get(), (IActivityTaskManager) this.activityTaskManagerProvider.get(), (CameraIntentsWrapper) this.cameraIntentsProvider.get(), (ContentResolver) this.contentResolverProvider.get(), (Executor) this.uiExecutorProvider.get());
    }
}