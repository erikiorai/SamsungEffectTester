package com.android.systemui.screenshot;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.HardwareRenderer;
import android.graphics.Matrix;
import android.graphics.RecordingCanvas;
import android.graphics.Rect;
import android.graphics.RenderNode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Process;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.Log;
import android.view.ScrollCaptureResponse;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.android.internal.logging.UiEventLogger;
import com.android.settingslib.widget.ActionBarShadowController;
import com.android.systemui.R$id;
import com.android.systemui.R$layout;
import com.android.systemui.R$string;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.Flags;
import com.android.systemui.screenshot.CropView;
import com.android.systemui.screenshot.ImageExporter;
import com.android.systemui.screenshot.ImageLoader;
import com.android.systemui.screenshot.LongScreenshotActivity;
import com.android.systemui.screenshot.ScrollCaptureController;
import com.google.common.util.concurrent.ListenableFuture;
import java.io.File;
import java.time.ZonedDateTime;
import java.util.UUID;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;

/* loaded from: mainsysui33.jar:com/android/systemui/screenshot/LongScreenshotActivity.class */
public class LongScreenshotActivity extends Activity {
    public static final String TAG = LogConfig.logTag(LongScreenshotActivity.class);
    public final ActionIntentExecutor mActionExecutor;
    public final Executor mBackgroundExecutor;
    public ListenableFuture<ImageLoader.Result> mCacheLoadFuture;
    public ListenableFuture<File> mCacheSaveFuture;
    public View mCancel;
    public CropView mCropView;
    public View mEdit;
    public ImageView mEnterTransitionView;
    public final FeatureFlags mFeatureFlags;
    public final ImageExporter mImageExporter;
    public ScrollCaptureController.LongScreenshot mLongScreenshot;
    public final LongScreenshotData mLongScreenshotHolder;
    public MagnifierView mMagnifierView;
    public Bitmap mOutputBitmap;
    public ImageView mPreview;
    public View mSave;
    public File mSavedImagePath;
    public UserHandle mScreenshotUserHandle;
    public ScrollCaptureResponse mScrollCaptureResponse;
    public View mShare;
    public boolean mTransitionStarted;
    public ImageView mTransitionView;
    public final UiEventLogger mUiEventLogger;
    public final Executor mUiExecutor;

    /* renamed from: com.android.systemui.screenshot.LongScreenshotActivity$1 */
    /* loaded from: mainsysui33.jar:com/android/systemui/screenshot/LongScreenshotActivity$1.class */
    public class AnonymousClass1 implements ViewTreeObserver.OnPreDrawListener {
        public final /* synthetic */ float val$bottomFraction;
        public final /* synthetic */ float val$topFraction;

        /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.screenshot.LongScreenshotActivity$1$$ExternalSyntheticLambda0.run():void] */
        /* renamed from: $r8$lambda$0pLAaD5zM9Vsmh-JUuaGTwE8CHc */
        public static /* synthetic */ void m4243$r8$lambda$0pLAaD5zM9VsmhJUuaGTwE8CHc(AnonymousClass1 anonymousClass1, float f, float f2) {
            anonymousClass1.lambda$onPreDraw$1(f, f2);
        }

        /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.screenshot.LongScreenshotActivity$1$$ExternalSyntheticLambda1.run():void] */
        public static /* synthetic */ void $r8$lambda$FzbI6_Z0dM2xU25QZ5103XaY_cw(AnonymousClass1 anonymousClass1, float f, float f2) {
            anonymousClass1.lambda$onPreDraw$0(f, f2);
        }

        public AnonymousClass1(float f, float f2) {
            LongScreenshotActivity.this = r4;
            this.val$topFraction = f;
            this.val$bottomFraction = f2;
        }

        public /* synthetic */ void lambda$onPreDraw$0(float f, float f2) {
            LongScreenshotActivity.this.mPreview.animate().alpha(1.0f);
            LongScreenshotActivity.this.mCropView.setBoundaryPosition(CropView.CropBoundary.TOP, f);
            LongScreenshotActivity.this.mCropView.setBoundaryPosition(CropView.CropBoundary.BOTTOM, f2);
            LongScreenshotActivity.this.mCropView.animateEntrance();
            LongScreenshotActivity.this.mCropView.setVisibility(0);
            LongScreenshotActivity.this.setButtonsEnabled(true);
        }

        public /* synthetic */ void lambda$onPreDraw$1(final float f, final float f2) {
            Rect rect = new Rect();
            LongScreenshotActivity.this.mEnterTransitionView.getBoundsOnScreen(rect);
            LongScreenshotActivity.this.mLongScreenshotHolder.takeTransitionDestinationCallback().setTransitionDestination(rect, new Runnable() { // from class: com.android.systemui.screenshot.LongScreenshotActivity$1$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    LongScreenshotActivity.AnonymousClass1.$r8$lambda$FzbI6_Z0dM2xU25QZ5103XaY_cw(LongScreenshotActivity.AnonymousClass1.this, f, f2);
                }
            });
        }

        @Override // android.view.ViewTreeObserver.OnPreDrawListener
        public boolean onPreDraw() {
            LongScreenshotActivity.this.mEnterTransitionView.getViewTreeObserver().removeOnPreDrawListener(this);
            LongScreenshotActivity.this.updateImageDimensions();
            ImageView imageView = LongScreenshotActivity.this.mEnterTransitionView;
            final float f = this.val$topFraction;
            final float f2 = this.val$bottomFraction;
            imageView.post(new Runnable() { // from class: com.android.systemui.screenshot.LongScreenshotActivity$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    LongScreenshotActivity.AnonymousClass1.m4243$r8$lambda$0pLAaD5zM9VsmhJUuaGTwE8CHc(LongScreenshotActivity.AnonymousClass1.this, f, f2);
                }
            });
            return true;
        }
    }

    /* renamed from: com.android.systemui.screenshot.LongScreenshotActivity$2 */
    /* loaded from: mainsysui33.jar:com/android/systemui/screenshot/LongScreenshotActivity$2.class */
    public static /* synthetic */ class AnonymousClass2 {
        public static final /* synthetic */ int[] $SwitchMap$com$android$systemui$screenshot$LongScreenshotActivity$PendingAction;

        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:31:0x002b -> B:37:0x0014). Please submit an issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:33:0x002f -> B:41:0x001f). Please submit an issue!!! */
        static {
            int[] iArr = new int[PendingAction.values().length];
            $SwitchMap$com$android$systemui$screenshot$LongScreenshotActivity$PendingAction = iArr;
            try {
                iArr[PendingAction.EDIT.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$android$systemui$screenshot$LongScreenshotActivity$PendingAction[PendingAction.SHARE.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$android$systemui$screenshot$LongScreenshotActivity$PendingAction[PendingAction.SAVE.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/screenshot/LongScreenshotActivity$PendingAction.class */
    public enum PendingAction {
        SHARE,
        EDIT,
        SAVE
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.screenshot.LongScreenshotActivity$$ExternalSyntheticLambda0.run():void] */
    public static /* synthetic */ void $r8$lambda$Ise6YOW3HWrs4du3NMRkxL3Oqj8(LongScreenshotActivity longScreenshotActivity, ListenableFuture listenableFuture) {
        longScreenshotActivity.lambda$onStart$1(listenableFuture);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.screenshot.LongScreenshotActivity$$ExternalSyntheticLambda2.onLayoutChange(android.view.View, int, int, int, int, int, int, int, int):void] */
    public static /* synthetic */ void $r8$lambda$MYFi5wKU3CkJeeDGZ8VeNuDOoFA(LongScreenshotActivity longScreenshotActivity, View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        longScreenshotActivity.lambda$onCreate$0(view, i, i2, i3, i4, i5, i6, i7, i8);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.screenshot.LongScreenshotActivity$$ExternalSyntheticLambda3.run():void] */
    public static /* synthetic */ void $r8$lambda$_ZlZSXMYcGJIm8arvK1OF2XBF6s(LongScreenshotActivity longScreenshotActivity) {
        longScreenshotActivity.lambda$onLongScreenshotReceived$2();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.screenshot.LongScreenshotActivity$$ExternalSyntheticLambda4.run():void] */
    /* renamed from: $r8$lambda$k5xu7-vF8LkGJAloB_x6-IZSwV0 */
    public static /* synthetic */ void m4235$r8$lambda$k5xu7vF8LkGJAloB_x6IZSwV0(LongScreenshotActivity longScreenshotActivity, PendingAction pendingAction, ListenableFuture listenableFuture) {
        longScreenshotActivity.lambda$startExport$3(pendingAction, listenableFuture);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.screenshot.LongScreenshotActivity$$ExternalSyntheticLambda1.onClick(android.view.View):void] */
    public static /* synthetic */ void $r8$lambda$qOUpLbBDnQlAC3CzBI4dsyuYNs4(LongScreenshotActivity longScreenshotActivity, View view) {
        longScreenshotActivity.onClicked(view);
    }

    public LongScreenshotActivity(UiEventLogger uiEventLogger, ImageExporter imageExporter, Executor executor, Executor executor2, LongScreenshotData longScreenshotData, ActionIntentExecutor actionIntentExecutor, FeatureFlags featureFlags) {
        this.mUiEventLogger = uiEventLogger;
        this.mUiExecutor = executor;
        this.mBackgroundExecutor = executor2;
        this.mImageExporter = imageExporter;
        this.mLongScreenshotHolder = longScreenshotData;
        this.mActionExecutor = actionIntentExecutor;
        this.mFeatureFlags = featureFlags;
    }

    public /* synthetic */ void lambda$onCreate$0(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        updateImageDimensions();
    }

    public /* synthetic */ void lambda$onLongScreenshotReceived$2() {
        try {
            this.mSavedImagePath = (File) this.mCacheSaveFuture.get();
        } catch (InterruptedException | CancellationException | ExecutionException e) {
            Log.e(TAG, "Error saving temp image file", e);
            finishAndRemoveTask();
        }
    }

    public /* synthetic */ void lambda$onStart$1(ListenableFuture listenableFuture) {
        Log.d(TAG, "cached bitmap load complete");
        try {
            onCachedImageLoaded((ImageLoader.Result) listenableFuture.get());
        } catch (InterruptedException | CancellationException | ExecutionException e) {
            Log.e(TAG, "Failed to load cached image", e);
            File file = this.mSavedImagePath;
            if (file != null) {
                file.delete();
                this.mSavedImagePath = null;
            }
            finishAndRemoveTask();
        }
    }

    public static Bitmap renderBitmap(Drawable drawable, Rect rect) {
        RenderNode renderNode = new RenderNode("Bitmap Export");
        renderNode.setPosition(0, 0, rect.width(), rect.height());
        RecordingCanvas beginRecording = renderNode.beginRecording();
        beginRecording.translate(-rect.left, -rect.top);
        beginRecording.clipRect(rect);
        drawable.draw(beginRecording);
        renderNode.endRecording();
        return HardwareRenderer.createHardwareBitmap(renderNode, rect.width(), rect.height());
    }

    public void cleanupCache() {
        ListenableFuture<File> listenableFuture = this.mCacheSaveFuture;
        if (listenableFuture != null) {
            listenableFuture.cancel(true);
        }
        File file = this.mSavedImagePath;
        if (file != null) {
            file.delete();
            this.mSavedImagePath = null;
        }
    }

    public final void doEdit(Uri uri) {
        if (this.mFeatureFlags.isEnabled(Flags.SCREENSHOT_WORK_PROFILE_POLICY) && this.mScreenshotUserHandle != Process.myUserHandle()) {
            this.mActionExecutor.launchIntentAsync(ActionIntentCreator.INSTANCE.createEditIntent(uri, this), null, this.mScreenshotUserHandle.getIdentifier(), false);
            return;
        }
        String string = getString(R$string.config_screenshotEditor);
        Intent intent = new Intent("android.intent.action.EDIT");
        if (!TextUtils.isEmpty(string)) {
            intent.setComponent(ComponentName.unflattenFromString(string));
        }
        intent.setDataAndType(uri, "image/png");
        intent.addFlags(3);
        this.mTransitionView.setImageBitmap(this.mOutputBitmap);
        this.mTransitionView.setVisibility(0);
        this.mTransitionView.setTransitionName("screenshot_preview_image");
        this.mTransitionStarted = true;
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this, this.mTransitionView, "screenshot_preview_image").toBundle());
    }

    public final void doShare(Uri uri) {
        if (this.mFeatureFlags.isEnabled(Flags.SCREENSHOT_WORK_PROFILE_POLICY)) {
            this.mActionExecutor.launchIntentAsync(ActionIntentCreator.INSTANCE.createShareIntent(uri, null), null, this.mScreenshotUserHandle.getIdentifier(), false);
            return;
        }
        Intent intent = new Intent("android.intent.action.SEND");
        intent.setType("image/png");
        intent.putExtra("android.intent.extra.STREAM", uri);
        intent.addFlags(268468225);
        startActivityAsUser(Intent.createChooser(intent, null).addFlags(1), UserHandle.CURRENT);
    }

    public final void onCachedImageLoaded(ImageLoader.Result result) {
        this.mUiEventLogger.log(ScreenshotEvent.SCREENSHOT_LONG_SCREENSHOT_ACTIVITY_CACHED_IMAGE_LOADED);
        BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(), result.bitmap);
        this.mPreview.setImageDrawable(bitmapDrawable);
        this.mPreview.setAlpha(1.0f);
        this.mMagnifierView.setDrawable(bitmapDrawable, result.bitmap.getWidth(), result.bitmap.getHeight());
        this.mCropView.setVisibility(0);
        this.mSavedImagePath = result.fileName;
        setButtonsEnabled(true);
    }

    public final void onClicked(View view) {
        int id = view.getId();
        view.setPressed(true);
        setButtonsEnabled(false);
        if (id == R$id.save) {
            this.mUiEventLogger.log(ScreenshotEvent.SCREENSHOT_LONG_SCREENSHOT_SAVED);
            startExport(PendingAction.SAVE);
        } else if (id == R$id.edit) {
            this.mUiEventLogger.log(ScreenshotEvent.SCREENSHOT_LONG_SCREENSHOT_EDIT);
            startExport(PendingAction.EDIT);
        } else if (id == R$id.share) {
            this.mUiEventLogger.log(ScreenshotEvent.SCREENSHOT_LONG_SCREENSHOT_SHARE);
            startExport(PendingAction.SHARE);
        } else if (id == R$id.cancel) {
            this.mUiEventLogger.log(ScreenshotEvent.SCREENSHOT_LONG_SCREENSHOT_EXIT);
            finishAndRemoveTask();
        }
    }

    @Override // android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R$layout.long_screenshot);
        this.mPreview = (ImageView) requireViewById(R$id.preview);
        this.mSave = requireViewById(R$id.save);
        this.mEdit = requireViewById(R$id.edit);
        this.mShare = requireViewById(R$id.share);
        this.mCancel = requireViewById(R$id.cancel);
        this.mCropView = (CropView) requireViewById(R$id.crop_view);
        MagnifierView magnifierView = (MagnifierView) requireViewById(R$id.magnifier);
        this.mMagnifierView = magnifierView;
        this.mCropView.setCropInteractionListener(magnifierView);
        this.mTransitionView = (ImageView) requireViewById(R$id.transition);
        this.mEnterTransitionView = (ImageView) requireViewById(R$id.enter_transition);
        this.mSave.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.screenshot.LongScreenshotActivity$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                LongScreenshotActivity.$r8$lambda$qOUpLbBDnQlAC3CzBI4dsyuYNs4(LongScreenshotActivity.this, view);
            }
        });
        this.mCancel.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.screenshot.LongScreenshotActivity$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                LongScreenshotActivity.$r8$lambda$qOUpLbBDnQlAC3CzBI4dsyuYNs4(LongScreenshotActivity.this, view);
            }
        });
        this.mEdit.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.screenshot.LongScreenshotActivity$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                LongScreenshotActivity.$r8$lambda$qOUpLbBDnQlAC3CzBI4dsyuYNs4(LongScreenshotActivity.this, view);
            }
        });
        this.mShare.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.screenshot.LongScreenshotActivity$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                LongScreenshotActivity.$r8$lambda$qOUpLbBDnQlAC3CzBI4dsyuYNs4(LongScreenshotActivity.this, view);
            }
        });
        this.mPreview.addOnLayoutChangeListener(new View.OnLayoutChangeListener() { // from class: com.android.systemui.screenshot.LongScreenshotActivity$$ExternalSyntheticLambda2
            @Override // android.view.View.OnLayoutChangeListener
            public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                LongScreenshotActivity.$r8$lambda$MYFi5wKU3CkJeeDGZ8VeNuDOoFA(LongScreenshotActivity.this, view, i, i2, i3, i4, i5, i6, i7, i8);
            }
        });
        Intent intent = getIntent();
        this.mScrollCaptureResponse = intent.getParcelableExtra("capture-response");
        UserHandle userHandle = (UserHandle) intent.getParcelableExtra("screenshot-userhandle", UserHandle.class);
        this.mScreenshotUserHandle = userHandle;
        if (userHandle == null) {
            this.mScreenshotUserHandle = Process.myUserHandle();
        }
        if (bundle != null) {
            String string = bundle.getString("saved-image-path");
            if (string == null) {
                Log.e(TAG, "Missing saved state entry with key 'saved-image-path'!");
                finishAndRemoveTask();
                return;
            }
            this.mSavedImagePath = new File(string);
            this.mCacheLoadFuture = new ImageLoader(getContentResolver()).load(this.mSavedImagePath);
        }
    }

    /* renamed from: onExportCompleted */
    public final void lambda$startExport$3(PendingAction pendingAction, ListenableFuture<ImageExporter.Result> listenableFuture) {
        setButtonsEnabled(true);
        try {
            ImageExporter.Result result = (ImageExporter.Result) listenableFuture.get();
            int i = AnonymousClass2.$SwitchMap$com$android$systemui$screenshot$LongScreenshotActivity$PendingAction[pendingAction.ordinal()];
            if (i == 1) {
                doEdit(result.uri);
            } else if (i == 2) {
                doShare(result.uri);
            } else if (i != 3) {
            } else {
                finishAndRemoveTask();
            }
        } catch (InterruptedException | CancellationException | ExecutionException e) {
            Log.e(TAG, "failed to export", e);
        }
    }

    public final void onLongScreenshotReceived(ScrollCaptureController.LongScreenshot longScreenshot) {
        String str = TAG;
        Log.i(str, "Completed: " + longScreenshot);
        this.mLongScreenshot = longScreenshot;
        Drawable drawable = longScreenshot.getDrawable();
        this.mPreview.setImageDrawable(drawable);
        this.mMagnifierView.setDrawable(this.mLongScreenshot.getDrawable(), this.mLongScreenshot.getWidth(), this.mLongScreenshot.getHeight());
        float max = Math.max((float) ActionBarShadowController.ELEVATION_LOW, (-this.mLongScreenshot.getTop()) / this.mLongScreenshot.getHeight());
        float min = Math.min(1.0f, 1.0f - ((this.mLongScreenshot.getBottom() - this.mLongScreenshot.getPageHeight()) / this.mLongScreenshot.getHeight()));
        this.mEnterTransitionView.setImageDrawable(drawable);
        this.mEnterTransitionView.getViewTreeObserver().addOnPreDrawListener(new AnonymousClass1(max, min));
        ListenableFuture<File> exportToRawFile = this.mImageExporter.exportToRawFile(this.mBackgroundExecutor, this.mLongScreenshot.toBitmap(), new File(getCacheDir(), "long_screenshot_cache.png"));
        this.mCacheSaveFuture = exportToRawFile;
        exportToRawFile.addListener(new Runnable() { // from class: com.android.systemui.screenshot.LongScreenshotActivity$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                LongScreenshotActivity.$r8$lambda$_ZlZSXMYcGJIm8arvK1OF2XBF6s(LongScreenshotActivity.this);
            }
        }, this.mUiExecutor);
    }

    @Override // android.app.Activity
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        File file = this.mSavedImagePath;
        if (file != null) {
            bundle.putString("saved-image-path", file.getPath());
        }
    }

    @Override // android.app.Activity
    public void onStart() {
        super.onStart();
        this.mUiEventLogger.log(ScreenshotEvent.SCREENSHOT_LONG_SCREENSHOT_ACTIVITY_STARTED);
        if (this.mPreview.getDrawable() != null) {
            return;
        }
        if (this.mCacheLoadFuture != null) {
            Log.d(TAG, "mCacheLoadFuture != null");
            final ListenableFuture<ImageLoader.Result> listenableFuture = this.mCacheLoadFuture;
            listenableFuture.addListener(new Runnable() { // from class: com.android.systemui.screenshot.LongScreenshotActivity$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    LongScreenshotActivity.$r8$lambda$Ise6YOW3HWrs4du3NMRkxL3Oqj8(LongScreenshotActivity.this, listenableFuture);
                }
            }, this.mUiExecutor);
            this.mCacheLoadFuture = null;
            return;
        }
        ScrollCaptureController.LongScreenshot takeLongScreenshot = this.mLongScreenshotHolder.takeLongScreenshot();
        setMagnification(this.mLongScreenshotHolder.getNeedsMagnification());
        if (takeLongScreenshot != null) {
            onLongScreenshotReceived(takeLongScreenshot);
            return;
        }
        Log.e(TAG, "No long screenshot available!");
        finishAndRemoveTask();
    }

    @Override // android.app.Activity
    public void onStop() {
        super.onStop();
        if (this.mTransitionStarted) {
            finish();
        }
        if (isFinishing()) {
            this.mUiEventLogger.log(ScreenshotEvent.SCREENSHOT_LONG_SCREENSHOT_ACTIVITY_FINISHED);
            ScrollCaptureResponse scrollCaptureResponse = this.mScrollCaptureResponse;
            if (scrollCaptureResponse != null) {
                scrollCaptureResponse.close();
            }
            cleanupCache();
            ScrollCaptureController.LongScreenshot longScreenshot = this.mLongScreenshot;
            if (longScreenshot != null) {
                longScreenshot.release();
            }
        }
    }

    public final void setButtonsEnabled(boolean z) {
        this.mSave.setEnabled(z);
        this.mEdit.setEnabled(z);
        this.mShare.setEnabled(z);
    }

    public final void setMagnification(boolean z) {
        if (z) {
            this.mCropView.setCropInteractionListener(this.mMagnifierView);
        } else {
            this.mCropView.setCropInteractionListener(null);
        }
    }

    public final void startExport(final PendingAction pendingAction) {
        Drawable drawable = this.mPreview.getDrawable();
        if (drawable == null) {
            Log.e(TAG, "No drawable, skipping export!");
            return;
        }
        Rect cropBoundaries = this.mCropView.getCropBoundaries(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        if (cropBoundaries.isEmpty()) {
            Log.w(TAG, "Crop bounds empty, skipping export.");
            return;
        }
        updateImageDimensions();
        this.mOutputBitmap = renderBitmap(drawable, cropBoundaries);
        final ListenableFuture<ImageExporter.Result> export = this.mImageExporter.export(this.mBackgroundExecutor, UUID.randomUUID(), this.mOutputBitmap, ZonedDateTime.now(), this.mFeatureFlags.isEnabled(Flags.SCREENSHOT_WORK_PROFILE_POLICY) ? this.mScreenshotUserHandle : Process.myUserHandle(), this.mLongScreenshotHolder.getForegroundAppName());
        export.addListener(new Runnable() { // from class: com.android.systemui.screenshot.LongScreenshotActivity$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                LongScreenshotActivity.m4235$r8$lambda$k5xu7vF8LkGJAloB_x6IZSwV0(LongScreenshotActivity.this, pendingAction, export);
            }
        }, this.mUiExecutor);
    }

    public final void updateImageDimensions() {
        float intrinsicHeight;
        Drawable drawable = this.mPreview.getDrawable();
        if (drawable == null) {
            return;
        }
        Rect bounds = drawable.getBounds();
        float width = bounds.width() / bounds.height();
        int width2 = (this.mPreview.getWidth() - this.mPreview.getPaddingLeft()) - this.mPreview.getPaddingRight();
        int height = (this.mPreview.getHeight() - this.mPreview.getPaddingTop()) - this.mPreview.getPaddingBottom();
        float f = width2;
        float f2 = height;
        float f3 = f / f2;
        int paddingLeft = this.mPreview.getPaddingLeft();
        int paddingTop = this.mPreview.getPaddingTop();
        int i = 0;
        if (width > f3) {
            int i2 = (int) ((f3 * f2) / width);
            i = (height - i2) / 2;
            this.mCropView.setExtraPadding(this.mPreview.getPaddingTop() + i, this.mPreview.getPaddingBottom() + i);
            paddingTop += i;
            this.mCropView.setImageWidth(width2);
            intrinsicHeight = f / this.mPreview.getDrawable().getIntrinsicWidth();
            height = i2;
        } else {
            int i3 = (int) ((f * width) / f3);
            paddingLeft += (width2 - i3) / 2;
            this.mCropView.setExtraPadding(this.mPreview.getPaddingTop(), this.mPreview.getPaddingBottom());
            this.mCropView.setImageWidth((int) (width * f2));
            intrinsicHeight = f2 / this.mPreview.getDrawable().getIntrinsicHeight();
            width2 = i3;
        }
        Rect cropBoundaries = this.mCropView.getCropBoundaries(width2, height);
        this.mTransitionView.setTranslationX(paddingLeft + cropBoundaries.left);
        this.mTransitionView.setTranslationY(paddingTop + cropBoundaries.top);
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) this.mTransitionView.getLayoutParams();
        ((ViewGroup.MarginLayoutParams) layoutParams).width = cropBoundaries.width();
        ((ViewGroup.MarginLayoutParams) layoutParams).height = cropBoundaries.height();
        this.mTransitionView.setLayoutParams(layoutParams);
        if (this.mLongScreenshot != null) {
            ConstraintLayout.LayoutParams layoutParams2 = (ConstraintLayout.LayoutParams) this.mEnterTransitionView.getLayoutParams();
            float max = Math.max((float) ActionBarShadowController.ELEVATION_LOW, (-this.mLongScreenshot.getTop()) / this.mLongScreenshot.getHeight());
            ((ViewGroup.MarginLayoutParams) layoutParams2).width = (int) (drawable.getIntrinsicWidth() * intrinsicHeight);
            ((ViewGroup.MarginLayoutParams) layoutParams2).height = (int) (this.mLongScreenshot.getPageHeight() * intrinsicHeight);
            this.mEnterTransitionView.setLayoutParams(layoutParams2);
            Matrix matrix = new Matrix();
            matrix.setScale(intrinsicHeight, intrinsicHeight);
            matrix.postTranslate(ActionBarShadowController.ELEVATION_LOW, (-intrinsicHeight) * drawable.getIntrinsicHeight() * max);
            this.mEnterTransitionView.setImageMatrix(matrix);
            this.mEnterTransitionView.setTranslationY((max * f2) + this.mPreview.getPaddingTop() + i);
        }
    }
}