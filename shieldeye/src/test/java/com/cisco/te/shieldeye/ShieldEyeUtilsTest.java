package com.cisco.te.shieldeye;

import org.junit.jupiter.api.Test;

import java.time.Instant;

import static java.lang.Thread.sleep;
import static org.junit.jupiter.api.Assertions.*;

class ShieldEyeUtilsTest {

    @Test
    void getPeriodInMinutes12() {
        ShieldEyeUtils shieldEyeUtils = new ShieldEyeUtils();
        long now = Instant.now().getEpochSecond();
        long twelveMinPlusInSec = 12 * 60+10;
        long minPeriod = shieldEyeUtils.getPeriodInMinutes(now-twelveMinPlusInSec);
        assert(minPeriod>=12);
    }

    @Test
    void getPeriodInMinutes1() {
        ShieldEyeUtils shieldEyeUtils = new ShieldEyeUtils();
        long now = Instant.now().getEpochSecond();
        long oneMin = 1 *60;
        long minPeriod = shieldEyeUtils.getPeriodInMinutes(now-oneMin);
        assert(minPeriod>=1);
    }
}