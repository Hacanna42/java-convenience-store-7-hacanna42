package store.domain.promotion;

import camp.nextstep.edu.missionutils.DateTimes;

public class Promotion {
    private String promotionName;
    private int requiredBuyCount;
    private int toGiveItemCount;
    private Period period;

    public Promotion(PromotionParameter promotionParameter) {
        this.promotionName = promotionParameter.getPromotionName();
        this.requiredBuyCount = promotionParameter.getRequiredBuyCount();
        this.toGiveItemCount = promotionParameter.getToGiveItemCount();
        this.period = promotionParameter.getPeriod();
    }

    public String getPromotionName() {
        return promotionName;
    }

    public boolean isAvailablePromotion() {
        return period.isBetweenPeriod(DateTimes.now());
    }
}
