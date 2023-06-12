package com.cisco.te.shieldeye;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cisco.te.shieldeye.model.SecurityScanResponse;
import com.cisco.te.shieldeye.model.TargetScanResult;
import com.cisco.te.shieldeye.sdavc.client.SdAvcClient;
import com.cisco.te.shieldeye.sdavc.client.SdAvcClientConstants;
import com.cisco.te.shieldeye.sdavc.client.SdAvcServerDefaultLogin;
import com.cisco.te.shieldeye.sdavc.client.model.DcsDevice;
import com.cisco.te.shieldeye.sdavc.client.model.DetectedAnomaly;

@Component
public class SdavcSecurityService {

	public static String SDAVC_SEVRVER_IP = "10.10.10.10";
	public static int SDAVC_PORT = SdAvcClientConstants.SDAVC_PORT;
	public static String SDAVC_USER = "user";
	public static String SDAVC_PASSWORD = "password";

	@Autowired
	private SdAvcClient sdAvcClient;

	/**
	 * Get security scan for sdavc segment from SDAVC server and return results for
	 * targetIps
	 * 
	 * @param targetIps
	 * @param segment
	 * @param periodInMinutes
	 * @return SecurityScanResponse
	 */
	public SecurityScanResponse getSecurityIssues(List<String> targetIps, String segment, Integer periodInMinutes) {
		SecurityScanResponse scanResponse = new SecurityScanResponse();

		try {
			SdAvcServerDefaultLogin sdAvclogin = new SdAvcServerDefaultLogin();
			sdAvclogin.setSdAvcIp(SDAVC_SEVRVER_IP);
			sdAvclogin.setSdavcPort(SDAVC_PORT);
			sdAvclogin.setUserName(SDAVC_USER);
			sdAvclogin.setPassword(SDAVC_PASSWORD);

			// TODO replace method call to the getDcsDevices
			List<DcsDevice> devices = sdAvcClient.getDcsDevices(sdAvclogin, segment, periodInMinutes);
			scanResponse.setStatus("Success");

			Map<String, TargetScanResult> scanResultsPerDevice = new HashMap<>();
			for (DcsDevice dcsDevice : devices) {
				String deviceIp = dcsDevice.getMetadata().getProbeData().getIp();
				if (targetIps.contains(deviceIp)) {
					TargetScanResult deviceScanResult;
					if (scanResultsPerDevice.containsKey(deviceIp)) {
						deviceScanResult = scanResultsPerDevice.get(deviceIp);
					} else {
						deviceScanResult = new TargetScanResult();
						deviceScanResult.setTargetIp(deviceIp);
						scanResultsPerDevice.put(deviceIp, deviceScanResult);
					}
					List<DetectedAnomaly> anomalies = dcsDevice.getMetadata().getAnomalies().getDetectedAnomalies();
					deviceScanResult.add(anomalies);
				}
			}
			scanResponse.setTargetScanResult(scanResultsPerDevice.values());
		} catch (Exception e) {
			scanResponse.setStatus(e.toString());
		}
		return scanResponse;
	}

}
