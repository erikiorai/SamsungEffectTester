package com.android.systemui.privacy.television;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.os.RemoteException;
import android.util.Log;
import android.view.IWindowManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.android.settingslib.widget.ActionBarShadowController;
import com.android.systemui.CoreStartable;
import com.android.systemui.R$color;
import com.android.systemui.R$dimen;
import com.android.systemui.R$id;
import com.android.systemui.R$integer;
import com.android.systemui.R$layout;
import com.android.systemui.R$string;
import com.android.systemui.privacy.PrivacyChipBuilder;
import com.android.systemui.privacy.PrivacyItem;
import com.android.systemui.privacy.PrivacyItemController;
import com.android.systemui.privacy.PrivacyType;
import com.android.systemui.privacy.television.PrivacyChipDrawable;
import com.android.systemui.qs.tiles.dialog.InternetDialogController;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;

/* loaded from: mainsysui33.jar:com/android/systemui/privacy/television/TvOngoingPrivacyChip.class */
public class TvOngoingPrivacyChip implements CoreStartable, PrivacyItemController.Callback, PrivacyChipDrawable.PrivacyChipDrawableListener {
    public boolean mAllIndicatorsEnabled;
    public final int mAnimationDurationMs;
    public ObjectAnimator mAnimator;
    public PrivacyChipDrawable mChipDrawable;
    public final Context mContext;
    public final IWindowManager mIWindowManager;
    public final int mIconMarginStart;
    public final int mIconSize;
    public LinearLayout mIconsContainer;
    public ViewGroup mIndicatorView;
    public boolean mIsRtl;
    public boolean mMicCameraIndicatorFlagEnabled;
    public final PrivacyItemController mPrivacyItemController;
    public boolean mViewAndWindowAdded;
    public final Rect[] mBounds = new Rect[4];
    public List<PrivacyItem> mPrivacyItems = Collections.emptyList();
    public final Handler mUiThreadHandler = new Handler(Looper.getMainLooper());
    public final Runnable mCollapseRunnable = new Runnable() { // from class: com.android.systemui.privacy.television.TvOngoingPrivacyChip$$ExternalSyntheticLambda0
        @Override // java.lang.Runnable
        public final void run() {
            TvOngoingPrivacyChip.m3697$r8$lambda$rYshBeBnuQSp72_uvv2UFsVkks(TvOngoingPrivacyChip.this);
        }
    };
    public final Runnable mAccessibilityRunnable = new Runnable() { // from class: com.android.systemui.privacy.television.TvOngoingPrivacyChip$$ExternalSyntheticLambda1
        @Override // java.lang.Runnable
        public final void run() {
            TvOngoingPrivacyChip.m3696$r8$lambda$l83IDWsNpezgIAyLjyvKglRFEw(TvOngoingPrivacyChip.this);
        }
    };
    public final List<PrivacyItem> mItemsBeforeLastAnnouncement = new LinkedList();
    public int mState = 0;

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.privacy.television.TvOngoingPrivacyChip$$ExternalSyntheticLambda2.test(java.lang.Object):boolean] */
    public static /* synthetic */ boolean $r8$lambda$FVVf4OWon75NpMFVwiM2ikuaDzo(PrivacyItem privacyItem) {
        return lambda$onPrivacyItemsChanged$0(privacyItem);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.privacy.television.TvOngoingPrivacyChip$$ExternalSyntheticLambda1.run():void] */
    /* renamed from: $r8$lambda$l83IDWsNpezgIA-yLjyvKglRFEw */
    public static /* synthetic */ void m3696$r8$lambda$l83IDWsNpezgIAyLjyvKglRFEw(TvOngoingPrivacyChip tvOngoingPrivacyChip) {
        tvOngoingPrivacyChip.makeAccessibilityAnnouncement();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.privacy.television.TvOngoingPrivacyChip$$ExternalSyntheticLambda0.run():void] */
    /* renamed from: $r8$lambda$rYshB-eBnuQSp72_uvv2UFsVkks */
    public static /* synthetic */ void m3697$r8$lambda$rYshBeBnuQSp72_uvv2UFsVkks(TvOngoingPrivacyChip tvOngoingPrivacyChip) {
        tvOngoingPrivacyChip.collapseChip();
    }

    public TvOngoingPrivacyChip(Context context, PrivacyItemController privacyItemController, IWindowManager iWindowManager) {
        boolean z = false;
        this.mContext = context;
        this.mPrivacyItemController = privacyItemController;
        this.mIWindowManager = iWindowManager;
        Resources resources = context.getResources();
        this.mIconMarginStart = Math.round(resources.getDimension(R$dimen.privacy_chip_icon_margin_in_between));
        this.mIconSize = resources.getDimensionPixelSize(R$dimen.privacy_chip_icon_size);
        this.mIsRtl = context.getResources().getConfiguration().getLayoutDirection() == 1 ? true : z;
        updateStaticPrivacyIndicatorBounds();
        this.mAnimationDurationMs = resources.getInteger(R$integer.privacy_chip_animation_millis);
        this.mMicCameraIndicatorFlagEnabled = privacyItemController.getMicCameraAvailable();
        this.mAllIndicatorsEnabled = privacyItemController.getAllIndicatorsAvailable();
    }

    public static /* synthetic */ boolean lambda$onPrivacyItemsChanged$0(PrivacyItem privacyItem) {
        return privacyItem.getPrivacyType() == PrivacyType.TYPE_LOCATION;
    }

    public final void animateIconAlphaTo(float f) {
        ObjectAnimator objectAnimator = this.mAnimator;
        if (objectAnimator == null) {
            ObjectAnimator objectAnimator2 = new ObjectAnimator();
            this.mAnimator = objectAnimator2;
            objectAnimator2.setTarget(this.mIconsContainer);
            this.mAnimator.setProperty(View.ALPHA);
            this.mAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.privacy.television.TvOngoingPrivacyChip.2
                public boolean mCancelled;

                {
                    TvOngoingPrivacyChip.this = this;
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationCancel(Animator animator) {
                    this.mCancelled = true;
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    if (this.mCancelled) {
                        return;
                    }
                    TvOngoingPrivacyChip.this.onIconAnimationFinished();
                }

                @Override // android.animation.Animator.AnimatorListener
                public void onAnimationStart(Animator animator, boolean z) {
                    this.mCancelled = false;
                }
            });
        } else if (objectAnimator.isRunning()) {
            this.mAnimator.cancel();
        }
        if (this.mIconsContainer.getAlpha() == f) {
            return;
        }
        this.mAnimator.setDuration(this.mAnimationDurationMs);
        this.mAnimator.setFloatValues(f);
        this.mAnimator.start();
    }

    public final void animateIconAppearance() {
        animateIconAlphaTo(1.0f);
    }

    public final void animateIconDisappearance() {
        animateIconAlphaTo(ActionBarShadowController.ELEVATION_LOW);
    }

    public final void collapseChip() {
        if (this.mState != 2) {
            return;
        }
        this.mState = 3;
        PrivacyChipDrawable privacyChipDrawable = this.mChipDrawable;
        if (privacyChipDrawable != null) {
            privacyChipDrawable.collapse();
        }
        animateIconDisappearance();
    }

    public final void collapseLater() {
        this.mUiThreadHandler.removeCallbacks(this.mCollapseRunnable);
        this.mUiThreadHandler.postDelayed(this.mCollapseRunnable, InternetDialogController.SHORT_DURATION_TIMEOUT);
    }

    public final void createAndShowIndicator() {
        this.mState = 1;
        if (this.mIndicatorView != null || this.mViewAndWindowAdded) {
            removeIndicatorView();
        }
        ViewGroup viewGroup = (ViewGroup) LayoutInflater.from(this.mContext).inflate(R$layout.tv_ongoing_privacy_chip, (ViewGroup) null);
        this.mIndicatorView = viewGroup;
        viewGroup.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.android.systemui.privacy.television.TvOngoingPrivacyChip.1
            {
                TvOngoingPrivacyChip.this = this;
            }

            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public void onGlobalLayout() {
                if (TvOngoingPrivacyChip.this.mState != 1) {
                    return;
                }
                TvOngoingPrivacyChip.this.mViewAndWindowAdded = true;
                TvOngoingPrivacyChip.this.mIndicatorView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                TvOngoingPrivacyChip.this.postAccessibilityAnnouncement();
                TvOngoingPrivacyChip.this.animateIconAppearance();
                TvOngoingPrivacyChip.this.mChipDrawable.startInitialFadeIn();
            }
        });
        PrivacyChipDrawable privacyChipDrawable = new PrivacyChipDrawable(this.mContext);
        this.mChipDrawable = privacyChipDrawable;
        privacyChipDrawable.setListener(this);
        this.mChipDrawable.setRtl(this.mIsRtl);
        ImageView imageView = (ImageView) this.mIndicatorView.findViewById(R$id.chip_drawable);
        if (imageView != null) {
            imageView.setImageDrawable(this.mChipDrawable);
        }
        LinearLayout linearLayout = (LinearLayout) this.mIndicatorView.findViewById(R$id.icons_container);
        this.mIconsContainer = linearLayout;
        linearLayout.setAlpha(ActionBarShadowController.ELEVATION_LOW);
        updateIcons();
        ((WindowManager) this.mContext.getSystemService(WindowManager.class)).addView(this.mIndicatorView, getWindowLayoutParams());
    }

    public final void fadeOutIndicator() {
        int i = this.mState;
        if (i == 0 || i == 4) {
            return;
        }
        this.mUiThreadHandler.removeCallbacks(this.mCollapseRunnable);
        if (this.mViewAndWindowAdded) {
            this.mState = 4;
            animateIconDisappearance();
        } else {
            this.mState = 0;
            removeIndicatorView();
        }
        PrivacyChipDrawable privacyChipDrawable = this.mChipDrawable;
        if (privacyChipDrawable != null) {
            privacyChipDrawable.updateIcons(0);
        }
    }

    public final WindowManager.LayoutParams getWindowLayoutParams() {
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(-2, -2, 2006, 8, -3);
        layoutParams.gravity = (this.mIsRtl ? 3 : 5) | 48;
        layoutParams.setTitle("MicrophoneCaptureIndicator");
        layoutParams.packageName = this.mContext.getPackageName();
        return layoutParams;
    }

    public final boolean isChipDisabled() {
        return (this.mMicCameraIndicatorFlagEnabled || this.mAllIndicatorsEnabled) ? false : true;
    }

    public final boolean listContainsPrivacyType(List<PrivacyItem> list, PrivacyType privacyType) {
        for (PrivacyItem privacyItem : list) {
            if (privacyItem.getPrivacyType() == privacyType) {
                return true;
            }
        }
        return false;
    }

    public final void makeAccessibilityAnnouncement() {
        int i;
        if (this.mIndicatorView == null) {
            return;
        }
        List<PrivacyItem> list = this.mItemsBeforeLastAnnouncement;
        PrivacyType privacyType = PrivacyType.TYPE_CAMERA;
        boolean listContainsPrivacyType = listContainsPrivacyType(list, privacyType);
        boolean listContainsPrivacyType2 = listContainsPrivacyType(this.mPrivacyItems, privacyType);
        List<PrivacyItem> list2 = this.mItemsBeforeLastAnnouncement;
        PrivacyType privacyType2 = PrivacyType.TYPE_MICROPHONE;
        boolean listContainsPrivacyType3 = listContainsPrivacyType(list2, privacyType2);
        boolean listContainsPrivacyType4 = listContainsPrivacyType(this.mPrivacyItems, privacyType2);
        if (!listContainsPrivacyType && listContainsPrivacyType2 && !listContainsPrivacyType3 && listContainsPrivacyType4) {
            i = R$string.mic_and_camera_recording_announcement;
        } else if (!listContainsPrivacyType || listContainsPrivacyType2 || !listContainsPrivacyType3 || listContainsPrivacyType4) {
            int i2 = (!listContainsPrivacyType || listContainsPrivacyType2) ? (listContainsPrivacyType || !listContainsPrivacyType2) ? 0 : R$string.camera_recording_announcement : R$string.camera_stopped_recording_announcement;
            int i3 = i2;
            if (i2 != 0) {
                this.mIndicatorView.announceForAccessibility(this.mContext.getString(i2));
                i3 = 0;
            }
            if (!listContainsPrivacyType3 || listContainsPrivacyType4) {
                i = i3;
                if (!listContainsPrivacyType3) {
                    i = i3;
                    if (listContainsPrivacyType4) {
                        i = R$string.mic_recording_announcement;
                    }
                }
            } else {
                i = R$string.mic_stopped_recording_announcement;
            }
        } else {
            i = R$string.mic_camera_stopped_recording_announcement;
        }
        if (i != 0) {
            this.mIndicatorView.announceForAccessibility(this.mContext.getString(i));
        }
        this.mItemsBeforeLastAnnouncement.clear();
        this.mItemsBeforeLastAnnouncement.addAll(this.mPrivacyItems);
    }

    @Override // com.android.systemui.CoreStartable
    public void onConfigurationChanged(Configuration configuration) {
        boolean z = true;
        if (configuration.getLayoutDirection() != 1) {
            z = false;
        }
        if (this.mIsRtl == z) {
            return;
        }
        this.mIsRtl = z;
        updateStaticPrivacyIndicatorBounds();
        if (this.mState == 0 || this.mIndicatorView == null) {
            return;
        }
        fadeOutIndicator();
        createAndShowIndicator();
    }

    @Override // com.android.systemui.privacy.television.PrivacyChipDrawable.PrivacyChipDrawableListener
    public void onFadeOutFinished() {
        if (this.mState == 4) {
            removeIndicatorView();
            this.mState = 0;
        }
    }

    @Override // com.android.systemui.privacy.PrivacyConfig.Callback
    public void onFlagMicCameraChanged(boolean z) {
        this.mMicCameraIndicatorFlagEnabled = z;
        updateChipOnFlagChanged();
    }

    public final void onIconAnimationFinished() {
        int i = this.mState;
        if (i == 1 || i == 2) {
            collapseLater();
        }
        int i2 = this.mState;
        if (i2 == 1) {
            this.mState = 2;
        } else if (i2 == 4) {
            removeIndicatorView();
            this.mState = 0;
        }
    }

    @Override // com.android.systemui.privacy.PrivacyItemController.Callback
    public void onPrivacyItemsChanged(List<PrivacyItem> list) {
        ArrayList arrayList = new ArrayList(list);
        arrayList.removeIf(new Predicate() { // from class: com.android.systemui.privacy.television.TvOngoingPrivacyChip$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return TvOngoingPrivacyChip.$r8$lambda$FVVf4OWon75NpMFVwiM2ikuaDzo((PrivacyItem) obj);
            }
        });
        if (isChipDisabled()) {
            fadeOutIndicator();
            this.mPrivacyItems = arrayList;
        } else if (arrayList.size() == this.mPrivacyItems.size() && this.mPrivacyItems.containsAll(arrayList)) {
        } else {
            this.mPrivacyItems = arrayList;
            postAccessibilityAnnouncement();
            updateChip();
        }
    }

    public final void postAccessibilityAnnouncement() {
        this.mUiThreadHandler.removeCallbacks(this.mAccessibilityRunnable);
        if (this.mPrivacyItems.size() == 0) {
            makeAccessibilityAnnouncement();
        } else {
            this.mUiThreadHandler.postDelayed(this.mAccessibilityRunnable, 500L);
        }
    }

    public final void removeIndicatorView() {
        ViewGroup viewGroup;
        WindowManager windowManager = (WindowManager) this.mContext.getSystemService(WindowManager.class);
        if (windowManager != null && (viewGroup = this.mIndicatorView) != null) {
            windowManager.removeView(viewGroup);
        }
        this.mIndicatorView = null;
        this.mAnimator = null;
        PrivacyChipDrawable privacyChipDrawable = this.mChipDrawable;
        if (privacyChipDrawable != null) {
            privacyChipDrawable.setListener(null);
            this.mChipDrawable = null;
        }
        this.mViewAndWindowAdded = false;
    }

    @Override // com.android.systemui.CoreStartable
    public void start() {
        this.mPrivacyItemController.addCallback(this);
    }

    public final void updateChip() {
        if (this.mPrivacyItems.isEmpty()) {
            fadeOutIndicator();
            return;
        }
        int i = this.mState;
        if (i == 0) {
            createAndShowIndicator();
        } else if (i == 1 || i == 2) {
            updateIcons();
            collapseLater();
        } else if (i == 3 || i == 4) {
            this.mState = 2;
            updateIcons();
            animateIconAppearance();
        }
    }

    public final void updateChipOnFlagChanged() {
        if (isChipDisabled()) {
            fadeOutIndicator();
        } else {
            updateChip();
        }
    }

    public final void updateIcons() {
        List<Drawable> generateIcons = new PrivacyChipBuilder(this.mContext, this.mPrivacyItems).generateIcons();
        this.mIconsContainer.removeAllViews();
        for (int i = 0; i < generateIcons.size(); i++) {
            Drawable drawable = generateIcons.get(i);
            drawable.mutate().setTint(this.mContext.getColor(R$color.privacy_icon_tint));
            ImageView imageView = new ImageView(this.mContext);
            imageView.setImageDrawable(drawable);
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            LinearLayout linearLayout = this.mIconsContainer;
            int i2 = this.mIconSize;
            linearLayout.addView(imageView, i2, i2);
            if (i != 0) {
                ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) imageView.getLayoutParams();
                marginLayoutParams.setMarginStart(this.mIconMarginStart);
                imageView.setLayoutParams(marginLayoutParams);
            }
        }
        PrivacyChipDrawable privacyChipDrawable = this.mChipDrawable;
        if (privacyChipDrawable != null) {
            privacyChipDrawable.updateIcons(generateIcons.size());
        }
    }

    public final void updateStaticPrivacyIndicatorBounds() {
        Resources resources = this.mContext.getResources();
        int dimensionPixelSize = resources.getDimensionPixelSize(R$dimen.privacy_chip_max_width);
        int dimensionPixelSize2 = resources.getDimensionPixelSize(R$dimen.privacy_chip_height);
        int dimensionPixelSize3 = resources.getDimensionPixelSize(R$dimen.privacy_chip_margin) * 2;
        Rect bounds = ((WindowManager) this.mContext.getSystemService(WindowManager.class)).getCurrentWindowMetrics().getBounds();
        Rect[] rectArr = this.mBounds;
        boolean z = this.mIsRtl;
        int i = z ? bounds.left : (bounds.right - dimensionPixelSize3) - dimensionPixelSize;
        int i2 = bounds.top;
        rectArr[0] = new Rect(i, i2, z ? bounds.left + dimensionPixelSize3 + dimensionPixelSize : bounds.right, dimensionPixelSize3 + i2 + dimensionPixelSize2);
        try {
            this.mIWindowManager.updateStaticPrivacyIndicatorBounds(this.mContext.getDisplayId(), this.mBounds);
        } catch (RemoteException e) {
            Log.w("TvOngoingPrivacyChip", "could not update privacy indicator bounds");
        }
    }
}