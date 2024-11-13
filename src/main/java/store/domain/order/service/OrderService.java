package store.domain.order.service;

import java.util.List;
import java.util.Optional;
import store.domain.order.OrderItem;
import store.domain.order.OrderStatus;
import store.domain.product.Product;
import store.domain.receipt.BuyItem;
import store.domain.receipt.FreeItem;
import store.domain.receipt.Receipt;

public class OrderService {

    public void order(OrderStatus orderStatus, OrderItem orderItem, Receipt receipt) {
        if (orderStatus.isMultipleStock()) {
            purchaseMultipleStock(orderStatus, orderItem, receipt);
            return;
        }

        purchaseSingleStock(orderStatus.getFirstProduct(), orderItem, receipt);
    }

    private void purchaseMultipleStock(OrderStatus orderStatus, OrderItem orderItem, Receipt receipt) {
        if (orderStatus.hasPromotionProduct()) {
            purchaseMixedStock(orderStatus, orderItem, receipt);
            return;
        }
        purchaseMultipleNormalStock(orderStatus, orderItem, receipt);
    }

    private void purchaseMixedStock(OrderStatus orderStatus, OrderItem orderItem, Receipt receipt) {
        List<Product> products = orderStatus.getMultipleProducts();
        Product promotionProduct = products.stream().filter(Product::isPromotedProduct).findFirst().get();
        Product notPromotionProduct = products.stream().filter(product -> !product.isPromotedProduct()).findFirst()
                .get();

        updateReceiptInMixedProducts(orderItem, receipt, promotionProduct, notPromotionProduct);
    }

    private static void updateReceiptInMixedProducts(OrderItem orderItem, Receipt receipt, Product promotionProduct,
                                                     Product notPromotionProduct) {
        int maxQuantityOfPromotionProduct = promotionProduct.getQuantity();

        promotionProduct.sell(maxQuantityOfPromotionProduct);
        notPromotionProduct.sell(orderItem.getQuantity() - maxQuantityOfPromotionProduct);

        receipt.addPriceOfPromotionItem(promotionProduct.getRegularPurchasePrice(maxQuantityOfPromotionProduct));
        updateReceiptBuyItem(receipt, orderItem, orderItem.getQuantity(),
                promotionProduct.getRegularPurchasePrice(orderItem.getQuantity()));
        updateReceiptFreeItem(receipt, orderItem,
                promotionProduct.getPromotionDiscountAmount(maxQuantityOfPromotionProduct),
                promotionProduct.getPromotionDiscountPrice(maxQuantityOfPromotionProduct));
    }

    private void purchaseMultipleNormalStock(OrderStatus orderStatus, OrderItem orderItem, Receipt receipt) {
        List<Product> products = orderStatus.getMultipleProducts();
        Optional<Product> optionalProduct = products.stream()
                .filter(product -> product.isStockAvailable(orderItem.getQuantity())).findFirst();

        if (optionalProduct.isPresent()) {
            purchaseSingleStock(optionalProduct.get(), orderItem, receipt);
            return;
        }

        updateReceiptInMultipleNormalProducts(orderItem, receipt, products);
    }

    private static void updateReceiptInMultipleNormalProducts(OrderItem orderItem, Receipt receipt,
                                                              List<Product> products) {
        int currentRegularPrice = 0;

        int maxQuantityOfFirstProduct = products.getFirst().getQuantity();
        products.getFirst().sell(maxQuantityOfFirstProduct);
        currentRegularPrice += products.getFirst().getRegularPurchasePrice(maxQuantityOfFirstProduct);
        int remainBuyQuantity = orderItem.getQuantity() - maxQuantityOfFirstProduct;
        products.getLast().sell(remainBuyQuantity);
        currentRegularPrice += products.getLast().getRegularPurchasePrice(remainBuyQuantity);

        updateReceiptBuyItem(receipt, orderItem, orderItem.getQuantity(), currentRegularPrice);
    }

    private void purchaseSingleStock(Product product, OrderItem orderItem, Receipt receipt) {
        int buyQuantity = orderItem.getQuantity();
        if (buyQuantity == 0) {
            return;
        }
        updateReceiptBuyItem(receipt, orderItem, buyQuantity, product.getRegularPurchasePrice(buyQuantity));
        if (product.isPromotedProduct()) {
            updateReceiptFreeItem(receipt, orderItem, product.getPromotionDiscountAmount(buyQuantity),
                    product.getPromotionDiscountPrice(buyQuantity));
            receipt.addPriceOfPromotionItem(product.getRegularPurchasePrice(buyQuantity));
        }
        product.sell(buyQuantity);
    }

    private static void updateReceiptBuyItem(Receipt receipt, OrderItem orderItem, int buyQuantity,
                                             int regularPurchasePrice) {
        BuyItem item = new BuyItem(orderItem.getItemName(), buyQuantity, regularPurchasePrice);
        receipt.addBuyItem(item);
    }

    private static void updateReceiptFreeItem(Receipt receipt, OrderItem orderItem, int discountedAmount,
                                              int discountedPrice) {
        FreeItem item = new FreeItem(orderItem.getItemName(), discountedAmount, discountedPrice);
        receipt.addFreeItem(item);
    }
}
