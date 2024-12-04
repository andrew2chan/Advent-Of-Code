import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public class Main {
    public static final String TEXT_TO_LOCATE = "XMAS";
    public static final int[][] directions = new int[][] {
            new int[]{ -1, 0 }, //top
            new int[]{ 0, 1 }, //right
            new int[]{ 1, 0 }, //bottom
            new int[]{ 0, -1 }, //left
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

            /*for(String[] a : arr) {
                System.out.println(Arrays.toString(a));
            }*/

            int counter = 0;
            for(int i = 0; i < arr.length; i++) {
                for(int k = 0; k < arr[i].length; k++) {
                    if(arr[i][k].equals("X")) { // only come in if we find an X
                        for(int j = 0; j < directions.length; j++) {
                            counter += recurse(arr, 0, i, k, directions[j][0], directions[j][1]);
                        }
                    }
                }
            }

            System.out.println(counter);
        }
        catch(IOException ex) {
            System.out.println(ex);
        }
    }

    public static int recurse(String[][] arr, int startingLetterIndex, int currX, int currY, int directionX, int directionY) {
        if(startingLetterIndex >= TEXT_TO_LOCATE.length()) return 0;
        if(!arr[currX][currY].equals(String.valueOf(TEXT_TO_LOCATE.charAt(startingLetterIndex)))) return 0;
        if(startingLetterIndex == TEXT_TO_LOCATE.length()-1) {
            return 1;
        }

        //System.out.println(TEXT_TO_LOCATE.charAt(startingLetterIndex) + " " + currX + " " + currY);

        int counter = 0;
        int nextX = currX + directionX;
        int nextY = currY + directionY;

        if(nextX < 0 || nextX >= arr.length || nextY < 0 || nextY >= arr[0].length) return 0;

        if(arr[nextX][nextY].equals(String.valueOf(TEXT_TO_LOCATE.charAt(startingLetterIndex + 1)))) {
            counter += recurse(arr, startingLetterIndex + 1, nextX, nextY, directionX, directionY);
        }

        return counter;
    }
}
