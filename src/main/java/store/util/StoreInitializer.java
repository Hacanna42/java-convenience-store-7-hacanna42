package store.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import store.domain.Stock;
import store.domain.promotion.Promotion;
import store.domain.promotion.PromotionParameter;
import store.domain.promotion.Promotions;
import store.domain.product.Product;
import store.domain.product.ProductParameter;

public class StoreInitializer {
    private Promotions promotions;

    public void initializeStore() {
        try {
            promotions = readPromotionFile();
            readProductFile();
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
        List<String> parameters = List.of(line.split(","));
        PromotionParameter promotionParameter = new PromotionParameter(parameters);
        Promotion promotion = new Promotion(promotionParameter);
        return promotion;
    }

    private Stock readProductFile() throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("src/main/resources/products.md"));
        Validator.checkProductFirstLine(scanner.nextLine());
        return readProductLines(scanner);
    }

    private Stock readProductLines(Scanner scanner) {
        List<Product> products = new ArrayList<>();
        while (scanner.hasNext()) {
            String currentLine = scanner.nextLine();
            products.add(initProductThat(currentLine));
        }
    }

    private Product initProductThat(String line) {
        Validator.checkProductInitLine(line);
        List<String> parameters = List.of(line.split(","));
        ProductParameter productParameter = new ProductParameter(parameters);
        Promotion promotion = findPromotionByName(productParameter.getPromotionName());
        return new Product(productParameter, promotion);
    }

    private Promotion findPromotionByName(String promotionName) {
        Optional<Promotion> promotion = promotions.findPromotionByName(promotionName);
        if (promotion.isPresent()) {
            return promotion.get();
        }

        throw new RuntimeException("TODO: exception 처리 기능 추가");
    }
}
