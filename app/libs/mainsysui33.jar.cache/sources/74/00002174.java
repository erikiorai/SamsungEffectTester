package com.android.systemui.qs.customize;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.res.Configuration;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import com.android.systemui.R$id;
import com.android.systemui.R$layout;
import com.android.systemui.R$string;
import com.android.systemui.plugins.qs.QS;
import com.android.systemui.plugins.qs.QSContainerController;
import com.android.systemui.qs.QSDetailClipper;
import com.android.systemui.qs.QSUtils;
import com.android.systemui.statusbar.phone.LightBarController;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/customize/QSCustomizer.class */
public class QSCustomizer extends LinearLayout {
    public boolean isShown;
    public final QSDetailClipper mClipper;
    public final Animator.AnimatorListener mCollapseAnimationListener;
    public boolean mCustomizing;
    public boolean mIsShowingNavBackdrop;
    public boolean mOpening;
    public QS mQs;
    public QSContainerController mQsContainerController;
    public final RecyclerView mRecyclerView;
    public final View mTransparentView;
    public int mX;
    public int mY;

    /* loaded from: mainsysui33.jar:com/android/systemui/qs/customize/QSCustomizer$ExpandAnimatorListener.class */
    public class ExpandAnimatorListener extends AnimatorListenerAdapter {
        public final TileAdapter mTileAdapter;

        public ExpandAnimatorListener(TileAdapter tileAdapter) {
            this.mTileAdapter = tileAdapter;
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationCancel(Animator animator) {
            QSCustomizer.this.mOpening = false;
            QSCustomizer.this.mQs.notifyCustomizeChanged();
            QSCustomizer.this.mQsContainerController.setCustomizerAnimating(false);
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            if (QSCustomizer.this.isShown) {
                QSCustomizer.this.setCustomizing(true);
            }
            QSCustomizer.this.mOpening = false;
            QSCustomizer.this.mQsContainerController.setCustomizerAnimating(false);
            QSCustomizer.this.mRecyclerView.setAdapter(this.mTileAdapter);
        }
    }

    public QSCustomizer(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mCollapseAnimationListener = new AnimatorListenerAdapter() { // from class: com.android.systemui.qs.customize.QSCustomizer.1
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animator) {
                if (!QSCustomizer.this.isShown) {
                    QSCustomizer.this.setVisibility(8);
                }
                QSCustomizer.this.mQsContainerController.setCustomizerAnimating(false);
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                if (!QSCustomizer.this.isShown) {
                    QSCustomizer.this.setVisibility(8);
                }
                QSCustomizer.this.mQsContainerController.setCustomizerAnimating(false);
            }
        };
        LayoutInflater.from(getContext()).inflate(R$layout.qs_customize_panel_content, this);
        this.mClipper = new QSDetailClipper(findViewById(R$id.customize_container));
        Toolbar toolbar = (Toolbar) findViewById(16908731);
        TypedValue typedValue = new TypedValue();
        ((LinearLayout) this).mContext.getTheme().resolveAttribute(16843531, typedValue, true);
        toolbar.setNavigationIcon(getResources().getDrawable(typedValue.resourceId, ((LinearLayout) this).mContext.getTheme()));
        toolbar.getMenu().add(0, 1, 0, 17041421).setShowAsAction(1);
        toolbar.setTitle(R$string.qs_edit);
        RecyclerView recyclerView = (RecyclerView) findViewById(16908298);
        this.mRecyclerView = recyclerView;
        this.mTransparentView = findViewById(R$id.customizer_transparent_view);
        DefaultItemAnimator defaultItemAnimator = new DefaultItemAnimator();
        defaultItemAnimator.setMoveDuration(150L);
        recyclerView.setItemAnimator(defaultItemAnimator);
        updateTransparentViewHeight();
    }

    public RecyclerView getRecyclerView() {
        return this.mRecyclerView;
    }

    public void hide(boolean z) {
        if (this.isShown) {
            this.isShown = false;
            this.mClipper.cancelAnimator();
            this.mOpening = false;
            long j = 0;
            if (z) {
                j = this.mClipper.animateCircularClip(this.mX, this.mY, false, this.mCollapseAnimationListener);
            } else {
                setVisibility(8);
            }
            this.mQsContainerController.setCustomizerAnimating(z);
            this.mQsContainerController.setCustomizerShowing(false, j);
        }
    }

    public boolean isCustomizing() {
        return this.mCustomizing || this.mOpening;
    }

    public boolean isOpening() {
        return this.mOpening;
    }

    @Override // android.view.View
    public boolean isShown() {
        return this.isShown;
    }

    public void setContainerController(QSContainerController qSContainerController) {
        this.mQsContainerController = qSContainerController;
    }

    public void setCustomizing(boolean z) {
        this.mCustomizing = z;
        this.mQs.notifyCustomizeChanged();
    }

    public void setEditLocation(int i, int i2) {
        int[] locationOnScreen = findViewById(R$id.customize_container).getLocationOnScreen();
        this.mX = i - locationOnScreen[0];
        this.mY = i2 - locationOnScreen[1];
    }

    public void setQs(QS qs) {
        this.mQs = qs;
    }

    public void show(int i, int i2, TileAdapter tileAdapter) {
        if (this.isShown) {
            return;
        }
        this.mRecyclerView.getLayoutManager().scrollToPosition(0);
        int[] locationOnScreen = findViewById(R$id.customize_container).getLocationOnScreen();
        this.mX = i - locationOnScreen[0];
        this.mY = i2 - locationOnScreen[1];
        this.isShown = true;
        this.mOpening = true;
        setVisibility(0);
        long animateCircularClip = this.mClipper.animateCircularClip(this.mX, this.mY, true, new ExpandAnimatorListener(tileAdapter));
        this.mQsContainerController.setCustomizerAnimating(true);
        this.mQsContainerController.setCustomizerShowing(true, animateCircularClip);
    }

    public void showImmediately() {
        if (this.isShown) {
            return;
        }
        this.mRecyclerView.getLayoutManager().scrollToPosition(0);
        setVisibility(0);
        this.mClipper.cancelAnimator();
        this.mClipper.showBackground();
        this.isShown = true;
        setCustomizing(true);
        this.mQsContainerController.setCustomizerAnimating(false);
        this.mQsContainerController.setCustomizerShowing(true);
    }

    public void updateNavBackDrop(Configuration configuration, LightBarController lightBarController) {
        View findViewById = findViewById(R$id.nav_bar_background);
        int i = 0;
        boolean z = configuration.smallestScreenWidthDp >= 600 || configuration.orientation != 2;
        this.mIsShowingNavBackdrop = z;
        if (findViewById != null) {
            if (!z) {
                i = 8;
            }
            findViewById.setVisibility(i);
        }
        updateNavColors(lightBarController);
    }

    public void updateNavColors(LightBarController lightBarController) {
        lightBarController.setQsCustomizing(this.mIsShowingNavBackdrop && this.isShown);
    }

    public void updateResources() {
        updateTransparentViewHeight();
        this.mRecyclerView.getAdapter().notifyItemChanged(0);
    }

    public final void updateTransparentViewHeight() {
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.mTransparentView.getLayoutParams();
        layoutParams.height = QSUtils.getQsHeaderSystemIconsAreaHeight(((LinearLayout) this).mContext);
        this.mTransparentView.setLayoutParams(layoutParams);
    }
}