
package com.cisco.te.shieldeye.sdavc.client.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AnomalyDecision {

    @SerializedName("anomalyType")
    @Expose
    private String anomalyType;
    @SerializedName("confidence")
    @Expose
    private Integer confidence;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("detectionID")
    @Expose
    private List<String> detectionID;
    @SerializedName("severity")
    @Expose
    private Integer severity;

    public String getAnomalyType() {
        return anomalyType;
    }

    public void setAnomalyType(String anomalyType) {
        this.anomalyType = anomalyType;
    }

    public Integer getConfidence() {
        return confidence;
    }

    public void setConfidence(Integer confidence) {
        this.confidence = confidence;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getDetectionID() {
        return detectionID;
    }

    public void setDetectionID(List<String> detectionID) {
        this.detectionID = detectionID;
    }

    public Integer getSeverity() {
        return severity;
    }

    public void setSeverity(Integer severity) {
        this.severity = severity;
    }

}
