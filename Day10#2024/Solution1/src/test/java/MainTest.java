import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.net.URISyntaxException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MainTest {
    private Logic logic;

    public MainTest() {
        logic = new Logic();
    }

    @Test
    public void applicationReady() {
        assertTrue(true);
    }

    @ParameterizedTest(name = "test 1")
    @ValueSource(strings = {"test.txt"})
    public void fileInput1Correct(String fileInput) throws URISyntaxException {
        Path path = Path.of(Main.class.getClassLoader().getResource(fileInput).toURI());
        int answer = logic.runLogic(path);

        assertEquals(2, answer);
    }

    @ParameterizedTest(name = "test 2")
    @ValueSource(strings = {"test2.txt"})
    public void fileInput2Correct(String fileInput) throws URISyntaxException {
        Path path = Path.of(Main.class.getClassLoader().getResource(fileInput).toURI());
        int answer = logic.runLogic(path);

        assertEquals(4, answer);
    }

    @ParameterizedTest(name = "test 3")
    @ValueSource(strings = {"test3.txt"})
    public void fileInput3Correct(String fileInput) throws URISyntaxException {
        Path path = Path.of(Main.class.getClassLoader().getResource(fileInput).toURI());
        int answer = logic.runLogic(path);

        assertEquals(3, answer);
    }

    @ParameterizedTest(name = "test 4")
    @ValueSource(strings = {"test4.txt"})
    public void fileInput4Correct(String fileInput) throws URISyntaxException {
        Path path = Path.of(Main.class.getClassLoader().getResource(fileInput).toURI());
        int answer = logic.runLogic(path);

        assertEquals(36, answer);
    }
}
