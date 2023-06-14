package com.cisco.te.shieldeye.sdavc.client.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AnomalyReduced extends SharedAnomaly {

    @SerializedName("detectionTime")
    @Expose
    private Long detectionTime;

    @SerializedName("detectionType")
    @Expose
    private String detectionType;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @SerializedName("description")
    @Expose
    private String description;
}
