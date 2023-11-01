package com.android.systemui.qs.tileimpl;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import androidx.constraintlayout.widget.R$styleable;
import com.android.systemui.plugins.BcSmartspaceDataPlugin;
import com.android.systemui.plugins.qs.QSFactory;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.plugins.qs.QSTileView;
import com.android.systemui.qs.QSHost;
import com.android.systemui.qs.external.CustomTile;
import com.android.systemui.qs.tiles.AODTile;
import com.android.systemui.qs.tiles.AirplaneModeTile;
import com.android.systemui.qs.tiles.AlarmTile;
import com.android.systemui.qs.tiles.AmbientDisplayTile;
import com.android.systemui.qs.tiles.AntiFlickerTile;
import com.android.systemui.qs.tiles.BatterySaverTile;
import com.android.systemui.qs.tiles.BluetoothTile;
import com.android.systemui.qs.tiles.CaffeineTile;
import com.android.systemui.qs.tiles.CameraToggleTile;
import com.android.systemui.qs.tiles.CastTile;
import com.android.systemui.qs.tiles.CellularTile;
import com.android.systemui.qs.tiles.ColorCorrectionTile;
import com.android.systemui.qs.tiles.ColorInversionTile;
import com.android.systemui.qs.tiles.DataSaverTile;
import com.android.systemui.qs.tiles.DeviceControlsTile;
import com.android.systemui.qs.tiles.DndTile;
import com.android.systemui.qs.tiles.DreamTile;
import com.android.systemui.qs.tiles.FlashlightTile;
import com.android.systemui.qs.tiles.HeadsUpTile;
import com.android.systemui.qs.tiles.HotspotTile;
import com.android.systemui.qs.tiles.InternetTile;
import com.android.systemui.qs.tiles.LiveDisplayTile;
import com.android.systemui.qs.tiles.LocationTile;
import com.android.systemui.qs.tiles.MicrophoneToggleTile;
import com.android.systemui.qs.tiles.NfcTile;
import com.android.systemui.qs.tiles.NightDisplayTile;
import com.android.systemui.qs.tiles.OneHandedModeTile;
import com.android.systemui.qs.tiles.PowerShareTile;
import com.android.systemui.qs.tiles.QRCodeScannerTile;
import com.android.systemui.qs.tiles.QuickAccessWalletTile;
import com.android.systemui.qs.tiles.ReadingModeTile;
import com.android.systemui.qs.tiles.ReduceBrightColorsTile;
import com.android.systemui.qs.tiles.RotationLockTile;
import com.android.systemui.qs.tiles.ScreenRecordTile;
import com.android.systemui.qs.tiles.SyncTile;
import com.android.systemui.qs.tiles.UiModeNightTile;
import com.android.systemui.qs.tiles.UsbTetherTile;
import com.android.systemui.qs.tiles.VpnTile;
import com.android.systemui.qs.tiles.WifiTile;
import com.android.systemui.qs.tiles.WorkModeTile;
import com.android.systemui.util.leak.GarbageMonitor;
import dagger.Lazy;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/tileimpl/QSFactoryImpl.class */
public class QSFactoryImpl implements QSFactory {
    private static final String TAG = "QSFactory";
    private final Provider<AODTile> mAODTileProvider;
    private final Provider<AirplaneModeTile> mAirplaneModeTileProvider;
    private final Provider<AlarmTile> mAlarmTileProvider;
    private final Provider<AmbientDisplayTile> mAmbientDisplayTileProvider;
    private final Provider<AntiFlickerTile> mAntiFlickerTileProvider;
    private final Provider<BatterySaverTile> mBatterySaverTileProvider;
    private final Provider<BluetoothTile> mBluetoothTileProvider;
    private final Provider<CaffeineTile> mCaffeineTileProvider;
    private final Provider<CameraToggleTile> mCameraToggleTileProvider;
    private final Provider<CastTile> mCastTileProvider;
    private final Provider<CellularTile> mCellularTileProvider;
    private final Provider<ColorCorrectionTile> mColorCorrectionTileProvider;
    private final Provider<ColorInversionTile> mColorInversionTileProvider;
    private final Provider<CustomTile.Builder> mCustomTileBuilderProvider;
    private final Provider<DataSaverTile> mDataSaverTileProvider;
    private final Provider<DeviceControlsTile> mDeviceControlsTileProvider;
    private final Provider<DndTile> mDndTileProvider;
    private final Provider<DreamTile> mDreamTileProvider;
    private final Provider<FlashlightTile> mFlashlightTileProvider;
    private final Provider<HeadsUpTile> mHeadsUpTileProvider;
    private final Provider<HotspotTile> mHotspotTileProvider;
    private final Provider<InternetTile> mInternetTileProvider;
    private final Provider<LiveDisplayTile> mLiveDisplayTileProvider;
    private final Provider<LocationTile> mLocationTileProvider;
    private final Provider<GarbageMonitor.MemoryTile> mMemoryTileProvider;
    private final Provider<MicrophoneToggleTile> mMicrophoneToggleTileProvider;
    private final Provider<NfcTile> mNfcTileProvider;
    private final Provider<NightDisplayTile> mNightDisplayTileProvider;
    private final Provider<OneHandedModeTile> mOneHandedModeTileProvider;
    private final Provider<PowerShareTile> mPowerShareTileProvider;
    private final Provider<QRCodeScannerTile> mQRCodeScannerTileProvider;
    private final Lazy<QSHost> mQsHostLazy;
    private final Provider<QuickAccessWalletTile> mQuickAccessWalletTileProvider;
    private final Provider<ReadingModeTile> mReadingModeTileProvider;
    private final Provider<ReduceBrightColorsTile> mReduceBrightColorsTileProvider;
    private final Provider<RotationLockTile> mRotationLockTileProvider;
    private final Provider<ScreenRecordTile> mScreenRecordTileProvider;
    private final Provider<SyncTile> mSyncTileProvider;
    private final Provider<UiModeNightTile> mUiModeNightTileProvider;
    private final Provider<UsbTetherTile> mUsbTetherTileProvider;
    private final Provider<VpnTile> mVpnTileProvider;
    private final Provider<WifiTile> mWifiTileProvider;
    private final Provider<WorkModeTile> mWorkModeTileProvider;

    public QSFactoryImpl(Lazy<QSHost> lazy, Provider<CustomTile.Builder> provider, Provider<WifiTile> provider2, Provider<InternetTile> provider3, Provider<BluetoothTile> provider4, Provider<CellularTile> provider5, Provider<DndTile> provider6, Provider<ColorInversionTile> provider7, Provider<AirplaneModeTile> provider8, Provider<WorkModeTile> provider9, Provider<RotationLockTile> provider10, Provider<FlashlightTile> provider11, Provider<LocationTile> provider12, Provider<CastTile> provider13, Provider<HotspotTile> provider14, Provider<BatterySaverTile> provider15, Provider<DataSaverTile> provider16, Provider<NightDisplayTile> provider17, Provider<NfcTile> provider18, Provider<GarbageMonitor.MemoryTile> provider19, Provider<UiModeNightTile> provider20, Provider<ScreenRecordTile> provider21, Provider<ReduceBrightColorsTile> provider22, Provider<CameraToggleTile> provider23, Provider<MicrophoneToggleTile> provider24, Provider<DeviceControlsTile> provider25, Provider<AlarmTile> provider26, Provider<QuickAccessWalletTile> provider27, Provider<QRCodeScannerTile> provider28, Provider<OneHandedModeTile> provider29, Provider<ColorCorrectionTile> provider30, Provider<DreamTile> provider31, Provider<PowerShareTile> provider32, Provider<CaffeineTile> provider33, Provider<HeadsUpTile> provider34, Provider<SyncTile> provider35, Provider<AmbientDisplayTile> provider36, Provider<UsbTetherTile> provider37, Provider<AODTile> provider38, Provider<VpnTile> provider39, Provider<LiveDisplayTile> provider40, Provider<ReadingModeTile> provider41, Provider<AntiFlickerTile> provider42) {
        this.mQsHostLazy = lazy;
        this.mCustomTileBuilderProvider = provider;
        this.mWifiTileProvider = provider2;
        this.mInternetTileProvider = provider3;
        this.mBluetoothTileProvider = provider4;
        this.mCellularTileProvider = provider5;
        this.mDndTileProvider = provider6;
        this.mColorInversionTileProvider = provider7;
        this.mAirplaneModeTileProvider = provider8;
        this.mWorkModeTileProvider = provider9;
        this.mRotationLockTileProvider = provider10;
        this.mFlashlightTileProvider = provider11;
        this.mLocationTileProvider = provider12;
        this.mCastTileProvider = provider13;
        this.mHotspotTileProvider = provider14;
        this.mBatterySaverTileProvider = provider15;
        this.mDataSaverTileProvider = provider16;
        this.mNightDisplayTileProvider = provider17;
        this.mNfcTileProvider = provider18;
        this.mMemoryTileProvider = provider19;
        this.mUiModeNightTileProvider = provider20;
        this.mScreenRecordTileProvider = provider21;
        this.mReduceBrightColorsTileProvider = provider22;
        this.mCameraToggleTileProvider = provider23;
        this.mMicrophoneToggleTileProvider = provider24;
        this.mDeviceControlsTileProvider = provider25;
        this.mAlarmTileProvider = provider26;
        this.mQuickAccessWalletTileProvider = provider27;
        this.mQRCodeScannerTileProvider = provider28;
        this.mOneHandedModeTileProvider = provider29;
        this.mColorCorrectionTileProvider = provider30;
        this.mDreamTileProvider = provider31;
        this.mPowerShareTileProvider = provider32;
        this.mCaffeineTileProvider = provider33;
        this.mHeadsUpTileProvider = provider34;
        this.mSyncTileProvider = provider35;
        this.mAmbientDisplayTileProvider = provider36;
        this.mUsbTetherTileProvider = provider37;
        this.mAODTileProvider = provider38;
        this.mVpnTileProvider = provider39;
        this.mLiveDisplayTileProvider = provider40;
        this.mReadingModeTileProvider = provider41;
        this.mAntiFlickerTileProvider = provider42;
    }

    @Override // com.android.systemui.plugins.qs.QSFactory
    public final QSTile createTile(String str) {
        QSTileImpl createTileInternal = createTileInternal(str);
        if (createTileInternal != null) {
            createTileInternal.initialize();
            createTileInternal.postStale();
        }
        return createTileInternal;
    }

    public QSTileImpl createTileInternal(String str) {
        str.hashCode();
        boolean z = true;
        switch (str.hashCode()) {
            case -2109393100:
                if (str.equals("onehanded")) {
                    z = false;
                    break;
                }
                break;
            case -2103993829:
                if (str.equals("ambient_display")) {
                    z = true;
                    break;
                }
                break;
            case -2016941037:
                if (str.equals("inversion")) {
                    z = true;
                    break;
                }
                break;
            case -1319150730:
                if (str.equals("reading_mode")) {
                    z = true;
                    break;
                }
                break;
            case -1183073498:
                if (str.equals("flashlight")) {
                    z = true;
                    break;
                }
                break;
            case -1180580266:
                if (str.equals("livedisplay")) {
                    z = true;
                    break;
                }
                break;
            case -1114859577:
                if (str.equals("heads_up")) {
                    z = true;
                    break;
                }
                break;
            case -998626789:
                if (str.equals("anti_flicker")) {
                    z = true;
                    break;
                }
                break;
            case -805491779:
                if (str.equals("screenrecord")) {
                    z = true;
                    break;
                }
                break;
            case -795192327:
                if (str.equals("wallet")) {
                    z = true;
                    break;
                }
                break;
            case -677011630:
                if (str.equals("airplane")) {
                    z = true;
                    break;
                }
                break;
            case -657925702:
                if (str.equals("color_correction")) {
                    z = true;
                    break;
                }
                break;
            case -566933834:
                if (str.equals("controls")) {
                    z = true;
                    break;
                }
                break;
            case -349438983:
                if (str.equals("caffeine")) {
                    z = true;
                    break;
                }
                break;
            case -343519030:
                if (str.equals("reduce_brightness")) {
                    z = true;
                    break;
                }
                break;
            case -331239923:
                if (str.equals("battery")) {
                    z = true;
                    break;
                }
                break;
            case -40300674:
                if (str.equals("rotation")) {
                    z = true;
                    break;
                }
                break;
            case -37334949:
                if (str.equals("mictoggle")) {
                    z = true;
                    break;
                }
                break;
            case 3154:
                if (str.equals("bt")) {
                    z = true;
                    break;
                }
                break;
            case 96758:
                if (str.equals("aod")) {
                    z = true;
                    break;
                }
                break;
            case 99610:
                if (str.equals("dnd")) {
                    z = true;
                    break;
                }
                break;
            case 108971:
                if (str.equals("nfc")) {
                    z = true;
                    break;
                }
                break;
            case 116980:
                if (str.equals("vpn")) {
                    z = true;
                    break;
                }
                break;
            case 3046207:
                if (str.equals("cast")) {
                    z = true;
                    break;
                }
                break;
            case 3049826:
                if (str.equals("cell")) {
                    z = true;
                    break;
                }
                break;
            case 3075958:
                if (str.equals("dark")) {
                    z = true;
                    break;
                }
                break;
            case 3545755:
                if (str.equals("sync")) {
                    z = true;
                    break;
                }
                break;
            case 3649301:
                if (str.equals("wifi")) {
                    z = true;
                    break;
                }
                break;
            case 3655441:
                if (str.equals("work")) {
                    z = true;
                    break;
                }
                break;
            case 6344377:
                if (str.equals("cameratoggle")) {
                    z = true;
                    break;
                }
                break;
            case 92895825:
                if (str.equals("alarm")) {
                    z = true;
                    break;
                }
                break;
            case 95848451:
                if (str.equals(BcSmartspaceDataPlugin.UI_SURFACE_DREAM)) {
                    z = true;
                    break;
                }
                break;
            case 104817688:
                if (str.equals("night")) {
                    z = true;
                    break;
                }
                break;
            case 109211285:
                if (str.equals("saver")) {
                    z = true;
                    break;
                }
                break;
            case 459055610:
                if (str.equals("powershare")) {
                    z = true;
                    break;
                }
                break;
            case 570410817:
                if (str.equals("internet")) {
                    z = true;
                    break;
                }
                break;
            case 876619530:
                if (str.equals("qr_code_scanner")) {
                    z = true;
                    break;
                }
                break;
            case 1099603663:
                if (str.equals("hotspot")) {
                    z = true;
                    break;
                }
                break;
            case 1901043637:
                if (str.equals("location")) {
                    z = true;
                    break;
                }
                break;
            case 1906931501:
                if (str.equals("usb_tether")) {
                    z = true;
                    break;
                }
                break;
        }
        switch (z) {
            case false:
                return (QSTileImpl) this.mOneHandedModeTileProvider.get();
            case true:
                return (QSTileImpl) this.mAmbientDisplayTileProvider.get();
            case true:
                return (QSTileImpl) this.mColorInversionTileProvider.get();
            case true:
                return (QSTileImpl) this.mReadingModeTileProvider.get();
            case true:
                return (QSTileImpl) this.mFlashlightTileProvider.get();
            case true:
                return (QSTileImpl) this.mLiveDisplayTileProvider.get();
            case true:
                return (QSTileImpl) this.mHeadsUpTileProvider.get();
            case true:
                return (QSTileImpl) this.mAntiFlickerTileProvider.get();
            case true:
                return (QSTileImpl) this.mScreenRecordTileProvider.get();
            case true:
                return (QSTileImpl) this.mQuickAccessWalletTileProvider.get();
            case true:
                return (QSTileImpl) this.mAirplaneModeTileProvider.get();
            case true:
                return (QSTileImpl) this.mColorCorrectionTileProvider.get();
            case true:
                return (QSTileImpl) this.mDeviceControlsTileProvider.get();
            case true:
                return (QSTileImpl) this.mCaffeineTileProvider.get();
            case true:
                return (QSTileImpl) this.mReduceBrightColorsTileProvider.get();
            case true:
                return (QSTileImpl) this.mBatterySaverTileProvider.get();
            case true:
                return (QSTileImpl) this.mRotationLockTileProvider.get();
            case true:
                return (QSTileImpl) this.mMicrophoneToggleTileProvider.get();
            case true:
                return (QSTileImpl) this.mBluetoothTileProvider.get();
            case true:
                return (QSTileImpl) this.mAODTileProvider.get();
            case true:
                return (QSTileImpl) this.mDndTileProvider.get();
            case true:
                return (QSTileImpl) this.mNfcTileProvider.get();
            case true:
                return (QSTileImpl) this.mVpnTileProvider.get();
            case true:
                return (QSTileImpl) this.mCastTileProvider.get();
            case true:
                return (QSTileImpl) this.mCellularTileProvider.get();
            case true:
                return (QSTileImpl) this.mUiModeNightTileProvider.get();
            case true:
                return (QSTileImpl) this.mSyncTileProvider.get();
            case true:
                return (QSTileImpl) this.mWifiTileProvider.get();
            case true:
                return (QSTileImpl) this.mWorkModeTileProvider.get();
            case true:
                return (QSTileImpl) this.mCameraToggleTileProvider.get();
            case true:
                return (QSTileImpl) this.mAlarmTileProvider.get();
            case true:
                return (QSTileImpl) this.mDreamTileProvider.get();
            case true:
                return (QSTileImpl) this.mNightDisplayTileProvider.get();
            case true:
                return (QSTileImpl) this.mDataSaverTileProvider.get();
            case true:
                return (QSTileImpl) this.mPowerShareTileProvider.get();
            case true:
                return (QSTileImpl) this.mInternetTileProvider.get();
            case R$styleable.ConstraintLayout_Layout_constraint_referenced_tags /* 36 */:
                return (QSTileImpl) this.mQRCodeScannerTileProvider.get();
            case true:
                return (QSTileImpl) this.mHotspotTileProvider.get();
            case true:
                return (QSTileImpl) this.mLocationTileProvider.get();
            case true:
                return (QSTileImpl) this.mUsbTetherTileProvider.get();
            default:
                if (str.startsWith("custom(")) {
                    return CustomTile.create((CustomTile.Builder) this.mCustomTileBuilderProvider.get(), str, ((QSHost) this.mQsHostLazy.get()).getUserContext());
                }
                if (Build.IS_DEBUGGABLE && str.equals("dbg:mem")) {
                    return (QSTileImpl) this.mMemoryTileProvider.get();
                }
                Log.w(TAG, "No stock tile spec: " + str);
                return null;
        }
    }

    @Override // com.android.systemui.plugins.qs.QSFactory
    public QSTileView createTileView(Context context, QSTile qSTile, boolean z) {
        return new QSTileViewImpl(context, qSTile.createTileView(context), z);
    }
}