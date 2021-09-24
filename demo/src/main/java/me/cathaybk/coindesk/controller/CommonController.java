package me.cathaybk.coindesk.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;

import me.cathaybk.coindesk.dto.ResponseDto;
import me.cathaybk.coindesk.exception.BusinessException;

public class CommonController {

	private static final Logger logger = LoggerFactory.getLogger(CommonController.class);
	
	@ExceptionHandler(value = Exception.class)
	public ResponseDto<?> errorHandler(Exception e) {
		logger.error("發生系統錯誤", e);
		return ResponseDto.error(null, "發生未知的錯誤，請稍後再試");
	}
	
	@ExceptionHandler(value = BusinessException.class)
	public ResponseDto<?> errorHandler(BusinessException e) {
		logger.error("發生邏輯錯誤", e);
		return ResponseDto.error(null, e.getMessage());
	}
}
