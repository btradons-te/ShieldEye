package com.cisco.te.shieldeye.client.te;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.sql.Timestamp;

import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;



class TeClientTest {

    TeClient teClient = new TeClient();
    @Test
    void getTeTimestamp() throws IOException, InterruptedException {
        Timestamp timestamp = teClient.getTeTimestamp();
        assertInstanceOf(Timestamp.class,timestamp);
    }

    @Test
    void getTEtest() throws IOException, InterruptedException {
        teClient.getTEtest();
    }
}