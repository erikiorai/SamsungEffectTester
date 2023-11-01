package com.android.systemui.hdmi;

import com.android.internal.app.LocalePicker;
import com.android.systemui.util.settings.SecureSettings;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Locale;
import java.util.concurrent.Executor;

/* loaded from: mainsysui33.jar:com/android/systemui/hdmi/HdmiCecSetMenuLanguageHelper.class */
public class HdmiCecSetMenuLanguageHelper {
    public final Executor mBackgroundExecutor;
    public HashSet<String> mDenylist;
    public Locale mLocale;
    public final SecureSettings mSecureSettings;

    public HdmiCecSetMenuLanguageHelper(Executor executor, SecureSettings secureSettings) {
        this.mBackgroundExecutor = executor;
        this.mSecureSettings = secureSettings;
        String stringForUser = secureSettings.getStringForUser("hdmi_cec_set_menu_language_denylist", -2);
        this.mDenylist = new HashSet<>(stringForUser == null ? Collections.EMPTY_SET : Arrays.asList(stringForUser.split(",")));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$acceptLocale$0() {
        LocalePicker.updateLocale(this.mLocale);
    }

    public void acceptLocale() {
        this.mBackgroundExecutor.execute(new Runnable() { // from class: com.android.systemui.hdmi.HdmiCecSetMenuLanguageHelper$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                HdmiCecSetMenuLanguageHelper.this.lambda$acceptLocale$0();
            }
        });
    }

    public void declineLocale() {
        this.mDenylist.add(this.mLocale.toLanguageTag());
        this.mSecureSettings.putStringForUser("hdmi_cec_set_menu_language_denylist", String.join(",", this.mDenylist), -2);
    }

    public Locale getLocale() {
        return this.mLocale;
    }

    public boolean isLocaleDenylisted() {
        return this.mDenylist.contains(this.mLocale.toLanguageTag());
    }

    public void setLocale(String str) {
        this.mLocale = Locale.forLanguageTag(str);
    }
}