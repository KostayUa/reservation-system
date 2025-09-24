package com.kostay.reservation_system.reservations;

public record ReservationSearchFilter(
        Long userId,
        Long roomId,
        Integer pageSize,
        Integer pageNumber
) {
}
