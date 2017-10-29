import Config.FilterConfig;
import Config.GroupingConfig;
import Config.ToolConfig;
import Controller.InputController;
import Entities.LogException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class InputControllerTest {

    String goodData, badData1;

    public InputControllerTest() {
    }

    @Before
    public void setUpFilterP() {
        goodData = "pathI \"C://Users//DDD//IdeaProjects//LogAnalysator//logs\"" +
                " -usr Ann -pat Error(.)*" +
                "  -grtime year " +
                "-thrN 5 -pathO \"C://Users//DDD//IdeaProjects//LogAnalysator//outbound.log\"";
        badData1 = "pathI \"C://Users//DDD//Idea&Projects//LogAnalysator//logs\"" +
                " -usr Ann -pat Error(.)*" +
                "  -grtime year " +
                "-thrN 5 -pathO \"C://Users//DDD//IdeaProjects//LogAnalysator//outbound.log\"";
    }

    @Test
    public void testHandleInput() throws LogException {
        assertEquals(false, InputController.getInstance().handleInput(goodData));
        assertEquals("Ann", FilterConfig.getInstance().getUserName());
        assertEquals("Error(.)*", FilterConfig.getInstance().getPattern());
        assertNull(FilterConfig.getInstance().getTimeStart());
        assertNull(FilterConfig.getInstance().getTimeEnd());
        assertEquals(false, GroupingConfig.getInstance().hasUserName());
        assertEquals(5, ToolConfig.getInstance().getThreadsNumber());
        assertEquals("C://Users//DDD//IdeaProjects//LogAnalysator//logs", ToolConfig.getInstance().getLogsPath());
        assertEquals("C://Users//DDD//IdeaProjects//LogAnalysator//outbound.log", ToolConfig.getInstance().getOutputPath());
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