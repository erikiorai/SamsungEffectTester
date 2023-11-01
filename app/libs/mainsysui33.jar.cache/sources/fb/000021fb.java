package com.android.systemui.qs.footer.data.repository;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.UserManager;
import com.android.systemui.R$bool;
import com.android.systemui.common.coroutine.ConflatedCallbackFlow;
import com.android.systemui.qs.footer.data.model.UserSwitcherStatusModel;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.statusbar.policy.UserInfoController;
import com.android.systemui.statusbar.policy.UserSwitcherController;
import com.android.systemui.util.settings.GlobalSettings;
import kotlin.Pair;
import kotlin.coroutines.Continuation;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/footer/data/repository/UserSwitcherRepositoryImpl.class */
public final class UserSwitcherRepositoryImpl implements UserSwitcherRepository {
    public static final Companion Companion = new Companion(null);
    public final CoroutineDispatcher bgDispatcher;
    public final Handler bgHandler;
    public final Context context;
    public final Flow<Pair<Drawable, Boolean>> currentUserInfo;
    public final Flow<String> currentUserName;
    public final GlobalSettings globalSetting;
    public final Flow<Boolean> isEnabled;
    public final boolean showUserSwitcherForSingleUser;
    public final UserInfoController userInfoController;
    public final UserManager userManager;
    public final UserSwitcherController userSwitcherController;
    public final Flow<UserSwitcherStatusModel> userSwitcherStatus;
    public final UserTracker userTracker;

    /* loaded from: mainsysui33.jar:com/android/systemui/qs/footer/data/repository/UserSwitcherRepositoryImpl$Companion.class */
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public UserSwitcherRepositoryImpl(Context context, Handler handler, CoroutineDispatcher coroutineDispatcher, UserManager userManager, UserTracker userTracker, UserSwitcherController userSwitcherController, UserInfoController userInfoController, GlobalSettings globalSettings) {
        this.context = context;
        this.bgHandler = handler;
        this.bgDispatcher = coroutineDispatcher;
        this.userManager = userManager;
        this.userTracker = userTracker;
        this.userSwitcherController = userSwitcherController;
        this.userInfoController = userInfoController;
        this.globalSetting = globalSettings;
        this.showUserSwitcherForSingleUser = context.getResources().getBoolean(R$bool.qs_show_user_switcher_for_single_user);
        ConflatedCallbackFlow conflatedCallbackFlow = ConflatedCallbackFlow.INSTANCE;
        Flow<Boolean> conflatedCallbackFlow2 = conflatedCallbackFlow.conflatedCallbackFlow(new UserSwitcherRepositoryImpl$isEnabled$1(this, null));
        this.isEnabled = conflatedCallbackFlow2;
        this.currentUserName = conflatedCallbackFlow.conflatedCallbackFlow(new UserSwitcherRepositoryImpl$currentUserName$1(this, null));
        this.currentUserInfo = conflatedCallbackFlow.conflatedCallbackFlow(new UserSwitcherRepositoryImpl$currentUserInfo$1(this, null));
        this.userSwitcherStatus = FlowKt.distinctUntilChanged(FlowKt.transformLatest(conflatedCallbackFlow2, new UserSwitcherRepositoryImpl$special$$inlined$flatMapLatest$1(null, this)));
    }

    public final Object getCurrentUser(Continuation<? super String> continuation) {
        return BuildersKt.withContext(this.bgDispatcher, new UserSwitcherRepositoryImpl$getCurrentUser$2(this, null), continuation);
    }

    @Override // com.android.systemui.qs.footer.data.repository.UserSwitcherRepository
    public Flow<UserSwitcherStatusModel> getUserSwitcherStatus() {
        return this.userSwitcherStatus;
    }

    public final Object isGuestUser(Continuation<? super Boolean> continuation) {
        return BuildersKt.withContext(this.bgDispatcher, new UserSwitcherRepositoryImpl$isGuestUser$2(this, null), continuation);
    }

    public final Object isUserSwitcherEnabled(Continuation<? super Boolean> continuation) {
        return BuildersKt.withContext(this.bgDispatcher, new UserSwitcherRepositoryImpl$isUserSwitcherEnabled$2(this, null), continuation);
    }
}