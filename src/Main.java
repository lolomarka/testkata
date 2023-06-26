import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try{
            while (true){
                String line = reader.readLine();
                String result = calc(line);
                System.out.println(result);
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    private static Double romeConvert(String in){
        return switch (in.toLowerCase()) {
            case "i" -> 1.0;
            case "ii" -> 2.0;
            case "iii" -> 3.0;
            case "iv" -> 4.0;
            case "v" -> 5.0;
            case "vi" -> 6.0;
            case "vii" -> 7.0;
            case "viii" -> 8.0;
            case "ix" -> 9.0;
            case "x" -> 10.0;
            default -> throw new NumberFormatException();
        };
    }

    public static String calc(String input) {
        var args = input.split(" ");
        if(args.length != 3)
            throw new RuntimeException();
        String operator = args[1];
        String arg1 = args[0];
        String arg2 = args[2];
        Double d1;
        Double d2;


        boolean isNumeric = true;
        try{
            d1 = Double.parseDouble(arg1);
            d2 = Double.parseDouble(arg2);
        }catch (NumberFormatException ex){
            isNumeric = false;
            d1 = romeConvert(arg1);
            d2 = romeConvert(arg2);
        }
        if (d1 < 0 || d1 > 10 || d2 < 0 || d2 > 10)
            throw new RuntimeException();
        Double result = switch (operator) {
            case "+" -> d1 + d2;
            case "-" -> d1 - d2;
            case "/" -> d1 / d2;
            case "*" -> d1 * d2;
            default -> throw new RuntimeException();
        };

        if(isNumeric)
            return String.valueOf(result.intValue());
        else {
            return arabicToRoman(result.intValue());
        }
    }

    public static String arabicToRoman(int number) {
        if ((number <= 0) || (number > 4000)) {
            throw new IllegalArgumentException(number + " is not in range (0,4000]");
        }

        List<RomanNumeral> romanNumerals = RomanNumeral.getReverseSortedValues();

        int i = 0;
        StringBuilder sb = new StringBuilder();

        while ((number > 0) && (i < romanNumerals.size())) {
            RomanNumeral currentSymbol = romanNumerals.get(i);
            if (currentSymbol.getValue() <= number) {
                sb.append(currentSymbol.name());
                number -= currentSymbol.getValue();
            } else {
                i++;
            }
        }

        return sb.toString();
    }

    enum RomanNumeral {
        I(1), IV(4), V(5), IX(9), X(10),
        XL(40), L(50), XC(90), C(100),
        CD(400), D(500), CM(900), M(1000);

        private final int value;

        RomanNumeral(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static List<RomanNumeral> getReverseSortedValues() {
            return Arrays.stream(values())
                    .sorted(Comparator.comparing((RomanNumeral e) -> e.value).reversed())
                    .collect(Collectors.toList());
        }
    }
}