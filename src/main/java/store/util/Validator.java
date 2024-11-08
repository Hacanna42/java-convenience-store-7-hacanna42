package store.util;

public class Validator {
    // 상품명, 수량은 하이픈(-)으로, 개별 상품은 대괄호([])로 묶어 쉼표(,)로 구분되어 있는지 확인하는 정규식
    private static final String BUY_INPUT_REGEX = "^(\\[[^,\\[\\]]+?-\\d+?],)*\\[[^,\\[\\]]+?-\\d+?]$";
    // 프로모션 파일의 형식이 "name,buy,get,start_date,end_date" 인지 확인하는 정규식
    private static final String PROMOTION_FIRST_LINE_REGEX = "^name,buy,get,start_date,end_date$";
    private static final String PROMOTION_INIT_LINE_REGEX = "^.+?,\\d+,\\d+,\\d{4}(-\\d\\d){2},\\d{4}(-\\d\\d){2}$";
    // 상품 파일의 형식이 "name,price,quantity,promotion" 인지 확인하는 정규식
    private static final String PRODUCT_FIRST_LINE_REGEX = "^name,price,quantity,promotion$";
    private static final String PRODUCT_INIT_LINE_REGEX = "^.+?,\\d+?,\\d+?,.+?$";

    public static void checkBuyInput(String input) {
        if (!input.matches(BUY_INPUT_REGEX)) {
            throw new IllegalArgumentException("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.");
        }
    }

    public static void checkPromotionFirstLine(String firstLine) {
        if (!firstLine.matches(PROMOTION_FIRST_LINE_REGEX)) {
            throw new IllegalArgumentException("[ERROR] promotions.md의 파라미터 형식이 올바르지 않습니다.");
        }
    }

    public static void checkPromotionInitLine(String line) {
        if (!line.matches(PROMOTION_INIT_LINE_REGEX)) {
            throw new IllegalArgumentException("[ERROR] 올바르지 않은 promotions.md 형식입니다.");
        }
    }

    public static void checkProductFirstLine(String firstLine) {
        if (!firstLine.matches(PRODUCT_FIRST_LINE_REGEX)) {
            throw new IllegalArgumentException("[ERROR] products.md의 파라미터 형식이 올바르지 않습니다.");
        }
    }

    public static void checkProductInitLine(String line) {
        if (!line.matches(PRODUCT_INIT_LINE_REGEX)) {
            throw new IllegalArgumentException("[ERROR] 올바르지 않은 products.md 형식입니다.");
        }
    }

    // TODO: 그냥 상품명에 반점을 금지해야 할지 생각하기
}
