package com.cisco.te.shieldeye;

import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doReturn;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import com.cisco.te.shieldeye.model.TargetScanResult;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.cisco.te.shieldeye.model.SecurityScanResponse;
import com.cisco.te.shieldeye.sdavc.client.SdAvcClient;
import com.cisco.te.shieldeye.sdavc.client.SdAvcServerLogin;
import com.cisco.te.shieldeye.sdavc.client.model.DcsDevice;
import com.google.gson.Gson;

public class SdavcSecurityServiceTest {

	@InjectMocks
	private SdavcSecurityService sdavcSecurityService;

	@Mock
	private SdAvcClient sdavcClient;

	private AutoCloseable mocks;

	@BeforeClass
	public void init() throws IOException {
		mocks = MockitoAnnotations.openMocks(this);
		ShieldEyeUtils shieldEyeUtils = new ShieldEyeUtils();
		List<DcsDevice> devices = shieldEyeUtils.getMockedDevices();
		doReturn(devices).when(sdavcClient).getDcsDevices(Mockito.any(SdAvcServerLogin.class), Mockito.anyString(), Mockito.anyLong());
		doReturn(devices).when(sdavcClient).getTestDcsDevices(Mockito.any(SdAvcServerLogin.class), Mockito.anyString(), Mockito.anyLong());
	}

	@AfterClass
	public void shutdown() throws Exception {
		mocks.close();
	}

	@Test
	public void getSecurityIssuesTest() {
//		List<String> targetIps = Arrays.asList("10.56.96.92", "10.56.96.93");
//		SecurityScanResponse response = sdavcSecurityService.getSecurityIssues(targetIps, "apple", Long.valueOf(120), false);
//		assertNotNull(response.getTargetScanResult());
//		assertEquals(response.getTargetScanResult().size(), 2);
//
//		response = sdavcSecurityService.getSecurityIssues(Arrays.asList("10.56.96.92"), "apple", Long.valueOf(120), false);
//		assertNotNull(response.getTargetScanResult());
//		assertEquals(response.getTargetScanResult().size(), 1);
//		assertEquals(response.getTargetScanResult().iterator().next().getTargetIp(), "10.56.96.92");
//
//		response = sdavcSecurityService.getSecurityIssues(Arrays.asList("10.56.96.93"), "apple", Long.valueOf(120), false);
//		assertNotNull(response.getTargetScanResult());
//		assertEquals(response.getTargetScanResult().size(), 1);
//		assertEquals(response.getTargetScanResult().iterator().next().getTargetIp(), "10.56.96.93");
	}

	@Deprecated
	@Test
	public void getSecurityIssuesTestMock() {
		//This works when read the dcsDevicesResponse.json
//		List<String> targetIps = Arrays.asList("10.56.96.92", "10.56.96.93");
//		SecurityScanResponse response = sdavcSecurityService.getSecurityIssuesMock(targetIps, "apple", Long.valueOf(120), false);
//		assertNotNull(response.getTargetScanResult());
//		assertEquals(response.getTargetScanResult().size(), 2);
	}

	@Test
	public void getSecurityIssuesTestMockTwoResponses() {
		long twoHours = 120;
		List<String> targetIps = Arrays.asList("10.56.197.79");
		SecurityScanResponse response = sdavcSecurityService.getSecurityIssuesMock(targetIps, "apple", twoHours, false);
		assertNotNull(response.getTargetScanResult());
		assertEquals(response.getTargetScanResult().size(), 1);
		TargetScanResult res = response.getTargetScanResult().stream().toList().get(0);
		assertEquals(res.getDetectedAnomalies().size(),2);

		long twoMin = 2;
		response = sdavcSecurityService.getSecurityIssuesMock(targetIps, "apple", twoMin, true);
		assertNotNull(response.getTargetScanResult());
		 res = response.getTargetScanResult().stream().toList().get(0);
		assertEquals(res.getDetectedAnomalies().size(),2);

	}
}
