package com.android.systemui.dreams.complication;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.android.systemui.dreams.complication.Complication;
import com.android.systemui.dreams.complication.ComplicationLayoutEngine;
import com.android.systemui.statusbar.CrossFadeHelper;
import com.android.systemui.touch.TouchInsetManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/* loaded from: mainsysui33.jar:com/android/systemui/dreams/complication/ComplicationLayoutEngine.class */
public class ComplicationLayoutEngine implements Complication.VisibilityController {
    public final int mDefaultMargin;
    public final int mFadeInDuration;
    public final int mFadeOutDuration;
    public final ConstraintLayout mLayout;
    public final TouchInsetManager.TouchInsetSession mSession;
    public final HashMap<ComplicationId, ViewEntry> mEntries = new HashMap<>();
    public final HashMap<Integer, PositionGroup> mPositions = new HashMap<>();

    /* loaded from: mainsysui33.jar:com/android/systemui/dreams/complication/ComplicationLayoutEngine$DirectionGroup.class */
    public static class DirectionGroup implements ViewEntry.Parent {
        public final Parent mParent;
        public final ArrayList<ViewEntry> mViews;

        /* loaded from: mainsysui33.jar:com/android/systemui/dreams/complication/ComplicationLayoutEngine$DirectionGroup$Parent.class */
        public interface Parent {
            void onEntriesChanged();
        }

        public ViewEntry getHead() {
            return this.mViews.isEmpty() ? null : this.mViews.get(0);
        }

        public final List<ViewEntry> getViews() {
            return this.mViews;
        }

        @Override // com.android.systemui.dreams.complication.ComplicationLayoutEngine.ViewEntry.Parent
        public void removeEntry(ViewEntry viewEntry) {
            this.mViews.remove(viewEntry);
            this.mParent.onEntriesChanged();
        }

        public void updateViews(View view) {
            Iterator<ViewEntry> it = this.mViews.iterator();
            while (it.hasNext()) {
                ViewEntry next = it.next();
                next.applyLayoutParams(view);
                view = next.getView();
            }
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/dreams/complication/ComplicationLayoutEngine$PositionGroup.class */
    public static class PositionGroup implements DirectionGroup.Parent {
        public final HashMap<Integer, DirectionGroup> mDirectionGroups = new HashMap<>();

        public final ArrayList<ViewEntry> getViews() {
            ArrayList<ViewEntry> arrayList = new ArrayList<>();
            for (DirectionGroup directionGroup : this.mDirectionGroups.values()) {
                arrayList.addAll(directionGroup.getViews());
            }
            return arrayList;
        }

        @Override // com.android.systemui.dreams.complication.ComplicationLayoutEngine.DirectionGroup.Parent
        public void onEntriesChanged() {
            updateViews();
        }

        public final void updateViews() {
            ViewEntry viewEntry = null;
            for (DirectionGroup directionGroup : this.mDirectionGroups.values()) {
                ViewEntry head = directionGroup.getHead();
                if (viewEntry == null || (head != null && head.compareTo(viewEntry) > 0)) {
                    viewEntry = head;
                }
            }
            if (viewEntry == null) {
                return;
            }
            for (DirectionGroup directionGroup2 : this.mDirectionGroups.values()) {
                directionGroup2.updateViews(viewEntry.getView());
            }
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/dreams/complication/ComplicationLayoutEngine$ViewEntry.class */
    public static class ViewEntry implements Comparable<ViewEntry> {
        public final int mCategory;
        public final Parent mParent;
        public final TouchInsetManager.TouchInsetSession mTouchInsetSession;
        public final View mView;

        /* loaded from: mainsysui33.jar:com/android/systemui/dreams/complication/ComplicationLayoutEngine$ViewEntry$Builder.class */
        public static class Builder {
            public final int mCategory;
            public int mDefaultMargin;
            public final TouchInsetManager.TouchInsetSession mTouchSession;
            public final View mView;

            public Builder(View view, TouchInsetManager.TouchInsetSession touchInsetSession, ComplicationLayoutParams complicationLayoutParams, int i) {
                this.mView = view;
                this.mCategory = i;
                this.mTouchSession = touchInsetSession;
            }

            public Builder setDefaultMargin(int i) {
                this.mDefaultMargin = i;
                return this;
            }
        }

        /* loaded from: mainsysui33.jar:com/android/systemui/dreams/complication/ComplicationLayoutEngine$ViewEntry$Parent.class */
        public interface Parent {
            void removeEntry(ViewEntry viewEntry);
        }

        public void applyLayoutParams(View view) {
            throw null;
        }

        @Override // java.lang.Comparable
        public int compareTo(ViewEntry viewEntry) {
            int i = viewEntry.mCategory;
            int i2 = this.mCategory;
            if (i != i2) {
                return i2 == 2 ? 1 : -1;
            }
            throw null;
        }

        public final View getView() {
            return this.mView;
        }

        public void remove() {
            this.mParent.removeEntry(this);
            ((ViewGroup) this.mView.getParent()).removeView(this.mView);
            this.mTouchInsetSession.removeViewFromTracking(this.mView);
        }
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.dreams.complication.ComplicationLayoutEngine$$ExternalSyntheticLambda1.apply(java.lang.Object):java.lang.Object] */
    public static /* synthetic */ Stream $r8$lambda$4nP4eO61VbRSTwmhy_EH_wUhByc(Map.Entry entry) {
        return lambda$getViewsAtPosition$1(entry);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.dreams.complication.ComplicationLayoutEngine$$ExternalSyntheticLambda0.test(java.lang.Object):boolean] */
    /* renamed from: $r8$lambda$TOeGmCtbH2nb8feT2LMBmb6F1-Y */
    public static /* synthetic */ boolean m2583$r8$lambda$TOeGmCtbH2nb8feT2LMBmb6F1Y(int i, Map.Entry entry) {
        return lambda$getViewsAtPosition$0(i, entry);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.dreams.complication.ComplicationLayoutEngine$$ExternalSyntheticLambda2.apply(java.lang.Object):java.lang.Object] */
    public static /* synthetic */ View $r8$lambda$cye2lCDd2QX2byPo4ypl_IF7goI(Object obj) {
        return lambda$getViewsAtPosition$2(obj);
    }

    public ComplicationLayoutEngine(ConstraintLayout constraintLayout, int i, TouchInsetManager.TouchInsetSession touchInsetSession, int i2, int i3) {
        this.mLayout = constraintLayout;
        this.mDefaultMargin = i;
        this.mSession = touchInsetSession;
        this.mFadeInDuration = i2;
        this.mFadeOutDuration = i3;
    }

    public static /* synthetic */ boolean lambda$getViewsAtPosition$0(int i, Map.Entry entry) {
        return (((Integer) entry.getKey()).intValue() & i) == i;
    }

    public static /* synthetic */ Stream lambda$getViewsAtPosition$1(Map.Entry entry) {
        return ((PositionGroup) entry.getValue()).getViews().stream();
    }

    public static /* synthetic */ View lambda$getViewsAtPosition$2(Object obj) {
        return ((ViewEntry) obj).getView();
    }

    public void addComplication(ComplicationId complicationId, View view, ComplicationLayoutParams complicationLayoutParams, int i) {
        Log.d("ComplicationLayoutEng", "@" + Integer.toHexString(hashCode()) + " addComplication: " + complicationId);
        if (this.mEntries.containsKey(complicationId)) {
            removeComplication(complicationId);
        }
        new ViewEntry.Builder(view, this.mSession, complicationLayoutParams, i).setDefaultMargin(this.mDefaultMargin);
        throw null;
    }

    public List<View> getViewsAtPosition(final int i) {
        return (List) this.mPositions.entrySet().stream().filter(new Predicate() { // from class: com.android.systemui.dreams.complication.ComplicationLayoutEngine$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ComplicationLayoutEngine.m2583$r8$lambda$TOeGmCtbH2nb8feT2LMBmb6F1Y(i, (Map.Entry) obj);
            }
        }).flatMap(new Function() { // from class: com.android.systemui.dreams.complication.ComplicationLayoutEngine$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ComplicationLayoutEngine.$r8$lambda$4nP4eO61VbRSTwmhy_EH_wUhByc((Map.Entry) obj);
            }
        }).map(new Function() { // from class: com.android.systemui.dreams.complication.ComplicationLayoutEngine$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ComplicationLayoutEngine.$r8$lambda$cye2lCDd2QX2byPo4ypl_IF7goI((ComplicationLayoutEngine.ViewEntry) obj);
            }
        }).collect(Collectors.toList());
    }

    public boolean removeComplication(ComplicationId complicationId) {
        ViewEntry remove = this.mEntries.remove(complicationId);
        if (remove != null) {
            remove.remove();
            return true;
        }
        Log.e("ComplicationLayoutEng", "could not find id:" + complicationId);
        return false;
    }

    @Override // com.android.systemui.dreams.complication.Complication.VisibilityController
    public void setVisibility(int i) {
        if (i == 0) {
            CrossFadeHelper.fadeIn(this.mLayout, this.mFadeInDuration, 0);
        } else {
            CrossFadeHelper.fadeOut(this.mLayout, this.mFadeOutDuration, 0, (Runnable) null);
        }
    }
}