
package com.cisco.te.shieldeye.sdavc.client.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ClassificationData {

    @SerializedName("confidenceDeviceType")
    @Expose
    private List<String> confidenceDeviceType;
    @SerializedName("confidenceHardwareManufacturer")
    @Expose
    private List<String> confidenceHardwareManufacturer;
    @SerializedName("confidenceHardwareModel")
    @Expose
    private List<String> confidenceHardwareModel;
    @SerializedName("confidenceOperatingSystem")
    @Expose
    private List<String> confidenceOperatingSystem;
    @SerializedName("conflictingTagsSource")
    @Expose
    private List<String> conflictingTagsSource;
    @SerializedName("consistency")
    @Expose
    private List<String> consistency;
    @SerializedName("debug")
    @Expose
    private List<String> debug;
    @SerializedName("deviceType")
    @Expose
    private List<String> deviceType;
    @SerializedName("hardwareManufacturer")
    @Expose
    private List<String> hardwareManufacturer;
    @SerializedName("hardwareModel")
    @Expose
    private List<String> hardwareModel;
    @SerializedName("matchedRules")
    @Expose
    private MatchedRules matchedRules;
    @SerializedName("operatingSystem")
    @Expose
    private List<String> operatingSystem;
    @SerializedName("pktDataPrev")
    @Expose
    private List<String> pktDataPrev;
    @SerializedName("pluginId")
    @Expose
    private List<String> pluginId;

    public List<String> getConfidenceDeviceType() {
        return confidenceDeviceType;
    }

    public void setConfidenceDeviceType(List<String> confidenceDeviceType) {
        this.confidenceDeviceType = confidenceDeviceType;
    }

    public List<String> getConfidenceHardwareManufacturer() {
        return confidenceHardwareManufacturer;
    }

    public void setConfidenceHardwareManufacturer(List<String> confidenceHardwareManufacturer) {
        this.confidenceHardwareManufacturer = confidenceHardwareManufacturer;
    }

    public List<String> getConfidenceHardwareModel() {
        return confidenceHardwareModel;
    }

    public void setConfidenceHardwareModel(List<String> confidenceHardwareModel) {
        this.confidenceHardwareModel = confidenceHardwareModel;
    }

    public List<String> getConfidenceOperatingSystem() {
        return confidenceOperatingSystem;
    }

    public void setConfidenceOperatingSystem(List<String> confidenceOperatingSystem) {
        this.confidenceOperatingSystem = confidenceOperatingSystem;
    }

    public List<String> getConflictingTagsSource() {
        return conflictingTagsSource;
    }

    public void setConflictingTagsSource(List<String> conflictingTagsSource) {
        this.conflictingTagsSource = conflictingTagsSource;
    }

    public List<String> getConsistency() {
        return consistency;
    }

    public void setConsistency(List<String> consistency) {
        this.consistency = consistency;
    }

    public List<String> getDebug() {
        return debug;
    }

    public void setDebug(List<String> debug) {
        this.debug = debug;
    }

    public List<String> getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(List<String> deviceType) {
        this.deviceType = deviceType;
    }

    public List<String> getHardwareManufacturer() {
        return hardwareManufacturer;
    }

    public void setHardwareManufacturer(List<String> hardwareManufacturer) {
        this.hardwareManufacturer = hardwareManufacturer;
    }

    public List<String> getHardwareModel() {
        return hardwareModel;
    }

    public void setHardwareModel(List<String> hardwareModel) {
        this.hardwareModel = hardwareModel;
    }

    public MatchedRules getMatchedRules() {
        return matchedRules;
    }

    public void setMatchedRules(MatchedRules matchedRules) {
        this.matchedRules = matchedRules;
    }

    public List<String> getOperatingSystem() {
        return operatingSystem;
    }

    public void setOperatingSystem(List<String> operatingSystem) {
        this.operatingSystem = operatingSystem;
    }

    public List<String> getPktDataPrev() {
        return pktDataPrev;
    }

    public void setPktDataPrev(List<String> pktDataPrev) {
        this.pktDataPrev = pktDataPrev;
    }

    public List<String> getPluginId() {
        return pluginId;
    }

    public void setPluginId(List<String> pluginId) {
        this.pluginId = pluginId;
    }

}
