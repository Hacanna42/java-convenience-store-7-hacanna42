package store.util;

public class Validator {
    // 상품명, 수량은 하이픈(-)으로, 개별 상품은 대괄호([])로 묶어 쉼표(,)로 구분되어 있는지 확인하는 정규식
    private static final String BUY_INPUT_REGEX = "^(\\[.+?-\\d+?],)*\\[.+?-\\d+?]$";
    // 프로모션 파일의 형식이 "name,buy,get,start_date,end_date" 인지 확인하는 정규식
    private static final String PROMOTION_FIRST_LINE_REGEX = "^name,buy,get,start_date,end_date$";
    private static final String PROMOTION_INIT_LINE_REGEX = "^.+?,\\d+,\\d+,\\d{4}(-\\d\\d){2},\\d{4}(-\\d\\d){2}$";

    public static void checkBuyInput(String input) {
        if (!input.matches(BUY_INPUT_REGEX)) {
            throw new IllegalArgumentException("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.");
        }
    }

    public static void checkPromotionFirstLine(String line) {
        if (!line.matches(PROMOTION_FIRST_LINE_REGEX)) {
            throw new IllegalArgumentException("[ERROR] 올바르지 않은 promotions.md 형식입니다.1");
        }
    }

    public static void checkPromotionInitLine(String line) {
        if (!line.matches(PROMOTION_INIT_LINE_REGEX)) {
            throw new IllegalArgumentException("[ERROR] 올바르지 않은 promotions.md 형식입니다.2");
        }
    }
}
