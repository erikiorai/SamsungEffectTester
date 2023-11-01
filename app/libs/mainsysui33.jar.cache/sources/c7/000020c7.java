package com.android.systemui.qs;

import android.content.res.Configuration;
import android.view.MotionEvent;
import android.view.View;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.Flags;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.util.ViewController;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/QSContainerImplController.class */
public class QSContainerImplController extends ViewController<QSContainerImpl> {
    public final ConfigurationController mConfigurationController;
    public final ConfigurationController.ConfigurationListener mConfigurationListener;
    public final View.OnTouchListener mContainerTouchHandler;
    public final FalsingManager mFalsingManager;
    public final NonInterceptingScrollView mQSPanelContainer;
    public final QSPanelController mQsPanelController;
    public final QuickStatusBarHeaderController mQuickStatusBarHeaderController;

    public QSContainerImplController(QSContainerImpl qSContainerImpl, QSPanelController qSPanelController, QuickStatusBarHeaderController quickStatusBarHeaderController, ConfigurationController configurationController, FalsingManager falsingManager, FeatureFlags featureFlags) {
        super(qSContainerImpl);
        this.mConfigurationListener = new ConfigurationController.ConfigurationListener() { // from class: com.android.systemui.qs.QSContainerImplController.1
            public void onConfigChanged(Configuration configuration) {
                ((QSContainerImpl) ((ViewController) QSContainerImplController.this).mView).updateResources(QSContainerImplController.this.mQsPanelController, QSContainerImplController.this.mQuickStatusBarHeaderController);
            }
        };
        this.mContainerTouchHandler = new View.OnTouchListener() { // from class: com.android.systemui.qs.QSContainerImplController.2
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getActionMasked() == 1 && QSContainerImplController.this.mQSPanelContainer.isPreventingIntercept()) {
                    QSContainerImplController.this.mFalsingManager.isFalseTouch(17);
                    return false;
                }
                return false;
            }
        };
        this.mQsPanelController = qSPanelController;
        this.mQuickStatusBarHeaderController = quickStatusBarHeaderController;
        this.mConfigurationController = configurationController;
        this.mFalsingManager = falsingManager;
        this.mQSPanelContainer = ((QSContainerImpl) ((ViewController) this).mView).getQSPanelContainer();
        qSContainerImpl.setUseCombinedHeaders(featureFlags.isEnabled(Flags.COMBINED_QS_HEADERS));
    }

    public QSContainerImpl getView() {
        return (QSContainerImpl) ((ViewController) this).mView;
    }

    public void onInit() {
        this.mQuickStatusBarHeaderController.init();
    }

    public void onViewAttached() {
        ((QSContainerImpl) ((ViewController) this).mView).updateResources(this.mQsPanelController, this.mQuickStatusBarHeaderController);
        this.mConfigurationController.addCallback(this.mConfigurationListener);
        this.mQSPanelContainer.setOnTouchListener(this.mContainerTouchHandler);
    }

    public void onViewDetached() {
        this.mConfigurationController.removeCallback(this.mConfigurationListener);
        this.mQSPanelContainer.setOnTouchListener(null);
    }

    public void setListening(boolean z) {
        this.mQuickStatusBarHeaderController.setListening(z);
    }
}