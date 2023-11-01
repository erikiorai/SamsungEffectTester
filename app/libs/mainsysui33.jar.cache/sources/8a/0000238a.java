package com.android.systemui.recents;

import android.content.Context;
import android.os.Handler;
import android.os.RemoteException;
import android.util.Log;
import com.android.systemui.globalactions.GlobalActionsDialogLite$ActionsDialogLite$$ExternalSyntheticLambda9;
import com.android.systemui.shared.recents.IOverviewProxy;
import com.android.systemui.statusbar.phone.CentralSurfaces;
import dagger.Lazy;
import java.util.Optional;

/* loaded from: mainsysui33.jar:com/android/systemui/recents/OverviewProxyRecentsImpl.class */
public class OverviewProxyRecentsImpl implements RecentsImplementation {
    public final Lazy<Optional<CentralSurfaces>> mCentralSurfacesOptionalLazy;
    public Handler mHandler;
    public final OverviewProxyService mOverviewProxyService;

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.recents.OverviewProxyRecentsImpl$$ExternalSyntheticLambda1.run():void] */
    public static /* synthetic */ void $r8$lambda$1mam8pOGjuwldef5yP49TwCUeIo(OverviewProxyRecentsImpl overviewProxyRecentsImpl, Runnable runnable) {
        overviewProxyRecentsImpl.lambda$toggleRecentApps$1(runnable);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.recents.OverviewProxyRecentsImpl$$ExternalSyntheticLambda0.run():void] */
    /* renamed from: $r8$lambda$7C70Yx-ac-MEOtydiJgffqcvlF8 */
    public static /* synthetic */ void m4102$r8$lambda$7C70YxacMEOtydiJgffqcvlF8(OverviewProxyRecentsImpl overviewProxyRecentsImpl) {
        overviewProxyRecentsImpl.lambda$toggleRecentApps$0();
    }

    public OverviewProxyRecentsImpl(Lazy<Optional<CentralSurfaces>> lazy, OverviewProxyService overviewProxyService) {
        this.mCentralSurfacesOptionalLazy = lazy;
        this.mOverviewProxyService = overviewProxyService;
    }

    public /* synthetic */ void lambda$toggleRecentApps$0() {
        try {
            if (this.mOverviewProxyService.getProxy() != null) {
                this.mOverviewProxyService.getProxy().onOverviewToggle();
                this.mOverviewProxyService.notifyToggleRecentApps();
            }
        } catch (RemoteException e) {
            Log.e("OverviewProxyRecentsImpl", "Cannot send toggle recents through proxy service.", e);
        }
    }

    public /* synthetic */ void lambda$toggleRecentApps$1(Runnable runnable) {
        this.mHandler.post(runnable);
    }

    @Override // com.android.systemui.recents.RecentsImplementation
    public void hideRecentApps(boolean z, boolean z2) {
        IOverviewProxy proxy = this.mOverviewProxyService.getProxy();
        if (proxy != null) {
            try {
                proxy.onOverviewHidden(z, z2);
            } catch (RemoteException e) {
                Log.e("OverviewProxyRecentsImpl", "Failed to send overview hide event to launcher.", e);
            }
        }
    }

    @Override // com.android.systemui.recents.RecentsImplementation
    public void onStart(Context context) {
        this.mHandler = new Handler();
    }

    @Override // com.android.systemui.recents.RecentsImplementation
    public void showRecentApps(boolean z) {
        IOverviewProxy proxy = this.mOverviewProxyService.getProxy();
        if (proxy != null) {
            try {
                proxy.onOverviewShown(z);
            } catch (RemoteException e) {
                Log.e("OverviewProxyRecentsImpl", "Failed to send overview show event to launcher.", e);
            }
        }
    }

    @Override // com.android.systemui.recents.RecentsImplementation
    public void toggleRecentApps() {
        if (this.mOverviewProxyService.getProxy() != null) {
            final Runnable runnable = new Runnable() { // from class: com.android.systemui.recents.OverviewProxyRecentsImpl$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    OverviewProxyRecentsImpl.m4102$r8$lambda$7C70YxacMEOtydiJgffqcvlF8(OverviewProxyRecentsImpl.this);
                }
            };
            Optional optional = (Optional) this.mCentralSurfacesOptionalLazy.get();
            if (((Boolean) optional.map(new GlobalActionsDialogLite$ActionsDialogLite$$ExternalSyntheticLambda9()).orElse(Boolean.FALSE)).booleanValue()) {
                ((CentralSurfaces) optional.get()).executeRunnableDismissingKeyguard(new Runnable() { // from class: com.android.systemui.recents.OverviewProxyRecentsImpl$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        OverviewProxyRecentsImpl.$r8$lambda$1mam8pOGjuwldef5yP49TwCUeIo(OverviewProxyRecentsImpl.this, runnable);
                    }
                }, (Runnable) null, true, false, true);
            } else {
                runnable.run();
            }
        }
    }
}