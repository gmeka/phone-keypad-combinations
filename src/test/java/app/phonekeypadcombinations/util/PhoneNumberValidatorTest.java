//package app.keypadtoalphanumeric.util;
//
//import app.keypadtoalphanumeric.model.Result;
//import org.junit.jupiter.api.Test;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//class PhoneNumberValidatorTest {
//
//    @Test
//    void validate_negative_shortNumber() {
//        Result result = PhoneNumberValidator.validate("12");
//
//        assertEquals("12", result.getPhoneNum());
//        assertEquals(1, result.getErrors().size());
//        assertEquals("Phone number length should be 7 or 10", result.getErrors().get(0));
//    }
//
//    @Test
//    void validate_negative_specialChars() {
//        Result result = PhoneNumberValidator.validate("1&`d`2");
//
//        assertEquals("1&`d`2", result.getPhoneNum());
//        assertEquals(2, result.getErrors().size());
//        assertEquals("Phone number must be numeric", result.getErrors().get(0));
//        assertEquals("Phone number length should be 7 or 10", result.getErrors().get(1));
//    }
//
//    @Test
//    void validate() {
//        Result result = PhoneNumberValidator.validate("1234567");
//
//        assertEquals("1234567", result.getPhoneNum());
//        assertEquals(0, result.getErrors().size());
//    }
//
//}