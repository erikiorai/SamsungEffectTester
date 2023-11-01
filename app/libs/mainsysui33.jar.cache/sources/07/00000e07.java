package com.android.settingslib.bluetooth;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothLeAudioCodecConfigMetadata;
import android.bluetooth.BluetoothLeAudioContentMetadata;
import android.bluetooth.BluetoothLeBroadcastChannel;
import android.bluetooth.BluetoothLeBroadcastMetadata;
import android.bluetooth.BluetoothLeBroadcastSubgroup;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

/* loaded from: mainsysui33.jar:com/android/settingslib/bluetooth/LocalBluetoothLeBroadcastMetadata.class */
public class LocalBluetoothLeBroadcastMetadata {
    public byte[] mBroadcastCode;
    public int mBroadcastId;
    public boolean mIsEncrypted;
    public int mPaSyncInterval;
    public int mPresentationDelayMicros;
    public int mSourceAddressType;
    public int mSourceAdvertisingSid;
    public BluetoothDevice mSourceDevice;
    public List<BluetoothLeBroadcastSubgroup> mSubgroupList;

    public LocalBluetoothLeBroadcastMetadata(BluetoothLeBroadcastMetadata bluetoothLeBroadcastMetadata) {
        this.mSourceAddressType = bluetoothLeBroadcastMetadata.getSourceAddressType();
        this.mSourceDevice = bluetoothLeBroadcastMetadata.getSourceDevice();
        this.mSourceAdvertisingSid = bluetoothLeBroadcastMetadata.getSourceAdvertisingSid();
        this.mBroadcastId = bluetoothLeBroadcastMetadata.getBroadcastId();
        this.mPaSyncInterval = bluetoothLeBroadcastMetadata.getPaSyncInterval();
        this.mIsEncrypted = bluetoothLeBroadcastMetadata.isEncrypted();
        this.mBroadcastCode = bluetoothLeBroadcastMetadata.getBroadcastCode();
        this.mPresentationDelayMicros = bluetoothLeBroadcastMetadata.getPresentationDelayMicros();
        this.mSubgroupList = bluetoothLeBroadcastMetadata.getSubgroups();
    }

    public final String convertAudioCodecConfigToString(BluetoothLeAudioCodecConfigMetadata bluetoothLeAudioCodecConfigMetadata) {
        long audioLocation = bluetoothLeAudioCodecConfigMetadata.getAudioLocation();
        String str = new String(bluetoothLeAudioCodecConfigMetadata.getRawMetadata(), StandardCharsets.UTF_8);
        return "AL:<" + String.valueOf(audioLocation) + ">;CCRM:<" + str + ">;";
    }

    public final String convertAudioContentToString(BluetoothLeAudioContentMetadata bluetoothLeAudioContentMetadata) {
        String str = new String(bluetoothLeAudioContentMetadata.getRawMetadata(), StandardCharsets.UTF_8);
        return "PI:<" + bluetoothLeAudioContentMetadata.getProgramInfo() + ">;L:<" + bluetoothLeAudioContentMetadata.getLanguage() + ">;ACRM:<" + str + ">;";
    }

    public final String convertChannelToString(List<BluetoothLeBroadcastChannel> list) {
        StringBuilder sb = new StringBuilder();
        for (BluetoothLeBroadcastChannel bluetoothLeBroadcastChannel : list) {
            String convertAudioCodecConfigToString = convertAudioCodecConfigToString(bluetoothLeBroadcastChannel.getCodecMetadata());
            sb.append("CI:<" + bluetoothLeBroadcastChannel.getChannelIndex() + ">;BCCM:<" + convertAudioCodecConfigToString + ">;");
        }
        return sb.toString();
    }

    public final String convertSubgroupToString(List<BluetoothLeBroadcastSubgroup> list) {
        StringBuilder sb = new StringBuilder();
        for (BluetoothLeBroadcastSubgroup bluetoothLeBroadcastSubgroup : list) {
            String convertAudioCodecConfigToString = convertAudioCodecConfigToString(bluetoothLeBroadcastSubgroup.getCodecSpecificConfig());
            String convertAudioContentToString = convertAudioContentToString(bluetoothLeBroadcastSubgroup.getContentMetadata());
            String convertChannelToString = convertChannelToString(bluetoothLeBroadcastSubgroup.getChannels());
            sb.append("CID:<" + bluetoothLeBroadcastSubgroup.getCodecId() + ">;CC:<" + convertAudioCodecConfigToString + ">;AC:<" + convertAudioContentToString + ">;BC:<" + convertChannelToString + ">;");
        }
        return sb.toString();
    }

    public String convertToQrCodeString() {
        String convertSubgroupToString = convertSubgroupToString(this.mSubgroupList);
        return "BT:T:<" + this.mSourceAddressType + ">;D:<" + this.mSourceDevice + ">;AS:<" + this.mSourceAdvertisingSid + ">;B:<" + this.mBroadcastId + ">;SI:<" + this.mPaSyncInterval + ">;E:<" + this.mIsEncrypted + ">;C:<" + Arrays.toString(this.mBroadcastCode) + ">;D:<" + this.mPresentationDelayMicros + ">;G:<" + convertSubgroupToString + ">;";
    }
}