package store.domain.order;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class OrderItemTest {
    @DisplayName("addQuantity()가 주어진 값을 수량에 추가하는지 확인")
    @ParameterizedTest()
    @CsvSource({
            "0, 1, 1",
            "5, 0, 5",
            "10, 10, 20",
    })
    void 주문_수량_추가_테스트(int initialQuantity, int addAmount, int expectedQuantity) {
        // given
        OrderItem orderItem = new OrderItem("과자", initialQuantity);

        // when
        orderItem.addQuantity(addAmount);

        // then
        assertThat(orderItem.getQuantity()).isEqualTo(expectedQuantity);
    }

    @DisplayName("subQuantity()가 주어진 값을 수량에서 빼는지 확인")
    @ParameterizedTest()
    @CsvSource({
            "5, 1, 4",
            "5, 5, 0",
            "10, 3, 7",
            "10, 0, 10",
    })
    void 주문_수량_감소_테스트(int initialQuantity, int subtractAmount, int expectedQuantity) {
        // given
        OrderItem orderItem = new OrderItem("과자", initialQuantity);

        // when
        orderItem.subQuantity(subtractAmount);

        // then
        assertThat(orderItem.getQuantity()).isEqualTo(expectedQuantity);
    }

    @DisplayName("addQuantity()가 음수 값을 수량에 추가하지 않는지 확인")
    @ParameterizedTest()
    @CsvSource({"0, -1, 0"})
    void 주문_음수_수량_추가_테스트(int initialQuantity, int addAmount, int expectedQuantity) {
        // given
        OrderItem orderItem = new OrderItem("과자", initialQuantity);

        // when
        orderItem.addQuantity(addAmount);

        // then
        assertThat(orderItem.getQuantity()).isEqualTo(expectedQuantity);
    }

    @DisplayName("subQuantity()가 음수 값을 수량에 추가하지 않는지 확인")
    @ParameterizedTest()
    @CsvSource({"5, -1, 5"})
    void 주문_음수_수량_감소_테스트(int initialQuantity, int subtractAmount, int expectedQuantity) {
        // given
        OrderItem orderItem = new OrderItem("과자", initialQuantity);

        // when
        orderItem.subQuantity(subtractAmount);

        // then
        assertThat(orderItem.getQuantity()).isEqualTo(expectedQuantity);
    }
}
