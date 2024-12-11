import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class Logic {
    private Map<String, Long> cache = new HashMap<>();

    public Logic() {
    }

    public long runLogic(Path filePath) {
        long start = System.currentTimeMillis();
        List<Long> arr = new ArrayList<>();
        Long stones = 0L;

        try(BufferedReader reader = Files.newBufferedReader(filePath, StandardCharsets.UTF_8)) {
            while(reader.ready()) {
                String line = reader.readLine();;
                String[] lineSplit = line.split(" ");
                arr = Arrays.stream(lineSplit).map(Long::valueOf).collect(Collectors.toList());
            }

            for(int i = 0; i < arr.size(); i++) {
                stones += recurse(arr.get(i), 0);
                //System.out.println();
            }


            System.out.println(stones);
        }
        catch(IOException ex) {
            System.out.println(ex);
        }

        System.out.println((System.currentTimeMillis() - start) + "ms");
        return stones;
    }

    public long recurse(Long value, int blink) {
        if(cache.get(value + "#" + blink) != null) return cache.get(value + "#" + blink);
        if(blink == 75) return 1;

        long currIterationStones = 0;

        //System.out.println(value);
        Long tmpValue = value;
        if(value == 0) {
            value = 1L;
            currIterationStones += recurse(value, blink + 1);
        }
        else if(value.toString().length()%2 == 0) {
            String firstHalf = value.toString().substring(0, (int)Math.floor(value.toString().length()/2.0));
            String secondHalf = value.toString().substring((int)Math.floor(value.toString().length()/2.0));

            currIterationStones += recurse(Long.valueOf(firstHalf), blink + 1) + recurse(Long.valueOf(secondHalf), blink +1);
        }
        else {
            value *= 2024L;
            currIterationStones += recurse(value, blink + 1);
        }
        cache.put(tmpValue + "#" + blink, currIterationStones);

        return currIterationStones;
    }
}
