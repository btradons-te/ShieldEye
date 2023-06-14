package com.cisco.te.shieldeye.sdavc.client;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cisco.te.shieldeye.sdavc.client.model.DcsDevice;
import com.google.gson.Gson;

/**
 *
 * @author yurpopov
 *
 */

@Component
public class SdAvcClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(SdAvcClient.class);
    
    @Autowired
    private SdAvcConnectorFactory sdavcConnectorFactory;  

	public List<DcsDevice> getTestDcsDevices(SdAvcServerLogin sdAvclogin, String segment, long periodInMinutes) throws SdAvcClientException {
        Gson gson = new Gson();
    	DcsDevice[] devices = gson.fromJson(testResponse, DcsDevice[].class);
        return Arrays.asList(devices);		
	}

    
    /**
     * 
     * @param sdavcServerLogin
     * @param segment
     * @param periodInMinutes - back in time from now period to query results, minutes
     * @return
     * @throws SdAvcClientException
     */
    public List<DcsDevice> getDcsDevices(SdAvcServerLogin sdavcServerLogin, String segment, long periodInMinutes) throws SdAvcClientException {
    	String url = buildUrl(segment, periodInMinutes);
    	DcsDevice[] devices = httpGetAsType(sdavcServerLogin, url, DcsDevice[].class);
        return Arrays.asList(devices);
    }

	String buildUrl(String segment, long periodInMinutes) {
		return String.format(SdAvcClientConstants.SDAVC_DCS_DEVICES, segment, periodInMinutes);
	}

	/**
	 * Init connector and login into SDAVC
	 * 
	 * @param sdavcServerLogin
	 * @return
	 * @throws SdAvcClientException
	 */
	private SdAvcConnector login(SdAvcServerLogin sdavcServerLogin) throws SdAvcClientException  {
		SdAvcConnector sdavcConnector;
		try {
			sdavcConnector = sdavcConnectorFactory.initSdAvcConnector(sdavcServerLogin);
			sdavcConnector.login();
		} catch (Exception e) {
			throw new SdAvcClientException("Error login into SDAVC %s:%s", e, sdavcServerLogin.getSdAvcIp(),
					String.valueOf(sdavcServerLogin.getSdavcPort()));
		}
		return sdavcConnector;
	}

    /**
     * Login into SDAVC and get Object of type T from the response
     * @param <T>
     * @param sdavcServerLogin
     * @param url
     * @param classOfT
     * @return
     * @throws SdAvcClientException
     */
    public <T> T httpGetAsType(SdAvcServerLogin sdavcServerLogin, String url, Class<T> classOfT)
            throws SdAvcClientException {

        SdAvcConnector sdavcConnector = login(sdavcServerLogin);
        return httpGetAsType(sdavcConnector, url, classOfT);
    }
    
    /**
     * Get Object of type T from the response after the login into sdavc
     * @param <T>
     * @param sdavcConnector
     * @param url
     * @param classOfT
     * @return
     * @throws SdAvcClientException
     */
    <T> T httpGetAsType(SdAvcConnector sdavcConnector, String url, Class<T> classOfT) throws SdAvcClientException {
        T response = null;
        String bodyAsString = httpGetAsJson(sdavcConnector, url);
        LOGGER.info("Recieved DCS devices respnse: {}", bodyAsString);
        try {
            Gson gson = new Gson();
            response = gson.fromJson(bodyAsString, (Type) classOfT);
        } catch (Exception e) {
        	throw new SdAvcClientException("Error parsing SDAVC response. URL: %s, response: %s", e, url, bodyAsString);
        }
        return response;
    }
	
	
    /**
     * Get response as JSON string
     * @param sdavcConnector
     * @param url
     * @return
     * @throws SdAvcClientException
     */
    public String httpGetAsJson(SdAvcConnector sdavcConnector, String url) throws SdAvcClientException {
        String bodyAsString = null;
        try {
            bodyAsString = sdavcConnector.httpGet(url);
        } catch (Exception e) {
        	throw new SdAvcClientException("Error getting response from SDAVC IP: %s, port %s, URL %s ", e, sdavcConnector.getServerIP(), String.valueOf(sdavcConnector.getAvcPort()), url);
        }
        return bodyAsString;
    }

    private String testResponse = "[\n"
    		+ "    {\n"
    		+ "    \"hitRate\": 2,\n"
    		+ "    \"key\": \"A0:36:00:DD:00:00\",\n"
    		+ "    \"lastClearTime\": \"1970-01-01T00:00:00Z\",\n"
    		+ "    \"lastHitsTime\": \"2023-03-20T22:29:32Z\",\n"
    		+ "    \"lastSyncTime\": \"1970-01-01T00:00:00Z\",\n"
    		+ "    \"metadata\":\n"
    		+ "        {\n"
    		+ "        \"anomalies\":\n"
    		+ "            {\n"
    		+ "            \"anomalyDecision\":\n"
    		+ "                [\n"
    		+ "                    {\n"
    		+ "                    \"anomalyType\": \"weakCredentialsDecision\",\n"
    		+ "                    \"confidence\": 100,\n"
    		+ "                    \"description\": \"weak credentials were identified\",\n"
    		+ "                    \"detectionID\":\n"
    		+ "                        [\n"
    		+ "                        \"1643\"\n"
    		+ "                        ],\n"
    		+ "                    \"severity\": 80\n"
    		+ "                    }\n"
    		+ "                ],\n"
    		+ "            \"detectedAnomalies\":\n"
    		+ "                [\n"
    		+ "                    {\n"
    		+ "                    \"anomalyProbes\":\n"
    		+ "                        [\n"
    		+ "                            {\n"
    		+ "                            \"detectionTime\":\n"
    		+ "                                [\n"
    		+ "                                \"1679351481846\"\n"
    		+ "                                ],\n"
    		+ "                            \"port\":\n"
    		+ "                                [\n"
    		+ "                                \"23\"\n"
    		+ "                                ],\n"
    		+ "                            \"protocol\":\n"
    		+ "                                [\n"
    		+ "                                \"telnet\"\n"
    		+ "                                ],\n"
    		+ "                            \"scanNames\":\n"
    		+ "                                [\n"
    		+ "                                \"WeakCredentialListScanner\"\n"
    		+ "                                ],\n"
    		+ "                            \"scannedIp\":\n"
    		+ "                                [\n"
    		+ "                                \"10.56.96.92\"\n"
    		+ "                                ],\n"
    		+ "                            \"version\":\n"
    		+ "                                [\n"
    		+ "                                \"\"\n"
    		+ "                                ],\n"
    		+ "                            \"weakPassword\":\n"
    		+ "                                [\n"
    		+ "                                \"lab\"\n"
    		+ "                                ],\n"
    		+ "                            \"weakUserName\":\n"
    		+ "                                [\n"
    		+ "                                \"lab\"\n"
    		+ "                                ]\n"
    		+ "                            }\n"
    		+ "                        ],\n"
    		+ "                    \"confidence\": 100,\n"
    		+ "                    \"detectionID\": \"1643\",\n"
    		+ "                    \"detectionTime\": 1679351481846,\n"
    		+ "                    \"detectionType\": \"weakCredentials\",\n"
    		+ "                    \"reason\": \"weak credentials were identified\",\n"
    		+ "                    \"sdavcReason\": \"weak credentials were identified\",\n"
    		+ "                    \"severity\": 80,\n"
    		+ "                    \"sources\":\n"
    		+ "                        [\n"
    		+ "                        \"protocol\",\n"
    		+ "                        \"weakPassword\",\n"
    		+ "                        \"port\",\n"
    		+ "                        \"scannedIp\",\n"
    		+ "                        \"scanNames\",\n"
    		+ "                        \"weakUserName\",\n"
    		+ "                        \"version\",\n"
    		+ "                        \"detectionTime\"\n"
    		+ "                        ]\n"
    		+ "                    }\n"
    		+ "                ]\n"
    		+ "            },\n"
    		+ "        \"classificationData\":\n"
    		+ "            {\n"
    		+ "            \"confidenceDeviceType\":\n"
    		+ "                [\n"
    		+ "                \"20\"\n"
    		+ "                ],\n"
    		+ "            \"confidenceHardwareManufacturer\":\n"
    		+ "                [\n"
    		+ "                \"20\"\n"
    		+ "                ],\n"
    		+ "            \"confidenceHardwareModel\":\n"
    		+ "                [\n"
    		+ "                \"20\"\n"
    		+ "                ],\n"
    		+ "            \"confidenceOperatingSystem\":\n"
    		+ "                [\n"
    		+ "                \"20\"\n"
    		+ "                ],\n"
    		+ "            \"conflictingTagsSource\":\n"
    		+ "                [\n"
    		+ "                \"{\\\"nbar\\\":[\\\"macAddress\\\",\\\"userAgent\\\",\\\"ip\\\"]}\"\n"
    		+ "                ],\n"
    		+ "            \"consistency\":\n"
    		+ "                [\n"
    		+ "                \" \"\n"
    		+ "                ],\n"
    		+ "            \"debug\":\n"
    		+ "                [\n"
    		+ "                \"{\\\"fifRate\\\":2,\\\"apiRate\\\":1,\\\"hitData\\\":[\\\"\\\"],\\\"ipData\\\":[\\\"{\\\\\\\"ip\\\\\\\":\\\\\\\"10.56.96.92\\\\\\\", \\\\\\\"cSeq\\\\\\\":1, \\\\\\\"sHitSeq\\\\\\\":1, \\\\\\\"lHitSeq\\\\\\\":2, \\\\\\\"hits\\\\\\\":2, \\\\\\\"cSrc\\\\\\\":\\\\\\\"selfLine\\\\\\\", \\\\\\\"othSrc\\\\\\\":\\\\\\\"vmLine,selfLine\\\\\\\", \\\\\\\"lVmMngSeq\\\\\\\":0}\\\"],\\\"value\\\":[\\\" \\\"]}\"\n"
    		+ "                ],\n"
    		+ "            \"deviceType\":\n"
    		+ "                [\n"
    		+ "                \"Mobile Device\"\n"
    		+ "                ],\n"
    		+ "            \"hardwareManufacturer\":\n"
    		+ "                [\n"
    		+ "                \"Apple, Inc.\"\n"
    		+ "                ],\n"
    		+ "            \"hardwareModel\":\n"
    		+ "                [\n"
    		+ "                \"iPhone 4\",\n"
    		+ "                \"Apple-iPhone\",\n"
    		+ "                \"Apple-Device\"\n"
    		+ "                ],\n"
    		+ "            \"matchedRules\":\n"
    		+ "                {\n"
    		+ "                \"deviceType\":\n"
    		+ "                    {\n"
    		+ "                    \"ruleId\": \"userAgentAppleIosExtractorRule\",\n"
    		+ "                    \"ruleType\": \"System\"\n"
    		+ "                    },\n"
    		+ "                \"hardwareManufacturer\":\n"
    		+ "                    {\n"
    		+ "                    \"ruleId\": \"userAgentAppleModelNameRule\",\n"
    		+ "                    \"ruleType\": \"System\"\n"
    		+ "                    },\n"
    		+ "                \"hardwareModel\":\n"
    		+ "                    {\n"
    		+ "                    \"ruleId\": \"userAgentAppleModelNameRule\",\n"
    		+ "                    \"ruleType\": \"System\"\n"
    		+ "                    },\n"
    		+ "                \"operatingSystem\":\n"
    		+ "                    {\n"
    		+ "                    \"ruleId\": \"userAgentAppleIosExtractorRule\",\n"
    		+ "                    \"ruleType\": \"System\"\n"
    		+ "                    }\n"
    		+ "                },\n"
    		+ "            \"operatingSystem\":\n"
    		+ "                [\n"
    		+ "                \"iOS 7 (7.0.6)\",\n"
    		+ "                \"iOS 7\",\n"
    		+ "                \"iOS\"\n"
    		+ "                ],\n"
    		+ "            \"pktDataPrev\":\n"
    		+ "                [\n"
    		+ "                \"Device ID = c9300-stack, FIF rate = 2, API rate = 1\"\n"
    		+ "                ],\n"
    		+ "            \"pluginId\":\n"
    		+ "                [\n"
    		+ "                \"64.1\"\n"
    		+ "                ]\n"
    		+ "            },\n"
    		+ "        \"probeData\":\n"
    		+ "            {\n"
    		+ "            \"ip\": \"10.56.96.92\",\n"
    		+ "            \"macAddress\": \"A0:36:00:DD:00:00\",\n"
    		+ "            \"userAgent\": \"itunesstored/1.0 iOS/7.0.6 model/iPhone3,1 (4; dt:27)\",\n"
    		+ "            \"vrf\": \"global\",\n"
    		+ "            \"weakCredentialsScanResult\":\n"
    		+ "                [\n"
    		+ "                \"{\\\"scanResults\\\":[{\\\"protocol\\\":\\\"ssh\\\",\\\"version\\\":\\\"v1/v2\\\",\\\"port\\\":22,\\\"scanIds\\\":[\\\"WeakCredentialListScanner\\\"],\\\"scanTime\\\":1679352281125},{\\\"protocol\\\":\\\"telnet\\\",\\\"version\\\":\\\"\\\",\\\"port\\\":23,\\\"scanIds\\\":[\\\"WeakCredentialListScanner\\\"],\\\"scanTime\\\":1679352286082}],\\\"scannedIp\\\":\\\"10.56.96.92\\\",\\\"scanTime\\\":1679352286082,\\\"reachable\\\":true}\"\n"
    		+ "                ]\n"
    		+ "            },\n"
    		+ "        \"probeSources\":\n"
    		+ "            {\n"
    		+ "            \"ip\": \"nbar\",\n"
    		+ "            \"userAgent\": \"nbar\"\n"
    		+ "            }\n"
    		+ "        },\n"
    		+ "    \"probeChangeTime\": \"2023-03-20T22:31:21Z\",\n"
    		+ "    \"profilingTime\": \"2023-03-20T22:31:21Z\",\n"
    		+ "    \"segment\": \"apple\"\n"
    		+ "    },\n"
    		+ "    {\n"
    		+ "    \"hitRate\": 2,\n"
    		+ "    \"key\": \"A0:36:00:DD:00:01\",\n"
    		+ "    \"lastClearTime\": \"1970-01-01T00:00:00Z\",\n"
    		+ "    \"lastHitsTime\": \"2023-03-20T22:29:43Z\",\n"
    		+ "    \"lastSyncTime\": \"1970-01-01T00:00:00Z\",\n"
    		+ "    \"metadata\":\n"
    		+ "        {\n"
    		+ "        \"anomalies\":\n"
    		+ "            {\n"
    		+ "            \"anomalyDecision\":\n"
    		+ "                [\n"
    		+ "                    {\n"
    		+ "                    \"anomalyType\": \"unauthorizedPorts\",\n"
    		+ "                    \"confidence\": 100,\n"
    		+ "                    \"description\": \"unauthorized ports were identified\",\n"
    		+ "                    \"detectionID\":\n"
    		+ "                        [\n"
    		+ "                        \"1543\"\n"
    		+ "                        ],\n"
    		+ "                    \"severity\": 80\n"
    		+ "                    }\n"
    		+ "                ],\n"
    		+ "            \"detectedAnomalies\":\n"
    		+ "                [\n"
    		+ "                    {\n"
    		+ "                    \"anomalyProbes\":\n"
    		+ "                        [\n"
    		+ "                            {\n"
    		+ "                            \"scannedIp\":\n"
    		+ "                                [\n"
    		+ "                                \"10.56.96.93\"\n"
    		+ "                                ],\n"
    		+ "                            \"scanTcpNames\":\n"
    		+ "                                [\n"
    		+ "                                \"CustomOpenPortScanner\"\n"
    		+ "                                ],\n"
    		+ "                            \"unauthorizedTcpPorts\":\n"
    		+ "                                [\n"
    		+ "                                \"8443\"\n"
    		+ "                                ],\n"
    		+ "                            \"unauthorizedTcpPortsDetectionTime\":\n"
    		+ "                                [\n"
    		+ "                                \"1679351471488\"\n"
    		+ "                                ]\n"
    		+ "                            }\n"
    		+ "                        ],\n"
    		+ "                    \"confidence\": 100,\n"
    		+ "                    \"detectionID\": \"1543\",\n"
    		+ "                    \"detectionTime\": 1679351471488,\n"
    		+ "                    \"detectionType\": \"unauthorizedPorts\",\n"
    		+ "                    \"reason\": \"unauthorized ports were identified\",\n"
    		+ "                    \"sdavcReason\": \"unauthorized ports were identified\",\n"
    		+ "                    \"severity\": 80,\n"
    		+ "                    \"sources\":\n"
    		+ "                        [\n"
    		+ "                        \"unauthorizedTcpPortsDetectionTime\",\n"
    		+ "                        \"scannedIp\",\n"
    		+ "                        \"scanTcpNames\",\n"
    		+ "                        \"unauthorizedTcpPorts\"\n"
    		+ "                        ]\n"
    		+ "                    }\n"
    		+ "                ]\n"
    		+ "            },\n"
    		+ "        \"classificationData\":\n"
    		+ "            {\n"
    		+ "            \"confidenceDeviceType\":\n"
    		+ "                [\n"
    		+ "                \"20\"\n"
    		+ "                ],\n"
    		+ "            \"confidenceHardwareManufacturer\":\n"
    		+ "                [\n"
    		+ "                \"20\"\n"
    		+ "                ],\n"
    		+ "            \"confidenceHardwareModel\":\n"
    		+ "                [\n"
    		+ "                \"20\"\n"
    		+ "                ],\n"
    		+ "            \"confidenceOperatingSystem\":\n"
    		+ "                [\n"
    		+ "                \"20\"\n"
    		+ "                ],\n"
    		+ "            \"conflictingTagsSource\":\n"
    		+ "                [\n"
    		+ "                \"{\\\"nbar\\\":[\\\"macAddress\\\",\\\"userAgent\\\",\\\"ip\\\"]}\"\n"
    		+ "                ],\n"
    		+ "            \"consistency\":\n"
    		+ "                [\n"
    		+ "                \" \"\n"
    		+ "                ],\n"
    		+ "            \"debug\":\n"
    		+ "                [\n"
    		+ "                \"{\\\"fifRate\\\":2,\\\"apiRate\\\":1,\\\"hitData\\\":[\\\"\\\"],\\\"ipData\\\":[\\\"{\\\\\\\"ip\\\\\\\":\\\\\\\"10.56.96.93\\\\\\\", \\\\\\\"cSeq\\\\\\\":1, \\\\\\\"sHitSeq\\\\\\\":1, \\\\\\\"lHitSeq\\\\\\\":2, \\\\\\\"hits\\\\\\\":2, \\\\\\\"cSrc\\\\\\\":\\\\\\\"selfLine\\\\\\\", \\\\\\\"othSrc\\\\\\\":\\\\\\\"vmLine,selfLine\\\\\\\", \\\\\\\"lVmMngSeq\\\\\\\":0}\\\"],\\\"value\\\":[\\\" \\\"]}\"\n"
    		+ "                ],\n"
    		+ "            \"deviceType\":\n"
    		+ "                [\n"
    		+ "                \"Mobile Device\"\n"
    		+ "                ],\n"
    		+ "            \"hardwareManufacturer\":\n"
    		+ "                [\n"
    		+ "                \"Apple, Inc.\"\n"
    		+ "                ],\n"
    		+ "            \"hardwareModel\":\n"
    		+ "                [\n"
    		+ "                \"iPhone 4\",\n"
    		+ "                \"Apple-iPhone\",\n"
    		+ "                \"Apple-Device\"\n"
    		+ "                ],\n"
    		+ "            \"matchedRules\":\n"
    		+ "                {\n"
    		+ "                \"deviceType\":\n"
    		+ "                    {\n"
    		+ "                    \"ruleId\": \"userAgentAppleIosExtractorRule\",\n"
    		+ "                    \"ruleType\": \"System\"\n"
    		+ "                    },\n"
    		+ "                \"hardwareManufacturer\":\n"
    		+ "                    {\n"
    		+ "                    \"ruleId\": \"userAgentAppleModelNameRule\",\n"
    		+ "                    \"ruleType\": \"System\"\n"
    		+ "                    },\n"
    		+ "                \"hardwareModel\":\n"
    		+ "                    {\n"
    		+ "                    \"ruleId\": \"userAgentAppleModelNameRule\",\n"
    		+ "                    \"ruleType\": \"System\"\n"
    		+ "                    },\n"
    		+ "                \"operatingSystem\":\n"
    		+ "                    {\n"
    		+ "                    \"ruleId\": \"userAgentAppleIosExtractorRule\",\n"
    		+ "                    \"ruleType\": \"System\"\n"
    		+ "                    }\n"
    		+ "                },\n"
    		+ "            \"operatingSystem\":\n"
    		+ "                [\n"
    		+ "                \"iOS 7 (7.0.6)\",\n"
    		+ "                \"iOS 7\",\n"
    		+ "                \"iOS\"\n"
    		+ "                ],\n"
    		+ "            \"pktDataPrev\":\n"
    		+ "                [\n"
    		+ "                \"Device ID = c9300-stack, FIF rate = 2, API rate = 1\"\n"
    		+ "                ],\n"
    		+ "            \"pluginId\":\n"
    		+ "                [\n"
    		+ "                \"64.1\"\n"
    		+ "                ]\n"
    		+ "            },\n"
    		+ "        \"probeData\":\n"
    		+ "            {\n"
    		+ "            \"ip\": \"10.56.96.93\",\n"
    		+ "            \"macAddress\": \"A0:36:00:DD:00:01\",\n"
    		+ "            \"nmapScanResult\":\n"
    		+ "                [\n"
    		+ "                \"{\\\"tcp\\\":{\\\"openPorts\\\":[\\\"8443\\\"],\\\"scanTime\\\":1679352276369,\\\"scanIds\\\":[\\\"CustomOpenPortScanner\\\"]},\\\"udp\\\":{\\\"openPorts\\\":[],\\\"scanTime\\\":1679352276648,\\\"scanIds\\\":[\\\"CustomOpenPortScanner\\\"]},\\\"scannedIp\\\":\\\"10.56.96.93\\\",\\\"scanTime\\\":1679351467483,\\\"reachable\\\":true}\"\n"
    		+ "                ],\n"
    		+ "            \"userAgent\": \"itunesstored/1.0 iOS/7.0.6 model/iPhone3,1 (4; dt:27)\",\n"
    		+ "            \"vrf\": \"global\"\n"
    		+ "            },\n"
    		+ "        \"probeSources\":\n"
    		+ "            {\n"
    		+ "            \"ip\": \"nbar\",\n"
    		+ "            \"userAgent\": \"nbar\"\n"
    		+ "            }\n"
    		+ "        },\n"
    		+ "    \"probeChangeTime\": \"2023-03-20T22:31:11Z\",\n"
    		+ "    \"profilingTime\": \"2023-03-20T22:31:11Z\",\n"
    		+ "    \"segment\": \"apple\"\n"
    		+ "    }\n"
    		+ "]";    
    
}