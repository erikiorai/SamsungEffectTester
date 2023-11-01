package com.android.systemui.animation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.util.IntProperty;
import android.util.Property;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import androidx.recyclerview.widget.RecyclerView;
import com.android.settingslib.widget.ActionBarShadowController;
import com.android.systemui.animation.ViewHierarchyAnimator;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt__CollectionsJVMKt;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: mainsysui33.jar:com/android/systemui/animation/ViewHierarchyAnimator.class */
public final class ViewHierarchyAnimator {
    public static final Companion Companion;
    public static final Interpolator DEFAULT_ADDITION_INTERPOLATOR;
    public static final Interpolator DEFAULT_FADE_IN_INTERPOLATOR;
    public static final Interpolator DEFAULT_INTERPOLATOR;
    public static final Interpolator DEFAULT_REMOVAL_INTERPOLATOR;
    public static final Map<Bound, IntProperty<View>> PROPERTIES;

    /* loaded from: mainsysui33.jar:com/android/systemui/animation/ViewHierarchyAnimator$Bound.class */
    public static final abstract class Bound {
        private final String label;
        private final int overrideTag;
        public static final Bound LEFT = new LEFT("LEFT", 0);
        public static final Bound TOP = new TOP("TOP", 1);
        public static final Bound RIGHT = new RIGHT("RIGHT", 2);
        public static final Bound BOTTOM = new BOTTOM("BOTTOM", 3);
        public static final /* synthetic */ Bound[] $VALUES = $values();

        /* loaded from: mainsysui33.jar:com/android/systemui/animation/ViewHierarchyAnimator$Bound$BOTTOM.class */
        public static final class BOTTOM extends Bound {
            public BOTTOM(String str, int i) {
                super(str, i, "bottom", R$id.tag_override_bottom, null);
            }

            @Override // com.android.systemui.animation.ViewHierarchyAnimator.Bound
            public int getValue(View view) {
                return view.getBottom();
            }

            @Override // com.android.systemui.animation.ViewHierarchyAnimator.Bound
            public void setValue(View view, int i) {
                view.setBottom(i);
            }
        }

        /* loaded from: mainsysui33.jar:com/android/systemui/animation/ViewHierarchyAnimator$Bound$LEFT.class */
        public static final class LEFT extends Bound {
            public LEFT(String str, int i) {
                super(str, i, "left", R$id.tag_override_left, null);
            }

            @Override // com.android.systemui.animation.ViewHierarchyAnimator.Bound
            public int getValue(View view) {
                return view.getLeft();
            }

            @Override // com.android.systemui.animation.ViewHierarchyAnimator.Bound
            public void setValue(View view, int i) {
                view.setLeft(i);
            }
        }

        /* loaded from: mainsysui33.jar:com/android/systemui/animation/ViewHierarchyAnimator$Bound$RIGHT.class */
        public static final class RIGHT extends Bound {
            public RIGHT(String str, int i) {
                super(str, i, "right", R$id.tag_override_right, null);
            }

            @Override // com.android.systemui.animation.ViewHierarchyAnimator.Bound
            public int getValue(View view) {
                return view.getRight();
            }

            @Override // com.android.systemui.animation.ViewHierarchyAnimator.Bound
            public void setValue(View view, int i) {
                view.setRight(i);
            }
        }

        /* loaded from: mainsysui33.jar:com/android/systemui/animation/ViewHierarchyAnimator$Bound$TOP.class */
        public static final class TOP extends Bound {
            public TOP(String str, int i) {
                super(str, i, "top", R$id.tag_override_top, null);
            }

            @Override // com.android.systemui.animation.ViewHierarchyAnimator.Bound
            public int getValue(View view) {
                return view.getTop();
            }

            @Override // com.android.systemui.animation.ViewHierarchyAnimator.Bound
            public void setValue(View view, int i) {
                view.setTop(i);
            }
        }

        public static final /* synthetic */ Bound[] $values() {
            return new Bound[]{LEFT, TOP, RIGHT, BOTTOM};
        }

        public Bound(String str, int i, String str2, int i2) {
            super(str, i);
            this.label = str2;
            this.overrideTag = i2;
        }

        public /* synthetic */ Bound(String str, int i, String str2, int i2, DefaultConstructorMarker defaultConstructorMarker) {
            this(str, i, str2, i2);
        }

        public static Bound valueOf(String str) {
            return (Bound) Enum.valueOf(Bound.class, str);
        }

        public static Bound[] values() {
            return (Bound[]) $VALUES.clone();
        }

        public final String getLabel() {
            return this.label;
        }

        public final int getOverrideTag() {
            return this.overrideTag;
        }

        public abstract int getValue(View view);

        public abstract void setValue(View view, int i);
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/animation/ViewHierarchyAnimator$Companion.class */
    public static final class Companion {

        /* loaded from: mainsysui33.jar:com/android/systemui/animation/ViewHierarchyAnimator$Companion$WhenMappings.class */
        public final /* synthetic */ class WhenMappings {
            public static final /* synthetic */ int[] $EnumSwitchMapping$0;

            static {
                int[] iArr = new int[Hotspot.values().length];
                try {
                    iArr[Hotspot.CENTER.ordinal()] = 1;
                } catch (NoSuchFieldError e) {
                }
                try {
                    iArr[Hotspot.BOTTOM_LEFT.ordinal()] = 2;
                } catch (NoSuchFieldError e2) {
                }
                try {
                    iArr[Hotspot.LEFT.ordinal()] = 3;
                } catch (NoSuchFieldError e3) {
                }
                try {
                    iArr[Hotspot.TOP_LEFT.ordinal()] = 4;
                } catch (NoSuchFieldError e4) {
                }
                try {
                    iArr[Hotspot.TOP.ordinal()] = 5;
                } catch (NoSuchFieldError e5) {
                }
                try {
                    iArr[Hotspot.BOTTOM.ordinal()] = 6;
                } catch (NoSuchFieldError e6) {
                }
                try {
                    iArr[Hotspot.TOP_RIGHT.ordinal()] = 7;
                } catch (NoSuchFieldError e7) {
                }
                try {
                    iArr[Hotspot.RIGHT.ordinal()] = 8;
                } catch (NoSuchFieldError e8) {
                }
                try {
                    iArr[Hotspot.BOTTOM_RIGHT.ordinal()] = 9;
                } catch (NoSuchFieldError e9) {
                }
                $EnumSwitchMapping$0 = iArr;
            }
        }

        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.animation.ViewHierarchyAnimator$Companion$createListener$1.onLayoutChange(android.view.View, int, int, int, int, int, int, int, int):void, com.android.systemui.animation.ViewHierarchyAnimator$Companion$createViewProperty$1.get(android.view.View):java.lang.Integer] */
        public static final /* synthetic */ Integer access$getBound(Companion companion, View view, Bound bound) {
            return companion.getBound(view, bound);
        }

        /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.animation.ViewHierarchyAnimator$Companion$createListener$1.onLayoutChange(android.view.View, int, int, int, int, int, int, int, int):void] */
        public static final /* synthetic */ boolean access$occupiesSpace(Companion companion, int i, int i2, int i3, int i4, int i5) {
            return companion.occupiesSpace(i, i2, i3, i4, i5);
        }

        /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.animation.ViewHierarchyAnimator$Companion$createListener$1.onLayoutChange(android.view.View, int, int, int, int, int, int, int, int):void] */
        public static final /* synthetic */ Map access$processStartValues(Companion companion, Hotspot hotspot, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, boolean z) {
            return companion.processStartValues(hotspot, i, i2, i3, i4, i5, i6, i7, i8, z);
        }

        /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.animation.ViewHierarchyAnimator$Companion$startAnimation$1.onAnimationEnd(android.animation.Animator):void] */
        public static final /* synthetic */ void access$recursivelyRemoveListener(Companion companion, View view) {
            companion.recursivelyRemoveListener(view);
        }

        /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.animation.ViewHierarchyAnimator$Companion$createListener$1.onLayoutChange(android.view.View, int, int, int, int, int, int, int, int):void, com.android.systemui.animation.ViewHierarchyAnimator$Companion$createViewProperty$1.setValue(android.view.View, int):void] */
        public static final /* synthetic */ void access$setBound(Companion companion, View view, Bound bound, int i) {
            companion.setBound(view, bound, i);
        }

        /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.animation.ViewHierarchyAnimator$Companion$createListener$1.onLayoutChange(android.view.View, int, int, int, int, int, int, int, int):void] */
        public static final /* synthetic */ void access$startAnimation(Companion companion, View view, Set set, Map map, Map map2, Interpolator interpolator, long j, boolean z, Runnable runnable) {
            companion.startAnimation(view, set, map, map2, interpolator, j, z, runnable);
        }

        public static /* synthetic */ boolean animateAddition$default(Companion companion, View view, Hotspot hotspot, Interpolator interpolator, long j, boolean z, boolean z2, Interpolator interpolator2, Runnable runnable, int i, Object obj) {
            if ((i & 2) != 0) {
                hotspot = Hotspot.CENTER;
            }
            if ((i & 4) != 0) {
                interpolator = ViewHierarchyAnimator.DEFAULT_ADDITION_INTERPOLATOR;
            }
            if ((i & 8) != 0) {
                j = 500;
            }
            if ((i & 16) != 0) {
                z = false;
            }
            if ((i & 32) != 0) {
                z2 = false;
            }
            if ((i & 64) != 0) {
                interpolator2 = ViewHierarchyAnimator.DEFAULT_FADE_IN_INTERPOLATOR;
            }
            if ((i & RecyclerView.ViewHolder.FLAG_IGNORE) != 0) {
                runnable = null;
            }
            return companion.animateAddition(view, hotspot, interpolator, j, z, z2, interpolator2, runnable);
        }

        public static /* synthetic */ View.OnLayoutChangeListener createListener$default(Companion companion, Interpolator interpolator, long j, boolean z, Hotspot hotspot, boolean z2, Runnable runnable, int i, Object obj) {
            if ((i & 8) != 0) {
                hotspot = null;
            }
            if ((i & 16) != 0) {
                z2 = false;
            }
            if ((i & 32) != 0) {
                runnable = null;
            }
            return companion.createListener(interpolator, j, z, hotspot, z2, runnable);
        }

        public static /* synthetic */ void startAnimation$default(Companion companion, View view, Set set, Map map, Map map2, Interpolator interpolator, long j, boolean z, Runnable runnable, int i, Object obj) {
            if ((i & RecyclerView.ViewHolder.FLAG_IGNORE) != 0) {
                runnable = null;
            }
            companion.startAnimation(view, set, map, map2, interpolator, j, z, runnable);
        }

        public final void addListener(View view, View.OnLayoutChangeListener onLayoutChangeListener, boolean z) {
            int i = R$id.tag_layout_listener;
            Object tag = view.getTag(i);
            if (tag != null && (tag instanceof View.OnLayoutChangeListener)) {
                view.removeOnLayoutChangeListener((View.OnLayoutChangeListener) tag);
            }
            view.addOnLayoutChangeListener(onLayoutChangeListener);
            view.setTag(i, onLayoutChangeListener);
            if ((view instanceof ViewGroup) && z) {
                ViewGroup viewGroup = (ViewGroup) view;
                int childCount = viewGroup.getChildCount();
                for (int i2 = 0; i2 < childCount; i2++) {
                    addListener(viewGroup.getChildAt(i2), onLayoutChangeListener, true);
                }
            }
        }

        public final boolean animateAddition(View view, Hotspot hotspot, Interpolator interpolator, long j, boolean z, boolean z2, Interpolator interpolator2, Runnable runnable) {
            if (occupiesSpace(view.getVisibility(), view.getLeft(), view.getTop(), view.getRight(), view.getBottom())) {
                return false;
            }
            addListener(view, createAdditionListener(hotspot, interpolator, j, !z, runnable), true);
            if (z2) {
                if (!(view instanceof ViewGroup)) {
                    createAndStartFadeInAnimator(view, j / 2, 0L, interpolator2);
                    return true;
                }
                long j2 = j / 6;
                createAndStartFadeInAnimator(view, j2, 0L, interpolator2);
                long j3 = j / 3;
                ViewGroup viewGroup = (ViewGroup) view;
                int childCount = viewGroup.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    createAndStartFadeInAnimator(viewGroup.getChildAt(i), j3, j2, interpolator2);
                }
                return true;
            }
            return true;
        }

        public final boolean animateRemoval(final View view, Hotspot hotspot, Interpolator interpolator, final long j, boolean z, final Runnable runnable) {
            if (occupiesSpace(view.getVisibility(), view.getLeft(), view.getTop(), view.getRight(), view.getBottom())) {
                final ViewGroup viewGroup = (ViewGroup) view.getParent();
                View.OnLayoutChangeListener createUpdateListener = createUpdateListener(interpolator, j, true);
                int childCount = viewGroup.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    View childAt = viewGroup.getChildAt(i);
                    if (!Intrinsics.areEqual(childAt, view)) {
                        addListener(childAt, createUpdateListener, false);
                    }
                }
                boolean z2 = viewGroup.getChildCount() > 1;
                if (z2) {
                    viewGroup.removeView(view);
                    viewGroup.getOverlay().add(view);
                }
                final boolean z3 = z2;
                Runnable runnable2 = new Runnable() { // from class: com.android.systemui.animation.ViewHierarchyAnimator$Companion$animateRemoval$endRunnable$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        if (z3) {
                            viewGroup.getOverlay().remove(view);
                        } else {
                            viewGroup.removeView(view);
                        }
                        Runnable runnable3 = runnable;
                        if (runnable3 != null) {
                            runnable3.run();
                        }
                    }
                };
                Bound bound = Bound.LEFT;
                Pair pair = TuplesKt.to(bound, Integer.valueOf(view.getLeft()));
                Bound bound2 = Bound.TOP;
                Pair pair2 = TuplesKt.to(bound2, Integer.valueOf(view.getTop()));
                Bound bound3 = Bound.RIGHT;
                Pair pair3 = TuplesKt.to(bound3, Integer.valueOf(view.getRight()));
                Bound bound4 = Bound.BOTTOM;
                Map<Bound, Integer> mapOf = MapsKt__MapsKt.mapOf(new Pair[]{pair, pair2, pair3, TuplesKt.to(bound4, Integer.valueOf(view.getBottom()))});
                Map<Bound, Integer> processEndValuesForRemoval = processEndValuesForRemoval(hotspot, view, view.getLeft(), view.getTop(), view.getRight(), view.getBottom(), z);
                LinkedHashSet linkedHashSet = new LinkedHashSet();
                if (view.getLeft() != ((Number) MapsKt__MapsKt.getValue(processEndValuesForRemoval, bound)).intValue()) {
                    linkedHashSet.add(bound);
                }
                if (view.getTop() != ((Number) MapsKt__MapsKt.getValue(processEndValuesForRemoval, bound2)).intValue()) {
                    linkedHashSet.add(bound2);
                }
                if (view.getRight() != ((Number) MapsKt__MapsKt.getValue(processEndValuesForRemoval, bound3)).intValue()) {
                    linkedHashSet.add(bound3);
                }
                if (view.getBottom() != ((Number) MapsKt__MapsKt.getValue(processEndValuesForRemoval, bound4)).intValue()) {
                    linkedHashSet.add(bound4);
                }
                startAnimation(view, linkedHashSet, mapOf, processEndValuesForRemoval, interpolator, j, true, runnable2);
                if (!(view instanceof ViewGroup)) {
                    long j2 = j / 2;
                    view.animate().alpha(ActionBarShadowController.ELEVATION_LOW).setInterpolator(Interpolators.ALPHA_OUT).setDuration(j2).setStartDelay(j2).start();
                    return true;
                }
                ViewGroup viewGroup2 = (ViewGroup) view;
                shiftChildrenForRemoval(viewGroup2, hotspot, processEndValuesForRemoval, interpolator, j);
                final float[] fArr = new float[viewGroup2.getChildCount()];
                int childCount2 = viewGroup2.getChildCount();
                for (int i2 = 0; i2 < childCount2; i2++) {
                    fArr[i2] = viewGroup2.getChildAt(i2).getAlpha();
                }
                ValueAnimator ofFloat = ValueAnimator.ofFloat(1.0f, ActionBarShadowController.ELEVATION_LOW);
                ofFloat.setInterpolator(Interpolators.ALPHA_OUT);
                ofFloat.setDuration(j / 2);
                ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.animation.ViewHierarchyAnimator$Companion$animateRemoval$1
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                        int childCount3 = ((ViewGroup) view).getChildCount();
                        for (int i3 = 0; i3 < childCount3; i3++) {
                            ((ViewGroup) view).getChildAt(i3).setAlpha(((Float) valueAnimator.getAnimatedValue()).floatValue() * fArr[i3]);
                        }
                    }
                });
                ofFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.animation.ViewHierarchyAnimator$Companion$animateRemoval$2
                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animator) {
                        view.animate().alpha(ActionBarShadowController.ELEVATION_LOW).setInterpolator(Interpolators.ALPHA_OUT).setDuration(j / 2).start();
                    }
                });
                ofFloat.start();
                return true;
            }
            return false;
        }

        public final View.OnLayoutChangeListener createAdditionListener(Hotspot hotspot, Interpolator interpolator, long j, boolean z, Runnable runnable) {
            return createListener(interpolator, j, true, hotspot, z, runnable);
        }

        public final void createAndStartFadeInAnimator(final View view, long j, long j2, Interpolator interpolator) {
            ObjectAnimator ofFloat = ObjectAnimator.ofFloat(view, "alpha", 1.0f);
            ofFloat.setStartDelay(j2);
            ofFloat.setDuration(j);
            ofFloat.setInterpolator(interpolator);
            ofFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.animation.ViewHierarchyAnimator$Companion$createAndStartFadeInAnimator$1
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    view.setTag(R$id.tag_alpha_animator, null);
                }
            });
            int i = R$id.tag_alpha_animator;
            Object tag = view.getTag(i);
            ObjectAnimator objectAnimator = tag instanceof ObjectAnimator ? (ObjectAnimator) tag : null;
            if (objectAnimator != null) {
                objectAnimator.cancel();
            }
            view.setTag(i, ofFloat);
            ofFloat.start();
        }

        public final View.OnLayoutChangeListener createListener(final Interpolator interpolator, final long j, final boolean z, final Hotspot hotspot, final boolean z2, final Runnable runnable) {
            return new View.OnLayoutChangeListener() { // from class: com.android.systemui.animation.ViewHierarchyAnimator$Companion$createListener$1
                @Override // android.view.View.OnLayoutChangeListener
                public void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                    if (view == null) {
                        return;
                    }
                    ViewHierarchyAnimator.Companion companion = ViewHierarchyAnimator.Companion;
                    ViewHierarchyAnimator.Bound bound = ViewHierarchyAnimator.Bound.LEFT;
                    Integer access$getBound = ViewHierarchyAnimator.Companion.access$getBound(companion, view, bound);
                    if (access$getBound != null) {
                        i5 = access$getBound.intValue();
                    }
                    ViewHierarchyAnimator.Bound bound2 = ViewHierarchyAnimator.Bound.TOP;
                    Integer access$getBound2 = ViewHierarchyAnimator.Companion.access$getBound(companion, view, bound2);
                    if (access$getBound2 != null) {
                        i6 = access$getBound2.intValue();
                    }
                    ViewHierarchyAnimator.Bound bound3 = ViewHierarchyAnimator.Bound.RIGHT;
                    Integer access$getBound3 = ViewHierarchyAnimator.Companion.access$getBound(companion, view, bound3);
                    if (access$getBound3 != null) {
                        i7 = access$getBound3.intValue();
                    }
                    ViewHierarchyAnimator.Bound bound4 = ViewHierarchyAnimator.Bound.BOTTOM;
                    Integer access$getBound4 = ViewHierarchyAnimator.Companion.access$getBound(companion, view, bound4);
                    if (access$getBound4 != null) {
                        i8 = access$getBound4.intValue();
                    }
                    Object tag = view.getTag(R$id.tag_animator);
                    ObjectAnimator objectAnimator = tag instanceof ObjectAnimator ? (ObjectAnimator) tag : null;
                    if (objectAnimator != null) {
                        objectAnimator.cancel();
                    }
                    if (!ViewHierarchyAnimator.Companion.access$occupiesSpace(companion, view.getVisibility(), i, i2, i3, i4)) {
                        ViewHierarchyAnimator.Companion.access$setBound(companion, view, bound, i);
                        ViewHierarchyAnimator.Companion.access$setBound(companion, view, bound2, i2);
                        ViewHierarchyAnimator.Companion.access$setBound(companion, view, bound3, i3);
                        ViewHierarchyAnimator.Companion.access$setBound(companion, view, bound4, i4);
                        return;
                    }
                    Map access$processStartValues = ViewHierarchyAnimator.Companion.access$processStartValues(companion, ViewHierarchyAnimator.Hotspot.this, i, i2, i3, i4, i5, i6, i7, i8, z2);
                    Map mapOf = MapsKt__MapsKt.mapOf(new Pair[]{TuplesKt.to(bound, Integer.valueOf(i)), TuplesKt.to(bound2, Integer.valueOf(i2)), TuplesKt.to(bound3, Integer.valueOf(i3)), TuplesKt.to(bound4, Integer.valueOf(i4))});
                    LinkedHashSet linkedHashSet = new LinkedHashSet();
                    if (((Number) MapsKt__MapsKt.getValue(access$processStartValues, bound)).intValue() != i) {
                        linkedHashSet.add(bound);
                    }
                    if (((Number) MapsKt__MapsKt.getValue(access$processStartValues, bound2)).intValue() != i2) {
                        linkedHashSet.add(bound2);
                    }
                    if (((Number) MapsKt__MapsKt.getValue(access$processStartValues, bound3)).intValue() != i3) {
                        linkedHashSet.add(bound3);
                    }
                    if (((Number) MapsKt__MapsKt.getValue(access$processStartValues, bound4)).intValue() != i4) {
                        linkedHashSet.add(bound4);
                    }
                    if (!linkedHashSet.isEmpty()) {
                        ViewHierarchyAnimator.Companion.access$startAnimation(companion, view, linkedHashSet, access$processStartValues, mapOf, interpolator, j, z, runnable);
                    }
                }
            };
        }

        public final View.OnLayoutChangeListener createUpdateListener(Interpolator interpolator, long j, boolean z) {
            return createListener$default(this, interpolator, j, z, null, false, null, 56, null);
        }

        public final IntProperty<View> createViewProperty(final Bound bound) {
            return new IntProperty<View>(bound.getLabel()) { // from class: com.android.systemui.animation.ViewHierarchyAnimator$Companion$createViewProperty$1
                /* JADX DEBUG: Method merged with bridge method */
                @Override // android.util.Property
                public Integer get(View view) {
                    Integer access$getBound = ViewHierarchyAnimator.Companion.access$getBound(ViewHierarchyAnimator.Companion, view, ViewHierarchyAnimator.Bound.this);
                    return Integer.valueOf(access$getBound != null ? access$getBound.intValue() : ViewHierarchyAnimator.Bound.this.getValue(view));
                }

                /* JADX DEBUG: Method merged with bridge method */
                @Override // android.util.IntProperty
                public void setValue(View view, int i) {
                    ViewHierarchyAnimator.Companion.access$setBound(ViewHierarchyAnimator.Companion, view, ViewHierarchyAnimator.Bound.this, i);
                }
            };
        }

        public final Integer getBound(View view, Bound bound) {
            Object tag = view.getTag(bound.getOverrideTag());
            return tag instanceof Integer ? (Integer) tag : null;
        }

        public final boolean occupiesSpace(int i, int i2, int i3, int i4, int i5) {
            return (i == 8 || i2 == i4 || i3 == i5) ? false : true;
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        public final Map<Bound, Integer> processChildEndValuesForRemoval(Hotspot hotspot, int i, int i2, int i3, int i4, int i5, int i6) {
            int i7 = (i3 - i) / 2;
            int i8 = (i4 - i2) / 2;
            int[] iArr = WhenMappings.$EnumSwitchMapping$0;
            switch (iArr[hotspot.ordinal()]) {
                case 1:
                    i = (i5 / 2) - i7;
                    break;
                case 2:
                case 3:
                case 4:
                    i = -i7;
                    break;
                case 5:
                case 6:
                    break;
                case 7:
                case 8:
                case 9:
                    i = i5 - i7;
                    break;
                default:
                    throw new NoWhenBranchMatchedException();
            }
            switch (iArr[hotspot.ordinal()]) {
                case 1:
                    i2 = (i6 / 2) - i8;
                    break;
                case 2:
                case 6:
                case 9:
                    i2 = i6 - i8;
                    break;
                case 3:
                case 8:
                    break;
                case 4:
                case 5:
                case 7:
                    i2 = -i8;
                    break;
                default:
                    throw new NoWhenBranchMatchedException();
            }
            int i9 = i5;
            switch (iArr[hotspot.ordinal()]) {
                case 1:
                    i9 = i5 / 2;
                    i3 = i9 + i7;
                    break;
                case 2:
                case 3:
                case 4:
                    i3 = i7;
                    break;
                case 5:
                case 6:
                    break;
                case 7:
                case 8:
                case 9:
                    i3 = i9 + i7;
                    break;
                default:
                    throw new NoWhenBranchMatchedException();
            }
            int i10 = i6;
            switch (iArr[hotspot.ordinal()]) {
                case 1:
                    i10 = i6 / 2;
                case 2:
                case 6:
                case 9:
                    i4 = i10 + i8;
                    break;
                case 3:
                case 8:
                    break;
                case 4:
                case 5:
                case 7:
                    i4 = i8;
                    break;
                default:
                    throw new NoWhenBranchMatchedException();
            }
            return MapsKt__MapsKt.mapOf(new Pair[]{TuplesKt.to(Bound.LEFT, Integer.valueOf(i)), TuplesKt.to(Bound.TOP, Integer.valueOf(i2)), TuplesKt.to(Bound.RIGHT, Integer.valueOf(i3)), TuplesKt.to(Bound.BOTTOM, Integer.valueOf(i4))});
        }

        public final Map<Bound, Integer> processEndValuesForRemoval(Hotspot hotspot, View view, int i, int i2, int i3, int i4, boolean z) {
            DimenHolder dimenHolder;
            Map<Bound, Integer> mapOf;
            if (z && (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams)) {
                ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
                dimenHolder = new DimenHolder(marginLayoutParams.leftMargin, marginLayoutParams.topMargin, marginLayoutParams.rightMargin, marginLayoutParams.bottomMargin);
            } else {
                dimenHolder = new DimenHolder(0, 0, 0, 0);
            }
            int left = i - dimenHolder.getLeft();
            int top = i2 - dimenHolder.getTop();
            int right = dimenHolder.getRight() + i3;
            int bottom = dimenHolder.getBottom() + i4;
            switch (WhenMappings.$EnumSwitchMapping$0[hotspot.ordinal()]) {
                case 1:
                    int i5 = (left + right) / 2;
                    int i6 = (top + bottom) / 2;
                    mapOf = MapsKt__MapsKt.mapOf(new Pair[]{TuplesKt.to(Bound.LEFT, Integer.valueOf(i5)), TuplesKt.to(Bound.RIGHT, Integer.valueOf(i5)), TuplesKt.to(Bound.TOP, Integer.valueOf(i6)), TuplesKt.to(Bound.BOTTOM, Integer.valueOf(i6))});
                    break;
                case 2:
                    mapOf = MapsKt__MapsKt.mapOf(new Pair[]{TuplesKt.to(Bound.BOTTOM, Integer.valueOf(bottom)), TuplesKt.to(Bound.TOP, Integer.valueOf(bottom)), TuplesKt.to(Bound.LEFT, Integer.valueOf(left)), TuplesKt.to(Bound.RIGHT, Integer.valueOf(left))});
                    break;
                case 3:
                    mapOf = MapsKt__MapsKt.mapOf(new Pair[]{TuplesKt.to(Bound.LEFT, Integer.valueOf(left)), TuplesKt.to(Bound.RIGHT, Integer.valueOf(left)), TuplesKt.to(Bound.TOP, Integer.valueOf(i2)), TuplesKt.to(Bound.BOTTOM, Integer.valueOf(i4))});
                    break;
                case 4:
                    mapOf = MapsKt__MapsKt.mapOf(new Pair[]{TuplesKt.to(Bound.TOP, Integer.valueOf(top)), TuplesKt.to(Bound.BOTTOM, Integer.valueOf(top)), TuplesKt.to(Bound.LEFT, Integer.valueOf(left)), TuplesKt.to(Bound.RIGHT, Integer.valueOf(left))});
                    break;
                case 5:
                    mapOf = MapsKt__MapsKt.mapOf(new Pair[]{TuplesKt.to(Bound.TOP, Integer.valueOf(top)), TuplesKt.to(Bound.BOTTOM, Integer.valueOf(top)), TuplesKt.to(Bound.LEFT, Integer.valueOf(i)), TuplesKt.to(Bound.RIGHT, Integer.valueOf(i3))});
                    break;
                case 6:
                    mapOf = MapsKt__MapsKt.mapOf(new Pair[]{TuplesKt.to(Bound.BOTTOM, Integer.valueOf(bottom)), TuplesKt.to(Bound.TOP, Integer.valueOf(bottom)), TuplesKt.to(Bound.LEFT, Integer.valueOf(i)), TuplesKt.to(Bound.RIGHT, Integer.valueOf(i3))});
                    break;
                case 7:
                    mapOf = MapsKt__MapsKt.mapOf(new Pair[]{TuplesKt.to(Bound.TOP, Integer.valueOf(top)), TuplesKt.to(Bound.BOTTOM, Integer.valueOf(top)), TuplesKt.to(Bound.RIGHT, Integer.valueOf(right)), TuplesKt.to(Bound.LEFT, Integer.valueOf(right))});
                    break;
                case 8:
                    mapOf = MapsKt__MapsKt.mapOf(new Pair[]{TuplesKt.to(Bound.RIGHT, Integer.valueOf(right)), TuplesKt.to(Bound.LEFT, Integer.valueOf(right)), TuplesKt.to(Bound.TOP, Integer.valueOf(i2)), TuplesKt.to(Bound.BOTTOM, Integer.valueOf(i4))});
                    break;
                case 9:
                    mapOf = MapsKt__MapsKt.mapOf(new Pair[]{TuplesKt.to(Bound.BOTTOM, Integer.valueOf(bottom)), TuplesKt.to(Bound.TOP, Integer.valueOf(bottom)), TuplesKt.to(Bound.RIGHT, Integer.valueOf(right)), TuplesKt.to(Bound.LEFT, Integer.valueOf(right))});
                    break;
                default:
                    throw new NoWhenBranchMatchedException();
            }
            return mapOf;
        }

        public final Map<Bound, Integer> processStartValues(Hotspot hotspot, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, boolean z) {
            int i9;
            int i10;
            int i11 = i5;
            if (z) {
                i11 = i;
            }
            int i12 = i6;
            if (z) {
                i12 = i2;
            }
            int i13 = i7;
            if (z) {
                i13 = i3;
            }
            if (z) {
                i8 = i4;
            }
            int i14 = i11;
            int i15 = i12;
            int i16 = i13;
            int i17 = i8;
            if (hotspot != null) {
                int[] iArr = WhenMappings.$EnumSwitchMapping$0;
                switch (iArr[hotspot.ordinal()]) {
                    case 1:
                        i9 = (i + i3) / 2;
                        break;
                    case 2:
                    case 3:
                    case 4:
                        i9 = Math.min(i11, i);
                        break;
                    case 5:
                    case 6:
                        i9 = i;
                        break;
                    case 7:
                    case 8:
                    case 9:
                        i9 = Math.max(i13, i3);
                        break;
                    default:
                        throw new NoWhenBranchMatchedException();
                }
                switch (iArr[hotspot.ordinal()]) {
                    case 1:
                        i10 = (i2 + i4) / 2;
                        break;
                    case 2:
                    case 6:
                    case 9:
                        i10 = Math.max(i8, i4);
                        break;
                    case 3:
                    case 8:
                        i10 = i2;
                        break;
                    case 4:
                    case 5:
                    case 7:
                        i10 = Math.min(i12, i2);
                        break;
                    default:
                        throw new NoWhenBranchMatchedException();
                }
                int i18 = i3;
                switch (iArr[hotspot.ordinal()]) {
                    case 1:
                        i18 = (i + i3) / 2;
                        break;
                    case 2:
                    case 3:
                    case 4:
                        i18 = Math.min(i11, i);
                        break;
                    case 5:
                    case 6:
                        break;
                    case 7:
                    case 8:
                    case 9:
                        i18 = Math.max(i13, i3);
                        break;
                    default:
                        throw new NoWhenBranchMatchedException();
                }
                int i19 = i4;
                switch (iArr[hotspot.ordinal()]) {
                    case 1:
                        i19 = (i2 + i4) / 2;
                        break;
                    case 2:
                    case 6:
                    case 9:
                        i19 = Math.max(i8, i4);
                        break;
                    case 3:
                    case 8:
                        break;
                    case 4:
                    case 5:
                    case 7:
                        i19 = Math.min(i12, i2);
                        break;
                    default:
                        throw new NoWhenBranchMatchedException();
                }
                i14 = i9;
                int i20 = i10;
                i17 = i19;
                i16 = i18;
                i15 = i20;
            }
            return MapsKt__MapsKt.mapOf(new Pair[]{TuplesKt.to(Bound.LEFT, Integer.valueOf(i14)), TuplesKt.to(Bound.TOP, Integer.valueOf(i15)), TuplesKt.to(Bound.RIGHT, Integer.valueOf(i16)), TuplesKt.to(Bound.BOTTOM, Integer.valueOf(i17))});
        }

        public final void recursivelyRemoveListener(View view) {
            int i = R$id.tag_layout_listener;
            Object tag = view.getTag(i);
            if (tag != null && (tag instanceof View.OnLayoutChangeListener)) {
                view.setTag(i, null);
                view.removeOnLayoutChangeListener((View.OnLayoutChangeListener) tag);
            }
            if (view instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) view;
                int childCount = viewGroup.getChildCount();
                for (int i2 = 0; i2 < childCount; i2++) {
                    recursivelyRemoveListener(viewGroup.getChildAt(i2));
                }
            }
        }

        public final void setBound(View view, Bound bound, int i) {
            view.setTag(bound.getOverrideTag(), Integer.valueOf(i));
            bound.setValue(view, i);
        }

        public final void shiftChildrenForRemoval(ViewGroup viewGroup, Hotspot hotspot, Map<Bound, Integer> map, Interpolator interpolator, long j) {
            int childCount = viewGroup.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childAt = viewGroup.getChildAt(i);
                Bound bound = Bound.LEFT;
                Pair pair = TuplesKt.to(bound, Integer.valueOf(childAt.getLeft()));
                Bound bound2 = Bound.TOP;
                Pair pair2 = TuplesKt.to(bound2, Integer.valueOf(childAt.getTop()));
                Bound bound3 = Bound.RIGHT;
                Pair pair3 = TuplesKt.to(bound3, Integer.valueOf(childAt.getRight()));
                Bound bound4 = Bound.BOTTOM;
                Map mapOf = MapsKt__MapsKt.mapOf(new Pair[]{pair, pair2, pair3, TuplesKt.to(bound4, Integer.valueOf(childAt.getBottom()))});
                Map<Bound, Integer> processChildEndValuesForRemoval = processChildEndValuesForRemoval(hotspot, childAt.getLeft(), childAt.getTop(), childAt.getRight(), childAt.getBottom(), ((Number) MapsKt__MapsKt.getValue(map, bound3)).intValue() - ((Number) MapsKt__MapsKt.getValue(map, bound)).intValue(), ((Number) MapsKt__MapsKt.getValue(map, bound4)).intValue() - ((Number) MapsKt__MapsKt.getValue(map, bound2)).intValue());
                LinkedHashSet linkedHashSet = new LinkedHashSet();
                if (childAt.getLeft() != ((Number) MapsKt__MapsKt.getValue(map, bound)).intValue()) {
                    linkedHashSet.add(bound);
                }
                if (childAt.getTop() != ((Number) MapsKt__MapsKt.getValue(map, bound2)).intValue()) {
                    linkedHashSet.add(bound2);
                }
                if (childAt.getRight() != ((Number) MapsKt__MapsKt.getValue(map, bound3)).intValue()) {
                    linkedHashSet.add(bound3);
                }
                if (childAt.getBottom() != ((Number) MapsKt__MapsKt.getValue(map, bound4)).intValue()) {
                    linkedHashSet.add(bound4);
                }
                startAnimation$default(this, childAt, linkedHashSet, mapOf, processChildEndValuesForRemoval, interpolator, j, true, null, RecyclerView.ViewHolder.FLAG_IGNORE, null);
            }
        }

        public final void startAnimation(final View view, final Set<? extends Bound> set, Map<Bound, Integer> map, Map<Bound, Integer> map2, Interpolator interpolator, long j, final boolean z, final Runnable runnable) {
            List createListBuilder = CollectionsKt__CollectionsJVMKt.createListBuilder();
            Set<? extends Bound> set2 = set;
            for (Bound bound : set2) {
                createListBuilder.add(PropertyValuesHolder.ofInt((Property) ViewHierarchyAnimator.PROPERTIES.get(bound), ((Number) MapsKt__MapsKt.getValue(map, bound)).intValue(), ((Number) MapsKt__MapsKt.getValue(map2, bound)).intValue()));
            }
            PropertyValuesHolder[] propertyValuesHolderArr = (PropertyValuesHolder[]) CollectionsKt__CollectionsJVMKt.build(createListBuilder).toArray(new PropertyValuesHolder[0]);
            Object tag = view.getTag(R$id.tag_animator);
            ObjectAnimator objectAnimator = tag instanceof ObjectAnimator ? (ObjectAnimator) tag : null;
            if (objectAnimator != null) {
                objectAnimator.cancel();
            }
            ObjectAnimator ofPropertyValuesHolder = ObjectAnimator.ofPropertyValuesHolder(view, (PropertyValuesHolder[]) Arrays.copyOf(propertyValuesHolderArr, propertyValuesHolderArr.length));
            ofPropertyValuesHolder.setInterpolator(interpolator);
            ofPropertyValuesHolder.setDuration(j);
            ofPropertyValuesHolder.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.animation.ViewHierarchyAnimator$Companion$startAnimation$1
                public boolean cancelled;

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationCancel(Animator animator) {
                    this.cancelled = true;
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    Runnable runnable2;
                    view.setTag(R$id.tag_animator, null);
                    Set<ViewHierarchyAnimator.Bound> set3 = set;
                    View view2 = view;
                    for (ViewHierarchyAnimator.Bound bound2 : set3) {
                        view2.setTag(bound2.getOverrideTag(), null);
                    }
                    if (z && !this.cancelled) {
                        ViewHierarchyAnimator.Companion.access$recursivelyRemoveListener(ViewHierarchyAnimator.Companion, view);
                    }
                    if (this.cancelled || (runnable2 = runnable) == null) {
                        return;
                    }
                    runnable2.run();
                }
            });
            for (Bound bound2 : set2) {
                ViewHierarchyAnimator.Companion.setBound(view, bound2, ((Number) MapsKt__MapsKt.getValue(map, bound2)).intValue());
            }
            view.setTag(R$id.tag_animator, ofPropertyValuesHolder);
            ofPropertyValuesHolder.start();
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/animation/ViewHierarchyAnimator$DimenHolder.class */
    public static final class DimenHolder {
        public final int bottom;
        public final int left;
        public final int right;
        public final int top;

        public DimenHolder(int i, int i2, int i3, int i4) {
            this.left = i;
            this.top = i2;
            this.right = i3;
            this.bottom = i4;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj instanceof DimenHolder) {
                DimenHolder dimenHolder = (DimenHolder) obj;
                return this.left == dimenHolder.left && this.top == dimenHolder.top && this.right == dimenHolder.right && this.bottom == dimenHolder.bottom;
            }
            return false;
        }

        public final int getBottom() {
            return this.bottom;
        }

        public final int getLeft() {
            return this.left;
        }

        public final int getRight() {
            return this.right;
        }

        public final int getTop() {
            return this.top;
        }

        public int hashCode() {
            return (((((Integer.hashCode(this.left) * 31) + Integer.hashCode(this.top)) * 31) + Integer.hashCode(this.right)) * 31) + Integer.hashCode(this.bottom);
        }

        public String toString() {
            int i = this.left;
            int i2 = this.top;
            int i3 = this.right;
            int i4 = this.bottom;
            return "DimenHolder(left=" + i + ", top=" + i2 + ", right=" + i3 + ", bottom=" + i4 + ")";
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/animation/ViewHierarchyAnimator$Hotspot.class */
    public enum Hotspot {
        CENTER,
        LEFT,
        TOP_LEFT,
        TOP,
        TOP_RIGHT,
        RIGHT,
        BOTTOM_RIGHT,
        BOTTOM,
        BOTTOM_LEFT
    }

    static {
        Companion companion = new Companion(null);
        Companion = companion;
        DEFAULT_INTERPOLATOR = Interpolators.STANDARD;
        DEFAULT_ADDITION_INTERPOLATOR = Interpolators.STANDARD_DECELERATE;
        DEFAULT_REMOVAL_INTERPOLATOR = Interpolators.STANDARD_ACCELERATE;
        DEFAULT_FADE_IN_INTERPOLATOR = Interpolators.ALPHA_IN;
        Bound bound = Bound.LEFT;
        Pair pair = TuplesKt.to(bound, companion.createViewProperty(bound));
        Bound bound2 = Bound.TOP;
        Pair pair2 = TuplesKt.to(bound2, companion.createViewProperty(bound2));
        Bound bound3 = Bound.RIGHT;
        Pair pair3 = TuplesKt.to(bound3, companion.createViewProperty(bound3));
        Bound bound4 = Bound.BOTTOM;
        PROPERTIES = MapsKt__MapsKt.mapOf(new Pair[]{pair, pair2, pair3, TuplesKt.to(bound4, companion.createViewProperty(bound4))});
    }
}