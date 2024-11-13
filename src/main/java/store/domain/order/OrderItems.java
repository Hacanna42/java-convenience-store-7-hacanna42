package store.domain.order;

import java.util.List;

/**
 * OrderItems 는 OrderItem 의 일급 컬렉션입니다.
 * @see OrderItem
 */
public class OrderItems {
    private final List<OrderItem> orderItems;

    public OrderItems(List<OrderItem> orderItems) {
        this.orderItems = List.copyOf(orderItems);
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }
}
