package com.android.systemui.clipboardoverlay;

import android.app.RemoteAction;
import android.content.ClipData;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;
import android.provider.DeviceConfig;
import android.text.TextUtils;
import android.view.textclassifier.TextClassificationManager;
import android.view.textclassifier.TextClassifier;
import android.view.textclassifier.TextLinks;
import com.android.systemui.R$string;
import java.util.ArrayList;
import java.util.Optional;
import java.util.function.Predicate;

/* loaded from: mainsysui33.jar:com/android/systemui/clipboardoverlay/ClipboardOverlayUtils.class */
public class ClipboardOverlayUtils {
    public final TextClassifier mTextClassifier;

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.clipboardoverlay.ClipboardOverlayUtils$$ExternalSyntheticLambda0.test(java.lang.Object):boolean] */
    public static /* synthetic */ boolean $r8$lambda$GNSP8EeadmZA093CWHXqKXliN54(String str, RemoteAction remoteAction) {
        return lambda$getAction$0(str, remoteAction);
    }

    public ClipboardOverlayUtils(TextClassificationManager textClassificationManager) {
        this.mTextClassifier = textClassificationManager.getTextClassifier();
    }

    public static /* synthetic */ boolean lambda$getAction$0(String str, RemoteAction remoteAction) {
        ComponentName component = remoteAction.getActionIntent().getIntent().getComponent();
        return (component == null || TextUtils.equals(str, component.getPackageName())) ? false : true;
    }

    public Optional<RemoteAction> getAction(ClipData.Item item, final String str) {
        return getActions(item).stream().filter(new Predicate() { // from class: com.android.systemui.clipboardoverlay.ClipboardOverlayUtils$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ClipboardOverlayUtils.$r8$lambda$GNSP8EeadmZA093CWHXqKXliN54(str, (RemoteAction) obj);
            }
        }).findFirst();
    }

    public final ArrayList<RemoteAction> getActions(ClipData.Item item) {
        ArrayList<RemoteAction> arrayList = new ArrayList<>();
        for (TextLinks.TextLink textLink : item.getTextLinks().getLinks()) {
            arrayList.addAll(this.mTextClassifier.classifyText(item.getText(), textLink.getStart(), textLink.getEnd(), null).getActions());
        }
        return arrayList;
    }

    public boolean isRemoteCopy(Context context, ClipData clipData, String str) {
        boolean z = false;
        if (clipData != null) {
            z = false;
            if (clipData.getDescription().getExtras() != null) {
                z = false;
                if (clipData.getDescription().getExtras().getBoolean("android.content.extra.IS_REMOTE_DEVICE")) {
                    if (Build.isDebuggable() && DeviceConfig.getBoolean("systemui", "clipboard_ignore_remote_copy_source", false)) {
                        return true;
                    }
                    ComponentName unflattenFromString = ComponentName.unflattenFromString(context.getResources().getString(R$string.config_remoteCopyPackage));
                    z = false;
                    if (unflattenFromString != null) {
                        z = unflattenFromString.getPackageName().equals(str);
                    }
                }
            }
        }
        return z;
    }
}