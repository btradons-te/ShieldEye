package com.cisco.te.shieldeye.sdavc.client;

import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.cisco.te.shieldeye.sdavc.client.model.DcsDevice;

public class SdAvcClientTest {

	@Spy
	@InjectMocks
	private SdAvcClient sdAvcClient;

	@Mock
	private SdAvcConnector sdAvcConnector;

	@Mock
	private SdAvcConnectorFactory sdAvcApiFactory;

	@Mock
	private SdAvcServerLogin sdAvcServerLogin;

	private AutoCloseable mocks;

	@BeforeClass
	public void init() {
		mocks = MockitoAnnotations.openMocks(this);
	}

	@AfterClass
	public void shutdown() throws Exception {
		mocks.close();
	}

	@Test
  public void getAnomaliesTest() throws Exception {
      when(sdAvcApiFactory.initSdAvcConnector(sdAvcServerLogin)).thenReturn(sdAvcConnector);
      when(sdAvcConnector.login()).thenReturn(StringUtils.EMPTY);
      when(sdAvcConnector.httpGet(sdAvcClient.buildUrl("apple", 120))).thenReturn(readJsonFile("src/test/resources/dcsDevicesResponse.json"));
      List<DcsDevice> devices = sdAvcClient.getDcsDevices(sdAvcServerLogin, "apple", 120);
      assertNotNull(devices);
      assertEquals(devices.size(), 2);
  }

	private String readJsonFile(String file) {
		String json = null;
		try {
			json = new String(Files.readAllBytes(Paths.get(file)));
		} catch (Exception e) {
			json = StringUtils.EMPTY;
		}
		return json;
	}
}
