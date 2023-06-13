package com.cisco.te.shieldeye;

import java.util.Arrays;
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
	private TeClient teClient;
	
	@GetMapping("/security-scan")
	public SecurityScanResponse securityScan(@RequestParam(value = "segment", defaultValue = "apple") String segment, @RequestParam(value = "periodInMinutes", defaultValue = "120") Integer periodInMinutes) {
		logger.info("Getting security scan for segment={}, period={}", segment, periodInMinutes);
		List<String> targetIps = teClient.getTEtest();
		SecurityScanResponse scan = sdavcSecurityService.getSecurityIssues(targetIps, segment, periodInMinutes);		
		return scan; 
	}
}
