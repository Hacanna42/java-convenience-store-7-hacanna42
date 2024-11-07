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

    /* 구매할 수 있는 최대 프로모션 상품 개수를 구하는 법
     * 콜라 2+1 프로모션 상품이 7개 있을 때, 사용자가 얻을 수 있는 콜라의 최대 개수는 6개다. 4개를 구매하고, 2개를 무료로 받으면 총 6개이기 때문이다.
     * 5개를 구매하고 2+1 이벤트를 최대한 적용하여 최대 7개를 얻을수도 있다고 생각할 수 있지만, 우테코에서 제시한 실행 결과 예시에 따르면 그렇게 할 수 없다.
     * 따라서, 프로모션 재고 수가 N일때 사용자가 얻을 수 있는 최대 개수는 [N - (N % (requiredBuyCount + toGiveItemCount))] 이다.
     */
    public int getMaxAvailableQuantity(int currentStockCount) {
        return currentStockCount - (currentStockCount % (requiredBuyCount + toGiveItemCount));
    }

    public String getPromotionName() {
        return promotionName;
    }

    public boolean isAvailablePromotion() {
        return period.isBetweenPeriod(DateTimes.now());
    }
}
