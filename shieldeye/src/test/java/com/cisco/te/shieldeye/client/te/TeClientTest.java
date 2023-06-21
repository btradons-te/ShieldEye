package com.cisco.te.shieldeye.client.te;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import com.beust.ah.A;
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
        List<Long> timeFrame = new ArrayList<>();
        long oneHour = 60*60;
        timeFrame.add(1687336162L -7* oneHour);
        timeFrame.add(1687336162L + 300);
        teClient.getTEtest(timeFrame, "10.56.197.79");
    }
}