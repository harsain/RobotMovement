package com.harsain.robotmovement;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by harsain on 7/1/17.
 */
public class Command {
    private static final ArrayList<String> validFirstCommandNames = new ArrayList<String>(Arrays.asList("PLACE", "QUIT"));

    private String name;
    private ArrayList<String> arguments = new ArrayList<String>();

    public Command(String name, String arguments, boolean isFirstCommand) throws Exception {
        if (CommandNameEnum.contains(name.toUpperCase())){
            if (isFirstCommand && !validFirstCommandNames.contains(name.toUpperCase())) {
                throw new InvalidFirstCommandException("First command can only be " + validFirstCommandNames);
            }
            this.name = name.toUpperCase();
            if (this.name.equals(CommandNameEnum.PLACE.toString())) {
                this.validateAndSetArguments(arguments);
            }
        } else {
            throw new Exception("Invalid command");
        }
    }

    private void validateAndSetArguments(String args) throws Exception {
        ArrayList<String> arguments = new ArrayList<String>(Arrays.asList(args.split(",")));
        if (arguments.size() == 3) {
            try {
                Integer x = Integer.parseInt(arguments.get(0));
                Integer y = Integer.parseInt(arguments.get(1));
                DirectionEnum dir = DirectionEnum.valueOf(arguments.get(2).toUpperCase());
                this.arguments.add(String.valueOf(x));
                this.arguments.add(String.valueOf(y));
                this.arguments.add(dir.toString());
            } catch (NumberFormatException numEx) {
                throw new Exception("Invalid x,y values. PLACE takes 3 parameters in format INT,INT,STRING no spaces. eg. 1,2,EAST");
            } catch (IllegalArgumentException illegalArgEx) {
                throw new Exception("Invalid direction input. PLACE takes 3 parameters in format INT,INT,STRING no spaces. eg. 1,2,EAST");
            } catch (Exception ex) {
                throw new Exception("Invalid input. PLACE takes 3 parameters in format INT,INT,STRING no spaces");
            }
        } else {
            throw new InvalidFirstCommandException("Invalid command");
        }
    }

    public static CommandNameEnum[] getCommands() {
        return CommandNameEnum.values();
    }

    public String getName() {
        return name;
    }

    public ArrayList<String> getArguments() {
        return arguments;
    }
}