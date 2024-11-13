package store.domain.promotion;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

class PromotionTest {

    private Promotion promotion;
    private PromotionParameter promotionParameter;

    @BeforeEach
    void setUp() {
        promotionParameter = new PromotionParameter(List.of(
                "Discount Promotion", // 프로모션 이름
                "2",                  // 필요한 구매 수량
                "1",                  // 무료 제공 아이템 수량
                "2024-01-01",         // 시작 날짜
                "2024-12-31"          // 종료 날짜
        ));
        promotion = new Promotion(promotionParameter);
    }

    @DisplayName("프로모션 이름이 올바르게 반환되는지 테스트")
    @Test
    void 프로모션_이름_반환_테스트() {
        assertThat(promotion.getPromotionName()).isEqualTo("Discount Promotion");
    }

    @DisplayName("필요한 구매 수량이 올바르게 반환되는지 테스트")
    @Test
    void 필요한_구매_수량_반환_테스트() {
        assertThat(promotion.getRequiredBuyCount()).isEqualTo(2);
    }

    @DisplayName("프로모션 기간 내의 날짜에 대해 프로모션 사용 가능 여부를 테스트")
    @Test
    void 프로모션_기간_내_사용가능_테스트() {
        LocalDateTime dateWithinPeriod = LocalDate.of(2024, 6, 1).atStartOfDay();
        Period period = promotionParameter.getPeriod();
        assertThat(period.isBetweenPeriod(dateWithinPeriod)).isTrue();
    }

    @DisplayName("프로모션 기간 외의 날짜에 대해 프로모션 사용 불가 여부를 테스트")
    @Test
    void 프로모션_기간_외_사용불가_테스트() {
        LocalDateTime dateOutsidePeriod = LocalDate.of(2023, 12, 2).atStartOfDay();
        Period period = promotionParameter.getPeriod();
        assertThat(period.isBetweenPeriod(dateOutsidePeriod)).isFalse();
    }

    @DisplayName("최대 적용 가능한 프로모션 수량이 올바르게 계산되는지 테스트")
    @Test
    void 최대_프로모션_적용수량_계산_테스트() {
        int currentStockCount = 7;
        int maxPromotionQuantity = promotion.getMaxAvailablePromotionQuantity(currentStockCount);
        assertThat(maxPromotionQuantity).isEqualTo(6);
    }

    @DisplayName("프로모션 적용 시 할인 가격이 올바르게 계산되는지 테스트")
    @Test
    void 프로모션_할인가격_계산_테스트() {
        int buyQuantity = 4;
        int regularPrice = 1000;
        int discountPrice = promotion.getDiscountPrice(buyQuantity, regularPrice);
        assertThat(discountPrice).isEqualTo(1000);
    }

    @DisplayName("프로모션 할인 수량이 올바르게 계산되는지 테스트")
    @Test
    void 프로모션_할인수량_계산_테스트() {
        int buyQuantity = 4;
        int discountedQuantity = promotion.getDiscountedQuantity(buyQuantity);
        assertThat(discountedQuantity).isEqualTo(1);
    }
}

