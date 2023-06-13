package com.cisco.te.shieldeye.client.te;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class TeTestList {
    @JsonProperty("test") // i.e "agent-to-server"
    public List<TeTest> tests;
}