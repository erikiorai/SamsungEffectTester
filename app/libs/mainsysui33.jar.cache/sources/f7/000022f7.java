package com.android.systemui.qs.tiles;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.service.quickaccesswallet.GetWalletCardsError;
import android.service.quickaccesswallet.GetWalletCardsResponse;
import android.service.quickaccesswallet.QuickAccessWalletClient;
import android.service.quickaccesswallet.WalletCard;
import android.util.Log;
import android.view.View;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.logging.MetricsLogger;
import com.android.systemui.R$drawable;
import com.android.systemui.R$string;
import com.android.systemui.animation.ActivityLaunchAnimator;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.qs.QSHost;
import com.android.systemui.qs.logging.QSLogger;
import com.android.systemui.qs.tileimpl.QSTileImpl;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.util.settings.SecureSettings;
import com.android.systemui.wallet.controller.QuickAccessWalletController;
import java.util.List;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/tiles/QuickAccessWalletTile.class */
public class QuickAccessWalletTile extends QSTileImpl<QSTile.State> {
    public final WalletCardRetriever mCardRetriever;
    @VisibleForTesting
    public Drawable mCardViewDrawable;
    public final QuickAccessWalletController mController;
    public boolean mIsWalletUpdating;
    public final KeyguardStateController mKeyguardStateController;
    public final CharSequence mLabel;
    public final PackageManager mPackageManager;
    public final SecureSettings mSecureSettings;
    public WalletCard mSelectedCard;

    /* loaded from: mainsysui33.jar:com/android/systemui/qs/tiles/QuickAccessWalletTile$WalletCardRetriever.class */
    public class WalletCardRetriever implements QuickAccessWalletClient.OnWalletCardsRetrievedCallback {
        public WalletCardRetriever() {
            QuickAccessWalletTile.this = r4;
        }

        public void onWalletCardRetrievalError(GetWalletCardsError getWalletCardsError) {
            QuickAccessWalletTile.this.mIsWalletUpdating = false;
            QuickAccessWalletTile quickAccessWalletTile = QuickAccessWalletTile.this;
            quickAccessWalletTile.mCardViewDrawable = null;
            quickAccessWalletTile.mSelectedCard = null;
            QuickAccessWalletTile.this.refreshState();
        }

        public void onWalletCardsRetrieved(GetWalletCardsResponse getWalletCardsResponse) {
            Log.i("QuickAccessWalletTile", "Successfully retrieved wallet cards.");
            QuickAccessWalletTile.this.mIsWalletUpdating = false;
            List<WalletCard> walletCards = getWalletCardsResponse.getWalletCards();
            if (walletCards.isEmpty()) {
                Log.d("QuickAccessWalletTile", "No wallet cards exist.");
                QuickAccessWalletTile quickAccessWalletTile = QuickAccessWalletTile.this;
                quickAccessWalletTile.mCardViewDrawable = null;
                quickAccessWalletTile.mSelectedCard = null;
                QuickAccessWalletTile.this.refreshState();
                return;
            }
            int selectedIndex = getWalletCardsResponse.getSelectedIndex();
            if (selectedIndex >= walletCards.size()) {
                Log.w("QuickAccessWalletTile", "Error retrieving cards: Invalid selected card index.");
                QuickAccessWalletTile.this.mSelectedCard = null;
                QuickAccessWalletTile.this.mCardViewDrawable = null;
                return;
            }
            QuickAccessWalletTile.this.mSelectedCard = walletCards.get(selectedIndex);
            QuickAccessWalletTile quickAccessWalletTile2 = QuickAccessWalletTile.this;
            quickAccessWalletTile2.mCardViewDrawable = quickAccessWalletTile2.mSelectedCard.getCardImage().loadDrawable(QuickAccessWalletTile.this.mContext);
            QuickAccessWalletTile.this.refreshState();
        }
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.tiles.QuickAccessWalletTile$$ExternalSyntheticLambda0.run():void] */
    public static /* synthetic */ void $r8$lambda$EuQEGzcUPDUHonbXRkk2TfbRaew(QuickAccessWalletTile quickAccessWalletTile, ActivityLaunchAnimator.Controller controller) {
        quickAccessWalletTile.lambda$handleClick$0(controller);
    }

    public QuickAccessWalletTile(QSHost qSHost, Looper looper, Handler handler, FalsingManager falsingManager, MetricsLogger metricsLogger, StatusBarStateController statusBarStateController, ActivityStarter activityStarter, QSLogger qSLogger, KeyguardStateController keyguardStateController, PackageManager packageManager, SecureSettings secureSettings, QuickAccessWalletController quickAccessWalletController) {
        super(qSHost, looper, handler, falsingManager, metricsLogger, statusBarStateController, activityStarter, qSLogger);
        this.mLabel = this.mContext.getString(R$string.wallet_title);
        this.mCardRetriever = new WalletCardRetriever();
        this.mIsWalletUpdating = true;
        this.mController = quickAccessWalletController;
        this.mKeyguardStateController = keyguardStateController;
        this.mPackageManager = packageManager;
        this.mSecureSettings = secureSettings;
    }

    public /* synthetic */ void lambda$handleClick$0(ActivityLaunchAnimator.Controller controller) {
        this.mController.startQuickAccessUiIntent(this.mActivityStarter, controller, this.mSelectedCard != null);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public Intent getLongClickIntent() {
        return null;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public int getMetricsCategory() {
        return 0;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public CharSequence getTileLabel() {
        CharSequence serviceLabel = this.mController.getWalletClient().getServiceLabel();
        CharSequence charSequence = serviceLabel;
        if (serviceLabel == null) {
            charSequence = this.mLabel;
        }
        return charSequence;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public void handleClick(View view) {
        final ActivityLaunchAnimator.Controller fromView = view == null ? null : ActivityLaunchAnimator.Controller.fromView(view, 32);
        this.mUiHandler.post(new Runnable() { // from class: com.android.systemui.qs.tiles.QuickAccessWalletTile$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                QuickAccessWalletTile.$r8$lambda$EuQEGzcUPDUHonbXRkk2TfbRaew(QuickAccessWalletTile.this, fromView);
            }
        });
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public void handleDestroy() {
        super.handleDestroy();
        this.mController.unregisterWalletChangeObservers(new QuickAccessWalletController.WalletChangeEvent[]{QuickAccessWalletController.WalletChangeEvent.DEFAULT_PAYMENT_APP_CHANGE});
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public void handleSetListening(boolean z) {
        super.handleSetListening(z);
        if (z) {
            this.mController.setupWalletChangeObservers(this.mCardRetriever, new QuickAccessWalletController.WalletChangeEvent[]{QuickAccessWalletController.WalletChangeEvent.DEFAULT_PAYMENT_APP_CHANGE});
            if (!this.mController.getWalletClient().isWalletServiceAvailable() || !this.mController.getWalletClient().isWalletFeatureAvailable()) {
                Log.i("QuickAccessWalletTile", "QAW service is unavailable, recreating the wallet client.");
                this.mController.reCreateWalletClient();
            }
            this.mController.queryWalletCards(this.mCardRetriever);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v50, types: [com.android.systemui.plugins.qs.QSTile$Icon] */
    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public void handleUpdateState(QSTile.State state, Object obj) {
        CharSequence serviceLabel = this.mController.getWalletClient().getServiceLabel();
        CharSequence charSequence = serviceLabel;
        if (serviceLabel == null) {
            charSequence = this.mLabel;
        }
        state.label = charSequence;
        state.contentDescription = charSequence;
        Drawable tileIcon = this.mController.getWalletClient().getTileIcon();
        state.icon = tileIcon == null ? QSTileImpl.ResourceIcon.get(R$drawable.ic_wallet_lockscreen) : new QSTileImpl.DrawableIcon(tileIcon);
        boolean isUnlocked = this.mKeyguardStateController.isUnlocked();
        int i = 1;
        if (!this.mController.getWalletClient().isWalletServiceAvailable() || !this.mController.getWalletClient().isWalletFeatureAvailable()) {
            state.state = 0;
            state.secondaryLabel = null;
            state.sideViewCustomDrawable = null;
            return;
        }
        WalletCard walletCard = this.mSelectedCard;
        if (walletCard != null) {
            if (!(!isUnlocked)) {
                i = 2;
            }
            state.state = i;
            state.secondaryLabel = walletCard.getContentDescription();
            state.sideViewCustomDrawable = this.mCardViewDrawable;
        } else {
            state.state = 1;
            state.secondaryLabel = this.mContext.getString(this.mIsWalletUpdating ? R$string.wallet_secondary_label_updating : R$string.wallet_secondary_label_no_card);
            state.sideViewCustomDrawable = null;
        }
        state.stateDescription = state.secondaryLabel;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public boolean isAvailable() {
        return (!this.mPackageManager.hasSystemFeature("android.hardware.nfc.hce") || this.mPackageManager.hasSystemFeature("org.chromium.arc") || this.mSecureSettings.getStringForUser("nfc_payment_default_component", -2) == null) ? false : true;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public QSTile.State newTileState() {
        QSTile.State state = new QSTile.State();
        state.handlesLongClick = false;
        return state;
    }
}