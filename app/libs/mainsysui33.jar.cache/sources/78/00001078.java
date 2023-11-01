package com.android.systemui.accessibility.floatingmenu;

import android.text.Annotation;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ClickableSpan;
import android.view.View;
import com.android.systemui.accessibility.floatingmenu.AnnotationLinkSpan;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/* loaded from: mainsysui33.jar:com/android/systemui/accessibility/floatingmenu/AnnotationLinkSpan.class */
public class AnnotationLinkSpan extends ClickableSpan {
    public final Optional<View.OnClickListener> mClickListener;

    /* loaded from: mainsysui33.jar:com/android/systemui/accessibility/floatingmenu/AnnotationLinkSpan$LinkInfo.class */
    public static class LinkInfo {
        public final Optional<String> mAnnotation;
        public final Optional<View.OnClickListener> mListener;

        /* JADX DEBUG: Marked for inline */
        /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.accessibility.floatingmenu.AnnotationLinkSpan$$ExternalSyntheticLambda2.apply(java.lang.Object):java.lang.Object] */
        /* renamed from: -$$Nest$fgetmListener  reason: not valid java name */
        public static /* bridge */ /* synthetic */ Optional m1415$$Nest$fgetmListener(LinkInfo linkInfo) {
            return linkInfo.mListener;
        }

        public LinkInfo(String str, View.OnClickListener onClickListener) {
            this.mAnnotation = Optional.of(str);
            this.mListener = Optional.ofNullable(onClickListener);
        }
    }

    public AnnotationLinkSpan(View.OnClickListener onClickListener) {
        this.mClickListener = Optional.ofNullable(onClickListener);
    }

    public static /* synthetic */ boolean lambda$linkify$1(String str, LinkInfo linkInfo) {
        return linkInfo.mAnnotation.isPresent() && ((String) linkInfo.mAnnotation.get()).equals(str);
    }

    public static /* synthetic */ void lambda$linkify$3(SpannableStringBuilder spannableStringBuilder, SpannableString spannableString, Annotation annotation, View.OnClickListener onClickListener) {
        AnnotationLinkSpan annotationLinkSpan = new AnnotationLinkSpan(onClickListener);
        spannableStringBuilder.setSpan(annotationLinkSpan, spannableString.getSpanStart(annotation), spannableString.getSpanEnd(annotation), spannableString.getSpanFlags(annotationLinkSpan));
    }

    public static /* synthetic */ void lambda$linkify$4(LinkInfo[] linkInfoArr, final SpannableStringBuilder spannableStringBuilder, final SpannableString spannableString, final Annotation annotation) {
        final String value = annotation.getValue();
        Arrays.asList(linkInfoArr).stream().filter(new Predicate() { // from class: com.android.systemui.accessibility.floatingmenu.AnnotationLinkSpan$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                boolean lambda$linkify$1;
                lambda$linkify$1 = AnnotationLinkSpan.lambda$linkify$1(value, (AnnotationLinkSpan.LinkInfo) obj);
                return lambda$linkify$1;
            }
        }).findFirst().flatMap(new Function() { // from class: com.android.systemui.accessibility.floatingmenu.AnnotationLinkSpan$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                Optional m1415$$Nest$fgetmListener;
                m1415$$Nest$fgetmListener = AnnotationLinkSpan.LinkInfo.m1415$$Nest$fgetmListener((AnnotationLinkSpan.LinkInfo) obj);
                return m1415$$Nest$fgetmListener;
            }
        }).ifPresent(new Consumer() { // from class: com.android.systemui.accessibility.floatingmenu.AnnotationLinkSpan$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                AnnotationLinkSpan.lambda$linkify$3(spannableStringBuilder, spannableString, annotation, (View.OnClickListener) obj);
            }
        });
    }

    public static CharSequence linkify(CharSequence charSequence, final LinkInfo... linkInfoArr) {
        final SpannableString spannableString = new SpannableString(charSequence);
        Annotation[] annotationArr = (Annotation[]) spannableString.getSpans(0, spannableString.length(), Annotation.class);
        final SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(spannableString);
        Arrays.asList(annotationArr).forEach(new Consumer() { // from class: com.android.systemui.accessibility.floatingmenu.AnnotationLinkSpan$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                AnnotationLinkSpan.lambda$linkify$4(linkInfoArr, spannableStringBuilder, spannableString, (Annotation) obj);
            }
        });
        return spannableStringBuilder;
    }

    @Override // android.text.style.ClickableSpan
    public void onClick(final View view) {
        this.mClickListener.ifPresent(new Consumer() { // from class: com.android.systemui.accessibility.floatingmenu.AnnotationLinkSpan$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((View.OnClickListener) obj).onClick(view);
            }
        });
    }
}