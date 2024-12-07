import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();

        try(BufferedReader reader = Files.newBufferedReader(Path.of("src/input.txt"), StandardCharsets.UTF_8)) {
            long answer = 0;

            while(reader.ready()) {
                List<Long> numbers = new ArrayList<>();
                String line = reader.readLine();
                String[] lineCalculationAndNumbers = line.split(":");

                Long calculationResult = Long.parseLong(lineCalculationAndNumbers[0]); // the calculation result

                for(String s : lineCalculationAndNumbers[1].trim().split(" ")) { //add it all into the queue
                    numbers.add(Long.parseLong(s));
                }

                //System.out.println(numbers);
                //System.out.println(calculationResult);

                if(isValid(numbers, calculationResult, 1, numbers.get(0) )) answer += calculationResult;
            }

            System.out.println(answer);
        }
        catch(IOException ex) {
            System.out.println(ex);
        }

        System.out.println(System.currentTimeMillis() - start + "ms");
    }

    public static boolean isValid(List<Long> numbers, Long target, int currPos, long acc) {
        if(currPos >= numbers.size()) {
            if(target.longValue() == acc) return true;
            return false;
        }

        boolean summation = isValid(numbers, target, currPos + 1, acc + numbers.get(currPos) );
        boolean product = isValid(numbers, target, currPos + 1, acc * numbers.get(currPos) );

        return product || summation;
    }
}
