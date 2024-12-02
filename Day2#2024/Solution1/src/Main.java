import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {

        try(BufferedReader reader = Files.newBufferedReader(Path.of("src/input.txt"), StandardCharsets.UTF_8)) {
            int totalValid = 0;

            while(reader.ready()) {
                String[] lineSplit = reader.readLine().split("\\s");

                int prevState = 0; //0 = unknown / 1 = increasing / -1 = decreasing
                int prev = convertToInt(lineSplit[0]);

                int validNums = 1; // 1 since a single number will always be valid
                for(int i = 1; i < lineSplit.length; i++) {
                    int current = convertToInt(lineSplit[i]);

                    if(Math.abs(prev - current) < 1 || Math.abs(prev - current) > 3) break;
                    if(prev > current) { //decreasing
                        if(prevState == 0) { //set initial to decreasing. Should only run on first iteration
                            prevState = -1;
                            validNums++;
                            prev = current;
                        }
                        else if(prevState == -1) {
                            validNums++;
                            prev = current;
                        }
                        else if(prevState == 1) { //prevstate was increasing and now it's decreasing
                            break;
                        }
                    }
                    else if(prev < current) { //increasing
                        if(prevState == 0) {
                            prevState = 1;
                            validNums++;
                            prev = current;
                        }
                        else if(prevState == 1) {
                            validNums++;
                            prev = current;
                        }
                        else if(prevState == -1) { //prev state was decreasing but now it's increasing
                            break;
                        }
                    }
                    else { //prev and current are equal
                        break;
                    }
                }

                System.out.println(validNums + " " + (validNums == lineSplit.length));
                if(validNums == lineSplit.length) totalValid++;
            }

            System.out.println(totalValid);
        }
        catch(IOException ex) {
            System.out.println(ex);
        }
    }

    public static int convertToInt(String num) {
        return Integer.parseInt(num);
    }
}
