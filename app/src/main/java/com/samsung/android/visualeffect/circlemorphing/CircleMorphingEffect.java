package com.samsung.android.visualeffect.circlemorphing;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;
import com.samsung.android.visualeffect.EffectCmdType;
import com.samsung.android.visualeffect.circlemorphing.morphing.Morphing;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes10.dex */
public class CircleMorphingEffect extends View {
    public static final long ANIMATION_DURATION = 1000;
    protected final String TAG;
    private final String VERSION;
    private ValueAnimator absorbAnimator;
    private boolean drawCircle;
    private ArrayList<Morphing> list;
    private Paint paint;

    public CircleMorphingEffect(Context context) {
        super(context);
        this.VERSION = "1.3";
        this.TAG = "visualeffectCircleMorphingEffect";
        this.drawCircle = false;
        this.list = new ArrayList<>();
        Log.d("visualeffectCircleMorphingEffect", "CircleMorphingEffect Contructor : Version = 1.3");
        setAnimator();
    }

    private void setAnimator() {
        this.absorbAnimator = ValueAnimator.ofFloat(0.0f, 1.0f);
        this.absorbAnimator.setDuration(1000L);
        this.absorbAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.samsung.android.visualeffect.CircleMorphing.CircleMorphingEffect.1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator animation) {
                CircleMorphingEffect.this.updateAbsorbAnimator();
            }
        });
    }

    public void drawCircle(Paint circlePaint) {
        Log.d("visualeffectCircleMorphingEffect", "drawCircle");
        this.drawCircle = true;
        this.paint = circlePaint;
    }

    public void addMorph(Morphing morph) {
        this.list.add(morph);
        morph.setListener(new Morphing.StatusChangedListener() { // from class: com.samsung.android.visualeffect.CircleMorphing.CircleMorphingEffect.2
            @Override // com.samsung.android.visualeffect.CircleMorphing.morphing.Morphing.StatusChangedListener
            public void onSeparated() {
                CircleMorphingEffect.this.startAbsorbAnimation();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startAbsorbAnimation() {
        this.absorbAnimator.cancel();
        this.absorbAnimator.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateAbsorbAnimator() {
        draw();
    }

    public void removeMorph(Morphing morph) {
        Log.d("visualeffectCircleMorphingEffect", "removeMorph");
        morph.clear();
        this.list.remove(morph);
    }

    public void removeAllMorph() {
        Log.d("visualeffectCircleMorphingEffect", "removeAllMorph");
        clear();
        this.list.clear();
    }

    public void clear() {
        Log.d("visualeffectCircleMorphingEffect", EffectCmdType.CLEAR);
        this.absorbAnimator.cancel();
        Iterator<Morphing> it = this.list.iterator();
        while (it.hasNext()) {
            Morphing morph = it.next();
            morph.clear();
        }
        draw();
    }

    public void draw() {
        invalidate();
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (Morphing morph : this.list) {
            if (this.drawCircle && this.paint != null) {
                if (morph.showCircle1) {
                    canvas.drawCircle(morph.x1, morph.y1, morph.radius1, this.paint);
                }
                if (morph.showCircle2) {
                    canvas.drawCircle(morph.x2, morph.y2, morph.radius2, this.paint);
                }
            }
            if (morph.showCircle1 && morph.showCircle2) {
                morph.drawMorph(canvas);
            } else {
                morph.clear();
            }
            draw();
        }
    }
}