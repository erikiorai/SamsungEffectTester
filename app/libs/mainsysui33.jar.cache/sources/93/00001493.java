package com.android.systemui.controls.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.ActivityOptions;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.LayerDrawable;
import android.service.controls.Control;
import android.util.IndentingPrintWriter;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListPopupWindow;
import android.widget.Space;
import android.widget.TextView;
import com.android.settingslib.widget.ActionBarShadowController;
import com.android.systemui.Dumpable;
import com.android.systemui.R$color;
import com.android.systemui.R$dimen;
import com.android.systemui.R$drawable;
import com.android.systemui.R$id;
import com.android.systemui.R$layout;
import com.android.systemui.R$string;
import com.android.systemui.R$style;
import com.android.systemui.controls.ControlsMetricsLogger;
import com.android.systemui.controls.ControlsServiceInfo;
import com.android.systemui.controls.CustomIconCache;
import com.android.systemui.controls.controller.ControlInfo;
import com.android.systemui.controls.controller.ControlsController;
import com.android.systemui.controls.controller.StructureInfo;
import com.android.systemui.controls.management.ControlAdapter;
import com.android.systemui.controls.management.ControlsEditingActivity;
import com.android.systemui.controls.management.ControlsFavoritingActivity;
import com.android.systemui.controls.management.ControlsListingController;
import com.android.systemui.controls.management.ControlsProviderSelectorActivity;
import com.android.systemui.controls.settings.ControlsSettingsRepository;
import com.android.systemui.controls.ui.SelectedItem;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.globalactions.GlobalActionsPopupMenu;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.settings.UserFileManager;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.util.DumpUtilsKt;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.wm.shell.TaskView;
import com.android.wm.shell.TaskViewFactory;
import dagger.Lazy;
import java.io.PrintWriter;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt__MutableCollectionsJVMKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.MapsKt__MapsJVMKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.CallableReference;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.ranges.RangesKt___RangesKt;

/* loaded from: mainsysui33.jar:com/android/systemui/controls/ui/ControlsUiControllerImpl.class */
public final class ControlsUiControllerImpl implements ControlsUiController, Dumpable {
    public static final Companion Companion = new Companion(null);
    public Context activityContext;
    public final ActivityStarter activityStarter;
    public List<StructureInfo> allStructures;
    public final DelayableExecutor bgExecutor;
    public final Collator collator;
    public final Context context;
    public final ControlActionCoordinator controlActionCoordinator;
    public final Lazy<ControlsController> controlsController;
    public final Lazy<ControlsListingController> controlsListingController;
    public final ControlsMetricsLogger controlsMetricsLogger;
    public final ControlsSettingsRepository controlsSettingsRepository;
    public final CustomIconCache iconCache;
    public final KeyguardStateController keyguardStateController;
    public ControlsListingController.ControlsListingCallback listingCallback;
    public final Comparator<SelectionItem> localeComparator;
    public Runnable onDismiss;
    public final Consumer<Boolean> onSeedingComplete;
    public ViewGroup parent;
    public ListPopupWindow popup;
    public final ContextThemeWrapper popupThemedContext;
    public boolean retainCache;
    public PanelTaskViewController taskViewController;
    public final Optional<TaskViewFactory> taskViewFactory;
    public final DelayableExecutor uiExecutor;
    public final UserFileManager userFileManager;
    public final UserTracker userTracker;
    public SelectedItem selectedItem = SelectedItem.Companion.getEMPTY_SELECTION();
    public final Map<ControlKey, ControlWithState> controlsById = new LinkedHashMap();
    public final Map<ControlKey, ControlViewHolder> controlViewsById = new LinkedHashMap();
    public boolean hidden = true;
    public List<SelectionItem> lastSelections = CollectionsKt__CollectionsKt.emptyList();

    /* loaded from: mainsysui33.jar:com/android/systemui/controls/ui/ControlsUiControllerImpl$Companion.class */
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public ControlsUiControllerImpl(Lazy<ControlsController> lazy, Context context, DelayableExecutor delayableExecutor, DelayableExecutor delayableExecutor2, Lazy<ControlsListingController> lazy2, ControlActionCoordinator controlActionCoordinator, ActivityStarter activityStarter, CustomIconCache customIconCache, ControlsMetricsLogger controlsMetricsLogger, KeyguardStateController keyguardStateController, UserFileManager userFileManager, UserTracker userTracker, Optional<TaskViewFactory> optional, ControlsSettingsRepository controlsSettingsRepository, DumpManager dumpManager) {
        this.controlsController = lazy;
        this.context = context;
        this.uiExecutor = delayableExecutor;
        this.bgExecutor = delayableExecutor2;
        this.controlsListingController = lazy2;
        this.controlActionCoordinator = controlActionCoordinator;
        this.activityStarter = activityStarter;
        this.iconCache = customIconCache;
        this.controlsMetricsLogger = controlsMetricsLogger;
        this.keyguardStateController = keyguardStateController;
        this.userFileManager = userFileManager;
        this.userTracker = userTracker;
        this.taskViewFactory = optional;
        this.controlsSettingsRepository = controlsSettingsRepository;
        this.popupThemedContext = new ContextThemeWrapper(context, R$style.Control_ListPopupWindow);
        final Collator collator = Collator.getInstance(context.getResources().getConfiguration().getLocales().get(0));
        this.collator = collator;
        this.localeComparator = new Comparator() { // from class: com.android.systemui.controls.ui.ControlsUiControllerImpl$special$$inlined$compareBy$1
            @Override // java.util.Comparator
            public final int compare(T t, T t2) {
                return collator.compare(((SelectionItem) t).getTitle(), ((SelectionItem) t2).getTitle());
            }
        };
        this.onSeedingComplete = new Consumer() { // from class: com.android.systemui.controls.ui.ControlsUiControllerImpl$onSeedingComplete$1
            /* JADX DEBUG: Method merged with bridge method */
            @Override // java.util.function.Consumer
            public final void accept(Boolean bool) {
                T next;
                if (bool.booleanValue()) {
                    ControlsUiControllerImpl controlsUiControllerImpl = ControlsUiControllerImpl.this;
                    Iterator<T> it = ((ControlsController) controlsUiControllerImpl.getControlsController().get()).getFavorites().iterator();
                    if (it.hasNext()) {
                        next = it.next();
                        if (it.hasNext()) {
                            int size = ((StructureInfo) next).getControls().size();
                            T t = next;
                            do {
                                T next2 = it.next();
                                int size2 = ((StructureInfo) next2).getControls().size();
                                next = t;
                                int i = size;
                                if (size < size2) {
                                    next = next2;
                                    i = size2;
                                }
                                t = next;
                                size = i;
                            } while (it.hasNext());
                        }
                    } else {
                        next = null;
                    }
                    StructureInfo structureInfo = (StructureInfo) next;
                    ControlsUiControllerImpl.access$setSelectedItem$p(controlsUiControllerImpl, structureInfo != null ? new SelectedItem.StructureItem(structureInfo) : SelectedItem.Companion.getEMPTY_SELECTION());
                    ControlsUiControllerImpl controlsUiControllerImpl2 = ControlsUiControllerImpl.this;
                    ControlsUiControllerImpl.access$updatePreferences(controlsUiControllerImpl2, ControlsUiControllerImpl.access$getSelectedItem$p(controlsUiControllerImpl2));
                }
                ControlsUiControllerImpl controlsUiControllerImpl3 = ControlsUiControllerImpl.this;
                ViewGroup access$getParent$p = ControlsUiControllerImpl.access$getParent$p(controlsUiControllerImpl3);
                if (access$getParent$p == null) {
                    access$getParent$p = null;
                }
                ControlsUiControllerImpl.access$reload(controlsUiControllerImpl3, access$getParent$p);
            }
        };
        DumpManager.registerDumpable$default(dumpManager, ControlsUiControllerImpl.class.getName(), this, null, 4, null);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.controls.ui.ControlsUiControllerImpl$createPanelView$1.run():void, com.android.systemui.controls.ui.ControlsUiControllerImpl$createPanelView$1.1.accept(com.android.wm.shell.TaskView):void, com.android.systemui.controls.ui.ControlsUiControllerImpl$reload$1.onAnimationEnd(android.animation.Animator):void] */
    public static final /* synthetic */ Context access$getActivityContext$p(ControlsUiControllerImpl controlsUiControllerImpl) {
        return controlsUiControllerImpl.activityContext;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.controls.ui.ControlsUiControllerImpl$onActionResponse$1.run():void, com.android.systemui.controls.ui.ControlsUiControllerImpl$reload$1.onAnimationEnd(android.animation.Animator):void] */
    public static final /* synthetic */ Map access$getControlViewsById$p(ControlsUiControllerImpl controlsUiControllerImpl) {
        return controlsUiControllerImpl.controlViewsById;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.controls.ui.ControlsUiControllerImpl$reload$1.onAnimationEnd(android.animation.Animator):void] */
    public static final /* synthetic */ Map access$getControlsById$p(ControlsUiControllerImpl controlsUiControllerImpl) {
        return controlsUiControllerImpl.controlsById;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.controls.ui.ControlsUiControllerImpl$createControlsSpaceFrame$1$1.onClick(android.view.View):void, com.android.systemui.controls.ui.ControlsUiControllerImpl$createPanelView$1.1.accept(com.android.wm.shell.TaskView):void, com.android.systemui.controls.ui.ControlsUiControllerImpl$reload$1.onAnimationEnd(android.animation.Animator):void] */
    public static final /* synthetic */ Runnable access$getOnDismiss$p(ControlsUiControllerImpl controlsUiControllerImpl) {
        return controlsUiControllerImpl.onDismiss;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.controls.ui.ControlsUiControllerImpl$createCallback$1$onServicesUpdated$1.run():void, com.android.systemui.controls.ui.ControlsUiControllerImpl$onSeedingComplete$1.accept(java.lang.Boolean):void] */
    public static final /* synthetic */ ViewGroup access$getParent$p(ControlsUiControllerImpl controlsUiControllerImpl) {
        return controlsUiControllerImpl.parent;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.controls.ui.ControlsUiControllerImpl$createDropDown$2.onClick(android.view.View):void, com.android.systemui.controls.ui.ControlsUiControllerImpl$createMenu$1.onClick(android.view.View):void] */
    public static final /* synthetic */ ContextThemeWrapper access$getPopupThemedContext$p(ControlsUiControllerImpl controlsUiControllerImpl) {
        return controlsUiControllerImpl.popupThemedContext;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.controls.ui.ControlsUiControllerImpl$onSeedingComplete$1.accept(java.lang.Boolean):void] */
    public static final /* synthetic */ SelectedItem access$getSelectedItem$p(ControlsUiControllerImpl controlsUiControllerImpl) {
        return controlsUiControllerImpl.selectedItem;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.controls.ui.ControlsUiControllerImpl$createPanelView$1.run():void] */
    public static final /* synthetic */ Optional access$getTaskViewFactory$p(ControlsUiControllerImpl controlsUiControllerImpl) {
        return controlsUiControllerImpl.taskViewFactory;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.controls.ui.ControlsUiControllerImpl$onSeedingComplete$1.accept(java.lang.Boolean):void] */
    public static final /* synthetic */ void access$reload(ControlsUiControllerImpl controlsUiControllerImpl, ViewGroup viewGroup) {
        controlsUiControllerImpl.reload(viewGroup);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.controls.ui.ControlsUiControllerImpl$createDropDown$2.onClick(android.view.View):void, com.android.systemui.controls.ui.ControlsUiControllerImpl$createMenu$1.onClick(android.view.View):void] */
    public static final /* synthetic */ void access$setPopup$p(ControlsUiControllerImpl controlsUiControllerImpl, ListPopupWindow listPopupWindow) {
        controlsUiControllerImpl.popup = listPopupWindow;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.controls.ui.ControlsUiControllerImpl$onSeedingComplete$1.accept(java.lang.Boolean):void] */
    public static final /* synthetic */ void access$setSelectedItem$p(ControlsUiControllerImpl controlsUiControllerImpl, SelectedItem selectedItem) {
        controlsUiControllerImpl.selectedItem = selectedItem;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.controls.ui.ControlsUiControllerImpl$createPanelView$1.1.accept(com.android.wm.shell.TaskView):void] */
    public static final /* synthetic */ void access$setTaskViewController$p(ControlsUiControllerImpl controlsUiControllerImpl, PanelTaskViewController panelTaskViewController) {
        controlsUiControllerImpl.taskViewController = panelTaskViewController;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.controls.ui.ControlsUiControllerImpl$createMenu$1$onClick$1$1.onItemClick(android.widget.AdapterView<?>, android.view.View, int, long):void] */
    public static final /* synthetic */ void access$startEditingActivity(ControlsUiControllerImpl controlsUiControllerImpl, StructureInfo structureInfo) {
        controlsUiControllerImpl.startEditingActivity(structureInfo);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.controls.ui.ControlsUiControllerImpl$createMenu$1$onClick$1$1.onItemClick(android.widget.AdapterView<?>, android.view.View, int, long):void] */
    public static final /* synthetic */ void access$startFavoritingActivity(ControlsUiControllerImpl controlsUiControllerImpl, StructureInfo structureInfo) {
        controlsUiControllerImpl.startFavoritingActivity(structureInfo);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.controls.ui.ControlsUiControllerImpl$createMenu$1$onClick$1$1.onItemClick(android.widget.AdapterView<?>, android.view.View, int, long):void] */
    public static final /* synthetic */ void access$startProviderSelectorActivity(ControlsUiControllerImpl controlsUiControllerImpl) {
        controlsUiControllerImpl.startProviderSelectorActivity();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.controls.ui.ControlsUiControllerImpl$createDropDown$2$onClick$1$1.onItemClick(android.widget.AdapterView<?>, android.view.View, int, long):void] */
    public static final /* synthetic */ void access$switchAppOrStructure(ControlsUiControllerImpl controlsUiControllerImpl, SelectionItem selectionItem) {
        controlsUiControllerImpl.switchAppOrStructure(selectionItem);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.controls.ui.ControlsUiControllerImpl$onSeedingComplete$1.accept(java.lang.Boolean):void] */
    public static final /* synthetic */ void access$updatePreferences(ControlsUiControllerImpl controlsUiControllerImpl, SelectedItem selectedItem) {
        controlsUiControllerImpl.updatePreferences(selectedItem);
    }

    public void closeDialogs(boolean z) {
        if (z) {
            ListPopupWindow listPopupWindow = this.popup;
            if (listPopupWindow != null) {
                listPopupWindow.dismissImmediate();
            }
        } else {
            ListPopupWindow listPopupWindow2 = this.popup;
            if (listPopupWindow2 != null) {
                listPopupWindow2.dismiss();
            }
        }
        this.popup = null;
        for (Map.Entry<ControlKey, ControlViewHolder> entry : this.controlViewsById.entrySet()) {
            entry.getValue().dismiss();
        }
        this.controlActionCoordinator.closeDialogs();
    }

    public final ControlsListingController.ControlsListingCallback createCallback(final Function1<? super List<SelectionItem>, Unit> function1) {
        return new ControlsListingController.ControlsListingCallback() { // from class: com.android.systemui.controls.ui.ControlsUiControllerImpl$createCallback$1
            @Override // com.android.systemui.controls.management.ControlsListingController.ControlsListingCallback
            public void onServicesUpdated(List<ControlsServiceInfo> list) {
                List<ControlsServiceInfo> list2 = list;
                final ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list2, 10));
                for (ControlsServiceInfo controlsServiceInfo : list2) {
                    arrayList.add(new SelectionItem(controlsServiceInfo.loadLabel(), "", controlsServiceInfo.loadIcon(), controlsServiceInfo.componentName, controlsServiceInfo.getServiceInfo().applicationInfo.uid, controlsServiceInfo.getPanelActivity()));
                }
                DelayableExecutor uiExecutor = ControlsUiControllerImpl.this.getUiExecutor();
                final ControlsUiControllerImpl controlsUiControllerImpl = ControlsUiControllerImpl.this;
                final Function1<List<SelectionItem>, Unit> function12 = function1;
                uiExecutor.execute(new Runnable() { // from class: com.android.systemui.controls.ui.ControlsUiControllerImpl$createCallback$1$onServicesUpdated$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        ViewGroup access$getParent$p = ControlsUiControllerImpl.access$getParent$p(ControlsUiControllerImpl.this);
                        ViewGroup viewGroup = access$getParent$p;
                        if (access$getParent$p == null) {
                            viewGroup = null;
                        }
                        viewGroup.removeAllViews();
                        if (arrayList.size() > 0) {
                            function12.invoke(arrayList);
                        }
                    }
                });
            }
        };
    }

    public final void createControlsSpaceFrame() {
        Context context = this.activityContext;
        Context context2 = context;
        if (context == null) {
            context2 = null;
        }
        LayoutInflater from = LayoutInflater.from(context2);
        int i = R$layout.controls_with_favorites;
        ViewGroup viewGroup = this.parent;
        ViewGroup viewGroup2 = viewGroup;
        if (viewGroup == null) {
            viewGroup2 = null;
        }
        from.inflate(i, viewGroup2, true);
        ViewGroup viewGroup3 = this.parent;
        if (viewGroup3 == null) {
            viewGroup3 = null;
        }
        ImageView imageView = (ImageView) viewGroup3.requireViewById(R$id.controls_close);
        imageView.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.controls.ui.ControlsUiControllerImpl$createControlsSpaceFrame$1$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                Runnable access$getOnDismiss$p = ControlsUiControllerImpl.access$getOnDismiss$p(ControlsUiControllerImpl.this);
                Runnable runnable = access$getOnDismiss$p;
                if (access$getOnDismiss$p == null) {
                    runnable = null;
                }
                runnable.run();
            }
        });
        imageView.setVisibility(0);
    }

    public final void createDropDown(List<SelectionItem> list, SelectionItem selectionItem) {
        for (SelectionItem selectionItem2 : list) {
            RenderInfo.Companion.registerComponentIcon(selectionItem2.getComponentName(), selectionItem2.getIcon());
        }
        final Ref.ObjectRef objectRef = new Ref.ObjectRef();
        ItemAdapter itemAdapter = new ItemAdapter(this.context, R$layout.controls_spinner_item);
        itemAdapter.addAll(list);
        objectRef.element = itemAdapter;
        int dimensionPixelSize = this.context.getResources().getDimensionPixelSize(R$dimen.controls_header_app_icon_size);
        ViewGroup viewGroup = this.parent;
        ViewGroup viewGroup2 = viewGroup;
        if (viewGroup == null) {
            viewGroup2 = null;
        }
        TextView textView = (TextView) viewGroup2.requireViewById(R$id.app_or_structure_spinner);
        textView.setText(selectionItem.getTitle());
        ((LayerDrawable) textView.getBackground()).getDrawable(0).setTint(textView.getContext().getResources().getColor(R$color.control_spinner_dropdown, null));
        selectionItem.getIcon().setBounds(0, 0, dimensionPixelSize, dimensionPixelSize);
        textView.setCompoundDrawablePadding((int) (dimensionPixelSize / 2.4f));
        textView.setCompoundDrawablesRelative(selectionItem.getIcon(), null, null, null);
        ViewGroup viewGroup3 = this.parent;
        ViewGroup viewGroup4 = viewGroup3;
        if (viewGroup3 == null) {
            viewGroup4 = null;
        }
        final ViewGroup viewGroup5 = (ViewGroup) viewGroup4.requireViewById(R$id.controls_header);
        if (list.size() == 1) {
            textView.setBackground(null);
            viewGroup5.setOnClickListener(null);
            return;
        }
        ViewGroup viewGroup6 = this.parent;
        if (viewGroup6 == null) {
            viewGroup6 = null;
        }
        textView.setBackground(viewGroup6.getContext().getResources().getDrawable(R$drawable.control_spinner_background));
        viewGroup5.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.controls.ui.ControlsUiControllerImpl$createDropDown$2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ControlsUiControllerImpl controlsUiControllerImpl = ControlsUiControllerImpl.this;
                final GlobalActionsPopupMenu globalActionsPopupMenu = new GlobalActionsPopupMenu(ControlsUiControllerImpl.access$getPopupThemedContext$p(ControlsUiControllerImpl.this), true);
                ViewGroup viewGroup7 = viewGroup5;
                Ref.ObjectRef<ItemAdapter> objectRef2 = objectRef;
                final ControlsUiControllerImpl controlsUiControllerImpl2 = ControlsUiControllerImpl.this;
                globalActionsPopupMenu.setAnchorView(viewGroup7);
                globalActionsPopupMenu.setAdapter((ListAdapter) objectRef2.element);
                globalActionsPopupMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.android.systemui.controls.ui.ControlsUiControllerImpl$createDropDown$2$onClick$1$1
                    @Override // android.widget.AdapterView.OnItemClickListener
                    public void onItemClick(AdapterView<?> adapterView, View view2, int i, long j) {
                        ControlsUiControllerImpl.access$switchAppOrStructure(ControlsUiControllerImpl.this, (SelectionItem) adapterView.getItemAtPosition(i));
                        globalActionsPopupMenu.dismiss();
                    }
                });
                globalActionsPopupMenu.show();
                ControlsUiControllerImpl.access$setPopup$p(controlsUiControllerImpl, globalActionsPopupMenu);
            }
        });
    }

    public final void createListView(SelectionItem selectionItem) {
        SelectedItem selectedItem = this.selectedItem;
        if (selectedItem instanceof SelectedItem.StructureItem) {
            StructureInfo structure = ((SelectedItem.StructureItem) selectedItem).getStructure();
            Context context = this.activityContext;
            Context context2 = context;
            if (context == null) {
                context2 = null;
            }
            LayoutInflater from = LayoutInflater.from(context2);
            ControlAdapter.Companion companion = ControlAdapter.Companion;
            Context context3 = this.activityContext;
            Context context4 = context3;
            if (context3 == null) {
                context4 = null;
            }
            int findMaxColumns = companion.findMaxColumns(context4.getResources());
            ViewGroup viewGroup = this.parent;
            if (viewGroup == null) {
                viewGroup = null;
            }
            ViewGroup viewGroup2 = (ViewGroup) viewGroup.requireViewById(R$id.global_actions_controls_list);
            ViewGroup createRow = createRow(from, viewGroup2);
            for (ControlInfo controlInfo : structure.getControls()) {
                ControlKey controlKey = new ControlKey(structure.getComponentName(), controlInfo.getControlId());
                ControlWithState controlWithState = this.controlsById.get(controlKey);
                if (controlWithState != null) {
                    ViewGroup viewGroup3 = createRow;
                    if (createRow.getChildCount() == findMaxColumns) {
                        viewGroup3 = createRow(from, viewGroup2);
                    }
                    ViewGroup viewGroup4 = (ViewGroup) from.inflate(R$layout.controls_base_item, viewGroup3, false);
                    viewGroup3.addView(viewGroup4);
                    if (viewGroup3.getChildCount() == 1) {
                        ((ViewGroup.MarginLayoutParams) viewGroup4.getLayoutParams()).setMarginStart(0);
                    }
                    ControlViewHolder controlViewHolder = new ControlViewHolder(viewGroup4, (ControlsController) this.controlsController.get(), this.uiExecutor, this.bgExecutor, this.controlActionCoordinator, this.controlsMetricsLogger, selectionItem.getUid());
                    controlViewHolder.bindData(controlWithState, false);
                    this.controlViewsById.put(controlKey, controlViewHolder);
                    createRow = viewGroup3;
                }
            }
            int size = structure.getControls().size() % findMaxColumns;
            int dimensionPixelSize = this.context.getResources().getDimensionPixelSize(R$dimen.control_spacing);
            for (int i = size == 0 ? 0 : findMaxColumns - size; i > 0; i--) {
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, 0, 1.0f);
                layoutParams.setMarginStart(dimensionPixelSize);
                createRow.addView(new Space(this.context), layoutParams);
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:29:0x002f, code lost:
        if (r0 == null) goto L18;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void createMenu() {
        StructureInfo empty_structure;
        SelectedItem selectedItem = this.selectedItem;
        final boolean z = selectedItem instanceof SelectedItem.PanelItem;
        SelectedItem.StructureItem structureItem = selectedItem instanceof SelectedItem.StructureItem ? (SelectedItem.StructureItem) selectedItem : null;
        if (structureItem != null) {
            StructureInfo structure = structureItem.getStructure();
            empty_structure = structure;
        }
        empty_structure = StructureInfo.Companion.getEMPTY_STRUCTURE();
        String[] strArr = z ? new String[]{this.context.getResources().getString(R$string.controls_menu_add)} : new String[]{this.context.getResources().getString(R$string.controls_menu_add), this.context.getResources().getString(R$string.controls_menu_edit)};
        final Ref.ObjectRef objectRef = new Ref.ObjectRef();
        objectRef.element = new ArrayAdapter(this.context, R$layout.controls_more_item, strArr);
        ViewGroup viewGroup = this.parent;
        if (viewGroup == null) {
            viewGroup = null;
        }
        final ImageView imageView = (ImageView) viewGroup.requireViewById(R$id.controls_more);
        final StructureInfo structureInfo = empty_structure;
        imageView.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.controls.ui.ControlsUiControllerImpl$createMenu$1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ControlsUiControllerImpl controlsUiControllerImpl = ControlsUiControllerImpl.this;
                final GlobalActionsPopupMenu globalActionsPopupMenu = new GlobalActionsPopupMenu(ControlsUiControllerImpl.access$getPopupThemedContext$p(ControlsUiControllerImpl.this), false);
                ImageView imageView2 = imageView;
                Ref.ObjectRef<ArrayAdapter<String>> objectRef2 = objectRef;
                final boolean z2 = z;
                final ControlsUiControllerImpl controlsUiControllerImpl2 = ControlsUiControllerImpl.this;
                final StructureInfo structureInfo2 = structureInfo;
                globalActionsPopupMenu.setAnchorView(imageView2);
                globalActionsPopupMenu.setAdapter((ListAdapter) objectRef2.element);
                globalActionsPopupMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.android.systemui.controls.ui.ControlsUiControllerImpl$createMenu$1$onClick$1$1
                    @Override // android.widget.AdapterView.OnItemClickListener
                    public void onItemClick(AdapterView<?> adapterView, View view2, int i, long j) {
                        if (i != 0) {
                            if (i == 1) {
                                ControlsUiControllerImpl.access$startEditingActivity(controlsUiControllerImpl2, structureInfo2);
                            }
                        } else if (z2) {
                            ControlsUiControllerImpl.access$startProviderSelectorActivity(controlsUiControllerImpl2);
                        } else {
                            ControlsUiControllerImpl.access$startFavoritingActivity(controlsUiControllerImpl2, structureInfo2);
                        }
                        globalActionsPopupMenu.dismiss();
                    }
                });
                globalActionsPopupMenu.show();
                ControlsUiControllerImpl.access$setPopup$p(controlsUiControllerImpl, globalActionsPopupMenu);
            }
        });
    }

    public final void createPanelView(ComponentName componentName) {
        final PendingIntent activityAsUser = PendingIntent.getActivityAsUser(this.context, 0, new Intent().setComponent(componentName).putExtra("android.service.controls.extra.LOCKSCREEN_ALLOW_TRIVIAL_CONTROLS", ((Boolean) this.controlsSettingsRepository.getAllowActionOnTrivialControlsInLockscreen().getValue()).booleanValue()), 201326592, null, this.userTracker.getUserHandle());
        ViewGroup viewGroup = this.parent;
        ViewGroup viewGroup2 = viewGroup;
        if (viewGroup == null) {
            viewGroup2 = null;
        }
        viewGroup2.requireViewById(R$id.controls_scroll_view).setVisibility(8);
        ViewGroup viewGroup3 = this.parent;
        if (viewGroup3 == null) {
            viewGroup3 = null;
        }
        final FrameLayout frameLayout = (FrameLayout) viewGroup3.requireViewById(R$id.controls_panel);
        frameLayout.setVisibility(0);
        frameLayout.post(new Runnable() { // from class: com.android.systemui.controls.ui.ControlsUiControllerImpl$createPanelView$1
            @Override // java.lang.Runnable
            public final void run() {
                TaskViewFactory taskViewFactory = (TaskViewFactory) ControlsUiControllerImpl.access$getTaskViewFactory$p(ControlsUiControllerImpl.this).get();
                Context access$getActivityContext$p = ControlsUiControllerImpl.access$getActivityContext$p(ControlsUiControllerImpl.this);
                Context context = access$getActivityContext$p;
                if (access$getActivityContext$p == null) {
                    context = null;
                }
                final ControlsUiControllerImpl controlsUiControllerImpl = ControlsUiControllerImpl.this;
                final PendingIntent pendingIntent = activityAsUser;
                final FrameLayout frameLayout2 = frameLayout;
                taskViewFactory.create(context, ControlsUiControllerImpl.this.getUiExecutor(), new Consumer() { // from class: com.android.systemui.controls.ui.ControlsUiControllerImpl$createPanelView$1.1

                    /* renamed from: com.android.systemui.controls.ui.ControlsUiControllerImpl$createPanelView$1$1$1  reason: invalid class name and collision with other inner class name */
                    /* loaded from: mainsysui33.jar:com/android/systemui/controls/ui/ControlsUiControllerImpl$createPanelView$1$1$1.class */
                    public final /* synthetic */ class C00061 extends FunctionReferenceImpl implements Function0<Unit> {
                        public C00061(Object obj) {
                            super(0, obj, Runnable.class, "run", "run()V", 0);
                        }

                        public /* bridge */ /* synthetic */ Object invoke() {
                            m1874invoke();
                            return Unit.INSTANCE;
                        }

                        /* renamed from: invoke  reason: collision with other method in class */
                        public final void m1874invoke() {
                            ((Runnable) ((CallableReference) this).receiver).run();
                        }
                    }

                    /* JADX DEBUG: Method merged with bridge method */
                    @Override // java.util.function.Consumer
                    public final void accept(TaskView taskView) {
                        ControlsUiControllerImpl controlsUiControllerImpl2 = ControlsUiControllerImpl.this;
                        Context access$getActivityContext$p2 = ControlsUiControllerImpl.access$getActivityContext$p(controlsUiControllerImpl2);
                        Runnable runnable = null;
                        if (access$getActivityContext$p2 == null) {
                            access$getActivityContext$p2 = null;
                        }
                        Executor uiExecutor = ControlsUiControllerImpl.this.getUiExecutor();
                        PendingIntent pendingIntent2 = pendingIntent;
                        Runnable access$getOnDismiss$p = ControlsUiControllerImpl.access$getOnDismiss$p(ControlsUiControllerImpl.this);
                        if (access$getOnDismiss$p != null) {
                            runnable = access$getOnDismiss$p;
                        }
                        PanelTaskViewController panelTaskViewController = new PanelTaskViewController(access$getActivityContext$p2, uiExecutor, pendingIntent2, taskView, new C00061(runnable));
                        frameLayout2.addView(taskView);
                        panelTaskViewController.launchTaskView();
                        ControlsUiControllerImpl.access$setTaskViewController$p(controlsUiControllerImpl2, panelTaskViewController);
                    }
                });
            }
        });
    }

    public final ViewGroup createRow(LayoutInflater layoutInflater, ViewGroup viewGroup) {
        ViewGroup viewGroup2 = (ViewGroup) layoutInflater.inflate(R$layout.controls_row, viewGroup, false);
        viewGroup.addView(viewGroup2);
        return viewGroup2;
    }

    @Override // com.android.systemui.Dumpable
    public void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println("ControlsUiControllerImpl:");
        IndentingPrintWriter asIndenting = DumpUtilsKt.asIndenting(printWriter);
        if (asIndenting instanceof IndentingPrintWriter) {
            asIndenting.increaseIndent();
        }
        boolean z = this.hidden;
        asIndenting.println("hidden: " + z);
        SelectedItem selectedItem = this.selectedItem;
        asIndenting.println("selectedItem: " + selectedItem);
        List<SelectionItem> list = this.lastSelections;
        asIndenting.println("lastSelections: " + list);
        Object value = this.controlsSettingsRepository.getAllowActionOnTrivialControlsInLockscreen().getValue();
        asIndenting.println("setting: " + value);
        asIndenting.decreaseIndent();
    }

    public final SelectionItem findSelectionItem(SelectedItem selectedItem, List<SelectionItem> list) {
        Object obj;
        Iterator<T> it = list.iterator();
        while (true) {
            if (!it.hasNext()) {
                obj = null;
                break;
            }
            Object next = it.next();
            if (((SelectionItem) next).matches(selectedItem)) {
                obj = next;
                break;
            }
        }
        return (SelectionItem) obj;
    }

    public final Lazy<ControlsController> getControlsController() {
        return this.controlsController;
    }

    @Override // com.android.systemui.controls.ui.ControlsUiController
    public SelectedItem getPreferredSelectedItem(List<StructureInfo> list) {
        Object obj;
        SelectedItem structureItem;
        StructureInfo structureInfo;
        SharedPreferences sharedPreferences = getSharedPreferences();
        String string = sharedPreferences.getString("controls_component", null);
        ComponentName unflattenFromString = string != null ? ComponentName.unflattenFromString(string) : null;
        ComponentName componentName = unflattenFromString;
        if (unflattenFromString == null) {
            componentName = StructureInfo.Companion.getEMPTY_COMPONENT();
        }
        String string2 = sharedPreferences.getString("controls_structure", "");
        Intrinsics.checkNotNull(string2);
        if (sharedPreferences.getBoolean("controls_is_panel", false)) {
            structureItem = new SelectedItem.PanelItem(string2, componentName);
        } else if (list.isEmpty()) {
            return SelectedItem.Companion.getEMPTY_SELECTION();
        } else {
            Iterator<T> it = list.iterator();
            do {
                obj = null;
                if (!it.hasNext()) {
                    break;
                }
                obj = it.next();
                structureInfo = (StructureInfo) obj;
            } while (!(Intrinsics.areEqual(componentName, structureInfo.getComponentName()) && Intrinsics.areEqual(string2, structureInfo.getStructure())));
            StructureInfo structureInfo2 = (StructureInfo) obj;
            StructureInfo structureInfo3 = structureInfo2;
            if (structureInfo2 == null) {
                structureInfo3 = list.get(0);
            }
            structureItem = new SelectedItem.StructureItem(structureInfo3);
        }
        return structureItem;
    }

    public final SharedPreferences getSharedPreferences() {
        return this.userFileManager.getSharedPreferences("controls_prefs", 0, this.userTracker.getUserId());
    }

    public final DelayableExecutor getUiExecutor() {
        return this.uiExecutor;
    }

    @Override // com.android.systemui.controls.ui.ControlsUiController
    public void hide() {
        this.hidden = true;
        closeDialogs(true);
        ((ControlsController) this.controlsController.get()).unsubscribe();
        PanelTaskViewController panelTaskViewController = this.taskViewController;
        if (panelTaskViewController != null) {
            panelTaskViewController.dismiss();
        }
        this.taskViewController = null;
        ViewGroup viewGroup = this.parent;
        ViewGroup viewGroup2 = viewGroup;
        if (viewGroup == null) {
            viewGroup2 = null;
        }
        viewGroup2.removeAllViews();
        this.controlsById.clear();
        this.controlViewsById.clear();
        ControlsListingController controlsListingController = (ControlsListingController) this.controlsListingController.get();
        ControlsListingController.ControlsListingCallback controlsListingCallback = this.listingCallback;
        if (controlsListingCallback == null) {
            controlsListingCallback = null;
        }
        controlsListingController.removeCallback(controlsListingCallback);
        if (this.retainCache) {
            return;
        }
        RenderInfo.Companion.clearCache();
    }

    public final void initialView(List<SelectionItem> list) {
        boolean z;
        List<SelectionItem> list2 = list;
        if (!(list2 instanceof Collection) || !list2.isEmpty()) {
            Iterator<T> it = list2.iterator();
            while (true) {
                z = false;
                if (!it.hasNext()) {
                    break;
                } else if (((SelectionItem) it.next()).isPanel()) {
                    z = true;
                    break;
                }
            }
        } else {
            z = false;
        }
        if (z) {
            showControlsView(list);
        } else {
            showInitialSetupView(list);
        }
    }

    public final boolean maybeUpdateSelectedItem(SelectionItem selectionItem) {
        Object obj;
        SelectedItem.PanelItem structureItem;
        StructureInfo structureInfo;
        boolean z = true;
        if (selectionItem.isPanel()) {
            structureItem = new SelectedItem.PanelItem(selectionItem.getAppName(), selectionItem.getComponentName());
        } else {
            List<StructureInfo> list = this.allStructures;
            List<StructureInfo> list2 = list;
            if (list == null) {
                list2 = null;
            }
            Iterator<T> it = list2.iterator();
            do {
                obj = null;
                if (!it.hasNext()) {
                    break;
                }
                obj = it.next();
                structureInfo = (StructureInfo) obj;
            } while (!(Intrinsics.areEqual(structureInfo.getStructure(), selectionItem.getStructure()) && Intrinsics.areEqual(structureInfo.getComponentName(), selectionItem.getComponentName())));
            StructureInfo structureInfo2 = (StructureInfo) obj;
            StructureInfo structureInfo3 = structureInfo2;
            if (structureInfo2 == null) {
                structureInfo3 = StructureInfo.Companion.getEMPTY_STRUCTURE();
            }
            structureItem = new SelectedItem.StructureItem(structureInfo3);
        }
        if (Intrinsics.areEqual(structureItem, this.selectedItem)) {
            z = false;
        } else {
            this.selectedItem = structureItem;
            updatePreferences(structureItem);
        }
        return z;
    }

    @Override // com.android.systemui.controls.ui.ControlsUiController
    public void onActionResponse(ComponentName componentName, String str, final int i) {
        final ControlKey controlKey = new ControlKey(componentName, str);
        this.uiExecutor.execute(new Runnable() { // from class: com.android.systemui.controls.ui.ControlsUiControllerImpl$onActionResponse$1
            @Override // java.lang.Runnable
            public final void run() {
                ControlViewHolder controlViewHolder = (ControlViewHolder) ControlsUiControllerImpl.access$getControlViewsById$p(ControlsUiControllerImpl.this).get(controlKey);
                if (controlViewHolder != null) {
                    controlViewHolder.actionResponse(i);
                }
            }
        });
    }

    @Override // com.android.systemui.controls.ui.ControlsUiController
    public void onRefreshState(ComponentName componentName, List<Control> list) {
        boolean isUnlocked = this.keyguardStateController.isUnlocked();
        for (Control control : list) {
            ControlWithState controlWithState = this.controlsById.get(new ControlKey(componentName, control.getControlId()));
            if (controlWithState != null) {
                Log.d("ControlsUiController", "onRefreshState() for id: " + control.getControlId());
                this.iconCache.store(componentName, control.getControlId(), control.getCustomIcon());
                final ControlWithState controlWithState2 = new ControlWithState(componentName, controlWithState.getCi(), control);
                ControlKey controlKey = new ControlKey(componentName, control.getControlId());
                this.controlsById.put(controlKey, controlWithState2);
                final ControlViewHolder controlViewHolder = this.controlViewsById.get(controlKey);
                if (controlViewHolder != null) {
                    final boolean z = !isUnlocked;
                    this.uiExecutor.execute(new Runnable() { // from class: com.android.systemui.controls.ui.ControlsUiControllerImpl$onRefreshState$1$1$1$1
                        @Override // java.lang.Runnable
                        public final void run() {
                            ControlViewHolder.this.bindData(controlWithState2, z);
                        }
                    });
                }
            }
        }
    }

    public final void putIntentExtras(Intent intent, StructureInfo structureInfo) {
        intent.putExtra("extra_app_label", ((ControlsListingController) this.controlsListingController.get()).getAppLabel(structureInfo.getComponentName()));
        intent.putExtra("extra_structure", structureInfo.getStructure());
        intent.putExtra("android.intent.extra.COMPONENT_NAME", structureInfo.getComponentName());
    }

    public final void reload(final ViewGroup viewGroup) {
        if (this.hidden) {
            return;
        }
        ControlsListingController controlsListingController = (ControlsListingController) this.controlsListingController.get();
        ControlsListingController.ControlsListingCallback controlsListingCallback = this.listingCallback;
        ControlsListingController.ControlsListingCallback controlsListingCallback2 = controlsListingCallback;
        if (controlsListingCallback == null) {
            controlsListingCallback2 = null;
        }
        controlsListingController.removeCallback(controlsListingCallback2);
        ((ControlsController) this.controlsController.get()).unsubscribe();
        PanelTaskViewController panelTaskViewController = this.taskViewController;
        if (panelTaskViewController != null) {
            panelTaskViewController.dismiss();
        }
        this.taskViewController = null;
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(viewGroup, "alpha", 1.0f, ActionBarShadowController.ELEVATION_LOW);
        ofFloat.setInterpolator(new AccelerateInterpolator(1.0f));
        ofFloat.setDuration(200L);
        ofFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.controls.ui.ControlsUiControllerImpl$reload$1
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                ControlsUiControllerImpl.access$getControlViewsById$p(ControlsUiControllerImpl.this).clear();
                ControlsUiControllerImpl.access$getControlsById$p(ControlsUiControllerImpl.this).clear();
                ControlsUiControllerImpl controlsUiControllerImpl = ControlsUiControllerImpl.this;
                ViewGroup viewGroup2 = viewGroup;
                Runnable access$getOnDismiss$p = ControlsUiControllerImpl.access$getOnDismiss$p(controlsUiControllerImpl);
                Context context = null;
                Runnable runnable = access$getOnDismiss$p;
                if (access$getOnDismiss$p == null) {
                    runnable = null;
                }
                Context access$getActivityContext$p = ControlsUiControllerImpl.access$getActivityContext$p(ControlsUiControllerImpl.this);
                if (access$getActivityContext$p != null) {
                    context = access$getActivityContext$p;
                }
                controlsUiControllerImpl.show(viewGroup2, runnable, context);
                ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(viewGroup, "alpha", ActionBarShadowController.ELEVATION_LOW, 1.0f);
                ofFloat2.setInterpolator(new DecelerateInterpolator(1.0f));
                ofFloat2.setDuration(200L);
                ofFloat2.start();
            }
        });
        ofFloat.start();
    }

    @Override // com.android.systemui.controls.ui.ControlsUiController
    public Class<?> resolveActivity() {
        boolean z;
        Class<?> cls;
        List<StructureInfo> favorites = ((ControlsController) this.controlsController.get()).getFavorites();
        SelectedItem preferredSelectedItem = getPreferredSelectedItem(favorites);
        List<ControlsServiceInfo> currentServices = ((ControlsListingController) this.controlsListingController.get()).getCurrentServices();
        if (!(currentServices instanceof Collection) || !currentServices.isEmpty()) {
            Iterator<T> it = currentServices.iterator();
            while (true) {
                z = false;
                if (!it.hasNext()) {
                    break;
                }
                if (((ControlsServiceInfo) it.next()).getPanelActivity() != null) {
                    z = true;
                    break;
                }
            }
        } else {
            z = false;
        }
        if (((ControlsController) this.controlsController.get()).addSeedingFavoritesCallback(this.onSeedingComplete)) {
            cls = ControlsActivity.class;
        } else {
            cls = ControlsActivity.class;
            if (!preferredSelectedItem.getHasControls()) {
                cls = ControlsActivity.class;
                if (favorites.size() <= 1) {
                    cls = ControlsActivity.class;
                    if (!z) {
                        cls = ControlsProviderSelectorActivity.class;
                    }
                }
            }
        }
        return cls;
    }

    /* JADX DEBUG: Multi-variable search result rejected for r0v45, resolved type: java.util.Map<com.android.systemui.controls.ui.ControlKey, com.android.systemui.controls.ui.ControlWithState> */
    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.android.systemui.controls.ui.ControlsUiController
    public void show(ViewGroup viewGroup, Runnable runnable, Context context) {
        Log.d("ControlsUiController", "show()");
        this.parent = viewGroup;
        this.onDismiss = runnable;
        this.activityContext = context;
        this.hidden = false;
        this.retainCache = false;
        this.controlActionCoordinator.setActivityContext(context);
        List<StructureInfo> favorites = ((ControlsController) this.controlsController.get()).getFavorites();
        this.allStructures = favorites;
        List<StructureInfo> list = favorites;
        if (favorites == null) {
            list = null;
        }
        this.selectedItem = getPreferredSelectedItem(list);
        if (((ControlsController) this.controlsController.get()).addSeedingFavoritesCallback(this.onSeedingComplete)) {
            this.listingCallback = createCallback(new ControlsUiControllerImpl$show$1(this));
        } else {
            SelectedItem selectedItem = this.selectedItem;
            if (!(selectedItem instanceof SelectedItem.PanelItem) && !selectedItem.getHasControls()) {
                List<StructureInfo> list2 = this.allStructures;
                List<StructureInfo> list3 = list2;
                if (list2 == null) {
                    list3 = null;
                }
                if (list3.size() <= 1) {
                    this.listingCallback = createCallback(new ControlsUiControllerImpl$show$2(this));
                }
            }
            SelectedItem selectedItem2 = this.selectedItem;
            if (selectedItem2 instanceof SelectedItem.StructureItem) {
                SelectedItem.StructureItem structureItem = (SelectedItem.StructureItem) selectedItem2;
                List<ControlInfo> controls = structureItem.getStructure().getControls();
                ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(controls, 10));
                for (ControlInfo controlInfo : controls) {
                    arrayList.add(new ControlWithState(structureItem.getStructure().getComponentName(), controlInfo, null));
                }
                Map<ControlKey, ControlWithState> map = this.controlsById;
                for (Object obj : arrayList) {
                    map.put(new ControlKey(structureItem.getStructure().getComponentName(), ((ControlWithState) obj).getCi().getControlId()), obj);
                }
                ((ControlsController) this.controlsController.get()).subscribeToFavorites(structureItem.getStructure());
            }
            this.listingCallback = createCallback(new ControlsUiControllerImpl$show$5(this));
        }
        ControlsListingController controlsListingController = (ControlsListingController) this.controlsListingController.get();
        ControlsListingController.ControlsListingCallback controlsListingCallback = this.listingCallback;
        if (controlsListingCallback == null) {
            controlsListingCallback = null;
        }
        controlsListingController.addCallback(controlsListingCallback);
    }

    public final void showControlsView(List<SelectionItem> list) {
        this.controlViewsById.clear();
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (Object obj : list) {
            if (((SelectionItem) obj).isPanel()) {
                arrayList.add(obj);
            } else {
                arrayList2.add(obj);
            }
        }
        Pair pair = new Pair(arrayList, arrayList2);
        List list2 = (List) pair.component1();
        List list3 = (List) pair.component2();
        List<SelectionItem> list4 = list2;
        ArrayList arrayList3 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list4, 10));
        for (SelectionItem selectionItem : list4) {
            arrayList3.add(selectionItem.getComponentName());
        }
        Set set = CollectionsKt___CollectionsKt.toSet(arrayList3);
        List list5 = list3;
        LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt___RangesKt.coerceAtLeast(MapsKt__MapsJVMKt.mapCapacity(CollectionsKt__IterablesKt.collectionSizeOrDefault(list5, 10)), 16));
        for (Object obj2 : list5) {
            linkedHashMap.put(((SelectionItem) obj2).getComponentName(), obj2);
        }
        LinkedHashMap linkedHashMap2 = new LinkedHashMap();
        for (Map.Entry entry : linkedHashMap.entrySet()) {
            if (!set.contains(entry.getKey())) {
                linkedHashMap2.put(entry.getKey(), entry.getValue());
            }
        }
        ArrayList arrayList4 = new ArrayList();
        List<StructureInfo> list6 = this.allStructures;
        List<StructureInfo> list7 = list6;
        if (list6 == null) {
            list7 = null;
        }
        for (StructureInfo structureInfo : list7) {
            SelectionItem selectionItem2 = (SelectionItem) linkedHashMap2.get(structureInfo.getComponentName());
            SelectionItem copy$default = selectionItem2 != null ? SelectionItem.copy$default(selectionItem2, null, structureInfo.getStructure(), null, null, 0, null, 61, null) : null;
            if (copy$default != null) {
                arrayList4.add(copy$default);
            }
        }
        List list8 = list2;
        arrayList4.addAll(list8);
        CollectionsKt__MutableCollectionsJVMKt.sortWith(arrayList4, this.localeComparator);
        this.lastSelections = arrayList4;
        SelectionItem findSelectionItem = findSelectionItem(this.selectedItem, arrayList4);
        SelectionItem selectionItem3 = findSelectionItem;
        if (findSelectionItem == null) {
            selectionItem3 = list8.isEmpty() ^ true ? (SelectionItem) list2.get(0) : list.get(0);
        }
        maybeUpdateSelectedItem(selectionItem3);
        createControlsSpaceFrame();
        if (this.taskViewFactory.isPresent() && selectionItem3.isPanel()) {
            ComponentName panelComponentName = selectionItem3.getPanelComponentName();
            Intrinsics.checkNotNull(panelComponentName);
            createPanelView(panelComponentName);
        } else if (selectionItem3.isPanel()) {
            Log.w("ControlsUiController", "Not TaskViewFactory to display panel " + selectionItem3);
        } else {
            this.controlsMetricsLogger.refreshBegin(selectionItem3.getUid(), !this.keyguardStateController.isUnlocked());
            createListView(selectionItem3);
        }
        createDropDown(arrayList4, selectionItem3);
        createMenu();
    }

    public final void showInitialSetupView(List<SelectionItem> list) {
        startProviderSelectorActivity();
        Runnable runnable = this.onDismiss;
        Runnable runnable2 = runnable;
        if (runnable == null) {
            runnable2 = null;
        }
        runnable2.run();
    }

    public final void showSeedingView(List<SelectionItem> list) {
        LayoutInflater from = LayoutInflater.from(this.context);
        int i = R$layout.controls_no_favorites;
        ViewGroup viewGroup = this.parent;
        ViewGroup viewGroup2 = viewGroup;
        if (viewGroup == null) {
            viewGroup2 = null;
        }
        from.inflate(i, viewGroup2, true);
        ViewGroup viewGroup3 = this.parent;
        if (viewGroup3 == null) {
            viewGroup3 = null;
        }
        ((TextView) viewGroup3.requireViewById(R$id.controls_subtitle)).setText(this.context.getResources().getString(R$string.controls_seeding_in_progress));
    }

    public final void startActivity(Intent intent) {
        intent.putExtra("extra_animate", true);
        if (this.keyguardStateController.isShowing()) {
            this.activityStarter.postStartActivityDismissingKeyguard(intent, 0);
            return;
        }
        Context context = this.activityContext;
        Activity activity = null;
        Context context2 = context;
        if (context == null) {
            context2 = null;
        }
        Context context3 = this.activityContext;
        if (context3 != null) {
            activity = context3;
        }
        context2.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(activity, new android.util.Pair[0]).toBundle());
    }

    public final void startEditingActivity(StructureInfo structureInfo) {
        startTargetedActivity(structureInfo, ControlsEditingActivity.class);
    }

    public final void startFavoritingActivity(StructureInfo structureInfo) {
        startTargetedActivity(structureInfo, ControlsFavoritingActivity.class);
    }

    public final void startProviderSelectorActivity() {
        Context context = this.activityContext;
        Context context2 = context;
        if (context == null) {
            context2 = null;
        }
        Intent intent = new Intent(context2, ControlsProviderSelectorActivity.class);
        intent.putExtra("back_should_exit", true);
        startActivity(intent);
    }

    public final void startTargetedActivity(StructureInfo structureInfo, Class<?> cls) {
        Context context = this.activityContext;
        Context context2 = context;
        if (context == null) {
            context2 = null;
        }
        Intent intent = new Intent(context2, cls);
        putIntentExtras(intent, structureInfo);
        startActivity(intent);
        this.retainCache = true;
    }

    public final void switchAppOrStructure(SelectionItem selectionItem) {
        if (maybeUpdateSelectedItem(selectionItem)) {
            ViewGroup viewGroup = this.parent;
            ViewGroup viewGroup2 = viewGroup;
            if (viewGroup == null) {
                viewGroup2 = null;
            }
            reload(viewGroup2);
        }
    }

    public final void updatePreferences(SelectedItem selectedItem) {
        getSharedPreferences().edit().putString("controls_component", selectedItem.getComponentName().flattenToString()).putString("controls_structure", selectedItem.getName().toString()).putBoolean("controls_is_panel", selectedItem instanceof SelectedItem.PanelItem).commit();
    }
}