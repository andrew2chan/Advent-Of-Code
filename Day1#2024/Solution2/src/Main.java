import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        List<Integer> list1 = new ArrayList<>();
        Map<Integer, Integer> similarityMap = new HashMap<>();

        //read from file and save into list 1 into an array list but a similarity map into a hashmap
        try(BufferedReader reader = Files.newBufferedReader(Path.of("./src/data.txt"), StandardCharsets.UTF_8)) {
            while(reader.ready()) {
                String line = reader.readLine();
                String[] lineSplit = line.split("\\s+");
                list1.add(Integer.parseInt(lineSplit[0])); // get the first value

                similarityMap.compute(Integer.parseInt(lineSplit[1]), (k, v) -> (v == null) ? 1 : similarityMap.get(k) + 1); //make the second value into a hashmap with the number of frequency
            }

            //calculate total
            int totalSize = 0;
            for(int i = 0; i < list1.size(); i++) {
                totalSize += list1.get(i) * (similarityMap.get(list1.get(i)) == null ? 0 : similarityMap.get(list1.get(i))); //total size = item from first value * the value of similarityMap.get(first item) or default 0 if we can't find it
            }
            System.out.println(totalSize);
        }
        catch(IOException ex) {
            System.out.println(ex);
        }
    }
}
