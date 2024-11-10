package store.domain;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import store.domain.order.OrderItem;
import store.domain.order.OrderStatus;
import store.domain.product.Product;
import store.domain.product.ProductParameter;
import store.messages.ErrorMessage;

public class Stock {
    private final List<Product> stock;

    public Stock(List<Product> stock) {
        this.stock = stock;
    }

    public List<Product> getProducts() {
        return stock;
    }

    /* generateNormalProductFromOnlyPromotionProduct() 메서드의 존재 이유
     * 사실 납득은 잘 가지 않지만 우아한테크코스의 실행 결과 예시에 따르면 프로모션이 적용된 모든 상품은, 프로모션이 적용되지 않은 버전의 상품 정보도 표시해야 함.
     * 따라서, 프로모션이 적용된 상품에 대해서 프로모션이 적용되지 않은 일반 상품이 존재하지 않으면 일반 상품을 생성함.
     */
    public void generateNormalProductFromOnlyPromotionProduct() {
        List<Product> onlyPromotionProducts = findOnlyPromotionProducts(stock);
        for (Product product : onlyPromotionProducts) {
            ProductParameter productParameter = new ProductParameter(
                    List.of(product.getName(), String.valueOf(product.getPrice()), "0", "null"));
            insertProduct(new Product(productParameter, null));
        }
    }

    public OrderStatus getOrderStatus(OrderItem orderItem) {
        List<Product> foundProducts = findProductsByName(orderItem.getItemName());
        if (foundProducts.isEmpty()) {
            return OrderStatus.outOfStock(foundProducts);
        }

        return checkOrderAvailability(orderItem, findAvailableProductsByName(orderItem.getItemName()));
    }

    private OrderStatus checkOrderAvailability(OrderItem orderItem, List<Product> foundAvailableProducts) {
        if (foundAvailableProducts.isEmpty()) {
            return OrderStatus.outOfStock(findProductsByName(orderItem.getItemName()));
        }
        if (foundAvailableProducts.size() == 1) {
            return getOrderStatusWithSingleProduct(orderItem, foundAvailableProducts.getFirst());
        }
        if (foundAvailableProducts.size() == 2) {
            return getOrderStatusWithMultipleProducts(orderItem, foundAvailableProducts);
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

    private List<Product> findProductsByName(String productName) {
        return stock.stream().filter(product -> Objects.equals(product.getName(), productName)).toList();
    }

    private List<Product> findAvailableProductsByName(String productName) {
        return stock.stream().filter(product -> Objects.equals(product.getName(), productName))
                .filter(product -> product.getQuantity() > 0).toList();
    }

    private int findProductIndexByName(String productName) {
        for (int i = 0; i < stock.size(); i++) {
            if (stock.get(i).getName().equals(productName)) {
                return i;
            }
        }
        return -1;
    }

    private void insertProduct(Product product) {
        int index = findProductIndexByName(product.getName());
        if (index != -1) {
            // 동일한 이름의 상품이 존재하는 경우, 그 상품 다음 위치에 추가
            stock.add(index + 1, product);
            return;
        }
        stock.add(product);
    }

    private List<Product> findOnlyPromotionProducts(List<Product> products) {
        Map<String, List<Product>> productByName = products.stream()
                .collect(Collectors.groupingBy(Product::getName));

        return productByName.values().stream()
                .filter(productList -> productList.size() == 1) // 유일하게 하나만 존재하는 이름의 상품들 필터링
                .flatMap(Collection::stream)
                .filter(Product::isPromotedProduct)
                .collect(Collectors.toList());
    }
}
