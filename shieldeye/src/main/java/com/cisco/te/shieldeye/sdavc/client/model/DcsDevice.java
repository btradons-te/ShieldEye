
package com.cisco.te.shieldeye.sdavc.client.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DcsDevice {

    @SerializedName("hitRate")
    @Expose
    private Integer hitRate;
    @SerializedName("key")
    @Expose
    private String key;
    @SerializedName("lastClearTime")
    @Expose
    private String lastClearTime;
    @SerializedName("lastHitsTime")
    @Expose
    private String lastHitsTime;
    @SerializedName("lastSyncTime")
    @Expose
    private String lastSyncTime;
    @SerializedName("metadata")
    @Expose
    private Metadata metadata;
    @SerializedName("probeChangeTime")
    @Expose
    private String probeChangeTime;
    @SerializedName("profilingTime")
    @Expose
    private String profilingTime;
    @SerializedName("segment")
    @Expose
    private String segment;

    public Integer getHitRate() {
        return hitRate;
    }

    public void setHitRate(Integer hitRate) {
        this.hitRate = hitRate;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getLastClearTime() {
        return lastClearTime;
    }

    public void setLastClearTime(String lastClearTime) {
        this.lastClearTime = lastClearTime;
    }

    public String getLastHitsTime() {
        return lastHitsTime;
    }

    public void setLastHitsTime(String lastHitsTime) {
        this.lastHitsTime = lastHitsTime;
    }

    public String getLastSyncTime() {
        return lastSyncTime;
    }

    public void setLastSyncTime(String lastSyncTime) {
        this.lastSyncTime = lastSyncTime;
    }

    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    public String getProbeChangeTime() {
        return probeChangeTime;
    }

    public void setProbeChangeTime(String probeChangeTime) {
        this.probeChangeTime = probeChangeTime;
    }

    public String getProfilingTime() {
        return profilingTime;
    }

    public void setProfilingTime(String profilingTime) {
        this.profilingTime = profilingTime;
    }

    public String getSegment() {
        return segment;
    }

    public void setSegment(String segment) {
        this.segment = segment;
    }

}
