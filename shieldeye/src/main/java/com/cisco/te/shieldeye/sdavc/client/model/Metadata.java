
package com.cisco.te.shieldeye.sdavc.client.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Metadata {

    @SerializedName("anomalies")
    @Expose
    private Anomalies anomalies;
    @SerializedName("classificationData")
    @Expose
    private ClassificationData classificationData;
    @SerializedName("probeData")
    @Expose
    private ProbeData probeData;
    @SerializedName("probeSources")
    @Expose
    private ProbeSources probeSources;

    public Anomalies getAnomalies() {
        return anomalies;
    }

    public void setAnomalies(Anomalies anomalies) {
        this.anomalies = anomalies;
    }

    public ClassificationData getClassificationData() {
        return classificationData;
    }

    public void setClassificationData(ClassificationData classificationData) {
        this.classificationData = classificationData;
    }

    public ProbeData getProbeData() {
        return probeData;
    }

    public void setProbeData(ProbeData probeData) {
        this.probeData = probeData;
    }

    public ProbeSources getProbeSources() {
        return probeSources;
    }

    public void setProbeSources(ProbeSources probeSources) {
        this.probeSources = probeSources;
    }

}
