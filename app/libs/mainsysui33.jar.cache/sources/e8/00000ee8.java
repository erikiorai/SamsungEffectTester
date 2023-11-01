package com.android.settingslib.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Animatable2;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;
import androidx.vectordrawable.graphics.drawable.Animatable2Compat;
import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieListener;
import java.io.FileNotFoundException;
import java.io.InputStream;

/* loaded from: mainsysui33.jar:com/android/settingslib/widget/IllustrationPreference.class */
public class IllustrationPreference extends Preference {
    public final Animatable2.AnimationCallback mAnimationCallback;
    public final Animatable2Compat.AnimationCallback mAnimationCallbackCompat;
    public Drawable mImageDrawable;
    public int mImageResId;
    public Uri mImageUri;
    public boolean mIsAutoScale;
    public int mMaxHeight;
    public View mMiddleGroundView;

    /* JADX DEBUG: Method not inlined, still used in: [com.android.settingslib.widget.IllustrationPreference$$ExternalSyntheticLambda1.onResult(java.lang.Object):void] */
    public static /* synthetic */ void $r8$lambda$5HXvlsscCLhfYvKcdLvnjpfwtbU(int i, FrameLayout frameLayout, Throwable th) {
        lambda$startLottieAnimationWith$1(i, frameLayout, th);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.settingslib.widget.IllustrationPreference$$ExternalSyntheticLambda0.onResult(java.lang.Object):void] */
    public static /* synthetic */ void $r8$lambda$erLFEYMER2agMFjLwc8lDJTVeco(Uri uri, FrameLayout frameLayout, Throwable th) {
        lambda$startLottieAnimationWith$0(uri, frameLayout, th);
    }

    public IllustrationPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mMaxHeight = -1;
        this.mAnimationCallback = new Animatable2.AnimationCallback() { // from class: com.android.settingslib.widget.IllustrationPreference.1
            {
                IllustrationPreference.this = this;
            }

            @Override // android.graphics.drawable.Animatable2.AnimationCallback
            public void onAnimationEnd(Drawable drawable) {
                ((Animatable) drawable).start();
            }
        };
        this.mAnimationCallbackCompat = new Animatable2Compat.AnimationCallback() { // from class: com.android.settingslib.widget.IllustrationPreference.2
            {
                IllustrationPreference.this = this;
            }

            @Override // androidx.vectordrawable.graphics.drawable.Animatable2Compat.AnimationCallback
            public void onAnimationEnd(Drawable drawable) {
                ((Animatable) drawable).start();
            }
        };
        init(context, attributeSet);
    }

    public static InputStream getInputStreamFromUri(Context context, Uri uri) {
        try {
            return context.getContentResolver().openInputStream(uri);
        } catch (FileNotFoundException e) {
            Log.w("IllustrationPreference", "Cannot find content uri: " + uri, e);
            return null;
        }
    }

    public static /* synthetic */ void lambda$startLottieAnimationWith$0(Uri uri, FrameLayout frameLayout, Throwable th) {
        Log.w("IllustrationPreference", "Invalid illustration image uri: " + uri, th);
        frameLayout.setVisibility(8);
    }

    public static /* synthetic */ void lambda$startLottieAnimationWith$1(int i, FrameLayout frameLayout, Throwable th) {
        Log.w("IllustrationPreference", "Invalid illustration resource id: " + i, th);
        frameLayout.setVisibility(8);
    }

    public static void resetAnimation(Drawable drawable) {
        if (drawable instanceof Animatable) {
            if (drawable instanceof Animatable2) {
                ((Animatable2) drawable).clearAnimationCallbacks();
            } else if (drawable instanceof Animatable2Compat) {
                ((Animatable2Compat) drawable).clearAnimationCallbacks();
            }
            ((Animatable) drawable).stop();
        }
    }

    public static void resetAnimations(LottieAnimationView lottieAnimationView) {
        resetAnimation(lottieAnimationView.getDrawable());
        lottieAnimationView.cancelAnimation();
    }

    public static void startLottieAnimationWith(final FrameLayout frameLayout, LottieAnimationView lottieAnimationView, final int i) {
        frameLayout.setVisibility(0);
        lottieAnimationView.setFailureListener(new LottieListener() { // from class: com.android.settingslib.widget.IllustrationPreference$$ExternalSyntheticLambda1
            @Override // com.airbnb.lottie.LottieListener
            public final void onResult(Object obj) {
                IllustrationPreference.$r8$lambda$5HXvlsscCLhfYvKcdLvnjpfwtbU(i, frameLayout, (Throwable) obj);
            }
        });
        lottieAnimationView.setAnimation(i);
        lottieAnimationView.setRepeatCount(-1);
        lottieAnimationView.playAnimation();
    }

    public static void startLottieAnimationWith(final FrameLayout frameLayout, LottieAnimationView lottieAnimationView, final Uri uri) {
        InputStream inputStreamFromUri = getInputStreamFromUri(lottieAnimationView.getContext(), uri);
        frameLayout.setVisibility(0);
        lottieAnimationView.setFailureListener(new LottieListener() { // from class: com.android.settingslib.widget.IllustrationPreference$$ExternalSyntheticLambda0
            @Override // com.airbnb.lottie.LottieListener
            public final void onResult(Object obj) {
                IllustrationPreference.$r8$lambda$erLFEYMER2agMFjLwc8lDJTVeco(uri, frameLayout, (Throwable) obj);
            }
        });
        lottieAnimationView.setAnimation(inputStreamFromUri, null);
        lottieAnimationView.setRepeatCount(-1);
        lottieAnimationView.playAnimation();
    }

    public final void handleImageFrameMaxHeight(ImageView imageView, ImageView imageView2) {
        if (this.mMaxHeight == -1) {
            return;
        }
        Resources resources = imageView.getResources();
        int dimensionPixelSize = resources.getDimensionPixelSize(R$dimen.settingslib_illustration_width);
        int dimensionPixelSize2 = resources.getDimensionPixelSize(R$dimen.settingslib_illustration_height);
        int min = Math.min(this.mMaxHeight, dimensionPixelSize2);
        imageView.setMaxHeight(min);
        imageView2.setMaxHeight(min);
        imageView2.setMaxWidth((int) (min * (dimensionPixelSize / dimensionPixelSize2)));
    }

    public final void handleImageWithAnimation(FrameLayout frameLayout, LottieAnimationView lottieAnimationView) {
        if (this.mImageDrawable != null) {
            resetAnimations(lottieAnimationView);
            lottieAnimationView.setImageDrawable(this.mImageDrawable);
            Drawable drawable = lottieAnimationView.getDrawable();
            if (drawable != null) {
                startAnimation(drawable);
            }
        }
        if (this.mImageUri != null) {
            resetAnimations(lottieAnimationView);
            lottieAnimationView.setImageURI(this.mImageUri);
            Drawable drawable2 = lottieAnimationView.getDrawable();
            if (drawable2 != null) {
                startAnimation(drawable2);
            } else {
                startLottieAnimationWith(frameLayout, lottieAnimationView, this.mImageUri);
            }
        }
        if (this.mImageResId > 0) {
            resetAnimations(lottieAnimationView);
            lottieAnimationView.setImageResource(this.mImageResId);
            Drawable drawable3 = lottieAnimationView.getDrawable();
            if (drawable3 != null) {
                startAnimation(drawable3);
            } else {
                startLottieAnimationWith(frameLayout, lottieAnimationView, this.mImageResId);
            }
        }
    }

    public final void handleMiddleGroundView(ViewGroup viewGroup) {
        viewGroup.removeAllViews();
        View view = this.mMiddleGroundView;
        if (view == null) {
            viewGroup.setVisibility(8);
            return;
        }
        viewGroup.addView(view);
        viewGroup.setVisibility(0);
    }

    public final void init(Context context, AttributeSet attributeSet) {
        setLayoutResource(R$layout.illustration_preference);
        this.mIsAutoScale = false;
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.LottieAnimationView, 0, 0);
            this.mImageResId = obtainStyledAttributes.getResourceId(R$styleable.LottieAnimationView_lottie_rawRes, 0);
            obtainStyledAttributes.recycle();
        }
    }

    @Override // androidx.preference.Preference
    public void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        ImageView imageView = (ImageView) preferenceViewHolder.findViewById(R$id.background_view);
        ViewGroup viewGroup = (FrameLayout) preferenceViewHolder.findViewById(R$id.middleground_layout);
        LottieAnimationView lottieAnimationView = (LottieAnimationView) preferenceViewHolder.findViewById(R$id.lottie_view);
        int i = getContext().getResources().getDisplayMetrics().widthPixels;
        int i2 = getContext().getResources().getDisplayMetrics().heightPixels;
        FrameLayout frameLayout = (FrameLayout) preferenceViewHolder.findViewById(R$id.illustration_frame);
        ViewGroup.LayoutParams layoutParams = frameLayout.getLayoutParams();
        if (i >= i2) {
            i = i2;
        }
        layoutParams.width = i;
        frameLayout.setLayoutParams(layoutParams);
        handleImageWithAnimation(frameLayout, lottieAnimationView);
        handleImageFrameMaxHeight(imageView, lottieAnimationView);
        boolean z = this.mIsAutoScale;
        if (z) {
            lottieAnimationView.setScaleType(z ? ImageView.ScaleType.CENTER_CROP : ImageView.ScaleType.CENTER_INSIDE);
        }
        handleMiddleGroundView(viewGroup);
    }

    public final void startAnimation(Drawable drawable) {
        if (drawable instanceof Animatable) {
            if (drawable instanceof Animatable2) {
                ((Animatable2) drawable).registerAnimationCallback(this.mAnimationCallback);
            } else if (drawable instanceof Animatable2Compat) {
                ((Animatable2Compat) drawable).registerAnimationCallback(this.mAnimationCallbackCompat);
            } else if (drawable instanceof AnimationDrawable) {
                ((AnimationDrawable) drawable).setOneShot(false);
            }
            ((Animatable) drawable).start();
        }
    }
}