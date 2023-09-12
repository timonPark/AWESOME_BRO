package org.awesomeboro.awesome_bro.exception;

import lombok.Getter;
import org.awesomeboro.awesome_bro.constant.ErrorCode;

// 사용자를 찾지 못했을 때 발생하는 예외
@Getter
public class UserNotFoundException extends RuntimeException {
    private final ErrorCode errorCode;

    public UserNotFoundException(Long id) {
        super(ErrorCode.LOGIN_ERROR.getMessage());
        this.errorCode = ErrorCode.LOGIN_ERROR;
    }

    public UserNotFoundException(String email) {
        super(ErrorCode.UNDEFINED_EMAIL.getMessage());
        this.errorCode = ErrorCode.UNDEFINED_EMAIL;
    }
}

