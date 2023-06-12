
package com.cisco.te.shieldeye.sdavc.client.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProbeData {

    @SerializedName("ip")
    @Expose
    private String ip;
    @SerializedName("macAddress")
    @Expose
    private String macAddress;
    @SerializedName("userAgent")
    @Expose
    private String userAgent;
    @SerializedName("vrf")
    @Expose
    private String vrf;
    @SerializedName("weakCredentialsScanResult")
    @Expose
    private List<String> weakCredentialsScanResult;
    @SerializedName("nmapScanResult")
    @Expose
    private List<String> nmapScanResult;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getVrf() {
        return vrf;
    }

    public void setVrf(String vrf) {
        this.vrf = vrf;
    }

    public List<String> getWeakCredentialsScanResult() {
        return weakCredentialsScanResult;
    }

    public void setWeakCredentialsScanResult(List<String> weakCredentialsScanResult) {
        this.weakCredentialsScanResult = weakCredentialsScanResult;
    }

    public List<String> getNmapScanResult() {
        return nmapScanResult;
    }

    public void setNmapScanResult(List<String> nmapScanResult) {
        this.nmapScanResult = nmapScanResult;
    }

}
