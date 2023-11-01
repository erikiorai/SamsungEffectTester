package com.android.systemui.media;

import android.app.ActivityOptions;
import android.app.WaitResult;
import android.content.Intent;
import android.content.res.Configuration;
import android.media.projection.IMediaProjection;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.ResultReceiver;
import android.os.UserHandle;
import android.view.ViewGroup;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.app.ChooserActivity;
import com.android.internal.app.ResolverListController;
import com.android.internal.app.chooser.NotSelectableTargetInfo;
import com.android.internal.app.chooser.TargetInfo;
import com.android.systemui.R$layout;
import com.android.systemui.R$string;
import com.android.systemui.R$style;
import com.android.systemui.mediaprojection.appselector.MediaProjectionAppSelectorComponent;
import com.android.systemui.mediaprojection.appselector.MediaProjectionAppSelectorController;
import com.android.systemui.mediaprojection.appselector.MediaProjectionAppSelectorResultHandler;
import com.android.systemui.mediaprojection.appselector.MediaProjectionAppSelectorView;
import com.android.systemui.mediaprojection.appselector.data.RecentTask;
import com.android.systemui.mediaprojection.appselector.view.MediaProjectionRecentsViewController;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.util.AsyncActivityLauncher;
import java.util.List;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: mainsysui33.jar:com/android/systemui/media/MediaProjectionAppSelectorActivity.class */
public final class MediaProjectionAppSelectorActivity extends ChooserActivity implements MediaProjectionAppSelectorView, MediaProjectionAppSelectorResultHandler {
    public static final Companion Companion = new Companion(null);
    public final AsyncActivityLauncher activityLauncher;
    public final MediaProjectionAppSelectorComponent.Factory componentFactory;
    public ConfigurationController configurationController;
    public MediaProjectionAppSelectorController controller;
    public final Function1<UserHandle, ResolverListController> listControllerFactory;
    public MediaProjectionRecentsViewController recentsViewController;

    /* loaded from: mainsysui33.jar:com/android/systemui/media/MediaProjectionAppSelectorActivity$Companion.class */
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public MediaProjectionAppSelectorActivity(MediaProjectionAppSelectorComponent.Factory factory, AsyncActivityLauncher asyncActivityLauncher) {
        this(factory, asyncActivityLauncher, null);
    }

    /* JADX DEBUG: Multi-variable search result rejected for r6v0, resolved type: kotlin.jvm.functions.Function1<? super android.os.UserHandle, ? extends com.android.internal.app.ResolverListController> */
    /* JADX WARN: Multi-variable type inference failed */
    public MediaProjectionAppSelectorActivity(MediaProjectionAppSelectorComponent.Factory factory, AsyncActivityLauncher asyncActivityLauncher, @VisibleForTesting Function1<? super UserHandle, ? extends ResolverListController> function1) {
        this.componentFactory = factory;
        this.activityLauncher = asyncActivityLauncher;
        this.listControllerFactory = function1;
    }

    public int appliedThemeResId() {
        return R$style.Theme_SystemUI_MediaProjectionAppSelector;
    }

    @Override // com.android.systemui.mediaprojection.appselector.MediaProjectionAppSelectorView
    public void bind(List<RecentTask> list) {
        MediaProjectionRecentsViewController mediaProjectionRecentsViewController = this.recentsViewController;
        MediaProjectionRecentsViewController mediaProjectionRecentsViewController2 = mediaProjectionRecentsViewController;
        if (mediaProjectionRecentsViewController == null) {
            mediaProjectionRecentsViewController2 = null;
        }
        mediaProjectionRecentsViewController2.bind(list);
    }

    public ViewGroup createContentPreviewView(ViewGroup viewGroup) {
        MediaProjectionRecentsViewController mediaProjectionRecentsViewController = this.recentsViewController;
        MediaProjectionRecentsViewController mediaProjectionRecentsViewController2 = mediaProjectionRecentsViewController;
        if (mediaProjectionRecentsViewController == null) {
            mediaProjectionRecentsViewController2 = null;
        }
        return mediaProjectionRecentsViewController2.createView(viewGroup);
    }

    public final Intent createIntent(TargetInfo targetInfo) {
        Intent intent = new Intent(targetInfo.getResolvedIntent());
        intent.setFlags(intent.getFlags() | 268435456);
        intent.setFlags(intent.getFlags() & (-33554433));
        return intent;
    }

    /* JADX WARN: Code restructure failed: missing block: B:5:0x0017, code lost:
        if (r0 == null) goto L8;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public ResolverListController createListController(UserHandle userHandle) {
        ResolverListController createListController;
        Function1<UserHandle, ResolverListController> function1 = this.listControllerFactory;
        if (function1 != null) {
            ResolverListController resolverListController = (ResolverListController) function1.invoke(userHandle);
            createListController = resolverListController;
        }
        createListController = super.createListController(userHandle);
        return createListController;
    }

    public int getLayoutResource() {
        return R$layout.media_projection_app_selector;
    }

    public void onActivityStarted(TargetInfo targetInfo) {
    }

    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        ConfigurationController configurationController = this.configurationController;
        ConfigurationController configurationController2 = configurationController;
        if (configurationController == null) {
            configurationController2 = null;
        }
        configurationController2.onConfigurationChanged(configuration);
    }

    public void onCreate(Bundle bundle) {
        MediaProjectionAppSelectorComponent create = this.componentFactory.create(this, this, this);
        this.configurationController = create.getConfigurationController();
        this.controller = create.getController();
        this.recentsViewController = create.getRecentsViewController();
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.LAUNCHER");
        getIntent().putExtra("android.intent.extra.INTENT", intent);
        getIntent().putExtra("android.intent.extra.TITLE", getString(R$string.media_projection_permission_app_selector_title));
        super.onCreate(bundle);
        MediaProjectionAppSelectorController mediaProjectionAppSelectorController = this.controller;
        MediaProjectionAppSelectorController mediaProjectionAppSelectorController2 = mediaProjectionAppSelectorController;
        if (mediaProjectionAppSelectorController == null) {
            mediaProjectionAppSelectorController2 = null;
        }
        mediaProjectionAppSelectorController2.init();
    }

    public void onDestroy() {
        this.activityLauncher.destroy();
        MediaProjectionAppSelectorController mediaProjectionAppSelectorController = this.controller;
        MediaProjectionAppSelectorController mediaProjectionAppSelectorController2 = mediaProjectionAppSelectorController;
        if (mediaProjectionAppSelectorController == null) {
            mediaProjectionAppSelectorController2 = null;
        }
        mediaProjectionAppSelectorController2.destroy();
        super.onDestroy();
    }

    @Override // com.android.systemui.mediaprojection.appselector.MediaProjectionAppSelectorResultHandler
    public void returnSelectedApp(IBinder iBinder) {
        if (getIntent().hasExtra("capture_region_result_receiver")) {
            ResultReceiver resultReceiver = (ResultReceiver) getIntent().getParcelableExtra("capture_region_result_receiver", ResultReceiver.class);
            MediaProjectionCaptureTarget mediaProjectionCaptureTarget = new MediaProjectionCaptureTarget(iBinder);
            Bundle bundle = new Bundle();
            bundle.putParcelable("capture_region", mediaProjectionCaptureTarget);
            resultReceiver.send(-1, bundle);
        } else {
            IMediaProjection asInterface = IMediaProjection.Stub.asInterface(getIntent().getIBinderExtra("android.media.projection.extra.EXTRA_MEDIA_PROJECTION"));
            asInterface.setLaunchCookie(iBinder);
            Intent intent = new Intent();
            intent.putExtra("android.media.projection.extra.EXTRA_MEDIA_PROJECTION", asInterface.asBinder());
            setResult(-1, intent);
            setForceSendResultForMediaProjection();
        }
        finish();
    }

    public boolean shouldGetOnlyDefaultActivities() {
        return false;
    }

    public boolean shouldShowContentPreview() {
        return true;
    }

    public void startSelected(int i, boolean z, boolean z2) {
        TargetInfo targetInfoForPosition = ((ChooserActivity) this).mChooserMultiProfilePagerAdapter.getActiveListAdapter().targetInfoForPosition(i, z2);
        if (targetInfoForPosition == null || (targetInfoForPosition instanceof NotSelectableTargetInfo)) {
            return;
        }
        Intent createIntent = createIntent(targetInfoForPosition);
        final Binder binder = new Binder("media_projection_launch_token");
        ActivityOptions makeBasic = ActivityOptions.makeBasic();
        makeBasic.setLaunchCookie(binder);
        this.activityLauncher.startActivityAsUser(createIntent, ((ChooserActivity) this).mMultiProfilePagerAdapter.getActiveListAdapter().getUserHandle(), makeBasic.toBundle(), new Function1<WaitResult, Unit>() { // from class: com.android.systemui.media.MediaProjectionAppSelectorActivity$startSelected$activityStarted$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            public /* bridge */ /* synthetic */ Object invoke(Object obj) {
                invoke((WaitResult) obj);
                return Unit.INSTANCE;
            }

            public final void invoke(WaitResult waitResult) {
                MediaProjectionAppSelectorActivity.this.returnSelectedApp(binder);
            }
        });
        targetInfoForPosition.isSuspended();
    }
}