package store.domain.product;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.domain.promotion.Promotion;
import store.domain.promotion.PromotionParameter;
import java.util.List;

class ProductTest {

    private Product productWithPromotion;
    private Product productWithoutPromotion;
    private Promotion promotion;

    @BeforeEach
    void setUp() {
        // PromotionParameter 객체 생성
        PromotionParameter promotionParameter = new PromotionParameter(List.of(
                "Discount Promotion",  // 프로모션 이름
                "2",                   // 필요한 구매 수량
                "1",                   // 무료 제공 아이템 수량
                "2024-01-01",          // 시작 날짜
                "2024-12-31"           // 종료 날짜
        ));

        // Promotion 객체 생성
        promotion = new Promotion(promotionParameter);
        // ProductParameter 객체 생성
        ProductParameter productParameter = new ProductParameter(List.of("TestProduct", "1000", "10"));

        productWithPromotion = new Product(productParameter, promotion);
        productWithoutPromotion = new Product(productParameter, null);
    }

    @DisplayName("상품 이름이 올바르게 반환되는지 테스트")
    @Test
    void 상품_이름_반환_테스트() {
        assertThat(productWithPromotion.getName()).isEqualTo("TestProduct");
    }

    @DisplayName("상품 가격이 올바르게 반환되는지 테스트")
    @Test
    void 상품_가격_반환_테스트() {
        assertThat(productWithPromotion.getPrice()).isEqualTo(1000);
    }

    @DisplayName("프로모션 이름이 없는 경우 빈 문자열을 반환하는지 테스트")
    @Test
    void 프로모션_없는_경우_이름_빈문자열_테스트() {
        assertThat(productWithoutPromotion.getPromotionName()).isEmpty();
    }

    @DisplayName("프로모션 이름이 올바르게 반환되는지 테스트")
    @Test
    void 프로모션_이름_반환_테스트() {
        assertThat(productWithPromotion.getPromotionName()).isEqualTo("Discount Promotion");
    }

    @DisplayName("상품의 수량이 올바르게 반환되는지 테스트")
    @Test
    void 상품_수량_반환_테스트() {
        assertThat(productWithPromotion.getQuantity()).isEqualTo(10);
    }

    @DisplayName("상품 판매 시 수량이 감소하는지 테스트")
    @Test
    void 상품_판매_수량_감소_테스트() {
        productWithPromotion.sell(3);
        assertThat(productWithPromotion.getQuantity()).isEqualTo(7);
    }

    @DisplayName("일반 구매 시 총 구매 가격이 올바르게 계산되는지 테스트")
    @Test
    void 일반_구매_총구매가격_계산_테스트() {
        int amount = 3;
        assertThat(productWithPromotion.getRegularPurchasePrice(amount)).isEqualTo(3000);
    }

    @DisplayName("프로모션 할인 가격이 없는 경우 0을 반환하는지 테스트")
    @Test
    void 프로모션_할인_없는경우_할인가격_테스트() {
        int amount = 3;
        assertThat(productWithoutPromotion.getPromotionDiscountPrice(amount)).isEqualTo(0);
    }

    @DisplayName("프로모션 할인 가격이 올바르게 계산되는지 테스트")
    @Test
    void 프로모션_할인가격_계산_테스트() {
        int amount = 3;
        int discountPrice = promotion.getDiscountPrice(amount, productWithPromotion.getPrice());
        assertThat(productWithPromotion.getPromotionDiscountPrice(amount)).isEqualTo(discountPrice);
    }

    @DisplayName("프로모션 할인 수량이 없는 경우 0을 반환하는지 테스트")
    @Test
    void 프로모션_할인수량_없는경우_테스트() {
        int amount = 3;
        assertThat(productWithoutPromotion.getPromotionDiscountAmount(amount)).isEqualTo(0);
    }

    @DisplayName("프로모션 할인 수량이 올바르게 계산되는지 테스트")
    @Test
    void 프로모션_할인수량_계산_테스트() {
        int amount = 3;
        int discountAmount = promotion.getDiscountedQuantity(amount);
        assertThat(productWithPromotion.getPromotionDiscountAmount(amount)).isEqualTo(discountAmount);
    }

    @DisplayName("최대 적용 가능한 프로모션 상품 개수가 올바르게 반환되는지 테스트")
    @Test
    void 최대_프로모션_적용개수_반환_테스트() {
        int maxQuantity = promotion.getMaxAvailablePromotionQuantity(productWithPromotion.getQuantity());
        assertThat(productWithPromotion.getMaxAvailablePromotionQuantity()).isEqualTo(maxQuantity);
    }

    @DisplayName("무료로 받을 수 있는 상품이 있는지 확인하는 함수가 올바르게 작동하는지 테스트")
    @Test
    void 무료상품_여부_확인_테스트() {
        int buyQuantity = 2;
        assertThat(productWithPromotion.isCanGetFreeProduct(buyQuantity)).isTrue();
    }

    @DisplayName("재고가 구매 수량 이상일 때 true 반환하는지 테스트")
    @Test
    void 재고_구매가능여부_테스트() {
        int toBuyQuantity = 5;
        assertThat(productWithPromotion.isStockAvailable(toBuyQuantity)).isTrue();
    }

    @DisplayName("재고가 부족할 때 false 반환하는지 테스트")
    @Test
    void 재고_부족시_구매불가_테스트() {
        int toBuyQuantity = 11;
        assertThat(productWithPromotion.isStockAvailable(toBuyQuantity)).isFalse();
    }

    @DisplayName("프로모션 적용 가능한 상품인지 확인하는 함수가 올바르게 작동하는지 테스트")
    @Test
    void 프로모션_적용가능여부_테스트() {
        assertThat(productWithPromotion.isPromotedProduct()).isTrue();
    }
}
