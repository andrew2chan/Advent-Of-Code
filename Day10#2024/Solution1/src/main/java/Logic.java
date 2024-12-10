import java.io.BufferedReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Logic {
    private final int[][] directions = new int[][] {
            new int[]{ -1, 0 },
            new int[]{ 0, 1 },
            new int[]{ 1, 0 },
            new int[]{ 0, -1 }
    };
    private Set<List<String>> completedCoordSet = new HashSet<>();
    private Set<List<String>> visitedNodeSet = new HashSet<>();

    public Logic() {
    }

    public int runLogic(Path filePath) {
        int answer = 0;

        List<List<String>> arr = new ArrayList<>();
        int[] initialPos = new int[2];

        try(BufferedReader reader = Files.newBufferedReader(filePath, StandardCharsets.UTF_8)) {
            while(reader.ready()) {
                String line = reader.readLine();;
                //System.out.println(line);
                String[] lineSplit = line.split("");
                List<String> tmpArr = new ArrayList<>(Arrays.asList(lineSplit));
                arr.add(tmpArr);
            }

            //System.out.println(arr);

        }
        catch(IOException ex) {
            System.out.println(ex);
        }

        for(int i = 0; i < arr.size(); i++) {
            for(int k = 0; k < arr.get(i).size(); k++) {
                if(!arr.get(i).get(k).equals("0")) continue;
                completedCoordSet = new HashSet<>();
                visitedNodeSet = new HashSet<>();
                answer += traverse(arr, new int[]{i, k}, 0);
            }
        }
        //answer = traverse(arr, initialPos, 0);

        return answer;

    }

    private int traverse(List<List<String>> arr, int[] pos, int numToSearchFor) {
        List<String> visitingNode = new ArrayList<>(List.of(String.valueOf(pos[0]), String.valueOf(pos[1])));
        if(visitedNodeSet.contains(visitingNode)) return 0;
        if(pos[0] < 0 || pos[0] >= arr.size() || pos[1] < 0 || pos[1] >= arr.get(pos[0]).size()) return 0;
        if(!arr.get(pos[0]).get(pos[1]).equals(String.valueOf(numToSearchFor))) return 0; //continue if the next num isn't in seq
        //System.out.println("Val: " + arr.get(pos[0]).get(pos[1]) + " X: " + pos[0] + " Y: " + pos[1]);

        if(String.valueOf(numToSearchFor).equals("9")) { //found our end
            if(completedCoordSet.contains(visitingNode)) return 0; //already have this node

            visitedNodeSet.add(visitingNode);
            //arr.get(pos[0]).set(pos[1], "#");
            completedCoordSet.add(visitingNode);
            return 1;
        }

        //arr.get(pos[0]).set(pos[1], "#");
        visitedNodeSet.add(visitingNode);

        /*System.out.println("Next num we are looking for " + (numToSearchFor + 1));
        for(List<String> arr1 : arr) {
            System.out.println(arr1);
        }
        System.out.println();*/

        int counter = 0;
        for(int[] nextCoordsPair : directions) {
            int nextX = pos[0] + nextCoordsPair[0];
            int nextY = pos[1] + nextCoordsPair[1];
            if(nextX < 0 || nextX >= arr.size() || nextY < 0 || nextY >= arr.get(pos[0]).size()) continue; //next is out of bounds so continue with other dir

            counter += traverse(arr, new int[]{nextX, nextY}, numToSearchFor + 1);
        }
        return counter;
    }
}
