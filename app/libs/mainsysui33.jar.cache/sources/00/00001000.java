package com.android.systemui;

import android.app.Activity;
import android.app.Application;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ContentProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import androidx.core.app.AppComponentFactory;
import com.android.systemui.dagger.ContextComponentHelper;
import com.android.systemui.dagger.SysUIComponent;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ExecutionException;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: mainsysui33.jar:com/android/systemui/SystemUIAppComponentFactoryBase.class */
public abstract class SystemUIAppComponentFactoryBase extends AppComponentFactory {
    public static final Companion Companion = new Companion(null);
    public static SystemUIInitializer systemUIInitializer;
    public ContextComponentHelper componentHelper;

    /* loaded from: mainsysui33.jar:com/android/systemui/SystemUIAppComponentFactoryBase$Companion.class */
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/SystemUIAppComponentFactoryBase$ContextAvailableCallback.class */
    public interface ContextAvailableCallback {
        SystemUIInitializer onContextAvailable(Context context);
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/SystemUIAppComponentFactoryBase$ContextInitializer.class */
    public interface ContextInitializer {
        void setContextAvailableCallback(ContextAvailableCallback contextAvailableCallback);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.SystemUIAppComponentFactoryBase$instantiateApplicationCompat$1.onContextAvailable(android.content.Context):com.android.systemui.SystemUIInitializer] */
    public static final /* synthetic */ SystemUIInitializer access$createSystemUIInitializerInternal(SystemUIAppComponentFactoryBase systemUIAppComponentFactoryBase, Context context) {
        return systemUIAppComponentFactoryBase.createSystemUIInitializerInternal(context);
    }

    public abstract SystemUIInitializer createSystemUIInitializer(Context context);

    public final SystemUIInitializer createSystemUIInitializerInternal(Context context) {
        SystemUIInitializer systemUIInitializer2 = systemUIInitializer;
        if (systemUIInitializer2 == null) {
            SystemUIInitializer createSystemUIInitializer = createSystemUIInitializer(context.getApplicationContext());
            try {
                createSystemUIInitializer.init(false);
                createSystemUIInitializer.getSysUIComponent().inject(this);
                systemUIInitializer = createSystemUIInitializer;
                return createSystemUIInitializer;
            } catch (InterruptedException e) {
                throw new RuntimeException("Failed to initialize SysUI", e);
            } catch (ExecutionException e2) {
                throw new RuntimeException("Failed to initialize SysUI", e2);
            }
        }
        return systemUIInitializer2;
    }

    public final ContextComponentHelper getComponentHelper() {
        ContextComponentHelper contextComponentHelper = this.componentHelper;
        if (contextComponentHelper != null) {
            return contextComponentHelper;
        }
        return null;
    }

    @Override // androidx.core.app.AppComponentFactory
    public Activity instantiateActivityCompat(ClassLoader classLoader, String str, Intent intent) {
        SystemUIInitializer systemUIInitializer2;
        SysUIComponent sysUIComponent;
        if (this.componentHelper == null && (systemUIInitializer2 = systemUIInitializer) != null && (sysUIComponent = systemUIInitializer2.getSysUIComponent()) != null) {
            sysUIComponent.inject(this);
        }
        Activity resolveActivity = getComponentHelper().resolveActivity(str);
        Activity activity = resolveActivity;
        if (resolveActivity == null) {
            activity = super.instantiateActivityCompat(classLoader, str, intent);
        }
        return activity;
    }

    @Override // androidx.core.app.AppComponentFactory
    public Application instantiateApplicationCompat(ClassLoader classLoader, String str) {
        Application instantiateApplicationCompat = super.instantiateApplicationCompat(classLoader, str);
        if (instantiateApplicationCompat instanceof ContextInitializer) {
            ((ContextInitializer) instantiateApplicationCompat).setContextAvailableCallback(new ContextAvailableCallback() { // from class: com.android.systemui.SystemUIAppComponentFactoryBase$instantiateApplicationCompat$1
                @Override // com.android.systemui.SystemUIAppComponentFactoryBase.ContextAvailableCallback
                public final SystemUIInitializer onContextAvailable(Context context) {
                    return SystemUIAppComponentFactoryBase.access$createSystemUIInitializerInternal(SystemUIAppComponentFactoryBase.this, context);
                }
            });
            return instantiateApplicationCompat;
        }
        throw new RuntimeException("App must implement ContextInitializer");
    }

    @Override // androidx.core.app.AppComponentFactory
    public ContentProvider instantiateProviderCompat(ClassLoader classLoader, String str) {
        final ContentProvider instantiateProviderCompat = super.instantiateProviderCompat(classLoader, str);
        if (instantiateProviderCompat instanceof ContextInitializer) {
            ((ContextInitializer) instantiateProviderCompat).setContextAvailableCallback(new ContextAvailableCallback() { // from class: com.android.systemui.SystemUIAppComponentFactoryBase$instantiateProviderCompat$1
                @Override // com.android.systemui.SystemUIAppComponentFactoryBase.ContextAvailableCallback
                public final SystemUIInitializer onContextAvailable(Context context) {
                    SystemUIInitializer createSystemUIInitializerInternal;
                    createSystemUIInitializerInternal = SystemUIAppComponentFactoryBase.this.createSystemUIInitializerInternal(context);
                    SysUIComponent sysUIComponent = createSystemUIInitializerInternal.getSysUIComponent();
                    try {
                        sysUIComponent.getClass().getMethod("inject", instantiateProviderCompat.getClass()).invoke(sysUIComponent, instantiateProviderCompat);
                    } catch (IllegalAccessException e) {
                        Class<?> cls = instantiateProviderCompat.getClass();
                        Log.w("AppComponentFactory", "No injector for class: " + cls, e);
                    } catch (NoSuchMethodException e2) {
                        Class<?> cls2 = instantiateProviderCompat.getClass();
                        Log.w("AppComponentFactory", "No injector for class: " + cls2, e2);
                    } catch (InvocationTargetException e3) {
                        Class<?> cls3 = instantiateProviderCompat.getClass();
                        Log.w("AppComponentFactory", "No injector for class: " + cls3, e3);
                    }
                    return createSystemUIInitializerInternal;
                }
            });
        }
        return instantiateProviderCompat;
    }

    @Override // androidx.core.app.AppComponentFactory
    public BroadcastReceiver instantiateReceiverCompat(ClassLoader classLoader, String str, Intent intent) {
        SystemUIInitializer systemUIInitializer2;
        SysUIComponent sysUIComponent;
        if (this.componentHelper == null && (systemUIInitializer2 = systemUIInitializer) != null && (sysUIComponent = systemUIInitializer2.getSysUIComponent()) != null) {
            sysUIComponent.inject(this);
        }
        BroadcastReceiver resolveBroadcastReceiver = getComponentHelper().resolveBroadcastReceiver(str);
        BroadcastReceiver broadcastReceiver = resolveBroadcastReceiver;
        if (resolveBroadcastReceiver == null) {
            broadcastReceiver = super.instantiateReceiverCompat(classLoader, str, intent);
        }
        return broadcastReceiver;
    }

    @Override // androidx.core.app.AppComponentFactory
    public Service instantiateServiceCompat(ClassLoader classLoader, String str, Intent intent) {
        SystemUIInitializer systemUIInitializer2;
        SysUIComponent sysUIComponent;
        if (this.componentHelper == null && (systemUIInitializer2 = systemUIInitializer) != null && (sysUIComponent = systemUIInitializer2.getSysUIComponent()) != null) {
            sysUIComponent.inject(this);
        }
        Service resolveService = getComponentHelper().resolveService(str);
        Service service = resolveService;
        if (resolveService == null) {
            service = super.instantiateServiceCompat(classLoader, str, intent);
        }
        return service;
    }

    public final void setComponentHelper(ContextComponentHelper contextComponentHelper) {
        this.componentHelper = contextComponentHelper;
    }
}