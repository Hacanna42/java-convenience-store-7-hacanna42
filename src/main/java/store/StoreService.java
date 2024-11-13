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
    private static final int FREE_PROMOTION_ITEM_COUNT = 1;

    private final OrderService orderService;

    public StoreService(OrderService orderService) {
        this.orderService = orderService;
    }

    public OrderItems getOrderItems(String input) {
        return makeOrderItems(getSeparatedInput(input));
    }

    public Receipt proceedPurchase(Stock stock, OrderItems orderItems) {
        Receipt receipt = new Receipt();
        orderItems.getOrderItems().forEach(orderItem -> proceedOrder(stock, orderItem, receipt));
        checkApplyMembership(receipt);

        return receipt;
    }

    private void proceedOrder(Stock stock, OrderItem orderItem, Receipt receipt) {
        OrderStatus orderStatus = stock.getOrderStatus(orderItem);
        checkOutOfStock(orderStatus);
        confirmOrder(orderStatus, orderItem);
        orderService.order(orderStatus, orderItem, receipt);
    }

    private void checkOutOfStock(OrderStatus orderStatus) {
        if (!orderStatus.isProductFound()) {
            throw new NoSuchElementException(ErrorMessage.INVALID_PRODUCT_NAME.getMessage());
        }
        if (!orderStatus.isInStock()) {
            throw new IllegalStateException(ErrorMessage.OVER_STOCK.getMessage());
        }
    }

    private void confirmOrder(OrderStatus orderStatus, OrderItem orderItem) {
        addCanGetFreeItem(orderStatus, orderItem);
        checkNotAppliedPromotionItem(orderStatus, orderItem);
    }

    private void addCanGetFreeItem(OrderStatus orderStatus, OrderItem orderItem) {
        if (orderStatus.isCanGetFreeItem()) {
            boolean addFreeItem = View.getInstance()
                    .promptFreePromotion(orderItem.getItemName(), FREE_PROMOTION_ITEM_COUNT);
            if (addFreeItem) {
                orderItem.addQuantity(FREE_PROMOTION_ITEM_COUNT);
            }
        }
    }

    private void checkNotAppliedPromotionItem(OrderStatus orderStatus, OrderItem orderItem) {
        if (!(orderStatus.isMultipleStock() && orderStatus.hasPromotionProduct())) {
            return;
        }

        int notAppliedItemCount = orderStatus.getNotAppliedItemCount(orderItem.getQuantity());
        // 정가로 결제해야 하는 수량을 제외하는 옵션을 선택했다면, 비프로모션 상품을 판매 대상에서 삭제하고 주문 개수를 감소시킴.
        if (!askProceedWithNotPromotionItems(orderItem, notAppliedItemCount)) {
            orderItem.subQuantity(notAppliedItemCount);
            orderStatus.removeNormalProduct();
        }
    }

    private boolean askProceedWithNotPromotionItems(OrderItem orderItem, int notAppliedItemCount) {
        return View.getInstance()
                .promptInsufficientPromotion(orderItem.getItemName(), notAppliedItemCount);
    }

    private void checkApplyMembership(Receipt receipt) {
        if (View.getInstance().promptMembershipDiscount()) {
            receipt.applyMembershipDiscount();
        }
    }

    private OrderItems makeOrderItems(List<String> separatedInputs) {
        List<OrderItem> orderItems = new ArrayList<>();
        Pattern pattern = Pattern.compile(ORDER_ITEM_PATTERN_REGEX);

        for (String input : separatedInputs) {
            Matcher matcher = pattern.matcher(input);
            if (matcher.matches()) {
                orderItems.add(makeOrderItem(matcher));
            }
        }
        return new OrderItems(orderItems);
    }

    private OrderItem makeOrderItem(Matcher matcher) {
        String name = matcher.group(1);
        int quantity = Integer.parseInt(matcher.group(2));
        return new OrderItem(name, quantity);
    }

    private List<String> getSeparatedInput(String input) {
        return List.of(input.split(","));
    }
}
