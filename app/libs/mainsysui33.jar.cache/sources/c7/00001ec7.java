package com.android.systemui.navigationbar.gestural;

import android.content.res.Resources;
import com.android.settingslib.widget.ActionBarShadowController;
import com.android.systemui.R$dimen;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: mainsysui33.jar:com/android/systemui/navigationbar/gestural/EdgePanelParams.class */
public final class EdgePanelParams {
    public int arrowPaddingEnd;
    public float arrowThickness;
    public float cancelledEdgeCornerRadius;
    public int fingerOffset;
    public int minArrowYPosition;
    public int minDeltaForSwitch;
    public Resources resources;
    public float swipeProgressThreshold;
    public float swipeTriggerThreshold;
    public BackIndicatorDimens entryIndicator = new BackIndicatorDimens(ActionBarShadowController.ELEVATION_LOW, null, null, 7, null);
    public BackIndicatorDimens activeIndicator = new BackIndicatorDimens(ActionBarShadowController.ELEVATION_LOW, null, null, 7, null);
    public BackIndicatorDimens preThresholdIndicator = new BackIndicatorDimens(ActionBarShadowController.ELEVATION_LOW, null, null, 7, null);
    public BackIndicatorDimens fullyStretchedIndicator = new BackIndicatorDimens(ActionBarShadowController.ELEVATION_LOW, null, null, 7, null);
    public ArrowDimens cancelledArrowDimens = new ArrowDimens(ActionBarShadowController.ELEVATION_LOW, ActionBarShadowController.ELEVATION_LOW, 3, null);

    /* loaded from: mainsysui33.jar:com/android/systemui/navigationbar/gestural/EdgePanelParams$ArrowDimens.class */
    public static final class ArrowDimens {
        public final float height;
        public final float length;

        public ArrowDimens() {
            this(ActionBarShadowController.ELEVATION_LOW, ActionBarShadowController.ELEVATION_LOW, 3, null);
        }

        public ArrowDimens(float f, float f2) {
            this.length = f;
            this.height = f2;
        }

        public /* synthetic */ ArrowDimens(float f, float f2, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this((i & 1) != 0 ? 0.0f : f, (i & 2) != 0 ? 0.0f : f2);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj instanceof ArrowDimens) {
                ArrowDimens arrowDimens = (ArrowDimens) obj;
                return Float.compare(this.length, arrowDimens.length) == 0 && Float.compare(this.height, arrowDimens.height) == 0;
            }
            return false;
        }

        public final float getHeight() {
            return this.height;
        }

        public final float getLength() {
            return this.length;
        }

        public int hashCode() {
            return (Float.hashCode(this.length) * 31) + Float.hashCode(this.height);
        }

        public String toString() {
            float f = this.length;
            float f2 = this.height;
            return "ArrowDimens(length=" + f + ", height=" + f2 + ")";
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/navigationbar/gestural/EdgePanelParams$BackIndicatorDimens.class */
    public static final class BackIndicatorDimens {
        public final ArrowDimens arrowDimens;
        public final BackgroundDimens backgroundDimens;
        public final float horizontalTranslation;

        public BackIndicatorDimens() {
            this(ActionBarShadowController.ELEVATION_LOW, null, null, 7, null);
        }

        public BackIndicatorDimens(float f, ArrowDimens arrowDimens, BackgroundDimens backgroundDimens) {
            this.horizontalTranslation = f;
            this.arrowDimens = arrowDimens;
            this.backgroundDimens = backgroundDimens;
        }

        public /* synthetic */ BackIndicatorDimens(float f, ArrowDimens arrowDimens, BackgroundDimens backgroundDimens, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this((i & 1) != 0 ? 0.0f : f, (i & 2) != 0 ? new ArrowDimens(ActionBarShadowController.ELEVATION_LOW, ActionBarShadowController.ELEVATION_LOW, 3, null) : arrowDimens, (i & 4) != 0 ? new BackgroundDimens(ActionBarShadowController.ELEVATION_LOW, ActionBarShadowController.ELEVATION_LOW, ActionBarShadowController.ELEVATION_LOW, ActionBarShadowController.ELEVATION_LOW, 15, null) : backgroundDimens);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj instanceof BackIndicatorDimens) {
                BackIndicatorDimens backIndicatorDimens = (BackIndicatorDimens) obj;
                return Float.compare(this.horizontalTranslation, backIndicatorDimens.horizontalTranslation) == 0 && Intrinsics.areEqual(this.arrowDimens, backIndicatorDimens.arrowDimens) && Intrinsics.areEqual(this.backgroundDimens, backIndicatorDimens.backgroundDimens);
            }
            return false;
        }

        public final ArrowDimens getArrowDimens() {
            return this.arrowDimens;
        }

        public final BackgroundDimens getBackgroundDimens() {
            return this.backgroundDimens;
        }

        public final float getHorizontalTranslation() {
            return this.horizontalTranslation;
        }

        public int hashCode() {
            return (((Float.hashCode(this.horizontalTranslation) * 31) + this.arrowDimens.hashCode()) * 31) + this.backgroundDimens.hashCode();
        }

        public String toString() {
            float f = this.horizontalTranslation;
            ArrowDimens arrowDimens = this.arrowDimens;
            BackgroundDimens backgroundDimens = this.backgroundDimens;
            return "BackIndicatorDimens(horizontalTranslation=" + f + ", arrowDimens=" + arrowDimens + ", backgroundDimens=" + backgroundDimens + ")";
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/navigationbar/gestural/EdgePanelParams$BackgroundDimens.class */
    public static final class BackgroundDimens {
        public final float edgeCornerRadius;
        public final float farCornerRadius;
        public final float height;
        public final float width;

        public BackgroundDimens() {
            this(ActionBarShadowController.ELEVATION_LOW, ActionBarShadowController.ELEVATION_LOW, ActionBarShadowController.ELEVATION_LOW, ActionBarShadowController.ELEVATION_LOW, 15, null);
        }

        public BackgroundDimens(float f, float f2, float f3, float f4) {
            this.width = f;
            this.height = f2;
            this.edgeCornerRadius = f3;
            this.farCornerRadius = f4;
        }

        public /* synthetic */ BackgroundDimens(float f, float f2, float f3, float f4, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this((i & 1) != 0 ? 0.0f : f, (i & 2) != 0 ? 0.0f : f2, (i & 4) != 0 ? 0.0f : f3, (i & 8) != 0 ? 0.0f : f4);
        }

        public static /* synthetic */ BackgroundDimens copy$default(BackgroundDimens backgroundDimens, float f, float f2, float f3, float f4, int i, Object obj) {
            if ((i & 1) != 0) {
                f = backgroundDimens.width;
            }
            if ((i & 2) != 0) {
                f2 = backgroundDimens.height;
            }
            if ((i & 4) != 0) {
                f3 = backgroundDimens.edgeCornerRadius;
            }
            if ((i & 8) != 0) {
                f4 = backgroundDimens.farCornerRadius;
            }
            return backgroundDimens.copy(f, f2, f3, f4);
        }

        public final BackgroundDimens copy(float f, float f2, float f3, float f4) {
            return new BackgroundDimens(f, f2, f3, f4);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj instanceof BackgroundDimens) {
                BackgroundDimens backgroundDimens = (BackgroundDimens) obj;
                return Float.compare(this.width, backgroundDimens.width) == 0 && Float.compare(this.height, backgroundDimens.height) == 0 && Float.compare(this.edgeCornerRadius, backgroundDimens.edgeCornerRadius) == 0 && Float.compare(this.farCornerRadius, backgroundDimens.farCornerRadius) == 0;
            }
            return false;
        }

        public final float getEdgeCornerRadius() {
            return this.edgeCornerRadius;
        }

        public final float getFarCornerRadius() {
            return this.farCornerRadius;
        }

        public final float getHeight() {
            return this.height;
        }

        public final float getWidth() {
            return this.width;
        }

        public int hashCode() {
            return (((((Float.hashCode(this.width) * 31) + Float.hashCode(this.height)) * 31) + Float.hashCode(this.edgeCornerRadius)) * 31) + Float.hashCode(this.farCornerRadius);
        }

        public String toString() {
            float f = this.width;
            float f2 = this.height;
            float f3 = this.edgeCornerRadius;
            float f4 = this.farCornerRadius;
            return "BackgroundDimens(width=" + f + ", height=" + f2 + ", edgeCornerRadius=" + f3 + ", farCornerRadius=" + f4 + ")";
        }
    }

    public EdgePanelParams(Resources resources) {
        this.resources = resources;
        update(this.resources);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof EdgePanelParams) && Intrinsics.areEqual(this.resources, ((EdgePanelParams) obj).resources);
    }

    public final BackIndicatorDimens getActiveIndicator() {
        return this.activeIndicator;
    }

    public final float getArrowThickness() {
        return this.arrowThickness;
    }

    public final ArrowDimens getCancelledArrowDimens() {
        return this.cancelledArrowDimens;
    }

    public final float getCancelledEdgeCornerRadius() {
        return this.cancelledEdgeCornerRadius;
    }

    public final float getDimen(int i) {
        return this.resources.getDimension(i);
    }

    public final BackIndicatorDimens getEntryIndicator() {
        return this.entryIndicator;
    }

    public final int getFingerOffset() {
        return this.fingerOffset;
    }

    public final BackIndicatorDimens getFullyStretchedIndicator() {
        return this.fullyStretchedIndicator;
    }

    public final int getMinArrowYPosition() {
        return this.minArrowYPosition;
    }

    public final int getMinDeltaForSwitch() {
        return this.minDeltaForSwitch;
    }

    public final BackIndicatorDimens getPreThresholdIndicator() {
        return this.preThresholdIndicator;
    }

    public final int getPx(int i) {
        return this.resources.getDimensionPixelSize(i);
    }

    public final float getSwipeProgressThreshold() {
        return this.swipeProgressThreshold;
    }

    public final float getSwipeTriggerThreshold() {
        return this.swipeTriggerThreshold;
    }

    public int hashCode() {
        return this.resources.hashCode();
    }

    public String toString() {
        Resources resources = this.resources;
        return "EdgePanelParams(resources=" + resources + ")";
    }

    public final void update(Resources resources) {
        this.resources = resources;
        this.arrowThickness = getDimen(R$dimen.navigation_edge_arrow_thickness);
        this.arrowPaddingEnd = getPx(R$dimen.navigation_edge_panel_padding);
        this.minArrowYPosition = getPx(R$dimen.navigation_edge_arrow_min_y);
        this.fingerOffset = getPx(R$dimen.navigation_edge_finger_offset);
        this.swipeTriggerThreshold = getDimen(R$dimen.navigation_edge_action_drag_threshold);
        this.swipeProgressThreshold = getDimen(R$dimen.navigation_edge_action_progress_threshold);
        this.minDeltaForSwitch = getPx(R$dimen.navigation_edge_minimum_x_delta_for_switch);
        this.entryIndicator = new BackIndicatorDimens(getDimen(R$dimen.navigation_edge_entry_margin), new ArrowDimens(getDimen(R$dimen.navigation_edge_entry_arrow_length), getDimen(R$dimen.navigation_edge_entry_arrow_height)), new BackgroundDimens(getDimen(R$dimen.navigation_edge_entry_background_width), getDimen(R$dimen.navigation_edge_entry_background_height), getDimen(R$dimen.navigation_edge_entry_edge_corners), getDimen(R$dimen.navigation_edge_entry_far_corners)));
        this.activeIndicator = new BackIndicatorDimens(getDimen(R$dimen.navigation_edge_active_margin), new ArrowDimens(getDimen(R$dimen.navigation_edge_active_arrow_length), getDimen(R$dimen.navigation_edge_active_arrow_height)), new BackgroundDimens(getDimen(R$dimen.navigation_edge_active_background_width), getDimen(R$dimen.navigation_edge_active_background_height), getDimen(R$dimen.navigation_edge_active_edge_corners), getDimen(R$dimen.navigation_edge_active_far_corners)));
        this.preThresholdIndicator = new BackIndicatorDimens(getDimen(R$dimen.navigation_edge_pre_threshold_margin), new ArrowDimens(this.entryIndicator.getArrowDimens().getLength(), this.entryIndicator.getArrowDimens().getHeight()), new BackgroundDimens(getDimen(R$dimen.navigation_edge_pre_threshold_background_width), getDimen(R$dimen.navigation_edge_pre_threshold_background_height), getDimen(R$dimen.navigation_edge_pre_threshold_edge_corners), getDimen(R$dimen.navigation_edge_pre_threshold_far_corners)));
        this.fullyStretchedIndicator = new BackIndicatorDimens(getDimen(R$dimen.navigation_edge_stretch_margin), new ArrowDimens(getDimen(R$dimen.navigation_edge_stretched_arrow_length), getDimen(R$dimen.navigation_edge_stretched_arrow_height)), new BackgroundDimens(getDimen(R$dimen.navigation_edge_stretch_background_width), getDimen(R$dimen.navigation_edge_stretch_background_height), getDimen(R$dimen.navigation_edge_stretch_edge_corners), getDimen(R$dimen.navigation_edge_stretch_far_corners)));
        this.cancelledEdgeCornerRadius = getDimen(R$dimen.navigation_edge_cancelled_edge_corners);
        this.cancelledArrowDimens = new ArrowDimens(getDimen(R$dimen.navigation_edge_cancelled_arrow_length), getDimen(R$dimen.navigation_edge_cancelled_arrow_height));
    }
}