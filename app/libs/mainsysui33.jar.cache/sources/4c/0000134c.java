package com.android.systemui.clipboardoverlay;

import android.content.Context;
import android.widget.Toast;
import com.android.systemui.R$string;

/* loaded from: mainsysui33.jar:com/android/systemui/clipboardoverlay/ClipboardToast.class */
public class ClipboardToast extends Toast.Callback {
    public final Context mContext;
    public Toast mCopiedToast;

    public ClipboardToast(Context context) {
        this.mContext = context;
    }

    public boolean isShowing() {
        return this.mCopiedToast != null;
    }

    @Override // android.widget.Toast.Callback
    public void onToastHidden() {
        super.onToastHidden();
        this.mCopiedToast = null;
    }

    public void showCopiedToast() {
        Toast toast = this.mCopiedToast;
        if (toast != null) {
            toast.cancel();
        }
        Toast makeText = Toast.makeText(this.mContext, R$string.clipboard_overlay_text_copied, 0);
        this.mCopiedToast = makeText;
        makeText.show();
    }
}