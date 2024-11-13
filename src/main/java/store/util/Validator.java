package store.util;

import store.messages.ErrorMessage;

/**
 * Validator 는 사용자의 입력 검증이나 파일 검증을 처리하는 유틸성 클래스입니다.
 */
public class Validator {
    // 상품명, 수량은 하이픈(-)으로, 개별 상품은 대괄호([])로 묶어 쉼표(,)로 구분되어 있는지 확인하는 정규식
    private static final String BUY_INPUT_REGEX = "^(\\[[^,\\[\\]]+?-\\d+?],)*\\[[^,\\[\\]]+?-\\d+?]$";

    // 프로모션 파일의 형식이 "name,buy,get,start_date,end_date" 인지 확인하는 정규식
    private static final String PROMOTION_FIRST_LINE_REGEX = "^name,buy,get,start_date,end_date$";
    private static final String PROMOTION_INIT_LINE_REGEX = "^[^,]+?,\\d+,\\d+,\\d{4}(-\\d\\d){2},\\d{4}(-\\d\\d){2}$";

    // 상품 파일의 형식이 "name,price,quantity,promotion" 인지 확인하는 정규식
    private static final String PRODUCT_FIRST_LINE_REGEX = "^name,price,quantity,promotion$";
    private static final String PRODUCT_INIT_LINE_REGEX = "^[^,]+?,\\d+?,\\d+?,[^,]+?$";

    public static void checkBuyInput(String input) {
        if (!input.matches(BUY_INPUT_REGEX)) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_INPUT.getMessage());
        }
    }

    public static void checkPromotionFirstLine(String firstLine) {
        if (!firstLine.matches(PROMOTION_FIRST_LINE_REGEX)) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_PROMOTION_FILE_PARAMETERS.getMessage());
        }
    }

    public static void checkPromotionInitLine(String line) {
        if (!line.matches(PROMOTION_INIT_LINE_REGEX)) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_PROMOTION_FILE_FORM.getMessage());
        }
    }

    public static void checkProductFirstLine(String firstLine) {
        if (!firstLine.matches(PRODUCT_FIRST_LINE_REGEX)) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_PRODUCT_FILE_PARAMETERS.getMessage());
        }
    }

    public static void checkProductInitLine(String line) {
        if (!line.matches(PRODUCT_INIT_LINE_REGEX)) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_PRODUCT_FILE_FORM.getMessage());
        }
    }
}
