package com.android.systemui.qs;

import android.app.IActivityManager;
import android.app.IForegroundServiceObserver;
import android.app.job.IUserVisibleJobObserver;
import android.app.job.JobScheduler;
import android.app.job.UserVisibleJobSummary;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.UserInfo;
import android.graphics.drawable.Drawable;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.UserHandle;
import android.provider.DeviceConfig;
import android.text.format.DateUtils;
import android.util.ArrayMap;
import android.util.IndentingPrintWriter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.systemui.Dumpable;
import com.android.systemui.R$dimen;
import com.android.systemui.R$id;
import com.android.systemui.R$layout;
import com.android.systemui.R$string;
import com.android.systemui.animation.DialogCuj;
import com.android.systemui.animation.DialogLaunchAnimator;
import com.android.systemui.animation.Expandable;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.qs.FgsManagerController;
import com.android.systemui.qs.FgsManagerControllerImpl;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.shared.system.SysUiStatsLog;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.android.systemui.util.DeviceConfigProxy;
import com.android.systemui.util.time.SystemClock;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Executor;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.comparisons.ComparisonsKt__ComparisonsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.MutableStateFlow;
import kotlinx.coroutines.flow.StateFlow;
import kotlinx.coroutines.flow.StateFlowKt;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/FgsManagerControllerImpl.class */
public final class FgsManagerControllerImpl implements Dumpable, FgsManagerController {
    public static final Companion Companion = new Companion(null);
    public final MutableStateFlow<Boolean> _isAvailable;
    public final MutableStateFlow<Boolean> _showFooterDot;
    public final IActivityManager activityManager;
    public final AppListAdapter appListAdapter;
    public final Executor backgroundExecutor;
    public final BroadcastDispatcher broadcastDispatcher;
    public final Context context;
    public Set<Integer> currentProfileIds;
    public final DeviceConfigProxy deviceConfigProxy;
    public SystemUIDialog dialog;
    public final DialogLaunchAnimator dialogLaunchAnimator;
    public final DumpManager dumpManager;
    public final ForegroundServiceObserver foregroundServiceObserver;
    public boolean initialized;
    public final StateFlow<Boolean> isAvailable;
    public final JobScheduler jobScheduler;
    public int lastNumberOfVisiblePackages;
    public final Object lock;
    public final Executor mainExecutor;
    public boolean newChangesSinceDialogWasDismissed;
    public final Set<FgsManagerController.OnDialogDismissedListener> onDialogDismissedListeners;
    public final Set<FgsManagerController.OnNumberOfPackagesChangedListener> onNumberOfPackagesChangedListeners;
    public final PackageManager packageManager;
    public ArrayMap<UserPackage, RunningApp> runningApps;
    public final Map<UserPackage, StartTimeAndIdentifiers> runningTaskIdentifiers;
    public final StateFlow<Boolean> showFooterDot;
    public boolean showStopBtnForUserAllowlistedApps;
    public boolean showUserVisibleJobs;
    public final SystemClock systemClock;
    public final UserTracker userTracker;
    public final FgsManagerControllerImpl$userTrackerCallback$1 userTrackerCallback;
    public final UserVisibleJobObserver userVisibleJobObserver;

    /* loaded from: mainsysui33.jar:com/android/systemui/qs/FgsManagerControllerImpl$AppItemViewHolder.class */
    public static final class AppItemViewHolder extends RecyclerView.ViewHolder {
        public final TextView appLabelView;
        public final TextView durationView;
        public final ImageView iconView;
        public final Button stopButton;

        public AppItemViewHolder(View view) {
            super(view);
            this.appLabelView = (TextView) view.requireViewById(R$id.fgs_manager_app_item_label);
            this.durationView = (TextView) view.requireViewById(R$id.fgs_manager_app_item_duration);
            this.iconView = (ImageView) view.requireViewById(R$id.fgs_manager_app_item_icon);
            this.stopButton = (Button) view.requireViewById(R$id.fgs_manager_app_item_stop_button);
        }

        public final TextView getAppLabelView() {
            return this.appLabelView;
        }

        public final TextView getDurationView() {
            return this.durationView;
        }

        public final ImageView getIconView() {
            return this.iconView;
        }

        public final Button getStopButton() {
            return this.stopButton;
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/qs/FgsManagerControllerImpl$AppListAdapter.class */
    public final class AppListAdapter extends RecyclerView.Adapter<AppItemViewHolder> {
        public final Object lock = new Object();
        public List<RunningApp> data = CollectionsKt__CollectionsKt.emptyList();

        public AppListAdapter() {
            FgsManagerControllerImpl.this = r5;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemCount() {
            return this.data.size();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public void onBindViewHolder(final AppItemViewHolder appItemViewHolder, int i) {
            final Ref.ObjectRef objectRef = new Ref.ObjectRef();
            synchronized (this.lock) {
                objectRef.element = this.data.get(i);
                Unit unit = Unit.INSTANCE;
            }
            final FgsManagerControllerImpl fgsManagerControllerImpl = FgsManagerControllerImpl.this;
            appItemViewHolder.getIconView().setImageDrawable(((RunningApp) objectRef.element).getIcon());
            appItemViewHolder.getAppLabelView().setText(((RunningApp) objectRef.element).getAppLabel());
            appItemViewHolder.getDurationView().setText(DateUtils.formatDuration(Math.max(fgsManagerControllerImpl.systemClock.elapsedRealtime() - ((RunningApp) objectRef.element).getTimeStarted(), 60000L), 20));
            appItemViewHolder.getStopButton().setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.qs.FgsManagerControllerImpl$AppListAdapter$onBindViewHolder$2$1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    FgsManagerControllerImpl.AppItemViewHolder.this.getStopButton().setText(R$string.fgs_manager_app_item_stop_button_stopped_label);
                    FgsManagerControllerImpl.access$stopPackage(fgsManagerControllerImpl, ((FgsManagerControllerImpl.RunningApp) objectRef.element).getUserId(), ((FgsManagerControllerImpl.RunningApp) objectRef.element).getPackageName(), ((FgsManagerControllerImpl.RunningApp) objectRef.element).getTimeStarted());
                }
            });
            if (((RunningApp) objectRef.element).getUiControl() == UIControl.HIDE_BUTTON) {
                appItemViewHolder.getStopButton().setVisibility(4);
            }
            if (((RunningApp) objectRef.element).getStopped()) {
                appItemViewHolder.getStopButton().setEnabled(false);
                appItemViewHolder.getStopButton().setText(R$string.fgs_manager_app_item_stop_button_stopped_label);
                appItemViewHolder.getDurationView().setVisibility(8);
                return;
            }
            appItemViewHolder.getStopButton().setEnabled(true);
            appItemViewHolder.getStopButton().setText(R$string.fgs_manager_app_item_stop_button_label);
            appItemViewHolder.getDurationView().setVisibility(0);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public AppItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            return new AppItemViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R$layout.fgs_manager_app_item, viewGroup, false));
        }

        public final void setData(final List<RunningApp> list) {
            final Ref.ObjectRef objectRef = new Ref.ObjectRef();
            objectRef.element = this.data;
            this.data = list;
            DiffUtil.calculateDiff(new DiffUtil.Callback() { // from class: com.android.systemui.qs.FgsManagerControllerImpl$AppListAdapter$setData$1
                @Override // androidx.recyclerview.widget.DiffUtil.Callback
                public boolean areContentsTheSame(int i, int i2) {
                    return ((FgsManagerControllerImpl.RunningApp) ((List) objectRef.element).get(i)).getStopped() == list.get(i2).getStopped();
                }

                @Override // androidx.recyclerview.widget.DiffUtil.Callback
                public boolean areItemsTheSame(int i, int i2) {
                    return Intrinsics.areEqual(((List) objectRef.element).get(i), list.get(i2));
                }

                @Override // androidx.recyclerview.widget.DiffUtil.Callback
                public int getNewListSize() {
                    return list.size();
                }

                @Override // androidx.recyclerview.widget.DiffUtil.Callback
                public int getOldListSize() {
                    return ((List) objectRef.element).size();
                }
            }).dispatchUpdatesTo(this);
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/qs/FgsManagerControllerImpl$Companion.class */
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/qs/FgsManagerControllerImpl$ForegroundServiceObserver.class */
    public final class ForegroundServiceObserver extends IForegroundServiceObserver.Stub {
        public ForegroundServiceObserver() {
            FgsManagerControllerImpl.this = r4;
        }

        /* JADX WARN: Removed duplicated region for block: B:54:0x008e A[Catch: all -> 0x00ad, TRY_ENTER, TryCatch #0 {, blocks: (B:37:0x0012, B:39:0x0025, B:42:0x003f, B:44:0x0058, B:56:0x009d, B:45:0x0062, B:48:0x0078, B:54:0x008e), top: B:64:0x0012 }] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public void onForegroundStateChanged(IBinder iBinder, String str, int i, boolean z) {
            Object obj = FgsManagerControllerImpl.this.lock;
            FgsManagerControllerImpl fgsManagerControllerImpl = FgsManagerControllerImpl.this;
            synchronized (obj) {
                UserPackage userPackage = new UserPackage(i, str);
                if (z) {
                    Map map = fgsManagerControllerImpl.runningTaskIdentifiers;
                    Object obj2 = map.get(userPackage);
                    Object obj3 = obj2;
                    if (obj2 == null) {
                        obj3 = new StartTimeAndIdentifiers(fgsManagerControllerImpl.systemClock);
                        map.put(userPackage, obj3);
                    }
                    ((StartTimeAndIdentifiers) obj3).addFgsToken(iBinder);
                } else {
                    StartTimeAndIdentifiers startTimeAndIdentifiers = (StartTimeAndIdentifiers) fgsManagerControllerImpl.runningTaskIdentifiers.get(userPackage);
                    boolean z2 = true;
                    if (startTimeAndIdentifiers != null) {
                        startTimeAndIdentifiers.removeFgsToken(iBinder);
                        if (startTimeAndIdentifiers.isEmpty()) {
                            if (z2) {
                                fgsManagerControllerImpl.runningTaskIdentifiers.remove(userPackage);
                            }
                        }
                    }
                    z2 = false;
                    if (z2) {
                    }
                }
                fgsManagerControllerImpl.updateNumberOfVisibleRunningPackagesLocked();
                fgsManagerControllerImpl.updateAppItemsLocked();
                Unit unit = Unit.INSTANCE;
            }
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/qs/FgsManagerControllerImpl$RunningApp.class */
    public static final class RunningApp {
        public CharSequence appLabel;
        public Drawable icon;
        public final String packageName;
        public boolean stopped;
        public final long timeStarted;
        public final UIControl uiControl;
        public final int userId;

        public RunningApp(int i, String str, long j, UIControl uIControl) {
            this.userId = i;
            this.packageName = str;
            this.timeStarted = j;
            this.uiControl = uIControl;
            this.appLabel = "";
        }

        public RunningApp(int i, String str, long j, UIControl uIControl, CharSequence charSequence, Drawable drawable) {
            this(i, str, j, uIControl);
            this.appLabel = charSequence;
            this.icon = drawable;
        }

        public static /* synthetic */ RunningApp copy$default(RunningApp runningApp, int i, String str, long j, UIControl uIControl, int i2, Object obj) {
            if ((i2 & 1) != 0) {
                i = runningApp.userId;
            }
            if ((i2 & 2) != 0) {
                str = runningApp.packageName;
            }
            if ((i2 & 4) != 0) {
                j = runningApp.timeStarted;
            }
            if ((i2 & 8) != 0) {
                uIControl = runningApp.uiControl;
            }
            return runningApp.copy(i, str, j, uIControl);
        }

        public final RunningApp copy(int i, String str, long j, UIControl uIControl) {
            return new RunningApp(i, str, j, uIControl);
        }

        public final void dump(PrintWriter printWriter, SystemClock systemClock) {
            printWriter.println("RunningApp: [");
            boolean z = printWriter instanceof IndentingPrintWriter;
            if (z) {
                ((IndentingPrintWriter) printWriter).increaseIndent();
            }
            int i = this.userId;
            printWriter.println("userId=" + i);
            String str = this.packageName;
            printWriter.println("packageName=" + str);
            long j = this.timeStarted;
            long elapsedRealtime = systemClock.elapsedRealtime();
            long j2 = this.timeStarted;
            printWriter.println("timeStarted=" + j + " (time since start = " + (elapsedRealtime - j2) + "ms)");
            UIControl uIControl = this.uiControl;
            StringBuilder sb = new StringBuilder();
            sb.append("uiControl=");
            sb.append(uIControl);
            printWriter.println(sb.toString());
            CharSequence charSequence = this.appLabel;
            printWriter.println("appLabel=" + ((Object) charSequence));
            Drawable drawable = this.icon;
            printWriter.println("icon=" + drawable);
            boolean z2 = this.stopped;
            printWriter.println("stopped=" + z2);
            if (z) {
                ((IndentingPrintWriter) printWriter).decreaseIndent();
            }
            printWriter.println("]");
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj instanceof RunningApp) {
                RunningApp runningApp = (RunningApp) obj;
                return this.userId == runningApp.userId && Intrinsics.areEqual(this.packageName, runningApp.packageName) && this.timeStarted == runningApp.timeStarted && this.uiControl == runningApp.uiControl;
            }
            return false;
        }

        public final CharSequence getAppLabel() {
            return this.appLabel;
        }

        public final Drawable getIcon() {
            return this.icon;
        }

        public final String getPackageName() {
            return this.packageName;
        }

        public final boolean getStopped() {
            return this.stopped;
        }

        public final long getTimeStarted() {
            return this.timeStarted;
        }

        public final UIControl getUiControl() {
            return this.uiControl;
        }

        public final int getUserId() {
            return this.userId;
        }

        public int hashCode() {
            return (((((Integer.hashCode(this.userId) * 31) + this.packageName.hashCode()) * 31) + Long.hashCode(this.timeStarted)) * 31) + this.uiControl.hashCode();
        }

        public final void setAppLabel(CharSequence charSequence) {
            this.appLabel = charSequence;
        }

        public final void setIcon(Drawable drawable) {
            this.icon = drawable;
        }

        public final void setStopped(boolean z) {
            this.stopped = z;
        }

        public String toString() {
            int i = this.userId;
            String str = this.packageName;
            long j = this.timeStarted;
            UIControl uIControl = this.uiControl;
            return "RunningApp(userId=" + i + ", packageName=" + str + ", timeStarted=" + j + ", uiControl=" + uIControl + ")";
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/qs/FgsManagerControllerImpl$StartTimeAndIdentifiers.class */
    public static final class StartTimeAndIdentifiers {
        public final Set<IBinder> fgsTokens = new LinkedHashSet();
        public final Set<UserVisibleJobSummary> jobSummaries = new LinkedHashSet();
        public final long startTime;
        public final SystemClock systemClock;

        public StartTimeAndIdentifiers(SystemClock systemClock) {
            this.systemClock = systemClock;
            this.startTime = systemClock.elapsedRealtime();
        }

        public final void addFgsToken(IBinder iBinder) {
            this.fgsTokens.add(iBinder);
        }

        public final void addJobSummary(UserVisibleJobSummary userVisibleJobSummary) {
            this.jobSummaries.add(userVisibleJobSummary);
        }

        public final void clearJobSummaries() {
            this.jobSummaries.clear();
        }

        public final void dump(PrintWriter printWriter) {
            printWriter.println("StartTimeAndIdentifiers: [");
            boolean z = printWriter instanceof IndentingPrintWriter;
            if (z) {
                ((IndentingPrintWriter) printWriter).increaseIndent();
            }
            long j = this.startTime;
            long elapsedRealtime = this.systemClock.elapsedRealtime();
            long j2 = this.startTime;
            printWriter.println("startTime=" + j + " (time running = " + (elapsedRealtime - j2) + "ms)");
            printWriter.println("fgs tokens: [");
            if (z) {
                ((IndentingPrintWriter) printWriter).increaseIndent();
            }
            for (IBinder iBinder : this.fgsTokens) {
                printWriter.println(String.valueOf(iBinder));
            }
            if (z) {
                ((IndentingPrintWriter) printWriter).decreaseIndent();
            }
            printWriter.println("job summaries: [");
            if (z) {
                ((IndentingPrintWriter) printWriter).increaseIndent();
            }
            for (UserVisibleJobSummary userVisibleJobSummary : this.jobSummaries) {
                printWriter.println(String.valueOf(userVisibleJobSummary));
            }
            if (z) {
                ((IndentingPrintWriter) printWriter).decreaseIndent();
            }
            printWriter.println("]");
            if (z) {
                ((IndentingPrintWriter) printWriter).decreaseIndent();
            }
            printWriter.println("]");
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof StartTimeAndIdentifiers) && Intrinsics.areEqual(this.systemClock, ((StartTimeAndIdentifiers) obj).systemClock);
        }

        public final long getStartTime() {
            return this.startTime;
        }

        public final boolean hasFgs() {
            return !this.fgsTokens.isEmpty();
        }

        public final boolean hasRunningJobs() {
            return !this.jobSummaries.isEmpty();
        }

        public int hashCode() {
            return this.systemClock.hashCode();
        }

        public final boolean isEmpty() {
            return this.fgsTokens.isEmpty() && this.jobSummaries.isEmpty();
        }

        public final void removeFgsToken(IBinder iBinder) {
            this.fgsTokens.remove(iBinder);
        }

        public final void removeJobSummary(UserVisibleJobSummary userVisibleJobSummary) {
            this.jobSummaries.remove(userVisibleJobSummary);
        }

        public String toString() {
            SystemClock systemClock = this.systemClock;
            return "StartTimeAndIdentifiers(systemClock=" + systemClock + ")";
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/qs/FgsManagerControllerImpl$UIControl.class */
    public enum UIControl {
        NORMAL,
        HIDE_BUTTON,
        HIDE_ENTRY
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/qs/FgsManagerControllerImpl$UserPackage.class */
    public final class UserPackage {
        public final String packageName;
        public boolean uiControlInitialized;
        public final Lazy uid$delegate;
        public final int userId;
        public int backgroundRestrictionExemptionReason = -1;
        public UIControl uiControl = UIControl.NORMAL;

        public UserPackage(int i, String str) {
            FgsManagerControllerImpl.this = r7;
            this.userId = i;
            this.packageName = str;
            this.uid$delegate = LazyKt__LazyJVMKt.lazy(new Function0<Integer>() { // from class: com.android.systemui.qs.FgsManagerControllerImpl$UserPackage$uid$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                /* JADX DEBUG: Method merged with bridge method */
                /* renamed from: invoke */
                public final Integer m3717invoke() {
                    return Integer.valueOf(FgsManagerControllerImpl.access$getPackageManager$p(FgsManagerControllerImpl.this).getPackageUidAsUser(this.getPackageName(), this.getUserId()));
                }
            });
        }

        public final void dump(PrintWriter printWriter) {
            printWriter.println("UserPackage: [");
            boolean z = printWriter instanceof IndentingPrintWriter;
            if (z) {
                ((IndentingPrintWriter) printWriter).increaseIndent();
            }
            int i = this.userId;
            printWriter.println("userId=" + i);
            String str = this.packageName;
            printWriter.println("packageName=" + str);
            UIControl uiControl = getUiControl();
            int i2 = this.backgroundRestrictionExemptionReason;
            printWriter.println("uiControl=" + uiControl + " (reason=" + i2 + ")");
            if (z) {
                ((IndentingPrintWriter) printWriter).decreaseIndent();
            }
            printWriter.println("]");
        }

        public boolean equals(Object obj) {
            if (obj instanceof UserPackage) {
                UserPackage userPackage = (UserPackage) obj;
                boolean z = false;
                if (Intrinsics.areEqual(userPackage.packageName, this.packageName)) {
                    z = false;
                    if (userPackage.userId == this.userId) {
                        z = true;
                    }
                }
                return z;
            }
            return false;
        }

        public final String getPackageName() {
            return this.packageName;
        }

        public final UIControl getUiControl() {
            if (!this.uiControlInitialized) {
                updateUiControl();
            }
            return this.uiControl;
        }

        public final int getUid() {
            return ((Number) this.uid$delegate.getValue()).intValue();
        }

        public final int getUserId() {
            return this.userId;
        }

        public int hashCode() {
            return Objects.hash(Integer.valueOf(this.userId), this.packageName);
        }

        public final void updateUiControl() {
            UIControl uIControl;
            int backgroundRestrictionExemptionReason = FgsManagerControllerImpl.this.activityManager.getBackgroundRestrictionExemptionReason(getUid());
            this.backgroundRestrictionExemptionReason = backgroundRestrictionExemptionReason;
            if (backgroundRestrictionExemptionReason != 10 && backgroundRestrictionExemptionReason != 11) {
                if (backgroundRestrictionExemptionReason == 51 || backgroundRestrictionExemptionReason == 63) {
                    uIControl = UIControl.HIDE_ENTRY;
                } else if (backgroundRestrictionExemptionReason == 65) {
                    uIControl = FgsManagerControllerImpl.this.showStopBtnForUserAllowlistedApps ? UIControl.NORMAL : UIControl.HIDE_BUTTON;
                } else if (backgroundRestrictionExemptionReason != 300 && backgroundRestrictionExemptionReason != 318 && backgroundRestrictionExemptionReason != 320 && backgroundRestrictionExemptionReason != 55 && backgroundRestrictionExemptionReason != 56) {
                    switch (backgroundRestrictionExemptionReason) {
                        case 322:
                        case 323:
                        case 324:
                            break;
                        default:
                            uIControl = UIControl.NORMAL;
                            break;
                    }
                }
                this.uiControl = uIControl;
                this.uiControlInitialized = true;
            }
            uIControl = UIControl.HIDE_BUTTON;
            this.uiControl = uIControl;
            this.uiControlInitialized = true;
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/qs/FgsManagerControllerImpl$UserVisibleJobObserver.class */
    public final class UserVisibleJobObserver extends IUserVisibleJobObserver.Stub {
        public UserVisibleJobObserver() {
            FgsManagerControllerImpl.this = r4;
        }

        /* JADX WARN: Removed duplicated region for block: B:54:0x009d A[Catch: all -> 0x00bb, TRY_ENTER, TryCatch #0 {, blocks: (B:37:0x0010, B:39:0x0028, B:42:0x0043, B:44:0x0060, B:56:0x00ac, B:45:0x006a, B:48:0x0083, B:54:0x009d), top: B:64:0x0010 }] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public void onUserVisibleJobStateChanged(UserVisibleJobSummary userVisibleJobSummary, boolean z) {
            Object obj = FgsManagerControllerImpl.this.lock;
            FgsManagerControllerImpl fgsManagerControllerImpl = FgsManagerControllerImpl.this;
            synchronized (obj) {
                UserPackage userPackage = new UserPackage(userVisibleJobSummary.getSourceUserId(), userVisibleJobSummary.getSourcePackageName());
                if (z) {
                    Map map = fgsManagerControllerImpl.runningTaskIdentifiers;
                    Object obj2 = map.get(userPackage);
                    Object obj3 = obj2;
                    if (obj2 == null) {
                        obj3 = new StartTimeAndIdentifiers(fgsManagerControllerImpl.systemClock);
                        map.put(userPackage, obj3);
                    }
                    ((StartTimeAndIdentifiers) obj3).addJobSummary(userVisibleJobSummary);
                } else {
                    StartTimeAndIdentifiers startTimeAndIdentifiers = (StartTimeAndIdentifiers) fgsManagerControllerImpl.runningTaskIdentifiers.get(userPackage);
                    boolean z2 = true;
                    if (startTimeAndIdentifiers != null) {
                        startTimeAndIdentifiers.removeJobSummary(userVisibleJobSummary);
                        if (startTimeAndIdentifiers.isEmpty()) {
                            if (z2) {
                                fgsManagerControllerImpl.runningTaskIdentifiers.remove(userPackage);
                            }
                        }
                    }
                    z2 = false;
                    if (z2) {
                    }
                }
                fgsManagerControllerImpl.updateNumberOfVisibleRunningPackagesLocked();
                fgsManagerControllerImpl.updateAppItemsLocked();
                Unit unit = Unit.INSTANCE;
            }
        }
    }

    /* JADX WARN: Type inference failed for: r1v23, types: [com.android.systemui.qs.FgsManagerControllerImpl$userTrackerCallback$1] */
    public FgsManagerControllerImpl(Context context, Executor executor, Executor executor2, SystemClock systemClock, IActivityManager iActivityManager, JobScheduler jobScheduler, PackageManager packageManager, UserTracker userTracker, DeviceConfigProxy deviceConfigProxy, DialogLaunchAnimator dialogLaunchAnimator, BroadcastDispatcher broadcastDispatcher, DumpManager dumpManager) {
        this.context = context;
        this.mainExecutor = executor;
        this.backgroundExecutor = executor2;
        this.systemClock = systemClock;
        this.activityManager = iActivityManager;
        this.jobScheduler = jobScheduler;
        this.packageManager = packageManager;
        this.userTracker = userTracker;
        this.deviceConfigProxy = deviceConfigProxy;
        this.dialogLaunchAnimator = dialogLaunchAnimator;
        this.broadcastDispatcher = broadcastDispatcher;
        this.dumpManager = dumpManager;
        Boolean bool = Boolean.FALSE;
        MutableStateFlow<Boolean> MutableStateFlow = StateFlowKt.MutableStateFlow(bool);
        this._isAvailable = MutableStateFlow;
        this.isAvailable = FlowKt.asStateFlow(MutableStateFlow);
        MutableStateFlow<Boolean> MutableStateFlow2 = StateFlowKt.MutableStateFlow(bool);
        this._showFooterDot = MutableStateFlow2;
        this.showFooterDot = FlowKt.asStateFlow(MutableStateFlow2);
        this.lock = new Object();
        this.currentProfileIds = new LinkedHashSet();
        this.runningTaskIdentifiers = new LinkedHashMap();
        this.appListAdapter = new AppListAdapter();
        this.runningApps = new ArrayMap<>();
        this.userTrackerCallback = new UserTracker.Callback() { // from class: com.android.systemui.qs.FgsManagerControllerImpl$userTrackerCallback$1
            public void onProfilesChanged(List<? extends UserInfo> list) {
                Object access$getLock$p = FgsManagerControllerImpl.access$getLock$p(FgsManagerControllerImpl.this);
                FgsManagerControllerImpl fgsManagerControllerImpl = FgsManagerControllerImpl.this;
                synchronized (access$getLock$p) {
                    FgsManagerControllerImpl.access$getCurrentProfileIds$p(fgsManagerControllerImpl).clear();
                    Set access$getCurrentProfileIds$p = FgsManagerControllerImpl.access$getCurrentProfileIds$p(fgsManagerControllerImpl);
                    List<? extends UserInfo> list2 = list;
                    ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list2, 10));
                    for (UserInfo userInfo : list2) {
                        arrayList.add(Integer.valueOf(userInfo.id));
                    }
                    access$getCurrentProfileIds$p.addAll(arrayList);
                    FgsManagerControllerImpl.access$setLastNumberOfVisiblePackages$p(fgsManagerControllerImpl, 0);
                    FgsManagerControllerImpl.access$updateNumberOfVisibleRunningPackagesLocked(fgsManagerControllerImpl);
                    Unit unit = Unit.INSTANCE;
                }
            }

            public void onUserChanged(int i, Context context2) {
            }
        };
        this.foregroundServiceObserver = new ForegroundServiceObserver();
        this.userVisibleJobObserver = new UserVisibleJobObserver();
        this.onNumberOfPackagesChangedListeners = new LinkedHashSet();
        this.onDialogDismissedListeners = new LinkedHashSet();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.FgsManagerControllerImpl$updateAppItemsLocked$3.run():void] */
    public static final /* synthetic */ AppListAdapter access$getAppListAdapter$p(FgsManagerControllerImpl fgsManagerControllerImpl) {
        return fgsManagerControllerImpl.appListAdapter;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.FgsManagerControllerImpl$userTrackerCallback$1.onProfilesChanged(java.util.List<? extends android.content.pm.UserInfo>):void] */
    public static final /* synthetic */ Set access$getCurrentProfileIds$p(FgsManagerControllerImpl fgsManagerControllerImpl) {
        return fgsManagerControllerImpl.currentProfileIds;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.FgsManagerControllerImpl$showDialog$1$3.run():void] */
    public static final /* synthetic */ DialogLaunchAnimator access$getDialogLaunchAnimator$p(FgsManagerControllerImpl fgsManagerControllerImpl) {
        return fgsManagerControllerImpl.dialogLaunchAnimator;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.FgsManagerControllerImpl$showDialog$1$2.onDismiss(android.content.DialogInterface):void, com.android.systemui.qs.FgsManagerControllerImpl$showDialog$1$4.run():void, com.android.systemui.qs.FgsManagerControllerImpl$userTrackerCallback$1.onProfilesChanged(java.util.List<? extends android.content.pm.UserInfo>):void] */
    public static final /* synthetic */ Object access$getLock$p(FgsManagerControllerImpl fgsManagerControllerImpl) {
        return fgsManagerControllerImpl.lock;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.FgsManagerControllerImpl$showDialog$1$2.onDismiss(android.content.DialogInterface):void] */
    public static final /* synthetic */ Executor access$getMainExecutor$p(FgsManagerControllerImpl fgsManagerControllerImpl) {
        return fgsManagerControllerImpl.mainExecutor;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.FgsManagerControllerImpl$showDialog$1$2.onDismiss(android.content.DialogInterface):void] */
    public static final /* synthetic */ Set access$getOnDialogDismissedListeners$p(FgsManagerControllerImpl fgsManagerControllerImpl) {
        return fgsManagerControllerImpl.onDialogDismissedListeners;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.FgsManagerControllerImpl$UserPackage$uid$2.invoke():java.lang.Integer, com.android.systemui.qs.FgsManagerControllerImpl$logEvent$1.run():void] */
    public static final /* synthetic */ PackageManager access$getPackageManager$p(FgsManagerControllerImpl fgsManagerControllerImpl) {
        return fgsManagerControllerImpl.packageManager;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.FgsManagerControllerImpl$updateAppItemsLocked$3.run():void] */
    public static final /* synthetic */ ArrayMap access$getRunningApps$p(FgsManagerControllerImpl fgsManagerControllerImpl) {
        return fgsManagerControllerImpl.runningApps;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.FgsManagerControllerImpl$init$1$2.onPropertiesChanged(android.provider.DeviceConfig$Properties):void] */
    public static final /* synthetic */ boolean access$getShowStopBtnForUserAllowlistedApps$p(FgsManagerControllerImpl fgsManagerControllerImpl) {
        return fgsManagerControllerImpl.showStopBtnForUserAllowlistedApps;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.FgsManagerControllerImpl$init$1$2.onPropertiesChanged(android.provider.DeviceConfig$Properties):void] */
    public static final /* synthetic */ boolean access$getShowUserVisibleJobs$p(FgsManagerControllerImpl fgsManagerControllerImpl) {
        return fgsManagerControllerImpl.showUserVisibleJobs;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.FgsManagerControllerImpl$init$1$2.onPropertiesChanged(android.provider.DeviceConfig$Properties):void] */
    public static final /* synthetic */ void access$onShowUserVisibleJobsFlagChanged(FgsManagerControllerImpl fgsManagerControllerImpl) {
        fgsManagerControllerImpl.onShowUserVisibleJobsFlagChanged();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.FgsManagerControllerImpl$showDialog$1$2.onDismiss(android.content.DialogInterface):void] */
    public static final /* synthetic */ void access$setDialog$p(FgsManagerControllerImpl fgsManagerControllerImpl, SystemUIDialog systemUIDialog) {
        fgsManagerControllerImpl.dialog = systemUIDialog;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.FgsManagerControllerImpl$userTrackerCallback$1.onProfilesChanged(java.util.List<? extends android.content.pm.UserInfo>):void] */
    public static final /* synthetic */ void access$setLastNumberOfVisiblePackages$p(FgsManagerControllerImpl fgsManagerControllerImpl, int i) {
        fgsManagerControllerImpl.lastNumberOfVisiblePackages = i;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.FgsManagerControllerImpl$showDialog$1$2.onDismiss(android.content.DialogInterface):void] */
    public static final /* synthetic */ void access$setNewChangesSinceDialogWasDismissed$p(FgsManagerControllerImpl fgsManagerControllerImpl, boolean z) {
        fgsManagerControllerImpl.newChangesSinceDialogWasDismissed = z;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.FgsManagerControllerImpl$init$1$2.onPropertiesChanged(android.provider.DeviceConfig$Properties):void] */
    public static final /* synthetic */ void access$setShowStopBtnForUserAllowlistedApps$p(FgsManagerControllerImpl fgsManagerControllerImpl, boolean z) {
        fgsManagerControllerImpl.showStopBtnForUserAllowlistedApps = z;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.FgsManagerControllerImpl$init$1$2.onPropertiesChanged(android.provider.DeviceConfig$Properties):void] */
    public static final /* synthetic */ void access$setShowUserVisibleJobs$p(FgsManagerControllerImpl fgsManagerControllerImpl, boolean z) {
        fgsManagerControllerImpl.showUserVisibleJobs = z;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.FgsManagerControllerImpl$AppListAdapter$onBindViewHolder$2$1.onClick(android.view.View):void] */
    public static final /* synthetic */ void access$stopPackage(FgsManagerControllerImpl fgsManagerControllerImpl, int i, String str, long j) {
        fgsManagerControllerImpl.stopPackage(i, str, j);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.FgsManagerControllerImpl$showDialog$1$2.onDismiss(android.content.DialogInterface):void, com.android.systemui.qs.FgsManagerControllerImpl$showDialog$1$4.run():void] */
    public static final /* synthetic */ void access$updateAppItemsLocked(FgsManagerControllerImpl fgsManagerControllerImpl) {
        fgsManagerControllerImpl.updateAppItemsLocked();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.FgsManagerControllerImpl$userTrackerCallback$1.onProfilesChanged(java.util.List<? extends android.content.pm.UserInfo>):void] */
    public static final /* synthetic */ void access$updateNumberOfVisibleRunningPackagesLocked(FgsManagerControllerImpl fgsManagerControllerImpl) {
        fgsManagerControllerImpl.updateNumberOfVisibleRunningPackagesLocked();
    }

    @Override // com.android.systemui.qs.FgsManagerController
    public void addOnDialogDismissedListener(FgsManagerController.OnDialogDismissedListener onDialogDismissedListener) {
        synchronized (this.lock) {
            this.onDialogDismissedListeners.add(onDialogDismissedListener);
        }
    }

    @Override // com.android.systemui.qs.FgsManagerController
    public void addOnNumberOfPackagesChangedListener(FgsManagerController.OnNumberOfPackagesChangedListener onNumberOfPackagesChangedListener) {
        synchronized (this.lock) {
            this.onNumberOfPackagesChangedListeners.add(onNumberOfPackagesChangedListener);
        }
    }

    @Override // com.android.systemui.Dumpable
    public void dump(PrintWriter printWriter, String[] strArr) {
        PrintWriter indentingPrintWriter = new IndentingPrintWriter(printWriter);
        synchronized (this.lock) {
            Set<Integer> set = this.currentProfileIds;
            indentingPrintWriter.println("current user profiles = " + set);
            boolean newChangesSinceDialogWasDismissed = getNewChangesSinceDialogWasDismissed();
            indentingPrintWriter.println("newChangesSinceDialogWasShown=" + newChangesSinceDialogWasDismissed);
            indentingPrintWriter.println("Running task identifiers: [");
            indentingPrintWriter.increaseIndent();
            for (Map.Entry<UserPackage, StartTimeAndIdentifiers> entry : this.runningTaskIdentifiers.entrySet()) {
                UserPackage key = entry.getKey();
                indentingPrintWriter.println("{");
                indentingPrintWriter.increaseIndent();
                key.dump(indentingPrintWriter);
                entry.getValue().dump(indentingPrintWriter);
                indentingPrintWriter.decreaseIndent();
                indentingPrintWriter.println("}");
            }
            indentingPrintWriter.decreaseIndent();
            indentingPrintWriter.println("]");
            indentingPrintWriter.println("Loaded package UI info: [");
            indentingPrintWriter.increaseIndent();
            for (Map.Entry<UserPackage, RunningApp> entry2 : this.runningApps.entrySet()) {
                UserPackage key2 = entry2.getKey();
                indentingPrintWriter.println("{");
                indentingPrintWriter.increaseIndent();
                key2.dump(indentingPrintWriter);
                entry2.getValue().dump(indentingPrintWriter, this.systemClock);
                indentingPrintWriter.decreaseIndent();
                indentingPrintWriter.println("}");
            }
            indentingPrintWriter.decreaseIndent();
            indentingPrintWriter.println("]");
            Unit unit = Unit.INSTANCE;
        }
    }

    @Override // com.android.systemui.qs.FgsManagerController
    public boolean getNewChangesSinceDialogWasDismissed() {
        return this.newChangesSinceDialogWasDismissed;
    }

    @Override // com.android.systemui.qs.FgsManagerController
    public int getNumRunningPackages() {
        int numVisiblePackagesLocked;
        synchronized (this.lock) {
            numVisiblePackagesLocked = getNumVisiblePackagesLocked();
        }
        return numVisiblePackagesLocked;
    }

    public final int getNumVisibleButtonsLocked() {
        Set<UserPackage> keySet = this.runningTaskIdentifiers.keySet();
        int i = 0;
        if (!(keySet instanceof Collection) || !keySet.isEmpty()) {
            i = 0;
            for (UserPackage userPackage : keySet) {
                if (userPackage.getUiControl() != UIControl.HIDE_BUTTON && this.currentProfileIds.contains(Integer.valueOf(userPackage.getUserId()))) {
                    int i2 = i + 1;
                    i = i2;
                    if (i2 < 0) {
                        CollectionsKt__CollectionsKt.throwCountOverflow();
                        i = i2;
                    }
                }
            }
        }
        return i;
    }

    public final int getNumVisiblePackagesLocked() {
        Set<UserPackage> keySet = this.runningTaskIdentifiers.keySet();
        int i = 0;
        if (!(keySet instanceof Collection) || !keySet.isEmpty()) {
            i = 0;
            for (UserPackage userPackage : keySet) {
                if (userPackage.getUiControl() != UIControl.HIDE_ENTRY && this.currentProfileIds.contains(Integer.valueOf(userPackage.getUserId()))) {
                    int i2 = i + 1;
                    i = i2;
                    if (i2 < 0) {
                        CollectionsKt__CollectionsKt.throwCountOverflow();
                        i = i2;
                    }
                }
            }
        }
        return i;
    }

    @Override // com.android.systemui.qs.FgsManagerController
    public StateFlow<Boolean> getShowFooterDot() {
        return this.showFooterDot;
    }

    public final MutableStateFlow<Boolean> get_isAvailable() {
        return this._isAvailable;
    }

    public final MutableStateFlow<Boolean> get_showFooterDot() {
        return this._showFooterDot;
    }

    @Override // com.android.systemui.qs.FgsManagerController
    public void init() {
        synchronized (this.lock) {
            if (this.initialized) {
                return;
            }
            this.showUserVisibleJobs = this.deviceConfigProxy.getBoolean("systemui", "task_manager_show_user_visible_jobs", false);
            try {
                this.activityManager.registerForegroundServiceObserver(this.foregroundServiceObserver);
                if (this.showUserVisibleJobs) {
                    this.jobScheduler.registerUserVisibleJobObserver(this.userVisibleJobObserver);
                }
            } catch (RemoteException e) {
                e.rethrowFromSystemServer();
            }
            this.userTracker.addCallback(this.userTrackerCallback, this.backgroundExecutor);
            Set<Integer> set = this.currentProfileIds;
            List<UserInfo> userProfiles = this.userTracker.getUserProfiles();
            ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(userProfiles, 10));
            for (UserInfo userInfo : userProfiles) {
                arrayList.add(Integer.valueOf(userInfo.id));
            }
            set.addAll(arrayList);
            this.deviceConfigProxy.addOnPropertiesChangedListener("systemui", this.backgroundExecutor, new DeviceConfig.OnPropertiesChangedListener() { // from class: com.android.systemui.qs.FgsManagerControllerImpl$init$1$2
                public final void onPropertiesChanged(DeviceConfig.Properties properties) {
                    FgsManagerControllerImpl.this.get_isAvailable().setValue(Boolean.valueOf(properties.getBoolean("task_manager_enabled", ((Boolean) FgsManagerControllerImpl.this.get_isAvailable().getValue()).booleanValue())));
                    FgsManagerControllerImpl.this.get_showFooterDot().setValue(Boolean.valueOf(properties.getBoolean("task_manager_show_footer_dot", ((Boolean) FgsManagerControllerImpl.this.get_showFooterDot().getValue()).booleanValue())));
                    FgsManagerControllerImpl fgsManagerControllerImpl = FgsManagerControllerImpl.this;
                    FgsManagerControllerImpl.access$setShowStopBtnForUserAllowlistedApps$p(fgsManagerControllerImpl, properties.getBoolean("show_stop_button_for_user_allowlisted_apps", FgsManagerControllerImpl.access$getShowStopBtnForUserAllowlistedApps$p(fgsManagerControllerImpl)));
                    boolean access$getShowUserVisibleJobs$p = FgsManagerControllerImpl.access$getShowUserVisibleJobs$p(FgsManagerControllerImpl.this);
                    FgsManagerControllerImpl fgsManagerControllerImpl2 = FgsManagerControllerImpl.this;
                    FgsManagerControllerImpl.access$setShowUserVisibleJobs$p(fgsManagerControllerImpl2, properties.getBoolean("task_manager_show_user_visible_jobs", FgsManagerControllerImpl.access$getShowUserVisibleJobs$p(fgsManagerControllerImpl2)));
                    if (FgsManagerControllerImpl.access$getShowUserVisibleJobs$p(FgsManagerControllerImpl.this) != access$getShowUserVisibleJobs$p) {
                        FgsManagerControllerImpl.access$onShowUserVisibleJobsFlagChanged(FgsManagerControllerImpl.this);
                    }
                }
            });
            this._isAvailable.setValue(Boolean.valueOf(this.deviceConfigProxy.getBoolean("systemui", "task_manager_enabled", true)));
            this._showFooterDot.setValue(Boolean.valueOf(this.deviceConfigProxy.getBoolean("systemui", "task_manager_show_footer_dot", false)));
            this.showStopBtnForUserAllowlistedApps = this.deviceConfigProxy.getBoolean("systemui", "show_stop_button_for_user_allowlisted_apps", true);
            this.dumpManager.registerDumpable(this);
            BroadcastDispatcher.registerReceiver$default(this.broadcastDispatcher, new BroadcastReceiver() { // from class: com.android.systemui.qs.FgsManagerControllerImpl$init$1$3
                @Override // android.content.BroadcastReceiver
                public void onReceive(Context context, Intent intent) {
                    if (Intrinsics.areEqual(intent.getAction(), "android.intent.action.SHOW_FOREGROUND_SERVICE_MANAGER")) {
                        FgsManagerControllerImpl.this.showDialog(null);
                    }
                }
            }, new IntentFilter("android.intent.action.SHOW_FOREGROUND_SERVICE_MANAGER"), this.mainExecutor, null, 4, null, 40, null);
            this.initialized = true;
            Unit unit = Unit.INSTANCE;
        }
    }

    @Override // com.android.systemui.qs.FgsManagerController
    public StateFlow<Boolean> isAvailable() {
        return this.isAvailable;
    }

    public final void logEvent(boolean z, final String str, final int i, final long j) {
        final long elapsedRealtime = this.systemClock.elapsedRealtime();
        final int i2 = z ? 2 : 1;
        this.backgroundExecutor.execute(new Runnable() { // from class: com.android.systemui.qs.FgsManagerControllerImpl$logEvent$1
            @Override // java.lang.Runnable
            public final void run() {
                SysUiStatsLog.write(450, FgsManagerControllerImpl.access$getPackageManager$p(FgsManagerControllerImpl.this).getPackageUidAsUser(str, i), i2, elapsedRealtime - j);
            }
        });
    }

    public final void onShowUserVisibleJobsFlagChanged() {
        if (this.showUserVisibleJobs) {
            this.jobScheduler.registerUserVisibleJobObserver(this.userVisibleJobObserver);
            return;
        }
        this.jobScheduler.unregisterUserVisibleJobObserver(this.userVisibleJobObserver);
        synchronized (this.lock) {
            for (Map.Entry<UserPackage, StartTimeAndIdentifiers> entry : this.runningTaskIdentifiers.entrySet()) {
                UserPackage key = entry.getKey();
                StartTimeAndIdentifiers value = entry.getValue();
                if (value.hasFgs()) {
                    value.clearJobSummaries();
                } else {
                    this.runningTaskIdentifiers.remove(key);
                }
            }
            updateNumberOfVisibleRunningPackagesLocked();
            updateAppItemsLocked();
            Unit unit = Unit.INSTANCE;
        }
    }

    @Override // com.android.systemui.qs.FgsManagerController
    public void removeOnDialogDismissedListener(FgsManagerController.OnDialogDismissedListener onDialogDismissedListener) {
        synchronized (this.lock) {
            this.onDialogDismissedListeners.remove(onDialogDismissedListener);
        }
    }

    @Override // com.android.systemui.qs.FgsManagerController
    public void removeOnNumberOfPackagesChangedListener(FgsManagerController.OnNumberOfPackagesChangedListener onNumberOfPackagesChangedListener) {
        synchronized (this.lock) {
            this.onNumberOfPackagesChangedListeners.remove(onNumberOfPackagesChangedListener);
        }
    }

    @Override // com.android.systemui.qs.FgsManagerController
    public void showDialog(final Expandable expandable) {
        synchronized (this.lock) {
            if (this.dialog == null) {
                for (UserPackage userPackage : this.runningTaskIdentifiers.keySet()) {
                    userPackage.updateUiControl();
                }
                final SystemUIDialog systemUIDialog = new SystemUIDialog(this.context);
                systemUIDialog.setTitle(R$string.fgs_manager_dialog_title);
                systemUIDialog.setMessage(R$string.fgs_manager_dialog_message);
                Context context = systemUIDialog.getContext();
                RecyclerView recyclerView = new RecyclerView(context);
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
                recyclerView.setAdapter(this.appListAdapter);
                systemUIDialog.setView(recyclerView, 0, context.getResources().getDimensionPixelSize(R$dimen.fgs_manager_list_top_spacing), 0, 0);
                this.dialog = systemUIDialog;
                systemUIDialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.android.systemui.qs.FgsManagerControllerImpl$showDialog$1$2
                    @Override // android.content.DialogInterface.OnDismissListener
                    public final void onDismiss(DialogInterface dialogInterface) {
                        FgsManagerControllerImpl.access$setNewChangesSinceDialogWasDismissed$p(FgsManagerControllerImpl.this, false);
                        Object access$getLock$p = FgsManagerControllerImpl.access$getLock$p(FgsManagerControllerImpl.this);
                        FgsManagerControllerImpl fgsManagerControllerImpl = FgsManagerControllerImpl.this;
                        synchronized (access$getLock$p) {
                            FgsManagerControllerImpl.access$setDialog$p(fgsManagerControllerImpl, null);
                            FgsManagerControllerImpl.access$updateAppItemsLocked(fgsManagerControllerImpl);
                            Unit unit = Unit.INSTANCE;
                        }
                        Set<FgsManagerController.OnDialogDismissedListener> access$getOnDialogDismissedListeners$p = FgsManagerControllerImpl.access$getOnDialogDismissedListeners$p(FgsManagerControllerImpl.this);
                        FgsManagerControllerImpl fgsManagerControllerImpl2 = FgsManagerControllerImpl.this;
                        for (final FgsManagerController.OnDialogDismissedListener onDialogDismissedListener : access$getOnDialogDismissedListeners$p) {
                            FgsManagerControllerImpl.access$getMainExecutor$p(fgsManagerControllerImpl2).execute(new Runnable() { // from class: com.android.systemui.qs.FgsManagerControllerImpl$showDialog$1$2$2$1
                                @Override // java.lang.Runnable
                                public final void run() {
                                    FgsManagerController.OnDialogDismissedListener.this.onDialogDismissed();
                                }
                            });
                        }
                    }
                });
                this.mainExecutor.execute(new Runnable() { // from class: com.android.systemui.qs.FgsManagerControllerImpl$showDialog$1$3
                    @Override // java.lang.Runnable
                    public final void run() {
                        Expandable expandable2 = Expandable.this;
                        DialogLaunchAnimator.Controller dialogLaunchController = expandable2 != null ? expandable2.dialogLaunchController(new DialogCuj(58, "active_background_apps")) : null;
                        if (dialogLaunchController != null) {
                            DialogLaunchAnimator.show$default(FgsManagerControllerImpl.access$getDialogLaunchAnimator$p(this), systemUIDialog, dialogLaunchController, false, 4, null);
                        } else {
                            systemUIDialog.show();
                        }
                    }
                });
                this.backgroundExecutor.execute(new Runnable() { // from class: com.android.systemui.qs.FgsManagerControllerImpl$showDialog$1$4
                    @Override // java.lang.Runnable
                    public final void run() {
                        Object access$getLock$p = FgsManagerControllerImpl.access$getLock$p(FgsManagerControllerImpl.this);
                        FgsManagerControllerImpl fgsManagerControllerImpl = FgsManagerControllerImpl.this;
                        synchronized (access$getLock$p) {
                            FgsManagerControllerImpl.access$updateAppItemsLocked(fgsManagerControllerImpl);
                            Unit unit = Unit.INSTANCE;
                        }
                    }
                });
            }
            Unit unit = Unit.INSTANCE;
        }
    }

    public final void stopPackage(int i, String str, long j) {
        logEvent(true, str, i, j);
        UserPackage userPackage = new UserPackage(i, str);
        if (this.showUserVisibleJobs) {
            StartTimeAndIdentifiers startTimeAndIdentifiers = this.runningTaskIdentifiers.get(userPackage);
            if (startTimeAndIdentifiers != null && startTimeAndIdentifiers.hasRunningJobs()) {
                this.jobScheduler.stopUserVisibleJobsForUser(str, i);
            }
        }
        StartTimeAndIdentifiers startTimeAndIdentifiers2 = this.runningTaskIdentifiers.get(userPackage);
        if (startTimeAndIdentifiers2 != null && startTimeAndIdentifiers2.hasFgs()) {
            this.activityManager.stopAppForUser(str, i);
        }
    }

    public final void updateAppItemsLocked() {
        if (this.dialog == null) {
            this.runningApps.clear();
            return;
        }
        Set<UserPackage> keySet = this.runningTaskIdentifiers.keySet();
        ArrayList<UserPackage> arrayList = new ArrayList();
        for (Object obj : keySet) {
            UserPackage userPackage = (UserPackage) obj;
            boolean z = false;
            if (this.currentProfileIds.contains(Integer.valueOf(userPackage.getUserId()))) {
                z = false;
                if (userPackage.getUiControl() != UIControl.HIDE_ENTRY) {
                    RunningApp runningApp = this.runningApps.get(userPackage);
                    z = false;
                    if (!(runningApp != null && runningApp.getStopped())) {
                        z = true;
                    }
                }
            }
            if (z) {
                arrayList.add(obj);
            }
        }
        Set<UserPackage> keySet2 = this.runningApps.keySet();
        ArrayList<UserPackage> arrayList2 = new ArrayList();
        for (Object obj2 : keySet2) {
            if (!this.runningTaskIdentifiers.containsKey((UserPackage) obj2)) {
                arrayList2.add(obj2);
            }
        }
        for (UserPackage userPackage2 : arrayList) {
            ApplicationInfo applicationInfoAsUser = this.packageManager.getApplicationInfoAsUser(userPackage2.getPackageName(), 0, userPackage2.getUserId());
            ArrayMap<UserPackage, RunningApp> arrayMap = this.runningApps;
            int userId = userPackage2.getUserId();
            String packageName = userPackage2.getPackageName();
            StartTimeAndIdentifiers startTimeAndIdentifiers = this.runningTaskIdentifiers.get(userPackage2);
            Intrinsics.checkNotNull(startTimeAndIdentifiers);
            long startTime = startTimeAndIdentifiers.getStartTime();
            UIControl uiControl = userPackage2.getUiControl();
            CharSequence applicationLabel = this.packageManager.getApplicationLabel(applicationInfoAsUser);
            PackageManager packageManager = this.packageManager;
            arrayMap.put(userPackage2, new RunningApp(userId, packageName, startTime, uiControl, applicationLabel, packageManager.getUserBadgedIcon(packageManager.getApplicationIcon(applicationInfoAsUser), UserHandle.of(userPackage2.getUserId()))));
            String packageName2 = userPackage2.getPackageName();
            int userId2 = userPackage2.getUserId();
            RunningApp runningApp2 = this.runningApps.get(userPackage2);
            Intrinsics.checkNotNull(runningApp2);
            logEvent(false, packageName2, userId2, runningApp2.getTimeStarted());
        }
        for (UserPackage userPackage3 : arrayList2) {
            RunningApp runningApp3 = this.runningApps.get(userPackage3);
            Intrinsics.checkNotNull(runningApp3);
            RunningApp runningApp4 = runningApp3;
            RunningApp copy$default = RunningApp.copy$default(runningApp4, 0, null, 0L, null, 15, null);
            copy$default.setStopped(true);
            copy$default.setAppLabel(runningApp4.getAppLabel());
            copy$default.setIcon(runningApp4.getIcon());
            this.runningApps.put(userPackage3, copy$default);
        }
        this.mainExecutor.execute(new Runnable() { // from class: com.android.systemui.qs.FgsManagerControllerImpl$updateAppItemsLocked$3
            @Override // java.lang.Runnable
            public final void run() {
                FgsManagerControllerImpl.access$getAppListAdapter$p(FgsManagerControllerImpl.this).setData(CollectionsKt___CollectionsKt.sortedWith(CollectionsKt___CollectionsKt.toList(FgsManagerControllerImpl.access$getRunningApps$p(FgsManagerControllerImpl.this).values()), new Comparator() { // from class: com.android.systemui.qs.FgsManagerControllerImpl$updateAppItemsLocked$3$run$$inlined$sortedByDescending$1
                    @Override // java.util.Comparator
                    public final int compare(T t, T t2) {
                        return ComparisonsKt__ComparisonsKt.compareValues(Long.valueOf(((FgsManagerControllerImpl.RunningApp) t2).getTimeStarted()), Long.valueOf(((FgsManagerControllerImpl.RunningApp) t).getTimeStarted()));
                    }
                }));
            }
        });
    }

    public final void updateNumberOfVisibleRunningPackagesLocked() {
        final int numVisiblePackagesLocked = getNumVisiblePackagesLocked();
        if (numVisiblePackagesLocked != this.lastNumberOfVisiblePackages) {
            this.lastNumberOfVisiblePackages = numVisiblePackagesLocked;
            this.newChangesSinceDialogWasDismissed = true;
            for (final FgsManagerController.OnNumberOfPackagesChangedListener onNumberOfPackagesChangedListener : this.onNumberOfPackagesChangedListeners) {
                this.backgroundExecutor.execute(new Runnable() { // from class: com.android.systemui.qs.FgsManagerControllerImpl$updateNumberOfVisibleRunningPackagesLocked$1$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        FgsManagerController.OnNumberOfPackagesChangedListener.this.onNumberOfPackagesChanged(numVisiblePackagesLocked);
                    }
                });
            }
        }
    }

    @Override // com.android.systemui.qs.FgsManagerController
    public int visibleButtonsCount() {
        int numVisibleButtonsLocked;
        synchronized (this.lock) {
            numVisibleButtonsLocked = getNumVisibleButtonsLocked();
        }
        return numVisibleButtonsLocked;
    }
}