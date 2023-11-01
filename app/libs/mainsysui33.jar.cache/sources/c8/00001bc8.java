package com.android.systemui.media;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.media.projection.IMediaProjection;
import android.media.projection.IMediaProjectionManager;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.text.BidiFormatter;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.StyleSpan;
import android.util.Log;
import com.android.systemui.Dependency;
import com.android.systemui.R$drawable;
import com.android.systemui.R$string;
import com.android.systemui.R$style;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.Flags;
import com.android.systemui.screenrecord.MediaProjectionPermissionDialog;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.android.systemui.util.Utils;

/* loaded from: mainsysui33.jar:com/android/systemui/media/MediaProjectionPermissionActivity.class */
public class MediaProjectionPermissionActivity extends Activity implements DialogInterface.OnClickListener, DialogInterface.OnCancelListener {
    public AlertDialog mDialog;
    public FeatureFlags mFeatureFlags;
    public String mPackageName;
    public IMediaProjectionManager mService;
    public int mUid;

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.MediaProjectionPermissionActivity$$ExternalSyntheticLambda0.run():void] */
    /* renamed from: $r8$lambda$7i7M538BVOy-v4sErfYqBwXOX8I */
    public static /* synthetic */ void m3150$r8$lambda$7i7M538BVOyv4sErfYqBwXOX8I(MediaProjectionPermissionActivity mediaProjectionPermissionActivity) {
        mediaProjectionPermissionActivity.lambda$onCreate$0();
    }

    public /* synthetic */ void lambda$onCreate$0() {
        grantMediaProjectionPermission(((MediaProjectionPermissionDialog) this.mDialog).getSelectedScreenShareOption().getMode());
    }

    public final IMediaProjection createProjection(int i, String str) throws RemoteException {
        return this.mService.createProjection(i, str, 0, false);
    }

    public final Intent getMediaProjectionIntent(int i, String str) throws RemoteException {
        IMediaProjection createProjection = createProjection(i, str);
        Intent intent = new Intent();
        intent.putExtra("android.media.projection.extra.EXTRA_MEDIA_PROJECTION", createProjection.asBinder());
        return intent;
    }

    /* JADX WARN: Code restructure failed: missing block: B:47:0x0058, code lost:
        if (r9 != null) goto L19;
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x005b, code lost:
        r9.dismiss();
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x005f, code lost:
        finish();
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x007f, code lost:
        if (r9 == null) goto L16;
     */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x0085, code lost:
        return;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void grantMediaProjectionPermission(int i) {
        AlertDialog alertDialog;
        try {
            if (i == 0) {
                try {
                    setResult(-1, getMediaProjectionIntent(this.mUid, this.mPackageName));
                } catch (RemoteException e) {
                    Log.e("MediaProjectionPermissionActivity", "Error granting projection permission", e);
                    setResult(0);
                    alertDialog = this.mDialog;
                }
            }
            if (isPartialScreenSharingEnabled() && i == 1) {
                IMediaProjection createProjection = createProjection(this.mUid, this.mPackageName);
                Intent intent = new Intent(this, MediaProjectionAppSelectorActivity.class);
                intent.putExtra("android.media.projection.extra.EXTRA_MEDIA_PROJECTION", createProjection.asBinder());
                intent.setFlags(33554432);
                startActivity(intent);
            }
            alertDialog = this.mDialog;
        } catch (Throwable th) {
            AlertDialog alertDialog2 = this.mDialog;
            if (alertDialog2 != null) {
                alertDialog2.dismiss();
            }
            finish();
            throw th;
        }
    }

    public final boolean isPartialScreenSharingEnabled() {
        return this.mFeatureFlags.isEnabled(Flags.WM_ENABLE_PARTIAL_SCREEN_SHARING);
    }

    @Override // android.content.DialogInterface.OnCancelListener
    public void onCancel(DialogInterface dialogInterface) {
        finish();
    }

    @Override // android.content.DialogInterface.OnClickListener
    public void onClick(DialogInterface dialogInterface, int i) {
        if (i == -1) {
            grantMediaProjectionPermission(0);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v95, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r8v0, types: [com.android.systemui.media.MediaProjectionPermissionActivity, android.content.DialogInterface$OnClickListener, android.content.Context, android.content.DialogInterface$OnCancelListener, android.app.Activity] */
    @Override // android.app.Activity
    public void onCreate(Bundle bundle) {
        String str;
        SpannableString spannableString;
        String string;
        super.onCreate(bundle);
        this.mFeatureFlags = (FeatureFlags) Dependency.get(FeatureFlags.class);
        this.mPackageName = getCallingPackage();
        this.mService = IMediaProjectionManager.Stub.asInterface(ServiceManager.getService("media_projection"));
        if (this.mPackageName == null) {
            finish();
            return;
        }
        PackageManager packageManager = getPackageManager();
        try {
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(this.mPackageName, 0);
            int i = applicationInfo.uid;
            this.mUid = i;
            try {
                if (this.mService.hasProjectionPermission(i, this.mPackageName)) {
                    setResult(-1, getMediaProjectionIntent(this.mUid, this.mPackageName));
                    finish();
                    return;
                }
                TextPaint textPaint = new TextPaint();
                textPaint.setTextSize(42.0f);
                String str2 = null;
                if (Utils.isHeadlessRemoteDisplayProvider(packageManager, this.mPackageName)) {
                    spannableString = getString(R$string.media_projection_dialog_service_text);
                    string = getString(R$string.media_projection_dialog_service_title);
                } else {
                    String charSequence = applicationInfo.loadLabel(packageManager).toString();
                    int length = charSequence.length();
                    int i2 = 0;
                    while (true) {
                        int i3 = i2;
                        str = charSequence;
                        if (i3 >= length) {
                            break;
                        }
                        int codePointAt = charSequence.codePointAt(i3);
                        int type = Character.getType(codePointAt);
                        if (type == 13 || type == 15 || type == 14) {
                            break;
                        }
                        i2 = i3 + Character.charCount(codePointAt);
                    }
                    String str3 = str;
                    if (str.isEmpty()) {
                        str3 = this.mPackageName;
                    }
                    str2 = BidiFormatter.getInstance().unicodeWrap(TextUtils.ellipsize(str3, textPaint, 500.0f, TextUtils.TruncateAt.END).toString());
                    String string2 = getString(R$string.media_projection_dialog_text, new Object[]{str2});
                    spannableString = new SpannableString(string2);
                    int indexOf = string2.indexOf(str2);
                    if (indexOf >= 0) {
                        spannableString.setSpan(new StyleSpan(1), indexOf, str2.length() + indexOf, 0);
                    }
                    string = getString(R$string.media_projection_dialog_title, new Object[]{str2});
                }
                if (isPartialScreenSharingEnabled()) {
                    this.mDialog = new MediaProjectionPermissionDialog(this, new Runnable() { // from class: com.android.systemui.media.MediaProjectionPermissionActivity$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            MediaProjectionPermissionActivity.m3150$r8$lambda$7i7M538BVOyv4sErfYqBwXOX8I(MediaProjectionPermissionActivity.this);
                        }
                    }, str2);
                } else {
                    this.mDialog = new AlertDialog.Builder(this, R$style.Theme_SystemUI_Dialog).setTitle(string).setIcon(R$drawable.ic_media_projection_permission).setMessage(spannableString).setPositiveButton(R$string.media_projection_action_text, (DialogInterface.OnClickListener) this).setNeutralButton(17039360, (DialogInterface.OnClickListener) this).create();
                }
                SystemUIDialog.registerDismissListener(this.mDialog);
                SystemUIDialog.applyFlags(this.mDialog);
                SystemUIDialog.setDialogSize(this.mDialog);
                this.mDialog.setOnCancelListener(this);
                this.mDialog.create();
                this.mDialog.getButton(-1).setFilterTouchesWhenObscured(true);
                this.mDialog.getWindow().addSystemFlags(524288);
                this.mDialog.show();
            } catch (RemoteException e) {
                Log.e("MediaProjectionPermissionActivity", "Error checking projection permissions", e);
                finish();
            }
        } catch (PackageManager.NameNotFoundException e2) {
            Log.e("MediaProjectionPermissionActivity", "unable to look up package name", e2);
            finish();
        }
    }

    @Override // android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        AlertDialog alertDialog = this.mDialog;
        if (alertDialog != null) {
            alertDialog.dismiss();
        }
    }
}