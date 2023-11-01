package com.android.systemui.dreams;

import android.view.View;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.RepeatOnLifecycleKt;
import com.android.systemui.dreams.DreamOverlayAnimationsController;
import com.android.systemui.dreams.complication.ComplicationLayoutParams;
import com.android.systemui.keyguard.ui.viewmodel.DreamingToLockscreenTransitionViewModel;
import com.android.systemui.statusbar.policy.ConfigurationController;
import java.util.function.Consumer;
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
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.MutableStateFlow;
import kotlinx.coroutines.flow.StateFlowKt;

@DebugMetadata(c = "com.android.systemui.dreams.DreamOverlayAnimationsController$init$1", f = "DreamOverlayAnimationsController.kt", l = {96}, m = "invokeSuspend")
/* loaded from: mainsysui33.jar:com/android/systemui/dreams/DreamOverlayAnimationsController$init$1.class */
public final class DreamOverlayAnimationsController$init$1 extends SuspendLambda implements Function3<LifecycleOwner, View, Continuation<? super Unit>, Object> {
    public final /* synthetic */ View $view;
    private /* synthetic */ Object L$0;
    public int label;
    public final /* synthetic */ DreamOverlayAnimationsController this$0;

    @DebugMetadata(c = "com.android.systemui.dreams.DreamOverlayAnimationsController$init$1$1", f = "DreamOverlayAnimationsController.kt", l = {}, m = "invokeSuspend")
    /* renamed from: com.android.systemui.dreams.DreamOverlayAnimationsController$init$1$1  reason: invalid class name */
    /* loaded from: mainsysui33.jar:com/android/systemui/dreams/DreamOverlayAnimationsController$init$1$1.class */
    public static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        public final /* synthetic */ MutableStateFlow<DreamOverlayAnimationsController.ConfigurationBasedDimensions> $configurationBasedDimensions;
        private /* synthetic */ Object L$0;
        public int label;
        public final /* synthetic */ DreamOverlayAnimationsController this$0;

        @DebugMetadata(c = "com.android.systemui.dreams.DreamOverlayAnimationsController$init$1$1$1", f = "DreamOverlayAnimationsController.kt", l = {103}, m = "invokeSuspend")
        /* renamed from: com.android.systemui.dreams.DreamOverlayAnimationsController$init$1$1$1  reason: invalid class name and collision with other inner class name */
        /* loaded from: mainsysui33.jar:com/android/systemui/dreams/DreamOverlayAnimationsController$init$1$1$1.class */
        public static final class C00071 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            public final /* synthetic */ MutableStateFlow<DreamOverlayAnimationsController.ConfigurationBasedDimensions> $configurationBasedDimensions;
            public int label;
            public final /* synthetic */ DreamOverlayAnimationsController this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C00071(MutableStateFlow<DreamOverlayAnimationsController.ConfigurationBasedDimensions> mutableStateFlow, DreamOverlayAnimationsController dreamOverlayAnimationsController, Continuation<? super C00071> continuation) {
                super(2, continuation);
                this.$configurationBasedDimensions = mutableStateFlow;
                this.this$0 = dreamOverlayAnimationsController;
            }

            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new C00071(this.$configurationBasedDimensions, this.this$0, continuation);
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
                    Flow transformLatest = FlowKt.transformLatest(this.$configurationBasedDimensions, new DreamOverlayAnimationsController$init$1$1$1$invokeSuspend$$inlined$flatMapLatest$1(null, this.this$0));
                    final DreamOverlayAnimationsController dreamOverlayAnimationsController = this.this$0;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.dreams.DreamOverlayAnimationsController.init.1.1.1.2
                        public final Object emit(final float f, Continuation<? super Unit> continuation) {
                            final DreamOverlayAnimationsController dreamOverlayAnimationsController2 = DreamOverlayAnimationsController.this;
                            ComplicationLayoutParams.iteratePositions(new Consumer() { // from class: com.android.systemui.dreams.DreamOverlayAnimationsController.init.1.1.1.2.1
                                public final void accept(int i2) {
                                    DreamOverlayAnimationsController.this.setElementsTranslationYAtPosition(f, i2);
                                }

                                @Override // java.util.function.Consumer
                                public /* bridge */ /* synthetic */ void accept(Object obj2) {
                                    accept(((Number) obj2).intValue());
                                }
                            }, 3);
                            return Unit.INSTANCE;
                        }

                        public /* bridge */ /* synthetic */ Object emit(Object obj2, Continuation continuation) {
                            return emit(((Number) obj2).floatValue(), continuation);
                        }
                    };
                    this.label = 1;
                    if (transformLatest.collect(flowCollector, this) == coroutine_suspended) {
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

        @DebugMetadata(c = "com.android.systemui.dreams.DreamOverlayAnimationsController$init$1$1$2", f = "DreamOverlayAnimationsController.kt", l = {115}, m = "invokeSuspend")
        /* renamed from: com.android.systemui.dreams.DreamOverlayAnimationsController$init$1$1$2  reason: invalid class name */
        /* loaded from: mainsysui33.jar:com/android/systemui/dreams/DreamOverlayAnimationsController$init$1$1$2.class */
        public static final class AnonymousClass2 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            public int label;
            public final /* synthetic */ DreamOverlayAnimationsController this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass2(DreamOverlayAnimationsController dreamOverlayAnimationsController, Continuation<? super AnonymousClass2> continuation) {
                super(2, continuation);
                this.this$0 = dreamOverlayAnimationsController;
            }

            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new AnonymousClass2(this.this$0, continuation);
            }

            /* JADX DEBUG: Method merged with bridge method */
            public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
                return create(coroutineScope, continuation).invokeSuspend(Unit.INSTANCE);
            }

            public final Object invokeSuspend(Object obj) {
                DreamingToLockscreenTransitionViewModel dreamingToLockscreenTransitionViewModel;
                Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    dreamingToLockscreenTransitionViewModel = this.this$0.transitionViewModel;
                    Flow<Float> dreamOverlayAlpha = dreamingToLockscreenTransitionViewModel.getDreamOverlayAlpha();
                    final DreamOverlayAnimationsController dreamOverlayAnimationsController = this.this$0;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.dreams.DreamOverlayAnimationsController.init.1.1.2.1
                        public final Object emit(final float f, Continuation<? super Unit> continuation) {
                            final DreamOverlayAnimationsController dreamOverlayAnimationsController2 = DreamOverlayAnimationsController.this;
                            ComplicationLayoutParams.iteratePositions(new Consumer() { // from class: com.android.systemui.dreams.DreamOverlayAnimationsController.init.1.1.2.1.1
                                public final void accept(int i2) {
                                    DreamOverlayAnimationsController.this.setElementsAlphaAtPosition(f, i2, true);
                                }

                                @Override // java.util.function.Consumer
                                public /* bridge */ /* synthetic */ void accept(Object obj2) {
                                    accept(((Number) obj2).intValue());
                                }
                            }, 3);
                            return Unit.INSTANCE;
                        }

                        public /* bridge */ /* synthetic */ Object emit(Object obj2, Continuation continuation) {
                            return emit(((Number) obj2).floatValue(), continuation);
                        }
                    };
                    this.label = 1;
                    if (dreamOverlayAlpha.collect(flowCollector, this) == coroutine_suspended) {
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
        public AnonymousClass1(MutableStateFlow<DreamOverlayAnimationsController.ConfigurationBasedDimensions> mutableStateFlow, DreamOverlayAnimationsController dreamOverlayAnimationsController, Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
            this.$configurationBasedDimensions = mutableStateFlow;
            this.this$0 = dreamOverlayAnimationsController;
        }

        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$configurationBasedDimensions, this.this$0, continuation);
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
                BuildersKt.launch$default(coroutineScope, (CoroutineContext) null, (CoroutineStart) null, new C00071(this.$configurationBasedDimensions, this.this$0, null), 3, (Object) null);
                BuildersKt.launch$default(coroutineScope, (CoroutineContext) null, (CoroutineStart) null, new AnonymousClass2(this.this$0, null), 3, (Object) null);
                return Unit.INSTANCE;
            }
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DreamOverlayAnimationsController$init$1(DreamOverlayAnimationsController dreamOverlayAnimationsController, View view, Continuation<? super DreamOverlayAnimationsController$init$1> continuation) {
        super(3, continuation);
        this.this$0 = dreamOverlayAnimationsController;
        this.$view = view;
    }

    /* JADX DEBUG: Method merged with bridge method */
    public final Object invoke(LifecycleOwner lifecycleOwner, View view, Continuation<? super Unit> continuation) {
        DreamOverlayAnimationsController$init$1 dreamOverlayAnimationsController$init$1 = new DreamOverlayAnimationsController$init$1(this.this$0, this.$view, continuation);
        dreamOverlayAnimationsController$init$1.L$0 = lifecycleOwner;
        return dreamOverlayAnimationsController$init$1.invokeSuspend(Unit.INSTANCE);
    }

    public final Object invokeSuspend(Object obj) {
        DreamOverlayAnimationsController.ConfigurationBasedDimensions loadFromResources;
        DreamOverlayAnimationsController$init$1$configCallback$1 dreamOverlayAnimationsController$init$1$configCallback$1;
        ConfigurationController configurationController;
        ConfigurationController configurationController2;
        Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            LifecycleOwner lifecycleOwner = (LifecycleOwner) this.L$0;
            loadFromResources = this.this$0.loadFromResources(this.$view);
            final MutableStateFlow MutableStateFlow = StateFlowKt.MutableStateFlow(loadFromResources);
            final DreamOverlayAnimationsController dreamOverlayAnimationsController = this.this$0;
            final View view = this.$view;
            dreamOverlayAnimationsController$init$1$configCallback$1 = new ConfigurationController.ConfigurationListener() { // from class: com.android.systemui.dreams.DreamOverlayAnimationsController$init$1$configCallback$1
                public void onDensityOrFontScaleChanged() {
                    DreamOverlayAnimationsController.ConfigurationBasedDimensions loadFromResources2;
                    MutableStateFlow<DreamOverlayAnimationsController.ConfigurationBasedDimensions> mutableStateFlow = MutableStateFlow;
                    loadFromResources2 = dreamOverlayAnimationsController.loadFromResources(view);
                    mutableStateFlow.setValue(loadFromResources2);
                }
            };
            configurationController = this.this$0.configController;
            configurationController.addCallback(dreamOverlayAnimationsController$init$1$configCallback$1);
            Lifecycle.State state = Lifecycle.State.CREATED;
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(MutableStateFlow, this.this$0, null);
            this.L$0 = dreamOverlayAnimationsController$init$1$configCallback$1;
            this.label = 1;
            if (RepeatOnLifecycleKt.repeatOnLifecycle(lifecycleOwner, state, (Function2<? super CoroutineScope, ? super Continuation<? super Unit>, ? extends Object>) anonymousClass1, (Continuation<? super Unit>) this) == coroutine_suspended) {
                return coroutine_suspended;
            }
        } else if (i != 1) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        } else {
            ResultKt.throwOnFailure(obj);
            dreamOverlayAnimationsController$init$1$configCallback$1 = (DreamOverlayAnimationsController$init$1$configCallback$1) this.L$0;
        }
        configurationController2 = this.this$0.configController;
        configurationController2.removeCallback(dreamOverlayAnimationsController$init$1$configCallback$1);
        return Unit.INSTANCE;
    }
}