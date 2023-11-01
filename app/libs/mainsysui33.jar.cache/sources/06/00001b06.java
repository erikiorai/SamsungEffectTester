package com.android.systemui.keyguard.ui.binder;

import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.R$styleable;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.RepeatOnLifecycleKt;
import androidx.recyclerview.widget.RecyclerView;
import com.android.internal.policy.SystemBarUtils;
import com.android.keyguard.KeyguardHostViewController;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.settingslib.widget.ActionBarShadowController;
import com.android.systemui.keyguard.shared.model.BouncerShowMessageModel;
import com.android.systemui.keyguard.shared.model.KeyguardBouncerModel;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardBouncerViewModel;
import kotlin.KotlinNothingValueException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineStart;
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

@DebugMetadata(c = "com.android.systemui.keyguard.ui.binder.KeyguardBouncerViewBinder$bind$1", f = "KeyguardBouncerViewBinder.kt", l = {91}, m = "invokeSuspend")
/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/ui/binder/KeyguardBouncerViewBinder$bind$1.class */
public final class KeyguardBouncerViewBinder$bind$1 extends SuspendLambda implements Function3<LifecycleOwner, View, Continuation<? super Unit>, Object> {
    public final /* synthetic */ KeyguardBouncerViewBinder$bind$delegate$1 $delegate;
    public final /* synthetic */ KeyguardHostViewController $hostViewController;
    public final /* synthetic */ ViewGroup $view;
    public final /* synthetic */ KeyguardBouncerViewModel $viewModel;
    private /* synthetic */ Object L$0;
    public int label;

    @DebugMetadata(c = "com.android.systemui.keyguard.ui.binder.KeyguardBouncerViewBinder$bind$1$1", f = "KeyguardBouncerViewBinder.kt", l = {207}, m = "invokeSuspend")
    /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardBouncerViewBinder$bind$1$1  reason: invalid class name */
    /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/ui/binder/KeyguardBouncerViewBinder$bind$1$1.class */
    public static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        public final /* synthetic */ KeyguardBouncerViewBinder$bind$delegate$1 $delegate;
        public final /* synthetic */ KeyguardHostViewController $hostViewController;
        public final /* synthetic */ ViewGroup $view;
        public final /* synthetic */ KeyguardBouncerViewModel $viewModel;
        private /* synthetic */ Object L$0;
        public int label;

        @DebugMetadata(c = "com.android.systemui.keyguard.ui.binder.KeyguardBouncerViewBinder$bind$1$1$1", f = "KeyguardBouncerViewBinder.kt", l = {95}, m = "invokeSuspend")
        /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardBouncerViewBinder$bind$1$1$1  reason: invalid class name and collision with other inner class name */
        /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/ui/binder/KeyguardBouncerViewBinder$bind$1$1$1.class */
        public static final class C00201 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            public final /* synthetic */ KeyguardHostViewController $hostViewController;
            public final /* synthetic */ ViewGroup $view;
            public final /* synthetic */ KeyguardBouncerViewModel $viewModel;
            public int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C00201(KeyguardBouncerViewModel keyguardBouncerViewModel, KeyguardHostViewController keyguardHostViewController, ViewGroup viewGroup, Continuation<? super C00201> continuation) {
                super(2, continuation);
                this.$viewModel = keyguardBouncerViewModel;
                this.$hostViewController = keyguardHostViewController;
                this.$view = viewGroup;
            }

            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new C00201(this.$viewModel, this.$hostViewController, this.$view, continuation);
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
                    Flow<KeyguardBouncerModel> show = this.$viewModel.getShow();
                    final KeyguardHostViewController keyguardHostViewController = this.$hostViewController;
                    final ViewGroup viewGroup = this.$view;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardBouncerViewBinder.bind.1.1.1.1
                        public final Object emit(KeyguardBouncerModel keyguardBouncerModel, Continuation<? super Unit> continuation) {
                            KeyguardHostViewController.this.showPromptReason(keyguardBouncerModel.getPromptReason());
                            CharSequence errorMessage = keyguardBouncerModel.getErrorMessage();
                            if (errorMessage != null) {
                                KeyguardHostViewController.this.showErrorMessage(errorMessage);
                            }
                            KeyguardHostViewController.this.showPrimarySecurityScreen();
                            KeyguardHostViewController.this.appear(SystemBarUtils.getStatusBarHeight(viewGroup.getContext()));
                            return Unit.INSTANCE;
                        }

                        public /* bridge */ /* synthetic */ Object emit(Object obj2, Continuation continuation) {
                            return emit((KeyguardBouncerModel) obj2, (Continuation<? super Unit>) continuation);
                        }
                    };
                    this.label = 1;
                    if (show.collect(flowCollector, this) == coroutine_suspended) {
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

        @DebugMetadata(c = "com.android.systemui.keyguard.ui.binder.KeyguardBouncerViewBinder$bind$1$1$10", f = "KeyguardBouncerViewBinder.kt", l = {168}, m = "invokeSuspend")
        /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardBouncerViewBinder$bind$1$1$10  reason: invalid class name */
        /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/ui/binder/KeyguardBouncerViewBinder$bind$1$1$10.class */
        public static final class AnonymousClass10 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            public final /* synthetic */ KeyguardHostViewController $hostViewController;
            public final /* synthetic */ KeyguardBouncerViewModel $viewModel;
            public int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass10(KeyguardBouncerViewModel keyguardBouncerViewModel, KeyguardHostViewController keyguardHostViewController, Continuation<? super AnonymousClass10> continuation) {
                super(2, continuation);
                this.$viewModel = keyguardBouncerViewModel;
                this.$hostViewController = keyguardHostViewController;
            }

            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new AnonymousClass10(this.$viewModel, this.$hostViewController, continuation);
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
                    Flow<Float> keyguardPosition = this.$viewModel.getKeyguardPosition();
                    final KeyguardHostViewController keyguardHostViewController = this.$hostViewController;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardBouncerViewBinder.bind.1.1.10.1
                        public final Object emit(float f, Continuation<? super Unit> continuation) {
                            KeyguardHostViewController.this.updateKeyguardPosition(f);
                            return Unit.INSTANCE;
                        }

                        public /* bridge */ /* synthetic */ Object emit(Object obj2, Continuation continuation) {
                            return emit(((Number) obj2).floatValue(), continuation);
                        }
                    };
                    this.label = 1;
                    if (keyguardPosition.collect(flowCollector, this) == coroutine_suspended) {
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

        @DebugMetadata(c = "com.android.systemui.keyguard.ui.binder.KeyguardBouncerViewBinder$bind$1$1$11", f = "KeyguardBouncerViewBinder.kt", l = {174}, m = "invokeSuspend")
        /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardBouncerViewBinder$bind$1$1$11  reason: invalid class name */
        /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/ui/binder/KeyguardBouncerViewBinder$bind$1$1$11.class */
        public static final class AnonymousClass11 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            public final /* synthetic */ KeyguardHostViewController $hostViewController;
            public final /* synthetic */ KeyguardBouncerViewModel $viewModel;
            public int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass11(KeyguardBouncerViewModel keyguardBouncerViewModel, KeyguardHostViewController keyguardHostViewController, Continuation<? super AnonymousClass11> continuation) {
                super(2, continuation);
                this.$viewModel = keyguardBouncerViewModel;
                this.$hostViewController = keyguardHostViewController;
            }

            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new AnonymousClass11(this.$viewModel, this.$hostViewController, continuation);
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
                    Flow<Boolean> updateResources = this.$viewModel.getUpdateResources();
                    final KeyguardHostViewController keyguardHostViewController = this.$hostViewController;
                    final KeyguardBouncerViewModel keyguardBouncerViewModel = this.$viewModel;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardBouncerViewBinder.bind.1.1.11.1
                        public /* bridge */ /* synthetic */ Object emit(Object obj2, Continuation continuation) {
                            return emit(((Boolean) obj2).booleanValue(), continuation);
                        }

                        public final Object emit(boolean z, Continuation<? super Unit> continuation) {
                            KeyguardHostViewController.this.updateResources();
                            keyguardBouncerViewModel.notifyUpdateResources();
                            return Unit.INSTANCE;
                        }
                    };
                    this.label = 1;
                    if (updateResources.collect(flowCollector, this) == coroutine_suspended) {
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

        @DebugMetadata(c = "com.android.systemui.keyguard.ui.binder.KeyguardBouncerViewBinder$bind$1$1$12", f = "KeyguardBouncerViewBinder.kt", l = {181}, m = "invokeSuspend")
        /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardBouncerViewBinder$bind$1$1$12  reason: invalid class name */
        /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/ui/binder/KeyguardBouncerViewBinder$bind$1$1$12.class */
        public static final class AnonymousClass12 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            public final /* synthetic */ KeyguardHostViewController $hostViewController;
            public final /* synthetic */ KeyguardBouncerViewModel $viewModel;
            public int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass12(KeyguardBouncerViewModel keyguardBouncerViewModel, KeyguardHostViewController keyguardHostViewController, Continuation<? super AnonymousClass12> continuation) {
                super(2, continuation);
                this.$viewModel = keyguardBouncerViewModel;
                this.$hostViewController = keyguardHostViewController;
            }

            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new AnonymousClass12(this.$viewModel, this.$hostViewController, continuation);
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
                    Flow<BouncerShowMessageModel> bouncerShowMessage = this.$viewModel.getBouncerShowMessage();
                    final KeyguardHostViewController keyguardHostViewController = this.$hostViewController;
                    final KeyguardBouncerViewModel keyguardBouncerViewModel = this.$viewModel;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardBouncerViewBinder.bind.1.1.12.1
                        public final Object emit(BouncerShowMessageModel bouncerShowMessageModel, Continuation<? super Unit> continuation) {
                            KeyguardHostViewController.this.showMessage(bouncerShowMessageModel.getMessage(), bouncerShowMessageModel.getColorStateList());
                            keyguardBouncerViewModel.onMessageShown();
                            return Unit.INSTANCE;
                        }

                        public /* bridge */ /* synthetic */ Object emit(Object obj2, Continuation continuation) {
                            return emit((BouncerShowMessageModel) obj2, (Continuation<? super Unit>) continuation);
                        }
                    };
                    this.label = 1;
                    if (bouncerShowMessage.collect(flowCollector, this) == coroutine_suspended) {
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

        @DebugMetadata(c = "com.android.systemui.keyguard.ui.binder.KeyguardBouncerViewBinder$bind$1$1$13", f = "KeyguardBouncerViewBinder.kt", l = {188}, m = "invokeSuspend")
        /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardBouncerViewBinder$bind$1$1$13  reason: invalid class name */
        /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/ui/binder/KeyguardBouncerViewBinder$bind$1$1$13.class */
        public static final class AnonymousClass13 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            public final /* synthetic */ KeyguardHostViewController $hostViewController;
            public final /* synthetic */ KeyguardBouncerViewModel $viewModel;
            public int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass13(KeyguardBouncerViewModel keyguardBouncerViewModel, KeyguardHostViewController keyguardHostViewController, Continuation<? super AnonymousClass13> continuation) {
                super(2, continuation);
                this.$viewModel = keyguardBouncerViewModel;
                this.$hostViewController = keyguardHostViewController;
            }

            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new AnonymousClass13(this.$viewModel, this.$hostViewController, continuation);
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
                    Flow<Boolean> keyguardAuthenticated = this.$viewModel.getKeyguardAuthenticated();
                    final KeyguardHostViewController keyguardHostViewController = this.$hostViewController;
                    final KeyguardBouncerViewModel keyguardBouncerViewModel = this.$viewModel;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardBouncerViewBinder.bind.1.1.13.1
                        public /* bridge */ /* synthetic */ Object emit(Object obj2, Continuation continuation) {
                            return emit(((Boolean) obj2).booleanValue(), continuation);
                        }

                        public final Object emit(boolean z, Continuation<? super Unit> continuation) {
                            KeyguardHostViewController.this.finish(z, KeyguardUpdateMonitor.getCurrentUser());
                            keyguardBouncerViewModel.notifyKeyguardAuthenticated();
                            return Unit.INSTANCE;
                        }
                    };
                    this.label = 1;
                    if (keyguardAuthenticated.collect(flowCollector, this) == coroutine_suspended) {
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

        @DebugMetadata(c = "com.android.systemui.keyguard.ui.binder.KeyguardBouncerViewBinder$bind$1$1$14", f = "KeyguardBouncerViewBinder.kt", l = {197}, m = "invokeSuspend")
        /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardBouncerViewBinder$bind$1$1$14  reason: invalid class name */
        /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/ui/binder/KeyguardBouncerViewBinder$bind$1$1$14.class */
        public static final class AnonymousClass14 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            public final /* synthetic */ ViewGroup $view;
            public final /* synthetic */ KeyguardBouncerViewModel $viewModel;
            public int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass14(KeyguardBouncerViewModel keyguardBouncerViewModel, ViewGroup viewGroup, Continuation<? super AnonymousClass14> continuation) {
                super(2, continuation);
                this.$viewModel = keyguardBouncerViewModel;
                this.$view = viewGroup;
            }

            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new AnonymousClass14(this.$viewModel, this.$view, continuation);
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
                    KeyguardBouncerViewModel keyguardBouncerViewModel = this.$viewModel;
                    final ViewGroup viewGroup = this.$view;
                    Flow<Integer> observeOnIsBackButtonEnabled = keyguardBouncerViewModel.observeOnIsBackButtonEnabled(new Function0<Integer>() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardBouncerViewBinder.bind.1.1.14.1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(0);
                        }

                        /* JADX DEBUG: Method merged with bridge method */
                        /* renamed from: invoke */
                        public final Integer m3079invoke() {
                            return Integer.valueOf(viewGroup.getSystemUiVisibility());
                        }
                    });
                    final ViewGroup viewGroup2 = this.$view;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardBouncerViewBinder.bind.1.1.14.2
                        public final Object emit(int i2, Continuation<? super Unit> continuation) {
                            viewGroup2.setSystemUiVisibility(i2);
                            return Unit.INSTANCE;
                        }

                        public /* bridge */ /* synthetic */ Object emit(Object obj2, Continuation continuation) {
                            return emit(((Number) obj2).intValue(), continuation);
                        }
                    };
                    this.label = 1;
                    if (observeOnIsBackButtonEnabled.collect(flowCollector, this) == coroutine_suspended) {
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

        @DebugMetadata(c = "com.android.systemui.keyguard.ui.binder.KeyguardBouncerViewBinder$bind$1$1$15", f = "KeyguardBouncerViewBinder.kt", l = {201}, m = "invokeSuspend")
        /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardBouncerViewBinder$bind$1$1$15  reason: invalid class name */
        /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/ui/binder/KeyguardBouncerViewBinder$bind$1$1$15.class */
        public static final class AnonymousClass15 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            public final /* synthetic */ KeyguardHostViewController $hostViewController;
            public final /* synthetic */ ViewGroup $view;
            public final /* synthetic */ KeyguardBouncerViewModel $viewModel;
            public int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass15(KeyguardBouncerViewModel keyguardBouncerViewModel, ViewGroup viewGroup, KeyguardHostViewController keyguardHostViewController, Continuation<? super AnonymousClass15> continuation) {
                super(2, continuation);
                this.$viewModel = keyguardBouncerViewModel;
                this.$view = viewGroup;
                this.$hostViewController = keyguardHostViewController;
            }

            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new AnonymousClass15(this.$viewModel, this.$view, this.$hostViewController, continuation);
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
                    Flow<Unit> screenTurnedOff = this.$viewModel.getScreenTurnedOff();
                    final ViewGroup viewGroup = this.$view;
                    final KeyguardHostViewController keyguardHostViewController = this.$hostViewController;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardBouncerViewBinder.bind.1.1.15.1
                        public /* bridge */ /* synthetic */ Object emit(Object obj2, Continuation continuation) {
                            return emit((Unit) obj2, (Continuation<? super Unit>) continuation);
                        }

                        public final Object emit(Unit unit, Continuation<? super Unit> continuation) {
                            if (viewGroup.getVisibility() == 0) {
                                keyguardHostViewController.onPause();
                            }
                            return Unit.INSTANCE;
                        }
                    };
                    this.label = 1;
                    if (screenTurnedOff.collect(flowCollector, this) == coroutine_suspended) {
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

        @DebugMetadata(c = "com.android.systemui.keyguard.ui.binder.KeyguardBouncerViewBinder$bind$1$1$2", f = "KeyguardBouncerViewBinder.kt", l = {108}, m = "invokeSuspend")
        /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardBouncerViewBinder$bind$1$1$2  reason: invalid class name */
        /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/ui/binder/KeyguardBouncerViewBinder$bind$1$1$2.class */
        public static final class AnonymousClass2 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            public final /* synthetic */ KeyguardHostViewController $hostViewController;
            public final /* synthetic */ KeyguardBouncerViewModel $viewModel;
            public int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass2(KeyguardBouncerViewModel keyguardBouncerViewModel, KeyguardHostViewController keyguardHostViewController, Continuation<? super AnonymousClass2> continuation) {
                super(2, continuation);
                this.$viewModel = keyguardBouncerViewModel;
                this.$hostViewController = keyguardHostViewController;
            }

            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new AnonymousClass2(this.$viewModel, this.$hostViewController, continuation);
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
                    Flow<KeyguardBouncerModel> showWithFullExpansion = this.$viewModel.getShowWithFullExpansion();
                    final KeyguardHostViewController keyguardHostViewController = this.$hostViewController;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardBouncerViewBinder.bind.1.1.2.1
                        public final Object emit(KeyguardBouncerModel keyguardBouncerModel, Continuation<? super Unit> continuation) {
                            KeyguardHostViewController.this.resetSecurityContainer();
                            KeyguardHostViewController.this.showPromptReason(keyguardBouncerModel.getPromptReason());
                            KeyguardHostViewController.this.onResume();
                            return Unit.INSTANCE;
                        }

                        public /* bridge */ /* synthetic */ Object emit(Object obj2, Continuation continuation) {
                            return emit((KeyguardBouncerModel) obj2, (Continuation<? super Unit>) continuation);
                        }
                    };
                    this.label = 1;
                    if (showWithFullExpansion.collect(flowCollector, this) == coroutine_suspended) {
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

        @DebugMetadata(c = "com.android.systemui.keyguard.ui.binder.KeyguardBouncerViewBinder$bind$1$1$3", f = "KeyguardBouncerViewBinder.kt", l = {116}, m = "invokeSuspend")
        /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardBouncerViewBinder$bind$1$1$3  reason: invalid class name */
        /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/ui/binder/KeyguardBouncerViewBinder$bind$1$1$3.class */
        public static final class AnonymousClass3 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            public final /* synthetic */ KeyguardHostViewController $hostViewController;
            public final /* synthetic */ KeyguardBouncerViewModel $viewModel;
            public int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass3(KeyguardBouncerViewModel keyguardBouncerViewModel, KeyguardHostViewController keyguardHostViewController, Continuation<? super AnonymousClass3> continuation) {
                super(2, continuation);
                this.$viewModel = keyguardBouncerViewModel;
                this.$hostViewController = keyguardHostViewController;
            }

            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new AnonymousClass3(this.$viewModel, this.$hostViewController, continuation);
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
                    Flow<Unit> hide = this.$viewModel.getHide();
                    final KeyguardHostViewController keyguardHostViewController = this.$hostViewController;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardBouncerViewBinder.bind.1.1.3.1
                        public /* bridge */ /* synthetic */ Object emit(Object obj2, Continuation continuation) {
                            return emit((Unit) obj2, (Continuation<? super Unit>) continuation);
                        }

                        public final Object emit(Unit unit, Continuation<? super Unit> continuation) {
                            KeyguardHostViewController.this.cancelDismissAction();
                            KeyguardHostViewController.this.cleanUp();
                            KeyguardHostViewController.this.resetSecurityContainer();
                            return Unit.INSTANCE;
                        }
                    };
                    this.label = 1;
                    if (hide.collect(flowCollector, this) == coroutine_suspended) {
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

        @DebugMetadata(c = "com.android.systemui.keyguard.ui.binder.KeyguardBouncerViewBinder$bind$1$1$4", f = "KeyguardBouncerViewBinder.kt", l = {R$styleable.AppCompatTheme_windowMinWidthMajor}, m = "invokeSuspend")
        /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardBouncerViewBinder$bind$1$1$4  reason: invalid class name */
        /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/ui/binder/KeyguardBouncerViewBinder$bind$1$1$4.class */
        public static final class AnonymousClass4 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            public final /* synthetic */ KeyguardHostViewController $hostViewController;
            public final /* synthetic */ KeyguardBouncerViewModel $viewModel;
            public int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass4(KeyguardBouncerViewModel keyguardBouncerViewModel, KeyguardHostViewController keyguardHostViewController, Continuation<? super AnonymousClass4> continuation) {
                super(2, continuation);
                this.$viewModel = keyguardBouncerViewModel;
                this.$hostViewController = keyguardHostViewController;
            }

            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new AnonymousClass4(this.$viewModel, this.$hostViewController, continuation);
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
                    Flow<Unit> startingToHide = this.$viewModel.getStartingToHide();
                    final KeyguardHostViewController keyguardHostViewController = this.$hostViewController;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardBouncerViewBinder.bind.1.1.4.1
                        public /* bridge */ /* synthetic */ Object emit(Object obj2, Continuation continuation) {
                            return emit((Unit) obj2, (Continuation<? super Unit>) continuation);
                        }

                        public final Object emit(Unit unit, Continuation<? super Unit> continuation) {
                            KeyguardHostViewController.this.onStartingToHide();
                            return Unit.INSTANCE;
                        }
                    };
                    this.label = 1;
                    if (startingToHide.collect(flowCollector, this) == coroutine_suspended) {
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

        @DebugMetadata(c = "com.android.systemui.keyguard.ui.binder.KeyguardBouncerViewBinder$bind$1$1$5", f = "KeyguardBouncerViewBinder.kt", l = {RecyclerView.ViewHolder.FLAG_IGNORE}, m = "invokeSuspend")
        /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardBouncerViewBinder$bind$1$1$5  reason: invalid class name */
        /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/ui/binder/KeyguardBouncerViewBinder$bind$1$1$5.class */
        public static final class AnonymousClass5 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            public final /* synthetic */ KeyguardHostViewController $hostViewController;
            public final /* synthetic */ KeyguardBouncerViewModel $viewModel;
            public int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass5(KeyguardBouncerViewModel keyguardBouncerViewModel, KeyguardHostViewController keyguardHostViewController, Continuation<? super AnonymousClass5> continuation) {
                super(2, continuation);
                this.$viewModel = keyguardBouncerViewModel;
                this.$hostViewController = keyguardHostViewController;
            }

            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new AnonymousClass5(this.$viewModel, this.$hostViewController, continuation);
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
                    Flow<Runnable> startDisappearAnimation = this.$viewModel.getStartDisappearAnimation();
                    final KeyguardHostViewController keyguardHostViewController = this.$hostViewController;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardBouncerViewBinder.bind.1.1.5.1
                        public /* bridge */ /* synthetic */ Object emit(Object obj2, Continuation continuation) {
                            return emit((Runnable) obj2, (Continuation<? super Unit>) continuation);
                        }

                        public final Object emit(Runnable runnable, Continuation<? super Unit> continuation) {
                            KeyguardHostViewController.this.startDisappearAnimation(runnable);
                            return Unit.INSTANCE;
                        }
                    };
                    this.label = 1;
                    if (startDisappearAnimation.collect(flowCollector, this) == coroutine_suspended) {
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

        @DebugMetadata(c = "com.android.systemui.keyguard.ui.binder.KeyguardBouncerViewBinder$bind$1$1$6", f = "KeyguardBouncerViewBinder.kt", l = {134}, m = "invokeSuspend")
        /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardBouncerViewBinder$bind$1$1$6  reason: invalid class name */
        /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/ui/binder/KeyguardBouncerViewBinder$bind$1$1$6.class */
        public static final class AnonymousClass6 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            public final /* synthetic */ KeyguardHostViewController $hostViewController;
            public final /* synthetic */ KeyguardBouncerViewModel $viewModel;
            public int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass6(KeyguardBouncerViewModel keyguardBouncerViewModel, KeyguardHostViewController keyguardHostViewController, Continuation<? super AnonymousClass6> continuation) {
                super(2, continuation);
                this.$viewModel = keyguardBouncerViewModel;
                this.$hostViewController = keyguardHostViewController;
            }

            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new AnonymousClass6(this.$viewModel, this.$hostViewController, continuation);
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
                    Flow<Float> bouncerExpansionAmount = this.$viewModel.getBouncerExpansionAmount();
                    final KeyguardHostViewController keyguardHostViewController = this.$hostViewController;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardBouncerViewBinder.bind.1.1.6.1
                        public final Object emit(float f, Continuation<? super Unit> continuation) {
                            KeyguardHostViewController.this.setExpansion(f);
                            return Unit.INSTANCE;
                        }

                        public /* bridge */ /* synthetic */ Object emit(Object obj2, Continuation continuation) {
                            return emit(((Number) obj2).floatValue(), continuation);
                        }
                    };
                    this.label = 1;
                    if (bouncerExpansionAmount.collect(flowCollector, this) == coroutine_suspended) {
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

        @DebugMetadata(c = "com.android.systemui.keyguard.ui.binder.KeyguardBouncerViewBinder$bind$1$1$7", f = "KeyguardBouncerViewBinder.kt", l = {142}, m = "invokeSuspend")
        /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardBouncerViewBinder$bind$1$1$7  reason: invalid class name */
        /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/ui/binder/KeyguardBouncerViewBinder$bind$1$1$7.class */
        public static final class AnonymousClass7 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            public final /* synthetic */ KeyguardHostViewController $hostViewController;
            public final /* synthetic */ ViewGroup $view;
            public final /* synthetic */ KeyguardBouncerViewModel $viewModel;
            public int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass7(KeyguardBouncerViewModel keyguardBouncerViewModel, KeyguardHostViewController keyguardHostViewController, ViewGroup viewGroup, Continuation<? super AnonymousClass7> continuation) {
                super(2, continuation);
                this.$viewModel = keyguardBouncerViewModel;
                this.$hostViewController = keyguardHostViewController;
                this.$view = viewGroup;
            }

            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new AnonymousClass7(this.$viewModel, this.$hostViewController, this.$view, continuation);
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
                    final Flow<Float> bouncerExpansionAmount = this.$viewModel.getBouncerExpansionAmount();
                    Flow<Float> flow = new Flow<Float>() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardBouncerViewBinder$bind$1$1$7$invokeSuspend$$inlined$filter$1

                        /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardBouncerViewBinder$bind$1$1$7$invokeSuspend$$inlined$filter$1$2  reason: invalid class name */
                        /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/ui/binder/KeyguardBouncerViewBinder$bind$1$1$7$invokeSuspend$$inlined$filter$1$2.class */
                        public static final class AnonymousClass2<T> implements FlowCollector {
                            public final /* synthetic */ FlowCollector $this_unsafeFlow;

                            @DebugMetadata(c = "com.android.systemui.keyguard.ui.binder.KeyguardBouncerViewBinder$bind$1$1$7$invokeSuspend$$inlined$filter$1$2", f = "KeyguardBouncerViewBinder.kt", l = {223}, m = "emit")
                            /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardBouncerViewBinder$bind$1$1$7$invokeSuspend$$inlined$filter$1$2$1  reason: invalid class name */
                            /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/ui/binder/KeyguardBouncerViewBinder$bind$1$1$7$invokeSuspend$$inlined$filter$1$2$1.class */
                            public static final class AnonymousClass1 extends ContinuationImpl {
                                public Object L$0;
                                public Object L$1;
                                public int label;
                                public /* synthetic */ Object result;

                                public AnonymousClass1(Continuation continuation) {
                                    super(continuation);
                                }

                                public final Object invokeSuspend(Object obj) {
                                    this.result = obj;
                                    this.label |= Integer.MIN_VALUE;
                                    return AnonymousClass2.this.emit(null, this);
                                }
                            }

                            public AnonymousClass2(FlowCollector flowCollector) {
                                this.$this_unsafeFlow = flowCollector;
                            }

                            /* JADX WARN: Removed duplicated region for block: B:10:0x0047  */
                            /* JADX WARN: Removed duplicated region for block: B:15:0x005f  */
                            /*
                                Code decompiled incorrectly, please refer to instructions dump.
                            */
                            public final Object emit(Object obj, Continuation continuation) {
                                AnonymousClass1 anonymousClass1;
                                int i;
                                if (continuation instanceof AnonymousClass1) {
                                    AnonymousClass1 anonymousClass12 = (AnonymousClass1) continuation;
                                    int i2 = anonymousClass12.label;
                                    if ((i2 & Integer.MIN_VALUE) != 0) {
                                        anonymousClass12.label = i2 - 2147483648;
                                        anonymousClass1 = anonymousClass12;
                                        Object obj2 = anonymousClass1.result;
                                        Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
                                        i = anonymousClass1.label;
                                        if (i != 0) {
                                            ResultKt.throwOnFailure(obj2);
                                            FlowCollector flowCollector = this.$this_unsafeFlow;
                                            if (((Number) obj).floatValue() == ActionBarShadowController.ELEVATION_LOW) {
                                                anonymousClass1.label = 1;
                                                if (flowCollector.emit(obj, anonymousClass1) == coroutine_suspended) {
                                                    return coroutine_suspended;
                                                }
                                            }
                                        } else if (i != 1) {
                                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                        } else {
                                            ResultKt.throwOnFailure(obj2);
                                        }
                                        return Unit.INSTANCE;
                                    }
                                }
                                anonymousClass1 = new AnonymousClass1(continuation);
                                Object obj22 = anonymousClass1.result;
                                Object coroutine_suspended2 = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
                                i = anonymousClass1.label;
                                if (i != 0) {
                                }
                                return Unit.INSTANCE;
                            }
                        }

                        public Object collect(FlowCollector flowCollector, Continuation continuation) {
                            Object collect = bouncerExpansionAmount.collect(new AnonymousClass2(flowCollector), continuation);
                            return collect == IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED() ? collect : Unit.INSTANCE;
                        }
                    };
                    final KeyguardHostViewController keyguardHostViewController = this.$hostViewController;
                    final ViewGroup viewGroup = this.$view;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardBouncerViewBinder.bind.1.1.7.2
                        public final Object emit(float f, Continuation<? super Unit> continuation) {
                            KeyguardHostViewController.this.onResume();
                            viewGroup.announceForAccessibility(KeyguardHostViewController.this.getAccessibilityTitleForCurrentMode());
                            return Unit.INSTANCE;
                        }

                        public /* bridge */ /* synthetic */ Object emit(Object obj2, Continuation continuation) {
                            return emit(((Number) obj2).floatValue(), continuation);
                        }
                    };
                    this.label = 1;
                    if (flow.collect(flowCollector, this) == coroutine_suspended) {
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

        @DebugMetadata(c = "com.android.systemui.keyguard.ui.binder.KeyguardBouncerViewBinder$bind$1$1$8", f = "KeyguardBouncerViewBinder.kt", l = {151}, m = "invokeSuspend")
        /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardBouncerViewBinder$bind$1$1$8  reason: invalid class name */
        /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/ui/binder/KeyguardBouncerViewBinder$bind$1$1$8.class */
        public static final class AnonymousClass8 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            public final /* synthetic */ KeyguardHostViewController $hostViewController;
            public final /* synthetic */ ViewGroup $view;
            public final /* synthetic */ KeyguardBouncerViewModel $viewModel;
            public int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass8(KeyguardBouncerViewModel keyguardBouncerViewModel, ViewGroup viewGroup, KeyguardHostViewController keyguardHostViewController, Continuation<? super AnonymousClass8> continuation) {
                super(2, continuation);
                this.$viewModel = keyguardBouncerViewModel;
                this.$view = viewGroup;
                this.$hostViewController = keyguardHostViewController;
            }

            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new AnonymousClass8(this.$viewModel, this.$view, this.$hostViewController, continuation);
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
                    Flow<Boolean> isBouncerVisible = this.$viewModel.isBouncerVisible();
                    final ViewGroup viewGroup = this.$view;
                    final KeyguardHostViewController keyguardHostViewController = this.$hostViewController;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardBouncerViewBinder.bind.1.1.8.1
                        public /* bridge */ /* synthetic */ Object emit(Object obj2, Continuation continuation) {
                            return emit(((Boolean) obj2).booleanValue(), continuation);
                        }

                        public final Object emit(boolean z, Continuation<? super Unit> continuation) {
                            int i2 = z ? 0 : 4;
                            viewGroup.setVisibility(i2);
                            keyguardHostViewController.onBouncerVisibilityChanged(i2);
                            return Unit.INSTANCE;
                        }
                    };
                    this.label = 1;
                    if (isBouncerVisible.collect(flowCollector, this) == coroutine_suspended) {
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

        @DebugMetadata(c = "com.android.systemui.keyguard.ui.binder.KeyguardBouncerViewBinder$bind$1$1$9", f = "KeyguardBouncerViewBinder.kt", l = {161}, m = "invokeSuspend")
        /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardBouncerViewBinder$bind$1$1$9  reason: invalid class name */
        /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/ui/binder/KeyguardBouncerViewBinder$bind$1$1$9.class */
        public static final class AnonymousClass9 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            public final /* synthetic */ KeyguardHostViewController $hostViewController;
            public final /* synthetic */ KeyguardBouncerViewModel $viewModel;
            public int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass9(KeyguardBouncerViewModel keyguardBouncerViewModel, KeyguardHostViewController keyguardHostViewController, Continuation<? super AnonymousClass9> continuation) {
                super(2, continuation);
                this.$viewModel = keyguardBouncerViewModel;
                this.$hostViewController = keyguardHostViewController;
            }

            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new AnonymousClass9(this.$viewModel, this.$hostViewController, continuation);
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
                    final Flow<Boolean> isBouncerVisible = this.$viewModel.isBouncerVisible();
                    Flow<Boolean> flow = new Flow<Boolean>() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardBouncerViewBinder$bind$1$1$9$invokeSuspend$$inlined$filter$1

                        /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardBouncerViewBinder$bind$1$1$9$invokeSuspend$$inlined$filter$1$2  reason: invalid class name */
                        /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/ui/binder/KeyguardBouncerViewBinder$bind$1$1$9$invokeSuspend$$inlined$filter$1$2.class */
                        public static final class AnonymousClass2<T> implements FlowCollector {
                            public final /* synthetic */ FlowCollector $this_unsafeFlow;

                            @DebugMetadata(c = "com.android.systemui.keyguard.ui.binder.KeyguardBouncerViewBinder$bind$1$1$9$invokeSuspend$$inlined$filter$1$2", f = "KeyguardBouncerViewBinder.kt", l = {223}, m = "emit")
                            /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardBouncerViewBinder$bind$1$1$9$invokeSuspend$$inlined$filter$1$2$1  reason: invalid class name */
                            /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/ui/binder/KeyguardBouncerViewBinder$bind$1$1$9$invokeSuspend$$inlined$filter$1$2$1.class */
                            public static final class AnonymousClass1 extends ContinuationImpl {
                                public Object L$0;
                                public Object L$1;
                                public int label;
                                public /* synthetic */ Object result;

                                public AnonymousClass1(Continuation continuation) {
                                    super(continuation);
                                }

                                public final Object invokeSuspend(Object obj) {
                                    this.result = obj;
                                    this.label |= Integer.MIN_VALUE;
                                    return AnonymousClass2.this.emit(null, this);
                                }
                            }

                            public AnonymousClass2(FlowCollector flowCollector) {
                                this.$this_unsafeFlow = flowCollector;
                            }

                            /* JADX WARN: Removed duplicated region for block: B:10:0x0047  */
                            /* JADX WARN: Removed duplicated region for block: B:15:0x005f  */
                            /*
                                Code decompiled incorrectly, please refer to instructions dump.
                            */
                            public final Object emit(Object obj, Continuation continuation) {
                                AnonymousClass1 anonymousClass1;
                                int i;
                                if (continuation instanceof AnonymousClass1) {
                                    AnonymousClass1 anonymousClass12 = (AnonymousClass1) continuation;
                                    int i2 = anonymousClass12.label;
                                    if ((i2 & Integer.MIN_VALUE) != 0) {
                                        anonymousClass12.label = i2 - 2147483648;
                                        anonymousClass1 = anonymousClass12;
                                        Object obj2 = anonymousClass1.result;
                                        Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
                                        i = anonymousClass1.label;
                                        if (i != 0) {
                                            ResultKt.throwOnFailure(obj2);
                                            FlowCollector flowCollector = this.$this_unsafeFlow;
                                            if (!((Boolean) obj).booleanValue()) {
                                                anonymousClass1.label = 1;
                                                if (flowCollector.emit(obj, anonymousClass1) == coroutine_suspended) {
                                                    return coroutine_suspended;
                                                }
                                            }
                                        } else if (i != 1) {
                                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                        } else {
                                            ResultKt.throwOnFailure(obj2);
                                        }
                                        return Unit.INSTANCE;
                                    }
                                }
                                anonymousClass1 = new AnonymousClass1(continuation);
                                Object obj22 = anonymousClass1.result;
                                Object coroutine_suspended2 = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
                                i = anonymousClass1.label;
                                if (i != 0) {
                                }
                                return Unit.INSTANCE;
                            }
                        }

                        public Object collect(FlowCollector flowCollector, Continuation continuation) {
                            Object collect = isBouncerVisible.collect(new AnonymousClass2(flowCollector), continuation);
                            return collect == IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED() ? collect : Unit.INSTANCE;
                        }
                    };
                    final KeyguardHostViewController keyguardHostViewController = this.$hostViewController;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardBouncerViewBinder.bind.1.1.9.2
                        public /* bridge */ /* synthetic */ Object emit(Object obj2, Continuation continuation) {
                            return emit(((Boolean) obj2).booleanValue(), continuation);
                        }

                        public final Object emit(boolean z, Continuation<? super Unit> continuation) {
                            KeyguardHostViewController.this.resetSecurityContainer();
                            return Unit.INSTANCE;
                        }
                    };
                    this.label = 1;
                    if (flow.collect(flowCollector, this) == coroutine_suspended) {
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
        public AnonymousClass1(KeyguardBouncerViewModel keyguardBouncerViewModel, KeyguardBouncerViewBinder$bind$delegate$1 keyguardBouncerViewBinder$bind$delegate$1, KeyguardHostViewController keyguardHostViewController, ViewGroup viewGroup, Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
            this.$viewModel = keyguardBouncerViewModel;
            this.$delegate = keyguardBouncerViewBinder$bind$delegate$1;
            this.$hostViewController = keyguardHostViewController;
            this.$view = viewGroup;
        }

        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$viewModel, this.$delegate, this.$hostViewController, this.$view, continuation);
            anonymousClass1.L$0 = obj;
            return anonymousClass1;
        }

        /* JADX DEBUG: Method merged with bridge method */
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return create(coroutineScope, continuation).invokeSuspend(Unit.INSTANCE);
        }

        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            try {
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
                    this.$viewModel.setBouncerViewDelegate(this.$delegate);
                    BuildersKt.launch$default(coroutineScope, (CoroutineContext) null, (CoroutineStart) null, new C00201(this.$viewModel, this.$hostViewController, this.$view, null), 3, (Object) null);
                    BuildersKt.launch$default(coroutineScope, (CoroutineContext) null, (CoroutineStart) null, new AnonymousClass2(this.$viewModel, this.$hostViewController, null), 3, (Object) null);
                    BuildersKt.launch$default(coroutineScope, (CoroutineContext) null, (CoroutineStart) null, new AnonymousClass3(this.$viewModel, this.$hostViewController, null), 3, (Object) null);
                    BuildersKt.launch$default(coroutineScope, (CoroutineContext) null, (CoroutineStart) null, new AnonymousClass4(this.$viewModel, this.$hostViewController, null), 3, (Object) null);
                    BuildersKt.launch$default(coroutineScope, (CoroutineContext) null, (CoroutineStart) null, new AnonymousClass5(this.$viewModel, this.$hostViewController, null), 3, (Object) null);
                    BuildersKt.launch$default(coroutineScope, (CoroutineContext) null, (CoroutineStart) null, new AnonymousClass6(this.$viewModel, this.$hostViewController, null), 3, (Object) null);
                    BuildersKt.launch$default(coroutineScope, (CoroutineContext) null, (CoroutineStart) null, new AnonymousClass7(this.$viewModel, this.$hostViewController, this.$view, null), 3, (Object) null);
                    BuildersKt.launch$default(coroutineScope, (CoroutineContext) null, (CoroutineStart) null, new AnonymousClass8(this.$viewModel, this.$view, this.$hostViewController, null), 3, (Object) null);
                    BuildersKt.launch$default(coroutineScope, (CoroutineContext) null, (CoroutineStart) null, new AnonymousClass9(this.$viewModel, this.$hostViewController, null), 3, (Object) null);
                    BuildersKt.launch$default(coroutineScope, (CoroutineContext) null, (CoroutineStart) null, new AnonymousClass10(this.$viewModel, this.$hostViewController, null), 3, (Object) null);
                    BuildersKt.launch$default(coroutineScope, (CoroutineContext) null, (CoroutineStart) null, new AnonymousClass11(this.$viewModel, this.$hostViewController, null), 3, (Object) null);
                    BuildersKt.launch$default(coroutineScope, (CoroutineContext) null, (CoroutineStart) null, new AnonymousClass12(this.$viewModel, this.$hostViewController, null), 3, (Object) null);
                    BuildersKt.launch$default(coroutineScope, (CoroutineContext) null, (CoroutineStart) null, new AnonymousClass13(this.$viewModel, this.$hostViewController, null), 3, (Object) null);
                    BuildersKt.launch$default(coroutineScope, (CoroutineContext) null, (CoroutineStart) null, new AnonymousClass14(this.$viewModel, this.$view, null), 3, (Object) null);
                    BuildersKt.launch$default(coroutineScope, (CoroutineContext) null, (CoroutineStart) null, new AnonymousClass15(this.$viewModel, this.$view, this.$hostViewController, null), 3, (Object) null);
                    this.label = 1;
                    if (DelayKt.awaitCancellation(this) == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                } else if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                } else {
                    ResultKt.throwOnFailure(obj);
                }
                throw new KotlinNothingValueException();
            } catch (Throwable th) {
                this.$viewModel.setBouncerViewDelegate(null);
                throw th;
            }
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KeyguardBouncerViewBinder$bind$1(KeyguardBouncerViewModel keyguardBouncerViewModel, KeyguardBouncerViewBinder$bind$delegate$1 keyguardBouncerViewBinder$bind$delegate$1, KeyguardHostViewController keyguardHostViewController, ViewGroup viewGroup, Continuation<? super KeyguardBouncerViewBinder$bind$1> continuation) {
        super(3, continuation);
        this.$viewModel = keyguardBouncerViewModel;
        this.$delegate = keyguardBouncerViewBinder$bind$delegate$1;
        this.$hostViewController = keyguardHostViewController;
        this.$view = viewGroup;
    }

    /* JADX DEBUG: Method merged with bridge method */
    public final Object invoke(LifecycleOwner lifecycleOwner, View view, Continuation<? super Unit> continuation) {
        KeyguardBouncerViewBinder$bind$1 keyguardBouncerViewBinder$bind$1 = new KeyguardBouncerViewBinder$bind$1(this.$viewModel, this.$delegate, this.$hostViewController, this.$view, continuation);
        keyguardBouncerViewBinder$bind$1.L$0 = lifecycleOwner;
        return keyguardBouncerViewBinder$bind$1.invokeSuspend(Unit.INSTANCE);
    }

    public final Object invokeSuspend(Object obj) {
        Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            LifecycleOwner lifecycleOwner = (LifecycleOwner) this.L$0;
            Lifecycle.State state = Lifecycle.State.CREATED;
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$viewModel, this.$delegate, this.$hostViewController, this.$view, null);
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