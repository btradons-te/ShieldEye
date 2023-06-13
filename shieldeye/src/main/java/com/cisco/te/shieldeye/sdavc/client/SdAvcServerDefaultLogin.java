package com.cisco.te.shieldeye.sdavc.client;

/**
 * @author yurpopov
 *
 */
public class SdAvcServerDefaultLogin implements SdAvcServerLogin {

    private String sdAvcIp;
    private String userName;
    private String password;
    private boolean enabled = true;
    private int sdavcPort;
    private int serviceCacheAging;

    /**
     * @param sdAvcIp
     *            the server IP to set
     */
    public SdAvcServerLogin setSdAvcIp(String sdAvcIp) {
        this.sdAvcIp = sdAvcIp;
        return this;
    }

    /**
     * @param userName
     *            the userName to set
     */
    public SdAvcServerLogin setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    /**
     * Set not encrypted password
     *
     * @param password
     *            the password to set
     */
    public SdAvcServerLogin setPassword(String password) {
        this.password = password;
        return this;
    }

    /**
     * @param enabled
     *            the enabled to set
     */
    public SdAvcServerLogin setEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    /**
     * set aging time in msec
     *
     * @return
     */
    public SdAvcServerLogin setServiceCacheAging(int serviceCacheAging) {
        this.serviceCacheAging = serviceCacheAging;
        return this;
    }

    /**
     * @return the containerIP
     */
    @Override
    public String getSdAvcIp() {
        return sdAvcIp;
    }

    /**
     * @return the userName
     */
    @Override
    public String getUserName() {
        return userName;
    }

    /**
     * @return the password
     */
    @Override
    public String getPassword() {
        return password;
    }

    /**
     * @return the enabled
     */
    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public int getServiceCacheAging() {
        return serviceCacheAging;
    }

    /**
     * get Sdavc port
     *
     * @return
     */
    @Override
    public int getSdavcPort() {
        return sdavcPort;
    }

    /**
     * Set sdavc port
     *
     * @param sdavcPort
     */
    public SdAvcServerLogin setSdavcPort(int sdavcPort) {
        this.sdavcPort = sdavcPort;
        return this;
    }
}
