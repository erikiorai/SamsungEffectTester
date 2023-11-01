package com.android.systemui.qs;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.android.systemui.R$id;
import com.android.systemui.R$string;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.util.ViewController;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/QSFooterViewController.class */
public class QSFooterViewController extends ViewController<QSFooterView> implements QSFooter {
    public final ActivityStarter mActivityStarter;
    public final TextView mBuildText;
    public final View mEditButton;
    public final FalsingManager mFalsingManager;
    public final PageIndicator mPageIndicator;
    public final QSPanelController mQsPanelController;
    public final UserTracker mUserTracker;

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.QSFooterViewController$$ExternalSyntheticLambda2.run():void] */
    /* renamed from: $r8$lambda$D7Rrsi6vzF0zfz-hIY_qwqaPMIM */
    public static /* synthetic */ void m3756$r8$lambda$D7Rrsi6vzF0zfzhIY_qwqaPMIM(QSFooterViewController qSFooterViewController, View view) {
        qSFooterViewController.lambda$onViewAttached$1(view);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.QSFooterViewController$$ExternalSyntheticLambda0.onLongClick(android.view.View):boolean] */
    public static /* synthetic */ boolean $r8$lambda$nBAXmu1jkTppRgFbgyMSyV023JU(QSFooterViewController qSFooterViewController, View view) {
        return qSFooterViewController.lambda$onViewAttached$0(view);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.QSFooterViewController$$ExternalSyntheticLambda1.onClick(android.view.View):void] */
    public static /* synthetic */ void $r8$lambda$o4IR4CU3eXFN9b72RjH9OlQFoeU(QSFooterViewController qSFooterViewController, View view) {
        qSFooterViewController.lambda$onViewAttached$2(view);
    }

    public QSFooterViewController(QSFooterView qSFooterView, UserTracker userTracker, FalsingManager falsingManager, ActivityStarter activityStarter, QSPanelController qSPanelController) {
        super(qSFooterView);
        this.mUserTracker = userTracker;
        this.mQsPanelController = qSPanelController;
        this.mFalsingManager = falsingManager;
        this.mActivityStarter = activityStarter;
        this.mBuildText = (TextView) ((QSFooterView) ((ViewController) this).mView).findViewById(R$id.build);
        this.mPageIndicator = (PageIndicator) ((QSFooterView) ((ViewController) this).mView).findViewById(R$id.footer_page_indicator);
        this.mEditButton = ((QSFooterView) ((ViewController) this).mView).findViewById(16908291);
    }

    public /* synthetic */ boolean lambda$onViewAttached$0(View view) {
        CharSequence text = this.mBuildText.getText();
        if (TextUtils.isEmpty(text)) {
            return false;
        }
        ((ClipboardManager) this.mUserTracker.getUserContext().getSystemService(ClipboardManager.class)).setPrimaryClip(ClipData.newPlainText(getResources().getString(R$string.build_number_clip_data_label), text));
        Toast.makeText(getContext(), R$string.build_number_copy_toast, 0).show();
        return true;
    }

    public /* synthetic */ void lambda$onViewAttached$1(View view) {
        this.mQsPanelController.showEdit(view);
    }

    public /* synthetic */ void lambda$onViewAttached$2(final View view) {
        if (this.mFalsingManager.isFalseTap(1)) {
            return;
        }
        this.mActivityStarter.postQSRunnableDismissingKeyguard(new Runnable() { // from class: com.android.systemui.qs.QSFooterViewController$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                QSFooterViewController.m3756$r8$lambda$D7Rrsi6vzF0zfzhIY_qwqaPMIM(QSFooterViewController.this, view);
            }
        });
    }

    @Override // com.android.systemui.qs.QSFooter
    public void disable(int i, int i2, boolean z) {
        ((QSFooterView) ((ViewController) this).mView).disable(i2);
    }

    public void onViewAttached() {
        this.mBuildText.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.android.systemui.qs.QSFooterViewController$$ExternalSyntheticLambda0
            @Override // android.view.View.OnLongClickListener
            public final boolean onLongClick(View view) {
                return QSFooterViewController.$r8$lambda$nBAXmu1jkTppRgFbgyMSyV023JU(QSFooterViewController.this, view);
            }
        });
        this.mEditButton.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.qs.QSFooterViewController$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                QSFooterViewController.$r8$lambda$o4IR4CU3eXFN9b72RjH9OlQFoeU(QSFooterViewController.this, view);
            }
        });
        this.mQsPanelController.setFooterPageIndicator(this.mPageIndicator);
        ((QSFooterView) ((ViewController) this).mView).updateEverything();
    }

    public void onViewDetached() {
    }

    @Override // com.android.systemui.qs.QSFooter
    public void setExpanded(boolean z) {
        ((QSFooterView) ((ViewController) this).mView).setExpanded(z);
    }

    @Override // com.android.systemui.qs.QSFooter
    public void setExpansion(float f) {
        ((QSFooterView) ((ViewController) this).mView).setExpansion(f);
    }

    @Override // com.android.systemui.qs.QSFooter
    public void setKeyguardShowing(boolean z) {
        ((QSFooterView) ((ViewController) this).mView).setKeyguardShowing();
    }

    @Override // com.android.systemui.qs.QSFooter
    public void setVisibility(int i) {
        ((QSFooterView) ((ViewController) this).mView).setVisibility(i);
        this.mEditButton.setClickable(i == 0);
    }
}