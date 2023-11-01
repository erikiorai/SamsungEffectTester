package androidx.lifecycle;

import android.app.Application;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.lifecycle.viewmodel.MutableCreationExtras;
import java.lang.reflect.InvocationTargetException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: mainsysui33.jar:androidx/lifecycle/ViewModelProvider.class */
public class ViewModelProvider {
    public final CreationExtras defaultCreationExtras;
    public final Factory factory;
    public final ViewModelStore store;

    /* loaded from: mainsysui33.jar:androidx/lifecycle/ViewModelProvider$AndroidViewModelFactory.class */
    public static class AndroidViewModelFactory extends NewInstanceFactory {
        public final Application application;
        public static final Companion Companion = new Companion(null);
        public static final CreationExtras.Key<Application> APPLICATION_KEY = Companion.ApplicationKeyImpl.INSTANCE;

        /* loaded from: mainsysui33.jar:androidx/lifecycle/ViewModelProvider$AndroidViewModelFactory$Companion.class */
        public static final class Companion {

            /* loaded from: mainsysui33.jar:androidx/lifecycle/ViewModelProvider$AndroidViewModelFactory$Companion$ApplicationKeyImpl.class */
            public static final class ApplicationKeyImpl implements CreationExtras.Key<Application> {
                public static final ApplicationKeyImpl INSTANCE = new ApplicationKeyImpl();
            }

            public Companion() {
            }

            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }
        }

        public AndroidViewModelFactory() {
            this(null, 0);
        }

        public AndroidViewModelFactory(Application application, int i) {
            this.application = application;
        }

        @Override // androidx.lifecycle.ViewModelProvider.NewInstanceFactory, androidx.lifecycle.ViewModelProvider.Factory
        public <T extends ViewModel> T create(Class<T> cls) {
            Application application = this.application;
            if (application != null) {
                return (T) create(cls, application);
            }
            throw new UnsupportedOperationException("AndroidViewModelFactory constructed with empty constructor works only with create(modelClass: Class<T>, extras: CreationExtras).");
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r0v13, types: [androidx.lifecycle.ViewModel] */
        public final <T extends ViewModel> T create(Class<T> cls, Application application) {
            T newInstance;
            if (AndroidViewModel.class.isAssignableFrom(cls)) {
                try {
                    newInstance = cls.getConstructor(Application.class).newInstance(application);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(Intrinsics.stringPlus("Cannot create an instance of ", cls), e);
                } catch (InstantiationException e2) {
                    throw new RuntimeException(Intrinsics.stringPlus("Cannot create an instance of ", cls), e2);
                } catch (NoSuchMethodException e3) {
                    throw new RuntimeException(Intrinsics.stringPlus("Cannot create an instance of ", cls), e3);
                } catch (InvocationTargetException e4) {
                    throw new RuntimeException(Intrinsics.stringPlus("Cannot create an instance of ", cls), e4);
                }
            } else {
                newInstance = super.create(cls);
            }
            return newInstance;
        }

        @Override // androidx.lifecycle.ViewModelProvider.Factory
        public <T extends ViewModel> T create(Class<T> cls, CreationExtras creationExtras) {
            ViewModel create;
            Application application = this.application;
            if (application != null) {
                create = create(cls, application);
            } else {
                Application application2 = (Application) creationExtras.get(APPLICATION_KEY);
                if (application2 != null) {
                    create = create(cls, application2);
                } else if (AndroidViewModel.class.isAssignableFrom(cls)) {
                    throw new IllegalArgumentException("CreationExtras must have an application by `APPLICATION_KEY`");
                } else {
                    create = super.create(cls);
                }
            }
            return (T) create;
        }
    }

    /* loaded from: mainsysui33.jar:androidx/lifecycle/ViewModelProvider$Factory.class */
    public interface Factory {
        public static final Companion Companion = Companion.$$INSTANCE;

        /* loaded from: mainsysui33.jar:androidx/lifecycle/ViewModelProvider$Factory$Companion.class */
        public static final class Companion {
            public static final /* synthetic */ Companion $$INSTANCE = new Companion();
        }

        default <T extends ViewModel> T create(Class<T> cls) {
            throw new UnsupportedOperationException("Factory.create(String) is unsupported.  This Factory requires `CreationExtras` to be passed into `create` method.");
        }

        default <T extends ViewModel> T create(Class<T> cls, CreationExtras creationExtras) {
            return (T) create(cls);
        }
    }

    /* loaded from: mainsysui33.jar:androidx/lifecycle/ViewModelProvider$NewInstanceFactory.class */
    public static class NewInstanceFactory implements Factory {
        public static final Companion Companion = new Companion(null);
        public static final CreationExtras.Key<String> VIEW_MODEL_KEY = Companion.ViewModelKeyImpl.INSTANCE;

        /* loaded from: mainsysui33.jar:androidx/lifecycle/ViewModelProvider$NewInstanceFactory$Companion.class */
        public static final class Companion {

            /* loaded from: mainsysui33.jar:androidx/lifecycle/ViewModelProvider$NewInstanceFactory$Companion$ViewModelKeyImpl.class */
            public static final class ViewModelKeyImpl implements CreationExtras.Key<String> {
                public static final ViewModelKeyImpl INSTANCE = new ViewModelKeyImpl();
            }

            public Companion() {
            }

            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }
        }

        @Override // androidx.lifecycle.ViewModelProvider.Factory
        public <T extends ViewModel> T create(Class<T> cls) {
            try {
                return cls.newInstance();
            } catch (IllegalAccessException e) {
                throw new RuntimeException(Intrinsics.stringPlus("Cannot create an instance of ", cls), e);
            } catch (InstantiationException e2) {
                throw new RuntimeException(Intrinsics.stringPlus("Cannot create an instance of ", cls), e2);
            }
        }
    }

    /* loaded from: mainsysui33.jar:androidx/lifecycle/ViewModelProvider$OnRequeryFactory.class */
    public static class OnRequeryFactory {
        public void onRequery(ViewModel viewModel) {
        }
    }

    public ViewModelProvider(ViewModelStore viewModelStore, Factory factory) {
        this(viewModelStore, factory, null, 4, null);
    }

    public ViewModelProvider(ViewModelStore viewModelStore, Factory factory, CreationExtras creationExtras) {
        this.store = viewModelStore;
        this.factory = factory;
        this.defaultCreationExtras = creationExtras;
    }

    public /* synthetic */ ViewModelProvider(ViewModelStore viewModelStore, Factory factory, CreationExtras creationExtras, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(viewModelStore, factory, (i & 4) != 0 ? CreationExtras.Empty.INSTANCE : creationExtras);
    }

    public ViewModelProvider(ViewModelStoreOwner viewModelStoreOwner, Factory factory) {
        this(viewModelStoreOwner.getViewModelStore(), factory, ViewModelProviderKt.defaultCreationExtras(viewModelStoreOwner));
    }

    public <T extends ViewModel> T get(Class<T> cls) {
        String canonicalName = cls.getCanonicalName();
        if (canonicalName != null) {
            return (T) get(Intrinsics.stringPlus("androidx.lifecycle.ViewModelProvider.DefaultKey:", canonicalName), cls);
        }
        throw new IllegalArgumentException("Local and anonymous classes can not be ViewModels");
    }

    public <T extends ViewModel> T get(String str, Class<T> cls) {
        T t = (T) this.store.get(str);
        if (!cls.isInstance(t)) {
            MutableCreationExtras mutableCreationExtras = new MutableCreationExtras(this.defaultCreationExtras);
            mutableCreationExtras.set(NewInstanceFactory.VIEW_MODEL_KEY, str);
            T t2 = (T) this.factory.create(cls, mutableCreationExtras);
            this.store.put(str, t2);
            return t2;
        }
        Factory factory = this.factory;
        OnRequeryFactory onRequeryFactory = factory instanceof OnRequeryFactory ? (OnRequeryFactory) factory : null;
        if (onRequeryFactory != null) {
            onRequeryFactory.onRequery(t);
        }
        if (t != null) {
            return t;
        }
        throw new NullPointerException("null cannot be cast to non-null type T of androidx.lifecycle.ViewModelProvider.get");
    }
}