package store.messages;

public enum ErrorMessage {
    INVALID_INPUT("올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요."),
    INVALID_INPUT_OTHER("잘못된 입력입니다. 다시 입력해 주세요."),
    INVALID_PROMOTION_FILE_PARAMETERS("promotions.md의 파라미터 형식이 올바르지 않습니다."),
    INVALID_PROMOTION_FILE_FORM("올바르지 않은 promotions.md 형식입니다."),
    INVALID_PRODUCT_FILE_PARAMETERS("products.md의 파라미터 형식이 올바르지 않습니다."),
    INVALID_PRODUCT_FILE_FORM("올바르지 않은 products.md 형식입니다."),
    INVALID_PRODUCT_NAME("존재하지 않는 상품입니다. 다시 입력해 주세요."),
    INVALID_PRODUCT_PROMOTIONS("products.md 파일의 형식에 문제가 있습니다. 동일 상품에 여러 프로모션이 적용될 수 없습니다."),
    OVER_STOCK("재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요."),
    FILE_NOT_FOUND("편의점 구성 파일을 찾을 수 없습니다. 프로그램을 종료합니다.");

    private static final String ERROR_PREFIX = "[ERROR] ";
    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return ERROR_PREFIX + message;
    }
}
