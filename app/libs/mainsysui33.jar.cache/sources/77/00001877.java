package com.android.systemui.globalactions;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ListAdapter;
import androidx.constraintlayout.helper.widget.Flow;
import com.android.systemui.R$drawable;
import com.android.systemui.R$id;
import com.android.systemui.R$layout;

/* loaded from: mainsysui33.jar:com/android/systemui/globalactions/GlobalActionsPowerDialog.class */
public class GlobalActionsPowerDialog {
    public static Dialog create(Context context, ListAdapter listAdapter) {
        ViewGroup viewGroup = (ViewGroup) LayoutInflater.from(context).inflate(R$layout.global_actions_power_dialog_flow, (ViewGroup) null);
        Flow flow = (Flow) viewGroup.findViewById(R$id.power_flow);
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View view = listAdapter.getView(i, null, viewGroup);
            view.setId(View.generateViewId());
            viewGroup.addView(view);
            flow.addView(view);
        }
        Resources resources = context.getResources();
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(1);
        dialog.setContentView(viewGroup);
        Window window = dialog.getWindow();
        window.setType(2020);
        window.setTitle("");
        window.setBackgroundDrawable(resources.getDrawable(R$drawable.global_actions_lite_background, context.getTheme()));
        window.addFlags(131072);
        return dialog;
    }
}