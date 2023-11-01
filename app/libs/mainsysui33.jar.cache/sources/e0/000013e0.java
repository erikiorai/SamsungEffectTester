package com.android.systemui.controls.management;

import android.content.ComponentName;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.RecyclerView;
import com.android.systemui.R$id;
import com.android.systemui.R$layout;
import com.android.systemui.controls.ControlsServiceInfo;
import com.android.systemui.controls.management.ControlsListingController;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Executor;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.functions.Function1;

/* loaded from: mainsysui33.jar:com/android/systemui/controls/management/AppAdapter.class */
public final class AppAdapter extends RecyclerView.Adapter<Holder> {
    public final AppAdapter$callback$1 callback;
    public final FavoritesRenderer favoritesRenderer;
    public final LayoutInflater layoutInflater;
    public List<ControlsServiceInfo> listOfServices = CollectionsKt__CollectionsKt.emptyList();
    public final Function1<ComponentName, Unit> onAppSelected;
    public final Resources resources;

    /* loaded from: mainsysui33.jar:com/android/systemui/controls/management/AppAdapter$Holder.class */
    public static final class Holder extends RecyclerView.ViewHolder {
        public final FavoritesRenderer favRenderer;
        public final TextView favorites;
        public final ImageView icon;
        public final TextView title;

        public Holder(View view, FavoritesRenderer favoritesRenderer) {
            super(view);
            this.favRenderer = favoritesRenderer;
            this.icon = (ImageView) this.itemView.requireViewById(16908294);
            this.title = (TextView) this.itemView.requireViewById(16908310);
            this.favorites = (TextView) this.itemView.requireViewById(R$id.favorites);
        }

        public final void bindData(ControlsServiceInfo controlsServiceInfo) {
            this.icon.setImageDrawable(controlsServiceInfo.loadIcon());
            this.title.setText(controlsServiceInfo.loadLabel());
            String renderFavoritesForComponent = this.favRenderer.renderFavoritesForComponent(controlsServiceInfo.componentName);
            this.favorites.setText(renderFavoritesForComponent);
            this.favorites.setVisibility(renderFavoritesForComponent == null ? 8 : 0);
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for r12v0, resolved type: kotlin.jvm.functions.Function1<? super android.content.ComponentName, kotlin.Unit> */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v6, types: [com.android.systemui.controls.management.AppAdapter$callback$1, java.lang.Object] */
    public AppAdapter(final Executor executor, final Executor executor2, Lifecycle lifecycle, ControlsListingController controlsListingController, LayoutInflater layoutInflater, Function1<? super ComponentName, Unit> function1, FavoritesRenderer favoritesRenderer, Resources resources) {
        this.layoutInflater = layoutInflater;
        this.onAppSelected = function1;
        this.favoritesRenderer = favoritesRenderer;
        this.resources = resources;
        ?? r0 = new ControlsListingController.ControlsListingCallback() { // from class: com.android.systemui.controls.management.AppAdapter$callback$1
            @Override // com.android.systemui.controls.management.ControlsListingController.ControlsListingCallback
            public void onServicesUpdated(final List<ControlsServiceInfo> list) {
                Executor executor3 = executor;
                final AppAdapter appAdapter = this;
                final Executor executor4 = executor2;
                executor3.execute(new Runnable() { // from class: com.android.systemui.controls.management.AppAdapter$callback$1$onServicesUpdated$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        final Collator collator = Collator.getInstance(AppAdapter.access$getResources$p(AppAdapter.this).getConfiguration().getLocales().get(0));
                        Comparator comparator = new Comparator() { // from class: com.android.systemui.controls.management.AppAdapter$callback$1$onServicesUpdated$1$run$$inlined$compareBy$1
                            @Override // java.util.Comparator
                            public final int compare(T t, T t2) {
                                Comparator comparator2 = collator;
                                CharSequence loadLabel = ((ControlsServiceInfo) t).loadLabel();
                                CharSequence charSequence = loadLabel;
                                if (loadLabel == null) {
                                    charSequence = "";
                                }
                                CharSequence loadLabel2 = ((ControlsServiceInfo) t2).loadLabel();
                                if (loadLabel2 == null) {
                                    loadLabel2 = "";
                                }
                                return comparator2.compare(charSequence, loadLabel2);
                            }
                        };
                        AppAdapter appAdapter2 = AppAdapter.this;
                        List<ControlsServiceInfo> list2 = list;
                        ArrayList arrayList = new ArrayList();
                        for (Object obj : list2) {
                            if (((ControlsServiceInfo) obj).getPanelActivity() == null) {
                                arrayList.add(obj);
                            }
                        }
                        AppAdapter.access$setListOfServices$p(appAdapter2, CollectionsKt___CollectionsKt.sortedWith(arrayList, comparator));
                        Executor executor5 = executor4;
                        final AppAdapter appAdapter3 = AppAdapter.this;
                        executor5.execute(new Runnable() { // from class: com.android.systemui.controls.management.AppAdapter$callback$1$onServicesUpdated$1.2
                            @Override // java.lang.Runnable
                            public final void run() {
                                AppAdapter.this.notifyDataSetChanged();
                            }
                        });
                    }
                });
            }
        };
        this.callback = r0;
        controlsListingController.observe(lifecycle, (Object) r0);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.controls.management.AppAdapter$onBindViewHolder$1.onClick(android.view.View):void] */
    public static final /* synthetic */ List access$getListOfServices$p(AppAdapter appAdapter) {
        return appAdapter.listOfServices;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.controls.management.AppAdapter$onBindViewHolder$1.onClick(android.view.View):void] */
    public static final /* synthetic */ Function1 access$getOnAppSelected$p(AppAdapter appAdapter) {
        return appAdapter.onAppSelected;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.controls.management.AppAdapter$callback$1$onServicesUpdated$1.run():void] */
    public static final /* synthetic */ Resources access$getResources$p(AppAdapter appAdapter) {
        return appAdapter.resources;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.controls.management.AppAdapter$callback$1$onServicesUpdated$1.run():void] */
    public static final /* synthetic */ void access$setListOfServices$p(AppAdapter appAdapter, List list) {
        appAdapter.listOfServices = list;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.listOfServices.size();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(Holder holder, final int i) {
        holder.bindData(this.listOfServices.get(i));
        holder.itemView.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.controls.management.AppAdapter$onBindViewHolder$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                AppAdapter.access$getOnAppSelected$p(AppAdapter.this).invoke(ComponentName.unflattenFromString(((ControlsServiceInfo) AppAdapter.access$getListOfServices$p(AppAdapter.this).get(i)).getKey()));
            }
        });
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public Holder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new Holder(this.layoutInflater.inflate(R$layout.controls_app_item, viewGroup, false), this.favoritesRenderer);
    }
}