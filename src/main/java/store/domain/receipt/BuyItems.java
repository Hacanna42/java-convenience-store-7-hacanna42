package store.domain.receipt;

import java.util.List;

/**
 * BuyItems 는 BuyItem 의 일급 컬렉션입니다.
 * @see BuyItem
 */
public class BuyItems {
    private final List<BuyItem> buyItems;

    public BuyItems(List<BuyItem> buyItems) {
        this.buyItems = buyItems;
    }

    public void add(BuyItem buyItem) {
        buyItems.add(buyItem);
    }

    public List<BuyItem> getBuyItems() {
        return buyItems;
    }

    public int getTotalQuantity() {
        return buyItems.stream().mapToInt(BuyItem::getQuantity).sum();
    }

    public int getTotalPrice() {
        return buyItems.stream().mapToInt(BuyItem::getTotalPrice).sum();
    }
}
