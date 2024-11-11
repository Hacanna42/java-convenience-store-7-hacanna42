package store.domain.receipt;

/**
 * BuyItem 은 Receipt(영수증) 정보에 저장되기 위해 존재하는 객체로, 결론적으로 구매한 품목의 결과를 저장합니다.
 * 구매한 품목의 정보를 저장하고, 제공하는 것이 책임입니다.
 *
 * @see Receipt
 */
public class BuyItem {
    private final String name;
    private final int quantity;
    private final int totalPrice;

    public BuyItem(String name, int quantity, int totalPrice) {
        this.name = name;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }
}
