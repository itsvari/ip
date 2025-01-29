package songbird.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests the Ui class.
 * The Ui class is responsible for handling the user interface of the Songbird chatbot,
 * including input and output.
 *
 * @author Ashe Low
 * @version CS2103T AY24/25 Semester 2
 */
public class UiTest {
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    /**
     * Sets up the output stream captor before each test.
     * This is to capture the output of the System.out.println() method.
     */
    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    /**
     * Tests the greet() method in the Ui class.
     * The greet() method should print the Songbird logo and a welcome message.
     */
    @Test
    public void testGreet() {
        Ui ui = new Ui();
        ui.greet();
        String expectedOutput = """
               _____                   __    _          __
              / ___/____  ____  ____ _/ /_  (_)________/ /
              \\__ \\/ __ \\/ __ \\/ __ `/ __ \\/ / ___/ __  /
             ___/ / /_/ / / / / /_/ / /_/ / / /  / /_/ /
            /____/\\____/_/ /_/\\__, /_.___/_/_/   \\__,_/
                             /____/
            Songbird(TM) AI by VariTech Heavy Industries, (C) 3025. All rights reserved.
            We know exactly what you're thinking. Don't worry, we won't judge.
            --------------------
            """;
        assertEquals(expectedOutput.trim(), outputStreamCaptor.toString().trim());
    }

    /**
     * Tests the readCommand() method in the Ui class.
     * The readCommand() method should read the user's command from the console input.
     * The method should return the user's command as a string.
     */
    @Test
    public void testReadCommand() {
        String input = "test command";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Ui ui = new Ui();
        assertEquals(input, ui.readCommand());
    }

    /**
     * Tests the respond() method in the Ui class.
     * The respond() method should respond to the user with the given messages.
     * Each message should be printed on a new line and prefixed with "S> ".
     * The method should take in a variable number of messages to respond with,
     * which are printed in the order they are provided.
     *
     */
    @Test
    public void testRespond() {
        Ui.respond("Hello", "World");
        String expectedOutput = "S> Hello\nS> World";
        assertEquals(expectedOutput.trim(), outputStreamCaptor.toString().trim());
    }

    /**
     * Tests the error() method in the Ui class.
     * The error() method should respond to the user with an error message containing the given text.
     * Each error message should be prefixed with "S> ERROR :: ".
     * The method should take in a variable number of error messages to respond with,
     * which are printed in the order they are provided.
     */
    @Test
    public void testError() {
        Ui.error("An error occurred", "Another error occurred");
        String expectedOutput = "S> ERROR :: An error occurred\nS> ERROR :: Another error occurred";
        assertEquals(expectedOutput.trim(), outputStreamCaptor.toString().trim());
    }
}
