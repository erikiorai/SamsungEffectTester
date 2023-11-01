package com.android.systemui.clipboardoverlay;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.RemoteAction;
import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.hardware.display.DisplayManager;
import android.hardware.input.InputManager;
import android.net.Uri;
import android.os.Looper;
import android.provider.DeviceConfig;
import android.text.TextUtils;
import android.util.Log;
import android.util.Size;
import android.view.Display;
import android.view.InputEvent;
import android.view.InputEventReceiver;
import android.view.InputMonitor;
import android.view.MotionEvent;
import android.view.WindowInsets;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.R$dimen;
import com.android.systemui.R$string;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.broadcast.BroadcastSender;
import com.android.systemui.clipboardoverlay.ClipboardListener;
import com.android.systemui.clipboardoverlay.ClipboardOverlayView;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.Flags;
import com.android.systemui.screenshot.TimeoutHandler;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.Executor;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/* loaded from: mainsysui33.jar:com/android/systemui/clipboardoverlay/ClipboardOverlayController.class */
public class ClipboardOverlayController implements ClipboardListener.ClipboardOverlay {
    public final Executor mBgExecutor;
    public final BroadcastDispatcher mBroadcastDispatcher;
    public final ClipboardOverlayView.ClipboardOverlayCallbacks mClipboardCallbacks;
    public final ClipboardLogger mClipboardLogger;
    public final ClipboardOverlayUtils mClipboardUtils;
    public BroadcastReceiver mCloseDialogsReceiver;
    public final Context mContext;
    public final DisplayManager mDisplayManager;
    public Animator mEnterAnimator;
    public Animator mExitAnimator;
    public final FeatureFlags mFeatureFlags;
    public InputEventReceiver mInputEventReceiver;
    public InputMonitor mInputMonitor;
    public Runnable mOnEditTapped;
    public Runnable mOnPreviewTapped;
    public Runnable mOnRemoteCopyTapped;
    public Runnable mOnSessionCompleteListener;
    public Runnable mOnShareTapped;
    public Runnable mOnUiUpdate;
    public BroadcastReceiver mScreenshotReceiver;
    public final TimeoutHandler mTimeoutHandler;
    public final ClipboardOverlayView mView;
    public final ClipboardOverlayWindow mWindow;

    /* loaded from: mainsysui33.jar:com/android/systemui/clipboardoverlay/ClipboardOverlayController$ClipboardLogger.class */
    public static class ClipboardLogger {
        public String mClipSource;
        public boolean mGuarded = false;
        public final UiEventLogger mUiEventLogger;

        public ClipboardLogger(UiEventLogger uiEventLogger) {
            this.mUiEventLogger = uiEventLogger;
        }

        public void logSessionComplete(UiEventLogger.UiEventEnum uiEventEnum) {
            if (this.mGuarded) {
                return;
            }
            this.mGuarded = true;
            this.mUiEventLogger.log(uiEventEnum, 0, this.mClipSource);
        }

        public void logUnguarded(UiEventLogger.UiEventEnum uiEventEnum) {
            this.mUiEventLogger.log(uiEventEnum, 0, this.mClipSource);
        }

        public void reset() {
            this.mGuarded = false;
            this.mClipSource = null;
        }

        public void setClipSource(String str) {
            this.mClipSource = str;
        }
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.clipboardoverlay.ClipboardOverlayController$$ExternalSyntheticLambda11.run():void] */
    /* renamed from: $r8$lambda$-zVNj3qWrNeQtwQi8gY3nv7U3Pg */
    public static /* synthetic */ void m1726$r8$lambda$zVNj3qWrNeQtwQi8gY3nv7U3Pg(ClipboardOverlayController clipboardOverlayController, Optional optional) {
        clipboardOverlayController.lambda$classifyText$8(optional);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.clipboardoverlay.ClipboardOverlayController$$ExternalSyntheticLambda7.run():void] */
    public static /* synthetic */ void $r8$lambda$7VcwFuyg8uo63cyNUgz8r4mZ1Co(ClipboardOverlayController clipboardOverlayController, Uri uri) {
        clipboardOverlayController.lambda$tryShowEditableImage$10(uri);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.clipboardoverlay.ClipboardOverlayController$$ExternalSyntheticLambda10.run():void] */
    public static /* synthetic */ void $r8$lambda$AZ9Lvq5FbIeS_x13t7fd0xGv27o(ClipboardOverlayController clipboardOverlayController) {
        clipboardOverlayController.editText();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.clipboardoverlay.ClipboardOverlayController$$ExternalSyntheticLambda9.run():void] */
    public static /* synthetic */ void $r8$lambda$IcMvuIHm9sKcit5T99unQPR7IKk(ClipboardOverlayController clipboardOverlayController, Intent intent) {
        clipboardOverlayController.lambda$maybeShowRemoteCopy$5(intent);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.clipboardoverlay.ClipboardOverlayController$$ExternalSyntheticLambda3.run():void] */
    /* renamed from: $r8$lambda$M6kWsZ3-hnk5pbWkShKB3MKIygQ */
    public static /* synthetic */ void m1727$r8$lambda$M6kWsZ3hnk5pbWkShKB3MKIygQ(ClipboardOverlayController clipboardOverlayController) {
        clipboardOverlayController.lambda$new$2();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.clipboardoverlay.ClipboardOverlayController$$ExternalSyntheticLambda2.run():void] */
    public static /* synthetic */ void $r8$lambda$MSfFklsZu0A98kQIBq8nGkr6wcA(ClipboardOverlayController clipboardOverlayController) {
        clipboardOverlayController.lambda$new$1();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.clipboardoverlay.ClipboardOverlayController$$ExternalSyntheticLambda13.run():void] */
    /* renamed from: $r8$lambda$SJNWjFHZUnoa6HgWRvqjGC-0uSU */
    public static /* synthetic */ void m1728$r8$lambda$SJNWjFHZUnoa6HgWRvqjGC0uSU(ClipboardOverlayController clipboardOverlayController) {
        clipboardOverlayController.lambda$classifyText$6();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.clipboardoverlay.ClipboardOverlayController$$ExternalSyntheticLambda12.accept(java.lang.Object):void] */
    /* renamed from: $r8$lambda$TvmhzD8ymo-YFD-6MAa1aJyHB38 */
    public static /* synthetic */ void m1729$r8$lambda$TvmhzD8ymoYFD6MAa1aJyHB38(ClipboardOverlayController clipboardOverlayController, RemoteAction remoteAction) {
        clipboardOverlayController.lambda$classifyText$7(remoteAction);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.clipboardoverlay.ClipboardOverlayController$$ExternalSyntheticLambda4.run():void] */
    public static /* synthetic */ void $r8$lambda$YsVdOsCZaTxl8AWSsB_MX045Ldk(ClipboardOverlayController clipboardOverlayController, ClipData clipData) {
        clipboardOverlayController.lambda$setClipData$3(clipData);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.clipboardoverlay.ClipboardOverlayController$$ExternalSyntheticLambda1.run():void] */
    /* renamed from: $r8$lambda$cAMXSVN7I3_n6ZBYXrYqBgs-RDE */
    public static /* synthetic */ void m1730$r8$lambda$cAMXSVN7I3_n6ZBYXrYqBgsRDE(ClipboardOverlayController clipboardOverlayController) {
        clipboardOverlayController.lambda$new$0();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.clipboardoverlay.ClipboardOverlayController$$ExternalSyntheticLambda8.run():void] */
    public static /* synthetic */ void $r8$lambda$rf2KB6ibWTbkc9LfvPYQ7bKuFm0(ClipboardOverlayController clipboardOverlayController, ClipData.Item item, String str) {
        clipboardOverlayController.lambda$classifyText$9(item, str);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.clipboardoverlay.ClipboardOverlayController$$ExternalSyntheticLambda5.run():void] */
    /* renamed from: $r8$lambda$ts9Uo5FP3WQ9hGFATp4s-IIMCCM */
    public static /* synthetic */ void m1731$r8$lambda$ts9Uo5FP3WQ9hGFATp4sIIMCCM(ClipboardOverlayController clipboardOverlayController, ClipData clipData) {
        clipboardOverlayController.lambda$setClipData$4(clipData);
    }

    public ClipboardOverlayController(Context context, final ClipboardOverlayView clipboardOverlayView, ClipboardOverlayWindow clipboardOverlayWindow, BroadcastDispatcher broadcastDispatcher, BroadcastSender broadcastSender, TimeoutHandler timeoutHandler, FeatureFlags featureFlags, ClipboardOverlayUtils clipboardOverlayUtils, Executor executor, UiEventLogger uiEventLogger) {
        ClipboardOverlayView.ClipboardOverlayCallbacks clipboardOverlayCallbacks = new ClipboardOverlayView.ClipboardOverlayCallbacks() { // from class: com.android.systemui.clipboardoverlay.ClipboardOverlayController.1
            {
                ClipboardOverlayController.this = this;
            }

            @Override // com.android.systemui.clipboardoverlay.ClipboardOverlayView.ClipboardOverlayCallbacks
            public void onDismissButtonTapped() {
                ClipboardOverlayController.this.mClipboardLogger.logSessionComplete(ClipboardOverlayEvent.CLIPBOARD_OVERLAY_DISMISS_TAPPED);
                ClipboardOverlayController.this.animateOut();
            }

            @Override // com.android.systemui.screenshot.DraggableConstraintLayout.SwipeDismissCallbacks
            public void onDismissComplete() {
                ClipboardOverlayController.this.hideImmediate();
            }

            @Override // com.android.systemui.clipboardoverlay.ClipboardOverlayView.ClipboardOverlayCallbacks
            public void onEditButtonTapped() {
                if (ClipboardOverlayController.this.mOnEditTapped != null) {
                    ClipboardOverlayController.this.mOnEditTapped.run();
                }
            }

            @Override // com.android.systemui.screenshot.DraggableConstraintLayout.SwipeDismissCallbacks
            public void onInteraction() {
                if (ClipboardOverlayController.this.mOnUiUpdate != null) {
                    ClipboardOverlayController.this.mOnUiUpdate.run();
                }
            }

            @Override // com.android.systemui.clipboardoverlay.ClipboardOverlayView.ClipboardOverlayCallbacks
            public void onPreviewTapped() {
                if (ClipboardOverlayController.this.mOnPreviewTapped != null) {
                    ClipboardOverlayController.this.mOnPreviewTapped.run();
                }
            }

            @Override // com.android.systemui.clipboardoverlay.ClipboardOverlayView.ClipboardOverlayCallbacks
            public void onRemoteCopyButtonTapped() {
                if (ClipboardOverlayController.this.mOnRemoteCopyTapped != null) {
                    ClipboardOverlayController.this.mOnRemoteCopyTapped.run();
                }
            }

            @Override // com.android.systemui.clipboardoverlay.ClipboardOverlayView.ClipboardOverlayCallbacks
            public void onShareButtonTapped() {
                if (ClipboardOverlayController.this.mOnShareTapped != null) {
                    ClipboardOverlayController.this.mOnShareTapped.run();
                }
            }

            @Override // com.android.systemui.screenshot.DraggableConstraintLayout.SwipeDismissCallbacks
            public void onSwipeDismissInitiated(Animator animator) {
                ClipboardOverlayController.this.mClipboardLogger.logSessionComplete(ClipboardOverlayEvent.CLIPBOARD_OVERLAY_SWIPE_DISMISSED);
                ClipboardOverlayController.this.mExitAnimator = animator;
            }
        };
        this.mClipboardCallbacks = clipboardOverlayCallbacks;
        this.mBroadcastDispatcher = broadcastDispatcher;
        DisplayManager displayManager = (DisplayManager) context.getSystemService(DisplayManager.class);
        Objects.requireNonNull(displayManager);
        this.mDisplayManager = displayManager;
        Context createWindowContext = context.createDisplayContext(getDefaultDisplay()).createWindowContext(2036, null);
        this.mContext = createWindowContext;
        this.mClipboardLogger = new ClipboardLogger(uiEventLogger);
        this.mView = clipboardOverlayView;
        this.mWindow = clipboardOverlayWindow;
        Objects.requireNonNull(clipboardOverlayView);
        clipboardOverlayWindow.init(new BiConsumer() { // from class: com.android.systemui.clipboardoverlay.ClipboardOverlayController$$ExternalSyntheticLambda0
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                ClipboardOverlayView.this.setInsets((WindowInsets) obj, ((Integer) obj2).intValue());
            }
        }, new Runnable() { // from class: com.android.systemui.clipboardoverlay.ClipboardOverlayController$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                ClipboardOverlayController.m1730$r8$lambda$cAMXSVN7I3_n6ZBYXrYqBgsRDE(ClipboardOverlayController.this);
            }
        });
        this.mTimeoutHandler = timeoutHandler;
        timeoutHandler.setDefaultTimeoutMillis(6000);
        this.mFeatureFlags = featureFlags;
        this.mClipboardUtils = clipboardOverlayUtils;
        this.mBgExecutor = executor;
        clipboardOverlayView.setCallbacks(clipboardOverlayCallbacks);
        clipboardOverlayWindow.withWindowAttached(new Runnable() { // from class: com.android.systemui.clipboardoverlay.ClipboardOverlayController$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                ClipboardOverlayController.$r8$lambda$MSfFklsZu0A98kQIBq8nGkr6wcA(ClipboardOverlayController.this);
            }
        });
        timeoutHandler.setOnTimeoutRunnable(new Runnable() { // from class: com.android.systemui.clipboardoverlay.ClipboardOverlayController$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                ClipboardOverlayController.m1727$r8$lambda$M6kWsZ3hnk5pbWkShKB3MKIygQ(ClipboardOverlayController.this);
            }
        });
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() { // from class: com.android.systemui.clipboardoverlay.ClipboardOverlayController.2
            {
                ClipboardOverlayController.this = this;
            }

            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                if ("android.intent.action.CLOSE_SYSTEM_DIALOGS".equals(intent.getAction())) {
                    ClipboardOverlayController.this.mClipboardLogger.logSessionComplete(ClipboardOverlayEvent.CLIPBOARD_OVERLAY_DISMISSED_OTHER);
                    ClipboardOverlayController.this.animateOut();
                }
            }
        };
        this.mCloseDialogsReceiver = broadcastReceiver;
        broadcastDispatcher.registerReceiver(broadcastReceiver, new IntentFilter("android.intent.action.CLOSE_SYSTEM_DIALOGS"));
        BroadcastReceiver broadcastReceiver2 = new BroadcastReceiver() { // from class: com.android.systemui.clipboardoverlay.ClipboardOverlayController.3
            {
                ClipboardOverlayController.this = this;
            }

            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                if ("com.android.systemui.SCREENSHOT".equals(intent.getAction())) {
                    ClipboardOverlayController.this.mClipboardLogger.logSessionComplete(ClipboardOverlayEvent.CLIPBOARD_OVERLAY_DISMISSED_OTHER);
                    ClipboardOverlayController.this.animateOut();
                }
            }
        };
        this.mScreenshotReceiver = broadcastReceiver2;
        broadcastDispatcher.registerReceiver(broadcastReceiver2, new IntentFilter("com.android.systemui.SCREENSHOT"), null, null, 2, "com.android.systemui.permission.SELF");
        monitorOutsideTouches();
        Intent intent = new Intent("com.android.systemui.COPY");
        intent.setPackage(createWindowContext.getPackageName());
        broadcastSender.sendBroadcast(intent, "com.android.systemui.permission.SELF");
    }

    public /* synthetic */ void lambda$classifyText$6() {
        this.mClipboardLogger.logSessionComplete(ClipboardOverlayEvent.CLIPBOARD_OVERLAY_ACTION_TAPPED);
        animateOut();
    }

    public /* synthetic */ void lambda$classifyText$7(RemoteAction remoteAction) {
        this.mView.setActionChip(remoteAction, new Runnable() { // from class: com.android.systemui.clipboardoverlay.ClipboardOverlayController$$ExternalSyntheticLambda13
            @Override // java.lang.Runnable
            public final void run() {
                ClipboardOverlayController.m1728$r8$lambda$SJNWjFHZUnoa6HgWRvqjGC0uSU(ClipboardOverlayController.this);
            }
        });
        this.mClipboardLogger.logUnguarded(ClipboardOverlayEvent.CLIPBOARD_OVERLAY_ACTION_SHOWN);
    }

    public /* synthetic */ void lambda$classifyText$8(Optional optional) {
        this.mView.resetActionChips();
        optional.ifPresent(new Consumer() { // from class: com.android.systemui.clipboardoverlay.ClipboardOverlayController$$ExternalSyntheticLambda12
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ClipboardOverlayController.m1729$r8$lambda$TvmhzD8ymoYFD6MAa1aJyHB38(ClipboardOverlayController.this, (RemoteAction) obj);
            }
        });
    }

    public /* synthetic */ void lambda$classifyText$9(ClipData.Item item, String str) {
        final Optional<RemoteAction> action = this.mClipboardUtils.getAction(item, str);
        this.mView.post(new Runnable() { // from class: com.android.systemui.clipboardoverlay.ClipboardOverlayController$$ExternalSyntheticLambda11
            @Override // java.lang.Runnable
            public final void run() {
                ClipboardOverlayController.m1726$r8$lambda$zVNj3qWrNeQtwQi8gY3nv7U3Pg(ClipboardOverlayController.this, action);
            }
        });
    }

    public /* synthetic */ void lambda$maybeShowRemoteCopy$5(Intent intent) {
        this.mClipboardLogger.logSessionComplete(ClipboardOverlayEvent.CLIPBOARD_OVERLAY_REMOTE_COPY_TAPPED);
        this.mContext.startActivity(intent);
        animateOut();
    }

    public /* synthetic */ void lambda$new$0() {
        this.mClipboardLogger.logSessionComplete(ClipboardOverlayEvent.CLIPBOARD_OVERLAY_DISMISSED_OTHER);
        hideImmediate();
    }

    public /* synthetic */ void lambda$new$1() {
        this.mWindow.setContentView(this.mView);
        this.mView.setInsets(this.mWindow.getWindowInsets(), this.mContext.getResources().getConfiguration().orientation);
    }

    public /* synthetic */ void lambda$new$2() {
        this.mClipboardLogger.logSessionComplete(ClipboardOverlayEvent.CLIPBOARD_OVERLAY_TIMED_OUT);
        animateOut();
    }

    public final void animateIn() {
        Animator animator = this.mEnterAnimator;
        if (animator == null || !animator.isRunning()) {
            Animator enterAnimation = this.mView.getEnterAnimation();
            this.mEnterAnimator = enterAnimation;
            enterAnimation.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.clipboardoverlay.ClipboardOverlayController.5
                {
                    ClipboardOverlayController.this = this;
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator2) {
                    super.onAnimationEnd(animator2);
                    if (ClipboardOverlayController.this.mOnUiUpdate != null) {
                        ClipboardOverlayController.this.mOnUiUpdate.run();
                    }
                }
            });
            this.mEnterAnimator.start();
        }
    }

    public final void animateOut() {
        Animator animator = this.mExitAnimator;
        if (animator == null || !animator.isRunning()) {
            Animator exitAnimation = this.mView.getExitAnimation();
            exitAnimation.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.clipboardoverlay.ClipboardOverlayController.6
                public boolean mCancelled;

                {
                    ClipboardOverlayController.this = this;
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationCancel(Animator animator2) {
                    super.onAnimationCancel(animator2);
                    this.mCancelled = true;
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator2) {
                    super.onAnimationEnd(animator2);
                    if (this.mCancelled) {
                        return;
                    }
                    ClipboardOverlayController.this.hideImmediate();
                }
            });
            this.mExitAnimator = exitAnimation;
            exitAnimation.start();
        }
    }

    public final void classifyText(final ClipData.Item item, final String str) {
        this.mBgExecutor.execute(new Runnable() { // from class: com.android.systemui.clipboardoverlay.ClipboardOverlayController$$ExternalSyntheticLambda8
            @Override // java.lang.Runnable
            public final void run() {
                ClipboardOverlayController.$r8$lambda$rf2KB6ibWTbkc9LfvPYQ7bKuFm0(ClipboardOverlayController.this, item, str);
            }
        });
    }

    /* renamed from: editImage */
    public final void lambda$tryShowEditableImage$10(Uri uri) {
        this.mClipboardLogger.logSessionComplete(ClipboardOverlayEvent.CLIPBOARD_OVERLAY_EDIT_TAPPED);
        Context context = this.mContext;
        context.startActivity(IntentCreator.getImageEditIntent(uri, context));
        animateOut();
    }

    public final void editText() {
        this.mClipboardLogger.logSessionComplete(ClipboardOverlayEvent.CLIPBOARD_OVERLAY_EDIT_TAPPED);
        Context context = this.mContext;
        context.startActivity(IntentCreator.getTextEditorIntent(context));
        animateOut();
    }

    public final Display getDefaultDisplay() {
        return this.mDisplayManager.getDisplay(0);
    }

    public void hideImmediate() {
        this.mTimeoutHandler.cancelTimeout();
        this.mWindow.remove();
        BroadcastReceiver broadcastReceiver = this.mCloseDialogsReceiver;
        if (broadcastReceiver != null) {
            this.mBroadcastDispatcher.unregisterReceiver(broadcastReceiver);
            this.mCloseDialogsReceiver = null;
        }
        BroadcastReceiver broadcastReceiver2 = this.mScreenshotReceiver;
        if (broadcastReceiver2 != null) {
            this.mBroadcastDispatcher.unregisterReceiver(broadcastReceiver2);
            this.mScreenshotReceiver = null;
        }
        InputEventReceiver inputEventReceiver = this.mInputEventReceiver;
        if (inputEventReceiver != null) {
            inputEventReceiver.dispose();
            this.mInputEventReceiver = null;
        }
        InputMonitor inputMonitor = this.mInputMonitor;
        if (inputMonitor != null) {
            inputMonitor.dispose();
            this.mInputMonitor = null;
        }
        Runnable runnable = this.mOnSessionCompleteListener;
        if (runnable != null) {
            runnable.run();
        }
    }

    public final void maybeShowRemoteCopy(ClipData clipData) {
        final Intent remoteCopyIntent = IntentCreator.getRemoteCopyIntent(clipData, this.mContext);
        if (this.mContext.getPackageManager().resolveActivity(remoteCopyIntent, PackageManager.ResolveInfoFlags.of(0L)) == null) {
            this.mView.setRemoteCopyVisibility(false);
            return;
        }
        this.mView.setRemoteCopyVisibility(true);
        this.mOnRemoteCopyTapped = new Runnable() { // from class: com.android.systemui.clipboardoverlay.ClipboardOverlayController$$ExternalSyntheticLambda9
            @Override // java.lang.Runnable
            public final void run() {
                ClipboardOverlayController.$r8$lambda$IcMvuIHm9sKcit5T99unQPR7IKk(ClipboardOverlayController.this, remoteCopyIntent);
            }
        };
    }

    public final void monitorOutsideTouches() {
        this.mInputMonitor = ((InputManager) this.mContext.getSystemService(InputManager.class)).monitorGestureInput("clipboard overlay", 0);
        this.mInputEventReceiver = new InputEventReceiver(this.mInputMonitor.getInputChannel(), Looper.getMainLooper()) { // from class: com.android.systemui.clipboardoverlay.ClipboardOverlayController.4
            {
                ClipboardOverlayController.this = this;
            }

            public void onInputEvent(InputEvent inputEvent) {
                if (inputEvent instanceof MotionEvent) {
                    MotionEvent motionEvent = (MotionEvent) inputEvent;
                    if (motionEvent.getActionMasked() == 0 && !ClipboardOverlayController.this.mView.isInTouchRegion((int) motionEvent.getRawX(), (int) motionEvent.getRawY())) {
                        ClipboardOverlayController.this.mClipboardLogger.logSessionComplete(ClipboardOverlayEvent.CLIPBOARD_OVERLAY_TAP_OUTSIDE);
                        ClipboardOverlayController.this.animateOut();
                    }
                }
                finishInputEvent(inputEvent, true);
            }
        };
    }

    public final void reset() {
        this.mOnRemoteCopyTapped = null;
        this.mOnShareTapped = null;
        this.mOnEditTapped = null;
        this.mOnPreviewTapped = null;
        this.mView.reset();
        this.mTimeoutHandler.cancelTimeout();
        this.mClipboardLogger.reset();
    }

    @Override // com.android.systemui.clipboardoverlay.ClipboardListener.ClipboardOverlay
    public void setClipData(final ClipData clipData, String str) {
        String str2;
        Animator animator = this.mExitAnimator;
        if (animator != null && animator.isRunning()) {
            this.mExitAnimator.cancel();
        }
        reset();
        this.mClipboardLogger.setClipSource(str);
        String string = this.mContext.getString(R$string.clipboard_content_copied);
        boolean z = (clipData == null || clipData.getDescription().getExtras() == null || !clipData.getDescription().getExtras().getBoolean("android.content.extra.IS_SENSITIVE")) ? false : true;
        boolean z2 = this.mFeatureFlags.isEnabled(Flags.CLIPBOARD_REMOTE_BEHAVIOR) && this.mClipboardUtils.isRemoteCopy(this.mContext, clipData, str);
        if (clipData == null || clipData.getItemCount() == 0) {
            this.mView.showDefaultTextPreview();
            str2 = string;
        } else if (!TextUtils.isEmpty(clipData.getItemAt(0).getText())) {
            ClipData.Item itemAt = clipData.getItemAt(0);
            if ((z2 || DeviceConfig.getBoolean("systemui", "clipboard_overlay_show_actions", false)) && itemAt.getTextLinks() != null) {
                classifyText(clipData.getItemAt(0), str);
            }
            if (z) {
                showEditableText(this.mContext.getString(R$string.clipboard_asterisks), true);
            } else {
                showEditableText(itemAt.getText(), false);
            }
            this.mOnShareTapped = new Runnable() { // from class: com.android.systemui.clipboardoverlay.ClipboardOverlayController$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    ClipboardOverlayController.$r8$lambda$YsVdOsCZaTxl8AWSsB_MX045Ldk(ClipboardOverlayController.this, clipData);
                }
            };
            this.mView.showShareChip();
            str2 = this.mContext.getString(R$string.clipboard_text_copied);
        } else if (clipData.getItemAt(0).getUri() != null) {
            str2 = string;
            if (tryShowEditableImage(clipData.getItemAt(0).getUri(), z)) {
                this.mOnShareTapped = new Runnable() { // from class: com.android.systemui.clipboardoverlay.ClipboardOverlayController$$ExternalSyntheticLambda5
                    @Override // java.lang.Runnable
                    public final void run() {
                        ClipboardOverlayController.m1731$r8$lambda$ts9Uo5FP3WQ9hGFATp4sIIMCCM(ClipboardOverlayController.this, clipData);
                    }
                };
                this.mView.showShareChip();
                str2 = this.mContext.getString(R$string.clipboard_image_copied);
            }
        } else {
            this.mView.showDefaultTextPreview();
            str2 = string;
        }
        if (!z2) {
            maybeShowRemoteCopy(clipData);
        }
        animateIn();
        this.mView.announceForAccessibility(str2);
        if (z2) {
            this.mTimeoutHandler.cancelTimeout();
            this.mOnUiUpdate = null;
            return;
        }
        final TimeoutHandler timeoutHandler = this.mTimeoutHandler;
        Objects.requireNonNull(timeoutHandler);
        Runnable runnable = new Runnable() { // from class: com.android.systemui.clipboardoverlay.ClipboardOverlayController$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                TimeoutHandler.this.resetTimeout();
            }
        };
        this.mOnUiUpdate = runnable;
        runnable.run();
    }

    @Override // com.android.systemui.clipboardoverlay.ClipboardListener.ClipboardOverlay
    public void setOnSessionCompleteListener(Runnable runnable) {
        this.mOnSessionCompleteListener = runnable;
    }

    /* renamed from: shareContent */
    public final void lambda$setClipData$4(ClipData clipData) {
        this.mClipboardLogger.logSessionComplete(ClipboardOverlayEvent.CLIPBOARD_OVERLAY_SHARE_TAPPED);
        Context context = this.mContext;
        context.startActivity(IntentCreator.getShareIntent(clipData, context));
        animateOut();
    }

    public final void showEditableText(CharSequence charSequence, boolean z) {
        this.mView.showTextPreview(charSequence, z);
        this.mView.setEditAccessibilityAction(true);
        this.mOnPreviewTapped = new Runnable() { // from class: com.android.systemui.clipboardoverlay.ClipboardOverlayController$$ExternalSyntheticLambda10
            @Override // java.lang.Runnable
            public final void run() {
                ClipboardOverlayController.$r8$lambda$AZ9Lvq5FbIeS_x13t7fd0xGv27o(ClipboardOverlayController.this);
            }
        };
        if (DeviceConfig.getBoolean("systemui", "clipboard_overlay_show_edit_button", false)) {
            this.mOnEditTapped = new Runnable() { // from class: com.android.systemui.clipboardoverlay.ClipboardOverlayController$$ExternalSyntheticLambda10
                @Override // java.lang.Runnable
                public final void run() {
                    ClipboardOverlayController.$r8$lambda$AZ9Lvq5FbIeS_x13t7fd0xGv27o(ClipboardOverlayController.this);
                }
            };
            this.mView.showEditChip(this.mContext.getString(R$string.clipboard_edit_text_description));
        }
    }

    public final boolean tryShowEditableImage(final Uri uri, boolean z) {
        boolean z2;
        Runnable runnable = new Runnable() { // from class: com.android.systemui.clipboardoverlay.ClipboardOverlayController$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                ClipboardOverlayController.$r8$lambda$7VcwFuyg8uo63cyNUgz8r4mZ1Co(ClipboardOverlayController.this, uri);
            }
        };
        ContentResolver contentResolver = this.mContext.getContentResolver();
        String type = contentResolver.getType(uri);
        boolean z3 = type != null && type.startsWith("image");
        if (z) {
            this.mView.showImagePreview(null);
            z2 = z3;
            if (z3) {
                this.mOnPreviewTapped = runnable;
                this.mView.setEditAccessibilityAction(true);
                z2 = z3;
            }
        } else if (z3) {
            try {
                int dimensionPixelSize = this.mContext.getResources().getDimensionPixelSize(R$dimen.overlay_x_scale);
                this.mView.showImagePreview(contentResolver.loadThumbnail(uri, new Size(dimensionPixelSize, dimensionPixelSize * 4), null));
                this.mView.setEditAccessibilityAction(true);
                this.mOnPreviewTapped = runnable;
                z2 = z3;
            } catch (IOException e) {
                Log.e("ClipboardOverlayCtrlr", "Thumbnail loading failed", e);
                this.mView.showDefaultTextPreview();
                z2 = false;
            }
        } else {
            this.mView.showDefaultTextPreview();
            z2 = z3;
        }
        if (z2 && DeviceConfig.getBoolean("systemui", "clipboard_overlay_show_edit_button", false)) {
            this.mView.showEditChip(this.mContext.getString(R$string.clipboard_edit_image_description));
        }
        return z2;
    }
}