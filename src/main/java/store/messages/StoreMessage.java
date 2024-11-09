package store.messages;

public enum StoreMessage {
    GREETING("안녕하세요. W편의점입니다."),
    BUY_REQUEST("구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])"),
    STOCK_NOTICE("현재 보유하고 있는 상품입니다."),
    ASK_FREE_PROMOTION("현재 %s은(는) %d개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)%n"),
    ASK_INSUFFICIENT_PROMOTION("현재 %s %d개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)%n"),
    ASK_MEMBERSHIP_DISCOUNT("멤버십 할인을 받으시겠습니까? (Y/N)"),
    ASK_CONTINUE_SHOPPING("감사합니다. 구매하고 싶은 다른 상품이 있나요? (Y/N)"),

    STOCK_STATUS("- %s %,d원 %,d개 %s%n"),
    STOCK_STATUS_NO_STOCK("- %s %,d원 재고 없음 %s%n");

    private final String message;

    StoreMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
