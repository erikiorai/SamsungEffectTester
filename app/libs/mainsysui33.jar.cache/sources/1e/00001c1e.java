package com.android.systemui.media.controls.pipeline;

import android.bluetooth.BluetoothLeBroadcast;
import android.bluetooth.BluetoothLeBroadcastMetadata;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.MediaRouter2Manager;
import android.media.RoutingSessionInfo;
import android.media.session.MediaController;
import android.media.session.MediaSession;
import android.text.TextUtils;
import android.util.Log;
import com.android.settingslib.bluetooth.LocalBluetoothLeBroadcast;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import com.android.settingslib.bluetooth.LocalBluetoothProfileManager;
import com.android.settingslib.media.LocalMediaManager;
import com.android.settingslib.media.MediaDevice;
import com.android.systemui.Dumpable;
import com.android.systemui.R$drawable;
import com.android.systemui.R$string;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.media.controls.models.player.MediaData;
import com.android.systemui.media.controls.models.player.MediaDeviceData;
import com.android.systemui.media.controls.models.recommendation.SmartspaceMediaData;
import com.android.systemui.media.controls.pipeline.MediaDataManager;
import com.android.systemui.media.controls.pipeline.MediaDeviceManager;
import com.android.systemui.media.controls.util.MediaControllerFactory;
import com.android.systemui.media.controls.util.MediaDataUtils;
import com.android.systemui.media.muteawait.MediaMuteAwaitConnectionManager;
import com.android.systemui.media.muteawait.MediaMuteAwaitConnectionManagerFactory;
import com.android.systemui.statusbar.policy.ConfigurationController;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executor;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: mainsysui33.jar:com/android/systemui/media/controls/pipeline/MediaDeviceManager.class */
public final class MediaDeviceManager implements MediaDataManager.Listener, Dumpable {
    public final Executor bgExecutor;
    public final ConfigurationController configurationController;
    public final Context context;
    public final MediaControllerFactory controllerFactory;
    public final Executor fgExecutor;
    public final LocalBluetoothManager localBluetoothManager;
    public final LocalMediaManagerFactory localMediaManagerFactory;
    public final MediaRouter2Manager mr2manager;
    public final MediaMuteAwaitConnectionManagerFactory muteAwaitConnectionManagerFactory;
    public final Set<Listener> listeners = new LinkedHashSet();
    public final Map<String, Entry> entries = new LinkedHashMap();

    /* loaded from: mainsysui33.jar:com/android/systemui/media/controls/pipeline/MediaDeviceManager$Entry.class */
    public final class Entry extends MediaController.Callback implements LocalMediaManager.DeviceCallback, BluetoothLeBroadcast.Callback {
        public AboutToConnectDevice aboutToConnectDeviceOverride;
        public String broadcastDescription;
        public final MediaDeviceManager$Entry$configListener$1 configListener = new ConfigurationController.ConfigurationListener() { // from class: com.android.systemui.media.controls.pipeline.MediaDeviceManager$Entry$configListener$1
            public void onLocaleListChanged() {
                MediaDeviceManager.Entry.access$updateCurrent(MediaDeviceManager.Entry.this);
            }
        };
        public final MediaController controller;
        public MediaDeviceData current;
        public final String key;
        public final LocalMediaManager localMediaManager;
        public final MediaMuteAwaitConnectionManager muteAwaitConnectionManager;
        public final String oldKey;
        public int playbackType;
        public boolean started;

        /* JADX WARN: Type inference failed for: r1v6, types: [com.android.systemui.media.controls.pipeline.MediaDeviceManager$Entry$configListener$1] */
        public Entry(String str, String str2, MediaController mediaController, LocalMediaManager localMediaManager, MediaMuteAwaitConnectionManager mediaMuteAwaitConnectionManager) {
            MediaDeviceManager.this = r6;
            this.key = str;
            this.oldKey = str2;
            this.controller = mediaController;
            this.localMediaManager = localMediaManager;
            this.muteAwaitConnectionManager = mediaMuteAwaitConnectionManager;
        }

        /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.controls.pipeline.MediaDeviceManager$Entry$configListener$1.onLocaleListChanged():void, com.android.systemui.media.controls.pipeline.MediaDeviceManager$Entry$onDeviceListUpdate$1.run():void, com.android.systemui.media.controls.pipeline.MediaDeviceManager$Entry$onSelectedDeviceStateChanged$1.run():void] */
        public static final /* synthetic */ void access$updateCurrent(Entry entry) {
            entry.updateCurrent();
        }

        public final void dump(PrintWriter printWriter) {
            MediaController mediaController = this.controller;
            RoutingSessionInfo routingSessionForMediaController = mediaController != null ? MediaDeviceManager.this.mr2manager.getRoutingSessionForMediaController(mediaController) : null;
            List selectedRoutes = routingSessionForMediaController != null ? MediaDeviceManager.this.mr2manager.getSelectedRoutes(routingSessionForMediaController) : null;
            MediaDeviceData mediaDeviceData = this.current;
            CharSequence name = mediaDeviceData != null ? mediaDeviceData.getName() : null;
            printWriter.println("    current device is " + ((Object) name));
            MediaController mediaController2 = this.controller;
            Integer num = null;
            if (mediaController2 != null) {
                MediaController.PlaybackInfo playbackInfo = mediaController2.getPlaybackInfo();
                num = null;
                if (playbackInfo != null) {
                    num = Integer.valueOf(playbackInfo.getPlaybackType());
                }
            }
            printWriter.println("    PlaybackType=" + num + " (1 for local, 2 for remote) cached=" + this.playbackType);
            StringBuilder sb = new StringBuilder();
            sb.append("    routingSession=");
            sb.append(routingSessionForMediaController);
            printWriter.println(sb.toString());
            printWriter.println("    selectedRoutes=" + selectedRoutes);
        }

        public final void getBroadcastingInfo(LocalBluetoothLeBroadcast localBluetoothLeBroadcast) {
            String appSourceName = localBluetoothLeBroadcast.getAppSourceName();
            if (TextUtils.equals(MediaDataUtils.getAppLabel(MediaDeviceManager.this.context, this.localMediaManager.getPackageName(), MediaDeviceManager.this.context.getString(R$string.bt_le_audio_broadcast_dialog_unknown_name)), appSourceName)) {
                this.broadcastDescription = MediaDeviceManager.this.context.getString(R$string.broadcasting_description_is_broadcasting);
            } else {
                this.broadcastDescription = appSourceName;
            }
        }

        public final MediaController getController() {
            return this.controller;
        }

        public final String getKey() {
            return this.key;
        }

        public final LocalMediaManager getLocalMediaManager() {
            return this.localMediaManager;
        }

        public final MediaMuteAwaitConnectionManager getMuteAwaitConnectionManager() {
            return this.muteAwaitConnectionManager;
        }

        public final String getOldKey() {
            return this.oldKey;
        }

        public final MediaSession.Token getToken() {
            MediaController mediaController = this.controller;
            return mediaController != null ? mediaController.getSessionToken() : null;
        }

        public final boolean isLeAudioBroadcastEnabled() {
            if (MediaDeviceManager.this.localBluetoothManager == null) {
                Log.d("MediaDeviceManager", "Can not get LocalBluetoothManager");
                return false;
            }
            LocalBluetoothProfileManager profileManager = MediaDeviceManager.this.localBluetoothManager.getProfileManager();
            if (profileManager == null) {
                Log.d("MediaDeviceManager", "Can not get LocalBluetoothProfileManager");
                return false;
            }
            LocalBluetoothLeBroadcast leAudioBroadcastProfile = profileManager.getLeAudioBroadcastProfile();
            if (leAudioBroadcastProfile == null || !leAudioBroadcastProfile.isEnabled(null)) {
                Log.d("MediaDeviceManager", "Can not get LocalBluetoothLeBroadcast");
                return false;
            }
            getBroadcastingInfo(leAudioBroadcastProfile);
            return true;
        }

        @Override // com.android.settingslib.media.LocalMediaManager.DeviceCallback
        public void onAboutToConnectDeviceAdded(String str, String str2, Drawable drawable) {
            this.aboutToConnectDeviceOverride = new AboutToConnectDevice(this.localMediaManager.getMediaDeviceById(str), new MediaDeviceData(true, drawable, str2, null, null, false, 24, null));
            updateCurrent();
        }

        @Override // com.android.settingslib.media.LocalMediaManager.DeviceCallback
        public void onAboutToConnectDeviceRemoved() {
            this.aboutToConnectDeviceOverride = null;
            updateCurrent();
        }

        @Override // android.media.session.MediaController.Callback
        public void onAudioInfoChanged(MediaController.PlaybackInfo playbackInfo) {
            int playbackType = playbackInfo != null ? playbackInfo.getPlaybackType() : 0;
            if (playbackType == this.playbackType) {
                return;
            }
            this.playbackType = playbackType;
            updateCurrent();
        }

        public void onBroadcastMetadataChanged(int i, BluetoothLeBroadcastMetadata bluetoothLeBroadcastMetadata) {
            Log.d("MediaDeviceManager", "onBroadcastMetadataChanged(), broadcastId = " + i + " , metadata = " + bluetoothLeBroadcastMetadata);
            updateCurrent();
        }

        public void onBroadcastStartFailed(int i) {
            Log.d("MediaDeviceManager", "onBroadcastStartFailed(), reason = " + i);
        }

        public void onBroadcastStarted(int i, int i2) {
            Log.d("MediaDeviceManager", "onBroadcastStarted(), reason = " + i + " , broadcastId = " + i2);
            updateCurrent();
        }

        public void onBroadcastStopFailed(int i) {
            Log.d("MediaDeviceManager", "onBroadcastStopFailed(), reason = " + i);
        }

        public void onBroadcastStopped(int i, int i2) {
            Log.d("MediaDeviceManager", "onBroadcastStopped(), reason = " + i + " , broadcastId = " + i2);
            updateCurrent();
        }

        public void onBroadcastUpdateFailed(int i, int i2) {
            Log.d("MediaDeviceManager", "onBroadcastUpdateFailed(), reason = " + i + " , broadcastId = " + i2);
        }

        public void onBroadcastUpdated(int i, int i2) {
            Log.d("MediaDeviceManager", "onBroadcastUpdated(), reason = " + i + " , broadcastId = " + i2);
            updateCurrent();
        }

        @Override // com.android.settingslib.media.LocalMediaManager.DeviceCallback
        public void onDeviceListUpdate(List<? extends MediaDevice> list) {
            MediaDeviceManager.this.bgExecutor.execute(new Runnable() { // from class: com.android.systemui.media.controls.pipeline.MediaDeviceManager$Entry$onDeviceListUpdate$1
                @Override // java.lang.Runnable
                public final void run() {
                    MediaDeviceManager.Entry.access$updateCurrent(MediaDeviceManager.Entry.this);
                }
            });
        }

        public void onPlaybackStarted(int i, int i2) {
        }

        public void onPlaybackStopped(int i, int i2) {
        }

        @Override // com.android.settingslib.media.LocalMediaManager.DeviceCallback
        public void onSelectedDeviceStateChanged(MediaDevice mediaDevice, int i) {
            MediaDeviceManager.this.bgExecutor.execute(new Runnable() { // from class: com.android.systemui.media.controls.pipeline.MediaDeviceManager$Entry$onSelectedDeviceStateChanged$1
                @Override // java.lang.Runnable
                public final void run() {
                    MediaDeviceManager.Entry.access$updateCurrent(MediaDeviceManager.Entry.this);
                }
            });
        }

        public final void setCurrent(final MediaDeviceData mediaDeviceData) {
            boolean z = mediaDeviceData != null && mediaDeviceData.equalsWithoutIcon(this.current);
            if (this.started && z) {
                return;
            }
            this.current = mediaDeviceData;
            Executor executor = MediaDeviceManager.this.fgExecutor;
            final MediaDeviceManager mediaDeviceManager = MediaDeviceManager.this;
            executor.execute(new Runnable() { // from class: com.android.systemui.media.controls.pipeline.MediaDeviceManager$Entry$current$1
                @Override // java.lang.Runnable
                public final void run() {
                    MediaDeviceManager.access$processDevice(MediaDeviceManager.this, this.getKey(), this.getOldKey(), mediaDeviceData);
                }
            });
        }

        public final void start() {
            Executor executor = MediaDeviceManager.this.bgExecutor;
            final MediaDeviceManager mediaDeviceManager = MediaDeviceManager.this;
            executor.execute(new Runnable() { // from class: com.android.systemui.media.controls.pipeline.MediaDeviceManager$Entry$start$1
                @Override // java.lang.Runnable
                public final void run() {
                    boolean z;
                    ConfigurationController configurationController;
                    MediaDeviceManager$Entry$configListener$1 mediaDeviceManager$Entry$configListener$1;
                    MediaController.PlaybackInfo playbackInfo;
                    z = MediaDeviceManager.Entry.this.started;
                    if (z) {
                        return;
                    }
                    MediaDeviceManager.Entry.this.getLocalMediaManager().registerCallback(MediaDeviceManager.Entry.this);
                    MediaDeviceManager.Entry.this.getLocalMediaManager().startScan();
                    MediaMuteAwaitConnectionManager muteAwaitConnectionManager = MediaDeviceManager.Entry.this.getMuteAwaitConnectionManager();
                    if (muteAwaitConnectionManager != null) {
                        muteAwaitConnectionManager.startListening();
                    }
                    MediaDeviceManager.Entry entry = MediaDeviceManager.Entry.this;
                    MediaController controller = entry.getController();
                    entry.playbackType = (controller == null || (playbackInfo = controller.getPlaybackInfo()) == null) ? 0 : playbackInfo.getPlaybackType();
                    MediaController controller2 = MediaDeviceManager.Entry.this.getController();
                    if (controller2 != null) {
                        controller2.registerCallback(MediaDeviceManager.Entry.this);
                    }
                    MediaDeviceManager.Entry.this.updateCurrent();
                    MediaDeviceManager.Entry.this.started = true;
                    configurationController = mediaDeviceManager.configurationController;
                    mediaDeviceManager$Entry$configListener$1 = MediaDeviceManager.Entry.this.configListener;
                    configurationController.addCallback(mediaDeviceManager$Entry$configListener$1);
                }
            });
        }

        public final void stop() {
            Executor executor = MediaDeviceManager.this.bgExecutor;
            final MediaDeviceManager mediaDeviceManager = MediaDeviceManager.this;
            executor.execute(new Runnable() { // from class: com.android.systemui.media.controls.pipeline.MediaDeviceManager$Entry$stop$1
                @Override // java.lang.Runnable
                public final void run() {
                    boolean z;
                    ConfigurationController configurationController;
                    MediaDeviceManager$Entry$configListener$1 mediaDeviceManager$Entry$configListener$1;
                    z = MediaDeviceManager.Entry.this.started;
                    if (z) {
                        MediaDeviceManager.Entry.this.started = false;
                        MediaController controller = MediaDeviceManager.Entry.this.getController();
                        if (controller != null) {
                            controller.unregisterCallback(MediaDeviceManager.Entry.this);
                        }
                        MediaDeviceManager.Entry.this.getLocalMediaManager().stopScan();
                        MediaDeviceManager.Entry.this.getLocalMediaManager().unregisterCallback(MediaDeviceManager.Entry.this);
                        MediaMuteAwaitConnectionManager muteAwaitConnectionManager = MediaDeviceManager.Entry.this.getMuteAwaitConnectionManager();
                        if (muteAwaitConnectionManager != null) {
                            muteAwaitConnectionManager.stopListening();
                        }
                        configurationController = mediaDeviceManager.configurationController;
                        mediaDeviceManager$Entry$configListener$1 = MediaDeviceManager.Entry.this.configListener;
                        configurationController.removeCallback(mediaDeviceManager$Entry$configListener$1);
                    }
                }
            });
        }

        /* JADX WARN: Code restructure failed: missing block: B:71:0x0059, code lost:
            if (r0 == null) goto L53;
         */
        /* JADX WARN: Code restructure failed: missing block: B:96:0x00c3, code lost:
            if (r0 == null) goto L48;
         */
        /* JADX WARN: Removed duplicated region for block: B:102:0x00d3  */
        /* JADX WARN: Removed duplicated region for block: B:103:0x00dc  */
        /* JADX WARN: Removed duplicated region for block: B:106:0x00e3  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final void updateCurrent() {
            MediaDevice currentConnectedDevice;
            String name;
            CharSequence name2;
            if (isLeAudioBroadcastEnabled()) {
                setCurrent(new MediaDeviceData(true, MediaDeviceManager.this.context.getDrawable(R$drawable.settings_input_antenna), this.broadcastDescription, null, null, true, 16, null));
                return;
            }
            AboutToConnectDevice aboutToConnectDevice = this.aboutToConnectDeviceOverride;
            if (aboutToConnectDevice != null && aboutToConnectDevice.getFullMediaDevice() == null && aboutToConnectDevice.getBackupMediaDeviceData() != null) {
                setCurrent(aboutToConnectDevice.getBackupMediaDeviceData());
                return;
            }
            if (aboutToConnectDevice != null) {
                MediaDevice fullMediaDevice = aboutToConnectDevice.getFullMediaDevice();
                currentConnectedDevice = fullMediaDevice;
            }
            currentConnectedDevice = this.localMediaManager.getCurrentConnectedDevice();
            MediaController mediaController = this.controller;
            String str = null;
            RoutingSessionInfo routingSessionForMediaController = mediaController != null ? MediaDeviceManager.this.mr2manager.getRoutingSessionForMediaController(mediaController) : null;
            boolean z = currentConnectedDevice != null && (this.controller == null || routingSessionForMediaController != null);
            if (this.controller == null || routingSessionForMediaController != null) {
                if (routingSessionForMediaController != null && (name2 = routingSessionForMediaController.getName()) != null) {
                    String obj = name2.toString();
                    name = obj;
                }
                if (currentConnectedDevice != null) {
                    name = currentConnectedDevice.getName();
                    Drawable iconWithoutBackground = currentConnectedDevice == null ? currentConnectedDevice.getIconWithoutBackground() : null;
                    if (currentConnectedDevice != null) {
                        str = currentConnectedDevice.getId();
                    }
                    setCurrent(new MediaDeviceData(z, iconWithoutBackground, name, null, str, false, 8, null));
                }
            }
            name = null;
            if (currentConnectedDevice == null) {
            }
            if (currentConnectedDevice != null) {
            }
            setCurrent(new MediaDeviceData(z, iconWithoutBackground, name, null, str, false, 8, null));
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/media/controls/pipeline/MediaDeviceManager$Listener.class */
    public interface Listener {
        void onKeyRemoved(String str);

        void onMediaDeviceChanged(String str, String str2, MediaDeviceData mediaDeviceData);
    }

    public MediaDeviceManager(Context context, MediaControllerFactory mediaControllerFactory, LocalMediaManagerFactory localMediaManagerFactory, MediaRouter2Manager mediaRouter2Manager, MediaMuteAwaitConnectionManagerFactory mediaMuteAwaitConnectionManagerFactory, ConfigurationController configurationController, LocalBluetoothManager localBluetoothManager, Executor executor, Executor executor2, DumpManager dumpManager) {
        this.context = context;
        this.controllerFactory = mediaControllerFactory;
        this.localMediaManagerFactory = localMediaManagerFactory;
        this.mr2manager = mediaRouter2Manager;
        this.muteAwaitConnectionManagerFactory = mediaMuteAwaitConnectionManagerFactory;
        this.configurationController = configurationController;
        this.localBluetoothManager = localBluetoothManager;
        this.fgExecutor = executor;
        this.bgExecutor = executor2;
        DumpManager.registerDumpable$default(dumpManager, MediaDeviceManager.class.getName(), this, null, 4, null);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.controls.pipeline.MediaDeviceManager$Entry$current$1.run():void] */
    public static final /* synthetic */ void access$processDevice(MediaDeviceManager mediaDeviceManager, String str, String str2, MediaDeviceData mediaDeviceData) {
        mediaDeviceManager.processDevice(str, str2, mediaDeviceData);
    }

    public final boolean addListener(Listener listener) {
        return this.listeners.add(listener);
    }

    @Override // com.android.systemui.Dumpable
    public void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println("MediaDeviceManager state:");
        for (Map.Entry<String, Entry> entry : this.entries.entrySet()) {
            String key = entry.getKey();
            Entry value = entry.getValue();
            printWriter.println("  key=" + key);
            value.dump(printWriter);
        }
    }

    @Override // com.android.systemui.media.controls.pipeline.MediaDataManager.Listener
    public void onMediaDataLoaded(String str, String str2, MediaData mediaData, boolean z, int i, boolean z2) {
        Entry remove;
        if (str2 != null && !Intrinsics.areEqual(str2, str) && (remove = this.entries.remove(str2)) != null) {
            remove.stop();
        }
        Entry entry = this.entries.get(str);
        if (entry == null || !Intrinsics.areEqual(entry.getToken(), mediaData.getToken())) {
            if (entry != null) {
                entry.stop();
            }
            if (mediaData.getDevice() != null) {
                processDevice(str, str2, mediaData.getDevice());
                return;
            }
            MediaSession.Token token = mediaData.getToken();
            MediaController create = token != null ? this.controllerFactory.create(token) : null;
            LocalMediaManager create2 = this.localMediaManagerFactory.create(mediaData.getPackageName());
            Entry entry2 = new Entry(str, str2, create, create2, this.muteAwaitConnectionManagerFactory.create(create2));
            this.entries.put(str, entry2);
            entry2.start();
        }
    }

    @Override // com.android.systemui.media.controls.pipeline.MediaDataManager.Listener
    public void onMediaDataRemoved(String str) {
        Entry remove = this.entries.remove(str);
        if (remove != null) {
            remove.stop();
        }
        if (remove != null) {
            for (Listener listener : this.listeners) {
                listener.onKeyRemoved(str);
            }
        }
    }

    @Override // com.android.systemui.media.controls.pipeline.MediaDataManager.Listener
    public void onSmartspaceMediaDataLoaded(String str, SmartspaceMediaData smartspaceMediaData, boolean z) {
        MediaDataManager.Listener.DefaultImpls.onSmartspaceMediaDataLoaded(this, str, smartspaceMediaData, z);
    }

    @Override // com.android.systemui.media.controls.pipeline.MediaDataManager.Listener
    public void onSmartspaceMediaDataRemoved(String str, boolean z) {
        MediaDataManager.Listener.DefaultImpls.onSmartspaceMediaDataRemoved(this, str, z);
    }

    public final void processDevice(String str, String str2, MediaDeviceData mediaDeviceData) {
        for (Listener listener : this.listeners) {
            listener.onMediaDeviceChanged(str, str2, mediaDeviceData);
        }
    }
}