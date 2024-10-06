package com.samsung.android.visualeffect;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import java.util.HashMap;

/* loaded from: classes.dex */
public class EffectView extends FrameLayout implements IEffectView {
    private final String TAG;
    private Context mContext;
    private int mEffectType;
    private IEffectView mView;

    public EffectView(Context context) {
        super(context);
        this.TAG = "EffectView";
        this.mContext = context;
    }

    public EffectView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.TAG = "EffectView";
        this.mContext = context;
    }

    public EffectView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.TAG = "EffectView";
        this.mContext = context;
    }

    public EffectView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.TAG = "EffectView";
        this.mContext = context;
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

    public void setEffect(int effect) {
        if (this.mView != null) {
            Log.d("EffectView", "setEffect : Current mView is " + this.mView.getClass());
            removeAllViews();
        }
        this.mView = InnerViewManager.getInstance(this.mContext, effect);
        addView((View) this.mView);
        this.mEffectType = effect;
    }

    public int getEffect() {
        if (this.mView == null) {
            Log.d("EffectView", "getEffect : Current mView is " + ((Object) null));
            return -1;
        }
        return this.mEffectType;
    }

    @Override // com.samsung.android.visualeffect.IEffectView
    public void init(EffectDataObj data) {
        if (this.mView == null) {
            Log.d("EffectView", "setInitValues : mView is null");
        } else {
            this.mView.init(data);
        }
    }

    @Override // com.samsung.android.visualeffect.IEffectView
    public void reInit(EffectDataObj data) {
        if (this.mView == null) {
            Log.d("EffectView", "reInitAndValues : mView is null");
        } else if (data != null) {
            this.mView.reInit(data);
        } else {
            this.mView.reInit(new EffectDataObj());
        }
    }

    @Override // com.samsung.android.visualeffect.IEffectView
    public void handleTouchEvent(MotionEvent event, View view) {
        if (this.mView == null) {
            Log.d("EffectView", "handleTouchEvent : mView is null");
        } else {
            this.mView.handleTouchEvent(event, view);
        }
    }

    public boolean handleHoverEvent(MotionEvent event) {
        if (this.mView == null) {
            Log.d("EffectView", "handleHoverEvent : mView is null");
        } else {
            return mView.handleHoverEvent(event);
        }
        return false;
    }

    @Override
    public void drawPause() {
        if (mView != null)
            mView.drawPause();
    }

    @Override
    public void drawResume() {
        if (mView != null)
            mView.drawResume();
    }

    public void onCommand(String cmd, HashMap<?, ?> params) {
        if (cmd.contentEquals(EffectCmdType.CLEAR)) {
            this.mView.clearScreen();
        } else {
            handleCustomEvent(3, params);
        }
    }

    @Override // com.samsung.android.visualeffect.IEffectView
    public void handleCustomEvent(int cmd, HashMap<?, ?> params) {
        if (this.mView == null) {
            Log.d("EffectView", "handleCustomEvent : mView is null");
        } else if (params != null) {
            this.mView.handleCustomEvent(cmd, params);
        } else {
            this.mView.handleCustomEvent(cmd, new HashMap<>());
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

    @Override // com.samsung.android.visualeffect.IEffectView
    public void setListener(IEffectListener listener) {
        if (this.mView == null) {
            Log.d("EffectView", "setListener : mView is null");
        } else {
            this.mView.setListener(listener);
        }
    }

    @Override // com.samsung.android.visualeffect.IEffectView
    public void removeListener() {
        if (this.mView == null) {
            Log.d("EffectView", "removeListener : mView is null");
        } else {
            this.mView.removeListener();
        }
    }

    public void removeEffect() {
        removeAllViews();
        this.mView = null;
    }

}