package com.android.systemui.controls.management;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.systemui.R$dimen;
import com.android.systemui.R$id;
import com.android.systemui.R$layout;
import java.util.List;

/* loaded from: mainsysui33.jar:com/android/systemui/controls/management/StructureAdapter.class */
public final class StructureAdapter extends RecyclerView.Adapter<StructureHolder> {
    public final List<StructureContainer> models;

    /* loaded from: mainsysui33.jar:com/android/systemui/controls/management/StructureAdapter$StructureHolder.class */
    public static final class StructureHolder extends RecyclerView.ViewHolder {
        public final ControlAdapter controlAdapter;
        public final RecyclerView recyclerView;

        public StructureHolder(View view) {
            super(view);
            this.recyclerView = (RecyclerView) this.itemView.requireViewById(R$id.listAll);
            this.controlAdapter = new ControlAdapter(this.itemView.getContext().getResources().getFloat(R$dimen.control_card_elevation));
            setUpRecyclerView();
        }

        public final void bind(ControlsModel controlsModel) {
            this.controlAdapter.changeModel(controlsModel);
        }

        public final void setUpRecyclerView() {
            int dimensionPixelSize = this.itemView.getContext().getResources().getDimensionPixelSize(R$dimen.controls_card_margin);
            MarginItemDecorator marginItemDecorator = new MarginItemDecorator(dimensionPixelSize, dimensionPixelSize);
            final int findMaxColumns = ControlAdapter.Companion.findMaxColumns(this.itemView.getResources());
            final RecyclerView recyclerView = this.recyclerView;
            recyclerView.setAdapter(this.controlAdapter);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(this.recyclerView.getContext(), findMaxColumns);
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() { // from class: com.android.systemui.controls.management.StructureAdapter$StructureHolder$setUpRecyclerView$1$1$1
                @Override // androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
                public int getSpanSize(int i) {
                    RecyclerView.Adapter adapter = RecyclerView.this.getAdapter();
                    boolean z = false;
                    if (adapter != null) {
                        z = false;
                        if (adapter.getItemViewType(i) == 1) {
                            z = true;
                        }
                    }
                    int i2 = 1;
                    if (!z) {
                        i2 = findMaxColumns;
                    }
                    return i2;
                }
            });
            recyclerView.setLayoutManager(gridLayoutManager);
            recyclerView.addItemDecoration(marginItemDecorator);
        }
    }

    public StructureAdapter(List<StructureContainer> list) {
        this.models = list;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.models.size();
    }

    /* JADX DEBUG: Method merged with bridge method */
    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(StructureHolder structureHolder, int i) {
        structureHolder.bind(this.models.get(i).getModel());
    }

    /* JADX DEBUG: Method merged with bridge method */
    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public StructureHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new StructureHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R$layout.controls_structure_page, viewGroup, false));
    }
}