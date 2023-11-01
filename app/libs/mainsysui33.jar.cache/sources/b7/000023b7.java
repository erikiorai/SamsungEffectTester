package com.android.systemui.recents;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Configuration;
import android.provider.Settings;
import com.android.systemui.CoreStartable;
import com.android.systemui.statusbar.CommandQueue;
import java.io.PrintWriter;

/* loaded from: mainsysui33.jar:com/android/systemui/recents/Recents.class */
public class Recents implements CoreStartable, CommandQueue.Callbacks {
    public final CommandQueue mCommandQueue;
    public final Context mContext;
    public final RecentsImplementation mImpl;

    public Recents(Context context, RecentsImplementation recentsImplementation, CommandQueue commandQueue) {
        this.mContext = context;
        this.mImpl = recentsImplementation;
        this.mCommandQueue = commandQueue;
    }

    public void appTransitionFinished(int i) {
        if (this.mContext.getDisplayId() == i) {
            this.mImpl.onAppTransitionFinished();
        }
    }

    public void cancelPreloadRecentApps() {
        if (isUserSetup()) {
            this.mImpl.cancelPreloadRecentApps();
        }
    }

    @Override // com.android.systemui.CoreStartable, com.android.systemui.Dumpable
    public void dump(PrintWriter printWriter, String[] strArr) {
        this.mImpl.dump(printWriter);
    }

    public void hideRecentApps(boolean z, boolean z2) {
        if (isUserSetup()) {
            this.mImpl.hideRecentApps(z, z2);
        }
    }

    public final boolean isUserSetup() {
        ContentResolver contentResolver = this.mContext.getContentResolver();
        boolean z = false;
        if (Settings.Global.getInt(contentResolver, "device_provisioned", 0) != 0) {
            z = false;
            if (Settings.Secure.getInt(contentResolver, "user_setup_complete", 0) != 0) {
                z = true;
            }
        }
        return z;
    }

    @Override // com.android.systemui.CoreStartable
    public void onBootCompleted() {
        this.mImpl.onBootCompleted();
    }

    @Override // com.android.systemui.CoreStartable
    public void onConfigurationChanged(Configuration configuration) {
        this.mImpl.onConfigurationChanged(configuration);
    }

    public void preloadRecentApps() {
        if (isUserSetup()) {
            this.mImpl.preloadRecentApps();
        }
    }

    public void showRecentApps(boolean z) {
        if (isUserSetup()) {
            this.mImpl.showRecentApps(z);
        }
    }

    @Override // com.android.systemui.CoreStartable
    public void start() {
        this.mCommandQueue.addCallback(this);
        this.mImpl.onStart(this.mContext);
    }

    public void toggleRecentApps() {
        if (isUserSetup()) {
            this.mImpl.toggleRecentApps();
        }
    }
}