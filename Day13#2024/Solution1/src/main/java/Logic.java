import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
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

            for(int i = 0; i < arr.size(); i=i+3) {
                int tmpAnswer = 0;
                cache = new HashMap<>();
                List<Long> firstPair = arr.get(i);
                List<Long> secondPair = arr.get(i+1);
                List<Long> result = arr.get(i+2);
                /*int ax = 0;
                int ay = 0;

                for(int k = 1; k <= 100; k++) {
                    ax += firstPair.get(0);
                    ay += firstPair.get(1);
                    int bx = 0;
                    int by = 0;

                    for(int j = 1; j <=100; j++) {
                        bx += secondPair.get(0);
                        by += secondPair.get(1);

                        if(ax + bx == result.get(0) && ay + by == result.get(1)) {
                            tmpAnswer += k * 3 + j;
                            break;
                        }
                    }
                }

                answer += tmpAnswer;*/
                tmpAnswer = recurse(firstPair, secondPair, result, 0, 0, 0);
                if(tmpAnswer != Integer.MAX_VALUE) answer += tmpAnswer;
                //System.out.println(answer);
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

    public int recurse(List<Long> first, List<Long> second, List<Long> result, int tokens, int aPressed, int bPressed) {
        if(cache.get(result.getFirst() + "#" + result.getLast()) != null) return cache.get(result.getFirst() + "#" + result.getLast());
        if(aPressed > 100 || bPressed > 100) return Integer.MAX_VALUE;
        if(result.getFirst() < 0 || result.getLast() < 0) return Integer.MAX_VALUE; //we are past result so no solution
        if(result.getFirst() == 0 && result.getLast() == 0) {
            return tokens;
        }

        Integer resultA = recurse(first, second, List.of(result.getFirst() - first.getFirst(), result.getLast() - first.getLast()), tokens + 3, aPressed + 1 ,bPressed);
        Integer resultB = recurse(first, second, List.of(result.getFirst() - second.getFirst(), result.getLast() - second.getLast()), tokens + 1, aPressed, bPressed + 1);

        cache.put(result.getFirst() + "#" + result.getLast(), Math.min(resultA, resultB));

        return Math.min(resultA, resultB);

    }

}
