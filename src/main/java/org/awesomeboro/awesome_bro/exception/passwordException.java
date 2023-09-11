package org.awesomeboro.awesome_bro.exception;

import lombok.Getter;
import org.awesomeboro.awesome_bro.constant.ErrorCode;

@Getter
public class passwordException extends RuntimeException{
    private final ErrorCode errorCode;

    public passwordException() {
        super(ErrorCode.BAD_CREDENTIALS.getMessage());
        this.errorCode = ErrorCode.BAD_CREDENTIALS;
    }
}
