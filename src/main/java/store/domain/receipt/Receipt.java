package store.domain.receipt;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Receipt 는 구매한 품목과, 무료로 제공받은 품목을 포함한 최종 구매 관련 정보들을 필드에 가지고 있습니다.
 * 영수증 정보에 필요한 정보들을 관리하고 제공하는 것이 책임입니다.
 *
 * @see BuyItems
 * @see FreeItems
 */
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
        if (membershipDiscount > 8000) { // 최대 할인 한도는 8,000원
            membershipDiscount = 8000;
        }
    }
}
