
package com.cisco.te.shieldeye.sdavc.client.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MatchedRules {

    @SerializedName("deviceType")
    @Expose
    private DeviceType deviceType;
    @SerializedName("hardwareManufacturer")
    @Expose
    private HardwareManufacturer hardwareManufacturer;
    @SerializedName("hardwareModel")
    @Expose
    private HardwareModel hardwareModel;
    @SerializedName("operatingSystem")
    @Expose
    private OperatingSystem operatingSystem;

    public DeviceType getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(DeviceType deviceType) {
        this.deviceType = deviceType;
    }

    public HardwareManufacturer getHardwareManufacturer() {
        return hardwareManufacturer;
    }

    public void setHardwareManufacturer(HardwareManufacturer hardwareManufacturer) {
        this.hardwareManufacturer = hardwareManufacturer;
    }

    public HardwareModel getHardwareModel() {
        return hardwareModel;
    }

    public void setHardwareModel(HardwareModel hardwareModel) {
        this.hardwareModel = hardwareModel;
    }

    public OperatingSystem getOperatingSystem() {
        return operatingSystem;
    }

    public void setOperatingSystem(OperatingSystem operatingSystem) {
        this.operatingSystem = operatingSystem;
    }

}
