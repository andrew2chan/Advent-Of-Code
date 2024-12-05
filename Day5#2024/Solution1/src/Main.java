import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        Map<Integer, Set<Integer>> map = new HashMap<>(); // mapping is { after value: values that must come before }

        try(BufferedReader reader = Files.newBufferedReader(Path.of("src/test.txt"), StandardCharsets.UTF_8)) {
            while(reader.ready()) { //process the mapping for correct order here
                String line = reader.readLine();
                if(line.isEmpty()) break;
                String[] lineSplit = line.split("\\|");
                Integer beforeValue = Integer.parseInt(lineSplit[0]);
                Integer afterValue = Integer.parseInt(lineSplit[1]);
                if(map.get(afterValue) == null) {
                    map.put(afterValue, new HashSet<>(List.of(beforeValue)));
                }
                else {
                    Set<Integer> item = map.get(afterValue);
                    item.add(beforeValue);
                    map.put(afterValue, item);
                }
            }

            System.out.println(map.toString());

            int sum = 0;
            while(reader.ready()) { //read the actual lists
                String line = reader.readLine();
                String[] lineSplit = line.split(",");
                boolean valid = true;
                Set<Integer> visited = new HashSet<>();
                visited.add(Integer.parseInt(lineSplit[0])); //add the first value as visited

                for(int i = 1; i < lineSplit.length; i++) { //first value will have no dependencies so assume it is valid
                    Integer val = Integer.parseInt(lineSplit[i]);
                    System.out.println(val);

                    //map.get(val) == null in the case that we get a value that has no dependencies which is not possible since the second value MUST at least depend on the first value
                    //since our mapping is [after: everything that must come before], if the mapping of everything that must come before doesn't contain everything we have visited so far then it is not valid
                    if(map.get(val) == null || !map.get(val).containsAll(visited)) {
                        valid = false;
                        break;
                    }

                    visited.add(val);
                }
                System.out.println();

                if(valid) sum += Integer.parseInt(lineSplit[(int)Math.floor(lineSplit.length/2)]);
            }

            System.out.println(sum);
        }
        catch(IOException ex) {
            System.out.println(ex);
        }
    }
}
