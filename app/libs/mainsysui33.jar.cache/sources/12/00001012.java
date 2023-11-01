package com.android.systemui;

import android.app.Application;
import android.content.Context;
import com.android.systemui.dagger.DaggerReferenceGlobalRootComponent;
import com.android.systemui.dagger.GlobalRootComponent;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: mainsysui33.jar:com/android/systemui/SystemUIInitializerImpl.class */
public final class SystemUIInitializerImpl extends SystemUIInitializer {
    public static final Companion Companion = new Companion(null);
    private static final String SCREENSHOT_CROSS_PROFILE_PROCESS = "com.android.systemui:screenshot_cross_profile";
    private static final String SYSTEMUI_PROCESS = "com.android.systemui";

    /* loaded from: mainsysui33.jar:com/android/systemui/SystemUIInitializerImpl$Companion.class */
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public SystemUIInitializerImpl(Context context) {
        super(context);
    }

    @Override // com.android.systemui.SystemUIInitializer
    public GlobalRootComponent.Builder getGlobalRootComponentBuilder() {
        return Intrinsics.areEqual(Application.getProcessName(), SCREENSHOT_CROSS_PROFILE_PROCESS) ? null : DaggerReferenceGlobalRootComponent.builder();
    }
}