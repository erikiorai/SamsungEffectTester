package com.android.systemui.keyguard.ui.binder;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.RepeatOnLifecycleKt;
import com.android.settingslib.widget.ActionBarShadowController;
import com.android.systemui.keyguard.ui.binder.KeyguardBottomAreaViewBinder;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardBottomAreaViewModel;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardQuickAffordanceViewModel;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.statusbar.VibratorHelper;
import com.android.systemui.util.kotlin.FlowKt;
import com.android.systemui.util.kotlin.WithPrev;
import kotlin.KotlinNothingValueException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineStart;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.MutableStateFlow;

@DebugMetadata(c = "com.android.systemui.keyguard.ui.binder.KeyguardBottomAreaViewBinder$bind$1", f = "KeyguardBottomAreaViewBinder.kt", l = {118}, m = "invokeSuspend")
/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/ui/binder/KeyguardBottomAreaViewBinder$bind$1.class */
public final class KeyguardBottomAreaViewBinder$bind$1 extends SuspendLambda implements Function3<LifecycleOwner, View, Continuation<? super Unit>, Object> {
    public final /* synthetic */ View $ambientIndicationArea;
    public final /* synthetic */ MutableStateFlow<KeyguardBottomAreaViewBinder.ConfigurationBasedDimensions> $configurationBasedDimensions;
    public final /* synthetic */ ImageView $endButton;
    public final /* synthetic */ FalsingManager $falsingManager;
    public final /* synthetic */ View $indicationArea;
    public final /* synthetic */ TextView $indicationText;
    public final /* synthetic */ TextView $indicationTextBottom;
    public final /* synthetic */ Function1<Integer, Unit> $messageDisplayer;
    public final /* synthetic */ View $overlayContainer;
    public final /* synthetic */ ImageView $startButton;
    public final /* synthetic */ VibratorHelper $vibratorHelper;
    public final /* synthetic */ ViewGroup $view;
    public final /* synthetic */ KeyguardBottomAreaViewModel $viewModel;
    private /* synthetic */ Object L$0;
    public int label;

    @DebugMetadata(c = "com.android.systemui.keyguard.ui.binder.KeyguardBottomAreaViewBinder$bind$1$1", f = "KeyguardBottomAreaViewBinder.kt", l = {}, m = "invokeSuspend")
    /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardBottomAreaViewBinder$bind$1$1  reason: invalid class name */
    /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/ui/binder/KeyguardBottomAreaViewBinder$bind$1$1.class */
    public static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        public final /* synthetic */ View $ambientIndicationArea;
        public final /* synthetic */ MutableStateFlow<KeyguardBottomAreaViewBinder.ConfigurationBasedDimensions> $configurationBasedDimensions;
        public final /* synthetic */ ImageView $endButton;
        public final /* synthetic */ FalsingManager $falsingManager;
        public final /* synthetic */ View $indicationArea;
        public final /* synthetic */ TextView $indicationText;
        public final /* synthetic */ TextView $indicationTextBottom;
        public final /* synthetic */ Function1<Integer, Unit> $messageDisplayer;
        public final /* synthetic */ View $overlayContainer;
        public final /* synthetic */ ImageView $startButton;
        public final /* synthetic */ VibratorHelper $vibratorHelper;
        public final /* synthetic */ ViewGroup $view;
        public final /* synthetic */ KeyguardBottomAreaViewModel $viewModel;
        private /* synthetic */ Object L$0;
        public int label;

        @DebugMetadata(c = "com.android.systemui.keyguard.ui.binder.KeyguardBottomAreaViewBinder$bind$1$1$1", f = "KeyguardBottomAreaViewBinder.kt", l = {120}, m = "invokeSuspend")
        /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardBottomAreaViewBinder$bind$1$1$1  reason: invalid class name and collision with other inner class name */
        /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/ui/binder/KeyguardBottomAreaViewBinder$bind$1$1$1.class */
        public static final class C00121 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            public final /* synthetic */ FalsingManager $falsingManager;
            public final /* synthetic */ Function1<Integer, Unit> $messageDisplayer;
            public final /* synthetic */ ImageView $startButton;
            public final /* synthetic */ VibratorHelper $vibratorHelper;
            public final /* synthetic */ KeyguardBottomAreaViewModel $viewModel;
            public int label;

            /* JADX DEBUG: Multi-variable search result rejected for r8v0, resolved type: kotlin.jvm.functions.Function1<? super java.lang.Integer, kotlin.Unit> */
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            public C00121(KeyguardBottomAreaViewModel keyguardBottomAreaViewModel, ImageView imageView, FalsingManager falsingManager, Function1<? super Integer, Unit> function1, VibratorHelper vibratorHelper, Continuation<? super C00121> continuation) {
                super(2, continuation);
                this.$viewModel = keyguardBottomAreaViewModel;
                this.$startButton = imageView;
                this.$falsingManager = falsingManager;
                this.$messageDisplayer = function1;
                this.$vibratorHelper = vibratorHelper;
            }

            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new C00121(this.$viewModel, this.$startButton, this.$falsingManager, this.$messageDisplayer, this.$vibratorHelper, continuation);
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
                    Flow<KeyguardQuickAffordanceViewModel> startButton = this.$viewModel.getStartButton();
                    final ImageView imageView = this.$startButton;
                    final FalsingManager falsingManager = this.$falsingManager;
                    final Function1<Integer, Unit> function1 = this.$messageDisplayer;
                    final VibratorHelper vibratorHelper = this.$vibratorHelper;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardBottomAreaViewBinder.bind.1.1.1.1
                        public final Object emit(KeyguardQuickAffordanceViewModel keyguardQuickAffordanceViewModel, Continuation<? super Unit> continuation) {
                            KeyguardBottomAreaViewBinder.INSTANCE.updateButton(imageView, keyguardQuickAffordanceViewModel, falsingManager, function1, vibratorHelper);
                            return Unit.INSTANCE;
                        }

                        public /* bridge */ /* synthetic */ Object emit(Object obj2, Continuation continuation) {
                            return emit((KeyguardQuickAffordanceViewModel) obj2, (Continuation<? super Unit>) continuation);
                        }
                    };
                    this.label = 1;
                    if (startButton.collect(flowCollector, this) == coroutine_suspended) {
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

        @DebugMetadata(c = "com.android.systemui.keyguard.ui.binder.KeyguardBottomAreaViewBinder$bind$1$1$10", f = "KeyguardBottomAreaViewBinder.kt", l = {230}, m = "invokeSuspend")
        /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardBottomAreaViewBinder$bind$1$1$10  reason: invalid class name */
        /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/ui/binder/KeyguardBottomAreaViewBinder$bind$1$1$10.class */
        public static final class AnonymousClass10 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            public final /* synthetic */ MutableStateFlow<KeyguardBottomAreaViewBinder.ConfigurationBasedDimensions> $configurationBasedDimensions;
            public final /* synthetic */ ImageView $endButton;
            public final /* synthetic */ TextView $indicationText;
            public final /* synthetic */ TextView $indicationTextBottom;
            public final /* synthetic */ ImageView $startButton;
            public int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass10(MutableStateFlow<KeyguardBottomAreaViewBinder.ConfigurationBasedDimensions> mutableStateFlow, TextView textView, TextView textView2, ImageView imageView, ImageView imageView2, Continuation<? super AnonymousClass10> continuation) {
                super(2, continuation);
                this.$configurationBasedDimensions = mutableStateFlow;
                this.$indicationText = textView;
                this.$indicationTextBottom = textView2;
                this.$startButton = imageView;
                this.$endButton = imageView2;
            }

            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new AnonymousClass10(this.$configurationBasedDimensions, this.$indicationText, this.$indicationTextBottom, this.$startButton, this.$endButton, continuation);
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
                    MutableStateFlow<KeyguardBottomAreaViewBinder.ConfigurationBasedDimensions> mutableStateFlow = this.$configurationBasedDimensions;
                    final TextView textView = this.$indicationText;
                    final TextView textView2 = this.$indicationTextBottom;
                    final ImageView imageView = this.$startButton;
                    final ImageView imageView2 = this.$endButton;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardBottomAreaViewBinder.bind.1.1.10.1
                        public final Object emit(KeyguardBottomAreaViewBinder.ConfigurationBasedDimensions configurationBasedDimensions, Continuation<? super Unit> continuation) {
                            textView.setTextSize(0, configurationBasedDimensions.getIndicationTextSizePx());
                            textView2.setTextSize(0, configurationBasedDimensions.getIndicationTextSizePx());
                            ImageView imageView3 = imageView;
                            ViewGroup.LayoutParams layoutParams = imageView3.getLayoutParams();
                            if (layoutParams != null) {
                                layoutParams.width = configurationBasedDimensions.getButtonSizePx().getWidth();
                                layoutParams.height = configurationBasedDimensions.getButtonSizePx().getHeight();
                                imageView3.setLayoutParams(layoutParams);
                                ImageView imageView4 = imageView2;
                                ViewGroup.LayoutParams layoutParams2 = imageView4.getLayoutParams();
                                if (layoutParams2 != null) {
                                    layoutParams2.width = configurationBasedDimensions.getButtonSizePx().getWidth();
                                    layoutParams2.height = configurationBasedDimensions.getButtonSizePx().getHeight();
                                    imageView4.setLayoutParams(layoutParams2);
                                    return Unit.INSTANCE;
                                }
                                throw new NullPointerException("null cannot be cast to non-null type android.view.ViewGroup.LayoutParams");
                            }
                            throw new NullPointerException("null cannot be cast to non-null type android.view.ViewGroup.LayoutParams");
                        }

                        public /* bridge */ /* synthetic */ Object emit(Object obj2, Continuation continuation) {
                            return emit((KeyguardBottomAreaViewBinder.ConfigurationBasedDimensions) obj2, (Continuation<? super Unit>) continuation);
                        }
                    };
                    this.label = 1;
                    if (mutableStateFlow.collect(flowCollector, this) == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                } else if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                } else {
                    ResultKt.throwOnFailure(obj);
                }
                throw new KotlinNothingValueException();
            }
        }

        @DebugMetadata(c = "com.android.systemui.keyguard.ui.binder.KeyguardBottomAreaViewBinder$bind$1$1$2", f = "KeyguardBottomAreaViewBinder.kt", l = {135}, m = "invokeSuspend")
        /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardBottomAreaViewBinder$bind$1$1$2  reason: invalid class name */
        /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/ui/binder/KeyguardBottomAreaViewBinder$bind$1$1$2.class */
        public static final class AnonymousClass2 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            public final /* synthetic */ VibratorHelper $vibratorHelper;
            public final /* synthetic */ KeyguardBottomAreaViewModel $viewModel;
            public int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass2(KeyguardBottomAreaViewModel keyguardBottomAreaViewModel, VibratorHelper vibratorHelper, Continuation<? super AnonymousClass2> continuation) {
                super(2, continuation);
                this.$viewModel = keyguardBottomAreaViewModel;
                this.$vibratorHelper = vibratorHelper;
            }

            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new AnonymousClass2(this.$viewModel, this.$vibratorHelper, continuation);
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
                    final Flow<KeyguardQuickAffordanceViewModel> startButton = this.$viewModel.getStartButton();
                    Flow pairwise = FlowKt.pairwise(new Flow<Boolean>() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardBottomAreaViewBinder$bind$1$1$2$invokeSuspend$$inlined$map$1

                        /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardBottomAreaViewBinder$bind$1$1$2$invokeSuspend$$inlined$map$1$2  reason: invalid class name */
                        /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/ui/binder/KeyguardBottomAreaViewBinder$bind$1$1$2$invokeSuspend$$inlined$map$1$2.class */
                        public static final class AnonymousClass2<T> implements FlowCollector {
                            public final /* synthetic */ FlowCollector $this_unsafeFlow;

                            @DebugMetadata(c = "com.android.systemui.keyguard.ui.binder.KeyguardBottomAreaViewBinder$bind$1$1$2$invokeSuspend$$inlined$map$1$2", f = "KeyguardBottomAreaViewBinder.kt", l = {223}, m = "emit")
                            /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardBottomAreaViewBinder$bind$1$1$2$invokeSuspend$$inlined$map$1$2$1  reason: invalid class name */
                            /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/ui/binder/KeyguardBottomAreaViewBinder$bind$1$1$2$invokeSuspend$$inlined$map$1$2$1.class */
                            public static final class AnonymousClass1 extends ContinuationImpl {
                                public Object L$0;
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
                                            Boolean boxBoolean = Boxing.boxBoolean(((KeyguardQuickAffordanceViewModel) obj).isActivated());
                                            anonymousClass1.label = 1;
                                            if (flowCollector.emit(boxBoolean, anonymousClass1) == coroutine_suspended) {
                                                return coroutine_suspended;
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
                            Object collect = startButton.collect(new AnonymousClass2(flowCollector), continuation);
                            return collect == IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED() ? collect : Unit.INSTANCE;
                        }
                    });
                    final VibratorHelper vibratorHelper = this.$vibratorHelper;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardBottomAreaViewBinder.bind.1.1.2.2
                        public final Object emit(WithPrev<Boolean> withPrev, Continuation<? super Unit> continuation) {
                            VibratorHelper vibratorHelper2;
                            boolean booleanValue = ((Boolean) withPrev.component1()).booleanValue();
                            boolean booleanValue2 = ((Boolean) withPrev.component2()).booleanValue();
                            if (!booleanValue && booleanValue2) {
                                VibratorHelper vibratorHelper3 = vibratorHelper;
                                if (vibratorHelper3 != null) {
                                    vibratorHelper3.vibrate(KeyguardBottomAreaViewBinder.Vibrations.INSTANCE.getActivated());
                                }
                            } else if (booleanValue && !booleanValue2 && (vibratorHelper2 = vibratorHelper) != null) {
                                vibratorHelper2.vibrate(KeyguardBottomAreaViewBinder.Vibrations.INSTANCE.getDeactivated());
                            }
                            return Unit.INSTANCE;
                        }

                        public /* bridge */ /* synthetic */ Object emit(Object obj2, Continuation continuation) {
                            return emit((WithPrev) obj2, (Continuation<? super Unit>) continuation);
                        }
                    };
                    this.label = 1;
                    if (pairwise.collect(flowCollector, this) == coroutine_suspended) {
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

        @DebugMetadata(c = "com.android.systemui.keyguard.ui.binder.KeyguardBottomAreaViewBinder$bind$1$1$3", f = "KeyguardBottomAreaViewBinder.kt", l = {144}, m = "invokeSuspend")
        /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardBottomAreaViewBinder$bind$1$1$3  reason: invalid class name */
        /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/ui/binder/KeyguardBottomAreaViewBinder$bind$1$1$3.class */
        public static final class AnonymousClass3 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            public final /* synthetic */ ImageView $endButton;
            public final /* synthetic */ FalsingManager $falsingManager;
            public final /* synthetic */ Function1<Integer, Unit> $messageDisplayer;
            public final /* synthetic */ VibratorHelper $vibratorHelper;
            public final /* synthetic */ KeyguardBottomAreaViewModel $viewModel;
            public int label;

            /* JADX DEBUG: Multi-variable search result rejected for r8v0, resolved type: kotlin.jvm.functions.Function1<? super java.lang.Integer, kotlin.Unit> */
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            public AnonymousClass3(KeyguardBottomAreaViewModel keyguardBottomAreaViewModel, ImageView imageView, FalsingManager falsingManager, Function1<? super Integer, Unit> function1, VibratorHelper vibratorHelper, Continuation<? super AnonymousClass3> continuation) {
                super(2, continuation);
                this.$viewModel = keyguardBottomAreaViewModel;
                this.$endButton = imageView;
                this.$falsingManager = falsingManager;
                this.$messageDisplayer = function1;
                this.$vibratorHelper = vibratorHelper;
            }

            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new AnonymousClass3(this.$viewModel, this.$endButton, this.$falsingManager, this.$messageDisplayer, this.$vibratorHelper, continuation);
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
                    Flow<KeyguardQuickAffordanceViewModel> endButton = this.$viewModel.getEndButton();
                    final ImageView imageView = this.$endButton;
                    final FalsingManager falsingManager = this.$falsingManager;
                    final Function1<Integer, Unit> function1 = this.$messageDisplayer;
                    final VibratorHelper vibratorHelper = this.$vibratorHelper;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardBottomAreaViewBinder.bind.1.1.3.1
                        public final Object emit(KeyguardQuickAffordanceViewModel keyguardQuickAffordanceViewModel, Continuation<? super Unit> continuation) {
                            KeyguardBottomAreaViewBinder.INSTANCE.updateButton(imageView, keyguardQuickAffordanceViewModel, falsingManager, function1, vibratorHelper);
                            return Unit.INSTANCE;
                        }

                        public /* bridge */ /* synthetic */ Object emit(Object obj2, Continuation continuation) {
                            return emit((KeyguardQuickAffordanceViewModel) obj2, (Continuation<? super Unit>) continuation);
                        }
                    };
                    this.label = 1;
                    if (endButton.collect(flowCollector, this) == coroutine_suspended) {
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

        @DebugMetadata(c = "com.android.systemui.keyguard.ui.binder.KeyguardBottomAreaViewBinder$bind$1$1$4", f = "KeyguardBottomAreaViewBinder.kt", l = {159}, m = "invokeSuspend")
        /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardBottomAreaViewBinder$bind$1$1$4  reason: invalid class name */
        /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/ui/binder/KeyguardBottomAreaViewBinder$bind$1$1$4.class */
        public static final class AnonymousClass4 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            public final /* synthetic */ VibratorHelper $vibratorHelper;
            public final /* synthetic */ KeyguardBottomAreaViewModel $viewModel;
            public int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass4(KeyguardBottomAreaViewModel keyguardBottomAreaViewModel, VibratorHelper vibratorHelper, Continuation<? super AnonymousClass4> continuation) {
                super(2, continuation);
                this.$viewModel = keyguardBottomAreaViewModel;
                this.$vibratorHelper = vibratorHelper;
            }

            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new AnonymousClass4(this.$viewModel, this.$vibratorHelper, continuation);
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
                    final Flow<KeyguardQuickAffordanceViewModel> endButton = this.$viewModel.getEndButton();
                    Flow pairwise = FlowKt.pairwise(new Flow<Boolean>() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardBottomAreaViewBinder$bind$1$1$4$invokeSuspend$$inlined$map$1

                        /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardBottomAreaViewBinder$bind$1$1$4$invokeSuspend$$inlined$map$1$2  reason: invalid class name */
                        /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/ui/binder/KeyguardBottomAreaViewBinder$bind$1$1$4$invokeSuspend$$inlined$map$1$2.class */
                        public static final class AnonymousClass2<T> implements FlowCollector {
                            public final /* synthetic */ FlowCollector $this_unsafeFlow;

                            @DebugMetadata(c = "com.android.systemui.keyguard.ui.binder.KeyguardBottomAreaViewBinder$bind$1$1$4$invokeSuspend$$inlined$map$1$2", f = "KeyguardBottomAreaViewBinder.kt", l = {223}, m = "emit")
                            /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardBottomAreaViewBinder$bind$1$1$4$invokeSuspend$$inlined$map$1$2$1  reason: invalid class name */
                            /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/ui/binder/KeyguardBottomAreaViewBinder$bind$1$1$4$invokeSuspend$$inlined$map$1$2$1.class */
                            public static final class AnonymousClass1 extends ContinuationImpl {
                                public Object L$0;
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
                                            Boolean boxBoolean = Boxing.boxBoolean(((KeyguardQuickAffordanceViewModel) obj).isActivated());
                                            anonymousClass1.label = 1;
                                            if (flowCollector.emit(boxBoolean, anonymousClass1) == coroutine_suspended) {
                                                return coroutine_suspended;
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
                            Object collect = endButton.collect(new AnonymousClass2(flowCollector), continuation);
                            return collect == IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED() ? collect : Unit.INSTANCE;
                        }
                    });
                    final VibratorHelper vibratorHelper = this.$vibratorHelper;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardBottomAreaViewBinder.bind.1.1.4.2
                        public final Object emit(WithPrev<Boolean> withPrev, Continuation<? super Unit> continuation) {
                            VibratorHelper vibratorHelper2;
                            boolean booleanValue = ((Boolean) withPrev.component1()).booleanValue();
                            boolean booleanValue2 = ((Boolean) withPrev.component2()).booleanValue();
                            if (!booleanValue && booleanValue2) {
                                VibratorHelper vibratorHelper3 = vibratorHelper;
                                if (vibratorHelper3 != null) {
                                    vibratorHelper3.vibrate(KeyguardBottomAreaViewBinder.Vibrations.INSTANCE.getActivated());
                                }
                            } else if (booleanValue && !booleanValue2 && (vibratorHelper2 = vibratorHelper) != null) {
                                vibratorHelper2.vibrate(KeyguardBottomAreaViewBinder.Vibrations.INSTANCE.getDeactivated());
                            }
                            return Unit.INSTANCE;
                        }

                        public /* bridge */ /* synthetic */ Object emit(Object obj2, Continuation continuation) {
                            return emit((WithPrev) obj2, (Continuation<? super Unit>) continuation);
                        }
                    };
                    this.label = 1;
                    if (pairwise.collect(flowCollector, this) == coroutine_suspended) {
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

        @DebugMetadata(c = "com.android.systemui.keyguard.ui.binder.KeyguardBottomAreaViewBinder$bind$1$1$5", f = "KeyguardBottomAreaViewBinder.kt", l = {168}, m = "invokeSuspend")
        /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardBottomAreaViewBinder$bind$1$1$5  reason: invalid class name */
        /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/ui/binder/KeyguardBottomAreaViewBinder$bind$1$1$5.class */
        public static final class AnonymousClass5 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            public final /* synthetic */ View $overlayContainer;
            public final /* synthetic */ KeyguardBottomAreaViewModel $viewModel;
            public int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass5(KeyguardBottomAreaViewModel keyguardBottomAreaViewModel, View view, Continuation<? super AnonymousClass5> continuation) {
                super(2, continuation);
                this.$viewModel = keyguardBottomAreaViewModel;
                this.$overlayContainer = view;
            }

            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new AnonymousClass5(this.$viewModel, this.$overlayContainer, continuation);
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
                    Flow<Boolean> isOverlayContainerVisible = this.$viewModel.isOverlayContainerVisible();
                    final View view = this.$overlayContainer;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardBottomAreaViewBinder.bind.1.1.5.1
                        public /* bridge */ /* synthetic */ Object emit(Object obj2, Continuation continuation) {
                            return emit(((Boolean) obj2).booleanValue(), continuation);
                        }

                        public final Object emit(boolean z, Continuation<? super Unit> continuation) {
                            view.setVisibility(z ? 0 : 4);
                            return Unit.INSTANCE;
                        }
                    };
                    this.label = 1;
                    if (isOverlayContainerVisible.collect(flowCollector, this) == coroutine_suspended) {
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

        @DebugMetadata(c = "com.android.systemui.keyguard.ui.binder.KeyguardBottomAreaViewBinder$bind$1$1$6", f = "KeyguardBottomAreaViewBinder.kt", l = {179}, m = "invokeSuspend")
        /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardBottomAreaViewBinder$bind$1$1$6  reason: invalid class name */
        /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/ui/binder/KeyguardBottomAreaViewBinder$bind$1$1$6.class */
        public static final class AnonymousClass6 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            public final /* synthetic */ View $ambientIndicationArea;
            public final /* synthetic */ ImageView $endButton;
            public final /* synthetic */ View $indicationArea;
            public final /* synthetic */ ImageView $startButton;
            public final /* synthetic */ ViewGroup $view;
            public final /* synthetic */ KeyguardBottomAreaViewModel $viewModel;
            public int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass6(KeyguardBottomAreaViewModel keyguardBottomAreaViewModel, ViewGroup viewGroup, View view, View view2, ImageView imageView, ImageView imageView2, Continuation<? super AnonymousClass6> continuation) {
                super(2, continuation);
                this.$viewModel = keyguardBottomAreaViewModel;
                this.$view = viewGroup;
                this.$ambientIndicationArea = view;
                this.$indicationArea = view2;
                this.$startButton = imageView;
                this.$endButton = imageView2;
            }

            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new AnonymousClass6(this.$viewModel, this.$view, this.$ambientIndicationArea, this.$indicationArea, this.$startButton, this.$endButton, continuation);
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
                    Flow<Float> alpha = this.$viewModel.getAlpha();
                    final ViewGroup viewGroup = this.$view;
                    final View view = this.$ambientIndicationArea;
                    final View view2 = this.$indicationArea;
                    final ImageView imageView = this.$startButton;
                    final ImageView imageView2 = this.$endButton;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardBottomAreaViewBinder.bind.1.1.6.1
                        public final Object emit(float f, Continuation<? super Unit> continuation) {
                            ViewGroup viewGroup2 = viewGroup;
                            int i2 = 0;
                            if (f == ActionBarShadowController.ELEVATION_LOW) {
                                i2 = 4;
                            }
                            viewGroup2.setImportantForAccessibility(i2);
                            View view3 = view;
                            if (view3 != null) {
                                view3.setAlpha(f);
                            }
                            view2.setAlpha(f);
                            imageView.setAlpha(f);
                            imageView2.setAlpha(f);
                            return Unit.INSTANCE;
                        }

                        public /* bridge */ /* synthetic */ Object emit(Object obj2, Continuation continuation) {
                            return emit(((Number) obj2).floatValue(), continuation);
                        }
                    };
                    this.label = 1;
                    if (alpha.collect(flowCollector, this) == coroutine_suspended) {
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

        @DebugMetadata(c = "com.android.systemui.keyguard.ui.binder.KeyguardBottomAreaViewBinder$bind$1$1$7", f = "KeyguardBottomAreaViewBinder.kt", l = {195}, m = "invokeSuspend")
        /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardBottomAreaViewBinder$bind$1$1$7  reason: invalid class name */
        /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/ui/binder/KeyguardBottomAreaViewBinder$bind$1$1$7.class */
        public static final class AnonymousClass7 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            public final /* synthetic */ View $ambientIndicationArea;
            public final /* synthetic */ View $indicationArea;
            public final /* synthetic */ KeyguardBottomAreaViewModel $viewModel;
            public int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass7(KeyguardBottomAreaViewModel keyguardBottomAreaViewModel, View view, View view2, Continuation<? super AnonymousClass7> continuation) {
                super(2, continuation);
                this.$viewModel = keyguardBottomAreaViewModel;
                this.$indicationArea = view;
                this.$ambientIndicationArea = view2;
            }

            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new AnonymousClass7(this.$viewModel, this.$indicationArea, this.$ambientIndicationArea, continuation);
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
                    Flow<Float> indicationAreaTranslationX = this.$viewModel.getIndicationAreaTranslationX();
                    final View view = this.$indicationArea;
                    final View view2 = this.$ambientIndicationArea;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardBottomAreaViewBinder.bind.1.1.7.1
                        public final Object emit(float f, Continuation<? super Unit> continuation) {
                            view.setTranslationX(f);
                            View view3 = view2;
                            if (view3 != null) {
                                view3.setTranslationX(f);
                            }
                            return Unit.INSTANCE;
                        }

                        public /* bridge */ /* synthetic */ Object emit(Object obj2, Continuation continuation) {
                            return emit(((Number) obj2).floatValue(), continuation);
                        }
                    };
                    this.label = 1;
                    if (indicationAreaTranslationX.collect(flowCollector, this) == coroutine_suspended) {
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

        @DebugMetadata(c = "com.android.systemui.keyguard.ui.binder.KeyguardBottomAreaViewBinder$bind$1$1$8", f = "KeyguardBottomAreaViewBinder.kt", l = {212}, m = "invokeSuspend")
        /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardBottomAreaViewBinder$bind$1$1$8  reason: invalid class name */
        /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/ui/binder/KeyguardBottomAreaViewBinder$bind$1$1$8.class */
        public static final class AnonymousClass8 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            public final /* synthetic */ MutableStateFlow<KeyguardBottomAreaViewBinder.ConfigurationBasedDimensions> $configurationBasedDimensions;
            public final /* synthetic */ View $indicationArea;
            public final /* synthetic */ KeyguardBottomAreaViewModel $viewModel;
            public int label;

            @DebugMetadata(c = "com.android.systemui.keyguard.ui.binder.KeyguardBottomAreaViewBinder$bind$1$1$8$2", f = "KeyguardBottomAreaViewBinder.kt", l = {}, m = "invokeSuspend")
            /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardBottomAreaViewBinder$bind$1$1$8$2  reason: invalid class name */
            /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/ui/binder/KeyguardBottomAreaViewBinder$bind$1$1$8$2.class */
            public static final class AnonymousClass2 extends SuspendLambda implements Function3<Boolean, Integer, Continuation<? super Integer>, Object> {
                public /* synthetic */ int I$0;
                public /* synthetic */ boolean Z$0;
                public int label;

                public AnonymousClass2(Continuation<? super AnonymousClass2> continuation) {
                    super(3, continuation);
                }

                public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2, Object obj3) {
                    return invoke(((Boolean) obj).booleanValue(), ((Number) obj2).intValue(), (Continuation) obj3);
                }

                public final Object invoke(boolean z, int i, Continuation<? super Integer> continuation) {
                    AnonymousClass2 anonymousClass2 = new AnonymousClass2(continuation);
                    anonymousClass2.Z$0 = z;
                    anonymousClass2.I$0 = i;
                    return anonymousClass2.invokeSuspend(Unit.INSTANCE);
                }

                public final Object invokeSuspend(Object obj) {
                    IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
                    if (this.label == 0) {
                        ResultKt.throwOnFailure(obj);
                        boolean z = this.Z$0;
                        int i = this.I$0;
                        if (!z) {
                            i = 0;
                        }
                        return Boxing.boxInt(i);
                    }
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
            }

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass8(KeyguardBottomAreaViewModel keyguardBottomAreaViewModel, MutableStateFlow<KeyguardBottomAreaViewBinder.ConfigurationBasedDimensions> mutableStateFlow, View view, Continuation<? super AnonymousClass8> continuation) {
                super(2, continuation);
                this.$viewModel = keyguardBottomAreaViewModel;
                this.$configurationBasedDimensions = mutableStateFlow;
                this.$indicationArea = view;
            }

            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new AnonymousClass8(this.$viewModel, this.$configurationBasedDimensions, this.$indicationArea, continuation);
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
                    Flow<Boolean> isIndicationAreaPadded = this.$viewModel.isIndicationAreaPadded();
                    final Flow flow = this.$configurationBasedDimensions;
                    Flow combine = kotlinx.coroutines.flow.FlowKt.combine(isIndicationAreaPadded, new Flow<Integer>() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardBottomAreaViewBinder$bind$1$1$8$invokeSuspend$$inlined$map$1

                        /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardBottomAreaViewBinder$bind$1$1$8$invokeSuspend$$inlined$map$1$2  reason: invalid class name */
                        /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/ui/binder/KeyguardBottomAreaViewBinder$bind$1$1$8$invokeSuspend$$inlined$map$1$2.class */
                        public static final class AnonymousClass2<T> implements FlowCollector {
                            public final /* synthetic */ FlowCollector $this_unsafeFlow;

                            @DebugMetadata(c = "com.android.systemui.keyguard.ui.binder.KeyguardBottomAreaViewBinder$bind$1$1$8$invokeSuspend$$inlined$map$1$2", f = "KeyguardBottomAreaViewBinder.kt", l = {223}, m = "emit")
                            /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardBottomAreaViewBinder$bind$1$1$8$invokeSuspend$$inlined$map$1$2$1  reason: invalid class name */
                            /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/ui/binder/KeyguardBottomAreaViewBinder$bind$1$1$8$invokeSuspend$$inlined$map$1$2$1.class */
                            public static final class AnonymousClass1 extends ContinuationImpl {
                                public Object L$0;
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
                                            Integer boxInt = Boxing.boxInt(((KeyguardBottomAreaViewBinder.ConfigurationBasedDimensions) obj).getIndicationAreaPaddingPx());
                                            anonymousClass1.label = 1;
                                            if (flowCollector.emit(boxInt, anonymousClass1) == coroutine_suspended) {
                                                return coroutine_suspended;
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
                            Object collect = flow.collect(new AnonymousClass2(flowCollector), continuation);
                            return collect == IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED() ? collect : Unit.INSTANCE;
                        }
                    }, new AnonymousClass2(null));
                    final View view = this.$indicationArea;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardBottomAreaViewBinder.bind.1.1.8.3
                        public final Object emit(int i2, Continuation<? super Unit> continuation) {
                            view.setPadding(i2, 0, i2, 0);
                            return Unit.INSTANCE;
                        }

                        public /* bridge */ /* synthetic */ Object emit(Object obj2, Continuation continuation) {
                            return emit(((Number) obj2).intValue(), continuation);
                        }
                    };
                    this.label = 1;
                    if (combine.collect(flowCollector, this) == coroutine_suspended) {
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

        @DebugMetadata(c = "com.android.systemui.keyguard.ui.binder.KeyguardBottomAreaViewBinder$bind$1$1$9", f = "KeyguardBottomAreaViewBinder.kt", l = {223}, m = "invokeSuspend")
        /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardBottomAreaViewBinder$bind$1$1$9  reason: invalid class name */
        /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/ui/binder/KeyguardBottomAreaViewBinder$bind$1$1$9.class */
        public static final class AnonymousClass9 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            public final /* synthetic */ View $ambientIndicationArea;
            public final /* synthetic */ MutableStateFlow<KeyguardBottomAreaViewBinder.ConfigurationBasedDimensions> $configurationBasedDimensions;
            public final /* synthetic */ View $indicationArea;
            public final /* synthetic */ KeyguardBottomAreaViewModel $viewModel;
            public int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass9(MutableStateFlow<KeyguardBottomAreaViewBinder.ConfigurationBasedDimensions> mutableStateFlow, KeyguardBottomAreaViewModel keyguardBottomAreaViewModel, View view, View view2, Continuation<? super AnonymousClass9> continuation) {
                super(2, continuation);
                this.$configurationBasedDimensions = mutableStateFlow;
                this.$viewModel = keyguardBottomAreaViewModel;
                this.$indicationArea = view;
                this.$ambientIndicationArea = view2;
            }

            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new AnonymousClass9(this.$configurationBasedDimensions, this.$viewModel, this.$indicationArea, this.$ambientIndicationArea, continuation);
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
                    final Flow flow = this.$configurationBasedDimensions;
                    Flow transformLatest = kotlinx.coroutines.flow.FlowKt.transformLatest(new Flow<Integer>() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardBottomAreaViewBinder$bind$1$1$9$invokeSuspend$$inlined$map$1

                        /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardBottomAreaViewBinder$bind$1$1$9$invokeSuspend$$inlined$map$1$2  reason: invalid class name */
                        /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/ui/binder/KeyguardBottomAreaViewBinder$bind$1$1$9$invokeSuspend$$inlined$map$1$2.class */
                        public static final class AnonymousClass2<T> implements FlowCollector {
                            public final /* synthetic */ FlowCollector $this_unsafeFlow;

                            @DebugMetadata(c = "com.android.systemui.keyguard.ui.binder.KeyguardBottomAreaViewBinder$bind$1$1$9$invokeSuspend$$inlined$map$1$2", f = "KeyguardBottomAreaViewBinder.kt", l = {223}, m = "emit")
                            /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardBottomAreaViewBinder$bind$1$1$9$invokeSuspend$$inlined$map$1$2$1  reason: invalid class name */
                            /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/ui/binder/KeyguardBottomAreaViewBinder$bind$1$1$9$invokeSuspend$$inlined$map$1$2$1.class */
                            public static final class AnonymousClass1 extends ContinuationImpl {
                                public Object L$0;
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
                                            Integer boxInt = Boxing.boxInt(((KeyguardBottomAreaViewBinder.ConfigurationBasedDimensions) obj).getDefaultBurnInPreventionYOffsetPx());
                                            anonymousClass1.label = 1;
                                            if (flowCollector.emit(boxInt, anonymousClass1) == coroutine_suspended) {
                                                return coroutine_suspended;
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
                            Object collect = flow.collect(new AnonymousClass2(flowCollector), continuation);
                            return collect == IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED() ? collect : Unit.INSTANCE;
                        }
                    }, new KeyguardBottomAreaViewBinder$bind$1$1$9$invokeSuspend$$inlined$flatMapLatest$1(null, this.$viewModel));
                    final View view = this.$indicationArea;
                    final View view2 = this.$ambientIndicationArea;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardBottomAreaViewBinder.bind.1.1.9.3
                        public final Object emit(float f, Continuation<? super Unit> continuation) {
                            view.setTranslationY(f);
                            View view3 = view2;
                            if (view3 != null) {
                                view3.setTranslationY(f);
                            }
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

        /* JADX DEBUG: Multi-variable search result rejected for r8v0, resolved type: kotlin.jvm.functions.Function1<? super java.lang.Integer, kotlin.Unit> */
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        public AnonymousClass1(KeyguardBottomAreaViewModel keyguardBottomAreaViewModel, ImageView imageView, FalsingManager falsingManager, Function1<? super Integer, Unit> function1, VibratorHelper vibratorHelper, ImageView imageView2, View view, ViewGroup viewGroup, View view2, View view3, MutableStateFlow<KeyguardBottomAreaViewBinder.ConfigurationBasedDimensions> mutableStateFlow, TextView textView, TextView textView2, Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
            this.$viewModel = keyguardBottomAreaViewModel;
            this.$startButton = imageView;
            this.$falsingManager = falsingManager;
            this.$messageDisplayer = function1;
            this.$vibratorHelper = vibratorHelper;
            this.$endButton = imageView2;
            this.$overlayContainer = view;
            this.$view = viewGroup;
            this.$ambientIndicationArea = view2;
            this.$indicationArea = view3;
            this.$configurationBasedDimensions = mutableStateFlow;
            this.$indicationText = textView;
            this.$indicationTextBottom = textView2;
        }

        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$viewModel, this.$startButton, this.$falsingManager, this.$messageDisplayer, this.$vibratorHelper, this.$endButton, this.$overlayContainer, this.$view, this.$ambientIndicationArea, this.$indicationArea, this.$configurationBasedDimensions, this.$indicationText, this.$indicationTextBottom, continuation);
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
                BuildersKt.launch$default(coroutineScope, (CoroutineContext) null, (CoroutineStart) null, new C00121(this.$viewModel, this.$startButton, this.$falsingManager, this.$messageDisplayer, this.$vibratorHelper, null), 3, (Object) null);
                BuildersKt.launch$default(coroutineScope, (CoroutineContext) null, (CoroutineStart) null, new AnonymousClass2(this.$viewModel, this.$vibratorHelper, null), 3, (Object) null);
                BuildersKt.launch$default(coroutineScope, (CoroutineContext) null, (CoroutineStart) null, new AnonymousClass3(this.$viewModel, this.$endButton, this.$falsingManager, this.$messageDisplayer, this.$vibratorHelper, null), 3, (Object) null);
                BuildersKt.launch$default(coroutineScope, (CoroutineContext) null, (CoroutineStart) null, new AnonymousClass4(this.$viewModel, this.$vibratorHelper, null), 3, (Object) null);
                BuildersKt.launch$default(coroutineScope, (CoroutineContext) null, (CoroutineStart) null, new AnonymousClass5(this.$viewModel, this.$overlayContainer, null), 3, (Object) null);
                BuildersKt.launch$default(coroutineScope, (CoroutineContext) null, (CoroutineStart) null, new AnonymousClass6(this.$viewModel, this.$view, this.$ambientIndicationArea, this.$indicationArea, this.$startButton, this.$endButton, null), 3, (Object) null);
                BuildersKt.launch$default(coroutineScope, (CoroutineContext) null, (CoroutineStart) null, new AnonymousClass7(this.$viewModel, this.$indicationArea, this.$ambientIndicationArea, null), 3, (Object) null);
                BuildersKt.launch$default(coroutineScope, (CoroutineContext) null, (CoroutineStart) null, new AnonymousClass8(this.$viewModel, this.$configurationBasedDimensions, this.$indicationArea, null), 3, (Object) null);
                BuildersKt.launch$default(coroutineScope, (CoroutineContext) null, (CoroutineStart) null, new AnonymousClass9(this.$configurationBasedDimensions, this.$viewModel, this.$indicationArea, this.$ambientIndicationArea, null), 3, (Object) null);
                BuildersKt.launch$default(coroutineScope, (CoroutineContext) null, (CoroutineStart) null, new AnonymousClass10(this.$configurationBasedDimensions, this.$indicationText, this.$indicationTextBottom, this.$startButton, this.$endButton, null), 3, (Object) null);
                return Unit.INSTANCE;
            }
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for r8v0, resolved type: kotlin.jvm.functions.Function1<? super java.lang.Integer, kotlin.Unit> */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public KeyguardBottomAreaViewBinder$bind$1(KeyguardBottomAreaViewModel keyguardBottomAreaViewModel, ImageView imageView, FalsingManager falsingManager, Function1<? super Integer, Unit> function1, VibratorHelper vibratorHelper, ImageView imageView2, View view, ViewGroup viewGroup, View view2, View view3, MutableStateFlow<KeyguardBottomAreaViewBinder.ConfigurationBasedDimensions> mutableStateFlow, TextView textView, TextView textView2, Continuation<? super KeyguardBottomAreaViewBinder$bind$1> continuation) {
        super(3, continuation);
        this.$viewModel = keyguardBottomAreaViewModel;
        this.$startButton = imageView;
        this.$falsingManager = falsingManager;
        this.$messageDisplayer = function1;
        this.$vibratorHelper = vibratorHelper;
        this.$endButton = imageView2;
        this.$overlayContainer = view;
        this.$view = viewGroup;
        this.$ambientIndicationArea = view2;
        this.$indicationArea = view3;
        this.$configurationBasedDimensions = mutableStateFlow;
        this.$indicationText = textView;
        this.$indicationTextBottom = textView2;
    }

    /* JADX DEBUG: Method merged with bridge method */
    public final Object invoke(LifecycleOwner lifecycleOwner, View view, Continuation<? super Unit> continuation) {
        KeyguardBottomAreaViewBinder$bind$1 keyguardBottomAreaViewBinder$bind$1 = new KeyguardBottomAreaViewBinder$bind$1(this.$viewModel, this.$startButton, this.$falsingManager, this.$messageDisplayer, this.$vibratorHelper, this.$endButton, this.$overlayContainer, this.$view, this.$ambientIndicationArea, this.$indicationArea, this.$configurationBasedDimensions, this.$indicationText, this.$indicationTextBottom, continuation);
        keyguardBottomAreaViewBinder$bind$1.L$0 = lifecycleOwner;
        return keyguardBottomAreaViewBinder$bind$1.invokeSuspend(Unit.INSTANCE);
    }

    public final Object invokeSuspend(Object obj) {
        Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            LifecycleOwner lifecycleOwner = (LifecycleOwner) this.L$0;
            Lifecycle.State state = Lifecycle.State.STARTED;
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$viewModel, this.$startButton, this.$falsingManager, this.$messageDisplayer, this.$vibratorHelper, this.$endButton, this.$overlayContainer, this.$view, this.$ambientIndicationArea, this.$indicationArea, this.$configurationBasedDimensions, this.$indicationText, this.$indicationTextBottom, null);
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