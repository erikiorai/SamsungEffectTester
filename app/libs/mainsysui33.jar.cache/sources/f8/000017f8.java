package com.android.systemui.flags;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.AdaptedFunctionReference;
import kotlin.jvm.internal.FunctionReferenceImpl;
import org.json.JSONObject;

/* loaded from: mainsysui33.jar:com/android/systemui/flags/StringFlagSerializer.class */
public final class StringFlagSerializer extends FlagSerializer<String> {
    public static final StringFlagSerializer INSTANCE = new StringFlagSerializer();

    /* renamed from: com.android.systemui.flags.StringFlagSerializer$1  reason: invalid class name */
    /* loaded from: mainsysui33.jar:com/android/systemui/flags/StringFlagSerializer$1.class */
    public final /* synthetic */ class AnonymousClass1 extends AdaptedFunctionReference implements Function3<JSONObject, String, Object, Unit> {
        public static final AnonymousClass1 INSTANCE = new AnonymousClass1();

        public AnonymousClass1() {
            super(3, JSONObject.class, "put", "put(Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;", 8);
        }

        public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2, Object obj3) {
            invoke((JSONObject) obj, (String) obj2, obj3);
            return Unit.INSTANCE;
        }

        public final void invoke(JSONObject jSONObject, String str, Object obj) {
            jSONObject.put(str, obj);
        }
    }

    /* renamed from: com.android.systemui.flags.StringFlagSerializer$2  reason: invalid class name */
    /* loaded from: mainsysui33.jar:com/android/systemui/flags/StringFlagSerializer$2.class */
    public final /* synthetic */ class AnonymousClass2 extends FunctionReferenceImpl implements Function2<JSONObject, String, String> {
        public static final AnonymousClass2 INSTANCE = new AnonymousClass2();

        public AnonymousClass2() {
            super(2, JSONObject.class, "getString", "getString(Ljava/lang/String;)Ljava/lang/String;", 0);
        }

        /* JADX DEBUG: Method merged with bridge method */
        public final String invoke(JSONObject jSONObject, String str) {
            return jSONObject.getString(str);
        }
    }

    public StringFlagSerializer() {
        super("string", AnonymousClass1.INSTANCE, AnonymousClass2.INSTANCE);
    }
}