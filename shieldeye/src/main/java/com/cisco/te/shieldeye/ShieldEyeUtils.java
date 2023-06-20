package com.cisco.te.shieldeye;

import com.cisco.te.shieldeye.sdavc.client.UnauthPorts;
import com.cisco.te.shieldeye.sdavc.client.WeakCred;
import com.cisco.te.shieldeye.sdavc.client.model.*;
import com.google.gson.Gson;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;


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
            String json = new String(Files.readAllBytes(Paths.get("src/test/resources/two_responses.json")));
            List<DcsDevice> devices = Arrays.asList(new Gson().fromJson(json, DcsDevice[].class));
            return devices;
        } catch (IOException io) {
            System.out.println(io.getMessage());
            return new ArrayList<>();
        }
    }

    public static List<AnomalyReduced> createReducedAnomalies(long lastHit, Anomalies anomalies, boolean showSensitive) {
        List<AnomalyReduced> anomalyReducedList = new ArrayList<>();

        long oneDay = 24 * 60 * 60;
        for (int i = 0; i < anomalies.getAnomalyDecision().size(); i++) {
            AnomalyReduced ar = new AnomalyReduced();
            DetectedAnomaly da = anomalies.getDetectedAnomalies().get(i);
            String detectionType = da.getDetectionType();
            String description = da.getReason();
            if (detectionType.equals("weakCredentials")) {
                WeakCred wc = new WeakCred(da, showSensitive);
                description += ". " + wc;
            } else if (detectionType.equals("unauthorizedPorts")) {
                UnauthPorts up = new UnauthPorts(da, showSensitive);
                description += ". " + up;
            }
            ar.setDescription(description);
            ar.setDetectionType(da.getDetectionType());
            ar.setDetectionTime(Instant.now().getEpochSecond() - oneDay);
//            ar.setDetectionTime(Instant.now().getEpochSecond());
            ar.setLastHit(lastHit);
            anomalyReducedList.add(ar);
        }

        return anomalyReducedList;
    }

    public static long getRandomTimeFromStart(long windowStart) {
        Random rand = new Random();
        // Setting the upper bound to generate the
        // random numbers in specific range
        int upperbound = 25;
        int int_random = rand.nextInt(upperbound);
        long timeInEpoch = windowStart + int_random;
//        Date date = new Date(timeInEpoch*1000);
//        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String formatted = format.format(date);
//        return formatted;
        return timeInEpoch;
    }
}


