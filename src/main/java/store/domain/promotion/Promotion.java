package store.domain.promotion;

public class Promotion {
    private String promotionName;
    private int requiredBuyCount;
    private int toGiveItemCount;
    private Period promotionPeriod;

    public Promotion(PromotionParameter promotionParameter) {
        this.promotionName = promotionParameter.getPromotionName();
        this.requiredBuyCount = promotionParameter.getRequiredBuyCount();
        this.toGiveItemCount = promotionParameter.getToGiveItemCount();
        this.promotionPeriod = promotionParameter.getPromotionPeriod();
    }
}
