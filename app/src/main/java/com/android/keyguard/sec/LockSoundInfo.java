package com.android.keyguard.sec;

import android.content.Context;
import android.media.SoundPool;
import android.util.Log;

/* loaded from: classes.dex */
public class LockSoundInfo {
    private static String TAG = "LockSoundInfo";
    private static final int TYPE_PATH = 1;
    private static final int TYPE_RESOURCE = 2;
    String mPathLock;
    String mPathUnlock;
    int mResIdLock;
    int mResIdUnlock;
    int mTypeLock;
    int mTypeUnlock;

    public LockSoundInfo(int resIdLock, int resIdUnlock) {
        this.mTypeLock = -1;
        this.mTypeUnlock = -1;
        this.mPathLock = null;
        this.mPathUnlock = null;
        this.mResIdLock = 0;
        this.mResIdUnlock = 0;
        this.mTypeLock = 2;
        this.mTypeUnlock = 2;
        this.mResIdLock = resIdLock;
        this.mResIdUnlock = resIdUnlock;
    }

    public LockSoundInfo(int resIdLock, String pathUnlock) {
        this.mTypeLock = -1;
        this.mTypeUnlock = -1;
        this.mPathLock = null;
        this.mPathUnlock = null;
        this.mResIdLock = 0;
        this.mResIdUnlock = 0;
        this.mTypeLock = 2;
        this.mTypeUnlock = 1;
        this.mResIdLock = resIdLock;
        this.mPathUnlock = pathUnlock;
    }

    public LockSoundInfo(String pathLock, int resIdUnlock) {
        this.mTypeLock = -1;
        this.mTypeUnlock = -1;
        this.mPathLock = null;
        this.mPathUnlock = null;
        this.mResIdLock = 0;
        this.mResIdUnlock = 0;
        this.mTypeLock = 1;
        this.mTypeUnlock = 2;
        this.mPathLock = pathLock;
        this.mResIdUnlock = resIdUnlock;
    }

    public LockSoundInfo(String pathLock, String pathUnlock) {
        this.mTypeLock = -1;
        this.mTypeUnlock = -1;
        this.mPathLock = null;
        this.mPathUnlock = null;
        this.mResIdLock = 0;
        this.mResIdUnlock = 0;
        this.mTypeLock = 1;
        this.mTypeUnlock = 1;
        this.mPathLock = pathLock;
        this.mPathUnlock = pathUnlock;
    }

    private boolean isLockSoundTypePath() {
        return this.mTypeLock == 1;
    }

    private boolean isUnlockSoundTypePath() {
        return this.mTypeUnlock == 1;
    }

    private int getResIdLock() {
        return this.mResIdLock;
    }

    private int getResIdUnlock() {
        return this.mResIdUnlock;
    }

    private String getPathLock() {
        return this.mPathLock;
    }

    private String getPathUnlock() {
        return this.mPathUnlock;
    }

    public int reloadSoundIdLock(Context context, int currentId, SoundPool soundPool) {
        int retSoundId = 0;
        if (isLockSoundTypePath()) {
            String path = getPathLock();
            if (path != null) {
                soundPool.unload(currentId);
                retSoundId = soundPool.load(path, 1);
            }
            if (path == null || retSoundId == 0) {
                Log.w(TAG, "failed to load lock sound from " + path);
                if (path == null) {
                    return currentId;
                }
                return retSoundId;
            }
            return retSoundId;
        }
        int resId = getResIdLock();
        if (resId != 0) {
            soundPool.unload(currentId);
            retSoundId = soundPool.load(context, resId, 1);
        }
        if (resId == 0 || retSoundId == 0) {
            Log.w(TAG, "failed to load lock sound from resource " + resId);
            if (resId == 0) {
                return currentId;
            }
            return retSoundId;
        }
        return retSoundId;
    }

    public int reloadSoundIdUnlock(Context context, int currentId, SoundPool soundPool) {
        int retSoundId = 0;
        if (isUnlockSoundTypePath()) {
            String path = getPathUnlock();
            if (path != null) {
                soundPool.unload(currentId);
                retSoundId = soundPool.load(path, 1);
            }
            if (path == null || retSoundId == 0) {
                Log.w(TAG, "failed to load unlock sound from " + path);
                if (path == null) {
                    return currentId;
                }
                return retSoundId;
            }
            return retSoundId;
        }
        int resId = getResIdUnlock();
        if (resId != 0) {
            soundPool.unload(currentId);
            retSoundId = soundPool.load(context, resId, 1);
        }
        if (resId == 0 || retSoundId == 0) {
            Log.w(TAG, "failed to load unlock sound from resource " + resId);
            if (resId == 0) {
                return currentId;
            }
            return retSoundId;
        }
        return retSoundId;
    }
}