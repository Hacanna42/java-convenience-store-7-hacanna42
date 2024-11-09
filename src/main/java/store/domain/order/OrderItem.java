package store.domain.order;

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
        quantity += amount;
    }

    public void subQuantity(int amount) {
        quantity -= amount;
    }
}
