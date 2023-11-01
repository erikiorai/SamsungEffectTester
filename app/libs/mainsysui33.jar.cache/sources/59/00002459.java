package com.android.systemui.screenshot;

import android.app.ActivityTaskManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Process;
import android.os.RemoteException;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.DeviceConfig;
import android.text.TextUtils;
import android.util.Log;
import com.android.internal.annotations.VisibleForTesting;
import com.android.systemui.R$drawable;
import com.android.systemui.R$string;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.Flags;
import com.android.systemui.flags.UnreleasedFlag;
import com.android.systemui.screenshot.ImageExporter;
import com.android.systemui.screenshot.ScreenshotController;
import com.android.systemui.screenshot.ScreenshotNotificationSmartActionsProvider;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

/* loaded from: mainsysui33.jar:com/android/systemui/screenshot/SaveImageInBackgroundTask.class */
public class SaveImageInBackgroundTask extends AsyncTask<String, Void, Void> {
    public static final String TAG = LogConfig.logTag(SaveImageInBackgroundTask.class);
    public final Context mContext;
    public FeatureFlags mFlags;
    public final ImageExporter mImageExporter;
    public long mImageTime;
    public final ScreenshotController.SaveImageInBackgroundData mParams;
    public String mScreenshotId;
    public final ScreenshotSmartActions mScreenshotSmartActions;
    public final Supplier<ScreenshotController.SavedImageData.ActionTransition> mSharedElementTransition;
    public final ScreenshotNotificationSmartActionsProvider mSmartActionsProvider;
    public final Random mRandom = new Random();
    public final ScreenshotController.SavedImageData mImageData = new ScreenshotController.SavedImageData();
    public final ScreenshotController.QuickShareData mQuickShareData = new ScreenshotController.QuickShareData();

    public SaveImageInBackgroundTask(Context context, FeatureFlags featureFlags, ImageExporter imageExporter, ScreenshotSmartActions screenshotSmartActions, ScreenshotController.SaveImageInBackgroundData saveImageInBackgroundData, Supplier<ScreenshotController.SavedImageData.ActionTransition> supplier, ScreenshotNotificationSmartActionsProvider screenshotNotificationSmartActionsProvider) {
        this.mContext = context;
        this.mFlags = featureFlags;
        this.mScreenshotSmartActions = screenshotSmartActions;
        this.mSharedElementTransition = supplier;
        this.mImageExporter = imageExporter;
        this.mParams = saveImageInBackgroundData;
        this.mSmartActionsProvider = screenshotNotificationSmartActionsProvider;
    }

    public static void addIntentExtras(String str, Intent intent, String str2, boolean z) {
        intent.putExtra("android:screenshot_action_type", str2).putExtra("android:screenshot_id", str).putExtra("android:smart_actions_enabled", z);
    }

    @VisibleForTesting
    private Notification.Action createQuickShareAction(Context context, Notification.Action action, Uri uri) {
        if (action == null) {
            return null;
        }
        Intent intent = action.actionIntent.getIntent();
        intent.setType("image/png");
        intent.putExtra("android.intent.extra.STREAM", uri);
        intent.putExtra("android.intent.extra.SUBJECT", String.format("Screenshot (%s)", DateFormat.getDateTimeInstance().format(new Date(this.mImageTime))));
        intent.setClipData(new ClipData(new ClipDescription("content", new String[]{"image/png"}), new ClipData.Item(uri)));
        intent.addFlags(1);
        PendingIntent activity = PendingIntent.getActivity(context, 0, intent, 335544320);
        Bundle extras = action.getExtras();
        String string = extras.getString(ScreenshotNotificationSmartActionsProvider.ACTION_TYPE, ScreenshotNotificationSmartActionsProvider.DEFAULT_ACTION_TYPE);
        Intent addFlags = new Intent(context, SmartActionsReceiver.class).putExtra("android:screenshot_action_intent", activity).addFlags(268435456);
        addIntentExtras(this.mScreenshotId, addFlags, string, true);
        return new Notification.Action.Builder(action.getIcon(), action.title, PendingIntent.getBroadcast(context, this.mRandom.nextInt(), addFlags, 335544320)).setContextual(true).addExtras(extras).build();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ ScreenshotController.SavedImageData.ActionTransition lambda$createEditAction$2(Context context, Uri uri, boolean z, Resources resources) {
        ScreenshotController.SavedImageData.ActionTransition actionTransition = this.mSharedElementTransition.get();
        String string = context.getString(R$string.config_screenshotEditor);
        Intent intent = new Intent("android.intent.action.EDIT");
        if (!TextUtils.isEmpty(string)) {
            intent.setComponent(ComponentName.unflattenFromString(string));
        }
        intent.setDataAndType(uri, "image/png");
        intent.addFlags(1);
        intent.addFlags(2);
        intent.addFlags(268468224);
        actionTransition.action = new Notification.Action.Builder(Icon.createWithResource(resources, R$drawable.ic_screenshot_edit), resources.getString(17041485), PendingIntent.getBroadcastAsUser(context, this.mContext.getUserId(), new Intent(context, ActionProxyReceiver.class).putExtra("android:screenshot_action_intent", PendingIntent.getActivityAsUser(context, 0, intent, 67108864, actionTransition.bundle, UserHandle.CURRENT)).putExtra("android:screenshot_id", this.mScreenshotId).putExtra("android:smart_actions_enabled", z).putExtra("android:screenshot_override_transition", true).setAction("android.intent.action.EDIT").addFlags(268435456), 335544320, UserHandle.SYSTEM)).build();
        return actionTransition;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ ScreenshotController.SavedImageData.ActionTransition lambda$createShareAction$1(Uri uri, Context context, boolean z, Resources resources) {
        ScreenshotController.SavedImageData.ActionTransition actionTransition = this.mSharedElementTransition.get();
        Intent intent = new Intent("android.intent.action.SEND");
        intent.setDataAndType(uri, "image/png");
        intent.putExtra("android.intent.extra.STREAM", uri);
        intent.setClipData(new ClipData(new ClipDescription("content", new String[]{"text/plain"}), new ClipData.Item(uri)));
        intent.putExtra("android.intent.extra.SUBJECT", getSubjectString());
        intent.addFlags(1).addFlags(2);
        actionTransition.action = new Notification.Action.Builder(Icon.createWithResource(resources, R$drawable.ic_screenshot_share), resources.getString(17041530), PendingIntent.getBroadcastAsUser(context, context.getUserId(), new Intent(context, ActionProxyReceiver.class).putExtra("android:screenshot_action_intent", PendingIntent.getActivityAsUser(context, 0, Intent.createChooser(intent, null).addFlags(268468224).addFlags(1), 335544320, actionTransition.bundle, UserHandle.CURRENT)).putExtra("android:screenshot_disallow_enter_pip", true).putExtra("android:screenshot_id", this.mScreenshotId).putExtra("android:smart_actions_enabled", z).setAction("android.intent.action.SEND").addFlags(268435456), 335544320, UserHandle.SYSTEM)).build();
        return actionTransition;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ ScreenshotController.SavedImageData.ActionTransition lambda$createViewAction$0(Uri uri, Context context, boolean z, Resources resources) {
        ScreenshotController.SavedImageData.ActionTransition actionTransition = this.mSharedElementTransition.get();
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setDataAndType(uri, "image/png");
        intent.addFlags(1);
        intent.addFlags(268468224);
        actionTransition.action = new Notification.Action.Builder(Icon.createWithResource(resources, 17302844), resources.getString(17040425), PendingIntent.getBroadcastAsUser(context, this.mContext.getUserId(), new Intent(context, ActionProxyReceiver.class).putExtra("android:screenshot_action_intent", PendingIntent.getActivityAsUser(context, 0, intent, 67108864, actionTransition.bundle, UserHandle.CURRENT)).putExtra("android:screenshot_id", this.mScreenshotId).putExtra("android:smart_actions_enabled", z).putExtra("android:screenshot_override_transition", true).setAction("android.intent.action.VIEW").addFlags(268435456), 335544320, UserHandle.SYSTEM)).build();
        return actionTransition;
    }

    public final List<Notification.Action> buildSmartActions(List<Notification.Action> list, Context context) {
        ArrayList arrayList = new ArrayList();
        for (Notification.Action action : list) {
            Bundle extras = action.getExtras();
            String string = extras.getString(ScreenshotNotificationSmartActionsProvider.ACTION_TYPE, ScreenshotNotificationSmartActionsProvider.DEFAULT_ACTION_TYPE);
            Intent addFlags = new Intent(context, SmartActionsReceiver.class).putExtra("android:screenshot_action_intent", action.actionIntent).addFlags(268435456);
            addIntentExtras(this.mScreenshotId, addFlags, string, true);
            arrayList.add(new Notification.Action.Builder(action.getIcon(), action.title, PendingIntent.getBroadcast(context, this.mRandom.nextInt(), addFlags, 335544320)).setContextual(true).addExtras(extras).build());
        }
        return arrayList;
    }

    @VisibleForTesting
    public Notification.Action createDeleteAction(Context context, Resources resources, Uri uri, boolean z) {
        return new Notification.Action.Builder(Icon.createWithResource(resources, R$drawable.ic_screenshot_delete), resources.getString(17040157), PendingIntent.getBroadcast(context, this.mContext.getUserId(), new Intent(context, DeleteScreenshotReceiver.class).putExtra("android:screenshot_uri_id", uri.toString()).putExtra("android:screenshot_id", this.mScreenshotId).putExtra("android:smart_actions_enabled", z).addFlags(268435456), 1409286144)).build();
    }

    @VisibleForTesting
    public Supplier<ScreenshotController.SavedImageData.ActionTransition> createEditAction(final Context context, final Resources resources, final Uri uri, final boolean z) {
        return new Supplier() { // from class: com.android.systemui.screenshot.SaveImageInBackgroundTask$$ExternalSyntheticLambda1
            @Override // java.util.function.Supplier
            public final Object get() {
                ScreenshotController.SavedImageData.ActionTransition lambda$createEditAction$2;
                lambda$createEditAction$2 = SaveImageInBackgroundTask.this.lambda$createEditAction$2(context, uri, z, resources);
                return lambda$createEditAction$2;
            }
        };
    }

    @VisibleForTesting
    public Supplier<ScreenshotController.SavedImageData.ActionTransition> createShareAction(final Context context, final Resources resources, final Uri uri, final boolean z) {
        return new Supplier() { // from class: com.android.systemui.screenshot.SaveImageInBackgroundTask$$ExternalSyntheticLambda3
            @Override // java.util.function.Supplier
            public final Object get() {
                ScreenshotController.SavedImageData.ActionTransition lambda$createShareAction$1;
                lambda$createShareAction$1 = SaveImageInBackgroundTask.this.lambda$createShareAction$1(uri, context, z, resources);
                return lambda$createShareAction$1;
            }
        };
    }

    @VisibleForTesting
    public Supplier<ScreenshotController.SavedImageData.ActionTransition> createViewAction(final Context context, final Resources resources, final Uri uri, final boolean z) {
        return new Supplier() { // from class: com.android.systemui.screenshot.SaveImageInBackgroundTask$$ExternalSyntheticLambda2
            @Override // java.util.function.Supplier
            public final Object get() {
                ScreenshotController.SavedImageData.ActionTransition lambda$createViewAction$0;
                lambda$createViewAction$0 = SaveImageInBackgroundTask.this.lambda$createViewAction$0(uri, context, z, resources);
                return lambda$createViewAction$0;
            }
        };
    }

    /* JADX DEBUG: Method merged with bridge method */
    @Override // android.os.AsyncTask
    public Void doInBackground(String... strArr) {
        if (isCancelled()) {
            return null;
        }
        UUID randomUUID = UUID.randomUUID();
        FeatureFlags featureFlags = this.mFlags;
        UnreleasedFlag unreleasedFlag = Flags.SCREENSHOT_WORK_PROFILE_POLICY;
        UserHandle userHandleOfForegroundApplication = featureFlags.isEnabled(unreleasedFlag) ? this.mParams.owner : getUserHandleOfForegroundApplication(this.mContext);
        Thread.currentThread().setPriority(10);
        Bitmap bitmap = this.mParams.image;
        this.mScreenshotId = String.format("Screenshot_%s", randomUUID);
        boolean z = !(this.mFlags.isEnabled(unreleasedFlag) && userHandleOfForegroundApplication != Process.myUserHandle()) && DeviceConfig.getBoolean("systemui", "enable_screenshot_notification_smart_actions", true);
        if (z) {
            try {
                if (this.mParams.mQuickShareActionsReadyListener != null) {
                    queryQuickShareAction(bitmap, userHandleOfForegroundApplication);
                }
            } catch (Exception e) {
                this.mParams.clearImage();
                this.mImageData.reset();
                this.mQuickShareData.reset();
                this.mParams.mActionsReadyListener.onActionsReady(this.mImageData);
                this.mParams.finisher.accept(null);
                return null;
            }
        }
        ImageExporter.Result result = (ImageExporter.Result) this.mImageExporter.export(new SaveImageInBackgroundTask$$ExternalSyntheticLambda0(), randomUUID, bitmap, this.mParams.owner, strArr != null ? strArr[0] : null).get();
        String str = TAG;
        Log.d(str, "Saved screenshot: " + result);
        Uri uri = result.uri;
        this.mImageTime = result.timestamp;
        ScreenshotSmartActions screenshotSmartActions = this.mScreenshotSmartActions;
        String str2 = this.mScreenshotId;
        ScreenshotNotificationSmartActionsProvider screenshotNotificationSmartActionsProvider = this.mSmartActionsProvider;
        ScreenshotNotificationSmartActionsProvider.ScreenshotSmartActionType screenshotSmartActionType = ScreenshotNotificationSmartActionsProvider.ScreenshotSmartActionType.REGULAR_SMART_ACTIONS;
        CompletableFuture<List<Notification.Action>> smartActionsFuture = screenshotSmartActions.getSmartActionsFuture(str2, uri, bitmap, screenshotNotificationSmartActionsProvider, screenshotSmartActionType, z, userHandleOfForegroundApplication);
        ArrayList arrayList = new ArrayList();
        if (z) {
            arrayList.addAll(buildSmartActions(this.mScreenshotSmartActions.getSmartActions(this.mScreenshotId, smartActionsFuture, DeviceConfig.getInt("systemui", "screenshot_notification_smart_actions_timeout_ms", 1000), this.mSmartActionsProvider, screenshotSmartActionType), this.mContext));
        }
        ScreenshotController.SavedImageData savedImageData = this.mImageData;
        savedImageData.uri = uri;
        savedImageData.owner = userHandleOfForegroundApplication;
        savedImageData.smartActions = arrayList;
        Context context = this.mContext;
        savedImageData.viewTransition = createViewAction(context, context.getResources(), uri, z);
        ScreenshotController.SavedImageData savedImageData2 = this.mImageData;
        Context context2 = this.mContext;
        savedImageData2.shareTransition = createShareAction(context2, context2.getResources(), uri, z);
        ScreenshotController.SavedImageData savedImageData3 = this.mImageData;
        Context context3 = this.mContext;
        savedImageData3.editTransition = createEditAction(context3, context3.getResources(), uri, z);
        ScreenshotController.SavedImageData savedImageData4 = this.mImageData;
        Context context4 = this.mContext;
        savedImageData4.deleteAction = createDeleteAction(context4, context4.getResources(), uri, z);
        this.mImageData.quickShareAction = createQuickShareAction(this.mContext, this.mQuickShareData.quickShareAction, uri);
        this.mImageData.subject = getSubjectString();
        this.mParams.mActionsReadyListener.onActionsReady(this.mImageData);
        this.mParams.finisher.accept(this.mImageData.uri);
        this.mParams.image = null;
        return null;
    }

    public final String getSubjectString() {
        return String.format("Screenshot (%s)", DateFormat.getDateTimeInstance().format(new Date(this.mImageTime)));
    }

    public final UserHandle getUserHandleOfForegroundApplication(Context context) {
        int userId;
        UserManager userManager = UserManager.get(context);
        try {
            userId = ActivityTaskManager.getService().getLastResumedActivityUserId();
        } catch (RemoteException e) {
            userId = context.getUserId();
        }
        return userManager.getUserInfo(userId).getUserHandle();
    }

    /* JADX DEBUG: Method merged with bridge method */
    @Override // android.os.AsyncTask
    public void onCancelled(Void r4) {
        this.mImageData.reset();
        this.mQuickShareData.reset();
        this.mParams.mActionsReadyListener.onActionsReady(this.mImageData);
        this.mParams.finisher.accept(null);
        this.mParams.clearImage();
    }

    public final void queryQuickShareAction(Bitmap bitmap, UserHandle userHandle) {
        ScreenshotSmartActions screenshotSmartActions = this.mScreenshotSmartActions;
        String str = this.mScreenshotId;
        ScreenshotNotificationSmartActionsProvider screenshotNotificationSmartActionsProvider = this.mSmartActionsProvider;
        ScreenshotNotificationSmartActionsProvider.ScreenshotSmartActionType screenshotSmartActionType = ScreenshotNotificationSmartActionsProvider.ScreenshotSmartActionType.QUICK_SHARE_ACTION;
        List<Notification.Action> smartActions = this.mScreenshotSmartActions.getSmartActions(this.mScreenshotId, screenshotSmartActions.getSmartActionsFuture(str, null, bitmap, screenshotNotificationSmartActionsProvider, screenshotSmartActionType, true, userHandle), DeviceConfig.getInt("systemui", "screenshot_notification_quick_share_actions_timeout_ms", 500), this.mSmartActionsProvider, screenshotSmartActionType);
        if (smartActions.isEmpty()) {
            return;
        }
        this.mQuickShareData.quickShareAction = smartActions.get(0);
        this.mParams.mQuickShareActionsReadyListener.onActionsReady(this.mQuickShareData);
    }

    public void setActionsReadyListener(ScreenshotController.ActionsReadyListener actionsReadyListener) {
        this.mParams.mActionsReadyListener = actionsReadyListener;
    }
}