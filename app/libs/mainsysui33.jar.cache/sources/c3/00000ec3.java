package com.android.settingslib.users;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;
import com.android.settingslib.R$id;
import com.android.settingslib.R$layout;
import com.android.settingslib.R$string;

/* loaded from: mainsysui33.jar:com/android/settingslib/users/UserCreatingDialog.class */
public class UserCreatingDialog extends AlertDialog {
    public UserCreatingDialog(Context context) {
        this(context, false);
    }

    public UserCreatingDialog(Context context, boolean z) {
        super(context, 16974546);
        inflateContent(z);
        getWindow().setType(2010);
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.privateFlags = 272;
        getWindow().setAttributes(attributes);
    }

    public final void inflateContent(boolean z) {
        setCancelable(false);
        View inflate = LayoutInflater.from(getContext()).inflate(R$layout.user_creation_progress_dialog, (ViewGroup) null);
        String string = getContext().getString(z ? R$string.creating_new_guest_dialog_message : R$string.creating_new_user_dialog_message);
        inflate.setAccessibilityPaneTitle(string);
        ((TextView) inflate.findViewById(R$id.message)).setText(string);
        setView(inflate);
    }
}