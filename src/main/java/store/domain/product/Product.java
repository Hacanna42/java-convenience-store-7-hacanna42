package store.domain.product;

import store.domain.promotion.Promotion;

public class Product {
    private final String name;
    private final int price;
    private final int quantity;
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

    public int getQuantity() {
        return quantity;
    }

    public boolean isAvailableBuyQuantity(int toBuyQuantity) {
        if (isPromotedProduct()) {
            return promotion.getMaxAvailableQuantity(quantity) >= toBuyQuantity;
        }

        return quantity >= toBuyQuantity;
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
