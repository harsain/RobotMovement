package com.harsain.robotmovement;

import junit.framework.TestCase;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;

/**
 * Created by harsain on 8/1/17.
 */
public class RobotTest extends TestCase {
    private Robot robot;
    final ByteArrayOutputStream myOut = new ByteArrayOutputStream();

    public void setUp() throws Exception {
        super.setUp();

        System.setOut(new PrintStream(myOut));

        robot = new Robot();
        robot.setX(0);
        robot.setY(0);
    }

    public void testTemp() {
        assertEquals(true, true);
    }

    public void testIncorrectFirstCommand() throws Exception {
        try {
            robot.setDirection(DirectionEnum.WEST);
            Command cmd = new Command("report", "", false);
            robot.command(cmd);
            fail("Missing Exception");
        } catch (InvalidFirstCommandException expected) {
            assertEquals("First command has to be PLACE int,int,string", expected.getMessage());
        }
    }

    public void testOutOfBoundFirstCommand() throws Exception {
        try {
            robot.setDirection(DirectionEnum.WEST);
            Command cmd = new Command("place", "6,6,east", true);
            robot.command(cmd);
            fail("Missing Exception");
        } catch (InvalidPositionAttributeException e) {
            assertEquals("Either x or y is out of limits", e.getMessage());
        }
    }

    public void testCorrectReportCommand() throws Exception {
        try {
            robot.setDirection(DirectionEnum.WEST);
            Command cmd = new Command("place", "1,1,east", true);
            robot.command(cmd);
            Command cmd1 = new Command("report", "", false);
            OutputStream os = new ByteArrayOutputStream();
            PrintStream ps = new PrintStream(os);
            System.setOut(ps);
            robot.command(cmd1);
            assertEquals(os.toString(), "1, 1, EAST\n");
            System.out.println(os.toString());
            PrintStream originalOut = System.out;
            System.setOut(originalOut);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void testDirectionCommand() throws Exception {
        Command cmd = new Command("place", "1,1,east", true);
        robot.command(cmd);
        Command cmd1 = new Command("left", "", false);
        robot.command(cmd1);
        Command cmd2 = new Command("report", "", false);
        System.setOut(new PrintStream(myOut));
        robot.command(cmd2);
        assertEquals("1, 1, NORTH\n", myOut.toString());
        System.setOut(null);
    }

    public void testOutOfLimitMovementCommand() throws Exception{
        Command cmd = new Command("place", "4,4,north", true);
        robot.command(cmd);
        Command cmd1 = new Command("move", "", false);
        robot.command(cmd1);
        Command cmd2 = new Command("report", "", false);
        System.setOut(new PrintStream(myOut));
        robot.command(cmd2);
        assertEquals("4, 4, NORTH\n", myOut.toString());
        System.setOut(null);
    }

}