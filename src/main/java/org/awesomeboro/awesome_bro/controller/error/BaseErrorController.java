package org.awesomeboro.awesome_bro.controller.error;

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
		System.out.println("이엑스:"+response+"바디:"+response.getStatus()+"헤더:"+response.getHeaderNames()+"스테이터스코드:"+status+"리퀘스트:"+response.getContentType());
		return new ModelAndView("error", Map.of(
				"statusCode", status.value(),
				"errorCode", errorCode,
				"message", errorCode.getMessage(status.getReasonPhrase())
		),
		status);
	}

	@RequestMapping("/error")
	public ResponseEntity<ApiErrorResponse> error(HttpServletResponse response) {
		HttpStatus status = HttpStatus.valueOf(response.getStatus());
		ErrorCode errorCode = status.is4xxClientError() ? ErrorCode.BAD_REQUEST : ErrorCode.INTERNAL_ERROR;
		System.out.println("이엑스:"+status+"바디:"+response.getStatus()+"헤더:"+response.getHeaderNames()+"스테이터스코드:"+status+"리퀘스트:"+response.getContentType());
		return ResponseEntity
				.status (status)
				.body(ApiErrorResponse.of(false, errorCode));
	}


}
