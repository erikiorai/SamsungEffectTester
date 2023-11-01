package com.android.keyguard;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.android.internal.annotations.VisibleForTesting;
import com.android.keyguard.EmergencyButtonController;
import com.android.keyguard.KeyguardInputViewController;
import com.android.keyguard.KeyguardSecurityModel;
import com.android.systemui.R$layout;
import com.android.systemui.util.ViewController;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: mainsysui33.jar:com/android/keyguard/KeyguardSecurityViewFlipperController.class */
public class KeyguardSecurityViewFlipperController extends ViewController<KeyguardSecurityViewFlipper> {
    public static final boolean DEBUG = KeyguardConstants.DEBUG;
    public final List<KeyguardInputViewController<KeyguardInputView>> mChildren;
    public final EmergencyButtonController.Factory mEmergencyButtonControllerFactory;
    public final KeyguardInputViewController.Factory mKeyguardSecurityViewControllerFactory;
    public final LayoutInflater mLayoutInflater;

    /* renamed from: com.android.keyguard.KeyguardSecurityViewFlipperController$1  reason: invalid class name */
    /* loaded from: mainsysui33.jar:com/android/keyguard/KeyguardSecurityViewFlipperController$1.class */
    public static /* synthetic */ class AnonymousClass1 {
        public static final /* synthetic */ int[] $SwitchMap$com$android$keyguard$KeyguardSecurityModel$SecurityMode;

        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:13:0x0041 -> B:27:0x0014). Please submit an issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:15:0x0045 -> B:25:0x001f). Please submit an issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:17:0x0049 -> B:23:0x002a). Please submit an issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:19:0x004d -> B:29:0x0035). Please submit an issue!!! */
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
                $SwitchMap$com$android$keyguard$KeyguardSecurityModel$SecurityMode[KeyguardSecurityModel.SecurityMode.SimPin.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$android$keyguard$KeyguardSecurityModel$SecurityMode[KeyguardSecurityModel.SecurityMode.SimPuk.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
        }
    }

    /* loaded from: mainsysui33.jar:com/android/keyguard/KeyguardSecurityViewFlipperController$NullKeyguardInputViewController.class */
    public static class NullKeyguardInputViewController extends KeyguardInputViewController<KeyguardInputView> {
        public NullKeyguardInputViewController(KeyguardSecurityModel.SecurityMode securityMode, KeyguardSecurityCallback keyguardSecurityCallback, EmergencyButtonController emergencyButtonController) {
            super(null, securityMode, keyguardSecurityCallback, emergencyButtonController, null);
        }

        @Override // com.android.keyguard.KeyguardInputViewController
        public int getInitialMessageResId() {
            return 0;
        }

        @Override // com.android.keyguard.KeyguardSecurityView
        public boolean needsInput() {
            return false;
        }

        @Override // com.android.keyguard.KeyguardSecurityView
        public void onStartingToHide() {
        }
    }

    public KeyguardSecurityViewFlipperController(KeyguardSecurityViewFlipper keyguardSecurityViewFlipper, LayoutInflater layoutInflater, KeyguardInputViewController.Factory factory, EmergencyButtonController.Factory factory2) {
        super(keyguardSecurityViewFlipper);
        this.mChildren = new ArrayList();
        this.mKeyguardSecurityViewControllerFactory = factory;
        this.mLayoutInflater = layoutInflater;
        this.mEmergencyButtonControllerFactory = factory2;
    }

    public void clearViews() {
        ((KeyguardSecurityViewFlipper) ((ViewController) this).mView).removeAllViews();
        this.mChildren.clear();
    }

    public final int getLayoutIdFor(KeyguardSecurityModel.SecurityMode securityMode) {
        int i = AnonymousClass1.$SwitchMap$com$android$keyguard$KeyguardSecurityModel$SecurityMode[securityMode.ordinal()];
        if (i != 1) {
            if (i != 2) {
                if (i != 3) {
                    if (i != 4) {
                        if (i != 5) {
                            return 0;
                        }
                        return R$layout.keyguard_sim_puk_view;
                    }
                    return R$layout.keyguard_sim_pin_view;
                }
                return R$layout.keyguard_password_view;
            }
            return R$layout.keyguard_pin_view;
        }
        return R$layout.keyguard_pattern_view;
    }

    /* JADX DEBUG: Failed to insert an additional move for type inference into block B:27:0x002d */
    @VisibleForTesting
    public KeyguardInputViewController<KeyguardInputView> getSecurityView(KeyguardSecurityModel.SecurityMode securityMode, KeyguardSecurityCallback keyguardSecurityCallback) {
        KeyguardInputViewController keyguardInputViewController;
        Iterator<KeyguardInputViewController<KeyguardInputView>> it = this.mChildren.iterator();
        while (true) {
            if (!it.hasNext()) {
                keyguardInputViewController = null;
                break;
            }
            keyguardInputViewController = it.next();
            if (keyguardInputViewController.getSecurityMode() == securityMode) {
                break;
            }
        }
        KeyguardInputViewController keyguardInputViewController2 = keyguardInputViewController;
        if (keyguardInputViewController == null) {
            keyguardInputViewController2 = keyguardInputViewController;
            if (securityMode != KeyguardSecurityModel.SecurityMode.None) {
                keyguardInputViewController2 = keyguardInputViewController;
                if (securityMode != KeyguardSecurityModel.SecurityMode.Invalid) {
                    int layoutIdFor = getLayoutIdFor(securityMode);
                    keyguardInputViewController2 = keyguardInputViewController;
                    if (layoutIdFor != 0) {
                        if (DEBUG) {
                            Log.v("KeyguardSecurityView", "inflating id = " + layoutIdFor);
                        }
                        KeyguardInputView keyguardInputView = (KeyguardInputView) this.mLayoutInflater.inflate(layoutIdFor, (ViewGroup) ((ViewController) this).mView, false);
                        ((KeyguardSecurityViewFlipper) ((ViewController) this).mView).addView(keyguardInputView);
                        KeyguardInputViewController create = this.mKeyguardSecurityViewControllerFactory.create(keyguardInputView, securityMode, keyguardSecurityCallback);
                        create.init();
                        this.mChildren.add(create);
                        keyguardInputViewController2 = create;
                    }
                }
            }
        }
        KeyguardInputViewController keyguardInputViewController3 = keyguardInputViewController2;
        if (keyguardInputViewController2 == null) {
            keyguardInputViewController3 = new NullKeyguardInputViewController(securityMode, keyguardSecurityCallback, this.mEmergencyButtonControllerFactory.create(null));
        }
        return keyguardInputViewController3;
    }

    public void onViewAttached() {
    }

    public void onViewDetached() {
    }

    public void reset() {
        for (KeyguardInputViewController<KeyguardInputView> keyguardInputViewController : this.mChildren) {
            keyguardInputViewController.reset();
        }
    }

    public void show(KeyguardInputViewController<KeyguardInputView> keyguardInputViewController) {
        int indexIn = keyguardInputViewController.getIndexIn((KeyguardSecurityViewFlipper) ((ViewController) this).mView);
        if (indexIn != -1) {
            ((KeyguardSecurityViewFlipper) ((ViewController) this).mView).setDisplayedChild(indexIn);
        }
    }
}