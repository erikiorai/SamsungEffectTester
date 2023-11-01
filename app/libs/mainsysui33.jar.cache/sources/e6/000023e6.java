package com.android.systemui.screenrecord;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.ResultReceiver;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Switch;
import android.widget.TextView;
import com.android.systemui.R$id;
import com.android.systemui.R$layout;
import com.android.systemui.R$string;
import com.android.systemui.animation.ActivityLaunchAnimator;
import com.android.systemui.animation.DialogLaunchAnimator;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.Flags;
import com.android.systemui.media.MediaProjectionAppSelectorActivity;
import com.android.systemui.media.MediaProjectionCaptureTarget;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.settings.UserContextProvider;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import java.util.Arrays;
import java.util.List;

/* loaded from: mainsysui33.jar:com/android/systemui/screenrecord/ScreenRecordDialog.class */
public class ScreenRecordDialog extends SystemUIDialog {
    public static final List<ScreenRecordingAudioSource> MODES = Arrays.asList(ScreenRecordingAudioSource.INTERNAL, ScreenRecordingAudioSource.MIC, ScreenRecordingAudioSource.MIC_AND_INTERNAL);
    public final ActivityStarter mActivityStarter;
    public Switch mAudioSwitch;
    public final RecordingController mController;
    public final DialogLaunchAnimator mDialogLaunchAnimator;
    public final FeatureFlags mFlags;
    public final Runnable mOnStartRecordingClicked;
    public Spinner mOptions;
    public Switch mTapsSwitch;
    public final UserContextProvider mUserContextProvider;

    /* loaded from: mainsysui33.jar:com/android/systemui/screenrecord/ScreenRecordDialog$CaptureTargetResultReceiver.class */
    public class CaptureTargetResultReceiver extends ResultReceiver {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public CaptureTargetResultReceiver() {
            super(new Handler(Looper.getMainLooper()));
            ScreenRecordDialog.this = r6;
        }

        @Override // android.os.ResultReceiver
        public void onReceiveResult(int i, Bundle bundle) {
            if (i == -1) {
                ScreenRecordDialog.this.requestScreenCapture((MediaProjectionCaptureTarget) bundle.getParcelable("capture_region", MediaProjectionCaptureTarget.class));
            }
        }
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.screenrecord.ScreenRecordDialog$$ExternalSyntheticLambda3.onItemClick(android.widget.AdapterView, android.view.View, int, long):void] */
    /* renamed from: $r8$lambda$DfP4RS-mg7sMDmU00ptkHRUQFLk */
    public static /* synthetic */ void m4182$r8$lambda$DfP4RSmg7sMDmU00ptkHRUQFLk(ScreenRecordDialog screenRecordDialog, AdapterView adapterView, View view, int i, long j) {
        screenRecordDialog.lambda$onCreate$3(adapterView, view, i, j);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.screenrecord.ScreenRecordDialog$$ExternalSyntheticLambda2.onClick(android.view.View):void] */
    /* renamed from: $r8$lambda$GjxAc-nQE8dKjpOW8JAEb0U3jAc */
    public static /* synthetic */ void m4183$r8$lambda$GjxAcnQE8dKjpOW8JAEb0U3jAc(ScreenRecordDialog screenRecordDialog, TextView textView, View view) {
        screenRecordDialog.lambda$onCreate$2(textView, view);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.screenrecord.ScreenRecordDialog$$ExternalSyntheticLambda0.onClick(android.view.View):void] */
    /* renamed from: $r8$lambda$kFSSxDPd4SoYlukTYR6--_jrBnA */
    public static /* synthetic */ void m4184$r8$lambda$kFSSxDPd4SoYlukTYR6_jrBnA(ScreenRecordDialog screenRecordDialog, View view) {
        screenRecordDialog.lambda$onCreate$0(view);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.screenrecord.ScreenRecordDialog$$ExternalSyntheticLambda1.onClick(android.view.View):void] */
    /* renamed from: $r8$lambda$lVWDoboCKsFZ2Eqqn-m7xNoqinQ */
    public static /* synthetic */ void m4185$r8$lambda$lVWDoboCKsFZ2Eqqnm7xNoqinQ(ScreenRecordDialog screenRecordDialog, View view) {
        screenRecordDialog.lambda$onCreate$1(view);
    }

    public ScreenRecordDialog(Context context, RecordingController recordingController, ActivityStarter activityStarter, UserContextProvider userContextProvider, FeatureFlags featureFlags, DialogLaunchAnimator dialogLaunchAnimator, Runnable runnable) {
        super(context);
        this.mController = recordingController;
        this.mUserContextProvider = userContextProvider;
        this.mActivityStarter = activityStarter;
        this.mDialogLaunchAnimator = dialogLaunchAnimator;
        this.mFlags = featureFlags;
        this.mOnStartRecordingClicked = runnable;
    }

    /* JADX DEBUG: Multi-variable search result rejected for r2v0, resolved type: com.android.systemui.screenrecord.ScreenRecordDialog */
    /* JADX WARN: Multi-variable type inference failed */
    public /* synthetic */ void lambda$onCreate$0(View view) {
        dismiss();
    }

    /* JADX DEBUG: Multi-variable search result rejected for r3v0, resolved type: com.android.systemui.screenrecord.ScreenRecordDialog */
    /* JADX WARN: Multi-variable type inference failed */
    public /* synthetic */ void lambda$onCreate$1(View view) {
        Runnable runnable = this.mOnStartRecordingClicked;
        if (runnable != null) {
            runnable.run();
        }
        requestScreenCapture(null);
        dismiss();
    }

    /* JADX DEBUG: Multi-variable search result rejected for r6v0, resolved type: com.android.systemui.screenrecord.ScreenRecordDialog */
    /* JADX WARN: Multi-variable type inference failed */
    public /* synthetic */ void lambda$onCreate$2(TextView textView, View view) {
        Intent intent = new Intent(getContext(), MediaProjectionAppSelectorActivity.class);
        intent.addFlags(268435456);
        intent.putExtra("capture_region_result_receiver", new CaptureTargetResultReceiver());
        ActivityLaunchAnimator.Controller createActivityLaunchController = this.mDialogLaunchAnimator.createActivityLaunchController(textView);
        if (createActivityLaunchController == null) {
            dismiss();
        }
        this.mActivityStarter.startActivity(intent, true, createActivityLaunchController);
    }

    public /* synthetic */ void lambda$onCreate$3(AdapterView adapterView, View view, int i, long j) {
        this.mAudioSwitch.setChecked(true);
    }

    /* JADX DEBUG: Multi-variable search result rejected for r6v0, resolved type: com.android.systemui.screenrecord.ScreenRecordDialog */
    /* JADX WARN: Multi-variable type inference failed */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Window window = getWindow();
        window.addPrivateFlags(16);
        window.setGravity(17);
        setTitle(R$string.screenrecord_name);
        setContentView(R$layout.screen_record_dialog);
        ((TextView) findViewById(R$id.button_cancel)).setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.screenrecord.ScreenRecordDialog$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ScreenRecordDialog.m4184$r8$lambda$kFSSxDPd4SoYlukTYR6_jrBnA(ScreenRecordDialog.this, view);
            }
        });
        ((TextView) findViewById(R$id.button_start)).setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.screenrecord.ScreenRecordDialog$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ScreenRecordDialog.m4185$r8$lambda$lVWDoboCKsFZ2Eqqnm7xNoqinQ(ScreenRecordDialog.this, view);
            }
        });
        if (this.mFlags.isEnabled(Flags.WM_ENABLE_PARTIAL_SCREEN_SHARING)) {
            final TextView textView = (TextView) findViewById(R$id.button_app);
            textView.setVisibility(0);
            textView.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.screenrecord.ScreenRecordDialog$$ExternalSyntheticLambda2
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    ScreenRecordDialog.m4183$r8$lambda$GjxAcnQE8dKjpOW8JAEb0U3jAc(ScreenRecordDialog.this, textView, view);
                }
            });
        }
        this.mAudioSwitch = (Switch) findViewById(R$id.screenrecord_audio_switch);
        this.mTapsSwitch = (Switch) findViewById(R$id.screenrecord_taps_switch);
        this.mOptions = (Spinner) findViewById(R$id.screen_recording_options);
        ScreenRecordingAdapter screenRecordingAdapter = new ScreenRecordingAdapter(getContext().getApplicationContext(), 17367049, MODES);
        screenRecordingAdapter.setDropDownViewResource(17367049);
        this.mOptions.setAdapter((SpinnerAdapter) screenRecordingAdapter);
        this.mOptions.setOnItemClickListenerInt(new AdapterView.OnItemClickListener() { // from class: com.android.systemui.screenrecord.ScreenRecordDialog$$ExternalSyntheticLambda3
            @Override // android.widget.AdapterView.OnItemClickListener
            public final void onItemClick(AdapterView adapterView, View view, int i, long j) {
                ScreenRecordDialog.m4182$r8$lambda$DfP4RSmg7sMDmU00ptkHRUQFLk(ScreenRecordDialog.this, adapterView, view, i, j);
            }
        });
    }

    public final void requestScreenCapture(MediaProjectionCaptureTarget mediaProjectionCaptureTarget) {
        Context userContext = this.mUserContextProvider.getUserContext();
        this.mController.startCountdown(3000L, 1000L, PendingIntent.getForegroundService(userContext, 2, RecordingService.getStartIntent(userContext, -1, (this.mAudioSwitch.isChecked() ? (ScreenRecordingAudioSource) this.mOptions.getSelectedItem() : ScreenRecordingAudioSource.NONE).ordinal(), this.mTapsSwitch.isChecked(), mediaProjectionCaptureTarget), 201326592), PendingIntent.getService(userContext, 2, RecordingService.getStopIntent(userContext), 201326592));
    }
}