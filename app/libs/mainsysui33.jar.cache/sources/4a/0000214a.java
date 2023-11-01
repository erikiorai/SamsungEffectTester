package com.android.systemui.qs;

import android.content.Context;
import android.database.ContentObserver;
import android.hardware.display.ColorDisplayManager;
import android.net.Uri;
import android.os.Handler;
import android.os.HandlerExecutor;
import android.provider.Settings;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.statusbar.policy.CallbackController;
import com.android.systemui.util.settings.SecureSettings;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/ReduceBrightColorsController.class */
public class ReduceBrightColorsController implements CallbackController<Listener> {
    public final ContentObserver mContentObserver;
    public UserTracker.Callback mCurrentUserTrackerCallback;
    public final Handler mHandler;
    public final ArrayList<Listener> mListeners = new ArrayList<>();
    public final ColorDisplayManager mManager;
    public final SecureSettings mSecureSettings;
    public final UserTracker mUserTracker;

    /* loaded from: mainsysui33.jar:com/android/systemui/qs/ReduceBrightColorsController$Listener.class */
    public interface Listener {
        default void onActivated(boolean z) {
        }
    }

    public ReduceBrightColorsController(UserTracker userTracker, Handler handler, ColorDisplayManager colorDisplayManager, SecureSettings secureSettings) {
        this.mManager = colorDisplayManager;
        this.mUserTracker = userTracker;
        this.mHandler = handler;
        this.mSecureSettings = secureSettings;
        this.mContentObserver = new ContentObserver(handler) { // from class: com.android.systemui.qs.ReduceBrightColorsController.1
            @Override // android.database.ContentObserver
            public void onChange(boolean z, Uri uri) {
                super.onChange(z, uri);
                String lastPathSegment = uri == null ? null : uri.getLastPathSegment();
                synchronized (ReduceBrightColorsController.this.mListeners) {
                    if (lastPathSegment != null) {
                        if (ReduceBrightColorsController.this.mListeners.size() != 0 && lastPathSegment.equals("reduce_bright_colors_activated")) {
                            Iterator it = ReduceBrightColorsController.this.mListeners.iterator();
                            while (it.hasNext()) {
                                ((Listener) it.next()).onActivated(ReduceBrightColorsController.this.mManager.isReduceBrightColorsActivated());
                            }
                        }
                    }
                }
            }
        };
        UserTracker.Callback callback = new UserTracker.Callback() { // from class: com.android.systemui.qs.ReduceBrightColorsController.2
            public void onUserChanged(int i, Context context) {
                synchronized (ReduceBrightColorsController.this.mListeners) {
                    if (ReduceBrightColorsController.this.mListeners.size() > 0) {
                        ReduceBrightColorsController.this.mSecureSettings.unregisterContentObserver(ReduceBrightColorsController.this.mContentObserver);
                        ReduceBrightColorsController.this.mSecureSettings.registerContentObserverForUser(Settings.Secure.getUriFor("reduce_bright_colors_activated"), false, ReduceBrightColorsController.this.mContentObserver, i);
                    }
                }
            }
        };
        this.mCurrentUserTrackerCallback = callback;
        userTracker.addCallback(callback, new HandlerExecutor(handler));
    }

    /* JADX DEBUG: Method merged with bridge method */
    public void addCallback(Listener listener) {
        synchronized (this.mListeners) {
            if (!this.mListeners.contains(listener)) {
                this.mListeners.add(listener);
                if (this.mListeners.size() == 1) {
                    this.mSecureSettings.registerContentObserverForUser(Settings.Secure.getUriFor("reduce_bright_colors_activated"), false, this.mContentObserver, this.mUserTracker.getUserId());
                }
            }
        }
    }

    public boolean isReduceBrightColorsActivated() {
        return this.mManager.isReduceBrightColorsActivated();
    }

    /* JADX DEBUG: Method merged with bridge method */
    public void removeCallback(Listener listener) {
        synchronized (this.mListeners) {
            if (this.mListeners.remove(listener) && this.mListeners.size() == 0) {
                this.mSecureSettings.unregisterContentObserver(this.mContentObserver);
            }
        }
    }

    public void setReduceBrightColorsActivated(boolean z) {
        this.mManager.setReduceBrightColorsActivated(z);
    }
}