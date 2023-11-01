package com.android.systemui.keyguard.ui.binder;

import android.view.View;
import androidx.constraintlayout.widget.R$styleable;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.RepeatOnLifecycleKt;
import com.android.systemui.keyguard.ui.viewmodel.LightRevealScrimViewModel;
import com.android.systemui.statusbar.LightRevealEffect;
import com.android.systemui.statusbar.LightRevealScrim;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineStart;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

@DebugMetadata(c = "com.android.systemui.keyguard.ui.binder.LightRevealScrimViewBinder$bind$1", f = "LightRevealScrimViewBinder.kt", l = {30}, m = "invokeSuspend")
/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/ui/binder/LightRevealScrimViewBinder$bind$1.class */
public final class LightRevealScrimViewBinder$bind$1 extends SuspendLambda implements Function3<LifecycleOwner, View, Continuation<? super Unit>, Object> {
    public final /* synthetic */ LightRevealScrim $revealScrim;
    public final /* synthetic */ LightRevealScrimViewModel $viewModel;
    private /* synthetic */ Object L$0;
    public int label;

    @DebugMetadata(c = "com.android.systemui.keyguard.ui.binder.LightRevealScrimViewBinder$bind$1$1", f = "LightRevealScrimViewBinder.kt", l = {}, m = "invokeSuspend")
    /* renamed from: com.android.systemui.keyguard.ui.binder.LightRevealScrimViewBinder$bind$1$1  reason: invalid class name */
    /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/ui/binder/LightRevealScrimViewBinder$bind$1$1.class */
    public static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        public final /* synthetic */ LightRevealScrim $revealScrim;
        public final /* synthetic */ LightRevealScrimViewModel $viewModel;
        private /* synthetic */ Object L$0;
        public int label;

        @DebugMetadata(c = "com.android.systemui.keyguard.ui.binder.LightRevealScrimViewBinder$bind$1$1$1", f = "LightRevealScrimViewBinder.kt", l = {32}, m = "invokeSuspend")
        /* renamed from: com.android.systemui.keyguard.ui.binder.LightRevealScrimViewBinder$bind$1$1$1  reason: invalid class name and collision with other inner class name */
        /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/ui/binder/LightRevealScrimViewBinder$bind$1$1$1.class */
        public static final class C00341 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            public final /* synthetic */ LightRevealScrim $revealScrim;
            public final /* synthetic */ LightRevealScrimViewModel $viewModel;
            public int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C00341(LightRevealScrimViewModel lightRevealScrimViewModel, LightRevealScrim lightRevealScrim, Continuation<? super C00341> continuation) {
                super(2, continuation);
                this.$viewModel = lightRevealScrimViewModel;
                this.$revealScrim = lightRevealScrim;
            }

            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new C00341(this.$viewModel, this.$revealScrim, continuation);
            }

            /* JADX DEBUG: Method merged with bridge method */
            public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
                return create(coroutineScope, continuation).invokeSuspend(Unit.INSTANCE);
            }

            public final Object invokeSuspend(Object obj) {
                Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    Flow<Float> revealAmount = this.$viewModel.getRevealAmount();
                    final LightRevealScrim lightRevealScrim = this.$revealScrim;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.ui.binder.LightRevealScrimViewBinder.bind.1.1.1.1
                        public final Object emit(float f, Continuation<? super Unit> continuation) {
                            lightRevealScrim.setRevealAmount(f);
                            return Unit.INSTANCE;
                        }

                        public /* bridge */ /* synthetic */ Object emit(Object obj2, Continuation continuation) {
                            return emit(((Number) obj2).floatValue(), continuation);
                        }
                    };
                    this.label = 1;
                    if (revealAmount.collect(flowCollector, this) == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                } else if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                } else {
                    ResultKt.throwOnFailure(obj);
                }
                return Unit.INSTANCE;
            }
        }

        @DebugMetadata(c = "com.android.systemui.keyguard.ui.binder.LightRevealScrimViewBinder$bind$1$1$2", f = "LightRevealScrimViewBinder.kt", l = {R$styleable.ConstraintLayout_Layout_constraint_referenced_tags}, m = "invokeSuspend")
        /* renamed from: com.android.systemui.keyguard.ui.binder.LightRevealScrimViewBinder$bind$1$1$2  reason: invalid class name */
        /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/ui/binder/LightRevealScrimViewBinder$bind$1$1$2.class */
        public static final class AnonymousClass2 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            public final /* synthetic */ LightRevealScrim $revealScrim;
            public final /* synthetic */ LightRevealScrimViewModel $viewModel;
            public int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass2(LightRevealScrimViewModel lightRevealScrimViewModel, LightRevealScrim lightRevealScrim, Continuation<? super AnonymousClass2> continuation) {
                super(2, continuation);
                this.$viewModel = lightRevealScrimViewModel;
                this.$revealScrim = lightRevealScrim;
            }

            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new AnonymousClass2(this.$viewModel, this.$revealScrim, continuation);
            }

            /* JADX DEBUG: Method merged with bridge method */
            public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
                return create(coroutineScope, continuation).invokeSuspend(Unit.INSTANCE);
            }

            public final Object invokeSuspend(Object obj) {
                Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    Flow<LightRevealEffect> lightRevealEffect = this.$viewModel.getLightRevealEffect();
                    final LightRevealScrim lightRevealScrim = this.$revealScrim;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.ui.binder.LightRevealScrimViewBinder.bind.1.1.2.1
                        public final Object emit(LightRevealEffect lightRevealEffect2, Continuation<? super Unit> continuation) {
                            lightRevealScrim.setRevealEffect(lightRevealEffect2);
                            return Unit.INSTANCE;
                        }

                        public /* bridge */ /* synthetic */ Object emit(Object obj2, Continuation continuation) {
                            return emit((LightRevealEffect) obj2, (Continuation<? super Unit>) continuation);
                        }
                    };
                    this.label = 1;
                    if (lightRevealEffect.collect(flowCollector, this) == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                } else if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                } else {
                    ResultKt.throwOnFailure(obj);
                }
                return Unit.INSTANCE;
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(LightRevealScrimViewModel lightRevealScrimViewModel, LightRevealScrim lightRevealScrim, Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
            this.$viewModel = lightRevealScrimViewModel;
            this.$revealScrim = lightRevealScrim;
        }

        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$viewModel, this.$revealScrim, continuation);
            anonymousClass1.L$0 = obj;
            return anonymousClass1;
        }

        /* JADX DEBUG: Method merged with bridge method */
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return create(coroutineScope, continuation).invokeSuspend(Unit.INSTANCE);
        }

        public final Object invokeSuspend(Object obj) {
            IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label == 0) {
                ResultKt.throwOnFailure(obj);
                CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
                BuildersKt.launch$default(coroutineScope, (CoroutineContext) null, (CoroutineStart) null, new C00341(this.$viewModel, this.$revealScrim, null), 3, (Object) null);
                BuildersKt.launch$default(coroutineScope, (CoroutineContext) null, (CoroutineStart) null, new AnonymousClass2(this.$viewModel, this.$revealScrim, null), 3, (Object) null);
                return Unit.INSTANCE;
            }
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LightRevealScrimViewBinder$bind$1(LightRevealScrimViewModel lightRevealScrimViewModel, LightRevealScrim lightRevealScrim, Continuation<? super LightRevealScrimViewBinder$bind$1> continuation) {
        super(3, continuation);
        this.$viewModel = lightRevealScrimViewModel;
        this.$revealScrim = lightRevealScrim;
    }

    /* JADX DEBUG: Method merged with bridge method */
    public final Object invoke(LifecycleOwner lifecycleOwner, View view, Continuation<? super Unit> continuation) {
        LightRevealScrimViewBinder$bind$1 lightRevealScrimViewBinder$bind$1 = new LightRevealScrimViewBinder$bind$1(this.$viewModel, this.$revealScrim, continuation);
        lightRevealScrimViewBinder$bind$1.L$0 = lifecycleOwner;
        return lightRevealScrimViewBinder$bind$1.invokeSuspend(Unit.INSTANCE);
    }

    public final Object invokeSuspend(Object obj) {
        Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            LifecycleOwner lifecycleOwner = (LifecycleOwner) this.L$0;
            Lifecycle.State state = Lifecycle.State.CREATED;
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$viewModel, this.$revealScrim, null);
            this.label = 1;
            if (RepeatOnLifecycleKt.repeatOnLifecycle(lifecycleOwner, state, (Function2<? super CoroutineScope, ? super Continuation<? super Unit>, ? extends Object>) anonymousClass1, (Continuation<? super Unit>) this) == coroutine_suspended) {
                return coroutine_suspended;
            }
        } else if (i != 1) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        } else {
            ResultKt.throwOnFailure(obj);
        }
        return Unit.INSTANCE;
    }
}