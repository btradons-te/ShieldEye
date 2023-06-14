package com.cisco.te.shieldeye;

import java.util.ArrayList;
import java.util.List;


public class ShieldEyeControllerUtils {
    public static int FIVE_MINUTES_EPOCH = 5 * 60;
    private List<Integer> getTimeFrame(int roundId, int windowSize){
        List<Integer> timeFrame = new ArrayList<>();
        timeFrame.add(roundId);
        timeFrame.add(roundId + FIVE_MINUTES_EPOCH);
    }
}
