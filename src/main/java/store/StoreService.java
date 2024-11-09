package store;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import store.domain.Stock;
import store.domain.order.OrderItem;
import store.domain.order.OrderItems;
import store.domain.order.OrderStatus;
import store.domain.order.service.OrderService;
import store.domain.receipt.Receipt;
import store.messages.ErrorMessage;
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

    public Receipt proceedPurchase(Stock stock, OrderItems orderItems) {
        Receipt receipt = new Receipt();
        for (OrderItem orderItem : orderItems.getOrderItems()) {
            OrderStatus orderStatus = stock.getOrderStatus(orderItem);
            checkOutOfStock(orderStatus);
            confirmOrder(orderStatus, orderItem);

            orderService.purchase(orderStatus, orderItem, receipt);
        }
        checkApplyMembership(receipt);

        return receipt;
    }

    private void checkApplyMembership(Receipt receipt) {
        if (View.getInstance().promptMembershipDiscount()) {
            receipt.applyMembershipDiscount();
        }
    }

    private void checkOutOfStock(OrderStatus orderStatus) {
        if (!orderStatus.isProductFound()) {
            throw new NoSuchElementException(ErrorMessage.INVALID_PRODUCT_NAME.getMessage());
        }
        if (!orderStatus.isInStock()) {
            throw new IllegalStateException(ErrorMessage.OVER_STOCK.getMessage());
        }
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
                orderStatus.removeNormalProduct();
            }
        }
    }

    private void confirmOrder(OrderStatus orderStatus, OrderItem orderItem) {
        addCanGetFreeItem(orderStatus, orderItem);
        checkApplyPromotionItem(orderStatus, orderItem);
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
