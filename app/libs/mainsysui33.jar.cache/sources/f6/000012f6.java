package com.android.systemui.clipboardoverlay;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.SystemProperties;
import android.provider.Settings;
import android.util.Log;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.CoreStartable;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.Flags;
import com.android.systemui.util.DeviceConfigProxy;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/clipboardoverlay/ClipboardListener.class */
public class ClipboardListener implements CoreStartable, ClipboardManager.OnPrimaryClipChangedListener {
    @VisibleForTesting
    public static final String EXTRA_SUPPRESS_OVERLAY = "com.android.systemui.SUPPRESS_CLIPBOARD_OVERLAY";
    @VisibleForTesting
    public static final String SHELL_PACKAGE = "com.android.shell";
    public final ClipboardManager mClipboardManager;
    public ClipboardOverlay mClipboardOverlay;
    public final ClipboardToast mClipboardToast;
    public final Context mContext;
    public final DeviceConfigProxy mDeviceConfig;
    public final FeatureFlags mFeatureFlags;
    public final ClipboardOverlayControllerLegacyFactory mOverlayFactory;
    public final Provider<ClipboardOverlayController> mOverlayProvider;
    public final UiEventLogger mUiEventLogger;
    public boolean mUsingNewOverlay;

    /* loaded from: mainsysui33.jar:com/android/systemui/clipboardoverlay/ClipboardListener$ClipboardOverlay.class */
    public interface ClipboardOverlay {
        void setClipData(ClipData clipData, String str);

        void setOnSessionCompleteListener(Runnable runnable);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.clipboardoverlay.ClipboardListener$$ExternalSyntheticLambda0.run():void] */
    public static /* synthetic */ void $r8$lambda$JIo8rS20L3yAzNt6EpaBN1DMj4U(ClipboardListener clipboardListener) {
        clipboardListener.lambda$onPrimaryClipChanged$0();
    }

    public ClipboardListener(Context context, DeviceConfigProxy deviceConfigProxy, Provider<ClipboardOverlayController> provider, ClipboardOverlayControllerLegacyFactory clipboardOverlayControllerLegacyFactory, ClipboardToast clipboardToast, ClipboardManager clipboardManager, UiEventLogger uiEventLogger, FeatureFlags featureFlags) {
        this.mContext = context;
        this.mDeviceConfig = deviceConfigProxy;
        this.mOverlayProvider = provider;
        this.mOverlayFactory = clipboardOverlayControllerLegacyFactory;
        this.mClipboardToast = clipboardToast;
        this.mClipboardManager = clipboardManager;
        this.mUiEventLogger = uiEventLogger;
        this.mFeatureFlags = featureFlags;
        this.mUsingNewOverlay = featureFlags.isEnabled(Flags.CLIPBOARD_OVERLAY_REFACTOR);
    }

    public static boolean isEmulator() {
        return SystemProperties.getBoolean("ro.boot.qemu", false);
    }

    public /* synthetic */ void lambda$onPrimaryClipChanged$0() {
        this.mClipboardOverlay = null;
    }

    @VisibleForTesting
    public static boolean shouldSuppressOverlay(ClipData clipData, String str, boolean z) {
        if ((!z && !SHELL_PACKAGE.equals(str)) || clipData == null || clipData.getDescription().getExtras() == null) {
            return false;
        }
        return clipData.getDescription().getExtras().getBoolean(EXTRA_SUPPRESS_OVERLAY, false);
    }

    public final boolean isUserSetupComplete() {
        boolean z = false;
        if (Settings.Secure.getInt(this.mContext.getContentResolver(), "user_setup_complete", 0) == 1) {
            z = true;
        }
        return z;
    }

    @Override // android.content.ClipboardManager.OnPrimaryClipChangedListener
    public void onPrimaryClipChanged() {
        if (this.mClipboardManager.hasPrimaryClip()) {
            String primaryClipSource = this.mClipboardManager.getPrimaryClipSource();
            ClipData primaryClip = this.mClipboardManager.getPrimaryClip();
            if (shouldSuppressOverlay(primaryClip, primaryClipSource, isEmulator())) {
                Log.i("ClipboardListener", "Clipboard overlay suppressed.");
            } else if (!isUserSetupComplete()) {
                if (shouldShowToast(primaryClip)) {
                    this.mUiEventLogger.log(ClipboardOverlayEvent.CLIPBOARD_TOAST_SHOWN, 0, primaryClipSource);
                    this.mClipboardToast.showCopiedToast();
                }
            } else {
                boolean isEnabled = this.mFeatureFlags.isEnabled(Flags.CLIPBOARD_OVERLAY_REFACTOR);
                if (this.mClipboardOverlay == null || isEnabled != this.mUsingNewOverlay) {
                    this.mUsingNewOverlay = isEnabled;
                    if (isEnabled) {
                        this.mClipboardOverlay = (ClipboardOverlay) this.mOverlayProvider.get();
                    } else {
                        this.mClipboardOverlay = this.mOverlayFactory.create(this.mContext);
                    }
                    this.mUiEventLogger.log(ClipboardOverlayEvent.CLIPBOARD_OVERLAY_ENTERED, 0, primaryClipSource);
                } else {
                    this.mUiEventLogger.log(ClipboardOverlayEvent.CLIPBOARD_OVERLAY_UPDATED, 0, primaryClipSource);
                }
                this.mClipboardOverlay.setClipData(primaryClip, primaryClipSource);
                this.mClipboardOverlay.setOnSessionCompleteListener(new Runnable() { // from class: com.android.systemui.clipboardoverlay.ClipboardListener$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        ClipboardListener.$r8$lambda$JIo8rS20L3yAzNt6EpaBN1DMj4U(ClipboardListener.this);
                    }
                });
            }
        }
    }

    public boolean shouldShowToast(ClipData clipData) {
        if (clipData == null) {
            return false;
        }
        if (clipData.getDescription().getClassificationStatus() == 3) {
            return !this.mClipboardToast.isShowing();
        }
        return true;
    }

    @Override // com.android.systemui.CoreStartable
    public void start() {
        if (this.mDeviceConfig.getBoolean("systemui", "clipboard_overlay_enabled", true)) {
            this.mClipboardManager.addPrimaryClipChangedListener(this);
        }
    }
}