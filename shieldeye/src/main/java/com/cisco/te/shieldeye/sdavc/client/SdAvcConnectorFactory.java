package com.cisco.te.shieldeye.sdavc.client;

import org.springframework.stereotype.Component;

/**
 * 
 * @author yurpopov
 *
 */

@Component
public class SdAvcConnectorFactory {

    public SdAvcConnector initSdAvcConnector(SdAvcServerLogin sdavcServerLogin) throws SdAvcClientException {
        return initSdAvcConnector(sdavcServerLogin, SdAvcConnector.HTTPS_PROTOCOL);
    }

    public SdAvcConnector initSdAvcConnector(SdAvcServerLogin sdavcServerLogin, String protocol) throws SdAvcClientException {
        SdAvcConnector sdAvcApi;
        if (sdavcServerLogin != null && sdavcServerLogin.isEnabled()) {
            sdAvcApi = new SdAvcConnector(sdavcServerLogin.getSdAvcIp(), sdavcServerLogin.getSdavcPort(),
                    sdavcServerLogin.getUserName(), sdavcServerLogin.getPassword(), protocol);
        } else {
            throw new SdAvcClientException("Sdavc loging not defined or disabled");
        }
        return sdAvcApi;
    }
}
