package com.cisco.te.shieldeye;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.cisco.te.shieldeye.client.te.TeClient;
import com.cisco.te.shieldeye.sdavc.client.model.DcsDevice;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cisco.te.shieldeye.model.SecurityScanResponse;

/**
 * @author yurpopov
 */

@RestController
public class ShieldEyeController {

    private static Logger logger = LoggerFactory.getLogger(ShieldEyeController.class);

    @Autowired
    private SdavcSecurityService sdavcSecurityService;


    @Autowired
    private TeClient teClient;

    @GetMapping("/security-scan")
    public SecurityScanResponse securityScan(@RequestParam(value = "segment", defaultValue = "apple") String segment, @RequestParam(value = "windowStart", defaultValue = "1686731700") Long windowStart, @RequestParam(value = "windowSize", defaultValue = "5") Integer windowSize, @RequestParam(value = "mock", defaultValue = "true") boolean isMock, @RequestParam(value = "targetIp", defaultValue = "") String targetIp) {
        logger.info("Getting security scan for segment={}, windowStart={}, windowSize={}, targetIp={}", segment, windowStart, windowSize, targetIp);
        logger.info("isMock: {}", isMock);
        boolean showSensitiveData = false;

        List<Long> timeFrame = ShieldEyeUtils.getTimeFrame(windowStart, windowSize);
        long periodInMinutes = ShieldEyeUtils.getPeriodInMinutes(windowStart);
        logger.info("Period in min are: {}", periodInMinutes);
        List<String> targetIps = new ArrayList<>();
        if (targetIp.equals("")) {
            targetIps = teClient.getTEtest(timeFrame);
        } else {
            targetIps.add(targetIp);
        }
        logger.info("Real - Potential servers are {}", targetIps);
        SecurityScanResponse scan;
        if (isMock) {
            logger.info("Mock - Potential servers are {}", targetIps);
            scan = sdavcSecurityService.getSecurityIssuesMock(targetIps, segment, periodInMinutes, showSensitiveData);
        } else {
            scan = sdavcSecurityService.getSecurityIssues(targetIps, segment, periodInMinutes, showSensitiveData);
        }
        return scan;
    }

}
