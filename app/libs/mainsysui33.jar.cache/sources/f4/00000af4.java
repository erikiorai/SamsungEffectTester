package com.android.app.motiontool.nano;

import com.google.protobuf.nano.CodedInputByteBufferNano;
import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import com.google.protobuf.nano.WireFormatNano;
import java.io.IOException;

/* loaded from: mainsysui33.jar:com/android/app/motiontool/nano/BeginTraceRequest.class */
public final class BeginTraceRequest extends MessageNano {
    public WindowIdentifier window;

    public BeginTraceRequest() {
        clear();
    }

    public BeginTraceRequest clear() {
        this.window = null;
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
        return i;
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: mergeFrom */
    public BeginTraceRequest m484mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
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
        super.writeTo(codedOutputByteBufferNano);
    }
}