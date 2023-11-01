package com.android.keyguard;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.android.internal.policy.SystemBarUtils;
import com.android.systemui.R$id;
import com.android.systemui.R$style;
import java.lang.ref.WeakReference;

/* loaded from: mainsysui33.jar:com/android/keyguard/KeyguardMessageArea.class */
public abstract class KeyguardMessageArea extends TextView implements SecurityMessageDisplay {
    public static final Object ANNOUNCE_TOKEN = new Object();
    public ViewGroup mContainer;
    public final Handler mHandler;
    public boolean mIsVisible;
    public CharSequence mMessage;
    public int mTopMargin;

    /* loaded from: mainsysui33.jar:com/android/keyguard/KeyguardMessageArea$AnnounceRunnable.class */
    public static class AnnounceRunnable implements Runnable {
        public final WeakReference<View> mHost;
        public final CharSequence mTextToAnnounce;

        public AnnounceRunnable(View view, CharSequence charSequence) {
            this.mHost = new WeakReference<>(view);
            this.mTextToAnnounce = charSequence;
        }

        @Override // java.lang.Runnable
        public void run() {
            View view = this.mHost.get();
            if (view != null) {
                view.announceForAccessibility(this.mTextToAnnounce);
            }
        }
    }

    public KeyguardMessageArea(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        setLayerType(2, null);
        this.mHandler = new Handler(Looper.myLooper());
        onThemeChanged();
    }

    public final void clearMessage() {
        this.mMessage = null;
        update();
    }

    @Override // android.widget.TextView, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mContainer = (ViewGroup) getRootView().findViewById(R$id.keyguard_message_area_container);
    }

    public void onConfigChanged() {
        int statusBarHeight;
        if (this.mContainer == null || this.mTopMargin == (statusBarHeight = SystemBarUtils.getStatusBarHeight(getContext()))) {
            return;
        }
        this.mTopMargin = statusBarHeight;
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) this.mContainer.getLayoutParams();
        marginLayoutParams.topMargin = this.mTopMargin;
        this.mContainer.setLayoutParams(marginLayoutParams);
    }

    public void onDensityOrFontScaleChanged() {
        TypedArray obtainStyledAttributes = ((TextView) this).mContext.obtainStyledAttributes(R$style.Keyguard_TextView, new int[]{16842901});
        setTextSize(0, obtainStyledAttributes.getDimensionPixelSize(0, 0));
        obtainStyledAttributes.recycle();
    }

    public void onThemeChanged() {
        update();
    }

    public final void securityMessageChanged(CharSequence charSequence) {
        this.mMessage = charSequence;
        update();
        Handler handler = this.mHandler;
        Object obj = ANNOUNCE_TOKEN;
        handler.removeCallbacksAndMessages(obj);
        this.mHandler.postAtTime(new AnnounceRunnable(this, getText()), obj, SystemClock.uptimeMillis() + 250);
    }

    public void setIsVisible(boolean z) {
        if (this.mIsVisible != z) {
            this.mIsVisible = z;
            update();
        }
    }

    public void setMessage(CharSequence charSequence, boolean z) {
        if (TextUtils.isEmpty(charSequence)) {
            clearMessage();
        } else {
            securityMessageChanged(charSequence);
        }
    }

    public void update() {
        CharSequence charSequence = this.mMessage;
        setVisibility((TextUtils.isEmpty(charSequence) || !this.mIsVisible) ? 4 : 0);
        setText(charSequence);
        updateTextColor();
    }

    public abstract void updateTextColor();
}