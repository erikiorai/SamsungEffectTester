package com.android.systemui.media.controls.models.player;

import android.media.MediaMetadata;
import android.media.session.MediaController;
import android.media.session.MediaSession;
import android.media.session.PlaybackState;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewParent;
import android.widget.SeekBar;
import androidx.core.view.GestureDetectorCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.android.systemui.media.controls.models.player.SeekBarViewModel;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.statusbar.NotificationMediaManager;
import com.android.systemui.util.concurrency.RepeatableExecutor;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: mainsysui33.jar:com/android/systemui/media/controls/models/player/SeekBarViewModel.class */
public final class SeekBarViewModel {
    public Progress _data = new Progress(false, false, false, false, null, 0);
    public final MutableLiveData<Progress> _progress;
    public final RepeatableExecutor bgExecutor;
    public SeekBarViewModel$callback$1 callback;
    public Runnable cancel;
    public MediaController controller;
    public EnabledChangeListener enabledChangeListener;
    public final FalsingManager falsingManager;
    public boolean isFalseSeek;
    public boolean listening;
    public Function0<Unit> logSeek;
    public PlaybackState playbackState;
    public boolean scrubbing;
    public ScrubbingChangeListener scrubbingChangeListener;

    /* loaded from: mainsysui33.jar:com/android/systemui/media/controls/models/player/SeekBarViewModel$EnabledChangeListener.class */
    public interface EnabledChangeListener {
        void onEnabledChanged(boolean z);
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/media/controls/models/player/SeekBarViewModel$Progress.class */
    public static final class Progress {
        public final int duration;
        public final Integer elapsedTime;
        public final boolean enabled;
        public final boolean playing;
        public final boolean scrubbing;
        public final boolean seekAvailable;

        public Progress(boolean z, boolean z2, boolean z3, boolean z4, Integer num, int i) {
            this.enabled = z;
            this.seekAvailable = z2;
            this.playing = z3;
            this.scrubbing = z4;
            this.elapsedTime = num;
            this.duration = i;
        }

        public static /* synthetic */ Progress copy$default(Progress progress, boolean z, boolean z2, boolean z3, boolean z4, Integer num, int i, int i2, Object obj) {
            if ((i2 & 1) != 0) {
                z = progress.enabled;
            }
            if ((i2 & 2) != 0) {
                z2 = progress.seekAvailable;
            }
            if ((i2 & 4) != 0) {
                z3 = progress.playing;
            }
            if ((i2 & 8) != 0) {
                z4 = progress.scrubbing;
            }
            if ((i2 & 16) != 0) {
                num = progress.elapsedTime;
            }
            if ((i2 & 32) != 0) {
                i = progress.duration;
            }
            return progress.copy(z, z2, z3, z4, num, i);
        }

        public final Progress copy(boolean z, boolean z2, boolean z3, boolean z4, Integer num, int i) {
            return new Progress(z, z2, z3, z4, num, i);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj instanceof Progress) {
                Progress progress = (Progress) obj;
                return this.enabled == progress.enabled && this.seekAvailable == progress.seekAvailable && this.playing == progress.playing && this.scrubbing == progress.scrubbing && Intrinsics.areEqual(this.elapsedTime, progress.elapsedTime) && this.duration == progress.duration;
            }
            return false;
        }

        public final int getDuration() {
            return this.duration;
        }

        public final Integer getElapsedTime() {
            return this.elapsedTime;
        }

        public final boolean getEnabled() {
            return this.enabled;
        }

        public final boolean getPlaying() {
            return this.playing;
        }

        public final boolean getScrubbing() {
            return this.scrubbing;
        }

        public final boolean getSeekAvailable() {
            return this.seekAvailable;
        }

        public int hashCode() {
            boolean z = this.enabled;
            int i = 1;
            boolean z2 = z;
            if (z) {
                z2 = true;
            }
            boolean z3 = this.seekAvailable;
            boolean z4 = z3;
            if (z3) {
                z4 = true;
            }
            boolean z5 = this.playing;
            boolean z6 = z5;
            if (z5) {
                z6 = true;
            }
            boolean z7 = this.scrubbing;
            if (!z7) {
                i = z7;
            }
            Integer num = this.elapsedTime;
            return ((((((((((z2 ? 1 : 0) * 31) + (z4 ? 1 : 0)) * 31) + (z6 ? 1 : 0)) * 31) + i) * 31) + (num == null ? 0 : num.hashCode())) * 31) + Integer.hashCode(this.duration);
        }

        public String toString() {
            boolean z = this.enabled;
            boolean z2 = this.seekAvailable;
            boolean z3 = this.playing;
            boolean z4 = this.scrubbing;
            Integer num = this.elapsedTime;
            int i = this.duration;
            return "Progress(enabled=" + z + ", seekAvailable=" + z2 + ", playing=" + z3 + ", scrubbing=" + z4 + ", elapsedTime=" + num + ", duration=" + i + ")";
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/media/controls/models/player/SeekBarViewModel$ScrubbingChangeListener.class */
    public interface ScrubbingChangeListener {
        void onScrubbingChanged(boolean z);
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/media/controls/models/player/SeekBarViewModel$SeekBarChangeListener.class */
    public static final class SeekBarChangeListener implements SeekBar.OnSeekBarChangeListener {
        public final FalsingManager falsingManager;
        public final SeekBarViewModel viewModel;

        public SeekBarChangeListener(SeekBarViewModel seekBarViewModel, FalsingManager falsingManager) {
            this.viewModel = seekBarViewModel;
            this.falsingManager = falsingManager;
        }

        @Override // android.widget.SeekBar.OnSeekBarChangeListener
        public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
            if (z) {
                this.viewModel.onSeekProgress(i);
            }
        }

        @Override // android.widget.SeekBar.OnSeekBarChangeListener
        public void onStartTrackingTouch(SeekBar seekBar) {
            this.viewModel.onSeekStarting();
        }

        @Override // android.widget.SeekBar.OnSeekBarChangeListener
        public void onStopTrackingTouch(SeekBar seekBar) {
            if (this.falsingManager.isFalseTouch(18)) {
                this.viewModel.onSeekFalse();
            }
            this.viewModel.onSeek(seekBar.getProgress());
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/media/controls/models/player/SeekBarViewModel$SeekBarTouchListener.class */
    public static final class SeekBarTouchListener implements View.OnTouchListener, GestureDetector.OnGestureListener {
        public final SeekBar bar;
        public final GestureDetectorCompat detector;
        public final int flingVelocity;
        public boolean shouldGoToSeekBar;
        public final SeekBarViewModel viewModel;

        public SeekBarTouchListener(SeekBarViewModel seekBarViewModel, SeekBar seekBar) {
            this.viewModel = seekBarViewModel;
            this.bar = seekBar;
            this.detector = new GestureDetectorCompat(seekBar.getContext(), this);
            this.flingVelocity = ViewConfiguration.get(seekBar.getContext()).getScaledMinimumFlingVelocity() * 10;
        }

        @Override // android.view.GestureDetector.OnGestureListener
        public boolean onDown(MotionEvent motionEvent) {
            double d;
            double d2;
            ViewParent parent;
            int paddingLeft = this.bar.getPaddingLeft();
            int paddingRight = this.bar.getPaddingRight();
            int progress = this.bar.getProgress();
            int max = this.bar.getMax() - this.bar.getMin();
            double min = max > 0 ? (progress - this.bar.getMin()) / max : 0.0d;
            int width = (this.bar.getWidth() - paddingLeft) - paddingRight;
            if (this.bar.isLayoutRtl()) {
                d = paddingLeft;
                d2 = width * (1 - min);
            } else {
                d = paddingLeft;
                d2 = width * min;
            }
            double d3 = d + d2;
            int height = this.bar.getHeight() / 2;
            long round = Math.round(d3);
            long j = height;
            int i = (int) (round - j);
            int round2 = (int) (Math.round(d3) + j);
            int round3 = Math.round(motionEvent.getX());
            boolean z = round3 >= i && round3 <= round2;
            this.shouldGoToSeekBar = z;
            if (z && (parent = this.bar.getParent()) != null) {
                parent.requestDisallowInterceptTouchEvent(true);
            }
            return this.shouldGoToSeekBar;
        }

        @Override // android.view.GestureDetector.OnGestureListener
        public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
            if (Math.abs(f) > this.flingVelocity || Math.abs(f2) > this.flingVelocity) {
                this.viewModel.onSeekFalse();
            }
            return this.shouldGoToSeekBar;
        }

        @Override // android.view.GestureDetector.OnGestureListener
        public void onLongPress(MotionEvent motionEvent) {
        }

        @Override // android.view.GestureDetector.OnGestureListener
        public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
            return this.shouldGoToSeekBar;
        }

        @Override // android.view.GestureDetector.OnGestureListener
        public void onShowPress(MotionEvent motionEvent) {
        }

        @Override // android.view.GestureDetector.OnGestureListener
        public boolean onSingleTapUp(MotionEvent motionEvent) {
            this.shouldGoToSeekBar = true;
            return true;
        }

        @Override // android.view.View.OnTouchListener
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (Intrinsics.areEqual(view, this.bar)) {
                this.detector.onTouchEvent(motionEvent);
                return !this.shouldGoToSeekBar;
            }
            return false;
        }
    }

    /* JADX WARN: Type inference failed for: r1v7, types: [com.android.systemui.media.controls.models.player.SeekBarViewModel$callback$1] */
    public SeekBarViewModel(RepeatableExecutor repeatableExecutor, FalsingManager falsingManager) {
        this.bgExecutor = repeatableExecutor;
        this.falsingManager = falsingManager;
        MutableLiveData<Progress> mutableLiveData = new MutableLiveData<>();
        mutableLiveData.postValue(this._data);
        this._progress = mutableLiveData;
        this.callback = new MediaController.Callback() { // from class: com.android.systemui.media.controls.models.player.SeekBarViewModel$callback$1
            @Override // android.media.session.MediaController.Callback
            public void onPlaybackStateChanged(PlaybackState playbackState) {
                SeekBarViewModel.access$setPlaybackState$p(SeekBarViewModel.this, playbackState);
                if (SeekBarViewModel.access$getPlaybackState$p(SeekBarViewModel.this) != null) {
                    Integer num = 0;
                    if (!num.equals(SeekBarViewModel.access$getPlaybackState$p(SeekBarViewModel.this))) {
                        SeekBarViewModel.access$checkIfPollingNeeded(SeekBarViewModel.this);
                        return;
                    }
                }
                SeekBarViewModel.this.clearController();
            }

            @Override // android.media.session.MediaController.Callback
            public void onSessionDestroyed() {
                SeekBarViewModel.this.clearController();
            }
        };
        this.listening = true;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.controls.models.player.SeekBarViewModel$callback$1.onPlaybackStateChanged(android.media.session.PlaybackState):void, com.android.systemui.media.controls.models.player.SeekBarViewModel$listening$1.run():void] */
    public static final /* synthetic */ void access$checkIfPollingNeeded(SeekBarViewModel seekBarViewModel) {
        seekBarViewModel.checkIfPollingNeeded();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.controls.models.player.SeekBarViewModel$checkIfPollingNeeded$1.run():void, com.android.systemui.media.controls.models.player.SeekBarViewModel$onSeek$1.run():void] */
    public static final /* synthetic */ void access$checkPlaybackPosition(SeekBarViewModel seekBarViewModel) {
        seekBarViewModel.checkPlaybackPosition();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.controls.models.player.SeekBarViewModel$clearController$1.run():void, com.android.systemui.media.controls.models.player.SeekBarViewModel$onDestroy$1.run():void] */
    public static final /* synthetic */ Runnable access$getCancel$p(SeekBarViewModel seekBarViewModel) {
        return seekBarViewModel.cancel;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.controls.models.player.SeekBarViewModel$onSeek$1.run():void] */
    public static final /* synthetic */ MediaController access$getController$p(SeekBarViewModel seekBarViewModel) {
        return seekBarViewModel.controller;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.controls.models.player.SeekBarViewModel$listening$1.run():void] */
    public static final /* synthetic */ boolean access$getListening$p(SeekBarViewModel seekBarViewModel) {
        return seekBarViewModel.listening;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.controls.models.player.SeekBarViewModel$callback$1.onPlaybackStateChanged(android.media.session.PlaybackState):void] */
    public static final /* synthetic */ PlaybackState access$getPlaybackState$p(SeekBarViewModel seekBarViewModel) {
        return seekBarViewModel.playbackState;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.controls.models.player.SeekBarViewModel$onSeekFalse$1.run():void, com.android.systemui.media.controls.models.player.SeekBarViewModel$onSeekProgress$1.run():void] */
    public static final /* synthetic */ boolean access$getScrubbing$p(SeekBarViewModel seekBarViewModel) {
        return seekBarViewModel.scrubbing;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.controls.models.player.SeekBarViewModel$clearController$1.run():void, com.android.systemui.media.controls.models.player.SeekBarViewModel$onSeekProgress$1.run():void] */
    public static final /* synthetic */ Progress access$get_data$p(SeekBarViewModel seekBarViewModel) {
        return seekBarViewModel._data;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.controls.models.player.SeekBarViewModel$onSeek$1.run():void] */
    public static final /* synthetic */ boolean access$isFalseSeek$p(SeekBarViewModel seekBarViewModel) {
        return seekBarViewModel.isFalseSeek;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.controls.models.player.SeekBarViewModel$clearController$1.run():void, com.android.systemui.media.controls.models.player.SeekBarViewModel$onDestroy$1.run():void] */
    public static final /* synthetic */ void access$setCancel$p(SeekBarViewModel seekBarViewModel, Runnable runnable) {
        seekBarViewModel.cancel = runnable;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.controls.models.player.SeekBarViewModel$clearController$1.run():void, com.android.systemui.media.controls.models.player.SeekBarViewModel$onDestroy$1.run():void] */
    public static final /* synthetic */ void access$setController(SeekBarViewModel seekBarViewModel, MediaController mediaController) {
        seekBarViewModel.setController(mediaController);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.controls.models.player.SeekBarViewModel$onDestroy$1.run():void] */
    public static final /* synthetic */ void access$setEnabledChangeListener$p(SeekBarViewModel seekBarViewModel, EnabledChangeListener enabledChangeListener) {
        seekBarViewModel.enabledChangeListener = enabledChangeListener;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.controls.models.player.SeekBarViewModel$onSeekFalse$1.run():void, com.android.systemui.media.controls.models.player.SeekBarViewModel$onSeekStarting$1.run():void] */
    public static final /* synthetic */ void access$setFalseSeek$p(SeekBarViewModel seekBarViewModel, boolean z) {
        seekBarViewModel.isFalseSeek = z;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.controls.models.player.SeekBarViewModel$listening$1.run():void] */
    public static final /* synthetic */ void access$setListening$p(SeekBarViewModel seekBarViewModel, boolean z) {
        seekBarViewModel.listening = z;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.controls.models.player.SeekBarViewModel$callback$1.onPlaybackStateChanged(android.media.session.PlaybackState):void, com.android.systemui.media.controls.models.player.SeekBarViewModel$clearController$1.run():void, com.android.systemui.media.controls.models.player.SeekBarViewModel$onDestroy$1.run():void, com.android.systemui.media.controls.models.player.SeekBarViewModel$onSeek$1.run():void] */
    public static final /* synthetic */ void access$setPlaybackState$p(SeekBarViewModel seekBarViewModel, PlaybackState playbackState) {
        seekBarViewModel.playbackState = playbackState;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.controls.models.player.SeekBarViewModel$onSeek$1.run():void, com.android.systemui.media.controls.models.player.SeekBarViewModel$onSeekStarting$1.run():void] */
    public static final /* synthetic */ void access$setScrubbing(SeekBarViewModel seekBarViewModel, boolean z) {
        seekBarViewModel.setScrubbing(z);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.controls.models.player.SeekBarViewModel$onDestroy$1.run():void] */
    public static final /* synthetic */ void access$setScrubbingChangeListener$p(SeekBarViewModel seekBarViewModel, ScrubbingChangeListener scrubbingChangeListener) {
        seekBarViewModel.scrubbingChangeListener = scrubbingChangeListener;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.controls.models.player.SeekBarViewModel$clearController$1.run():void, com.android.systemui.media.controls.models.player.SeekBarViewModel$onSeekProgress$1.run():void] */
    public static final /* synthetic */ void access$set_data(SeekBarViewModel seekBarViewModel, Progress progress) {
        seekBarViewModel.set_data(progress);
    }

    public final void attachTouchHandlers(SeekBar seekBar) {
        seekBar.setOnSeekBarChangeListener(getSeekBarListener());
        seekBar.setOnTouchListener(new SeekBarTouchListener(this, seekBar));
    }

    public final void checkIfPollingNeeded() {
        boolean z;
        boolean isInMotion;
        boolean z2 = false;
        if (this.listening) {
            z2 = false;
            if (!this.scrubbing) {
                PlaybackState playbackState = this.playbackState;
                if (playbackState != null) {
                    isInMotion = SeekBarViewModelKt.isInMotion(playbackState);
                    z = isInMotion;
                } else {
                    z = false;
                }
                z2 = false;
                if (z) {
                    z2 = true;
                }
            }
        }
        if (z2) {
            if (this.cancel == null) {
                this.cancel = this.bgExecutor.executeRepeatedly(new Runnable() { // from class: com.android.systemui.media.controls.models.player.SeekBarViewModel$checkIfPollingNeeded$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        SeekBarViewModel.access$checkPlaybackPosition(SeekBarViewModel.this);
                    }
                }, 0L, 100L);
                return;
            }
            return;
        }
        Runnable runnable = this.cancel;
        if (runnable != null) {
            runnable.run();
        }
        this.cancel = null;
    }

    public final void checkPlaybackPosition() {
        Integer num;
        long computePosition;
        int duration = this._data.getDuration();
        PlaybackState playbackState = this.playbackState;
        if (playbackState != null) {
            computePosition = SeekBarViewModelKt.computePosition(playbackState, duration);
            num = Integer.valueOf((int) computePosition);
        } else {
            num = null;
        }
        if (num == null || Intrinsics.areEqual(this._data.getElapsedTime(), num)) {
            return;
        }
        set_data(Progress.copy$default(this._data, false, false, false, false, num, 0, 47, null));
    }

    public final void clearController() {
        this.bgExecutor.execute(new Runnable() { // from class: com.android.systemui.media.controls.models.player.SeekBarViewModel$clearController$1
            @Override // java.lang.Runnable
            public final void run() {
                SeekBarViewModel.access$setController(SeekBarViewModel.this, null);
                SeekBarViewModel.access$setPlaybackState$p(SeekBarViewModel.this, null);
                Runnable access$getCancel$p = SeekBarViewModel.access$getCancel$p(SeekBarViewModel.this);
                if (access$getCancel$p != null) {
                    access$getCancel$p.run();
                }
                SeekBarViewModel.access$setCancel$p(SeekBarViewModel.this, null);
                SeekBarViewModel seekBarViewModel = SeekBarViewModel.this;
                SeekBarViewModel.access$set_data(seekBarViewModel, SeekBarViewModel.Progress.copy$default(SeekBarViewModel.access$get_data$p(seekBarViewModel), false, false, false, false, null, 0, 62, null));
            }
        });
    }

    public final Function0<Unit> getLogSeek() {
        Function0<Unit> function0 = this.logSeek;
        if (function0 != null) {
            return function0;
        }
        return null;
    }

    public final LiveData<Progress> getProgress() {
        return this._progress;
    }

    public final SeekBar.OnSeekBarChangeListener getSeekBarListener() {
        return new SeekBarChangeListener(this, this.falsingManager);
    }

    public final void onDestroy() {
        this.bgExecutor.execute(new Runnable() { // from class: com.android.systemui.media.controls.models.player.SeekBarViewModel$onDestroy$1
            @Override // java.lang.Runnable
            public final void run() {
                SeekBarViewModel.access$setController(SeekBarViewModel.this, null);
                SeekBarViewModel.access$setPlaybackState$p(SeekBarViewModel.this, null);
                Runnable access$getCancel$p = SeekBarViewModel.access$getCancel$p(SeekBarViewModel.this);
                if (access$getCancel$p != null) {
                    access$getCancel$p.run();
                }
                SeekBarViewModel.access$setCancel$p(SeekBarViewModel.this, null);
                SeekBarViewModel.access$setScrubbingChangeListener$p(SeekBarViewModel.this, null);
                SeekBarViewModel.access$setEnabledChangeListener$p(SeekBarViewModel.this, null);
            }
        });
    }

    public final void onSeek(final long j) {
        this.bgExecutor.execute(new Runnable() { // from class: com.android.systemui.media.controls.models.player.SeekBarViewModel$onSeek$1
            @Override // java.lang.Runnable
            public final void run() {
                MediaController.TransportControls transportControls;
                if (SeekBarViewModel.access$isFalseSeek$p(SeekBarViewModel.this)) {
                    SeekBarViewModel.access$setScrubbing(SeekBarViewModel.this, false);
                    SeekBarViewModel.access$checkPlaybackPosition(SeekBarViewModel.this);
                    return;
                }
                SeekBarViewModel.this.getLogSeek().invoke();
                MediaController access$getController$p = SeekBarViewModel.access$getController$p(SeekBarViewModel.this);
                if (access$getController$p != null && (transportControls = access$getController$p.getTransportControls()) != null) {
                    transportControls.seekTo(j);
                }
                SeekBarViewModel.access$setPlaybackState$p(SeekBarViewModel.this, null);
                SeekBarViewModel.access$setScrubbing(SeekBarViewModel.this, false);
            }
        });
    }

    public final void onSeekFalse() {
        this.bgExecutor.execute(new Runnable() { // from class: com.android.systemui.media.controls.models.player.SeekBarViewModel$onSeekFalse$1
            @Override // java.lang.Runnable
            public final void run() {
                if (SeekBarViewModel.access$getScrubbing$p(SeekBarViewModel.this)) {
                    SeekBarViewModel.access$setFalseSeek$p(SeekBarViewModel.this, true);
                }
            }
        });
    }

    public final void onSeekProgress(final long j) {
        this.bgExecutor.execute(new Runnable() { // from class: com.android.systemui.media.controls.models.player.SeekBarViewModel$onSeekProgress$1
            @Override // java.lang.Runnable
            public final void run() {
                if (!SeekBarViewModel.access$getScrubbing$p(SeekBarViewModel.this)) {
                    SeekBarViewModel.this.onSeek(j);
                    return;
                }
                SeekBarViewModel seekBarViewModel = SeekBarViewModel.this;
                SeekBarViewModel.access$set_data(seekBarViewModel, SeekBarViewModel.Progress.copy$default(SeekBarViewModel.access$get_data$p(seekBarViewModel), false, false, false, false, Integer.valueOf((int) j), 0, 47, null));
            }
        });
    }

    public final void onSeekStarting() {
        this.bgExecutor.execute(new Runnable() { // from class: com.android.systemui.media.controls.models.player.SeekBarViewModel$onSeekStarting$1
            @Override // java.lang.Runnable
            public final void run() {
                SeekBarViewModel.access$setScrubbing(SeekBarViewModel.this, true);
                SeekBarViewModel.access$setFalseSeek$p(SeekBarViewModel.this, false);
            }
        });
    }

    public final void removeEnabledChangeListener(EnabledChangeListener enabledChangeListener) {
        if (Intrinsics.areEqual(enabledChangeListener, this.enabledChangeListener)) {
            this.enabledChangeListener = null;
        }
    }

    public final void removeScrubbingChangeListener(ScrubbingChangeListener scrubbingChangeListener) {
        if (Intrinsics.areEqual(scrubbingChangeListener, this.scrubbingChangeListener)) {
            this.scrubbingChangeListener = null;
        }
    }

    public final void setController(MediaController mediaController) {
        MediaController mediaController2 = this.controller;
        MediaSession.Token token = null;
        MediaSession.Token sessionToken = mediaController2 != null ? mediaController2.getSessionToken() : null;
        if (mediaController != null) {
            token = mediaController.getSessionToken();
        }
        if (Intrinsics.areEqual(sessionToken, token)) {
            return;
        }
        MediaController mediaController3 = this.controller;
        if (mediaController3 != null) {
            mediaController3.unregisterCallback(this.callback);
        }
        if (mediaController != null) {
            mediaController.registerCallback(this.callback);
        }
        this.controller = mediaController;
    }

    public final void setEnabledChangeListener(EnabledChangeListener enabledChangeListener) {
        this.enabledChangeListener = enabledChangeListener;
    }

    public final void setListening(final boolean z) {
        this.bgExecutor.execute(new Runnable() { // from class: com.android.systemui.media.controls.models.player.SeekBarViewModel$listening$1
            @Override // java.lang.Runnable
            public final void run() {
                boolean access$getListening$p = SeekBarViewModel.access$getListening$p(SeekBarViewModel.this);
                boolean z2 = z;
                if (access$getListening$p != z2) {
                    SeekBarViewModel.access$setListening$p(SeekBarViewModel.this, z2);
                    SeekBarViewModel.access$checkIfPollingNeeded(SeekBarViewModel.this);
                }
            }
        });
    }

    public final void setLogSeek(Function0<Unit> function0) {
        this.logSeek = function0;
    }

    public final void setScrubbing(boolean z) {
        if (this.scrubbing != z) {
            this.scrubbing = z;
            checkIfPollingNeeded();
            ScrubbingChangeListener scrubbingChangeListener = this.scrubbingChangeListener;
            if (scrubbingChangeListener != null) {
                scrubbingChangeListener.onScrubbingChanged(z);
            }
            set_data(Progress.copy$default(this._data, false, false, false, z, null, 0, 55, null));
        }
    }

    public final void setScrubbingChangeListener(ScrubbingChangeListener scrubbingChangeListener) {
        this.scrubbingChangeListener = scrubbingChangeListener;
    }

    public final void set_data(Progress progress) {
        EnabledChangeListener enabledChangeListener;
        boolean z = progress.getEnabled() != this._data.getEnabled();
        this._data = progress;
        if (z && (enabledChangeListener = this.enabledChangeListener) != null) {
            enabledChangeListener.onEnabledChanged(progress.getEnabled());
        }
        this._progress.postValue(progress);
    }

    public final void updateController(MediaController mediaController) {
        boolean z;
        setController(mediaController);
        MediaController mediaController2 = this.controller;
        Integer num = null;
        this.playbackState = mediaController2 != null ? mediaController2.getPlaybackState() : null;
        MediaController mediaController3 = this.controller;
        MediaMetadata metadata = mediaController3 != null ? mediaController3.getMetadata() : null;
        PlaybackState playbackState = this.playbackState;
        boolean z2 = ((playbackState != null ? playbackState.getActions() : 0L) & 256) != 0;
        PlaybackState playbackState2 = this.playbackState;
        if (playbackState2 != null) {
            num = Integer.valueOf((int) playbackState2.getPosition());
        }
        int i = metadata != null ? (int) metadata.getLong("android.media.metadata.DURATION") : 0;
        PlaybackState playbackState3 = this.playbackState;
        boolean isPlayingState = NotificationMediaManager.isPlayingState(playbackState3 != null ? playbackState3.getState() : 0);
        PlaybackState playbackState4 = this.playbackState;
        if (playbackState4 != null) {
            if (!(playbackState4 != null && playbackState4.getState() == 0) && i > 0) {
                z = true;
                set_data(new Progress(z, z2, isPlayingState, this.scrubbing, num, i));
                checkIfPollingNeeded();
            }
        }
        z = false;
        set_data(new Progress(z, z2, isPlayingState, this.scrubbing, num, i));
        checkIfPollingNeeded();
    }
}