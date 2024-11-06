package store.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class StoreInitializer {
    public void initializeStore() {

    }

    public void readPromotionFile() throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("src/main/resources/products.md"));
        while (scanner.hasNext()) {
            String currentLine = scanner.nextLine();
            System.out.println(currentLine);
        }
    }

    // TODO: 파일 입출력 및 가공 (StringParser와 협력)
}
