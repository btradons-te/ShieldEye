package com.cisco.te.shieldeye;

import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;


@Component
public class ShieldEyeUtils {

    public List<Long> getTimeFrame(long windowStart, int windowSize){
        List<Long> timeFrame = new ArrayList<>();
        timeFrame.add(windowStart);
        timeFrame.add(windowStart + windowSize*60); // window size is in minutes. TimeFrame is epoch.
        return timeFrame;
    }

    public long getPeriodInMinutes(long windowStart){
        return (long) Math.ceil((Instant.now().getEpochSecond() - windowStart)/60.0);
    }
}
