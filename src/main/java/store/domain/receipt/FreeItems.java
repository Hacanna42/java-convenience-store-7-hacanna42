package store.domain.receipt;

import java.util.List;

public class FreeItems {
    private final List<FreeItem> freeItems;

    public FreeItems(List<FreeItem> freeItems) {
        this.freeItems = freeItems;
    }

    public void add(FreeItem freeItem) {
        freeItems.add(freeItem);
    }

    public List<FreeItem> getFreeItems() {
        return freeItems;
    }
}
