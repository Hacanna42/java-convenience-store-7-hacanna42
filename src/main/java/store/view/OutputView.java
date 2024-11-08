package store.view;

import java.util.List;
import store.domain.Stock;
import store.domain.product.Product;

class OutputView {
    protected void printGreetingMessage() {
        System.out.println("안녕하세요. W편의점입니다.");
    }

    protected void printBuyRequestMessage() {
        newLine();
        System.out.println("구매하실 상품명과 수량을 입력해 주세요.");
    }

    protected void printStockStatus(List<Product> products) {
        printStockNoticeMessage();
        newLine();
        for (Product product : products) {
            System.out.println("- " + product.toString());
        }
    }

    private void printStockNoticeMessage() {
        System.out.println("현재 보유하고 있는 상품입니다.");
    }

    private void newLine() {
        System.out.println();
    }
}
