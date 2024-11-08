package store.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import store.domain.order.OrderItem;
import store.domain.order.OrderItems;
import store.domain.order.OrderStatus;

public class StoreService {
    // [콜라-10] 에서 이름과 수량을 캡처하는 패턴 정규식
    private static final String ORDER_ITEM_PATTERN_REGEX = "\\[(.+)-(\\d+)]";

    public OrderItems getOrderItems(String input) {
        return makeOrderItems(getSeparatedInput(input));
    }

    public void proceedPurchase(Stock stock, OrderItems orderItems) {
        for (OrderItem orderItem : orderItems.getOrderItems()) {
            OrderStatus orderStatus = stock.getOrderStatus(orderItem);

            if (!orderStatus.isInStock()) {
                // 재고에 없는 상품 안내
                System.out.println("재고 없음");
            }

            if (orderStatus.isCanGetFreeItem()) {
                // 무료 상품 안내
                System.out.println("무료로 하나 받아갈 수 있음.");
            }

            if (orderStatus.isMultipleStock()) {
                if (orderStatus.getNotAppliedItemCount(orderItem.getQuantity()) > 0) {
                    System.out.println("상품 " + orderStatus.getNotAppliedItemCount(orderItem.getQuantity()) + "개는 프로모션을 적용받지 못함.");
                }
            }

        }
    }

    private void purchase(OrderItem orderItem) {

    }

    private OrderItems makeOrderItems(List<String> separatedInputs) {
        List<OrderItem> orderItems = new ArrayList<>();
        Pattern pattern = Pattern.compile(ORDER_ITEM_PATTERN_REGEX);

        for (String input : separatedInputs) {
            Matcher matcher = pattern.matcher(input);
            if (matcher.matches()) {
                String name = matcher.group(1);
                int quantity = Integer.parseInt(matcher.group(2));
                System.out.println("New Order Item: " + name + ", " + quantity);
                OrderItem orderItem = new OrderItem(name, quantity);
                orderItems.add(orderItem);
            }
        }

        return new OrderItems(orderItems);
    }

    private List<String> getSeparatedInput(String input) {
        return List.of(input.split(","));
    }
}
