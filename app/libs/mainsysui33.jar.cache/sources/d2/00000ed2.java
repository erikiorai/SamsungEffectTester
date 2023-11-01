package com.android.settingslib.volume;

import android.media.MediaMetadata;
import android.media.session.MediaController;
import android.media.session.PlaybackState;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.Objects;

/* loaded from: mainsysui33.jar:com/android/settingslib/volume/Util.class */
public class Util {
    public static final int[] AUDIO_MANAGER_FLAGS = {1, 16, 4, 2, 8, RecyclerView.ViewHolder.FLAG_MOVED, RecyclerView.ViewHolder.FLAG_IGNORE, RecyclerView.ViewHolder.FLAG_APPEARED_IN_PRE_LAYOUT, RecyclerView.ViewHolder.FLAG_ADAPTER_FULLUPDATE};
    public static final String[] AUDIO_MANAGER_FLAG_NAMES = {"SHOW_UI", "VIBRATE", "PLAY_SOUND", "ALLOW_RINGER_MODES", "REMOVE_SOUND_AND_VIBRATE", "SHOW_VIBRATE_HINT", "SHOW_SILENT_HINT", "FROM_KEY", "SHOW_UI_WARNINGS"};

    public static String audioManagerFlagsToString(int i) {
        return bitFieldToString(i, AUDIO_MANAGER_FLAGS, AUDIO_MANAGER_FLAG_NAMES);
    }

    public static String bitFieldToString(int i, int[] iArr, String[] strArr) {
        if (i == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        int i2 = i;
        for (int i3 = 0; i3 < iArr.length; i3++) {
            if ((iArr[i3] & i2) != 0) {
                if (sb.length() > 0) {
                    sb.append(',');
                }
                sb.append(strArr[i3]);
            }
            i2 &= iArr[i3] ^ (-1);
        }
        if (i2 != 0) {
            if (sb.length() > 0) {
                sb.append(',');
            }
            sb.append("UNKNOWN_");
            sb.append(i2);
        }
        return sb.toString();
    }

    /* JADX WARN: Code restructure failed: missing block: B:5:0x000c, code lost:
        if (r2.length() == 0) goto L8;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static CharSequence emptyToNull(CharSequence charSequence) {
        CharSequence charSequence2;
        if (charSequence != null) {
            charSequence2 = charSequence;
        }
        charSequence2 = null;
        return charSequence2;
    }

    public static String logTag(Class<?> cls) {
        String str = "vol." + cls.getSimpleName();
        if (str.length() >= 23) {
            str = str.substring(0, 23);
        }
        return str;
    }

    public static String mediaMetadataToString(MediaMetadata mediaMetadata) {
        if (mediaMetadata == null) {
            return null;
        }
        return mediaMetadata.getDescription().toString();
    }

    public static String playbackInfoToString(MediaController.PlaybackInfo playbackInfo) {
        if (playbackInfo == null) {
            return null;
        }
        return String.format("PlaybackInfo[vol=%s,max=%s,type=%s,vc=%s],atts=%s", Integer.valueOf(playbackInfo.getCurrentVolume()), Integer.valueOf(playbackInfo.getMaxVolume()), playbackInfoTypeToString(playbackInfo.getPlaybackType()), volumeProviderControlToString(playbackInfo.getVolumeControl()), playbackInfo.getAudioAttributes());
    }

    public static String playbackInfoTypeToString(int i) {
        if (i != 1) {
            if (i != 2) {
                return "UNKNOWN_" + i;
            }
            return "REMOTE";
        }
        return "LOCAL";
    }

    public static String playbackStateStateToString(int i) {
        if (i != 0) {
            if (i != 1) {
                if (i != 2) {
                    if (i != 3) {
                        return "UNKNOWN_" + i;
                    }
                    return "STATE_PLAYING";
                }
                return "STATE_PAUSED";
            }
            return "STATE_STOPPED";
        }
        return "STATE_NONE";
    }

    public static String playbackStateToString(PlaybackState playbackState) {
        if (playbackState == null) {
            return null;
        }
        return playbackStateStateToString(playbackState.getState()) + " " + playbackState;
    }

    public static boolean setText(TextView textView, CharSequence charSequence) {
        if (Objects.equals(emptyToNull(textView.getText()), emptyToNull(charSequence))) {
            return false;
        }
        textView.setText(charSequence);
        return true;
    }

    public static String volumeProviderControlToString(int i) {
        if (i != 0) {
            if (i != 1) {
                if (i != 2) {
                    return "VOLUME_CONTROL_UNKNOWN_" + i;
                }
                return "VOLUME_CONTROL_ABSOLUTE";
            }
            return "VOLUME_CONTROL_RELATIVE";
        }
        return "VOLUME_CONTROL_FIXED";
    }
}