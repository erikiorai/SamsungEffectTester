package com.android.systemui.media.controls.models.recommendation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.systemui.R$id;
import com.android.systemui.R$layout;
import com.android.systemui.media.controls.models.GutsViewHolder;
import com.android.systemui.media.controls.ui.IlluminationDrawable;
import com.android.systemui.util.animation.TransitionLayout;
import java.util.List;
import java.util.Set;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.SetsKt__SetsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: mainsysui33.jar:com/android/systemui/media/controls/models/recommendation/RecommendationViewHolder.class */
public final class RecommendationViewHolder {
    public static final Companion Companion = new Companion(null);
    public static final Set<Integer> controlsIds;
    public static final Set<Integer> mediaContainersIds;
    public static final Set<Integer> mediaTitlesAndSubtitlesIds;
    public final ImageView cardIcon;
    public final GutsViewHolder gutsViewHolder;
    public final List<ViewGroup> mediaCoverContainers;
    public final List<ImageView> mediaCoverItems;
    public final List<TextView> mediaSubtitles;
    public final List<TextView> mediaTitles;
    public final TransitionLayout recommendations;

    /* loaded from: mainsysui33.jar:com/android/systemui/media/controls/models/recommendation/RecommendationViewHolder$Companion.class */
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final RecommendationViewHolder create(LayoutInflater layoutInflater, ViewGroup viewGroup) {
            View inflate = layoutInflater.inflate(R$layout.media_smartspace_recommendations, viewGroup, false);
            inflate.setLayoutDirection(3);
            return new RecommendationViewHolder(inflate, null);
        }

        public final Set<Integer> getControlsIds() {
            return RecommendationViewHolder.controlsIds;
        }

        public final Set<Integer> getMediaContainersIds() {
            return RecommendationViewHolder.mediaContainersIds;
        }

        public final Set<Integer> getMediaTitlesAndSubtitlesIds() {
            return RecommendationViewHolder.mediaTitlesAndSubtitlesIds;
        }
    }

    static {
        int i = R$id.recommendation_card_icon;
        int i2 = R$id.media_cover1;
        int i3 = R$id.media_cover2;
        int i4 = R$id.media_cover3;
        int i5 = R$id.media_cover1_container;
        int i6 = R$id.media_cover2_container;
        int i7 = R$id.media_cover3_container;
        int i8 = R$id.media_title1;
        int i9 = R$id.media_title2;
        int i10 = R$id.media_title3;
        int i11 = R$id.media_subtitle1;
        int i12 = R$id.media_subtitle2;
        int i13 = R$id.media_subtitle3;
        controlsIds = SetsKt__SetsKt.setOf(new Integer[]{Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3), Integer.valueOf(i4), Integer.valueOf(i5), Integer.valueOf(i6), Integer.valueOf(i7), Integer.valueOf(i8), Integer.valueOf(i9), Integer.valueOf(i10), Integer.valueOf(i11), Integer.valueOf(i12), Integer.valueOf(i13)});
        mediaTitlesAndSubtitlesIds = SetsKt__SetsKt.setOf(new Integer[]{Integer.valueOf(i8), Integer.valueOf(i9), Integer.valueOf(i10), Integer.valueOf(i11), Integer.valueOf(i12), Integer.valueOf(i13)});
        mediaContainersIds = SetsKt__SetsKt.setOf(new Integer[]{Integer.valueOf(i5), Integer.valueOf(i6), Integer.valueOf(i7)});
    }

    public RecommendationViewHolder(View view) {
        TransitionLayout transitionLayout = (TransitionLayout) view;
        this.recommendations = transitionLayout;
        this.cardIcon = (ImageView) view.requireViewById(R$id.recommendation_card_icon);
        this.mediaCoverItems = CollectionsKt__CollectionsKt.listOf(new ImageView[]{(ImageView) view.requireViewById(R$id.media_cover1), (ImageView) view.requireViewById(R$id.media_cover2), (ImageView) view.requireViewById(R$id.media_cover3)});
        List<ViewGroup> listOf = CollectionsKt__CollectionsKt.listOf(new ViewGroup[]{(ViewGroup) view.requireViewById(R$id.media_cover1_container), (ViewGroup) view.requireViewById(R$id.media_cover2_container), (ViewGroup) view.requireViewById(R$id.media_cover3_container)});
        this.mediaCoverContainers = listOf;
        this.mediaTitles = CollectionsKt__CollectionsKt.listOf(new TextView[]{(TextView) view.requireViewById(R$id.media_title1), (TextView) view.requireViewById(R$id.media_title2), (TextView) view.requireViewById(R$id.media_title3)});
        this.mediaSubtitles = CollectionsKt__CollectionsKt.listOf(new TextView[]{(TextView) view.requireViewById(R$id.media_subtitle1), (TextView) view.requireViewById(R$id.media_subtitle2), (TextView) view.requireViewById(R$id.media_subtitle3)});
        this.gutsViewHolder = new GutsViewHolder(view);
        IlluminationDrawable illuminationDrawable = (IlluminationDrawable) transitionLayout.getBackground();
        for (ViewGroup viewGroup : listOf) {
            illuminationDrawable.registerLightSource(viewGroup);
        }
        illuminationDrawable.registerLightSource(this.gutsViewHolder.getCancel());
        illuminationDrawable.registerLightSource(this.gutsViewHolder.getDismiss());
        illuminationDrawable.registerLightSource(this.gutsViewHolder.getSettings());
    }

    public /* synthetic */ RecommendationViewHolder(View view, DefaultConstructorMarker defaultConstructorMarker) {
        this(view);
    }

    public final ImageView getCardIcon() {
        return this.cardIcon;
    }

    public final GutsViewHolder getGutsViewHolder() {
        return this.gutsViewHolder;
    }

    public final List<ViewGroup> getMediaCoverContainers() {
        return this.mediaCoverContainers;
    }

    public final List<ImageView> getMediaCoverItems() {
        return this.mediaCoverItems;
    }

    public final List<TextView> getMediaSubtitles() {
        return this.mediaSubtitles;
    }

    public final List<TextView> getMediaTitles() {
        return this.mediaTitles;
    }

    public final TransitionLayout getRecommendations() {
        return this.recommendations;
    }

    public final void marquee(boolean z, long j) {
        this.gutsViewHolder.marquee(z, j, "RecommendationViewHolder");
    }
}