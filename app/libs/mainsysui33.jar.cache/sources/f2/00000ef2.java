package com.android.settingslib.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import com.android.settingslib.utils.BuildCompatUtils;
import java.util.ArrayList;
import java.util.List;

/* loaded from: mainsysui33.jar:com/android/settingslib/widget/MainSwitchBar.class */
public class MainSwitchBar extends LinearLayout implements CompoundButton.OnCheckedChangeListener {
    public int mBackgroundActivatedColor;
    public int mBackgroundColor;
    public Drawable mBackgroundDisabled;
    public Drawable mBackgroundOff;
    public Drawable mBackgroundOn;
    public View mFrameView;
    public Switch mSwitch;
    public final List<OnMainSwitchChangeListener> mSwitchChangeListeners;
    public TextView mTextView;

    /* loaded from: mainsysui33.jar:com/android/settingslib/widget/MainSwitchBar$SavedState.class */
    public static class SavedState extends View.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() { // from class: com.android.settingslib.widget.MainSwitchBar.SavedState.1
            @Override // android.os.Parcelable.Creator
            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            @Override // android.os.Parcelable.Creator
            public SavedState[] newArray(int i) {
                return new SavedState[i];
            }
        };
        public boolean mChecked;
        public boolean mVisible;

        public SavedState(Parcel parcel) {
            super(parcel);
            this.mChecked = ((Boolean) parcel.readValue(null)).booleanValue();
            this.mVisible = ((Boolean) parcel.readValue(null)).booleanValue();
        }

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        public String toString() {
            return "MainSwitchBar.SavedState{" + Integer.toHexString(System.identityHashCode(this)) + " checked=" + this.mChecked + " visible=" + this.mVisible + "}";
        }

        @Override // android.view.View.BaseSavedState, android.view.AbsSavedState, android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeValue(Boolean.valueOf(this.mChecked));
            parcel.writeValue(Boolean.valueOf(this.mVisible));
        }
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.settingslib.widget.MainSwitchBar$$ExternalSyntheticLambda0.onSwitchChanged(android.widget.Switch, boolean):void] */
    /* renamed from: $r8$lambda$rN8bSl54RQDz28q33LXHW78-FT8 */
    public static /* synthetic */ void m1174$r8$lambda$rN8bSl54RQDz28q33LXHW78FT8(MainSwitchBar mainSwitchBar, Switch r5, boolean z) {
        mainSwitchBar.lambda$new$0(r5, z);
    }

    public MainSwitchBar(Context context) {
        this(context, null);
    }

    public MainSwitchBar(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public MainSwitchBar(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public MainSwitchBar(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mSwitchChangeListeners = new ArrayList();
        LayoutInflater.from(context).inflate(R$layout.settingslib_main_switch_bar, this);
        if (!BuildCompatUtils.isAtLeastS()) {
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(new int[]{16843829});
            this.mBackgroundActivatedColor = obtainStyledAttributes.getColor(0, 0);
            this.mBackgroundColor = context.getColor(R$color.material_grey_600);
            obtainStyledAttributes.recycle();
        }
        setFocusable(true);
        setClickable(true);
        this.mFrameView = findViewById(R$id.frame);
        this.mTextView = (TextView) findViewById(R$id.switch_text);
        this.mSwitch = (Switch) findViewById(16908352);
        if (BuildCompatUtils.isAtLeastS()) {
            this.mBackgroundOn = getContext().getDrawable(R$drawable.settingslib_switch_bar_bg_on);
            this.mBackgroundOff = getContext().getDrawable(R$drawable.settingslib_switch_bar_bg_off);
            this.mBackgroundDisabled = getContext().getDrawable(R$drawable.settingslib_switch_bar_bg_disabled);
        }
        addOnSwitchChangeListener(new OnMainSwitchChangeListener() { // from class: com.android.settingslib.widget.MainSwitchBar$$ExternalSyntheticLambda0
            @Override // com.android.settingslib.widget.OnMainSwitchChangeListener
            public final void onSwitchChanged(Switch r5, boolean z) {
                MainSwitchBar.m1174$r8$lambda$rN8bSl54RQDz28q33LXHW78FT8(MainSwitchBar.this, r5, z);
            }
        });
        if (this.mSwitch.getVisibility() == 0) {
            this.mSwitch.setOnCheckedChangeListener(this);
        }
        setChecked(this.mSwitch.isChecked());
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes2 = context.obtainStyledAttributes(attributeSet, androidx.preference.R$styleable.Preference, 0, 0);
            setTitle(obtainStyledAttributes2.getText(androidx.preference.R$styleable.Preference_android_title));
            obtainStyledAttributes2.recycle();
        }
        setBackground(this.mSwitch.isChecked());
    }

    public /* synthetic */ void lambda$new$0(Switch r4, boolean z) {
        setChecked(z);
    }

    public void addOnSwitchChangeListener(OnMainSwitchChangeListener onMainSwitchChangeListener) {
        if (this.mSwitchChangeListeners.contains(onMainSwitchChangeListener)) {
            return;
        }
        this.mSwitchChangeListeners.add(onMainSwitchChangeListener);
    }

    public boolean isChecked() {
        return this.mSwitch.isChecked();
    }

    public boolean isShowing() {
        return getVisibility() == 0;
    }

    @Override // android.widget.CompoundButton.OnCheckedChangeListener
    public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        propagateChecked(z);
    }

    @Override // android.view.View
    public void onRestoreInstanceState(Parcelable parcelable) {
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        this.mSwitch.setChecked(savedState.mChecked);
        setChecked(savedState.mChecked);
        setBackground(savedState.mChecked);
        setVisibility(savedState.mVisible ? 0 : 8);
        this.mSwitch.setOnCheckedChangeListener(savedState.mVisible ? this : null);
        requestLayout();
    }

    @Override // android.view.View
    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.mChecked = this.mSwitch.isChecked();
        savedState.mVisible = isShowing();
        return savedState;
    }

    @Override // android.view.View
    public boolean performClick() {
        this.mSwitch.performClick();
        return super.performClick();
    }

    public final void propagateChecked(boolean z) {
        setBackground(z);
        int size = this.mSwitchChangeListeners.size();
        for (int i = 0; i < size; i++) {
            this.mSwitchChangeListeners.get(i).onSwitchChanged(this.mSwitch, z);
        }
    }

    public final void setBackground(boolean z) {
        if (BuildCompatUtils.isAtLeastS()) {
            this.mFrameView.setBackground(z ? this.mBackgroundOn : this.mBackgroundOff);
        } else {
            setBackgroundColor(z ? this.mBackgroundActivatedColor : this.mBackgroundColor);
        }
    }

    public void setChecked(boolean z) {
        Switch r0 = this.mSwitch;
        if (r0 != null) {
            r0.setChecked(z);
        }
        setBackground(z);
    }

    @Override // android.view.View
    public void setEnabled(boolean z) {
        super.setEnabled(z);
        this.mTextView.setEnabled(z);
        this.mSwitch.setEnabled(z);
        if (BuildCompatUtils.isAtLeastS()) {
            if (z) {
                this.mFrameView.setBackground(isChecked() ? this.mBackgroundOn : this.mBackgroundOff);
            } else {
                this.mFrameView.setBackground(this.mBackgroundDisabled);
            }
        }
    }

    public void setTitle(CharSequence charSequence) {
        TextView textView = this.mTextView;
        if (textView != null) {
            textView.setText(charSequence);
        }
    }

    public void show() {
        setVisibility(0);
        this.mSwitch.setOnCheckedChangeListener(this);
    }
}