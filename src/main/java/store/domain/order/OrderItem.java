package store.domain.order;

/**
 * OrderItem 은 사용자의 주문 내역(주문한 상품 이름, 수량)을 필드로 가지고 있습니다.
 * 사용자의 주문 내역을 관리하는 것이 책임입니다.
 */
public class OrderItem {
    private final String itemName;
    private int quantity;

    public OrderItem(String itemName, int quantity) {
        this.itemName = itemName;
        this.quantity = quantity;
    }

    public String getItemName() {
        return itemName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void addQuantity(int amount) {
        if (amount > 0) {
            quantity += amount;
        }
    }

    public void subQuantity(int amount) {
        if (amount > 0) {
            quantity -= amount;
        }
    }
}
