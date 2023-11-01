package com.android.systemui.screenrecord;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import com.android.systemui.R$string;
import java.util.List;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: mainsysui33.jar:com/android/systemui/screenrecord/MediaProjectionPermissionDialog.class */
public final class MediaProjectionPermissionDialog extends BaseScreenSharePermissionDialog {
    public static final Companion Companion = new Companion(null);
    public final String appName;
    public final Runnable onStartRecordingClicked;

    /* loaded from: mainsysui33.jar:com/android/systemui/screenrecord/MediaProjectionPermissionDialog$Companion.class */
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final List<ScreenShareOption> createOptionList(String str) {
            return CollectionsKt__CollectionsKt.listOf(new ScreenShareOption[]{new ScreenShareOption(0, R$string.media_projection_permission_dialog_option_entire_screen, str == null ? R$string.media_projection_permission_dialog_system_service_warning_entire_screen : R$string.media_projection_permission_dialog_warning_entire_screen), new ScreenShareOption(1, R$string.media_projection_permission_dialog_option_single_app, str == null ? R$string.media_projection_permission_dialog_system_service_warning_single_app : R$string.media_projection_permission_dialog_warning_single_app)});
        }
    }

    public MediaProjectionPermissionDialog(Context context, Runnable runnable, String str) {
        super(context, Companion.createOptionList(str), str, null, null, 24, null);
        this.onStartRecordingClicked = runnable;
        this.appName = str;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.screenrecord.MediaProjectionPermissionDialog$onCreate$1.onClick(android.view.View):void] */
    public static final /* synthetic */ Runnable access$getOnStartRecordingClicked$p(MediaProjectionPermissionDialog mediaProjectionPermissionDialog) {
        return mediaProjectionPermissionDialog.onStartRecordingClicked;
    }

    @Override // com.android.systemui.screenrecord.BaseScreenSharePermissionDialog
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (this.appName == null) {
            setDialogTitle(R$string.media_projection_permission_dialog_system_service_title);
        } else {
            setDialogTitle(R$string.media_projection_permission_dialog_title);
        }
        setStartButtonText(R$string.media_projection_permission_dialog_continue);
        setStartButtonOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.screenrecord.MediaProjectionPermissionDialog$onCreate$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MediaProjectionPermissionDialog.access$getOnStartRecordingClicked$p(MediaProjectionPermissionDialog.this).run();
                MediaProjectionPermissionDialog.this.dismiss();
            }
        });
    }
}