package com.android.systemui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Property;
import android.util.SparseArray;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import androidx.recyclerview.widget.RecyclerView;
import com.android.settingslib.widget.ActionBarShadowController;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/* loaded from: mainsysui33.jar:com/android/systemui/DessertCaseView.class */
public class DessertCaseView extends FrameLayout {
    public static final float[] ALPHA_MASK;
    public static final float[] MASK;
    public static final int NUM_PASTRIES;
    public static final int[] PASTRIES;
    public static final int[] RARE_PASTRIES;
    public static final String TAG = DessertCaseView.class.getSimpleName();
    public static final float[] WHITE_MASK;
    public static final int[] XRARE_PASTRIES;
    public static final int[] XXRARE_PASTRIES;
    public float[] hsv;
    public int mCellSize;
    public View[] mCells;
    public int mColumns;
    public SparseArray<Drawable> mDrawables;
    public final Set<Point> mFreeList;
    public final Handler mHandler;
    public int mHeight;
    public final Runnable mJuggle;
    public int mRows;
    public boolean mStarted;
    public int mWidth;
    public final HashSet<View> tmpSet;

    /* loaded from: mainsysui33.jar:com/android/systemui/DessertCaseView$RescalingContainer.class */
    public static class RescalingContainer extends FrameLayout {
        public DessertCaseView mView;

        public RescalingContainer(Context context) {
            super(context);
            setSystemUiVisibility(5638);
        }

        @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
        public void onLayout(boolean z, int i, int i2, int i3, int i4) {
            float f = i3 - i;
            float f2 = i4 - i2;
            int i5 = (int) ((f / 0.25f) / 2.0f);
            int i6 = (int) ((f2 / 0.25f) / 2.0f);
            int i7 = (int) (i + (f * 0.5f));
            int i8 = (int) (i2 + (f2 * 0.5f));
            this.mView.layout(i7 - i5, i8 - i6, i7 + i5, i8 + i6);
        }

        public void setView(DessertCaseView dessertCaseView) {
            addView(dessertCaseView);
            this.mView = dessertCaseView;
        }
    }

    static {
        int[] iArr = {R$drawable.dessert_kitkat, R$drawable.dessert_android};
        PASTRIES = iArr;
        int[] iArr2 = {R$drawable.dessert_cupcake, R$drawable.dessert_donut, R$drawable.dessert_eclair, R$drawable.dessert_froyo, R$drawable.dessert_gingerbread, R$drawable.dessert_honeycomb, R$drawable.dessert_ics, R$drawable.dessert_jellybean};
        RARE_PASTRIES = iArr2;
        int[] iArr3 = {R$drawable.dessert_petitfour, R$drawable.dessert_donutburger, R$drawable.dessert_flan, R$drawable.dessert_keylimepie};
        XRARE_PASTRIES = iArr3;
        int[] iArr4 = {R$drawable.dessert_zombiegingerbread, R$drawable.dessert_dandroid, R$drawable.dessert_jandycane};
        XXRARE_PASTRIES = iArr4;
        NUM_PASTRIES = iArr.length + iArr2.length + iArr3.length + iArr4.length;
        MASK = new float[]{ActionBarShadowController.ELEVATION_LOW, ActionBarShadowController.ELEVATION_LOW, ActionBarShadowController.ELEVATION_LOW, ActionBarShadowController.ELEVATION_LOW, 255.0f, ActionBarShadowController.ELEVATION_LOW, ActionBarShadowController.ELEVATION_LOW, ActionBarShadowController.ELEVATION_LOW, ActionBarShadowController.ELEVATION_LOW, 255.0f, ActionBarShadowController.ELEVATION_LOW, ActionBarShadowController.ELEVATION_LOW, ActionBarShadowController.ELEVATION_LOW, ActionBarShadowController.ELEVATION_LOW, 255.0f, 1.0f, ActionBarShadowController.ELEVATION_LOW, ActionBarShadowController.ELEVATION_LOW, ActionBarShadowController.ELEVATION_LOW, ActionBarShadowController.ELEVATION_LOW};
        ALPHA_MASK = new float[]{ActionBarShadowController.ELEVATION_LOW, ActionBarShadowController.ELEVATION_LOW, ActionBarShadowController.ELEVATION_LOW, ActionBarShadowController.ELEVATION_LOW, 255.0f, ActionBarShadowController.ELEVATION_LOW, ActionBarShadowController.ELEVATION_LOW, ActionBarShadowController.ELEVATION_LOW, ActionBarShadowController.ELEVATION_LOW, 255.0f, ActionBarShadowController.ELEVATION_LOW, ActionBarShadowController.ELEVATION_LOW, ActionBarShadowController.ELEVATION_LOW, ActionBarShadowController.ELEVATION_LOW, 255.0f, ActionBarShadowController.ELEVATION_LOW, ActionBarShadowController.ELEVATION_LOW, ActionBarShadowController.ELEVATION_LOW, 1.0f, ActionBarShadowController.ELEVATION_LOW};
        WHITE_MASK = new float[]{ActionBarShadowController.ELEVATION_LOW, ActionBarShadowController.ELEVATION_LOW, ActionBarShadowController.ELEVATION_LOW, ActionBarShadowController.ELEVATION_LOW, 255.0f, ActionBarShadowController.ELEVATION_LOW, ActionBarShadowController.ELEVATION_LOW, ActionBarShadowController.ELEVATION_LOW, ActionBarShadowController.ELEVATION_LOW, 255.0f, ActionBarShadowController.ELEVATION_LOW, ActionBarShadowController.ELEVATION_LOW, ActionBarShadowController.ELEVATION_LOW, ActionBarShadowController.ELEVATION_LOW, 255.0f, -1.0f, ActionBarShadowController.ELEVATION_LOW, ActionBarShadowController.ELEVATION_LOW, ActionBarShadowController.ELEVATION_LOW, 255.0f};
    }

    public DessertCaseView(Context context) {
        this(context, null);
    }

    public DessertCaseView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    /* JADX DEBUG: Multi-variable search result rejected for r0v23, resolved type: int[] */
    /* JADX DEBUG: Multi-variable search result rejected for r0v24, resolved type: java.lang.Object[] */
    /* JADX DEBUG: Multi-variable search result rejected for r0v30, resolved type: byte */
    /* JADX WARN: Multi-variable type inference failed */
    public DessertCaseView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        Object[] objArr;
        this.mDrawables = new SparseArray<>(NUM_PASTRIES);
        this.mFreeList = new HashSet();
        this.mHandler = new Handler();
        this.mJuggle = new Runnable() { // from class: com.android.systemui.DessertCaseView.1
            @Override // java.lang.Runnable
            public void run() {
                int childCount = DessertCaseView.this.getChildCount();
                for (int i2 = 0; i2 < 1; i2++) {
                    DessertCaseView.this.place(DessertCaseView.this.getChildAt((int) (Math.random() * childCount)), true);
                }
                DessertCaseView.this.fillFreeList();
                if (DessertCaseView.this.mStarted) {
                    DessertCaseView.this.mHandler.postDelayed(DessertCaseView.this.mJuggle, 2000L);
                }
            }
        };
        this.hsv = new float[]{ActionBarShadowController.ELEVATION_LOW, 1.0f, 0.85f};
        this.tmpSet = new HashSet<>();
        Resources resources = getResources();
        this.mStarted = false;
        this.mCellSize = resources.getDimensionPixelSize(R$dimen.dessert_case_cell_size);
        BitmapFactory.Options options = new BitmapFactory.Options();
        if (this.mCellSize < 512) {
            options.inSampleSize = 2;
        }
        options.inMutable = true;
        Bitmap bitmap = null;
        int[] iArr = PASTRIES;
        int[] iArr2 = RARE_PASTRIES;
        int[] iArr3 = XRARE_PASTRIES;
        int[] iArr4 = XXRARE_PASTRIES;
        for (int i2 = 0; i2 < 4; i2++) {
            for (byte b : new int[]{iArr, iArr2, iArr3, iArr4}[i2]) {
                options.inBitmap = bitmap;
                bitmap = BitmapFactory.decodeResource(resources, b, options);
                BitmapDrawable bitmapDrawable = new BitmapDrawable(resources, convertToAlphaMask(bitmap));
                bitmapDrawable.setColorFilter(new ColorMatrixColorFilter(ALPHA_MASK));
                int i3 = this.mCellSize;
                bitmapDrawable.setBounds(0, 0, i3, i3);
                this.mDrawables.append(b, bitmapDrawable);
            }
        }
    }

    public static Bitmap convertToAlphaMask(Bitmap bitmap) {
        Bitmap createBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ALPHA_8);
        Canvas canvas = new Canvas(createBitmap);
        Paint paint = new Paint();
        paint.setColorFilter(new ColorMatrixColorFilter(MASK));
        canvas.drawBitmap(bitmap, ActionBarShadowController.ELEVATION_LOW, ActionBarShadowController.ELEVATION_LOW, paint);
        return createBitmap;
    }

    public static float frand() {
        return (float) Math.random();
    }

    public static float frand(float f, float f2) {
        return (frand() * (f2 - f)) + f;
    }

    public static int irand(int i, int i2) {
        return (int) frand(i, i2);
    }

    public void fillFreeList() {
        fillFreeList(500);
    }

    public void fillFreeList(int i) {
        synchronized (this) {
            Context context = getContext();
            int i2 = this.mCellSize;
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(i2, i2);
            while (!this.mFreeList.isEmpty()) {
                Point next = this.mFreeList.iterator().next();
                this.mFreeList.remove(next);
                if (this.mCells[(next.y * this.mColumns) + next.x] == null) {
                    final ImageView imageView = new ImageView(context);
                    imageView.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.DessertCaseView.2
                        @Override // android.view.View.OnClickListener
                        public void onClick(View view) {
                            DessertCaseView.this.place(imageView, true);
                            DessertCaseView.this.postDelayed(new Runnable() { // from class: com.android.systemui.DessertCaseView.2.1
                                @Override // java.lang.Runnable
                                public void run() {
                                    DessertCaseView.this.fillFreeList();
                                }
                            }, 250L);
                        }
                    });
                    imageView.setBackgroundColor(random_color());
                    float frand = frand();
                    Drawable drawable = frand < 5.0E-4f ? this.mDrawables.get(pick(XXRARE_PASTRIES)) : frand < 0.005f ? this.mDrawables.get(pick(XRARE_PASTRIES)) : frand < 0.5f ? this.mDrawables.get(pick(RARE_PASTRIES)) : frand < 0.7f ? this.mDrawables.get(pick(PASTRIES)) : null;
                    if (drawable != null) {
                        imageView.getOverlay().add(drawable);
                    }
                    int i3 = this.mCellSize;
                    layoutParams.height = i3;
                    layoutParams.width = i3;
                    addView(imageView, layoutParams);
                    place(imageView, next, false);
                    if (i > 0) {
                        float intValue = ((Integer) imageView.getTag(33554434)).intValue();
                        float f = 0.5f * intValue;
                        imageView.setScaleX(f);
                        imageView.setScaleY(f);
                        imageView.setAlpha(ActionBarShadowController.ELEVATION_LOW);
                        imageView.animate().withLayer().scaleX(intValue).scaleY(intValue).alpha(1.0f).setDuration(i);
                    }
                }
            }
        }
    }

    public final Point[] getOccupied(View view) {
        int intValue = ((Integer) view.getTag(33554434)).intValue();
        Point point = (Point) view.getTag(33554433);
        if (point == null || intValue == 0) {
            return new Point[0];
        }
        Point[] pointArr = new Point[intValue * intValue];
        int i = 0;
        for (int i2 = 0; i2 < intValue; i2++) {
            int i3 = 0;
            while (i3 < intValue) {
                pointArr[i] = new Point(point.x + i2, point.y + i3);
                i3++;
                i++;
            }
        }
        return pointArr;
    }

    public final Animator.AnimatorListener makeHardwareLayerListener(final View view) {
        return new AnimatorListenerAdapter() { // from class: com.android.systemui.DessertCaseView.3
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                view.setLayerType(0, null);
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator) {
                view.setLayerType(2, null);
                view.buildLayer();
            }
        };
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override // android.view.View
    public void onSizeChanged(int i, int i2, int i3, int i4) {
        synchronized (this) {
            super.onSizeChanged(i, i2, i3, i4);
            if (this.mWidth == i && this.mHeight == i2) {
                return;
            }
            boolean z = this.mStarted;
            if (z) {
                stop();
            }
            this.mWidth = i;
            this.mHeight = i2;
            this.mCells = null;
            removeAllViewsInLayout();
            this.mFreeList.clear();
            int i5 = this.mHeight;
            int i6 = this.mCellSize;
            int i7 = i5 / i6;
            this.mRows = i7;
            int i8 = this.mWidth / i6;
            this.mColumns = i8;
            this.mCells = new View[i7 * i8];
            setScaleX(0.25f);
            setScaleY(0.25f);
            setTranslationX((this.mWidth - (this.mCellSize * this.mColumns)) * 0.5f * 0.25f);
            setTranslationY((this.mHeight - (this.mCellSize * this.mRows)) * 0.5f * 0.25f);
            for (int i9 = 0; i9 < this.mRows; i9++) {
                for (int i10 = 0; i10 < this.mColumns; i10++) {
                    this.mFreeList.add(new Point(i10, i9));
                }
            }
            if (z) {
                start();
            }
        }
    }

    public int pick(int[] iArr) {
        return iArr[(int) (Math.random() * iArr.length)];
    }

    public void place(View view, Point point, boolean z) {
        int i;
        Point[] occupied;
        Point[] occupied2;
        synchronized (this) {
            int i2 = point.x;
            int i3 = point.y;
            float frand = frand();
            if (view.getTag(33554433) != null) {
                for (Point point2 : getOccupied(view)) {
                    this.mFreeList.add(point2);
                    this.mCells[(point2.y * this.mColumns) + point2.x] = null;
                }
            }
            if (frand < 0.01f) {
                if (i2 < this.mColumns - 3 && i3 < this.mRows - 3) {
                    i = 4;
                }
                i = 1;
            } else if (frand < 0.1f) {
                if (i2 < this.mColumns - 2 && i3 < this.mRows - 2) {
                    i = 3;
                }
                i = 1;
            } else {
                if (frand < 0.33f && i2 != this.mColumns - 1 && i3 != this.mRows - 1) {
                    i = 2;
                }
                i = 1;
            }
            view.setTag(33554433, point);
            view.setTag(33554434, Integer.valueOf(i));
            this.tmpSet.clear();
            Point[] occupied3 = getOccupied(view);
            for (Point point3 : occupied3) {
                View view2 = this.mCells[(point3.y * this.mColumns) + point3.x];
                if (view2 != null) {
                    this.tmpSet.add(view2);
                }
            }
            Iterator<View> it = this.tmpSet.iterator();
            while (it.hasNext()) {
                final View next = it.next();
                for (Point point4 : getOccupied(next)) {
                    this.mFreeList.add(point4);
                    this.mCells[(point4.y * this.mColumns) + point4.x] = null;
                }
                if (next != view) {
                    next.setTag(33554433, null);
                    if (z) {
                        next.animate().withLayer().scaleX(0.5f).scaleY(0.5f).alpha(ActionBarShadowController.ELEVATION_LOW).setDuration(500L).setInterpolator(new AccelerateInterpolator()).setListener(new Animator.AnimatorListener() { // from class: com.android.systemui.DessertCaseView.4
                            @Override // android.animation.Animator.AnimatorListener
                            public void onAnimationCancel(Animator animator) {
                            }

                            @Override // android.animation.Animator.AnimatorListener
                            public void onAnimationEnd(Animator animator) {
                                DessertCaseView.this.removeView(next);
                            }

                            @Override // android.animation.Animator.AnimatorListener
                            public void onAnimationRepeat(Animator animator) {
                            }

                            @Override // android.animation.Animator.AnimatorListener
                            public void onAnimationStart(Animator animator) {
                            }
                        }).start();
                    } else {
                        removeView(next);
                    }
                }
            }
            for (Point point5 : occupied3) {
                this.mCells[(point5.y * this.mColumns) + point5.x] = view;
                this.mFreeList.remove(point5);
            }
            float irand = irand(0, 4) * 90.0f;
            if (z) {
                view.bringToFront();
                AnimatorSet animatorSet = new AnimatorSet();
                float f = i;
                animatorSet.playTogether(ObjectAnimator.ofFloat(view, View.SCALE_X, f), ObjectAnimator.ofFloat(view, View.SCALE_Y, f));
                animatorSet.setInterpolator(new AnticipateOvershootInterpolator());
                animatorSet.setDuration(500L);
                AnimatorSet animatorSet2 = new AnimatorSet();
                ObjectAnimator ofFloat = ObjectAnimator.ofFloat(view, View.ROTATION, irand);
                Property property = View.X;
                int i4 = this.mCellSize;
                int i5 = i - 1;
                ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(view, property, (i2 * i4) + ((i4 * i5) / 2));
                Property property2 = View.Y;
                int i6 = this.mCellSize;
                animatorSet2.playTogether(ofFloat, ofFloat2, ObjectAnimator.ofFloat(view, property2, (i3 * i6) + ((i5 * i6) / 2)));
                animatorSet2.setInterpolator(new DecelerateInterpolator());
                animatorSet2.setDuration(500L);
                animatorSet.addListener(makeHardwareLayerListener(view));
                animatorSet.start();
                animatorSet2.start();
            } else {
                int i7 = this.mCellSize;
                int i8 = i - 1;
                view.setX((i2 * i7) + ((i7 * i8) / 2));
                int i9 = this.mCellSize;
                view.setY((i3 * i9) + ((i8 * i9) / 2));
                float f2 = i;
                view.setScaleX(f2);
                view.setScaleY(f2);
                view.setRotation(irand);
            }
        }
    }

    public void place(View view, boolean z) {
        place(view, new Point(irand(0, this.mColumns), irand(0, this.mRows)), z);
    }

    public int random_color() {
        this.hsv[0] = irand(0, 12) * 30.0f;
        return Color.HSVToColor(this.hsv);
    }

    public void start() {
        if (!this.mStarted) {
            this.mStarted = true;
            fillFreeList(RecyclerView.MAX_SCROLL_DURATION);
        }
        this.mHandler.postDelayed(this.mJuggle, 5000L);
    }

    public void stop() {
        this.mStarted = false;
        this.mHandler.removeCallbacks(this.mJuggle);
    }
}