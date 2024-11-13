package store.domain.receipt;

import java.util.List;

/**
 * FreeItems 는 FreeItem 의 일급 컬렉션입니다.
 * @see FreeItem
 */
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
