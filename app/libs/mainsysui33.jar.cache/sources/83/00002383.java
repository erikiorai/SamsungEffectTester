package com.android.systemui.reardisplay;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.hardware.devicestate.DeviceStateManager;
import android.hardware.devicestate.DeviceStateManagerGlobal;
import android.view.View;
import com.airbnb.lottie.LottieAnimationView;
import com.android.internal.annotations.VisibleForTesting;
import com.android.systemui.CoreStartable;
import com.android.systemui.R$id;
import com.android.systemui.R$layout;
import com.android.systemui.R$string;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import java.util.concurrent.Executor;

@SuppressLint({"VisibleForTests"})
/* loaded from: mainsysui33.jar:com/android/systemui/reardisplay/RearDisplayDialogController.class */
public class RearDisplayDialogController implements CoreStartable, CommandQueue.Callbacks {
    public final CommandQueue mCommandQueue;
    public final Context mContext;
    public DeviceStateManagerGlobal mDeviceStateManagerGlobal;
    public final Executor mExecutor;
    public int[] mFoldedStates;
    @VisibleForTesting
    public SystemUIDialog mRearDisplayEducationDialog;
    public boolean mStartedFolded;
    public boolean mServiceNotified = false;
    public int mAnimationRepeatCount = -1;
    public DeviceStateManager.DeviceStateCallback mDeviceStateManagerCallback = new DeviceStateManagerCallback();

    /* loaded from: mainsysui33.jar:com/android/systemui/reardisplay/RearDisplayDialogController$DeviceStateManagerCallback.class */
    public class DeviceStateManagerCallback implements DeviceStateManager.DeviceStateCallback {
        public DeviceStateManagerCallback() {
            RearDisplayDialogController.this = r4;
        }

        public void onBaseStateChanged(int i) {
            if (RearDisplayDialogController.this.mStartedFolded && !RearDisplayDialogController.this.isFoldedState(i)) {
                RearDisplayDialogController.this.mRearDisplayEducationDialog.dismiss();
                RearDisplayDialogController.this.closeOverlayAndNotifyService(false);
            } else if (RearDisplayDialogController.this.mStartedFolded || !RearDisplayDialogController.this.isFoldedState(i)) {
            } else {
                RearDisplayDialogController.this.mRearDisplayEducationDialog.dismiss();
                RearDisplayDialogController.this.closeOverlayAndNotifyService(true);
            }
        }

        public void onStateChanged(int i) {
        }
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.reardisplay.RearDisplayDialogController$$ExternalSyntheticLambda1.onClick(android.content.DialogInterface, int):void] */
    /* renamed from: $r8$lambda$-cGu-Fm4nd123rXfLGT8_zQERJA */
    public static /* synthetic */ void m4096$r8$lambda$cGuFm4nd123rXfLGT8_zQERJA(RearDisplayDialogController rearDisplayDialogController, DialogInterface dialogInterface, int i) {
        rearDisplayDialogController.lambda$configureDialogButtons$1(dialogInterface, i);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.reardisplay.RearDisplayDialogController$$ExternalSyntheticLambda2.onDismiss(android.content.DialogInterface):void] */
    public static /* synthetic */ void $r8$lambda$CEmc3ErZir25LsztiHcKdQaV7PM(RearDisplayDialogController rearDisplayDialogController, DialogInterface dialogInterface) {
        rearDisplayDialogController.lambda$configureDialogButtons$2(dialogInterface);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.reardisplay.RearDisplayDialogController$$ExternalSyntheticLambda0.onClick(android.content.DialogInterface, int):void] */
    /* renamed from: $r8$lambda$q7_bL-VeQnbpBQ1Ji3KBdeoAcqA */
    public static /* synthetic */ void m4097$r8$lambda$q7_bLVeQnbpBQ1Ji3KBdeoAcqA(RearDisplayDialogController rearDisplayDialogController, DialogInterface dialogInterface, int i) {
        rearDisplayDialogController.lambda$configureDialogButtons$0(dialogInterface, i);
    }

    public RearDisplayDialogController(Context context, CommandQueue commandQueue, Executor executor) {
        this.mContext = context;
        this.mCommandQueue = commandQueue;
        this.mExecutor = executor;
    }

    public /* synthetic */ void lambda$configureDialogButtons$0(DialogInterface dialogInterface, int i) {
        closeOverlayAndNotifyService(false);
    }

    public /* synthetic */ void lambda$configureDialogButtons$1(DialogInterface dialogInterface, int i) {
        closeOverlayAndNotifyService(true);
    }

    public /* synthetic */ void lambda$configureDialogButtons$2(DialogInterface dialogInterface) {
        if (this.mServiceNotified) {
            return;
        }
        closeOverlayAndNotifyService(true);
    }

    public final void closeOverlayAndNotifyService(boolean z) {
        this.mServiceNotified = true;
        this.mDeviceStateManagerGlobal.unregisterDeviceStateCallback(this.mDeviceStateManagerCallback);
        this.mDeviceStateManagerGlobal.onStateRequestOverlayDismissed(z);
    }

    public final void configureDialogButtons() {
        if (!this.mStartedFolded) {
            this.mRearDisplayEducationDialog.setPositiveButton(R$string.rear_display_bottom_sheet_confirm, new DialogInterface.OnClickListener() { // from class: com.android.systemui.reardisplay.RearDisplayDialogController$$ExternalSyntheticLambda0
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    RearDisplayDialogController.m4097$r8$lambda$q7_bLVeQnbpBQ1Ji3KBdeoAcqA(RearDisplayDialogController.this, dialogInterface, i);
                }
            }, true);
        }
        this.mRearDisplayEducationDialog.setNegativeButton(R$string.rear_display_bottom_sheet_cancel, new DialogInterface.OnClickListener() { // from class: com.android.systemui.reardisplay.RearDisplayDialogController$$ExternalSyntheticLambda1
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                RearDisplayDialogController.m4096$r8$lambda$cGuFm4nd123rXfLGT8_zQERJA(RearDisplayDialogController.this, dialogInterface, i);
            }
        }, true);
        this.mRearDisplayEducationDialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.android.systemui.reardisplay.RearDisplayDialogController$$ExternalSyntheticLambda2
            @Override // android.content.DialogInterface.OnDismissListener
            public final void onDismiss(DialogInterface dialogInterface) {
                RearDisplayDialogController.$r8$lambda$CEmc3ErZir25LsztiHcKdQaV7PM(RearDisplayDialogController.this, dialogInterface);
            }
        });
    }

    public final void createAndShowDialog() {
        this.mServiceNotified = false;
        Context context = this.mRearDisplayEducationDialog.getContext();
        View inflate = this.mStartedFolded ? View.inflate(context, R$layout.activity_rear_display_education, null) : View.inflate(context, R$layout.activity_rear_display_education_opened, null);
        ((LottieAnimationView) inflate.findViewById(R$id.rear_display_folded_animation)).setRepeatCount(this.mAnimationRepeatCount);
        this.mRearDisplayEducationDialog.setView(inflate);
        configureDialogButtons();
        this.mRearDisplayEducationDialog.show();
    }

    public final void initializeValues(int i) {
        this.mRearDisplayEducationDialog = new SystemUIDialog(this.mContext);
        if (this.mFoldedStates == null) {
            this.mFoldedStates = this.mContext.getResources().getIntArray(17236075);
        }
        this.mStartedFolded = isFoldedState(i);
        DeviceStateManagerGlobal deviceStateManagerGlobal = DeviceStateManagerGlobal.getInstance();
        this.mDeviceStateManagerGlobal = deviceStateManagerGlobal;
        deviceStateManagerGlobal.registerDeviceStateCallback(this.mDeviceStateManagerCallback, this.mExecutor);
    }

    public final boolean isFoldedState(int i) {
        int i2 = 0;
        while (true) {
            int[] iArr = this.mFoldedStates;
            if (i2 >= iArr.length) {
                return false;
            }
            if (iArr[i2] == i) {
                return true;
            }
            i2++;
        }
    }

    public void showRearDisplayDialog(int i) {
        initializeValues(i);
        createAndShowDialog();
    }

    @Override // com.android.systemui.CoreStartable
    public void start() {
        this.mCommandQueue.addCallback(this);
    }
}