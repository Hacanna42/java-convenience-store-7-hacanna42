package store.service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import store.domain.Stock;
import store.domain.order.OrderItem;
import store.domain.order.OrderItems;
import store.domain.order.OrderStatus;
import store.domain.order.service.OrderService;
import store.domain.receipt.BuyItems;
import store.domain.receipt.Receipt;
import store.view.View;

public class StoreService {
    private static final String ORDER_ITEM_PATTERN_REGEX = "\\[(.+)-(\\d+)]";

    private final OrderService orderService;

    public StoreService(OrderService orderService) {
        this.orderService = orderService;
    }

    public OrderItems getOrderItems(String input) {
        return makeOrderItems(getSeparatedInput(input));
    }

    public void proceedPurchase(Stock stock, OrderItems orderItems) {
        Receipt receipt = new Receipt();
        for (OrderItem orderItem : orderItems.getOrderItems()) {
            OrderStatus orderStatus = stock.getOrderStatus(orderItem);
            if (!checkOrder(orderStatus, orderItem)) {
                continue; // 해당 항목 결제 취소
            }

            orderService.purchase(orderStatus, orderItem, receipt);
        }
    }

    private boolean checkContinueWhenOutOfStock(OrderStatus orderStatus, OrderItem orderItem) {
        if (!orderStatus.isInStock()) {
            return View.getInstance().promptOutOfStock(orderItem.getItemName());
        }

        // 재고가 있으면 Continue
        return true;
    }

    private boolean checkCancelPurchase(OrderStatus orderStatus, OrderItem orderItem) {
        return !checkContinueWhenOutOfStock(orderStatus, orderItem);
    }

    private void addCanGetFreeItem(OrderStatus orderStatus, OrderItem orderItem) {
        if (orderStatus.isCanGetFreeItem()) {
            boolean addFreeItem = View.getInstance().promptFreePromotion(orderItem.getItemName(), 1);
            if (addFreeItem) {
                orderItem.addQuantity(1);
            }
        }
    }

    private void checkApplyPromotionItem(OrderStatus orderStatus, OrderItem orderItem) {
        if (orderStatus.isMultipleStock() && orderStatus.hasPromotionProduct()) {
            int notAppliedItemCount = orderStatus.getNotAppliedItemCount(orderItem.getQuantity());
            boolean proceedWithNotPromotionItems = View.getInstance()
                    .promptInsufficientPromotion(orderItem.getItemName(), notAppliedItemCount);
            if (!proceedWithNotPromotionItems) { // 정가로 결제해야하는 수량만큼 제외한 후 결제를 진행한다면
                orderItem.subQuantity(notAppliedItemCount);
            }
        }
    }

    private boolean checkOrder(OrderStatus orderStatus, OrderItem orderItem) {
        if (checkCancelPurchase(orderStatus, orderItem)) {
            return false;
        }

        addCanGetFreeItem(orderStatus, orderItem);
        checkApplyPromotionItem(orderStatus, orderItem);
        return true;
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
