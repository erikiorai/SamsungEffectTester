package com.android.systemui.clipboardoverlay;

import android.content.ClipData;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import com.android.systemui.R$string;

/* loaded from: mainsysui33.jar:com/android/systemui/clipboardoverlay/IntentCreator.class */
public class IntentCreator {
    public static Intent getImageEditIntent(Uri uri, Context context) {
        String string = context.getString(R$string.config_screenshotEditor);
        Intent intent = new Intent("android.intent.action.EDIT");
        if (!TextUtils.isEmpty(string)) {
            intent.setComponent(ComponentName.unflattenFromString(string));
        }
        intent.setDataAndType(uri, "image/*");
        intent.addFlags(1);
        intent.addFlags(268468224);
        intent.putExtra("edit_source_clipboard", true);
        return intent;
    }

    public static Intent getRemoteCopyIntent(ClipData clipData, Context context) {
        Intent intent = new Intent("android.intent.action.REMOTE_COPY");
        String string = context.getString(R$string.config_remoteCopyPackage);
        if (!TextUtils.isEmpty(string)) {
            intent.setComponent(ComponentName.unflattenFromString(string));
        }
        intent.setClipData(clipData);
        intent.addFlags(1);
        intent.addFlags(268468224);
        return intent;
    }

    public static Intent getShareIntent(ClipData clipData, Context context) {
        Intent intent = new Intent("android.intent.action.SEND");
        if (clipData.getItemAt(0).getUri() != null) {
            intent.setDataAndType(clipData.getItemAt(0).getUri(), clipData.getDescription().getMimeType(0));
            intent.putExtra("android.intent.extra.STREAM", clipData.getItemAt(0).getUri());
            intent.addFlags(1);
        } else {
            intent.putExtra("android.intent.extra.TEXT", clipData.getItemAt(0).coerceToText(context).toString());
            intent.setType("text/plain");
        }
        return Intent.createChooser(intent, null).addFlags(268468224).addFlags(1);
    }

    public static Intent getTextEditorIntent(Context context) {
        Intent intent = new Intent(context, EditTextActivity.class);
        intent.addFlags(268468224);
        return intent;
    }
}