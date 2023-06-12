package com.cisco.te.shieldeye.sdavc.client;

public interface SdAvcServerLogin {
    /**
     * Getter for the 'containerIP' attribute.
     *
     */
    String getSdAvcIp();

    /**
     * Get sdavc port
     *
     * @return
     */
    int getSdavcPort();

    /**
     * Getter for the 'userName' attribute.
     *
     */
    String getUserName();

    /**
     * Get not encrypted password
     *
     * @return
     */
    String getPassword();

    /**
     * is server enabled
     *
     * @return
     */
    boolean isEnabled();

    /**
     * server cach aging in msec
     */
    int getServiceCacheAging();
}
