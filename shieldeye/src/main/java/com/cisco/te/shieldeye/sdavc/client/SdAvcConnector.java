package com.cisco.te.shieldeye.sdavc.client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.InetAddressValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

/**
 *
 * @author yurpopov
 *
 */
public class SdAvcConnector extends HttpsUriConnectionTrustAllServers {
    private String serverIP;
    private String sessionId = null;
    private int avcPort = 9090;
    private int dashboardPort = 8080;
    private String avcBaseUrl;
    private String userName = "";
    private String serverPass = "";
    private String protocol = HTTPS_PROTOCOL;

    public static final String HTTPS_PROTOCOL = "https://";
    public static final String HTTP_PROTOCOL = "http://";
    public static final int HTTP_URL_CONN_TIMEOUT = 30000;

    private static final Logger LOGGER = LoggerFactory.getLogger(SdAvcConnector.class);

    private static final String LOGIN_MESSAGE = "This login operation uses the hosting platform's SSH credentials";
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String HTTP_RESPONSE_PARSING_ERROR = "Error converting json response into object. Object: %s, response: %s";
    private static final String HTTP_FAILED_TO_GET_RESPONSE = "Failed to get for URL: %s";


    public SdAvcConnector(String ip, int port, String username, String password, String protocol) {
        this.serverIP = ip;
        this.userName = username;
        this.serverPass = password;
        this.dashboardPort = port;
        this.avcPort = port;
        this.protocol = protocol;
        this.avcBaseUrl = getBaseUrl(this.protocol,this.serverIP,this.avcPort);
    }

    private String getBaseUrl(String protocol, String ip, int port) {
        String baseUrl;
        if (InetAddressValidator.getInstance().isValidInet6Address(ip)) {
            LOGGER.info("The SDAVC client IP address {} is a valid IPv6 address", ip);
            baseUrl = protocol + "[" + ip + "]:" + port;
        } else {
            //could be IpV4 or a Hostname
            LOGGER.info("The SDAVC client IP/Hostname address is: {}", ip);
            baseUrl = protocol + ip + ":" + port;
        }
        return baseUrl;
    }    
    
    public int getAvcPort() {
        return avcPort;
    }

    public String getServerIP() {
        return serverIP;
    }

    public String login() throws Exception {
        return login(false);
    }

    /**
     * search for the sd_avc attribute
     */
    private String login(boolean force) throws Exception {
        return login(this.userName, this.serverPass, force);
    }

    /**
     * login to sdavc application and get a token
     */
    @SuppressWarnings("boxing")
    protected String login(String username, String password, boolean force) throws Exception {
        if (!force && this.sessionId != null) {
            return this.sessionId;
        }

        // trying to login via rest login support since 2.1.0
        this.sessionId = loginViaRest(username, password);

        if (this.sessionId == null) {
            throw new SdAvcClientException("Failed to login into SDAVC server", HTTPS_PROTOCOL,
                    getServerIP(), String.valueOf(dashboardPort));
        }
        return sessionId;
    }

    private String loginViaRest(String username, String password) throws Exception {
        String urlParameters = "username=" + username + "&password=" + password;

        byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
        int postDataLength = postData.length;
        String baseUrl = getBaseUrl(HTTPS_PROTOCOL,getServerIP(),dashboardPort);
        String request = baseUrl + "/avc-sd-service/external-api/login";
        URL url = new URL(request);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setInstanceFollowRedirects(false);
        conn.setRequestMethod("POST");
        conn.setRequestProperty(CONTENT_TYPE, "application/x-www-form-urlencoded");
        conn.setRequestProperty("charset", "utf-8");
        conn.setRequestProperty("Content-Length", Integer.toString(postDataLength));
        conn.setUseCaches(false);
        // set 30 second
        conn.setReadTimeout(HTTP_URL_CONN_TIMEOUT);
        conn.setConnectTimeout(HTTP_URL_CONN_TIMEOUT);
        try (DataOutputStream wr = new DataOutputStream(conn.getOutputStream())) {
            wr.write(postData);
        }

        String response = getConnectionResponse(conn);

        if (StringUtils.isBlank(response)) {
            return null;
        }

        JsonObject res = convertToJsonObjet(response);

        if (res.has("token")) {
            return res.get("token").getAsString();
        }
        return null;
    }

    /**
     * convert Http connection response to json object
     */
    public JsonObject convertToJsonObjet(String response) throws Exception {
        return convertResponseToJson(response, JsonObject.class);
    }

    /**
     * convert response to json data
     */
    public <T> T convertResponseToJson(String response, Class<T> classOfT) throws Exception {
        Gson gson = new Gson();
        T jsonArray;
        try {
            jsonArray = gson.fromJson(response, (Type) classOfT);
        } catch (Exception e) {
            throw new SdAvcClientException(HTTP_RESPONSE_PARSING_ERROR, e, String.valueOf(classOfT), response);
        }

        return jsonArray;
    }

    public <T> Map<String, T> convertResponseToMap(String response) throws Exception {
        Gson gson = new Gson();
        Map<String, T> map = new HashMap<>();
        try {
            Type type = new TypeToken<Map<String, T>>() {
            }.getType();
            map = gson.fromJson(response, type);
        } catch (Exception e) {
            throw new SdAvcClientException(HTTP_RESPONSE_PARSING_ERROR, e, "Map<String, T>", response);
        }
        return map;
    }

    /**
     * perform Https Get action
     */
    public String httpGet(String urlToRead) throws Exception {
        HttpURLConnection conn;
        if (protocol.equals(HTTPS_PROTOCOL)) {
            conn = getConnection(urlToRead, "GET");
        } else {
            conn = getConnectionNonSecured(urlToRead, "GET");
        }

        String response = getConnectionResponse(conn);

        if (response.contains(LOGIN_MESSAGE)) {
            login(true);
            response = getConnectionResponse(conn);
        }

        if (response == null) {
            throw new SdAvcClientException(HTTP_FAILED_TO_GET_RESPONSE, urlToRead);
        }
        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace("Response result for GET url {}: {}", urlToRead, response);
        }
        return response;
    }

    public String httpPost(String urlToRead, String data) throws Exception {
        return httpMethod(urlToRead, data, "POST", "application/json");
    }
    
    public String httpPostUrlEncoded(String urlToRead, String data) throws Exception {
        return httpMethodUrlEncoded(urlToRead, data, "POST",  "application/x-www-form-urlencoded");
    }

    public String httpPut(String urlToRead, String data) throws Exception {
        return httpMethod(urlToRead, data, "PUT",  "application/json");
    }
    
    private String httpMethodUrlEncoded(String urlToRead, String urlParameters, String httpMessage, String type)
            throws Exception {
        byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
        int postDataLength = postData.length;

        HttpURLConnection conn = getConnection(urlToRead, httpMessage);
        conn.setDoOutput(true);
        conn.setInstanceFollowRedirects(false);

        conn.setRequestProperty(CONTENT_TYPE, "application/x-www-form-urlencoded");
        conn.setRequestProperty("charset", "utf-8");
        conn.setRequestProperty("Content-Length", Integer.toString(postDataLength));
        conn.setUseCaches(false);
        // set 30 second
        conn.setReadTimeout(HTTP_URL_CONN_TIMEOUT);
        conn.setConnectTimeout(HTTP_URL_CONN_TIMEOUT);

        try (DataOutputStream wr = new DataOutputStream(conn.getOutputStream())) {
            wr.write(postData);
        }

        String response = getConnectionResponse(conn);

        // reach login page
        if (response.contains(LOGIN_MESSAGE)) {
            login(true);
            response = getConnectionResponse(conn);
        }

        if (response == null) {
            throw new SdAvcClientException(HTTP_FAILED_TO_GET_RESPONSE, urlToRead);
        }

        return response;
    }

    public String httpMethod(String urlToRead, String data, String httpMessage, String type) throws Exception {        
        HttpURLConnection conn = getConnection(urlToRead, httpMessage);

        conn.setDoOutput(true);
        conn.setRequestMethod(httpMessage);
        conn.setRequestProperty(CONTENT_TYPE, type);
        conn.setRequestProperty("Content-Length", String.valueOf(data));
        // set 30 second
        conn.setReadTimeout(HTTP_URL_CONN_TIMEOUT);
        conn.setConnectTimeout(HTTP_URL_CONN_TIMEOUT);

        String response = null;
        OutputStream os = conn.getOutputStream();
        try {
            os.write(data.getBytes());

            response = getConnectionResponse(conn);

            // reach login page
            if (response.contains(LOGIN_MESSAGE)) {
                login(true);
                response = getConnectionResponse(conn);
            }

            if (response == null) {
                throw new SdAvcClientException(HTTP_FAILED_TO_GET_RESPONSE, urlToRead);
            }
        } finally {
            os.close();
        }
        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace("Response result for {} url {}: {}", httpMessage, urlToRead, response);
        }
        return response;
    }

    /**
     * https connection via HttpUrlConnection class
     */
    private HttpURLConnection getConnection(String avcUrl, String requestedMethod) throws Exception {
        login(false);
        URL url = new URL(this.avcBaseUrl + avcUrl);
        HttpURLConnection con;

        con = (HttpsURLConnection) url.openConnection();
        ((HttpsURLConnection) con).setHostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        });

        con.setRequestMethod(requestedMethod);
        // 2.0.0
        con.setRequestProperty("Cookie", "JSESSIONID=" + sessionId);
        // starting from 2.1.0 use token
        con.setRequestProperty("Authorization", sessionId);

        return con;
    }

    /**
     * https connection via HttpUrlConnection class
     */
    private HttpURLConnection getConnectionNonSecured(String avcUrl, String requestedMethod) throws Exception {
        URL url = new URL(this.avcBaseUrl + avcUrl);
        HttpURLConnection con;

        con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod(requestedMethod);

        return con;
    }

    /**
     * return response for given http connection
     */
    public String getConnectionResponse(HttpURLConnection conn) throws Exception {
        StringBuilder result = new StringBuilder();
        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        rd.close();
        return result.toString();
    }
}
