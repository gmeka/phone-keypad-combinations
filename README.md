### Phone-keypad-combinations generator

**Input:** phone number (Numeric)

**Output:** list of alpha-numeric combinations for given phone number

**Assumptions:**
  - Number itself is a possible combinations. Ex: service user [2, A, B, C] as valid combinations for keypad entry 2.
  - Any special chars ignore. Ex: '+' ignored for keypad entry of 0
  
  ``` 
Map<String, List<String>> KEYPAD_MAP = new HashMap<>() {{
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
```

**Building application **

1. Checkout code 
2. cd into the location from terminal
3. Run
 ` mvn clean package`
 
 **Running Application**
 
 Run `java -jar target/phone-keypad-combinations.jar`
 
 **Launch application as Docker container**
 1. Build Image - `docker image build -t phone-keypad-combinations .`
 2. Launch image as container - `docker run -d -p 8080:8080 phone-keypad-combinations:latest`