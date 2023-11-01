package com.android.systemui.people.ui.view;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.RepeatOnLifecycleKt;
import com.android.systemui.people.ui.viewmodel.PeopleViewModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

@DebugMetadata(c = "com.android.systemui.people.ui.view.PeopleViewBinder$bind$3", f = "PeopleViewBinder.kt", l = {116}, m = "invokeSuspend")
/* loaded from: mainsysui33.jar:com/android/systemui/people/ui/view/PeopleViewBinder$bind$3.class */
public final class PeopleViewBinder$bind$3 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    public final /* synthetic */ LifecycleOwner $lifecycleOwner;
    public final /* synthetic */ PeopleViewModel $viewModel;
    public int label;

    @DebugMetadata(c = "com.android.systemui.people.ui.view.PeopleViewBinder$bind$3$1", f = "PeopleViewBinder.kt", l = {}, m = "invokeSuspend")
    /* renamed from: com.android.systemui.people.ui.view.PeopleViewBinder$bind$3$1  reason: invalid class name */
    /* loaded from: mainsysui33.jar:com/android/systemui/people/ui/view/PeopleViewBinder$bind$3$1.class */
    public static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        public final /* synthetic */ PeopleViewModel $viewModel;
        public int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(PeopleViewModel peopleViewModel, Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
            this.$viewModel = peopleViewModel;
        }

        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return new AnonymousClass1(this.$viewModel, continuation);
        }

        /* JADX DEBUG: Method merged with bridge method */
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return create(coroutineScope, continuation).invokeSuspend(Unit.INSTANCE);
        }

        public final Object invokeSuspend(Object obj) {
            IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label == 0) {
                ResultKt.throwOnFailure(obj);
                this.$viewModel.onTileRefreshRequested();
                return Unit.INSTANCE;
            }
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PeopleViewBinder$bind$3(LifecycleOwner lifecycleOwner, PeopleViewModel peopleViewModel, Continuation<? super PeopleViewBinder$bind$3> continuation) {
        super(2, continuation);
        this.$lifecycleOwner = lifecycleOwner;
        this.$viewModel = peopleViewModel;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new PeopleViewBinder$bind$3(this.$lifecycleOwner, this.$viewModel, continuation);
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
            Lifecycle.State state = Lifecycle.State.RESUMED;
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$viewModel, null);
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