package com.android.systemui.clipboardoverlay;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.app.ICompatCameraControlCallback;
import android.app.RemoteAction;
import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Insets;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.drawable.Icon;
import android.hardware.display.DisplayManager;
import android.hardware.input.InputManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.IBinder;
import android.os.Looper;
import android.provider.DeviceConfig;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.MathUtils;
import android.util.Size;
import android.view.Display;
import android.view.DisplayCutout;
import android.view.InputEvent;
import android.view.InputEventReceiver;
import android.view.InputMonitor;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewRootImpl;
import android.view.ViewTreeObserver;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityManager;
import android.view.animation.LinearInterpolator;
import android.view.animation.PathInterpolator;
import android.view.textclassifier.TextClassificationManager;
import android.view.textclassifier.TextClassifier;
import android.view.textclassifier.TextLinks;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import com.android.internal.logging.UiEventLogger;
import com.android.internal.policy.PhoneWindow;
import com.android.settingslib.widget.ActionBarShadowController;
import com.android.systemui.R$dimen;
import com.android.systemui.R$drawable;
import com.android.systemui.R$id;
import com.android.systemui.R$layout;
import com.android.systemui.R$string;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.broadcast.BroadcastSender;
import com.android.systemui.clipboardoverlay.ClipboardListener;
import com.android.systemui.screenshot.DraggableConstraintLayout;
import com.android.systemui.screenshot.FloatingWindowUtil;
import com.android.systemui.screenshot.OverlayActionChip;
import com.android.systemui.screenshot.TimeoutHandler;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

/* loaded from: mainsysui33.jar:com/android/systemui/clipboardoverlay/ClipboardOverlayControllerLegacy.class */
public class ClipboardOverlayControllerLegacy implements ClipboardListener.ClipboardOverlay {
    public final AccessibilityManager mAccessibilityManager;
    public final LinearLayout mActionContainer;
    public final View mActionContainerBackground;
    public final BroadcastDispatcher mBroadcastDispatcher;
    public final ClipboardLogger mClipboardLogger;
    public final View mClipboardPreview;
    public BroadcastReceiver mCloseDialogsReceiver;
    public final Context mContext;
    public final View mDismissButton;
    public final DisplayManager mDisplayManager;
    public final DisplayMetrics mDisplayMetrics;
    public final OverlayActionChip mEditChip;
    public Animator mEnterAnimator;
    public Animator mExitAnimator;
    public final TextView mHiddenPreview;
    public final ImageView mImagePreview;
    public InputEventReceiver mInputEventReceiver;
    public InputMonitor mInputMonitor;
    public boolean mKeyboardVisible;
    public Runnable mOnSessionCompleteListener;
    public final int mOrientation;
    public final View mPreviewBorder;
    public final OverlayActionChip mRemoteCopyChip;
    public BroadcastReceiver mScreenshotReceiver;
    public final OverlayActionChip mShareChip;
    public final TextClassifier mTextClassifier;
    public final TextView mTextPreview;
    public final TimeoutHandler mTimeoutHandler;
    public final DraggableConstraintLayout mView;
    public final PhoneWindow mWindow;
    public final WindowManager.LayoutParams mWindowLayoutParams;
    public final WindowManager mWindowManager;
    public final ArrayList<OverlayActionChip> mActionChips = new ArrayList<>();
    public boolean mBlockAttach = false;

    /* loaded from: mainsysui33.jar:com/android/systemui/clipboardoverlay/ClipboardOverlayControllerLegacy$ClipboardLogger.class */
    public static class ClipboardLogger {
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
            this.mUiEventLogger.log(uiEventEnum);
        }

        public void reset() {
            this.mGuarded = false;
        }
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.clipboardoverlay.ClipboardOverlayControllerLegacy$$ExternalSyntheticLambda17.onAnimationUpdate(android.animation.ValueAnimator):void] */
    /* renamed from: $r8$lambda$0qvG6BM_9ry-qVOELADYBM7U3R8 */
    public static /* synthetic */ void m1741$r8$lambda$0qvG6BM_9ryqVOELADYBM7U3R8(ClipboardOverlayControllerLegacy clipboardOverlayControllerLegacy, ValueAnimator valueAnimator) {
        clipboardOverlayControllerLegacy.lambda$getExitAnimation$17(valueAnimator);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.clipboardoverlay.ClipboardOverlayControllerLegacy$$ExternalSyntheticLambda10.onClick(android.view.View):void] */
    public static /* synthetic */ void $r8$lambda$2FeIE5kdMQKh8t69VG59ybfxxII(ClipboardOverlayControllerLegacy clipboardOverlayControllerLegacy, View view) {
        clipboardOverlayControllerLegacy.lambda$showEditableText$11(view);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.clipboardoverlay.ClipboardOverlayControllerLegacy$$ExternalSyntheticLambda11.run():void] */
    public static /* synthetic */ void $r8$lambda$2MDnfCsZmHJNEEtb8rv6VbYN47E(ClipboardOverlayControllerLegacy clipboardOverlayControllerLegacy) {
        clipboardOverlayControllerLegacy.animateIn();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.clipboardoverlay.ClipboardOverlayControllerLegacy$$ExternalSyntheticLambda5.onClick(android.view.View):void] */
    /* renamed from: $r8$lambda$N_UotaH-GU_6Wj8IFbatg690yuM */
    public static /* synthetic */ void m1742$r8$lambda$N_UotaHGU_6Wj8IFbatg690yuM(ClipboardOverlayControllerLegacy clipboardOverlayControllerLegacy, Intent intent, View view) {
        clipboardOverlayControllerLegacy.lambda$setClipData$5(intent, view);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.clipboardoverlay.ClipboardOverlayControllerLegacy$$ExternalSyntheticLambda0.onPreDraw():boolean] */
    public static /* synthetic */ boolean $r8$lambda$Qzoh8rr0KSiELib0v3fWMgb2iL4(ClipboardOverlayControllerLegacy clipboardOverlayControllerLegacy) {
        return clipboardOverlayControllerLegacy.lambda$new$0();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.clipboardoverlay.ClipboardOverlayControllerLegacy$$ExternalSyntheticLambda13.onAnimationUpdate(android.animation.ValueAnimator):void] */
    public static /* synthetic */ void $r8$lambda$RouSspEWfc2ULWkzs2VZgwqN0zw(ClipboardOverlayControllerLegacy clipboardOverlayControllerLegacy, ValueAnimator valueAnimator) {
        clipboardOverlayControllerLegacy.lambda$getEnterAnimation$13(valueAnimator);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.clipboardoverlay.ClipboardOverlayControllerLegacy$$ExternalSyntheticLambda4.run():void] */
    public static /* synthetic */ void $r8$lambda$ZFpJr3KOBEfVwUWVvLcENsbnM60(ClipboardOverlayControllerLegacy clipboardOverlayControllerLegacy, ClipData clipData, String str) {
        clipboardOverlayControllerLegacy.lambda$setClipData$4(clipData, str);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.clipboardoverlay.ClipboardOverlayControllerLegacy$$ExternalSyntheticLambda15.onAnimationUpdate(android.animation.ValueAnimator):void] */
    public static /* synthetic */ void $r8$lambda$cXbNaJzOcF1_xmhyFRJU5FvbPA4(ClipboardOverlayControllerLegacy clipboardOverlayControllerLegacy, ValueAnimator valueAnimator) {
        clipboardOverlayControllerLegacy.lambda$getEnterAnimation$15(valueAnimator);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.clipboardoverlay.ClipboardOverlayControllerLegacy$$ExternalSyntheticLambda7.onClick(android.view.View):void] */
    public static /* synthetic */ void $r8$lambda$dSvpyY070DqziwtGCnKBFJbXwzE(ClipboardOverlayControllerLegacy clipboardOverlayControllerLegacy, Uri uri, View view) {
        clipboardOverlayControllerLegacy.lambda$tryShowEditableImage$12(uri, view);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.clipboardoverlay.ClipboardOverlayControllerLegacy$$ExternalSyntheticLambda16.onAnimationUpdate(android.animation.ValueAnimator):void] */
    /* renamed from: $r8$lambda$duQDosID8iFq4zbM0x-37sT0rUE */
    public static /* synthetic */ void m1743$r8$lambda$duQDosID8iFq4zbM0x37sT0rUE(ClipboardOverlayControllerLegacy clipboardOverlayControllerLegacy, ValueAnimator valueAnimator) {
        clipboardOverlayControllerLegacy.lambda$getExitAnimation$16(valueAnimator);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.clipboardoverlay.ClipboardOverlayControllerLegacy$$ExternalSyntheticLambda19.run():void] */
    public static /* synthetic */ void $r8$lambda$fFOv1jod1I_PWbbUCijlKkElTyo(ClipboardOverlayControllerLegacy clipboardOverlayControllerLegacy) {
        clipboardOverlayControllerLegacy.lambda$constructActionChip$9();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.clipboardoverlay.ClipboardOverlayControllerLegacy$$ExternalSyntheticLambda2.run():void] */
    public static /* synthetic */ void $r8$lambda$fO1O2T2W77jnF_IGV5lj_TiOoOE(ClipboardOverlayControllerLegacy clipboardOverlayControllerLegacy) {
        clipboardOverlayControllerLegacy.lambda$new$2();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.clipboardoverlay.ClipboardOverlayControllerLegacy$$ExternalSyntheticLambda18.onAnimationUpdate(android.animation.ValueAnimator):void] */
    public static /* synthetic */ void $r8$lambda$fyQ2xe0PXj1RiUr_t54T30aSqvA(ClipboardOverlayControllerLegacy clipboardOverlayControllerLegacy, ValueAnimator valueAnimator) {
        clipboardOverlayControllerLegacy.lambda$getExitAnimation$18(valueAnimator);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.clipboardoverlay.ClipboardOverlayControllerLegacy$$ExternalSyntheticLambda9.onClick(android.view.View):void] */
    /* renamed from: $r8$lambda$h_sFtQRAzr-TwzgZzub4UNMu9MQ */
    public static /* synthetic */ void m1744$r8$lambda$h_sFtQRAzrTwzgZzub4UNMu9MQ(ClipboardOverlayControllerLegacy clipboardOverlayControllerLegacy, ClipData clipData, View view) {
        clipboardOverlayControllerLegacy.lambda$showShareChip$8(clipData, view);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.clipboardoverlay.ClipboardOverlayControllerLegacy$$ExternalSyntheticLambda12.run():void] */
    public static /* synthetic */ void $r8$lambda$p0XNtuNRuuIJ9riKr0qB982WvdQ(ClipboardOverlayControllerLegacy clipboardOverlayControllerLegacy, ArrayList arrayList, String str) {
        clipboardOverlayControllerLegacy.lambda$classifyText$7(arrayList, str);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.clipboardoverlay.ClipboardOverlayControllerLegacy$$ExternalSyntheticLambda14.onAnimationUpdate(android.animation.ValueAnimator):void] */
    public static /* synthetic */ void $r8$lambda$vYu8RATPci9kN7QqXAjJDNe7gJk(ClipboardOverlayControllerLegacy clipboardOverlayControllerLegacy, ValueAnimator valueAnimator) {
        clipboardOverlayControllerLegacy.lambda$getEnterAnimation$14(valueAnimator);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.clipboardoverlay.ClipboardOverlayControllerLegacy$$ExternalSyntheticLambda6.run():void] */
    public static /* synthetic */ void $r8$lambda$vqCPMAcOggBXDX3RekrDC44QqXw(ClipboardOverlayControllerLegacy clipboardOverlayControllerLegacy, String str) {
        clipboardOverlayControllerLegacy.lambda$setClipData$6(str);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.clipboardoverlay.ClipboardOverlayControllerLegacy$$ExternalSyntheticLambda8.onLayoutChange(android.view.View, int, int, int, int, int, int, int, int):void] */
    public static /* synthetic */ void $r8$lambda$wyDbPQLX8fZ7u3cIsF0Fec1eAjg(ClipboardOverlayControllerLegacy clipboardOverlayControllerLegacy, CharSequence charSequence, TextView textView, View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        clipboardOverlayControllerLegacy.lambda$showTextPreview$10(charSequence, textView, view, i, i2, i3, i4, i5, i6, i7, i8);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.clipboardoverlay.ClipboardOverlayControllerLegacy$$ExternalSyntheticLambda1.onClick(android.view.View):void] */
    public static /* synthetic */ void $r8$lambda$xuNzeNLx5bmhBbv9XcXN6hyLIDI(ClipboardOverlayControllerLegacy clipboardOverlayControllerLegacy, View view) {
        clipboardOverlayControllerLegacy.lambda$new$1(view);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.clipboardoverlay.ClipboardOverlayControllerLegacy$$ExternalSyntheticLambda3.run():void] */
    public static /* synthetic */ void $r8$lambda$yMPxfTsmT45QLGhf0jCTqHSmdJw(ClipboardOverlayControllerLegacy clipboardOverlayControllerLegacy) {
        clipboardOverlayControllerLegacy.lambda$new$3();
    }

    public ClipboardOverlayControllerLegacy(Context context, BroadcastDispatcher broadcastDispatcher, BroadcastSender broadcastSender, TimeoutHandler timeoutHandler, UiEventLogger uiEventLogger) {
        this.mBroadcastDispatcher = broadcastDispatcher;
        DisplayManager displayManager = (DisplayManager) context.getSystemService(DisplayManager.class);
        Objects.requireNonNull(displayManager);
        this.mDisplayManager = displayManager;
        Context createWindowContext = context.createDisplayContext(getDefaultDisplay()).createWindowContext(2036, null);
        this.mContext = createWindowContext;
        this.mClipboardLogger = new ClipboardLogger(uiEventLogger);
        this.mAccessibilityManager = AccessibilityManager.getInstance(createWindowContext);
        TextClassificationManager textClassificationManager = (TextClassificationManager) context.getSystemService(TextClassificationManager.class);
        Objects.requireNonNull(textClassificationManager);
        this.mTextClassifier = textClassificationManager.getTextClassifier();
        WindowManager windowManager = (WindowManager) createWindowContext.getSystemService(WindowManager.class);
        this.mWindowManager = windowManager;
        DisplayMetrics displayMetrics = new DisplayMetrics();
        this.mDisplayMetrics = displayMetrics;
        createWindowContext.getDisplay().getRealMetrics(displayMetrics);
        this.mTimeoutHandler = timeoutHandler;
        timeoutHandler.setDefaultTimeoutMillis(6000);
        WindowManager.LayoutParams floatingWindowParams = FloatingWindowUtil.getFloatingWindowParams();
        this.mWindowLayoutParams = floatingWindowParams;
        floatingWindowParams.setTitle("ClipboardOverlay");
        PhoneWindow floatingWindow = FloatingWindowUtil.getFloatingWindow(createWindowContext);
        this.mWindow = floatingWindow;
        floatingWindow.setWindowManager(windowManager, (IBinder) null, (String) null);
        setWindowFocusable(false);
        DraggableConstraintLayout draggableConstraintLayout = (DraggableConstraintLayout) LayoutInflater.from(createWindowContext).inflate(R$layout.clipboard_overlay_legacy, (ViewGroup) null);
        this.mView = draggableConstraintLayout;
        View findViewById = draggableConstraintLayout.findViewById(R$id.actions_container_background);
        Objects.requireNonNull(findViewById);
        this.mActionContainerBackground = findViewById;
        LinearLayout linearLayout = (LinearLayout) draggableConstraintLayout.findViewById(R$id.actions);
        Objects.requireNonNull(linearLayout);
        this.mActionContainer = linearLayout;
        View findViewById2 = draggableConstraintLayout.findViewById(R$id.clipboard_preview);
        Objects.requireNonNull(findViewById2);
        this.mClipboardPreview = findViewById2;
        ImageView imageView = (ImageView) draggableConstraintLayout.findViewById(R$id.image_preview);
        Objects.requireNonNull(imageView);
        this.mImagePreview = imageView;
        TextView textView = (TextView) draggableConstraintLayout.findViewById(R$id.text_preview);
        Objects.requireNonNull(textView);
        this.mTextPreview = textView;
        TextView textView2 = (TextView) draggableConstraintLayout.findViewById(R$id.hidden_preview);
        Objects.requireNonNull(textView2);
        this.mHiddenPreview = textView2;
        View findViewById3 = draggableConstraintLayout.findViewById(R$id.preview_border);
        Objects.requireNonNull(findViewById3);
        this.mPreviewBorder = findViewById3;
        OverlayActionChip overlayActionChip = (OverlayActionChip) draggableConstraintLayout.findViewById(R$id.edit_chip);
        Objects.requireNonNull(overlayActionChip);
        this.mEditChip = overlayActionChip;
        OverlayActionChip overlayActionChip2 = (OverlayActionChip) draggableConstraintLayout.findViewById(R$id.share_chip);
        Objects.requireNonNull(overlayActionChip2);
        this.mShareChip = overlayActionChip2;
        OverlayActionChip overlayActionChip3 = (OverlayActionChip) draggableConstraintLayout.findViewById(R$id.remote_copy_chip);
        Objects.requireNonNull(overlayActionChip3);
        this.mRemoteCopyChip = overlayActionChip3;
        overlayActionChip.setAlpha(1.0f);
        overlayActionChip2.setAlpha(1.0f);
        overlayActionChip3.setAlpha(1.0f);
        View findViewById4 = draggableConstraintLayout.findViewById(R$id.dismiss_button);
        Objects.requireNonNull(findViewById4);
        this.mDismissButton = findViewById4;
        overlayActionChip2.setContentDescription(createWindowContext.getString(17041530));
        draggableConstraintLayout.setCallbacks(new DraggableConstraintLayout.SwipeDismissCallbacks() { // from class: com.android.systemui.clipboardoverlay.ClipboardOverlayControllerLegacy.1
            {
                ClipboardOverlayControllerLegacy.this = this;
            }

            @Override // com.android.systemui.screenshot.DraggableConstraintLayout.SwipeDismissCallbacks
            public void onDismissComplete() {
                ClipboardOverlayControllerLegacy.this.hideImmediate();
            }

            @Override // com.android.systemui.screenshot.DraggableConstraintLayout.SwipeDismissCallbacks
            public void onInteraction() {
                ClipboardOverlayControllerLegacy.this.mTimeoutHandler.resetTimeout();
            }

            @Override // com.android.systemui.screenshot.DraggableConstraintLayout.SwipeDismissCallbacks
            public void onSwipeDismissInitiated(Animator animator) {
                ClipboardOverlayControllerLegacy.this.mClipboardLogger.logSessionComplete(ClipboardOverlayEvent.CLIPBOARD_OVERLAY_SWIPE_DISMISSED);
                ClipboardOverlayControllerLegacy.this.mExitAnimator = animator;
            }
        });
        textView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() { // from class: com.android.systemui.clipboardoverlay.ClipboardOverlayControllerLegacy$$ExternalSyntheticLambda0
            @Override // android.view.ViewTreeObserver.OnPreDrawListener
            public final boolean onPreDraw() {
                return ClipboardOverlayControllerLegacy.$r8$lambda$Qzoh8rr0KSiELib0v3fWMgb2iL4(ClipboardOverlayControllerLegacy.this);
            }
        });
        findViewById4.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.clipboardoverlay.ClipboardOverlayControllerLegacy$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ClipboardOverlayControllerLegacy.$r8$lambda$xuNzeNLx5bmhBbv9XcXN6hyLIDI(ClipboardOverlayControllerLegacy.this, view);
            }
        });
        overlayActionChip.setIcon(Icon.createWithResource(createWindowContext, R$drawable.ic_screenshot_edit), true);
        overlayActionChip3.setIcon(Icon.createWithResource(createWindowContext, R$drawable.ic_baseline_devices_24), true);
        overlayActionChip2.setIcon(Icon.createWithResource(createWindowContext, R$drawable.ic_screenshot_share), true);
        this.mOrientation = createWindowContext.getResources().getConfiguration().orientation;
        attachWindow();
        withWindowAttached(new Runnable() { // from class: com.android.systemui.clipboardoverlay.ClipboardOverlayControllerLegacy$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                ClipboardOverlayControllerLegacy.$r8$lambda$fO1O2T2W77jnF_IGV5lj_TiOoOE(ClipboardOverlayControllerLegacy.this);
            }
        });
        timeoutHandler.setOnTimeoutRunnable(new Runnable() { // from class: com.android.systemui.clipboardoverlay.ClipboardOverlayControllerLegacy$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                ClipboardOverlayControllerLegacy.$r8$lambda$yMPxfTsmT45QLGhf0jCTqHSmdJw(ClipboardOverlayControllerLegacy.this);
            }
        });
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() { // from class: com.android.systemui.clipboardoverlay.ClipboardOverlayControllerLegacy.4
            {
                ClipboardOverlayControllerLegacy.this = this;
            }

            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                if ("android.intent.action.CLOSE_SYSTEM_DIALOGS".equals(intent.getAction())) {
                    ClipboardOverlayControllerLegacy.this.mClipboardLogger.logSessionComplete(ClipboardOverlayEvent.CLIPBOARD_OVERLAY_DISMISSED_OTHER);
                    ClipboardOverlayControllerLegacy.this.animateOut();
                }
            }
        };
        this.mCloseDialogsReceiver = broadcastReceiver;
        broadcastDispatcher.registerReceiver(broadcastReceiver, new IntentFilter("android.intent.action.CLOSE_SYSTEM_DIALOGS"));
        BroadcastReceiver broadcastReceiver2 = new BroadcastReceiver() { // from class: com.android.systemui.clipboardoverlay.ClipboardOverlayControllerLegacy.5
            {
                ClipboardOverlayControllerLegacy.this = this;
            }

            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                if ("com.android.systemui.SCREENSHOT".equals(intent.getAction())) {
                    ClipboardOverlayControllerLegacy.this.mClipboardLogger.logSessionComplete(ClipboardOverlayEvent.CLIPBOARD_OVERLAY_DISMISSED_OTHER);
                    ClipboardOverlayControllerLegacy.this.animateOut();
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

    public static boolean fitsInView(CharSequence charSequence, TextView textView, Paint paint, float f) {
        paint.setTextSize(f);
        return paint.measureText(charSequence.toString()) < ((float) ((textView.getWidth() - textView.getPaddingLeft()) - textView.getPaddingRight()));
    }

    public static boolean isOneWord(CharSequence charSequence) {
        boolean z = true;
        if (charSequence.toString().split("\\s+", 2).length != 1) {
            z = false;
        }
        return z;
    }

    public /* synthetic */ void lambda$classifyText$7(ArrayList arrayList, String str) {
        resetActionChips();
        if (arrayList.size() > 0) {
            this.mActionContainerBackground.setVisibility(0);
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                RemoteAction remoteAction = (RemoteAction) it.next();
                ComponentName component = remoteAction.getActionIntent().getIntent().getComponent();
                if (component != null && !TextUtils.equals(str, component.getPackageName())) {
                    OverlayActionChip constructActionChip = constructActionChip(remoteAction);
                    this.mActionContainer.addView(constructActionChip);
                    this.mActionChips.add(constructActionChip);
                    return;
                }
            }
        }
    }

    public /* synthetic */ void lambda$constructActionChip$9() {
        this.mClipboardLogger.logSessionComplete(ClipboardOverlayEvent.CLIPBOARD_OVERLAY_ACTION_TAPPED);
        animateOut();
    }

    public /* synthetic */ void lambda$getEnterAnimation$13(ValueAnimator valueAnimator) {
        this.mView.setAlpha(valueAnimator.getAnimatedFraction());
    }

    public /* synthetic */ void lambda$getEnterAnimation$14(ValueAnimator valueAnimator) {
        float lerp = MathUtils.lerp(0.9f, 1.0f, valueAnimator.getAnimatedFraction());
        this.mClipboardPreview.setScaleX(lerp);
        this.mClipboardPreview.setScaleY(lerp);
        this.mPreviewBorder.setScaleX(lerp);
        this.mPreviewBorder.setScaleY(lerp);
        float width = (this.mClipboardPreview.getWidth() / 2.0f) + this.mClipboardPreview.getX();
        View view = this.mActionContainerBackground;
        view.setPivotX(width - view.getX());
        LinearLayout linearLayout = this.mActionContainer;
        linearLayout.setPivotX(width - ((View) linearLayout.getParent()).getX());
        float lerp2 = MathUtils.lerp(0.7f, 1.0f, valueAnimator.getAnimatedFraction());
        float lerp3 = MathUtils.lerp(0.9f, 1.0f, valueAnimator.getAnimatedFraction());
        this.mActionContainer.setScaleX(lerp2);
        this.mActionContainer.setScaleY(lerp3);
        this.mActionContainerBackground.setScaleX(lerp2);
        this.mActionContainerBackground.setScaleY(lerp3);
    }

    public /* synthetic */ void lambda$getEnterAnimation$15(ValueAnimator valueAnimator) {
        float animatedFraction = valueAnimator.getAnimatedFraction();
        this.mClipboardPreview.setAlpha(animatedFraction);
        this.mPreviewBorder.setAlpha(animatedFraction);
        this.mDismissButton.setAlpha(animatedFraction);
        this.mActionContainer.setAlpha(animatedFraction);
    }

    public /* synthetic */ void lambda$getExitAnimation$16(ValueAnimator valueAnimator) {
        this.mView.setAlpha(1.0f - valueAnimator.getAnimatedFraction());
    }

    public /* synthetic */ void lambda$getExitAnimation$17(ValueAnimator valueAnimator) {
        float lerp = MathUtils.lerp(1.0f, 0.9f, valueAnimator.getAnimatedFraction());
        this.mClipboardPreview.setScaleX(lerp);
        this.mClipboardPreview.setScaleY(lerp);
        this.mPreviewBorder.setScaleX(lerp);
        this.mPreviewBorder.setScaleY(lerp);
        float width = (this.mClipboardPreview.getWidth() / 2.0f) + this.mClipboardPreview.getX();
        View view = this.mActionContainerBackground;
        view.setPivotX(width - view.getX());
        LinearLayout linearLayout = this.mActionContainer;
        linearLayout.setPivotX(width - ((View) linearLayout.getParent()).getX());
        float lerp2 = MathUtils.lerp(1.0f, 0.8f, valueAnimator.getAnimatedFraction());
        float lerp3 = MathUtils.lerp(1.0f, 0.9f, valueAnimator.getAnimatedFraction());
        this.mActionContainer.setScaleX(lerp2);
        this.mActionContainer.setScaleY(lerp3);
        this.mActionContainerBackground.setScaleX(lerp2);
        this.mActionContainerBackground.setScaleY(lerp3);
    }

    public /* synthetic */ void lambda$getExitAnimation$18(ValueAnimator valueAnimator) {
        float animatedFraction = 1.0f - valueAnimator.getAnimatedFraction();
        this.mClipboardPreview.setAlpha(animatedFraction);
        this.mPreviewBorder.setAlpha(animatedFraction);
        this.mDismissButton.setAlpha(animatedFraction);
        this.mActionContainer.setAlpha(animatedFraction);
    }

    public /* synthetic */ boolean lambda$new$0() {
        int height = this.mTextPreview.getHeight();
        int paddingTop = this.mTextPreview.getPaddingTop();
        int paddingBottom = this.mTextPreview.getPaddingBottom();
        TextView textView = this.mTextPreview;
        textView.setMaxLines((height - (paddingTop + paddingBottom)) / textView.getLineHeight());
        return true;
    }

    public /* synthetic */ void lambda$new$1(View view) {
        this.mClipboardLogger.logSessionComplete(ClipboardOverlayEvent.CLIPBOARD_OVERLAY_DISMISS_TAPPED);
        animateOut();
    }

    public /* synthetic */ void lambda$new$2() {
        this.mWindow.setContentView(this.mView);
        WindowInsets windowInsets = this.mWindowManager.getCurrentWindowMetrics().getWindowInsets();
        this.mKeyboardVisible = windowInsets.isVisible(WindowInsets.Type.ime());
        updateInsets(windowInsets);
        this.mWindow.peekDecorView().getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.android.systemui.clipboardoverlay.ClipboardOverlayControllerLegacy.2
            {
                ClipboardOverlayControllerLegacy.this = this;
            }

            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public void onGlobalLayout() {
                WindowInsets windowInsets2 = ClipboardOverlayControllerLegacy.this.mWindowManager.getCurrentWindowMetrics().getWindowInsets();
                boolean isVisible = windowInsets2.isVisible(WindowInsets.Type.ime());
                if (isVisible != ClipboardOverlayControllerLegacy.this.mKeyboardVisible) {
                    ClipboardOverlayControllerLegacy.this.mKeyboardVisible = isVisible;
                    ClipboardOverlayControllerLegacy.this.updateInsets(windowInsets2);
                }
            }
        });
        this.mWindow.peekDecorView().getViewRootImpl().setActivityConfigCallback(new ViewRootImpl.ActivityConfigCallback() { // from class: com.android.systemui.clipboardoverlay.ClipboardOverlayControllerLegacy.3
            {
                ClipboardOverlayControllerLegacy.this = this;
            }

            public void onConfigurationChanged(Configuration configuration, int i) {
                if (ClipboardOverlayControllerLegacy.this.mContext.getResources().getConfiguration().orientation != ClipboardOverlayControllerLegacy.this.mOrientation) {
                    ClipboardOverlayControllerLegacy.this.mClipboardLogger.logSessionComplete(ClipboardOverlayEvent.CLIPBOARD_OVERLAY_DISMISSED_OTHER);
                    ClipboardOverlayControllerLegacy.this.hideImmediate();
                }
            }

            public void requestCompatCameraControl(boolean z, boolean z2, ICompatCameraControlCallback iCompatCameraControlCallback) {
                Log.w("ClipboardOverlayCtrlr", "unexpected requestCompatCameraControl call");
            }
        });
    }

    public /* synthetic */ void lambda$new$3() {
        this.mClipboardLogger.logSessionComplete(ClipboardOverlayEvent.CLIPBOARD_OVERLAY_TIMED_OUT);
        animateOut();
    }

    public /* synthetic */ void lambda$setClipData$4(ClipData clipData, String str) {
        classifyText(clipData.getItemAt(0), str);
    }

    public /* synthetic */ void lambda$setClipData$5(Intent intent, View view) {
        this.mClipboardLogger.logSessionComplete(ClipboardOverlayEvent.CLIPBOARD_OVERLAY_REMOTE_COPY_TAPPED);
        this.mContext.startActivity(intent);
        animateOut();
    }

    public /* synthetic */ void lambda$setClipData$6(String str) {
        Animator animator = this.mEnterAnimator;
        if (animator == null || !animator.isRunning()) {
            this.mView.post(new Runnable() { // from class: com.android.systemui.clipboardoverlay.ClipboardOverlayControllerLegacy$$ExternalSyntheticLambda11
                @Override // java.lang.Runnable
                public final void run() {
                    ClipboardOverlayControllerLegacy.$r8$lambda$2MDnfCsZmHJNEEtb8rv6VbYN47E(ClipboardOverlayControllerLegacy.this);
                }
            });
        }
        this.mView.announceForAccessibility(str);
    }

    public /* synthetic */ void lambda$showEditableText$11(View view) {
        editText();
    }

    public /* synthetic */ void lambda$showShareChip$8(ClipData clipData, View view) {
        shareContent(clipData);
    }

    public /* synthetic */ void lambda$showTextPreview$10(CharSequence charSequence, TextView textView, View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        if (i3 - i != i7 - i5) {
            updateTextSize(charSequence, textView);
        }
    }

    public /* synthetic */ void lambda$tryShowEditableImage$12(Uri uri, View view) {
        editImage(uri);
    }

    public final void animateIn() {
        if (this.mAccessibilityManager.isEnabled()) {
            this.mDismissButton.setVisibility(0);
        }
        Animator enterAnimation = getEnterAnimation();
        this.mEnterAnimator = enterAnimation;
        enterAnimation.start();
    }

    public final void animateOut() {
        Animator animator = this.mExitAnimator;
        if (animator == null || !animator.isRunning()) {
            Animator exitAnimation = getExitAnimation();
            exitAnimation.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.clipboardoverlay.ClipboardOverlayControllerLegacy.7
                public boolean mCancelled;

                {
                    ClipboardOverlayControllerLegacy.this = this;
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
                    ClipboardOverlayControllerLegacy.this.hideImmediate();
                }
            });
            this.mExitAnimator = exitAnimation;
            exitAnimation.start();
        }
    }

    public final void attachWindow() {
        View decorView = this.mWindow.getDecorView();
        if (decorView.isAttachedToWindow() || this.mBlockAttach) {
            return;
        }
        this.mBlockAttach = true;
        this.mWindowManager.addView(decorView, this.mWindowLayoutParams);
        decorView.requestApplyInsets();
        this.mView.requestApplyInsets();
        decorView.getViewTreeObserver().addOnWindowAttachListener(new ViewTreeObserver.OnWindowAttachListener() { // from class: com.android.systemui.clipboardoverlay.ClipboardOverlayControllerLegacy.9
            {
                ClipboardOverlayControllerLegacy.this = this;
            }

            @Override // android.view.ViewTreeObserver.OnWindowAttachListener
            public void onWindowAttached() {
                ClipboardOverlayControllerLegacy.this.mBlockAttach = false;
            }

            @Override // android.view.ViewTreeObserver.OnWindowAttachListener
            public void onWindowDetached() {
            }
        });
    }

    public final void classifyText(ClipData.Item item, final String str) {
        final ArrayList arrayList = new ArrayList();
        for (TextLinks.TextLink textLink : item.getTextLinks().getLinks()) {
            arrayList.addAll(this.mTextClassifier.classifyText(item.getText(), textLink.getStart(), textLink.getEnd(), null).getActions());
        }
        this.mView.post(new Runnable() { // from class: com.android.systemui.clipboardoverlay.ClipboardOverlayControllerLegacy$$ExternalSyntheticLambda12
            @Override // java.lang.Runnable
            public final void run() {
                ClipboardOverlayControllerLegacy.$r8$lambda$p0XNtuNRuuIJ9riKr0qB982WvdQ(ClipboardOverlayControllerLegacy.this, arrayList, str);
            }
        });
    }

    public final OverlayActionChip constructActionChip(RemoteAction remoteAction) {
        OverlayActionChip overlayActionChip = (OverlayActionChip) LayoutInflater.from(this.mContext).inflate(R$layout.overlay_action_chip, (ViewGroup) this.mActionContainer, false);
        overlayActionChip.setText(remoteAction.getTitle());
        overlayActionChip.setContentDescription(remoteAction.getTitle());
        overlayActionChip.setIcon(remoteAction.getIcon(), false);
        overlayActionChip.setPendingIntent(remoteAction.getActionIntent(), new Runnable() { // from class: com.android.systemui.clipboardoverlay.ClipboardOverlayControllerLegacy$$ExternalSyntheticLambda19
            @Override // java.lang.Runnable
            public final void run() {
                ClipboardOverlayControllerLegacy.$r8$lambda$fFOv1jod1I_PWbbUCijlKkElTyo(ClipboardOverlayControllerLegacy.this);
            }
        });
        overlayActionChip.setAlpha(1.0f);
        return overlayActionChip;
    }

    public final void editImage(Uri uri) {
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

    public final Animator getEnterAnimation() {
        LinearInterpolator linearInterpolator = new LinearInterpolator();
        PathInterpolator pathInterpolator = new PathInterpolator(ActionBarShadowController.ELEVATION_LOW, ActionBarShadowController.ELEVATION_LOW, ActionBarShadowController.ELEVATION_LOW, 1.0f);
        AnimatorSet animatorSet = new AnimatorSet();
        ValueAnimator ofFloat = ValueAnimator.ofFloat(ActionBarShadowController.ELEVATION_LOW, 1.0f);
        ofFloat.setInterpolator(linearInterpolator);
        ofFloat.setDuration(66L);
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.clipboardoverlay.ClipboardOverlayControllerLegacy$$ExternalSyntheticLambda13
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                ClipboardOverlayControllerLegacy.$r8$lambda$RouSspEWfc2ULWkzs2VZgwqN0zw(ClipboardOverlayControllerLegacy.this, valueAnimator);
            }
        });
        ValueAnimator ofFloat2 = ValueAnimator.ofFloat(ActionBarShadowController.ELEVATION_LOW, 1.0f);
        ofFloat2.setInterpolator(pathInterpolator);
        ofFloat2.setDuration(333L);
        ofFloat2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.clipboardoverlay.ClipboardOverlayControllerLegacy$$ExternalSyntheticLambda14
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                ClipboardOverlayControllerLegacy.$r8$lambda$vYu8RATPci9kN7QqXAjJDNe7gJk(ClipboardOverlayControllerLegacy.this, valueAnimator);
            }
        });
        ValueAnimator ofFloat3 = ValueAnimator.ofFloat(ActionBarShadowController.ELEVATION_LOW, 1.0f);
        ofFloat3.setInterpolator(linearInterpolator);
        ofFloat3.setDuration(283L);
        ofFloat3.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.clipboardoverlay.ClipboardOverlayControllerLegacy$$ExternalSyntheticLambda15
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                ClipboardOverlayControllerLegacy.$r8$lambda$cXbNaJzOcF1_xmhyFRJU5FvbPA4(ClipboardOverlayControllerLegacy.this, valueAnimator);
            }
        });
        this.mActionContainer.setAlpha(ActionBarShadowController.ELEVATION_LOW);
        this.mPreviewBorder.setAlpha(ActionBarShadowController.ELEVATION_LOW);
        this.mClipboardPreview.setAlpha(ActionBarShadowController.ELEVATION_LOW);
        animatorSet.play(ofFloat).with(ofFloat2);
        animatorSet.play(ofFloat3).after(50L).after(ofFloat);
        animatorSet.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.clipboardoverlay.ClipboardOverlayControllerLegacy.8
            {
                ClipboardOverlayControllerLegacy.this = this;
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                super.onAnimationEnd(animator);
                ClipboardOverlayControllerLegacy.this.mView.setAlpha(1.0f);
                ClipboardOverlayControllerLegacy.this.mTimeoutHandler.resetTimeout();
            }
        });
        return animatorSet;
    }

    public final Animator getExitAnimation() {
        LinearInterpolator linearInterpolator = new LinearInterpolator();
        PathInterpolator pathInterpolator = new PathInterpolator(0.3f, ActionBarShadowController.ELEVATION_LOW, 1.0f, 1.0f);
        AnimatorSet animatorSet = new AnimatorSet();
        ValueAnimator ofFloat = ValueAnimator.ofFloat(ActionBarShadowController.ELEVATION_LOW, 1.0f);
        ofFloat.setInterpolator(linearInterpolator);
        ofFloat.setDuration(100L);
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.clipboardoverlay.ClipboardOverlayControllerLegacy$$ExternalSyntheticLambda16
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                ClipboardOverlayControllerLegacy.m1743$r8$lambda$duQDosID8iFq4zbM0x37sT0rUE(ClipboardOverlayControllerLegacy.this, valueAnimator);
            }
        });
        ValueAnimator ofFloat2 = ValueAnimator.ofFloat(ActionBarShadowController.ELEVATION_LOW, 1.0f);
        ofFloat2.setInterpolator(pathInterpolator);
        ofFloat2.setDuration(250L);
        ofFloat2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.clipboardoverlay.ClipboardOverlayControllerLegacy$$ExternalSyntheticLambda17
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                ClipboardOverlayControllerLegacy.m1741$r8$lambda$0qvG6BM_9ryqVOELADYBM7U3R8(ClipboardOverlayControllerLegacy.this, valueAnimator);
            }
        });
        ValueAnimator ofFloat3 = ValueAnimator.ofFloat(ActionBarShadowController.ELEVATION_LOW, 1.0f);
        ofFloat3.setInterpolator(linearInterpolator);
        ofFloat3.setDuration(166L);
        ofFloat3.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.clipboardoverlay.ClipboardOverlayControllerLegacy$$ExternalSyntheticLambda18
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                ClipboardOverlayControllerLegacy.$r8$lambda$fyQ2xe0PXj1RiUr_t54T30aSqvA(ClipboardOverlayControllerLegacy.this, valueAnimator);
            }
        });
        animatorSet.play(ofFloat3).with(ofFloat2);
        animatorSet.play(ofFloat).after(150L).after(ofFloat3);
        return animatorSet;
    }

    public final void hideImmediate() {
        this.mTimeoutHandler.cancelTimeout();
        View peekDecorView = this.mWindow.peekDecorView();
        if (peekDecorView != null && peekDecorView.isAttachedToWindow()) {
            this.mWindowManager.removeViewImmediate(peekDecorView);
        }
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

    public final void monitorOutsideTouches() {
        this.mInputMonitor = ((InputManager) this.mContext.getSystemService(InputManager.class)).monitorGestureInput("clipboard overlay", 0);
        this.mInputEventReceiver = new InputEventReceiver(this.mInputMonitor.getInputChannel(), Looper.getMainLooper()) { // from class: com.android.systemui.clipboardoverlay.ClipboardOverlayControllerLegacy.6
            {
                ClipboardOverlayControllerLegacy.this = this;
            }

            public void onInputEvent(InputEvent inputEvent) {
                if (inputEvent instanceof MotionEvent) {
                    MotionEvent motionEvent = (MotionEvent) inputEvent;
                    if (motionEvent.getActionMasked() == 0) {
                        Region region = new Region();
                        Rect rect = new Rect();
                        ClipboardOverlayControllerLegacy.this.mPreviewBorder.getBoundsOnScreen(rect);
                        rect.inset((int) FloatingWindowUtil.dpToPx(ClipboardOverlayControllerLegacy.this.mDisplayMetrics, -12.0f), (int) FloatingWindowUtil.dpToPx(ClipboardOverlayControllerLegacy.this.mDisplayMetrics, -12.0f));
                        region.op(rect, Region.Op.UNION);
                        ClipboardOverlayControllerLegacy.this.mActionContainerBackground.getBoundsOnScreen(rect);
                        rect.inset((int) FloatingWindowUtil.dpToPx(ClipboardOverlayControllerLegacy.this.mDisplayMetrics, -12.0f), (int) FloatingWindowUtil.dpToPx(ClipboardOverlayControllerLegacy.this.mDisplayMetrics, -12.0f));
                        region.op(rect, Region.Op.UNION);
                        ClipboardOverlayControllerLegacy.this.mDismissButton.getBoundsOnScreen(rect);
                        region.op(rect, Region.Op.UNION);
                        if (!region.contains((int) motionEvent.getRawX(), (int) motionEvent.getRawY())) {
                            ClipboardOverlayControllerLegacy.this.mClipboardLogger.logSessionComplete(ClipboardOverlayEvent.CLIPBOARD_OVERLAY_TAP_OUTSIDE);
                            ClipboardOverlayControllerLegacy.this.animateOut();
                        }
                    }
                }
                finishInputEvent(inputEvent, true);
            }
        };
    }

    public final void reset() {
        this.mView.setTranslationX(ActionBarShadowController.ELEVATION_LOW);
        this.mView.setAlpha(ActionBarShadowController.ELEVATION_LOW);
        this.mActionContainerBackground.setVisibility(8);
        this.mShareChip.setVisibility(8);
        this.mEditChip.setVisibility(8);
        this.mRemoteCopyChip.setVisibility(8);
        resetActionChips();
        this.mTimeoutHandler.cancelTimeout();
        this.mClipboardLogger.reset();
    }

    public final void resetActionChips() {
        Iterator<OverlayActionChip> it = this.mActionChips.iterator();
        while (it.hasNext()) {
            this.mActionContainer.removeView(it.next());
        }
        this.mActionChips.clear();
    }

    public final void setAccessibilityActionToEdit(View view) {
        ViewCompat.replaceAccessibilityAction(view, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_CLICK, this.mContext.getString(R$string.clipboard_edit), null);
    }

    @Override // com.android.systemui.clipboardoverlay.ClipboardListener.ClipboardOverlay
    public void setClipData(final ClipData clipData, final String str) {
        String string;
        Animator animator = this.mExitAnimator;
        if (animator != null && animator.isRunning()) {
            this.mExitAnimator.cancel();
        }
        reset();
        boolean z = (clipData == null || clipData.getDescription().getExtras() == null || !clipData.getDescription().getExtras().getBoolean("android.content.extra.IS_SENSITIVE")) ? false : true;
        if (clipData == null || clipData.getItemCount() == 0) {
            showTextPreview(this.mContext.getResources().getString(R$string.clipboard_overlay_text_copied), this.mTextPreview);
            string = this.mContext.getString(R$string.clipboard_content_copied);
        } else if (!TextUtils.isEmpty(clipData.getItemAt(0).getText())) {
            ClipData.Item itemAt = clipData.getItemAt(0);
            if (DeviceConfig.getBoolean("systemui", "clipboard_overlay_show_actions", false) && itemAt.getTextLinks() != null) {
                AsyncTask.execute(new Runnable() { // from class: com.android.systemui.clipboardoverlay.ClipboardOverlayControllerLegacy$$ExternalSyntheticLambda4
                    @Override // java.lang.Runnable
                    public final void run() {
                        ClipboardOverlayControllerLegacy.$r8$lambda$ZFpJr3KOBEfVwUWVvLcENsbnM60(ClipboardOverlayControllerLegacy.this, clipData, str);
                    }
                });
            }
            if (z) {
                showEditableText(this.mContext.getResources().getString(R$string.clipboard_asterisks), true);
            } else {
                showEditableText(itemAt.getText(), false);
            }
            showShareChip(clipData);
            string = this.mContext.getString(R$string.clipboard_text_copied);
        } else if (clipData.getItemAt(0).getUri() == null) {
            showTextPreview(this.mContext.getResources().getString(R$string.clipboard_overlay_text_copied), this.mTextPreview);
            string = this.mContext.getString(R$string.clipboard_content_copied);
        } else if (tryShowEditableImage(clipData.getItemAt(0).getUri(), z)) {
            showShareChip(clipData);
            string = this.mContext.getString(R$string.clipboard_image_copied);
        } else {
            string = this.mContext.getString(R$string.clipboard_content_copied);
        }
        final Intent remoteCopyIntent = IntentCreator.getRemoteCopyIntent(clipData, this.mContext);
        if (this.mContext.getPackageManager().resolveActivity(remoteCopyIntent, PackageManager.ResolveInfoFlags.of(0L)) != null) {
            this.mRemoteCopyChip.setContentDescription(this.mContext.getString(R$string.clipboard_send_nearby_description));
            this.mRemoteCopyChip.setVisibility(0);
            this.mRemoteCopyChip.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.clipboardoverlay.ClipboardOverlayControllerLegacy$$ExternalSyntheticLambda5
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    ClipboardOverlayControllerLegacy.m1742$r8$lambda$N_UotaHGU_6Wj8IFbatg690yuM(ClipboardOverlayControllerLegacy.this, remoteCopyIntent, view);
                }
            });
            this.mActionContainerBackground.setVisibility(0);
        } else {
            this.mRemoteCopyChip.setVisibility(8);
        }
        final String str2 = string;
        withWindowAttached(new Runnable() { // from class: com.android.systemui.clipboardoverlay.ClipboardOverlayControllerLegacy$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                ClipboardOverlayControllerLegacy.$r8$lambda$vqCPMAcOggBXDX3RekrDC44QqXw(ClipboardOverlayControllerLegacy.this, str2);
            }
        });
        this.mTimeoutHandler.resetTimeout();
    }

    @Override // com.android.systemui.clipboardoverlay.ClipboardListener.ClipboardOverlay
    public void setOnSessionCompleteListener(Runnable runnable) {
        this.mOnSessionCompleteListener = runnable;
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

    public final void shareContent(ClipData clipData) {
        this.mClipboardLogger.logSessionComplete(ClipboardOverlayEvent.CLIPBOARD_OVERLAY_SHARE_TAPPED);
        Context context = this.mContext;
        context.startActivity(IntentCreator.getShareIntent(clipData, context));
        animateOut();
    }

    public final void showEditableText(CharSequence charSequence, boolean z) {
        TextView textView = z ? this.mHiddenPreview : this.mTextPreview;
        showTextPreview(charSequence, textView);
        View.OnClickListener onClickListener = new View.OnClickListener() { // from class: com.android.systemui.clipboardoverlay.ClipboardOverlayControllerLegacy$$ExternalSyntheticLambda10
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ClipboardOverlayControllerLegacy.$r8$lambda$2FeIE5kdMQKh8t69VG59ybfxxII(ClipboardOverlayControllerLegacy.this, view);
            }
        };
        setAccessibilityActionToEdit(textView);
        if (DeviceConfig.getBoolean("systemui", "clipboard_overlay_show_edit_button", false)) {
            this.mEditChip.setVisibility(0);
            this.mActionContainerBackground.setVisibility(0);
            this.mEditChip.setContentDescription(this.mContext.getString(R$string.clipboard_edit_text_description));
            this.mEditChip.setOnClickListener(onClickListener);
        }
        textView.setOnClickListener(onClickListener);
    }

    public final void showShareChip(final ClipData clipData) {
        this.mShareChip.setVisibility(0);
        this.mActionContainerBackground.setVisibility(0);
        this.mShareChip.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.clipboardoverlay.ClipboardOverlayControllerLegacy$$ExternalSyntheticLambda9
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ClipboardOverlayControllerLegacy.m1744$r8$lambda$h_sFtQRAzrTwzgZzub4UNMu9MQ(ClipboardOverlayControllerLegacy.this, clipData, view);
            }
        });
    }

    public final void showSinglePreview(View view) {
        this.mTextPreview.setVisibility(8);
        this.mImagePreview.setVisibility(8);
        this.mHiddenPreview.setVisibility(8);
        view.setVisibility(0);
    }

    public final void showTextPreview(CharSequence charSequence, final TextView textView) {
        showSinglePreview(textView);
        final CharSequence subSequence = charSequence.subSequence(0, Math.min(500, charSequence.length()));
        textView.setText(subSequence);
        updateTextSize(subSequence, textView);
        textView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() { // from class: com.android.systemui.clipboardoverlay.ClipboardOverlayControllerLegacy$$ExternalSyntheticLambda8
            @Override // android.view.View.OnLayoutChangeListener
            public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                ClipboardOverlayControllerLegacy.$r8$lambda$wyDbPQLX8fZ7u3cIsF0Fec1eAjg(ClipboardOverlayControllerLegacy.this, subSequence, textView, view, i, i2, i3, i4, i5, i6, i7, i8);
            }
        });
        this.mEditChip.setVisibility(8);
    }

    public final boolean tryShowEditableImage(final Uri uri, boolean z) {
        boolean z2;
        View.OnClickListener onClickListener = new View.OnClickListener() { // from class: com.android.systemui.clipboardoverlay.ClipboardOverlayControllerLegacy$$ExternalSyntheticLambda7
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ClipboardOverlayControllerLegacy.$r8$lambda$dSvpyY070DqziwtGCnKBFJbXwzE(ClipboardOverlayControllerLegacy.this, uri, view);
            }
        };
        ContentResolver contentResolver = this.mContext.getContentResolver();
        String type = contentResolver.getType(uri);
        boolean z3 = type != null && type.startsWith("image");
        if (z) {
            this.mHiddenPreview.setText(this.mContext.getString(R$string.clipboard_text_hidden));
            showSinglePreview(this.mHiddenPreview);
            z2 = z3;
            if (z3) {
                this.mHiddenPreview.setOnClickListener(onClickListener);
                setAccessibilityActionToEdit(this.mHiddenPreview);
                z2 = z3;
            }
        } else if (z3) {
            try {
                int dimensionPixelSize = this.mContext.getResources().getDimensionPixelSize(R$dimen.overlay_x_scale);
                Bitmap loadThumbnail = contentResolver.loadThumbnail(uri, new Size(dimensionPixelSize, dimensionPixelSize * 4), null);
                showSinglePreview(this.mImagePreview);
                this.mImagePreview.setImageBitmap(loadThumbnail);
                this.mImagePreview.setOnClickListener(onClickListener);
                setAccessibilityActionToEdit(this.mImagePreview);
                z2 = z3;
            } catch (IOException e) {
                Log.e("ClipboardOverlayCtrlr", "Thumbnail loading failed", e);
                showTextPreview(this.mContext.getResources().getString(R$string.clipboard_overlay_text_copied), this.mTextPreview);
                z2 = false;
            }
        } else {
            showTextPreview(this.mContext.getResources().getString(R$string.clipboard_overlay_text_copied), this.mTextPreview);
            z2 = z3;
        }
        if (z2 && DeviceConfig.getBoolean("systemui", "clipboard_overlay_show_edit_button", false)) {
            this.mEditChip.setVisibility(0);
            this.mActionContainerBackground.setVisibility(0);
            this.mEditChip.setOnClickListener(onClickListener);
            this.mEditChip.setContentDescription(this.mContext.getString(R$string.clipboard_edit_image_description));
        }
        return z2;
    }

    public final void updateInsets(WindowInsets windowInsets) {
        int i = this.mContext.getResources().getConfiguration().orientation;
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.mView.getLayoutParams();
        if (layoutParams == null) {
            return;
        }
        DisplayCutout displayCutout = windowInsets.getDisplayCutout();
        Insets insets = windowInsets.getInsets(WindowInsets.Type.navigationBars());
        Insets insets2 = windowInsets.getInsets(WindowInsets.Type.ime());
        if (displayCutout == null) {
            layoutParams.setMargins(0, 0, 0, Math.max(insets2.bottom, insets.bottom));
        } else {
            Insets waterfallInsets = displayCutout.getWaterfallInsets();
            if (i == 1) {
                layoutParams.setMargins(waterfallInsets.left, Math.max(displayCutout.getSafeInsetTop(), waterfallInsets.top), waterfallInsets.right, Math.max(insets2.bottom, Math.max(displayCutout.getSafeInsetBottom(), Math.max(insets.bottom, waterfallInsets.bottom))));
            } else {
                layoutParams.setMargins(waterfallInsets.left, waterfallInsets.top, waterfallInsets.right, Math.max(insets2.bottom, Math.max(insets.bottom, waterfallInsets.bottom)));
            }
        }
        this.mView.setLayoutParams(layoutParams);
        this.mView.requestLayout();
    }

    public final void updateTextSize(CharSequence charSequence, TextView textView) {
        Paint paint = new Paint(textView.getPaint());
        Resources resources = textView.getResources();
        float dimensionPixelSize = resources.getDimensionPixelSize(R$dimen.clipboard_overlay_min_font);
        float dimensionPixelSize2 = resources.getDimensionPixelSize(R$dimen.clipboard_overlay_max_font);
        if (!isOneWord(charSequence) || !fitsInView(charSequence, textView, paint, dimensionPixelSize)) {
            textView.setAutoSizeTextTypeUniformWithConfiguration((int) dimensionPixelSize, (int) dimensionPixelSize2, 4, 0);
            textView.setGravity(8388627);
            return;
        }
        while (true) {
            float f = 4.0f + dimensionPixelSize;
            if (f >= dimensionPixelSize2 || !fitsInView(charSequence, textView, paint, f)) {
                break;
            }
            dimensionPixelSize = f;
        }
        textView.setAutoSizeTextTypeWithDefaults(0);
        textView.setGravity(17);
        textView.setTextSize(0, (int) dimensionPixelSize);
    }

    public final void withWindowAttached(final Runnable runnable) {
        final View decorView = this.mWindow.getDecorView();
        if (decorView.isAttachedToWindow()) {
            runnable.run();
        } else {
            decorView.getViewTreeObserver().addOnWindowAttachListener(new ViewTreeObserver.OnWindowAttachListener() { // from class: com.android.systemui.clipboardoverlay.ClipboardOverlayControllerLegacy.10
                {
                    ClipboardOverlayControllerLegacy.this = this;
                }

                @Override // android.view.ViewTreeObserver.OnWindowAttachListener
                public void onWindowAttached() {
                    ClipboardOverlayControllerLegacy.this.mBlockAttach = false;
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