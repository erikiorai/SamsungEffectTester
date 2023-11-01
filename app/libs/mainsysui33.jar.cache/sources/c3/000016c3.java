package com.android.systemui.dreams;

import android.content.ComponentName;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowManager;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.ViewModelStore;
import com.android.internal.logging.UiEventLogger;
import com.android.internal.policy.PhoneWindow;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.systemui.dreams.DreamOverlayService;
import com.android.systemui.dreams.complication.Complication;
import com.android.systemui.dreams.dagger.DreamOverlayComponent;
import com.android.systemui.dreams.touch.DreamOverlayTouchMonitor;
import com.android.systemui.util.concurrency.DelayableExecutor;

/* loaded from: mainsysui33.jar:com/android/systemui/dreams/DreamOverlayService.class */
public class DreamOverlayService extends android.service.dreams.DreamOverlayService {
    public static final boolean DEBUG = Log.isLoggable("DreamOverlayService", 3);
    public final Context mContext;
    public final DreamOverlayCallbackController mDreamOverlayCallbackController;
    public final DreamOverlayComponent mDreamOverlayComponent;
    public DreamOverlayContainerViewController mDreamOverlayContainerViewController;
    public DreamOverlayTouchMonitor mDreamOverlayTouchMonitor;
    public final DelayableExecutor mExecutor;
    public final KeyguardUpdateMonitorCallback mKeyguardCallback;
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public final LifecycleRegistry mLifecycleRegistry;
    public final ComponentName mLowLightDreamComponent;
    public final DreamOverlayStateController mStateController;
    public final UiEventLogger mUiEventLogger;
    public Window mWindow;
    public final WindowManager mWindowManager;
    public boolean mStarted = false;
    public boolean mDestroyed = false;

    /* renamed from: com.android.systemui.dreams.DreamOverlayService$1 */
    /* loaded from: mainsysui33.jar:com/android/systemui/dreams/DreamOverlayService$1.class */
    public class AnonymousClass1 extends KeyguardUpdateMonitorCallback {
        /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.dreams.DreamOverlayService$1$$ExternalSyntheticLambda0.run():void] */
        /* renamed from: $r8$lambda$T-tovK9zzUbIxZ7Zes18jcQViZQ */
        public static /* synthetic */ void m2557$r8$lambda$TtovK9zzUbIxZ7Zes18jcQViZQ(AnonymousClass1 anonymousClass1, boolean z) {
            anonymousClass1.lambda$onShadeExpandedChanged$0(z);
        }

        public AnonymousClass1() {
            DreamOverlayService.this = r4;
        }

        public /* synthetic */ void lambda$onShadeExpandedChanged$0(boolean z) {
            Lifecycle.State currentStateLocked = DreamOverlayService.this.getCurrentStateLocked();
            Lifecycle.State state = Lifecycle.State.RESUMED;
            if (currentStateLocked == state || DreamOverlayService.this.getCurrentStateLocked() == Lifecycle.State.STARTED) {
                DreamOverlayService dreamOverlayService = DreamOverlayService.this;
                if (z) {
                    state = Lifecycle.State.STARTED;
                }
                dreamOverlayService.setCurrentStateLocked(state);
            }
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public void onShadeExpandedChanged(final boolean z) {
            DreamOverlayService.this.mExecutor.execute(new Runnable() { // from class: com.android.systemui.dreams.DreamOverlayService$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    DreamOverlayService.AnonymousClass1.m2557$r8$lambda$TtovK9zzUbIxZ7Zes18jcQViZQ(DreamOverlayService.AnonymousClass1.this, z);
                }
            });
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/dreams/DreamOverlayService$DreamOverlayEvent.class */
    public enum DreamOverlayEvent implements UiEventLogger.UiEventEnum {
        DREAM_OVERLAY_ENTER_START(989),
        DREAM_OVERLAY_COMPLETE_START(990);
        
        private final int mId;

        DreamOverlayEvent(int i) {
            this.mId = i;
        }

        public int getId() {
            return this.mId;
        }
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.dreams.DreamOverlayService$$ExternalSyntheticLambda4.run():void] */
    /* renamed from: $r8$lambda$3YCQ7TTvUJfx2JfDj1T-khUxiOg */
    public static /* synthetic */ void m2551$r8$lambda$3YCQ7TTvUJfx2JfDj1TkhUxiOg(DreamOverlayService dreamOverlayService) {
        dreamOverlayService.lambda$new$1();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.dreams.DreamOverlayService$$ExternalSyntheticLambda1.run():void] */
    public static /* synthetic */ void $r8$lambda$AQHFFUxVMFzh7U0VyyLeRJ9sZGg(DreamOverlayService dreamOverlayService, WindowManager.LayoutParams layoutParams) {
        dreamOverlayService.lambda$onStartDream$3(layoutParams);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.dreams.DreamOverlayService$$ExternalSyntheticLambda0.run():void] */
    /* renamed from: $r8$lambda$LRGxrKnU5aziQgTuU8AAV-fUHf0 */
    public static /* synthetic */ void m2552$r8$lambda$LRGxrKnU5aziQgTuU8AAVfUHf0(DreamOverlayService dreamOverlayService, Runnable runnable) {
        dreamOverlayService.lambda$onWakeUp$4(runnable);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.dreams.DreamOverlayService$$ExternalSyntheticLambda2.run():void] */
    public static /* synthetic */ void $r8$lambda$NUwlRYyNd3FBJm9tdasNbfOPHFc(DreamOverlayService dreamOverlayService) {
        dreamOverlayService.lambda$onDestroy$2();
    }

    public DreamOverlayService(Context context, DelayableExecutor delayableExecutor, WindowManager windowManager, DreamOverlayComponent.Factory factory, DreamOverlayStateController dreamOverlayStateController, KeyguardUpdateMonitor keyguardUpdateMonitor, UiEventLogger uiEventLogger, ComponentName componentName, DreamOverlayCallbackController dreamOverlayCallbackController) {
        AnonymousClass1 anonymousClass1 = new AnonymousClass1();
        this.mKeyguardCallback = anonymousClass1;
        this.mContext = context;
        this.mExecutor = delayableExecutor;
        this.mWindowManager = windowManager;
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        this.mLowLightDreamComponent = componentName;
        keyguardUpdateMonitor.registerCallback(anonymousClass1);
        this.mStateController = dreamOverlayStateController;
        this.mUiEventLogger = uiEventLogger;
        this.mDreamOverlayCallbackController = dreamOverlayCallbackController;
        DreamOverlayComponent create = factory.create(new ViewModelStore(), new Complication.Host() { // from class: com.android.systemui.dreams.DreamOverlayService$$ExternalSyntheticLambda3
        });
        this.mDreamOverlayComponent = create;
        this.mLifecycleRegistry = create.getLifecycleRegistry();
        delayableExecutor.execute(new Runnable() { // from class: com.android.systemui.dreams.DreamOverlayService$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                DreamOverlayService.m2551$r8$lambda$3YCQ7TTvUJfx2JfDj1TkhUxiOg(DreamOverlayService.this);
            }
        });
    }

    public /* synthetic */ void lambda$new$1() {
        setCurrentStateLocked(Lifecycle.State.CREATED);
    }

    public /* synthetic */ void lambda$onDestroy$2() {
        setCurrentStateLocked(Lifecycle.State.DESTROYED);
        resetCurrentDreamOverlayLocked();
        this.mDestroyed = true;
    }

    public /* synthetic */ void lambda$onStartDream$3(WindowManager.LayoutParams layoutParams) {
        setCurrentStateLocked(Lifecycle.State.STARTED);
        this.mUiEventLogger.log(DreamOverlayEvent.DREAM_OVERLAY_ENTER_START);
        if (this.mDestroyed) {
            return;
        }
        if (this.mStarted) {
            resetCurrentDreamOverlayLocked();
        }
        this.mDreamOverlayContainerViewController = this.mDreamOverlayComponent.getDreamOverlayContainerViewController();
        DreamOverlayTouchMonitor dreamOverlayTouchMonitor = this.mDreamOverlayComponent.getDreamOverlayTouchMonitor();
        this.mDreamOverlayTouchMonitor = dreamOverlayTouchMonitor;
        dreamOverlayTouchMonitor.init();
        this.mStateController.setShouldShowComplications(shouldShowComplications());
        addOverlayWindowLocked(layoutParams);
        setCurrentStateLocked(Lifecycle.State.RESUMED);
        this.mStateController.setOverlayActive(true);
        ComponentName dreamComponent = getDreamComponent();
        this.mStateController.setLowLightActive(dreamComponent != null && dreamComponent.equals(this.mLowLightDreamComponent));
        this.mUiEventLogger.log(DreamOverlayEvent.DREAM_OVERLAY_COMPLETE_START);
        this.mDreamOverlayCallbackController.onStartDream();
        this.mStarted = true;
    }

    public /* synthetic */ void lambda$onWakeUp$4(Runnable runnable) {
        if (this.mDreamOverlayContainerViewController != null) {
            this.mDreamOverlayCallbackController.onWakeUp();
            this.mDreamOverlayContainerViewController.wakeUp(runnable, this.mExecutor);
        }
    }

    public final void addOverlayWindowLocked(WindowManager.LayoutParams layoutParams) {
        PhoneWindow phoneWindow = new PhoneWindow(this.mContext);
        this.mWindow = phoneWindow;
        phoneWindow.setAttributes(layoutParams);
        this.mWindow.setWindowManager(null, layoutParams.token, "DreamOverlay", true);
        this.mWindow.setBackgroundDrawable(new ColorDrawable(0));
        this.mWindow.clearFlags(Integer.MIN_VALUE);
        this.mWindow.addFlags(8);
        this.mWindow.requestFeature(1);
        this.mWindow.getDecorView().getWindowInsetsController().hide(WindowInsets.Type.systemBars());
        this.mWindow.setDecorFitsSystemWindows(false);
        if (DEBUG) {
            Log.d("DreamOverlayService", "adding overlay window to dream");
        }
        this.mDreamOverlayContainerViewController.init();
        removeContainerViewFromParentLocked();
        this.mWindow.setContentView(this.mDreamOverlayContainerViewController.getContainerView());
        this.mWindowManager.addView(this.mWindow.getDecorView(), this.mWindow.getAttributes());
    }

    public final Lifecycle.State getCurrentStateLocked() {
        return this.mLifecycleRegistry.getCurrentState();
    }

    public void onDestroy() {
        this.mKeyguardUpdateMonitor.removeCallback(this.mKeyguardCallback);
        this.mExecutor.execute(new Runnable() { // from class: com.android.systemui.dreams.DreamOverlayService$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                DreamOverlayService.$r8$lambda$NUwlRYyNd3FBJm9tdasNbfOPHFc(DreamOverlayService.this);
            }
        });
        super.onDestroy();
    }

    public void onStartDream(final WindowManager.LayoutParams layoutParams) {
        this.mExecutor.execute(new Runnable() { // from class: com.android.systemui.dreams.DreamOverlayService$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                DreamOverlayService.$r8$lambda$AQHFFUxVMFzh7U0VyyLeRJ9sZGg(DreamOverlayService.this, layoutParams);
            }
        });
    }

    public void onWakeUp(final Runnable runnable) {
        this.mExecutor.execute(new Runnable() { // from class: com.android.systemui.dreams.DreamOverlayService$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                DreamOverlayService.m2552$r8$lambda$LRGxrKnU5aziQgTuU8AAVfUHf0(DreamOverlayService.this, runnable);
            }
        });
    }

    public final void removeContainerViewFromParentLocked() {
        ViewGroup viewGroup;
        View containerView = this.mDreamOverlayContainerViewController.getContainerView();
        if (containerView == null || (viewGroup = (ViewGroup) containerView.getParent()) == null) {
            return;
        }
        Log.w("DreamOverlayService", "Removing dream overlay container view parent!");
        viewGroup.removeView(containerView);
    }

    public final void resetCurrentDreamOverlayLocked() {
        Window window;
        if (this.mStarted && (window = this.mWindow) != null) {
            try {
                this.mWindowManager.removeView(window.getDecorView());
            } catch (IllegalArgumentException e) {
                Log.e("DreamOverlayService", "Error removing decor view when resetting overlay", e);
            }
        }
        this.mStateController.setOverlayActive(false);
        this.mStateController.setLowLightActive(false);
        this.mStateController.setEntryAnimationsFinished(false);
        this.mDreamOverlayContainerViewController = null;
        this.mDreamOverlayTouchMonitor = null;
        this.mStarted = false;
    }

    public final void setCurrentStateLocked(Lifecycle.State state) {
        this.mLifecycleRegistry.setCurrentState(state);
    }
}