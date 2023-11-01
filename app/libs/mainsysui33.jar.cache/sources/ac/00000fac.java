package com.android.systemui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.UserInfo;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.qs.QSUserSwitcherEvent;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.android.systemui.statusbar.policy.UserSwitcherController;
import com.android.systemui.util.settings.SecureSettings;
import java.util.concurrent.Executor;

/* loaded from: mainsysui33.jar:com/android/systemui/GuestResumeSessionReceiver.class */
public class GuestResumeSessionReceiver {
    @VisibleForTesting
    public static final String SETTING_GUEST_HAS_LOGGED_IN = "systemui.guest_has_logged_in";
    public final GuestSessionNotification mGuestSessionNotification;
    public final Executor mMainExecutor;
    @VisibleForTesting
    public AlertDialog mNewSessionDialog;
    public final ResetSessionDialog.Factory mResetSessionDialogFactory;
    public final SecureSettings mSecureSettings;
    @VisibleForTesting
    public final UserTracker.Callback mUserChangedCallback = new UserTracker.Callback() { // from class: com.android.systemui.GuestResumeSessionReceiver.1
        public void onUserChanged(int i, Context context) {
            int i2;
            GuestResumeSessionReceiver.this.cancelDialog();
            UserInfo userInfo = GuestResumeSessionReceiver.this.mUserTracker.getUserInfo();
            if (userInfo.isGuest()) {
                boolean z = false;
                int intForUser = GuestResumeSessionReceiver.this.mSecureSettings.getIntForUser(GuestResumeSessionReceiver.SETTING_GUEST_HAS_LOGGED_IN, 0, i);
                if (intForUser == 0) {
                    GuestResumeSessionReceiver.this.mSecureSettings.putIntForUser(GuestResumeSessionReceiver.SETTING_GUEST_HAS_LOGGED_IN, 1, i);
                    i2 = 1;
                } else {
                    i2 = intForUser;
                    if (intForUser == 1) {
                        i2 = 2;
                        GuestResumeSessionReceiver.this.mSecureSettings.putIntForUser(GuestResumeSessionReceiver.SETTING_GUEST_HAS_LOGGED_IN, 2, i);
                    }
                }
                GuestSessionNotification guestSessionNotification = GuestResumeSessionReceiver.this.mGuestSessionNotification;
                if (i2 <= 1) {
                    z = true;
                }
                guestSessionNotification.createPersistentNotification(userInfo, z);
                if (i2 > 1) {
                    GuestResumeSessionReceiver guestResumeSessionReceiver = GuestResumeSessionReceiver.this;
                    guestResumeSessionReceiver.mNewSessionDialog = guestResumeSessionReceiver.mResetSessionDialogFactory.create(i);
                    GuestResumeSessionReceiver.this.mNewSessionDialog.show();
                }
            }
        }
    };
    public final UserTracker mUserTracker;

    @VisibleForTesting
    /* loaded from: mainsysui33.jar:com/android/systemui/GuestResumeSessionReceiver$ResetSessionDialog.class */
    public static class ResetSessionDialog extends SystemUIDialog implements DialogInterface.OnClickListener {
        @VisibleForTesting
        public static final int BUTTON_DONTWIPE = -1;
        @VisibleForTesting
        public static final int BUTTON_WIPE = -2;
        public final UiEventLogger mUiEventLogger;
        public final int mUserId;
        public final UserSwitcherController mUserSwitcherController;

        /* loaded from: mainsysui33.jar:com/android/systemui/GuestResumeSessionReceiver$ResetSessionDialog$Factory.class */
        public interface Factory {
            ResetSessionDialog create(int i);
        }

        /* JADX DEBUG: Multi-variable search result rejected for r5v0, resolved type: com.android.systemui.GuestResumeSessionReceiver$ResetSessionDialog */
        /* JADX WARN: Multi-variable type inference failed */
        public ResetSessionDialog(Context context, UserSwitcherController userSwitcherController, UiEventLogger uiEventLogger, int i) {
            super(context, false);
            setTitle(context.getString(R$string.guest_wipe_session_title));
            setMessage(context.getString(R$string.guest_wipe_session_message));
            setCanceledOnTouchOutside(false);
            setButton(-2, context.getString(R$string.guest_wipe_session_wipe), this);
            setButton(-1, context.getString(R$string.guest_wipe_session_dontwipe), this);
            this.mUserSwitcherController = userSwitcherController;
            this.mUiEventLogger = uiEventLogger;
            this.mUserId = i;
        }

        /* JADX DEBUG: Multi-variable search result rejected for r4v0, resolved type: com.android.systemui.GuestResumeSessionReceiver$ResetSessionDialog */
        /* JADX WARN: Multi-variable type inference failed */
        @Override // android.content.DialogInterface.OnClickListener
        public void onClick(DialogInterface dialogInterface, int i) {
            if (i == -2) {
                this.mUiEventLogger.log(QSUserSwitcherEvent.QS_USER_GUEST_WIPE);
                this.mUserSwitcherController.removeGuestUser(this.mUserId, -10000);
                dismiss();
            } else if (i == -1) {
                this.mUiEventLogger.log(QSUserSwitcherEvent.QS_USER_GUEST_CONTINUE);
                cancel();
            }
        }
    }

    public GuestResumeSessionReceiver(Executor executor, UserTracker userTracker, SecureSettings secureSettings, GuestSessionNotification guestSessionNotification, ResetSessionDialog.Factory factory) {
        this.mMainExecutor = executor;
        this.mUserTracker = userTracker;
        this.mSecureSettings = secureSettings;
        this.mGuestSessionNotification = guestSessionNotification;
        this.mResetSessionDialogFactory = factory;
    }

    public final void cancelDialog() {
        AlertDialog alertDialog = this.mNewSessionDialog;
        if (alertDialog == null || !alertDialog.isShowing()) {
            return;
        }
        this.mNewSessionDialog.cancel();
        this.mNewSessionDialog = null;
    }

    public void register() {
        this.mUserTracker.addCallback(this.mUserChangedCallback, this.mMainExecutor);
    }
}