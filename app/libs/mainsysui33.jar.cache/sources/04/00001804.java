package com.android.systemui.fragments;

import android.app.Fragment;
import android.app.FragmentController;
import android.app.FragmentHostCallback;
import android.app.FragmentManager;
import android.app.FragmentManagerNonConfig;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import android.os.Trace;
import android.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import com.android.settingslib.applications.InterestingConfigChanges;
import com.android.systemui.Dependency;
import com.android.systemui.fragments.FragmentHostManager;
import com.android.systemui.fragments.FragmentService;
import com.android.systemui.plugins.Plugin;
import com.android.systemui.util.leak.LeakDetector;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Consumer;

/* loaded from: mainsysui33.jar:com/android/systemui/fragments/FragmentHostManager.class */
public class FragmentHostManager {
    public final InterestingConfigChanges mConfigChanges;
    public final Context mContext;
    public FragmentController mFragments;
    public FragmentManager.FragmentLifecycleCallbacks mLifecycleCallbacks;
    public final FragmentService mManager;
    public final ExtensionFragmentManager mPlugins;
    public final View mRootView;
    public final Handler mHandler = new Handler(Looper.getMainLooper());
    public final HashMap<String, ArrayList<FragmentListener>> mListeners = new HashMap<>();

    /* loaded from: mainsysui33.jar:com/android/systemui/fragments/FragmentHostManager$ExtensionFragmentManager.class */
    public class ExtensionFragmentManager {
        public final ArrayMap<String, Context> mExtensionLookup = new ArrayMap<>();

        public ExtensionFragmentManager() {
            FragmentHostManager.this = r5;
        }

        public Fragment instantiate(Context context, String str, Bundle bundle) {
            Context context2 = this.mExtensionLookup.get(str);
            if (context2 != null) {
                Fragment instantiateWithInjections = instantiateWithInjections(context2, str, bundle);
                if (instantiateWithInjections instanceof Plugin) {
                    ((Plugin) instantiateWithInjections).onCreate(FragmentHostManager.this.mContext, context2);
                }
                return instantiateWithInjections;
            }
            return instantiateWithInjections(context, str, bundle);
        }

        public final Fragment instantiateWithInjections(Context context, String str, Bundle bundle) {
            FragmentService.FragmentInstantiationInfo fragmentInstantiationInfo = FragmentHostManager.this.mManager.getInjectionMap().get(str);
            if (fragmentInstantiationInfo != null) {
                try {
                    Fragment fragment = (Fragment) fragmentInstantiationInfo.mMethod.invoke(fragmentInstantiationInfo.mDaggerComponent, new Object[0]);
                    if (bundle != null) {
                        bundle.setClassLoader(fragment.getClass().getClassLoader());
                        fragment.setArguments(bundle);
                    }
                    return fragment;
                } catch (IllegalAccessException | InvocationTargetException e) {
                    throw new Fragment.InstantiationException("Unable to instantiate " + str, e);
                }
            }
            return Fragment.instantiate(context, str, bundle);
        }

        public void setCurrentExtension(int i, String str, String str2, String str3, Context context) {
            if (str2 != null) {
                this.mExtensionLookup.remove(str2);
            }
            this.mExtensionLookup.put(str3, context);
            FragmentHostManager.this.getFragmentManager().beginTransaction().replace(i, instantiate(context, str3, null), str).commit();
            FragmentHostManager.this.reloadFragments();
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/fragments/FragmentHostManager$FragmentListener.class */
    public interface FragmentListener {
        void onFragmentViewCreated(String str, Fragment fragment);

        default void onFragmentViewDestroyed(String str, Fragment fragment) {
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/fragments/FragmentHostManager$HostCallbacks.class */
    public class HostCallbacks extends FragmentHostCallback<FragmentHostManager> {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public HostCallbacks() {
            super(r6.mContext, r6.mHandler, 0);
            FragmentHostManager.this = r6;
        }

        public Fragment instantiate(Context context, String str, Bundle bundle) {
            return FragmentHostManager.this.mPlugins.instantiate(context, str, bundle);
        }

        @Override // android.app.FragmentHostCallback
        public void onAttachFragment(Fragment fragment) {
        }

        @Override // android.app.FragmentHostCallback
        public void onDump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
            FragmentHostManager.this.dump(str, fileDescriptor, printWriter, strArr);
        }

        @Override // android.app.FragmentHostCallback, android.app.FragmentContainer
        public <T extends View> T onFindViewById(int i) {
            return (T) FragmentHostManager.this.findViewById(i);
        }

        @Override // android.app.FragmentHostCallback
        public FragmentHostManager onGetHost() {
            return FragmentHostManager.this;
        }

        @Override // android.app.FragmentHostCallback
        public LayoutInflater onGetLayoutInflater() {
            return LayoutInflater.from(FragmentHostManager.this.mContext);
        }

        @Override // android.app.FragmentHostCallback
        public int onGetWindowAnimations() {
            return 0;
        }

        @Override // android.app.FragmentHostCallback, android.app.FragmentContainer
        public boolean onHasView() {
            return true;
        }

        @Override // android.app.FragmentHostCallback
        public boolean onHasWindowAnimations() {
            return false;
        }

        @Override // android.app.FragmentHostCallback
        public boolean onShouldSaveFragmentState(Fragment fragment) {
            return true;
        }

        @Override // android.app.FragmentHostCallback
        public boolean onUseFragmentManagerInflaterFactory() {
            return true;
        }
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.fragments.FragmentHostManager$$ExternalSyntheticLambda1.accept(java.lang.Object):void] */
    public static /* synthetic */ void $r8$lambda$QlJMKD9uPd8hZrV2scpLIysb7yc(String str, Fragment fragment, FragmentListener fragmentListener) {
        fragmentListener.onFragmentViewCreated(str, fragment);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.fragments.FragmentHostManager$$ExternalSyntheticLambda0.accept(java.lang.Object):void] */
    public static /* synthetic */ void $r8$lambda$jqcTN9upx2bwJ3BAaMHXjltVmKA(String str, Fragment fragment, FragmentListener fragmentListener) {
        fragmentListener.onFragmentViewDestroyed(str, fragment);
    }

    public FragmentHostManager(FragmentService fragmentService, View view) {
        InterestingConfigChanges interestingConfigChanges = new InterestingConfigChanges(-1073741820);
        this.mConfigChanges = interestingConfigChanges;
        this.mPlugins = new ExtensionFragmentManager();
        Context context = view.getContext();
        this.mContext = context;
        this.mManager = fragmentService;
        this.mRootView = view;
        interestingConfigChanges.applyNewConfig(context.getResources());
        createFragmentHost(null);
    }

    public static FragmentHostManager get(View view) {
        try {
            return ((FragmentService) Dependency.get(FragmentService.class)).getFragmentHostManager(view);
        } catch (ClassCastException e) {
            throw e;
        }
    }

    public FragmentHostManager addTagListener(String str, FragmentListener fragmentListener) {
        ArrayList<FragmentListener> arrayList = this.mListeners.get(str);
        ArrayList<FragmentListener> arrayList2 = arrayList;
        if (arrayList == null) {
            arrayList2 = new ArrayList<>();
            this.mListeners.put(str, arrayList2);
        }
        arrayList2.add(fragmentListener);
        Fragment findFragmentByTag = getFragmentManager().findFragmentByTag(str);
        if (findFragmentByTag != null && findFragmentByTag.getView() != null) {
            fragmentListener.onFragmentViewCreated(str, findFragmentByTag);
        }
        return this;
    }

    public <T> T create(Class<T> cls) {
        return (T) this.mPlugins.instantiate(this.mContext, cls.getName(), null);
    }

    public final void createFragmentHost(Parcelable parcelable) {
        FragmentController createController = FragmentController.createController(new HostCallbacks());
        this.mFragments = createController;
        createController.attachHost(null);
        this.mLifecycleCallbacks = new FragmentManager.FragmentLifecycleCallbacks() { // from class: com.android.systemui.fragments.FragmentHostManager.1
            {
                FragmentHostManager.this = this;
            }

            @Override // android.app.FragmentManager.FragmentLifecycleCallbacks
            public void onFragmentDestroyed(FragmentManager fragmentManager, Fragment fragment) {
                ((LeakDetector) Dependency.get(LeakDetector.class)).trackGarbage(fragment);
            }

            @Override // android.app.FragmentManager.FragmentLifecycleCallbacks
            public void onFragmentViewCreated(FragmentManager fragmentManager, Fragment fragment, View view, Bundle bundle) {
                FragmentHostManager.this.onFragmentViewCreated(fragment);
            }

            @Override // android.app.FragmentManager.FragmentLifecycleCallbacks
            public void onFragmentViewDestroyed(FragmentManager fragmentManager, Fragment fragment) {
                FragmentHostManager.this.onFragmentViewDestroyed(fragment);
            }
        };
        this.mFragments.getFragmentManager().registerFragmentLifecycleCallbacks(this.mLifecycleCallbacks, true);
        if (parcelable != null) {
            this.mFragments.restoreAllState(parcelable, (FragmentManagerNonConfig) null);
        }
        this.mFragments.dispatchCreate();
        this.mFragments.dispatchStart();
        this.mFragments.dispatchResume();
    }

    public void destroy() {
        this.mFragments.dispatchDestroy();
    }

    public final Parcelable destroyFragmentHost() {
        this.mFragments.dispatchPause();
        Parcelable saveAllState = this.mFragments.saveAllState();
        this.mFragments.dispatchStop();
        this.mFragments.dispatchDestroy();
        this.mFragments.getFragmentManager().unregisterFragmentLifecycleCallbacks(this.mLifecycleCallbacks);
        return saveAllState;
    }

    public final void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
    }

    public final <T extends View> T findViewById(int i) {
        return (T) this.mRootView.findViewById(i);
    }

    public ExtensionFragmentManager getExtensionManager() {
        return this.mPlugins;
    }

    public FragmentManager getFragmentManager() {
        return this.mFragments.getFragmentManager();
    }

    public void onConfigurationChanged(Configuration configuration) {
        if (this.mConfigChanges.applyNewConfig(this.mContext.getResources())) {
            reloadFragments();
        } else {
            this.mFragments.dispatchConfigurationChanged(configuration);
        }
    }

    public final void onFragmentViewCreated(final Fragment fragment) {
        final String tag = fragment.getTag();
        ArrayList<FragmentListener> arrayList = this.mListeners.get(tag);
        if (arrayList != null) {
            arrayList.forEach(new Consumer() { // from class: com.android.systemui.fragments.FragmentHostManager$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    FragmentHostManager.$r8$lambda$QlJMKD9uPd8hZrV2scpLIysb7yc(tag, fragment, (FragmentHostManager.FragmentListener) obj);
                }
            });
        }
    }

    public final void onFragmentViewDestroyed(final Fragment fragment) {
        final String tag = fragment.getTag();
        ArrayList<FragmentListener> arrayList = this.mListeners.get(tag);
        if (arrayList != null) {
            arrayList.forEach(new Consumer() { // from class: com.android.systemui.fragments.FragmentHostManager$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    FragmentHostManager.$r8$lambda$jqcTN9upx2bwJ3BAaMHXjltVmKA(tag, fragment, (FragmentHostManager.FragmentListener) obj);
                }
            });
        }
    }

    public void reloadFragments() {
        Trace.beginSection("FrargmentHostManager#reloadFragments");
        createFragmentHost(destroyFragmentHost());
        Trace.endSection();
    }

    public void removeTagListener(String str, FragmentListener fragmentListener) {
        ArrayList<FragmentListener> arrayList = this.mListeners.get(str);
        if (arrayList != null && arrayList.remove(fragmentListener) && arrayList.size() == 0) {
            this.mListeners.remove(str);
        }
    }
}