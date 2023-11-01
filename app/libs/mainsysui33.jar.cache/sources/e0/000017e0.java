package com.android.systemui.flags;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.AdaptedFunctionReference;
import kotlin.jvm.internal.FunctionReferenceImpl;
import org.json.JSONObject;

/* loaded from: mainsysui33.jar:com/android/systemui/flags/IntFlagSerializer.class */
public final class IntFlagSerializer extends FlagSerializer<Integer> {
    public static final IntFlagSerializer INSTANCE = new IntFlagSerializer();

    /* renamed from: com.android.systemui.flags.IntFlagSerializer$1  reason: invalid class name */
    /* loaded from: mainsysui33.jar:com/android/systemui/flags/IntFlagSerializer$1.class */
    public final /* synthetic */ class AnonymousClass1 extends AdaptedFunctionReference implements Function3<JSONObject, String, Integer, Unit> {
        public static final AnonymousClass1 INSTANCE = new AnonymousClass1();

        public AnonymousClass1() {
            super(3, JSONObject.class, "put", "put(Ljava/lang/String;I)Lorg/json/JSONObject;", 8);
        }

        public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2, Object obj3) {
            invoke((JSONObject) obj, (String) obj2, ((Number) obj3).intValue());
            return Unit.INSTANCE;
        }

        public final void invoke(JSONObject jSONObject, String str, int i) {
            jSONObject.put(str, i);
        }
    }

    /* renamed from: com.android.systemui.flags.IntFlagSerializer$2  reason: invalid class name */
    /* loaded from: mainsysui33.jar:com/android/systemui/flags/IntFlagSerializer$2.class */
    public final /* synthetic */ class AnonymousClass2 extends FunctionReferenceImpl implements Function2<JSONObject, String, Integer> {
        public static final AnonymousClass2 INSTANCE = new AnonymousClass2();

        public AnonymousClass2() {
            super(2, JSONObject.class, "getInt", "getInt(Ljava/lang/String;)I", 0);
        }

        /* JADX DEBUG: Method merged with bridge method */
        public final Integer invoke(JSONObject jSONObject, String str) {
            return Integer.valueOf(jSONObject.getInt(str));
        }
    }

    public IntFlagSerializer() {
        super("int", AnonymousClass1.INSTANCE, AnonymousClass2.INSTANCE);
    }
}