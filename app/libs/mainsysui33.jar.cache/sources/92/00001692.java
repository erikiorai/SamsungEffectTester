package com.android.systemui.doze;

import android.app.IWallpaperManager;
import android.os.RemoteException;
import android.util.Log;
import com.android.systemui.doze.DozeMachine;
import com.android.systemui.statusbar.phone.BiometricUnlockController;
import com.android.systemui.statusbar.phone.DozeParameters;
import java.io.PrintWriter;

/* loaded from: mainsysui33.jar:com/android/systemui/doze/DozeWallpaperState.class */
public class DozeWallpaperState implements DozeMachine.Part {
    public static final boolean DEBUG = Log.isLoggable("DozeWallpaperState", 3);
    public final BiometricUnlockController mBiometricUnlockController;
    public final DozeParameters mDozeParameters;
    public boolean mIsAmbientMode;
    public final IWallpaperManager mWallpaperManagerService;

    /* renamed from: com.android.systemui.doze.DozeWallpaperState$1  reason: invalid class name */
    /* loaded from: mainsysui33.jar:com/android/systemui/doze/DozeWallpaperState$1.class */
    public static /* synthetic */ class AnonymousClass1 {
        public static final /* synthetic */ int[] $SwitchMap$com$android$systemui$doze$DozeMachine$State;

        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:21:0x0071 -> B:49:0x0014). Please submit an issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:23:0x0075 -> B:45:0x001f). Please submit an issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:25:0x0079 -> B:41:0x002a). Please submit an issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:27:0x007d -> B:53:0x0035). Please submit an issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:29:0x0081 -> B:47:0x0040). Please submit an issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:31:0x0085 -> B:43:0x004c). Please submit an issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:33:0x0089 -> B:39:0x0058). Please submit an issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:35:0x008d -> B:51:0x0064). Please submit an issue!!! */
        static {
            int[] iArr = new int[DozeMachine.State.values().length];
            $SwitchMap$com$android$systemui$doze$DozeMachine$State = iArr;
            try {
                iArr[DozeMachine.State.DOZE.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$android$systemui$doze$DozeMachine$State[DozeMachine.State.DOZE_AOD.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$android$systemui$doze$DozeMachine$State[DozeMachine.State.DOZE_AOD_DOCKED.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$android$systemui$doze$DozeMachine$State[DozeMachine.State.DOZE_AOD_PAUSING.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$android$systemui$doze$DozeMachine$State[DozeMachine.State.DOZE_AOD_PAUSED.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$com$android$systemui$doze$DozeMachine$State[DozeMachine.State.DOZE_REQUEST_PULSE.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$com$android$systemui$doze$DozeMachine$State[DozeMachine.State.DOZE_PULSE_DONE.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
            try {
                $SwitchMap$com$android$systemui$doze$DozeMachine$State[DozeMachine.State.DOZE_PULSING.ordinal()] = 8;
            } catch (NoSuchFieldError e8) {
            }
            try {
                $SwitchMap$com$android$systemui$doze$DozeMachine$State[DozeMachine.State.DOZE_PULSING_BRIGHT.ordinal()] = 9;
            } catch (NoSuchFieldError e9) {
            }
        }
    }

    public DozeWallpaperState(IWallpaperManager iWallpaperManager, BiometricUnlockController biometricUnlockController, DozeParameters dozeParameters) {
        this.mWallpaperManagerService = iWallpaperManager;
        this.mBiometricUnlockController = biometricUnlockController;
        this.mDozeParameters = dozeParameters;
    }

    @Override // com.android.systemui.doze.DozeMachine.Part
    public void dump(PrintWriter printWriter) {
        printWriter.println("DozeWallpaperState:");
        printWriter.println(" isAmbientMode: " + this.mIsAmbientMode);
        StringBuilder sb = new StringBuilder();
        sb.append(" hasWallpaperService: ");
        sb.append(this.mWallpaperManagerService != null);
        printWriter.println(sb.toString());
    }

    @Override // com.android.systemui.doze.DozeMachine.Part
    public void transitionTo(DozeMachine.State state, DozeMachine.State state2) {
        boolean z;
        boolean z2 = false;
        switch (AnonymousClass1.$SwitchMap$com$android$systemui$doze$DozeMachine$State[state2.ordinal()]) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
                z = true;
                break;
            default:
                z = false;
                break;
        }
        if (z) {
            z2 = this.mDozeParameters.shouldControlScreenOff();
        } else {
            boolean z3 = state == DozeMachine.State.DOZE_PULSING && state2 == DozeMachine.State.FINISH;
            if (((!this.mDozeParameters.getDisplayNeedsBlanking()) && !this.mBiometricUnlockController.unlockedByWakeAndUnlock()) || z3) {
                z2 = true;
            }
        }
        if (z != this.mIsAmbientMode) {
            this.mIsAmbientMode = z;
            if (this.mWallpaperManagerService != null) {
                long j = z2 ? 500L : 0L;
                try {
                    if (DEBUG) {
                        Log.i("DozeWallpaperState", "AOD wallpaper state changed to: " + this.mIsAmbientMode + ", animationDuration: " + j);
                    }
                    this.mWallpaperManagerService.setInAmbientMode(this.mIsAmbientMode, j);
                } catch (RemoteException e) {
                    Log.w("DozeWallpaperState", "Cannot notify state to WallpaperManagerService: " + this.mIsAmbientMode);
                }
            }
        }
    }
}