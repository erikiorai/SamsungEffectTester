/*package com.aj.effect;

import static com.aj.effect.BuildConfig.DEBUG;
import static de.robv.android.xposed.XposedBridge.log;
import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;

import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.android.keyguard.sec.KeyguardEffectViewController;
import com.android.keyguard.sec.KeyguardUnlockView;

import de.robv.android.xposed.IXposedHookInitPackageResources;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_InitPackageResources;
import de.robv.android.xposed.callbacks.XC_LayoutInflated;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class Hook implements IXposedHookLoadPackage, IXposedHookInitPackageResources {

    @Override
    public void handleInitPackageResources(XC_InitPackageResources.InitPackageResourcesParam resparam) throws Throwable {
        if (!resparam.packageName.equals("com.android.systemui"))
            return;

        resparam.res.hookLayout("com.android.systemui", "layout", "super_notification_shade", new XC_LayoutInflated() {
            @Override
            public void handleLayoutInflated(LayoutInflatedParam liparam) throws Throwable {
                FrameLayout backdrop = (FrameLayout) liparam.view.findViewById(
                        liparam.res.getIdentifier("backdrop", "id", "com.android.systemui"));
                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                FrameLayout behind = new FrameLayout(backdrop.getContext());
                behind.setLayoutParams(params);
                FrameLayout front = new FrameLayout(backdrop.getContext());
                front.setLayoutParams(params);
                KeyguardUnlockView unlockView = new KeyguardUnlockView(backdrop.getContext());
                unlockView.setLayoutParams(params);
                backdrop.addView(behind);
                backdrop.addView(front);
                backdrop.addView(unlockView);
                unlockView.onFinishInflate();
                KeyguardEffectViewController.getInstance(backdrop.getContext()).setEffectLayout(behind, front, null);
            }
        });
    }

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {
        if (loadPackageParam.packageName.equals("com.android.systemui")) {
            log("handleLoadPackage: in there");

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
                    });
        }
    }
}*/
