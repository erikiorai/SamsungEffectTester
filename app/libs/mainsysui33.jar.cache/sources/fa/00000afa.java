package com.android.app.motiontool.nano;

import com.google.protobuf.nano.CodedInputByteBufferNano;
import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import com.google.protobuf.nano.WireFormatNano;
import java.io.IOException;

/* loaded from: mainsysui33.jar:com/android/app/motiontool/nano/HandshakeResponse.class */
public final class HandshakeResponse extends MessageNano {
    public int serverVersion;
    public int status;

    public HandshakeResponse() {
        clear();
    }

    public HandshakeResponse clear() {
        this.status = 1;
        this.serverVersion = 0;
        ((MessageNano) this).cachedSize = -1;
        return this;
    }

    public int computeSerializedSize() {
        int computeSerializedSize = super.computeSerializedSize();
        int i = this.status;
        int i2 = computeSerializedSize;
        if (i != 1) {
            i2 = computeSerializedSize + CodedOutputByteBufferNano.computeInt32Size(1, i);
        }
        int i3 = this.serverVersion;
        int i4 = i2;
        if (i3 != 0) {
            i4 = i2 + CodedOutputByteBufferNano.computeInt32Size(2, i3);
        }
        return i4;
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: mergeFrom */
    public HandshakeResponse m490mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
        while (true) {
            int readTag = codedInputByteBufferNano.readTag();
            if (readTag == 0) {
                return this;
            }
            if (readTag == 8) {
                int readInt32 = codedInputByteBufferNano.readInt32();
                if (readInt32 == 1 || readInt32 == 2) {
                    this.status = readInt32;
                }
            } else if (readTag == 16) {
                this.serverVersion = codedInputByteBufferNano.readInt32();
            } else if (!WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                return this;
            }
        }
    }

    public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
        int i = this.status;
        if (i != 1) {
            codedOutputByteBufferNano.writeInt32(1, i);
        }
        int i2 = this.serverVersion;
        if (i2 != 0) {
            codedOutputByteBufferNano.writeInt32(2, i2);
        }
        super.writeTo(codedOutputByteBufferNano);
    }
}