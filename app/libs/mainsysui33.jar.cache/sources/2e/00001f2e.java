package com.android.systemui.people.ui.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import com.android.systemui.R$dimen;
import com.android.systemui.people.PeopleTileViewHelper;
import com.android.systemui.people.data.model.PeopleTileModel;
import com.android.systemui.people.data.repository.PeopleTileRepository;
import com.android.systemui.people.data.repository.PeopleWidgetRepository;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.MutableStateFlow;
import kotlinx.coroutines.flow.StateFlow;
import kotlinx.coroutines.flow.StateFlowKt;

/* loaded from: mainsysui33.jar:com/android/systemui/people/ui/viewmodel/PeopleViewModel.class */
public final class PeopleViewModel extends ViewModel {
    public static final Companion Companion = new Companion(null);
    public final MutableStateFlow<Integer> _appWidgetId;
    public final MutableStateFlow<List<PeopleTileViewModel>> _priorityTiles;
    public final MutableStateFlow<List<PeopleTileViewModel>> _recentTiles;
    public final MutableStateFlow<Result> _result;
    public final StateFlow<Integer> appWidgetId;
    public final Context context;
    public final StateFlow<List<PeopleTileViewModel>> priorityTiles;
    public final StateFlow<List<PeopleTileViewModel>> recentTiles;
    public final StateFlow<Result> result;
    public final PeopleTileRepository tileRepository;
    public final PeopleWidgetRepository widgetRepository;

    /* loaded from: mainsysui33.jar:com/android/systemui/people/ui/viewmodel/PeopleViewModel$Companion.class */
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/people/ui/viewmodel/PeopleViewModel$Factory.class */
    public static final class Factory implements ViewModelProvider.Factory {
        public final Context context;
        public final PeopleTileRepository tileRepository;
        public final PeopleWidgetRepository widgetRepository;

        public Factory(Context context, PeopleTileRepository peopleTileRepository, PeopleWidgetRepository peopleWidgetRepository) {
            this.context = context;
            this.tileRepository = peopleTileRepository;
            this.widgetRepository = peopleWidgetRepository;
        }

        @Override // androidx.lifecycle.ViewModelProvider.Factory
        public <T extends ViewModel> T create(Class<T> cls) {
            if (Intrinsics.areEqual(cls, PeopleViewModel.class)) {
                return new PeopleViewModel(this.context, this.tileRepository, this.widgetRepository);
            }
            throw new IllegalStateException("Check failed.".toString());
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/people/ui/viewmodel/PeopleViewModel$Result.class */
    public static abstract class Result {

        /* loaded from: mainsysui33.jar:com/android/systemui/people/ui/viewmodel/PeopleViewModel$Result$Cancelled.class */
        public static final class Cancelled extends Result {
            public static final Cancelled INSTANCE = new Cancelled();

            public Cancelled() {
                super(null);
            }
        }

        /* loaded from: mainsysui33.jar:com/android/systemui/people/ui/viewmodel/PeopleViewModel$Result$Success.class */
        public static final class Success extends Result {
            public final Intent data;

            public Success(Intent intent) {
                super(null);
                this.data = intent;
            }

            public final Intent getData() {
                return this.data;
            }
        }

        public Result() {
        }

        public /* synthetic */ Result(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public PeopleViewModel(Context context, PeopleTileRepository peopleTileRepository, PeopleWidgetRepository peopleWidgetRepository) {
        this.context = context;
        this.tileRepository = peopleTileRepository;
        this.widgetRepository = peopleWidgetRepository;
        MutableStateFlow<List<PeopleTileViewModel>> MutableStateFlow = StateFlowKt.MutableStateFlow(priorityTiles());
        this._priorityTiles = MutableStateFlow;
        this.priorityTiles = FlowKt.asStateFlow(MutableStateFlow);
        MutableStateFlow<List<PeopleTileViewModel>> MutableStateFlow2 = StateFlowKt.MutableStateFlow(recentTiles());
        this._recentTiles = MutableStateFlow2;
        this.recentTiles = FlowKt.asStateFlow(MutableStateFlow2);
        MutableStateFlow<Integer> MutableStateFlow3 = StateFlowKt.MutableStateFlow(0);
        this._appWidgetId = MutableStateFlow3;
        this.appWidgetId = FlowKt.asStateFlow(MutableStateFlow3);
        MutableStateFlow<Result> MutableStateFlow4 = StateFlowKt.MutableStateFlow((Object) null);
        this._result = MutableStateFlow4;
        this.result = FlowKt.asStateFlow(MutableStateFlow4);
    }

    public final void clearResult() {
        this._result.setValue((Object) null);
    }

    public final StateFlow<List<PeopleTileViewModel>> getPriorityTiles() {
        return this.priorityTiles;
    }

    public final StateFlow<List<PeopleTileViewModel>> getRecentTiles() {
        return this.recentTiles;
    }

    public final StateFlow<Result> getResult() {
        return this.result;
    }

    public final void onTileClicked(PeopleTileViewModel peopleTileViewModel) {
        this.widgetRepository.setWidgetTile(((Number) this._appWidgetId.getValue()).intValue(), peopleTileViewModel.getKey());
        MutableStateFlow<Result> mutableStateFlow = this._result;
        Intent intent = new Intent();
        intent.putExtra("appWidgetId", ((Number) this.appWidgetId.getValue()).intValue());
        mutableStateFlow.setValue(new Result.Success(intent));
    }

    public final void onTileRefreshRequested() {
        this._priorityTiles.setValue(priorityTiles());
        this._recentTiles.setValue(recentTiles());
    }

    public final void onUserJourneyCancelled() {
        this._result.setValue(Result.Cancelled.INSTANCE);
    }

    public final void onWidgetIdChanged(int i) {
        this._appWidgetId.setValue(Integer.valueOf(i));
    }

    /* JADX DEBUG: Failed to insert an additional move for type inference into block B:14:0x0052 */
    public final List<PeopleTileViewModel> priorityTiles() {
        ArrayList emptyList;
        try {
            List<PeopleTileModel> priorityTiles = this.tileRepository.priorityTiles();
            ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(priorityTiles, 10));
            Iterator<T> it = priorityTiles.iterator();
            while (true) {
                emptyList = arrayList;
                if (!it.hasNext()) {
                    break;
                }
                arrayList.add(toViewModel((PeopleTileModel) it.next()));
            }
        } catch (Exception e) {
            Log.e("PeopleViewModel", "Couldn't retrieve priority conversations", e);
            emptyList = CollectionsKt__CollectionsKt.emptyList();
        }
        return emptyList;
    }

    /* JADX DEBUG: Failed to insert an additional move for type inference into block B:14:0x0052 */
    public final List<PeopleTileViewModel> recentTiles() {
        ArrayList emptyList;
        try {
            List<PeopleTileModel> recentTiles = this.tileRepository.recentTiles();
            ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(recentTiles, 10));
            Iterator<T> it = recentTiles.iterator();
            while (true) {
                emptyList = arrayList;
                if (!it.hasNext()) {
                    break;
                }
                arrayList.add(toViewModel((PeopleTileModel) it.next()));
            }
        } catch (Exception e) {
            Log.e("PeopleViewModel", "Couldn't retrieve recent conversations", e);
            emptyList = CollectionsKt__CollectionsKt.emptyList();
        }
        return emptyList;
    }

    public final PeopleTileViewModel toViewModel(PeopleTileModel peopleTileModel) {
        Context context = this.context;
        return new PeopleTileViewModel(peopleTileModel.getKey(), PeopleTileViewHelper.getPersonIconBitmap(context, peopleTileModel, PeopleTileViewHelper.getSizeInDp(context, R$dimen.avatar_size_for_medium, context.getResources().getDisplayMetrics().density)), peopleTileModel.getUsername());
    }
}