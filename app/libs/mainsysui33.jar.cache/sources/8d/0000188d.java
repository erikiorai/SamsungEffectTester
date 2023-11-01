package com.android.systemui.keyguard;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.content.pm.ProviderInfo;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.util.Log;
import com.android.systemui.SystemUIAppComponentFactoryBase;
import com.android.systemui.keyguard.domain.interactor.KeyguardQuickAffordanceInteractor;
import com.android.systemui.keyguard.shared.model.KeyguardPickerFlag;
import com.android.systemui.keyguard.shared.model.KeyguardQuickAffordancePickerRepresentation;
import com.android.systemui.keyguard.shared.model.KeyguardSlotPickerRepresentation;
import com.android.systemui.keyguard.ui.preview.KeyguardRemotePreviewManager;
import com.android.systemui.shared.customization.data.content.CustomizationProviderContract;
import java.util.List;
import java.util.Map;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.BuildersKt;

/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/CustomizationProvider.class */
public final class CustomizationProvider extends ContentProvider implements SystemUIAppComponentFactoryBase.ContextInitializer {
    public static final Companion Companion = new Companion(null);
    public SystemUIAppComponentFactoryBase.ContextAvailableCallback contextAvailableCallback;
    public KeyguardQuickAffordanceInteractor interactor;
    public KeyguardRemotePreviewManager previewManager;
    public final UriMatcher uriMatcher;

    /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/CustomizationProvider$Companion.class */
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public CustomizationProvider() {
        UriMatcher uriMatcher = new UriMatcher(-1);
        CustomizationProviderContract.LockScreenQuickAffordances lockScreenQuickAffordances = CustomizationProviderContract.LockScreenQuickAffordances.INSTANCE;
        uriMatcher.addURI("com.android.systemui.customization", lockScreenQuickAffordances.qualifiedTablePath("slots"), 1);
        uriMatcher.addURI("com.android.systemui.customization", lockScreenQuickAffordances.qualifiedTablePath("affordances"), 2);
        uriMatcher.addURI("com.android.systemui.customization", lockScreenQuickAffordances.qualifiedTablePath("selections"), 3);
        uriMatcher.addURI("com.android.systemui.customization", "flags", 4);
        this.uriMatcher = uriMatcher;
    }

    @Override // android.content.ContentProvider
    public void attachInfo(Context context, ProviderInfo providerInfo) {
        SystemUIAppComponentFactoryBase.ContextAvailableCallback contextAvailableCallback = this.contextAvailableCallback;
        SystemUIAppComponentFactoryBase.ContextAvailableCallback contextAvailableCallback2 = contextAvailableCallback;
        if (contextAvailableCallback == null) {
            contextAvailableCallback2 = null;
        }
        if (context == null) {
            throw new IllegalStateException("Required value was null.".toString());
        }
        contextAvailableCallback2.onContextAvailable(context);
        super.attachInfo(context, providerInfo);
    }

    @Override // android.content.ContentProvider
    public Bundle call(String str, String str2, Bundle bundle) {
        return requireContext().checkPermission("android.permission.BIND_WALLPAPER", Binder.getCallingPid(), Binder.getCallingUid()) == 0 ? getPreviewManager().preview(bundle) : null;
    }

    @Override // android.content.ContentProvider
    public int delete(Uri uri, String str, String[] strArr) {
        if (this.uriMatcher.match(uri) == 3) {
            return deleteSelection(uri, strArr);
        }
        throw new UnsupportedOperationException();
    }

    public final int deleteSelection(Uri uri, String[] strArr) {
        Pair pair;
        ContentResolver contentResolver;
        if (strArr != null) {
            int length = strArr.length;
            int i = 0;
            if (length == 1) {
                pair = new Pair(strArr[0], (Object) null);
            } else if (length != 2) {
                throw new IllegalArgumentException("Cannot delete selection, selection arguments has wrong size, expected to have 1 or 2 arguments, had " + strArr.length + " instead!");
            } else {
                pair = new Pair(strArr[0], strArr[1]);
            }
            String str = (String) pair.component1();
            String str2 = (String) pair.component2();
            if (getInteractor().unselect(str, str2)) {
                Log.d("KeyguardQuickAffordanceProvider", "Successfully unselected " + str2 + " for slot " + str);
                Context context = getContext();
                if (context != null && (contentResolver = context.getContentResolver()) != null) {
                    contentResolver.notifyChange(uri, null);
                }
                i = 1;
            } else {
                Log.d("KeyguardQuickAffordanceProvider", "Failed to unselect " + str2 + " for slot " + str);
            }
            return i;
        }
        throw new IllegalArgumentException("Cannot delete selection, selection arguments not included!");
    }

    public final KeyguardQuickAffordanceInteractor getInteractor() {
        KeyguardQuickAffordanceInteractor keyguardQuickAffordanceInteractor = this.interactor;
        if (keyguardQuickAffordanceInteractor != null) {
            return keyguardQuickAffordanceInteractor;
        }
        return null;
    }

    public final KeyguardRemotePreviewManager getPreviewManager() {
        KeyguardRemotePreviewManager keyguardRemotePreviewManager = this.previewManager;
        if (keyguardRemotePreviewManager != null) {
            return keyguardRemotePreviewManager;
        }
        return null;
    }

    @Override // android.content.ContentProvider
    public String getType(Uri uri) {
        int match = this.uriMatcher.match(uri);
        String str = (match == 1 || match == 2 || match == 3 || match == 4) ? "vnd.android.cursor.dir/vnd." : null;
        int match2 = this.uriMatcher.match(uri);
        String qualifiedTablePath = match2 != 1 ? match2 != 2 ? match2 != 3 ? match2 != 4 ? null : "flags" : CustomizationProviderContract.LockScreenQuickAffordances.INSTANCE.qualifiedTablePath("selections") : CustomizationProviderContract.LockScreenQuickAffordances.INSTANCE.qualifiedTablePath("affordances") : CustomizationProviderContract.LockScreenQuickAffordances.INSTANCE.qualifiedTablePath("slots");
        if (str == null || qualifiedTablePath == null) {
            return null;
        }
        return str + "com.android.systemui.customization." + qualifiedTablePath;
    }

    @Override // android.content.ContentProvider
    public Uri insert(Uri uri, ContentValues contentValues) {
        if (this.uriMatcher.match(uri) == 3) {
            return insertSelection(contentValues);
        }
        throw new UnsupportedOperationException();
    }

    /* JADX WARN: Code restructure failed: missing block: B:20:0x0053, code lost:
        if (r0.length() == 0) goto L34;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Uri insertSelection(ContentValues contentValues) {
        boolean z;
        ContentResolver contentResolver;
        if (contentValues != null) {
            if (contentValues.containsKey("slot_id")) {
                if (contentValues.containsKey("affordance_id")) {
                    String asString = contentValues.getAsString("slot_id");
                    String asString2 = contentValues.getAsString("affordance_id");
                    if (asString == null || asString.length() == 0) {
                        throw new IllegalArgumentException("Cannot insert selection, slot ID was empty!");
                    }
                    if (asString2 != null) {
                        z = false;
                    }
                    z = true;
                    if (z) {
                        throw new IllegalArgumentException("Cannot insert selection, affordance ID was empty!");
                    }
                    Uri uri = null;
                    if (getInteractor().select(asString, asString2)) {
                        Log.d("KeyguardQuickAffordanceProvider", "Successfully selected " + asString2 + " for slot " + asString);
                        Context context = getContext();
                        if (context != null && (contentResolver = context.getContentResolver()) != null) {
                            contentResolver.notifyChange(CustomizationProviderContract.LockScreenQuickAffordances.SelectionTable.INSTANCE.getURI(), null);
                        }
                        uri = CustomizationProviderContract.LockScreenQuickAffordances.SelectionTable.INSTANCE.getURI();
                    } else {
                        Log.d("KeyguardQuickAffordanceProvider", "Failed to select " + asString2 + " for slot " + asString);
                    }
                    return uri;
                }
                throw new IllegalArgumentException("Cannot insert selection, \"affordance_id\" not specified!");
            }
            throw new IllegalArgumentException("Cannot insert selection, \"slot_id\" not specified!");
        }
        throw new IllegalArgumentException("Cannot insert selection, no values passed in!");
    }

    @Override // android.content.ContentProvider
    public boolean onCreate() {
        return true;
    }

    @Override // android.content.ContentProvider
    public Cursor query(Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
        int match = this.uriMatcher.match(uri);
        Cursor cursor = null;
        if (match == 1) {
            cursor = querySlots();
        } else if (match == 2) {
            cursor = (Cursor) BuildersKt.runBlocking$default((CoroutineContext) null, new CustomizationProvider$query$1(this, null), 1, (Object) null);
        } else if (match == 3) {
            cursor = (Cursor) BuildersKt.runBlocking$default((CoroutineContext) null, new CustomizationProvider$query$2(this, null), 1, (Object) null);
        } else if (match == 4) {
            cursor = queryFlags();
        }
        return cursor;
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0045  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x006d  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x00f1  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object queryAffordances(Continuation<? super Cursor> continuation) {
        Continuation<? super List<KeyguardQuickAffordancePickerRepresentation>> customizationProvider$queryAffordances$1;
        int i;
        MatrixCursor matrixCursor;
        MatrixCursor matrixCursor2;
        if (continuation instanceof CustomizationProvider$queryAffordances$1) {
            Continuation<? super List<KeyguardQuickAffordancePickerRepresentation>> continuation2 = (CustomizationProvider$queryAffordances$1) continuation;
            int i2 = continuation2.label;
            if ((i2 & Integer.MIN_VALUE) != 0) {
                continuation2.label = i2 - 2147483648;
                customizationProvider$queryAffordances$1 = continuation2;
                Object obj = customizationProvider$queryAffordances$1.result;
                Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
                i = customizationProvider$queryAffordances$1.label;
                if (i != 0) {
                    ResultKt.throwOnFailure(obj);
                    MatrixCursor matrixCursor3 = new MatrixCursor(new String[]{"id", "name", "icon", "is_enabled", "enablement_instructions", "enablement_action_text", "enablement_action_intent"});
                    KeyguardQuickAffordanceInteractor interactor = getInteractor();
                    customizationProvider$queryAffordances$1.L$0 = matrixCursor3;
                    customizationProvider$queryAffordances$1.L$1 = matrixCursor3;
                    customizationProvider$queryAffordances$1.label = 1;
                    obj = interactor.getAffordancePickerRepresentations(customizationProvider$queryAffordances$1);
                    if (obj == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                    matrixCursor = matrixCursor3;
                    matrixCursor2 = matrixCursor3;
                } else if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                } else {
                    matrixCursor = (MatrixCursor) customizationProvider$queryAffordances$1.L$1;
                    matrixCursor2 = (MatrixCursor) customizationProvider$queryAffordances$1.L$0;
                    ResultKt.throwOnFailure(obj);
                }
                for (KeyguardQuickAffordancePickerRepresentation keyguardQuickAffordancePickerRepresentation : (Iterable) obj) {
                    String id = keyguardQuickAffordancePickerRepresentation.getId();
                    String name = keyguardQuickAffordancePickerRepresentation.getName();
                    Integer boxInt = Boxing.boxInt(keyguardQuickAffordancePickerRepresentation.getIconResourceId());
                    Integer boxInt2 = Boxing.boxInt(keyguardQuickAffordancePickerRepresentation.isEnabled() ? 1 : 0);
                    List<String> instructions = keyguardQuickAffordancePickerRepresentation.getInstructions();
                    matrixCursor.addRow(new Object[]{id, name, boxInt, boxInt2, instructions != null ? CollectionsKt___CollectionsKt.joinToString$default(instructions, "][", (CharSequence) null, (CharSequence) null, 0, (CharSequence) null, (Function1) null, 62, (Object) null) : null, keyguardQuickAffordancePickerRepresentation.getActionText(), keyguardQuickAffordancePickerRepresentation.getActionComponentName()});
                }
                return matrixCursor2;
            }
        }
        customizationProvider$queryAffordances$1 = new CustomizationProvider$queryAffordances$1(this, continuation);
        Object obj2 = customizationProvider$queryAffordances$1.result;
        Object coroutine_suspended2 = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
        i = customizationProvider$queryAffordances$1.label;
        if (i != 0) {
        }
        while (r0.hasNext()) {
        }
        return matrixCursor2;
    }

    public final Cursor queryFlags() {
        MatrixCursor matrixCursor = new MatrixCursor(new String[]{"name", "value"});
        for (KeyguardPickerFlag keyguardPickerFlag : getInteractor().getPickerFlags()) {
            matrixCursor.addRow(new Object[]{keyguardPickerFlag.getName(), Integer.valueOf(keyguardPickerFlag.getValue() ? 1 : 0)});
        }
        return matrixCursor;
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0045  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x006d  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x00d7  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object querySelections(Continuation<? super Cursor> continuation) {
        Continuation<? super Map<String, ? extends List<KeyguardQuickAffordancePickerRepresentation>>> customizationProvider$querySelections$1;
        int i;
        MatrixCursor matrixCursor;
        MatrixCursor matrixCursor2;
        if (continuation instanceof CustomizationProvider$querySelections$1) {
            Continuation<? super Map<String, ? extends List<KeyguardQuickAffordancePickerRepresentation>>> continuation2 = (CustomizationProvider$querySelections$1) continuation;
            int i2 = continuation2.label;
            if ((i2 & Integer.MIN_VALUE) != 0) {
                continuation2.label = i2 - 2147483648;
                customizationProvider$querySelections$1 = continuation2;
                Object obj = customizationProvider$querySelections$1.result;
                Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
                i = customizationProvider$querySelections$1.label;
                if (i != 0) {
                    ResultKt.throwOnFailure(obj);
                    MatrixCursor matrixCursor3 = new MatrixCursor(new String[]{"slot_id", "affordance_id", "affordance_name"});
                    KeyguardQuickAffordanceInteractor interactor = getInteractor();
                    customizationProvider$querySelections$1.L$0 = matrixCursor3;
                    customizationProvider$querySelections$1.L$1 = matrixCursor3;
                    customizationProvider$querySelections$1.label = 1;
                    obj = interactor.getSelections(customizationProvider$querySelections$1);
                    if (obj == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                    matrixCursor = matrixCursor3;
                    matrixCursor2 = matrixCursor;
                } else if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                } else {
                    matrixCursor2 = (MatrixCursor) customizationProvider$querySelections$1.L$1;
                    matrixCursor = (MatrixCursor) customizationProvider$querySelections$1.L$0;
                    ResultKt.throwOnFailure(obj);
                }
                for (Map.Entry entry : ((Map) obj).entrySet()) {
                    String str = (String) entry.getKey();
                    for (KeyguardQuickAffordancePickerRepresentation keyguardQuickAffordancePickerRepresentation : (List) entry.getValue()) {
                        matrixCursor2.addRow(new String[]{str, keyguardQuickAffordancePickerRepresentation.getId(), keyguardQuickAffordancePickerRepresentation.getName()});
                    }
                }
                return matrixCursor;
            }
        }
        customizationProvider$querySelections$1 = new CustomizationProvider$querySelections$1(this, continuation);
        Object obj2 = customizationProvider$querySelections$1.result;
        Object coroutine_suspended2 = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
        i = customizationProvider$querySelections$1.label;
        if (i != 0) {
        }
        while (r0.hasNext()) {
        }
        return matrixCursor;
    }

    public final Cursor querySlots() {
        MatrixCursor matrixCursor = new MatrixCursor(new String[]{"id", "capacity"});
        for (KeyguardSlotPickerRepresentation keyguardSlotPickerRepresentation : getInteractor().getSlotPickerRepresentations()) {
            matrixCursor.addRow(new Object[]{keyguardSlotPickerRepresentation.getId(), Integer.valueOf(keyguardSlotPickerRepresentation.getMaxSelectedAffordances())});
        }
        return matrixCursor;
    }

    @Override // com.android.systemui.SystemUIAppComponentFactoryBase.ContextInitializer
    public void setContextAvailableCallback(SystemUIAppComponentFactoryBase.ContextAvailableCallback contextAvailableCallback) {
        this.contextAvailableCallback = contextAvailableCallback;
    }

    @Override // android.content.ContentProvider
    public int update(Uri uri, ContentValues contentValues, String str, String[] strArr) {
        Log.e("KeyguardQuickAffordanceProvider", "Update is not supported!");
        return 0;
    }
}