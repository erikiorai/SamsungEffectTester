package com.android.systemui.controls.management;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.service.controls.Control;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.activity.ComponentActivity;
import com.android.systemui.R$dimen;
import com.android.systemui.R$id;
import com.android.systemui.R$layout;
import com.android.systemui.R$string;
import com.android.systemui.controls.ControlsServiceInfo;
import com.android.systemui.controls.controller.ControlInfo;
import com.android.systemui.controls.controller.ControlsController;
import com.android.systemui.controls.controller.StructureInfo;
import com.android.systemui.controls.management.ControlsListingController;
import com.android.systemui.controls.ui.RenderInfo;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Executor;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: mainsysui33.jar:com/android/systemui/controls/management/ControlsRequestDialog.class */
public class ControlsRequestDialog extends ComponentActivity implements DialogInterface.OnClickListener, DialogInterface.OnCancelListener {
    public static final Companion Companion = new Companion(null);
    public Control control;
    public ComponentName controlComponent;
    public final ControlsController controller;
    public final ControlsListingController controlsListingController;
    public Dialog dialog;
    public final Executor mainExecutor;
    public final UserTracker userTracker;
    public final ControlsRequestDialog$callback$1 callback = new ControlsListingController.ControlsListingCallback() { // from class: com.android.systemui.controls.management.ControlsRequestDialog$callback$1
        @Override // com.android.systemui.controls.management.ControlsListingController.ControlsListingCallback
        public void onServicesUpdated(List<ControlsServiceInfo> list) {
        }
    };
    public final UserTracker.Callback userTrackerCallback = new UserTracker.Callback() { // from class: com.android.systemui.controls.management.ControlsRequestDialog$userTrackerCallback$1
        public final int startingUser;

        {
            this.startingUser = ControlsRequestDialog.access$getController$p(ControlsRequestDialog.this).getCurrentUserId();
        }

        public void onUserChanged(int i, Context context) {
            if (i != this.startingUser) {
                ControlsRequestDialog.access$getUserTracker$p(ControlsRequestDialog.this).removeCallback(this);
                ControlsRequestDialog.this.finish();
            }
        }
    };

    /* loaded from: mainsysui33.jar:com/android/systemui/controls/management/ControlsRequestDialog$Companion.class */
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* JADX WARN: Type inference failed for: r1v4, types: [com.android.systemui.controls.management.ControlsRequestDialog$callback$1] */
    public ControlsRequestDialog(Executor executor, ControlsController controlsController, UserTracker userTracker, ControlsListingController controlsListingController) {
        this.mainExecutor = executor;
        this.controller = controlsController;
        this.userTracker = userTracker;
        this.controlsListingController = controlsListingController;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.controls.management.ControlsRequestDialog$userTrackerCallback$1.<init>(com.android.systemui.controls.management.ControlsRequestDialog):void] */
    public static final /* synthetic */ ControlsController access$getController$p(ControlsRequestDialog controlsRequestDialog) {
        return controlsRequestDialog.controller;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.controls.management.ControlsRequestDialog$userTrackerCallback$1.onUserChanged(int, android.content.Context):void] */
    public static final /* synthetic */ UserTracker access$getUserTracker$p(ControlsRequestDialog controlsRequestDialog) {
        return controlsRequestDialog.userTracker;
    }

    public final Dialog createDialog(CharSequence charSequence) {
        RenderInfo.Companion companion = RenderInfo.Companion;
        ComponentName componentName = this.controlComponent;
        if (componentName == null) {
            componentName = null;
        }
        Control control = this.control;
        Control control2 = control;
        if (control == null) {
            control2 = null;
        }
        RenderInfo lookup$default = RenderInfo.Companion.lookup$default(companion, this, componentName, control2.getDeviceType(), 0, 8, null);
        View inflate = LayoutInflater.from(this).inflate(R$layout.controls_dialog, (ViewGroup) null);
        ImageView imageView = (ImageView) inflate.requireViewById(R$id.icon);
        imageView.setImageDrawable(lookup$default.getIcon());
        imageView.setImageTintList(imageView.getContext().getResources().getColorStateList(lookup$default.getForeground(), imageView.getContext().getTheme()));
        TextView textView = (TextView) inflate.requireViewById(R$id.title);
        Control control3 = this.control;
        Control control4 = control3;
        if (control3 == null) {
            control4 = null;
        }
        textView.setText(control4.getTitle());
        TextView textView2 = (TextView) inflate.requireViewById(R$id.subtitle);
        Control control5 = this.control;
        if (control5 == null) {
            control5 = null;
        }
        textView2.setText(control5.getSubtitle());
        inflate.requireViewById(R$id.control).setElevation(inflate.getResources().getFloat(R$dimen.control_card_elevation));
        AlertDialog create = new AlertDialog.Builder(this).setTitle(getString(R$string.controls_dialog_title)).setMessage(getString(R$string.controls_dialog_message, new Object[]{charSequence})).setPositiveButton(R$string.controls_dialog_ok, this).setNegativeButton(17039360, this).setOnCancelListener(this).setView(inflate).create();
        SystemUIDialog.registerDismissListener(create);
        create.setCanceledOnTouchOutside(true);
        return create;
    }

    public final boolean isCurrentFavorite() {
        boolean z;
        ControlsController controlsController = this.controller;
        ComponentName componentName = this.controlComponent;
        ComponentName componentName2 = componentName;
        if (componentName == null) {
            componentName2 = null;
        }
        List<StructureInfo> favoritesForComponent = controlsController.getFavoritesForComponent(componentName2);
        boolean z2 = true;
        if (!(favoritesForComponent instanceof Collection) || !favoritesForComponent.isEmpty()) {
            for (StructureInfo structureInfo : favoritesForComponent) {
                List<ControlInfo> controls = structureInfo.getControls();
                if (!(controls instanceof Collection) || !controls.isEmpty()) {
                    for (ControlInfo controlInfo : controls) {
                        String controlId = controlInfo.getControlId();
                        Control control = this.control;
                        Control control2 = control;
                        if (control == null) {
                            control2 = null;
                        }
                        if (Intrinsics.areEqual(controlId, control2.getControlId())) {
                            z = true;
                            break;
                        }
                    }
                }
                z = false;
                if (z) {
                    break;
                }
            }
        }
        z2 = false;
        return z2;
    }

    @Override // android.content.DialogInterface.OnCancelListener
    public void onCancel(DialogInterface dialogInterface) {
        finish();
    }

    @Override // android.content.DialogInterface.OnClickListener
    public void onClick(DialogInterface dialogInterface, int i) {
        if (i == -1) {
            ControlsController controlsController = this.controller;
            ComponentName componentName = this.controlComponent;
            ComponentName componentName2 = componentName;
            if (componentName == null) {
                componentName2 = null;
            }
            Control control = this.control;
            Control control2 = control;
            if (control == null) {
                control2 = null;
            }
            CharSequence structure = control2.getStructure();
            CharSequence charSequence = structure;
            if (structure == null) {
                charSequence = "";
            }
            Control control3 = this.control;
            Control control4 = control3;
            if (control3 == null) {
                control4 = null;
            }
            String controlId = control4.getControlId();
            Control control5 = this.control;
            Control control6 = control5;
            if (control5 == null) {
                control6 = null;
            }
            CharSequence title = control6.getTitle();
            Control control7 = this.control;
            Control control8 = control7;
            if (control7 == null) {
                control8 = null;
            }
            CharSequence subtitle = control8.getSubtitle();
            Control control9 = this.control;
            if (control9 == null) {
                control9 = null;
            }
            controlsController.addFavorite(componentName2, charSequence, new ControlInfo(controlId, title, subtitle, control9.getDeviceType()));
        }
        finish();
    }

    @Override // androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.userTracker.addCallback(this.userTrackerCallback, this.mainExecutor);
        this.controlsListingController.addCallback(this.callback);
        int intExtra = getIntent().getIntExtra("android.intent.extra.USER_ID", -10000);
        int currentUserId = this.controller.getCurrentUserId();
        if (intExtra != currentUserId) {
            Log.w("ControlsRequestDialog", "Current user (" + currentUserId + ") different from request user (" + intExtra + ")");
            finish();
        }
        ComponentName componentName = (ComponentName) getIntent().getParcelableExtra("android.intent.extra.COMPONENT_NAME");
        if (componentName == null) {
            Log.e("ControlsRequestDialog", "Request did not contain componentName");
            finish();
            return;
        }
        this.controlComponent = componentName;
        Control control = (Control) getIntent().getParcelableExtra("android.service.controls.extra.CONTROL");
        if (control != null) {
            this.control = control;
            return;
        }
        Log.e("ControlsRequestDialog", "Request did not contain control");
        finish();
    }

    @Override // android.app.Activity
    public void onDestroy() {
        Dialog dialog = this.dialog;
        if (dialog != null) {
            dialog.dismiss();
        }
        this.userTracker.removeCallback(this.userTrackerCallback);
        this.controlsListingController.removeCallback(this.callback);
        super.onDestroy();
    }

    @Override // android.app.Activity
    public void onResume() {
        super.onResume();
        CharSequence verifyComponentAndGetLabel = verifyComponentAndGetLabel();
        ComponentName componentName = null;
        if (verifyComponentAndGetLabel == null) {
            ComponentName componentName2 = this.controlComponent;
            if (componentName2 != null) {
                componentName = componentName2;
            }
            Log.e("ControlsRequestDialog", "The component specified (" + componentName.flattenToString() + " is not a valid ControlsProviderService");
            finish();
            return;
        }
        if (isCurrentFavorite()) {
            Control control = this.control;
            if (control == null) {
                control = null;
            }
            CharSequence title = control.getTitle();
            Log.w("ControlsRequestDialog", "The control " + ((Object) title) + " is already a favorite");
            finish();
        }
        Dialog createDialog = createDialog(verifyComponentAndGetLabel);
        this.dialog = createDialog;
        if (createDialog != null) {
            createDialog.show();
        }
    }

    public final CharSequence verifyComponentAndGetLabel() {
        ControlsListingController controlsListingController = this.controlsListingController;
        ComponentName componentName = this.controlComponent;
        ComponentName componentName2 = componentName;
        if (componentName == null) {
            componentName2 = null;
        }
        return controlsListingController.getAppLabel(componentName2);
    }
}