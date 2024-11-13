package store.domain.product;

import java.util.List;

/**
 * ProductParameter 는 Product 를 초기화하기 위한 컬렉션 필드와, 인덱스와 파라미터를 매치하기 위한 Enum 을 가지고 있습니다.
 * Product 의 생성자로 전달되어 Product 를 올바르게 초기화하는 것이 역할입니다.
 */
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
