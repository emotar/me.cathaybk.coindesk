package me.cathaybk.coindesk.controller;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import me.cathaybk.coindesk.dto.CurrencyResponseDto;
import me.cathaybk.coindesk.dto.ResponseDto;
import me.cathaybk.coindesk.entity.CurrencyEntity;
import me.cathaybk.coindesk.service.CurrencyService;

@RestController
@RequestMapping("/currencies")
public class CurrencyController extends CommonController {

	private CurrencyService currencyService;
	
	public CurrencyController(CurrencyService currencyService) {
		this.currencyService = currencyService;
	}

	@GetMapping()
	public ResponseEntity<?> find(@RequestParam(value = "code", required = false) String code) {

		Optional<?> result = Optional.empty();

		if (StringUtils.isBlank(code)) {
			result = Optional.ofNullable(this.currencyService.all());

		} else {
			Optional<CurrencyEntity> currencyEntity = currencyService.find(code);
			result = currencyEntity.map(item -> new CurrencyResponseDto(item.getCode(), item.getChineseName()));
		}

		return result.isPresent() ? ResponseEntity.ok(ResponseDto.success(result.get()))
				: new ResponseEntity<ResponseDto>(ResponseDto.success(), HttpStatus.NOT_FOUND);

	}

	@PostMapping
	public ResponseEntity<?> add(@RequestBody CurrencyResponseDto dto) {
		
		return ResponseEntity.ok(ResponseDto.success(this.currencyService.add(currencyService.dtoToEntity(dto))));
	}

	@DeleteMapping("/{code}")
	public ResponseEntity<?> delete(@PathVariable("code") String code) {
		this.currencyService.delete(code);
		return new ResponseEntity<ResponseDto>(ResponseDto.success(null), HttpStatus.NO_CONTENT);
	}
	
	
	@PutMapping()
	public ResponseEntity<?> update(@RequestBody CurrencyResponseDto dto) {
		this.currencyService.update(dto.getCode(), dto.getChineseName());
		return ResponseEntity.ok(ResponseDto.success(null));
	}
	
}
