package store.domain.order;

import java.util.List;
import store.domain.product.Product;

public class OrderStatus {
    private final List<Product> products;
    private final boolean inStock;
    private final boolean canGetFreeItem;
    private final int promotionCanAppliedCount;

    public OrderStatus(List<Product> products, boolean inStock, boolean canGetFreeItem, int promotionCanAppliedCount) {
        this.products = products;
        this.inStock = inStock;
        this.canGetFreeItem = canGetFreeItem;
        this.promotionCanAppliedCount = promotionCanAppliedCount;
    }

    public OrderStatus(List<Product> products, boolean inStock) {
        this(products, inStock, false, 0);
    }

    public Product getSingleProduct() {
        return products.getFirst();
    }

    public List<Product> getMultipleProducts() {
        return products;
    }

    public boolean isCanGetFreeItem() {
        return canGetFreeItem;
    }

    public boolean isInStock() {
        return inStock;
    }

    public boolean hasPromotionProduct() {
        return products.stream().anyMatch(Product::isPromotedProduct);
    }

    public int getAppliedPromotionCount() {
        return promotionCanAppliedCount;
    }

    public int getNotAppliedItemCount(int quantity) {
        if (promotionCanAppliedCount < quantity) {
            return quantity - promotionCanAppliedCount;
        }
        return 0;
    }

    public boolean isMultipleStock() {
        return products.size() == 2;
    }

    public static OrderStatus inMultipleNormalProductStock(List<Product> products) {
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
