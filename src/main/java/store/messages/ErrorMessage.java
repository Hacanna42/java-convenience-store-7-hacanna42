package store.messages;

public enum ErrorMessage {
    INVALID_INPUT("올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요."),
    INVALID_INPUT_OTHER("잘못된 입력입니다. 다시 입력해 주세요."),
    INVALID_PROMOTION_FILE_PARAMETERS("promotions.md의 파라미터 형식이 올바르지 않습니다."),
    INVALID_PROMOTION_FILE_FORM("올바르지 않은 promotions.md 형식입니다."),
    INVALID_PRODUCT_FILE_PARAMETERS("products.md의 파라미터 형식이 올바르지 않습니다."),
    INVALID_PRODUCT_FILE_FORM("올바르지 않은 products.md 형식입니다."),
    INVALID_PRODUCT_NAME("존재하지 않는 상품입니다. 다시 입력해 주세요."),
    OVER_STOCK("재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.");

    private static final String ERROR_PREFIX = "[ERROR] ";
    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return ERROR_PREFIX + message;
    }
}
