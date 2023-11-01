package com.android.systemui.qrcodescanner.controller;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.os.Handler;
import android.provider.DeviceConfig;
import android.util.Log;
import com.android.systemui.qrcodescanner.controller.QRCodeScannerController;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.statusbar.policy.CallbackController;
import com.android.systemui.util.DeviceConfigProxy;
import com.android.systemui.util.settings.SecureSettings;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/* loaded from: mainsysui33.jar:com/android/systemui/qrcodescanner/controller/QRCodeScannerController.class */
public class QRCodeScannerController implements CallbackController<Callback> {
    public final boolean mConfigEnableLockScreenButton;
    public final Context mContext;
    public final DeviceConfigProxy mDeviceConfigProxy;
    public final Executor mExecutor;
    public boolean mQRCodeScannerEnabled;
    public final SecureSettings mSecureSettings;
    public final UserTracker mUserTracker;
    public final ArrayList<Callback> mCallbacks = new ArrayList<>();
    public HashMap<Integer, ContentObserver> mQRCodeScannerPreferenceObserver = new HashMap<>();
    public DeviceConfig.OnPropertiesChangedListener mOnDefaultQRCodeScannerChangedListener = null;
    public UserTracker.Callback mUserChangedListener = null;
    public Intent mIntent = null;
    public String mQRCodeScannerActivity = null;
    public ComponentName mComponentName = null;
    public AtomicInteger mQRCodeScannerPreferenceChangeEvents = new AtomicInteger(0);
    public AtomicInteger mDefaultQRCodeScannerChangeEvents = new AtomicInteger(0);
    public Boolean mIsCameraAvailable = null;

    /* renamed from: com.android.systemui.qrcodescanner.controller.QRCodeScannerController$1  reason: invalid class name */
    /* loaded from: mainsysui33.jar:com/android/systemui/qrcodescanner/controller/QRCodeScannerController$1.class */
    public class AnonymousClass1 extends ContentObserver {
        public AnonymousClass1(Handler handler) {
            super(handler);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onChange$0() {
            QRCodeScannerController.this.updateQRCodeScannerPreferenceDetails(false);
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z) {
            QRCodeScannerController.this.mExecutor.execute(new Runnable() { // from class: com.android.systemui.qrcodescanner.controller.QRCodeScannerController$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    QRCodeScannerController.AnonymousClass1.this.lambda$onChange$0();
                }
            });
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/qrcodescanner/controller/QRCodeScannerController$Callback.class */
    public interface Callback {
        default void onQRCodeScannerActivityChanged() {
        }

        default void onQRCodeScannerPreferenceChanged() {
        }
    }

    public QRCodeScannerController(Context context, Executor executor, SecureSettings secureSettings, DeviceConfigProxy deviceConfigProxy, UserTracker userTracker) {
        this.mContext = context;
        this.mExecutor = executor;
        this.mSecureSettings = secureSettings;
        this.mDeviceConfigProxy = deviceConfigProxy;
        this.mUserTracker = userTracker;
        this.mConfigEnableLockScreenButton = context.getResources().getBoolean(17891336);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$registerDefaultQRCodeScannerObserver$4(DeviceConfig.Properties properties) {
        if ("systemui".equals(properties.getNamespace()) && properties.getKeyset().contains("default_qr_code_scanner")) {
            lambda$registerDefaultQRCodeScannerObserver$3();
            updateQRCodeScannerPreferenceDetails(true);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$registerQRCodePreferenceObserver$5() {
        updateQRCodeScannerPreferenceDetails(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$unregisterQRCodePreferenceObserver$0(Integer num, ContentObserver contentObserver) {
        this.mSecureSettings.unregisterContentObserver(contentObserver);
    }

    /* JADX DEBUG: Method merged with bridge method */
    public void addCallback(Callback callback) {
        if (isCameraAvailable()) {
            synchronized (this.mCallbacks) {
                this.mCallbacks.add(callback);
            }
        }
    }

    public final String getDefaultScannerActivity() {
        return this.mContext.getResources().getString(17039952);
    }

    public Intent getIntent() {
        return this.mIntent;
    }

    public boolean isAbleToOpenCameraApp() {
        Intent intent = this.mIntent;
        return intent != null && isActivityCallable(intent);
    }

    public final boolean isActivityAvailable(Intent intent) {
        if (intent.getComponent() == null) {
            return false;
        }
        return !this.mContext.getPackageManager().queryIntentActivities(intent, 537698816).isEmpty();
    }

    public final boolean isActivityCallable(Intent intent) {
        if (intent.getComponent() == null) {
            return false;
        }
        return !this.mContext.getPackageManager().queryIntentActivities(intent, 819200).isEmpty();
    }

    public boolean isAvailableOnDevice() {
        return this.mConfigEnableLockScreenButton;
    }

    public boolean isCameraAvailable() {
        if (this.mIsCameraAvailable == null) {
            this.mIsCameraAvailable = Boolean.valueOf(this.mContext.getPackageManager().hasSystemFeature("android.hardware.camera"));
        }
        return this.mIsCameraAvailable.booleanValue();
    }

    public boolean isEnabledForLockScreenButton() {
        return this.mQRCodeScannerEnabled && isAbleToOpenCameraApp() && isAvailableOnDevice();
    }

    public final void notifyQRCodeScannerActivityChanged() {
        ArrayList arrayList;
        synchronized (this.mCallbacks) {
            arrayList = (ArrayList) this.mCallbacks.clone();
        }
        arrayList.forEach(new Consumer() { // from class: com.android.systemui.qrcodescanner.controller.QRCodeScannerController$$ExternalSyntheticLambda5
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((QRCodeScannerController.Callback) obj).onQRCodeScannerActivityChanged();
            }
        });
    }

    public final void notifyQRCodeScannerPreferenceChanged() {
        ArrayList arrayList;
        synchronized (this.mCallbacks) {
            arrayList = (ArrayList) this.mCallbacks.clone();
        }
        arrayList.forEach(new Consumer() { // from class: com.android.systemui.qrcodescanner.controller.QRCodeScannerController$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((QRCodeScannerController.Callback) obj).onQRCodeScannerPreferenceChanged();
            }
        });
    }

    public final void registerDefaultQRCodeScannerObserver() {
        if (this.mOnDefaultQRCodeScannerChangedListener != null) {
            return;
        }
        this.mExecutor.execute(new Runnable() { // from class: com.android.systemui.qrcodescanner.controller.QRCodeScannerController$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                QRCodeScannerController.this.lambda$registerDefaultQRCodeScannerObserver$3();
            }
        });
        DeviceConfig.OnPropertiesChangedListener onPropertiesChangedListener = new DeviceConfig.OnPropertiesChangedListener() { // from class: com.android.systemui.qrcodescanner.controller.QRCodeScannerController$$ExternalSyntheticLambda2
            public final void onPropertiesChanged(DeviceConfig.Properties properties) {
                QRCodeScannerController.this.lambda$registerDefaultQRCodeScannerObserver$4(properties);
            }
        };
        this.mOnDefaultQRCodeScannerChangedListener = onPropertiesChangedListener;
        this.mDeviceConfigProxy.addOnPropertiesChangedListener("systemui", this.mExecutor, onPropertiesChangedListener);
    }

    public final void registerQRCodePreferenceObserver() {
        if (this.mConfigEnableLockScreenButton) {
            int userId = this.mUserTracker.getUserId();
            if (this.mQRCodeScannerPreferenceObserver.getOrDefault(Integer.valueOf(userId), null) != null) {
                return;
            }
            this.mExecutor.execute(new Runnable() { // from class: com.android.systemui.qrcodescanner.controller.QRCodeScannerController$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    QRCodeScannerController.this.lambda$registerQRCodePreferenceObserver$5();
                }
            });
            this.mQRCodeScannerPreferenceObserver.put(Integer.valueOf(userId), new AnonymousClass1(null));
            SecureSettings secureSettings = this.mSecureSettings;
            secureSettings.registerContentObserverForUser(secureSettings.getUriFor("lock_screen_show_qr_code_scanner"), false, this.mQRCodeScannerPreferenceObserver.get(Integer.valueOf(userId)), userId);
        }
    }

    public void registerQRCodeScannerChangeObservers(int... iArr) {
        if (isCameraAvailable()) {
            for (int i : iArr) {
                if (i == 0) {
                    this.mDefaultQRCodeScannerChangeEvents.incrementAndGet();
                    registerDefaultQRCodeScannerObserver();
                } else if (i != 1) {
                    Log.e("QRCodeScannerController", "Unrecognised event: " + i);
                } else {
                    this.mQRCodeScannerPreferenceChangeEvents.incrementAndGet();
                    registerQRCodePreferenceObserver();
                    registerUserChangeObservers();
                }
            }
        }
    }

    public final void registerUserChangeObservers() {
        if (this.mUserChangedListener != null) {
            return;
        }
        UserTracker.Callback callback = new UserTracker.Callback() { // from class: com.android.systemui.qrcodescanner.controller.QRCodeScannerController.2
            public void onUserChanged(int i, Context context) {
                QRCodeScannerController.this.registerQRCodePreferenceObserver();
                QRCodeScannerController.this.updateQRCodeScannerPreferenceDetails(true);
            }
        };
        this.mUserChangedListener = callback;
        this.mUserTracker.addCallback(callback, this.mExecutor);
    }

    /* JADX DEBUG: Method merged with bridge method */
    public void removeCallback(Callback callback) {
        if (isCameraAvailable()) {
            synchronized (this.mCallbacks) {
                this.mCallbacks.remove(callback);
            }
        }
    }

    public final void unregisterDefaultQRCodeScannerObserver() {
        this.mDeviceConfigProxy.removeOnPropertiesChangedListener(this.mOnDefaultQRCodeScannerChangedListener);
        this.mOnDefaultQRCodeScannerChangedListener = null;
        this.mQRCodeScannerActivity = null;
        this.mIntent = null;
        this.mComponentName = null;
    }

    public final void unregisterQRCodePreferenceObserver() {
        if (this.mConfigEnableLockScreenButton) {
            this.mQRCodeScannerPreferenceObserver.forEach(new BiConsumer() { // from class: com.android.systemui.qrcodescanner.controller.QRCodeScannerController$$ExternalSyntheticLambda0
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    QRCodeScannerController.this.lambda$unregisterQRCodePreferenceObserver$0((Integer) obj, (ContentObserver) obj2);
                }
            });
            this.mQRCodeScannerPreferenceObserver = new HashMap<>();
            this.mSecureSettings.putStringForUser("show_qr_code_scanner_setting", (String) null, this.mUserTracker.getUserId());
        }
    }

    public void unregisterQRCodeScannerChangeObservers(int... iArr) {
        if (isCameraAvailable()) {
            for (int i : iArr) {
                if (i != 0) {
                    if (i != 1) {
                        Log.e("QRCodeScannerController", "Unrecognised event: " + i);
                    } else if (this.mUserTracker != null && this.mQRCodeScannerPreferenceChangeEvents.decrementAndGet() == 0) {
                        unregisterQRCodePreferenceObserver();
                        unregisterUserChangeObservers();
                    }
                } else if (this.mOnDefaultQRCodeScannerChangedListener != null && this.mDefaultQRCodeScannerChangeEvents.decrementAndGet() == 0) {
                    unregisterDefaultQRCodeScannerObserver();
                }
            }
        }
    }

    public final void unregisterUserChangeObservers() {
        this.mUserTracker.removeCallback(this.mUserChangedListener);
        this.mUserChangedListener = null;
        this.mQRCodeScannerEnabled = false;
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: updateQRCodeScannerActivityDetails */
    public final void lambda$registerDefaultQRCodeScannerObserver$3() {
        ComponentName componentName;
        String string = this.mDeviceConfigProxy.getString("systemui", "default_qr_code_scanner", "");
        String str = string;
        if (Objects.equals(string, "")) {
            str = getDefaultScannerActivity();
        }
        String str2 = this.mQRCodeScannerActivity;
        Intent intent = new Intent();
        if (str != null) {
            componentName = ComponentName.unflattenFromString(str);
            intent.setComponent(componentName);
            intent.addFlags(335544320);
        } else {
            componentName = null;
        }
        if (isActivityAvailable(intent)) {
            this.mQRCodeScannerActivity = str;
            this.mComponentName = componentName;
            this.mIntent = intent;
        } else {
            this.mQRCodeScannerActivity = null;
            this.mComponentName = null;
            this.mIntent = null;
        }
        if (Objects.equals(this.mQRCodeScannerActivity, str2)) {
            return;
        }
        notifyQRCodeScannerActivityChanged();
    }

    public final void updateQRCodeScannerPreferenceDetails(boolean z) {
        if (this.mConfigEnableLockScreenButton) {
            boolean z2 = this.mQRCodeScannerEnabled;
            boolean z3 = false;
            if (this.mSecureSettings.getIntForUser("lock_screen_show_qr_code_scanner", 0, this.mUserTracker.getUserId()) != 0) {
                z3 = true;
            }
            this.mQRCodeScannerEnabled = z3;
            if (z) {
                this.mSecureSettings.putStringForUser("show_qr_code_scanner_setting", this.mQRCodeScannerActivity, this.mUserTracker.getUserId());
            }
            if (Objects.equals(Boolean.valueOf(this.mQRCodeScannerEnabled), Boolean.valueOf(z2))) {
                return;
            }
            notifyQRCodeScannerPreferenceChanged();
        }
    }
}