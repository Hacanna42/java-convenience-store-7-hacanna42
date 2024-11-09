package store.domain;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import store.domain.order.OrderItem;
import store.domain.order.OrderStatus;
import store.domain.product.Product;
import store.messages.ErrorMessage;

public class Stock {
    private final List<Product> stock;

    public Stock(List<Product> stock) {
        this.stock = stock;
    }

    public List<Product> getProducts() {
        return stock;
    }

    public OrderStatus getOrderStatus(OrderItem orderItem) {
        List<Product> foundProducts = findAvailableProductsByName(orderItem.getItemName());
        return checkOrderAvailability(orderItem, foundProducts);
    }

    private OrderStatus checkOrderAvailability(OrderItem orderItem, List<Product> foundProducts) {
        if (foundProducts.isEmpty()) {
            return OrderStatus.outOfStock(foundProducts);
        }
        if (foundProducts.size() == 1) {
            return getOrderStatusWithSingleProduct(orderItem, foundProducts.getFirst());
        }
        if (foundProducts.size() == 2) {
            return getOrderStatusWithMultipleProducts(orderItem, foundProducts);
        }

        throw new IllegalArgumentException(ErrorMessage.INVALID_PRODUCT_PROMOTIONS.getMessage());
    }

    private static OrderStatus getOrderStatusWithSingleProduct(OrderItem orderItem, Product product) {
        if (!product.isStockAvailable(orderItem.getQuantity())) {
            return OrderStatus.outOfStock(List.of(product));
        }
        if (product.isPromotedProduct()) {
            boolean canGetFreeItem = product.isCanGetFreeProduct(orderItem.getQuantity());
            int maxPromotionCanAppliedCount = product.getMaxAvailablePromotionQuantity();
            return OrderStatus.inOnlyPromotionStock(product, canGetFreeItem, maxPromotionCanAppliedCount);
        }

        return OrderStatus.inOnlyNormalStock(product);
    }

    private OrderStatus getOrderStatusWithMultipleProducts(OrderItem orderItem, List<Product> foundProducts) {
        if (getAllQuantity(foundProducts) < orderItem.getQuantity()) {
            return OrderStatus.outOfStock(foundProducts);
        }

        if (hasPromotedProduct(foundProducts)) {
            return checkMixedProductsSituation(orderItem, foundProducts);
        }

        // 프로모션이 적용되지 않은 상품만 2개라면
        return OrderStatus.inMultipleNormalProductStock(foundProducts);
    }

    private OrderStatus checkMixedProductsSituation(OrderItem orderItem, List<Product> foundProducts) {
        Product promotedProduct = getPromotedProduct(foundProducts).get();
        if (promotedProduct.isStockAvailable(orderItem.getQuantity())) { // 프로모션 제품 재고만으로 처리 가능하면
            boolean canGetFreeItem = promotedProduct.isCanGetFreeProduct(orderItem.getQuantity());
            int maxPromotionCanAppliedCount = promotedProduct.getMaxAvailablePromotionQuantity();
            return OrderStatus.inOnlyPromotionStock(promotedProduct, canGetFreeItem, maxPromotionCanAppliedCount);
        }

        // 프로모션 제품의 재고만으로 처리가 불가능하다면, 프로모션 재고는 일단 다 쓰고, 나머지 양은 비프로모션 재고로 처리
        int maxPromotionCanAppliedCount = promotedProduct.getMaxAvailablePromotionQuantity();
        return new OrderStatus(foundProducts, true, false, maxPromotionCanAppliedCount);
    }

    private boolean hasPromotedProduct(List<Product> products) {
        return products.stream().anyMatch(Product::isPromotedProduct);
    }

    private Optional<Product> getPromotedProduct(List<Product> products) {
        return products.stream().filter(Product::isPromotedProduct).findFirst();
    }

    private int getAllQuantity(List<Product> products) {
        return products.getFirst().getQuantity() + products.getLast().getQuantity();
    }

    private List<Product> findAvailableProductsByName(String productName) {
        return stock.stream().filter(product -> Objects.equals(product.getName(), productName))
                .filter(product -> product.getQuantity() > 0).toList();
    }
}
