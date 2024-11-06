package store.view;

public class OutputView {
    public void printGreetingMessage() {
        System.out.println("안녕하세요. W편의점입니다.");
    }

    public void printStockStatus(/* 이곳에 재고 역할의 객체 추가 */) {
        printStockNoticeMessage();
        // TODO: 재고 역할에서 재고 정보를 얻어오기
    }

    private void printStockNoticeMessage() {
        System.out.println("현재 보유하고 있는 상품입니다.");
    }
}
