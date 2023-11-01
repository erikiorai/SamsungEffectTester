package com.android.systemui.people.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Outline;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.LinearLayout;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleOwnerKt;
import com.android.systemui.R$dimen;
import com.android.systemui.R$id;
import com.android.systemui.R$layout;
import com.android.systemui.people.PeopleSpaceTileView;
import com.android.systemui.people.ui.viewmodel.PeopleTileViewModel;
import com.android.systemui.people.ui.viewmodel.PeopleViewModel;
import java.util.List;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineStart;

/* loaded from: mainsysui33.jar:com/android/systemui/people/ui/view/PeopleViewBinder.class */
public final class PeopleViewBinder {
    public static final PeopleViewBinder INSTANCE = new PeopleViewBinder();
    public static final PeopleViewBinder$ViewOutlineProvider$1 ViewOutlineProvider = new ViewOutlineProvider() { // from class: com.android.systemui.people.ui.view.PeopleViewBinder$ViewOutlineProvider$1
        @Override // android.view.ViewOutlineProvider
        public void getOutline(View view, Outline outline) {
            outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(), view.getContext().getResources().getDimension(R$dimen.people_space_widget_radius));
        }
    };

    public static final void bind(ViewGroup viewGroup, PeopleViewModel peopleViewModel, LifecycleOwner lifecycleOwner, Function1<? super PeopleViewModel.Result, Unit> function1) {
        BuildersKt.launch$default(LifecycleOwnerKt.getLifecycleScope(lifecycleOwner), (CoroutineContext) null, (CoroutineStart) null, new PeopleViewBinder$bind$1(lifecycleOwner, peopleViewModel, function1, null), 3, (Object) null);
        BuildersKt.launch$default(LifecycleOwnerKt.getLifecycleScope(lifecycleOwner), (CoroutineContext) null, (CoroutineStart) null, new PeopleViewBinder$bind$2(lifecycleOwner, peopleViewModel, viewGroup, null), 3, (Object) null);
        BuildersKt.launch$default(LifecycleOwnerKt.getLifecycleScope(lifecycleOwner), (CoroutineContext) null, (CoroutineStart) null, new PeopleViewBinder$bind$3(lifecycleOwner, peopleViewModel, null), 3, (Object) null);
    }

    public static final ViewGroup create(Context context) {
        return (ViewGroup) LayoutInflater.from(context).inflate(R$layout.people_space_activity, (ViewGroup) null);
    }

    public final void bindTileView(PeopleSpaceTileView peopleSpaceTileView, final PeopleTileViewModel peopleTileViewModel, final Function1<? super PeopleTileViewModel, Unit> function1) {
        try {
            peopleSpaceTileView.setName(peopleTileViewModel.getUsername());
            peopleSpaceTileView.setPersonIcon(peopleTileViewModel.getIcon());
            peopleSpaceTileView.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.people.ui.view.PeopleViewBinder$bindTileView$1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    function1.invoke(peopleTileViewModel);
                }
            });
        } catch (Exception e) {
            Log.e("PeopleViewBinder", "Couldn't retrieve shortcut information", e);
        }
    }

    public final void setConversationsContent(ViewGroup viewGroup, List<PeopleTileViewModel> list, List<PeopleTileViewModel> list2, Function1<? super PeopleTileViewModel, Unit> function1) {
        if (viewGroup.getChildCount() > 1) {
            int childCount = viewGroup.getChildCount();
            throw new IllegalStateException(("view has " + childCount + " children, it should have maximum 1").toString());
        }
        int i = R$id.top_level_with_conversations;
        if (viewGroup.findViewById(i) == null) {
            if (viewGroup.getChildCount() == 1) {
                viewGroup.removeViewAt(0);
            }
            LayoutInflater.from(viewGroup.getContext()).inflate(R$layout.people_space_activity_with_conversations, viewGroup);
        }
        View requireViewById = viewGroup.requireViewById(i);
        setTileViews(requireViewById, R$id.priority, R$id.priority_tiles, list, function1);
        setTileViews(requireViewById, R$id.recent, R$id.recent_tiles, list2, function1);
    }

    public final void setNoConversationsContent(ViewGroup viewGroup, final Function0<Unit> function0) {
        if (viewGroup.getChildCount() > 1) {
            int childCount = viewGroup.getChildCount();
            throw new IllegalStateException(("view has " + childCount + " children, it should have maximum 1").toString());
        } else if (viewGroup.findViewById(R$id.top_level_no_conversations) != null) {
        } else {
            if (viewGroup.getChildCount() == 1) {
                viewGroup.removeViewAt(0);
            }
            Context context = viewGroup.getContext();
            View inflate = LayoutInflater.from(context).inflate(R$layout.people_space_activity_no_conversations, viewGroup);
            inflate.findViewById(R$id.got_it_button).setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.people.ui.view.PeopleViewBinder$setNoConversationsContent$1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    function0.invoke();
                }
            });
            GradientDrawable gradientDrawable = (GradientDrawable) ((LinearLayout) inflate.findViewById(16908288)).getBackground();
            TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(new int[]{17956909});
            gradientDrawable.setColor(obtainStyledAttributes.getColor(0, -1));
            obtainStyledAttributes.recycle();
        }
    }

    public final void setTileViews(View view, int i, int i2, List<PeopleTileViewModel> list, Function1<? super PeopleTileViewModel, Unit> function1) {
        ViewGroup viewGroup = (ViewGroup) view.requireViewById(i2);
        viewGroup.removeAllViews();
        viewGroup.setOutlineProvider(ViewOutlineProvider);
        LinearLayout linearLayout = (LinearLayout) view.requireViewById(i);
        if (list.isEmpty()) {
            linearLayout.setVisibility(8);
            return;
        }
        linearLayout.setVisibility(0);
        int i3 = 0;
        for (Object obj : list) {
            if (i3 < 0) {
                CollectionsKt__CollectionsKt.throwIndexOverflow();
            }
            PeopleTileViewModel peopleTileViewModel = (PeopleTileViewModel) obj;
            Context context = view.getContext();
            String shortcutId = peopleTileViewModel.getKey().getShortcutId();
            boolean z = true;
            if (i3 != list.size() - 1) {
                z = false;
            }
            INSTANCE.bindTileView(new PeopleSpaceTileView(context, viewGroup, shortcutId, z), peopleTileViewModel, function1);
            i3++;
        }
    }
}