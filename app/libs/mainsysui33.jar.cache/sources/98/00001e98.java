package com.android.systemui.navigationbar.buttons;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.android.systemui.R$attr;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

/* loaded from: mainsysui33.jar:com/android/systemui/navigationbar/buttons/NearestTouchFrame.class */
public class NearestTouchFrame extends FrameLayout {
    public final List<View> mAttachedChildren;
    public final Comparator<View> mChildRegionComparator;
    public final List<View> mClickableChildren;
    public final boolean mIsActive;
    public boolean mIsVertical;
    public final int[] mOffset;
    public final int[] mTmpInt;
    public final Map<View, Rect> mTouchableRegions;
    public View mTouchingChild;

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.navigationbar.buttons.NearestTouchFrame$$ExternalSyntheticLambda2.compare(java.lang.Object, java.lang.Object):int] */
    public static /* synthetic */ int $r8$lambda$LndNtE171GrDstizbH6GW6dxx5A(NearestTouchFrame nearestTouchFrame, View view, View view2) {
        return nearestTouchFrame.lambda$new$0(view, view2);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.navigationbar.buttons.NearestTouchFrame$$ExternalSyntheticLambda1.test(java.lang.Object):boolean] */
    public static /* synthetic */ boolean $r8$lambda$xuWwZFrW1M3IJoYiswYsphtd4B0(NearestTouchFrame nearestTouchFrame, int i, int i2, View view) {
        return nearestTouchFrame.lambda$onTouchEvent$1(i, i2, view);
    }

    public NearestTouchFrame(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, context.getResources().getConfiguration());
    }

    public NearestTouchFrame(Context context, AttributeSet attributeSet, Configuration configuration) {
        super(context, attributeSet);
        this.mClickableChildren = new ArrayList();
        this.mAttachedChildren = new ArrayList();
        this.mTmpInt = new int[2];
        this.mOffset = new int[2];
        this.mTouchableRegions = new HashMap();
        this.mChildRegionComparator = new Comparator() { // from class: com.android.systemui.navigationbar.buttons.NearestTouchFrame$$ExternalSyntheticLambda2
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return NearestTouchFrame.$r8$lambda$LndNtE171GrDstizbH6GW6dxx5A(NearestTouchFrame.this, (View) obj, (View) obj2);
            }
        };
        this.mIsActive = configuration.smallestScreenWidthDp < 600;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, new int[]{R$attr.isVertical});
        this.mIsVertical = obtainStyledAttributes.getBoolean(0, false);
        obtainStyledAttributes.recycle();
    }

    public /* synthetic */ int lambda$new$0(View view, View view2) {
        boolean z = this.mIsVertical;
        view.getLocationInWindow(this.mTmpInt);
        int[] iArr = this.mTmpInt;
        int i = iArr[z ? 1 : 0];
        int i2 = this.mOffset[z ? 1 : 0];
        view2.getLocationInWindow(iArr);
        return (i - i2) - (this.mTmpInt[z ? 1 : 0] - this.mOffset[z ? 1 : 0]);
    }

    public /* synthetic */ boolean lambda$onTouchEvent$1(int i, int i2, View view) {
        return this.mTouchableRegions.get(view).contains(i, i2);
    }

    public final void addClickableChildren(ViewGroup viewGroup) {
        int childCount = viewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = viewGroup.getChildAt(i);
            if (childAt.isClickable()) {
                this.mClickableChildren.add(childAt);
            } else if (childAt instanceof ViewGroup) {
                addClickableChildren((ViewGroup) childAt);
            }
        }
    }

    public final void cacheClosestChildLocations() {
        if (getWidth() == 0 || getHeight() == 0) {
            return;
        }
        this.mClickableChildren.sort(this.mChildRegionComparator);
        Stream<View> filter = this.mClickableChildren.stream().filter(new NearestTouchFrame$$ExternalSyntheticLambda0());
        final List<View> list = this.mAttachedChildren;
        Objects.requireNonNull(list);
        filter.forEachOrdered(new Consumer() { // from class: com.android.systemui.navigationbar.buttons.NearestTouchFrame$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                list.add((View) obj);
            }
        });
        for (int i = 0; i < this.mAttachedChildren.size(); i++) {
            View view = this.mAttachedChildren.get(i);
            if (view.isAttachedToWindow()) {
                Rect childsBounds = getChildsBounds(view);
                if (i == 0) {
                    if (this.mIsVertical) {
                        childsBounds.top = 0;
                    } else {
                        childsBounds.left = 0;
                    }
                    this.mTouchableRegions.put(view, childsBounds);
                } else {
                    Rect rect = this.mTouchableRegions.get(this.mAttachedChildren.get(i - 1));
                    if (this.mIsVertical) {
                        int i2 = childsBounds.top;
                        int i3 = rect.bottom;
                        int i4 = i2 - i3;
                        int i5 = i4 / 2;
                        childsBounds.top = i2 - i5;
                        rect.bottom = i3 + (i5 - (i4 % 2 == 0 ? 1 : 0));
                    } else {
                        int i6 = childsBounds.left;
                        int i7 = rect.right;
                        int i8 = i6 - i7;
                        int i9 = i8 / 2;
                        childsBounds.left = i6 - i9;
                        rect.right = i7 + (i9 - (i8 % 2 == 0 ? 1 : 0));
                    }
                    if (i == this.mClickableChildren.size() - 1) {
                        if (this.mIsVertical) {
                            childsBounds.bottom = getHeight();
                        } else {
                            childsBounds.right = getWidth();
                        }
                    }
                    this.mTouchableRegions.put(view, childsBounds);
                }
            }
        }
    }

    public final Rect getChildsBounds(View view) {
        view.getLocationInWindow(this.mTmpInt);
        int[] iArr = this.mTmpInt;
        int i = iArr[0];
        int[] iArr2 = this.mOffset;
        int i2 = i - iArr2[0];
        int i3 = iArr[1] - iArr2[1];
        return new Rect(i2, i3, view.getWidth() + i2, view.getHeight() + i3);
    }

    public Map<View, Rect> getFullTouchableChildRegions() {
        HashMap hashMap = new HashMap(this.mTouchableRegions.size());
        getLocationOnScreen(this.mTmpInt);
        for (Map.Entry<View, Rect> entry : this.mTouchableRegions.entrySet()) {
            View key = entry.getKey();
            Rect rect = new Rect(entry.getValue());
            int[] iArr = this.mTmpInt;
            rect.offset(iArr[0], iArr[1]);
            hashMap.put(key, rect);
        }
        return hashMap;
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        this.mClickableChildren.clear();
        this.mAttachedChildren.clear();
        this.mTouchableRegions.clear();
        addClickableChildren(this);
        getLocationInWindow(this.mOffset);
        cacheClosestChildLocations();
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (this.mIsActive) {
            final int x = (int) motionEvent.getX();
            final int y = (int) motionEvent.getY();
            if (motionEvent.getAction() == 0) {
                this.mTouchingChild = this.mClickableChildren.stream().filter(new NearestTouchFrame$$ExternalSyntheticLambda0()).filter(new Predicate() { // from class: com.android.systemui.navigationbar.buttons.NearestTouchFrame$$ExternalSyntheticLambda1
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return NearestTouchFrame.$r8$lambda$xuWwZFrW1M3IJoYiswYsphtd4B0(NearestTouchFrame.this, x, y, (View) obj);
                    }
                }).findFirst().orElse(null);
            }
            View view = this.mTouchingChild;
            if (view != null) {
                motionEvent.offsetLocation((view.getWidth() / 2) - x, (this.mTouchingChild.getHeight() / 2) - y);
                return this.mTouchingChild.getVisibility() == 0 && this.mTouchingChild.dispatchTouchEvent(motionEvent);
            }
        }
        return super.onTouchEvent(motionEvent);
    }

    public void setIsVertical(boolean z) {
        this.mIsVertical = z;
    }
}