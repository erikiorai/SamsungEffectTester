package com.android.systemui.media.controls.models.player;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.constraintlayout.widget.Barrier;
import com.android.systemui.R$id;
import com.android.systemui.R$layout;
import com.android.systemui.media.controls.models.GutsViewHolder;
import com.android.systemui.surfaceeffects.ripple.MultiRippleView;
import com.android.systemui.surfaceeffects.turbulencenoise.TurbulenceNoiseView;
import com.android.systemui.util.animation.TransitionLayout;
import java.util.List;
import java.util.Set;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.SetsKt__SetsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: mainsysui33.jar:com/android/systemui/media/controls/models/player/MediaViewHolder.class */
public final class MediaViewHolder {
    public static final Companion Companion = new Companion(null);
    public static final Set<Integer> controlsIds;
    public static final Set<Integer> expandedBottomActionIds;
    public static final Set<Integer> genericButtonIds;
    public final ImageButton action0;
    public final ImageButton action1;
    public final ImageButton action2;
    public final ImageButton action3;
    public final ImageButton action4;
    public final ImageButton actionNext;
    public final ImageButton actionPlayPause;
    public final ImageButton actionPrev;
    public final Barrier actionsTopBarrier;
    public final ImageView albumView;
    public final ImageView appIcon;
    public final TextView artistText;
    public final GutsViewHolder gutsViewHolder;
    public final MultiRippleView multiRippleView;
    public final TransitionLayout player;
    public final TextView scrubbingElapsedTimeView;
    public final TextView scrubbingTotalTimeView;
    public final ViewGroup seamless;
    public final View seamlessButton;
    public final ImageView seamlessIcon;
    public final TextView seamlessText;
    public final SeekBar seekBar;
    public final TextView titleText;
    public final TurbulenceNoiseView turbulenceNoiseView;

    /* loaded from: mainsysui33.jar:com/android/systemui/media/controls/models/player/MediaViewHolder$Companion.class */
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final MediaViewHolder create(LayoutInflater layoutInflater, ViewGroup viewGroup) {
            View inflate = layoutInflater.inflate(R$layout.media_session_view, viewGroup, false);
            inflate.setLayerType(2, null);
            inflate.setLayoutDirection(3);
            MediaViewHolder mediaViewHolder = new MediaViewHolder(inflate);
            mediaViewHolder.getSeekBar().setLayoutDirection(0);
            return mediaViewHolder;
        }

        public final Set<Integer> getControlsIds() {
            return MediaViewHolder.controlsIds;
        }

        public final Set<Integer> getGenericButtonIds() {
            return MediaViewHolder.genericButtonIds;
        }
    }

    static {
        int i = R$id.icon;
        int i2 = R$id.app_name;
        int i3 = R$id.header_title;
        int i4 = R$id.header_artist;
        int i5 = R$id.media_seamless;
        int i6 = R$id.media_progress_bar;
        int i7 = R$id.actionPlayPause;
        int i8 = R$id.actionNext;
        int i9 = R$id.actionPrev;
        int i10 = R$id.action0;
        int i11 = R$id.action1;
        int i12 = R$id.action2;
        int i13 = R$id.action3;
        int i14 = R$id.action4;
        int i15 = R$id.media_scrubbing_elapsed_time;
        int i16 = R$id.media_scrubbing_total_time;
        controlsIds = SetsKt__SetsKt.setOf(new Integer[]{Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3), Integer.valueOf(i4), Integer.valueOf(i5), Integer.valueOf(i6), Integer.valueOf(i7), Integer.valueOf(i8), Integer.valueOf(i9), Integer.valueOf(i10), Integer.valueOf(i11), Integer.valueOf(i12), Integer.valueOf(i13), Integer.valueOf(i14), Integer.valueOf(i), Integer.valueOf(i15), Integer.valueOf(i16)});
        genericButtonIds = SetsKt__SetsKt.setOf(new Integer[]{Integer.valueOf(i10), Integer.valueOf(i11), Integer.valueOf(i12), Integer.valueOf(i13), Integer.valueOf(i14)});
        expandedBottomActionIds = SetsKt__SetsKt.setOf(new Integer[]{Integer.valueOf(i9), Integer.valueOf(i8), Integer.valueOf(i10), Integer.valueOf(i11), Integer.valueOf(i12), Integer.valueOf(i13), Integer.valueOf(i14), Integer.valueOf(i15), Integer.valueOf(i16)});
    }

    public MediaViewHolder(View view) {
        this.player = (TransitionLayout) view;
        this.albumView = (ImageView) view.requireViewById(R$id.album_art);
        this.multiRippleView = view.requireViewById(R$id.touch_ripple_view);
        this.turbulenceNoiseView = view.requireViewById(R$id.turbulence_noise_view);
        this.appIcon = (ImageView) view.requireViewById(R$id.icon);
        this.titleText = (TextView) view.requireViewById(R$id.header_title);
        this.artistText = (TextView) view.requireViewById(R$id.header_artist);
        this.seamless = (ViewGroup) view.requireViewById(R$id.media_seamless);
        this.seamlessIcon = (ImageView) view.requireViewById(R$id.media_seamless_image);
        this.seamlessText = (TextView) view.requireViewById(R$id.media_seamless_text);
        this.seamlessButton = view.requireViewById(R$id.media_seamless_button);
        this.seekBar = (SeekBar) view.requireViewById(R$id.media_progress_bar);
        this.scrubbingElapsedTimeView = (TextView) view.requireViewById(R$id.media_scrubbing_elapsed_time);
        this.scrubbingTotalTimeView = (TextView) view.requireViewById(R$id.media_scrubbing_total_time);
        this.gutsViewHolder = new GutsViewHolder(view);
        this.actionPlayPause = (ImageButton) view.requireViewById(R$id.actionPlayPause);
        this.actionNext = (ImageButton) view.requireViewById(R$id.actionNext);
        this.actionPrev = (ImageButton) view.requireViewById(R$id.actionPrev);
        this.action0 = (ImageButton) view.requireViewById(R$id.action0);
        this.action1 = (ImageButton) view.requireViewById(R$id.action1);
        this.action2 = (ImageButton) view.requireViewById(R$id.action2);
        this.action3 = (ImageButton) view.requireViewById(R$id.action3);
        this.action4 = (ImageButton) view.requireViewById(R$id.action4);
        this.actionsTopBarrier = (Barrier) view.requireViewById(R$id.media_action_barrier_top);
    }

    public final ImageButton getAction(int i) {
        ImageButton imageButton;
        if (i == R$id.actionPlayPause) {
            imageButton = this.actionPlayPause;
        } else if (i == R$id.actionNext) {
            imageButton = this.actionNext;
        } else if (i == R$id.actionPrev) {
            imageButton = this.actionPrev;
        } else if (i == R$id.action0) {
            imageButton = this.action0;
        } else if (i == R$id.action1) {
            imageButton = this.action1;
        } else if (i == R$id.action2) {
            imageButton = this.action2;
        } else if (i == R$id.action3) {
            imageButton = this.action3;
        } else if (i != R$id.action4) {
            throw new IllegalArgumentException();
        } else {
            imageButton = this.action4;
        }
        return imageButton;
    }

    public final ImageButton getActionPlayPause() {
        return this.actionPlayPause;
    }

    public final ImageView getAlbumView() {
        return this.albumView;
    }

    public final ImageView getAppIcon() {
        return this.appIcon;
    }

    public final TextView getArtistText() {
        return this.artistText;
    }

    public final GutsViewHolder getGutsViewHolder() {
        return this.gutsViewHolder;
    }

    public final MultiRippleView getMultiRippleView() {
        return this.multiRippleView;
    }

    public final TransitionLayout getPlayer() {
        return this.player;
    }

    public final TextView getScrubbingElapsedTimeView() {
        return this.scrubbingElapsedTimeView;
    }

    public final TextView getScrubbingTotalTimeView() {
        return this.scrubbingTotalTimeView;
    }

    public final ViewGroup getSeamless() {
        return this.seamless;
    }

    public final View getSeamlessButton() {
        return this.seamlessButton;
    }

    public final ImageView getSeamlessIcon() {
        return this.seamlessIcon;
    }

    public final TextView getSeamlessText() {
        return this.seamlessText;
    }

    public final SeekBar getSeekBar() {
        return this.seekBar;
    }

    public final TextView getTitleText() {
        return this.titleText;
    }

    public final List<ImageButton> getTransparentActionButtons() {
        return CollectionsKt__CollectionsKt.listOf(new ImageButton[]{this.actionNext, this.actionPrev, this.action0, this.action1, this.action2, this.action3, this.action4});
    }

    public final TurbulenceNoiseView getTurbulenceNoiseView() {
        return this.turbulenceNoiseView;
    }

    public final void marquee(boolean z, long j) {
        this.gutsViewHolder.marquee(z, j, "MediaViewHolder");
    }
}