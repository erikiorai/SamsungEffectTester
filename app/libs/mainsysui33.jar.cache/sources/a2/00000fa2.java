package com.android.systemui;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.UserInfo;
import android.os.UserHandle;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.qs.QSUserSwitcherEvent;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.android.systemui.statusbar.policy.UserSwitcherController;

/* loaded from: mainsysui33.jar:com/android/systemui/GuestResetOrExitSessionReceiver.class */
public final class GuestResetOrExitSessionReceiver extends BroadcastReceiver {
    public static final String TAG = GuestResetOrExitSessionReceiver.class.getSimpleName();
    public final BroadcastDispatcher mBroadcastDispatcher;
    public AlertDialog mExitSessionDialog;
    public final ExitSessionDialog.Factory mExitSessionDialogFactory;
    public AlertDialog mResetSessionDialog;
    public final ResetSessionDialog.Factory mResetSessionDialogFactory;
    public final UserTracker mUserTracker;

    /* loaded from: mainsysui33.jar:com/android/systemui/GuestResetOrExitSessionReceiver$ExitSessionDialog.class */
    public static final class ExitSessionDialog extends SystemUIDialog implements DialogInterface.OnClickListener {
        public boolean mIsEphemeral;
        public final int mUserId;
        public final UserSwitcherController mUserSwitcherController;

        /* loaded from: mainsysui33.jar:com/android/systemui/GuestResetOrExitSessionReceiver$ExitSessionDialog$Factory.class */
        public interface Factory {
            ExitSessionDialog create(int i, boolean z);
        }

        /* JADX DEBUG: Multi-variable search result rejected for r5v0, resolved type: com.android.systemui.GuestResetOrExitSessionReceiver$ExitSessionDialog */
        /* JADX WARN: Multi-variable type inference failed */
        public ExitSessionDialog(Context context, UserSwitcherController userSwitcherController, int i, boolean z) {
            super(context);
            if (z) {
                setTitle(context.getString(com.android.settingslib.R$string.guest_exit_dialog_title));
                setMessage(context.getString(com.android.settingslib.R$string.guest_exit_dialog_message));
                setButton(-3, context.getString(17039360), this);
                setButton(-1, context.getString(com.android.settingslib.R$string.guest_exit_dialog_button), this);
            } else {
                setTitle(context.getString(com.android.settingslib.R$string.guest_exit_dialog_title_non_ephemeral));
                setMessage(context.getString(com.android.settingslib.R$string.guest_exit_dialog_message_non_ephemeral));
                setButton(-3, context.getString(17039360), this);
                setButton(-2, context.getString(com.android.settingslib.R$string.guest_exit_clear_data_button), this);
                setButton(-1, context.getString(com.android.settingslib.R$string.guest_exit_save_data_button), this);
            }
            setCanceledOnTouchOutside(false);
            this.mUserSwitcherController = userSwitcherController;
            this.mUserId = i;
            this.mIsEphemeral = z;
        }

        /* JADX DEBUG: Multi-variable search result rejected for r5v0, resolved type: com.android.systemui.GuestResetOrExitSessionReceiver$ExitSessionDialog */
        /* JADX WARN: Multi-variable type inference failed */
        @Override // android.content.DialogInterface.OnClickListener
        public void onClick(DialogInterface dialogInterface, int i) {
            if (this.mIsEphemeral) {
                if (i == -1) {
                    this.mUserSwitcherController.exitGuestUser(this.mUserId, -10000, false);
                } else if (i == -3) {
                    cancel();
                }
            } else if (i == -1) {
                this.mUserSwitcherController.exitGuestUser(this.mUserId, -10000, false);
            } else if (i == -2) {
                this.mUserSwitcherController.exitGuestUser(this.mUserId, -10000, true);
            } else if (i == -3) {
                cancel();
            }
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/GuestResetOrExitSessionReceiver$ResetSessionDialog.class */
    public static final class ResetSessionDialog extends SystemUIDialog implements DialogInterface.OnClickListener {
        public final UiEventLogger mUiEventLogger;
        public final int mUserId;
        public final UserSwitcherController mUserSwitcherController;

        /* loaded from: mainsysui33.jar:com/android/systemui/GuestResetOrExitSessionReceiver$ResetSessionDialog$Factory.class */
        public interface Factory {
            ResetSessionDialog create(int i);
        }

        /* JADX DEBUG: Multi-variable search result rejected for r5v0, resolved type: com.android.systemui.GuestResetOrExitSessionReceiver$ResetSessionDialog */
        /* JADX WARN: Multi-variable type inference failed */
        public ResetSessionDialog(Context context, UserSwitcherController userSwitcherController, UiEventLogger uiEventLogger, int i) {
            super(context);
            setTitle(com.android.settingslib.R$string.guest_reset_and_restart_dialog_title);
            setMessage(context.getString(com.android.settingslib.R$string.guest_reset_and_restart_dialog_message));
            setButton(-3, context.getString(17039360), this);
            setButton(-1, context.getString(com.android.settingslib.R$string.guest_reset_guest_confirm_button), this);
            setCanceledOnTouchOutside(false);
            this.mUserSwitcherController = userSwitcherController;
            this.mUiEventLogger = uiEventLogger;
            this.mUserId = i;
        }

        /* JADX DEBUG: Multi-variable search result rejected for r4v0, resolved type: com.android.systemui.GuestResetOrExitSessionReceiver$ResetSessionDialog */
        /* JADX WARN: Multi-variable type inference failed */
        @Override // android.content.DialogInterface.OnClickListener
        public void onClick(DialogInterface dialogInterface, int i) {
            if (i == -1) {
                this.mUiEventLogger.log(QSUserSwitcherEvent.QS_USER_GUEST_REMOVE);
                this.mUserSwitcherController.removeGuestUser(this.mUserId, -10000);
            } else if (i == -3) {
                cancel();
            }
        }
    }

    public GuestResetOrExitSessionReceiver(UserTracker userTracker, BroadcastDispatcher broadcastDispatcher, ResetSessionDialog.Factory factory, ExitSessionDialog.Factory factory2) {
        this.mUserTracker = userTracker;
        this.mBroadcastDispatcher = broadcastDispatcher;
        this.mResetSessionDialogFactory = factory;
        this.mExitSessionDialogFactory = factory2;
    }

    public final void cancelExitDialog() {
        AlertDialog alertDialog = this.mExitSessionDialog;
        if (alertDialog == null || !alertDialog.isShowing()) {
            return;
        }
        this.mExitSessionDialog.cancel();
        this.mExitSessionDialog = null;
    }

    public final void cancelResetDialog() {
        AlertDialog alertDialog = this.mResetSessionDialog;
        if (alertDialog == null || !alertDialog.isShowing()) {
            return;
        }
        this.mResetSessionDialog.cancel();
        this.mResetSessionDialog = null;
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        cancelResetDialog();
        cancelExitDialog();
        UserInfo userInfo = this.mUserTracker.getUserInfo();
        if (userInfo.isGuest()) {
            if ("android.intent.action.GUEST_RESET".equals(action)) {
                SystemUIDialog create = this.mResetSessionDialogFactory.create(userInfo.id);
                this.mResetSessionDialog = create;
                create.show();
            } else if ("android.intent.action.GUEST_EXIT".equals(action)) {
                SystemUIDialog create2 = this.mExitSessionDialogFactory.create(userInfo.id, userInfo.isEphemeral());
                this.mExitSessionDialog = create2;
                create2.show();
            }
        }
    }

    public void register() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.GUEST_RESET");
        intentFilter.addAction("android.intent.action.GUEST_EXIT");
        this.mBroadcastDispatcher.registerReceiver(this, intentFilter, null, UserHandle.SYSTEM);
    }
}