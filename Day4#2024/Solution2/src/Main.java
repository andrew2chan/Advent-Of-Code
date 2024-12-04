import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Main {
    public static final String TEXT_TO_LOCATE = "MAS";
    public static final int[][] directions = new int[][] {
            new int[]{ -1, -1 }, //top left
            new int[]{ -1, 1 }, //top right
            new int[]{ 1, 1 }, //bottom right
            new int[]{ 1, -1 }, //bottom left
    };

    public static void main(String[] args) {
        String[][] arr = new String[140][140];

        try(BufferedReader reader = Files.newBufferedReader(Path.of("src/input.txt"), StandardCharsets.UTF_8)) {
            for(int i = 0; i < arr.length; i++) { // make the text file into a 2d array
                String line = reader.readLine();
                String[] lineSplit = line.split("");
                for(int k = 0; k < lineSplit.length; k++) {
                    arr[i][k] = lineSplit[k];
                }
            }


            int counter = 0;
            List<String> listOfTextToLocate = Arrays.asList(TEXT_TO_LOCATE.split(""));
            Set<String> containsSet = new HashSet<>(listOfTextToLocate);
            for(int i = 0; i < arr.length; i++) {
                for(int k = 0; k < arr[i].length; k++) {
                    if(arr[i][k].equals("A")) { // only come in if we find an X
                        boolean border = false;

                        for(int j = 0; j < directions.length; j++) {
                            int nextX = i + directions[j][0];
                            int nextY = k + directions[j][1];

                            if(nextX < 0 || nextX >= arr.length || nextY < 0 || nextY >= arr[i].length) {
                                border = true;
                                break;
                            }
                        }

                        if(border) continue; //if we are on the borders then just go to next since we can never have x-mas

                        Map<String, Integer> mapTopLeftToBottomRight = new HashMap<>();
                        Map<String, Integer> mapTopRightToBottomLeft = new HashMap<>();

                        /*
                            idea here is make sure that we have mas from top left to bottom right and mas from top right to bottom left so we use a hashmap to keep counts and as long as we have 1 of each (uniques are dealt with
                            by the hashmap) then we are good
                         */
                        mapTopLeftToBottomRight.put("A", 1);
                        mapTopRightToBottomLeft.put("A", 1);

                        mapTopLeftToBottomRight.compute(arr[i-1][k-1], (key,v) -> (v == null) ? 1 : mapTopLeftToBottomRight.get(key) + 1); //top left
                        mapTopLeftToBottomRight.compute(arr[i+1][k+1], (key,v) -> (v == null) ? 1 : mapTopLeftToBottomRight.get(key) + 1); //bottom right

                        mapTopRightToBottomLeft.compute(arr[i-1][k+1], (key,v) -> (v == null) ? 1 : mapTopRightToBottomLeft.get(key) + 1); //top right
                        mapTopRightToBottomLeft.compute(arr[i+1][k-1], (key,v) -> (v == null) ? 1 : mapTopRightToBottomLeft.get(key) + 1); //bottom left

                        if(mapTopLeftToBottomRight.keySet().containsAll(containsSet) && mapTopRightToBottomLeft.keySet().containsAll(containsSet)) counter++;
                    }
                }
            }

            System.out.println(counter);
        }
        catch(IOException ex) {
            System.out.println(ex);
        }
    }
}
