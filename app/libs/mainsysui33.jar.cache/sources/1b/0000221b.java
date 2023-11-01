package com.android.systemui.qs.footer.ui.binder;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.LinearLayout;
import androidx.appcompat.R$styleable;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleOwnerKt;
import androidx.lifecycle.RepeatOnLifecycleKt;
import com.android.systemui.qs.footer.ui.viewmodel.FooterActionsButtonViewModel;
import com.android.systemui.qs.footer.ui.viewmodel.FooterActionsForegroundServicesButtonViewModel;
import com.android.systemui.qs.footer.ui.viewmodel.FooterActionsSecurityButtonViewModel;
import com.android.systemui.qs.footer.ui.viewmodel.FooterActionsViewModel;
import kotlin.KotlinNothingValueException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.math.MathKt__MathJVMKt;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineStart;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.StateFlow;

@DebugMetadata(c = "com.android.systemui.qs.footer.ui.binder.FooterActionsViewBinder$bind$1", f = "FooterActionsViewBinder.kt", l = {R$styleable.AppCompatTheme_windowMinWidthMajor}, m = "invokeSuspend")
/* loaded from: mainsysui33.jar:com/android/systemui/qs/footer/ui/binder/FooterActionsViewBinder$bind$1.class */
public final class FooterActionsViewBinder$bind$1 extends SuspendLambda implements Function3<LifecycleOwner, View, Continuation<? super Unit>, Object> {
    public final /* synthetic */ NumberButtonViewHolder $foregroundServicesWithNumberHolder;
    public final /* synthetic */ TextButtonViewHolder $foregroundServicesWithTextHolder;
    public final /* synthetic */ Ref.ObjectRef<FooterActionsForegroundServicesButtonViewModel> $previousForegroundServices;
    public final /* synthetic */ Ref.ObjectRef<FooterActionsSecurityButtonViewModel> $previousSecurity;
    public final /* synthetic */ Ref.ObjectRef<FooterActionsButtonViewModel> $previousUserSwitcher;
    public final /* synthetic */ LifecycleOwner $qsVisibilityLifecycleOwner;
    public final /* synthetic */ TextButtonViewHolder $securityHolder;
    public final /* synthetic */ IconButtonViewHolder $userSwitcherHolder;
    public final /* synthetic */ LinearLayout $view;
    public final /* synthetic */ FooterActionsViewModel $viewModel;
    private /* synthetic */ Object L$0;
    public int label;

    @DebugMetadata(c = "com.android.systemui.qs.footer.ui.binder.FooterActionsViewBinder$bind$1$1", f = "FooterActionsViewBinder.kt", l = {}, m = "invokeSuspend")
    /* renamed from: com.android.systemui.qs.footer.ui.binder.FooterActionsViewBinder$bind$1$1  reason: invalid class name */
    /* loaded from: mainsysui33.jar:com/android/systemui/qs/footer/ui/binder/FooterActionsViewBinder$bind$1$1.class */
    public static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        public final /* synthetic */ LinearLayout $view;
        public final /* synthetic */ FooterActionsViewModel $viewModel;
        private /* synthetic */ Object L$0;
        public int label;

        @DebugMetadata(c = "com.android.systemui.qs.footer.ui.binder.FooterActionsViewBinder$bind$1$1$1", f = "FooterActionsViewBinder.kt", l = {107}, m = "invokeSuspend")
        /* renamed from: com.android.systemui.qs.footer.ui.binder.FooterActionsViewBinder$bind$1$1$1  reason: invalid class name and collision with other inner class name */
        /* loaded from: mainsysui33.jar:com/android/systemui/qs/footer/ui/binder/FooterActionsViewBinder$bind$1$1$1.class */
        public static final class C00421 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            public final /* synthetic */ LinearLayout $view;
            public final /* synthetic */ FooterActionsViewModel $viewModel;
            public int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C00421(FooterActionsViewModel footerActionsViewModel, LinearLayout linearLayout, Continuation<? super C00421> continuation) {
                super(2, continuation);
                this.$viewModel = footerActionsViewModel;
                this.$view = linearLayout;
            }

            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new C00421(this.$viewModel, this.$view, continuation);
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
                    FooterActionsViewModel footerActionsViewModel = this.$viewModel;
                    Context context = this.$view.getContext();
                    this.label = 1;
                    if (footerActionsViewModel.observeDeviceMonitoringDialogRequests(context, this) == coroutine_suspended) {
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

        @DebugMetadata(c = "com.android.systemui.qs.footer.ui.binder.FooterActionsViewBinder$bind$1$1$2", f = "FooterActionsViewBinder.kt", l = {112}, m = "invokeSuspend")
        /* renamed from: com.android.systemui.qs.footer.ui.binder.FooterActionsViewBinder$bind$1$1$2  reason: invalid class name */
        /* loaded from: mainsysui33.jar:com/android/systemui/qs/footer/ui/binder/FooterActionsViewBinder$bind$1$1$2.class */
        public static final class AnonymousClass2 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            public final /* synthetic */ LinearLayout $view;
            public final /* synthetic */ FooterActionsViewModel $viewModel;
            public int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass2(FooterActionsViewModel footerActionsViewModel, LinearLayout linearLayout, Continuation<? super AnonymousClass2> continuation) {
                super(2, continuation);
                this.$viewModel = footerActionsViewModel;
                this.$view = linearLayout;
            }

            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new AnonymousClass2(this.$viewModel, this.$view, continuation);
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
                    StateFlow<Boolean> isVisible = this.$viewModel.isVisible();
                    final LinearLayout linearLayout = this.$view;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.qs.footer.ui.binder.FooterActionsViewBinder.bind.1.1.2.1
                        public /* bridge */ /* synthetic */ Object emit(Object obj2, Continuation continuation) {
                            return emit(((Boolean) obj2).booleanValue(), continuation);
                        }

                        public final Object emit(boolean z, Continuation<? super Unit> continuation) {
                            linearLayout.setVisibility(z ^ true ? 4 : 0);
                            return Unit.INSTANCE;
                        }
                    };
                    this.label = 1;
                    if (isVisible.collect(flowCollector, this) == coroutine_suspended) {
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

        @DebugMetadata(c = "com.android.systemui.qs.footer.ui.binder.FooterActionsViewBinder$bind$1$1$3", f = "FooterActionsViewBinder.kt", l = {115}, m = "invokeSuspend")
        /* renamed from: com.android.systemui.qs.footer.ui.binder.FooterActionsViewBinder$bind$1$1$3  reason: invalid class name */
        /* loaded from: mainsysui33.jar:com/android/systemui/qs/footer/ui/binder/FooterActionsViewBinder$bind$1$1$3.class */
        public static final class AnonymousClass3 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            public final /* synthetic */ LinearLayout $view;
            public final /* synthetic */ FooterActionsViewModel $viewModel;
            public int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass3(FooterActionsViewModel footerActionsViewModel, LinearLayout linearLayout, Continuation<? super AnonymousClass3> continuation) {
                super(2, continuation);
                this.$viewModel = footerActionsViewModel;
                this.$view = linearLayout;
            }

            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new AnonymousClass3(this.$viewModel, this.$view, continuation);
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
                    StateFlow<Float> alpha = this.$viewModel.getAlpha();
                    final LinearLayout linearLayout = this.$view;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.qs.footer.ui.binder.FooterActionsViewBinder.bind.1.1.3.1
                        public final Object emit(float f, Continuation<? super Unit> continuation) {
                            linearLayout.setAlpha(f);
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
                throw new KotlinNothingValueException();
            }
        }

        @DebugMetadata(c = "com.android.systemui.qs.footer.ui.binder.FooterActionsViewBinder$bind$1$1$4", f = "FooterActionsViewBinder.kt", l = {117}, m = "invokeSuspend")
        /* renamed from: com.android.systemui.qs.footer.ui.binder.FooterActionsViewBinder$bind$1$1$4  reason: invalid class name */
        /* loaded from: mainsysui33.jar:com/android/systemui/qs/footer/ui/binder/FooterActionsViewBinder$bind$1$1$4.class */
        public static final class AnonymousClass4 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            public final /* synthetic */ LinearLayout $view;
            public final /* synthetic */ FooterActionsViewModel $viewModel;
            public int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass4(FooterActionsViewModel footerActionsViewModel, LinearLayout linearLayout, Continuation<? super AnonymousClass4> continuation) {
                super(2, continuation);
                this.$viewModel = footerActionsViewModel;
                this.$view = linearLayout;
            }

            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new AnonymousClass4(this.$viewModel, this.$view, continuation);
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
                    StateFlow<Float> backgroundAlpha = this.$viewModel.getBackgroundAlpha();
                    final LinearLayout linearLayout = this.$view;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.qs.footer.ui.binder.FooterActionsViewBinder.bind.1.1.4.1
                        public final Object emit(float f, Continuation<? super Unit> continuation) {
                            Drawable background = linearLayout.getBackground();
                            if (background != null) {
                                background.setAlpha(MathKt__MathJVMKt.roundToInt(f * 255));
                            }
                            return Unit.INSTANCE;
                        }

                        public /* bridge */ /* synthetic */ Object emit(Object obj2, Continuation continuation) {
                            return emit(((Number) obj2).floatValue(), continuation);
                        }
                    };
                    this.label = 1;
                    if (backgroundAlpha.collect(flowCollector, this) == coroutine_suspended) {
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

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(FooterActionsViewModel footerActionsViewModel, LinearLayout linearLayout, Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
            this.$viewModel = footerActionsViewModel;
            this.$view = linearLayout;
        }

        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$viewModel, this.$view, continuation);
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
                BuildersKt.launch$default(coroutineScope, (CoroutineContext) null, (CoroutineStart) null, new C00421(this.$viewModel, this.$view, null), 3, (Object) null);
                BuildersKt.launch$default(coroutineScope, (CoroutineContext) null, (CoroutineStart) null, new AnonymousClass2(this.$viewModel, this.$view, null), 3, (Object) null);
                BuildersKt.launch$default(coroutineScope, (CoroutineContext) null, (CoroutineStart) null, new AnonymousClass3(this.$viewModel, this.$view, null), 3, (Object) null);
                BuildersKt.launch$default(coroutineScope, (CoroutineContext) null, (CoroutineStart) null, new AnonymousClass4(this.$viewModel, this.$view, null), 3, (Object) null);
                return Unit.INSTANCE;
            }
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }

    @DebugMetadata(c = "com.android.systemui.qs.footer.ui.binder.FooterActionsViewBinder$bind$1$2", f = "FooterActionsViewBinder.kt", l = {}, m = "invokeSuspend")
    /* renamed from: com.android.systemui.qs.footer.ui.binder.FooterActionsViewBinder$bind$1$2  reason: invalid class name */
    /* loaded from: mainsysui33.jar:com/android/systemui/qs/footer/ui/binder/FooterActionsViewBinder$bind$1$2.class */
    public static final class AnonymousClass2 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        public final /* synthetic */ NumberButtonViewHolder $foregroundServicesWithNumberHolder;
        public final /* synthetic */ TextButtonViewHolder $foregroundServicesWithTextHolder;
        public final /* synthetic */ Ref.ObjectRef<FooterActionsForegroundServicesButtonViewModel> $previousForegroundServices;
        public final /* synthetic */ Ref.ObjectRef<FooterActionsSecurityButtonViewModel> $previousSecurity;
        public final /* synthetic */ Ref.ObjectRef<FooterActionsButtonViewModel> $previousUserSwitcher;
        public final /* synthetic */ TextButtonViewHolder $securityHolder;
        public final /* synthetic */ IconButtonViewHolder $userSwitcherHolder;
        public final /* synthetic */ LinearLayout $view;
        public final /* synthetic */ FooterActionsViewModel $viewModel;
        private /* synthetic */ Object L$0;
        public int label;

        @DebugMetadata(c = "com.android.systemui.qs.footer.ui.binder.FooterActionsViewBinder$bind$1$2$1", f = "FooterActionsViewBinder.kt", l = {127}, m = "invokeSuspend")
        /* renamed from: com.android.systemui.qs.footer.ui.binder.FooterActionsViewBinder$bind$1$2$1  reason: invalid class name */
        /* loaded from: mainsysui33.jar:com/android/systemui/qs/footer/ui/binder/FooterActionsViewBinder$bind$1$2$1.class */
        public static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            public final /* synthetic */ Ref.ObjectRef<FooterActionsSecurityButtonViewModel> $previousSecurity;
            public final /* synthetic */ TextButtonViewHolder $securityHolder;
            public final /* synthetic */ LinearLayout $view;
            public final /* synthetic */ FooterActionsViewModel $viewModel;
            public int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass1(FooterActionsViewModel footerActionsViewModel, Ref.ObjectRef<FooterActionsSecurityButtonViewModel> objectRef, LinearLayout linearLayout, TextButtonViewHolder textButtonViewHolder, Continuation<? super AnonymousClass1> continuation) {
                super(2, continuation);
                this.$viewModel = footerActionsViewModel;
                this.$previousSecurity = objectRef;
                this.$view = linearLayout;
                this.$securityHolder = textButtonViewHolder;
            }

            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new AnonymousClass1(this.$viewModel, this.$previousSecurity, this.$view, this.$securityHolder, continuation);
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
                    Flow<FooterActionsSecurityButtonViewModel> security = this.$viewModel.getSecurity();
                    final Ref.ObjectRef<FooterActionsSecurityButtonViewModel> objectRef = this.$previousSecurity;
                    final LinearLayout linearLayout = this.$view;
                    final TextButtonViewHolder textButtonViewHolder = this.$securityHolder;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.qs.footer.ui.binder.FooterActionsViewBinder.bind.1.2.1.1
                        public final Object emit(FooterActionsSecurityButtonViewModel footerActionsSecurityButtonViewModel, Continuation<? super Unit> continuation) {
                            if (!Intrinsics.areEqual(objectRef.element, footerActionsSecurityButtonViewModel)) {
                                FooterActionsViewBinder.INSTANCE.bindSecurity(linearLayout.getContext(), textButtonViewHolder, footerActionsSecurityButtonViewModel);
                                objectRef.element = footerActionsSecurityButtonViewModel;
                            }
                            return Unit.INSTANCE;
                        }

                        public /* bridge */ /* synthetic */ Object emit(Object obj2, Continuation continuation) {
                            return emit((FooterActionsSecurityButtonViewModel) obj2, (Continuation<? super Unit>) continuation);
                        }
                    };
                    this.label = 1;
                    if (security.collect(flowCollector, this) == coroutine_suspended) {
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

        @DebugMetadata(c = "com.android.systemui.qs.footer.ui.binder.FooterActionsViewBinder$bind$1$2$2", f = "FooterActionsViewBinder.kt", l = {137}, m = "invokeSuspend")
        /* renamed from: com.android.systemui.qs.footer.ui.binder.FooterActionsViewBinder$bind$1$2$2  reason: invalid class name and collision with other inner class name */
        /* loaded from: mainsysui33.jar:com/android/systemui/qs/footer/ui/binder/FooterActionsViewBinder$bind$1$2$2.class */
        public static final class C00472 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            public final /* synthetic */ NumberButtonViewHolder $foregroundServicesWithNumberHolder;
            public final /* synthetic */ TextButtonViewHolder $foregroundServicesWithTextHolder;
            public final /* synthetic */ Ref.ObjectRef<FooterActionsForegroundServicesButtonViewModel> $previousForegroundServices;
            public final /* synthetic */ FooterActionsViewModel $viewModel;
            public int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C00472(FooterActionsViewModel footerActionsViewModel, Ref.ObjectRef<FooterActionsForegroundServicesButtonViewModel> objectRef, NumberButtonViewHolder numberButtonViewHolder, TextButtonViewHolder textButtonViewHolder, Continuation<? super C00472> continuation) {
                super(2, continuation);
                this.$viewModel = footerActionsViewModel;
                this.$previousForegroundServices = objectRef;
                this.$foregroundServicesWithNumberHolder = numberButtonViewHolder;
                this.$foregroundServicesWithTextHolder = textButtonViewHolder;
            }

            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new C00472(this.$viewModel, this.$previousForegroundServices, this.$foregroundServicesWithNumberHolder, this.$foregroundServicesWithTextHolder, continuation);
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
                    Flow<FooterActionsForegroundServicesButtonViewModel> foregroundServices = this.$viewModel.getForegroundServices();
                    final Ref.ObjectRef<FooterActionsForegroundServicesButtonViewModel> objectRef = this.$previousForegroundServices;
                    final NumberButtonViewHolder numberButtonViewHolder = this.$foregroundServicesWithNumberHolder;
                    final TextButtonViewHolder textButtonViewHolder = this.$foregroundServicesWithTextHolder;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.qs.footer.ui.binder.FooterActionsViewBinder.bind.1.2.2.1
                        public final Object emit(FooterActionsForegroundServicesButtonViewModel footerActionsForegroundServicesButtonViewModel, Continuation<? super Unit> continuation) {
                            if (!Intrinsics.areEqual(objectRef.element, footerActionsForegroundServicesButtonViewModel)) {
                                FooterActionsViewBinder.INSTANCE.bindForegroundService(numberButtonViewHolder, textButtonViewHolder, footerActionsForegroundServicesButtonViewModel);
                                objectRef.element = footerActionsForegroundServicesButtonViewModel;
                            }
                            return Unit.INSTANCE;
                        }

                        public /* bridge */ /* synthetic */ Object emit(Object obj2, Continuation continuation) {
                            return emit((FooterActionsForegroundServicesButtonViewModel) obj2, (Continuation<? super Unit>) continuation);
                        }
                    };
                    this.label = 1;
                    if (foregroundServices.collect(flowCollector, this) == coroutine_suspended) {
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

        @DebugMetadata(c = "com.android.systemui.qs.footer.ui.binder.FooterActionsViewBinder$bind$1$2$3", f = "FooterActionsViewBinder.kt", l = {151}, m = "invokeSuspend")
        /* renamed from: com.android.systemui.qs.footer.ui.binder.FooterActionsViewBinder$bind$1$2$3  reason: invalid class name */
        /* loaded from: mainsysui33.jar:com/android/systemui/qs/footer/ui/binder/FooterActionsViewBinder$bind$1$2$3.class */
        public static final class AnonymousClass3 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            public final /* synthetic */ Ref.ObjectRef<FooterActionsButtonViewModel> $previousUserSwitcher;
            public final /* synthetic */ IconButtonViewHolder $userSwitcherHolder;
            public final /* synthetic */ FooterActionsViewModel $viewModel;
            public int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass3(FooterActionsViewModel footerActionsViewModel, Ref.ObjectRef<FooterActionsButtonViewModel> objectRef, IconButtonViewHolder iconButtonViewHolder, Continuation<? super AnonymousClass3> continuation) {
                super(2, continuation);
                this.$viewModel = footerActionsViewModel;
                this.$previousUserSwitcher = objectRef;
                this.$userSwitcherHolder = iconButtonViewHolder;
            }

            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new AnonymousClass3(this.$viewModel, this.$previousUserSwitcher, this.$userSwitcherHolder, continuation);
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
                    Flow<FooterActionsButtonViewModel> userSwitcher = this.$viewModel.getUserSwitcher();
                    final Ref.ObjectRef<FooterActionsButtonViewModel> objectRef = this.$previousUserSwitcher;
                    final IconButtonViewHolder iconButtonViewHolder = this.$userSwitcherHolder;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.qs.footer.ui.binder.FooterActionsViewBinder.bind.1.2.3.1
                        public final Object emit(FooterActionsButtonViewModel footerActionsButtonViewModel, Continuation<? super Unit> continuation) {
                            if (!Intrinsics.areEqual(objectRef.element, footerActionsButtonViewModel)) {
                                FooterActionsViewBinder.INSTANCE.bindButton(iconButtonViewHolder, footerActionsButtonViewModel);
                                objectRef.element = footerActionsButtonViewModel;
                            }
                            return Unit.INSTANCE;
                        }

                        public /* bridge */ /* synthetic */ Object emit(Object obj2, Continuation continuation) {
                            return emit((FooterActionsButtonViewModel) obj2, (Continuation<? super Unit>) continuation);
                        }
                    };
                    this.label = 1;
                    if (userSwitcher.collect(flowCollector, this) == coroutine_suspended) {
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
        public AnonymousClass2(FooterActionsViewModel footerActionsViewModel, Ref.ObjectRef<FooterActionsSecurityButtonViewModel> objectRef, LinearLayout linearLayout, TextButtonViewHolder textButtonViewHolder, Ref.ObjectRef<FooterActionsForegroundServicesButtonViewModel> objectRef2, NumberButtonViewHolder numberButtonViewHolder, TextButtonViewHolder textButtonViewHolder2, Ref.ObjectRef<FooterActionsButtonViewModel> objectRef3, IconButtonViewHolder iconButtonViewHolder, Continuation<? super AnonymousClass2> continuation) {
            super(2, continuation);
            this.$viewModel = footerActionsViewModel;
            this.$previousSecurity = objectRef;
            this.$view = linearLayout;
            this.$securityHolder = textButtonViewHolder;
            this.$previousForegroundServices = objectRef2;
            this.$foregroundServicesWithNumberHolder = numberButtonViewHolder;
            this.$foregroundServicesWithTextHolder = textButtonViewHolder2;
            this.$previousUserSwitcher = objectRef3;
            this.$userSwitcherHolder = iconButtonViewHolder;
        }

        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            AnonymousClass2 anonymousClass2 = new AnonymousClass2(this.$viewModel, this.$previousSecurity, this.$view, this.$securityHolder, this.$previousForegroundServices, this.$foregroundServicesWithNumberHolder, this.$foregroundServicesWithTextHolder, this.$previousUserSwitcher, this.$userSwitcherHolder, continuation);
            anonymousClass2.L$0 = obj;
            return anonymousClass2;
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
                BuildersKt.launch$default(coroutineScope, (CoroutineContext) null, (CoroutineStart) null, new AnonymousClass1(this.$viewModel, this.$previousSecurity, this.$view, this.$securityHolder, null), 3, (Object) null);
                BuildersKt.launch$default(coroutineScope, (CoroutineContext) null, (CoroutineStart) null, new C00472(this.$viewModel, this.$previousForegroundServices, this.$foregroundServicesWithNumberHolder, this.$foregroundServicesWithTextHolder, null), 3, (Object) null);
                BuildersKt.launch$default(coroutineScope, (CoroutineContext) null, (CoroutineStart) null, new AnonymousClass3(this.$viewModel, this.$previousUserSwitcher, this.$userSwitcherHolder, null), 3, (Object) null);
                return Unit.INSTANCE;
            }
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FooterActionsViewBinder$bind$1(LifecycleOwner lifecycleOwner, FooterActionsViewModel footerActionsViewModel, LinearLayout linearLayout, Ref.ObjectRef<FooterActionsSecurityButtonViewModel> objectRef, TextButtonViewHolder textButtonViewHolder, Ref.ObjectRef<FooterActionsForegroundServicesButtonViewModel> objectRef2, NumberButtonViewHolder numberButtonViewHolder, TextButtonViewHolder textButtonViewHolder2, Ref.ObjectRef<FooterActionsButtonViewModel> objectRef3, IconButtonViewHolder iconButtonViewHolder, Continuation<? super FooterActionsViewBinder$bind$1> continuation) {
        super(3, continuation);
        this.$qsVisibilityLifecycleOwner = lifecycleOwner;
        this.$viewModel = footerActionsViewModel;
        this.$view = linearLayout;
        this.$previousSecurity = objectRef;
        this.$securityHolder = textButtonViewHolder;
        this.$previousForegroundServices = objectRef2;
        this.$foregroundServicesWithNumberHolder = numberButtonViewHolder;
        this.$foregroundServicesWithTextHolder = textButtonViewHolder2;
        this.$previousUserSwitcher = objectRef3;
        this.$userSwitcherHolder = iconButtonViewHolder;
    }

    /* JADX DEBUG: Method merged with bridge method */
    public final Object invoke(LifecycleOwner lifecycleOwner, View view, Continuation<? super Unit> continuation) {
        FooterActionsViewBinder$bind$1 footerActionsViewBinder$bind$1 = new FooterActionsViewBinder$bind$1(this.$qsVisibilityLifecycleOwner, this.$viewModel, this.$view, this.$previousSecurity, this.$securityHolder, this.$previousForegroundServices, this.$foregroundServicesWithNumberHolder, this.$foregroundServicesWithTextHolder, this.$previousUserSwitcher, this.$userSwitcherHolder, continuation);
        footerActionsViewBinder$bind$1.L$0 = lifecycleOwner;
        return footerActionsViewBinder$bind$1.invokeSuspend(Unit.INSTANCE);
    }

    public final Object invokeSuspend(Object obj) {
        Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            BuildersKt.launch$default(LifecycleOwnerKt.getLifecycleScope((LifecycleOwner) this.L$0), (CoroutineContext) null, (CoroutineStart) null, new AnonymousClass1(this.$viewModel, this.$view, null), 3, (Object) null);
            LifecycleOwner lifecycleOwner = this.$qsVisibilityLifecycleOwner;
            Lifecycle.State state = Lifecycle.State.RESUMED;
            AnonymousClass2 anonymousClass2 = new AnonymousClass2(this.$viewModel, this.$previousSecurity, this.$view, this.$securityHolder, this.$previousForegroundServices, this.$foregroundServicesWithNumberHolder, this.$foregroundServicesWithTextHolder, this.$previousUserSwitcher, this.$userSwitcherHolder, null);
            this.label = 1;
            if (RepeatOnLifecycleKt.repeatOnLifecycle(lifecycleOwner, state, (Function2<? super CoroutineScope, ? super Continuation<? super Unit>, ? extends Object>) anonymousClass2, (Continuation<? super Unit>) this) == coroutine_suspended) {
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