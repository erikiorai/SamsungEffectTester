package com.android.systemui.qs;

import android.content.Context;
import android.os.Handler;
import android.util.ArraySet;
import com.android.systemui.Prefs;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.qs.QSTileRevealController;
import com.android.systemui.qs.customize.QSCustomizerController;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/QSTileRevealController.class */
public class QSTileRevealController {
    public final Context mContext;
    public final PagedTileLayout mPagedTileLayout;
    public final QSPanelController mQSPanelController;
    public final QSCustomizerController mQsCustomizerController;
    public final ArraySet<String> mTilesToReveal = new ArraySet<>();
    public final Handler mHandler = new Handler();
    public final Runnable mRevealQsTiles = new AnonymousClass1();

    /* renamed from: com.android.systemui.qs.QSTileRevealController$1 */
    /* loaded from: mainsysui33.jar:com/android/systemui/qs/QSTileRevealController$1.class */
    public class AnonymousClass1 implements Runnable {
        /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.QSTileRevealController$1$$ExternalSyntheticLambda0.run():void] */
        public static /* synthetic */ void $r8$lambda$qFKk6nsJKac5F0IoIVnRcgqLwZ0(AnonymousClass1 anonymousClass1) {
            anonymousClass1.lambda$run$0();
        }

        public AnonymousClass1() {
            QSTileRevealController.this = r4;
        }

        public /* synthetic */ void lambda$run$0() {
            if (QSTileRevealController.this.mQSPanelController.isExpanded()) {
                QSTileRevealController qSTileRevealController = QSTileRevealController.this;
                qSTileRevealController.addTileSpecsToRevealed(qSTileRevealController.mTilesToReveal);
                QSTileRevealController.this.mTilesToReveal.clear();
            }
        }

        @Override // java.lang.Runnable
        public void run() {
            QSTileRevealController.this.mPagedTileLayout.startTileReveal(QSTileRevealController.this.mTilesToReveal, new Runnable() { // from class: com.android.systemui.qs.QSTileRevealController$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    QSTileRevealController.AnonymousClass1.$r8$lambda$qFKk6nsJKac5F0IoIVnRcgqLwZ0(QSTileRevealController.AnonymousClass1.this);
                }
            });
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/qs/QSTileRevealController$Factory.class */
    public static class Factory {
        public final Context mContext;
        public final QSCustomizerController mQsCustomizerController;

        public Factory(Context context, QSCustomizerController qSCustomizerController) {
            this.mContext = context;
            this.mQsCustomizerController = qSCustomizerController;
        }

        public QSTileRevealController create(QSPanelController qSPanelController, PagedTileLayout pagedTileLayout) {
            return new QSTileRevealController(this.mContext, qSPanelController, pagedTileLayout, this.mQsCustomizerController);
        }
    }

    public QSTileRevealController(Context context, QSPanelController qSPanelController, PagedTileLayout pagedTileLayout, QSCustomizerController qSCustomizerController) {
        this.mContext = context;
        this.mQSPanelController = qSPanelController;
        this.mPagedTileLayout = pagedTileLayout;
        this.mQsCustomizerController = qSCustomizerController;
    }

    public final void addTileSpecsToRevealed(ArraySet<String> arraySet) {
        ArraySet arraySet2 = new ArraySet(Prefs.getStringSet(this.mContext, "QsTileSpecsRevealed", Collections.EMPTY_SET));
        arraySet2.addAll((ArraySet) arraySet);
        Prefs.putStringSet(this.mContext, "QsTileSpecsRevealed", arraySet2);
    }

    public void setExpansion(float f) {
        if (f == 1.0f) {
            this.mHandler.postDelayed(this.mRevealQsTiles, 500L);
        } else {
            this.mHandler.removeCallbacks(this.mRevealQsTiles);
        }
    }

    public void updateRevealedTiles(Collection<QSTile> collection) {
        ArraySet<String> arraySet = new ArraySet<>();
        for (QSTile qSTile : collection) {
            arraySet.add(qSTile.getTileSpec());
        }
        Set<String> stringSet = Prefs.getStringSet(this.mContext, "QsTileSpecsRevealed", Collections.EMPTY_SET);
        if (stringSet.isEmpty() || this.mQsCustomizerController.isCustomizing()) {
            addTileSpecsToRevealed(arraySet);
            return;
        }
        arraySet.removeAll(stringSet);
        this.mTilesToReveal.addAll((ArraySet<? extends String>) arraySet);
    }
}