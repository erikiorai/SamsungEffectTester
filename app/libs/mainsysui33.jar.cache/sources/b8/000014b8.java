package com.android.systemui.controls.ui;

import com.android.systemui.R$color;
import com.android.systemui.R$drawable;
import java.util.Map;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.MapsKt__MapWithDefaultKt;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.functions.Function1;

/* loaded from: mainsysui33.jar:com/android/systemui/controls/ui/RenderInfoKt.class */
public final class RenderInfoKt {
    public static final Map<Integer, Pair<Integer, Integer>> deviceColorMap = MapsKt__MapWithDefaultKt.withDefault(MapsKt__MapsKt.mapOf(new Pair[]{TuplesKt.to(49001, new Pair(Integer.valueOf(R$color.control_default_foreground), Integer.valueOf(R$color.control_default_background))), TuplesKt.to(49002, new Pair(Integer.valueOf(R$color.thermo_heat_foreground), Integer.valueOf(R$color.control_enabled_thermo_heat_background))), TuplesKt.to(49003, new Pair(Integer.valueOf(R$color.thermo_cool_foreground), Integer.valueOf(R$color.control_enabled_thermo_cool_background))), TuplesKt.to(13, new Pair(Integer.valueOf(R$color.light_foreground), Integer.valueOf(R$color.control_enabled_light_background))), TuplesKt.to(50, new Pair(Integer.valueOf(R$color.camera_foreground), Integer.valueOf(R$color.control_enabled_default_background)))}), new Function1<Integer, Pair<? extends Integer, ? extends Integer>>() { // from class: com.android.systemui.controls.ui.RenderInfoKt$deviceColorMap$1
        public /* bridge */ /* synthetic */ Object invoke(Object obj) {
            return invoke(((Number) obj).intValue());
        }

        public final Pair<Integer, Integer> invoke(int i) {
            return new Pair<>(Integer.valueOf(R$color.control_foreground), Integer.valueOf(R$color.control_enabled_default_background));
        }
    });
    public static final Map<Integer, Integer> deviceIconMap;

    static {
        int i = R$drawable.ic_device_thermostat_off;
        Pair pair = TuplesKt.to(49001, Integer.valueOf(i));
        int i2 = R$drawable.ic_device_thermostat;
        Pair pair2 = TuplesKt.to(49002, Integer.valueOf(i2));
        Pair pair3 = TuplesKt.to(49003, Integer.valueOf(i2));
        Pair pair4 = TuplesKt.to(49004, Integer.valueOf(i2));
        Pair pair5 = TuplesKt.to(49005, Integer.valueOf(i));
        Pair pair6 = TuplesKt.to(49, Integer.valueOf(i2));
        Pair pair7 = TuplesKt.to(13, Integer.valueOf(R$drawable.ic_device_light));
        Pair pair8 = TuplesKt.to(50, Integer.valueOf(R$drawable.ic_device_camera));
        Pair pair9 = TuplesKt.to(45, Integer.valueOf(R$drawable.ic_device_lock));
        Pair pair10 = TuplesKt.to(21, Integer.valueOf(R$drawable.ic_device_switch));
        Pair pair11 = TuplesKt.to(15, Integer.valueOf(R$drawable.ic_device_outlet));
        Pair pair12 = TuplesKt.to(32, Integer.valueOf(R$drawable.ic_device_vacuum));
        Pair pair13 = TuplesKt.to(26, Integer.valueOf(R$drawable.ic_device_mop));
        int i3 = R$drawable.ic_device_air_freshener;
        Pair pair14 = TuplesKt.to(3, Integer.valueOf(i3));
        Pair pair15 = TuplesKt.to(4, Integer.valueOf(R$drawable.ic_device_air_purifier));
        Pair pair16 = TuplesKt.to(8, Integer.valueOf(R$drawable.ic_device_fan));
        Pair pair17 = TuplesKt.to(10, Integer.valueOf(R$drawable.ic_device_hood));
        int i4 = R$drawable.ic_device_kettle;
        Pair pair18 = TuplesKt.to(12, Integer.valueOf(i4));
        Pair pair19 = TuplesKt.to(14, Integer.valueOf(R$drawable.ic_device_microwave));
        Pair pair20 = TuplesKt.to(17, Integer.valueOf(R$drawable.ic_device_remote_control));
        Pair pair21 = TuplesKt.to(18, Integer.valueOf(R$drawable.ic_device_set_top));
        Pair pair22 = TuplesKt.to(20, Integer.valueOf(R$drawable.ic_device_styler));
        Pair pair23 = TuplesKt.to(22, Integer.valueOf(R$drawable.ic_device_tv));
        Pair pair24 = TuplesKt.to(23, Integer.valueOf(R$drawable.ic_device_water_heater));
        Pair pair25 = TuplesKt.to(24, Integer.valueOf(R$drawable.ic_device_dishwasher));
        Pair pair26 = TuplesKt.to(28, Integer.valueOf(R$drawable.ic_device_multicooker));
        Pair pair27 = TuplesKt.to(30, Integer.valueOf(R$drawable.ic_device_sprinkler));
        int i5 = R$drawable.ic_device_washer;
        Pair pair28 = TuplesKt.to(31, Integer.valueOf(i5));
        int i6 = R$drawable.ic_device_blinds;
        Pair pair29 = TuplesKt.to(34, Integer.valueOf(i6));
        int i7 = R$drawable.ic_device_drawer;
        Pair pair30 = TuplesKt.to(38, Integer.valueOf(i7));
        Pair pair31 = TuplesKt.to(39, Integer.valueOf(R$drawable.ic_device_garage));
        Pair pair32 = TuplesKt.to(40, Integer.valueOf(R$drawable.ic_device_gate));
        int i8 = R$drawable.ic_device_pergola;
        Pair pair33 = TuplesKt.to(41, Integer.valueOf(i8));
        int i9 = R$drawable.ic_device_window;
        deviceIconMap = MapsKt__MapWithDefaultKt.withDefault(MapsKt__MapsKt.mapOf(new Pair[]{pair, pair2, pair3, pair4, pair5, pair6, pair7, pair8, pair9, pair10, pair11, pair12, pair13, pair14, pair15, pair16, pair17, pair18, pair19, pair20, pair21, pair22, pair23, pair24, pair25, pair26, pair27, pair28, pair29, pair30, pair31, pair32, pair33, TuplesKt.to(43, Integer.valueOf(i9)), TuplesKt.to(44, Integer.valueOf(R$drawable.ic_device_valve)), TuplesKt.to(46, Integer.valueOf(R$drawable.ic_device_security_system)), TuplesKt.to(48, Integer.valueOf(R$drawable.ic_device_refrigerator)), TuplesKt.to(51, Integer.valueOf(R$drawable.ic_device_doorbell)), TuplesKt.to(52, -1), TuplesKt.to(1, Integer.valueOf(i2)), TuplesKt.to(2, Integer.valueOf(i2)), TuplesKt.to(5, Integer.valueOf(i4)), TuplesKt.to(6, Integer.valueOf(i3)), TuplesKt.to(16, Integer.valueOf(i2)), TuplesKt.to(19, Integer.valueOf(R$drawable.ic_device_cooking)), TuplesKt.to(7, Integer.valueOf(R$drawable.ic_device_display)), TuplesKt.to(25, Integer.valueOf(i5)), TuplesKt.to(27, Integer.valueOf(R$drawable.ic_device_outdoor_garden)), TuplesKt.to(29, Integer.valueOf(R$drawable.ic_device_water)), TuplesKt.to(33, Integer.valueOf(i8)), TuplesKt.to(35, Integer.valueOf(i7)), TuplesKt.to(36, Integer.valueOf(i6)), TuplesKt.to(37, Integer.valueOf(R$drawable.ic_device_door)), TuplesKt.to(42, Integer.valueOf(i9)), TuplesKt.to(47, Integer.valueOf(i2)), TuplesKt.to(-1000, Integer.valueOf(R$drawable.ic_error_outline))}), new Function1<Integer, Integer>() { // from class: com.android.systemui.controls.ui.RenderInfoKt$deviceIconMap$1
            public final Integer invoke(int i10) {
                return Integer.valueOf(R$drawable.ic_device_unknown);
            }

            public /* bridge */ /* synthetic */ Object invoke(Object obj) {
                return invoke(((Number) obj).intValue());
            }
        });
    }
}