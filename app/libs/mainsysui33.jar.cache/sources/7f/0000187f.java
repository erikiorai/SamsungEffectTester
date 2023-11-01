package com.android.systemui.keyboard;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.content.Context;
import android.content.DialogInterface;
import android.hardware.input.InputManager;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Pair;
import android.widget.Toast;
import com.android.settingslib.bluetooth.BluetoothCallback;
import com.android.settingslib.bluetooth.BluetoothUtils;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.settingslib.bluetooth.CachedBluetoothDeviceManager;
import com.android.settingslib.bluetooth.LocalBluetoothAdapter;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import com.android.settingslib.bluetooth.LocalBluetoothProfileManager;
import com.android.systemui.CoreStartable;
import com.android.systemui.R$string;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/keyboard/KeyboardUI.class */
public class KeyboardUI implements CoreStartable, InputManager.OnTabletModeChangedListener {
    public final Provider<LocalBluetoothManager> mBluetoothManagerProvider;
    public boolean mBootCompleted;
    public long mBootCompletedTime;
    public CachedBluetoothDeviceManager mCachedDeviceManager;
    public volatile Context mContext;
    public BluetoothDialog mDialog;
    public boolean mEnabled;
    public volatile KeyboardHandler mHandler;
    public String mKeyboardName;
    public LocalBluetoothAdapter mLocalBluetoothAdapter;
    public LocalBluetoothProfileManager mProfileManager;
    public ScanCallback mScanCallback;
    public int mState;
    public volatile KeyboardUIHandler mUIHandler;
    public int mInTabletMode = -1;
    public int mScanAttempt = 0;

    /* loaded from: mainsysui33.jar:com/android/systemui/keyboard/KeyboardUI$BluetoothCallbackHandler.class */
    public final class BluetoothCallbackHandler implements BluetoothCallback {
        public BluetoothCallbackHandler() {
        }

        @Override // com.android.settingslib.bluetooth.BluetoothCallback
        public void onBluetoothStateChanged(int i) {
            KeyboardUI.this.mHandler.obtainMessage(4, i, 0).sendToTarget();
        }

        @Override // com.android.settingslib.bluetooth.BluetoothCallback
        public void onDeviceBondStateChanged(CachedBluetoothDevice cachedBluetoothDevice, int i) {
            KeyboardUI.this.mHandler.obtainMessage(5, i, 0, cachedBluetoothDevice).sendToTarget();
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/keyboard/KeyboardUI$BluetoothDialogClickListener.class */
    public final class BluetoothDialogClickListener implements DialogInterface.OnClickListener {
        public BluetoothDialogClickListener() {
        }

        @Override // android.content.DialogInterface.OnClickListener
        public void onClick(DialogInterface dialogInterface, int i) {
            KeyboardUI.this.mHandler.obtainMessage(3, -1 == i ? 1 : 0, 0).sendToTarget();
            KeyboardUI.this.mDialog = null;
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/keyboard/KeyboardUI$BluetoothDialogDismissListener.class */
    public final class BluetoothDialogDismissListener implements DialogInterface.OnDismissListener {
        public BluetoothDialogDismissListener() {
        }

        @Override // android.content.DialogInterface.OnDismissListener
        public void onDismiss(DialogInterface dialogInterface) {
            KeyboardUI.this.mDialog = null;
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/keyboard/KeyboardUI$BluetoothErrorListener.class */
    public final class BluetoothErrorListener implements BluetoothUtils.ErrorListener {
        public BluetoothErrorListener() {
        }

        @Override // com.android.settingslib.bluetooth.BluetoothUtils.ErrorListener
        public void onShowError(Context context, String str, int i) {
            KeyboardUI.this.mHandler.obtainMessage(11, i, 0, new Pair(context, str)).sendToTarget();
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/keyboard/KeyboardUI$KeyboardHandler.class */
    public final class KeyboardHandler extends Handler {
        public KeyboardHandler(Looper looper) {
            super(looper, null, true);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            switch (message.what) {
                case 0:
                    KeyboardUI.this.init();
                    return;
                case 1:
                    KeyboardUI.this.onBootCompletedInternal();
                    return;
                case 2:
                    KeyboardUI.this.processKeyboardState();
                    return;
                case 3:
                    boolean z = true;
                    if (message.arg1 != 1) {
                        z = false;
                    }
                    if (z) {
                        KeyboardUI.this.mLocalBluetoothAdapter.enable();
                        return;
                    } else {
                        KeyboardUI.this.mState = 8;
                        return;
                    }
                case 4:
                    KeyboardUI.this.onBluetoothStateChangedInternal(message.arg1);
                    return;
                case 5:
                    KeyboardUI.this.onDeviceBondStateChangedInternal((CachedBluetoothDevice) message.obj, message.arg1);
                    return;
                case 6:
                    KeyboardUI.this.onDeviceAddedInternal(KeyboardUI.this.getCachedBluetoothDevice((BluetoothDevice) message.obj));
                    return;
                case 7:
                    KeyboardUI.this.onBleScanFailedInternal();
                    return;
                case 8:
                case 9:
                default:
                    return;
                case 10:
                    KeyboardUI.this.bleAbortScanInternal(message.arg1);
                    return;
                case 11:
                    Pair pair = (Pair) message.obj;
                    KeyboardUI.this.onShowErrorInternal((Context) pair.first, (String) pair.second, message.arg1);
                    return;
            }
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/keyboard/KeyboardUI$KeyboardScanCallback.class */
    public final class KeyboardScanCallback extends ScanCallback {
        public KeyboardScanCallback() {
        }

        public final boolean isDeviceDiscoverable(ScanResult scanResult) {
            return (scanResult.getScanRecord().getAdvertiseFlags() & 3) != 0;
        }

        @Override // android.bluetooth.le.ScanCallback
        public void onBatchScanResults(List<ScanResult> list) {
            BluetoothDevice bluetoothDevice = null;
            int i = Integer.MIN_VALUE;
            for (ScanResult scanResult : list) {
                if (isDeviceDiscoverable(scanResult) && scanResult.getRssi() > i) {
                    bluetoothDevice = scanResult.getDevice();
                    i = scanResult.getRssi();
                }
            }
            if (bluetoothDevice != null) {
                KeyboardUI.this.mHandler.obtainMessage(6, bluetoothDevice).sendToTarget();
            }
        }

        @Override // android.bluetooth.le.ScanCallback
        public void onScanFailed(int i) {
            KeyboardUI.this.mHandler.obtainMessage(7).sendToTarget();
        }

        @Override // android.bluetooth.le.ScanCallback
        public void onScanResult(int i, ScanResult scanResult) {
            if (isDeviceDiscoverable(scanResult)) {
                KeyboardUI.this.mHandler.obtainMessage(6, scanResult.getDevice()).sendToTarget();
            }
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/keyboard/KeyboardUI$KeyboardUIHandler.class */
    public final class KeyboardUIHandler extends Handler {
        public KeyboardUIHandler() {
            super(Looper.getMainLooper(), null, true);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            int i = message.what;
            if (i != 8) {
                if (i == 9 && KeyboardUI.this.mDialog != null) {
                    KeyboardUI.this.mDialog.dismiss();
                }
            } else if (KeyboardUI.this.mDialog != null) {
            } else {
                BluetoothDialogClickListener bluetoothDialogClickListener = new BluetoothDialogClickListener();
                DialogInterface.OnDismissListener bluetoothDialogDismissListener = new BluetoothDialogDismissListener();
                KeyboardUI.this.mDialog = new BluetoothDialog(KeyboardUI.this.mContext);
                KeyboardUI.this.mDialog.setTitle(R$string.enable_bluetooth_title);
                KeyboardUI.this.mDialog.setMessage(R$string.enable_bluetooth_message);
                KeyboardUI.this.mDialog.setPositiveButton(R$string.enable_bluetooth_confirmation_ok, bluetoothDialogClickListener);
                KeyboardUI.this.mDialog.setNegativeButton(17039360, bluetoothDialogClickListener);
                KeyboardUI.this.mDialog.setOnDismissListener(bluetoothDialogDismissListener);
                KeyboardUI.this.mDialog.show();
            }
        }
    }

    public KeyboardUI(Context context, Provider<LocalBluetoothManager> provider) {
        this.mContext = context;
        this.mBluetoothManagerProvider = provider;
    }

    public static String stateToString(int i) {
        switch (i) {
            case -1:
                return "STATE_NOT_ENABLED";
            case 0:
            default:
                return "STATE_UNKNOWN (" + i + ")";
            case 1:
                return "STATE_WAITING_FOR_BOOT_COMPLETED";
            case 2:
                return "STATE_WAITING_FOR_TABLET_MODE_EXIT";
            case 3:
                return "STATE_WAITING_FOR_DEVICE_DISCOVERY";
            case 4:
                return "STATE_WAITING_FOR_BLUETOOTH";
            case 5:
                return "STATE_PAIRING";
            case 6:
                return "STATE_PAIRED";
            case 7:
                return "STATE_PAIRING_FAILED";
            case 8:
                return "STATE_USER_CANCELLED";
            case 9:
                return "STATE_DEVICE_NOT_FOUND";
        }
    }

    public final void bleAbortScanInternal(int i) {
        if (this.mState == 3 && i == this.mScanAttempt) {
            stopScanning();
            this.mState = 9;
        }
    }

    @Override // com.android.systemui.CoreStartable, com.android.systemui.Dumpable
    public void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println("KeyboardUI:");
        printWriter.println("  mEnabled=" + this.mEnabled);
        printWriter.println("  mBootCompleted=" + this.mEnabled);
        printWriter.println("  mBootCompletedTime=" + this.mBootCompletedTime);
        printWriter.println("  mKeyboardName=" + this.mKeyboardName);
        printWriter.println("  mInTabletMode=" + this.mInTabletMode);
        printWriter.println("  mState=" + stateToString(this.mState));
    }

    public final CachedBluetoothDevice getCachedBluetoothDevice(BluetoothDevice bluetoothDevice) {
        CachedBluetoothDevice findDevice = this.mCachedDeviceManager.findDevice(bluetoothDevice);
        CachedBluetoothDevice cachedBluetoothDevice = findDevice;
        if (findDevice == null) {
            cachedBluetoothDevice = this.mCachedDeviceManager.addDevice(bluetoothDevice);
        }
        return cachedBluetoothDevice;
    }

    public final CachedBluetoothDevice getDiscoveredKeyboard() {
        for (CachedBluetoothDevice cachedBluetoothDevice : this.mCachedDeviceManager.getCachedDevicesCopy()) {
            if (cachedBluetoothDevice.getName().equals(this.mKeyboardName)) {
                return cachedBluetoothDevice;
            }
        }
        return null;
    }

    public final CachedBluetoothDevice getPairedKeyboard() {
        for (BluetoothDevice bluetoothDevice : this.mLocalBluetoothAdapter.getBondedDevices()) {
            if (this.mKeyboardName.equals(bluetoothDevice.getName())) {
                return getCachedBluetoothDevice(bluetoothDevice);
            }
        }
        return null;
    }

    public final void init() {
        LocalBluetoothManager localBluetoothManager;
        Context context = this.mContext;
        String string = context.getString(17040024);
        this.mKeyboardName = string;
        if (TextUtils.isEmpty(string) || (localBluetoothManager = (LocalBluetoothManager) this.mBluetoothManagerProvider.get()) == null) {
            return;
        }
        this.mEnabled = true;
        this.mCachedDeviceManager = localBluetoothManager.getCachedDeviceManager();
        this.mLocalBluetoothAdapter = localBluetoothManager.getBluetoothAdapter();
        this.mProfileManager = localBluetoothManager.getProfileManager();
        localBluetoothManager.getEventManager().registerCallback(new BluetoothCallbackHandler());
        BluetoothUtils.setErrorListener(new BluetoothErrorListener());
        InputManager inputManager = (InputManager) context.getSystemService(InputManager.class);
        inputManager.registerOnTabletModeChangedListener(this, this.mHandler);
        this.mInTabletMode = inputManager.isInTabletMode();
        processKeyboardState();
        this.mUIHandler = new KeyboardUIHandler();
    }

    public final boolean isUserSetupComplete() {
        boolean z = false;
        if (Settings.Secure.getIntForUser(this.mContext.getContentResolver(), "user_setup_complete", 0, -2) != 0) {
            z = true;
        }
        return z;
    }

    public final void onBleScanFailedInternal() {
        this.mScanCallback = null;
        if (this.mState == 3) {
            this.mState = 9;
        }
    }

    public final void onBluetoothStateChangedInternal(int i) {
        if (i == 12 && this.mState == 4) {
            processKeyboardState();
        }
    }

    @Override // com.android.systemui.CoreStartable
    public void onBootCompleted() {
        this.mHandler.sendEmptyMessage(1);
    }

    public void onBootCompletedInternal() {
        this.mBootCompleted = true;
        this.mBootCompletedTime = SystemClock.uptimeMillis();
        if (this.mState == 1) {
            processKeyboardState();
        }
    }

    public final void onDeviceAddedInternal(CachedBluetoothDevice cachedBluetoothDevice) {
        if (this.mState == 3 && cachedBluetoothDevice.getName().equals(this.mKeyboardName)) {
            stopScanning();
            cachedBluetoothDevice.startPairing();
            this.mState = 5;
        }
    }

    public final void onDeviceBondStateChangedInternal(CachedBluetoothDevice cachedBluetoothDevice, int i) {
        if (this.mState == 5 && cachedBluetoothDevice.getName().equals(this.mKeyboardName)) {
            if (i == 12) {
                this.mState = 6;
            } else if (i == 10) {
                this.mState = 7;
            }
        }
    }

    public final void onShowErrorInternal(Context context, String str, int i) {
        int i2 = this.mState;
        if ((i2 == 5 || i2 == 7) && this.mKeyboardName.equals(str)) {
            Toast.makeText(context, context.getString(i, str), 0).show();
        }
    }

    public void onTabletModeChanged(long j, boolean z) {
        if ((!z || this.mInTabletMode == 1) && (z || this.mInTabletMode == 0)) {
            return;
        }
        this.mInTabletMode = z ? 1 : 0;
        processKeyboardState();
    }

    public final void processKeyboardState() {
        this.mHandler.removeMessages(2);
        if (!this.mEnabled) {
            this.mState = -1;
        } else if (!this.mBootCompleted) {
            this.mState = 1;
        } else if (this.mInTabletMode != 0) {
            int i = this.mState;
            if (i == 3) {
                stopScanning();
            } else if (i == 4) {
                this.mUIHandler.sendEmptyMessage(9);
            }
            this.mState = 2;
        } else {
            int state = this.mLocalBluetoothAdapter.getState();
            if ((state == 11 || state == 12) && this.mState == 4) {
                this.mUIHandler.sendEmptyMessage(9);
            }
            if (state == 11) {
                this.mState = 4;
            } else if (state != 12) {
                this.mState = 4;
                showBluetoothDialog();
            } else {
                CachedBluetoothDevice pairedKeyboard = getPairedKeyboard();
                int i2 = this.mState;
                if (i2 == 2 || i2 == 4) {
                    if (pairedKeyboard != null) {
                        this.mState = 6;
                        pairedKeyboard.connect(false);
                        return;
                    }
                    this.mCachedDeviceManager.clearNonBondedDevices();
                }
                CachedBluetoothDevice discoveredKeyboard = getDiscoveredKeyboard();
                if (discoveredKeyboard != null) {
                    this.mState = 5;
                    discoveredKeyboard.startPairing();
                    return;
                }
                this.mState = 3;
                startScanning();
            }
        }
    }

    public final void showBluetoothDialog() {
        if (!isUserSetupComplete()) {
            this.mLocalBluetoothAdapter.enable();
            return;
        }
        long uptimeMillis = SystemClock.uptimeMillis();
        long j = this.mBootCompletedTime + 10000;
        if (j < uptimeMillis) {
            this.mUIHandler.sendEmptyMessage(8);
        } else {
            this.mHandler.sendEmptyMessageAtTime(2, j);
        }
    }

    @Override // com.android.systemui.CoreStartable
    public void start() {
        HandlerThread handlerThread = new HandlerThread("Keyboard", 10);
        handlerThread.start();
        this.mHandler = new KeyboardHandler(handlerThread.getLooper());
        this.mHandler.sendEmptyMessage(0);
    }

    public final void startScanning() {
        BluetoothLeScanner bluetoothLeScanner = this.mLocalBluetoothAdapter.getBluetoothLeScanner();
        ScanFilter build = new ScanFilter.Builder().setDeviceName(this.mKeyboardName).build();
        ScanSettings build2 = new ScanSettings.Builder().setCallbackType(1).setNumOfMatches(1).setScanMode(2).setReportDelay(0L).build();
        this.mScanCallback = new KeyboardScanCallback();
        bluetoothLeScanner.startScan(Arrays.asList(build), build2, this.mScanCallback);
        KeyboardHandler keyboardHandler = this.mHandler;
        int i = this.mScanAttempt + 1;
        this.mScanAttempt = i;
        this.mHandler.sendMessageDelayed(keyboardHandler.obtainMessage(10, i, 0), 30000L);
    }

    public final void stopScanning() {
        if (this.mScanCallback != null) {
            BluetoothLeScanner bluetoothLeScanner = this.mLocalBluetoothAdapter.getBluetoothLeScanner();
            if (bluetoothLeScanner != null) {
                bluetoothLeScanner.stopScan(this.mScanCallback);
            }
            this.mScanCallback = null;
        }
    }
}