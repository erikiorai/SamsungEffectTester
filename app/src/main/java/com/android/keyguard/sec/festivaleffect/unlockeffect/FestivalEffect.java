package com.android.keyguard.sec.festivaleffect.unlockeffect;

import android.animation.Animator;
import android.content.Context;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;

import java.lang.reflect.Field;

public class FestivalEffect extends FrameLayout {
    public FestivalEffect(@NonNull Context context) {
        super(context);
    }

    public void clearEffect() {}

    public void add(float x, float y) {};
    public void move(float x, float y) {};

    public void drawPause() {
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                Object value = field.get(this);
                if (value instanceof Animator) {
                    Animator animator = (Animator) value;
                    animator.pause();
                }
            } catch (IllegalAccessException e) {
                // Handle the exception
            }
        }
    }

    public void drawResume() {
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                Object value = field.get(this);
                if (value instanceof Animator) {
                    Animator animator = (Animator) value;
                    animator.resume();
                }
            } catch (IllegalAccessException e) {
                // Handle the exception
            }
        }
    }
}
