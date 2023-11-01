package com.android.keyguard;

import android.database.ContentObserver;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import com.android.systemui.Dumpable;
import com.android.systemui.R$dimen;
import com.android.systemui.R$id;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.keyguard.KeyguardUnlockAnimationController;
import com.android.systemui.plugins.ClockAnimations;
import com.android.systemui.plugins.ClockController;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.shared.clocks.ClockRegistry;
import com.android.systemui.statusbar.lockscreen.LockscreenSmartspaceController;
import com.android.systemui.statusbar.notification.AnimatableProperty;
import com.android.systemui.statusbar.notification.PropertyAnimator;
import com.android.systemui.statusbar.notification.stack.AnimationProperties;
import com.android.systemui.statusbar.phone.NotificationIconAreaController;
import com.android.systemui.util.ViewController;
import com.android.systemui.util.settings.SecureSettings;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.concurrent.Executor;

/* loaded from: mainsysui33.jar:com/android/keyguard/KeyguardClockSwitchController.class */
public class KeyguardClockSwitchController extends ViewController<KeyguardClockSwitch> implements Dumpable {
    public boolean mCanShowDoubleLineClock;
    public final ClockRegistry.ClockChangeListener mClockChangedListener;
    public final ClockEventController mClockEventController;
    public final ClockRegistry mClockRegistry;
    public int mCurrentClockSize;
    public final ContentObserver mDoubleLineClockObserver;
    public final DumpManager mDumpManager;
    public int mKeyguardLargeClockTopMargin;
    public final KeyguardSliceViewController mKeyguardSliceViewController;
    public int mKeyguardSmallClockTopMargin;
    public final KeyguardUnlockAnimationController mKeyguardUnlockAnimationController;
    public final KeyguardUnlockAnimationController.KeyguardUnlockAnimationListener mKeyguardUnlockAnimationListener;
    public FrameLayout mLargeClockFrame;
    public final NotificationIconAreaController mNotificationIconAreaController;
    public boolean mOnlyClock;
    public final SecureSettings mSecureSettings;
    public FrameLayout mSmallClockFrame;
    public final LockscreenSmartspaceController mSmartspaceController;
    public View mSmartspaceView;
    public ViewGroup mStatusArea;
    public final StatusBarStateController mStatusBarStateController;
    public final Executor mUiExecutor;

    /* JADX DEBUG: Method not inlined, still used in: [com.android.keyguard.KeyguardClockSwitchController$$ExternalSyntheticLambda1.run():void] */
    public static /* synthetic */ void $r8$lambda$9pd8dyQM9IARhXuJzXidUcAA8u0(KeyguardClockSwitchController keyguardClockSwitchController) {
        keyguardClockSwitchController.lambda$updateDoubleLineClock$1();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.keyguard.KeyguardClockSwitchController$$ExternalSyntheticLambda0.onClockChanged():void] */
    public static /* synthetic */ void $r8$lambda$hlnQuP4NM6tEcQZRX_07S_k_7K8(KeyguardClockSwitchController keyguardClockSwitchController) {
        keyguardClockSwitchController.lambda$new$0();
    }

    public KeyguardClockSwitchController(KeyguardClockSwitch keyguardClockSwitch, StatusBarStateController statusBarStateController, ClockRegistry clockRegistry, KeyguardSliceViewController keyguardSliceViewController, NotificationIconAreaController notificationIconAreaController, LockscreenSmartspaceController lockscreenSmartspaceController, KeyguardUnlockAnimationController keyguardUnlockAnimationController, SecureSettings secureSettings, Executor executor, DumpManager dumpManager, ClockEventController clockEventController) {
        super(keyguardClockSwitch);
        this.mCurrentClockSize = 1;
        this.mKeyguardSmallClockTopMargin = 0;
        this.mKeyguardLargeClockTopMargin = 0;
        this.mOnlyClock = false;
        this.mCanShowDoubleLineClock = true;
        this.mDoubleLineClockObserver = new ContentObserver(null) { // from class: com.android.keyguard.KeyguardClockSwitchController.1
            {
                KeyguardClockSwitchController.this = this;
            }

            @Override // android.database.ContentObserver
            public void onChange(boolean z) {
                KeyguardClockSwitchController.this.updateDoubleLineClock();
            }
        };
        this.mKeyguardUnlockAnimationListener = new KeyguardUnlockAnimationController.KeyguardUnlockAnimationListener() { // from class: com.android.keyguard.KeyguardClockSwitchController.2
            {
                KeyguardClockSwitchController.this = this;
            }

            @Override // com.android.systemui.keyguard.KeyguardUnlockAnimationController.KeyguardUnlockAnimationListener
            public void onUnlockAnimationFinished() {
                KeyguardClockSwitchController.this.setClipChildrenForUnlock(true);
            }
        };
        this.mStatusBarStateController = statusBarStateController;
        this.mClockRegistry = clockRegistry;
        this.mKeyguardSliceViewController = keyguardSliceViewController;
        this.mNotificationIconAreaController = notificationIconAreaController;
        this.mSmartspaceController = lockscreenSmartspaceController;
        this.mSecureSettings = secureSettings;
        this.mUiExecutor = executor;
        this.mKeyguardUnlockAnimationController = keyguardUnlockAnimationController;
        this.mDumpManager = dumpManager;
        this.mClockEventController = clockEventController;
        this.mClockChangedListener = new ClockRegistry.ClockChangeListener() { // from class: com.android.keyguard.KeyguardClockSwitchController$$ExternalSyntheticLambda0
            public final void onClockChanged() {
                KeyguardClockSwitchController.$r8$lambda$hlnQuP4NM6tEcQZRX_07S_k_7K8(KeyguardClockSwitchController.this);
            }
        };
    }

    public /* synthetic */ void lambda$new$0() {
        setClock(this.mClockRegistry.createCurrentClock());
    }

    public /* synthetic */ void lambda$updateDoubleLineClock$1() {
        displayClock(1, true);
    }

    public final void addSmartspaceView(int i) {
        this.mSmartspaceView = this.mSmartspaceController.buildAndConnectView((ViewGroup) ((ViewController) this).mView);
        this.mStatusArea.addView(this.mSmartspaceView, i, new LinearLayout.LayoutParams(-1, -2));
        this.mSmartspaceView.setPaddingRelative(getContext().getResources().getDimensionPixelSize(R$dimen.below_clock_padding_start), 0, getContext().getResources().getDimensionPixelSize(R$dimen.below_clock_padding_end), 0);
        this.mKeyguardUnlockAnimationController.setLockscreenSmartspace(this.mSmartspaceView);
    }

    public void animateFoldToAod(float f) {
        ClockController clock = getClock();
        if (clock != null) {
            clock.getAnimations().fold(f);
        }
    }

    public void displayClock(int i, boolean z) {
        if (this.mCanShowDoubleLineClock || i != 0) {
            this.mCurrentClockSize = i;
            ClockController clock = getClock();
            boolean switchToClock = ((KeyguardClockSwitch) ((ViewController) this).mView).switchToClock(i, z);
            if (clock != null && z && switchToClock && i == 0) {
                clock.getAnimations().enter();
            }
        }
    }

    @Override // com.android.systemui.Dumpable
    public void dump(PrintWriter printWriter, String[] strArr) {
        StringBuilder sb = new StringBuilder();
        sb.append("currentClockSizeLarge=");
        sb.append(this.mCurrentClockSize == 0);
        printWriter.println(sb.toString());
        printWriter.println("mCanShowDoubleLineClock=" + this.mCanShowDoubleLineClock);
        ((KeyguardClockSwitch) ((ViewController) this).mView).dump(printWriter, strArr);
        ClockController clock = getClock();
        if (clock != null) {
            clock.dump(printWriter);
        }
    }

    public final ClockController getClock() {
        return this.mClockEventController.getClock();
    }

    public ClockAnimations getClockAnimations() {
        return getClock().getAnimations();
    }

    public int getClockBottom(int i) {
        ClockController clock = getClock();
        if (clock == null) {
            return 0;
        }
        if (this.mLargeClockFrame.getVisibility() == 0) {
            return (this.mLargeClockFrame.getHeight() / 2) + (clock.getLargeClock().getView().getHeight() / 2) + (this.mKeyguardLargeClockTopMargin / (-2));
        }
        return clock.getSmallClock().getView().getHeight() + i + this.mKeyguardSmallClockTopMargin;
    }

    public int getClockHeight() {
        ClockController clock = getClock();
        if (clock == null) {
            return 0;
        }
        return this.mLargeClockFrame.getVisibility() == 0 ? clock.getLargeClock().getView().getHeight() : clock.getSmallClock().getView().getHeight();
    }

    public final int getCurrentLayoutDirection() {
        return TextUtils.getLayoutDirectionFromLocale(Locale.getDefault());
    }

    public int getNotificationIconAreaHeight() {
        return this.mNotificationIconAreaController.getHeight();
    }

    public boolean isClockTopAligned() {
        return this.mLargeClockFrame.getVisibility() != 0;
    }

    public void onDensityOrFontScaleChanged() {
        ((KeyguardClockSwitch) ((ViewController) this).mView).onDensityOrFontScaleChanged();
        this.mKeyguardSmallClockTopMargin = ((KeyguardClockSwitch) ((ViewController) this).mView).getResources().getDimensionPixelSize(R$dimen.keyguard_clock_top_margin);
        this.mKeyguardLargeClockTopMargin = ((KeyguardClockSwitch) ((ViewController) this).mView).getResources().getDimensionPixelSize(R$dimen.keyguard_large_clock_top_margin);
        ((KeyguardClockSwitch) ((ViewController) this).mView).lambda$onLayout$0();
    }

    public void onInit() {
        this.mKeyguardSliceViewController.init();
        this.mSmallClockFrame = (FrameLayout) ((KeyguardClockSwitch) ((ViewController) this).mView).findViewById(R$id.lockscreen_clock_view);
        this.mLargeClockFrame = (FrameLayout) ((KeyguardClockSwitch) ((ViewController) this).mView).findViewById(R$id.lockscreen_clock_view_large);
        this.mDumpManager.unregisterDumpable(getClass().toString());
        this.mDumpManager.registerDumpable(getClass().toString(), this);
    }

    public void onLocaleListChanged() {
        int indexOfChild;
        if (!this.mSmartspaceController.isEnabled() || (indexOfChild = this.mStatusArea.indexOfChild(this.mSmartspaceView)) < 0) {
            return;
        }
        this.mStatusArea.removeView(this.mSmartspaceView);
        addSmartspaceView(indexOfChild);
    }

    public void onViewAttached() {
        this.mClockRegistry.registerClockChangeListener(this.mClockChangedListener);
        setClock(this.mClockRegistry.createCurrentClock());
        this.mClockEventController.registerListeners(((ViewController) this).mView);
        this.mKeyguardSmallClockTopMargin = ((KeyguardClockSwitch) ((ViewController) this).mView).getResources().getDimensionPixelSize(R$dimen.keyguard_clock_top_margin);
        this.mKeyguardLargeClockTopMargin = ((KeyguardClockSwitch) ((ViewController) this).mView).getResources().getDimensionPixelSize(R$dimen.keyguard_large_clock_top_margin);
        if (this.mOnlyClock) {
            ((KeyguardClockSwitch) ((ViewController) this).mView).findViewById(R$id.keyguard_slice_view).setVisibility(8);
            ((KeyguardClockSwitch) ((ViewController) this).mView).findViewById(R$id.left_aligned_notification_icon_container).setVisibility(8);
            return;
        }
        updateAodIcons();
        this.mStatusArea = (ViewGroup) ((KeyguardClockSwitch) ((ViewController) this).mView).findViewById(R$id.keyguard_status_area);
        if (this.mSmartspaceController.isEnabled()) {
            View findViewById = ((KeyguardClockSwitch) ((ViewController) this).mView).findViewById(R$id.keyguard_slice_view);
            int indexOfChild = this.mStatusArea.indexOfChild(findViewById);
            findViewById.setVisibility(8);
            addSmartspaceView(indexOfChild);
        }
        this.mSecureSettings.registerContentObserverForUser(Settings.Secure.getUriFor("lockscreen_use_double_line_clock"), false, this.mDoubleLineClockObserver, -1);
        updateDoubleLineClock();
        this.mKeyguardUnlockAnimationController.addKeyguardUnlockAnimationListener(this.mKeyguardUnlockAnimationListener);
    }

    public void onViewDetached() {
        this.mClockRegistry.unregisterClockChangeListener(this.mClockChangedListener);
        this.mClockEventController.unregisterListeners();
        setClock(null);
        this.mSecureSettings.unregisterContentObserver(this.mDoubleLineClockObserver);
        this.mKeyguardUnlockAnimationController.removeKeyguardUnlockAnimationListener(this.mKeyguardUnlockAnimationListener);
    }

    public void refresh() {
        LockscreenSmartspaceController lockscreenSmartspaceController = this.mSmartspaceController;
        if (lockscreenSmartspaceController != null) {
            lockscreenSmartspaceController.requestSmartspaceUpdate();
        }
        ClockController clock = getClock();
        if (clock != null) {
            clock.getEvents().onTimeTick();
        }
    }

    public final void setClipChildrenForUnlock(boolean z) {
        ViewGroup viewGroup = this.mStatusArea;
        if (viewGroup != null) {
            viewGroup.setClipChildren(z);
        }
    }

    public final void setClock(ClockController clockController) {
        this.mClockEventController.setClock(clockController);
        ((KeyguardClockSwitch) ((ViewController) this).mView).setClock(clockController, this.mStatusBarStateController.getState());
    }

    public void setOnlyClock(boolean z) {
        this.mOnlyClock = z;
    }

    public final void updateAodIcons() {
        this.mNotificationIconAreaController.setupAodIcons(((KeyguardClockSwitch) ((ViewController) this).mView).findViewById(R$id.left_aligned_notification_icon_container));
    }

    public final void updateDoubleLineClock() {
        boolean z = true;
        if (this.mSecureSettings.getIntForUser("lockscreen_use_double_line_clock", 1, -2) == 0) {
            z = false;
        }
        this.mCanShowDoubleLineClock = z;
        if (z) {
            return;
        }
        this.mUiExecutor.execute(new Runnable() { // from class: com.android.keyguard.KeyguardClockSwitchController$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                KeyguardClockSwitchController.$r8$lambda$9pd8dyQM9IARhXuJzXidUcAA8u0(KeyguardClockSwitchController.this);
            }
        });
    }

    public void updatePosition(int i, float f, AnimationProperties animationProperties, boolean z) {
        int i2 = i;
        if (getCurrentLayoutDirection() == 1) {
            i2 = -i;
        }
        FrameLayout frameLayout = this.mSmallClockFrame;
        AnimatableProperty animatableProperty = AnimatableProperty.TRANSLATION_X;
        float f2 = i2;
        PropertyAnimator.setProperty(frameLayout, animatableProperty, f2, animationProperties, z);
        PropertyAnimator.setProperty(this.mLargeClockFrame, AnimatableProperty.SCALE_X, f, animationProperties, z);
        PropertyAnimator.setProperty(this.mLargeClockFrame, AnimatableProperty.SCALE_Y, f, animationProperties, z);
        ViewGroup viewGroup = this.mStatusArea;
        if (viewGroup != null) {
            PropertyAnimator.setProperty(viewGroup, animatableProperty, f2, animationProperties, z);
        }
    }
}