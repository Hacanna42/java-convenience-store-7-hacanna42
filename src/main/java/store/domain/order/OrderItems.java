package store.domain.order;

import java.util.Collections;
import java.util.List;

/**
 * OrderItems 는 OrderItem 의 일급 컬렉션입니다.
 */
public class OrderItems {
    private final List<OrderItem> orderItems;

    public OrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public List<OrderItem> getOrderItems() {
        return Collections.unmodifiableList(orderItems);
    }
}
