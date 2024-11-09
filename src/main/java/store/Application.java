package store;

import store.domain.Stock;
import store.domain.order.service.OrderService;
import store.util.StoreInitializer;

public class Application {
    public static void main(String[] args) {
        Stock stock = new StoreInitializer().initStock();
        StoreService storeService = new StoreService(new OrderService());

        StoreController storeController = new StoreController(storeService, stock);
        storeController.run();
    }
}
