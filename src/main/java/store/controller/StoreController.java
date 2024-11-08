package store.controller;

import store.domain.Stock;
import store.service.StoreService;
import store.domain.order.OrderItems;
import store.view.View;

public class StoreController {
    private final StoreService storeService;
    private final Stock stock;

    public StoreController(StoreService storeService, Stock stock) {
        this.storeService = storeService;
        this.stock = stock;
    }

    public void run() {
        printGreetingMessage();
        printStockStatus(stock);
        OrderItems orderItems = getOrderItems();
        storeService.proceedPurchase(stock, orderItems);
    }

    private OrderItems getOrderItems() {
        String input = View.getInstance().promptBuyItems();
        return storeService.getOrderItems(input);
    }

    private void printGreetingMessage() {
        View.getInstance().printGreetingMessage();
    }

    private void printStockStatus(Stock stock) {
        View.getInstance().printStockStatus(stock.getProducts());
    }
}
