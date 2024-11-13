package store.messages;

public enum ReceiptForm {
    HEADER("==============W 편의점================"),
    TITLES("%-15s\t%-10s\t%s%n"),
    BUY_ITEMS("%-15s\t%-10d\t%,d%n"),
    PROMOTION_DIVIDER("==============증   정================"),
    PROMOTION_ITEMS("%-15s\t%d%n"),
    FINAL_DIVIDER("===================================="),

    TOTAL_PRICE("%-15s\t%-10d\t%,d%n"),
    PROMOTION_DISCOUNT("%-25s\t\t-%,d%n"),
    MEMBERSHIP_DISCOUNT("%-25s\t\t-%,d%n"),
    FINAL_PRICE("%-25s\t\t%,d%n"),

    WORD_ITEM_NAME("상품명"),
    WORD_ITEM_QUANTITY("수량"),
    WORD_ITEM_PRICE("금액"),
    WORD_OUT_OF_STOCK("재고 없음"),
    WORD_TOTAL_PRICE("총구매액"),
    WORD_PROMOTION_DISCOUNT("행사할인"),
    WORD_MEMBERSHIP_DISCOUNT("멤버십할인"),
    WORD_FINAL_PRICE("내실돈");

    private final String message;

    ReceiptForm(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
