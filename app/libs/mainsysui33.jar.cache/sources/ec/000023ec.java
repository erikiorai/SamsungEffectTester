package com.android.systemui.screenrecord;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.ResultReceiver;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Switch;
import com.android.systemui.R$color;
import com.android.systemui.R$drawable;
import com.android.systemui.R$id;
import com.android.systemui.R$layout;
import com.android.systemui.R$string;
import com.android.systemui.animation.ActivityLaunchAnimator;
import com.android.systemui.animation.DialogLaunchAnimator;
import com.android.systemui.media.MediaProjectionAppSelectorActivity;
import com.android.systemui.media.MediaProjectionCaptureTarget;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.screenrecord.ScreenRecordPermissionDialog;
import com.android.systemui.settings.UserContextProvider;
import java.util.List;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: mainsysui33.jar:com/android/systemui/screenrecord/ScreenRecordPermissionDialog.class */
public final class ScreenRecordPermissionDialog extends BaseScreenSharePermissionDialog {
    public static final Companion Companion = new Companion(null);
    public static final List<ScreenRecordingAudioSource> MODES = CollectionsKt__CollectionsKt.listOf(new ScreenRecordingAudioSource[]{ScreenRecordingAudioSource.INTERNAL, ScreenRecordingAudioSource.MIC, ScreenRecordingAudioSource.MIC_AND_INTERNAL});
    public final ActivityStarter activityStarter;
    public Switch audioSwitch;
    public final RecordingController controller;
    public final DialogLaunchAnimator dialogLaunchAnimator;
    public final Runnable onStartRecordingClicked;
    public Spinner options;
    public Switch tapsSwitch;
    public View tapsView;
    public final UserContextProvider userContextProvider;

    /* loaded from: mainsysui33.jar:com/android/systemui/screenrecord/ScreenRecordPermissionDialog$CaptureTargetResultReceiver.class */
    public final class CaptureTargetResultReceiver extends ResultReceiver {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public CaptureTargetResultReceiver() {
            super(new Handler(Looper.getMainLooper()));
            ScreenRecordPermissionDialog.this = r6;
        }

        @Override // android.os.ResultReceiver
        public void onReceiveResult(int i, Bundle bundle) {
            if (i == -1) {
                ScreenRecordPermissionDialog.this.requestScreenCapture((MediaProjectionCaptureTarget) bundle.getParcelable("capture_region", MediaProjectionCaptureTarget.class));
            }
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/screenrecord/ScreenRecordPermissionDialog$Companion.class */
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final List<ScreenShareOption> createOptionList() {
            return CollectionsKt__CollectionsKt.listOf(new ScreenShareOption[]{new ScreenShareOption(0, R$string.screenrecord_option_entire_screen, R$string.screenrecord_warning_entire_screen), new ScreenShareOption(1, R$string.screenrecord_option_single_app, R$string.screenrecord_warning_single_app)});
        }
    }

    public ScreenRecordPermissionDialog(Context context, RecordingController recordingController, ActivityStarter activityStarter, DialogLaunchAnimator dialogLaunchAnimator, UserContextProvider userContextProvider, Runnable runnable) {
        super(context, Companion.createOptionList(), null, Integer.valueOf(R$drawable.ic_screenrecord), Integer.valueOf(R$color.screenrecord_icon_color));
        this.controller = recordingController;
        this.activityStarter = activityStarter;
        this.dialogLaunchAnimator = dialogLaunchAnimator;
        this.userContextProvider = userContextProvider;
        this.onStartRecordingClicked = runnable;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.screenrecord.ScreenRecordPermissionDialog$initRecordOptionsView$1.onItemClick(android.widget.AdapterView<?>, android.view.View, int, long):void] */
    public static final /* synthetic */ Switch access$getAudioSwitch$p(ScreenRecordPermissionDialog screenRecordPermissionDialog) {
        return screenRecordPermissionDialog.audioSwitch;
    }

    @Override // com.android.systemui.screenrecord.BaseScreenSharePermissionDialog
    public Integer getOptionsViewLayoutId() {
        return Integer.valueOf(R$layout.screen_record_options);
    }

    /* JADX DEBUG: Multi-variable search result rejected for r6v0, resolved type: com.android.systemui.screenrecord.ScreenRecordPermissionDialog */
    /* JADX WARN: Multi-variable type inference failed */
    public final void initRecordOptionsView() {
        this.audioSwitch = (Switch) findViewById(R$id.screenrecord_audio_switch);
        this.tapsSwitch = (Switch) findViewById(R$id.screenrecord_taps_switch);
        this.tapsView = findViewById(R$id.show_taps);
        updateTapsViewVisibility();
        this.options = (Spinner) findViewById(R$id.screen_recording_options);
        ScreenRecordingAdapter screenRecordingAdapter = new ScreenRecordingAdapter(getContext(), 17367049, MODES);
        screenRecordingAdapter.setDropDownViewResource(17367049);
        Spinner spinner = this.options;
        Spinner spinner2 = spinner;
        if (spinner == null) {
            spinner2 = null;
        }
        spinner2.setAdapter((SpinnerAdapter) screenRecordingAdapter);
        Spinner spinner3 = this.options;
        if (spinner3 == null) {
            spinner3 = null;
        }
        spinner3.setOnItemClickListenerInt(new AdapterView.OnItemClickListener() { // from class: com.android.systemui.screenrecord.ScreenRecordPermissionDialog$initRecordOptionsView$1
            @Override // android.widget.AdapterView.OnItemClickListener
            public final void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                Switch access$getAudioSwitch$p = ScreenRecordPermissionDialog.access$getAudioSwitch$p(ScreenRecordPermissionDialog.this);
                Switch r4 = access$getAudioSwitch$p;
                if (access$getAudioSwitch$p == null) {
                    r4 = null;
                }
                r4.setChecked(true);
            }
        });
    }

    @Override // com.android.systemui.screenrecord.BaseScreenSharePermissionDialog
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setDialogTitle(R$string.screenrecord_start_label);
        setStartButtonText(R$string.screenrecord_start_recording);
        setStartButtonOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.screenrecord.ScreenRecordPermissionDialog$onCreate$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                Runnable runnable;
                DialogLaunchAnimator dialogLaunchAnimator;
                ActivityStarter activityStarter;
                runnable = ScreenRecordPermissionDialog.this.onStartRecordingClicked;
                if (runnable != null) {
                    runnable.run();
                }
                if (ScreenRecordPermissionDialog.this.getSelectedScreenShareOption().getMode() == 0) {
                    ScreenRecordPermissionDialog.this.requestScreenCapture(null);
                }
                if (ScreenRecordPermissionDialog.this.getSelectedScreenShareOption().getMode() == 1) {
                    Intent intent = new Intent(ScreenRecordPermissionDialog.this.getContext(), MediaProjectionAppSelectorActivity.class);
                    intent.addFlags(268435456);
                    intent.putExtra("capture_region_result_receiver", new ScreenRecordPermissionDialog.CaptureTargetResultReceiver());
                    dialogLaunchAnimator = ScreenRecordPermissionDialog.this.dialogLaunchAnimator;
                    Intrinsics.checkNotNull(view);
                    ActivityLaunchAnimator.Controller createActivityLaunchController$default = DialogLaunchAnimator.createActivityLaunchController$default(dialogLaunchAnimator, view, null, 2, null);
                    if (createActivityLaunchController$default == null) {
                        ScreenRecordPermissionDialog.this.dismiss();
                    }
                    activityStarter = ScreenRecordPermissionDialog.this.activityStarter;
                    activityStarter.startActivity(intent, true, createActivityLaunchController$default);
                }
                ScreenRecordPermissionDialog.this.dismiss();
            }
        });
        initRecordOptionsView();
    }

    @Override // com.android.systemui.screenrecord.BaseScreenSharePermissionDialog, android.widget.AdapterView.OnItemSelectedListener
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
        super.onItemSelected(adapterView, view, i, j);
        updateTapsViewVisibility();
    }

    /* JADX WARN: Code restructure failed: missing block: B:31:0x0034, code lost:
        if (r15.isChecked() != false) goto L8;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void requestScreenCapture(MediaProjectionCaptureTarget mediaProjectionCaptureTarget) {
        ScreenRecordingAudioSource screenRecordingAudioSource;
        Context userContext = this.userContextProvider.getUserContext();
        boolean z = true;
        if (getSelectedScreenShareOption().getMode() != 1) {
            Switch r0 = this.tapsSwitch;
            Switch r15 = r0;
            if (r0 == null) {
                r15 = null;
            }
        }
        z = false;
        Switch r02 = this.audioSwitch;
        Switch r152 = r02;
        if (r02 == null) {
            r152 = null;
        }
        if (r152.isChecked()) {
            Spinner spinner = this.options;
            if (spinner == null) {
                spinner = null;
            }
            screenRecordingAudioSource = (ScreenRecordingAudioSource) spinner.getSelectedItem();
        } else {
            screenRecordingAudioSource = ScreenRecordingAudioSource.NONE;
        }
        this.controller.startCountdown(3000L, 1000L, PendingIntent.getForegroundService(userContext, 2, RecordingService.getStartIntent(userContext, -1, screenRecordingAudioSource.ordinal(), z, mediaProjectionCaptureTarget), 201326592), PendingIntent.getService(userContext, 2, RecordingService.getStopIntent(userContext), 201326592));
    }

    public final void updateTapsViewVisibility() {
        View view = this.tapsView;
        View view2 = view;
        if (view == null) {
            view2 = null;
        }
        view2.setVisibility(getSelectedScreenShareOption().getMode() == 1 ? 8 : 0);
    }
}