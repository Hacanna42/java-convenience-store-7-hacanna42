package store.view;

import java.util.List;
import store.domain.product.Product;
import store.domain.receipt.BuyItem;
import store.domain.receipt.FreeItem;
import store.domain.receipt.Receipt;
import store.messages.ReceiptForm;
import store.messages.StoreMessage;

class OutputView {
    protected void printGreetingMessage() {
        System.out.println(StoreMessage.GREETING.getMessage());
    }

    protected void printContinueShoppingMessage() {
        System.out.println(StoreMessage.ASK_CONTINUE_SHOPPING.getMessage());
    }

    protected void printReceipt(Receipt receipt) {
        newLine();
        System.out.println(ReceiptForm.HEADER.getMessage());
        System.out.printf(ReceiptForm.TITLES.getMessage(), ReceiptForm.WORD_ITEM_NAME.getMessage(),
                ReceiptForm.WORD_ITEM_QUANTITY.getMessage(), ReceiptForm.WORD_ITEM_PRICE.getMessage());

        // 구매한 상품 출력
        for (BuyItem item : receipt.getBuyItems()) {
            System.out.printf(ReceiptForm.BUY_ITEMS.getMessage(), item.getName(), item.getQuantity(), item.getTotalPrice());
        }

        System.out.println(ReceiptForm.PROMOTION_DIVIDER.getMessage());
        // 증정 상품 출력
        for (FreeItem item : receipt.getFreeItems()) {
            System.out.printf(ReceiptForm.PROMOTION_ITEMS.getMessage(), item.getName(), item.getQuantity());
        }

        System.out.println(ReceiptForm.FINAL_DIVIDER.getMessage());
        // 총 구매액, 행사할인, 멤버십 할인, 내실 돈 출력
        System.out.printf(ReceiptForm.TOTAL_PRICE.getMessage(), ReceiptForm.WORD_TOTAL_PRICE.getMessage(), receipt.getTotalQuantity(), receipt.getTotalPrice());
        System.out.printf(ReceiptForm.PROMOTION_DISCOUNT.getMessage(), ReceiptForm.WORD_PROMOTION_DISCOUNT.getMessage(), receipt.getTotalPromotionDiscount());
        System.out.printf(ReceiptForm.MEMBERSHIP_DISCOUNT.getMessage(), ReceiptForm.WORD_MEMBERSHIP_DISCOUNT.getMessage(), receipt.getMembershipDiscount());
        System.out.printf(ReceiptForm.FINAL_PRICE.getMessage(), ReceiptForm.WORD_FINAL_PRICE.getMessage(), receipt.getFinalPrice());
        newLine();
    }

    protected void printBuyRequestMessage() {
        newLine();
        System.out.println(StoreMessage.BUY_REQUEST.getMessage());
    }

    protected void printStockStatus(List<Product> products) {
        printStockNoticeMessage();
        newLine();
        for (Product product : products) {
            if (product.getQuantity() != 0) {
                System.out.printf(StoreMessage.STOCK_STATUS.getMessage(), product.getName(), product.getPrice(), product.getQuantity(),
                        product.getPromotionName());
                continue;
            }
            System.out.printf(StoreMessage.STOCK_STATUS_NO_STOCK.getMessage(), product.getName(), product.getPrice(),
                    product.getPromotionName());
        }
    }

    protected void printFreePromotionNotice(String itemName, int freeItemCount) {
        newLine();
        System.out.printf(StoreMessage.ASK_FREE_PROMOTION.getMessage(), itemName, freeItemCount);
    }

    protected void printInsufficientPromotionNotice(String itemName, int notAppliedItemCount) {
        newLine();
        System.out.printf(StoreMessage.ASK_INSUFFICIENT_PROMOTION.getMessage(), itemName, notAppliedItemCount);
    }

    protected void printMembershipDiscountNotice() {
        newLine();
        System.out.println(StoreMessage.ASK_MEMBERSHIP_DISCOUNT.getMessage());
    }

    private void printStockNoticeMessage() {
        System.out.println(StoreMessage.STOCK_NOTICE.getMessage());
    }

    protected void printErrorMessage(String errorMessage) {
        newLine();
        System.out.println(errorMessage);
    }

    protected void newLine() {
        System.out.println();
    }
}
