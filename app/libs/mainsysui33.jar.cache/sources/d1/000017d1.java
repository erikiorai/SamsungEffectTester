package com.android.systemui.flags;

import androidx.constraintlayout.widget.R$styleable;
import androidx.recyclerview.widget.RecyclerView;
import com.android.internal.annotations.Keep;
import com.android.systemui.R$bool;

/* loaded from: mainsysui33.jar:com/android/systemui/flags/Flags.class */
public final class Flags {
    public static final UnreleasedFlag A11Y_FLOATING_MENU_FLING_SPRING_ANIMATIONS;
    public static final ResourceBooleanFlag ACTIVE_UNLOCK_CHIPBAR;
    public static final UnreleasedFlag APP_PANELS_ALL_APPS_ALLOWED;
    public static final UnreleasedFlag AUTO_PIN_CONFIRMATION;
    public static final ResourceBooleanFlag BATTERY_SHIELD_ICON;
    public static final UnreleasedFlag BIOMETRICS_ANIMATION_REVAMP;
    public static final ResourceBooleanFlag BOUNCER_USER_SWITCHER;
    public static final ResourceBooleanFlag CHARGING_RIPPLE;
    public static final UnreleasedFlag CHOOSER_UNBUNDLED;
    public static final ReleasedFlag CLIPBOARD_OVERLAY_REFACTOR;
    public static final ReleasedFlag CLIPBOARD_REMOTE_BEHAVIOR;
    public static final ResourceBooleanFlag COMBINED_QS_HEADERS;
    public static final UnreleasedFlag CUSTOMIZABLE_LOCK_SCREEN_QUICK_AFFORDANCES;
    public static final UnreleasedFlag DOZING_MIGRATION_1;
    public static final UnreleasedFlag DREAM_MEDIA_COMPLICATION;
    public static final UnreleasedFlag DREAM_MEDIA_TAP_TO_OPEN;
    @Keep
    public static final SysPropBooleanFlag ENABLE_FLING_TO_DISMISS_BUBBLE;
    @Keep
    public static final SysPropBooleanFlag ENABLE_FLING_TO_DISMISS_PIP;
    @Keep
    public static final SysPropBooleanFlag ENABLE_PIP_KEEP_CLEAR_ALGORITHM;
    public static final UnreleasedFlag ENABLE_STYLUS_CHARGING_UI;
    public static final UnreleasedFlag ENABLE_USI_BATTERY_NOTIFICATIONS;
    public static final UnreleasedFlag EXPERIMENTAL_FLAG;
    public static final UnreleasedFlag FACE_AUTH_REFACTOR;
    public static final ReleasedFlag FALSING_FOR_LONG_TAPS;
    public static final UnreleasedFlag FILTER_UNSEEN_NOTIFS_ON_KEYGUARD;
    public static final UnreleasedFlag FSI_CHROME;
    public static final UnreleasedFlag FSI_ON_DND_UPDATE;
    public static final UnreleasedFlag FSI_REQUIRES_KEYGUARD;
    public static final ResourceBooleanFlag FULL_SCREEN_USER_SWITCHER;
    @Keep
    public static final SysPropBooleanFlag HIDE_NAVBAR_WINDOW;
    public static final Flags INSTANCE = new Flags();
    public static final UnreleasedFlag INSTANT_VOICE_REPLY;
    public static final UnreleasedFlag LEAVE_SHADE_OPEN_FOR_BUGREPORT;
    public static final UnreleasedFlag LIGHT_REVEAL_MIGRATION;
    public static final ReleasedFlag LOCKSCREEN_ANIMATIONS;
    public static final UnreleasedFlag LOCKSCREEN_CUSTOM_CLOCKS;
    public static final UnreleasedFlag MEDIA_FALSING_PENALTY;
    public static final ReleasedFlag MEDIA_MUTE_AWAIT;
    public static final ReleasedFlag MEDIA_NEARBY_DEVICES;
    public static final UnreleasedFlag MEDIA_SESSION_ACTIONS;
    public static final UnreleasedFlag MEDIA_TAP_TO_TRANSFER;
    public static final UnreleasedFlag MEDIA_TTT_RECEIVER_SUCCESS_RIPPLE;
    public static final ReleasedFlag MODERN_BOUNCER;
    public static final ResourceBooleanFlag MONET;
    public static final SysPropBooleanFlag MONOCHROMATIC_THEMES;
    public static final UnreleasedFlag NEW_BACK_AFFORDANCE;
    public static final UnreleasedFlag NEW_ELLIPSE_DETECTION;
    public static final UnreleasedFlag NEW_STATUS_BAR_ICONS_DEBUG_COLORING;
    public static final UnreleasedFlag NEW_STATUS_BAR_MOBILE_ICONS;
    public static final UnreleasedFlag NEW_STATUS_BAR_MOBILE_ICONS_BACKEND;
    public static final UnreleasedFlag NEW_STATUS_BAR_WIFI_ICON;
    public static final UnreleasedFlag NEW_STATUS_BAR_WIFI_ICON_BACKEND;
    public static final UnreleasedFlag NEW_UDFPS_OVERLAY;
    public static final ReleasedFlag NEW_UNLOCK_SWIPE_ANIMATION;
    public static final UnreleasedFlag NOTE_TASKS;
    public static final UnreleasedFlag NOTIFICATION_DISMISSAL_FADE;
    public static final ResourceBooleanFlag NOTIFICATION_DRAG_TO_CONTENTS;
    public static final UnreleasedFlag NOTIFICATION_GROUP_DISMISSAL_ANIMATION;
    public static final UnreleasedFlag NOTIFICATION_INLINE_REPLY_ANIMATION;
    public static final UnreleasedFlag NOTIFICATION_MEMORY_LOGGING_ENABLED;
    public static final ReleasedFlag NOTIFICATION_MEMORY_MONITOR_ENABLED;
    public static final UnreleasedFlag NOTIFICATION_PIPELINE_DEVELOPER_LOGGING;
    public static final UnreleasedFlag NO_HUN_FOR_OLD_WHEN;
    public static final UnreleasedFlag NSSL_DEBUG_LINES;
    public static final UnreleasedFlag NSSL_DEBUG_REMOVE_ANIMATION;
    public static final ReleasedFlag ONGOING_CALL_IN_IMMERSIVE;
    public static final ReleasedFlag ONGOING_CALL_IN_IMMERSIVE_CHIP_TAP;
    public static final ReleasedFlag ONGOING_CALL_STATUS_BAR_CHIP;
    public static final UnreleasedFlag OUTPUT_SWITCHER_ADVANCED_LAYOUT;
    public static final UnreleasedFlag OUTPUT_SWITCHER_DEVICE_STATUS;
    public static final UnreleasedFlag OUTPUT_SWITCHER_ROUTES_PROCESSING;
    public static final ResourceBooleanFlag PEOPLE_TILE;
    public static final ReleasedFlag POWER_MENU_LITE;
    public static final UnreleasedFlag QS_SECONDARY_DATA_SUB_INFO;
    public static final ResourceBooleanFlag QS_USER_DETAIL_SHORTCUT;
    public static final UnreleasedFlag QUICK_TAP_FLOW_FRAMEWORK;
    public static final ReleasedFlag QUICK_TAP_IN_PCC;
    public static final UnreleasedFlag REGION_SAMPLING;
    public static final UnreleasedFlag REVAMPED_WALLPAPER_UI;
    public static final ReleasedFlag ROUNDED_BOX_RIPPLE;
    public static final ReleasedFlag SCREENSHOT_REQUEST_PROCESSOR;
    public static final UnreleasedFlag SCREENSHOT_WORK_PROFILE_POLICY;
    public static final UnreleasedFlag SCREEN_CONTENTS_TRANSLATION;
    public static final ReleasedFlag SEMI_STABLE_SORT;
    public static final UnreleasedFlag SHOW_LOWLIGHT_ON_DIRECT_BOOT;
    public static final UnreleasedFlag SIMPLIFIED_APPEAR_FRACTION;
    public static final ReleasedFlag SIMULATE_DOCK_THROUGH_CHARGING;
    public static final ResourceBooleanFlag SMARTSPACE;
    public static final UnreleasedFlag SMARTSPACE_DATE_WEATHER_DECOUPLED;
    public static final ReleasedFlag SMARTSPACE_SHARED_ELEMENT_TRANSITION_ENABLED;
    public static final ReleasedFlag STABILITY_INDEX_FIX;
    public static final UnreleasedFlag STEP_CLOCK_ANIMATION;
    public static final UnreleasedFlag TEAMFOOD;
    public static final UnreleasedFlag TRACKPAD_GESTURE_BACK;
    public static final UnreleasedFlag TRACK_STYLUS_EVER_USED;
    public static final UnreleasedFlag UDFPS_ELLIPSE_DEBUG_UI;
    public static final UnreleasedFlag UDFPS_ELLIPSE_DETECTION;
    public static final UnreleasedFlag UDFPS_NEW_TOUCH_DETECTION;
    public static final UnreleasedFlag UMO_SURFACE_RIPPLE;
    public static final UnreleasedFlag UMO_TURBULENCE_NOISE;
    public static final UnreleasedFlag UNOCCLUSION_TRANSITION;
    @Keep
    public static final UnreleasedFlag USE_APP_PANELS;
    public static final ReleasedFlag USE_ROUNDNESS_SOURCETYPES;
    public static final UnreleasedFlag WARN_ON_BLOCKING_BINDER_TRANSACTIONS;
    @Keep
    public static final SysPropBooleanFlag WM_ALWAYS_ENFORCE_PREDICTIVE_BACK;
    @Keep
    public static final UnreleasedFlag WM_BUBBLE_BAR;
    @Keep
    public static final SysPropBooleanFlag WM_CAPTION_ON_SHELL;
    @Keep
    public static final SysPropBooleanFlag WM_DESKTOP_WINDOWING;
    @Keep
    public static final SysPropBooleanFlag WM_DESKTOP_WINDOWING_2;
    @Keep
    public static final UnreleasedFlag WM_ENABLE_PARTIAL_SCREEN_SHARING;
    @Keep
    public static final SysPropBooleanFlag WM_ENABLE_PREDICTIVE_BACK;
    @Keep
    public static final SysPropBooleanFlag WM_ENABLE_PREDICTIVE_BACK_ANIM;
    public static final UnreleasedFlag WM_ENABLE_PREDICTIVE_BACK_BOUNCER_ANIM;
    public static final UnreleasedFlag WM_ENABLE_PREDICTIVE_BACK_SYSUI;
    @Keep
    public static final SysPropBooleanFlag WM_ENABLE_SHELL_TRANSITIONS;

    static {
        FlagsFactory flagsFactory = FlagsFactory.INSTANCE;
        TEAMFOOD = FlagsFactory.unreleasedFlag$default(flagsFactory, 1, "teamfood", null, false, 12, null);
        NOTIFICATION_PIPELINE_DEVELOPER_LOGGING = FlagsFactory.unreleasedFlag$default(flagsFactory, 103, "notification_pipeline_developer_logging", null, false, 12, null);
        NSSL_DEBUG_LINES = FlagsFactory.unreleasedFlag$default(flagsFactory, 105, "nssl_debug_lines", null, false, 12, null);
        NSSL_DEBUG_REMOVE_ANIMATION = FlagsFactory.unreleasedFlag$default(flagsFactory, 106, "nssl_debug_remove_animation", null, false, 12, null);
        NOTIFICATION_DRAG_TO_CONTENTS = FlagsFactory.resourceBooleanFlag$default(flagsFactory, 108, R$bool.config_notificationToContents, "notification_drag_to_contents", null, false, 24, null);
        FSI_REQUIRES_KEYGUARD = FlagsFactory.unreleasedFlag$default(flagsFactory, R$styleable.ConstraintLayout_Layout_layout_goneMarginStart, "fsi_requires_keyguard", null, true, 4, null);
        FSI_ON_DND_UPDATE = FlagsFactory.unreleasedFlag$default(flagsFactory, 259130119, "fsi_on_dnd_update", null, true, 4, null);
        INSTANT_VOICE_REPLY = FlagsFactory.unreleasedFlag$default(flagsFactory, 111, "instant_voice_reply", null, true, 4, null);
        NOTIFICATION_MEMORY_MONITOR_ENABLED = FlagsFactory.releasedFlag$default(flagsFactory, 112, "notification_memory_monitor_enabled", null, false, 12, null);
        NOTIFICATION_MEMORY_LOGGING_ENABLED = FlagsFactory.unreleasedFlag$default(flagsFactory, 119, "notification_memory_logging_enabled", null, true, 4, null);
        NOTIFICATION_DISMISSAL_FADE = FlagsFactory.unreleasedFlag$default(flagsFactory, 113, "notification_dismissal_fade", null, true, 4, null);
        STABILITY_INDEX_FIX = FlagsFactory.releasedFlag$default(flagsFactory, 114, "stability_index_fix", null, false, 12, null);
        SEMI_STABLE_SORT = FlagsFactory.releasedFlag$default(flagsFactory, 115, "semi_stable_sort", null, false, 12, null);
        USE_ROUNDNESS_SOURCETYPES = FlagsFactory.releasedFlag$default(flagsFactory, 116, "use_roundness_sourcetype", null, false, 12, null);
        NOTIFICATION_GROUP_DISMISSAL_ANIMATION = FlagsFactory.unreleasedFlag$default(flagsFactory, 259217907, "notification_group_dismissal_animation", null, true, 4, null);
        FSI_CHROME = FlagsFactory.unreleasedFlag$default(flagsFactory, 117, "fsi_chrome", null, false, 12, null);
        SIMPLIFIED_APPEAR_FRACTION = FlagsFactory.unreleasedFlag$default(flagsFactory, 259395680, "simplified_appear_fraction", null, true, 4, null);
        NO_HUN_FOR_OLD_WHEN = FlagsFactory.unreleasedFlag$default(flagsFactory, 118, "no_hun_for_old_when", null, true, 4, null);
        NOTIFICATION_INLINE_REPLY_ANIMATION = FlagsFactory.unreleasedFlag$default(flagsFactory, 174148361, "notification_inline_reply_animation", null, true, 4, null);
        FILTER_UNSEEN_NOTIFS_ON_KEYGUARD = FlagsFactory.unreleasedFlag$default(flagsFactory, 254647461, "filter_unseen_notifs_on_keyguard", null, true, 4, null);
        LOCKSCREEN_ANIMATIONS = FlagsFactory.releasedFlag$default(flagsFactory, 201, "lockscreen_animations", null, false, 12, null);
        NEW_UNLOCK_SWIPE_ANIMATION = FlagsFactory.releasedFlag$default(flagsFactory, 202, "new_unlock_swipe_animation", null, false, 12, null);
        CHARGING_RIPPLE = FlagsFactory.resourceBooleanFlag$default(flagsFactory, 203, R$bool.flag_charging_ripple, "charging_ripple", null, false, 24, null);
        BOUNCER_USER_SWITCHER = FlagsFactory.resourceBooleanFlag$default(flagsFactory, 204, R$bool.config_enableBouncerUserSwitcher, "bouncer_user_switcher", null, false, 24, null);
        LOCKSCREEN_CUSTOM_CLOCKS = FlagsFactory.unreleasedFlag$default(flagsFactory, 207, "lockscreen_custom_clocks", null, true, 4, null);
        MODERN_BOUNCER = FlagsFactory.releasedFlag$default(flagsFactory, 208, "modern_bouncer", null, false, 12, null);
        STEP_CLOCK_ANIMATION = FlagsFactory.unreleasedFlag$default(flagsFactory, 212, "step_clock_animation", null, true, 4, null);
        DOZING_MIGRATION_1 = FlagsFactory.unreleasedFlag$default(flagsFactory, 213, "dozing_migration_1", null, false, 12, null);
        NEW_ELLIPSE_DETECTION = FlagsFactory.unreleasedFlag$default(flagsFactory, 214, "new_ellipse_detection", null, false, 12, null);
        NEW_UDFPS_OVERLAY = FlagsFactory.unreleasedFlag$default(flagsFactory, 215, "new_udfps_overlay", null, false, 12, null);
        CUSTOMIZABLE_LOCK_SCREEN_QUICK_AFFORDANCES = FlagsFactory.unreleasedFlag$default(flagsFactory, 216, "customizable_lock_screen_quick_affordances", null, false, 4, null);
        ACTIVE_UNLOCK_CHIPBAR = FlagsFactory.resourceBooleanFlag$default(flagsFactory, 217, R$bool.flag_active_unlock_chipbar, "active_unlock_chipbar", null, false, 24, null);
        LIGHT_REVEAL_MIGRATION = FlagsFactory.unreleasedFlag$default(flagsFactory, 218, "light_reveal_migration", null, false, 4, null);
        FACE_AUTH_REFACTOR = FlagsFactory.unreleasedFlag$default(flagsFactory, 220, "face_auth_refactor", null, false, 12, null);
        BIOMETRICS_ANIMATION_REVAMP = FlagsFactory.unreleasedFlag$default(flagsFactory, 221, "biometrics_animation_revamp", null, false, 12, null);
        REVAMPED_WALLPAPER_UI = FlagsFactory.unreleasedFlag$default(flagsFactory, 222, "revamped_wallpaper_ui", null, false, 4, null);
        UNOCCLUSION_TRANSITION = FlagsFactory.unreleasedFlag$default(flagsFactory, 223, "unocclusion_transition", null, false, 4, null);
        AUTO_PIN_CONFIRMATION = FlagsFactory.unreleasedFlag$default(flagsFactory, 224, "auto_pin_confirmation", "auto_pin_confirmation", false, 8, null);
        POWER_MENU_LITE = FlagsFactory.releasedFlag$default(flagsFactory, 300, "power_menu_lite", null, false, 12, null);
        SMARTSPACE_SHARED_ELEMENT_TRANSITION_ENABLED = FlagsFactory.releasedFlag$default(flagsFactory, 401, "smartspace_shared_element_transition_enabled", null, false, 12, null);
        SMARTSPACE = FlagsFactory.resourceBooleanFlag$default(flagsFactory, 402, R$bool.flag_smartspace, "smartspace", null, false, 24, null);
        SMARTSPACE_DATE_WEATHER_DECOUPLED = FlagsFactory.unreleasedFlag$default(flagsFactory, 403, "smartspace_date_weather_decoupled", null, false, 12, null);
        COMBINED_QS_HEADERS = FlagsFactory.resourceBooleanFlag$default(flagsFactory, 501, R$bool.flag_combined_qs_headers, "combined_qs_headers", null, false, 24, null);
        PEOPLE_TILE = FlagsFactory.resourceBooleanFlag$default(flagsFactory, 502, R$bool.flag_conversations, "people_tile", null, false, 24, null);
        QS_USER_DETAIL_SHORTCUT = FlagsFactory.resourceBooleanFlag$default(flagsFactory, 503, R$bool.flag_lockscreen_qs_user_detail_shortcut, "qs_user_detail_shortcut", null, false, 24, null);
        FULL_SCREEN_USER_SWITCHER = FlagsFactory.resourceBooleanFlag$default(flagsFactory, 506, R$bool.config_enableFullscreenUserSwitcher, "full_screen_user_switcher", null, false, 24, null);
        QS_SECONDARY_DATA_SUB_INFO = FlagsFactory.unreleasedFlag$default(flagsFactory, 508, "qs_secondary_data_sub_info", null, true, 4, null);
        NEW_STATUS_BAR_MOBILE_ICONS = FlagsFactory.unreleasedFlag$default(flagsFactory, 606, "new_status_bar_mobile_icons", null, false, 12, null);
        NEW_STATUS_BAR_WIFI_ICON = FlagsFactory.unreleasedFlag$default(flagsFactory, 607, "new_status_bar_wifi_icon", null, false, 12, null);
        NEW_STATUS_BAR_MOBILE_ICONS_BACKEND = FlagsFactory.unreleasedFlag$default(flagsFactory, 608, "new_status_bar_mobile_icons_backend", null, false, 12, null);
        NEW_STATUS_BAR_WIFI_ICON_BACKEND = FlagsFactory.unreleasedFlag$default(flagsFactory, 609, "new_status_bar_wifi_icon_backend", null, false, 12, null);
        BATTERY_SHIELD_ICON = FlagsFactory.resourceBooleanFlag$default(flagsFactory, 610, R$bool.flag_battery_shield_icon, "battery_shield_icon", null, false, 24, null);
        NEW_STATUS_BAR_ICONS_DEBUG_COLORING = FlagsFactory.unreleasedFlag$default(flagsFactory, 611, "new_status_bar_icons_debug_coloring", null, false, 12, null);
        ONGOING_CALL_STATUS_BAR_CHIP = FlagsFactory.releasedFlag$default(flagsFactory, 700, "ongoing_call_status_bar_chip", null, false, 12, null);
        ONGOING_CALL_IN_IMMERSIVE = FlagsFactory.releasedFlag$default(flagsFactory, 701, "ongoing_call_in_immersive", null, false, 12, null);
        ONGOING_CALL_IN_IMMERSIVE_CHIP_TAP = FlagsFactory.releasedFlag$default(flagsFactory, 702, "ongoing_call_in_immersive_chip_tap", null, false, 12, null);
        MONET = FlagsFactory.resourceBooleanFlag$default(flagsFactory, 800, R$bool.flag_monet, "monet", null, false, 24, null);
        REGION_SAMPLING = FlagsFactory.unreleasedFlag$default(flagsFactory, 801, "region_sampling", null, true, 4, null);
        SCREEN_CONTENTS_TRANSLATION = FlagsFactory.unreleasedFlag$default(flagsFactory, 803, "screen_contents_translation", null, false, 12, null);
        MONOCHROMATIC_THEMES = FlagsFactory.sysPropBooleanFlag$default(flagsFactory, 804, "persist.sysui.monochromatic", null, false, 4, null);
        MEDIA_TAP_TO_TRANSFER = FlagsFactory.unreleasedFlag$default(flagsFactory, 900, "media_tap_to_transfer", null, true, 4, null);
        MEDIA_SESSION_ACTIONS = FlagsFactory.unreleasedFlag$default(flagsFactory, 901, "media_session_actions", null, false, 12, null);
        MEDIA_NEARBY_DEVICES = FlagsFactory.releasedFlag$default(flagsFactory, 903, "media_nearby_devices", null, false, 12, null);
        MEDIA_MUTE_AWAIT = FlagsFactory.releasedFlag$default(flagsFactory, 904, "media_mute_await", null, false, 12, null);
        DREAM_MEDIA_COMPLICATION = FlagsFactory.unreleasedFlag$default(flagsFactory, 905, "dream_media_complication", null, false, 12, null);
        DREAM_MEDIA_TAP_TO_OPEN = FlagsFactory.unreleasedFlag$default(flagsFactory, 906, "dream_media_tap_to_open", null, false, 12, null);
        UMO_SURFACE_RIPPLE = FlagsFactory.unreleasedFlag$default(flagsFactory, 907, "umo_surface_ripple", null, false, 12, null);
        MEDIA_FALSING_PENALTY = FlagsFactory.unreleasedFlag$default(flagsFactory, 908, "media_falsing_media", null, true, 4, null);
        UMO_TURBULENCE_NOISE = FlagsFactory.unreleasedFlag$default(flagsFactory, 909, "umo_turbulence_noise", null, false, 12, null);
        MEDIA_TTT_RECEIVER_SUCCESS_RIPPLE = FlagsFactory.unreleasedFlag$default(flagsFactory, 910, "media_ttt_receiver_success_ripple", null, true, 4, null);
        SIMULATE_DOCK_THROUGH_CHARGING = FlagsFactory.releasedFlag$default(flagsFactory, 1000, "simulate_dock_through_charging", null, false, 12, null);
        ROUNDED_BOX_RIPPLE = FlagsFactory.releasedFlag$default(flagsFactory, 1002, "rounded_box_ripple", null, false, 12, null);
        SHOW_LOWLIGHT_ON_DIRECT_BOOT = FlagsFactory.unreleasedFlag$default(flagsFactory, 1003, "show_lowlight_on_direct_boot", null, false, 12, null);
        WM_ENABLE_SHELL_TRANSITIONS = FlagsFactory.sysPropBooleanFlag$default(flagsFactory, 1100, "persist.wm.debug.shell_transit", null, false, 4, null);
        WM_ENABLE_PARTIAL_SCREEN_SHARING = flagsFactory.unreleasedFlag(1102, "record_task_content", "window_manager", true);
        HIDE_NAVBAR_WINDOW = FlagsFactory.sysPropBooleanFlag$default(flagsFactory, 1103, "persist.wm.debug.hide_navbar_window", null, false, 4, null);
        WM_DESKTOP_WINDOWING = FlagsFactory.sysPropBooleanFlag$default(flagsFactory, 1104, "persist.wm.debug.desktop_mode", null, false, 4, null);
        WM_CAPTION_ON_SHELL = FlagsFactory.sysPropBooleanFlag$default(flagsFactory, 1105, "persist.wm.debug.caption_on_shell", null, false, 4, null);
        ENABLE_FLING_TO_DISMISS_BUBBLE = FlagsFactory.sysPropBooleanFlag$default(flagsFactory, 1108, "persist.wm.debug.fling_to_dismiss_bubble", null, true, 4, null);
        ENABLE_FLING_TO_DISMISS_PIP = FlagsFactory.sysPropBooleanFlag$default(flagsFactory, 1109, "persist.wm.debug.fling_to_dismiss_pip", null, true, 4, null);
        ENABLE_PIP_KEEP_CLEAR_ALGORITHM = FlagsFactory.sysPropBooleanFlag$default(flagsFactory, 1110, "persist.wm.debug.enable_pip_keep_clear_algorithm", null, false, 4, null);
        WM_BUBBLE_BAR = FlagsFactory.unreleasedFlag$default(flagsFactory, 1111, "wm_bubble_bar", null, false, 12, null);
        WM_DESKTOP_WINDOWING_2 = FlagsFactory.sysPropBooleanFlag$default(flagsFactory, 1112, "persist.wm.debug.desktop_mode_2", null, false, 4, null);
        WM_ENABLE_PREDICTIVE_BACK = FlagsFactory.sysPropBooleanFlag$default(flagsFactory, 1200, "persist.wm.debug.predictive_back", null, true, 4, null);
        WM_ENABLE_PREDICTIVE_BACK_ANIM = FlagsFactory.sysPropBooleanFlag$default(flagsFactory, 1201, "persist.wm.debug.predictive_back_anim", null, false, 4, null);
        WM_ALWAYS_ENFORCE_PREDICTIVE_BACK = FlagsFactory.sysPropBooleanFlag$default(flagsFactory, 1202, "persist.wm.debug.predictive_back_always_enforce", null, false, 4, null);
        NEW_BACK_AFFORDANCE = FlagsFactory.unreleasedFlag$default(flagsFactory, 1203, "new_back_affordance", null, false, 4, null);
        WM_ENABLE_PREDICTIVE_BACK_SYSUI = FlagsFactory.unreleasedFlag$default(flagsFactory, 1204, "persist.wm.debug.predictive_back_sysui_enable", null, true, 4, null);
        TRACKPAD_GESTURE_BACK = FlagsFactory.unreleasedFlag$default(flagsFactory, 1205, "trackpad_gesture_back", null, false, 4, null);
        WM_ENABLE_PREDICTIVE_BACK_BOUNCER_ANIM = FlagsFactory.unreleasedFlag$default(flagsFactory, 1206, "persist.wm.debug.predictive_back_bouncer_anim", null, true, 4, null);
        SCREENSHOT_REQUEST_PROCESSOR = FlagsFactory.releasedFlag$default(flagsFactory, 1300, "screenshot_request_processor", null, false, 12, null);
        SCREENSHOT_WORK_PROFILE_POLICY = FlagsFactory.unreleasedFlag$default(flagsFactory, 1301, "screenshot_work_profile_policy", null, true, 4, null);
        QUICK_TAP_IN_PCC = FlagsFactory.releasedFlag$default(flagsFactory, 1400, "quick_tap_in_pcc", null, false, 12, null);
        QUICK_TAP_FLOW_FRAMEWORK = FlagsFactory.unreleasedFlag$default(flagsFactory, 1401, "quick_tap_flow_framework", null, false, 4, null);
        CHOOSER_UNBUNDLED = FlagsFactory.unreleasedFlag$default(flagsFactory, 1500, "chooser_unbundled", null, true, 4, null);
        A11Y_FLOATING_MENU_FLING_SPRING_ANIMATIONS = FlagsFactory.unreleasedFlag$default(flagsFactory, 1600, "a11y_floating_menu_fling_spring_animations", null, false, 12, null);
        CLIPBOARD_OVERLAY_REFACTOR = FlagsFactory.releasedFlag$default(flagsFactory, 1700, "clipboard_overlay_refactor", null, false, 12, null);
        CLIPBOARD_REMOTE_BEHAVIOR = FlagsFactory.releasedFlag$default(flagsFactory, 1701, "clipboard_remote_behavior", null, false, 12, null);
        LEAVE_SHADE_OPEN_FOR_BUGREPORT = FlagsFactory.unreleasedFlag$default(flagsFactory, 1800, "leave_shade_open_for_bugreport", null, true, 4, null);
        NOTE_TASKS = FlagsFactory.unreleasedFlag$default(flagsFactory, 1900, "keycode_flag", null, false, 12, null);
        USE_APP_PANELS = FlagsFactory.unreleasedFlag$default(flagsFactory, RecyclerView.MAX_SCROLL_DURATION, "use_app_panels", null, true, 4, null);
        APP_PANELS_ALL_APPS_ALLOWED = FlagsFactory.unreleasedFlag$default(flagsFactory, 2001, "app_panels_all_apps_allowed", null, true, 4, null);
        FALSING_FOR_LONG_TAPS = FlagsFactory.releasedFlag$default(flagsFactory, 2100, "falsing_for_long_taps", null, false, 12, null);
        UDFPS_NEW_TOUCH_DETECTION = FlagsFactory.unreleasedFlag$default(flagsFactory, 2200, "udfps_new_touch_detection", null, false, 12, null);
        UDFPS_ELLIPSE_DEBUG_UI = FlagsFactory.unreleasedFlag$default(flagsFactory, 2201, "udfps_ellipse_debug", null, false, 12, null);
        UDFPS_ELLIPSE_DETECTION = FlagsFactory.unreleasedFlag$default(flagsFactory, 2202, "udfps_ellipse_detection", null, false, 12, null);
        TRACK_STYLUS_EVER_USED = FlagsFactory.unreleasedFlag$default(flagsFactory, 2300, "track_stylus_ever_used", null, false, 12, null);
        ENABLE_STYLUS_CHARGING_UI = FlagsFactory.unreleasedFlag$default(flagsFactory, 2301, "enable_stylus_charging_ui", null, false, 12, null);
        ENABLE_USI_BATTERY_NOTIFICATIONS = FlagsFactory.unreleasedFlag$default(flagsFactory, 2302, "enable_usi_battery_notifications", null, false, 12, null);
        WARN_ON_BLOCKING_BINDER_TRANSACTIONS = FlagsFactory.unreleasedFlag$default(flagsFactory, 2400, "warn_on_blocking_binder_transactions", null, false, 12, null);
        OUTPUT_SWITCHER_ADVANCED_LAYOUT = FlagsFactory.unreleasedFlag$default(flagsFactory, 2500, "output_switcher_advanced_layout", null, false, 12, null);
        OUTPUT_SWITCHER_ROUTES_PROCESSING = FlagsFactory.unreleasedFlag$default(flagsFactory, 2501, "output_switcher_routes_processing", null, false, 12, null);
        OUTPUT_SWITCHER_DEVICE_STATUS = FlagsFactory.unreleasedFlag$default(flagsFactory, 2502, "output_switcher_device_status", null, false, 12, null);
        EXPERIMENTAL_FLAG = FlagsFactory.unreleasedFlag$default(flagsFactory, 2, "exp_flag_release", null, false, 12, null);
    }

    public final ResourceBooleanFlag getCHARGING_RIPPLE() {
        return CHARGING_RIPPLE;
    }

    public final UnreleasedFlag getCHOOSER_UNBUNDLED() {
        return CHOOSER_UNBUNDLED;
    }

    public final UnreleasedFlag getFILTER_UNSEEN_NOTIFS_ON_KEYGUARD() {
        return FILTER_UNSEEN_NOTIFS_ON_KEYGUARD;
    }

    public final UnreleasedFlag getFSI_ON_DND_UPDATE() {
        return FSI_ON_DND_UPDATE;
    }

    public final UnreleasedFlag getFSI_REQUIRES_KEYGUARD() {
        return FSI_REQUIRES_KEYGUARD;
    }

    public final ReleasedFlag getMEDIA_MUTE_AWAIT() {
        return MEDIA_MUTE_AWAIT;
    }

    public final ReleasedFlag getMEDIA_NEARBY_DEVICES() {
        return MEDIA_NEARBY_DEVICES;
    }

    public final UnreleasedFlag getMEDIA_SESSION_ACTIONS() {
        return MEDIA_SESSION_ACTIONS;
    }

    public final UnreleasedFlag getMEDIA_TAP_TO_TRANSFER() {
        return MEDIA_TAP_TO_TRANSFER;
    }

    public final UnreleasedFlag getMEDIA_TTT_RECEIVER_SUCCESS_RIPPLE() {
        return MEDIA_TTT_RECEIVER_SUCCESS_RIPPLE;
    }

    public final UnreleasedFlag getNEW_STATUS_BAR_ICONS_DEBUG_COLORING() {
        return NEW_STATUS_BAR_ICONS_DEBUG_COLORING;
    }

    public final UnreleasedFlag getNEW_STATUS_BAR_MOBILE_ICONS() {
        return NEW_STATUS_BAR_MOBILE_ICONS;
    }

    public final UnreleasedFlag getNEW_STATUS_BAR_MOBILE_ICONS_BACKEND() {
        return NEW_STATUS_BAR_MOBILE_ICONS_BACKEND;
    }

    public final UnreleasedFlag getNEW_STATUS_BAR_WIFI_ICON() {
        return NEW_STATUS_BAR_WIFI_ICON;
    }

    public final UnreleasedFlag getNEW_STATUS_BAR_WIFI_ICON_BACKEND() {
        return NEW_STATUS_BAR_WIFI_ICON_BACKEND;
    }

    public final ReleasedFlag getNEW_UNLOCK_SWIPE_ANIMATION() {
        return NEW_UNLOCK_SWIPE_ANIMATION;
    }

    public final UnreleasedFlag getNOTIFICATION_MEMORY_LOGGING_ENABLED() {
        return NOTIFICATION_MEMORY_LOGGING_ENABLED;
    }

    public final ReleasedFlag getNOTIFICATION_MEMORY_MONITOR_ENABLED() {
        return NOTIFICATION_MEMORY_MONITOR_ENABLED;
    }

    public final UnreleasedFlag getNOTIFICATION_PIPELINE_DEVELOPER_LOGGING() {
        return NOTIFICATION_PIPELINE_DEVELOPER_LOGGING;
    }

    public final UnreleasedFlag getNO_HUN_FOR_OLD_WHEN() {
        return NO_HUN_FOR_OLD_WHEN;
    }

    public final ReleasedFlag getONGOING_CALL_IN_IMMERSIVE() {
        return ONGOING_CALL_IN_IMMERSIVE;
    }

    public final ReleasedFlag getONGOING_CALL_IN_IMMERSIVE_CHIP_TAP() {
        return ONGOING_CALL_IN_IMMERSIVE_CHIP_TAP;
    }

    public final ReleasedFlag getONGOING_CALL_STATUS_BAR_CHIP() {
        return ONGOING_CALL_STATUS_BAR_CHIP;
    }

    public final UnreleasedFlag getREGION_SAMPLING() {
        return REGION_SAMPLING;
    }

    public final ReleasedFlag getSEMI_STABLE_SORT() {
        return SEMI_STABLE_SORT;
    }

    public final ResourceBooleanFlag getSMARTSPACE() {
        return SMARTSPACE;
    }

    public final ReleasedFlag getSMARTSPACE_SHARED_ELEMENT_TRANSITION_ENABLED() {
        return SMARTSPACE_SHARED_ELEMENT_TRANSITION_ENABLED;
    }

    public final ReleasedFlag getSTABILITY_INDEX_FIX() {
        return STABILITY_INDEX_FIX;
    }
}