package org.awesomeboro.awesome_bro.constant;

import java.util.Optional;
import java.util.function.Predicate;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

	OK(0, ErrorCategory.NORMAL, "OK"),

	BAD_REQUEST(10000, ErrorCategory.CLIENT_SIDE, "bad request"),
	SPRING_BAD_REQUST(10001, ErrorCategory.CLIENT_SIDE, "Spring-detected bad request"),

	INTERNAL_ERROR(20000, ErrorCategory.SERVER_SIDE, "internal error"),
	SPRING_INTERNAL_ERROR(20001, ErrorCategory.SERVER_SIDE, "Spring-detected internal error"),
	WRONG_PASSWORD(20002, ErrorCategory.SERVER_SIDE, "비밀번호가 틀렸습니다."),
	LOGIN_ERROR(20003, ErrorCategory.SERVER_SIDE, "로그인에 실패했습니다."),
	PASSWORD_LENGTH_ERROR(20004, ErrorCategory.SERVER_SIDE, "비밀번호는 8자 이상 15자 이하로 입력해주세요."),
	UNDEFINED_EMAIL(20005, ErrorCategory.SERVER_SIDE, "가입되지 않은 이메일입니다."),
	EMAIL_ALREADY_EXISTS(20006, ErrorCategory.SERVER_SIDE, "이미 가입된 이메일입니다."),
	DELETED_USER(20007, ErrorCategory.SERVER_SIDE, "탈퇴한 회원입니다."),
	WRONG_JWT(2008, ErrorCategory.SERVER_SIDE, "잘못된 토큰입니다.");



	private final Integer code;
	private final ErrorCategory errorCategory;
	private final String message;

	public String getMessage(Exception e) { return getMessage(e.getMessage());}

	public String getMessage(String message) {
		return Optional.ofNullable(message)
				.filter(Predicate.not(String:: isBlank))
//				.orElse(getMessage());
				.orElse(this.message);
	}

	public boolean isClientSideError() { return this.getErrorCategory() == ErrorCategory.CLIENT_SIDE; }

	public boolean isServerSideError() { return this.getErrorCategory() == ErrorCategory.SERVER_SIDE; }

	@Override public String toString() {
		return String.format("%s (%d)", name(), this.getCode());
	}

	public enum ErrorCategory {
		NORMAL, CLIENT_SIDE, SERVER_SIDE
	}
}
