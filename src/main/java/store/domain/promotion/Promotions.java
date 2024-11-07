package store.domain.promotion;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class Promotions {
    private List<Promotion> promotions;

    public Promotions(List<Promotion> promotions) {
        this.promotions = promotions;
    }

    public Optional<Promotion> findPromotionByName(String promotionName) {
        return promotions.stream()
                .filter(promotion -> Objects.equals(promotion.getPromotionName(), promotionName))
                .findFirst();
    }
}
