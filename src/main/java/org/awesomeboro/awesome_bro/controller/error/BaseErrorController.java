package org.awesomeboro.awesome_bro.controller.error;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Map;
import org.awesomeboro.awesome_bro.constant.ErrorCode;
import org.awesomeboro.awesome_bro.dto.ApiErrorResponse;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class BaseErrorController implements ErrorController {

	@RequestMapping(path ="/error", produces = MediaType.TEXT_HTML_VALUE)
	public ModelAndView errorHtml(HttpServletResponse response) {
		HttpStatus status = HttpStatus.valueOf(response.getStatus());
		ErrorCode errorCode = status.is4xxClientError() ? ErrorCode.BAD_REQUEST : ErrorCode.INTERNAL_ERROR;
		return new ModelAndView("error", Map.of(
				"statusCode", status.value(),
				"errorCode", errorCode,
				"message", errorCode.getMessage(status.getReasonPhrase())
		),
		status);
	}

	@RequestMapping("/error")
	public ResponseEntity<ApiErrorResponse> error(HttpServletRequest request,HttpServletResponse response) {
		HttpStatus status = HttpStatus.valueOf(response.getStatus());
		String errorMessage = (String) request.getAttribute(RequestDispatcher.ERROR_MESSAGE);
		// ErrorCode를 사용하여 ApiErrorResponse 객체 생성
		ErrorCode errorCode = status.is4xxClientError() ? ErrorCode.BAD_REQUEST : ErrorCode.INTERNAL_ERROR;

		// Custom error message를 사용하여 ApiErrorResponse 객체 생성
		ApiErrorResponse apiErrorResponse = ApiErrorResponse.of(false, errorCode.getCode(), errorMessage);

		return ResponseEntity.status(status).body(apiErrorResponse);
	}

}
