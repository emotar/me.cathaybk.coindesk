package me.cathaybk.coindesk;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;

import me.cathaybk.coindesk.component.JavascirptMessageConverter;
import me.cathaybk.coindesk.config.TestConfig;
import me.cathaybk.coindesk.controller.CoindeskController;
import me.cathaybk.coindesk.entity.CurrencyEntity;
import me.cathaybk.coindesk.service.CurrencyService;



/**
 * 測試呼叫資料轉換的API，並顯示其內容。
 * 
 * @author jerry
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TestPropertySource(locations = {"classpath:test.properties"})
@SpringApplicationConfiguration(classes = TestConfig.class)
public class CoindeskControllerTest {

	private MockMvc mockMvc;

	private CurrencyService currencyService;
	
	private Map<String, CurrencyEntity> currenciesMap;
	
	@Before
	public void init() {
		RestTemplate restTemplate = new RestTemplate();
		currencyService = Mockito.mock(CurrencyService.class);
		restTemplate.getMessageConverters().add(new JavascirptMessageConverter());		
		CoindeskController coindeskController = new CoindeskController(currencyService, restTemplate);		
		this.mockMvc = MockMvcBuilders.standaloneSetup(coindeskController).build();
				
		List<CurrencyEntity> currencies = new ArrayList<>();
		currencies.add(new CurrencyEntity("USD", "美金"));
		currencies.add(new CurrencyEntity("EUR", "歐元"));
		currencies.add(new CurrencyEntity("GBP", "英鎊"));
		currencies.add(new CurrencyEntity("TWD", "新台幣"));
		
		currenciesMap = currencies.stream().collect(Collectors.toMap(x -> x.getCode(), Function.identity()));
		
		ReflectionTestUtils.setField(coindeskController, "coinDeskUrl", "https://api.coindesk.com/v1/bpi/currentprice.json");
	}
	
	@Test
	public void test() throws Exception {
		when(currencyService.currencyMap()).thenReturn(currenciesMap);	

		this.mockMvc.perform(
				get("/coindesk").
				characterEncoding("utf-8"))
				.andDo(print())
				.andExpect(content().contentType(CurrencyControllerTest.JSON_UTF8))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.payload", notNullValue()));
	}

}
