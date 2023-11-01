package com.android.systemui.screenshot;

import android.content.Context;
import android.graphics.Rect;
import android.media.Image;
import android.media.ImageReader;
import android.os.DeadObjectException;
import android.os.IBinder;
import android.os.ICancellationSignal;
import android.os.RemoteException;
import android.util.Log;
import android.view.IScrollCaptureCallbacks;
import android.view.IScrollCaptureConnection;
import android.view.IScrollCaptureResponseListener;
import android.view.IWindowManager;
import android.view.ScrollCaptureResponse;
import androidx.concurrent.futures.CallbackToFutureAdapter;
import com.android.internal.annotations.VisibleForTesting;
import com.android.systemui.screenshot.ScrollCaptureClient;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.Objects;
import java.util.concurrent.Executor;

/* loaded from: mainsysui33.jar:com/android/systemui/screenshot/ScrollCaptureClient.class */
public class ScrollCaptureClient {
    @VisibleForTesting
    public static final int MATCH_ANY_TASK = -1;
    public static final String TAG = LogConfig.logTag(ScrollCaptureClient.class);
    public final Executor mBgExecutor;
    public IBinder mHostWindowToken;
    public final IWindowManager mWindowManagerService;

    /* loaded from: mainsysui33.jar:com/android/systemui/screenshot/ScrollCaptureClient$CaptureResult.class */
    public static class CaptureResult {
        public final Rect captured;
        public final Image image;
        public final Rect requested;

        public CaptureResult(Image image, Rect rect, Rect rect2) {
            this.image = image;
            this.requested = rect;
            this.captured = rect2;
        }

        public String toString() {
            return "CaptureResult{requested=" + this.requested + " (" + this.requested.width() + "x" + this.requested.height() + "), captured=" + this.captured + " (" + this.captured.width() + "x" + this.captured.height() + "), image=" + this.image + '}';
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/screenshot/ScrollCaptureClient$Session.class */
    public interface Session {
        ListenableFuture<Void> end();

        int getMaxTiles();

        int getPageHeight();

        int getTargetHeight();

        int getTileHeight();

        void release();

        ListenableFuture<CaptureResult> requestTile(int i);
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/screenshot/ScrollCaptureClient$SessionWrapper.class */
    public static class SessionWrapper extends IScrollCaptureCallbacks.Stub implements Session, IBinder.DeathRecipient, ImageReader.OnImageAvailableListener {
        public final Executor mBgExecutor;
        public final Rect mBoundsInWindow;
        public ICancellationSignal mCancellationSignal;
        public Rect mCapturedArea;
        public Image mCapturedImage;
        public IScrollCaptureConnection mConnection;
        public CallbackToFutureAdapter.Completer<Void> mEndCompleter;
        public final Object mLock;
        public ImageReader mReader;
        public Rect mRequestRect;
        public CallbackToFutureAdapter.Completer<Session> mStartCompleter;
        public boolean mStarted;
        public final int mTargetHeight;
        public final int mTileHeight;
        public CallbackToFutureAdapter.Completer<CaptureResult> mTileRequestCompleter;
        public final int mTileWidth;
        public final Rect mWindowBounds;

        /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.screenshot.ScrollCaptureClient$SessionWrapper$$ExternalSyntheticLambda3.run():void] */
        /* renamed from: $r8$lambda$1TsZYhF7ZoMkceocPbqlqO1-JxA */
        public static /* synthetic */ void m4321$r8$lambda$1TsZYhF7ZoMkceocPbqlqO1JxA(SessionWrapper sessionWrapper) {
            sessionWrapper.lambda$requestTile$1();
        }

        /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.screenshot.ScrollCaptureClient$SessionWrapper$$ExternalSyntheticLambda0.attachCompleter(androidx.concurrent.futures.CallbackToFutureAdapter$Completer):java.lang.Object] */
        public static /* synthetic */ Object $r8$lambda$WO2Btku8DuKAzrj6gQCqDRC_R24(SessionWrapper sessionWrapper, CallbackToFutureAdapter.Completer completer) {
            return sessionWrapper.lambda$requestTile$2(completer);
        }

        /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.screenshot.ScrollCaptureClient$SessionWrapper$$ExternalSyntheticLambda2.run():void] */
        public static /* synthetic */ void $r8$lambda$f0Q_LzeIMJqZJrEDfg_JWL6imWw(SessionWrapper sessionWrapper) {
            sessionWrapper.lambda$start$0();
        }

        /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.screenshot.ScrollCaptureClient$SessionWrapper$$ExternalSyntheticLambda1.attachCompleter(androidx.concurrent.futures.CallbackToFutureAdapter$Completer):java.lang.Object] */
        /* renamed from: $r8$lambda$gDIRXQjUBPPb4R-ZHAd3HM4Yjps */
        public static /* synthetic */ Object m4322$r8$lambda$gDIRXQjUBPPb4RZHAd3HM4Yjps(SessionWrapper sessionWrapper, CallbackToFutureAdapter.Completer completer) {
            return sessionWrapper.lambda$end$3(completer);
        }

        public SessionWrapper(IScrollCaptureConnection iScrollCaptureConnection, Rect rect, Rect rect2, float f, Executor executor) throws RemoteException {
            this.mLock = new Object();
            Objects.requireNonNull(iScrollCaptureConnection);
            IScrollCaptureConnection iScrollCaptureConnection2 = iScrollCaptureConnection;
            this.mConnection = iScrollCaptureConnection2;
            iScrollCaptureConnection2.asBinder().linkToDeath(this, 0);
            Objects.requireNonNull(rect);
            this.mWindowBounds = rect;
            Objects.requireNonNull(rect2);
            this.mBoundsInWindow = rect2;
            int min = Math.min(4194304, (rect2.width() * rect2.height()) / 2);
            this.mTileWidth = rect2.width();
            this.mTileHeight = min / rect2.width();
            this.mTargetHeight = (int) (rect2.height() * f);
            this.mBgExecutor = executor;
        }

        public /* synthetic */ Object lambda$end$3(CallbackToFutureAdapter.Completer completer) throws Exception {
            if (!this.mStarted) {
                try {
                    this.mConnection.asBinder().unlinkToDeath(this, 0);
                    this.mConnection.close();
                } catch (RemoteException e) {
                }
                this.mConnection = null;
                completer.set(null);
                return "";
            }
            this.mEndCompleter = completer;
            try {
                this.mConnection.endCapture();
                return "IScrollCaptureCallbacks#onCaptureEnded";
            } catch (RemoteException e2) {
                completer.setException(e2);
                return "IScrollCaptureCallbacks#onCaptureEnded";
            }
        }

        public /* synthetic */ void lambda$requestTile$1() {
            try {
                this.mCancellationSignal.cancel();
            } catch (RemoteException e) {
            }
        }

        public /* synthetic */ Object lambda$requestTile$2(CallbackToFutureAdapter.Completer completer) throws Exception {
            IScrollCaptureConnection iScrollCaptureConnection = this.mConnection;
            if (iScrollCaptureConnection == null || !iScrollCaptureConnection.asBinder().isBinderAlive()) {
                completer.setException(new DeadObjectException("Connection is closed!"));
                return "";
            }
            try {
                this.mTileRequestCompleter = completer;
                this.mCancellationSignal = this.mConnection.requestImage(this.mRequestRect);
                completer.addCancellationListener(new Runnable() { // from class: com.android.systemui.screenshot.ScrollCaptureClient$SessionWrapper$$ExternalSyntheticLambda3
                    @Override // java.lang.Runnable
                    public final void run() {
                        ScrollCaptureClient.SessionWrapper.m4321$r8$lambda$1TsZYhF7ZoMkceocPbqlqO1JxA(ScrollCaptureClient.SessionWrapper.this);
                    }
                }, new SaveImageInBackgroundTask$$ExternalSyntheticLambda0());
                return "IScrollCaptureCallbacks#onImageRequestCompleted";
            } catch (RemoteException e) {
                completer.setException(e);
                return "IScrollCaptureCallbacks#onImageRequestCompleted";
            }
        }

        public /* synthetic */ void lambda$start$0() {
            try {
                this.mCancellationSignal.cancel();
            } catch (RemoteException e) {
            }
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            Log.d(ScrollCaptureClient.TAG, "binderDied! The target process just crashed :-(");
            this.mConnection = null;
            CallbackToFutureAdapter.Completer<Session> completer = this.mStartCompleter;
            if (completer != null) {
                completer.setException(new DeadObjectException("The remote process died"));
            }
            CallbackToFutureAdapter.Completer<CaptureResult> completer2 = this.mTileRequestCompleter;
            if (completer2 != null) {
                completer2.setException(new DeadObjectException("The remote process died"));
            }
            CallbackToFutureAdapter.Completer<Void> completer3 = this.mEndCompleter;
            if (completer3 != null) {
                completer3.setException(new DeadObjectException("The remote process died"));
            }
        }

        public final void completeCaptureRequest() {
            CaptureResult captureResult = new CaptureResult(this.mCapturedImage, this.mRequestRect, this.mCapturedArea);
            this.mCapturedImage = null;
            this.mRequestRect = null;
            this.mCapturedArea = null;
            this.mTileRequestCompleter.set(captureResult);
        }

        @Override // com.android.systemui.screenshot.ScrollCaptureClient.Session
        public ListenableFuture<Void> end() {
            Log.d(ScrollCaptureClient.TAG, "end()");
            return CallbackToFutureAdapter.getFuture(new CallbackToFutureAdapter.Resolver() { // from class: com.android.systemui.screenshot.ScrollCaptureClient$SessionWrapper$$ExternalSyntheticLambda1
                @Override // androidx.concurrent.futures.CallbackToFutureAdapter.Resolver
                public final Object attachCompleter(CallbackToFutureAdapter.Completer completer) {
                    return ScrollCaptureClient.SessionWrapper.m4322$r8$lambda$gDIRXQjUBPPb4RZHAd3HM4Yjps(ScrollCaptureClient.SessionWrapper.this, completer);
                }
            });
        }

        @Override // com.android.systemui.screenshot.ScrollCaptureClient.Session
        public int getMaxTiles() {
            return 30;
        }

        @Override // com.android.systemui.screenshot.ScrollCaptureClient.Session
        public int getPageHeight() {
            return this.mBoundsInWindow.height();
        }

        @Override // com.android.systemui.screenshot.ScrollCaptureClient.Session
        public int getTargetHeight() {
            return this.mTargetHeight;
        }

        @Override // com.android.systemui.screenshot.ScrollCaptureClient.Session
        public int getTileHeight() {
            return this.mTileHeight;
        }

        public void onCaptureEnded() {
            try {
                this.mConnection.close();
            } catch (RemoteException e) {
            }
            this.mConnection = null;
            this.mEndCompleter.set(null);
        }

        public void onCaptureStarted() {
            Log.d(ScrollCaptureClient.TAG, "onCaptureStarted");
            this.mStartCompleter.set(this);
        }

        @Override // android.media.ImageReader.OnImageAvailableListener
        public void onImageAvailable(ImageReader imageReader) {
            synchronized (this.mLock) {
                this.mCapturedImage = this.mReader.acquireLatestImage();
                if (this.mCapturedArea != null) {
                    completeCaptureRequest();
                }
            }
        }

        public void onImageRequestCompleted(int i, Rect rect) {
            synchronized (this.mLock) {
                this.mCapturedArea = rect;
                if (this.mCapturedImage != null || rect == null || rect.isEmpty()) {
                    completeCaptureRequest();
                }
            }
        }

        @Override // com.android.systemui.screenshot.ScrollCaptureClient.Session
        public void release() {
            this.mReader.close();
        }

        @Override // com.android.systemui.screenshot.ScrollCaptureClient.Session
        public ListenableFuture<CaptureResult> requestTile(int i) {
            this.mRequestRect = new Rect(0, i, this.mTileWidth, this.mTileHeight + i);
            return CallbackToFutureAdapter.getFuture(new CallbackToFutureAdapter.Resolver() { // from class: com.android.systemui.screenshot.ScrollCaptureClient$SessionWrapper$$ExternalSyntheticLambda0
                @Override // androidx.concurrent.futures.CallbackToFutureAdapter.Resolver
                public final Object attachCompleter(CallbackToFutureAdapter.Completer completer) {
                    return ScrollCaptureClient.SessionWrapper.$r8$lambda$WO2Btku8DuKAzrj6gQCqDRC_R24(ScrollCaptureClient.SessionWrapper.this, completer);
                }
            });
        }

        public final void start(CallbackToFutureAdapter.Completer<Session> completer) {
            ImageReader newInstance = ImageReader.newInstance(this.mTileWidth, this.mTileHeight, 1, 30, 256L);
            this.mReader = newInstance;
            this.mStartCompleter = completer;
            newInstance.setOnImageAvailableListenerWithExecutor(this, this.mBgExecutor);
            try {
                this.mCancellationSignal = this.mConnection.startCapture(this.mReader.getSurface(), this);
                completer.addCancellationListener(new Runnable() { // from class: com.android.systemui.screenshot.ScrollCaptureClient$SessionWrapper$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        ScrollCaptureClient.SessionWrapper.$r8$lambda$f0Q_LzeIMJqZJrEDfg_JWL6imWw(ScrollCaptureClient.SessionWrapper.this);
                    }
                }, new SaveImageInBackgroundTask$$ExternalSyntheticLambda0());
                this.mStarted = true;
            } catch (RemoteException e) {
                this.mReader.close();
                completer.setException(e);
            }
        }
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.screenshot.ScrollCaptureClient$$ExternalSyntheticLambda1.attachCompleter(androidx.concurrent.futures.CallbackToFutureAdapter$Completer):java.lang.Object] */
    public static /* synthetic */ Object $r8$lambda$cnJXGVMvkhtRlp19lL_v4k85vnw(ScrollCaptureClient scrollCaptureClient, int i, int i2, CallbackToFutureAdapter.Completer completer) {
        return scrollCaptureClient.lambda$request$0(i, i2, completer);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.screenshot.ScrollCaptureClient$$ExternalSyntheticLambda0.attachCompleter(androidx.concurrent.futures.CallbackToFutureAdapter$Completer):java.lang.Object] */
    public static /* synthetic */ Object $r8$lambda$wk068HP7VZNs8NQ02V0YtHrBQXA(ScrollCaptureClient scrollCaptureClient, IScrollCaptureConnection iScrollCaptureConnection, ScrollCaptureResponse scrollCaptureResponse, float f, CallbackToFutureAdapter.Completer completer) {
        return scrollCaptureClient.lambda$start$1(iScrollCaptureConnection, scrollCaptureResponse, f, completer);
    }

    public ScrollCaptureClient(IWindowManager iWindowManager, Executor executor, Context context) {
        Objects.requireNonNull(context.getDisplay(), "context must be associated with a Display!");
        this.mBgExecutor = executor;
        this.mWindowManagerService = iWindowManager;
    }

    public /* synthetic */ Object lambda$request$0(int i, int i2, final CallbackToFutureAdapter.Completer completer) throws Exception {
        try {
            this.mWindowManagerService.requestScrollCapture(i, this.mHostWindowToken, i2, new IScrollCaptureResponseListener.Stub() { // from class: com.android.systemui.screenshot.ScrollCaptureClient.1
                {
                    ScrollCaptureClient.this = this;
                }

                public void onScrollCaptureResponse(ScrollCaptureResponse scrollCaptureResponse) {
                    completer.set(scrollCaptureResponse);
                }
            });
        } catch (RemoteException e) {
            completer.setException(e);
        }
        return "ScrollCaptureClient#request(displayId=" + i + ", taskId=" + i2 + ")";
    }

    public /* synthetic */ Object lambda$start$1(IScrollCaptureConnection iScrollCaptureConnection, ScrollCaptureResponse scrollCaptureResponse, float f, CallbackToFutureAdapter.Completer completer) throws Exception {
        if (iScrollCaptureConnection == null || !iScrollCaptureConnection.asBinder().isBinderAlive()) {
            completer.setException(new DeadObjectException("No active connection!"));
            return "";
        }
        new SessionWrapper(iScrollCaptureConnection, scrollCaptureResponse.getWindowBounds(), scrollCaptureResponse.getBoundsInWindow(), f, this.mBgExecutor).start(completer);
        return "IScrollCaptureCallbacks#onCaptureStarted";
    }

    public ListenableFuture<ScrollCaptureResponse> request(int i) {
        return request(i, -1);
    }

    public ListenableFuture<ScrollCaptureResponse> request(final int i, final int i2) {
        return CallbackToFutureAdapter.getFuture(new CallbackToFutureAdapter.Resolver() { // from class: com.android.systemui.screenshot.ScrollCaptureClient$$ExternalSyntheticLambda1
            @Override // androidx.concurrent.futures.CallbackToFutureAdapter.Resolver
            public final Object attachCompleter(CallbackToFutureAdapter.Completer completer) {
                return ScrollCaptureClient.$r8$lambda$cnJXGVMvkhtRlp19lL_v4k85vnw(ScrollCaptureClient.this, i, i2, completer);
            }
        });
    }

    public void setHostWindowToken(IBinder iBinder) {
        this.mHostWindowToken = iBinder;
    }

    public ListenableFuture<Session> start(final ScrollCaptureResponse scrollCaptureResponse, final float f) {
        final IScrollCaptureConnection connection = scrollCaptureResponse.getConnection();
        return CallbackToFutureAdapter.getFuture(new CallbackToFutureAdapter.Resolver() { // from class: com.android.systemui.screenshot.ScrollCaptureClient$$ExternalSyntheticLambda0
            @Override // androidx.concurrent.futures.CallbackToFutureAdapter.Resolver
            public final Object attachCompleter(CallbackToFutureAdapter.Completer completer) {
                return ScrollCaptureClient.$r8$lambda$wk068HP7VZNs8NQ02V0YtHrBQXA(ScrollCaptureClient.this, connection, scrollCaptureResponse, f, completer);
            }
        });
    }
}