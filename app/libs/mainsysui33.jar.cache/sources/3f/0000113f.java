package com.android.systemui.backup;

import android.app.job.JobScheduler;
import android.content.Context;
import android.os.Environment;
import com.android.systemui.controls.controller.AuxiliaryPersistenceWrapper;
import java.io.File;
import kotlin.Unit;
import kotlin.io.FilesKt__UtilsKt;
import kotlin.jvm.functions.Function0;

/* loaded from: mainsysui33.jar:com/android/systemui/backup/BackupHelperKt.class */
public final class BackupHelperKt {
    public static final Function0<Unit> getPPControlsFile(final Context context) {
        return new Function0<Unit>() { // from class: com.android.systemui.backup.BackupHelperKt$getPPControlsFile$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            public /* bridge */ /* synthetic */ Object invoke() {
                m1489invoke();
                return Unit.INSTANCE;
            }

            /* renamed from: invoke  reason: collision with other method in class */
            public final void m1489invoke() {
                File filesDir = context.getFilesDir();
                File buildPath = Environment.buildPath(filesDir, new String[]{"controls_favorites.xml"});
                if (buildPath.exists()) {
                    FilesKt__UtilsKt.copyTo$default(buildPath, Environment.buildPath(filesDir, new String[]{"aux_controls_favorites.xml"}), false, 0, 6, (Object) null);
                    JobScheduler jobScheduler = (JobScheduler) context.getSystemService(JobScheduler.class);
                    if (jobScheduler != null) {
                        jobScheduler.schedule(AuxiliaryPersistenceWrapper.DeletionJobService.Companion.getJobForContext(context));
                    }
                }
            }
        };
    }
}