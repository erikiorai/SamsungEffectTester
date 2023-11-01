package com.android.systemui.assist;

import android.app.ActivityOptions;
import android.app.SearchManager;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.metrics.LogMaker;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.Log;
import androidx.recyclerview.widget.RecyclerView;
import com.android.internal.app.AssistUtils;
import com.android.internal.app.IVoiceInteractionSessionListener;
import com.android.internal.app.IVoiceInteractionSessionShowCallback;
import com.android.internal.logging.MetricsLogger;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.DejankUtils;
import com.android.systemui.R$anim;
import com.android.systemui.assist.ui.DefaultUiController;
import com.android.systemui.model.SysUiState;
import com.android.systemui.recents.OverviewProxyService;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import dagger.Lazy;
import java.util.function.Supplier;

/* loaded from: mainsysui33.jar:com/android/systemui/assist/AssistManager.class */
public class AssistManager {
    public static final String ACTION_KEY = "action";
    public static final String CONSTRAINED_KEY = "should_constrain";
    public static final int DISMISS_REASON_BACK = 3;
    public static final int DISMISS_REASON_INVOCATION_CANCELLED = 1;
    public static final int DISMISS_REASON_TAP = 2;
    public static final int DISMISS_REASON_TIMEOUT = 4;
    private static final String INVOCATION_PHONE_STATE_KEY = "invocation_phone_state";
    private static final String INVOCATION_TIME_MS_KEY = "invocation_time_ms";
    public static final int INVOCATION_TYPE_GESTURE = 1;
    public static final int INVOCATION_TYPE_HOME_BUTTON_LONG_PRESS = 5;
    public static final String INVOCATION_TYPE_KEY = "invocation_type";
    public static final int INVOCATION_TYPE_OTHER = 2;
    public static final int INVOCATION_TYPE_POWER_BUTTON_LONG_PRESS = 6;
    public static final int INVOCATION_TYPE_QUICK_SEARCH_BAR = 4;
    public static final int INVOCATION_TYPE_UNKNOWN = 0;
    public static final int INVOCATION_TYPE_VOICE = 3;
    public static final String SET_ASSIST_GESTURE_CONSTRAINED_ACTION = "set_assist_gesture_constrained";
    private static final String TAG = "AssistManager";
    private static final long TIMEOUT_ACTIVITY = 1000;
    private static final long TIMEOUT_SERVICE = 2500;
    private static final boolean VERBOSE = false;
    private final AssistDisclosure mAssistDisclosure;
    public final AssistLogger mAssistLogger;
    public final AssistUtils mAssistUtils;
    private final CommandQueue mCommandQueue;
    public final Context mContext;
    private final DeviceProvisionedController mDeviceProvisionedController;
    private final PhoneStateMonitor mPhoneStateMonitor;
    public final Lazy<SysUiState> mSysUiState;
    private final UiController mUiController;

    /* loaded from: mainsysui33.jar:com/android/systemui/assist/AssistManager$UiController.class */
    public interface UiController {
        void hide();

        void onGestureCompletion(float f);

        void onInvocationProgress(int i, float f);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.assist.AssistManager$$ExternalSyntheticLambda0.get():java.lang.Object] */
    public static /* synthetic */ Boolean $r8$lambda$74FZmt9WIQN_bFpNUS0L6JbASyk(AssistManager assistManager) {
        return assistManager.lambda$canVoiceAssistBeLaunchedFromKeyguard$0();
    }

    public AssistManager(DeviceProvisionedController deviceProvisionedController, Context context, AssistUtils assistUtils, CommandQueue commandQueue, PhoneStateMonitor phoneStateMonitor, OverviewProxyService overviewProxyService, Lazy<SysUiState> lazy, DefaultUiController defaultUiController, AssistLogger assistLogger, Handler handler) {
        this.mContext = context;
        this.mDeviceProvisionedController = deviceProvisionedController;
        this.mCommandQueue = commandQueue;
        this.mAssistUtils = assistUtils;
        this.mAssistDisclosure = new AssistDisclosure(context, handler);
        this.mPhoneStateMonitor = phoneStateMonitor;
        this.mAssistLogger = assistLogger;
        registerVoiceInteractionSessionListener();
        this.mUiController = defaultUiController;
        this.mSysUiState = lazy;
        overviewProxyService.addCallback(new OverviewProxyService.OverviewProxyListener() { // from class: com.android.systemui.assist.AssistManager.1
            {
                AssistManager.this = this;
            }

            @Override // com.android.systemui.recents.OverviewProxyService.OverviewProxyListener
            public void onAssistantGestureCompletion(float f) {
                AssistManager.this.onGestureCompletion(f);
            }

            @Override // com.android.systemui.recents.OverviewProxyService.OverviewProxyListener
            public void onAssistantProgress(float f) {
                AssistManager.this.onInvocationProgress(1, f);
            }
        });
    }

    private ComponentName getAssistInfo() {
        return getAssistInfoForUser(KeyguardUpdateMonitor.getCurrentUser());
    }

    private boolean isVoiceSessionRunning() {
        return this.mAssistUtils.isSessionRunning();
    }

    public /* synthetic */ Boolean lambda$canVoiceAssistBeLaunchedFromKeyguard$0() {
        return Boolean.valueOf(this.mAssistUtils.activeServiceSupportsLaunchFromKeyguard());
    }

    private void startAssistActivity(Bundle bundle, ComponentName componentName) {
        final Intent assistIntent;
        if (this.mDeviceProvisionedController.isDeviceProvisioned()) {
            boolean z = false;
            this.mCommandQueue.animateCollapsePanels(3, false);
            if (Settings.Secure.getIntForUser(this.mContext.getContentResolver(), "assist_structure_enabled", 1, -2) != 0) {
                z = true;
            }
            SearchManager searchManager = (SearchManager) this.mContext.getSystemService("search");
            if (searchManager == null || (assistIntent = searchManager.getAssistIntent(z)) == null) {
                return;
            }
            assistIntent.setComponent(componentName);
            assistIntent.putExtras(bundle);
            if (z && AssistUtils.isDisclosureEnabled(this.mContext)) {
                showDisclosure();
            }
            try {
                final ActivityOptions makeCustomAnimation = ActivityOptions.makeCustomAnimation(this.mContext, R$anim.search_launch_enter, R$anim.search_launch_exit);
                assistIntent.addFlags(268435456);
                AsyncTask.execute(new Runnable() { // from class: com.android.systemui.assist.AssistManager.3
                    {
                        AssistManager.this = this;
                    }

                    @Override // java.lang.Runnable
                    public void run() {
                        AssistManager.this.mContext.startActivityAsUser(assistIntent, makeCustomAnimation.toBundle(), new UserHandle(-2));
                    }
                });
            } catch (ActivityNotFoundException e) {
                Log.w(TAG, "Activity not found for " + assistIntent.getAction());
            }
        }
    }

    private void startAssistInternal(Bundle bundle, ComponentName componentName, boolean z) {
        if (z) {
            startVoiceInteractor(bundle);
        } else {
            startAssistActivity(bundle, componentName);
        }
    }

    private void startVoiceInteractor(Bundle bundle) {
        this.mAssistUtils.showSessionForActiveService(bundle, 4, (IVoiceInteractionSessionShowCallback) null, (IBinder) null);
    }

    public boolean canVoiceAssistBeLaunchedFromKeyguard() {
        return ((Boolean) DejankUtils.whitelistIpcs(new Supplier() { // from class: com.android.systemui.assist.AssistManager$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final Object get() {
                return AssistManager.$r8$lambda$74FZmt9WIQN_bFpNUS0L6JbASyk(AssistManager.this);
            }
        })).booleanValue();
    }

    public ComponentName getAssistInfoForUser(int i) {
        return this.mAssistUtils.getAssistComponentForUser(i);
    }

    public ComponentName getVoiceInteractorComponentName() {
        return this.mAssistUtils.getActiveServiceComponentName();
    }

    public void hideAssist() {
        this.mAssistUtils.hideCurrentSession();
    }

    public void launchVoiceAssistFromKeyguard() {
        this.mAssistUtils.launchVoiceAssistFromKeyguard();
    }

    public void logStartAssistLegacy(int i, int i2) {
        MetricsLogger.action(new LogMaker(1716).setType(1).setSubtype(toLoggingSubType(i, i2)));
    }

    public void onGestureCompletion(float f) {
        this.mUiController.onGestureCompletion(f);
    }

    public void onInvocationProgress(int i, float f) {
        this.mUiController.onInvocationProgress(i, f);
    }

    public void onLockscreenShown() {
        AsyncTask.execute(new Runnable() { // from class: com.android.systemui.assist.AssistManager.4
            {
                AssistManager.this = this;
            }

            @Override // java.lang.Runnable
            public void run() {
                AssistManager.this.mAssistUtils.onLockscreenShown();
            }
        });
    }

    public void registerVoiceInteractionSessionListener() {
        this.mAssistUtils.registerVoiceInteractionSessionListener(new IVoiceInteractionSessionListener.Stub() { // from class: com.android.systemui.assist.AssistManager.2
            {
                AssistManager.this = this;
            }

            public void onSetUiHints(Bundle bundle) {
                if (AssistManager.SET_ASSIST_GESTURE_CONSTRAINED_ACTION.equals(bundle.getString(AssistManager.ACTION_KEY))) {
                    ((SysUiState) AssistManager.this.mSysUiState.get()).setFlag(RecyclerView.ViewHolder.FLAG_BOUNCED_FROM_HIDDEN_LIST, bundle.getBoolean(AssistManager.CONSTRAINED_KEY, false)).commitUpdate(0);
                }
            }

            public void onVoiceSessionHidden() throws RemoteException {
                AssistManager.this.mAssistLogger.reportAssistantSessionEvent(AssistantSessionEvent.ASSISTANT_SESSION_CLOSE);
            }

            public void onVoiceSessionShown() throws RemoteException {
                AssistManager.this.mAssistLogger.reportAssistantSessionEvent(AssistantSessionEvent.ASSISTANT_SESSION_UPDATE);
            }

            public void onVoiceSessionWindowVisibilityChanged(boolean z) throws RemoteException {
            }
        });
    }

    public void showDisclosure() {
        this.mAssistDisclosure.postShow();
    }

    public void startAssist(Bundle bundle) {
        ComponentName assistInfo = getAssistInfo();
        if (assistInfo == null) {
            return;
        }
        boolean equals = assistInfo.equals(getVoiceInteractorComponentName());
        Bundle bundle2 = bundle;
        if (bundle == null) {
            bundle2 = new Bundle();
        }
        int i = bundle2.getInt(INVOCATION_TYPE_KEY, 0);
        int phoneState = this.mPhoneStateMonitor.getPhoneState();
        bundle2.putInt(INVOCATION_PHONE_STATE_KEY, phoneState);
        bundle2.putLong(INVOCATION_TIME_MS_KEY, SystemClock.elapsedRealtime());
        this.mAssistLogger.reportAssistantInvocationEventFromLegacy(i, true, assistInfo, Integer.valueOf(phoneState));
        logStartAssistLegacy(i, phoneState);
        startAssistInternal(bundle2, assistInfo, equals);
    }

    public int toLoggingSubType(int i) {
        return toLoggingSubType(i, this.mPhoneStateMonitor.getPhoneState());
    }

    public final int toLoggingSubType(int i, int i2) {
        return (i << 1) | 0 | (i2 << 4);
    }
}