package com.android.keyguard;

import android.app.PendingIntent;
import android.net.Uri;
import android.os.Trace;
import android.util.Log;
import android.view.Display;
import android.view.View;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.slice.Slice;
import androidx.slice.SliceViewManager;
import androidx.slice.widget.ListContent;
import androidx.slice.widget.RowContent;
import androidx.slice.widget.SliceContent;
import androidx.slice.widget.SliceLiveData;
import com.android.systemui.Dumpable;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.keyguard.KeyguardSliceProvider;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.tuner.TunerService;
import com.android.systemui.util.ViewController;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/* loaded from: mainsysui33.jar:com/android/keyguard/KeyguardSliceViewController.class */
public class KeyguardSliceViewController extends ViewController<KeyguardSliceView> implements Dumpable {
    public final ActivityStarter mActivityStarter;
    public Map<View, PendingIntent> mClickActions;
    public final ConfigurationController mConfigurationController;
    public ConfigurationController.ConfigurationListener mConfigurationListener;
    public int mDisplayId;
    public final DumpManager mDumpManager;
    public Uri mKeyguardSliceUri;
    public LiveData<Slice> mLiveData;
    public Observer<Slice> mObserver;
    public View.OnClickListener mOnClickListener;
    public Slice mSlice;
    public TunerService.Tunable mTunable;
    public final TunerService mTunerService;

    /* JADX DEBUG: Method not inlined, still used in: [com.android.keyguard.KeyguardSliceViewController$$ExternalSyntheticLambda0.onTuningChanged(java.lang.String, java.lang.String):void] */
    /* renamed from: $r8$lambda$ndFpx236MS-8vkLU2wiapo5K4tM */
    public static /* synthetic */ void m694$r8$lambda$ndFpx236MS8vkLU2wiapo5K4tM(KeyguardSliceViewController keyguardSliceViewController, String str, String str2) {
        keyguardSliceViewController.lambda$new$0(str, str2);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.keyguard.KeyguardSliceViewController$$ExternalSyntheticLambda1.test(java.lang.Object):boolean] */
    public static /* synthetic */ boolean $r8$lambda$tFGuFHYvAhZsxLxKMyAo_E0op5o(SliceContent sliceContent) {
        return lambda$showSlice$1(sliceContent);
    }

    public KeyguardSliceViewController(KeyguardSliceView keyguardSliceView, ActivityStarter activityStarter, ConfigurationController configurationController, TunerService tunerService, DumpManager dumpManager) {
        super(keyguardSliceView);
        this.mTunable = new TunerService.Tunable() { // from class: com.android.keyguard.KeyguardSliceViewController$$ExternalSyntheticLambda0
            public final void onTuningChanged(String str, String str2) {
                KeyguardSliceViewController.m694$r8$lambda$ndFpx236MS8vkLU2wiapo5K4tM(KeyguardSliceViewController.this, str, str2);
            }
        };
        this.mConfigurationListener = new ConfigurationController.ConfigurationListener() { // from class: com.android.keyguard.KeyguardSliceViewController.1
            {
                KeyguardSliceViewController.this = this;
            }

            public void onDensityOrFontScaleChanged() {
                ((KeyguardSliceView) ((ViewController) KeyguardSliceViewController.this).mView).onDensityOrFontScaleChanged();
            }

            public void onThemeChanged() {
                ((KeyguardSliceView) ((ViewController) KeyguardSliceViewController.this).mView).onOverlayChanged();
            }
        };
        this.mObserver = new Observer<Slice>() { // from class: com.android.keyguard.KeyguardSliceViewController.2
            {
                KeyguardSliceViewController.this = this;
            }

            @Override // androidx.lifecycle.Observer
            public void onChanged(Slice slice) {
                KeyguardSliceViewController.this.mSlice = slice;
                KeyguardSliceViewController.this.showSlice(slice);
            }
        };
        this.mOnClickListener = new View.OnClickListener() { // from class: com.android.keyguard.KeyguardSliceViewController.3
            {
                KeyguardSliceViewController.this = this;
            }

            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                PendingIntent pendingIntent = (PendingIntent) KeyguardSliceViewController.this.mClickActions.get(view);
                if (pendingIntent == null || KeyguardSliceViewController.this.mActivityStarter == null) {
                    return;
                }
                KeyguardSliceViewController.this.mActivityStarter.startPendingIntentDismissingKeyguard(pendingIntent);
            }
        };
        this.mActivityStarter = activityStarter;
        this.mConfigurationController = configurationController;
        this.mTunerService = tunerService;
        this.mDumpManager = dumpManager;
    }

    public /* synthetic */ void lambda$new$0(String str, String str2) {
        setupUri(str2);
    }

    public static /* synthetic */ boolean lambda$showSlice$1(SliceContent sliceContent) {
        return !KeyguardSliceProvider.KEYGUARD_ACTION_URI.equals(sliceContent.getSliceItem().getSlice().getUri().toString());
    }

    @Override // com.android.systemui.Dumpable
    public void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println("  mSlice: " + this.mSlice);
        printWriter.println("  mClickActions: " + this.mClickActions);
    }

    public void onViewAttached() {
        LiveData<Slice> liveData;
        Display display = ((KeyguardSliceView) ((ViewController) this).mView).getDisplay();
        if (display != null) {
            this.mDisplayId = display.getDisplayId();
        }
        this.mTunerService.addTunable(this.mTunable, new String[]{"keyguard_slice_uri"});
        if (this.mDisplayId == 0 && (liveData = this.mLiveData) != null) {
            liveData.observeForever(this.mObserver);
        }
        this.mConfigurationController.addCallback(this.mConfigurationListener);
        DumpManager dumpManager = this.mDumpManager;
        dumpManager.registerDumpable("KeyguardSliceViewCtrl@" + Integer.toHexString(hashCode()), this);
    }

    public void onViewDetached() {
        if (this.mDisplayId == 0) {
            this.mLiveData.removeObserver(this.mObserver);
        }
        this.mTunerService.removeTunable(this.mTunable);
        this.mConfigurationController.removeCallback(this.mConfigurationListener);
        DumpManager dumpManager = this.mDumpManager;
        dumpManager.unregisterDumpable("KeyguardSliceViewCtrl@" + Integer.toHexString(hashCode()));
    }

    public void refresh() {
        Slice bindSlice;
        Trace.beginSection("KeyguardSliceViewController#refresh");
        if (KeyguardSliceProvider.KEYGUARD_SLICE_URI.equals(this.mKeyguardSliceUri.toString())) {
            KeyguardSliceProvider attachedInstance = KeyguardSliceProvider.getAttachedInstance();
            if (attachedInstance != null) {
                bindSlice = attachedInstance.onBindSlice(this.mKeyguardSliceUri);
            } else {
                Log.w("KeyguardSliceViewCtrl", "Keyguard slice not bound yet?");
                bindSlice = null;
            }
        } else {
            bindSlice = SliceViewManager.getInstance(((KeyguardSliceView) ((ViewController) this).mView).getContext()).bindSlice(this.mKeyguardSliceUri);
        }
        this.mObserver.onChanged(bindSlice);
        Trace.endSection();
    }

    public void setupUri(String str) {
        String str2 = str;
        if (str == null) {
            str2 = KeyguardSliceProvider.KEYGUARD_SLICE_URI;
        }
        LiveData<Slice> liveData = this.mLiveData;
        boolean z = false;
        if (liveData != null) {
            z = false;
            if (liveData.hasActiveObservers()) {
                z = true;
                this.mLiveData.removeObserver(this.mObserver);
            }
        }
        this.mKeyguardSliceUri = Uri.parse(str2);
        LiveData<Slice> fromUri = SliceLiveData.fromUri(((KeyguardSliceView) ((ViewController) this).mView).getContext(), this.mKeyguardSliceUri);
        this.mLiveData = fromUri;
        if (z) {
            fromUri.observeForever(this.mObserver);
        }
    }

    public void showSlice(Slice slice) {
        Trace.beginSection("KeyguardSliceViewController#showSlice");
        if (slice == null) {
            ((KeyguardSliceView) ((ViewController) this).mView).hideSlice();
            Trace.endSection();
            return;
        }
        ListContent listContent = new ListContent(slice);
        RowContent header = listContent.getHeader();
        boolean z = (header == null || header.getSliceItem().hasHint("list_item")) ? false : true;
        List<SliceContent> list = (List) listContent.getRowItems().stream().filter(new Predicate() { // from class: com.android.keyguard.KeyguardSliceViewController$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return KeyguardSliceViewController.$r8$lambda$tFGuFHYvAhZsxLxKMyAo_E0op5o((SliceContent) obj);
            }
        }).collect(Collectors.toList());
        KeyguardSliceView keyguardSliceView = (KeyguardSliceView) ((ViewController) this).mView;
        if (!z) {
            header = null;
        }
        this.mClickActions = keyguardSliceView.showSlice(header, list);
        Trace.endSection();
    }
}