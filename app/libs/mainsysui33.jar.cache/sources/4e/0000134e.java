package com.android.systemui.clipboardoverlay;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import com.android.systemui.R$id;
import com.android.systemui.R$layout;
import com.android.systemui.R$string;
import java.util.Objects;

/* loaded from: mainsysui33.jar:com/android/systemui/clipboardoverlay/EditTextActivity.class */
public class EditTextActivity extends Activity implements ClipboardManager.OnPrimaryClipChangedListener {
    public TextView mAttribution;
    public ClipboardManager mClipboardManager;
    public EditText mEditText;
    public boolean mSensitive;

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.clipboardoverlay.EditTextActivity$$ExternalSyntheticLambda0.onClick(android.view.View):void] */
    public static /* synthetic */ void $r8$lambda$w5_7GNw8BPlPe2OD9DGLe6YDagQ(EditTextActivity editTextActivity, View view) {
        editTextActivity.lambda$onCreate$0(view);
    }

    public /* synthetic */ void lambda$onCreate$0(View view) {
        saveToClipboard();
    }

    public final void hideIme() {
        ((InputMethodManager) getSystemService(InputMethodManager.class)).hideSoftInputFromWindow(this.mEditText.getWindowToken(), 0);
    }

    @Override // android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R$layout.clipboard_edit_text_activity);
        findViewById(R$id.done_button).setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.clipboardoverlay.EditTextActivity$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                EditTextActivity.$r8$lambda$w5_7GNw8BPlPe2OD9DGLe6YDagQ(EditTextActivity.this, view);
            }
        });
        this.mEditText = (EditText) findViewById(R$id.edit_text);
        this.mAttribution = (TextView) findViewById(R$id.attribution);
        ClipboardManager clipboardManager = (ClipboardManager) getSystemService(ClipboardManager.class);
        Objects.requireNonNull(clipboardManager);
        this.mClipboardManager = clipboardManager;
    }

    @Override // android.app.Activity
    public void onPause() {
        this.mClipboardManager.removePrimaryClipChangedListener(this);
        super.onPause();
    }

    @Override // android.content.ClipboardManager.OnPrimaryClipChangedListener
    public void onPrimaryClipChanged() {
        hideIme();
        finish();
    }

    @Override // android.app.Activity
    public void onStart() {
        super.onStart();
        ClipData primaryClip = this.mClipboardManager.getPrimaryClip();
        if (primaryClip == null) {
            finish();
            return;
        }
        PackageManager packageManager = getApplicationContext().getPackageManager();
        boolean z = true;
        try {
            this.mAttribution.setText(getResources().getString(R$string.clipboard_edit_source, packageManager.getApplicationLabel(packageManager.getApplicationInfo(this.mClipboardManager.getPrimaryClipSource(), PackageManager.ApplicationInfoFlags.of(0L)))));
        } catch (PackageManager.NameNotFoundException e) {
            Log.w("EditTextActivity", "Package not found: " + this.mClipboardManager.getPrimaryClipSource(), e);
        }
        this.mEditText.setText(primaryClip.getItemAt(0).getText());
        this.mEditText.requestFocus();
        this.mEditText.setSelection(0);
        if (primaryClip.getDescription().getExtras() == null || !primaryClip.getDescription().getExtras().getBoolean("android.content.extra.IS_SENSITIVE")) {
            z = false;
        }
        this.mSensitive = z;
        this.mClipboardManager.addPrimaryClipChangedListener(this);
    }

    public final void saveToClipboard() {
        hideIme();
        Editable text = this.mEditText.getText();
        text.clearSpans();
        ClipData newPlainText = ClipData.newPlainText("text", text);
        PersistableBundle persistableBundle = new PersistableBundle();
        persistableBundle.putBoolean("android.content.extra.IS_SENSITIVE", this.mSensitive);
        newPlainText.getDescription().setExtras(persistableBundle);
        this.mClipboardManager.setPrimaryClip(newPlainText);
        finish();
    }
}