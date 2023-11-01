package com.android.systemui.qs.tiles;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.internal.util.ArrayUtils;
import com.android.systemui.FontSizeUtils;
import com.android.systemui.R$dimen;
import com.android.systemui.R$id;
import com.android.systemui.R$layout;
import com.android.systemui.R$styleable;
import com.android.systemui.statusbar.phone.UserAvatarView;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/tiles/UserDetailItemView.class */
public class UserDetailItemView extends LinearLayout {
    public static int layoutResId = R$layout.qs_user_detail_item;
    public int mActivatedStyle;
    public UserAvatarView mAvatar;
    public TextView mName;
    public int mRegularStyle;
    public View mRestrictedPadlock;

    public UserDetailItemView(Context context) {
        this(context, null);
    }

    public UserDetailItemView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public UserDetailItemView(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public UserDetailItemView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.UserDetailItemView, i, i2);
        int indexCount = obtainStyledAttributes.getIndexCount();
        for (int i3 = 0; i3 < indexCount; i3++) {
            int index = obtainStyledAttributes.getIndex(i3);
            if (index == R$styleable.UserDetailItemView_regularTextAppearance) {
                this.mRegularStyle = obtainStyledAttributes.getResourceId(index, 0);
            } else if (index == R$styleable.UserDetailItemView_activatedTextAppearance) {
                this.mActivatedStyle = obtainStyledAttributes.getResourceId(index, 0);
            }
        }
        obtainStyledAttributes.recycle();
    }

    public static UserDetailItemView convertOrInflate(Context context, View view, ViewGroup viewGroup) {
        View view2 = view;
        if (!(view instanceof UserDetailItemView)) {
            view2 = LayoutInflater.from(context).inflate(layoutResId, viewGroup, false);
        }
        return (UserDetailItemView) view2;
    }

    public void bind(String str, Drawable drawable, int i) {
        this.mName.setText(str);
        this.mAvatar.setDrawableWithBadge(drawable, i);
    }

    @Override // android.view.ViewGroup, android.view.View
    public void drawableStateChanged() {
        super.drawableStateChanged();
        updateTextStyle();
    }

    public int getFontSizeDimen() {
        return R$dimen.qs_tile_text_size;
    }

    @Override // android.view.View
    public boolean hasOverlappingRendering() {
        return false;
    }

    @Override // android.view.View
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        FontSizeUtils.updateFontSize(this.mName, getFontSizeDimen());
    }

    @Override // android.view.View
    public void onFinishInflate() {
        this.mAvatar = findViewById(R$id.user_picture);
        TextView textView = (TextView) findViewById(R$id.user_name);
        this.mName = textView;
        if (this.mRegularStyle == 0) {
            this.mRegularStyle = textView.getExplicitStyle();
        }
        if (this.mActivatedStyle == 0) {
            this.mActivatedStyle = this.mName.getExplicitStyle();
        }
        updateTextStyle();
        this.mRestrictedPadlock = findViewById(R$id.restricted_padlock);
    }

    public void setDisabledByAdmin(boolean z) {
        View view = this.mRestrictedPadlock;
        if (view != null) {
            view.setVisibility(z ? 0 : 8);
        }
        setEnabled(!z);
    }

    @Override // android.view.View
    public void setEnabled(boolean z) {
        super.setEnabled(z);
        this.mName.setEnabled(z);
        this.mAvatar.setEnabled(z);
    }

    public final void updateTextStyle() {
        this.mName.setTextAppearance(ArrayUtils.contains(getDrawableState(), 16843518) ? this.mActivatedStyle : this.mRegularStyle);
    }
}