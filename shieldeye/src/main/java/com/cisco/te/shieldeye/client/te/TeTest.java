package com.cisco.te.shieldeye.client.te;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.NotImplementedException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TeTest {
    private static SimpleDateFormat df = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
    @JsonProperty("testName") // i.e "icmp_test_1"
    public String testName;

    @JsonProperty("type") // i.e "agent-to-server"
    public String type;

    @JsonProperty("server") // i.e "agent-to-server"
    public String server;

    @JsonProperty("createdDate") // i.e "agent-to-server"
    public String dateRaw;

    public boolean isSecScanCB() {
        return secScanCB;
    }

    public void setSecScanCB(boolean secScanCB) {
        this.secScanCB = secScanCB;
    }

    @JsonProperty("securityScanCheckBox") // i.e "agent-to-server"
    public boolean secScanCB;

    public String toString(){
        return String.format("Test: %s, type: %s, server: %s",testName,type,server);
    }

    public boolean isRelevant(long timeFrameEnd) {
        try {
            Date date = df.parse(this.dateRaw);
            long createdTestEpoch = date.getTime()/1000; // epoch time in second
            return createdTestEpoch < timeFrameEnd;
        } catch (Exception e){
            return true;
        }
    }

    public String normalizedServer(){
        throw new NotImplementedException();
    }
}
