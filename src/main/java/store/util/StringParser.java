package store.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StringParser {
    /* reverseSplit() 이 필요한 이유?
     * promotions.md 파일에서 "탄산2+1,2,1,2024-01-01,2024-12-31" 를 읽어올 때,
     * 만약 프로모션 name "탄산2+1"에 구분자 ","가 포함되어 있는 경우 (예: 달콤,짭짤 이벤트)
     * split()이 예상한 대로 동작하지 않기 때문에, 역순으로 limit 만큼의 split()으로 해결
     */
    public static List<String> reverseSplit(String separator, String input, int limit) {
        String reversed = new StringBuilder(input).reverse().toString();
        String[] reversedSplit = reversed.split(separator, limit);

        List<String> resultList = new ArrayList<>();
        for (String part : reversedSplit) {
            resultList.add(new StringBuilder(part).reverse().toString());
        }

        Collections.reverse(resultList);
        return resultList;
    }
}
