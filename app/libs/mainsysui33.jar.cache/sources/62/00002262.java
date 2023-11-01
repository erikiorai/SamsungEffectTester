package com.android.systemui.qs.nano;

import com.android.systemui.util.nano.ComponentNameProto;
import com.google.protobuf.nano.CodedInputByteBufferNano;
import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.InternalNano;
import com.google.protobuf.nano.MessageNano;
import com.google.protobuf.nano.WireFormatNano;
import java.io.IOException;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/nano/QsTileState.class */
public final class QsTileState extends MessageNano {
    private static volatile QsTileState[] _emptyArray;
    private Object identifier_;
    private Object optionalBooleanState_;
    private Object optionalLabel_;
    private Object optionalSecondaryLabel_;
    public int state;
    private int identifierCase_ = 0;
    private int optionalBooleanStateCase_ = 0;
    private int optionalLabelCase_ = 0;
    private int optionalSecondaryLabelCase_ = 0;

    public QsTileState() {
        clear();
    }

    public static QsTileState[] emptyArray() {
        if (_emptyArray == null) {
            synchronized (InternalNano.LAZY_INIT_LOCK) {
                if (_emptyArray == null) {
                    _emptyArray = new QsTileState[0];
                }
            }
        }
        return _emptyArray;
    }

    public QsTileState clear() {
        this.state = 0;
        clearIdentifier();
        clearOptionalBooleanState();
        clearOptionalLabel();
        clearOptionalSecondaryLabel();
        ((MessageNano) this).cachedSize = -1;
        return this;
    }

    public QsTileState clearIdentifier() {
        this.identifierCase_ = 0;
        this.identifier_ = null;
        return this;
    }

    public QsTileState clearOptionalBooleanState() {
        this.optionalBooleanStateCase_ = 0;
        this.optionalBooleanState_ = null;
        return this;
    }

    public QsTileState clearOptionalLabel() {
        this.optionalLabelCase_ = 0;
        this.optionalLabel_ = null;
        return this;
    }

    public QsTileState clearOptionalSecondaryLabel() {
        this.optionalSecondaryLabelCase_ = 0;
        this.optionalSecondaryLabel_ = null;
        return this;
    }

    public int computeSerializedSize() {
        int computeSerializedSize = super.computeSerializedSize();
        int i = computeSerializedSize;
        if (this.identifierCase_ == 1) {
            i = computeSerializedSize + CodedOutputByteBufferNano.computeStringSize(1, (String) this.identifier_);
        }
        int i2 = i;
        if (this.identifierCase_ == 2) {
            i2 = i + CodedOutputByteBufferNano.computeMessageSize(2, (MessageNano) this.identifier_);
        }
        int i3 = this.state;
        int i4 = i2;
        if (i3 != 0) {
            i4 = i2 + CodedOutputByteBufferNano.computeInt32Size(3, i3);
        }
        int i5 = i4;
        if (this.optionalBooleanStateCase_ == 4) {
            i5 = i4 + CodedOutputByteBufferNano.computeBoolSize(4, ((Boolean) this.optionalBooleanState_).booleanValue());
        }
        int i6 = i5;
        if (this.optionalLabelCase_ == 5) {
            i6 = i5 + CodedOutputByteBufferNano.computeStringSize(5, (String) this.optionalLabel_);
        }
        int i7 = i6;
        if (this.optionalSecondaryLabelCase_ == 6) {
            i7 = i6 + CodedOutputByteBufferNano.computeStringSize(6, (String) this.optionalSecondaryLabel_);
        }
        return i7;
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: mergeFrom */
    public QsTileState m3958mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
        while (true) {
            int readTag = codedInputByteBufferNano.readTag();
            if (readTag == 0) {
                return this;
            }
            if (readTag == 10) {
                this.identifier_ = codedInputByteBufferNano.readString();
                this.identifierCase_ = 1;
            } else if (readTag == 18) {
                if (this.identifierCase_ != 2) {
                    this.identifier_ = new ComponentNameProto();
                }
                codedInputByteBufferNano.readMessage((MessageNano) this.identifier_);
                this.identifierCase_ = 2;
            } else if (readTag == 24) {
                int readInt32 = codedInputByteBufferNano.readInt32();
                if (readInt32 == 0 || readInt32 == 1 || readInt32 == 2) {
                    this.state = readInt32;
                }
            } else if (readTag == 32) {
                this.optionalBooleanState_ = Boolean.valueOf(codedInputByteBufferNano.readBool());
                this.optionalBooleanStateCase_ = 4;
            } else if (readTag == 42) {
                this.optionalLabel_ = codedInputByteBufferNano.readString();
                this.optionalLabelCase_ = 5;
            } else if (readTag == 50) {
                this.optionalSecondaryLabel_ = codedInputByteBufferNano.readString();
                this.optionalSecondaryLabelCase_ = 6;
            } else if (!WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                return this;
            }
        }
    }

    public QsTileState setBooleanState(boolean z) {
        this.optionalBooleanStateCase_ = 4;
        this.optionalBooleanState_ = Boolean.valueOf(z);
        return this;
    }

    public QsTileState setComponentName(ComponentNameProto componentNameProto) {
        componentNameProto.getClass();
        this.identifierCase_ = 2;
        this.identifier_ = componentNameProto;
        return this;
    }

    public QsTileState setLabel(String str) {
        this.optionalLabelCase_ = 5;
        this.optionalLabel_ = str;
        return this;
    }

    public QsTileState setSecondaryLabel(String str) {
        this.optionalSecondaryLabelCase_ = 6;
        this.optionalSecondaryLabel_ = str;
        return this;
    }

    public QsTileState setSpec(String str) {
        this.identifierCase_ = 1;
        this.identifier_ = str;
        return this;
    }

    public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
        if (this.identifierCase_ == 1) {
            codedOutputByteBufferNano.writeString(1, (String) this.identifier_);
        }
        if (this.identifierCase_ == 2) {
            codedOutputByteBufferNano.writeMessage(2, (MessageNano) this.identifier_);
        }
        int i = this.state;
        if (i != 0) {
            codedOutputByteBufferNano.writeInt32(3, i);
        }
        if (this.optionalBooleanStateCase_ == 4) {
            codedOutputByteBufferNano.writeBool(4, ((Boolean) this.optionalBooleanState_).booleanValue());
        }
        if (this.optionalLabelCase_ == 5) {
            codedOutputByteBufferNano.writeString(5, (String) this.optionalLabel_);
        }
        if (this.optionalSecondaryLabelCase_ == 6) {
            codedOutputByteBufferNano.writeString(6, (String) this.optionalSecondaryLabel_);
        }
        super.writeTo(codedOutputByteBufferNano);
    }
}