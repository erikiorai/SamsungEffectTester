package com.android.systemui.dreams.touch;

import android.graphics.Region;
import android.view.GestureDetector;
import android.view.InputEvent;
import android.view.MotionEvent;
import androidx.concurrent.futures.CallbackToFutureAdapter;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import com.android.systemui.dreams.DreamOverlayStateController$$ExternalSyntheticLambda3;
import com.android.systemui.dreams.touch.DreamOverlayTouchMonitor;
import com.android.systemui.dreams.touch.DreamTouchHandler;
import com.android.systemui.dreams.touch.dagger.InputSessionComponent;
import com.android.systemui.shared.system.InputChannelCompat;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/* loaded from: mainsysui33.jar:com/android/systemui/dreams/touch/DreamOverlayTouchMonitor.class */
public class DreamOverlayTouchMonitor {
    public InputSession mCurrentInputSession;
    public final Executor mExecutor;
    public final Collection<DreamTouchHandler> mHandlers;
    public InputSessionComponent.Factory mInputSessionFactory;
    public final Lifecycle mLifecycle;
    public final LifecycleObserver mLifecycleObserver = new DefaultLifecycleObserver() { // from class: com.android.systemui.dreams.touch.DreamOverlayTouchMonitor.1
        {
            DreamOverlayTouchMonitor.this = this;
        }

        @Override // androidx.lifecycle.DefaultLifecycleObserver, androidx.lifecycle.FullLifecycleObserver
        public void onPause(LifecycleOwner lifecycleOwner) {
            DreamOverlayTouchMonitor.this.stopMonitoring();
        }

        @Override // androidx.lifecycle.DefaultLifecycleObserver, androidx.lifecycle.FullLifecycleObserver
        public void onResume(LifecycleOwner lifecycleOwner) {
            DreamOverlayTouchMonitor.this.startMonitoring();
        }
    };
    public final HashSet<TouchSessionImpl> mActiveTouchSessions = new HashSet<>();
    public InputChannelCompat.InputEventListener mInputEventListener = new AnonymousClass2();
    public GestureDetector.OnGestureListener mOnGestureListener = new AnonymousClass3();

    /* renamed from: com.android.systemui.dreams.touch.DreamOverlayTouchMonitor$2 */
    /* loaded from: mainsysui33.jar:com/android/systemui/dreams/touch/DreamOverlayTouchMonitor$2.class */
    public class AnonymousClass2 implements InputChannelCompat.InputEventListener {
        /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.dreams.touch.DreamOverlayTouchMonitor$2$$ExternalSyntheticLambda1.apply(java.lang.Object):java.lang.Object] */
        public static /* synthetic */ Collection $r8$lambda$2FjpQuMHJ7kjEvDb9eSDAtD3F0s(TouchSessionImpl touchSessionImpl) {
            return touchSessionImpl.getEventListeners();
        }

        /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.dreams.touch.DreamOverlayTouchMonitor$2$$ExternalSyntheticLambda3.accept(java.lang.Object):void] */
        public static /* synthetic */ void $r8$lambda$OU6P1UZanX5J3LOVhwFM91FAWuc(InputEvent inputEvent, InputChannelCompat.InputEventListener inputEventListener) {
            inputEventListener.onInputEvent(inputEvent);
        }

        /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.dreams.touch.DreamOverlayTouchMonitor$2$$ExternalSyntheticLambda0.accept(java.lang.Object, java.lang.Object):void] */
        /* renamed from: $r8$lambda$hRJGxHDQIPkt-DkpAfd6rpd_9Qc */
        public static /* synthetic */ void m2646$r8$lambda$hRJGxHDQIPktDkpAfd6rpd_9Qc(DreamTouchHandler dreamTouchHandler, DreamTouchHandler.TouchSession touchSession) {
            dreamTouchHandler.onSessionStart(touchSession);
        }

        public AnonymousClass2() {
            DreamOverlayTouchMonitor.this = r4;
        }

        public void onInputEvent(final InputEvent inputEvent) {
            if (DreamOverlayTouchMonitor.this.mActiveTouchSessions.isEmpty()) {
                HashMap hashMap = new HashMap();
                for (DreamTouchHandler dreamTouchHandler : DreamOverlayTouchMonitor.this.mHandlers) {
                    Region obtain = Region.obtain();
                    dreamTouchHandler.getTouchInitiationRegion(obtain);
                    if (!obtain.isEmpty()) {
                        if (inputEvent instanceof MotionEvent) {
                            MotionEvent motionEvent = (MotionEvent) inputEvent;
                            if (!obtain.contains(Math.round(motionEvent.getX()), Math.round(motionEvent.getY()))) {
                            }
                        }
                    }
                    TouchSessionImpl touchSessionImpl = new TouchSessionImpl(DreamOverlayTouchMonitor.this, null);
                    DreamOverlayTouchMonitor.this.mActiveTouchSessions.add(touchSessionImpl);
                    hashMap.put(dreamTouchHandler, touchSessionImpl);
                }
                hashMap.forEach(new BiConsumer() { // from class: com.android.systemui.dreams.touch.DreamOverlayTouchMonitor$2$$ExternalSyntheticLambda0
                    @Override // java.util.function.BiConsumer
                    public final void accept(Object obj, Object obj2) {
                        DreamOverlayTouchMonitor.AnonymousClass2.m2646$r8$lambda$hRJGxHDQIPktDkpAfd6rpd_9Qc((DreamTouchHandler) obj, (DreamTouchHandler.TouchSession) obj2);
                    }
                });
            }
            DreamOverlayTouchMonitor.this.mActiveTouchSessions.stream().map(new Function() { // from class: com.android.systemui.dreams.touch.DreamOverlayTouchMonitor$2$$ExternalSyntheticLambda1
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return DreamOverlayTouchMonitor.AnonymousClass2.$r8$lambda$2FjpQuMHJ7kjEvDb9eSDAtD3F0s((DreamOverlayTouchMonitor.TouchSessionImpl) obj);
                }
            }).flatMap(new DreamOverlayTouchMonitor$2$$ExternalSyntheticLambda2()).forEach(new Consumer() { // from class: com.android.systemui.dreams.touch.DreamOverlayTouchMonitor$2$$ExternalSyntheticLambda3
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    DreamOverlayTouchMonitor.AnonymousClass2.$r8$lambda$OU6P1UZanX5J3LOVhwFM91FAWuc(inputEvent, (InputChannelCompat.InputEventListener) obj);
                }
            });
        }
    }

    /* renamed from: com.android.systemui.dreams.touch.DreamOverlayTouchMonitor$3 */
    /* loaded from: mainsysui33.jar:com/android/systemui/dreams/touch/DreamOverlayTouchMonitor$3.class */
    public class AnonymousClass3 implements GestureDetector.OnGestureListener {
        /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.dreams.touch.DreamOverlayTouchMonitor$3$$ExternalSyntheticLambda10.apply(java.lang.Object):java.lang.Object] */
        public static /* synthetic */ Boolean $r8$lambda$1FYMbpYOTEbYPmbqMIwSGObsvzM(Evaluator evaluator, GestureDetector.OnGestureListener onGestureListener) {
            return lambda$evaluate$0(evaluator, onGestureListener);
        }

        /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.dreams.touch.DreamOverlayTouchMonitor$3$$ExternalSyntheticLambda11.test(java.lang.Object):boolean] */
        public static /* synthetic */ boolean $r8$lambda$1YgsbgY16iKFROySf0zVxo3GXXc(Boolean bool) {
            return bool.booleanValue();
        }

        /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.dreams.touch.DreamOverlayTouchMonitor$3$$ExternalSyntheticLambda3.evaluate(android.view.GestureDetector$OnGestureListener):boolean] */
        public static /* synthetic */ boolean $r8$lambda$6d3AVguiFbumeghmEJnGfTVKBmY(MotionEvent motionEvent, GestureDetector.OnGestureListener onGestureListener) {
            return lambda$onDown$6(motionEvent, onGestureListener);
        }

        /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.dreams.touch.DreamOverlayTouchMonitor$3$$ExternalSyntheticLambda6.apply(java.lang.Object):java.lang.Object] */
        public static /* synthetic */ Boolean $r8$lambda$HMNvedyCn2eXCOtYjyrkVWDklD0(Evaluator evaluator, Set set, TouchSessionImpl touchSessionImpl) {
            return lambda$evaluate$2(evaluator, set, touchSessionImpl);
        }

        /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.dreams.touch.DreamOverlayTouchMonitor$3$$ExternalSyntheticLambda2.accept(java.lang.Object):void] */
        public static /* synthetic */ void $r8$lambda$N7mrgKuN6MAtpZS8JdRvaE6Ii0w(MotionEvent motionEvent, GestureDetector.OnGestureListener onGestureListener) {
            onGestureListener.onShowPress(motionEvent);
        }

        /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.dreams.touch.DreamOverlayTouchMonitor$3$$ExternalSyntheticLambda5.evaluate(android.view.GestureDetector$OnGestureListener):boolean] */
        /* renamed from: $r8$lambda$NAaa-YZE6DNVYVi209Iw4lxD0VY */
        public static /* synthetic */ boolean m2647$r8$lambda$NAaaYZE6DNVYVi209Iw4lxD0VY(MotionEvent motionEvent, GestureDetector.OnGestureListener onGestureListener) {
            return lambda$onSingleTapUp$11(motionEvent, onGestureListener);
        }

        /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.dreams.touch.DreamOverlayTouchMonitor$3$$ExternalSyntheticLambda7.test(java.lang.Object):boolean] */
        /* renamed from: $r8$lambda$PhMulborYo8vc-eumzNwSWazWQI */
        public static /* synthetic */ boolean m2648$r8$lambda$PhMulborYo8vceumzNwSWazWQI(Boolean bool) {
            return bool.booleanValue();
        }

        /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.dreams.touch.DreamOverlayTouchMonitor$3$$ExternalSyntheticLambda4.accept(java.lang.Object):void] */
        public static /* synthetic */ void $r8$lambda$RfI4TR1PAyyEnrYC2cbFTj70wp4(MotionEvent motionEvent, GestureDetector.OnGestureListener onGestureListener) {
            onGestureListener.onLongPress(motionEvent);
        }

        /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.dreams.touch.DreamOverlayTouchMonitor$3$$ExternalSyntheticLambda0.evaluate(android.view.GestureDetector$OnGestureListener):boolean] */
        public static /* synthetic */ boolean $r8$lambda$VpFkev5uQbgdNNeOnTFzKqOevhA(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2, GestureDetector.OnGestureListener onGestureListener) {
            return lambda$onFling$7(motionEvent, motionEvent2, f, f2, onGestureListener);
        }

        /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.dreams.touch.DreamOverlayTouchMonitor$3$$ExternalSyntheticLambda9.accept(java.lang.Object):void] */
        public static /* synthetic */ void $r8$lambda$_5IVrYByB79PKKa0nR4mgnUd0ds(Consumer consumer, GestureDetector.OnGestureListener onGestureListener) {
            consumer.accept(onGestureListener);
        }

        /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.dreams.touch.DreamOverlayTouchMonitor$3$$ExternalSyntheticLambda1.evaluate(android.view.GestureDetector$OnGestureListener):boolean] */
        public static /* synthetic */ boolean $r8$lambda$j7tV9t1QCsLhsIcEUnDLoDPJmfM(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2, GestureDetector.OnGestureListener onGestureListener) {
            return lambda$onScroll$9(motionEvent, motionEvent2, f, f2, onGestureListener);
        }

        /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.dreams.touch.DreamOverlayTouchMonitor$3$$ExternalSyntheticLambda8.apply(java.lang.Object):java.lang.Object] */
        public static /* synthetic */ Collection $r8$lambda$saKmI7fVwKFHtCQbq5IYlU8k67E(TouchSessionImpl touchSessionImpl) {
            return touchSessionImpl.getGestureListeners();
        }

        public AnonymousClass3() {
            DreamOverlayTouchMonitor.this = r4;
        }

        public static /* synthetic */ Boolean lambda$evaluate$0(Evaluator evaluator, GestureDetector.OnGestureListener onGestureListener) {
            return Boolean.valueOf(evaluator.evaluate(onGestureListener));
        }

        public static /* synthetic */ Boolean lambda$evaluate$2(final Evaluator evaluator, Set set, TouchSessionImpl touchSessionImpl) {
            boolean anyMatch = touchSessionImpl.getGestureListeners().stream().map(new Function() { // from class: com.android.systemui.dreams.touch.DreamOverlayTouchMonitor$3$$ExternalSyntheticLambda10
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return DreamOverlayTouchMonitor.AnonymousClass3.$r8$lambda$1FYMbpYOTEbYPmbqMIwSGObsvzM(DreamOverlayTouchMonitor.Evaluator.this, (GestureDetector.OnGestureListener) obj);
                }
            }).anyMatch(new Predicate() { // from class: com.android.systemui.dreams.touch.DreamOverlayTouchMonitor$3$$ExternalSyntheticLambda11
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return DreamOverlayTouchMonitor.AnonymousClass3.$r8$lambda$1YgsbgY16iKFROySf0zVxo3GXXc((Boolean) obj);
                }
            });
            if (anyMatch) {
                set.add(touchSessionImpl);
            }
            return Boolean.valueOf(anyMatch);
        }

        public static /* synthetic */ boolean lambda$onDown$6(MotionEvent motionEvent, GestureDetector.OnGestureListener onGestureListener) {
            return onGestureListener.onDown(motionEvent);
        }

        public static /* synthetic */ boolean lambda$onFling$7(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2, GestureDetector.OnGestureListener onGestureListener) {
            return onGestureListener.onFling(motionEvent, motionEvent2, f, f2);
        }

        public static /* synthetic */ boolean lambda$onScroll$9(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2, GestureDetector.OnGestureListener onGestureListener) {
            return onGestureListener.onScroll(motionEvent, motionEvent2, f, f2);
        }

        public static /* synthetic */ boolean lambda$onSingleTapUp$11(MotionEvent motionEvent, GestureDetector.OnGestureListener onGestureListener) {
            return onGestureListener.onSingleTapUp(motionEvent);
        }

        public final boolean evaluate(final Evaluator evaluator) {
            final HashSet hashSet = new HashSet();
            boolean anyMatch = DreamOverlayTouchMonitor.this.mActiveTouchSessions.stream().map(new Function() { // from class: com.android.systemui.dreams.touch.DreamOverlayTouchMonitor$3$$ExternalSyntheticLambda6
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return DreamOverlayTouchMonitor.AnonymousClass3.$r8$lambda$HMNvedyCn2eXCOtYjyrkVWDklD0(DreamOverlayTouchMonitor.Evaluator.this, hashSet, (DreamOverlayTouchMonitor.TouchSessionImpl) obj);
                }
            }).anyMatch(new Predicate() { // from class: com.android.systemui.dreams.touch.DreamOverlayTouchMonitor$3$$ExternalSyntheticLambda7
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return DreamOverlayTouchMonitor.AnonymousClass3.m2648$r8$lambda$PhMulborYo8vceumzNwSWazWQI((Boolean) obj);
                }
            });
            if (anyMatch) {
                DreamOverlayTouchMonitor.this.isolate(hashSet);
            }
            return anyMatch;
        }

        public final void observe(final Consumer<GestureDetector.OnGestureListener> consumer) {
            DreamOverlayTouchMonitor.this.mActiveTouchSessions.stream().map(new Function() { // from class: com.android.systemui.dreams.touch.DreamOverlayTouchMonitor$3$$ExternalSyntheticLambda8
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return DreamOverlayTouchMonitor.AnonymousClass3.$r8$lambda$saKmI7fVwKFHtCQbq5IYlU8k67E((DreamOverlayTouchMonitor.TouchSessionImpl) obj);
                }
            }).flatMap(new DreamOverlayTouchMonitor$2$$ExternalSyntheticLambda2()).forEach(new Consumer() { // from class: com.android.systemui.dreams.touch.DreamOverlayTouchMonitor$3$$ExternalSyntheticLambda9
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    DreamOverlayTouchMonitor.AnonymousClass3.$r8$lambda$_5IVrYByB79PKKa0nR4mgnUd0ds(consumer, (GestureDetector.OnGestureListener) obj);
                }
            });
        }

        @Override // android.view.GestureDetector.OnGestureListener
        public boolean onDown(final MotionEvent motionEvent) {
            return evaluate(new Evaluator() { // from class: com.android.systemui.dreams.touch.DreamOverlayTouchMonitor$3$$ExternalSyntheticLambda3
                @Override // com.android.systemui.dreams.touch.DreamOverlayTouchMonitor.Evaluator
                public final boolean evaluate(GestureDetector.OnGestureListener onGestureListener) {
                    return DreamOverlayTouchMonitor.AnonymousClass3.$r8$lambda$6d3AVguiFbumeghmEJnGfTVKBmY(motionEvent, onGestureListener);
                }
            });
        }

        @Override // android.view.GestureDetector.OnGestureListener
        public boolean onFling(final MotionEvent motionEvent, final MotionEvent motionEvent2, final float f, final float f2) {
            return evaluate(new Evaluator() { // from class: com.android.systemui.dreams.touch.DreamOverlayTouchMonitor$3$$ExternalSyntheticLambda0
                @Override // com.android.systemui.dreams.touch.DreamOverlayTouchMonitor.Evaluator
                public final boolean evaluate(GestureDetector.OnGestureListener onGestureListener) {
                    return DreamOverlayTouchMonitor.AnonymousClass3.$r8$lambda$VpFkev5uQbgdNNeOnTFzKqOevhA(motionEvent, motionEvent2, f, f2, onGestureListener);
                }
            });
        }

        @Override // android.view.GestureDetector.OnGestureListener
        public void onLongPress(final MotionEvent motionEvent) {
            observe(new Consumer() { // from class: com.android.systemui.dreams.touch.DreamOverlayTouchMonitor$3$$ExternalSyntheticLambda4
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    DreamOverlayTouchMonitor.AnonymousClass3.$r8$lambda$RfI4TR1PAyyEnrYC2cbFTj70wp4(motionEvent, (GestureDetector.OnGestureListener) obj);
                }
            });
        }

        @Override // android.view.GestureDetector.OnGestureListener
        public boolean onScroll(final MotionEvent motionEvent, final MotionEvent motionEvent2, final float f, final float f2) {
            return evaluate(new Evaluator() { // from class: com.android.systemui.dreams.touch.DreamOverlayTouchMonitor$3$$ExternalSyntheticLambda1
                @Override // com.android.systemui.dreams.touch.DreamOverlayTouchMonitor.Evaluator
                public final boolean evaluate(GestureDetector.OnGestureListener onGestureListener) {
                    return DreamOverlayTouchMonitor.AnonymousClass3.$r8$lambda$j7tV9t1QCsLhsIcEUnDLoDPJmfM(motionEvent, motionEvent2, f, f2, onGestureListener);
                }
            });
        }

        @Override // android.view.GestureDetector.OnGestureListener
        public void onShowPress(final MotionEvent motionEvent) {
            observe(new Consumer() { // from class: com.android.systemui.dreams.touch.DreamOverlayTouchMonitor$3$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    DreamOverlayTouchMonitor.AnonymousClass3.$r8$lambda$N7mrgKuN6MAtpZS8JdRvaE6Ii0w(motionEvent, (GestureDetector.OnGestureListener) obj);
                }
            });
        }

        @Override // android.view.GestureDetector.OnGestureListener
        public boolean onSingleTapUp(final MotionEvent motionEvent) {
            return evaluate(new Evaluator() { // from class: com.android.systemui.dreams.touch.DreamOverlayTouchMonitor$3$$ExternalSyntheticLambda5
                @Override // com.android.systemui.dreams.touch.DreamOverlayTouchMonitor.Evaluator
                public final boolean evaluate(GestureDetector.OnGestureListener onGestureListener) {
                    return DreamOverlayTouchMonitor.AnonymousClass3.m2647$r8$lambda$NAaaYZE6DNVYVi209Iw4lxD0VY(motionEvent, onGestureListener);
                }
            });
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/dreams/touch/DreamOverlayTouchMonitor$Evaluator.class */
    public interface Evaluator {
        boolean evaluate(GestureDetector.OnGestureListener onGestureListener);
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/dreams/touch/DreamOverlayTouchMonitor$TouchSessionImpl.class */
    public static class TouchSessionImpl implements DreamTouchHandler.TouchSession {
        public final TouchSessionImpl mPredecessor;
        public final DreamOverlayTouchMonitor mTouchMonitor;
        public final HashSet<InputChannelCompat.InputEventListener> mEventListeners = new HashSet<>();
        public final HashSet<GestureDetector.OnGestureListener> mGestureListeners = new HashSet<>();
        public final HashSet<DreamTouchHandler.TouchSession.Callback> mCallbacks = new HashSet<>();

        /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.dreams.touch.DreamOverlayTouchMonitor$TouchSessionImpl$$ExternalSyntheticLambda0.accept(java.lang.Object):void] */
        /* renamed from: $r8$lambda$YBzCsgb9AN5sxBhKDExD-cZnSzg */
        public static /* synthetic */ void m2649$r8$lambda$YBzCsgb9AN5sxBhKDExDcZnSzg(DreamTouchHandler.TouchSession.Callback callback) {
            callback.onRemoved();
        }

        /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.dreams.touch.DreamOverlayTouchMonitor.$r8$lambda$JYWb1RDRveW0DRoVN8G6WN8gVDQ(com.android.systemui.dreams.touch.DreamOverlayTouchMonitor$TouchSessionImpl):void, com.android.systemui.dreams.touch.DreamOverlayTouchMonitor.$r8$lambda$JYWb1RDRveW0DRoVN8G6WN8gVDQ(com.android.systemui.dreams.touch.DreamOverlayTouchMonitor$TouchSessionImpl):void] */
        /* renamed from: -$$Nest$monRemoved */
        public static /* bridge */ /* synthetic */ void m2651$$Nest$monRemoved(TouchSessionImpl touchSessionImpl) {
            touchSessionImpl.onRemoved();
        }

        public TouchSessionImpl(DreamOverlayTouchMonitor dreamOverlayTouchMonitor, TouchSessionImpl touchSessionImpl) {
            this.mPredecessor = touchSessionImpl;
            this.mTouchMonitor = dreamOverlayTouchMonitor;
        }

        @Override // com.android.systemui.dreams.touch.DreamTouchHandler.TouchSession
        public int getActiveSessionCount() {
            return this.mTouchMonitor.getSessionCount();
        }

        public Collection<InputChannelCompat.InputEventListener> getEventListeners() {
            return this.mEventListeners;
        }

        public Collection<GestureDetector.OnGestureListener> getGestureListeners() {
            return this.mGestureListeners;
        }

        public final TouchSessionImpl getPredecessor() {
            return this.mPredecessor;
        }

        public final void onRemoved() {
            this.mCallbacks.forEach(new Consumer() { // from class: com.android.systemui.dreams.touch.DreamOverlayTouchMonitor$TouchSessionImpl$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    DreamOverlayTouchMonitor.TouchSessionImpl.m2649$r8$lambda$YBzCsgb9AN5sxBhKDExDcZnSzg((DreamTouchHandler.TouchSession.Callback) obj);
                }
            });
        }

        @Override // com.android.systemui.dreams.touch.DreamTouchHandler.TouchSession
        public ListenableFuture<DreamTouchHandler.TouchSession> pop() {
            return this.mTouchMonitor.pop(this);
        }

        @Override // com.android.systemui.dreams.touch.DreamTouchHandler.TouchSession
        public void registerCallback(DreamTouchHandler.TouchSession.Callback callback) {
            this.mCallbacks.add(callback);
        }

        @Override // com.android.systemui.dreams.touch.DreamTouchHandler.TouchSession
        public boolean registerGestureListener(GestureDetector.OnGestureListener onGestureListener) {
            return this.mGestureListeners.add(onGestureListener);
        }

        @Override // com.android.systemui.dreams.touch.DreamTouchHandler.TouchSession
        public boolean registerInputListener(InputChannelCompat.InputEventListener inputEventListener) {
            return this.mEventListeners.add(inputEventListener);
        }
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.dreams.touch.DreamOverlayTouchMonitor$$ExternalSyntheticLambda1.accept(java.lang.Object):void] */
    public static /* synthetic */ void $r8$lambda$JYWb1RDRveW0DRoVN8G6WN8gVDQ(TouchSessionImpl touchSessionImpl) {
        TouchSessionImpl.m2651$$Nest$monRemoved(touchSessionImpl);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.dreams.touch.DreamOverlayTouchMonitor$$ExternalSyntheticLambda3.run():void] */
    public static /* synthetic */ void $r8$lambda$fvU_mfPdrnwcMFOwhUutHjkWCnU(DreamOverlayTouchMonitor dreamOverlayTouchMonitor, TouchSessionImpl touchSessionImpl, CallbackToFutureAdapter.Completer completer) {
        dreamOverlayTouchMonitor.lambda$pop$2(touchSessionImpl, completer);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.dreams.touch.DreamOverlayTouchMonitor$$ExternalSyntheticLambda2.attachCompleter(androidx.concurrent.futures.CallbackToFutureAdapter$Completer):java.lang.Object] */
    public static /* synthetic */ Object $r8$lambda$ieZA8Clm3blF8R5elqqZpXJSRc0(DreamOverlayTouchMonitor dreamOverlayTouchMonitor, TouchSessionImpl touchSessionImpl, CallbackToFutureAdapter.Completer completer) {
        return dreamOverlayTouchMonitor.lambda$pop$3(touchSessionImpl, completer);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.dreams.touch.DreamOverlayTouchMonitor$$ExternalSyntheticLambda0.test(java.lang.Object):boolean] */
    public static /* synthetic */ boolean $r8$lambda$xhYsZsZF7dMHzNM_Ep81vtXnE7k(Set set, TouchSessionImpl touchSessionImpl) {
        return lambda$isolate$4(set, touchSessionImpl);
    }

    public DreamOverlayTouchMonitor(Executor executor, Lifecycle lifecycle, InputSessionComponent.Factory factory, Set<DreamTouchHandler> set) {
        this.mHandlers = set;
        this.mInputSessionFactory = factory;
        this.mExecutor = executor;
        this.mLifecycle = lifecycle;
    }

    public static /* synthetic */ boolean lambda$isolate$4(Set set, TouchSessionImpl touchSessionImpl) {
        return !set.contains(touchSessionImpl);
    }

    public /* synthetic */ void lambda$pop$2(TouchSessionImpl touchSessionImpl, CallbackToFutureAdapter.Completer completer) {
        if (this.mActiveTouchSessions.remove(touchSessionImpl)) {
            touchSessionImpl.onRemoved();
            TouchSessionImpl predecessor = touchSessionImpl.getPredecessor();
            if (predecessor != null) {
                this.mActiveTouchSessions.add(predecessor);
            }
            completer.set(predecessor);
        }
    }

    public /* synthetic */ Object lambda$pop$3(final TouchSessionImpl touchSessionImpl, final CallbackToFutureAdapter.Completer completer) throws Exception {
        this.mExecutor.execute(new Runnable() { // from class: com.android.systemui.dreams.touch.DreamOverlayTouchMonitor$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                DreamOverlayTouchMonitor.$r8$lambda$fvU_mfPdrnwcMFOwhUutHjkWCnU(DreamOverlayTouchMonitor.this, touchSessionImpl, completer);
            }
        });
        return "DreamOverlayTouchMonitor::pop";
    }

    public final int getSessionCount() {
        return this.mActiveTouchSessions.size();
    }

    public void init() {
        this.mLifecycle.addObserver(this.mLifecycleObserver);
    }

    public final void isolate(final Set<TouchSessionImpl> set) {
        Collection<?> collection = (Collection) this.mActiveTouchSessions.stream().filter(new Predicate() { // from class: com.android.systemui.dreams.touch.DreamOverlayTouchMonitor$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return DreamOverlayTouchMonitor.$r8$lambda$xhYsZsZF7dMHzNM_Ep81vtXnE7k(set, (DreamOverlayTouchMonitor.TouchSessionImpl) obj);
            }
        }).collect(Collectors.toCollection(new DreamOverlayStateController$$ExternalSyntheticLambda3()));
        collection.forEach(new Consumer() { // from class: com.android.systemui.dreams.touch.DreamOverlayTouchMonitor$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                DreamOverlayTouchMonitor.$r8$lambda$JYWb1RDRveW0DRoVN8G6WN8gVDQ((DreamOverlayTouchMonitor.TouchSessionImpl) obj);
            }
        });
        this.mActiveTouchSessions.removeAll(collection);
    }

    public final ListenableFuture<DreamTouchHandler.TouchSession> pop(final TouchSessionImpl touchSessionImpl) {
        return CallbackToFutureAdapter.getFuture(new CallbackToFutureAdapter.Resolver() { // from class: com.android.systemui.dreams.touch.DreamOverlayTouchMonitor$$ExternalSyntheticLambda2
            @Override // androidx.concurrent.futures.CallbackToFutureAdapter.Resolver
            public final Object attachCompleter(CallbackToFutureAdapter.Completer completer) {
                return DreamOverlayTouchMonitor.$r8$lambda$ieZA8Clm3blF8R5elqqZpXJSRc0(DreamOverlayTouchMonitor.this, touchSessionImpl, completer);
            }
        });
    }

    public final void startMonitoring() {
        stopMonitoring();
        this.mCurrentInputSession = this.mInputSessionFactory.create("dreamOverlay", this.mInputEventListener, this.mOnGestureListener, true).getInputSession();
    }

    public final void stopMonitoring() {
        InputSession inputSession = this.mCurrentInputSession;
        if (inputSession == null) {
            return;
        }
        inputSession.dispose();
        this.mCurrentInputSession = null;
    }
}