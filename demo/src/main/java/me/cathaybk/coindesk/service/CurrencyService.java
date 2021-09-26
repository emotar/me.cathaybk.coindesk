package me.cathaybk.coindesk.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import me.cathaybk.coindesk.dto.CurrencyResponseDto;
import me.cathaybk.coindesk.entity.CurrencyEntity;
import me.cathaybk.coindesk.repository.CurrencyRepository;

@Service
@Transactional
public class CurrencyService {

	@Autowired
	private CurrencyRepository currencyRepository;

	public CurrencyEntity dtoToEntity(CurrencyResponseDto dto) {
		CurrencyEntity entity = new CurrencyEntity();
		entity.setChineseName(dto.getChineseName());
		entity.setCode(dto.getCode());
		return entity;
	}
	


	public Optional<CurrencyEntity> find(String code) {
		return this.currencyRepository.findByCode(code);
	}


	@Transactional(value = TxType.REQUIRED)
	public CurrencyEntity add(CurrencyEntity entity) {
		return this.currencyRepository.save(entity);
	}

	@Transactional(value = TxType.REQUIRED)
	public void update(String code, String chineseName) {
		Optional<CurrencyEntity> currencyEntityOptional = this.currencyRepository.findByCode(code);
		CurrencyEntity currencyEntity = null;
		if (currencyEntityOptional.isPresent()) {
			currencyEntity = currencyEntityOptional.get();
			currencyEntity.setChineseName(chineseName);		
			
		} else {
			currencyEntity = new CurrencyEntity();
			currencyEntity.setCode(code);
			currencyEntity.setChineseName(chineseName);
		}
		
		this.currencyRepository.save(currencyEntity);
	}
	
	@Transactional(value = TxType.REQUIRED)
	public void delete(String code) {
		this.currencyRepository.deleteByCode(code);
	}
	
	public List<CurrencyEntity> all() {
		return this.currencyRepository.findAll();
	}
	
	public Map<String, CurrencyEntity> currencyMap() {
		return this.all().stream().collect(Collectors.toMap(x -> x.getCode(), Function.identity()));
	}
}
