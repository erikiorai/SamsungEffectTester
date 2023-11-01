package com.android.systemui.media.dialog;

import android.app.AlertDialog;
import android.app.KeyguardManager;
import android.app.Notification;
import android.app.WallpaperColors;
import android.bluetooth.BluetoothLeBroadcast;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.media.AudioManager;
import android.media.INearbyMediaDevicesUpdateCallback;
import android.media.MediaMetadata;
import android.media.NearbyDevice;
import android.media.session.MediaController;
import android.media.session.MediaSessionManager;
import android.media.session.PlaybackState;
import android.os.IBinder;
import android.os.PowerExemptionManager;
import android.os.RemoteException;
import android.os.UserHandle;
import android.os.UserManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import androidx.core.graphics.drawable.IconCompat;
import com.android.settingslib.RestrictedLockUtilsInternal;
import com.android.settingslib.Utils;
import com.android.settingslib.bluetooth.BluetoothUtils;
import com.android.settingslib.bluetooth.LocalBluetoothLeBroadcast;
import com.android.settingslib.bluetooth.LocalBluetoothLeBroadcastMetadata;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import com.android.settingslib.media.InfoMediaManager;
import com.android.settingslib.media.LocalMediaManager;
import com.android.settingslib.media.MediaDevice;
import com.android.settingslib.utils.ThreadUtils;
import com.android.systemui.R$color;
import com.android.systemui.R$dimen;
import com.android.systemui.R$string;
import com.android.systemui.animation.ActivityLaunchAnimator;
import com.android.systemui.animation.DialogLaunchAnimator;
import com.android.systemui.broadcast.BroadcastSender;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.Flags;
import com.android.systemui.media.nearby.NearbyMediaDevicesManager;
import com.android.systemui.monet.ColorScheme;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.notifcollection.CommonNotifCollection;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executor;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/* loaded from: mainsysui33.jar:com/android/systemui/media/dialog/MediaOutputController.class */
public class MediaOutputController implements LocalMediaManager.DeviceCallback, INearbyMediaDevicesUpdateCallback {
    public static final boolean DEBUG = Log.isLoggable("MediaOutputController", 3);
    public float mActiveRadius;
    public final ActivityStarter mActivityStarter;
    public final AudioManager mAudioManager;
    public Callback mCallback;
    public int mColorButtonBackground;
    public int mColorConnectedItemBackground;
    public int mColorDialogBackground;
    public int mColorItemBackground;
    public int mColorItemContent;
    public int mColorPositiveButtonText;
    public int mColorSeekbarProgress;
    public final Context mContext;
    public int mCurrentState;
    public final DialogLaunchAnimator mDialogLaunchAnimator;
    public FeatureFlags mFeatureFlags;
    public float mInactiveRadius;
    public int mItemMarginEndDefault;
    public int mItemMarginEndSelectable;
    public final KeyguardManager mKeyGuardManager;
    public final LocalBluetoothManager mLocalBluetoothManager;
    public LocalMediaManager mLocalMediaManager;
    public MediaController mMediaController;
    public final MediaSessionManager mMediaSessionManager;
    private MediaOutputMetricLogger mMetricLogger;
    public final NearbyMediaDevicesManager mNearbyMediaDevicesManager;
    public final CommonNotifCollection mNotifCollection;
    public final String mPackageName;
    public final PowerExemptionManager mPowerExemptionManager;
    public final List<MediaDevice> mGroupMediaDevices = new CopyOnWriteArrayList();
    public final Object mMediaDevicesLock = new Object();
    public final List<MediaDevice> mMediaDevices = new CopyOnWriteArrayList();
    public final List<MediaDevice> mCachedMediaDevices = new CopyOnWriteArrayList();
    public final List<MediaItem> mMediaItemList = new CopyOnWriteArrayList();
    public final Map<String, Integer> mNearbyDeviceInfoMap = new ConcurrentHashMap();
    public boolean mIsRefreshing = false;
    public boolean mNeedRefresh = false;
    public final MediaController.Callback mCb = new MediaController.Callback() { // from class: com.android.systemui.media.dialog.MediaOutputController.1
        {
            MediaOutputController.this = this;
        }

        @Override // android.media.session.MediaController.Callback
        public void onMetadataChanged(MediaMetadata mediaMetadata) {
            MediaOutputController.this.mCallback.onMediaChanged();
        }

        @Override // android.media.session.MediaController.Callback
        public void onPlaybackStateChanged(PlaybackState playbackState) {
            int state = playbackState == null ? 1 : playbackState.getState();
            if (MediaOutputController.this.mCurrentState == state) {
                return;
            }
            if (state == 1) {
                MediaOutputController.this.mCallback.onMediaStoppedOrPaused();
            }
            MediaOutputController.this.mCurrentState = state;
        }
    };

    /* renamed from: com.android.systemui.media.dialog.MediaOutputController$2 */
    /* loaded from: mainsysui33.jar:com/android/systemui/media/dialog/MediaOutputController$2.class */
    public static /* synthetic */ class AnonymousClass2 {
        public static final /* synthetic */ int[] $SwitchMap$com$android$systemui$media$dialog$MediaOutputController$BroadcastNotifyDialog;

        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:23:0x0020 -> B:27:0x0014). Please submit an issue!!! */
        static {
            int[] iArr = new int[BroadcastNotifyDialog.values().length];
            $SwitchMap$com$android$systemui$media$dialog$MediaOutputController$BroadcastNotifyDialog = iArr;
            try {
                iArr[BroadcastNotifyDialog.ACTION_FIRST_LAUNCH.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$android$systemui$media$dialog$MediaOutputController$BroadcastNotifyDialog[BroadcastNotifyDialog.ACTION_BROADCAST_INFO_ICON.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/media/dialog/MediaOutputController$BroadcastNotifyDialog.class */
    public enum BroadcastNotifyDialog {
        ACTION_FIRST_LAUNCH,
        ACTION_BROADCAST_INFO_ICON
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/media/dialog/MediaOutputController$Callback.class */
    public interface Callback {
        void dismissDialog();

        void onDeviceListChanged();

        void onMediaChanged();

        void onMediaStoppedOrPaused();

        void onRouteChanged();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.dialog.MediaOutputController$$ExternalSyntheticLambda5.accept(java.lang.Object, java.lang.Object):void] */
    public static /* synthetic */ void $r8$lambda$OhpshnR_H3C74TOcyq__F86lQqI(MediaOutputController mediaOutputController, Integer num, MediaItem mediaItem) {
        mediaOutputController.lambda$buildMediaItems$0(num, mediaItem);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.dialog.MediaOutputController$$ExternalSyntheticLambda1.run():void] */
    public static /* synthetic */ void $r8$lambda$ZCwc3HB9QK7NhM3VZuj38V5ZyhM(MediaOutputController mediaOutputController, MediaDevice mediaDevice) {
        mediaOutputController.lambda$connectDevice$1(mediaDevice);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.dialog.MediaOutputController$$ExternalSyntheticLambda0.run():void] */
    /* renamed from: $r8$lambda$i3jTUAmQ-9skOXrb9VY8XdyEjcU */
    public static /* synthetic */ void m3316$r8$lambda$i3jTUAmQ9skOXrb9VY8XdyEjcU(MediaDevice mediaDevice, int i) {
        mediaDevice.requestSetVolume(i);
    }

    public MediaOutputController(Context context, String str, MediaSessionManager mediaSessionManager, LocalBluetoothManager localBluetoothManager, ActivityStarter activityStarter, CommonNotifCollection commonNotifCollection, DialogLaunchAnimator dialogLaunchAnimator, Optional<NearbyMediaDevicesManager> optional, AudioManager audioManager, PowerExemptionManager powerExemptionManager, KeyguardManager keyguardManager, FeatureFlags featureFlags) {
        this.mContext = context;
        this.mPackageName = str;
        this.mMediaSessionManager = mediaSessionManager;
        this.mLocalBluetoothManager = localBluetoothManager;
        this.mActivityStarter = activityStarter;
        this.mNotifCollection = commonNotifCollection;
        this.mAudioManager = audioManager;
        this.mPowerExemptionManager = powerExemptionManager;
        this.mKeyGuardManager = keyguardManager;
        this.mFeatureFlags = featureFlags;
        this.mLocalMediaManager = new LocalMediaManager(context, localBluetoothManager, new InfoMediaManager(context, str, null, localBluetoothManager), str);
        this.mMetricLogger = new MediaOutputMetricLogger(context, str);
        this.mDialogLaunchAnimator = dialogLaunchAnimator;
        this.mNearbyMediaDevicesManager = optional.orElse(null);
        this.mColorItemContent = Utils.getColorStateListDefaultColor(context, R$color.media_dialog_item_main_content);
        this.mColorSeekbarProgress = Utils.getColorStateListDefaultColor(context, R$color.media_dialog_seekbar_progress);
        this.mColorButtonBackground = Utils.getColorStateListDefaultColor(context, R$color.media_dialog_button_background);
        this.mColorItemBackground = Utils.getColorStateListDefaultColor(context, R$color.media_dialog_item_background);
        this.mColorConnectedItemBackground = Utils.getColorStateListDefaultColor(context, R$color.media_dialog_connected_item_background);
        this.mColorPositiveButtonText = Utils.getColorStateListDefaultColor(context, R$color.media_dialog_solid_button_text);
        this.mInactiveRadius = context.getResources().getDimension(R$dimen.media_output_dialog_background_radius);
        this.mActiveRadius = context.getResources().getDimension(R$dimen.media_output_dialog_active_background_radius);
        this.mColorDialogBackground = Utils.getColorStateListDefaultColor(context, R$color.media_dialog_background);
        this.mItemMarginEndDefault = (int) context.getResources().getDimension(R$dimen.media_output_dialog_default_margin_end);
        this.mItemMarginEndSelectable = (int) context.getResources().getDimension(R$dimen.media_output_dialog_selectable_margin_end);
    }

    public /* synthetic */ void lambda$buildMediaItems$0(Integer num, MediaItem mediaItem) {
        this.mMediaItemList.add(num.intValue(), mediaItem);
    }

    public /* synthetic */ void lambda$connectDevice$1(MediaDevice mediaDevice) {
        this.mLocalMediaManager.connectDevice(mediaDevice);
    }

    public boolean addDeviceToPlayMedia(MediaDevice mediaDevice) {
        this.mMetricLogger.logInteractionExpansion(mediaDevice);
        return this.mLocalMediaManager.addDeviceToPlayMedia(mediaDevice);
    }

    public void adjustVolume(final MediaDevice mediaDevice, final int i) {
        this.mMetricLogger.logInteractionAdjustVolume(mediaDevice);
        ThreadUtils.postOnBackgroundThread(new Runnable() { // from class: com.android.systemui.media.dialog.MediaOutputController$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                MediaOutputController.m3316$r8$lambda$i3jTUAmQ9skOXrb9VY8XdyEjcU(MediaDevice.this, i);
            }
        });
    }

    public IBinder asBinder() {
        return null;
    }

    public final void attachRangeInfo(List<MediaDevice> list) {
        for (MediaDevice mediaDevice : list) {
            if (this.mNearbyDeviceInfoMap.containsKey(mediaDevice.getId())) {
                mediaDevice.setRangeZone(this.mNearbyDeviceInfoMap.get(mediaDevice.getId()).intValue());
            }
        }
    }

    public final void buildDefaultMediaDevices(List<MediaDevice> list) {
        synchronized (this.mMediaDevicesLock) {
            attachRangeInfo(list);
            Collections.sort(list, Comparator.naturalOrder());
            if (!this.mMediaDevices.isEmpty()) {
                ArrayList arrayList = new ArrayList();
                for (MediaDevice mediaDevice : this.mMediaDevices) {
                    Iterator<MediaDevice> it = list.iterator();
                    while (true) {
                        if (it.hasNext()) {
                            MediaDevice next = it.next();
                            if (TextUtils.equals(mediaDevice.getId(), next.getId())) {
                                arrayList.add(next);
                                break;
                            }
                        }
                    }
                }
                if (arrayList.size() != list.size()) {
                    list.removeAll(arrayList);
                    arrayList.addAll(list);
                }
                this.mMediaDevices.clear();
                this.mMediaDevices.addAll(arrayList);
                return;
            }
            boolean z = hasMutingExpectedDevice() && !isCurrentConnectedDeviceRemote();
            MediaDevice currentConnectedMediaDevice = z ? null : getCurrentConnectedMediaDevice();
            if (currentConnectedMediaDevice != null) {
                for (MediaDevice mediaDevice2 : list) {
                    if (TextUtils.equals(mediaDevice2.getId(), currentConnectedMediaDevice.getId())) {
                        this.mMediaDevices.add(0, mediaDevice2);
                    } else {
                        this.mMediaDevices.add(mediaDevice2);
                    }
                }
                return;
            }
            if (DEBUG) {
                Log.d("MediaOutputController", "No connected media device or muting expected device exist.");
            }
            if (z) {
                for (MediaDevice mediaDevice3 : list) {
                    if (mediaDevice3.isMutingExpectedDevice()) {
                        this.mMediaDevices.add(0, mediaDevice3);
                    } else {
                        this.mMediaDevices.add(mediaDevice3);
                    }
                }
            } else {
                this.mMediaDevices.addAll(list);
            }
        }
    }

    public final void buildMediaDevices(List<MediaDevice> list) {
        if (isAdvancedLayoutSupported()) {
            buildMediaItems(list);
        } else {
            buildDefaultMediaDevices(list);
        }
    }

    public final void buildMediaItems(List<MediaDevice> list) {
        synchronized (this.mMediaDevicesLock) {
            attachRangeInfo(list);
            Collections.sort(list, Comparator.naturalOrder());
            if (!this.mMediaItemList.isEmpty()) {
                ArrayList arrayList = new ArrayList();
                HashMap hashMap = new HashMap();
                for (MediaItem mediaItem : this.mMediaItemList) {
                    Iterator<MediaDevice> it = list.iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            break;
                        }
                        MediaDevice next = it.next();
                        if (mediaItem.getMediaDevice().isPresent() && TextUtils.equals(mediaItem.getMediaDevice().get().getId(), next.getId())) {
                            arrayList.add(next);
                            break;
                        }
                    }
                    if (mediaItem.getMediaItemType() == 1) {
                        hashMap.put(Integer.valueOf(this.mMediaItemList.indexOf(mediaItem)), mediaItem);
                    }
                }
                if (arrayList.size() != list.size()) {
                    list.removeAll(arrayList);
                    arrayList.addAll(list);
                }
                this.mMediaItemList.clear();
                this.mMediaItemList.addAll((Collection) arrayList.stream().map(new Function() { // from class: com.android.systemui.media.dialog.MediaOutputController$$ExternalSyntheticLambda4
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        return new MediaItem((MediaDevice) obj);
                    }
                }).collect(Collectors.toList()));
                hashMap.forEach(new BiConsumer() { // from class: com.android.systemui.media.dialog.MediaOutputController$$ExternalSyntheticLambda5
                    @Override // java.util.function.BiConsumer
                    public final void accept(Object obj, Object obj2) {
                        MediaOutputController.$r8$lambda$OhpshnR_H3C74TOcyq__F86lQqI(MediaOutputController.this, (Integer) obj, (MediaItem) obj2);
                    }
                });
                this.mMediaItemList.add(new MediaItem());
                return;
            }
            boolean z = hasMutingExpectedDevice() && !isCurrentConnectedDeviceRemote();
            MediaDevice currentConnectedMediaDevice = z ? null : getCurrentConnectedMediaDevice();
            if (currentConnectedMediaDevice != null) {
                for (MediaDevice mediaDevice : list) {
                    if (TextUtils.equals(mediaDevice.getId(), currentConnectedMediaDevice.getId())) {
                        this.mMediaItemList.add(0, new MediaItem(mediaDevice));
                    } else {
                        this.mMediaItemList.add(new MediaItem(mediaDevice));
                    }
                }
                categorizeMediaItems(currentConnectedMediaDevice);
                return;
            }
            if (DEBUG) {
                Log.d("MediaOutputController", "No connected media device or muting expected device exist.");
            }
            if (z) {
                for (MediaDevice mediaDevice2 : list) {
                    if (mediaDevice2.isMutingExpectedDevice()) {
                        this.mMediaItemList.add(0, new MediaItem(mediaDevice2));
                        this.mMediaItemList.add(1, new MediaItem(this.mContext.getString(R$string.media_output_group_title_speakers_and_displays), 1));
                    } else {
                        this.mMediaItemList.add(new MediaItem(mediaDevice2));
                    }
                }
                this.mMediaItemList.add(new MediaItem());
            } else {
                this.mMediaItemList.addAll((Collection) list.stream().map(new Function() { // from class: com.android.systemui.media.dialog.MediaOutputController$$ExternalSyntheticLambda4
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        return new MediaItem((MediaDevice) obj);
                    }
                }).collect(Collectors.toList()));
                categorizeMediaItems(null);
            }
        }
    }

    public void cancelMuteAwaitConnection() {
        if (this.mAudioManager.getMutingExpectedDevice() == null) {
            return;
        }
        try {
            synchronized (this.mMediaDevicesLock) {
                this.mMediaDevices.removeIf(new Predicate() { // from class: com.android.systemui.media.dialog.MediaOutputController$$ExternalSyntheticLambda2
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((MediaDevice) obj).isMutingExpectedDevice();
                    }
                });
                this.mMediaItemList.removeIf(new Predicate() { // from class: com.android.systemui.media.dialog.MediaOutputController$$ExternalSyntheticLambda3
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((MediaItem) obj).isMutingExpectedDevice();
                    }
                });
            }
            AudioManager audioManager = this.mAudioManager;
            audioManager.cancelMuteAwaitConnection(audioManager.getMutingExpectedDevice());
        } catch (Exception e) {
            Log.d("MediaOutputController", "Unable to cancel mute await connection");
        }
    }

    public final void categorizeMediaItems(MediaDevice mediaDevice) {
        synchronized (this.mMediaDevicesLock) {
            Set set = (Set) getSelectedMediaDevice().stream().map(new Function() { // from class: com.android.systemui.media.dialog.MediaOutputController$$ExternalSyntheticLambda6
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return ((MediaDevice) obj).getId();
                }
            }).collect(Collectors.toSet());
            if (mediaDevice != null) {
                set.add(mediaDevice.getId());
            }
            Iterator<MediaItem> it = this.mMediaItemList.iterator();
            int i = 1;
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                MediaItem next = it.next();
                if (next.getMediaDevice().isPresent()) {
                    if (!set.contains(next.getMediaDevice().get().getId())) {
                        this.mMediaItemList.add(i, new MediaItem(this.mContext.getString(R$string.media_output_group_title_speakers_and_displays), 1));
                        break;
                    }
                    i = this.mMediaItemList.indexOf(next) + 1;
                }
            }
            this.mMediaItemList.add(new MediaItem());
        }
    }

    public void connectDevice(final MediaDevice mediaDevice) {
        this.mMetricLogger.updateOutputEndPoints(getCurrentConnectedMediaDevice(), mediaDevice);
        ThreadUtils.postOnBackgroundThread(new Runnable() { // from class: com.android.systemui.media.dialog.MediaOutputController$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                MediaOutputController.$r8$lambda$ZCwc3HB9QK7NhM3VZuj38V5ZyhM(MediaOutputController.this, mediaDevice);
            }
        });
    }

    public float getActiveRadius() {
        return this.mActiveRadius;
    }

    public Intent getAppLaunchIntent() {
        if (this.mPackageName.isEmpty()) {
            return null;
        }
        return this.mContext.getPackageManager().getLaunchIntentForPackage(this.mPackageName);
    }

    public Drawable getAppSourceIconFromPackage() {
        if (this.mPackageName.isEmpty()) {
            return null;
        }
        try {
            Log.d("MediaOutputController", "try to get app icon");
            return this.mContext.getPackageManager().getApplicationIcon(this.mPackageName);
        } catch (PackageManager.NameNotFoundException e) {
            Log.d("MediaOutputController", "icon not found");
            return null;
        }
    }

    public String getAppSourceName() {
        ApplicationInfo applicationInfo = null;
        if (this.mPackageName.isEmpty()) {
            return null;
        }
        PackageManager packageManager = this.mContext.getPackageManager();
        try {
            applicationInfo = packageManager.getApplicationInfo(this.mPackageName, PackageManager.ApplicationInfoFlags.of(0L));
        } catch (PackageManager.NameNotFoundException e) {
        }
        return (String) (applicationInfo != null ? packageManager.getApplicationLabel(applicationInfo) : this.mContext.getString(R$string.media_output_dialog_unknown_launch_app_name));
    }

    public String getBroadcastCode() {
        LocalBluetoothLeBroadcast leAudioBroadcastProfile = this.mLocalBluetoothManager.getProfileManager().getLeAudioBroadcastProfile();
        if (leAudioBroadcastProfile == null) {
            Log.d("MediaOutputController", "getBroadcastCode: LE Audio Broadcast is null");
            return "";
        }
        return new String(leAudioBroadcastProfile.getBroadcastCode(), StandardCharsets.UTF_8);
    }

    public String getBroadcastMetadata() {
        LocalBluetoothLeBroadcast leAudioBroadcastProfile = this.mLocalBluetoothManager.getProfileManager().getLeAudioBroadcastProfile();
        if (leAudioBroadcastProfile == null) {
            Log.d("MediaOutputController", "getBroadcastMetadata: LE Audio Broadcast is null");
            return "";
        }
        LocalBluetoothLeBroadcastMetadata localBluetoothLeBroadcastMetaData = leAudioBroadcastProfile.getLocalBluetoothLeBroadcastMetaData();
        return localBluetoothLeBroadcastMetaData != null ? localBluetoothLeBroadcastMetaData.convertToQrCodeString() : "";
    }

    public String getBroadcastName() {
        LocalBluetoothLeBroadcast leAudioBroadcastProfile = this.mLocalBluetoothManager.getProfileManager().getLeAudioBroadcastProfile();
        if (leAudioBroadcastProfile == null) {
            Log.d("MediaOutputController", "getBroadcastName: LE Audio Broadcast is null");
            return "";
        }
        return leAudioBroadcastProfile.getProgramInfo();
    }

    public int getColorButtonBackground() {
        return this.mColorButtonBackground;
    }

    public int getColorConnectedItemBackground() {
        return this.mColorConnectedItemBackground;
    }

    public int getColorDialogBackground() {
        return this.mColorDialogBackground;
    }

    public int getColorItemBackground() {
        return this.mColorItemBackground;
    }

    public int getColorItemContent() {
        return this.mColorItemContent;
    }

    public int getColorPositiveButtonText() {
        return this.mColorPositiveButtonText;
    }

    public int getColorSeekbarProgress() {
        return this.mColorSeekbarProgress;
    }

    public MediaDevice getCurrentConnectedMediaDevice() {
        return this.mLocalMediaManager.getCurrentConnectedDevice();
    }

    public List<MediaDevice> getDeselectableMediaDevice() {
        return this.mLocalMediaManager.getDeselectableMediaDevice();
    }

    public IconCompat getDeviceIconCompat(MediaDevice mediaDevice) {
        Drawable icon = mediaDevice.getIcon();
        Drawable drawable = icon;
        if (icon == null) {
            if (DEBUG) {
                Log.d("MediaOutputController", "getDeviceIconCompat() device : " + mediaDevice.getName() + ", drawable is null");
            }
            drawable = this.mContext.getDrawable(17302335);
        }
        if (!(drawable instanceof BitmapDrawable)) {
            setColorFilter(drawable, isActiveItem(mediaDevice));
        }
        return BluetoothUtils.createIconWithDrawable(drawable);
    }

    public IconCompat getHeaderIcon() {
        Bitmap iconBitmap;
        MediaController mediaController = this.mMediaController;
        if (mediaController == null) {
            return null;
        }
        MediaMetadata metadata = mediaController.getMetadata();
        if (metadata != null && (iconBitmap = metadata.getDescription().getIconBitmap()) != null) {
            Context context = this.mContext;
            return IconCompat.createWithBitmap(Utils.convertCornerRadiusBitmap(context, iconBitmap, context.getResources().getDimensionPixelSize(R$dimen.media_output_dialog_icon_corner_radius)));
        }
        if (DEBUG) {
            Log.d("MediaOutputController", "Media meta data does not contain icon information");
        }
        return getNotificationIcon();
    }

    public CharSequence getHeaderSubTitle() {
        MediaMetadata metadata;
        MediaController mediaController = this.mMediaController;
        if (mediaController == null || (metadata = mediaController.getMetadata()) == null) {
            return null;
        }
        return metadata.getDescription().getSubtitle();
    }

    public CharSequence getHeaderTitle() {
        MediaMetadata metadata;
        MediaController mediaController = this.mMediaController;
        return (mediaController == null || (metadata = mediaController.getMetadata()) == null) ? this.mContext.getText(R$string.controls_media_title) : metadata.getDescription().getTitle();
    }

    public float getInactiveRadius() {
        return this.mInactiveRadius;
    }

    public int getItemMarginEndDefault() {
        return this.mItemMarginEndDefault;
    }

    public int getItemMarginEndSelectable() {
        return this.mItemMarginEndSelectable;
    }

    public Collection<MediaDevice> getMediaDevices() {
        return this.mMediaDevices;
    }

    public List<MediaItem> getMediaItemList() {
        return this.mMediaItemList;
    }

    public IconCompat getNotificationIcon() {
        if (TextUtils.isEmpty(this.mPackageName)) {
            return null;
        }
        for (NotificationEntry notificationEntry : this.mNotifCollection.getAllNotifs()) {
            Notification notification = notificationEntry.getSbn().getNotification();
            if (notification.isMediaNotification() && TextUtils.equals(notificationEntry.getSbn().getPackageName(), this.mPackageName)) {
                Icon largeIcon = notification.getLargeIcon();
                if (largeIcon == null) {
                    return null;
                }
                return IconCompat.createFromIcon(largeIcon);
            }
        }
        return null;
    }

    public IconCompat getNotificationSmallIcon() {
        if (TextUtils.isEmpty(this.mPackageName)) {
            return null;
        }
        for (NotificationEntry notificationEntry : this.mNotifCollection.getAllNotifs()) {
            Notification notification = notificationEntry.getSbn().getNotification();
            if (notification.isMediaNotification() && TextUtils.equals(notificationEntry.getSbn().getPackageName(), this.mPackageName)) {
                Icon smallIcon = notification.getSmallIcon();
                if (smallIcon == null) {
                    return null;
                }
                return IconCompat.createFromIcon(smallIcon);
            }
        }
        return null;
    }

    public List<MediaDevice> getSelectableMediaDevice() {
        return this.mLocalMediaManager.getSelectableMediaDevice();
    }

    public List<MediaDevice> getSelectedMediaDevice() {
        return this.mLocalMediaManager.getSelectedMediaDevice();
    }

    public boolean hasAdjustVolumeUserRestriction() {
        if (RestrictedLockUtilsInternal.checkIfRestrictionEnforced(this.mContext, "no_adjust_volume", UserHandle.myUserId()) != null) {
            return true;
        }
        return ((UserManager) this.mContext.getSystemService(UserManager.class)).hasBaseUserRestriction("no_adjust_volume", UserHandle.of(UserHandle.myUserId()));
    }

    public boolean hasMutingExpectedDevice() {
        return this.mAudioManager.getMutingExpectedDevice() != null;
    }

    public boolean isActiveItem(MediaDevice mediaDevice) {
        boolean equals = this.mLocalMediaManager.getCurrentConnectedDevice().getId().equals(mediaDevice.getId());
        boolean z = false;
        boolean z2 = getSelectedMediaDevice().size() > 1 && getSelectedMediaDevice().contains(mediaDevice);
        if ((!hasAdjustVolumeUserRestriction() && equals && !isAnyDeviceTransferring()) || z2) {
            z = true;
        }
        return z;
    }

    public boolean isActiveRemoteDevice(MediaDevice mediaDevice) {
        List<String> features = mediaDevice.getFeatures();
        return features.contains("android.media.route.feature.REMOTE_PLAYBACK") || features.contains("android.media.route.feature.REMOTE_AUDIO_PLAYBACK") || features.contains("android.media.route.feature.REMOTE_VIDEO_PLAYBACK") || features.contains("android.media.route.feature.REMOTE_GROUP_PLAYBACK");
    }

    public boolean isAdvancedLayoutSupported() {
        return this.mFeatureFlags.isEnabled(Flags.OUTPUT_SWITCHER_ADVANCED_LAYOUT);
    }

    public boolean isAnyDeviceTransferring() {
        synchronized (this.mMediaDevicesLock) {
            if (!isAdvancedLayoutSupported()) {
                Iterator<MediaDevice> it = this.mMediaDevices.iterator();
                do {
                    if (it.hasNext()) {
                    }
                } while (it.next().getState() != 1);
                return true;
            }
            for (MediaItem mediaItem : this.mMediaItemList) {
                if (mediaItem.getMediaDevice().isPresent() && mediaItem.getMediaDevice().get().getState() == 1) {
                    return true;
                }
            }
            return false;
        }
    }

    public boolean isBluetoothLeBroadcastEnabled() {
        LocalBluetoothLeBroadcast leAudioBroadcastProfile = this.mLocalBluetoothManager.getProfileManager().getLeAudioBroadcastProfile();
        if (leAudioBroadcastProfile == null) {
            return false;
        }
        return leAudioBroadcastProfile.isEnabled(null);
    }

    public boolean isBluetoothLeDevice(MediaDevice mediaDevice) {
        return mediaDevice.isBLEDevice();
    }

    public boolean isBroadcastSupported() {
        return this.mLocalBluetoothManager.getProfileManager().getLeAudioBroadcastProfile() != null;
    }

    public boolean isCurrentConnectedDeviceRemote() {
        MediaDevice currentConnectedMediaDevice = getCurrentConnectedMediaDevice();
        return currentConnectedMediaDevice != null && isActiveRemoteDevice(currentConnectedMediaDevice);
    }

    public final boolean isPlayBackInfoLocal() {
        MediaController mediaController = this.mMediaController;
        boolean z = true;
        if (mediaController == null || mediaController.getPlaybackInfo() == null || this.mMediaController.getPlaybackInfo().getPlaybackType() != 1) {
            z = false;
        }
        return z;
    }

    public boolean isPlaying() {
        PlaybackState playbackState;
        MediaController mediaController = this.mMediaController;
        boolean z = false;
        if (mediaController == null || (playbackState = mediaController.getPlaybackState()) == null) {
            return false;
        }
        if (playbackState.getState() == 3) {
            z = true;
        }
        return z;
    }

    public boolean isRefreshing() {
        return this.mIsRefreshing;
    }

    public boolean isVolumeControlEnabled(MediaDevice mediaDevice) {
        return (isPlayBackInfoLocal() || mediaDevice.getDeviceType() != 7) && !mediaDevice.isVolumeFixed();
    }

    public void launchBluetoothPairing(View view) {
        KeyguardManager keyguardManager;
        ActivityLaunchAnimator.Controller createActivityLaunchController = this.mDialogLaunchAnimator.createActivityLaunchController(view);
        if (createActivityLaunchController == null || ((keyguardManager = this.mKeyGuardManager) != null && keyguardManager.isKeyguardLocked())) {
            this.mCallback.dismissDialog();
        }
        Intent addFlags = new Intent("android.settings.BLUETOOTH_SETTINGS").addFlags(335544320);
        Intent intent = new Intent("android.settings.SETTINGS_EMBED_DEEP_LINK_ACTIVITY");
        if (intent.resolveActivity(this.mContext.getPackageManager()) == null) {
            this.mActivityStarter.startActivity(addFlags, true, createActivityLaunchController);
            return;
        }
        Log.d("MediaOutputController", "Device support split mode, launch page with deep link");
        intent.setFlags(268435456);
        intent.putExtra("android.provider.extra.SETTINGS_EMBEDDED_DEEP_LINK_INTENT_URI", addFlags.toUri(1));
        intent.putExtra("android.provider.extra.SETTINGS_EMBEDDED_DEEP_LINK_HIGHLIGHT_MENU_KEY", "top_level_connected_devices");
        this.mActivityStarter.startActivity(intent, true, createActivityLaunchController);
    }

    public void launchLeBroadcastNotifyDialog(View view, BroadcastSender broadcastSender, BroadcastNotifyDialog broadcastNotifyDialog, DialogInterface.OnClickListener onClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.mContext);
        int i = AnonymousClass2.$SwitchMap$com$android$systemui$media$dialog$MediaOutputController$BroadcastNotifyDialog[broadcastNotifyDialog.ordinal()];
        if (i == 1) {
            builder.setTitle(R$string.media_output_first_broadcast_title);
            builder.setMessage(R$string.media_output_first_notify_broadcast_message);
            builder.setNegativeButton(17039360, (DialogInterface.OnClickListener) null);
            builder.setPositiveButton(R$string.media_output_broadcast, onClickListener);
        } else if (i == 2) {
            builder.setTitle(R$string.media_output_broadcast);
            builder.setMessage(R$string.media_output_broadcasting_message);
            builder.setPositiveButton(17039370, (DialogInterface.OnClickListener) null);
        }
        AlertDialog create = builder.create();
        create.getWindow().setType(2009);
        SystemUIDialog.setShowForAllUsers(create, true);
        SystemUIDialog.registerDismissListener(create);
        create.show();
    }

    public void launchMediaOutputBroadcastDialog(View view, BroadcastSender broadcastSender) {
        this.mDialogLaunchAnimator.showFromView(new MediaOutputBroadcastDialog(this.mContext, true, broadcastSender, new MediaOutputController(this.mContext, this.mPackageName, this.mMediaSessionManager, this.mLocalBluetoothManager, this.mActivityStarter, this.mNotifCollection, this.mDialogLaunchAnimator, Optional.of(this.mNearbyMediaDevicesManager), this.mAudioManager, this.mPowerExemptionManager, this.mKeyGuardManager, this.mFeatureFlags)), view);
    }

    @Override // com.android.settingslib.media.LocalMediaManager.DeviceCallback
    public void onDeviceAttributesChanged() {
        this.mCallback.onRouteChanged();
    }

    /* JADX DEBUG: Type inference failed for r0v24. Raw type applied. Possible types: java.util.List<com.android.systemui.media.dialog.MediaItem> */
    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.android.settingslib.media.LocalMediaManager.DeviceCallback
    public void onDeviceListUpdate(List<MediaDevice> list) {
        if ((isAdvancedLayoutSupported() ? this.mMediaItemList : this.mMediaDevices).isEmpty() || !this.mIsRefreshing) {
            buildMediaDevices(list);
            this.mCallback.onDeviceListChanged();
            return;
        }
        synchronized (this.mMediaDevicesLock) {
            this.mNeedRefresh = true;
            this.mCachedMediaDevices.clear();
            this.mCachedMediaDevices.addAll(list);
        }
    }

    public void onDevicesUpdated(List<NearbyDevice> list) throws RemoteException {
        this.mNearbyDeviceInfoMap.clear();
        for (NearbyDevice nearbyDevice : list) {
            this.mNearbyDeviceInfoMap.put(nearbyDevice.getMediaRoute2Id(), Integer.valueOf(nearbyDevice.getRangeZone()));
        }
        this.mNearbyMediaDevicesManager.unregisterNearbyDevicesCallback(this);
    }

    @Override // com.android.settingslib.media.LocalMediaManager.DeviceCallback
    public void onRequestFailed(int i) {
        this.mCallback.onRouteChanged();
        if (isAdvancedLayoutSupported()) {
            this.mMetricLogger.logOutputItemFailure(new ArrayList(this.mMediaItemList), i);
        } else {
            this.mMetricLogger.logOutputFailure(new ArrayList(this.mMediaDevices), i);
        }
    }

    @Override // com.android.settingslib.media.LocalMediaManager.DeviceCallback
    public void onSelectedDeviceStateChanged(MediaDevice mediaDevice, int i) {
        this.mCallback.onRouteChanged();
        if (isAdvancedLayoutSupported()) {
            this.mMetricLogger.logOutputItemSuccess(mediaDevice.toString(), new ArrayList(this.mMediaItemList));
        } else {
            this.mMetricLogger.logOutputSuccess(mediaDevice.toString(), new ArrayList(this.mMediaDevices));
        }
    }

    public void refreshDataSetIfNeeded() {
        if (this.mNeedRefresh) {
            buildMediaDevices(this.mCachedMediaDevices);
            this.mCallback.onDeviceListChanged();
            this.mNeedRefresh = false;
        }
    }

    public void registerLeBroadcastServiceCallBack(Executor executor, BluetoothLeBroadcast.Callback callback) {
        LocalBluetoothLeBroadcast leAudioBroadcastProfile = this.mLocalBluetoothManager.getProfileManager().getLeAudioBroadcastProfile();
        if (leAudioBroadcastProfile == null) {
            Log.d("MediaOutputController", "The broadcast profile is null");
        } else {
            leAudioBroadcastProfile.registerServiceCallBack(executor, callback);
        }
    }

    public void releaseSession() {
        this.mMetricLogger.logInteractionStopCasting();
        this.mLocalMediaManager.releaseSession();
    }

    public boolean removeDeviceFromPlayMedia(MediaDevice mediaDevice) {
        return this.mLocalMediaManager.removeDeviceFromPlayMedia(mediaDevice);
    }

    public void setBroadcastCode(String str) {
        LocalBluetoothLeBroadcast leAudioBroadcastProfile = this.mLocalBluetoothManager.getProfileManager().getLeAudioBroadcastProfile();
        if (leAudioBroadcastProfile == null) {
            Log.d("MediaOutputController", "setBroadcastCode: LE Audio Broadcast is null");
        } else {
            leAudioBroadcastProfile.setBroadcastCode(str.getBytes(StandardCharsets.UTF_8));
        }
    }

    public void setBroadcastName(String str) {
        LocalBluetoothLeBroadcast leAudioBroadcastProfile = this.mLocalBluetoothManager.getProfileManager().getLeAudioBroadcastProfile();
        if (leAudioBroadcastProfile == null) {
            Log.d("MediaOutputController", "setBroadcastName: LE Audio Broadcast is null");
        } else {
            leAudioBroadcastProfile.setProgramInfo(str);
        }
    }

    public void setColorFilter(Drawable drawable, boolean z) {
        drawable.setColorFilter(new PorterDuffColorFilter(this.mColorItemContent, PorterDuff.Mode.SRC_IN));
    }

    public void setCurrentColorScheme(WallpaperColors wallpaperColors, boolean z) {
        ColorScheme colorScheme = new ColorScheme(wallpaperColors, z);
        if (z) {
            this.mColorItemContent = colorScheme.getAccent1().get(2).intValue();
            this.mColorSeekbarProgress = colorScheme.getAccent2().get(7).intValue();
            this.mColorButtonBackground = colorScheme.getAccent1().get(4).intValue();
            this.mColorItemBackground = colorScheme.getNeutral2().get(9).intValue();
            this.mColorConnectedItemBackground = colorScheme.getAccent2().get(9).intValue();
            this.mColorPositiveButtonText = colorScheme.getAccent2().get(9).intValue();
            this.mColorDialogBackground = colorScheme.getNeutral1().get(10).intValue();
            return;
        }
        this.mColorItemContent = colorScheme.getAccent1().get(9).intValue();
        this.mColorSeekbarProgress = colorScheme.getAccent1().get(4).intValue();
        this.mColorButtonBackground = colorScheme.getAccent1().get(7).intValue();
        this.mColorItemBackground = colorScheme.getAccent2().get(1).intValue();
        this.mColorConnectedItemBackground = colorScheme.getAccent1().get(2).intValue();
        this.mColorPositiveButtonText = colorScheme.getNeutral1().get(1).intValue();
        this.mColorDialogBackground = colorScheme.getBackgroundColor();
    }

    public void setRefreshing(boolean z) {
        this.mIsRefreshing = z;
    }

    public void setTemporaryAllowListExceptionIfNeeded(MediaDevice mediaDevice) {
        String str;
        PowerExemptionManager powerExemptionManager = this.mPowerExemptionManager;
        if (powerExemptionManager == null || (str = this.mPackageName) == null) {
            Log.w("MediaOutputController", "powerExemptionManager or package name is null");
        } else {
            powerExemptionManager.addToTemporaryAllowList(str, 325, "mediaoutput:remote_transfer", 20000L);
        }
    }

    public boolean shouldShowLaunchSection() {
        return false;
    }

    public void start(Callback callback) {
        synchronized (this.mMediaDevicesLock) {
            this.mCachedMediaDevices.clear();
            this.mMediaDevices.clear();
            this.mMediaItemList.clear();
        }
        this.mNearbyDeviceInfoMap.clear();
        NearbyMediaDevicesManager nearbyMediaDevicesManager = this.mNearbyMediaDevicesManager;
        if (nearbyMediaDevicesManager != null) {
            nearbyMediaDevicesManager.registerNearbyDevicesCallback(this);
        }
        if (!TextUtils.isEmpty(this.mPackageName)) {
            Iterator<MediaController> it = this.mMediaSessionManager.getActiveSessions(null).iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                MediaController next = it.next();
                if (TextUtils.equals(next.getPackageName(), this.mPackageName)) {
                    this.mMediaController = next;
                    next.unregisterCallback(this.mCb);
                    if (this.mMediaController.getPlaybackState() != null) {
                        this.mCurrentState = this.mMediaController.getPlaybackState().getState();
                    }
                    this.mMediaController.registerCallback(this.mCb);
                }
            }
        }
        if (this.mMediaController == null && DEBUG) {
            Log.d("MediaOutputController", "No media controller for " + this.mPackageName);
        }
        this.mCallback = callback;
        this.mLocalMediaManager.registerCallback(this);
        this.mLocalMediaManager.startScan();
    }

    public boolean startBluetoothLeBroadcast() {
        LocalBluetoothLeBroadcast leAudioBroadcastProfile = this.mLocalBluetoothManager.getProfileManager().getLeAudioBroadcastProfile();
        if (leAudioBroadcastProfile == null) {
            Log.d("MediaOutputController", "The broadcast profile is null");
            return false;
        }
        leAudioBroadcastProfile.startBroadcast(getAppSourceName(), null);
        return true;
    }

    public void stop() {
        MediaController mediaController = this.mMediaController;
        if (mediaController != null) {
            mediaController.unregisterCallback(this.mCb);
        }
        this.mLocalMediaManager.unregisterCallback(this);
        this.mLocalMediaManager.stopScan();
        synchronized (this.mMediaDevicesLock) {
            this.mCachedMediaDevices.clear();
            this.mMediaDevices.clear();
            this.mMediaItemList.clear();
        }
        NearbyMediaDevicesManager nearbyMediaDevicesManager = this.mNearbyMediaDevicesManager;
        if (nearbyMediaDevicesManager != null) {
            nearbyMediaDevicesManager.unregisterNearbyDevicesCallback(this);
        }
        this.mNearbyDeviceInfoMap.clear();
    }

    public boolean stopBluetoothLeBroadcast() {
        LocalBluetoothLeBroadcast leAudioBroadcastProfile = this.mLocalBluetoothManager.getProfileManager().getLeAudioBroadcastProfile();
        if (leAudioBroadcastProfile == null) {
            Log.d("MediaOutputController", "The broadcast profile is null");
            return false;
        }
        leAudioBroadcastProfile.stopLatestBroadcast();
        return true;
    }

    public void unregisterLeBroadcastServiceCallBack(BluetoothLeBroadcast.Callback callback) {
        LocalBluetoothLeBroadcast leAudioBroadcastProfile = this.mLocalBluetoothManager.getProfileManager().getLeAudioBroadcastProfile();
        if (leAudioBroadcastProfile == null) {
            Log.d("MediaOutputController", "The broadcast profile is null");
        } else {
            leAudioBroadcastProfile.unregisterServiceCallBack(callback);
        }
    }

    public boolean updateBluetoothLeBroadcast() {
        LocalBluetoothLeBroadcast leAudioBroadcastProfile = this.mLocalBluetoothManager.getProfileManager().getLeAudioBroadcastProfile();
        if (leAudioBroadcastProfile == null) {
            Log.d("MediaOutputController", "The broadcast profile is null");
            return false;
        }
        leAudioBroadcastProfile.updateBroadcast(getAppSourceName(), null);
        return true;
    }
}