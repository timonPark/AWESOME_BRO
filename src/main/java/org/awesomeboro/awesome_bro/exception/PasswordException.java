package org.awesomeboro.awesome_bro.exception;

import lombok.Getter;
import org.awesomeboro.awesome_bro.constant.ErrorCode;

@Getter
public class PasswordException extends RuntimeException{
    private final ErrorCode errorCode;

    // 비밀번호가 틀렸을 때 사용되는 생성자
    public PasswordException() {
        super(ErrorCode.BAD_CREDENTIALS.getMessage());
        this.errorCode = ErrorCode.BAD_CREDENTIALS;
    }

    // 비밀번호 길이가 8자 이하일 때 사용되는 생성자
    public PasswordException(ErrorCode errorCode, String password) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    // 정적 팩토리 메서드를 사용한 예제
    public static PasswordException wrongPasswordException() {
        return new PasswordException(ErrorCode.BAD_CREDENTIALS, null);
    }

    public static PasswordException passwordLengthException(String password) {
        return new PasswordException(ErrorCode.PASSWORD_LENGTH_ERROR, password);
    }
}
