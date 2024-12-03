import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        List<Integer[]> items = new ArrayList<>();
        int flag = 1; //1 = do, 0 = don't

        try(BufferedReader reader = Files.newBufferedReader(Path.of("src/input.txt"), StandardCharsets.UTF_8)) {
            while(reader.ready()) {
                String line = reader.readLine();

                Pattern pattern = Pattern.compile("(mul\\((\\d+),(\\d+)\\))|don\\'t\\(\\)|do\\(\\)");
                Matcher matcher = pattern.matcher(line);

                while(matcher.find()) {
                    if(matcher.group(0).equals("don't()")) flag = 0;
                    if(matcher.group(0).equals("do()")) flag = 1;

                    if(flag == 1 && !matcher.group(0).equals("do()")) {
                        //System.out.println(matcher.group(0) + " " + matcher.group(1) + " " + matcher.group(2) + " " + matcher.group(3));
                        items.add(new Integer[]{ Integer.parseInt(matcher.group(2)), Integer.parseInt(matcher.group(3)) });
                    }
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
