package com.android.systemui.media.taptotransfer.receiver;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.media.MediaRoute2Info;
import android.os.Handler;
import android.os.PowerManager;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityManager;
import android.widget.ImageView;
import com.android.internal.widget.CachingIconView;
import com.android.settingslib.Utils;
import com.android.settingslib.widget.ActionBarShadowController;
import com.android.systemui.R$attr;
import com.android.systemui.R$dimen;
import com.android.systemui.R$id;
import com.android.systemui.R$layout;
import com.android.systemui.common.shared.model.ContentDescription;
import com.android.systemui.common.ui.binder.TintedIconViewBinder;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.media.taptotransfer.MediaTttFlags;
import com.android.systemui.media.taptotransfer.common.IconInfo;
import com.android.systemui.media.taptotransfer.common.MediaTttIcon;
import com.android.systemui.media.taptotransfer.common.MediaTttLogger;
import com.android.systemui.media.taptotransfer.common.MediaTttUtils;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.temporarydisplay.TemporaryViewDisplayController;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.time.SystemClock;
import com.android.systemui.util.view.ViewUtil;
import com.android.systemui.util.wakelock.WakeLock;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: mainsysui33.jar:com/android/systemui/media/taptotransfer/receiver/MediaTttChipControllerReceiver.class */
public class MediaTttChipControllerReceiver extends TemporaryViewDisplayController<ChipReceiverInfo, MediaTttLogger<ChipReceiverInfo>> {
    public final CommandQueue commandQueue;
    public final MediaTttChipControllerReceiver$commandQueueCallbacks$1 commandQueueCallbacks;
    public final Handler mainHandler;
    public float maxRippleHeight;
    public float maxRippleWidth;
    public final MediaTttFlags mediaTttFlags;
    public final MediaTttReceiverUiEventLogger uiEventLogger;
    public final ViewUtil viewUtil;
    @SuppressLint({"WrongConstant"})
    public final WindowManager.LayoutParams windowLayoutParams;

    /* JADX WARN: Type inference failed for: r1v12, types: [com.android.systemui.media.taptotransfer.receiver.MediaTttChipControllerReceiver$commandQueueCallbacks$1] */
    public MediaTttChipControllerReceiver(CommandQueue commandQueue, Context context, MediaTttLogger<ChipReceiverInfo> mediaTttLogger, WindowManager windowManager, DelayableExecutor delayableExecutor, AccessibilityManager accessibilityManager, ConfigurationController configurationController, DumpManager dumpManager, PowerManager powerManager, Handler handler, MediaTttFlags mediaTttFlags, MediaTttReceiverUiEventLogger mediaTttReceiverUiEventLogger, ViewUtil viewUtil, WakeLock.Builder builder, SystemClock systemClock) {
        super(context, mediaTttLogger, windowManager, delayableExecutor, accessibilityManager, configurationController, dumpManager, powerManager, R$layout.media_ttt_chip_receiver, builder, systemClock);
        this.commandQueue = commandQueue;
        this.mainHandler = handler;
        this.mediaTttFlags = mediaTttFlags;
        this.uiEventLogger = mediaTttReceiverUiEventLogger;
        this.viewUtil = viewUtil;
        WindowManager.LayoutParams commonWindowLayoutParams$frameworks__base__packages__SystemUI__android_common__SystemUI_core = getCommonWindowLayoutParams$frameworks__base__packages__SystemUI__android_common__SystemUI_core();
        commonWindowLayoutParams$frameworks__base__packages__SystemUI__android_common__SystemUI_core.gravity = 81;
        commonWindowLayoutParams$frameworks__base__packages__SystemUI__android_common__SystemUI_core.width = -1;
        commonWindowLayoutParams$frameworks__base__packages__SystemUI__android_common__SystemUI_core.height = -1;
        commonWindowLayoutParams$frameworks__base__packages__SystemUI__android_common__SystemUI_core.layoutInDisplayCutoutMode = 3;
        commonWindowLayoutParams$frameworks__base__packages__SystemUI__android_common__SystemUI_core.setFitInsetsTypes(0);
        this.windowLayoutParams = commonWindowLayoutParams$frameworks__base__packages__SystemUI__android_common__SystemUI_core;
        this.commandQueueCallbacks = new CommandQueue.Callbacks() { // from class: com.android.systemui.media.taptotransfer.receiver.MediaTttChipControllerReceiver$commandQueueCallbacks$1
            public void updateMediaTapToTransferReceiverDisplay(int i, MediaRoute2Info mediaRoute2Info, Icon icon, CharSequence charSequence) {
                MediaTttChipControllerReceiver.access$updateMediaTapToTransferReceiverDisplay(MediaTttChipControllerReceiver.this, i, mediaRoute2Info, icon, charSequence);
            }
        };
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.taptotransfer.receiver.MediaTttChipControllerReceiver$commandQueueCallbacks$1.updateMediaTapToTransferReceiverDisplay(int, android.media.MediaRoute2Info, android.graphics.drawable.Icon, java.lang.CharSequence):void] */
    public static final /* synthetic */ void access$updateMediaTapToTransferReceiverDisplay(MediaTttChipControllerReceiver mediaTttChipControllerReceiver, int i, MediaRoute2Info mediaRoute2Info, Icon icon, CharSequence charSequence) {
        mediaTttChipControllerReceiver.updateMediaTapToTransferReceiverDisplay(i, mediaRoute2Info, icon, charSequence);
    }

    public static /* synthetic */ void layoutRipple$default(MediaTttChipControllerReceiver mediaTttChipControllerReceiver, ReceiverChipRippleView receiverChipRippleView, boolean z, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: layoutRipple");
        }
        if ((i & 2) != 0) {
            z = false;
        }
        mediaTttChipControllerReceiver.layoutRipple(receiverChipRippleView, z);
    }

    public void animateViewIn$frameworks__base__packages__SystemUI__android_common__SystemUI_core(final ViewGroup viewGroup) {
        CachingIconView appIconView = getAppIconView(viewGroup);
        appIconView.animate().translationYBy((-1) * getTranslationAmount()).setDuration(MediaTttChipControllerReceiverKt.getICON_TRANSLATION_ANIM_DURATION()).start();
        appIconView.animate().alpha(1.0f).setDuration(MediaTttChipControllerReceiverKt.getICON_ALPHA_ANIM_DURATION()).start();
        appIconView.postOnAnimation(new Runnable() { // from class: com.android.systemui.media.taptotransfer.receiver.MediaTttChipControllerReceiver$animateViewIn$1
            @Override // java.lang.Runnable
            public final void run() {
                viewGroup.requestAccessibilityFocus();
            }
        });
        expandRipple((ReceiverChipRippleView) viewGroup.requireViewById(R$id.ripple));
    }

    public void animateViewOut$frameworks__base__packages__SystemUI__android_common__SystemUI_core(ViewGroup viewGroup, String str, Runnable runnable) {
        CachingIconView appIconView = getAppIconView(viewGroup);
        appIconView.animate().translationYBy(getTranslationAmount()).setDuration(MediaTttChipControllerReceiverKt.getICON_TRANSLATION_ANIM_DURATION()).start();
        appIconView.animate().alpha(ActionBarShadowController.ELEVATION_LOW).setDuration(MediaTttChipControllerReceiverKt.getICON_ALPHA_ANIM_DURATION()).start();
        ReceiverChipRippleView receiverChipRippleView = (ReceiverChipRippleView) viewGroup.requireViewById(R$id.ripple);
        if (Intrinsics.areEqual(str, "TRANSFER_TO_RECEIVER_SUCCEEDED") && this.mediaTttFlags.isMediaTttReceiverSuccessRippleEnabled()) {
            expandRippleToFull(receiverChipRippleView, runnable);
        } else {
            receiverChipRippleView.collapseRipple(runnable);
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for r6v0, resolved type: com.android.systemui.media.taptotransfer.receiver.ReceiverChipRippleView */
    /* JADX WARN: Multi-variable type inference failed */
    public final void expandRipple(ReceiverChipRippleView receiverChipRippleView) {
        if (receiverChipRippleView.rippleInProgress()) {
            return;
        }
        receiverChipRippleView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() { // from class: com.android.systemui.media.taptotransfer.receiver.MediaTttChipControllerReceiver$expandRipple$1
            /* JADX WARN: Type inference failed for: r0v2, types: [android.view.View, com.android.systemui.media.taptotransfer.receiver.ReceiverChipRippleView] */
            @Override // android.view.View.OnLayoutChangeListener
            public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                if (view == null) {
                    return;
                }
                ?? r0 = (ReceiverChipRippleView) view;
                MediaTttChipControllerReceiver.layoutRipple$default(MediaTttChipControllerReceiver.this, r0, false, 2, null);
                r0.invalidate();
            }
        });
        receiverChipRippleView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() { // from class: com.android.systemui.media.taptotransfer.receiver.MediaTttChipControllerReceiver$expandRipple$2
            /* JADX WARN: Type inference failed for: r0v2, types: [android.view.View, com.android.systemui.media.taptotransfer.receiver.ReceiverChipRippleView] */
            @Override // android.view.View.OnAttachStateChangeListener
            public void onViewAttachedToWindow(View view) {
                if (view == null) {
                    return;
                }
                ?? r0 = (ReceiverChipRippleView) view;
                MediaTttChipControllerReceiver.layoutRipple$default(MediaTttChipControllerReceiver.this, r0, false, 2, null);
                ReceiverChipRippleView.expandRipple$default(r0, null, 1, null);
                r0.removeOnAttachStateChangeListener(this);
            }

            @Override // android.view.View.OnAttachStateChangeListener
            public void onViewDetachedFromWindow(View view) {
            }
        });
    }

    public final void expandRippleToFull(ReceiverChipRippleView receiverChipRippleView, Runnable runnable) {
        layoutRipple(receiverChipRippleView, true);
        receiverChipRippleView.expandToFull(this.maxRippleHeight, runnable);
    }

    public final CachingIconView getAppIconView(View view) {
        return view.requireViewById(R$id.app_icon);
    }

    public void getTouchableRegion(View view, Rect rect) {
        this.viewUtil.setRectToViewWindowLocation(getAppIconView(view), rect);
    }

    public final int getTranslationAmount() {
        return getContext$frameworks__base__packages__SystemUI__android_common__SystemUI_core().getResources().getDimensionPixelSize(R$dimen.media_ttt_receiver_vert_translation);
    }

    public WindowManager.LayoutParams getWindowLayoutParams$frameworks__base__packages__SystemUI__android_common__SystemUI_core() {
        return this.windowLayoutParams;
    }

    public final void layoutRipple(ReceiverChipRippleView receiverChipRippleView, boolean z) {
        Rect bounds = getWindowManager$frameworks__base__packages__SystemUI__android_common__SystemUI_core().getCurrentWindowMetrics().getBounds();
        float height = bounds.height();
        float width = bounds.width();
        if (z) {
            this.maxRippleHeight = height * 2.0f;
            this.maxRippleWidth = 2.0f * width;
        } else {
            this.maxRippleHeight = height / 2.0f;
            this.maxRippleWidth = width / 2.0f;
        }
        receiverChipRippleView.setMaxSize(this.maxRippleWidth, this.maxRippleHeight);
        receiverChipRippleView.setCenter(width * 0.5f, height);
        receiverChipRippleView.setColor(Utils.getColorAttrDefaultColor(getContext$frameworks__base__packages__SystemUI__android_common__SystemUI_core(), R$attr.wallpaperTextColorAccent), 70);
    }

    public void start() {
        super.start();
        if (this.mediaTttFlags.isMediaTttEnabled()) {
            this.commandQueue.addCallback(this.commandQueueCallbacks);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:26:0x001b, code lost:
        if (r0 == null) goto L20;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updateMediaTapToTransferReceiverDisplay(int i, final MediaRoute2Info mediaRoute2Info, Icon icon, final CharSequence charSequence) {
        String str;
        ChipStateReceiver receiverStateFromId = ChipStateReceiver.Companion.getReceiverStateFromId(i);
        if (receiverStateFromId != null) {
            String name = receiverStateFromId.name();
            str = name;
        }
        str = "Invalid";
        ((MediaTttLogger) getLogger$frameworks__base__packages__SystemUI__android_common__SystemUI_core()).logStateChange(str, mediaRoute2Info.getId(), mediaRoute2Info.getClientPackageName());
        if (receiverStateFromId == null) {
            ((MediaTttLogger) getLogger$frameworks__base__packages__SystemUI__android_common__SystemUI_core()).logStateChangeError(i);
            return;
        }
        this.uiEventLogger.logReceiverStateChange(receiverStateFromId);
        if (receiverStateFromId != ChipStateReceiver.CLOSE_TO_SENDER) {
            removeView(mediaRoute2Info.getId(), receiverStateFromId.name());
        } else if (icon == null) {
            TemporaryViewDisplayController.displayView$default(this, new ChipReceiverInfo(mediaRoute2Info, null, charSequence, null, null, mediaRoute2Info.getId(), null, 88, null), (Runnable) null, 2, (Object) null);
        } else {
            icon.loadDrawableAsync(getContext$frameworks__base__packages__SystemUI__android_common__SystemUI_core(), new Icon.OnDrawableLoadedListener() { // from class: com.android.systemui.media.taptotransfer.receiver.MediaTttChipControllerReceiver$updateMediaTapToTransferReceiverDisplay$1
                @Override // android.graphics.drawable.Icon.OnDrawableLoadedListener
                public final void onDrawableLoaded(Drawable drawable) {
                    MediaTttChipControllerReceiver mediaTttChipControllerReceiver = MediaTttChipControllerReceiver.this;
                    MediaRoute2Info mediaRoute2Info2 = mediaRoute2Info;
                    TemporaryViewDisplayController.displayView$default(mediaTttChipControllerReceiver, new ChipReceiverInfo(mediaRoute2Info2, drawable, charSequence, null, null, mediaRoute2Info2.getId(), null, 88, null), (Runnable) null, 2, (Object) null);
                }
            }, this.mainHandler);
        }
    }

    public void updateView(ChipReceiverInfo chipReceiverInfo, ViewGroup viewGroup) {
        IconInfo iconInfoFromPackageName = MediaTttUtils.Companion.getIconInfoFromPackageName(getContext$frameworks__base__packages__SystemUI__android_common__SystemUI_core(), chipReceiverInfo.getRouteInfo().getClientPackageName(), (MediaTttLogger) getLogger$frameworks__base__packages__SystemUI__android_common__SystemUI_core());
        IconInfo iconInfo = iconInfoFromPackageName;
        if (chipReceiverInfo.getAppNameOverride() != null) {
            iconInfo = IconInfo.copy$default(iconInfoFromPackageName, new ContentDescription.Loaded(chipReceiverInfo.getAppNameOverride().toString()), null, null, false, 14, null);
        }
        IconInfo iconInfo2 = iconInfo;
        IconInfo iconInfo3 = iconInfo2;
        if (chipReceiverInfo.getAppIconDrawableOverride() != null) {
            iconInfo3 = IconInfo.copy$default(iconInfo2, null, new MediaTttIcon.Loaded(chipReceiverInfo.getAppIconDrawableOverride()), null, true, 5, null);
        }
        int dimensionPixelSize = iconInfo3.isAppIcon() ? 0 : getContext$frameworks__base__packages__SystemUI__android_common__SystemUI_core().getResources().getDimensionPixelSize(R$dimen.media_ttt_generic_icon_padding);
        ImageView appIconView = getAppIconView(viewGroup);
        appIconView.setPadding(dimensionPixelSize, dimensionPixelSize, dimensionPixelSize, dimensionPixelSize);
        TintedIconViewBinder.INSTANCE.bind(iconInfo3.toTintedIcon(), appIconView);
    }
}