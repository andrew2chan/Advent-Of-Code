import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        int[][] direction = new int[][]{
                new int[]{ -1, 0 }, // 0 = up
                new int[]{ 0, 1 }, // 1 = right
                new int[]{ 1, 0 }, // 2 = down
                new int[]{ 0, -1 } // 3 = left
        };
        int currDirection = 0;

        List<List<String>> dataArr = new ArrayList<>();
        int[] currPos = new int[2];

        try(BufferedReader reader = Files.newBufferedReader(Path.of("src/input.txt"), StandardCharsets.UTF_8)) {
            int rowCounter = 0;
            while(reader.ready()) {
                String line = reader.readLine();
                String[] lineSplit = line.split("");
                List<String> tmpList = new ArrayList<>(Arrays.asList(lineSplit));
                dataArr.add(tmpList);

                //determine if this line has the start pos
                Set<String> tmpSet = new HashSet<>(tmpList);
                if(tmpSet.contains("^")) { //if it does then save the starting pos
                    currPos[0] = rowCounter;
                    currPos[1] = tmpList.indexOf("^");
                }
                rowCounter++;
            }

            System.out.println(dataArr);
            int distinctMoves = 1; //starts at 1 for the current starting pos

            //keep going until out of bounds
            while(true) {
                if(currPos[0] < 0 || currPos[0] >= dataArr.size() || currPos[1] < 0 || currPos[1] >= dataArr.get(currPos[0]).size()) break; // we have hit a boundary so we are done / this case is specific for the reassignment if "#"

                dataArr.get(currPos[0]).set(currPos[1], "X"); //set the current position to visited denoted by X

                /*for(List<String> l : dataArr) {
                    for(String l2 : l) {
                        System.out.print(l2);
                    }
                    System.out.println();
                }*/

                int nextX = currPos[0] + direction[currDirection][0];
                int nextY = currPos[1] + direction[currDirection][1];
                if(nextX < 0 || nextX >= dataArr.size() || nextY < 0 || nextY >= dataArr.get(currPos[0]).size()) break; // we have hit a boundary so we are done

                String nextChar = dataArr.get(nextX).get(nextY);
                if(nextChar.equals("#")) { //keeps turning by 90 deg until we find a way to go
                    currDirection++;
                    if(currDirection > 3) currDirection = 0; // loop back to the beginning again
                }
                else if(nextChar.equals("X")) { //just skip to next pos
                    currPos[0] = nextX;
                    currPos[1] = nextY;
                }
                else if(nextChar.equals(("."))) { //set to next pos but also punches as distinct
                    currPos[0] = nextX;
                    currPos[1] = nextY;
                    distinctMoves++;
                }

                //System.out.println();
            }

            System.out.println(distinctMoves);
        }
        catch(IOException ex) {
            System.out.println(ex);
        }
    }
}
