import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try(BufferedReader reader = Files.newBufferedReader(Path.of("src/test.txt"), StandardCharsets.UTF_8)) {
            String line = reader.readLine();

            List<String> listOfBlock = new ArrayList<>();
            int id = 0;
            for(int i = 0; i < line.length(); i++) { //create an arraylist that is in the correct format
                for (int k = 0; k < Character.getNumericValue(line.charAt(i)); k++) {
                    if (i % 2 == 0) {
                        listOfBlock.add(String.valueOf(id));
                    }
                    else{
                        listOfBlock.add(".");
                    }
                }
                if(i%2 == 0) id++;
            }

            //System.out.println(listOfBlock);

            int left = 0;
            int right = listOfBlock.size() - 1;
            while(left < right) {
                while(left < right && !listOfBlock.get(left).equals(".")) { //left looks for not .
                    left++;
                }
                while(left < right && listOfBlock.get(right).equals(".")) { //right looks for .
                    right--;
                }
                String tmp = listOfBlock.get(right); //swap
                listOfBlock.set(left, tmp);
                listOfBlock.set(right, ".");
            }

            System.out.println(listOfBlock);

            long checkSum = 0;
            for(int i = 0; i < listOfBlock.size(); i++) {
                if(listOfBlock.get(i).equals(".")) break;
                checkSum += Long.parseLong(listOfBlock.get(i)) * i;
            }

            System.out.println(checkSum);
        }
        catch(IOException ex) {
            System.out.println(ex.getStackTrace());
        }
    }
}
