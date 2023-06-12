/**
 * 
 */
package com.cisco.te.shieldeye.model;

import java.util.ArrayList;
import java.util.List;

import com.cisco.te.shieldeye.sdavc.client.model.DetectedAnomaly;

/**
 * @author yurpopov
 *
 */
public class TargetScanResult {
	private String targetIp;
	private List<DetectedAnomaly> detectedAnomalies;
	/**
	 * @return the targetIp
	 */
	public String getTargetIp() {
		return targetIp;
	}
	/**
	 * @param targetIp the targetIp to set
	 */
	public void setTargetIp(String targetIp) {
		this.targetIp = targetIp;
	}
	/**
	 * @return the detectedAnomalies
	 */
	public List<DetectedAnomaly> getDetectedAnomalies() {
		return detectedAnomalies;
	}
	/**
	 * @param detectedAnomalies the detectedAnomalies to set
	 */
	public void setDetectedAnomalies(List<DetectedAnomaly> detectedAnomalies) {
		this.detectedAnomalies = detectedAnomalies;
	}
	
	/**
	 * Add detected anomalies list
	 * @param anomalies
	 */
	public void add(List<DetectedAnomaly> anomalies) {
		if (this.detectedAnomalies == null) {
			this.detectedAnomalies = new ArrayList<>();
		}
		this.detectedAnomalies.addAll(anomalies);
	}
}
