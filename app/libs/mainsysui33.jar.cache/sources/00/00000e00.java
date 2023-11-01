package com.android.settingslib.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothLeAudioContentMetadata;
import android.bluetooth.BluetoothLeBroadcast;
import android.bluetooth.BluetoothLeBroadcastMetadata;
import android.bluetooth.BluetoothLeBroadcastSubgroup;
import android.bluetooth.BluetoothProfile;
import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Predicate;

/* loaded from: mainsysui33.jar:com/android/settingslib/bluetooth/LocalBluetoothLeBroadcast.class */
public class LocalBluetoothLeBroadcast implements LocalBluetoothProfile {
    public static final Uri[] SETTINGS_URIS = {Settings.Secure.getUriFor("bluetooth_le_broadcast_program_info"), Settings.Secure.getUriFor("bluetooth_le_broadcast_code"), Settings.Secure.getUriFor("bluetooth_le_broadcast_app_source_name")};
    public BluetoothLeAudioContentMetadata mBluetoothLeAudioContentMetadata;
    public BluetoothLeBroadcastMetadata mBluetoothLeBroadcastMetadata;
    public final BluetoothLeBroadcast.Callback mBroadcastCallback;
    public byte[] mBroadcastCode;
    public BluetoothLeAudioContentMetadata.Builder mBuilder;
    public ContentResolver mContentResolver;
    public Executor mExecutor;
    public boolean mIsProfileReady;
    public String mProgramInfo;
    public BluetoothLeBroadcast mService;
    public final BluetoothProfile.ServiceListener mServiceListener;
    public ContentObserver mSettingsObserver;
    public int mBroadcastId = -1;
    public String mAppSourceName = "";
    public String mNewAppSourceName = "";

    /* loaded from: mainsysui33.jar:com/android/settingslib/bluetooth/LocalBluetoothLeBroadcast$BroadcastSettingsObserver.class */
    public class BroadcastSettingsObserver extends ContentObserver {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public BroadcastSettingsObserver(Handler handler) {
            super(handler);
            LocalBluetoothLeBroadcast.this = r4;
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z) {
            Log.d("LocalBluetoothLeBroadcast", "BroadcastSettingsObserver: onChange");
            LocalBluetoothLeBroadcast.this.updateBroadcastInfoFromContentProvider();
        }
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.settingslib.bluetooth.LocalBluetoothLeBroadcast$$ExternalSyntheticLambda0.test(java.lang.Object):boolean] */
    /* renamed from: $r8$lambda$vSXWvLyjKCuQkBE4wqbacF-iB9k */
    public static /* synthetic */ boolean m1016$r8$lambda$vSXWvLyjKCuQkBE4wqbacFiB9k(LocalBluetoothLeBroadcast localBluetoothLeBroadcast, BluetoothLeBroadcastMetadata bluetoothLeBroadcastMetadata) {
        return localBluetoothLeBroadcast.lambda$getLatestBluetoothLeBroadcastMetadata$0(bluetoothLeBroadcastMetadata);
    }

    public LocalBluetoothLeBroadcast(Context context) {
        BluetoothProfile.ServiceListener serviceListener = new BluetoothProfile.ServiceListener() { // from class: com.android.settingslib.bluetooth.LocalBluetoothLeBroadcast.1
            {
                LocalBluetoothLeBroadcast.this = this;
            }

            @Override // android.bluetooth.BluetoothProfile.ServiceListener
            public void onServiceConnected(int i, BluetoothProfile bluetoothProfile) {
                Log.d("LocalBluetoothLeBroadcast", "Bluetooth service connected");
                if (LocalBluetoothLeBroadcast.this.mIsProfileReady) {
                    return;
                }
                LocalBluetoothLeBroadcast.this.mService = (BluetoothLeBroadcast) bluetoothProfile;
                LocalBluetoothLeBroadcast.this.mIsProfileReady = true;
                LocalBluetoothLeBroadcast localBluetoothLeBroadcast = LocalBluetoothLeBroadcast.this;
                localBluetoothLeBroadcast.registerServiceCallBack(localBluetoothLeBroadcast.mExecutor, LocalBluetoothLeBroadcast.this.mBroadcastCallback);
                List<BluetoothLeBroadcastMetadata> allBroadcastMetadata = LocalBluetoothLeBroadcast.this.getAllBroadcastMetadata();
                if (!allBroadcastMetadata.isEmpty()) {
                    LocalBluetoothLeBroadcast.this.updateBroadcastInfoFromBroadcastMetadata(allBroadcastMetadata.get(0));
                }
                LocalBluetoothLeBroadcast.this.registerContentObserver();
            }

            @Override // android.bluetooth.BluetoothProfile.ServiceListener
            public void onServiceDisconnected(int i) {
                Log.d("LocalBluetoothLeBroadcast", "Bluetooth service disconnected");
                if (LocalBluetoothLeBroadcast.this.mIsProfileReady) {
                    LocalBluetoothLeBroadcast.this.mIsProfileReady = false;
                    LocalBluetoothLeBroadcast localBluetoothLeBroadcast = LocalBluetoothLeBroadcast.this;
                    localBluetoothLeBroadcast.unregisterServiceCallBack(localBluetoothLeBroadcast.mBroadcastCallback);
                    LocalBluetoothLeBroadcast.this.unregisterContentObserver();
                }
            }
        };
        this.mServiceListener = serviceListener;
        this.mBroadcastCallback = new BluetoothLeBroadcast.Callback() { // from class: com.android.settingslib.bluetooth.LocalBluetoothLeBroadcast.2
            {
                LocalBluetoothLeBroadcast.this = this;
            }

            public void onBroadcastMetadataChanged(int i, BluetoothLeBroadcastMetadata bluetoothLeBroadcastMetadata) {
                Log.d("LocalBluetoothLeBroadcast", "onBroadcastMetadataChanged(), broadcastId = " + i);
                LocalBluetoothLeBroadcast.this.setLatestBluetoothLeBroadcastMetadata(bluetoothLeBroadcastMetadata);
            }

            public void onBroadcastStartFailed(int i) {
                Log.d("LocalBluetoothLeBroadcast", "onBroadcastStartFailed(), reason = " + i);
            }

            public void onBroadcastStarted(int i, int i2) {
                Log.d("LocalBluetoothLeBroadcast", "onBroadcastStarted(), reason = " + i + ", broadcastId = " + i2);
                LocalBluetoothLeBroadcast.this.setLatestBroadcastId(i2);
                LocalBluetoothLeBroadcast localBluetoothLeBroadcast = LocalBluetoothLeBroadcast.this;
                localBluetoothLeBroadcast.setAppSourceName(localBluetoothLeBroadcast.mNewAppSourceName, true);
            }

            public void onBroadcastStopFailed(int i) {
                Log.d("LocalBluetoothLeBroadcast", "onBroadcastStopFailed(), reason = " + i);
            }

            public void onBroadcastStopped(int i, int i2) {
                Log.d("LocalBluetoothLeBroadcast", "onBroadcastStopped(), reason = " + i + ", broadcastId = " + i2);
                LocalBluetoothLeBroadcast.this.resetCacheInfo();
            }

            public void onBroadcastUpdateFailed(int i, int i2) {
                Log.d("LocalBluetoothLeBroadcast", "onBroadcastUpdateFailed(), reason = " + i + ", broadcastId = " + i2);
            }

            public void onBroadcastUpdated(int i, int i2) {
                Log.d("LocalBluetoothLeBroadcast", "onBroadcastUpdated(), reason = " + i + ", broadcastId = " + i2);
                LocalBluetoothLeBroadcast.this.setLatestBroadcastId(i2);
                LocalBluetoothLeBroadcast localBluetoothLeBroadcast = LocalBluetoothLeBroadcast.this;
                localBluetoothLeBroadcast.setAppSourceName(localBluetoothLeBroadcast.mNewAppSourceName, true);
            }

            public void onPlaybackStarted(int i, int i2) {
            }

            public void onPlaybackStopped(int i, int i2) {
            }
        };
        this.mExecutor = Executors.newSingleThreadExecutor();
        this.mBuilder = new BluetoothLeAudioContentMetadata.Builder();
        this.mContentResolver = context.getContentResolver();
        this.mSettingsObserver = new BroadcastSettingsObserver(new Handler(Looper.getMainLooper()));
        updateBroadcastInfoFromContentProvider();
        BluetoothAdapter.getDefaultAdapter().getProfileProxy(context, serviceListener, 26);
    }

    public /* synthetic */ boolean lambda$getLatestBluetoothLeBroadcastMetadata$0(BluetoothLeBroadcastMetadata bluetoothLeBroadcastMetadata) {
        return bluetoothLeBroadcastMetadata.getBroadcastId() == this.mBroadcastId;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public boolean accessProfileEnabled() {
        return false;
    }

    public final void buildContentMetadata(String str, String str2) {
        this.mBluetoothLeAudioContentMetadata = this.mBuilder.setLanguage(str).setProgramInfo(str2).build();
    }

    public void finalize() {
        Log.d("LocalBluetoothLeBroadcast", "finalize()");
        if (this.mService != null) {
            try {
                BluetoothAdapter.getDefaultAdapter().closeProfileProxy(26, this.mService);
                this.mService = null;
            } catch (Throwable th) {
                Log.w("LocalBluetoothLeBroadcast", "Error cleaning up LeAudio proxy", th);
            }
        }
    }

    public final String generateRandomPassword() {
        String uuid = UUID.randomUUID().toString();
        return uuid.substring(0, 8) + uuid.substring(9, 13);
    }

    public List<BluetoothLeBroadcastMetadata> getAllBroadcastMetadata() {
        BluetoothLeBroadcast bluetoothLeBroadcast = this.mService;
        if (bluetoothLeBroadcast == null) {
            Log.d("LocalBluetoothLeBroadcast", "The BluetoothLeBroadcast is null.");
            return Collections.emptyList();
        }
        return bluetoothLeBroadcast.getAllBroadcastMetadata();
    }

    public String getAppSourceName() {
        return this.mAppSourceName;
    }

    public byte[] getBroadcastCode() {
        return this.mBroadcastCode;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public int getConnectionStatus(BluetoothDevice bluetoothDevice) {
        BluetoothLeBroadcast bluetoothLeBroadcast = this.mService;
        if (bluetoothLeBroadcast == null) {
            return 0;
        }
        return bluetoothLeBroadcast.getConnectionState(bluetoothDevice);
    }

    public final byte[] getDefaultValueOfBroadcastCode() {
        return generateRandomPassword().getBytes(StandardCharsets.UTF_8);
    }

    public final String getDefaultValueOfProgramInfo() {
        int nextInt = ThreadLocalRandom.current().nextInt(1000, 9999);
        return BluetoothAdapter.getDefaultAdapter().getName() + "_" + nextInt;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public int getDrawableResource(BluetoothClass bluetoothClass) {
        return 0;
    }

    public BluetoothLeBroadcastMetadata getLatestBluetoothLeBroadcastMetadata() {
        BluetoothLeBroadcast bluetoothLeBroadcast = this.mService;
        if (bluetoothLeBroadcast == null) {
            Log.d("LocalBluetoothLeBroadcast", "The BluetoothLeBroadcast is null");
            return null;
        }
        if (this.mBluetoothLeBroadcastMetadata == null) {
            this.mBluetoothLeBroadcastMetadata = (BluetoothLeBroadcastMetadata) bluetoothLeBroadcast.getAllBroadcastMetadata().stream().filter(new Predicate() { // from class: com.android.settingslib.bluetooth.LocalBluetoothLeBroadcast$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return LocalBluetoothLeBroadcast.m1016$r8$lambda$vSXWvLyjKCuQkBE4wqbacFiB9k(LocalBluetoothLeBroadcast.this, (BluetoothLeBroadcastMetadata) obj);
                }
            }).findFirst().orElse(null);
        }
        return this.mBluetoothLeBroadcastMetadata;
    }

    public LocalBluetoothLeBroadcastMetadata getLocalBluetoothLeBroadcastMetaData() {
        BluetoothLeBroadcastMetadata latestBluetoothLeBroadcastMetadata = getLatestBluetoothLeBroadcastMetadata();
        if (latestBluetoothLeBroadcastMetadata == null) {
            Log.d("LocalBluetoothLeBroadcast", "The BluetoothLeBroadcastMetadata is null.");
            return null;
        }
        return new LocalBluetoothLeBroadcastMetadata(latestBluetoothLeBroadcastMetadata);
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public int getProfileId() {
        return 26;
    }

    public String getProgramInfo() {
        return this.mProgramInfo;
    }

    public boolean isEnabled(BluetoothDevice bluetoothDevice) {
        BluetoothLeBroadcast bluetoothLeBroadcast = this.mService;
        if (bluetoothLeBroadcast == null) {
            return false;
        }
        return !bluetoothLeBroadcast.getAllBroadcastMetadata().isEmpty();
    }

    public final void registerContentObserver() {
        if (this.mContentResolver == null) {
            Log.d("LocalBluetoothLeBroadcast", "mContentResolver is null");
            return;
        }
        for (Uri uri : SETTINGS_URIS) {
            this.mContentResolver.registerContentObserver(uri, false, this.mSettingsObserver);
        }
    }

    public void registerServiceCallBack(Executor executor, BluetoothLeBroadcast.Callback callback) {
        BluetoothLeBroadcast bluetoothLeBroadcast = this.mService;
        if (bluetoothLeBroadcast == null) {
            Log.d("LocalBluetoothLeBroadcast", "The BluetoothLeBroadcast is null.");
        } else {
            bluetoothLeBroadcast.registerCallback(executor, callback);
        }
    }

    public final void resetCacheInfo() {
        Log.d("LocalBluetoothLeBroadcast", "resetCacheInfo:");
        setAppSourceName("", true);
        this.mBluetoothLeBroadcastMetadata = null;
        this.mBroadcastId = -1;
    }

    public final void setAppSourceName(String str, boolean z) {
        String str2 = str;
        if (TextUtils.isEmpty(str)) {
            str2 = "";
        }
        String str3 = this.mAppSourceName;
        if (str3 != null && TextUtils.equals(str3, str2)) {
            Log.d("LocalBluetoothLeBroadcast", "setAppSourceName: appSourceName is not changed");
            return;
        }
        this.mAppSourceName = str2;
        this.mNewAppSourceName = "";
        if (z) {
            ContentResolver contentResolver = this.mContentResolver;
            if (contentResolver == null) {
                Log.d("LocalBluetoothLeBroadcast", "mContentResolver is null");
            } else {
                Settings.Secure.putString(contentResolver, "bluetooth_le_broadcast_app_source_name", str2);
            }
        }
    }

    public void setBroadcastCode(byte[] bArr) {
        setBroadcastCode(bArr, true);
    }

    public final void setBroadcastCode(byte[] bArr, boolean z) {
        if (bArr == null) {
            Log.d("LocalBluetoothLeBroadcast", "setBroadcastCode: broadcastCode is null");
            return;
        }
        byte[] bArr2 = this.mBroadcastCode;
        if (bArr2 != null && Arrays.equals(bArr, bArr2)) {
            Log.d("LocalBluetoothLeBroadcast", "setBroadcastCode: broadcastCode is not changed");
            return;
        }
        this.mBroadcastCode = bArr;
        if (z) {
            ContentResolver contentResolver = this.mContentResolver;
            if (contentResolver == null) {
                Log.d("LocalBluetoothLeBroadcast", "mContentResolver is null");
            } else {
                Settings.Secure.putString(contentResolver, "bluetooth_le_broadcast_code", new String(bArr, StandardCharsets.UTF_8));
            }
        }
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public boolean setEnabled(BluetoothDevice bluetoothDevice, boolean z) {
        return false;
    }

    public final void setLatestBluetoothLeBroadcastMetadata(BluetoothLeBroadcastMetadata bluetoothLeBroadcastMetadata) {
        if (bluetoothLeBroadcastMetadata == null || bluetoothLeBroadcastMetadata.getBroadcastId() != this.mBroadcastId) {
            return;
        }
        this.mBluetoothLeBroadcastMetadata = bluetoothLeBroadcastMetadata;
        updateBroadcastInfoFromBroadcastMetadata(bluetoothLeBroadcastMetadata);
    }

    public final void setLatestBroadcastId(int i) {
        Log.d("LocalBluetoothLeBroadcast", "setLatestBroadcastId: mBroadcastId is " + i);
        this.mBroadcastId = i;
    }

    public void setProgramInfo(String str) {
        setProgramInfo(str, true);
    }

    public final void setProgramInfo(String str, boolean z) {
        if (TextUtils.isEmpty(str)) {
            Log.d("LocalBluetoothLeBroadcast", "setProgramInfo: programInfo is null or empty");
            return;
        }
        String str2 = this.mProgramInfo;
        if (str2 != null && TextUtils.equals(str2, str)) {
            Log.d("LocalBluetoothLeBroadcast", "setProgramInfo: programInfo is not changed");
            return;
        }
        Log.d("LocalBluetoothLeBroadcast", "setProgramInfo: " + str);
        this.mProgramInfo = str;
        if (z) {
            ContentResolver contentResolver = this.mContentResolver;
            if (contentResolver == null) {
                Log.d("LocalBluetoothLeBroadcast", "mContentResolver is null");
            } else {
                Settings.Secure.putString(contentResolver, "bluetooth_le_broadcast_program_info", str);
            }
        }
    }

    public void startBroadcast(String str, String str2) {
        this.mNewAppSourceName = str;
        if (this.mService == null) {
            Log.d("LocalBluetoothLeBroadcast", "The BluetoothLeBroadcast is null when starting the broadcast.");
            return;
        }
        String programInfo = getProgramInfo();
        Log.d("LocalBluetoothLeBroadcast", "startBroadcast: language = " + str2 + " ,programInfo = " + programInfo);
        buildContentMetadata(str2, programInfo);
        this.mService.startBroadcast(this.mBluetoothLeAudioContentMetadata, this.mBroadcastCode);
    }

    public void stopBroadcast(int i) {
        if (this.mService == null) {
            Log.d("LocalBluetoothLeBroadcast", "The BluetoothLeBroadcast is null when stopping the broadcast.");
            return;
        }
        Log.d("LocalBluetoothLeBroadcast", "stopBroadcast()");
        this.mService.stopBroadcast(i);
    }

    public void stopLatestBroadcast() {
        stopBroadcast(this.mBroadcastId);
    }

    public String toString() {
        return "LE_AUDIO_BROADCAST";
    }

    public final void unregisterContentObserver() {
        ContentResolver contentResolver = this.mContentResolver;
        if (contentResolver == null) {
            Log.d("LocalBluetoothLeBroadcast", "mContentResolver is null");
        } else {
            contentResolver.unregisterContentObserver(this.mSettingsObserver);
        }
    }

    public void unregisterServiceCallBack(BluetoothLeBroadcast.Callback callback) {
        BluetoothLeBroadcast bluetoothLeBroadcast = this.mService;
        if (bluetoothLeBroadcast == null) {
            Log.d("LocalBluetoothLeBroadcast", "The BluetoothLeBroadcast is null.");
        } else {
            bluetoothLeBroadcast.unregisterCallback(callback);
        }
    }

    public void updateBroadcast(String str, String str2) {
        if (this.mService == null) {
            Log.d("LocalBluetoothLeBroadcast", "The BluetoothLeBroadcast is null when updating the broadcast.");
            return;
        }
        String programInfo = getProgramInfo();
        Log.d("LocalBluetoothLeBroadcast", "updateBroadcast: language = " + str2 + " ,programInfo = " + programInfo);
        this.mNewAppSourceName = str;
        BluetoothLeAudioContentMetadata build = this.mBuilder.setProgramInfo(programInfo).build();
        this.mBluetoothLeAudioContentMetadata = build;
        this.mService.updateBroadcast(this.mBroadcastId, build);
    }

    public final void updateBroadcastInfoFromBroadcastMetadata(BluetoothLeBroadcastMetadata bluetoothLeBroadcastMetadata) {
        if (bluetoothLeBroadcastMetadata == null) {
            Log.d("LocalBluetoothLeBroadcast", "The bluetoothLeBroadcastMetadata is null");
            return;
        }
        setBroadcastCode(bluetoothLeBroadcastMetadata.getBroadcastCode());
        setLatestBroadcastId(bluetoothLeBroadcastMetadata.getBroadcastId());
        List subgroups = bluetoothLeBroadcastMetadata.getSubgroups();
        if (subgroups == null || subgroups.size() < 1) {
            Log.d("LocalBluetoothLeBroadcast", "The subgroup is not valid value");
            return;
        }
        setProgramInfo(((BluetoothLeBroadcastSubgroup) subgroups.get(0)).getContentMetadata().getProgramInfo());
        setAppSourceName(getAppSourceName(), true);
    }

    public final void updateBroadcastInfoFromContentProvider() {
        ContentResolver contentResolver = this.mContentResolver;
        if (contentResolver == null) {
            Log.d("LocalBluetoothLeBroadcast", "updateBroadcastInfoFromContentProvider: mContentResolver is null");
            return;
        }
        String string = Settings.Secure.getString(contentResolver, "bluetooth_le_broadcast_program_info");
        String str = string;
        if (string == null) {
            str = getDefaultValueOfProgramInfo();
        }
        setProgramInfo(str, false);
        String string2 = Settings.Secure.getString(this.mContentResolver, "bluetooth_le_broadcast_code");
        setBroadcastCode(string2 == null ? getDefaultValueOfBroadcastCode() : string2.getBytes(StandardCharsets.UTF_8), false);
        setAppSourceName(Settings.Secure.getString(this.mContentResolver, "bluetooth_le_broadcast_app_source_name"), false);
    }
}