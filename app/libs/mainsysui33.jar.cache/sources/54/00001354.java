package com.android.systemui.colorextraction;

import android.app.WallpaperColors;
import android.app.WallpaperManager;
import android.content.Context;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.colorextraction.ColorExtractor;
import com.android.internal.colorextraction.types.ExtractionType;
import com.android.internal.colorextraction.types.Tonal;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.Dumpable;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.statusbar.policy.ConfigurationController;
import java.io.PrintWriter;
import java.util.Arrays;

/* loaded from: mainsysui33.jar:com/android/systemui/colorextraction/SysuiColorExtractor.class */
public class SysuiColorExtractor extends ColorExtractor implements Dumpable, ConfigurationController.ConfigurationListener {
    public final ColorExtractor.GradientColors mBackdropColors;
    public boolean mHasMediaArtwork;
    public final ColorExtractor.GradientColors mNeutralColorsLock;
    public final Tonal mTonal;

    /* JADX DEBUG: Multi-variable search result rejected for r6v0, resolved type: com.android.systemui.colorextraction.SysuiColorExtractor */
    /* JADX WARN: Multi-variable type inference failed */
    @VisibleForTesting
    public SysuiColorExtractor(Context context, ExtractionType extractionType, ConfigurationController configurationController, WallpaperManager wallpaperManager, DumpManager dumpManager, boolean z) {
        super(context, extractionType, z, wallpaperManager);
        this.mTonal = extractionType instanceof Tonal ? (Tonal) extractionType : new Tonal(context);
        this.mNeutralColorsLock = new ColorExtractor.GradientColors();
        configurationController.addCallback(this);
        dumpManager.registerDumpable(getClass().getSimpleName(), this);
        ColorExtractor.GradientColors gradientColors = new ColorExtractor.GradientColors();
        this.mBackdropColors = gradientColors;
        gradientColors.setMainColor(-16777216);
        if (wallpaperManager.isWallpaperSupported()) {
            wallpaperManager.removeOnColorsChangedListener(this);
            wallpaperManager.addOnColorsChangedListener(this, null, -1);
        }
    }

    public SysuiColorExtractor(Context context, ConfigurationController configurationController, DumpManager dumpManager) {
        this(context, new Tonal(context), configurationController, (WallpaperManager) context.getSystemService(WallpaperManager.class), dumpManager, false);
    }

    @Override // com.android.systemui.Dumpable
    public void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println("SysuiColorExtractor:");
        printWriter.println("  Current wallpaper colors:");
        printWriter.println("    system: " + ((ColorExtractor) this).mSystemColors);
        printWriter.println("    lock: " + ((ColorExtractor) this).mLockColors);
        ColorExtractor.GradientColors[] gradientColorsArr = (ColorExtractor.GradientColors[]) ((ColorExtractor) this).mGradientColors.get(1);
        ColorExtractor.GradientColors[] gradientColorsArr2 = (ColorExtractor.GradientColors[]) ((ColorExtractor) this).mGradientColors.get(2);
        printWriter.println("  Gradients:");
        printWriter.println("    system: " + Arrays.toString(gradientColorsArr));
        printWriter.println("    lock: " + Arrays.toString(gradientColorsArr2));
        printWriter.println("  Neutral colors: " + this.mNeutralColorsLock);
        printWriter.println("  Has media backdrop: " + this.mHasMediaArtwork);
    }

    public void extractWallpaperColors() {
        ColorExtractor.GradientColors gradientColors;
        super.extractWallpaperColors();
        Tonal tonal = this.mTonal;
        if (tonal == null || (gradientColors = this.mNeutralColorsLock) == null) {
            return;
        }
        WallpaperColors wallpaperColors = ((ColorExtractor) this).mLockColors;
        WallpaperColors wallpaperColors2 = wallpaperColors;
        if (wallpaperColors == null) {
            wallpaperColors2 = ((ColorExtractor) this).mSystemColors;
        }
        tonal.applyFallback(wallpaperColors2, gradientColors);
    }

    public ColorExtractor.GradientColors getColors(int i, int i2) {
        return (!this.mHasMediaArtwork || (i & 2) == 0) ? super.getColors(i, i2) : this.mBackdropColors;
    }

    public ColorExtractor.GradientColors getNeutralColors() {
        return this.mHasMediaArtwork ? this.mBackdropColors : this.mNeutralColorsLock;
    }

    public void onColorsChanged(WallpaperColors wallpaperColors, int i, int i2) {
        if (i2 != KeyguardUpdateMonitor.getCurrentUser()) {
            return;
        }
        if ((i & 2) != 0) {
            this.mTonal.applyFallback(wallpaperColors, this.mNeutralColorsLock);
        }
        super.onColorsChanged(wallpaperColors, i);
    }

    public void onUiModeChanged() {
        extractWallpaperColors();
        triggerColorsChanged(3);
    }

    public void setHasMediaArtwork(boolean z) {
        if (this.mHasMediaArtwork != z) {
            this.mHasMediaArtwork = z;
            triggerColorsChanged(2);
        }
    }
}