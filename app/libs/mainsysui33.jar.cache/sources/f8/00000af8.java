package com.android.app.motiontool.nano;

import com.google.protobuf.nano.CodedInputByteBufferNano;
import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import com.google.protobuf.nano.WireFormatNano;
import java.io.IOException;

/* loaded from: mainsysui33.jar:com/android/app/motiontool/nano/ErrorResponse.class */
public final class ErrorResponse extends MessageNano {
    public int code;
    public String message;

    public ErrorResponse() {
        clear();
    }

    public ErrorResponse clear() {
        this.code = 0;
        this.message = "";
        ((MessageNano) this).cachedSize = -1;
        return this;
    }

    public int computeSerializedSize() {
        int computeSerializedSize = super.computeSerializedSize();
        int i = this.code;
        int i2 = computeSerializedSize;
        if (i != 0) {
            i2 = computeSerializedSize + CodedOutputByteBufferNano.computeInt32Size(1, i);
        }
        int i3 = i2;
        if (!this.message.equals("")) {
            i3 = i2 + CodedOutputByteBufferNano.computeStringSize(2, this.message);
        }
        return i3;
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: mergeFrom */
    public ErrorResponse m488mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
        while (true) {
            int readTag = codedInputByteBufferNano.readTag();
            if (readTag == 0) {
                return this;
            }
            if (readTag == 8) {
                int readInt32 = codedInputByteBufferNano.readInt32();
                if (readInt32 == 0 || readInt32 == 1 || readInt32 == 2 || readInt32 == 3) {
                    this.code = readInt32;
                }
            } else if (readTag == 18) {
                this.message = codedInputByteBufferNano.readString();
            } else if (!WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                return this;
            }
        }
    }

    public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
        int i = this.code;
        if (i != 0) {
            codedOutputByteBufferNano.writeInt32(1, i);
        }
        if (!this.message.equals("")) {
            codedOutputByteBufferNano.writeString(2, this.message);
        }
        super.writeTo(codedOutputByteBufferNano);
    }
}