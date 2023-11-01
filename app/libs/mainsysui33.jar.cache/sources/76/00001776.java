package com.android.systemui.dreams.touch;

import android.util.Log;
import android.view.InputEvent;
import android.view.MotionEvent;
import com.android.systemui.dreams.DreamOverlayStateController;
import com.android.systemui.dreams.complication.Complication;
import com.android.systemui.dreams.touch.DreamTouchHandler;
import com.android.systemui.shared.system.InputChannelCompat;
import com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager;
import com.android.systemui.touch.TouchInsetManager;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.ArrayDeque;
import java.util.concurrent.ExecutionException;

/* loaded from: mainsysui33.jar:com/android/systemui/dreams/touch/HideComplicationTouchHandler.class */
public class HideComplicationTouchHandler implements DreamTouchHandler {
    public static final boolean DEBUG = Log.isLoggable("HideComplicationHandler", 3);
    public final DelayableExecutor mExecutor;
    public final int mFadeOutDelay;
    public Runnable mHiddenCallback;
    public final DreamOverlayStateController mOverlayStateController;
    public final int mRestoreTimeout;
    public final StatusBarKeyguardViewManager mStatusBarKeyguardViewManager;
    public final TouchInsetManager mTouchInsetManager;
    public final Complication.VisibilityController mVisibilityController;
    public boolean mHidden = false;
    public final ArrayDeque<Runnable> mCancelCallbacks = new ArrayDeque<>();
    public final Runnable mRestoreComplications = new Runnable() { // from class: com.android.systemui.dreams.touch.HideComplicationTouchHandler.1
        {
            HideComplicationTouchHandler.this = this;
        }

        @Override // java.lang.Runnable
        public void run() {
            HideComplicationTouchHandler.this.mVisibilityController.setVisibility(0);
            HideComplicationTouchHandler.this.mHidden = false;
        }
    };
    public final Runnable mHideComplications = new Runnable() { // from class: com.android.systemui.dreams.touch.HideComplicationTouchHandler.2
        {
            HideComplicationTouchHandler.this = this;
        }

        @Override // java.lang.Runnable
        public void run() {
            if (HideComplicationTouchHandler.this.mOverlayStateController.areExitAnimationsRunning()) {
                return;
            }
            HideComplicationTouchHandler.this.mVisibilityController.setVisibility(4);
            HideComplicationTouchHandler.this.mHidden = true;
            if (HideComplicationTouchHandler.this.mHiddenCallback != null) {
                HideComplicationTouchHandler.this.mHiddenCallback.run();
                HideComplicationTouchHandler.this.mHiddenCallback = null;
            }
        }
    };

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.dreams.touch.HideComplicationTouchHandler$$ExternalSyntheticLambda2.run():void] */
    public static /* synthetic */ void $r8$lambda$Ud1vATgKzWI6EriUIwnTenSgkR0(HideComplicationTouchHandler hideComplicationTouchHandler) {
        hideComplicationTouchHandler.lambda$onSessionStart$1();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.dreams.touch.HideComplicationTouchHandler$$ExternalSyntheticLambda1.run():void] */
    public static /* synthetic */ void $r8$lambda$V7crX96s8ofLMLRqHwiSEXDqw6Q(HideComplicationTouchHandler hideComplicationTouchHandler, ListenableFuture listenableFuture, DreamTouchHandler.TouchSession touchSession) {
        hideComplicationTouchHandler.lambda$onSessionStart$0(listenableFuture, touchSession);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.dreams.touch.HideComplicationTouchHandler$$ExternalSyntheticLambda3.run():void] */
    public static /* synthetic */ void $r8$lambda$uyz4VWzARP8bo0BPaEW4PB2x9E8(HideComplicationTouchHandler hideComplicationTouchHandler, Runnable runnable) {
        hideComplicationTouchHandler.lambda$runAfterHidden$3(runnable);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.dreams.touch.HideComplicationTouchHandler$$ExternalSyntheticLambda0.onInputEvent(android.view.InputEvent):void] */
    /* renamed from: $r8$lambda$vCa6o3TKiEmG-jfAiu-PLbIT1MA */
    public static /* synthetic */ void m2652$r8$lambda$vCa6o3TKiEmGjfAiuPLbIT1MA(HideComplicationTouchHandler hideComplicationTouchHandler, DreamTouchHandler.TouchSession touchSession, InputEvent inputEvent) {
        hideComplicationTouchHandler.lambda$onSessionStart$2(touchSession, inputEvent);
    }

    public HideComplicationTouchHandler(Complication.VisibilityController visibilityController, int i, int i2, TouchInsetManager touchInsetManager, StatusBarKeyguardViewManager statusBarKeyguardViewManager, DelayableExecutor delayableExecutor, DreamOverlayStateController dreamOverlayStateController) {
        this.mVisibilityController = visibilityController;
        this.mRestoreTimeout = i;
        this.mFadeOutDelay = i2;
        this.mStatusBarKeyguardViewManager = statusBarKeyguardViewManager;
        this.mTouchInsetManager = touchInsetManager;
        this.mExecutor = delayableExecutor;
        this.mOverlayStateController = dreamOverlayStateController;
    }

    public /* synthetic */ void lambda$onSessionStart$0(ListenableFuture listenableFuture, DreamTouchHandler.TouchSession touchSession) {
        try {
            if (((Boolean) listenableFuture.get()).booleanValue()) {
                touchSession.pop();
                return;
            }
            while (!this.mCancelCallbacks.isEmpty()) {
                this.mCancelCallbacks.pop().run();
            }
            this.mCancelCallbacks.add(this.mExecutor.executeDelayed(this.mHideComplications, this.mFadeOutDelay));
        } catch (InterruptedException | ExecutionException e) {
            Log.e("HideComplicationHandler", "could not check TouchInsetManager:" + e);
        }
    }

    public /* synthetic */ void lambda$onSessionStart$1() {
        this.mCancelCallbacks.add(this.mExecutor.executeDelayed(this.mRestoreComplications, this.mRestoreTimeout));
    }

    public /* synthetic */ void lambda$onSessionStart$2(final DreamTouchHandler.TouchSession touchSession, InputEvent inputEvent) {
        if (inputEvent instanceof MotionEvent) {
            MotionEvent motionEvent = (MotionEvent) inputEvent;
            if (motionEvent.getAction() == 0) {
                if (DEBUG) {
                    Log.d("HideComplicationHandler", "ACTION_DOWN received");
                }
                final ListenableFuture checkWithinTouchRegion = this.mTouchInsetManager.checkWithinTouchRegion(Math.round(motionEvent.getX()), Math.round(motionEvent.getY()));
                checkWithinTouchRegion.addListener(new Runnable() { // from class: com.android.systemui.dreams.touch.HideComplicationTouchHandler$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        HideComplicationTouchHandler.$r8$lambda$V7crX96s8ofLMLRqHwiSEXDqw6Q(HideComplicationTouchHandler.this, checkWithinTouchRegion, touchSession);
                    }
                }, this.mExecutor);
            } else if (motionEvent.getAction() == 3 || motionEvent.getAction() == 1) {
                touchSession.pop();
                runAfterHidden(new Runnable() { // from class: com.android.systemui.dreams.touch.HideComplicationTouchHandler$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        HideComplicationTouchHandler.$r8$lambda$Ud1vATgKzWI6EriUIwnTenSgkR0(HideComplicationTouchHandler.this);
                    }
                });
            }
        }
    }

    public /* synthetic */ void lambda$runAfterHidden$3(Runnable runnable) {
        if (this.mHidden) {
            runnable.run();
        } else {
            this.mHiddenCallback = runnable;
        }
    }

    @Override // com.android.systemui.dreams.touch.DreamTouchHandler
    public void onSessionStart(final DreamTouchHandler.TouchSession touchSession) {
        boolean z = DEBUG;
        if (z) {
            Log.d("HideComplicationHandler", "onSessionStart");
        }
        boolean isBouncerShowing = this.mStatusBarKeyguardViewManager.isBouncerShowing();
        if (touchSession.getActiveSessionCount() <= 1 && !isBouncerShowing && !this.mOverlayStateController.areExitAnimationsRunning()) {
            touchSession.registerInputListener(new InputChannelCompat.InputEventListener() { // from class: com.android.systemui.dreams.touch.HideComplicationTouchHandler$$ExternalSyntheticLambda0
                public final void onInputEvent(InputEvent inputEvent) {
                    HideComplicationTouchHandler.m2652$r8$lambda$vCa6o3TKiEmGjfAiuPLbIT1MA(HideComplicationTouchHandler.this, touchSession, inputEvent);
                }
            });
            return;
        }
        if (z) {
            Log.d("HideComplicationHandler", "not fading. Active session count: " + touchSession.getActiveSessionCount() + ". Bouncer showing: " + isBouncerShowing);
        }
        touchSession.pop();
    }

    public final void runAfterHidden(final Runnable runnable) {
        this.mExecutor.execute(new Runnable() { // from class: com.android.systemui.dreams.touch.HideComplicationTouchHandler$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                HideComplicationTouchHandler.$r8$lambda$uyz4VWzARP8bo0BPaEW4PB2x9E8(HideComplicationTouchHandler.this, runnable);
            }
        });
    }
}