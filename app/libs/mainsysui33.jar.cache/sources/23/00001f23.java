package com.android.systemui.people.ui.view;

import android.view.ViewGroup;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.RepeatOnLifecycleKt;
import com.android.systemui.people.ui.viewmodel.PeopleTileViewModel;
import com.android.systemui.people.ui.viewmodel.PeopleViewModel;
import java.util.List;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.CallableReference;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;

@DebugMetadata(c = "com.android.systemui.people.ui.view.PeopleViewBinder$bind$2", f = "PeopleViewBinder.kt", l = {91}, m = "invokeSuspend")
/* loaded from: mainsysui33.jar:com/android/systemui/people/ui/view/PeopleViewBinder$bind$2.class */
public final class PeopleViewBinder$bind$2 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    public final /* synthetic */ LifecycleOwner $lifecycleOwner;
    public final /* synthetic */ ViewGroup $view;
    public final /* synthetic */ PeopleViewModel $viewModel;
    public int label;

    @DebugMetadata(c = "com.android.systemui.people.ui.view.PeopleViewBinder$bind$2$1", f = "PeopleViewBinder.kt", l = {98}, m = "invokeSuspend")
    /* renamed from: com.android.systemui.people.ui.view.PeopleViewBinder$bind$2$1  reason: invalid class name */
    /* loaded from: mainsysui33.jar:com/android/systemui/people/ui/view/PeopleViewBinder$bind$2$1.class */
    public static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        public final /* synthetic */ ViewGroup $view;
        public final /* synthetic */ PeopleViewModel $viewModel;
        public int label;

        @DebugMetadata(c = "com.android.systemui.people.ui.view.PeopleViewBinder$bind$2$1$1", f = "PeopleViewBinder.kt", l = {}, m = "invokeSuspend")
        /* renamed from: com.android.systemui.people.ui.view.PeopleViewBinder$bind$2$1$1  reason: invalid class name and collision with other inner class name */
        /* loaded from: mainsysui33.jar:com/android/systemui/people/ui/view/PeopleViewBinder$bind$2$1$1.class */
        public static final class C00391 extends SuspendLambda implements Function3<List<? extends PeopleTileViewModel>, List<? extends PeopleTileViewModel>, Continuation<? super Pair<? extends List<? extends PeopleTileViewModel>, ? extends List<? extends PeopleTileViewModel>>>, Object> {
            public /* synthetic */ Object L$0;
            public /* synthetic */ Object L$1;
            public int label;

            public C00391(Continuation<? super C00391> continuation) {
                super(3, continuation);
            }

            /* JADX DEBUG: Method merged with bridge method */
            public final Object invoke(List<PeopleTileViewModel> list, List<PeopleTileViewModel> list2, Continuation<? super Pair<? extends List<PeopleTileViewModel>, ? extends List<PeopleTileViewModel>>> continuation) {
                C00391 c00391 = new C00391(continuation);
                c00391.L$0 = list;
                c00391.L$1 = list2;
                return c00391.invokeSuspend(Unit.INSTANCE);
            }

            public final Object invokeSuspend(Object obj) {
                IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label == 0) {
                    ResultKt.throwOnFailure(obj);
                    return TuplesKt.to((List) this.L$0, (List) this.L$1);
                }
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(PeopleViewModel peopleViewModel, ViewGroup viewGroup, Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
            this.$viewModel = peopleViewModel;
            this.$view = viewGroup;
        }

        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return new AnonymousClass1(this.$viewModel, this.$view, continuation);
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
                Flow combine = FlowKt.combine(this.$viewModel.getPriorityTiles(), this.$viewModel.getRecentTiles(), new C00391(null));
                final ViewGroup viewGroup = this.$view;
                final PeopleViewModel peopleViewModel = this.$viewModel;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.people.ui.view.PeopleViewBinder.bind.2.1.2

                    /* renamed from: com.android.systemui.people.ui.view.PeopleViewBinder$bind$2$1$2$1  reason: invalid class name and collision with other inner class name */
                    /* loaded from: mainsysui33.jar:com/android/systemui/people/ui/view/PeopleViewBinder$bind$2$1$2$1.class */
                    public final /* synthetic */ class C00401 extends FunctionReferenceImpl implements Function1<PeopleTileViewModel, Unit> {
                        public C00401(Object obj) {
                            super(1, obj, PeopleViewModel.class, "onTileClicked", "onTileClicked(Lcom/android/systemui/people/ui/viewmodel/PeopleTileViewModel;)V", 0);
                        }

                        public /* bridge */ /* synthetic */ Object invoke(Object obj) {
                            invoke((PeopleTileViewModel) obj);
                            return Unit.INSTANCE;
                        }

                        public final void invoke(PeopleTileViewModel peopleTileViewModel) {
                            ((PeopleViewModel) ((CallableReference) this).receiver).onTileClicked(peopleTileViewModel);
                        }
                    }

                    /* renamed from: com.android.systemui.people.ui.view.PeopleViewBinder$bind$2$1$2$2  reason: invalid class name and collision with other inner class name */
                    /* loaded from: mainsysui33.jar:com/android/systemui/people/ui/view/PeopleViewBinder$bind$2$1$2$2.class */
                    public final /* synthetic */ class C00412 extends FunctionReferenceImpl implements Function0<Unit> {
                        public C00412(Object obj) {
                            super(0, obj, PeopleViewModel.class, "onUserJourneyCancelled", "onUserJourneyCancelled()V", 0);
                        }

                        public /* bridge */ /* synthetic */ Object invoke() {
                            m3549invoke();
                            return Unit.INSTANCE;
                        }

                        /* renamed from: invoke  reason: collision with other method in class */
                        public final void m3549invoke() {
                            ((PeopleViewModel) ((CallableReference) this).receiver).onUserJourneyCancelled();
                        }
                    }

                    public /* bridge */ /* synthetic */ Object emit(Object obj2, Continuation continuation) {
                        return emit((Pair) obj2, (Continuation<? super Unit>) continuation);
                    }

                    public final Object emit(Pair<? extends List<PeopleTileViewModel>, ? extends List<PeopleTileViewModel>> pair, Continuation<? super Unit> continuation) {
                        List list = (List) pair.component1();
                        List list2 = (List) pair.component2();
                        if ((!list.isEmpty()) || (!list2.isEmpty())) {
                            PeopleViewBinder.INSTANCE.setConversationsContent(viewGroup, list, list2, new C00401(peopleViewModel));
                        } else {
                            PeopleViewBinder.INSTANCE.setNoConversationsContent(viewGroup, new C00412(peopleViewModel));
                        }
                        return Unit.INSTANCE;
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

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PeopleViewBinder$bind$2(LifecycleOwner lifecycleOwner, PeopleViewModel peopleViewModel, ViewGroup viewGroup, Continuation<? super PeopleViewBinder$bind$2> continuation) {
        super(2, continuation);
        this.$lifecycleOwner = lifecycleOwner;
        this.$viewModel = peopleViewModel;
        this.$view = viewGroup;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new PeopleViewBinder$bind$2(this.$lifecycleOwner, this.$viewModel, this.$view, continuation);
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
            Lifecycle.State state = Lifecycle.State.STARTED;
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$viewModel, this.$view, null);
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