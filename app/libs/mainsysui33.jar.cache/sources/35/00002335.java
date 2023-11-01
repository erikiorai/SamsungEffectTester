package com.android.systemui.qs.tiles.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.ServiceState;
import android.telephony.SignalStrength;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyDisplayInfo;
import android.telephony.TelephonyManager;
import android.text.Html;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.internal.logging.UiEventLogger;
import com.android.settingslib.Utils;
import com.android.settingslib.wifi.WifiEnterpriseRestrictionUtils;
import com.android.systemui.Prefs;
import com.android.systemui.R$color;
import com.android.systemui.R$dimen;
import com.android.systemui.R$drawable;
import com.android.systemui.R$id;
import com.android.systemui.R$layout;
import com.android.systemui.R$string;
import com.android.systemui.R$style;
import com.android.systemui.accessibility.floatingmenu.AnnotationLinkSpan;
import com.android.systemui.animation.DialogLaunchAnimator;
import com.android.systemui.qs.tiles.dialog.InternetDialogController;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.wifitrackerlib.WifiEntry;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/tiles/dialog/InternetDialog.class */
public class InternetDialog extends SystemUIDialog implements InternetDialogController.InternetDialogCallback {
    public static final boolean DEBUG = Log.isLoggable("InternetDialog", 3);
    public InternetAdapter mAdapter;
    public Button mAirplaneModeButton;
    public TextView mAirplaneModeSummaryText;
    public AlertDialog mAlertDialog;
    public final Executor mBackgroundExecutor;
    public Drawable mBackgroundOff;
    public Drawable mBackgroundOn;
    public boolean mCanChangeWifiState;
    public boolean mCanConfigMobileData;
    public boolean mCanConfigWifi;
    public LinearLayout mConnectedWifListLayout;
    public WifiEntry mConnectedWifiEntry;
    public ImageView mConnectedWifiIcon;
    public TextView mConnectedWifiSummaryText;
    public TextView mConnectedWifiTitleText;
    public Context mContext;
    public int mDefaultDataSubId;
    public final DialogLaunchAnimator mDialogLaunchAnimator;
    public View mDialogView;
    public View mDivider;
    public Button mDoneButton;
    public LinearLayout mEthernetLayout;
    public final Handler mHandler;
    public boolean mHasMoreWifiEntries;
    public final Runnable mHideProgressBarRunnable;
    public Runnable mHideSearchingRunnable;
    public InternetDialogController mInternetDialogController;
    public InternetDialogFactory mInternetDialogFactory;
    public LinearLayout mInternetDialogLayout;
    public TextView mInternetDialogSubTitle;
    public TextView mInternetDialogTitle;
    public boolean mIsProgressBarVisible;
    public boolean mIsSearchingHidden;
    public KeyguardStateController mKeyguard;
    public Switch mMobileDataToggle;
    public LinearLayout mMobileNetworkLayout;
    public TextView mMobileSummaryText;
    public TextView mMobileTitleText;
    public View mMobileToggleDivider;
    public ProgressBar mProgressBar;
    public LinearLayout mSecondaryMobileNetworkLayout;
    public TextView mSecondaryMobileSummaryText;
    public TextView mSecondaryMobileTitleText;
    public LinearLayout mSeeAllLayout;
    public ImageView mSignalIcon;
    public SubscriptionManager mSubscriptionManager;
    public TelephonyManager mTelephonyManager;
    public LinearLayout mTurnWifiOnLayout;
    public UiEventLogger mUiEventLogger;
    public Switch mWiFiToggle;
    public int mWifiEntriesCount;
    public int mWifiNetworkHeight;
    public RecyclerView mWifiRecyclerView;
    public LinearLayout mWifiScanNotifyLayout;
    public TextView mWifiScanNotifyText;
    public ImageView mWifiSettingsIcon;
    public TextView mWifiToggleTitleText;

    /* loaded from: mainsysui33.jar:com/android/systemui/qs/tiles/dialog/InternetDialog$InternetDialogEvent.class */
    public enum InternetDialogEvent implements UiEventLogger.UiEventEnum {
        INTERNET_DIALOG_SHOW(843);
        
        private final int mId;

        InternetDialogEvent(int i) {
            this.mId = i;
        }

        public int getId() {
            return this.mId;
        }
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.tiles.dialog.InternetDialog$$ExternalSyntheticLambda26.run():void] */
    public static /* synthetic */ void $r8$lambda$0CADgPdbHyG4YNSvgtxpRiSmi30(InternetDialog internetDialog, int i, ImageView imageView) {
        internetDialog.lambda$setMobileDataLayout$10(i, imageView);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.tiles.dialog.InternetDialog$$ExternalSyntheticLambda18.run():void] */
    public static /* synthetic */ void $r8$lambda$4NzwTZU7dN71UEzdlHj0IKK3ebE(InternetDialog internetDialog) {
        internetDialog.lambda$onDataConnectionStateChanged$23();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.tiles.dialog.InternetDialog$$ExternalSyntheticLambda20.onClick(android.content.DialogInterface, int):void] */
    /* renamed from: $r8$lambda$7QBF-huuylnbYiL6E4VrpAfEQDU */
    public static /* synthetic */ void m4063$r8$lambda$7QBFhuuylnbYiL6E4VrpAfEQDU(InternetDialog internetDialog, DialogInterface dialogInterface, int i) {
        internetDialog.lambda$showTurnOffMobileDialog$11(dialogInterface, i);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.tiles.dialog.InternetDialog$$ExternalSyntheticLambda30.run():void] */
    public static /* synthetic */ void $r8$lambda$CE3l8AMm1QZgBL08oSXcrjdMK8M(InternetDialog internetDialog, Drawable drawable) {
        internetDialog.lambda$setMobileDataLayout$7(drawable);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.tiles.dialog.InternetDialog$$ExternalSyntheticLambda19.run():void] */
    /* renamed from: $r8$lambda$E6WkWr9ywSpobYn9NQzqfTCR-P4 */
    public static /* synthetic */ void m4064$r8$lambda$E6WkWr9ywSpobYn9NQzqfTCRP4(InternetDialog internetDialog) {
        internetDialog.lambda$onUserMobileDataStateChanged$21();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.tiles.dialog.InternetDialog$$ExternalSyntheticLambda1.run():void] */
    public static /* synthetic */ void $r8$lambda$EKJgpuZG9ictHM_gCrVbveR1_iY(InternetDialog internetDialog) {
        internetDialog.lambda$new$1();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.tiles.dialog.InternetDialog$$ExternalSyntheticLambda28.onClick(android.content.DialogInterface, int):void] */
    public static /* synthetic */ void $r8$lambda$GPb2tGlmS63DEv6_eqrlHcVbEps(InternetDialog internetDialog, int i, DialogInterface dialogInterface, int i2) {
        internetDialog.lambda$showTurnOffAutoDataSwitchDialog$15(i, dialogInterface, i2);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.tiles.dialog.InternetDialog$$ExternalSyntheticLambda17.run():void] */
    /* renamed from: $r8$lambda$JJcR8b6e3uO7BIovT2Wc1G-j2KY */
    public static /* synthetic */ void m4065$r8$lambda$JJcR8b6e3uO7BIovT2Wc1Gj2KY(InternetDialog internetDialog, WifiEntry wifiEntry, List list, boolean z, boolean z2) {
        internetDialog.lambda$onAccessPointsChanged$26(wifiEntry, list, z, z2);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.tiles.dialog.InternetDialog$$ExternalSyntheticLambda12.run():void] */
    /* renamed from: $r8$lambda$KJJLdsLb_IJlhV_sRAmEMvme-_A */
    public static /* synthetic */ void m4066$r8$lambda$KJJLdsLb_IJlhV_sRAmEMvme_A(InternetDialog internetDialog) {
        internetDialog.lambda$onCapabilitiesChanged$18();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.tiles.dialog.InternetDialog$$ExternalSyntheticLambda3.onClick(android.view.View):void] */
    public static /* synthetic */ void $r8$lambda$PrGpIZu9U35k9sUDAkWyBuXyY1s(InternetDialog internetDialog, View view) {
        internetDialog.lambda$setOnClickListener$2(view);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.tiles.dialog.InternetDialog$$ExternalSyntheticLambda8.onClick(android.view.View):void] */
    public static /* synthetic */ void $r8$lambda$VWHTXHOhPbrdt1wEsU6MVWlbx4s(InternetDialog internetDialog, View view) {
        internetDialog.lambda$setOnClickListener$5(view);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.tiles.dialog.InternetDialog$$ExternalSyntheticLambda14.run():void] */
    public static /* synthetic */ void $r8$lambda$WVOEyB9Lo7vEZLhKDEByOP_9ANw(InternetDialog internetDialog) {
        internetDialog.lambda$onSubscriptionsChanged$20();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.tiles.dialog.InternetDialog$$ExternalSyntheticLambda15.run():void] */
    public static /* synthetic */ void $r8$lambda$X4Bc1p1P6pWapNKTGwz8VRXWpOY(InternetDialog internetDialog) {
        internetDialog.lambda$onServiceStateChanged$22();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.tiles.dialog.InternetDialog$$ExternalSyntheticLambda16.run():void] */
    public static /* synthetic */ void $r8$lambda$_BYFiNi8Lh4l9c7TgtgmpSnUBFM(InternetDialog internetDialog) {
        internetDialog.lambda$onDisplayInfoChanged$25();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.tiles.dialog.InternetDialog$$ExternalSyntheticLambda9.onClick(android.view.View):void] */
    /* renamed from: $r8$lambda$axkiz1h-wgFDACftw3OBC50Y9WI */
    public static /* synthetic */ void m4067$r8$lambda$axkiz1hwgFDACftw3OBC50Y9WI(InternetDialog internetDialog, View view) {
        internetDialog.lambda$setOnClickListener$6(view);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.tiles.dialog.InternetDialog$$ExternalSyntheticLambda11.run():void] */
    public static /* synthetic */ void $r8$lambda$eU4bAZRsEeeXUsm9f2vUn9q0a5w(InternetDialog internetDialog) {
        internetDialog.lambda$onSimStateChanged$17();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.tiles.dialog.InternetDialog$$ExternalSyntheticLambda24.run():void] */
    public static /* synthetic */ void $r8$lambda$iwp3Ye28i4C8iZ3zk3yR2e8pVWU(InternetDialog internetDialog) {
        internetDialog.lambda$setMobileDataLayout$8();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.tiles.dialog.InternetDialog$$ExternalSyntheticLambda10.run():void] */
    /* renamed from: $r8$lambda$kOryJYC6vlcARE19goST3kj-2sQ */
    public static /* synthetic */ void m4068$r8$lambda$kOryJYC6vlcARE19goST3kj2sQ(InternetDialog internetDialog) {
        internetDialog.lambda$onLost$19();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.tiles.dialog.InternetDialog$$ExternalSyntheticLambda27.onClick(android.content.DialogInterface, int):void] */
    public static /* synthetic */ void $r8$lambda$msholHtzlH86y_jRTG43pDqLOVY(DialogInterface dialogInterface, int i) {
        lambda$showTurnOffAutoDataSwitchDialog$14(dialogInterface, i);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.tiles.dialog.InternetDialog$$ExternalSyntheticLambda2.run():void] */
    public static /* synthetic */ void $r8$lambda$o53o7noPNjtieZ258npqlGyV2rQ(InternetDialog internetDialog) {
        internetDialog.lambda$onRefreshCarrierInfo$16();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.tiles.dialog.InternetDialog$$ExternalSyntheticLambda22.onCancel(android.content.DialogInterface):void] */
    public static /* synthetic */ void $r8$lambda$pOrm81aC_maR7KgJ0mEiW6EtNr8(InternetDialog internetDialog, DialogInterface dialogInterface) {
        internetDialog.lambda$showTurnOffMobileDialog$13(dialogInterface);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.tiles.dialog.InternetDialog$$ExternalSyntheticLambda13.run():void] */
    public static /* synthetic */ void $r8$lambda$qPT1jv6j51nrJbFjO29iRSEzJWw(InternetDialog internetDialog) {
        internetDialog.lambda$onSignalStrengthsChanged$24();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.tiles.dialog.InternetDialog$$ExternalSyntheticLambda7.onCheckedChanged(android.widget.CompoundButton, boolean):void] */
    public static /* synthetic */ void $r8$lambda$sM2l_1yTqOPkg09oWnVfRaz56ig(InternetDialog internetDialog, CompoundButton compoundButton, boolean z) {
        internetDialog.lambda$setOnClickListener$4(compoundButton, z);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.tiles.dialog.InternetDialog$$ExternalSyntheticLambda29.run():void] */
    /* renamed from: $r8$lambda$sjn2tBPXuu-R-_Vpm_XEUc9DKN4 */
    public static /* synthetic */ void m4069$r8$lambda$sjn2tBPXuuR_Vpm_XEUc9DKN4(ImageView imageView, Drawable drawable) {
        imageView.setImageDrawable(drawable);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.tiles.dialog.InternetDialog$$ExternalSyntheticLambda21.onClick(android.content.DialogInterface, int):void] */
    public static /* synthetic */ void $r8$lambda$tmuYbrJoVlZieyHoWFJqyhkzUbA(InternetDialog internetDialog, DialogInterface dialogInterface, int i) {
        internetDialog.lambda$showTurnOffMobileDialog$12(dialogInterface, i);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.tiles.dialog.InternetDialog$$ExternalSyntheticLambda4.onCheckedChanged(android.widget.CompoundButton, boolean):void] */
    /* renamed from: $r8$lambda$wgD4_8Mf4t2LBCQMdWOtL-wFeBc */
    public static /* synthetic */ void m4070$r8$lambda$wgD4_8Mf4t2LBCQMdWOtLwFeBc(InternetDialog internetDialog, CompoundButton compoundButton, boolean z) {
        internetDialog.lambda$setOnClickListener$3(compoundButton, z);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.tiles.dialog.InternetDialog$$ExternalSyntheticLambda0.run():void] */
    public static /* synthetic */ void $r8$lambda$yV74yZfoRU70HGjqVSG2ekqTOLw(InternetDialog internetDialog) {
        internetDialog.lambda$new$0();
    }

    /* JADX DEBUG: Multi-variable search result rejected for r5v0, resolved type: com.android.systemui.qs.tiles.dialog.InternetDialog */
    /* JADX WARN: Multi-variable type inference failed */
    public InternetDialog(Context context, InternetDialogFactory internetDialogFactory, InternetDialogController internetDialogController, boolean z, boolean z2, boolean z3, UiEventLogger uiEventLogger, DialogLaunchAnimator dialogLaunchAnimator, Handler handler, Executor executor, KeyguardStateController keyguardStateController) {
        super(context);
        this.mBackgroundOff = null;
        this.mDefaultDataSubId = -1;
        this.mHideProgressBarRunnable = new Runnable() { // from class: com.android.systemui.qs.tiles.dialog.InternetDialog$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                InternetDialog.$r8$lambda$yV74yZfoRU70HGjqVSG2ekqTOLw(InternetDialog.this);
            }
        };
        this.mHideSearchingRunnable = new Runnable() { // from class: com.android.systemui.qs.tiles.dialog.InternetDialog$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                InternetDialog.$r8$lambda$EKJgpuZG9ictHM_gCrVbveR1_iY(InternetDialog.this);
            }
        };
        if (DEBUG) {
            Log.d("InternetDialog", "Init InternetDialog");
        }
        this.mContext = getContext();
        this.mHandler = handler;
        this.mBackgroundExecutor = executor;
        this.mInternetDialogFactory = internetDialogFactory;
        this.mInternetDialogController = internetDialogController;
        this.mSubscriptionManager = internetDialogController.getSubscriptionManager();
        this.mDefaultDataSubId = this.mInternetDialogController.getDefaultDataSubscriptionId();
        this.mTelephonyManager = this.mInternetDialogController.getTelephonyManager();
        this.mCanConfigMobileData = z;
        this.mCanConfigWifi = z2;
        this.mCanChangeWifiState = WifiEnterpriseRestrictionUtils.isChangeWifiStateAllowed(context);
        this.mKeyguard = keyguardStateController;
        this.mUiEventLogger = uiEventLogger;
        this.mDialogLaunchAnimator = dialogLaunchAnimator;
        this.mAdapter = new InternetAdapter(this.mInternetDialogController);
        if (z3) {
            return;
        }
        getWindow().setType(2038);
    }

    public /* synthetic */ void lambda$new$0() {
        setProgressBarVisible(false);
    }

    public /* synthetic */ void lambda$new$1() {
        this.mIsSearchingHidden = true;
        this.mInternetDialogSubTitle.setText(getSubtitleText());
    }

    public /* synthetic */ void lambda$onAccessPointsChanged$26(WifiEntry wifiEntry, List list, boolean z, boolean z2) {
        this.mConnectedWifiEntry = wifiEntry;
        this.mWifiEntriesCount = list == null ? 0 : list.size();
        this.mHasMoreWifiEntries = z;
        updateDialog(z2);
        this.mAdapter.setWifiEntries(list, this.mWifiEntriesCount);
        this.mAdapter.notifyDataSetChanged();
    }

    public /* synthetic */ void lambda$onCapabilitiesChanged$18() {
        updateDialog(true);
    }

    public /* synthetic */ void lambda$onDataConnectionStateChanged$23() {
        updateDialog(true);
    }

    public /* synthetic */ void lambda$onDisplayInfoChanged$25() {
        updateDialog(true);
    }

    public /* synthetic */ void lambda$onLost$19() {
        updateDialog(true);
    }

    public /* synthetic */ void lambda$onRefreshCarrierInfo$16() {
        updateDialog(true);
    }

    public /* synthetic */ void lambda$onServiceStateChanged$22() {
        updateDialog(true);
    }

    public /* synthetic */ void lambda$onSignalStrengthsChanged$24() {
        updateDialog(true);
    }

    public /* synthetic */ void lambda$onSimStateChanged$17() {
        updateDialog(true);
    }

    public /* synthetic */ void lambda$onSubscriptionsChanged$20() {
        updateDialog(true);
    }

    public /* synthetic */ void lambda$onUserMobileDataStateChanged$21() {
        updateDialog(true);
    }

    public /* synthetic */ void lambda$setMobileDataLayout$10(int i, final ImageView imageView) {
        final Drawable signalStrengthDrawable = getSignalStrengthDrawable(i);
        this.mHandler.post(new Runnable() { // from class: com.android.systemui.qs.tiles.dialog.InternetDialog$$ExternalSyntheticLambda29
            @Override // java.lang.Runnable
            public final void run() {
                InternetDialog.m4069$r8$lambda$sjn2tBPXuuR_Vpm_XEUc9DKN4(imageView, signalStrengthDrawable);
            }
        });
    }

    public /* synthetic */ void lambda$setMobileDataLayout$7(Drawable drawable) {
        this.mSignalIcon.setImageDrawable(drawable);
    }

    public /* synthetic */ void lambda$setMobileDataLayout$8() {
        final Drawable signalStrengthDrawable = getSignalStrengthDrawable(this.mDefaultDataSubId);
        this.mHandler.post(new Runnable() { // from class: com.android.systemui.qs.tiles.dialog.InternetDialog$$ExternalSyntheticLambda30
            @Override // java.lang.Runnable
            public final void run() {
                InternetDialog.$r8$lambda$CE3l8AMm1QZgBL08oSXcrjdMK8M(InternetDialog.this, signalStrengthDrawable);
            }
        });
    }

    public /* synthetic */ void lambda$setOnClickListener$2(View view) {
        int activeAutoSwitchNonDdsSubId = this.mInternetDialogController.getActiveAutoSwitchNonDdsSubId();
        if (activeAutoSwitchNonDdsSubId != -1) {
            showTurnOffAutoDataSwitchDialog(activeAutoSwitchNonDdsSubId);
        }
        if (!this.mInternetDialogController.isMobileDataEnabled() || this.mInternetDialogController.isDeviceLocked() || this.mInternetDialogController.activeNetworkIsCellular()) {
            return;
        }
        this.mInternetDialogController.connectCarrierNetwork();
    }

    public /* synthetic */ void lambda$setOnClickListener$3(CompoundButton compoundButton, boolean z) {
        if (!z && shouldShowMobileDialog()) {
            showTurnOffMobileDialog();
        } else if (shouldShowMobileDialog() || this.mInternetDialogController.isMobileDataEnabled() == z) {
        } else {
            this.mInternetDialogController.setMobileDataEnabled(this.mContext, this.mDefaultDataSubId, z, false);
        }
    }

    public /* synthetic */ void lambda$setOnClickListener$4(CompoundButton compoundButton, boolean z) {
        if (this.mInternetDialogController.isWifiEnabled() == z) {
            return;
        }
        this.mInternetDialogController.setWifiEnabled(z);
    }

    /* JADX DEBUG: Multi-variable search result rejected for r2v0, resolved type: com.android.systemui.qs.tiles.dialog.InternetDialog */
    /* JADX WARN: Multi-variable type inference failed */
    public /* synthetic */ void lambda$setOnClickListener$5(View view) {
        dismiss();
    }

    public /* synthetic */ void lambda$setOnClickListener$6(View view) {
        this.mInternetDialogController.setAirplaneModeDisabled();
    }

    public static /* synthetic */ void lambda$showTurnOffAutoDataSwitchDialog$14(DialogInterface dialogInterface, int i) {
    }

    public /* synthetic */ void lambda$showTurnOffAutoDataSwitchDialog$15(int i, DialogInterface dialogInterface, int i2) {
        this.mInternetDialogController.setAutoDataSwitchMobileDataPolicy(i, false);
        LinearLayout linearLayout = this.mSecondaryMobileNetworkLayout;
        if (linearLayout != null) {
            linearLayout.setVisibility(8);
        }
    }

    public /* synthetic */ void lambda$showTurnOffMobileDialog$11(DialogInterface dialogInterface, int i) {
        this.mMobileDataToggle.setChecked(true);
    }

    public /* synthetic */ void lambda$showTurnOffMobileDialog$12(DialogInterface dialogInterface, int i) {
        this.mInternetDialogController.setMobileDataEnabled(this.mContext, this.mDefaultDataSubId, false, false);
        this.mMobileDataToggle.setChecked(false);
        Prefs.putBoolean(this.mContext, "QsHasTurnedOffMobileData", true);
    }

    public /* synthetic */ void lambda$showTurnOffMobileDialog$13(DialogInterface dialogInterface) {
        this.mMobileDataToggle.setChecked(true);
    }

    /* JADX DEBUG: Multi-variable search result rejected for r3v0, resolved type: com.android.systemui.qs.tiles.dialog.InternetDialog */
    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.android.systemui.qs.tiles.dialog.InternetDialogController.InternetDialogCallback
    public void dismissDialog() {
        if (DEBUG) {
            Log.d("InternetDialog", "dismissDialog");
        }
        this.mInternetDialogFactory.destroyDialog();
        dismiss();
    }

    public CharSequence getDialogTitleText() {
        return this.mInternetDialogController.getDialogTitleText();
    }

    public String getMobileNetworkSummary(int i) {
        return this.mInternetDialogController.getMobileNetworkSummary(i);
    }

    public CharSequence getMobileNetworkTitle(int i) {
        return this.mInternetDialogController.getMobileNetworkTitle(i);
    }

    public final Drawable getSignalStrengthDrawable(int i) {
        return this.mInternetDialogController.getSignalStrengthDrawable(i);
    }

    public CharSequence getSubtitleText() {
        return this.mInternetDialogController.getSubtitleText(this.mIsProgressBarVisible && !this.mIsSearchingHidden);
    }

    public int getWifiListMaxCount() {
        int i = this.mEthernetLayout.getVisibility() == 0 ? 3 : 4;
        int i2 = i;
        if (this.mMobileNetworkLayout.getVisibility() == 0) {
            i2 = i - 1;
        }
        int i3 = i2 > 3 ? 3 : i2;
        int i4 = i3;
        if (this.mConnectedWifListLayout.getVisibility() == 0) {
            i4 = i3 - 1;
        }
        return i4;
    }

    public void hideWifiViews() {
        setProgressBarVisible(false);
        this.mTurnWifiOnLayout.setVisibility(8);
        this.mConnectedWifListLayout.setVisibility(8);
        this.mWifiRecyclerView.setVisibility(8);
        this.mSeeAllLayout.setVisibility(8);
    }

    @Override // com.android.systemui.qs.tiles.dialog.InternetDialogController.InternetDialogCallback
    public void onAccessPointsChanged(final List<WifiEntry> list, final WifiEntry wifiEntry, final boolean z) {
        final boolean z2 = this.mMobileNetworkLayout.getVisibility() == 0 && this.mInternetDialogController.isAirplaneModeEnabled();
        this.mHandler.post(new Runnable() { // from class: com.android.systemui.qs.tiles.dialog.InternetDialog$$ExternalSyntheticLambda17
            @Override // java.lang.Runnable
            public final void run() {
                InternetDialog.m4065$r8$lambda$JJcR8b6e3uO7BIovT2Wc1Gj2KY(InternetDialog.this, wifiEntry, list, z, z2);
            }
        });
    }

    @Override // com.android.systemui.qs.tiles.dialog.InternetDialogController.InternetDialogCallback
    public void onCapabilitiesChanged(Network network, NetworkCapabilities networkCapabilities) {
        this.mHandler.post(new Runnable() { // from class: com.android.systemui.qs.tiles.dialog.InternetDialog$$ExternalSyntheticLambda12
            @Override // java.lang.Runnable
            public final void run() {
                InternetDialog.m4066$r8$lambda$KJJLdsLb_IJlhV_sRAmEMvme_A(InternetDialog.this);
            }
        });
    }

    public void onClickConnectedSecondarySub(View view) {
        this.mInternetDialogController.launchMobileNetworkSettings(view);
    }

    public void onClickConnectedWifi(View view) {
        WifiEntry wifiEntry = this.mConnectedWifiEntry;
        if (wifiEntry == null) {
            return;
        }
        this.mInternetDialogController.launchWifiDetailsSetting(wifiEntry.getKey(), view);
    }

    public void onClickSeeMoreButton(View view) {
        this.mInternetDialogController.launchNetworkSetting(view);
    }

    /* JADX DEBUG: Multi-variable search result rejected for r5v0, resolved type: com.android.systemui.qs.tiles.dialog.InternetDialog */
    /* JADX WARN: Multi-variable type inference failed */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (DEBUG) {
            Log.d("InternetDialog", "onCreate");
        }
        this.mUiEventLogger.log(InternetDialogEvent.INTERNET_DIALOG_SHOW);
        View inflate = LayoutInflater.from(this.mContext).inflate(R$layout.internet_connectivity_dialog, (ViewGroup) null);
        this.mDialogView = inflate;
        inflate.setAccessibilityPaneTitle(this.mContext.getText(R$string.accessibility_desc_quick_settings));
        Window window = getWindow();
        window.setContentView(this.mDialogView);
        window.setWindowAnimations(R$style.Animation_InternetDialog);
        this.mWifiNetworkHeight = this.mContext.getResources().getDimensionPixelSize(R$dimen.internet_dialog_wifi_network_height);
        this.mInternetDialogLayout = (LinearLayout) this.mDialogView.requireViewById(R$id.internet_connectivity_dialog);
        this.mInternetDialogTitle = (TextView) this.mDialogView.requireViewById(R$id.internet_dialog_title);
        this.mInternetDialogSubTitle = (TextView) this.mDialogView.requireViewById(R$id.internet_dialog_subtitle);
        this.mDivider = this.mDialogView.requireViewById(R$id.divider);
        this.mProgressBar = (ProgressBar) this.mDialogView.requireViewById(R$id.wifi_searching_progress);
        this.mEthernetLayout = (LinearLayout) this.mDialogView.requireViewById(R$id.ethernet_layout);
        this.mMobileNetworkLayout = (LinearLayout) this.mDialogView.requireViewById(R$id.mobile_network_layout);
        this.mTurnWifiOnLayout = (LinearLayout) this.mDialogView.requireViewById(R$id.turn_on_wifi_layout);
        this.mWifiToggleTitleText = (TextView) this.mDialogView.requireViewById(R$id.wifi_toggle_title);
        this.mWifiScanNotifyLayout = (LinearLayout) this.mDialogView.requireViewById(R$id.wifi_scan_notify_layout);
        this.mWifiScanNotifyText = (TextView) this.mDialogView.requireViewById(R$id.wifi_scan_notify_text);
        this.mConnectedWifListLayout = (LinearLayout) this.mDialogView.requireViewById(R$id.wifi_connected_layout);
        this.mConnectedWifiIcon = (ImageView) this.mDialogView.requireViewById(R$id.wifi_connected_icon);
        this.mConnectedWifiTitleText = (TextView) this.mDialogView.requireViewById(R$id.wifi_connected_title);
        this.mConnectedWifiSummaryText = (TextView) this.mDialogView.requireViewById(R$id.wifi_connected_summary);
        this.mWifiSettingsIcon = (ImageView) this.mDialogView.requireViewById(R$id.wifi_settings_icon);
        this.mWifiRecyclerView = (RecyclerView) this.mDialogView.requireViewById(R$id.wifi_list_layout);
        this.mSeeAllLayout = (LinearLayout) this.mDialogView.requireViewById(R$id.see_all_layout);
        this.mDoneButton = (Button) this.mDialogView.requireViewById(R$id.done_button);
        this.mAirplaneModeButton = (Button) this.mDialogView.requireViewById(R$id.apm_button);
        this.mSignalIcon = (ImageView) this.mDialogView.requireViewById(R$id.signal_icon);
        this.mMobileTitleText = (TextView) this.mDialogView.requireViewById(R$id.mobile_title);
        this.mMobileSummaryText = (TextView) this.mDialogView.requireViewById(R$id.mobile_summary);
        this.mAirplaneModeSummaryText = (TextView) this.mDialogView.requireViewById(R$id.airplane_mode_summary);
        this.mMobileToggleDivider = this.mDialogView.requireViewById(R$id.mobile_toggle_divider);
        this.mMobileDataToggle = (Switch) this.mDialogView.requireViewById(R$id.mobile_toggle);
        this.mWiFiToggle = (Switch) this.mDialogView.requireViewById(R$id.wifi_toggle);
        this.mBackgroundOn = this.mContext.getDrawable(R$drawable.settingslib_switch_bar_bg_on);
        this.mInternetDialogTitle.setText(getDialogTitleText());
        this.mInternetDialogTitle.setGravity(8388627);
        this.mBackgroundOff = this.mContext.getDrawable(R$drawable.internet_dialog_selected_effect);
        setOnClickListener();
        this.mTurnWifiOnLayout.setBackground(null);
        this.mAirplaneModeButton.setVisibility(this.mInternetDialogController.isAirplaneModeEnabled() ? 0 : 8);
        this.mWifiRecyclerView.setLayoutManager(new LinearLayoutManager(this.mContext));
        this.mWifiRecyclerView.setAdapter(this.mAdapter);
    }

    @Override // com.android.systemui.qs.tiles.dialog.InternetDialogController.InternetDialogCallback
    public void onDataConnectionStateChanged(int i, int i2) {
        this.mHandler.post(new Runnable() { // from class: com.android.systemui.qs.tiles.dialog.InternetDialog$$ExternalSyntheticLambda18
            @Override // java.lang.Runnable
            public final void run() {
                InternetDialog.$r8$lambda$4NzwTZU7dN71UEzdlHj0IKK3ebE(InternetDialog.this);
            }
        });
    }

    @Override // com.android.systemui.qs.tiles.dialog.InternetDialogController.InternetDialogCallback
    public void onDisplayInfoChanged(TelephonyDisplayInfo telephonyDisplayInfo) {
        this.mHandler.post(new Runnable() { // from class: com.android.systemui.qs.tiles.dialog.InternetDialog$$ExternalSyntheticLambda16
            @Override // java.lang.Runnable
            public final void run() {
                InternetDialog.$r8$lambda$_BYFiNi8Lh4l9c7TgtgmpSnUBFM(InternetDialog.this);
            }
        });
    }

    @Override // com.android.systemui.qs.tiles.dialog.InternetDialogController.InternetDialogCallback
    public void onLost(Network network) {
        this.mHandler.post(new Runnable() { // from class: com.android.systemui.qs.tiles.dialog.InternetDialog$$ExternalSyntheticLambda10
            @Override // java.lang.Runnable
            public final void run() {
                InternetDialog.m4068$r8$lambda$kOryJYC6vlcARE19goST3kj2sQ(InternetDialog.this);
            }
        });
    }

    @Override // com.android.systemui.qs.tiles.dialog.InternetDialogController.InternetDialogCallback
    public void onRefreshCarrierInfo() {
        this.mHandler.post(new Runnable() { // from class: com.android.systemui.qs.tiles.dialog.InternetDialog$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                InternetDialog.$r8$lambda$o53o7noPNjtieZ258npqlGyV2rQ(InternetDialog.this);
            }
        });
    }

    @Override // com.android.systemui.qs.tiles.dialog.InternetDialogController.InternetDialogCallback
    public void onServiceStateChanged(ServiceState serviceState) {
        this.mHandler.post(new Runnable() { // from class: com.android.systemui.qs.tiles.dialog.InternetDialog$$ExternalSyntheticLambda15
            @Override // java.lang.Runnable
            public final void run() {
                InternetDialog.$r8$lambda$X4Bc1p1P6pWapNKTGwz8VRXWpOY(InternetDialog.this);
            }
        });
    }

    @Override // com.android.systemui.qs.tiles.dialog.InternetDialogController.InternetDialogCallback
    public void onSignalStrengthsChanged(SignalStrength signalStrength) {
        this.mHandler.post(new Runnable() { // from class: com.android.systemui.qs.tiles.dialog.InternetDialog$$ExternalSyntheticLambda13
            @Override // java.lang.Runnable
            public final void run() {
                InternetDialog.$r8$lambda$qPT1jv6j51nrJbFjO29iRSEzJWw(InternetDialog.this);
            }
        });
    }

    @Override // com.android.systemui.qs.tiles.dialog.InternetDialogController.InternetDialogCallback
    public void onSimStateChanged() {
        this.mHandler.post(new Runnable() { // from class: com.android.systemui.qs.tiles.dialog.InternetDialog$$ExternalSyntheticLambda11
            @Override // java.lang.Runnable
            public final void run() {
                InternetDialog.$r8$lambda$eU4bAZRsEeeXUsm9f2vUn9q0a5w(InternetDialog.this);
            }
        });
    }

    public void onStart() {
        super.onStart();
        if (DEBUG) {
            Log.d("InternetDialog", "onStart");
        }
        this.mInternetDialogController.onStart(this, this.mCanConfigWifi);
        if (this.mCanConfigWifi) {
            return;
        }
        hideWifiViews();
    }

    public void onStop() {
        super.onStop();
        if (DEBUG) {
            Log.d("InternetDialog", "onStop");
        }
        this.mHandler.removeCallbacks(this.mHideProgressBarRunnable);
        this.mHandler.removeCallbacks(this.mHideSearchingRunnable);
        this.mMobileNetworkLayout.setOnClickListener(null);
        this.mMobileDataToggle.setOnCheckedChangeListener(null);
        this.mConnectedWifListLayout.setOnClickListener(null);
        LinearLayout linearLayout = this.mSecondaryMobileNetworkLayout;
        if (linearLayout != null) {
            linearLayout.setOnClickListener(null);
        }
        this.mSeeAllLayout.setOnClickListener(null);
        this.mWiFiToggle.setOnCheckedChangeListener(null);
        this.mDoneButton.setOnClickListener(null);
        this.mAirplaneModeButton.setOnClickListener(null);
        this.mInternetDialogController.onStop();
        this.mInternetDialogFactory.destroyDialog();
    }

    @Override // com.android.systemui.qs.tiles.dialog.InternetDialogController.InternetDialogCallback
    public void onSubscriptionsChanged(int i) {
        this.mDefaultDataSubId = i;
        this.mTelephonyManager = this.mTelephonyManager.createForSubscriptionId(i);
        this.mHandler.post(new Runnable() { // from class: com.android.systemui.qs.tiles.dialog.InternetDialog$$ExternalSyntheticLambda14
            @Override // java.lang.Runnable
            public final void run() {
                InternetDialog.$r8$lambda$WVOEyB9Lo7vEZLhKDEByOP_9ANw(InternetDialog.this);
            }
        });
    }

    @Override // com.android.systemui.qs.tiles.dialog.InternetDialogController.InternetDialogCallback
    public void onUserMobileDataStateChanged(boolean z) {
        this.mHandler.post(new Runnable() { // from class: com.android.systemui.qs.tiles.dialog.InternetDialog$$ExternalSyntheticLambda19
            @Override // java.lang.Runnable
            public final void run() {
                InternetDialog.m4064$r8$lambda$E6WkWr9ywSpobYn9NQzqfTCRP4(InternetDialog.this);
            }
        });
    }

    /* JADX DEBUG: Multi-variable search result rejected for r3v0, resolved type: com.android.systemui.qs.tiles.dialog.InternetDialog */
    /* JADX WARN: Multi-variable type inference failed */
    public void onWindowFocusChanged(boolean z) {
        super/*android.app.AlertDialog*/.onWindowFocusChanged(z);
        AlertDialog alertDialog = this.mAlertDialog;
        if (alertDialog == null || alertDialog.isShowing() || z || !isShowing()) {
            return;
        }
        dismiss();
    }

    /* JADX DEBUG: Multi-variable search result rejected for r7v0, resolved type: com.android.systemui.qs.tiles.dialog.InternetDialog */
    /* JADX WARN: Multi-variable type inference failed */
    public final void setMobileDataLayout(boolean z, boolean z2) {
        boolean z3 = z || z2;
        if (DEBUG) {
            Log.d("InternetDialog", "setMobileDataLayout, isCarrierNetworkActive = " + z2);
        }
        boolean isWifiEnabled = this.mInternetDialogController.isWifiEnabled();
        if (!this.mInternetDialogController.hasActiveSubId() && (!isWifiEnabled || !z2)) {
            this.mMobileNetworkLayout.setVisibility(8);
            LinearLayout linearLayout = this.mSecondaryMobileNetworkLayout;
            if (linearLayout != null) {
                linearLayout.setVisibility(8);
                return;
            }
            return;
        }
        this.mMobileNetworkLayout.setVisibility(0);
        this.mMobileDataToggle.setChecked(this.mInternetDialogController.isMobileDataEnabled());
        this.mMobileTitleText.setText(getMobileNetworkTitle(this.mDefaultDataSubId));
        String mobileNetworkSummary = getMobileNetworkSummary(this.mDefaultDataSubId);
        if (TextUtils.isEmpty(mobileNetworkSummary)) {
            this.mMobileSummaryText.setVisibility(8);
        } else {
            this.mMobileSummaryText.setText(Html.fromHtml(mobileNetworkSummary, 0));
            this.mMobileSummaryText.setBreakStrategy(0);
            this.mMobileSummaryText.setVisibility(0);
        }
        this.mBackgroundExecutor.execute(new Runnable() { // from class: com.android.systemui.qs.tiles.dialog.InternetDialog$$ExternalSyntheticLambda24
            @Override // java.lang.Runnable
            public final void run() {
                InternetDialog.$r8$lambda$iwp3Ye28i4C8iZ3zk3yR2e8pVWU(InternetDialog.this);
            }
        });
        TypedArray obtainStyledAttributes = this.mContext.obtainStyledAttributes(R$style.InternetDialog_Divider_Active, new int[]{16842964});
        int colorAttrDefaultColor = Utils.getColorAttrDefaultColor(this.mContext, 16842808);
        View view = this.mMobileToggleDivider;
        int i = colorAttrDefaultColor;
        if (z3) {
            i = obtainStyledAttributes.getColor(0, colorAttrDefaultColor);
        }
        view.setBackgroundColor(i);
        obtainStyledAttributes.recycle();
        this.mMobileDataToggle.setVisibility(this.mCanConfigMobileData ? 0 : 4);
        View view2 = this.mMobileToggleDivider;
        int i2 = 4;
        if (this.mCanConfigMobileData) {
            i2 = 0;
        }
        view2.setVisibility(i2);
        final int activeAutoSwitchNonDdsSubId = this.mInternetDialogController.getActiveAutoSwitchNonDdsSubId();
        int i3 = activeAutoSwitchNonDdsSubId != -1 ? 0 : 8;
        int i4 = z3 ? R$style.TextAppearance_InternetDialog_Secondary_Active : R$style.TextAppearance_InternetDialog_Secondary;
        if (i3 == 0) {
            ViewStub viewStub = (ViewStub) this.mDialogView.findViewById(R$id.secondary_mobile_network_stub);
            if (viewStub != null) {
                viewStub.inflate();
            }
            LinearLayout linearLayout2 = (LinearLayout) findViewById(R$id.secondary_mobile_network_layout);
            this.mSecondaryMobileNetworkLayout = linearLayout2;
            linearLayout2.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.qs.tiles.dialog.InternetDialog$$ExternalSyntheticLambda25
                @Override // android.view.View.OnClickListener
                public final void onClick(View view3) {
                    InternetDialog.this.onClickConnectedSecondarySub(view3);
                }
            });
            this.mSecondaryMobileNetworkLayout.setBackground(this.mBackgroundOn);
            TextView textView = (TextView) this.mDialogView.requireViewById(R$id.secondary_mobile_title);
            this.mSecondaryMobileTitleText = textView;
            textView.setText(getMobileNetworkTitle(activeAutoSwitchNonDdsSubId));
            TextView textView2 = this.mSecondaryMobileTitleText;
            int i5 = R$style.TextAppearance_InternetDialog_Active;
            textView2.setTextAppearance(i5);
            this.mSecondaryMobileSummaryText = (TextView) this.mDialogView.requireViewById(R$id.secondary_mobile_summary);
            String mobileNetworkSummary2 = getMobileNetworkSummary(activeAutoSwitchNonDdsSubId);
            if (!TextUtils.isEmpty(mobileNetworkSummary2)) {
                this.mSecondaryMobileSummaryText.setText(Html.fromHtml(mobileNetworkSummary2, 0));
                this.mSecondaryMobileSummaryText.setBreakStrategy(0);
                this.mSecondaryMobileSummaryText.setTextAppearance(i5);
            }
            final ImageView imageView = (ImageView) this.mDialogView.requireViewById(R$id.secondary_signal_icon);
            this.mBackgroundExecutor.execute(new Runnable() { // from class: com.android.systemui.qs.tiles.dialog.InternetDialog$$ExternalSyntheticLambda26
                @Override // java.lang.Runnable
                public final void run() {
                    InternetDialog.$r8$lambda$0CADgPdbHyG4YNSvgtxpRiSmi30(InternetDialog.this, activeAutoSwitchNonDdsSubId, imageView);
                }
            });
            ((ImageView) this.mDialogView.requireViewById(R$id.secondary_settings_icon)).setColorFilter(this.mContext.getColor(R$color.connected_network_primary_color));
            this.mMobileNetworkLayout.setBackground(this.mBackgroundOff);
            this.mMobileTitleText.setTextAppearance(R$style.TextAppearance_InternetDialog);
            this.mMobileSummaryText.setTextAppearance(R$style.TextAppearance_InternetDialog_Secondary);
            this.mSignalIcon.setColorFilter(this.mContext.getColor(R$color.connected_network_secondary_color));
        } else {
            this.mMobileNetworkLayout.setBackground(z3 ? this.mBackgroundOn : this.mBackgroundOff);
            this.mMobileTitleText.setTextAppearance(z3 ? R$style.TextAppearance_InternetDialog_Active : R$style.TextAppearance_InternetDialog);
            this.mMobileSummaryText.setTextAppearance(i4);
        }
        LinearLayout linearLayout3 = this.mSecondaryMobileNetworkLayout;
        if (linearLayout3 != null) {
            linearLayout3.setVisibility(i3);
        }
        if (!this.mInternetDialogController.isAirplaneModeEnabled()) {
            this.mAirplaneModeSummaryText.setVisibility(8);
            return;
        }
        this.mAirplaneModeSummaryText.setVisibility(0);
        this.mAirplaneModeSummaryText.setText(this.mContext.getText(R$string.airplane_mode));
        this.mAirplaneModeSummaryText.setTextAppearance(i4);
    }

    public final void setOnClickListener() {
        this.mMobileNetworkLayout.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.qs.tiles.dialog.InternetDialog$$ExternalSyntheticLambda3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                InternetDialog.$r8$lambda$PrGpIZu9U35k9sUDAkWyBuXyY1s(InternetDialog.this, view);
            }
        });
        this.mMobileDataToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.android.systemui.qs.tiles.dialog.InternetDialog$$ExternalSyntheticLambda4
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                InternetDialog.m4070$r8$lambda$wgD4_8Mf4t2LBCQMdWOtLwFeBc(InternetDialog.this, compoundButton, z);
            }
        });
        this.mConnectedWifListLayout.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.qs.tiles.dialog.InternetDialog$$ExternalSyntheticLambda5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                InternetDialog.this.onClickConnectedWifi(view);
            }
        });
        this.mSeeAllLayout.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.qs.tiles.dialog.InternetDialog$$ExternalSyntheticLambda6
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                InternetDialog.this.onClickSeeMoreButton(view);
            }
        });
        this.mWiFiToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.android.systemui.qs.tiles.dialog.InternetDialog$$ExternalSyntheticLambda7
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                InternetDialog.$r8$lambda$sM2l_1yTqOPkg09oWnVfRaz56ig(InternetDialog.this, compoundButton, z);
            }
        });
        this.mDoneButton.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.qs.tiles.dialog.InternetDialog$$ExternalSyntheticLambda8
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                InternetDialog.$r8$lambda$VWHTXHOhPbrdt1wEsU6MVWlbx4s(InternetDialog.this, view);
            }
        });
        this.mAirplaneModeButton.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.qs.tiles.dialog.InternetDialog$$ExternalSyntheticLambda9
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                InternetDialog.m4067$r8$lambda$axkiz1hwgFDACftw3OBC50Y9WI(InternetDialog.this, view);
            }
        });
    }

    public final void setProgressBarVisible(boolean z) {
        if (this.mIsProgressBarVisible == z) {
            return;
        }
        this.mIsProgressBarVisible = z;
        this.mProgressBar.setVisibility(z ? 0 : 8);
        this.mProgressBar.setIndeterminate(z);
        View view = this.mDivider;
        int i = 0;
        if (z) {
            i = 8;
        }
        view.setVisibility(i);
        this.mInternetDialogSubTitle.setText(getSubtitleText());
    }

    public final boolean shouldShowMobileDialog() {
        return this.mInternetDialogController.isMobileDataEnabled() && !Prefs.getBoolean(this.mContext, "QsHasTurnedOffMobileData", false);
    }

    public void showProgressBar() {
        if (!this.mInternetDialogController.isWifiEnabled() || this.mInternetDialogController.isDeviceLocked()) {
            setProgressBarVisible(false);
            return;
        }
        setProgressBarVisible(true);
        if (this.mConnectedWifiEntry != null || this.mWifiEntriesCount > 0) {
            this.mHandler.postDelayed(this.mHideProgressBarRunnable, 1500L);
        } else if (this.mIsSearchingHidden) {
        } else {
            this.mHandler.postDelayed(this.mHideSearchingRunnable, 1500L);
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for r8v0, resolved type: com.android.systemui.qs.tiles.dialog.InternetDialog */
    /* JADX WARN: Multi-variable type inference failed */
    public final void showTurnOffAutoDataSwitchDialog(final int i) {
        CharSequence mobileNetworkTitle = getMobileNetworkTitle(this.mDefaultDataSubId);
        String str = mobileNetworkTitle;
        if (TextUtils.isEmpty(mobileNetworkTitle)) {
            str = this.mContext.getString(R$string.mobile_data_disable_message_default_carrier);
        }
        AlertDialog create = new AlertDialog.Builder(this.mContext).setTitle(this.mContext.getString(R$string.auto_data_switch_disable_title, str)).setMessage(R$string.auto_data_switch_disable_message).setNegativeButton(R$string.auto_data_switch_dialog_negative_button, new DialogInterface.OnClickListener() { // from class: com.android.systemui.qs.tiles.dialog.InternetDialog$$ExternalSyntheticLambda27
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i2) {
                InternetDialog.$r8$lambda$msholHtzlH86y_jRTG43pDqLOVY(dialogInterface, i2);
            }
        }).setPositiveButton(R$string.auto_data_switch_dialog_positive_button, new DialogInterface.OnClickListener() { // from class: com.android.systemui.qs.tiles.dialog.InternetDialog$$ExternalSyntheticLambda28
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i2) {
                InternetDialog.$r8$lambda$GPb2tGlmS63DEv6_eqrlHcVbEps(InternetDialog.this, i, dialogInterface, i2);
            }
        }).create();
        this.mAlertDialog = create;
        create.getWindow().setType(2009);
        SystemUIDialog.setShowForAllUsers(this.mAlertDialog, true);
        SystemUIDialog.registerDismissListener(this.mAlertDialog);
        SystemUIDialog.setWindowOnTop(this.mAlertDialog, this.mKeyguard.isShowing());
        this.mDialogLaunchAnimator.showFromDialog(this.mAlertDialog, this, null, false);
    }

    /* JADX DEBUG: Multi-variable search result rejected for r8v0, resolved type: com.android.systemui.qs.tiles.dialog.InternetDialog */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x001d, code lost:
        if (r0 == false) goto L8;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void showTurnOffMobileDialog() {
        String string;
        CharSequence mobileNetworkTitle = getMobileNetworkTitle(this.mDefaultDataSubId);
        boolean isVoiceStateInService = this.mInternetDialogController.isVoiceStateInService(this.mDefaultDataSubId);
        if (!TextUtils.isEmpty(mobileNetworkTitle)) {
            string = mobileNetworkTitle;
        }
        string = this.mContext.getString(R$string.mobile_data_disable_message_default_carrier);
        AlertDialog create = new AlertDialog.Builder(this.mContext).setTitle(R$string.mobile_data_disable_title).setMessage(this.mContext.getString(R$string.mobile_data_disable_message, string)).setNegativeButton(17039360, new DialogInterface.OnClickListener() { // from class: com.android.systemui.qs.tiles.dialog.InternetDialog$$ExternalSyntheticLambda20
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                InternetDialog.m4063$r8$lambda$7QBFhuuylnbYiL6E4VrpAfEQDU(InternetDialog.this, dialogInterface, i);
            }
        }).setPositiveButton(17039669, new DialogInterface.OnClickListener() { // from class: com.android.systemui.qs.tiles.dialog.InternetDialog$$ExternalSyntheticLambda21
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                InternetDialog.$r8$lambda$tmuYbrJoVlZieyHoWFJqyhkzUbA(InternetDialog.this, dialogInterface, i);
            }
        }).create();
        this.mAlertDialog = create;
        create.setOnCancelListener(new DialogInterface.OnCancelListener() { // from class: com.android.systemui.qs.tiles.dialog.InternetDialog$$ExternalSyntheticLambda22
            @Override // android.content.DialogInterface.OnCancelListener
            public final void onCancel(DialogInterface dialogInterface) {
                InternetDialog.$r8$lambda$pOrm81aC_maR7KgJ0mEiW6EtNr8(InternetDialog.this, dialogInterface);
            }
        });
        this.mAlertDialog.getWindow().setType(2009);
        SystemUIDialog.setShowForAllUsers(this.mAlertDialog, true);
        SystemUIDialog.registerDismissListener(this.mAlertDialog);
        SystemUIDialog.setWindowOnTop(this.mAlertDialog, this.mKeyguard.isShowing());
        this.mDialogLaunchAnimator.showFromDialog(this.mAlertDialog, this, null, false);
    }

    public final void updateConnectedWifi(boolean z, boolean z2) {
        if (!z || this.mConnectedWifiEntry == null || z2) {
            this.mConnectedWifListLayout.setVisibility(8);
            return;
        }
        this.mConnectedWifListLayout.setVisibility(0);
        this.mConnectedWifiTitleText.setText(this.mConnectedWifiEntry.getTitle());
        this.mConnectedWifiSummaryText.setText(this.mConnectedWifiEntry.getSummary(false));
        this.mConnectedWifiIcon.setImageDrawable(this.mInternetDialogController.getInternetWifiDrawable(this.mConnectedWifiEntry));
        this.mWifiSettingsIcon.setColorFilter(this.mContext.getColor(R$color.connected_network_primary_color));
        LinearLayout linearLayout = this.mSecondaryMobileNetworkLayout;
        if (linearLayout != null) {
            linearLayout.setVisibility(8);
        }
    }

    public void updateDialog(boolean z) {
        if (DEBUG) {
            Log.d("InternetDialog", "updateDialog");
        }
        this.mInternetDialogTitle.setText(getDialogTitleText());
        this.mInternetDialogSubTitle.setText(getSubtitleText());
        this.mAirplaneModeButton.setVisibility(this.mInternetDialogController.isAirplaneModeEnabled() ? 0 : 8);
        updateEthernet();
        if (z) {
            setMobileDataLayout(this.mInternetDialogController.activeNetworkIsCellular(), this.mInternetDialogController.isCarrierNetworkActive());
        }
        if (this.mCanConfigWifi) {
            showProgressBar();
            boolean isDeviceLocked = this.mInternetDialogController.isDeviceLocked();
            boolean isWifiEnabled = this.mInternetDialogController.isWifiEnabled();
            boolean isWifiScanEnabled = this.mInternetDialogController.isWifiScanEnabled();
            updateWifiToggle(isWifiEnabled, isDeviceLocked);
            updateConnectedWifi(isWifiEnabled, isDeviceLocked);
            updateWifiListAndSeeAll(isWifiEnabled, isDeviceLocked);
            updateWifiScanNotify(isWifiEnabled, isWifiScanEnabled, isDeviceLocked);
        }
    }

    public final void updateEthernet() {
        this.mEthernetLayout.setVisibility(this.mInternetDialogController.hasEthernet() ? 0 : 8);
    }

    public final void updateWifiListAndSeeAll(boolean z, boolean z2) {
        if (!z || z2) {
            this.mWifiRecyclerView.setVisibility(8);
            this.mSeeAllLayout.setVisibility(8);
            return;
        }
        int wifiListMaxCount = getWifiListMaxCount();
        if (this.mAdapter.getItemCount() > wifiListMaxCount) {
            this.mHasMoreWifiEntries = true;
        }
        this.mAdapter.setMaxEntriesCount(wifiListMaxCount);
        int i = this.mWifiNetworkHeight * wifiListMaxCount;
        if (this.mWifiRecyclerView.getMinimumHeight() != i) {
            this.mWifiRecyclerView.setMinimumHeight(i);
        }
        int i2 = 0;
        this.mWifiRecyclerView.setVisibility(0);
        LinearLayout linearLayout = this.mSeeAllLayout;
        if (!this.mHasMoreWifiEntries) {
            i2 = 4;
        }
        linearLayout.setVisibility(i2);
    }

    /* JADX DEBUG: Multi-variable search result rejected for r7v0, resolved type: com.android.systemui.qs.tiles.dialog.InternetDialog */
    /* JADX WARN: Multi-variable type inference failed */
    public final void updateWifiScanNotify(boolean z, boolean z2, boolean z3) {
        if (z || !z2 || z3) {
            this.mWifiScanNotifyLayout.setVisibility(8);
            return;
        }
        if (TextUtils.isEmpty(this.mWifiScanNotifyText.getText())) {
            final InternetDialogController internetDialogController = this.mInternetDialogController;
            Objects.requireNonNull(internetDialogController);
            this.mWifiScanNotifyText.setText(AnnotationLinkSpan.linkify(getContext().getText(R$string.wifi_scan_notify_message), new AnnotationLinkSpan.LinkInfo("link", new View.OnClickListener() { // from class: com.android.systemui.qs.tiles.dialog.InternetDialog$$ExternalSyntheticLambda23
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    InternetDialogController.this.launchWifiScanningSetting(view);
                }
            })));
            this.mWifiScanNotifyText.setMovementMethod(LinkMovementMethod.getInstance());
        }
        this.mWifiScanNotifyLayout.setVisibility(0);
    }

    public final void updateWifiToggle(boolean z, boolean z2) {
        if (this.mWiFiToggle.isChecked() != z) {
            this.mWiFiToggle.setChecked(z);
        }
        if (z2) {
            this.mWifiToggleTitleText.setTextAppearance(this.mConnectedWifiEntry != null ? R$style.TextAppearance_InternetDialog_Active : R$style.TextAppearance_InternetDialog);
        }
        this.mTurnWifiOnLayout.setBackground((!z2 || this.mConnectedWifiEntry == null) ? null : this.mBackgroundOn);
        if (this.mCanChangeWifiState || !this.mWiFiToggle.isEnabled()) {
            return;
        }
        this.mWiFiToggle.setEnabled(false);
        this.mWifiToggleTitleText.setEnabled(false);
        TextView textView = (TextView) this.mDialogView.requireViewById(R$id.wifi_toggle_summary);
        textView.setEnabled(false);
        textView.setVisibility(0);
    }
}