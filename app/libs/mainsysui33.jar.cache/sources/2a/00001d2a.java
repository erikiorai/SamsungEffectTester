package com.android.systemui.media.dialog;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.app.WallpaperColors;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Typeface;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.Icon;
import android.graphics.drawable.LayerDrawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.android.settingslib.media.MediaDevice;
import com.android.settingslib.utils.ThreadUtils;
import com.android.settingslib.widget.ActionBarShadowController;
import com.android.systemui.R$drawable;
import com.android.systemui.R$id;
import com.android.systemui.R$layout;
import com.android.systemui.R$string;
import com.android.systemui.media.dialog.MediaOutputBaseAdapter;
import java.util.List;

/* loaded from: mainsysui33.jar:com/android/systemui/media/dialog/MediaOutputBaseAdapter.class */
public abstract class MediaOutputBaseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public Context mContext;
    public final MediaOutputController mController;
    public View mHolderView;
    public boolean mIsDragging = false;
    public int mCurrentActivePosition = -1;
    public boolean mIsInitVolumeFirstTime = true;

    /* loaded from: mainsysui33.jar:com/android/systemui/media/dialog/MediaOutputBaseAdapter$MediaDeviceBaseViewHolder.class */
    public abstract class MediaDeviceBaseViewHolder extends RecyclerView.ViewHolder {
        public final CheckBox mCheckBox;
        public final ViewGroup mContainerLayout;
        public ValueAnimator mCornerAnimator;
        public String mDeviceId;
        public final ViewGroup mEndTouchArea;
        public final FrameLayout mIconAreaLayout;
        public final FrameLayout mItemLayout;
        public final ProgressBar mProgressBar;
        public final MediaOutputSeekbar mSeekBar;
        public final ImageView mStatusIcon;
        public final TextView mSubTitleText;
        public final ImageView mTitleIcon;
        public final TextView mTitleText;
        public final LinearLayout mTwoLineLayout;
        public final TextView mTwoLineTitleText;
        public ValueAnimator mVolumeAnimator;
        public final TextView mVolumeValueText;

        /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.dialog.MediaOutputBaseAdapter$MediaDeviceBaseViewHolder$$ExternalSyntheticLambda6.run():void] */
        /* renamed from: $r8$lambda$Na3XjXL5rN4McMWpQ-lsDmf61Fs */
        public static /* synthetic */ void m3302$r8$lambda$Na3XjXL5rN4McMWpQlsDmf61Fs(MediaDeviceBaseViewHolder mediaDeviceBaseViewHolder, MediaDevice mediaDevice, Icon icon) {
            mediaDeviceBaseViewHolder.lambda$setUpDeviceIcon$5(mediaDevice, icon);
        }

        /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.dialog.MediaOutputBaseAdapter$MediaDeviceBaseViewHolder$$ExternalSyntheticLambda5.onTouch(android.view.View, android.view.MotionEvent):boolean] */
        public static /* synthetic */ boolean $r8$lambda$VO0RSvBIQkUBJHe2V267cOvol0E(View view, MotionEvent motionEvent) {
            return lambda$enableSeekBar$4(view, motionEvent);
        }

        /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.dialog.MediaOutputBaseAdapter$MediaDeviceBaseViewHolder$$ExternalSyntheticLambda1.run():void] */
        /* renamed from: $r8$lambda$jN45dxcI2Hwcr9YvZWVZJXk_-FU */
        public static /* synthetic */ void m3303$r8$lambda$jN45dxcI2Hwcr9YvZWVZJXk_FU(MediaDeviceBaseViewHolder mediaDeviceBaseViewHolder, MediaDevice mediaDevice) {
            mediaDeviceBaseViewHolder.lambda$setUpDeviceIcon$6(mediaDevice);
        }

        /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.dialog.MediaOutputBaseAdapter$MediaDeviceBaseViewHolder$$ExternalSyntheticLambda0.onAnimationUpdate(android.animation.ValueAnimator):void] */
        public static /* synthetic */ void $r8$lambda$nCDiUXHaF55z7zFtxBHFwmpECjA(MediaDeviceBaseViewHolder mediaDeviceBaseViewHolder, ValueAnimator valueAnimator) {
            mediaDeviceBaseViewHolder.lambda$initAnimator$2(valueAnimator);
        }

        /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.dialog.MediaOutputBaseAdapter$MediaDeviceBaseViewHolder$$ExternalSyntheticLambda3.onTouch(android.view.View, android.view.MotionEvent):boolean] */
        public static /* synthetic */ boolean $r8$lambda$pjr0BvpmAoIJxTonpYK6BtQGBPk(View view, MotionEvent motionEvent) {
            return lambda$disableSeekBar$3(view, motionEvent);
        }

        /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.dialog.MediaOutputBaseAdapter$MediaDeviceBaseViewHolder$$ExternalSyntheticLambda4.onAnimationUpdate(android.animation.ValueAnimator):void] */
        public static /* synthetic */ void $r8$lambda$vhaZ8g6YfPhsGVmo5Sa3Nrz8VHc(MediaDeviceBaseViewHolder mediaDeviceBaseViewHolder, GradientDrawable gradientDrawable, int i, GradientDrawable gradientDrawable2, ValueAnimator valueAnimator) {
            mediaDeviceBaseViewHolder.lambda$animateCornerAndVolume$1(gradientDrawable, i, gradientDrawable2, valueAnimator);
        }

        /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.dialog.MediaOutputBaseAdapter$MediaDeviceBaseViewHolder$$ExternalSyntheticLambda2.onClick(android.view.View):void] */
        /* renamed from: $r8$lambda$xkYsV7R2040S-ki-MDjbAJShF2M */
        public static /* synthetic */ void m3304$r8$lambda$xkYsV7R2040SkiMDjbAJShF2M(MediaDeviceBaseViewHolder mediaDeviceBaseViewHolder, MediaDevice mediaDevice, View view) {
            mediaDeviceBaseViewHolder.lambda$initSeekbar$0(mediaDevice, view);
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public MediaDeviceBaseViewHolder(View view) {
            super(view);
            MediaOutputBaseAdapter.this = r5;
            this.mContainerLayout = (ViewGroup) view.requireViewById(R$id.device_container);
            this.mItemLayout = (FrameLayout) view.requireViewById(R$id.item_layout);
            this.mTitleText = (TextView) view.requireViewById(R$id.title);
            this.mSubTitleText = (TextView) view.requireViewById(R$id.subtitle);
            this.mTwoLineLayout = (LinearLayout) view.requireViewById(R$id.two_line_layout);
            this.mTwoLineTitleText = (TextView) view.requireViewById(R$id.two_line_title);
            this.mTitleIcon = (ImageView) view.requireViewById(R$id.title_icon);
            this.mProgressBar = (ProgressBar) view.requireViewById(R$id.volume_indeterminate_progress);
            this.mSeekBar = (MediaOutputSeekbar) view.requireViewById(R$id.volume_seekbar);
            this.mStatusIcon = (ImageView) view.requireViewById(R$id.media_output_item_status);
            this.mCheckBox = (CheckBox) view.requireViewById(R$id.check_box);
            this.mEndTouchArea = (ViewGroup) view.requireViewById(R$id.end_action_area);
            if (r5.mController.isAdvancedLayoutSupported()) {
                this.mVolumeValueText = (TextView) view.requireViewById(R$id.volume_value);
                this.mIconAreaLayout = (FrameLayout) view.requireViewById(R$id.icon_area);
            } else {
                this.mVolumeValueText = null;
                this.mIconAreaLayout = null;
            }
            initAnimator();
        }

        public /* synthetic */ void lambda$animateCornerAndVolume$1(GradientDrawable gradientDrawable, int i, GradientDrawable gradientDrawable2, ValueAnimator valueAnimator) {
            float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
            gradientDrawable.setCornerRadius(floatValue);
            if (!MediaOutputBaseAdapter.this.mController.isAdvancedLayoutSupported()) {
                gradientDrawable2.setCornerRadius(floatValue);
            } else if (i == 0) {
                gradientDrawable2.setCornerRadius(floatValue);
            } else {
                gradientDrawable2.setCornerRadii(new float[]{floatValue, floatValue, ActionBarShadowController.ELEVATION_LOW, ActionBarShadowController.ELEVATION_LOW, ActionBarShadowController.ELEVATION_LOW, ActionBarShadowController.ELEVATION_LOW, floatValue, floatValue});
            }
        }

        public static /* synthetic */ boolean lambda$disableSeekBar$3(View view, MotionEvent motionEvent) {
            return true;
        }

        public static /* synthetic */ boolean lambda$enableSeekBar$4(View view, MotionEvent motionEvent) {
            return false;
        }

        public /* synthetic */ void lambda$initAnimator$2(ValueAnimator valueAnimator) {
            this.mSeekBar.setProgress(((Integer) valueAnimator.getAnimatedValue()).intValue());
        }

        public /* synthetic */ void lambda$initSeekbar$0(MediaDevice mediaDevice, View view) {
            this.mSeekBar.resetVolume();
            MediaOutputBaseAdapter.this.mController.adjustVolume(mediaDevice, 0);
            updateMutedVolumeIcon();
        }

        public /* synthetic */ void lambda$setUpDeviceIcon$5(MediaDevice mediaDevice, Icon icon) {
            if (TextUtils.equals(this.mDeviceId, mediaDevice.getId())) {
                this.mTitleIcon.setImageIcon(icon);
                this.mTitleIcon.setColorFilter(MediaOutputBaseAdapter.this.mController.getColorItemContent());
            }
        }

        public /* synthetic */ void lambda$setUpDeviceIcon$6(final MediaDevice mediaDevice) {
            final Icon icon = MediaOutputBaseAdapter.this.mController.getDeviceIconCompat(mediaDevice).toIcon(MediaOutputBaseAdapter.this.mContext);
            ThreadUtils.postOnMainThread(new Runnable() { // from class: com.android.systemui.media.dialog.MediaOutputBaseAdapter$MediaDeviceBaseViewHolder$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    MediaOutputBaseAdapter.MediaDeviceBaseViewHolder.m3302$r8$lambda$Na3XjXL5rN4McMWpQlsDmf61Fs(MediaOutputBaseAdapter.MediaDeviceBaseViewHolder.this, mediaDevice, icon);
                }
            });
        }

        public final void animateCornerAndVolume(int i, final int i2) {
            final GradientDrawable gradientDrawable = (GradientDrawable) this.mItemLayout.getBackground();
            final GradientDrawable gradientDrawable2 = (GradientDrawable) (MediaOutputBaseAdapter.this.mController.isAdvancedLayoutSupported() ? this.mIconAreaLayout.getBackground() : ((ClipDrawable) ((LayerDrawable) this.mSeekBar.getProgressDrawable()).findDrawableByLayerId(16908301)).getDrawable());
            this.mCornerAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.media.dialog.MediaOutputBaseAdapter$MediaDeviceBaseViewHolder$$ExternalSyntheticLambda4
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    MediaOutputBaseAdapter.MediaDeviceBaseViewHolder.$r8$lambda$vhaZ8g6YfPhsGVmo5Sa3Nrz8VHc(MediaOutputBaseAdapter.MediaDeviceBaseViewHolder.this, gradientDrawable, i2, gradientDrawable2, valueAnimator);
                }
            });
            this.mVolumeAnimator.setIntValues(i, i2);
            this.mVolumeAnimator.start();
            this.mCornerAnimator.start();
        }

        public void disableSeekBar() {
            this.mSeekBar.setEnabled(false);
            this.mSeekBar.setOnTouchListener(new View.OnTouchListener() { // from class: com.android.systemui.media.dialog.MediaOutputBaseAdapter$MediaDeviceBaseViewHolder$$ExternalSyntheticLambda3
                @Override // android.view.View.OnTouchListener
                public final boolean onTouch(View view, MotionEvent motionEvent) {
                    return MediaOutputBaseAdapter.MediaDeviceBaseViewHolder.$r8$lambda$pjr0BvpmAoIJxTonpYK6BtQGBPk(view, motionEvent);
                }
            });
        }

        public final void enableSeekBar() {
            this.mSeekBar.setEnabled(true);
            this.mSeekBar.setOnTouchListener(new View.OnTouchListener() { // from class: com.android.systemui.media.dialog.MediaOutputBaseAdapter$MediaDeviceBaseViewHolder$$ExternalSyntheticLambda5
                @Override // android.view.View.OnTouchListener
                public final boolean onTouch(View view, MotionEvent motionEvent) {
                    return MediaOutputBaseAdapter.MediaDeviceBaseViewHolder.$r8$lambda$VO0RSvBIQkUBJHe2V267cOvol0E(view, motionEvent);
                }
            });
        }

        public final void initAnimator() {
            ValueAnimator ofFloat = ValueAnimator.ofFloat(MediaOutputBaseAdapter.this.mController.getInactiveRadius(), MediaOutputBaseAdapter.this.mController.getActiveRadius());
            this.mCornerAnimator = ofFloat;
            ofFloat.setDuration(500L);
            this.mCornerAnimator.setInterpolator(new LinearInterpolator());
            ValueAnimator ofInt = ValueAnimator.ofInt(new int[0]);
            this.mVolumeAnimator = ofInt;
            ofInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.media.dialog.MediaOutputBaseAdapter$MediaDeviceBaseViewHolder$$ExternalSyntheticLambda0
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    MediaOutputBaseAdapter.MediaDeviceBaseViewHolder.$r8$lambda$nCDiUXHaF55z7zFtxBHFwmpECjA(MediaOutputBaseAdapter.MediaDeviceBaseViewHolder.this, valueAnimator);
                }
            });
            this.mVolumeAnimator.setDuration(500L);
            this.mVolumeAnimator.setInterpolator(new LinearInterpolator());
            this.mVolumeAnimator.addListener(new Animator.AnimatorListener() { // from class: com.android.systemui.media.dialog.MediaOutputBaseAdapter.MediaDeviceBaseViewHolder.2
                {
                    MediaDeviceBaseViewHolder.this = this;
                }

                @Override // android.animation.Animator.AnimatorListener
                public void onAnimationCancel(Animator animator) {
                    MediaDeviceBaseViewHolder.this.mSeekBar.setEnabled(true);
                }

                @Override // android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    MediaDeviceBaseViewHolder.this.mSeekBar.setEnabled(true);
                }

                @Override // android.animation.Animator.AnimatorListener
                public void onAnimationRepeat(Animator animator) {
                }

                @Override // android.animation.Animator.AnimatorListener
                public void onAnimationStart(Animator animator) {
                    MediaDeviceBaseViewHolder.this.mSeekBar.setEnabled(false);
                }
            });
        }

        public void initMutingExpectedDevice() {
            disableSeekBar();
            Drawable mutate = MediaOutputBaseAdapter.this.mContext.getDrawable(R$drawable.media_output_item_background_active).mutate();
            mutate.setColorFilter(new PorterDuffColorFilter(MediaOutputBaseAdapter.this.mController.getColorConnectedItemBackground(), PorterDuff.Mode.SRC_IN));
            this.mItemLayout.setBackground(mutate);
        }

        public void initSeekbar(final MediaDevice mediaDevice, boolean z) {
            if (MediaOutputBaseAdapter.this.mController.isVolumeControlEnabled(mediaDevice)) {
                enableSeekBar();
            } else {
                disableSeekBar();
            }
            this.mSeekBar.setMaxVolume(mediaDevice.getMaxVolume());
            int currentVolume = mediaDevice.getCurrentVolume();
            if (this.mSeekBar.getVolume() != currentVolume) {
                if (!z || MediaOutputBaseAdapter.this.mIsInitVolumeFirstTime) {
                    if (!this.mVolumeAnimator.isStarted()) {
                        if (MediaOutputBaseAdapter.this.mController.isAdvancedLayoutSupported()) {
                            if (((int) ((currentVolume * 100000.0d) / this.mSeekBar.getMax())) == 0) {
                                updateMutedVolumeIcon();
                            } else {
                                updateUnmutedVolumeIcon();
                            }
                        }
                        this.mSeekBar.setVolume(currentVolume);
                    }
                } else if (MediaOutputBaseAdapter.this.mController.isAdvancedLayoutSupported()) {
                    updateTitleIcon(currentVolume == 0 ? R$drawable.media_output_icon_volume_off : R$drawable.media_output_icon_volume, MediaOutputBaseAdapter.this.mController.getColorItemContent());
                } else {
                    animateCornerAndVolume(this.mSeekBar.getProgress(), MediaOutputSeekbar.scaleVolumeToProgress(currentVolume));
                }
            } else if (MediaOutputBaseAdapter.this.mController.isAdvancedLayoutSupported() && currentVolume == 0) {
                this.mSeekBar.resetVolume();
                updateMutedVolumeIcon();
            }
            if (MediaOutputBaseAdapter.this.mIsInitVolumeFirstTime) {
                MediaOutputBaseAdapter.this.mIsInitVolumeFirstTime = false;
            }
            if (MediaOutputBaseAdapter.this.mController.isAdvancedLayoutSupported()) {
                updateIconAreaClickListener(new View.OnClickListener() { // from class: com.android.systemui.media.dialog.MediaOutputBaseAdapter$MediaDeviceBaseViewHolder$$ExternalSyntheticLambda2
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        MediaOutputBaseAdapter.MediaDeviceBaseViewHolder.m3304$r8$lambda$xkYsV7R2040SkiMDjbAJShF2M(MediaOutputBaseAdapter.MediaDeviceBaseViewHolder.this, mediaDevice, view);
                    }
                });
            }
            this.mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.android.systemui.media.dialog.MediaOutputBaseAdapter.MediaDeviceBaseViewHolder.1
                {
                    MediaDeviceBaseViewHolder.this = this;
                }

                @Override // android.widget.SeekBar.OnSeekBarChangeListener
                public void onProgressChanged(SeekBar seekBar, int i, boolean z2) {
                    if (mediaDevice == null || !z2) {
                        return;
                    }
                    int scaleProgressToVolume = MediaOutputSeekbar.scaleProgressToVolume(i);
                    int currentVolume2 = mediaDevice.getCurrentVolume();
                    if (MediaOutputBaseAdapter.this.mController.isAdvancedLayoutSupported()) {
                        int max = (int) ((scaleProgressToVolume * 100000.0d) / seekBar.getMax());
                        MediaDeviceBaseViewHolder mediaDeviceBaseViewHolder = MediaDeviceBaseViewHolder.this;
                        mediaDeviceBaseViewHolder.mVolumeValueText.setText(MediaOutputBaseAdapter.this.mContext.getResources().getString(R$string.media_output_dialog_volume_percentage, Integer.valueOf(max)));
                        MediaDeviceBaseViewHolder.this.mVolumeValueText.setVisibility(0);
                    }
                    if (scaleProgressToVolume != currentVolume2) {
                        MediaOutputBaseAdapter.this.mController.adjustVolume(mediaDevice, scaleProgressToVolume);
                        if (MediaOutputBaseAdapter.this.mController.isAdvancedLayoutSupported() && currentVolume2 == 0) {
                            MediaDeviceBaseViewHolder.this.updateUnmutedVolumeIcon();
                        }
                    }
                }

                @Override // android.widget.SeekBar.OnSeekBarChangeListener
                public void onStartTrackingTouch(SeekBar seekBar) {
                    if (MediaOutputBaseAdapter.this.mController.isAdvancedLayoutSupported()) {
                        MediaDeviceBaseViewHolder.this.mTitleIcon.setVisibility(4);
                        MediaDeviceBaseViewHolder.this.mVolumeValueText.setVisibility(0);
                    }
                    MediaOutputBaseAdapter.this.mIsDragging = true;
                }

                @Override // android.widget.SeekBar.OnSeekBarChangeListener
                public void onStopTrackingTouch(SeekBar seekBar) {
                    if (MediaOutputBaseAdapter.this.mController.isAdvancedLayoutSupported()) {
                        if (((int) ((MediaOutputSeekbar.scaleProgressToVolume(seekBar.getProgress()) * 100000.0d) / seekBar.getMax())) == 0) {
                            seekBar.setProgress(0);
                            MediaDeviceBaseViewHolder.this.updateMutedVolumeIcon();
                        } else {
                            MediaDeviceBaseViewHolder.this.updateUnmutedVolumeIcon();
                        }
                        MediaDeviceBaseViewHolder.this.mTitleIcon.setVisibility(0);
                        MediaDeviceBaseViewHolder.this.mVolumeValueText.setVisibility(8);
                    }
                    MediaOutputBaseAdapter.this.mIsDragging = false;
                }
            });
        }

        public void onBind(MediaDevice mediaDevice, int i) {
            this.mDeviceId = mediaDevice.getId();
            this.mCheckBox.setVisibility(8);
            this.mStatusIcon.setVisibility(8);
            this.mEndTouchArea.setVisibility(8);
            this.mEndTouchArea.setImportantForAccessibility(2);
            this.mContainerLayout.setOnClickListener(null);
            this.mContainerLayout.setContentDescription(null);
            this.mTitleIcon.setOnClickListener(null);
            this.mTitleText.setTextColor(MediaOutputBaseAdapter.this.mController.getColorItemContent());
            this.mSubTitleText.setTextColor(MediaOutputBaseAdapter.this.mController.getColorItemContent());
            this.mTwoLineTitleText.setTextColor(MediaOutputBaseAdapter.this.mController.getColorItemContent());
            if (MediaOutputBaseAdapter.this.mController.isAdvancedLayoutSupported()) {
                this.mIconAreaLayout.setOnClickListener(null);
                this.mVolumeValueText.setTextColor(MediaOutputBaseAdapter.this.mController.getColorItemContent());
            }
            this.mSeekBar.getProgressDrawable().setColorFilter(new PorterDuffColorFilter(MediaOutputBaseAdapter.this.mController.getColorSeekbarProgress(), PorterDuff.Mode.SRC_IN));
        }

        public void setSingleLineLayout(CharSequence charSequence) {
            setSingleLineLayout(charSequence, false, false, false, false);
        }

        public void setSingleLineLayout(CharSequence charSequence, boolean z, boolean z2, boolean z3, boolean z4) {
            this.mTwoLineLayout.setVisibility(8);
            boolean z5 = z || z2;
            if (!this.mCornerAnimator.isRunning()) {
                this.mItemLayout.setBackground(z ? MediaOutputBaseAdapter.this.mContext.getDrawable(R$drawable.media_output_item_background_active).mutate() : MediaOutputBaseAdapter.this.mContext.getDrawable(R$drawable.media_output_item_background).mutate());
                if (z) {
                    GradientDrawable gradientDrawable = (GradientDrawable) ((ClipDrawable) ((LayerDrawable) this.mSeekBar.getProgressDrawable()).findDrawableByLayerId(16908301)).getDrawable();
                    if (MediaOutputBaseAdapter.this.mController.isAdvancedLayoutSupported()) {
                        gradientDrawable.setCornerRadii(new float[]{ActionBarShadowController.ELEVATION_LOW, ActionBarShadowController.ELEVATION_LOW, MediaOutputBaseAdapter.this.mController.getActiveRadius(), MediaOutputBaseAdapter.this.mController.getActiveRadius(), MediaOutputBaseAdapter.this.mController.getActiveRadius(), MediaOutputBaseAdapter.this.mController.getActiveRadius(), ActionBarShadowController.ELEVATION_LOW, ActionBarShadowController.ELEVATION_LOW});
                    } else {
                        gradientDrawable.setCornerRadius(MediaOutputBaseAdapter.this.mController.getActiveRadius());
                    }
                }
            }
            this.mItemLayout.getBackground().setColorFilter(new PorterDuffColorFilter(z5 ? MediaOutputBaseAdapter.this.mController.getColorConnectedItemBackground() : MediaOutputBaseAdapter.this.mController.getColorItemBackground(), PorterDuff.Mode.SRC_IN));
            if (MediaOutputBaseAdapter.this.mController.isAdvancedLayoutSupported()) {
                this.mIconAreaLayout.getBackground().setColorFilter(new PorterDuffColorFilter(z ? MediaOutputBaseAdapter.this.mController.getColorSeekbarProgress() : z2 ? MediaOutputBaseAdapter.this.mController.getColorConnectedItemBackground() : MediaOutputBaseAdapter.this.mController.getColorItemBackground(), PorterDuff.Mode.SRC_IN));
            }
            this.mProgressBar.setVisibility(z2 ? 0 : 8);
            this.mSeekBar.setAlpha(1.0f);
            this.mSeekBar.setVisibility(z ? 0 : 8);
            if (!z) {
                this.mSeekBar.resetVolume();
            }
            this.mTitleText.setText(charSequence);
            this.mTitleText.setVisibility(0);
            this.mCheckBox.setVisibility(z3 ? 0 : 8);
            ViewGroup viewGroup = this.mEndTouchArea;
            int i = 8;
            if (z4) {
                i = 0;
            }
            viewGroup.setVisibility(i);
            if (MediaOutputBaseAdapter.this.mController.isAdvancedLayoutSupported()) {
                ((ViewGroup.MarginLayoutParams) this.mItemLayout.getLayoutParams()).rightMargin = z4 ? MediaOutputBaseAdapter.this.mController.getItemMarginEndSelectable() : MediaOutputBaseAdapter.this.mController.getItemMarginEndDefault();
            }
            this.mTitleIcon.setColorFilter(MediaOutputBaseAdapter.this.mController.getColorItemContent());
        }

        public final void setTwoLineLayout(MediaDevice mediaDevice, CharSequence charSequence, boolean z, boolean z2, boolean z3, boolean z4, boolean z5) {
            this.mTitleText.setVisibility(8);
            this.mTwoLineLayout.setVisibility(0);
            this.mStatusIcon.setVisibility(z5 ? 0 : 8);
            this.mSeekBar.setAlpha(1.0f);
            this.mSeekBar.setVisibility(z2 ? 0 : 8);
            Drawable mutate = MediaOutputBaseAdapter.this.mContext.getDrawable(R$drawable.media_output_item_background).mutate();
            mutate.setColorFilter(new PorterDuffColorFilter(MediaOutputBaseAdapter.this.mController.getColorItemBackground(), PorterDuff.Mode.SRC_IN));
            this.mItemLayout.setBackground(mutate);
            this.mProgressBar.setVisibility(z3 ? 0 : 8);
            TextView textView = this.mSubTitleText;
            int i = 8;
            if (z4) {
                i = 0;
            }
            textView.setVisibility(i);
            this.mTwoLineTitleText.setTranslationY(ActionBarShadowController.ELEVATION_LOW);
            TextView textView2 = this.mTwoLineTitleText;
            if (mediaDevice != null) {
                charSequence = MediaOutputBaseAdapter.this.getItemTitle(mediaDevice);
            }
            textView2.setText(charSequence);
            this.mTwoLineTitleText.setTypeface(Typeface.create(MediaOutputBaseAdapter.this.mContext.getString(z ? 17039996 : 17039995), 0));
        }

        public void setTwoLineLayout(MediaDevice mediaDevice, boolean z, boolean z2, boolean z3, boolean z4, boolean z5) {
            setTwoLineLayout(mediaDevice, null, z, z2, z3, z4, z5);
        }

        public void setUpDeviceIcon(final MediaDevice mediaDevice) {
            ThreadUtils.postOnBackgroundThread(new Runnable() { // from class: com.android.systemui.media.dialog.MediaOutputBaseAdapter$MediaDeviceBaseViewHolder$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    MediaOutputBaseAdapter.MediaDeviceBaseViewHolder.m3303$r8$lambda$jN45dxcI2Hwcr9YvZWVZJXk_FU(MediaOutputBaseAdapter.MediaDeviceBaseViewHolder.this, mediaDevice);
                }
            });
        }

        public void updateIconAreaClickListener(View.OnClickListener onClickListener) {
            if (MediaOutputBaseAdapter.this.mController.isAdvancedLayoutSupported()) {
                this.mIconAreaLayout.setOnClickListener(onClickListener);
            }
            this.mTitleIcon.setOnClickListener(onClickListener);
        }

        public void updateMutedVolumeIcon() {
            this.mIconAreaLayout.setBackground(MediaOutputBaseAdapter.this.mContext.getDrawable(R$drawable.media_output_item_background_active));
            updateTitleIcon(R$drawable.media_output_icon_volume_off, MediaOutputBaseAdapter.this.mController.getColorItemContent());
        }

        public void updateTitleIcon(int i, int i2) {
            this.mTitleIcon.setImageDrawable(MediaOutputBaseAdapter.this.mContext.getDrawable(i));
            this.mTitleIcon.setColorFilter(i2);
            if (MediaOutputBaseAdapter.this.mController.isAdvancedLayoutSupported()) {
                this.mIconAreaLayout.getBackground().setColorFilter(new PorterDuffColorFilter(MediaOutputBaseAdapter.this.mController.getColorSeekbarProgress(), PorterDuff.Mode.SRC_IN));
            }
        }

        public void updateUnmutedVolumeIcon() {
            this.mIconAreaLayout.setBackground(MediaOutputBaseAdapter.this.mContext.getDrawable(R$drawable.media_output_title_icon_area));
            updateTitleIcon(R$drawable.media_output_icon_volume, MediaOutputBaseAdapter.this.mController.getColorItemContent());
        }
    }

    public MediaOutputBaseAdapter(MediaOutputController mediaOutputController) {
        this.mController = mediaOutputController;
    }

    public int getCurrentActivePosition() {
        return this.mCurrentActivePosition;
    }

    public CharSequence getItemTitle(MediaDevice mediaDevice) {
        return mediaDevice.getName();
    }

    public boolean isCurrentlyConnected(MediaDevice mediaDevice) {
        boolean z = true;
        if (!TextUtils.equals(mediaDevice.getId(), this.mController.getCurrentConnectedMediaDevice().getId())) {
            z = this.mController.getSelectedMediaDevice().size() == 1 && isDeviceIncluded(this.mController.getSelectedMediaDevice(), mediaDevice);
        }
        return z;
    }

    public boolean isDeviceIncluded(List<MediaDevice> list, MediaDevice mediaDevice) {
        for (MediaDevice mediaDevice2 : list) {
            if (TextUtils.equals(mediaDevice2.getId(), mediaDevice.getId())) {
                return true;
            }
        }
        return false;
    }

    public boolean isDragging() {
        return this.mIsDragging;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        this.mContext = context;
        this.mHolderView = LayoutInflater.from(context).inflate(this.mController.isAdvancedLayoutSupported() ? MediaItem.getMediaLayoutId(i) : R$layout.media_output_list_item, viewGroup, false);
        return null;
    }

    public void updateColorScheme(WallpaperColors wallpaperColors, boolean z) {
        this.mController.setCurrentColorScheme(wallpaperColors, z);
    }
}