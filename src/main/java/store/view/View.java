package store.view;

import java.util.List;
import java.util.function.Supplier;
import store.domain.product.Product;
import store.domain.receipt.Receipt;
import store.util.Validator;

/**
 * View 는 InputView 와 OutputView 객체를 필드에 가지고 있는 싱글톤 객체입니다.
 * 입출력에 관한 모든 동작을 처리하는 것이 책임입니다.
 */
public class View {
    private static View instance;
    private final InputView inputView;
    private final OutputView outputView;

    private View(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public static View getInstance() {
        if (instance == null) {
            instance = new View(new InputView(), new OutputView());
        }
        return instance;
    }

    public boolean promptContinueShopping() {
        outputView.printContinueShoppingMessage();
        boolean continueShopping = inputView.getYesOrNo();
        outputView.newLine();
        return continueShopping;
    }

    public void printErrorMessage(String errorMessage) {
        outputView.printErrorMessage(errorMessage);
    }

    public void printReceipt(Receipt receipt) {
        if (!receipt.hasPurchased()) {
            outputView.printNoReceiptNotice();
            return;
        }
        outputView.printReceipt(receipt);
    }

    public void printGreetingMessage() {
        outputView.printGreetingMessage();
    }

    public void printStockStatus(List<Product> products) {
        outputView.printStockStatus(products);
    }

    public String promptBuyItems() {
        return retryPrompt(() -> {
            outputView.printBuyRequestMessage();
            String input = inputView.getInput();
            Validator.checkBuyInput(input);
            return input;
        });
    }

    public boolean promptFreePromotion(String itemName, int freeItemCount) {
        outputView.printFreePromotionNotice(itemName, freeItemCount);
        return inputView.getYesOrNo();
    }

    public boolean promptInsufficientPromotion(String itemName, int notAppliedItemCount) {
        outputView.printInsufficientPromotionNotice(itemName, notAppliedItemCount);
        return inputView.getYesOrNo();
    }

    public boolean promptMembershipDiscount() {
        outputView.printMembershipDiscountNotice();
        return inputView.getYesOrNo();
    }

    private String retryPrompt(Supplier<String> supplier) {
        while (true) {
            try {
                return supplier.get();
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
