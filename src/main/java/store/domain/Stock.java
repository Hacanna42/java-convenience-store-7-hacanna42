package store.domain;

import java.util.List;
import java.util.Objects;
import store.domain.product.Product;

public class Stock {
    private final List<Product> stock;

    public Stock(List<Product> stock) {
        this.stock = stock;
    }

    public List<Product> getProducts() {
        return stock;
    }

    public Product getProduct(String productName) {
        findProductByName(productName);
        return ..
    }

    private Product findProductByName(String productName) {
        List<Product> products = stock.stream()
                .filter(product -> Objects.equals(product.getName(), productName))
                .toList();

        // 프로모션 적용 상품과 미적용 상품 두개가 있을 수 있음.
        // 프로모션이 적용 되어있는 걸 먼저 가져와야 하고, 만약 재고가 부족하면 고지 이후 비프로모션 재고로 해야 함.
        // 그러면 수량도 파라미터로 입력되게 만드는 편이 나을까 .?
    }
}
