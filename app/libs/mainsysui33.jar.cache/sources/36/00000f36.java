package com.android.systemui;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.UserHandle;
import android.view.View;
import com.android.systemui.animation.ActivityLaunchAnimator;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.statusbar.phone.CentralSurfaces;
import dagger.Lazy;
import java.util.Optional;
import java.util.function.Consumer;

/* loaded from: mainsysui33.jar:com/android/systemui/ActivityStarterDelegate.class */
public class ActivityStarterDelegate implements ActivityStarter {
    public Lazy<Optional<CentralSurfaces>> mActualStarterOptionalLazy;

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.ActivityStarterDelegate$$ExternalSyntheticLambda8.accept(java.lang.Object):void] */
    public static /* synthetic */ void $r8$lambda$1jsJbS_cHqED0YGaXMx6RPyODkE(Intent intent, boolean z, ActivityStarter.Callback callback, CentralSurfaces centralSurfaces) {
        centralSurfaces.startActivity(intent, z, callback);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.ActivityStarterDelegate$$ExternalSyntheticLambda11.accept(java.lang.Object):void] */
    /* renamed from: $r8$lambda$3HyvJfFetou0JnIvbo0e-WFwNnM */
    public static /* synthetic */ void m1221$r8$lambda$3HyvJfFetou0JnIvbo0eWFwNnM(PendingIntent pendingIntent, Runnable runnable, View view, CentralSurfaces centralSurfaces) {
        centralSurfaces.startPendingIntentDismissingKeyguard(pendingIntent, runnable, view);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.ActivityStarterDelegate$$ExternalSyntheticLambda4.accept(java.lang.Object):void] */
    /* renamed from: $r8$lambda$9WZo7WIxl2lDbDk1MR8qWEZYN-Q */
    public static /* synthetic */ void m1222$r8$lambda$9WZo7WIxl2lDbDk1MR8qWEZYNQ(PendingIntent pendingIntent, CentralSurfaces centralSurfaces) {
        centralSurfaces.startPendingIntentDismissingKeyguard(pendingIntent);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.ActivityStarterDelegate$$ExternalSyntheticLambda14.accept(java.lang.Object):void] */
    /* renamed from: $r8$lambda$C6jhdGpdfG6ajZ4wH-7BtuTqQk0 */
    public static /* synthetic */ void m1223$r8$lambda$C6jhdGpdfG6ajZ4wH7BtuTqQk0(Intent intent, boolean z, ActivityLaunchAnimator.Controller controller, boolean z2, CentralSurfaces centralSurfaces) {
        centralSurfaces.startActivity(intent, z, controller, z2);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.ActivityStarterDelegate$$ExternalSyntheticLambda1.accept(java.lang.Object):void] */
    /* renamed from: $r8$lambda$FOoK3nLG8e34ZPzxM92Q-8qnxto */
    public static /* synthetic */ void m1224$r8$lambda$FOoK3nLG8e34ZPzxM92Q8qnxto(Intent intent, boolean z, boolean z2, CentralSurfaces centralSurfaces) {
        centralSurfaces.startActivity(intent, z, z2);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.ActivityStarterDelegate$$ExternalSyntheticLambda10.accept(java.lang.Object):void] */
    /* renamed from: $r8$lambda$NNfR2Z3mUl-n3DO7-wAmh54ZEr4 */
    public static /* synthetic */ void m1225$r8$lambda$NNfR2Z3mUln3DO7wAmh54ZEr4(Intent intent, int i, CentralSurfaces centralSurfaces) {
        centralSurfaces.postStartActivityDismissingKeyguard(intent, i);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.ActivityStarterDelegate$$ExternalSyntheticLambda5.accept(java.lang.Object):void] */
    public static /* synthetic */ void $r8$lambda$QItOJMQzdl0XKPu06sxYtTA9lBQ(Intent intent, boolean z, boolean z2, int i, CentralSurfaces centralSurfaces) {
        centralSurfaces.startActivity(intent, z, z2, i);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.ActivityStarterDelegate$$ExternalSyntheticLambda2.accept(java.lang.Object):void] */
    public static /* synthetic */ void $r8$lambda$Y1IScJde2_biBEbejDQVDctAFQA(Intent intent, boolean z, CentralSurfaces centralSurfaces) {
        centralSurfaces.startActivity(intent, z);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.ActivityStarterDelegate$$ExternalSyntheticLambda7.accept(java.lang.Object):void] */
    /* renamed from: $r8$lambda$Z-A3RBB6XSE_R3Lm4UNpriWUJ6E */
    public static /* synthetic */ void m1226$r8$lambda$ZA3RBB6XSE_R3Lm4UNpriWUJ6E(Intent intent, int i, ActivityLaunchAnimator.Controller controller, CentralSurfaces centralSurfaces) {
        centralSurfaces.postStartActivityDismissingKeyguard(intent, i, controller);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.ActivityStarterDelegate$$ExternalSyntheticLambda12.accept(java.lang.Object):void] */
    public static /* synthetic */ void $r8$lambda$de_yhbJlOsWsdyk6_krlDjanqQI(ActivityStarter.OnDismissAction onDismissAction, Runnable runnable, boolean z, CentralSurfaces centralSurfaces) {
        centralSurfaces.dismissKeyguardThenExecute(onDismissAction, runnable, z);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.ActivityStarterDelegate$$ExternalSyntheticLambda13.accept(java.lang.Object):void] */
    public static /* synthetic */ void $r8$lambda$jaysjSgXzoyFWDrlt1di8tN6XqI(PendingIntent pendingIntent, CentralSurfaces centralSurfaces) {
        centralSurfaces.postStartActivityDismissingKeyguard(pendingIntent);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.ActivityStarterDelegate$$ExternalSyntheticLambda6.accept(java.lang.Object):void] */
    public static /* synthetic */ void $r8$lambda$k74iBTgTMiZS1mC1bsoVF_a_Dm0(Runnable runnable, CentralSurfaces centralSurfaces) {
        centralSurfaces.postQSRunnableDismissingKeyguard(runnable);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.ActivityStarterDelegate$$ExternalSyntheticLambda9.accept(java.lang.Object):void] */
    public static /* synthetic */ void $r8$lambda$o5tN4c5qhWYkWCQ4kZIchuZbJcw(PendingIntent pendingIntent, Runnable runnable, CentralSurfaces centralSurfaces) {
        centralSurfaces.startPendingIntentDismissingKeyguard(pendingIntent, runnable);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.ActivityStarterDelegate$$ExternalSyntheticLambda3.accept(java.lang.Object):void] */
    public static /* synthetic */ void $r8$lambda$qd8m_xr7i6MlptFEGRfHTLrLlyY(PendingIntent pendingIntent, ActivityLaunchAnimator.Controller controller, CentralSurfaces centralSurfaces) {
        centralSurfaces.postStartActivityDismissingKeyguard(pendingIntent, controller);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.ActivityStarterDelegate$$ExternalSyntheticLambda0.accept(java.lang.Object):void] */
    public static /* synthetic */ void $r8$lambda$s3up7B4nw9aDWlVtCg0jqu49ORI(PendingIntent pendingIntent, Runnable runnable, ActivityLaunchAnimator.Controller controller, CentralSurfaces centralSurfaces) {
        centralSurfaces.startPendingIntentDismissingKeyguard(pendingIntent, runnable, controller);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.ActivityStarterDelegate$$ExternalSyntheticLambda15.accept(java.lang.Object):void] */
    /* renamed from: $r8$lambda$yNjEB3A3CCnYZOaQouQ-IqqY40s */
    public static /* synthetic */ void m1227$r8$lambda$yNjEB3A3CCnYZOaQouQIqqY40s(Intent intent, boolean z, ActivityLaunchAnimator.Controller controller, boolean z2, UserHandle userHandle, CentralSurfaces centralSurfaces) {
        centralSurfaces.startActivity(intent, z, controller, z2, userHandle);
    }

    public ActivityStarterDelegate(Lazy<Optional<CentralSurfaces>> lazy) {
        this.mActualStarterOptionalLazy = lazy;
    }

    @Override // com.android.systemui.plugins.ActivityStarter
    public void dismissKeyguardThenExecute(final ActivityStarter.OnDismissAction onDismissAction, final Runnable runnable, final boolean z) {
        ((Optional) this.mActualStarterOptionalLazy.get()).ifPresent(new Consumer() { // from class: com.android.systemui.ActivityStarterDelegate$$ExternalSyntheticLambda12
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ActivityStarterDelegate.$r8$lambda$de_yhbJlOsWsdyk6_krlDjanqQI(ActivityStarter.OnDismissAction.this, runnable, z, (CentralSurfaces) obj);
            }
        });
    }

    @Override // com.android.systemui.plugins.ActivityStarter
    public void postQSRunnableDismissingKeyguard(final Runnable runnable) {
        ((Optional) this.mActualStarterOptionalLazy.get()).ifPresent(new Consumer() { // from class: com.android.systemui.ActivityStarterDelegate$$ExternalSyntheticLambda6
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ActivityStarterDelegate.$r8$lambda$k74iBTgTMiZS1mC1bsoVF_a_Dm0(runnable, (CentralSurfaces) obj);
            }
        });
    }

    @Override // com.android.systemui.plugins.ActivityStarter
    public void postStartActivityDismissingKeyguard(final PendingIntent pendingIntent) {
        ((Optional) this.mActualStarterOptionalLazy.get()).ifPresent(new Consumer() { // from class: com.android.systemui.ActivityStarterDelegate$$ExternalSyntheticLambda13
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ActivityStarterDelegate.$r8$lambda$jaysjSgXzoyFWDrlt1di8tN6XqI(pendingIntent, (CentralSurfaces) obj);
            }
        });
    }

    @Override // com.android.systemui.plugins.ActivityStarter
    public void postStartActivityDismissingKeyguard(final PendingIntent pendingIntent, final ActivityLaunchAnimator.Controller controller) {
        ((Optional) this.mActualStarterOptionalLazy.get()).ifPresent(new Consumer() { // from class: com.android.systemui.ActivityStarterDelegate$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ActivityStarterDelegate.$r8$lambda$qd8m_xr7i6MlptFEGRfHTLrLlyY(pendingIntent, controller, (CentralSurfaces) obj);
            }
        });
    }

    @Override // com.android.systemui.plugins.ActivityStarter
    public void postStartActivityDismissingKeyguard(final Intent intent, final int i) {
        ((Optional) this.mActualStarterOptionalLazy.get()).ifPresent(new Consumer() { // from class: com.android.systemui.ActivityStarterDelegate$$ExternalSyntheticLambda10
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ActivityStarterDelegate.m1225$r8$lambda$NNfR2Z3mUln3DO7wAmh54ZEr4(intent, i, (CentralSurfaces) obj);
            }
        });
    }

    @Override // com.android.systemui.plugins.ActivityStarter
    public void postStartActivityDismissingKeyguard(final Intent intent, final int i, final ActivityLaunchAnimator.Controller controller) {
        ((Optional) this.mActualStarterOptionalLazy.get()).ifPresent(new Consumer() { // from class: com.android.systemui.ActivityStarterDelegate$$ExternalSyntheticLambda7
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ActivityStarterDelegate.m1226$r8$lambda$ZA3RBB6XSE_R3Lm4UNpriWUJ6E(intent, i, controller, (CentralSurfaces) obj);
            }
        });
    }

    @Override // com.android.systemui.plugins.ActivityStarter
    public void startActivity(final Intent intent, final boolean z) {
        ((Optional) this.mActualStarterOptionalLazy.get()).ifPresent(new Consumer() { // from class: com.android.systemui.ActivityStarterDelegate$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ActivityStarterDelegate.$r8$lambda$Y1IScJde2_biBEbejDQVDctAFQA(intent, z, (CentralSurfaces) obj);
            }
        });
    }

    @Override // com.android.systemui.plugins.ActivityStarter
    public void startActivity(final Intent intent, final boolean z, final ActivityLaunchAnimator.Controller controller, final boolean z2) {
        ((Optional) this.mActualStarterOptionalLazy.get()).ifPresent(new Consumer() { // from class: com.android.systemui.ActivityStarterDelegate$$ExternalSyntheticLambda14
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ActivityStarterDelegate.m1223$r8$lambda$C6jhdGpdfG6ajZ4wH7BtuTqQk0(intent, z, controller, z2, (CentralSurfaces) obj);
            }
        });
    }

    @Override // com.android.systemui.plugins.ActivityStarter
    public void startActivity(final Intent intent, final boolean z, final ActivityLaunchAnimator.Controller controller, final boolean z2, final UserHandle userHandle) {
        ((Optional) this.mActualStarterOptionalLazy.get()).ifPresent(new Consumer() { // from class: com.android.systemui.ActivityStarterDelegate$$ExternalSyntheticLambda15
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ActivityStarterDelegate.m1227$r8$lambda$yNjEB3A3CCnYZOaQouQIqqY40s(intent, z, controller, z2, userHandle, (CentralSurfaces) obj);
            }
        });
    }

    @Override // com.android.systemui.plugins.ActivityStarter
    public void startActivity(final Intent intent, final boolean z, final ActivityStarter.Callback callback) {
        ((Optional) this.mActualStarterOptionalLazy.get()).ifPresent(new Consumer() { // from class: com.android.systemui.ActivityStarterDelegate$$ExternalSyntheticLambda8
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ActivityStarterDelegate.$r8$lambda$1jsJbS_cHqED0YGaXMx6RPyODkE(intent, z, callback, (CentralSurfaces) obj);
            }
        });
    }

    @Override // com.android.systemui.plugins.ActivityStarter
    public void startActivity(final Intent intent, final boolean z, final boolean z2) {
        ((Optional) this.mActualStarterOptionalLazy.get()).ifPresent(new Consumer() { // from class: com.android.systemui.ActivityStarterDelegate$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ActivityStarterDelegate.m1224$r8$lambda$FOoK3nLG8e34ZPzxM92Q8qnxto(intent, z, z2, (CentralSurfaces) obj);
            }
        });
    }

    @Override // com.android.systemui.plugins.ActivityStarter
    public void startActivity(final Intent intent, final boolean z, final boolean z2, final int i) {
        ((Optional) this.mActualStarterOptionalLazy.get()).ifPresent(new Consumer() { // from class: com.android.systemui.ActivityStarterDelegate$$ExternalSyntheticLambda5
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ActivityStarterDelegate.$r8$lambda$QItOJMQzdl0XKPu06sxYtTA9lBQ(intent, z, z2, i, (CentralSurfaces) obj);
            }
        });
    }

    @Override // com.android.systemui.plugins.ActivityStarter
    public void startPendingIntentDismissingKeyguard(final PendingIntent pendingIntent) {
        ((Optional) this.mActualStarterOptionalLazy.get()).ifPresent(new Consumer() { // from class: com.android.systemui.ActivityStarterDelegate$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ActivityStarterDelegate.m1222$r8$lambda$9WZo7WIxl2lDbDk1MR8qWEZYNQ(pendingIntent, (CentralSurfaces) obj);
            }
        });
    }

    @Override // com.android.systemui.plugins.ActivityStarter
    public void startPendingIntentDismissingKeyguard(final PendingIntent pendingIntent, final Runnable runnable) {
        ((Optional) this.mActualStarterOptionalLazy.get()).ifPresent(new Consumer() { // from class: com.android.systemui.ActivityStarterDelegate$$ExternalSyntheticLambda9
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ActivityStarterDelegate.$r8$lambda$o5tN4c5qhWYkWCQ4kZIchuZbJcw(pendingIntent, runnable, (CentralSurfaces) obj);
            }
        });
    }

    @Override // com.android.systemui.plugins.ActivityStarter
    public void startPendingIntentDismissingKeyguard(final PendingIntent pendingIntent, final Runnable runnable, final View view) {
        ((Optional) this.mActualStarterOptionalLazy.get()).ifPresent(new Consumer() { // from class: com.android.systemui.ActivityStarterDelegate$$ExternalSyntheticLambda11
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ActivityStarterDelegate.m1221$r8$lambda$3HyvJfFetou0JnIvbo0eWFwNnM(pendingIntent, runnable, view, (CentralSurfaces) obj);
            }
        });
    }

    @Override // com.android.systemui.plugins.ActivityStarter
    public void startPendingIntentDismissingKeyguard(final PendingIntent pendingIntent, final Runnable runnable, final ActivityLaunchAnimator.Controller controller) {
        ((Optional) this.mActualStarterOptionalLazy.get()).ifPresent(new Consumer() { // from class: com.android.systemui.ActivityStarterDelegate$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ActivityStarterDelegate.$r8$lambda$s3up7B4nw9aDWlVtCg0jqu49ORI(pendingIntent, runnable, controller, (CentralSurfaces) obj);
            }
        });
    }
}