package com.android.app.motiontool;

import android.ddm.DdmHandle;
import com.android.app.motiontool.nano.BeginTraceRequest;
import com.android.app.motiontool.nano.BeginTraceResponse;
import com.android.app.motiontool.nano.EndTraceRequest;
import com.android.app.motiontool.nano.EndTraceResponse;
import com.android.app.motiontool.nano.ErrorResponse;
import com.android.app.motiontool.nano.HandshakeRequest;
import com.android.app.motiontool.nano.HandshakeResponse;
import com.android.app.motiontool.nano.MotionToolsRequest;
import com.android.app.motiontool.nano.MotionToolsResponse;
import com.android.app.motiontool.nano.PollTraceRequest;
import com.android.app.motiontool.nano.PollTraceResponse;
import com.google.protobuf.nano.InvalidProtocolBufferNanoException;
import com.google.protobuf.nano.MessageNano;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.apache.harmony.dalvik.ddmc.Chunk;
import org.apache.harmony.dalvik.ddmc.ChunkHandler;
import org.apache.harmony.dalvik.ddmc.DdmServer;

/* loaded from: mainsysui33.jar:com/android/app/motiontool/DdmHandleMotionTool.class */
public final class DdmHandleMotionTool extends DdmHandle {
    public static DdmHandleMotionTool INSTANCE;
    public final MotionToolManager motionToolManager;
    public static final Companion Companion = new Companion(null);
    public static final int CHUNK_MOTO = ChunkHandler.type("MOTO");

    /* loaded from: mainsysui33.jar:com/android/app/motiontool/DdmHandleMotionTool$Companion.class */
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final DdmHandleMotionTool getInstance(MotionToolManager motionToolManager) {
            DdmHandleMotionTool ddmHandleMotionTool;
            synchronized (this) {
                DdmHandleMotionTool ddmHandleMotionTool2 = DdmHandleMotionTool.INSTANCE;
                ddmHandleMotionTool = ddmHandleMotionTool2;
                if (ddmHandleMotionTool2 == null) {
                    ddmHandleMotionTool = new DdmHandleMotionTool(motionToolManager, null);
                    Companion companion = DdmHandleMotionTool.Companion;
                    DdmHandleMotionTool.INSTANCE = ddmHandleMotionTool;
                }
            }
            return ddmHandleMotionTool;
        }
    }

    public DdmHandleMotionTool(MotionToolManager motionToolManager) {
        this.motionToolManager = motionToolManager;
    }

    public /* synthetic */ DdmHandleMotionTool(MotionToolManager motionToolManager, DefaultConstructorMarker defaultConstructorMarker) {
        this(motionToolManager);
    }

    public final ErrorResponse createUnknownTraceIdResponse(int i) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.code = 2;
        errorResponse.message = "No running Trace found with traceId " + i;
        return errorResponse;
    }

    public final ErrorResponse createWindowNotFoundResponse(String str) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.code = 3;
        errorResponse.message = "No window found with windowId " + str;
        return errorResponse;
    }

    public final MotionToolsResponse handleBeginTraceRequest(final BeginTraceRequest beginTraceRequest) {
        final MotionToolsResponse motionToolsResponse = new MotionToolsResponse();
        tryCatchingMotionToolManagerExceptions(motionToolsResponse, new Function0<Unit>() { // from class: com.android.app.motiontool.DdmHandleMotionTool$handleBeginTraceRequest$1$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            public /* bridge */ /* synthetic */ Object invoke() {
                m479invoke();
                return Unit.INSTANCE;
            }

            /* renamed from: invoke  reason: collision with other method in class */
            public final void m479invoke() {
                MotionToolManager motionToolManager;
                MotionToolsResponse motionToolsResponse2 = MotionToolsResponse.this;
                BeginTraceResponse beginTraceResponse = new BeginTraceResponse();
                DdmHandleMotionTool ddmHandleMotionTool = this;
                BeginTraceRequest beginTraceRequest2 = beginTraceRequest;
                motionToolManager = ddmHandleMotionTool.motionToolManager;
                beginTraceResponse.traceId = motionToolManager.beginTrace(beginTraceRequest2.window.rootWindow);
                motionToolsResponse2.setBeginTrace(beginTraceResponse);
            }
        });
        return motionToolsResponse;
    }

    public Chunk handleChunk(Chunk chunk) {
        MotionToolsResponse handleHandshakeRequest;
        try {
            MotionToolsRequest parseFrom = MotionToolsRequest.parseFrom(DdmHandle.wrapChunk(chunk).array());
            int typeCase = parseFrom.getTypeCase();
            if (typeCase == 1) {
                handleHandshakeRequest = handleHandshakeRequest(parseFrom.getHandshake());
            } else if (typeCase == 2) {
                handleHandshakeRequest = handleBeginTraceRequest(parseFrom.getBeginTrace());
            } else if (typeCase == 3) {
                handleHandshakeRequest = handleEndTraceRequest(parseFrom.getEndTrace());
            } else if (typeCase != 4) {
                handleHandshakeRequest = new MotionToolsResponse();
                ErrorResponse errorResponse = new ErrorResponse();
                errorResponse.code = 1;
                errorResponse.message = "Unknown request type";
                handleHandshakeRequest.setError(errorResponse);
            } else {
                handleHandshakeRequest = handlePollTraceRequest(parseFrom.getPollTrace());
            }
            byte[] byteArray = MessageNano.toByteArray(handleHandshakeRequest);
            return new Chunk(CHUNK_MOTO, byteArray, 0, byteArray.length);
        } catch (InvalidProtocolBufferNanoException e) {
            ErrorResponse errorResponse2 = new ErrorResponse();
            errorResponse2.code = 1;
            errorResponse2.message = "Invalid request format (Protobuf parse exception)";
            MotionToolsResponse motionToolsResponse = new MotionToolsResponse();
            motionToolsResponse.setError(errorResponse2);
            byte[] byteArray2 = MessageNano.toByteArray(motionToolsResponse);
            return new Chunk(CHUNK_MOTO, byteArray2, 0, byteArray2.length);
        }
    }

    public final MotionToolsResponse handleEndTraceRequest(final EndTraceRequest endTraceRequest) {
        final MotionToolsResponse motionToolsResponse = new MotionToolsResponse();
        tryCatchingMotionToolManagerExceptions(motionToolsResponse, new Function0<Unit>() { // from class: com.android.app.motiontool.DdmHandleMotionTool$handleEndTraceRequest$1$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            public /* bridge */ /* synthetic */ Object invoke() {
                m480invoke();
                return Unit.INSTANCE;
            }

            /* renamed from: invoke  reason: collision with other method in class */
            public final void m480invoke() {
                MotionToolManager motionToolManager;
                MotionToolsResponse motionToolsResponse2 = MotionToolsResponse.this;
                EndTraceResponse endTraceResponse = new EndTraceResponse();
                DdmHandleMotionTool ddmHandleMotionTool = this;
                EndTraceRequest endTraceRequest2 = endTraceRequest;
                motionToolManager = ddmHandleMotionTool.motionToolManager;
                endTraceResponse.exportedData = motionToolManager.endTrace(endTraceRequest2.traceId);
                motionToolsResponse2.setEndTrace(endTraceResponse);
            }
        });
        return motionToolsResponse;
    }

    public final MotionToolsResponse handleHandshakeRequest(HandshakeRequest handshakeRequest) {
        MotionToolsResponse motionToolsResponse = new MotionToolsResponse();
        HandshakeResponse handshakeResponse = new HandshakeResponse();
        int i = 1;
        handshakeResponse.serverVersion = 1;
        if (!this.motionToolManager.hasWindow(handshakeRequest.window)) {
            i = 2;
        }
        handshakeResponse.status = i;
        motionToolsResponse.setHandshake(handshakeResponse);
        return motionToolsResponse;
    }

    public final MotionToolsResponse handlePollTraceRequest(final PollTraceRequest pollTraceRequest) {
        final MotionToolsResponse motionToolsResponse = new MotionToolsResponse();
        tryCatchingMotionToolManagerExceptions(motionToolsResponse, new Function0<Unit>() { // from class: com.android.app.motiontool.DdmHandleMotionTool$handlePollTraceRequest$1$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            public /* bridge */ /* synthetic */ Object invoke() {
                m481invoke();
                return Unit.INSTANCE;
            }

            /* renamed from: invoke  reason: collision with other method in class */
            public final void m481invoke() {
                MotionToolManager motionToolManager;
                MotionToolsResponse motionToolsResponse2 = MotionToolsResponse.this;
                PollTraceResponse pollTraceResponse = new PollTraceResponse();
                DdmHandleMotionTool ddmHandleMotionTool = this;
                PollTraceRequest pollTraceRequest2 = pollTraceRequest;
                motionToolManager = ddmHandleMotionTool.motionToolManager;
                pollTraceResponse.exportedData = motionToolManager.pollTrace(pollTraceRequest2.traceId);
                motionToolsResponse2.setPollTrace(pollTraceResponse);
            }
        });
        return motionToolsResponse;
    }

    public void onConnected() {
    }

    public void onDisconnected() {
    }

    public final void register() {
        DdmServer.registerHandler(CHUNK_MOTO, this);
    }

    public final void tryCatchingMotionToolManagerExceptions(MotionToolsResponse motionToolsResponse, Function0<Unit> function0) {
        try {
            function0.invoke();
        } catch (UnknownTraceIdException e) {
            motionToolsResponse.setError(createUnknownTraceIdResponse(e.getTraceId()));
        } catch (WindowNotFoundException e2) {
            motionToolsResponse.setError(createWindowNotFoundResponse(e2.getWindowId()));
        }
    }
}