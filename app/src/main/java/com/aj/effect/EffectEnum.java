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
        this.assigned = assigned;
        this.drawable = drawable == null ? R.drawable.setting_preview_unlock_nothing : drawable;
    }

    public static final EffectEnum NONE = new EffectEnum(R.string.unlock_effect_none, KeyguardEffectViewController.EFFECT_NONE, R.drawable.setting_preview_unlock_none);
    public static final EffectEnum RIPPLE = new EffectEnum(R.string.unlock_effect_ripple, KeyguardEffectViewController.EFFECT_RIPPLE, R.drawable.setting_preview_unlock_ripple);
    public static final EffectEnum LIGHTING = new EffectEnum(R.string.light_effect, KeyguardEffectViewController.EFFECT_LIGHT, R.drawable.setting_preview_unlock_lensflare);
    public static final EffectEnum POPPINGCOLOURS = new EffectEnum(R.string.unlock_effect_popping, KeyguardEffectViewController.EFFECT_POPPING_COLOR, R.drawable.setting_preview_unlock_poppingcolor);
    public static final EffectEnum WATERCOLOUR = new EffectEnum(R.string.unlock_effect_watercolor, KeyguardEffectViewController.EFFECT_WATERCOLOR, R.drawable.setting_preview_unlock_watercolor);
    public static final EffectEnum BLIND = new EffectEnum(R.string.blind_effect, KeyguardEffectViewController.EFFECT_BLIND, R.drawable.setting_preview_unlock_blind);
    public static final EffectEnum TENSION = new EffectEnum(R.string.tension_effect, KeyguardEffectViewController.EFFECT_MASS_TENSION, null); // TODO: tension
    public static final EffectEnum STONESKIPPING = new EffectEnum(R.string.unlock_effect_simple_ripple, KeyguardEffectViewController.EFFECT_MASS_RIPPLE, R.drawable.setting_preview_unlock_stoneskipping);
    public static final EffectEnum BRILLIANTRING = new EffectEnum(R.string.unlock_effect_brilliant_ring, KeyguardEffectViewController.EFFECT_BRILLIANTRING, R.drawable.setting_preview_unlock_brilliantring);
    public static final EffectEnum BRILLIANTCUT = new EffectEnum(R.string.brilliant_cut, KeyguardEffectViewController.EFFECT_BRILLIANTCUT, R.drawable.setting_preview_unlock_brilliantcut);
    public static final EffectEnum INDIGODIFFUSION = new EffectEnum(R.string.unlock_effect_montblanc, KeyguardEffectViewController.EFFECT_MONTBLANC, R.drawable.setting_preview_unlock_montblanc);
    public static final EffectEnum ABSTRACTTILES = new EffectEnum(R.string.unlock_effect_abstract, KeyguardEffectViewController.EFFECT_ABSTRACTTILE, R.drawable.setting_preview_unlock_abstract_tiles);
    public static final EffectEnum GEOMETRICMOSAIC = new EffectEnum(R.string.unlock_effect_geometric_mosaic, KeyguardEffectViewController.EFFECT_GEOMETRICMOSAIC, R.drawable.setting_preview_unlock_geometric_mosaic);
    public static final EffectEnum WATERDROPLET = new EffectEnum(R.string.unlock_effect_liquid, KeyguardEffectViewController.EFFECT_LIQUID, R.drawable.setting_preview_unlock_liquid); // TODO lowres waterdroplet drawable
    public static final EffectEnum SPARKLINGBUBBLES = new EffectEnum(R.string.unlock_effect_particle, KeyguardEffectViewController.EFFECT_PARTICLE, R.drawable.setting_preview_unlock_particle);
    public static final EffectEnum COLOURDROPLET = new EffectEnum(R.string.unlock_effect_colour_droplet, KeyguardEffectViewController.EFFECT_COLOURDROPLET, R.drawable.setting_preview_unlock_colour_droplet);

    public static final EffectEnum LIQUID = new EffectEnum(R.string.unlock_effect_liquid_old, 16, R.drawable.setting_preview_unlock_liquid_old);

    // todo fix seasonal images, kor note 3 5.0.1 firmware
    public static final EffectEnum SEASONAL = new EffectEnum(R.string.festival_seasonal, KeyguardEffectViewController.EFFECT_SEASONAL, null);
    public static final EffectEnum SPRING = new EffectEnum(R.string.festival_effect_spring, KeyguardEffectViewController.EFFECT_SPRING, null);
    public static final EffectEnum SUMMER = new EffectEnum(R.string.festival_effect_summer, KeyguardEffectViewController.EFFECT_SUMMER, null);
    public static final EffectEnum AUTUMN = new EffectEnum(R.string.festival_effect_fall, KeyguardEffectViewController.EFFECT_AUTUMN, null);
    public static final EffectEnum WINTER = new EffectEnum(R.string.festival_effect_winter, KeyguardEffectViewController.EFFECT_WINTER, null);


    public static final EffectEnum MORPHING = new EffectEnum(R.string.unlock_effect, 18, null);
    public static final EffectEnum POPPINGGOODLOCK = new EffectEnum(R.string.unlock_effect_poppinggl, 19, null);
    public static final EffectEnum RECTANGLETRAVELLER = new EffectEnum(R.string.unlock_effect_rectangle, 20, null);
    public static final EffectEnum BOUNCINGCOLOR = new EffectEnum(R.string.unlock_effect_bouncing, 21, null);

    /*public static final List<EffectEnum> effectList = Arrays.asList(NONE,
            RIPPLE, LIGHTING, POPPINGCOLOURS, WATERCOLOUR, BLIND, TENSION,
            STONESKIPPING, BRILLIANTRING, BRILLIANTCUT, INDIGODIFFUSION, ABSTRACTTILES,
            GEOMETRICMOSAIC, WATERDROPLET, SPARKLINGBUBBLES, COLOURDROPLET);

    public static EffectEnum getByInt(int assigned) {
        for (EffectEnum e : effectList) {
            if (e.assigned == assigned)
                return e;
        }
        return INDIGODIFFUSION; // TODO DEFAULT EFFECT;
    }*/
}