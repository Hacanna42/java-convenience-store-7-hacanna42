package store.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import store.domain.order.OrderItem;
import store.domain.order.OrderItems;

public class StoreService {
    // [콜라-10] 에서 이름과 수량을 캡처하는 패턴 정규식
    private static final String ORDER_ITEM_PATTERN_REGEX = "\\[(.+)-(\\d+)]";

    public OrderItems getOrderItems(String input) {
        return makeOrderItems(getSeparatedInput(input));
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
