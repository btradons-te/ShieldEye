package com.cisco.te.shieldeye.client.te;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Map;

public class TeParser {
    private ObjectMapper mapper;
    public TeParser(){
        mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false); // This configure mapper do ignore non relevant fields.
    }

    public TeTestList ParseTestResult(String result){
        TeTestList teTestList = null;
        try {
            teTestList = mapper.readValue(result, TeTestList.class);
        } catch (Exception e){
            System.out.println(e.toString());
        }
        return  teTestList;
    }
}
