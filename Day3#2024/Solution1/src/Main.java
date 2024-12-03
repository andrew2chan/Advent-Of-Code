import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        List<Integer[]> items = new ArrayList<>();

        try(BufferedReader reader = Files.newBufferedReader(Path.of("src/input.txt"), StandardCharsets.UTF_8)) {
            while(reader.ready()) {
                String line = reader.readLine();

                Pattern pattern = Pattern.compile("mul\\((\\d+),(\\d+)\\)");
                Matcher matcher = pattern.matcher(line);

                while(matcher.find()) {
                    //System.out.println(matcher.group(0) + " " + matcher.group(1) + " " + matcher.group(2));
                    items.add(new Integer[]{ Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)) });
                }
            }

            int sum = 0;
            for(Integer[] i : items) {
                //System.out.println(Arrays.toString(i));
                sum += i[0].intValue() * i[1].intValue();
            }
            System.out.println(sum);
        }
        catch(IOException ex) {
            System.out.println(ex);
        }
    }
}
