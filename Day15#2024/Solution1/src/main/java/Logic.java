import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Logic {
    private Map<String, Integer> cache = new HashMap<>();
    private final int[][] directions = new int[][]{
            new int[]{ -1, 0 }, //top
            new int[]{ 0, 1 }, //right
            new int[]{ 1, 0 }, //down
            new int[]{ 0, -1 } //left
    };

    public Logic() {
    }

    public long runLogic(Path filePath) {
        long start = System.currentTimeMillis();
        long answer = 0;

        Set<List<Integer>> blocks = new HashSet<>();
        List<Integer> charPos = new ArrayList<>();
        List<List<Integer>> boxes = new ArrayList<>();

        List<List<Long>> arr = new ArrayList<>();

        try(BufferedReader reader = Files.newBufferedReader(filePath, StandardCharsets.UTF_8)) {
            int rowCounter = 0;
            while(reader.ready()) { //this will read and consume the blank line, so next reader.readline will start from inputs
                String line = reader.readLine();
                if(line.isEmpty()) break;
                String[] lineSplit = line.split("");
                for(int i = 0; i < lineSplit.length; i++) {
                    String c = lineSplit[i];
                    if(c.equals("#")) blocks.add(List.of( rowCounter, i ));
                    if(c.equals("O")) boxes.add(new ArrayList<>(List.of( rowCounter, i )));
                    if(c.equals("@")) charPos = new ArrayList<>(List.of( rowCounter, i ));
                }
                rowCounter++;
            }

            System.out.println(blocks);
            System.out.println(charPos);
            System.out.println(boxes);

            while(reader.ready()) { //read the inputs
                String line = reader.readLine();
                String[] lineSplit = line.split("");
                for(int i = 0; i < lineSplit.length; i++) {
                    String c = lineSplit[i]; // do something for each input
                    //System.out.println(c);
                    int nextX = charPos.get(0);
                    int nextY = charPos.get(1);
                    if(c.equals("^")) {
                        nextX += directions[0][0];
                        nextY += directions[0][1];
                    }
                    else if(c.equals(">")) {
                        nextX += directions[1][0];
                        nextY += directions[1][1];
                    }
                    else if(c.equals("v")) {
                        nextX += directions[2][0];
                        nextY += directions[2][1];
                    }
                    else if(c.equals("<")) {
                        nextX += directions[3][0];
                        nextY += directions[3][1];
                    }

                    if(blocks.contains(List.of( nextX, nextY ))) continue; // the next position is a block so just continue
                    else if(boxes.contains(List.of( nextX, nextY ))) { //if next pos of char is a box
                        for(int k = 0; k < boxes.size(); k++) {
                            int nextBoxX = boxes.get(k).get(0);
                            int nextBoxY = boxes.get(k).get(1);
                            if(nextBoxX != nextX || nextBoxY != nextY) continue; //continues look for next box if current box doesn't match next pos
                            boolean isValid = false;
                            if(c.equals("^")) {
                                isValid = pushBlocks(blocks, boxes, nextX, nextY, directions[0][0], directions[0][1]);
                            }
                            else if(c.equals(">")) {
                                isValid = pushBlocks(blocks, boxes, nextX, nextY, directions[1][0], directions[1][1]);
                            }
                            else if(c.equals("v")) {
                                isValid = pushBlocks(blocks, boxes, nextX, nextY, directions[2][0], directions[2][1]);
                            }
                            else if(c.equals("<")) {
                                isValid = pushBlocks(blocks, boxes, nextX, nextY, directions[3][0], directions[3][1]);
                            }

                            if(isValid) {
                                charPos.set(0, nextX);
                                charPos.set(1, nextY);
                            }
                        }

                    }
                    else { //blank space so we just move charPos
                        charPos.set(0, nextX);
                        charPos.set(1, nextY);
                    }

                    /*System.out.println();
                    System.out.println(c + " " + nextX + " " + nextY);
                    System.out.println(charPos);
                    System.out.println(boxes);*/
                }
            }
        }
        catch(IOException ex) {
            System.out.println(ex);
        }

        System.out.println();
        System.out.println(charPos);
        System.out.println(boxes);

        for(List<Integer> box: boxes) {
            answer += 100 * box.get(0) + box.get(1);
        }

        System.out.println(answer);
        //System.out.println(cache);
        System.out.println((System.currentTimeMillis() - start) + "ms");
        return answer;
    }

    private boolean pushBlocks(Set<List<Integer>> blocks, List<List<Integer>> boxes, int currX, int currY, int directionX, int directionY) {
        if(blocks.contains(List.of( currX, currY ))) return false;
        if(!boxes.contains(List.of(currX, currY))) return true;

        boolean isValid = pushBlocks(blocks, boxes, currX + directionX, currY + directionY, directionX, directionY);

        if(isValid) {
            Integer ind = boxes.indexOf(List.of( currX, currY ));
            boxes.set(ind, List.of( currX + directionX, currY + directionY ));
        }

        return isValid;
    }
}
