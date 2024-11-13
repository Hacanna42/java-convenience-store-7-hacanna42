package store.view;

import camp.nextstep.edu.missionutils.Console;

class InputView {
    private static final String BOOLEAN_PROMPT_TRUE_VALUE = "y";

    protected String getInput() {
        return Console.readLine();
    }

    protected boolean getYesOrNo() {
        String input = Console.readLine();
        return input.equalsIgnoreCase(BOOLEAN_PROMPT_TRUE_VALUE);
    }
}
