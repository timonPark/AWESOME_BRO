package org.awesomeboro.awesome_bro.exception;

import lombok.Getter;
import org.awesomeboro.awesome_bro.constant.ErrorCode;

// 이메일이 이미 존재할 때 발생하는 예외
@Getter
public class EmailAlreadyExistsException extends RuntimeException {
    private final ErrorCode errorCode;

    public EmailAlreadyExistsException(String email) {
        super();
        this.errorCode = ErrorCode.LOGIN_ERROR;
    }
}
