package com.android.settingslib.development;

import android.os.SystemProperties;
import androidx.lifecycle.LifecycleObserver;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import com.android.settingslib.R$array;
import com.android.settingslib.core.ConfirmationDialogController;

/* loaded from: mainsysui33.jar:com/android/settingslib/development/AbstractLogpersistPreferenceController.class */
public abstract class AbstractLogpersistPreferenceController extends DeveloperOptionsPreferenceController implements Preference.OnPreferenceChangeListener, LifecycleObserver, ConfirmationDialogController {
    public static final String ACTUAL_LOGPERSIST_PROPERTY = "logd.logpersistd";
    public static final String ACTUAL_LOGPERSIST_PROPERTY_BUFFER = "logd.logpersistd.buffer";
    public static final String SELECT_LOGPERSIST_PROPERTY_SERVICE = "logcatd";
    public ListPreference mLogpersist;
    public boolean mLogpersistCleared;

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public boolean onPreferenceChange(Preference preference, Object obj) {
        if (preference == this.mLogpersist) {
            writeLogpersistOption(obj, false);
            return true;
        }
        return false;
    }

    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:21:0x0063 -> B:18:0x005c). Please submit an issue!!! */
    public void setLogpersistOff(boolean z) {
        String str;
        SystemProperties.set("persist.logd.logpersistd.buffer", "");
        SystemProperties.set(ACTUAL_LOGPERSIST_PROPERTY_BUFFER, "");
        SystemProperties.set("persist.logd.logpersistd", "");
        SystemProperties.set(ACTUAL_LOGPERSIST_PROPERTY, z ? "" : "stop");
        SystemPropPoker.getInstance().poke();
        if (z) {
            updateLogpersistValues();
            return;
        }
        for (int i = 0; i < 3 && (str = SystemProperties.get(ACTUAL_LOGPERSIST_PROPERTY)) != null && !str.equals(""); i++) {
            try {
                Thread.sleep(100L);
            } catch (InterruptedException e) {
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x0027, code lost:
        if (r0.length() == 0) goto L46;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void updateLogpersistValues() {
        char c;
        if (this.mLogpersist == null) {
            return;
        }
        String str = SystemProperties.get(ACTUAL_LOGPERSIST_PROPERTY);
        String str2 = str;
        if (str == null) {
            str2 = "";
        }
        String str3 = SystemProperties.get(ACTUAL_LOGPERSIST_PROPERTY_BUFFER);
        String str4 = str3 != null ? str3 : "all";
        if (!str2.equals(SELECT_LOGPERSIST_PROPERTY_SERVICE)) {
            c = 0;
        } else if (str4.equals("kernel")) {
            c = 3;
        } else {
            if (!str4.equals("all") && !str4.contains("radio") && str4.contains("security") && str4.contains("kernel")) {
                c = 2;
                if (!str4.contains("default")) {
                    int i = 0;
                    while (true) {
                        c = 2;
                        if (i >= 4) {
                            break;
                        } else if (!str4.contains(new String[]{"main", "events", "system", "crash"}[i])) {
                            break;
                        } else {
                            i++;
                        }
                    }
                }
            }
            c = 1;
        }
        this.mLogpersist.setValue(this.mContext.getResources().getStringArray(R$array.select_logpersist_values)[c]);
        this.mLogpersist.setSummary(this.mContext.getResources().getStringArray(R$array.select_logpersist_summaries)[c]);
        if (c != 0) {
            this.mLogpersistCleared = false;
        } else if (this.mLogpersistCleared) {
        } else {
            SystemProperties.set(ACTUAL_LOGPERSIST_PROPERTY, "clear");
            SystemPropPoker.getInstance().poke();
            this.mLogpersistCleared = true;
        }
    }

    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:46:0x00dc -> B:29:0x0099). Please submit an issue!!! */
    public void writeLogpersistOption(Object obj, boolean z) {
        String str;
        String str2;
        if (this.mLogpersist == null) {
            return;
        }
        String str3 = SystemProperties.get("persist.log.tag");
        Object obj2 = obj;
        boolean z2 = z;
        if (str3 != null) {
            obj2 = obj;
            z2 = z;
            if (str3.startsWith("Settings")) {
                obj2 = null;
                z2 = true;
            }
        }
        if (obj2 == null || obj2.toString().equals("")) {
            if (z2) {
                this.mLogpersistCleared = false;
            } else if (!this.mLogpersistCleared && (str = SystemProperties.get(ACTUAL_LOGPERSIST_PROPERTY)) != null && str.equals(SELECT_LOGPERSIST_PROPERTY_SERVICE)) {
                showConfirmationDialog(this.mLogpersist);
                return;
            }
            setLogpersistOff(true);
            return;
        }
        String str4 = SystemProperties.get(ACTUAL_LOGPERSIST_PROPERTY_BUFFER);
        if (str4 != null && !str4.equals(obj2.toString())) {
            setLogpersistOff(false);
        }
        SystemProperties.set("persist.logd.logpersistd.buffer", obj2.toString());
        SystemProperties.set("persist.logd.logpersistd", SELECT_LOGPERSIST_PROPERTY_SERVICE);
        SystemPropPoker.getInstance().poke();
        for (int i = 0; i < 3 && ((str2 = SystemProperties.get(ACTUAL_LOGPERSIST_PROPERTY)) == null || !str2.equals(SELECT_LOGPERSIST_PROPERTY_SERVICE)); i++) {
            try {
                Thread.sleep(100L);
            } catch (InterruptedException e) {
            }
        }
        updateLogpersistValues();
    }
}