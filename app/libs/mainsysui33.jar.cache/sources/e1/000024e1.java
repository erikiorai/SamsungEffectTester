package com.android.systemui.screenshot;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.provider.Settings;
import android.util.Log;
import android.view.ScrollCaptureResponse;
import androidx.concurrent.futures.CallbackToFutureAdapter;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.screenshot.ScrollCaptureClient;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;

/* loaded from: mainsysui33.jar:com/android/systemui/screenshot/ScrollCaptureController.class */
public class ScrollCaptureController {
    public static final String TAG = LogConfig.logTag(ScrollCaptureController.class);
    public final Executor mBgExecutor;
    public volatile boolean mCancelled;
    public CallbackToFutureAdapter.Completer<LongScreenshot> mCaptureCompleter;
    public final ScrollCaptureClient mClient;
    public final Context mContext;
    public ListenableFuture<Void> mEndFuture;
    public final UiEventLogger mEventLogger;
    public boolean mFinishOnBoundary;
    public final ImageTileSet mImageTileSet;
    public boolean mScrollingUp = true;
    public ScrollCaptureClient.Session mSession;
    public ListenableFuture<ScrollCaptureClient.Session> mSessionFuture;
    public ListenableFuture<ScrollCaptureClient.CaptureResult> mTileFuture;
    public String mWindowOwner;

    /* loaded from: mainsysui33.jar:com/android/systemui/screenshot/ScrollCaptureController$BitmapScreenshot.class */
    public static class BitmapScreenshot extends LongScreenshot {
        public final Bitmap mBitmap;
        public final BitmapDrawable mBitmapDrawable;

        public BitmapScreenshot(Context context, Bitmap bitmap) {
            super(null, null);
            this.mBitmap = bitmap;
            this.mBitmapDrawable = new BitmapDrawable(context.getResources(), bitmap);
        }

        @Override // com.android.systemui.screenshot.ScrollCaptureController.LongScreenshot
        public int getBottom() {
            return getHeight();
        }

        @Override // com.android.systemui.screenshot.ScrollCaptureController.LongScreenshot
        public Drawable getDrawable() {
            return this.mBitmapDrawable;
        }

        @Override // com.android.systemui.screenshot.ScrollCaptureController.LongScreenshot
        public int getHeight() {
            return this.mBitmap.getHeight();
        }

        @Override // com.android.systemui.screenshot.ScrollCaptureController.LongScreenshot
        public int getLeft() {
            return 0;
        }

        @Override // com.android.systemui.screenshot.ScrollCaptureController.LongScreenshot
        public int getPageHeight() {
            return getHeight();
        }

        @Override // com.android.systemui.screenshot.ScrollCaptureController.LongScreenshot
        public int getTop() {
            return 0;
        }

        @Override // com.android.systemui.screenshot.ScrollCaptureController.LongScreenshot
        public int getWidth() {
            return this.mBitmap.getWidth();
        }

        @Override // com.android.systemui.screenshot.ScrollCaptureController.LongScreenshot
        public void release() {
        }

        @Override // com.android.systemui.screenshot.ScrollCaptureController.LongScreenshot
        public Bitmap toBitmap() {
            return this.mBitmap;
        }

        @Override // com.android.systemui.screenshot.ScrollCaptureController.LongScreenshot
        public String toString() {
            return "BitmapScrenshot{w=" + getWidth() + ", h=" + getHeight() + "}";
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/screenshot/ScrollCaptureController$LongScreenshot.class */
    public static class LongScreenshot {
        public final ImageTileSet mImageTileSet;
        public final ScrollCaptureClient.Session mSession;

        public LongScreenshot(ScrollCaptureClient.Session session, ImageTileSet imageTileSet) {
            this.mSession = session;
            this.mImageTileSet = imageTileSet;
        }

        public int getBottom() {
            return this.mImageTileSet.getBottom();
        }

        public Drawable getDrawable() {
            return this.mImageTileSet.getDrawable();
        }

        public int getHeight() {
            return this.mImageTileSet.getHeight();
        }

        public int getLeft() {
            return this.mImageTileSet.getLeft();
        }

        public int getPageHeight() {
            return this.mSession.getPageHeight();
        }

        public int getTop() {
            return this.mImageTileSet.getTop();
        }

        public int getWidth() {
            return this.mImageTileSet.getWidth();
        }

        public void release() {
            this.mImageTileSet.clear();
            this.mSession.release();
        }

        public Bitmap toBitmap() {
            return this.mImageTileSet.toBitmap();
        }

        public String toString() {
            return "LongScreenshot{w=" + this.mImageTileSet.getWidth() + ", h=" + this.mImageTileSet.getHeight() + "}";
        }
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.screenshot.ScrollCaptureController$$ExternalSyntheticLambda5.run():void] */
    public static /* synthetic */ void $r8$lambda$3jTmq7IJxdvD9bWqrBGDs_wdlAU(ScrollCaptureController scrollCaptureController) {
        scrollCaptureController.lambda$finishCapture$3();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.screenshot.ScrollCaptureController$$ExternalSyntheticLambda4.run():void] */
    /* renamed from: $r8$lambda$4sO_sN6742S0RDVb5SN-JLbf7OE */
    public static /* synthetic */ void m4326$r8$lambda$4sO_sN6742S0RDVb5SNJLbf7OE(ScrollCaptureController scrollCaptureController) {
        scrollCaptureController.lambda$requestNextTile$2();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.screenshot.ScrollCaptureController$$ExternalSyntheticLambda3.run():void] */
    public static /* synthetic */ void $r8$lambda$UsRbGCSwo6pGJQQ7tX1VhzKDeDY(ScrollCaptureController scrollCaptureController) {
        scrollCaptureController.onStartComplete();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.screenshot.ScrollCaptureController$$ExternalSyntheticLambda2.run():void] */
    public static /* synthetic */ void $r8$lambda$bI0cwJvE79VKK__iQ4niRLvqJd4(ScrollCaptureController scrollCaptureController, ScrollCaptureResponse scrollCaptureResponse) {
        scrollCaptureController.lambda$run$0(scrollCaptureResponse);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.screenshot.ScrollCaptureController$$ExternalSyntheticLambda1.run():void] */
    public static /* synthetic */ void $r8$lambda$ervptMwzg9jfo8vHNGLDzxN8hvk(ScrollCaptureController scrollCaptureController) {
        scrollCaptureController.onCancelled();
    }

    public ScrollCaptureController(Context context, Executor executor, ScrollCaptureClient scrollCaptureClient, ImageTileSet imageTileSet, UiEventLogger uiEventLogger) {
        this.mContext = context;
        this.mBgExecutor = executor;
        this.mClient = scrollCaptureClient;
        this.mImageTileSet = imageTileSet;
        this.mEventLogger = uiEventLogger;
    }

    public /* synthetic */ void lambda$finishCapture$3() {
        this.mCaptureCompleter.set(new LongScreenshot(this.mSession, this.mImageTileSet));
    }

    public /* synthetic */ void lambda$requestNextTile$2() {
        try {
            onCaptureResult((ScrollCaptureClient.CaptureResult) this.mTileFuture.get());
        } catch (InterruptedException | ExecutionException e) {
            Log.e(TAG, "requestTile failed!", e);
            this.mCaptureCompleter.setException(e);
        } catch (CancellationException e2) {
            Log.e(TAG, "requestTile cancelled");
        }
    }

    public /* synthetic */ void lambda$run$0(ScrollCaptureResponse scrollCaptureResponse) {
        ListenableFuture<ScrollCaptureClient.Session> start = this.mClient.start(scrollCaptureResponse, Settings.Secure.getFloat(this.mContext.getContentResolver(), "screenshot.scroll_max_pages", 3.0f));
        this.mSessionFuture = start;
        start.addListener(new Runnable() { // from class: com.android.systemui.screenshot.ScrollCaptureController$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                ScrollCaptureController.$r8$lambda$UsRbGCSwo6pGJQQ7tX1VhzKDeDY(ScrollCaptureController.this);
            }
        }, this.mContext.getMainExecutor());
    }

    public /* synthetic */ Object lambda$run$1(final ScrollCaptureResponse scrollCaptureResponse, CallbackToFutureAdapter.Completer completer) throws Exception {
        this.mCaptureCompleter = completer;
        this.mWindowOwner = scrollCaptureResponse.getPackageName();
        this.mCaptureCompleter.addCancellationListener(new Runnable() { // from class: com.android.systemui.screenshot.ScrollCaptureController$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                ScrollCaptureController.$r8$lambda$ervptMwzg9jfo8vHNGLDzxN8hvk(ScrollCaptureController.this);
            }
        }, this.mBgExecutor);
        this.mBgExecutor.execute(new Runnable() { // from class: com.android.systemui.screenshot.ScrollCaptureController$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                ScrollCaptureController.$r8$lambda$bI0cwJvE79VKK__iQ4niRLvqJd4(ScrollCaptureController.this, scrollCaptureResponse);
            }
        });
        return "<batch scroll capture>";
    }

    public final void finishCapture() {
        if (this.mImageTileSet.getHeight() > 0) {
            this.mEventLogger.log(ScreenshotEvent.SCREENSHOT_LONG_SCREENSHOT_COMPLETED, 0, this.mWindowOwner);
        } else {
            this.mEventLogger.log(ScreenshotEvent.SCREENSHOT_LONG_SCREENSHOT_FAILURE, 0, this.mWindowOwner);
        }
        ListenableFuture<Void> end = this.mSession.end();
        this.mEndFuture = end;
        end.addListener(new Runnable() { // from class: com.android.systemui.screenshot.ScrollCaptureController$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                ScrollCaptureController.$r8$lambda$3jTmq7IJxdvD9bWqrBGDs_wdlAU(ScrollCaptureController.this);
            }
        }, this.mContext.getMainExecutor());
    }

    @VisibleForTesting
    public float getTargetTopSizeRatio() {
        return 0.4f;
    }

    public final void onCancelled() {
        this.mCancelled = true;
        ListenableFuture<ScrollCaptureClient.Session> listenableFuture = this.mSessionFuture;
        if (listenableFuture != null) {
            listenableFuture.cancel(true);
        }
        ListenableFuture<ScrollCaptureClient.CaptureResult> listenableFuture2 = this.mTileFuture;
        if (listenableFuture2 != null) {
            listenableFuture2.cancel(true);
        }
        ScrollCaptureClient.Session session = this.mSession;
        if (session != null) {
            session.end();
        }
        this.mEventLogger.log(ScreenshotEvent.SCREENSHOT_LONG_SCREENSHOT_FAILURE, 0, this.mWindowOwner);
    }

    public final void onCaptureResult(ScrollCaptureClient.CaptureResult captureResult) {
        int bottom;
        int top;
        int tileHeight;
        boolean z = captureResult.captured.height() == 0;
        if (z) {
            if (this.mFinishOnBoundary) {
                finishCapture();
                return;
            }
            this.mImageTileSet.clear();
            this.mFinishOnBoundary = true;
            this.mScrollingUp = !this.mScrollingUp;
        } else if (this.mImageTileSet.size() + 1 >= this.mSession.getMaxTiles()) {
            finishCapture();
            return;
        } else if (this.mScrollingUp && !this.mFinishOnBoundary && this.mImageTileSet.getHeight() + captureResult.captured.height() >= this.mSession.getTargetHeight() * 0.4f) {
            this.mImageTileSet.clear();
            this.mScrollingUp = false;
        }
        if (!z) {
            this.mImageTileSet.lambda$addTile$0(new ImageTile(captureResult.image, captureResult.captured));
        }
        Rect gaps = this.mImageTileSet.getGaps();
        if (!gaps.isEmpty()) {
            requestNextTile(gaps.top);
        } else if (this.mImageTileSet.getHeight() >= this.mSession.getTargetHeight()) {
            finishCapture();
        } else if (z) {
            if (!this.mScrollingUp) {
                bottom = captureResult.requested.bottom;
                requestNextTile(bottom);
            }
            top = captureResult.requested.top;
            tileHeight = this.mSession.getTileHeight();
            bottom = top - tileHeight;
            requestNextTile(bottom);
        } else if (!this.mScrollingUp) {
            bottom = this.mImageTileSet.getBottom();
            requestNextTile(bottom);
        } else {
            top = this.mImageTileSet.getTop();
            tileHeight = this.mSession.getTileHeight();
            bottom = top - tileHeight;
            requestNextTile(bottom);
        }
    }

    public final void onStartComplete() {
        try {
            this.mSession = (ScrollCaptureClient.Session) this.mSessionFuture.get();
            this.mEventLogger.log(ScreenshotEvent.SCREENSHOT_LONG_SCREENSHOT_STARTED, 0, this.mWindowOwner);
            requestNextTile(0);
        } catch (InterruptedException | ExecutionException e) {
            Log.e(TAG, "session start failed!");
            this.mCaptureCompleter.setException(e);
            this.mEventLogger.log(ScreenshotEvent.SCREENSHOT_LONG_SCREENSHOT_FAILURE, 0, this.mWindowOwner);
        }
    }

    public final void requestNextTile(int i) {
        if (this.mCancelled) {
            Log.d(TAG, "requestNextTile: CANCELLED");
            return;
        }
        ListenableFuture<ScrollCaptureClient.CaptureResult> requestTile = this.mSession.requestTile(i);
        this.mTileFuture = requestTile;
        requestTile.addListener(new Runnable() { // from class: com.android.systemui.screenshot.ScrollCaptureController$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                ScrollCaptureController.m4326$r8$lambda$4sO_sN6742S0RDVb5SNJLbf7OE(ScrollCaptureController.this);
            }
        }, this.mBgExecutor);
    }

    public ListenableFuture<LongScreenshot> run(final ScrollCaptureResponse scrollCaptureResponse) {
        this.mCancelled = false;
        return CallbackToFutureAdapter.getFuture(new CallbackToFutureAdapter.Resolver() { // from class: com.android.systemui.screenshot.ScrollCaptureController$$ExternalSyntheticLambda0
            @Override // androidx.concurrent.futures.CallbackToFutureAdapter.Resolver
            public final Object attachCompleter(CallbackToFutureAdapter.Completer completer) {
                Object lambda$run$1;
                lambda$run$1 = ScrollCaptureController.this.lambda$run$1(scrollCaptureResponse, completer);
                return lambda$run$1;
            }
        });
    }
}