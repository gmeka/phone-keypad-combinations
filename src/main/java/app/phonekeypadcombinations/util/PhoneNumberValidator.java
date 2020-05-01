package app.phonekeypadcombinations.util;

import app.phonekeypadcombinations.model.Result;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public final class PhoneNumberValidator {
    public static Result validate(String phoneNum) {
        Result result = new Result();
        result.setPhoneNum(phoneNum);
        List<String> errors = new ArrayList<>();
        try{
            Long.valueOf(phoneNum).longValue();
        } catch (Exception ex) {
            log.error("Phone number failed to parse to integer. Exception: ", ex);
            errors.add("Phone number must be numeric");
        }

        if( ! (phoneNum.length() == 7 || phoneNum.length() == 10) ) {
            errors.add("Phone number length should be 7 or 10");
        }

        result.setErrors(errors);

        return result;
    }
}
