package com.android.systemui;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/* loaded from: mainsysui33.jar:com/android/systemui/SystemUISecondaryUserService.class */
public class SystemUISecondaryUserService extends Service {
    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        ((SystemUIApplication) getApplication()).startSecondaryUserServicesIfNeeded();
    }
}