import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class Logic {

    public Logic() {
    }

    public int runLogic(Path filePath) {

        List<Long> arr = new ArrayList<>();

        try(BufferedReader reader = Files.newBufferedReader(filePath, StandardCharsets.UTF_8)) {
            while(reader.ready()) {
                String line = reader.readLine();;
                String[] lineSplit = line.split(" ");
                arr = Arrays.stream(lineSplit).map(Long::valueOf).collect(Collectors.toList());
            }

            for(int k = 0; k < 25; k++) {
                for (int i = 0; i < arr.size(); i++) {
                    if (arr.get(i) == 0L) {
                        arr.set(i, 1L);
                    } else if (arr.get(i).toString().length() % 2 == 0) {
                        String stringifiedVal = arr.get(i).toString();
                        String firstHalf = stringifiedVal.substring(0, (int) Math.floor(stringifiedVal.length() / 2.0));
                        String secondHalf = stringifiedVal.substring((int) Math.floor(stringifiedVal.length() / 2.0));
                        arr.set(i, Long.valueOf(firstHalf));
                        arr.add(i + 1, Long.valueOf(secondHalf));
                        i++;
                    } else {
                        arr.set(i, arr.get(i) * 2024L);
                    }
                }
            }

            System.out.println(arr.size());
        }
        catch(IOException ex) {
            System.out.println(ex);
        }

        return arr.size();
    }
}
