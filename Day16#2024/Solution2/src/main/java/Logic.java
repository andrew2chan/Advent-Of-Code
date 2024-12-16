import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Logic {
    private static class CellDetails {
        public int x = 0;
        public int y = 0;
        public int g = 0;
        public int h = 0;
        public int f = 0;
        public int direction = 1;
        public Set<CellDetails> markedTiles = new HashSet<>();

        public CellDetails() {
        }

        public CellDetails(int x, int y, int g, int h, int f, int direction, Set<CellDetails> markedTiles) {
            this.x = x;
            this.y = y;
            this.g = g;
            this.h = h;
            this.f = f;
            this.direction = direction;
            this.markedTiles = markedTiles;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            CellDetails that = (CellDetails) o;
            return x == that.x && y == that.y && g == that.g && h == that.h && f == that.f && direction == that.direction && Objects.equals(markedTiles, that.markedTiles);
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y, g, h, f, direction, markedTiles);
        }

        @Override
        public String toString() {
            return "CellDetails{" +
                    "x=" + x +
                    ", y=" + y +
                    ", g=" + g +
                    ", h=" + h +
                    ", f=" + f +
                    ", direction=" + direction +
                    '}';
        }
    }

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
        long answer = Long.MAX_VALUE;

        List<List<String>> arr = new ArrayList<>();
        Set<CellDetails> visited = new HashSet<>();
        CellDetails startPos = new CellDetails();
        CellDetails endPos = new CellDetails();
        PriorityQueue<CellDetails> q = new PriorityQueue<>(Comparator.comparingInt(cell -> cell.f));
        Set<CellDetails> tiles = new HashSet<>();

        try(BufferedReader reader = Files.newBufferedReader(filePath, StandardCharsets.UTF_8)) {
            int rowCounter = 0;
            while(reader.ready()) { //this will read and consume the blank line, so next reader.readline will start from inputs
                String line = reader.readLine();
                if(line.isEmpty()) break;
                String[] lineSplit = line.split("");
                List<String> tmpArr = new ArrayList<>(List.of(lineSplit));
                arr.add(tmpArr);
                if(tmpArr.contains("S")) {
                    startPos.x = rowCounter;
                    startPos.y = tmpArr.indexOf("S");
                }
                if(tmpArr.contains("E")) {
                    endPos.x = rowCounter;
                    endPos.y = tmpArr.indexOf("E");
                }
                rowCounter++;
            }

            /*for(List<String> a : arr) {
                System.out.println(a);
            }
            System.out.println();*/

            q.add(startPos);
            int maxG = Integer.MAX_VALUE;

            while(!q.isEmpty()) {
                CellDetails currCell = q.poll(); // will get the smallest f every time

                //if(visited.contains(currCell)) continue;

                visited.add(currCell);

                if(arr.get(currCell.x).get(currCell.y).equals("E") && maxG == Integer.MAX_VALUE) {
                    /*for(List<String> a : arr) {
                        System.out.println(a);
                    }
                    System.out.println();*/

                    maxG = currCell.g;

                    /*System.out.println(q.size());
                    System.out.println(currCell.g);
                    return currCell.g;*/
                    //continue;
                }
                if(maxG == currCell.g) {
                    tiles.addAll(currCell.markedTiles);
                    System.out.println(tiles.size());
                    continue;
                }
                if(currCell.g > maxG) continue;

                for(int i = -1; i <= 1; i++) {
                    int newDirection = (currCell.direction + i + directions.length) % directions.length; // gives us the next direction

                    int newX = currCell.x + directions[newDirection][0];
                    int newY = currCell.y + directions[newDirection][1];

                    if(arr.get(newX).get(newY).equals("#")) continue;

                    int newG = currCell.g + (i == 0 ? 1 : 1001);
                    int newH = heuristic(currCell.x, currCell.y, endPos.x, endPos.y);
                    int newF = newG + newH;
                    Set<CellDetails> newMarkedTiles = new HashSet<>(currCell.markedTiles);
                    newMarkedTiles.add(currCell);

                    arr.get(currCell.x).set(currCell.y, "X");

                    CellDetails newCell = new CellDetails(newX, newY, newG, newH, newF, newDirection, newMarkedTiles);

                    q.add(newCell);
                }
            }
        }
        catch(IOException ex) {
            System.out.println(ex);
        }

        for(List<String> a : arr) {
            System.out.println(a);
        }
        System.out.println();

        System.out.println(answer);
        //System.out.println(cache);
        System.out.println((System.currentTimeMillis() - start) + "ms");
        return answer;
    }

    private int heuristic(int x1, int y1, int x2, int y2) {
        return Math.abs(x1 - x2) + Math.abs(y1 - y2);
    }
}
