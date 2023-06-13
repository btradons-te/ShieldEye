package com.cisco.te.shieldeye.sdavc.client;

public final class SdAvcClientConstants {

    private SdAvcClientConstants() {
    }

    public static final int SDAVC_PORT = 8443;
    //"/avc-sd-service/api/dcs/devices?segment=apple&periodInMinutes=120&size=8000&page=0"
    public static final String SDAVC_DCS_DEVICES = "/avc-sd-service/api/dcs/devices?segment=%s&periodInMinutes=%d&size=8000&page=0";
}
