package com.android.systemui.qs.user;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.R$id;
import com.android.systemui.R$layout;
import com.android.systemui.R$string;
import com.android.systemui.animation.ActivityLaunchAnimator;
import com.android.systemui.animation.DialogCuj;
import com.android.systemui.animation.DialogLaunchAnimator;
import com.android.systemui.animation.Expandable;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.qs.QSUserSwitcherEvent;
import com.android.systemui.qs.tiles.UserDetailView;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import javax.inject.Provider;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/user/UserSwitchDialogController.class */
public final class UserSwitchDialogController {
    public static final Companion Companion = new Companion(null);
    public static final Intent USER_SETTINGS_INTENT = new Intent("android.settings.USER_SETTINGS");
    public final ActivityStarter activityStarter;
    public final Function1<Context, SystemUIDialog> dialogFactory;
    public final DialogLaunchAnimator dialogLaunchAnimator;
    public final FalsingManager falsingManager;
    public final UiEventLogger uiEventLogger;
    public final Provider<UserDetailView.Adapter> userDetailViewAdapterProvider;

    /* loaded from: mainsysui33.jar:com/android/systemui/qs/user/UserSwitchDialogController$Companion.class */
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/qs/user/UserSwitchDialogController$DialogShower.class */
    public interface DialogShower extends DialogInterface {
        void showDialog(Dialog dialog, DialogCuj dialogCuj);
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/qs/user/UserSwitchDialogController$DialogShowerImpl.class */
    public static final class DialogShowerImpl implements DialogInterface, DialogShower {
        public final Dialog animateFrom;
        public final DialogLaunchAnimator dialogLaunchAnimator;

        public DialogShowerImpl(Dialog dialog, DialogLaunchAnimator dialogLaunchAnimator) {
            this.animateFrom = dialog;
            this.dialogLaunchAnimator = dialogLaunchAnimator;
        }

        @Override // android.content.DialogInterface
        public void cancel() {
            this.animateFrom.cancel();
        }

        @Override // android.content.DialogInterface
        public void dismiss() {
            this.animateFrom.dismiss();
        }

        @Override // com.android.systemui.qs.user.UserSwitchDialogController.DialogShower
        public void showDialog(Dialog dialog, DialogCuj dialogCuj) {
            DialogLaunchAnimator.showFromDialog$default(this.dialogLaunchAnimator, dialog, this.animateFrom, dialogCuj, false, 8, null);
        }
    }

    public UserSwitchDialogController(Provider<UserDetailView.Adapter> provider, ActivityStarter activityStarter, FalsingManager falsingManager, DialogLaunchAnimator dialogLaunchAnimator, UiEventLogger uiEventLogger) {
        this(provider, activityStarter, falsingManager, dialogLaunchAnimator, uiEventLogger, new Function1<Context, SystemUIDialog>() { // from class: com.android.systemui.qs.user.UserSwitchDialogController.1
            public final SystemUIDialog invoke(Context context) {
                return new SystemUIDialog(context);
            }
        });
    }

    /* JADX DEBUG: Multi-variable search result rejected for r9v0, resolved type: kotlin.jvm.functions.Function1<? super android.content.Context, ? extends com.android.systemui.statusbar.phone.SystemUIDialog> */
    /* JADX WARN: Multi-variable type inference failed */
    public UserSwitchDialogController(Provider<UserDetailView.Adapter> provider, ActivityStarter activityStarter, FalsingManager falsingManager, DialogLaunchAnimator dialogLaunchAnimator, UiEventLogger uiEventLogger, Function1<? super Context, ? extends SystemUIDialog> function1) {
        this.userDetailViewAdapterProvider = provider;
        this.activityStarter = activityStarter;
        this.falsingManager = falsingManager;
        this.dialogLaunchAnimator = dialogLaunchAnimator;
        this.uiEventLogger = uiEventLogger;
        this.dialogFactory = function1;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.user.UserSwitchDialogController$showDialog$1$1.onClick(android.content.DialogInterface, int):void] */
    public static final /* synthetic */ UiEventLogger access$getUiEventLogger$p(UserSwitchDialogController userSwitchDialogController) {
        return userSwitchDialogController.uiEventLogger;
    }

    public final void showDialog(Context context, Expandable expandable) {
        final SystemUIDialog systemUIDialog = (SystemUIDialog) this.dialogFactory.invoke(context);
        systemUIDialog.setShowForAllUsers(true);
        systemUIDialog.setCanceledOnTouchOutside(true);
        systemUIDialog.setTitle(R$string.qs_user_switch_dialog_title);
        systemUIDialog.setPositiveButton(R$string.quick_settings_done, new DialogInterface.OnClickListener() { // from class: com.android.systemui.qs.user.UserSwitchDialogController$showDialog$1$1
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                UserSwitchDialogController.access$getUiEventLogger$p(UserSwitchDialogController.this).log(QSUserSwitcherEvent.QS_USER_DETAIL_CLOSE);
            }
        });
        systemUIDialog.setNeutralButton(R$string.quick_settings_more_user_settings, new DialogInterface.OnClickListener() { // from class: com.android.systemui.qs.user.UserSwitchDialogController$showDialog$1$2
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                FalsingManager falsingManager;
                UiEventLogger uiEventLogger;
                DialogLaunchAnimator dialogLaunchAnimator;
                ActivityStarter activityStarter;
                Intent intent;
                falsingManager = UserSwitchDialogController.this.falsingManager;
                if (falsingManager.isFalseTap(1)) {
                    return;
                }
                uiEventLogger = UserSwitchDialogController.this.uiEventLogger;
                uiEventLogger.log(QSUserSwitcherEvent.QS_USER_MORE_SETTINGS);
                dialogLaunchAnimator = UserSwitchDialogController.this.dialogLaunchAnimator;
                ActivityLaunchAnimator.Controller createActivityLaunchController$default = DialogLaunchAnimator.createActivityLaunchController$default(dialogLaunchAnimator, systemUIDialog.getButton(-3), null, 2, null);
                if (createActivityLaunchController$default == null) {
                    systemUIDialog.dismiss();
                }
                activityStarter = UserSwitchDialogController.this.activityStarter;
                intent = UserSwitchDialogController.USER_SETTINGS_INTENT;
                activityStarter.postStartActivityDismissingKeyguard(intent, 0, createActivityLaunchController$default);
            }
        }, false);
        View inflate = LayoutInflater.from(systemUIDialog.getContext()).inflate(R$layout.qs_user_dialog_content, (ViewGroup) null);
        systemUIDialog.setView(inflate);
        UserDetailView.Adapter adapter = (UserDetailView.Adapter) this.userDetailViewAdapterProvider.get();
        adapter.linkToViewGroup((ViewGroup) inflate.findViewById(R$id.grid));
        DialogLaunchAnimator.Controller dialogLaunchController = expandable.dialogLaunchController(new DialogCuj(58, "switch_user"));
        if (dialogLaunchController != null) {
            DialogLaunchAnimator.show$default(this.dialogLaunchAnimator, systemUIDialog, dialogLaunchController, false, 4, null);
        } else {
            systemUIDialog.show();
        }
        this.uiEventLogger.log(QSUserSwitcherEvent.QS_USER_DETAIL_OPEN);
        adapter.injectDialogShower(new DialogShowerImpl(systemUIDialog, this.dialogLaunchAnimator));
    }
}