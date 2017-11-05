import Config.FilterConfig;
import Config.GroupingConfig;
import Config.ToolConfig;
import Controller.InputController;
import Entities.LogException;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class InputControllerTest {

    String goodData, badData1;
    File resourcesDirectory;

    public InputControllerTest() {
    }

    @Before
    public void setUpFilterP() {
        resourcesDirectory = new File("src/test/resources");
        goodData = "pathI " +
                resourcesDirectory.getAbsolutePath().concat(File.separator + "validTestData") +
                " -usr Ann -pat Error(.)*" +
                "  -grtime year " +
                "-thrN 5 " +
                "-pathO " + resourcesDirectory.getAbsolutePath().concat(File.separator + "testOutput")
                .concat(File.separator + "test1.log");
        badData1 = "pathI " + resourcesDirectory.getAbsolutePath().concat(File.separator + "&invalidFileName") +
                " -usr Ann -pat Error(.)*" +
                "  -grtime year " +
                "-thrN 5 " +
                "-pathO " + resourcesDirectory.getAbsolutePath().concat(File.separator + "testOutput")
                .concat(File.separator + "test1.log");
    }

    @Test
    public void testHandleInput() throws LogException {
        System.out.println(goodData);
        assertEquals(false, InputController.getInstance().handleInput(goodData));
        assertEquals("Ann", FilterConfig.getInstance().getUserName());
        assertEquals("Error(.)*", FilterConfig.getInstance().getPattern());
        assertNull(FilterConfig.getInstance().getTimeStart());
        assertNull(FilterConfig.getInstance().getTimeEnd());
        assertEquals(false, GroupingConfig.getInstance().hasUserName());
        assertEquals(5, ToolConfig.getInstance().getThreadsNumber());
        assertEquals(resourcesDirectory.getAbsolutePath().concat(File.separator + "validTestData"), ToolConfig.getInstance().getLogsPath());
        assertEquals(resourcesDirectory.getAbsolutePath().concat(File.separator + "testOutput")
                .concat(File.separator + "test1.log"), ToolConfig.getInstance().getOutputPath());
    }

    @Test
    public void testHelpCommand() throws LogException {
        assertEquals(true, InputController.getInstance().handleInput("h"));
    }

    @Test(expected = LogException.class)
    public void testWrongInputPath() throws LogException {
        InputController.getInstance().handleInput(badData1);
    }

}