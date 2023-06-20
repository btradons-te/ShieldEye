package com.cisco.te.shieldeye;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import com.cisco.te.shieldeye.sdavc.client.model.*;
import org.codehaus.plexus.util.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cisco.te.shieldeye.model.SecurityScanResponse;
import com.cisco.te.shieldeye.model.TargetScanResult;
import com.cisco.te.shieldeye.sdavc.client.SdAvcClient;
import com.cisco.te.shieldeye.sdavc.client.SdAvcClientConstants;
import com.cisco.te.shieldeye.sdavc.client.SdAvcServerDefaultLogin;

@Component
public class SdavcSecurityService {

	public static String SDAVC_SEVRVER_IP = "10.56.198.128";
	public static int SDAVC_PORT = SdAvcClientConstants.SDAVC_PORT;
	public static String SDAVC_USER = "lab";
	public static String SDAVC_PASSWORD = "lab";

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
	public SecurityScanResponse getSecurityIssues(List<String> targetIps, String segment, Long periodInMinutes, boolean showSensitive) {
		SecurityScanResponse scanResponse = new SecurityScanResponse();
		try {
			SdAvcServerDefaultLogin sdAvclogin = new SdAvcServerDefaultLogin();
			sdAvclogin.setSdAvcIp(SDAVC_SEVRVER_IP);
			sdAvclogin.setSdavcPort(SDAVC_PORT);
			sdAvclogin.setUserName(SDAVC_USER);
			sdAvclogin.setPassword(SDAVC_PASSWORD);
			sdAvclogin.setEnabled(true);

			// TODO replace method call to the getDcsDevices
			List<DcsDevice> devices = sdAvcClient.getDcsDevices(sdAvclogin, segment, periodInMinutes);
			System.out.println("Devices ----> " + devices);
			scanResponse.setStatus("Success");

			Map<String, TargetScanResult> scanResultsPerDevice = new HashMap<>();
			for (DcsDevice dcsDevice : devices) {
				String lastHit = dcsDevice.getLastHitsTime();
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
					Anomalies anomalies = dcsDevice.getMetadata().getAnomalies();
					List<AnomalyReduced> reducedAnomalies = ShieldEyeUtils.createReducedAnomalies(lastHit, anomalies, showSensitive);
					deviceScanResult.add(reducedAnomalies);
				}
			}
			scanResponse.setTargetScanResult(scanResultsPerDevice.values());
		} catch (Exception e) {
			scanResponse.setStatus(ExceptionUtils.getFullStackTrace(e));
		}
		return scanResponse;
	}

	public SecurityScanResponse getEmptySecurityIssues() {
		SecurityScanResponse scanResponse = new SecurityScanResponse();
		scanResponse.setTargetScanResult(new ArrayList<>());
		scanResponse.setStatus("Success");
		return scanResponse;
	}


	public SecurityScanResponse getSecurityIssuesMock(List<String> targetIps, String segment, Long periodInMinutes, boolean showSensitive, long windowStart) {
		SecurityScanResponse scanResponse = new SecurityScanResponse();
		try {
			List<DcsDevice> devices = ShieldEyeUtils.getMockedDevices();
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
					Anomalies anomaliesRaw = dcsDevice.getMetadata().getAnomalies();
//					String lastHitTime = dcsDevice.getLastHitsTime();
					String lastHitTime = ShieldEyeUtils.getRandomTimeFromStart(windowStart);
					List<AnomalyReduced> anomalies = ShieldEyeUtils.createReducedAnomalies(lastHitTime, anomaliesRaw, showSensitive);
					deviceScanResult.add(anomalies);
				}
			}
			scanResponse.setTargetScanResult(scanResultsPerDevice.values());
		} catch (Exception e) {
			scanResponse.setStatus(ExceptionUtils.getFullStackTrace(e));
		}
		return scanResponse;
	}

}
