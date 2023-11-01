package com.android.systemui.flags;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.AdaptedFunctionReference;
import kotlin.jvm.internal.FunctionReferenceImpl;
import org.json.JSONObject;

/* loaded from: mainsysui33.jar:com/android/systemui/flags/BooleanFlagSerializer.class */
public final class BooleanFlagSerializer extends FlagSerializer<Boolean> {
    public static final BooleanFlagSerializer INSTANCE = new BooleanFlagSerializer();

    /* renamed from: com.android.systemui.flags.BooleanFlagSerializer$1  reason: invalid class name */
    /* loaded from: mainsysui33.jar:com/android/systemui/flags/BooleanFlagSerializer$1.class */
    public final /* synthetic */ class AnonymousClass1 extends AdaptedFunctionReference implements Function3<JSONObject, String, Boolean, Unit> {
        public static final AnonymousClass1 INSTANCE = new AnonymousClass1();

        public AnonymousClass1() {
            super(3, JSONObject.class, "put", "put(Ljava/lang/String;Z)Lorg/json/JSONObject;", 8);
        }

        public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2, Object obj3) {
            invoke((JSONObject) obj, (String) obj2, ((Boolean) obj3).booleanValue());
            return Unit.INSTANCE;
        }

        public final void invoke(JSONObject jSONObject, String str, boolean z) {
            jSONObject.put(str, z);
        }
    }

    /* renamed from: com.android.systemui.flags.BooleanFlagSerializer$2  reason: invalid class name */
    /* loaded from: mainsysui33.jar:com/android/systemui/flags/BooleanFlagSerializer$2.class */
    public final /* synthetic */ class AnonymousClass2 extends FunctionReferenceImpl implements Function2<JSONObject, String, Boolean> {
        public static final AnonymousClass2 INSTANCE = new AnonymousClass2();

        public AnonymousClass2() {
            super(2, JSONObject.class, "getBoolean", "getBoolean(Ljava/lang/String;)Z", 0);
        }

        /* JADX DEBUG: Method merged with bridge method */
        public final Boolean invoke(JSONObject jSONObject, String str) {
            return Boolean.valueOf(jSONObject.getBoolean(str));
        }
    }

    public BooleanFlagSerializer() {
        super("boolean", AnonymousClass1.INSTANCE, AnonymousClass2.INSTANCE);
    }
}