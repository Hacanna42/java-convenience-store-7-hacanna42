package store.domain.promotion;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Promotions 는 Promotion 의 일급 컬렉션입니다.
 * @see Promotion
 */
public class Promotions {
    private final List<Promotion> promotions;

    public Promotions(List<Promotion> promotions) {
        this.promotions = List.copyOf(promotions);
    }

    public Optional<Promotion> findPromotionByName(String promotionName) {
        return promotions.stream()
                .filter(promotion -> Objects.equals(promotion.getPromotionName(), promotionName))
                .findFirst();
    }
}
