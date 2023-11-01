package com.android.systemui.screenrecord;

import android.media.AudioFormat;
import android.media.AudioPlaybackCaptureConfiguration;
import android.media.AudioRecord;
import android.media.MediaCodec;
import android.media.MediaCrypto;
import android.media.MediaFormat;
import android.media.MediaMuxer;
import android.media.projection.MediaProjection;
import android.util.Log;
import android.util.MathUtils;
import android.view.Surface;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;

/* loaded from: mainsysui33.jar:com/android/systemui/screenrecord/ScreenInternalAudioRecorder.class */
public class ScreenInternalAudioRecorder {
    public static String TAG = "ScreenAudioRecorder";
    public AudioRecord mAudioRecord;
    public AudioRecord mAudioRecordMic;
    public MediaCodec mCodec;
    public MediaProjection mMediaProjection;
    public boolean mMic;
    public MediaMuxer mMuxer;
    public long mPresentationTime;
    public boolean mStarted;
    public Thread mThread;
    public long mTotalBytes;
    public Config mConfig = new Config();
    public int mTrackId = -1;

    /* loaded from: mainsysui33.jar:com/android/systemui/screenrecord/ScreenInternalAudioRecorder$Config.class */
    public static class Config {
        public int channelOutMask = 4;
        public int channelInMask = 16;
        public int encoding = 2;
        public int sampleRate = 44100;
        public int bitRate = 196000;
        public int bufferSizeBytes = 131072;
        public boolean privileged = true;
        public boolean legacy_app_looback = false;

        public String toString() {
            return "channelMask=" + this.channelOutMask + "\n   encoding=" + this.encoding + "\n sampleRate=" + this.sampleRate + "\n bufferSize=" + this.bufferSizeBytes + "\n privileged=" + this.privileged + "\n legacy app looback=" + this.legacy_app_looback;
        }
    }

    public ScreenInternalAudioRecorder(String str, MediaProjection mediaProjection, boolean z) throws IOException {
        this.mMic = z;
        this.mMuxer = new MediaMuxer(str, 0);
        this.mMediaProjection = mediaProjection;
        String str2 = TAG;
        Log.d(str2, "creating audio file " + str);
        setupSimple();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setupSimple$0(int i) {
        short[] sArr;
        int read;
        byte[] bArr = new byte[i];
        short[] sArr2 = null;
        if (this.mMic) {
            int i2 = i / 2;
            sArr2 = new short[i2];
            sArr = new short[i2];
        } else {
            sArr = null;
        }
        int i3 = 0;
        int i4 = 0;
        int i5 = 0;
        int i6 = 0;
        while (true) {
            if (this.mMic) {
                int read2 = this.mAudioRecord.read(sArr2, i6, sArr2.length - i6);
                int read3 = this.mAudioRecordMic.read(sArr, i3, sArr.length - i3);
                if (read2 < 0 && read3 < 0) {
                    break;
                }
                int i7 = read2;
                if (read2 < 0) {
                    Arrays.fill(sArr2, (short) 0);
                    i6 = i3;
                    i7 = read3;
                }
                int i8 = read3;
                if (read3 < 0) {
                    Arrays.fill(sArr, (short) 0);
                    i3 = i6;
                    i8 = i7;
                }
                i5 = i7 + i6;
                i4 = i8 + i3;
                int min = Math.min(i5, i4);
                read = min * 2;
                scaleValues(sArr, min, 1.4f);
                addAndConvertBuffers(sArr2, sArr, bArr, min);
                shiftToStart(sArr2, min, i6);
                shiftToStart(sArr, min, i3);
                i6 = i5 - min;
                i3 = i4 - min;
            } else {
                read = this.mAudioRecord.read(bArr, 0, i);
            }
            if (read < 0) {
                Log.e(TAG, "read error " + read + ", shorts internal: " + i5 + ", shorts mic: " + i4);
                break;
            }
            encode(bArr, read);
        }
        endStream();
    }

    public final void addAndConvertBuffers(short[] sArr, short[] sArr2, byte[] bArr, int i) {
        for (int i2 = 0; i2 < i; i2++) {
            short constrain = (short) MathUtils.constrain(sArr[i2] + sArr2[i2], -32768, 32767);
            int i3 = i2 * 2;
            bArr[i3] = (byte) (constrain & 255);
            bArr[i3 + 1] = (byte) ((constrain >> 8) & 255);
        }
    }

    public final void encode(byte[] bArr, int i) {
        int i2 = 0;
        while (i > 0) {
            int dequeueInputBuffer = this.mCodec.dequeueInputBuffer(500L);
            if (dequeueInputBuffer < 0) {
                writeOutput();
                return;
            }
            ByteBuffer inputBuffer = this.mCodec.getInputBuffer(dequeueInputBuffer);
            inputBuffer.clear();
            int capacity = inputBuffer.capacity();
            if (i <= capacity) {
                capacity = i;
            }
            i -= capacity;
            inputBuffer.put(bArr, i2, capacity);
            i2 += capacity;
            this.mCodec.queueInputBuffer(dequeueInputBuffer, 0, capacity, this.mPresentationTime, 0);
            long j = this.mTotalBytes + capacity + 0;
            this.mTotalBytes = j;
            this.mPresentationTime = ((j / 2) * 1000000) / this.mConfig.sampleRate;
            writeOutput();
        }
    }

    public void end() {
        this.mAudioRecord.stop();
        if (this.mMic) {
            this.mAudioRecordMic.stop();
        }
        this.mAudioRecord.release();
        if (this.mMic) {
            this.mAudioRecordMic.release();
        }
        try {
            this.mThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.mCodec.stop();
        this.mCodec.release();
        this.mMuxer.stop();
        this.mMuxer.release();
        this.mThread = null;
    }

    public final void endStream() {
        this.mCodec.queueInputBuffer(this.mCodec.dequeueInputBuffer(500L), 0, 0, this.mPresentationTime, 4);
        writeOutput();
    }

    public final void scaleValues(short[] sArr, int i, float f) {
        for (int i2 = 0; i2 < i; i2++) {
            sArr[i2] = (short) MathUtils.constrain((int) (sArr[i2] * f), -32768, 32767);
        }
    }

    public final void setupSimple() throws IOException {
        Config config = this.mConfig;
        final int minBufferSize = AudioRecord.getMinBufferSize(config.sampleRate, config.channelInMask, config.encoding) * 2;
        String str = TAG;
        Log.d(str, "audio buffer size: " + minBufferSize);
        AudioFormat build = new AudioFormat.Builder().setEncoding(this.mConfig.encoding).setSampleRate(this.mConfig.sampleRate).setChannelMask(this.mConfig.channelOutMask).build();
        this.mAudioRecord = new AudioRecord.Builder().setAudioFormat(build).setAudioPlaybackCaptureConfig(new AudioPlaybackCaptureConfiguration.Builder(this.mMediaProjection).addMatchingUsage(1).addMatchingUsage(0).addMatchingUsage(14).build()).build();
        if (this.mMic) {
            Config config2 = this.mConfig;
            this.mAudioRecordMic = new AudioRecord(7, config2.sampleRate, 16, config2.encoding, minBufferSize);
        }
        this.mCodec = MediaCodec.createEncoderByType("audio/mp4a-latm");
        MediaFormat createAudioFormat = MediaFormat.createAudioFormat("audio/mp4a-latm", this.mConfig.sampleRate, 1);
        createAudioFormat.setInteger("aac-profile", 2);
        createAudioFormat.setInteger("bitrate", this.mConfig.bitRate);
        createAudioFormat.setInteger("pcm-encoding", this.mConfig.encoding);
        this.mCodec.configure(createAudioFormat, (Surface) null, (MediaCrypto) null, 1);
        this.mThread = new Thread(new Runnable() { // from class: com.android.systemui.screenrecord.ScreenInternalAudioRecorder$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                ScreenInternalAudioRecorder.this.lambda$setupSimple$0(minBufferSize);
            }
        });
    }

    public final void shiftToStart(short[] sArr, int i, int i2) {
        for (int i3 = 0; i3 < i2 - i; i3++) {
            sArr[i3] = sArr[i + i3];
        }
    }

    public void start() throws IllegalStateException {
        synchronized (this) {
            if (this.mStarted) {
                if (this.mThread != null) {
                    throw new IllegalStateException("Recording already started");
                }
                throw new IllegalStateException("Recording stopped and can't restart (single use)");
            }
            this.mStarted = true;
            this.mAudioRecord.startRecording();
            if (this.mMic) {
                this.mAudioRecordMic.startRecording();
            }
            String str = TAG;
            Log.d(str, "channel count " + this.mAudioRecord.getChannelCount());
            this.mCodec.start();
            if (this.mAudioRecord.getRecordingState() != 3) {
                throw new IllegalStateException("Audio recording failed to start");
            }
            this.mThread.start();
        }
    }

    public final void writeOutput() {
        while (true) {
            MediaCodec.BufferInfo bufferInfo = new MediaCodec.BufferInfo();
            int dequeueOutputBuffer = this.mCodec.dequeueOutputBuffer(bufferInfo, 500L);
            if (dequeueOutputBuffer == -2) {
                this.mTrackId = this.mMuxer.addTrack(this.mCodec.getOutputFormat());
                this.mMuxer.start();
            } else if (dequeueOutputBuffer == -1 || this.mTrackId < 0) {
                return;
            } else {
                ByteBuffer outputBuffer = this.mCodec.getOutputBuffer(dequeueOutputBuffer);
                if ((bufferInfo.flags & 2) == 0 || bufferInfo.size == 0) {
                    this.mMuxer.writeSampleData(this.mTrackId, outputBuffer, bufferInfo);
                }
                this.mCodec.releaseOutputBuffer(dequeueOutputBuffer, false);
            }
        }
    }
}