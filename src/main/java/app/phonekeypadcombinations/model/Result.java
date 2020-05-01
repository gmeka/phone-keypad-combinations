package app.phonekeypadcombinations.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
public class Result {
    @NonNull
    private String phoneNum;
    @NonNull
    private int resultCount;
    @NonNull
    private int pageNum;
    @NonNull
    private int pageSize;
    private List<String> combinations = new ArrayList<>();
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<String> errors;

}