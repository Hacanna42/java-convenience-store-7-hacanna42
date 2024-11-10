package store.domain.order;

import java.util.NoSuchElementException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import store.domain.product.Product;

import java.util.List;
import store.domain.product.ProductParameter;
import store.domain.promotion.Promotion;
import store.domain.promotion.PromotionParameter;

import static org.assertj.core.api.Assertions.*;

class OrderStatusTest {

    private Product createProduct(String name, boolean isPromoted) {
        ProductParameter productParameter = new ProductParameter(List.of(name, "0", "0", "promotion"));
        PromotionParameter promotionParameter = new PromotionParameter(
                List.of("promotionName", "0", "0", "2024-11-10", "2024-11-10"));
        if (isPromoted) {
            return new Product(productParameter, new Promotion(promotionParameter));
        }
        return new Product(productParameter, null);
    }

    @DisplayName("OrderStatus 생성자 필드 초기화 테스트")
    @ParameterizedTest(name = "재고 상태: {1}, 무료 증정 가능 여부: {2}, 프로모션 적용 가능 수량: {3}")
    @CsvSource({
            "Product A, true, true, 3",
            "Product B, false, false, 0"
    })
    void 생성자_필드_초기화_테스트(String productName, boolean inStock, boolean canGetFreeItem, int promotionCanAppliedCount) {
        List<Product> products = List.of(createProduct(productName, false));

        OrderStatus orderStatus = new OrderStatus(products, inStock, canGetFreeItem, promotionCanAppliedCount);

        assertThat(orderStatus.getMultipleProducts()).containsExactlyElementsOf(products);
        assertThat(orderStatus.isInStock()).isEqualTo(inStock);
        assertThat(orderStatus.isCanGetFreeItem()).isEqualTo(canGetFreeItem);
        assertThat(orderStatus.getNotAppliedItemCount(5)).isEqualTo(Math.max(0, 5 - promotionCanAppliedCount));
    }

    @DisplayName("getFirstProduct() 호출 시 빈 리스트일 경우 예외 발생")
    @ParameterizedTest(name = "재고 여부: {0}")
    @CsvSource({"true", "false"})
    void 빈_리스트_첫번째_상품_예외_테스트(boolean inStock) {
        OrderStatus orderStatus = new OrderStatus(List.of(), inStock);
        assertThatExceptionOfType(NoSuchElementException.class).isThrownBy(orderStatus::getFirstProduct);
    }

    @DisplayName("removeNormalProduct()가 정상 상품을 제거하는지 테스트")
    @Test
    void 정상상품_제거_테스트() {
        List<Product> products = List.of(
                createProduct("Normal Product", false),
                createProduct("Promoted Product", true)
        );
        OrderStatus orderStatus = new OrderStatus(products, true);
        orderStatus.removeNormalProduct();

        assertThat(orderStatus.getMultipleProducts())
                .allMatch(Product::isPromotedProduct);
    }

    @DisplayName("isProductFound()가 제품이 존재할 때 true, 존재하지 않을 때 false를 반환하는지 테스트")
    @ParameterizedTest(name = "재고 상태: {0}, 제품 존재 여부: {1}")
    @CsvSource({
            "true, true",
            "false, false"
    })
    void 제품_존재여부_테스트(boolean inStock, boolean expectedProductFound) {
        OrderStatus orderStatus = new OrderStatus(List.of(), false);
        if (inStock) {
            orderStatus = new OrderStatus(List.of(createProduct("Product A", false)), true);
        }

        assertThat(orderStatus.isProductFound()).isEqualTo(expectedProductFound);
    }

    @DisplayName("getNotAppliedItemCount()가 프로모션이 적용되지 않은 수량을 반환하는지 테스트")
    @ParameterizedTest(name = "적용 가능한 수량: {1}, 총 수량: {2}, 미적용 수량: {3}")
    @CsvSource({
            "true, 2, 3, 1",
            "true, 2, 2, 0",
            "false, 0, 1, 1"
    })
    void 프로모션_미적용_수량_반환_테스트(boolean inStock, int promotionCanAppliedCount, int quantity, int expectedNotApplied) {
        OrderStatus orderStatus = new OrderStatus(List.of(createProduct("Product A", false)), inStock, false,
                promotionCanAppliedCount);

        assertThat(orderStatus.getNotAppliedItemCount(quantity)).isEqualTo(expectedNotApplied);
    }

    @DisplayName("isMultipleStock()이 상품이 2개일 때 true를 반환하는지 테스트")
    @ParameterizedTest(name = "상품 수: {0}, 예상 결과: {1}")
    @CsvSource({
            "1, false",
            "2, true",
    })
    void 여러개_상품_반환_메서드_테스트(int productCount, boolean expectedResult) {
        List<Product> products = List.of(
                createProduct("Product A", false),
                createProduct("Product B", false)
        ).subList(0, productCount);

        OrderStatus orderStatus = new OrderStatus(products, true);

        assertThat(orderStatus.isMultipleStock()).isEqualTo(expectedResult);
    }
}
