package com.airbnb.lottie.model.content;

import com.airbnb.lottie.LottieDrawable;
import com.airbnb.lottie.animation.content.Content;
import com.airbnb.lottie.animation.content.ContentGroup;
import com.airbnb.lottie.model.layer.BaseLayer;
import java.util.Arrays;
import java.util.List;

/* loaded from: mainsysui33.jar:com/airbnb/lottie/model/content/ShapeGroup.class */
public class ShapeGroup implements ContentModel {
    public final boolean hidden;
    public final List<ContentModel> items;
    public final String name;

    public ShapeGroup(String str, List<ContentModel> list, boolean z) {
        this.name = str;
        this.items = list;
        this.hidden = z;
    }

    public List<ContentModel> getItems() {
        return this.items;
    }

    public String getName() {
        return this.name;
    }

    public boolean isHidden() {
        return this.hidden;
    }

    @Override // com.airbnb.lottie.model.content.ContentModel
    public Content toContent(LottieDrawable lottieDrawable, BaseLayer baseLayer) {
        return new ContentGroup(lottieDrawable, baseLayer, this);
    }

    public String toString() {
        return "ShapeGroup{name='" + this.name + "' Shapes: " + Arrays.toString(this.items.toArray()) + '}';
    }
}