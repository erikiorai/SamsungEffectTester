package com.android.systemui.controls;

import android.content.Context;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;
import com.android.settingslib.widget.ActionBarShadowController;
import com.android.systemui.Prefs;
import com.android.systemui.R$dimen;
import com.android.systemui.R$id;
import com.android.systemui.R$layout;
import com.android.systemui.recents.TriangleShape;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: mainsysui33.jar:com/android/systemui/controls/TooltipManager.class */
public final class TooltipManager {
    public static final Companion Companion = new Companion(null);
    public final View arrowView;
    public final boolean below;
    public final View dismissView;
    public final ViewGroup layout;
    public final int maxTimesShown;
    public final String preferenceName;
    public final Function1<Integer, Unit> preferenceStorer;
    public int shown;
    public final TextView textView;

    /* loaded from: mainsysui33.jar:com/android/systemui/controls/TooltipManager$Companion.class */
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public TooltipManager(final Context context, String str, int i, boolean z) {
        this.preferenceName = str;
        this.maxTimesShown = i;
        this.below = z;
        this.shown = Prefs.getInt(context, str, 0);
        ViewGroup viewGroup = (ViewGroup) LayoutInflater.from(context).inflate(R$layout.controls_onboarding, (ViewGroup) null);
        this.layout = viewGroup;
        this.preferenceStorer = new Function1<Integer, Unit>() { // from class: com.android.systemui.controls.TooltipManager$preferenceStorer$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            public /* bridge */ /* synthetic */ Object invoke(Object obj) {
                invoke(((Number) obj).intValue());
                return Unit.INSTANCE;
            }

            public final void invoke(int i2) {
                Prefs.putInt(context, TooltipManager.access$getPreferenceName$p(this), i2);
            }
        };
        viewGroup.setAlpha(ActionBarShadowController.ELEVATION_LOW);
        this.textView = (TextView) viewGroup.requireViewById(R$id.onboarding_text);
        View requireViewById = viewGroup.requireViewById(R$id.dismiss);
        requireViewById.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.controls.TooltipManager$dismissView$1$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                TooltipManager.this.hide(true);
            }
        });
        this.dismissView = requireViewById;
        View requireViewById2 = viewGroup.requireViewById(R$id.arrow);
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(16843829, typedValue, true);
        int color = context.getResources().getColor(typedValue.resourceId, context.getTheme());
        int dimensionPixelSize = context.getResources().getDimensionPixelSize(R$dimen.recents_onboarding_toast_arrow_corner_radius);
        ViewGroup.LayoutParams layoutParams = requireViewById2.getLayoutParams();
        ShapeDrawable shapeDrawable = new ShapeDrawable(TriangleShape.create(layoutParams.width, layoutParams.height, z));
        Paint paint = shapeDrawable.getPaint();
        paint.setColor(color);
        paint.setPathEffect(new CornerPathEffect(dimensionPixelSize));
        requireViewById2.setBackground(shapeDrawable);
        this.arrowView = requireViewById2;
        if (z) {
            return;
        }
        viewGroup.removeView(requireViewById2);
        viewGroup.addView(requireViewById2);
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) requireViewById2.getLayoutParams();
        marginLayoutParams.bottomMargin = marginLayoutParams.topMargin;
        marginLayoutParams.topMargin = 0;
    }

    public /* synthetic */ TooltipManager(Context context, String str, int i, boolean z, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, str, (i2 & 4) != 0 ? 2 : i, (i2 & 8) != 0 ? true : z);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.controls.TooltipManager$show$1.run():void] */
    public static final /* synthetic */ boolean access$getBelow$p(TooltipManager tooltipManager) {
        return tooltipManager.below;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.controls.TooltipManager$preferenceStorer$1.invoke(int):void] */
    public static final /* synthetic */ String access$getPreferenceName$p(TooltipManager tooltipManager) {
        return tooltipManager.preferenceName;
    }

    public final ViewGroup getLayout() {
        return this.layout;
    }

    public final void hide(final boolean z) {
        if (this.layout.getAlpha() == ActionBarShadowController.ELEVATION_LOW) {
            return;
        }
        this.layout.post(new Runnable() { // from class: com.android.systemui.controls.TooltipManager$hide$1
            @Override // java.lang.Runnable
            public final void run() {
                if (z) {
                    this.getLayout().animate().alpha(ActionBarShadowController.ELEVATION_LOW).withLayer().setStartDelay(0L).setDuration(100L).setInterpolator(new AccelerateInterpolator()).start();
                    return;
                }
                this.getLayout().animate().cancel();
                this.getLayout().setAlpha(ActionBarShadowController.ELEVATION_LOW);
            }
        });
    }

    public final boolean shouldShow() {
        return this.shown < this.maxTimesShown;
    }

    public final void show(int i, final int i2, final int i3) {
        if (shouldShow()) {
            this.textView.setText(i);
            int i4 = this.shown + 1;
            this.shown = i4;
            this.preferenceStorer.invoke(Integer.valueOf(i4));
            this.layout.post(new Runnable() { // from class: com.android.systemui.controls.TooltipManager$show$1
                @Override // java.lang.Runnable
                public final void run() {
                    int[] iArr;
                    TooltipManager.this.getLayout().getLocationOnScreen(new int[2]);
                    TooltipManager.this.getLayout().setTranslationX((i2 - iArr[0]) - (TooltipManager.this.getLayout().getWidth() / 2));
                    TooltipManager.this.getLayout().setTranslationY((i3 - iArr[1]) - (!TooltipManager.access$getBelow$p(TooltipManager.this) ? TooltipManager.this.getLayout().getHeight() : 0));
                    boolean z = false;
                    if (TooltipManager.this.getLayout().getAlpha() == ActionBarShadowController.ELEVATION_LOW) {
                        z = true;
                    }
                    if (z) {
                        TooltipManager.this.getLayout().animate().alpha(1.0f).withLayer().setStartDelay(500L).setDuration(300L).setInterpolator(new DecelerateInterpolator()).start();
                    }
                }
            });
        }
    }
}