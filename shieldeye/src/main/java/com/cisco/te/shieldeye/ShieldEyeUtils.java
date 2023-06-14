package com.cisco.te.shieldeye;

import com.cisco.te.shieldeye.sdavc.client.model.*;
import com.google.gson.Gson;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ShieldEyeUtils {

    public static List<Long> getTimeFrame(long windowStart, int windowSize) {
        List<Long> timeFrame = new ArrayList<>();
        timeFrame.add(windowStart);
        timeFrame.add(windowStart + windowSize * 60); // window size is in minutes. TimeFrame is epoch.
        return timeFrame;
    }

    public static long getPeriodInMinutes(long windowStart) {
        return (long) Math.ceil((Instant.now().getEpochSecond() - windowStart) / 60.0);
    }

    public static List<DcsDevice> getMockedDevices() {
        try {
            String json = new String(Files.readAllBytes(Paths.get("src/test/resources/dcsDevicesResponse.json")));
            List<DcsDevice> devices = Arrays.asList(new Gson().fromJson(json, DcsDevice[].class));
            return devices;
        } catch (IOException io) {
            System.out.println(io.getMessage());
            return new ArrayList<>();
        }
    }

    public static List<SharedAnomaly> createRedAnomaliesFromList(Anomalies anomalies) {
        long oneDay = 24*60*60;
        List<SharedAnomaly> anomalyReducedList = new ArrayList<>();
        AnomalyReduced ar = new AnomalyReduced();
        ar.setDescription(anomalies.getAnomalyDecision().get(0).getDescription());
        ar.setDetectionType(anomalies.getAnomalyDecision().get(0).getAnomalyType());
        ar.setDetectionTime(Instant.now().getEpochSecond()-oneDay);
        anomalyReducedList.add(ar);
        return anomalyReducedList;
    }
}


