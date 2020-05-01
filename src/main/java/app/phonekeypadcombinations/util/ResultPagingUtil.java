package app.phonekeypadcombinations.util;

import app.phonekeypadcombinations.model.Result;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public final class ResultPagingUtil {

    public static Result PaginateResponse(List<String> combinations, String phoneNum,
                                   int pageNum, int pageSize) {

        Result result = new Result(phoneNum, combinations.size(), pageNum, pageSize);
        int startIndex = pageNum * pageSize;
        int endIndex = Math.min(combinations.size(), startIndex + pageSize);

        log.info("returning paginated result:: page: {} and size: {}", pageNum, pageSize);
        result.setCombinations(combinations.subList(startIndex, endIndex));
        return result;
    }
}
