package store.service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import store.domain.Stock;
import store.domain.order.OrderItem;
import store.domain.order.OrderItems;
import store.domain.order.OrderStatus;
import store.view.View;

public class StoreService {
    private static final String ORDER_ITEM_PATTERN_REGEX = "\\[(.+)-(\\d+)]";

    public OrderItems getOrderItems(String input) {
        return makeOrderItems(getSeparatedInput(input));
    }

    public void proceedPurchase(Stock stock, OrderItems orderItems) {
        for (OrderItem orderItem : orderItems.getOrderItems()) {
            OrderStatus orderStatus = stock.getOrderStatus(orderItem);

            // 리팩토링 예정: 객체를 객체답게.

            if (!orderStatus.isInStock()) {
                // 재고에 없는 상품 안내
                System.out.println("재고에 없는 상품 안내 추가 예정");
            }

            if (orderStatus.isCanGetFreeItem()) {
                // 무료 상품 안내
                View.getInstance().promptFreePromotion(orderItem.getItemName(), 1);
            }

            if (orderStatus.isMultipleStock()) {
                if (orderStatus.getNotAppliedItemCount(orderItem.getQuantity()) > 0) {
                    View.getInstance().promptInsufficientPromotion(orderItem.getItemName(), orderStatus.getNotAppliedItemCount(orderItem.getQuantity()));
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
