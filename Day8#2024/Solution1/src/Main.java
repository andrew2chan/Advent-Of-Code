import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();

        try(BufferedReader reader = Files.newBufferedReader(Path.of("src/input.txt"), StandardCharsets.UTF_8)) {
            Map<String, List<Integer[]>> map = new HashMap<>(); //upper case letter/lower case letter/number: its position in the

            int rowCounter = 0; //will keep track of which row we have and also how many rows we have
            int maxCols = 0; //will keep track of the max number of cols we have
            while(reader.ready()) {
                String line = reader.readLine();
                String[] lineSplit = line.split("");
                maxCols = lineSplit.length;
                for(int i = 0; i < lineSplit.length; i++) {
                    if(lineSplit[i].equals(".")) continue;
                    final int tmpFinalRowCounter = rowCounter; //make these final so I can use them in the lambda
                    final int tmpI = i;
                    map.computeIfAbsent(lineSplit[i], (k) -> new ArrayList<>());

                    map.get(lineSplit[i]).add(new Integer[] { tmpFinalRowCounter, tmpI });
                }
                rowCounter++;
            }

            System.out.println(rowCounter);
            System.out.println(maxCols);

            int distinctAntiNodes = 0;
            Set<List<Integer>> visitedSet = new HashSet<>();
            for(String key : map.keySet()) { //go through each key
                List<Integer[]> currList = map.get(key);
                for(int i = 0; i < currList.size(); i++) { //go through each position pair
                    for(int k = 0; k < currList.size(); k++) { //comparing it with every other key pair
                        if(i == k) continue;
                        int diffX = currList.get(i)[0] - currList.get(k)[0];
                        int diffY = currList.get(i)[1] - currList.get(k)[1];

                        int antiNodeX = currList.get(i)[0] + diffX;
                        int antiNodeY = currList.get(i)[1] + diffY;
                        if(antiNodeX < 0 || antiNodeX >= rowCounter || antiNodeY < 0 || antiNodeY >= maxCols) continue;
                        if(visitedSet.contains(List.of(antiNodeX, antiNodeY))) continue;
                        //System.out.println(antiNodeX + " " + antiNodeY);
                        visitedSet.add(List.of(antiNodeX, antiNodeY)); //using list because it checks equals based on values rather than reference
                        distinctAntiNodes++;
                    }
                }
            }

            System.out.println(distinctAntiNodes);
        }
        catch(IOException ex) {
            System.out.println(ex);
        }

        System.out.println(System.currentTimeMillis() - start + "ms");
    }
}
