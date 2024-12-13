import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

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
                    Set<String> visitedCorners = new HashSet<>();
                    answer += recurse(arr, tmpVisited, i, k, arr.get(i).get(k), visitedCorners) * tmpVisited.size(); // finds the perimeter
                    //System.out.println("CURRENT ANSWER: " + answer);
                    visited.addAll(tmpVisited);
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

    public long recurse(List<List<String>> arr, Set<String> visited, int x, int y, String letterToSearchFor, Set<String> visitedCorners) {
        String key = x + "#" + y;

        if(x < 0 || x >= arr.size() || y < 0 || y >= arr.get(x).size()) return 0; // we went past borders so just return 1 to make a border;
        if(!arr.get(x).get(y).equals(letterToSearchFor)) return 0; //the next letter isn't the same so we add a border
        if(visited.contains(key)) return 0; //we visited this node so don't add border just return

        visited.add(key); //add the node as visited

        long sides = 0;
        for(int[] direction : directions) {
            int nextX = x + direction[0];
            int nextY = y + direction[1];

            sides += recurse(arr, visited, nextX, nextY, letterToSearchFor, visitedCorners);
        }

        int[][][] corner = new int[][][]{
                new int[][]{ // top left
                        new int[]{-1, 0},
                        new int[]{0, -1},
                        new int[]{-1, -1}
                },
                new int[][]{ //top right
                        new int[]{ -1, 0 },
                        new int[]{ 0, 1},
                        new int[]{ -1, 1}
                },
                new int[][]{ //bottom right
                        new int[]{ 1, 0 },
                        new int[]{ 0, 1},
                        new int[]{ 1, 1}
                },
                new int[][]{ //bottom left
                        new int[]{ 1, 0 },
                        new int[]{ 0, -1},
                        new int[]{ 1, -1}
                },
        };

        int cornerCounter = 0;
        for(int i = 0; i < corner.length; i++) {
            //System.out.println(x + " " + y + " " + letterToSearchFor);
            int cornerConcavity = 0;
            for(int k = 0; k < corner[i].length; k++) {
                int nextX = x + corner[i][k][0];
                int nextY = y + corner[i][k][1];

                if(k < 2) {
                    if(nextX < 0 || nextX >= arr.size() || nextY < 0 || nextY >= arr.get(x).size()) {
                        cornerConcavity--;
                        continue;
                    }

                    if(!arr.get(nextX).get(nextY).equals(letterToSearchFor)) {
                        cornerConcavity--;
                        continue;
                    }

                    cornerConcavity++;
                }
                else {
                    if(cornerConcavity != 2 && cornerConcavity != -2) break;

                    if(cornerConcavity == 2) {
                        if(nextX < 0 || nextX >= arr.size() || nextY < 0 || nextY >= arr.get(x).size()) {
                            cornerCounter++;
                            break;
                        }

                        if(!arr.get(nextX).get(nextY).equals(letterToSearchFor)) {
                            cornerCounter++;
                            break;
                        }

                        break;
                    }

                    cornerCounter++;
                }

            }
        }

        //System.out.println(cornerCounter + " " + letterToSearchFor);
        return sides + cornerCounter;
    }

}
