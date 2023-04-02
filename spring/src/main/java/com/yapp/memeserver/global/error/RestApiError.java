package com.yapp.memeserver.global.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
// 언체크 예외(런타임 예외)를 상속받는 예외 클래스
public class RestApiError extends RuntimeException {

    private final ErrorCode errorCode;

}