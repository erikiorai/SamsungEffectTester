package com.android.app.motiontool.nano;

import com.android.app.viewcapture.data.nano.ExportedData;
import com.google.protobuf.nano.CodedInputByteBufferNano;
import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import com.google.protobuf.nano.WireFormatNano;
import java.io.IOException;

/* loaded from: mainsysui33.jar:com/android/app/motiontool/nano/PollTraceResponse.class */
public final class PollTraceResponse extends MessageNano {
    public ExportedData exportedData;

    public PollTraceResponse() {
        clear();
    }

    public PollTraceResponse clear() {
        this.exportedData = null;
        ((MessageNano) this).cachedSize = -1;
        return this;
    }

    public int computeSerializedSize() {
        int computeSerializedSize = super.computeSerializedSize();
        ExportedData exportedData = this.exportedData;
        int i = computeSerializedSize;
        if (exportedData != null) {
            i = computeSerializedSize + CodedOutputByteBufferNano.computeMessageSize(1, exportedData);
        }
        return i;
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: mergeFrom */
    public PollTraceResponse m494mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
        while (true) {
            int readTag = codedInputByteBufferNano.readTag();
            if (readTag == 0) {
                return this;
            }
            if (readTag == 10) {
                if (this.exportedData == null) {
                    this.exportedData = new ExportedData();
                }
                codedInputByteBufferNano.readMessage(this.exportedData);
            } else if (!WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                return this;
            }
        }
    }

    public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
        ExportedData exportedData = this.exportedData;
        if (exportedData != null) {
            codedOutputByteBufferNano.writeMessage(1, exportedData);
        }
        super.writeTo(codedOutputByteBufferNano);
    }
}