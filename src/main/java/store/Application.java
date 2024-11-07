package store;

import store.domain.Stock;
import store.domain.StoreController;
import store.domain.StoreService;
import store.util.StoreInitializer;

public class Application {
    public static void main(String[] args) {
        // 재고 초기화
        StoreInitializer storeInitializer = new StoreInitializer();
        Stock stock = storeInitializer.initStock();

        StoreService storeService = new StoreService();
        StoreController storeController = new StoreController(storeService, stock);
        storeController.run();
    }
}
