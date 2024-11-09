package store.domain.receipt;

import java.util.ArrayList;

public class Receipt {
    private final BuyItems buyItems;
    private final FreeItems freeItems;
    private int totalPrice;
    private int totalPriceOfPromotionItem;
    private int promotionDiscount;

    public Receipt() {
        this.buyItems = new BuyItems(new ArrayList<>());
        this.freeItems = new FreeItems(new ArrayList<>());
    }

    public void addBuyItem(BuyItem buyItem) {
        buyItems.add(buyItem);
        totalPrice += buyItem.getTotalPrice();
    }

    public void addFreeItem(FreeItem freeItem) {
        freeItems.add(freeItem);
        promotionDiscount += freeItem.getTotalDiscount();
    }

    public void addPriceOfPromotionItem(int price) {
        totalPriceOfPromotionItem += price;
    }
}
