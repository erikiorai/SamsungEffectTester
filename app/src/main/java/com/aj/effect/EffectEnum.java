package com.aj.effect;

import com.android.keyguard.sec.KeyguardEffectViewController;

import java.util.Arrays;
import java.util.List;

public class EffectEnum {
    public int name;
    public int assigned;
    public int drawable;

    public EffectEnum(Integer name, int assigned, Integer drawable)
    {
        this.name = name == null ? R.string.unlock_effect : name;
        this.assigned = assigned; // TODO default effect
        this.drawable = drawable == null ? R.drawable.setting_preview_unlock_none : drawable;
    }

    public static final EffectEnum NONE = new EffectEnum(R.string.unlock_effect_none, 0, null);
    public static final EffectEnum RIPPLE = new EffectEnum(R.string.unlock_effect_ripple, 1, R.drawable.setting_preview_unlock_ripple);
    public static final EffectEnum LIGHTING = new EffectEnum(R.string.light_effect, 2, R.drawable.setting_preview_unlock_light);
    public static final EffectEnum POPPINGCOLOURS = new EffectEnum(R.string.unlock_effect_popping, 3, R.drawable.setting_preview_unlock_poppingcolor);
    public static final EffectEnum WATERCOLOUR = new EffectEnum(R.string.unlock_effect_watercolor, 4, R.drawable.setting_preview_unlock_watercolor);
    public static final EffectEnum BLIND = new EffectEnum(R.string.blind_effect, 5, R.drawable.setting_preview_unlock_blind);
    public static final EffectEnum EFFECT_MASS_TENSION = new EffectEnum(null, KeyguardEffectViewController.EFFECT_MASS_TENSION, null); // TODO: tension
    public static final EffectEnum EFFECT_MASS_RIPPLE = new EffectEnum(R.string.unlock_effect_simple_ripple, 7, R.drawable.setting_preview_unlock_stoneskipping);
    public static final EffectEnum BRILLIANTRING = new EffectEnum(R.string.unlock_effect_brilliant_ring, 8, R.drawable.setting_preview_unlock_brilliantring);
    public static final EffectEnum BRILLIANTCUT = new EffectEnum(R.string.brilliant_cut, 9, R.drawable.setting_preview_unlock_brilliantcut);
    public static final EffectEnum INDIGODIFFUSION = new EffectEnum(R.string.unlock_effect_montblanc, 10, R.drawable.setting_preview_unlock_montblanc);
    public static final EffectEnum ABSTRACTTILES = new EffectEnum(R.string.unlock_effect_abstract, 11, R.drawable.setting_preview_unlock_abstract_tiles);
    public static final EffectEnum GEOMETRICMOSAIC = new EffectEnum(R.string.unlock_effect_geometric_mosaic, 12, R.drawable.setting_preview_unlock_geometric_mosaic);
    public static final EffectEnum WATERDROPLET = new EffectEnum(R.string.unlock_effect_liquid, 13, R.drawable.setting_preview_unlock_liquid_w); // TODO lowres waterdroplet drawable
    public static final EffectEnum SPARKLINGBUBBLES = new EffectEnum(R.string.unlock_effect_particle, 14, R.drawable.setting_preview_unlock_particle);
    public static final EffectEnum COLOURDROPLET = new EffectEnum(R.string.unlock_effect_colour_droplet, 15, R.drawable.setting_preview_unlock_liquid);
    public static final EffectEnum MORPHING = new EffectEnum(R.string.unlock_effect, 18, null);
    public static final EffectEnum POPPINGGOODLOCK = new EffectEnum(R.string.unlock_effect_poppinggl, 19, null);
    public static final EffectEnum RECTANGLETRAVELLER = new EffectEnum(R.string.unlock_effect_rectangle, 20, null);
    public static final EffectEnum BOUNCINGCOLOR = new EffectEnum(R.string.unlock_effect_bouncing, 21, null);

    private static final List<EffectEnum> effectList = Arrays.asList(NONE,
            RIPPLE, LIGHTING, POPPINGCOLOURS, WATERCOLOUR, BLIND, EFFECT_MASS_TENSION,
            EFFECT_MASS_RIPPLE, BRILLIANTRING, BRILLIANTCUT, INDIGODIFFUSION, ABSTRACTTILES,
            GEOMETRICMOSAIC, WATERDROPLET, SPARKLINGBUBBLES, COLOURDROPLET);

    public static EffectEnum getByInt(int assigned) {
        for (EffectEnum e : effectList) {
            if (e.assigned == assigned)
                return e;
        }
        return INDIGODIFFUSION; // TODO DEFAULT EFFECT;
    }
}