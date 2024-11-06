package store.view;

import java.util.function.Supplier;
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

    public String promptBuyItems() {
        return retryPrompt(() -> {
            outputView.printBuyRequestMessage();
            String input = inputView.getInput();
            Validator.checkBuyInput(input);
            return input;
        });
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
