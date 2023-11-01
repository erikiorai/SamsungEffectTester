package com.android.systemui.screenshot;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import com.android.systemui.screenshot.ICrossProfileService;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: mainsysui33.jar:com/android/systemui/screenshot/ScreenshotCrossProfileService.class */
public final class ScreenshotCrossProfileService extends Service {
    public static final Companion Companion = new Companion(null);
    public final IBinder mBinder = new ICrossProfileService.Stub() { // from class: com.android.systemui.screenshot.ScreenshotCrossProfileService$mBinder$1
        @Override // com.android.systemui.screenshot.ICrossProfileService
        public void launchIntent(Intent intent, Bundle bundle) {
            ScreenshotCrossProfileService.this.startActivity(intent, bundle);
        }
    };

    /* loaded from: mainsysui33.jar:com/android/systemui/screenshot/ScreenshotCrossProfileService$Companion.class */
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        Log.d("ScreenshotProxyService", "onBind: " + intent);
        return this.mBinder;
    }
}