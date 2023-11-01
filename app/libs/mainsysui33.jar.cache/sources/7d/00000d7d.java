package com.android.settingslib;

import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.TextView;
import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;
import com.android.settingslib.RestrictedLockUtils;
import com.android.settingslib.utils.BuildCompatUtils;
import java.util.function.Supplier;

/* loaded from: mainsysui33.jar:com/android/settingslib/RestrictedPreferenceHelper.class */
public class RestrictedPreferenceHelper {
    public String mAttrUserRestriction;
    public final Context mContext;
    public boolean mDisabledByAdmin;
    public boolean mDisabledByAppOps;
    public boolean mDisabledSummary;
    public RestrictedLockUtils.EnforcedAdmin mEnforcedAdmin;
    public final Preference mPreference;
    public String packageName;
    public int uid;

    /* JADX DEBUG: Method not inlined, still used in: [com.android.settingslib.RestrictedPreferenceHelper$$ExternalSyntheticLambda0.get():java.lang.Object] */
    public static /* synthetic */ String $r8$lambda$PpwEbNSD_9QumHyGDvBzP3cweX8(RestrictedPreferenceHelper restrictedPreferenceHelper) {
        return restrictedPreferenceHelper.lambda$getDisabledByAdminUpdatableString$0();
    }

    public RestrictedPreferenceHelper(Context context, Preference preference, AttributeSet attributeSet) {
        this(context, preference, attributeSet, null, -1);
    }

    public RestrictedPreferenceHelper(Context context, Preference preference, AttributeSet attributeSet, String str, int i) {
        CharSequence charSequence;
        this.mAttrUserRestriction = null;
        this.mDisabledSummary = false;
        this.mContext = context;
        this.mPreference = preference;
        this.packageName = str;
        this.uid = i;
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.RestrictedPreference);
            TypedValue peekValue = obtainStyledAttributes.peekValue(R$styleable.RestrictedPreference_userRestriction);
            if (peekValue == null || peekValue.type != 3) {
                charSequence = null;
            } else {
                int i2 = peekValue.resourceId;
                charSequence = i2 != 0 ? context.getText(i2) : peekValue.string;
            }
            String charSequence2 = charSequence == null ? null : charSequence.toString();
            this.mAttrUserRestriction = charSequence2;
            if (RestrictedLockUtilsInternal.hasBaseUserRestriction(context, charSequence2, UserHandle.myUserId())) {
                this.mAttrUserRestriction = null;
                return;
            }
            TypedValue peekValue2 = obtainStyledAttributes.peekValue(R$styleable.RestrictedPreference_useAdminDisabledSummary);
            if (peekValue2 != null) {
                boolean z = false;
                if (peekValue2.type == 18) {
                    z = false;
                    if (peekValue2.data != 0) {
                        z = true;
                    }
                }
                this.mDisabledSummary = z;
            }
        }
    }

    public /* synthetic */ String lambda$getDisabledByAdminUpdatableString$0() {
        return this.mContext.getString(R$string.disabled_by_admin_summary_text);
    }

    public void checkRestrictionAndSetDisabled(String str, int i) {
        setDisabledByAdmin(RestrictedLockUtilsInternal.checkIfRestrictionEnforced(this.mContext, str, i));
    }

    public final String getDisabledByAdminUpdatableString() {
        return ((DevicePolicyManager) this.mContext.getSystemService(DevicePolicyManager.class)).getResources().getString("Settings.CONTROLLED_BY_ADMIN_SUMMARY", new Supplier() { // from class: com.android.settingslib.RestrictedPreferenceHelper$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final Object get() {
                return RestrictedPreferenceHelper.$r8$lambda$PpwEbNSD_9QumHyGDvBzP3cweX8(RestrictedPreferenceHelper.this);
            }
        });
    }

    public boolean isDisabledByAdmin() {
        return this.mDisabledByAdmin;
    }

    public boolean isDisabledByAppOps() {
        return this.mDisabledByAppOps;
    }

    public void onAttachedToHierarchy() {
        String str = this.mAttrUserRestriction;
        if (str != null) {
            checkRestrictionAndSetDisabled(str, UserHandle.myUserId());
        }
    }

    public void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        TextView textView;
        if (this.mDisabledByAdmin || this.mDisabledByAppOps) {
            preferenceViewHolder.itemView.setEnabled(true);
        }
        if (!this.mDisabledSummary || (textView = (TextView) preferenceViewHolder.findViewById(16908304)) == null) {
            return;
        }
        String disabledByAdminUpdatableString = BuildCompatUtils.isAtLeastT() ? getDisabledByAdminUpdatableString() : this.mContext.getString(R$string.disabled_by_admin_summary_text);
        if (this.mDisabledByAdmin) {
            textView.setText(disabledByAdminUpdatableString);
        } else if (this.mDisabledByAppOps) {
            textView.setText(R$string.disabled_by_app_ops_text);
        } else if (TextUtils.equals(disabledByAdminUpdatableString, textView.getText())) {
            textView.setText((CharSequence) null);
        }
    }

    public boolean performClick() {
        if (this.mDisabledByAdmin) {
            RestrictedLockUtils.sendShowAdminSupportDetailsIntent(this.mContext, this.mEnforcedAdmin);
            return true;
        } else if (this.mDisabledByAppOps) {
            RestrictedLockUtilsInternal.sendShowRestrictedSettingDialogIntent(this.mContext, this.packageName, this.uid);
            return true;
        } else {
            return false;
        }
    }

    public boolean setDisabledByAdmin(RestrictedLockUtils.EnforcedAdmin enforcedAdmin) {
        boolean z;
        boolean z2 = enforcedAdmin != null;
        this.mEnforcedAdmin = enforcedAdmin;
        if (this.mDisabledByAdmin != z2) {
            this.mDisabledByAdmin = z2;
            updateDisabledState();
            z = true;
        } else {
            z = false;
        }
        return z;
    }

    public boolean setDisabledByAppOps(boolean z) {
        boolean z2;
        if (this.mDisabledByAppOps != z) {
            this.mDisabledByAppOps = z;
            z2 = true;
            updateDisabledState();
        } else {
            z2 = false;
        }
        return z2;
    }

    public final void updateDisabledState() {
        Preference preference = this.mPreference;
        if (!(preference instanceof RestrictedTopLevelPreference)) {
            preference.setEnabled((this.mDisabledByAdmin || this.mDisabledByAppOps) ? false : true);
        }
        Preference preference2 = this.mPreference;
        if (preference2 instanceof PrimarySwitchPreference) {
            ((PrimarySwitchPreference) preference2).setSwitchEnabled((this.mDisabledByAdmin || this.mDisabledByAppOps) ? false : true);
        }
    }

    public void useAdminDisabledSummary(boolean z) {
        this.mDisabledSummary = z;
    }
}