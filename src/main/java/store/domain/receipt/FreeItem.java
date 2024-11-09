package store.domain.receipt;

public class FreeItem {
    private final String name;
    private final int quantity;
    private final int totalDiscount;

    public FreeItem(String name, int quantity, int totalDiscount) {
        this.name = name;
        this.quantity = quantity;
        this.totalDiscount = totalDiscount;
    }

    public int getTotalDiscount() {
        return totalDiscount;
    }
}