package store.view;

import camp.nextstep.edu.missionutils.Console;

class InputView {
    protected String getInput() {
        return Console.readLine();
    }

    protected boolean getYesOrNo() {
        String input = Console.readLine();
        return input.equalsIgnoreCase("y");
    }
}
