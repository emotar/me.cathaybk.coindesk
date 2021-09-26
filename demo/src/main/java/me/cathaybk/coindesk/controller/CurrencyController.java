package me.cathaybk.coindesk.controller;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import me.cathaybk.coindesk.dto.CurrencyResponseDto;
import me.cathaybk.coindesk.dto.ResponseDto;
import me.cathaybk.coindesk.service.CurrencyService;

@RestController
@RequestMapping("/currencies")
public class CurrencyController extends CommonController {


	private CurrencyService currencyService;

	public CurrencyController() {
		
	}
	@Autowired
	public CurrencyController(CurrencyService currencyService) {
		this.currencyService = currencyService;
	}
	
	@RequestMapping(method = {RequestMethod.GET})	
	public ResponseEntity<?> find(@RequestParam(value = "code", required = false) String code) {

		Optional<?> result = Optional.empty();

		if (StringUtils.isBlank(code)) {
			result = Optional.ofNullable(this.currencyService.all());

		} else {
			result = currencyService.find(code);			
		}

		return result.isPresent() ? ResponseEntity.ok(ResponseDto.success(result.get()))
				: new ResponseEntity<ResponseDto<?>>(ResponseDto.success(), HttpStatus.NOT_FOUND);

	}

	@RequestMapping(method = {RequestMethod.POST})
	public ResponseEntity<?> add(@RequestBody CurrencyResponseDto dto) {
		
		return ResponseEntity.ok(ResponseDto.success(this.currencyService.add(this.currencyService.dtoToEntity(dto))));
	}

	@RequestMapping(method = {RequestMethod.DELETE}, value = "/{code}")
	public ResponseEntity<?> delete(@PathVariable("code") String code) {
		this.currencyService.delete(code);
		return new ResponseEntity<ResponseDto<?>>(ResponseDto.success(null), HttpStatus.NO_CONTENT);
	}
	
	
	@RequestMapping(method = {RequestMethod.PUT})
	public ResponseEntity<?> update(@RequestBody CurrencyResponseDto dto) {
		this.currencyService.update(dto.getCode(), dto.getChineseName());
		return ResponseEntity.ok(ResponseDto.success(null));
	}
	
}
