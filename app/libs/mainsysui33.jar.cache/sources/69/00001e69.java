package com.android.systemui.navigationbar;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.om.IOverlayManager;
import android.content.pm.PackageManager;
import android.content.res.ApkAssets;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.Log;
import com.android.systemui.Dumpable;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.shared.system.ActivityManagerWrapper;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.concurrent.Executor;

/* loaded from: mainsysui33.jar:com/android/systemui/navigationbar/NavigationModeController.class */
public class NavigationModeController implements Dumpable {
    public static final String TAG = "NavigationModeController";
    public final Context mContext;
    public Context mCurrentUserContext;
    public final DeviceProvisionedController.DeviceProvisionedListener mDeviceProvisionedCallback;
    public ArrayList<ModeChangedListener> mListeners = new ArrayList<>();
    public final IOverlayManager mOverlayManager;
    public BroadcastReceiver mReceiver;
    public SettingsObserver mSettingsObserver;
    public final Executor mUiBgExecutor;

    /* loaded from: mainsysui33.jar:com/android/systemui/navigationbar/NavigationModeController$ModeChangedListener.class */
    public interface ModeChangedListener {
        void onNavigationModeChanged(int i);

        default void onSettingsChanged() {
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/navigationbar/NavigationModeController$SettingsObserver.class */
    public final class SettingsObserver extends ContentObserver {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public SettingsObserver(Handler handler) {
            super(handler);
            NavigationModeController.this = r4;
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z, Uri uri) {
            for (int i = 0; i < NavigationModeController.this.mListeners.size(); i++) {
                ((ModeChangedListener) NavigationModeController.this.mListeners.get(i)).onSettingsChanged();
            }
        }
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.navigationbar.NavigationModeController$$ExternalSyntheticLambda0.run():void] */
    public static /* synthetic */ void $r8$lambda$C9GKvhPovG6F7vnqVz_RXQIMWQE(NavigationModeController navigationModeController, int i) {
        navigationModeController.lambda$updateCurrentInteractionMode$0(i);
    }

    public NavigationModeController(Context context, DeviceProvisionedController deviceProvisionedController, ConfigurationController configurationController, Executor executor, DumpManager dumpManager) {
        DeviceProvisionedController.DeviceProvisionedListener deviceProvisionedListener = new DeviceProvisionedController.DeviceProvisionedListener() { // from class: com.android.systemui.navigationbar.NavigationModeController.1
            {
                NavigationModeController.this = this;
            }

            public void onUserSwitched() {
                String str = NavigationModeController.TAG;
                Log.d(str, "onUserSwitched: " + ActivityManagerWrapper.getInstance().getCurrentUserId());
                NavigationModeController.this.updateCurrentInteractionMode(true);
            }
        };
        this.mDeviceProvisionedCallback = deviceProvisionedListener;
        this.mReceiver = new BroadcastReceiver() { // from class: com.android.systemui.navigationbar.NavigationModeController.2
            {
                NavigationModeController.this = this;
            }

            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                Log.d(NavigationModeController.TAG, "ACTION_OVERLAY_CHANGED");
                NavigationModeController.this.updateCurrentInteractionMode(true);
            }
        };
        this.mContext = context;
        this.mCurrentUserContext = context;
        this.mOverlayManager = IOverlayManager.Stub.asInterface(ServiceManager.getService("overlay"));
        this.mUiBgExecutor = executor;
        dumpManager.registerDumpable(getClass().getSimpleName(), this);
        deviceProvisionedController.addCallback(deviceProvisionedListener);
        IntentFilter intentFilter = new IntentFilter("android.intent.action.OVERLAY_CHANGED");
        intentFilter.addDataScheme("package");
        intentFilter.addDataSchemeSpecificPart("android", 0);
        context.registerReceiverAsUser(this.mReceiver, UserHandle.ALL, intentFilter, null, null);
        this.mSettingsObserver = new SettingsObserver(new Handler());
        context.getContentResolver().registerContentObserver(Settings.System.getUriFor("back_gesture_height"), false, this.mSettingsObserver, -1);
        configurationController.addCallback(new ConfigurationController.ConfigurationListener() { // from class: com.android.systemui.navigationbar.NavigationModeController.3
            {
                NavigationModeController.this = this;
            }

            public void onThemeChanged() {
                Log.d(NavigationModeController.TAG, "onOverlayChanged");
                NavigationModeController.this.updateCurrentInteractionMode(true);
            }
        });
        updateCurrentInteractionMode(false);
    }

    public /* synthetic */ void lambda$updateCurrentInteractionMode$0(int i) {
        Settings.Secure.putString(this.mCurrentUserContext.getContentResolver(), "navigation_mode", String.valueOf(i));
    }

    public int addListener(ModeChangedListener modeChangedListener) {
        this.mListeners.add(modeChangedListener);
        return getCurrentInteractionMode(this.mCurrentUserContext);
    }

    @Override // com.android.systemui.Dumpable
    public void dump(PrintWriter printWriter, String[] strArr) {
        String str;
        printWriter.println("NavigationModeController:");
        printWriter.println("  mode=" + getCurrentInteractionMode(this.mCurrentUserContext));
        try {
            str = String.join(", ", this.mOverlayManager.getDefaultOverlayPackages());
        } catch (RemoteException e) {
            str = "failed_to_fetch";
        }
        printWriter.println("  defaultOverlays=" + str);
        dumpAssetPaths(this.mCurrentUserContext);
    }

    public final void dumpAssetPaths(Context context) {
        ApkAssets[] apkAssets;
        String str = TAG;
        Log.d(str, "  contextUser=" + this.mCurrentUserContext.getUserId());
        Log.d(str, "  assetPaths=");
        for (ApkAssets apkAssets2 : context.getResources().getAssets().getApkAssets()) {
            Log.d(TAG, "    " + apkAssets2.getDebugName());
        }
    }

    public final int getCurrentInteractionMode(Context context) {
        int integer = context.getResources().getInteger(17694906);
        String str = TAG;
        Log.d(str, "getCurrentInteractionMode: mode=" + integer + " contextUser=" + context.getUserId());
        return integer;
    }

    public Context getCurrentUserContext() {
        int currentUserId = ActivityManagerWrapper.getInstance().getCurrentUserId();
        String str = TAG;
        Log.d(str, "getCurrentUserContext: contextUser=" + this.mContext.getUserId() + " currentUser=" + currentUserId);
        if (this.mContext.getUserId() == currentUserId) {
            return this.mContext;
        }
        try {
            Context context = this.mContext;
            return context.createPackageContextAsUser(context.getPackageName(), 0, UserHandle.of(currentUserId));
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "Failed to create package context", e);
            return null;
        }
    }

    public boolean getImeDrawsImeNavBar() {
        return this.mCurrentUserContext.getResources().getBoolean(17891699);
    }

    public void removeListener(ModeChangedListener modeChangedListener) {
        this.mListeners.remove(modeChangedListener);
    }

    public void updateCurrentInteractionMode(boolean z) {
        Context currentUserContext = getCurrentUserContext();
        this.mCurrentUserContext = currentUserContext;
        final int currentInteractionMode = getCurrentInteractionMode(currentUserContext);
        this.mUiBgExecutor.execute(new Runnable() { // from class: com.android.systemui.navigationbar.NavigationModeController$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                NavigationModeController.$r8$lambda$C9GKvhPovG6F7vnqVz_RXQIMWQE(NavigationModeController.this, currentInteractionMode);
            }
        });
        Log.d(TAG, "updateCurrentInteractionMode: mode=" + currentInteractionMode);
        dumpAssetPaths(this.mCurrentUserContext);
        if (z) {
            for (int i = 0; i < this.mListeners.size(); i++) {
                this.mListeners.get(i).onNavigationModeChanged(currentInteractionMode);
            }
        }
    }
}