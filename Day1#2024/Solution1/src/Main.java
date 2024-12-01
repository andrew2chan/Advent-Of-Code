import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Integer> list1 = new ArrayList<>();
        List<Integer> list2 = new ArrayList<>();

        //read from file and save into arraylists
        try(BufferedReader reader = Files.newBufferedReader(Path.of("./src/data.txt"), StandardCharsets.UTF_8)) {
            while(reader.ready()) {
                String line = reader.readLine();
                String[] lineSplit = line.split("\\s+");
                list1.add(Integer.parseInt(lineSplit[0]));
                list2.add(Integer.parseInt(lineSplit[1]));
            }

            //sort lists
            list1.sort((a, b) -> a-b);
            list2.sort((a, b) -> a-b);

            //calculate total
            int totalSize = 0;
            for(int i = 0; i < list1.size(); i++) {
                totalSize += Math.abs(list1.get(i) - list2.get(i));
            }
            System.out.println(totalSize);
        }
        catch(IOException ex) {
            System.out.println(ex);
        }
    }
}
