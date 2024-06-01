package bsmgg.bsmgg_backend.global.error;

import bsmgg.bsmgg_backend.global.error.exception.ErrorCode;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ErrorResponse {
    public static final String errorLogsFormat = """
            {
            	"status": "%s",
            	"message": "%s"
            }
            """;

    private int status;
    private String message;

    @Builder
    protected ErrorResponse(final ErrorCode errorCode) {
        this.status = errorCode.getStatus();
        this.message = errorCode.getMessage();
    }

    public static ErrorResponse of(final ErrorCode code) {
        return new ErrorResponse(code);
    }


    @Override
    public String toString() {
        return errorLogsFormat.formatted(status, message);
    }
}
