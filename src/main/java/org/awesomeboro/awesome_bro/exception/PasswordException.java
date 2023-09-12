package org.awesomeboro.awesome_bro.exception;

import lombok.Getter;
import org.awesomeboro.awesome_bro.constant.ErrorCode;

@Getter
public class PasswordException extends RuntimeException{
    private final ErrorCode errorCode;

    public PasswordException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

}
