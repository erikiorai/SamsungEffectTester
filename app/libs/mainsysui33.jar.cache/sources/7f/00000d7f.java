package com.android.settingslib;

import android.app.AppOpsManager;
import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.core.content.res.TypedArrayUtils;
import androidx.preference.PreferenceManager;
import androidx.preference.PreferenceViewHolder;
import androidx.preference.SwitchPreference;
import com.android.settingslib.utils.BuildCompatUtils;
import java.util.function.Supplier;

/* loaded from: mainsysui33.jar:com/android/settingslib/RestrictedSwitchPreference.class */
public class RestrictedSwitchPreference extends SwitchPreference {
    public AppOpsManager mAppOpsManager;
    public RestrictedPreferenceHelper mHelper;
    public int mIconSize;
    public CharSequence mRestrictedSwitchSummary;
    public boolean mUseAdditionalSummary;

    /* JADX DEBUG: Method not inlined, still used in: [com.android.settingslib.RestrictedSwitchPreference$$ExternalSyntheticLambda0.get():java.lang.Object] */
    public static /* synthetic */ String $r8$lambda$lVy9C7QetpoqFjFRQaSTCJv1FGM(Context context, int i) {
        return context.getString(i);
    }

    public RestrictedSwitchPreference(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, TypedArrayUtils.getAttr(context, R$attr.switchPreferenceStyle, 16843629));
    }

    public RestrictedSwitchPreference(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public RestrictedSwitchPreference(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mUseAdditionalSummary = false;
        this.mHelper = new RestrictedPreferenceHelper(context, this, attributeSet);
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.RestrictedSwitchPreference);
            TypedValue peekValue = obtainStyledAttributes.peekValue(R$styleable.RestrictedSwitchPreference_useAdditionalSummary);
            if (peekValue != null) {
                this.mUseAdditionalSummary = peekValue.type == 18 && peekValue.data != 0;
            }
            TypedValue peekValue2 = obtainStyledAttributes.peekValue(R$styleable.RestrictedSwitchPreference_restrictedSwitchSummary);
            obtainStyledAttributes.recycle();
            if (peekValue2 != null && peekValue2.type == 3) {
                int i3 = peekValue2.resourceId;
                if (i3 != 0) {
                    this.mRestrictedSwitchSummary = context.getText(i3);
                } else {
                    this.mRestrictedSwitchSummary = peekValue2.string;
                }
            }
        }
        if (this.mUseAdditionalSummary) {
            setLayoutResource(R$layout.restricted_switch_preference);
            useAdminDisabledSummary(false);
        }
    }

    public static String getUpdatableEnterpriseString(final Context context, String str, final int i) {
        return !BuildCompatUtils.isAtLeastT() ? context.getString(i) : ((DevicePolicyManager) context.getSystemService(DevicePolicyManager.class)).getResources().getString(str, new Supplier() { // from class: com.android.settingslib.RestrictedSwitchPreference$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final Object get() {
                return RestrictedSwitchPreference.$r8$lambda$lVy9C7QetpoqFjFRQaSTCJv1FGM(context, i);
            }
        });
    }

    public boolean isDisabledByAdmin() {
        return this.mHelper.isDisabledByAdmin();
    }

    public boolean isDisabledByAppOps() {
        return this.mHelper.isDisabledByAppOps();
    }

    @Override // androidx.preference.Preference
    public void onAttachedToHierarchy(PreferenceManager preferenceManager) {
        this.mHelper.onAttachedToHierarchy();
        super.onAttachedToHierarchy(preferenceManager);
    }

    @Override // androidx.preference.SwitchPreference, androidx.preference.Preference
    public void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        View findViewById = preferenceViewHolder.findViewById(16908352);
        if (findViewById != null) {
            findViewById.getRootView().setFilterTouchesWhenObscured(true);
        }
        this.mHelper.onBindViewHolder(preferenceViewHolder);
        CharSequence charSequence = this.mRestrictedSwitchSummary;
        String str = charSequence;
        if (charSequence == null) {
            str = isChecked() ? getUpdatableEnterpriseString(getContext(), "Settings.ENABLED_BY_ADMIN_SWITCH_SUMMARY", R$string.enabled_by_admin) : getUpdatableEnterpriseString(getContext(), "Settings.DISABLED_BY_ADMIN_SWITCH_SUMMARY", R$string.disabled_by_admin);
        }
        ImageView imageView = (ImageView) preferenceViewHolder.itemView.findViewById(16908294);
        if (this.mIconSize > 0) {
            int i = this.mIconSize;
            imageView.setLayoutParams(new LinearLayout.LayoutParams(i, i));
        }
        if (!this.mUseAdditionalSummary) {
            TextView textView = (TextView) preferenceViewHolder.findViewById(16908304);
            if (textView == null || !isDisabledByAdmin()) {
                return;
            }
            textView.setText(str);
            textView.setVisibility(0);
            return;
        }
        TextView textView2 = (TextView) preferenceViewHolder.findViewById(R$id.additional_summary);
        if (textView2 != null) {
            if (!isDisabledByAdmin()) {
                textView2.setVisibility(8);
                return;
            }
            textView2.setText(str);
            textView2.setVisibility(0);
        }
    }

    @Override // androidx.preference.Preference
    public void performClick() {
        if (this.mHelper.performClick()) {
            return;
        }
        super.performClick();
    }

    public void setAppOps(AppOpsManager appOpsManager) {
        this.mAppOpsManager = appOpsManager;
    }

    @Override // androidx.preference.Preference
    public void setEnabled(boolean z) {
        boolean z2;
        if (z && isDisabledByAdmin()) {
            this.mHelper.setDisabledByAdmin(null);
            z2 = true;
        } else {
            z2 = false;
        }
        if (z && isDisabledByAppOps()) {
            this.mHelper.setDisabledByAppOps(false);
            z2 = true;
        }
        if (z2) {
            return;
        }
        super.setEnabled(z);
    }

    public void useAdminDisabledSummary(boolean z) {
        this.mHelper.useAdminDisabledSummary(z);
    }
}