package android.hardware.google.pixel.vendor;

import com.google.protobuf.ByteString;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.Internal;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.MessageLiteOrBuilder;
import com.google.protobuf.Parser;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

/* loaded from: mainsysui33.jar:android/hardware/google/pixel/vendor/PixelAtoms.class */
public final class PixelAtoms {

    /* renamed from: android.hardware.google.pixel.vendor.PixelAtoms$1  reason: invalid class name */
    /* loaded from: mainsysui33.jar:android/hardware/google/pixel/vendor/PixelAtoms$1.class */
    public static /* synthetic */ class AnonymousClass1 {
        public static final /* synthetic */ int[] $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke;

        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:17:0x0059 -> B:33:0x0014). Please submit an issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:19:0x005d -> B:43:0x001f). Please submit an issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:21:0x0061 -> B:39:0x002a). Please submit an issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:23:0x0065 -> B:35:0x0035). Please submit an issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:25:0x0069 -> B:31:0x0040). Please submit an issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:27:0x006d -> B:41:0x004c). Please submit an issue!!! */
        static {
            int[] iArr = new int[GeneratedMessageLite.MethodToInvoke.values().length];
            $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke = iArr;
            try {
                iArr[GeneratedMessageLite.MethodToInvoke.NEW_MUTABLE_INSTANCE.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke[GeneratedMessageLite.MethodToInvoke.NEW_BUILDER.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke[GeneratedMessageLite.MethodToInvoke.BUILD_MESSAGE_INFO.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke[GeneratedMessageLite.MethodToInvoke.GET_DEFAULT_INSTANCE.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke[GeneratedMessageLite.MethodToInvoke.GET_PARSER.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke[GeneratedMessageLite.MethodToInvoke.GET_MEMOIZED_IS_INITIALIZED.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke[GeneratedMessageLite.MethodToInvoke.SET_MEMOIZED_IS_INITIALIZED.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
        }
    }

    /* loaded from: mainsysui33.jar:android/hardware/google/pixel/vendor/PixelAtoms$DoubleTapNanoappEventReported.class */
    public static final class DoubleTapNanoappEventReported extends GeneratedMessageLite<DoubleTapNanoappEventReported, Builder> implements DoubleTapNanoappEventReportedOrBuilder {
        private static final DoubleTapNanoappEventReported DEFAULT_INSTANCE;
        private static volatile Parser<DoubleTapNanoappEventReported> PARSER;

        /* loaded from: mainsysui33.jar:android/hardware/google/pixel/vendor/PixelAtoms$DoubleTapNanoappEventReported$Builder.class */
        public static final class Builder extends GeneratedMessageLite.Builder<DoubleTapNanoappEventReported, Builder> implements DoubleTapNanoappEventReportedOrBuilder {
            private Builder() {
                super(DoubleTapNanoappEventReported.DEFAULT_INSTANCE);
            }
        }

        /* loaded from: mainsysui33.jar:android/hardware/google/pixel/vendor/PixelAtoms$DoubleTapNanoappEventReported$Type.class */
        public enum Type implements Internal.EnumLite {
            UNKNOWN(0),
            GATE_START(1),
            GATE_STOP(2),
            HIGH_IMU_ODR_START(3),
            HIGH_IMU_ODR_STOP(4),
            ML_PREDICTION_START(5),
            ML_PREDICTION_STOP(6),
            SINGLE_TAP(7),
            DOUBLE_TAP(8);
            
            public static final int DOUBLE_TAP_VALUE = 8;
            public static final int GATE_START_VALUE = 1;
            public static final int GATE_STOP_VALUE = 2;
            public static final int HIGH_IMU_ODR_START_VALUE = 3;
            public static final int HIGH_IMU_ODR_STOP_VALUE = 4;
            public static final int ML_PREDICTION_START_VALUE = 5;
            public static final int ML_PREDICTION_STOP_VALUE = 6;
            public static final int SINGLE_TAP_VALUE = 7;
            public static final int UNKNOWN_VALUE = 0;
            private static final Internal.EnumLiteMap<Type> internalValueMap = new Internal.EnumLiteMap<Type>() { // from class: android.hardware.google.pixel.vendor.PixelAtoms.DoubleTapNanoappEventReported.Type.1
                /* JADX DEBUG: Method merged with bridge method */
                /* renamed from: findValueByNumber */
                public Type m8findValueByNumber(int i) {
                    return Type.forNumber(i);
                }
            };
            private final int value;

            /* loaded from: mainsysui33.jar:android/hardware/google/pixel/vendor/PixelAtoms$DoubleTapNanoappEventReported$Type$TypeVerifier.class */
            public static final class TypeVerifier implements Internal.EnumVerifier {
                public static final Internal.EnumVerifier INSTANCE = new TypeVerifier();

                private TypeVerifier() {
                }

                public boolean isInRange(int i) {
                    return Type.forNumber(i) != null;
                }
            }

            Type(int i) {
                this.value = i;
            }

            public static Type forNumber(int i) {
                switch (i) {
                    case 0:
                        return UNKNOWN;
                    case 1:
                        return GATE_START;
                    case 2:
                        return GATE_STOP;
                    case 3:
                        return HIGH_IMU_ODR_START;
                    case 4:
                        return HIGH_IMU_ODR_STOP;
                    case 5:
                        return ML_PREDICTION_START;
                    case 6:
                        return ML_PREDICTION_STOP;
                    case 7:
                        return SINGLE_TAP;
                    case 8:
                        return DOUBLE_TAP;
                    default:
                        return null;
                }
            }

            public static Internal.EnumLiteMap<Type> internalGetValueMap() {
                return internalValueMap;
            }

            public static Internal.EnumVerifier internalGetVerifier() {
                return TypeVerifier.INSTANCE;
            }

            @Deprecated
            public static Type valueOf(int i) {
                return forNumber(i);
            }

            public final int getNumber() {
                return this.value;
            }
        }

        static {
            DoubleTapNanoappEventReported doubleTapNanoappEventReported = new DoubleTapNanoappEventReported();
            DEFAULT_INSTANCE = doubleTapNanoappEventReported;
            GeneratedMessageLite.registerDefaultInstance(DoubleTapNanoappEventReported.class, doubleTapNanoappEventReported);
        }

        private DoubleTapNanoappEventReported() {
        }

        public static DoubleTapNanoappEventReported getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(DoubleTapNanoappEventReported doubleTapNanoappEventReported) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(doubleTapNanoappEventReported);
        }

        public static DoubleTapNanoappEventReported parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (DoubleTapNanoappEventReported) GeneratedMessageLite.parseDelimitedFrom(DEFAULT_INSTANCE, inputStream);
        }

        public static DoubleTapNanoappEventReported parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (DoubleTapNanoappEventReported) GeneratedMessageLite.parseDelimitedFrom(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static DoubleTapNanoappEventReported parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (DoubleTapNanoappEventReported) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteString);
        }

        public static DoubleTapNanoappEventReported parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (DoubleTapNanoappEventReported) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteString, extensionRegistryLite);
        }

        public static DoubleTapNanoappEventReported parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (DoubleTapNanoappEventReported) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, codedInputStream);
        }

        public static DoubleTapNanoappEventReported parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (DoubleTapNanoappEventReported) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, codedInputStream, extensionRegistryLite);
        }

        public static DoubleTapNanoappEventReported parseFrom(InputStream inputStream) throws IOException {
            return (DoubleTapNanoappEventReported) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, inputStream);
        }

        public static DoubleTapNanoappEventReported parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (DoubleTapNanoappEventReported) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static DoubleTapNanoappEventReported parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (DoubleTapNanoappEventReported) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteBuffer);
        }

        public static DoubleTapNanoappEventReported parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (DoubleTapNanoappEventReported) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteBuffer, extensionRegistryLite);
        }

        public static DoubleTapNanoappEventReported parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (DoubleTapNanoappEventReported) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, bArr);
        }

        public static DoubleTapNanoappEventReported parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (DoubleTapNanoappEventReported) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, bArr, extensionRegistryLite);
        }

        public static Parser<DoubleTapNanoappEventReported> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }

        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            switch (AnonymousClass1.$SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke[methodToInvoke.ordinal()]) {
                case 1:
                    return new DoubleTapNanoappEventReported();
                case 2:
                    return new Builder();
                case 3:
                    return GeneratedMessageLite.newMessageInfo(DEFAULT_INSTANCE, "\u0001��", (Object[]) null);
                case 4:
                    return DEFAULT_INSTANCE;
                case 5:
                    GeneratedMessageLite.DefaultInstanceBasedParser defaultInstanceBasedParser = PARSER;
                    GeneratedMessageLite.DefaultInstanceBasedParser defaultInstanceBasedParser2 = defaultInstanceBasedParser;
                    if (defaultInstanceBasedParser == null) {
                        synchronized (DoubleTapNanoappEventReported.class) {
                            try {
                                GeneratedMessageLite.DefaultInstanceBasedParser defaultInstanceBasedParser3 = PARSER;
                                defaultInstanceBasedParser2 = defaultInstanceBasedParser3;
                                if (defaultInstanceBasedParser3 == null) {
                                    defaultInstanceBasedParser2 = new GeneratedMessageLite.DefaultInstanceBasedParser(DEFAULT_INSTANCE);
                                    PARSER = defaultInstanceBasedParser2;
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    }
                    return defaultInstanceBasedParser2;
                case 6:
                    return (byte) 1;
                case 7:
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }
    }

    /* loaded from: mainsysui33.jar:android/hardware/google/pixel/vendor/PixelAtoms$DoubleTapNanoappEventReportedOrBuilder.class */
    public interface DoubleTapNanoappEventReportedOrBuilder extends MessageLiteOrBuilder {
    }

    /* loaded from: mainsysui33.jar:android/hardware/google/pixel/vendor/PixelAtoms$ReverseDomainNames.class */
    public static final class ReverseDomainNames extends GeneratedMessageLite<ReverseDomainNames, Builder> implements ReverseDomainNamesOrBuilder {
        private static final ReverseDomainNames DEFAULT_INSTANCE;
        private static volatile Parser<ReverseDomainNames> PARSER;
        public static final int PIXEL_FIELD_NUMBER = 1;
        private int bitField0_;
        private String pixel_ = "com.google.pixel";

        /* loaded from: mainsysui33.jar:android/hardware/google/pixel/vendor/PixelAtoms$ReverseDomainNames$Builder.class */
        public static final class Builder extends GeneratedMessageLite.Builder<ReverseDomainNames, Builder> implements ReverseDomainNamesOrBuilder {
            private Builder() {
                super(ReverseDomainNames.DEFAULT_INSTANCE);
            }

            public Builder clearPixel() {
                copyOnWrite();
                ((ReverseDomainNames) ((GeneratedMessageLite.Builder) this).instance).clearPixel();
                return this;
            }

            @Override // android.hardware.google.pixel.vendor.PixelAtoms.ReverseDomainNamesOrBuilder
            public String getPixel() {
                return ((ReverseDomainNames) ((GeneratedMessageLite.Builder) this).instance).getPixel();
            }

            @Override // android.hardware.google.pixel.vendor.PixelAtoms.ReverseDomainNamesOrBuilder
            public ByteString getPixelBytes() {
                return ((ReverseDomainNames) ((GeneratedMessageLite.Builder) this).instance).getPixelBytes();
            }

            @Override // android.hardware.google.pixel.vendor.PixelAtoms.ReverseDomainNamesOrBuilder
            public boolean hasPixel() {
                return ((ReverseDomainNames) ((GeneratedMessageLite.Builder) this).instance).hasPixel();
            }

            public Builder setPixel(String str) {
                copyOnWrite();
                ((ReverseDomainNames) ((GeneratedMessageLite.Builder) this).instance).setPixel(str);
                return this;
            }

            public Builder setPixelBytes(ByteString byteString) {
                copyOnWrite();
                ((ReverseDomainNames) ((GeneratedMessageLite.Builder) this).instance).setPixelBytes(byteString);
                return this;
            }
        }

        static {
            ReverseDomainNames reverseDomainNames = new ReverseDomainNames();
            DEFAULT_INSTANCE = reverseDomainNames;
            GeneratedMessageLite.registerDefaultInstance(ReverseDomainNames.class, reverseDomainNames);
        }

        private ReverseDomainNames() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearPixel() {
            this.bitField0_ &= -2;
            this.pixel_ = getDefaultInstance().getPixel();
        }

        public static ReverseDomainNames getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(ReverseDomainNames reverseDomainNames) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(reverseDomainNames);
        }

        public static ReverseDomainNames parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (ReverseDomainNames) GeneratedMessageLite.parseDelimitedFrom(DEFAULT_INSTANCE, inputStream);
        }

        public static ReverseDomainNames parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (ReverseDomainNames) GeneratedMessageLite.parseDelimitedFrom(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static ReverseDomainNames parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (ReverseDomainNames) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteString);
        }

        public static ReverseDomainNames parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (ReverseDomainNames) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteString, extensionRegistryLite);
        }

        public static ReverseDomainNames parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (ReverseDomainNames) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, codedInputStream);
        }

        public static ReverseDomainNames parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (ReverseDomainNames) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, codedInputStream, extensionRegistryLite);
        }

        public static ReverseDomainNames parseFrom(InputStream inputStream) throws IOException {
            return (ReverseDomainNames) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, inputStream);
        }

        public static ReverseDomainNames parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (ReverseDomainNames) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static ReverseDomainNames parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (ReverseDomainNames) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteBuffer);
        }

        public static ReverseDomainNames parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (ReverseDomainNames) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteBuffer, extensionRegistryLite);
        }

        public static ReverseDomainNames parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (ReverseDomainNames) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, bArr);
        }

        public static ReverseDomainNames parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (ReverseDomainNames) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, bArr, extensionRegistryLite);
        }

        public static Parser<ReverseDomainNames> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setPixel(String str) {
            str.getClass();
            this.bitField0_ |= 1;
            this.pixel_ = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setPixelBytes(ByteString byteString) {
            byteString.getClass();
            this.bitField0_ |= 1;
            this.pixel_ = byteString.toStringUtf8();
        }

        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            switch (AnonymousClass1.$SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke[methodToInvoke.ordinal()]) {
                case 1:
                    return new ReverseDomainNames();
                case 2:
                    return new Builder();
                case 3:
                    return GeneratedMessageLite.newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0001��\u0001\u0001\u0001\u0001������\u0001\b��", new Object[]{"bitField0_", "pixel_"});
                case 4:
                    return DEFAULT_INSTANCE;
                case 5:
                    GeneratedMessageLite.DefaultInstanceBasedParser defaultInstanceBasedParser = PARSER;
                    GeneratedMessageLite.DefaultInstanceBasedParser defaultInstanceBasedParser2 = defaultInstanceBasedParser;
                    if (defaultInstanceBasedParser == null) {
                        synchronized (ReverseDomainNames.class) {
                            try {
                                GeneratedMessageLite.DefaultInstanceBasedParser defaultInstanceBasedParser3 = PARSER;
                                defaultInstanceBasedParser2 = defaultInstanceBasedParser3;
                                if (defaultInstanceBasedParser3 == null) {
                                    defaultInstanceBasedParser2 = new GeneratedMessageLite.DefaultInstanceBasedParser(DEFAULT_INSTANCE);
                                    PARSER = defaultInstanceBasedParser2;
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    }
                    return defaultInstanceBasedParser2;
                case 6:
                    return (byte) 1;
                case 7:
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        @Override // android.hardware.google.pixel.vendor.PixelAtoms.ReverseDomainNamesOrBuilder
        public String getPixel() {
            return this.pixel_;
        }

        @Override // android.hardware.google.pixel.vendor.PixelAtoms.ReverseDomainNamesOrBuilder
        public ByteString getPixelBytes() {
            return ByteString.copyFromUtf8(this.pixel_);
        }

        @Override // android.hardware.google.pixel.vendor.PixelAtoms.ReverseDomainNamesOrBuilder
        public boolean hasPixel() {
            boolean z = true;
            if ((this.bitField0_ & 1) == 0) {
                z = false;
            }
            return z;
        }
    }

    /* loaded from: mainsysui33.jar:android/hardware/google/pixel/vendor/PixelAtoms$ReverseDomainNamesOrBuilder.class */
    public interface ReverseDomainNamesOrBuilder extends MessageLiteOrBuilder {
        String getPixel();

        ByteString getPixelBytes();

        boolean hasPixel();
    }

    private PixelAtoms() {
    }

    public static void registerAllExtensions(ExtensionRegistryLite extensionRegistryLite) {
    }
}