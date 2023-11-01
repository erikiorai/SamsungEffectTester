package com.android.app.viewcapture.data.nano;

import androidx.appcompat.R$styleable;
import androidx.recyclerview.widget.RecyclerView;
import com.android.settingslib.widget.ActionBarShadowController;
import com.google.protobuf.nano.CodedInputByteBufferNano;
import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.InternalNano;
import com.google.protobuf.nano.MessageNano;
import com.google.protobuf.nano.WireFormatNano;
import java.io.IOException;

/* loaded from: mainsysui33.jar:com/android/app/viewcapture/data/nano/ViewNode.class */
public final class ViewNode extends MessageNano {
    private static volatile ViewNode[] _emptyArray;
    public float alpha;
    public ViewNode[] children;
    public int classnameIndex;
    public boolean clipChildren;
    public float elevation;
    public int hashcode;
    public int height;
    public String id;
    public int left;
    public float scaleX;
    public float scaleY;
    public int scrollX;
    public int scrollY;
    public int top;
    public float translationX;
    public float translationY;
    public int visibility;
    public int width;
    public boolean willNotDraw;

    public ViewNode() {
        clear();
    }

    public static ViewNode[] emptyArray() {
        if (_emptyArray == null) {
            synchronized (InternalNano.LAZY_INIT_LOCK) {
                if (_emptyArray == null) {
                    _emptyArray = new ViewNode[0];
                }
            }
        }
        return _emptyArray;
    }

    public ViewNode clear() {
        this.classnameIndex = 0;
        this.hashcode = 0;
        this.children = emptyArray();
        this.id = "";
        this.left = 0;
        this.top = 0;
        this.width = 0;
        this.height = 0;
        this.scrollX = 0;
        this.scrollY = 0;
        this.translationX = ActionBarShadowController.ELEVATION_LOW;
        this.translationY = ActionBarShadowController.ELEVATION_LOW;
        this.scaleX = 1.0f;
        this.scaleY = 1.0f;
        this.alpha = 1.0f;
        this.willNotDraw = false;
        this.clipChildren = false;
        this.visibility = 0;
        this.elevation = ActionBarShadowController.ELEVATION_LOW;
        ((MessageNano) this).cachedSize = -1;
        return this;
    }

    public int computeSerializedSize() {
        int computeSerializedSize = super.computeSerializedSize();
        int i = this.classnameIndex;
        int i2 = computeSerializedSize;
        if (i != 0) {
            i2 = computeSerializedSize + CodedOutputByteBufferNano.computeInt32Size(1, i);
        }
        int i3 = this.hashcode;
        int i4 = i2;
        if (i3 != 0) {
            i4 = i2 + CodedOutputByteBufferNano.computeInt32Size(2, i3);
        }
        ViewNode[] viewNodeArr = this.children;
        int i5 = i4;
        if (viewNodeArr != null) {
            i5 = i4;
            if (viewNodeArr.length > 0) {
                int i6 = 0;
                while (true) {
                    ViewNode[] viewNodeArr2 = this.children;
                    i5 = i4;
                    if (i6 >= viewNodeArr2.length) {
                        break;
                    }
                    ViewNode viewNode = viewNodeArr2[i6];
                    int i7 = i4;
                    if (viewNode != null) {
                        i7 = i4 + CodedOutputByteBufferNano.computeMessageSize(3, viewNode);
                    }
                    i6++;
                    i4 = i7;
                }
            }
        }
        int i8 = i5;
        if (!this.id.equals("")) {
            i8 = i5 + CodedOutputByteBufferNano.computeStringSize(4, this.id);
        }
        int i9 = this.left;
        int i10 = i8;
        if (i9 != 0) {
            i10 = i8 + CodedOutputByteBufferNano.computeInt32Size(5, i9);
        }
        int i11 = this.top;
        int i12 = i10;
        if (i11 != 0) {
            i12 = i10 + CodedOutputByteBufferNano.computeInt32Size(6, i11);
        }
        int i13 = this.width;
        int i14 = i12;
        if (i13 != 0) {
            i14 = i12 + CodedOutputByteBufferNano.computeInt32Size(7, i13);
        }
        int i15 = this.height;
        int i16 = i14;
        if (i15 != 0) {
            i16 = i14 + CodedOutputByteBufferNano.computeInt32Size(8, i15);
        }
        int i17 = this.scrollX;
        int i18 = i16;
        if (i17 != 0) {
            i18 = i16 + CodedOutputByteBufferNano.computeInt32Size(9, i17);
        }
        int i19 = this.scrollY;
        int i20 = i18;
        if (i19 != 0) {
            i20 = i18 + CodedOutputByteBufferNano.computeInt32Size(10, i19);
        }
        int i21 = i20;
        if (Float.floatToIntBits(this.translationX) != Float.floatToIntBits(ActionBarShadowController.ELEVATION_LOW)) {
            i21 = i20 + CodedOutputByteBufferNano.computeFloatSize(11, this.translationX);
        }
        int i22 = i21;
        if (Float.floatToIntBits(this.translationY) != Float.floatToIntBits(ActionBarShadowController.ELEVATION_LOW)) {
            i22 = i21 + CodedOutputByteBufferNano.computeFloatSize(12, this.translationY);
        }
        int i23 = i22;
        if (Float.floatToIntBits(this.scaleX) != Float.floatToIntBits(1.0f)) {
            i23 = i22 + CodedOutputByteBufferNano.computeFloatSize(13, this.scaleX);
        }
        int i24 = i23;
        if (Float.floatToIntBits(this.scaleY) != Float.floatToIntBits(1.0f)) {
            i24 = i23 + CodedOutputByteBufferNano.computeFloatSize(14, this.scaleY);
        }
        int i25 = i24;
        if (Float.floatToIntBits(this.alpha) != Float.floatToIntBits(1.0f)) {
            i25 = i24 + CodedOutputByteBufferNano.computeFloatSize(15, this.alpha);
        }
        boolean z = this.willNotDraw;
        int i26 = i25;
        if (z) {
            i26 = i25 + CodedOutputByteBufferNano.computeBoolSize(16, z);
        }
        boolean z2 = this.clipChildren;
        int i27 = i26;
        if (z2) {
            i27 = i26 + CodedOutputByteBufferNano.computeBoolSize(17, z2);
        }
        int i28 = this.visibility;
        int i29 = i27;
        if (i28 != 0) {
            i29 = i27 + CodedOutputByteBufferNano.computeInt32Size(18, i28);
        }
        int i30 = i29;
        if (Float.floatToIntBits(this.elevation) != Float.floatToIntBits(ActionBarShadowController.ELEVATION_LOW)) {
            i30 = i29 + CodedOutputByteBufferNano.computeFloatSize(19, this.elevation);
        }
        return i30;
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: mergeFrom */
    public ViewNode m512mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
        while (true) {
            int readTag = codedInputByteBufferNano.readTag();
            switch (readTag) {
                case 0:
                    return this;
                case 8:
                    this.classnameIndex = codedInputByteBufferNano.readInt32();
                    break;
                case 16:
                    this.hashcode = codedInputByteBufferNano.readInt32();
                    break;
                case 26:
                    int repeatedFieldArrayLength = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 26);
                    ViewNode[] viewNodeArr = this.children;
                    int length = viewNodeArr == null ? 0 : viewNodeArr.length;
                    int i = repeatedFieldArrayLength + length;
                    ViewNode[] viewNodeArr2 = new ViewNode[i];
                    int i2 = length;
                    if (length != 0) {
                        System.arraycopy(viewNodeArr, 0, viewNodeArr2, 0, length);
                        i2 = length;
                    }
                    while (i2 < i - 1) {
                        ViewNode viewNode = new ViewNode();
                        viewNodeArr2[i2] = viewNode;
                        codedInputByteBufferNano.readMessage(viewNode);
                        codedInputByteBufferNano.readTag();
                        i2++;
                    }
                    ViewNode viewNode2 = new ViewNode();
                    viewNodeArr2[i2] = viewNode2;
                    codedInputByteBufferNano.readMessage(viewNode2);
                    this.children = viewNodeArr2;
                    break;
                case 34:
                    this.id = codedInputByteBufferNano.readString();
                    break;
                case 40:
                    this.left = codedInputByteBufferNano.readInt32();
                    break;
                case 48:
                    this.top = codedInputByteBufferNano.readInt32();
                    break;
                case 56:
                    this.width = codedInputByteBufferNano.readInt32();
                    break;
                case 64:
                    this.height = codedInputByteBufferNano.readInt32();
                    break;
                case 72:
                    this.scrollX = codedInputByteBufferNano.readInt32();
                    break;
                case 80:
                    this.scrollY = codedInputByteBufferNano.readInt32();
                    break;
                case 93:
                    this.translationX = codedInputByteBufferNano.readFloat();
                    break;
                case 101:
                    this.translationY = codedInputByteBufferNano.readFloat();
                    break;
                case 109:
                    this.scaleX = codedInputByteBufferNano.readFloat();
                    break;
                case 117:
                    this.scaleY = codedInputByteBufferNano.readFloat();
                    break;
                case R$styleable.AppCompatTheme_windowMinWidthMinor /* 125 */:
                    this.alpha = codedInputByteBufferNano.readFloat();
                    break;
                case RecyclerView.ViewHolder.FLAG_IGNORE /* 128 */:
                    this.willNotDraw = codedInputByteBufferNano.readBool();
                    break;
                case 136:
                    this.clipChildren = codedInputByteBufferNano.readBool();
                    break;
                case 144:
                    this.visibility = codedInputByteBufferNano.readInt32();
                    break;
                case 157:
                    this.elevation = codedInputByteBufferNano.readFloat();
                    break;
                default:
                    if (WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                        break;
                    } else {
                        return this;
                    }
            }
        }
    }

    public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
        int i = this.classnameIndex;
        if (i != 0) {
            codedOutputByteBufferNano.writeInt32(1, i);
        }
        int i2 = this.hashcode;
        if (i2 != 0) {
            codedOutputByteBufferNano.writeInt32(2, i2);
        }
        ViewNode[] viewNodeArr = this.children;
        if (viewNodeArr != null && viewNodeArr.length > 0) {
            int i3 = 0;
            while (true) {
                ViewNode[] viewNodeArr2 = this.children;
                if (i3 >= viewNodeArr2.length) {
                    break;
                }
                ViewNode viewNode = viewNodeArr2[i3];
                if (viewNode != null) {
                    codedOutputByteBufferNano.writeMessage(3, viewNode);
                }
                i3++;
            }
        }
        if (!this.id.equals("")) {
            codedOutputByteBufferNano.writeString(4, this.id);
        }
        int i4 = this.left;
        if (i4 != 0) {
            codedOutputByteBufferNano.writeInt32(5, i4);
        }
        int i5 = this.top;
        if (i5 != 0) {
            codedOutputByteBufferNano.writeInt32(6, i5);
        }
        int i6 = this.width;
        if (i6 != 0) {
            codedOutputByteBufferNano.writeInt32(7, i6);
        }
        int i7 = this.height;
        if (i7 != 0) {
            codedOutputByteBufferNano.writeInt32(8, i7);
        }
        int i8 = this.scrollX;
        if (i8 != 0) {
            codedOutputByteBufferNano.writeInt32(9, i8);
        }
        int i9 = this.scrollY;
        if (i9 != 0) {
            codedOutputByteBufferNano.writeInt32(10, i9);
        }
        if (Float.floatToIntBits(this.translationX) != Float.floatToIntBits(ActionBarShadowController.ELEVATION_LOW)) {
            codedOutputByteBufferNano.writeFloat(11, this.translationX);
        }
        if (Float.floatToIntBits(this.translationY) != Float.floatToIntBits(ActionBarShadowController.ELEVATION_LOW)) {
            codedOutputByteBufferNano.writeFloat(12, this.translationY);
        }
        if (Float.floatToIntBits(this.scaleX) != Float.floatToIntBits(1.0f)) {
            codedOutputByteBufferNano.writeFloat(13, this.scaleX);
        }
        if (Float.floatToIntBits(this.scaleY) != Float.floatToIntBits(1.0f)) {
            codedOutputByteBufferNano.writeFloat(14, this.scaleY);
        }
        if (Float.floatToIntBits(this.alpha) != Float.floatToIntBits(1.0f)) {
            codedOutputByteBufferNano.writeFloat(15, this.alpha);
        }
        boolean z = this.willNotDraw;
        if (z) {
            codedOutputByteBufferNano.writeBool(16, z);
        }
        boolean z2 = this.clipChildren;
        if (z2) {
            codedOutputByteBufferNano.writeBool(17, z2);
        }
        int i10 = this.visibility;
        if (i10 != 0) {
            codedOutputByteBufferNano.writeInt32(18, i10);
        }
        if (Float.floatToIntBits(this.elevation) != Float.floatToIntBits(ActionBarShadowController.ELEVATION_LOW)) {
            codedOutputByteBufferNano.writeFloat(19, this.elevation);
        }
        super.writeTo(codedOutputByteBufferNano);
    }
}