package com.cisco.te.shieldeye.sdavc.client;

import com.cisco.te.shieldeye.sdavc.client.model.DetectedAnomaly;

import java.util.ArrayList;
import java.util.List;

public class UnauthPorts {
    List<String> portsList;
    public UnauthPorts(DetectedAnomaly detectedAnomaly, boolean showSensitive){
        portsList = new ArrayList<>();
        if (showSensitive){
        portsList.add(detectedAnomaly.getAnomalyProbes().get(0).getUnauthorizedTcpPorts().get(0));
        } else {
            String tmp = detectedAnomaly.getAnomalyProbes().get(0).getUnauthorizedTcpPorts().get(0).substring(0,1);
            for (int i =1; i<detectedAnomaly.getAnomalyProbes().get(0).getUnauthorizedTcpPorts().get(0).length();i++){
                tmp +=  "*";
            }
            portsList.add(tmp);
        }
    }

    public String toString(){
        String tmp = "";
        for (String p: portsList){
            tmp += " "+p;
        }
        return "Ports: " + tmp;
    }
}
