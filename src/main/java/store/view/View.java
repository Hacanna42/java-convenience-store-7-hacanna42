package store.view;

public class View {
    private static View instance;
    private InputView inputView;
    private OutputView outputView;

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

    // TODO: 다양한 입출력 함수 추가
}
