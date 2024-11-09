package store;

import java.io.FileNotFoundException;
import store.domain.Stock;
import store.domain.order.service.OrderService;
import store.messages.ErrorMessage;
import store.util.StoreInitializer;
import store.view.View;

public class Application {
    public static void main(String[] args) {
        try {
            Stock stock = new StoreInitializer().initStock();
            StoreService storeService = new StoreService(new OrderService());

            StoreController storeController = new StoreController(storeService, stock);
            storeController.run();
        } catch (FileNotFoundException e) {
            View.getInstance().printErrorMessage(ErrorMessage.FILE_NOT_FOUND.getMessage());
        }
    }
}
