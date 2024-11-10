package store.domain.product;

import store.domain.promotion.Promotion;

public class Product {
    private final String name;
    private final int price;
    private int quantity;
    private final Promotion promotion;

    public Product(ProductParameter productParameter, Promotion promotion) {
        this.name = productParameter.getName();
        this.price = productParameter.getPrice();
        this.quantity = productParameter.getQuantity();
        this.promotion = promotion;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getPromotionName() {
        String promotionName = "";
        if (promotion != null) {
            promotionName = promotion.getPromotionName();
        }
        return promotionName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void sell(int amount) {
        quantity -= amount;
    }

    public int getRegularPurchasePrice(int amount) {
        return amount * price;
    }

    public int getPromotionDiscountPrice(int amount) {
        if (!isPromotedProduct()) {
            return 0;
        }
        return promotion.getDiscountPrice(amount, price);
    }

    public int getPromotionDiscountAmount(int amount) {
        if (!isPromotedProduct()) {
            return 0;
        }
        return promotion.getDiscountedQuantity(amount);
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
        return promotion.getRequiredBuyCount() == buyQuantity && quantity > buyQuantity;
    }

    public boolean isStockAvailable(int toBuyQuantity) {
        return quantity >= toBuyQuantity;
    }

    public boolean isPromotedProduct() {
        if (promotion != null) {
            return promotion.isAvailablePromotion();
        }
        return false;
    }
}
