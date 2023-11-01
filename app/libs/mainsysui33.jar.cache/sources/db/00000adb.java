package com.airbnb.lottie.parser.moshi;

import com.airbnb.lottie.parser.moshi.JsonReader;
import java.io.EOFException;
import java.io.IOException;
import okio.Buffer;
import okio.BufferedSource;
import okio.ByteString;

/* loaded from: mainsysui33.jar:com/airbnb/lottie/parser/moshi/JsonUtf8Reader.class */
public final class JsonUtf8Reader extends JsonReader {
    public final Buffer buffer;
    public int peeked = 0;
    public long peekedLong;
    public int peekedNumberLength;
    public String peekedString;
    public final BufferedSource source;
    public static final ByteString SINGLE_QUOTE_OR_SLASH = ByteString.encodeUtf8("'\\");
    public static final ByteString DOUBLE_QUOTE_OR_SLASH = ByteString.encodeUtf8("\"\\");
    public static final ByteString UNQUOTED_STRING_TERMINALS = ByteString.encodeUtf8("{}[]:, \n\t\r\f/\\;#=");
    public static final ByteString LINEFEED_OR_CARRIAGE_RETURN = ByteString.encodeUtf8("\n\r");
    public static final ByteString CLOSING_BLOCK_COMMENT = ByteString.encodeUtf8("*/");

    public JsonUtf8Reader(BufferedSource bufferedSource) {
        if (bufferedSource == null) {
            throw new NullPointerException("source == null");
        }
        this.source = bufferedSource;
        this.buffer = bufferedSource.getBuffer();
        pushScope(6);
    }

    @Override // com.airbnb.lottie.parser.moshi.JsonReader
    public void beginArray() throws IOException {
        int i = this.peeked;
        int i2 = i;
        if (i == 0) {
            i2 = doPeek();
        }
        if (i2 == 3) {
            pushScope(1);
            this.pathIndices[this.stackSize - 1] = 0;
            this.peeked = 0;
            return;
        }
        throw new JsonDataException("Expected BEGIN_ARRAY but was " + peek() + " at path " + getPath());
    }

    @Override // com.airbnb.lottie.parser.moshi.JsonReader
    public void beginObject() throws IOException {
        int i = this.peeked;
        int i2 = i;
        if (i == 0) {
            i2 = doPeek();
        }
        if (i2 == 1) {
            pushScope(3);
            this.peeked = 0;
            return;
        }
        throw new JsonDataException("Expected BEGIN_OBJECT but was " + peek() + " at path " + getPath());
    }

    public final void checkLenient() throws IOException {
        if (!this.lenient) {
            throw syntaxError("Use JsonReader.setLenient(true) to accept malformed JSON");
        }
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        this.peeked = 0;
        this.scopes[0] = 8;
        this.stackSize = 1;
        this.buffer.clear();
        this.source.close();
    }

    public final int doPeek() throws IOException {
        int[] iArr = this.scopes;
        int i = this.stackSize;
        int i2 = iArr[i - 1];
        if (i2 == 1) {
            iArr[i - 1] = 2;
        } else if (i2 == 2) {
            int nextNonWhitespace = nextNonWhitespace(true);
            this.buffer.readByte();
            if (nextNonWhitespace != 44) {
                if (nextNonWhitespace != 59) {
                    if (nextNonWhitespace == 93) {
                        this.peeked = 4;
                        return 4;
                    }
                    throw syntaxError("Unterminated array");
                }
                checkLenient();
            }
        } else if (i2 == 3 || i2 == 5) {
            iArr[i - 1] = 4;
            if (i2 == 5) {
                int nextNonWhitespace2 = nextNonWhitespace(true);
                this.buffer.readByte();
                if (nextNonWhitespace2 != 44) {
                    if (nextNonWhitespace2 != 59) {
                        if (nextNonWhitespace2 == 125) {
                            this.peeked = 2;
                            return 2;
                        }
                        throw syntaxError("Unterminated object");
                    }
                    checkLenient();
                }
            }
            int nextNonWhitespace3 = nextNonWhitespace(true);
            if (nextNonWhitespace3 == 34) {
                this.buffer.readByte();
                this.peeked = 13;
                return 13;
            } else if (nextNonWhitespace3 == 39) {
                this.buffer.readByte();
                checkLenient();
                this.peeked = 12;
                return 12;
            } else if (nextNonWhitespace3 != 125) {
                checkLenient();
                if (isLiteral((char) nextNonWhitespace3)) {
                    this.peeked = 14;
                    return 14;
                }
                throw syntaxError("Expected name");
            } else if (i2 != 5) {
                this.buffer.readByte();
                this.peeked = 2;
                return 2;
            } else {
                throw syntaxError("Expected name");
            }
        } else if (i2 == 4) {
            iArr[i - 1] = 5;
            int nextNonWhitespace4 = nextNonWhitespace(true);
            this.buffer.readByte();
            if (nextNonWhitespace4 != 58) {
                if (nextNonWhitespace4 != 61) {
                    throw syntaxError("Expected ':'");
                }
                checkLenient();
                if (this.source.request(1L) && this.buffer.getByte(0L) == 62) {
                    this.buffer.readByte();
                }
            }
        } else if (i2 == 6) {
            iArr[i - 1] = 7;
        } else if (i2 == 7) {
            if (nextNonWhitespace(false) == -1) {
                this.peeked = 18;
                return 18;
            }
            checkLenient();
        } else if (i2 == 8) {
            throw new IllegalStateException("JsonReader is closed");
        }
        int nextNonWhitespace5 = nextNonWhitespace(true);
        if (nextNonWhitespace5 == 34) {
            this.buffer.readByte();
            this.peeked = 9;
            return 9;
        } else if (nextNonWhitespace5 == 39) {
            checkLenient();
            this.buffer.readByte();
            this.peeked = 8;
            return 8;
        } else {
            if (nextNonWhitespace5 != 44 && nextNonWhitespace5 != 59) {
                if (nextNonWhitespace5 == 91) {
                    this.buffer.readByte();
                    this.peeked = 3;
                    return 3;
                } else if (nextNonWhitespace5 != 93) {
                    if (nextNonWhitespace5 == 123) {
                        this.buffer.readByte();
                        this.peeked = 1;
                        return 1;
                    }
                    int peekKeyword = peekKeyword();
                    if (peekKeyword != 0) {
                        return peekKeyword;
                    }
                    int peekNumber = peekNumber();
                    if (peekNumber != 0) {
                        return peekNumber;
                    }
                    if (isLiteral(this.buffer.getByte(0L))) {
                        checkLenient();
                        this.peeked = 10;
                        return 10;
                    }
                    throw syntaxError("Expected value");
                } else if (i2 == 1) {
                    this.buffer.readByte();
                    this.peeked = 4;
                    return 4;
                }
            }
            if (i2 == 1 || i2 == 2) {
                checkLenient();
                this.peeked = 7;
                return 7;
            }
            throw syntaxError("Unexpected value");
        }
    }

    @Override // com.airbnb.lottie.parser.moshi.JsonReader
    public void endArray() throws IOException {
        int i = this.peeked;
        int i2 = i;
        if (i == 0) {
            i2 = doPeek();
        }
        if (i2 != 4) {
            throw new JsonDataException("Expected END_ARRAY but was " + peek() + " at path " + getPath());
        }
        int i3 = this.stackSize - 1;
        this.stackSize = i3;
        int[] iArr = this.pathIndices;
        int i4 = i3 - 1;
        iArr[i4] = iArr[i4] + 1;
        this.peeked = 0;
    }

    @Override // com.airbnb.lottie.parser.moshi.JsonReader
    public void endObject() throws IOException {
        int i = this.peeked;
        int i2 = i;
        if (i == 0) {
            i2 = doPeek();
        }
        if (i2 != 2) {
            throw new JsonDataException("Expected END_OBJECT but was " + peek() + " at path " + getPath());
        }
        int i3 = this.stackSize - 1;
        this.stackSize = i3;
        this.pathNames[i3] = null;
        int[] iArr = this.pathIndices;
        int i4 = i3 - 1;
        iArr[i4] = iArr[i4] + 1;
        this.peeked = 0;
    }

    public final int findName(String str, JsonReader.Options options) {
        int length = options.strings.length;
        for (int i = 0; i < length; i++) {
            if (str.equals(options.strings[i])) {
                this.peeked = 0;
                this.pathNames[this.stackSize - 1] = str;
                return i;
            }
        }
        return -1;
    }

    @Override // com.airbnb.lottie.parser.moshi.JsonReader
    public boolean hasNext() throws IOException {
        int i = this.peeked;
        int i2 = i;
        if (i == 0) {
            i2 = doPeek();
        }
        return (i2 == 2 || i2 == 4 || i2 == 18) ? false : true;
    }

    public final boolean isLiteral(int i) throws IOException {
        if (i == 9 || i == 10 || i == 12 || i == 13 || i == 32) {
            return false;
        }
        if (i != 35) {
            if (i == 44) {
                return false;
            }
            if (i != 47 && i != 61) {
                if (i == 123 || i == 125 || i == 58) {
                    return false;
                }
                if (i != 59) {
                    switch (i) {
                        case 91:
                        case 93:
                            return false;
                        case 92:
                            break;
                        default:
                            return true;
                    }
                }
            }
        }
        checkLenient();
        return false;
    }

    @Override // com.airbnb.lottie.parser.moshi.JsonReader
    public boolean nextBoolean() throws IOException {
        int i = this.peeked;
        int i2 = i;
        if (i == 0) {
            i2 = doPeek();
        }
        if (i2 == 5) {
            this.peeked = 0;
            int[] iArr = this.pathIndices;
            int i3 = this.stackSize - 1;
            iArr[i3] = iArr[i3] + 1;
            return true;
        } else if (i2 == 6) {
            this.peeked = 0;
            int[] iArr2 = this.pathIndices;
            int i4 = this.stackSize - 1;
            iArr2[i4] = iArr2[i4] + 1;
            return false;
        } else {
            throw new JsonDataException("Expected a boolean but was " + peek() + " at path " + getPath());
        }
    }

    @Override // com.airbnb.lottie.parser.moshi.JsonReader
    public double nextDouble() throws IOException {
        int i = this.peeked;
        int i2 = i;
        if (i == 0) {
            i2 = doPeek();
        }
        if (i2 == 16) {
            this.peeked = 0;
            int[] iArr = this.pathIndices;
            int i3 = this.stackSize - 1;
            iArr[i3] = iArr[i3] + 1;
            return this.peekedLong;
        }
        if (i2 == 17) {
            this.peekedString = this.buffer.readUtf8(this.peekedNumberLength);
        } else if (i2 == 9) {
            this.peekedString = nextQuotedValue(DOUBLE_QUOTE_OR_SLASH);
        } else if (i2 == 8) {
            this.peekedString = nextQuotedValue(SINGLE_QUOTE_OR_SLASH);
        } else if (i2 == 10) {
            this.peekedString = nextUnquotedValue();
        } else if (i2 != 11) {
            throw new JsonDataException("Expected a double but was " + peek() + " at path " + getPath());
        }
        this.peeked = 11;
        try {
            double parseDouble = Double.parseDouble(this.peekedString);
            if (this.lenient || !(Double.isNaN(parseDouble) || Double.isInfinite(parseDouble))) {
                this.peekedString = null;
                this.peeked = 0;
                int[] iArr2 = this.pathIndices;
                int i4 = this.stackSize - 1;
                iArr2[i4] = iArr2[i4] + 1;
                return parseDouble;
            }
            throw new JsonEncodingException("JSON forbids NaN and infinities: " + parseDouble + " at path " + getPath());
        } catch (NumberFormatException e) {
            throw new JsonDataException("Expected a double but was " + this.peekedString + " at path " + getPath());
        }
    }

    @Override // com.airbnb.lottie.parser.moshi.JsonReader
    public int nextInt() throws IOException {
        int i = this.peeked;
        int i2 = i;
        if (i == 0) {
            i2 = doPeek();
        }
        if (i2 == 16) {
            long j = this.peekedLong;
            int i3 = (int) j;
            if (j == i3) {
                this.peeked = 0;
                int[] iArr = this.pathIndices;
                int i4 = this.stackSize - 1;
                iArr[i4] = iArr[i4] + 1;
                return i3;
            }
            throw new JsonDataException("Expected an int but was " + this.peekedLong + " at path " + getPath());
        }
        if (i2 == 17) {
            this.peekedString = this.buffer.readUtf8(this.peekedNumberLength);
        } else if (i2 == 9 || i2 == 8) {
            String nextQuotedValue = i2 == 9 ? nextQuotedValue(DOUBLE_QUOTE_OR_SLASH) : nextQuotedValue(SINGLE_QUOTE_OR_SLASH);
            this.peekedString = nextQuotedValue;
            try {
                int parseInt = Integer.parseInt(nextQuotedValue);
                this.peeked = 0;
                int[] iArr2 = this.pathIndices;
                int i5 = this.stackSize - 1;
                iArr2[i5] = iArr2[i5] + 1;
                return parseInt;
            } catch (NumberFormatException e) {
            }
        } else if (i2 != 11) {
            throw new JsonDataException("Expected an int but was " + peek() + " at path " + getPath());
        }
        this.peeked = 11;
        try {
            double parseDouble = Double.parseDouble(this.peekedString);
            int i6 = (int) parseDouble;
            if (i6 == parseDouble) {
                this.peekedString = null;
                this.peeked = 0;
                int[] iArr3 = this.pathIndices;
                int i7 = this.stackSize - 1;
                iArr3[i7] = iArr3[i7] + 1;
                return i6;
            }
            throw new JsonDataException("Expected an int but was " + this.peekedString + " at path " + getPath());
        } catch (NumberFormatException e2) {
            throw new JsonDataException("Expected an int but was " + this.peekedString + " at path " + getPath());
        }
    }

    @Override // com.airbnb.lottie.parser.moshi.JsonReader
    public String nextName() throws IOException {
        String str;
        int i = this.peeked;
        int i2 = i;
        if (i == 0) {
            i2 = doPeek();
        }
        if (i2 == 14) {
            str = nextUnquotedValue();
        } else if (i2 == 13) {
            str = nextQuotedValue(DOUBLE_QUOTE_OR_SLASH);
        } else if (i2 == 12) {
            str = nextQuotedValue(SINGLE_QUOTE_OR_SLASH);
        } else if (i2 != 15) {
            throw new JsonDataException("Expected a name but was " + peek() + " at path " + getPath());
        } else {
            str = this.peekedString;
        }
        this.peeked = 0;
        this.pathNames[this.stackSize - 1] = str;
        return str;
    }

    /* JADX WARN: Code restructure failed: missing block: B:14:0x003d, code lost:
        r4.buffer.skip(r0 - 1);
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x004c, code lost:
        if (r0 != 47) goto L15;
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x005b, code lost:
        if (r4.source.request(2) != false) goto L28;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x005f, code lost:
        return r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x0060, code lost:
        checkLenient();
        r0 = r4.buffer.getByte(1);
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x0072, code lost:
        if (r0 == 42) goto L30;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x0079, code lost:
        if (r0 == 47) goto L40;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x007d, code lost:
        return r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x007e, code lost:
        r4.buffer.readByte();
        r4.buffer.readByte();
        skipToEndOfLine();
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x0095, code lost:
        r4.buffer.readByte();
        r4.buffer.readByte();
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x00a9, code lost:
        if (skipToEndOfBlockComment() == false) goto L33;
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x00b6, code lost:
        throw syntaxError("Unterminated comment");
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x00ba, code lost:
        if (r0 != 35) goto L21;
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x00bd, code lost:
        checkLenient();
        skipToEndOfLine();
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x00c9, code lost:
        return r0;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final int nextNonWhitespace(boolean z) throws IOException {
        while (true) {
            int i = 0;
            while (true) {
                int i2 = i;
                int i3 = i2 + 1;
                if (!this.source.request(i3)) {
                    if (z) {
                        throw new EOFException("End of input");
                    }
                    return -1;
                }
                byte b = this.buffer.getByte(i2);
                if (b != 10 && b != 32 && b != 13 && b != 9) {
                    break;
                }
                i = i3;
            }
        }
    }

    public final String nextQuotedValue(ByteString byteString) throws IOException {
        StringBuilder sb = null;
        while (true) {
            StringBuilder sb2 = sb;
            long indexOfElement = this.source.indexOfElement(byteString);
            if (indexOfElement == -1) {
                throw syntaxError("Unterminated string");
            }
            if (this.buffer.getByte(indexOfElement) != 92) {
                if (sb2 == null) {
                    String readUtf8 = this.buffer.readUtf8(indexOfElement);
                    this.buffer.readByte();
                    return readUtf8;
                }
                sb2.append(this.buffer.readUtf8(indexOfElement));
                this.buffer.readByte();
                return sb2.toString();
            }
            StringBuilder sb3 = sb2;
            if (sb2 == null) {
                sb3 = new StringBuilder();
            }
            sb3.append(this.buffer.readUtf8(indexOfElement));
            this.buffer.readByte();
            sb3.append(readEscapeCharacter());
            sb = sb3;
        }
    }

    @Override // com.airbnb.lottie.parser.moshi.JsonReader
    public String nextString() throws IOException {
        String readUtf8;
        int i = this.peeked;
        int i2 = i;
        if (i == 0) {
            i2 = doPeek();
        }
        if (i2 == 10) {
            readUtf8 = nextUnquotedValue();
        } else if (i2 == 9) {
            readUtf8 = nextQuotedValue(DOUBLE_QUOTE_OR_SLASH);
        } else if (i2 == 8) {
            readUtf8 = nextQuotedValue(SINGLE_QUOTE_OR_SLASH);
        } else if (i2 == 11) {
            readUtf8 = this.peekedString;
            this.peekedString = null;
        } else if (i2 == 16) {
            readUtf8 = Long.toString(this.peekedLong);
        } else if (i2 != 17) {
            throw new JsonDataException("Expected a string but was " + peek() + " at path " + getPath());
        } else {
            readUtf8 = this.buffer.readUtf8(this.peekedNumberLength);
        }
        this.peeked = 0;
        int[] iArr = this.pathIndices;
        int i3 = this.stackSize - 1;
        iArr[i3] = iArr[i3] + 1;
        return readUtf8;
    }

    public final String nextUnquotedValue() throws IOException {
        long indexOfElement = this.source.indexOfElement(UNQUOTED_STRING_TERMINALS);
        Buffer buffer = this.buffer;
        return indexOfElement != -1 ? buffer.readUtf8(indexOfElement) : buffer.readUtf8();
    }

    @Override // com.airbnb.lottie.parser.moshi.JsonReader
    public JsonReader.Token peek() throws IOException {
        int i = this.peeked;
        int i2 = i;
        if (i == 0) {
            i2 = doPeek();
        }
        switch (i2) {
            case 1:
                return JsonReader.Token.BEGIN_OBJECT;
            case 2:
                return JsonReader.Token.END_OBJECT;
            case 3:
                return JsonReader.Token.BEGIN_ARRAY;
            case 4:
                return JsonReader.Token.END_ARRAY;
            case 5:
            case 6:
                return JsonReader.Token.BOOLEAN;
            case 7:
                return JsonReader.Token.NULL;
            case 8:
            case 9:
            case 10:
            case 11:
                return JsonReader.Token.STRING;
            case 12:
            case 13:
            case 14:
            case 15:
                return JsonReader.Token.NAME;
            case 16:
            case 17:
                return JsonReader.Token.NUMBER;
            case 18:
                return JsonReader.Token.END_DOCUMENT;
            default:
                throw new AssertionError();
        }
    }

    public final int peekKeyword() throws IOException {
        int i;
        String str;
        String str2;
        byte b = this.buffer.getByte(0L);
        if (b == 116 || b == 84) {
            i = 5;
            str = "true";
            str2 = "TRUE";
        } else if (b == 102 || b == 70) {
            i = 6;
            str = "false";
            str2 = "FALSE";
        } else if (b != 110 && b != 78) {
            return 0;
        } else {
            i = 7;
            str = "null";
            str2 = "NULL";
        }
        int length = str.length();
        int i2 = 1;
        while (true) {
            int i3 = i2;
            if (i3 >= length) {
                if (this.source.request(length + 1) && isLiteral(this.buffer.getByte(length))) {
                    return 0;
                }
                this.buffer.skip(length);
                this.peeked = i;
                return i;
            }
            int i4 = i3 + 1;
            if (!this.source.request(i4)) {
                return 0;
            }
            byte b2 = this.buffer.getByte(i3);
            if (b2 != str.charAt(i3) && b2 != str2.charAt(i3)) {
                return 0;
            }
            i2 = i4;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:48:0x00e7, code lost:
        if (r11 == true) goto L50;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final int peekNumber() throws IOException {
        byte b;
        long j;
        boolean z;
        boolean z2;
        int i = 0;
        long j2 = 0;
        boolean z3 = true;
        int i2 = 0;
        boolean z4 = false;
        boolean z5 = false;
        while (true) {
            int i3 = i2 + 1;
            if (!this.source.request(i3)) {
                break;
            }
            b = this.buffer.getByte(i2);
            if (b != 43) {
                if (b == 69 || b == 101) {
                    if (!z4 && !z4) {
                        return i;
                    }
                    z4 = true;
                } else if (b != 45) {
                    if (b != 46) {
                        if (b < 48 || b > 57) {
                            break;
                        }
                        if (z4 || !z4) {
                            j = -(b - 48);
                            z = true;
                            z2 = z3;
                        } else if (z4) {
                            if (j2 == 0) {
                                return i;
                            }
                            j = (10 * j2) - (b - 48);
                            int i4 = (j2 > (-922337203685477580L) ? 1 : (j2 == (-922337203685477580L) ? 0 : -1));
                            z2 = z3 & (i4 > 0 || (i4 == 0 && j < j2));
                            z = z4;
                        } else if (z4) {
                            i = 0;
                            z4 = true;
                        } else {
                            if (!z4) {
                                z = z4;
                                z2 = z3;
                                j = j2;
                            }
                            i = 0;
                            z4 = true;
                        }
                        i = 0;
                        z4 = z;
                        z3 = z2;
                        j2 = j;
                    } else if (!z4) {
                        return i;
                    } else {
                        z4 = true;
                    }
                } else if (!z4) {
                    z4 = true;
                    z5 = true;
                } else if (!z4) {
                    return i;
                } else {
                    z4 = true;
                }
            } else if (!z4) {
                return i;
            } else {
                z4 = true;
            }
            i2 = i3;
        }
        if (isLiteral(b)) {
            return 0;
        }
        if (z4 && z3 && ((j2 != Long.MIN_VALUE || z5) && (j2 != 0 || !z5))) {
            if (!z5) {
                j2 = -j2;
            }
            this.peekedLong = j2;
            this.buffer.skip(i2);
            this.peeked = 16;
            return 16;
        } else if (z4 || z4 || z4) {
            this.peekedNumberLength = i2;
            this.peeked = 17;
            return 17;
        } else {
            return 0;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r8v7 */
    public final char readEscapeCharacter() throws IOException {
        int i;
        int i2;
        if (this.source.request(1L)) {
            byte readByte = this.buffer.readByte();
            if (readByte == 10 || readByte == 34 || readByte == 39 || readByte == 47 || readByte == 92) {
                return (char) readByte;
            }
            if (readByte != 98) {
                if (readByte != 102) {
                    if (readByte != 110) {
                        if (readByte != 114) {
                            if (readByte != 116) {
                                if (readByte != 117) {
                                    if (this.lenient) {
                                        return (char) readByte;
                                    }
                                    throw syntaxError("Invalid escape sequence: \\" + ((char) readByte));
                                } else if (!this.source.request(4L)) {
                                    throw new EOFException("Unterminated escape sequence at path " + getPath());
                                } else {
                                    int i3 = 0;
                                    char c = 0;
                                    while (true) {
                                        char c2 = c;
                                        if (i3 >= 4) {
                                            this.buffer.skip(4L);
                                            return c2;
                                        }
                                        byte b = this.buffer.getByte(i3);
                                        char c3 = (char) (c2 << 4);
                                        if (b < 48 || b > 57) {
                                            if (b >= 97 && b <= 102) {
                                                i = b - 97;
                                            } else if (b < 65 || b > 70) {
                                                break;
                                            } else {
                                                i = b - 65;
                                            }
                                            i2 = i + 10;
                                        } else {
                                            i2 = b - 48;
                                        }
                                        c = (char) (c3 + i2);
                                        i3++;
                                    }
                                    throw syntaxError("\\u" + this.buffer.readUtf8(4L));
                                }
                            }
                            return '\t';
                        }
                        return '\r';
                    }
                    return '\n';
                }
                return '\f';
            }
            return '\b';
        }
        throw syntaxError("Unterminated escape sequence");
    }

    @Override // com.airbnb.lottie.parser.moshi.JsonReader
    public int selectName(JsonReader.Options options) throws IOException {
        int i = this.peeked;
        int i2 = i;
        if (i == 0) {
            i2 = doPeek();
        }
        if (i2 < 12 || i2 > 15) {
            return -1;
        }
        if (i2 == 15) {
            return findName(this.peekedString, options);
        }
        int select = this.source.select(options.doubleQuoteSuffix);
        if (select != -1) {
            this.peeked = 0;
            this.pathNames[this.stackSize - 1] = options.strings[select];
            return select;
        }
        String str = this.pathNames[this.stackSize - 1];
        String nextName = nextName();
        int findName = findName(nextName, options);
        if (findName == -1) {
            this.peeked = 15;
            this.peekedString = nextName;
            this.pathNames[this.stackSize - 1] = str;
        }
        return findName;
    }

    @Override // com.airbnb.lottie.parser.moshi.JsonReader
    public void skipName() throws IOException {
        if (this.failOnUnknown) {
            throw new JsonDataException("Cannot skip unexpected " + peek() + " at " + getPath());
        }
        int i = this.peeked;
        int i2 = i;
        if (i == 0) {
            i2 = doPeek();
        }
        if (i2 == 14) {
            skipUnquotedValue();
        } else if (i2 == 13) {
            skipQuotedValue(DOUBLE_QUOTE_OR_SLASH);
        } else if (i2 == 12) {
            skipQuotedValue(SINGLE_QUOTE_OR_SLASH);
        } else if (i2 != 15) {
            throw new JsonDataException("Expected a name but was " + peek() + " at path " + getPath());
        }
        this.peeked = 0;
        this.pathNames[this.stackSize - 1] = "null";
    }

    public final void skipQuotedValue(ByteString byteString) throws IOException {
        while (true) {
            long indexOfElement = this.source.indexOfElement(byteString);
            if (indexOfElement == -1) {
                throw syntaxError("Unterminated string");
            }
            if (this.buffer.getByte(indexOfElement) != 92) {
                this.buffer.skip(indexOfElement + 1);
                return;
            } else {
                this.buffer.skip(indexOfElement + 1);
                readEscapeCharacter();
            }
        }
    }

    public final boolean skipToEndOfBlockComment() throws IOException {
        BufferedSource bufferedSource = this.source;
        ByteString byteString = CLOSING_BLOCK_COMMENT;
        long indexOf = bufferedSource.indexOf(byteString);
        boolean z = indexOf != -1;
        Buffer buffer = this.buffer;
        buffer.skip(z ? indexOf + byteString.size() : buffer.size());
        return z;
    }

    public final void skipToEndOfLine() throws IOException {
        long indexOfElement = this.source.indexOfElement(LINEFEED_OR_CARRIAGE_RETURN);
        Buffer buffer = this.buffer;
        buffer.skip(indexOfElement != -1 ? indexOfElement + 1 : buffer.size());
    }

    public final void skipUnquotedValue() throws IOException {
        long indexOfElement = this.source.indexOfElement(UNQUOTED_STRING_TERMINALS);
        Buffer buffer = this.buffer;
        if (indexOfElement == -1) {
            indexOfElement = buffer.size();
        }
        buffer.skip(indexOfElement);
    }

    @Override // com.airbnb.lottie.parser.moshi.JsonReader
    public void skipValue() throws IOException {
        int i;
        if (this.failOnUnknown) {
            throw new JsonDataException("Cannot skip unexpected " + peek() + " at " + getPath());
        }
        int i2 = 0;
        do {
            int i3 = this.peeked;
            int i4 = i3;
            if (i3 == 0) {
                i4 = doPeek();
            }
            if (i4 == 3) {
                pushScope(1);
            } else if (i4 == 1) {
                pushScope(3);
            } else {
                if (i4 == 4) {
                    i = i2 - 1;
                    if (i < 0) {
                        throw new JsonDataException("Expected a value but was " + peek() + " at path " + getPath());
                    }
                    this.stackSize--;
                } else if (i4 == 2) {
                    i = i2 - 1;
                    if (i < 0) {
                        throw new JsonDataException("Expected a value but was " + peek() + " at path " + getPath());
                    }
                    this.stackSize--;
                } else if (i4 == 14 || i4 == 10) {
                    skipUnquotedValue();
                    i = i2;
                } else if (i4 == 9 || i4 == 13) {
                    skipQuotedValue(DOUBLE_QUOTE_OR_SLASH);
                    i = i2;
                } else if (i4 == 8 || i4 == 12) {
                    skipQuotedValue(SINGLE_QUOTE_OR_SLASH);
                    i = i2;
                } else if (i4 == 17) {
                    this.buffer.skip(this.peekedNumberLength);
                    i = i2;
                } else if (i4 == 18) {
                    throw new JsonDataException("Expected a value but was " + peek() + " at path " + getPath());
                } else {
                    i = i2;
                }
                this.peeked = 0;
                i2 = i;
            }
            i = i2 + 1;
            this.peeked = 0;
            i2 = i;
        } while (i != 0);
        int[] iArr = this.pathIndices;
        int i5 = this.stackSize;
        int i6 = i5 - 1;
        iArr[i6] = iArr[i6] + 1;
        this.pathNames[i5 - 1] = "null";
    }

    public String toString() {
        return "JsonReader(" + this.source + ")";
    }
}