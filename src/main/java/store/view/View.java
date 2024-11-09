package store.view;

import java.util.List;
import java.util.function.Supplier;
import store.domain.product.Product;
import store.util.Validator;

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

    public boolean promptOutOfStock(String itemName) {
        outputView.printOutOfStockNotice(itemName);
        return inputView.getYesOrNo();
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

    public boolean promptForAdditionalItem() {
        outputView.printAdditionalItemNotice();
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
