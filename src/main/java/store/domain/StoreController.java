package store.domain;

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
        OrderItems orderItems = getOrderItems();
    }

    // TODO: 사용자에게 원하는 수량을 입력 받고 배열로 반환
    private OrderItems getOrderItems() {
        String input = View.getInstance().promptBuyItems();
        return storeService.getOrderItems(input);
    }

    private void printGreetingMessage() {
        View.getInstance().printGreetingMessage();
    }
}
