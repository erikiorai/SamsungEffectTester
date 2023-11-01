package com.android.systemui.screenshot;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import com.android.systemui.R$string;

/* loaded from: mainsysui33.jar:com/android/systemui/screenshot/ActionIntentCreator.class */
public final class ActionIntentCreator {
    public static final ActionIntentCreator INSTANCE = new ActionIntentCreator();

    public final Intent createEditIntent(Uri uri, Context context) {
        Intent intent = new Intent("android.intent.action.EDIT");
        String string = context.getString(R$string.config_screenshotEditor);
        if (string != null) {
            if (string.length() > 0) {
                intent.setComponent(ComponentName.unflattenFromString(string));
            }
        }
        return intent.setDataAndType(uri, "image/png").addFlags(1).addFlags(2).addFlags(268435456).addFlags(32768);
    }

    public final Intent createShareIntent(Uri uri, String str) {
        Intent intent = new Intent("android.intent.action.SEND");
        intent.setDataAndType(uri, "image/png");
        intent.putExtra("android.intent.extra.STREAM", uri);
        intent.setClipData(new ClipData(new ClipDescription("content", new String[]{"text/plain"}), new ClipData.Item(uri)));
        intent.putExtra("android.intent.extra.SUBJECT", str);
        intent.addFlags(1);
        intent.addFlags(2);
        return Intent.createChooser(intent, null).addFlags(32768).addFlags(268435456).addFlags(1);
    }
}