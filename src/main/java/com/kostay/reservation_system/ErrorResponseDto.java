package com.kostay.reservation_system;

import java.time.LocalDateTime;

public record ErrorResponseDto(
        String message,
        String detailedMessage,
        LocalDateTime timestamp
) {
}
