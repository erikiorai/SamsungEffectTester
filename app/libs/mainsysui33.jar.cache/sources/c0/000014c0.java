package com.android.systemui.controls.ui;

import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.service.controls.Control;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
import com.android.systemui.R$string;

/* loaded from: mainsysui33.jar:com/android/systemui/controls/ui/StatusBehavior.class */
public final class StatusBehavior implements Behavior {
    public ControlViewHolder cvh;

    @Override // com.android.systemui.controls.ui.Behavior
    public void bind(final ControlWithState controlWithState, int i) {
        int i2;
        Control control = controlWithState.getControl();
        int status = control != null ? control.getStatus() : 0;
        if (status == 2) {
            getCvh().getLayout().setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.controls.ui.StatusBehavior$bind$msg$1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    StatusBehavior statusBehavior = StatusBehavior.this;
                    statusBehavior.showNotFoundDialog(statusBehavior.getCvh(), controlWithState);
                }
            });
            getCvh().getLayout().setOnLongClickListener(new View.OnLongClickListener() { // from class: com.android.systemui.controls.ui.StatusBehavior$bind$msg$2
                @Override // android.view.View.OnLongClickListener
                public final boolean onLongClick(View view) {
                    StatusBehavior statusBehavior = StatusBehavior.this;
                    statusBehavior.showNotFoundDialog(statusBehavior.getCvh(), controlWithState);
                    return true;
                }
            });
            i2 = R$string.controls_error_removed;
        } else if (status == 3) {
            i2 = R$string.controls_error_generic;
        } else if (status != 4) {
            getCvh().setLoading(true);
            i2 = 17040621;
        } else {
            i2 = R$string.controls_error_timeout;
        }
        ControlViewHolder.setStatusText$default(getCvh(), getCvh().getContext().getString(i2), false, 2, null);
        ControlViewHolder.applyRenderInfo$frameworks__base__packages__SystemUI__android_common__SystemUI_core$default(getCvh(), false, i, false, 4, null);
    }

    public final ControlViewHolder getCvh() {
        ControlViewHolder controlViewHolder = this.cvh;
        if (controlViewHolder != null) {
            return controlViewHolder;
        }
        return null;
    }

    @Override // com.android.systemui.controls.ui.Behavior
    public void initialize(ControlViewHolder controlViewHolder) {
        setCvh(controlViewHolder);
    }

    public final void setCvh(ControlViewHolder controlViewHolder) {
        this.cvh = controlViewHolder;
    }

    public final void showNotFoundDialog(final ControlViewHolder controlViewHolder, final ControlWithState controlWithState) {
        PackageManager packageManager = controlViewHolder.getContext().getPackageManager();
        CharSequence applicationLabel = packageManager.getApplicationLabel(packageManager.getApplicationInfo(controlWithState.getComponentName().getPackageName(), RecyclerView.ViewHolder.FLAG_IGNORE));
        final AlertDialog.Builder builder = new AlertDialog.Builder(controlViewHolder.getContext(), 16974545);
        Resources resources = controlViewHolder.getContext().getResources();
        builder.setTitle(resources.getString(R$string.controls_error_removed_title));
        builder.setMessage(resources.getString(R$string.controls_error_removed_message, controlViewHolder.getTitle().getText(), applicationLabel));
        builder.setPositiveButton(R$string.controls_open_app, new DialogInterface.OnClickListener() { // from class: com.android.systemui.controls.ui.StatusBehavior$showNotFoundDialog$builder$1$1
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                PendingIntent appIntent;
                try {
                    Control control = ControlWithState.this.getControl();
                    if (control != null && (appIntent = control.getAppIntent()) != null) {
                        appIntent.send();
                    }
                    builder.getContext().sendBroadcast(new Intent("android.intent.action.CLOSE_SYSTEM_DIALOGS"));
                } catch (PendingIntent.CanceledException e) {
                    controlViewHolder.setErrorStatus();
                }
                dialogInterface.dismiss();
            }
        });
        builder.setNegativeButton(17039360, new DialogInterface.OnClickListener() { // from class: com.android.systemui.controls.ui.StatusBehavior$showNotFoundDialog$builder$1$2
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        AlertDialog create = builder.create();
        create.getWindow().setType(2020);
        create.show();
        controlViewHolder.setVisibleDialog(create);
    }
}