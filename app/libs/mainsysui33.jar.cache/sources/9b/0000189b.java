package com.android.systemui.keyguard;

import android.content.res.ColorStateList;
import android.os.SystemClock;
import android.text.TextUtils;
import com.android.systemui.Dumpable;
import com.android.systemui.keyguard.KeyguardIndication;
import com.android.systemui.keyguard.KeyguardIndicationRotateTextViewController;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.phone.KeyguardIndicationTextView;
import com.android.systemui.util.ViewController;
import com.android.systemui.util.concurrency.DelayableExecutor;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/KeyguardIndicationRotateTextViewController.class */
public class KeyguardIndicationRotateTextViewController extends ViewController<KeyguardIndicationTextView> implements Dumpable {
    public int mCurrIndicationType;
    public CharSequence mCurrMessage;
    public final DelayableExecutor mExecutor;
    public final Map<Integer, KeyguardIndication> mIndicationMessages;
    public final List<Integer> mIndicationQueue;
    public final ColorStateList mInitialTextColorState;
    public boolean mIsDozing;
    public long mLastIndicationSwitch;
    public final float mMaxAlpha;
    public ShowNextIndication mShowNextIndicationRunnable;
    public final StatusBarStateController mStatusBarStateController;
    public StatusBarStateController.StateListener mStatusBarStateListener;

    /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/KeyguardIndicationRotateTextViewController$ShowNextIndication.class */
    public class ShowNextIndication {
        public Runnable mCancelDelayedRunnable;
        public final Runnable mShowIndicationRunnable;

        /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.keyguard.KeyguardIndicationRotateTextViewController$ShowNextIndication$$ExternalSyntheticLambda0.run():void] */
        /* renamed from: $r8$lambda$LhvHSXKxjwPKlxaHUB-tDLbvRaM */
        public static /* synthetic */ void m2810$r8$lambda$LhvHSXKxjwPKlxaHUBtDLbvRaM(ShowNextIndication showNextIndication) {
            showNextIndication.lambda$new$0();
        }

        public ShowNextIndication(long j) {
            KeyguardIndicationRotateTextViewController.this = r7;
            Runnable runnable = new Runnable() { // from class: com.android.systemui.keyguard.KeyguardIndicationRotateTextViewController$ShowNextIndication$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    KeyguardIndicationRotateTextViewController.ShowNextIndication.m2810$r8$lambda$LhvHSXKxjwPKlxaHUBtDLbvRaM(KeyguardIndicationRotateTextViewController.ShowNextIndication.this);
                }
            };
            this.mShowIndicationRunnable = runnable;
            this.mCancelDelayedRunnable = r7.mExecutor.executeDelayed(runnable, j);
        }

        public /* synthetic */ void lambda$new$0() {
            KeyguardIndicationRotateTextViewController.this.showIndication(KeyguardIndicationRotateTextViewController.this.mIndicationQueue.size() == 0 ? -1 : ((Integer) KeyguardIndicationRotateTextViewController.this.mIndicationQueue.get(0)).intValue());
        }

        public void cancelDelayedExecution() {
            Runnable runnable = this.mCancelDelayedRunnable;
            if (runnable != null) {
                runnable.run();
                this.mCancelDelayedRunnable = null;
            }
        }

        public void runImmediately() {
            cancelDelayedExecution();
            this.mShowIndicationRunnable.run();
        }
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.keyguard.KeyguardIndicationRotateTextViewController$$ExternalSyntheticLambda0.test(java.lang.Object):boolean] */
    /* renamed from: $r8$lambda$6Y9Oy47QcJ-cTN489A6Srsk1N_Y */
    public static /* synthetic */ boolean m2801$r8$lambda$6Y9Oy47QcJcTN489A6Srsk1N_Y(int i, Integer num) {
        return lambda$updateIndication$0(i, num);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.keyguard.KeyguardIndicationRotateTextViewController$$ExternalSyntheticLambda1.test(java.lang.Object):boolean] */
    public static /* synthetic */ boolean $r8$lambda$EM89DmuwGo7Ve1xWwt1SseGo4CQ(int i, Integer num) {
        return lambda$updateIndication$1(i, num);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.keyguard.KeyguardIndicationRotateTextViewController$$ExternalSyntheticLambda2.test(java.lang.Object):boolean] */
    /* renamed from: $r8$lambda$i1x6qWtL39dnpTsXYK2-gz02S5s */
    public static /* synthetic */ boolean m2802$r8$lambda$i1x6qWtL39dnpTsXYK2gz02S5s(int i, Integer num) {
        return lambda$showIndication$2(i, num);
    }

    public KeyguardIndicationRotateTextViewController(KeyguardIndicationTextView keyguardIndicationTextView, DelayableExecutor delayableExecutor, StatusBarStateController statusBarStateController) {
        super(keyguardIndicationTextView);
        this.mIndicationMessages = new HashMap();
        this.mIndicationQueue = new LinkedList();
        this.mCurrIndicationType = -1;
        this.mStatusBarStateListener = new StatusBarStateController.StateListener() { // from class: com.android.systemui.keyguard.KeyguardIndicationRotateTextViewController.1
            {
                KeyguardIndicationRotateTextViewController.this = this;
            }

            @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
            public void onDozeAmountChanged(float f, float f2) {
                ((ViewController) KeyguardIndicationRotateTextViewController.this).mView.setAlpha((1.0f - f) * KeyguardIndicationRotateTextViewController.this.mMaxAlpha);
            }

            @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
            public void onDozingChanged(boolean z) {
                if (z == KeyguardIndicationRotateTextViewController.this.mIsDozing) {
                    return;
                }
                KeyguardIndicationRotateTextViewController.this.mIsDozing = z;
                if (KeyguardIndicationRotateTextViewController.this.mIsDozing) {
                    KeyguardIndicationRotateTextViewController.this.showIndication(-1);
                } else if (KeyguardIndicationRotateTextViewController.this.mIndicationQueue.size() > 0) {
                    KeyguardIndicationRotateTextViewController keyguardIndicationRotateTextViewController = KeyguardIndicationRotateTextViewController.this;
                    keyguardIndicationRotateTextViewController.showIndication(((Integer) keyguardIndicationRotateTextViewController.mIndicationQueue.get(0)).intValue());
                }
            }
        };
        this.mMaxAlpha = keyguardIndicationTextView.getAlpha();
        this.mExecutor = delayableExecutor;
        KeyguardIndicationTextView keyguardIndicationTextView2 = ((ViewController) this).mView;
        this.mInitialTextColorState = keyguardIndicationTextView2 != null ? keyguardIndicationTextView2.getTextColors() : ColorStateList.valueOf(-1);
        this.mStatusBarStateController = statusBarStateController;
        init();
    }

    public static /* synthetic */ boolean lambda$showIndication$2(int i, Integer num) {
        return num.intValue() == i;
    }

    public static /* synthetic */ boolean lambda$updateIndication$0(int i, Integer num) {
        return num.intValue() == i;
    }

    public static /* synthetic */ boolean lambda$updateIndication$1(int i, Integer num) {
        return num.intValue() == i;
    }

    public final void cancelScheduledIndication() {
        ShowNextIndication showNextIndication = this.mShowNextIndicationRunnable;
        if (showNextIndication != null) {
            showNextIndication.cancelDelayedExecution();
            this.mShowNextIndicationRunnable = null;
        }
    }

    public void clearMessages() {
        this.mCurrIndicationType = -1;
        this.mIndicationQueue.clear();
        this.mIndicationMessages.clear();
        ((ViewController) this).mView.clearMessages();
    }

    @Override // com.android.systemui.Dumpable
    public void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println("KeyguardIndicationRotatingTextViewController:");
        printWriter.println("    currentMessage=" + ((Object) ((ViewController) this).mView.getText()));
        printWriter.println("    dozing:" + this.mIsDozing);
        printWriter.println("    queue:" + this.mIndicationQueue.toString());
        printWriter.println("    showNextIndicationRunnable:" + this.mShowNextIndicationRunnable);
        if (hasIndications()) {
            printWriter.println("    All messages:");
            for (Integer num : this.mIndicationMessages.keySet()) {
                int intValue = num.intValue();
                printWriter.println("        type=" + intValue + " " + this.mIndicationMessages.get(Integer.valueOf(intValue)));
            }
        }
    }

    public final long getMinVisibilityMillis(KeyguardIndication keyguardIndication) {
        if (keyguardIndication == null || keyguardIndication.getMinVisibilityMillis() == null) {
            return 0L;
        }
        return keyguardIndication.getMinVisibilityMillis().longValue();
    }

    public boolean hasIndications() {
        return this.mIndicationMessages.keySet().size() > 0;
    }

    public void hideIndication(int i) {
        if (!this.mIndicationMessages.containsKey(Integer.valueOf(i)) || TextUtils.isEmpty(this.mIndicationMessages.get(Integer.valueOf(i)).getMessage())) {
            return;
        }
        updateIndication(i, null, true);
    }

    public void hideTransient() {
        hideIndication(5);
    }

    public boolean isNextIndicationScheduled() {
        return this.mShowNextIndicationRunnable != null;
    }

    public void onViewAttached() {
        this.mStatusBarStateController.addCallback(this.mStatusBarStateListener);
    }

    public void onViewDetached() {
        this.mStatusBarStateController.removeCallback(this.mStatusBarStateListener);
        cancelScheduledIndication();
    }

    public final void scheduleShowNextIndication(long j) {
        cancelScheduledIndication();
        this.mShowNextIndicationRunnable = new ShowNextIndication(j);
    }

    public final void showIndication(final int i) {
        cancelScheduledIndication();
        CharSequence charSequence = this.mCurrMessage;
        int i2 = this.mCurrIndicationType;
        this.mCurrIndicationType = i;
        this.mCurrMessage = this.mIndicationMessages.get(Integer.valueOf(i)) != null ? this.mIndicationMessages.get(Integer.valueOf(i)).getMessage() : null;
        this.mIndicationQueue.removeIf(new Predicate() { // from class: com.android.systemui.keyguard.KeyguardIndicationRotateTextViewController$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return KeyguardIndicationRotateTextViewController.m2802$r8$lambda$i1x6qWtL39dnpTsXYK2gz02S5s(i, (Integer) obj);
            }
        });
        if (this.mCurrIndicationType != -1) {
            this.mIndicationQueue.add(Integer.valueOf(i));
        }
        this.mLastIndicationSwitch = SystemClock.uptimeMillis();
        if (!TextUtils.equals(charSequence, this.mCurrMessage) || i2 != this.mCurrIndicationType) {
            ((ViewController) this).mView.switchIndication(this.mIndicationMessages.get(Integer.valueOf(i)));
        }
        if (this.mCurrIndicationType == -1 || this.mIndicationQueue.size() <= 1) {
            return;
        }
        scheduleShowNextIndication(Math.max(getMinVisibilityMillis(this.mIndicationMessages.get(Integer.valueOf(i))), 3500L));
    }

    public void showTransient(CharSequence charSequence) {
        updateIndication(5, new KeyguardIndication.Builder().setMessage(charSequence).setMinVisibilityMillis(2600L).setTextColor(this.mInitialTextColorState).build(), true);
    }

    public void updateIndication(final int i, KeyguardIndication keyguardIndication, boolean z) {
        if (i == 10) {
            return;
        }
        long minVisibilityMillis = getMinVisibilityMillis(this.mIndicationMessages.get(Integer.valueOf(this.mCurrIndicationType)));
        boolean z2 = true;
        boolean z3 = (keyguardIndication == null || TextUtils.isEmpty(keyguardIndication.getMessage())) ? false : true;
        if (z3) {
            if (!this.mIndicationQueue.contains(Integer.valueOf(i))) {
                this.mIndicationQueue.add(Integer.valueOf(i));
            }
            this.mIndicationMessages.put(Integer.valueOf(i), keyguardIndication);
        } else {
            this.mIndicationMessages.remove(Integer.valueOf(i));
            this.mIndicationQueue.removeIf(new Predicate() { // from class: com.android.systemui.keyguard.KeyguardIndicationRotateTextViewController$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return KeyguardIndicationRotateTextViewController.m2801$r8$lambda$6Y9Oy47QcJcTN489A6Srsk1N_Y(i, (Integer) obj);
                }
            });
        }
        if (this.mIsDozing) {
            return;
        }
        long uptimeMillis = SystemClock.uptimeMillis() - this.mLastIndicationSwitch;
        if (uptimeMillis < minVisibilityMillis) {
            z2 = false;
        }
        if (!z3) {
            if (this.mCurrIndicationType == i && !z3 && z) {
                if (!z2) {
                    scheduleShowNextIndication(minVisibilityMillis - uptimeMillis);
                    return;
                }
                ShowNextIndication showNextIndication = this.mShowNextIndicationRunnable;
                if (showNextIndication != null) {
                    showNextIndication.runImmediately();
                    return;
                } else {
                    showIndication(-1);
                    return;
                }
            }
            return;
        }
        int i2 = this.mCurrIndicationType;
        if (i2 == -1 || i2 == i) {
            showIndication(i);
        } else if (z) {
            if (z2) {
                showIndication(i);
                return;
            }
            this.mIndicationQueue.removeIf(new Predicate() { // from class: com.android.systemui.keyguard.KeyguardIndicationRotateTextViewController$$ExternalSyntheticLambda1
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return KeyguardIndicationRotateTextViewController.$r8$lambda$EM89DmuwGo7Ve1xWwt1SseGo4CQ(i, (Integer) obj);
                }
            });
            this.mIndicationQueue.add(0, Integer.valueOf(i));
            scheduleShowNextIndication(minVisibilityMillis - uptimeMillis);
        } else if (isNextIndicationScheduled()) {
        } else {
            long max = Math.max(getMinVisibilityMillis(this.mIndicationMessages.get(Integer.valueOf(i))), 3500L);
            if (uptimeMillis >= max) {
                showIndication(i);
            } else {
                scheduleShowNextIndication(max - uptimeMillis);
            }
        }
    }
}