package store.domain;

import java.util.List;
import java.util.Objects;
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

    public boolean isAvailableInStock(OrderItem orderItem) {
        List<Product> foundProducts = findProductsByName(orderItem.getItemName());
        if (foundProducts.isEmpty()) {
            return false;
        }

        if (foundProducts.size() == 1) {
            Product product = foundProducts.getFirst();
            return product.isAvailableBuyQuantity(orderItem.getQuantity());
        }

        if (foundProducts.size() == 2) {
            if (hasPromotedProduct(foundProducts)) {
//                Product product = foundProducts.stream().filter(Product::isPromotedProduct).findFirst().get();
            }

        }
        // 상품이 2개 검색된 경우 (프로모션과 비 프로모션)


        /* 프로모션 관계 없이 해당 상품 수량 부족시 구매 실패 처리
         * 1. 찾아진 상품이 0개인 경우: 상품 없음.
         * 2. 찾아진 상품이 1개인 경우: 프로모션 상품인가? 비프로모션 상품인가?
         * 2-1. 프로모션 상품인 경우: 수량을 체크한다.
         * 2-1-1. 수량이 충분한 경우: 구매
         * 2-1-2. 수량이 불충분한 경우: 구매 실패
         *
         * 2-2. 비프로모션 상품인 경우: 수량을 체크한다.
         * 2-2-1. 수량이 충분한 경우: 구매
         * 2-2-2. 수량이 불충분한 경우: 구매 실패
         *
         * 3. 찾아진 상품이 2개인 경우: 프로모션 상품과 비 프로모션 상품이 같이 있는 경우. (우테코 본문: 동일 상품에 여러 프로모션이 적용되지 않는다)
         * 3-1. 프로모션 상품의 수량을 체크한다
         * 3-1-1. 프로모션 상품의 수량이 충분한 경우: 구매
         * 3-1-2. 프로모션 상품의 수량이 불충분한 경우: 최대한 받을 수 있는 개수 계산 (예를 들어 2+1 콜라가 7개라면 6개까지밖에 적용을 못 받음 6+3으로)
         * 3-1-3. 나머지 개수를 비프로모션 재고로 커버할 수 있는지 파악한다. 커버 가능하다면 결제 성공, 불가능하다면 실패.
         */
    }

    private boolean hasPromotedProduct(List<Product> products) {
        return products.stream().anyMatch(Product::isPromotedProduct);
    }

    private List<Product> findProductsByName(String productName) {
        return stock.stream()
                .filter(product -> Objects.equals(product.getName(), productName))
                .toList();
    }
}
