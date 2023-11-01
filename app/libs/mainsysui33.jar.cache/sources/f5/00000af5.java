package com.android.app.motiontool.nano;

import com.google.protobuf.nano.CodedInputByteBufferNano;
import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import com.google.protobuf.nano.WireFormatNano;
import java.io.IOException;

/* loaded from: mainsysui33.jar:com/android/app/motiontool/nano/BeginTraceResponse.class */
public final class BeginTraceResponse extends MessageNano {
    public int traceId;

    public BeginTraceResponse() {
        clear();
    }

    public BeginTraceResponse clear() {
        this.traceId = 0;
        ((MessageNano) this).cachedSize = -1;
        return this;
    }

    public int computeSerializedSize() {
        int computeSerializedSize = super.computeSerializedSize();
        int i = this.traceId;
        int i2 = computeSerializedSize;
        if (i != 0) {
            i2 = computeSerializedSize + CodedOutputByteBufferNano.computeInt32Size(1, i);
        }
        return i2;
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: mergeFrom */
    public BeginTraceResponse m485mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
        while (true) {
            int readTag = codedInputByteBufferNano.readTag();
            if (readTag == 0) {
                return this;
            }
            if (readTag == 8) {
                this.traceId = codedInputByteBufferNano.readInt32();
            } else if (!WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                return this;
            }
        }
    }

    public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
        int i = this.traceId;
        if (i != 0) {
            codedOutputByteBufferNano.writeInt32(1, i);
        }
        super.writeTo(codedOutputByteBufferNano);
    }
}