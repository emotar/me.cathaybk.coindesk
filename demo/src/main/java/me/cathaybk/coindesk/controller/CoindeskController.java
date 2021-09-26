package me.cathaybk.coindesk.controller;

import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import me.cathaybk.coindesk.dto.CoindeskApiDto;
import me.cathaybk.coindesk.dto.CoindeskApiDto.Bpi;
import me.cathaybk.coindesk.dto.CoindeskResponseDto;
import me.cathaybk.coindesk.dto.ResponseDto;
import me.cathaybk.coindesk.entity.CurrencyEntity;
import me.cathaybk.coindesk.service.CurrencyService;

@RestController
@RequestMapping("/coindesk")
public class CoindeskController extends CommonController {
	
	private CurrencyService currencyService;

	private RestTemplate restTemplate;
	
	@Value("${coindesk_url:https://api.coindesk.com/v1/bpi/currentprice.json}")
	private String coinDeskUrl;

	public CoindeskController() {}
	
	@Autowired
	public CoindeskController(CurrencyService currencyService, RestTemplate restTemplate) {
		this.currencyService = currencyService;
		this.restTemplate = restTemplate;
	}
	
	@RequestMapping(method = {RequestMethod.GET})
	public ResponseEntity<?> find(@RequestParam(value = "code", required = false) String code) {

		ResponseEntity<?> result = null;
		CoindeskApiDto coindeskApiResult = this.restTemplate.getForObject(coinDeskUrl, CoindeskApiDto.class);
		Map<String, CurrencyEntity> currencyMaps = this.currencyService.currencyMap();
		Date now = new Date();
		
		
		if (StringUtils.isNotBlank(code)) {
			
			Bpi bpi = coindeskApiResult.getBpi().get(code);
			CoindeskResponseDto.Currency coindeskResponseCurrencyDto = new CoindeskResponseDto.Currency();
			CurrencyEntity currencyEntity = currencyMaps.get(code);
			
			if (bpi != null) {
				coindeskResponseCurrencyDto.setChineseCurrencyName(currencyEntity.getChineseName());
				coindeskResponseCurrencyDto.setCurrencyCode(code);
				coindeskResponseCurrencyDto.setRate(bpi.getRateFloat());

				result = ResponseEntity.ok(ResponseDto.success(new CoindeskResponseDto(now, coindeskResponseCurrencyDto)));
				
			} else {		
				result = ResponseEntity.ok(ResponseDto.success(null));
			}
			
		} else {
			result = ResponseEntity.ok(ResponseDto.success(
				new CoindeskResponseDto(now,					
										currencyMaps.values().stream().map(item -> {
											
											Double rate = null;
											if (coindeskApiResult.getBpi().containsKey(item.getCode())) {
												rate = coindeskApiResult.getBpi().get(item.getCode()).getRateFloat();
											}
											
											CoindeskResponseDto.Currency coindeskResponseCurrencyDto = new CoindeskResponseDto.Currency();
											coindeskResponseCurrencyDto.setChineseCurrencyName(item.getChineseName());
											coindeskResponseCurrencyDto.setCurrencyCode(item.getCode());
											coindeskResponseCurrencyDto.setRate(rate == null ? null : rate);
											
											return coindeskResponseCurrencyDto;
										}).collect(Collectors.toList())
				))
			);
			
			
			

		}
		return result;
		
	}
}
