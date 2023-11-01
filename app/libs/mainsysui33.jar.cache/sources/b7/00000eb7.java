package com.android.settingslib.users;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.UserHandle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import com.android.internal.util.UserIcons;
import com.android.settingslib.R$id;
import com.android.settingslib.R$layout;
import com.android.settingslib.RestrictedLockUtils;
import com.android.settingslib.RestrictedLockUtilsInternal;
import com.android.settingslib.drawable.CircleFramedDrawable;
import java.io.File;
import java.util.function.BiConsumer;

/* loaded from: mainsysui33.jar:com/android/settingslib/users/EditUserInfoController.class */
public class EditUserInfoController {
    public Dialog mEditUserInfoDialog;
    public EditUserPhotoController mEditUserPhotoController;
    public final String mFileAuthority;
    public Drawable mSavedDrawable;
    public Bitmap mSavedPhoto;
    public boolean mWaitingForActivityResult = false;

    /* JADX DEBUG: Method not inlined, still used in: [com.android.settingslib.users.EditUserInfoController$$ExternalSyntheticLambda2.onClick(android.content.DialogInterface, int):void] */
    /* renamed from: $r8$lambda$CE06hsmNALfgmtmQ-bJJNCp1R3M */
    public static /* synthetic */ void m1146$r8$lambda$CE06hsmNALfgmtmQbJJNCp1R3M(EditUserInfoController editUserInfoController, Runnable runnable, DialogInterface dialogInterface, int i) {
        editUserInfoController.lambda$buildDialog$2(runnable, dialogInterface, i);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.settingslib.users.EditUserInfoController$$ExternalSyntheticLambda3.onCancel(android.content.DialogInterface):void] */
    public static /* synthetic */ void $r8$lambda$GoyE3hZWHSlKbPS52JzcxAXFZVw(EditUserInfoController editUserInfoController, Runnable runnable, DialogInterface dialogInterface) {
        editUserInfoController.lambda$buildDialog$3(runnable, dialogInterface);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.settingslib.users.EditUserInfoController$$ExternalSyntheticLambda1.onClick(android.content.DialogInterface, int):void] */
    public static /* synthetic */ void $r8$lambda$uu_jPOOXorxwdG6HFHdLVYh022Q(EditUserInfoController editUserInfoController, Drawable drawable, EditText editText, String str, BiConsumer biConsumer, DialogInterface dialogInterface, int i) {
        editUserInfoController.lambda$buildDialog$1(drawable, editText, str, biConsumer, dialogInterface, i);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.settingslib.users.EditUserInfoController$$ExternalSyntheticLambda0.onClick(android.view.View):void] */
    /* renamed from: $r8$lambda$vyBXS3iW61nwcHYFAZerUf-LUc8 */
    public static /* synthetic */ void m1147$r8$lambda$vyBXS3iW61nwcHYFAZerUfLUc8(Activity activity, RestrictedLockUtils.EnforcedAdmin enforcedAdmin, View view) {
        RestrictedLockUtils.sendShowAdminSupportDetailsIntent(activity, enforcedAdmin);
    }

    public EditUserInfoController(String str) {
        this.mFileAuthority = str;
    }

    public /* synthetic */ void lambda$buildDialog$1(Drawable drawable, EditText editText, String str, BiConsumer biConsumer, DialogInterface dialogInterface, int i) {
        EditUserPhotoController editUserPhotoController = this.mEditUserPhotoController;
        Drawable newUserPhotoDrawable = editUserPhotoController != null ? editUserPhotoController.getNewUserPhotoDrawable() : null;
        if (newUserPhotoDrawable != null) {
            drawable = newUserPhotoDrawable;
        }
        String trim = editText.getText().toString().trim();
        if (!trim.isEmpty()) {
            str = trim;
        }
        clear();
        if (biConsumer != null) {
            biConsumer.accept(str, drawable);
        }
    }

    public /* synthetic */ void lambda$buildDialog$2(Runnable runnable, DialogInterface dialogInterface, int i) {
        clear();
        if (runnable != null) {
            runnable.run();
        }
    }

    public /* synthetic */ void lambda$buildDialog$3(Runnable runnable, DialogInterface dialogInterface) {
        clear();
        if (runnable != null) {
            runnable.run();
        }
    }

    public final Dialog buildDialog(Activity activity, View view, final EditText editText, final Drawable drawable, final String str, String str2, final BiConsumer<String, Drawable> biConsumer, final Runnable runnable) {
        return new AlertDialog.Builder(activity).setTitle(str2).setView(view).setCancelable(true).setPositiveButton(17039370, new DialogInterface.OnClickListener() { // from class: com.android.settingslib.users.EditUserInfoController$$ExternalSyntheticLambda1
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                EditUserInfoController.$r8$lambda$uu_jPOOXorxwdG6HFHdLVYh022Q(EditUserInfoController.this, drawable, editText, str, biConsumer, dialogInterface, i);
            }
        }).setNegativeButton(17039360, new DialogInterface.OnClickListener() { // from class: com.android.settingslib.users.EditUserInfoController$$ExternalSyntheticLambda2
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                EditUserInfoController.m1146$r8$lambda$CE06hsmNALfgmtmQbJJNCp1R3M(EditUserInfoController.this, runnable, dialogInterface, i);
            }
        }).setOnCancelListener(new DialogInterface.OnCancelListener() { // from class: com.android.settingslib.users.EditUserInfoController$$ExternalSyntheticLambda3
            @Override // android.content.DialogInterface.OnCancelListener
            public final void onCancel(DialogInterface dialogInterface) {
                EditUserInfoController.$r8$lambda$GoyE3hZWHSlKbPS52JzcxAXFZVw(EditUserInfoController.this, runnable, dialogInterface);
            }
        }).create();
    }

    public final void clear() {
        EditUserPhotoController editUserPhotoController = this.mEditUserPhotoController;
        if (editUserPhotoController != null) {
            editUserPhotoController.removeNewUserPhotoBitmapFile();
        }
        this.mEditUserInfoDialog = null;
        this.mSavedPhoto = null;
        this.mSavedDrawable = null;
    }

    public Dialog createDialog(final Activity activity, ActivityStarter activityStarter, Drawable drawable, String str, String str2, BiConsumer<String, Drawable> biConsumer, Runnable runnable) {
        View inflate = LayoutInflater.from(activity).inflate(R$layout.edit_user_info_dialog_content, (ViewGroup) null);
        EditText editText = (EditText) inflate.findViewById(R$id.user_name);
        editText.setText(str);
        ImageView imageView = (ImageView) inflate.findViewById(R$id.user_photo);
        imageView.setImageDrawable(getUserIcon(activity, drawable != null ? drawable : UserIcons.getDefaultUserIcon(activity.getResources(), -10000, false)));
        if (isChangePhotoRestrictedByBase(activity)) {
            inflate.findViewById(R$id.add_a_photo_icon).setVisibility(8);
        } else {
            final RestrictedLockUtils.EnforcedAdmin changePhotoAdminRestriction = getChangePhotoAdminRestriction(activity);
            if (changePhotoAdminRestriction != null) {
                imageView.setOnClickListener(new View.OnClickListener() { // from class: com.android.settingslib.users.EditUserInfoController$$ExternalSyntheticLambda0
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        EditUserInfoController.m1147$r8$lambda$vyBXS3iW61nwcHYFAZerUfLUc8(activity, changePhotoAdminRestriction, view);
                    }
                });
            } else {
                this.mEditUserPhotoController = createEditUserPhotoController(activity, activityStarter, imageView);
            }
        }
        Dialog buildDialog = buildDialog(activity, inflate, editText, drawable, str, str2, biConsumer, runnable);
        this.mEditUserInfoDialog = buildDialog;
        buildDialog.getWindow().setSoftInputMode(4);
        return this.mEditUserInfoDialog;
    }

    public EditUserPhotoController createEditUserPhotoController(Activity activity, ActivityStarter activityStarter, ImageView imageView) {
        return new EditUserPhotoController(activity, activityStarter, imageView, this.mSavedPhoto, this.mSavedDrawable, this.mFileAuthority);
    }

    public RestrictedLockUtils.EnforcedAdmin getChangePhotoAdminRestriction(Context context) {
        return RestrictedLockUtilsInternal.checkIfRestrictionEnforced(context, "no_set_user_icon", UserHandle.myUserId());
    }

    public final Drawable getUserIcon(Activity activity, Drawable drawable) {
        Bitmap bitmap = this.mSavedPhoto;
        if (bitmap != null) {
            CircleFramedDrawable circleFramedDrawable = CircleFramedDrawable.getInstance(activity, bitmap);
            this.mSavedDrawable = circleFramedDrawable;
            return circleFramedDrawable;
        }
        return drawable;
    }

    public boolean isChangePhotoRestrictedByBase(Context context) {
        return RestrictedLockUtilsInternal.hasBaseUserRestriction(context, "no_set_user_icon", UserHandle.myUserId());
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        this.mWaitingForActivityResult = false;
        EditUserPhotoController editUserPhotoController = this.mEditUserPhotoController;
        if (editUserPhotoController == null || this.mEditUserInfoDialog == null) {
            return;
        }
        editUserPhotoController.onActivityResult(i, i2, intent);
    }

    public void onRestoreInstanceState(Bundle bundle) {
        String string = bundle.getString("pending_photo");
        if (string != null) {
            this.mSavedPhoto = EditUserPhotoController.loadNewUserPhotoBitmap(new File(string));
        }
        this.mWaitingForActivityResult = bundle.getBoolean("awaiting_result", false);
    }

    public void onSaveInstanceState(Bundle bundle) {
        EditUserPhotoController editUserPhotoController;
        File saveNewUserPhotoBitmap;
        if (this.mEditUserInfoDialog != null && (editUserPhotoController = this.mEditUserPhotoController) != null && (saveNewUserPhotoBitmap = editUserPhotoController.saveNewUserPhotoBitmap()) != null) {
            bundle.putString("pending_photo", saveNewUserPhotoBitmap.getPath());
        }
        bundle.putBoolean("awaiting_result", this.mWaitingForActivityResult);
    }

    public void startingActivityForResult() {
        this.mWaitingForActivityResult = true;
    }
}