package com.example.security.jwt.global.exception.error;

import com.example.security.jwt.global.exception.ErrorCode;
import lombok.Getter;

@Getter
public class CommonException extends RuntimeException {
    private ErrorCode errorCode;

    public CommonException(ErrorCode errorCode) {
        super(errorCode.getMessage());
    }
}
