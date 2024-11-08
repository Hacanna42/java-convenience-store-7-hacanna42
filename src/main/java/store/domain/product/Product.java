package store.domain.product;

import store.domain.promotion.Promotion;

public class Product {
    private final String name;
    private final int price;
    private final int quantity;
    private final Promotion promotion;
    // TODO: 고민사항 - promotionQuantity 필드를 따로 만들어서 관리하는게 나으려나?

    public Product(ProductParameter productParameter, Promotion promotion) {
        this.name = productParameter.getName();
        this.price = productParameter.getPrice();
        this.quantity = productParameter.getQuantity();
        this.promotion = promotion;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    // 최대 적용 가능한 프로모션 상품 개수를 반환하는 함수
    public int getMaxAvailablePromotionQuantity() {
        if (isPromotedProduct()) {
            return promotion.getMaxAvailablePromotionQuantity(quantity);
        }
        return 0;
    }

    // 프로모션을 통해 무료로 받을 수 있는 상품이 존재하는지 확인하는 함수
    public boolean isCanGetFreeProduct(int buyQuantity) {
        if (!isPromotedProduct()) {
            return false;
        }
        return promotion.getRequiredBuyCount() == buyQuantity;
    }

    public boolean isStockAvailable(int toBuyQuantity) {
        return quantity >= toBuyQuantity;
    }

    public boolean isAllBuyItemCanApplyPromotion(int toBuyQuantity) {
        if (isPromotedProduct()) {
            return promotion.getMaxAvailablePromotionQuantity(quantity) >= toBuyQuantity;
        }
        return false;
    }

    public boolean isPromotedProduct() {
        if (promotion != null) {
            return promotion.isAvailablePromotion();
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(name).append(" ").append(price).append("원 ").append(quantity).append("개");
        if (promotion != null) {
            stringBuilder.append(" ").append(promotion.getPromotionName());
        }

        return stringBuilder.toString();
    }
}
