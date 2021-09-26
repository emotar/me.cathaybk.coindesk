package me.cathaybk.coindesk;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import me.cathaybk.coindesk.component.JavascirptMessageConverter;
import me.cathaybk.coindesk.dto.CoindeskApiDto;

/**
 * 測試呼叫coindesk API，並顯示其內容。
 * 
 * @author jerry
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class CoindeskApiTest {

	public static final String coinDeskUrl = "https://api.coindesk.com/v1/bpi/currentprice.json";
	
	private static final Logger logger = LoggerFactory.getLogger(CoindeskApiTest.class);
	
	@Test
	public void test() throws JsonProcessingException {
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(new JavascirptMessageConverter());
		CoindeskApiDto coindeskApiDto = restTemplate.getForObject(coinDeskUrl, CoindeskApiDto.class);
		assertNotNull(coindeskApiDto);
		logger.debug(new ObjectMapper().writeValueAsString(coindeskApiDto));
	}

}
