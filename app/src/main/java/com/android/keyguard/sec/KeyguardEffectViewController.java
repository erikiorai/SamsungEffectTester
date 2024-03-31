package com.android.keyguard.sec;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.ContentObserver;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.hardware.SensorEvent;
import android.os.FileObserver;
import android.os.Handler;
import android.os.PowerManager;
import android.provider.Settings;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.aj.effect.MainActivity;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class KeyguardEffectViewController implements KeyguardEffectViewBase {
    private static final String ACTION_IMAGES_CHANGED = "com.sec.android.slidingGallery.LOCKSCREEN_IMAGE_CHANGED";
    public static final String ACTION_LOCKSCREEN_IMAGE_CHANGED = "com.sec.android.gallery3d.LOCKSCREEN_IMAGE_CHANGED";

    public static final int EFFECT_ABSTRACTTILE = 11;
    public static final int EFFECT_AUTUMN = 83;
    public static final int EFFECT_BLIND = 5;
    public static final int EFFECT_BRILLIANTCUT = 9;
    public static final int EFFECT_BRILLIANTRING = 8;
    public static final int EFFECT_COLOURDROPLET = 15;
    public static final int EFFECT_GEOMETRICMOSAIC = 12;
    public static final int EFFECT_HOME = -2;
    public static final int EFFECT_JUST_LOCK_LIVE_WALLPAPER = 100;
    public static final int EFFECT_LIGHT = 2;
    public static final int EFFECT_LIQUID = 13;
    public static final int EFFECT_LIVEWALLPAPER = -1;
    public static final int EFFECT_MASS_RIPPLE = 7;
    public static final int EFFECT_MASS_TENSION = 6;
    public static final int EFFECT_MONTBLANC = 10;
    public static final int EFFECT_NONE = 0;
    public static final int EFFECT_PARTICLE = 14;
    public static final int EFFECT_POPPING_COLOR = 3;
    public static final int EFFECT_RIPPLE = 1;
    public static final int EFFECT_SEASONAL = 85;
    public static final int EFFECT_SPRING = 81;
    public static final int EFFECT_SUMMER = 82;
    public static final int EFFECT_TILT = 101;
    public static final int EFFECT_WATERCOLOR = 4;
    public static final int EFFECT_WINTER = 84;
    public static final int EFFECT_ZOOM_PANNING = 80;

    private static final String EMPTY_WALLPAPER_IMAGE_PATH = "/system/wallpaper/keyguard_empty_image.jpg";
    public static final int KEYGUARD_DEFAULT_WALLPAPER_TYPE_BRILLIANT = 1;
    public static final String LOCK_SOUND_ABSTRACT_TILE = "/system/media/audio/ui/abstracttile_lock.ogg";
    public static final String LOCK_SOUND_AUTUMN = "/system/media/audio/ui/autumn_lock.ogg";
    public static final String LOCK_SOUND_BLIND = "/system/media/audio/ui/blind_lock.ogg";
    public static final String LOCK_SOUND_BRILLIANT_CUT = "/system/media/audio/ui/brilliantcut_lock.ogg";
    public static final String LOCK_SOUND_BRILLIANT_RING = "/system/media/audio/ui/brilliantring_lock.ogg";
    public static final String LOCK_SOUND_GEOMETRIC_MOSAIC = "/system/media/audio/ui/GeometricMosaic_lock.ogg";
    public static final String LOCK_SOUND_LENS = "/system/media/audio/ui/lens_flare_lock.ogg";
    public static final String LOCK_SOUND_NONE = "/system/media/audio/ui/Lock_none_effect.ogg";
    public static final String LOCK_SOUND_PARTICLE = "/system/media/audio/ui/Particle_Lock.ogg";
    public static final String LOCK_SOUND_SPRING = "/system/media/audio/ui/spring_lock.ogg";
    public static final String LOCK_SOUND_SUMMER = "/system/media/audio/ui/summer_lock.ogg";
    public static final String LOCK_SOUND_WINTER = "/system/media/audio/ui/winter_lock.ogg";
    private static final int MSG_CHARGE_STATE_CHANGED = 4852;
    private static final int MSG_WALLPAPER_FILE_CHANGED = 4850;
    private static final int MSG_WALLPAPER_PATH_CHANGED = 4849;
    private static final int MSG_WALLPAPER_PRELOAD_CHANGED = 4851;
    private static final int MSG_WALLPAPER_TYPE_CHANGED = 4848;
    private static final String RICH_LOCK_CATEGORIES_WALLPAPER_ROOT_PATH = "/data/data/com.samsung.android.keyguardwallpaperupdator/files/wallpaper_images";
    private static final String RICH_LOCK_WALLPAPER_ROOT_PATH = "/data/data/com.samsung.android.keyguardwallpaperupdator";
    public static final String SETTING_KEYGUARD_DEFAULT_WALLPAPER_TYPE_FOR_EFFECT = "keyguard_default_wallpaper_type_for_effect";
    public static final String SETTING_KEYGUARD_SET_DEFAULT_WALLPAPER = "keyguard_set_default_wallpaper";
    public static final String SETTING_LOCKSCREEN_MONTBLANC_WALLPAPER = "lockscreen_montblanc_wallpaper";
    public static final int SLIDING_INTERNAL_EVERY_12HOUR = 2;
    public static final int SLIDING_INTERNAL_EVERY_1HOUR = 1;
    public static final int SLIDING_INTERNAL_EVERY_24HOUR = 3;
    public static final int SLIDING_INTERNAL_SCREENOFF = 0;
    public static final String SlidingWallpaperPath = "com.sec.android.slidingGallery";
    private static final String TAG = "KeyguardEffectViewController";
    public static final String UNLOCK_SOUND_AUTUMN = "/system/media/audio/ui/autumn_unlock.ogg";
    public static final String UNLOCK_SOUND_LENS = "/system/media/audio/ui/lock_screen_silence.ogg";
    public static final String UNLOCK_SOUND_NONE = "/system/media/audio/ui/Unlock_none_effect.ogg";
    public static final String UNLOCK_SOUND_PARTICLE = "/system/media/audio/ui/lock_screen_silence.ogg";
    public static final String UNLOCK_SOUND_SPRING = "/system/media/audio/ui/spring_unlock.ogg";
    public static final String UNLOCK_SOUND_SUMMER = "/system/media/audio/ui/summer_unlock.ogg";
    public static final String UNLOCK_SOUND_WINTER = "/system/media/audio/ui/winter_unlock.ogg";
    private static int displayHeight;
    private static int displayWidth;
    private static KeyguardEffectViewController sKeyguardEffectViewController;
    public static ArrayList<String> uriArray;
    private ContentObserver mContentObserver;
    private Context mContext;
    private int mCurrentEffect;
    private boolean mFestivalEffectEnabled;
    private FileObserver mFileObserver;
    private boolean mIsShowing;
    private LockSoundChangeCallback mLockSoundChangeCallback;
    private int mOldEffect;
    private PowerManager mPowerManager;
    private View mStatusBarGradationView;
    protected String mWallpaperPath;
    private int mWallpaperType;
    private static int mSlidingInterval = 0;
    private static int mSlidingTotalCount = 0;
    private static int mSlidingHour = 0;
    private static int mSlidingMin = 0;
    private static long mSlidingTime = 0;
    private static int mSlidingWallpaperIndex = 0;
    private final int UNLOCK_EFFECT_VIEW_FOREGROUND = 1;
    private final int UNLOCK_EFFECT_VIEW_BACKGROUND = 2;
    private FrameLayout mBackgroundRootLayout = null;
    private FrameLayout mForegroundRootLayout = null;
    //private FrameLayout mNotificationPanel = null;
    private KeyguardEffectViewBase mUnlockEffectView = null;
    private KeyguardEffectViewBase mBackgroundView = null;
    private KeyguardEffectViewBase mForegroundView = null;
    private KeyguardEffectViewBase mChargeView = null;
    private boolean mBgHasAddChargeView = false;
    private KeyguardEffectViewBase mForegroundCircleView = null;
    private boolean mIsVisible = true;
    private boolean mUserSwitching = false;
    private boolean mMusicBackgroundSet = false;
    private boolean mBootCompleted = false;
    private boolean mEmergencyModeStateChanged = false;
    private KeyguardFestivalEffect mFestivalEffect;
    private String mOldPrimaryEffect = null;
    /* private final Handler mHandler = new Handler() { // from class: com.android.keyguard.sec.KeyguardEffectViewController.1
        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case KeyguardEffectViewController.MSG_WALLPAPER_TYPE_CHANGED /* 4848 :
                    KeyguardEffectViewController.this.handleWallpaperTypeChanged();
                    return;
                case KeyguardEffectViewController.MSG_WALLPAPER_PATH_CHANGED /* 4849 :
                case KeyguardEffectViewController.MSG_WALLPAPER_FILE_CHANGED /* 4850 :
                    KeyguardEffectViewController.this.handleWallpaperImageChanged();
                    return;
                case KeyguardEffectViewController.MSG_WALLPAPER_PRELOAD_CHANGED /* 4851 :
                    KeyguardEffectViewController.this.handleSetGradationLayer();
                    return;
                case KeyguardEffectViewController.MSG_CHARGE_STATE_CHANGED /* 4852 :
                    KeyguardEffectViewController.this.handleChargeStateChange();
                    return;
                default:
                    return;
            }
        }
    }; */

    /* KeyguardUpdateMonitorCallback mInfoCallback = new KeyguardUpdateMonitorCallback() { // from class: com.android.keyguard.sec.KeyguardEffectViewController.2
        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public void onUserSwitching(int userId) {
            Log.d(KeyguardEffectViewController.TAG, "*** onUserSwitching - userId :" + userId);
            KeyguardEffectViewController.this.mUserSwitching = true;
            KeyguardEffectViewController.this.handleWallpaperTypeChanged();
            KeyguardEffectViewController.this.setWallpaperFileObserver();
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public void onScreenTurnedOn() {
            KeyguardEffectViewController.this.screenTurnedOn();
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public void onScreenTurnedOff(int why) {
            super.onScreenTurnedOff(why);
            KeyguardEffectViewController.this.screenTurnedOff();
        }
    };
    private KeyguardUpdateMonitorCallback mUpdateMonitorCallbacks = new KeyguardUpdateMonitorCallback() { // from class: com.android.keyguard.sec.KeyguardEffectViewController.3
        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public void onBootCompleted() {
            Log.d(KeyguardEffectViewController.TAG, "onBootCompleted: mBootCompleted = true;");
            KeyguardEffectViewController.this.mBootCompleted = true;
        }
    };
    private final BroadcastReceiver mAdminReceiver = new BroadcastReceiver() { // from class: com.android.keyguard.sec.KeyguardEffectViewController.4
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context cxt, Intent intent) {
            String action = intent.getAction();
            Log.d(KeyguardEffectViewController.TAG, "onReceive action : " + intent.getAction());
            if (action.equals("android.intent.action.TIME_TICK")) {
                if (KeyguardEffectViewController.this.mWallpaperPath == null || !KeyguardEffectViewController.this.mWallpaperPath.contains(KeyguardEffectViewController.SlidingWallpaperPath) || !KeyguardEffectViewController.setSlidingWallpaperInfo(KeyguardEffectViewController.this.mContext, intent)) {
                    Log.i(KeyguardEffectViewController.TAG, "*** don't update sliding image ***");
                    return;
                }
            } else if (action.equals(KeyguardEffectViewController.ACTION_IMAGES_CHANGED)) {
                KeyguardEffectViewController.this.getDataFromSlideshow(cxt);
            } else if (action.equals(KeyguardEffectViewUtil.ACTION_UPDATE_LOCKSCREEN_WALLPAPER)) {
            }
            if (KeyguardEffectViewController.this.mHandler.hasMessages(KeyguardEffectViewController.MSG_WALLPAPER_FILE_CHANGED)) {
                KeyguardEffectViewController.this.mHandler.removeMessages(KeyguardEffectViewController.MSG_WALLPAPER_FILE_CHANGED);
            }
            KeyguardEffectViewController.this.mHandler.sendEmptyMessage(KeyguardEffectViewController.MSG_WALLPAPER_FILE_CHANGED);
        }
    }; */
    public interface LockSoundChangeCallback {
        void reloadLockSound(String str, String str2);
    }

    @SuppressLint("LongLogTag")
    public KeyguardEffectViewController(Context context) {
        this.mFestivalEffectEnabled = false;
        this.mContext = context;
        //initWallpaperTypes();
        //setWallpaperContentObservers();

        this.mFestivalEffect = new KeyguardFestivalEffect(context, null);
        displayWidth = context.getResources().getDisplayMetrics().widthPixels;
        displayHeight = context.getResources().getDisplayMetrics().heightPixels;
    }

    @SuppressLint("LongLogTag")
    public static KeyguardEffectViewController getInstance(Context context) {
        if (sKeyguardEffectViewController == null) {
            sKeyguardEffectViewController = new KeyguardEffectViewController(context);
            Log.i(TAG, "*** KeyguardEffectView create instance ***");
        }
        return sKeyguardEffectViewController;
    }

    @SuppressLint("LongLogTag")
    public static KeyguardEffectViewController getInstanceIfExists(Context context) {
        Log.i(TAG, "*** KeyguardEffectView getInstanceIfExists ***");
        return sKeyguardEffectViewController;
    }

    /* public static boolean isLockScreenEffect(int effectType) {
        return effectType == 0 || effectType == 1 || effectType == 2 || effectType == 3 || effectType == 4 || effectType == 5 || effectType == 6 || effectType == 7 || effectType == 8 || effectType == 9 || effectType == 80 || effectType == 10 || effectType == 100 || effectType == 101 || effectType == 81 || effectType == 82 || effectType == 83 || effectType == 84 || effectType == 85 || effectType == 11 || effectType == 12;
    }

    private final void setWallpaperContentObservers() {
        this.mContentObserver = new ContentObserver(this.mHandler) { // from class: com.android.keyguard.sec.KeyguardEffectViewController.6
            @Override // android.database.ContentObserver
            public void onChange(boolean selfChange, Uri uri) {
                if (uri != null) {
                    if (uri.equals(Settings.System.getUriFor("lockscreen_wallpaper"))) {
                        if (KeyguardEffectViewController.this.mHandler.hasMessages(KeyguardEffectViewController.MSG_WALLPAPER_TYPE_CHANGED)) {
                            KeyguardEffectViewController.this.mHandler.removeMessages(KeyguardEffectViewController.MSG_WALLPAPER_TYPE_CHANGED);
                        }
                        KeyguardEffectViewController.this.mHandler.sendEmptyMessage(KeyguardEffectViewController.MSG_WALLPAPER_TYPE_CHANGED);
                    } else if (uri.equals(Settings.System.getUriFor("lockscreen_ripple_effect"))) {
                        if (KeyguardEffectViewController.this.mHandler.hasMessages(KeyguardEffectViewController.MSG_WALLPAPER_TYPE_CHANGED)) {
                            KeyguardEffectViewController.this.mHandler.removeMessages(KeyguardEffectViewController.MSG_WALLPAPER_TYPE_CHANGED);
                        }
                        KeyguardEffectViewController.this.mHandler.sendEmptyMessage(KeyguardEffectViewController.MSG_WALLPAPER_TYPE_CHANGED);
                    } else if (uri.equals(Settings.System.getUriFor("lockscreen_wallpaper_path")) || uri.equals(Settings.System.getUriFor("lockscreen_wallpaper_path_2"))) {
                        super.onChange(selfChange);
                        KeyguardEffectViewController.this.setWallpaperFileObserver();
                        Log.d(KeyguardEffectViewController.TAG, "mWallpaperPath = " + KeyguardEffectViewController.this.mWallpaperPath);
                        if (KeyguardEffectViewController.this.mHandler.hasMessages(KeyguardEffectViewController.MSG_WALLPAPER_PATH_CHANGED)) {
                            KeyguardEffectViewController.this.mHandler.removeMessages(KeyguardEffectViewController.MSG_WALLPAPER_PATH_CHANGED);
                        }
                        KeyguardEffectViewController.this.mHandler.sendEmptyMessage(KeyguardEffectViewController.MSG_WALLPAPER_PATH_CHANGED);
                    } else if (uri.equals(Settings.System.getUriFor("emergency_mode"))) {
                        if (KeyguardEffectViewController.this.mHandler.hasMessages(KeyguardEffectViewController.MSG_WALLPAPER_FILE_CHANGED)) {
                            KeyguardEffectViewController.this.mHandler.removeMessages(KeyguardEffectViewController.MSG_WALLPAPER_FILE_CHANGED);
                        }
                        KeyguardEffectViewController.this.mEmergencyModeStateChanged = true;
                        KeyguardEffectViewController.this.mHandler.sendEmptyMessage(KeyguardEffectViewController.MSG_WALLPAPER_FILE_CHANGED);
                    } else if (uri.equals(Settings.System.getUriFor("lockscreen_wallpaper_transparent"))) {
                        if (KeyguardEffectViewController.this.mHandler.hasMessages(KeyguardEffectViewController.MSG_WALLPAPER_PRELOAD_CHANGED)) {
                            KeyguardEffectViewController.this.mHandler.removeMessages(KeyguardEffectViewController.MSG_WALLPAPER_PRELOAD_CHANGED);
                        }
                        KeyguardEffectViewController.this.mHandler.sendEmptyMessage(KeyguardEffectViewController.MSG_WALLPAPER_PRELOAD_CHANGED);
                    } else if (uri.equals(Settings.System.getUriFor("lockscreen_zoom_panning_effect"))) {
                        if (KeyguardEffectViewController.this.mHandler.hasMessages(KeyguardEffectViewController.MSG_WALLPAPER_TYPE_CHANGED)) {
                            KeyguardEffectViewController.this.mHandler.removeMessages(KeyguardEffectViewController.MSG_WALLPAPER_TYPE_CHANGED);
                        }
                        KeyguardEffectViewController.this.mHandler.sendEmptyMessage(KeyguardEffectViewController.MSG_WALLPAPER_TYPE_CHANGED);
                    }
                }
            }
        };
        this.mContext.getContentResolver().registerContentObserver(Settings.System.getUriFor("lockscreen_ripple_effect"), false, this.mContentObserver, -1);
        this.mContext.getContentResolver().registerContentObserver(Settings.System.getUriFor("lockscreen_wallpaper"), false, this.mContentObserver, -1);
        this.mContext.getContentResolver().registerContentObserver(Settings.System.getUriFor("lockscreen_wallpaper_path"), false, this.mContentObserver, -1);
        this.mContext.getContentResolver().registerContentObserver(Settings.System.getUriFor("lockscreen_wallpaper_path_2"), false, this.mContentObserver, -1);
        this.mContext.getContentResolver().registerContentObserver(Settings.System.getUriFor("emergency_mode"), false, this.mContentObserver, -1);
        this.mContext.getContentResolver().registerContentObserver(Settings.System.getUriFor("lockscreen_wallpaper_transparent"), false, this.mContentObserver, -1);
        this.mContext.getContentResolver().registerContentObserver(Settings.System.getUriFor("lockscreen_zoom_panning_effect"), false, this.mContentObserver, -1);
        setWallpaperFileObserver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_IMAGES_CHANGED);
        filter.addAction("android.intent.action.TIME_TICK");
        filter.addAction("android.intent.action.TIME_SET");
        filter.addAction(ACTION_LOCKSCREEN_IMAGE_CHANGED);
        filter.addAction(KeyguardEffectViewUtil.ACTION_UPDATE_LOCKSCREEN_WALLPAPER);
        filter.addAction("android.intent.action.DEFAULT_CS_SIM_CHANGED");
        this.mContext.registerReceiver(this.mAdminReceiver, filter);
    }

     JADX INFO: Access modifiers changed from: private
    public void setWallpaperFileObserver() {
        this.mWallpaperPath = Settings.System.getStringForUser(this.mContext.getContentResolver(), "lockscreen_wallpaper_path", -2);
        Log.d(TAG, "mWallpaperPath = " + this.mWallpaperPath);
        if (this.mWallpaperPath != null) {
            if (this.mWallpaperPath != null && this.mWallpaperPath.contains(SlidingWallpaperPath)) {
                getDataFromSlideshow(this.mContext);
            }
            if (this.mFileObserver != null) {
                this.mFileObserver.stopWatching();
                this.mFileObserver = null;
            }
            this.mFileObserver = new FileObserver(this.mWallpaperPath) { // from class: com.android.keyguard.sec.KeyguardEffectViewController.7
                @Override // android.os.FileObserver
                public void onEvent(int event, String path) {
                    switch (event) {
                        case 8:
                            break;
                        default:
                            return;
                        case 512:
                        case 1024:
                            Settings.System.putStringForUser(KeyguardEffectViewController.this.mContext.getContentResolver(), "lockscreen_wallpaper_path", Feature.mLocalSecurity, -2);
                            break;
                    }
                    Log.i(KeyguardEffectViewController.TAG, "CLOSE_WRITE");
                    if (KeyguardEffectViewController.this.mHandler.hasMessages(KeyguardEffectViewController.MSG_WALLPAPER_FILE_CHANGED)) {
                        KeyguardEffectViewController.this.mHandler.removeMessages(KeyguardEffectViewController.MSG_WALLPAPER_FILE_CHANGED);
                    }
                    KeyguardEffectViewController.this.mHandler.sendEmptyMessage(KeyguardEffectViewController.MSG_WALLPAPER_FILE_CHANGED);
                }
            };
            this.mFileObserver.startWatching();
        }
    }

    private boolean handleFestivalEffect() {
        KeyguardEffectViewBase festivalView;
        if (this.mFestivalEffect.isEnabled() || this.mFestivalEffect.isCommonDayShowFestivalWallpaper()) {
            boolean isStrongPowerSavingEnabled = KeyguardEffectViewUtil.isStrongPowerSavingMode(this.mContext);
            if (isStrongPowerSavingEnabled || (festivalView = this.mFestivalEffect.getFestivalView()) == null) {
                return false;
            }
            if (this.mBackgroundView != festivalView) {
                Log.d("KeyguardFestivalEffect", "change festival");
                this.mBackgroundView = null;
                this.mBackgroundView = festivalView;
                Log.i(TAG, "handleFestivalEffect   mIsShowing = " + this.mIsShowing);
                setBackground();
            }
            if (this.mFestivalEffect.isUnlockEffectEnabled()) {
                this.mForegroundView = null;
                this.mForegroundView = this.mFestivalEffect.getUnlockEffectView();
                this.mUnlockEffectView = this.mForegroundView;
                this.mCurrentEffect = 85;
                setForeground();
            } else {
                this.mForegroundType = Foreground.circle;
                makeForeground(this.mForegroundType);
                this.mCurrentEffect = 0;
                if (this.mForegroundView != null) {
                    this.mUnlockEffectView = this.mForegroundView;
                } else {
                    this.mUnlockEffectView = this.mForegroundCircleView;
                }
            }
            return true;
        }
        return false;
    } */

    /*private String getEffectClassName(String nameOfEffect) {
        if (nameOfEffect == null || nameOfEffect.length() == 0) {
            return null;
        }
        if ("LiveWallpaper".equals(nameOfEffect)) {
            return "com.android.keyguard.sec.rich.KeyguardEffectView" + nameOfEffect;
        }
        if ("Spring".equals(nameOfEffect) || "Summer".equals(nameOfEffect) || "Autumn".equals(nameOfEffect) || "Winter".equals(nameOfEffect) || "Seasonal".equals(nameOfEffect)) {
            return this.mFestivalEffect.getFestivalEffectClassName(nameOfEffect);
        }
        return "com.android.keyguard.sec.KeyguardEffectView" + nameOfEffect;
    } */

    public void handleWallpaperTypeChanged() {
        if (this.mBackgroundRootLayout != null) {
            // TODO: get effect
            this.mCurrentEffect = mContext.getSharedPreferences("settings", 0).getInt("lockscreen_ripple_effect", MainActivity.effect); //todo Settings.System.getInt(mContext.getContentResolver(), "lockscreen_ripple_effect", 0); // getIntForUser(this.mContext.getContentResolver(), "lockscreen_ripple_effect", 0, -2);
            if (this.mForegroundCircleView == null) {
                this.mForegroundCircleView = new KeyguardEffectViewNone(this.mContext);
            }
            /*if (isZoomPanningEffectEnabled() && isRichLockWallpaper()) {
                this.mCurrentEffect = 80;
            }
            if (handleFestivalEffect()) {
                this.mOldBackgroundType = Background.unspecified;
                this.mOldForegroundType = Foreground.unspecified;
                return;
            }
            int wallpaperType = KeyguardEffectViewUtil.getWallpaperType(this.mContext); */
            //handleSetGradationLayer();
            //this.mBackgroundRootLayout.setBackgroundColor(Color.GRAY);//WHITE);
            if (this.mOldEffect != this.mCurrentEffect) {
                this.mOldEffect = this.mCurrentEffect;
                cleanUp();
                // TODO: make use of reloadLockSound(); in future
            }
            /*
            this.mForegroundType = this.mForegroundTypeMapping.get(Integer.valueOf(this.mCurrentEffect));
            if (this.mForegroundType == null) {
                this.mForegroundType = Foreground.circle;
            }
            this.mBackgroundType = this.mBackgroundTypeMapping.get(Integer.valueOf(this.mCurrentEffect));
            if (this.mBackgroundType == null) {
                this.mBackgroundType = Background.wallpaper;
            }
            if (this.mForegroundType != this.mOldForegroundType || this.mForegroundType == Foreground.seasonal) {
                this.mOldForegroundType = this.mForegroundType;
                makeForeground(this.mForegroundType);
            }
            if (this.mBackgroundType != this.mOldBackgroundType) {
                this.mOldBackgroundType = this.mBackgroundType;
                makeBackground(this.mBackgroundType);
            } else if (isJustLockLiveEnabled()) {
                this.mBackgroundView.update();
            }
            if (this.mUnlockEffectViewMapping != null && this.mUnlockEffectViewMapping.containsKey(Integer.valueOf(this.mCurrentEffect)) && this.mUnlockEffectViewMapping.get(Integer.valueOf(this.mCurrentEffect)).intValue() == 2) {
                this.mUnlockEffectView = this.mBackgroundView;
            } else if (this.mForegroundView == null) {
                this.mUnlockEffectView = this.mForegroundCircleView;
            } else {
                this.mUnlockEffectView = this.mForegroundView;
            }*/
            makeEffectViews(mCurrentEffect);
            setLayerAndBitmapForParticleEffect();
        }
    }

    // TODO: isLockLive?
    public boolean isJustLockLiveEnabled() {
        /*boolean isLiveWallpaperBackground = this.mBackgroundView != null && (this.mBackgroundView instanceof KeyguardEffectViewLiveWallpaper);
        return this.mCurrentEffect == 100;*/
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private
    public void handleChargeStateChange() {
        if (this.mFestivalEffect.isChargeState()) {
            if (this.mBgHasAddChargeView) {
                this.mBackgroundRootLayout.removeView((View) this.mChargeView);
                this.mBgHasAddChargeView = false;
                this.mChargeView = null;
            }
            this.mChargeView = this.mFestivalEffect.getChargeEffectView();
            if (this.mChargeView != null) {
                if (this.mBackgroundView == null) {
                    this.mBackgroundRootLayout.addView((View) this.mChargeView, 0);
                } else {
                    this.mBackgroundRootLayout.addView((View) this.mChargeView, 1);
                }
                this.mBgHasAddChargeView = true;
            }
        } else if (this.mBgHasAddChargeView) {
            this.mBackgroundRootLayout.removeView((View) this.mChargeView);
            this.mBgHasAddChargeView = false;
            this.mChargeView = null;
        }
    }

    public boolean isZoomPanningEffectEnabled() {
        boolean isZoomPanningEffect = Settings.System.getIntForUser(this.mContext.getContentResolver(), "lockscreen_zoom_panning_effect", 0, -2) == 1;
        boolean isPowerSavingModeEnabled = KeyguardEffectViewUtil.isPowerSavingMode(this.mContext);
        Log.d(TAG, "isZoomPanningEffectEnabled() isZoomPanningEffect = " + isZoomPanningEffect + ", isPowerSavingMode = " + isPowerSavingModeEnabled);
        return isZoomPanningEffect && !isPowerSavingModeEnabled;
    }

    public boolean isRichLockWallpaper() {
        if (this.mWallpaperPath == null) {
            return false;
        }
        boolean isRichLock = this.mWallpaperPath.startsWith(RICH_LOCK_WALLPAPER_ROOT_PATH);
        Log.d(TAG, "isRichLockWallpaper() = " + isRichLock);
        return isRichLock;
    } */

    /*public boolean isCategoriesWallpaper() {
        boolean z = true;
        if (this.mWallpaperPath == null) {
            return false;
        }
        boolean isCategoriesWallpaper = this.mWallpaperPath.startsWith(RICH_LOCK_CATEGORIES_WALLPAPER_ROOT_PATH);
        boolean isLiveWallpaper = this.mCurrentEffect == -1;
        Log.d(TAG, "isCategoriesWallpaper = " + isCategoriesWallpaper + ", isLiveWallpaper = " + isLiveWallpaper);
        if (!isCategoriesWallpaper || isLiveWallpaper) {
            z = false;
        }
        return z;
    }*/

    private void setForeground() {
        if (this.mForegroundRootLayout != null) {
            this.mForegroundRootLayout.removeAllViews();
        }
        if (this.mForegroundView != null) {
            this.mForegroundRootLayout.addView((View) this.mForegroundView, -1, -1);
        } else {
            this.mForegroundRootLayout.addView((View) this.mForegroundCircleView, -1, -1);
        }
    }

    private void setBackground() {
        if (this.mChargeView != null) {
            this.mChargeView.cleanUp();
        }
        this.mBackgroundRootLayout.removeAllViews();
        if (this.mBackgroundView != null) {
            this.mBackgroundRootLayout.addView((View) this.mBackgroundView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            //mBackgroundRootLayout.invalidate();
            handleSetGradationLayer();
        }
        if (this.mFestivalEffect.isChargeState()) {
            this.mChargeView = this.mFestivalEffect.getChargeEffectView();
            if (this.mChargeView != null) {
                this.mBackgroundRootLayout.addView((View) this.mChargeView, -1, -1);
                this.mBgHasAddChargeView = true;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleWallpaperImageChanged() {
        if (!this.mMusicBackgroundSet && this.mBackgroundView != null) {
            handleWallpaperTypeChanged();
            updateMontblancEffectType();
            this.mBackgroundView.update();
        }
    }

    /* @SuppressLint("LongLogTag")
    public void setLiveWallpaperBg(Bitmap bmp) {
        if (this.mBackgroundView == null) {
            int wallpaperType = KeyguardEffectViewUtil.getWallpaperType(this.mContext);
            if (wallpaperType == 0 || wallpaperType == 2) {
                Log.d(TAG, "setLiveWallpaperBg = " + (bmp != null));
                ((EffectBehindView) this.mBackgroundRootLayout).setLiveWallpaperBg(bmp);
            }
        }
    } */

    /* TODO: handle music bg
    @SuppressLint("LongLogTag")
    public void handleUpdateKeyguardMusicBackground(Bitmap bmp) {
        Log.d(TAG, "handleUpdateKeyguardMusicBackground(): bmp=" + bmp);
        if (this.mBackgroundView != null) {
            this.mMusicBackgroundSet = true;
            if (this.mBackgroundView instanceof KeyguardEffectViewWallpaper) {
                ((KeyguardEffectViewWallpaper) this.mBackgroundView).setContextualWallpaper(bmp);
            } else if (this.mBackgroundView instanceof KeyguardEffectViewRippleInk) {
                ((KeyguardEffectViewRippleInk) this.mBackgroundView).setContextualWallpaper(getARGB8888Bitmap(bmp));
            } else if (this.mBackgroundView instanceof KeyguardEffectViewWaterColor) {
                ((KeyguardEffectViewWaterColor) this.mBackgroundView).setContextualWallpaper(getARGB8888Bitmap(bmp));
            } else if (this.mBackgroundView instanceof KeyguardEffectViewZoomPanning) {
                ((KeyguardEffectViewZoomPanning) this.mBackgroundView).setContextualWallpaper(getARGB8888Bitmap(bmp));
            } else if (this.mBackgroundView instanceof KeyguardEffectViewIndigoDiffusion) {
                ((KeyguardEffectViewIndigoDiffusion) this.mBackgroundView).setContextualWallpaper(getARGB8888Bitmap(bmp));
            } else if (this.mBackgroundView instanceof KeyguardEffectViewAbstractTile) {
                ((KeyguardEffectViewAbstractTile) this.mBackgroundView).setContextualWallpaper(getARGB8888Bitmap(bmp));
            } else if (this.mBackgroundView instanceof KeyguardEffectViewGeometricMosaic) {
                ((KeyguardEffectViewGeometricMosaic) this.mBackgroundView).setContextualWallpaper(getARGB8888Bitmap(bmp));
            }
        }
    }

    @SuppressLint("LongLogTag")
    public Bitmap getARGB8888Bitmap(Bitmap srcBitmap) {
        if (srcBitmap == null) {
            return null;
        }
        if (srcBitmap.getConfig() != Bitmap.Config.ARGB_8888) {
            Log.i(TAG, "start to convert album art");
            int width = srcBitmap.getWidth();
            int height = srcBitmap.getHeight();
            if (width <= 0 || height <= 0) {
                return null;
            }
            Bitmap destBitmap = Bitmap.createBitmap(srcBitmap.getWidth(), srcBitmap.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas destCanvas = new Canvas(destBitmap);
            destCanvas.drawBitmap(srcBitmap, 0.0f, 0.0f, (Paint) null);
            return destBitmap;
        }
        return srcBitmap;
    } */

    public void updateMontblancEffectType() {
        /* TODO: update montblanc
        if (this.mBackgroundView instanceof KeyguardEffectViewIndigoDiffusion) {
            int montblancWallpaperInkType = Settings.System.getIntForUser(this.mContext.getContentResolver(), "lockscreen_montblanc_wallpaper", 0, -2);
            int type = 0;
            if (montblancWallpaperInkType == 2) {
                type = 1;
            }
            ((KeyguardEffectViewIndigoDiffusion) this.mBackgroundView).settingsForImageType(type);
        }*/
    }

    @SuppressLint("LongLogTag")
    @Override // com.android.keyguard.sec.KeyguardEffectViewBase
    public void show() {
        Log.d(TAG, "show()");
        //KeyguardUpdateMonitor.getInstance(this.mContext).registerCallback(this.mInfoCallback);
        this.mFestivalEffect.SetShowState(true);
        if (this.mForegroundView != null) {
            this.mForegroundView.show();
        }
        if (this.mForegroundCircleView != null) {
            this.mForegroundCircleView.show();
        }
        if (this.mBackgroundView != null) {
            this.mBackgroundView.show();
        }
        if (this.mFestivalEffect.isChargeState() && this.mFestivalEffect.GetScreenState() && this.mChargeView != null) {
            this.mChargeView.show();
            this.mFestivalEffect.pauseAnimation();
        }
    }

    @SuppressLint("LongLogTag")
    @Override // com.android.keyguard.sec.KeyguardEffectViewBase
    // TODO: rewrite the whole thing
    public void cleanUp() {
        Log.d(TAG, "cleanUp()");
        if (this.mForegroundView != null) {
            this.mForegroundView.cleanUp();
        }
        if (this.mForegroundCircleView != null) {
            this.mForegroundCircleView.cleanUp();
        }
        if (this.mBackgroundView != null) {
            this.mBackgroundView.cleanUp();
        }
        if (this.mChargeView != null) {
            this.mChargeView.cleanUp();
        }
        //KeyguardUpdateMonitor.getInstance(this.mContext).removeCallback(this.mInfoCallback);
    }

    @SuppressLint("LongLogTag")
    @Override // com.android.keyguard.sec.KeyguardEffectViewBase
    public void reset() {
        Log.d(TAG, "reset()");
        if (this.mForegroundView != null) {
            this.mForegroundView.reset();
        }
        if (this.mForegroundCircleView != null) {
            this.mForegroundCircleView.reset();
        }
        if (this.mBackgroundView != null) {
            this.mBackgroundView.reset();
        }
    }

    @Override // com.android.keyguard.sec.KeyguardEffectViewBase
    public void setContextualWallpaper(Bitmap bmp) {
        Log.d(TAG, "reset()");
        if (this.mForegroundView != null) {
            this.mForegroundView.setContextualWallpaper(bmp);
        }
        if (this.mForegroundCircleView != null) {
            this.mForegroundCircleView.setContextualWallpaper(bmp);
        }
        if (this.mBackgroundView != null) {
            this.mBackgroundView.setContextualWallpaper(bmp);
        }
    }

    @Override // com.android.keyguard.sec.KeyguardEffectViewBase
    public void screenTurnedOn() {
        if (this.mForegroundView != null) {
            this.mForegroundView.screenTurnedOn();
        }
        if (this.mForegroundCircleView != null) {
            this.mForegroundCircleView.screenTurnedOn();
        }
        if (this.mBackgroundView != null) {
            this.mBackgroundView.screenTurnedOn();
        }
    }

    @Override // com.android.keyguard.sec.KeyguardEffectViewBase
    public long getUnlockDelay() {
        if (this.mUnlockEffectView != null) {
            return this.mUnlockEffectView.getUnlockDelay();
        }
        return 0L;
    }

    @Override // com.android.keyguard.sec.KeyguardEffectViewBase
    public void playLockSound() {
        if (this.mUnlockEffectView != null) {
            this.mUnlockEffectView.playLockSound();
        }
    }

    @Override // com.android.keyguard.sec.KeyguardEffectViewBase
    public void screenTurnedOff() {
        this.mFestivalEffect.SetScreenState(false);
        if (this.mForegroundView != null) {
            this.mForegroundView.screenTurnedOff();
        }
        if (this.mForegroundCircleView != null) {
            this.mForegroundCircleView.screenTurnedOff();
        }
        if (this.mBackgroundView != null) {
            this.mBackgroundView.screenTurnedOff();
        }
        if (this.mChargeView != null) {
            this.mChargeView.screenTurnedOff();
        }
    }

    @Override // com.android.keyguard.sec.KeyguardEffectViewBase
    public void update() {
        if (this.mForegroundView != null) {
            this.mForegroundView.update();
        }
        if (this.mForegroundCircleView != null) {
            this.mForegroundCircleView.update();
        }
        if (this.mBackgroundView != null) {
            this.mBackgroundView.update();
        }
    }

    @Override // com.android.keyguard.sec.KeyguardEffectViewBase
    public void showUnlockAffordance(long startDelay, Rect rect) {
        if (this.mUnlockEffectView != null) {
            this.mUnlockEffectView.showUnlockAffordance(startDelay, rect);
        }
    }

    @Override // com.android.keyguard.sec.KeyguardEffectViewBase
    public void handleUnlock(View view, MotionEvent event) {
        if (view != null && this.mForegroundCircleView != null) {
            this.mForegroundCircleView.handleUnlock(view, event);
            MainActivity.switchActivity(mContext);
        } else if (this.mUnlockEffectView != null) {
            this.mUnlockEffectView.handleUnlock(view, event);
            // todo handle un;lock
            Handler handler = new Handler();
            handler.postDelayed(() -> {
                MainActivity.switchActivity(mContext);
            }, getUnlockDelay()+250L);

        }
    }

    @Override // com.android.keyguard.sec.KeyguardEffectViewBase
    public boolean handleTouchEvent(View view, MotionEvent event) {
        if (view != null && this.mForegroundCircleView != null) {
            return this.mForegroundCircleView.handleTouchEvent(view, event);
        }
        if (this.mUnlockEffectView != null) {
            return this.mUnlockEffectView.handleTouchEvent(view, event);
        }
        return false;
    }

    @Override // com.android.keyguard.sec.KeyguardEffectViewBase
    public boolean handleTouchEventForPatternLock(View view, MotionEvent event) {
        if (this.mUnlockEffectView != null) {
            return this.mUnlockEffectView.handleTouchEventForPatternLock(view, event);
        }
        return false;
    }

    @Override // com.android.keyguard.sec.KeyguardEffectViewBase
    public boolean handleHoverEvent(View view, MotionEvent event) {
        // TODO: rippleink limitation switch
        if (this.mUnlockEffectView == null) {
            return false;
        }
        Log.d(TAG, "handleHoverEvent: called");
        return mUnlockEffectView.handleHoverEvent(view, event);
    }

    public boolean handleSensorEvent(SensorEvent event) {
        if (this.mMusicBackgroundSet) {
        }
        return false;
    }

    public FrameLayout getForegroundLayout() {
        return this.mForegroundRootLayout;
    }

    public FrameLayout getBackgroundLayout() {
        return this.mBackgroundRootLayout;
    }

    /* @SuppressLint("LongLogTag")
    public static String getSlidingWallpaperPath(Context context) {
        String ret;
        if (uriArray != null) {
            Log.d(TAG, "mSlidingWallpaperIndex = " + mSlidingWallpaperIndex + " , uriArray.size(): " + uriArray.size());
            if (mSlidingWallpaperIndex >= uriArray.size()) {
                mSlidingWallpaperIndex = 0;
            }
            String ret2 = uriArray.get(mSlidingWallpaperIndex);
            ret = ret2;
            File file = new File(ret);
            if (!file.exists()) {
                ret = EMPTY_WALLPAPER_IMAGE_PATH;
            }
        } else {
            ret = EMPTY_WALLPAPER_IMAGE_PATH;
        }
        Log.d(TAG, "getSlidingWallpaperPath = " + ret);
        return ret;
    }

    @SuppressLint("LongLogTag")
    public static boolean setSlidingWallpaperInfo(Context context, Intent intent) {
        boolean results = false;
        String action = intent.getAction();
        Log.d(TAG, "mSlidingInterval = " + mSlidingInterval + " , mSlidingWallpaperIndex:" + mSlidingWallpaperIndex);
        switch (mSlidingInterval) {
            case 0:
                if (action.equals("android.intent.action.SCREEN_OFF")) {
                    results = true;
                    break;
                }
                break;
            case 1:
            case 2:
            case 3:
                if (action.equals("android.intent.action.TIME_TICK")) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(System.currentTimeMillis());
                    String hourText = new SimpleDateFormat("HH").format(calendar.getTime());
                    String hourTextBy12 = new SimpleDateFormat("hh").format(calendar.getTime());
                    String minuteText = new SimpleDateFormat("mm").format(calendar.getTime());
                    int mHour = Integer.valueOf(hourText).intValue();
                    int mHour12 = Integer.valueOf(hourTextBy12).intValue();
                    int mMinute = Integer.valueOf(minuteText).intValue();
                    Log.d(TAG, " " + mHour + " , : " + mMinute + " , " + mSlidingHour + ",  :" + mSlidingMin);
                    if (mSlidingInterval == 1) {
                        if (mMinute == mSlidingMin) {
                            results = true;
                            break;
                        }
                    } else if (mSlidingInterval == 2) {
                        if (mHour12 == mSlidingHour && mMinute == mSlidingMin) {
                            results = true;
                            break;
                        }
                    } else if (mSlidingInterval == 3 && mHour == mSlidingHour && mMinute == mSlidingMin) {
                        results = true;
                        break;
                    }
                }
                break;
        }
        if (results) {
            mSlidingWallpaperIndex++;
            if (mSlidingWallpaperIndex >= mSlidingTotalCount) {
                mSlidingWallpaperIndex = 0;
            }
        }
        Log.d(TAG, "results = " + results + " , mSlidingWallpaperIndex:" + mSlidingWallpaperIndex);
        return results;
    }

    // JADX INFO: Access modifiers changed from: private
    @SuppressLint("LongLogTag")
    public void getDataFromSlideshow(Context context) {
        boolean isEmergencyMode = Settings.System.getIntForUser(context.getContentResolver(), "emergency_mode", 0, -2) == 1;
        boolean isUltraPowerSavingMode = Settings.System.getInt(context.getContentResolver(), "ultra_powersaving_mode", 0) == 1;
        Log.d(TAG, "EMMode : " + isEmergencyMode + ",UPSMode :" + isUltraPowerSavingMode);
        if (isEmergencyMode || isUltraPowerSavingMode) {
            uriArray = null;
            return;
        }
        ContentResolver resolver = context.getContentResolver();
        Uri uri = Uri.parse("content://com.samsung.slidinggallery");
        Bundle bundle = resolver.call(uri, "getData", (String) null, (Bundle) null);
        if (bundle != null) {
            mSlidingInterval = bundle.getInt("interval");
            mSlidingTotalCount = bundle.getInt("imgCount");
            mSlidingTime = bundle.getLong(Constants.REMOTE_VIEW_INFO_TIME);
            mSlidingHour = bundle.getInt("hour");
            mSlidingMin = bundle.getInt("minute");
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(mSlidingTime);
            String hourText = new SimpleDateFormat("HH").format(calendar.getTime());
            String minuteText = new SimpleDateFormat("mm").format(calendar.getTime());
            if (mSlidingInterval == 2) {
                hourText = new SimpleDateFormat("hh").format(calendar.getTime());
            }
            mSlidingHour = Integer.valueOf(hourText).intValue();
            mSlidingMin = Integer.valueOf(minuteText).intValue();
            uriArray = bundle.getStringArrayList("uriArray");
            Log.d(TAG, "interval: " + mSlidingInterval);
            Log.d(TAG, "imgCount: " + mSlidingTotalCount);
            Log.d(TAG, "mSlidingTime: " + mSlidingTime + " ,Hour :" + mSlidingHour + " ,Min:" + mSlidingMin);
            if (uriArray != null) {
                Iterator i$ = uriArray.iterator();
                while (i$.hasNext()) {
                    String uriStr = i$.next();
                    Log.d(TAG, "uriStr: " + uriStr);
                }
                return;
            }
            return;
        }
        uriArray = null;
    } */

    /* TODO: get scaled wallpaper
    @SuppressLint("LongLogTag")
    public static BitmapDrawable getScaledBitmapDrawable(Context context) {
        InputStream sis = null;
        BitmapDrawable tempBitmapDrawable = null;
        Bitmap tempBitmap;
        String tempPath = getSlidingWallpaperPath(context);
        Log.d(TAG, "getScaledBitmapDrawable Path = " + tempPath);
        try {
            File mdmFile = new File(tempPath);
            InputStream sis2 = new FileInputStream(mdmFile);
            int mOrientation = getSlidingWallpaperDegree(tempPath);
            BitmapFactory.Options opts = new BitmapFactory.Options();
            opts.inSampleSize = 4;
            Bitmap tempBitmap2 = BitmapFactory.decodeStream(sis2, null, opts);
            if (mOrientation > 0) {
                tempBitmap2 = adjustPhotoRotation(tempBitmap2, mOrientation);
            }
            Log.d(TAG, "getScaledBitmapDrawable File = " + mdmFile.exists() + " ,canRead() : " + mdmFile.canRead() + " tempBitmap:" + tempBitmap2 + " displayWidth=" + displayWidth + " displayHeight=" + displayHeight);

            tempBitmap = Bitmap.createScaledBitmap(tempBitmap2, displayWidth, displayHeight, true);
            sis = sis2;

            tempBitmapDrawable = new BitmapDrawable(tempBitmap);
        } catch (IOException e2) {
        }
        try {
            sis.close();
        } catch (IOException e3) {
            e3.printStackTrace();
        }
        return tempBitmapDrawable;
    }

    private static int getSlidingWallpaperDegree(String path) {
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt("Orientation", 1);
            switch (orientation) {
                case 3:
                    return 180;
                case 4:
                case 5:
                case 7:
                default:
                    return 0;
                case 6:
                    return 90;
                case 8:
                    return 270;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }

    private static Bitmap adjustPhotoRotation(Bitmap bm, int orientationDegree) {
        Matrix m = new Matrix();
        m.setRotate(orientationDegree, bm.getWidth() / 2.0f, bm.getHeight() / 2.0f);
        try {
            return Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), m, true);
        } catch (OutOfMemoryError e) {
            return null;
        }
    } */


    @SuppressLint("LongLogTag")
    @Override // com.android.keyguard.sec.KeyguardEffectViewBase
    public void setHidden(boolean isHidden) {
        Log.i(TAG, "setHidden = " + isHidden);
        if (this.mForegroundView != null) {
            this.mForegroundView.setHidden(isHidden);
        }
        if (this.mForegroundCircleView != null) {
            this.mForegroundCircleView.setHidden(isHidden);
        }
        if (this.mBackgroundView != null) {
            this.mBackgroundView.setHidden(isHidden);
        }
        this.mIsVisible = !isHidden;
    }

    protected int getCurrentEffecType() {
        return this.mCurrentEffect;
    }

    public void setLayerAndBitmapForParticleEffect() {
        /* TODO: particle layers bitmaps
        if (this.mForegroundView instanceof KeyguardEffectViewParticleSpace) {
            KeyguardEffectViewParticleSpace effectViewParticleSpace = (KeyguardEffectViewParticleSpace) this.mForegroundView;
            KeyguardEffectViewWallpaper effectWallpaper = (KeyguardEffectViewWallpaper) this.mBackgroundView;
            effectViewParticleSpace.setLayers(effectWallpaper, this.mNotificationPanel);
        }
        if (this.mBackgroundView instanceof KeyguardEffectViewZoomPanning) {
            KeyguardEffectViewZoomPanning effectViewZoomPanning = (KeyguardEffectViewZoomPanning) this.mBackgroundView;
            effectViewZoomPanning.setLayers(this.mNotificationPanel);
        } */
    }

    /* public void removeMusicWallpaper() {
        this.mMusicBackgroundSet = false;
        updateAttributionInfoView();
        if (this.mBackgroundView instanceof KeyguardEffectViewZoomPanning) {
            ((KeyguardEffectViewZoomPanning) this.mBackgroundView).removeMusicWallpaper();
        }
        getDefaultWallpaperTypeForEffect();
    }

    public int getDefaultWallpaperTypeForEffect() {
        return Settings.System.getIntForUser(this.mContext.getContentResolver(), SETTING_KEYGUARD_DEFAULT_WALLPAPER_TYPE_FOR_EFFECT, 1, -2);
    }
    public boolean isMusicBackgroundSet() {
        return this.mMusicBackgroundSet;
    } */

    public void setEffectLayout(View background, View foreground, View panel) {
        this.mBackgroundRootLayout = (FrameLayout) background;
        this.mForegroundRootLayout = (FrameLayout) foreground;
        // TODO: remember this.mNotificationPanel = (FrameLayout) panel;
        handleWallpaperTypeChanged();
    }

    @SuppressLint("LongLogTag")
    public void setKeyguardShowing(boolean showing) {
        Log.i(TAG, "setKeyguardShowing = " + showing);
        int visibility = showing ? View.VISIBLE : View.GONE;
        this.mBackgroundRootLayout.setVisibility(visibility);
        this.mForegroundRootLayout.setVisibility(visibility);
        if (showing) {
            if (this.mIsShowing) {
                reset();
            } else {
                show();
            }
        } else {
            cleanUp();
        }
        this.mIsShowing = showing;
        setFestivalKeyguardShowing(showing, visibility);
    }

    @SuppressLint("LongLogTag")
    public void setFestivalKeyguardShowing(boolean showing, int visibility) {
        Log.i(TAG, "setFestivalKeyguardShowing = " + showing + " ,:" + isFestivalActivated());
        if (isFestivalActivated() && this.mBackgroundView != null) {
            ViewGroup convertedView = (ViewGroup) this.mBackgroundView;
            for (int childIdx = 0; childIdx < convertedView.getChildCount(); childIdx++) {
                View childView = convertedView.getChildAt(childIdx);
                if (childView != null) {
                    childView.setVisibility(visibility);
                }
            }
            convertedView.setVisibility(visibility);
            convertedView.invalidate();
        }
    }

    /* TODO: remember this moment. reloadLockSound();
    public void setLockSoundChangeCallback(LockSoundChangeCallback callback) {
        this.mLockSoundChangeCallback = callback;
        reloadLockSound();
    }

    @SuppressLint("LongLogTag")
    public void reloadLockSound() {
        LockSoundInfo info;
        Log.d(TAG, "reloadLockSound()");
        if (isFestivalActivated()) {
            switch (this.mCurrentEffect) {
                case EFFECT_SEASONAL:
                    int season = getCurrentSeasonEffect();
                    info = new LockSoundInfo(getSeasonalLockSoundPath(season, true), getSeasonalLockSoundPath(season, false));
                    break;
                default:
                    info = KeyguardEffectViewNone.getLockSoundInfo();
                    break;
            }
        } else {
            switch (this.mCurrentEffect) {
                case EFFECT_RIPPLE:
                case EFFECT_MASS_RIPPLE:
                case EFFECT_MONTBLANC:
                    info = KeyguardEffectViewRippleInk.getLockSoundInfo();
                    break;
                case EFFECT_LIGHT:
                    info = KeyguardEffectViewLensFlare.getLockSoundInfo();
                    break;
                case EFFECT_POPPING_COLOR:
                    info = KeyguardEffectViewParticleSpace.getLockSoundInfo();
                    break;
                case EFFECT_WATERCOLOR:
                    info = KeyguardEffectViewWaterColor.getLockSoundInfo();
                    break;
                case EFFECT_BLIND:
                    info = KeyguardEffectViewBlind.getLockSoundInfo();
                    break;
                case EFFECT_BRILLIANTRING:
                    info = KeyguardEffectViewBrilliantRing.getLockSoundInfo();
                    break;
                case EFFECT_BRILLIANTCUT:
                    info = KeyguardEffectViewBrilliantCut.getLockSoundInfo();
                    break;
                case EFFECT_ABSTRACTTILE:
                    info = KeyguardEffectViewAbstractTile.getLockSoundInfo();
                    break;
                case EFFECT_GEOMETRICMOSAIC:
                    info = KeyguardEffectViewGeometricMosaic.getLockSoundInfo();
                    break;
                case EFFECT_LIQUID:
                    info = KeyguardEffectViewWaterDroplet.getLockSoundInfo();
                    break;
                case EFFECT_PARTICLE:
                    info = KeyguardEffectViewSparklingBubbles.getLockSoundInfo();
                    break;
                case EFFECT_COLOURDROPLET:
                    info = KeyguardEffectViewColourDroplet.getLockSoundInfo();
                    break;
                case EFFECT_SPRING:
                case EFFECT_SUMMER:
                case EFFECT_AUTUMN:
                case EFFECT_WINTER:
                    info = new LockSoundInfo(getSeasonalLockSoundPath(this.mCurrentEffect, true), getSeasonalLockSoundPath(this.mCurrentEffect, false));
                    break;
                default:
                    info = KeyguardEffectViewNone.getLockSoundInfo();
                    break;
            }
        }
        if (this.mLockSoundChangeCallback != null) {
            this.mLockSoundChangeCallback.reloadLockSound(info);
        }
    } */



    @SuppressLint("LongLogTag")
    public boolean isFestivalActivated() {
        Log.d(TAG, "isFestivalActivated()" + this.mFestivalEffectEnabled);
        return this.mFestivalEffectEnabled && this.mFestivalEffect != null && this.mFestivalEffect.isActivated();
    }


    // JADX INFO: Access modifiers changed from: private
    public void handleSetGradationLayer() {
        /* if (this.mBackgroundRootLayout != null) {
            boolean isPreloadedWallpaper = Settings.System.getInt(this.mContext.getContentResolver(), "lockscreen_wallpaper_transparent", 1) == 1;
            Log.d(TAG, "isPreloadedWallpaper=" + isPreloadedWallpaper);
            if (!isPreloadedWallpaper && !KeyguardProperties.isSupportBlendedFilter()) {
                if (this.mStatusBarGradationView == null) {
                    this.mStatusBarGradationView = new View(this.mContext);
                    this.mStatusBarGradationView.setBackgroundResource(R.drawable.gradation_indi_bg);
                    this.mStatusBarGradationView.setLayoutParams(new FrameLayout.LayoutParams(-1, -2));
                }
                if (this.mBackgroundRootLayout.indexOfChild(this.mStatusBarGradationView) == -1) {
                    this.mBackgroundRootLayout.addView(this.mStatusBarGradationView, -1, -2);
                } else {
                    this.mBackgroundRootLayout.bringChildToFront(this.mStatusBarGradationView);
                }
            } else if (this.mStatusBarGradationView != null) {
                this.mBackgroundRootLayout.removeView(this.mStatusBarGradationView);
                this.mStatusBarGradationView = null;
            }
        } */
    }

    private String getEffectName(int effect) {
        String nameOfEffect;
        /*if (isZoomPanningEffectEnabled() && isRichLockWallpaper() && !isJustLockLiveEnabled() && KeyguardEffectViewUtil.getWallpaperType(this.mContext) != 0) {
            return "ZoomPanning";
        }
        boolean isOpenThemeEnabled = this.mWallpaperProcessSeparated && KeyguardProperties.isSupportElasticPlugin() && KeyguardEffectViewUtil.isOpenThemeWallpaperEnabled(this.mContext);
        if (isOpenThemeEnabled) {
            return "OpenTheme";
        }*/
        switch (effect) {
            case EFFECT_HOME:
            case EFFECT_LIVEWALLPAPER:
                nameOfEffect = null;
                break;
            case EFFECT_NONE:
                nameOfEffect = "None";
                break;
            case EFFECT_RIPPLE:
                nameOfEffect = "RippleInk";
                break;
            case EFFECT_LIGHT:
                nameOfEffect = "LensFlare";
                break;
            case EFFECT_POPPING_COLOR:
                nameOfEffect = "ParticleSpace";
                break;
            case EFFECT_WATERCOLOR:
                nameOfEffect = "WaterColor";
                break;
            case EFFECT_BLIND:
                nameOfEffect = "Blind";
                break;
            case EFFECT_MASS_TENSION:
                nameOfEffect = "MassTension";
                break;
            case EFFECT_MASS_RIPPLE:
                nameOfEffect = "MassRipple";
                break;
            case EFFECT_BRILLIANTRING:
                nameOfEffect = "BrilliantRing";
                break;
            case EFFECT_BRILLIANTCUT:
                nameOfEffect = "BrilliantCut";
                break;
            case EFFECT_MONTBLANC:
                nameOfEffect = "IndigoDiffusion";
                break;
            case EFFECT_ABSTRACTTILE:
                nameOfEffect = "AbstractTile";
                break;
            case EFFECT_GEOMETRICMOSAIC:
                nameOfEffect = "GeometricMosaic";
                break;
            case EFFECT_LIQUID:
                nameOfEffect = "WaterDroplet";
                break;
            case EFFECT_PARTICLE:
                nameOfEffect = "SparklingBubbles";
                break;
            case EFFECT_COLOURDROPLET:
                nameOfEffect = "ColourDroplet";
                break;
            case 18: // todo NEW EFFECTS
                nameOfEffect = "Morphing";
                break;
            case 19:
                nameOfEffect = "PoppingColor";
                break;
            case 20:
                nameOfEffect = "RectangleTraveller";
                break;
            case 21:
                nameOfEffect = "BouncingColor";
                break;
            case EFFECT_SPRING:
                nameOfEffect = "Spring";
                break;
            case EFFECT_SUMMER:
                nameOfEffect = "Summer";
                break;
            case EFFECT_AUTUMN:
                nameOfEffect = "Autumn";
                break;
            case EFFECT_WINTER:
                nameOfEffect = "Winter";
                break;
            case EFFECT_SEASONAL /* 85 */:
                nameOfEffect = "Seasonal";
                break;
            case EFFECT_JUST_LOCK_LIVE_WALLPAPER:
                nameOfEffect = "LiveWallpaper";
                break;
            default:
                nameOfEffect = "None";
                break;
        }
        return nameOfEffect;
    }

    private String getEffectClassName(String nameOfEffect) {
        if (nameOfEffect == null || nameOfEffect.isEmpty()) {
            return null;
        }
        if ("LiveWallpaper".equals(nameOfEffect)) {
            return "com.android.keyguard.sec.rich.KeyguardEffectView" + nameOfEffect;
        }
        if ("Spring".equals(nameOfEffect) || "Summer".equals(nameOfEffect) || "Autumn".equals(nameOfEffect) || "Winter".equals(nameOfEffect) || "Seasonal".equals(nameOfEffect)) {
            return this.mFestivalEffect.getFestivalEffectClassName(nameOfEffect);
        }
        // TODO: paths??????????
        return "com.android.keyguard.sec.KeyguardEffectView" + nameOfEffect;
    }

    @SuppressLint("LongLogTag")
    private KeyguardEffectViewBase createEffectInstance(String nameOfClass) {
        if (nameOfClass == null) {
            return null;
        }
        try {
            KeyguardEffectViewBase createdEffect = (KeyguardEffectViewBase) Class.forName(nameOfClass).getConstructor(Context.class).newInstance(this.mContext);
            return createdEffect;
        } catch (ClassNotFoundException e) {
            Log.w(TAG, nameOfClass + " ClassNotFoundException");
            return null;
        } catch (IllegalAccessException e2) {
            Log.w(TAG, nameOfClass + " IllegalAccessException");
            return null;
        } catch (IllegalArgumentException e3) {
            Log.w(TAG, nameOfClass + " IllegalArgumentException");
            return null;
        } catch (InstantiationException e4) {
            Log.w(TAG, nameOfClass + " InstantiationException");
            return null;
        } catch (NoSuchMethodException e5) {
            Log.w(TAG, nameOfClass + " NoSuchMethodException");
            return null;
        } catch (SecurityException e6) {
            Log.w(TAG, nameOfClass + " SecurityException");
            return null;
        } catch (InvocationTargetException e7) {
            Log.w(TAG, nameOfClass + " InvocationTargetException");
            Log.w(TAG, "createEffectInstance: ", e7.getCause());
            return null;
        }
    }

    @SuppressLint("LongLogTag")
    private boolean isBackgroundEffect(String nameOfClass) {
        if (nameOfClass == null) {
            return false;
        }
        try {
            Class<?> tempClass = Class.forName(nameOfClass);
            Method m = tempClass.getDeclaredMethod("isBackgroundEffect", null);
            boolean retValue = ((Boolean) m.invoke(null, null)).booleanValue();
            return retValue;
        } catch (ClassNotFoundException e) {
            Log.w(TAG, nameOfClass + " ClassNotFoundException");
            return false;
        } catch (IllegalAccessException e2) {
            Log.w(TAG, nameOfClass + " IllegalAccessException");
            return false;
        } catch (IllegalArgumentException e3) {
            Log.w(TAG, nameOfClass + " IllegalArgumentException");
            return false;
        } catch (NoSuchMethodException e4) {
            Log.w(TAG, nameOfClass + " NoSuchMethodException");
            return false;
        } catch (SecurityException e5) {
            Log.w(TAG, nameOfClass + " SecurityException");
            return false;
        } catch (InvocationTargetException e6) {
            Log.w(TAG, nameOfClass + " InvocationTargetException");
            return false;
        }
    }

    @SuppressLint("LongLogTag")
    private String getCounterEffectName(String nameOfClass) {
        if (nameOfClass == null) {
            return null;
        }
        try {
            Class<?> tempClass = Class.forName(nameOfClass);
            Method m = tempClass.getDeclaredMethod("getCounterEffectName", null);
            String className = (String) m.invoke(null, null);
            return className;
        } catch (ClassNotFoundException e) {
            Log.w(TAG, nameOfClass + " ClassNotFoundException");
            return null;
        } catch (IllegalAccessException e2) {
            Log.w(TAG, nameOfClass + " IllegalAccessException");
            return null;
        } catch (IllegalArgumentException e3) {
            Log.w(TAG, nameOfClass + " IllegalArgumentException");
            return null;
        } catch (NoSuchMethodException e4) {
            Log.w(TAG, nameOfClass + " NoSuchMethodException");
            return null;
        } catch (SecurityException e5) {
            Log.w(TAG, nameOfClass + " SecurityException");
            return null;
        } catch (InvocationTargetException e6) {
            Log.w(TAG, nameOfClass + " InvocationTargetException");
            return null;
        }
    }

    public KeyguardEffectViewBase getUnlockEffect() {
        return mUnlockEffectView;
    }

    @SuppressLint("LongLogTag")
    private void makeEffectViews(int effect) {
        KeyguardEffectViewBase primaryEffect;
        boolean isPrimaryBackground;
        String tempClassName;
        String nameOfClass = getEffectClassName(getEffectName(effect));
        if ((nameOfClass != null && !nameOfClass.equals(this.mOldPrimaryEffect)) || (nameOfClass == null && this.mOldPrimaryEffect != null)) {
            //this.mWallpaperProcessSeparated) {
            this.mOldPrimaryEffect = nameOfClass;
            if (nameOfClass == null || "LiveWallpaper".equals(getEffectName(effect)) || "None".equals(getEffectName(effect))) {
                this.mBackgroundView = null;
                setBackground();
                this.mForegroundView = null;
                setForeground();
                this.mUnlockEffectView = this.mForegroundCircleView;
                return;
            }
                /*if (isBackgroundEffect(nameOfClass)) {
                    isPrimaryBackground = true;
                    tempClassName = getEffectClassName(getCounterEffectName(nameOfClass));
                } else {
                    isPrimaryBackground = false;
                    tempClassName = nameOfClass;
                }
                KeyguardEffectViewBase primaryEffect2 = createEffectInstance(tempClassName);
                Log.d(TAG, "makeEffectViews: instance done");
                this.mForegroundView = primaryEffect2;
                if (isPrimaryBackground) {
                    this.mUnlockEffectView = null;
                } else if (this.mForegroundView != null) {
                    this.mUnlockEffectView = this.mForegroundView;
                } else {
                    this.mUnlockEffectView = this.mForegroundCircleView;
                }
                Log.d(TAG, "mUnlockEffectView = " + this.mUnlockEffectView);
                setForeground();
                //return;*/
            primaryEffect = createEffectInstance(nameOfClass);
            if (primaryEffect == null) {
                this.mOldPrimaryEffect = null;
                this.mBackgroundView = null;
                setBackground();
                this.mForegroundView = null;
                setForeground();
                this.mUnlockEffectView = this.mForegroundCircleView;
                return;
            }
            if (isBackgroundEffect(nameOfClass)) {
                this.mBackgroundView = primaryEffect;
                this.mOldPrimaryEffect = this.mBackgroundView.getClass().getName().toString();
                if (!isJustLockLiveEnabled()) {
                    this.mUnlockEffectView = this.mBackgroundView;
                } else {
                    this.mUnlockEffectView = this.mForegroundCircleView;
                }
                Log.d(TAG, "Sets backgound view first");
            } else {
                if (primaryEffect != this.mForegroundCircleView) {
                    this.mForegroundView = primaryEffect;
                }
                this.mOldPrimaryEffect = primaryEffect.getClass().getName().toString();
                this.mUnlockEffectView = primaryEffect;
                Log.d(TAG, "Sets foreground view first");
            }
            KeyguardEffectViewBase secondaryEffect = createEffectInstance(getEffectClassName(getCounterEffectName(nameOfClass)));
            if (this.mBackgroundView == primaryEffect) {
                this.mForegroundView = secondaryEffect;
                Log.d(TAG, "Sets foreground view later");
            } else {
                this.mBackgroundView = secondaryEffect;
                Log.d(TAG, "Sets background view later");
            }
            if (this.mBackgroundView != null) {
                Log.d(TAG, "mBackgroundView is not null");
                setBackground();
                this.mBackgroundView.update();
            } else setForeground(); // TODO set when background is null
            this.mUserSwitching = false;
        } else if (this.mBackgroundView != null && !true) {//this.mWallpaperProcessSeparated) {
            this.mBackgroundView.update();
        }
    }

}