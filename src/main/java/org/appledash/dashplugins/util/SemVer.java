package org.appledash.dashplugins.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by appledash on 7/18/17.
 * Blackjack is best pony.
 */
public class SemVer {
    private static final Pattern SEMVER_PATTERN = Pattern.compile("^([0-9]+)\\.([0-9]+)\\.([0-9]+)$");
    private final int major;
    private final int minor;
    private final int patch;

    public SemVer(String version) {
        Matcher m = SEMVER_PATTERN.matcher(version);

        if (!m.matches()) {
            throw new IllegalArgumentException("Invalid SemVer specified.");
        }

        this.major = Integer.valueOf(m.group(1));
        this.minor = Integer.valueOf(m.group(2));
        this.patch = Integer.valueOf(m.group(3));
    }

    public int getMajor() {
        return major;
    }

    public int getMinor() {
        return minor;
    }

    public int getPatch() {
        return patch;
    }

    @Override
    public String toString() {
        return String.format("%d.%d.%d", this.major, this.minor, this.patch);
    }

    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }
}
