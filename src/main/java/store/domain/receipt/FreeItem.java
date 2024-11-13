package store.domain.receipt;

/**
 * FreeItem 은 Receipt(영수증) 정보에 저장되기 위해 존재하는 객체로, 결론적으로 무료로 제공받은 프로모션 품목의 결과를 저장합니다.
 * 무료로 제공받은 프로모션 품목의 정보를 저장하고, 제공하는 것이 책임입니다.
 *
 * @see Receipt
 */
public class FreeItem {
    private final String name;
    private final int quantity;
    private final int totalDiscount;

    public FreeItem(String name, int quantity, int totalDiscount) {
        this.name = name;
        this.quantity = quantity;
        this.totalDiscount = totalDiscount;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getTotalDiscount() {
        return totalDiscount;
    }
}
