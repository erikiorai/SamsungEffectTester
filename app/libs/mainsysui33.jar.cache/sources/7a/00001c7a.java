package com.android.systemui.media.controls.ui;

import android.content.Context;
import android.content.res.Configuration;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.view.ViewGroup;
import android.view.ViewParent;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.SysuiStatusBarStateController;
import com.android.systemui.statusbar.notification.stack.MediaContainerView;
import com.android.systemui.statusbar.phone.KeyguardBypassController;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.util.LargeScreenUtils;
import com.android.systemui.util.settings.SecureSettings;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: mainsysui33.jar:com/android/systemui/media/controls/ui/KeyguardMediaController.class */
public final class KeyguardMediaController {
    public boolean allowMediaPlayerOnLockScreen;
    public final KeyguardBypassController bypassController;
    public final Context context;
    public final Handler handler;
    public final Uri lockScreenMediaPlayerUri;
    public final MediaHost mediaHost;
    public final SecureSettings secureSettings;
    public MediaContainerView singlePaneContainer;
    public ViewGroup splitShadeContainer;
    public final SysuiStatusBarStateController statusBarStateController;
    public boolean useSplitShade;
    public Function1<? super Boolean, Unit> visibilityChangedListener;
    public boolean visible;

    public KeyguardMediaController(MediaHost mediaHost, KeyguardBypassController keyguardBypassController, SysuiStatusBarStateController sysuiStatusBarStateController, Context context, SecureSettings secureSettings, Handler handler, ConfigurationController configurationController) {
        this.mediaHost = mediaHost;
        this.bypassController = keyguardBypassController;
        this.statusBarStateController = sysuiStatusBarStateController;
        this.context = context;
        this.secureSettings = secureSettings;
        this.handler = handler;
        sysuiStatusBarStateController.addCallback(new StatusBarStateController.StateListener() { // from class: com.android.systemui.media.controls.ui.KeyguardMediaController.1
            {
                KeyguardMediaController.this = this;
            }

            @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
            public void onStateChanged(int i) {
                KeyguardMediaController.this.refreshMediaPosition();
            }
        });
        configurationController.addCallback(new ConfigurationController.ConfigurationListener() { // from class: com.android.systemui.media.controls.ui.KeyguardMediaController.2
            {
                KeyguardMediaController.this = this;
            }

            public void onConfigChanged(Configuration configuration) {
                KeyguardMediaController.this.updateResources();
            }
        });
        secureSettings.registerContentObserverForUser("media_controls_lock_screen", new ContentObserver(handler) { // from class: com.android.systemui.media.controls.ui.KeyguardMediaController$settingsObserver$1
            @Override // android.database.ContentObserver
            public void onChange(boolean z, Uri uri) {
                if (Intrinsics.areEqual(uri, KeyguardMediaController.access$getLockScreenMediaPlayerUri$p(KeyguardMediaController.this))) {
                    KeyguardMediaController keyguardMediaController = KeyguardMediaController.this;
                    KeyguardMediaController.access$setAllowMediaPlayerOnLockScreen$p(keyguardMediaController, KeyguardMediaController.access$getSecureSettings$p(keyguardMediaController).getBoolForUser("media_controls_lock_screen", true, -2));
                    KeyguardMediaController.this.refreshMediaPosition();
                }
            }
        }, -1);
        mediaHost.setExpansion(1.0f);
        mediaHost.setShowsOnlyActiveMedia(true);
        mediaHost.setFalsingProtectionNeeded(true);
        mediaHost.init(2);
        updateResources();
        this.allowMediaPlayerOnLockScreen = true;
        this.lockScreenMediaPlayerUri = secureSettings.getUriFor("media_controls_lock_screen");
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.controls.ui.KeyguardMediaController$settingsObserver$1.onChange(boolean, android.net.Uri):void] */
    public static final /* synthetic */ Uri access$getLockScreenMediaPlayerUri$p(KeyguardMediaController keyguardMediaController) {
        return keyguardMediaController.lockScreenMediaPlayerUri;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.controls.ui.KeyguardMediaController$settingsObserver$1.onChange(boolean, android.net.Uri):void] */
    public static final /* synthetic */ SecureSettings access$getSecureSettings$p(KeyguardMediaController keyguardMediaController) {
        return keyguardMediaController.secureSettings;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.controls.ui.KeyguardMediaController$settingsObserver$1.onChange(boolean, android.net.Uri):void] */
    public static final /* synthetic */ void access$setAllowMediaPlayerOnLockScreen$p(KeyguardMediaController keyguardMediaController, boolean z) {
        keyguardMediaController.allowMediaPlayerOnLockScreen = z;
    }

    public static /* synthetic */ void getUseSplitShade$annotations() {
    }

    public final void attachSinglePaneContainer(MediaContainerView mediaContainerView) {
        boolean z = this.singlePaneContainer == null;
        this.singlePaneContainer = mediaContainerView;
        if (z) {
            this.mediaHost.addVisibilityChangeListener(new KeyguardMediaController$attachSinglePaneContainer$1(this));
        }
        reattachHostView();
        onMediaHostVisibilityChanged(this.mediaHost.getVisible());
    }

    public final void attachSplitShadeContainer(ViewGroup viewGroup) {
        this.splitShadeContainer = viewGroup;
        reattachHostView();
        refreshMediaPosition();
    }

    public final MediaContainerView getSinglePaneContainer() {
        return this.singlePaneContainer;
    }

    public final void hideMediaPlayer() {
        setVisibility(this.splitShadeContainer, 8);
        setVisibility(this.singlePaneContainer, 8);
    }

    public final void onMediaHostVisibilityChanged(boolean z) {
        refreshMediaPosition();
        if (z) {
            ViewGroup.LayoutParams layoutParams = this.mediaHost.getHostView().getLayoutParams();
            layoutParams.height = -2;
            layoutParams.width = -1;
        }
    }

    public final void reattachHostView() {
        ViewGroup viewGroup;
        MediaContainerView mediaContainerView;
        if (this.useSplitShade) {
            mediaContainerView = this.splitShadeContainer;
            viewGroup = this.singlePaneContainer;
        } else {
            viewGroup = this.splitShadeContainer;
            mediaContainerView = this.singlePaneContainer;
        }
        if (viewGroup != null && viewGroup.getChildCount() == 1) {
            viewGroup.removeAllViews();
        }
        if (mediaContainerView != null && mediaContainerView.getChildCount() == 0) {
            ViewParent parent = this.mediaHost.getHostView().getParent();
            if (parent != null) {
                ViewGroup viewGroup2 = parent instanceof ViewGroup ? (ViewGroup) parent : null;
                if (viewGroup2 != null) {
                    viewGroup2.removeView(this.mediaHost.getHostView());
                }
            }
            mediaContainerView.addView(this.mediaHost.getHostView());
        }
    }

    public final void refreshMediaPosition() {
        boolean z = this.statusBarStateController.getState() == 1;
        boolean z2 = false;
        if (this.mediaHost.getVisible()) {
            z2 = false;
            if (!this.bypassController.getBypassEnabled()) {
                z2 = false;
                if (z) {
                    z2 = false;
                    if (this.allowMediaPlayerOnLockScreen) {
                        z2 = true;
                    }
                }
            }
        }
        this.visible = z2;
        if (z2) {
            showMediaPlayer();
        } else {
            hideMediaPlayer();
        }
    }

    public final void setUseSplitShade(boolean z) {
        if (this.useSplitShade == z) {
            return;
        }
        this.useSplitShade = z;
        reattachHostView();
        refreshMediaPosition();
    }

    public final void setVisibility(ViewGroup viewGroup, int i) {
        Function1<? super Boolean, Unit> function1;
        Integer valueOf = viewGroup != null ? Integer.valueOf(viewGroup.getVisibility()) : null;
        if (viewGroup != null) {
            viewGroup.setVisibility(i);
        }
        if ((valueOf != null && valueOf.intValue() == i) || (function1 = this.visibilityChangedListener) == null) {
            return;
        }
        function1.invoke(Boolean.valueOf(i == 0));
    }

    public final void setVisibilityChangedListener(Function1<? super Boolean, Unit> function1) {
        this.visibilityChangedListener = function1;
    }

    public final void showMediaPlayer() {
        if (this.useSplitShade) {
            setVisibility(this.splitShadeContainer, 0);
            setVisibility(this.singlePaneContainer, 8);
            return;
        }
        setVisibility(this.singlePaneContainer, 0);
        setVisibility(this.splitShadeContainer, 8);
    }

    public final void updateResources() {
        setUseSplitShade(LargeScreenUtils.shouldUseSplitNotificationShade(this.context.getResources()));
    }
}