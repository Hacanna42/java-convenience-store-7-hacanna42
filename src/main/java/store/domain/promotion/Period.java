package store.domain.promotion;

import java.time.LocalDateTime;

/**
 * Period 는 날짜 두개로 이루어진 기간 정보를 필드로 가지고 있습니다.
 * 특정 날짜가 기간 내인지 확인하는 것이 책임입니다.
 */
public class Period {
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;

    public Period(LocalDateTime startDate, LocalDateTime endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public boolean isBetweenPeriod(LocalDateTime date) {
        return !(date.isBefore(startDate) || date.isAfter(endDate));
    }
}
