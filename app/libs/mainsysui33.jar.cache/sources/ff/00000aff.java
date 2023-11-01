package com.android.app.motiontool.nano;

import com.google.protobuf.nano.CodedInputByteBufferNano;
import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import com.google.protobuf.nano.WireFormatNano;
import java.io.IOException;

/* loaded from: mainsysui33.jar:com/android/app/motiontool/nano/WindowIdentifier.class */
public final class WindowIdentifier extends MessageNano {
    public String rootWindow;

    public WindowIdentifier() {
        clear();
    }

    public WindowIdentifier clear() {
        this.rootWindow = "";
        ((MessageNano) this).cachedSize = -1;
        return this;
    }

    public int computeSerializedSize() {
        int computeSerializedSize = super.computeSerializedSize();
        int i = computeSerializedSize;
        if (!this.rootWindow.equals("")) {
            i = computeSerializedSize + CodedOutputByteBufferNano.computeStringSize(1, this.rootWindow);
        }
        return i;
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: mergeFrom */
    public WindowIdentifier m495mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
        while (true) {
            int readTag = codedInputByteBufferNano.readTag();
            if (readTag == 0) {
                return this;
            }
            if (readTag == 10) {
                this.rootWindow = codedInputByteBufferNano.readString();
            } else if (!WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                return this;
            }
        }
    }

    public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
        if (!this.rootWindow.equals("")) {
            codedOutputByteBufferNano.writeString(1, this.rootWindow);
        }
        super.writeTo(codedOutputByteBufferNano);
    }
}