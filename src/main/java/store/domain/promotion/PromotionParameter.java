package store.domain.promotion;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * PromotionParameter 는 Promotion 를 초기화하기 위한 컬렉션 필드와, 인덱스와 파라미터를 매치하기 위한 Enum 을 가지고 있습니다.
 * Promotion 의 생성자로 전달되어 Promotion 를 올바르게 초기화하는 것이 역할입니다.
 */
public class PromotionParameter {

    public enum ParameterSequence {
        PROMOTION_NAME(0),
        REQUIRED_BUY_COUNT(1),
        TO_GIVE_ITEM_COUNT(2),
        START_DATE(3),
        END_DATE(4);

        private final int sequence;

        ParameterSequence(int sequence) {
            this.sequence = sequence;
        }

        public int getSequence() {
            return sequence;
        }
    }

    private final List<String> promotionParameters;

    public PromotionParameter(List<String> promotionParameters) {
        this.promotionParameters = promotionParameters;
    }

    public String getPromotionName() {
        return promotionParameters.get(ParameterSequence.PROMOTION_NAME.getSequence());
    }

    public int getRequiredBuyCount() {
        String requiredBuyCount = promotionParameters.get(ParameterSequence.REQUIRED_BUY_COUNT.getSequence());
        return Integer.parseInt(requiredBuyCount);
    }

    public int getToGiveItemCount() {
        String toGiveItemCount = promotionParameters.get(ParameterSequence.TO_GIVE_ITEM_COUNT.getSequence());
        return Integer.parseInt(toGiveItemCount);
    }

    public Period getPeriod() {
        return new Period(getStartDate(), getEndDate());
    }

    private LocalDateTime getStartDate() {
        String startDate = promotionParameters.get(ParameterSequence.START_DATE.getSequence());
        LocalDate localDate = LocalDate.parse(startDate, DateTimeFormatter.ISO_LOCAL_DATE);
        return localDate.atStartOfDay();
    }

    private LocalDateTime getEndDate() {
        String endDate = promotionParameters.get(ParameterSequence.END_DATE.getSequence());
        LocalDate localDate = LocalDate.parse(endDate, DateTimeFormatter.ISO_LOCAL_DATE);
        return localDate.atStartOfDay();
    }
}

