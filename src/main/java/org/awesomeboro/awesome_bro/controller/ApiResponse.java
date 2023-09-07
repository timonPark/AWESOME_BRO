package org.awesomeboro.awesome_bro.controller;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ApiResponse<T> {

	private boolean success;
	private T data;
	private String message;

	public static <T> ApiResponse<T> createSuccess(T data) {
		return new ApiResponse<>(true, data, null);
	}

	public static ApiResponse<?> createSuccessWithNoContent() {
		return new ApiResponse<>(true, null, null);
	}

	private ApiResponse(boolean success, T data, String message) {
		this.success = success;
		this.data = data;
		this.message = message;
	}

}
