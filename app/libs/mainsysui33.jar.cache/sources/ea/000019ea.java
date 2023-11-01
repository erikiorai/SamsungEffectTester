package com.android.systemui.keyguard.domain.backup;

import android.app.backup.SharedPreferencesBackupHelper;
import android.content.Context;
import com.android.systemui.settings.UserFileManagerImpl;
import java.io.File;
import kotlin.Unit;

/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/domain/backup/KeyguardQuickAffordanceBackupHelper.class */
public final class KeyguardQuickAffordanceBackupHelper extends SharedPreferencesBackupHelper {
    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public KeyguardQuickAffordanceBackupHelper(Context context, int i) {
        super(context, r12);
        UserFileManagerImpl.Companion companion = UserFileManagerImpl.Companion;
        String str = "quick_affordance_selections";
        if (!companion.isPrimaryUser(i)) {
            File secondaryUserFile = companion.secondaryUserFile(context, "quick_affordance_selections", "shared_prefs", i);
            companion.ensureParentDirExists(secondaryUserFile);
            Unit unit = Unit.INSTANCE;
            str = secondaryUserFile.toString();
        }
    }
}