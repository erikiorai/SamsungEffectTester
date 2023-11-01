package com.android.app.viewcapture;

import android.content.res.Resources;
import android.media.permission.SafeCloseable;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Trace;
import android.util.SparseArray;
import android.view.Choreographer;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import androidx.recyclerview.widget.RecyclerView;
import com.android.app.viewcapture.ViewCapture;
import com.android.app.viewcapture.data.nano.ExportedData;
import com.android.app.viewcapture.data.nano.FrameData;
import com.android.app.viewcapture.data.nano.ViewNode;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.FutureTask;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Predicate;

/* loaded from: mainsysui33.jar:com/android/app/viewcapture/ViewCapture.class */
public class ViewCapture {
    public static ViewCapture INSTANCE;
    public static final LooperExecutor MAIN_EXECUTOR = new LooperExecutor(Looper.getMainLooper());
    public final Executor mExecutor;
    public final int mMemorySize;
    public final List<WindowListener> mListeners = new ArrayList();
    public ViewRef mPool = new ViewRef();

    /* loaded from: mainsysui33.jar:com/android/app/viewcapture/ViewCapture$ViewIdProvider.class */
    public static final class ViewIdProvider {
        public final SparseArray<String> mNames = new SparseArray<>();
        public final Resources mRes;

        public ViewIdProvider(Resources resources) {
            this.mRes = resources;
        }

        public String getName(int i) {
            String str = this.mNames.get(i);
            String str2 = str;
            if (str == null) {
                if (i >= 0) {
                    try {
                        str2 = this.mRes.getResourceTypeName(i) + '/' + this.mRes.getResourceEntryName(i);
                    } catch (Resources.NotFoundException e) {
                        str2 = "id/0x" + Integer.toHexString(i).toUpperCase();
                    }
                } else {
                    str2 = "NO_ID";
                }
                this.mNames.put(i, str2);
            }
            return str2;
        }
    }

    /* loaded from: mainsysui33.jar:com/android/app/viewcapture/ViewCapture$ViewPropertyRef.class */
    public static class ViewPropertyRef {
        public float alpha;
        public int bottom;
        public int childCount;
        public Class clazz;
        public boolean clipChildren;
        public float elevation;
        public int hashCode;
        public int id;
        public int left;
        public ViewPropertyRef next;
        public int right;
        public float scaleX;
        public float scaleY;
        public int scrollX;
        public int scrollY;
        public int top;
        public float translateX;
        public float translateY;
        public int visibility;
        public boolean willNotDraw;

        public ViewPropertyRef() {
            this.childCount = 0;
        }

        public ViewPropertyRef toProto(ViewIdProvider viewIdProvider, ArrayList<Class> arrayList, ViewNode viewNode) {
            int indexOf = arrayList.indexOf(this.clazz);
            int i = indexOf;
            if (indexOf < 0) {
                i = arrayList.size();
                arrayList.add(this.clazz);
            }
            viewNode.classnameIndex = i;
            viewNode.hashcode = this.hashCode;
            viewNode.id = viewIdProvider.getName(this.id);
            int i2 = this.left;
            viewNode.left = i2;
            int i3 = this.top;
            viewNode.top = i3;
            viewNode.width = this.right - i2;
            viewNode.height = this.bottom - i3;
            viewNode.translationX = this.translateX;
            viewNode.translationY = this.translateY;
            viewNode.scaleX = this.scaleX;
            viewNode.scaleY = this.scaleY;
            viewNode.alpha = this.alpha;
            viewNode.visibility = this.visibility;
            viewNode.willNotDraw = this.willNotDraw;
            viewNode.elevation = this.elevation;
            viewNode.clipChildren = this.clipChildren;
            ViewPropertyRef viewPropertyRef = this.next;
            viewNode.children = new ViewNode[this.childCount];
            for (int i4 = 0; i4 < this.childCount && viewPropertyRef != null; i4++) {
                ViewNode viewNode2 = new ViewNode();
                viewPropertyRef = viewPropertyRef.toProto(viewIdProvider, arrayList, viewNode2);
                viewNode.children[i4] = viewNode2;
            }
            return viewPropertyRef;
        }

        public void transferTo(ViewPropertyRef viewPropertyRef) {
            viewPropertyRef.clazz = this.clazz;
            viewPropertyRef.hashCode = this.hashCode;
            viewPropertyRef.childCount = this.childCount;
            viewPropertyRef.id = this.id;
            viewPropertyRef.left = this.left;
            viewPropertyRef.top = this.top;
            viewPropertyRef.right = this.right;
            viewPropertyRef.bottom = this.bottom;
            viewPropertyRef.scrollX = this.scrollX;
            viewPropertyRef.scrollY = this.scrollY;
            viewPropertyRef.scaleX = this.scaleX;
            viewPropertyRef.scaleY = this.scaleY;
            viewPropertyRef.translateX = this.translateX;
            viewPropertyRef.translateY = this.translateY;
            viewPropertyRef.alpha = this.alpha;
            viewPropertyRef.visibility = this.visibility;
            viewPropertyRef.willNotDraw = this.willNotDraw;
            viewPropertyRef.clipChildren = this.clipChildren;
            viewPropertyRef.elevation = this.elevation;
        }
    }

    /* loaded from: mainsysui33.jar:com/android/app/viewcapture/ViewCapture$ViewRef.class */
    public static class ViewRef implements Runnable {
        public Consumer<ViewRef> callback;
        public int childCount;
        public long choreographerTimeNanos;
        public ViewRef next;
        public View view;

        public ViewRef() {
            this.childCount = 0;
            this.callback = null;
            this.choreographerTimeNanos = 0L;
        }

        @Override // java.lang.Runnable
        public void run() {
            Consumer<ViewRef> consumer = this.callback;
            this.callback = null;
            if (consumer != null) {
                consumer.accept(this);
            }
        }

        public void transferTo(ViewPropertyRef viewPropertyRef) {
            viewPropertyRef.childCount = this.childCount;
            View view = this.view;
            this.view = null;
            viewPropertyRef.clazz = view.getClass();
            viewPropertyRef.hashCode = view.hashCode();
            viewPropertyRef.id = view.getId();
            viewPropertyRef.left = view.getLeft();
            viewPropertyRef.top = view.getTop();
            viewPropertyRef.right = view.getRight();
            viewPropertyRef.bottom = view.getBottom();
            viewPropertyRef.scrollX = view.getScrollX();
            viewPropertyRef.scrollY = view.getScrollY();
            viewPropertyRef.translateX = view.getTranslationX();
            viewPropertyRef.translateY = view.getTranslationY();
            viewPropertyRef.scaleX = view.getScaleX();
            viewPropertyRef.scaleY = view.getScaleY();
            viewPropertyRef.alpha = view.getAlpha();
            viewPropertyRef.elevation = view.getElevation();
            viewPropertyRef.visibility = view.getVisibility();
            viewPropertyRef.willNotDraw = view.willNotDraw();
        }
    }

    /* loaded from: mainsysui33.jar:com/android/app/viewcapture/ViewCapture$WindowListener.class */
    public class WindowListener implements ViewTreeObserver.OnDrawListener {
        public Choreographer mChoreographer;
        public final long[] mFrameTimesNanosBg;
        public final ViewPropertyRef[] mNodesBg;
        public final View mRoot;
        public final String name;
        public final ViewRef mViewRef = new ViewRef();
        public int mFrameIndexBg = -1;
        public boolean mIsFirstFrame = true;
        public boolean mDestroyed = false;
        public final Consumer<ViewRef> mCaptureCallback = new Consumer() { // from class: com.android.app.viewcapture.ViewCapture$WindowListener$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ViewCapture.WindowListener.m507$r8$lambda$zuRHAm3V72IcKVlLLUsduOw4Y(ViewCapture.WindowListener.this, (ViewCapture.ViewRef) obj);
            }
        };

        /* JADX DEBUG: Method not inlined, still used in: [com.android.app.viewcapture.ViewCapture$WindowListener$$ExternalSyntheticLambda2.run():void] */
        /* renamed from: $r8$lambda$Q9s4gOw1mFJaB7XH-enGW0_1H7I */
        public static /* synthetic */ void m506$r8$lambda$Q9s4gOw1mFJaB7XHenGW0_1H7I(WindowListener windowListener, ViewRef viewRef, ViewRef viewRef2) {
            windowListener.lambda$captureViewPropertiesBg$0(viewRef, viewRef2);
        }

        /* JADX DEBUG: Method not inlined, still used in: [com.android.app.viewcapture.ViewCapture$WindowListener$$ExternalSyntheticLambda4.apply(int):java.lang.Object] */
        public static /* synthetic */ String[] $r8$lambda$tb_g3XTu2fxNrDjpg8dBcNqpP3A(int i) {
            return lambda$dumpToProto$1(i);
        }

        /* JADX DEBUG: Method not inlined, still used in: [com.android.app.viewcapture.ViewCapture$WindowListener$$ExternalSyntheticLambda0.accept(java.lang.Object):void] */
        /* renamed from: $r8$lambda$zuRHA-m3V-72IcKVlLLUsduOw4Y */
        public static /* synthetic */ void m507$r8$lambda$zuRHAm3V72IcKVlLLUsduOw4Y(WindowListener windowListener, ViewRef viewRef) {
            windowListener.captureViewPropertiesBg(viewRef);
        }

        /* JADX DEBUG: Method not inlined, still used in: [com.android.app.viewcapture.ViewCapture.$r8$lambda$b9kVkp7yVCd5vIxeY4oE6zu710c(com.android.app.viewcapture.ViewCapture$WindowListener, com.android.app.viewcapture.ViewCapture$ViewIdProvider):com.android.app.viewcapture.data.nano.ExportedData, com.android.app.viewcapture.ViewCapture.$r8$lambda$b9kVkp7yVCd5vIxeY4oE6zu710c(com.android.app.viewcapture.ViewCapture$WindowListener, com.android.app.viewcapture.ViewCapture$ViewIdProvider):com.android.app.viewcapture.data.nano.ExportedData] */
        /* renamed from: -$$Nest$mdumpToProto */
        public static /* bridge */ /* synthetic */ ExportedData m509$$Nest$mdumpToProto(WindowListener windowListener, ViewIdProvider viewIdProvider) {
            return windowListener.dumpToProto(viewIdProvider);
        }

        public WindowListener(View view, String str) {
            ViewCapture.this = r6;
            this.mFrameTimesNanosBg = new long[r6.mMemorySize];
            this.mNodesBg = new ViewPropertyRef[r6.mMemorySize];
            try {
                this.mChoreographer = (Choreographer) ViewCapture.MAIN_EXECUTOR.submit(new Callable() { // from class: com.android.app.viewcapture.ViewCapture$WindowListener$$ExternalSyntheticLambda1
                    @Override // java.util.concurrent.Callable
                    public final Object call() {
                        return Choreographer.getInstance();
                    }
                }).get();
                this.mRoot = view;
                this.name = str;
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        }

        public /* synthetic */ void lambda$captureViewPropertiesBg$0(ViewRef viewRef, ViewRef viewRef2) {
            ViewCapture.this.lambda$initPool$2(viewRef, viewRef2);
        }

        public static /* synthetic */ String[] lambda$dumpToProto$1(int i) {
            return new String[i];
        }

        public void attachToRoot() {
            if (this.mRoot.isAttachedToWindow()) {
                this.mRoot.getViewTreeObserver().addOnDrawListener(this);
            } else {
                this.mRoot.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() { // from class: com.android.app.viewcapture.ViewCapture.WindowListener.1
                    {
                        WindowListener.this = this;
                    }

                    @Override // android.view.View.OnAttachStateChangeListener
                    public void onViewAttachedToWindow(View view) {
                        if (!WindowListener.this.mDestroyed) {
                            WindowListener.this.mRoot.getViewTreeObserver().addOnDrawListener(WindowListener.this);
                        }
                        WindowListener.this.mRoot.removeOnAttachStateChangeListener(this);
                    }

                    @Override // android.view.View.OnAttachStateChangeListener
                    public void onViewDetachedFromWindow(View view) {
                    }
                });
            }
        }

        public final void captureViewPropertiesBg(final ViewRef viewRef) {
            ViewPropertyRef viewPropertyRef;
            ViewPropertyRef viewPropertyRef2;
            ViewPropertyRef viewPropertyRef3;
            ViewPropertyRef viewPropertyRef4;
            long j = viewRef.choreographerTimeNanos;
            int i = this.mFrameIndexBg + 1;
            this.mFrameIndexBg = i;
            if (i >= ViewCapture.this.mMemorySize) {
                this.mFrameIndexBg = 0;
            }
            long[] jArr = this.mFrameTimesNanosBg;
            int i2 = this.mFrameIndexBg;
            jArr[i2] = j;
            ViewPropertyRef viewPropertyRef5 = this.mNodesBg[i2];
            ViewRef viewRef2 = viewRef;
            ViewPropertyRef viewPropertyRef6 = null;
            ViewPropertyRef viewPropertyRef7 = null;
            while (true) {
                ViewPropertyRef viewPropertyRef8 = viewPropertyRef7;
                viewPropertyRef = viewPropertyRef6;
                if (viewRef2 == null) {
                    break;
                }
                if (viewPropertyRef5 == null) {
                    viewPropertyRef2 = viewPropertyRef5;
                    viewPropertyRef5 = new ViewPropertyRef();
                } else {
                    viewPropertyRef2 = viewPropertyRef5.next;
                    viewPropertyRef5.next = null;
                }
                if (viewRef2.childCount < 0) {
                    viewPropertyRef3 = findInLastFrame(viewRef2.view.hashCode());
                    viewRef2.childCount = viewPropertyRef3 != null ? viewPropertyRef3.childCount : 0;
                } else {
                    viewPropertyRef3 = null;
                }
                viewRef2.transferTo(viewPropertyRef5);
                if (viewPropertyRef6 == null) {
                    viewPropertyRef6 = viewPropertyRef5;
                } else {
                    viewPropertyRef8.next = viewPropertyRef5;
                }
                ViewPropertyRef viewPropertyRef9 = viewPropertyRef5;
                ViewPropertyRef viewPropertyRef10 = viewPropertyRef2;
                if (viewPropertyRef3 != null) {
                    int i3 = viewPropertyRef3.childCount;
                    while (true) {
                        viewPropertyRef9 = viewPropertyRef5;
                        viewPropertyRef10 = viewPropertyRef2;
                        if (i3 <= 0) {
                            break;
                        }
                        viewPropertyRef3 = viewPropertyRef3.next;
                        i3 = (i3 - 1) + viewPropertyRef3.childCount;
                        if (viewPropertyRef2 == null) {
                            viewPropertyRef4 = viewPropertyRef2;
                            viewPropertyRef2 = new ViewPropertyRef();
                        } else {
                            viewPropertyRef4 = viewPropertyRef2.next;
                            viewPropertyRef2.next = null;
                        }
                        viewPropertyRef3.transferTo(viewPropertyRef2);
                        viewPropertyRef5.next = viewPropertyRef2;
                        viewPropertyRef5 = viewPropertyRef2;
                        viewPropertyRef2 = viewPropertyRef4;
                    }
                }
                viewPropertyRef5 = viewPropertyRef10;
                ViewRef viewRef3 = viewRef2.next;
                if (viewRef3 == null) {
                    final ViewRef viewRef4 = viewRef2;
                    ViewCapture.MAIN_EXECUTOR.execute(new Runnable() { // from class: com.android.app.viewcapture.ViewCapture$WindowListener$$ExternalSyntheticLambda2
                        @Override // java.lang.Runnable
                        public final void run() {
                            ViewCapture.WindowListener.m506$r8$lambda$Q9s4gOw1mFJaB7XHenGW0_1H7I(ViewCapture.WindowListener.this, viewRef, viewRef4);
                        }
                    });
                    viewPropertyRef = viewPropertyRef6;
                    break;
                }
                viewRef2 = viewRef3;
                viewPropertyRef7 = viewPropertyRef9;
            }
            this.mNodesBg[this.mFrameIndexBg] = viewPropertyRef;
        }

        public final ViewRef captureViewTree(View view, ViewRef viewRef) {
            ViewRef viewRef2;
            if (ViewCapture.this.mPool != null) {
                viewRef2 = ViewCapture.this.mPool;
                ViewCapture viewCapture = ViewCapture.this;
                viewCapture.mPool = viewCapture.mPool.next;
                viewRef2.next = null;
            } else {
                viewRef2 = new ViewRef();
            }
            viewRef2.view = view;
            viewRef.next = viewRef2;
            if (!(view instanceof ViewGroup)) {
                viewRef2.childCount = 0;
                return viewRef2;
            }
            ViewGroup viewGroup = (ViewGroup) view;
            if ((view.mPrivateFlags & (-2145386496)) == 0 && !this.mIsFirstFrame) {
                viewRef2.childCount = -viewGroup.getChildCount();
                return viewRef2;
            }
            int childCount = viewGroup.getChildCount();
            viewRef2.childCount = childCount;
            for (int i = 0; i < childCount; i++) {
                viewRef2 = captureViewTree(viewGroup.getChildAt(i), viewRef2);
            }
            return viewRef2;
        }

        public void destroy() {
            this.mRoot.getViewTreeObserver().removeOnDrawListener(this);
            this.mDestroyed = true;
        }

        public final ExportedData dumpToProto(ViewIdProvider viewIdProvider) {
            int i = this.mNodesBg[ViewCapture.this.mMemorySize - 1] == null ? this.mFrameIndexBg + 1 : ViewCapture.this.mMemorySize;
            ExportedData exportedData = new ExportedData();
            exportedData.frameData = new FrameData[i];
            ArrayList<Class> arrayList = new ArrayList<>();
            for (int i2 = i - 1; i2 >= 0; i2--) {
                int i3 = ((ViewCapture.this.mMemorySize + this.mFrameIndexBg) - i2) % ViewCapture.this.mMemorySize;
                ViewNode viewNode = new ViewNode();
                this.mNodesBg[i3].toProto(viewIdProvider, arrayList, viewNode);
                FrameData frameData = new FrameData();
                frameData.node = viewNode;
                frameData.timestamp = this.mFrameTimesNanosBg[i3];
                exportedData.frameData[(i - i2) - 1] = frameData;
            }
            exportedData.classname = (String[]) arrayList.stream().map(new ViewCapture$WindowListener$$ExternalSyntheticLambda3()).toArray(new IntFunction() { // from class: com.android.app.viewcapture.ViewCapture$WindowListener$$ExternalSyntheticLambda4
                @Override // java.util.function.IntFunction
                public final Object apply(int i4) {
                    return ViewCapture.WindowListener.$r8$lambda$tb_g3XTu2fxNrDjpg8dBcNqpP3A(i4);
                }
            });
            return exportedData;
        }

        public final ViewPropertyRef findInLastFrame(int i) {
            ViewPropertyRef viewPropertyRef;
            int i2 = this.mFrameIndexBg;
            int i3 = i2;
            if (i2 == 0) {
                i3 = ViewCapture.this.mMemorySize;
            }
            ViewPropertyRef viewPropertyRef2 = this.mNodesBg[i3 - 1];
            while (true) {
                viewPropertyRef = viewPropertyRef2;
                if (viewPropertyRef == null || viewPropertyRef.hashCode == i) {
                    break;
                }
                viewPropertyRef2 = viewPropertyRef.next;
            }
            return viewPropertyRef;
        }

        @Override // android.view.ViewTreeObserver.OnDrawListener
        public void onDraw() {
            Trace.beginSection("view_capture");
            captureViewTree(this.mRoot, this.mViewRef);
            ViewRef viewRef = this.mViewRef.next;
            if (viewRef != null) {
                viewRef.callback = this.mCaptureCallback;
                viewRef.choreographerTimeNanos = this.mChoreographer.getFrameTimeNanos();
                ViewCapture.this.mExecutor.execute(viewRef);
            }
            this.mIsFirstFrame = false;
            Trace.endSection();
        }
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.app.viewcapture.ViewCapture$$ExternalSyntheticLambda2.apply(java.lang.Object):java.lang.Object] */
    /* renamed from: $r8$lambda$1g4YnBNstZ-mqwhgsJ6EmF_4ZEs */
    public static /* synthetic */ FutureTask m496$r8$lambda$1g4YnBNstZmqwhgsJ6EmF_4ZEs(ViewCapture viewCapture, ViewIdProvider viewIdProvider, WindowListener windowListener) {
        return viewCapture.lambda$getDumpTask$10(viewIdProvider, windowListener);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.app.viewcapture.ViewCapture$$ExternalSyntheticLambda6.run():void] */
    public static /* synthetic */ void $r8$lambda$7PnWDshFOaHG0nt2EUtEzS9sbno(ViewCapture viewCapture, int i) {
        viewCapture.lambda$new$1(i);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.app.viewcapture.ViewCapture$$ExternalSyntheticLambda1.test(java.lang.Object):boolean] */
    public static /* synthetic */ boolean $r8$lambda$DhaAs1YR9vuhk1ZKOsHiJ2aHm3A(View view, WindowListener windowListener) {
        return lambda$getDumpTask$8(view, windowListener);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.app.viewcapture.ViewCapture$$ExternalSyntheticLambda5.close():void] */
    public static /* synthetic */ void $r8$lambda$GDUOfkr8VMufJ13z3HC7hvut03s(ViewCapture viewCapture, WindowListener windowListener) {
        viewCapture.lambda$startCapture$4(windowListener);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.app.viewcapture.ViewCapture$$ExternalSyntheticLambda0.call():java.lang.Object] */
    /* renamed from: $r8$lambda$L8hGvlt-tCKs2vaoaSVs8zG58Vo */
    public static /* synthetic */ ViewCapture m497$r8$lambda$L8hGvlttCKs2vaoaSVs8zG58Vo(boolean z, int i, int i2) {
        return getInstance(z, i, i2);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.app.viewcapture.ViewCapture$$ExternalSyntheticLambda8.run():void] */
    /* renamed from: $r8$lambda$NzKcLYQ7-vUqtbFhK4jrCiFLzWo */
    public static /* synthetic */ void m498$r8$lambda$NzKcLYQ7vUqtbFhK4jrCiFLzWo(ViewCapture viewCapture, ViewRef viewRef, ViewRef viewRef2) {
        viewCapture.lambda$initPool$2(viewRef, viewRef2);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.app.viewcapture.ViewCapture$$ExternalSyntheticLambda3.call():java.lang.Object] */
    public static /* synthetic */ ExportedData $r8$lambda$b9kVkp7yVCd5vIxeY4oE6zu710c(WindowListener windowListener, ViewIdProvider viewIdProvider) {
        return WindowListener.m509$$Nest$mdumpToProto(windowListener, viewIdProvider);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.app.viewcapture.ViewCapture$$ExternalSyntheticLambda4.run():void] */
    /* renamed from: $r8$lambda$fKrj8qUyma1OiIArU-fAQHBaCCo */
    public static /* synthetic */ void m499$r8$lambda$fKrj8qUyma1OiIArUfAQHBaCCo(WindowListener windowListener) {
        lambda$startCapture$3(windowListener);
    }

    public ViewCapture(boolean z, int i, final int i2) {
        this.mMemorySize = i;
        if (z) {
            this.mExecutor = createAndStartNewLooperExecutor("ViewCapture", -2);
        } else {
            this.mExecutor = MAIN_EXECUTOR;
        }
        this.mExecutor.execute(new Runnable() { // from class: com.android.app.viewcapture.ViewCapture$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                ViewCapture.$r8$lambda$7PnWDshFOaHG0nt2EUtEzS9sbno(ViewCapture.this, i2);
            }
        });
    }

    public static LooperExecutor createAndStartNewLooperExecutor(String str, int i) {
        HandlerThread handlerThread = new HandlerThread(str, i);
        handlerThread.start();
        return new LooperExecutor(handlerThread.getLooper());
    }

    public static ViewCapture getInstance() {
        return getInstance(true, RecyclerView.MAX_SCROLL_DURATION, 300);
    }

    public static ViewCapture getInstance(final boolean z, final int i, final int i2) {
        if (INSTANCE == null) {
            if (Looper.myLooper() != Looper.getMainLooper()) {
                try {
                    return (ViewCapture) MAIN_EXECUTOR.submit(new Callable() { // from class: com.android.app.viewcapture.ViewCapture$$ExternalSyntheticLambda0
                        @Override // java.util.concurrent.Callable
                        public final Object call() {
                            return ViewCapture.m497$r8$lambda$L8hGvlttCKs2vaoaSVs8zG58Vo(z, i, i2);
                        }
                    }).get();
                } catch (InterruptedException | ExecutionException e) {
                    throw new RuntimeException(e);
                }
            }
            INSTANCE = new ViewCapture(z, i, i2);
        }
        return INSTANCE;
    }

    public /* synthetic */ FutureTask lambda$getDumpTask$10(final ViewIdProvider viewIdProvider, final WindowListener windowListener) {
        FutureTask futureTask = new FutureTask(new Callable() { // from class: com.android.app.viewcapture.ViewCapture$$ExternalSyntheticLambda3
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return ViewCapture.$r8$lambda$b9kVkp7yVCd5vIxeY4oE6zu710c(ViewCapture.WindowListener.this, viewIdProvider);
            }
        });
        this.mExecutor.execute(futureTask);
        return futureTask;
    }

    public static /* synthetic */ boolean lambda$getDumpTask$8(View view, WindowListener windowListener) {
        return windowListener.mRoot.equals(view);
    }

    public static /* synthetic */ void lambda$startCapture$3(final WindowListener windowListener) {
        LooperExecutor looperExecutor = MAIN_EXECUTOR;
        Objects.requireNonNull(windowListener);
        looperExecutor.execute(new Runnable() { // from class: com.android.app.viewcapture.ViewCapture$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                ViewCapture.WindowListener.this.attachToRoot();
            }
        });
    }

    public /* synthetic */ void lambda$startCapture$4(WindowListener windowListener) {
        this.mListeners.remove(windowListener);
        windowListener.destroy();
    }

    /* renamed from: addToPool */
    public final void lambda$initPool$2(ViewRef viewRef, ViewRef viewRef2) {
        viewRef2.next = this.mPool;
        this.mPool = viewRef;
    }

    public Optional<FutureTask<ExportedData>> getDumpTask(final View view) {
        final ViewIdProvider viewIdProvider = new ViewIdProvider(view.getContext().getApplicationContext().getResources());
        return this.mListeners.stream().filter(new Predicate() { // from class: com.android.app.viewcapture.ViewCapture$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ViewCapture.$r8$lambda$DhaAs1YR9vuhk1ZKOsHiJ2aHm3A(view, (ViewCapture.WindowListener) obj);
            }
        }).map(new Function() { // from class: com.android.app.viewcapture.ViewCapture$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ViewCapture.m496$r8$lambda$1g4YnBNstZmqwhgsJ6EmF_4ZEs(ViewCapture.this, viewIdProvider, (ViewCapture.WindowListener) obj);
            }
        }).findFirst();
    }

    /* renamed from: initPool */
    public final void lambda$new$1(int i) {
        final ViewRef viewRef = new ViewRef();
        int i2 = 0;
        ViewRef viewRef2 = viewRef;
        while (true) {
            final ViewRef viewRef3 = viewRef2;
            if (i2 >= i) {
                MAIN_EXECUTOR.execute(new Runnable() { // from class: com.android.app.viewcapture.ViewCapture$$ExternalSyntheticLambda8
                    @Override // java.lang.Runnable
                    public final void run() {
                        ViewCapture.m498$r8$lambda$NzKcLYQ7vUqtbFhK4jrCiFLzWo(ViewCapture.this, viewRef, viewRef3);
                    }
                });
                return;
            }
            ViewRef viewRef4 = new ViewRef();
            viewRef3.next = viewRef4;
            i2++;
            viewRef2 = viewRef4;
        }
    }

    public SafeCloseable startCapture(View view, String str) {
        final WindowListener windowListener = new WindowListener(view, str);
        this.mExecutor.execute(new Runnable() { // from class: com.android.app.viewcapture.ViewCapture$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                ViewCapture.m499$r8$lambda$fKrj8qUyma1OiIArUfAQHBaCCo(ViewCapture.WindowListener.this);
            }
        });
        this.mListeners.add(windowListener);
        return new SafeCloseable() { // from class: com.android.app.viewcapture.ViewCapture$$ExternalSyntheticLambda5
            public final void close() {
                ViewCapture.$r8$lambda$GDUOfkr8VMufJ13z3HC7hvut03s(ViewCapture.this, windowListener);
            }
        };
    }
}