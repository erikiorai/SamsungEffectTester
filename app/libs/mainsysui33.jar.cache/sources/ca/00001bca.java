package com.android.systemui.media;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Looper;
import android.os.PowerManager;
import android.os.SystemClock;
import android.util.Log;
import com.android.internal.annotations.GuardedBy;
import java.lang.Thread;
import java.util.LinkedList;

/* loaded from: mainsysui33.jar:com/android/systemui/media/NotificationPlayer.class */
public class NotificationPlayer implements MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener {
    @GuardedBy({"mQueueAudioFocusLock"})
    public AudioManager mAudioManagerWithAudioFocus;
    @GuardedBy({"mCompletionHandlingLock"})
    public CreationAndCompletionThread mCompletionThread;
    @GuardedBy({"mCompletionHandlingLock"})
    public Looper mLooper;
    @GuardedBy({"mPlayerLock"})
    public MediaPlayer mPlayer;
    public String mTag;
    @GuardedBy({"mCmdQueue"})
    public CmdThread mThread;
    @GuardedBy({"mCmdQueue"})
    public PowerManager.WakeLock mWakeLock;
    public final LinkedList<Command> mCmdQueue = new LinkedList<>();
    public final Object mCompletionHandlingLock = new Object();
    public final Object mPlayerLock = new Object();
    public final Object mQueueAudioFocusLock = new Object();
    public int mNotificationRampTimeMs = 0;
    public int mState = 2;

    /* loaded from: mainsysui33.jar:com/android/systemui/media/NotificationPlayer$CmdThread.class */
    public final class CmdThread extends Thread {
        public CmdThread() {
            super("NotificationPlayer-" + NotificationPlayer.this.mTag);
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            Command command;
            MediaPlayer mediaPlayer;
            while (true) {
                synchronized (NotificationPlayer.this.mCmdQueue) {
                    command = (Command) NotificationPlayer.this.mCmdQueue.removeFirst();
                }
                int i = command.code;
                if (i == 1) {
                    NotificationPlayer.this.startSound(command);
                } else if (i == 2) {
                    synchronized (NotificationPlayer.this.mPlayerLock) {
                        mediaPlayer = NotificationPlayer.this.mPlayer;
                        NotificationPlayer.this.mPlayer = null;
                    }
                    if (mediaPlayer != null) {
                        long uptimeMillis = SystemClock.uptimeMillis() - command.requestTime;
                        if (uptimeMillis > 1000) {
                            String str = NotificationPlayer.this.mTag;
                            Log.w(str, "Notification stop delayed by " + uptimeMillis + "msecs");
                        }
                        try {
                            mediaPlayer.stop();
                        } catch (Exception e) {
                        }
                        mediaPlayer.release();
                        synchronized (NotificationPlayer.this.mQueueAudioFocusLock) {
                            if (NotificationPlayer.this.mAudioManagerWithAudioFocus != null) {
                                NotificationPlayer.this.mAudioManagerWithAudioFocus.abandonAudioFocus(null);
                                NotificationPlayer.this.mAudioManagerWithAudioFocus = null;
                            }
                        }
                        synchronized (NotificationPlayer.this.mCompletionHandlingLock) {
                            if (NotificationPlayer.this.mLooper != null && NotificationPlayer.this.mLooper.getThread().getState() != Thread.State.TERMINATED) {
                                NotificationPlayer.this.mLooper.quit();
                            }
                        }
                    } else {
                        Log.w(NotificationPlayer.this.mTag, "STOP command without a player");
                    }
                }
                synchronized (NotificationPlayer.this.mCmdQueue) {
                    if (NotificationPlayer.this.mCmdQueue.size() == 0) {
                        NotificationPlayer.this.mThread = null;
                        NotificationPlayer.this.releaseWakeLock();
                        return;
                    }
                }
            }
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/media/NotificationPlayer$Command.class */
    public static final class Command {
        public AudioAttributes attributes;
        public int code;
        public Context context;
        public boolean looping;
        public long requestTime;
        public Uri uri;

        public Command() {
        }

        public String toString() {
            return "{ code=" + this.code + " looping=" + this.looping + " attributes=" + this.attributes + " uri=" + this.uri + " }";
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/media/NotificationPlayer$CreationAndCompletionThread.class */
    public final class CreationAndCompletionThread extends Thread {
        public Command mCmd;

        public CreationAndCompletionThread(Command command) {
            this.mCmd = command;
        }

        /* JADX WARN: Removed duplicated region for block: B:60:0x0196  */
        @Override // java.lang.Thread, java.lang.Runnable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public void run() {
            MediaPlayer mediaPlayer;
            MediaPlayer mediaPlayer2;
            Looper.prepare();
            NotificationPlayer.this.mLooper = Looper.myLooper();
            synchronized (this) {
                AudioManager audioManager = (AudioManager) this.mCmd.context.getSystemService("audio");
                try {
                    mediaPlayer = new MediaPlayer();
                    try {
                        Command command = this.mCmd;
                        if (command.attributes == null) {
                            command.attributes = new AudioAttributes.Builder().setUsage(5).setContentType(4).build();
                        }
                        mediaPlayer.setAudioAttributes(this.mCmd.attributes);
                        Command command2 = this.mCmd;
                        mediaPlayer.setDataSource(command2.context, command2.uri);
                        mediaPlayer.setLooping(this.mCmd.looping);
                        mediaPlayer.setOnCompletionListener(NotificationPlayer.this);
                        mediaPlayer.setOnErrorListener(NotificationPlayer.this);
                        mediaPlayer.prepare();
                        Uri uri = this.mCmd.uri;
                        if (uri != null && uri.getEncodedPath() != null && this.mCmd.uri.getEncodedPath().length() > 0 && !audioManager.isMusicActiveRemotely()) {
                            synchronized (NotificationPlayer.this.mQueueAudioFocusLock) {
                                if (NotificationPlayer.this.mAudioManagerWithAudioFocus == null) {
                                    int i = 3;
                                    Command command3 = this.mCmd;
                                    if (command3.looping) {
                                        i = 1;
                                    }
                                    NotificationPlayer.this.mNotificationRampTimeMs = audioManager.getFocusRampTimeMs(i, command3.attributes);
                                    audioManager.requestAudioFocus(null, this.mCmd.attributes, i, 0);
                                    NotificationPlayer.this.mAudioManagerWithAudioFocus = audioManager;
                                }
                            }
                        }
                        try {
                            Thread.sleep(NotificationPlayer.this.mNotificationRampTimeMs);
                        } catch (InterruptedException e) {
                            Log.e(NotificationPlayer.this.mTag, "Exception while sleeping to sync notification playback with ducking", e);
                        }
                        mediaPlayer.start();
                    } catch (Exception e2) {
                        e = e2;
                        if (mediaPlayer != null) {
                            mediaPlayer.release();
                            mediaPlayer = null;
                        }
                        Log.w(NotificationPlayer.this.mTag, "error loading sound for " + this.mCmd.uri, e);
                        NotificationPlayer.this.abandonAudioFocusAfterError();
                        synchronized (NotificationPlayer.this.mPlayerLock) {
                        }
                    }
                } catch (Exception e3) {
                    e = e3;
                    mediaPlayer = null;
                }
                synchronized (NotificationPlayer.this.mPlayerLock) {
                    mediaPlayer2 = NotificationPlayer.this.mPlayer;
                    NotificationPlayer.this.mPlayer = mediaPlayer;
                }
                if (mediaPlayer2 != null) {
                    mediaPlayer2.pause();
                    try {
                        Thread.sleep(100L);
                    } catch (InterruptedException e4) {
                    }
                    mediaPlayer2.release();
                }
                notify();
            }
            Looper.loop();
        }
    }

    public NotificationPlayer(String str) {
        if (str != null) {
            this.mTag = str;
        } else {
            this.mTag = "NotificationPlayer";
        }
    }

    public final void abandonAudioFocusAfterError() {
        synchronized (this.mQueueAudioFocusLock) {
            AudioManager audioManager = this.mAudioManagerWithAudioFocus;
            if (audioManager != null) {
                audioManager.abandonAudioFocus(null);
                this.mAudioManagerWithAudioFocus = null;
            }
        }
    }

    @GuardedBy({"mCmdQueue"})
    public final void acquireWakeLock() {
        PowerManager.WakeLock wakeLock = this.mWakeLock;
        if (wakeLock != null) {
            wakeLock.acquire();
        }
    }

    @GuardedBy({"mCmdQueue"})
    public final void enqueueLocked(Command command) {
        this.mCmdQueue.add(command);
        if (this.mThread == null) {
            acquireWakeLock();
            CmdThread cmdThread = new CmdThread();
            this.mThread = cmdThread;
            cmdThread.start();
        }
    }

    @Override // android.media.MediaPlayer.OnCompletionListener
    public void onCompletion(MediaPlayer mediaPlayer) {
        synchronized (this.mQueueAudioFocusLock) {
            AudioManager audioManager = this.mAudioManagerWithAudioFocus;
            if (audioManager != null) {
                audioManager.abandonAudioFocus(null);
                this.mAudioManagerWithAudioFocus = null;
            }
        }
        synchronized (this.mCmdQueue) {
            synchronized (this.mCompletionHandlingLock) {
                if (this.mCmdQueue.size() == 0) {
                    Looper looper = this.mLooper;
                    if (looper != null) {
                        looper.quit();
                    }
                    this.mCompletionThread = null;
                }
            }
        }
        synchronized (this.mPlayerLock) {
            if (mediaPlayer == this.mPlayer) {
                this.mPlayer = null;
            }
        }
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
    }

    @Override // android.media.MediaPlayer.OnErrorListener
    public boolean onError(MediaPlayer mediaPlayer, int i, int i2) {
        String str = this.mTag;
        Log.e(str, "error " + i + " (extra=" + i2 + ") playing notification");
        onCompletion(mediaPlayer);
        return true;
    }

    public void play(Context context, Uri uri, boolean z, AudioAttributes audioAttributes) {
        Command command = new Command();
        command.requestTime = SystemClock.uptimeMillis();
        command.code = 1;
        command.context = context;
        command.uri = uri;
        command.looping = z;
        command.attributes = audioAttributes;
        synchronized (this.mCmdQueue) {
            enqueueLocked(command);
            this.mState = 1;
        }
    }

    @GuardedBy({"mCmdQueue"})
    public final void releaseWakeLock() {
        PowerManager.WakeLock wakeLock = this.mWakeLock;
        if (wakeLock != null) {
            wakeLock.release();
        }
    }

    public void setUsesWakeLock(Context context) {
        synchronized (this.mCmdQueue) {
            if (this.mWakeLock != null || this.mThread != null) {
                throw new RuntimeException("assertion failed mWakeLock=" + this.mWakeLock + " mThread=" + this.mThread);
            }
            this.mWakeLock = ((PowerManager) context.getSystemService("power")).newWakeLock(1, this.mTag);
        }
    }

    public final void startSound(Command command) {
        try {
            synchronized (this.mCompletionHandlingLock) {
                Looper looper = this.mLooper;
                if (looper != null && looper.getThread().getState() != Thread.State.TERMINATED) {
                    this.mLooper.quit();
                }
                CreationAndCompletionThread creationAndCompletionThread = new CreationAndCompletionThread(command);
                this.mCompletionThread = creationAndCompletionThread;
                synchronized (creationAndCompletionThread) {
                    this.mCompletionThread.start();
                    this.mCompletionThread.wait();
                }
            }
            long uptimeMillis = SystemClock.uptimeMillis() - command.requestTime;
            if (uptimeMillis > 1000) {
                String str = this.mTag;
                Log.w(str, "Notification sound delayed by " + uptimeMillis + "msecs");
            }
        } catch (Exception e) {
            String str2 = this.mTag;
            Log.w(str2, "error loading sound for " + command.uri, e);
        }
    }

    public void stop() {
        synchronized (this.mCmdQueue) {
            if (this.mState != 2) {
                Command command = new Command();
                command.requestTime = SystemClock.uptimeMillis();
                command.code = 2;
                enqueueLocked(command);
                this.mState = 2;
            }
        }
    }
}