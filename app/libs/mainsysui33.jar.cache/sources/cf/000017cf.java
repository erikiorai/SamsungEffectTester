package com.android.systemui.flags;

import android.util.Log;
import androidx.core.graphics.drawable.IconCompat;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: mainsysui33.jar:com/android/systemui/flags/FlagSerializer.class */
public abstract class FlagSerializer<T> {
    public final Function2<JSONObject, String, T> getter;
    public final Function3<JSONObject, String, T, Unit> setter;
    public final String type;

    /* JADX DEBUG: Multi-variable search result rejected for r5v0, resolved type: kotlin.jvm.functions.Function3<? super org.json.JSONObject, ? super java.lang.String, ? super T, kotlin.Unit> */
    /* JADX DEBUG: Multi-variable search result rejected for r6v0, resolved type: kotlin.jvm.functions.Function2<? super org.json.JSONObject, ? super java.lang.String, ? extends T> */
    /* JADX WARN: Multi-variable type inference failed */
    public FlagSerializer(String str, Function3<? super JSONObject, ? super String, ? super T, Unit> function3, Function2<? super JSONObject, ? super String, ? extends T> function2) {
        this.type = str;
        this.setter = function3;
        this.getter = function2;
    }

    /* JADX DEBUG: Multi-variable search result rejected for r0v20, resolved type: java.lang.Object */
    /* JADX WARN: Multi-variable type inference failed */
    public final T fromSettingsData(String str) {
        if (str != null) {
            if (str.length() == 0) {
                return null;
            }
            try {
                JSONObject jSONObject = new JSONObject(str);
                T t = null;
                if (Intrinsics.areEqual(jSONObject.getString(IconCompat.EXTRA_TYPE), this.type)) {
                    t = this.getter.invoke(jSONObject, "value");
                }
                return t;
            } catch (JSONException e) {
                Log.w("FlagSerializer", "read error", e);
                throw new InvalidFlagStorageException();
            }
        }
        return null;
    }

    public final String toSettingsData(T t) {
        String str;
        try {
            JSONObject put = new JSONObject().put(IconCompat.EXTRA_TYPE, this.type);
            this.setter.invoke(put, "value", t);
            str = put.toString();
        } catch (JSONException e) {
            Log.w("FlagSerializer", "write error", e);
            str = null;
        }
        return str;
    }
}