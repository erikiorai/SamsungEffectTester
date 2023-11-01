package com.android.systemui.qs.customize;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import com.android.internal.R;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.R$dimen;
import com.android.systemui.R$drawable;
import com.android.systemui.R$integer;
import com.android.systemui.R$layout;
import com.android.systemui.R$string;
import com.android.systemui.R$style;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.qs.QSEditEvent;
import com.android.systemui.qs.QSTileHost;
import com.android.systemui.qs.customize.TileQueryHelper;
import com.android.systemui.qs.external.CustomTile;
import com.android.systemui.qs.tileimpl.QSIconViewImpl;
import com.android.systemui.qs.tileimpl.QSTileViewImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/customize/TileAdapter.class */
public class TileAdapter extends RecyclerView.Adapter<Holder> implements TileQueryHelper.TileStateListener {
    public static final int NUM_COLUMNS_ID = R$integer.quick_settings_num_columns;
    public final AccessibilityDelegateCompat mAccessibilityDelegate;
    public int mAccessibilityFromIndex;
    public List<TileQueryHelper.TileInfo> mAllTiles;
    public final ItemTouchHelper.Callback mCallbacks;
    public final Context mContext;
    public Holder mCurrentDrag;
    public List<String> mCurrentSpecs;
    public RecyclerView.ItemDecoration mDecoration;
    public int mEditIndex;
    public int mFocusIndex;
    public final QSTileHost mHost;
    public final ItemTouchHelper mItemTouchHelper;
    public final MarginTileDecoration mMarginDecoration;
    public final int mMinNumTiles;
    public boolean mNeedsFocus;
    public int mNumColumns;
    public List<TileQueryHelper.TileInfo> mOtherTiles;
    public RecyclerView mRecyclerView;
    public final GridLayoutManager.SpanSizeLookup mSizeLookup;
    public int mTileDividerIndex;
    public final UiEventLogger mUiEventLogger;
    public final Handler mHandler = new Handler();
    public final List<TileQueryHelper.TileInfo> mTiles = new ArrayList();
    public int mAccessibilityAction = 0;

    /* loaded from: mainsysui33.jar:com/android/systemui/qs/customize/TileAdapter$Holder.class */
    public class Holder extends RecyclerView.ViewHolder {
        public QSTileViewImpl mTileView;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Holder(View view) {
            super(view);
            TileAdapter.this = r4;
            if (view instanceof FrameLayout) {
                QSTileViewImpl qSTileViewImpl = (QSTileViewImpl) ((FrameLayout) view).getChildAt(0);
                this.mTileView = qSTileViewImpl;
                qSTileViewImpl.getIcon().disableAnimation();
                this.mTileView.setTag(this);
                ViewCompat.setAccessibilityDelegate(this.mTileView, r4.mAccessibilityDelegate);
            }
        }

        public final void add() {
            if (TileAdapter.this.addFromPosition(getLayoutPosition())) {
                View view = this.itemView;
                view.announceForAccessibility(view.getContext().getText(R$string.accessibility_qs_edit_tile_added));
            }
        }

        public boolean canAdd() {
            return TileAdapter.this.canAddFromPosition(getLayoutPosition());
        }

        public boolean canRemove() {
            return TileAdapter.this.canRemoveFromPosition(getLayoutPosition());
        }

        public boolean canTakeAccessibleAction() {
            return TileAdapter.this.mAccessibilityAction == 0;
        }

        public void clearDrag() {
            this.itemView.clearAnimation();
            this.itemView.setScaleX(1.0f);
            this.itemView.setScaleY(1.0f);
        }

        public CustomizeTileView getTileAsCustomizeView() {
            return (CustomizeTileView) this.mTileView;
        }

        public boolean isCurrentTile() {
            return TileAdapter.this.isCurrentTile(getLayoutPosition());
        }

        public final void remove() {
            if (TileAdapter.this.removeFromPosition(getLayoutPosition())) {
                View view = this.itemView;
                view.announceForAccessibility(view.getContext().getText(R$string.accessibility_qs_edit_tile_removed));
            }
        }

        public void startAccessibleAdd() {
            TileAdapter.this.startAccessibleAdd(getLayoutPosition());
        }

        public void startAccessibleMove() {
            TileAdapter.this.startAccessibleMove(getLayoutPosition());
        }

        public void startDrag() {
            this.itemView.animate().setDuration(100L).scaleX(1.2f).scaleY(1.2f);
        }

        public void stopDrag() {
            this.itemView.animate().setDuration(100L).scaleX(1.0f).scaleY(1.0f);
        }

        public void toggleState() {
            if (canAdd()) {
                add();
            } else {
                remove();
            }
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/qs/customize/TileAdapter$MarginTileDecoration.class */
    public static class MarginTileDecoration extends RecyclerView.ItemDecoration {
        public int mHalfMargin;

        public MarginTileDecoration() {
        }

        @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
        public void getItemOffsets(Rect rect, View view, RecyclerView recyclerView, RecyclerView.State state) {
            if (recyclerView.getLayoutManager() == null) {
                return;
            }
            GridLayoutManager gridLayoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
            int spanIndex = ((GridLayoutManager.LayoutParams) view.getLayoutParams()).getSpanIndex();
            if (view instanceof TextView) {
                super.getItemOffsets(rect, view, recyclerView, state);
            } else if (spanIndex != 0 && spanIndex != gridLayoutManager.getSpanCount() - 1) {
                int i = this.mHalfMargin;
                rect.left = i;
                rect.right = i;
            } else if (recyclerView.isLayoutRtl()) {
                if (spanIndex == 0) {
                    rect.left = this.mHalfMargin;
                    rect.right = 0;
                    return;
                }
                rect.left = 0;
                rect.right = this.mHalfMargin;
            } else if (spanIndex == 0) {
                rect.left = 0;
                rect.right = this.mHalfMargin;
            } else {
                rect.left = this.mHalfMargin;
                rect.right = 0;
            }
        }

        public void setHalfMargin(int i) {
            this.mHalfMargin = i;
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/qs/customize/TileAdapter$TileItemDecoration.class */
    public class TileItemDecoration extends RecyclerView.ItemDecoration {
        public final Drawable mDrawable;

        public TileItemDecoration(Context context) {
            TileAdapter.this = r5;
            this.mDrawable = context.getDrawable(R$drawable.qs_customize_tile_decoration);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
        public void onDraw(Canvas canvas, RecyclerView recyclerView, RecyclerView.State state) {
            super.onDraw(canvas, recyclerView, state);
            int childCount = recyclerView.getChildCount();
            int width = recyclerView.getWidth();
            int bottom = recyclerView.getBottom();
            for (int i = 0; i < childCount; i++) {
                View childAt = recyclerView.getChildAt(i);
                RecyclerView.ViewHolder childViewHolder = recyclerView.getChildViewHolder(childAt);
                if (childViewHolder != TileAdapter.this.mCurrentDrag && childViewHolder.getAdapterPosition() != 0 && (childViewHolder.getAdapterPosition() >= TileAdapter.this.mEditIndex || (childAt instanceof TextView))) {
                    this.mDrawable.setBounds(0, childAt.getTop() + Math.round(ViewCompat.getTranslationY(childAt)), width, bottom);
                    this.mDrawable.draw(canvas);
                    return;
                }
            }
        }
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.customize.TileAdapter$$ExternalSyntheticLambda0.run():void] */
    public static /* synthetic */ void $r8$lambda$ne_2B2Q9iXWeYP_hVQojF97RxoM(TileAdapter tileAdapter, int i) {
        tileAdapter.lambda$startAccessibleAdd$0(i);
    }

    public TileAdapter(Context context, QSTileHost qSTileHost, UiEventLogger uiEventLogger) {
        GridLayoutManager.SpanSizeLookup spanSizeLookup = new GridLayoutManager.SpanSizeLookup() { // from class: com.android.systemui.qs.customize.TileAdapter.5
            {
                TileAdapter.this = this;
            }

            @Override // androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
            public int getSpanSize(int i) {
                int itemViewType = TileAdapter.this.getItemViewType(i);
                if (itemViewType == 1 || itemViewType == 4 || itemViewType == 3) {
                    return TileAdapter.this.mNumColumns;
                }
                return 1;
            }
        };
        this.mSizeLookup = spanSizeLookup;
        ItemTouchHelper.Callback callback = new ItemTouchHelper.Callback() { // from class: com.android.systemui.qs.customize.TileAdapter.6
            {
                TileAdapter.this = this;
            }

            @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
            public boolean canDropOver(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder viewHolder2) {
                int adapterPosition = viewHolder2.getAdapterPosition();
                boolean z = false;
                if (adapterPosition != 0) {
                    if (adapterPosition == -1) {
                        z = false;
                    } else if (!TileAdapter.this.canRemoveTiles() && viewHolder.getAdapterPosition() < TileAdapter.this.mEditIndex) {
                        boolean z2 = false;
                        if (adapterPosition < TileAdapter.this.mEditIndex) {
                            z2 = true;
                        }
                        return z2;
                    } else {
                        z = false;
                        if (adapterPosition <= TileAdapter.this.mEditIndex + 1) {
                            z = true;
                        }
                    }
                }
                return z;
            }

            @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
            public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                ((Holder) viewHolder).stopDrag();
                super.clearView(recyclerView, viewHolder);
            }

            @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                int itemViewType = viewHolder.getItemViewType();
                return (itemViewType == 1 || itemViewType == 3 || itemViewType == 4) ? ItemTouchHelper.Callback.makeMovementFlags(0, 0) : ItemTouchHelper.Callback.makeMovementFlags(15, 0);
            }

            @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
            public boolean isItemViewSwipeEnabled() {
                return false;
            }

            @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
            public boolean isLongPressDragEnabled() {
                return true;
            }

            @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder viewHolder2) {
                int adapterPosition = viewHolder.getAdapterPosition();
                int adapterPosition2 = viewHolder2.getAdapterPosition();
                if (adapterPosition == 0 || adapterPosition == -1 || adapterPosition2 == 0 || adapterPosition2 == -1) {
                    return false;
                }
                return TileAdapter.this.move(adapterPosition, adapterPosition2);
            }

            @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
            public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int i) {
                super.onSelectedChanged(viewHolder, i);
                if (i != 2) {
                    viewHolder = null;
                }
                if (viewHolder == TileAdapter.this.mCurrentDrag) {
                    return;
                }
                if (TileAdapter.this.mCurrentDrag != null) {
                    int adapterPosition = TileAdapter.this.mCurrentDrag.getAdapterPosition();
                    if (adapterPosition == -1) {
                        return;
                    }
                    ((CustomizeTileView) TileAdapter.this.mCurrentDrag.mTileView).setShowAppLabel(adapterPosition > TileAdapter.this.mEditIndex && !((TileQueryHelper.TileInfo) TileAdapter.this.mTiles.get(adapterPosition)).isSystem);
                    TileAdapter.this.mCurrentDrag.stopDrag();
                    TileAdapter.this.mCurrentDrag = null;
                }
                if (viewHolder != null) {
                    TileAdapter.this.mCurrentDrag = (Holder) viewHolder;
                    TileAdapter.this.mCurrentDrag.startDrag();
                }
                TileAdapter.this.mHandler.post(new Runnable() { // from class: com.android.systemui.qs.customize.TileAdapter.6.1
                    {
                        AnonymousClass6.this = this;
                    }

                    @Override // java.lang.Runnable
                    public void run() {
                        TileAdapter tileAdapter = TileAdapter.this;
                        tileAdapter.notifyItemChanged(tileAdapter.mEditIndex);
                    }
                });
            }

            @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int i) {
            }
        };
        this.mCallbacks = callback;
        this.mContext = context;
        this.mHost = qSTileHost;
        this.mUiEventLogger = uiEventLogger;
        this.mItemTouchHelper = new ItemTouchHelper(callback);
        this.mDecoration = new TileItemDecoration(context);
        this.mMarginDecoration = new MarginTileDecoration();
        this.mMinNumTiles = context.getResources().getInteger(R$integer.quick_settings_min_num_tiles);
        this.mNumColumns = context.getResources().getInteger(NUM_COLUMNS_ID);
        this.mAccessibilityDelegate = new TileAdapterDelegate();
        spanSizeLookup.setSpanIndexCacheEnabled(true);
    }

    public static int calculateHeaderMinHeight(Context context) {
        Resources resources = context.getResources();
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(R$style.QSCustomizeToolbar, R.styleable.Toolbar);
        int i = 0;
        int resourceId = obtainStyledAttributes.getResourceId(27, 0);
        obtainStyledAttributes.recycle();
        if (resourceId != 0) {
            TypedArray obtainStyledAttributes2 = context.obtainStyledAttributes(resourceId, android.R.styleable.View);
            i = obtainStyledAttributes2.getDimensionPixelSize(36, 0);
            obtainStyledAttributes2.recycle();
        }
        return ((((resources.getDimensionPixelSize(R$dimen.qs_panel_padding_top) + resources.getDimensionPixelSize(R$dimen.brightness_mirror_height)) + resources.getDimensionPixelSize(R$dimen.qs_brightness_margin_top)) + resources.getDimensionPixelSize(R$dimen.qs_brightness_margin_bottom)) - i) - resources.getDimensionPixelSize(R$dimen.qs_tile_margin_top_bottom);
    }

    public /* synthetic */ void lambda$startAccessibleAdd$0(int i) {
        RecyclerView recyclerView = this.mRecyclerView;
        if (recyclerView != null) {
            recyclerView.smoothScrollToPosition(i);
        }
    }

    public static String strip(TileQueryHelper.TileInfo tileInfo) {
        String str = tileInfo.spec;
        String str2 = str;
        if (str.startsWith("custom(")) {
            str2 = CustomTile.getComponentFromSpec(str).getPackageName();
        }
        return str2;
    }

    public final boolean addFromPosition(int i) {
        if (canAddFromPosition(i)) {
            move(i, this.mEditIndex);
            return true;
        }
        return false;
    }

    public final boolean canAddFromPosition(int i) {
        return i > this.mEditIndex;
    }

    public final boolean canRemoveFromPosition(int i) {
        return canRemoveTiles() && isCurrentTile(i);
    }

    public final boolean canRemoveTiles() {
        return this.mCurrentSpecs.size() > this.mMinNumTiles;
    }

    public void changeHalfMargin(int i) {
        this.mMarginDecoration.setHalfMargin(i);
    }

    public final void clearAccessibilityState() {
        this.mNeedsFocus = false;
        if (this.mAccessibilityAction == 1) {
            List<TileQueryHelper.TileInfo> list = this.mTiles;
            int i = this.mEditIndex - 1;
            this.mEditIndex = i;
            list.remove(i);
            notifyDataSetChanged();
        }
        this.mAccessibilityAction = 0;
    }

    public final void focusOnHolder(final Holder holder) {
        if (this.mNeedsFocus) {
            holder.mTileView.requestLayout();
            holder.mTileView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() { // from class: com.android.systemui.qs.customize.TileAdapter.4
                {
                    TileAdapter.this = this;
                }

                @Override // android.view.View.OnLayoutChangeListener
                public void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                    holder.mTileView.removeOnLayoutChangeListener(this);
                    holder.mTileView.requestAccessibilityFocus();
                }
            });
            this.mNeedsFocus = false;
            this.mFocusIndex = -1;
        }
    }

    public final TileQueryHelper.TileInfo getAndRemoveOther(String str) {
        for (int i = 0; i < this.mOtherTiles.size(); i++) {
            if (this.mOtherTiles.get(i).spec.equals(str)) {
                return this.mOtherTiles.remove(i);
            }
        }
        return null;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.mTiles.size();
    }

    public RecyclerView.ItemDecoration getItemDecoration() {
        return this.mDecoration;
    }

    public ItemTouchHelper getItemTouchHelper() {
        return this.mItemTouchHelper;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemViewType(int i) {
        if (i == 0) {
            return 3;
        }
        if (this.mAccessibilityAction == 1 && i == this.mEditIndex - 1) {
            return 2;
        }
        if (i == this.mTileDividerIndex) {
            return 4;
        }
        return this.mTiles.get(i) == null ? 1 : 0;
    }

    public RecyclerView.ItemDecoration getMarginItemDecoration() {
        return this.mMarginDecoration;
    }

    public int getNumColumns() {
        return this.mNumColumns;
    }

    public GridLayoutManager.SpanSizeLookup getSizeLookup() {
        return this.mSizeLookup;
    }

    public final boolean isCurrentTile(int i) {
        return i < this.mEditIndex;
    }

    public final <T> void move(int i, int i2, List<T> list, boolean z) {
        list.add(i2, list.remove(i));
        if (z) {
            notifyItemMoved(i, i2);
        }
    }

    public final boolean move(int i, int i2) {
        return move(i, i2, true);
    }

    public final boolean move(int i, int i2, boolean z) {
        if (i2 == i) {
            return true;
        }
        move(i, i2, this.mTiles, z);
        updateDividerLocations();
        int i3 = this.mEditIndex;
        if (i2 >= i3) {
            this.mUiEventLogger.log(QSEditEvent.QS_EDIT_REMOVE, 0, strip(this.mTiles.get(i2)));
        } else if (i >= i3) {
            this.mUiEventLogger.log(QSEditEvent.QS_EDIT_ADD, 0, strip(this.mTiles.get(i2)));
        } else {
            this.mUiEventLogger.log(QSEditEvent.QS_EDIT_MOVE, 0, strip(this.mTiles.get(i2)));
        }
        saveSpecs(this.mHost);
        return true;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        this.mRecyclerView = recyclerView;
    }

    /* JADX WARN: Code restructure failed: missing block: B:127:0x0209, code lost:
        if (r0.isSystem != false) goto L62;
     */
    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void onBindViewHolder(final Holder holder, int i) {
        boolean z;
        if (holder.getItemViewType() == 3) {
            setSelectableForHeaders(holder.itemView);
            return;
        }
        int i2 = 4;
        if (holder.getItemViewType() == 4) {
            View view = holder.itemView;
            if (this.mTileDividerIndex < this.mTiles.size() - 1) {
                i2 = 0;
            }
            view.setVisibility(i2);
        } else if (holder.getItemViewType() == 1) {
            Resources resources = this.mContext.getResources();
            ((TextView) holder.itemView.findViewById(16908310)).setText(this.mCurrentDrag == null ? resources.getString(R$string.tap_to_add_tiles) : (canRemoveTiles() || this.mCurrentDrag.getAdapterPosition() >= this.mEditIndex) ? resources.getString(R$string.drag_to_remove_tiles) : resources.getString(R$string.drag_to_remove_disabled, Integer.valueOf(this.mMinNumTiles)));
            setSelectableForHeaders(holder.itemView);
        } else if (holder.getItemViewType() == 2) {
            holder.mTileView.setClickable(true);
            holder.mTileView.setFocusable(true);
            holder.mTileView.setFocusableInTouchMode(true);
            holder.mTileView.setVisibility(0);
            holder.mTileView.setImportantForAccessibility(1);
            holder.mTileView.setContentDescription(this.mContext.getString(R$string.accessibility_qs_edit_tile_add_to_position, Integer.valueOf(i)));
            holder.mTileView.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.qs.customize.TileAdapter.1
                {
                    TileAdapter.this = this;
                }

                @Override // android.view.View.OnClickListener
                public void onClick(View view2) {
                    TileAdapter.this.selectPosition(holder.getLayoutPosition());
                }
            });
            focusOnHolder(holder);
        } else {
            TileQueryHelper.TileInfo tileInfo = this.mTiles.get(i);
            boolean z2 = i > 0 && i < this.mEditIndex;
            if (z2 && this.mAccessibilityAction == 1) {
                tileInfo.state.contentDescription = this.mContext.getString(R$string.accessibility_qs_edit_tile_add_to_position, Integer.valueOf(i));
            } else if (z2 && this.mAccessibilityAction == 2) {
                tileInfo.state.contentDescription = this.mContext.getString(R$string.accessibility_qs_edit_tile_move_to_position, Integer.valueOf(i));
            } else {
                QSTile.State state = tileInfo.state;
                state.contentDescription = state.label;
            }
            tileInfo.state.expandedAccessibilityClassName = "";
            CustomizeTileView tileAsCustomizeView = holder.getTileAsCustomizeView();
            Objects.requireNonNull(tileAsCustomizeView, "The holder must have a tileView");
            tileAsCustomizeView.changeState(tileInfo.state);
            tileAsCustomizeView.setShowAppLabel(i > this.mEditIndex && !tileInfo.isSystem);
            if (i >= this.mEditIndex) {
                z = false;
            }
            z = true;
            tileAsCustomizeView.setShowSideView(z);
            holder.mTileView.setSelected(true);
            holder.mTileView.setImportantForAccessibility(1);
            holder.mTileView.setClickable(true);
            holder.mTileView.setOnClickListener(null);
            holder.mTileView.setFocusable(true);
            holder.mTileView.setFocusableInTouchMode(true);
            if (this.mAccessibilityAction != 0) {
                holder.mTileView.setClickable(z2);
                holder.mTileView.setFocusable(z2);
                holder.mTileView.setFocusableInTouchMode(z2);
                QSTileViewImpl qSTileViewImpl = holder.mTileView;
                if (z2) {
                    i2 = 1;
                }
                qSTileViewImpl.setImportantForAccessibility(i2);
                if (z2) {
                    holder.mTileView.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.qs.customize.TileAdapter.2
                        {
                            TileAdapter.this = this;
                        }

                        @Override // android.view.View.OnClickListener
                        public void onClick(View view2) {
                            int layoutPosition = holder.getLayoutPosition();
                            if (layoutPosition == -1 || TileAdapter.this.mAccessibilityAction == 0) {
                                return;
                            }
                            TileAdapter.this.selectPosition(layoutPosition);
                        }
                    });
                }
            }
            if (i == this.mFocusIndex) {
                focusOnHolder(holder);
            }
            holder.mTileView.setOnTouchListener(new View.OnTouchListener() { // from class: com.android.systemui.qs.customize.TileAdapter.3
                {
                    TileAdapter.this = this;
                }

                @Override // android.view.View.OnTouchListener
                public boolean onTouch(View view2, MotionEvent motionEvent) {
                    if (motionEvent.getAction() == 1) {
                        int layoutPosition = holder.getLayoutPosition();
                        if (layoutPosition >= TileAdapter.this.mEditIndex) {
                            TileAdapter tileAdapter = TileAdapter.this;
                            tileAdapter.move(layoutPosition, tileAdapter.mEditIndex, true);
                            return false;
                        } else if (TileAdapter.this.canRemoveTiles()) {
                            TileAdapter tileAdapter2 = TileAdapter.this;
                            tileAdapter2.move(layoutPosition, tileAdapter2.mEditIndex, true);
                            return false;
                        } else {
                            return false;
                        }
                    }
                    return false;
                }
            });
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public Holder onCreateViewHolder(ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater from = LayoutInflater.from(context);
        if (i == 3) {
            View inflate = from.inflate(R$layout.qs_customize_header, viewGroup, false);
            inflate.setMinimumHeight(calculateHeaderMinHeight(context));
            return new Holder(inflate);
        } else if (i == 4) {
            return new Holder(from.inflate(R$layout.qs_customize_tile_divider, viewGroup, false));
        } else {
            if (i == 1) {
                return new Holder(from.inflate(R$layout.qs_customize_divider, viewGroup, false));
            }
            FrameLayout frameLayout = (FrameLayout) from.inflate(R$layout.qs_customize_tile_frame, viewGroup, false);
            frameLayout.addView(new CustomizeTileView(context, new QSIconViewImpl(context)));
            return new Holder(frameLayout);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        this.mRecyclerView = null;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public boolean onFailedToRecycleView(Holder holder) {
        holder.stopDrag();
        holder.clearDrag();
        return true;
    }

    @Override // com.android.systemui.qs.customize.TileQueryHelper.TileStateListener
    public void onTilesChanged(List<TileQueryHelper.TileInfo> list) {
        this.mAllTiles = list;
        recalcSpecs();
    }

    public final void recalcSpecs() {
        if (this.mCurrentSpecs == null || this.mAllTiles == null) {
            return;
        }
        this.mOtherTiles = new ArrayList(this.mAllTiles);
        this.mTiles.clear();
        this.mTiles.add(null);
        for (int i = 0; i < this.mCurrentSpecs.size(); i++) {
            TileQueryHelper.TileInfo andRemoveOther = getAndRemoveOther(this.mCurrentSpecs.get(i));
            if (andRemoveOther != null) {
                this.mTiles.add(andRemoveOther);
            }
        }
        this.mTiles.add(null);
        int i2 = 0;
        while (true) {
            int i3 = i2;
            if (i3 >= this.mOtherTiles.size()) {
                this.mTileDividerIndex = this.mTiles.size();
                this.mTiles.add(null);
                this.mTiles.addAll(this.mOtherTiles);
                updateDividerLocations();
                notifyDataSetChanged();
                return;
            }
            TileQueryHelper.TileInfo tileInfo = this.mOtherTiles.get(i3);
            int i4 = i3;
            if (tileInfo.isSystem) {
                this.mOtherTiles.remove(i3);
                this.mTiles.add(tileInfo);
                i4 = i3 - 1;
            }
            i2 = i4 + 1;
        }
    }

    public final boolean removeFromPosition(int i) {
        if (canRemoveFromPosition(i)) {
            move(i, this.mTiles.get(i).isSystem ? this.mEditIndex : this.mTileDividerIndex);
            return true;
        }
        return false;
    }

    public void resetTileSpecs(List<String> list) {
        this.mHost.changeTilesByUser(this.mCurrentSpecs, list);
        setTileSpecs(list);
    }

    public void saveSpecs(QSTileHost qSTileHost) {
        ArrayList arrayList = new ArrayList();
        clearAccessibilityState();
        for (int i = 1; i < this.mTiles.size() && this.mTiles.get(i) != null; i++) {
            arrayList.add(this.mTiles.get(i).spec);
        }
        qSTileHost.changeTilesByUser(this.mCurrentSpecs, arrayList);
        this.mCurrentSpecs = arrayList;
    }

    public final void selectPosition(int i) {
        if (this.mAccessibilityAction == 1) {
            List<TileQueryHelper.TileInfo> list = this.mTiles;
            int i2 = this.mEditIndex;
            this.mEditIndex = i2 - 1;
            list.remove(i2);
        }
        this.mAccessibilityAction = 0;
        move(this.mAccessibilityFromIndex, i, false);
        this.mFocusIndex = i;
        this.mNeedsFocus = true;
        notifyDataSetChanged();
    }

    public final void setSelectableForHeaders(View view) {
        int i = 1;
        boolean z = this.mAccessibilityAction == 0;
        view.setFocusable(z);
        if (!z) {
            i = 4;
        }
        view.setImportantForAccessibility(i);
        view.setFocusableInTouchMode(z);
    }

    public void setTileSpecs(List<String> list) {
        if (list.equals(this.mCurrentSpecs)) {
            return;
        }
        this.mCurrentSpecs = list;
        recalcSpecs();
    }

    public final void startAccessibleAdd(int i) {
        this.mAccessibilityFromIndex = i;
        this.mAccessibilityAction = 1;
        List<TileQueryHelper.TileInfo> list = this.mTiles;
        int i2 = this.mEditIndex;
        this.mEditIndex = i2 + 1;
        list.add(i2, null);
        this.mTileDividerIndex++;
        final int i3 = this.mEditIndex - 1;
        this.mFocusIndex = i3;
        this.mNeedsFocus = true;
        RecyclerView recyclerView = this.mRecyclerView;
        if (recyclerView != null) {
            recyclerView.post(new Runnable() { // from class: com.android.systemui.qs.customize.TileAdapter$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    TileAdapter.$r8$lambda$ne_2B2Q9iXWeYP_hVQojF97RxoM(TileAdapter.this, i3);
                }
            });
        }
        notifyDataSetChanged();
    }

    public final void startAccessibleMove(int i) {
        this.mAccessibilityFromIndex = i;
        this.mAccessibilityAction = 2;
        this.mFocusIndex = i;
        this.mNeedsFocus = true;
        notifyDataSetChanged();
    }

    public final void updateDividerLocations() {
        this.mEditIndex = -1;
        this.mTileDividerIndex = this.mTiles.size();
        for (int i = 1; i < this.mTiles.size(); i++) {
            if (this.mTiles.get(i) == null) {
                if (this.mEditIndex == -1) {
                    this.mEditIndex = i;
                } else {
                    this.mTileDividerIndex = i;
                }
            }
        }
        int size = this.mTiles.size();
        int i2 = this.mTileDividerIndex;
        if (size - 1 == i2) {
            notifyItemChanged(i2);
        }
    }

    public boolean updateNumColumns() {
        int integer = this.mContext.getResources().getInteger(NUM_COLUMNS_ID);
        if (integer != this.mNumColumns) {
            this.mNumColumns = integer;
            return true;
        }
        return false;
    }
}