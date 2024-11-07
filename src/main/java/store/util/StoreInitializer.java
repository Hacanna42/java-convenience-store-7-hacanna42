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

    public Stock initStock() {
        try {
            promotions = readPromotionFile();
            return readProductFile();
        } catch (FileNotFoundException e) {
            throw new RuntimeException("TODO: 프로그램을 종료해야 하는 Exception 처리 추가");
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
        return new Promotion(promotionParameter);
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

        return new Stock(products);
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
        return promotion.orElse(null);
    }
}
