package store.domain.receipt;

public class Receipt {
    private BuyItems buyItems;
    private FreeItems freeItems;
    private int totalPrice;
    private int promotionDiscount;
    private int membershipDiscount;

    public void addBuyItem(BuyItem buyItem) {
        buyItems.add(buyItem);
    }

    public void addFreeItem(FreeItem freeItem) {
        freeItems.add(freeItem);
    }
}
