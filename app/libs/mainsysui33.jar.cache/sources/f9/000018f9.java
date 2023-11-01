package com.android.systemui.keyguard;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityOptions;
import android.app.KeyguardManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.UserHandle;
import android.os.UserManager;
import android.widget.ImageView;
import android.window.OnBackInvokedCallback;
import com.android.internal.annotations.VisibleForTesting;
import com.android.systemui.R$id;
import com.android.systemui.R$layout;
import com.android.systemui.broadcast.BroadcastDispatcher;

/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/WorkLockActivity.class */
public class WorkLockActivity extends Activity {
    public final BroadcastDispatcher mBroadcastDispatcher;
    public KeyguardManager mKgm;
    public PackageManager mPackageManager;
    public UserManager mUserManager;
    public final OnBackInvokedCallback mBackCallback = new OnBackInvokedCallback() { // from class: com.android.systemui.keyguard.WorkLockActivity$$ExternalSyntheticLambda0
        public final void onBackInvoked() {
            WorkLockActivity.$r8$lambda$cYHbW_qutwQ2sTpgnrsksiHpNlg(WorkLockActivity.this);
        }
    };
    public final BroadcastReceiver mLockEventReceiver = new BroadcastReceiver() { // from class: com.android.systemui.keyguard.WorkLockActivity.1
        {
            WorkLockActivity.this = this;
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            int targetUserId = WorkLockActivity.this.getTargetUserId();
            if (intent.getIntExtra("android.intent.extra.user_handle", targetUserId) != targetUserId || WorkLockActivity.this.getKeyguardManager().isDeviceLocked(targetUserId)) {
                return;
            }
            WorkLockActivity.this.finish();
        }
    };

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.keyguard.WorkLockActivity$$ExternalSyntheticLambda0.onBackInvoked():void] */
    public static /* synthetic */ void $r8$lambda$cYHbW_qutwQ2sTpgnrsksiHpNlg(WorkLockActivity workLockActivity) {
        workLockActivity.onBackInvoked();
    }

    public WorkLockActivity(BroadcastDispatcher broadcastDispatcher, UserManager userManager, PackageManager packageManager) {
        this.mBroadcastDispatcher = broadcastDispatcher;
        this.mUserManager = userManager;
        this.mPackageManager = packageManager;
    }

    @VisibleForTesting
    public Drawable getBadgedIcon() {
        String stringExtra = getIntent().getStringExtra("android.intent.extra.PACKAGE_NAME");
        if (stringExtra.isEmpty()) {
            return null;
        }
        try {
            UserManager userManager = this.mUserManager;
            PackageManager packageManager = this.mPackageManager;
            return userManager.getBadgedIconForUser(packageManager.getApplicationIcon(packageManager.getApplicationInfoAsUser(stringExtra, PackageManager.ApplicationInfoFlags.of(0L), getTargetUserId())), UserHandle.of(getTargetUserId()));
        } catch (PackageManager.NameNotFoundException e) {
            return null;
        }
    }

    public final KeyguardManager getKeyguardManager() {
        if (this.mKgm == null) {
            this.mKgm = (KeyguardManager) getSystemService("keyguard");
        }
        return this.mKgm;
    }

    @VisibleForTesting
    public final int getTargetUserId() {
        return getIntent().getIntExtra("android.intent.extra.USER_ID", UserHandle.myUserId());
    }

    public final void goToHomeScreen() {
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.HOME");
        intent.setFlags(268435456);
        startActivity(intent);
    }

    @Override // android.app.Activity
    public void onActivityResult(int i, int i2, Intent intent) {
        if (i != 1 || i2 == -1) {
            return;
        }
        goToHomeScreen();
    }

    public final void onBackInvoked() {
    }

    @Override // android.app.Activity
    public void onBackPressed() {
        onBackInvoked();
    }

    @Override // android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mBroadcastDispatcher.registerReceiver(this.mLockEventReceiver, new IntentFilter("android.intent.action.DEVICE_LOCKED_CHANGED"), null, UserHandle.ALL);
        if (!getKeyguardManager().isDeviceLocked(getTargetUserId())) {
            finish();
            return;
        }
        setOverlayWithDecorCaptionEnabled(true);
        setContentView(R$layout.auth_biometric_background);
        Drawable badgedIcon = getBadgedIcon();
        if (badgedIcon != null) {
            ((ImageView) findViewById(R$id.icon)).setImageDrawable(badgedIcon);
        }
        getOnBackInvokedDispatcher().registerOnBackInvokedCallback(0, this.mBackCallback);
    }

    @Override // android.app.Activity
    public void onDestroy() {
        unregisterBroadcastReceiver();
        getOnBackInvokedDispatcher().unregisterOnBackInvokedCallback(this.mBackCallback);
        super.onDestroy();
    }

    @Override // android.app.Activity, android.view.Window.Callback
    public void onWindowFocusChanged(boolean z) {
        if (z) {
            showConfirmCredentialActivity();
        }
    }

    @Override // android.app.Activity
    public void setTaskDescription(ActivityManager.TaskDescription taskDescription) {
    }

    public final void showConfirmCredentialActivity() {
        Intent createConfirmDeviceCredentialIntent;
        if (isFinishing() || !getKeyguardManager().isDeviceLocked(getTargetUserId()) || (createConfirmDeviceCredentialIntent = getKeyguardManager().createConfirmDeviceCredentialIntent(null, null, getTargetUserId(), true)) == null) {
            return;
        }
        ActivityOptions makeBasic = ActivityOptions.makeBasic();
        makeBasic.setLaunchTaskId(getTaskId());
        PendingIntent activity = PendingIntent.getActivity(this, -1, getIntent(), 1409286144, makeBasic.toBundle());
        if (activity != null) {
            createConfirmDeviceCredentialIntent.putExtra("android.intent.extra.INTENT", activity.getIntentSender());
        }
        ActivityOptions makeBasic2 = ActivityOptions.makeBasic();
        makeBasic2.setLaunchTaskId(getTaskId());
        makeBasic2.setTaskOverlay(true, true);
        startActivityForResult(createConfirmDeviceCredentialIntent, 1, makeBasic2.toBundle());
    }

    @VisibleForTesting
    public void unregisterBroadcastReceiver() {
        this.mBroadcastDispatcher.unregisterReceiver(this.mLockEventReceiver);
    }
}