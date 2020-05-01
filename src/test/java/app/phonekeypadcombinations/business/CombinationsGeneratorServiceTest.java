package app.phonekeypadcombinations.business;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

class CombinationsGeneratorServiceTest {

    CombinationsGeneratorService service;
    List<String> expected, result;
    String phoneNumber;

    @BeforeEach
    public void before() {
        service = new CombinationsGeneratorService();
    }
    @Test
    void getCombinations_valid_1digit() {
        phoneNumber = "2";
        expected = List.of("2", "A", "B", "C");

        result = service.getCombinations(phoneNumber);

        assertNotNull(expected);
        assertEquals(expected.size(), result.size());
        assertThat(expected, contains("2", "A", "B", "C"));
        assertThat(result, is(expected));
    }

    @Test
    void getCombinations_valid_2digit() {
        phoneNumber = "27";
        expected = List.of("27", "2P", "2Q", "2R", "2S", "A7", "AP", "AQ", "AR", "AS", "B7", "BP", "BQ", "BR", "BS", "C7", "CP", "CQ", "CR", "CS");

        result = service.getCombinations(phoneNumber);

        assertNotNull(expected);
        assertEquals(expected.size(), result.size());
        assertThat(result, is(expected));
    }

    @Test()
    void getCombinations_negative_alphanumeric() {
        phoneNumber = "2A";
        assertThrows(NumberFormatException.class, () -> service.getCombinations(phoneNumber));
    }

    @Test()
    void getCombinations_negative_specialChars() {
        phoneNumber = "2`!45(";
        assertThrows(NumberFormatException.class, () -> service.getCombinations(phoneNumber));
    }

    @Test()
    void getCount_negative_alphanumeric() {
        phoneNumber = "2A";
        assertThrows(NumberFormatException.class, () -> service.countCombinations(phoneNumber));
    }

    @Test()
    void getCount_negative_specialChars() {
        phoneNumber = "2`!45(";
        assertThrows(NumberFormatException.class, () -> service.countCombinations(phoneNumber));
    }

    @Test
    void countCombinations_1digit() {
        phoneNumber = "7";
        int expected = 5;

        int result = service.countCombinations(phoneNumber);

        assertEquals(expected, result);
    }

    @Test
    void countCombinations_multiDigit() {
        assertEquals(1, service.countCombinations("0"));
        assertEquals(1, service.countCombinations("10"));
        assertEquals(16, service.countCombinations("23"));
        assertEquals(102400, service.countCombinations("0123456789"));
    }
}