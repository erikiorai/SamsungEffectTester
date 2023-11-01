package com.android.keyguard;

import android.content.Context;
import android.view.ViewGroup;
import com.android.systemui.R$dimen;
import com.android.systemui.R$id;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.shared.animation.UnfoldConstantTranslateAnimator;
import com.android.systemui.unfold.util.NaturalRotationUnfoldProgressProvider;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.collections.SetsKt__SetsKt;
import kotlin.jvm.functions.Function0;

/* loaded from: mainsysui33.jar:com/android/keyguard/KeyguardUnfoldTransition.class */
public final class KeyguardUnfoldTransition {
    public final Context context;
    public final Function0<Boolean> filterKeyguard;
    public final Function0<Boolean> filterKeyguardAndSplitShadeOnly;
    public boolean statusViewCentered;
    public final Lazy translateAnimator$delegate;

    public KeyguardUnfoldTransition(Context context, final StatusBarStateController statusBarStateController, final NaturalRotationUnfoldProgressProvider naturalRotationUnfoldProgressProvider) {
        this.context = context;
        this.filterKeyguardAndSplitShadeOnly = new Function0<Boolean>() { // from class: com.android.keyguard.KeyguardUnfoldTransition$filterKeyguardAndSplitShadeOnly$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            /* JADX DEBUG: Method merged with bridge method */
            /* renamed from: invoke */
            public final Boolean m705invoke() {
                boolean z = true;
                if (StatusBarStateController.this.getState() != 1 || this.getStatusViewCentered()) {
                    z = false;
                }
                return Boolean.valueOf(z);
            }
        };
        this.filterKeyguard = new Function0<Boolean>() { // from class: com.android.keyguard.KeyguardUnfoldTransition$filterKeyguard$1
            {
                super(0);
            }

            /* JADX DEBUG: Method merged with bridge method */
            /* renamed from: invoke */
            public final Boolean m704invoke() {
                boolean z = true;
                if (StatusBarStateController.this.getState() != 1) {
                    z = false;
                }
                return Boolean.valueOf(z);
            }
        };
        this.translateAnimator$delegate = LazyKt__LazyJVMKt.lazy(new Function0<UnfoldConstantTranslateAnimator>() { // from class: com.android.keyguard.KeyguardUnfoldTransition$translateAnimator$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            /* JADX DEBUG: Method merged with bridge method */
            /* renamed from: invoke */
            public final UnfoldConstantTranslateAnimator m706invoke() {
                int i = R$id.keyguard_status_area;
                UnfoldConstantTranslateAnimator.Direction direction = UnfoldConstantTranslateAnimator.Direction.START;
                UnfoldConstantTranslateAnimator.ViewIdToTranslate viewIdToTranslate = new UnfoldConstantTranslateAnimator.ViewIdToTranslate(i, direction, KeyguardUnfoldTransition.access$getFilterKeyguard$p(KeyguardUnfoldTransition.this));
                UnfoldConstantTranslateAnimator.ViewIdToTranslate viewIdToTranslate2 = new UnfoldConstantTranslateAnimator.ViewIdToTranslate(R$id.lockscreen_clock_view_large, direction, KeyguardUnfoldTransition.access$getFilterKeyguardAndSplitShadeOnly$p(KeyguardUnfoldTransition.this));
                UnfoldConstantTranslateAnimator.ViewIdToTranslate viewIdToTranslate3 = new UnfoldConstantTranslateAnimator.ViewIdToTranslate(R$id.lockscreen_clock_view, direction, KeyguardUnfoldTransition.access$getFilterKeyguard$p(KeyguardUnfoldTransition.this));
                int i2 = R$id.notification_stack_scroller;
                UnfoldConstantTranslateAnimator.Direction direction2 = UnfoldConstantTranslateAnimator.Direction.END;
                return new UnfoldConstantTranslateAnimator(SetsKt__SetsKt.setOf(new UnfoldConstantTranslateAnimator.ViewIdToTranslate[]{viewIdToTranslate, viewIdToTranslate2, viewIdToTranslate3, new UnfoldConstantTranslateAnimator.ViewIdToTranslate(i2, direction2, KeyguardUnfoldTransition.access$getFilterKeyguardAndSplitShadeOnly$p(KeyguardUnfoldTransition.this)), new UnfoldConstantTranslateAnimator.ViewIdToTranslate(R$id.start_button, direction, KeyguardUnfoldTransition.access$getFilterKeyguard$p(KeyguardUnfoldTransition.this)), new UnfoldConstantTranslateAnimator.ViewIdToTranslate(R$id.end_button, direction2, KeyguardUnfoldTransition.access$getFilterKeyguard$p(KeyguardUnfoldTransition.this))}), naturalRotationUnfoldProgressProvider);
            }
        });
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.keyguard.KeyguardUnfoldTransition$translateAnimator$2.invoke():com.android.systemui.shared.animation.UnfoldConstantTranslateAnimator] */
    public static final /* synthetic */ Function0 access$getFilterKeyguard$p(KeyguardUnfoldTransition keyguardUnfoldTransition) {
        return keyguardUnfoldTransition.filterKeyguard;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.keyguard.KeyguardUnfoldTransition$translateAnimator$2.invoke():com.android.systemui.shared.animation.UnfoldConstantTranslateAnimator] */
    public static final /* synthetic */ Function0 access$getFilterKeyguardAndSplitShadeOnly$p(KeyguardUnfoldTransition keyguardUnfoldTransition) {
        return keyguardUnfoldTransition.filterKeyguardAndSplitShadeOnly;
    }

    public final boolean getStatusViewCentered() {
        return this.statusViewCentered;
    }

    public final UnfoldConstantTranslateAnimator getTranslateAnimator() {
        return (UnfoldConstantTranslateAnimator) this.translateAnimator$delegate.getValue();
    }

    public final void setStatusViewCentered(boolean z) {
        this.statusViewCentered = z;
    }

    public final void setup(ViewGroup viewGroup) {
        getTranslateAnimator().init(viewGroup, this.context.getResources().getDimensionPixelSize(R$dimen.keyguard_unfold_translation_x));
    }
}