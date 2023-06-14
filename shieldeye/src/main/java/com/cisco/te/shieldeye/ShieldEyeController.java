package com.cisco.te.shieldeye;

import java.util.List;

import com.cisco.te.shieldeye.client.te.TeClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cisco.te.shieldeye.model.SecurityScanResponse;

/**
 * 
 * @author yurpopov
 *
 */

@RestController
public class ShieldEyeController {
	
	private static Logger logger = LoggerFactory.getLogger(ShieldEyeController.class);

	@Autowired
	private SdavcSecurityService sdavcSecurityService;

	@Autowired
	private ShieldEyeUtils shieldEyeUtils;

	@Autowired
	private TeClient teClient;
	
	@GetMapping("/security-scan")
	public SecurityScanResponse securityScan(@RequestParam(value = "segment", defaultValue = "apple") String segment, @RequestParam(value = "windowStart", defaultValue = "1686731700") Long windowStart, @RequestParam(value = "windowSize", defaultValue = "5") Integer windowSize, @RequestParam(value = "mock", defaultValue = "false") boolean isMock) {
		logger.info("Getting security scan for segment={}, windowStart={}, windowSize={}", segment, windowStart, windowSize);

		List<Long> timeFrame = shieldEyeUtils.getTimeFrame(windowStart, windowSize);
		long periodInMinutes = shieldEyeUtils.getPeriodInMinutes(windowStart);
		List<String> targetIps = teClient.getTEtest(timeFrame);
		logger.info("Potential servers are {}",targetIps);
		SecurityScanResponse scan;
		if (isMock){
			scan = sdavcSecurityService.getSecurityIssuesMock(targetIps, segment, periodInMinutes);
		}
		scan = sdavcSecurityService.getSecurityIssues(targetIps, segment, periodInMinutes);
		return scan; 
	}


}
