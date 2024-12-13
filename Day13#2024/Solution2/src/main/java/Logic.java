import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        List<List<Long>> arr = new ArrayList<>();

        try(BufferedReader reader = Files.newBufferedReader(filePath, StandardCharsets.UTF_8)) {
            while(reader.ready()) {
                String line = reader.readLine();;
                if(line.isEmpty()) continue;
                String[] lineSplit = line.replaceAll("(Button A:)|(Button B:)|(Prize:)|\\s+|[XY\\+\\=]", "").split(",");
                List<Long> inputs = new ArrayList<>(List.of(Long.parseLong(lineSplit[0]), Long.parseLong(lineSplit[1])));
                arr.add(inputs);
            }

            /*for(List<Long> i : arr) {
                System.out.println(i);
            }*/

            int tmpAnswer = 0;
            for(int i = 0; i < arr.size(); i=i+3) {
                List<Long> firstPair = arr.get(i);
                List<Long> secondPair = arr.get(i+1);
                List<Long> result = List.of(arr.get(i+2).getFirst() + 10000000000000L, arr.get(i+2).getLast() + 10000000000000L);
                //List<Long> result = List.of(arr.get(i+2).getFirst(), arr.get(i+2).getLast());

                long x1 = firstPair.getFirst();
                long y1 = firstPair.getLast();
                long x2 = secondPair.getFirst();
                long y2 = secondPair.getLast();
                long rx = result.getFirst();
                long ry = result.getLast();

                /*
                    X1 + X2 = RX
                    Y1 + Y2 = RY

                    |  X1   X2  |   =  | RX |
                    |  Y1   Y2  |      | RY |
                 */
                System.out.println(result.getFirst() + " " + result.getLast());
                //System.out.println((firstPair.getFirst() * secondPair.getLast()) + " " + (firstPair.getLast() * secondPair.getFirst() * 1.0));
                long determinant = (x1 * y2) - (x2 * y1);

                /*
                    |  RX   X2  |
                    |  RY   Y2  |
                 */
                long x = (rx * y2) - (x2 * ry);

                /*
                    |  X1   RX  |
                    |  Y1   RY  |
                 */
                long y = (x1 * ry) - (rx * y1);
                //System.out.println(determinant + " " + x + " " + y);

                long calcA = x / determinant;
                long calcB = y / determinant;
                //System.out.println(calcA + " " + calcB);
                //System.out.println( ((calcA * firstPair.getFirst()) + (calcB * secondPair.getFirst())) + " " + ((calcA * firstPair.getLast()) + (calcB * secondPair.getLast())) );

                System.out.println("X: " + (long)((calcA * firstPair.getFirst()) + (calcB * secondPair.getFirst())));
                System.out.println("Y: " + (long)((calcA * firstPair.getLast()) + (calcB * secondPair.getLast())));

                System.out.println((calcA * x1) + (calcB * x2));
                System.out.println(rx);
                System.out.println((calcA * y1) + (calcB * y2));
                System.out.println(ry);
                if((calcA * x1) + (calcB * x2) == rx && (calcA * y1) + (calcB * y2) == ry) {
                    System.out.println("GOOD");
                    answer += (calcA * 3 + calcB);
                }
                System.out.println(answer);
                System.out.println();
            }
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
