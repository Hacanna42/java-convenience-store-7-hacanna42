package store.domain.promotion;

import camp.nextstep.edu.missionutils.DateTimes;

/**
 * Promotion 은 프로모션 정보를 필드로 가지고 있습니다.
 * 프로모션 할인 가격 등 다양한 프로모션 정보를 제공하는 것이 책임입니다.
 */
public class Promotion {
    private final String promotionName;
    private final int requiredBuyCount;
    private final int toGiveItemCount;
    private final Period period;

    public Promotion(PromotionParameter promotionParameter) {
        this.promotionName = promotionParameter.getPromotionName();
        this.requiredBuyCount = promotionParameter.getRequiredBuyCount();
        this.toGiveItemCount = promotionParameter.getToGiveItemCount();
        this.period = promotionParameter.getPeriod();
    }

    /* 프로모션을 적용할 수 있는 상품 개수를 구하는 법
     * 콜라 2+1 프로모션 상품이 7개 있을 때, 사용자가 프로모션을 적용할 수 있는 콜라의 최대 개수는 6개다. 4개를 구매하고, 2개를 무료로 받으면 총 6개이기 때문이다.
     * 5개를 구매하고 2+1 이벤트를 최대한 적용하여 최대 7개를 얻는 것도 가능하다. 하지만 사용자가 프로모션 재고로는 처리가 불가능한 수량을 입력했을 경우,
     * 우테코에서 제시한 실행 결과 예시에 따르면 정가로 구매해야 하는 콜라의 개수는 (사용자가 입력한 수량) - (최대 적용 가능한 프로모션 수량) 이기 때문에
     * 정가로 구매하는 상품을 제외하는 옵션을 택할 시 프로모션 재고가 몇 개 남을수도 있다.
     *
     * 따라서, 프로모션 재고 수가 N일때 프로모션이 적용될 수 있는 최대 개수는 [N - (N % (requiredBuyCount + toGiveItemCount))] 이다.
     */
    public int getMaxAvailablePromotionQuantity(int currentStockCount) {
        return currentStockCount - (currentStockCount % (requiredBuyCount + toGiveItemCount));
    }

    public String getPromotionName() {
        return promotionName;
    }

    public int getRequiredBuyCount() {
        return requiredBuyCount;
    }

    public boolean isAvailablePromotion() {
        return period.isBetweenPeriod(DateTimes.now());
    }

    public int getDiscountPrice(int buyQuantity, int regularPrice) {
        int freeItemCount = buyQuantity / (requiredBuyCount + toGiveItemCount);
        return freeItemCount * regularPrice;
    }

    public int getDiscountedQuantity(int buyQuantity) {
        return buyQuantity / (requiredBuyCount + toGiveItemCount);
    }
}
