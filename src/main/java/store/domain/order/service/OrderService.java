package store.domain.order.service;

import store.domain.order.OrderItem;
import store.domain.order.OrderStatus;
import store.domain.product.Product;

public class OrderService {
    public void purchase(OrderStatus orderStatus, OrderItem orderItem) {
        if (orderStatus.isMultipleStock()) {
            purchaseMultipleStock(orderStatus, orderItem);
            return;
        }

        purchaseSingleStock(orderStatus, orderItem);
    }

    private void purchaseMultipleStock(OrderStatus orderStatus, OrderItem orderItem) {

    }

    private void purchaseSingleStock(OrderStatus orderStatus, OrderItem orderItem) {
        /*
        1. orderItem의 quantity 만큼 product의 quantity 차감
        2. 구입 정가와, 프로모션 적용 정가 반환
         */
        Product product = orderStatus.getSingleProduct();
        int buyQuantity = orderItem.getQuantity();
        product.sell(buyQuantity);
        product.getPurchasePrice(buyQuantity);
    }
}
