package store.domain.promotion;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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

    List<String> promotionParameters;

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

    public Period getPromotionPeriod() {
        try {
            return new Period(getStartDate(), getEndDate());
        } catch (ParseException parseException) {
            System.out.println("[ERROR] promotions.md 파일의 날짜 형식이 올바르지 않습니다. 프로그램을 종료합니다.");
            throw new RuntimeException();
        }
    }

    private Date getStartDate() throws ParseException {
        String startDate = promotionParameters.get(ParameterSequence.START_DATE.getSequence());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.parse(startDate);
    }

    private Date getEndDate() throws ParseException {
        String endDate = promotionParameters.get(ParameterSequence.END_DATE.getSequence());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.parse(endDate);
    }
}

