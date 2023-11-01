package com.android.app.motiontool.nano;

import com.google.protobuf.nano.CodedInputByteBufferNano;
import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import com.google.protobuf.nano.WireFormatNano;
import java.io.IOException;

/* loaded from: mainsysui33.jar:com/android/app/motiontool/nano/HandshakeRequest.class */
public final class HandshakeRequest extends MessageNano {
    public int clientVersion;
    public WindowIdentifier window;

    public HandshakeRequest() {
        clear();
    }

    public HandshakeRequest clear() {
        this.window = null;
        this.clientVersion = 0;
        ((MessageNano) this).cachedSize = -1;
        return this;
    }

    public int computeSerializedSize() {
        int computeSerializedSize = super.computeSerializedSize();
        WindowIdentifier windowIdentifier = this.window;
        int i = computeSerializedSize;
        if (windowIdentifier != null) {
            i = computeSerializedSize + CodedOutputByteBufferNano.computeMessageSize(1, windowIdentifier);
        }
        int i2 = this.clientVersion;
        int i3 = i;
        if (i2 != 0) {
            i3 = i + CodedOutputByteBufferNano.computeInt32Size(2, i2);
        }
        return i3;
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: mergeFrom */
    public HandshakeRequest m489mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
        while (true) {
            int readTag = codedInputByteBufferNano.readTag();
            if (readTag == 0) {
                return this;
            }
            if (readTag == 10) {
                if (this.window == null) {
                    this.window = new WindowIdentifier();
                }
                codedInputByteBufferNano.readMessage(this.window);
            } else if (readTag == 16) {
                this.clientVersion = codedInputByteBufferNano.readInt32();
            } else if (!WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                return this;
            }
        }
    }

    public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
        WindowIdentifier windowIdentifier = this.window;
        if (windowIdentifier != null) {
            codedOutputByteBufferNano.writeMessage(1, windowIdentifier);
        }
        int i = this.clientVersion;
        if (i != 0) {
            codedOutputByteBufferNano.writeInt32(2, i);
        }
        super.writeTo(codedOutputByteBufferNano);
    }
}