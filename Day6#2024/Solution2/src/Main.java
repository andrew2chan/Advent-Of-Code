import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Main {
    public static final ArrayList<ArrayList<String>> initialArrDupe = new ArrayList<>();
    public static final int[] initialStartPos = new int[2];

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        int[][] direction = new int[][]{
                new int[]{ -1, 0 }, // 0 = up
                new int[]{ 0, 1 }, // 1 = right
                new int[]{ 1, 0 }, // 2 = down
                new int[]{ 0, -1 } // 3 = left
        };

        ArrayList<ArrayList<String>> dataArr = new ArrayList<>();
        int[] currPos = new int[2];

        try(BufferedReader reader = Files.newBufferedReader(Path.of("src/input.txt"), StandardCharsets.UTF_8)) {
            int rowCounter = 0;
            while(reader.ready()) {
                String line = reader.readLine();
                String[] lineSplit = line.split("");
                ArrayList<String> tmpList = new ArrayList<>(Arrays.asList(lineSplit));
                dataArr.add((ArrayList<String>)tmpList.clone());
                initialArrDupe.add((ArrayList<String>)tmpList.clone());

                //determine if this line has the start pos
                Set<String> tmpSet = new HashSet<>(tmpList);
                if(tmpSet.contains("^")) { //if it does then save the starting pos
                    currPos[0] = rowCounter;
                    currPos[1] = tmpList.indexOf("^");
                }
                rowCounter++;
            }
            initialStartPos[0] = currPos[0];
            initialStartPos[1] = currPos[1];

            //int distinctMoves = 1; //starts at 1 for the current starting pos
            int possibleBlocks = 0;

            //traverse
            for(int i = 0; i < dataArr.size(); i++) {
                for(int k = 0; k < dataArr.get(i).size(); k++) {
                    resetToInitialVals(currPos, dataArr); //reset to initial board every time we try a new block
                    if(dataArr.get(i).get(k).equals("#") || dataArr.get(i).get(k).equals("^") ) continue; //continue if we are on a block tile already or if starting position
                    int currDirection = 0;
                    dataArr.get(i).set(k, "#"); //try putting a block in every position

                    //keep going until out of bounds
                    while(true) {
                        //if(currPos[0] < 0 || currPos[0] >= dataArr.size() || currPos[1] < 0 || currPos[1] >= dataArr.get(currPos[0]).size()) break; // we have hit a boundary so we are done / this case is specific for the reassignment if "#"

                        if(dataArr.get(currPos[0]).get(currPos[1]).equals(".") || dataArr.get(currPos[0]).get(currPos[1]).equals("^") ) { //first time visiting node
                            dataArr.get(currPos[0]).set(currPos[1], String.valueOf(Integer.parseInt("1"))); //set the current position to visited denoted by X
                        }
                        else { //addition times visiting
                            Integer newValue = Integer.parseInt(dataArr.get(currPos[0]).get(currPos[1])) + 1;
                            if(newValue >= 4) { //loop detected so add a possible block and break
                                possibleBlocks++;
                                break;
                            }
                            dataArr.get(currPos[0]).set(currPos[1], String.valueOf(newValue));
                        }

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
                        else if(nextChar.equals(("."))) { //set to next pos but also punches as distinct
                            currPos[0] = nextX;
                            currPos[1] = nextY;
                            //distinctMoves++;
                        }
                        else { //just skip to next pos for anything else
                            currPos[0] = nextX;
                            currPos[1] = nextY;
                        }

                        //System.out.println();
                    }

                    //System.out.println();
                }
           }



            //System.out.println(distinctMoves);
            System.out.println(possibleBlocks);
        }
        catch(IOException ex) {
            System.out.println(ex);
        }

        System.out.println(System.currentTimeMillis() - start + "ms");
    }

    public static void resetToInitialVals(int[] currPos, ArrayList<ArrayList<String>> currArr) {
        for(int i = 0; i < initialArrDupe.size(); i++) {
            currArr.set(i, (ArrayList<String>)initialArrDupe.get(i).clone());
        }

        currPos[0] = initialStartPos[0];
        currPos[1] = initialStartPos[1];
    }
}
