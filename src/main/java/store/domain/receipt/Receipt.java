package store.domain.receipt;

import java.util.ArrayList;
import java.util.List;

public class Receipt {
    private final BuyItems buyItems;
    private final FreeItems freeItems;
    private int totalPrice;
    private int totalPriceOfPromotionItem;
    private int totalPromotionDiscount;
    private int membershipDiscount;

    public Receipt() {
        this.buyItems = new BuyItems(new ArrayList<>());
        this.freeItems = new FreeItems(new ArrayList<>());
    }

    public boolean hasPurchased() {
        return totalPrice != 0;
    }

    public int getTotalQuantity() {
        return buyItems.getTotalQuantity();
    }

    public int getTotalPrice() {
        return buyItems.getTotalPrice();
    }

    public int getTotalPromotionDiscount() {
        return totalPromotionDiscount;
    }

    public int getMembershipDiscount() {
        return membershipDiscount;
    }

    public int getFinalPrice() {
        return (totalPrice - totalPromotionDiscount) - membershipDiscount;
    }

    public void addBuyItem(BuyItem buyItem) {
        buyItems.add(buyItem);
        totalPrice += buyItem.getTotalPrice();
    }

    public void addFreeItem(FreeItem freeItem) {
        freeItems.add(freeItem);
        totalPromotionDiscount += freeItem.getTotalDiscount();
    }

    public void addPriceOfPromotionItem(int price) {
        totalPriceOfPromotionItem += price;
    }

    public void applyMembershipDiscount() {
        int totalPriceOfNormalItems = totalPrice - totalPriceOfPromotionItem;
        membershipDiscount = (int) (totalPriceOfNormalItems * 0.3);
    }

    public List<BuyItem> getBuyItems() {
        return buyItems.getBuyItems();
    }

    public List<FreeItem> getFreeItems() {
        return freeItems.getFreeItems();
    }
}
