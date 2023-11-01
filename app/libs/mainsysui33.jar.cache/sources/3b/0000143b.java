package com.android.systemui.controls.management;

import android.content.ComponentName;
import android.util.Log;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import com.android.systemui.controls.ControlInterface;
import com.android.systemui.controls.CustomIconCache;
import com.android.systemui.controls.controller.ControlInfo;
import com.android.systemui.controls.management.ControlsModel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: mainsysui33.jar:com/android/systemui/controls/management/FavoritesModel.class */
public final class FavoritesModel implements ControlsModel {
    public static final Companion Companion = new Companion(null);
    public RecyclerView.Adapter<?> adapter;
    public final ComponentName componentName;
    public final CustomIconCache customIconCache;
    public int dividerPosition;
    public final List<ElementWrapper> elements;
    public final FavoritesModelCallback favoritesModelCallback;
    public final ItemTouchHelper.SimpleCallback itemTouchHelperCallback;
    public boolean modified;
    public final ControlsModel.MoveHelper moveHelper = new ControlsModel.MoveHelper() { // from class: com.android.systemui.controls.management.FavoritesModel$moveHelper$1
        @Override // com.android.systemui.controls.management.ControlsModel.MoveHelper
        public boolean canMoveAfter(int i) {
            boolean z = true;
            if (i < 0 || i >= FavoritesModel.access$getDividerPosition$p(FavoritesModel.this) - 1) {
                z = false;
            }
            return z;
        }

        @Override // com.android.systemui.controls.management.ControlsModel.MoveHelper
        public boolean canMoveBefore(int i) {
            return i > 0 && i < FavoritesModel.access$getDividerPosition$p(FavoritesModel.this);
        }

        @Override // com.android.systemui.controls.management.ControlsModel.MoveHelper
        public void moveAfter(int i) {
            if (canMoveAfter(i)) {
                FavoritesModel.this.onMoveItem(i, i + 1);
                return;
            }
            Log.w("FavoritesModel", "Cannot move position " + i + " after");
        }

        @Override // com.android.systemui.controls.management.ControlsModel.MoveHelper
        public void moveBefore(int i) {
            if (canMoveBefore(i)) {
                FavoritesModel.this.onMoveItem(i, i - 1);
                return;
            }
            Log.w("FavoritesModel", "Cannot move position " + i + " before");
        }
    };

    /* loaded from: mainsysui33.jar:com/android/systemui/controls/management/FavoritesModel$Companion.class */
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/controls/management/FavoritesModel$FavoritesModelCallback.class */
    public interface FavoritesModelCallback extends ControlsModel.ControlsModelCallback {
        void onNoneChanged(boolean z);
    }

    public FavoritesModel(CustomIconCache customIconCache, ComponentName componentName, List<ControlInfo> list, FavoritesModelCallback favoritesModelCallback) {
        this.customIconCache = customIconCache;
        this.componentName = componentName;
        this.favoritesModelCallback = favoritesModelCallback;
        List<ControlInfo> list2 = list;
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list2, 10));
        for (ControlInfo controlInfo : list2) {
            arrayList.add(new ControlInfoWrapper(this.componentName, controlInfo, true, new FavoritesModel$elements$1$1(this.customIconCache)));
        }
        this.elements = CollectionsKt___CollectionsKt.plus(arrayList, new DividerWrapper(false, false, 3, null));
        this.dividerPosition = getElements().size() - 1;
        this.itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback() { // from class: com.android.systemui.controls.management.FavoritesModel$itemTouchHelperCallback$1
            public final int MOVEMENT;

            {
                super(0, 0);
                this.MOVEMENT = 15;
            }

            @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
            public boolean canDropOver(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder viewHolder2) {
                int i;
                int bindingAdapterPosition = viewHolder2.getBindingAdapterPosition();
                i = FavoritesModel.this.dividerPosition;
                return bindingAdapterPosition < i;
            }

            @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                int i;
                int bindingAdapterPosition = viewHolder.getBindingAdapterPosition();
                i = FavoritesModel.this.dividerPosition;
                return bindingAdapterPosition < i ? ItemTouchHelper.Callback.makeMovementFlags(this.MOVEMENT, 0) : ItemTouchHelper.Callback.makeMovementFlags(0, 0);
            }

            @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
            public boolean isItemViewSwipeEnabled() {
                return false;
            }

            @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder viewHolder2) {
                FavoritesModel.this.onMoveItem(viewHolder.getBindingAdapterPosition(), viewHolder2.getBindingAdapterPosition());
                return true;
            }

            @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int i) {
            }
        };
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.controls.management.FavoritesModel$moveHelper$1.canMoveAfter(int):boolean, com.android.systemui.controls.management.FavoritesModel$moveHelper$1.canMoveBefore(int):boolean] */
    public static final /* synthetic */ int access$getDividerPosition$p(FavoritesModel favoritesModel) {
        return favoritesModel.dividerPosition;
    }

    public void attachAdapter(RecyclerView.Adapter<?> adapter) {
        this.adapter = adapter;
    }

    @Override // com.android.systemui.controls.management.ControlsModel
    public void changeFavoriteStatus(String str, boolean z) {
        Iterator<ElementWrapper> it = getElements().iterator();
        int i = 0;
        while (true) {
            if (!it.hasNext()) {
                i = -1;
                break;
            }
            ElementWrapper next = it.next();
            if ((next instanceof ControlInterface) && Intrinsics.areEqual(((ControlInterface) next).getControlId(), str)) {
                break;
            }
            i++;
        }
        if (i == -1) {
            return;
        }
        int i2 = this.dividerPosition;
        if (i >= i2 || !z) {
            if (i <= i2 || z) {
                if (z) {
                    onMoveItemInternal(i, i2);
                } else {
                    onMoveItemInternal(i, getElements().size() - 1);
                }
            }
        }
    }

    @Override // com.android.systemui.controls.management.ControlsModel
    public List<ElementWrapper> getElements() {
        return this.elements;
    }

    @Override // com.android.systemui.controls.management.ControlsModel
    public List<ControlInfo> getFavorites() {
        List<ElementWrapper> take = CollectionsKt___CollectionsKt.take(getElements(), this.dividerPosition);
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(take, 10));
        for (ElementWrapper elementWrapper : take) {
            arrayList.add(((ControlInfoWrapper) elementWrapper).getControlInfo());
        }
        return arrayList;
    }

    public final ItemTouchHelper.SimpleCallback getItemTouchHelperCallback() {
        return this.itemTouchHelperCallback;
    }

    @Override // com.android.systemui.controls.management.ControlsModel
    public ControlsModel.MoveHelper getMoveHelper() {
        return this.moveHelper;
    }

    public final void moveElement(int i, int i2) {
        if (i < i2) {
            while (i < i2) {
                int i3 = i + 1;
                Collections.swap(getElements(), i, i3);
                i = i3;
            }
            return;
        }
        int i4 = i2 + 1;
        if (i4 > i) {
            return;
        }
        while (true) {
            Collections.swap(getElements(), i, i - 1);
            if (i == i4) {
                return;
            }
            i--;
        }
    }

    public void onMoveItem(int i, int i2) {
        onMoveItemInternal(i, i2);
    }

    /* JADX WARN: Code restructure failed: missing block: B:49:0x0027, code lost:
        if (r7 <= r0) goto L9;
     */
    /* JADX WARN: Removed duplicated region for block: B:63:0x007d  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x00a8  */
    /* JADX WARN: Removed duplicated region for block: B:73:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onMoveItemInternal(int i, int i2) {
        boolean z;
        RecyclerView.Adapter<?> adapter;
        RecyclerView.Adapter<?> adapter2;
        int i3 = this.dividerPosition;
        if (i == i3) {
            return;
        }
        if (i >= i3 || i2 < i3) {
            z = false;
            if (i > i3) {
                z = false;
            }
            moveElement(i, i2);
            adapter = this.adapter;
            if (adapter != null) {
                adapter.notifyItemMoved(i, i2);
            }
            if (z && (adapter2 = this.adapter) != null) {
                adapter2.notifyItemChanged(i2, new Object());
            }
            if (this.modified) {
                this.modified = true;
                this.favoritesModelCallback.onFirstChange();
                return;
            }
            return;
        }
        if (i < i3 && i2 >= i3) {
            ((ControlInfoWrapper) getElements().get(i)).setFavorite(false);
        } else if (i > i3 && i2 <= i3) {
            ((ControlInfoWrapper) getElements().get(i)).setFavorite(true);
        }
        updateDivider(i, i2);
        z = true;
        moveElement(i, i2);
        adapter = this.adapter;
        if (adapter != null) {
        }
        if (z) {
            adapter2.notifyItemChanged(i2, new Object());
        }
        if (this.modified) {
        }
    }

    public final void updateDivider(int i, int i2) {
        boolean z;
        RecyclerView.Adapter<?> adapter;
        int i3 = this.dividerPosition;
        boolean z2 = false;
        if (i >= i3 || i2 < i3) {
            z2 = false;
            if (i > i3) {
                z2 = false;
                if (i2 <= i3) {
                    int i4 = i3 + 1;
                    this.dividerPosition = i4;
                    if (i4 == 1) {
                        updateDividerNone(i3, false);
                        z = true;
                    } else {
                        z = false;
                    }
                    if (this.dividerPosition == getElements().size() - 1) {
                        updateDividerShow(i3, false);
                        z2 = true;
                    } else {
                        z2 = z;
                    }
                }
            }
        } else {
            int i5 = i3 - 1;
            this.dividerPosition = i5;
            if (i5 == 0) {
                updateDividerNone(i3, true);
                z2 = true;
            }
            if (this.dividerPosition == getElements().size() - 2) {
                updateDividerShow(i3, true);
                z2 = true;
            }
        }
        if (!z2 || (adapter = this.adapter) == null) {
            return;
        }
        adapter.notifyItemChanged(i3);
    }

    public final void updateDividerNone(int i, boolean z) {
        ((DividerWrapper) getElements().get(i)).setShowNone(z);
        this.favoritesModelCallback.onNoneChanged(z);
    }

    public final void updateDividerShow(int i, boolean z) {
        ((DividerWrapper) getElements().get(i)).setShowDivider(z);
    }
}