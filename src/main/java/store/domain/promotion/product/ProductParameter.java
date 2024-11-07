package store.domain.promotion.product;

import java.util.List;

public class ProductParameter {

    public enum ProductSequence {
        NAME(0),
        PRICE(1),
        QUANTITY(2),
        PROMOTION_NAME(3);

        private final int sequence;

        ProductSequence(int sequence) {
            this.sequence = sequence;
        }

        public int getSequence() {
            return sequence;
        }
    }

    private final List<String> productParameters;

    public ProductParameter(List<String> productParameters) {
        this.productParameters = productParameters;
    }

    public String getName() {
        return productParameters.get(ProductSequence.NAME.getSequence());
    }

    public int getPrice() {
        String price = productParameters.get(ProductSequence.PRICE.getSequence());
        return Integer.parseInt(price);
    }

    public int getQuantity() {
        String quantity = productParameters.get(ProductSequence.QUANTITY.getSequence());
        return Integer.parseInt(quantity);
    }

    public String getPromotionName() {
        return productParameters.get(ProductSequence.PROMOTION_NAME.getSequence());
    }
}
