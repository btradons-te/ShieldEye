
package com.cisco.te.shieldeye.sdavc.client.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AnomalyProbe {

    @SerializedName("detectionTime")
    @Expose
    private List<String> detectionTime;
    @SerializedName("port")
    @Expose
    private List<String> port;
    @SerializedName("protocol")
    @Expose
    private List<String> protocol;
    @SerializedName("scanNames")
    @Expose
    private List<String> scanNames;
    @SerializedName("scannedIp")
    @Expose
    private List<String> scannedIp;
    @SerializedName("version")
    @Expose
    private List<String> version;
    @SerializedName("weakPassword")
    @Expose
    private List<String> weakPassword;
    @SerializedName("weakUserName")
    @Expose
    private List<String> weakUserName;
    @SerializedName("scanTcpNames")
    @Expose
    private List<String> scanTcpNames;
    @SerializedName("unauthorizedTcpPorts")
    @Expose
    private List<String> unauthorizedTcpPorts;
    @SerializedName("unauthorizedTcpPortsDetectionTime")
    @Expose
    private List<String> unauthorizedTcpPortsDetectionTime;

    public List<String> getDetectionTime() {
        return detectionTime;
    }

    public void setDetectionTime(List<String> detectionTime) {
        this.detectionTime = detectionTime;
    }

    public List<String> getPort() {
        return port;
    }

    public void setPort(List<String> port) {
        this.port = port;
    }

    public List<String> getProtocol() {
        return protocol;
    }

    public void setProtocol(List<String> protocol) {
        this.protocol = protocol;
    }

    public List<String> getScanNames() {
        return scanNames;
    }

    public void setScanNames(List<String> scanNames) {
        this.scanNames = scanNames;
    }

    public List<String> getScannedIp() {
        return scannedIp;
    }

    public void setScannedIp(List<String> scannedIp) {
        this.scannedIp = scannedIp;
    }

    public List<String> getVersion() {
        return version;
    }

    public void setVersion(List<String> version) {
        this.version = version;
    }

    public List<String> getWeakPassword() {
        return weakPassword;
    }

    public void setWeakPassword(List<String> weakPassword) {
        this.weakPassword = weakPassword;
    }

    public List<String> getWeakUserName() {
        return weakUserName;
    }

    public void setWeakUserName(List<String> weakUserName) {
        this.weakUserName = weakUserName;
    }

    public List<String> getScanTcpNames() {
        return scanTcpNames;
    }

    public void setScanTcpNames(List<String> scanTcpNames) {
        this.scanTcpNames = scanTcpNames;
    }

    public List<String> getUnauthorizedTcpPorts() {
        return unauthorizedTcpPorts;
    }

    public void setUnauthorizedTcpPorts(List<String> unauthorizedTcpPorts) {
        this.unauthorizedTcpPorts = unauthorizedTcpPorts;
    }

    public List<String> getUnauthorizedTcpPortsDetectionTime() {
        return unauthorizedTcpPortsDetectionTime;
    }

    public void setUnauthorizedTcpPortsDetectionTime(List<String> unauthorizedTcpPortsDetectionTime) {
        this.unauthorizedTcpPortsDetectionTime = unauthorizedTcpPortsDetectionTime;
    }

}
