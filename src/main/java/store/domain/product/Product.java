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
}
