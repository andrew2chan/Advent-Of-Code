import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Logic {
    private Map<String, Integer> cache = new HashMap<>();
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

        List<Integer> arr = new ArrayList<>();

        try(BufferedReader reader = Files.newBufferedReader(filePath, StandardCharsets.UTF_8)) {
            Pattern pattern = Pattern.compile("(-?\\d+)");
            int topLeftQuad = 0;
            int topRightQuad = 0;
            int bottomLeftQuad = 0;
            int bottomRightQuad = 0;

            while(reader.ready()) {
                String line = reader.readLine();;
                if(line.isEmpty()) continue;
                Matcher matcher = pattern.matcher(line);

                //matches will be in the format [startX, startY, velocityY, velocityX]
                List<Integer> matches = new ArrayList<>();

                while(matcher.find()) {
                    matches.add(Integer.parseInt(matcher.group()));
                }

                //System.out.println(matches);

                Integer startX = matches.get(0);
                Integer startY = matches.get(1);
                // positive velocity moves tile to right / negative moves to left
                Integer velocityX = matches.get(2);
                // positive moves down / negative moves up
                Integer velocityY = matches.get(3);

                final int X_SIZE = 101;
                final int Y_SIZE = 103;
                final int SECONDS_PASSED = 100;

                // general formula = (startPos + (velocity * seconds)) % play area
                Integer finalX = (startX + (velocityX * SECONDS_PASSED)) % X_SIZE;
                Integer finalY = (startY + (velocityY * SECONDS_PASSED)) % Y_SIZE;
                List<Integer> finalValues = new ArrayList<>();

                /*for(int i = 1; i <= 100; i++) {
                    finalX = (startX + (velocityX * i)) % X_SIZE;
                    finalY = (startY + (velocityY * i)) % Y_SIZE;
                    System.out.println(((finalY < 0) ? Y_SIZE + finalY : finalY) + " " + finalX);
                }*/

                finalValues.add((((finalY < 0) ? Y_SIZE + finalY : finalY)));
                finalValues.add((((finalX < 0) ? X_SIZE + finalX : finalX)));
                System.out.println(finalValues);

                int halfBoardX = X_SIZE/2;
                int halfBoardY = Y_SIZE/2;

                System.out.println("BAD: " + halfBoardX + " " + halfBoardY);
                System.out.println(halfBoardY + " " + finalValues.get(0));
                if(halfBoardY != finalValues.get(0) && halfBoardX != finalValues.get(1)) {
                    if(finalValues.get(0) < halfBoardY && finalValues.get(1) < halfBoardX) topLeftQuad++;
                    if(finalValues.get(0) < halfBoardY && finalValues.get(1) > halfBoardX) topRightQuad++;
                    if(finalValues.get(0) > halfBoardY && finalValues.get(1) < halfBoardX) bottomLeftQuad++;
                    if(finalValues.get(0) > halfBoardY && finalValues.get(1) > halfBoardX) bottomRightQuad++;
                    System.out.println("ADDED");
                }
            }

            /*for(List<Long> i : arr) {
                System.out.println(i);
            }*/

            answer += (topRightQuad * topLeftQuad * bottomLeftQuad * bottomRightQuad);
            System.out.println(topLeftQuad + " " + topRightQuad + " " + bottomLeftQuad + " " + bottomRightQuad);
        }
        catch(IOException ex) {
            System.out.println(ex);
        }


        System.out.println(answer);
        //System.out.println(cache);
        System.out.println((System.currentTimeMillis() - start) + "ms");
        return answer;
    }

}
