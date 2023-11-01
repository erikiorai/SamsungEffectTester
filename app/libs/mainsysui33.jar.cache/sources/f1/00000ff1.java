package com.android.systemui;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentProvider;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.UserHandle;
import android.util.ArrayMap;
import android.util.ArraySet;
import com.android.internal.annotations.VisibleForTesting;
import com.android.systemui.broadcast.BroadcastDispatcher;
import java.util.Iterator;

/* loaded from: mainsysui33.jar:com/android/systemui/SliceBroadcastRelayHandler.class */
public class SliceBroadcastRelayHandler implements CoreStartable {
    public final BroadcastDispatcher mBroadcastDispatcher;
    public final Context mContext;
    public final ArrayMap<Uri, BroadcastRelay> mRelays = new ArrayMap<>();
    public final BroadcastReceiver mReceiver = new BroadcastReceiver() { // from class: com.android.systemui.SliceBroadcastRelayHandler.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            SliceBroadcastRelayHandler.this.handleIntent(intent);
        }
    };

    /* loaded from: mainsysui33.jar:com/android/systemui/SliceBroadcastRelayHandler$BroadcastRelay.class */
    public static class BroadcastRelay extends BroadcastReceiver {
        public final ArraySet<ComponentName> mReceivers = new ArraySet<>();
        public final Uri mUri;
        public final UserHandle mUserId;

        public BroadcastRelay(Uri uri) {
            this.mUserId = new UserHandle(ContentProvider.getUserIdFromUri(uri));
            this.mUri = uri;
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            intent.addFlags(268435456);
            Iterator<ComponentName> it = this.mReceivers.iterator();
            while (it.hasNext()) {
                intent.setComponent(it.next());
                intent.putExtra("uri", this.mUri.toString());
                context.sendBroadcastAsUser(intent, this.mUserId);
            }
        }

        public void register(Context context, ComponentName componentName, IntentFilter intentFilter) {
            this.mReceivers.add(componentName);
            context.registerReceiver(this, intentFilter, 2);
        }

        public void unregister(Context context) {
            context.unregisterReceiver(this);
        }
    }

    public SliceBroadcastRelayHandler(Context context, BroadcastDispatcher broadcastDispatcher) {
        this.mContext = context;
        this.mBroadcastDispatcher = broadcastDispatcher;
    }

    public final BroadcastRelay getAndRemoveRelay(Uri uri) {
        return this.mRelays.remove(uri);
    }

    public final BroadcastRelay getOrCreateRelay(Uri uri) {
        BroadcastRelay broadcastRelay = this.mRelays.get(uri);
        BroadcastRelay broadcastRelay2 = broadcastRelay;
        if (broadcastRelay == null) {
            broadcastRelay2 = new BroadcastRelay(uri);
            this.mRelays.put(uri, broadcastRelay2);
        }
        return broadcastRelay2;
    }

    @VisibleForTesting
    public void handleIntent(Intent intent) {
        BroadcastRelay andRemoveRelay;
        if ("com.android.settingslib.action.REGISTER_SLICE_RECEIVER".equals(intent.getAction())) {
            getOrCreateRelay((Uri) intent.getParcelableExtra("uri")).register(this.mContext, (ComponentName) intent.getParcelableExtra("receiver"), (IntentFilter) intent.getParcelableExtra("filter"));
        } else if (!"com.android.settingslib.action.UNREGISTER_SLICE_RECEIVER".equals(intent.getAction()) || (andRemoveRelay = getAndRemoveRelay((Uri) intent.getParcelableExtra("uri"))) == null) {
        } else {
            andRemoveRelay.unregister(this.mContext);
        }
    }

    @Override // com.android.systemui.CoreStartable
    public void start() {
        IntentFilter intentFilter = new IntentFilter("com.android.settingslib.action.REGISTER_SLICE_RECEIVER");
        intentFilter.addAction("com.android.settingslib.action.UNREGISTER_SLICE_RECEIVER");
        this.mBroadcastDispatcher.registerReceiver(this.mReceiver, intentFilter);
    }
}