package store.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import store.domain.promotion.Promotion;
import store.domain.promotion.PromotionParameter;
import store.domain.promotion.Promotions;

public class StoreInitializer {
    public void initializeStore() {
        try {
            readPromotionFile();
        } catch (FileNotFoundException e) {

        }
    }

    private Promotions readPromotionFile() throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("src/main/resources/promotions.md"));
        Validator.checkPromotionFirstLine(scanner.nextLine());
        return readPromotionLines(scanner);
    }

    private Promotions readPromotionLines(Scanner scanner) {
        List<Promotion> promotions = new ArrayList<>();
        while (scanner.hasNext()) {
            String currentLine = scanner.nextLine();
            promotions.add(initPromotionThat(currentLine));
        }
        return new Promotions(promotions);
    }

    private Promotion initPromotionThat(String line) {
        Validator.checkPromotionInitLine(line);
        List<String> parameters = StringParser.reverseSplit(",", line, 5);
        PromotionParameter promotionParameter = new PromotionParameter(parameters);
        Promotion promotion = new Promotion(promotionParameter);
        return promotion;
    }
}
