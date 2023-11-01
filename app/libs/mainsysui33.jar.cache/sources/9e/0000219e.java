package com.android.systemui.qs.dagger;

import android.content.Context;
import android.view.View;
import com.android.systemui.R$id;
import com.android.systemui.battery.BatteryMeterView;
import com.android.systemui.privacy.OngoingPrivacyChip;
import com.android.systemui.qs.QSContainerImpl;
import com.android.systemui.qs.QSFooter;
import com.android.systemui.qs.QSFooterView;
import com.android.systemui.qs.QSFooterViewController;
import com.android.systemui.qs.QSFragment;
import com.android.systemui.qs.QSPanel;
import com.android.systemui.qs.QuickQSPanel;
import com.android.systemui.qs.QuickStatusBarHeader;
import com.android.systemui.qs.customize.QSCustomizer;
import com.android.systemui.statusbar.phone.StatusIconContainer;
import com.android.systemui.util.Utils;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/dagger/QSFragmentModule.class */
public interface QSFragmentModule {
    static QSPanel provideQSPanel(View view) {
        return (QSPanel) view.findViewById(R$id.quick_settings_panel);
    }

    /* JADX DEBUG: Multi-variable search result rejected for r2v0, resolved type: com.android.systemui.qs.QSFragment */
    /* JADX WARN: Multi-variable type inference failed */
    static View provideRootView(QSFragment qSFragment) {
        return qSFragment.getView();
    }

    static Context provideThemedContext(View view) {
        return view.getContext();
    }

    static BatteryMeterView providesBatteryMeterView(QuickStatusBarHeader quickStatusBarHeader) {
        return (BatteryMeterView) quickStatusBarHeader.findViewById(R$id.batteryRemainingIcon);
    }

    static OngoingPrivacyChip providesPrivacyChip(QuickStatusBarHeader quickStatusBarHeader) {
        return (OngoingPrivacyChip) quickStatusBarHeader.findViewById(R$id.privacy_chip);
    }

    static QSContainerImpl providesQSContainerImpl(View view) {
        return (QSContainerImpl) view.findViewById(R$id.quick_settings_container);
    }

    static QSCustomizer providesQSCutomizer(View view) {
        return (QSCustomizer) view.findViewById(R$id.qs_customize);
    }

    static QSFooter providesQSFooter(QSFooterViewController qSFooterViewController) {
        qSFooterViewController.init();
        return qSFooterViewController;
    }

    static QSFooterView providesQSFooterView(View view) {
        return (QSFooterView) view.findViewById(R$id.qs_footer);
    }

    static boolean providesQSUsingCollapsedLandscapeMedia(Context context) {
        return Utils.useCollapsedMediaInLandscape(context.getResources());
    }

    static boolean providesQSUsingMediaPlayer(Context context) {
        return Utils.useQsMediaPlayer(context);
    }

    static QuickQSPanel providesQuickQSPanel(QuickStatusBarHeader quickStatusBarHeader) {
        return (QuickQSPanel) quickStatusBarHeader.findViewById(R$id.quick_qs_panel);
    }

    static QuickStatusBarHeader providesQuickStatusBarHeader(View view) {
        return (QuickStatusBarHeader) view.findViewById(R$id.header);
    }

    static StatusIconContainer providesStatusIconContainer(QuickStatusBarHeader quickStatusBarHeader) {
        return quickStatusBarHeader.findViewById(R$id.statusIcons);
    }
}