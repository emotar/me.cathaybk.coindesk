package me.cathaybk.coindesk.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import me.cathaybk.coindesk.entity.CurrencyEntity;

@Repository
public interface CurrencyRepository extends JpaRepository<CurrencyEntity, Integer> {

	public Optional<CurrencyEntity> findByCode(String code);
	
	public void deleteByCode(String code);
}
