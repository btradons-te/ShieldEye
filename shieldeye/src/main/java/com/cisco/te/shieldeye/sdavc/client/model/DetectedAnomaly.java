
package com.cisco.te.shieldeye.sdavc.client.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DetectedAnomaly {

    @SerializedName("anomalyProbes")
    @Expose
    private List<AnomalyProbe> anomalyProbes;
    @SerializedName("confidence")
    @Expose
    private Integer confidence;
    @SerializedName("detectionID")
    @Expose
    private String detectionID;
    @SerializedName("detectionTime")
    @Expose
    private Long detectionTime;
    @SerializedName("detectionType")
    @Expose
    private String detectionType;
    @SerializedName("reason")
    @Expose
    private String reason;
    @SerializedName("sdavcReason")
    @Expose
    private String sdavcReason;
    @SerializedName("severity")
    @Expose
    private Integer severity;
    @SerializedName("sources")
    @Expose
    private List<String> sources;

    public List<AnomalyProbe> getAnomalyProbes() {
        return anomalyProbes;
    }

    public void setAnomalyProbes(List<AnomalyProbe> anomalyProbes) {
        this.anomalyProbes = anomalyProbes;
    }

    public Integer getConfidence() {
        return confidence;
    }

    public void setConfidence(Integer confidence) {
        this.confidence = confidence;
    }

    public String getDetectionID() {
        return detectionID;
    }

    public void setDetectionID(String detectionID) {
        this.detectionID = detectionID;
    }

    public Long getDetectionTime() {
        return detectionTime;
    }

    public void setDetectionTime(Long detectionTime) {
        this.detectionTime = detectionTime;
    }

    public String getDetectionType() {
        return detectionType;
    }

    public void setDetectionType(String detectionType) {
        this.detectionType = detectionType;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getSdavcReason() {
        return sdavcReason;
    }

    public void setSdavcReason(String sdavcReason) {
        this.sdavcReason = sdavcReason;
    }

    public Integer getSeverity() {
        return severity;
    }

    public void setSeverity(Integer severity) {
        this.severity = severity;
    }

    public List<String> getSources() {
        return sources;
    }

    public void setSources(List<String> sources) {
        this.sources = sources;
    }

}
