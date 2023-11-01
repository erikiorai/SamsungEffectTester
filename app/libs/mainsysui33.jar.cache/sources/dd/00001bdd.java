package com.android.systemui.media.controls.models.player;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.graphics.drawable.Drawable;
import android.text.format.DateUtils;
import android.widget.SeekBar;
import androidx.lifecycle.Observer;
import com.android.internal.annotations.VisibleForTesting;
import com.android.systemui.R$dimen;
import com.android.systemui.R$string;
import com.android.systemui.animation.Interpolators;
import com.android.systemui.media.controls.models.player.SeekBarViewModel;
import com.android.systemui.media.controls.ui.SquigglyProgress;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: mainsysui33.jar:com/android/systemui/media/controls/models/player/SeekBarObserver.class */
public class SeekBarObserver implements Observer<SeekBarViewModel.Progress> {
    public static final Companion Companion = new Companion(null);
    public static final int RESET_ANIMATION_DURATION_MS = 750;
    public static final int RESET_ANIMATION_THRESHOLD_MS = 250;
    public final MediaViewHolder holder;
    public final int seekBarDisabledHeight;
    public final int seekBarDisabledVerticalPadding;
    public final int seekBarEnabledMaxHeight;
    public final int seekBarEnabledVerticalPadding;
    public Animator seekBarResetAnimator;

    /* loaded from: mainsysui33.jar:com/android/systemui/media/controls/models/player/SeekBarObserver$Companion.class */
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public SeekBarObserver(MediaViewHolder mediaViewHolder) {
        this.holder = mediaViewHolder;
        this.seekBarEnabledMaxHeight = mediaViewHolder.getSeekBar().getContext().getResources().getDimensionPixelSize(R$dimen.qs_media_enabled_seekbar_height);
        this.seekBarDisabledHeight = mediaViewHolder.getSeekBar().getContext().getResources().getDimensionPixelSize(R$dimen.qs_media_disabled_seekbar_height);
        this.seekBarEnabledVerticalPadding = mediaViewHolder.getSeekBar().getContext().getResources().getDimensionPixelSize(R$dimen.qs_media_session_enabled_seekbar_vertical_padding);
        this.seekBarDisabledVerticalPadding = mediaViewHolder.getSeekBar().getContext().getResources().getDimensionPixelSize(R$dimen.qs_media_session_disabled_seekbar_vertical_padding);
        float dimensionPixelSize = mediaViewHolder.getSeekBar().getContext().getResources().getDimensionPixelSize(R$dimen.qs_media_seekbar_progress_wavelength);
        float dimensionPixelSize2 = mediaViewHolder.getSeekBar().getContext().getResources().getDimensionPixelSize(R$dimen.qs_media_seekbar_progress_amplitude);
        float dimensionPixelSize3 = mediaViewHolder.getSeekBar().getContext().getResources().getDimensionPixelSize(R$dimen.qs_media_seekbar_progress_phase);
        float dimensionPixelSize4 = mediaViewHolder.getSeekBar().getContext().getResources().getDimensionPixelSize(R$dimen.qs_media_seekbar_progress_stroke_width);
        Drawable progressDrawable = mediaViewHolder.getSeekBar().getProgressDrawable();
        SquigglyProgress squigglyProgress = progressDrawable instanceof SquigglyProgress ? (SquigglyProgress) progressDrawable : null;
        if (squigglyProgress != null) {
            squigglyProgress.setWaveLength(dimensionPixelSize);
            squigglyProgress.setLineAmplitude(dimensionPixelSize2);
            squigglyProgress.setPhaseSpeed(dimensionPixelSize3);
            squigglyProgress.setStrokeWidth(dimensionPixelSize4);
        }
    }

    @VisibleForTesting
    public Animator buildResetAnimator(int i) {
        SeekBar seekBar = this.holder.getSeekBar();
        int progress = this.holder.getSeekBar().getProgress();
        int i2 = RESET_ANIMATION_DURATION_MS;
        ObjectAnimator ofInt = ObjectAnimator.ofInt(seekBar, "progress", progress, i + i2);
        ofInt.setAutoCancel(true);
        ofInt.setDuration(i2);
        ofInt.setInterpolator(Interpolators.EMPHASIZED);
        return ofInt;
    }

    /* JADX DEBUG: Method merged with bridge method */
    @Override // androidx.lifecycle.Observer
    public void onChanged(SeekBarViewModel.Progress progress) {
        Drawable progressDrawable = this.holder.getSeekBar().getProgressDrawable();
        SquigglyProgress squigglyProgress = progressDrawable instanceof SquigglyProgress ? (SquigglyProgress) progressDrawable : null;
        if (!progress.getEnabled()) {
            if (this.holder.getSeekBar().getMaxHeight() != this.seekBarDisabledHeight) {
                this.holder.getSeekBar().setMaxHeight(this.seekBarDisabledHeight);
                setVerticalPadding(this.seekBarDisabledVerticalPadding);
            }
            this.holder.getSeekBar().setEnabled(false);
            if (squigglyProgress != null) {
                squigglyProgress.setAnimate(false);
            }
            this.holder.getSeekBar().getThumb().setAlpha(0);
            this.holder.getSeekBar().setProgress(0);
            this.holder.getSeekBar().setContentDescription("");
            this.holder.getScrubbingElapsedTimeView().setText("");
            this.holder.getScrubbingTotalTimeView().setText("");
            return;
        }
        this.holder.getSeekBar().getThumb().setAlpha(progress.getSeekAvailable() ? 255 : 0);
        this.holder.getSeekBar().setEnabled(progress.getSeekAvailable());
        if (squigglyProgress != null) {
            squigglyProgress.setAnimate(progress.getPlaying() && !progress.getScrubbing());
        }
        if (squigglyProgress != null) {
            squigglyProgress.setTransitionEnabled(!progress.getSeekAvailable());
        }
        if (this.holder.getSeekBar().getMaxHeight() != this.seekBarEnabledMaxHeight) {
            this.holder.getSeekBar().setMaxHeight(this.seekBarEnabledMaxHeight);
            setVerticalPadding(this.seekBarEnabledVerticalPadding);
        }
        this.holder.getSeekBar().setMax(progress.getDuration());
        String formatElapsedTime = DateUtils.formatElapsedTime(progress.getDuration() / 1000);
        if (progress.getScrubbing()) {
            this.holder.getScrubbingTotalTimeView().setText(formatElapsedTime);
        }
        Integer elapsedTime = progress.getElapsedTime();
        if (elapsedTime != null) {
            int intValue = elapsedTime.intValue();
            if (!progress.getScrubbing()) {
                Animator animator = this.seekBarResetAnimator;
                if (!(animator != null ? animator.isRunning() : false)) {
                    int i = RESET_ANIMATION_THRESHOLD_MS;
                    if (intValue > i || this.holder.getSeekBar().getProgress() <= i) {
                        this.holder.getSeekBar().setProgress(intValue);
                    } else {
                        Animator buildResetAnimator = buildResetAnimator(intValue);
                        buildResetAnimator.start();
                        this.seekBarResetAnimator = buildResetAnimator;
                    }
                }
            }
            String formatElapsedTime2 = DateUtils.formatElapsedTime(intValue / 1000);
            if (progress.getScrubbing()) {
                this.holder.getScrubbingElapsedTimeView().setText(formatElapsedTime2);
            }
            this.holder.getSeekBar().setContentDescription(this.holder.getSeekBar().getContext().getString(R$string.controls_media_seekbar_description, formatElapsedTime2, formatElapsedTime));
        }
    }

    public final void setVerticalPadding(int i) {
        this.holder.getSeekBar().setPadding(this.holder.getSeekBar().getPaddingLeft(), i, this.holder.getSeekBar().getPaddingRight(), this.holder.getSeekBar().getPaddingBottom());
    }
}