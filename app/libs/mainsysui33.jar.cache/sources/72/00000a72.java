package com.airbnb.lottie.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* loaded from: mainsysui33.jar:com/airbnb/lottie/model/KeyPath.class */
public class KeyPath {
    public final List<String> keys;
    public KeyPathElement resolvedElement;

    public KeyPath(KeyPath keyPath) {
        this.keys = new ArrayList(keyPath.keys);
        this.resolvedElement = keyPath.resolvedElement;
    }

    public KeyPath(String... strArr) {
        this.keys = Arrays.asList(strArr);
    }

    public KeyPath addKey(String str) {
        KeyPath keyPath = new KeyPath(this);
        keyPath.keys.add(str);
        return keyPath;
    }

    public final boolean endsWithGlobstar() {
        List<String> list = this.keys;
        return list.get(list.size() - 1).equals("**");
    }

    /* JADX WARN: Code restructure failed: missing block: B:24:0x0084, code lost:
        if (endsWithGlobstar() != false) goto L23;
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x00e8, code lost:
        if (endsWithGlobstar() != false) goto L42;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean fullyResolvesTo(String str, int i) {
        boolean z;
        boolean z2;
        if (i >= this.keys.size()) {
            return false;
        }
        boolean z3 = i == this.keys.size() - 1;
        String str2 = this.keys.get(i);
        if (!str2.equals("**")) {
            boolean z4 = str2.equals(str) || str2.equals("*");
            if (!z3) {
                z2 = false;
                if (i == this.keys.size() - 2) {
                    z2 = false;
                }
                return z2;
            }
            z2 = false;
            if (z4) {
                z2 = true;
            }
            return z2;
        }
        if (!(!z3 && this.keys.get(i + 1).equals(str))) {
            if (z3) {
                return true;
            }
            int i2 = i + 1;
            if (i2 < this.keys.size() - 1) {
                return false;
            }
            return this.keys.get(i2).equals(str);
        }
        if (i != this.keys.size() - 2) {
            z = false;
            if (i == this.keys.size() - 3) {
                z = false;
            }
            return z;
        }
        z = true;
        return z;
    }

    public KeyPathElement getResolvedElement() {
        return this.resolvedElement;
    }

    public int incrementDepthBy(String str, int i) {
        if (isContainer(str)) {
            return 0;
        }
        if (this.keys.get(i).equals("**")) {
            return (i != this.keys.size() - 1 && this.keys.get(i + 1).equals(str)) ? 2 : 0;
        }
        return 1;
    }

    public final boolean isContainer(String str) {
        return "__container".equals(str);
    }

    public boolean matches(String str, int i) {
        if (isContainer(str)) {
            return true;
        }
        if (i >= this.keys.size()) {
            return false;
        }
        return this.keys.get(i).equals(str) || this.keys.get(i).equals("**") || this.keys.get(i).equals("*");
    }

    public boolean propagateToChildren(String str, int i) {
        if ("__container".equals(str)) {
            return true;
        }
        boolean z = true;
        if (i >= this.keys.size() - 1) {
            z = this.keys.get(i).equals("**");
        }
        return z;
    }

    public KeyPath resolve(KeyPathElement keyPathElement) {
        KeyPath keyPath = new KeyPath(this);
        keyPath.resolvedElement = keyPathElement;
        return keyPath;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("KeyPath{keys=");
        sb.append(this.keys);
        sb.append(",resolved=");
        sb.append(this.resolvedElement != null);
        sb.append('}');
        return sb.toString();
    }
}