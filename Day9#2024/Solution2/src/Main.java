import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        try(BufferedReader reader = Files.newBufferedReader(Path.of("src/input.txt"), StandardCharsets.UTF_8)) {
            String line = reader.readLine();

            ArrayList<String> listOfBlock = new ArrayList<>();
            Stack<String> mainStack = new Stack<>();
            Stack<String> tmpStack = new Stack<>();
            Map<String, List<Integer>> map = new HashMap<>();

            int id = 0;
            for(int i = 0; i < line.length(); i++) { //create an arraylist that is in the correct format
                for (int k = 0; k < Character.getNumericValue(line.charAt(i)); k++) {
                    if (i % 2 == 0) {
                        listOfBlock.add(String.valueOf(id));
                        map.putIfAbsent(String.valueOf(id), new ArrayList<>());
                        map.get(String.valueOf((id))).add(listOfBlock.size()-1);
                    }
                    else{
                        listOfBlock.add(".");
                    }
                }

                if(i%2 == 0) {
                    mainStack.push(String.valueOf(id));
                    id++;
                }
            }

            //System.out.println(listOfBlock);


            for(int i = 0; i < listOfBlock.size(); i++) {
                if(!listOfBlock.get(i).equals(".")) { continue; }
                int leftStartPos = i;
                int freeSpace = 0;
                do {
                    freeSpace++;
                    i++;
                } while(listOfBlock.get(i).equals("."));

                while(!mainStack.empty()) {
                    String key = mainStack.pop();
                    if(map.get(key).size() <= freeSpace) {
                        if(leftStartPos > map.get(key).getFirst()) break;
                        for(int k = 0; k < map.get(key).size(); k++) {
                            listOfBlock.set(leftStartPos, key);
                            listOfBlock.set(map.get(key).get(k), "#");
                            leftStartPos++;
                        }
                        map.remove(key);
                        i = 0;
                        //System.out.println(listOfBlock);
                        break;
                    }
                    tmpStack.push(key);
                }

                while(!tmpStack.empty()) mainStack.push(tmpStack.pop());
            }

            //System.out.println(listOfBlock);

            long checkSum = 0;
            for(int i = 0; i < listOfBlock.size(); i++) {
                if(listOfBlock.get(i).equals(".") || listOfBlock.get(i).equals("#")) continue;
                checkSum += Long.parseLong(listOfBlock.get(i)) * i;
            }

            System.out.println(checkSum);
            System.out.println(System.currentTimeMillis() - start + "ms");
        }
        catch(IOException ex) {
            System.out.println(ex.getStackTrace());
        }
    }
}
