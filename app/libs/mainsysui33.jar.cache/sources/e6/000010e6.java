package com.android.systemui.animation;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.fonts.Font;
import android.graphics.text.PositionedGlyphs;
import android.text.Layout;
import android.text.TextPaint;
import android.text.TextShaper;
import android.util.MathUtils;
import com.android.internal.graphics.ColorUtils;
import com.android.settingslib.widget.ActionBarShadowController;
import com.android.systemui.animation.TextAnimator;
import com.android.systemui.animation.TextInterpolator;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: mainsysui33.jar:com/android/systemui/animation/TextInterpolator.class */
public final class TextInterpolator {
    public final TextPaint basePaint;
    public Function2<? super TextAnimator.PositionedGlyph, ? super Float, Unit> glyphFilter;
    public Layout layout;
    public float progress;
    public final TextPaint targetPaint;
    public List<Line> lines = CollectionsKt__CollectionsKt.emptyList();
    public final FontInterpolator fontInterpolator = new FontInterpolator();
    public final TextPaint tmpPaint = new TextPaint();
    public final Lazy tmpPaintForGlyph$delegate = LazyKt__LazyJVMKt.lazy(new Function0<TextPaint>() { // from class: com.android.systemui.animation.TextInterpolator$tmpPaintForGlyph$2
        /* JADX DEBUG: Method merged with bridge method */
        /* renamed from: invoke */
        public final TextPaint m1452invoke() {
            return new TextPaint();
        }
    });
    public final Lazy tmpGlyph$delegate = LazyKt__LazyJVMKt.lazy(new Function0<MutablePositionedGlyph>() { // from class: com.android.systemui.animation.TextInterpolator$tmpGlyph$2
        /* JADX DEBUG: Method merged with bridge method */
        /* renamed from: invoke */
        public final TextInterpolator.MutablePositionedGlyph m1450invoke() {
            return new TextInterpolator.MutablePositionedGlyph();
        }
    });
    public float[] tmpPositionArray = new float[20];

    /* loaded from: mainsysui33.jar:com/android/systemui/animation/TextInterpolator$FontRun.class */
    public static final class FontRun {
        public Font baseFont;
        public final int end;
        public final int start;
        public Font targetFont;

        public FontRun(int i, int i2, Font font, Font font2) {
            this.start = i;
            this.end = i2;
            this.baseFont = font;
            this.targetFont = font2;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj instanceof FontRun) {
                FontRun fontRun = (FontRun) obj;
                return this.start == fontRun.start && this.end == fontRun.end && Intrinsics.areEqual(this.baseFont, fontRun.baseFont) && Intrinsics.areEqual(this.targetFont, fontRun.targetFont);
            }
            return false;
        }

        public final Font getBaseFont() {
            return this.baseFont;
        }

        public final int getEnd() {
            return this.end;
        }

        public final int getLength() {
            return this.end - this.start;
        }

        public final int getStart() {
            return this.start;
        }

        public final Font getTargetFont() {
            return this.targetFont;
        }

        public int hashCode() {
            return (((((Integer.hashCode(this.start) * 31) + Integer.hashCode(this.end)) * 31) + this.baseFont.hashCode()) * 31) + this.targetFont.hashCode();
        }

        public final void setBaseFont(Font font) {
            this.baseFont = font;
        }

        public final void setTargetFont(Font font) {
            this.targetFont = font;
        }

        public String toString() {
            int i = this.start;
            int i2 = this.end;
            Font font = this.baseFont;
            Font font2 = this.targetFont;
            return "FontRun(start=" + i + ", end=" + i2 + ", baseFont=" + font + ", targetFont=" + font2 + ")";
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/animation/TextInterpolator$Line.class */
    public static final class Line {
        public final List<Run> runs;

        public Line(List<Run> list) {
            this.runs = list;
        }

        public final List<Run> getRuns() {
            return this.runs;
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/animation/TextInterpolator$MutablePositionedGlyph.class */
    public static final class MutablePositionedGlyph extends TextAnimator.PositionedGlyph {
        public Font font;
        public int glyphId;
        public int glyphIndex;
        public int runLength;
        public int runStart;

        public MutablePositionedGlyph() {
            super(null);
        }

        @Override // com.android.systemui.animation.TextAnimator.PositionedGlyph
        public int getGlyphIndex() {
            return this.glyphIndex;
        }

        public void setFont(Font font) {
            this.font = font;
        }

        public void setGlyphId(int i) {
            this.glyphId = i;
        }

        public void setGlyphIndex(int i) {
            this.glyphIndex = i;
        }

        public void setRunLength(int i) {
            this.runLength = i;
        }

        public void setRunStart(int i) {
            this.runStart = i;
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/animation/TextInterpolator$Run.class */
    public static final class Run {
        public final float[] baseX;
        public final float[] baseY;
        public final List<FontRun> fontRuns;
        public final int[] glyphIds;
        public final float[] targetX;
        public final float[] targetY;

        public Run(int[] iArr, float[] fArr, float[] fArr2, float[] fArr3, float[] fArr4, List<FontRun> list) {
            this.glyphIds = iArr;
            this.baseX = fArr;
            this.baseY = fArr2;
            this.targetX = fArr3;
            this.targetY = fArr4;
            this.fontRuns = list;
        }

        public final float[] getBaseX() {
            return this.baseX;
        }

        public final float[] getBaseY() {
            return this.baseY;
        }

        public final List<FontRun> getFontRuns() {
            return this.fontRuns;
        }

        public final int[] getGlyphIds() {
            return this.glyphIds;
        }

        public final float[] getTargetX() {
            return this.targetX;
        }

        public final float[] getTargetY() {
            return this.targetY;
        }
    }

    public TextInterpolator(Layout layout) {
        this.basePaint = new TextPaint(layout.getPaint());
        this.targetPaint = new TextPaint(layout.getPaint());
        this.layout = layout;
        shapeText(layout);
    }

    public final void draw(Canvas canvas) {
        float drawOrigin;
        lerp(this.basePaint, this.targetPaint, this.progress, this.tmpPaint);
        int i = 0;
        for (Object obj : this.lines) {
            if (i < 0) {
                CollectionsKt__CollectionsKt.throwIndexOverflow();
            }
            for (Run run : ((Line) obj).getRuns()) {
                canvas.save();
                try {
                    drawOrigin = TextInterpolatorKt.getDrawOrigin(getLayout(), i);
                    canvas.translate(drawOrigin, getLayout().getLineBaseline(i));
                    for (FontRun fontRun : run.getFontRuns()) {
                        drawFontRun(canvas, run, fontRun, i, this.tmpPaint);
                    }
                } finally {
                    canvas.restore();
                }
            }
            i++;
        }
    }

    public final void drawFontRun(Canvas canvas, Run run, FontRun fontRun, int i, Paint paint) {
        int i2;
        Font lerp = this.fontInterpolator.lerp(fontRun.getBaseFont(), fontRun.getTargetFont(), this.progress);
        Function2<? super TextAnimator.PositionedGlyph, ? super Float, Unit> function2 = this.glyphFilter;
        int i3 = 0;
        if (function2 == null) {
            int start = fontRun.getStart();
            int end = fontRun.getEnd();
            while (start < end) {
                int i4 = i3 + 1;
                this.tmpPositionArray[i3] = MathUtils.lerp(run.getBaseX()[start], run.getTargetX()[start], this.progress);
                this.tmpPositionArray[i4] = MathUtils.lerp(run.getBaseY()[start], run.getTargetY()[start], this.progress);
                start++;
                i3 = i4 + 1;
            }
            canvas.drawGlyphs(run.getGlyphIds(), fontRun.getStart(), this.tmpPositionArray, 0, fontRun.getLength(), lerp, paint);
            return;
        }
        getTmpGlyph().setFont(lerp);
        getTmpGlyph().setRunStart(fontRun.getStart());
        getTmpGlyph().setRunLength(fontRun.getEnd() - fontRun.getStart());
        getTmpGlyph().setLineNo(i);
        getTmpPaintForGlyph().set(paint);
        int start2 = fontRun.getStart();
        int start3 = fontRun.getStart();
        int end2 = fontRun.getEnd();
        int i5 = 0;
        while (start3 < end2) {
            getTmpGlyph().setGlyphIndex(start3);
            getTmpGlyph().setGlyphId(run.getGlyphIds()[start3]);
            getTmpGlyph().setX(MathUtils.lerp(run.getBaseX()[start3], run.getTargetX()[start3], this.progress));
            getTmpGlyph().setY(MathUtils.lerp(run.getBaseY()[start3], run.getTargetY()[start3], this.progress));
            getTmpGlyph().setTextSize(paint.getTextSize());
            getTmpGlyph().setColor(paint.getColor());
            function2.invoke(getTmpGlyph(), Float.valueOf(this.progress));
            if (getTmpGlyph().getTextSize() == paint.getTextSize()) {
                i2 = start2;
                if (getTmpGlyph().getColor() == paint.getColor()) {
                    int i6 = i5 + 1;
                    this.tmpPositionArray[i5] = getTmpGlyph().getX();
                    this.tmpPositionArray[i6] = getTmpGlyph().getY();
                    start3++;
                    i5 = i6 + 1;
                    start2 = i2;
                }
            }
            getTmpPaintForGlyph().setTextSize(getTmpGlyph().getTextSize());
            getTmpPaintForGlyph().setColor(getTmpGlyph().getColor());
            canvas.drawGlyphs(run.getGlyphIds(), start2, this.tmpPositionArray, 0, start3 - start2, lerp, getTmpPaintForGlyph());
            i5 = 0;
            i2 = start3;
            int i62 = i5 + 1;
            this.tmpPositionArray[i5] = getTmpGlyph().getX();
            this.tmpPositionArray[i62] = getTmpGlyph().getY();
            start3++;
            i5 = i62 + 1;
            start2 = i2;
        }
        canvas.drawGlyphs(run.getGlyphIds(), start2, this.tmpPositionArray, 0, fontRun.getEnd() - start2, lerp, getTmpPaintForGlyph());
    }

    public final Layout getLayout() {
        return this.layout;
    }

    public final TextPaint getTargetPaint() {
        return this.targetPaint;
    }

    public final MutablePositionedGlyph getTmpGlyph() {
        return (MutablePositionedGlyph) this.tmpGlyph$delegate.getValue();
    }

    public final TextPaint getTmpPaintForGlyph() {
        return (TextPaint) this.tmpPaintForGlyph$delegate.getValue();
    }

    public final void lerp(Paint paint, Paint paint2, float f, Paint paint3) {
        paint3.set(paint);
        paint3.setTextSize(MathUtils.lerp(paint.getTextSize(), paint2.getTextSize(), f));
        paint3.setColor(ColorUtils.blendARGB(paint.getColor(), paint2.getColor(), f));
    }

    public final void onTargetPaintModified() {
        updatePositionsAndFonts(shapeText(getLayout(), this.targetPaint), false);
    }

    public final void rebase() {
        float f = this.progress;
        if (f == ActionBarShadowController.ELEVATION_LOW) {
            return;
        }
        if (f == 1.0f) {
            this.basePaint.set(this.targetPaint);
        } else {
            lerp(this.basePaint, this.targetPaint, f, this.tmpPaint);
            this.basePaint.set(this.tmpPaint);
        }
        for (Line line : this.lines) {
            for (Run run : line.getRuns()) {
                int length = run.getBaseX().length;
                for (int i = 0; i < length; i++) {
                    run.getBaseX()[i] = MathUtils.lerp(run.getBaseX()[i], run.getTargetX()[i], this.progress);
                    run.getBaseY()[i] = MathUtils.lerp(run.getBaseY()[i], run.getTargetY()[i], this.progress);
                }
                for (FontRun fontRun : run.getFontRuns()) {
                    fontRun.setBaseFont(this.fontInterpolator.lerp(fontRun.getBaseFont(), fontRun.getTargetFont(), this.progress));
                }
            }
        }
        this.progress = ActionBarShadowController.ELEVATION_LOW;
    }

    public final void setGlyphFilter(Function2<? super TextAnimator.PositionedGlyph, ? super Float, Unit> function2) {
        this.glyphFilter = function2;
    }

    public final void setLayout(Layout layout) {
        this.layout = layout;
        shapeText(layout);
    }

    public final void setProgress(float f) {
        this.progress = f;
    }

    public final List<List<PositionedGlyphs>> shapeText(Layout layout, TextPaint textPaint) {
        ArrayList arrayList = new ArrayList();
        int lineCount = layout.getLineCount();
        for (int i = 0; i < lineCount; i++) {
            int lineStart = layout.getLineStart(i);
            int lineEnd = layout.getLineEnd(i) - lineStart;
            int i2 = (lineStart + lineEnd) - 1;
            int i3 = lineEnd;
            if (i2 > lineStart) {
                i3 = lineEnd;
                if (i2 < layout.getText().length()) {
                    i3 = lineEnd;
                    if (layout.getText().charAt(i2) == '\n') {
                        i3 = lineEnd - 1;
                    }
                }
            }
            final ArrayList arrayList2 = new ArrayList();
            TextShaper.shapeText(layout.getText(), lineStart, i3, layout.getTextDirectionHeuristic(), textPaint, new TextShaper.GlyphsConsumer() { // from class: com.android.systemui.animation.TextInterpolator$shapeText$3
                @Override // android.text.TextShaper.GlyphsConsumer
                public final void accept(int i4, int i5, PositionedGlyphs positionedGlyphs, TextPaint textPaint2) {
                    arrayList2.add(positionedGlyphs);
                }
            });
            arrayList.add(arrayList2);
        }
        return arrayList;
    }

    public final void shapeText(Layout layout) {
        TextInterpolator textInterpolator = this;
        List<List<PositionedGlyphs>> shapeText = textInterpolator.shapeText(layout, textInterpolator.basePaint);
        List<List<PositionedGlyphs>> shapeText2 = textInterpolator.shapeText(layout, textInterpolator.targetPaint);
        if (!(shapeText.size() == shapeText2.size())) {
            throw new IllegalArgumentException("The new layout result has different line count.".toString());
        }
        List<List<PositionedGlyphs>> list = shapeText;
        Iterator<T> it = list.iterator();
        List<List<PositionedGlyphs>> list2 = shapeText2;
        Iterator<T> it2 = list2.iterator();
        ArrayList arrayList = new ArrayList(Math.min(CollectionsKt__IterablesKt.collectionSizeOrDefault(list, 10), CollectionsKt__IterablesKt.collectionSizeOrDefault(list2, 10)));
        int i = 0;
        while (it.hasNext() && it2.hasNext()) {
            Object next = it.next();
            List list3 = (List) it2.next();
            List list4 = (List) next;
            Iterator it3 = list4.iterator();
            List list5 = list3;
            Iterator it4 = list5.iterator();
            ArrayList arrayList2 = new ArrayList(Math.min(CollectionsKt__IterablesKt.collectionSizeOrDefault(list4, 10), CollectionsKt__IterablesKt.collectionSizeOrDefault(list5, 10)));
            while (it3.hasNext() && it4.hasNext()) {
                Object next2 = it3.next();
                PositionedGlyphs positionedGlyphs = (PositionedGlyphs) it4.next();
                PositionedGlyphs positionedGlyphs2 = (PositionedGlyphs) next2;
                if (!(positionedGlyphs2.glyphCount() == positionedGlyphs.glyphCount())) {
                    throw new IllegalArgumentException(("Inconsistent glyph count at line " + textInterpolator.lines.size()).toString());
                }
                int glyphCount = positionedGlyphs2.glyphCount();
                int[] iArr = new int[glyphCount];
                for (int i2 = 0; i2 < glyphCount; i2++) {
                    int glyphId = positionedGlyphs2.getGlyphId(i2);
                    if (!(glyphId == positionedGlyphs.getGlyphId(i2))) {
                        throw new IllegalArgumentException(("Inconsistent glyph ID at " + i2 + " in line " + textInterpolator.lines.size()).toString());
                    }
                    Unit unit = Unit.INSTANCE;
                    iArr[i2] = glyphId;
                }
                float[] fArr = new float[glyphCount];
                for (int i3 = 0; i3 < glyphCount; i3++) {
                    fArr[i3] = positionedGlyphs2.getGlyphX(i3);
                }
                float[] fArr2 = new float[glyphCount];
                for (int i4 = 0; i4 < glyphCount; i4++) {
                    fArr2[i4] = positionedGlyphs2.getGlyphY(i4);
                }
                float[] fArr3 = new float[glyphCount];
                for (int i5 = 0; i5 < glyphCount; i5++) {
                    fArr3[i5] = positionedGlyphs.getGlyphX(i5);
                }
                float[] fArr4 = new float[glyphCount];
                for (int i6 = 0; i6 < glyphCount; i6++) {
                    fArr4[i6] = positionedGlyphs.getGlyphY(i6);
                }
                ArrayList arrayList3 = new ArrayList();
                if (glyphCount != 0) {
                    Font font = positionedGlyphs2.getFont(0);
                    Font font2 = positionedGlyphs.getFont(0);
                    if (!FontInterpolator.Companion.canInterpolate(font, font2)) {
                        throw new IllegalArgumentException(("Cannot interpolate font at 0 (" + font + " vs " + font2 + ")").toString());
                    }
                    int i7 = i;
                    int i8 = 0;
                    for (int i9 = 1; i9 < glyphCount; i9++) {
                        Font font3 = positionedGlyphs2.getFont(i9);
                        Font font4 = positionedGlyphs.getFont(i9);
                        if (font != font3) {
                            if (!(font2 != font4)) {
                                throw new IllegalArgumentException(("Base font has changed at " + i9 + " but target font has not changed.").toString());
                            }
                            arrayList3.add(new FontRun(i8, i9, font, font2));
                            i7 = Math.max(i7, i9 - i8);
                            if (!FontInterpolator.Companion.canInterpolate(font3, font4)) {
                                throw new IllegalArgumentException(("Cannot interpolate font at " + i9 + " (" + font3 + " vs " + font4 + ")").toString());
                            }
                            font2 = font4;
                            i8 = i9;
                            font = font3;
                        } else {
                            if (!(font2 == font4)) {
                                throw new IllegalArgumentException(("Base font has not changed at " + i9 + " but target font has changed.").toString());
                            }
                        }
                    }
                    arrayList3.add(new FontRun(i8, glyphCount, font, font2));
                    i = Math.max(i7, glyphCount - i8);
                }
                arrayList2.add(new Run(iArr, fArr, fArr2, fArr3, fArr4, arrayList3));
                textInterpolator = this;
            }
            arrayList.add(new Line(arrayList2));
        }
        textInterpolator.lines = arrayList;
        int i10 = i * 2;
        if (textInterpolator.tmpPositionArray.length < i10) {
            textInterpolator.tmpPositionArray = new float[i10];
        }
    }

    public final void updatePositionsAndFonts(List<? extends List<PositionedGlyphs>> list, boolean z) {
        if (!(list.size() == this.lines.size())) {
            throw new IllegalStateException("The new layout result has different line count.".toString());
        }
        List<Line> list2 = this.lines;
        Iterator<T> it = list2.iterator();
        List<? extends List<PositionedGlyphs>> list3 = list;
        Iterator<T> it2 = list3.iterator();
        ArrayList arrayList = new ArrayList(Math.min(CollectionsKt__IterablesKt.collectionSizeOrDefault(list2, 10), CollectionsKt__IterablesKt.collectionSizeOrDefault(list3, 10)));
        while (it.hasNext() && it2.hasNext()) {
            Object next = it.next();
            List list4 = (List) it2.next();
            List<Run> runs = ((Line) next).getRuns();
            Iterator<T> it3 = runs.iterator();
            List list5 = list4;
            Iterator it4 = list5.iterator();
            ArrayList arrayList2 = new ArrayList(Math.min(CollectionsKt__IterablesKt.collectionSizeOrDefault(runs, 10), CollectionsKt__IterablesKt.collectionSizeOrDefault(list5, 10)));
            while (it3.hasNext() && it4.hasNext()) {
                Object next2 = it3.next();
                PositionedGlyphs positionedGlyphs = (PositionedGlyphs) it4.next();
                Run run = (Run) next2;
                if (!(positionedGlyphs.glyphCount() == run.getGlyphIds().length)) {
                    throw new IllegalArgumentException("The new layout has different glyph count.".toString());
                }
                for (FontRun fontRun : run.getFontRuns()) {
                    Font font = positionedGlyphs.getFont(fontRun.getStart());
                    int end = fontRun.getEnd();
                    for (int start = fontRun.getStart(); start < end; start++) {
                        if (!(positionedGlyphs.getGlyphId(fontRun.getStart()) == run.getGlyphIds()[fontRun.getStart()])) {
                            throw new IllegalArgumentException(("The new layout has different glyph ID at " + fontRun.getStart()).toString());
                        }
                        if (!(font == positionedGlyphs.getFont(start))) {
                            throw new IllegalArgumentException(("The new layout has different font run. " + font + " vs " + positionedGlyphs.getFont(start) + " at " + start).toString());
                        }
                    }
                    if (!FontInterpolator.Companion.canInterpolate(font, fontRun.getBaseFont())) {
                        throw new IllegalArgumentException(("New font cannot be interpolated with existing font. " + font + ", " + fontRun.getBaseFont()).toString());
                    } else if (z) {
                        fontRun.setBaseFont(font);
                    } else {
                        fontRun.setTargetFont(font);
                    }
                }
                if (z) {
                    int length = run.getBaseX().length;
                    for (int i = 0; i < length; i++) {
                        run.getBaseX()[i] = positionedGlyphs.getGlyphX(i);
                        run.getBaseY()[i] = positionedGlyphs.getGlyphY(i);
                    }
                } else {
                    int length2 = run.getBaseX().length;
                    for (int i2 = 0; i2 < length2; i2++) {
                        run.getTargetX()[i2] = positionedGlyphs.getGlyphX(i2);
                        run.getTargetY()[i2] = positionedGlyphs.getGlyphY(i2);
                    }
                }
                arrayList2.add(Unit.INSTANCE);
            }
            arrayList.add(arrayList2);
        }
    }
}