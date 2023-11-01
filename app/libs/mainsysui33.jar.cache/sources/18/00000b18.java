package com.android.app.viewcapture.data.nano;

import com.google.protobuf.nano.CodedInputByteBufferNano;
import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.InternalNano;
import com.google.protobuf.nano.MessageNano;
import com.google.protobuf.nano.WireFormatNano;
import java.io.IOException;

/* loaded from: mainsysui33.jar:com/android/app/viewcapture/data/nano/FrameData.class */
public final class FrameData extends MessageNano {
    private static volatile FrameData[] _emptyArray;
    public ViewNode node;
    public long timestamp;

    public FrameData() {
        clear();
    }

    public static FrameData[] emptyArray() {
        if (_emptyArray == null) {
            synchronized (InternalNano.LAZY_INIT_LOCK) {
                if (_emptyArray == null) {
                    _emptyArray = new FrameData[0];
                }
            }
        }
        return _emptyArray;
    }

    public FrameData clear() {
        this.timestamp = 0L;
        this.node = null;
        ((MessageNano) this).cachedSize = -1;
        return this;
    }

    public int computeSerializedSize() {
        int computeSerializedSize = super.computeSerializedSize();
        long j = this.timestamp;
        int i = computeSerializedSize;
        if (j != 0) {
            i = computeSerializedSize + CodedOutputByteBufferNano.computeInt64Size(1, j);
        }
        ViewNode viewNode = this.node;
        int i2 = i;
        if (viewNode != null) {
            i2 = i + CodedOutputByteBufferNano.computeMessageSize(2, viewNode);
        }
        return i2;
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: mergeFrom */
    public FrameData m511mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
        while (true) {
            int readTag = codedInputByteBufferNano.readTag();
            if (readTag == 0) {
                return this;
            }
            if (readTag == 8) {
                this.timestamp = codedInputByteBufferNano.readInt64();
            } else if (readTag == 18) {
                if (this.node == null) {
                    this.node = new ViewNode();
                }
                codedInputByteBufferNano.readMessage(this.node);
            } else if (!WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                return this;
            }
        }
    }

    public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
        long j = this.timestamp;
        if (j != 0) {
            codedOutputByteBufferNano.writeInt64(1, j);
        }
        ViewNode viewNode = this.node;
        if (viewNode != null) {
            codedOutputByteBufferNano.writeMessage(2, viewNode);
        }
        super.writeTo(codedOutputByteBufferNano);
    }
}