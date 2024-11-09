package store.domain.order.service;

import store.domain.order.OrderItem;
import store.domain.order.OrderStatus;
import store.domain.product.Product;
import store.domain.receipt.BuyItem;
import store.domain.receipt.FreeItem;
import store.domain.receipt.Receipt;

public class OrderService {

    public void purchase(OrderStatus orderStatus, OrderItem orderItem, Receipt receipt) {
        if (orderStatus.isMultipleStock()) {
            purchaseMultipleStock(orderStatus, orderItem, receipt);
            return;
        }

        purchaseSingleStock(orderStatus, orderItem, receipt);
    }

    private void purchaseMultipleStock(OrderStatus orderStatus, OrderItem orderItem, Receipt receipt) {

    }

    private void purchaseSingleStock(OrderStatus orderStatus, OrderItem orderItem, Receipt receipt) {
        Product product = orderStatus.getSingleProduct();
        int buyQuantity = orderItem.getQuantity();

        BuyItem buyItem = new BuyItem(orderItem.getItemName(), buyQuantity, product.getRegularPurchasePrice(buyQuantity));
        receipt.addBuyItem(buyItem);
        if (product.isPromotedProduct()) {
            int discountedAmount = product.getPromotionDiscountAmount(buyQuantity);
            int discountedPrice = product.getPromotionDiscountPrice(buyQuantity);
            FreeItem freeItem = new FreeItem(orderItem.getItemName(), discountedAmount, discountedPrice);
            receipt.addFreeItem(freeItem);
        }

        product.sell(buyQuantity);
    }
}
