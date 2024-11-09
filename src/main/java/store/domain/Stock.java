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
        if (foundProducts.isEmpty()) {
            return OrderStatus.outOfStock(foundProducts);
        }

        if (foundProducts.size() == 1) {
            Product product = foundProducts.getFirst();
            if (!product.isStockAvailable(orderItem.getQuantity())) {
                return OrderStatus.outOfStock(List.of(product));
            }
            if (product.isPromotedProduct()) {
                // 프로모션을 통해 공짜로 하나를 얻을 수 있는 지 고려, 프로모션 재고 부족으로 적용 못 받는 개수가 있는지 고려.
                boolean canGetFreeItem = product.isCanGetFreeProduct(orderItem.getQuantity());
                int maxPromotionCanAppliedCount = product.getMaxAvailablePromotionQuantity();
                return OrderStatus.inPromotionStock(product, canGetFreeItem, maxPromotionCanAppliedCount);
            }
            // 프로모션 제품이 아닌 경우
            return OrderStatus.inNormalStock(product);
        }

        if (foundProducts.size() == 2) {
            int allProductCountInStock = foundProducts.getFirst().getQuantity() + foundProducts.getLast().getQuantity();
            if (allProductCountInStock < orderItem.getQuantity()) { // 모든 상품을 합쳐도 재고 부족일 경우
                return OrderStatus.outOfStock(foundProducts);
            }

            // 하나는 프로모션 적용 가능 제품인 경우: 프로모션 안에서만 처리 가능한지 체크
            if (hasPromotedProduct(foundProducts)) {
                // 프로모션을 통해 공짜로 하나를 얻을 수 있는 지 고려, 프로모션 재고 부족으로 적용 못 받는 개수가 있는지 고려.
                Product promotedProduct = getPromotedProduct(foundProducts).get();
                if (promotedProduct.isStockAvailable(orderItem.getQuantity())) { // 프로모션 제품 재고만으로 처리 가능하면
                    boolean canGetFreeItem = promotedProduct.isCanGetFreeProduct(orderItem.getQuantity());
                    int maxPromotionCanAppliedCount = promotedProduct.getMaxAvailablePromotionQuantity();
                    return OrderStatus.inPromotionStock(promotedProduct, canGetFreeItem, maxPromotionCanAppliedCount);
                }

                // 프로모션 제품 재고만으로 처리 불가능할 때
                // 프로모션 재고는 일단 다 쓰고, 나머지 양은 비프로모션 재고로 해야 함
                int maxPromotionCanAppliedCount = promotedProduct.getMaxAvailablePromotionQuantity();
                return new OrderStatus(foundProducts, true, false, maxPromotionCanAppliedCount);
            }

            // 비-프로모션 제품 재고만 있을 때
            return OrderStatus.inMultipleNormalProductStock(foundProducts);
        }

        throw new IllegalArgumentException(ErrorMessage.INVALID_PRODUCT_PROMOTIONS.getMessage());
    }

    private boolean hasPromotedProduct(List<Product> products) {
        return products.stream().anyMatch(Product::isPromotedProduct);
    }

    private Optional<Product> getPromotedProduct(List<Product> products) {
        return products.stream().filter(Product::isPromotedProduct).findFirst();
    }

    private List<Product> findAvailableProductsByName(String productName) {
        return stock.stream()
                .filter(product -> Objects.equals(product.getName(), productName))
                .filter(product -> product.getQuantity() > 0)
                .toList();
    }
}
