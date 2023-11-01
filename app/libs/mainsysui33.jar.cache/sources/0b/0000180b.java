package com.android.systemui.fragments;

import android.app.Fragment;
import android.content.res.Configuration;
import android.os.Handler;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import com.android.systemui.Dumpable;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.fragments.FragmentService;
import com.android.systemui.qs.QSFragment;
import com.android.systemui.statusbar.policy.ConfigurationController;
import java.io.PrintWriter;
import java.lang.reflect.Method;

/* loaded from: mainsysui33.jar:com/android/systemui/fragments/FragmentService.class */
public class FragmentService implements Dumpable {
    public final ArrayMap<View, FragmentHostState> mHosts = new ArrayMap<>();
    public final ArrayMap<String, FragmentInstantiationInfo> mInjectionMap = new ArrayMap<>();
    public final Handler mHandler = new Handler();
    public ConfigurationController.ConfigurationListener mConfigurationListener = new ConfigurationController.ConfigurationListener() { // from class: com.android.systemui.fragments.FragmentService.1
        {
            FragmentService.this = this;
        }

        public void onConfigChanged(Configuration configuration) {
            for (FragmentHostState fragmentHostState : FragmentService.this.mHosts.values()) {
                fragmentHostState.sendConfigurationChange(configuration);
            }
        }
    };

    /* loaded from: mainsysui33.jar:com/android/systemui/fragments/FragmentService$FragmentCreator.class */
    public interface FragmentCreator {

        /* loaded from: mainsysui33.jar:com/android/systemui/fragments/FragmentService$FragmentCreator$Factory.class */
        public interface Factory {
            FragmentCreator build();
        }

        QSFragment createQSFragment();
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/fragments/FragmentService$FragmentHostState.class */
    public class FragmentHostState {
        public FragmentHostManager mFragmentHostManager;
        public final View mView;

        /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.fragments.FragmentService$FragmentHostState$$ExternalSyntheticLambda0.run():void] */
        /* renamed from: $r8$lambda$GTODP_uYHSkUpHCZ-v1oU22BpWU */
        public static /* synthetic */ void m2724$r8$lambda$GTODP_uYHSkUpHCZv1oU22BpWU(FragmentHostState fragmentHostState, Configuration configuration) {
            fragmentHostState.lambda$sendConfigurationChange$0(configuration);
        }

        public FragmentHostState(View view) {
            FragmentService.this = r7;
            this.mView = view;
            this.mFragmentHostManager = new FragmentHostManager(r7, view);
        }

        public FragmentHostManager getFragmentHostManager() {
            return this.mFragmentHostManager;
        }

        /* renamed from: handleSendConfigurationChange */
        public final void lambda$sendConfigurationChange$0(Configuration configuration) {
            this.mFragmentHostManager.onConfigurationChanged(configuration);
        }

        public void sendConfigurationChange(final Configuration configuration) {
            FragmentService.this.mHandler.post(new Runnable() { // from class: com.android.systemui.fragments.FragmentService$FragmentHostState$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    FragmentService.FragmentHostState.m2724$r8$lambda$GTODP_uYHSkUpHCZv1oU22BpWU(FragmentService.FragmentHostState.this, configuration);
                }
            });
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/fragments/FragmentService$FragmentInstantiationInfo.class */
    public static class FragmentInstantiationInfo {
        public final Object mDaggerComponent;
        public final Method mMethod;

        public FragmentInstantiationInfo(Method method, Object obj) {
            this.mMethod = method;
            this.mDaggerComponent = obj;
        }
    }

    public FragmentService(FragmentCreator.Factory factory, ConfigurationController configurationController, DumpManager dumpManager) {
        addFragmentInstantiationProvider(factory.build());
        configurationController.addCallback(this.mConfigurationListener);
        dumpManager.registerDumpable(getClass().getSimpleName(), this);
    }

    public void addFragmentInstantiationProvider(Object obj) {
        Method[] declaredMethods;
        for (Method method : obj.getClass().getDeclaredMethods()) {
            if (Fragment.class.isAssignableFrom(method.getReturnType()) && (method.getModifiers() & 1) != 0) {
                String name = method.getReturnType().getName();
                if (this.mInjectionMap.containsKey(name)) {
                    Log.w("FragmentService", "Fragment " + name + " is already provided by different Dagger component; Not adding method");
                } else {
                    this.mInjectionMap.put(name, new FragmentInstantiationInfo(method, obj));
                }
            }
        }
    }

    public void destroyAll() {
        for (FragmentHostState fragmentHostState : this.mHosts.values()) {
            fragmentHostState.mFragmentHostManager.destroy();
        }
    }

    @Override // com.android.systemui.Dumpable
    public void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println("Dumping fragments:");
        for (FragmentHostState fragmentHostState : this.mHosts.values()) {
            fragmentHostState.mFragmentHostManager.getFragmentManager().dump("  ", null, printWriter, strArr);
        }
    }

    public FragmentHostManager getFragmentHostManager(View view) {
        View rootView = view.getRootView();
        FragmentHostState fragmentHostState = this.mHosts.get(rootView);
        FragmentHostState fragmentHostState2 = fragmentHostState;
        if (fragmentHostState == null) {
            fragmentHostState2 = new FragmentHostState(rootView);
            this.mHosts.put(rootView, fragmentHostState2);
        }
        return fragmentHostState2.getFragmentHostManager();
    }

    public ArrayMap<String, FragmentInstantiationInfo> getInjectionMap() {
        return this.mInjectionMap;
    }
}