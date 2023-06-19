/**
 * 
 */
package com.cisco.te.shieldeye.model;

import com.cisco.te.shieldeye.sdavc.client.model.AnomalyReduced;
import com.cisco.te.shieldeye.sdavc.client.model.DetectedAnomaly;
import com.cisco.te.shieldeye.sdavc.client.model.SharedAnomaly;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yurpopov
 *
 */
public class TargetScanResult {
	private String targetIp;
	private List<SharedAnomaly> detectedAnomalies;
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
	public List<SharedAnomaly> getDetectedAnomalies() {
		return detectedAnomalies;
	}
	/**
	 * @param detectedAnomalies the detectedAnomalies to set
	 */
	public void setDetectedAnomalies(List<SharedAnomaly> detectedAnomalies) {
		this.detectedAnomalies = detectedAnomalies;
	}
	
	/**
	 * Add detected anomalies list
	 * @param anomalies
	 */
	public void add(List<AnomalyReduced> anomalies) {
		if (this.detectedAnomalies == null) {
			this.detectedAnomalies = new ArrayList<>();
		}
		this.detectedAnomalies.addAll(anomalies);
	}
}
