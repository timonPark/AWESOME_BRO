package org.awesomeboro.awesome_bro.exception;

import lombok.Getter;
import org.awesomeboro.awesome_bro.constant.ErrorCode;

@Getter
public class UserNotFoundException extends RuntimeException {
    private final ErrorCode errorCode;

    public UserNotFoundException(Long id) {
        super(String.format(ErrorCode.LOGIN_ERROR.getMessage(), id));
        this.errorCode = ErrorCode.LOGIN_ERROR;
    }
    public UserNotFoundException(String email) {
        super(String.format(ErrorCode.LOGIN_ERROR.getMessage(), email));
        this.errorCode = ErrorCode.LOGIN_ERROR;
    }
}
