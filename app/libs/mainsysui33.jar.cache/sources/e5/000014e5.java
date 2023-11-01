package com.android.systemui.dagger;

import android.app.Activity;
import android.app.Service;
import android.content.BroadcastReceiver;
import com.android.systemui.recents.RecentsImplementation;
import java.util.Map;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/dagger/ContextComponentResolver.class */
public class ContextComponentResolver implements ContextComponentHelper {
    public final Map<Class<?>, Provider<Activity>> mActivityCreators;
    public final Map<Class<?>, Provider<BroadcastReceiver>> mBroadcastReceiverCreators;
    public final Map<Class<?>, Provider<RecentsImplementation>> mRecentsCreators;
    public final Map<Class<?>, Provider<Service>> mServiceCreators;

    public ContextComponentResolver(Map<Class<?>, Provider<Activity>> map, Map<Class<?>, Provider<Service>> map2, Map<Class<?>, Provider<RecentsImplementation>> map3, Map<Class<?>, Provider<BroadcastReceiver>> map4) {
        this.mActivityCreators = map;
        this.mServiceCreators = map2;
        this.mRecentsCreators = map3;
        this.mBroadcastReceiverCreators = map4;
    }

    /* JADX DEBUG: Multi-variable search result rejected for r0v8, resolved type: java.lang.Object */
    /* JADX WARN: Multi-variable type inference failed */
    public final <T> T resolve(String str, Map<Class<?>, Provider<T>> map) {
        T t;
        try {
            Provider<T> provider = map.get(Class.forName(str));
            t = provider == null ? null : provider.get();
        } catch (ClassNotFoundException e) {
            t = null;
        }
        return t;
    }

    @Override // com.android.systemui.dagger.ContextComponentHelper
    public Activity resolveActivity(String str) {
        return (Activity) resolve(str, this.mActivityCreators);
    }

    @Override // com.android.systemui.dagger.ContextComponentHelper
    public BroadcastReceiver resolveBroadcastReceiver(String str) {
        return (BroadcastReceiver) resolve(str, this.mBroadcastReceiverCreators);
    }

    @Override // com.android.systemui.dagger.ContextComponentHelper
    public RecentsImplementation resolveRecents(String str) {
        return (RecentsImplementation) resolve(str, this.mRecentsCreators);
    }

    @Override // com.android.systemui.dagger.ContextComponentHelper
    public Service resolveService(String str) {
        return (Service) resolve(str, this.mServiceCreators);
    }
}