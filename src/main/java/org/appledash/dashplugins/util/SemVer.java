package org.appledash.dashplugins.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents a version number that adheres to the SemVer standard of versioning.
 */
public class SemVer implements Comparable<SemVer> {
    private static final Pattern SEMVER_PATTERN = Pattern.compile("^([0-9]+)\\.([0-9]+)\\.([0-9]+)$");
    private final int major;
    private final int minor;
    private final int patch;

    /**
     * Construct a new SemVer instance using the given version string.
     * @param version Version string.
     * @throws IllegalArgumentException if version is not valid SemVer.
     */
    public SemVer(String version) {
        Matcher m = SEMVER_PATTERN.matcher(version);

        if (!m.matches()) {
            throw new IllegalArgumentException("Invalid SemVer specified.");
        }

        this.major = Integer.valueOf(m.group(1));
        this.minor = Integer.valueOf(m.group(2));
        this.patch = Integer.valueOf(m.group(3));
    }

    /**
     * Get the major version number of this version.
     * @return Major version number.
     */
    public int getMajor() {
        return major;
    }

    /**
     * Get the minor version number of this version.
     * @return Minor version number.
     */
    public int getMinor() {
        return minor;
    }

    /**
     * Get the patch version number of this version.
     * @return Patch version number.
     */
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

    @Override
    public int compareTo(SemVer other) {
        int result = major - other.major;

        if (result == 0) {
            result = minor - other.minor;

            if (result == 0) {
                result = patch - other.patch;
            }
        }

        return result;
    }
}
