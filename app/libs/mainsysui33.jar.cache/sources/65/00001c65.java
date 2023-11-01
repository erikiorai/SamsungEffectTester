package com.android.systemui.media.controls.ui;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.widget.ImageButton;
import com.android.settingslib.Utils;
import com.android.systemui.R$color;
import com.android.systemui.media.controls.models.player.MediaViewHolder;
import com.android.systemui.monet.ColorScheme;
import com.android.systemui.surfaceeffects.ripple.MultiRippleController;
import com.android.systemui.surfaceeffects.turbulencenoise.TurbulenceNoiseController;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* loaded from: mainsysui33.jar:com/android/systemui/media/controls/ui/ColorSchemeTransition.class */
public final class ColorSchemeTransition {
    public final AnimatingColorTransition accentPrimary;
    public final AnimatingColorTransition accentSecondary;
    public final int bgColor;
    public final AnimatingColorTransition colorSeamless;
    public final AnimatingColorTransition[] colorTransitions;
    public final Context context;
    public final MediaViewHolder mediaViewHolder;
    public final MultiRippleController multiRippleController;
    public final AnimatingColorTransition surfaceColor;
    public final AnimatingColorTransition textPrimary;
    public final AnimatingColorTransition textPrimaryInverse;
    public final AnimatingColorTransition textSecondary;
    public final AnimatingColorTransition textTertiary;
    public final TurbulenceNoiseController turbulenceNoiseController;

    /* renamed from: com.android.systemui.media.controls.ui.ColorSchemeTransition$1 */
    /* loaded from: mainsysui33.jar:com/android/systemui/media/controls/ui/ColorSchemeTransition$1.class */
    public final /* synthetic */ class AnonymousClass1 extends FunctionReferenceImpl implements Function3<Integer, Function1<? super ColorScheme, ? extends Integer>, Function1<? super Integer, ? extends Unit>, AnimatingColorTransition> {
        public static final AnonymousClass1 INSTANCE = new AnonymousClass1();

        public AnonymousClass1() {
            super(3, AnimatingColorTransition.class, "<init>", "<init>(ILkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;)V", 0);
        }

        public final AnimatingColorTransition invoke(int i, Function1<? super ColorScheme, Integer> function1, Function1<? super Integer, Unit> function12) {
            return new AnimatingColorTransition(i, function1, function12);
        }

        public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2, Object obj3) {
            return invoke(((Number) obj).intValue(), (Function1) obj2, (Function1) obj3);
        }
    }

    public ColorSchemeTransition(Context context, MediaViewHolder mediaViewHolder, MultiRippleController multiRippleController, TurbulenceNoiseController turbulenceNoiseController) {
        this(context, mediaViewHolder, multiRippleController, turbulenceNoiseController, AnonymousClass1.INSTANCE);
    }

    public ColorSchemeTransition(Context context, MediaViewHolder mediaViewHolder, MultiRippleController multiRippleController, TurbulenceNoiseController turbulenceNoiseController, Function3<? super Integer, ? super Function1<? super ColorScheme, Integer>, ? super Function1<? super Integer, Unit>, ? extends AnimatingColorTransition> function3) {
        this.context = context;
        this.mediaViewHolder = mediaViewHolder;
        this.multiRippleController = multiRippleController;
        this.turbulenceNoiseController = turbulenceNoiseController;
        int color = context.getColor(R$color.material_dynamic_secondary95);
        this.bgColor = color;
        AnimatingColorTransition animatingColorTransition = (AnimatingColorTransition) function3.invoke(Integer.valueOf(color), ColorSchemeTransition$surfaceColor$1.INSTANCE, new Function1<Integer, Unit>() { // from class: com.android.systemui.media.controls.ui.ColorSchemeTransition$surfaceColor$2
            {
                super(1);
            }

            public /* bridge */ /* synthetic */ Object invoke(Object obj) {
                invoke(((Number) obj).intValue());
                return Unit.INSTANCE;
            }

            public final void invoke(int i) {
                ColorStateList valueOf = ColorStateList.valueOf(i);
                ColorSchemeTransition.access$getMediaViewHolder$p(ColorSchemeTransition.this).getSeamlessIcon().setImageTintList(valueOf);
                ColorSchemeTransition.access$getMediaViewHolder$p(ColorSchemeTransition.this).getSeamlessText().setTextColor(i);
                ColorSchemeTransition.access$getMediaViewHolder$p(ColorSchemeTransition.this).getAlbumView().setBackgroundTintList(valueOf);
                ColorSchemeTransition.access$getMediaViewHolder$p(ColorSchemeTransition.this).getGutsViewHolder().setSurfaceColor(i);
            }
        });
        this.surfaceColor = animatingColorTransition;
        AnimatingColorTransition animatingColorTransition2 = (AnimatingColorTransition) function3.invoke(Integer.valueOf(loadDefaultColor(16842806)), ColorSchemeTransition$accentPrimary$1.INSTANCE, new Function1<Integer, Unit>() { // from class: com.android.systemui.media.controls.ui.ColorSchemeTransition$accentPrimary$2
            {
                super(1);
            }

            public /* bridge */ /* synthetic */ Object invoke(Object obj) {
                invoke(((Number) obj).intValue());
                return Unit.INSTANCE;
            }

            public final void invoke(int i) {
                ColorSchemeTransition.access$getMediaViewHolder$p(ColorSchemeTransition.this).getActionPlayPause().setBackgroundTintList(ColorStateList.valueOf(i));
                ColorSchemeTransition.access$getMediaViewHolder$p(ColorSchemeTransition.this).getGutsViewHolder().setAccentPrimaryColor(i);
                ColorSchemeTransition.access$getMultiRippleController$p(ColorSchemeTransition.this).updateColor(i);
                ColorSchemeTransition.access$getTurbulenceNoiseController$p(ColorSchemeTransition.this).updateNoiseColor(i);
            }
        });
        this.accentPrimary = animatingColorTransition2;
        AnimatingColorTransition animatingColorTransition3 = (AnimatingColorTransition) function3.invoke(Integer.valueOf(loadDefaultColor(16842806)), ColorSchemeTransition$accentSecondary$1.INSTANCE, new Function1<Integer, Unit>() { // from class: com.android.systemui.media.controls.ui.ColorSchemeTransition$accentSecondary$2
            {
                super(1);
            }

            public /* bridge */ /* synthetic */ Object invoke(Object obj) {
                invoke(((Number) obj).intValue());
                return Unit.INSTANCE;
            }

            public final void invoke(int i) {
                ColorStateList valueOf = ColorStateList.valueOf(i);
                Drawable background = ColorSchemeTransition.access$getMediaViewHolder$p(ColorSchemeTransition.this).getSeamlessButton().getBackground();
                RippleDrawable rippleDrawable = background instanceof RippleDrawable ? (RippleDrawable) background : null;
                if (rippleDrawable != null) {
                    rippleDrawable.setColor(valueOf);
                    rippleDrawable.setEffectColor(valueOf);
                }
            }
        });
        this.accentSecondary = animatingColorTransition3;
        AnimatingColorTransition animatingColorTransition4 = (AnimatingColorTransition) function3.invoke(Integer.valueOf(loadDefaultColor(16842806)), new Function1<ColorScheme, Integer>() { // from class: com.android.systemui.media.controls.ui.ColorSchemeTransition$colorSeamless$1
            {
                super(1);
            }

            /* JADX DEBUG: Method merged with bridge method */
            public final Integer invoke(ColorScheme colorScheme) {
                return Integer.valueOf((ColorSchemeTransition.access$getContext$p(ColorSchemeTransition.this).getResources().getConfiguration().uiMode & 48) == 32 ? colorScheme.getAccent1().get(2).intValue() : colorScheme.getAccent1().get(3).intValue());
            }
        }, new Function1<Integer, Unit>() { // from class: com.android.systemui.media.controls.ui.ColorSchemeTransition$colorSeamless$2
            {
                super(1);
            }

            public /* bridge */ /* synthetic */ Object invoke(Object obj) {
                invoke(((Number) obj).intValue());
                return Unit.INSTANCE;
            }

            public final void invoke(int i) {
                ColorSchemeTransition.access$getMediaViewHolder$p(ColorSchemeTransition.this).getSeamlessButton().setBackgroundTintList(ColorStateList.valueOf(i));
            }
        });
        this.colorSeamless = animatingColorTransition4;
        AnimatingColorTransition animatingColorTransition5 = (AnimatingColorTransition) function3.invoke(Integer.valueOf(loadDefaultColor(16842806)), ColorSchemeTransition$textPrimary$1.INSTANCE, new Function1<Integer, Unit>() { // from class: com.android.systemui.media.controls.ui.ColorSchemeTransition$textPrimary$2
            {
                super(1);
            }

            public /* bridge */ /* synthetic */ Object invoke(Object obj) {
                invoke(((Number) obj).intValue());
                return Unit.INSTANCE;
            }

            public final void invoke(int i) {
                ColorSchemeTransition.access$getMediaViewHolder$p(ColorSchemeTransition.this).getTitleText().setTextColor(i);
                ColorStateList valueOf = ColorStateList.valueOf(i);
                ColorSchemeTransition.access$getMediaViewHolder$p(ColorSchemeTransition.this).getSeekBar().getThumb().setTintList(valueOf);
                ColorSchemeTransition.access$getMediaViewHolder$p(ColorSchemeTransition.this).getSeekBar().setProgressTintList(valueOf);
                ColorSchemeTransition.access$getMediaViewHolder$p(ColorSchemeTransition.this).getScrubbingElapsedTimeView().setTextColor(valueOf);
                ColorSchemeTransition.access$getMediaViewHolder$p(ColorSchemeTransition.this).getScrubbingTotalTimeView().setTextColor(valueOf);
                for (ImageButton imageButton : ColorSchemeTransition.access$getMediaViewHolder$p(ColorSchemeTransition.this).getTransparentActionButtons()) {
                    imageButton.setImageTintList(valueOf);
                }
                ColorSchemeTransition.access$getMediaViewHolder$p(ColorSchemeTransition.this).getGutsViewHolder().setTextPrimaryColor(i);
            }
        });
        this.textPrimary = animatingColorTransition5;
        AnimatingColorTransition animatingColorTransition6 = (AnimatingColorTransition) function3.invoke(Integer.valueOf(loadDefaultColor(16842809)), ColorSchemeTransition$textPrimaryInverse$1.INSTANCE, new Function1<Integer, Unit>() { // from class: com.android.systemui.media.controls.ui.ColorSchemeTransition$textPrimaryInverse$2
            {
                super(1);
            }

            public /* bridge */ /* synthetic */ Object invoke(Object obj) {
                invoke(((Number) obj).intValue());
                return Unit.INSTANCE;
            }

            public final void invoke(int i) {
                ColorSchemeTransition.access$getMediaViewHolder$p(ColorSchemeTransition.this).getActionPlayPause().setImageTintList(ColorStateList.valueOf(i));
            }
        });
        this.textPrimaryInverse = animatingColorTransition6;
        AnimatingColorTransition animatingColorTransition7 = (AnimatingColorTransition) function3.invoke(Integer.valueOf(loadDefaultColor(16842808)), ColorSchemeTransition$textSecondary$1.INSTANCE, new Function1<Integer, Unit>() { // from class: com.android.systemui.media.controls.ui.ColorSchemeTransition$textSecondary$2
            {
                super(1);
            }

            public /* bridge */ /* synthetic */ Object invoke(Object obj) {
                invoke(((Number) obj).intValue());
                return Unit.INSTANCE;
            }

            public final void invoke(int i) {
                ColorSchemeTransition.access$getMediaViewHolder$p(ColorSchemeTransition.this).getArtistText().setTextColor(i);
            }
        });
        this.textSecondary = animatingColorTransition7;
        AnimatingColorTransition animatingColorTransition8 = (AnimatingColorTransition) function3.invoke(Integer.valueOf(loadDefaultColor(16843282)), ColorSchemeTransition$textTertiary$1.INSTANCE, new Function1<Integer, Unit>() { // from class: com.android.systemui.media.controls.ui.ColorSchemeTransition$textTertiary$2
            {
                super(1);
            }

            public /* bridge */ /* synthetic */ Object invoke(Object obj) {
                invoke(((Number) obj).intValue());
                return Unit.INSTANCE;
            }

            public final void invoke(int i) {
                ColorSchemeTransition.access$getMediaViewHolder$p(ColorSchemeTransition.this).getSeekBar().setProgressBackgroundTintList(ColorStateList.valueOf(i));
            }
        });
        this.textTertiary = animatingColorTransition8;
        this.colorTransitions = new AnimatingColorTransition[]{animatingColorTransition, animatingColorTransition4, animatingColorTransition2, animatingColorTransition3, animatingColorTransition5, animatingColorTransition6, animatingColorTransition7, animatingColorTransition8};
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.controls.ui.ColorSchemeTransition$colorSeamless$1.invoke(com.android.systemui.monet.ColorScheme):java.lang.Integer] */
    public static final /* synthetic */ Context access$getContext$p(ColorSchemeTransition colorSchemeTransition) {
        return colorSchemeTransition.context;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.controls.ui.ColorSchemeTransition$accentPrimary$2.invoke(int):void, com.android.systemui.media.controls.ui.ColorSchemeTransition$accentSecondary$2.invoke(int):void, com.android.systemui.media.controls.ui.ColorSchemeTransition$colorSeamless$2.invoke(int):void, com.android.systemui.media.controls.ui.ColorSchemeTransition$surfaceColor$2.invoke(int):void, com.android.systemui.media.controls.ui.ColorSchemeTransition$textPrimary$2.invoke(int):void, com.android.systemui.media.controls.ui.ColorSchemeTransition$textPrimaryInverse$2.invoke(int):void, com.android.systemui.media.controls.ui.ColorSchemeTransition$textSecondary$2.invoke(int):void, com.android.systemui.media.controls.ui.ColorSchemeTransition$textTertiary$2.invoke(int):void] */
    public static final /* synthetic */ MediaViewHolder access$getMediaViewHolder$p(ColorSchemeTransition colorSchemeTransition) {
        return colorSchemeTransition.mediaViewHolder;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.controls.ui.ColorSchemeTransition$accentPrimary$2.invoke(int):void] */
    public static final /* synthetic */ MultiRippleController access$getMultiRippleController$p(ColorSchemeTransition colorSchemeTransition) {
        return colorSchemeTransition.multiRippleController;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.controls.ui.ColorSchemeTransition$accentPrimary$2.invoke(int):void] */
    public static final /* synthetic */ TurbulenceNoiseController access$getTurbulenceNoiseController$p(ColorSchemeTransition colorSchemeTransition) {
        return colorSchemeTransition.turbulenceNoiseController;
    }

    public final AnimatingColorTransition getAccentPrimary() {
        return this.accentPrimary;
    }

    public final int getBgColor() {
        return this.bgColor;
    }

    public final int loadDefaultColor(int i) {
        return Utils.getColorAttr(this.context, i).getDefaultColor();
    }

    public final boolean updateColorScheme(ColorScheme colorScheme) {
        boolean z = false;
        for (AnimatingColorTransition animatingColorTransition : this.colorTransitions) {
            z = animatingColorTransition.updateColorScheme(colorScheme) || z;
        }
        if (colorScheme != null) {
            this.mediaViewHolder.getGutsViewHolder().setColorScheme(colorScheme);
        }
        return z;
    }
}