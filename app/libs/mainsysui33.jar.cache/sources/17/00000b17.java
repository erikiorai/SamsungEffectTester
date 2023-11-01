package com.android.app.viewcapture.data.nano;

import com.google.protobuf.nano.CodedInputByteBufferNano;
import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import com.google.protobuf.nano.WireFormatNano;
import java.io.IOException;

/* loaded from: mainsysui33.jar:com/android/app/viewcapture/data/nano/ExportedData.class */
public final class ExportedData extends MessageNano {
    public String[] classname;
    public FrameData[] frameData;

    public ExportedData() {
        clear();
    }

    public ExportedData clear() {
        this.frameData = FrameData.emptyArray();
        this.classname = WireFormatNano.EMPTY_STRING_ARRAY;
        ((MessageNano) this).cachedSize = -1;
        return this;
    }

    public int computeSerializedSize() {
        int computeSerializedSize = super.computeSerializedSize();
        FrameData[] frameDataArr = this.frameData;
        int i = computeSerializedSize;
        if (frameDataArr != null) {
            i = computeSerializedSize;
            if (frameDataArr.length > 0) {
                int i2 = 0;
                while (true) {
                    FrameData[] frameDataArr2 = this.frameData;
                    i = computeSerializedSize;
                    if (i2 >= frameDataArr2.length) {
                        break;
                    }
                    FrameData frameData = frameDataArr2[i2];
                    int i3 = computeSerializedSize;
                    if (frameData != null) {
                        i3 = computeSerializedSize + CodedOutputByteBufferNano.computeMessageSize(1, frameData);
                    }
                    i2++;
                    computeSerializedSize = i3;
                }
            }
        }
        String[] strArr = this.classname;
        int i4 = i;
        if (strArr != null) {
            i4 = i;
            if (strArr.length > 0) {
                int i5 = 0;
                int i6 = 0;
                int i7 = 0;
                while (true) {
                    String[] strArr2 = this.classname;
                    if (i7 >= strArr2.length) {
                        break;
                    }
                    String str = strArr2[i7];
                    int i8 = i5;
                    int i9 = i6;
                    if (str != null) {
                        i9 = i6 + 1;
                        i8 = i5 + CodedOutputByteBufferNano.computeStringSizeNoTag(str);
                    }
                    i7++;
                    i5 = i8;
                    i6 = i9;
                }
                i4 = i + i5 + (i6 * 1);
            }
        }
        return i4;
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: mergeFrom */
    public ExportedData m510mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
        while (true) {
            int readTag = codedInputByteBufferNano.readTag();
            if (readTag == 0) {
                return this;
            }
            if (readTag == 10) {
                int repeatedFieldArrayLength = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 10);
                FrameData[] frameDataArr = this.frameData;
                int length = frameDataArr == null ? 0 : frameDataArr.length;
                int i = repeatedFieldArrayLength + length;
                FrameData[] frameDataArr2 = new FrameData[i];
                int i2 = length;
                if (length != 0) {
                    System.arraycopy(frameDataArr, 0, frameDataArr2, 0, length);
                    i2 = length;
                }
                while (i2 < i - 1) {
                    FrameData frameData = new FrameData();
                    frameDataArr2[i2] = frameData;
                    codedInputByteBufferNano.readMessage(frameData);
                    codedInputByteBufferNano.readTag();
                    i2++;
                }
                FrameData frameData2 = new FrameData();
                frameDataArr2[i2] = frameData2;
                codedInputByteBufferNano.readMessage(frameData2);
                this.frameData = frameDataArr2;
            } else if (readTag == 18) {
                int repeatedFieldArrayLength2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 18);
                String[] strArr = this.classname;
                int length2 = strArr == null ? 0 : strArr.length;
                int i3 = repeatedFieldArrayLength2 + length2;
                String[] strArr2 = new String[i3];
                int i4 = length2;
                if (length2 != 0) {
                    System.arraycopy(strArr, 0, strArr2, 0, length2);
                    i4 = length2;
                }
                while (i4 < i3 - 1) {
                    strArr2[i4] = codedInputByteBufferNano.readString();
                    codedInputByteBufferNano.readTag();
                    i4++;
                }
                strArr2[i4] = codedInputByteBufferNano.readString();
                this.classname = strArr2;
            } else if (!WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                return this;
            }
        }
    }

    public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
        FrameData[] frameDataArr = this.frameData;
        if (frameDataArr != null && frameDataArr.length > 0) {
            int i = 0;
            while (true) {
                FrameData[] frameDataArr2 = this.frameData;
                if (i >= frameDataArr2.length) {
                    break;
                }
                FrameData frameData = frameDataArr2[i];
                if (frameData != null) {
                    codedOutputByteBufferNano.writeMessage(1, frameData);
                }
                i++;
            }
        }
        String[] strArr = this.classname;
        if (strArr != null && strArr.length > 0) {
            int i2 = 0;
            while (true) {
                String[] strArr2 = this.classname;
                if (i2 >= strArr2.length) {
                    break;
                }
                String str = strArr2[i2];
                if (str != null) {
                    codedOutputByteBufferNano.writeString(2, str);
                }
                i2++;
            }
        }
        super.writeTo(codedOutputByteBufferNano);
    }
}