package org.appledash.dashplugins.test;

import org.appledash.dashplugins.util.SemVer;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by appledash on 7/18/17.
 * Blackjack is best pony.
 */
public class SemVerTest {
    @Test
    public void testParsing() {
        SemVer semVer = new SemVer("1.0.0");
        Assert.assertEquals(1, semVer.getMajor());
        Assert.assertEquals(0, semVer.getMinor());
        Assert.assertEquals(0, semVer.getPatch());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidParsing() {
        new SemVer("potato");
    }
}
