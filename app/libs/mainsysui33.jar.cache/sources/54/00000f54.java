package com.android.systemui;

import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.FlagListenable;
import com.android.systemui.flags.Flags;
import kotlin.KotlinNothingValueException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.DebugProbesKt;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CancellableContinuationImpl;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineStart;

@DebugMetadata(c = "com.android.systemui.ChooserSelector$start$1", f = "ChooserSelector.kt", l = {43, 86}, m = "invokeSuspend")
/* loaded from: mainsysui33.jar:com/android/systemui/ChooserSelector$start$1.class */
public final class ChooserSelector$start$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    private /* synthetic */ Object L$0;
    public Object L$1;
    public int label;
    public final /* synthetic */ ChooserSelector this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ChooserSelector$start$1(ChooserSelector chooserSelector, Continuation<? super ChooserSelector$start$1> continuation) {
        super(2, continuation);
        this.this$0 = chooserSelector;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        ChooserSelector$start$1 chooserSelector$start$1 = new ChooserSelector$start$1(this.this$0, continuation);
        chooserSelector$start$1.L$0 = obj;
        return chooserSelector$start$1;
    }

    /* JADX DEBUG: Method merged with bridge method */
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return create(coroutineScope, continuation).invokeSuspend(Unit.INSTANCE);
    }

    public final Object invokeSuspend(Object obj) {
        FlagListenable.Listener listener;
        FeatureFlags featureFlags;
        Object updateUnbundledChooserEnabled;
        ChooserSelector chooserSelector;
        ChooserSelector chooserSelector2;
        ChooserSelector chooserSelector3;
        FeatureFlags featureFlags2;
        FlagListenable.Listener listener2;
        Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        try {
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                final CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
                final ChooserSelector chooserSelector4 = this.this$0;
                listener = new FlagListenable.Listener() { // from class: com.android.systemui.ChooserSelector$start$1$listener$1

                    @DebugMetadata(c = "com.android.systemui.ChooserSelector$start$1$listener$1$1", f = "ChooserSelector.kt", l = {38}, m = "invokeSuspend")
                    /* renamed from: com.android.systemui.ChooserSelector$start$1$listener$1$1  reason: invalid class name */
                    /* loaded from: mainsysui33.jar:com/android/systemui/ChooserSelector$start$1$listener$1$1.class */
                    public static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
                        public int label;
                        public final /* synthetic */ ChooserSelector this$0;

                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        public AnonymousClass1(ChooserSelector chooserSelector, Continuation<? super AnonymousClass1> continuation) {
                            super(2, continuation);
                            this.this$0 = chooserSelector;
                        }

                        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                            return new AnonymousClass1(this.this$0, continuation);
                        }

                        /* JADX DEBUG: Method merged with bridge method */
                        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
                            return create(coroutineScope, continuation).invokeSuspend(Unit.INSTANCE);
                        }

                        public final Object invokeSuspend(Object obj) {
                            Object updateUnbundledChooserEnabled;
                            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
                            int i = this.label;
                            if (i == 0) {
                                ResultKt.throwOnFailure(obj);
                                ChooserSelector chooserSelector = this.this$0;
                                this.label = 1;
                                updateUnbundledChooserEnabled = chooserSelector.updateUnbundledChooserEnabled(this);
                                if (updateUnbundledChooserEnabled == coroutine_suspended) {
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

                    @Override // com.android.systemui.flags.FlagListenable.Listener
                    public final void onFlagChanged(FlagListenable.FlagEvent flagEvent) {
                        if (flagEvent.getFlagId() == Flags.INSTANCE.getCHOOSER_UNBUNDLED().getId()) {
                            BuildersKt.launch$default(coroutineScope, (CoroutineContext) null, (CoroutineStart) null, new AnonymousClass1(chooserSelector4, null), 3, (Object) null);
                            flagEvent.requestNoRestart();
                        }
                    }
                };
                featureFlags = this.this$0.featureFlags;
                featureFlags.addListener(Flags.INSTANCE.getCHOOSER_UNBUNDLED(), listener);
                ChooserSelector chooserSelector5 = this.this$0;
                this.L$0 = listener;
                this.label = 1;
                updateUnbundledChooserEnabled = chooserSelector5.updateUnbundledChooserEnabled(this);
                if (updateUnbundledChooserEnabled == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else if (i != 1) {
                if (i != 2) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                chooserSelector3 = (ChooserSelector) this.L$1;
                FlagListenable.Listener listener3 = (FlagListenable.Listener) this.L$0;
                chooserSelector2 = chooserSelector3;
                listener2 = listener3;
                try {
                    ResultKt.throwOnFailure(obj);
                    listener = listener3;
                    ChooserSelector chooserSelector6 = chooserSelector3;
                    chooserSelector2 = chooserSelector3;
                    listener2 = listener;
                    throw new KotlinNothingValueException();
                } catch (Throwable th) {
                    th = th;
                    listener = listener2;
                    featureFlags2 = chooserSelector2.featureFlags;
                    featureFlags2.removeListener(listener);
                    throw th;
                }
            } else {
                ResultKt.throwOnFailure(obj);
                listener = (FlagListenable.Listener) this.L$0;
            }
            this.L$0 = listener;
            this.L$1 = chooserSelector;
            this.label = 2;
            CancellableContinuationImpl cancellableContinuationImpl = new CancellableContinuationImpl(IntrinsicsKt__IntrinsicsJvmKt.intercepted(this), 1);
            cancellableContinuationImpl.initCancellability();
            Object result = cancellableContinuationImpl.getResult();
            if (result == IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
                DebugProbesKt.probeCoroutineSuspended(this);
            }
            if (result == coroutine_suspended) {
                return coroutine_suspended;
            }
            chooserSelector3 = chooserSelector;
            ChooserSelector chooserSelector62 = chooserSelector3;
            chooserSelector2 = chooserSelector3;
            listener2 = listener;
            throw new KotlinNothingValueException();
        } catch (Throwable th2) {
            th = th2;
            chooserSelector2 = chooserSelector;
            featureFlags2 = chooserSelector2.featureFlags;
            featureFlags2.removeListener(listener);
            throw th;
        }
        chooserSelector = this.this$0;
    }
}