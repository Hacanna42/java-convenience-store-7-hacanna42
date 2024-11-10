package store.domain.receipt;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ReceiptTest {

    private Receipt receipt;

    @BeforeEach
    void setUp() {
        receipt = new Receipt();
    }

    @DisplayName("구매 항목 추가 시 총 가격과 총 수량이 올바르게 업데이트되는지 테스트")
    @Test
    void 구매항목_추가_총가격_총수량_업데이트_테스트() {
        BuyItem item1 = new BuyItem("Item A", 2, 2000);
        BuyItem item2 = new BuyItem("Item B", 3, 3000);

        receipt.addBuyItem(item1);
        receipt.addBuyItem(item2);

        assertThat(receipt.getTotalPrice()).isEqualTo(5000);
        assertThat(receipt.getTotalQuantity()).isEqualTo(5);
    }

    @DisplayName("무료 항목 추가 시 총 할인 금액이 올바르게 업데이트되는지 테스트")
    @Test
    void 무료항목_추가_총할인금액_업데이트_테스트() {
        FreeItem freeItem1 = new FreeItem("Free A", 1, 500);
        FreeItem freeItem2 = new FreeItem("Free B", 2, 1000);

        receipt.addFreeItem(freeItem1);
        receipt.addFreeItem(freeItem2);

        assertThat(receipt.getTotalPromotionDiscount()).isEqualTo(1500);
    }

    @DisplayName("멤버십 할인 적용 시 최대 8000원으로 제한되는지 테스트")
    @Test
    void 멤버십_적용_최대할인_테스트() {
        BuyItem normalItem = new BuyItem("Normal Item", 10, 30000); // 비프로모션 상품
        BuyItem promotionItem = new BuyItem("Promo Item", 5, 10000); // 프로모션 상품

        receipt.addBuyItem(normalItem);
        receipt.addBuyItem(promotionItem);
        receipt.addPriceOfPromotionItem(promotionItem.getTotalPrice());

        receipt.applyMembershipDiscount();

        assertThat(receipt.getMembershipDiscount()).isEqualTo(8000);
    }

    @DisplayName("멤버십 할인 적용 시 정가 상품에 대해 30% 할인되는지 테스트")
    @Test
    void 멤버십_적용_정가상품_30퍼센트할인_테스트() {
        BuyItem normalItem = new BuyItem("Normal Item", 5, 10000);

        receipt.addBuyItem(normalItem);
        receipt.applyMembershipDiscount();

        assertThat(receipt.getMembershipDiscount()).isEqualTo(3000); // 10000 * 0.3 = 3000
    }

    @DisplayName("최종 가격이 총 가격에서 프로모션 할인과 멤버십 할인을 제외한 금액으로 계산되는지 테스트")
    @Test
    void 최종가격_계산_테스트() {
        BuyItem item = new BuyItem("Item A", 5, 20000);
        FreeItem freeItem = new FreeItem("Free A", 2, 5000);

        receipt.addBuyItem(item);
        receipt.addFreeItem(freeItem);
        receipt.applyMembershipDiscount();

        int expectedFinalPrice = (20000 - 5000) - receipt.getMembershipDiscount();
        assertThat(receipt.getFinalPrice()).isEqualTo(expectedFinalPrice);
    }

    @DisplayName("구매 항목이 있을 때 hasPurchased()가 true를 반환하는지 테스트")
    @Test
    void 구매항목_존재여부_테스트() {
        assertThat(receipt.hasPurchased()).isFalse();

        BuyItem item = new BuyItem("Item A", 2, 2000);
        receipt.addBuyItem(item);

        assertThat(receipt.hasPurchased()).isTrue();
    }

    @DisplayName("무료 항목이 있을 때 hasFreeItem()이 true를 반환하는지 테스트")
    @Test
    void 무료항목_존재여부_테스트() {
        assertThat(receipt.hasFreeItem()).isFalse();

        FreeItem freeItem = new FreeItem("Free A", 1, 500);
        receipt.addFreeItem(freeItem);

        assertThat(receipt.hasFreeItem()).isTrue();
    }
}
