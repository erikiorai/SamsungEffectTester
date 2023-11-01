package com.android.app.motiontool;

import android.media.permission.SafeCloseable;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.CallableReference;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* loaded from: mainsysui33.jar:com/android/app/motiontool/MotionToolManager$beginTrace$1.class */
public final /* synthetic */ class MotionToolManager$beginTrace$1 extends FunctionReferenceImpl implements Function0<Unit> {
    public MotionToolManager$beginTrace$1(Object obj) {
        super(0, obj, SafeCloseable.class, "close", "close()V", 0);
    }

    public /* bridge */ /* synthetic */ Object invoke() {
        m483invoke();
        return Unit.INSTANCE;
    }

    /* renamed from: invoke  reason: collision with other method in class */
    public final void m483invoke() {
        ((SafeCloseable) ((CallableReference) this).receiver).close();
    }
}