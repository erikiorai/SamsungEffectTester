package com.android.systemui.backup;

import android.app.backup.BackupAgentHelper;
import android.app.backup.BackupDataInputStream;
import android.app.backup.BackupDataOutput;
import android.app.backup.FileBackupHelper;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.os.UserHandle;
import android.util.Log;
import com.android.systemui.keyguard.domain.backup.KeyguardQuickAffordanceBackupHelper;
import com.android.systemui.people.widget.PeopleBackupHelper;
import java.util.Arrays;
import java.util.Map;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.MapsKt__MapsJVMKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: mainsysui33.jar:com/android/systemui/backup/BackupHelper.class */
public class BackupHelper extends BackupAgentHelper {
    public static final Companion Companion = new Companion(null);
    public static final Object controlsDataLock = new Object();

    /* loaded from: mainsysui33.jar:com/android/systemui/backup/BackupHelper$Companion.class */
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final Object getControlsDataLock() {
            return BackupHelper.controlsDataLock;
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/backup/BackupHelper$NoOverwriteFileBackupHelper.class */
    public static final class NoOverwriteFileBackupHelper extends FileBackupHelper {
        public final Context context;
        public final Map<String, Function0<Unit>> fileNamesAndPostProcess;
        public final Object lock;

        /* JADX WARN: Illegal instructions before constructor call */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public NoOverwriteFileBackupHelper(Object obj, Context context, Map<String, ? extends Function0<Unit>> map) {
            super(context, (String[]) Arrays.copyOf(r0, r0.length));
            String[] strArr = (String[]) map.keySet().toArray(new String[0]);
            this.lock = obj;
            this.context = context;
            this.fileNamesAndPostProcess = map;
        }

        @Override // android.app.backup.FileBackupHelper, android.app.backup.BackupHelper
        public void performBackup(ParcelFileDescriptor parcelFileDescriptor, BackupDataOutput backupDataOutput, ParcelFileDescriptor parcelFileDescriptor2) {
            synchronized (this.lock) {
                super.performBackup(parcelFileDescriptor, backupDataOutput, parcelFileDescriptor2);
                Unit unit = Unit.INSTANCE;
            }
        }

        @Override // android.app.backup.FileBackupHelper, android.app.backup.BackupHelper
        public void restoreEntity(BackupDataInputStream backupDataInputStream) {
            if (Environment.buildPath(this.context.getFilesDir(), new String[]{backupDataInputStream.getKey()}).exists()) {
                String key = backupDataInputStream.getKey();
                Log.w("BackupHelper", "File " + key + " already exists. Skipping restore.");
                return;
            }
            synchronized (this.lock) {
                super.restoreEntity(backupDataInputStream);
                Function0<Unit> function0 = this.fileNamesAndPostProcess.get(backupDataInputStream.getKey());
                if (function0 != null) {
                    function0.invoke();
                    Unit unit = Unit.INSTANCE;
                }
            }
        }
    }

    public void onCreate(UserHandle userHandle, int i) {
        Function0 pPControlsFile;
        super.onCreate();
        pPControlsFile = BackupHelperKt.getPPControlsFile(this);
        addHelper("systemui.files_no_overwrite", new NoOverwriteFileBackupHelper(controlsDataLock, this, MapsKt__MapsJVMKt.mapOf(TuplesKt.to("controls_favorites.xml", pPControlsFile))));
        if (userHandle.isSystem()) {
            addHelper("systemui.people.shared_preferences", new PeopleBackupHelper(this, userHandle, (String[]) PeopleBackupHelper.getFilesToBackup().toArray(new String[0])));
            addHelper("systemui.keyguard.quickaffordance.shared_preferences", new KeyguardQuickAffordanceBackupHelper(this, userHandle.getIdentifier()));
        }
    }

    @Override // android.app.backup.BackupAgent
    public void onRestoreFinished() {
        super.onRestoreFinished();
        Intent intent = new Intent("com.android.systemui.backup.RESTORE_FINISHED");
        intent.setPackage(getPackageName());
        intent.putExtra("android.intent.extra.USER_ID", getUserId());
        intent.setFlags(1073741824);
        sendBroadcastAsUser(intent, UserHandle.SYSTEM, "com.android.systemui.permission.SELF");
    }
}