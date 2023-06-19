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
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.stereotype.Component;


@Component
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
    public List<String> getTEtest(List<Long> timeFrame) {
        try {
            HttpResponse<String> response = client.send(getRequestWithToken(TESTS_URL), HttpResponse.BodyHandlers.ofString());
//            System.out.println(response.statusCode());
//            System.out.println(response.body());
            if (response.statusCode() != 200) {
                System.out.println("ERROR: status code " + response.statusCode());
                return null;
            }
            TeTestList teTestList = teParser.ParseTestResult(response.body());
            teTestList.tests = teTestList.tests.stream().filter(t -> t.isRelevant(timeFrame.get(1))).toList();
            Set<String> servers = getServersFromTestList(teTestList);
            List<String> ipList = filterServers(servers);
        return ipList;
        }
        catch (Exception e){
            System.out.println(e.toString());
            return new ArrayList<>();
        }
    }

    private List<String> filterServers (Set<String> servers){
        Pattern pattern = Pattern.compile("([0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3})");
        Set<String> ipList = new HashSet<>();
        for (String server: servers){
            if (server == null) continue;
            Matcher m = pattern.matcher(server);
            if (m.find()){
                String ip = m.group(0);
                ipList.add(ip);
            }
        }
        return ipList.stream().toList();
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
