package com.android.app.motiontool.nano;

import com.google.protobuf.nano.CodedInputByteBufferNano;
import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import com.google.protobuf.nano.WireFormatNano;
import java.io.IOException;

/* loaded from: mainsysui33.jar:com/android/app/motiontool/nano/MotionToolsResponse.class */
public final class MotionToolsResponse extends MessageNano {
    private int typeCase_ = 0;
    private Object type_;

    public MotionToolsResponse() {
        clear();
    }

    public MotionToolsResponse clear() {
        clearType();
        ((MessageNano) this).cachedSize = -1;
        return this;
    }

    public MotionToolsResponse clearType() {
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
        int i5 = i4;
        if (this.typeCase_ == 5) {
            i5 = i4 + CodedOutputByteBufferNano.computeMessageSize(5, (MessageNano) this.type_);
        }
        return i5;
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: mergeFrom */
    public MotionToolsResponse m492mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
        while (true) {
            int readTag = codedInputByteBufferNano.readTag();
            if (readTag == 0) {
                return this;
            }
            if (readTag == 10) {
                if (this.typeCase_ != 1) {
                    this.type_ = new ErrorResponse();
                }
                codedInputByteBufferNano.readMessage((MessageNano) this.type_);
                this.typeCase_ = 1;
            } else if (readTag == 18) {
                if (this.typeCase_ != 2) {
                    this.type_ = new HandshakeResponse();
                }
                codedInputByteBufferNano.readMessage((MessageNano) this.type_);
                this.typeCase_ = 2;
            } else if (readTag == 26) {
                if (this.typeCase_ != 3) {
                    this.type_ = new BeginTraceResponse();
                }
                codedInputByteBufferNano.readMessage((MessageNano) this.type_);
                this.typeCase_ = 3;
            } else if (readTag == 34) {
                if (this.typeCase_ != 4) {
                    this.type_ = new EndTraceResponse();
                }
                codedInputByteBufferNano.readMessage((MessageNano) this.type_);
                this.typeCase_ = 4;
            } else if (readTag == 42) {
                if (this.typeCase_ != 5) {
                    this.type_ = new PollTraceResponse();
                }
                codedInputByteBufferNano.readMessage((MessageNano) this.type_);
                this.typeCase_ = 5;
            } else if (!WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                return this;
            }
        }
    }

    public MotionToolsResponse setBeginTrace(BeginTraceResponse beginTraceResponse) {
        beginTraceResponse.getClass();
        this.typeCase_ = 3;
        this.type_ = beginTraceResponse;
        return this;
    }

    public MotionToolsResponse setEndTrace(EndTraceResponse endTraceResponse) {
        endTraceResponse.getClass();
        this.typeCase_ = 4;
        this.type_ = endTraceResponse;
        return this;
    }

    public MotionToolsResponse setError(ErrorResponse errorResponse) {
        errorResponse.getClass();
        this.typeCase_ = 1;
        this.type_ = errorResponse;
        return this;
    }

    public MotionToolsResponse setHandshake(HandshakeResponse handshakeResponse) {
        handshakeResponse.getClass();
        this.typeCase_ = 2;
        this.type_ = handshakeResponse;
        return this;
    }

    public MotionToolsResponse setPollTrace(PollTraceResponse pollTraceResponse) {
        pollTraceResponse.getClass();
        this.typeCase_ = 5;
        this.type_ = pollTraceResponse;
        return this;
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
        if (this.typeCase_ == 5) {
            codedOutputByteBufferNano.writeMessage(5, (MessageNano) this.type_);
        }
        super.writeTo(codedOutputByteBufferNano);
    }
}