package com.android.systemui.dump.nano;

import com.android.systemui.qs.nano.QsTileState;
import com.google.protobuf.nano.CodedInputByteBufferNano;
import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import com.google.protobuf.nano.WireFormatNano;
import java.io.IOException;

/* loaded from: mainsysui33.jar:com/android/systemui/dump/nano/SystemUIProtoDump.class */
public final class SystemUIProtoDump extends MessageNano {
    public QsTileState[] tiles;

    public SystemUIProtoDump() {
        clear();
    }

    public SystemUIProtoDump clear() {
        this.tiles = QsTileState.emptyArray();
        ((MessageNano) this).cachedSize = -1;
        return this;
    }

    public int computeSerializedSize() {
        int computeSerializedSize = super.computeSerializedSize();
        QsTileState[] qsTileStateArr = this.tiles;
        int i = computeSerializedSize;
        if (qsTileStateArr != null) {
            i = computeSerializedSize;
            if (qsTileStateArr.length > 0) {
                int i2 = 0;
                while (true) {
                    QsTileState[] qsTileStateArr2 = this.tiles;
                    i = computeSerializedSize;
                    if (i2 >= qsTileStateArr2.length) {
                        break;
                    }
                    QsTileState qsTileState = qsTileStateArr2[i2];
                    int i3 = computeSerializedSize;
                    if (qsTileState != null) {
                        i3 = computeSerializedSize + CodedOutputByteBufferNano.computeMessageSize(1, qsTileState);
                    }
                    i2++;
                    computeSerializedSize = i3;
                }
            }
        }
        return i;
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: mergeFrom */
    public SystemUIProtoDump m2672mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
        while (true) {
            int readTag = codedInputByteBufferNano.readTag();
            if (readTag == 0) {
                return this;
            }
            if (readTag == 10) {
                int repeatedFieldArrayLength = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 10);
                QsTileState[] qsTileStateArr = this.tiles;
                int length = qsTileStateArr == null ? 0 : qsTileStateArr.length;
                int i = repeatedFieldArrayLength + length;
                QsTileState[] qsTileStateArr2 = new QsTileState[i];
                int i2 = length;
                if (length != 0) {
                    System.arraycopy(qsTileStateArr, 0, qsTileStateArr2, 0, length);
                    i2 = length;
                }
                while (i2 < i - 1) {
                    QsTileState qsTileState = new QsTileState();
                    qsTileStateArr2[i2] = qsTileState;
                    codedInputByteBufferNano.readMessage(qsTileState);
                    codedInputByteBufferNano.readTag();
                    i2++;
                }
                QsTileState qsTileState2 = new QsTileState();
                qsTileStateArr2[i2] = qsTileState2;
                codedInputByteBufferNano.readMessage(qsTileState2);
                this.tiles = qsTileStateArr2;
            } else if (!WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                return this;
            }
        }
    }

    public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
        QsTileState[] qsTileStateArr = this.tiles;
        if (qsTileStateArr != null && qsTileStateArr.length > 0) {
            int i = 0;
            while (true) {
                QsTileState[] qsTileStateArr2 = this.tiles;
                if (i >= qsTileStateArr2.length) {
                    break;
                }
                QsTileState qsTileState = qsTileStateArr2[i];
                if (qsTileState != null) {
                    codedOutputByteBufferNano.writeMessage(1, qsTileState);
                }
                i++;
            }
        }
        super.writeTo(codedOutputByteBufferNano);
    }
}