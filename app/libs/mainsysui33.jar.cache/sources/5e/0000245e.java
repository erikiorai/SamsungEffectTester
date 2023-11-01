package com.android.systemui.screenshot;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.ActivityManager;
import android.app.ActivityOptions;
import android.app.ActivityTaskManager;
import android.app.ExitTransitionCoordinator;
import android.app.ICompatCameraControlCallback;
import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Insets;
import android.graphics.Rect;
import android.hardware.display.DisplayManager;
import android.media.AudioAttributes;
import android.media.AudioSystem;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Pair;
import android.view.Display;
import android.view.IRemoteAnimationFinishedCallback;
import android.view.IRemoteAnimationRunner;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.RemoteAnimationAdapter;
import android.view.RemoteAnimationTarget;
import android.view.ScrollCaptureResponse;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewRootImpl;
import android.view.ViewTreeObserver;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.WindowManagerGlobal;
import android.view.accessibility.AccessibilityManager;
import android.widget.Toast;
import android.window.OnBackInvokedCallback;
import android.window.WindowContext;
import androidx.concurrent.futures.CallbackToFutureAdapter;
import com.android.internal.logging.UiEventLogger;
import com.android.internal.policy.PhoneWindow;
import com.android.internal.statusbar.IStatusBarService;
import com.android.settingslib.applications.InterestingConfigChanges;
import com.android.systemui.R$layout;
import com.android.systemui.R$string;
import com.android.systemui.broadcast.BroadcastSender;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.Flags;
import com.android.systemui.screenshot.ScreenshotController;
import com.android.systemui.screenshot.ScreenshotView;
import com.android.systemui.screenshot.ScrollCaptureController;
import com.android.systemui.screenshot.TakeScreenshotService;
import com.android.systemui.shared.system.TaskStackChangeListener;
import com.android.systemui.shared.system.TaskStackChangeListeners;
import com.android.systemui.util.Assert;
import com.google.common.util.concurrent.ListenableFuture;
import java.io.File;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Consumer;
import java.util.function.Supplier;

/* loaded from: mainsysui33.jar:com/android/systemui/screenshot/ScreenshotController.class */
public class ScreenshotController {
    public final AccessibilityManager mAccessibilityManager;
    public final ActionIntentExecutor mActionExecutor;
    public final ExecutorService mBgExecutor;
    public boolean mBlockAttach;
    public final BroadcastSender mBroadcastSender;
    public final ListenableFuture<MediaPlayer> mCameraSound;
    public final InterestingConfigChanges mConfigChanges;
    public final WindowContext mContext;
    public BroadcastReceiver mCopyBroadcastReceiver;
    public TakeScreenshotService.RequestCallback mCurrentRequestCallback;
    public final DisplayManager mDisplayManager;
    public final FeatureFlags mFlags;
    public final ImageCapture mImageCapture;
    public final ImageExporter mImageExporter;
    public final boolean mIsLowRamDevice;
    public ListenableFuture<ScrollCaptureResponse> mLastScrollCaptureRequest;
    public ScrollCaptureResponse mLastScrollCaptureResponse;
    public ListenableFuture<ScrollCaptureController.LongScreenshot> mLongScreenshotFuture;
    public final LongScreenshotData mLongScreenshotHolder;
    public final Executor mMainExecutor;
    public final ScreenshotNotificationsController mNotificationsController;
    public PackageManager mPm;
    public SaveImageInBackgroundTask mSaveInBgTask;
    public Bitmap mScreenBitmap;
    public Animator mScreenshotAnimation;
    public final TimeoutHandler mScreenshotHandler;
    public final ScreenshotNotificationSmartActionsProvider mScreenshotNotificationSmartActionsProvider;
    public final ScreenshotSmartActions mScreenshotSmartActions;
    public boolean mScreenshotTakenInPortrait;
    public ScreenshotView mScreenshotView;
    public final ScrollCaptureClient mScrollCaptureClient;
    public final ScrollCaptureController mScrollCaptureController;
    public final IStatusBarService mStatusBarService;
    public ComponentName mTaskComponentName;
    public final TaskStackChangeListener mTaskListener;
    public final UiEventLogger mUiEventLogger;
    public final UserManager mUserManager;
    public final PhoneWindow mWindow;
    public final WindowManager.LayoutParams mWindowLayoutParams;
    public final WindowManager mWindowManager;
    public static final String TAG = LogConfig.logTag(ScreenshotController.class);
    public static final IRemoteAnimationRunner.Stub SCREENSHOT_REMOTE_RUNNER = new IRemoteAnimationRunner.Stub() { // from class: com.android.systemui.screenshot.ScreenshotController.1
        public void onAnimationCancelled(boolean z) {
        }

        public void onAnimationStart(int i, RemoteAnimationTarget[] remoteAnimationTargetArr, RemoteAnimationTarget[] remoteAnimationTargetArr2, RemoteAnimationTarget[] remoteAnimationTargetArr3, IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback) {
            try {
                iRemoteAnimationFinishedCallback.onAnimationFinished();
            } catch (RemoteException e) {
                Log.e(ScreenshotController.TAG, "Error finishing screenshot remote animation", e);
            }
        }
    };
    public final OnBackInvokedCallback mOnBackInvokedCallback = new OnBackInvokedCallback() { // from class: com.android.systemui.screenshot.ScreenshotController$$ExternalSyntheticLambda1
        public final void onBackInvoked() {
            ScreenshotController.$r8$lambda$MrGvEfVcsxCiUeEVAVoOzvbu4Zk(ScreenshotController.this);
        }
    };
    public final FullScreenshotRunnable mFullScreenshotRunnable = new FullScreenshotRunnable();
    public String mPackageName = "";

    /* renamed from: com.android.systemui.screenshot.ScreenshotController$2 */
    /* loaded from: mainsysui33.jar:com/android/systemui/screenshot/ScreenshotController$2.class */
    public class AnonymousClass2 implements TaskStackChangeListener {
        public AnonymousClass2() {
            ScreenshotController.this = r4;
        }

        public /* synthetic */ void lambda$onTaskStackChanged$0() {
            ScreenshotController.this.updateForegroundTaskSync();
        }

        public void onTaskStackChanged() {
            ScreenshotController.this.mBgExecutor.execute(new Runnable() { // from class: com.android.systemui.screenshot.ScreenshotController$2$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    ScreenshotController.AnonymousClass2.this.lambda$onTaskStackChanged$0();
                }
            });
        }
    }

    /* renamed from: com.android.systemui.screenshot.ScreenshotController$6 */
    /* loaded from: mainsysui33.jar:com/android/systemui/screenshot/ScreenshotController$6.class */
    public class AnonymousClass6 implements ViewRootImpl.ActivityConfigCallback {
        public final /* synthetic */ UserHandle val$owner;

        public AnonymousClass6(UserHandle userHandle) {
            ScreenshotController.this = r4;
            this.val$owner = userHandle;
        }

        public /* synthetic */ void lambda$onConfigurationChanged$0(UserHandle userHandle) {
            ScreenshotController.this.requestScrollCapture(userHandle);
        }

        public void onConfigurationChanged(Configuration configuration, int i) {
            if (ScreenshotController.this.mConfigChanges.applyNewConfig(ScreenshotController.this.mContext.getResources())) {
                ScreenshotController.this.mScreenshotView.hideScrollChip();
                TimeoutHandler timeoutHandler = ScreenshotController.this.mScreenshotHandler;
                final UserHandle userHandle = this.val$owner;
                timeoutHandler.postDelayed(new Runnable() { // from class: com.android.systemui.screenshot.ScreenshotController$6$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        ScreenshotController.AnonymousClass6.this.lambda$onConfigurationChanged$0(userHandle);
                    }
                }, 150L);
                ScreenshotController.this.mScreenshotView.updateInsets(ScreenshotController.this.mWindowManager.getCurrentWindowMetrics().getWindowInsets());
                if (ScreenshotController.this.mScreenshotAnimation == null || !ScreenshotController.this.mScreenshotAnimation.isRunning()) {
                    return;
                }
                ScreenshotController.this.mScreenshotAnimation.end();
            }
        }

        public void requestCompatCameraControl(boolean z, boolean z2, ICompatCameraControlCallback iCompatCameraControlCallback) {
            Log.w(ScreenshotController.TAG, "Unexpected requestCompatCameraControl callback");
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/screenshot/ScreenshotController$ActionsReadyListener.class */
    public interface ActionsReadyListener {
        void onActionsReady(SavedImageData savedImageData);
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/screenshot/ScreenshotController$FullScreenshotRunnable.class */
    public class FullScreenshotRunnable implements Runnable {
        public Consumer<Uri> mFinisher;
        public TakeScreenshotService.RequestCallback mRequestCallback;
        public ComponentName mTopComponent;

        public FullScreenshotRunnable() {
            ScreenshotController.this = r4;
        }

        @Override // java.lang.Runnable
        public void run() {
            ScreenshotController.this.takeScreenshotFullscreenInternal(this.mTopComponent, this.mFinisher, this.mRequestCallback);
        }

        public void setArgs(ComponentName componentName, Consumer<Uri> consumer, TakeScreenshotService.RequestCallback requestCallback) {
            this.mTopComponent = componentName;
            this.mFinisher = consumer;
            this.mRequestCallback = requestCallback;
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/screenshot/ScreenshotController$QuickShareActionReadyListener.class */
    public interface QuickShareActionReadyListener {
        void onActionsReady(QuickShareData quickShareData);
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/screenshot/ScreenshotController$QuickShareData.class */
    public static class QuickShareData {
        public Notification.Action quickShareAction;

        public void reset() {
            this.quickShareAction = null;
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/screenshot/ScreenshotController$SaveImageInBackgroundData.class */
    public static class SaveImageInBackgroundData {
        public Consumer<Uri> finisher;
        public Bitmap image;
        public ActionsReadyListener mActionsReadyListener;
        public QuickShareActionReadyListener mQuickShareActionsReadyListener;
        public UserHandle owner;

        public void clearImage() {
            this.image = null;
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/screenshot/ScreenshotController$SavedImageData.class */
    public static class SavedImageData {
        public Notification.Action deleteAction;
        public Supplier<ActionTransition> editTransition;
        public UserHandle owner;
        public Notification.Action quickShareAction;
        public Supplier<ActionTransition> shareTransition;
        public List<Notification.Action> smartActions;
        public String subject;
        public Uri uri;
        public Supplier<ActionTransition> viewTransition;

        /* loaded from: mainsysui33.jar:com/android/systemui/screenshot/ScreenshotController$SavedImageData$ActionTransition.class */
        public static class ActionTransition {
            public Notification.Action action;
            public Bundle bundle;
            public Runnable onCancelRunnable;
        }

        public void reset() {
            this.uri = null;
            this.shareTransition = null;
            this.editTransition = null;
            this.deleteAction = null;
            this.smartActions = null;
            this.quickShareAction = null;
            this.subject = null;
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/screenshot/ScreenshotController$ScreenshotExitTransitionCallbacksSupplier.class */
    public class ScreenshotExitTransitionCallbacksSupplier implements Supplier<ExitTransitionCoordinator.ExitTransitionCallbacks> {
        public final boolean mDismissOnHideSharedElements;

        public ScreenshotExitTransitionCallbacksSupplier(boolean z) {
            ScreenshotController.this = r4;
            this.mDismissOnHideSharedElements = z;
        }

        @Override // java.util.function.Supplier
        public ExitTransitionCoordinator.ExitTransitionCallbacks get() {
            return new ExitTransitionCoordinator.ExitTransitionCallbacks() { // from class: com.android.systemui.screenshot.ScreenshotController.ScreenshotExitTransitionCallbacksSupplier.1
                {
                    ScreenshotExitTransitionCallbacksSupplier.this = this;
                }

                public void hideSharedElements() {
                    ScreenshotExitTransitionCallbacksSupplier screenshotExitTransitionCallbacksSupplier = ScreenshotExitTransitionCallbacksSupplier.this;
                    if (screenshotExitTransitionCallbacksSupplier.mDismissOnHideSharedElements) {
                        ScreenshotController.this.finishDismiss();
                    }
                }

                public boolean isReturnTransitionAllowed() {
                    return false;
                }

                public void onFinish() {
                }
            };
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/screenshot/ScreenshotController$TransitionDestination.class */
    public interface TransitionDestination {
        void setTransitionDestination(Rect rect, Runnable runnable);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.screenshot.ScreenshotController$$ExternalSyntheticLambda17.run():void] */
    public static /* synthetic */ void $r8$lambda$5q2e2wcD3rdTFVIqOi515iEjd6w(ScreenshotController screenshotController) {
        screenshotController.lambda$saveScreenshotAndToast$14();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.screenshot.ScreenshotController$$ExternalSyntheticLambda2.run():void] */
    /* renamed from: $r8$lambda$CWK89jW7LkbQDqDbQsJ8H-fqC6M */
    public static /* synthetic */ void m4258$r8$lambda$CWK89jW7LkbQDqDbQsJ8HfqC6M(ScreenshotController screenshotController) {
        screenshotController.lambda$new$1();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.screenshot.ScreenshotController$$ExternalSyntheticLambda21.run():void] */
    public static /* synthetic */ void $r8$lambda$JXyiCptiU0BdybYLspo49kppWoA(ScreenshotController screenshotController, ScrollCaptureResponse scrollCaptureResponse, UserHandle userHandle) {
        screenshotController.lambda$onScrollCaptureResponseReady$9(scrollCaptureResponse, userHandle);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.screenshot.ScreenshotController$$ExternalSyntheticLambda1.onBackInvoked():void] */
    public static /* synthetic */ void $r8$lambda$MrGvEfVcsxCiUeEVAVoOzvbu4Zk(ScreenshotController screenshotController) {
        screenshotController.lambda$new$0();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.screenshot.ScreenshotController$$ExternalSyntheticLambda10.onApplyWindowInsets(android.view.View, android.view.WindowInsets):android.view.WindowInsets] */
    /* renamed from: $r8$lambda$NcleYra3ctma4jh-G9B0nzRrxpM */
    public static /* synthetic */ WindowInsets m4260$r8$lambda$NcleYra3ctma4jhG9B0nzRrxpM(View view, WindowInsets windowInsets) {
        return lambda$saveScreenshot$5(view, windowInsets);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.screenshot.ScreenshotController$$ExternalSyntheticLambda15.run():void] */
    public static /* synthetic */ void $r8$lambda$QmLpxeHDcrHYawDtZbebKztzJ5E(ScreenshotController screenshotController, QuickShareData quickShareData) {
        screenshotController.lambda$showUiOnQuickShareActionReady$17(quickShareData);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.screenshot.ScreenshotController$$ExternalSyntheticLambda22.run():void] */
    public static /* synthetic */ void $r8$lambda$TG3lTIiv5MZe6piEghUrjOcMsWM(ScreenshotController screenshotController, ScrollCaptureResponse scrollCaptureResponse, UserHandle userHandle) {
        screenshotController.lambda$onScrollCaptureResponseReady$8(scrollCaptureResponse, userHandle);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.screenshot.ScreenshotController$$ExternalSyntheticLambda19.get():java.lang.Object] */
    public static /* synthetic */ SavedImageData.ActionTransition $r8$lambda$VRfQ2HNRVyPAb3ag1rgyCbGb8Oc(ScreenshotController screenshotController) {
        return screenshotController.lambda$getActionTransitionSupplier$19();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.screenshot.ScreenshotController$$ExternalSyntheticLambda20.run():void] */
    public static /* synthetic */ void $r8$lambda$YMKaI8i5EYuu56JBGI102Wp5YNE(ScreenshotController screenshotController) {
        screenshotController.lambda$getActionTransitionSupplier$18();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.screenshot.ScreenshotController$$ExternalSyntheticLambda16.run():void] */
    public static /* synthetic */ void $r8$lambda$_78myrYqphlTMCQadQbGMiJivu4(ScreenshotController screenshotController) {
        screenshotController.lambda$playCameraSound$13();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.screenshot.ScreenshotController$$ExternalSyntheticLambda11.run():void] */
    /* renamed from: $r8$lambda$bzaiUvEg-f79hAAj1DYUcGOzf3M */
    public static /* synthetic */ void m4263$r8$lambda$bzaiUvEgf79hAAj1DYUcGOzf3M(ScreenshotController screenshotController, SavedImageData savedImageData) {
        screenshotController.lambda$showUiOnActionsReady$16(savedImageData);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.screenshot.ScreenshotController$$ExternalSyntheticLambda0.run():void] */
    public static /* synthetic */ void $r8$lambda$jM4w0qiXe2Cwbxhq2rXmgTojbLA(ScreenshotController screenshotController, UserHandle userHandle) {
        screenshotController.lambda$runBatchScrollCapture$10(userHandle);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.screenshot.ScreenshotController$$ExternalSyntheticLambda18.run():void] */
    public static /* synthetic */ void $r8$lambda$pmPDoY6o1TfyBVPrsjk0WXuuNSI(ScreenshotController screenshotController, ListenableFuture listenableFuture, UserHandle userHandle) {
        screenshotController.lambda$requestScrollCapture$6(listenableFuture, userHandle);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.screenshot.ScreenshotController$$ExternalSyntheticLambda13.onActionsReady(com.android.systemui.screenshot.ScreenshotController$SavedImageData):void] */
    /* renamed from: $r8$lambda$sn-O_r5MhZTGhZlqz5awgtLtL2Y */
    public static /* synthetic */ void m4266$r8$lambda$snO_r5MhZTGhZlqz5awgtLtL2Y(ScreenshotController screenshotController, Consumer consumer, SavedImageData savedImageData) {
        screenshotController.lambda$saveScreenshotAndToast$15(consumer, savedImageData);
    }

    public ScreenshotController(Context context, FeatureFlags featureFlags, ScreenshotSmartActions screenshotSmartActions, ScreenshotNotificationsController screenshotNotificationsController, ScrollCaptureClient scrollCaptureClient, UiEventLogger uiEventLogger, ImageExporter imageExporter, ImageCapture imageCapture, Executor executor, ScrollCaptureController scrollCaptureController, IStatusBarService iStatusBarService, LongScreenshotData longScreenshotData, ActivityManager activityManager, TimeoutHandler timeoutHandler, BroadcastSender broadcastSender, ScreenshotNotificationSmartActionsProvider screenshotNotificationSmartActionsProvider, ActionIntentExecutor actionIntentExecutor, UserManager userManager) {
        InterestingConfigChanges interestingConfigChanges = new InterestingConfigChanges(-2147474556);
        this.mConfigChanges = interestingConfigChanges;
        AnonymousClass2 anonymousClass2 = new AnonymousClass2();
        this.mTaskListener = anonymousClass2;
        this.mScreenshotSmartActions = screenshotSmartActions;
        this.mNotificationsController = screenshotNotificationsController;
        this.mScrollCaptureClient = scrollCaptureClient;
        this.mUiEventLogger = uiEventLogger;
        this.mImageExporter = imageExporter;
        this.mImageCapture = imageCapture;
        this.mMainExecutor = executor;
        this.mScrollCaptureController = scrollCaptureController;
        this.mStatusBarService = iStatusBarService;
        this.mLongScreenshotHolder = longScreenshotData;
        this.mIsLowRamDevice = activityManager.isLowRamDevice();
        this.mScreenshotNotificationSmartActionsProvider = screenshotNotificationSmartActionsProvider;
        this.mBgExecutor = Executors.newSingleThreadExecutor();
        this.mBroadcastSender = broadcastSender;
        this.mScreenshotHandler = timeoutHandler;
        timeoutHandler.setDefaultTimeoutMillis(6000);
        timeoutHandler.setOnTimeoutRunnable(new Runnable() { // from class: com.android.systemui.screenshot.ScreenshotController$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                ScreenshotController.m4258$r8$lambda$CWK89jW7LkbQDqDbQsJ8HfqC6M(ScreenshotController.this);
            }
        });
        DisplayManager displayManager = (DisplayManager) context.getSystemService(DisplayManager.class);
        Objects.requireNonNull(displayManager);
        this.mDisplayManager = displayManager;
        WindowContext createWindowContext = context.createDisplayContext(getDefaultDisplay()).createWindowContext(2036, null);
        this.mContext = createWindowContext;
        WindowManager windowManager = (WindowManager) createWindowContext.getSystemService(WindowManager.class);
        this.mWindowManager = windowManager;
        this.mFlags = featureFlags;
        this.mActionExecutor = actionIntentExecutor;
        this.mUserManager = userManager;
        this.mAccessibilityManager = AccessibilityManager.getInstance(createWindowContext);
        WindowManager.LayoutParams floatingWindowParams = FloatingWindowUtil.getFloatingWindowParams();
        this.mWindowLayoutParams = floatingWindowParams;
        floatingWindowParams.setTitle("ScreenshotAnimation");
        PhoneWindow floatingWindow = FloatingWindowUtil.getFloatingWindow(createWindowContext);
        this.mWindow = floatingWindow;
        floatingWindow.setWindowManager(windowManager, (IBinder) null, (String) null);
        interestingConfigChanges.applyNewConfig(context.getResources());
        reloadAssets();
        this.mCameraSound = loadCameraSound();
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() { // from class: com.android.systemui.screenshot.ScreenshotController.3
            {
                ScreenshotController.this = this;
            }

            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                if ("com.android.systemui.COPY".equals(intent.getAction())) {
                    ScreenshotController.this.dismissScreenshot(ScreenshotEvent.SCREENSHOT_DISMISSED_OTHER);
                }
            }
        };
        this.mCopyBroadcastReceiver = broadcastReceiver;
        createWindowContext.registerReceiver(broadcastReceiver, new IntentFilter("com.android.systemui.COPY"), "com.android.systemui.permission.SELF", (Handler) null, 4);
        this.mPm = createWindowContext.getPackageManager();
        TaskStackChangeListeners.getInstance().registerTaskStackListener(anonymousClass2);
        updateForegroundTaskSync();
    }

    public static boolean aspectRatiosMatch(Bitmap bitmap, Insets insets, Rect rect) {
        int width = (bitmap.getWidth() - insets.left) - insets.right;
        int height = (bitmap.getHeight() - insets.top) - insets.bottom;
        boolean z = false;
        if (height != 0) {
            z = false;
            if (width != 0) {
                z = false;
                if (bitmap.getWidth() != 0) {
                    if (bitmap.getHeight() == 0) {
                        z = false;
                    } else {
                        z = false;
                        if (Math.abs((width / height) - (rect.width() / rect.height())) < 0.1f) {
                            z = true;
                        }
                    }
                }
            }
        }
        return z;
    }

    public /* synthetic */ void lambda$getActionTransitionSupplier$18() {
        ActivityOptions.stopSharedElementAnimation(this.mWindow);
    }

    public /* synthetic */ SavedImageData.ActionTransition lambda$getActionTransitionSupplier$19() {
        Pair startSharedElementAnimation = ActivityOptions.startSharedElementAnimation(this.mWindow, new ScreenshotExitTransitionCallbacksSupplier(true).get(), null, new Pair[]{Pair.create(this.mScreenshotView.getScreenshotPreview(), "screenshot_preview_image")});
        ((ExitTransitionCoordinator) startSharedElementAnimation.second).startExit();
        SavedImageData.ActionTransition actionTransition = new SavedImageData.ActionTransition();
        actionTransition.bundle = ((ActivityOptions) startSharedElementAnimation.first).toBundle();
        actionTransition.onCancelRunnable = new Runnable() { // from class: com.android.systemui.screenshot.ScreenshotController$$ExternalSyntheticLambda20
            @Override // java.lang.Runnable
            public final void run() {
                ScreenshotController.$r8$lambda$YMKaI8i5EYuu56JBGI102Wp5YNE(ScreenshotController.this);
            }
        };
        return actionTransition;
    }

    public /* synthetic */ void lambda$loadCameraSound$11(CallbackToFutureAdapter.Completer completer) {
        try {
            completer.set(MediaPlayer.create(this.mContext, Uri.fromFile(new File(this.mContext.getResources().getString(17039909))), null, new AudioAttributes.Builder().setUsage(13).setContentType(4).build(), AudioSystem.newAudioSessionId()));
        } catch (IllegalStateException e) {
            Log.w(TAG, "Screenshot sound initialization failed", e);
            completer.set(null);
        }
    }

    public /* synthetic */ Object lambda$loadCameraSound$12(final CallbackToFutureAdapter.Completer completer) throws Exception {
        this.mBgExecutor.execute(new Runnable() { // from class: com.android.systemui.screenshot.ScreenshotController$$ExternalSyntheticLambda14
            @Override // java.lang.Runnable
            public final void run() {
                ScreenshotController.this.lambda$loadCameraSound$11(completer);
            }
        });
        return "ScreenshotController#loadCameraSound";
    }

    public /* synthetic */ void lambda$new$1() {
        dismissScreenshot(ScreenshotEvent.SCREENSHOT_INTERACTION_TIMEOUT);
    }

    public /* synthetic */ void lambda$onScrollCaptureResponseReady$9(final ScrollCaptureResponse scrollCaptureResponse, final UserHandle userHandle) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getDefaultDisplay().getRealMetrics(displayMetrics);
        this.mScreenshotView.prepareScrollingTransition(scrollCaptureResponse, this.mScreenBitmap, this.mImageCapture.captureDisplay(0, new Rect(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels)), this.mScreenshotTakenInPortrait);
        this.mScreenshotView.post(new Runnable() { // from class: com.android.systemui.screenshot.ScreenshotController$$ExternalSyntheticLambda22
            @Override // java.lang.Runnable
            public final void run() {
                ScreenshotController.$r8$lambda$TG3lTIiv5MZe6piEghUrjOcMsWM(ScreenshotController.this, scrollCaptureResponse, userHandle);
            }
        });
    }

    public /* synthetic */ void lambda$playCameraSound$13() {
        try {
            MediaPlayer mediaPlayer = (MediaPlayer) this.mCameraSound.get();
            if (mediaPlayer != null) {
                mediaPlayer.start();
            }
        } catch (InterruptedException | ExecutionException e) {
        }
    }

    public /* synthetic */ boolean lambda$reloadAssets$2(View view, int i, KeyEvent keyEvent) {
        if (i == 4) {
            lambda$new$0();
            return true;
        }
        return false;
    }

    public /* synthetic */ void lambda$runBatchScrollCapture$10(UserHandle userHandle) {
        try {
            ScrollCaptureController.LongScreenshot longScreenshot = (ScrollCaptureController.LongScreenshot) this.mLongScreenshotFuture.get();
            if (longScreenshot.getHeight() == 0) {
                this.mScreenshotView.restoreNonScrollingUi();
                return;
            }
            this.mLongScreenshotHolder.setNeedsMagnification(true);
            startLongScreenshotActivity(longScreenshot, userHandle);
        } catch (InterruptedException | ExecutionException e) {
            Log.e(TAG, "Exception", e);
            this.mScreenshotView.restoreNonScrollingUi();
        } catch (CancellationException e2) {
            Log.e(TAG, "Long screenshot cancelled");
        }
    }

    public /* synthetic */ void lambda$saveScreenshot$3(UserHandle userHandle) {
        if (this.mFlags.isEnabled(Flags.SCREENSHOT_WORK_PROFILE_POLICY) && this.mUserManager.isManagedProfile(userHandle.getIdentifier())) {
            this.mScreenshotView.announceForAccessibility(this.mContext.getResources().getString(R$string.screenshot_saving_work_profile_title));
        } else {
            this.mScreenshotView.announceForAccessibility(this.mContext.getResources().getString(R$string.screenshot_saving_title));
        }
    }

    public /* synthetic */ void lambda$saveScreenshot$4(UserHandle userHandle) {
        requestScrollCapture(userHandle);
        this.mWindow.peekDecorView().getViewRootImpl().setActivityConfigCallback(new AnonymousClass6(userHandle));
    }

    public static /* synthetic */ WindowInsets lambda$saveScreenshot$5(View view, WindowInsets windowInsets) {
        return WindowInsets.CONSUMED;
    }

    public /* synthetic */ void lambda$saveScreenshotAndToast$14() {
        Toast.makeText((Context) this.mContext, R$string.screenshot_saved_title, 0).show();
    }

    public /* synthetic */ void lambda$saveScreenshotAndToast$15(Consumer consumer, SavedImageData savedImageData) {
        consumer.accept(savedImageData.uri);
        if (savedImageData.uri == null) {
            this.mUiEventLogger.log(ScreenshotEvent.SCREENSHOT_NOT_SAVED, 0, this.mPackageName);
            this.mNotificationsController.notifyScreenshotError(R$string.screenshot_failed_to_save_text);
            return;
        }
        this.mUiEventLogger.log(ScreenshotEvent.SCREENSHOT_SAVED, 0, this.mPackageName);
        this.mScreenshotHandler.post(new Runnable() { // from class: com.android.systemui.screenshot.ScreenshotController$$ExternalSyntheticLambda17
            @Override // java.lang.Runnable
            public final void run() {
                ScreenshotController.$r8$lambda$5q2e2wcD3rdTFVIqOi515iEjd6w(ScreenshotController.this);
            }
        });
    }

    public /* synthetic */ void lambda$showUiOnActionsReady$16(final SavedImageData savedImageData) {
        Animator animator = this.mScreenshotAnimation;
        if (animator == null || !animator.isRunning()) {
            doPostAnimation(savedImageData);
        } else {
            this.mScreenshotAnimation.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.screenshot.ScreenshotController.9
                {
                    ScreenshotController.this = this;
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator2) {
                    super.onAnimationEnd(animator2);
                    ScreenshotController.this.doPostAnimation(savedImageData);
                }
            });
        }
    }

    public /* synthetic */ void lambda$showUiOnQuickShareActionReady$17(final QuickShareData quickShareData) {
        Animator animator = this.mScreenshotAnimation;
        if (animator == null || !animator.isRunning()) {
            this.mScreenshotView.addQuickShareChip(quickShareData.quickShareAction);
        } else {
            this.mScreenshotAnimation.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.screenshot.ScreenshotController.10
                {
                    ScreenshotController.this = this;
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator2) {
                    super.onAnimationEnd(animator2);
                    ScreenshotController.this.mScreenshotView.addQuickShareChip(quickShareData.quickShareAction);
                }
            });
        }
    }

    public /* synthetic */ void lambda$startLongScreenshotActivity$7(ScrollCaptureController.LongScreenshot longScreenshot, Rect rect, Runnable runnable) {
        this.mScreenshotView.startLongScreenshotTransition(rect, runnable, longScreenshot);
        this.mContext.sendBroadcast(new Intent("android.intent.action.CLOSE_SYSTEM_DIALOGS"));
    }

    public final boolean allowLongScreenshots() {
        return !this.mIsLowRamDevice;
    }

    public final void attachWindow() {
        View decorView = this.mWindow.getDecorView();
        if (decorView.isAttachedToWindow() || this.mBlockAttach) {
            return;
        }
        this.mBlockAttach = true;
        this.mWindowManager.addView(decorView, this.mWindowLayoutParams);
        decorView.requestApplyInsets();
    }

    public final Bitmap captureScreenshot() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getDefaultDisplay().getRealMetrics(displayMetrics);
        return this.mImageCapture.captureDisplay(0, new Rect(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels));
    }

    public void dismissScreenshot(ScreenshotEvent screenshotEvent) {
        if (this.mScreenshotView.isDismissing()) {
            return;
        }
        this.mUiEventLogger.log(screenshotEvent, 0, this.mPackageName);
        this.mScreenshotHandler.cancelTimeout();
        this.mScreenshotView.animateDismissal();
    }

    public final void doPostAnimation(SavedImageData savedImageData) {
        this.mScreenshotView.setChipIntents(savedImageData);
        if (this.mFlags.isEnabled(Flags.SCREENSHOT_WORK_PROFILE_POLICY) && this.mUserManager.isManagedProfile(savedImageData.owner.getIdentifier())) {
            this.mScreenshotView.showWorkProfileMessage("Files");
        }
    }

    public final void finishDismiss() {
        ListenableFuture<ScrollCaptureResponse> listenableFuture = this.mLastScrollCaptureRequest;
        if (listenableFuture != null) {
            listenableFuture.cancel(true);
            this.mLastScrollCaptureRequest = null;
        }
        ScrollCaptureResponse scrollCaptureResponse = this.mLastScrollCaptureResponse;
        if (scrollCaptureResponse != null) {
            scrollCaptureResponse.close();
            this.mLastScrollCaptureResponse = null;
        }
        ListenableFuture<ScrollCaptureController.LongScreenshot> listenableFuture2 = this.mLongScreenshotFuture;
        if (listenableFuture2 != null) {
            listenableFuture2.cancel(true);
        }
        TakeScreenshotService.RequestCallback requestCallback = this.mCurrentRequestCallback;
        if (requestCallback != null) {
            requestCallback.onFinish();
            this.mCurrentRequestCallback = null;
        }
        this.mScreenshotView.reset();
        removeWindow();
        this.mScreenshotHandler.cancelTimeout();
    }

    public final Supplier<SavedImageData.ActionTransition> getActionTransitionSupplier() {
        return new Supplier() { // from class: com.android.systemui.screenshot.ScreenshotController$$ExternalSyntheticLambda19
            @Override // java.util.function.Supplier
            public final Object get() {
                return ScreenshotController.$r8$lambda$VRfQ2HNRVyPAb3ag1rgyCbGb8Oc(ScreenshotController.this);
            }
        };
    }

    public final Display getDefaultDisplay() {
        return this.mDisplayManager.getDisplay(0);
    }

    public final String getForegroundAppLabel() {
        try {
            return this.mPm.getActivityInfo(this.mTaskComponentName, 0).applicationInfo.loadLabel(this.mPm).toString();
        } catch (PackageManager.NameNotFoundException e) {
            return null;
        }
    }

    public void handleImageAsScreenshot(Bitmap bitmap, Rect rect, Insets insets, int i, int i2, ComponentName componentName, Consumer<Uri> consumer, TakeScreenshotService.RequestCallback requestCallback) {
        boolean z;
        Assert.isMainThread();
        if (bitmap == null) {
            Log.e(TAG, "Got null bitmap from screenshot message");
            this.mNotificationsController.notifyScreenshotError(R$string.screenshot_failed_to_capture_text);
            requestCallback.reportError();
            return;
        }
        if (aspectRatiosMatch(bitmap, insets, rect)) {
            z = false;
        } else {
            insets = Insets.NONE;
            rect.set(0, 0, bitmap.getWidth(), bitmap.getHeight());
            z = true;
        }
        this.mCurrentRequestCallback = requestCallback;
        saveScreenshot(bitmap, consumer, rect, insets, componentName, z, UserHandle.of(i2));
    }

    public boolean isPendingSharedTransition() {
        return this.mScreenshotView.isPendingSharedTransition();
    }

    public final boolean isUserSetupComplete(UserHandle userHandle) {
        boolean z = true;
        if (!this.mFlags.isEnabled(Flags.SCREENSHOT_WORK_PROFILE_POLICY)) {
            return Settings.Secure.getInt(this.mContext.getContentResolver(), "user_setup_complete", 0) == 1;
        }
        if (Settings.Secure.getInt(this.mContext.createContextAsUser(userHandle, 0).getContentResolver(), "user_setup_complete", 0) != 1) {
            z = false;
        }
        return z;
    }

    public final ListenableFuture<MediaPlayer> loadCameraSound() {
        return CallbackToFutureAdapter.getFuture(new CallbackToFutureAdapter.Resolver() { // from class: com.android.systemui.screenshot.ScreenshotController$$ExternalSyntheticLambda5
            @Override // androidx.concurrent.futures.CallbackToFutureAdapter.Resolver
            public final Object attachCompleter(CallbackToFutureAdapter.Completer completer) {
                Object lambda$loadCameraSound$12;
                lambda$loadCameraSound$12 = ScreenshotController.this.lambda$loadCameraSound$12(completer);
                return lambda$loadCameraSound$12;
            }
        });
    }

    public final void logSuccessOnActionsReady(SavedImageData savedImageData) {
        if (savedImageData.uri == null) {
            this.mUiEventLogger.log(ScreenshotEvent.SCREENSHOT_NOT_SAVED, 0, this.mPackageName);
            this.mNotificationsController.notifyScreenshotError(R$string.screenshot_failed_to_save_text);
            return;
        }
        this.mUiEventLogger.log(ScreenshotEvent.SCREENSHOT_SAVED, 0, this.mPackageName);
        if (this.mFlags.isEnabled(Flags.SCREENSHOT_WORK_PROFILE_POLICY) && this.mUserManager.isManagedProfile(savedImageData.owner.getIdentifier())) {
            this.mUiEventLogger.log(ScreenshotEvent.SCREENSHOT_SAVED_TO_WORK_PROFILE, 0, this.mPackageName);
        }
    }

    public void onDestroy() {
        SaveImageInBackgroundTask saveImageInBackgroundTask = this.mSaveInBgTask;
        if (saveImageInBackgroundTask != null) {
            saveImageInBackgroundTask.setActionsReadyListener(new ScreenshotController$$ExternalSyntheticLambda3(this));
        }
        removeWindow();
        releaseMediaPlayer();
        releaseContext();
        TaskStackChangeListeners.getInstance().unregisterTaskStackListener(this.mTaskListener);
        this.mBgExecutor.shutdownNow();
    }

    /* renamed from: onScrollCaptureResponseReady */
    public final void lambda$requestScrollCapture$6(Future<ScrollCaptureResponse> future, final UserHandle userHandle) {
        try {
            ScrollCaptureResponse scrollCaptureResponse = this.mLastScrollCaptureResponse;
            if (scrollCaptureResponse != null) {
                scrollCaptureResponse.close();
                this.mLastScrollCaptureResponse = null;
            }
            if (future.isCancelled()) {
                return;
            }
            ScrollCaptureResponse scrollCaptureResponse2 = future.get();
            this.mLastScrollCaptureResponse = scrollCaptureResponse2;
            if (scrollCaptureResponse2.isConnected()) {
                String str = TAG;
                Log.d(str, "ScrollCapture: connected to window [" + this.mLastScrollCaptureResponse.getWindowTitle() + "]");
                final ScrollCaptureResponse scrollCaptureResponse3 = this.mLastScrollCaptureResponse;
                this.mScreenshotView.showScrollChip(scrollCaptureResponse3.getPackageName(), new Runnable() { // from class: com.android.systemui.screenshot.ScreenshotController$$ExternalSyntheticLambda21
                    @Override // java.lang.Runnable
                    public final void run() {
                        ScreenshotController.$r8$lambda$JXyiCptiU0BdybYLspo49kppWoA(ScreenshotController.this, scrollCaptureResponse3, userHandle);
                    }
                });
                return;
            }
            String str2 = TAG;
            Log.d(str2, "ScrollCapture: " + this.mLastScrollCaptureResponse.getDescription() + " [" + this.mLastScrollCaptureResponse.getWindowTitle() + "]");
        } catch (InterruptedException | ExecutionException e) {
            Log.e(TAG, "requestScrollCapture failed", e);
        }
    }

    public final void playCameraSound() {
        this.mCameraSound.addListener(new Runnable() { // from class: com.android.systemui.screenshot.ScreenshotController$$ExternalSyntheticLambda16
            @Override // java.lang.Runnable
            public final void run() {
                ScreenshotController.$r8$lambda$_78myrYqphlTMCQadQbGMiJivu4(ScreenshotController.this);
            }
        }, this.mBgExecutor);
    }

    public final void releaseContext() {
        this.mContext.unregisterReceiver(this.mCopyBroadcastReceiver);
        this.mContext.release();
    }

    public final void releaseMediaPlayer() {
        try {
            MediaPlayer mediaPlayer = (MediaPlayer) this.mCameraSound.get();
            if (mediaPlayer != null) {
                mediaPlayer.release();
            }
        } catch (InterruptedException | ExecutionException e) {
        }
    }

    public final void reloadAssets() {
        ScreenshotView screenshotView = (ScreenshotView) LayoutInflater.from(this.mContext).inflate(R$layout.screenshot, (ViewGroup) null);
        this.mScreenshotView = screenshotView;
        screenshotView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() { // from class: com.android.systemui.screenshot.ScreenshotController.4
            {
                ScreenshotController.this = this;
            }

            @Override // android.view.View.OnAttachStateChangeListener
            public void onViewAttachedToWindow(View view) {
                ScreenshotController.this.mScreenshotView.findOnBackInvokedDispatcher().registerOnBackInvokedCallback(0, ScreenshotController.this.mOnBackInvokedCallback);
            }

            @Override // android.view.View.OnAttachStateChangeListener
            public void onViewDetachedFromWindow(View view) {
                ScreenshotController.this.mScreenshotView.findOnBackInvokedDispatcher().unregisterOnBackInvokedCallback(ScreenshotController.this.mOnBackInvokedCallback);
            }
        });
        this.mScreenshotView.init(this.mUiEventLogger, new ScreenshotView.ScreenshotViewCallback() { // from class: com.android.systemui.screenshot.ScreenshotController.5
            {
                ScreenshotController.this = this;
            }

            @Override // com.android.systemui.screenshot.ScreenshotView.ScreenshotViewCallback
            public void onDismiss() {
                ScreenshotController.this.finishDismiss();
            }

            @Override // com.android.systemui.screenshot.ScreenshotView.ScreenshotViewCallback
            public void onTouchOutside() {
                ScreenshotController.this.dismissScreenshot(ScreenshotEvent.SCREENSHOT_DISMISSED_OTHER);
            }

            @Override // com.android.systemui.screenshot.ScreenshotView.ScreenshotViewCallback
            public void onUserInteraction() {
                ScreenshotController.this.mScreenshotHandler.resetTimeout();
            }
        }, this.mActionExecutor, this.mFlags);
        this.mScreenshotView.setDefaultTimeoutMillis(this.mScreenshotHandler.getDefaultTimeoutMillis());
        this.mScreenshotView.setOnKeyListener(new View.OnKeyListener() { // from class: com.android.systemui.screenshot.ScreenshotController$$ExternalSyntheticLambda4
            @Override // android.view.View.OnKeyListener
            public final boolean onKey(View view, int i, KeyEvent keyEvent) {
                boolean lambda$reloadAssets$2;
                lambda$reloadAssets$2 = ScreenshotController.this.lambda$reloadAssets$2(view, i, keyEvent);
                return lambda$reloadAssets$2;
            }
        });
        this.mScreenshotView.getViewTreeObserver().addOnComputeInternalInsetsListener(this.mScreenshotView);
    }

    public void removeWindow() {
        View peekDecorView = this.mWindow.peekDecorView();
        if (peekDecorView != null && peekDecorView.isAttachedToWindow()) {
            this.mWindowManager.removeViewImmediate(peekDecorView);
        }
        ScreenshotView screenshotView = this.mScreenshotView;
        if (screenshotView != null) {
            screenshotView.stopInputListening();
        }
    }

    public final void requestScrollCapture(final UserHandle userHandle) {
        if (!allowLongScreenshots()) {
            Log.d(TAG, "Long screenshots not supported on this device");
            return;
        }
        this.mScrollCaptureClient.setHostWindowToken(this.mWindow.getDecorView().getWindowToken());
        ListenableFuture<ScrollCaptureResponse> listenableFuture = this.mLastScrollCaptureRequest;
        if (listenableFuture != null) {
            listenableFuture.cancel(true);
        }
        final ListenableFuture<ScrollCaptureResponse> request = this.mScrollCaptureClient.request(0);
        this.mLastScrollCaptureRequest = request;
        request.addListener(new Runnable() { // from class: com.android.systemui.screenshot.ScreenshotController$$ExternalSyntheticLambda18
            @Override // java.lang.Runnable
            public final void run() {
                ScreenshotController.$r8$lambda$pmPDoY6o1TfyBVPrsjk0WXuuNSI(ScreenshotController.this, request, userHandle);
            }
        }, this.mMainExecutor);
    }

    /* renamed from: respondToBack */
    public final void lambda$new$0() {
        dismissScreenshot(ScreenshotEvent.SCREENSHOT_DISMISSED_OTHER);
    }

    /* renamed from: runBatchScrollCapture */
    public final void lambda$onScrollCaptureResponseReady$8(ScrollCaptureResponse scrollCaptureResponse, final UserHandle userHandle) {
        this.mLastScrollCaptureResponse = null;
        ListenableFuture<ScrollCaptureController.LongScreenshot> listenableFuture = this.mLongScreenshotFuture;
        if (listenableFuture != null) {
            listenableFuture.cancel(true);
        }
        ListenableFuture<ScrollCaptureController.LongScreenshot> run = this.mScrollCaptureController.run(scrollCaptureResponse);
        this.mLongScreenshotFuture = run;
        run.addListener(new Runnable() { // from class: com.android.systemui.screenshot.ScreenshotController$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                ScreenshotController.$r8$lambda$jM4w0qiXe2Cwbxhq2rXmgTojbLA(ScreenshotController.this, userHandle);
            }
        }, this.mMainExecutor);
    }

    public final void saveScreenshot(Bitmap bitmap, Consumer<Uri> consumer, final Rect rect, Insets insets, ComponentName componentName, final boolean z, final UserHandle userHandle) {
        withWindowAttached(new Runnable() { // from class: com.android.systemui.screenshot.ScreenshotController$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                ScreenshotController.this.lambda$saveScreenshot$3(userHandle);
            }
        });
        this.mScreenshotView.reset();
        if (this.mScreenshotView.isAttachedToWindow() && !this.mScreenshotView.isDismissing()) {
            this.mUiEventLogger.log(ScreenshotEvent.SCREENSHOT_REENTERED, 0, this.mPackageName);
        }
        String packageName = componentName == null ? "" : componentName.getPackageName();
        this.mPackageName = packageName;
        this.mScreenshotView.setPackageName(packageName);
        this.mScreenshotView.updateOrientation(this.mWindowManager.getCurrentWindowMetrics().getWindowInsets());
        this.mScreenBitmap = bitmap;
        if (!isUserSetupComplete(userHandle)) {
            Log.w(TAG, "User setup not complete, displaying toast only");
            saveScreenshotAndToast(userHandle, consumer);
            return;
        }
        this.mScreenBitmap.setHasAlpha(false);
        this.mScreenBitmap.prepareToDraw();
        saveScreenshotInWorkerThread(userHandle, consumer, new ActionsReadyListener() { // from class: com.android.systemui.screenshot.ScreenshotController$$ExternalSyntheticLambda7
            @Override // com.android.systemui.screenshot.ScreenshotController.ActionsReadyListener
            public final void onActionsReady(ScreenshotController.SavedImageData savedImageData) {
                ScreenshotController.this.showUiOnActionsReady(savedImageData);
            }
        }, new QuickShareActionReadyListener() { // from class: com.android.systemui.screenshot.ScreenshotController$$ExternalSyntheticLambda8
            @Override // com.android.systemui.screenshot.ScreenshotController.QuickShareActionReadyListener
            public final void onActionsReady(ScreenshotController.QuickShareData quickShareData) {
                ScreenshotController.this.showUiOnQuickShareActionReady(quickShareData);
            }
        });
        setWindowFocusable(true);
        withWindowAttached(new Runnable() { // from class: com.android.systemui.screenshot.ScreenshotController$$ExternalSyntheticLambda9
            @Override // java.lang.Runnable
            public final void run() {
                ScreenshotController.this.lambda$saveScreenshot$4(userHandle);
            }
        });
        attachWindow();
        this.mScreenshotView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() { // from class: com.android.systemui.screenshot.ScreenshotController.7
            {
                ScreenshotController.this = this;
            }

            @Override // android.view.ViewTreeObserver.OnPreDrawListener
            public boolean onPreDraw() {
                ScreenshotController.this.mScreenshotView.getViewTreeObserver().removeOnPreDrawListener(this);
                ScreenshotController.this.startAnimation(rect, z);
                return true;
            }
        });
        if (this.mFlags.isEnabled(Flags.SCREENSHOT_WORK_PROFILE_POLICY)) {
            this.mScreenshotView.badgeScreenshot(this.mContext.getPackageManager().getUserBadgeForDensity(userHandle, 0));
        }
        this.mScreenshotView.setScreenshot(this.mScreenBitmap, insets);
        setContentView(this.mScreenshotView);
        this.mWindow.getDecorView().setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() { // from class: com.android.systemui.screenshot.ScreenshotController$$ExternalSyntheticLambda10
            @Override // android.view.View.OnApplyWindowInsetsListener
            public final WindowInsets onApplyWindowInsets(View view, WindowInsets windowInsets) {
                return ScreenshotController.m4260$r8$lambda$NcleYra3ctma4jhG9B0nzRrxpM(view, windowInsets);
            }
        });
        this.mScreenshotHandler.cancelTimeout();
    }

    public final void saveScreenshotAndToast(UserHandle userHandle, final Consumer<Uri> consumer) {
        playCameraSound();
        saveScreenshotInWorkerThread(userHandle, consumer, new ActionsReadyListener() { // from class: com.android.systemui.screenshot.ScreenshotController$$ExternalSyntheticLambda13
            @Override // com.android.systemui.screenshot.ScreenshotController.ActionsReadyListener
            public final void onActionsReady(ScreenshotController.SavedImageData savedImageData) {
                ScreenshotController.m4266$r8$lambda$snO_r5MhZTGhZlqz5awgtLtL2Y(ScreenshotController.this, consumer, savedImageData);
            }
        }, null);
    }

    public final void saveScreenshotInWorkerThread(UserHandle userHandle, Consumer<Uri> consumer, ActionsReadyListener actionsReadyListener, QuickShareActionReadyListener quickShareActionReadyListener) {
        SaveImageInBackgroundData saveImageInBackgroundData = new SaveImageInBackgroundData();
        saveImageInBackgroundData.image = this.mScreenBitmap;
        saveImageInBackgroundData.finisher = consumer;
        saveImageInBackgroundData.mActionsReadyListener = actionsReadyListener;
        saveImageInBackgroundData.mQuickShareActionsReadyListener = quickShareActionReadyListener;
        saveImageInBackgroundData.owner = userHandle;
        SaveImageInBackgroundTask saveImageInBackgroundTask = this.mSaveInBgTask;
        if (saveImageInBackgroundTask != null) {
            saveImageInBackgroundTask.setActionsReadyListener(new ScreenshotController$$ExternalSyntheticLambda3(this));
        }
        SaveImageInBackgroundTask saveImageInBackgroundTask2 = new SaveImageInBackgroundTask(this.mContext, this.mFlags, this.mImageExporter, this.mScreenshotSmartActions, saveImageInBackgroundData, getActionTransitionSupplier(), this.mScreenshotNotificationSmartActionsProvider);
        this.mSaveInBgTask = saveImageInBackgroundTask2;
        saveImageInBackgroundTask2.execute(getForegroundAppLabel());
    }

    public final void setContentView(View view) {
        this.mWindow.setContentView(view);
    }

    public final void setWindowFocusable(boolean z) {
        View peekDecorView;
        WindowManager.LayoutParams layoutParams = this.mWindowLayoutParams;
        int i = layoutParams.flags;
        if (z) {
            layoutParams.flags = i & (-9);
        } else {
            layoutParams.flags = i | 8;
        }
        if (layoutParams.flags == i || (peekDecorView = this.mWindow.peekDecorView()) == null || !peekDecorView.isAttachedToWindow()) {
            return;
        }
        this.mWindowManager.updateViewLayout(peekDecorView, this.mWindowLayoutParams);
    }

    public final void showUiOnActionsReady(final SavedImageData savedImageData) {
        logSuccessOnActionsReady(savedImageData);
        this.mScreenshotHandler.resetTimeout();
        if (savedImageData.uri != null) {
            if (!savedImageData.owner.equals(Process.myUserHandle())) {
                String str = TAG;
                Log.d(str, "Screenshot saved to user " + savedImageData.owner + " as " + savedImageData.uri);
            }
            this.mScreenshotHandler.post(new Runnable() { // from class: com.android.systemui.screenshot.ScreenshotController$$ExternalSyntheticLambda11
                @Override // java.lang.Runnable
                public final void run() {
                    ScreenshotController.m4263$r8$lambda$bzaiUvEgf79hAAj1DYUcGOzf3M(ScreenshotController.this, savedImageData);
                }
            });
        }
    }

    public final void showUiOnQuickShareActionReady(final QuickShareData quickShareData) {
        if (quickShareData.quickShareAction != null) {
            this.mScreenshotHandler.post(new Runnable() { // from class: com.android.systemui.screenshot.ScreenshotController$$ExternalSyntheticLambda15
                @Override // java.lang.Runnable
                public final void run() {
                    ScreenshotController.$r8$lambda$QmLpxeHDcrHYawDtZbebKztzJ5E(ScreenshotController.this, quickShareData);
                }
            });
        }
    }

    public final void startAnimation(Rect rect, boolean z) {
        Animator animator = this.mScreenshotAnimation;
        if (animator != null && animator.isRunning()) {
            this.mScreenshotAnimation.cancel();
        }
        this.mScreenshotAnimation = this.mScreenshotView.createScreenshotDropInAnimation(rect, z);
        playCameraSound();
        this.mScreenshotAnimation.start();
    }

    public void startLongScreenshotActivity(final ScrollCaptureController.LongScreenshot longScreenshot, UserHandle userHandle) {
        this.mLongScreenshotHolder.setForegroundAppName(getForegroundAppLabel());
        this.mLongScreenshotHolder.setLongScreenshot(longScreenshot);
        this.mLongScreenshotHolder.setTransitionDestinationCallback(new TransitionDestination() { // from class: com.android.systemui.screenshot.ScreenshotController$$ExternalSyntheticLambda12
            @Override // com.android.systemui.screenshot.ScreenshotController.TransitionDestination
            public final void setTransitionDestination(Rect rect, Runnable runnable) {
                ScreenshotController.this.lambda$startLongScreenshotActivity$7(longScreenshot, rect, runnable);
            }
        });
        Intent intent = new Intent((Context) this.mContext, (Class<?>) LongScreenshotActivity.class);
        intent.putExtra("screenshot-userhandle", userHandle);
        intent.setFlags(335544320);
        WindowContext windowContext = this.mContext;
        windowContext.startActivity(intent, ActivityOptions.makeCustomAnimation(windowContext, 0, 0).toBundle());
        try {
            WindowManagerGlobal.getWindowManagerService().overridePendingAppTransitionRemote(new RemoteAnimationAdapter(SCREENSHOT_REMOTE_RUNNER, 0L, 0L), 0);
        } catch (Exception e) {
            Log.e(TAG, "Error overriding screenshot app transition", e);
        }
        try {
            this.mStatusBarService.collapsePanels();
        } catch (RemoteException e2) {
            Log.e(TAG, "Error during collapsing panels", e2);
        }
    }

    public final void startPartialScreenshotActivity(UserHandle userHandle) {
        ScrollCaptureController.BitmapScreenshot bitmapScreenshot = new ScrollCaptureController.BitmapScreenshot(this.mContext, captureScreenshot());
        this.mLongScreenshotHolder.setNeedsMagnification(false);
        startLongScreenshotActivity(bitmapScreenshot, userHandle);
    }

    public void takeScreenshotFullscreen(ComponentName componentName, Consumer<Uri> consumer, TakeScreenshotService.RequestCallback requestCallback) {
        Assert.isMainThread();
        this.mScreenshotHandler.removeCallbacks(this.mFullScreenshotRunnable);
        this.mCurrentRequestCallback = null;
        dismissScreenshot(ScreenshotEvent.SCREENSHOT_DISMISSED_OTHER);
        this.mFullScreenshotRunnable.setArgs(componentName, consumer, requestCallback);
        this.mScreenshotHandler.postDelayed(this.mFullScreenshotRunnable, 50L);
    }

    public void takeScreenshotFullscreenInternal(ComponentName componentName, Consumer<Uri> consumer, TakeScreenshotService.RequestCallback requestCallback) {
        this.mCurrentRequestCallback = requestCallback;
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getDefaultDisplay().getRealMetrics(displayMetrics);
        takeScreenshotInternal(componentName, consumer, new Rect(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels));
    }

    public final void takeScreenshotInternal(ComponentName componentName, Consumer<Uri> consumer, Rect rect) {
        boolean z = true;
        if (this.mContext.getResources().getConfiguration().orientation != 1) {
            z = false;
        }
        this.mScreenshotTakenInPortrait = z;
        Rect rect2 = new Rect(rect);
        Bitmap captureDisplay = this.mImageCapture.captureDisplay(0, rect);
        if (captureDisplay != null) {
            saveScreenshot(captureDisplay, consumer, rect2, Insets.NONE, componentName, true, Process.myUserHandle());
            this.mBroadcastSender.sendBroadcast(new Intent("com.android.systemui.SCREENSHOT"), "com.android.systemui.permission.SELF");
            return;
        }
        Log.e(TAG, "takeScreenshotInternal: Screenshot bitmap was null");
        this.mNotificationsController.notifyScreenshotError(R$string.screenshot_failed_to_capture_text);
        TakeScreenshotService.RequestCallback requestCallback = this.mCurrentRequestCallback;
        if (requestCallback != null) {
            requestCallback.reportError();
        }
    }

    public void takeScreenshotPartial(ComponentName componentName, Consumer<Uri> consumer, TakeScreenshotService.RequestCallback requestCallback) {
        Assert.isMainThread();
        startPartialScreenshotActivity(Process.myUserHandle());
        consumer.accept(null);
    }

    public final void updateForegroundTaskSync() {
        ComponentName componentName;
        try {
            ActivityTaskManager.RootTaskInfo focusedRootTaskInfo = ActivityTaskManager.getService().getFocusedRootTaskInfo();
            if (focusedRootTaskInfo == null || (componentName = focusedRootTaskInfo.topActivity) == null) {
                return;
            }
            this.mTaskComponentName = componentName;
        } catch (RemoteException e) {
            Log.e(TAG, "Failed to get foreground task component", e);
        }
    }

    public final void withWindowAttached(final Runnable runnable) {
        final View decorView = this.mWindow.getDecorView();
        if (decorView.isAttachedToWindow()) {
            runnable.run();
        } else {
            decorView.getViewTreeObserver().addOnWindowAttachListener(new ViewTreeObserver.OnWindowAttachListener() { // from class: com.android.systemui.screenshot.ScreenshotController.8
                {
                    ScreenshotController.this = this;
                }

                @Override // android.view.ViewTreeObserver.OnWindowAttachListener
                public void onWindowAttached() {
                    ScreenshotController.this.mBlockAttach = false;
                    decorView.getViewTreeObserver().removeOnWindowAttachListener(this);
                    runnable.run();
                }

                @Override // android.view.ViewTreeObserver.OnWindowAttachListener
                public void onWindowDetached() {
                }
            });
        }
    }
}