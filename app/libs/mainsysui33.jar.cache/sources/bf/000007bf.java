package androidx.preference;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.AbsSavedState;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.res.TypedArrayUtils;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.preference.PreferenceManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/* loaded from: mainsysui33.jar:androidx/preference/Preference.class */
public class Preference implements Comparable<Preference> {
    public boolean mAllowDividerAbove;
    public boolean mAllowDividerBelow;
    public boolean mBaseMethodCalled;
    public final View.OnClickListener mClickListener;
    public Context mContext;
    public boolean mCopyingEnabled;
    public Object mDefaultValue;
    public String mDependencyKey;
    public boolean mDependencyMet;
    public List<Preference> mDependents;
    public boolean mEnabled;
    public Bundle mExtras;
    public String mFragment;
    public boolean mHasId;
    public boolean mHasSingleLineTitleAttr;
    public Drawable mIcon;
    public int mIconResId;
    public boolean mIconSpaceReserved;
    public long mId;
    public Intent mIntent;
    public String mKey;
    public int mLayoutResId;
    public OnPreferenceChangeInternalListener mListener;
    public OnPreferenceChangeListener mOnChangeListener;
    public OnPreferenceClickListener mOnClickListener;
    public OnPreferenceCopyListener mOnCopyListener;
    public int mOrder;
    public boolean mParentDependencyMet;
    public PreferenceGroup mParentGroup;
    public boolean mPersistent;
    public PreferenceManager mPreferenceManager;
    public boolean mRequiresKey;
    public boolean mSelectable;
    public boolean mShouldDisableView;
    public boolean mSingleLineTitle;
    public CharSequence mSummary;
    public SummaryProvider mSummaryProvider;
    public CharSequence mTitle;
    public int mViewId;
    public boolean mVisible;
    public boolean mWasDetached;
    public int mWidgetLayoutResId;

    /* loaded from: mainsysui33.jar:androidx/preference/Preference$BaseSavedState.class */
    public static class BaseSavedState extends AbsSavedState {
        public static final Parcelable.Creator<BaseSavedState> CREATOR = new Parcelable.Creator<BaseSavedState>() { // from class: androidx.preference.Preference.BaseSavedState.1
            /* JADX DEBUG: Method merged with bridge method */
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public BaseSavedState createFromParcel(Parcel parcel) {
                return new BaseSavedState(parcel);
            }

            /* JADX DEBUG: Method merged with bridge method */
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public BaseSavedState[] newArray(int i) {
                return new BaseSavedState[i];
            }
        };

        public BaseSavedState(Parcel parcel) {
            super(parcel);
        }

        public BaseSavedState(Parcelable parcelable) {
            super(parcelable);
        }
    }

    /* loaded from: mainsysui33.jar:androidx/preference/Preference$OnPreferenceChangeInternalListener.class */
    public interface OnPreferenceChangeInternalListener {
        void onPreferenceChange(Preference preference);

        void onPreferenceHierarchyChange(Preference preference);

        void onPreferenceVisibilityChange(Preference preference);
    }

    /* loaded from: mainsysui33.jar:androidx/preference/Preference$OnPreferenceChangeListener.class */
    public interface OnPreferenceChangeListener {
        boolean onPreferenceChange(Preference preference, Object obj);
    }

    /* loaded from: mainsysui33.jar:androidx/preference/Preference$OnPreferenceClickListener.class */
    public interface OnPreferenceClickListener {
        boolean onPreferenceClick(Preference preference);
    }

    /* loaded from: mainsysui33.jar:androidx/preference/Preference$OnPreferenceCopyListener.class */
    public static class OnPreferenceCopyListener implements View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {
        public final Preference mPreference;

        public OnPreferenceCopyListener(Preference preference) {
            this.mPreference = preference;
        }

        @Override // android.view.View.OnCreateContextMenuListener
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            CharSequence summary = this.mPreference.getSummary();
            if (!this.mPreference.isCopyingEnabled() || TextUtils.isEmpty(summary)) {
                return;
            }
            contextMenu.setHeaderTitle(summary);
            contextMenu.add(0, 0, 0, R$string.copy).setOnMenuItemClickListener(this);
        }

        @Override // android.view.MenuItem.OnMenuItemClickListener
        public boolean onMenuItemClick(MenuItem menuItem) {
            ClipboardManager clipboardManager = (ClipboardManager) this.mPreference.getContext().getSystemService("clipboard");
            CharSequence summary = this.mPreference.getSummary();
            clipboardManager.setPrimaryClip(ClipData.newPlainText("Preference", summary));
            Toast.makeText(this.mPreference.getContext(), this.mPreference.getContext().getString(R$string.preference_copied, summary), 0).show();
            return true;
        }
    }

    /* loaded from: mainsysui33.jar:androidx/preference/Preference$SummaryProvider.class */
    public interface SummaryProvider<T extends Preference> {
        CharSequence provideSummary(T t);
    }

    public Preference(Context context) {
        this(context, null);
    }

    public Preference(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, TypedArrayUtils.getAttr(context, R$attr.preferenceStyle, 16842894));
    }

    public Preference(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public Preference(Context context, AttributeSet attributeSet, int i, int i2) {
        this.mOrder = Integer.MAX_VALUE;
        this.mViewId = 0;
        this.mEnabled = true;
        this.mSelectable = true;
        this.mPersistent = true;
        this.mDependencyMet = true;
        this.mParentDependencyMet = true;
        this.mVisible = true;
        this.mAllowDividerAbove = true;
        this.mAllowDividerBelow = true;
        this.mSingleLineTitle = true;
        this.mShouldDisableView = true;
        int i3 = R$layout.preference;
        this.mLayoutResId = i3;
        this.mClickListener = new View.OnClickListener() { // from class: androidx.preference.Preference.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Preference.this.performClick(view);
            }
        };
        this.mContext = context;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.Preference, i, i2);
        this.mIconResId = TypedArrayUtils.getResourceId(obtainStyledAttributes, R$styleable.Preference_icon, R$styleable.Preference_android_icon, 0);
        this.mKey = TypedArrayUtils.getString(obtainStyledAttributes, R$styleable.Preference_key, R$styleable.Preference_android_key);
        this.mTitle = TypedArrayUtils.getText(obtainStyledAttributes, R$styleable.Preference_title, R$styleable.Preference_android_title);
        this.mSummary = TypedArrayUtils.getText(obtainStyledAttributes, R$styleable.Preference_summary, R$styleable.Preference_android_summary);
        this.mOrder = TypedArrayUtils.getInt(obtainStyledAttributes, R$styleable.Preference_order, R$styleable.Preference_android_order, Integer.MAX_VALUE);
        this.mFragment = TypedArrayUtils.getString(obtainStyledAttributes, R$styleable.Preference_fragment, R$styleable.Preference_android_fragment);
        this.mLayoutResId = TypedArrayUtils.getResourceId(obtainStyledAttributes, R$styleable.Preference_layout, R$styleable.Preference_android_layout, i3);
        this.mWidgetLayoutResId = TypedArrayUtils.getResourceId(obtainStyledAttributes, R$styleable.Preference_widgetLayout, R$styleable.Preference_android_widgetLayout, 0);
        this.mEnabled = TypedArrayUtils.getBoolean(obtainStyledAttributes, R$styleable.Preference_enabled, R$styleable.Preference_android_enabled, true);
        this.mSelectable = TypedArrayUtils.getBoolean(obtainStyledAttributes, R$styleable.Preference_selectable, R$styleable.Preference_android_selectable, true);
        this.mPersistent = TypedArrayUtils.getBoolean(obtainStyledAttributes, R$styleable.Preference_persistent, R$styleable.Preference_android_persistent, true);
        this.mDependencyKey = TypedArrayUtils.getString(obtainStyledAttributes, R$styleable.Preference_dependency, R$styleable.Preference_android_dependency);
        int i4 = R$styleable.Preference_allowDividerAbove;
        this.mAllowDividerAbove = TypedArrayUtils.getBoolean(obtainStyledAttributes, i4, i4, this.mSelectable);
        int i5 = R$styleable.Preference_allowDividerBelow;
        this.mAllowDividerBelow = TypedArrayUtils.getBoolean(obtainStyledAttributes, i5, i5, this.mSelectable);
        int i6 = R$styleable.Preference_defaultValue;
        if (obtainStyledAttributes.hasValue(i6)) {
            this.mDefaultValue = onGetDefaultValue(obtainStyledAttributes, i6);
        } else {
            int i7 = R$styleable.Preference_android_defaultValue;
            if (obtainStyledAttributes.hasValue(i7)) {
                this.mDefaultValue = onGetDefaultValue(obtainStyledAttributes, i7);
            }
        }
        this.mShouldDisableView = TypedArrayUtils.getBoolean(obtainStyledAttributes, R$styleable.Preference_shouldDisableView, R$styleable.Preference_android_shouldDisableView, true);
        int i8 = R$styleable.Preference_singleLineTitle;
        boolean hasValue = obtainStyledAttributes.hasValue(i8);
        this.mHasSingleLineTitleAttr = hasValue;
        if (hasValue) {
            this.mSingleLineTitle = TypedArrayUtils.getBoolean(obtainStyledAttributes, i8, R$styleable.Preference_android_singleLineTitle, true);
        }
        this.mIconSpaceReserved = TypedArrayUtils.getBoolean(obtainStyledAttributes, R$styleable.Preference_iconSpaceReserved, R$styleable.Preference_android_iconSpaceReserved, false);
        int i9 = R$styleable.Preference_isPreferenceVisible;
        this.mVisible = TypedArrayUtils.getBoolean(obtainStyledAttributes, i9, i9, true);
        int i10 = R$styleable.Preference_enableCopying;
        this.mCopyingEnabled = TypedArrayUtils.getBoolean(obtainStyledAttributes, i10, i10, false);
        obtainStyledAttributes.recycle();
    }

    public void assignParent(PreferenceGroup preferenceGroup) {
        if (preferenceGroup != null && this.mParentGroup != null) {
            throw new IllegalStateException("This preference already has a parent. You must remove the existing parent before assigning a new one.");
        }
        this.mParentGroup = preferenceGroup;
    }

    public boolean callChangeListener(Object obj) {
        OnPreferenceChangeListener onPreferenceChangeListener = this.mOnChangeListener;
        return onPreferenceChangeListener == null || onPreferenceChangeListener.onPreferenceChange(this, obj);
    }

    public final void clearWasDetached() {
        this.mWasDetached = false;
    }

    /* JADX DEBUG: Method merged with bridge method */
    @Override // java.lang.Comparable
    public int compareTo(Preference preference) {
        int i = this.mOrder;
        int i2 = preference.mOrder;
        if (i != i2) {
            return i - i2;
        }
        CharSequence charSequence = this.mTitle;
        CharSequence charSequence2 = preference.mTitle;
        if (charSequence == charSequence2) {
            return 0;
        }
        if (charSequence == null) {
            return 1;
        }
        if (charSequence2 == null) {
            return -1;
        }
        return charSequence.toString().compareToIgnoreCase(preference.mTitle.toString());
    }

    public void dispatchRestoreInstanceState(Bundle bundle) {
        Parcelable parcelable;
        if (!hasKey() || (parcelable = bundle.getParcelable(this.mKey)) == null) {
            return;
        }
        this.mBaseMethodCalled = false;
        onRestoreInstanceState(parcelable);
        if (!this.mBaseMethodCalled) {
            throw new IllegalStateException("Derived class did not call super.onRestoreInstanceState()");
        }
    }

    public void dispatchSaveInstanceState(Bundle bundle) {
        if (hasKey()) {
            this.mBaseMethodCalled = false;
            Parcelable onSaveInstanceState = onSaveInstanceState();
            if (!this.mBaseMethodCalled) {
                throw new IllegalStateException("Derived class did not call super.onSaveInstanceState()");
            }
            if (onSaveInstanceState != null) {
                bundle.putParcelable(this.mKey, onSaveInstanceState);
            }
        }
    }

    public final void dispatchSetInitialValue() {
        getPreferenceDataStore();
        if (shouldPersist() && getSharedPreferences().contains(this.mKey)) {
            onSetInitialValue(true, null);
            return;
        }
        Object obj = this.mDefaultValue;
        if (obj != null) {
            onSetInitialValue(false, obj);
        }
    }

    public <T extends Preference> T findPreferenceInHierarchy(String str) {
        PreferenceManager preferenceManager = this.mPreferenceManager;
        if (preferenceManager == null) {
            return null;
        }
        return (T) preferenceManager.findPreference(str);
    }

    public Context getContext() {
        return this.mContext;
    }

    public Bundle getExtras() {
        if (this.mExtras == null) {
            this.mExtras = new Bundle();
        }
        return this.mExtras;
    }

    public StringBuilder getFilterableStringBuilder() {
        StringBuilder sb = new StringBuilder();
        CharSequence title = getTitle();
        if (!TextUtils.isEmpty(title)) {
            sb.append(title);
            sb.append(' ');
        }
        CharSequence summary = getSummary();
        if (!TextUtils.isEmpty(summary)) {
            sb.append(summary);
            sb.append(' ');
        }
        if (sb.length() > 0) {
            sb.setLength(sb.length() - 1);
        }
        return sb;
    }

    public String getFragment() {
        return this.mFragment;
    }

    public Drawable getIcon() {
        int i;
        if (this.mIcon == null && (i = this.mIconResId) != 0) {
            this.mIcon = AppCompatResources.getDrawable(this.mContext, i);
        }
        return this.mIcon;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public long getId() {
        return this.mId;
    }

    public Intent getIntent() {
        return this.mIntent;
    }

    public String getKey() {
        return this.mKey;
    }

    public final int getLayoutResource() {
        return this.mLayoutResId;
    }

    public int getOrder() {
        return this.mOrder;
    }

    public PreferenceGroup getParent() {
        return this.mParentGroup;
    }

    public boolean getPersistedBoolean(boolean z) {
        if (shouldPersist()) {
            getPreferenceDataStore();
            return this.mPreferenceManager.getSharedPreferences().getBoolean(this.mKey, z);
        }
        return z;
    }

    public int getPersistedInt(int i) {
        if (shouldPersist()) {
            getPreferenceDataStore();
            return this.mPreferenceManager.getSharedPreferences().getInt(this.mKey, i);
        }
        return i;
    }

    public String getPersistedString(String str) {
        if (shouldPersist()) {
            getPreferenceDataStore();
            return this.mPreferenceManager.getSharedPreferences().getString(this.mKey, str);
        }
        return str;
    }

    public Set<String> getPersistedStringSet(Set<String> set) {
        if (shouldPersist()) {
            getPreferenceDataStore();
            return this.mPreferenceManager.getSharedPreferences().getStringSet(this.mKey, set);
        }
        return set;
    }

    public PreferenceDataStore getPreferenceDataStore() {
        PreferenceManager preferenceManager = this.mPreferenceManager;
        if (preferenceManager != null) {
            preferenceManager.getPreferenceDataStore();
            return null;
        }
        return null;
    }

    public PreferenceManager getPreferenceManager() {
        return this.mPreferenceManager;
    }

    public SharedPreferences getSharedPreferences() {
        if (this.mPreferenceManager != null) {
            getPreferenceDataStore();
            return this.mPreferenceManager.getSharedPreferences();
        }
        return null;
    }

    public CharSequence getSummary() {
        return getSummaryProvider() != null ? getSummaryProvider().provideSummary(this) : this.mSummary;
    }

    public final SummaryProvider getSummaryProvider() {
        return this.mSummaryProvider;
    }

    public CharSequence getTitle() {
        return this.mTitle;
    }

    public final int getWidgetLayoutResource() {
        return this.mWidgetLayoutResId;
    }

    public boolean hasKey() {
        return !TextUtils.isEmpty(this.mKey);
    }

    public boolean isCopyingEnabled() {
        return this.mCopyingEnabled;
    }

    public boolean isEnabled() {
        return this.mEnabled && this.mDependencyMet && this.mParentDependencyMet;
    }

    public boolean isPersistent() {
        return this.mPersistent;
    }

    public boolean isSelectable() {
        return this.mSelectable;
    }

    public final boolean isVisible() {
        return this.mVisible;
    }

    public void notifyChanged() {
        OnPreferenceChangeInternalListener onPreferenceChangeInternalListener = this.mListener;
        if (onPreferenceChangeInternalListener != null) {
            onPreferenceChangeInternalListener.onPreferenceChange(this);
        }
    }

    public void notifyDependencyChange(boolean z) {
        List<Preference> list = this.mDependents;
        if (list == null) {
            return;
        }
        int size = list.size();
        for (int i = 0; i < size; i++) {
            list.get(i).onDependencyChanged(this, z);
        }
    }

    public void notifyHierarchyChanged() {
        OnPreferenceChangeInternalListener onPreferenceChangeInternalListener = this.mListener;
        if (onPreferenceChangeInternalListener != null) {
            onPreferenceChangeInternalListener.onPreferenceHierarchyChange(this);
        }
    }

    public void onAttached() {
        registerDependency();
    }

    public void onAttachedToHierarchy(PreferenceManager preferenceManager) {
        this.mPreferenceManager = preferenceManager;
        if (!this.mHasId) {
            this.mId = preferenceManager.getNextId();
        }
        dispatchSetInitialValue();
    }

    public void onAttachedToHierarchy(PreferenceManager preferenceManager, long j) {
        this.mId = j;
        this.mHasId = true;
        try {
            onAttachedToHierarchy(preferenceManager);
        } finally {
            this.mHasId = false;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0067  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x00c7  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x0133  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x0141  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x016d  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x0179  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x01c4  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x01cc  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        Integer num;
        TextView textView;
        ImageView imageView;
        View findViewById;
        View view;
        boolean isCopyingEnabled;
        View view2 = preferenceViewHolder.itemView;
        view2.setOnClickListener(this.mClickListener);
        view2.setId(this.mViewId);
        TextView textView2 = (TextView) preferenceViewHolder.findViewById(16908304);
        if (textView2 != null) {
            CharSequence summary = getSummary();
            if (!TextUtils.isEmpty(summary)) {
                textView2.setText(summary);
                textView2.setVisibility(0);
                num = Integer.valueOf(textView2.getCurrentTextColor());
                textView = (TextView) preferenceViewHolder.findViewById(16908310);
                if (textView != null) {
                    CharSequence title = getTitle();
                    if (TextUtils.isEmpty(title)) {
                        textView.setVisibility(8);
                    } else {
                        textView.setText(title);
                        textView.setVisibility(0);
                        if (this.mHasSingleLineTitleAttr) {
                            textView.setSingleLine(this.mSingleLineTitle);
                        }
                        if (!isSelectable() && isEnabled() && num != null) {
                            textView.setTextColor(num.intValue());
                        }
                    }
                }
                imageView = (ImageView) preferenceViewHolder.findViewById(16908294);
                if (imageView != null) {
                    int i = this.mIconResId;
                    if (i != 0 || this.mIcon != null) {
                        if (this.mIcon == null) {
                            this.mIcon = AppCompatResources.getDrawable(this.mContext, i);
                        }
                        Drawable drawable = this.mIcon;
                        if (drawable != null) {
                            imageView.setImageDrawable(drawable);
                        }
                    }
                    if (this.mIcon != null) {
                        imageView.setVisibility(0);
                    } else {
                        imageView.setVisibility(this.mIconSpaceReserved ? 4 : 8);
                    }
                }
                findViewById = preferenceViewHolder.findViewById(R$id.icon_frame);
                view = findViewById;
                if (findViewById == null) {
                    view = preferenceViewHolder.findViewById(16908350);
                }
                if (view != null) {
                    if (this.mIcon != null) {
                        view.setVisibility(0);
                    } else {
                        int i2 = 8;
                        if (this.mIconSpaceReserved) {
                            i2 = 4;
                        }
                        view.setVisibility(i2);
                    }
                }
                if (this.mShouldDisableView) {
                    setEnabledStateOnViews(view2, true);
                } else {
                    setEnabledStateOnViews(view2, isEnabled());
                }
                boolean isSelectable = isSelectable();
                view2.setFocusable(isSelectable);
                view2.setClickable(isSelectable);
                preferenceViewHolder.setDividerAllowedAbove(this.mAllowDividerAbove);
                preferenceViewHolder.setDividerAllowedBelow(this.mAllowDividerBelow);
                isCopyingEnabled = isCopyingEnabled();
                if (isCopyingEnabled && this.mOnCopyListener == null) {
                    this.mOnCopyListener = new OnPreferenceCopyListener(this);
                }
                view2.setOnCreateContextMenuListener(!isCopyingEnabled ? this.mOnCopyListener : null);
                view2.setLongClickable(isCopyingEnabled);
                if (isCopyingEnabled || isSelectable) {
                }
                ViewCompat.setBackground(view2, null);
                return;
            }
            textView2.setVisibility(8);
        }
        num = null;
        textView = (TextView) preferenceViewHolder.findViewById(16908310);
        if (textView != null) {
        }
        imageView = (ImageView) preferenceViewHolder.findViewById(16908294);
        if (imageView != null) {
        }
        findViewById = preferenceViewHolder.findViewById(R$id.icon_frame);
        view = findViewById;
        if (findViewById == null) {
        }
        if (view != null) {
        }
        if (this.mShouldDisableView) {
        }
        boolean isSelectable2 = isSelectable();
        view2.setFocusable(isSelectable2);
        view2.setClickable(isSelectable2);
        preferenceViewHolder.setDividerAllowedAbove(this.mAllowDividerAbove);
        preferenceViewHolder.setDividerAllowedBelow(this.mAllowDividerBelow);
        isCopyingEnabled = isCopyingEnabled();
        if (isCopyingEnabled) {
            this.mOnCopyListener = new OnPreferenceCopyListener(this);
        }
        view2.setOnCreateContextMenuListener(!isCopyingEnabled ? this.mOnCopyListener : null);
        view2.setLongClickable(isCopyingEnabled);
        if (isCopyingEnabled) {
        }
    }

    public void onClick() {
    }

    public void onDependencyChanged(Preference preference, boolean z) {
        if (this.mDependencyMet == z) {
            this.mDependencyMet = !z;
            notifyDependencyChange(shouldDisableDependents());
            notifyChanged();
        }
    }

    public void onDetached() {
        unregisterDependency();
        this.mWasDetached = true;
    }

    public Object onGetDefaultValue(TypedArray typedArray, int i) {
        return null;
    }

    @Deprecated
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
    }

    public void onParentChanged(Preference preference, boolean z) {
        if (this.mParentDependencyMet == z) {
            this.mParentDependencyMet = !z;
            notifyDependencyChange(shouldDisableDependents());
            notifyChanged();
        }
    }

    public void onPrepareForRemoval() {
        unregisterDependency();
    }

    public void onRestoreInstanceState(Parcelable parcelable) {
        this.mBaseMethodCalled = true;
        if (parcelable != AbsSavedState.EMPTY_STATE && parcelable != null) {
            throw new IllegalArgumentException("Wrong state class -- expecting Preference State");
        }
    }

    public Parcelable onSaveInstanceState() {
        this.mBaseMethodCalled = true;
        return AbsSavedState.EMPTY_STATE;
    }

    public void onSetInitialValue(Object obj) {
    }

    @Deprecated
    public void onSetInitialValue(boolean z, Object obj) {
        onSetInitialValue(obj);
    }

    public void performClick() {
        PreferenceManager.OnPreferenceTreeClickListener onPreferenceTreeClickListener;
        if (isEnabled() && isSelectable()) {
            onClick();
            OnPreferenceClickListener onPreferenceClickListener = this.mOnClickListener;
            if (onPreferenceClickListener == null || !onPreferenceClickListener.onPreferenceClick(this)) {
                PreferenceManager preferenceManager = getPreferenceManager();
                if ((preferenceManager == null || (onPreferenceTreeClickListener = preferenceManager.getOnPreferenceTreeClickListener()) == null || !onPreferenceTreeClickListener.onPreferenceTreeClick(this)) && this.mIntent != null) {
                    getContext().startActivity(this.mIntent);
                }
            }
        }
    }

    public void performClick(View view) {
        performClick();
    }

    public boolean persistBoolean(boolean z) {
        if (shouldPersist()) {
            if (z == getPersistedBoolean(!z)) {
                return true;
            }
            getPreferenceDataStore();
            SharedPreferences.Editor editor = this.mPreferenceManager.getEditor();
            editor.putBoolean(this.mKey, z);
            tryCommit(editor);
            return true;
        }
        return false;
    }

    public boolean persistInt(int i) {
        if (shouldPersist()) {
            if (i == getPersistedInt(i ^ (-1))) {
                return true;
            }
            getPreferenceDataStore();
            SharedPreferences.Editor editor = this.mPreferenceManager.getEditor();
            editor.putInt(this.mKey, i);
            tryCommit(editor);
            return true;
        }
        return false;
    }

    public boolean persistString(String str) {
        if (shouldPersist()) {
            if (TextUtils.equals(str, getPersistedString(null))) {
                return true;
            }
            getPreferenceDataStore();
            SharedPreferences.Editor editor = this.mPreferenceManager.getEditor();
            editor.putString(this.mKey, str);
            tryCommit(editor);
            return true;
        }
        return false;
    }

    public boolean persistStringSet(Set<String> set) {
        if (shouldPersist()) {
            if (set.equals(getPersistedStringSet(null))) {
                return true;
            }
            getPreferenceDataStore();
            SharedPreferences.Editor editor = this.mPreferenceManager.getEditor();
            editor.putStringSet(this.mKey, set);
            tryCommit(editor);
            return true;
        }
        return false;
    }

    public final void registerDependency() {
        if (TextUtils.isEmpty(this.mDependencyKey)) {
            return;
        }
        Preference findPreferenceInHierarchy = findPreferenceInHierarchy(this.mDependencyKey);
        if (findPreferenceInHierarchy != null) {
            findPreferenceInHierarchy.registerDependent(this);
            return;
        }
        throw new IllegalStateException("Dependency \"" + this.mDependencyKey + "\" not found for preference \"" + this.mKey + "\" (title: \"" + ((Object) this.mTitle) + "\"");
    }

    public final void registerDependent(Preference preference) {
        if (this.mDependents == null) {
            this.mDependents = new ArrayList();
        }
        this.mDependents.add(preference);
        preference.onDependencyChanged(this, shouldDisableDependents());
    }

    public void requireKey() {
        if (TextUtils.isEmpty(this.mKey)) {
            throw new IllegalStateException("Preference does not have a key assigned.");
        }
        this.mRequiresKey = true;
    }

    public void restoreHierarchyState(Bundle bundle) {
        dispatchRestoreInstanceState(bundle);
    }

    public void saveHierarchyState(Bundle bundle) {
        dispatchSaveInstanceState(bundle);
    }

    public void setEnabled(boolean z) {
        if (this.mEnabled != z) {
            this.mEnabled = z;
            notifyDependencyChange(shouldDisableDependents());
            notifyChanged();
        }
    }

    public final void setEnabledStateOnViews(View view, boolean z) {
        view.setEnabled(z);
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            for (int childCount = viewGroup.getChildCount() - 1; childCount >= 0; childCount--) {
                setEnabledStateOnViews(viewGroup.getChildAt(childCount), z);
            }
        }
    }

    public void setIcon(int i) {
        setIcon(AppCompatResources.getDrawable(this.mContext, i));
        this.mIconResId = i;
    }

    public void setIcon(Drawable drawable) {
        if (this.mIcon != drawable) {
            this.mIcon = drawable;
            this.mIconResId = 0;
            notifyChanged();
        }
    }

    public void setIconSpaceReserved(boolean z) {
        if (this.mIconSpaceReserved != z) {
            this.mIconSpaceReserved = z;
            notifyChanged();
        }
    }

    public void setIntent(Intent intent) {
        this.mIntent = intent;
    }

    public void setKey(String str) {
        this.mKey = str;
        if (!this.mRequiresKey || hasKey()) {
            return;
        }
        requireKey();
    }

    public void setLayoutResource(int i) {
        this.mLayoutResId = i;
    }

    public final void setOnPreferenceChangeInternalListener(OnPreferenceChangeInternalListener onPreferenceChangeInternalListener) {
        this.mListener = onPreferenceChangeInternalListener;
    }

    public void setOnPreferenceChangeListener(OnPreferenceChangeListener onPreferenceChangeListener) {
        this.mOnChangeListener = onPreferenceChangeListener;
    }

    public void setOnPreferenceClickListener(OnPreferenceClickListener onPreferenceClickListener) {
        this.mOnClickListener = onPreferenceClickListener;
    }

    public void setOrder(int i) {
        if (i != this.mOrder) {
            this.mOrder = i;
            notifyHierarchyChanged();
        }
    }

    public void setPersistent(boolean z) {
        this.mPersistent = z;
    }

    public void setSelectable(boolean z) {
        if (this.mSelectable != z) {
            this.mSelectable = z;
            notifyChanged();
        }
    }

    public void setShouldDisableView(boolean z) {
        if (this.mShouldDisableView != z) {
            this.mShouldDisableView = z;
            notifyChanged();
        }
    }

    public void setSummary(int i) {
        setSummary(this.mContext.getString(i));
    }

    public void setSummary(CharSequence charSequence) {
        if (getSummaryProvider() != null) {
            throw new IllegalStateException("Preference already has a SummaryProvider set.");
        }
        if (TextUtils.equals(this.mSummary, charSequence)) {
            return;
        }
        this.mSummary = charSequence;
        notifyChanged();
    }

    public final void setSummaryProvider(SummaryProvider summaryProvider) {
        this.mSummaryProvider = summaryProvider;
        notifyChanged();
    }

    public void setTitle(int i) {
        setTitle(this.mContext.getString(i));
    }

    public void setTitle(CharSequence charSequence) {
        if (TextUtils.equals(charSequence, this.mTitle)) {
            return;
        }
        this.mTitle = charSequence;
        notifyChanged();
    }

    public final void setVisible(boolean z) {
        if (this.mVisible != z) {
            this.mVisible = z;
            OnPreferenceChangeInternalListener onPreferenceChangeInternalListener = this.mListener;
            if (onPreferenceChangeInternalListener != null) {
                onPreferenceChangeInternalListener.onPreferenceVisibilityChange(this);
            }
        }
    }

    public void setWidgetLayoutResource(int i) {
        this.mWidgetLayoutResId = i;
    }

    public boolean shouldDisableDependents() {
        return !isEnabled();
    }

    public boolean shouldPersist() {
        return this.mPreferenceManager != null && isPersistent() && hasKey();
    }

    public String toString() {
        return getFilterableStringBuilder().toString();
    }

    public final void tryCommit(SharedPreferences.Editor editor) {
        if (this.mPreferenceManager.shouldCommit()) {
            editor.apply();
        }
    }

    public final void unregisterDependency() {
        Preference findPreferenceInHierarchy;
        String str = this.mDependencyKey;
        if (str == null || (findPreferenceInHierarchy = findPreferenceInHierarchy(str)) == null) {
            return;
        }
        findPreferenceInHierarchy.unregisterDependent(this);
    }

    public final void unregisterDependent(Preference preference) {
        List<Preference> list = this.mDependents;
        if (list != null) {
            list.remove(preference);
        }
    }
}