package store.view;

import java.util.List;
import store.domain.product.Product;
import store.domain.receipt.BuyItem;
import store.domain.receipt.FreeItem;
import store.domain.receipt.Receipt;

class OutputView {
    private static final String GREETING_MESSAGE = "안녕하세요. W편의점입니다.";
    public static final String BUY_REQUEST_MESSAGE = "구매하실 상품명과 수량을 입력해 주세요.";
    private static final String STOCK_NOTICE_MESSAGE = "현재 보유하고 있는 상품입니다.";
    private static final String ITEM_OUT_OF_STOCK_NOTICE = "%s은(는) 재고가 부족하여 구매할 수 없습니다. 해당 상품을 제외하고 진행하시겠습니까? (Y/N)";
    private static final String FREE_PROMOTION_NOTICE = "현재 %s은(는) %d개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)";
    private static final String INSUFFICIENT_PROMOTION_NOTICE = "현재 %s %d개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)";
    private static final String MEMBERSHIP_DISCOUNT_NOTICE = "멤버십 할인을 받으시겠습니까? (Y/N)";
    private static final String ADDITIONAL_ITEM_NOTICE = "감사합니다. 구매하고 싶은 다른 상품이 있나요? (Y/N)";

    protected void printGreetingMessage() {
        System.out.println(GREETING_MESSAGE);
    }

    protected void printReceipt(Receipt receipt) {
        System.out.println("==============W 편의점================");
        System.out.printf("%-15s\t%-10s\t%s\n", "상품명", "수량", "금액");

        // 구매한 상품 출력
        for (BuyItem item : receipt.getBuyItems()) {
            System.out.printf("%-15s\t%-10d\t%,d\n", item.getName(), item.getQuantity(), item.getTotalPrice());
        }

        System.out.println("==============증   정================");
        // 증정 상품 출력
        for (FreeItem item : receipt.getFreeItems()) {
            System.out.printf("%-15s\t%d\n", item.getName(), item.getQuantity());
        }

        System.out.println("====================================");
        // 총 구매액, 행사할인, 멤버십 할인, 내실 돈 출력
        System.out.printf("%-15s\t%-10d\t%,d\n", "총구매액", receipt.getTotalQuantity(), receipt.getTotalPrice());
        System.out.printf("%-25s\t\t%,d\n", "행사할인", -receipt.getTotalPromotionDiscount());
        System.out.printf("%-25s\t\t%,d\n", "멤버십할인", -receipt.getMembershipDiscount());
        System.out.printf("%-25s\t\t%,d\n", "내실돈", receipt.getFinalPrice());
    }

    protected void printBuyRequestMessage() {
        newLine();
        System.out.println(BUY_REQUEST_MESSAGE);
    }

    protected void printStockStatus(List<Product> products) {
        printStockNoticeMessage();
        newLine();
        for (Product product : products) {
            System.out.println("- " + product.toString());
        }
    }

    protected void printOutOfStockNotice(String itemName) {
        System.out.printf(ITEM_OUT_OF_STOCK_NOTICE + "%n", itemName);
    }

    protected void printFreePromotionNotice(String itemName, int freeItemCount) {
        System.out.printf(FREE_PROMOTION_NOTICE + "%n", itemName, freeItemCount);
    }

    protected void printInsufficientPromotionNotice(String itemName, int notAppliedItemCount) {
        System.out.printf(INSUFFICIENT_PROMOTION_NOTICE + "%n", itemName, notAppliedItemCount);
    }

    protected void printMembershipDiscountNotice() {
        System.out.println(MEMBERSHIP_DISCOUNT_NOTICE);
    }

    protected void printAdditionalItemNotice() {
        System.out.println(ADDITIONAL_ITEM_NOTICE);
    }

    private void printStockNoticeMessage() {
        System.out.println(STOCK_NOTICE_MESSAGE);
    }

    private void newLine() {
        System.out.println();
    }
}
