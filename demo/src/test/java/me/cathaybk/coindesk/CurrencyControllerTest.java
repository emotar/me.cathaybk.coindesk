package me.cathaybk.coindesk;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import me.cathaybk.coindesk.controller.CurrencyController;
import me.cathaybk.coindesk.dto.CurrencyResponseDto;
import me.cathaybk.coindesk.entity.CurrencyEntity;
import me.cathaybk.coindesk.service.CurrencyService;


@RunWith(MockitoJUnitRunner.class)
public class CurrencyControllerTest {

	private MockMvc mockMvc;

	@Mock
	private CurrencyService currencyService;

	private Map<String, CurrencyEntity> currencies;
	
	public static final String JSON_UTF8 = "application/json;charset=UTF-8";
	
	@Before
	public void init() {
		CurrencyEntity twd = new CurrencyEntity();
		twd.setChineseName("台幣");
		twd.setCode("TWD");

		CurrencyEntity usd = new CurrencyEntity();
		usd.setChineseName("美金");
		usd.setCode("USD");
		
		
		CurrencyEntity gbp = new CurrencyEntity();
		gbp.setChineseName("英鎊");
		gbp.setCode("GBP");
		
		CurrencyEntity eur = new CurrencyEntity();
		eur.setChineseName("歐元");
		eur.setCode("EUR");
		
		List<CurrencyEntity> currs = new ArrayList<>();
		currs.add(twd);
		currs.add(usd);
		currs.add(gbp);
		currs.add(eur);
		
		currencies = currs.stream().collect(Collectors.toMap(x -> x.getCode(), Function.identity()));

		this.mockMvc = MockMvcBuilders.standaloneSetup(new CurrencyController(currencyService)).build();
	
	}
	
	/**
	 * 測試呼叫查詢幣別對應表資料API，並顯示其內容
	 * 
	 * @throws Exception
	 */
	//@Test
	public void testCurrencyByCode() throws Exception {

		when(currencyService.all()).thenReturn(new ArrayList<>(currencies.values()));
		when(currencyService.find("TWD")).thenReturn(Optional.ofNullable(currencies.get("TWD")));

		this.mockMvc.perform(
				get("/currencies").
				param("code", "TWD").
				characterEncoding("utf-8"))
				.andDo(print())
				.andExpect(content().contentType(CurrencyControllerTest.JSON_UTF8))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.payload.code", equalTo("TWD")));
		
	}
	
	
	
	/**
	 * 測試呼叫查詢幣別對應表資料API，並顯示其內容
	 * 
	 * @throws Exception
	 */
	//@Test
	public void testAllCurrency() throws Exception {
		when(currencyService.all()).thenReturn(new ArrayList<>(currencies.values()));
		this.mockMvc.perform(get("/currencies")).andDo(print())
			.andExpect(content().contentType(CurrencyControllerTest.JSON_UTF8))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.payload", notNullValue()));
		
	}
	
	/**
	 * 測試呼叫新增幣別對應表資料API。
	 * 
	 * @throws Exception
	 */
	//@Test
	public void testAddCurrency() throws Exception {
		when(currencyService.add(Mockito.any(CurrencyEntity.class))).thenReturn(new CurrencyEntity("HKD", "港幣"));
		
		
		this.mockMvc.perform(
					post("/currencies").
					contentType(CurrencyControllerTest.JSON_UTF8).
					characterEncoding("utf-8").
					content(new ObjectMapper().writeValueAsString(new CurrencyEntity("HKD", "港幣")))).
			andDo(print()).
			andExpect(content().contentType(CurrencyControllerTest.JSON_UTF8)).
			andExpect(status().isOk()).
			andExpect(jsonPath("$.payload.code", equalTo("HKD")));
			
	}
	
	
	/**
	 * 測試呼叫刪除幣別對應表資料API。
	 * 
	 * @throws Exception
	 */
	//@Test
	public void testDeleteCurrency() throws Exception {

		this.mockMvc.perform(
					delete("/currencies/HKD").
					contentType(CurrencyControllerTest.JSON_UTF8).
					characterEncoding("utf-8")).
			andDo(print()).
			andExpect(content().contentType(CurrencyControllerTest.JSON_UTF8)).
			andExpect(status().isNoContent());
		
			verify(currencyService, times(1)).delete("HKD");
	}

	
	/**
	 * 測試呼叫更新幣別對應表資料API，並顯示其內容。
	 * 
	 */
	@Test
	public void testUpdateCurrency() throws Exception {
		
		this.mockMvc.perform(
					put("/currencies").
					contentType(CurrencyControllerTest.JSON_UTF8).
					characterEncoding("utf-8").
					content(new ObjectMapper().writeValueAsString(new CurrencyResponseDto("TWD", "台幣")))).
					
			andDo(print()).
			andExpect(content().contentType(CurrencyControllerTest.JSON_UTF8)).
			andExpect(status().isOk());
		
			verify(currencyService, times(1)).update("TWD", "台幣");
	}
}
