package com.android.systemui.people.ui.view;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.RepeatOnLifecycleKt;
import com.android.systemui.people.ui.viewmodel.PeopleViewModel;
import kotlin.KotlinNothingValueException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.StateFlow;

@DebugMetadata(c = "com.android.systemui.people.ui.view.PeopleViewBinder$bind$1", f = "PeopleViewBinder.kt", l = {79}, m = "invokeSuspend")
/* loaded from: mainsysui33.jar:com/android/systemui/people/ui/view/PeopleViewBinder$bind$1.class */
public final class PeopleViewBinder$bind$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    public final /* synthetic */ LifecycleOwner $lifecycleOwner;
    public final /* synthetic */ Function1<PeopleViewModel.Result, Unit> $onResult;
    public final /* synthetic */ PeopleViewModel $viewModel;
    public int label;

    @DebugMetadata(c = "com.android.systemui.people.ui.view.PeopleViewBinder$bind$1$1", f = "PeopleViewBinder.kt", l = {80}, m = "invokeSuspend")
    /* renamed from: com.android.systemui.people.ui.view.PeopleViewBinder$bind$1$1  reason: invalid class name */
    /* loaded from: mainsysui33.jar:com/android/systemui/people/ui/view/PeopleViewBinder$bind$1$1.class */
    public static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        public final /* synthetic */ Function1<PeopleViewModel.Result, Unit> $onResult;
        public final /* synthetic */ PeopleViewModel $viewModel;
        public int label;

        /* JADX DEBUG: Multi-variable search result rejected for r6v0, resolved type: kotlin.jvm.functions.Function1<? super com.android.systemui.people.ui.viewmodel.PeopleViewModel$Result, kotlin.Unit> */
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        public AnonymousClass1(PeopleViewModel peopleViewModel, Function1<? super PeopleViewModel.Result, Unit> function1, Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
            this.$viewModel = peopleViewModel;
            this.$onResult = function1;
        }

        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return new AnonymousClass1(this.$viewModel, this.$onResult, continuation);
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
                StateFlow<PeopleViewModel.Result> result = this.$viewModel.getResult();
                final PeopleViewModel peopleViewModel = this.$viewModel;
                final Function1<PeopleViewModel.Result, Unit> function1 = this.$onResult;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.people.ui.view.PeopleViewBinder.bind.1.1.1
                    public final Object emit(PeopleViewModel.Result result2, Continuation<? super Unit> continuation) {
                        if (result2 != null) {
                            PeopleViewModel.this.clearResult();
                            function1.invoke(result2);
                        }
                        return Unit.INSTANCE;
                    }

                    public /* bridge */ /* synthetic */ Object emit(Object obj2, Continuation continuation) {
                        return emit((PeopleViewModel.Result) obj2, (Continuation<? super Unit>) continuation);
                    }
                };
                this.label = 1;
                if (result.collect(flowCollector, this) == coroutine_suspended) {
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

    /* JADX DEBUG: Multi-variable search result rejected for r7v0, resolved type: kotlin.jvm.functions.Function1<? super com.android.systemui.people.ui.viewmodel.PeopleViewModel$Result, kotlin.Unit> */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public PeopleViewBinder$bind$1(LifecycleOwner lifecycleOwner, PeopleViewModel peopleViewModel, Function1<? super PeopleViewModel.Result, Unit> function1, Continuation<? super PeopleViewBinder$bind$1> continuation) {
        super(2, continuation);
        this.$lifecycleOwner = lifecycleOwner;
        this.$viewModel = peopleViewModel;
        this.$onResult = function1;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new PeopleViewBinder$bind$1(this.$lifecycleOwner, this.$viewModel, this.$onResult, continuation);
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
            LifecycleOwner lifecycleOwner = this.$lifecycleOwner;
            Lifecycle.State state = Lifecycle.State.CREATED;
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$viewModel, this.$onResult, null);
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