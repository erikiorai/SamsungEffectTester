package com.android.systemui.screenrecord;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.hardware.display.VirtualDisplay;
import android.media.MediaCodec;
import android.media.MediaCodecInfo;
import android.media.MediaRecorder;
import android.media.ThumbnailUtils;
import android.media.projection.IMediaProjection;
import android.media.projection.IMediaProjectionManager;
import android.media.projection.MediaProjection;
import android.net.Uri;
import android.os.Handler;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Size;
import android.view.Surface;
import android.view.WindowManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.systemui.R$integer;
import com.android.systemui.media.MediaProjectionCaptureTarget;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/* loaded from: mainsysui33.jar:com/android/systemui/screenrecord/ScreenMediaRecorder.class */
public class ScreenMediaRecorder extends MediaProjection.Callback {
    public ScreenInternalAudioRecorder mAudio;
    public ScreenRecordingAudioSource mAudioSource;
    public final MediaProjectionCaptureTarget mCaptureRegion;
    public Context mContext;
    public final Handler mHandler;
    public Surface mInputSurface;
    public ScreenMediaRecorderListener mListener;
    public int mMaxRefreshRate;
    public MediaProjection mMediaProjection;
    public MediaRecorder mMediaRecorder;
    public ScreenRecordingMuxer mMuxer;
    public File mTempAudioFile;
    public File mTempVideoFile;
    public int mUser;
    public VirtualDisplay mVirtualDisplay;

    /* loaded from: mainsysui33.jar:com/android/systemui/screenrecord/ScreenMediaRecorder$Closer.class */
    public static class Closer implements Closeable {
        public final List<Closeable> mCloseables;

        public Closer() {
            this.mCloseables = new ArrayList();
        }

        @Override // java.io.Closeable, java.lang.AutoCloseable
        public void close() throws IOException {
            IOException iOException = null;
            for (int i = 0; i < this.mCloseables.size(); i++) {
                try {
                    this.mCloseables.get(i).close();
                } catch (Throwable th) {
                    if (iOException == null) {
                        iOException = th;
                    } else {
                        th.printStackTrace();
                    }
                }
            }
            if (iOException != null) {
                if (iOException instanceof IOException) {
                    throw iOException;
                }
                if (!(iOException instanceof RuntimeException)) {
                    throw iOException;
                }
                throw iOException;
            }
        }

        public void register(Closeable closeable) {
            this.mCloseables.add(closeable);
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/screenrecord/ScreenMediaRecorder$SavedRecording.class */
    public class SavedRecording {
        public Bitmap mThumbnailBitmap;
        public Uri mUri;

        public SavedRecording(Uri uri, File file, Size size) {
            ScreenMediaRecorder.this = r6;
            this.mUri = uri;
            try {
                this.mThumbnailBitmap = ThumbnailUtils.createVideoThumbnail(file, size, null);
            } catch (IOException e) {
                Log.e("ScreenMediaRecorder", "Error creating thumbnail", e);
            }
        }

        public Bitmap getThumbnail() {
            return this.mThumbnailBitmap;
        }

        public Uri getUri() {
            return this.mUri;
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/screenrecord/ScreenMediaRecorder$ScreenMediaRecorderListener.class */
    public interface ScreenMediaRecorderListener {
        void onInfo(MediaRecorder mediaRecorder, int i, int i2);

        void onStopped();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.screenrecord.ScreenMediaRecorder$$ExternalSyntheticLambda5.close():void] */
    /* renamed from: $r8$lambda$J-qXMXgE_6SMtEBXQ98mB96xdQg */
    public static /* synthetic */ void m4181$r8$lambda$JqXMXgE_6SMtEBXQ98mB96xdQg(ScreenMediaRecorder screenMediaRecorder) {
        screenMediaRecorder.stopInternalAudioRecording();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.screenrecord.ScreenMediaRecorder$$ExternalSyntheticLambda6.onInfo(android.media.MediaRecorder, int, int):void] */
    public static /* synthetic */ void $r8$lambda$wiaRRPFfmaVvSWpFuM7EJdM22Vc(ScreenMediaRecorder screenMediaRecorder, MediaRecorder mediaRecorder, int i, int i2) {
        screenMediaRecorder.lambda$prepare$0(mediaRecorder, i, i2);
    }

    public ScreenMediaRecorder(Context context, Handler handler, int i, ScreenRecordingAudioSource screenRecordingAudioSource, MediaProjectionCaptureTarget mediaProjectionCaptureTarget, ScreenMediaRecorderListener screenMediaRecorderListener) {
        this.mContext = context;
        this.mHandler = handler;
        this.mUser = i;
        this.mCaptureRegion = mediaProjectionCaptureTarget;
        this.mListener = screenMediaRecorderListener;
        this.mAudioSource = screenRecordingAudioSource;
        this.mMaxRefreshRate = context.getResources().getInteger(R$integer.config_screenRecorderMaxFramerate);
    }

    public /* synthetic */ void lambda$prepare$0(MediaRecorder mediaRecorder, int i, int i2) {
        this.mListener.onInfo(mediaRecorder, i, i2);
    }

    public void end() throws IOException {
        Closer closer = new Closer();
        final MediaRecorder mediaRecorder = this.mMediaRecorder;
        Objects.requireNonNull(mediaRecorder);
        closer.register(new Closeable() { // from class: com.android.systemui.screenrecord.ScreenMediaRecorder$$ExternalSyntheticLambda0
            @Override // java.io.Closeable, java.lang.AutoCloseable
            public final void close() {
                mediaRecorder.stop();
            }
        });
        final MediaRecorder mediaRecorder2 = this.mMediaRecorder;
        Objects.requireNonNull(mediaRecorder2);
        closer.register(new Closeable() { // from class: com.android.systemui.screenrecord.ScreenMediaRecorder$$ExternalSyntheticLambda1
            @Override // java.io.Closeable, java.lang.AutoCloseable
            public final void close() {
                mediaRecorder2.release();
            }
        });
        final Surface surface = this.mInputSurface;
        Objects.requireNonNull(surface);
        closer.register(new Closeable() { // from class: com.android.systemui.screenrecord.ScreenMediaRecorder$$ExternalSyntheticLambda2
            @Override // java.io.Closeable, java.lang.AutoCloseable
            public final void close() {
                surface.release();
            }
        });
        final VirtualDisplay virtualDisplay = this.mVirtualDisplay;
        Objects.requireNonNull(virtualDisplay);
        closer.register(new Closeable() { // from class: com.android.systemui.screenrecord.ScreenMediaRecorder$$ExternalSyntheticLambda3
            @Override // java.io.Closeable, java.lang.AutoCloseable
            public final void close() {
                virtualDisplay.release();
            }
        });
        final MediaProjection mediaProjection = this.mMediaProjection;
        Objects.requireNonNull(mediaProjection);
        closer.register(new Closeable() { // from class: com.android.systemui.screenrecord.ScreenMediaRecorder$$ExternalSyntheticLambda4
            @Override // java.io.Closeable, java.lang.AutoCloseable
            public final void close() {
                mediaProjection.stop();
            }
        });
        closer.register(new Closeable() { // from class: com.android.systemui.screenrecord.ScreenMediaRecorder$$ExternalSyntheticLambda5
            @Override // java.io.Closeable, java.lang.AutoCloseable
            public final void close() {
                ScreenMediaRecorder.m4181$r8$lambda$JqXMXgE_6SMtEBXQ98mB96xdQg(ScreenMediaRecorder.this);
            }
        });
        closer.close();
        this.mMediaRecorder = null;
        this.mMediaProjection = null;
        Log.d("ScreenMediaRecorder", "end recording");
    }

    public final int[] getSupportedSize(int i, int i2, int i3) throws IOException {
        MediaCodec createDecoderByType = MediaCodec.createDecoderByType("video/avc");
        MediaCodecInfo.VideoCapabilities videoCapabilities = createDecoderByType.getCodecInfo().getCapabilitiesForType("video/avc").getVideoCapabilities();
        createDecoderByType.release();
        int intValue = videoCapabilities.getSupportedWidths().getUpper().intValue();
        int intValue2 = videoCapabilities.getSupportedHeights().getUpper().intValue();
        int widthAlignment = i % videoCapabilities.getWidthAlignment() != 0 ? i - (i % videoCapabilities.getWidthAlignment()) : i;
        int heightAlignment = i2 % videoCapabilities.getHeightAlignment() != 0 ? i2 - (i2 % videoCapabilities.getHeightAlignment()) : i2;
        if (intValue >= widthAlignment && intValue2 >= heightAlignment && videoCapabilities.isSizeSupported(widthAlignment, heightAlignment)) {
            int intValue3 = videoCapabilities.getSupportedFrameRatesFor(widthAlignment, heightAlignment).getUpper().intValue();
            int i4 = i3;
            if (intValue3 < i3) {
                i4 = intValue3;
            }
            Log.d("ScreenMediaRecorder", "Screen size supported at rate " + i4);
            return new int[]{widthAlignment, heightAlignment, i4};
        }
        double d = intValue;
        double d2 = i;
        double d3 = d / d2;
        double d4 = intValue2;
        double d5 = i2;
        double min = Math.min(d3, d4 / d5);
        int i5 = (int) (d2 * min);
        int i6 = (int) (d5 * min);
        int i7 = i5;
        if (i5 % videoCapabilities.getWidthAlignment() != 0) {
            i7 = i5 - (i5 % videoCapabilities.getWidthAlignment());
        }
        int i8 = i6;
        if (i6 % videoCapabilities.getHeightAlignment() != 0) {
            i8 = i6 - (i6 % videoCapabilities.getHeightAlignment());
        }
        int intValue4 = videoCapabilities.getSupportedFrameRatesFor(i7, i8).getUpper().intValue();
        int i9 = i3;
        if (intValue4 < i3) {
            i9 = intValue4;
        }
        Log.d("ScreenMediaRecorder", "Resized by " + min + ": " + i7 + ", " + i8 + ", " + i9);
        return new int[]{i7, i8, i9};
    }

    @Override // android.media.projection.MediaProjection.Callback
    public void onStop() {
        Log.d("ScreenMediaRecorder", "The system notified about stopping the projection");
        this.mListener.onStopped();
    }

    public final void prepare() throws IOException, RemoteException, RuntimeException {
        boolean z = false;
        IMediaProjection asInterface = IMediaProjection.Stub.asInterface(IMediaProjectionManager.Stub.asInterface(ServiceManager.getService("media_projection")).createProjection(this.mUser, this.mContext.getPackageName(), 0, false).asBinder());
        MediaProjectionCaptureTarget mediaProjectionCaptureTarget = this.mCaptureRegion;
        if (mediaProjectionCaptureTarget != null) {
            asInterface.setLaunchCookie(mediaProjectionCaptureTarget.getLaunchCookie());
        }
        MediaProjection mediaProjection = new MediaProjection(this.mContext, asInterface);
        this.mMediaProjection = mediaProjection;
        mediaProjection.registerCallback(this, this.mHandler);
        File cacheDir = this.mContext.getCacheDir();
        cacheDir.mkdirs();
        this.mTempVideoFile = File.createTempFile("temp", ".mp4", cacheDir);
        MediaRecorder mediaRecorder = new MediaRecorder();
        this.mMediaRecorder = mediaRecorder;
        ScreenRecordingAudioSource screenRecordingAudioSource = this.mAudioSource;
        ScreenRecordingAudioSource screenRecordingAudioSource2 = ScreenRecordingAudioSource.MIC;
        if (screenRecordingAudioSource == screenRecordingAudioSource2) {
            mediaRecorder.setAudioSource(0);
        }
        this.mMediaRecorder.setVideoSource(2);
        this.mMediaRecorder.setOutputFormat(2);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) this.mContext.getSystemService("window");
        windowManager.getDefaultDisplay().getRealMetrics(displayMetrics);
        int refreshRate = (int) windowManager.getDefaultDisplay().getRefreshRate();
        int i = this.mMaxRefreshRate;
        int i2 = refreshRate;
        if (i != 0) {
            i2 = refreshRate;
            if (refreshRate > i) {
                i2 = i;
            }
        }
        int[] supportedSize = getSupportedSize(displayMetrics.widthPixels, displayMetrics.heightPixels, i2);
        int i3 = supportedSize[0];
        int i4 = supportedSize[1];
        int i5 = supportedSize[2];
        this.mMediaRecorder.setVideoEncoder(2);
        this.mMediaRecorder.setVideoEncodingProfileLevel(2, RecyclerView.ViewHolder.FLAG_TMP_DETACHED);
        this.mMediaRecorder.setVideoSize(i3, i4);
        this.mMediaRecorder.setVideoFrameRate(i5);
        this.mMediaRecorder.setVideoEncodingBitRate((((i3 * i4) * i5) / 30) * 6);
        this.mMediaRecorder.setMaxDuration(3600000);
        this.mMediaRecorder.setMaxFileSize(5000000000L);
        if (this.mAudioSource == screenRecordingAudioSource2) {
            this.mMediaRecorder.setAudioEncoder(4);
            this.mMediaRecorder.setAudioChannels(1);
            this.mMediaRecorder.setAudioEncodingBitRate(196000);
            this.mMediaRecorder.setAudioSamplingRate(44100);
        }
        this.mMediaRecorder.setOutputFile(this.mTempVideoFile);
        this.mMediaRecorder.prepare();
        Surface surface = this.mMediaRecorder.getSurface();
        this.mInputSurface = surface;
        this.mVirtualDisplay = this.mMediaProjection.createVirtualDisplay("Recording Display", i3, i4, displayMetrics.densityDpi, 16, surface, new VirtualDisplay.Callback() { // from class: com.android.systemui.screenrecord.ScreenMediaRecorder.1
            {
                ScreenMediaRecorder.this = this;
            }

            @Override // android.hardware.display.VirtualDisplay.Callback
            public void onStopped() {
                ScreenMediaRecorder.this.onStop();
            }
        }, this.mHandler);
        this.mMediaRecorder.setOnInfoListener(new MediaRecorder.OnInfoListener() { // from class: com.android.systemui.screenrecord.ScreenMediaRecorder$$ExternalSyntheticLambda6
            @Override // android.media.MediaRecorder.OnInfoListener
            public final void onInfo(MediaRecorder mediaRecorder2, int i6, int i7) {
                ScreenMediaRecorder.$r8$lambda$wiaRRPFfmaVvSWpFuM7EJdM22Vc(ScreenMediaRecorder.this, mediaRecorder2, i6, i7);
            }
        });
        ScreenRecordingAudioSource screenRecordingAudioSource3 = this.mAudioSource;
        if (screenRecordingAudioSource3 == ScreenRecordingAudioSource.INTERNAL || screenRecordingAudioSource3 == ScreenRecordingAudioSource.MIC_AND_INTERNAL) {
            this.mTempAudioFile = File.createTempFile("temp", ".aac", this.mContext.getCacheDir());
            String absolutePath = this.mTempAudioFile.getAbsolutePath();
            MediaProjection mediaProjection2 = this.mMediaProjection;
            if (this.mAudioSource == ScreenRecordingAudioSource.MIC_AND_INTERNAL) {
                z = true;
            }
            this.mAudio = new ScreenInternalAudioRecorder(absolutePath, mediaProjection2, z);
        }
    }

    public final void recordInternalAudio() throws IllegalStateException {
        ScreenRecordingAudioSource screenRecordingAudioSource = this.mAudioSource;
        if (screenRecordingAudioSource == ScreenRecordingAudioSource.INTERNAL || screenRecordingAudioSource == ScreenRecordingAudioSource.MIC_AND_INTERNAL) {
            this.mAudio.start();
        }
    }

    public void release() {
        File file = this.mTempVideoFile;
        if (file != null) {
            file.delete();
        }
        File file2 = this.mTempAudioFile;
        if (file2 != null) {
            file2.delete();
        }
    }

    public SavedRecording save() throws IOException {
        String format = new SimpleDateFormat("'screen-'yyyyMMdd-HHmmss'.mp4'").format(new Date());
        ContentValues contentValues = new ContentValues();
        contentValues.put("_display_name", format);
        contentValues.put("mime_type", "video/mp4");
        contentValues.put("date_added", Long.valueOf(System.currentTimeMillis()));
        contentValues.put("datetaken", Long.valueOf(System.currentTimeMillis()));
        ContentResolver contentResolver = this.mContext.getContentResolver();
        Uri insert = contentResolver.insert(MediaStore.Video.Media.getContentUri("external_primary"), contentValues);
        Log.d("ScreenMediaRecorder", insert.toString());
        ScreenRecordingAudioSource screenRecordingAudioSource = this.mAudioSource;
        if (screenRecordingAudioSource == ScreenRecordingAudioSource.MIC_AND_INTERNAL || screenRecordingAudioSource == ScreenRecordingAudioSource.INTERNAL) {
            try {
                Log.d("ScreenMediaRecorder", "muxing recording");
                File createTempFile = File.createTempFile("temp", ".mp4", this.mContext.getCacheDir());
                ScreenRecordingMuxer screenRecordingMuxer = new ScreenRecordingMuxer(0, createTempFile.getAbsolutePath(), this.mTempVideoFile.getAbsolutePath(), this.mTempAudioFile.getAbsolutePath());
                this.mMuxer = screenRecordingMuxer;
                screenRecordingMuxer.mux();
                this.mTempVideoFile.delete();
                this.mTempVideoFile = createTempFile;
            } catch (IOException e) {
                Log.e("ScreenMediaRecorder", "muxing recording " + e.getMessage());
                e.printStackTrace();
            }
        }
        OutputStream openOutputStream = contentResolver.openOutputStream(insert, "w");
        Files.copy(this.mTempVideoFile.toPath(), openOutputStream);
        openOutputStream.close();
        File file = this.mTempAudioFile;
        if (file != null) {
            file.delete();
        }
        DisplayMetrics displayMetrics = this.mContext.getResources().getDisplayMetrics();
        SavedRecording savedRecording = new SavedRecording(insert, this.mTempVideoFile, new Size(displayMetrics.widthPixels, displayMetrics.heightPixels));
        this.mTempVideoFile.delete();
        return savedRecording;
    }

    public void start() throws IOException, RemoteException, RuntimeException {
        Log.d("ScreenMediaRecorder", "start recording");
        prepare();
        this.mMediaRecorder.start();
        recordInternalAudio();
    }

    public final void stopInternalAudioRecording() {
        ScreenRecordingAudioSource screenRecordingAudioSource = this.mAudioSource;
        if (screenRecordingAudioSource == ScreenRecordingAudioSource.INTERNAL || screenRecordingAudioSource == ScreenRecordingAudioSource.MIC_AND_INTERNAL) {
            this.mAudio.end();
            this.mAudio = null;
        }
    }
}