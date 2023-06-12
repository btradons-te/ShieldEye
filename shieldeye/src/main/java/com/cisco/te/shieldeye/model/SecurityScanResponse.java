package com.cisco.te.shieldeye.model;

import java.util.Collection;

public class SecurityScanResponse {

	
	private Collection<TargetScanResult> targetScanResult;
	
	private String status;
	
	public void setStatus(String status) {
		this.status = status;		
	}

	public String getStatus() {
		return status;
	}

	/**
	 * @return the targetScanResult
	 */
	public Collection<TargetScanResult> getTargetScanResult() {
		return targetScanResult;
	}

	/**
	 * @param collection the targetScanResult to set
	 */
	public void setTargetScanResult(Collection<TargetScanResult> collection) {
		this.targetScanResult = collection;
	}

}
