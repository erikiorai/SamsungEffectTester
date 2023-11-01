package com.android.systemui.media.dialog;

import android.app.WallpaperColors;
import android.bluetooth.BluetoothLeBroadcast;
import android.bluetooth.BluetoothLeBroadcastMetadata;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.core.graphics.drawable.IconCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.systemui.R$dimen;
import com.android.systemui.R$id;
import com.android.systemui.R$layout;
import com.android.systemui.R$string;
import com.android.systemui.R$style;
import com.android.systemui.broadcast.BroadcastSender;
import com.android.systemui.media.dialog.MediaOutputBaseDialog;
import com.android.systemui.media.dialog.MediaOutputController;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/* loaded from: mainsysui33.jar:com/android/systemui/media/dialog/MediaOutputBaseDialog.class */
public abstract class MediaOutputBaseDialog extends SystemUIDialog implements MediaOutputController.Callback {
    public MediaOutputBaseAdapter mAdapter;
    public Button mAppButton;
    public ImageView mAppResourceIcon;
    public final BluetoothLeBroadcast.Callback mBroadcastCallback;
    public ImageView mBroadcastIcon;
    public final BroadcastSender mBroadcastSender;
    public LinearLayout mCastAppLayout;
    public final Context mContext;
    public LinearLayout mDeviceListLayout;
    public final ViewTreeObserver.OnGlobalLayoutListener mDeviceListLayoutListener;
    public RecyclerView mDevicesRecyclerView;
    public View mDialogView;
    public Button mDoneButton;
    public Executor mExecutor;
    public ImageView mHeaderIcon;
    public TextView mHeaderSubtitle;
    public TextView mHeaderTitle;
    public int mItemHeight;
    public final RecyclerView.LayoutManager mLayoutManager;
    public int mListMaxHeight;
    public final Handler mMainThreadHandler;
    public final MediaOutputController mMediaOutputController;
    public boolean mShouldLaunchLeBroadcastDialog;
    public Button mStopButton;
    public WallpaperColors mWallpaperColors;

    /* renamed from: com.android.systemui.media.dialog.MediaOutputBaseDialog$1 */
    /* loaded from: mainsysui33.jar:com/android/systemui/media/dialog/MediaOutputBaseDialog$1.class */
    public class AnonymousClass1 implements BluetoothLeBroadcast.Callback {
        /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.dialog.MediaOutputBaseDialog$1$$ExternalSyntheticLambda0.run():void] */
        public static /* synthetic */ void $r8$lambda$CiXNkCMw5Ydarq0ZffAbmABuzLY(AnonymousClass1 anonymousClass1) {
            anonymousClass1.lambda$onBroadcastStopped$3();
        }

        /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.dialog.MediaOutputBaseDialog$1$$ExternalSyntheticLambda3.run():void] */
        /* renamed from: $r8$lambda$HbM7vdNSqj8BFubp-mUf5WjXnRM */
        public static /* synthetic */ void m3309$r8$lambda$HbM7vdNSqj8BFubpmUf5WjXnRM(AnonymousClass1 anonymousClass1) {
            anonymousClass1.lambda$onBroadcastStopFailed$4();
        }

        /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.dialog.MediaOutputBaseDialog$1$$ExternalSyntheticLambda1.run():void] */
        public static /* synthetic */ void $r8$lambda$HzMb8676ljuf_9ZH8ColKD97RFM(AnonymousClass1 anonymousClass1) {
            anonymousClass1.lambda$onBroadcastStarted$0();
        }

        /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.dialog.MediaOutputBaseDialog$1$$ExternalSyntheticLambda2.run():void] */
        public static /* synthetic */ void $r8$lambda$QzQQm5_wVVCe43cFXkuO9ohvrAg(AnonymousClass1 anonymousClass1) {
            anonymousClass1.lambda$onBroadcastStartFailed$1();
        }

        /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.dialog.MediaOutputBaseDialog$1$$ExternalSyntheticLambda4.run():void] */
        public static /* synthetic */ void $r8$lambda$bSQPUujDeUMPwhiA7AeuBa0Y0GU(AnonymousClass1 anonymousClass1) {
            anonymousClass1.lambda$onBroadcastUpdateFailed$6();
        }

        /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.dialog.MediaOutputBaseDialog$1$$ExternalSyntheticLambda5.run():void] */
        public static /* synthetic */ void $r8$lambda$fKo9Myo4WqHq07ZVaIZSlc3vVYM(AnonymousClass1 anonymousClass1) {
            anonymousClass1.lambda$onBroadcastUpdated$5();
        }

        /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.dialog.MediaOutputBaseDialog$1$$ExternalSyntheticLambda6.run():void] */
        /* renamed from: $r8$lambda$nto1gr-ntff9cs4qeVly2edRxTA */
        public static /* synthetic */ void m3310$r8$lambda$nto1grntff9cs4qeVly2edRxTA(AnonymousClass1 anonymousClass1) {
            anonymousClass1.lambda$onBroadcastMetadataChanged$2();
        }

        public AnonymousClass1() {
            MediaOutputBaseDialog.this = r4;
        }

        public /* synthetic */ void lambda$onBroadcastMetadataChanged$2() {
            MediaOutputBaseDialog.this.handleLeBroadcastMetadataChanged();
        }

        public /* synthetic */ void lambda$onBroadcastStartFailed$1() {
            MediaOutputBaseDialog.this.handleLeBroadcastStartFailed();
        }

        public /* synthetic */ void lambda$onBroadcastStarted$0() {
            MediaOutputBaseDialog.this.handleLeBroadcastStarted();
        }

        public /* synthetic */ void lambda$onBroadcastStopFailed$4() {
            MediaOutputBaseDialog.this.handleLeBroadcastStopFailed();
        }

        public /* synthetic */ void lambda$onBroadcastStopped$3() {
            MediaOutputBaseDialog.this.handleLeBroadcastStopped();
        }

        public /* synthetic */ void lambda$onBroadcastUpdateFailed$6() {
            MediaOutputBaseDialog.this.handleLeBroadcastUpdateFailed();
        }

        public /* synthetic */ void lambda$onBroadcastUpdated$5() {
            MediaOutputBaseDialog.this.handleLeBroadcastUpdated();
        }

        public void onBroadcastMetadataChanged(int i, BluetoothLeBroadcastMetadata bluetoothLeBroadcastMetadata) {
            Log.d("MediaOutputDialog", "onBroadcastMetadataChanged(), broadcastId = " + i + ", metadata = " + bluetoothLeBroadcastMetadata);
            MediaOutputBaseDialog.this.mMainThreadHandler.post(new Runnable() { // from class: com.android.systemui.media.dialog.MediaOutputBaseDialog$1$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    MediaOutputBaseDialog.AnonymousClass1.m3310$r8$lambda$nto1grntff9cs4qeVly2edRxTA(MediaOutputBaseDialog.AnonymousClass1.this);
                }
            });
        }

        public void onBroadcastStartFailed(int i) {
            Log.d("MediaOutputDialog", "onBroadcastStartFailed(), reason = " + i);
            MediaOutputBaseDialog.this.mMainThreadHandler.postDelayed(new Runnable() { // from class: com.android.systemui.media.dialog.MediaOutputBaseDialog$1$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    MediaOutputBaseDialog.AnonymousClass1.$r8$lambda$QzQQm5_wVVCe43cFXkuO9ohvrAg(MediaOutputBaseDialog.AnonymousClass1.this);
                }
            }, 3000L);
        }

        public void onBroadcastStarted(int i, int i2) {
            Log.d("MediaOutputDialog", "onBroadcastStarted(), reason = " + i + ", broadcastId = " + i2);
            MediaOutputBaseDialog.this.mMainThreadHandler.post(new Runnable() { // from class: com.android.systemui.media.dialog.MediaOutputBaseDialog$1$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    MediaOutputBaseDialog.AnonymousClass1.$r8$lambda$HzMb8676ljuf_9ZH8ColKD97RFM(MediaOutputBaseDialog.AnonymousClass1.this);
                }
            });
        }

        public void onBroadcastStopFailed(int i) {
            Log.d("MediaOutputDialog", "onBroadcastStopFailed(), reason = " + i);
            MediaOutputBaseDialog.this.mMainThreadHandler.post(new Runnable() { // from class: com.android.systemui.media.dialog.MediaOutputBaseDialog$1$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    MediaOutputBaseDialog.AnonymousClass1.m3309$r8$lambda$HbM7vdNSqj8BFubpmUf5WjXnRM(MediaOutputBaseDialog.AnonymousClass1.this);
                }
            });
        }

        public void onBroadcastStopped(int i, int i2) {
            Log.d("MediaOutputDialog", "onBroadcastStopped(), reason = " + i + ", broadcastId = " + i2);
            MediaOutputBaseDialog.this.mMainThreadHandler.post(new Runnable() { // from class: com.android.systemui.media.dialog.MediaOutputBaseDialog$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    MediaOutputBaseDialog.AnonymousClass1.$r8$lambda$CiXNkCMw5Ydarq0ZffAbmABuzLY(MediaOutputBaseDialog.AnonymousClass1.this);
                }
            });
        }

        public void onBroadcastUpdateFailed(int i, int i2) {
            Log.d("MediaOutputDialog", "onBroadcastUpdateFailed(), reason = " + i + ", broadcastId = " + i2);
            MediaOutputBaseDialog.this.mMainThreadHandler.post(new Runnable() { // from class: com.android.systemui.media.dialog.MediaOutputBaseDialog$1$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    MediaOutputBaseDialog.AnonymousClass1.$r8$lambda$bSQPUujDeUMPwhiA7AeuBa0Y0GU(MediaOutputBaseDialog.AnonymousClass1.this);
                }
            });
        }

        public void onBroadcastUpdated(int i, int i2) {
            Log.d("MediaOutputDialog", "onBroadcastUpdated(), reason = " + i + ", broadcastId = " + i2);
            MediaOutputBaseDialog.this.mMainThreadHandler.post(new Runnable() { // from class: com.android.systemui.media.dialog.MediaOutputBaseDialog$1$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    MediaOutputBaseDialog.AnonymousClass1.$r8$lambda$fKo9Myo4WqHq07ZVaIZSlc3vVYM(MediaOutputBaseDialog.AnonymousClass1.this);
                }
            });
        }

        public void onPlaybackStarted(int i, int i2) {
        }

        public void onPlaybackStopped(int i, int i2) {
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/media/dialog/MediaOutputBaseDialog$LayoutManagerWrapper.class */
    public class LayoutManagerWrapper extends LinearLayoutManager {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public LayoutManagerWrapper(Context context) {
            super(context);
            MediaOutputBaseDialog.this = r4;
        }

        @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
        public void onLayoutCompleted(RecyclerView.State state) {
            super.onLayoutCompleted(state);
            MediaOutputBaseDialog.this.mMediaOutputController.setRefreshing(false);
            MediaOutputBaseDialog.this.mMediaOutputController.refreshDataSetIfNeeded();
        }
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.dialog.MediaOutputBaseDialog$$ExternalSyntheticLambda10.run():void] */
    public static /* synthetic */ void $r8$lambda$5CWchNOCItgAizsxzelZ2QpeC3I(MediaOutputBaseDialog mediaOutputBaseDialog) {
        mediaOutputBaseDialog.lambda$onDeviceListChanged$11();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.dialog.MediaOutputBaseDialog$$ExternalSyntheticLambda2.onClick(android.view.View):void] */
    public static /* synthetic */ void $r8$lambda$DXWg7ZF69x5GvRYyCrcvw1mJgAo(MediaOutputBaseDialog mediaOutputBaseDialog, View view) {
        mediaOutputBaseDialog.lambda$onCreate$1(view);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.dialog.MediaOutputBaseDialog$$ExternalSyntheticLambda11.run():void] */
    public static /* synthetic */ void $r8$lambda$H9inKYNk6EfYVsxUvBiJiTTaM1I(MediaOutputBaseDialog mediaOutputBaseDialog) {
        mediaOutputBaseDialog.lambda$onRouteChanged$10();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.dialog.MediaOutputBaseDialog$$ExternalSyntheticLambda7.onClick(android.view.View):void] */
    public static /* synthetic */ void $r8$lambda$LT9sowbOQ1Px8wGdl7gWO5qIqkU(MediaOutputBaseDialog mediaOutputBaseDialog, View view) {
        mediaOutputBaseDialog.lambda$refresh$6(view);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.dialog.MediaOutputBaseDialog$$ExternalSyntheticLambda8.onClick(android.content.DialogInterface, int):void] */
    public static /* synthetic */ void $r8$lambda$TwvkyMcpmeJLiybgv19ePwIRtaE(MediaOutputBaseDialog mediaOutputBaseDialog, DialogInterface dialogInterface, int i) {
        mediaOutputBaseDialog.lambda$startLeBroadcastDialogForFirstTime$7(dialogInterface, i);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.dialog.MediaOutputBaseDialog$$ExternalSyntheticLambda9.run():void] */
    public static /* synthetic */ void $r8$lambda$Z2C3pC9FkPPI1iYBGSdt_sPbAdE(MediaOutputBaseDialog mediaOutputBaseDialog) {
        mediaOutputBaseDialog.lambda$stopLeBroadcast$8();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.dialog.MediaOutputBaseDialog$$ExternalSyntheticLambda0.onGlobalLayout():void] */
    /* renamed from: $r8$lambda$maUu-QNGsMoIvHEAr1ZRNK_oqoE */
    public static /* synthetic */ void m3305$r8$lambda$maUuQNGsMoIvHEAr1ZRNK_oqoE(MediaOutputBaseDialog mediaOutputBaseDialog) {
        mediaOutputBaseDialog.lambda$new$0();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.dialog.MediaOutputBaseDialog$$ExternalSyntheticLambda3.onClick(android.view.View):void] */
    /* renamed from: $r8$lambda$pqVNoF96-6j4Kvu_Y2Y6ktptQlM */
    public static /* synthetic */ void m3306$r8$lambda$pqVNoF966j4Kvu_Y2Y6ktptQlM(MediaOutputBaseDialog mediaOutputBaseDialog, View view) {
        mediaOutputBaseDialog.lambda$onCreate$2(view);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.dialog.MediaOutputBaseDialog$$ExternalSyntheticLambda4.onClick(android.view.View):void] */
    public static /* synthetic */ void $r8$lambda$r1AKjMGRlcvcmEdjOQMD0oYbOf0(MediaOutputBaseDialog mediaOutputBaseDialog, View view) {
        mediaOutputBaseDialog.lambda$onCreate$3(view);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.dialog.MediaOutputBaseDialog$$ExternalSyntheticLambda1.run():void] */
    /* renamed from: $r8$lambda$sBVK_AK5v9280eOmL6KozpPPe-A */
    public static /* synthetic */ void m3307$r8$lambda$sBVK_AK5v9280eOmL6KozpPPeA(MediaOutputBaseDialog mediaOutputBaseDialog) {
        mediaOutputBaseDialog.lambda$onMediaChanged$9();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.dialog.MediaOutputBaseDialog$$ExternalSyntheticLambda6.onClick(android.view.View):void] */
    public static /* synthetic */ void $r8$lambda$ssfSxKde8QCcus74QRhrK3s3ATs(MediaOutputBaseDialog mediaOutputBaseDialog, View view) {
        mediaOutputBaseDialog.lambda$refresh$5(view);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.dialog.MediaOutputBaseDialog$$ExternalSyntheticLambda5.onClick(android.view.View):void] */
    public static /* synthetic */ void $r8$lambda$yGjnwhbPMQ2_xRCzEG70N1eLaRg(MediaOutputBaseDialog mediaOutputBaseDialog, View view) {
        mediaOutputBaseDialog.lambda$onCreate$4(view);
    }

    /* JADX DEBUG: Multi-variable search result rejected for r6v0, resolved type: com.android.systemui.media.dialog.MediaOutputBaseDialog */
    /* JADX WARN: Multi-variable type inference failed */
    public MediaOutputBaseDialog(Context context, BroadcastSender broadcastSender, MediaOutputController mediaOutputController) {
        super(context, R$style.Theme_SystemUI_Dialog_Media);
        this.mMainThreadHandler = new Handler(Looper.getMainLooper());
        this.mDeviceListLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.android.systemui.media.dialog.MediaOutputBaseDialog$$ExternalSyntheticLambda0
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public final void onGlobalLayout() {
                MediaOutputBaseDialog.m3305$r8$lambda$maUuQNGsMoIvHEAr1ZRNK_oqoE(MediaOutputBaseDialog.this);
            }
        };
        this.mBroadcastCallback = new AnonymousClass1();
        Context context2 = getContext();
        this.mContext = context2;
        this.mBroadcastSender = broadcastSender;
        this.mMediaOutputController = mediaOutputController;
        this.mLayoutManager = new LayoutManagerWrapper(context2);
        this.mListMaxHeight = context.getResources().getDimensionPixelSize(R$dimen.media_output_dialog_list_max_height);
        this.mItemHeight = context.getResources().getDimensionPixelSize(R$dimen.media_output_dialog_list_item_height);
        this.mExecutor = Executors.newSingleThreadExecutor();
    }

    public /* synthetic */ void lambda$new$0() {
        ViewGroup.LayoutParams layoutParams = this.mDeviceListLayout.getLayoutParams();
        int min = Math.min(this.mAdapter.getItemCount() * this.mItemHeight, this.mListMaxHeight);
        if (min != layoutParams.height) {
            layoutParams.height = min;
            this.mDeviceListLayout.setLayoutParams(layoutParams);
        }
    }

    public /* synthetic */ void lambda$onCreate$1(View view) {
        onHeaderIconClick();
    }

    /* JADX DEBUG: Multi-variable search result rejected for r2v0, resolved type: com.android.systemui.media.dialog.MediaOutputBaseDialog */
    /* JADX WARN: Multi-variable type inference failed */
    public /* synthetic */ void lambda$onCreate$2(View view) {
        dismiss();
    }

    /* JADX DEBUG: Multi-variable search result rejected for r2v0, resolved type: com.android.systemui.media.dialog.MediaOutputBaseDialog */
    /* JADX WARN: Multi-variable type inference failed */
    public /* synthetic */ void lambda$onCreate$3(View view) {
        this.mMediaOutputController.releaseSession();
        dismiss();
    }

    /* JADX DEBUG: Multi-variable search result rejected for r3v0, resolved type: com.android.systemui.media.dialog.MediaOutputBaseDialog */
    /* JADX WARN: Multi-variable type inference failed */
    public /* synthetic */ void lambda$onCreate$4(View view) {
        this.mBroadcastSender.closeSystemDialogs();
        if (this.mMediaOutputController.getAppLaunchIntent() != null) {
            this.mContext.startActivity(this.mMediaOutputController.getAppLaunchIntent());
        }
        dismiss();
    }

    public /* synthetic */ void lambda$onDeviceListChanged$11() {
        refresh(true);
    }

    public /* synthetic */ void lambda$refresh$5(View view) {
        onStopButtonClick();
    }

    public /* synthetic */ void lambda$refresh$6(View view) {
        onBroadcastIconClick();
    }

    public /* synthetic */ void lambda$startLeBroadcastDialogForFirstTime$7(DialogInterface dialogInterface, int i) {
        startLeBroadcast();
    }

    /* JADX DEBUG: Multi-variable search result rejected for r2v0, resolved type: com.android.systemui.media.dialog.MediaOutputBaseDialog */
    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.android.systemui.media.dialog.MediaOutputController.Callback
    public void dismissDialog() {
        dismiss();
    }

    public abstract IconCompat getAppSourceIcon();

    public int getBroadcastIconVisibility() {
        return 8;
    }

    public View getDialogView() {
        return this.mDialogView;
    }

    public abstract IconCompat getHeaderIcon();

    public abstract int getHeaderIconRes();

    public abstract int getHeaderIconSize();

    public abstract CharSequence getHeaderSubtitle();

    public abstract CharSequence getHeaderText();

    public CharSequence getStopButtonText() {
        return this.mContext.getText(R$string.keyboard_key_media_stop);
    }

    public abstract int getStopButtonVisibility();

    public void handleLeBroadcastMetadataChanged() {
        if (this.mShouldLaunchLeBroadcastDialog) {
            startLeBroadcastDialog();
            this.mShouldLaunchLeBroadcastDialog = false;
        }
        lambda$stopLeBroadcast$8();
    }

    public void handleLeBroadcastStartFailed() {
        this.mStopButton.setText(R$string.media_output_broadcast_start_failed);
        this.mStopButton.setEnabled(false);
        lambda$stopLeBroadcast$8();
    }

    public void handleLeBroadcastStarted() {
        this.mShouldLaunchLeBroadcastDialog = true;
    }

    public void handleLeBroadcastStopFailed() {
        lambda$stopLeBroadcast$8();
    }

    public void handleLeBroadcastStopped() {
        this.mShouldLaunchLeBroadcastDialog = false;
        lambda$stopLeBroadcast$8();
    }

    public void handleLeBroadcastUpdateFailed() {
        lambda$stopLeBroadcast$8();
    }

    public void handleLeBroadcastUpdated() {
        lambda$stopLeBroadcast$8();
    }

    public boolean isBroadcastSupported() {
        return false;
    }

    public void onBroadcastIconClick() {
    }

    /* JADX DEBUG: Multi-variable search result rejected for r5v0, resolved type: com.android.systemui.media.dialog.MediaOutputBaseDialog */
    /* JADX WARN: Multi-variable type inference failed */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mDialogView = LayoutInflater.from(this.mContext).inflate(R$layout.media_output_dialog, (ViewGroup) null);
        Window window = getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.gravity = 17;
        attributes.setFitInsetsTypes(WindowInsets.Type.statusBars() | WindowInsets.Type.navigationBars());
        attributes.setFitInsetsSides(WindowInsets.Side.all());
        attributes.setFitInsetsIgnoringVisibility(true);
        window.setAttributes(attributes);
        window.setContentView(this.mDialogView);
        window.setTitle(this.mContext.getString(R$string.media_output_dialog_accessibility_title));
        this.mHeaderTitle = (TextView) this.mDialogView.requireViewById(R$id.header_title);
        this.mHeaderSubtitle = (TextView) this.mDialogView.requireViewById(R$id.header_subtitle);
        this.mHeaderIcon = (ImageView) this.mDialogView.requireViewById(R$id.header_icon);
        this.mDevicesRecyclerView = (RecyclerView) this.mDialogView.requireViewById(R$id.list_result);
        this.mDeviceListLayout = (LinearLayout) this.mDialogView.requireViewById(R$id.device_list);
        this.mDoneButton = (Button) this.mDialogView.requireViewById(R$id.done);
        this.mStopButton = (Button) this.mDialogView.requireViewById(R$id.stop);
        this.mAppButton = (Button) this.mDialogView.requireViewById(R$id.launch_app_button);
        this.mAppResourceIcon = (ImageView) this.mDialogView.requireViewById(R$id.app_source_icon);
        this.mCastAppLayout = (LinearLayout) this.mDialogView.requireViewById(R$id.cast_app_section);
        this.mBroadcastIcon = (ImageView) this.mDialogView.requireViewById(R$id.broadcast_icon);
        this.mDeviceListLayout.getViewTreeObserver().addOnGlobalLayoutListener(this.mDeviceListLayoutListener);
        this.mLayoutManager.setAutoMeasureEnabled(true);
        this.mDevicesRecyclerView.setLayoutManager(this.mLayoutManager);
        this.mDevicesRecyclerView.setAdapter(this.mAdapter);
        this.mDevicesRecyclerView.setHasFixedSize(false);
        this.mHeaderIcon.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.media.dialog.MediaOutputBaseDialog$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MediaOutputBaseDialog.$r8$lambda$DXWg7ZF69x5GvRYyCrcvw1mJgAo(MediaOutputBaseDialog.this, view);
            }
        });
        this.mDoneButton.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.media.dialog.MediaOutputBaseDialog$$ExternalSyntheticLambda3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MediaOutputBaseDialog.m3306$r8$lambda$pqVNoF966j4Kvu_Y2Y6ktptQlM(MediaOutputBaseDialog.this, view);
            }
        });
        this.mStopButton.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.media.dialog.MediaOutputBaseDialog$$ExternalSyntheticLambda4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MediaOutputBaseDialog.$r8$lambda$r1AKjMGRlcvcmEdjOQMD0oYbOf0(MediaOutputBaseDialog.this, view);
            }
        });
        this.mAppButton.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.media.dialog.MediaOutputBaseDialog$$ExternalSyntheticLambda5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MediaOutputBaseDialog.$r8$lambda$yGjnwhbPMQ2_xRCzEG70N1eLaRg(MediaOutputBaseDialog.this, view);
            }
        });
    }

    @Override // com.android.systemui.media.dialog.MediaOutputController.Callback
    public void onDeviceListChanged() {
        this.mMainThreadHandler.post(new Runnable() { // from class: com.android.systemui.media.dialog.MediaOutputBaseDialog$$ExternalSyntheticLambda10
            @Override // java.lang.Runnable
            public final void run() {
                MediaOutputBaseDialog.$r8$lambda$5CWchNOCItgAizsxzelZ2QpeC3I(MediaOutputBaseDialog.this);
            }
        });
    }

    public void onHeaderIconClick() {
    }

    @Override // com.android.systemui.media.dialog.MediaOutputController.Callback
    public void onMediaChanged() {
        this.mMainThreadHandler.post(new Runnable() { // from class: com.android.systemui.media.dialog.MediaOutputBaseDialog$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                MediaOutputBaseDialog.m3307$r8$lambda$sBVK_AK5v9280eOmL6KozpPPeA(MediaOutputBaseDialog.this);
            }
        });
    }

    /* JADX DEBUG: Multi-variable search result rejected for r2v0, resolved type: com.android.systemui.media.dialog.MediaOutputBaseDialog */
    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.android.systemui.media.dialog.MediaOutputController.Callback
    public void onMediaStoppedOrPaused() {
        if (isShowing()) {
            dismiss();
        }
    }

    @Override // com.android.systemui.media.dialog.MediaOutputController.Callback
    public void onRouteChanged() {
        this.mMainThreadHandler.post(new Runnable() { // from class: com.android.systemui.media.dialog.MediaOutputBaseDialog$$ExternalSyntheticLambda11
            @Override // java.lang.Runnable
            public final void run() {
                MediaOutputBaseDialog.$r8$lambda$H9inKYNk6EfYVsxUvBiJiTTaM1I(MediaOutputBaseDialog.this);
            }
        });
    }

    public void onStart() {
        super.onStart();
        this.mMediaOutputController.start(this);
        if (isBroadcastSupported()) {
            this.mMediaOutputController.registerLeBroadcastServiceCallBack(this.mExecutor, this.mBroadcastCallback);
        }
    }

    public void onStop() {
        super.onStop();
        if (isBroadcastSupported()) {
            this.mMediaOutputController.unregisterLeBroadcastServiceCallBack(this.mBroadcastCallback);
        }
        this.mMediaOutputController.stop();
    }

    /* JADX DEBUG: Multi-variable search result rejected for r2v0, resolved type: com.android.systemui.media.dialog.MediaOutputBaseDialog */
    /* JADX WARN: Multi-variable type inference failed */
    public void onStopButtonClick() {
        this.mMediaOutputController.releaseSession();
        dismiss();
    }

    /* renamed from: refresh */
    public void lambda$stopLeBroadcast$8() {
        refresh(false);
    }

    /* JADX WARN: Removed duplicated region for block: B:100:0x01a2  */
    /* JADX WARN: Removed duplicated region for block: B:101:0x01b8  */
    /* JADX WARN: Removed duplicated region for block: B:104:0x0222  */
    /* JADX WARN: Removed duplicated region for block: B:114:0x0257  */
    /* JADX WARN: Removed duplicated region for block: B:90:0x0102  */
    /* JADX WARN: Removed duplicated region for block: B:91:0x0127  */
    /* JADX WARN: Removed duplicated region for block: B:97:0x0154  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void refresh(boolean z) {
        boolean z2;
        CharSequence headerSubtitle;
        if (this.mMediaOutputController.isRefreshing()) {
            return;
        }
        this.mMediaOutputController.setRefreshing(true);
        int headerIconRes = getHeaderIconRes();
        IconCompat headerIcon = getHeaderIcon();
        IconCompat appSourceIcon = getAppSourceIcon();
        this.mCastAppLayout.setVisibility(this.mMediaOutputController.shouldShowLaunchSection() ? 0 : 8);
        if (headerIconRes != 0) {
            this.mHeaderIcon.setVisibility(0);
            this.mHeaderIcon.setImageResource(headerIconRes);
        } else if (headerIcon != null) {
            Icon icon = headerIcon.toIcon(this.mContext);
            if (icon.getType() == 1 || icon.getType() == 5) {
                boolean z3 = (this.mContext.getResources().getConfiguration().uiMode & 48) == 32;
                WallpaperColors fromBitmap = WallpaperColors.fromBitmap(icon.getBitmap());
                boolean z4 = !fromBitmap.equals(this.mWallpaperColors);
                z2 = z4;
                if (z4) {
                    this.mAdapter.updateColorScheme(fromBitmap, z3);
                    updateButtonBackgroundColorFilter();
                    updateDialogBackgroundColor();
                    z2 = z4;
                }
            } else {
                updateButtonBackgroundColorFilter();
                updateDialogBackgroundColor();
                z2 = false;
            }
            this.mHeaderIcon.setVisibility(0);
            this.mHeaderIcon.setImageIcon(icon);
            if (appSourceIcon == null) {
                Icon icon2 = appSourceIcon.toIcon(this.mContext);
                this.mAppResourceIcon.setColorFilter(this.mMediaOutputController.getColorItemContent());
                this.mAppResourceIcon.setImageIcon(icon2);
            } else {
                Drawable appSourceIconFromPackage = this.mMediaOutputController.getAppSourceIconFromPackage();
                if (appSourceIconFromPackage != null) {
                    this.mAppResourceIcon.setImageDrawable(appSourceIconFromPackage);
                } else {
                    this.mAppResourceIcon.setVisibility(8);
                }
            }
            if (this.mHeaderIcon.getVisibility() == 0) {
                int headerIconSize = getHeaderIconSize();
                this.mHeaderIcon.setLayoutParams(new LinearLayout.LayoutParams(this.mContext.getResources().getDimensionPixelSize(R$dimen.media_output_dialog_header_icon_padding) + headerIconSize, headerIconSize));
            }
            this.mAppButton.setText(this.mMediaOutputController.getAppSourceName());
            this.mHeaderTitle.setText(getHeaderText());
            headerSubtitle = getHeaderSubtitle();
            if (TextUtils.isEmpty(headerSubtitle)) {
                this.mHeaderSubtitle.setVisibility(0);
                this.mHeaderSubtitle.setText(headerSubtitle);
                this.mHeaderTitle.setGravity(0);
            } else {
                this.mHeaderSubtitle.setVisibility(8);
                this.mHeaderTitle.setGravity(8388627);
            }
            this.mStopButton.setVisibility(getStopButtonVisibility());
            this.mStopButton.setEnabled(true);
            this.mStopButton.setText(getStopButtonText());
            this.mStopButton.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.media.dialog.MediaOutputBaseDialog$$ExternalSyntheticLambda6
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    MediaOutputBaseDialog.$r8$lambda$ssfSxKde8QCcus74QRhrK3s3ATs(MediaOutputBaseDialog.this, view);
                }
            });
            this.mBroadcastIcon.setVisibility(getBroadcastIconVisibility());
            this.mBroadcastIcon.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.media.dialog.MediaOutputBaseDialog$$ExternalSyntheticLambda7
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    MediaOutputBaseDialog.$r8$lambda$LT9sowbOQ1Px8wGdl7gWO5qIqkU(MediaOutputBaseDialog.this, view);
                }
            });
            if (!this.mAdapter.isDragging()) {
                this.mMediaOutputController.setRefreshing(false);
                this.mMediaOutputController.refreshDataSetIfNeeded();
                return;
            }
            int currentActivePosition = this.mAdapter.getCurrentActivePosition();
            if (z2 || z || currentActivePosition < 0 || currentActivePosition >= this.mAdapter.getItemCount()) {
                this.mAdapter.notifyDataSetChanged();
                return;
            } else {
                this.mAdapter.notifyItemChanged(currentActivePosition);
                return;
            }
        } else {
            updateButtonBackgroundColorFilter();
            updateDialogBackgroundColor();
            this.mHeaderIcon.setVisibility(8);
        }
        z2 = false;
        if (appSourceIcon == null) {
        }
        if (this.mHeaderIcon.getVisibility() == 0) {
        }
        this.mAppButton.setText(this.mMediaOutputController.getAppSourceName());
        this.mHeaderTitle.setText(getHeaderText());
        headerSubtitle = getHeaderSubtitle();
        if (TextUtils.isEmpty(headerSubtitle)) {
        }
        this.mStopButton.setVisibility(getStopButtonVisibility());
        this.mStopButton.setEnabled(true);
        this.mStopButton.setText(getStopButtonText());
        this.mStopButton.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.media.dialog.MediaOutputBaseDialog$$ExternalSyntheticLambda6
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MediaOutputBaseDialog.$r8$lambda$ssfSxKde8QCcus74QRhrK3s3ATs(MediaOutputBaseDialog.this, view);
            }
        });
        this.mBroadcastIcon.setVisibility(getBroadcastIconVisibility());
        this.mBroadcastIcon.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.media.dialog.MediaOutputBaseDialog$$ExternalSyntheticLambda7
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MediaOutputBaseDialog.$r8$lambda$LT9sowbOQ1Px8wGdl7gWO5qIqkU(MediaOutputBaseDialog.this, view);
            }
        });
        if (!this.mAdapter.isDragging()) {
        }
    }

    public void startLeBroadcast() {
        this.mStopButton.setText(R$string.media_output_broadcast_starting);
        this.mStopButton.setEnabled(false);
        if (this.mMediaOutputController.startBluetoothLeBroadcast()) {
            return;
        }
        handleLeBroadcastStartFailed();
    }

    public void startLeBroadcastDialog() {
        this.mMediaOutputController.launchMediaOutputBroadcastDialog(this.mDialogView, this.mBroadcastSender);
        lambda$stopLeBroadcast$8();
    }

    public boolean startLeBroadcastDialogForFirstTime() {
        SharedPreferences sharedPreferences = this.mContext.getSharedPreferences("MediaOutputDialog", 0);
        if (sharedPreferences == null || !sharedPreferences.getBoolean("PrefIsLeBroadcastFirstLaunch", true)) {
            return false;
        }
        Log.d("MediaOutputDialog", "PREF_IS_LE_BROADCAST_FIRST_LAUNCH: true");
        this.mMediaOutputController.launchLeBroadcastNotifyDialog(this.mDialogView, this.mBroadcastSender, MediaOutputController.BroadcastNotifyDialog.ACTION_FIRST_LAUNCH, new DialogInterface.OnClickListener() { // from class: com.android.systemui.media.dialog.MediaOutputBaseDialog$$ExternalSyntheticLambda8
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                MediaOutputBaseDialog.$r8$lambda$TwvkyMcpmeJLiybgv19ePwIRtaE(MediaOutputBaseDialog.this, dialogInterface, i);
            }
        });
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putBoolean("PrefIsLeBroadcastFirstLaunch", false);
        edit.apply();
        return true;
    }

    public void stopLeBroadcast() {
        this.mStopButton.setEnabled(false);
        if (this.mMediaOutputController.stopBluetoothLeBroadcast()) {
            return;
        }
        this.mMainThreadHandler.post(new Runnable() { // from class: com.android.systemui.media.dialog.MediaOutputBaseDialog$$ExternalSyntheticLambda9
            @Override // java.lang.Runnable
            public final void run() {
                MediaOutputBaseDialog.$r8$lambda$Z2C3pC9FkPPI1iYBGSdt_sPbAdE(MediaOutputBaseDialog.this);
            }
        });
    }

    public final void updateButtonBackgroundColorFilter() {
        PorterDuffColorFilter porterDuffColorFilter = new PorterDuffColorFilter(this.mMediaOutputController.getColorButtonBackground(), PorterDuff.Mode.SRC_IN);
        this.mDoneButton.getBackground().setColorFilter(porterDuffColorFilter);
        this.mStopButton.getBackground().setColorFilter(porterDuffColorFilter);
        this.mDoneButton.setTextColor(this.mMediaOutputController.getColorPositiveButtonText());
    }

    public final void updateDialogBackgroundColor() {
        getDialogView().getBackground().setTint(this.mMediaOutputController.getColorDialogBackground());
        this.mDeviceListLayout.setBackgroundColor(this.mMediaOutputController.getColorDialogBackground());
    }
}