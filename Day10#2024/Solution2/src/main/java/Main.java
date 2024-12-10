import java.net.URISyntaxException;
import java.nio.file.Path;

public class Main {

    public static void main(String[] args) throws URISyntaxException {
        Logic logic = new Logic();

        System.out.print(logic.runLogic(Path.of(Main.class.getClassLoader().getResource("input.txt").toURI())));
    }
}
