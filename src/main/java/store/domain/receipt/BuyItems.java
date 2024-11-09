package store.domain.receipt;

import java.util.List;

public class BuyItems {
    private final List<BuyItem> buyItems;

    public BuyItems(List<BuyItem> buyItems) {
        this.buyItems = buyItems;
    }

    public void add(BuyItem buyItem) {
        buyItems.add(buyItem);
    }
}
