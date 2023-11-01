package com.android.systemui.qs.external;

import android.content.ComponentName;
import android.content.DialogInterface;
import android.graphics.drawable.Icon;
import android.os.RemoteException;
import android.util.Log;
import com.android.internal.logging.InstanceId;
import com.android.internal.statusbar.IAddTileResultCallback;
import com.android.systemui.R$string;
import com.android.systemui.qs.QSTileHost;
import com.android.systemui.qs.external.TileRequestDialog;
import com.android.systemui.qs.external.TileServiceRequestController;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.commandline.Command;
import com.android.systemui.statusbar.commandline.CommandRegistry;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import java.io.PrintWriter;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/external/TileServiceRequestController.class */
public final class TileServiceRequestController {
    public static final Companion Companion = new Companion(null);
    public final CommandQueue commandQueue;
    public final TileServiceRequestController$commandQueueCallback$1 commandQueueCallback;
    public final CommandRegistry commandRegistry;
    public Function1<? super String, Unit> dialogCanceller;
    public final Function0<TileRequestDialog> dialogCreator;
    public final TileRequestDialogEventLogger eventLogger;
    public final QSTileHost qsTileHost;

    /* loaded from: mainsysui33.jar:com/android/systemui/qs/external/TileServiceRequestController$Builder.class */
    public static final class Builder {
        public final CommandQueue commandQueue;
        public final CommandRegistry commandRegistry;

        public Builder(CommandQueue commandQueue, CommandRegistry commandRegistry) {
            this.commandQueue = commandQueue;
            this.commandRegistry = commandRegistry;
        }

        public final TileServiceRequestController create(QSTileHost qSTileHost) {
            return new TileServiceRequestController(qSTileHost, this.commandQueue, this.commandRegistry, new TileRequestDialogEventLogger(), null, 16, null);
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/qs/external/TileServiceRequestController$Companion.class */
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/qs/external/TileServiceRequestController$SingleShotConsumer.class */
    public static final class SingleShotConsumer<T> implements Consumer<T> {
        public final Consumer<T> consumer;
        public final AtomicBoolean dispatched = new AtomicBoolean(false);

        public SingleShotConsumer(Consumer<T> consumer) {
            this.consumer = consumer;
        }

        @Override // java.util.function.Consumer
        public void accept(T t) {
            if (this.dispatched.compareAndSet(false, true)) {
                this.consumer.accept(t);
            }
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/qs/external/TileServiceRequestController$TileServiceRequestCommand.class */
    public final class TileServiceRequestCommand implements Command {
        public TileServiceRequestCommand() {
            TileServiceRequestController.this = r4;
        }

        public void execute(PrintWriter printWriter, List<String> list) {
            ComponentName unflattenFromString = ComponentName.unflattenFromString(list.get(0));
            if (unflattenFromString != null) {
                TileServiceRequestController.this.requestTileAdd$frameworks__base__packages__SystemUI__android_common__SystemUI_core(unflattenFromString, list.get(1), list.get(2), null, new Consumer() { // from class: com.android.systemui.qs.external.TileServiceRequestController$TileServiceRequestCommand$execute$1
                    public final void accept(int i) {
                        Log.d("TileServiceRequestController", "Response: " + i);
                    }

                    @Override // java.util.function.Consumer
                    public /* bridge */ /* synthetic */ void accept(Object obj) {
                        accept(((Number) obj).intValue());
                    }
                });
                return;
            }
            String str = list.get(0);
            Log.w("TileServiceRequestController", "Malformed componentName " + ((Object) str));
        }
    }

    /* JADX WARN: Type inference failed for: r1v5, types: [com.android.systemui.qs.external.TileServiceRequestController$commandQueueCallback$1] */
    public TileServiceRequestController(QSTileHost qSTileHost, CommandQueue commandQueue, CommandRegistry commandRegistry, TileRequestDialogEventLogger tileRequestDialogEventLogger, Function0<TileRequestDialog> function0) {
        this.qsTileHost = qSTileHost;
        this.commandQueue = commandQueue;
        this.commandRegistry = commandRegistry;
        this.eventLogger = tileRequestDialogEventLogger;
        this.dialogCreator = function0;
        this.commandQueueCallback = new CommandQueue.Callbacks() { // from class: com.android.systemui.qs.external.TileServiceRequestController$commandQueueCallback$1
            public void cancelRequestAddTile(String str) {
                Function1 access$getDialogCanceller$p = TileServiceRequestController.access$getDialogCanceller$p(TileServiceRequestController.this);
                if (access$getDialogCanceller$p != null) {
                    access$getDialogCanceller$p.invoke(str);
                }
            }

            public void requestAddTile(ComponentName componentName, CharSequence charSequence, CharSequence charSequence2, Icon icon, final IAddTileResultCallback iAddTileResultCallback) {
                TileServiceRequestController.this.requestTileAdd$frameworks__base__packages__SystemUI__android_common__SystemUI_core(componentName, charSequence, charSequence2, icon, new Consumer() { // from class: com.android.systemui.qs.external.TileServiceRequestController$commandQueueCallback$1$requestAddTile$1
                    public final void accept(int i) {
                        try {
                            iAddTileResultCallback.onTileRequest(i);
                        } catch (RemoteException e) {
                            Log.e("TileServiceRequestController", "Couldn't respond to request", e);
                        }
                    }

                    @Override // java.util.function.Consumer
                    public /* bridge */ /* synthetic */ void accept(Object obj) {
                        accept(((Number) obj).intValue());
                    }
                });
            }
        };
    }

    public /* synthetic */ TileServiceRequestController(final QSTileHost qSTileHost, CommandQueue commandQueue, CommandRegistry commandRegistry, TileRequestDialogEventLogger tileRequestDialogEventLogger, Function0<TileRequestDialog> function0, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(qSTileHost, commandQueue, commandRegistry, tileRequestDialogEventLogger, (i & 16) != 0 ? new Function0<TileRequestDialog>() { // from class: com.android.systemui.qs.external.TileServiceRequestController.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            /* renamed from: invoke */
            public final TileRequestDialog m3908invoke() {
                return new TileRequestDialog(qSTileHost.getContext());
            }
        } : function0);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.external.TileServiceRequestController$requestTileAdd$dialogResponse$1.accept(int):void] */
    public static final /* synthetic */ void access$addTile(TileServiceRequestController tileServiceRequestController, ComponentName componentName) {
        tileServiceRequestController.addTile(componentName);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.external.TileServiceRequestController$commandQueueCallback$1.cancelRequestAddTile(java.lang.String):void] */
    public static final /* synthetic */ Function1 access$getDialogCanceller$p(TileServiceRequestController tileServiceRequestController) {
        return tileServiceRequestController.dialogCanceller;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.external.TileServiceRequestController$requestTileAdd$dialogResponse$1.accept(int):void] */
    public static final /* synthetic */ TileRequestDialogEventLogger access$getEventLogger$p(TileServiceRequestController tileServiceRequestController) {
        return tileServiceRequestController.eventLogger;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.external.TileServiceRequestController$requestTileAdd$1$1.invoke(java.lang.String):void, com.android.systemui.qs.external.TileServiceRequestController$requestTileAdd$dialogResponse$1.accept(int):void] */
    public static final /* synthetic */ void access$setDialogCanceller$p(TileServiceRequestController tileServiceRequestController, Function1 function1) {
        tileServiceRequestController.dialogCanceller = function1;
    }

    public final void addTile(ComponentName componentName) {
        this.qsTileHost.addTile(componentName, true);
    }

    /* JADX WARN: Type inference failed for: r0v5, types: [android.app.AlertDialog, com.android.systemui.statusbar.phone.SystemUIDialog, com.android.systemui.qs.external.TileRequestDialog] */
    public final SystemUIDialog createDialog(TileRequestDialog.TileData tileData, final SingleShotConsumer<Integer> singleShotConsumer) {
        DialogInterface.OnClickListener onClickListener = new DialogInterface.OnClickListener() { // from class: com.android.systemui.qs.external.TileServiceRequestController$createDialog$dialogClickListener$1
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                if (i == -1) {
                    singleShotConsumer.accept(2);
                } else {
                    singleShotConsumer.accept(0);
                }
            }
        };
        Object invoke = this.dialogCreator.invoke();
        ?? r0 = (TileRequestDialog) invoke;
        r0.setTileData(tileData);
        r0.setShowForAllUsers(true);
        r0.setCanceledOnTouchOutside(true);
        r0.setOnCancelListener(new DialogInterface.OnCancelListener() { // from class: com.android.systemui.qs.external.TileServiceRequestController$createDialog$1$1
            @Override // android.content.DialogInterface.OnCancelListener
            public final void onCancel(DialogInterface dialogInterface) {
                singleShotConsumer.accept(3);
            }
        });
        r0.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.android.systemui.qs.external.TileServiceRequestController$createDialog$1$2
            @Override // android.content.DialogInterface.OnDismissListener
            public final void onDismiss(DialogInterface dialogInterface) {
                singleShotConsumer.accept(3);
            }
        });
        r0.setPositiveButton(R$string.qs_tile_request_dialog_add, onClickListener);
        r0.setNegativeButton(R$string.qs_tile_request_dialog_not_add, onClickListener);
        return (SystemUIDialog) invoke;
    }

    public final void init() {
        this.commandRegistry.registerCommand("tile-service-add", new Function0<Command>() { // from class: com.android.systemui.qs.external.TileServiceRequestController$init$1
            {
                super(0);
            }

            /* JADX DEBUG: Method merged with bridge method */
            /* renamed from: invoke */
            public final Command m3910invoke() {
                return new TileServiceRequestController.TileServiceRequestCommand();
            }
        });
        this.commandQueue.addCallback(this.commandQueueCallback);
    }

    public final boolean isTileAlreadyAdded(ComponentName componentName) {
        return this.qsTileHost.indexOf(CustomTile.toSpec(componentName)) != -1;
    }

    public final void requestTileAdd$frameworks__base__packages__SystemUI__android_common__SystemUI_core(final ComponentName componentName, CharSequence charSequence, CharSequence charSequence2, Icon icon, final Consumer<Integer> consumer) {
        final InstanceId newInstanceId = this.eventLogger.newInstanceId();
        final String packageName = componentName.getPackageName();
        if (isTileAlreadyAdded(componentName)) {
            consumer.accept(1);
            this.eventLogger.logTileAlreadyAdded(packageName, newInstanceId);
            return;
        }
        final SystemUIDialog createDialog = createDialog(new TileRequestDialog.TileData(charSequence, charSequence2, icon), new SingleShotConsumer<>(new Consumer() { // from class: com.android.systemui.qs.external.TileServiceRequestController$requestTileAdd$dialogResponse$1
            public final void accept(int i) {
                if (i == 2) {
                    TileServiceRequestController.access$addTile(TileServiceRequestController.this, componentName);
                }
                TileServiceRequestController.access$setDialogCanceller$p(TileServiceRequestController.this, null);
                TileServiceRequestController.access$getEventLogger$p(TileServiceRequestController.this).logUserResponse(i, packageName, newInstanceId);
                consumer.accept(Integer.valueOf(i));
            }

            @Override // java.util.function.Consumer
            public /* bridge */ /* synthetic */ void accept(Object obj) {
                accept(((Number) obj).intValue());
            }
        }));
        this.dialogCanceller = new Function1<String, Unit>() { // from class: com.android.systemui.qs.external.TileServiceRequestController$requestTileAdd$1$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            public /* bridge */ /* synthetic */ Object invoke(Object obj) {
                invoke((String) obj);
                return Unit.INSTANCE;
            }

            public final void invoke(String str) {
                if (Intrinsics.areEqual(packageName, str)) {
                    createDialog.cancel();
                }
                TileServiceRequestController.access$setDialogCanceller$p(this, null);
            }
        };
        createDialog.show();
        this.eventLogger.logDialogShown(packageName, newInstanceId);
    }
}