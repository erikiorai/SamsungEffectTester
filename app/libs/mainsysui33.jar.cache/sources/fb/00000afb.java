package com.android.app.motiontool.nano;

import com.google.protobuf.nano.CodedInputByteBufferNano;
import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.InvalidProtocolBufferNanoException;
import com.google.protobuf.nano.MessageNano;
import com.google.protobuf.nano.WireFormatNano;
import java.io.IOException;

/* loaded from: mainsysui33.jar:com/android/app/motiontool/nano/MotionToolsRequest.class */
public final class MotionToolsRequest extends MessageNano {
    private int typeCase_ = 0;
    private Object type_;

    public MotionToolsRequest() {
        clear();
    }

    public static MotionToolsRequest parseFrom(byte[] bArr) throws InvalidProtocolBufferNanoException {
        return (MotionToolsRequest) MessageNano.mergeFrom(new MotionToolsRequest(), bArr);
    }

    public MotionToolsRequest clear() {
        clearType();
        ((MessageNano) this).cachedSize = -1;
        return this;
    }

    public MotionToolsRequest clearType() {
        this.typeCase_ = 0;
        this.type_ = null;
        return this;
    }

    public int computeSerializedSize() {
        int computeSerializedSize = super.computeSerializedSize();
        int i = computeSerializedSize;
        if (this.typeCase_ == 1) {
            i = computeSerializedSize + CodedOutputByteBufferNano.computeMessageSize(1, (MessageNano) this.type_);
        }
        int i2 = i;
        if (this.typeCase_ == 2) {
            i2 = i + CodedOutputByteBufferNano.computeMessageSize(2, (MessageNano) this.type_);
        }
        int i3 = i2;
        if (this.typeCase_ == 3) {
            i3 = i2 + CodedOutputByteBufferNano.computeMessageSize(3, (MessageNano) this.type_);
        }
        int i4 = i3;
        if (this.typeCase_ == 4) {
            i4 = i3 + CodedOutputByteBufferNano.computeMessageSize(4, (MessageNano) this.type_);
        }
        return i4;
    }

    public BeginTraceRequest getBeginTrace() {
        if (this.typeCase_ == 2) {
            return (BeginTraceRequest) this.type_;
        }
        return null;
    }

    public EndTraceRequest getEndTrace() {
        if (this.typeCase_ == 3) {
            return (EndTraceRequest) this.type_;
        }
        return null;
    }

    public HandshakeRequest getHandshake() {
        if (this.typeCase_ == 1) {
            return (HandshakeRequest) this.type_;
        }
        return null;
    }

    public PollTraceRequest getPollTrace() {
        if (this.typeCase_ == 4) {
            return (PollTraceRequest) this.type_;
        }
        return null;
    }

    public int getTypeCase() {
        return this.typeCase_;
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: mergeFrom */
    public MotionToolsRequest m491mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
        while (true) {
            int readTag = codedInputByteBufferNano.readTag();
            if (readTag == 0) {
                return this;
            }
            if (readTag == 10) {
                if (this.typeCase_ != 1) {
                    this.type_ = new HandshakeRequest();
                }
                codedInputByteBufferNano.readMessage((MessageNano) this.type_);
                this.typeCase_ = 1;
            } else if (readTag == 18) {
                if (this.typeCase_ != 2) {
                    this.type_ = new BeginTraceRequest();
                }
                codedInputByteBufferNano.readMessage((MessageNano) this.type_);
                this.typeCase_ = 2;
            } else if (readTag == 26) {
                if (this.typeCase_ != 3) {
                    this.type_ = new EndTraceRequest();
                }
                codedInputByteBufferNano.readMessage((MessageNano) this.type_);
                this.typeCase_ = 3;
            } else if (readTag == 34) {
                if (this.typeCase_ != 4) {
                    this.type_ = new PollTraceRequest();
                }
                codedInputByteBufferNano.readMessage((MessageNano) this.type_);
                this.typeCase_ = 4;
            } else if (!WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                return this;
            }
        }
    }

    public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
        if (this.typeCase_ == 1) {
            codedOutputByteBufferNano.writeMessage(1, (MessageNano) this.type_);
        }
        if (this.typeCase_ == 2) {
            codedOutputByteBufferNano.writeMessage(2, (MessageNano) this.type_);
        }
        if (this.typeCase_ == 3) {
            codedOutputByteBufferNano.writeMessage(3, (MessageNano) this.type_);
        }
        if (this.typeCase_ == 4) {
            codedOutputByteBufferNano.writeMessage(4, (MessageNano) this.type_);
        }
        super.writeTo(codedOutputByteBufferNano);
    }
}