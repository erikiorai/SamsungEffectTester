package com.android.systemui.controls.management;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.android.systemui.R$dimen;
import com.android.systemui.R$drawable;
import com.android.systemui.R$integer;
import com.android.systemui.R$layout;
import com.android.systemui.controls.ControlInterface;
import java.util.List;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: mainsysui33.jar:com/android/systemui/controls/management/ControlAdapter.class */
public final class ControlAdapter extends RecyclerView.Adapter<Holder> {
    public static final Companion Companion = new Companion(null);
    public final float elevation;
    public ControlsModel model;

    /* loaded from: mainsysui33.jar:com/android/systemui/controls/management/ControlAdapter$Companion.class */
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final int findMaxColumns(Resources resources) {
            int integer = resources.getInteger(R$integer.controls_max_columns);
            int integer2 = resources.getInteger(R$integer.controls_max_columns_adjust_below_width_dp);
            TypedValue typedValue = new TypedValue();
            boolean z = true;
            resources.getValue(R$dimen.controls_max_columns_adjust_above_font_scale, typedValue, true);
            float f = typedValue.getFloat();
            Configuration configuration = resources.getConfiguration();
            if (configuration.orientation != 1) {
                z = false;
            }
            int i = integer;
            if (z) {
                int i2 = configuration.screenWidthDp;
                i = integer;
                if (i2 != 0) {
                    i = integer;
                    if (i2 <= integer2) {
                        i = integer;
                        if (configuration.fontScale >= f) {
                            i = integer - 1;
                        }
                    }
                }
            }
            return i;
        }
    }

    public ControlAdapter(float f) {
        this.elevation = f;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.controls.management.ControlAdapter$onCreateViewHolder$2.invoke(java.lang.String, boolean):void] */
    public static final /* synthetic */ ControlsModel access$getModel$p(ControlAdapter controlAdapter) {
        return controlAdapter.model;
    }

    public final void changeModel(ControlsModel controlsModel) {
        this.model = controlsModel;
        notifyDataSetChanged();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        List<ElementWrapper> elements;
        ControlsModel controlsModel = this.model;
        return (controlsModel == null || (elements = controlsModel.getElements()) == null) ? 0 : elements.size();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemViewType(int i) {
        ControlsModel controlsModel = this.model;
        if (controlsModel != null) {
            ElementWrapper elementWrapper = controlsModel.getElements().get(i);
            int i2 = 1;
            if (elementWrapper instanceof ZoneNameWrapper) {
                i2 = 0;
            } else if (!(elementWrapper instanceof ControlStatusWrapper) && !(elementWrapper instanceof ControlInfoWrapper)) {
                if (!(elementWrapper instanceof DividerWrapper)) {
                    throw new NoWhenBranchMatchedException();
                }
                i2 = 2;
            }
            return i2;
        }
        throw new IllegalStateException("Getting item type for null model");
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public /* bridge */ /* synthetic */ void onBindViewHolder(Holder holder, int i, List list) {
        onBindViewHolder2(holder, i, (List<Object>) list);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(Holder holder, int i) {
        ControlsModel controlsModel = this.model;
        if (controlsModel != null) {
            holder.bindData(controlsModel.getElements().get(i));
        }
    }

    /* renamed from: onBindViewHolder */
    public void onBindViewHolder2(Holder holder, int i, List<Object> list) {
        if (list.isEmpty()) {
            super.onBindViewHolder((ControlAdapter) holder, i, list);
            return;
        }
        ControlsModel controlsModel = this.model;
        if (controlsModel != null) {
            ElementWrapper elementWrapper = controlsModel.getElements().get(i);
            if (elementWrapper instanceof ControlInterface) {
                holder.updateFavorite(((ControlInterface) elementWrapper).getFavorite());
            }
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public Holder onCreateViewHolder(ViewGroup viewGroup, int i) {
        Holder zoneHolder;
        LayoutInflater from = LayoutInflater.from(viewGroup.getContext());
        if (i == 0) {
            zoneHolder = new ZoneHolder(from.inflate(R$layout.controls_zone_header, viewGroup, false));
        } else if (i == 1) {
            View inflate = from.inflate(R$layout.controls_base_item, viewGroup, false);
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) inflate.getLayoutParams();
            marginLayoutParams.width = -1;
            marginLayoutParams.topMargin = 0;
            marginLayoutParams.bottomMargin = 0;
            marginLayoutParams.leftMargin = 0;
            marginLayoutParams.rightMargin = 0;
            inflate.setElevation(this.elevation);
            inflate.setBackground(viewGroup.getContext().getDrawable(R$drawable.control_background_ripple));
            ControlsModel controlsModel = this.model;
            zoneHolder = new ControlHolder(inflate, controlsModel != null ? controlsModel.getMoveHelper() : null, new Function2<String, Boolean, Unit>() { // from class: com.android.systemui.controls.management.ControlAdapter$onCreateViewHolder$2
                {
                    super(2);
                }

                public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
                    invoke((String) obj, ((Boolean) obj2).booleanValue());
                    return Unit.INSTANCE;
                }

                public final void invoke(String str, boolean z) {
                    ControlsModel access$getModel$p = ControlAdapter.access$getModel$p(ControlAdapter.this);
                    if (access$getModel$p != null) {
                        access$getModel$p.changeFavoriteStatus(str, z);
                    }
                }
            });
        } else if (i != 2) {
            throw new IllegalStateException("Wrong viewType: " + i);
        } else {
            zoneHolder = new DividerHolder(from.inflate(R$layout.controls_horizontal_divider_with_empty, viewGroup, false));
        }
        return zoneHolder;
    }
}