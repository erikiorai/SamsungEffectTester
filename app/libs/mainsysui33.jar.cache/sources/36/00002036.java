package com.android.systemui.privacy;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowInsets;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.settingslib.Utils;
import com.android.systemui.R$drawable;
import com.android.systemui.R$id;
import com.android.systemui.R$layout;
import com.android.systemui.R$string;
import com.android.systemui.R$style;
import com.android.systemui.privacy.PrivacyDialog;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: mainsysui33.jar:com/android/systemui/privacy/PrivacyDialog.class */
public final class PrivacyDialog extends SystemUIDialog {
    public final View.OnClickListener clickListener;
    public final List<WeakReference<OnDialogDismissed>> dismissListeners;
    public final AtomicBoolean dismissed;
    public final String enterpriseText;
    public final int iconColorSolid;
    public final List<PrivacyElement> list;
    public final String phonecall;
    public ViewGroup rootView;

    /* loaded from: mainsysui33.jar:com/android/systemui/privacy/PrivacyDialog$OnDialogDismissed.class */
    public interface OnDialogDismissed {
        void onDialogDismissed();
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/privacy/PrivacyDialog$PrivacyElement.class */
    public static final class PrivacyElement {
        public final boolean active;
        public final CharSequence applicationName;
        public final CharSequence attributionLabel;
        public final CharSequence attributionTag;
        public final StringBuilder builder;
        public final boolean enterprise;
        public final long lastActiveTimestamp;
        public final Intent navigationIntent;
        public final String packageName;
        public final CharSequence permGroupName;
        public final boolean phoneCall;
        public final CharSequence proxyLabel;
        public final PrivacyType type;
        public final int userId;

        public PrivacyElement(PrivacyType privacyType, String str, int i, CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3, CharSequence charSequence4, long j, boolean z, boolean z2, boolean z3, CharSequence charSequence5, Intent intent) {
            this.type = privacyType;
            this.packageName = str;
            this.userId = i;
            this.applicationName = charSequence;
            this.attributionTag = charSequence2;
            this.attributionLabel = charSequence3;
            this.proxyLabel = charSequence4;
            this.lastActiveTimestamp = j;
            this.active = z;
            this.enterprise = z2;
            this.phoneCall = z3;
            this.permGroupName = charSequence5;
            this.navigationIntent = intent;
            StringBuilder sb = new StringBuilder("PrivacyElement(");
            this.builder = sb;
            String logName = privacyType.getLogName();
            sb.append("type=" + logName);
            sb.append(", packageName=" + str);
            sb.append(", userId=" + i);
            sb.append(", appName=" + ((Object) charSequence));
            if (charSequence2 != null) {
                sb.append(", attributionTag=" + ((Object) charSequence2));
            }
            if (charSequence3 != null) {
                sb.append(", attributionLabel=" + ((Object) charSequence3));
            }
            if (charSequence4 != null) {
                sb.append(", proxyLabel=" + ((Object) charSequence4));
            }
            sb.append(", lastActive=" + j);
            if (z) {
                sb.append(", active");
            }
            if (z2) {
                sb.append(", enterprise");
            }
            if (z3) {
                sb.append(", phoneCall");
            }
            sb.append(", permGroupName=" + ((Object) charSequence5) + ")");
            if (intent != null) {
                sb.append(", navigationIntent=" + intent);
            }
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj instanceof PrivacyElement) {
                PrivacyElement privacyElement = (PrivacyElement) obj;
                return this.type == privacyElement.type && Intrinsics.areEqual(this.packageName, privacyElement.packageName) && this.userId == privacyElement.userId && Intrinsics.areEqual(this.applicationName, privacyElement.applicationName) && Intrinsics.areEqual(this.attributionTag, privacyElement.attributionTag) && Intrinsics.areEqual(this.attributionLabel, privacyElement.attributionLabel) && Intrinsics.areEqual(this.proxyLabel, privacyElement.proxyLabel) && this.lastActiveTimestamp == privacyElement.lastActiveTimestamp && this.active == privacyElement.active && this.enterprise == privacyElement.enterprise && this.phoneCall == privacyElement.phoneCall && Intrinsics.areEqual(this.permGroupName, privacyElement.permGroupName) && Intrinsics.areEqual(this.navigationIntent, privacyElement.navigationIntent);
            }
            return false;
        }

        public final boolean getActive() {
            return this.active;
        }

        public final CharSequence getApplicationName() {
            return this.applicationName;
        }

        public final CharSequence getAttributionLabel() {
            return this.attributionLabel;
        }

        public final CharSequence getAttributionTag() {
            return this.attributionTag;
        }

        public final boolean getEnterprise() {
            return this.enterprise;
        }

        public final long getLastActiveTimestamp() {
            return this.lastActiveTimestamp;
        }

        public final Intent getNavigationIntent() {
            return this.navigationIntent;
        }

        public final String getPackageName() {
            return this.packageName;
        }

        public final boolean getPhoneCall() {
            return this.phoneCall;
        }

        public final CharSequence getProxyLabel() {
            return this.proxyLabel;
        }

        public final PrivacyType getType() {
            return this.type;
        }

        public final int getUserId() {
            return this.userId;
        }

        public int hashCode() {
            int hashCode = this.type.hashCode();
            int hashCode2 = this.packageName.hashCode();
            int hashCode3 = Integer.hashCode(this.userId);
            int hashCode4 = this.applicationName.hashCode();
            CharSequence charSequence = this.attributionTag;
            int i = 0;
            int hashCode5 = charSequence == null ? 0 : charSequence.hashCode();
            CharSequence charSequence2 = this.attributionLabel;
            int hashCode6 = charSequence2 == null ? 0 : charSequence2.hashCode();
            CharSequence charSequence3 = this.proxyLabel;
            int hashCode7 = charSequence3 == null ? 0 : charSequence3.hashCode();
            int hashCode8 = Long.hashCode(this.lastActiveTimestamp);
            boolean z = this.active;
            int i2 = 1;
            int i3 = z ? 1 : 0;
            if (z) {
                i3 = 1;
            }
            boolean z2 = this.enterprise;
            int i4 = z2 ? 1 : 0;
            if (z2) {
                i4 = 1;
            }
            boolean z3 = this.phoneCall;
            if (!z3) {
                i2 = z3 ? 1 : 0;
            }
            int hashCode9 = this.permGroupName.hashCode();
            Intent intent = this.navigationIntent;
            if (intent != null) {
                i = intent.hashCode();
            }
            return (((((((((((((((((((((((hashCode * 31) + hashCode2) * 31) + hashCode3) * 31) + hashCode4) * 31) + hashCode5) * 31) + hashCode6) * 31) + hashCode7) * 31) + hashCode8) * 31) + i3) * 31) + i4) * 31) + i2) * 31) + hashCode9) * 31) + i;
        }

        public String toString() {
            return this.builder.toString();
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/privacy/PrivacyDialog$WhenMappings.class */
    public final /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[PrivacyType.values().length];
            try {
                iArr[PrivacyType.TYPE_LOCATION.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                iArr[PrivacyType.TYPE_CAMERA.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                iArr[PrivacyType.TYPE_MICROPHONE.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                iArr[PrivacyType.TYPE_MEDIA_PROJECTION.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for r5v0, resolved type: com.android.systemui.privacy.PrivacyDialog */
    /* JADX WARN: Multi-variable type inference failed */
    public PrivacyDialog(Context context, List<PrivacyElement> list, final Function4<? super String, ? super Integer, ? super CharSequence, ? super Intent, Unit> function4) {
        super(context, R$style.PrivacyDialog);
        this.list = list;
        this.dismissListeners = new ArrayList();
        this.dismissed = new AtomicBoolean(false);
        this.iconColorSolid = Utils.getColorAttrDefaultColor(getContext(), 16843827);
        String string = context.getString(R$string.ongoing_privacy_dialog_enterprise);
        this.enterpriseText = " " + string;
        this.phonecall = context.getString(R$string.ongoing_privacy_dialog_phonecall);
        this.clickListener = new View.OnClickListener() { // from class: com.android.systemui.privacy.PrivacyDialog$clickListener$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                Object tag = view.getTag();
                if (tag != null) {
                    PrivacyDialog.PrivacyElement privacyElement = (PrivacyDialog.PrivacyElement) tag;
                    function4.invoke(privacyElement.getPackageName(), Integer.valueOf(privacyElement.getUserId()), privacyElement.getAttributionTag(), privacyElement.getNavigationIntent());
                }
            }
        };
    }

    public final void addOnDismissListener(OnDialogDismissed onDialogDismissed) {
        if (this.dismissed.get()) {
            onDialogDismissed.onDialogDismissed();
        } else {
            this.dismissListeners.add(new WeakReference<>(onDialogDismissed));
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for r8v0, resolved type: com.android.systemui.privacy.PrivacyDialog */
    /* JADX WARN: Multi-variable type inference failed */
    public final View createView(PrivacyElement privacyElement) {
        LayoutInflater from = LayoutInflater.from(getContext());
        int i = R$layout.privacy_dialog_item;
        ViewGroup viewGroup = this.rootView;
        ViewGroup viewGroup2 = viewGroup;
        if (viewGroup == null) {
            viewGroup2 = null;
        }
        ViewGroup viewGroup3 = (ViewGroup) from.inflate(i, viewGroup2, false);
        LayerDrawable drawableForType = getDrawableForType(privacyElement.getType());
        int i2 = R$id.icon;
        drawableForType.findDrawableByLayerId(i2).setTint(this.iconColorSolid);
        ImageView imageView = (ImageView) viewGroup3.requireViewById(i2);
        imageView.setImageDrawable(drawableForType);
        imageView.setContentDescription(privacyElement.getType().getName(imageView.getContext()));
        int stringIdForState = getStringIdForState(privacyElement.getActive());
        String applicationName = privacyElement.getPhoneCall() ? this.phonecall : privacyElement.getApplicationName();
        CharSequence charSequence = applicationName;
        if (privacyElement.getEnterprise()) {
            charSequence = TextUtils.concat(applicationName, this.enterpriseText);
        }
        ((TextView) viewGroup3.requireViewById(R$id.text)).setText(getFinalText(getContext().getString(stringIdForState, charSequence), privacyElement.getAttributionLabel(), privacyElement.getProxyLabel()));
        if (privacyElement.getPhoneCall()) {
            viewGroup3.requireViewById(R$id.chevron).setVisibility(8);
        }
        viewGroup3.setTag(privacyElement);
        if (!privacyElement.getPhoneCall()) {
            viewGroup3.setOnClickListener(this.clickListener);
        }
        return viewGroup3;
    }

    /* JADX DEBUG: Multi-variable search result rejected for r3v0, resolved type: com.android.systemui.privacy.PrivacyDialog */
    /* JADX WARN: Multi-variable type inference failed */
    public final LayerDrawable getDrawableForType(PrivacyType privacyType) {
        int i;
        Context context = getContext();
        int i2 = WhenMappings.$EnumSwitchMapping$0[privacyType.ordinal()];
        if (i2 == 1) {
            i = R$drawable.privacy_item_circle_location;
        } else if (i2 == 2) {
            i = R$drawable.privacy_item_circle_camera;
        } else if (i2 == 3) {
            i = R$drawable.privacy_item_circle_microphone;
        } else if (i2 != 4) {
            throw new NoWhenBranchMatchedException();
        } else {
            i = R$drawable.privacy_item_circle_media_projection;
        }
        return (LayerDrawable) context.getDrawable(i);
    }

    /* JADX DEBUG: Multi-variable search result rejected for r7v0, resolved type: com.android.systemui.privacy.PrivacyDialog */
    /* JADX WARN: Multi-variable type inference failed */
    public final CharSequence getFinalText(CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3) {
        String string = (charSequence2 == null || charSequence3 == null) ? charSequence2 != null ? getContext().getString(R$string.ongoing_privacy_dialog_attribution_label, charSequence2) : charSequence3 != null ? getContext().getString(R$string.ongoing_privacy_dialog_attribution_text, charSequence3) : null : getContext().getString(R$string.ongoing_privacy_dialog_attribution_proxy_label, charSequence2, charSequence3);
        CharSequence charSequence4 = charSequence;
        if (string != null) {
            charSequence4 = TextUtils.concat(charSequence, " ", string);
        }
        return charSequence4;
    }

    public final int getStringIdForState(boolean z) {
        return z ? R$string.ongoing_privacy_dialog_using_op : R$string.ongoing_privacy_dialog_recent_op;
    }

    /* JADX DEBUG: Multi-variable search result rejected for r4v0, resolved type: com.android.systemui.privacy.PrivacyDialog */
    /* JADX WARN: Multi-variable type inference failed */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Window window = getWindow();
        if (window != null) {
            window.getAttributes().setFitInsetsTypes(window.getAttributes().getFitInsetsTypes() | WindowInsets.Type.statusBars());
            window.getAttributes().receiveInsetsIgnoringZOrder = true;
            window.setGravity(49);
        }
        setTitle(R$string.ongoing_privacy_dialog_a11y_title);
        setContentView(R$layout.privacy_dialog);
        this.rootView = (ViewGroup) requireViewById(R$id.root);
        for (PrivacyElement privacyElement : this.list) {
            ViewGroup viewGroup = this.rootView;
            ViewGroup viewGroup2 = viewGroup;
            if (viewGroup == null) {
                viewGroup2 = null;
            }
            viewGroup2.addView(createView(privacyElement));
        }
    }

    public void onStop() {
        super.onStop();
        this.dismissed.set(true);
        Iterator<WeakReference<OnDialogDismissed>> it = this.dismissListeners.iterator();
        while (it.hasNext()) {
            it.remove();
            OnDialogDismissed onDialogDismissed = it.next().get();
            if (onDialogDismissed != null) {
                onDialogDismissed.onDialogDismissed();
            }
        }
    }
}