package com.cisco.te.shieldeye.sdavc.client;

public class SdAvcClientException extends RuntimeException {

    private static final String SDAVC_CLIENT_EXCEPTION = "Unknown SDAVC exception";
    
    public SdAvcClientException() {
        super(SDAVC_CLIENT_EXCEPTION);
    }

    public SdAvcClientException(String message) {
        super(message);
    }

    public SdAvcClientException(String message, String ... params) {
        super(String.format(message, (Object[]) params));
    }

    public SdAvcClientException(String message, Throwable cause, String ... params) {
        super(String.format(message, (Object[]) params), cause);
    }

    public SdAvcClientException(Throwable cause) {
        super(SDAVC_CLIENT_EXCEPTION, cause);
    }
}
