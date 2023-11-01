package com.android.systemui.controls.controller;

import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import com.android.internal.annotations.VisibleForTesting;
import com.android.systemui.backup.BackupHelper;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import kotlin.Pair;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: mainsysui33.jar:com/android/systemui/controls/controller/AuxiliaryPersistenceWrapper.class */
public final class AuxiliaryPersistenceWrapper {
    public static final Companion Companion = new Companion(null);
    public List<StructureInfo> favorites;
    public ControlsFavoritePersistenceWrapper persistenceWrapper;

    /* loaded from: mainsysui33.jar:com/android/systemui/controls/controller/AuxiliaryPersistenceWrapper$Companion.class */
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/controls/controller/AuxiliaryPersistenceWrapper$DeletionJobService.class */
    public static final class DeletionJobService extends JobService {
        public static final Companion Companion = new Companion(null);
        public static final int DELETE_FILE_JOB_ID = 1000;
        public static final long WEEK_IN_MILLIS = TimeUnit.DAYS.toMillis(7);

        /* loaded from: mainsysui33.jar:com/android/systemui/controls/controller/AuxiliaryPersistenceWrapper$DeletionJobService$Companion.class */
        public static final class Companion {
            public Companion() {
            }

            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            @VisibleForTesting
            public static /* synthetic */ void getDELETE_FILE_JOB_ID$frameworks__base__packages__SystemUI__android_common__SystemUI_core$annotations() {
            }

            public final int getDELETE_FILE_JOB_ID$frameworks__base__packages__SystemUI__android_common__SystemUI_core() {
                return DeletionJobService.DELETE_FILE_JOB_ID;
            }

            public final JobInfo getJobForContext(Context context) {
                return new JobInfo.Builder(getDELETE_FILE_JOB_ID$frameworks__base__packages__SystemUI__android_common__SystemUI_core() + context.getUserId(), new ComponentName(context, DeletionJobService.class)).setMinimumLatency(DeletionJobService.WEEK_IN_MILLIS).setPersisted(true).build();
            }
        }

        @VisibleForTesting
        public final void attachContext(Context context) {
            attachBaseContext(context);
        }

        @Override // android.app.job.JobService
        public boolean onStartJob(JobParameters jobParameters) {
            synchronized (BackupHelper.Companion.getControlsDataLock()) {
                getBaseContext().deleteFile("aux_controls_favorites.xml");
            }
            return false;
        }

        @Override // android.app.job.JobService
        public boolean onStopJob(JobParameters jobParameters) {
            return true;
        }
    }

    @VisibleForTesting
    public AuxiliaryPersistenceWrapper(ControlsFavoritePersistenceWrapper controlsFavoritePersistenceWrapper) {
        this.persistenceWrapper = controlsFavoritePersistenceWrapper;
        this.favorites = CollectionsKt__CollectionsKt.emptyList();
        initialize();
    }

    public AuxiliaryPersistenceWrapper(File file, Executor executor) {
        this(new ControlsFavoritePersistenceWrapper(file, executor, null, 4, null));
    }

    public final void changeFile(File file) {
        this.persistenceWrapper.changeFileAndBackupManager(file, null);
        initialize();
    }

    public final List<StructureInfo> getCachedFavoritesAndRemoveFor(ComponentName componentName) {
        if (this.persistenceWrapper.getFileExists()) {
            List<StructureInfo> list = this.favorites;
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList();
            for (Object obj : list) {
                if (Intrinsics.areEqual(((StructureInfo) obj).getComponentName(), componentName)) {
                    arrayList.add(obj);
                } else {
                    arrayList2.add(obj);
                }
            }
            Pair pair = new Pair(arrayList, arrayList2);
            List<StructureInfo> list2 = (List) pair.component1();
            List<StructureInfo> list3 = (List) pair.component2();
            this.favorites = list3;
            if (!list3.isEmpty()) {
                this.persistenceWrapper.storeFavorites(list3);
            } else {
                this.persistenceWrapper.deleteFile();
            }
            return list2;
        }
        return CollectionsKt__CollectionsKt.emptyList();
    }

    public final List<StructureInfo> getFavorites() {
        return this.favorites;
    }

    public final void initialize() {
        this.favorites = this.persistenceWrapper.getFileExists() ? this.persistenceWrapper.readFavorites() : CollectionsKt__CollectionsKt.emptyList();
    }
}