package ch.uzh.ifi.seal.soprafs15.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import ch.uzh.ifi.seal.soprafs15.controller.beans.JsonUriWrapper;

public abstract class GenericService {
	
	Logger logger = LoggerFactory.getLogger(GenericService.class);
	
	protected JsonUriWrapper getJsonUrl(String uri) {
		JsonUriWrapper wrapper = new JsonUriWrapper();
		wrapper.setUri(uri);
		
		return wrapper;
	}
	
	@ExceptionHandler(TransactionSystemException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	public void handleTransactionSystemException(Exception exception, HttpServletRequest request) {
		logger.error("", exception);
	}
	
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public void handleException(Exception exception, HttpServletRequest request) {
		logger.error("", exception);
	}
}
