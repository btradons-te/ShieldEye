package com.cisco.te.shieldeye.client.te;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.time.Duration;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;



public class TeClient {

    public static final String STATUS_URL = "https://api.thousandeyes.com/v6/status.json";
    public static final String TESTS_URL = "https://api.thousandeyes.com/v6/tests.json";
    private HttpClient client;
    private JsonParser jsonParser;
    private String authToken;

    private TeParser teParser;

    public TeClient() {
        this.setHttpClient();
        jsonParser = JsonParserFactory.getJsonParser();
        authToken = "398f3c3d-d381-41d1-88c5-7b37094563e7"; // bearer auth token. Taken from ben TE account
        teParser = new TeParser();
    }

    public Timestamp getTeTimestamp() throws IOException, InterruptedException {
        HttpResponse<String> response =
                client.send(getRequest(STATUS_URL), HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200){
            System.out.println("ERROR: status code " + response.statusCode());
            return null;
        }
        Map<String,Object> result = jsonParser.parseMap(response.body());
     return new Timestamp(Long.parseLong(result.get("timestamp").toString()));
    }
    public Set<String> getTEtest() throws IOException, InterruptedException{
        HttpResponse<String> response =
                client.send(getRequestWithToken(TESTS_URL), HttpResponse.BodyHandlers.ofString());
        System.out.println(response.statusCode());
        System.out.println(response.body());
        if (response.statusCode() != 200) {
            System.out.println("ERROR: status code " + response.statusCode());
            return null;
        }
        TeTestList teTestList = teParser.ParseTestResult(response.body());
        Set<String> servers = getServersFromTestList(teTestList);
        System.out.println("Servers are: "+servers.toString());
        return servers;
    }

    public Set<String> getServersFromTestList(TeTestList teTestList){
        Set<String> serversSet = new HashSet<>();
        for (TeTest teTest: teTestList.tests){
            serversSet.add(teTest.server);
        }
        return serversSet;
    }



    private HttpRequest getRequest(String url)  {
        HttpRequest request = HttpRequest.newBuilder()
                                         .uri(URI.create(url))
                                         .timeout(Duration.ofMinutes(1))
                                         .header("Content-Type", "application/json")
                                         .GET().build();
        return request;
    }

    private HttpRequest getRequestWithToken(String url)  {
        HttpRequest request = HttpRequest.newBuilder()
                                         .uri(URI.create(url))
                                         .timeout(Duration.ofMinutes(1))
                                         .header("Content-Type", "application/json")
                                         .header("Authorization", "Bearer "+authToken)
                                         .GET().build();
        return request;
    }
    private void setHttpClient(){
        client = HttpClient.newBuilder()
                                      .version(HttpClient.Version.HTTP_2)
                                      .build();
    }


}
