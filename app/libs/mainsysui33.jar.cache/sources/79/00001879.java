package com.android.systemui.hdmi;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.systemui.R$id;
import com.android.systemui.R$string;
import com.android.systemui.tv.TvBottomSheetActivity;

/* loaded from: mainsysui33.jar:com/android/systemui/hdmi/HdmiCecSetMenuLanguageActivity.class */
public class HdmiCecSetMenuLanguageActivity extends TvBottomSheetActivity implements View.OnClickListener {
    public static final String TAG = HdmiCecSetMenuLanguageActivity.class.getSimpleName();
    public final HdmiCecSetMenuLanguageHelper mHdmiCecSetMenuLanguageHelper;

    public HdmiCecSetMenuLanguageActivity(HdmiCecSetMenuLanguageHelper hdmiCecSetMenuLanguageHelper) {
        this.mHdmiCecSetMenuLanguageHelper = hdmiCecSetMenuLanguageHelper;
    }

    /* JADX DEBUG: Multi-variable search result rejected for r3v0, resolved type: com.android.systemui.hdmi.HdmiCecSetMenuLanguageActivity */
    /* JADX WARN: Multi-variable type inference failed */
    public void initUI(CharSequence charSequence, CharSequence charSequence2) {
        TextView textView = (TextView) findViewById(R$id.bottom_sheet_title);
        TextView textView2 = (TextView) findViewById(R$id.bottom_sheet_body);
        ImageView imageView = (ImageView) findViewById(R$id.bottom_sheet_icon);
        ImageView imageView2 = (ImageView) findViewById(R$id.bottom_sheet_second_icon);
        Button button = (Button) findViewById(R$id.bottom_sheet_positive_button);
        Button button2 = (Button) findViewById(R$id.bottom_sheet_negative_button);
        textView.setText(charSequence);
        textView2.setText(charSequence2);
        imageView.setImageResource(17302854);
        imageView2.setVisibility(8);
        button.setText(R$string.hdmi_cec_set_menu_language_accept);
        button.setOnClickListener(this);
        button2.setText(R$string.hdmi_cec_set_menu_language_decline);
        button2.setOnClickListener(this);
        button2.requestFocus();
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        if (view.getId() == R$id.bottom_sheet_positive_button) {
            this.mHdmiCecSetMenuLanguageHelper.acceptLocale();
        } else {
            this.mHdmiCecSetMenuLanguageHelper.declineLocale();
        }
        finish();
    }

    /* JADX DEBUG: Multi-variable search result rejected for r3v0, resolved type: com.android.systemui.hdmi.HdmiCecSetMenuLanguageActivity */
    /* JADX WARN: Multi-variable type inference failed */
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().addPrivateFlags(524288);
        this.mHdmiCecSetMenuLanguageHelper.setLocale(getIntent().getStringExtra("android.hardware.hdmi.extra.LOCALE"));
        if (this.mHdmiCecSetMenuLanguageHelper.isLocaleDenylisted()) {
            finish();
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for r8v0, resolved type: com.android.systemui.hdmi.HdmiCecSetMenuLanguageActivity */
    /* JADX WARN: Multi-variable type inference failed */
    public void onResume() {
        super/*android.app.Activity*/.onResume();
        initUI(getString(R$string.hdmi_cec_set_menu_language_title, new Object[]{this.mHdmiCecSetMenuLanguageHelper.getLocale().getDisplayLanguage()}), getString(R$string.hdmi_cec_set_menu_language_description));
    }
}