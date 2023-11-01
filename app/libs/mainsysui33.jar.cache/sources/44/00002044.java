package com.android.systemui.privacy;

import android.content.Context;
import android.content.Intent;
import com.android.systemui.privacy.PrivacyDialog;
import com.android.systemui.privacy.PrivacyDialogController;
import java.util.List;
import kotlin.Unit;
import kotlin.jvm.functions.Function4;

/* loaded from: mainsysui33.jar:com/android/systemui/privacy/PrivacyDialogControllerKt.class */
public final class PrivacyDialogControllerKt {
    public static final PrivacyDialogControllerKt$defaultDialogProvider$1 defaultDialogProvider = new PrivacyDialogController.DialogProvider() { // from class: com.android.systemui.privacy.PrivacyDialogControllerKt$defaultDialogProvider$1
        @Override // com.android.systemui.privacy.PrivacyDialogController.DialogProvider
        public PrivacyDialog makeDialog(Context context, List<PrivacyDialog.PrivacyElement> list, Function4<? super String, ? super Integer, ? super CharSequence, ? super Intent, Unit> function4) {
            return new PrivacyDialog(context, list, function4);
        }
    };
}