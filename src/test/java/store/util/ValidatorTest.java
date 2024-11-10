package store.util;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
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

    @Nested
    class 프로모션_파일_테스트 {
        @DisplayName("promotions.md 파일 첫번째 줄은 양식과 동일해야 한다.")
        @ParameterizedTest
        @ValueSource(strings = {"name,buy,get,start_date,end_date"})
        void 올바른_프로모션_파일_첫번째줄_테스트(String firstLine) {
            assertThatNoException().isThrownBy(() -> Validator.checkPromotionFirstLine(firstLine));
        }

        @DisplayName("promotions.md 파일 첫번째 줄이 양식과 동일하지 않으면 오류가 발생한다.")
        @ParameterizedTest
        @ValueSource(strings = {"name,buy,get,period"})
        void 잘못된_프로모션_파일_첫째줄_테스트(String firstLine) {
            assertThatIllegalArgumentException().isThrownBy(() -> Validator.checkPromotionFirstLine(firstLine));
        }

        @DisplayName("promotions.md 파일의 프로모션 초기화 줄은 양식과 동일해야 한다.")
        @ParameterizedTest
        @ValueSource(strings = {"탄산2+1,2,1,2024-01-01,2024-12-31"})
        void 올바른_프로모션_파일_초기화줄_테스트(String line) {
            assertThatNoException().isThrownBy(() -> Validator.checkPromotionInitLine(line));
        }

        @DisplayName("promotions.md 파일의 프로모션 초기화 줄은 양식과 동일하지 않으면 오류가 발생한다.")
        @ParameterizedTest
        @ValueSource(strings = {"탄산2+1,2,1,2024-01-01~2024-12-31"})
        void 잘못된_프로모션_파일_초기화줄_테스트(String line) {
            assertThatIllegalArgumentException().isThrownBy(() -> Validator.checkPromotionInitLine(line));
        }
    }

    @Nested
    class 상품_파일_테스트 {
        @DisplayName("products.md 파일 첫번째 줄이 양식과 동일하지 않으면 오류가 발생한다.")
        @ParameterizedTest
        @ValueSource(strings = {"name,price,quantity,promotion"})
        void 올바른_상품_파일_첫번째줄_테스트(String firstLine) {
            assertThatNoException().isThrownBy(() -> Validator.checkProductFirstLine(firstLine));
        }

        @DisplayName("products.md 파일 첫번째 줄은 양식과 동일해야 한다.")
        @ParameterizedTest
        @ValueSource(strings = {"name,price,quantity,event"})
        void 잘못된_상품_파일_첫번째줄_테스트(String firstLine) {
            assertThatIllegalArgumentException().isThrownBy(() -> Validator.checkProductFirstLine(firstLine));
        }

        @DisplayName("products.md 파일의 상품 초기화 줄은 양식과 동일해야 한다.")
        @ParameterizedTest
        @ValueSource(strings = {"콜라,1000,10,탄산2+1"})
        void 올바른_상품_파일_초기화줄_테스트(String line) {
            assertThatNoException().isThrownBy(() -> Validator.checkProductInitLine(line));
        }

        @DisplayName("products.md 파일의 상품 초기화 줄은 양식과 동일하지 않으면 오류가 발생한다.")
        @ParameterizedTest
        @ValueSource(strings = {"콜라,1000,10,탄산2+1,이벤트"})
        void 잘못된_상품_파일_초기화줄_테스트(String line) {
            assertThatIllegalArgumentException().isThrownBy(() -> Validator.checkProductInitLine(line));
        }
    }
}
