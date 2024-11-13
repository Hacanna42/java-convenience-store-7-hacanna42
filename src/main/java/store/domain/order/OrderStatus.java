package store.domain.order;

import java.util.List;
import store.domain.product.Product;

/**
 * OrderStatus 클래스는 주문 요청에 대한 결과를 필드로 가지고 있습니다.
 * 결제에 필요한 정보를 제공하는 것이 책임입니다.
 */
public class OrderStatus {
    private List<Product> products;
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

    public Product getFirstProduct() {
        return products.getFirst();
    }

    public void removeNormalProduct() {
        products = products.stream().filter(Product::isPromotedProduct).toList();
    }

    public List<Product> getMultipleProducts() {
        return products;
    }

    public boolean isCanGetFreeItem() {
        return canGetFreeItem;
    }

    public boolean isProductFound() {
        return !products.isEmpty();
    }

    public boolean isInStock() {
        return inStock;
    }

    public boolean hasPromotionProduct() {
        return products.stream().anyMatch(Product::isPromotedProduct);
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

    public static OrderStatus outOfStock(List<Product> products) {
        return new OrderStatus(products, false);
    }

    public static OrderStatus inOnlyNormalStock(Product product) {
        return new OrderStatus(List.of(product), true);
    }

    public static OrderStatus inOnlyPromotionStock(Product product, boolean canGetFreeItem,
                                                   int promotionCanAppliedCount) {
        return new OrderStatus(List.of(product), true, canGetFreeItem, promotionCanAppliedCount);
    }
}
