package com.android.systemui.media.dialog;

import android.app.KeyguardManager;
import android.content.Context;
import android.media.AudioManager;
import android.media.session.MediaSessionManager;
import android.os.PowerExemptionManager;
import android.view.View;
import com.android.internal.logging.UiEventLogger;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import com.android.systemui.animation.DialogCuj;
import com.android.systemui.animation.DialogLaunchAnimator;
import com.android.systemui.broadcast.BroadcastSender;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.media.nearby.NearbyMediaDevicesManager;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.statusbar.notification.collection.notifcollection.CommonNotifCollection;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import java.util.Optional;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: mainsysui33.jar:com/android/systemui/media/dialog/MediaOutputDialogFactory.class */
public final class MediaOutputDialogFactory {
    public static final Companion Companion = new Companion(null);
    public static MediaOutputDialog mediaOutputDialog;
    public final AudioManager audioManager;
    public final BroadcastSender broadcastSender;
    public final Context context;
    public final DialogLaunchAnimator dialogLaunchAnimator;
    public final FeatureFlags featureFlags;
    public final KeyguardManager keyGuardManager;
    public final LocalBluetoothManager lbm;
    public final MediaSessionManager mediaSessionManager;
    public final Optional<NearbyMediaDevicesManager> nearbyMediaDevicesManagerOptional;
    public final CommonNotifCollection notifCollection;
    public final PowerExemptionManager powerExemptionManager;
    public final ActivityStarter starter;
    public final UiEventLogger uiEventLogger;

    /* loaded from: mainsysui33.jar:com/android/systemui/media/dialog/MediaOutputDialogFactory$Companion.class */
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public MediaOutputDialogFactory(Context context, MediaSessionManager mediaSessionManager, LocalBluetoothManager localBluetoothManager, ActivityStarter activityStarter, BroadcastSender broadcastSender, CommonNotifCollection commonNotifCollection, UiEventLogger uiEventLogger, DialogLaunchAnimator dialogLaunchAnimator, Optional<NearbyMediaDevicesManager> optional, AudioManager audioManager, PowerExemptionManager powerExemptionManager, KeyguardManager keyguardManager, FeatureFlags featureFlags) {
        this.context = context;
        this.mediaSessionManager = mediaSessionManager;
        this.lbm = localBluetoothManager;
        this.starter = activityStarter;
        this.broadcastSender = broadcastSender;
        this.notifCollection = commonNotifCollection;
        this.uiEventLogger = uiEventLogger;
        this.dialogLaunchAnimator = dialogLaunchAnimator;
        this.nearbyMediaDevicesManagerOptional = optional;
        this.audioManager = audioManager;
        this.powerExemptionManager = powerExemptionManager;
        this.keyGuardManager = keyguardManager;
        this.featureFlags = featureFlags;
    }

    public static /* synthetic */ void create$default(MediaOutputDialogFactory mediaOutputDialogFactory, String str, boolean z, View view, int i, Object obj) {
        if ((i & 4) != 0) {
            view = null;
        }
        mediaOutputDialogFactory.create(str, z, view);
    }

    /* JADX WARN: Type inference failed for: r0v3, types: [android.app.AlertDialog, android.app.Dialog, com.android.systemui.media.dialog.MediaOutputDialog] */
    public final void create(String str, boolean z, View view) {
        SystemUIDialog systemUIDialog = mediaOutputDialog;
        if (systemUIDialog != null) {
            systemUIDialog.dismiss();
        }
        ?? mediaOutputDialog2 = new MediaOutputDialog(this.context, z, this.broadcastSender, new MediaOutputController(this.context, str, this.mediaSessionManager, this.lbm, this.starter, this.notifCollection, this.dialogLaunchAnimator, this.nearbyMediaDevicesManagerOptional, this.audioManager, this.powerExemptionManager, this.keyGuardManager, this.featureFlags), this.uiEventLogger);
        mediaOutputDialog = mediaOutputDialog2;
        if (view != null) {
            DialogLaunchAnimator.showFromView$default(this.dialogLaunchAnimator, mediaOutputDialog2, view, new DialogCuj(58, "media_output"), false, 8, null);
        } else {
            mediaOutputDialog2.show();
        }
    }
}