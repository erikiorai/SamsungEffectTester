package com.android.systemui.keyguard.domain.interactor;

import android.app.AlertDialog;
import android.content.Intent;
import android.util.Log;
import com.android.internal.widget.LockPatternUtils;
import com.android.systemui.animation.ActivityLaunchAnimator;
import com.android.systemui.animation.DialogLaunchAnimator;
import com.android.systemui.animation.Expandable;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.Flags;
import com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig;
import com.android.systemui.keyguard.data.repository.KeyguardQuickAffordanceRepository;
import com.android.systemui.keyguard.domain.model.KeyguardQuickAffordanceModel;
import com.android.systemui.keyguard.domain.quickaffordance.KeyguardQuickAffordanceRegistry;
import com.android.systemui.keyguard.shared.model.KeyguardPickerFlag;
import com.android.systemui.keyguard.shared.model.KeyguardQuickAffordancePickerRepresentation;
import com.android.systemui.keyguard.shared.model.KeyguardSlotPickerRepresentation;
import com.android.systemui.keyguard.shared.quickaffordance.KeyguardQuickAffordancePosition;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import dagger.Lazy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.MapsKt__MapsJVMKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt___RangesKt;
import kotlin.text.StringsKt__StringsKt;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.internal.CombineKt;

/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/domain/interactor/KeyguardQuickAffordanceInteractor.class */
public final class KeyguardQuickAffordanceInteractor {
    public static final Companion Companion = new Companion(null);
    public final ActivityStarter activityStarter;
    public final FeatureFlags featureFlags;
    public final KeyguardInteractor keyguardInteractor;
    public final KeyguardStateController keyguardStateController;
    public final DialogLaunchAnimator launchAnimator;
    public final LockPatternUtils lockPatternUtils;
    public final KeyguardQuickAffordanceRegistry<? extends KeyguardQuickAffordanceConfig> registry;
    public final Lazy<KeyguardQuickAffordanceRepository> repository;
    public final UserTracker userTracker;

    /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/domain/interactor/KeyguardQuickAffordanceInteractor$Companion.class */
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public KeyguardQuickAffordanceInteractor(KeyguardInteractor keyguardInteractor, KeyguardQuickAffordanceRegistry<? extends KeyguardQuickAffordanceConfig> keyguardQuickAffordanceRegistry, LockPatternUtils lockPatternUtils, KeyguardStateController keyguardStateController, UserTracker userTracker, ActivityStarter activityStarter, FeatureFlags featureFlags, Lazy<KeyguardQuickAffordanceRepository> lazy, DialogLaunchAnimator dialogLaunchAnimator) {
        this.keyguardInteractor = keyguardInteractor;
        this.registry = keyguardQuickAffordanceRegistry;
        this.lockPatternUtils = lockPatternUtils;
        this.keyguardStateController = keyguardStateController;
        this.userTracker = userTracker;
        this.activityStarter = activityStarter;
        this.featureFlags = featureFlags;
        this.repository = lazy;
        this.launchAnimator = dialogLaunchAnimator;
    }

    public final Flow<KeyguardQuickAffordanceModel> combinedConfigs(final KeyguardQuickAffordancePosition keyguardQuickAffordancePosition, final List<? extends KeyguardQuickAffordanceConfig> list) {
        if (list.isEmpty()) {
            return FlowKt.flowOf(KeyguardQuickAffordanceModel.Hidden.INSTANCE);
        }
        List<? extends KeyguardQuickAffordanceConfig> list2 = list;
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list2, 10));
        for (KeyguardQuickAffordanceConfig keyguardQuickAffordanceConfig : list2) {
            arrayList.add(FlowKt.onStart(keyguardQuickAffordanceConfig.getLockScreenState(), new KeyguardQuickAffordanceInteractor$combinedConfigs$1$1(null)));
        }
        final Flow[] flowArr = (Flow[]) CollectionsKt___CollectionsKt.toList(arrayList).toArray(new Flow[0]);
        return new Flow<KeyguardQuickAffordanceModel>() { // from class: com.android.systemui.keyguard.domain.interactor.KeyguardQuickAffordanceInteractor$combinedConfigs$$inlined$combine$1

            @DebugMetadata(c = "com.android.systemui.keyguard.domain.interactor.KeyguardQuickAffordanceInteractor$combinedConfigs$$inlined$combine$1$3", f = "KeyguardQuickAffordanceInteractor.kt", l = {292}, m = "invokeSuspend")
            /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardQuickAffordanceInteractor$combinedConfigs$$inlined$combine$1$3  reason: invalid class name */
            /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/domain/interactor/KeyguardQuickAffordanceInteractor$combinedConfigs$$inlined$combine$1$3.class */
            public static final class AnonymousClass3 extends SuspendLambda implements Function3<FlowCollector<? super KeyguardQuickAffordanceModel>, KeyguardQuickAffordanceConfig.LockScreenState[], Continuation<? super Unit>, Object> {
                public final /* synthetic */ List $configs$inlined;
                public final /* synthetic */ KeyguardQuickAffordancePosition $position$inlined;
                private /* synthetic */ Object L$0;
                public /* synthetic */ Object L$1;
                public int label;
                public final /* synthetic */ KeyguardQuickAffordanceInteractor this$0;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public AnonymousClass3(Continuation continuation, List list, KeyguardQuickAffordanceInteractor keyguardQuickAffordanceInteractor, KeyguardQuickAffordancePosition keyguardQuickAffordancePosition) {
                    super(3, continuation);
                    this.$configs$inlined = list;
                    this.this$0 = keyguardQuickAffordanceInteractor;
                    this.$position$inlined = keyguardQuickAffordancePosition;
                }

                /* JADX DEBUG: Multi-variable search result rejected for r5v0, resolved type: com.android.systemui.keyguard.domain.interactor.KeyguardQuickAffordanceInteractor$combinedConfigs$$inlined$combine$1$3 */
                /* JADX WARN: Multi-variable type inference failed */
                public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2, Object obj3) {
                    return invoke((FlowCollector<? super KeyguardQuickAffordanceModel>) obj, (KeyguardQuickAffordanceConfig.LockScreenState[]) obj2, (Continuation<? super Unit>) obj3);
                }

                public final Object invoke(FlowCollector<? super KeyguardQuickAffordanceModel> flowCollector, KeyguardQuickAffordanceConfig.LockScreenState[] lockScreenStateArr, Continuation<? super Unit> continuation) {
                    AnonymousClass3 anonymousClass3 = new AnonymousClass3(continuation, this.$configs$inlined, this.this$0, this.$position$inlined);
                    anonymousClass3.L$0 = flowCollector;
                    anonymousClass3.L$1 = lockScreenStateArr;
                    return anonymousClass3.invokeSuspend(Unit.INSTANCE);
                }

                public final Object invokeSuspend(Object obj) {
                    Object obj2;
                    boolean isUsingRepository;
                    String encode;
                    Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
                    int i = this.label;
                    if (i == 0) {
                        ResultKt.throwOnFailure(obj);
                        FlowCollector flowCollector = (FlowCollector) this.L$0;
                        KeyguardQuickAffordanceConfig.LockScreenState[] lockScreenStateArr = (KeyguardQuickAffordanceConfig.LockScreenState[]) ((Object[]) this.L$1);
                        int i2 = 0;
                        int length = lockScreenStateArr.length;
                        while (true) {
                            if (i2 >= length) {
                                i2 = -1;
                                break;
                            } else if (lockScreenStateArr[i2] instanceof KeyguardQuickAffordanceConfig.LockScreenState.Visible) {
                                break;
                            } else {
                                i2++;
                            }
                        }
                        if (i2 != -1) {
                            KeyguardQuickAffordanceConfig.LockScreenState.Visible visible = (KeyguardQuickAffordanceConfig.LockScreenState.Visible) lockScreenStateArr[i2];
                            String key = ((KeyguardQuickAffordanceConfig) this.$configs$inlined.get(i2)).getKey();
                            String str = key;
                            isUsingRepository = this.this$0.isUsingRepository();
                            if (isUsingRepository) {
                                encode = this.this$0.encode(key, this.$position$inlined.toSlotId());
                                str = encode;
                            }
                            obj2 = new KeyguardQuickAffordanceModel.Visible(str, visible.getIcon(), visible.getActivationState());
                        } else {
                            obj2 = KeyguardQuickAffordanceModel.Hidden.INSTANCE;
                        }
                        this.label = 1;
                        if (flowCollector.emit(obj2, this) == coroutine_suspended) {
                            return coroutine_suspended;
                        }
                    } else if (i != 1) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    } else {
                        ResultKt.throwOnFailure(obj);
                    }
                    return Unit.INSTANCE;
                }
            }

            public Object collect(FlowCollector flowCollector, Continuation continuation) {
                final Flow[] flowArr2 = flowArr;
                Object combineInternal = CombineKt.combineInternal(flowCollector, flowArr2, new Function0<KeyguardQuickAffordanceConfig.LockScreenState[]>() { // from class: com.android.systemui.keyguard.domain.interactor.KeyguardQuickAffordanceInteractor$combinedConfigs$$inlined$combine$1.2
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    /* JADX DEBUG: Method merged with bridge method */
                    public final KeyguardQuickAffordanceConfig.LockScreenState[] invoke() {
                        return new KeyguardQuickAffordanceConfig.LockScreenState[flowArr2.length];
                    }
                }, new AnonymousClass3(null, list, this, keyguardQuickAffordancePosition), continuation);
                return combineInternal == IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED() ? combineInternal : Unit.INSTANCE;
            }
        };
    }

    public final Pair<String, String> decode(String str) {
        List split$default = StringsKt__StringsKt.split$default(str, new String[]{"::"}, false, 0, 6, (Object) null);
        return new Pair<>(split$default.get(0), split$default.get(1));
    }

    public final String encode(String str, String str2) {
        return str2 + "::" + str;
    }

    public final Object getAffordancePickerRepresentations(Continuation<? super List<KeyguardQuickAffordancePickerRepresentation>> continuation) {
        return ((KeyguardQuickAffordanceRepository) this.repository.get()).getAffordancePickerRepresentations(continuation);
    }

    public final List<KeyguardPickerFlag> getPickerFlags() {
        return CollectionsKt__CollectionsKt.listOf(new KeyguardPickerFlag[]{new KeyguardPickerFlag("revamped_wallpaper_ui", this.featureFlags.isEnabled(Flags.REVAMPED_WALLPAPER_UI)), new KeyguardPickerFlag("is_custom_lock_screen_quick_affordances_feature_enabled", this.featureFlags.isEnabled(Flags.CUSTOMIZABLE_LOCK_SCREEN_QUICK_AFFORDANCES)), new KeyguardPickerFlag("is_custom_clocks_feature_enabled", this.featureFlags.isEnabled(Flags.LOCKSCREEN_CUSTOM_CLOCKS))});
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0045  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0071  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x00ed A[LOOP:0: B:21:0x00e3->B:23:0x00ed, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0139  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object getSelections(Continuation<? super Map<String, ? extends List<KeyguardQuickAffordancePickerRepresentation>>> continuation) {
        Continuation<? super List<KeyguardQuickAffordancePickerRepresentation>> keyguardQuickAffordanceInteractor$getSelections$1;
        int i;
        List<KeyguardSlotPickerRepresentation> slotPickerRepresentations;
        Map<String, List<String>> map;
        if (continuation instanceof KeyguardQuickAffordanceInteractor$getSelections$1) {
            Continuation<? super List<KeyguardQuickAffordancePickerRepresentation>> continuation2 = (KeyguardQuickAffordanceInteractor$getSelections$1) continuation;
            int i2 = continuation2.label;
            if ((i2 & Integer.MIN_VALUE) != 0) {
                continuation2.label = i2 - 2147483648;
                keyguardQuickAffordanceInteractor$getSelections$1 = continuation2;
                Object obj = keyguardQuickAffordanceInteractor$getSelections$1.result;
                Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
                i = keyguardQuickAffordanceInteractor$getSelections$1.label;
                if (i != 0) {
                    ResultKt.throwOnFailure(obj);
                    slotPickerRepresentations = ((KeyguardQuickAffordanceRepository) this.repository.get()).getSlotPickerRepresentations();
                    Map<String, List<String>> selections = ((KeyguardQuickAffordanceRepository) this.repository.get()).getSelections();
                    keyguardQuickAffordanceInteractor$getSelections$1.L$0 = slotPickerRepresentations;
                    keyguardQuickAffordanceInteractor$getSelections$1.L$1 = selections;
                    keyguardQuickAffordanceInteractor$getSelections$1.label = 1;
                    obj = getAffordancePickerRepresentations(keyguardQuickAffordanceInteractor$getSelections$1);
                    if (obj == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                    map = selections;
                } else if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                } else {
                    Map<String, List<String>> map2 = (Map) keyguardQuickAffordanceInteractor$getSelections$1.L$1;
                    slotPickerRepresentations = (List) keyguardQuickAffordanceInteractor$getSelections$1.L$0;
                    ResultKt.throwOnFailure(obj);
                    map = map2;
                }
                Iterable iterable = (Iterable) obj;
                LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt___RangesKt.coerceAtLeast(MapsKt__MapsJVMKt.mapCapacity(CollectionsKt__IterablesKt.collectionSizeOrDefault(iterable, 10)), 16));
                for (Object obj2 : iterable) {
                    linkedHashMap.put(((KeyguardQuickAffordancePickerRepresentation) obj2).getId(), obj2);
                }
                List<KeyguardSlotPickerRepresentation> list = slotPickerRepresentations;
                LinkedHashMap linkedHashMap2 = new LinkedHashMap(RangesKt___RangesKt.coerceAtLeast(MapsKt__MapsJVMKt.mapCapacity(CollectionsKt__IterablesKt.collectionSizeOrDefault(list, 10)), 16));
                for (KeyguardSlotPickerRepresentation keyguardSlotPickerRepresentation : list) {
                    String id = keyguardSlotPickerRepresentation.getId();
                    List<String> list2 = map.get(keyguardSlotPickerRepresentation.getId());
                    List<String> list3 = list2;
                    if (list2 == null) {
                        list3 = CollectionsKt__CollectionsKt.emptyList();
                    }
                    ArrayList arrayList = new ArrayList();
                    for (String str : list3) {
                        KeyguardQuickAffordancePickerRepresentation keyguardQuickAffordancePickerRepresentation = (KeyguardQuickAffordancePickerRepresentation) linkedHashMap.get(str);
                        if (keyguardQuickAffordancePickerRepresentation != null) {
                            arrayList.add(keyguardQuickAffordancePickerRepresentation);
                        }
                    }
                    Pair pair = TuplesKt.to(id, arrayList);
                    linkedHashMap2.put(pair.getFirst(), pair.getSecond());
                }
                return linkedHashMap2;
            }
        }
        keyguardQuickAffordanceInteractor$getSelections$1 = new KeyguardQuickAffordanceInteractor$getSelections$1(this, continuation);
        Object obj3 = keyguardQuickAffordanceInteractor$getSelections$1.result;
        Object coroutine_suspended2 = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
        i = keyguardQuickAffordanceInteractor$getSelections$1.label;
        if (i != 0) {
        }
        Iterable iterable2 = (Iterable) obj3;
        LinkedHashMap linkedHashMap3 = new LinkedHashMap(RangesKt___RangesKt.coerceAtLeast(MapsKt__MapsJVMKt.mapCapacity(CollectionsKt__IterablesKt.collectionSizeOrDefault(iterable2, 10)), 16));
        while (r0.hasNext()) {
        }
        List<KeyguardSlotPickerRepresentation> list4 = slotPickerRepresentations;
        LinkedHashMap linkedHashMap22 = new LinkedHashMap(RangesKt___RangesKt.coerceAtLeast(MapsKt__MapsJVMKt.mapCapacity(CollectionsKt__IterablesKt.collectionSizeOrDefault(list4, 10)), 16));
        while (r0.hasNext()) {
        }
        return linkedHashMap22;
    }

    public final List<KeyguardSlotPickerRepresentation> getSlotPickerRepresentations() {
        if (isUsingRepository()) {
            return ((KeyguardQuickAffordanceRepository) this.repository.get()).getSlotPickerRepresentations();
        }
        throw new IllegalStateException("Check failed.".toString());
    }

    public final boolean getUseLongPress() {
        return this.featureFlags.isEnabled(Flags.CUSTOMIZABLE_LOCK_SCREEN_QUICK_AFFORDANCES);
    }

    public final boolean isUsingRepository() {
        return this.featureFlags.isEnabled(Flags.CUSTOMIZABLE_LOCK_SCREEN_QUICK_AFFORDANCES);
    }

    public final void launchQuickAffordance(Intent intent, boolean z, Expandable expandable) {
        ActivityLaunchAnimator.Controller controller = null;
        if (this.lockPatternUtils.getStrongAuthForUser(this.userTracker.getUserHandle().getIdentifier()) == 1 || !(z || this.keyguardStateController.isUnlocked())) {
            ActivityStarter activityStarter = this.activityStarter;
            if (expandable != null) {
                controller = Expandable.activityLaunchController$default(expandable, null, 1, null);
            }
            activityStarter.postStartActivityDismissingKeyguard(intent, 0, controller);
            return;
        }
        ActivityStarter activityStarter2 = this.activityStarter;
        ActivityLaunchAnimator.Controller controller2 = null;
        if (expandable != null) {
            controller2 = Expandable.activityLaunchController$default(expandable, null, 1, null);
        }
        activityStarter2.startActivity(intent, true, controller2, true);
    }

    public final void onQuickAffordanceTriggered(String str, Expandable expandable) {
        Object obj;
        KeyguardQuickAffordanceConfig keyguardQuickAffordanceConfig = null;
        if (isUsingRepository()) {
            Pair<String, String> decode = decode(str);
            String str2 = (String) decode.component1();
            String str3 = (String) decode.component2();
            List list = (List) ((Map) ((KeyguardQuickAffordanceRepository) this.repository.get()).m2964getSelections().getValue()).get(str2);
            if (list != null) {
                Iterator it = list.iterator();
                do {
                    obj = null;
                    if (!it.hasNext()) {
                        break;
                    }
                    obj = it.next();
                } while (!Intrinsics.areEqual(((KeyguardQuickAffordanceConfig) obj).getKey(), str3));
                keyguardQuickAffordanceConfig = (KeyguardQuickAffordanceConfig) obj;
            }
        } else {
            keyguardQuickAffordanceConfig = this.registry.get(str);
        }
        if (keyguardQuickAffordanceConfig == null) {
            Log.e("KeyguardQuickAffordanceInteractor", "Affordance config with key of \"" + str + "\" not found!");
            return;
        }
        KeyguardQuickAffordanceConfig.OnTriggeredResult onTriggered = keyguardQuickAffordanceConfig.onTriggered(expandable);
        if (onTriggered instanceof KeyguardQuickAffordanceConfig.OnTriggeredResult.StartActivity) {
            KeyguardQuickAffordanceConfig.OnTriggeredResult.StartActivity startActivity = (KeyguardQuickAffordanceConfig.OnTriggeredResult.StartActivity) onTriggered;
            launchQuickAffordance(startActivity.getIntent(), startActivity.getCanShowWhileLocked(), expandable);
        } else if ((onTriggered instanceof KeyguardQuickAffordanceConfig.OnTriggeredResult.Handled) || !(onTriggered instanceof KeyguardQuickAffordanceConfig.OnTriggeredResult.ShowDialog)) {
        } else {
            KeyguardQuickAffordanceConfig.OnTriggeredResult.ShowDialog showDialog = (KeyguardQuickAffordanceConfig.OnTriggeredResult.ShowDialog) onTriggered;
            showDialog(showDialog.getDialog(), showDialog.getExpandable());
        }
    }

    public final Flow<KeyguardQuickAffordanceModel> quickAffordance(KeyguardQuickAffordancePosition keyguardQuickAffordancePosition) {
        return FlowKt.combine(quickAffordanceAlwaysVisible(keyguardQuickAffordancePosition), this.keyguardInteractor.isDozing(), this.keyguardInteractor.isKeyguardShowing(), new KeyguardQuickAffordanceInteractor$quickAffordance$1(null));
    }

    public final Flow<KeyguardQuickAffordanceModel> quickAffordanceAlwaysVisible(KeyguardQuickAffordancePosition keyguardQuickAffordancePosition) {
        return quickAffordanceInternal(keyguardQuickAffordancePosition);
    }

    public final Flow<KeyguardQuickAffordanceModel> quickAffordanceInternal(final KeyguardQuickAffordancePosition keyguardQuickAffordancePosition) {
        Flow<KeyguardQuickAffordanceModel> combinedConfigs;
        if (isUsingRepository()) {
            final Flow m2964getSelections = ((KeyguardQuickAffordanceRepository) this.repository.get()).m2964getSelections();
            combinedConfigs = FlowKt.transformLatest(new Flow<List<? extends KeyguardQuickAffordanceConfig>>() { // from class: com.android.systemui.keyguard.domain.interactor.KeyguardQuickAffordanceInteractor$quickAffordanceInternal$$inlined$map$1

                /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardQuickAffordanceInteractor$quickAffordanceInternal$$inlined$map$1$2  reason: invalid class name */
                /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/domain/interactor/KeyguardQuickAffordanceInteractor$quickAffordanceInternal$$inlined$map$1$2.class */
                public static final class AnonymousClass2<T> implements FlowCollector {
                    public final /* synthetic */ KeyguardQuickAffordancePosition $position$inlined;
                    public final /* synthetic */ FlowCollector $this_unsafeFlow;

                    @DebugMetadata(c = "com.android.systemui.keyguard.domain.interactor.KeyguardQuickAffordanceInteractor$quickAffordanceInternal$$inlined$map$1$2", f = "KeyguardQuickAffordanceInteractor.kt", l = {223}, m = "emit")
                    /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardQuickAffordanceInteractor$quickAffordanceInternal$$inlined$map$1$2$1  reason: invalid class name */
                    /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/domain/interactor/KeyguardQuickAffordanceInteractor$quickAffordanceInternal$$inlined$map$1$2$1.class */
                    public static final class AnonymousClass1 extends ContinuationImpl {
                        public Object L$0;
                        public int label;
                        public /* synthetic */ Object result;

                        public AnonymousClass1(Continuation continuation) {
                            super(continuation);
                        }

                        public final Object invokeSuspend(Object obj) {
                            this.result = obj;
                            this.label |= Integer.MIN_VALUE;
                            return AnonymousClass2.this.emit(null, this);
                        }
                    }

                    public AnonymousClass2(FlowCollector flowCollector, KeyguardQuickAffordancePosition keyguardQuickAffordancePosition) {
                        this.$this_unsafeFlow = flowCollector;
                        this.$position$inlined = keyguardQuickAffordancePosition;
                    }

                    /* JADX WARN: Removed duplicated region for block: B:10:0x0047  */
                    /* JADX WARN: Removed duplicated region for block: B:15:0x005e  */
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                    */
                    public final Object emit(Object obj, Continuation continuation) {
                        AnonymousClass1 anonymousClass1;
                        int i;
                        if (continuation instanceof AnonymousClass1) {
                            AnonymousClass1 anonymousClass12 = (AnonymousClass1) continuation;
                            int i2 = anonymousClass12.label;
                            if ((i2 & Integer.MIN_VALUE) != 0) {
                                anonymousClass12.label = i2 - 2147483648;
                                anonymousClass1 = anonymousClass12;
                                Object obj2 = anonymousClass1.result;
                                Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
                                i = anonymousClass1.label;
                                if (i != 0) {
                                    ResultKt.throwOnFailure(obj2);
                                    FlowCollector flowCollector = this.$this_unsafeFlow;
                                    List list = (List) ((Map) obj).get(this.$position$inlined.toSlotId());
                                    List list2 = list;
                                    if (list == null) {
                                        list2 = CollectionsKt__CollectionsKt.emptyList();
                                    }
                                    anonymousClass1.label = 1;
                                    if (flowCollector.emit(list2, anonymousClass1) == coroutine_suspended) {
                                        return coroutine_suspended;
                                    }
                                } else if (i != 1) {
                                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                } else {
                                    ResultKt.throwOnFailure(obj2);
                                }
                                return Unit.INSTANCE;
                            }
                        }
                        anonymousClass1 = new AnonymousClass1(continuation);
                        Object obj22 = anonymousClass1.result;
                        Object coroutine_suspended2 = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
                        i = anonymousClass1.label;
                        if (i != 0) {
                        }
                        return Unit.INSTANCE;
                    }
                }

                public Object collect(FlowCollector flowCollector, Continuation continuation) {
                    Object collect = m2964getSelections.collect(new AnonymousClass2(flowCollector, keyguardQuickAffordancePosition), continuation);
                    return collect == IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED() ? collect : Unit.INSTANCE;
                }
            }, new KeyguardQuickAffordanceInteractor$quickAffordanceInternal$$inlined$flatMapLatest$1(null, this, keyguardQuickAffordancePosition));
        } else {
            combinedConfigs = combinedConfigs(keyguardQuickAffordancePosition, this.registry.getAll(keyguardQuickAffordancePosition));
        }
        return combinedConfigs;
    }

    public final boolean select(String str, String str2) {
        Object obj;
        if (isUsingRepository()) {
            Iterator<T> it = ((KeyguardQuickAffordanceRepository) this.repository.get()).getSlotPickerRepresentations().iterator();
            while (true) {
                if (!it.hasNext()) {
                    obj = null;
                    break;
                }
                obj = it.next();
                if (Intrinsics.areEqual(((KeyguardSlotPickerRepresentation) obj).getId(), str)) {
                    break;
                }
            }
            KeyguardSlotPickerRepresentation keyguardSlotPickerRepresentation = (KeyguardSlotPickerRepresentation) obj;
            if (keyguardSlotPickerRepresentation == null) {
                return false;
            }
            List<String> mutableList = CollectionsKt___CollectionsKt.toMutableList(((KeyguardQuickAffordanceRepository) this.repository.get()).getSelections().getOrDefault(str, CollectionsKt__CollectionsKt.emptyList()));
            if (!mutableList.remove(str2)) {
                while (mutableList.size() > 0 && mutableList.size() >= keyguardSlotPickerRepresentation.getMaxSelectedAffordances()) {
                    mutableList.remove(0);
                }
            }
            mutableList.add(str2);
            ((KeyguardQuickAffordanceRepository) this.repository.get()).setSelections(str, mutableList);
            return true;
        }
        throw new IllegalStateException("Check failed.".toString());
    }

    public final void showDialog(AlertDialog alertDialog, Expandable expandable) {
        DialogLaunchAnimator.Controller dialogLaunchController$default;
        if (expandable == null || (dialogLaunchController$default = Expandable.dialogLaunchController$default(expandable, null, 1, null)) == null) {
            return;
        }
        SystemUIDialog.applyFlags(alertDialog);
        SystemUIDialog.setShowForAllUsers(alertDialog, true);
        SystemUIDialog.registerDismissListener(alertDialog);
        SystemUIDialog.setDialogSize(alertDialog);
        DialogLaunchAnimator.show$default(this.launchAnimator, alertDialog, dialogLaunchController$default, false, 4, null);
    }

    public final boolean unselect(String str, String str2) {
        Object obj;
        if (isUsingRepository()) {
            Iterator<T> it = ((KeyguardQuickAffordanceRepository) this.repository.get()).getSlotPickerRepresentations().iterator();
            while (true) {
                if (!it.hasNext()) {
                    obj = null;
                    break;
                }
                obj = it.next();
                if (Intrinsics.areEqual(((KeyguardSlotPickerRepresentation) obj).getId(), str)) {
                    break;
                }
            }
            boolean z = false;
            if (obj == null) {
                return false;
            }
            if (str2 == null || str2.length() == 0) {
                if (!((KeyguardQuickAffordanceRepository) this.repository.get()).getSelections().getOrDefault(str, CollectionsKt__CollectionsKt.emptyList()).isEmpty()) {
                    ((KeyguardQuickAffordanceRepository) this.repository.get()).setSelections(str, CollectionsKt__CollectionsKt.emptyList());
                    z = true;
                }
                return z;
            }
            List<String> mutableList = CollectionsKt___CollectionsKt.toMutableList(((KeyguardQuickAffordanceRepository) this.repository.get()).getSelections().getOrDefault(str, CollectionsKt__CollectionsKt.emptyList()));
            boolean z2 = false;
            if (mutableList.remove(str2)) {
                ((KeyguardQuickAffordanceRepository) this.repository.get()).setSelections(str, mutableList);
                z2 = true;
            }
            return z2;
        }
        throw new IllegalStateException("Check failed.".toString());
    }
}