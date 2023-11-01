package com.android.systemui.mediaprojection.appselector;

import android.content.ComponentName;
import android.content.Context;
import com.android.launcher3.icons.IconFactory;
import com.android.systemui.media.MediaProjectionAppSelectorActivity;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.SupervisorKt;

/* loaded from: mainsysui33.jar:com/android/systemui/mediaprojection/appselector/MediaProjectionAppSelectorModule.class */
public interface MediaProjectionAppSelectorModule {
    public static final Companion Companion = Companion.$$INSTANCE;

    /* loaded from: mainsysui33.jar:com/android/systemui/mediaprojection/appselector/MediaProjectionAppSelectorModule$Companion.class */
    public static final class Companion {
        public static final /* synthetic */ Companion $$INSTANCE = new Companion();

        public final IconFactory bindIconFactory(Context context) {
            return IconFactory.obtain(context);
        }

        public final ComponentName provideAppSelectorComponentName(Context context) {
            return new ComponentName(context, MediaProjectionAppSelectorActivity.class);
        }

        public final CoroutineScope provideCoroutineScope(CoroutineScope coroutineScope) {
            return CoroutineScopeKt.CoroutineScope(coroutineScope.getCoroutineContext().plus(SupervisorKt.SupervisorJob$default((Job) null, 1, (Object) null)));
        }
    }
}