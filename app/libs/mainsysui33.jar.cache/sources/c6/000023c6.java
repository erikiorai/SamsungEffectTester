package com.android.systemui.screenrecord;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewStub;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import com.android.systemui.R$id;
import com.android.systemui.R$layout;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import java.util.ArrayList;
import java.util.List;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: mainsysui33.jar:com/android/systemui/screenrecord/BaseScreenSharePermissionDialog.class */
public class BaseScreenSharePermissionDialog extends SystemUIDialog implements AdapterView.OnItemSelectedListener {
    public final String appName;
    public final Integer dialogIconDrawable;
    public final Integer dialogIconTint;
    public TextView dialogTitle;
    public Spinner screenShareModeSpinner;
    public final List<ScreenShareOption> screenShareOptions;
    public ScreenShareOption selectedScreenShareOption;
    public TextView startButton;
    public TextView warning;

    public BaseScreenSharePermissionDialog(Context context, List<ScreenShareOption> list, String str, Integer num, Integer num2) {
        super(context);
        this.screenShareOptions = list;
        this.appName = str;
        this.dialogIconDrawable = num;
        this.dialogIconTint = num2;
        this.selectedScreenShareOption = (ScreenShareOption) CollectionsKt___CollectionsKt.first(list);
    }

    public /* synthetic */ BaseScreenSharePermissionDialog(Context context, List list, String str, Integer num, Integer num2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, list, str, (i & 8) != 0 ? null : num, (i & 16) != 0 ? null : num2);
    }

    /* JADX DEBUG: Multi-variable search result rejected for r3v0, resolved type: com.android.systemui.screenrecord.BaseScreenSharePermissionDialog */
    /* JADX WARN: Multi-variable type inference failed */
    public final void createOptionsView(Integer num) {
        if (num == null) {
            return;
        }
        ViewStub viewStub = (ViewStub) findViewById(R$id.options_stub);
        viewStub.setLayoutResource(num.intValue());
        viewStub.inflate();
    }

    public Integer getOptionsViewLayoutId() {
        return null;
    }

    public final ScreenShareOption getSelectedScreenShareOption() {
        return this.selectedScreenShareOption;
    }

    /* JADX DEBUG: Multi-variable search result rejected for r7v0, resolved type: com.android.systemui.screenrecord.BaseScreenSharePermissionDialog */
    /* JADX WARN: Multi-variable type inference failed */
    public final String getWarningText() {
        return getContext().getString(this.selectedScreenShareOption.getWarningText(), this.appName);
    }

    public final void initScreenShareOptions() {
        this.selectedScreenShareOption = (ScreenShareOption) CollectionsKt___CollectionsKt.first(this.screenShareOptions);
        TextView textView = this.warning;
        TextView textView2 = textView;
        if (textView == null) {
            textView2 = null;
        }
        textView2.setText(getWarningText());
        initScreenShareSpinner();
    }

    /* JADX DEBUG: Multi-variable search result rejected for r6v0, resolved type: com.android.systemui.screenrecord.BaseScreenSharePermissionDialog */
    /* JADX WARN: Multi-variable type inference failed */
    public final void initScreenShareSpinner() {
        List<ScreenShareOption> list = this.screenShareOptions;
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list, 10));
        for (ScreenShareOption screenShareOption : list) {
            arrayList.add(getContext().getString(screenShareOption.getSpinnerText()));
        }
        ArrayAdapter arrayAdapter = new ArrayAdapter(getContext().getApplicationContext(), R$layout.screen_share_dialog_spinner_text, (String[]) arrayList.toArray(new String[0]));
        arrayAdapter.setDropDownViewResource(R$layout.screen_share_dialog_spinner_item_text);
        Spinner spinner = (Spinner) findViewById(R$id.screen_share_mode_spinner);
        this.screenShareModeSpinner = spinner;
        Spinner spinner2 = spinner;
        if (spinner == null) {
            spinner2 = null;
        }
        spinner2.setAdapter((SpinnerAdapter) arrayAdapter);
        Spinner spinner3 = this.screenShareModeSpinner;
        if (spinner3 == null) {
            spinner3 = null;
        }
        spinner3.setOnItemSelectedListener(this);
    }

    /* JADX DEBUG: Multi-variable search result rejected for r5v0, resolved type: com.android.systemui.screenrecord.BaseScreenSharePermissionDialog */
    /* JADX WARN: Multi-variable type inference failed */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Window window = getWindow();
        window.addPrivateFlags(16);
        window.setGravity(17);
        setContentView(R$layout.screen_share_dialog);
        this.dialogTitle = (TextView) findViewById(R$id.screen_share_dialog_title);
        this.warning = (TextView) findViewById(R$id.text_warning);
        this.startButton = (TextView) findViewById(R$id.button_start);
        ((TextView) findViewById(R$id.button_cancel)).setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.screenrecord.BaseScreenSharePermissionDialog$onCreate$2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                BaseScreenSharePermissionDialog.this.dismiss();
            }
        });
        updateIcon();
        initScreenShareOptions();
        createOptionsView(getOptionsViewLayoutId());
    }

    @Override // android.widget.AdapterView.OnItemSelectedListener
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
        this.selectedScreenShareOption = this.screenShareOptions.get(i);
        TextView textView = this.warning;
        TextView textView2 = textView;
        if (textView == null) {
            textView2 = null;
        }
        textView2.setText(getWarningText());
    }

    @Override // android.widget.AdapterView.OnItemSelectedListener
    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    /* JADX DEBUG: Multi-variable search result rejected for r7v0, resolved type: com.android.systemui.screenrecord.BaseScreenSharePermissionDialog */
    /* JADX WARN: Multi-variable type inference failed */
    public final void setDialogTitle(int i) {
        String string = getContext().getString(i, this.appName);
        TextView textView = this.dialogTitle;
        TextView textView2 = textView;
        if (textView == null) {
            textView2 = null;
        }
        textView2.setText(string);
    }

    public final void setStartButtonOnClickListener(View.OnClickListener onClickListener) {
        TextView textView = this.startButton;
        TextView textView2 = textView;
        if (textView == null) {
            textView2 = null;
        }
        textView2.setOnClickListener(onClickListener);
    }

    public final void setStartButtonText(int i) {
        TextView textView = this.startButton;
        TextView textView2 = textView;
        if (textView == null) {
            textView2 = null;
        }
        textView2.setText(i);
    }

    /* JADX DEBUG: Multi-variable search result rejected for r4v0, resolved type: com.android.systemui.screenrecord.BaseScreenSharePermissionDialog */
    /* JADX WARN: Multi-variable type inference failed */
    public final void updateIcon() {
        ImageView imageView = (ImageView) findViewById(R$id.screen_share_dialog_icon);
        if (this.dialogIconTint != null) {
            imageView.setColorFilter(getContext().getColor(this.dialogIconTint.intValue()));
        }
        if (this.dialogIconDrawable != null) {
            imageView.setImageDrawable(getContext().getDrawable(this.dialogIconDrawable.intValue()));
        }
    }
}