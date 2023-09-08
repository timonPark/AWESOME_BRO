package org.awesomeboro.awesome_bro.controller.error;

import java.util.Map;
import org.awesomeboro.awesome_bro.constant.ErrorCode;
import org.awesomeboro.awesome_bro.exception.GeneralException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class BaseExceptionHandler {

	@ExceptionHandler
	public ModelAndView general(GeneralException e) {
		ErrorCode errorCode = e.getErrorCode();
		HttpStatus status = errorCode.isClientSideError() ?
				HttpStatus.BAD_REQUEST :
				HttpStatus.INTERNAL_SERVER_ERROR;

		return new ModelAndView("error", Map.of(
				"statusCode", status.value(),
				"errorCode", errorCode,
				"message", errorCode.getMessage(e)
		),
				status);
	}

	@ExceptionHandler
	public ModelAndView exception(Exception e) {
		ErrorCode errorCode = ErrorCode.INTERNAL_ERROR;
		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		return new ModelAndView("error", Map.of(
				"statusCode", status.value(),
				"errorCode", errorCode,
				"message", errorCode.getMessage(status.getReasonPhrase())
		),
				status);
	}

}
