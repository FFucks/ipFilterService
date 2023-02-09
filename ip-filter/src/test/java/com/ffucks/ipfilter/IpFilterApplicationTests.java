package com.ffucks.ipfilter;

import com.ffucks.ipfilter.models.IpFilterModel;
import com.ffucks.ipfilter.repository.IpFilterRepository;
import com.ffucks.ipfilter.services.IpFilterService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@SpringBootTest
public class IpFilterApplicationTests {

	private static final String SOURCE_IP = "192.168.0.1";
	private static final String DESTINATION_IP = "10.0.0.1";

	@Autowired
	IpFilterService ipFilterService;

	@MockBean
	IpFilterRepository ipFilterRepository;

	@BeforeEach
	public void init() {
		IpFilterModel ipFilterModel = new IpFilterModel(1L, "test", SOURCE_IP, DESTINATION_IP,"allow");
		Mockito.when(ipFilterRepository.findBySourceIpAndDestinationIp(ipFilterModel.getSourceIp(), ipFilterModel.getDestinationIp()))
				.thenReturn(ipFilterModel);
	}

	@Test
	public void service_verifyIp_OK() {
		boolean verifyIp = ipFilterService.verifyIp(SOURCE_IP);

		Assertions.assertTrue(verifyIp);
	}

	@Test
	public void service_verifyIp_NOK() {
		boolean verifyIp = ipFilterService.verifyIp("1921.168.0.155");

		Assertions.assertFalse(verifyIp);
	}

	@Test
	public void service_findByIp_OK() {
		Assertions.assertEquals(1L, ipFilterService.findByIp(SOURCE_IP, DESTINATION_IP).getId());
	}

	@Test
	public void service_findByIp_NOK() {
		Assertions.assertNotEquals(2L, ipFilterService.findByIp(SOURCE_IP, DESTINATION_IP).getId());
	}

	@Test
	public void service_getAllFilters_OK() {
		List<IpFilterModel> ipFilterModelList = Arrays.asList(
				new IpFilterModel(1L, "test", "192.168.0.5", "192.168.0.6","allow"),
				new IpFilterModel(2L, "test2", "127.0.0.1", "127.0.0.2", "deny")
		);

		Mockito.when(ipFilterService.getAllFilters()).thenReturn(ipFilterModelList);
		Assertions.assertEquals(ipFilterModelList, ipFilterService.getAllFilters());
	}

	@Test
	public void service_getAllFilters_NOK() {
		List<IpFilterModel> ipFilterModelList = Arrays.asList(
				new IpFilterModel(1L, "test", "192.168.0.5", "192.168.0.6", "allow"),
				new IpFilterModel(2L, "test2", "192.168.0.9", "192.168.0.10","deny")
		);

		Mockito.when(ipFilterService.getAllFilters()).thenReturn(ipFilterModelList);
		Assertions.assertNotEquals(new ArrayList<>(), ipFilterService.getAllFilters());
	}

	@Test
	public void service_saveIpFilter_OK() {
		ipFilterService.saveIpFilter(new IpFilterModel(2L, "test2", "192.168.0.9", "192.168.0.10", "deny"));
		Mockito.verify(ipFilterRepository, Mockito.times(1)).save(Mockito.any(IpFilterModel.class));
	}

	@Test
	public void service_delete_OK() {
		ipFilterService.delete(SOURCE_IP, DESTINATION_IP);
		Mockito.verify(ipFilterRepository, Mockito.times(1)).deleteBySourceIpAndDestinationIp(SOURCE_IP, DESTINATION_IP);
	}

}
