package store;

import store.util.StoreInitializer;
import store.view.View;

public class Application {
    public static void main(String[] args) {
        // TODO: 프로그램 구현
        StoreInitializer storeInitializer = new StoreInitializer();
        storeInitializer.initializeStore();
        View view = View.getInstance();
        view.printGreetingMessage();
        view.promptBuyItems();
    }
}
