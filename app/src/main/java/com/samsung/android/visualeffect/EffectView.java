package com.samsung.android.visualeffect;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import java.util.HashMap;


public class EffectView extends FrameLayout implements IEffectView {

    private final String TAG = "EffectView";
    private Context mContext;
    private int mEffectType;
    private IEffectView mView;

    public EffectView(Context context) {
        super(context);
        this.mContext = context;
    }

    @Override // com.samsung.android.visualeffect.IEffectView
    public void handleCustomEvent(int cmd, HashMap<?, ?> params) {
        if (this.mView == null) {
            Log.d("EffectView", "handleCustomEvent : mView is null");
        } else if (params == null) {
            this.mView.handleCustomEvent(cmd, new HashMap<>());
        } else {
            this.mView.handleCustomEvent(cmd, params);
        }
    }

    @Override // com.samsung.android.visualeffect.IEffectView
    public void handleTouchEvent(MotionEvent motionEvent, View view) {
        if (mView == null) {
            Log.d("EffectView", "handleTouchEvent : mView is null");
        } else {
            mView.handleTouchEvent(motionEvent, view);
        }
    }

    @Override // com.samsung.android.visualeffect.IEffectView
    public void clearScreen() {
        if (this.mView == null) {
            Log.d("EffectView", "clearScreen : mView is null");
        } else {
            this.mView.clearScreen();
        }
    }

    public int getEffect() {
        if (this.mView != null) {
            return this.mEffectType;
        }
        Log.d("EffectView", "getEffect : Current mView is " + ( null));
        return -1;
    }

    public void setEffect(int effect) {
        if (this.mView != null) {
            Log.d("EffectView", "setEffect : Current mView is " + this.mView.getClass());
            removeAllViews();
        }
        this.mView = InnerViewManager.getInstance(this.mContext, effect);
        addView((View) this.mView);
        this.mEffectType = effect;
    }

    public void setEffect(String effect) {
        if (this.mView != null) {
            Log.d("EffectView", "setEffect : Current mView is " + this.mView.getClass());
            removeAllViews();
        }
        this.mView = InnerViewManager.getInstance(this.mContext, 4);
        addView((View) this.mView);
        this.mEffectType = 4;
    }

    @Override // com.samsung.android.visualeffect.IEffectListener
    public void setListener(IEffectListener iEffectListener) {
        if (this.mView == null) {
            Log.d("EffectView", "setListener : mView is null");
        } else {
            this.mView.setListener(iEffectListener);
        }
    }
}
