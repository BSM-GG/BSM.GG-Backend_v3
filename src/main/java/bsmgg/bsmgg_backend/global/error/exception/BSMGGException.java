package bsmgg.bsmgg_backend.global.error.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class BSMGGException extends RuntimeException{
    private final ErrorCode errorCode;
}
