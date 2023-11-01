package com.android.keyguard;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BlendMode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.UserManager;
import android.transition.TransitionManager;
import android.util.AttributeSet;
import android.util.Log;
import android.util.MathUtils;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.view.WindowInsetsAnimation;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.SpringAnimation;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.internal.logging.UiEventLogger;
import com.android.internal.util.UserIcons;
import com.android.internal.widget.LockPatternUtils;
import com.android.keyguard.KeyguardSecurityContainer;
import com.android.keyguard.KeyguardSecurityModel;
import com.android.settingslib.Utils;
import com.android.settingslib.widget.ActionBarShadowController;
import com.android.systemui.Gefingerpoken;
import com.android.systemui.R$color;
import com.android.systemui.R$dimen;
import com.android.systemui.R$drawable;
import com.android.systemui.R$id;
import com.android.systemui.R$layout;
import com.android.systemui.R$string;
import com.android.systemui.animation.Interpolators;
import com.android.systemui.classifier.FalsingA11yDelegate;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.shared.system.SysUiStatsLog;
import com.android.systemui.statusbar.policy.BaseUserSwitcherAdapter;
import com.android.systemui.statusbar.policy.UserSwitcherController;
import com.android.systemui.user.data.source.UserRecord;
import com.android.systemui.util.settings.GlobalSettings;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;

/* loaded from: mainsysui33.jar:com/android/keyguard/KeyguardSecurityContainer.class */
public class KeyguardSecurityContainer extends ConstraintLayout {
    public int mActivePointerId;
    public AlertDialog mAlertDialog;
    public int mCurrentMode;
    public boolean mDisappearAnimRunning;
    public final GestureDetector mDoubleTapDetector;
    public FalsingA11yDelegate mFalsingA11yDelegate;
    public FalsingManager mFalsingManager;
    public GlobalSettings mGlobalSettings;
    public boolean mIsDragging;
    public float mLastTouchY;
    public final List<Gefingerpoken> mMotionEventListeners;
    public KeyguardSecurityViewFlipper mSecurityViewFlipper;
    public final SpringAnimation mSpringAnimation;
    public float mStartTouchY;
    public SwipeListener mSwipeListener;
    public boolean mSwipeUpToRetry;
    public UserSwitcherController mUserSwitcherController;
    public final VelocityTracker mVelocityTracker;
    public final ViewConfiguration mViewConfiguration;
    public ViewMode mViewMode;
    public int mWidth;
    public final WindowInsetsAnimation.Callback mWindowInsetsAnimationCallback;

    /* renamed from: com.android.keyguard.KeyguardSecurityContainer$2 */
    /* loaded from: mainsysui33.jar:com/android/keyguard/KeyguardSecurityContainer$2.class */
    public static /* synthetic */ class AnonymousClass2 {
        public static final /* synthetic */ int[] $SwitchMap$com$android$keyguard$KeyguardSecurityModel$SecurityMode;

        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:63:0x0059 -> B:79:0x0014). Please submit an issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:65:0x005d -> B:89:0x001f). Please submit an issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:67:0x0061 -> B:85:0x002a). Please submit an issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:69:0x0065 -> B:81:0x0035). Please submit an issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:71:0x0069 -> B:77:0x0040). Please submit an issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:73:0x006d -> B:87:0x004c). Please submit an issue!!! */
        static {
            int[] iArr = new int[KeyguardSecurityModel.SecurityMode.values().length];
            $SwitchMap$com$android$keyguard$KeyguardSecurityModel$SecurityMode = iArr;
            try {
                iArr[KeyguardSecurityModel.SecurityMode.Pattern.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$android$keyguard$KeyguardSecurityModel$SecurityMode[KeyguardSecurityModel.SecurityMode.PIN.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$android$keyguard$KeyguardSecurityModel$SecurityMode[KeyguardSecurityModel.SecurityMode.Password.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$android$keyguard$KeyguardSecurityModel$SecurityMode[KeyguardSecurityModel.SecurityMode.Invalid.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$android$keyguard$KeyguardSecurityModel$SecurityMode[KeyguardSecurityModel.SecurityMode.None.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$com$android$keyguard$KeyguardSecurityModel$SecurityMode[KeyguardSecurityModel.SecurityMode.SimPin.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$com$android$keyguard$KeyguardSecurityModel$SecurityMode[KeyguardSecurityModel.SecurityMode.SimPuk.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
        }
    }

    /* loaded from: mainsysui33.jar:com/android/keyguard/KeyguardSecurityContainer$BouncerUiEvent.class */
    public enum BouncerUiEvent implements UiEventLogger.UiEventEnum {
        UNKNOWN(0),
        BOUNCER_DISMISS_EXTENDED_ACCESS(413),
        BOUNCER_DISMISS_BIOMETRIC(414),
        BOUNCER_DISMISS_NONE_SECURITY(415),
        BOUNCER_DISMISS_PASSWORD(416),
        BOUNCER_DISMISS_SIM(417),
        BOUNCER_PASSWORD_SUCCESS(418),
        BOUNCER_PASSWORD_FAILURE(419);
        
        private final int mId;

        BouncerUiEvent(int i) {
            this.mId = i;
        }

        public int getId() {
            return this.mId;
        }
    }

    /* loaded from: mainsysui33.jar:com/android/keyguard/KeyguardSecurityContainer$DefaultViewMode.class */
    public static class DefaultViewMode implements ViewMode {
        public ConstraintLayout mView;
        public KeyguardSecurityViewFlipper mViewFlipper;

        @Override // com.android.keyguard.KeyguardSecurityContainer.ViewMode
        public void init(ConstraintLayout constraintLayout, GlobalSettings globalSettings, KeyguardSecurityViewFlipper keyguardSecurityViewFlipper, FalsingManager falsingManager, UserSwitcherController userSwitcherController, FalsingA11yDelegate falsingA11yDelegate) {
            this.mView = constraintLayout;
            this.mViewFlipper = keyguardSecurityViewFlipper;
            updateSecurityViewGroup();
        }

        public final void updateSecurityViewGroup() {
            ConstraintSet constraintSet = new ConstraintSet();
            constraintSet.connect(this.mViewFlipper.getId(), 6, 0, 6);
            constraintSet.connect(this.mViewFlipper.getId(), 7, 0, 7);
            constraintSet.connect(this.mViewFlipper.getId(), 4, 0, 4);
            constraintSet.connect(this.mViewFlipper.getId(), 3, 0, 3);
            constraintSet.constrainHeight(this.mViewFlipper.getId(), 0);
            constraintSet.constrainWidth(this.mViewFlipper.getId(), 0);
            constraintSet.applyTo(this.mView);
        }
    }

    /* loaded from: mainsysui33.jar:com/android/keyguard/KeyguardSecurityContainer$DoubleTapListener.class */
    public class DoubleTapListener extends GestureDetector.SimpleOnGestureListener {
        public DoubleTapListener() {
            KeyguardSecurityContainer.this = r4;
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnDoubleTapListener
        public boolean onDoubleTap(MotionEvent motionEvent) {
            return KeyguardSecurityContainer.this.handleDoubleTap(motionEvent);
        }
    }

    /* loaded from: mainsysui33.jar:com/android/keyguard/KeyguardSecurityContainer$OneHandedViewMode.class */
    public static class OneHandedViewMode extends SidedSecurityMode {
        public ConstraintLayout mView;
        public KeyguardSecurityViewFlipper mViewFlipper;

        @Override // com.android.keyguard.KeyguardSecurityContainer.ViewMode
        public void init(ConstraintLayout constraintLayout, GlobalSettings globalSettings, KeyguardSecurityViewFlipper keyguardSecurityViewFlipper, FalsingManager falsingManager, UserSwitcherController userSwitcherController, FalsingA11yDelegate falsingA11yDelegate) {
            init(constraintLayout, keyguardSecurityViewFlipper, globalSettings, true);
            this.mView = constraintLayout;
            this.mViewFlipper = keyguardSecurityViewFlipper;
            updateSecurityViewLocation(isLeftAligned(), false);
        }

        @Override // com.android.keyguard.KeyguardSecurityContainer.ViewMode
        public void updatePositionByTouchX(float f) {
            boolean z = f <= ((float) this.mView.getWidth()) / 2.0f;
            updateSideSetting(z);
            updateSecurityViewLocation(z, false);
        }

        @Override // com.android.keyguard.KeyguardSecurityContainer.ViewMode
        public void updateSecurityViewLocation() {
            updateSecurityViewLocation(isLeftAligned(), false);
        }

        @Override // com.android.keyguard.KeyguardSecurityContainer.SidedSecurityMode
        public void updateSecurityViewLocation(boolean z, boolean z2) {
            if (z2) {
                TransitionManager.beginDelayedTransition(this.mView, new KeyguardSecurityViewTransition());
            }
            ConstraintSet constraintSet = new ConstraintSet();
            if (z) {
                constraintSet.connect(this.mViewFlipper.getId(), 1, 0, 1);
            } else {
                constraintSet.connect(this.mViewFlipper.getId(), 2, 0, 2);
            }
            constraintSet.connect(this.mViewFlipper.getId(), 3, 0, 3);
            constraintSet.connect(this.mViewFlipper.getId(), 4, 0, 4);
            constraintSet.constrainPercentWidth(this.mViewFlipper.getId(), 0.5f);
            constraintSet.applyTo(this.mView);
        }
    }

    /* loaded from: mainsysui33.jar:com/android/keyguard/KeyguardSecurityContainer$SecurityCallback.class */
    public interface SecurityCallback {
        boolean dismiss(boolean z, int i, boolean z2, KeyguardSecurityModel.SecurityMode securityMode);

        void finish(boolean z, int i);

        void onCancelClicked();

        void onSecurityModeChanged(KeyguardSecurityModel.SecurityMode securityMode, boolean z);

        void reset();

        void userActivity();
    }

    /* loaded from: mainsysui33.jar:com/android/keyguard/KeyguardSecurityContainer$SidedSecurityMode.class */
    public static abstract class SidedSecurityMode implements ViewMode {
        public int mDefaultSideSetting;
        public GlobalSettings mGlobalSettings;
        public ConstraintLayout mView;
        public KeyguardSecurityViewFlipper mViewFlipper;

        @Override // com.android.keyguard.KeyguardSecurityContainer.ViewMode
        public void handleDoubleTap(MotionEvent motionEvent) {
            boolean isLeftAligned = isLeftAligned();
            if (isTouchOnTheOtherSideOfSecurity(motionEvent, isLeftAligned)) {
                boolean z = !isLeftAligned;
                updateSideSetting(z);
                SysUiStatsLog.write(63, z ? 5 : 6);
                updateSecurityViewLocation(z, true);
            }
        }

        public void init(ConstraintLayout constraintLayout, KeyguardSecurityViewFlipper keyguardSecurityViewFlipper, GlobalSettings globalSettings, boolean z) {
            this.mView = constraintLayout;
            this.mViewFlipper = keyguardSecurityViewFlipper;
            this.mGlobalSettings = globalSettings;
            this.mDefaultSideSetting = !z ? 1 : 0;
        }

        public boolean isLeftAligned() {
            return this.mGlobalSettings.getInt("one_handed_keyguard_side", this.mDefaultSideSetting) == 0;
        }

        public boolean isTouchOnTheOtherSideOfSecurity(MotionEvent motionEvent) {
            return isTouchOnTheOtherSideOfSecurity(motionEvent, isLeftAligned());
        }

        public final boolean isTouchOnTheOtherSideOfSecurity(MotionEvent motionEvent, boolean z) {
            float x = motionEvent.getX();
            return (z && x > ((float) this.mView.getWidth()) / 2.0f) || (!z && x < ((float) this.mView.getWidth()) / 2.0f);
        }

        public abstract void updateSecurityViewLocation(boolean z, boolean z2);

        public void updateSideSetting(boolean z) {
            this.mGlobalSettings.putInt("one_handed_keyguard_side", !z ? 1 : 0);
        }
    }

    /* loaded from: mainsysui33.jar:com/android/keyguard/KeyguardSecurityContainer$SwipeListener.class */
    public interface SwipeListener {
        void onSwipeUp();
    }

    /* loaded from: mainsysui33.jar:com/android/keyguard/KeyguardSecurityContainer$UserSwitcherViewMode.class */
    public static class UserSwitcherViewMode extends SidedSecurityMode {
        public FalsingA11yDelegate mFalsingA11yDelegate;
        public FalsingManager mFalsingManager;
        public KeyguardUserSwitcherPopupMenu mPopup;
        public Resources mResources;
        public UserSwitcherController.UserSwitchCallback mUserSwitchCallback = new UserSwitcherController.UserSwitchCallback() { // from class: com.android.keyguard.KeyguardSecurityContainer$UserSwitcherViewMode$$ExternalSyntheticLambda0
            public final void onUserSwitched() {
                KeyguardSecurityContainer.UserSwitcherViewMode.m637$r8$lambda$uI1bYLeVbnWfE91f5bDRGDXlM0(KeyguardSecurityContainer.UserSwitcherViewMode.this);
            }
        };
        public TextView mUserSwitcher;
        public UserSwitcherCallback mUserSwitcherCallback;
        public UserSwitcherController mUserSwitcherController;
        public ViewGroup mUserSwitcherViewGroup;
        public ConstraintLayout mView;
        public KeyguardSecurityViewFlipper mViewFlipper;

        /* loaded from: mainsysui33.jar:com/android/keyguard/KeyguardSecurityContainer$UserSwitcherViewMode$UserSwitcherCallback.class */
        public interface UserSwitcherCallback {
            void showUnlockToContinueMessage();
        }

        /* JADX DEBUG: Method not inlined, still used in: [com.android.keyguard.KeyguardSecurityContainer$UserSwitcherViewMode$$ExternalSyntheticLambda1.onAnimationUpdate(android.animation.ValueAnimator):void] */
        /* renamed from: $r8$lambda$7P0n9m7Hk6jzv0Wx5qZxa-Bh1Uw */
        public static /* synthetic */ void m636$r8$lambda$7P0n9m7Hk6jzv0Wx5qZxaBh1Uw(UserSwitcherViewMode userSwitcherViewMode, int i, ValueAnimator valueAnimator) {
            userSwitcherViewMode.lambda$startAppearAnimation$0(i, valueAnimator);
        }

        /* JADX DEBUG: Method not inlined, still used in: [com.android.keyguard.KeyguardSecurityContainer$UserSwitcherViewMode$$ExternalSyntheticLambda2.onClick(android.view.View):void] */
        public static /* synthetic */ void $r8$lambda$Eht9fEPukMCzlfqtjLo62eEZr1M(UserSwitcherViewMode userSwitcherViewMode, KeyguardUserSwitcherAnchor keyguardUserSwitcherAnchor, BaseUserSwitcherAdapter baseUserSwitcherAdapter, View view) {
            userSwitcherViewMode.lambda$setupUserSwitcher$2(keyguardUserSwitcherAnchor, baseUserSwitcherAdapter, view);
        }

        /* JADX DEBUG: Method not inlined, still used in: [com.android.keyguard.KeyguardSecurityContainer$UserSwitcherViewMode$$ExternalSyntheticLambda3.onItemClick(android.widget.AdapterView, android.view.View, int, long):void] */
        public static /* synthetic */ void $r8$lambda$mMpRWufEmVYEybB3bzovYMmZGrE(UserSwitcherViewMode userSwitcherViewMode, BaseUserSwitcherAdapter baseUserSwitcherAdapter, AdapterView adapterView, View view, int i, long j) {
            userSwitcherViewMode.lambda$setupUserSwitcher$1(baseUserSwitcherAdapter, adapterView, view, i, j);
        }

        /* JADX DEBUG: Method not inlined, still used in: [com.android.keyguard.KeyguardSecurityContainer$UserSwitcherViewMode$$ExternalSyntheticLambda0.onUserSwitched():void] */
        /* renamed from: $r8$lambda$uI1bYLeVbnWfE91f5-bDRGDXlM0 */
        public static /* synthetic */ void m637$r8$lambda$uI1bYLeVbnWfE91f5bDRGDXlM0(UserSwitcherViewMode userSwitcherViewMode) {
            userSwitcherViewMode.setupUserSwitcher();
        }

        public UserSwitcherViewMode(UserSwitcherCallback userSwitcherCallback) {
            this.mUserSwitcherCallback = userSwitcherCallback;
        }

        public /* synthetic */ void lambda$setupUserSwitcher$1(BaseUserSwitcherAdapter baseUserSwitcherAdapter, AdapterView adapterView, View view, int i, long j) {
            if (!this.mFalsingManager.isFalseTap(1) && view.isEnabled()) {
                UserRecord item = baseUserSwitcherAdapter.getItem(i - 1);
                if (item.isManageUsers || item.isAddSupervisedUser) {
                    this.mUserSwitcherCallback.showUnlockToContinueMessage();
                }
                if (!item.isCurrent) {
                    baseUserSwitcherAdapter.onUserListItemClicked(item);
                }
                this.mPopup.dismiss();
                this.mPopup = null;
            }
        }

        public /* synthetic */ void lambda$setupUserSwitcher$2(KeyguardUserSwitcherAnchor keyguardUserSwitcherAnchor, final BaseUserSwitcherAdapter baseUserSwitcherAdapter, View view) {
            if (this.mFalsingManager.isFalseTap(1)) {
                return;
            }
            KeyguardUserSwitcherPopupMenu keyguardUserSwitcherPopupMenu = new KeyguardUserSwitcherPopupMenu(this.mView.getContext(), this.mFalsingManager);
            this.mPopup = keyguardUserSwitcherPopupMenu;
            keyguardUserSwitcherPopupMenu.setAnchorView(keyguardUserSwitcherAnchor);
            this.mPopup.setAdapter(baseUserSwitcherAdapter);
            this.mPopup.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.android.keyguard.KeyguardSecurityContainer$UserSwitcherViewMode$$ExternalSyntheticLambda3
                @Override // android.widget.AdapterView.OnItemClickListener
                public final void onItemClick(AdapterView adapterView, View view2, int i, long j) {
                    KeyguardSecurityContainer.UserSwitcherViewMode.$r8$lambda$mMpRWufEmVYEybB3bzovYMmZGrE(KeyguardSecurityContainer.UserSwitcherViewMode.this, baseUserSwitcherAdapter, adapterView, view2, i, j);
                }
            });
            this.mPopup.show();
        }

        public /* synthetic */ void lambda$startAppearAnimation$0(int i, ValueAnimator valueAnimator) {
            float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
            this.mUserSwitcherViewGroup.setAlpha(floatValue);
            float f = i;
            this.mUserSwitcherViewGroup.setTranslationY(f - (floatValue * f));
        }

        public final Drawable findUserIcon(int i) {
            Bitmap userIcon = UserManager.get(this.mView.getContext()).getUserIcon(i);
            return userIcon != null ? new BitmapDrawable(userIcon) : UserIcons.getDefaultUserIcon(this.mResources, i, false);
        }

        public final void inflateUserSwitcher() {
            LayoutInflater.from(this.mView.getContext()).inflate(R$layout.keyguard_bouncer_user_switcher, (ViewGroup) this.mView, true);
            this.mUserSwitcherViewGroup = (ViewGroup) this.mView.findViewById(R$id.keyguard_bouncer_user_switcher);
            this.mUserSwitcher = (TextView) this.mView.findViewById(R$id.user_switcher_header);
        }

        @Override // com.android.keyguard.KeyguardSecurityContainer.ViewMode
        public void init(ConstraintLayout constraintLayout, GlobalSettings globalSettings, KeyguardSecurityViewFlipper keyguardSecurityViewFlipper, FalsingManager falsingManager, UserSwitcherController userSwitcherController, FalsingA11yDelegate falsingA11yDelegate) {
            init(constraintLayout, keyguardSecurityViewFlipper, globalSettings, false);
            this.mView = constraintLayout;
            this.mViewFlipper = keyguardSecurityViewFlipper;
            this.mFalsingManager = falsingManager;
            this.mUserSwitcherController = userSwitcherController;
            this.mResources = constraintLayout.getContext().getResources();
            this.mFalsingA11yDelegate = falsingA11yDelegate;
            if (this.mUserSwitcherViewGroup == null) {
                inflateUserSwitcher();
            }
            updateSecurityViewLocation();
            setupUserSwitcher();
            this.mUserSwitcherController.addUserSwitchCallback(this.mUserSwitchCallback);
        }

        @Override // com.android.keyguard.KeyguardSecurityContainer.ViewMode
        public void onDensityOrFontScaleChanged() {
            this.mView.removeView(this.mUserSwitcherViewGroup);
            inflateUserSwitcher();
        }

        @Override // com.android.keyguard.KeyguardSecurityContainer.ViewMode
        public void onDestroy() {
            this.mUserSwitcherController.removeUserSwitchCallback(this.mUserSwitchCallback);
        }

        @Override // com.android.keyguard.KeyguardSecurityContainer.ViewMode
        public void reloadColors() {
            TextView textView = (TextView) this.mView.findViewById(R$id.user_switcher_header);
            if (textView != null) {
                textView.setTextColor(Utils.getColorAttrDefaultColor(this.mView.getContext(), 16842806));
                textView.setBackground(this.mView.getContext().getDrawable(R$drawable.bouncer_user_switcher_header_bg));
                ((LayerDrawable) textView.getBackground().mutate()).findDrawableByLayerId(R$id.user_switcher_key_down).setTintList(Utils.getColorAttr(this.mView.getContext(), 16842806));
            }
        }

        @Override // com.android.keyguard.KeyguardSecurityContainer.ViewMode
        public void reset() {
            KeyguardUserSwitcherPopupMenu keyguardUserSwitcherPopupMenu = this.mPopup;
            if (keyguardUserSwitcherPopupMenu != null) {
                keyguardUserSwitcherPopupMenu.dismiss();
                this.mPopup = null;
            }
            setupUserSwitcher();
        }

        public final void setupUserSwitcher() {
            final UserRecord currentUserRecord = this.mUserSwitcherController.getCurrentUserRecord();
            if (currentUserRecord == null) {
                Log.e("KeyguardSecurityView", "Current user in user switcher is null.");
                return;
            }
            String currentUserName = this.mUserSwitcherController.getCurrentUserName();
            ((ImageView) this.mView.findViewById(R$id.user_icon)).setImageDrawable(findUserIcon(currentUserRecord.info.id));
            this.mUserSwitcher.setText(currentUserName);
            final KeyguardUserSwitcherAnchor keyguardUserSwitcherAnchor = (KeyguardUserSwitcherAnchor) this.mView.findViewById(R$id.user_switcher_anchor);
            keyguardUserSwitcherAnchor.setAccessibilityDelegate(this.mFalsingA11yDelegate);
            final BaseUserSwitcherAdapter baseUserSwitcherAdapter = new BaseUserSwitcherAdapter(this.mUserSwitcherController) { // from class: com.android.keyguard.KeyguardSecurityContainer.UserSwitcherViewMode.2
                {
                    UserSwitcherViewMode.this = this;
                }

                public final Drawable getDrawable(UserRecord userRecord, Context context) {
                    Drawable drawable = (userRecord.isCurrent && userRecord.isGuest) ? context.getDrawable(R$drawable.ic_avatar_guest_user) : BaseUserSwitcherAdapter.getIconDrawable(context, userRecord);
                    drawable.setTint(userRecord.isSwitchToEnabled ? Utils.getColorAttrDefaultColor(context, 17956901) : context.getResources().getColor(R$color.kg_user_switcher_restricted_avatar_icon_color, context.getTheme()));
                    Drawable drawable2 = context.getDrawable(R$drawable.user_avatar_bg);
                    drawable2.setTintBlendMode(BlendMode.DST);
                    drawable2.setTint(Utils.getColorAttrDefaultColor(context, 17956912));
                    return new LayerDrawable(new Drawable[]{drawable2, drawable});
                }

                public View getView(int i, View view, ViewGroup viewGroup) {
                    UserRecord item = getItem(i);
                    FrameLayout frameLayout = (FrameLayout) view;
                    boolean z = false;
                    FrameLayout frameLayout2 = frameLayout;
                    if (frameLayout == null) {
                        frameLayout2 = (FrameLayout) LayoutInflater.from(viewGroup.getContext()).inflate(R$layout.keyguard_bouncer_user_switcher_item, viewGroup, false);
                    }
                    TextView textView = (TextView) frameLayout2.getChildAt(0);
                    textView.setText(getName(viewGroup.getContext(), item));
                    BitmapDrawable bitmapDrawable = item.picture != null ? new BitmapDrawable(item.picture) : getDrawable(item, frameLayout2.getContext());
                    int dimensionPixelSize = frameLayout2.getResources().getDimensionPixelSize(R$dimen.bouncer_user_switcher_item_icon_size);
                    int dimensionPixelSize2 = frameLayout2.getResources().getDimensionPixelSize(R$dimen.bouncer_user_switcher_item_icon_padding);
                    bitmapDrawable.setBounds(0, 0, dimensionPixelSize, dimensionPixelSize);
                    textView.setCompoundDrawablePadding(dimensionPixelSize2);
                    textView.setCompoundDrawablesRelative(bitmapDrawable, null, null, null);
                    if (item == currentUserRecord) {
                        textView.setBackground(frameLayout2.getContext().getDrawable(R$drawable.bouncer_user_switcher_item_selected_bg));
                    } else {
                        textView.setBackground(null);
                    }
                    if (item == currentUserRecord) {
                        z = true;
                    }
                    textView.setSelected(z);
                    frameLayout2.setEnabled(item.isSwitchToEnabled);
                    UserSwitcherController.setSelectableAlpha(frameLayout2);
                    return frameLayout2;
                }
            };
            keyguardUserSwitcherAnchor.setOnClickListener(new View.OnClickListener() { // from class: com.android.keyguard.KeyguardSecurityContainer$UserSwitcherViewMode$$ExternalSyntheticLambda2
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    KeyguardSecurityContainer.UserSwitcherViewMode.$r8$lambda$Eht9fEPukMCzlfqtjLo62eEZr1M(KeyguardSecurityContainer.UserSwitcherViewMode.this, keyguardUserSwitcherAnchor, baseUserSwitcherAdapter, view);
                }
            });
        }

        @Override // com.android.keyguard.KeyguardSecurityContainer.ViewMode
        public void startAppearAnimation(KeyguardSecurityModel.SecurityMode securityMode) {
            if (securityMode == KeyguardSecurityModel.SecurityMode.Password) {
                return;
            }
            this.mUserSwitcherViewGroup.setAlpha(ActionBarShadowController.ELEVATION_LOW);
            ValueAnimator ofFloat = ValueAnimator.ofFloat(ActionBarShadowController.ELEVATION_LOW, 1.0f);
            final int dimensionPixelSize = this.mView.getResources().getDimensionPixelSize(R$dimen.pin_view_trans_y_entry);
            ofFloat.setInterpolator(Interpolators.STANDARD_DECELERATE);
            ofFloat.setDuration(650L);
            ofFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.keyguard.KeyguardSecurityContainer.UserSwitcherViewMode.1
                {
                    UserSwitcherViewMode.this = this;
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    UserSwitcherViewMode.this.mUserSwitcherViewGroup.setAlpha(1.0f);
                    UserSwitcherViewMode.this.mUserSwitcherViewGroup.setTranslationY(ActionBarShadowController.ELEVATION_LOW);
                }
            });
            ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.keyguard.KeyguardSecurityContainer$UserSwitcherViewMode$$ExternalSyntheticLambda1
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    KeyguardSecurityContainer.UserSwitcherViewMode.m636$r8$lambda$7P0n9m7Hk6jzv0Wx5qZxaBh1Uw(KeyguardSecurityContainer.UserSwitcherViewMode.this, dimensionPixelSize, valueAnimator);
                }
            });
            ofFloat.start();
        }

        @Override // com.android.keyguard.KeyguardSecurityContainer.ViewMode
        public void startDisappearAnimation(KeyguardSecurityModel.SecurityMode securityMode) {
            if (securityMode == KeyguardSecurityModel.SecurityMode.Password) {
                return;
            }
            int dimensionPixelSize = this.mResources.getDimensionPixelSize(R$dimen.disappear_y_translation);
            AnimatorSet animatorSet = new AnimatorSet();
            ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this.mView, View.TRANSLATION_Y, dimensionPixelSize);
            ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(this.mView, View.ALPHA, ActionBarShadowController.ELEVATION_LOW);
            animatorSet.setInterpolator(Interpolators.STANDARD_ACCELERATE);
            animatorSet.playTogether(ofFloat2, ofFloat);
            animatorSet.start();
        }

        @Override // com.android.keyguard.KeyguardSecurityContainer.ViewMode
        public void updateSecurityViewLocation() {
            updateSecurityViewLocation(isLeftAligned(), false);
        }

        @Override // com.android.keyguard.KeyguardSecurityContainer.SidedSecurityMode
        public void updateSecurityViewLocation(boolean z, boolean z2) {
            if (z2) {
                TransitionManager.beginDelayedTransition(this.mView, new KeyguardSecurityViewTransition());
            }
            int dimensionPixelSize = this.mResources.getDimensionPixelSize(R$dimen.bouncer_user_switcher_y_trans);
            int dimensionPixelSize2 = this.mResources.getDimensionPixelSize(R$dimen.bouncer_user_switcher_view_mode_view_flipper_bottom_margin);
            int dimensionPixelSize3 = this.mResources.getDimensionPixelSize(R$dimen.bouncer_user_switcher_view_mode_user_switcher_bottom_margin);
            if (this.mResources.getConfiguration().orientation == 1) {
                ConstraintSet constraintSet = new ConstraintSet();
                constraintSet.connect(this.mUserSwitcherViewGroup.getId(), 3, 0, 3, dimensionPixelSize);
                constraintSet.connect(this.mUserSwitcherViewGroup.getId(), 4, this.mViewFlipper.getId(), 3, dimensionPixelSize3);
                constraintSet.connect(this.mViewFlipper.getId(), 3, this.mUserSwitcherViewGroup.getId(), 4);
                constraintSet.connect(this.mViewFlipper.getId(), 4, 0, 4, dimensionPixelSize2);
                constraintSet.centerHorizontally(this.mViewFlipper.getId(), 0);
                constraintSet.centerHorizontally(this.mUserSwitcherViewGroup.getId(), 0);
                constraintSet.setVerticalChainStyle(this.mViewFlipper.getId(), 0);
                constraintSet.setVerticalChainStyle(this.mUserSwitcherViewGroup.getId(), 0);
                constraintSet.constrainHeight(this.mUserSwitcherViewGroup.getId(), -2);
                constraintSet.constrainWidth(this.mUserSwitcherViewGroup.getId(), -2);
                constraintSet.constrainHeight(this.mViewFlipper.getId(), 0);
                constraintSet.applyTo(this.mView);
                return;
            }
            int id = z ? this.mViewFlipper.getId() : this.mUserSwitcherViewGroup.getId();
            int id2 = z ? this.mUserSwitcherViewGroup.getId() : this.mViewFlipper.getId();
            ConstraintSet constraintSet2 = new ConstraintSet();
            constraintSet2.connect(id, 1, 0, 1);
            constraintSet2.connect(id, 2, id2, 1);
            constraintSet2.connect(id2, 1, id, 2);
            constraintSet2.connect(id2, 2, 0, 2);
            constraintSet2.connect(this.mUserSwitcherViewGroup.getId(), 3, 0, 3);
            constraintSet2.connect(this.mUserSwitcherViewGroup.getId(), 4, 0, 4, dimensionPixelSize);
            constraintSet2.connect(this.mViewFlipper.getId(), 3, 0, 3);
            constraintSet2.connect(this.mViewFlipper.getId(), 4, 0, 4);
            constraintSet2.setHorizontalChainStyle(this.mUserSwitcherViewGroup.getId(), 0);
            constraintSet2.setHorizontalChainStyle(this.mViewFlipper.getId(), 0);
            constraintSet2.constrainHeight(this.mUserSwitcherViewGroup.getId(), 0);
            constraintSet2.constrainWidth(this.mUserSwitcherViewGroup.getId(), 0);
            constraintSet2.constrainWidth(this.mViewFlipper.getId(), 0);
            constraintSet2.constrainHeight(this.mViewFlipper.getId(), 0);
            constraintSet2.applyTo(this.mView);
        }
    }

    /* loaded from: mainsysui33.jar:com/android/keyguard/KeyguardSecurityContainer$ViewMode.class */
    public interface ViewMode {
        default void handleDoubleTap(MotionEvent motionEvent) {
        }

        default void init(ConstraintLayout constraintLayout, GlobalSettings globalSettings, KeyguardSecurityViewFlipper keyguardSecurityViewFlipper, FalsingManager falsingManager, UserSwitcherController userSwitcherController, FalsingA11yDelegate falsingA11yDelegate) {
        }

        default void onDensityOrFontScaleChanged() {
        }

        default void onDestroy() {
        }

        default void reloadColors() {
        }

        default void reset() {
        }

        default void startAppearAnimation(KeyguardSecurityModel.SecurityMode securityMode) {
        }

        default void startDisappearAnimation(KeyguardSecurityModel.SecurityMode securityMode) {
        }

        default void updatePositionByTouchX(float f) {
        }

        default void updateSecurityViewLocation() {
        }
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.keyguard.KeyguardSecurityContainer$$ExternalSyntheticLambda1.test(java.lang.Object):boolean] */
    public static /* synthetic */ boolean $r8$lambda$F6CZdwTTzyX0eUVHA_bg6y_Eeo0(MotionEvent motionEvent, Gefingerpoken gefingerpoken) {
        return lambda$onInterceptTouchEvent$0(motionEvent, gefingerpoken);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.keyguard.KeyguardSecurityContainer$$ExternalSyntheticLambda0.test(java.lang.Object):boolean] */
    public static /* synthetic */ boolean $r8$lambda$J7kIOl0DwWmzaigpCnvoE9gikaI(MotionEvent motionEvent, Gefingerpoken gefingerpoken) {
        return lambda$onTouchEvent$1(motionEvent, gefingerpoken);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.keyguard.KeyguardSecurityContainer$$ExternalSyntheticLambda2.get():java.lang.Object] */
    /* renamed from: $r8$lambda$kd3A6NCNsK_0-CNZvXr4aXwnhpQ */
    public static /* synthetic */ String m630$r8$lambda$kd3A6NCNsK_0CNZvXr4aXwnhpQ(KeyguardSecurityContainer keyguardSecurityContainer, int i) {
        return keyguardSecurityContainer.lambda$showWipeDialog$3(i);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.keyguard.KeyguardSecurityContainer$$ExternalSyntheticLambda3.get():java.lang.Object] */
    public static /* synthetic */ String $r8$lambda$sWiPOffDB78XTGbEBLhBiAtFGsA(KeyguardSecurityContainer keyguardSecurityContainer, int i, int i2) {
        return keyguardSecurityContainer.lambda$showAlmostAtWipeDialog$2(i, i2);
    }

    public KeyguardSecurityContainer(Context context) {
        this(context, null, 0);
    }

    public KeyguardSecurityContainer(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public KeyguardSecurityContainer(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mVelocityTracker = VelocityTracker.obtain();
        this.mMotionEventListeners = new ArrayList();
        this.mLastTouchY = -1.0f;
        this.mActivePointerId = -1;
        this.mStartTouchY = -1.0f;
        this.mViewMode = new DefaultViewMode();
        this.mCurrentMode = -1;
        this.mWidth = -1;
        this.mWindowInsetsAnimationCallback = new WindowInsetsAnimation.Callback(0) { // from class: com.android.keyguard.KeyguardSecurityContainer.1
            public final Rect mInitialBounds = new Rect();
            public final Rect mFinalBounds = new Rect();

            {
                KeyguardSecurityContainer.this = this;
            }

            @Override // android.view.WindowInsetsAnimation.Callback
            public void onEnd(WindowInsetsAnimation windowInsetsAnimation) {
                if (KeyguardSecurityContainer.this.mDisappearAnimRunning) {
                    KeyguardSecurityContainer.this.endJankInstrument(20);
                    KeyguardSecurityContainer.this.setAlpha(ActionBarShadowController.ELEVATION_LOW);
                } else {
                    KeyguardSecurityContainer.this.endJankInstrument(17);
                }
                updateChildren(0, 1.0f);
            }

            @Override // android.view.WindowInsetsAnimation.Callback
            public void onPrepare(WindowInsetsAnimation windowInsetsAnimation) {
                KeyguardSecurityContainer.this.mSecurityViewFlipper.getBoundsOnScreen(this.mInitialBounds);
            }

            @Override // android.view.WindowInsetsAnimation.Callback
            public WindowInsets onProgress(WindowInsets windowInsets, List<WindowInsetsAnimation> list) {
                float f = KeyguardSecurityContainer.this.mDisappearAnimRunning ? -(this.mFinalBounds.bottom - this.mInitialBounds.bottom) : this.mInitialBounds.bottom - this.mFinalBounds.bottom;
                float f2 = KeyguardSecurityContainer.this.mDisappearAnimRunning ? -((this.mFinalBounds.bottom - this.mInitialBounds.bottom) * 0.75f) : 0.0f;
                int i2 = 0;
                float f3 = 1.0f;
                for (WindowInsetsAnimation windowInsetsAnimation : list) {
                    if ((windowInsetsAnimation.getTypeMask() & WindowInsets.Type.ime()) != 0) {
                        f3 = windowInsetsAnimation.getInterpolatedFraction();
                        i2 += (int) MathUtils.lerp(f, f2, f3);
                    }
                }
                updateChildren(i2, KeyguardSecurityContainer.this.mDisappearAnimRunning ? 1.0f - f3 : Math.max(f3, KeyguardSecurityContainer.this.getAlpha()));
                return windowInsets;
            }

            @Override // android.view.WindowInsetsAnimation.Callback
            public WindowInsetsAnimation.Bounds onStart(WindowInsetsAnimation windowInsetsAnimation, WindowInsetsAnimation.Bounds bounds) {
                if (KeyguardSecurityContainer.this.mDisappearAnimRunning) {
                    KeyguardSecurityContainer.this.beginJankInstrument(20);
                } else {
                    KeyguardSecurityContainer.this.beginJankInstrument(17);
                }
                KeyguardSecurityContainer.this.mSecurityViewFlipper.getBoundsOnScreen(this.mFinalBounds);
                return bounds;
            }

            public final void updateChildren(int i2, float f) {
                for (int i3 = 0; i3 < KeyguardSecurityContainer.this.getChildCount(); i3++) {
                    View childAt = KeyguardSecurityContainer.this.getChildAt(i3);
                    childAt.setTranslationY(i2);
                    childAt.setAlpha(f);
                }
            }
        };
        this.mSpringAnimation = new SpringAnimation(this, DynamicAnimation.Y);
        this.mViewConfiguration = ViewConfiguration.get(context);
        this.mDoubleTapDetector = new GestureDetector(context, new DoubleTapListener());
    }

    public static /* synthetic */ boolean lambda$onInterceptTouchEvent$0(MotionEvent motionEvent, Gefingerpoken gefingerpoken) {
        return gefingerpoken.onInterceptTouchEvent(motionEvent);
    }

    public static /* synthetic */ boolean lambda$onTouchEvent$1(MotionEvent motionEvent, Gefingerpoken gefingerpoken) {
        return gefingerpoken.onTouchEvent(motionEvent);
    }

    public /* synthetic */ String lambda$showAlmostAtWipeDialog$2(int i, int i2) {
        return ((ViewGroup) this).mContext.getString(R$string.kg_failed_attempts_almost_at_erase_profile, Integer.valueOf(i), Integer.valueOf(i2));
    }

    public /* synthetic */ String lambda$showWipeDialog$3(int i) {
        return ((ViewGroup) this).mContext.getString(R$string.kg_failed_attempts_now_erasing_profile, Integer.valueOf(i));
    }

    public void addMotionEventListener(Gefingerpoken gefingerpoken) {
        this.mMotionEventListeners.add(gefingerpoken);
    }

    public final void beginJankInstrument(int i) {
        KeyguardInputView securityView = this.mSecurityViewFlipper.getSecurityView();
        if (securityView == null) {
            return;
        }
        InteractionJankMonitor.getInstance().begin(securityView, i);
    }

    public final void endJankInstrument(int i) {
        InteractionJankMonitor.getInstance().end(i);
    }

    public CharSequence getTitle() {
        return this.mSecurityViewFlipper.getTitle();
    }

    public boolean handleDoubleTap(MotionEvent motionEvent) {
        if (this.mIsDragging) {
            return false;
        }
        this.mViewMode.handleDoubleTap(motionEvent);
        return true;
    }

    public void initMode(int i, GlobalSettings globalSettings, FalsingManager falsingManager, UserSwitcherController userSwitcherController, UserSwitcherViewMode.UserSwitcherCallback userSwitcherCallback, FalsingA11yDelegate falsingA11yDelegate) {
        if (this.mCurrentMode == i) {
            return;
        }
        Log.i("KeyguardSecurityView", "Switching mode from " + modeToString(this.mCurrentMode) + " to " + modeToString(i));
        this.mCurrentMode = i;
        this.mViewMode.onDestroy();
        if (i == 1) {
            this.mViewMode = new OneHandedViewMode();
        } else if (i != 2) {
            this.mViewMode = new DefaultViewMode();
        } else {
            this.mViewMode = new UserSwitcherViewMode(userSwitcherCallback);
        }
        this.mGlobalSettings = globalSettings;
        this.mFalsingManager = falsingManager;
        this.mFalsingA11yDelegate = falsingA11yDelegate;
        this.mUserSwitcherController = userSwitcherController;
        setupViewMode();
    }

    public boolean isSecurityLeftAligned() {
        ViewMode viewMode = this.mViewMode;
        return (viewMode instanceof SidedSecurityMode) && ((SidedSecurityMode) viewMode).isLeftAligned();
    }

    public boolean isSidedSecurityMode() {
        return this.mViewMode instanceof SidedSecurityMode;
    }

    public boolean isTouchOnTheOtherSideOfSecurity(MotionEvent motionEvent) {
        ViewMode viewMode = this.mViewMode;
        return (viewMode instanceof SidedSecurityMode) && ((SidedSecurityMode) viewMode).isTouchOnTheOtherSideOfSecurity(motionEvent);
    }

    public final String modeToString(int i) {
        if (i != -1) {
            if (i != 0) {
                if (i != 1) {
                    if (i == 2) {
                        return "UserSwitcher";
                    }
                    throw new IllegalArgumentException("mode: " + i + " not supported");
                }
                return "OneHanded";
            }
            return "Default";
        }
        return "Uninitialized";
    }

    @Override // android.view.View
    public WindowInsets onApplyWindowInsets(WindowInsets windowInsets) {
        int max = Integer.max(windowInsets.getInsetsIgnoringVisibility(WindowInsets.Type.systemBars()).bottom, windowInsets.getInsets(WindowInsets.Type.ime()).bottom);
        setPadding(getPaddingLeft(), getPaddingTop(), getPaddingRight(), Integer.max(max, getContext().getResources().getDimensionPixelSize(R$dimen.keyguard_security_view_bottom_margin)));
        return windowInsets.inset(0, 0, 0, max);
    }

    @Override // android.view.View
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this.mViewMode.updateSecurityViewLocation();
    }

    public void onDensityOrFontScaleChanged() {
        this.mViewMode.onDensityOrFontScaleChanged();
    }

    @Override // android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mSecurityViewFlipper = (KeyguardSecurityViewFlipper) findViewById(R$id.view_flipper);
    }

    /* JADX WARN: Code restructure failed: missing block: B:56:0x0040, code lost:
        if (r0 != 3) goto L14;
     */
    @Override // android.view.ViewGroup
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean onInterceptTouchEvent(final MotionEvent motionEvent) {
        boolean z = this.mMotionEventListeners.stream().anyMatch(new Predicate() { // from class: com.android.keyguard.KeyguardSecurityContainer$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return KeyguardSecurityContainer.$r8$lambda$F6CZdwTTzyX0eUVHA_bg6y_Eeo0(motionEvent, (Gefingerpoken) obj);
            }
        }) || super.onInterceptTouchEvent(motionEvent);
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked != 0) {
            if (actionMasked != 1) {
                if (actionMasked == 2) {
                    if (this.mIsDragging) {
                        return true;
                    }
                    if (!this.mSwipeUpToRetry || this.mSecurityViewFlipper.getSecurityView().disallowInterceptTouch(motionEvent)) {
                        return false;
                    }
                    int findPointerIndex = motionEvent.findPointerIndex(this.mActivePointerId);
                    float scaledTouchSlop = this.mViewConfiguration.getScaledTouchSlop();
                    if (findPointerIndex != -1 && this.mStartTouchY - motionEvent.getY(findPointerIndex) > scaledTouchSlop * 4.0f) {
                        this.mIsDragging = true;
                        return true;
                    }
                }
            }
            this.mIsDragging = false;
        } else {
            int actionIndex = motionEvent.getActionIndex();
            this.mStartTouchY = motionEvent.getY(actionIndex);
            this.mActivePointerId = motionEvent.getPointerId(actionIndex);
            this.mVelocityTracker.clear();
        }
        return z;
    }

    @Override // androidx.constraintlayout.widget.ConstraintLayout, android.view.ViewGroup, android.view.View
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        int i5 = i3 - i;
        if (!z || this.mWidth == i5) {
            return;
        }
        this.mWidth = i5;
        this.mViewMode.updateSecurityViewLocation();
    }

    public void onPause() {
        AlertDialog alertDialog = this.mAlertDialog;
        if (alertDialog != null) {
            alertDialog.dismiss();
            this.mAlertDialog = null;
        }
        this.mSecurityViewFlipper.setWindowInsetsAnimationCallback(null);
        this.mViewMode.reset();
    }

    public void onResume(KeyguardSecurityModel.SecurityMode securityMode, boolean z) {
        this.mSecurityViewFlipper.setWindowInsetsAnimationCallback(this.mWindowInsetsAnimationCallback);
        updateBiometricRetry(securityMode, z);
    }

    @Override // android.view.View
    public boolean onTouchEvent(final MotionEvent motionEvent) {
        SwipeListener swipeListener;
        int actionMasked = motionEvent.getActionMasked();
        if (!this.mMotionEventListeners.stream().anyMatch(new Predicate() { // from class: com.android.keyguard.KeyguardSecurityContainer$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return KeyguardSecurityContainer.$r8$lambda$J7kIOl0DwWmzaigpCnvoE9gikaI(motionEvent, (Gefingerpoken) obj);
            }
        })) {
            super.onTouchEvent(motionEvent);
        }
        this.mDoubleTapDetector.onTouchEvent(motionEvent);
        int i = 0;
        if (actionMasked != 1) {
            if (actionMasked == 2) {
                this.mVelocityTracker.addMovement(motionEvent);
                float y = motionEvent.getY(motionEvent.findPointerIndex(this.mActivePointerId));
                float f = this.mLastTouchY;
                if (f != -1.0f) {
                    setTranslationY(getTranslationY() + ((y - f) * 0.25f));
                }
                this.mLastTouchY = y;
            } else if (actionMasked != 3) {
                if (actionMasked == 6) {
                    int actionIndex = motionEvent.getActionIndex();
                    if (motionEvent.getPointerId(actionIndex) == this.mActivePointerId) {
                        if (actionIndex == 0) {
                            i = 1;
                        }
                        this.mLastTouchY = motionEvent.getY(i);
                        this.mActivePointerId = motionEvent.getPointerId(i);
                    }
                }
            }
            if (actionMasked == 1 || (-getTranslationY()) <= TypedValue.applyDimension(1, 10.0f, getResources().getDisplayMetrics()) || (swipeListener = this.mSwipeListener) == null) {
                return true;
            }
            swipeListener.onSwipeUp();
            return true;
        }
        this.mActivePointerId = -1;
        this.mLastTouchY = -1.0f;
        this.mIsDragging = false;
        startSpringAnimation(this.mVelocityTracker.getYVelocity());
        return actionMasked == 1 ? true : true;
    }

    public void reloadColors() {
        this.mViewMode.reloadColors();
    }

    public void removeMotionEventListener(Gefingerpoken gefingerpoken) {
        this.mMotionEventListeners.remove(gefingerpoken);
    }

    public void reset() {
        this.mViewMode.reset();
        this.mDisappearAnimRunning = false;
    }

    public void setSwipeListener(SwipeListener swipeListener) {
        this.mSwipeListener = swipeListener;
    }

    public final void setupViewMode() {
        GlobalSettings globalSettings;
        FalsingManager falsingManager;
        UserSwitcherController userSwitcherController;
        KeyguardSecurityViewFlipper keyguardSecurityViewFlipper = this.mSecurityViewFlipper;
        if (keyguardSecurityViewFlipper == null || (globalSettings = this.mGlobalSettings) == null || (falsingManager = this.mFalsingManager) == null || (userSwitcherController = this.mUserSwitcherController) == null) {
            return;
        }
        this.mViewMode.init(this, globalSettings, keyguardSecurityViewFlipper, falsingManager, userSwitcherController, this.mFalsingA11yDelegate);
    }

    @Override // androidx.constraintlayout.widget.ConstraintLayout, android.view.ViewGroup
    public boolean shouldDelayChildPressedState() {
        return true;
    }

    public void showAlmostAtWipeDialog(final int i, final int i2, int i3) {
        showDialog(null, i3 != 1 ? i3 != 2 ? i3 != 3 ? null : ((ViewGroup) this).mContext.getString(R$string.kg_failed_attempts_almost_at_erase_user, Integer.valueOf(i), Integer.valueOf(i2)) : ((DevicePolicyManager) ((ViewGroup) this).mContext.getSystemService(DevicePolicyManager.class)).getResources().getString("SystemUi.KEYGUARD_DIALOG_FAILED_ATTEMPTS_ALMOST_ERASING_PROFILE", new Supplier() { // from class: com.android.keyguard.KeyguardSecurityContainer$$ExternalSyntheticLambda3
            @Override // java.util.function.Supplier
            public final Object get() {
                return KeyguardSecurityContainer.$r8$lambda$sWiPOffDB78XTGbEBLhBiAtFGsA(KeyguardSecurityContainer.this, i, i2);
            }
        }, new Object[]{Integer.valueOf(i), Integer.valueOf(i2)}) : ((ViewGroup) this).mContext.getString(R$string.kg_failed_attempts_almost_at_wipe, Integer.valueOf(i), Integer.valueOf(i2)));
    }

    public final void showDialog(String str, String str2) {
        AlertDialog alertDialog = this.mAlertDialog;
        if (alertDialog != null) {
            alertDialog.dismiss();
        }
        AlertDialog create = new AlertDialog.Builder(((ViewGroup) this).mContext).setTitle(str).setMessage(str2).setCancelable(false).setNeutralButton(R$string.ok, (DialogInterface.OnClickListener) null).create();
        this.mAlertDialog = create;
        if (!(((ViewGroup) this).mContext instanceof Activity)) {
            create.getWindow().setType(2009);
        }
        this.mAlertDialog.show();
    }

    public void showTimeoutDialog(int i, int i2, LockPatternUtils lockPatternUtils, KeyguardSecurityModel.SecurityMode securityMode) {
        int i3 = i2 / 1000;
        int i4 = AnonymousClass2.$SwitchMap$com$android$keyguard$KeyguardSecurityModel$SecurityMode[securityMode.ordinal()];
        int i5 = i4 != 1 ? i4 != 2 ? i4 != 3 ? 0 : R$string.kg_too_many_failed_password_attempts_dialog_message : R$string.kg_too_many_failed_pin_attempts_dialog_message : R$string.kg_too_many_failed_pattern_attempts_dialog_message;
        if (i5 != 0) {
            showDialog(null, ((ViewGroup) this).mContext.getString(i5, Integer.valueOf(lockPatternUtils.getCurrentFailedPasswordAttempts(i)), Integer.valueOf(i3)));
        }
    }

    public void showWipeDialog(final int i, int i2) {
        showDialog(null, i2 != 1 ? i2 != 2 ? i2 != 3 ? null : ((ViewGroup) this).mContext.getString(R$string.kg_failed_attempts_now_erasing_user, Integer.valueOf(i)) : ((DevicePolicyManager) ((ViewGroup) this).mContext.getSystemService(DevicePolicyManager.class)).getResources().getString("SystemUi.KEYGUARD_DIALOG_FAILED_ATTEMPTS_ERASING_PROFILE", new Supplier() { // from class: com.android.keyguard.KeyguardSecurityContainer$$ExternalSyntheticLambda2
            @Override // java.util.function.Supplier
            public final Object get() {
                return KeyguardSecurityContainer.m630$r8$lambda$kd3A6NCNsK_0CNZvXr4aXwnhpQ(KeyguardSecurityContainer.this, i);
            }
        }, new Object[]{Integer.valueOf(i)}) : ((ViewGroup) this).mContext.getString(R$string.kg_failed_attempts_now_wiping, Integer.valueOf(i)));
    }

    public void startAppearAnimation(KeyguardSecurityModel.SecurityMode securityMode) {
        this.mViewMode.startAppearAnimation(securityMode);
    }

    public void startDisappearAnimation(KeyguardSecurityModel.SecurityMode securityMode) {
        this.mDisappearAnimRunning = true;
        this.mViewMode.startDisappearAnimation(securityMode);
    }

    public final void startSpringAnimation(float f) {
        this.mSpringAnimation.setStartVelocity(f).animateToFinalPosition(ActionBarShadowController.ELEVATION_LOW);
    }

    public final void updateBiometricRetry(KeyguardSecurityModel.SecurityMode securityMode, boolean z) {
        this.mSwipeUpToRetry = (!z || securityMode == KeyguardSecurityModel.SecurityMode.SimPin || securityMode == KeyguardSecurityModel.SecurityMode.SimPuk || securityMode == KeyguardSecurityModel.SecurityMode.None) ? false : true;
    }

    public void updatePositionByTouchX(float f) {
        this.mViewMode.updatePositionByTouchX(f);
    }
}