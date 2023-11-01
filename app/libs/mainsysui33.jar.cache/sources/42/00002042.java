package com.android.systemui.privacy;

import android.content.Intent;
import kotlin.Unit;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.CallableReference;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* loaded from: mainsysui33.jar:com/android/systemui/privacy/PrivacyDialogController$showDialog$1$1$d$1.class */
public final /* synthetic */ class PrivacyDialogController$showDialog$1$1$d$1 extends FunctionReferenceImpl implements Function4<String, Integer, CharSequence, Intent, Unit> {
    public PrivacyDialogController$showDialog$1$1$d$1(Object obj) {
        super(4, obj, PrivacyDialogController.class, "startActivity", "startActivity(Ljava/lang/String;ILjava/lang/CharSequence;Landroid/content/Intent;)V", 0);
    }

    public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
        invoke((String) obj, ((Number) obj2).intValue(), (CharSequence) obj3, (Intent) obj4);
        return Unit.INSTANCE;
    }

    public final void invoke(String str, int i, CharSequence charSequence, Intent intent) {
        ((PrivacyDialogController) ((CallableReference) this).receiver).startActivity(str, i, charSequence, intent);
    }
}