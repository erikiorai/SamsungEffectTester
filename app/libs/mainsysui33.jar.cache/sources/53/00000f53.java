package com.android.systemui;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.UserInfo;
import android.util.Log;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.settings.UserTracker;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineStart;

/* loaded from: mainsysui33.jar:com/android/systemui/ChooserSelector.class */
public final class ChooserSelector implements CoreStartable {
    public final CoroutineDispatcher bgDispatcher;
    public final ComponentName chooserComponent;
    public final Context context;
    public final CoroutineScope coroutineScope;
    public final FeatureFlags featureFlags;
    public final UserTracker userTracker;

    public ChooserSelector(Context context, UserTracker userTracker, FeatureFlags featureFlags, CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher) {
        this.context = context;
        this.userTracker = userTracker;
        this.featureFlags = featureFlags;
        this.coroutineScope = coroutineScope;
        this.bgDispatcher = coroutineDispatcher;
        this.chooserComponent = ComponentName.unflattenFromString(context.getResources().getString(17039913));
    }

    public final void setUnbundledChooserEnabled(boolean z) {
        int i = z ? 1 : 2;
        for (UserInfo userInfo : this.userTracker.getUserProfiles()) {
            try {
                this.context.createContextAsUser(userInfo.getUserHandle(), 0).getPackageManager().setComponentEnabledSetting(this.chooserComponent, i, 0);
            } catch (IllegalArgumentException e) {
                int i2 = userInfo.id;
                Log.w("ChooserSelector", "Unable to set IntentResolver enabled=" + z + " for user " + i2, e);
            }
        }
    }

    @Override // com.android.systemui.CoreStartable
    public void start() {
        BuildersKt.launch$default(this.coroutineScope, (CoroutineContext) null, (CoroutineStart) null, new ChooserSelector$start$1(this, null), 3, (Object) null);
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0042  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0060  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object updateUnbundledChooserEnabled(Continuation<? super Unit> continuation) {
        ChooserSelector$updateUnbundledChooserEnabled$1 chooserSelector$updateUnbundledChooserEnabled$1;
        int i;
        ChooserSelector chooserSelector;
        if (continuation instanceof ChooserSelector$updateUnbundledChooserEnabled$1) {
            ChooserSelector$updateUnbundledChooserEnabled$1 chooserSelector$updateUnbundledChooserEnabled$12 = (ChooserSelector$updateUnbundledChooserEnabled$1) continuation;
            int i2 = chooserSelector$updateUnbundledChooserEnabled$12.label;
            if ((i2 & Integer.MIN_VALUE) != 0) {
                chooserSelector$updateUnbundledChooserEnabled$12.label = i2 - 2147483648;
                chooserSelector$updateUnbundledChooserEnabled$1 = chooserSelector$updateUnbundledChooserEnabled$12;
                Object obj = chooserSelector$updateUnbundledChooserEnabled$1.result;
                Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
                i = chooserSelector$updateUnbundledChooserEnabled$1.label;
                if (i != 0) {
                    ResultKt.throwOnFailure(obj);
                    CoroutineDispatcher coroutineDispatcher = this.bgDispatcher;
                    ChooserSelector$updateUnbundledChooserEnabled$2 chooserSelector$updateUnbundledChooserEnabled$2 = new ChooserSelector$updateUnbundledChooserEnabled$2(this, null);
                    chooserSelector$updateUnbundledChooserEnabled$1.L$0 = this;
                    chooserSelector$updateUnbundledChooserEnabled$1.label = 1;
                    Object withContext = BuildersKt.withContext(coroutineDispatcher, chooserSelector$updateUnbundledChooserEnabled$2, chooserSelector$updateUnbundledChooserEnabled$1);
                    chooserSelector = this;
                    obj = withContext;
                    if (withContext == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                } else if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                } else {
                    chooserSelector = (ChooserSelector) chooserSelector$updateUnbundledChooserEnabled$1.L$0;
                    ResultKt.throwOnFailure(obj);
                }
                chooserSelector.setUnbundledChooserEnabled(((Boolean) obj).booleanValue());
                return Unit.INSTANCE;
            }
        }
        chooserSelector$updateUnbundledChooserEnabled$1 = new ChooserSelector$updateUnbundledChooserEnabled$1(this, continuation);
        Object obj2 = chooserSelector$updateUnbundledChooserEnabled$1.result;
        Object coroutine_suspended2 = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
        i = chooserSelector$updateUnbundledChooserEnabled$1.label;
        if (i != 0) {
        }
        chooserSelector.setUnbundledChooserEnabled(((Boolean) obj2).booleanValue());
        return Unit.INSTANCE;
    }
}