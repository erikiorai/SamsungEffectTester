package com.android.systemui.qs.footer.domain.interactor;

import android.content.Context;
import com.android.systemui.animation.Expandable;
import com.android.systemui.globalactions.GlobalActionsDialogLite;
import com.android.systemui.qs.footer.data.model.UserSwitcherStatusModel;
import com.android.systemui.qs.footer.domain.model.SecurityButtonConfig;
import kotlin.Unit;
import kotlinx.coroutines.flow.Flow;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/footer/domain/interactor/FooterActionsInteractor.class */
public interface FooterActionsInteractor {
    Flow<Unit> getDeviceMonitoringDialogRequests();

    Flow<Integer> getForegroundServicesCount();

    Flow<Boolean> getHasNewForegroundServices();

    Flow<SecurityButtonConfig> getSecurityButtonConfig();

    Flow<UserSwitcherStatusModel> getUserSwitcherStatus();

    void showDeviceMonitoringDialog(Context context, Expandable expandable);

    void showForegroundServicesDialog(Expandable expandable);

    void showPowerMenuDialog(GlobalActionsDialogLite globalActionsDialogLite, Expandable expandable);

    void showSettings(Expandable expandable);

    void showUserSwitcher(Context context, Expandable expandable);
}