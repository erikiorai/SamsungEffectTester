package com.android.systemui.controls.settings;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import com.android.systemui.R$drawable;
import com.android.systemui.R$string;
import com.android.systemui.R$style;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.settings.UserFileManager;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.android.systemui.util.settings.SecureSettings;
import java.util.List;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;

/* loaded from: mainsysui33.jar:com/android/systemui/controls/settings/ControlsSettingsDialogManagerImpl.class */
public final class ControlsSettingsDialogManagerImpl implements ControlsSettingsDialogManager {
    public final ActivityStarter activityStarter;
    public final ControlsSettingsRepository controlsSettingsRepository;
    public AlertDialog dialog;
    public final Function2<Context, Integer, AlertDialog> dialogProvider;
    public final SecureSettings secureSettings;
    public final UserFileManager userFileManager;
    public final UserTracker userTracker;

    /* loaded from: mainsysui33.jar:com/android/systemui/controls/settings/ControlsSettingsDialogManagerImpl$DialogListener.class */
    public final class DialogListener implements DialogInterface.OnClickListener, DialogInterface.OnCancelListener {
        public final int attempts;
        public final Function0<Unit> onComplete;
        public final SharedPreferences prefs;

        public DialogListener(SharedPreferences sharedPreferences, int i, Function0<Unit> function0) {
            ControlsSettingsDialogManagerImpl.this = r4;
            this.prefs = sharedPreferences;
            this.attempts = i;
            this.onComplete = function0;
        }

        @Override // android.content.DialogInterface.OnCancelListener
        public void onCancel(DialogInterface dialogInterface) {
            if (dialogInterface == null) {
                return;
            }
            if (this.attempts < 2) {
                this.prefs.edit().putInt("show_settings_attempts", this.attempts + 1).apply();
            }
            this.onComplete.invoke();
        }

        @Override // android.content.DialogInterface.OnClickListener
        public void onClick(DialogInterface dialogInterface, int i) {
            if (dialogInterface == null) {
                return;
            }
            if (i == -1) {
                List mutableListOf = CollectionsKt__CollectionsKt.mutableListOf(new String[]{"lockscreen_allow_trivial_controls"});
                if (!ControlsSettingsDialogManagerImpl.this.getShowDeviceControlsInLockscreen()) {
                    mutableListOf.add("lockscreen_show_controls");
                }
                ControlsSettingsDialogManagerImpl.this.turnOnSettingSecurely(mutableListOf);
            }
            if (this.attempts != 2) {
                this.prefs.edit().putInt("show_settings_attempts", 2).apply();
            }
            this.onComplete.invoke();
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/controls/settings/ControlsSettingsDialogManagerImpl$SettingsDialog.class */
    public static final class SettingsDialog extends AlertDialog {
        public SettingsDialog(Context context, int i) {
            super(context, i);
        }
    }

    public ControlsSettingsDialogManagerImpl(SecureSettings secureSettings, UserFileManager userFileManager, ControlsSettingsRepository controlsSettingsRepository, UserTracker userTracker, ActivityStarter activityStarter) {
        this(secureSettings, userFileManager, controlsSettingsRepository, userTracker, activityStarter, new Function2<Context, Integer, AlertDialog>() { // from class: com.android.systemui.controls.settings.ControlsSettingsDialogManagerImpl.1
            public final AlertDialog invoke(Context context, int i) {
                return new SettingsDialog(context, i);
            }

            public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
                return invoke((Context) obj, ((Number) obj2).intValue());
            }
        });
    }

    /* JADX DEBUG: Multi-variable search result rejected for r9v0, resolved type: kotlin.jvm.functions.Function2<? super android.content.Context, ? super java.lang.Integer, ? extends android.app.AlertDialog> */
    /* JADX WARN: Multi-variable type inference failed */
    public ControlsSettingsDialogManagerImpl(SecureSettings secureSettings, UserFileManager userFileManager, ControlsSettingsRepository controlsSettingsRepository, UserTracker userTracker, ActivityStarter activityStarter, Function2<? super Context, ? super Integer, ? extends AlertDialog> function2) {
        this.secureSettings = secureSettings;
        this.userFileManager = userFileManager;
        this.controlsSettingsRepository = controlsSettingsRepository;
        this.userTracker = userTracker;
        this.activityStarter = activityStarter;
        this.dialogProvider = function2;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.controls.settings.ControlsSettingsDialogManagerImpl$turnOnSettingSecurely$action$1.onDismiss():boolean] */
    public static final /* synthetic */ SecureSettings access$getSecureSettings$p(ControlsSettingsDialogManagerImpl controlsSettingsDialogManagerImpl) {
        return controlsSettingsDialogManagerImpl.secureSettings;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.controls.settings.ControlsSettingsDialogManagerImpl$turnOnSettingSecurely$action$1.onDismiss():boolean] */
    public static final /* synthetic */ UserTracker access$getUserTracker$p(ControlsSettingsDialogManagerImpl controlsSettingsDialogManagerImpl) {
        return controlsSettingsDialogManagerImpl.userTracker;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.controls.settings.ControlsSettingsDialogManagerImpl$maybeShowDialog$1.run():void] */
    public static final /* synthetic */ void access$setDialog$p(ControlsSettingsDialogManagerImpl controlsSettingsDialogManagerImpl, AlertDialog alertDialog) {
        controlsSettingsDialogManagerImpl.dialog = alertDialog;
    }

    @Override // com.android.systemui.controls.settings.ControlsSettingsDialogManager
    public void closeDialog() {
        AlertDialog alertDialog = this.dialog;
        if (alertDialog != null) {
            alertDialog.dismiss();
        }
    }

    public final boolean getAllowTrivialControls() {
        return ((Boolean) this.controlsSettingsRepository.getAllowActionOnTrivialControlsInLockscreen().getValue()).booleanValue();
    }

    public final boolean getShowDeviceControlsInLockscreen() {
        return ((Boolean) this.controlsSettingsRepository.getCanShowControlsInLockscreen().getValue()).booleanValue();
    }

    @Override // com.android.systemui.controls.settings.ControlsSettingsDialogManager
    public void maybeShowDialog(Context context, Function0<Unit> function0) {
        closeDialog();
        SharedPreferences sharedPreferences = this.userFileManager.getSharedPreferences("controls_prefs", 0, this.userTracker.getUserId());
        int i = sharedPreferences.getInt("show_settings_attempts", 0);
        if (i >= 2 || (getShowDeviceControlsInLockscreen() && getAllowTrivialControls())) {
            function0.invoke();
            return;
        }
        DialogListener dialogListener = new DialogListener(sharedPreferences, i, function0);
        AlertDialog alertDialog = (AlertDialog) this.dialogProvider.invoke(context, Integer.valueOf(R$style.Theme_SystemUI_Dialog));
        alertDialog.setIcon(R$drawable.ic_warning);
        alertDialog.setOnCancelListener(dialogListener);
        setNeutralButton(alertDialog, R$string.controls_settings_dialog_neutral_button, dialogListener);
        setPositiveButton(alertDialog, R$string.controls_settings_dialog_positive_button, dialogListener);
        if (getShowDeviceControlsInLockscreen()) {
            alertDialog.setTitle(R$string.controls_settings_trivial_controls_dialog_title);
            setMessage(alertDialog, R$string.controls_settings_trivial_controls_dialog_message);
        } else {
            alertDialog.setTitle(R$string.controls_settings_show_controls_dialog_title);
            setMessage(alertDialog, R$string.controls_settings_show_controls_dialog_message);
        }
        SystemUIDialog.registerDismissListener(alertDialog, new Runnable() { // from class: com.android.systemui.controls.settings.ControlsSettingsDialogManagerImpl$maybeShowDialog$1
            @Override // java.lang.Runnable
            public final void run() {
                ControlsSettingsDialogManagerImpl.access$setDialog$p(ControlsSettingsDialogManagerImpl.this, null);
            }
        });
        SystemUIDialog.setDialogSize(alertDialog);
        SystemUIDialog.setShowForAllUsers(alertDialog, true);
        this.dialog = alertDialog;
        alertDialog.show();
    }

    public final void setMessage(AlertDialog alertDialog, int i) {
        alertDialog.setMessage(alertDialog.getContext().getText(i));
    }

    public final void setNeutralButton(AlertDialog alertDialog, int i, DialogInterface.OnClickListener onClickListener) {
        alertDialog.setButton(-3, alertDialog.getContext().getText(i), onClickListener);
    }

    public final void setPositiveButton(AlertDialog alertDialog, int i, DialogInterface.OnClickListener onClickListener) {
        alertDialog.setButton(-1, alertDialog.getContext().getText(i), onClickListener);
    }

    public final void turnOnSettingSecurely(final List<String> list) {
        this.activityStarter.dismissKeyguardThenExecute(new ActivityStarter.OnDismissAction() { // from class: com.android.systemui.controls.settings.ControlsSettingsDialogManagerImpl$turnOnSettingSecurely$action$1
            @Override // com.android.systemui.plugins.ActivityStarter.OnDismissAction
            public final boolean onDismiss() {
                List<String> list2 = list;
                ControlsSettingsDialogManagerImpl controlsSettingsDialogManagerImpl = this;
                for (String str : list2) {
                    ControlsSettingsDialogManagerImpl.access$getSecureSettings$p(controlsSettingsDialogManagerImpl).putIntForUser(str, 1, ControlsSettingsDialogManagerImpl.access$getUserTracker$p(controlsSettingsDialogManagerImpl).getUserId());
                }
                return true;
            }
        }, null, true);
    }
}