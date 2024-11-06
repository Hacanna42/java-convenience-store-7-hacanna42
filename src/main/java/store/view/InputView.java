package store.view;

import camp.nextstep.edu.missionutils.Console;
import java.util.function.Supplier;

public class InputView {
    protected String getInput() {
        return Console.readLine();
    }
}
