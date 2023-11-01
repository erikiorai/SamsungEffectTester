package com.android.systemui.controls.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.service.controls.actions.BooleanAction;
import android.service.controls.actions.CommandAction;
import android.service.controls.actions.ControlAction;
import android.service.controls.actions.FloatAction;
import android.service.controls.actions.ModeAction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import com.android.systemui.R$id;
import com.android.systemui.R$layout;
import com.android.systemui.R$string;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

/* loaded from: mainsysui33.jar:com/android/systemui/controls/ui/ChallengeDialogs.class */
public final class ChallengeDialogs {
    public static final ChallengeDialogs INSTANCE = new ChallengeDialogs();

    public final ControlAction addChallengeValue(ControlAction controlAction, String str) {
        ControlAction modeAction;
        String templateId = controlAction.getTemplateId();
        if (controlAction instanceof BooleanAction) {
            modeAction = new BooleanAction(templateId, ((BooleanAction) controlAction).getNewState(), str);
        } else if (controlAction instanceof FloatAction) {
            modeAction = new FloatAction(templateId, ((FloatAction) controlAction).getNewValue(), str);
        } else if (controlAction instanceof CommandAction) {
            modeAction = new CommandAction(templateId, str);
        } else if (!(controlAction instanceof ModeAction)) {
            throw new IllegalStateException("'action' is not a known type: " + controlAction);
        } else {
            modeAction = new ModeAction(templateId, ((ModeAction) controlAction).getNewMode(), str);
        }
        return modeAction;
    }

    public final Dialog createConfirmationDialog(final ControlViewHolder controlViewHolder, final Function0<Unit> function0) {
        final ControlAction lastAction = controlViewHolder.getLastAction();
        if (lastAction == null) {
            Log.e("ControlsUiController", "Confirmation Dialog attempted but no last action is set. Will not show");
            return null;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(controlViewHolder.getContext(), 16974545);
        builder.setTitle(controlViewHolder.getContext().getResources().getString(R$string.controls_confirmation_message, controlViewHolder.getTitle().getText()));
        builder.setPositiveButton(17039370, new DialogInterface.OnClickListener() { // from class: com.android.systemui.controls.ui.ChallengeDialogs$createConfirmationDialog$builder$1$1
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                ControlAction addChallengeValue;
                ControlViewHolder controlViewHolder2 = ControlViewHolder.this;
                addChallengeValue = ChallengeDialogs.INSTANCE.addChallengeValue(lastAction, "true");
                controlViewHolder2.action(addChallengeValue);
                dialogInterface.dismiss();
            }
        });
        builder.setNegativeButton(17039360, new DialogInterface.OnClickListener() { // from class: com.android.systemui.controls.ui.ChallengeDialogs$createConfirmationDialog$builder$1$2
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                function0.invoke();
                dialogInterface.cancel();
            }
        });
        AlertDialog create = builder.create();
        create.getWindow().setType(2020);
        return create;
    }

    /* JADX WARN: Type inference failed for: r0v15, types: [android.app.AlertDialog, android.app.Dialog, com.android.systemui.controls.ui.ChallengeDialogs$createPinDialog$1] */
    public final Dialog createPinDialog(final ControlViewHolder controlViewHolder, final boolean z, boolean z2, final Function0<Unit> function0) {
        final ControlAction lastAction = controlViewHolder.getLastAction();
        if (lastAction == null) {
            Log.e("ControlsUiController", "PIN Dialog attempted but no last action is set. Will not show");
            return null;
        }
        Resources resources = controlViewHolder.getContext().getResources();
        Pair pair = z2 ? new Pair(resources.getString(R$string.controls_pin_wrong), Integer.valueOf(R$string.controls_pin_instructions_retry)) : new Pair(resources.getString(R$string.controls_pin_verify, controlViewHolder.getTitle().getText()), Integer.valueOf(R$string.controls_pin_instructions));
        String str = (String) pair.component1();
        final int intValue = ((Number) pair.component2()).intValue();
        final ?? r0 = new AlertDialog(controlViewHolder.getContext()) { // from class: com.android.systemui.controls.ui.ChallengeDialogs$createPinDialog$1
            @Override // android.app.Dialog, android.content.DialogInterface
            public void dismiss() {
                View decorView;
                InputMethodManager inputMethodManager;
                Window window = getWindow();
                if (window != null && (decorView = window.getDecorView()) != null && (inputMethodManager = (InputMethodManager) decorView.getContext().getSystemService(InputMethodManager.class)) != null) {
                    inputMethodManager.hideSoftInputFromWindow(decorView.getWindowToken(), 0);
                }
                super.dismiss();
            }
        };
        r0.setTitle(str);
        r0.setView(LayoutInflater.from(r0.getContext()).inflate(R$layout.controls_dialog_pin, (ViewGroup) null));
        r0.setButton(-1, r0.getContext().getText(17039370), new DialogInterface.OnClickListener() { // from class: com.android.systemui.controls.ui.ChallengeDialogs$createPinDialog$2$1
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                ControlAction addChallengeValue;
                if (dialogInterface instanceof Dialog) {
                    Dialog dialog = (Dialog) dialogInterface;
                    int i2 = R$id.controls_pin_input;
                    dialog.requireViewById(i2);
                    String obj = ((EditText) dialog.requireViewById(i2)).getText().toString();
                    ControlViewHolder controlViewHolder2 = ControlViewHolder.this;
                    addChallengeValue = ChallengeDialogs.INSTANCE.addChallengeValue(lastAction, obj);
                    controlViewHolder2.action(addChallengeValue);
                    dialogInterface.dismiss();
                }
            }
        });
        r0.setButton(-2, r0.getContext().getText(17039360), new DialogInterface.OnClickListener() { // from class: com.android.systemui.controls.ui.ChallengeDialogs$createPinDialog$2$2
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                function0.invoke();
                dialogInterface.cancel();
            }
        });
        Window window = r0.getWindow();
        window.setType(2020);
        window.setSoftInputMode(4);
        r0.setOnShowListener(new DialogInterface.OnShowListener() { // from class: com.android.systemui.controls.ui.ChallengeDialogs$createPinDialog$2$4
            @Override // android.content.DialogInterface.OnShowListener
            public final void onShow(DialogInterface dialogInterface) {
                final EditText editText = (EditText) requireViewById(R$id.controls_pin_input);
                editText.setHint(intValue);
                ChallengeDialogs$createPinDialog$1 challengeDialogs$createPinDialog$1 = ChallengeDialogs$createPinDialog$1.this;
                int i = R$id.controls_pin_use_alpha;
                final CheckBox checkBox = (CheckBox) challengeDialogs$createPinDialog$1.requireViewById(i);
                checkBox.setChecked(z);
                ChallengeDialogs.INSTANCE.setInputType(editText, checkBox.isChecked());
                ((CheckBox) requireViewById(i)).setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.controls.ui.ChallengeDialogs$createPinDialog$2$4.1
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        ChallengeDialogs.INSTANCE.setInputType(editText, checkBox.isChecked());
                    }
                });
                editText.requestFocus();
            }
        });
        return r0;
    }

    public final void setInputType(EditText editText, boolean z) {
        if (z) {
            editText.setInputType(129);
        } else {
            editText.setInputType(18);
        }
    }
}