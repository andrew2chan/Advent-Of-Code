import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class Logic {
    private Map<String, Long> cache = new HashMap<>();
    private final int[][] directions = new int[][]{
            new int[]{ -1, 0 },
            new int[]{ 0, 1 },
            new int[]{ 1, 0 },
            new int[]{ 0, -1 }
    };

    public Logic() {
    }

    public long runLogic(Path filePath) {
        long start = System.currentTimeMillis();
        long answer = 0;

        List<List<String>> arr = new ArrayList<>();

        try(BufferedReader reader = Files.newBufferedReader(filePath, StandardCharsets.UTF_8)) {
            while(reader.ready()) {
                String line = reader.readLine();;
                String[] lineSplit = line.split("");
                List<String> tmpArray = new ArrayList<>(List.of(lineSplit));
                arr.add(tmpArray);
            }

            Set<String> visited = new HashSet<>();
            for(int i = 0; i < arr.size(); i++) {
                for(int k = 0; k < arr.get(i).size(); k++) {
                    String key = i + "#" + k;
                    if(visited.contains(key)) continue;
                    Set<String> tmpVisited = new HashSet<>();
                    answer += recurse(arr, tmpVisited, i, k, arr.get(i).get(k)) * tmpVisited.size(); // finds the perimeter
                    visited.addAll(tmpVisited);
                    //System.out.println(arr.get(i).get(k) + " " + visited.size());
                }
            }
            System.out.println(answer);
        }
        catch(IOException ex) {
            System.out.println(ex);
        }

        System.out.println((System.currentTimeMillis() - start) + "ms");
        return answer;
    }

    public long recurse(List<List<String>> arr, Set<String> visited, int x, int y, String letterToSearchFor) {
        String key = x + "#" + y;

        if(x < 0 || x >= arr.size() || y < 0 || y >= arr.get(x).size()) return 1; // we went past borders so just return 1 to make a border;
        if(!arr.get(x).get(y).equals(letterToSearchFor)) return 1; //the next letter isn't the same so we add a border
        if(visited.contains(key)) return 0; //we visited this node so don't add border just return

        visited.add(x + "#" + y); //add the node as visited

        long perimeter = 0;
        for(int[] direction : directions) {
            int nextX = x + direction[0];
            int nextY = y + direction[1];

            perimeter += recurse(arr, visited, nextX, nextY, letterToSearchFor);
        }

        return perimeter;
    }
}
