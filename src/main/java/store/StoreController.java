package store;

import store.domain.Stock;
import store.domain.receipt.Receipt;
import store.domain.order.OrderItems;
import store.messages.ErrorMessage;
import store.view.View;

public class StoreController {
    private final StoreService storeService;
    private final Stock stock;

    public StoreController(StoreService storeService, Stock stock) {
        this.storeService = storeService;
        this.stock = stock;
    }

    public void run() {
        do {
            runStoreProcess();
        } while (askContinueShopping());
    }

    private void runStoreProcess() {
        printGreetingMessage();
        printStockStatus(stock);
        OrderItems orderItems = getOrderItems();
        try {
            Receipt receipt = proceedPurchase(orderItems);
            printReceipt(receipt);
        } catch (Exception e) {
            printErrorMessage(e.getMessage());
        }
    }

    private Receipt proceedPurchase(OrderItems orderItems) {
        return storeService.proceedPurchase(stock, orderItems);
    }

    private void printErrorMessage(String errorMessage) {
        View.getInstance().printErrorMessage(errorMessage);
    }

    private void printReceipt(Receipt receipt) {
        View.getInstance().printReceipt(receipt);
    }

    private boolean askContinueShopping() {
        return View.getInstance().promptContinueShopping();
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
