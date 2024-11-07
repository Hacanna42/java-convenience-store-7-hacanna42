package store.domain;

import java.util.List;
import store.domain.product.Product;

public class Stock {
    private final List<Product> stock;

    public Stock(List<Product> stock) {
        this.stock = stock;
    }
}
