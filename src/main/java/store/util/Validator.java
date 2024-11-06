package store.util;

public class Validator {
    // 상품명, 수량은 하이픈(-)으로, 개별 상품은 대괄호([])로 묶어 쉼표(,)로 구분되어 있는지 확인하는 정규식
    private static final String BUY_INPUT_REGEX = "^(\\[.+?-\\d+?\\],)*\\[.+?-\\d+?\\]$";

    public static void checkBuyInput(String input) {
        if (!input.matches(BUY_INPUT_REGEX)) {
            throw new IllegalArgumentException("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.");
        }
    }
}
