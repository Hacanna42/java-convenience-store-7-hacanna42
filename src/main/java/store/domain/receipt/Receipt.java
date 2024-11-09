package store.domain.receipt;

import java.util.ArrayList;
import java.util.Collections;
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

    public List<BuyItem> getBuyItems() {
        return Collections.unmodifiableList(buyItems.getBuyItems());
    }

    public List<FreeItem> getFreeItems() {
        return Collections.unmodifiableList(freeItems.getFreeItems());
    }

    public boolean hasPurchased() {
        return totalPrice != 0;
    }

    public boolean hasFreeItem() {
        return totalPromotionDiscount > 0;
    }

    public void addBuyItem(BuyItem buyItem) {
        buyItems.add(buyItem);
        totalPrice += buyItem.getTotalPrice();
    }

    public void addFreeItem(FreeItem freeItem) {
        if (freeItem.getQuantity() > 0) {
            freeItems.add(freeItem);
            totalPromotionDiscount += freeItem.getTotalDiscount();
        }
    }

    public void addPriceOfPromotionItem(int price) {
        totalPriceOfPromotionItem += price;
    }

    public void applyMembershipDiscount() {
        int totalPriceOfNormalItems = totalPrice - totalPriceOfPromotionItem;
        membershipDiscount = (int) (totalPriceOfNormalItems * 0.3);
    }
}
