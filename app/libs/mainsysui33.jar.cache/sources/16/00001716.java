package com.android.systemui.dreams.complication;

import android.database.ContentObserver;
import android.os.Handler;
import android.os.UserHandle;
import com.android.systemui.CoreStartable;
import com.android.systemui.dreams.DreamOverlayStateController;
import com.android.systemui.dreams.complication.ComplicationTypesUpdater;
import com.android.systemui.util.settings.SecureSettings;
import java.util.concurrent.Executor;

/* loaded from: mainsysui33.jar:com/android/systemui/dreams/complication/ComplicationTypesUpdater.class */
public class ComplicationTypesUpdater implements CoreStartable {
    public final DreamOverlayStateController mDreamOverlayStateController;
    public final Executor mExecutor;
    public final SecureSettings mSecureSettings;

    /* renamed from: com.android.systemui.dreams.complication.ComplicationTypesUpdater$1 */
    /* loaded from: mainsysui33.jar:com/android/systemui/dreams/complication/ComplicationTypesUpdater$1.class */
    public class AnonymousClass1 extends ContentObserver {
        /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.dreams.complication.ComplicationTypesUpdater$1$$ExternalSyntheticLambda0.run():void] */
        /* renamed from: $r8$lambda$K4Q3vFw7c-kmqOfQEHg1pvGpO5k */
        public static /* synthetic */ void m2592$r8$lambda$K4Q3vFw7ckmqOfQEHg1pvGpO5k(AnonymousClass1 anonymousClass1) {
            anonymousClass1.lambda$onChange$0();
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(Handler handler) {
            super(handler);
            ComplicationTypesUpdater.this = r4;
        }

        public /* synthetic */ void lambda$onChange$0() {
            ComplicationTypesUpdater.this.mDreamOverlayStateController.setAvailableComplicationTypes(ComplicationTypesUpdater.this.getAvailableComplicationTypes());
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z) {
            ComplicationTypesUpdater.this.mExecutor.execute(new Runnable() { // from class: com.android.systemui.dreams.complication.ComplicationTypesUpdater$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    ComplicationTypesUpdater.AnonymousClass1.m2592$r8$lambda$K4Q3vFw7ckmqOfQEHg1pvGpO5k(ComplicationTypesUpdater.AnonymousClass1.this);
                }
            });
        }
    }

    public final int getAvailableComplicationTypes() {
        throw null;
    }

    @Override // com.android.systemui.CoreStartable
    public void start() {
        AnonymousClass1 anonymousClass1 = new AnonymousClass1(null);
        this.mSecureSettings.registerContentObserverForUser("screensaver_complications_enabled", anonymousClass1, UserHandle.myUserId());
        anonymousClass1.onChange(false);
    }
}