package com.aj.effect;

import static de.robv.android.xposed.XposedBridge.log;
import static de.robv.android.xposed.XposedHelpers.callMethod;
import static de.robv.android.xposed.XposedHelpers.callStaticMethod;
import static de.robv.android.xposed.XposedHelpers.findAndHookConstructor;
import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;
import static de.robv.android.xposed.XposedHelpers.findClass;
import static de.robv.android.xposed.XposedHelpers.getObjectField;
import static de.robv.android.xposed.XposedHelpers.newInstance;

import android.content.Context;
import android.net.Uri;
import android.provider.Settings;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.android.keyguard.sec.KeyguardEffectViewController;
import com.android.keyguard.util.SettingsHelper;

import de.robv.android.xposed.IXposedHookInitPackageResources;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.callbacks.XC_InitPackageResources;
import de.robv.android.xposed.callbacks.XC_LayoutInflated;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class Hook implements IXposedHookLoadPackage, IXposedHookInitPackageResources {

    String packageName = "com.android.systemui";
    final FrameLayout[] behind = new FrameLayout[1];
    final FrameLayout[] front = new FrameLayout[1];
    @Override
    public void handleInitPackageResources(XC_InitPackageResources.InitPackageResourcesParam resparam) throws Throwable {
        long version;
        try {
            version = Long.parseLong(Utils.getSecUiVersion());
        } catch (NumberFormatException e) {
            log("Sorry, this pre-alpha works only on Samsung Experience 8.0");
            return;
        }
        if (version != 8.0 && !resparam.packageName.equals(packageName))
            return;

        XC_LayoutInflated handleStatusBar = new XC_LayoutInflated() {
            @Override
            public void handleLayoutInflated(LayoutInflatedParam liparam) throws Throwable {
                FrameLayout hideGroup1 = liparam.view.findViewById(liparam.res.getIdentifier("hided_by_cover_group1", "id", packageName));
                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                behind[0] = new FrameLayout(hideGroup1.getContext());
                behind[0].setLayoutParams(params);
                hideGroup1.addView(behind[0], 1);

                FrameLayout hideGroup2 = liparam.view.findViewById(liparam.res.getIdentifier("hided_by_cover_group2", "id", packageName));
                front[0] = new FrameLayout(hideGroup2.getContext());
                front[0].setLayoutParams(params);
                hideGroup2.addView(front[0], 0);

                /* leave for future
                FrameLayout backdrop = (FrameLayout) liparam.view.findViewById(
                        liparam.res.getIdentifier("backdrop", "id", "com.android.systemui"));

                KeyguardUnlockView unlockView = new KeyguardUnlockView(backdrop.getContext());
                unlockView.setLayoutParams(params);
                backdrop.addView(behind);
                backdrop.addView(front);
                backdrop.addView(unlockView);
                unlockView.onFinishInflate();
                KeyguardEffectViewController.getInstance(backdrop.getContext()).setEffectLayout(behind, front, null);*/
            }
        };

        resparam.res.hookLayout("com.android.systemui", "layout", "status_bar_window", handleStatusBar);
    }

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {
        long version;
        try {
            version = Long.parseLong(Utils.getSecUiVersion());
        } catch (NumberFormatException e) {
            log("Sorry, this pre-alpha works only on Samsung Experience 8.0");
            return;
        }
        if (version == 8.0 && loadPackageParam.packageName.equals("com.android.systemui")) {
            log("handleLoadPackage: in there, hooking SettingsHelper");

            String keyguard = "com.android.keyguard";
            String helper = keyguard + ".util.SettingsHelper";

            Class<?> helperClass = findClass(helper, loadPackageParam.classLoader);

            // Add listener to effect setting
            findAndHookMethod(helperClass,
                    "setUpSettingsItem", new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    super.afterHookedMethod(param);
                    callMethod(getObjectField(param.thisObject, "mItemLists"), "add", newInstance(findClass(helper + ".Item", loadPackageParam.classLoader),
                            "System", "lockscreen_ripple_effect", "Int", Utils.defaultUnlock, true));
                }
            });

            Class<?> KeyguardImageWallpaper = findClass(keyguard + ".wallpaper.KeyguardImageWallpaper", loadPackageParam.classLoader);
            final Object[] context = new Object[1];

            SettingsHelper.OnChangedCallback effectCallback = new SettingsHelper.OnChangedCallback() {
                @Override
                public void onChanged(Uri uri) {
                    if (!uri.equals(Settings.System.getUriFor("lockscreen_ripple_effect"))) {
                        return;
                    }
                    Log.d("KeyguardImageView", "hooked: changing effect");
                    KeyguardEffectViewController controller = KeyguardEffectViewController.getInstance((Context) context[0]);
                    controller.handleWallpaperTypeChanged();
                }
            };


            findAndHookConstructor(KeyguardImageWallpaper, new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    super.afterHookedMethod(param);
                    context[0] = getObjectField(param.thisObject, "mContext");
                }
            });
            findAndHookMethod(KeyguardImageWallpaper, "onAttachedToWindow", new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    super.afterHookedMethod(param);
                    Object settingCtx = callStaticMethod(helperClass, "getInstance", context[0]);
                    callMethod(settingCtx, "registerCallback", effectCallback, Settings.System.getUriFor("lockscreen_ripple_effect"));
                }
            });
            findAndHookMethod(KeyguardImageWallpaper, "onDetachedFromWindow", new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    super.afterHookedMethod(param);
                    Object settingCtx = callStaticMethod(helperClass, "getInstance", context[0]);
                    callMethod(settingCtx, "unregisterCallback", effectCallback);
                }
            });
            // findAndHookMethod(KeyguardImageWallpaper, )
            // todo basic functions hook to systemuiwallpaper


            findAndHookMethod(packageName + ".statusbar.phone.PhoneStatusBar", loadPackageParam.classLoader, "makeStatusBarView", new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    KeyguardEffectViewController.getInstance((Context) param.thisObject).
                            setEffectLayout(behind[0], front[0], null);
                    super.beforeHookedMethod(param);
                }
            });

            /* leave it too
            findAndHookMethod("com.android.systemui.statusbar.NotificationMediaManager",
                    loadPackageParam.classLoader, "finishUpdateMediaMetaData",
                    boolean.class, boolean.class, Bitmap.class, new XC_MethodHook() {
                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            super.afterHookedMethod(param);
                            View backDrop = (View) XposedHelpers.getObjectField(param.thisObject, "mBackdrop");
                            ImageView backDropBack = (ImageView) XposedHelpers.getObjectField(
                                    param.thisObject, "mBackdropBack");
                            if (backDrop == null || backDropBack == null) {
                                if (DEBUG) log("updateMediaMetaData: called too early");
                                return;
                            }

                            boolean hasMediaArtwork = param.args[2] != null;
                            if (DEBUG) log("finishUpdateMediaMetaData: hasMediaArtwork=" + hasMediaArtwork);

                            Object stateCtrl = XposedHelpers.getObjectField(param.thisObject, "mStatusBarStateController");
                            int state = (int) XposedHelpers.callMethod(stateCtrl, "getState");
                            if (!hasMediaArtwork && state != 0) {
                                backDrop.animate().cancel();
                                backDropBack.animate().cancel();
                                backDrop.setVisibility(View.VISIBLE);
                                backDrop.animate().alpha(1f);
                                if (DEBUG)
                                    log("finishUpdateMediaMetaData: showing custom background");
                            }
                            if (hasMediaArtwork && state != 0) {
                                backDrop.animate().cancel();
                                backDropBack.animate().cancel();
                                backDrop.setVisibility(View.VISIBLE);
                                backDrop.animate().alpha(1f);
                            }
                        }
                    });*/
        }
    }
}