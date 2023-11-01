package com.android.systemui.privacy;

import android.app.ActivityManager;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.UserInfo;
import android.os.UserHandle;
import android.permission.PermissionGroupUsage;
import android.permission.PermissionManager;
import android.util.Log;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.appops.AppOpsController;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.privacy.PrivacyDialog;
import com.android.systemui.privacy.PrivacyDialogController;
import com.android.systemui.privacy.logging.PrivacyLogger;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.concurrent.Executor;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsJVMKt;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt__MutableCollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.MapsKt__MapsJVMKt;
import kotlin.comparisons.ComparisonsKt__ComparisonsKt;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: mainsysui33.jar:com/android/systemui/privacy/PrivacyDialogController.class */
public final class PrivacyDialogController {
    public static final Companion Companion = new Companion(null);
    public final ActivityStarter activityStarter;
    public final AppOpsController appOpsController;
    public final Executor backgroundExecutor;
    public Dialog dialog;
    public final DialogProvider dialogProvider;
    public final KeyguardStateController keyguardStateController;
    public final PrivacyDialogController$onDialogDismissed$1 onDialogDismissed;
    public final PackageManager packageManager;
    public final PermissionManager permissionManager;
    public final PrivacyItemController privacyItemController;
    public final PrivacyLogger privacyLogger;
    public final UiEventLogger uiEventLogger;
    public final Executor uiExecutor;
    public final UserTracker userTracker;

    /* loaded from: mainsysui33.jar:com/android/systemui/privacy/PrivacyDialogController$Companion.class */
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/privacy/PrivacyDialogController$DialogProvider.class */
    public interface DialogProvider {
        PrivacyDialog makeDialog(Context context, List<PrivacyDialog.PrivacyElement> list, Function4<? super String, ? super Integer, ? super CharSequence, ? super Intent, Unit> function4);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public PrivacyDialogController(PermissionManager permissionManager, PackageManager packageManager, PrivacyItemController privacyItemController, UserTracker userTracker, ActivityStarter activityStarter, Executor executor, Executor executor2, PrivacyLogger privacyLogger, KeyguardStateController keyguardStateController, AppOpsController appOpsController, UiEventLogger uiEventLogger) {
        this(permissionManager, packageManager, privacyItemController, userTracker, activityStarter, executor, executor2, privacyLogger, keyguardStateController, appOpsController, uiEventLogger, r12);
        PrivacyDialogControllerKt$defaultDialogProvider$1 privacyDialogControllerKt$defaultDialogProvider$1;
        privacyDialogControllerKt$defaultDialogProvider$1 = PrivacyDialogControllerKt.defaultDialogProvider;
    }

    /* JADX WARN: Type inference failed for: r1v12, types: [com.android.systemui.privacy.PrivacyDialogController$onDialogDismissed$1] */
    public PrivacyDialogController(PermissionManager permissionManager, PackageManager packageManager, PrivacyItemController privacyItemController, UserTracker userTracker, ActivityStarter activityStarter, Executor executor, Executor executor2, PrivacyLogger privacyLogger, KeyguardStateController keyguardStateController, AppOpsController appOpsController, UiEventLogger uiEventLogger, DialogProvider dialogProvider) {
        this.permissionManager = permissionManager;
        this.packageManager = packageManager;
        this.privacyItemController = privacyItemController;
        this.userTracker = userTracker;
        this.activityStarter = activityStarter;
        this.backgroundExecutor = executor;
        this.uiExecutor = executor2;
        this.privacyLogger = privacyLogger;
        this.keyguardStateController = keyguardStateController;
        this.appOpsController = appOpsController;
        this.uiEventLogger = uiEventLogger;
        this.dialogProvider = dialogProvider;
        this.onDialogDismissed = new PrivacyDialog.OnDialogDismissed() { // from class: com.android.systemui.privacy.PrivacyDialogController$onDialogDismissed$1
            @Override // com.android.systemui.privacy.PrivacyDialog.OnDialogDismissed
            public void onDialogDismissed() {
                PrivacyLogger privacyLogger2;
                UiEventLogger uiEventLogger2;
                privacyLogger2 = PrivacyDialogController.this.privacyLogger;
                privacyLogger2.logPrivacyDialogDismissed();
                uiEventLogger2 = PrivacyDialogController.this.uiEventLogger;
                uiEventLogger2.log(PrivacyDialogEvent.PRIVACY_DIALOG_DISMISSED);
                PrivacyDialogController.this.dialog = null;
            }
        };
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.privacy.PrivacyDialogController$startActivity$1.onActivityStarted(int):void] */
    public static final /* synthetic */ Dialog access$getDialog$p(PrivacyDialogController privacyDialogController) {
        return privacyDialogController.dialog;
    }

    public final void dismissDialog() {
        Dialog dialog = this.dialog;
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    /* JADX DEBUG: Failed to insert an additional move for type inference into block B:99:0x0053 */
    public final List<PrivacyDialog.PrivacyElement> filterAndSelect(List<PrivacyDialog.PrivacyElement> list) {
        Object next;
        List emptyList;
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Object obj : list) {
            PrivacyType type = ((PrivacyDialog.PrivacyElement) obj).getType();
            Object obj2 = linkedHashMap.get(type);
            ArrayList arrayList = obj2;
            if (obj2 == null) {
                arrayList = new ArrayList();
                linkedHashMap.put(type, arrayList);
            }
            ((List) arrayList).add(obj);
        }
        SortedMap sortedMap = MapsKt__MapsJVMKt.toSortedMap(linkedHashMap);
        ArrayList arrayList2 = new ArrayList();
        for (Map.Entry entry : sortedMap.entrySet()) {
            List list2 = (List) entry.getValue();
            ArrayList arrayList3 = new ArrayList();
            for (Object obj3 : list2) {
                if (((PrivacyDialog.PrivacyElement) obj3).getActive()) {
                    arrayList3.add(obj3);
                }
            }
            if (!arrayList3.isEmpty()) {
                emptyList = CollectionsKt___CollectionsKt.sortedWith(arrayList3, new Comparator() { // from class: com.android.systemui.privacy.PrivacyDialogController$filterAndSelect$lambda$6$$inlined$sortedByDescending$1
                    @Override // java.util.Comparator
                    public final int compare(T t, T t2) {
                        return ComparisonsKt__ComparisonsKt.compareValues(Long.valueOf(((PrivacyDialog.PrivacyElement) t2).getLastActiveTimestamp()), Long.valueOf(((PrivacyDialog.PrivacyElement) t).getLastActiveTimestamp()));
                    }
                });
            } else {
                Iterator it = list2.iterator();
                if (it.hasNext()) {
                    next = it.next();
                    if (it.hasNext()) {
                        long lastActiveTimestamp = ((PrivacyDialog.PrivacyElement) next).getLastActiveTimestamp();
                        Object obj4 = next;
                        do {
                            Object next2 = it.next();
                            long lastActiveTimestamp2 = ((PrivacyDialog.PrivacyElement) next2).getLastActiveTimestamp();
                            next = obj4;
                            long j = lastActiveTimestamp;
                            if (lastActiveTimestamp < lastActiveTimestamp2) {
                                next = next2;
                                j = lastActiveTimestamp2;
                            }
                            obj4 = next;
                            lastActiveTimestamp = j;
                        } while (it.hasNext());
                    }
                } else {
                    next = null;
                }
                PrivacyDialog.PrivacyElement privacyElement = (PrivacyDialog.PrivacyElement) next;
                if (privacyElement != null) {
                    List listOf = CollectionsKt__CollectionsJVMKt.listOf(privacyElement);
                    emptyList = listOf;
                    if (listOf != null) {
                    }
                }
                emptyList = CollectionsKt__CollectionsKt.emptyList();
            }
            CollectionsKt__MutableCollectionsKt.addAll(arrayList2, emptyList);
        }
        return arrayList2;
    }

    public final PrivacyType filterType(PrivacyType privacyType) {
        PrivacyType privacyType2 = null;
        if (privacyType != null) {
            if (((privacyType != PrivacyType.TYPE_CAMERA && privacyType != PrivacyType.TYPE_MICROPHONE) || !this.privacyItemController.getMicCameraAvailable()) && (privacyType != PrivacyType.TYPE_LOCATION || !this.privacyItemController.getLocationAvailable())) {
                privacyType = null;
            }
            privacyType2 = privacyType;
        }
        return privacyType2;
    }

    public final Intent getDefaultManageAppPermissionsIntent(String str, int i) {
        Intent intent = new Intent("android.intent.action.MANAGE_APP_PERMISSIONS");
        intent.putExtra("android.intent.extra.PACKAGE_NAME", str);
        intent.putExtra("android.intent.extra.USER", UserHandle.of(i));
        return intent;
    }

    public final CharSequence getLabelForPackage(String str, int i) {
        try {
            str = this.packageManager.getApplicationInfoAsUser(str, 0, UserHandle.getUserId(i)).loadLabel(this.packageManager);
        } catch (PackageManager.NameNotFoundException e) {
            Log.w("PrivacyDialogController", "Label not found for: " + str);
        }
        return str;
    }

    public final Intent getManagePermissionIntent(String str, int i, CharSequence charSequence, CharSequence charSequence2, boolean z) {
        ActivityInfo activityInfo;
        if (charSequence2 != null && z) {
            Intent intent = new Intent("android.intent.action.MANAGE_PERMISSION_USAGE");
            intent.setPackage(str);
            intent.putExtra("android.intent.extra.PERMISSION_GROUP_NAME", charSequence.toString());
            intent.putExtra("android.intent.extra.ATTRIBUTION_TAGS", new String[]{charSequence2.toString()});
            intent.putExtra("android.intent.extra.SHOWING_ATTRIBUTION", true);
            ResolveInfo resolveActivity = this.packageManager.resolveActivity(intent, PackageManager.ResolveInfoFlags.of(0L));
            if (resolveActivity != null && (activityInfo = resolveActivity.activityInfo) != null && Intrinsics.areEqual(activityInfo.permission, "android.permission.START_VIEW_PERMISSION_USAGE")) {
                intent.setComponent(new ComponentName(str, resolveActivity.activityInfo.name));
                return intent;
            }
        }
        return getDefaultManageAppPermissionsIntent(str, i);
    }

    public final PrivacyType permGroupToPrivacyType(String str) {
        PrivacyType privacyType;
        int hashCode = str.hashCode();
        if (hashCode == -1140935117) {
            if (str.equals("android.permission-group.CAMERA")) {
                privacyType = PrivacyType.TYPE_CAMERA;
            }
            privacyType = null;
        } else if (hashCode != 828638019) {
            if (hashCode == 1581272376 && str.equals("android.permission-group.MICROPHONE")) {
                privacyType = PrivacyType.TYPE_MICROPHONE;
            }
            privacyType = null;
        } else {
            if (str.equals("android.permission-group.LOCATION")) {
                privacyType = PrivacyType.TYPE_LOCATION;
            }
            privacyType = null;
        }
        return privacyType;
    }

    public final List<PermissionGroupUsage> permGroupUsage() {
        return this.permissionManager.getIndicatorAppOpUsageData(this.appOpsController.isMicMuted());
    }

    public final void showDialog(final Context context) {
        dismissDialog();
        this.backgroundExecutor.execute(new Runnable() { // from class: com.android.systemui.privacy.PrivacyDialogController$showDialog$1
            /* JADX WARN: Code restructure failed: missing block: B:19:0x00bc, code lost:
                if (r0.isPhoneCall() != false) goto L24;
             */
            @Override // java.lang.Runnable
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final void run() {
                List<PermissionGroupUsage> permGroupUsage;
                UserTracker userTracker;
                PrivacyLogger privacyLogger;
                Executor executor;
                PrivacyType permGroupToPrivacyType;
                PrivacyType filterType;
                Object obj;
                PrivacyDialog.PrivacyElement privacyElement;
                CharSequence labelForPackage;
                CharSequence charSequence;
                Intent managePermissionIntent;
                permGroupUsage = PrivacyDialogController.this.permGroupUsage();
                userTracker = PrivacyDialogController.this.userTracker;
                List userProfiles = userTracker.getUserProfiles();
                privacyLogger = PrivacyDialogController.this.privacyLogger;
                privacyLogger.logUnfilteredPermGroupUsage(permGroupUsage);
                List<PermissionGroupUsage> list = permGroupUsage;
                PrivacyDialogController privacyDialogController = PrivacyDialogController.this;
                final ArrayList arrayList = new ArrayList();
                for (PermissionGroupUsage permissionGroupUsage : list) {
                    permGroupToPrivacyType = privacyDialogController.permGroupToPrivacyType(permissionGroupUsage.getPermissionGroupName());
                    filterType = privacyDialogController.filterType(permGroupToPrivacyType);
                    Iterator it = userProfiles.iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            obj = null;
                            break;
                        }
                        obj = it.next();
                        if (((UserInfo) obj).id == UserHandle.getUserId(permissionGroupUsage.getUid())) {
                            break;
                        }
                    }
                    UserInfo userInfo = (UserInfo) obj;
                    if (userInfo == null) {
                        privacyElement = null;
                    }
                    privacyElement = null;
                    if (filterType != null) {
                        if (permissionGroupUsage.isPhoneCall()) {
                            charSequence = "";
                        } else {
                            labelForPackage = privacyDialogController.getLabelForPackage(permissionGroupUsage.getPackageName(), permissionGroupUsage.getUid());
                            charSequence = labelForPackage;
                        }
                        int userId = UserHandle.getUserId(permissionGroupUsage.getUid());
                        String packageName = permissionGroupUsage.getPackageName();
                        CharSequence attributionTag = permissionGroupUsage.getAttributionTag();
                        CharSequence attributionLabel = permissionGroupUsage.getAttributionLabel();
                        CharSequence proxyLabel = permissionGroupUsage.getProxyLabel();
                        long lastAccessTimeMillis = permissionGroupUsage.getLastAccessTimeMillis();
                        boolean isActive = permissionGroupUsage.isActive();
                        boolean isManagedProfile = userInfo != null ? userInfo.isManagedProfile() : false;
                        boolean isPhoneCall = permissionGroupUsage.isPhoneCall();
                        String permissionGroupName = permissionGroupUsage.getPermissionGroupName();
                        managePermissionIntent = privacyDialogController.getManagePermissionIntent(permissionGroupUsage.getPackageName(), userId, permissionGroupUsage.getPermissionGroupName(), permissionGroupUsage.getAttributionTag(), permissionGroupUsage.getAttributionLabel() != null);
                        privacyElement = new PrivacyDialog.PrivacyElement(filterType, packageName, userId, charSequence, attributionTag, attributionLabel, proxyLabel, lastAccessTimeMillis, isActive, isManagedProfile, isPhoneCall, permissionGroupName, managePermissionIntent);
                    }
                    if (privacyElement != null) {
                        arrayList.add(privacyElement);
                    }
                }
                executor = PrivacyDialogController.this.uiExecutor;
                final PrivacyDialogController privacyDialogController2 = PrivacyDialogController.this;
                final Context context2 = context;
                executor.execute(new Runnable() { // from class: com.android.systemui.privacy.PrivacyDialogController$showDialog$1.1
                    /* JADX WARN: Type inference failed for: r0v12, types: [android.app.AlertDialog, com.android.systemui.privacy.PrivacyDialog, com.android.systemui.statusbar.phone.SystemUIDialog, android.app.Dialog] */
                    @Override // java.lang.Runnable
                    public final void run() {
                        List<PrivacyDialog.PrivacyElement> filterAndSelect;
                        PrivacyDialogController.DialogProvider dialogProvider;
                        PrivacyDialogController$onDialogDismissed$1 privacyDialogController$onDialogDismissed$1;
                        PrivacyLogger privacyLogger2;
                        filterAndSelect = PrivacyDialogController.this.filterAndSelect(arrayList);
                        if (!(!filterAndSelect.isEmpty())) {
                            Log.w("PrivacyDialogController", "Trying to show empty dialog");
                            return;
                        }
                        dialogProvider = PrivacyDialogController.this.dialogProvider;
                        ?? makeDialog = dialogProvider.makeDialog(context2, filterAndSelect, new PrivacyDialogController$showDialog$1$1$d$1(PrivacyDialogController.this));
                        makeDialog.setShowForAllUsers(true);
                        privacyDialogController$onDialogDismissed$1 = PrivacyDialogController.this.onDialogDismissed;
                        makeDialog.addOnDismissListener(privacyDialogController$onDialogDismissed$1);
                        makeDialog.show();
                        privacyLogger2 = PrivacyDialogController.this.privacyLogger;
                        privacyLogger2.logShowDialogContents(filterAndSelect);
                        PrivacyDialogController.this.dialog = makeDialog;
                    }
                });
            }
        });
    }

    public final void startActivity(String str, int i, CharSequence charSequence, Intent intent) {
        Dialog dialog;
        Intent intent2 = intent;
        if (intent == null) {
            intent2 = getDefaultManageAppPermissionsIntent(str, i);
        }
        this.uiEventLogger.log(PrivacyDialogEvent.PRIVACY_DIALOG_ITEM_CLICKED_TO_APP_SETTINGS, i, str);
        this.privacyLogger.logStartSettingsActivityFromDialog(str, i);
        if (!this.keyguardStateController.isUnlocked() && (dialog = this.dialog) != null) {
            dialog.hide();
        }
        this.activityStarter.startActivity(intent2, true, new ActivityStarter.Callback() { // from class: com.android.systemui.privacy.PrivacyDialogController$startActivity$1
            @Override // com.android.systemui.plugins.ActivityStarter.Callback
            public final void onActivityStarted(int i2) {
                if (ActivityManager.isStartResultSuccessful(i2)) {
                    PrivacyDialogController.this.dismissDialog();
                    return;
                }
                Dialog access$getDialog$p = PrivacyDialogController.access$getDialog$p(PrivacyDialogController.this);
                if (access$getDialog$p != null) {
                    access$getDialog$p.show();
                }
            }
        });
    }
}