package app.phonekeypadcombinations.business;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CombinationsGeneratorService {
    private static final Map<String, List<String>> KEYPAD_MAP = new HashMap<String, List<String>>() {{
        put("1", List.of("1"));
        put("2", List.of("2", "A", "B", "C"));
        put("3", List.of("3", "D", "E", "F"));
        put("4", List.of("4", "G", "H", "I"));
        put("5", List.of("5", "J", "K", "L"));
        put("6", List.of("6", "M", "N", "O"));
        put("7", List.of("7", "P", "Q", "R", "S"));
        put("8", List.of("8", "T", "U", "V"));
        put("9", List.of("9", "W", "X", "Y", "Z"));
        put("0", List.of("0"));
    }};

    private List<String> getCombinationsRecursive(String phoneNum, List<String> result) {
        if(phoneNum.length() == 0) {
            return result;
        }
        List<String> partialResult = new ArrayList<>();
        String firstDigit = String.valueOf(phoneNum.charAt(0));

        result.forEach((i) -> KEYPAD_MAP.get(firstDigit).forEach((j) -> {
            partialResult.add(i + j);
        }));
        return getCombinationsRecursive(phoneNum.substring(1), partialResult);
    }

    public List<String> getCombinations(String phoneNumber) {
        validate(phoneNumber);
        List<String> result = KEYPAD_MAP.get(String.valueOf(phoneNumber.charAt(0)));
        return getCombinationsRecursive(phoneNumber.substring(1), result);
    }

    private void validate(String phoneNumber) {
        Long.valueOf(phoneNumber).longValue();
    }

    public int countCombinations(String phoneNumber) {
        validate(phoneNumber);
        return Arrays.stream(phoneNumber.split(""))
                .mapToInt((s) -> KEYPAD_MAP.get(s).size())
                .reduce(Math::multiplyExact)
                .getAsInt();

    }

    public static void main(String[] args) {
        CombinationsGeneratorService myClass = new CombinationsGeneratorService();

        System.out.println(myClass.getCombinations("27"));
        System.out.println(myClass.countCombinations("1234567890"));

        List<String> list = new ArrayList<>(5120);
        System.out.println(list.size());

        System.out.println(Long.valueOf("4842384139").longValue());
    }
}