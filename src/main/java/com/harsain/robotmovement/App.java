package com.harsain.robotmovement;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by harsain on 7/1/17.
 */
public class App {

    private int isFirstCommand = 0;
    private Robot robot;

    private static App ourInstance = new App();

    public static App getInstance() {
        return ourInstance;
    }

    private App() {
        System.out.println("Robot Movement started");
        System.out.println("Creating Robot");

        robot = new Robot();

        System.out.println("Created default Robot");
    }

    /**
     * Starts the robot movement app
     */
    public void start() {

        boolean isFirstCommand = true;
        boolean requestCommand = true;

        Scanner scanner = new Scanner(System.in);
        // keep the app running & read user inputs
        while (requestCommand) {
            Command command = this.getCommand(scanner, isFirstCommand);
            if (command != null) {
                if (command.getName().equals(CommandNameEnum.QUIT.toString())) {
                    // don't request user for another command
                    requestCommand = false;
                } else{
                    try {
                        robot.command(command);
                        // only update this if the command was successful
                        isFirstCommand = false;
                    } catch (InvalidPositionAttributeException invalidPosEx) {
                        System.out.println("Exception: " + invalidPosEx.getMessage());
                    } catch (InvalidFirstCommandException e) {
                        System.out.println(e.getMessage());
                    }
                }
            }
        }
        // end of the app
        System.out.println("Thanks for using Robot movement. BYE BYE !!!");
    }

    /**
     * Returns a valid command, reading user input
     * @param scanner
     * @return
     */
    private Command getCommand(Scanner scanner, boolean isFirstCommand) {
        try {
            // if the app is running for the first time show welcome messages
            if (this.isFirstCommand == 0) {
                System.out.println("Welcome to the Robot management App:");
                System.out.println("------------------------------------");
                System.out.println("Available commands are: " + Arrays.asList(Command.getCommands()));
                this.isFirstCommand++;
            }

            // read user input
            String commandString = scanner.nextLine();

            // parsing user inputs
            ArrayList<String> userInputs = new ArrayList<String>(Arrays.asList(commandString.split(" ")));

            // check if input exists
            if (userInputs.size() > 0) {
                String commandName = userInputs.get(0);
                userInputs.remove(0);
                // get the arguments
                String arguments = "";
                if (userInputs.size() > 0) {
                    arguments = userInputs.get(0);
                }
                Command command = new Command(commandName, arguments, isFirstCommand);

                return command;
            }
            // displayed on invalid command
            System.out.println("Please enter a valid command");
        } catch (InvalidFirstCommandException invalidFirstCommand) {
            System.out.println(invalidFirstCommand.getMessage() + " try again");
        } catch (Exception ex) {
            System.out.println(ex.getMessage() + ", try again");
        }

        // returned when no command is generated
        return null;
    }
}
