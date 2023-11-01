package com.airbnb.lottie.parser.moshi;

import androidx.recyclerview.widget.RecyclerView;
import java.io.Closeable;
import java.io.IOException;
import java.util.Arrays;
import okio.Buffer;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.ByteString;

/* loaded from: mainsysui33.jar:com/airbnb/lottie/parser/moshi/JsonReader.class */
public abstract class JsonReader implements Closeable {
    public static final String[] REPLACEMENT_CHARS = new String[RecyclerView.ViewHolder.FLAG_IGNORE];
    public boolean failOnUnknown;
    public boolean lenient;
    public int stackSize;
    public int[] scopes = new int[32];
    public String[] pathNames = new String[32];
    public int[] pathIndices = new int[32];

    /* loaded from: mainsysui33.jar:com/airbnb/lottie/parser/moshi/JsonReader$Options.class */
    public static final class Options {
        public final okio.Options doubleQuoteSuffix;
        public final String[] strings;

        public Options(String[] strArr, okio.Options options) {
            this.strings = strArr;
            this.doubleQuoteSuffix = options;
        }

        public static Options of(String... strArr) {
            try {
                ByteString[] byteStringArr = new ByteString[strArr.length];
                Buffer buffer = new Buffer();
                for (int i = 0; i < strArr.length; i++) {
                    JsonReader.string(buffer, strArr[i]);
                    buffer.readByte();
                    byteStringArr[i] = buffer.readByteString();
                }
                return new Options((String[]) strArr.clone(), okio.Options.of(byteStringArr));
            } catch (IOException e) {
                throw new AssertionError(e);
            }
        }
    }

    /* loaded from: mainsysui33.jar:com/airbnb/lottie/parser/moshi/JsonReader$Token.class */
    public enum Token {
        BEGIN_ARRAY,
        END_ARRAY,
        BEGIN_OBJECT,
        END_OBJECT,
        NAME,
        STRING,
        NUMBER,
        BOOLEAN,
        NULL,
        END_DOCUMENT
    }

    static {
        for (int i = 0; i <= 31; i++) {
            REPLACEMENT_CHARS[i] = String.format("\\u%04x", Integer.valueOf(i));
        }
        String[] strArr = REPLACEMENT_CHARS;
        strArr[34] = "\\\"";
        strArr[92] = "\\\\";
        strArr[9] = "\\t";
        strArr[8] = "\\b";
        strArr[10] = "\\n";
        strArr[13] = "\\r";
        strArr[12] = "\\f";
    }

    public static JsonReader of(BufferedSource bufferedSource) {
        return new JsonUtf8Reader(bufferedSource);
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x006a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static void string(BufferedSink bufferedSink, String str) throws IOException {
        int i;
        int i2;
        String str2;
        String[] strArr = REPLACEMENT_CHARS;
        bufferedSink.writeByte(34);
        int length = str.length();
        int i3 = 0;
        int i4 = 0;
        while (true) {
            i = i4;
            if (i3 >= length) {
                break;
            }
            char charAt = str.charAt(i3);
            if (charAt < 128) {
                String str3 = strArr[charAt];
                str2 = str3;
                if (str3 == null) {
                    i2 = i;
                }
                if (i < i3) {
                    bufferedSink.writeUtf8(str, i, i3);
                }
                bufferedSink.writeUtf8(str2);
                i2 = i3 + 1;
            } else {
                if (charAt == 8232) {
                    str2 = "\\u2028";
                } else {
                    i2 = i;
                    if (charAt == 8233) {
                        str2 = "\\u2029";
                    }
                }
                if (i < i3) {
                }
                bufferedSink.writeUtf8(str2);
                i2 = i3 + 1;
            }
            i3++;
            i4 = i2;
        }
        if (i < length) {
            bufferedSink.writeUtf8(str, i, length);
        }
        bufferedSink.writeByte(34);
    }

    public abstract void beginArray() throws IOException;

    public abstract void beginObject() throws IOException;

    public abstract void endArray() throws IOException;

    public abstract void endObject() throws IOException;

    public final String getPath() {
        return JsonScope.getPath(this.stackSize, this.scopes, this.pathNames, this.pathIndices);
    }

    public abstract boolean hasNext() throws IOException;

    public abstract boolean nextBoolean() throws IOException;

    public abstract double nextDouble() throws IOException;

    public abstract int nextInt() throws IOException;

    public abstract String nextName() throws IOException;

    public abstract String nextString() throws IOException;

    public abstract Token peek() throws IOException;

    public final void pushScope(int i) {
        int i2 = this.stackSize;
        int[] iArr = this.scopes;
        if (i2 == iArr.length) {
            if (i2 == 256) {
                throw new JsonDataException("Nesting too deep at " + getPath());
            }
            this.scopes = Arrays.copyOf(iArr, iArr.length * 2);
            String[] strArr = this.pathNames;
            this.pathNames = (String[]) Arrays.copyOf(strArr, strArr.length * 2);
            int[] iArr2 = this.pathIndices;
            this.pathIndices = Arrays.copyOf(iArr2, iArr2.length * 2);
        }
        int[] iArr3 = this.scopes;
        int i3 = this.stackSize;
        this.stackSize = i3 + 1;
        iArr3[i3] = i;
    }

    public abstract int selectName(Options options) throws IOException;

    public abstract void skipName() throws IOException;

    public abstract void skipValue() throws IOException;

    public final JsonEncodingException syntaxError(String str) throws JsonEncodingException {
        throw new JsonEncodingException(str + " at path " + getPath());
    }
}