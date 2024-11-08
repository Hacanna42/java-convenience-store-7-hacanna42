package store.domain;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import store.domain.order.OrderItem;
import store.domain.product.Product;

public class Stock {
    private final List<Product> stock;

    public Stock(List<Product> stock) {
        this.stock = stock;
    }

    public List<Product> getProducts() {
        return stock;
    }

    public OrderStatus getOrderStatus(OrderItem orderItem) {
        List<Product> foundProducts = findProductsByName(orderItem.getItemName());
        if (foundProducts.isEmpty()) {
            return OrderStatus.outOfStock();
        }

        if (foundProducts.size() == 1) {
            Product product = foundProducts.getFirst();
            if (!product.isStockAvailable(orderItem.getQuantity())) {
                return OrderStatus.outOfStock();
            }
            if (product.isPromotedProduct()) {
                // 프로모션을 통해 공짜로 하나를 얻을 수 있는 지 고려, 프로모션 재고 부족으로 적용 못 받는 개수가 있는지 고려.
                boolean canGetFreeItem  = product.isCanGetFreeProduct(orderItem.getQuantity());
                int maxPromotionCanAppliedCount = product.getMaxAvailablePromotionQuantity();
                return OrderStatus.inPromotionStock(product, canGetFreeItem, maxPromotionCanAppliedCount);
            }
            // 프로모션 제품이 아닌 경우
            return OrderStatus.inNormalStock(product);
        }

        if (foundProducts.size() == 2) {
            int allProductCountInStock = foundProducts.getFirst().getQuantity() + foundProducts.getLast().getQuantity();
            if (allProductCountInStock < orderItem.getQuantity()) { // 모든 상품을 합쳐도 재고 부족일 경우
                return OrderStatus.outOfStock();
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
            }

            // 프로모션 제품 재고만으로 처리 불가능하면
            // 프로모션 제품 재고로 일단 처리하고... 나머지는 일반 재고에서 처리하도록
            return OrderStatus.inMultipleProductStock(foundProducts);
        }

        throw new RuntimeException("TODO: ..에러 추가");
    }

    // isAvailableInStock 의 책임: 재고가 있는지 체크한다. (어떤 방식으로든 구매가 가능한지)
    public boolean isAvailableInStock(OrderItem orderItem) {
        List<Product> foundProducts = findProductsByName(orderItem.getItemName());
        if (foundProducts.isEmpty()) {
            return false;
        }
        if (foundProducts.size() == 1) {
            return foundProducts.getFirst().isStockAvailable(orderItem.getQuantity());
        }
        if (foundProducts.size() == 2) {
            int allProductCountInStock = foundProducts.getFirst().getQuantity() + foundProducts.getLast().getQuantity();
            return allProductCountInStock >= orderItem.getQuantity();
        }

        throw new RuntimeException("TODO: 검색된 상품 에러 안내 추가");
    }

    private boolean hasPromotedProduct(List<Product> products) {
        return products.stream().anyMatch(Product::isPromotedProduct);
    }

    private Optional<Product> getPromotedProduct(List<Product> products) {
        return products.stream().filter(Product::isPromotedProduct).findFirst();
    }

    private Optional<Product> getNotPromotedProduct(List<Product> products) {
        return products.stream().filter(product -> !product.isPromotedProduct()).findFirst();
    }

    private List<Product> findProductsByName(String productName) {
        return stock.stream()
                .filter(product -> Objects.equals(product.getName(), productName))
                .toList();
    }
}
