package store;

import store.domain.Stock;
import store.util.StoreInitializer;
import store.view.View;

public class Application {
    public static void main(String[] args) {
        // TODO: 프로그램 구현
        StoreInitializer storeInitializer = new StoreInitializer();
        Stock stock = storeInitializer.initStock();

        View view = View.getInstance();
        view.printGreetingMessage();
        view.promptBuyItems();

        // TODO: 재고 정보 받아서 View에서 출력
        view.printStockStatus(stock.getProducts());
    }
}
