package store.domain.promotion;

import java.time.LocalDateTime;

public class Period {
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public Period(LocalDateTime startDate, LocalDateTime endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public boolean isBetweenPeriod(LocalDateTime date) {
        return !(date.isBefore(startDate) || date.isAfter(endDate));
    }
}
