package store.domain;

import java.util.Date;

public class Promotion {
    private String promotionName;
    private int requiredBuyCount;
    private int toGiveItemCount;
    private Period promotionPeriod;

    public Promotion(String promotionName, int requiredBuyCount, int toGiveItemCount, Period promotioPeriod) {
        this.promotionName = promotionName;
        this.requiredBuyCount = requiredBuyCount;
        this.toGiveItemCount = toGiveItemCount;
        this.promotionPeriod = promotioPeriod;
    }
}
