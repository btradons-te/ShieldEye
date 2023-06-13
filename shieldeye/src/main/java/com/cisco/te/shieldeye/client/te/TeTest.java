package com.cisco.te.shieldeye.client.te;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.NotImplementedException;

public class TeTest {
    @JsonProperty("testName") // i.e "icmp_test_1"
    public String testName;

    @JsonProperty("type") // i.e "agent-to-server"
    public String type;

    @JsonProperty("server") // i.e "agent-to-server"
    public String server;

    public String toString(){
        return String.format("Test: %s, type: %s, server: %s",testName,type,server);
    }

    public String normalizedServer(){
        throw new NotImplementedException();
    }
}
