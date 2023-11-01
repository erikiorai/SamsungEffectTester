package com.android.systemui.people.widget;

import android.app.backup.BackupDataInputStream;
import android.app.backup.BackupDataOutput;
import android.app.backup.SharedPreferencesBackupHelper;
import android.app.people.IPeopleManager;
import android.appwidget.AppWidgetManager;
import android.compat.annotation.UnsupportedAppUsage;
import android.content.ComponentName;
import android.content.ContentProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import com.android.internal.annotations.VisibleForTesting;
import com.android.systemui.people.PeopleBackupFollowUpJob;
import com.android.systemui.people.SharedPreferencesHelper;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/* loaded from: mainsysui33.jar:com/android/systemui/people/widget/PeopleBackupHelper.class */
public class PeopleBackupHelper extends SharedPreferencesBackupHelper {
    public final AppWidgetManager mAppWidgetManager;
    public final Context mContext;
    public final IPeopleManager mIPeopleManager;
    public final PackageManager mPackageManager;
    public final UserHandle mUserHandle;

    /* renamed from: com.android.systemui.people.widget.PeopleBackupHelper$1 */
    /* loaded from: mainsysui33.jar:com/android/systemui/people/widget/PeopleBackupHelper$1.class */
    public static /* synthetic */ class AnonymousClass1 {
        public static final /* synthetic */ int[] $SwitchMap$com$android$systemui$people$widget$PeopleBackupHelper$SharedFileEntryType;

        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:39:0x0036 -> B:49:0x0014). Please submit an issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:41:0x003a -> B:47:0x001f). Please submit an issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:43:0x003e -> B:53:0x002a). Please submit an issue!!! */
        static {
            int[] iArr = new int[SharedFileEntryType.values().length];
            $SwitchMap$com$android$systemui$people$widget$PeopleBackupHelper$SharedFileEntryType = iArr;
            try {
                iArr[SharedFileEntryType.WIDGET_ID.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$android$systemui$people$widget$PeopleBackupHelper$SharedFileEntryType[SharedFileEntryType.PEOPLE_TILE_KEY.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$android$systemui$people$widget$PeopleBackupHelper$SharedFileEntryType[SharedFileEntryType.CONTACT_URI.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$android$systemui$people$widget$PeopleBackupHelper$SharedFileEntryType[SharedFileEntryType.UNKNOWN.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/people/widget/PeopleBackupHelper$SharedFileEntryType.class */
    public enum SharedFileEntryType {
        UNKNOWN,
        WIDGET_ID,
        PEOPLE_TILE_KEY,
        CONTACT_URI
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.people.widget.PeopleBackupHelper$$ExternalSyntheticLambda1.test(java.lang.Object):boolean] */
    public static /* synthetic */ boolean $r8$lambda$PHVfTszxRSMkrpjotrAsvzGI8C8(List list, String str) {
        return list.contains(str);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.people.widget.PeopleBackupHelper$$ExternalSyntheticLambda0.accept(java.lang.Object):void] */
    public static /* synthetic */ void $r8$lambda$obj9SwiiJ6qJ3Yk90hxw_QC8mDA(PeopleBackupHelper peopleBackupHelper, SharedPreferences.Editor editor, List list, Map.Entry entry) {
        peopleBackupHelper.lambda$performBackup$0(editor, list, entry);
    }

    public PeopleBackupHelper(Context context, UserHandle userHandle, String[] strArr) {
        super(context, strArr);
        this.mContext = context;
        this.mUserHandle = userHandle;
        this.mPackageManager = context.getPackageManager();
        this.mIPeopleManager = IPeopleManager.Stub.asInterface(ServiceManager.getService("people"));
        this.mAppWidgetManager = AppWidgetManager.getInstance(context);
    }

    @VisibleForTesting
    public PeopleBackupHelper(Context context, UserHandle userHandle, String[] strArr, PackageManager packageManager, IPeopleManager iPeopleManager) {
        super(context, strArr);
        this.mContext = context;
        this.mUserHandle = userHandle;
        this.mPackageManager = packageManager;
        this.mIPeopleManager = iPeopleManager;
        this.mAppWidgetManager = AppWidgetManager.getInstance(context);
    }

    public static SharedFileEntryType getEntryType(Map.Entry<String, ?> entry) {
        String key = entry.getKey();
        if (key == null) {
            return SharedFileEntryType.UNKNOWN;
        }
        try {
            Integer.parseInt(key);
            try {
                String str = (String) entry.getValue();
                return SharedFileEntryType.WIDGET_ID;
            } catch (Exception e) {
                Log.w("PeopleBackupHelper", "Malformed value, skipping:" + entry.getValue());
                return SharedFileEntryType.UNKNOWN;
            }
        } catch (NumberFormatException e2) {
            try {
                Set set = (Set) entry.getValue();
                if (PeopleTileKey.fromString(key) != null) {
                    return SharedFileEntryType.PEOPLE_TILE_KEY;
                }
                try {
                    Uri.parse(key);
                    return SharedFileEntryType.CONTACT_URI;
                } catch (Exception e3) {
                    return SharedFileEntryType.UNKNOWN;
                }
            } catch (Exception e4) {
                Log.w("PeopleBackupHelper", "Malformed value, skipping:" + entry.getValue());
                return SharedFileEntryType.UNKNOWN;
            }
        }
    }

    public static List<String> getFilesToBackup() {
        return Collections.singletonList("shared_backup");
    }

    public static boolean isReadyForRestore(IPeopleManager iPeopleManager, PackageManager packageManager, PeopleTileKey peopleTileKey) {
        if (PeopleTileKey.isValid(peopleTileKey)) {
            try {
                packageManager.getPackageInfoAsUser(peopleTileKey.getPackageName(), 0, peopleTileKey.getUserId());
                return iPeopleManager.isConversation(peopleTileKey.getPackageName(), peopleTileKey.getUserId(), peopleTileKey.getShortcutId());
            } catch (PackageManager.NameNotFoundException | Exception e) {
                return false;
            }
        }
        return true;
    }

    public static void restoreWidgetIdFiles(Context context, Set<String> set, PeopleTileKey peopleTileKey) {
        for (String str : set) {
            SharedPreferencesHelper.setPeopleTileKey(context.getSharedPreferences(str, 0), peopleTileKey);
        }
    }

    public static void updateWidgets(Context context) {
        int[] appWidgetIds = AppWidgetManager.getInstance(context).getAppWidgetIds(new ComponentName(context, PeopleSpaceWidgetProvider.class));
        if (appWidgetIds == null || appWidgetIds.length == 0) {
            return;
        }
        Intent intent = new Intent(context, PeopleSpaceWidgetProvider.class);
        intent.setAction("android.appwidget.action.APPWIDGET_UPDATE");
        intent.putExtra("appWidgetIds", appWidgetIds);
        context.sendBroadcast(intent);
    }

    public final void backupContactUriKey(String str, Set<String> set, SharedPreferences.Editor editor) {
        Uri parse = Uri.parse(String.valueOf(str));
        if (!ContentProvider.uriHasUserId(parse)) {
            if (this.mUserHandle.isSystem()) {
                editor.putStringSet(parse.toString(), set);
                return;
            }
            return;
        }
        int userIdFromUri = ContentProvider.getUserIdFromUri(parse);
        if (userIdFromUri == this.mUserHandle.getIdentifier()) {
            Uri uriWithoutUserId = ContentProvider.getUriWithoutUserId(parse);
            editor.putInt("add_user_id_to_uri_" + uriWithoutUserId.toString(), userIdFromUri);
            editor.putStringSet(uriWithoutUserId.toString(), set);
        }
    }

    /* renamed from: backupKey */
    public void lambda$performBackup$0(Map.Entry<String, ?> entry, SharedPreferences.Editor editor, List<String> list) {
        String key = entry.getKey();
        if (TextUtils.isEmpty(key)) {
            return;
        }
        int i = AnonymousClass1.$SwitchMap$com$android$systemui$people$widget$PeopleBackupHelper$SharedFileEntryType[getEntryType(entry).ordinal()];
        if (i == 1) {
            backupWidgetIdKey(key, String.valueOf(entry.getValue()), editor, list);
        } else if (i == 2) {
            backupPeopleTileKey(key, (Set) entry.getValue(), editor, list);
        } else if (i == 3) {
            backupContactUriKey(key, (Set) entry.getValue(), editor);
        } else {
            Log.w("PeopleBackupHelper", "Key not identified, skipping: " + key);
        }
    }

    public final void backupPeopleTileKey(String str, Set<String> set, SharedPreferences.Editor editor, final List<String> list) {
        PeopleTileKey fromString = PeopleTileKey.fromString(str);
        if (fromString.getUserId() != this.mUserHandle.getIdentifier()) {
            return;
        }
        Set<String> set2 = (Set) set.stream().filter(new Predicate() { // from class: com.android.systemui.people.widget.PeopleBackupHelper$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return PeopleBackupHelper.$r8$lambda$PHVfTszxRSMkrpjotrAsvzGI8C8(list, (String) obj);
            }
        }).collect(Collectors.toSet());
        if (set2.isEmpty()) {
            return;
        }
        fromString.setUserId(-1);
        editor.putStringSet(fromString.toString(), set2);
    }

    public final void backupWidgetIdKey(String str, String str2, SharedPreferences.Editor editor, List<String> list) {
        if (list.contains(str)) {
            Uri parse = Uri.parse(str2);
            Uri uri = parse;
            if (ContentProvider.uriHasUserId(parse)) {
                int userIdFromUri = ContentProvider.getUserIdFromUri(parse);
                editor.putInt("add_user_id_to_uri_" + str, userIdFromUri);
                uri = ContentProvider.getUriWithoutUserId(parse);
            }
            editor.putString(str, uri.toString());
        }
    }

    public final List<String> getExistingWidgetsForUser(int i) {
        ArrayList arrayList = new ArrayList();
        for (int i2 : this.mAppWidgetManager.getAppWidgetIds(new ComponentName(this.mContext, PeopleSpaceWidgetProvider.class))) {
            String valueOf = String.valueOf(i2);
            if (this.mContext.getSharedPreferences(valueOf, 0).getInt("user_id", -1) == i) {
                arrayList.add(valueOf);
            }
        }
        return arrayList;
    }

    @Override // android.app.backup.SharedPreferencesBackupHelper, android.app.backup.BackupHelper
    public void performBackup(ParcelFileDescriptor parcelFileDescriptor, BackupDataOutput backupDataOutput, ParcelFileDescriptor parcelFileDescriptor2) {
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.mContext);
        if (defaultSharedPreferences.getAll().isEmpty()) {
            return;
        }
        final SharedPreferences.Editor edit = this.mContext.getSharedPreferences("shared_backup", 0).edit();
        edit.clear();
        final List<String> existingWidgetsForUser = getExistingWidgetsForUser(this.mUserHandle.getIdentifier());
        if (existingWidgetsForUser.isEmpty()) {
            return;
        }
        defaultSharedPreferences.getAll().entrySet().forEach(new Consumer() { // from class: com.android.systemui.people.widget.PeopleBackupHelper$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                PeopleBackupHelper.$r8$lambda$obj9SwiiJ6qJ3Yk90hxw_QC8mDA(PeopleBackupHelper.this, edit, existingWidgetsForUser, (Map.Entry) obj);
            }
        });
        edit.apply();
        super.performBackup(parcelFileDescriptor, backupDataOutput, parcelFileDescriptor2);
    }

    public final void restoreContactUriKey(String str, Set<String> set, SharedPreferences.Editor editor, int i) {
        Uri parse = Uri.parse(str);
        Uri uri = parse;
        if (i != -1) {
            uri = ContentProvider.createContentUriForUser(parse, UserHandle.of(i));
        }
        editor.putStringSet(uri.toString(), set);
    }

    @Override // android.app.backup.SharedPreferencesBackupHelper, android.app.backup.BackupHelper
    public void restoreEntity(BackupDataInputStream backupDataInputStream) {
        super.restoreEntity(backupDataInputStream);
        boolean z = false;
        SharedPreferences sharedPreferences = this.mContext.getSharedPreferences("shared_backup", 0);
        SharedPreferences.Editor edit = PreferenceManager.getDefaultSharedPreferences(this.mContext).edit();
        SharedPreferences.Editor edit2 = this.mContext.getSharedPreferences("shared_follow_up", 0).edit();
        for (Map.Entry<String, ?> entry : sharedPreferences.getAll().entrySet()) {
            if (!restoreKey(entry, edit, edit2, sharedPreferences)) {
                z = true;
            }
        }
        edit.apply();
        edit2.apply();
        SharedPreferencesHelper.clear(sharedPreferences);
        if (z) {
            PeopleBackupFollowUpJob.scheduleJob(this.mContext);
        }
        updateWidgets(this.mContext);
    }

    public boolean restoreKey(Map.Entry<String, ?> entry, SharedPreferences.Editor editor, SharedPreferences.Editor editor2, SharedPreferences sharedPreferences) {
        String key = entry.getKey();
        SharedFileEntryType entryType = getEntryType(entry);
        int i = sharedPreferences.getInt("add_user_id_to_uri_" + key, -1);
        int i2 = AnonymousClass1.$SwitchMap$com$android$systemui$people$widget$PeopleBackupHelper$SharedFileEntryType[entryType.ordinal()];
        if (i2 == 1) {
            restoreWidgetIdKey(key, String.valueOf(entry.getValue()), editor, i);
            return true;
        } else if (i2 != 2) {
            if (i2 == 3) {
                restoreContactUriKey(key, (Set) entry.getValue(), editor, i);
                return true;
            }
            Log.e("PeopleBackupHelper", "Key not identified, skipping:" + key);
            return true;
        } else {
            return restorePeopleTileKeyAndCorrespondingWidgetFile(key, (Set) entry.getValue(), editor, editor2);
        }
    }

    public final boolean restorePeopleTileKeyAndCorrespondingWidgetFile(String str, Set<String> set, SharedPreferences.Editor editor, SharedPreferences.Editor editor2) {
        PeopleTileKey fromString = PeopleTileKey.fromString(str);
        if (fromString == null) {
            return true;
        }
        fromString.setUserId(this.mUserHandle.getIdentifier());
        if (PeopleTileKey.isValid(fromString)) {
            boolean isReadyForRestore = isReadyForRestore(this.mIPeopleManager, this.mPackageManager, fromString);
            if (!isReadyForRestore) {
                editor2.putStringSet(fromString.toString(), set);
            }
            editor.putStringSet(fromString.toString(), set);
            restoreWidgetIdFiles(this.mContext, set, fromString);
            return isReadyForRestore;
        }
        return true;
    }

    public final void restoreWidgetIdKey(String str, String str2, SharedPreferences.Editor editor, int i) {
        Uri parse = Uri.parse(str2);
        Uri uri = parse;
        if (i != -1) {
            uri = ContentProvider.createContentUriForUser(parse, UserHandle.of(i));
        }
        editor.putString(str, uri.toString());
    }

    @Override // android.app.backup.SharedPreferencesBackupHelper, android.app.backup.BackupHelper
    @UnsupportedAppUsage
    public /* bridge */ /* synthetic */ void writeNewStateDescription(ParcelFileDescriptor parcelFileDescriptor) {
        super.writeNewStateDescription(parcelFileDescriptor);
    }
}