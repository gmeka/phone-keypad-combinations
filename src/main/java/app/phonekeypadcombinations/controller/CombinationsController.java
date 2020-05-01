package app.phonekeypadcombinations.controller;

import app.phonekeypadcombinations.business.CombinationsGeneratorService;
import app.phonekeypadcombinations.model.Result;
import app.phonekeypadcombinations.util.PhoneNumberValidator;
import app.phonekeypadcombinations.util.ResultPagingUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.List;

@Slf4j
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/phoneNum")
public class CombinationsController {

    @Autowired
    private CombinationsGeneratorService combinationGeneratorService;

    @GetMapping(path = "/{phoneNum}/combinations")
    public ResponseEntity<Result> getCombinations(
            @PathVariable("phoneNum") String phoneNumber,
            @RequestParam(name = "page", defaultValue = "0") final int page,
            @RequestParam(name = "startIndex", defaultValue = "0") final int startIndex,
            @RequestParam(name = "size", defaultValue = "10") final int size) throws Exception {

        log.info("Checking for alphanumeric combinations for phone# {}. requested page {} with size {}", phoneNumber, page, size);
        Result result = PhoneNumberValidator.validate(phoneNumber);

        if(result.getErrors().size()>0){
            return ResponseEntity.badRequest().body(result);
        }

        long start = System.currentTimeMillis();
        List<String> combinations = combinationGeneratorService.getCombinations(phoneNumber);
        log.info("Time Elapsed: {} millis", System.currentTimeMillis() - start);
        log.info("output Size: {}", combinations.size());

        result = ResultPagingUtil.PaginateResponse(combinations, phoneNumber, page, size);

        return ResponseEntity.ok(result);
    }

    @GetMapping(path = "/{phoneNum}/combinations/count")
    public ResponseEntity<Result> getCount(@PathVariable("phoneNum") String phoneNumber) throws Exception{

        log.info("Checking for alphanumeric combination count for phone# {}.", phoneNumber);
        Result result = PhoneNumberValidator.validate(phoneNumber);
        if(result.getErrors().size()>0){
            return ResponseEntity.badRequest().body(result);
        }
        long start = System.currentTimeMillis();
        int count = combinationGeneratorService.countCombinations(phoneNumber);
        log.info("Time Elapsed: {} millis", System.currentTimeMillis() - start);
        log.info("Combinations count: {}", count);
        result.setResultCount(count);
        return ResponseEntity.ok(result);
    }

    @ExceptionHandler({ NumberFormatException.class})
    public ResponseEntity<Result> handleNumberFormatException(Exception ex, WebRequest request) {
        Result result = new Result();
        result.setErrors(List.of(ex.getMessage()));
        return ResponseEntity.badRequest().body(result);
    }

    @ExceptionHandler({ Exception.class})
    public ResponseEntity<Result> handleException(Exception ex, WebRequest request) {
        Result result = new Result();
        result.setErrors(List.of(ex.getMessage()));
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
    }
}
