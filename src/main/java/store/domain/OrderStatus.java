package store.domain;

import java.util.List;
import store.domain.product.Product;

public class OrderStatus {
    private List<Product> products;
    private boolean inStock;
    private boolean canGetFreeItem;
    private int promotionCanAppliedCount;

    public OrderStatus(List<Product> products, boolean inStock, boolean canGetFreeItem, int promotionCanAppliedCount) {
        this.products = products;
        this.inStock = inStock;
        this.canGetFreeItem = canGetFreeItem;
        this.promotionCanAppliedCount = promotionCanAppliedCount;
    }

    public OrderStatus(List<Product> products, boolean inStock) {
        this.products = products;
        this.inStock = inStock;
    }

    public static OrderStatus inMultipleProductStock(List<Product> products) {
        return new OrderStatus(products, true);
    }

    public static OrderStatus outOfStock() {
        return new OrderStatus(null, false);
    }

    public static OrderStatus inNormalStock(Product product) {
        return new OrderStatus(List.of(product), true);
    }

    public static OrderStatus inPromotionStock(Product product, boolean canGetFreeItem, int promotionCanAppliedCount) {
        return new OrderStatus(List.of(product), true, canGetFreeItem, promotionCanAppliedCount);
    }
}
