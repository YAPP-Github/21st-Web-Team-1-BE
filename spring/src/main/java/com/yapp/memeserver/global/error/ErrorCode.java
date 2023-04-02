package com.yapp.memeserver.global.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // 공통
    INVALID_INPUT_VALUE("입력값이 올바르지 않습니다."),
    INVALID_PARAMETER("파라미터가 올바르지 않습니다."),
    RESOURCE_NOT_FOUND("해당 리소스를 찾을 수 없습니다."),
    INTERNAL_SERVER_ERROR("서버에 오류가 발생하였습니다."),
    ;

    private final String message;
}
