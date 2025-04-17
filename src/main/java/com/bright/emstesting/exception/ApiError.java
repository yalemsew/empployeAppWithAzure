package com.bright.emstesting.exception;

import java.time.Instant;

public record ApiError(
        String message,
        String path,
        int statusCode,
        Instant timeStamp
) {
}
