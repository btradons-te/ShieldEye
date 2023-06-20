
package com.cisco.te.shieldeye.sdavc.client.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Anomalies {

    @SerializedName("anomalyDecision")
    @Expose
    private List<AnomalyDecision> anomalyDecision;
    @SerializedName("detectedAnomalies")
    @Expose
    private List<DetectedAnomaly> detectedAnomalies;

    public List<AnomalyDecision> getAnomalyDecision() {
        return anomalyDecision;
    }

    public void setAnomalyDecision(List<AnomalyDecision> anomalyDecision) {
        this.anomalyDecision = anomalyDecision;
    }

    public List<DetectedAnomaly> getDetectedAnomalies() {
        return detectedAnomalies;
    }

    public void setDetectedAnomalies(List<DetectedAnomaly> detectedAnomalies) {
        this.detectedAnomalies = detectedAnomalies;
    }

}
