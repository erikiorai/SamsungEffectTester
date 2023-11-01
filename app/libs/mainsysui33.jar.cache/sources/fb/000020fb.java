package com.android.systemui.qs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.admin.DeviceAdminInfo;
import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.UserInfo;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.os.UserManager;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.systemui.R$drawable;
import com.android.systemui.R$id;
import com.android.systemui.R$layout;
import com.android.systemui.R$string;
import com.android.systemui.animation.DialogCuj;
import com.android.systemui.animation.DialogLaunchAnimator;
import com.android.systemui.animation.Expandable;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.qs.footer.domain.model.SecurityButtonConfig;
import com.android.systemui.security.data.model.SecurityModel;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.android.systemui.statusbar.policy.SecurityController;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/QSSecurityFooterUtils.class */
public class QSSecurityFooterUtils implements DialogInterface.OnClickListener {
    public static final boolean DEBUG = Log.isLoggable("QSSecurityFooterUtils", 3);
    public final ActivityStarter mActivityStarter;
    public Handler mBgHandler;
    public Context mContext;
    public AlertDialog mDialog;
    public final DialogLaunchAnimator mDialogLaunchAnimator;
    public final DevicePolicyManager mDpm;
    public final Handler mMainHandler;
    public final SecurityController mSecurityController;
    public final UserTracker mUserTracker;
    public final AtomicBoolean mShouldUseSettingsButton = new AtomicBoolean(false);
    public Supplier<String> mManagementTitleSupplier = new Supplier() { // from class: com.android.systemui.qs.QSSecurityFooterUtils$$ExternalSyntheticLambda0
        @Override // java.util.function.Supplier
        public final Object get() {
            return QSSecurityFooterUtils.$r8$lambda$M2dc1Dsjw_yCXmJ_LjuiIqYbvZQ(QSSecurityFooterUtils.this);
        }
    };
    public Supplier<String> mManagementMessageSupplier = new Supplier() { // from class: com.android.systemui.qs.QSSecurityFooterUtils$$ExternalSyntheticLambda6
        @Override // java.util.function.Supplier
        public final Object get() {
            return QSSecurityFooterUtils.m3785$r8$lambda$w73Rsq6HqaMKNIRTtXAYzxxbLw(QSSecurityFooterUtils.this);
        }
    };
    public Supplier<String> mManagementMonitoringStringSupplier = new Supplier() { // from class: com.android.systemui.qs.QSSecurityFooterUtils$$ExternalSyntheticLambda7
        @Override // java.util.function.Supplier
        public final Object get() {
            return QSSecurityFooterUtils.m3782$r8$lambda$rLy4CCBKz_9HFqYQx9DKeC_ME(QSSecurityFooterUtils.this);
        }
    };
    public Supplier<String> mManagementMultipleVpnStringSupplier = new Supplier() { // from class: com.android.systemui.qs.QSSecurityFooterUtils$$ExternalSyntheticLambda8
        @Override // java.util.function.Supplier
        public final Object get() {
            return QSSecurityFooterUtils.m3781$r8$lambda$ky0rWyJhvvImvz9_47qBkSZnAc(QSSecurityFooterUtils.this);
        }
    };
    public Supplier<String> mWorkProfileMonitoringStringSupplier = new Supplier() { // from class: com.android.systemui.qs.QSSecurityFooterUtils$$ExternalSyntheticLambda9
        @Override // java.util.function.Supplier
        public final Object get() {
            return QSSecurityFooterUtils.$r8$lambda$ym4iTnEREpls7CT83LHI0PFKmt4(QSSecurityFooterUtils.this);
        }
    };
    public Supplier<String> mWorkProfileNetworkStringSupplier = new Supplier() { // from class: com.android.systemui.qs.QSSecurityFooterUtils$$ExternalSyntheticLambda10
        @Override // java.util.function.Supplier
        public final Object get() {
            return QSSecurityFooterUtils.$r8$lambda$qFRhqQzktkbLfewI7fu77h1Fm_s(QSSecurityFooterUtils.this);
        }
    };
    public Supplier<String> mMonitoringSubtitleCaCertStringSupplier = new Supplier() { // from class: com.android.systemui.qs.QSSecurityFooterUtils$$ExternalSyntheticLambda11
        @Override // java.util.function.Supplier
        public final Object get() {
            return QSSecurityFooterUtils.$r8$lambda$m4cai7EcOzv1v6petZtpX2HJlyI(QSSecurityFooterUtils.this);
        }
    };
    public Supplier<String> mMonitoringSubtitleNetworkStringSupplier = new Supplier() { // from class: com.android.systemui.qs.QSSecurityFooterUtils$$ExternalSyntheticLambda12
        @Override // java.util.function.Supplier
        public final Object get() {
            return QSSecurityFooterUtils.$r8$lambda$xMJnQK3Pmvgpj0qlZ0Eai1d5VZQ(QSSecurityFooterUtils.this);
        }
    };
    public Supplier<String> mMonitoringSubtitleVpnStringSupplier = new Supplier() { // from class: com.android.systemui.qs.QSSecurityFooterUtils$$ExternalSyntheticLambda13
        @Override // java.util.function.Supplier
        public final Object get() {
            return QSSecurityFooterUtils.$r8$lambda$D8T_wr41Eb361tdKPIATVABqH4I(QSSecurityFooterUtils.this);
        }
    };
    public Supplier<String> mViewPoliciesButtonStringSupplier = new Supplier() { // from class: com.android.systemui.qs.QSSecurityFooterUtils$$ExternalSyntheticLambda14
        @Override // java.util.function.Supplier
        public final Object get() {
            return QSSecurityFooterUtils.m3773$r8$lambda$5EW9sKqFEowFUDtd6EZVToGU8(QSSecurityFooterUtils.this);
        }
    };
    public Supplier<String> mManagementDialogStringSupplier = new Supplier() { // from class: com.android.systemui.qs.QSSecurityFooterUtils$$ExternalSyntheticLambda1
        @Override // java.util.function.Supplier
        public final Object get() {
            return QSSecurityFooterUtils.$r8$lambda$RoneJ7RUghnetB9J3LdScjvf4Vg(QSSecurityFooterUtils.this);
        }
    };
    public Supplier<String> mManagementDialogCaCertStringSupplier = new Supplier() { // from class: com.android.systemui.qs.QSSecurityFooterUtils$$ExternalSyntheticLambda2
        @Override // java.util.function.Supplier
        public final Object get() {
            return QSSecurityFooterUtils.m3771$r8$lambda$RONVv0yOwi7OLpOO4G8gcIQ(QSSecurityFooterUtils.this);
        }
    };
    public Supplier<String> mWorkProfileDialogCaCertStringSupplier = new Supplier() { // from class: com.android.systemui.qs.QSSecurityFooterUtils$$ExternalSyntheticLambda3
        @Override // java.util.function.Supplier
        public final Object get() {
            return QSSecurityFooterUtils.$r8$lambda$D6dGgUJcmM87YRP75pwitSbboAM(QSSecurityFooterUtils.this);
        }
    };
    public Supplier<String> mManagementDialogNetworkStringSupplier = new Supplier() { // from class: com.android.systemui.qs.QSSecurityFooterUtils$$ExternalSyntheticLambda4
        @Override // java.util.function.Supplier
        public final Object get() {
            return QSSecurityFooterUtils.m3775$r8$lambda$EZTDm_IytBc3Y137az94Li2RI(QSSecurityFooterUtils.this);
        }
    };
    public Supplier<String> mWorkProfileDialogNetworkStringSupplier = new Supplier() { // from class: com.android.systemui.qs.QSSecurityFooterUtils$$ExternalSyntheticLambda5
        @Override // java.util.function.Supplier
        public final Object get() {
            return QSSecurityFooterUtils.m3778$r8$lambda$V_mtVCCmhbQsucgOrzQ0KUVtoM(QSSecurityFooterUtils.this);
        }
    };

    /* loaded from: mainsysui33.jar:com/android/systemui/qs/QSSecurityFooterUtils$VpnSpan.class */
    public class VpnSpan extends ClickableSpan {
        public VpnSpan() {
            QSSecurityFooterUtils.this = r4;
        }

        public boolean equals(Object obj) {
            return obj instanceof VpnSpan;
        }

        public int hashCode() {
            return 314159257;
        }

        @Override // android.text.style.ClickableSpan
        public void onClick(View view) {
            Intent intent = new Intent("android.settings.VPN_SETTINGS");
            QSSecurityFooterUtils.this.mDialog.dismiss();
            QSSecurityFooterUtils.this.mActivityStarter.postStartActivityDismissingKeyguard(intent, 0);
        }
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.QSSecurityFooterUtils$$ExternalSyntheticLambda2.get():java.lang.Object] */
    /* renamed from: $r8$lambda$-R-ONVv0y-Owi7OL-pOO4G8gcIQ */
    public static /* synthetic */ String m3771$r8$lambda$RONVv0yOwi7OLpOO4G8gcIQ(QSSecurityFooterUtils qSSecurityFooterUtils) {
        return qSSecurityFooterUtils.lambda$new$11();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.QSSecurityFooterUtils$$ExternalSyntheticLambda23.get():java.lang.Object] */
    /* renamed from: $r8$lambda$072X-llYLHIZ4Qgd9wsE2k2Sg9w */
    public static /* synthetic */ String m3772$r8$lambda$072XllYLHIZ4Qgd9wsE2k2Sg9w(QSSecurityFooterUtils qSSecurityFooterUtils, String str) {
        return qSSecurityFooterUtils.lambda$getManagedDeviceVpnText$17(str);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.QSSecurityFooterUtils$$ExternalSyntheticLambda22.get():java.lang.Object] */
    public static /* synthetic */ String $r8$lambda$3vA5ekny3ZJuMILu_pJiL8ek5iw(QSSecurityFooterUtils qSSecurityFooterUtils, CharSequence charSequence) {
        return qSSecurityFooterUtils.lambda$getManagedDeviceVpnText$16(charSequence);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.QSSecurityFooterUtils$$ExternalSyntheticLambda14.get():java.lang.Object] */
    /* renamed from: $r8$lambda$5EW9sKqFEowFU--Dtd6EZVToGU8 */
    public static /* synthetic */ String m3773$r8$lambda$5EW9sKqFEowFUDtd6EZVToGU8(QSSecurityFooterUtils qSSecurityFooterUtils) {
        return qSSecurityFooterUtils.lambda$new$9();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.QSSecurityFooterUtils$$ExternalSyntheticLambda29.run():void] */
    /* renamed from: $r8$lambda$8gY4-KhbWtetFAs_emIv79391DU */
    public static /* synthetic */ void m3774$r8$lambda$8gY4KhbWtetFAs_emIv79391DU(QSSecurityFooterUtils qSSecurityFooterUtils, Context context, String str, View view, Expandable expandable) {
        qSSecurityFooterUtils.lambda$createDialog$23(context, str, view, expandable);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.QSSecurityFooterUtils$$ExternalSyntheticLambda17.get():java.lang.Object] */
    public static /* synthetic */ String $r8$lambda$BLPyT7nTQTzgGIgsO4NXo55tDts(QSSecurityFooterUtils qSSecurityFooterUtils, String str) {
        return qSSecurityFooterUtils.lambda$getVpnMessage$27(str);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.QSSecurityFooterUtils$$ExternalSyntheticLambda24.get():java.lang.Object] */
    public static /* synthetic */ String $r8$lambda$CyPvFz5gnPGYSoNtA4eMnAiV7sE(QSSecurityFooterUtils qSSecurityFooterUtils, CharSequence charSequence, String str) {
        return qSSecurityFooterUtils.lambda$getManagedDeviceVpnText$18(charSequence, str);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.QSSecurityFooterUtils$$ExternalSyntheticLambda3.get():java.lang.Object] */
    public static /* synthetic */ String $r8$lambda$D6dGgUJcmM87YRP75pwitSbboAM(QSSecurityFooterUtils qSSecurityFooterUtils) {
        return qSSecurityFooterUtils.lambda$new$12();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.QSSecurityFooterUtils$$ExternalSyntheticLambda13.get():java.lang.Object] */
    public static /* synthetic */ String $r8$lambda$D8T_wr41Eb361tdKPIATVABqH4I(QSSecurityFooterUtils qSSecurityFooterUtils) {
        return qSSecurityFooterUtils.lambda$new$8();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.QSSecurityFooterUtils$$ExternalSyntheticLambda4.get():java.lang.Object] */
    /* renamed from: $r8$lambda$EZTDm_IytBc3Y13-7az-94Li2RI */
    public static /* synthetic */ String m3775$r8$lambda$EZTDm_IytBc3Y137az94Li2RI(QSSecurityFooterUtils qSSecurityFooterUtils) {
        return qSSecurityFooterUtils.lambda$new$13();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.QSSecurityFooterUtils$$ExternalSyntheticLambda21.get():java.lang.Object] */
    public static /* synthetic */ String $r8$lambda$GlkTVupogsux4NW7aOSfhleYYrM(QSSecurityFooterUtils qSSecurityFooterUtils, CharSequence charSequence) {
        return qSSecurityFooterUtils.lambda$getManagementMessage$25(charSequence);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.QSSecurityFooterUtils$$ExternalSyntheticLambda0.get():java.lang.Object] */
    public static /* synthetic */ String $r8$lambda$M2dc1Dsjw_yCXmJ_LjuiIqYbvZQ(QSSecurityFooterUtils qSSecurityFooterUtils) {
        return qSSecurityFooterUtils.lambda$new$0();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.QSSecurityFooterUtils$$ExternalSyntheticLambda19.get():java.lang.Object] */
    public static /* synthetic */ String $r8$lambda$MYJ8tfYmEZbRiGkI3sciuaF0eE0(QSSecurityFooterUtils qSSecurityFooterUtils, String str) {
        return qSSecurityFooterUtils.lambda$getVpnMessage$29(str);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.QSSecurityFooterUtils$$ExternalSyntheticLambda28.get():java.lang.Object] */
    /* renamed from: $r8$lambda$N4HM6nX8RA8dZMmkx-xBb_GRxXE */
    public static /* synthetic */ String m3776$r8$lambda$N4HM6nX8RA8dZMmkxxBb_GRxXE(QSSecurityFooterUtils qSSecurityFooterUtils, String str) {
        return qSSecurityFooterUtils.lambda$getVpnText$22(str);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.QSSecurityFooterUtils$$ExternalSyntheticLambda27.get():java.lang.Object] */
    /* renamed from: $r8$lambda$P1_44ROBgT-J1Pw-FY4sS_8qWqA */
    public static /* synthetic */ String m3777$r8$lambda$P1_44ROBgTJ1PwFY4sS_8qWqA(QSSecurityFooterUtils qSSecurityFooterUtils, String str) {
        return qSSecurityFooterUtils.lambda$getVpnText$21(str);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.QSSecurityFooterUtils$$ExternalSyntheticLambda1.get():java.lang.Object] */
    public static /* synthetic */ String $r8$lambda$RoneJ7RUghnetB9J3LdScjvf4Vg(QSSecurityFooterUtils qSSecurityFooterUtils) {
        return qSSecurityFooterUtils.lambda$new$10();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.QSSecurityFooterUtils$$ExternalSyntheticLambda18.get():java.lang.Object] */
    public static /* synthetic */ String $r8$lambda$V5AzZ0UFDYkUstaLp3EkwlfOAVw(QSSecurityFooterUtils qSSecurityFooterUtils, String str, String str2) {
        return qSSecurityFooterUtils.lambda$getVpnMessage$28(str, str2);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.QSSecurityFooterUtils$$ExternalSyntheticLambda5.get():java.lang.Object] */
    /* renamed from: $r8$lambda$V_mtVCCmhbQsucgOrzQ0KUV-toM */
    public static /* synthetic */ String m3778$r8$lambda$V_mtVCCmhbQsucgOrzQ0KUVtoM(QSSecurityFooterUtils qSSecurityFooterUtils) {
        return qSSecurityFooterUtils.lambda$new$14();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.QSSecurityFooterUtils$$ExternalSyntheticLambda15.run():void] */
    /* renamed from: $r8$lambda$cwYFaQL4qRTqe9l3mzU-cD9qibE */
    public static /* synthetic */ void m3779$r8$lambda$cwYFaQL4qRTqe9l3mzUcD9qibE(QSSecurityFooterUtils qSSecurityFooterUtils, Context context, Expandable expandable) {
        qSSecurityFooterUtils.lambda$createDialog$24(context, expandable);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.QSSecurityFooterUtils$$ExternalSyntheticLambda16.get():java.lang.Object] */
    /* renamed from: $r8$lambda$dot-ODUBcExOXoe0sNSjnryOSVE */
    public static /* synthetic */ String m3780$r8$lambda$dotODUBcExOXoe0sNSjnryOSVE(QSSecurityFooterUtils qSSecurityFooterUtils, String str, String str2) {
        return qSSecurityFooterUtils.lambda$getVpnMessage$26(str, str2);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.QSSecurityFooterUtils$$ExternalSyntheticLambda20.get():java.lang.Object] */
    public static /* synthetic */ String $r8$lambda$kEJoHDXCnuSuqO_xgQwBjYwPJFs(QSSecurityFooterUtils qSSecurityFooterUtils, String str) {
        return qSSecurityFooterUtils.lambda$getVpnMessage$30(str);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.QSSecurityFooterUtils$$ExternalSyntheticLambda8.get():java.lang.Object] */
    /* renamed from: $r8$lambda$ky0rWyJhvvImvz9_-47qBkSZnAc */
    public static /* synthetic */ String m3781$r8$lambda$ky0rWyJhvvImvz9_47qBkSZnAc(QSSecurityFooterUtils qSSecurityFooterUtils) {
        return qSSecurityFooterUtils.lambda$new$3();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.QSSecurityFooterUtils$$ExternalSyntheticLambda11.get():java.lang.Object] */
    public static /* synthetic */ String $r8$lambda$m4cai7EcOzv1v6petZtpX2HJlyI(QSSecurityFooterUtils qSSecurityFooterUtils) {
        return qSSecurityFooterUtils.lambda$new$6();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.QSSecurityFooterUtils$$ExternalSyntheticLambda10.get():java.lang.Object] */
    public static /* synthetic */ String $r8$lambda$qFRhqQzktkbLfewI7fu77h1Fm_s(QSSecurityFooterUtils qSSecurityFooterUtils) {
        return qSSecurityFooterUtils.lambda$new$5();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.QSSecurityFooterUtils$$ExternalSyntheticLambda7.get():java.lang.Object] */
    /* renamed from: $r8$lambda$rLy-4CCBKz_9HFqYQx9D-KeC_ME */
    public static /* synthetic */ String m3782$r8$lambda$rLy4CCBKz_9HFqYQx9DKeC_ME(QSSecurityFooterUtils qSSecurityFooterUtils) {
        return qSSecurityFooterUtils.lambda$new$2();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.QSSecurityFooterUtils$$ExternalSyntheticLambda25.get():java.lang.Object] */
    /* renamed from: $r8$lambda$v3JD52Y-cP65Gz8F7BntRN25v84 */
    public static /* synthetic */ String m3783$r8$lambda$v3JD52YcP65Gz8F7BntRN25v84(QSSecurityFooterUtils qSSecurityFooterUtils, CharSequence charSequence) {
        return qSSecurityFooterUtils.lambda$getMonitoringText$20(charSequence);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.QSSecurityFooterUtils$$ExternalSyntheticLambda30.get():java.lang.Object] */
    /* renamed from: $r8$lambda$vCjOK_UaJ8mI3-YWJhOoZvaZVWs */
    public static /* synthetic */ String m3784$r8$lambda$vCjOK_UaJ8mI3YWJhOoZvaZVWs(QSSecurityFooterUtils qSSecurityFooterUtils, CharSequence charSequence) {
        return qSSecurityFooterUtils.lambda$getMangedDeviceGeneralText$19(charSequence);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.QSSecurityFooterUtils$$ExternalSyntheticLambda6.get():java.lang.Object] */
    /* renamed from: $r8$lambda$w-73Rsq6HqaMKNIRTtXAYzxxbLw */
    public static /* synthetic */ String m3785$r8$lambda$w73Rsq6HqaMKNIRTtXAYzxxbLw(QSSecurityFooterUtils qSSecurityFooterUtils) {
        return qSSecurityFooterUtils.lambda$new$1();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.QSSecurityFooterUtils$$ExternalSyntheticLambda12.get():java.lang.Object] */
    public static /* synthetic */ String $r8$lambda$xMJnQK3Pmvgpj0qlZ0Eai1d5VZQ(QSSecurityFooterUtils qSSecurityFooterUtils) {
        return qSSecurityFooterUtils.lambda$new$7();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.QSSecurityFooterUtils$$ExternalSyntheticLambda26.get():java.lang.Object] */
    /* renamed from: $r8$lambda$y47LXzTvb8Bp-bBySSBjup0j7zQ */
    public static /* synthetic */ String m3786$r8$lambda$y47LXzTvb8BpbBySSBjup0j7zQ(QSSecurityFooterUtils qSSecurityFooterUtils, CharSequence charSequence) {
        return qSSecurityFooterUtils.lambda$getManagedDeviceMonitoringText$15(charSequence);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.QSSecurityFooterUtils$$ExternalSyntheticLambda9.get():java.lang.Object] */
    public static /* synthetic */ String $r8$lambda$ym4iTnEREpls7CT83LHI0PFKmt4(QSSecurityFooterUtils qSSecurityFooterUtils) {
        return qSSecurityFooterUtils.lambda$new$4();
    }

    public QSSecurityFooterUtils(Context context, DevicePolicyManager devicePolicyManager, UserTracker userTracker, Handler handler, ActivityStarter activityStarter, SecurityController securityController, Looper looper, DialogLaunchAnimator dialogLaunchAnimator) {
        this.mContext = context;
        this.mDpm = devicePolicyManager;
        this.mUserTracker = userTracker;
        this.mMainHandler = handler;
        this.mActivityStarter = activityStarter;
        this.mSecurityController = securityController;
        this.mBgHandler = new Handler(looper);
        this.mDialogLaunchAnimator = dialogLaunchAnimator;
    }

    public /* synthetic */ void lambda$createDialog$23(Context context, String str, View view, Expandable expandable) {
        SystemUIDialog systemUIDialog = new SystemUIDialog(context, 0);
        this.mDialog = systemUIDialog;
        systemUIDialog.requestWindowFeature(1);
        this.mDialog.setButton(-1, getPositiveButton(), this);
        AlertDialog alertDialog = this.mDialog;
        if (!this.mShouldUseSettingsButton.get()) {
            str = getNegativeButton();
        }
        alertDialog.setButton(-2, str, this);
        this.mDialog.setView(view);
        DialogLaunchAnimator.Controller dialogLaunchController = expandable != null ? expandable.dialogLaunchController(new DialogCuj(58, "managed_device_info")) : null;
        if (dialogLaunchController != null) {
            this.mDialogLaunchAnimator.show(this.mDialog, dialogLaunchController);
        } else {
            this.mDialog.show();
        }
    }

    public /* synthetic */ void lambda$createDialog$24(final Context context, final Expandable expandable) {
        final String settingsButton = getSettingsButton();
        final View createDialogView = createDialogView(context);
        this.mMainHandler.post(new Runnable() { // from class: com.android.systemui.qs.QSSecurityFooterUtils$$ExternalSyntheticLambda29
            @Override // java.lang.Runnable
            public final void run() {
                QSSecurityFooterUtils.m3774$r8$lambda$8gY4KhbWtetFAs_emIv79391DU(QSSecurityFooterUtils.this, context, settingsButton, createDialogView, expandable);
            }
        });
    }

    public /* synthetic */ String lambda$getManagedDeviceMonitoringText$15(CharSequence charSequence) {
        return this.mContext.getString(R$string.quick_settings_disclosure_named_management_monitoring, charSequence);
    }

    public /* synthetic */ String lambda$getManagedDeviceVpnText$16(CharSequence charSequence) {
        return this.mContext.getString(R$string.quick_settings_disclosure_named_management_vpns, charSequence);
    }

    public /* synthetic */ String lambda$getManagedDeviceVpnText$17(String str) {
        return this.mContext.getString(R$string.quick_settings_disclosure_management_named_vpn, str);
    }

    public /* synthetic */ String lambda$getManagedDeviceVpnText$18(CharSequence charSequence, String str) {
        return this.mContext.getString(R$string.quick_settings_disclosure_named_management_named_vpn, charSequence, str);
    }

    public /* synthetic */ String lambda$getManagementMessage$25(CharSequence charSequence) {
        return this.mContext.getString(R$string.monitoring_description_named_management, charSequence);
    }

    public /* synthetic */ String lambda$getMangedDeviceGeneralText$19(CharSequence charSequence) {
        return this.mContext.getString(R$string.quick_settings_disclosure_named_management, charSequence);
    }

    public /* synthetic */ String lambda$getMonitoringText$20(CharSequence charSequence) {
        return this.mContext.getString(R$string.quick_settings_disclosure_named_managed_profile_monitoring, charSequence);
    }

    public /* synthetic */ String lambda$getVpnMessage$26(String str, String str2) {
        return this.mContext.getString(R$string.monitoring_description_two_named_vpns, str, str2);
    }

    public /* synthetic */ String lambda$getVpnMessage$27(String str) {
        return this.mContext.getString(R$string.monitoring_description_named_vpn, str);
    }

    public /* synthetic */ String lambda$getVpnMessage$28(String str, String str2) {
        return this.mContext.getString(R$string.monitoring_description_two_named_vpns, str, str2);
    }

    public /* synthetic */ String lambda$getVpnMessage$29(String str) {
        return this.mContext.getString(R$string.monitoring_description_managed_profile_named_vpn, str);
    }

    public /* synthetic */ String lambda$getVpnMessage$30(String str) {
        return this.mContext.getString(R$string.monitoring_description_personal_profile_named_vpn, str);
    }

    public /* synthetic */ String lambda$getVpnText$21(String str) {
        return this.mContext.getString(R$string.quick_settings_disclosure_managed_profile_named_vpn, str);
    }

    public /* synthetic */ String lambda$getVpnText$22(String str) {
        return this.mContext.getString(R$string.quick_settings_disclosure_personal_profile_named_vpn, str);
    }

    public /* synthetic */ String lambda$new$0() {
        Context context = this.mContext;
        return context == null ? null : context.getString(R$string.monitoring_title_device_owned);
    }

    public /* synthetic */ String lambda$new$1() {
        Context context = this.mContext;
        return context == null ? null : context.getString(R$string.quick_settings_disclosure_management);
    }

    public /* synthetic */ String lambda$new$10() {
        Context context = this.mContext;
        return context == null ? null : context.getString(R$string.monitoring_description_management);
    }

    public /* synthetic */ String lambda$new$11() {
        Context context = this.mContext;
        return context == null ? null : context.getString(R$string.monitoring_description_management_ca_certificate);
    }

    public /* synthetic */ String lambda$new$12() {
        Context context = this.mContext;
        return context == null ? null : context.getString(R$string.monitoring_description_managed_profile_ca_certificate);
    }

    public /* synthetic */ String lambda$new$13() {
        Context context = this.mContext;
        return context == null ? null : context.getString(R$string.monitoring_description_management_network_logging);
    }

    public /* synthetic */ String lambda$new$14() {
        Context context = this.mContext;
        return context == null ? null : context.getString(R$string.monitoring_description_managed_profile_network_logging);
    }

    public /* synthetic */ String lambda$new$2() {
        Context context = this.mContext;
        return context == null ? null : context.getString(R$string.quick_settings_disclosure_management_monitoring);
    }

    public /* synthetic */ String lambda$new$3() {
        Context context = this.mContext;
        return context == null ? null : context.getString(R$string.quick_settings_disclosure_management_vpns);
    }

    public /* synthetic */ String lambda$new$4() {
        Context context = this.mContext;
        return context == null ? null : context.getString(R$string.quick_settings_disclosure_managed_profile_monitoring);
    }

    public /* synthetic */ String lambda$new$5() {
        Context context = this.mContext;
        return context == null ? null : context.getString(R$string.quick_settings_disclosure_managed_profile_network_activity);
    }

    public /* synthetic */ String lambda$new$6() {
        Context context = this.mContext;
        return context == null ? null : context.getString(R$string.monitoring_subtitle_ca_certificate);
    }

    public /* synthetic */ String lambda$new$7() {
        Context context = this.mContext;
        return context == null ? null : context.getString(R$string.monitoring_subtitle_network_logging);
    }

    public /* synthetic */ String lambda$new$8() {
        Context context = this.mContext;
        return context == null ? null : context.getString(R$string.monitoring_subtitle_vpn);
    }

    public /* synthetic */ String lambda$new$9() {
        Context context = this.mContext;
        return context == null ? null : context.getString(R$string.monitoring_button_view_policies);
    }

    public void configSubtitleVisibility(boolean z, boolean z2, boolean z3, boolean z4, View view) {
        if (z) {
            return;
        }
        int i = z3 ? (z2 ? 1 : 0) + 1 : z2 ? 1 : 0;
        int i2 = i;
        if (z4) {
            i2 = i + 1;
        }
        if (i2 != 1) {
            return;
        }
        if (z2) {
            view.findViewById(R$id.ca_certs_subtitle).setVisibility(8);
        }
        if (z3) {
            view.findViewById(R$id.network_logging_subtitle).setVisibility(8);
        }
        if (z4) {
            view.findViewById(R$id.vpn_subtitle).setVisibility(8);
        }
    }

    public final void createDialog(final Context context, final Expandable expandable) {
        this.mShouldUseSettingsButton.set(false);
        this.mBgHandler.post(new Runnable() { // from class: com.android.systemui.qs.QSSecurityFooterUtils$$ExternalSyntheticLambda15
            @Override // java.lang.Runnable
            public final void run() {
                QSSecurityFooterUtils.m3779$r8$lambda$cwYFaQL4qRTqe9l3mzUcD9qibE(QSSecurityFooterUtils.this, context, expandable);
            }
        });
    }

    public View createDialogView(Context context) {
        return this.mSecurityController.isParentalControlsEnabled() ? createParentalControlsDialogView(context) : createOrganizationDialogView(context);
    }

    public final View createOrganizationDialogView(Context context) {
        boolean isDeviceManaged = this.mSecurityController.isDeviceManaged();
        boolean hasWorkProfile = this.mSecurityController.hasWorkProfile();
        CharSequence deviceOwnerOrganizationName = this.mSecurityController.getDeviceOwnerOrganizationName();
        boolean hasCACertInCurrentUser = this.mSecurityController.hasCACertInCurrentUser();
        boolean hasCACertInWorkProfile = this.mSecurityController.hasCACertInWorkProfile();
        boolean isNetworkLoggingEnabled = this.mSecurityController.isNetworkLoggingEnabled();
        String primaryVpnName = this.mSecurityController.getPrimaryVpnName();
        String workProfileVpnName = this.mSecurityController.getWorkProfileVpnName();
        View inflate = LayoutInflater.from(context).inflate(R$layout.quick_settings_footer_dialog, (ViewGroup) null, false);
        ((TextView) inflate.findViewById(R$id.device_management_subtitle)).setText(getManagementTitle(deviceOwnerOrganizationName));
        CharSequence managementMessage = getManagementMessage(isDeviceManaged, deviceOwnerOrganizationName);
        boolean z = true;
        if (managementMessage == null) {
            inflate.findViewById(R$id.device_management_disclosures).setVisibility(8);
        } else {
            inflate.findViewById(R$id.device_management_disclosures).setVisibility(0);
            ((TextView) inflate.findViewById(R$id.device_management_warning)).setText(managementMessage);
            this.mShouldUseSettingsButton.set(true);
        }
        CharSequence caCertsMessage = getCaCertsMessage(isDeviceManaged, hasCACertInCurrentUser, hasCACertInWorkProfile);
        if (caCertsMessage == null) {
            inflate.findViewById(R$id.ca_certs_disclosures).setVisibility(8);
        } else {
            inflate.findViewById(R$id.ca_certs_disclosures).setVisibility(0);
            TextView textView = (TextView) inflate.findViewById(R$id.ca_certs_warning);
            textView.setText(caCertsMessage);
            textView.setMovementMethod(new LinkMovementMethod());
            ((TextView) inflate.findViewById(R$id.ca_certs_subtitle)).setText(this.mDpm.getResources().getString("SystemUi.QS_DIALOG_MONITORING_CA_CERT_SUBTITLE", this.mMonitoringSubtitleCaCertStringSupplier));
        }
        CharSequence networkLoggingMessage = getNetworkLoggingMessage(isDeviceManaged, isNetworkLoggingEnabled);
        if (networkLoggingMessage == null) {
            inflate.findViewById(R$id.network_logging_disclosures).setVisibility(8);
        } else {
            inflate.findViewById(R$id.network_logging_disclosures).setVisibility(0);
            ((TextView) inflate.findViewById(R$id.network_logging_warning)).setText(networkLoggingMessage);
            ((TextView) inflate.findViewById(R$id.network_logging_subtitle)).setText(this.mDpm.getResources().getString("SystemUi.QS_DIALOG_MONITORING_NETWORK_SUBTITLE", this.mMonitoringSubtitleNetworkStringSupplier));
        }
        CharSequence vpnMessage = getVpnMessage(isDeviceManaged, hasWorkProfile, primaryVpnName, workProfileVpnName);
        if (vpnMessage == null) {
            inflate.findViewById(R$id.vpn_disclosures).setVisibility(8);
        } else {
            inflate.findViewById(R$id.vpn_disclosures).setVisibility(0);
            TextView textView2 = (TextView) inflate.findViewById(R$id.vpn_warning);
            textView2.setText(vpnMessage);
            textView2.setMovementMethod(new LinkMovementMethod());
            ((TextView) inflate.findViewById(R$id.vpn_subtitle)).setText(this.mDpm.getResources().getString("SystemUi.QS_DIALOG_MONITORING_VPN_SUBTITLE", this.mMonitoringSubtitleVpnStringSupplier));
        }
        boolean z2 = managementMessage != null;
        boolean z3 = caCertsMessage != null;
        boolean z4 = networkLoggingMessage != null;
        if (vpnMessage == null) {
            z = false;
        }
        configSubtitleVisibility(z2, z3, z4, z, inflate);
        return inflate;
    }

    public final View createParentalControlsDialogView(Context context) {
        View inflate = LayoutInflater.from(context).inflate(R$layout.quick_settings_footer_dialog_parental_controls, (ViewGroup) null, false);
        DeviceAdminInfo deviceAdminInfo = this.mSecurityController.getDeviceAdminInfo();
        Drawable icon = this.mSecurityController.getIcon(deviceAdminInfo);
        if (icon != null) {
            ((ImageView) inflate.findViewById(R$id.parental_controls_icon)).setImageDrawable(icon);
        }
        ((TextView) inflate.findViewById(R$id.parental_controls_title)).setText(this.mSecurityController.getLabel(deviceAdminInfo));
        return inflate;
    }

    /* JADX WARN: Code restructure failed: missing block: B:117:0x00e3, code lost:
        if (r0 != false) goto L48;
     */
    /* JADX WARN: Removed duplicated region for block: B:132:0x0148  */
    /* JADX WARN: Removed duplicated region for block: B:133:0x0157  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public SecurityButtonConfig getButtonConfig(SecurityModel securityModel) {
        boolean z;
        boolean isDeviceManaged = securityModel.isDeviceManaged();
        UserInfo userInfo = this.mUserTracker.getUserInfo();
        boolean z2 = UserManager.isDeviceInDemoMode(this.mContext) && userInfo != null && userInfo.isDemo();
        boolean hasWorkProfile = securityModel.getHasWorkProfile();
        boolean hasCACertInCurrentUser = securityModel.getHasCACertInCurrentUser();
        boolean hasCACertInWorkProfile = securityModel.getHasCACertInWorkProfile();
        boolean isNetworkLoggingEnabled = securityModel.isNetworkLoggingEnabled();
        String primaryVpnName = securityModel.getPrimaryVpnName();
        String workProfileVpnName = securityModel.getWorkProfileVpnName();
        String deviceOwnerOrganizationName = securityModel.getDeviceOwnerOrganizationName();
        String workProfileOrganizationName = securityModel.getWorkProfileOrganizationName();
        boolean isProfileOwnerOfOrganizationOwnedDevice = securityModel.isProfileOwnerOfOrganizationOwnedDevice();
        boolean isParentalControlsEnabled = securityModel.isParentalControlsEnabled();
        boolean isWorkProfileOn = securityModel.isWorkProfileOn();
        boolean z3 = hasCACertInWorkProfile || workProfileVpnName != null || (hasWorkProfile && isNetworkLoggingEnabled);
        if ((isDeviceManaged && !z2) || hasCACertInCurrentUser || primaryVpnName != null || isProfileOwnerOfOrganizationOwnedDevice || isParentalControlsEnabled || (z3 && isWorkProfileOn)) {
            if (isProfileOwnerOfOrganizationOwnedDevice) {
                z = false;
                if (z3) {
                    z = false;
                }
                return new SecurityButtonConfig((isParentalControlsEnabled || securityModel.getDeviceAdminIcon() == null) ? (primaryVpnName == null || workProfileVpnName != null) ? !securityModel.isVpnBranded() ? new Icon.Resource(R$drawable.stat_sys_branded_vpn, null) : new Icon.Resource(R$drawable.stat_sys_vpn_ic, null) : new Icon.Resource(R$drawable.ic_info_outline, null) : new Icon.Loaded(securityModel.getDeviceAdminIcon(), null), getFooterText(isDeviceManaged, hasWorkProfile, hasCACertInCurrentUser, hasCACertInWorkProfile, isNetworkLoggingEnabled, primaryVpnName, workProfileVpnName, deviceOwnerOrganizationName, workProfileOrganizationName, isProfileOwnerOfOrganizationOwnedDevice, isParentalControlsEnabled, isWorkProfileOn).toString(), z);
            }
            z = true;
            return new SecurityButtonConfig((isParentalControlsEnabled || securityModel.getDeviceAdminIcon() == null) ? (primaryVpnName == null || workProfileVpnName != null) ? !securityModel.isVpnBranded() ? new Icon.Resource(R$drawable.stat_sys_branded_vpn, null) : new Icon.Resource(R$drawable.stat_sys_vpn_ic, null) : new Icon.Resource(R$drawable.ic_info_outline, null) : new Icon.Loaded(securityModel.getDeviceAdminIcon(), null), getFooterText(isDeviceManaged, hasWorkProfile, hasCACertInCurrentUser, hasCACertInWorkProfile, isNetworkLoggingEnabled, primaryVpnName, workProfileVpnName, deviceOwnerOrganizationName, workProfileOrganizationName, isProfileOwnerOfOrganizationOwnedDevice, isParentalControlsEnabled, isWorkProfileOn).toString(), z);
        }
        return null;
    }

    public CharSequence getCaCertsMessage(boolean z, boolean z2, boolean z3) {
        if (z2 || z3) {
            return z ? this.mDpm.getResources().getString("SystemUi.QS_DIALOG_MANAGEMENT_CA_CERT", this.mManagementDialogCaCertStringSupplier) : z3 ? this.mDpm.getResources().getString("SystemUi.QS_DIALOG_WORK_PROFILE_CA_CERT", this.mWorkProfileDialogCaCertStringSupplier) : this.mContext.getString(R$string.monitoring_description_ca_certificate);
        }
        return null;
    }

    public Dialog getDialog() {
        return this.mDialog;
    }

    public CharSequence getFooterText(boolean z, boolean z2, boolean z3, boolean z4, boolean z5, String str, String str2, CharSequence charSequence, CharSequence charSequence2, boolean z6, boolean z7, boolean z8) {
        return z7 ? this.mContext.getString(R$string.quick_settings_disclosure_parental_controls) : !z ? getManagedAndPersonalProfileFooterText(z2, z3, z4, z5, str, str2, charSequence2, z6, z8) : getManagedDeviceFooterText(z3, z4, z5, str, str2, charSequence);
    }

    public final String getManagedAndPersonalProfileFooterText(boolean z, boolean z2, boolean z3, boolean z4, String str, String str2, CharSequence charSequence, boolean z5, boolean z6) {
        if (z2 || (z3 && z6)) {
            return getMonitoringText(z2, z3, charSequence, z6);
        }
        if (str != null || (str2 != null && z6)) {
            return getVpnText(z, str, str2, z6);
        }
        if (z && z4 && z6) {
            return getManagedProfileNetworkActivityText();
        }
        if (z5) {
            return getMangedDeviceGeneralText(charSequence);
        }
        return null;
    }

    public final String getManagedDeviceFooterText(boolean z, boolean z2, boolean z3, String str, String str2, CharSequence charSequence) {
        return (z || z2 || z3) ? getManagedDeviceMonitoringText(charSequence) : (str == null && str2 == null) ? getMangedDeviceGeneralText(charSequence) : getManagedDeviceVpnText(str, str2, charSequence);
    }

    public final String getManagedDeviceMonitoringText(final CharSequence charSequence) {
        return charSequence == null ? this.mDpm.getResources().getString("SystemUi.QS_MSG_MANAGEMENT_MONITORING", this.mManagementMonitoringStringSupplier) : this.mDpm.getResources().getString("SystemUi.QS_MSG_NAMED_MANAGEMENT_MONITORING", new Supplier() { // from class: com.android.systemui.qs.QSSecurityFooterUtils$$ExternalSyntheticLambda26
            @Override // java.util.function.Supplier
            public final Object get() {
                return QSSecurityFooterUtils.m3786$r8$lambda$y47LXzTvb8BpbBySSBjup0j7zQ(QSSecurityFooterUtils.this, charSequence);
            }
        }, new Object[]{charSequence});
    }

    public final String getManagedDeviceVpnText(String str, String str2, final CharSequence charSequence) {
        if (str != null && str2 != null) {
            return charSequence == null ? this.mDpm.getResources().getString("SystemUi.QS_MSG_MANAGEMENT_MULTIPLE_VPNS", this.mManagementMultipleVpnStringSupplier) : this.mDpm.getResources().getString("SystemUi.QS_MSG_NAMED_MANAGEMENT_MULTIPLE_VPNS", new Supplier() { // from class: com.android.systemui.qs.QSSecurityFooterUtils$$ExternalSyntheticLambda22
                @Override // java.util.function.Supplier
                public final Object get() {
                    return QSSecurityFooterUtils.$r8$lambda$3vA5ekny3ZJuMILu_pJiL8ek5iw(QSSecurityFooterUtils.this, charSequence);
                }
            }, new Object[]{charSequence});
        }
        if (str == null) {
            str = str2;
        }
        if (charSequence == null) {
            final String str3 = str;
            return this.mDpm.getResources().getString("SystemUi.QS_MSG_MANAGEMENT_NAMED_VPN", new Supplier() { // from class: com.android.systemui.qs.QSSecurityFooterUtils$$ExternalSyntheticLambda23
                @Override // java.util.function.Supplier
                public final Object get() {
                    return QSSecurityFooterUtils.m3772$r8$lambda$072XllYLHIZ4Qgd9wsE2k2Sg9w(QSSecurityFooterUtils.this, str3);
                }
            }, new Object[]{str});
        }
        final String str4 = str;
        return this.mDpm.getResources().getString("SystemUi.QS_MSG_NAMED_MANAGEMENT_NAMED_VPN", new Supplier() { // from class: com.android.systemui.qs.QSSecurityFooterUtils$$ExternalSyntheticLambda24
            @Override // java.util.function.Supplier
            public final Object get() {
                return QSSecurityFooterUtils.$r8$lambda$CyPvFz5gnPGYSoNtA4eMnAiV7sE(QSSecurityFooterUtils.this, charSequence, str4);
            }
        }, new Object[]{charSequence, str});
    }

    public final String getManagedProfileNetworkActivityText() {
        return this.mDpm.getResources().getString("SystemUi.QS_MSG_WORK_PROFILE_NETWORK", this.mWorkProfileNetworkStringSupplier);
    }

    public CharSequence getManagementMessage(boolean z, final CharSequence charSequence) {
        if (z) {
            return charSequence != null ? isFinancedDevice() ? this.mContext.getString(R$string.monitoring_financed_description_named_management, charSequence, charSequence) : this.mDpm.getResources().getString("SystemUi.QS_DIALOG_NAMED_MANAGEMENT", new Supplier() { // from class: com.android.systemui.qs.QSSecurityFooterUtils$$ExternalSyntheticLambda21
                @Override // java.util.function.Supplier
                public final Object get() {
                    return QSSecurityFooterUtils.$r8$lambda$GlkTVupogsux4NW7aOSfhleYYrM(QSSecurityFooterUtils.this, charSequence);
                }
            }, new Object[]{charSequence}) : this.mDpm.getResources().getString("SystemUi.QS_DIALOG_MANAGEMENT", this.mManagementDialogStringSupplier);
        }
        return null;
    }

    public CharSequence getManagementTitle(CharSequence charSequence) {
        return (charSequence == null || !isFinancedDevice()) ? this.mDpm.getResources().getString("SystemUi.QS_DIALOG_MANAGEMENT_TITLE", this.mManagementTitleSupplier) : this.mContext.getString(R$string.monitoring_title_financed_device, charSequence);
    }

    public final String getMangedDeviceGeneralText(final CharSequence charSequence) {
        return charSequence == null ? this.mDpm.getResources().getString("SystemUi.QS_MSG_MANAGEMENT", this.mManagementMessageSupplier) : isFinancedDevice() ? this.mContext.getString(R$string.quick_settings_financed_disclosure_named_management, charSequence) : this.mDpm.getResources().getString("SystemUi.QS_MSG_NAMED_MANAGEMENT", new Supplier() { // from class: com.android.systemui.qs.QSSecurityFooterUtils$$ExternalSyntheticLambda30
            @Override // java.util.function.Supplier
            public final Object get() {
                return QSSecurityFooterUtils.m3784$r8$lambda$vCjOK_UaJ8mI3YWJhOoZvaZVWs(QSSecurityFooterUtils.this, charSequence);
            }
        }, new Object[]{charSequence});
    }

    public final String getMonitoringText(boolean z, boolean z2, final CharSequence charSequence, boolean z3) {
        if (z2 && z3) {
            return charSequence == null ? this.mDpm.getResources().getString("SystemUi.QS_MSG_WORK_PROFILE_MONITORING", this.mWorkProfileMonitoringStringSupplier) : this.mDpm.getResources().getString("SystemUi.QS_MSG_NAMED_WORK_PROFILE_MONITORING", new Supplier() { // from class: com.android.systemui.qs.QSSecurityFooterUtils$$ExternalSyntheticLambda25
                @Override // java.util.function.Supplier
                public final Object get() {
                    return QSSecurityFooterUtils.m3783$r8$lambda$v3JD52YcP65Gz8F7BntRN25v84(QSSecurityFooterUtils.this, charSequence);
                }
            }, new Object[]{charSequence});
        } else if (z) {
            return this.mContext.getString(R$string.quick_settings_disclosure_monitoring);
        } else {
            return null;
        }
    }

    public final String getNegativeButton() {
        if (this.mSecurityController.isParentalControlsEnabled()) {
            return this.mContext.getString(R$string.monitoring_button_view_controls);
        }
        return null;
    }

    public CharSequence getNetworkLoggingMessage(boolean z, boolean z2) {
        if (z2) {
            return z ? this.mDpm.getResources().getString("SystemUi.QS_DIALOG_MANAGEMENT_NETWORK", this.mManagementDialogNetworkStringSupplier) : this.mDpm.getResources().getString("SystemUi.QS_DIALOG_WORK_PROFILE_NETWORK", this.mWorkProfileDialogNetworkStringSupplier);
        }
        return null;
    }

    public final String getPositiveButton() {
        return this.mContext.getString(R$string.ok);
    }

    public String getSettingsButton() {
        return this.mDpm.getResources().getString("SystemUi.QS_DIALOG_VIEW_POLICIES", this.mViewPoliciesButtonStringSupplier);
    }

    public CharSequence getVpnMessage(boolean z, boolean z2, final String str, final String str2) {
        if (str == null && str2 == null) {
            return null;
        }
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        if (z) {
            if (str == null || str2 == null) {
                if (str == null) {
                    str = str2;
                }
                final String str3 = str;
                spannableStringBuilder.append((CharSequence) this.mDpm.getResources().getString("SystemUi.QS_DIALOG_MANAGEMENT_NAMED_VPN", new Supplier() { // from class: com.android.systemui.qs.QSSecurityFooterUtils$$ExternalSyntheticLambda17
                    @Override // java.util.function.Supplier
                    public final Object get() {
                        return QSSecurityFooterUtils.$r8$lambda$BLPyT7nTQTzgGIgsO4NXo55tDts(QSSecurityFooterUtils.this, str3);
                    }
                }, new Object[]{str}));
            } else {
                spannableStringBuilder.append((CharSequence) this.mDpm.getResources().getString("SystemUi.QS_DIALOG_MANAGEMENT_TWO_NAMED_VPN", new Supplier() { // from class: com.android.systemui.qs.QSSecurityFooterUtils$$ExternalSyntheticLambda16
                    @Override // java.util.function.Supplier
                    public final Object get() {
                        return QSSecurityFooterUtils.m3780$r8$lambda$dotODUBcExOXoe0sNSjnryOSVE(QSSecurityFooterUtils.this, str, str2);
                    }
                }, new Object[]{str, str2}));
            }
        } else if (str != null && str2 != null) {
            spannableStringBuilder.append((CharSequence) this.mDpm.getResources().getString("SystemUi.QS_DIALOG_MANAGEMENT_TWO_NAMED_VPN", new Supplier() { // from class: com.android.systemui.qs.QSSecurityFooterUtils$$ExternalSyntheticLambda18
                @Override // java.util.function.Supplier
                public final Object get() {
                    return QSSecurityFooterUtils.$r8$lambda$V5AzZ0UFDYkUstaLp3EkwlfOAVw(QSSecurityFooterUtils.this, str, str2);
                }
            }, new Object[]{str, str2}));
        } else if (str2 != null) {
            spannableStringBuilder.append((CharSequence) this.mDpm.getResources().getString("SystemUi.QS_DIALOG_WORK_PROFILE_NAMED_VPN", new Supplier() { // from class: com.android.systemui.qs.QSSecurityFooterUtils$$ExternalSyntheticLambda19
                @Override // java.util.function.Supplier
                public final Object get() {
                    return QSSecurityFooterUtils.$r8$lambda$MYJ8tfYmEZbRiGkI3sciuaF0eE0(QSSecurityFooterUtils.this, str2);
                }
            }, new Object[]{str2}));
        } else if (z2) {
            spannableStringBuilder.append((CharSequence) this.mDpm.getResources().getString("SystemUi.QS_DIALOG_PERSONAL_PROFILE_NAMED_VPN", new Supplier() { // from class: com.android.systemui.qs.QSSecurityFooterUtils$$ExternalSyntheticLambda20
                @Override // java.util.function.Supplier
                public final Object get() {
                    return QSSecurityFooterUtils.$r8$lambda$kEJoHDXCnuSuqO_xgQwBjYwPJFs(QSSecurityFooterUtils.this, str);
                }
            }, new Object[]{str}));
        } else {
            spannableStringBuilder.append((CharSequence) this.mContext.getString(R$string.monitoring_description_named_vpn, str));
        }
        spannableStringBuilder.append((CharSequence) this.mContext.getString(R$string.monitoring_description_vpn_settings_separator));
        spannableStringBuilder.append(this.mContext.getString(R$string.monitoring_description_vpn_settings), new VpnSpan(), 0);
        return spannableStringBuilder;
    }

    public final String getVpnText(boolean z, final String str, final String str2, boolean z2) {
        if (str == null || str2 == null) {
            if (str2 == null || !z2) {
                if (str != null) {
                    return z ? this.mDpm.getResources().getString("SystemUi.QS_MSG_PERSONAL_PROFILE_NAMED_VPN", new Supplier() { // from class: com.android.systemui.qs.QSSecurityFooterUtils$$ExternalSyntheticLambda28
                        @Override // java.util.function.Supplier
                        public final Object get() {
                            return QSSecurityFooterUtils.m3776$r8$lambda$N4HM6nX8RA8dZMmkxxBb_GRxXE(QSSecurityFooterUtils.this, str);
                        }
                    }, new Object[]{str}) : this.mContext.getString(R$string.quick_settings_disclosure_named_vpn, str);
                }
                return null;
            }
            return this.mDpm.getResources().getString("SystemUi.QS_MSG_WORK_PROFILE_NAMED_VPN", new Supplier() { // from class: com.android.systemui.qs.QSSecurityFooterUtils$$ExternalSyntheticLambda27
                @Override // java.util.function.Supplier
                public final Object get() {
                    return QSSecurityFooterUtils.m3777$r8$lambda$P1_44ROBgTJ1PwFY4sS_8qWqA(QSSecurityFooterUtils.this, str2);
                }
            }, new Object[]{str2});
        }
        return this.mContext.getString(R$string.quick_settings_disclosure_vpns);
    }

    /* JADX WARN: Code restructure failed: missing block: B:15:0x0022, code lost:
        if (r0.getDeviceOwnerType(r0.getDeviceOwnerComponentOnAnyUser()) == 1) goto L5;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean isFinancedDevice() {
        boolean z = true;
        if (this.mSecurityController.isDeviceManaged()) {
            SecurityController securityController = this.mSecurityController;
        }
        z = false;
        return z;
    }

    @Override // android.content.DialogInterface.OnClickListener
    public void onClick(DialogInterface dialogInterface, int i) {
        if (i == -2) {
            Intent intent = new Intent("android.settings.ENTERPRISE_PRIVACY_SETTINGS");
            dialogInterface.dismiss();
            this.mActivityStarter.postStartActivityDismissingKeyguard(intent, 0);
        }
    }

    public void showDeviceMonitoringDialog(Context context, Expandable expandable) {
        createDialog(context, expandable);
    }
}