package com.android.systemui.keyguard.data.quickaffordance;

import android.app.AlertDialog;
import android.content.Intent;
import com.android.systemui.animation.Expandable;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.keyguard.shared.quickaffordance.ActivationState;
import java.util.List;
import kotlin.coroutines.Continuation;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.flow.Flow;

/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/data/quickaffordance/KeyguardQuickAffordanceConfig.class */
public interface KeyguardQuickAffordanceConfig {
    public static final Companion Companion = Companion.$$INSTANCE;

    /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/data/quickaffordance/KeyguardQuickAffordanceConfig$Companion.class */
    public static final class Companion {
        public static final /* synthetic */ Companion $$INSTANCE = new Companion();

        /* JADX WARN: Code restructure failed: missing block: B:15:0x0033, code lost:
            if (r4.length() == 0) goto L19;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final String componentName(String str, String str2) {
            boolean z;
            String str3;
            if (str2 == null || str2.length() == 0) {
                str3 = null;
            } else {
                if (str != null) {
                    z = false;
                }
                z = true;
                str3 = str2;
                if (!z) {
                    str3 = str + "/" + str2;
                }
            }
            return str3;
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/data/quickaffordance/KeyguardQuickAffordanceConfig$DefaultImpls.class */
    public static final class DefaultImpls {
        public static Object getPickerScreenState(KeyguardQuickAffordanceConfig keyguardQuickAffordanceConfig, Continuation<? super PickerScreenState> continuation) {
            return PickerScreenState.Default.INSTANCE;
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/data/quickaffordance/KeyguardQuickAffordanceConfig$LockScreenState.class */
    public static abstract class LockScreenState {

        /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/data/quickaffordance/KeyguardQuickAffordanceConfig$LockScreenState$Hidden.class */
        public static final class Hidden extends LockScreenState {
            public static final Hidden INSTANCE = new Hidden();

            public Hidden() {
                super(null);
            }
        }

        /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/data/quickaffordance/KeyguardQuickAffordanceConfig$LockScreenState$Visible.class */
        public static final class Visible extends LockScreenState {
            public final ActivationState activationState;
            public final Icon icon;

            public Visible(Icon icon, ActivationState activationState) {
                super(null);
                this.icon = icon;
                this.activationState = activationState;
            }

            public /* synthetic */ Visible(Icon icon, ActivationState activationState, int i, DefaultConstructorMarker defaultConstructorMarker) {
                this(icon, (i & 2) != 0 ? ActivationState.NotSupported.INSTANCE : activationState);
            }

            public boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if (obj instanceof Visible) {
                    Visible visible = (Visible) obj;
                    return Intrinsics.areEqual(this.icon, visible.icon) && Intrinsics.areEqual(this.activationState, visible.activationState);
                }
                return false;
            }

            public final ActivationState getActivationState() {
                return this.activationState;
            }

            public final Icon getIcon() {
                return this.icon;
            }

            public int hashCode() {
                return (this.icon.hashCode() * 31) + this.activationState.hashCode();
            }

            public String toString() {
                Icon icon = this.icon;
                ActivationState activationState = this.activationState;
                return "Visible(icon=" + icon + ", activationState=" + activationState + ")";
            }
        }

        public LockScreenState() {
        }

        public /* synthetic */ LockScreenState(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/data/quickaffordance/KeyguardQuickAffordanceConfig$OnTriggeredResult.class */
    public static abstract class OnTriggeredResult {

        /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/data/quickaffordance/KeyguardQuickAffordanceConfig$OnTriggeredResult$Handled.class */
        public static final class Handled extends OnTriggeredResult {
            public static final Handled INSTANCE = new Handled();

            public Handled() {
                super(null);
            }
        }

        /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/data/quickaffordance/KeyguardQuickAffordanceConfig$OnTriggeredResult$ShowDialog.class */
        public static final class ShowDialog extends OnTriggeredResult {
            public final AlertDialog dialog;
            public final Expandable expandable;

            public ShowDialog(AlertDialog alertDialog, Expandable expandable) {
                super(null);
                this.dialog = alertDialog;
                this.expandable = expandable;
            }

            public boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if (obj instanceof ShowDialog) {
                    ShowDialog showDialog = (ShowDialog) obj;
                    return Intrinsics.areEqual(this.dialog, showDialog.dialog) && Intrinsics.areEqual(this.expandable, showDialog.expandable);
                }
                return false;
            }

            public final AlertDialog getDialog() {
                return this.dialog;
            }

            public final Expandable getExpandable() {
                return this.expandable;
            }

            public int hashCode() {
                int hashCode = this.dialog.hashCode();
                Expandable expandable = this.expandable;
                return (hashCode * 31) + (expandable == null ? 0 : expandable.hashCode());
            }

            public String toString() {
                AlertDialog alertDialog = this.dialog;
                Expandable expandable = this.expandable;
                return "ShowDialog(dialog=" + alertDialog + ", expandable=" + expandable + ")";
            }
        }

        /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/data/quickaffordance/KeyguardQuickAffordanceConfig$OnTriggeredResult$StartActivity.class */
        public static final class StartActivity extends OnTriggeredResult {
            public final boolean canShowWhileLocked;
            public final Intent intent;

            public StartActivity(Intent intent, boolean z) {
                super(null);
                this.intent = intent;
                this.canShowWhileLocked = z;
            }

            public boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if (obj instanceof StartActivity) {
                    StartActivity startActivity = (StartActivity) obj;
                    return Intrinsics.areEqual(this.intent, startActivity.intent) && this.canShowWhileLocked == startActivity.canShowWhileLocked;
                }
                return false;
            }

            public final boolean getCanShowWhileLocked() {
                return this.canShowWhileLocked;
            }

            public final Intent getIntent() {
                return this.intent;
            }

            public int hashCode() {
                int hashCode = this.intent.hashCode();
                boolean z = this.canShowWhileLocked;
                int i = z ? 1 : 0;
                if (z) {
                    i = 1;
                }
                return (hashCode * 31) + i;
            }

            public String toString() {
                Intent intent = this.intent;
                boolean z = this.canShowWhileLocked;
                return "StartActivity(intent=" + intent + ", canShowWhileLocked=" + z + ")";
            }
        }

        public OnTriggeredResult() {
        }

        public /* synthetic */ OnTriggeredResult(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/data/quickaffordance/KeyguardQuickAffordanceConfig$PickerScreenState.class */
    public static abstract class PickerScreenState {

        /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/data/quickaffordance/KeyguardQuickAffordanceConfig$PickerScreenState$Default.class */
        public static final class Default extends PickerScreenState {
            public static final Default INSTANCE = new Default();

            public Default() {
                super(null);
            }
        }

        /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/data/quickaffordance/KeyguardQuickAffordanceConfig$PickerScreenState$Disabled.class */
        public static final class Disabled extends PickerScreenState {
            public final String actionComponentName;
            public final String actionText;
            public final List<String> instructions;

            /* JADX WARN: Code restructure failed: missing block: B:21:0x0066, code lost:
                if ((r7 == null || r7.length() == 0) == false) goto L24;
             */
            /* JADX WARN: Removed duplicated region for block: B:44:0x00b4 A[RETURN] */
            /* JADX WARN: Removed duplicated region for block: B:45:0x00b5  */
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public Disabled(List<String> list, String str, String str2) {
                super(null);
                boolean z;
                this.instructions = list;
                this.actionText = str;
                this.actionComponentName = str2;
                if (!(!list.isEmpty())) {
                    throw new IllegalStateException("Instructions must not be empty!".toString());
                }
                if (str == null || str.length() == 0) {
                    z = true;
                }
                if (!(str == null || str.length() == 0)) {
                    if (!(str2 == null || str2.length() == 0)) {
                        z = true;
                        if (z) {
                            throw new IllegalStateException("actionText and actionComponentName must either both be null/empty or both be non-empty!".toString());
                        }
                        return;
                    }
                }
                z = false;
                if (z) {
                }
            }

            public /* synthetic */ Disabled(List list, String str, String str2, int i, DefaultConstructorMarker defaultConstructorMarker) {
                this(list, (i & 2) != 0 ? null : str, (i & 4) != 0 ? null : str2);
            }

            public boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if (obj instanceof Disabled) {
                    Disabled disabled = (Disabled) obj;
                    return Intrinsics.areEqual(this.instructions, disabled.instructions) && Intrinsics.areEqual(this.actionText, disabled.actionText) && Intrinsics.areEqual(this.actionComponentName, disabled.actionComponentName);
                }
                return false;
            }

            public final String getActionComponentName() {
                return this.actionComponentName;
            }

            public final String getActionText() {
                return this.actionText;
            }

            public final List<String> getInstructions() {
                return this.instructions;
            }

            public int hashCode() {
                int hashCode = this.instructions.hashCode();
                String str = this.actionText;
                int i = 0;
                int hashCode2 = str == null ? 0 : str.hashCode();
                String str2 = this.actionComponentName;
                if (str2 != null) {
                    i = str2.hashCode();
                }
                return (((hashCode * 31) + hashCode2) * 31) + i;
            }

            public String toString() {
                List<String> list = this.instructions;
                String str = this.actionText;
                String str2 = this.actionComponentName;
                return "Disabled(instructions=" + list + ", actionText=" + str + ", actionComponentName=" + str2 + ")";
            }
        }

        /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/data/quickaffordance/KeyguardQuickAffordanceConfig$PickerScreenState$UnavailableOnDevice.class */
        public static final class UnavailableOnDevice extends PickerScreenState {
            public static final UnavailableOnDevice INSTANCE = new UnavailableOnDevice();

            public UnavailableOnDevice() {
                super(null);
            }
        }

        public PickerScreenState() {
        }

        public /* synthetic */ PickerScreenState(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    String getKey();

    Flow<LockScreenState> getLockScreenState();

    int getPickerIconResourceId();

    String getPickerName();

    Object getPickerScreenState(Continuation<? super PickerScreenState> continuation);

    OnTriggeredResult onTriggered(Expandable expandable);
}