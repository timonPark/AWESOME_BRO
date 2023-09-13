package org.awesomeboro.awesome_bro.controller.error;

import io.jsonwebtoken.MalformedJwtException;
import org.awesomeboro.awesome_bro.constant.ErrorCode;
import org.awesomeboro.awesome_bro.dto.ApiErrorResponse;
import org.awesomeboro.awesome_bro.exception.GeneralException;
import org.awesomeboro.awesome_bro.exception.PasswordException;
import org.awesomeboro.awesome_bro.exception.UserNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice(annotations = RestController.class)
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler
	public ResponseEntity<Object> general(GeneralException e, WebRequest request) {
		ErrorCode errorCode = e.getErrorCode();
		HttpStatus status = errorCode.isClientSideError() ?
				HttpStatus.BAD_REQUEST :
				HttpStatus.INTERNAL_SERVER_ERROR;
		System.out.println("이엑스:"+e+"바디:"+e.getMessage()+"헤더:"+HttpHeaders.EMPTY+"스테이터스코드:"+status+"리퀘스트:"+request);
		return super.handleExceptionInternal(
				e,
				ApiErrorResponse.of(false, errorCode.getCode(), errorCode.getMessage(e)),
				HttpHeaders.EMPTY,
				status,
				request
		);
	}

	@ExceptionHandler
	public ResponseEntity<Object> exception(Exception e, WebRequest request) {
		ErrorCode errorCode = ErrorCode.INTERNAL_ERROR;
		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		return super.handleExceptionInternal(
				e,
				ApiErrorResponse.of(false, errorCode.getCode(), errorCode.getMessage(e)),
				HttpHeaders.EMPTY,
				status,
				request
		);
	}

	@Override
	protected ResponseEntity<Object> handleExceptionInternal(final Exception ex, final Object body, final HttpHeaders headers, final HttpStatusCode statusCode,
			final WebRequest request) {
		System.out.println("이엑스:"+ex+"바디:"+body+"헤더:"+headers+"스테이터스코드:"+statusCode+"리퀘스트:"+request);
		ErrorCode errorCode = statusCode.is4xxClientError() ?
				ErrorCode.SPRING_BAD_REQUST :
				ErrorCode.SPRING_INTERNAL_ERROR;
		return super.handleExceptionInternal(
				ex,
				ApiErrorResponse.of(false, errorCode.getCode(), errorCode.getMessage(ex)),
				headers,
				statusCode,
				request
		);
	}

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<Object> handleUserNotFoundException(final UserNotFoundException ex, WebRequest request) {
		ErrorCode errorCode = ex.getErrorCode();
		HttpStatus status = HttpStatus.NOT_FOUND;  // 적절한 상태 코드를 사용하세요.
		return super.handleExceptionInternal(
				ex,
				ApiErrorResponse.of(false, errorCode.getCode(), ex.getMessage()),
				HttpHeaders.EMPTY,
				status,
				request
		);
	}

	@ExceptionHandler(PasswordException.class)
	public ResponseEntity<Object> handlePasswordException(final PasswordException ex, WebRequest request) {
		ErrorCode errorCode = ex.getErrorCode();
		HttpStatus status = HttpStatus.NOT_FOUND;  // 적절한 상태 코드를 사용하세요.
		return super.handleExceptionInternal(
				ex,
				ApiErrorResponse.of(false, errorCode.getCode(), ex.getMessage()),
				HttpHeaders.EMPTY,
				status,
				request
		);
	}




}
