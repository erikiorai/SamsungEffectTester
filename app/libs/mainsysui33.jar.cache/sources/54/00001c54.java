package com.android.systemui.media.controls.resume;

import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.media.MediaDescription;
import android.media.browse.MediaBrowser;
import android.media.session.MediaController;
import android.media.session.MediaSession;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import com.android.internal.annotations.VisibleForTesting;
import java.util.List;

/* loaded from: mainsysui33.jar:com/android/systemui/media/controls/resume/ResumeMediaBrowser.class */
public class ResumeMediaBrowser {
    public final MediaBrowserFactory mBrowserFactory;
    public final Callback mCallback;
    public final ComponentName mComponentName;
    public final Context mContext;
    public final ResumeMediaBrowserLogger mLogger;
    public MediaBrowser mMediaBrowser;
    public MediaController mMediaController;
    public final MediaController.Callback mMediaControllerCallback = new SessionDestroyCallback();
    public final MediaBrowser.SubscriptionCallback mSubscriptionCallback = new MediaBrowser.SubscriptionCallback() { // from class: com.android.systemui.media.controls.resume.ResumeMediaBrowser.1
        @Override // android.media.browse.MediaBrowser.SubscriptionCallback
        public void onChildrenLoaded(String str, List<MediaBrowser.MediaItem> list) {
            if (list.size() == 0) {
                Log.d("ResumeMediaBrowser", "No children found for " + ResumeMediaBrowser.this.mComponentName);
                if (ResumeMediaBrowser.this.mCallback != null) {
                    ResumeMediaBrowser.this.mCallback.onError();
                }
            } else {
                MediaBrowser.MediaItem mediaItem = list.get(0);
                MediaDescription description = mediaItem.getDescription();
                if (!mediaItem.isPlayable() || ResumeMediaBrowser.this.mMediaBrowser == null) {
                    Log.d("ResumeMediaBrowser", "Child found but not playable for " + ResumeMediaBrowser.this.mComponentName);
                    if (ResumeMediaBrowser.this.mCallback != null) {
                        ResumeMediaBrowser.this.mCallback.onError();
                    }
                } else if (ResumeMediaBrowser.this.mCallback != null) {
                    ResumeMediaBrowser.this.mCallback.addTrack(description, ResumeMediaBrowser.this.mMediaBrowser.getServiceComponent(), ResumeMediaBrowser.this);
                }
            }
            ResumeMediaBrowser.this.disconnect();
        }

        @Override // android.media.browse.MediaBrowser.SubscriptionCallback
        public void onError(String str) {
            Log.d("ResumeMediaBrowser", "Subscribe error for " + ResumeMediaBrowser.this.mComponentName + ": " + str);
            if (ResumeMediaBrowser.this.mCallback != null) {
                ResumeMediaBrowser.this.mCallback.onError();
            }
            ResumeMediaBrowser.this.disconnect();
        }

        @Override // android.media.browse.MediaBrowser.SubscriptionCallback
        public void onError(String str, Bundle bundle) {
            Log.d("ResumeMediaBrowser", "Subscribe error for " + ResumeMediaBrowser.this.mComponentName + ": " + str + ", options: " + bundle);
            if (ResumeMediaBrowser.this.mCallback != null) {
                ResumeMediaBrowser.this.mCallback.onError();
            }
            ResumeMediaBrowser.this.disconnect();
        }
    };
    public final MediaBrowser.ConnectionCallback mConnectionCallback = new MediaBrowser.ConnectionCallback() { // from class: com.android.systemui.media.controls.resume.ResumeMediaBrowser.2
        @Override // android.media.browse.MediaBrowser.ConnectionCallback
        public void onConnected() {
            Log.d("ResumeMediaBrowser", "Service connected for " + ResumeMediaBrowser.this.mComponentName);
            ResumeMediaBrowser.this.updateMediaController();
            if (ResumeMediaBrowser.this.isBrowserConnected()) {
                String root = ResumeMediaBrowser.this.mMediaBrowser.getRoot();
                if (!TextUtils.isEmpty(root)) {
                    if (ResumeMediaBrowser.this.mCallback != null) {
                        ResumeMediaBrowser.this.mCallback.onConnected();
                    }
                    if (ResumeMediaBrowser.this.mMediaBrowser != null) {
                        ResumeMediaBrowser.this.mMediaBrowser.subscribe(root, ResumeMediaBrowser.this.mSubscriptionCallback);
                        return;
                    }
                    return;
                }
            }
            if (ResumeMediaBrowser.this.mCallback != null) {
                ResumeMediaBrowser.this.mCallback.onError();
            }
            ResumeMediaBrowser.this.disconnect();
        }

        @Override // android.media.browse.MediaBrowser.ConnectionCallback
        public void onConnectionFailed() {
            Log.d("ResumeMediaBrowser", "Connection failed for " + ResumeMediaBrowser.this.mComponentName);
            if (ResumeMediaBrowser.this.mCallback != null) {
                ResumeMediaBrowser.this.mCallback.onError();
            }
            ResumeMediaBrowser.this.disconnect();
        }

        @Override // android.media.browse.MediaBrowser.ConnectionCallback
        public void onConnectionSuspended() {
            Log.d("ResumeMediaBrowser", "Connection suspended for " + ResumeMediaBrowser.this.mComponentName);
            if (ResumeMediaBrowser.this.mCallback != null) {
                ResumeMediaBrowser.this.mCallback.onError();
            }
            ResumeMediaBrowser.this.disconnect();
        }
    };

    /* loaded from: mainsysui33.jar:com/android/systemui/media/controls/resume/ResumeMediaBrowser$Callback.class */
    public static class Callback {
        public void addTrack(MediaDescription mediaDescription, ComponentName componentName, ResumeMediaBrowser resumeMediaBrowser) {
            throw null;
        }

        public void onConnected() {
        }

        public void onError() {
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/media/controls/resume/ResumeMediaBrowser$SessionDestroyCallback.class */
    public class SessionDestroyCallback extends MediaController.Callback {
        public SessionDestroyCallback() {
        }

        @Override // android.media.session.MediaController.Callback
        public void onSessionDestroyed() {
            ResumeMediaBrowser.this.mLogger.logSessionDestroyed(ResumeMediaBrowser.this.isBrowserConnected(), ResumeMediaBrowser.this.mComponentName);
            ResumeMediaBrowser.this.disconnect();
        }
    }

    public ResumeMediaBrowser(Context context, Callback callback, ComponentName componentName, MediaBrowserFactory mediaBrowserFactory, ResumeMediaBrowserLogger resumeMediaBrowserLogger) {
        this.mContext = context;
        this.mCallback = callback;
        this.mComponentName = componentName;
        this.mBrowserFactory = mediaBrowserFactory;
        this.mLogger = resumeMediaBrowserLogger;
    }

    @VisibleForTesting
    public MediaController createMediaController(MediaSession.Token token) {
        return new MediaController(this.mContext, token);
    }

    public void disconnect() {
        if (this.mMediaBrowser != null) {
            this.mLogger.logDisconnect(this.mComponentName);
            this.mMediaBrowser.disconnect();
        }
        this.mMediaBrowser = null;
        updateMediaController();
    }

    public void findRecentMedia() {
        disconnect();
        Bundle bundle = new Bundle();
        bundle.putBoolean("android.service.media.extra.RECENT", true);
        this.mMediaBrowser = this.mBrowserFactory.create(this.mComponentName, this.mConnectionCallback, bundle);
        updateMediaController();
        this.mLogger.logConnection(this.mComponentName, "findRecentMedia");
        this.mMediaBrowser.connect();
    }

    public PendingIntent getAppIntent() {
        return PendingIntent.getActivity(this.mContext, 0, this.mContext.getPackageManager().getLaunchIntentForPackage(this.mComponentName.getPackageName()), 67108864);
    }

    public MediaSession.Token getToken() {
        if (isBrowserConnected()) {
            return this.mMediaBrowser.getSessionToken();
        }
        return null;
    }

    public final boolean isBrowserConnected() {
        MediaBrowser mediaBrowser = this.mMediaBrowser;
        return mediaBrowser != null && mediaBrowser.isConnected();
    }

    public void restart() {
        disconnect();
        Bundle bundle = new Bundle();
        bundle.putBoolean("android.service.media.extra.RECENT", true);
        this.mMediaBrowser = this.mBrowserFactory.create(this.mComponentName, new MediaBrowser.ConnectionCallback() { // from class: com.android.systemui.media.controls.resume.ResumeMediaBrowser.3
            @Override // android.media.browse.MediaBrowser.ConnectionCallback
            public void onConnected() {
                Log.d("ResumeMediaBrowser", "Connected for restart " + ResumeMediaBrowser.this.mMediaBrowser.isConnected());
                ResumeMediaBrowser.this.updateMediaController();
                if (!ResumeMediaBrowser.this.isBrowserConnected()) {
                    if (ResumeMediaBrowser.this.mCallback != null) {
                        ResumeMediaBrowser.this.mCallback.onError();
                    }
                    ResumeMediaBrowser.this.disconnect();
                    return;
                }
                MediaController createMediaController = ResumeMediaBrowser.this.createMediaController(ResumeMediaBrowser.this.mMediaBrowser.getSessionToken());
                createMediaController.getTransportControls();
                createMediaController.getTransportControls().prepare();
                createMediaController.getTransportControls().play();
                if (ResumeMediaBrowser.this.mCallback != null) {
                    ResumeMediaBrowser.this.mCallback.onConnected();
                }
            }

            @Override // android.media.browse.MediaBrowser.ConnectionCallback
            public void onConnectionFailed() {
                if (ResumeMediaBrowser.this.mCallback != null) {
                    ResumeMediaBrowser.this.mCallback.onError();
                }
                ResumeMediaBrowser.this.disconnect();
            }

            @Override // android.media.browse.MediaBrowser.ConnectionCallback
            public void onConnectionSuspended() {
                if (ResumeMediaBrowser.this.mCallback != null) {
                    ResumeMediaBrowser.this.mCallback.onError();
                }
                ResumeMediaBrowser.this.disconnect();
            }
        }, bundle);
        updateMediaController();
        this.mLogger.logConnection(this.mComponentName, "restart");
        this.mMediaBrowser.connect();
    }

    public void testConnection() {
        disconnect();
        Bundle bundle = new Bundle();
        bundle.putBoolean("android.service.media.extra.RECENT", true);
        this.mMediaBrowser = this.mBrowserFactory.create(this.mComponentName, this.mConnectionCallback, bundle);
        updateMediaController();
        this.mLogger.logConnection(this.mComponentName, "testConnection");
        this.mMediaBrowser.connect();
    }

    public final void updateMediaController() {
        MediaController mediaController = this.mMediaController;
        MediaSession.Token sessionToken = mediaController != null ? mediaController.getSessionToken() : null;
        MediaSession.Token token = getToken();
        if ((sessionToken == null && token == null) || (sessionToken != null && sessionToken.equals(token))) {
            return;
        }
        MediaController mediaController2 = this.mMediaController;
        if (mediaController2 != null) {
            mediaController2.unregisterCallback(this.mMediaControllerCallback);
        }
        if (token == null) {
            this.mMediaController = null;
            return;
        }
        MediaController createMediaController = createMediaController(token);
        this.mMediaController = createMediaController;
        createMediaController.registerCallback(this.mMediaControllerCallback);
    }
}