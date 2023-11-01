package com.android.systemui;

import java.util.ArrayList;

/* loaded from: mainsysui33.jar:com/android/systemui/InitController.class */
public class InitController {
    public boolean mTasksExecuted = false;
    public final ArrayList<Runnable> mTasks = new ArrayList<>();

    public void addPostInitTask(Runnable runnable) {
        if (this.mTasksExecuted) {
            throw new IllegalStateException("post init tasks have already been executed!");
        }
        this.mTasks.add(runnable);
    }

    public void executePostInitTasks() {
        while (!this.mTasks.isEmpty()) {
            this.mTasks.remove(0).run();
        }
        this.mTasksExecuted = true;
    }
}