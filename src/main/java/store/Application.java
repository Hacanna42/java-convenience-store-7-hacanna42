package store;

import org.junit.jupiter.api.Order;
import store.domain.Stock;
import store.controller.StoreController;
import store.domain.order.service.OrderService;
import store.service.StoreService;
import store.util.StoreInitializer;

public class Application {
    public static void main(String[] args) {
        StoreInitializer storeInitializer = new StoreInitializer();
        Stock stock = storeInitializer.initStock();

        StoreService storeService = new StoreService(new OrderService());
        StoreController storeController = new StoreController(storeService, stock);
        storeController.run();
    }
}
