package com.android.settingslib.inputmethod;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodInfo;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;
import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;
import com.android.internal.annotations.VisibleForTesting;
import com.android.settingslib.PrimarySwitchPreference;
import com.android.settingslib.R$dimen;
import com.android.settingslib.R$string;

/* loaded from: mainsysui33.jar:com/android/settingslib/inputmethod/InputMethodPreference.class */
public class InputMethodPreference extends PrimarySwitchPreference implements Preference.OnPreferenceClickListener, Preference.OnPreferenceChangeListener {
    public static final String TAG = InputMethodPreference.class.getSimpleName();
    public AlertDialog mDialog;
    public final boolean mHasPriorityInSorting;
    public final InputMethodInfo mImi;
    public final InputMethodSettingValuesWrapper mInputMethodSettingValues;
    public final boolean mIsAllowedByOrganization;
    public final OnSavePreferenceListener mOnSaveListener;
    public final int mUserId;

    /* loaded from: mainsysui33.jar:com/android/settingslib/inputmethod/InputMethodPreference$OnSavePreferenceListener.class */
    public interface OnSavePreferenceListener {
        void onSaveInputMethodPreference(InputMethodPreference inputMethodPreference);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.settingslib.inputmethod.InputMethodPreference$$ExternalSyntheticLambda3.onClick(android.content.DialogInterface, int):void] */
    public static /* synthetic */ void $r8$lambda$0_9zEqRVJVEplw5TXPGT29nOWT4(InputMethodPreference inputMethodPreference, DialogInterface dialogInterface, int i) {
        inputMethodPreference.lambda$showSecurityWarnDialog$1(dialogInterface, i);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.settingslib.inputmethod.InputMethodPreference$$ExternalSyntheticLambda5.onCancel(android.content.DialogInterface):void] */
    public static /* synthetic */ void $r8$lambda$8LqlIWHaJG25N3e0c20FnMxZOOU(InputMethodPreference inputMethodPreference, DialogInterface dialogInterface) {
        inputMethodPreference.lambda$showSecurityWarnDialog$3(dialogInterface);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.settingslib.inputmethod.InputMethodPreference$$ExternalSyntheticLambda0.onClick(android.view.View):void] */
    public static /* synthetic */ void $r8$lambda$J1GSVJvm4EodF9Wjvn5GTBrt6jA(InputMethodPreference inputMethodPreference, Switch r5, View view) {
        inputMethodPreference.lambda$onBindViewHolder$0(r5, view);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.settingslib.inputmethod.InputMethodPreference$$ExternalSyntheticLambda4.onClick(android.content.DialogInterface, int):void] */
    public static /* synthetic */ void $r8$lambda$MGIj0pZiIoTBNPi_fOC_4CtEYv8(InputMethodPreference inputMethodPreference, DialogInterface dialogInterface, int i) {
        inputMethodPreference.lambda$showSecurityWarnDialog$2(dialogInterface, i);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.settingslib.inputmethod.InputMethodPreference$$ExternalSyntheticLambda1.onClick(android.content.DialogInterface, int):void] */
    public static /* synthetic */ void $r8$lambda$_Wuz9oK1nsHGm46H2OTC7v2VkrY(InputMethodPreference inputMethodPreference, DialogInterface dialogInterface, int i) {
        inputMethodPreference.lambda$showDirectBootWarnDialog$4(dialogInterface, i);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.settingslib.inputmethod.InputMethodPreference$$ExternalSyntheticLambda2.onClick(android.content.DialogInterface, int):void] */
    /* renamed from: $r8$lambda$siVeSOlubA-0vIkEJyqooYg3vwk */
    public static /* synthetic */ void m1101$r8$lambda$siVeSOlubA0vIkEJyqooYg3vwk(InputMethodPreference inputMethodPreference, DialogInterface dialogInterface, int i) {
        inputMethodPreference.lambda$showDirectBootWarnDialog$5(dialogInterface, i);
    }

    @VisibleForTesting
    public InputMethodPreference(Context context, InputMethodInfo inputMethodInfo, CharSequence charSequence, boolean z, OnSavePreferenceListener onSavePreferenceListener, int i) {
        super(context);
        this.mDialog = null;
        setPersistent(false);
        this.mImi = inputMethodInfo;
        this.mIsAllowedByOrganization = z;
        this.mOnSaveListener = onSavePreferenceListener;
        setKey(inputMethodInfo.getId());
        setTitle(charSequence);
        String settingsActivity = inputMethodInfo.getSettingsActivity();
        if (TextUtils.isEmpty(settingsActivity)) {
            setIntent(null);
        } else {
            Intent intent = new Intent("android.intent.action.MAIN");
            intent.setClassName(inputMethodInfo.getPackageName(), settingsActivity);
            setIntent(intent);
        }
        this.mInputMethodSettingValues = InputMethodSettingValuesWrapper.getInstance(i != UserHandle.myUserId() ? getContext().createContextAsUser(UserHandle.of(i), 0) : context);
        this.mUserId = i;
        boolean z2 = false;
        if (inputMethodInfo.isSystem()) {
            z2 = false;
            if (InputMethodAndSubtypeUtil.isValidNonAuxAsciiCapableIme(inputMethodInfo)) {
                z2 = true;
            }
        }
        this.mHasPriorityInSorting = z2;
        setOnPreferenceClickListener(this);
        setOnPreferenceChangeListener(this);
    }

    public /* synthetic */ void lambda$onBindViewHolder$0(Switch r5, View view) {
        if (r5.isEnabled()) {
            boolean isChecked = isChecked();
            r5.setChecked(isChecked());
            callChangeListener(Boolean.valueOf(!isChecked));
        }
    }

    public /* synthetic */ void lambda$showDirectBootWarnDialog$4(DialogInterface dialogInterface, int i) {
        setCheckedInternal(true);
    }

    public /* synthetic */ void lambda$showDirectBootWarnDialog$5(DialogInterface dialogInterface, int i) {
        setCheckedInternal(false);
    }

    public /* synthetic */ void lambda$showSecurityWarnDialog$1(DialogInterface dialogInterface, int i) {
        if (this.mImi.getServiceInfo().directBootAware || isTv()) {
            setCheckedInternal(true);
        } else {
            showDirectBootWarnDialog();
        }
    }

    public /* synthetic */ void lambda$showSecurityWarnDialog$2(DialogInterface dialogInterface, int i) {
        setCheckedInternal(false);
    }

    public /* synthetic */ void lambda$showSecurityWarnDialog$3(DialogInterface dialogInterface) {
        setCheckedInternal(false);
    }

    public final boolean isTv() {
        return (getContext().getResources().getConfiguration().uiMode & 15) == 4;
    }

    @Override // com.android.settingslib.PrimarySwitchPreference, com.android.settingslib.RestrictedPreference, com.android.settingslib.widget.TwoTargetPreference, androidx.preference.Preference
    public void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        final Switch r0 = getSwitch();
        if (r0 != null) {
            r0.setOnClickListener(new View.OnClickListener() { // from class: com.android.settingslib.inputmethod.InputMethodPreference$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    InputMethodPreference.$r8$lambda$J1GSVJvm4EodF9Wjvn5GTBrt6jA(InputMethodPreference.this, r0, view);
                }
            });
        }
        ImageView imageView = (ImageView) preferenceViewHolder.itemView.findViewById(16908294);
        int dimensionPixelSize = getContext().getResources().getDimensionPixelSize(R$dimen.secondary_app_icon_size);
        if (imageView == null || dimensionPixelSize <= 0) {
            return;
        }
        ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
        layoutParams.height = dimensionPixelSize;
        layoutParams.width = dimensionPixelSize;
        imageView.setLayoutParams(layoutParams);
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public boolean onPreferenceChange(Preference preference, Object obj) {
        if (isChecked()) {
            setCheckedInternal(false);
            return false;
        } else if (!this.mImi.isSystem()) {
            showSecurityWarnDialog();
            return false;
        } else if (this.mImi.getServiceInfo().directBootAware || isTv()) {
            setCheckedInternal(true);
            return false;
        } else if (isTv()) {
            return false;
        } else {
            showDirectBootWarnDialog();
            return false;
        }
    }

    @Override // androidx.preference.Preference.OnPreferenceClickListener
    public boolean onPreferenceClick(Preference preference) {
        Context context = getContext();
        try {
            Intent intent = getIntent();
            if (intent != null) {
                context.startActivityAsUser(intent, UserHandle.of(this.mUserId));
                return true;
            }
            return true;
        } catch (ActivityNotFoundException e) {
            Log.d(TAG, "IME's Settings Activity Not Found", e);
            Toast.makeText(context, context.getString(R$string.failed_to_open_app_settings_toast, this.mImi.loadLabel(context.getPackageManager())), 1).show();
            return true;
        }
    }

    public final void setCheckedInternal(boolean z) {
        super.setChecked(z);
        this.mOnSaveListener.onSaveInputMethodPreference(this);
        notifyChanged();
    }

    public final void showDirectBootWarnDialog() {
        AlertDialog alertDialog = this.mDialog;
        if (alertDialog != null && alertDialog.isShowing()) {
            this.mDialog.dismiss();
        }
        Context context = getContext();
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(true);
        builder.setMessage(context.getText(R$string.direct_boot_unaware_dialog_message));
        builder.setPositiveButton(17039370, new DialogInterface.OnClickListener() { // from class: com.android.settingslib.inputmethod.InputMethodPreference$$ExternalSyntheticLambda1
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                InputMethodPreference.$r8$lambda$_Wuz9oK1nsHGm46H2OTC7v2VkrY(InputMethodPreference.this, dialogInterface, i);
            }
        });
        builder.setNegativeButton(17039360, new DialogInterface.OnClickListener() { // from class: com.android.settingslib.inputmethod.InputMethodPreference$$ExternalSyntheticLambda2
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                InputMethodPreference.m1101$r8$lambda$siVeSOlubA0vIkEJyqooYg3vwk(InputMethodPreference.this, dialogInterface, i);
            }
        });
        AlertDialog create = builder.create();
        this.mDialog = create;
        create.show();
    }

    public final void showSecurityWarnDialog() {
        AlertDialog alertDialog = this.mDialog;
        if (alertDialog != null && alertDialog.isShowing()) {
            this.mDialog.dismiss();
        }
        Context context = getContext();
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(true);
        builder.setTitle(17039380);
        builder.setMessage(context.getString(R$string.ime_security_warning, this.mImi.getServiceInfo().applicationInfo.loadLabel(context.getPackageManager())));
        builder.setPositiveButton(17039370, new DialogInterface.OnClickListener() { // from class: com.android.settingslib.inputmethod.InputMethodPreference$$ExternalSyntheticLambda3
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                InputMethodPreference.$r8$lambda$0_9zEqRVJVEplw5TXPGT29nOWT4(InputMethodPreference.this, dialogInterface, i);
            }
        });
        builder.setNegativeButton(17039360, new DialogInterface.OnClickListener() { // from class: com.android.settingslib.inputmethod.InputMethodPreference$$ExternalSyntheticLambda4
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                InputMethodPreference.$r8$lambda$MGIj0pZiIoTBNPi_fOC_4CtEYv8(InputMethodPreference.this, dialogInterface, i);
            }
        });
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() { // from class: com.android.settingslib.inputmethod.InputMethodPreference$$ExternalSyntheticLambda5
            @Override // android.content.DialogInterface.OnCancelListener
            public final void onCancel(DialogInterface dialogInterface) {
                InputMethodPreference.$r8$lambda$8LqlIWHaJG25N3e0c20FnMxZOOU(InputMethodPreference.this, dialogInterface);
            }
        });
        AlertDialog create = builder.create();
        this.mDialog = create;
        create.show();
    }
}