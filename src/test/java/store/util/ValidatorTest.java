package store.util;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class ValidatorTest {
    @Nested
    class 상품명_테스트 {
        @DisplayName("상품명, 수량은 하이픈(-)으로, 개별 상품은 대괄호([])로 묶어 쉼표(,)로 구분되어 있어야 한다.")
        @ParameterizedTest
        @ValueSource(strings = {"[콜라-3]", "[콜라-3],[에너지바-5]"})
        void 올바른_상품명_테스트(String input) {
            assertThatNoException().isThrownBy(() -> Validator.checkBuyInput(input));
        }

        @DisplayName("상품명, 수량은 하이픈(-)으로, 개별 상품은 대괄호([])로 묶어 쉼표(,)로 구분되어 있지 않으면 오류가 발생한다.")
        @ParameterizedTest
        @ValueSource(strings = {"[콜라-3개]", "[콜라-3개][에너지바-5]", "[3-에너지바]"})
        void 잘못된_상품명_테스트(String input) {
            assertThatIllegalArgumentException().isThrownBy(() -> Validator.checkBuyInput(input));
        }
    }
}
